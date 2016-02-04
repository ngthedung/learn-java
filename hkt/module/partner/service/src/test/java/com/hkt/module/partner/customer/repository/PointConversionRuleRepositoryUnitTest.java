package com.hkt.module.partner.customer.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.partner.AbstractUnitTest;
import com.hkt.module.partner.customer.entity.PointConversionRule;
import com.hkt.module.partner.customer.repository.PointRatioRepository;

@Transactional
public class PointConversionRuleRepositoryUnitTest extends AbstractUnitTest {
  @Autowired
  PointRatioRepository repository;
  
  @Test
  public void testCrud() {
    PointConversionRule emp = repository.save(create(1));
    assertEquals(emp, repository.findOne(emp.getId()));
    assertEquals(emp, repository.getConversionRuleMoneyToPoint(new Date()));
    repository.delete(emp);
    assertEquals(0, repository.count());
  }
  
  //khanhdd
  @Test
  public void testCrud1() {
    PointConversionRule emp = repository.save(create(1, true));
    assertNotNull(emp);
    assertEquals(1, repository.findByValueRecycleBin(true).size());
  }
  
  @Test
  public void testSearch() throws IOException {
    for (int i = 0; i < 5; i++) {
      repository.save(create(i));
    }
    assertEquals(5, repository.findByOrganizationLoginId("hkt").size());
    PointConversionRule emp = repository.getConversionRuleMoneyToPoint(new Date());
    System.out.println(emp.getName());
    repository.deleteByOrganizationLoginId("hkt");
    assertEquals(0, repository.findByOrganizationLoginId("hkt").size());
  }
  
  public PointConversionRule create(int i) {
    PointConversionRule emp = new PointConversionRule();
    emp.setName("quy doi diem" + i);
    emp.setOrganizationLoginId("hkt");
    emp.setStatus(PointConversionRule.Status.Active);
    emp.setMinPointToTrigger(1000);
    emp.setPointToCreditRatio(0.001);
    Calendar c = Calendar.getInstance();
    c.add(Calendar.DATE, 2);
    emp.setDateTo(c.getTime());
    emp.setDateFrom(new Date());
    return emp;
  }
  
  public PointConversionRule create(int i, boolean k) {
	    PointConversionRule emp = new PointConversionRule();
	    emp.setName("quy doi diem" + i);
	    emp.setOrganizationLoginId("hkt");
	    emp.setStatus(PointConversionRule.Status.Active);
	    emp.setMinPointToTrigger(1000);
	    emp.setPointToCreditRatio(0.001);
	    emp.setRecycleBin(k);
	    Calendar c = Calendar.getInstance();
	    c.add(Calendar.DATE, 2);
	    emp.setDateTo(c.getTime());
	    emp.setDateFrom(new Date());
	    return emp;
	  }
  
}
