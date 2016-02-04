package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.hkt.client.swingexp.app.core.HRJComboBox;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.module.accounting.entity.InvoiceItem;

@SuppressWarnings("serial")
public class PanelDiscountProduct extends JPanel implements IDialogResto {

  private JLabel lbnameproduct, lbquantityproduct, lbquantity1product, lbPrice;
  private JTextField txtnameproduct, txtquantityproduct, txtquantity1product, txtPrice;
  private InvoiceItem invoiceItem;
  private boolean edit;

  public boolean isEdit() {
    return edit;
  }

  public double getQuantityCance() {
    return MyDouble.parseDouble(txtquantity1product.getText());
  }

  public InvoiceItem getInvoiceItem() {
    return invoiceItem;
  }

  public PanelDiscountProduct(InvoiceItem invoiceItemq) {
    this.invoiceItem = invoiceItemq;
    setOpaque(false);
    lbnameproduct = new javax.swing.JLabel();
    lbquantityproduct = new javax.swing.JLabel();
    lbquantity1product = new javax.swing.JLabel();
    txtnameproduct = new javax.swing.JTextField(invoiceItem.getItemName());
    txtnameproduct.setEnabled(false);
    lbPrice = new javax.swing.JLabel("Tổng tiền");
    txtPrice = new javax.swing.JTextField(MyDouble.valueOf(invoiceItem.getTotal()).toString());
    txtPrice.setEnabled(false);
    txtquantityproduct = new javax.swing.JTextField(MyDouble.valueOf(invoiceItem.getDiscountRate()).toString());
    txtquantity1product = new javax.swing.JTextField(MyDouble.valueOf(invoiceItem.getDiscount()).toString());
    txtquantity1product.addCaretListener(new CaretListener() {

      @Override
      public void caretUpdate(CaretEvent e) {
        try {
          if (txtquantity1product.isFocusOwner()) {
            double rate = MyDouble.parseDouble(txtquantity1product.getText()) / invoiceItem.getTotal() * 100;
            txtquantityproduct.setText(MyDouble.valueOf(rate).toString());
          }
        } catch (Exception e2) {

        }

      }
    });
    txtquantityproduct.addCaretListener(new CaretListener() {

      @Override
      public void caretUpdate(CaretEvent e) {
        try {
          if (txtquantityproduct.isFocusOwner()) {
            double rate = MyDouble.parseDouble(txtquantityproduct.getText()) * invoiceItem.getTotal() / 100;
            txtquantity1product.setText(MyDouble.valueOf(rate).toString());
          }
        } catch (Exception e2) {

        }

      }
    });

    setBackground(new java.awt.Color(255, 255, 255));

    lbnameproduct.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    lbnameproduct.setText("Tên sản phẩm");

    lbquantityproduct.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    lbquantityproduct.setText("Chiết khấu %");

    lbquantity1product.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    lbquantity1product.setText("CK tiền");

    txtnameproduct.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

    txtquantityproduct.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
    txtquantityproduct.setHorizontalAlignment(JTextField.RIGHT);

    txtquantity1product.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
    txtquantity1product.setHorizontalAlignment(JTextField.RIGHT);
    txtPrice.setHorizontalAlignment(JTextField.RIGHT);

    MyGroupLayout layout = new MyGroupLayout(this);
    layout.add(0, 0, lbnameproduct);
    layout.add(0, 1, txtnameproduct);
    layout.add(1, 0, lbPrice);
    layout.add(1, 1, txtPrice);

    layout.add(2, 0, lbquantityproduct);
    layout.add(2, 1, txtquantityproduct);
    layout.add(3, 0, lbquantity1product);
    layout.add(3, 1, txtquantity1product);
    layout.updateGui();
    // javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    // this.setLayout(layout);
    // layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
    // layout
    // .createSequentialGroup()
    // .addGroup(
    // layout
    // .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
    // .addGroup(
    // javax.swing.GroupLayout.Alignment.TRAILING,
    // layout.createSequentialGroup().addComponent(lbquantityproduct)
    // .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
    // .addGroup(
    // layout
    // .createSequentialGroup()
    // .addGroup(
    // layout
    // .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
    // .addComponent(lbnameproduct)
    // .addGroup(
    // layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
    // .addComponent(lbquantity1product))).addGap(22, 22, 22)))
    // .addGroup(
    // layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(txtnameproduct)
    // .addComponent(txtquantityproduct,
    // javax.swing.GroupLayout.Alignment.TRAILING)
    // .addComponent(txtquantity1product,
    // javax.swing.GroupLayout.Alignment.TRAILING))));
    // layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
    // layout
    // .createSequentialGroup()
    // .addContainerGap()
    // .addGroup(
    // layout
    // .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
    // .addComponent(lbnameproduct)
    // .addComponent(txtnameproduct, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
    // javax.swing.GroupLayout.PREFERRED_SIZE))
    // .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
    // .addGroup(
    // layout
    // .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
    // .addComponent(lbquantityproduct)
    // .addComponent(txtquantityproduct, javax.swing.GroupLayout.PREFERRED_SIZE,
    // 23,
    // javax.swing.GroupLayout.PREFERRED_SIZE))
    // .addGap(11, 11, 11)
    // .addGroup(
    // layout
    // .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
    // .addComponent(lbquantity1product)
    // .addComponent(txtquantity1product,
    // javax.swing.GroupLayout.PREFERRED_SIZE, 23,
    // javax.swing.GroupLayout.PREFERRED_SIZE))
    // .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
    // .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)).addContainerGap()));
  }

  @Override
  public void Save() throws Exception {
    if (lbquantity1product.getForeground() != Color.red) {
      edit = true;
      invoiceItem.setDiscountRate(MyDouble.parseDouble(txtquantityproduct.getText()));
      ((JDialog) getRootPane().getParent()).dispose();
    }
  }
}
