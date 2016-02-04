package com.hkt.client.swingexp.app.component;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import com.hkt.client.swingexp.homescreen.HomeScreen;

public class PanelTrans extends javax.swing.JPanel {
  private ImageIcon backgroundIcon = new ImageIcon(HomeScreen.class.getResource("icon/trans38.png"));
  private Image     bgimage;

  public PanelTrans() {
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    ImageIcon iic = new ImageTool().resize(backgroundIcon, this.getSize());
    bgimage = iic.getImage();
    g.drawImage(bgimage, 1, 1, null);
  }

}
