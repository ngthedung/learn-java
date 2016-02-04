package com.hkt.client.swingexp.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.AccountServiceClient;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.khachhang.BasicInformation;
import com.hkt.client.swingexp.app.khachhang.OrganizationBasic;
import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.Account.Type;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountGroupDetail;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.account.entity.Profiles;

public class AccountModelManager {

  private static AccountModelManager instance;

  private ClientContext clientContext = ClientContext.getInstance();
  private AccountServiceClient clientCore = clientContext.getBean(AccountServiceClient.class);
  public static String Department = "Phòng ban";
  public static String Customer = "Khách hàng";
  public static String Supplier = "Nhà phân phối";
  public static String Employee = "Nhân viên";

  private AccountModelManager() {

  }

  public void saveSettings() {
    ClientContext.getInstance();
  }

  static public AccountModelManager getInstance() {
    if (instance == null) {
      instance = new AccountModelManager();
    }
    return instance;
  }

  public Account getAccountByLoginId(String loginId) throws Exception {
    return clientCore.getAccountByLoginId(loginId);
  }
  
  public Account getByEmail(String email){
    try {
     return clientCore.getByEmail(email); 
    } catch (Exception e) {
      return null;
    }
  }

  public Account getAccountById(long id) throws Exception {
    return clientCore.getAccountById(id);
  }
  
  public List<AccountGroup> findByParentPath(String path) throws Exception{
  	return clientCore.findByParentPath(path);
  }
  
  public List<AccountGroup> findByName(String path)  throws Exception{
  	return clientCore.findByName(path);
  }

  public Account saveAccount(Account account) throws Exception {
    return clientCore.saveAccount(account);
  }

  public List<Account> findAccountByType(Type type) throws Exception {
    return clientCore.findAccountByType(type);
  }

  public AccountGroup getGroupByPath(String path) throws Exception {
    return clientCore.getGroupByPath(path);
  }

  public AccountGroup getGroupByName(String name) throws Exception {
    return clientCore.getGroupByName(name);
  }

  public boolean deleteGroup(AccountGroup group) throws Exception {
    clientCore.deleteGroup(group);
    return true;
  }

  public AccountGroup saveGroup(AccountGroup group) throws Exception {
    return clientCore.saveGroup(group);
  }

  public AccountGroupDetail getRootGroupDetail() throws Exception {
    return clientCore.getRootGroupDetail();
  }

  public AccountGroupDetail getGroupDetailByPath(String path) throws Exception {
  	AccountGroupDetail accountGroupDetail =  clientCore.getGroupDetailByPath(path);
  	List<AccountGroup> list = accountGroupDetail.getChildren();
		Collections.sort(list, new Comparator<AccountGroup>() {
			@Override
			public int compare(AccountGroup it1, AccountGroup it2) {
				if (it1.getPriority() > it2.getPriority()) {
					return 1;
				} else {
					if (it1.getPriority() < it2.getPriority()) {
						return -1;
					} else {
						return 0;
					}
				}

			}
		});
		accountGroupDetail.setChildren(list);
    return accountGroupDetail;
  }

  public List<AccountGroup> getChildrensByPath(String parentPath) throws Exception {
  	List<AccountGroup> list = clientCore.getChildrensByPath(parentPath);
		Collections.sort(list, new Comparator<AccountGroup>() {
			@Override
			public int compare(AccountGroup it1, AccountGroup it2) {
				if (it1.getPriority() > it2.getPriority()) {
					return 1;
				} else {
					if (it1.getPriority() < it2.getPriority()) {
						return -1;
					} else {
						return 0;
					}
				}

			}
		});
    return list;
  }

//  public List<AccountGroup> findAllAccountGroup() throws Exception {
//	  	List<AccountGroup> list = clientCore.
//			Collections.sort(list, new Comparator<AccountGroup>() {
//				@Override
//				public int compare(AccountGroup it1, AccountGroup it2) {
//					if (it1.getPriority() > it2.getPriority()) {
//						return 1;
//					} else {
//						if (it1.getPriority() < it2.getPriority()) {
//							return -1;
//						} else {
//							return 0;
//						}
//					}
//
//				}
//			});
//	    return list;
//	  }

  
  public List<AccountGroup> getAllChildrensByPath(String path) throws Exception {
  	List<AccountGroup> list = clientCore.getAllChildrensByPath(path);
		Collections.sort(list, new Comparator<AccountGroup>() {
			@Override
			public int compare(AccountGroup it1, AccountGroup it2) {
				if (it1.getPriority() > it2.getPriority()) {
					return 1;
				} else {
					if (it1.getPriority() < it2.getPriority()) {
						return -1;
					} else {
						return 0;
					}
				}

			}
		});
    return list;
  }

