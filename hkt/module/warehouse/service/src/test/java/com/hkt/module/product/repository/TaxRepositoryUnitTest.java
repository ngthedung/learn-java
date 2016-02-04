package com.hkt.module.product.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.product.entity.Tax;
import com.hkt.module.product.repository.TaxRepository;
import com.hkt.module.warehouse.AbstractUnitTest;

@Transactional
public class TaxRepositoryUnitTest extends AbstractUnitTest {
  @Autowired
  private TaxRepository repo;
  
  @Test
  public void testCRUD() {
    Tax tax = createTax("VAT", 10);
    tax = repo.save(tax);
    assertEquals(1, repo.count());
    
    tax.setPercent(12);
    tax = repo.save(tax);
    assertEquals(1, repo.findByName("v").size());
    
    assertEquals(tax, repo.findByTaxRecycleBin(false).get(0));
    repo.delete(tax);
    assertEquals(0, repo.count());
  }
  
  public Tax createTax(String name, double percent) {
    Tax tax = new Tax();
    tax.setName(name);
    tax.setPercent(percent);
    tax.setRecycleBin(false);
    return tax;
  }
}
