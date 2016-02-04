package com.hkt.module.partner.customer.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.partner.AbstractUnitTest;
import com.hkt.module.partner.customer.entity.CreditTransaction;
import com.hkt.module.partner.customer.repository.CreditTransactionRepository;

@Transactional
public class CreditTransactionRepositoryUnitTest extends AbstractUnitTest {
  @Autowired
  CreditTransactionRepository repository;

  @Test
  public void testCrud() {
    CreditTransaction emp = repository.save(create());
    assertEquals(emp, repository.findOne(emp.getId()));
    repository.delete(emp);
    assertEquals(0, repository.count());
  }

  @Test
  public void testCrud1() {
    CreditTransaction emp = repository.save(create(true));
    
    assertNotNull(emp);
    assertEquals(1, repository.findByValueRecycleBin(true).size());
  }
  
  @Test
  public void testSearch() throws IOException {
    for (int i = 0; i < 5; i++) {
      repository.save(create());
    }
    assertEquals(5, repository.findByCreditId(2l).size()) ;
    repository.deleteByCreditId(2l);
    assertEquals(0, repository.findByCreditId(2l).size()) ;
  }

  public CreditTransaction create() {
    CreditTransaction emp = new CreditTransaction();
    emp.setLoginId("123zzo");
    emp.setInvoiceId(1);
    emp.setCreditId(2);
    emp.setAmount(200000);
    emp.setDescription("Khach hang thanh toan truoc thang 1");
    return emp;
  }
  
  public CreditTransaction create(boolean k) {
	    CreditTransaction emp = new CreditTransaction();
	    emp.setLoginId("123zzo");
	    emp.setInvoiceId(1);
	    emp.setCreditId(2);
	    emp.setAmount(200000);
	    emp.setRecycleBin(k);
	    emp.setDescription("Khach hang thanh toan truoc thang 1");
	    return emp;
  }
}
