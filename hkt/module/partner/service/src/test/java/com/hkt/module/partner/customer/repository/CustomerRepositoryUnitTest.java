package com.hkt.module.partner.customer.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.Account.Type;
import com.hkt.module.account.repository.AccountRepository;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.partner.AbstractUnitTest;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.customer.repository.CustomerRepository;
import com.hkt.util.json.JSONSerializer;

@Transactional
public class CustomerRepositoryUnitTest extends AbstractUnitTest {
  @Autowired
  CustomerRepository repository;
  
  @Autowired
  AccountRepository accountRepository;
    
  @Before
  public void createAccount(){
    Account account = new Account();
    account.setLoginId("123zzo");
    account.setPassword("abc");
    account.setEmail("long@email.com");
    account.setType(Type.USER);
    accountRepository.save(account);
    
    Account account2 = new Account();
    account2.setLoginId("hkt");
    account2.setPassword("abc");
    account2.setEmail("hkt@email.com");
    account2.setType(Type.ORGANIZATION);
    accountRepository.save(account2);
  }
  
  @After
  public void endTest(){
    accountRepository.deleteAll();
    repository.deleteAll();
  }
  
  @Test
  public void testCrud() {
    Customer emp = repository.save(create("123zzo", "hkt"));
    assertEquals(emp, repository.findOne(emp.getId()));
    repository.delete(emp);
    assertEquals(0, repository.count());
  }
//khanhdd
  @Test
  public void testCrud1() {
    Customer emp = repository.save(create("123zzo", "hkt", true));
    
    assertNotNull(emp);
    assertEquals(1, repository.findByValueRecycleBin(true).size());
  }
  
//  @Test
//  public void testSearch() throws IOException {
//    for (int i = 0; i < 5; i++) {
//      repository.save(create("123zzo" , "hkt"));
//    }
//    assertEquals(5, repository.findByOrganizationLoginId("hkt").size());
//   
//    FilterQuery query = new FilterQuery() ;
//    query.add("loginId", FilterQuery.Operator.LIKE, "123zz*") ;
//    FilterResult<Customer> result = repository.search(query) ;
//    assertEquals(5, result.getData().size()) ;
//    
//    query.add("organizationLoginId", FilterQuery.Operator.LIKE, "hkt*") ;
//    result = repository.search(query) ;
//    assertEquals(5, result.getData().size()) ;
//    
//    String json = JSONSerializer.INSTANCE.toString(query);
//    query = JSONSerializer.INSTANCE.fromString(json, FilterQuery.class) ;
//    System.out.println(json);
//  }

  public Customer create(String loginId, String orgLoginId) {
    Customer emp = new Customer();
    emp.setLoginId(loginId);
    emp.setOrganizationLoginId(orgLoginId);
    return emp;
  }
  
  public Customer create(String loginId, String orgLoginId, boolean k) {
	    Customer emp = new Customer();
	    emp.setLoginId(loginId);
	    emp.setOrganizationLoginId(orgLoginId);
	    emp.setRecycleBin(k);
	    return emp;
	  }
  
}
