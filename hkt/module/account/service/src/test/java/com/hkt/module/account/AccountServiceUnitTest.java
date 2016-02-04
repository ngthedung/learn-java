package com.hkt.module.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.AccountDetail;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountGroupDetail;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.account.util.AccountScenario;
import com.hkt.module.core.ServiceCallback;
import com.hkt.module.core.search.SearchQuery;
import com.hkt.module.core.search.SearchResult;
import com.hkt.util.json.JSONSerializer;
import com.hkt.util.test.Verifier;

public class AccountServiceUnitTest extends AbstractUnitTest {
  static ServiceCallback<AccountService> FAIL_CALLBACK = new ServiceCallback<AccountService>() {
    public void callback(AccountService service) {
      throw new RuntimeException("Fail. Expect a rollback if method annotate with the Transactionnal");
    }
  };
  
  static public String ORG_ACCOUNT = "hkt";
  static public String USER_ACCOUNT = "tu.phan";
  
  @Autowired
  AccountService service;
  
  private AccountScenario scenario;
  
  @Before
  public void setup() throws Exception {
    scenario = AccountScenario.loadTest();
    service.createScenario(scenario);
  }
  
  @After
  public void tearDown() throws Exception {
    service.deleteAll();
  }
  
  @Test
  public void testSerialization() throws IOException {
    assertTrue(scenario.getAccounts().size() > 0);
    assertTrue(scenario.getGroups().size() > 0);
    
    AccountDetail hktDetail = scenario.getAccounts().get(0);
    Account hkt = hktDetail.getAccount();
    
    // assertNotNull(hkt.getProfiles().getBasic().get("foundedDate")) ;
    System.out.println(JSONSerializer.INSTANCE.toString(scenario));
  }
  
  @Test
  public void testAccountCrud() throws Exception {
    // test transaction
    try {
      service.deleteAccountCallBack(service.getAccountByLoginId(ORG_ACCOUNT), FAIL_CALLBACK);
    } catch (Throwable t) {
      System.out.println("Fail callback exception: " + t.getMessage());
    }
    
    Account orgAccount = service.getAccountByLoginId(ORG_ACCOUNT);
    assertNotNull(orgAccount);
    assertNotNull(orgAccount.getProfiles());
    
    assertEquals(1, orgAccount.getContacts().size());
    assertEquals(Capability.ADMIN, service.getMembershipByAccountAndGroup(ORG_ACCOUNT, "hkt/employees")
        .getCapability());
    
    service.deleteAccountByLoginId(ORG_ACCOUNT);
    
    assertNull(service.getAccountByLoginId(ORG_ACCOUNT));
    assertNull(service.getMembershipByAccountAndGroup(ORG_ACCOUNT, "hkt/employees"));
  }
  
  @Test
  public void testFindAccounts() {
    List<Account> accounts = service.findAccountByLoginId("tu");
    assertEquals(10, accounts.size());
  }
  
  @Test
  public void testFilterAccounts() {
    // TODO: test filterAccounts(...) method
  }
  
  @Test
  public void testSearchAccounts() throws Exception {
    SearchQuery query = new SearchQuery("title:hkt");
    SearchResult<Account> result = service.searchAccounts(query);
    
    assertEquals(1, result.getTotalHits());
    
    System.out.println(JSONSerializer.INSTANCE.toString(result));
  }
  
  @Test
  public void testAccountDetailGet() throws Exception {
    Verifier<AccountDetail> accVerifier = new Verifier<AccountDetail>() {
      public void verify(AccountDetail detail) {
        assertNotNull(detail);
        assertEquals(ORG_ACCOUNT, detail.getAccount().getLoginId());
        assertEquals(3, detail.getMemberships().size());
        assertTrue(detail.getMemberships().contains(service.getMembershipByAccountAndGroup(ORG_ACCOUNT, "hkt")));
        assertTrue(detail.getMemberships().contains(
            service.getMembershipByAccountAndGroup(ORG_ACCOUNT, "hkt/employees")));
      }
    };
    accVerifier.verify(service.getAccountDetail(ORG_ACCOUNT));
    accVerifier.verify(service.getAccountDetail(service.getAccountByLoginId(ORG_ACCOUNT).getId()));
  }
  
  @Test
  public void testAccountGroupCrud() {
    // test transaction
    try {
      service.deleteGroupCallBack(service.getGroupByPath("hkt"), FAIL_CALLBACK);
    } catch (Throwable t) {
      System.out.println("Fail callback exception: " + t.getMessage());
    }
    
    // Expect the group and membership shoud not be deleted as there is an
    // exception
    // and spring framework should rollback the db to the initial state
    assertNotNull(service.getGroupByPath("hkt"));
    assertEquals(2, service.findMembershipByGroupPath("hkt/employees").size());
    
    assertNotNull(service.getGroupByPath("hkt/employees/leader"));
    assertEquals(2, service.findMembershipByGroupPath("hkt/employees/leader").size());
    
    service.deleteGroup(service.getGroupByPath("hkt"));
    
    assertNull(service.getGroupByPath("hkt"));
    assertEquals(0, service.findMembershipByGroupPath("hkt").size());
    assertNull(service.getGroupByPath("hkt/employees"));
    assertEquals(0, service.findMembershipByGroupPath("hkt/employees").size());
    assertNull(service.getGroupByPath("hkt/employees/leader"));
    assertEquals(0, service.findMembershipByGroupPath("hkt/employees/leader").size());
  }
  
  @Test
  public void testAccountGroupDetailGet() throws Exception {
    assertEquals(1, service.getRootGroupDetail().getChildren().size());
    AccountGroupDetail employeesGrp = service.getGroupDetailByPath("hkt/employees");
    assertNotNull(employeesGrp);
    assertEquals(2, employeesGrp.getMemberships().size());
    assertTrue(employeesGrp.getMemberships().contains(service.getMembershipByAccountAndGroup("hkt", "hkt/employees")));
    
    AccountGroupDetail leaderGrp = service.getGroupDetailByPath("hkt/employees/leader");
    assertNotNull(leaderGrp);
    assertEquals(2, leaderGrp.getMemberships().size());
    assertTrue(leaderGrp.getMemberships().contains(
        service.getMembershipByAccountAndGroup("hkt", "hkt/employees/leader")));
  }
  
  @Test
  public void testAccountMembership() throws Exception {
    AccountGroup group = service.getGroupByPath("hkt/employees/it");
     assertNotNull(group);
    
    AccountMembership membership = create("hkt", "hkt/employees/it", Capability.READ);
    membership = service.saveAccountMembership(membership);
    assertNotNull(membership);
    
  }
}