package com.hkt.module.product.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hkt.module.account.AccountService;
import com.hkt.module.account.util.AccountScenario;
import com.hkt.module.cms.CMSService;
import com.hkt.module.core.rest.Request;
import com.hkt.module.core.rest.Response;
import com.hkt.module.core.rest.RestService;
import com.hkt.module.product.ProductService;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.util.ProductScenario;
import com.hkt.module.warehouse.AbstractUnitTest;
import com.hkt.util.json.JSONSerializer;

public class ProductServiceDispatcherUnitTest extends AbstractUnitTest {
  @Autowired
  RestService restService;

  @Autowired
  ProductService service;
  
  private AccountScenario accountScenario;
  
  @Autowired
  private AccountService accountService;
  
  @Autowired
  CMSService cmsService;

  @Before
  public void setup() throws Exception {
    accountScenario = AccountScenario.loadTest();
    accountService.createScenario(accountScenario);
    
    ProductScenario scenario = ProductScenario.loadTest();
    service.createScenario(scenario);
  }

  @After
  public void tearDown() throws Exception{
    service.deleteAll();
    accountService.deleteAll();
    cmsService.deleteAll();
  }

  @Test
  public void testRestService() throws Exception {
    Request req = new Request("warehouse", "ProductService", "getProductByCode");
    req.addParam("code", "iphone5s");
    Response res = restService.dispatch(req);
    System.out.println(JSONSerializer.INSTANCE.toString(res));
    Product product = res.getDataAs(Product.class);
    assertNotNull(product);
    assertEquals("iphone5s", product.getCode());
  }
}