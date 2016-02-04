package com.hkt.module.partner.customer.rest;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hkt.module.account.AccountService;
import com.hkt.module.account.util.AccountScenario;
import com.hkt.module.core.ServiceCallback;
import com.hkt.module.core.rest.Request;
import com.hkt.module.core.rest.Response;
import com.hkt.module.core.rest.RestService;
import com.hkt.module.partner.AbstractUnitTest;
import com.hkt.module.partner.customer.CustomerService;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.customer.entity.Point;
import com.hkt.module.partner.customer.util.CustomerScenario;
import com.hkt.util.json.JSONSerializer;

public class CustomerServiceDispatcherUnitTest extends AbstractUnitTest {

  static ServiceCallback<CustomerService> FAIL_CALLBACK = new ServiceCallback<CustomerService>() {
    public void callback(CustomerService service) {
      throw new RuntimeException("Fail. Expect a rollback if method annotate with the Transactionnal");
    }
  };

  @Autowired
  RestService restService;

  @Autowired
  CustomerService service;
  
  @Autowired
  private AccountService  accountService ;

  @Before
  public void setup() throws Exception {
    accountService.createScenario(AccountScenario.loadTest()) ;
    CustomerScenario scenario = CustomerScenario.loadTest();
    service.createScenarioCustomer(scenario);    
  }

  @After
  public void tearDown() throws Exception {
    service.deleteAll();
    accountService.deleteAll();
  }
  
  @Test
  public void testRestService() throws Exception {
//    Request req = new Request("partner", "CustomerService", "getPointByCustomerId");
//    Customer customer = service.searchCustomer(null).getData().get(0);
//    req.addParam("customerId", customer.getId());
//    Response res = restService.dispatch(req);
//    System.out.println(JSONSerializer.INSTANCE.toString(res));
//    Point retInvoice = res.getDataAs(Point.class);
//    assertNotNull(retInvoice);
  }
}