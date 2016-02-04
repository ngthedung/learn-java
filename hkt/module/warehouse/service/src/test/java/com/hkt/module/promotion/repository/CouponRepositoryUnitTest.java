package com.hkt.module.promotion.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.promotion.entity.Coupon;
import com.hkt.module.warehouse.AbstractUnitTest;


@Transactional
public class CouponRepositoryUnitTest extends AbstractUnitTest {
  
  @Autowired
  CouponRepository couponRepository;
  
  @Test
  public void testCrud() {
    Coupon app = couponRepository.save(createCoupon());
    assertEquals(app, couponRepository.getCouponByCode("12545"));
    assertEquals(1, couponRepository.count());
    assertEquals(1, couponRepository.findCouponByRecycleBin(false).size());
    couponRepository.deleteTest("coupon");
    assertEquals(0, couponRepository.count());
  }
  
  public Coupon createCoupon(){
    Coupon coupon = new Coupon();
    coupon.setName("coupon");
    coupon.setCode("12545");
    coupon.setRecycleBin(false);
    return coupon;
  }

}
