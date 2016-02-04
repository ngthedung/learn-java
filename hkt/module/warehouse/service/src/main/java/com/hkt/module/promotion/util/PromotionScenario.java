package com.hkt.module.promotion.util;

import java.util.List;

import com.hkt.module.promotion.entity.PromotionDetail;
import com.hkt.module.promotion.entity.PromotionUsed;
import com.hkt.util.IOUtil;
import com.hkt.util.json.JSONReader;

public class PromotionScenario {
  private List<Promotions> promotions;
  
  public List<Promotions> getPromotions() {
    return promotions;
  }
  
  public void setPromotions(List<Promotions> promotions) {
    this.promotions = promotions;
  }
  
  static public class Promotions {
    private PromotionDetail               promotion;
    private List<PromotionUseds>    promotionUseds;
    
    public PromotionDetail getPromotion() {
      return promotion;
    }
    
    public void setPromotion(PromotionDetail promotion) {
      this.promotion = promotion;
    }
    
    public List<PromotionUseds> getPromotionUseds() {
      return promotionUseds;
    }
    
    public void setPromotionUseds(List<PromotionUseds> promotionUseds) {
      this.promotionUseds = promotionUseds;
    }
    
    static public class PromotionUseds {
      private PromotionUsed promotionUsed;
      
      public PromotionUsed getPromotionUsed() {
        return promotionUsed;
      }
      
      public void setPromotionUsed(PromotionUsed promotionUsed) {
        this.promotionUsed = promotionUsed;
      }
    }
    
  }
  
  static public PromotionScenario load(String res) throws Exception {
    JSONReader reader = new JSONReader(IOUtil.loadRes(res)) ;
    PromotionScenario scenario = reader.read(PromotionScenario.class) ;
    return scenario ;
  }
  
  static public PromotionScenario loadTest() throws Exception {
    return load("classpath:scenario/promotion.json") ;
  }
  
}
