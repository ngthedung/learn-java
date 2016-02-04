package com.hkt.module.account;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.Account.Type;
import com.hkt.module.account.entity.AccountDetail;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountGroupDetail;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.account.repository.AccountGroupRepository;
import com.hkt.module.account.repository.AccountMembershipRepository;
import com.hkt.module.account.repository.AccountRepository;
import com.hkt.module.account.util.AccountScenario;
import com.hkt.module.core.ServiceCallback;
import com.hkt.module.core.entity.AbstractPersistable.State;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.core.search.FSSearchService;
import com.hkt.module.core.search.SearchQuery;
import com.hkt.module.core.search.SearchResult;

@Service("AccountService")
public class AccountService {

	@Autowired
	private AccountRepository						accountRepo;

	@Autowired
	private AccountGroupRepository			accountGroupRepo;

	@Autowired
	private AccountMembershipRepository	membershipRepo;

	@Autowired
	private FSSearchService							searchService;

	public AccountService() {
		super();
	}

	@Transactional(readOnly = true)
	public Account getAccountById(Long accoutId) {
		return accountRepo.findOne(accoutId);
	}

	@Transactional(readOnly = true)
	public Account getByEmail(String email) {
		return accountRepo.getByEmail(email);
	}

	@Transactional(readOnly = true)
	public Account getAccountByLoginId(String loginId) {
		return accountRepo.getByLoginId(loginId);
	}

	@Transactional(readOnly = true)
	public Account login(String loginId, String password) {
		Account account = accountRepo.getByLoginId(loginId);
		if (account != null) {
			if (account.getPassword().equals(password))
				account.setPassword(null);
			else
				account = null;
		}
		return account;
	}

	@Transactional
	public Account saveAccount(Account account) throws Exception {
		boolean isNew = account.isNew();
		account = accountRepo.save(account);
		searchService.add(account.getId(), account, isNew);
		return account;
	}
  //khanhdd
	/** @param loginId */
	@Transactional
	public boolean deleteAccountByLoginId(String loginId) throws Exception {
		Account account = accountRepo.getByLoginId(loginId);
		if(account == null)
			return false;
		else return deleteAccountCallBack(account, null);
	}

	//khanhdd
	/**
	 * 
	 * @param account
	 * @param callback
	 */
	@Transactional
	public boolean deleteAccountCallBack(Account account, ServiceCallback<AccountService> callback) throws Exception {
		if (callback != null) {
			callback.callback(this);
		}
//		searchService.delete(Account.class, account.getId());
		if(account.isRecycleBin()){
			membershipRepo.deleteByAccountLoginId(account.getLoginId());
			accountRepo.delete(account.getId());
			return true;
		} else {
			try{
				List<AccountMembership> accMss = membershipRepo.findByAccountLoginId(account.getLoginId());
				for(AccountMembership acm : accMss){
					acm.setRecycleBin(true);
					membershipRepo.save(acm);
				}
				account.setRecycleBin(true);
				if(accountRepo.save(account) != null){
					return true;
				}else {
					return false;
				}	
			}catch(Exception e){
				return false;
			}		
		}
	}

	//khanhdd
	/**
	 * @param loginId
	 * @param groupPath
	 */
	@Transactional
	public boolean deleteMembershipByLoginIdAndGroupPath(String loginId, String groupPath) {
		AccountMembership accms = membershipRepo.getByAccountAndGroup(loginId, groupPath);
		if(accms == null){
			return false;
		} else {
			if(accms.isRecycleBin()){
				membershipRepo.deleteByAccountLoginIdAndGroupPath(loginId, groupPath);
				return true;
			} else {
				accms.setRecycleBin(true);
				if(membershipRepo.save(accms) != null)
					return true;
				else return false;
			}
		}

	}

	@Transactional(readOnly = true)
	public List<Account> findAccountByLoginId(String loginId) {
		loginId = loginId.replace('*', '%').toLowerCase();
		return accountRepo.findByLoginId(loginId);
	}

	@Transactional(readOnly = true)
	public List<Account> findAccountByLoginIdUser(String loginId) {
		loginId = loginId.replace('*', '%').toLowerCase();
		return accountRepo.findByLoginIdByUser(loginId, Type.USER);
	}

	@Transactional(readOnly = true)
	public List<Account> findAccountByLoginIdOrg(String loginId) {
		loginId = loginId.replace('*', '%').toLowerCase();
		return accountRepo.findByLoginIdByOrg(loginId, Type.ORGANIZATION);
	}

	public FilterResult<Account> filterAccount(FilterQuery query) {
		if (query == null)
			query = new FilterQuery();
		return accountRepo.search(query);
	}

	public SearchResult<Account> searchAccounts(SearchQuery query) throws Exception {
		return this.searchService.query(Account.class, query);
	}

