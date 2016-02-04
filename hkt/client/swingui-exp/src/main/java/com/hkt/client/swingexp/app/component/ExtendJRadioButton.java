package com.hkt.client.swingexp.app.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JRadioButton;

public class ExtendJRadioButton extends JRadioButton{
  public ExtendJRadioButton(String text) {
    setText(text);
    setFont(new Font("Tahoma", 1, 14));
    setOpaque(false);
    setBackground(Color.white);
  }

}
