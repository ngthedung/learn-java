package com.hkt.module.promotion.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.hkt.module.core.entity.AbstractPersistable;

@Table
@Entity
public class CustomerTarget extends AbstractPersistable<Long> {
  static public enum CustomerTargetType { ByGroup, ByCustomer }
  private String customerGroup ;
  private CustomerTargetType customerTargetType;

  public String getCustomerGroup() {
    return customerGroup;
  }

  public void setCustomerGroup(String customerGroup) {
    this.customerGroup = customerGroup;
  }

  public CustomerTargetType getCustomerTargetType() {
    return customerTargetType;
  }

  public void setCustomerTargetType(CustomerTargetType customerTargetType) {
    this.customerTargetType = customerTargetType;
  }
  
  
}
