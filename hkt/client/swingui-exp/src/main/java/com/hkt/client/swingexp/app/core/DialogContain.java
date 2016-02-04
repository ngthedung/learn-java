package com.hkt.client.swingexp.app.core;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import com.hkt.client.swingexp.homescreen.HomeScreen;

public class DialogContain extends JDialog {
  private JPanel            panelBackground;
  private JScrollPane       scrollPane;

  public DialogContain(JPanel iPanelListProduct) {
	  ImageIcon icon = new ImageIcon(HomeScreen.class.getResource("icon/icon.png"));
	  Image im = icon.getImage();
	  setIconImage(im);
	  
    panelBackground = new JPanel();
    scrollPane = new JScrollPane();
    
    setModal(true);
    setLocationRelativeTo(null);
    addKeyBindings(panelBackground);
    scrollPane.setViewportView((JPanel) iPanelListProduct);
    
    panelBackground.setLayout(new BorderLayout(0, 0));
    panelBackground.add(scrollPane, BorderLayout.CENTER);
    
    this.setLayout(new BorderLayout(0, 0));
    this.add(panelBackground, BorderLayout.CENTER);
  }

  private void addKeyBindings(JComponent jc) {
    jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "ESC");
    jc.getActionMap().put("ESC", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        dispose();
      }
    });
  }
}
