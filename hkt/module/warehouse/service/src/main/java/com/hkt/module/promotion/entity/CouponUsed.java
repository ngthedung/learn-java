package com.hkt.module.promotion.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.hkt.module.core.entity.AbstractPersistable;

@Table
@Entity
public class CouponUsed extends AbstractPersistable<Long> {

  private static final long serialVersionUID = 1L;
  private long couponId;
  private long invoiceId;
  private String couponName;
  private String couponCode;
  private Date usedDate;
  private double total;
  private String unit;

  public long getCouponId() {
    return couponId;
  }

  public long getInvoiceId() {
    return invoiceId;
  }

  public void setInvoiceId(long invoiceId) {
    this.invoiceId = invoiceId;
  }
  
  public double getTotal() {
    return total;
  }

  public void setTotal(double total) {
    this.total = total;
  }

  public void setCouponId(long couponId) {
    this.couponId = couponId;
  }

  public String getCouponName() {
    return couponName;
  }

  public void setCouponName(String couponName) {
    this.couponName = couponName;
  }

  public String getCouponCode() {
    return couponCode;
  }

  public void setCouponCode(String couponCode) {
    this.couponCode = couponCode;
  }

  public Date getUsedDate() {
    return usedDate;
  }

  public void setUsedDate(Date usedDate) {
    this.usedDate = usedDate;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  @Override
  public String toString() {
    return couponName;
  }

}
