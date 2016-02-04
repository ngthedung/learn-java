package com.hkt.client.rest.service;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hkt.client.rest.RESTClient;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.core.entity.ReportTable;
import com.hkt.module.core.entity.SQLSelectQuery;
import com.hkt.module.core.rest.Request;
import com.hkt.module.core.rest.Response;
import com.hkt.module.product.entity.Product;
import com.hkt.module.warehouse.entity.IdentityProduct;
import com.hkt.module.warehouse.entity.IdentityProduct.ExportType;
import com.hkt.module.warehouse.entity.IdentityProduct.ImportType;
import com.hkt.module.warehouse.entity.ProductCodes;
import com.hkt.module.warehouse.entity.Warehouse;
import com.hkt.module.warehouse.entity.WarehouseInventory;
import com.hkt.module.warehouse.entity.WarehouseProfile;

@Component
public class WarehouseServiceClient {
	@Autowired
	private RESTClient client;

	public ReportTable[] getReportTableInventory(SQLSelectQuery[] query) throws Exception {
		Request req = create("getReportTableInventory");
		req.addParam("query", query);
		Response res = client.POST(req);
		return res.getDataAs(ReportTable[].class);
	}

	Request create(String method) {
		return new Request("warehouse", "WarehouseService", method);
	}

	public void deleteWarehouseInventory(WarehouseInventory warehouseInventory) throws Exception {
		Request req = create("deleteWarehouseInventory");
		req.addParam("warehouseInventory", warehouseInventory);
		client.POST(req);
	}

