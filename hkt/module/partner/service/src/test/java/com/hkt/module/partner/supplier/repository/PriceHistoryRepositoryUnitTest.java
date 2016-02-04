package com.hkt.module.partner.supplier.repository;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.partner.AbstractUnitTest;
import com.hkt.module.partner.supplier.entity.PriceHistory;

@Transactional
public class PriceHistoryRepositoryUnitTest extends AbstractUnitTest {
  @Autowired
  PriceHistoryRepository repository;
  
  @Test
  public void testCrud() {
    PriceHistory emp = repository.save(create());
    assertEquals(emp, repository.findOne(emp.getId()));
    repository.delete(emp);
    assertEquals(0, repository.count());
  }
  
  @Test
  public void testSearch() throws IOException {
    for (int i = 0; i < 5; i++) {
      repository.save(create());
    }

    assertEquals(5, repository.findBySuppliedProductId(1l).size());
    repository.deleteBySuppliedProductId(1l);
    assertEquals(0, repository.findBySuppliedProductId(1l).size());
  }
  
  public PriceHistory create() {
    PriceHistory emp = new PriceHistory();
    emp.setSupplierLoginId("tranAnh");
    emp.setPrice(10000);
    emp.setSuppliedProductId(1l);;
    emp.setNote("thay doi lan 1");
    return emp;
  }
}
