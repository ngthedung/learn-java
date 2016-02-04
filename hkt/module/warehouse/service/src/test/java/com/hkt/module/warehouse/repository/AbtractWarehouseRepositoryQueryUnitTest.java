package com.hkt.module.warehouse.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hkt.module.product.repository.ProductRepository;
import com.hkt.module.warehouse.AbstractUnitTest;
import com.hkt.module.warehouse.entity.Warehouse;

public class AbtractWarehouseRepositoryQueryUnitTest extends AbstractUnitTest {
  @Autowired
  WarehouseRepository    warehouseRepository;

  @Autowired
  InventoryRepository  inInventoryRepository;

  @Autowired
  ProductRepository      productRepository;

  @Test
  public void test() {
  }
  
  Warehouse CreateWarehouse(AccountConfig accountConfig, String warehouseId, String name, String address) {
    Warehouse warehouse = new Warehouse();
    warehouse.setWarehouseId(warehouseId);
    warehouse.setName(name);
    warehouse.setOwnerId(accountConfig.loginId);
    warehouse.setAddress(address);
    return warehouseRepository.save(warehouse);
  }

  static public class AccountConfig {
    long   accountId;
    String loginId;
    String name;

    AccountConfig(long accountId, String loginId, String name, double salary) {
      this.accountId = accountId;
      this.loginId = loginId;
      this.name = name;
    }
  }

  static public class ProductConfig {
    String name;
    String pcode;
    String maker;

    ProductConfig(String name, String pcode, String maker) {
      this.name = name;
      this.pcode = pcode;
      this.maker = maker;
    }
  }

}
