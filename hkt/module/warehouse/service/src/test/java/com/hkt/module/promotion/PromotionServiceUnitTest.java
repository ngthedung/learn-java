package com.hkt.module.promotion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hkt.module.account.AccountService;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.account.util.AccountScenario;
import com.hkt.module.cms.CMSService;
import com.hkt.module.core.ServiceCallback;
import com.hkt.module.product.ProductService;
import com.hkt.module.product.util.ProductScenario;
import com.hkt.module.promotion.entity.Coupon;
import com.hkt.module.promotion.entity.Promotion;
import com.hkt.module.promotion.entity.PromotionDetail;
import com.hkt.module.promotion.entity.PromotionUsed;
import com.hkt.module.promotion.entity.TimeOption;
import com.hkt.module.promotion.entity.TimeOptionDetail;
import com.hkt.module.promotion.entity.Weekly;
import com.hkt.module.promotion.util.CouponScenario;
import com.hkt.module.promotion.util.MenuScenario;
import com.hkt.module.promotion.util.PromotionScenario;
import com.hkt.module.warehouse.AbstractUnitTest;
import com.hkt.util.json.JSONSerializer;

public class PromotionServiceUnitTest extends AbstractUnitTest {
  static ServiceCallback<PromotionService> FAIL_CALLBACK = new ServiceCallback<PromotionService>() {
    public void callback(PromotionService service) {
      throw new RuntimeException("Fail. Expect a rollback if method annotate with the Transactionnal");
    }
  };

  @Autowired
  PromotionService service;

  @Autowired
  ProductService productService;

  private PromotionScenario scenario;
  private CouponScenario couponScenario;
  private MenuScenario menuScenario;

  @Autowired
  private AccountService accountService;

  @Autowired
  private CMSService cmsService;

  @Before
  public void settup() throws Exception {
    accountService.createScenario(AccountScenario.loadTest());
    productService.createScenario(ProductScenario.loadTest());
    scenario = PromotionScenario.loadTest();
    service.createScenarioPromotion(scenario);
    couponScenario = CouponScenario.loadTest();
    service.createScenarioCoupon(couponScenario);
//    menuScenario = MenuScenario.loadTest();
//    service.createScenarioMenu(menuScenario);
    accountService.createMembership("tranAnh", "hkt/partner/customer", Capability.ADMIN);
  }

  @After
  public void testDown() throws Exception {
    productService.deleteAll();
    service.deleteAll();
    accountService.deleteAll();
    cmsService.deleteAll();
    
  }

  @Test
  public void testSerialization() throws IOException {
    assertTrue(scenario.getPromotions().size() > 0);
    System.out.println(JSONSerializer.INSTANCE.toString(scenario));
    System.out.println("<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    assertTrue(couponScenario.getCoupons().size() > 0);
    System.out.println(JSONSerializer.INSTANCE.toString(couponScenario));
    System.out.println("<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    //assertTrue(menuScenario.getMenus().size() > 0);
    System.out.println(JSONSerializer.INSTANCE.toString(menuScenario));
  }

  @Test
  public void testPromotion() {

    System.out.println(service.findPromotionProduct("hkt/employees","","","iphone5s",200000d,""));
    assertEquals(1, service.findPromotionProduct("hkt/employees","","","iphone5s",20000d,"").size());
    assertEquals(1, service.findPromotion("hkt/employees","","hkt/partner/customer",20000d,"").size());
    assertEquals(6, service.getPromotions().size());

    PromotionDetail promotion = service.getPromotions().get(0);
    try {
      service.deletePromotionCallBack(promotion, FAIL_CALLBACK);
    } catch (Exception e) {
      System.out.println("Fail callback exception: " + e.getMessage());
    }
  }

  @Test
  public void testMenu() {
//    List<Menu> menus = service.getAllMenus();
//    assertEquals(3, menus.size());
//    List<MenuItemRef> itemRefs = service.getAllMenuItemRef();
//    assertEquals(3, itemRefs.size());
  }

  @Test
  public void testCoupon() {
    List<Coupon> coupons = service.getAllCoupons();
    assertEquals(4, coupons.size());
  }

  @Test
  public void testPromotionUsed() {
    Promotion promotion = service.getPromotions().get(0);
    List<PromotionUsed> promotionUseds = service.findPromotionUsedByPromotionId(promotion.getId());
    assertEquals(1, promotionUseds.size());

    PromotionUsed promotionUsed = promotionUseds.get(0);
    try {
      service.deletePromotionUsedCallBack(promotionUsed, FAIL_CALLBACK);
    } catch (Exception e) {
      System.out.println("Fail callback exception: " + e.getMessage());
    }
  }

  @Test
  public void testTimeOptionDetail(){
    TimeOption timeOption = new TimeOption();
    timeOption.setCode("a");
    TimeOptionDetail timeOptionDetail = new TimeOptionDetail();
    timeOptionDetail.setTimeOption(timeOption);
    timeOption.setDailyChoose(true);
    timeOption.setWeeklyChoose(true);
    Weekly weekly = new Weekly();
    weekly.setCode("b");
    weekly.add(Integer.valueOf(6));
    weekly.setStep(1);
    timeOptionDetail.setWeekly(weekly);
    timeOptionDetail = service.saveTimeOptionDetail(timeOptionDetail);
    List<Weekly> weeklies = service.getWeeklyByTimeOp(timeOptionDetail.getTimeOption().getId());
    System.out.println(weeklies.get(0).dayOfWeeks().get(0));
    
  }
}
