package com.hkt.module.promotion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.account.AccountService;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.core.ServiceCallback;
import com.hkt.module.core.entity.AbstractPersistable.State;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.product.ProductService;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductTag;
import com.hkt.module.promotion.entity.Coupon;
import com.hkt.module.promotion.entity.CouponUsed;
import com.hkt.module.promotion.entity.CustomerTarget;
import com.hkt.module.promotion.entity.Daily;
import com.hkt.module.promotion.entity.LocationTarget;
import com.hkt.module.promotion.entity.Menu;
import com.hkt.module.promotion.entity.Menu.Type;
import com.hkt.module.promotion.entity.MenuItem;
import com.hkt.module.promotion.entity.MenuItem.MenuItemType;
import com.hkt.module.promotion.entity.MenuItemRef;
import com.hkt.module.promotion.entity.Monthly;
import com.hkt.module.promotion.entity.ProductTarget;
import com.hkt.module.promotion.entity.Promotion;
import com.hkt.module.promotion.entity.PromotionDetail;
import com.hkt.module.promotion.entity.PromotionGiveUsed;
import com.hkt.module.promotion.entity.PromotionGiveaways;
import com.hkt.module.promotion.entity.PromotionUsed;
import com.hkt.module.promotion.entity.TimeOption;
import com.hkt.module.promotion.entity.TimeOptionDetail;
import com.hkt.module.promotion.entity.Weekly;
import com.hkt.module.promotion.entity.Yearly;
import com.hkt.module.promotion.repository.CouponRepository;
import com.hkt.module.promotion.repository.CouponUsedRepository;
import com.hkt.module.promotion.repository.DailyRepository;
import com.hkt.module.promotion.repository.MenuItemRefRepository;
import com.hkt.module.promotion.repository.MenuItemRepository;
import com.hkt.module.promotion.repository.MenuRepository;
import com.hkt.module.promotion.repository.MonthlyRepository;
import com.hkt.module.promotion.repository.PromotionGiveUsedRepository;
import com.hkt.module.promotion.repository.PromotionGiveawaysRepository;
import com.hkt.module.promotion.repository.PromotionRepository;
import com.hkt.module.promotion.repository.PromotionUsedRepository;
import com.hkt.module.promotion.repository.TimeOptionRepository;
import com.hkt.module.promotion.repository.WeeklyRepository;
import com.hkt.module.promotion.repository.YearlyRepository;
import com.hkt.module.promotion.util.CouponScenario;
import com.hkt.module.promotion.util.MenuScenario;
import com.hkt.module.promotion.util.PromotionScenario;
import com.hkt.module.promotion.util.PromotionScenario.Promotions;
import com.hkt.module.promotion.util.PromotionScenario.Promotions.PromotionUseds;

@Service("PromotionService")
public class PromotionService {

  @Autowired
  PromotionRepository promotionRepo;

  @Autowired
  PromotionUsedRepository promotionUsedRepo;

  @Autowired
  ProductService productService;

  @Autowired
  MenuRepository menuRepo;

  @Autowired
  MenuItemRepository menuItemRepo;

  @Autowired
  CouponRepository couponRepo;

  @Autowired
  CouponUsedRepository couponUsedRepo;

  @Autowired
  PromotionGiveawaysRepository promotionGiveawaysRepo;

  @Autowired
  PromotionGiveUsedRepository promotionGiveUsedRepo;

  @Autowired
  MenuItemRefRepository menuItemRefRepo;

  @Autowired
  TimeOptionRepository timeOptionRepo;

  @Autowired
  DailyRepository dailyRepo;

  @Autowired
  WeeklyRepository weeklyRepo;

  @Autowired
  MonthlyRepository monthlyRepo;

  @Autowired
  YearlyRepository yearlyRepo;

  @Autowired
  private AccountService accountService;
  
  @PostConstruct
  public void onInit(){
  	AutoRunBackup autoRunBackup = new AutoRunBackup();
		autoRunBackup.startTimer();
  }

  @Transactional(readOnly = true)
  public List<PromotionDetail> getPromotions() {
    return promotionRepo.findPromotionDetailByRecycleBin(false);
  }

  @Transactional(readOnly = true)
  public List<Coupon> getAllCoupons() {
    return couponRepo.findCouponByRecycleBin(false);
  }

