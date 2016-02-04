package com.hkt.module.warehouse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hkt.module.warehouse.entity.IdentityProduct;
import com.hkt.module.warehouse.entity.IdentityProduct.ExportType;
import com.hkt.module.warehouse.entity.IdentityProduct.ImportType;

public interface IdentityProductRepository extends JpaRepository<IdentityProduct, Long>, IdentityProductRepositoryCustom {

  @Query("SELECT i FROM IdentityProduct i" +
      " LEFT JOIN FETCH i.identityProductAttributes item" +
      " WHERE i.id = :id ORDER BY i.id DESC" 
   )
  IdentityProduct getIdentityProductById(@Param("id") Long id) ;
  
  @Query("SELECT price FROM IdentityProduct WHERE id = (select max(id) from IdentityProduct where productCode=:productCode)")
  double getPriceReport(@Param("productCode") String productCode);
  
  
	@Query("SELECT i FROM IdentityProduct i WHERE i.invoiceCode = :invoiceCode AND i.recycleBin = 0 ORDER BY i.id DESC")
	List<IdentityProduct> getByInvoiceCode(@Param("invoiceCode") String invoiceCode);

	@Query("SELECT i FROM IdentityProduct i WHERE i.importType = :importType AND i.recycleBin = 0 ORDER BY i.id DESC")
	List<IdentityProduct> getByImportType(@Param("importType") ImportType importType);

	@Query("SELECT i FROM IdentityProduct i WHERE i.importType = :importType AND i.exportType = :exportType AND i.productCode = :productCode AND i.recycleBin = 0 ORDER BY i.id DESC")
	List<IdentityProduct> getByImportTypeAndProductCode(@Param("importType") ImportType importType, @Param("exportType") ExportType exportType, @Param("productCode") String productCode);

	@Query("SELECT i FROM IdentityProduct i WHERE i.importType = :importType AND i.warehouseCode = :warehouseCode AND i.exportType = :exportType AND i.recycleBin = 0 ORDER BY i.id DESC")
	List<IdentityProduct> getByImportTypeAndWarehouse(@Param("importType") ImportType importType, @Param("warehouseCode") String warehouseCode, @Param("exportType") ExportType exportType);

	@Query("SELECT i FROM IdentityProduct i WHERE i.importType = :importType AND i.warehouseCode IS NULL AND i.exportType = :exportType AND i.recycleBin = 0 ORDER BY i.id DESC")
	List<IdentityProduct> getByImportTypeAndWarehouseIsNull(@Param("importType") ImportType importType, @Param("exportType") ExportType exportType);
	
	@Query("SELECT i FROM IdentityProduct i WHERE i.importType = :importType AND i.productCode = :productCode AND i.warehouseCode = :warehouseCode AND i.exportType = :exportType AND i.recycleBin = 0 ORDER BY i.id DESC")
	List<IdentityProduct> getByImportTypeAndProductAndWarehouse(@Param("importType") ImportType importType, @Param("productCode") String productCode, @Param("warehouseCode") String warehouseCode, @Param("exportType") ExportType exportType);

	@Query("SELECT i FROM IdentityProduct i WHERE i.importType = :importType AND i.productCode = :productCode AND i.warehouseCode IS NULL AND i.exportType = :exportType AND i.recycleBin = 0 ORDER BY i.id DESC")
	List<IdentityProduct> getByImportTypeAndProductAndWarehouseIsNull(@Param("importType") ImportType importType, @Param("productCode") String productCode, @Param("exportType") ExportType exportType);
	
	@Query("SELECT i FROM IdentityProduct i WHERE i.exportType = :exportType AND i.recycleBin = 0 ORDER BY i.id DESC")
	List<IdentityProduct> getByExportType(@Param("exportType") ExportType exportType);

	@Query("SELECT i FROM IdentityProduct i WHERE i.productCode = :productCode AND i.recycleBin = 0 ORDER BY i.id DESC")
	List<IdentityProduct> getByProductCode(@Param("productCode") String productCode);

