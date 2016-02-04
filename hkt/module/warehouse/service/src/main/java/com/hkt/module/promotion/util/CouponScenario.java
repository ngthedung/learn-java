package com.hkt.module.promotion.util;

import java.util.List;

import com.hkt.module.promotion.entity.Coupon;
import com.hkt.util.IOUtil;
import com.hkt.util.json.JSONReader;

public class CouponScenario {
  private List<Coupon> coupons;
  
  public List<Coupon> getCoupons() {
    return coupons;
  }
  
  public void setPromotions(List<Coupon> coupons) {
    this.coupons = coupons;
  }  
  
  static public CouponScenario load(String res) throws Exception {
    JSONReader reader = new JSONReader(IOUtil.loadRes(res)) ;
    CouponScenario scenario = reader.read(CouponScenario.class) ;
    return scenario ;
  }
  
  static public CouponScenario loadTest() throws Exception {
    return load("classpath:scenario/coupon.json") ;
  }
  
}
