package com.hkt.module.warehouse.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.hkt.module.core.entity.AbstractPersistable;

@Table
@Entity
public class IdentityProductAttribute extends AbstractPersistable<Long> {
  private static final long serialVersionUID = 1L;
  
  private String name;
  private String value;
  private Date startDate;
  private Date endDate;
  
  public IdentityProductAttribute() {} 
  
  public IdentityProductAttribute(String name, String value) {
    this.name = name ;
    this.value = value ;
  }
  
  public Date getCreatedDate() {
    return startDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.startDate = createdDate;
  }

  public String getName() { return name; }
  
  public void setName(String name) { this.name = name; }
  
  public String getValue() { return value; }
  
  public void setValue(String value) { this.value = value; }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }  
  
}