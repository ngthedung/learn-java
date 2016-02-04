package com.hkt.client.swingexp.app.khohang.list;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.LocaleServiceClient;
import com.hkt.client.swingexp.app.banhang.PanelProductPriceType;
import com.hkt.client.swingexp.app.core.DialogList;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.UnitMoneyJComboBox;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.product.entity.ProductPriceType;

public class PanelSortbarcode extends JPanel implements IDialogResto {

  private JLabel lbBangGia, lbDVTien;
  private JComboBox btnBangGia;
  private UnitMoneyJComboBox btnDVTien;
  private ClientContext clientContext = ClientContext.getInstance();
  private LocaleServiceClient clientLocale = clientContext.getBean(LocaleServiceClient.class);
  private List<ProductPriceType> listTyGia = new ArrayList<ProductPriceType>();

  public PanelSortbarcode() {
    setOpaque(false);
    lbBangGia = new JLabel();
    lbDVTien = new JLabel();
    btnBangGia = new JComboBox();
    btnDVTien = new UnitMoneyJComboBox();

    try {
      // Lấy danh sách tỷ giá và add vào Combobox btnBangGia
      listTyGia = ProductModelManager.getInstance().getProductPriceTypes();
      DefaultComboBoxModel listgia = new DefaultComboBoxModel(listTyGia.toArray());
      btnBangGia.setModel(listgia);

    } catch (Exception e) {
      e.printStackTrace();
    }

    btnBangGia.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
          PanelProductPriceType pnProductPriceType;
          try {
            pnProductPriceType = new PanelProductPriceType();
            pnProductPriceType.setName("QuanLyBangGia");
            DialogMain dialog = new DialogMain(pnProductPriceType);
            dialog.setName("dlQuanLyBangGia");
            dialog.setIcon("banggia1.png");
            dialog.setTitle("Bảng giá hiện hành");
            dialog.setVisible(true);
            btnBangGia.setModel(new DefaultComboBoxModel(ProductModelManager.getInstance().getProductPriceTypes().toArray()));
          } catch (Exception e1) {
          }
        }
      }
    });
    // Lấy danh sách Đơn vị tiền tệ add vào Combobox btnDVTein
    try {
      btnDVTien.setModel(new DefaultComboBoxModel(clientLocale.getCurrencies().toArray()));
    } catch (Exception e) {
      btnDVTien.setModel(new DefaultComboBoxModel());
    }

    lbBangGia.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    lbBangGia.setText("Bảng giá");

    lbDVTien.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    lbDVTien.setText("Đơn vị tiền");

    btnBangGia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

    btnDVTien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
        layout
            .createSequentialGroup()
            // .addContainerGap()
            .addGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(lbBangGia)
                    .addComponent(lbDVTien))
            .addGap(9, 9, 9)
            .addGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBangGia, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDVTien, 0, 284, Short.MAX_VALUE)).addContainerGap()));
    layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
        layout
            .createSequentialGroup()
            // .addContainerGap()
            .addGroup(
                layout
                    .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbBangGia, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBangGia, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
                        javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(
                layout
                    .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbDVTien, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDVTien, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
                        javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
  }

  @Override
  public void Save() throws Exception {

    ((JDialog) getRootPane().getParent()).dispose();
    try {
      TableListBarcode barcode = new TableListBarcode(((ProductPriceType)btnBangGia.getSelectedItem()).getType(), btnDVTien
          .getSelectedCurrency().getCode());
      DialogList dialog = new DialogList(barcode);
      dialog.setTitle("Danh sách mã Barcode");
      dialog.setIcon("barcode1.png");
      dialog.setComponent1(barcode.getBtnPrint());
      dialog.setVisible(true);
    } catch (Exception e) {
      TableListBarcode barcode = new TableListBarcode("", btnDVTien.getSelectedCurrency().getCode());
      DialogList dialog = new DialogList(barcode);
      dialog.setComponent1(barcode.getBtnPrint());
      dialog.setTitle("Danh sách mã Barcode");
      dialog.setVisible(true);
    }

  }

}
