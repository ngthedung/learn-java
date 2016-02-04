package com.hkt.module.warehouse.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hkt.module.product.entity.Product;
import com.hkt.module.warehouse.entity.WarehouseInventory;

public interface InventoryRepository extends JpaRepository<WarehouseInventory, Long>, InventoryRepositoryCustom {
  
  @Query("SELECT i FROM WarehouseInventory i WHERE i.productCode = :productCode and i.code = :code ORDER BY i.id DESC" )
  WarehouseInventory getInventoryByProduct(@Param("productCode") String productCode,@Param("code") String code);
  
  @Query("SELECT i FROM WarehouseInventory i WHERE i.recycleBin = :valueBoolean ORDER BY i.id DESC")
  List<WarehouseInventory> findInventoryByRecycleBin(@Param("valueBoolean") boolean valueBoolean);
  
  @Query("SELECT i FROM WarehouseInventory i WHERE i.createdBy = :createdBy")
  List<WarehouseInventory> findInventoryByCreatedBy(@Param("createdBy") String createdBy);
  
  @Query("SELECT i FROM WarehouseInventory i WHERE i.productCode not in :productCodes "
  		+ "and i.startDate >=:date1 and i.startDate <=:date2 ")
  List<WarehouseInventory> findWarehouseInventoryNotUsed(@Param("productCodes") List<String> productCodes,
  		@Param("date1")Date date1,@Param("date2")Date date2);

  @Modifying
  @Query("DELETE FROM WarehouseInventory WHERE warehouseCode like %:test%")
  public void deleteTest(@Param("test") String test);
  
}
