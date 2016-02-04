package com.hkt.module.warehouse.util;

import java.util.List;

import com.hkt.module.warehouse.entity.IdentityProduct;
import com.hkt.module.warehouse.entity.Warehouse;
import com.hkt.util.IOUtil;
import com.hkt.util.json.JSONReader;

public class WarehouseScenario {
  
  private List<IdentityProduct> identityProducts;
  
  private List<Warehouse> warehouses;

  
  public List<IdentityProduct> getIdentityProducts() {
    return identityProducts;
  }

  public void setIdentityProducts(List<IdentityProduct> identityProducts) {
    this.identityProducts = identityProducts;
  }

  public List<Warehouse> getWarehouses() { return warehouses; }
  
  public void setWarehouses(List<Warehouse> warehouses) { this.warehouses = warehouses; }


  static public WarehouseScenario load(String res) throws Exception {
//    JSONReader reader = new JSONReader(IOUtil.loadRes(res));
//    WarehouseScenario scenario = reader.read(WarehouseScenario.class);
    return null;
  }
  
  static public WarehouseScenario loadTest() throws Exception {
    return load("classpath:scenario/warehouse.json");
  }
}
