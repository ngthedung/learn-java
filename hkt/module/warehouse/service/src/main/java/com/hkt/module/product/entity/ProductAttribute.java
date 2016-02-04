package com.hkt.module.product.entity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.hkt.module.core.entity.AbstractPersistable;

@Table(indexes={
    @Index(columnList = "name"),
    @Index(columnList = "value")
})
@Entity
public class ProductAttribute extends AbstractPersistable<Long>{
  private String name;
  private String value;
  
  public ProductAttribute() {} 
  
  public ProductAttribute(String name, String value) {
    this.name = name ;
    this.value = value ;
  }
  
  public String getName() { return name; }
  
  public void setName(String name) { this.name = name; }
  
  public String getValue() { return value; }
  
  public void setValue(String value) { this.value = value; }
}