	public List<Product> getProductDelete() throws Exception {
		Request req = create("getProductDelete");
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<Product>>() {
		});
	}
	
	public double getPriceReport(String productCode) throws Exception {
		Request req = create("getPriceReport");
		req.addParam("productCode", productCode);
		Response res = client.POST(req);
		return res.getDataAs(Double.class);
	}

	public List<WarehouseInventory> findWarehouseInventoryNotUsed(ProductCodes listProductCode,Date date) throws Exception {
		Request req = create("findWarehouseInventoryNotUsed");
		req.addParam("listProductCode", listProductCode);
		req.addParam("date", date);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<WarehouseInventory>>() {
		});
	}

	public List<WarehouseInventory> findInventoryByCreatedBy(String createdBy) throws Exception {
		Request req = create("findInventoryByCreatedBy");
		req.addParam("createdBy", createdBy);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<WarehouseInventory>>() {
		});
	}

	public List<Warehouse> findWarehouses() throws Exception {
		Request req = create("findWarehouses");
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<Warehouse>>() {
		});
	}

	public WarehouseInventory saveInventory(WarehouseInventory inventory) throws Exception {
		Request req = create("saveInventory");
		req.addParam("inventory", inventory);
		Response res = client.POST(req);
		return res.getDataAs(WarehouseInventory.class);
	}

	public WarehouseInventory getInventoryByProduct(String productCode, String code) throws Exception {
		Request req = create("getInventoryByProduct");
		req.addParam("productCode", productCode);
		req.addParam("code", code);
		Response res = client.POST(req);
		return res.getDataAs(WarehouseInventory.class);
	}

	public boolean deleteInventory(WarehouseInventory inventory) throws Exception {
		Request req = create("deleteInventory");
		req.addParam("inventory", inventory);
		client.POST(req);
		return true;
	}

	public WarehouseProfile saveWarehouseProfile(WarehouseProfile inventory) throws Exception {
		Request req = create("saveWarehouseProfile");
		req.addParam("inventory", inventory);
		Response res = client.POST(req);
		return res.getDataAs(WarehouseProfile.class);
	}

	public WarehouseProfile getWarehouseProfileByProduct(String productCode, String code) throws Exception {
		Request req = create("getWarehouseProfileByProduct");
		req.addParam("productCode", productCode);
		req.addParam("code", code);
		Response res = client.POST(req);
		return res.getDataAs(WarehouseProfile.class);
	}

	public boolean deleteWarehouseProfile(WarehouseProfile inventory) throws Exception {
		Request req = create("deleteWarehouseProfile");
		req.addParam("inventory", inventory);
		client.POST(req);
		return true;
	}

	public Warehouse saveWarehouse(Warehouse warehouse) throws Exception {
		Request req = create("saveWarehouse");
		req.addParam("warehouse", warehouse);
		Response res = client.POST(req);
		return res.getDataAs(Warehouse.class);
	}

	public boolean deleteWarehouseByWarehouseId(String warehouseId) throws Exception {
		Request req = create("deleteWarehouseByWarehouseId");
		req.addParam("warehouseId", warehouseId);
		client.POST(req);
		return true;
	}

	public IdentityProduct saveIdentityProduct(IdentityProduct identityProduct) throws Exception {
		Request req = create("saveIdentityProduct");
		req.addParam("identityProduct", identityProduct);
		Response res = client.POST(req);
		return res.getDataAs(IdentityProduct.class);
	}

	public List<IdentityProduct> getByImportType(ImportType importType) throws Exception {
		Request req = create("getByImportType");
		req.addParam("importType", importType);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<IdentityProduct>>() {
		});
	}

	public List<IdentityProduct> getByImportTypeAndWarehouse(ImportType importType, String warehouseCode)
	    throws Exception
	{
		Request req = create("getByImportTypeAndWarehouse");
		req.addParam("importType", importType);
		req.addParam("warehouseCode", warehouseCode);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<IdentityProduct>>() {
		});
	}

	public List<IdentityProduct> getByImportTypeAndWarehouseIsNull(ImportType importType) throws Exception {
		Request req = create("getByImportTypeAndWarehouseIsNull");
		req.addParam("importType", importType);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<IdentityProduct>>() {
		});
	}

	public List<IdentityProduct> findByProductCodeAndExportTypeAndWarehoseCode(String productCode, String warehouseCode)
	    throws Exception
	{
		Request req = create("findByProductCodeAndExportTypeAndWarehoseCode");
		req.addParam("productCode", productCode);
		req.addParam("warehouseCode", warehouseCode);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<IdentityProduct>>() {
		});
	}

	public List<IdentityProduct> getByImportTypeAndProductAndWarehouse(ImportType importType, String productCode,
	    String warehouseCode) throws Exception
	{
		Request req = create("getByImportTypeAndProductAndWarehouse");
		req.addParam("importType", importType);
		req.addParam("productCode", productCode);
		req.addParam("warehouseCode", warehouseCode);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<IdentityProduct>>() {
		});
	}

	public List<IdentityProduct> getByImportTypeAndProductAndWarehouseIsNull(ImportType importType, String productCode)
	    throws Exception
	{
		Request req = create("getByImportTypeAndProductAndWarehouseIsNull");
		req.addParam("importType", importType);
		req.addParam("productCode", productCode);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<IdentityProduct>>() {
		});
	}

	public List<IdentityProduct> getByExportTypeAndProductAndWarehouse(String productCode, String warehouseCode)
	    throws Exception
	{
		Request req = create("getByExportTypeAndProductAndWarehouse");
		req.addParam("productCode", productCode);
		req.addParam("warehouseCode", warehouseCode);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<IdentityProduct>>() {
		});
	}

	public List<IdentityProduct> getByExportTypeAndProductAndWarehouseIsNull(String productCode) throws Exception {
		Request req = create("getByExportTypeAndProductAndWarehouseIsNull");
		req.addParam("productCode", productCode);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<IdentityProduct>>() {
		});
	}

	public List<IdentityProduct> getByProductCode(String productCode) throws Exception {
		Request req = create("getByProductCode");
		req.addParam("productCode", productCode);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<IdentityProduct>>() {
		});
	}

	public List<IdentityProduct> getByExportType(ExportType exportType) throws Exception {
		Request req = create("getByExportType");
		req.addParam("importType", exportType);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<IdentityProduct>>() {
		});
	}

	public List<IdentityProduct> getByInvoiceExportCode(String invoiceExportCode) throws Exception {
		Request req = create("getByInvoiceExportCode");
		req.addParam("invoiceExportCode", invoiceExportCode);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<IdentityProduct>>() {
		});
	}

	public Warehouse getWarehouseByWarehouseId(String warehouseId) throws Exception {
		Request req = create("getWarehouseByWarehouseId");
		req.addParam("warehouseId", warehouseId);
		Response res = client.POST(req);
		return res.getDataAs(Warehouse.class);
	}

	public List<IdentityProduct> getByImportTypeAndProductCode(ImportType importType, String productCode)
	    throws Exception
	{
		Request req = create("getByImportTypeAndProductCode");
		req.addParam("importType", importType);
		req.addParam("productCode", productCode);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<IdentityProduct>>() {
		});
	}

	public List<IdentityProduct> getByInvoiceCode(String invoiceCode) throws Exception {
		Request req = create("getByInvoiceCode");
		req.addParam("invoiceCode", invoiceCode);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<IdentityProduct>>() {
		});
	}

	public List<IdentityProduct> getAllIdentityProduct() throws Exception {
		Request req = create("getAllIdentityProduct");
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<IdentityProduct>>() {
		});
	}

	public List<IdentityProduct> findByProductCodeAndExportType(String productCode) throws Exception {
		Request req = create("findByProductCodeAndExportType");
		req.addParam("productCode", productCode);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<IdentityProduct>>() {
		});
	}

	public List<IdentityProduct> getByInvoiceItemIdExport(long invoiceItemIdExport) throws Exception {
		Request req = create("getByInvoiceItemIdExport");
		req.addParam("invoiceItemIdExport", invoiceItemIdExport);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<IdentityProduct>>() {
		});
	}

	public List<IdentityProduct> getByInvoiceItemIdImport(long invoiceItemIdImport) throws Exception {
		Request req = create("getByInvoiceItemIdImport");
		req.addParam("invoiceItemIdImport", invoiceItemIdImport);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<IdentityProduct>>() {
		});
	}

	public List<IdentityProduct> getByShipmentImportCode(String shipmentImportCode) throws Exception {
		Request req = create("getByShipmentImportCode");
		req.addParam("shipmentImportCode", shipmentImportCode);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<IdentityProduct>>() {
		});
	}

	public List<IdentityProduct> getByShipmentExportCode(String shipmentExportCode) throws Exception {
		Request req = create("getByShipmentExportCode");
		req.addParam("shipmentExportCode", shipmentExportCode);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<IdentityProduct>>() {
		});
	}

	public void deleteIdentityProduct(IdentityProduct identityProduct) throws Exception {
		Request req = create("deleteIdentityProduct");
		req.addParam("identityProduct", identityProduct);
		client.POST(req);
	}

	public IdentityProduct deleteIdentityProductExport(IdentityProduct identityProduct) throws Exception {
		Request req = create("deleteIdentityProductExport");
		req.addParam("identityProduct", identityProduct);
		Response res = client.POST(req);
		return res.getDataAs(IdentityProduct.class);
	}

	public FilterResult<IdentityProduct> query(FilterQuery query) throws Exception {
		Request req = create("query");
		req.addParam("query", query);
		Response res = client.POST(req);
		return res.getDataAsByFilter(new TypeReference<FilterResult<IdentityProduct>>() {
		});
	}

	public ReportTable[] reportQuery(SQLSelectQuery[] query) throws Exception {
		Request req = create("reportQuery");
		req.addParam("query", query);
		Response res = client.POST(req);
		return res.getDataAs(ReportTable[].class);
	}

	public void deleteTest(String test) throws Exception {
		Request req = create("deleteTest");
		req.addParam("test", test);
		client.POST(req);
	}

}