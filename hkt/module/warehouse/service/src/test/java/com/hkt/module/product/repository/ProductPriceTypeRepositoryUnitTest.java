package com.hkt.module.product.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.Account.Type;
import com.hkt.module.account.repository.AccountRepository;
import com.hkt.module.product.entity.ProductPriceType;
import com.hkt.module.product.repository.ProductPriceTypeRepository;
import com.hkt.module.warehouse.AbstractUnitTest;

@Transactional
public class ProductPriceTypeRepositoryUnitTest extends AbstractUnitTest {
  @Autowired
  private ProductPriceTypeRepository repo;

  @Autowired
  private AccountRepository accountRepository;

  @Before
  public void createContext() {
    Account account2 = new Account();
    account2.setLoginId("hkt");
    account2.setPassword("abc");
    account2.setEmail("hkt@email.com");
    account2.setType(Type.ORGANIZATION);
    accountRepository.save(account2);
  }

  @Test
  public void testCRUD() {
    ProductPriceType p = createInstance("test");
    p = repo.save(p);
    assertNotNull(p);
    assertEquals(p, repo.getByType("test"));
    assertNotNull(repo.findByProductPriceTypeRecycleBin(false));
    assertEquals(p, repo.searchByType("test").get(0));
    repo.deleteTest("HKT");
    assertEquals(0, repo.count());
  }

  static ProductPriceType createInstance(String type) {
    ProductPriceType instance = new ProductPriceType();
    instance.setName("HKT");
    instance.setType(type);
    instance.setOrganizationLoginId("hkt");
    instance.setDescription(null);
    instance.setRecycleBin(false);
    return instance;
  }
}