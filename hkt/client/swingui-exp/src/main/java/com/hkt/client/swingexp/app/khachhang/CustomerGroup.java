package com.hkt.client.swingexp.app.khachhang;

import java.sql.Date;

public class CustomerGroup {
	private int id;
	private String code;
	private String name;
	private String phone;
	private String webside;
	private String national;
	private String city;
	private Date statday;
	private Date stopday;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getWebside() {
		return webside;
	}
	public void setWebside(String webside) {
		this.webside = webside;
	}
	public String getNational() {
		return national;
	}
	public void setNational(String national) {
		this.national = national;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Date getStatday() {
		return statday;
	}
	public void setStatday(Date statday) {
		this.statday = statday;
	}
	public Date getStopday() {
		return stopday;
	}
	public void setStopday(Date stopday) {
		this.stopday = stopday;
	}

}
