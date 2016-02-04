package com.hkt.client.swingexp.app.core;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.ProductServiceClient;
import com.hkt.client.swingexp.model.GuiModelManager;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductTag;

public class ProductJComboBox extends JComboBox<Product> {
  private ClientContext clientContext = ClientContext.getInstance();
  private ProductServiceClient clientProduct = clientContext.getBean(ProductServiceClient.class);
  private List<Product> products;
  
  public ProductJComboBox() {
    super();
    setFont(new Font("Tahoma", 0, 14));
    setOpaque(false);
    setPreferredSize(new Dimension(200, 23));
    resetData();
    addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
         if(e.getButton()==3){
           new GuiModelManager<Product>().viewDialog(ProductJComboBox.this,Product.class);
           resetData();
         }
        }
      });
  }
  
  public void resetData(){
    products = new ArrayList<Product>();
    try {
      FilterQuery filterQuery = new FilterQuery();
      products = clientProduct.filterProduct(filterQuery).getData();
      products.add(0, null);
    } catch (Exception e) {
      e.printStackTrace();
    }
    setModel(new DefaultComboBoxModel(products.toArray()));
  }
  
  public void removeIndex(int index){
    products.remove(0);
    setModel(new DefaultComboBoxModel(products.toArray()));
  }
  
  public void setData(List<Product> listProducts){
    listProducts.add(0, null);
    setModel(new DefaultComboBoxModel(listProducts.toArray()));
  }

  @Override
  public Product getItemAt(int index) {
    return products.get(index);
  }

  public Product getSelectedProduct() {
    try{
      return (Product)getSelectedItem();
    } catch(Exception ex){
      return null;
    }
  }

}
