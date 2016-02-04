package com.hkt.module.promotion.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table
@Entity
public class PromotionGiveaways extends Promotion{

  private String productCode;
  private double quantityBuy;
  private double quantityGive;
  private int priority;
  
  public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getProductCode() {
    return productCode;
  }

  public void setProductCode(String productCode) {
    this.productCode = productCode;
  }

  public double getQuantityBuy() {
    return quantityBuy;
  }

  public void setQuantityBuy(double quantityBuy) {
    this.quantityBuy = quantityBuy;
  }

  public double getQuantityGive() {
    return quantityGive;
  }

  public void setQuantityGive(double quantityGive) {
    this.quantityGive = quantityGive;
  }

}