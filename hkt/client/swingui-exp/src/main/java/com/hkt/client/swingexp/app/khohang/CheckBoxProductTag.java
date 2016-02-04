package com.hkt.client.swingexp.app.khohang;

import java.awt.Font;

import javax.swing.JCheckBox;

import com.hkt.module.product.entity.ProductTag;

@SuppressWarnings("serial")
public class CheckBoxProductTag extends JCheckBox{
	
private ProductTag productTag;

public ProductTag getProductTag() {
	return productTag;
}

public void setProductTag(ProductTag _productTag) {
	this.productTag = _productTag;
}

public CheckBoxProductTag(ProductTag proTag) {
	this.productTag = proTag;
    setOpaque(false);
    setText(proTag.getLabel());
    setFont(new Font("Tahoma", 1, 14));
  }
}
