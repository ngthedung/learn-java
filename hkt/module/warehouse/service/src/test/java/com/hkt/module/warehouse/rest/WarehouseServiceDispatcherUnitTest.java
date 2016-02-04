package com.hkt.module.warehouse.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

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
import com.hkt.module.product.util.ProductScenario;
import com.hkt.module.warehouse.AbstractUnitTest;
import com.hkt.module.warehouse.WarehouseService;
import com.hkt.module.warehouse.entity.WarehouseInventory;
import com.hkt.module.warehouse.entity.Warehouse;
import com.hkt.module.warehouse.util.WarehouseScenario;
import com.hkt.util.json.JSONSerializer;

public class WarehouseServiceDispatcherUnitTest extends AbstractUnitTest {
  @Autowired
  RestService restService;

  @Autowired
  WarehouseService service;
  
  private AccountScenario accountScenario;
  
  @Autowired
  private AccountService accountService;
  
  private ProductScenario productScenario;
  
  @Autowired
  private ProductService productService;
  
  @Autowired
  private CMSService cmsService;

  @Before
  public void setup() throws Exception {
    accountScenario = AccountScenario.loadTest();
    accountService.createScenario(accountScenario);
    
    productScenario = ProductScenario.loadTest();
    productService.createScenario(productScenario);
    
    WarehouseScenario scenario = WarehouseScenario.loadTest();
    service.createScenario(scenario);
  }

  @After
  public void tearDown() throws Exception{
    service.deleteAll();
    productService.deleteAll();
    accountService.deleteAll();
    cmsService.deleteAll();
  }

  @Test
  public void testRestService() throws Exception {
    WarehouseInventory i = new WarehouseInventory();
    i.setIncreaseQuantity(0);
    i.setIncreaseValue(0);
    i.setProductCode("a");
    i.setProfitQuantity(0);
    i.setProfitValue(0);
    i.setReductionQuantity(0);
    i.setReductionValue(0);
    i.setStartDate(new Date());
    Request req = new Request("warehouse", "WarehouseService", "getWarehouseByWarehouseId");
    req.addParam("warehouseId", "hkt-kho-01");
    Response res = restService.dispatch(req);
    System.out.println(JSONSerializer.INSTANCE.toString(res));
    Warehouse warehouse = res.getDataAs(Warehouse.class);
    assertNotNull(warehouse);
    assertEquals("hkt-kho-01", warehouse.getWarehouseId());
  }
}