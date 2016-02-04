package com.hkt.module.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.hkt.module.restaurant.entity.Shift;

public interface ShiftRepository extends CrudRepository<Shift, Long>{
	Shift getShiftByCode(String code);
	
	@Query("SELECT s FROM Shift s WHERE  s.recycleBin = :value")
	  List<Shift> findByValueRecycleBin(@Param("value") boolean value);
}
