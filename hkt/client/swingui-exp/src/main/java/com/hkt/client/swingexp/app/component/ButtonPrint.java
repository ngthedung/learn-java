package com.hkt.client.swingexp.app.component;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ButtonPrint extends JButton {
  public ButtonPrint() {
    setFont(new Font("Tahoma", 1, 14));
    setPreferredSize(new Dimension(120, 35));
    setSize(120, 35);
    setText("Print");
    setIcon(new ImageIcon("src/icon/print1.png"));
  }
}