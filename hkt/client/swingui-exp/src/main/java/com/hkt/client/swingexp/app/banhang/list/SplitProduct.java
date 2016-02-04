package com.hkt.client.swingexp.app.banhang.list;

public class SplitProduct {
	private String product;
	private String quantity;
	private String unit1;
	private String finalCharge;
	private String unit2;
	private String date;
	private String employee;
	private String table;
	private String partner;
	private String status;
	private String note;
	private String invoiceCode;
	public SplitProduct() {}
	public SplitProduct(String product,String invoiceCode, String quantity, String unit1,
			String finalCharge, String unit2, String date, String employee,
			String table, String partner, String status, String note) {
		this.product = product;
		this.invoiceCode = invoiceCode;
		this.quantity = quantity;
		this.unit1 = unit1;
		this.finalCharge = finalCharge;
		this.unit2 = unit2;
		this.date = date;
		this.employee = employee;
		this.table = table;
		this.partner = partner;
		this.status = status;
		this.note = note;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getUnit1() {
		return unit1;
	}
	public void setUnit1(String unit1) {
		this.unit1 = unit1;
	}
	public String getFinalCharge() {
		return finalCharge;
	}
	public void setFinalCharge(String finalCharge) {
		this.finalCharge = finalCharge;
	}
	public String getUnit2() {
		return unit2;
	}
	public void setUnit2(String unit2) {
		this.unit2 = unit2;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getEmployee() {
		return employee;
	}
	public void setEmployee(String employee) {
		this.employee = employee;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getInvoiceCode() {
  	return invoiceCode;
  }
	public void setInvoiceCode(String invoiceCode) {
  	this.invoiceCode = invoiceCode;
  }
	
	
}
