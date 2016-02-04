package com.hkt.module.accounting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.accounting.entity.BankAccount;
import com.hkt.module.accounting.entity.BankAccount.Type;

public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {
  
	@Query("SELECT b FROM BankAccount b WHERE b.type = :type and b.recycleBin = 0")
  List<BankAccount> findByType(@Param("type") Type type);
  
  @Query("SELECT b FROM BankAccount b WHERE b.recycleBin = :valueBoolean")
  List<BankAccount> findBankAccountByRecycleBin(@Param("valueBoolean") boolean valueBoolean);
	
  BankAccount getByBankAccountId(String bankAccountId);
  
  @Query("select a from BankAccount a where LCASE(a.bankAccountId) like %:bankAccountId%")
  List<BankAccount> findByBankAccount(@Param("bankAccountId") String bankAccountId);
}
