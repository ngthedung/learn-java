package com.hkt.module.partner.supplier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hkt.module.account.AccountService;
import com.hkt.module.account.util.AccountScenario;
import com.hkt.module.partner.AbstractUnitTest;
import com.hkt.module.partner.supplier.entity.SuppliedProduct;
import com.hkt.module.partner.supplier.entity.Supplier;
import com.hkt.module.partner.supplier.util.SupplierScenario;
import com.hkt.util.json.JSONSerializer;

public class SupplerServiceUnitTest  extends AbstractUnitTest {

  @Autowired
  SupplierService service;
  
  private SupplierScenario scenarioSupplier;
  
  @Autowired
  private AccountService  accountService ;
  
  @Before
  public void setup() throws Exception {    
    accountService.createScenario(AccountScenario.loadTest()) ;
    scenarioSupplier = SupplierScenario.loadTest();
    service.createScenarioSupplier(scenarioSupplier);
  }
  
  @After
  public void tearDown() throws Exception {
    service.deleteAll();
    accountService.deleteAll();
  }
  
  @Test
  public void testSerialization() throws Exception {    
    System.out.println(JSONSerializer.INSTANCE.toString(scenarioSupplier));
    assertTrue(scenarioSupplier.getSuppliers().size() > 0);
  }
//  
//  @Test
//  public void testPartner() {
//    assertEquals(2, service.searchSupplier(null).getData().size());
//    Supplier supplier = service.searchSupplier(null).getData().get(0);
//    assertNotNull(supplier);
//    long supplierId = supplier.getId();
//    
//    assertEquals(2, service.findSuppliedProductBySupplierId(supplierId).size());
//    
//    SuppliedProduct suppliedProduct =  service.findSuppliedProductBySupplierId(supplierId).get(0);
//    long suppliedProductId = suppliedProduct.getId();
//    assertEquals(3, service.findPriceHistoryBySuppliedProductId(suppliedProductId).size());
//    
//    service.deleteSupplier(supplier);
//    assertEquals(0, service.findPriceHistoryBySuppliedProductId(suppliedProductId).size());
//    assertEquals(0, service.findSuppliedProductBySupplierId(supplierId).size());
//  }
}
