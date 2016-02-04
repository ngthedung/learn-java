package com.hkt.module.partner.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.partner.customer.entity.CreditTransaction;

public interface CreditTransactionRepository extends CrudRepository<CreditTransaction, Long> {
	
  @Query("SELECT c FROM CreditTransaction c WHERE c.creditId = :creditId and c.recycleBin = 0 ORDER BY c.id DESC")
  List<CreditTransaction> findByCreditId(@Param("creditId") Long creditId);	
  
  CreditTransaction getByInvoiceId(long invoiceId);

  //khanhdd
  @Query("SELECT c FROM CreditTransaction c WHERE  c.recycleBin = :value")
  List<CreditTransaction> findByValueRecycleBin(@Param("value") boolean value); 
  
  @Modifying
  @Query(value = "DELETE FROM CreditTransaction WHERE creditId = :creditId")
  public void deleteByCreditId(@Param("creditId") Long creditId);
  
  @Modifying
  @Query(value = "DELETE FROM CreditTransaction WHERE loginId = :loginId")
  public void deleteByLoginId(@Param("loginId") String loginId);
  
  @Modifying
  @Query(value = "DELETE FROM CreditTransaction WHERE invoiceId = :invoiceId")
  public void deleteByInvoiceId(@Param("invoiceId") Long invoiceId);
  
  @Modifying
  @Query("DELETE FROM CreditTransaction WHERE loginId like %:test%")
  public void deleteTest(@Param("test") String test);
}
