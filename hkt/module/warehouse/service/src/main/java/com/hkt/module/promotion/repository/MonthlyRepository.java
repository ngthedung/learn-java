package com.hkt.module.promotion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hkt.module.promotion.entity.Monthly;

public interface MonthlyRepository extends JpaRepository<Monthly, Long> {
  
  @Query("SELECT d FROM Monthly d WHERE (d.idTimeOption = :idTimeOption and d.recycleBin = 0) ORDER BY d.id DESC")
  List<Monthly> getByTimeOptionId(@Param("idTimeOption") long idTimeOption);
  
  @Query("SELECT d FROM Monthly d WHERE d.recycleBin = :valueBoolean ORDER BY d.id DESC")
  List<Monthly> findMonthlyByRecycleBin(@Param("valueBoolean") boolean valueBoolean); 
}
