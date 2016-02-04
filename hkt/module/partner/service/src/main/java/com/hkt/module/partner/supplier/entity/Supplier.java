package com.hkt.module.partner.supplier.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import com.hkt.module.core.entity.AbstractPersistable;
import com.hkt.module.partner.supplier.repository.SupplierRepositotyListener;

@Table
@Entity
@EntityListeners({ SupplierRepositotyListener.class })
public class Supplier extends AbstractPersistable<Long> {
	private static final long serialVersionUID = 1L;

	private String loginId;
	private String organizationLoginId;
	private String name;
	private String mobile;
	private String type;
	private String address;
	private Date birthDay;
	private String description;

	private boolean interactive;

	public boolean isInteractive() {
		return interactive;
	}

	public void setInteractive(boolean interactive) {
		this.interactive = interactive;
	}

	public String codeView() {
		if (getCode().indexOf(":") > 0) {
			return getCode().substring(getCode().indexOf(":") + 1);
		} else {
			return getCode();
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Supplier() {
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getOrganizationLoginId() {
		return organizationLoginId;
	}

	public void setOrganizationLoginId(String organizationLoginId) {
		this.organizationLoginId = organizationLoginId;
	}

	@Override
	public String toString() {
		if (name == null) {
			name = loginId;
		}
		return name;
	}

}