  public List<AccountMembership> findMembershipByGroupPath(String path) throws Exception {
    return clientCore.findMembershipByGroupPath(path);
  }

  public List<AccountMembership> findMembershipByAccountLoginId(String loginId) throws Exception {
    return clientCore.findMembershipByAccountLoginId(loginId);
  }

  public AccountMembership saveAccountMembership(AccountMembership accountMembership) throws Exception {
	  if(accountMembership.getId()==null){
			AccountMembership aa = clientCore.getMembershipByAccountAndGroup(accountMembership.getLoginId(), accountMembership.getGroupPath());
			if(aa!=null){
		    aa.setRecycleBin(false);
			aa.setRole(accountMembership.getRole());
			return clientCore.saveAccountMembership(aa);
			}else {
				 return clientCore.saveAccountMembership(accountMembership);
			}
		}else {
			 return clientCore.saveAccountMembership(accountMembership);
		}
		
	 
  }

  public AccountMembership getMembershipByAccountAndGroup(String loginId, String path) throws Exception {
    return clientCore.getMembershipByAccountAndGroup(loginId, path);
  }

  public String getNameByLoginId(String loginId) {
    try {
      String name;
      Account account = getAccountByLoginId(loginId);
      if (account.getType() == Type.USER) {
        name = account.getProfiles().getBasic().get(BasicInformation.LAST_NAME).toString();
      } else {
        name = account.getProfiles().getBasic().get(OrganizationBasic.NAME).toString();
      }
      return name;
    } catch (Exception e) {
      return loginId;
    }
  }

  public String getBirthdayByLoginId(String loginId) {
    try {
      String name;
      Account account = getAccountByLoginId(loginId);
      if (account.getType() == Type.USER) {
        name = account.getProfiles().getBasic().get(BasicInformation.BIRTHDAY).toString();
      } else {
        name = account.getProfiles().getBasic().get(OrganizationBasic.FOUNDED_DATE).toString();
      }
      return name;
    } catch (Exception e) {
      return loginId;
    }
  }

  public String getSexByLoginId(String loginId) {

    try {
      String name;
      Account account = getAccountByLoginId(loginId);
      name = account.getProfiles().getBasic().get(BasicInformation.GENDER).toString();
      return name;
    } catch (Exception e) {
      return loginId;
    }

  }

  public String getMarriageByLoginId(String loginId) {

    try {
      String name;
      Account account = getAccountByLoginId(loginId);
      name = account.getProfiles().getBasic().get(BasicInformation.MARITAL_STATUS).toString();
      return name;
    } catch (Exception e) {

      return loginId;
    }
  }

  public String getPersonalIDByLoginId(String loginId) {

	    try {
	      String name;
	      Account account = getAccountByLoginId(loginId);
	      name = account.getProfiles().getBasic().get(BasicInformation.PERSONAL_ID).toString();
	      return name;
	    } catch (Exception e) {

	      return loginId;
	    }
	  }
  
  public String getPhoneByLoginId(String loginId) {
    try {
      String name;
      Account account = getAccountByLoginId(loginId);
      name = account.getContacts().get(0).getPhone()[0];
      return name;
    } catch (Exception e) {
      return "";
    }
  }

  public String getFaxByLoginId(String loginId) {
    try {
      String name;
      Account account = getAccountByLoginId(loginId);
      name = account.getContacts().get(0).getFax()[0];
      return name;
    } catch (Exception e) {
      return "";
    }
  }
  
