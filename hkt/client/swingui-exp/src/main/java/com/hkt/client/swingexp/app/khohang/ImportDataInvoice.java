package com.hkt.client.swingexp.app.khohang;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.component.GridLabelLayoutPabel;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.module.accounting.entity.Attribute;
import com.hkt.module.accounting.entity.Contributor;
import com.hkt.module.accounting.entity.Invoice.ActivityType;
import com.hkt.module.accounting.entity.Invoice.Status;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.accounting.entity.InvoiceItemAttribute;
import com.hkt.module.accounting.entity.InvoiceTransaction;

public class ImportDataInvoice extends GridLabelLayoutPabel implements IDialogMain {
  private JTextField    txtMaxRow, txtTotal, txtDiscount, txtDiscountRate, txtFinalCharge, txtType;
  private JComboBox     cbActivityType, cbStatus;
  private InvoiceDetail invoiceDetail;

  public ImportDataInvoice(InvoiceDetail invoiceDetail) {
    this.invoiceDetail = invoiceDetail;
    setOpaque(false);
    txtMaxRow = new JTextField();
    txtTotal = new JTextField();
    txtDiscount = new JTextField();
    txtDiscountRate = new JTextField();
    txtFinalCharge = new JTextField();
    String[] stringActivity = { ActivityType.Payment.toString(), ActivityType.Receipt.toString() };
    cbActivityType = new JComboBox(stringActivity);
    String[] stringStatus = { Status.Paid.toString(), Status.PartiallyPaid.toString(), Status.WaitingPaid.toString() };
    cbStatus = new JComboBox(stringStatus);
    txtType = new JTextField();
    add(0, 0, new JLabel("Imput max row:"));
    add(0, 1, txtMaxRow);
    add(1, 0, new JLabel("Total price:"));
    add(1, 1, txtTotal);
    add(2, 0, new JLabel("Discount:"));
    add(2, 1, txtDiscount);
    add(3, 0, new JLabel("Discount rate:"));
    add(3, 1, txtDiscountRate);
    add(4, 0, new JLabel("Final charge:"));
    add(4, 1, txtFinalCharge);
    add(5, 0, new JLabel("Type:"));
    add(5, 1, txtType);
    add(6, 0, new JLabel("Activity type:"));
    add(6, 1, cbActivityType);
    add(7, 0, new JLabel("Status:"));
    add(7, 1, cbStatus);
  }

  @Override
  public void save() throws Exception {
    double count = Double.parseDouble(txtMaxRow.getText());
    for (int i = 0; i < count; i++) {
      invoiceDetail.setId(-1L);
      invoiceDetail.setInvoiceCode("test.25.09-2" + i);
      if (cbActivityType.getSelectedItem().toString().equals(ActivityType.Receipt.toString())) {
        invoiceDetail.setActivityType(ActivityType.Receipt);
      }else{
        invoiceDetail.setActivityType(ActivityType.Payment);
      }
      List<Attribute> attributes = invoiceDetail.getAttributes();
      for (Attribute attribute : attributes) {
        attribute.setId(-1L);
      }
      List<InvoiceTransaction> invoiceTransactions = invoiceDetail.getTransactions();
      for (InvoiceTransaction invoiceTransaction : invoiceTransactions) {
        invoiceTransaction.setId(-1L);
      }
      List<Contributor> contributors = invoiceDetail.getContributors();
      for (Contributor contributor : contributors) {
        contributor.setId(-1L);
      }
      List<InvoiceItem> items = invoiceDetail.getItems();
      for (InvoiceItem invoiceItem : items) {
        invoiceItem.setItemCode(count + "test");
        invoiceItem.setInvoiceCode(invoiceDetail.getInvoiceCode());
        invoiceItem.setId(-1L);
        List<InvoiceItemAttribute> itemAttributes = invoiceItem.getReferences();
        for (InvoiceItemAttribute invoiceItemAttribute : itemAttributes) {
          invoiceItemAttribute.setId(-1L);
        }
      }
      // InvoiceDetail invoiceDetail = new InvoiceDetail();
      // invoiceDetail.setInvoiceCode(dateFormat.format(new Date()) + i);
      // if
      // (cbActivityType.getSelectedItem().toString().equals(ActivityType.Payment.toString()))
      // {
      // invoiceDetail.setActivityType(ActivityType.Payment);
      // }
      // if
      // (cbActivityType.getSelectedItem().toString().equals(ActivityType.Receipt.toString()))
      // {
      // invoiceDetail.setActivityType(ActivityType.Receipt);
      // }
      // if
      // (cbStatus.getSelectedItem().toString().equals(Status.Paid.toString()))
      // {
      // invoiceDetail.setStatus(Status.Paid);
      // }
      // if
      // (cbStatus.getSelectedItem().toString().equals(Status.PartiallyPaid.toString()))
      // {
      // invoiceDetail.setStatus(Status.PartiallyPaid);
      // }
      // if
      // (cbStatus.getSelectedItem().toString().equals(Status.WaitingPaid.toString()))
      // {
      // invoiceDetail.setStatus(Status.WaitingPaid);
      // }
      // invoiceDetail.setType(txtType.getText());
      // invoiceDetail.setTotal(Double.parseDouble(txtTotal.getText()));
      // if (txtDiscount.getText().trim().length() > 0)
      // invoiceDetail.setDiscountByItem(Double.parseDouble(txtDiscount.getText()));
      // if (txtDiscount.getText().trim().length() > 0)
      // invoiceDetail.setDiscount(Double.parseDouble(txtDiscount.getText()));
      // if (txtDiscountRate.getText().trim().length() > 0)
      // invoiceDetail.setDiscountRate(Double.parseDouble(txtDiscountRate.getText()));
      // invoiceDetail.setTotalPaid(Double.parseDouble(txtTotal.getText()));
      // invoiceDetail.setTotalTax(0);
      // invoiceDetail.setPoint(0);
      // invoiceDetail.setCredit(0);
      // invoiceDetail.setFinalCharge(Double.parseDouble(txtFinalCharge.getText()));
      // invoiceDetail.setCurrencyRate(0);
      // invoiceDetail.setCurrencyUnit("VND");
      // Calendar calendar = Calendar.getInstance();
      // calendar.setTime(new Date());
      // calendar.add(Calendar.DATE, i);
      // invoiceDetail.setStartDate(calendar.getTime());
      // invoiceDetail.setEndDate(calendar.getTime());
      AccountingModelManager.getInstance().saveInvoiceDetail(invoiceDetail);
    }
    JOptionPane.showMessageDialog(null, "Success !");
  }

  @Override
  public void edit() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void delete() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void reset() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void refresh() throws Exception {
    // TODO Auto-generated method stub

  }

}
