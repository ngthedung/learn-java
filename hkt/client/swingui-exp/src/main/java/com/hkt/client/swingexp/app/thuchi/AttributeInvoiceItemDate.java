package com.hkt.client.swingexp.app.thuchi;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.component.GridLabelLayoutPabel;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.accounting.entity.InvoiceItemAttribute;
import com.toedter.calendar.JTextFieldDateEditor;

public class AttributeInvoiceItemDate extends GridLabelLayoutPabel {
  private JTextField txtProvider, txtPrice, txtQuantity;
  private MyJDateChooser txtDateMenufacture, txtDateExpiry;
  private JButton      btnSave, btnExit;
  private InvoiceItem  invoiceItem;
  private Component parent;

  public AttributeInvoiceItemDate( InvoiceItem invoiceItem, Component parent) {
    this.invoiceItem = invoiceItem;
    this.parent = parent;
    setBackground(Color.white);
    JPanel panel = new JPanel();
    panel.setBackground(Color.white);
    JLabel label = new JLabel("Thông tin nhập hàng");
    label.setFont(new Font("Tahoma", Font.BOLD, 18));
    panel.add(label);
    add(0, 0, panel, 2);

    txtProvider = new JTextField(25);
    txtProvider.setFont(new Font("Tahoma", Font.PLAIN, 14));
    add(1, 0, new JLabel("Nhà cung cấp :"));
    add(1, 1, txtProvider);

    txtDateMenufacture = new MyJDateChooser();
    txtDateMenufacture.setDateFormatString("dd/MM/yyyy");
    JTextFieldDateEditor dateEditor = (JTextFieldDateEditor) txtDateMenufacture.getComponent(1);
    dateEditor.setHorizontalAlignment(JTextField.RIGHT);
    add(2, 0, new JLabel("Ngày sản xuất :"));
    add(2, 1, txtDateMenufacture);

    txtDateExpiry = new MyJDateChooser();
    txtDateExpiry.setDateFormatString("dd/MM/yyyy");
    JTextFieldDateEditor dateEditor1 = (JTextFieldDateEditor) txtDateExpiry.getComponent(1);
    dateEditor1.setHorizontalAlignment(JTextField.RIGHT);
    add(3, 0, new JLabel("Ngày hết hạn :"));
    add(3, 1, txtDateExpiry);

    txtQuantity = new JTextField();
    txtQuantity.setHorizontalAlignment(txtQuantity.RIGHT);
    txtQuantity.setFont(new Font("Tahoma", Font.PLAIN, 14));
    add(4, 0, new JLabel("Số lượng :"));
    add(4, 1, txtQuantity);

    txtPrice = new JTextField();
    txtPrice.setHorizontalAlignment(txtPrice.RIGHT);
    txtPrice.setFont(new Font("Tahoma", Font.PLAIN, 14));
    add(5, 0, new JLabel("Giá :"));
    add(5, 1, txtPrice);

    JPanel panel2 = new JPanel();
    panel2.setBackground(Color.white);
    panel2.setLayout(new FlowLayout(FlowLayout.RIGHT));
    btnSave = new JButton(" Lưu ");
    panel2.add(btnSave);
    btnExit = new JButton("Thoát");
    btnExit.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        exit();
      }
    });
    panel2.add(btnExit);
    panel2.add(new JLabel(" "));
    add(6, 0, panel2, 2);

    btnSave.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        save();        
      }
    });
  }
  
  public void save(){
    List<InvoiceItemAttribute> attributes = invoiceItem.getReferences();
    if (txtProvider.getText().length() > 0) {
      InvoiceItemAttribute itemAttribute = new InvoiceItemAttribute();
      itemAttribute.setName("provider");
      itemAttribute.setValue(txtProvider.getText());
      attributes.add(itemAttribute);
    }

    if (txtDateExpiry.getDate() != null) {
      InvoiceItemAttribute itemAttributeMenu = new InvoiceItemAttribute();
      itemAttributeMenu.setName("dateMenufacture");
      itemAttributeMenu.setValue(txtDateMenufacture.getDate().toString());
      attributes.add(itemAttributeMenu);
    }

    if (txtDateMenufacture.getDate() != null) {
      InvoiceItemAttribute itemAttributeExpiry = new InvoiceItemAttribute();
      itemAttributeExpiry.setName("dateExpiry");
      itemAttributeExpiry.setValue(txtDateExpiry.getDate().toString());
      attributes.add(itemAttributeExpiry);
    }       
    
    invoiceItem.setReferences(attributes);
    if(txtPrice.getText().length() > 0){
      try {
        double price = Double.parseDouble(txtPrice.getText().trim());
        invoiceItem.setUnitPrice(price);
      } catch (Exception e2) {
        JOptionPane.showMessageDialog(null, "Lỗi dữ liệu");
        return;
      }
    }
    
    if(txtQuantity.getText().length() > 0){
      try {
        double quantity = Double.parseDouble(txtQuantity.getText().trim());
        invoiceItem.setQuantity(quantity);
      } catch (Exception e2) {
        JOptionPane.showMessageDialog(null, "Lỗi dữ liệu");
        return;
      }
    }
    invoiceItem.setTotal(invoiceItem.getQuantity()*invoiceItem.getUnitPrice());
    invoiceItem.setFinalCharge(invoiceItem.getQuantity()*invoiceItem.getUnitPrice());
    
   // ((PanelPhieuDatMuaHang)parent).updateInvoiceItem(invoiceItem);
    
    ((JDialog) getRootPane().getParent()).dispose();
  }

  public void exit(){
    ((JDialog) getRootPane().getParent()).dispose();
  }
  
}
