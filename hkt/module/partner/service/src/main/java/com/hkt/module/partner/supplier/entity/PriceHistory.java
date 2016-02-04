package com.hkt.module.partner.supplier.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.hkt.module.core.entity.AbstractPersistable;

@Table
@Entity
public class PriceHistory extends AbstractPersistable<Long>{
  private static final long serialVersionUID = 1L;
  
  private String supplierLoginId ;
  private Long   suppliedProductId ;
  private double price ;
  private String note  ;
  
  public String getSupplierLoginId() { return supplierLoginId; }

  public void setSupplierLoginId(String supplierLoginId) { this.supplierLoginId = supplierLoginId; }

  public Long getSuppliedProductId() { return suppliedProductId; }
  
  public void setSuppliedProductId(Long suppliedProductId) { this.suppliedProductId = suppliedProductId; }
  
  public double getPrice() { return price; }
  
  public void setPrice(double price) { this.price = price; }
  
  public String getNote() { return note; }
  
  public void setNote(String note) { this.note = note; }
  
}
