package com.hkt.module.account.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.account.AbstractUnitTest;
import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.account.entity.AccountMembership.Capability;

@Transactional
public class AccountMembershipRepositoryUnitTest extends AbstractUnitTest {
  @Autowired 
  AccountMembershipRepository repository;
  @Autowired
  AccountRepository accountRepository;
  @Autowired
  AccountGroupRepository accountGroupRepository;
  
  
  @Test
  public void testCrud() {
    Account account = createAccount("admin");
    AccountGroup accountGroup = createGroup("employee");
    AccountMembership membership = create(account.getLoginId(), accountGroup.getPath(), Capability.READ) ;
    repository.save(membership) ;
    assertNotNull(repository.findOne(membership.getId())) ;
    repository.delete(membership.getId()) ;
    assertEquals(0, repository.count()) ;
    accountRepository.deleteAll();
    accountGroupRepository.deleteAll();
  }
  
  //khanhdd
  @Test
  public void testCrud1() {
    Account account = createAccount("admin");
    AccountGroup accountGroup = createGroup("employee");
    AccountMembership membership = create(account.getLoginId(), accountGroup.getPath(), Capability.READ) ;
    membership.setRecycleBin(true);
    repository.save(membership) ;
   
    assertNotNull(membership);
    assertEquals(1, repository.findByValueRecycleBin(true).size());
  }
  
  @Test
  public void testDeleteBy() {
    Account account = createAccount("admin");
    AccountGroup accountGroup = createGroup("employee");
    AccountGroup accountGroupChild = createGroup("employee/hr");
    repository.save(create(account.getLoginId(), accountGroup.getPath(), Capability.READ)) ;
    repository.save(create(account.getLoginId(), accountGroupChild.getPath(), Capability.READ)) ;
    repository.deleteByAccountLoginId(account.getLoginId()) ;
    assertEquals(0, repository.count()) ;
    Account acc1 = createAccount("user1");
    Account acc2 = createAccount("user2");
    repository.save(create(acc1.getLoginId(), accountGroup.getPath(), Capability.READ)) ;
    repository.save(create(acc2.getLoginId(), accountGroup.getPath(), Capability.READ)) ;
    repository.deleteByGroupPath(accountGroup.getPath()) ;
    assertEquals(0, repository.count()) ;
    accountRepository.deleteAll();
    accountGroupRepository.deleteAll();
  }
  
  @Test
  public void testFindByLoginId() {
    Account acc = createAccount("user");
    for(int i = 1; i <= 3; i++) {
      AccountGroup accountGroup = createGroup("group"+i);
      repository.save(create(acc.getLoginId(), accountGroup.getPath(), Capability.READ)) ;
    }
    assertEquals(3, repository.findByAccountLoginId(acc.getLoginId()).size())  ;
    accountRepository.deleteAll();
    accountGroupRepository.deleteAll();
  } 
  
  @Test
  public void testFindByGroupPath() {
    AccountGroup accountGroup = createGroup("group");
    for(int i = 1; i <= 3; i++) {
      Account acc = createAccount("user"+i);
      repository.save(create(acc.getLoginId(), accountGroup.getPath(), Capability.READ)) ;
    }
    assertEquals(3, repository.findByGroupPath(accountGroup.getPath()).size())  ;
    accountRepository.deleteAll();
    accountGroupRepository.deleteAll();
  }
  
  @Test
  public void testFindByAccountGroupId() {
    Account acc = createAccount("user");
    AccountGroup accountGroup = createGroup("group1");
    AccountGroup accountGroup2 = createGroup("group2");
    AccountMembership expect = repository.save(create(acc.getLoginId(), accountGroup.getPath(), Capability.READ)) ;
    repository.save(create(acc.getLoginId(), accountGroup2.getPath(), Capability.READ)) ;
    assertEquals(expect, repository.getByAccountAndGroup("user", "group1"))  ;
    accountRepository.deleteAll();
    accountGroupRepository.deleteAll();
  }
  
  public Account createAccount(String loginId){
    Account account = new Account();
    account.setLoginId(loginId);
    account.setPassword(loginId);
    account.setEmail(loginId+"@test");
    accountRepository.save(account);
    return account;
  }
  
  public AccountGroup createGroup(String name){
    AccountGroup accountGroup = new AccountGroup();
    accountGroup.setName(name);
    accountGroup.setPath(name);
    accountGroupRepository.save(accountGroup);
    return accountGroup;
  }
}