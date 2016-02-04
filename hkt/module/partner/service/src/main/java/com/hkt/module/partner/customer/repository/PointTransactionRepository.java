package com.hkt.module.partner.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.partner.customer.entity.Credit;
import com.hkt.module.partner.customer.entity.PointTransaction;

public interface PointTransactionRepository extends CrudRepository<PointTransaction, Long> {
  
  //khanhdd
  @Query("SELECT p FROM PointTransaction p WHERE p.pointId =:pointId and p.recycleBin = 0 ORDER BY p.id DESC")
  List<PointTransaction> findByPointId(@Param("pointId") Long pointId);
  
  @Query("SELECT p FROM PointTransaction p WHERE p.invoiceId =:invoiceId and p.point < 0")
  PointTransaction getByInvoiceId(@Param("invoiceId") long invoiceId);
  
  @Query("SELECT p FROM PointTransaction p WHERE p.invoiceId =:invoiceId")
  List<PointTransaction> findByInvoiceId(@Param("invoiceId") long invoiceId);
  
  //khanhdd
  @Query("SELECT p FROM PointTransaction p WHERE p.pointConversionRuleId =:pointConversionRuleId and p.recycleBin = 0")
  List<PointTransaction> findByPointConversionRuleId(@Param("pointConversionRuleId") Long pointConversionRuleId);
  
  @Modifying
  @Query(value = "DELETE FROM PointTransaction WHERE pointId = :pointId")
  public void deleteByPointId(@Param("pointId") Long pointId);
  
  @Modifying
  @Query(value = "DELETE FROM PointTransaction WHERE pointConversionRuleId = :pointConversionRuleId")
  public void deleteByPointConversionRuleId(@Param("pointConversionRuleId") Long pointConversionRuleId);
  
  @Modifying
  @Query(value = "DELETE FROM PointTransaction WHERE loginId = :loginId")
  public void deleteByLoginId(@Param("loginId") String loginId);
  
  @Modifying
  @Query(value = "DELETE FROM PointTransaction WHERE invoiceId = :invoiceId")
  public void deleteByInvoiceId(@Param("invoiceId") Long invoiceId);
  
  @Modifying
  @Query("DELETE FROM PointTransaction WHERE loginId like %:test%")
  public void deleteTest(@Param("test") String test);
 
  //khanhdd
  @Query("SELECT p FROM PointTransaction p WHERE  p.recycleBin = :value")
  List<PointTransaction> findByValueRecycleBin(@Param("value") boolean value); 
}
