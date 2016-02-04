package com.hkt.module.restaurant.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.hkt.module.core.entity.AbstractPersistable;

@Table
@Entity
public class LocationAttribute extends AbstractPersistable<Long> {
  private static final long serialVersionUID = 1L;
  
  private String name;
  private String value;
  
  public LocationAttribute() {} 
  
  public LocationAttribute(String name, String value) {
    this.name = name ;
    this.value = value ;
  }
  
  public String getName() { return name; }
  
  public void setName(String name) { this.name = name; }
  
  public String getValue() { return value; }
  
  public void setValue(String value) { this.value = value; }
}