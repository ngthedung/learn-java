package com.hkt.module.accounting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.restaurant.entity.Project;

public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Long>, InvoiceDetailRepositoryCustom {
  
  @Query("SELECT i FROM InvoiceDetail i" +
           " LEFT JOIN FETCH i.items item" +
           " LEFT JOIN FETCH item.references" +
           " LEFT JOIN FETCH i.transactions" +
           " LEFT JOIN FETCH i.attributes" +
           " LEFT JOIN FETCH i.contributors" +
           " WHERE i.id = :id"
      	)
  InvoiceDetail getInvoiceDetailById(@Param("id") Long id) ;
  
  @Query("SELECT i FROM InvoiceDetail i" +
	         " LEFT JOIN FETCH i.items item" +
	         " LEFT JOIN FETCH item.references" +
	         " LEFT JOIN FETCH i.transactions" +
	         " LEFT JOIN FETCH i.attributes" +
	         " LEFT JOIN FETCH i.contributors" +
	         " WHERE i.id = :id"
       	)
  InvoiceDetail findOne(@Param("id") Long id) ;
  
  @Query("SELECT p FROM InvoiceDetail p WHERE p.type = :type and p.invoiceCode LIKE %:code% ORDER BY p.endDate DESC")
  List<InvoiceDetail> findInvoiceDetailByCode(@Param("code") String code, @Param("type") String type);
  
  @Query("SELECT MIN(EXTRACT(YEAR FROM i.startDate)) FROM InvoiceDetail i")
  String getStartDateLessInvoiceDetail();
  
  @Query("SELECT i FROM InvoiceDetail i" +
	          " LEFT JOIN FETCH i.items item" +
	          " LEFT JOIN FETCH item.references" +
	          " LEFT JOIN FETCH i.transactions" +
	          " LEFT JOIN FETCH i.attributes" +
	          " LEFT JOIN FETCH i.contributors" +
	          " WHERE i.invoiceCode = :invoiceCode"
  			)
  InvoiceDetail getInvoiceDetailByCode(@Param("invoiceCode") String invoiceCode) ;
  
  @Query("SELECT i FROM InvoiceDetail i" +
      " LEFT JOIN FETCH i.items item" +
      " LEFT JOIN FETCH item.references" +
      " LEFT JOIN FETCH i.transactions" +
      " LEFT JOIN FETCH i.attributes" +
      " LEFT JOIN FETCH i.contributors" +
      " WHERE i.invoiceName = :invoiceName"
	)
  List<InvoiceDetail> findInvoiceDetailByName(@Param("invoiceName") String invoiceName) ;
  
  @Query("SELECT i FROM InvoiceDetail i" +
		      " LEFT JOIN FETCH i.items item" +
		      " LEFT JOIN FETCH item.references" +
		      " LEFT JOIN FETCH i.transactions" +
		      " LEFT JOIN FETCH i.attributes" +
		      " LEFT JOIN FETCH i.contributors" +
		      " WHERE item.id = :id"
  			)
  InvoiceDetail findOneByInvoiceItem(@Param("id") Long id) ;
  
  @Query("SELECT i FROM InvoiceDetail i" +
      " LEFT JOIN FETCH i.items item" +
      " LEFT JOIN FETCH item.references" +
      " LEFT JOIN FETCH i.transactions" +
      " LEFT JOIN FETCH i.attributes" +
      " LEFT JOIN FETCH i.contributors c" +
      " WHERE i.customerCode = :customerCode and i.type = 'Trả Trước' and i.activityType = 'Payment'"
    )
  InvoiceDetail getInvoiceDetailByCredit(@Param("customerCode") String customerCode) ;
  
  @Query("SELECT i FROM InvoiceItem i" +
		      " LEFT JOIN FETCH i.references"+
		      " WHERE i.id = :id"
  			)
  InvoiceItem getInvoiceItemById(@Param("id") Long id) ;
  
  @Query("SELECT i FROM InvoiceDetail i" +
				  " LEFT JOIN FETCH i.contributors c" +
				  " WHERE c.identifierId = :identifierId"
				)
  List<InvoiceDetail> findInvoiceDetailByIdentifierId(@Param("identifierId") String identifierId);
  
  @Query("SELECT i FROM InvoiceDetail i" +
      	 	" LEFT JOIN FETCH i.attributes c" +
      	 	" WHERE c.value = :value"
    	 	)
  List<InvoiceDetail> findInvoiceDetailByAttributeValue(@Param("value") String value);
  
  @Query("SELECT i FROM InvoiceDetail i" +
      		" LEFT JOIN FETCH i.items item" +
      		" LEFT JOIN FETCH item.references c" +
      		" WHERE (c.value = :value OR c.author = :value)")
  List<InvoiceDetail> findInvoiceDetailByAttributeItemValue(@Param("value") String value);
  
  @Modifying
  @Query("DELETE FROM Contributor WHERE invoiceId IN (SELECT id FROM InvoiceDetail WHERE invoiceName LIKE %:test%)")
  public void deleteTestContributor(@Param("test") String test);
  
  @Modifying
  @Query("DELETE FROM InvoiceItemAttribute WHERE invoiceItemId IN" +
  				" (SELECT id FROM InvoiceItem WHERE invoiceId in" +
  				" (SELECT id FROM InvoiceDetail WHERE invoiceName LIKE %:test%))")
  public void deleteTestInvoiceItemAttribute(@Param("test") String test);
  
  @Modifying
  @Query("DELETE FROM InvoiceItem WHERE invoiceId IN" +
  				" (SELECT id FROM InvoiceDetail WHERE invoiceName LIKE %:test%)")
  public void deleteTestInvoiceItem(@Param("test") String test);
  
  @Modifying
  @Query("DELETE FROM Attribute WHERE invoiceId IN" +
  				" (SELECT id FROM InvoiceDetail WHERE invoiceName LIKE %:test%)")
  public void deleteTestAttribute(@Param("test") String test);
  
  @Modifying
  @Query("DELETE FROM InvoiceTransaction WHERE invoiceId IN" +
  				" (SELECT id FROM InvoiceDetail WHERE invoiceName LIKE %:test%)")
  public void deleteTestInvoiceTransaction(@Param("test") String test);
  
  @Modifying
  @Query("DELETE FROM InvoiceDetail WHERE invoiceName LIKE %:test%")
  public void deleteTest(@Param("test") String test);
}