  @Transactional(readOnly = true)
  public Coupon getCouponByCode(String code) {
    return couponRepo.getCouponByCode(code);
  }
  
  @Transactional(readOnly = true)
  public CouponUsed getCouponUsed(String couponCode, long invoiceId){
  	return couponUsedRepo.getCouponUsed(couponCode, invoiceId);
  }

  @Transactional(readOnly = true)
  public List<Menu> getAllMenus() {
    return menuRepo.findMenuByRecycleBin(false);
  }

  @Transactional(readOnly = true)
  public List<MenuItem> getMenuItemByMenuItemType(MenuItemType menuItemType) {
    return menuItemRepo.getMenuItemByMenuItemType(menuItemType);
  }

  @Transactional(readOnly = true)
  public List<MenuItemRef> getAllMenuItemRef() {
    return menuItemRefRepo.findMenuItemRefByRecycleBin(false);
  }

  @Transactional
  public PromotionDetail savePromotion(PromotionDetail promotion) {
    List<ProductTarget> productTargets = promotion.getProductTargets();
    for (int i = 0; i < productTargets.size();) {
      ProductTarget productTarget = productTargets.get(i);
      if (productTarget.getPersistableState() == State.DELETED) {
        productTargets.remove(i);
      } else {
        i++;
      }
    }
    List<LocationTarget> locationTargets = promotion.getLocationTargets();
    for (int i = 0; i < locationTargets.size();) {
      LocationTarget locationTarget = locationTargets.get(i);
      if (locationTarget.getPersistableState() == State.DELETED) {
        locationTargets.remove(i);
      } else {
        i++;
      }
    }
    List<CustomerTarget> customerTargets = promotion.getCustomerTargets();
    for (int i = 0; i < customerTargets.size();) {
      CustomerTarget customerTarget = customerTargets.get(i);
      if (customerTarget.getPersistableState() == State.DELETED) {
        customerTargets.remove(i);
      } else {
        i++;
      }
    }
    promotion.setHaveCustomer(customerTargets.size() > 0);
    promotion.setHaveLocation(locationTargets.size() > 0);
    promotion.setHaveProduct(productTargets.size() > 0);
    return promotionRepo.save(promotion);
  }

  /** Delete Promotion */
  @Transactional
  public boolean deletePromotion(PromotionDetail promotion) {
    return deletePromotionCallBack(promotion, null);
  }

  /**
   * @param promotionDetail
   * @param callback
   */
  @Transactional
  public boolean deletePromotionCallBack(PromotionDetail promotion, ServiceCallback<PromotionService> callback) {
    if (callback != null) {
      callback.callback(this);
    }
    if (promotion == null) {
      return false;
    } else {
      if (promotion.isRecycleBin()) {
        promotionRepo.delete(promotion);
        promotionUsedRepo.deleteByPromotionId(promotion.getId());
        return true;
      } else {
        try {
          List<PromotionUsed> pus = promotionUsedRepo.findByPromotionId(promotion.getId());
          for (PromotionUsed pu : pus) {
            pu.setRecycleBin(true);
            promotionUsedRepo.save(pu);
          }
          promotion.setRecycleBin(true);
          promotionRepo.save(promotion);
          return true;
        } catch (Exception e) {
          return false;
        }
      }
    }
  }

  @Transactional(readOnly = true)
  public List<CouponUsed> getCouponUseds() {
    return (List<CouponUsed>) couponUsedRepo.findAll();
  }

  @Transactional(readOnly = true)
  public PromotionDetail getPromotionById(long promotionId) {
    return promotionRepo.findOne(promotionId);
  }

