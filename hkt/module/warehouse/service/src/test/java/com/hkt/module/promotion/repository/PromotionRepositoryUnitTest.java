package com.hkt.module.promotion.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.Account.Type;
import com.hkt.module.account.repository.AccountRepository;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.repository.ProductRepository;
import com.hkt.module.promotion.entity.CustomerTarget;
import com.hkt.module.promotion.entity.CustomerTarget.CustomerTargetType;
import com.hkt.module.promotion.entity.ProductTarget;
import com.hkt.module.promotion.entity.ProductTarget.ProductTargetType;
import com.hkt.module.promotion.entity.Promotion.Status;
import com.hkt.module.promotion.entity.PromotionDetail;
import com.hkt.module.warehouse.AbstractUnitTest;

@Transactional
public class PromotionRepositoryUnitTest extends AbstractUnitTest {
  
  @Autowired
  PromotionRepository repository;
  
  @Autowired
  ProductRepository productRepository;
  
  @Autowired
  AccountRepository accountRepository;
  
  
  @Before
  public void createData(){
    Product product = new Product();
    product.setCode("iphone5s");
    product.setName("iphone5s");
    product.setMaker("hkt");
    product.setRecycleBin(false);
    productRepository.save(product);
    
    Account account2 = new Account();
    account2.setLoginId("hkt");
    account2.setPassword("abc");
    account2.setEmail("hkt@email.com");
    account2.setType(Type.ORGANIZATION);
    account2.setRecycleBin(false);
    accountRepository.save(account2);
  }
  
  @After
  public void deleteData(){
    productRepository.deleteAll();
    accountRepository.deleteAll();
  }
  
  @Test
  public void testCrud() {
    PromotionDetail promotion = repository.save(create("KhuyenMai", "iphone5s", ProductTargetType.ByProduct));
    assertNotNull(promotion);
    List<String> list = new ArrayList<String>();
    list.add("hkt/customer");
    list.add("b");
    System.out.println(repository.findByCustomerTargetGroup(list, new Date(), 10000d,"a"));
    assertEquals(1, repository.findByCustomerGroup(list, new Date(), 10000d,"a").size());
    repository.deleteTestProductTarget("KhuyenMai");
    repository.deleteTestCustomerTarget("KhuyenMai");
    repository.deleteTest("KhuyenMai");
    assertEquals(0, repository.count());
  }
  
  @Test
  public void testSearch() {
    for (int i = 0; i < 5; i++) {
      repository.save(create("KhuyenMai"+ i, "iphone5s", ProductTargetType.ByProduct));
    }
    for (int i = 0; i < 5; i++) {
      repository.save(create("KhuyenMai"+ i, "hang-hoa", ProductTargetType.ByProductTag));
    }
    assertEquals(10, repository.findByOrganizationLoginId("hkt").size());
    List<String> a = new ArrayList<String>();
    a.add("hang-hoa");
    a.add("b");
    assertEquals(5, repository.findByProduct(a,new Date(),10000d,"a").size());
    List<String> b = new ArrayList<String>();
    b.add("hkt/customer");
    b.add("b");
    assertEquals(5, repository.findByCustomerGroupAndProduct(b, a, new Date(),10000d,"a").size());
    
    List<String> groupPaths = new ArrayList<String>();
    groupPaths.add("hkt/customer");
    assertEquals(10, repository.findByCustomerTargetGroup(groupPaths, new Date(),10000d,"a").size());
    
  }
  
  public PromotionDetail create(String name, String productIdentifierId, ProductTargetType productTargetType) {
    PromotionDetail promotion = new PromotionDetail();
    promotion.setName(name);
    promotion.setOrganizationLoginId("hkt");
    
    ProductTarget pTarget = new ProductTarget() ;
    pTarget.setProductIdentifierId(productIdentifierId) ;
    pTarget.setProductTargetType(productTargetType) ;
    pTarget.setRecycleBin(false);
    List<ProductTarget> productTargets = new ArrayList<ProductTarget>();
    productTargets.add(pTarget);
    promotion.setProductTargets(productTargets) ;
    
    CustomerTarget customerTarget = new CustomerTarget();
    customerTarget.setCustomerGroup("hkt/customer");
    customerTarget.setCustomerTargetType(CustomerTargetType.ByGroup);
    customerTarget.setRecycleBin(false);
    List<CustomerTarget> customerTargets = new ArrayList<CustomerTarget>();
    customerTargets.add(customerTarget);
    promotion.setCustomerTargets(customerTargets);
    
    promotion.setFromDate(new Date());
    promotion.setToDate(null);
    promotion.setStoreCode("a");
    promotion.setStatus(Status.Planned);
    promotion.setMaxUsePerCustomer(100);
    promotion.setDiscountRate(5);
    promotion.setDiscount(1000);
    promotion.setAppliedToMinExpense(0);
    promotion.setMaxExpense(200000);
    promotion.setCurrencyUnit("VND");
    promotion.setDescription("Giam gia");
    promotion.setRecycleBin(false);
    return promotion;
  }
}
