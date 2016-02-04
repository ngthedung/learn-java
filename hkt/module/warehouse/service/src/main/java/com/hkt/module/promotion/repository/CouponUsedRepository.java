package com.hkt.module.promotion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.promotion.entity.CouponUsed;

public interface CouponUsedRepository extends CrudRepository<CouponUsed, Long>{
  
	@Query("SELECT c FROM CouponUsed c WHERE c.couponCode = :couponCode AND c.recycleBin = 0")
  List<CouponUsed> findByCouponCode(@Param("couponCode") String couponCode);
  
	@Query("SELECT c FROM CouponUsed c WHERE c.invoiceId = :invoiceId AND c.recycleBin = 0")
	List<CouponUsed> findByinvoiceId(@Param("invoiceId") long invoiceId );
	
	@Query("SELECT c FROM CouponUsed c WHERE c.couponCode = :couponCode AND c.invoiceId = :invoiceId AND c.recycleBin = 0 ORDER BY c.id DESC")
  CouponUsed getCouponUsed(@Param("couponCode") String couponCode,@Param("invoiceId") long invoiceId );
	
  @Query("SELECT c FROM CouponUsed c WHERE c.recycleBin = :valueBoolean")
  List<CouponUsed> findCouponUsedByRecycleBin(@Param("valueBoolean") boolean valueBoolean);
  
  @Modifying
  @Query(value = "DELETE FROM CouponUsed WHERE invoiceId = :invoiceId")
  public void deleteByInvoiceId(@Param("invoiceId") long invoiceId);
}
