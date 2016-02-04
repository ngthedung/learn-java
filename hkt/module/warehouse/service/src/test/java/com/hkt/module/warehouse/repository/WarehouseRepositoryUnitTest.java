package com.hkt.module.warehouse.repository;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.warehouse.AbstractUnitTest;
import com.hkt.module.warehouse.entity.Warehouse;
import com.hkt.module.warehouse.entity.WarehouseInventory;

@Transactional
public class WarehouseRepositoryUnitTest extends AbstractUnitTest {
  @Autowired
  WarehouseRepository repository;
  
  @Autowired
  InventoryRepository inRepository;

  @Test
  public void testCrud() {
    Warehouse warehouse = repository.save(createWarehouse("hkt-kho-01", "hkt", "kho 1", "Ha Noi"));
    assertEquals(warehouse, repository.findOne(warehouse.getId()));
    assertEquals(warehouse, repository.findByWarehouseRecycleBin(false).get(0));
    repository.delete(warehouse);
    assertEquals(0, repository.count());
  }

  @Test
  public void testFind() {
    for (int i = 0; i < 5; i++) {
      String warehouseId = "hkt-kho" + i;
      repository.save(createWarehouse(warehouseId, "hkt", "kho " + i, "Ha Noi"));
    }
    assertEquals(5, repository.findByOwnerId("hkt").size());
  }
  
  @Test
  public void testInventory() {
    WarehouseInventory a = new WarehouseInventory();
    a.setProductCode("a");
    a.setStartDate(new Date());
    inRepository.save(a);
    WarehouseInventory a1 = new WarehouseInventory();
    a1.setProductCode("a1");
    a1.setStartDate(new Date());
    inRepository.save(a1);
    WarehouseInventory a2 = new WarehouseInventory();
    a2.setProductCode("a2");
    a2.setStartDate(new Date());
    inRepository.save(a2);
    List<String> li = new ArrayList<String>();
    li.add("a");
    li.add("a1");
    
    assertEquals(3, inRepository.count());
    Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 01);
		Calendar c1 = Calendar.getInstance();
		c1.set(Calendar.HOUR_OF_DAY, 23);
		c1.set(Calendar.MINUTE, 59);
    System.out.println(inRepository.findWarehouseInventoryNotUsed(li, c.getTime(), c1.getTime()));
  }

}
