package com.hkt.module.product.util;

import java.util.List;

import com.hkt.module.product.entity.ProductDetail;
import com.hkt.module.product.entity.ProductPrice;
import com.hkt.module.product.entity.ProductPriceType;
import com.hkt.module.product.entity.ProductTag;
import com.hkt.module.product.entity.Tax;
import com.hkt.util.IOUtil;
import com.hkt.util.json.JSONReader;

public class ProductScenario {
  private List<ProductDetail> products;
  
  private List<ProductPrice> productPrices;
  
  private List<ProductPriceType> productPriceTypes;
  
  private List<ProductTag> productTags;
  
  private List<Tax> taxs;
  
  public List<ProductTag> getProductTags() { return productTags; }
  
  public void setProductTags(List<ProductTag> productTags) { this.productTags = productTags; }
  
  public List<Tax> getTaxs() { return taxs; }
  
  public void setTaxs(List<Tax> taxs) { this.taxs = taxs; }
  
  public List<ProductPrice> getProductPrices() { return productPrices; }
  
  public void setProductPrices(List<ProductPrice> productPrices) { this.productPrices = productPrices; }
  
  public List<ProductPriceType> getProductPriceTypes() { return productPriceTypes; }

  public void setProductPriceTypes(List<ProductPriceType> productPriceTypes) { this.productPriceTypes = productPriceTypes; }

  public List<ProductDetail> getProducts() { return products; }
  
  public void setProducts(List<ProductDetail> products) { this.products = products; }
  
  static public ProductScenario load(String res) throws Exception {
    JSONReader reader = new JSONReader(IOUtil.loadRes(res));
    ProductScenario scenario = reader.read(ProductScenario.class);
    return scenario;
  }
  
  static public ProductScenario loadTest() throws Exception {
    return load("classpath:scenario/product.json");
  }
}
