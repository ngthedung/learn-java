package com.hkt.module.partner.supplier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.partner.customer.entity.CreditTransaction;
import com.hkt.module.partner.supplier.entity.SuppliedProduct;

public interface SuppliedProductRepository extends CrudRepository<SuppliedProduct, Long> {
	//khanhdd
  @Query("SELECT s FROM SuppliedProduct s WHERE s.supplierId = :supplierId and s.recycleBin = 0")
  List<SuppliedProduct> findBySupplierId(@Param("supplierId") Long supplierId);	
  
  //khanhdd
  @Query("SELECT s FROM SuppliedProduct s WHERE s.recycleBin = :value")
  List<SuppliedProduct> findByValueRecycleBin(@Param("value") boolean value); 
  
  SuppliedProduct getByCode(String code);
  
  @Modifying
  @Query(value = "DELETE FROM SuppliedProduct WHERE supplierId = :supplierId")
  public void deleteBySupplierId(@Param("supplierId") Long supplierId);
}
