package com.hkt.module.promotion.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.promotion.entity.PromotionGiveUsed;
import com.hkt.module.promotion.entity.PromotionGiveaways;
import com.hkt.module.promotion.entity.PromotionUsed;
import com.hkt.module.warehouse.AbstractUnitTest;

@Transactional
public class PromotionUsedRepositoryUnitTest extends AbstractUnitTest {
  
  @Autowired
  PromotionUsedRepository repository;
  
  @Autowired
  PromotionGiveUsedRepository promotionGiveUsedRepository;
  
  @Autowired
  PromotionGiveawaysRepository promotionGiveawaysRepository;
  
  @Test
  public void testCrud() {
    PromotionUsed promotionUsed = create_PromotionUsed(1);
    promotionUsed = repository.save(promotionUsed);
    assertNotNull(promotionUsed);
    promotionUsed.setCustomerLoginId("long");
    assertEquals(promotionUsed, repository.save(promotionUsed));
    assertEquals(1, repository.findPromotionUsedByRecycleBin(false).size());
    repository.deleteByPromotionId(1);
    assertEquals(0, repository.count());
  }
  
  @Test
  public void testSearch() {
    for (int i = 0; i < 5; i++) {
      repository.save(create_PromotionUsed(i));
    }
    assertEquals(1, repository.findByPromotionId(0).size());
  }
  
  public PromotionUsed create_PromotionUsed(long promotionId) {
    PromotionUsed p = new PromotionUsed();
    p.setPromotionId(promotionId);
    p.setCustomerLoginId("phananh");
    p.setInvoiceId(1);
    p.setUsedDate(new Date());
    p.setExpense(100);
    p.setSaving(100);
    p.setRecycleBin(false);
    return p;
  }
  
  @Test
  public void testPromotionGiveUsed() {
    PromotionGiveUsed p = new PromotionGiveUsed();
    p.setInvoiceId(1);
    p.setInvoiceItemCode("2");
    p.setUsedDate(new Date());
    p.setProductName("long");
    p.setRecycleBin(false);
    p = promotionGiveUsedRepository.save(p);
    assertNotNull(p);
    assertEquals(1, promotionGiveUsedRepository.getPromotionGiveUsedsByInvoiceId(1).size());
    assertEquals(p, promotionGiveUsedRepository.getPromotionByInvoiceItemCode("2"));
    assertEquals(p, promotionGiveUsedRepository.findPromotionGiveUsedByRecycleBin(false).get(0));
    promotionGiveUsedRepository.deleteByInvoiceId(1);
    assertEquals(0, promotionGiveUsedRepository.count());
  }
  
  
  @Test
  public void testPromotionGiveaways() {
    PromotionGiveaways promotionGiveaways = new PromotionGiveaways();
    promotionGiveaways.setCode("a");
    promotionGiveaways.setName("a");
    promotionGiveaways.setFromDate(new Date());
    promotionGiveaways.setProductCode("b");
    promotionGiveaways.setQuantityGive(1);
    promotionGiveaways.setQuantityBuy(5);
    promotionGiveaways.setRecycleBin(false);
    promotionGiveaways = promotionGiveawaysRepository.save(promotionGiveaways);
    
    assertNotNull(promotionGiveaways);
    assertEquals(promotionGiveaways, promotionGiveawaysRepository.getPromotionGiveawaysByProductCode("b", new Date()));
    assertEquals(promotionGiveaways, promotionGiveawaysRepository.findPromotionGiveawaysByRecycleBin(false).get(0));
    promotionGiveawaysRepository.deleteTest("a");
    assertEquals(0, promotionGiveawaysRepository.count());
  }
}
