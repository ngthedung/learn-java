package com.hkt.client.swingexp.app.component;

import java.awt.Font;

import javax.swing.JCheckBox;

public class ExtendJCheckBox extends JCheckBox {
  public ExtendJCheckBox(String text) {
    setOpaque(false);
    setText(text);
    setFont(new Font("Tahoma", 1, 14));
  }
}
