package com.hkt.module.hr.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

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
import com.hkt.module.hr.AbstractUnitTest;
import com.hkt.module.hr.entity.Employee;
import com.hkt.util.json.JSONSerializer;

@Transactional
public class EmployeeRepositoryUnitTest extends AbstractUnitTest {
  @Autowired
  EmployeeRepository repository;
  
  @Autowired
  AccountRepository accountRepository;
    
  @Before
  public void createAccount(){
    Account account = new Account();
    account.setLoginId("long");
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
    Calendar date = Calendar.getInstance();
    Employee emp = repository.save(create("long", "hkt", date.getTime(), date.getTime()));
    assertEquals(emp, repository.findOne(emp.getId()));
    repository.delete(emp);
    assertEquals(0, repository.count());
  }

  //khanhdd
  @Test
  public void testCrud1() {
    Calendar date = Calendar.getInstance();
    Employee emp = repository.save(create("long", "hkt", date.getTime(), date.getTime(), true));
   
    assertNotNull(emp);
    assertEquals(1, repository.findByValueRecycleBin(true).size());
  }
  
  @Test
  public void testSearch() throws IOException {
    Calendar date = Calendar.getInstance();
    for (int i = 0; i < 5; i++) {
      repository.save(create("long" , "hkt", date.getTime(), date.getTime()));
    }
    assertEquals(5, repository.findByOrganizationLoginId("hkt").size());
  }

  public Employee create(String loginId, String orgLoginId, Date startDate, Date leaveDate) {
    Employee emp = new Employee();
    emp.setLoginId(loginId);
    emp.setOrganizationLoginId(orgLoginId);
    emp.setStartDate(startDate);
    emp.setLeaveDate(leaveDate);
    return emp;
  }
  
  //khanhdd
  public Employee create(String loginId, String orgLoginId, Date startDate, Date leaveDate, boolean k) {
	    Employee emp = new Employee();
	    emp.setLoginId(loginId);
	    emp.setOrganizationLoginId(orgLoginId);
	    emp.setStartDate(startDate);
	    emp.setLeaveDate(leaveDate);
	    emp.setRecycleBin(k);
	    return emp;
	  }
}
