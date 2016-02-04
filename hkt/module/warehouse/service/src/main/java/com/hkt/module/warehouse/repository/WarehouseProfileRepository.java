package com.hkt.module.warehouse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hkt.module.warehouse.entity.WarehouseProfile;

public interface WarehouseProfileRepository extends JpaRepository<WarehouseProfile, Long>,WarehouseProfileRepositoryCustom {
  
  @Query("SELECT i FROM WarehouseProfile i WHERE i.productCode = :productCode and i.code = :code ORDER BY i.id DESC")
  WarehouseProfile getWarehouseProfileByProduct(@Param("productCode") String productCode,@Param("code") String code);
  
  @Query("SELECT i FROM WarehouseProfile i WHERE i.recycleBin = :valueBoolean ORDER BY i.id DESC")
  List<WarehouseProfile> findWarehouseProfileByRecycleBin(@Param("valueBoolean") boolean valueBoolean);

  
  @Modifying
  @Query("DELETE FROM WarehouseProfile WHERE warehouseCode like %:test%")
  public void deleteTest(@Param("test") String test);
  
}
