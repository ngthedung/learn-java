package com.hkt.module.partner.supplier.repository;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.partner.AbstractUnitTest;
import com.hkt.module.partner.supplier.entity.SuppliedProduct;

@Transactional
public class SuppliedProductRepositoryUnitTest extends AbstractUnitTest {
  @Autowired
  SuppliedProductRepository repository;
  
  @Test
  public void testCrud() {
    SuppliedProduct emp = repository.save(create("muoiot"));
    assertEquals(emp, repository.findOne(emp.getId()));
    repository.delete(emp);
    assertEquals(0, repository.count());
  }
  
  @Test
  public void testSearch() throws IOException {
    for (int i = 0; i < 5; i++) {
      repository.save(create("muoiot"+i));
    }
    SuppliedProduct suppliedProduct = repository.getByCode("muoiot1");
    Assert.assertNotNull(suppliedProduct);
    assertEquals(5, repository.findBySupplierId(1l).size());
    repository.deleteBySupplierId(suppliedProduct.getSupplierId());
    assertEquals(0, repository.findBySupplierId(1l).size());
  }
  
  public SuppliedProduct create(String code) {
    SuppliedProduct emp = new SuppliedProduct();
    emp.setLastPrice(10000);
    emp.setSupplierId(1l);
    emp.setName(code);
    emp.setCode(code);
    emp.setNote(code);
    return emp;
  }
}
