package com.hkt.client.swingexp.app.hethong;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

import com.hkt.client.swingexp.homescreen.HomeScreen;

public class Processing extends JDialog {
  private JLabel labelIcon;
  public Processing() {
    setLayout(new BorderLayout());
    labelIcon = new JLabel();
    labelIcon.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/loading4.gif")));
    labelIcon.setHorizontalAlignment(JLabel.CENTER);
    add(labelIcon, BorderLayout.CENTER);
    setAlwaysOnTop(true);
    setSize(Toolkit.getDefaultToolkit().getScreenSize());
    setUndecorated(true);
    setLocationRelativeTo(null);
    getRootPane().setOpaque(false);;
    getContentPane().setBackground(new Color(1, 1, 1, 1));
    setBackground (new Color (1, 1, 1, 1));
  }
}
