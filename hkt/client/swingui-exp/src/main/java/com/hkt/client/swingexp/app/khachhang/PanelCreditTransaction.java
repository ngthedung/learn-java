package com.hkt.client.swingexp.app.khachhang;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextArea;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.component.TextPopup;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.IMyObserver;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.accounting.DefaultInvoiceCalculator;
import com.hkt.module.accounting.entity.Invoice.ActivityType;
import com.hkt.module.accounting.entity.Invoice.Status;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.accounting.entity.InvoiceTransaction;
import com.hkt.module.accounting.entity.InvoiceTransaction.TransactionType;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.partner.customer.entity.Credit;
import com.hkt.module.partner.customer.entity.CreditTransaction;
import com.hkt.module.partner.customer.entity.Customer;

@SuppressWarnings("serial")
public class PanelCreditTransaction extends MyPanel implements IDialogMain, IMyObserver {
  public static String permisson;

  private ExtendJTextField txtTotalCredit, txtCreditUsed, txtCreditLeft;
  private ExtendJLabel lblPartner, lblTotalCredit, lblCreditUsed, lblCreditLeft, lblType, lblDescription, lblMessage;
  private ExtendJComboBox cbType;
  private ExtendJTextArea txtDescription;
  private TextPopup<Customer> txtCustomer;
  private JScrollPane scrollPane;
  private String[] itemsType = { "Cộng thêm", "Giảm đi" };
  private Credit credit = null;
  private CreditTransaction creditTransaction;
  private Long idCreditTest = null;

