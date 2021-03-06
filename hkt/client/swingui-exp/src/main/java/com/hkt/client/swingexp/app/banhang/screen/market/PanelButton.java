package com.hkt.client.swingexp.app.banhang.screen.market;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.hkt.client.swingexp.app.banhang.screen.often.DialogConfig;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.module.account.entity.Profile;

public class PanelButton extends javax.swing.JPanel implements ActionListener {

  /** Creates new form PanelButton */
  private DialogSuperMarket dialog;

  public PanelButton() {
    initComponents();
    setOpaque(false);
    jPanel1.setOpaque(false);
    jPanel2.setOpaque(false);
    button_1.addActionListener(this);
    button_2.addActionListener(this);
    button_3.addActionListener(this);
    button_4.addActionListener(this);
    button_5.addActionListener(this);
    button_6.addActionListener(this);
    button_7.addActionListener(this);
    button_8.addActionListener(this);
    button_9.addActionListener(this);
  }

  public void setDialogMaket(DialogSuperMarket dialog) {
    this.dialog = dialog;
  }

  // Load lại giao diện sau khi thiết lập
  public void resetGui() {
System.out.println(111111);
    try {
      Profile profile = AccountModelManager.getInstance().getProfileConfigAdmin();
      System.out.println(profile);
      if (!ManagerAuthenticate.getInstance().getLoginId().equals("admin")) {

        if (profile.get(DialogConfig.TraSau).toString().equals("true")) {
          btnAfterPayment.setEnabled(true);
        } else {
          btnAfterPayment.setEnabled(false);
        }

        if (profile.get(DialogConfig.CTKM).toString().equals("true")) {
          btnPromotions.setEnabled(true);
        } else {
          btnPromotions.setEnabled(false);
        }

        if (profile.get(DialogConfig.TTLe).toString().equals("true")) {
          btnPayOnce.setEnabled(true);
        } else {
          btnPayOnce.setEnabled(false);
        }

        if (profile.get(DialogConfig.PrintReceip).toString().equals("true")) {
          btnPrint.setEnabled(true);
        } else {
          btnPrint.setEnabled(false);
        }

        if (profile.get(DialogConfig.Paymen).toString().equals("true")) {
          buttonPayment.setEnabled(true);
        } else {
          buttonPayment.setEnabled(false);
        }

        if (profile.get(DialogConfig.SoLuong).toString().equals("true")) {
          btnAdd.setEnabled(true);
        } else {
          btnAdd.setEnabled(false);
        }

        if (profile.get(DialogConfig.DoiGia).toString().equals("true")) {
          btnChangePrice.setEnabled(true);
        } else {
          btnChangePrice.setEnabled(false);
        }

        if (profile.get(DialogConfig.XoaSP).toString().equals("true")) {
          btnDelete1.setEnabled(true);
        } else {
          btnDelete1.setEnabled(false);
        }
      }
    } catch (Exception e) {
    }
    // System.out.println("opend   " + new JVMEnv().getMemoryInfo());
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */

  private void initComponents() {

    jPanel1 = new javax.swing.JPanel();
    button_1 = new javax.swing.JButton();
    button_2 = new javax.swing.JButton();
    button_3 = new javax.swing.JButton();
    button_4 = new javax.swing.JButton();
    button_5 = new javax.swing.JButton();
    button_6 = new javax.swing.JButton();
    button_7 = new javax.swing.JButton();
    button_8 = new javax.swing.JButton();
    button_9 = new javax.swing.JButton();
    btnDelete1 = new javax.swing.JButton();
    btnAdd = new javax.swing.JButton();
    btnChangePrice = new javax.swing.JButton();
    button_C = new javax.swing.JButton();
    btnLichTrinh = new javax.swing.JButton();
    btnDiscountProduct = new javax.swing.JButton();
    jPanel2 = new javax.swing.JPanel();
    btnPrint = new javax.swing.JButton();
    buttonPayment = new javax.swing.JButton();
    btnPayOnce = new javax.swing.JButton();
    btnAfterPayment = new javax.swing.JButton();
    btnSetupSales = new javax.swing.JButton();
    btnPromotions = new javax.swing.JButton();

    jPanel1.setBackground(new java.awt.Color(255, 255, 255));

    button_1.setFont(new java.awt.Font("Tahoma", 1, 14));
    button_1.setText("1"); // NOI18N

    button_2.setFont(new java.awt.Font("Tahoma", 1, 14));
    button_2.setText("2"); // NOI18N

    button_3.setFont(new java.awt.Font("Tahoma", 1, 14));
    button_3.setText("3"); // NOI18N

    button_4.setFont(new java.awt.Font("Tahoma", 1, 14));
    button_4.setText("4"); // NOI18N

    button_5.setFont(new java.awt.Font("Tahoma", 1, 14));
    button_5.setText("5"); // NOI18N

    button_6.setFont(new java.awt.Font("Tahoma", 1, 14));
    button_6.setText("6"); // NOI18N

    button_7.setFont(new java.awt.Font("Tahoma", 1, 14));
    button_7.setText("7"); // NOI18N

    button_8.setFont(new java.awt.Font("Tahoma", 1, 14));
    button_8.setText("8"); // NOI18N

    button_9.setFont(new java.awt.Font("Tahoma", 1, 14));
    button_9.setText("9"); // NOI18N

    btnDelete1.setFont(new java.awt.Font("Tahoma", 1, 12));
    btnDelete1.setText("Xóa"); // NOI18N
    btnDelete1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnDelete1ActionPerformed(evt);
      }
    });

    btnAdd.setFont(new java.awt.Font("Tahoma", 1, 12));
    btnAdd.setText("Số lượng"); // NOI18N
    btnAdd.setMargin(new java.awt.Insets(0, 0, 0, 0));
    btnAdd.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnAddActionPerformed(evt);
      }
    });

    btnChangePrice.setFont(new java.awt.Font("Tahoma", 1, 12));
    btnChangePrice.setText("Đổi giá"); // NOI18N
    btnChangePrice.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnChangePriceActionPerformed(evt);
      }
    });

    button_C.setFont(new java.awt.Font("Tahoma", 1, 14));
    button_C.setText("C"); // NOI18N
    button_C.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        button_CActionPerformed(evt);
      }
    });

    btnLichTrinh.setFont(new java.awt.Font("Tahoma", 1, 12));
    btnLichTrinh.setText("Lịch trình"); // NOI18N
    btnLichTrinh.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnLichTrinhActionPerformed(evt);
      }
    });

    btnDiscountProduct.setFont(new java.awt.Font("Tahoma", 1, 12));
    btnDiscountProduct.setText("CK SP"); // NOI18N
    btnDiscountProduct.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnTraGopActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout
        .setHorizontalGroup(jPanel1Layout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(
                jPanel1Layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .addGroup(
                        jPanel1Layout
                            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(
                                jPanel1Layout
                                    .createSequentialGroup()
                                    .addComponent(button_1, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(button_2, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(button_3, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(button_4, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(button_5, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(button_6, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                                    .addGap(6, 6, 6)
                                    .addComponent(button_7, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(button_8, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(button_9, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(button_C, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
                            .addGroup(
                                jPanel1Layout
                                    .createSequentialGroup()
                                    .addComponent(btnDelete1, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnChangePrice, javax.swing.GroupLayout.DEFAULT_SIZE, 94,
                                        Short.MAX_VALUE)
                                    .addGap(6, 6, 6)
                                    .addComponent(btnLichTrinh, javax.swing.GroupLayout.DEFAULT_SIZE, 94,
                                        Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnDiscountProduct, javax.swing.GroupLayout.DEFAULT_SIZE, 95,
                                        Short.MAX_VALUE))).addContainerGap()));
    jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(
            jPanel1Layout
                .createSequentialGroup()
                .addContainerGap()
                .addGroup(
                    jPanel1Layout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(button_1, javax.swing.GroupLayout.PREFERRED_SIZE, 45,
                            javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(button_2, javax.swing.GroupLayout.PREFERRED_SIZE, 45,
                            javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(button_3, javax.swing.GroupLayout.PREFERRED_SIZE, 45,
                            javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(button_4, javax.swing.GroupLayout.PREFERRED_SIZE, 45,
                            javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(button_5, javax.swing.GroupLayout.PREFERRED_SIZE, 45,
                            javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(button_6, javax.swing.GroupLayout.PREFERRED_SIZE, 45,
                            javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(button_7, javax.swing.GroupLayout.PREFERRED_SIZE, 45,
                            javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(button_8, javax.swing.GroupLayout.PREFERRED_SIZE, 45,
                            javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(button_9, javax.swing.GroupLayout.PREFERRED_SIZE, 45,
                            javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(button_C, javax.swing.GroupLayout.PREFERRED_SIZE, 45,
                            javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                    javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(
                    jPanel1Layout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnDelete1, javax.swing.GroupLayout.PREFERRED_SIZE, 45,
                            javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 45,
                            javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnChangePrice, javax.swing.GroupLayout.PREFERRED_SIZE, 45,
                            javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnLichTrinh, javax.swing.GroupLayout.PREFERRED_SIZE, 45,
                            javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDiscountProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 45,
                            javax.swing.GroupLayout.PREFERRED_SIZE))));

    jPanel2.setBackground(new java.awt.Color(255, 255, 255));

    btnPrint.setFont(new java.awt.Font("Tahoma", 1, 13));
    btnPrint.setText("In hóa đơn"); // NOI18N
    btnPrint.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    btnPrint.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnPrintActionPerformed(evt);
      }
    });

    buttonPayment.setFont(new java.awt.Font("Tahoma", 1, 13));
    buttonPayment.setText("Thanh toán"); // NOI18N
    buttonPayment.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    buttonPayment.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        buttonPaymentActionPerformed(evt);
      }
    });

    btnPayOnce.setFont(new java.awt.Font("Tahoma", 1, 13));
    btnPayOnce.setText("Thanh toán lẻ"); // NOI18N
    btnPayOnce.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    btnPayOnce.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnPayOnceActionPerformed(evt);
      }
    });

    btnAfterPayment.setFont(new java.awt.Font("Tahoma", 1, 13));
    btnAfterPayment.setText("Trả sau"); // NOI18N
    btnAfterPayment.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnAfterPaymentActionPerformed(evt);
      }
    });

    btnSetupSales.setFont(new java.awt.Font("Tahoma", 1, 13));
    btnSetupSales.setText("Thiết lập bán"); // NOI18N
    btnSetupSales.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnSetupSalesActionPerformed(evt);
      }
    });

    btnPromotions.setFont(new java.awt.Font("Tahoma", 1, 12));
    btnPromotions.setText("Xem KM"); // NOI18N
    btnPromotions.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnPromotionsActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout
        .setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(
                jPanel2Layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .addGroup(
                        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnAfterPayment, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                            .addComponent(btnPrint, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))
                    .addGap(10, 10, 10)
                    .addGroup(
                        jPanel2Layout
                            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(
                                jPanel2Layout
                                    .createSequentialGroup()
                                    .addComponent(btnPayOnce, javax.swing.GroupLayout.DEFAULT_SIZE, 145,
                                        Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(buttonPayment, javax.swing.GroupLayout.DEFAULT_SIZE, 166,
                                        Short.MAX_VALUE))
                            .addGroup(
                                jPanel2Layout
                                    .createSequentialGroup()
                                    .addComponent(btnSetupSales, javax.swing.GroupLayout.DEFAULT_SIZE, 146,
                                        Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnPromotions, javax.swing.GroupLayout.DEFAULT_SIZE, 165,
                                        Short.MAX_VALUE))).addContainerGap()));
    jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(
            jPanel2Layout
                .createSequentialGroup()
                .addGroup(
                    jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnPrint, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                        .addComponent(buttonPayment, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                        .addComponent(btnPayOnce, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addGroup(
                    jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnAfterPayment, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                        .addComponent(btnPromotions, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                        .addComponent(btnSetupSales, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE))));

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(layout
        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE,
            javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE,
            javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
    layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
        layout
            .createSequentialGroup()
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.PREFERRED_SIZE)));
  }// </editor-fold>

  public javax.swing.JButton getBtnDiscountProduct() {
    return btnDiscountProduct;
  }

  private void btnDelete1ActionPerformed(java.awt.event.ActionEvent evt) {
    try {
      dialog.deleteProduct();
    } catch (Exception e) {
    }
  }

  private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {
    try {
      dialog.changeQuantityProduct();
    } catch (Exception e) {
    }
  }

  private void btnChangePriceActionPerformed(java.awt.event.ActionEvent evt) {
    try {
      dialog.changePriceProduct();
    } catch (Exception e) {
    }
  }

  private void button_CActionPerformed(java.awt.event.ActionEvent evt) {
    try {
      dialog.button_CActionPerformed(evt);
    } catch (Exception e) {
    }
  }

  private void btnLichTrinhActionPerformed(java.awt.event.ActionEvent evt) {
    try {
      dialog.lichTrinh();
    } catch (Exception e) {
    }
  }

  private void btnTraGopActionPerformed(java.awt.event.ActionEvent evt) {
    dialog.discountProduct();
  }

  private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {
    try {
      dialog.printInvoice();
    } catch (Exception e) {
    }
  }

  private void buttonPaymentActionPerformed(java.awt.event.ActionEvent evt) {
    try {
      dialog.payment();
    } catch (Exception e) {
    }

  }

  private void btnPayOnceActionPerformed(java.awt.event.ActionEvent evt) {
    try {
      dialog.paymentSlip();
    } catch (Exception e) {
    }
  }

  private void btnAfterPaymentActionPerformed(java.awt.event.ActionEvent evt) {
    try {
      dialog.paymentAfter();
    } catch (Exception e) {
    }
  }

  private void btnSetupSalesActionPerformed(java.awt.event.ActionEvent evt) {
    DialogConfig dialog = new DialogConfig(false, 600, 100);
    dialog.setName("dlThietLapBH");
    dialog.setTitle("Thiết lập bán hàng");
    dialog.setVisible(true);
  }

  private void btnPromotionsActionPerformed(java.awt.event.ActionEvent evt) {
    try {
      dialog.viewListPromotion();
    } catch (Exception e) {
    }

  }

  // Variables declaration - do not modify
  private javax.swing.JButton btnAdd;
  private javax.swing.JButton btnAfterPayment;
  private javax.swing.JButton btnChangePrice;
  private javax.swing.JButton btnDelete1;
  private javax.swing.JButton btnLichTrinh;
  private javax.swing.JButton btnPayOnce;
  private javax.swing.JButton btnPrint;
  private javax.swing.JButton btnPromotions;
  private javax.swing.JButton btnSetupSales;
  private javax.swing.JButton btnDiscountProduct;
  private javax.swing.JButton buttonPayment;
  private javax.swing.JButton button_1;
  private javax.swing.JButton button_2;
  private javax.swing.JButton button_3;
  private javax.swing.JButton button_4;
  private javax.swing.JButton button_5;
  private javax.swing.JButton button_6;
  private javax.swing.JButton button_7;
  private javax.swing.JButton button_8;
  private javax.swing.JButton button_9;
  private javax.swing.JButton button_C;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel2;

  // End of variables declaration
  @Override
  public void actionPerformed(ActionEvent e) {
    JButton btn = (JButton) e.getSource();
    try {
      double a = Double.parseDouble(btn.getText());
      dialog.updateItem(a);
    } catch (Exception e2) {
    }
  }
}
