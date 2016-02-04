package com.hkt.module.promotion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hkt.module.promotion.entity.Weekly;

public interface WeeklyRepository extends JpaRepository<Weekly, Long> {
  
  @Query("SELECT w from Weekly w WHERE w.idTimeOption = :idTimeOption and w.recycleBin = 0 ORDER BY w.id DESC")
  List<Weekly> getByTimeOptionId(@Param("idTimeOption") long idTimeOption);
  
  @Query("SELECT w FROM Weekly w WHERE w.recycleBin = :valueBoolean ORDER BY w.id DESC")
  List<Weekly> findWeeklyByRecycleBin(@Param("valueBoolean") boolean valueBoolean);
}
