package com.hkt.module.accounting.entity;

import java.util.ArrayList;
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

@Table(indexes = { @Index(columnList = "startDate", name = "startDate_Index")
//									 @Index(columnList = "itemCode", name = "itemCode_Index"), 
//									 @Index(columnList = "invoiceCode", name = "invoiceCode_Index"), 
//									 @Index(columnList = "itemName", name = "itemName_Index") 
})

@Entity
public class InvoiceItem extends AbstractPersistable<Long> {
	private static final long	serialVersionUID	= 1L;
	public static final String  Report  = "have";

	static public enum ActivityType {
		Payment, Receipt
	};

	@NotNull
	private String											itemCode;
	private String											invoiceCode;
	private String											itemName;
	private String                      type;

	private double											quantity;
	private double											quantityReal;
	private double											unitPrice;
	private double											total;
	private double											discountRate;
	private double											discount;

	private double											point;
	private double											credit;
	private String											status;
	private String                      typeReport;

	private double											discountRateByInvoice;
	private double											discountByInvoice;

	private double											taxRate;
	private double											tax;

	private double											finalCharge;
	private double											currencyRate=1;
	private String											currencyUnit;

	private Date												startDate;
	private String											description;
	private String											productCode;
	private String											warehouseCode;
	@Enumerated(EnumType.STRING)
	private ActivityType								activityType;

	@OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "invoiceItemId", nullable = false, updatable = false)
	@OrderColumn
	private List<InvoiceItemAttribute>	references	= new ArrayList<InvoiceItemAttribute>();

	
	
	public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getTypeReport() {
    return typeReport;
  }

  public void setTypeReport(String typeReport) {
    this.typeReport = typeReport;
  }

  public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public double getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(double currencyRate) {
		this.currencyRate = currencyRate;
	}

	public String getCurrencyUnit() {
		return currencyUnit;
	}

	public void setCurrencyUnit(String currencyUnit) {
		this.currencyUnit = currencyUnit;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getDiscountByInvoice() {
		return discountByInvoice;
	}

	public void setDiscountByInvoice(double discountByInvoice) {
		this.discountByInvoice = discountByInvoice;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String desc) {
		this.description = desc;
	}

	public List<InvoiceItemAttribute> getReferences() {
		return references;
	}

	public void setReferences(List<InvoiceItemAttribute> references) {
		this.references = references;
	}

	public double getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
	}

	public double getDiscountRateByInvoice() {
		return discountRateByInvoice;
	}

	public void setDiscountRateByInvoice(double discountRateByInvoice) {
		this.discountRateByInvoice = discountRateByInvoice;
	}

	public double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	public double getFinalCharge() {
		return finalCharge;
	}

	public void setFinalCharge(double finalCharge) {
		this.finalCharge = finalCharge;
	}

	public void add(InvoiceItemAttribute item) {
		if (isNew() && references == null) {
			references = new ArrayList<InvoiceItemAttribute>();
		}
		references.add(item);
	}

	public double getPoint() {
		return point;
	}

	public void setPoint(double point) {
		this.point = point;
	}

	public double getCredit() {
		return credit;
	}

	public void setCredit(double credit) {
		this.credit = credit;
	}

	public void calculate(double quantity, double unitPrice, double itemDiscount, double invoiceDiscount, double tax) {
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.discount = itemDiscount;
		this.discountByInvoice = invoiceDiscount;
		this.tax = tax;
		this.total = quantity * unitPrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return itemName;
	}

	public double getQuantityReal() {
		return quantityReal;
	}

	public void setQuantityReal(double quantityReal) {
		this.quantityReal = quantityReal;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public ActivityType getActivityType() {
		return activityType;
	}

	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}

}
