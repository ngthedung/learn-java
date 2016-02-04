package com.hkt.module.partner.supplier.rest;

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
import com.hkt.module.partner.supplier.SupplierService;
import com.hkt.module.partner.supplier.entity.Supplier;
import com.hkt.module.partner.supplier.util.SupplierScenario;
import com.hkt.util.json.JSONSerializer;

public class SupplierServiceDispatcherUnitTest extends AbstractUnitTest {

  static ServiceCallback<SupplierService> FAIL_CALLBACK = new ServiceCallback<SupplierService>() {
    public void callback(SupplierService service) {
      throw new RuntimeException("Fail. Expect a rollback if method annotate with the Transactionnal");
    }
  };

  @Autowired
  RestService restService;

  @Autowired
  SupplierService service;
  
  @Autowired
  private AccountService  accountService ;

  @Before
  public void setup() throws Exception {
    accountService.createScenario(AccountScenario.loadTest()) ;
    SupplierScenario scenario1 = SupplierScenario.loadTest();
    service.createScenarioSupplier(scenario1);
  }

  @After
  public void tearDown() throws Exception {
    service.deleteAll();
    accountService.deleteAll();
  }
  
  @Test
  public void testRestService() throws Exception {    
    Request req1 = new Request("partner", "SupplierService", "saveSupplier");
    Supplier supplier = new Supplier();
    supplier.setLoginId("tranAnh");
    supplier.setOrganizationLoginId("hkt");
    req1.addParam("supplier", supplier);
    Response res1 = restService.dispatch(req1);
    System.out.println(JSONSerializer.INSTANCE.toString(res1));
  }
}