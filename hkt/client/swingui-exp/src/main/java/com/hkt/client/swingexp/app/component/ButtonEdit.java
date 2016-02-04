package com.hkt.client.swingexp.app.component;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ButtonEdit extends JButton {
  public ButtonEdit() {
    setFont(new Font("Tahoma", 1, 14));
    setPreferredSize(new Dimension(120, 35));
    setSize(120, 35);
    setText("Chỉnh sửa");
    setIcon(new ImageIcon("src/icon/refresh1.png"));
  }
}