  @Transactional(readOnly = true)
  public List<PromotionDetail> findPromotion(String table, String location, String customerLoginId, final Double money,
      String storeCode) {
    List<PromotionDetail> promotions = new ArrayList<PromotionDetail>();
    List<AccountMembership> accountMemberships = accountService.findMembershipByAccountLoginId(customerLoginId);
    List<String> grList = new ArrayList<String>();
    grList.add(customerLoginId);
    for (AccountMembership ac : accountMemberships) {
      grList.add(ac.getGroupPath());
    }
    List<PromotionDetail> p5 = promotionRepo.findPromotion(table, location, grList, new Date(), money, storeCode);
    promotions.addAll(p5);

    List<PromotionDetail> p1 = promotionRepo.findByLocation(table, location, new Date(), money, storeCode);
    promotions.addAll(p1);
    List<PromotionDetail> p2 = promotionRepo.findByCustomerGroup(grList, new Date(), money, storeCode);
    promotions.addAll(p2);
    List<PromotionDetail> p3 = promotionRepo.findByStore(new Date(), money, storeCode);
    promotions.addAll(p3);
    Collections.sort(promotions, new Comparator<Promotion>() {
      @Override
      public int compare(Promotion it1, Promotion it2) {
        double a = it1.getDiscount();
        double b = it2.getDiscount();
        if (it1.getDiscountRate() != 0) {
          a = it1.getDiscountRate() * money / 100;
        }
        if (it2.getDiscountRate() != 0) {
          b = it2.getDiscountRate() * money / 100;
        }
        if (a > b) {
          return -1;
        } else {
          if (a < b) {
            return 1;
          } else {
            return 0;
          }
        }
      }
    });
    if (!promotions.isEmpty()) {
      List<PromotionDetail> list = new ArrayList<PromotionDetail>();
      double discount = 0;
      for (int i = 0; i < promotions.size(); i++) {
        if (promotions.get(i).isCumulative()) {
          list.add(promotions.get(i));
          if (promotions.get(i).getDiscount() != 0) {
            discount = discount + promotions.get(i).getDiscount();
          } else {
            discount = discount + promotions.get(i).getDiscountRate() * money / 100;
          }
        }
      }
      if (discount > promotions.get(0).getDiscount() && discount > promotions.get(0).getDiscountRate() * money / 100) {
        return list;
      } else {
        return promotions.subList(0, 1);
      }
    } else {
      return new ArrayList<PromotionDetail>();
    }
  }

  @Transactional(readOnly = true)
  public List<PromotionDetail> findPromotionProduct(String table, String location, String customerLoginId,
      String product, final Double money, String storeCode) {
    List<PromotionDetail> promotions = new ArrayList<PromotionDetail>();
    List<AccountMembership> accountMemberships = accountService.findMembershipByAccountLoginId(customerLoginId);
    List<String> grList = new ArrayList<String>();
    if (customerLoginId.trim().isEmpty()) {
      grList.add("/");
    } else {
      grList.add(customerLoginId);
    }
    for (AccountMembership ac : accountMemberships) {
      grList.add(ac.getGroupPath());
    }
    Product p = productService.getProductByCode(product);
    List<String> tags = new ArrayList<String>();
    tags.add(product);
    for (ProductTag tag : p.getProductTags()) {
      tags.add(tag.getTag());
    }
    List<PromotionDetail> p3 = promotionRepo.findByProduct(tags, new Date(), money, storeCode);
    promotions.addAll(p3);
    List<PromotionDetail> p5 = promotionRepo.findPromotionProduct(table, location, grList, tags, new Date(), money,
        storeCode);
    promotions.addAll(p5);
    List<PromotionDetail> p1 = promotionRepo.findByLocationAndProduct(table, location, tags, new Date(), money,
        storeCode);
    promotions.addAll(p1);
    List<PromotionDetail> p2 = promotionRepo.findByCustomerGroupAndProduct(grList, tags, new Date(), money, storeCode);
    promotions.addAll(p2);
    Collections.sort(promotions, new Comparator<Promotion>() {
      @Override
      public int compare(Promotion it1, Promotion it2) {
        double a = it1.getDiscount();
        double b = it2.getDiscount();
        if (it1.getDiscountRate() != 0) {
          a = it1.getDiscountRate() * money / 100;
        }
        if (it2.getDiscountRate() != 0) {
          b = it2.getDiscountRate() * money / 100;
        }
        if (a > b) {
          return -1;
        } else {
          if (a < b) {
            return 1;
          } else {
            return 0;
          }
        }
      }
    });
    if (!promotions.isEmpty()) {
      List<PromotionDetail> list = new ArrayList<PromotionDetail>();
      double discount = 0;
      for (int i = 0; i < promotions.size(); i++) {
        if (promotions.get(i).isCumulative()) {
          list.add(promotions.get(i));
          if (promotions.get(i).getDiscount() != 0) {
            discount = discount + promotions.get(i).getDiscount();
          } else {
            discount = discount + promotions.get(i).getDiscountRate() * money / 100;
          }
        }
      }
      if (discount > promotions.get(0).getDiscount() && discount > promotions.get(0).getDiscountRate() * money / 100) {
        return list;
      } else {
        return promotions.subList(0, 1);
      }
    } else {
      return new ArrayList<PromotionDetail>();
    }
  }

