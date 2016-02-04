  package com.hkt.module.partner.supplier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.partner.customer.entity.Credit;
import com.hkt.module.partner.customer.entity.CreditTransaction;
import com.hkt.module.partner.supplier.entity.PriceHistory;

public interface PriceHistoryRepository extends CrudRepository<PriceHistory, Long> {
	//khanhdd
  @Query("SELECT p FROM PriceHistory p WHERE  p.suppliedProductId = :suppliedProductId and p.recycleBin = 0")
  List<PriceHistory> findBySuppliedProductId(@Param("suppliedProductId") Long suppliedProductId); 	
  
  //khanhdd
  @Query("SELECT p FROM PriceHistory p WHERE p.recycleBin = :value")
  List<PriceHistory> findByValueRecycleBin(@Param("value") boolean value); 
  
  @Modifying
  @Query(value = "DELETE FROM PriceHistory WHERE suppliedProductId = :suppliedProductId")
  public void deleteBySuppliedProductId(@Param("suppliedProductId") Long suppliedProductId);
  
  @Modifying
  @Query(value = "DELETE FROM PriceHistory WHERE supplierLoginId = :supplierLoginId")
  public void deleteByLoginId(@Param("supplierLoginId") String supplierLoginId);
}
