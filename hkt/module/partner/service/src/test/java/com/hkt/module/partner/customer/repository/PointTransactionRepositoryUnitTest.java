package com.hkt.module.partner.customer.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.partner.AbstractUnitTest;
import com.hkt.module.partner.customer.entity.PointTransaction;
import com.hkt.module.partner.customer.repository.PointTransactionRepository;

@Transactional
public class PointTransactionRepositoryUnitTest extends AbstractUnitTest {
  @Autowired
  PointTransactionRepository repository;
  
  @Test
  public void testCrud() {
    PointTransaction emp = repository.save(create());
    assertEquals(emp, repository.findOne(emp.getId()));
    repository.delete(emp);
    assertEquals(0, repository.count());
  }
  
  @Test
  public void testCrud1() {
    PointTransaction emp = repository.save(create(true));
    
    assertNotNull(emp);
    System.out.println(repository.findByInvoiceId(1));
    assertNotNull(repository.getByInvoiceId(1));
    assertEquals(1, repository.findByValueRecycleBin(true).size());
  }
  
  @Test
  public void testSearch() throws IOException {
    for (int i = 0; i < 5; i++) {
      repository.save(create());
    }
    assertEquals(5, repository.findByPointConversionRuleId(3l).size());
    repository.deleteByPointConversionRuleId(3l);
    assertEquals(0, repository.findByPointConversionRuleId(3l).size());
    
    for (int i = 0; i < 5; i++) {
      repository.save(create());
    }
    assertEquals(5, repository.findByPointId(2l).size());
    repository.deleteByPointId(2l);
    assertEquals(0, repository.findByPointId(2l).size());
  }
  
  public PointTransaction create() {
    PointTransaction emp = new PointTransaction();
    emp.setLoginId("123zzo");
    emp.setPointId(2l);
    emp.setInvoiceId(1);
    emp.setPointConversionRuleId(3l);
    emp.setPoint(-20);
    return emp;
  }
  
  //khanhdd
  public PointTransaction create(boolean k) {
	    PointTransaction emp = new PointTransaction();
	    emp.setLoginId("123zzo");
	    emp.setPointId(2l);
	    emp.setInvoiceId(1);
	    emp.setPointConversionRuleId(3l);
	    emp.setPoint(-20);
	    emp.setRecycleBin(k);
	    return emp;
  }
}
