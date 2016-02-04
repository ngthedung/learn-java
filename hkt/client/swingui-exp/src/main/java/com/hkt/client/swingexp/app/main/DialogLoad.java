package com.hkt.client.swingexp.app.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.xmlbeans.impl.jam.JComment;

import com.hkt.client.swingexp.app.banhang.screen.often.PanelRemoteControl;
import com.hkt.client.swingexp.app.component.ImageTool;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.hethong.AdminConfig;
import com.hkt.client.swingexp.app.hethong.LoginDialog;
import com.hkt.client.swingexp.app.hethong.PanelSystemConfig;
import com.hkt.client.swingexp.app.nhansu.PanelPermission;
import com.hkt.client.swingexp.homescreen.HomeScreen;

public class DialogLoad extends JDialog {
  private JProgressBar jProgressBar1;
  private JLabel jLabel1;

  public DialogLoad() {

    setSize(470, 300);
    this.dispose();
    this.setUndecorated(true);
    setLocationRelativeTo(null);
    setResizable(false);
    setLayout(new GridLayout());
    add(new Panel());
    jProgressBar1.addChangeListener(new ChangeListener() {

      @Override
      public void stateChanged(ChangeEvent e) {
        JProgressBar bar = (JProgressBar) e.getSource();
        if (bar.getValue() > 30) {
          jLabel1.setText("   Starting modules ...");
        }
        if (bar.getValue() > 60) {
          jLabel1.setText("   Loading data ...");
        }
      }
    });

  }

  public void setValue(int n) {
    jProgressBar1.setValue(n);
  }

  public class Panel extends javax.swing.JPanel {

    private ImageIcon backgroundIcon = new ImageIcon(HomeScreen.class.getResource("icon/Start.png"));

    public Panel() {

      JPanel p = new JPanel();
      p.setLayout(new GridLayout());
      p.setOpaque(false);
      p.setBackground(Color.blue);
      jProgressBar1 = new javax.swing.JProgressBar();
      jProgressBar1.setForeground(Color.red);
      jProgressBar1.setBackground(Color.black);
      jProgressBar1.setBorder(null);
      jProgressBar1.setOpaque(false);
      jProgressBar1.setBorderPainted(false);
      p.add(jProgressBar1);
      jLabel1 = new javax.swing.JLabel();

      jLabel1.setText("   Starting HKT Software ...");
      jLabel1.setForeground(Color.WHITE);

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
      this.setLayout(layout);
      layout
          .setHorizontalGroup(layout
              .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(
                  layout.createSequentialGroup()
                      .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                      .addGap(20, 20, 20))
              .addComponent(p, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE));
      layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
          javax.swing.GroupLayout.Alignment.TRAILING,
          layout
              .createSequentialGroup()
              .addContainerGap(237, Short.MAX_VALUE)
              .addComponent(jLabel1)
              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
              .addComponent(p, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
                  javax.swing.GroupLayout.PREFERRED_SIZE).addGap(33, 33, 33)));
      this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false),
          "ESC");
      this.getActionMap().put("ESC", new AbstractAction() {

        @Override
        public void actionPerformed(ActionEvent e) {
        	try {
        		Runtime.getRuntime().exec("taskkill /F /IM HKTSoft.exe");
        		Runtime.getRuntime().exec("taskkill /F /IM HKTServer.exe");
        		Runtime.getRuntime().exec("taskkill /F /IM Java.exe");
          } catch (Exception e2) {
          }
          System.exit(0);

        }
      });

     
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      ImageIcon iic = new ImageTool().resize(backgroundIcon, this.getSize());
      Image bgimage = iic.getImage();
      g.drawImage(bgimage, 1, 1, null);
    }

  }
  

}
