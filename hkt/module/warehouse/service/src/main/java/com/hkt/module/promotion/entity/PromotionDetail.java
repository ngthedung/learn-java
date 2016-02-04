package com.hkt.module.promotion.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table
public class PromotionDetail extends Promotion {
  private double appliedToMinExpense;
  private double maxExpense;
  private boolean haveProduct;
  private boolean haveCustomer;
  private boolean haveLocation;
  private String storeCode;
  @Embedded
  @OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "promotionId", nullable = false, updatable = true)
  @OrderColumn
  private List<ProductTarget> productTargets;

  @Embedded
  @OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "promotionId", nullable = false, updatable = true)
  @OrderColumn
  private List<CustomerTarget> customerTargets;

  @Embedded
  @OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "promotionId", nullable = false, updatable = true)
  @OrderColumn
  private List<LocationTarget> locationTargets;

  public PromotionDetail() {
    productTargets = new ArrayList<ProductTarget>();
    customerTargets = new ArrayList<CustomerTarget>();
    locationTargets = new ArrayList<LocationTarget>();
    haveProduct = false;
    haveCustomer = false;
    haveLocation = false;
  }

  public List<ProductTarget> getProductTargets() {
    return productTargets;
  }

  public boolean isHaveProduct() {
    return haveProduct;
  }

  public void setHaveProduct(boolean haveProduct) {
    this.haveProduct = haveProduct;
  }

  public boolean isHaveCustomer() {
    return haveCustomer;
  }

  public void setHaveCustomer(boolean haveCustomer) {
    this.haveCustomer = haveCustomer;
  }

  public boolean isHaveLocation() {
    return haveLocation;
  }

  public void setHaveLocation(boolean haveLocation) {
    this.haveLocation = haveLocation;
  }

  public void setProductTargets(List<ProductTarget> productTargets) {
    this.productTargets = productTargets;
  }

  public void add(ProductTarget productTarget) {
    if (productTargets == null) {
      productTargets = new ArrayList<ProductTarget>();
    }
    productTargets.add(productTarget);
  }

  public List<CustomerTarget> getCustomerTargets() {
    return customerTargets;
  }

  public void setCustomerTargets(List<CustomerTarget> customerTargets) {
    this.customerTargets = customerTargets;
  }

  public void add(CustomerTarget customerTarget) {
    if (customerTargets == null) {
      customerTargets = new ArrayList<CustomerTarget>();
    }
    customerTargets.add(customerTarget);
  }

  public List<LocationTarget> getLocationTargets() {
    return locationTargets;
  }

  public void setLocationTargets(List<LocationTarget> locationTargets) {
    this.locationTargets = locationTargets;
  }

  public void add(LocationTarget locationTarget) {
    if (locationTargets == null) {
      locationTargets = new ArrayList<LocationTarget>();
    }
    locationTargets.add(locationTarget);
  }

  @JsonIgnore
  public boolean isNew() {
    return null == getId();
  }

  public double getAppliedToMinExpense() {
    return appliedToMinExpense;
  }

  public void setAppliedToMinExpense(double appliedToMinExpense) {
    this.appliedToMinExpense = appliedToMinExpense;
  }

  public double getMaxExpense() {
    return maxExpense;
  }

  public void setMaxExpense(double maxExpense) {
    this.maxExpense = maxExpense;
  }

  public String getStoreCode() {
    return storeCode;
  }

  public void setStoreCode(String storeCode) {
    this.storeCode = storeCode;
  }
  
  
}
