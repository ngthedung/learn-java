package com.hkt.module.hr.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.Account.Type;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.repository.AccountGroupRepository;
import com.hkt.module.account.repository.AccountRepository;
import com.hkt.module.hr.AbstractUnitTest;
import com.hkt.module.hr.entity.WorkPosition;
import com.hkt.module.hr.entity.WorkPosition.SalaryType;
import com.hkt.module.hr.entity.WorkPosition.Status;

@Transactional
public class WorkPositionRepositoryUnitTest extends AbstractUnitTest {
  @Autowired
  WorkPositionRepository repository;

  @Autowired
  AccountRepository accountRepository;
  
  @Autowired
  AccountGroupRepository accountGroupRepository;
    
  @Before
  public void createAccount(){
    Account account = new Account();
    account.setLoginId("long");
    account.setPassword("abc");
    account.setEmail("long@email.com");
    account.setType(Type.USER);
    accountRepository.save(account);
    
    AccountGroup account2 = new AccountGroup();
    account2.setName("hkt");
    account2.setPath("hkt");
    accountGroupRepository.save(account2);
  }
  
  @Test
  public void testCrud() {
    WorkPosition slr = repository.save(create(new Long(1)));
    assertEquals(slr, repository.findOne(slr.getId()));
    repository.delete(slr);
    assertEquals(0, repository.count());
  }

  //khanhdd
  @Test
  public void testCrud1() {
    WorkPosition slr = repository.save(create(new Long(1), true));
    
    assertNotNull(slr);
    assertEquals(1, repository.findByValueRecycleBin(true).size());
  }
  
  @Test
  public void testFind() {
    for (int i = 0; i < 5; i++) {
      repository.save(create(new Long(1)));
    }
    assertEquals(5, repository.findByEmployeeId(new Long(1)).size());
    
    Pageable topTen = new PageRequest(0, 10);
    Page<WorkPosition> page = repository.findByEmployeeId(1l, topTen);
    assertTrue(page.isFirstPage()) ;
    assertEquals(1, page.getTotalPages());
    assertEquals(5, page.getNumberOfElements());
  }

  public WorkPosition create(Long empId) {
    WorkPosition work = new WorkPosition();
    work.setEmployeeId(empId);
    work.setLoginId("long");
    work.setPositionTitle("Nhan Vien");
    work.setGroup("hkt");
    work.setFromDate(new Date());
    work.setToDate(null);
    work.setStatus(Status.Active);
    work.setSalaryType(SalaryType.Monthly);
    work.setSalary(100000);
    return work;
  }

  //khanhdd
  public WorkPosition create(Long empId, boolean k) {
	    WorkPosition work = new WorkPosition();
	    work.setEmployeeId(empId);
	    work.setLoginId("long");
	    work.setPositionTitle("Nhan Vien");
	    work.setGroup("hkt");
	    work.setFromDate(new Date());
	    work.setToDate(null);
	    work.setStatus(Status.Active);
	    work.setSalaryType(SalaryType.Monthly);
	    work.setSalary(100000);
	    work.setRecycleBin(k);
	    return work;
	  }
}
