package com.hkt.module.partner.customer.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Assert;
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
import com.hkt.module.partner.customer.entity.Point;
import com.hkt.module.partner.customer.repository.PointRepository;
import com.hkt.util.json.JSONSerializer;

@Transactional
public class PointRepositoryUnitTest extends AbstractUnitTest {
  @Autowired
  PointRepository repository;
  
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
  }

  @Test
  public void testCrud() {
    Point emp = repository.save(create("123zzo", 1));
    assertEquals(emp, repository.findOne(emp.getId()));
    repository.delete(emp);
    assertEquals(0, repository.count());
  }
  
  //khanhdd
  @Test
  public void testCrud1() {
    Point emp = repository.save(create("123zzo", 1, true));
    
    assertNotNull(emp);
    assertEquals(1, repository.findByValueRecycleBin(true).size());
  }

  @Test
  public void testSearch() throws IOException {
    for (int i = 0; i < 5; i++) {
      repository.save(create("123zzo", i));
    }
    Point Point = repository.getByCustomerId(2l);
    Assert.assertNotNull(Point);
   
    FilterQuery query = new FilterQuery() ;
    query.add("loginId", FilterQuery.Operator.LIKE, "123zz*") ;
    FilterResult<Point> result = repository.search(query) ;
    assertEquals(5, result.getData().size()) ;
    
    query.add("point", FilterQuery.Operator.EQUAL, "100") ;
    result = repository.search(query) ;
    assertEquals(5, result.getData().size()) ;
    
    String json = JSONSerializer.INSTANCE.toString(query);
    query = JSONSerializer.INSTANCE.fromString(json, FilterQuery.class) ;
    System.out.println(json);
  }

  public Point create(String loginId, long customerId) {
    Point emp = new Point();
    emp.setLoginId(loginId);
    emp.setCustomerId(customerId);
    emp.setPoint(100);
    return emp;
  }
  
  //khanhdd
  public Point create(String loginId, long customerId, boolean k) {
	    Point emp = new Point();
	    emp.setLoginId(loginId);
	    emp.setCustomerId(customerId);
	    emp.setPoint(100);
	    emp.setRecycleBin(k);
	    return emp;
}
  
}
