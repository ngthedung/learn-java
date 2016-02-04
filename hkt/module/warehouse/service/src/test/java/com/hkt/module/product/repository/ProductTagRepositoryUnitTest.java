package com.hkt.module.product.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.product.entity.ProductTag;
import com.hkt.module.product.repository.ProductTagRepository;
import com.hkt.module.warehouse.AbstractUnitTest;

@Transactional
public class ProductTagRepositoryUnitTest extends AbstractUnitTest {
  @Autowired
  private ProductTagRepository repo;
  
  @Test
  public void testCRUD() {
    ProductTag p = createInstance();
    p = repo.save(p);
    p = repo.findOne(p.getId());
    assertNotNull(p);
    assertEquals(p, repo.getByTag("Hang Hoa"));
    assertEquals(1, repo.findProductTag("t").size());
    assertEquals(1, repo.findProductTag("o").size());
    assertEquals(p, repo.findByProductTagRecycleBin(false).get(0));
    repo.delete(p);
    assertEquals(0, repo.count());
  }
  
  ProductTag createInstance() {
    ProductTag instance = new ProductTag();
    instance.setTag("Hang Hoa");
    instance.setLabel("Thanh Pham");
    return instance;
  }
}