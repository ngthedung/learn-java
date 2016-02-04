package com.hkt.client.swingexp.app.banhang.list;

public class GrossProduct {
	private String business;
	private String quantity;
	private String unit1;
	private String turnover;
	private String unit2;
	private String status;
	private String startDate;
	private String endDate;
	
	public GrossProduct() {}

	public GrossProduct(String business, String quantity, String unit1,
			String turnover, String unit2, String status, String startDate,
			String endDate) {
		this.business = business;
		this.quantity = quantity;
		this.unit1 = unit1;
		this.turnover = turnover;
		this.unit2 = unit2;
		this.status = status;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
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

	public String getTurnover() {
		return turnover;
	}

	public void setTurnover(String turnover) {
		this.turnover = turnover;
	}

	public String getUnit2() {
		return unit2;
	}

	public void setUnit2(String unit2) {
		this.unit2 = unit2;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
