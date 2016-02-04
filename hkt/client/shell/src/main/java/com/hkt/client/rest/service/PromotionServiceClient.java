package com.hkt.client.rest.service;

import java.util.List;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hkt.client.rest.RESTClient;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.core.rest.Request;
import com.hkt.module.core.rest.Response;
import com.hkt.module.promotion.entity.Coupon;
import com.hkt.module.promotion.entity.CouponUsed;
import com.hkt.module.promotion.entity.Daily;
import com.hkt.module.promotion.entity.Menu;
import com.hkt.module.promotion.entity.Menu.Type;
import com.hkt.module.promotion.entity.MenuItem;
import com.hkt.module.promotion.entity.MenuItem.MenuItemType;
import com.hkt.module.promotion.entity.MenuItemRef;
import com.hkt.module.promotion.entity.Monthly;
import com.hkt.module.promotion.entity.PromotionDetail;
import com.hkt.module.promotion.entity.PromotionGiveUsed;
import com.hkt.module.promotion.entity.PromotionGiveaways;
import com.hkt.module.promotion.entity.PromotionUsed;
import com.hkt.module.promotion.entity.TimeOption;
import com.hkt.module.promotion.entity.TimeOptionDetail;
import com.hkt.module.promotion.entity.Weekly;
import com.hkt.module.promotion.entity.Yearly;

@Component
public class PromotionServiceClient {
  @Autowired
  private RESTClient client;

  public Coupon saveCoupon(Coupon coupon) throws Exception {
    Request req = create("saveCoupon");
    req.addParam("coupon", coupon);
    Response res = client.POST(req);
    return res.getDataAs(Coupon.class);
  }

  public boolean deleteCoupon(Coupon coupon) throws Exception {
    Request req = create("deleteCoupon");
    req.addParam("coupon", coupon);
    client.POST(req);
    return true;
  }

