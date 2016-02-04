package com.hkt.client.swingexp.app.core;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

public class MouseEventMove extends MouseAdapter {

  public MouseEventMove() {
  }

  @Override
  public void mouseExited(MouseEvent e) {
      JLabel button = (JLabel) e.getSource();
      button.setOpaque(false);
      button.setFont(new Font("Tahoma", Font.PLAIN, 12));
  }

  @Override
  public void mouseEntered(MouseEvent e) {
      JLabel button = (JLabel) e.getSource();
      button.setOpaque(true);
      button.setBackground(new Color(252, 124, 4));
      button.setFont(new Font("Tahoma", Font.BOLD, 12));
  }

  @Override
  public void mouseMoved(MouseEvent e) {
      JLabel button = (JLabel) e.getSource();
      button.setOpaque(true);
      button.setBackground(new Color(252, 124, 4));
      button.setFont(new Font("Tahoma", Font.BOLD, 12));
  }
}

