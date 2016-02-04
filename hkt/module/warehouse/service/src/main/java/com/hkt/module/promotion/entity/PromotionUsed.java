package com.hkt.module.promotion.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.hkt.module.core.entity.AbstractPersistable;

@Table
@Entity
public class PromotionUsed extends AbstractPersistable<Long> {
  
  private static final long serialVersionUID = 1L;
  private long              promotionId;
  private String            customerLoginId;
  private long              invoiceId;
  private Date              usedDate;
  private double            expense;
  private double            saving;
  private double            quantity;
  private String            description;

  public String getDescription() {
    return description;
  }
  
  public double getQuantity() {
    return quantity;
  }

  public void setQuantity(double quantity) {
    this.quantity = quantity;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public long getPromotionId() {
    return promotionId;
  }
  
  public void setPromotionId(long promotionId) {
    this.promotionId = promotionId;
  }
  
  public String getCustomerLoginId() {
    return customerLoginId;
  }
  
  public void setCustomerLoginId(String customerLoginId) {
    this.customerLoginId = customerLoginId;
  }
  
  public long getInvoiceId() {
    return invoiceId;
  }
  
  public void setInvoiceId(long invoiceId) {
    this.invoiceId = invoiceId;
  }
  
  public Date getUsedDate() {
    return usedDate;
  }
  
  public void setUsedDate(Date usedDate) {
    this.usedDate = usedDate;
  }
  
  public double getExpense() {
    return expense;
  }
  
  public void setExpense(double expense) {
    this.expense = expense;
  }
  
  public double getSaving() {
    return saving;
  }
  
  public void setSaving(double saving) {
    this.saving = saving;
  }

  @Override
  public String toString() {
    return description;
  }
  
  
  
}
