package com.hkt.module.warehouse.repository;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.warehouse.AbstractUnitTest;
import com.hkt.module.warehouse.entity.IdentityProduct;
import com.hkt.module.warehouse.entity.WarehouseInventory;
import com.hkt.module.warehouse.entity.IdentityProduct.ExportType;
import com.hkt.module.warehouse.entity.IdentityProduct.ImportType;
import com.hkt.module.warehouse.entity.IdentityProductAttribute;

@Transactional
public class IdentityProductRepositoryUnitTest extends AbstractUnitTest {
  @Autowired
  IdentityProductRepository repository;
  
  @Autowired
  InventoryRepository inventoryRepository;

  @Test
  public void testCrud() {
    IdentityProduct identityProduct = repository.save(createIdentityProduct());
    System.out.println(repository.getPriceReport("P001"));
    assertEquals(identityProduct, repository.getIdentityProductById(identityProduct.getId()));
    assertEquals(1, repository.count());
    assertEquals(2, identityProduct.getIdentityProductAttributes().size());
    assertEquals(identityProduct, repository.findIdentityProductByRecycleBin(false).get(0));
    System.out.println(identityProduct.getImportDate());
   // identityProduct.setImportDate(null);
   long id = identityProduct.getId();
    IdentityProduct i = repository.getIdentityProductById(id);
   // System.out.println(i.getImportDate());
    i.setImportDate(null);
    
    IdentityProduct i1 = repository.getIdentityProductById(id);
    i1.setImportDate(new Date());
    //i.setImportDate(null);
    System.out.println(i.getImportDate());
   // repository.deleteIdentityProductAttributeByInvoiceCode("AHF8343");
   // repository.deleteByInvoiceCode("AHF8343");
    //assertEquals(0, repository.count());
  }
  
  @Test
  public void testCrud1() {
    WarehouseInventory i = new WarehouseInventory();
    i.setIncreaseQuantity(0);
    i.setIncreaseValue(0);
    i.setProductCode("a");
    i.setProfitQuantity(0);
    i.setProfitValue(0);
    i.setReductionQuantity(0);
    i.setReductionValue(0);
    i.setStartDate(new Date());
    inventoryRepository.save(i);
    assertEquals(1, inventoryRepository.count());
  }

  @Test
  public void testFind() {
    for (int i = 0; i < 5; i++) {
      repository.save(createIdentityProduct());
    }
    assertEquals(5, repository.getByInvoiceCode("AHF8343").size());
    assertEquals(5, repository.getByImportType(ImportType.Import).size());
    assertEquals(5, repository.getByImportTypeAndProductCode(ImportType.Import, ExportType.NotExport, "P001").size());
    assertEquals(5, repository.getByImportTypeAndWarehouse(ImportType.Import, "Kho1", ExportType.NotExport).size());
    assertEquals(5, repository.getByImportTypeAndProductAndWarehouse(ImportType.Import, "P001", "Kho1", ExportType.NotExport).size());
    assertEquals(5, repository.getByExportType(ExportType.NotExport).size());
    assertEquals(5, repository.getByProductCode("P001").size());
    assertEquals(5, repository.getByInvoiceExportCode("EXP8343").size());
    assertEquals(5, repository.findByProductCode("P001").size());
    assertEquals(5, repository.findByProductCodeAndExportType("P001", ExportType.NotExport).size());
    assertEquals(5, repository.getByInvoiceItemIdExport(2).size());
    assertEquals(5, repository.getByInvoiceItemIdImport(1).size());
    assertEquals(5, repository.getByShipmentImportCode("SIC001").size());
    assertEquals(5, repository.getByShipmentExportCode("SEC001").size());
  }
  
  private IdentityProduct createIdentityProduct(){
    IdentityProduct identityProduct = new IdentityProduct();
    identityProduct.setInvoiceCode("AHF8343");
    identityProduct.setImportDate(new Date());
    identityProduct.setImportType(ImportType.Import);
    identityProduct.setPrice(5000);
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

}
