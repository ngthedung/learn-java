package com.hkt.module.hr.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.hr.entity.WorkPosition;

public interface WorkPositionRepository extends CrudRepository<WorkPosition, Long> {
	
  //khanhdd
  @Query("SELECT w FROM WorkPosition w WHERE w.employeeId = :employeeId and w.recycleBin = 0")
  List<WorkPosition> findByEmployeeId(@Param("employeeId") Long employeeId);
  
  Page<WorkPosition> findByEmployeeId(Long employeeId, Pageable pageable);
  
  //khanhdd
  @Query("SELECT w FROM WorkPosition w WHERE  w.recycleBin = :value")
  List<WorkPosition> findByValueRecycleBin(@Param("value") boolean value); 
  
  @Modifying
  @Query(value = "DELETE FROM WorkPosition WHERE employeeId = :employeeId")
  public void deleteByEmployeeId(@Param("employeeId") Long employeeId);
}
