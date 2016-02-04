package com.hkt.module.partner.customer.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.hkt.module.core.entity.AbstractPersistable;

@Table
@Entity
public class Credit extends AbstractPersistable<Long> {
  private static final long serialVersionUID = 1L;
  
  static public enum UnitType {
    Point, Currency
  }
  
  private String   loginId;
  private long     customerId;
  private double   credit;
  private UnitType unitType;
  
  public String getLoginId() {
    return loginId;
  }
  
  public void setLoginId(String loginId) {
    this.loginId = loginId;
  }
  
  public long getCustomerId() {
    return customerId;
  }
  
  public void setCustomerId(long customerId) {
    this.customerId = customerId;
  }
  
  public double getCredit() {
    return credit;
  }
  
  public void setCredit(double credit) {
    this.credit = credit;
  }
  
  public UnitType getUnitType() {
    return unitType;
  }
  
  public void setUnitType(UnitType unitType) {
    this.unitType = unitType;
  }
  
}
