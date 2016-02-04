package com.hkt.module.partner.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.partner.customer.entity.Credit;
import com.hkt.module.partner.customer.entity.Point;

public interface PointRepository extends CrudRepository<Point, Long>, PointRepositoryCustom {
  
  Point getByCustomerId(Long customerId);
  
  @Modifying
  @Query(value = "DELETE FROM Point WHERE customerId = :customerId")
  public void deleteByCustomerId(@Param("customerId") Long customerId);
  
  //khanhdd
  @Query("SELECT p FROM Point p WHERE  p.recycleBin = :value")
  List<Point> findByValueRecycleBin(@Param("value") boolean value); 
  
  @Modifying
  @Query("DELETE FROM Point "
      + "WHERE customerId in (SELECT id FROM Customer WHERE name like %:test%)")
  public void deleteTest(@Param("test") String test);
}
