package com.hkt.client.swingexp.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.PromotionServiceClient;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.promotion.DateTimeConverter;
import com.hkt.module.promotion.entity.Coupon;
import com.hkt.module.promotion.entity.CouponUsed;
import com.hkt.module.promotion.entity.Daily;
import com.hkt.module.promotion.entity.Menu;
import com.hkt.module.promotion.entity.MenuItem;
import com.hkt.module.promotion.entity.MenuItem.MenuItemType;
import com.hkt.module.promotion.entity.MenuItemRef;
import com.hkt.module.promotion.entity.Monthly;
import com.hkt.module.promotion.entity.Promotion.Status;
import com.hkt.module.promotion.entity.PromotionDetail;
import com.hkt.module.promotion.entity.PromotionGiveUsed;
import com.hkt.module.promotion.entity.PromotionGiveaways;
import com.hkt.module.promotion.entity.PromotionUsed;
import com.hkt.module.promotion.entity.TimeOption;
import com.hkt.module.promotion.entity.TimeOptionDetail;
import com.hkt.module.promotion.entity.Weekly;
import com.hkt.module.promotion.entity.Yearly;

public class PromotionModelManager {
  private static PromotionModelManager instance;

  private ClientContext clientContext = ClientContext.getInstance();
  private PromotionServiceClient clientCore = clientContext.getBean(PromotionServiceClient.class);
  private DateTimeConverter dateTimeConverter;
  public boolean isPromotionDiscount;
  public boolean isPromotionProductDiscount;
 

  private PromotionModelManager() {
    dateTimeConverter = new DateTimeConverter();
  }

  static public PromotionModelManager getInstance() {
    if (instance == null) {
      instance = new PromotionModelManager();
    }
    return instance;
  }

  public PromotionDetail savePromotion(PromotionDetail promotion) throws Exception {
    return clientCore.savePromotion(promotion);
  }

  public boolean deletePromotion(PromotionDetail promotion) throws Exception {
    clientCore.deletePromotion(promotion);
    return true;
  }

  public List<PromotionDetail> getPromotions() throws Exception {
    List<PromotionDetail> promotions = clientCore.getPromotions();
    for (int i = 0; i < promotions.size();) {
      if (promotions.get(i).getStatus() == Status.Expired) {
        promotions.remove(i);
      } else {
        i++;
      }
    }
    return promotions;
  }
  
  public PromotionDetail getPromotionById(long promotionId) throws Exception {
  	return clientCore.getPromotionById(promotionId);
  }

  public Coupon saveCoupon(Coupon coupon) throws Exception {
    return clientCore.saveCoupon(coupon);
  }

  public boolean deleteCoupon(Coupon coupon) throws Exception {
    clientCore.deleteCoupon(coupon);
    return true;
  }

  public void deleteMenuItemRef(MenuItemRef menuItemRef) throws Exception {
    clientCore.deleteMenuItemRef(menuItemRef);
  }

  public List<Coupon> getCoupons() throws Exception {
    return clientCore.getCoupons();
  }

  public Coupon getCouponByCode(String code) throws Exception {
    return clientCore.getCouponByCode(code);
  }
  
  public CouponUsed saveCouponUsed(CouponUsed couponUsed) throws Exception {
    return clientCore.saveCouponUsed(couponUsed);
  }
  
  public List<CouponUsed> getCouponUsedsByInvoiceId(long invoiceId){
  	try {
  		return clientCore.findCouponUsedByInvoiceId(invoiceId);
    } catch (Exception e) {
	   return new ArrayList<CouponUsed>();
    }
 	 
 }
  
  public CouponUsed getCouponUsed(String couponCode, long invoiceId) {
  	try {
	    return clientCore.getCouponUsed(couponCode, invoiceId);
    } catch (Exception e) {
    	return null;
    }
  }
 
 public List<CouponUsed> getCouponUsedsByCouponCode(String couponCode){
	 try {
 		return clientCore.findByCouponCode(couponCode);
   } catch (Exception e) {
	   return new ArrayList<CouponUsed>();
   }
 }

  public Menu saveMenu(Menu menu) throws Exception {
    return clientCore.saveMenu(menu);
  }

