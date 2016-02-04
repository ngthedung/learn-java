package com.hkt.module.product.entity;

import java.util.List;

import com.hkt.module.promotion.entity.Promotion;
import com.hkt.module.promotion.entity.PromotionDetail;

public class ProductPriceDetail {
  private ProductPrice productPrice ;
  private List<PromotionDetail> promotions ;
  
  public ProductPrice getProductPrice() {
    return productPrice;
  }
  
  public void setProductPrice(ProductPrice productPrice) {
    this.productPrice = productPrice;
  }
  
  public List<PromotionDetail> getPromotions() {
    return promotions;
  }
  
  public void setPromotions(List<PromotionDetail> promotions) {
    this.promotions = promotions;
  }
  
  public PromotionDetail getPromotion(double total, int used){
    try {
      PromotionDetail promotion = promotions.get(0);
      double b;
      if(promotion.getDiscount()==0){
        b = promotion.getDiscountRate()*total/100d;
      }else {
        b = promotion.getDiscount();
      }
      for(PromotionDetail p : promotions){
        double a;
        if(p.getDiscount()==0){
          a = p.getDiscountRate()*total/100d;
        }else {
          a = p.getDiscount();
        }
        if(total<p.getMaxExpense()&&total>p.getAppliedToMinExpense()
            &&p.getMaxUsePerCustomer()>used && a>=b){
          promotion = p;
          b = a;
        }
      }
      return promotion;
    } catch (Exception e) {
      return null;
    }
  }
  
}
