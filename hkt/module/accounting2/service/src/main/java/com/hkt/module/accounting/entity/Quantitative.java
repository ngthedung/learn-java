package com.hkt.module.accounting.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;

import com.hkt.module.core.entity.AbstractPersistable;

@Table(indexes = { 
    @Index(columnList = "productCode"), 
    @Index(columnList = "warehouseId"), 
    @Index(columnList = "type"),
    @Index(columnList = "date"),
    @Index(columnList = "quantitativeCode") })
@Entity
public class Quantitative extends AbstractPersistable<Long> {
  private static final long serialVersionUID = 1L;

  static public enum Type {
    Import, Export
  };

  private String quantitativeCode;
  private String invoiceCode;
  private String invoiceItemCode;
  private String productCode;
  private String productName;
  private double quantity;
  private String warehouseId;
  private double totalPrice;
  private double currencyRate;
  private String unitPrice;
  private Date   date;
  @Enumerated(EnumType.STRING)
  private Type   type;

  public String getInvoiceCode() {
    return invoiceCode;
  }

  public String getQuantitativeCode() {
    return quantitativeCode;
  }

  public void setQuantitativeCode(String quantitativeCode) {
    this.quantitativeCode = quantitativeCode;
  }

  public double getCurrencyRate() {
    return currencyRate;
  }

  public void setCurrencyRate(double currencyRate) {
    this.currencyRate = currencyRate;
  }

  public void setInvoiceCode(String invoiceCode) {
    this.invoiceCode = invoiceCode;
  }

  public String getInvoiceItemCode() {
    return invoiceItemCode;
  }

  public void setInvoiceItemCode(String invoiceItemCode) {
    this.invoiceItemCode = invoiceItemCode;
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

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public double getQuantity() {
    return quantity;
  }

  public void setQuantity(double quantity) {
    this.quantity = quantity;
  }

  public String getWarehouseId() {
    return warehouseId;
  }

  public void setWarehouseId(String warehouseId) {
    this.warehouseId = warehouseId;
  }

  public double getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(double totalPrice) {
    this.totalPrice = totalPrice;
  }

  public String getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(String unitPrice) {
    this.unitPrice = unitPrice;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }
}
