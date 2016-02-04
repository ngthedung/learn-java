package com.hkt.client.swingexp.app.hethong.list;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductTag;

public class ProductTagJComboBoxDelete extends JComboBox<ProductTag> implements IComboBoxDelete<ProductTag> {

  private List<ProductTag> productTags;
  private ProductTag tagOld;

  public ProductTagJComboBoxDelete(ProductTag tagOld) {
    this.tagOld = tagOld;
    productTags = new ArrayList<ProductTag>();
    List<ProductTag> proTagChildren;
    try {
      productTags = ProductModelManager.getInstance().getProductTags();
      proTagChildren = ProductModelManager.getInstance().getChildrenTags(tagOld.getTag());
    } catch (Exception e) {
      proTagChildren = new ArrayList<ProductTag>();
    }

    for (int i =0;i<productTags.size();) {
      boolean children = false;
      for (ProductTag pGroup : proTagChildren) {
        if (productTags.get(i).getCode().equals(pGroup.getCode()) || productTags.get(i).getCode().equals(tagOld.getCode())) {
          children = true;
          break;
        }
      }
      if(children){
        productTags.remove(i);
      }else {
        i++;
      }
    }
    productTags.add(0, null);
    setModel(new DefaultComboBoxModel(productTags.toArray()));
  }

  public ProductTag getSelectedProductTag() {
  	try {
  		return (ProductTag) getSelectedItem();
    } catch (Exception e) {
	    return null;
    }
    
  }

  public void setSelectedProducTag(String codeProducTag) {
    for (int i = 0; i < productTags.size(); i++) {
      if (productTags.get(i).getCode().equals(codeProducTag)) {
        this.setSelectedIndex(i);
        break;
      }
    }
  }

  @Override
  public boolean delete(List<ProductTag> list) {

    try {
      for (int i = 0; i < list.size(); i++) {
        List<ProductTag> children = new ArrayList<ProductTag>();
        children = ProductModelManager.getInstance().getChildrenTags(list.get(i).getTag());

        List<Product> product = new ArrayList<Product>();
        product = ProductModelManager.getInstance().findByProductTag(list.get(i).getTag());

        if ((children.isEmpty() || children == null) && (product.isEmpty() || product == null)) {
          ProductModelManager.getInstance().deleteProductTag(list.get(i));
        } else {
          JOptionPane.showMessageDialog(null, "Không được phép xóa!");
          return false;
        }
      }
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public void changeGroup(List<ProductTag> list) {

    ProductTag tagNew = getSelectedProductTag();
    String tagNewStr="";
    if(tagNew!=null){
    	tagNewStr = tagNew.getTag();
    }
    try {
      for (int i = 0; i < list.size(); i++) {
        List<ProductTag> pTags = ProductModelManager.getInstance().getChildrenTags(list.get(i).getTag());
        for (int j = 0; j < pTags.size(); j++) {
          ProductTag pp = pTags.get(j);
          String strTag = pp.getTag().replaceAll(tagOld.getTag(),tagNewStr);
          pp.setTag(strTag);
          String strTagP = pp.getParentTag().replaceAll(tagOld.getTag(),tagNewStr);
          pp.setParentTag(strTagP);
          ProductModelManager.getInstance().saveProductTag(pp);
        }
        ProductTag tag = list.get(i);
        String strTag = tag.getTag().replaceAll(tagOld.getTag(),tagNewStr);
        tag.setTag(strTag);
        tag.setParentTag(tagNewStr);
        ProductModelManager.getInstance().saveProductTag(tag);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
