package com.hkt.module.promotion.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hkt.module.promotion.entity.PromotionGiveaways;

public interface PromotionGiveawaysRepository extends JpaRepository<PromotionGiveaways, Long> {

	PromotionGiveaways getPromotionGiveawaysByCode(String code);

	@Query("SELECT a FROM PromotionGiveaways a WHERE (a.productCode = :productCode " 
	+ "and (a.toDate > :date or a.toDate is null) and a.fromDate <= :date) ORDER BY a.id DESC")
	PromotionGiveaways getPromotionGiveawaysByProductCode(@Param("productCode") String productCode, @Param("date") Date date);

	@Query("SELECT a FROM PromotionGiveaways a WHERE a.recycleBin = :valueBoolean ORDER BY a.id DESC")
	List<PromotionGiveaways> findPromotionGiveawaysByRecycleBin(@Param("valueBoolean") boolean valueBoolean); 
	
	@Modifying
	@Query("DELETE FROM PromotionGiveaways WHERE name like %:test%")
	public void deleteTest(@Param("test") String test);
}
