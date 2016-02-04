package com.hkt.module.promotion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.promotion.entity.Coupon;

public interface CouponRepository extends CrudRepository<Coupon, Long> {

  Coupon getCouponByCode(String code);
  
  @Query("SELECT c FROM Coupon c WHERE c.recycleBin = :valueBoolean")
  List<Coupon> findCouponByRecycleBin(@Param("valueBoolean") boolean valueBoolean);
  
  @Modifying
  @Query("DELETE FROM Coupon WHERE name like %:test%")
  public void deleteTest(@Param("test") String test);  
}
