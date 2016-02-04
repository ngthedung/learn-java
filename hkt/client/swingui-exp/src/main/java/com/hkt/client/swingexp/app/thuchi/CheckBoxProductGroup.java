package com.hkt.client.swingexp.app.thuchi;

import java.awt.Font;

import javax.swing.JCheckBox;

import com.hkt.module.product.entity.ProductTag;

public class CheckBoxProductGroup extends JCheckBox {
  private ProductTag productGroup;

  public ProductTag getProductGroup() {
      return productGroup;
  }

  public CheckBoxProductGroup(ProductTag productGroup) {
      super(productGroup.getLabel());
      this.productGroup = productGroup;
      setFont(new Font("Tahoma", Font.BOLD, 14));
  }
}
