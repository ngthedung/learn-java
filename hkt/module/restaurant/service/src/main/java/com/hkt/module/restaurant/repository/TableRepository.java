package com.hkt.module.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.Table;

public interface TableRepository extends CrudRepository<Table, Long> {

  @Query("SELECT t FROM Table t WHERE t.locationCode = :locationCode and t.recycleBin = 0")	
  List<Table> findByLocationCode(@Param("locationCode") String locationCode);
  
  Table getTableByCode(String code);
  
  @Query("SELECT t FROM Table t WHERE  t.recycleBin = :value")
  List<Table> findByValueRecycleBin(@Param("value") boolean value); 
  
  @Modifying
  @Query("DELETE FROM Table WHERE name like %:test%")
  public void deleteTest(@Param("test") String test);

}