	@Query("SELECT i FROM IdentityProduct i WHERE i.invoiceExportCode = :invoiceExportCode AND i.recycleBin = 0 ORDER BY i.id DESC")
	List<IdentityProduct> getByInvoiceExportCode(@Param("invoiceExportCode") String invoiceExportCode);

	@Query("SELECT i FROM IdentityProduct i WHERE i.productCode LIKE %:productCode% AND i.recycleBin = 0 ORDER BY i.id DESC")
	List<IdentityProduct> findByProductCode(@Param("productCode") String productCode);

	@Query("SELECT i FROM IdentityProduct i WHERE i.productCode = :productCode AND i.exportType = :exportType AND "
	    + "i.warehouseCode is null ORDER BY i.id DESC")
	List<IdentityProduct> findByProductCodeAndExportType(@Param("productCode") String productCode, @Param("exportType") ExportType exportType);
	
	@Query("SELECT i FROM IdentityProduct i WHERE i.productCode = :productCode AND i.exportType = :exportType AND "
      + "i.warehouseCode =:warehouseCode ORDER BY i.id DESC")
  List<IdentityProduct> findByProductCodeAndExportTypeAndWarehoseCode(@Param("productCode") String productCode, 
      @Param("exportType") ExportType exportType, @Param("warehouseCode") String warehouseCode);

	@Query("SELECT i FROM IdentityProduct i WHERE i.invoiceItemIdExport = :invoiceItemIdExport AND i.recycleBin = 0 ORDER BY i.id DESC")
	List<IdentityProduct> getByInvoiceItemIdExport(@Param("invoiceItemIdExport") long invoiceItemIdExport);

	@Query("SELECT i FROM IdentityProduct i WHERE i.invoiceItemIdImport = :invoiceItemIdImport AND i.recycleBin = 0 ORDER BY i.id DESC")
	List<IdentityProduct> getByInvoiceItemIdImport(@Param("invoiceItemIdImport") long invoiceItemIdImport);

	@Query("SELECT i FROM IdentityProduct i WHERE i.shipmentImportCode = :shipmentImportCode AND i.recycleBin = 0 ORDER BY i.id DESC")
	List<IdentityProduct> getByShipmentImportCode(@Param("shipmentImportCode") String shipmentImportCode);

	@Query("SELECT i FROM IdentityProduct i WHERE i.shipmentExportCode = :shipmentExportCode AND i.recycleBin = 0 ORDER BY i.id DESC")
	List<IdentityProduct> getByShipmentExportCode(@Param("shipmentExportCode") String shipmentExportCode);
	
	@Query("SELECT i FROM IdentityProduct i WHERE i.recycleBin = :valueBoolean ORDER BY i.id DESC")
	List<IdentityProduct> findIdentityProductByRecycleBin(@Param("valueBoolean") boolean valueBoolean);
	
	@Modifying
	@Query("DELETE FROM IdentityProduct WHERE productCode like %:test%")
	public void deleteTest(@Param("test") String test);

	@Modifying
	@Query("DELETE FROM IdentityProductAttribute WHERE identityProductId IN (SELECT id FROM IdentityProduct WHERE invoiceCode = :invoiceCode or invoiceExportCode = :invoiceCode)")
	public void deleteIdentityProductAttributeByInvoiceCode(@Param("invoiceCode") String invoiceCode);

	@Modifying
	@Query("DELETE FROM IdentityProduct WHERE invoiceCode = :invoiceCode or invoiceExportCode = :invoiceCode")
	public void deleteByInvoiceCode(@Param("invoiceCode") String invoiceCode);

	@Modifying
	@Query("DELETE FROM IdentityProduct WHERE invoiceItemIdImport = :invoiceItemId or invoiceItemIdExport = :invoiceItemId")
	public void deleteByInvoiceItemId(@Param("invoiceItemId") long invoiceItemId);

}
