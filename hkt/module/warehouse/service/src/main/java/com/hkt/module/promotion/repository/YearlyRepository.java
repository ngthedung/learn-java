package com.hkt.module.promotion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hkt.module.promotion.entity.Yearly;

public interface YearlyRepository extends JpaRepository<Yearly, Long> {

  @Query("SELECT y FROM Yearly y WHERE (y.idTimeOption = :idTimeOption and y.recycleBin = 0) ORDER BY y.id DESC")
  List<Yearly> getByTimeOptionId(@Param("idTimeOption") long idTimeOption);
  
  @Query("SELECT y FROM Yearly y WHERE y.recycleBin = :valueBoolean ORDER BY y.id DESC")
  List<Yearly> findYearlyByRecycleBin(@Param("valueBoolean") boolean valueBoolean);
  
}
