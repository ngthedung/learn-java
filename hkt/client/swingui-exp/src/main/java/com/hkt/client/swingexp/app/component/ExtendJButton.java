package com.hkt.client.swingexp.app.component;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;

public class ExtendJButton extends JButton {
  public ExtendJButton(String text) {
    setText(text);
    setFont(new Font("Tahoma", 1, 14));
    setPreferredSize(new Dimension(120, 45));
    setSize(100, 45);
  }
}
