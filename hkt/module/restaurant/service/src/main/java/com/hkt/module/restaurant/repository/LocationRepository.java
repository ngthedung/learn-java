package com.hkt.module.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.Location.Status;

public interface LocationRepository extends CrudRepository<Location, Long> {

  Location getLocationByCode(String code);

  //khanhdd
  @Query("SELECT p FROM Location p WHERE p.status = :status and p.recycleBin = 0")
  List<Location> findLocationByStatus(@Param("status") Status status);

  @Query("SELECT p FROM Location p " + "JOIN p.locationAttributes t " + "WHERE t.name = :name and p.recycleBin = 0 "
      + " AND t.value = :value")
  List<Location> findByAttribute(@Param("name") String name, @Param("value") String value);
  
  @Query("SELECT p FROM Location p WHERE  p.recycleBin = :value ")
  List<Location> findByValueRecycleBin(@Param("value") boolean value); 
  
  @Modifying
  @Query("DELETE FROM Location WHERE name like %:test%")
  public void deleteTest(@Param("test") String test);

}