  // Hàm tạo
  public PanelCreditTransaction() {
    creditTransaction = new CreditTransaction();
    init();

    
    cbType.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (!txtTotalCredit.getText().equals("") && txtTotalCredit.getText() != null
            && !txtCreditUsed.getText().equals("") && txtCreditUsed.getText() != null) {
          calculateCredit();
        }
      }
    });

    txtCreditUsed.addCaretListener(new CaretListener() {

      @Override
      public void caretUpdate(CaretEvent e) {
        try {
          calculateCredit();
          lblMessage.setText("");
          lblCreditUsed.setForeground(new Color(51, 51, 51));
        } catch (Exception ex) {
          lblMessage.setText("Vui lòng nhập số");
          lblCreditUsed.setForeground(Color.RED);
        }

      }
    });
    if (txtCustomer.getItem()!=null) {
      try {
        credit = CustomerModelManager.getInstance().getCreditByCustomerId(txtCustomer.getItem().getId());
        if (credit == null) {
          credit = new Credit();
          credit.setLoginId(txtCustomer.getItem().getLoginId());
          credit.setCustomerId(txtCustomer.getItem().getId());
          credit.setCredit(0);
          credit = CustomerModelManager.getInstance().saveCredit(credit);
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
      txtTotalCredit.setText(MyDouble.valueOf(credit.getCredit()).toString());
    }
  }

  // Hàm tính toán tiền sử dụng
  private void calculateCredit() {
    double creditLeft = 0;
    if (cbType.getSelectedIndex() == 0) {
      creditLeft = MyDouble.parseDouble(txtTotalCredit.getText()) + MyDouble.parseDouble(txtCreditUsed.getText());
    } else {
      creditLeft = MyDouble.parseDouble(txtTotalCredit.getText()) - MyDouble.parseDouble(txtCreditUsed.getText());
    }
    txtCreditLeft.setText(MyDouble.valueOf(creditLeft).toString());
  }

  // Hàm cho phép người dùng nhập chỉnh sửa hay không?
  public void setEnabledCompoment(boolean value) {
    txtCreditUsed.setEnabled(value);
    txtDescription.setEnabled(value);
    txtCustomer.setEnabled(value);
    cbType.setEnabled(value);
  }

  // Lấy dữ liệu từ giao diện đổ vào đối tượng
  public void getData(Credit c, CreditTransaction ct) {
    this.credit = c;
    this.creditTransaction = ct;
    credit.setCredit(MyDouble.parseDouble(txtCreditLeft.getText()));
    creditTransaction.setCreditId(credit.getId());
    creditTransaction.setLoginId(credit.getLoginId());
    creditTransaction.setDescription(txtDescription.getText());
    if (cbType.getSelectedIndex() == 0)
      creditTransaction.setAmount(MyDouble.parseDouble(txtCreditUsed.getText()));
    if (cbType.getSelectedIndex() == 1)
      creditTransaction.setAmount(-MyDouble.parseDouble(txtCreditUsed.getText()));

    creditTransaction.setDateExecute(new Date());
  }

  // Hiện thị dữ liệu ra giao diện
  public void setData(Credit c, CreditTransaction ct) {
    this.credit = c;
    this.creditTransaction = ct;
    for(Customer cus: txtCustomer.getData()){
    	if(cus.getId().toString().equals(String.valueOf(c.getCustomerId()))){
    		txtCustomer.setItem(cus);
    		break;
    	}
    }
    txtTotalCredit.setText(MyDouble.valueOf(c.getCredit()).toString());
    txtDescription.setText(ct.getDescription());
    double numPointUsed = ct.getAmount();
    if (numPointUsed < 0) {
      cbType.setSelectedIndex(1);
      txtCreditUsed.setText(MyDouble.valueOf(ct.getAmount() / (-1)).toString());
    }
    if (numPointUsed > 0) {
      cbType.setSelectedIndex(0);
      txtCreditUsed.setText(MyDouble.valueOf(ct.getAmount()).toString());
    }
    setEnabledCompoment(false);
  }

  // Khởi tạo các components và sắp xếp vị trí trên giao diện
  @SuppressWarnings("unchecked")
  private void init() {
    createBeginTest();
    lblPartner = new ExtendJLabel("Khách hàng");
    lblTotalCredit = new ExtendJLabel("Tổng tiền");
    lblCreditUsed = new ExtendJLabel("Tiền SD");
    lblCreditLeft = new ExtendJLabel("Tiền còn lại");
    lblType = new ExtendJLabel("Loại");
    lblDescription = new ExtendJLabel("Ghi chú");
    lblMessage = new ExtendJLabel("");
    lblMessage.setForeground(Color.RED);

    txtCustomer = new TextPopup<Customer>(Customer.class);
		try {
			txtCustomer.setData(CustomerModelManager.getInstance().getCustomers(false));
		} catch (Exception e) {
		}
		txtCustomer.addObserver(this);
    cbType = new ExtendJComboBox();
    cbType.setModel(new DefaultComboBoxModel<String>(itemsType));

    txtCreditLeft = new ExtendJTextField();
    txtCreditUsed = new ExtendJTextField();
    txtTotalCredit = new ExtendJTextField();
    txtDescription = new ExtendJTextArea();
    scrollPane = new JScrollPane();
    scrollPane.setViewportView(txtDescription);

    txtCreditLeft.setHorizontalAlignment(JTextField.RIGHT);
    txtCreditUsed.setHorizontalAlignment(JTextField.RIGHT);
    txtTotalCredit.setHorizontalAlignment(JTextField.RIGHT);

    txtTotalCredit.setEnabled(false);
    txtCreditLeft.setEnabled(false);

    txtCreditUsed.setName("txtTienSD");
    txtDescription.setName("txtGhiChu");
    cbType.setName("cbLoai");

    MyGroupLayout layout = new MyGroupLayout(this);
    layout.add(0, 0, lblPartner);
    layout.add(0, 1, txtCustomer);
    layout.add(1, 0, lblTotalCredit);
    layout.add(1, 1, txtTotalCredit);
    layout.add(1, 2, lblCreditLeft);
    layout.add(1, 3, txtCreditLeft);
    layout.add(2, 0, lblCreditUsed);
    layout.add(2, 1, txtCreditUsed);
    layout.add(2, 2, lblType);
    layout.add(2, 3, cbType);
    layout.add(3, 0, lblDescription);
    layout.add(3, 1, scrollPane);
    layout.addMessenger(lblMessage);
    layout.updateGui();
  }

  // Kiểm tra dữ liệu trước khi lưu
  public boolean checkData() {
    if (credit != null) {
      double numCreditUsed = 0;
      int count = 0;
      try {
        numCreditUsed = MyDouble.parseDouble(txtCreditUsed.getText());
        if (numCreditUsed <= 0) {
          lblMessage.setText("Nhập số điểm dùng lớn hơn 0");
          lblCreditLeft.setForeground(Color.RED);
          count++;
        } else {
          lblMessage.setText("");
          lblCreditLeft.setForeground(new Color(51, 51, 51));
        }

        if (MyDouble.parseDouble(txtCreditLeft.getText()) < 0) {
          count++;
        } else {
          lblMessage.setText("");
          lblCreditLeft.setForeground(new Color(51, 51, 51));
        }
        if (txtTotalCredit.getText().equals("") && txtTotalCredit.getText() == null) {
          count++;
        } else {
          lblMessage.setText("");
          lblPartner.setForeground(new Color(51, 51, 51));
        }

        if (count > 0) {
          lblMessage.setText("Vui lòng kiểm tra dòng báo đỏ");
          lblCreditUsed.setForeground(Color.RED);
          return false;
        } else
          return true;
      } catch (Exception ex) {
        lblMessage.setText("Vui lòng kiểm tra dòng báo đỏ");
        lblCreditUsed.setForeground(Color.RED);
        return false;
      }
    } else {
      lblMessage.setText("Chưa có đối tác nào?");
      lblPartner.setForeground(Color.RED);
      return false;
    }
  }

  // Phương thức cho nút [Lưu]
  @Override
  public void save() throws Exception {
    if (UIConfigModelManager.getInstance().getPermission(permisson) == Capability.READ) {
      JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
    } else {
      if (checkData()) {
        getData(credit, creditTransaction);
        CustomerModelManager.getInstance().saveCredit(credit);
        CustomerModelManager.getInstance().saveCreditTransaction(creditTransaction);
        saveInvoiceCredit();
        DialogNotice.getInstace().setVisible(true);
        reset();
      }
    }
  }

  private void saveInvoiceCredit() throws Exception {
    DateFormat df = new SimpleDateFormat("yyyyMMddHHssmm");
    Customer customer = txtCustomer.getItem();
    Employee employee = HRModelManager.getInstance().getBydLoginId(ManagerAuthenticate.getInstance().getLoginId());
    if (employee == null) {
      employee = new Employee();
      employee.setLoginId("");
    }
    // Mỗi khách hàng chỉ có 1 invoice chi trả trước, kiểm tra xem khách hàng đã
    // có invoice chưa
    // nếu có rồi thì dùng luôn invoice đó + thêm tiền vào
    InvoiceDetail invoiceDetail = null;
    InvoiceItem invoiceItem = null;
    if (creditTransaction.getAmount() < 0) {
      invoiceDetail = AccountingModelManager.getInstance().getInvoiceDetailByCredit(customer.getCode());
      invoiceDetail = AccountingModelManager.getInstance().getInvoiceDetail(invoiceDetail.getId());
    }
    if (invoiceDetail == null) {
      invoiceDetail = new InvoiceDetail(true);
      invoiceItem = new InvoiceItem();
      invoiceDetail.setStartDate(new Date());
      invoiceDetail.setEndDate(new Date());
      invoiceDetail.setInvoiceCode("TC" + df.format(new Date()));

      invoiceDetail.setDepartmentCode(HRModelManager.getInstance().getDepartmentOther().getPath());

      invoiceDetail.setGroupCustomerCode(CustomerModelManager.getInstance().getGroupCustomerOther().getPath());
      invoiceDetail.setInvoiceName("KH " + customer.toString() + " ứng trước");
      invoiceItem.setItemName("KH " + customer.toString() + " ứng trước");
    } else {
      invoiceItem = invoiceDetail.getItems().get(0);
    }

    invoiceDetail.setType(AccountingModelManager.typeTraTruoc);
    if (creditTransaction.getAmount() > 0) {
      invoiceItem.setActivityType(com.hkt.module.accounting.entity.InvoiceItem.ActivityType.Receipt);
      invoiceDetail.setActivityType(ActivityType.Receipt);
    } else {
      invoiceItem.setActivityType(com.hkt.module.accounting.entity.InvoiceItem.ActivityType.Payment);
      invoiceDetail.setActivityType(ActivityType.Payment);
    }
    invoiceDetail.setCurrencyRate(1);
    invoiceDetail.setCurrencyUnit("VND");
    invoiceDetail.setDiscount(0);
    invoiceDetail.setDiscountRate(0);

    invoiceDetail.setTotalTax(0);
    invoiceDetail.setCustomerCode(customer.getCode());
    invoiceItem.setCurrencyRate(invoiceDetail.getCurrencyRate());
    invoiceItem.setCurrencyUnit(invoiceDetail.getCurrencyUnit());
    invoiceItem.setDiscount(0);
    invoiceItem.setDiscountByInvoice(invoiceDetail.getDiscount());
    invoiceItem.setDiscountRate(0);
    invoiceItem.setDiscountRateByInvoice(invoiceDetail.getDiscountRate());
    invoiceItem.setItemCode(df.format(new Date()));

    invoiceItem.setQuantity(1);
    invoiceItem.setTax(invoiceDetail.getTotalTax());
    invoiceItem.setStartDate(invoiceDetail.getStartDate());

    // Cộng thêm tiền nếu invoice của khách hàng là chi và trả trước
    if (creditTransaction.getAmount() < 0) {
      invoiceItem.setUnitPrice(invoiceItem.getUnitPrice() + (creditTransaction.getAmount() * (-1)));
      invoiceItem.setTotal(invoiceItem.getTotal() + (creditTransaction.getAmount() * (-1)));
      invoiceItem.setFinalCharge(invoiceItem.getFinalCharge() + (creditTransaction.getAmount() * (-1)));
      invoiceDetail.setTotal((creditTransaction.getAmount() * (-1)) + invoiceDetail.getTotal());
      invoiceDetail.setFinalCharge((creditTransaction.getAmount() * (-1)) + invoiceDetail.getFinalCharge());
    } else {
      invoiceDetail.setTotal((creditTransaction.getAmount()));
      invoiceDetail.setFinalCharge((creditTransaction.getAmount()));
      invoiceItem.setUnitPrice((creditTransaction.getAmount()));
      invoiceItem.setTotal((creditTransaction.getAmount()));
      invoiceItem.setFinalCharge((creditTransaction.getAmount()));
      invoiceDetail.setTotalPaid(creditTransaction.getAmount());
    }
    if (invoiceDetail.getId() == null) {
      invoiceDetail.add(invoiceItem);
    }
    invoiceDetail.setStatus(Status.Paid);

    InvoiceTransaction invoiceTransaction = new InvoiceTransaction();
    invoiceTransaction.setCreatedBy(employee.toString());
    invoiceTransaction.setCurrencyRate(invoiceDetail.getCurrencyRate());
    invoiceTransaction.setCurrencyUnit(invoiceDetail.getCurrencyUnit());
    invoiceTransaction.setType(AccountingModelManager.typeTraTruoc);
    if (creditTransaction.getAmount() > 0) {
      invoiceTransaction.setTotal(creditTransaction.getAmount());
    } else {
      invoiceTransaction.setTotal(creditTransaction.getAmount() * (-1));
    }

    invoiceTransaction.setTransactionDate(new Date());
    invoiceTransaction.setTransactionType(TransactionType.Cash);
    invoiceDetail.add(invoiceTransaction);
    invoiceDetail = invoiceDetail.calculate(new DefaultInvoiceCalculator());
    invoiceDetail = AccountingModelManager.getInstance().saveInvoiceDetail(invoiceDetail);

    // Nếu invoice của khách hàng là thu và trả trước thì tạo thêm 1 invoice chi
    // trả trước
    // invoice này là duy nhất của 1 khách hàng, nếu có rồi thì không cần tạo
    // nữa
    if (creditTransaction.getAmount() > 0) {

      InvoiceDetail invoice = AccountingModelManager.getInstance().getInvoiceDetailByCredit(customer.getCode());
      if (invoice == null) {
        invoice = new InvoiceDetail(true);
        invoice.setType(AccountingModelManager.typeTraTruoc);
        invoice.setActivityType(ActivityType.Payment);
        invoice.setCurrencyRate(1);
        invoice.setCurrencyUnit("VND");
        invoice.setDiscount(0);
        invoice.setDiscountRate(0);
        invoice.setTotal(0);
        invoice.setInvoiceName("KH " + customer.toString() + " sử dụng tiền ứng trước");
        invoice.setTotalTax(0);
        invoice.setStartDate(new Date());
        invoice.setEndDate(new Date());
        invoice.setInvoiceCode("TCTT" + df.format(new Date()));
        invoice.setFinalCharge(0);
        invoice.setStatus(Status.Paid);
        if (employee != null) {
          invoice.setEmployeeCode(employee.getCode());
        }
        invoice.setCustomerCode(customer.getCode());

        invoice.setDepartmentCode(HRModelManager.getInstance().getDepartmentOther().getPath());
        
        List<AccountMembership> accMemberShips = AccountModelManager.getInstance().findMembershipByAccountLoginId(
            customer.getLoginId());
        if (accMemberShips != null && accMemberShips.size() > 0) {
          invoice.setGroupCustomerCode(accMemberShips.get(0).getGroupPath());
        } else {
          invoice.setGroupCustomerCode(CustomerModelManager.getInstance().getGroupCustomerOther()
              .getPath());
        }
        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setCurrencyRate(invoiceDetail.getCurrencyRate());
        invoiceItem1.setCurrencyUnit(invoiceDetail.getCurrencyUnit());
        invoiceItem1.setDiscount(0);
        invoiceItem1.setDiscountByInvoice(0);
        invoiceItem1.setDiscountRate(0);
        invoiceItem1.setDiscountRateByInvoice(0);
        invoiceItem1.setFinalCharge(0);
        invoiceItem1.setItemCode(df.format(new Date()));
        invoiceItem1.setItemName("KH " + customer.toString() + " sử dụng tiền ứng trước");
        invoiceItem1.setQuantity(1);
        invoiceItem1.setTax(0);
        invoiceItem1.setTotal(0);
        invoiceItem1.setActivityType(InvoiceItem.ActivityType.Payment);
        invoiceItem1.setUnitPrice(0);
        
        invoiceItem1.setFinalCharge(0);
        invoice.add(invoiceItem1);
        AccountingModelManager.getInstance().saveInvoiceDetail(invoice);
      }
    }
  }

  // Phương thức cho nút [Sửa]
  @Override
  public void edit() throws Exception {
    if (UIConfigModelManager.getInstance().getPermission(permisson) == Capability.READ) {
      JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
    } else {
      txtCreditLeft.setText(MyDouble.valueOf(credit.getCredit()).toString());
      double numPointUsed = creditTransaction.getAmount();
      if (numPointUsed < 0) {
        txtTotalCredit.setText(MyDouble.valueOf(credit.getCredit() - creditTransaction.getAmount()).toString());
      }
      if (numPointUsed > 0) {
        txtTotalCredit.setText(MyDouble.valueOf(credit.getCredit() - creditTransaction.getAmount()).toString());
      }
      setEnabledCompoment(true);
    }
  }

  // Phương thức cho nút [Xóa]
  @Override
  public void delete() throws Exception {
    if (UIConfigModelManager.getInstance().getPermission(permisson) == Capability.ADMIN) {
      double numCreditUsed = creditTransaction.getAmount();
      if (numCreditUsed < 0) {
        credit.setCredit(credit.getCredit() - creditTransaction.getAmount());
      }
      if (numCreditUsed > 0) {
        credit.setCredit(credit.getCredit() + creditTransaction.getAmount());
      }

      String str = "Xóa  ";
      PanelChoise pnPanel = new PanelChoise(str + creditTransaction + "?");
      pnPanel.setName("Xoa");
      DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
      dialog1.setName("dlXoa");
      dialog1.setTitle("Xóa sử dụng trả trước");
      dialog1.setLocationRelativeTo(null);
      dialog1.setModal(true);
      dialog1.setVisible(true);

      if (pnPanel.isDelete()) {
        CustomerModelManager.getInstance().deleteCreditTransaction(creditTransaction);
        reset();
      } else if (pnPanel.isDelete() == false) {
        reset();
      } else {
        lblMessage.setText("Có lỗi không xóa được!");
      }
    } else {
      JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
    }
  }

  // Phương thức cho nút [Viết lại]
  @Override
  public void reset() throws Exception {
  	txtTotalCredit.setText("0");
    txtCreditUsed.setText("");
    txtCreditLeft.setText("");
    txtDescription.setText("");
    cbType.setSelectedIndex(0);
    lblMessage.setText("");
    lblCreditUsed.setForeground(new Color(51, 51, 51));
    lblPartner.setForeground(new Color(51, 51, 51));
    try {
      txtCustomer.setItem(null);
    } catch (Exception ex) {
    }

    creditTransaction = new CreditTransaction();
  }

  // Phương thức cho nút [Xem lại]
  @Override
  public void refresh() throws Exception {
    setData(credit, creditTransaction);
    txtCreditLeft.setText("");
  }

  // Tạo dữ liệu mẫu liên quan
  @Override
  public void createBeginTest() {
    if (isTest) {
      // Tạo đối tác
      Account account = new Account();
      account.setLoginId("HktTest1");
      account.setPassword("HktTest1");
      account.setEmail("HktTest1@gmail.com");
      try {
        AccountModelManager.getInstance().saveAccount(account);
      } catch (Exception e1) {
        e1.printStackTrace();
      }
      Customer customer = new Customer();
      customer.setLoginId("HktTest1");
      customer.setOrganizationLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
      Long idCustomer;
      try {
        Customer cus = CustomerModelManager.getInstance().saveCustomer(customer);
        idCustomer = cus.getId();
      } catch (Exception e1) {
        e1.printStackTrace();
        idCustomer = null;
      }

      // Tạo điểm cho đối tác
      Credit c = new Credit();
      c.setLoginId("HktTest1");
      c.setCustomerId(idCustomer);
      c.setCredit(0);
      try {
        c = CustomerModelManager.getInstance().saveCredit(c);
        idCreditTest = c.getId();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  // Tạo dữ liệu tự động test chuyển trang
  @Override
  public void createDataTest() {
    for (int i = 4; i <= 50; i++) {
      CreditTransaction ctTest = new CreditTransaction();
      ctTest.setLoginId("HktTest1");
      ctTest.setCreditId(idCreditTest);
      ctTest.setAmount(1000 * i);
      ctTest.setDateExecute(new Date());
      try {
        CustomerModelManager.getInstance().saveCreditTransaction(ctTest);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  // Xóa toàn bộ dữ liệu tự động sinh
  @Override
  public void deleteDataTest() {
    if (!PanelTestAll.runAll) {
      DialogTest.getInstance().show("Xóa toàn bộ dữ liệu test ?");
      if (DialogTest.getInstance().isTest()) {
        try {
          AccountModelManager.getInstance().deleteTest("HktTest");
          CustomerModelManager.getInstance().deleteTest("HktTest");
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

	@Override
  public void update(Object o, Object arg) {
		if(o instanceof Customer){
			Customer c = (Customer)o;
			try {
	      credit = CustomerModelManager.getInstance().getCreditByCustomerId(
	      		c.getId());
	      if (credit == null) {
	        credit = new Credit();
	        credit.setLoginId(c.getLoginId());
	        credit.setCustomerId(c.getId());
	        credit.setCredit(0);
	        credit = CustomerModelManager.getInstance().saveCredit(credit);
	      }
	    } catch (Exception e2) {
	      e2.printStackTrace();
	    }

	    txtTotalCredit.setText(MyDouble.valueOf(credit.getCredit()).toString());
	    if (!txtCreditUsed.getText().equals("") && txtCreditUsed.getText() != null) {
	      calculateCredit();
	    }
		}
		
	  
  }
}