  @Transactional(readOnly = true)
  public FilterResult<PromotionUsed> searchPromotionUsed(FilterQuery query) {
    if (query == null)
      query = new FilterQuery();
    return promotionUsedRepo.search(query);
  }

  @Transactional
  public PromotionUsed savePromotionUsed(PromotionUsed promotionUsed) {
    return promotionUsedRepo.save(promotionUsed);
  }

  /** @param promotionUsed */
  @Transactional
  public boolean deletePromotionUsed(PromotionUsed promotionUsed) {
    return deletePromotionUsedCallBack(promotionUsed, null);
  }

  /**
   * @param promotionUsed
   * @param callback
   */
  @Transactional
  public boolean deletePromotionUsedCallBack(PromotionUsed promotionUsed, ServiceCallback<PromotionService> callback) {
    if (callback != null) {
      callback.callback(this);
    }
    promotionUsedRepo.delete(promotionUsed);
    return true;
  }

  /**
   * @param invoiceId
   */
  @Transactional
  public boolean deleteByInvoiceId(long invoiceId) {
    promotionUsedRepo.deleteByInvoiceId(invoiceId);
    promotionGiveUsedRepo.deleteByInvoiceId(invoiceId);
    couponUsedRepo.deleteByInvoiceId(invoiceId);
    return true;
  }

  @Transactional(readOnly = true)
  public List<PromotionUsed> findPromotionUsedByPromotionId(long promotionId) {
    return promotionUsedRepo.findByPromotionId(promotionId);
  }

  @Transactional(readOnly = true)
  public List<PromotionUsed> findPromotionUsedByInvoiceId(long invoiceId) {
    return promotionUsedRepo.findByInvoiceId(invoiceId);
  }
  
  @Transactional(readOnly = true)
  public List<CouponUsed> findCouponUsedByInvoiceId(long invoiceId) {
    return couponUsedRepo.findByinvoiceId(invoiceId);
  }
  
  @Transactional(readOnly = true)
  public List<CouponUsed> findByCouponCode(String couponCode) {
    return couponUsedRepo.findByCouponCode(couponCode);
  }

  @Transactional(readOnly = true)
  public PromotionUsed getPromotionUsed(String code) {
    return promotionUsedRepo.getByCode(code);
  }

  /**
   * @param code
   */
  @Transactional
  public boolean deletePromotionUsedByCode(String code) {
    promotionUsedRepo.deleteByCode(code);
    return true;

  }

  @Transactional
  public void createScenarioPromotion(PromotionScenario scenario) throws Exception {
    List<Promotions> promotions = scenario.getPromotions();
    for (Promotions promotionData : promotions) {
      PromotionDetail promotion = savePromotion(promotionData.getPromotion());
      List<PromotionUseds> promotionUseds = promotionData.getPromotionUseds();
      for (PromotionUseds proUseds : promotionUseds) {
        PromotionUsed useds = proUseds.getPromotionUsed();

        useds.setPromotionId(promotion.getId());
        useds.setInvoiceId(1);

        promotionUsedRepo.save(useds);
      }
    }
  }

  @Transactional
  public void createScenarioCoupon(CouponScenario scenario) throws Exception {
    List<Coupon> coupons = scenario.getCoupons();
    couponRepo.save(coupons);
  }

  @Transactional
  public void createScenarioMenu(MenuScenario scenario) throws Exception {
    List<MenuItemRef> itemRefs = scenario.getMenuItemRefs();
    menuItemRefRepo.save(itemRefs);
    List<Menu> menus = scenario.getMenus();
    menuRepo.save(menus);
  }