  public List<Coupon> getCoupons() throws Exception {
    Request req = create("getCoupons");
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<Coupon>>() {
    });
  }

  public Coupon getCouponByCode(String code) throws Exception {
    Request req = create("getCouponByCode");
    req.addParam("code", code);
    Response res = client.POST(req);
    return res.getDataAs(Coupon.class);
  }

  public List<CouponUsed> getCouponUsed() throws Exception {
    Request req = create("getCouponUsed");
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<CouponUsed>>() {
    });
  }

  public Menu saveMenu(Menu menu) throws Exception {
    Request req = create("saveMenu");
    req.addParam("menu", menu);
    Response res = client.POST(req);
    return res.getDataAs(Menu.class);
  }
  
  public Menu getMenuByCode(String code) throws Exception {
    Request req = create("getMenuByCode");
    req.addParam("code", code);
    Response res = client.POST(req);
    return res.getDataAs(Menu.class);
  }

  public boolean deleteMenu(Menu menu) throws Exception {
    Request req = create("deleteMenu");
    req.addParam("query", menu);
    client.POST(req);
    return true;
  }

  public FilterResult<Menu> searchMenu(FilterQuery query) throws Exception {
    Request req = create("searchMenu");
    req.addParam("query", query);
    Response res = client.POST(req);
    return res.getDataAsByFilter(new TypeReference<FilterResult<Menu>>() {
    });
  }

  public List<Menu> getMenuByType(Type type) throws Exception {
    Request req = create("getMenuByType");
    req.addParam("type", type);
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<Menu>>() {
    });
  }
  
  public List<MenuItem> getMenuItemByMenuItemType(MenuItemType menuItemType) throws Exception{
    Request req = create("getMenuItemByMenuItemType");
    req.addParam("menuItemType", menuItemType);
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<MenuItem>>() {
    });
  }

  public List<PromotionGiveaways> getPromotionGiveaways() throws Exception {
    Request req = create("getPromotionGiveaways");
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<PromotionGiveaways>>() {
    });
  }

  public PromotionGiveaways savePromotionGiveaways(PromotionGiveaways promotionGiveaways) throws Exception {
    Request req = create("savePromotionGiveaways");
    req.addParam("promotionGiveaways", promotionGiveaways);
    Response res = client.POST(req);
    return res.getDataAs(PromotionGiveaways.class);
  }

  public PromotionGiveaways getPromotionGiveawaysByProductCode(String productCode) throws Exception {
    Request req = create("getPromotionGiveawaysByProductCode");
    req.addParam("productCode", productCode);
    Response res = client.POST(req);
    return res.getDataAs(PromotionGiveaways.class);
  }

  public boolean deletePromotionGiveaways(PromotionGiveaways promotionGiveaways) throws Exception {
    Request req = create("deletePromotionGiveaways");
    req.addParam("promotionGiveaways", promotionGiveaways);
    client.POST(req);
    return true;
  }

  public List<PromotionGiveUsed> getPromotionGiveUseds() throws Exception {
    Request req = create("getPromotionGiveUseds");
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<PromotionGiveUsed>>() {
    });
  }

  public List<PromotionGiveUsed> getPromotionGiveUsedsByInvoiceId(long invoiceId) throws Exception {
    Request req = create("getPromotionGiveUsedsByInvoiceId");
    req.addParam("invoiceId", invoiceId);
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<PromotionGiveUsed>>() {
    });
  }

  public PromotionGiveUsed getPromotionByInvoiceItemCode(String invoiceItemCode) throws Exception {
    Request req = create("getPromotionByInvoiceItemCode");
    req.addParam("invoiceItemCode", invoiceItemCode);
    Response res = client.POST(req);
    return res.getDataAs(PromotionGiveUsed.class);
  }
  
  public PromotionDetail getPromotionById(long promotionId) throws Exception {
    Request req = create("getPromotionById");
    req.addParam("promotionId", promotionId);
    Response res = client.POST(req);
    return res.getDataAs(PromotionDetail.class);
  }

  public PromotionGiveUsed savePromotionGiveUsed(PromotionGiveUsed promotionGiveUsed) throws Exception {
    Request req = create("savePromotionGiveUsed");
    req.addParam("promotionGiveUsed", promotionGiveUsed);
    Response res = client.POST(req);
    return res.getDataAs(PromotionGiveUsed.class);
  }

  public boolean deletePromotionGiveUsed(PromotionGiveUsed promotionGiveUsed) throws Exception {
    Request req = create("deletePromotionGiveUsed");
    req.addParam("promotionGiveUsed", promotionGiveUsed);
    client.POST(req);
    return true;
  }

  public List<PromotionDetail> getPromotions() throws Exception {
    Request req = create("getPromotions");
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<PromotionDetail>>() {
    });
  }

  public List<PromotionDetail> findPromotion(String table, String location, String customerLoginId, Double money, String storeCode)
      throws Exception {
    Request req = create("findPromotion");
    req.addParam("table", table);
    req.addParam("location", location);
    req.addParam("customerLoginId", customerLoginId);
    req.addParam("money", money);
    req.addParam("storeCode", storeCode);
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<PromotionDetail>>() {
    });
  }

  public List<PromotionDetail> findPromotionProduct(String table, String location, String customerLoginId,
      String product, Double money, String storeCode) throws Exception {
    Request req = create("findPromotionProduct");
    req.addParam("table", table);
    req.addParam("location", location);
    req.addParam("customerLoginId", customerLoginId);
    req.addParam("product", product);
    req.addParam("money", money);
    req.addParam("storeCode", storeCode);
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<PromotionDetail>>() {
    });
  }

  public PromotionDetail savePromotion(PromotionDetail promotion) throws Exception {
    Request req = create("savePromotion");
    req.addParam("promotion", promotion);
    Response res = client.POST(req);
    return res.getDataAs(PromotionDetail.class);
  }

  public boolean deletePromotion(PromotionDetail promotion) throws Exception {
    Request req = create("deletePromotion");
    req.addParam("promotion", promotion);
    client.POST(req);
    return true;
  }

  public PromotionUsed savePromotionUsed(PromotionUsed promotionUsed) throws Exception {
    Request req = create("savePromotionUsed");
    req.addParam("promotionUsed", promotionUsed);
    Response res = client.POST(req);
    return res.getDataAs(PromotionUsed.class);
  }
  
  public CouponUsed getCouponUsed(String couponCode, long invoiceId) throws Exception{
  	Request req = create("getCouponUsed");
    req.addParam("couponCode", couponCode);
    req.addParam("invoiceId", invoiceId);
    Response res = client.POST(req);
    return res.getDataAs(CouponUsed.class);
  }

  public boolean deletePromotionUsed(PromotionUsed promotionUsed) throws Exception {
    Request req = create("deletePromotionUsed");
    req.addParam("promotionUsed", promotionUsed);
    client.POST(req);
    return true;
  }

  public List<PromotionUsed> findPromotionUsedByInvoiceId(long invoiceId) throws Exception {
    Request req = create("findPromotionUsedByInvoiceId");
    req.addParam("invoiceId", invoiceId);
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<PromotionUsed>>() {
    });
  }
  
  

  public FilterResult<PromotionUsed> searchPromotionUsed(FilterQuery query) throws Exception {
    Request req = create("searchPromotionUsed");
    req.addParam("query", query);
    Response res = client.POST(req);
    return res.getDataAsByFilter(new TypeReference<FilterResult<PromotionUsed>>() {
    });
  }

  public PromotionUsed getPromotionUsed(String code) throws Exception {
    Request req = create("getPromotionUsed");
    req.addParam("code", code);
    Response res = client.POST(req);
    return res.getDataAs(PromotionUsed.class);
  }

  public boolean deletePromotionUsedByCode(String code) throws Exception {
    Request req = create("deletePromotionUsedByCode");
    req.addParam("code", code);
    client.POST(req);
    return true;
  }

  public boolean deleteByInvoiceId(long invoiceId) throws Exception {
    Request req = create("deleteByInvoiceId");
    req.addParam("invoiceId", invoiceId);
    client.POST(req);
    return true;
  }

  public MenuItemRef saveMenuItemRef(MenuItemRef menuItemRef) throws Exception {
    Request req = create("saveMenuItemRef");
    req.addParam("menuItemRef", menuItemRef);
    Response res = client.POST(req);
    return res.getDataAs(MenuItemRef.class);
  }

  public MenuItemRef getMenuItemRefByCode(String code) throws Exception {
    Request req = create("getMenuItemRefByCode");
    req.addParam("code", code);
    Response res = client.POST(req);
    return res.getDataAs(MenuItemRef.class);
  }
  
  public void deleteMenuItemRef(MenuItemRef menuItemRef) throws Exception{
    Request req = create("deleteMenuItemRef");
    req.addParam("menuItemRef", menuItemRef);
    client.POST(req);
  }

  public TimeOptionDetail saveTimeOptionDetail(TimeOptionDetail timeOptionDetail) throws Exception {
    Request req = create("saveTimeOptionDetail");
    req.addParam("timeOptionDetail", timeOptionDetail);
    Response res = client.POST(req);
    return res.getDataAs(TimeOptionDetail.class);
  }
  
  public CouponUsed saveCouponUsed(CouponUsed couponUsed) throws Exception{
  	 Request req = create("saveCouponUsed");
     req.addParam("couponUsed", couponUsed);
     Response res = client.POST(req);
     return res.getDataAs(CouponUsed.class);
  }

  public TimeOption findOneTimeOption(long id) throws Exception {
    Request req = create("findOneTimeOption");
    req.addParam("id", id);
    Response res = client.POST(req);
    return res.getDataAs(TimeOption.class);
  }

  public List<Daily> getDailyByTimeOp(long idTimeOption) throws Exception {
    Request req = create("getDailyByTimeOp");
    req.addParam("idTimeOption", idTimeOption);
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<Daily>>() {
    });
  }

  public List<Weekly> getWeeklyByTimeOp(long idTimeOption) throws Exception {
    Request req = create("getWeeklyByTimeOp");
    req.addParam("idTimeOption", idTimeOption);
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<Weekly>>() {
    });
  }

  public List<Monthly> getMonthlyByTimeOp(long idTimeOption) throws Exception {
    Request req = create("getMonthlyByTimeOp");
    req.addParam("idTimeOption", idTimeOption);
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<Monthly>>() {
    });
  }

  public List<Yearly> getYearlyByTimeOp(long idTimeOption) throws Exception {
    Request req = create("getYearlyByTimeOp");
    req.addParam("idTimeOption", idTimeOption);
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<Yearly>>() {
    });
  }

  public void deleteTest(String test) throws Exception {
    Request req = create("deleteTest");
    req.addParam("test", test);
    client.POST(req);
  }
  
  public List<CouponUsed> findCouponUsedByInvoiceId(long invoiceId) throws Exception{
  	 Request req = create("findCouponUsedByInvoiceId");
     req.addParam("invoiceId", invoiceId);
     Response res = client.POST(req);
     return res.getDataAs(new TypeReference<List<CouponUsed>>() {
     });
  }
  
  public List<CouponUsed> findByCouponCode(String couponCode) throws Exception{
  	 Request req = create("findByCouponCode");
     req.addParam("couponCode", couponCode);
     Response res = client.POST(req);
     return res.getDataAs(new TypeReference<List<CouponUsed>>() {
     });
  }

  Request create(String method) {
    return new Request("warehouse", "PromotionService", method);
  }

}
