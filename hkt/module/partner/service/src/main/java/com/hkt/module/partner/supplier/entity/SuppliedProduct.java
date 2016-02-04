package com.hkt.module.partner.supplier.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.hkt.module.core.entity.AbstractPersistable;

@Table
@Entity
public class SuppliedProduct extends AbstractPersistable<Long>{
  private static final long serialVersionUID = 1L;
  
  private Long   supplierId ;
  private String supplierLoginId;
  private String name ;
  private double lastPrice ;
  private String note ;
  
  public Long getSupplierId() { return supplierId; }
  
  public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }
  
  public String getSupplierLoginId() { return supplierLoginId; }

  public void setSupplierLoginId(String supplierLoginId) { this.supplierLoginId = supplierLoginId; }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  public String getName() { return name; }
  
  public void setName(String name) { this.name = name; }
  
  
  public double getLastPrice() { return lastPrice; }
  
  public void setLastPrice(double lastPrice) { this.lastPrice = lastPrice; }
  
  public String getNote() { return note; }
  
  public void setNote(String note) { this.note = note; }

  @Override
  public String toString() { return name ; }
  
}