  @Transactional
  public Menu saveMenu(Menu menu) {
    List<MenuItem> menuItems = menu.getMenuItems();
    for (int i = 0; i < menuItems.size(); i++) {
      MenuItem menuItem = menuItems.get(i);
      if (menuItem == null) {
        menuItems.remove(i);
        i--;
      }
      if (menuItem.getPersistableState() == State.DELETED) {
        if (menuItem.getMenuItemType() == MenuItemType.ByMenuItemRef) {
          if (menuItem.getIdentifierId() != null) {
            MenuItemRef itemRef = menuItemRefRepo.getMenuItemRefByCode(menuItem.getIdentifierId());
            menuItemRefRepo.delete(itemRef);
          }
        }
        menuItems.remove(i);
        i--;
      }
    }
    return menuRepo.save(menu);
  }

  @Transactional(readOnly = true)
  public Menu getMenuByCode(String code) {
    return menuRepo.getMenuByCode(code);
  }

  @Transactional
  public MenuItem saveMenuItem(MenuItem menuItem) {
    return menuItemRepo.save(menuItem);
  }

  @Transactional
  public Coupon saveCoupon(Coupon coupon) {
    return couponRepo.save(coupon);
  }
  
  @Transactional
  public CouponUsed saveCouponUsed(CouponUsed couponUsed) {
    return couponUsedRepo.save(couponUsed);
  }

  @Transactional(readOnly = true)
  public List<Coupon> getCoupons() {
    return couponRepo.findCouponByRecycleBin(false);
  }

  @Transactional(readOnly = true)
  public FilterResult<Menu> searchMenu(FilterQuery query) {
    if (query == null)
      query = new FilterQuery();
    return menuRepo.search(query);
  }

  /**
   * 
   * @param coupon
   * @return
   */
  @Transactional
  public boolean deleteCoupon(Coupon coupon) {
    if (coupon == null) {
      return false;
    } else {
      if (coupon.isRecycleBin()) {
        couponRepo.delete(coupon);
        return true;
      } else {
        coupon.setRecycleBin(true);
        if (couponRepo.save(coupon) != null)
          return true;
        else
          return false;
      }
    }
  }

  /**
   * @param menu
   */
  @Transactional
  public boolean deleteMenu(Menu menu) {
    if (menu == null) {
      return false;
    } else {
      if (menu.isRecycleBin()) {
        menuRepo.delete(menu);
        return true;
      } else {
        menu.setRecycleBin(true);
        if (menuRepo.save(menu) != null)
          return true;
        else
          return false;
      }
    }
  }

  @Transactional
  public PromotionGiveaways savePromotionGiveaways(PromotionGiveaways promotionGiveaways) {
    return promotionGiveawaysRepo.save(promotionGiveaways);
  }

  @Transactional
  public PromotionGiveaways getPromotionGiveawaysByProductCode(String productCode) {
    return promotionGiveawaysRepo.getPromotionGiveawaysByProductCode(productCode, new Date());
  }

  @Transactional(readOnly = true)
  public List<PromotionGiveaways> getPromotionGiveaways() {
    return promotionGiveawaysRepo.findPromotionGiveawaysByRecycleBin(false);
  }

  /**
   * @param promotionGiveaways
   */
  @Transactional
  public boolean deletePromotionGiveaways(PromotionGiveaways promotionGiveaways) {
    if (promotionGiveaways == null) {
      return false;
    } else {
      if (promotionGiveaways.isRecycleBin()) {
        promotionGiveawaysRepo.delete(promotionGiveaways);
        return true;
      } else {
        promotionGiveaways.setRecycleBin(true);
        if (promotionGiveawaysRepo.save(promotionGiveaways) != null)
          return true;
        else
          return false;
      }
    }
  }

  @Transactional
  public PromotionGiveUsed savePromotionGiveUsed(PromotionGiveUsed promotionGiveUsed) {
    return promotionGiveUsedRepo.save(promotionGiveUsed);
  }

  @Transactional(readOnly = true)
  public List<PromotionGiveUsed> getPromotionGiveUseds() {
    return (List<PromotionGiveUsed>) promotionGiveUsedRepo.findAll();
  }

  @Transactional(readOnly = true)
  public List<PromotionGiveUsed> getPromotionGiveUsedsByInvoiceId(long invoiceId) {
    return promotionGiveUsedRepo.getPromotionGiveUsedsByInvoiceId(invoiceId);
  }

  /**
   * @param promotionGiveUsed
   */
  @Transactional
  public boolean deletePromotionGiveUsed(PromotionGiveUsed promotionGiveUsed) {
    promotionGiveUsedRepo.delete(promotionGiveUsed);
    return true;
  }

