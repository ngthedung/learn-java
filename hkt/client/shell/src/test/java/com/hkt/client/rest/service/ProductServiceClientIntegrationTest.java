package com.hkt.client.rest.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import com.hkt.client.rest.ClientContext;
import com.hkt.module.accounting.entity.Bank;
import com.hkt.module.accounting.entity.BankTransaction;
import com.hkt.module.product.entity.ProductPrice;
import com.hkt.module.product.entity.ProductPriceType;
import com.hkt.module.product.entity.Tax;

public class ProductServiceClientIntegrationTest {
  private ClientContext clientContext = ClientContext.getInstance();
  private ProductServiceClient client = clientContext.getBean(ProductServiceClient.class);
  
  @Test
  public void testRestaurantService() throws Exception {
    
    ProductPriceType productPriceType = client.getProductPriceTypeByType("Bang gia thuong");
    assertNotNull(productPriceType);
    
    List<ProductPrice> productPrices = client.findByProductPriceByType(productPriceType.getType());
    
    assertEquals(3, productPrices.size());
    assertEquals(4, client.getProductTags().size());
    assertEquals(3, client.getProductPriceTypes().size());
    assertEquals(2, client.searchByProductCode("ip").size());
  }
  
  @Test
  public void testTax() throws Exception {
    assertEquals(1, client.getTaxs().size());
    
    Tax tax = new Tax();
    tax.setName("Update");
    tax.setPercent(20);
    
    tax = client.saveTax(tax);
    assertEquals(2, client.getTaxs().size());
    assertEquals("Update", client.getTaxByCode("Update").getName());
    
    client.deleteTax(tax);
    assertEquals(1, client.getTaxs().size());
    
    assertNotNull(client.getTaxByCode("VAT"));
  }
  
  @Test
  public void testProductPriceType() throws Exception{
    assertEquals(3, client.getProductPriceTypes().size());
    
    ProductPriceType priceType = new ProductPriceType();
    priceType.setType("Bảng giá ABC");
    priceType.setOrganizationLoginId("hkt");
    
    priceType = client.saveProductPriceType(priceType);
    assertEquals(4, client.getProductPriceTypes().size());
    
    client.deleteProductPriceType(priceType);
    assertEquals(3, client.getProductPriceTypes().size());
    
    assertNotNull(client.getProductPriceTypeByType("Bang gia thuong"));
    assertNotNull(client.getByProductAndProductPriceType("iphone5s","Bang gia thuong","VND"));
  }
}