	@Transactional(readOnly = true)
	public AccountDetail getAccountDetail(Long accoutId) {
		Account account = accountRepo.findOne(accoutId);
		if (account == null)
			return null;
		List<AccountMembership> memberships = membershipRepo.findByAccountLoginId(account.getLoginId());
		AccountDetail detail = new AccountDetail(account, memberships);
		return detail;
	}

	@Transactional(readOnly = true)
	public AccountDetail getAccountDetail(String loginId) {
		Account account = accountRepo.getByLoginId(loginId);
		if (account == null)
			return null;
		List<AccountMembership> memberships = membershipRepo.findByAccountLoginId(account.getLoginId());
		AccountDetail detail = new AccountDetail(account, memberships);
		return detail;
	}

	@Transactional
	public void saveAccountDetail(AccountDetail accDetail) throws Exception {
		Account account = accDetail.getAccount();
		account = saveAccount(account);
		List<AccountMembership> memberships = accDetail.getMemberships();
		if (memberships != null) {
			for (AccountMembership m : memberships) {
				AccountGroup group = getGroupByPath(m.getGroupPath());
				saveOrUpdateMembership(account, group, m.getCapability());
			}
		}
	}

	@Transactional(readOnly = true)
	public AccountGroup getGroupById(Long id) {
		return accountGroupRepo.findOne(id);
	}

	@Transactional(readOnly = true)
	public AccountGroup getGroupByPath(String path) {
		return accountGroupRepo.getByPath(path);
	}

	@Transactional(readOnly = true)
	public AccountGroup getByName(String name) {
		return accountGroupRepo.getByName(name);
	}

	@Transactional(readOnly = true)
	public List<AccountGroup> getChildrensByPath(String path) {
		return accountGroupRepo.getChildrensByPath(path);
	}
	
	@Transactional(readOnly = true)
	public List<AccountGroup> findByName(String path) {
		return accountGroupRepo.findByName(path);
	}

	@Transactional(readOnly = true)
	public List<AccountGroup> getAllChildrensByPath(String path) {
		return accountGroupRepo.getAllChildrensByPath(path);
	}

	@Transactional
	public AccountGroup saveGroup(AccountGroup group) {
		return accountGroupRepo.save(group);
	}

	@Transactional
	public AccountGroup createGroup(String parentPath, AccountGroup group) {
		AccountGroup parent = null;
		if (parentPath != null) {
			parent = accountGroupRepo.getByPath(parentPath);
			if (parent == null)
				throw new RuntimeException("Cannot find group " + parentPath);
		}
		group.setParent(parent);
		group = accountGroupRepo.save(group);
		return group;
	}

	@Transactional
	public void saveGroups(List<AccountGroup> groups) {
		if (groups != null) {
			for (AccountGroup group : groups) {
				String path = group.getPath();
				String parentPath = null;
				int idx = path.lastIndexOf('/');
				if (idx > 0)
					parentPath = path.substring(0, idx);
				createGroup(parentPath, group);
			}
		}
	}

	/**
	 * @param id
	 */
	public boolean deleteAccountGroup(Long id) {
		AccountGroup group = accountGroupRepo.findOne(id);
		if(group != null)
		 return deleteGroupCallBack(group, null);
		else return false;
	}

	/**
	 * @param group
	 */
	@Transactional
	public boolean deleteGroup(AccountGroup group) {
		return deleteGroupCallBack(group, null);
	}

	//khanhdd
	@Transactional
	public boolean deleteGroupCallBack(AccountGroup group, ServiceCallback<AccountService> callback) {
		if (callback != null)
			callback.callback(this);
		if(group == null){
			return false;
		}else{
			if(group.isRecycleBin()){
				accountGroupRepo.cascadeDelete(group);
				membershipRepo.deleteByGroupPath(group.getPath());
				return true;
			}else{
				try{
					List<AccountMembership> accmss = membershipRepo.findByGroupPath(group.getPath());
					for(AccountMembership ams : accmss){
						ams.setRecycleBin(true);
						membershipRepo.save(ams);
					}
					group.setRecycleBin(true);
					accountGroupRepo.save(group);
					return true;
				} catch(Exception e){
					return false;
				}
			}
		}
	}

	@Transactional(readOnly = true)
	public AccountGroupDetail getGroupDetailById(Long id) {
		AccountGroup group = accountGroupRepo.findOne(id);
		if (group == null)
			return null;
		List<AccountMembership> memberships = membershipRepo.findByGroupPath(group.getPath());
		List<AccountGroup> children = accountGroupRepo.findByParentPath(group.getPath());
		AccountGroupDetail detail = new AccountGroupDetail(group, memberships, children);
		return detail;
	}

