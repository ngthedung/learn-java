package com.hkt.module.promotion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.promotion.entity.PromotionGiveUsed;

public interface PromotionGiveUsedRepository extends CrudRepository<PromotionGiveUsed, Long> {

	@Query("SELECT p FROM PromotionGiveUsed p WHERE (p.invoiceId = :invoiceId and p.recycleBin = 0) ORDER BY p.id DESC")
  List<PromotionGiveUsed> getPromotionGiveUsedsByInvoiceId(@Param("invoiceId") long invoiceId);
  
	@Query("SELECT p FROM PromotionGiveUsed p WHERE p.recycleBin = :valueBoolean")
	List<PromotionGiveUsed> findPromotionGiveUsedByRecycleBin(@Param("valueBoolean") boolean valueBoolean);
	
  PromotionGiveUsed getPromotionByInvoiceItemCode(String invoiceItemCode);
  
  @Modifying
  @Query(value = "DELETE FROM PromotionGiveUsed WHERE invoiceId = :invoiceId")
  public void deleteByInvoiceId(@Param("invoiceId") long invoiceId);
}
