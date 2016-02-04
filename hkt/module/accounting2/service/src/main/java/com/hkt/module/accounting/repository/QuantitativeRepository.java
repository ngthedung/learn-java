package com.hkt.module.accounting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.accounting.entity.Quantitative;
import com.hkt.module.accounting.entity.Quantitative.Type;

public interface QuantitativeRepository extends CrudRepository<Quantitative, Long> {
  
	@Query("SELECT q FROM Quantitative q WHERE q.type = :type AND q.recycleBin = 0")
  List<Quantitative> findByType(@Param("type") Type type);

	@Query("SELECT q FROM Quantitative q WHERE q.invoiceCode = :invoiceCode AND q.recycleBin = 0")
  List<Quantitative> findByInvoiceCode(@Param("invoiceCode") String invoiceCode);

	@Query("SELECT q FROM Quantitative q WHERE q.invoiceItemCode = :invoiceItemCode AND q.recycleBin = 0")
  List<Quantitative> findByInvoiceItemCode(@Param("invoiceItemCode") String invoiceItemCode);
  
	@Query("SELECT q FROM Quantitative q WHERE q.recycleBin = :valueBoolean")
  List<Quantitative> findQuantitativeByRecycleBin(@Param("valueBoolean") boolean valueBoolean);
	
  Quantitative getByQuantitativeCode(String quantitativeCode);
  
  @Modifying
	@Query("DELETE FROM Quantitative WHERE invoiceItemCode = :ItemCode ")
	public void deleteByinvoiceItemCode(@Param("ItemCode") String ItemCode);
  
  @Modifying
  @Query("DELETE FROM Quantitative WHERE invoiceCode = :invoiceCode ")
  public void deleteByinvoiceCode(@Param("invoiceCode") String invoiceCode);
  
}
