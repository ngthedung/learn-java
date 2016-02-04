package com.hkt.module.promotion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.promotion.entity.PromotionUsed;

public interface PromotionUsedRepository extends CrudRepository<PromotionUsed, Long>, PromotionUsedRepositoryCustom {
  
	@Query("SELECT p FROM PromotionUsed p WHERE (p.promotionId = :promotionId and p.recycleBin = 0)")
	List<PromotionUsed> findByPromotionId(@Param("promotionId") long promotionId);
  
	@Query("SELECT p FROM PromotionUsed p WHERE (p.invoiceId = :invoiceId and p.recycleBin = 0)")
  List<PromotionUsed> findByInvoiceId(@Param("invoiceId") long invoiceId);
  
	@Query("SELECT p FROM PromotionUsed p WHERE p.recycleBin = :valueBoolean")
	List<PromotionUsed> findPromotionUsedByRecycleBin(@Param("valueBoolean") boolean valueBoolean);
	
  PromotionUsed getByCode(String code);
  
  @Modifying
  @Query(value = "DELETE FROM PromotionUsed WHERE promotionId = :promotionId")
  public void deleteByPromotionId(@Param("promotionId") long promotionId);
  
  @Modifying
  @Query(value = "DELETE FROM PromotionUsed WHERE invoiceId = :invoiceId")
  public void deleteByInvoiceId(@Param("invoiceId") long invoiceId);
  
  @Modifying
  @Query(value = "DELETE FROM PromotionUsed WHERE code = :code")
  public void deleteByCode(@Param("code") String code);
}
