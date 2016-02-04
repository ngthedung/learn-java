package com.hkt.module.accounting.entity;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.hkt.module.core.entity.AbstractPersistable;

@MappedSuperclass
public class Invoice extends AbstractPersistable<Long> {
	private static final long	serialVersionUID	= 1L;

	static public enum ActivityType {
		Payment, Receipt
	};

	static public enum Status {
		Paid, PartiallyPaid, WaitingPaid, PostPaid, ForRent
	};

	@NotNull
	private String				invoiceCode;
	@Enumerated(EnumType.STRING)
	private ActivityType	activityType;
	@Enumerated(EnumType.STRING)
	private Status				status;
	@NotNull
	private String				type;
	private String				invoiceName;

	/** Sum of InvoiceItem.total */
	private double				total;
	private double				discountByItem;
	private double				discount;
	private double				discountRate;
	private double				totalPaid;
	private double				totalTax;
	private double				service;
	private double				serviceRate;

	private double				point;
	private double				credit;
	private int						guest;

	private double				finalCharge;
	private double				currencyRate=1;
	private String				currencyUnit;
	private String				note;
	private String				description;
	private Date					startDate;
	private Date					endDate;

	private String				customerCode;
	private String				supplierCode;
	private String				employeeCode;
	private String				tableCode;
	private String				locationCode;
	private String				departmentCode;
	private String				groupCustomerCode;
	private String				warehouseId;
	private String				projectCode;
	private String				shiftCode;
	private String				storeCode;
	private Date					shiftDate;

	public double getService() {
		return service;
	}

	public void setService(double service) {
		this.service = service;
	}

	public double getServiceRate() {
		return serviceRate;
	}

	public void setServiceRate(double serviceRate) {
		this.serviceRate = serviceRate;
	}

	@JsonIgnore
	public boolean isNew() {
		return null == getId();
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public String codeView() {
		if (invoiceCode.indexOf(":") > 0) {
			return invoiceCode.substring(invoiceCode.indexOf(":") + 1);
		} else {
			return invoiceCode;
		}
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public int getGuest() {
		return guest;
	}

	public void setGuest(int guest) {
		this.guest = guest;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ActivityType getActivityType() {
		return activityType;
	}

	public String getInvoiceCode() {
		return invoiceCode;
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

	public void setInvoiceCode(String code) {
		this.invoiceCode = code;
	}

	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double amount) {
		this.total = amount;
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
	}

	public double getFinalCharge() {
		return finalCharge;
	}

	public void setFinalCharge(double finalCharge) {
		this.finalCharge = finalCharge;
	}

	public double getTotalPaid() {
		return totalPaid;
	}

	public void setTotalPaid(double totalPaid) {
		this.totalPaid = totalPaid;
	}

	public double getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(double totalTax) {
		this.totalTax = totalTax;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public double getDiscountByItem() {
		return discountByItem;
	}

	public void setDiscountByItem(double discountByItem) {
		this.discountByItem = discountByItem;
	}

	public String getInvoiceName() {
		return invoiceName;
	}

	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}

	public Date getShiftDate() {
		return shiftDate;
	}

	public void setShiftDate(Date shiftDate) {
		this.shiftDate = shiftDate;
	}

	public void set(Invoice invoice) {
		super.set(invoice);
		setType(invoice.getType());
		setInvoiceCode(invoice.getInvoiceCode());
		setActivityType(invoice.getActivityType());
		setTotal(invoice.getTotal());
		setCurrencyUnit(invoice.getCurrencyUnit());
		setStatus(invoice.getStatus());
		setDescription(invoice.getDescription());
		setNote(invoice.getNote());
		setDiscount(invoice.getDiscount());
		setDiscountRate(invoice.getDiscountRate());
		setFinalCharge(invoice.getFinalCharge());
		setTotalTax(invoice.getTotalTax());
		setStartDate(invoice.getStartDate());
		setDiscountByItem(invoice.getDiscountByItem());
		setEndDate(invoice.getEndDate());
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getGroupCustomerCode() {
		return groupCustomerCode;
	}

	public void setGroupCustomerCode(String groupCustomerCode) {
		this.groupCustomerCode = groupCustomerCode;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getShiftCode() {
		return shiftCode;
	}

	public void setShiftCode(String shiftCode) {
		this.shiftCode = shiftCode;
	}

	@Override
	public String toString() {
		return invoiceName;
	}

}