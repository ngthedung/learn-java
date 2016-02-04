package com.hkt.module.partner.customer.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.hkt.module.core.entity.AbstractPersistable;

@Table
@Entity
public class Point extends AbstractPersistable<Long> {
  private static final long serialVersionUID = 1L;
  
  private String            loginId;
  private long              customerId;
  private double              point;
  
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
  
  public double getPoint() {
    return point;
  }
  
  public void setPoint(double point) {
    this.point = point;
  }
}