  public Menu getMenuByCode(String code) throws Exception {
    return clientCore.getMenuByCode(code);
  }

  public boolean deleteMenu(Menu menu) throws Exception {
    return clientCore.deleteMenu(menu);
  }

  public List<Menu> searchMenu() throws Exception {
    return clientCore.searchMenu(new FilterQuery()).getData();
  }

  public List<MenuItem> getMenuItemByMenuItemType(MenuItemType menuItemType) throws Exception {
    return clientCore.getMenuItemByMenuItemType(menuItemType);
  }

  public PromotionGiveaways savePromotionGiveaways(PromotionGiveaways promotionGiveaways) throws Exception {
    return clientCore.savePromotionGiveaways(promotionGiveaways);
  }

  public PromotionGiveaways getPromotionGiveawaysByProductCode(String productCode) throws Exception {
    return clientCore.getPromotionGiveawaysByProductCode(productCode);
  }

  public boolean deletePromotionGiveaways(PromotionGiveaways promotionGiveaways) throws Exception {
    clientCore.deletePromotionGiveaways(promotionGiveaways);
    return true;
  }

  public List<PromotionGiveaways> getPromotionGiveaways() throws Exception {
    return clientCore.getPromotionGiveaways();
  }

  public PromotionGiveUsed savePromotionGiveUsed(PromotionGiveUsed promotionGiveUsed) throws Exception {
    return clientCore.savePromotionGiveUsed(promotionGiveUsed);
  }

  public PromotionGiveUsed getPromotionByInvoiceItems(List<InvoiceItem> invoiceItems) {
    try {
      for (InvoiceItem invoiceItem : invoiceItems) {
        PromotionGiveUsed promotionGiveUsed = clientCore.getPromotionByInvoiceItemCode(invoiceItem.getItemCode());
        if (promotionGiveUsed != null) {
          return promotionGiveUsed;
        }
      }
    } catch (Exception e) {
      return null;
    }

    return null;
  }

  public boolean deletePromotionGiveUsed(PromotionGiveUsed promotionGiveUsed) throws Exception {
    clientCore.deletePromotionGiveUsed(promotionGiveUsed);
    return true;
  }

  public List<PromotionGiveUsed> getPromotionGiveUseds() throws Exception {
    return clientCore.getPromotionGiveUseds();
  }

  public List<PromotionUsed> findPromotionUsedByInvoiceId(long invoiceId) throws Exception {
    return clientCore.findPromotionUsedByInvoiceId(invoiceId);
  }

  public PromotionUsed savePromotionUsed(PromotionUsed promotionUsed) throws Exception {
    return clientCore.savePromotionUsed(promotionUsed);
  }

  public boolean deletePromotionUsed(PromotionUsed promotionUsed) throws Exception {
    return clientCore.deletePromotionUsed(promotionUsed);
  }

  public boolean deleteByInvoiceId(long invoiceId) throws Exception {
    return clientCore.deleteByInvoiceId(invoiceId);
  }

  public List<PromotionUsed> getAllPromotionUsed() throws Exception {
    return clientCore.searchPromotionUsed(new FilterQuery()).getData();
  }

  public PromotionUsed getPromotionUsed(String code) throws Exception {
    return clientCore.getPromotionUsed(code);
  }

  public void deletePromotionUsedByCode(String code) throws Exception {
    clientCore.deletePromotionUsedByCode(code);
  }

  public List<PromotionGiveUsed> getPromotionGiveUsedsByInvoiceId(long invoiceId) throws Exception {
    return clientCore.getPromotionGiveUsedsByInvoiceId(invoiceId);
  }

