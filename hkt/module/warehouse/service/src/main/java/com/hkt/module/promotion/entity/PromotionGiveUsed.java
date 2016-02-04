package com.hkt.module.promotion.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.hkt.module.core.entity.AbstractPersistable;

@Table
@Entity
public class PromotionGiveUsed extends AbstractPersistable<Long> {
  
  private static final long serialVersionUID = 1L;
  private long              promotionGiveawaysId;
  private long              invoiceId;
  private String            invoiceItemCode;
  private Date              usedDate;
  private double            price;
  private double            quantity;
  private String            productName;
  private String            productCode;
  private String            invoiceCode;
  
 
  public String getInvoiceCode() {
    return invoiceCode;
  }

  public void setInvoiceCode(String invoiceCode) {
    this.invoiceCode = invoiceCode;
  }

  public String getProductCode() {
    return productCode;
  }

  public void setProductCode(String productCode) {
    this.productCode = productCode;
  }

  public String getProductName() {
    return productName;
  }
  
  public long getInvoiceId() {
    return invoiceId;
  }

  public void setInvoiceId(long invoiceId) {
    this.invoiceId = invoiceId;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public long getPromotionGiveawaysId() {
    return promotionGiveawaysId;
  }

  public void setPromotionGiveawaysId(long promotionGiveawaysId) {
    this.promotionGiveawaysId = promotionGiveawaysId;
  }

  public String getInvoiceItemCode() {
    return invoiceItemCode;
  }

  public void setInvoiceItemCode(String invoiceItemCode) {
    this.invoiceItemCode = invoiceItemCode;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public double getQuantity() {
    return quantity;
  }

  public void setQuantity(double quantity) {
    this.quantity = quantity;
  }

  public Date getUsedDate() {
    return usedDate;
  }
  
  public void setUsedDate(Date usedDate) {
    this.usedDate = usedDate;
  }

  @Override
  public String toString() {
    return productName ;
  }
  
  
  
}
