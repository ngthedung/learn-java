package com.hkt.module.accounting.entity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.hkt.module.core.entity.AbstractPersistable;

@Entity
@Table(name = "accounting2_bank", indexes={
    @Index(columnList="code")
})
public class Bank extends AbstractPersistable<Long> {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String bankCode;
	private String parentCode;
	private int priority;
	private boolean showBank;
	private String description;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return priority;
	}

	public void setIndex(int index) {
		this.priority = index;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	
	@Override
	public String toString() {
		return name;
	}

	public boolean isShowBank() {
		return showBank;
	}

	public void setShowBank(boolean showBank) {
		this.showBank = showBank;
	}
}
