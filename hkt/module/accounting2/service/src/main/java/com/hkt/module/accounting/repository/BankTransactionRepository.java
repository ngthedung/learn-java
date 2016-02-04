package com.hkt.module.accounting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.accounting.entity.BankTransaction;

public interface BankTransactionRepository extends CrudRepository<BankTransaction, Long> {

	@Query(value = "SELECT bt FROM BankTransaction bt WHERE (LCASE(bt.code) = :code and bt.recycleBin = 0)")
	List<BankTransaction> getByCode(@Param("code") String code);
	
	@Query(value = "SELECT bt FROM BankTransaction bt WHERE (LCASE(bt.name) LIKE %:name% and bt.recycleBin = 0)")
	List<BankTransaction> findByName(@Param("name") String name);
	
	@Query(value = "SELECT bt FROM BankTransaction bt WHERE bt.total > :total and bt.recycleBin = 0)")
	List<BankTransaction> findByRangeTotal(@Param("total") long total);
	
	@Query(value = "SELECT bt FROM BankTransaction bt WHERE (LCASE(bt.bankCode) LIKE %:bankCode% and bt.recycleBin = 0)")
	List<BankTransaction> findByBankCode(@Param("bankCode") String bankCode);
	
	@Query(value = "SELECT bt FROM BankTransaction bt WHERE (LCASE(bt.transactionCode) LIKE %:transactionCode% and bt.recycleBin = 0)")
	List<BankTransaction> findByTracsactionCode(@Param("transactionCode") String transactionCode);
	
	@Query(value = "SELECT bt FROM BankTransaction bt WHERE bt.type = :type and bt.recycleBin = 0)")
	List<BankTransaction> findByType(@Param("type") boolean type);
	
	@Query(value = "SELECT bt FROM BankTransaction bt WHERE bt.recycleBin = :valueBoolean")
	List<BankTransaction> findByBankTracsactionRecycleBin(@Param("valueBoolean") boolean valueBoolean);
  
	@Modifying
	@Query("DELETE FROM BankTransaction WHERE name like %:test%")
	public void deleteTest(@Param("test") String test);
	
}
