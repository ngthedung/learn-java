package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.component.ExtendJButton;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJRadioButton;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.print.ReportPrint;
import com.hkt.client.swingexp.app.virtualkey.text.OpenVitualkeyboard;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.accounting.DefaultInvoiceCalculator;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.accounting.entity.InvoiceTransaction;
import com.hkt.module.accounting.entity.InvoiceTransaction.TransactionType;
import com.hkt.module.partner.customer.entity.Credit;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.customer.entity.Point;
import com.hkt.module.restaurant.entity.Table;

public class PanelPaymentProduct extends JPanel implements IDialogResto {
  private TableModelItem tableModle1, tableModle2;
  private ExtendJTextField txtQuantityProcessor;
  private ExtendJLabel lblNumQuantity;
  private ExtendJButton btnAdd, btnAddAll, btnRemove, btnRemoveAll;
  private ExtendJLabel lblTotalQuantity, lblQuantityProcessor;
  private JTable table, table1;
  private ExtendJTextField txtPersentTax, txtMoneyTax, txtMoneyBeforeTax, txtDiscountPersent, txtDiscountMoney;
  private PanelInfoPayment infoPaymentL, infoPaymentR;
  private InvoiceDetail invoiceDetail;
  private DateFormat df = new SimpleDateFormat("yyyyMMddHHssmm");
  private boolean save;
  private Profile profile;
  private List<InvoiceItem> invoiceItems;

  public boolean isSave() {
    return save;
  }

  public InvoiceDetail getInvoiceDetail() {
    return invoiceDetail;
  }

  public PanelPaymentProduct(InvoiceDetail invoiceDetail) {
    this.invoiceDetail = invoiceDetail;
    Hashtable<String, InvoiceItem> hashMap = new Hashtable<String, InvoiceItem>();
    for (int i = 0; i < invoiceDetail.getItems().size(); i++) {
      if (!invoiceDetail.getItems().get(i).getStatus().equals(AccountingModelManager.isCance)
          && !invoiceDetail.getItems().get(i).getStatus().equals(AccountingModelManager.isPayment)
          && !invoiceDetail.getItems().get(i).getStatus().equals(AccountingModelManager.isPromotion)) {
        hashMap.put(invoiceDetail.getItems().get(i).getItemCode(), invoiceDetail.getItems().get(i));
      }
    }

    tableModle1 = new TableModelItem(hashMap);
    tableModle2 = new TableModelItem(new Hashtable<String, InvoiceItem>());
    init();
    infoPaymentL.getLblTotalMoney().setText(tableModle1.getTotal());
    txtDiscountMoney.setEnabled(false);
    txtDiscountPersent.setEnabled(false);
    txtMoneyBeforeTax.setEnabled(false);
    txtMoneyTax.setEnabled(false);
    txtPersentTax.setEnabled(false);
  }