  public String getEmailByLoginId(String loginId) {
    try {
      String name;
      Account account = getAccountByLoginId(loginId);
      name = account.getEmail();
      return name;
    } catch (Exception e) {
      return "";
    }
  }
  

  public String getRegistrationCodeByLoginId(String loginId) {
    try {
      String name;
      Account account = getAccountByLoginId(loginId);
      name = account.getProfiles().getBasic().get(OrganizationBasic.REGISTRATION_CODE).toString();
      return name;
    } catch (Exception e) {
      return "";
    }
  }
  
  public String getRepresentativeByLoginId(String loginId) {
	  try {
	      String name;
	      Account account = getAccountByLoginId(loginId);
	      name = account.getProfiles().getBasic().get(OrganizationBasic.REPRESENTATIVE).toString();
	      return name;
	    } catch (Exception e) {
	      return "";
	    }
  }

  public String getAddressByLoginId(String loginId) {

    try {
      String name;
      Account account = getAccountByLoginId(loginId);
      if (account.getContacts() != null && !account.getContacts().isEmpty()) {
        name = account.getContacts().get(0).getAddressNumber();
      } else {
        name = "";
      }
      return name;
    } catch (Exception e) {
      return "";
    }
  }

  public String getWebsideByLoginId(String loginId) {
    try {
      String name;
      Account account = getAccountByLoginId(loginId);
      name = account.getContacts().get(0).getWebsite()[0];
      return name;
    } catch (Exception e) {
      return "";
    }
  }

  public String getCountryByLoginId(String loginId) {
    try {
      String name;
      Account account = getAccountByLoginId(loginId);
      name = account.getContacts().get(0).getCountry();
      return name;
    } catch (Exception e) {
      return "";
    }
  }

  public String getCityByLoginId(String loginId) {
    try {
      String name;
      Account account = getAccountByLoginId(loginId);
      name = account.getContacts().get(0).getCity();
      return name;
    } catch (Exception e) {
      return "";
    }
  }

  public String getStartdayByLoginId(String loginId) {
    try {
      String name;
      Account account = getAccountByLoginId(loginId);
      name = account.getProfiles().getBasic().get(OrganizationBasic.FOUNDED_DATE).toString();
      return name;
    } catch (Exception e) {
      return "";
    }
  }

  public String getStopdayByLoginId(String loginId) {
    try {
      String name;
      Account account = getAccountByLoginId(loginId);
      name = account.getProfiles().getBasic().get(OrganizationBasic.TERMINATED_DATE).toString();
      return name;
    } catch (Exception e) {
      return "";
    }
  }

  public Profile getProfileConfigTable() {
    try {
      Account account = AccountModelManager.getInstance().getByEmail("admin@gmail.com");
      Profile profile = account.getProfiles().getConfig();
      if(profile==null){
        profile = new Profile();
      }
      return profile;
    } catch (Exception e) {
      return null;
    }
  }

  public Profile getProfileConfigAdmin() {
    try {
      Account account = AccountModelManager.getInstance().getAccountByLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
      Profile profile = account.getProfiles().getConfig();
      if(profile==null){
        profile = new Profile();
      }
      return profile;
    } catch (Exception e) {
      return new Profile();
    }
  }

  public void saveProfileConfig(String loginId, Profile profile) {
    try {
      Account account = getAccountByLoginId(loginId);
      Profiles profiles = account.getProfiles();
      if (profiles == null) {
        profiles = new Profiles();
      }
      profiles.setConfig(profile);
      account.setProfiles(profiles);
      saveAccount(account);
    } catch (Exception e) {
    }
  }
  
  public void deleteAccountByLoginId(String loginId) throws Exception{
    clientCore.deleteAccountByLoginId(loginId);
  }

  public void deleteMembershipByLoginIdAndGroupPath(String loginId, String groupPath) throws Exception {
    clientCore.deleteMembershipByLoginIdAndGroupPath(loginId, groupPath);
  }

  public void deleteTest(String test) throws Exception {
    clientCore.deleteTest(test);
  }

}