  @Transactional(readOnly = true)
  public PromotionGiveUsed getPromotionByInvoiceItemCode(String invoiceItemCode) {
    return promotionGiveUsedRepo.getPromotionByInvoiceItemCode(invoiceItemCode);
  }

  @Transactional
  public MenuItemRef saveMenuItemRef(MenuItemRef menuItemRef) {
    return menuItemRefRepo.save(menuItemRef);
  }

  /**
   * @param menuItemRef
   */
  @Transactional
  public boolean deleteMenuItemRef(MenuItemRef menuItemRef) {

    menuItemRefRepo.delete(menuItemRef);
    return true;
  }

  @Transactional(readOnly = true)
  public MenuItemRef getMenuItemRefByCode(String code) {
    return menuItemRefRepo.getMenuItemRefByCode(code);
  }

  @Transactional(readOnly = false)
  public List<Menu> getMenuByType(Type type) {
    return menuRepo.getMenuByType(type);
  }

  @Transactional
  public TimeOptionDetail saveTimeOptionDetail(TimeOptionDetail timeOptionDetail) {
    TimeOption timeOption = timeOptionRepo.save(timeOptionDetail.getTimeOption());
    if (timeOptionDetail.getDaily() != null) {
      Daily daily = timeOptionDetail.getDaily();
      daily.setIdTimeOption(timeOption.getId());
      dailyRepo.save(daily);
    }
    if (timeOptionDetail.getWeekly() != null) {
      Weekly weekly = timeOptionDetail.getWeekly();
      weekly.setIdTimeOption(timeOption.getId());
      weekly = weeklyRepo.save(weekly);
    }
    if (timeOptionDetail.getMonthly() != null) {
      Monthly monthly = timeOptionDetail.getMonthly();
      monthly.setIdTimeOption(timeOption.getId());
      monthlyRepo.save(monthly);
    }
    if (timeOptionDetail.getYearly() != null) {
      Yearly yearly = timeOptionDetail.getYearly();
      yearly.setIdTimeOption(timeOption.getId());
      yearlyRepo.save(yearly);
    }
    return timeOptionDetail;
  }

  @Transactional
  public Weekly getById(long id) {
    return weeklyRepo.findOne(id);
  }

  @Transactional
  public Weekly saveWeekly(Weekly weekly) {
    return weeklyRepo.save(weekly);
  }

  @Transactional
  public void deleteAll() throws Exception {
    promotionUsedRepo.deleteAll();
    promotionRepo.deleteAll();
    couponRepo.deleteAll();
    menuRepo.deleteAll();
    menuItemRefRepo.deleteAll();
    timeOptionRepo.deleteAll();
    weeklyRepo.deleteAll();
    yearlyRepo.deleteAll();
    dailyRepo.deleteAll();
  }

  @Transactional
  public void deleteTest(String test) {
    menuItemRefRepo.deleteTest(test);
    promotionGiveawaysRepo.deleteTest(test);
    promotionRepo.deleteTestCustomerTarget(test);
    promotionRepo.deleteTestLocationTarget(test);
    promotionRepo.deleteTest(test);
    promotionRepo.deleteTestProductTarget(test);
    couponRepo.deleteTest(test);
    menuItemRepo.deleteTest(test);
    menuRepo.deleteTest(test);
  }

  @Transactional(readOnly = true)
  public TimeOption findOneTimeOption(long id) {
    return timeOptionRepo.findOne(id);
  }

  @Transactional(readOnly = true)
  public List<Daily> getDailyByTimeOp(long idTimeOption) {
    return dailyRepo.getByTimeOptionId(idTimeOption);
  }

  @Transactional(readOnly = true)
  public List<Weekly> getWeeklyByTimeOp(long idTimeOption) {
    return weeklyRepo.getByTimeOptionId(idTimeOption);
  }

  @Transactional(readOnly = true)
  public List<Monthly> getMonthlyByTimeOp(long idTimeOption) {
    return monthlyRepo.getByTimeOptionId(idTimeOption);
  }

  @Transactional(readOnly = true)
  public List<Yearly> getYearlyByTimeOp(long idTimeOption) {
    return yearlyRepo.getByTimeOptionId(idTimeOption);
  }

}
