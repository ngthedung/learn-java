 package com.hkt.module.hr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.hr.entity.DailyWork;

public interface DailyWorkRepository extends CrudRepository<DailyWork, Long> {
	
  //khanhdd
  @Query("SELECT d FROM DailyWork d WHERE d.positionId = :positionId and d.recycleBin = 0")	
  List<DailyWork> findByPositionId(@Param("positionId") Long positionId);
  
  @Query("SELECT d FROM DailyWork d WHERE  d.recycleBin = :value")
  List<DailyWork> findByValueRecycleBin(@Param("value") boolean value); 
  
  @Modifying
  @Query(value = "DELETE FROM DailyWork WHERE loginId = :loginId")
  public void deleteByLoginId(@Param("loginId") String loginId);
  
  @Modifying
  @Query(value = "DELETE FROM DailyWork WHERE positionId = :positionId")
  public void deleteByPositionId(@Param("positionId") Long positionId);
}
