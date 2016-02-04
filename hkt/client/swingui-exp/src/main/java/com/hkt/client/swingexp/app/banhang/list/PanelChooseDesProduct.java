package com.hkt.client.swingexp.app.banhang.list;

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
import com.hkt.module.accounting.entity.InvoiceItem;

@SuppressWarnings("serial")
public class PanelChooseDesProduct extends JPanel implements IDialogResto {

  private JLabel lbnameproduct, lbquantityproduct, lbquantity1product, lbemployee;
  private JTextField txtnameproduct, txtquantityproduct, txtquantity1product;
  private HRJComboBox cboemployee;
  private InvoiceItem invoiceItem;
  private boolean edit;
  
  public boolean isEdit(){
    return edit;
  }
  
  public double getQuantityCance(){
    return MyDouble.parseDouble(txtquantity1product.getText());
  }

  public InvoiceItem getInvoiceItem() {
    return invoiceItem;
  }

  public PanelChooseDesProduct(InvoiceItem invoiceItemq) {
    this.invoiceItem = invoiceItemq;
    setOpaque(false);
    lbnameproduct = new javax.swing.JLabel();
    lbquantityproduct = new javax.swing.JLabel();
    lbquantity1product = new javax.swing.JLabel();
    lbemployee = new javax.swing.JLabel();
    txtnameproduct = new javax.swing.JTextField(invoiceItem.getItemName());
    txtnameproduct.setEnabled(false);
    txtquantityproduct = new javax.swing.JTextField(MyDouble.valueOf(invoiceItem.getQuantity() - 1).toString());
    txtquantityproduct.setEnabled(false);
    txtquantity1product = new javax.swing.JTextField("1");
    txtquantity1product.addCaretListener(new CaretListener() {

      @Override
      public void caretUpdate(CaretEvent e) {
        try {
          if (MyDouble.parseDouble(txtquantity1product.getText()) > invoiceItem.getQuantity()) {
            lbquantity1product.setForeground(Color.red);
          } else {
            lbquantity1product.setForeground(Color.black);
            txtquantityproduct.setText(MyDouble.valueOf(
                invoiceItem.getQuantity() - MyDouble.parseDouble(txtquantity1product.getText())).toString());
          }
        } catch (Exception e2) {
          lbquantity1product.setForeground(Color.red);
        }

      }
    });
    cboemployee = new HRJComboBox();

    setBackground(new java.awt.Color(255, 255, 255));

    lbnameproduct.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    lbnameproduct.setText("Tên sản phẩm");

    lbquantityproduct.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    lbquantityproduct.setText("Số lượng còn lại");

    lbquantity1product.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    lbquantity1product.setText("Số lượng hủy");

    lbemployee.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    lbemployee.setText("Nhân viên TH");

    txtnameproduct.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

    txtquantityproduct.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
    txtquantityproduct.setHorizontalAlignment(JTextField.RIGHT);

    txtquantity1product.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
    txtquantity1product.setHorizontalAlignment(JTextField.RIGHT);
    cboemployee.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
        layout
            .createSequentialGroup()
            .addGroup(
                layout
                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(
                        javax.swing.GroupLayout.Alignment.TRAILING,
                        layout.createSequentialGroup().addComponent(lbquantityproduct)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(
                        layout
                            .createSequentialGroup()
                            .addGroup(
                                layout
                                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbnameproduct)
                                    .addGroup(
                                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lbquantity1product).addComponent(lbemployee)))
                            .addGap(22, 22, 22)))
            .addGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(txtnameproduct)
                    .addComponent(txtquantityproduct, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtquantity1product, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cboemployee, javax.swing.GroupLayout.Alignment.TRAILING, 0, 310, Short.MAX_VALUE))));
    layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
        layout
            .createSequentialGroup()
            .addContainerGap()
            .addGroup(
                layout
                    .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbnameproduct)
                    .addComponent(txtnameproduct, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
                        javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(
                layout
                    .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbquantityproduct)
                    .addComponent(txtquantityproduct, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
                        javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(11, 11, 11)
            .addGroup(
                layout
                    .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbquantity1product)
                    .addComponent(txtquantity1product, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
                        javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(
                layout
                    .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbemployee)
                    .addComponent(cboemployee, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap()));
  }

  @Override
  public void Save() throws Exception {
    if (lbquantity1product.getForeground() != Color.red) {
      edit = true;
      invoiceItem.setQuantity(MyDouble.parseDouble(txtquantityproduct.getText()));
      ((JDialog)getRootPane().getParent()).dispose();
    }
  }
}
