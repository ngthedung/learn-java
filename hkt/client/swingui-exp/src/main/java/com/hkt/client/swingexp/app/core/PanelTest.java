package com.hkt.client.swingexp.app.core;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PanelTest extends JPanel implements IDialogResto {
  private static PanelTest panelTest;
  public static PanelTest getInstance(){
    if(panelTest==null){
      panelTest = new PanelTest();
    }
    return panelTest;
  }
  private JLabel lbchooseDelProduct;
  private boolean test;
  private int i = 0;
  private JLabel jLabel1;
  public boolean isTest() {
    return test;
  }

  public PanelTest() {
    setOpaque(false);
    jLabel1 = new javax.swing.JLabel();
    lbchooseDelProduct = new javax.swing.JLabel();

    jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel1.setText("");

    lbchooseDelProduct.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    lbchooseDelProduct.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    lbchooseDelProduct.setText("1");
    lbchooseDelProduct.setForeground(Color.red);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(layout
        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
        .addComponent(lbchooseDelProduct, javax.swing.GroupLayout.Alignment.TRAILING,
            javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE));
    layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
        layout.createSequentialGroup().addComponent(jLabel1)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(lbchooseDelProduct)));
    loadInfo();
  }

  private class loadInformation implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      if (i > 0) {
        lbchooseDelProduct.setText(String.valueOf(i));
      } else {
        test = true;
        ((JDialog) getRootPane().getParent()).dispose();
      }
      i--;
    }
  }

  private void loadInfo() {
    Timer timer = new Timer(1000, new loadInformation());
    timer.start();
  }

  @Override
  public void Save() throws Exception {
    test = true;
    ((JDialog) getRootPane().getParent()).dispose();
  }
  
  public void setString(String str){
    jLabel1.setText(str);
  }
}
