package com.hkt.module.warehouse;

import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductAttribute;
import com.hkt.module.product.entity.ProductTag;
import com.hkt.module.warehouse.entity.Warehouse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
    "classpath:META-INF/spring/module.core.service.xml",
    "classpath:META-INF/spring/module.account.service.xml",
    "classpath:META-INF/spring/module.cms.service.xml",
    "classpath:META-INF/spring/module.warehouse.service.xml" })
@TransactionConfiguration(defaultRollback = true)
abstract public class AbstractUnitTest {
  
  public Warehouse createWarehouse(String warehouseId, String ownerId, String name, String location) {
    Warehouse warehouse = new Warehouse();
    warehouse.setWarehouseId(warehouseId);
    warehouse.setOwnerId(ownerId);
    warehouse.setName(name);
    warehouse.setAddress(location);
    warehouse.setRecycleBin(false);
    return warehouse;
  }
  

  public Product createProduct(String code, String name, String maker, List<ProductTag> productTags) {
    Product product = new Product();
    product.setCode(code);
    product.setName(name);
    product.setMaker(maker);
    product.setProductTags(productTags);
    product.setRecycleBin(false);
    ProductAttribute productAttribute = new ProductAttribute("a", "b");
    product.add(productAttribute);
    return product;
  }
  
  
}
