package com.hkt.module.promotion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hkt.module.promotion.entity.Daily;

public interface DailyRepository extends JpaRepository<Daily, Long>  {
  
  @Query("SELECT d from Daily d WHERE (d.idTimeOption = :idTimeOption and d.recycleBin = 0)")
  List<Daily> getByTimeOptionId(@Param("idTimeOption")long idTimeOption);
 
  @Query("SELECT d from Daily d WHERE d.recycleBin = :valueBoolean")
  List<Daily> findDailyByRecycleBin(@Param("valueBoolean") boolean valueBoolean);
}
