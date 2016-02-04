package com.hkt.module.promotion.entity;

import java.util.Date;

import javax.persistence.MappedSuperclass;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.hkt.module.core.entity.AbstractPersistable;

@MappedSuperclass
public class Promotion extends AbstractPersistable<Long> {
  
  private static final long serialVersionUID = 1L;
  
  static public enum Status { Planned, Active, Expired }
  
  private String name;
  private String organizationLoginId;
  private Date   fromDate;
  private Date   toDate;
  private Status status = Status.Active;
  private long   timeOptionId;
  private int    maxUsePerCustomer;
  private double discountRate;
  private double discount; 
  private String currencyUnit;
  private String description;
  private boolean cumulative = false;
  private String typePromotion;
 
  public Promotion(){
    
  }
  
  public String getTypePromotion() {
    return typePromotion;
  }

  public void setTypePromotion(String typePromotion) {
    this.typePromotion = typePromotion;
  }

  public boolean isCumulative() {
    return cumulative;
  }

  public void setCumulative(boolean cumulative) {
    this.cumulative = cumulative;
  }

  public long getTimeOptionId() {
    return timeOptionId;
  }
  public void setTimeOptionId(long timeOptionId) {
    this.timeOptionId = timeOptionId;
  }

  @JsonIgnore
  public boolean isNew() { return null == getId(); }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getOrganizationLoginId() {
    return organizationLoginId;
  }
  
  public void setOrganizationLoginId(String organizationLoginId) {
    this.organizationLoginId = organizationLoginId;
  }

  public Date getFromDate() {
    return fromDate;
  }
  
  public void setFromDate(Date fromDate) {
    this.fromDate = fromDate;
  }
  
  public Date getToDate() {
    return toDate;
  }
  
  public void setToDate(Date toDate) {
    this.toDate = toDate;
  }
  
  public Status getStatus() {
    return status;
  }
  
  public void setStatus(Status status) {
    this.status = status;
  }
  
  public int getMaxUsePerCustomer() {
    return maxUsePerCustomer;
  }
  
  public void setMaxUsePerCustomer(int maxUsePerCustomer) {
    this.maxUsePerCustomer = maxUsePerCustomer;
  }
  
  public double getDiscountRate() {
    return discountRate;
  }
  
  public void setDiscountRate(double discountRate) {
    this.discountRate = discountRate;
  }
  
  public double getDiscount() {
    return discount;
  }
  
  public void setDiscount(double discount) {
    this.discount = discount;
  }
  
  public String getCurrencyUnit() {
    return currencyUnit;
  }
  
  public void setCurrencyUnit(String currencyUnit) {
    this.currencyUnit = currencyUnit;
  }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  @Override
	public String toString() {
		return name;
	}
  
  public void set(Promotion promotion) {
    super.set(promotion) ;
    setName(promotion.getName());;
    setOrganizationLoginId(promotion.getOrganizationLoginId());
    setFromDate(promotion.getFromDate());
    setToDate(promotion.getToDate());
    setStatus(promotion.getStatus());
    setMaxUsePerCustomer(promotion.getMaxUsePerCustomer());
    setDiscountRate(promotion.getDiscountRate());
    setDiscount(promotion.getDiscount());
    setCurrencyUnit(promotion.getCurrencyUnit());
    setDescription(promotion.getDescription());
  }
  
}
