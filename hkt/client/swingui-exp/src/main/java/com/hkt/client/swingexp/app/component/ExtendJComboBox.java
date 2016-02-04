package com.hkt.client.swingexp.app.component;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComboBox;

import com.hkt.client.swingexp.app.core.ProductTagJComboBox;
import com.hkt.client.swingexp.model.GuiModelManager;
import com.hkt.module.product.entity.ProductTag;

public class ExtendJComboBox extends JComboBox{
  public ExtendJComboBox(){
    setFont(new Font("Tahoma", 0, 14));
    setOpaque(false);
    setPreferredSize(new Dimension(200, 23));
  }

}
