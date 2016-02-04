package com.hkt.module.accounting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.accounting.entity.Bank;

public interface BankRepository extends CrudRepository<Bank, Long> {

	Bank getByCode(String code);
  
	@Query(value = "SELECT b FROM Bank b WHERE (LCASE(b.parentCode) = :parentCode and b.recycleBin = 0)")
	List<Bank> findByParentBank(@Param("parentCode") String parentCode);
	
	@Query(value = "SELECT b FROM Bank b WHERE (LCASE(b.bankCode) = :bankCode and b.recycleBin = 0)")
	Bank findByBankCode(@Param("bankCode") String bankCode);
	
	@Query(value = "SELECT b FROM Bank b WHERE (LCASE(b.name) LIKE %:name% and b.recycleBin = 0)")
	List<Bank> findByName(@Param("name") String name);
	
	@Query(value = "SELECT b FROM Bank b WHERE b.recycleBin = :valueBoolean")
	List<Bank> findByBankRecycleBin (@Param("valueBoolean") boolean valueBoolean);
	
	@Modifying
	@Query("DELETE FROM Bank WHERE name like %:test%")
	public void deleteTest(@Param("test") String test);
	
}