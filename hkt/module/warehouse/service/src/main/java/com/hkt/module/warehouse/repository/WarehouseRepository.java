package com.hkt.module.warehouse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.product.entity.ProductPrice;
import com.hkt.module.warehouse.entity.Warehouse;

public interface WarehouseRepository extends CrudRepository<Warehouse, Long> {

  @Query(value = "SELECT w FROM Warehouse w WHERE w.ownerId = :ownerId AND w.recycleBin = 0 ORDER BY w.id DESC")
  List<Warehouse> findByOwnerId(@Param("ownerId") String ownerId);

  @Query(value = "SELECT w FROM Warehouse w WHERE w.recycleBin = :valueBoolean ORDER BY w.id DESC")
  List<Warehouse> findByWarehouseRecycleBin(@Param("valueBoolean") boolean valueBoolean);
  
  Warehouse getByWarehouseId(String warehouseId);
  
  @Query(value = "SELECT w FROM Warehouse w WHERE LCASE(w.warehouseId) LIKE %:name% AND w.recycleBin = 0 ORDER BY w.id DESC")
  List<Warehouse> findByWarehouseId(@Param("name") String name);
  
  @Modifying
  @Query("DELETE FROM Warehouse WHERE name like %:test%")
  public void deleteTest(@Param("test") String test);
}