  public void init() {
    this.setLayout(new BorderLayout(15, 0));
    this.setOpaque(false);

    infoPaymentL = new PanelInfoPayment();
    table = new JTable();
    table.setModel(tableModle1);
    table.setName("tableL");
    table.setRowHeight(23);
    table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
    table.getColumnModel().getColumn(0).setMaxWidth(50);
    table.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent e) {
        JTable table = (JTable) e.getSource();
        if (table.getSelectedRow() >= 0) {
          double sl = 0;
          String strName = table.getValueAt(table.getSelectedRow(), 1).toString();
          for (int i = 0; i < table.getRowCount(); i++) {
            if (table.getValueAt(i, 1).toString().equals(strName)) {
              String str = table.getValueAt(i, 2).toString();
              sl = sl + MyDouble.parseDouble(str);
            }
          }
          lblNumQuantity.setText(MyDouble.valueOf(sl).toString());
          txtQuantityProcessor.setText("1");
        }
      }
    });
    for (int i = 0; i < table.getColumnCount(); i++) {
      table.getColumnModel().getColumn(i).setCellRenderer(new TableRerenderSale(true));
    }

    PanelTable tableL = new PanelTable(table);
    PANEL_CONTENT left = new PANEL_CONTENT(infoPaymentL, tableL);
    this.add(left, BorderLayout.LINE_START);

    PANEL_CENTER center = new PANEL_CENTER();
    this.add(center, BorderLayout.CENTER);

    infoPaymentR = new PanelInfoPayment();
    table1 = new JTable();
    table1.setModel(tableModle2);
    table1.setRowHeight(23);
    table1.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
    table1.getColumnModel().getColumn(0).setMaxWidth(50);
    table1.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent e) {
        JTable table = (JTable) e.getSource();
        if (table.getSelectedRow() >= 0) {
          double sl = 0;
          String strName = table.getValueAt(table.getSelectedRow(), 1).toString();
          for (int i = 0; i < table.getRowCount(); i++) {
            if (table.getValueAt(i, 1).toString().equals(strName)) {
              String str = table.getValueAt(i, 2).toString();
              sl = sl + MyDouble.parseDouble(str);
            }
          }
          lblNumQuantity.setText(MyDouble.valueOf(sl).toString());
          txtQuantityProcessor.setText("1");
        }
      }
    });
    for (int i = 0; i < table1.getColumnCount(); i++) {
      table1.getColumnModel().getColumn(i).setCellRenderer(new TableRerenderSale(true));
    }
    PanelTable tableR = new PanelTable(table1);
    PanelInfoTax infoR = new PanelInfoTax();
    PANEL_CONTENT right = new PANEL_CONTENT(infoPaymentR, tableR, infoR);
    this.add(right, BorderLayout.LINE_END);
    Profile profile = AccountModelManager.getInstance().getProfileConfigAdmin();
		if (profile.get(PanelRounding.TronLen) != null) {
			rounding = MyDouble.valueOf(profile.get(PanelRounding.TronLen).toString()).doubleValue();
		} else {
			if (profile.get(PanelRounding.TronPhay) != null) {
				rounding = MyDouble.valueOf(profile.get(PanelRounding.TronPhay).toString()).doubleValue() * -1;
			}
		}
  }

  private double rounding;
  protected class PANEL_CONTENT extends JPanel {
    private JPanel TOP, BOTTOM;
    private JScrollPane CENTER;

    public PANEL_CONTENT(JPanel top, JScrollPane center) {
      this.TOP = top;
      this.CENTER = center;
      this.setLayout(new BorderLayout(0, 3));
      this.add(TOP, BorderLayout.PAGE_START);
      this.add(CENTER, BorderLayout.CENTER);
      if  (Toolkit.getDefaultToolkit().getScreenSize().width <= 1024) {
      this.setPreferredSize(new Dimension(320, 300));
      } else {
    	  this.setPreferredSize(new Dimension(440, 300));
      }
      this.setBackground(new Color(156, 156, 156));
      this.setBorder(BorderFactory.createLineBorder(new Color(156, 156, 156), 5));
    }

    public PANEL_CONTENT(JPanel top, JScrollPane center, JPanel bottom) {
      this.TOP = top;
      this.CENTER = center;
      this.BOTTOM = bottom;
      this.setLayout(new BorderLayout(0, 3));
      this.add(TOP, BorderLayout.PAGE_START);
      this.add(CENTER, BorderLayout.CENTER);
      this.add(BOTTOM, BorderLayout.PAGE_END);
      if  (Toolkit.getDefaultToolkit().getScreenSize().width <= 1024) {
          this.setPreferredSize(new Dimension(320, 300));
          } else {
        	  this.setPreferredSize(new Dimension(440, 300));
          }
      this.setBackground(new Color(156, 156, 156));
      this.setBorder(BorderFactory.createLineBorder(new Color(156, 156, 156), 5));
    }
  }

  protected class PANEL_CENTER extends JPanel {
    private JPanel panel;

    public PANEL_CENTER() {
      init();
    }

    public void init() {
      this.setLayout(new GridLayout(6, 1, 1, 1));
//      this.setPreferredSize(new Dimension(85, 200));
      this.setOpaque(false);

      lblTotalQuantity = new ExtendJLabel("Tổng số lượng");
      lblNumQuantity = new ExtendJLabel("0");
      lblQuantityProcessor = new ExtendJLabel("Số lượng xử lý");
      lblTotalQuantity.setFont(new Font("Tahoma", Font.BOLD, 11));
      lblQuantityProcessor.setFont(new Font("Tahoma", Font.BOLD, 11));
      lblQuantityProcessor.setHorizontalAlignment(JLabel.CENTER);
      lblNumQuantity.setHorizontalAlignment(JLabel.CENTER);

      txtQuantityProcessor = new ExtendJTextField();
      txtQuantityProcessor.setPreferredSize(new Dimension(80, 22));
      txtQuantityProcessor.setHorizontalAlignment(JTextField.CENTER);
      txtQuantityProcessor.addCaretListener(new CaretListener() {
        @Override
        public void caretUpdate(CaretEvent e) {
          try {
            if (MyDouble.parseDouble(txtQuantityProcessor.getText()) > MyDouble.parseDouble(lblNumQuantity.getText())) {
              txtQuantityProcessor.setForeground(Color.red);
            } else {
              txtQuantityProcessor.setForeground(Color.black);
            }
          } catch (Exception e2) {
            txtQuantityProcessor.setForeground(Color.red);
          }

        }
      });
      btnAdd = new ExtendJButton(">");
      btnAddAll = new ExtendJButton(">>");
      btnRemove = new ExtendJButton("<");
      btnRemoveAll = new ExtendJButton("<<");

      btnAdd.setPreferredSize(new Dimension(80, 35));
      btnAddAll.setPreferredSize(new Dimension(80, 35));
      btnRemove.setPreferredSize(new Dimension(80, 35));
      btnRemoveAll.setPreferredSize(new Dimension(80, 35));

      btnAdd.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          InvoiceItem row = (InvoiceItem) tableModle1.getDataVector().get(table.getSelectedRow());
          if (row.getQuantity() >= MyDouble.parseDouble(txtQuantityProcessor.getText())) {
            tableModle2.updateValue(row, MyDouble.parseDouble(txtQuantityProcessor.getText()), false);
            tableModle1.updateValue(row, -MyDouble.parseDouble(txtQuantityProcessor.getText()), false);
          } else {
            tableModle2.updateValue(row, row.getQuantity(), false);
            tableModle1.updateValue(row, -row.getQuantity(), false);
            double ql = MyDouble.parseDouble(txtQuantityProcessor.getText()) - row.getQuantity();
            for (int i = 0; i < table.getRowCount(); i++) {
              InvoiceItem row1 = (InvoiceItem) tableModle1.getDataVector().get(i);
              String name = row.toString();
              if (i != table.getSelectedRow() && row.getItemName().equals(name)) {
                tableModle2.updateValue(row1, ql, false);
                tableModle1.updateValue(row1, -ql, false);
              }
            }

          }
          updateGuid();
        }
      });
      btnAddAll.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          Hashtable<String, InvoiceItem> hashMap = new Hashtable<String, InvoiceItem>();
          try {
            invoiceDetail = AccountingModelManager.getInstance().getInvoiceDetail(invoiceDetail.getId());
          } catch (Exception e2) {
          }

          for (int i = 0; i < invoiceDetail.getItems().size(); i++) {
            if (!invoiceDetail.getItems().get(i).getStatus().equals(AccountingModelManager.isCance)
                && !invoiceDetail.getItems().get(i).getStatus().equals(AccountingModelManager.isPayment)
                && !invoiceDetail.getItems().get(i).getStatus().equals(AccountingModelManager.isPromotion)) {
              hashMap.put(invoiceDetail.getItems().get(i).getItemCode(), invoiceDetail.getItems().get(i));
            }
          }
          tableModle2.setHashMap(hashMap);
          tableModle1.setHashMap(new Hashtable<String, InvoiceItem>());
          updateGuid();
        }
      });
      btnRemove.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          InvoiceItem row = (InvoiceItem) tableModle2.getDataVector().get(table1.getSelectedRow());
          if (row.getId() != null) {
            InvoiceItem i = createInvoiceItem(row);
            if (row.getQuantity() >= MyDouble.parseDouble(txtQuantityProcessor.getText())) {
              tableModle1.updateValue(row, MyDouble.parseDouble(txtQuantityProcessor.getText()), true);
              tableModle2.updateValue(i, -MyDouble.parseDouble(txtQuantityProcessor.getText()), false);
            } else {
              tableModle1.updateValue(row, row.getQuantity(), true);
              tableModle2.updateValue(row, -row.getQuantity(), false);
              double ql = MyDouble.parseDouble(txtQuantityProcessor.getText()) - row.getQuantity();
              for (int j = 0; j < table1.getRowCount(); j++) {
                InvoiceItem row1 = (InvoiceItem) tableModle2.getDataVector().get(j);
                String name = row.toString();
                if (j != table1.getSelectedRow() && row.getItemName().equals(name)) {
                  if(row1.getId()!=null){
                    InvoiceItem h = createInvoiceItem(row1);
                    tableModle1.updateValue(row1, ql, true);
                    tableModle2.updateValue(h, -ql, true);
                  }else {
                    tableModle1.updateValue(row1, ql, false);
                    tableModle2.updateValue(row1, -ql, false);
                  }
                 
                }
              }

            }
          } else {
            if (row.getQuantity() >= MyDouble.parseDouble(txtQuantityProcessor.getText())) {
              tableModle1.updateValue(row, MyDouble.parseDouble(txtQuantityProcessor.getText()), false);
              tableModle2.updateValue(row, -MyDouble.parseDouble(txtQuantityProcessor.getText()), false);
            } else {
              tableModle1.updateValue(row, row.getQuantity(), false);
              tableModle2.updateValue(row, -row.getQuantity(), false);
              double ql = MyDouble.parseDouble(txtQuantityProcessor.getText()) - row.getQuantity();
              for (int j = 0; j < table1.getRowCount(); j++) {
                InvoiceItem row1 = (InvoiceItem) tableModle2.getDataVector().get(j);
                String name = row.toString();
                if (j != table1.getSelectedRow() && row.getItemName().equals(name)) {
                  tableModle1.updateValue(row1, ql, false);
                  tableModle2.updateValue(row1, -ql, false);
                }
              }

            }
           
          }
          updateGuid();
        }
      });
      btnRemoveAll.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          Hashtable<String, InvoiceItem> hashMap = new Hashtable<String, InvoiceItem>();
          try {
            invoiceDetail = AccountingModelManager.getInstance().getInvoiceDetail(invoiceDetail.getId());
          } catch (Exception e2) {
          }

          for (int i = 0; i < invoiceDetail.getItems().size(); i++) {
            if (!invoiceDetail.getItems().get(i).getStatus().equals(AccountingModelManager.isCance)
                && !invoiceDetail.getItems().get(i).getStatus().equals(AccountingModelManager.isPayment)
                && !invoiceDetail.getItems().get(i).getStatus().equals(AccountingModelManager.isPromotion)) {
              hashMap.put(invoiceDetail.getItems().get(i).getItemCode(), invoiceDetail.getItems().get(i));
            }
          }
          tableModle1.setHashMap(hashMap);
          tableModle2.setHashMap(new Hashtable<String, InvoiceItem>());
          updateGuid();
        }
      });

      panel = new JPanel();
      panel.setOpaque(false);
