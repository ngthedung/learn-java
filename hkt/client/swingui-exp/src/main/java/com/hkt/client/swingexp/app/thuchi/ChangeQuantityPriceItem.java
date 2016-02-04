package com.hkt.client.swingexp.app.thuchi;

import java.awt.Color;
import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.component.GridLabelLayoutPabel;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.model.WarehouseModelManager;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.accounting.entity.InvoiceItemAttribute;
import com.hkt.module.warehouse.entity.IdentityProduct;
import com.hkt.module.warehouse.entity.IdentityProduct.ImportType;
import com.toedter.calendar.JTextFieldDateEditor;

public class ChangeQuantityPriceItem extends GridLabelLayoutPabel implements IDialogResto {
  private JTextField txtProvider, txtPrice, txtQuantity;
  private MyJDateChooser txtDateMenufacture, txtDateExpiry;
  private InvoiceItem invoiceItem;
  private JLabel lbMessage;

  public InvoiceItem getInvoiceItem() {
    return invoiceItem;
  }

  public ChangeQuantityPriceItem(InvoiceItem invoiceItem) {
    this.invoiceItem = invoiceItem;
    setOpaque(false);
    txtProvider = new JTextField(25);
    txtProvider.setFont(new Font("Tahoma", Font.PLAIN, 14));
    add(0, 0, new JLabel("Nhà cung cấp :"));
    add(0, 1, txtProvider);

    txtDateMenufacture = new MyJDateChooser();
    txtDateMenufacture.setDateFormatString("dd/MM/yyyy");
    add(1, 0, new JLabel("Ngày sản xuất :"));
    add(1, 1, txtDateMenufacture);

    txtDateExpiry = new MyJDateChooser();
    txtDateExpiry.setDateFormatString("dd/MM/yyyy");
    add(2, 0, new JLabel("Ngày hết hạn :"));
    add(2, 1, txtDateExpiry);

    txtQuantity = new JTextField();
    txtQuantity.setHorizontalAlignment(txtQuantity.RIGHT);
    txtQuantity.setFont(new Font("Tahoma", Font.PLAIN, 14));
    add(3, 0, new JLabel("Số lượng :"));
    add(3, 1, txtQuantity);

    txtPrice = new JTextField();
    txtPrice.setName("txtPrice");
    txtPrice.setHorizontalAlignment(txtPrice.RIGHT);
    txtPrice.setFont(new Font("Tahoma", Font.PLAIN, 14));
    add(4, 0, new JLabel("Giá :"));
    add(4, 1, txtPrice);

    lbMessage = new JLabel();
    lbMessage.setFont(new Font("Tahoma", Font.PLAIN, 14));
    lbMessage.setForeground(Color.red);
    add(5, 0, new JLabel(" "));
    add(5, 1, lbMessage);

    loadData();
  }

  public void save() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
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
      itemAttributeMenu.setValue(dateFormat.format(txtDateMenufacture.getDate()));
      attributes.add(itemAttributeMenu);
    }

    if (txtDateMenufacture.getDate() != null) {
      InvoiceItemAttribute itemAttributeExpiry = new InvoiceItemAttribute();
      itemAttributeExpiry.setName("dateExpiry");
      itemAttributeExpiry.setValue(dateFormat.format(txtDateExpiry.getDate()));
      attributes.add(itemAttributeExpiry);
    }

    invoiceItem.setReferences(attributes);
    if (txtPrice.getText().length() > 0) {
      try {
        double price = Double.parseDouble(txtPrice.getText().trim());
        invoiceItem.setUnitPrice(price);
      } catch (Exception e2) {
        JOptionPane.showMessageDialog(null, "Lỗi dữ liệu");
        return;
      }
    }

    if (txtQuantity.getText().length() > 0) {
      try {
        double quantity = Double.parseDouble(txtQuantity.getText().trim());
        invoiceItem.setQuantity(quantity);
      } catch (Exception e2) {
        JOptionPane.showMessageDialog(null, "Lỗi dữ liệu");
        return;
      }
    }
    invoiceItem.setTotal(invoiceItem.getQuantity() * invoiceItem.getUnitPrice());
    invoiceItem.setFinalCharge(invoiceItem.getQuantity() * invoiceItem.getUnitPrice());

    ((JDialog) getRootPane().getParent()).dispose();
  }

  private void loadData() {
    List<InvoiceItemAttribute> attributes = invoiceItem.getReferences();
    for (InvoiceItemAttribute invoiceItemAttribute : attributes) {
      if (invoiceItemAttribute.getName().equals("provider")) {
        txtProvider.setText(invoiceItemAttribute.getValue());
      }
      if (invoiceItemAttribute.getName().equals("dateMenufacture")) {
        Date date;
        try {
          date = new SimpleDateFormat("dd/MM/yyyy").parse(invoiceItemAttribute.getValue());
          txtDateMenufacture.setDate(date);
        } catch (ParseException e) {
          e.printStackTrace();
        }
      }
      if (invoiceItemAttribute.getName().equals("dateExpiry")) {
        Date date;
        try {
          date = new SimpleDateFormat("dd/MM/yyyy").parse(invoiceItemAttribute.getValue());
          txtDateExpiry.setDate(date);
        } catch (ParseException e) {
          e.printStackTrace();
        }
      }
      txtQuantity.setText(invoiceItem.getQuantity() + "");
      txtPrice.setText(invoiceItem.getUnitPrice() + "");
    }
  }

  public void exit() {
    ((JDialog) getRootPane().getParent()).dispose();
  }

  @Override
  public void Save() throws Exception {
    if (checkData()) {
      save();
    }
  }

  public boolean checkData() {
    double quantityInput = 0;
    double quantityPrior = 0;
    try {
      quantityInput = Double.parseDouble(txtQuantity.getText().trim());
      quantityPrior = invoiceItem.getQuantity();
    } catch (Exception e) {
      lbMessage.setText("Lỗi dữ liệu");
      return false;
    }
    if (quantityInput > quantityPrior) {
      double quantityCheck = quantityInput - quantityPrior;
      List<InvoiceItemAttribute> attributes = invoiceItem.getReferences();
      for (InvoiceItemAttribute invoiceItemAttribute : attributes) {
        String value = "";
        if (invoiceItemAttribute.getName().equals("product")) {
          value = invoiceItemAttribute.getValue();
        }
        List<IdentityProduct> identityProducts = new ArrayList<IdentityProduct>();
        try {
//          identityProducts = WarehouseModelManager.getInstance()
//              .getByImportTypeAndProductCode(ImportType.Import, value);
        } catch (Exception e) {
          e.printStackTrace();
        }
        if (identityProducts.size() - quantityPrior < quantityCheck) {
          lbMessage.setText("Số lượng sản phẩm không đủ.");
          return false;
        }
      }
    }
    if (quantityInput < 1) {
      lbMessage.setText("Số lượng sản phẩm phải lớn hơn 0.");
      return false;
    }
    double price = 0;
    try {
      price = Double.parseDouble(txtPrice.getText().trim());
    } catch (Exception e) {
      lbMessage.setText("Lỗi dữ liệu.");
      txtPrice.requestFocus();
      return false;
    }

    if (price <= 0) {
      lbMessage.setText("Giá sản phẩm phải lớn hơn 0.");
      return false;
    }

    lbMessage.setText("");
    return true;
  }
}
