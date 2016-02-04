package com.hkt.module.warehouse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hkt.module.account.AccountService;
import com.hkt.module.account.util.AccountScenario;
import com.hkt.module.cms.CMSService;
import com.hkt.module.core.ServiceCallback;
import com.hkt.module.product.ProductService;
import com.hkt.module.product.repository.ProductRepository;
import com.hkt.module.product.util.ProductScenario;
import com.hkt.module.warehouse.entity.IdentityProduct;
import com.hkt.module.warehouse.entity.IdentityProductAttribute;
import com.hkt.module.warehouse.entity.WarehouseInventory;
import com.hkt.module.warehouse.entity.Warehouse;
import com.hkt.module.warehouse.entity.IdentityProduct.ExportType;
import com.hkt.module.warehouse.entity.IdentityProduct.ImportType;
import com.hkt.module.warehouse.util.WarehouseScenario;

public class WarehouseServiceUnitTest extends AbstractUnitTest {
  static ServiceCallback<WarehouseService> FAIL_CALLBACK = new ServiceCallback<WarehouseService>() {
    public void callback(WarehouseService service) {
      throw new RuntimeException("Fail. Expect a rollback if method annotate with the Transactionnal");
    }
  };
  
//  static {
//    System.setProperty("hibernate.show_sql", "true");
//    System.setProperty("hibernate.format_sql", "true");
//  }
//  
  @Autowired
  private WarehouseService service;

  private AccountScenario accountScenario;
  
  @Autowired
  private AccountService accountService;
  
  private WarehouseScenario scenario;
  
  @Autowired
  private ProductRepository productRepository;
  
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
    
    scenario = WarehouseScenario.loadTest();
    service.createScenario(scenario);
  }

  @After
  public void testDown() throws Exception{
    service.deleteAll();
    productService.deleteAll();
    accountService.deleteAll();
    cmsService.deleteAll();
  }
  
  @Test
  public void testSerialization() {
    assertTrue(scenario.getWarehouses().size() > 0);
  }
  private IdentityProduct createIdentityProduct(){
    IdentityProduct identityProduct = new IdentityProduct();
    identityProduct.setInvoiceCode("AHF8343");
    identityProduct.setImportDate(new Date());
    identityProduct.setImportType(ImportType.Import);
    identityProduct.setInvoiceExportCode("EXP8343");
    identityProduct.setExportDate(new Date());
    identityProduct.setExportType(ExportType.NotExport);
    identityProduct.setProductCode("P001");
    identityProduct.setWarehouseCode("Kho1");
    identityProduct.setInvoiceItemIdImport(1);
    identityProduct.setInvoiceItemIdExport(2);
    identityProduct.setShipmentImportCode("SIC001");
    identityProduct.setShipmentExportCode("SEC001");
    
    List<IdentityProductAttribute> identityProductAttributes = new ArrayList<IdentityProductAttribute>();
    IdentityProductAttribute identityProductAttribute = new IdentityProductAttribute();
    identityProductAttribute.setName("warehouse");
    identityProductAttribute.setValue("hkt-warehouse");
    identityProductAttributes.add(identityProductAttribute);
    IdentityProductAttribute identityProductAttribute1 = new IdentityProductAttribute();
    identityProductAttribute1.setName("warehouse");
    identityProductAttribute1.setValue("hkt-warehouse-335");
    identityProductAttributes.add(identityProductAttribute1);
    
    identityProduct.setIdentityProductAttributes(identityProductAttributes);
    return identityProduct;
  }
  @Test
  public void testCrud() {
    IdentityProduct identityProduct = service.saveIdentityProduct(createIdentityProduct());
    assertEquals(identityProduct, service.getIdentityProductById(identityProduct.getId()));
  
   
   // identityProduct.setImportDate(null);
   long id = identityProduct.getId();
    IdentityProduct i = service.getIdentityProductById(id);
   // System.out.println(i.getImportDate());
    i.setImportDate(null);
    System.out.println(i.getImportDate());
    
    IdentityProduct i1 = service.getIdentityProductById(id);
    //i1.setImportDate(new Date());
    //i.setImportDate(null);
    System.out.println(i1.getImportDate());
   // repository.deleteIdentityProductAttributeByInvoiceCode("AHF8343");
   // repository.deleteByInvoiceCode("AHF8343");
    //assertEquals(0, repository.count());
  }
  
  @Test
  public void testWarehouse() {
    Warehouse warehouse = service.getWarehouseByWarehouseId("hkt-kho-01");
    assertNotNull(warehouse);
    try {
      service.deleteWarehouseCallBack(warehouse, FAIL_CALLBACK);
    } catch (Throwable t) {
      System.out.println("Fail callback exception: " + t.getMessage());
    }
   
    service.deleteWarehouseByWarehouseId("hkt-kho-01");
    
    warehouse = service.getWarehouseByWarehouseId("hkt-kho-01");
    assertNull(warehouse);
  }


}
