package com.hkt.module.promotion.rest;

import java.util.List;

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
import com.hkt.module.product.repository.ProductRepository;
import com.hkt.module.product.util.ProductScenario;
import com.hkt.module.promotion.PromotionService;
import com.hkt.module.promotion.entity.MenuItemRef;
import com.hkt.module.promotion.util.PromotionScenario;
import com.hkt.module.warehouse.AbstractUnitTest;
import com.hkt.util.json.JSONSerializer;

public class PromotionDispatcherUnitTest extends AbstractUnitTest {
  @Autowired
  RestService      restService;
  
  @Autowired
  PromotionService service;
  

  @Autowired
  ProductService productService;
  
  @Autowired
  private AccountService  accountService ;
  
  @Autowired
  private CMSService cmsService;
  
  @Autowired
  ProductRepository repository;
  
  @Before
  public void setup() throws Exception {
    accountService.createScenario(AccountScenario.loadTest()) ;
    productService.createScenario(ProductScenario.loadTest());
    PromotionScenario scenario = PromotionScenario.loadTest();
    service.createScenarioPromotion(scenario);
  }
  
  @After
  public void deleteAll() throws Exception {
    service.deleteAll();
    productService.deleteAll();
    accountService.deleteAll();
    cmsService.deleteAll();
  }
  
  @Test
  public void testRestService() throws Exception {
    MenuItemRef r = new MenuItemRef();
    r.setCode("1");
    r.setName("a");
   // r.setProducts((List<Product>)repository.findAll());
    Request req = new Request("warehouse", "PromotionService", "saveMenuItemRef");
    req.addParam("menuItemRef", r);
    Response res = restService.dispatch(req);
    System.out.println(JSONSerializer.INSTANCE.toString(res));
//    assertEquals("a", r.getName());
//    r = service.getMenuItemRefByCode("1");
//    r.setName("b");
//    r.getProducts().remove(0);
//    System.out.println("........................"+r.getId());
//    Request req1 = new Request("warehouse", "PromotionService", "saveMenuItemRef");
//    req1.addParam("menuItemRef", r);
//    Response res1 = restService.dispatch(req1);
//    System.out.println(JSONSerializer.INSTANCE.toString(res1));
    r = service.getMenuItemRefByCode("1");
    r.setProducts((List<Product>)repository.findAll());
    r.setName("b");
    System.out.println("........................"+r.getId());
    Request req2 = new Request("warehouse", "PromotionService", "saveMenuItemRef");
    req2.addParam("menuItemRef", r);
    Response res2 = restService.dispatch(req2);
    System.out.println(JSONSerializer.INSTANCE.toString(res2));
    
    
  }
}
