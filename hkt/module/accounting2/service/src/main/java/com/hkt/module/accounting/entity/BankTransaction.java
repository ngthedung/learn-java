package com.hkt.module.accounting.entity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.hkt.module.core.entity.AbstractPersistable;

@Entity
@Table(name = "accounting2_banktransaction", indexes={
    @Index(columnList="code")
})
public class BankTransaction extends AbstractPersistable<Long> {
	private static final long serialVersionUID = 1L;

	private String name;
	private String bankCode;
	private String transactionCode;
	private long total;
	private boolean type;
	private int priority;
	private String description;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public boolean isType() {
		return type;
	}

	public void setType(boolean type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIndex() {
		return priority;
	}

	public void setIndex(int priority) {
		this.priority = priority;
	}
}