	@Transactional(readOnly = true)
	public AccountGroupDetail getGroupDetailByPath(String path) {
		if (path == null)
			return getRootGroupDetail();

		AccountGroup group = accountGroupRepo.getByPath(path);
		if (group == null)
			return null;
		List<AccountMembership> memberships = membershipRepo.findByGroupPath(group.getPath());
		List<AccountGroup> children = accountGroupRepo.findByParentPath(group.getPath());
		AccountGroupDetail detail = new AccountGroupDetail(group, memberships, children);
		return detail;
	}

	@Transactional(readOnly = true)
	public AccountGroupDetail getRootGroupDetail() {
		List<AccountMembership> memberships = new ArrayList<AccountMembership>();
		List<AccountGroup> children = accountGroupRepo.findRootGroup();
		AccountGroupDetail detail = new AccountGroupDetail(null, memberships, children);
		return detail;
	}

	@Transactional(readOnly = true)
	public AccountMembership getMembership(Long id) {
		return membershipRepo.findOne(id);
	}

	@Transactional
	public AccountMembership saveOrUpdateMembership(Account account, AccountGroup group, Capability cap) {
		return saveOrUpdateMembership(account.getLoginId(), group.getPath(), cap);
	}

	AccountMembership saveOrUpdateMembership(String loginId, String gpath, Capability cap) {
		AccountMembership membership = membershipRepo.getByAccountAndGroup(loginId, gpath);
		if (membership == null) {
			membership = new AccountMembership();
			membership.setLoginId(loginId);
			membership.setGroupPath(gpath);
		}
		membership.setCapability(cap);
		return membershipRepo.save(membership);
	}

	@Transactional
	public AccountMembership saveAccountMembership(AccountMembership accountMembership) {
		return membershipRepo.save(accountMembership);
	}

	@Transactional
	public void saveAccountMemberships(List<AccountMembership> list) {
		if (list == null)
			return;
		for (AccountMembership m : list) {
			if (m.getPersistableState() == State.DELETED) {
				if (!m.isNew())
					membershipRepo.delete(m);
			} else {
				saveOrUpdateMembership(m.getLoginId(), m.getGroupPath(), m.getCapability());
			}
		}
	}

	public AccountMembership createMembership(String loginId, String groupPath, Capability cap) {
		Account account = accountRepo.getByLoginId(loginId);
		AccountGroup group = this.accountGroupRepo.getByPath(groupPath);
		return saveOrUpdateMembership(account, group, cap);
	}

	//khanhdd
	@Transactional
	public AccountMembership deleteMembership(String loginId, String groupPath) {
		AccountMembership membership = membershipRepo.getByAccountAndGroup(loginId, groupPath);
		if(membership == null){
			return null;
		} else {
			if(membership.isRecycleBin()){
				membershipRepo.delete(membership);
				return membership;
			} else {
				membership.setRecycleBin(true);
				return membershipRepo.save(membership);
			}
		}
	}

	@Transactional(readOnly = true)
	public List<AccountMembership> findMembershipByAccountLoginId(String loginId) {
		return membershipRepo.findByAccountLoginId(loginId);
	}

	@Transactional(readOnly = true)
	public List<AccountMembership> findMembershipByGroupPath(String path) {
		return membershipRepo.findByGroupPath(path);
	}

	@Transactional(readOnly = true)
	public AccountMembership getMembershipByAccountAndGroup(String loginId, String gpath) {
		return membershipRepo.getByAccountAndGroup(loginId, gpath);
	}

	public void createScenario(AccountScenario scenario) throws Exception {
		saveGroups(scenario.getGroups());
		if (scenario.getAccounts() != null) {
			for (AccountDetail sel : scenario.getAccounts())
				saveAccountDetail(sel);
		}
	}

	public String ping() {
		return "AccountService alive!!!";
	}

	@Transactional
	public void deleteAll() throws Exception {
		accountRepo.deleteAll();
		accountGroupRepo.deleteAll();
		membershipRepo.deleteAll();
		searchService.delete(Account.class);
	}

	@Transactional(readOnly = true)
	public List<AccountGroup> findGroupByName(String name) {
		return accountGroupRepo.findByName(name.toLowerCase());
	}

	@Transactional(readOnly = true)
	public List<AccountGroup> findGroupByParentPathAndName(String parentPath, String name) {
		return accountGroupRepo.findByParentPathAndName(parentPath, name.toLowerCase());
	}

	@Transactional(readOnly = true)
	public List<AccountGroup> findByParentPath(String parentPath) {
		return accountGroupRepo.findByParentPath(parentPath);
	}

	@Transactional(readOnly = true)
	public List<Account> findAccountByType(Type type) {
		return accountRepo.findAccountByType(type);
	}

	@Transactional(readOnly = true)
	public List<AccountGroup> findAllAccountGroup() {
		return (List<AccountGroup>) accountGroupRepo.findByValueRecycleBin(false);
	}

	@Transactional
	public void deleteTest(String test) {
		accountRepo.deleteTest(test);
		membershipRepo.deleteTest(test);
		accountGroupRepo.deleteTest(test);
	}

}