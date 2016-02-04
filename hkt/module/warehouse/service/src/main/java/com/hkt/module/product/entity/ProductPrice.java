package com.hkt.module.product.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.hkt.module.core.entity.AbstractPersistable;

@Entity
@Table(name = "warehouse_productPrice")
public class ProductPrice extends AbstractPersistable<Long> {
  private static final long serialVersionUID = 1L;

  private String            organizationLoginId;
  @ManyToOne
  private Product           product;
  @ManyToOne
  private ProductPriceType  productPriceType;

  @ManyToOne
  private Tax               tax;

  private String            unit;
  private double            price;
  private String            currencyUnit;
  private int               minQuantity;
  private int               maxQuantity      = Integer.MAX_VALUE;
  private String            description;

  public String getOrganizationLoginId() {
    return organizationLoginId;
  }

  public void setOrganizationLoginId(String organizationLoginId) {
    this.organizationLoginId = organizationLoginId;
  }
  
  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public ProductPriceType getProductPriceType() {
    return productPriceType;
  }

  public void setProductPriceType(ProductPriceType productPriceType) {
    this.productPriceType = productPriceType;
  } 

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getCurrencyUnit() {
    return currencyUnit;
  }

  public void setCurrencyUnit(String currencyUnit) {
    this.currencyUnit = currencyUnit;
  }

  public int getMinQuantity() {
    return minQuantity;
  }

  public void setMinQuantity(int minQuantity) {
    this.minQuantity = minQuantity;
  }

  public int getMaxQuantity() {
    return maxQuantity;
  }

  public void setMaxQuantity(int maxQuantity) {
    this.maxQuantity = maxQuantity;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Tax getTax() {
    return tax;
  }

  public void setTax(Tax tax) {
    this.tax = tax;
  }
  
  @Override
  public String toString() {
    return productPriceType.toString();
  }
}
