package com.hkt.module.partner.customer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hkt.module.account.AccountService;
import com.hkt.module.account.util.AccountScenario;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterQuery.Operator;
import com.hkt.module.partner.AbstractUnitTest;
import com.hkt.module.partner.customer.entity.Credit;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.customer.entity.Point;
import com.hkt.module.partner.customer.util.CustomerScenario;
import com.hkt.util.json.JSONSerializer;

public class CustomerServiceUnitTest extends AbstractUnitTest {
  
  @Autowired
  CustomerService service;
  
  private CustomerScenario scenarioCustomer;
  
  @Autowired
  private AccountService accountService;
  
  @Before
  public void setup() throws Exception {
    accountService.createScenario(AccountScenario.loadTest());
    scenarioCustomer = CustomerScenario.loadTest();
    service.createScenarioCustomer(scenarioCustomer);
  }
  
  @After
  public void tearDown() throws Exception {
    service.deleteAll();
    accountService.deleteAll();
  }
  
  @Test
  public void testSerialization() throws Exception {
    System.out.println(JSONSerializer.INSTANCE.toString(scenarioCustomer));
    assertTrue(scenarioCustomer.getCustomers().size() > 0);
    assertNotNull(scenarioCustomer.getRatio());
  }
  
//  @Test
//  public void testCustomer() {
//    FilterQuery filterQuery = new FilterQuery();
//    filterQuery.add("loginId", Operator.LIKE, "%mu%");
//    Customer customer = service.searchCustomer(filterQuery).getData().get(0);
//    assertNotNull(customer);
//    long customerId = customer.getId();
//    
//    Credit credit = service.getCreditByCustomerId(customerId);
//    assertNotNull(credit);
//    long creditId = credit.getId();
//    assertEquals(2, service.findCreditTransactionByCreditId(creditId).size());
//    assertEquals(2000000, credit.getCredit(), 0);
//    
//    service.deleteCreditTransactionByInvoiceId(2l);
//    credit = service.getCreditByCustomerId(customerId);
//    
//    assertEquals(5000000, credit.getCredit(), 0);
//    
//    Point point = service.getPointByCustomerId(customerId);
//    assertNotNull(point);
//    long pointId = point.getId();
//    assertEquals(2, service.findPointTransactionByPointId(pointId).size());
//    assertEquals(100, point.getPoint(), 0);
//    
//    service.deletePointTransactionByInvoiceId(2L);
//    point = service.getPointByCustomerId(customerId);
//    
//    assertEquals(150, point.getPoint(), 0);
//    
//    service.deleteCustomer(customer);
//    
//    assertEquals(0, service.findPointTransactionByPointId(pointId).size());
//    assertEquals(0, service.findCreditTransactionByCreditId(creditId).size());
//    
//    credit = service.getCreditByCustomerId(customerId);
//    assertNull(credit);
//    point = service.getPointByCustomerId(customerId);
//    assertNull(point);
//  }
  
  @Test
  public void testPointConversionRule() {
    assertEquals(1, service.getPointConversionRules().size());
  }
  
}
