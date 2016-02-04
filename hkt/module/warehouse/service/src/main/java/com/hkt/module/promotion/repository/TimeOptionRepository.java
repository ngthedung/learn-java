package com.hkt.module.promotion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hkt.module.promotion.entity.TimeOption;

public interface TimeOptionRepository extends JpaRepository<TimeOption, Long>  {
	
	@Query("SELECT t FROM TimeOption t WHERE t.recycleBin = :valueBoolean ORDER BY t.id DESC")
	List<TimeOption> findTimeOptionByRecycleBin(@Param("valueBoolean") boolean valueBoolean);
	
}
