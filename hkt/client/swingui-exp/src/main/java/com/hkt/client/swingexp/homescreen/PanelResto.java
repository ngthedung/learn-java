package com.hkt.client.swingexp.homescreen;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.hkt.client.swingexp.app.component.ImageTool;

public class PanelResto extends JPanel {

  private Image bgimage;
  private ImageIcon image = new ImageIcon(getClass().getResource("icon/saleperson.png"));

  public PanelResto() {
  }

  @Override
  protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      ImageIcon iic = new ImageTool().resize(image, this.getSize());
      bgimage = iic.getImage();
      g.drawImage(bgimage, 1, 1, null);
  }
}
