package com.hkt.module.accounting.entity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.hkt.module.core.entity.AbstractPersistable;

@Table(indexes = {
	@Index(columnList = "name", name = "name_Index"),
	@Index(columnList = "value", name = "value_Index")})

@Entity
public class Attribute extends AbstractPersistable<Long> {
  private static final long serialVersionUID = 1L;

  private String            name;
  private String            value;

  public Attribute() {
  }

  public Attribute(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

}