package com.hkt.module.accounting.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.hkt.module.core.entity.AbstractPersistable;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "bankAccountId" }))
public class BankAccount extends AbstractPersistable<Long> {
  private static final long serialVersionUID = 1L;

  static public enum Type {
    Money, CreditCard
  };

  @Column(unique = true)
  private String bankAccountId;
  private Type type;
  private String currency;
  private String address;
  private String employeeDelete;
  
  

  public String getEmployeeDelete() {
		return employeeDelete;
	}

	public void setEmployeeDelete(String employeeDelete) {
		this.employeeDelete = employeeDelete;
	}

	public String getBankAccountId() { return bankAccountId; }

  public void setBankAccountId(String bankAccountId) { this.bankAccountId = bankAccountId; }

  public Type getType() { return type; }

  public void setType(Type type) { this.type = type; }

  public String getCurrency() { return currency; }

  public void setCurrency(String currency) { this.currency = currency; }

  public String getAddress() { return address; }

  public void setAddress(String address) { this.address = address; }
}