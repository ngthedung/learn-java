package com.hkt.module.partner.supplier.entity;

import java.util.List;

public class SuppliedProductDetail {
  private SuppliedProduct product ;
  private List<PriceHistory> priceHistories ;
  
  public SuppliedProduct getProduct() {
    return product;
  }
  public void setProduct(SuppliedProduct product) {
    this.product = product;
  }
  public List<PriceHistory> getPriceHistories() {
    return priceHistories;
  }
  public void setPriceHistories(List<PriceHistory> priceHistories) {
    this.priceHistories = priceHistories;
  }
  
}
