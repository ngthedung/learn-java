package com.hkt.client.swingexp.app.core;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import com.hkt.client.swingexp.model.GuiModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.module.product.entity.ProductTag;

@SuppressWarnings("serial")
public class ProductTagJComboBox extends JComboBox<ProductTag> implements IMyObserver{
private List<ProductTag> holder;
  public ProductTagJComboBox() {
    super();
    setFont(new Font("Tahoma", 0, 14));
    setOpaque(false);
    setPreferredSize(new Dimension(200, 23));
    reset();
    final GuiModelManager<ProductTag> g= new GuiModelManager<ProductTag>();
    g.getObservable().addObserver(this);
    addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
         if(e.getButton()==3){
        	 g.viewDialog(ProductTagJComboBox.this, ProductTag.class);
           reset();
         }
        }
      });
  }

//  private String getNameViewProductTag(ProductTag productTag) {
//    String tag = productTag.getLabel();
//    try {
//      tag = getNameViewProductTag(ProductModelManager.getInstance().getProductTagParent(productTag)) + "/" + productTag.getLabel();
//      return tag;
//    } catch (Exception e) {
//      return tag;
//    }
//  }
  
  public void removeIndex(int index){
    holder.remove(index);
    setModel(new DefaultComboBoxModel(holder.toArray()));
  }

  public void setData(List<ProductTag> listProductTag) {
    holder = listProductTag;
    holder.add(0,null);
    setModel(new DefaultComboBoxModel(holder.toArray()));
  }

  @Override
  public ProductTag getItemAt(int index) {
    return holder.get(index);
  }

  public ProductTag getSelectedProductTag() {
    try{
      return (ProductTag) getSelectedItem();
    }
    catch(Exception ex){
      return null;
    }
  }

  public void reset() {
    try {
      holder = ProductModelManager.getInstance().getProductTags();
      holder.add(0, null);
      setModel(new DefaultComboBoxModel(holder.toArray()));
    } catch (Exception e) {
      holder = new ArrayList<ProductTag>();
    }
    
    
  }

@Override
public void update(Object o, Object arg) {
	reset();
	
}

}