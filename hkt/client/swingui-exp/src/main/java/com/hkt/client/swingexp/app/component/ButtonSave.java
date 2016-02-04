package com.hkt.client.swingexp.app.component;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ButtonSave extends JButton {
  public ButtonSave() {
    setFont(new Font("Tahoma", 1, 14));
    setPreferredSize(new Dimension(120, 35));
    setSize(120, 35);
    setText("Lưu");
    setIcon(new ImageIcon("src/icon/save1.png"));
  }
}
