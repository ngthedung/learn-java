package com.hkt.client.swingexp.app.core;

import java.util.List;

import com.hkt.client.swingexp.app.banhang.screen.often.TableEat;
import com.hkt.module.product.entity.Product;

public interface IScreenSales {
  public void addProduct(Product product);
  
  public void setTable(TableEat tableEat);
  
  public void setListProduct(List<Product> list);

}