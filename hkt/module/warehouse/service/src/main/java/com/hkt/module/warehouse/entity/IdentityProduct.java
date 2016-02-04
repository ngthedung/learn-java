package com.hkt.module.warehouse.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.hkt.module.core.entity.AbstractPersistable;

@Table(indexes = { @Index(columnList = "invoiceCode"), @Index(columnList = "warehouseCode") })
@Entity
public class IdentityProduct extends AbstractPersistable<Long> {
	private static final long	serialVersionUID	= 1L;

	static public enum ImportType {
		Import, NotImport
	}

	static public enum ExportType {
		Export, NotExport
	}

	@NotNull
	private String													invoiceCode;
	private String													invoiceExportCode;
	private long														invoiceItemIdImport;
	private long														invoiceItemIdExport;
	private String													productCode;
	private double													price;
	private double													priceExport;
	private double													profit;
	private double                          currencyRate;
	private double                          currencyExportRate;
	private String													unitPrice;
	private String													warehouseCode;
	private String													provider;
	private Date														dateMenufacture;
	private Date														dateExpiry;
	private String 													name;

	@OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "identityProductId", nullable = false, updatable = false)
	@OrderColumn
	private List<IdentityProductAttribute>	identityProductAttributes;
	private String													shipmentImportCode;
	private String													shipmentExportCode;
	private Date														importDate;
	private Date														exportDate;
	@Enumerated(EnumType.STRING)
	private ImportType											importType;
	@Enumerated(EnumType.STRING)
	private ExportType											exportType;

	public IdentityProduct() {

	}

	public double getCurrencyRate() {
    return currencyRate;
  }

  public void setCurrencyRate(double currencyRate) {
    this.currencyRate = currencyRate;
  }

  public double getPriceExport() {
		return priceExport;
	}

	public double getCurrencyExportRate() {
    return currencyExportRate;
  }

  public void setCurrencyExportRate(double currencyExportRate) {
    this.currencyExportRate = currencyExportRate;
  }

  public void setPriceExport(double priceExport) {
		//profit = priceExport - price;
		this.priceExport = price;
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceImportCode) {
		this.invoiceCode = invoiceImportCode;
	}

	public long getInvoiceItemIdImport() {
		return invoiceItemIdImport;
	}

	public void setInvoiceItemIdImport(long invoiceItemIdImport) {
		this.invoiceItemIdImport = invoiceItemIdImport;
	}

	public long getInvoiceItemIdExport() {
		return invoiceItemIdExport;
	}

	public void setInvoiceItemIdExport(long invoiceItemIdExport) {
		this.invoiceItemIdExport = invoiceItemIdExport;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getShipmentImportCode() {
		return shipmentImportCode;
	}

	public void setShipmentImportCode(String shipmentImportCode) {
		this.shipmentImportCode = shipmentImportCode;
	}

	public String getShipmentExportCode() {
		return shipmentExportCode;
	}

	public void setShipmentExportCode(String shipmentExportCode) {
		this.shipmentExportCode = shipmentExportCode;
	}

	public Date getImportDate() {
		return importDate;
	}

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

	public Date getExportDate() {
		return exportDate;
	}

	public void setExportDate(Date exportDate) {
		this.exportDate = exportDate;
	}

	public ImportType getImportType() {
		return importType;
	}

	public void setImportType(ImportType importType) {
		this.importType = importType;
	}

	public ExportType getExportType() {
		return exportType;
	}

	public void setExportType(ExportType exportType) {
		this.exportType = exportType;
	}

	public List<IdentityProductAttribute> getIdentityProductAttributes() {
		return identityProductAttributes;
	}

	public void setIdentityProductAttributes(List<IdentityProductAttribute> identityProductAttributes) {
		this.identityProductAttributes = identityProductAttributes;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		profit = priceExport - price;
		this.price = price;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public Date getDateMenufacture() {
		return dateMenufacture;
	}

	public void setDateMenufacture(Date dateMenufacture) {
		this.dateMenufacture = dateMenufacture;
	}

	public Date getDateExpiry() {
		return dateExpiry;
	}

	public void setDateExpiry(Date dateExpiry) {
		this.dateExpiry = dateExpiry;
	}

	public String getInvoiceExportCode() {
		return invoiceExportCode;
	}

	public void setInvoiceExportCode(String invoiceExportCode) {
		this.invoiceExportCode = invoiceExportCode;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return productCode;
	}

}
