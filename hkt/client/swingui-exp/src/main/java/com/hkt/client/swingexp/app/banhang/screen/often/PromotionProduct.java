package com.hkt.client.swingexp.app.banhang.screen.often;

import java.io.Serializable;

import com.hkt.client.swingexp.model.PromotionModelManager;
import com.hkt.module.promotion.entity.PromotionUsed;

public class PromotionProduct implements Serializable {
  private String name;
  private double quantity;
  private double unitPrice;
  private double total;
  private PromotionUsed promotionUsed;

  public PromotionProduct(PromotionUsed promotionUsed) {

    this.promotionUsed = promotionUsed;
    name = promotionUsed.getDescription();
    quantity = promotionUsed.getQuantity();
    unitPrice = promotionUsed.getSaving() / quantity;
    this.total = this.quantity * this.unitPrice;
  }

  public PromotionUsed getPromotionUsed() {
    return promotionUsed;
  }

  public void setPromotionUsed(PromotionUsed promotionUsed) {
    this.promotionUsed = promotionUsed;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getQuantity() {
    return quantity;
  }

  public void setQuantity(double quantity) {
    this.total = quantity * this.unitPrice;
    promotionUsed.setQuantity(quantity);
    promotionUsed.setSaving(total);
    this.quantity = quantity;
  }

  public double getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(double unitPrice) {
    this.total = this.quantity * unitPrice;
    promotionUsed.setQuantity(quantity);
    promotionUsed.setSaving(total);
    this.unitPrice = unitPrice;
  }

  public double getTotal() {
    return total;
  }

  public void setTotal(double total) {
    this.total = total;
  }

  public void savePromotion() {
    try {
      PromotionModelManager.getInstance().savePromotionUsed(promotionUsed);
    } catch (Exception e) {
     
    }
  }

  public void deletePromotion() {
    try {
      PromotionModelManager.getInstance().deletePromotionUsed(promotionUsed);
    } catch (Exception e) {
      
    }
  }

  @Override
  public String toString() {
    return name;
  }

}
