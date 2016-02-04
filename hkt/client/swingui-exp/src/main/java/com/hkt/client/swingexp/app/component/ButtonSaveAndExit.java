package com.hkt.client.swingexp.app.component;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ButtonSaveAndExit extends JButton {
  public ButtonSaveAndExit() {
    setFont(new Font("Tahoma", 1, 14));
    setPreferredSize(new Dimension(120, 35));
    setSize(120, 35);
    setText("Lưu và Thoát");
    setIcon(new ImageIcon("src/icon/save1.png"));
  }
}
