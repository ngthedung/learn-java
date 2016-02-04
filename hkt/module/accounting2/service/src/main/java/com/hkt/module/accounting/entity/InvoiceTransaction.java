package com.hkt.module.accounting.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;

import com.hkt.module.accounting.entity.InvoiceItem.ActivityType;
import com.hkt.module.core.entity.AbstractPersistable;

@Table(indexes = { @Index(columnList = "bankAccountId", name = "bankAccountId_Index"), @Index(columnList = "transactionType", name = "transactionType_Index"), @Index(columnList = "transactionDate", name = "transactionDate_Index") })
@Entity
public class InvoiceTransaction extends AbstractPersistable<Long> {
	private static final long	serialVersionUID	= 1L;

	static public enum TransactionType {
		Cash, Wire, CreditCard, CustomerCredit
	}

	static public enum ActivityType {
		Payment, Receipt
	};

	private String					bankAccountId;
	private TransactionType	transactionType;
	private Date						transactionDate;
	private double					total;
	private double					currencyRate=1;
	private String					currencyUnit;
	private String					description;
	private String					note;
	private String 					type = "";
	
	private String					customerCode;
	private String					employeeCode;
	private String					tableCode;
	private String					locationCode;
	private String					departmentCode;
	private String					groupCustomerCode;
	private String					warehouseId;
	private String					projectCode;
	private String					shiftCode;
	private String					storeCode;
	private String					supplierCode;
	private Date						shiftDate;
	
	@Enumerated(EnumType.STRING)
	private ActivityType		activityType;

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
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

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public String getBankAccountId() {
		return bankAccountId;
	}

	public void setBankAccountId(String bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public ActivityType getActivityType() {
		return activityType;
	}

	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public Date getShiftDate() {
		return shiftDate;
	}

	public void setShiftDate(Date shiftDate) {
		this.shiftDate = shiftDate;
	}

}
