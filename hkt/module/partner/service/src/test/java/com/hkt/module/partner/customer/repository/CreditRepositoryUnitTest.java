package com.hkt.module.partner.customer.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.partner.AbstractUnitTest;
import com.hkt.module.partner.customer.entity.Credit;
import com.hkt.module.partner.customer.repository.CreditRepository;
import com.hkt.util.json.JSONSerializer;

@Transactional
public class CreditRepositoryUnitTest extends AbstractUnitTest {
  @Autowired
  CreditRepository repository;

  @Test
  public void testCrud() {
    Credit emp = repository.save(create("123zzo", 1));
    assertEquals(emp, repository.findOne(emp.getId()));
    repository.delete(emp);
    assertEquals(0, repository.count());
  }
  
  @Test
  public void testCrud1() {
    Credit emp = repository.save(create("123zzo", 1, true));
    
    assertNotNull(emp);
    assertEquals(1, repository.findByValueRecycleBin(true).size());
  }


  @Test
  public void testSearch() throws IOException {
    for (int i = 0; i < 5; i++) {
      repository.save(create("123zzo" + i, i));
    }
    Credit Credit = repository.getByCustomerId(2l);
    Assert.assertNotNull(Credit);
   
    FilterQuery query = new FilterQuery() ;
    query.add("loginId", FilterQuery.Operator.LIKE, "123zzo*") ;
    FilterResult<Credit> result = repository.search(query) ;
    assertEquals(5, result.getData().size()) ;
    
    query.add("credit", FilterQuery.Operator.EQUAL, "1000000") ;
    result = repository.search(query) ;
    assertEquals(5, result.getData().size()) ;
    
    String json = JSONSerializer.INSTANCE.toString(query);
    query = JSONSerializer.INSTANCE.fromString(json, FilterQuery.class) ;
    System.out.println(json);
  }

  public Credit create(String loginId, long customerId) {
    Credit emp = new Credit();
    emp.setLoginId(loginId);
    emp.setCustomerId(customerId);
    emp.setCredit(1000000);
    return emp;
  }
  
  public Credit create(String loginId, long customerId, boolean k) {
	    Credit emp = new Credit();
	    emp.setLoginId(loginId);
	    emp.setCustomerId(customerId);
	    emp.setCredit(1000000);
	    emp.setRecycleBin(k);
	    return emp;
  }
  
}