//      this.add(panel);

      JLabel labelEmpty = new JLabel();
      labelEmpty.setPreferredSize(new Dimension(80, 5));
      panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
      panel.setOpaque(false);
//      panel.add(labelEmpty);
      panel.add(lblTotalQuantity);
      panel.add(lblNumQuantity);
      this.add(panel);

      panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
      panel.setOpaque(false);
      panel.add(lblQuantityProcessor);
      panel.add(txtQuantityProcessor);
      this.add(panel);

      panel = new JPanel(new GridLayout(2, 1, 1, 6));
      panel.setOpaque(false);
      panel.add(btnAddAll);
      panel.add(btnAdd);
      this.add(panel);

      panel = new JPanel(new GridLayout(2, 1, 1, 6));
      panel.setOpaque(false);
      panel.add(btnRemove);
      panel.add(btnRemoveAll);
      this.add(panel);

//      panel = new JPanel();
//      panel.setOpaque(false);
//      this.add(panel);

    }
  }

  protected class PanelInfoPayment extends JPanel {
    private ExtendJLabel lblNameTable, lblTable, lblCountGuest, lblUnitMoney, lblTotalMoney,lblNumberMoney;
    private ExtendJTextField txtCountGuest;
    private JPanel PANEL1, PANEL2, LINE1, LINE2;

    public ExtendJLabel getLblTotalMoney() {
      return lblNumberMoney;
    }

    public PanelInfoPayment() {
      this.setOpaque(false);

      lblNameTable = new ExtendJLabel("Tên bàn");
      lblTable = new ExtendJLabel("");
      lblTotalMoney = new ExtendJLabel("Tổng tiền");
      lblNumberMoney = new ExtendJLabel("0");
      lblUnitMoney = new ExtendJLabel("VNĐ");
      lblCountGuest = new ExtendJLabel("Số khách");
      txtCountGuest = new ExtendJTextField();
      try {
			profile = AccountModelManager.getInstance().getProfileConfigAdmin();
			if (profile.get(DialogConfig.Keyboard)!=null&&profile.get(DialogConfig.Keyboard).toString().equals("true")) {
				txtCountGuest.addMouseListener(new MouseAdapter() {
					@Override
					  public void mouseClicked(MouseEvent e) {
					   OpenVitualkeyboard.getInstancce().vitualTextKeyboard(txtCountGuest);
					  }
				});
			}

		} catch (Exception e) {
		}

      lblTotalMoney.setPreferredSize(new Dimension(100, 22));
      lblTotalMoney.setForeground(Color.WHITE);
      lblTotalMoney.setFont(new Font("Tahoma", Font.BOLD, 20));
      lblNumberMoney.setForeground(Color.WHITE);
      lblNumberMoney.setFont(new Font("Tahoma", Font.BOLD, 20));
      lblNumberMoney.setHorizontalAlignment(JLabel.RIGHT);
      lblUnitMoney.setForeground(Color.WHITE);
      lblUnitMoney.setFont(new Font("Tahoma", Font.BOLD, 20));
      lblUnitMoney.setHorizontalAlignment(JLabel.RIGHT);

      LINE1 = new JPanel();
      LINE2 = new JPanel();
      PANEL1 = new JPanel();
      PANEL2 = new JPanel();

      LINE1.setLayout(new FlowLayout(FlowLayout.LEFT));
      LINE1.setOpaque(false);
      LINE1.add(lblNameTable);
      LINE1.add(lblTable);

      LINE2.setLayout(new FlowLayout(FlowLayout.LEFT));
      LINE2.setOpaque(false);
      LINE2.add(lblCountGuest);
      LINE2.add(txtCountGuest);

      PANEL1.setLayout(new GridLayout(2, 1, 0, 1));
      PANEL1.setOpaque(false);
      PANEL1.add(LINE1);
      PANEL1.add(LINE2);

      LINE1 = new JPanel();
      LINE1.setOpaque(false);
      LINE1.setLayout(new BorderLayout(5, 0));
      LINE1.add(lblNumberMoney, BorderLayout.CENTER);
      LINE1.add(lblUnitMoney, BorderLayout.LINE_END);
      PANEL2.setLayout(new BorderLayout(0, 0));
      PANEL2.setBackground(Color.BLACK);
      PANEL2.add(lblTotalMoney, BorderLayout.LINE_START);
      PANEL2.add(LINE1, BorderLayout.LINE_END);
      PANEL2.setPreferredSize(new Dimension(300, 20));

      this.setLayout(new BorderLayout(0, 0));
      this.add(PANEL1, BorderLayout.CENTER);
      this.add(PANEL2, BorderLayout.PAGE_END);
    }
  }
  
  

  public ExtendJTextField getTxtMoneyBeforeTax() {
		return txtMoneyBeforeTax;
	}

	protected class PanelInfoTax extends JPanel {
    private JPanel panel;
    private ExtendJLabel lblPersentTax, lblMoneyTax, lblMoneyBeforeTax;
    private ExtendJRadioButton rdDiscountPersent, rdDiscountmoney;

    public PanelInfoTax() {
      this.setLayout(new GridLayout(3, 2, 5, 5));
      lblPersentTax = new ExtendJLabel("Thuế %");
      lblMoneyTax = new ExtendJLabel("Tiền thuế");
      lblMoneyBeforeTax = new ExtendJLabel("Tiền sau thuế");
      rdDiscountmoney = new ExtendJRadioButton("Chiết khấu tiền");
      rdDiscountPersent = new ExtendJRadioButton("Chiết khấu %");
      txtDiscountMoney = new ExtendJTextField();
      txtDiscountPersent = new ExtendJTextField();
      txtMoneyBeforeTax = new ExtendJTextField();
      txtMoneyTax = new ExtendJTextField();
      txtPersentTax = new ExtendJTextField();

      txtPersentTax.setHorizontalAlignment(JTextField.RIGHT);
      txtMoneyTax.setHorizontalAlignment(JTextField.RIGHT);
      txtMoneyBeforeTax.setHorizontalAlignment(JTextField.RIGHT);
      txtDiscountPersent.setHorizontalAlignment(JTextField.RIGHT);
      txtDiscountMoney.setHorizontalAlignment(JTextField.RIGHT);

      rdDiscountPersent.setPreferredSize(new Dimension(130, 22));
      rdDiscountmoney.setPreferredSize(new Dimension(130, 22));
      lblPersentTax.setPreferredSize(new Dimension(130, 22));
      lblMoneyTax.setPreferredSize(new Dimension(130, 22));
      lblMoneyBeforeTax.setPreferredSize(new Dimension(130, 22));

      panel = new JPanel();
      panel.setLayout(new BorderLayout(0, 0));
      panel.add(lblPersentTax, BorderLayout.LINE_START);
      panel.add(txtPersentTax, BorderLayout.CENTER);
      this.add(panel);

      panel = new JPanel();
      panel.setLayout(new BorderLayout(0, 0));
      panel.add(rdDiscountPersent, BorderLayout.LINE_START);
      panel.add(txtDiscountPersent, BorderLayout.CENTER);
      this.add(panel);

      panel = new JPanel();
      panel.setLayout(new BorderLayout(0, 0));
      panel.add(lblMoneyTax, BorderLayout.LINE_START);
      panel.add(txtMoneyTax, BorderLayout.CENTER);
      this.add(panel);

      panel = new JPanel();
      panel.setLayout(new BorderLayout(0, 0));
      panel.add(rdDiscountmoney, BorderLayout.LINE_START);
      panel.add(txtDiscountMoney, BorderLayout.CENTER);
      this.add(panel);

      panel = new JPanel();
      panel.setLayout(new BorderLayout(0, 0));
      panel.add(lblMoneyBeforeTax, BorderLayout.LINE_START);
      panel.add(txtMoneyBeforeTax, BorderLayout.CENTER);
      this.add(panel);
    }
  }

  public class PanelTable extends JScrollPane {

    public PanelTable(JTable table) {
      this.setViewportView(table);
      this.getViewport().setBackground(Color.WHITE);
    }
  }

  public class TableModelItem extends DefaultTableModel {

    private String[] header = { "STT", "Tên", "Số lượng", "Đơn giá", "Tiền" };

    public String getTotal() {
      double total = 0;
      Iterator<InvoiceItem> iterator = hashMap.values().iterator();
      while (iterator.hasNext()) {
        total = total + iterator.next().getTotal();
      }
      return MyDouble.valueOf(total).toString();
    }

    public String getDiscountRate() {
      try {
        return MyDouble.valueOf(hashMap.values().iterator().next().getDiscountRate()).toString();
      } catch (Exception e) {
        return "0";
      }

    }

    public String getDiscount() {
      double discount = (MyDouble.parseDouble(getTotal()) * MyDouble.parseDouble(getDiscountRate())) / 100d;
      return MyDouble.valueOf(discount).toString();
    }

    public String getTaxRate() {
      try {
        return MyDouble.valueOf(hashMap.values().iterator().next().getTaxRate()).toString();
      } catch (Exception e) {
       return "0";
      }
     
    }
    
    private MyDouble getMoney(double moneyMissing1) {
  		MyDouble a = new MyDouble(moneyMissing1);
  		if (rounding != 0) {
  			if (rounding > 0) {
  				moneyMissing1 = moneyMissing1 / rounding;
  				MyDouble b = new MyDouble(moneyMissing1);
  				b.setNumDot(0);

  				if (moneyMissing1 > new MyDouble(b.toString()).doubleValue()) {
  					moneyMissing1 = moneyMissing1 + 1;
  				}
  				MyDouble d = new MyDouble(moneyMissing1);
  				d.setNumDot(0);
  				MyDouble c = new MyDouble(d.toString());
  				moneyMissing1 = c.doubleValue() * rounding;
  				a = new MyDouble(moneyMissing1);
  				a.setNumDot(0);
  				moneyMissing1 = a.doubleValue();
  			} else {
  				try {
  					int k = MyDouble.valueOf(rounding).intValue() * (-1);
  					a = new MyDouble(moneyMissing1);
  					a.setNumDot(k);
  				} catch (Exception e) {
  				}
  			}
  		}
  		return a;
  	}

    public String getTax() {
      double discount = (MyDouble.parseDouble(getTotal()) * MyDouble.parseDouble(getTaxRate())) / 100d;
      return MyDouble.valueOf(discount).toString();
    }

    public String getFinalChange() {
      double finalChage = MyDouble.parseDouble(getTotal()) - MyDouble.parseDouble(getDiscount())
          + MyDouble.parseDouble(getTax());
      return getMoney(finalChage).toString();
    }

    private Hashtable<String, InvoiceItem> hashMap;

    public Vector getHeader() {
      return Vector.class.cast(header);
    }

    public Hashtable<String, InvoiceItem> getHashMap() {
      return hashMap;
    }

    public void setHashMap(Hashtable<String, InvoiceItem> hashMap) {
      this.hashMap = hashMap;
      dataVector = convertToVector(this.hashMap.values().toArray());
      fireTableDataChanged();
    }

    public void updateValue(InvoiceItem invoiceItem, double quantity, boolean flag) {
      InvoiceItem rowVector = hashMap.get(invoiceItem.getItemCode());
      if (rowVector != null) {
        if (invoiceItem.getQuantity() == quantity || flag) {
          if (invoiceItem.getId() != null || flag) {
            if(invoiceItem.getQuantity()==quantity){
              invoiceItem.setQuantity(rowVector.getQuantity()+ quantity);
            }else {
              invoiceItem.setQuantity(rowVector.getQuantity());
            }
            
            invoiceItem.setTotal(invoiceItem.getQuantity() * rowVector.getUnitPrice());
            hashMap.remove(rowVector.getItemCode());
            hashMap.put(invoiceItem.getItemCode(), invoiceItem);
            dataVector = convertToVector(this.hashMap.values().toArray());
            txtQuantityProcessor.setText(MyDouble.valueOf(invoiceItem.getQuantity()).toString().trim());
          }else {
            rowVector.setQuantity(quantity + rowVector.getQuantity());
            rowVector.setTotal(rowVector.getQuantity() * rowVector.getUnitPrice());
          }
        } else {
          if (quantity + rowVector.getQuantity() != 0) {
            rowVector.setQuantity(quantity + rowVector.getQuantity());
            rowVector.setTotal(rowVector.getQuantity() * rowVector.getUnitPrice());
          } else {
            hashMap.remove(invoiceItem.getItemCode());
            dataVector = convertToVector(this.hashMap.values().toArray());
          }
        }
        fireTableDataChanged();
      } else {
        if (invoiceItem.getQuantity() == quantity || flag) {
          invoiceItem.setQuantity(quantity);
          invoiceItem.setTotal(invoiceItem.getQuantity() * invoiceItem.getUnitPrice());
          addItem(invoiceItem.getItemCode(), invoiceItem);
        } else {
          InvoiceItem bean = createInvoiceItem(invoiceItem);
          bean.setQuantity(quantity);
          bean.setTotal(bean.getQuantity() * invoiceItem.getUnitPrice());
          addItem(bean.getItemCode(), bean);
        }

      }
    }

    public TableModelItem(Hashtable<String, InvoiceItem> hashMap) {
      if (hashMap == null) {
        this.hashMap = new Hashtable<String, InvoiceItem>();
      } else {
        this.hashMap = hashMap;
      }

      dataVector = convertToVector(this.hashMap.values().toArray());
    }

    public void addItem(String code, InvoiceItem invoiceItem) {
      hashMap.put(code, invoiceItem);
      int row = getRowCount();
      dataVector.insertElementAt(invoiceItem, row);
      fireTableRowsInserted(row, row);
    }

    @Override
    public int getColumnCount() {
      return header == null ? 0 : header.length;
    }

    @Override
    public String getColumnName(int column) {
      return header[column];
    }

    @Override
    public boolean isCellEditable(int row, int column) {
      return false;
    }

    @Override
    public void removeRow(int row) {
      InvoiceItem rowVector = (InvoiceItem) dataVector.get(row);
      hashMap.remove(rowVector.getItemCode());
      super.removeRow(row);
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
      if (column == 2) {
        InvoiceItem rowVector = (InvoiceItem) dataVector.elementAt(row);
        rowVector.setQuantity(Double.parseDouble(aValue.toString()));
        rowVector.setTotal(rowVector.getQuantity() * rowVector.getUnitPrice());
        fireTableRowsUpdated(row, row);
      } else {
        super.setValueAt(aValue, row, column);
      }
    }

    @Override
    public Object getValueAt(int row, int column) {
      switch (column) {
      case 0:
        return row + 1;
      case 1:
        try {
          return ((InvoiceItem) dataVector.get(row));
        } catch (Exception e) {
          Vector rowVector = (Vector) dataVector.elementAt(row);
          return rowVector.elementAt(column);
        }

      case 2:
        try {
          return MyDouble.valueOf(((InvoiceItem) dataVector.get(row)).getQuantity()).toString();
        } catch (Exception e) {
          Vector rowVector = (Vector) dataVector.elementAt(row);
          return rowVector.elementAt(column);
        }

      case 3:
        try {
          return MyDouble.valueOf(((InvoiceItem) dataVector.get(row)).getUnitPrice()).toString();
        } catch (Exception e) {
          Vector rowVector = (Vector) dataVector.elementAt(row);
          return rowVector.elementAt(column);
        }

      case 4:
        try {
          return MyDouble.valueOf(((InvoiceItem) dataVector.get(row)).getTotal()).toString();
        } catch (Exception e) {
          Vector rowVector = (Vector) dataVector.elementAt(row);
          return rowVector.elementAt(column);
        }

      default:
        return "";
      }
    }
  }
  
  public List<InvoiceItem> getInvoiceItemPayment(){
  	return invoiceItems;
  }

  @Override
  public void Save() throws Exception {
    Iterator<InvoiceItem> iterator1 = tableModle2.getHashMap().values().iterator();
    invoiceItems = new ArrayList<InvoiceItem>();
    while (iterator1.hasNext()) {
      InvoiceItem invoiceItem = iterator1.next();
      invoiceItems.add(invoiceItem);
      invoiceItem.setStatus(AccountingModelManager.isPayment);
      if (invoiceItem.getId() == null) {
        invoiceItem.setItemCode(df.format(new Date()));
        invoiceDetail.add(invoiceItem);
      }
    }
    InvoiceTransaction transaction = new InvoiceTransaction();
    transaction.setTransactionType(TransactionType.Cash);
    transaction.setCreatedBy(ManagerAuthenticate.getInstance().getLoginId());
    transaction.setCurrencyUnit("VND");
    transaction.setTotal(MyDouble.parseDouble(txtMoneyBeforeTax.getText()));
    transaction.setTransactionDate(new Date());
    invoiceDetail.add(transaction);
    invoiceDetail.calculate(new DefaultInvoiceCalculator());
    invoiceDetail = AccountingModelManager.getInstance().saveInvoiceDetail(invoiceDetail);
    save = true;
    ((JDialog) getRootPane().getParent()).dispose();
  }

  private InvoiceItem createInvoiceItem(InvoiceItem bean) {
    InvoiceItem invoiceItem = new InvoiceItem();
    invoiceItem.setItemCode(bean.getItemCode());
    invoiceItem.setItemName(bean.getItemName());
    invoiceItem.setUnitPrice(bean.getUnitPrice());
    invoiceItem.setQuantity(bean.getQuantity());
    invoiceItem.setTotal(bean.getTotal());
    invoiceItem.setDiscountRate(bean.getDiscountRate());
    invoiceItem.setTaxRate(bean.getTaxRate());
    invoiceItem.setCurrencyUnit(bean.getCurrencyUnit());
    invoiceItem.setStatus(bean.getStatus());
    invoiceItem.setActivityType(bean.getActivityType());
    invoiceItem.setStartDate(bean.getStartDate());
    invoiceItem.setProductCode(bean.getProductCode());
    return invoiceItem;
  }

  private void updateGuid() {
    infoPaymentL.getLblTotalMoney().setText(tableModle1.getTotal());
    infoPaymentR.getLblTotalMoney().setText(tableModle2.getTotal());
    txtDiscountMoney.setText(tableModle2.getDiscount());
    txtDiscountPersent.setText(tableModle2.getDiscountRate());
    txtMoneyTax.setText(tableModle2.getTax());
    txtPersentTax.setText(tableModle2.getTaxRate());
    txtMoneyBeforeTax.setText(tableModle2.getFinalChange());
  }

}
