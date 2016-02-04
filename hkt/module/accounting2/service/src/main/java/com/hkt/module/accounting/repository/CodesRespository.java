package com.hkt.module.accounting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.hkt.module.accounting.entity.ManageCodes;

public interface CodesRespository extends CrudRepository<ManageCodes, Long>{
	
	ManageCodes getCodesByCode(String code);
	
	@Query("SELECT c FROM ManageCodes c WHERE  c.recycleBin = :value")
	  List<ManageCodes> findByValueRecycleBin(@Param("value") boolean value); 
}
