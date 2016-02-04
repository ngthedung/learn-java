package com.hkt.client.swingexp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.WarehouseServiceClient;
import com.hkt.module.product.entity.Product;
import com.hkt.module.warehouse.entity.IdentityProduct;
import com.hkt.module.warehouse.entity.IdentityProduct.ImportType;
import com.hkt.module.warehouse.entity.ProductCodes;
import com.hkt.module.warehouse.entity.Warehouse;
import com.hkt.module.warehouse.entity.WarehouseInventory;
import com.hkt.module.warehouse.entity.WarehouseProfile;

public class WarehouseModelManager {
	private static WarehouseModelManager instance;
	private ClientContext clientContext = ClientContext.getInstance();
	private WarehouseServiceClient clientCore = clientContext.getBean(WarehouseServiceClient.class);
	public static String PRODUCT = "Hàng hóa";
	public static String GROUPPRODUCT = "Nhóm hàng hóa";
	public static String WAREHOUSE = "warehouse";
	public static String PROVIDER = "provider";
	public static String EMPLOYEE_IMPORT = "employeeimport";
	public static String EMPLOYEE_EXPORT = "employeeexport";
	public static String EMPLOYEE_CHANGE = "employeechange";

	public WarehouseModelManager() {

	}
	
	public double getPriceReport(String productCode){
		try {
	    return clientCore.getPriceReport(productCode);
    } catch (Exception e) {
    	e.printStackTrace();
	    return 0;
    }
	}

	public List<Warehouse> findWarehouses(){
		try {
			return clientCore.findWarehouses();
    } catch (Exception e) {
    	return new ArrayList<Warehouse>();
    }
		
	}
	
	public List<Product> getProductDelete(){
		try {
			return clientCore.getProductDelete();
    } catch (Exception e) {
	    return new ArrayList<Product>();
    }
	}
	
	public List<WarehouseInventory> findInventoryByCreatedBy(String createdBy) {
		try {
			return clientCore.findInventoryByCreatedBy(createdBy);
    } catch (Exception e) {
    	 return new ArrayList<WarehouseInventory>();
    }
	}

	public boolean deleteWarehouseInventory(WarehouseInventory warehouseInventory) {
		try {
			clientCore.deleteWarehouseInventory(warehouseInventory);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public Warehouse saveWarehouse(Warehouse warehouse) throws Exception {
		return clientCore.saveWarehouse(warehouse);
	}

	public boolean deleteWarehouseByWarehouseId(String warehouseId) throws Exception {
		return clientCore.deleteWarehouseByWarehouseId(warehouseId);
	}

	public Warehouse getWarehouseByWarehouseId(String warehouseId) throws Exception {
		return clientCore.getWarehouseByWarehouseId(warehouseId);
	}

	public IdentityProduct saveIdentityProduct(IdentityProduct identityProduct){
		try {
			return clientCore.saveIdentityProduct(identityProduct);
    } catch (Exception e) {
	    return null;
    }
		
	}

	public List<IdentityProduct> getByImportTypeAndWarehouse(ImportType importType, String warehouseCode)
	    throws Exception
	{
		if (warehouseCode == null)
			return clientCore.getByImportTypeAndWarehouseIsNull(importType);
		else
			return clientCore.getByImportTypeAndWarehouse(importType, warehouseCode);
	}
	
	public List<WarehouseInventory> findWarehouseInventoryNotUsed(ProductCodes listProductCode, Date date){
		try {
	    return clientCore.findWarehouseInventoryNotUsed(listProductCode, date);
    } catch (Exception e) {
	    return new ArrayList<WarehouseInventory>();
    }
	}

	public List<IdentityProduct> getByImportTypeAndProduct(ImportType importType, String productCode, String warehouseCode)
	    throws Exception
	{
		if (warehouseCode == null)
			return clientCore.getByImportTypeAndProductAndWarehouseIsNull(importType, productCode);
		else
			return clientCore.getByImportTypeAndProductAndWarehouse(importType, productCode, warehouseCode);
	}

	public List<IdentityProduct> getByExportTypeAndProduct(String productCode, String warehouseCode) throws Exception {
		if (warehouseCode == null)
			return clientCore.getByExportTypeAndProductAndWarehouseIsNull(productCode);
		else
			return clientCore.getByExportTypeAndProductAndWarehouse(productCode, warehouseCode);
	}

	public List<IdentityProduct> findByProductCodeAndExportTypeAndWarehoseCode(String productCode, String warehouseCode) {
		try {
			return clientCore.findByProductCodeAndExportTypeAndWarehoseCode(productCode, warehouseCode);
		} catch (Exception e) {
			return new ArrayList<IdentityProduct>();
		}
	}

	public List<IdentityProduct> getByInvoiceExportCode(String invoiceExportCode) throws Exception {
		return clientCore.getByInvoiceExportCode(invoiceExportCode);
	}

	public List<IdentityProduct> getByInvoiceCode(String invoiceCode) throws Exception {
		return clientCore.getByInvoiceCode(invoiceCode);
	}

	public List<IdentityProduct> getAllIdentityProduct() throws Exception {
		return clientCore.getAllIdentityProduct();
	}

	public List<IdentityProduct> findByProductCodeAndExportType(String productCode) throws Exception {
		return clientCore.findByProductCodeAndExportType(productCode);
	}

	public List<IdentityProduct> getByInvoiceItemIdExport(long invoiceItemIdExport) throws Exception {
		return clientCore.getByInvoiceItemIdExport(invoiceItemIdExport);
	}

	public List<IdentityProduct> getByInvoiceItemIdImport(long invoiceItemIdImport) throws Exception {
		return clientCore.getByInvoiceItemIdImport(invoiceItemIdImport);
	}

	public void deleteIdentityProduct(IdentityProduct identityProduct) throws Exception {
		clientCore.deleteIdentityProduct(identityProduct);
	}

	public List<IdentityProduct> getByshipmentImportCode(String shipmentImportCode) throws Exception {
		return clientCore.getByShipmentImportCode(shipmentImportCode);
	}

	public List<IdentityProduct> getByShipmentExportCode(String shipmentExportCode) throws Exception {
		return clientCore.getByShipmentExportCode(shipmentExportCode);
	}

	public IdentityProduct deleteIdentityProductExport(IdentityProduct identityProduct) {
		try {
			return clientCore.deleteIdentityProductExport(identityProduct);
		} catch (Exception e) {
			return null;
		}
	}

	static public WarehouseModelManager getInstance() {
		if (instance == null) {
			instance = new WarehouseModelManager();
		}
		return instance;
	}

	public void deleteTest(String test) throws Exception {
		clientCore.deleteTest(test);
	}

	public WarehouseInventory saveInventory(WarehouseInventory inventory) throws Exception {

		return clientCore.saveInventory(inventory);
	}

	public WarehouseInventory getInventoryByProduct(String productCode, String code) throws Exception {
		return clientCore.getInventoryByProduct(productCode, code);
	}

	public WarehouseProfile saveWarehouseProfile(WarehouseProfile inventory) throws Exception {
		return clientCore.saveWarehouseProfile(inventory);
	}

	public WarehouseProfile getWarehouseProfileByProduct(String productCode, String code) throws Exception {
		return clientCore.getWarehouseProfileByProduct(productCode, code);
	}
}