  public double getPromotion(String table, String location, String customerLoginId, Double money, String storeCode) {
    try {
      double promotion = 0;
      if(customerLoginId==null){
        customerLoginId="";
      }
      if(storeCode==null){
        storeCode = "";
      }
      List<PromotionDetail> promotions = clientCore.findPromotion(table, location, customerLoginId, money,storeCode);
      for (int i = 0; i < promotions.size(); i++) {
        if (promotions.get(i).getTimeOptionId() > 0) {
          if (!validateTimeOption(promotions.get(i).getTimeOptionId(), new Date(), promotions.get(i).getFromDate(),
              promotions.get(i).getToDate())) {
            if (promotions.get(i).getDiscount() > 0) {
              promotion = promotion + promotions.get(i).getDiscount();
              isPromotionDiscount = true;
            } else {
              isPromotionDiscount = false;
              promotion = promotion + promotions.get(i).getDiscountRate() * money / 100;
            }
          }
        } else {
          if (promotions.get(i).getDiscount() > 0) {
            isPromotionDiscount = true;
            promotion = promotion + promotions.get(i).getDiscount();
          } else {
            isPromotionDiscount = false;
            promotion = promotion + promotions.get(i).getDiscountRate() * money / 100;
          }
        }
      }
      return promotion;
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

  public double getPromotionProduct(String table, String location, String customerLoginId, String product, Double money
      ,String storeCode){
    try {
      double promotion = 0;
      List<PromotionDetail> promotions = clientCore.findPromotionProduct(table, location, customerLoginId, product,
          money,storeCode);
      for (int i = 0; i < promotions.size(); i++) {
        if (promotions.get(i).getTimeOptionId() > 0) {
          if (!validateTimeOption(promotions.get(i).getTimeOptionId(), new Date(), promotions.get(i).getFromDate(),
              promotions.get(i).getToDate())) {
            if (promotions.get(i).getDiscount() > 0) {
              isPromotionProductDiscount = true;
              promotion = promotion + promotions.get(i).getDiscount();
            } else {
              isPromotionProductDiscount = false;
              promotion = promotion + promotions.get(i).getDiscountRate() * money / 100;
            }
          }
        } else {
          if (promotions.get(i).getDiscount() > 0) {
            isPromotionProductDiscount = true;
            promotion = promotion + promotions.get(i).getDiscount();
          } else {
            isPromotionProductDiscount = false;
            promotion = promotion + promotions.get(i).getDiscountRate() * money / 100;
          }
        }
      }
      return promotion;
    } catch (Exception e) {
      return 0;
    }
  }

  public MenuItemRef saveMenuItemRef(MenuItemRef menuItemRef) throws Exception {
    return clientCore.saveMenuItemRef(menuItemRef);
  }

  public MenuItemRef getMenuItemRefByCode(String code) throws Exception {
    return clientCore.getMenuItemRefByCode(code);
  }

  public TimeOptionDetail saveTimeOptionDetail(TimeOptionDetail timeOptionDetail) throws Exception {
    return clientCore.saveTimeOptionDetail(timeOptionDetail);
  }

  public TimeOption findOneTimeOption(long id) throws Exception {
    return clientCore.findOneTimeOption(id);
  }

  public List<Daily> getDailyByTimeOp(long idTimeOption) throws Exception {
    return clientCore.getDailyByTimeOp(idTimeOption);
  }

  public List<Weekly> getWeeklyByTimeOp(long idTimeOption) throws Exception {
    return clientCore.getWeeklyByTimeOp(idTimeOption);
  }

  public List<Monthly> getMonthlyByTimeOp(long idTimeOption) throws Exception {
    return clientCore.getMonthlyByTimeOp(idTimeOption);
  }

  public List<Yearly> getYearlyByTimeOp(long idTimeOption) throws Exception {
    return clientCore.getYearlyByTimeOp(idTimeOption);
  }

  public boolean validateTimeOption(long idTimeOption, Date dateNow, Date dateStart, Date dateFinish) {
    Calendar cNow = Calendar.getInstance();
    Calendar cStart = Calendar.getInstance();
    Calendar cFinish = Calendar.getInstance();

    // cNow.setTime(dateNow);
    cStart.setTime(dateStart);
    cFinish.setTime(dateFinish);
    return validate(idTimeOption, cStart, cFinish, cNow);
  }

  public boolean validate(long idTimeOption, Calendar startDate, Calendar finishDate, Calendar currentDate) {
    try {

      TimeOption timeOption = PromotionModelManager.getInstance().findOneTimeOption(idTimeOption);
      if (timeOption != null) {
        if (timeOption.isDailyChoose()) {
          List<Daily> list = PromotionModelManager.getInstance().getDailyByTimeOp(idTimeOption);
          if (list != null && !list.isEmpty()) {
            Daily daily = list.get(0);
            return checkDailyLimit(daily, startDate, currentDate);
          } else {
            return false;
          }
        } else if (timeOption.isWeeklyChoose()) {
          List<Weekly> list = PromotionModelManager.getInstance().getWeeklyByTimeOp(idTimeOption);
          if (list != null && !list.isEmpty()) {
            Weekly weekly = list.get(0);
            return checkWeeklyLimit(weekly, startDate, currentDate);
          } else {
            return false;
          }
        } else if (timeOption.isMonthlyChoose()) {
          List<Monthly> list = PromotionModelManager.getInstance().getMonthlyByTimeOp(idTimeOption);
          if (list != null && !list.isEmpty()) {
            Monthly monthly = list.get(0);
            return checkMonthlyLimit(monthly, startDate, currentDate);
          } else {
            return false;
          }
        } else if (timeOption.isYearlyChoose()) {
          List<Yearly> list = PromotionModelManager.getInstance().getYearlyByTimeOp(idTimeOption);
          if (list != null && !list.isEmpty()) {
            Yearly yearly = list.get(0);
            return checkYearlyLimit(yearly, startDate, currentDate);
          } else {
            return false;
          }
        }
      } else {
        return true;
      }
      return true;

    } catch (Exception e) {
      e.printStackTrace();
    }
    return true;
  }

  protected boolean checkDailyLimit(Daily daily, Calendar startDate, Calendar current) {
    if (daily != null) { // Neu tuy chon hang ngay da dc
                         // chon------------------------------
      if (daily.isEveryWeekDay()) { // Tra ve true neu tuy chon la hang
                                    // ngay-------------
        return true;
      } else { // Nguoc lai neu tuy chon theo boi cua so
               // ngay----------------------------
        int num = daily.getStep();
        int days = (int) dateTimeConverter.countDayBetweenTwoDate(startDate, current);
        if (days % num == 0) { // Neu ngay tryen vao dung quy luat ap dung
          return true;
        } else { // Ngay tryen vao ko dung quy luat ap dung
          return false;
        }
      }
    } else {
      return true;
    }
  }

  /**
   * Kiem tra ngay nao do co thuoc quy luat ap dung theo tuan hay khong
   * 
   * @param saleOff
   * @param weekly
   * @param startDate
   * @param current
   * @return
   */
  protected boolean checkWeeklyLimit(Weekly weekly, Calendar startDate, Calendar current) {
    if (weekly != null) {
      int step = weekly.getStep();
      int day = current.get(Calendar.DAY_OF_WEEK);
      int weeks = dateTimeConverter.countWeeksBetweenTwoMonths(startDate, current);
      /**
       * Neu so tuan hien tai la tuan dau hoac so tuan hien tai-1 chia het cho
       * step thi tien hanh kiem tra ngay trong tuan
       */
      if ((weeks == 1) || (((weeks - 1) % step == 0) || (step == 0))) {
        List<Integer> dayOfWeek = weekly.dayOfWeeks();
        for (int i = 0; i < dayOfWeek.size(); i++) {
          int mid = dayOfWeek.get(i);
          if (day == mid) {
            return true;
          }
        }
        return false; // Nếu qua các vòng for mà ko hợp lệ --> false
      } else {
        return false;
      }
    } else {
      return true;
    }
  }

  public boolean checkMonthlyLimit(Monthly monthly, Calendar startDate, Calendar current) {
    if (monthly != null) {
      int calendarDay = current.get(Calendar.DAY_OF_MONTH);
      Date date = current.getTime();
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      int level = monthly.getLevel();
      int day = monthly.getDayRepeat();
      int step = monthly.getStep();
      int dayOfWeek = monthly.getDayOfWeek();
      int months = dateTimeConverter.countMonthsBetweenTwoMonths(startDate, calendar);
      boolean isNolevel = monthly.isNoLevel();
      if (isNolevel) {// Neu la tuy chon theo ngay
        if (((step == 0) || (months % step == 0)) && (day == calendarDay)) {
          return true;
        } else {
          return false;
        }
      } else {
        if (level == 5) {
          level = current.getActualMaximum(Calendar.DAY_OF_WEEK_IN_MONTH);
        }
        boolean value = dateTimeConverter.isOrder(current, dayOfWeek, level);
        if (((step == 0) || (months % step == 0)) && (value == true)) {
          return true;
        } else {
          return false;
        }
      }
    } else {
      return true;
    }
  }

  /**
   * Kiem tra tinh hop le cua nam
   * 
   * @param yearly
   * @param startDate
   * @param current
   * @return
   */
  public boolean checkYearlyLimit(Yearly yearly, Calendar startDate, Calendar current) {
    if (yearly != null) {
      int currentMonth = current.get(Calendar.MONTH) + 1;
      int dayOfWeekCurrent = current.get(Calendar.DAY_OF_WEEK);
      int currentDay = current.get(Calendar.DAY_OF_MONTH);
      int month = yearly.getMonth();
      boolean isEveryday = yearly.isEveryDay();
      if (month == currentMonth) {
        if (isEveryday) { // Neu tuy chon theo ngay
          int day = yearly.getDayOfMonth();
          if ((day == currentDay)) {
            return true;
          }
          return false;
        } else { // Neu tuy chon theo quy luat phan cap
          int level = yearly.getLevel();
          int dayLevel = yearly.getDayLevel();
          if (level == 5) {// Neu la tuy chon cuoi cung
            if (dayLevel == 0) {// Neu la tuy chon ngay thu level
              level = current.getActualMaximum(Calendar.DAY_OF_MONTH);
              return doValidateByDay(currentDay, level);
            } else if (dayLevel == 1) {// Neu la tuy chon cac ngay trong tuan
                                       // thu level
              level = current.getActualMaximum(Calendar.WEEK_OF_MONTH);
              int levelWeekCurrent = current.get(Calendar.WEEK_OF_MONTH);// Tuan
                                                                         // thu
                                                                         // may
                                                                         // trong
                                                                         // thang
                                                                         // hien
                                                                         // tai
              return doValidateByEveryDay(levelWeekCurrent, level);
            } else if (dayLevel == 2) {// Neu la tuy chon 2 ngay cuoi tuan thu
                                       // level
              if (dayOfWeekCurrent == 1 || dayOfWeekCurrent == 7) {
                if (dateTimeConverter.isLastDay(current, dayOfWeekCurrent)) {
                  return true;
                } else {
                  return false;
                }
              }
            }

          } else { // Khong phai tuy chon cuoi cung
            if (dayLevel == 0) {// Neu la tuy chon ngay thu level
              return doValidateByDay(currentDay, level);
            } else if (dayLevel == 1) {// Neu la tuy chon cac ngay trong tuan
                                       // thu level
              int levelWeekCurrent = current.get(Calendar.WEEK_OF_MONTH);// Tuan
                                                                         // thu
                                                                         // may
                                                                         // trong
                                                                         // thang
                                                                         // hien
                                                                         // tai
              return doValidateByEveryDay(levelWeekCurrent, level);
            } else if (dayLevel == 2) {// Neu la tuy chon 2 ngay cuoi tuan thu
                                       // level
              if (dayOfWeekCurrent == 1 || dayOfWeekCurrent == 7) {
                if (dateTimeConverter.isOrder(current, dayOfWeekCurrent, level)) {
                  return true;
                } else {
                  return false;
                }
              }
            }
          }
        }
      } else {
        return false;
      }
    } else {
      return true;
    }
    return false;
  }

  private boolean doValidateByDay(int currentDay, int level) {
    if (currentDay == level) { // Neu ngay hien tai trung voi quy luat phan cap
      return true;
    } else {
      return false;
    }
  }

  private boolean doValidateByEveryDay(int levelWeekCurrent, int level) {
    if (levelWeekCurrent == level) {
      return true;
    } else {
      return false;
    }
  }

  public void deleteTest(String test) throws Exception {
    clientCore.deleteTest(test);
  }

}
