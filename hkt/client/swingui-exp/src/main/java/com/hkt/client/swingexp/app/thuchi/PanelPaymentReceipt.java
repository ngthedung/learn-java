package com.hkt.client.swingexp.app.thuchi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.GenericOptionServiceClient;
import com.hkt.client.swingexp.app.banhang.PanelTax;
import com.hkt.client.swingexp.app.banhang.list.PanelUpfrontPayment;
import com.hkt.client.swingexp.app.banhang.screen.often.DialogPayOnce;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelPaymentProduct;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelTextMoneyPayment;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.component.TextPopup;
import com.hkt.client.swingexp.app.core.DepartmentJComboBox;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.HKTFile;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.IMyObserver;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.core.ProjectJComboBox;
import com.hkt.client.swingexp.app.core.StoreJComboBox;
import com.hkt.client.swingexp.app.core.TypeInvoiceJComboBox;
import com.hkt.client.swingexp.app.core.UnitMoneyJComboBox;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.app.hethong.PanelSystemConfig;
import com.hkt.client.swingexp.app.khuvucbanhang.PanelManageCodes;
import com.hkt.client.swingexp.app.print.ReportPhieuChi;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.ManagerConvert;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.SupplierModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.accounting.DefaultInvoiceCalculator;
import com.hkt.module.accounting.entity.Invoice;
import com.hkt.module.accounting.entity.Invoice.ActivityType;
import com.hkt.module.accounting.entity.Invoice.Status;
import com.hkt.module.accounting.entity.Contributor;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.accounting.entity.InvoiceTransaction;
import com.hkt.module.accounting.entity.InvoiceTransaction.TransactionType;
import com.hkt.module.accounting.entity.ManageCodes;
import com.hkt.module.config.generic.Option;
import com.hkt.module.config.locale.Currency;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.partner.customer.entity.Credit;
import com.hkt.module.partner.customer.entity.CreditTransaction;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.supplier.entity.Supplier;
import com.hkt.module.product.entity.Tax;
import com.hkt.module.restaurant.entity.Project;
import com.hkt.util.text.DateUtil;

/** Phiếu thu tiền, phiếu chi tiền */
public class PanelPaymentReceipt extends MyPanel implements IMyObserver, IDialogMain {
	private TextPopup<Employee> txtNhanVien, txtNVPhucVu;
	private TextPopup txtKhachHang;
	private TextPopup<Option> txtTypeInvoice;
	private JLabel lblMoneySpell, lbMessenger, lbPTPV, lbNghiepVu, lbTongTien, lbF12, lbStartDate, lbPhongBan,
	    lbNhanVien, lbKhachHang, lbThuePercent, lbTienThue, lbSauThue, lbDaTra, lbPhaiTra, lbMaNghiepVu, lbCuaHang,
	    lbDuAn;
	private TypeInvoiceJComboBox cbTypeInvoice;
	private JTextField txtNghiepVu, txtTongTien, txtPhaiTra, txtDaTra, txtSauThue, txtTienChietKhau, txtChietKhauPercent,
	    txtTienThue, txtMaNghiepVu, txtGhiChu, txtPTPhucVu;
	private DepartmentJComboBox cbDepartment;
	private StoreJComboBox cbStore;
	private TextPopup<Project> txtProject;
	private MyJDateChooser dcStartDate;
	private JComboBox<Double> cbThuePercent;
	private UnitMoneyJComboBox cbUnitMoney;
	private JCheckBox chbTraSau;
	private JRadioButton rbtChietKhauPercent, rbtTienChietKhau;
	private ActivityType activityType;
	private DateFormat df = new SimpleDateFormat("yyyyMMddHHssmm");
	private InvoiceDetail invoiceDetail;
	private ClientContext clientContext = ClientContext.getInstance();
	private GenericOptionServiceClient client = clientContext.getBean(GenericOptionServiceClient.class);
	private List<Option> options;
	private String[] comboBoxVariable;
	public static int F12;
	public static String permission1;
	public static String permission2;
	private boolean exit = false;
	private JComboBox<String> cboPartner;

	public PanelPaymentReceipt(ActivityType activityType) {

		this.activityType = activityType;
		this.setBackground(new Color(255, 255, 255));
		this.setOpaque(false);
		this.setName("pnPaymentReceipt");
		addKeyBindings(this);

		lbF12 = new JLabel("Loại (F12)");
		lbNghiepVu = new JLabel("Nghiệp vụ");
		lbTongTien = new JLabel("Tổng tiền");
		lbPhongBan = new JLabel("Phòng ban");
		lbKhachHang = new JLabel("Khách hàng");
		lbThuePercent = new JLabel("Thuế %");
		lbSauThue = new JLabel("Sau thuế");
		lbDaTra = new JLabel("Đã trả");
		lbPhaiTra = new JLabel("Phải trả");
		lbTienThue = new JLabel("Tiền thuế");
		lbPTPV = new JLabel("% Doanh thu");
		lbMessenger = new JLabel("");
		lbMaNghiepVu = new JLabel("Mã nghiệp vụ");
		lbCuaHang = new JLabel("Cửa hàng");
		lbDuAn = new JLabel("Dự án");
		lbNhanVien = new JLabel("Người TH");
		cboPartner = new JComboBox<String>();
		
		
		if (activityType.equals(ActivityType.Payment)) {
			lbStartDate = new JLabel("Ngày chi");

		} else {
			if (activityType.equals(ActivityType.Receipt)) {
				lbStartDate = new JLabel("Ngày thu");
			}
		}
		txtMaNghiepVu = new JTextField();
		txtGhiChu = new JTextField();
		txtNVPhucVu = new TextPopup<Employee>(Employee.class);
		txtPTPhucVu = new JTextField("0");
		txtPTPhucVu.setHorizontalAlignment(JTextField.RIGHT);

		txtMaNghiepVu.setName("txtManghiepVu");
		txtNghiepVu = new JTextField();
		txtNghiepVu.setText(DateUtil.asCompactDateTimeId(new Date()));
		txtNghiepVu.setName("txtNghiepVu");
		txtTypeInvoice = new TextPopup<Option>(Option.class);
		txtTypeInvoice.addObserver(this);
		txtTongTien = new JTextField();
		txtTongTien.setText("0");
		txtTongTien.setName("txtTongTien");
		txtNhanVien = new TextPopup<Employee>(Employee.class);
		txtNhanVien.addObserver(this);
		cboPartner.setOpaque(false);
		cboPartner.setBorder(null);
		Profile p = AccountModelManager.getInstance().getProfileConfigAdmin();
		if(p.get("IndexComboReceipt")!=null){
			if(p.get("IndexComboReceipt").toString().equals("1")){
				lbKhachHang.setText("Nhà cung cấp");
				txtKhachHang = new TextPopup<Supplier>(Supplier.class);
				try {
					txtKhachHang.setData(SupplierModelManager.getInstance().getAllSuppliers());
				} catch (Exception e) {
				}
			}else{
				txtKhachHang = new TextPopup<Customer>(Customer.class);
				try {
					txtKhachHang.setData(CustomerModelManager.getInstance().getCustomers(false));
				} catch (Exception e) {
				}
			}
		}else{
			if (activityType.equals(Invoice.ActivityType.Payment)) {
				lbKhachHang.setText("Nhà cung cấp");
				txtKhachHang = new TextPopup<Supplier>(Supplier.class);
				try {
					txtKhachHang.setData(SupplierModelManager.getInstance().getAllSuppliers());
				} catch (Exception e) {
				}
			} else {
				txtKhachHang = new TextPopup<Customer>(Customer.class);
				try {
					txtKhachHang.setData(CustomerModelManager.getInstance().getCustomers(false));
				} catch (Exception e) {
				}
			}
		}
		if (lbKhachHang.getText().equals("Khách hàng")) {
			String [] model = {"Khách hàng","Nhà cung cấp",};
			cboPartner.setModel(new DefaultComboBoxModel<String>(model));
		}else{
			String [] model = {"Nhà cung cấp","Khách hàng"};
			cboPartner.setModel(new DefaultComboBoxModel<String>(model));
		}
		txtSauThue = new JTextField();
		txtSauThue.setText("0");
		txtDaTra = new JTextField();
		txtDaTra.setText("0");
		txtPhaiTra = new JTextField();
		txtTienChietKhau = new JTextField();
		txtTienChietKhau.setText("0");
		txtTienThue = new JTextField();
		txtTienThue.setText("0");
		txtChietKhauPercent = new JTextField();
		txtChietKhauPercent.setText("0");

		dcStartDate = new MyJDateChooser();
		dcStartDate.setDate(new Date());
		cbStore = new StoreJComboBox();
		cbStore.setFont(new java.awt.Font("Tahoma", 0, 12));

		txtProject = new TextPopup<Project>(Project.class);
		txtProject.setFont(new java.awt.Font("Tahoma", 0, 12));

		cbTypeInvoice = new TypeInvoiceJComboBox();
		cbTypeInvoice.setName("cboTypeInvoice");
		cbTypeInvoice.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (cbTypeInvoice.getSelectedType().getLabel().toLowerCase().indexOf("lương") >= 0
				    || cbTypeInvoice.getSelectedType().getLabel().toLowerCase().indexOf("thưởng") >= 0) {
					txtKhachHang.setEnabled(false);
				} else {
					txtKhachHang.setEnabled(true);
				}

			}
		});
		cbDepartment = new DepartmentJComboBox();
		cbThuePercent = new JComboBox<Double>();
		cbUnitMoney = new UnitMoneyJComboBox();

		chbTraSau = new JCheckBox("Trả sau");
		chbTraSau.setOpaque(false);

		rbtChietKhauPercent = new JRadioButton("% CK");
		rbtChietKhauPercent.setOpaque(false);
		rbtTienChietKhau = new JRadioButton("Tiền CK");
		rbtTienChietKhau.setOpaque(false);

		lblMoneySpell = new JLabel("Tiền bằng chữ");
		lblMoneySpell.setForeground(new Color(124, 0, 0));
		lblMoneySpell.setHorizontalAlignment(SwingConstants.CENTER);

		txtTongTien.setHorizontalAlignment(JTextField.RIGHT);
		txtTienThue.setHorizontalAlignment(JTextField.RIGHT);
		txtChietKhauPercent.setHorizontalAlignment(JTextField.RIGHT);
		txtDaTra.setHorizontalAlignment(JTextField.RIGHT);
		txtPhaiTra.setHorizontalAlignment(JTextField.RIGHT);
		txtSauThue.setHorizontalAlignment(JTextField.RIGHT);
		txtTienChietKhau.setHorizontalAlignment(JTextField.RIGHT);

		comboBoxVariable = new String[3];
		comboBoxVariable[0] = ""; // ComboBox Department
		comboBoxVariable[1] = ""; // ComboBox Store
		comboBoxVariable[2] = RestaurantModelManager.getInstance().getProjectOther().getCode(); // ComboBox
		                                                                                        // Project
		try {
			FileReader fr = new FileReader(HKTFile.getFile("Database", "configPayment.json"));
			BufferedReader br = new BufferedReader(fr);
			String s;
			String jsonString = "";
			while ((s = br.readLine()) != null) {
				jsonString = jsonString + s;
			}
			br.close();
			fr.close();

			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(jsonString);
			comboBoxVariable[0] = (String) obj.get("departmentCode");
			comboBoxVariable[1] = (String) obj.get("storeCode");
			comboBoxVariable[2] = (String) obj.get("projectCode");
		} catch (Exception ex) {
		}

		if (!comboBoxVariable[0].equals("")) {
			cbDepartment.setSelectDepartmentByCode(comboBoxVariable[0]);
		} else {
			cbDepartment.setSelectDepartmentByIndex(0);
		}

		if (!comboBoxVariable[1].equals("")) {
			cbStore.setSelectStoreByCode(comboBoxVariable[1]);
		} else {
			cbStore.setSelectedIndex(0);
		}

		if (!comboBoxVariable[2].equals(RestaurantModelManager.getInstance().getProjectOther().getCode())
		    && !comboBoxVariable[2].equals("")) {
			Project project = RestaurantModelManager.getInstance().getProjectByCode(comboBoxVariable[2]);
			txtProject.setItem(project);
		} else {
			txtProject.setItem(null);
		}

		options = new ArrayList<Option>();
		try {
			options = client.getOptionGroup("accounting", "AccountingService", "type_invoice").getOptions();
			txtTypeInvoice.setData(options);
			for (int i = 0; i < options.size();) {
				if (options.get(i).getCode().equals(AccountingModelManager.typeBanHang)
				    || options.get(i).getCode().equals(AccountingModelManager.typeDatHang)
				    || options.get(i).getCode().equals(AccountingModelManager.typeTraHang)) {
					options.remove(i);
				} else {
					i++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		loadCode();
		try {
			List<Employee> emps = HRModelManager.getInstance().getEmployees();
			txtNhanVien.setData(emps);
			txtNVPhucVu.setData(emps);
			Employee e = HRModelManager.getInstance().getBydLoginId(ManagerAuthenticate.getInstance().getLoginId());
			if (e != null) {
				txtNhanVien.setItem(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			List<Tax> taxs = ProductModelManager.getInstance().getTaxs();
			List<Double> taxsPercent = new ArrayList<Double>();
			taxsPercent.add(0, null);
			for (Tax t : taxs) {
				taxsPercent.add(t.getPercent());
			}
			cbThuePercent.setModel(new DefaultComboBoxModel(taxsPercent.toArray()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		txtChietKhauPercent.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				if (rbtChietKhauPercent.isSelected()) {
					loadMoneyDiscout();
				}
			}
		});

		txtTienChietKhau.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				if (rbtTienChietKhau.isSelected()) {
					loadSumMoney();
				}
			}
		});

		rbtTienChietKhau.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (rbtTienChietKhau.isSelected()) {
					txtChietKhauPercent.setText("");
					txtChietKhauPercent.setEnabled(false);
					txtTienChietKhau.setEnabled(true);
					loadMoneyDiscout();
				}
			}
		});

		rbtChietKhauPercent.setSelected(true);
		ButtonGroup btn = new ButtonGroup();
		btn.add(rbtChietKhauPercent);
		btn.add(rbtTienChietKhau);

		rbtChietKhauPercent.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (rbtChietKhauPercent.isSelected()) {
					txtChietKhauPercent.setEnabled(true);
					txtTienChietKhau.setEnabled(false);
					loadMoneyDiscout();
				}
			}
		});

		cbThuePercent.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				loadMoneyDiscout();
			}
		});

		cbThuePercent.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					PanelTax panelTax;
					try {
						panelTax = new PanelTax();
						panelTax.setName("ThueHangHoa");
						DialogMain dialog = new DialogMain(panelTax);
						dialog.setIcon("tax1.png");
						dialog.setTitle("Thuế hàng hóa");
						dialog.setName("dlThueHangHoa");
						dialog.setVisible(true);
						try {
							List<Tax> taxs = ProductModelManager.getInstance().getTaxs();
							List<Double> taxsPercent = new ArrayList<Double>();
							taxsPercent.add(0, null);
							for (Tax t : taxs) {
								taxsPercent.add(t.getPercent());
							}
							cbThuePercent.setModel(new DefaultComboBoxModel(taxsPercent.toArray()));
						} catch (Exception e1) {
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		txtTongTien.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				loadMoneyDiscout();
			}
		});

		txtDaTra.setEnabled(false);
		txtPhaiTra.setEnabled(false);
		txtSauThue.setEnabled(false);
		txtTienThue.setEnabled(false);
		txtTienChietKhau.setEnabled(false);

		initPanel();
		txtTypeInvoice.setVisible(false);
		cbTypeInvoice.setVisible(true);

		if (F12 == 0) {
			txtTypeInvoice.setVisible(false);
			cbTypeInvoice.setVisible(true);
		} else {
			txtTypeInvoice.setVisible(true);
			cbTypeInvoice.setVisible(false);
		}
		try {
			txtProject.setData(RestaurantModelManager.getInstance().getAllProject());
		} catch (Exception e) {
			e.printStackTrace();
		}

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				try {
					if (exit) {
						((DialogMain) getRootPane().getParent()).getBtnReset().setEnabled(false);
					}
				} catch (Exception e2) {
				}

			}
		});
		dcStartDate.getDateEditor().addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				loadCode();

			}
		});
	}

	private void loadCode() {
		if (invoiceDetail == null || invoiceDetail.getId() == null) {
			try {
				if (dcStartDate.getDate() != null) {
					String code = AccountingModelManager.getInstance().getCodeObject(PanelManageCodes.InvoiceTC,
					    cbTypeInvoice.getSelectedType().getCode(), dcStartDate.getDate(), false);
					code = code.substring(code.indexOf(":") + 1);

					txtMaNghiepVu.setText(code);
					txtMaNghiepVu.setEnabled(false);
				} else {
					String code = AccountingModelManager.getInstance().getCodeObject(PanelManageCodes.InvoiceTC,
					    cbTypeInvoice.getSelectedType().getCode(), new Date(), false);
					code = code.substring(code.indexOf(":") + 1);

					txtMaNghiepVu.setText(code);
					txtMaNghiepVu.setEnabled(false);
				}
			} catch (Exception e) {
				txtMaNghiepVu.setText(DateUtil.asCompactDateTimeId(new Date()));
			}

		}
	}

	protected class JPanelLayout extends JPanel {

		public JPanelLayout(JComponent comp1, String pos1, JComponent comp2, String pos2) {
			this.setLayout(new BorderLayout(0, 0));
			this.add(comp1, pos1);
			this.add(comp2, pos2);
			comp2.setPreferredSize(new Dimension(440, comp2.getHeight()));
		}
	}

	// Thanh toán lẻ
	private void paymentSlip() {
		/** thanh toán lẻ bằng tiền */
		try {
			if (invoiceDetail == null) {
				chbTraSau.setSelected(true);
				checkSave(false);
				
				chbTraSau.setSelected(false);
			}
			PanelUpfrontPayment panel = new PanelUpfrontPayment();
			panel.initEvent(invoiceDetail);
			panel.setName("ScreenOften_PaymentbyMoney");
			DialogResto dialog = new DialogResto(panel, false, 300, 290);
			dialog.setName("dlScreenOften_PaymenbyMoney");
			dialog.setTitle("Thanh toán lẻ");
			dialog.setComponent1(panel.getLabel());
			dialog.setComponent2(panel.getJDateChooser());
			dialog.setModal(true);
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
			if (panel.isFlagPayment()) {
				AccountingModelManager.getInstance().saveInvoice(invoiceDetail);
				PanelTextMoneyPayment panelP = new PanelTextMoneyPayment();
				panelP.setName("panelPayment");
				panelP.initEvent(panel.getTxtBeforPayment().getText(), cbUnitMoney.getSelectedCurrency().getCode());
				DialogResto dialogP = new DialogResto(panelP, false, 400, 350);
				dialogP.setName("dlPayment");
				dialogP.setTitle("Thanh toán");
				dialogP.setLocationRelativeTo(null);
				dialogP.setModal(true);
				dialogP.setVisible(true);
				if (!panelP.isFlagPayment()) {
					return;
				}
				setInvoice(invoiceDetail);
				canceEdit(true);
				txtDaTra.setEnabled(false);
				txtPhaiTra.setEnabled(false);
				txtSauThue.setEnabled(false);
				txtMaNghiepVu.setEnabled(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initPanel() {
		final JPanelLayout jPanel1Border = new JPanelLayout(cbTypeInvoice, BorderLayout.CENTER, txtTypeInvoice,
		    BorderLayout.LINE_END);

		JPanel panel = new JPanel(new GridLayout(1, 2, 3, 3));
		panel.setOpaque(false);
		JButton btn = new JButton("LS Thanh Toán");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (invoiceDetail != null) {
					PanelTablePayment panelTablePayment = new PanelTablePayment(invoiceDetail);
					DialogResto dialog = new DialogResto(panelTablePayment, false, 0, 210);
					dialog.setIcon("cauhinh1.png");
					dialog.setLocationRelativeTo(null);
					dialog.setTitle("Lịch sử thanh toán");
					dialog.setVisible(true);
				}
			}
		});
		JButton btn1 = new JButton("Thanh toán lẻ");
		btn1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				paymentSlip();
			}
		});
		panel.add(btn1);
		panel.add(btn);
		panel.setPreferredSize(new Dimension(100, 23));
		panel.setMinimumSize(new Dimension(100, 23));
		panel.setMaximumSize(new Dimension(100, 23));

		JPanel panel1 = new JPanel(new BorderLayout(3, 3));
		panel1.setOpaque(false);
		panel1.add(txtPhaiTra, BorderLayout.CENTER);

		panel1.add(cbUnitMoney, BorderLayout.EAST);
		panel1.setPreferredSize(new Dimension(100, 23));
		panel1.setMinimumSize(new Dimension(100, 23));
		panel1.setMaximumSize(new Dimension(100, 23));
		MyGroupLayout layout = new MyGroupLayout(this);
		final JScrollPane b = new JScrollPane();
		b.setViewportView(lbKhachHang);
		b.setBorder(null);
		b.setOpaque(false);
		b.getViewport().setOpaque(false);
		
		final JScrollPane a = new JScrollPane();
		a.setViewportView(txtKhachHang);
		a.setBorder(null);
		a.setOpaque(false);
		a.getViewport().setOpaque(false);
		layout.add(0, 0, lbMaNghiepVu);
		layout.add(0, 1, txtMaNghiepVu);
		layout.add(0, 2, lbF12);
		layout.add(0, 3, jPanel1Border);
		layout.add(1, 0, lbNghiepVu);
		layout.add(1, 1, txtNghiepVu);
		layout.add(2, 0, lbTongTien);
		layout.add(2, 1, txtTongTien);
		layout.add(3, 0, new JLabel("Ghi chú"));
		layout.add(3, 1, txtGhiChu);
		layout.add(4, 0, lbStartDate);
		layout.add(4, 1, dcStartDate);
		layout.add(4, 2, lbPhongBan);
		layout.add(4, 3, cbDepartment);
		layout.add(5, 0, lbCuaHang);
		layout.add(5, 1, cbStore);
		layout.add(5, 2, lbDuAn);
		layout.add(5, 3, txtProject);
		layout.add(6, 0, lbNhanVien);
		layout.add(6, 1, txtNhanVien);
		layout.add(6, 2, b);
		layout.add(6, 3, a);
		layout.add(7, 0, new JLabel("Nhân viên"));
		layout.add(7, 1, txtNVPhucVu);
		layout.add(7, 2, lbPTPV);
		layout.add(7, 3, txtPTPhucVu);
		layout.add(8, 0, lbThuePercent);
		layout.add(8, 1, cbThuePercent);
		layout.add(8, 2, rbtChietKhauPercent);
		layout.add(8, 3, txtChietKhauPercent);
		layout.add(9, 0, lbTienThue);
		layout.add(9, 1, txtTienThue);
		layout.add(9, 2, rbtTienChietKhau);
		layout.add(9, 3, txtTienChietKhau);
		layout.add(10, 0, lbSauThue);
		layout.add(10, 1, txtSauThue);
		layout.add(10, 2, lbDaTra);
		layout.add(10, 3, txtDaTra);
		layout.add(11, 0, lbPhaiTra);
		layout.add(11, 1, panel1);
		layout.add(11, 2, chbTraSau);
		layout.add(11, 3, panel);
		layout.add(12, 0, lblMoneySpell);
		layout.addMessenger(lbMessenger);
		layout.updateGui();
		lbKhachHang.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()>=2){
					cboPartner.setSize(10, 21);
					cboPartner.setPreferredSize(new Dimension(10, 21));
					b.setViewportView(cboPartner);
				}
				
			}
			
		});
     cboPartner.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(cboPartner.getSelectedItem().toString().equals("Khách hàng")){
					lbKhachHang.setText("Khách hàng");
					txtKhachHang = new TextPopup<Customer>(Customer.class);
					try {
						txtKhachHang.setData(CustomerModelManager.getInstance().getCustomers(false));
					} catch (Exception e1) {
					}
					a.setViewportView(txtKhachHang);
				}else{
					lbKhachHang.setText("Nhà cung cấp");
					txtKhachHang = new TextPopup<Supplier>(Supplier.class);
					try {
						txtKhachHang.setData(SupplierModelManager.getInstance().getAllSuppliers());
					} catch (Exception e1) {
					}
					a.setViewportView(txtKhachHang);
				}
				Profile p = AccountModelManager.getInstance().getProfileConfigAdmin();
				if(cboPartner.getSelectedItem().toString().equals("Khách hàng")){
					p.put("IndexComboReceipt", "0");
				}else{
					p.put("IndexComboReceipt", "1");
				}
				
				AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(), p);
				b.setViewportView(lbKhachHang);
			}
		});
	}

	private void canceEdit(boolean edit) {
		List<Component> list = getAllComponents(this);
		for (Component c : list) {
			c.setEnabled(edit);
		}

	}

	private List<Component> getAllComponents(final Container c) {
		Component[] comps = c.getComponents();
		List<Component> compList = new ArrayList<Component>();
		for (Component comp : comps) {
			compList.add(comp);
			if (comp instanceof Container) {
				compList.addAll(getAllComponents((Container) comp));
			}
		}
		txtTienThue.setEnabled(false);
		return compList;
	}

	public void checkSave(boolean reset) throws Exception {
		InvoiceItem invoiceItem;
		Customer customer = null;
		Credit credit = null;
		if (activityType.equals(Invoice.ActivityType.Receipt)) {
			customer = (Customer) txtKhachHang.getItem();
		}

		if (cbTypeInvoice.getSelectedType().getCode().equals(AccountingModelManager.typeTraTruoc) && customer != null
		    && activityType.equals(ActivityType.Payment)) {
			invoiceDetail = AccountingModelManager.getInstance().getInvoiceDetailByCredit(customer.getCode());
			invoiceDetail = AccountingModelManager.getInstance().getInvoiceDetail(invoiceDetail.getId());
		}
		if (invoiceDetail == null) {
			invoiceDetail = new InvoiceDetail(true);
			invoiceItem = new InvoiceItem();

			invoiceDetail.setStartDate(dcStartDate.getDate());
			invoiceDetail.setEndDate(dcStartDate.getDate());
			String code = AccountingModelManager.getInstance().getCodeObject(PanelManageCodes.InvoiceTC,
			    cbTypeInvoice.getSelectedType().getCode(), dcStartDate.getDate(), true);
			if (code != null) {
				invoiceDetail.setInvoiceCode("TC" + code);
			} else {
				invoiceDetail.setInvoiceCode("TC" + txtMaNghiepVu.getText());
			}

		} else {
			try {
				invoiceItem = invoiceDetail.getItems().get(0);
			} catch (Exception e) {
				invoiceItem = new InvoiceItem();
			}

		}
		if (activityType.equals(Invoice.ActivityType.Payment)) {
			try {
				Supplier c = (Supplier) txtKhachHang.getItem();
				if (c != null) {
					invoiceDetail.setSupplierCode(c.getCode());
				}
			} catch (Exception e) {
				Customer c = (Customer) txtKhachHang.getItem();
				if (c != null) {
					invoiceDetail.setCustomerCode(c.getCode());
				}
			}
			
		}
		List<Contributor> contributors = invoiceDetail.getContributors();
		if (contributors == null) {
			contributors = new ArrayList<Contributor>();
		}

		String groupDepartmentOther = HRModelManager.getInstance().getDepartmentOther().getPath();
		// Tạo contributor thành viên phục vụ
		Employee nhanVienPv = (Employee) txtNVPhucVu.getItem();
		if (nhanVienPv != null) {
			boolean flag = false;
			Contributor contributorNVPV = null;
			for (Contributor c : contributors) {
				if (c.getIdentifierId().equals(nhanVienPv.getLoginId()) && c.getRole().equals(Contributor.nhanVienPV)) {
					flag = true;
					contributorNVPV = c;
					break;
				}
			}
			if (!flag) {
				contributorNVPV = new Contributor();
				contributorNVPV.setType(Contributor.Type.User);
				contributorNVPV.setRole(Contributor.nhanVienPV);
				contributorNVPV.setIdentifierId(nhanVienPv.getLoginId());
				contributorNVPV.setPercent(Integer.parseInt(txtPTPhucVu.getText()));
				try {
					List<AccountMembership> accMemberShips = AccountModelManager.getInstance().findMembershipByAccountLoginId(
					    nhanVienPv.getLoginId());
					if (accMemberShips != null && accMemberShips.size() > 0) {
						contributorNVPV.setIdentifierValue(accMemberShips.get(0).getGroupPath());
					} else {
						contributorNVPV.setIdentifierValue(groupDepartmentOther);
					}
				} catch (Exception e) {
					e.printStackTrace();
					contributorNVPV.setIdentifierValue(groupDepartmentOther);
				}
				contributors.add(contributorNVPV);
			} else {
				contributorNVPV.setPercent(Integer.parseInt(txtPTPhucVu.getText()));
			}
		}

		invoiceDetail.setType(cbTypeInvoice.getSelectedType().getCode());
		invoiceDetail.setActivityType(activityType);
		invoiceDetail.setLocationCode(RestaurantModelManager.getInstance().getLocationOther().getCode());
		invoiceDetail.setTableCode(RestaurantModelManager.getInstance().getTableOther().getCode());
		Currency currency = cbUnitMoney.getSelectedCurrency();
		invoiceDetail.setCurrencyRate(currency.getRate());
		invoiceDetail.setCurrencyUnit(currency.getCode());
		invoiceDetail.setDiscount(MyDouble.parseDouble(txtTienChietKhau.getText()));
		invoiceDetail.setDiscountRate(MyDouble.parseDouble(txtChietKhauPercent.getText()));
		invoiceDetail.setInvoiceName(txtNghiepVu.getText());
		invoiceDetail.setTotalTax(MyDouble.parseDouble(txtTienThue.getText()));
		invoiceDetail.setDescription(txtGhiChu.getText());
		if (cbStore.getSelectedStore() != null) {
			invoiceDetail.setStoreCode(cbStore.getSelectedStore().getCode());
		} else {
			invoiceDetail.setStoreCode(null);
		}

		String pathProject = RestaurantModelManager.getInstance().getProjectOther().getCode();
		if (txtProject.getItem() != null) {
			pathProject = getPathProjectCode(txtProject.getItem());
		}
		invoiceDetail.setProjectCode(pathProject);

		// Contributor phòng ban nhân viên
		Employee employee = (Employee) txtNhanVien.getItem();
		if (employee != null) { // Trường hợp chọn nhân viên
			invoiceDetail.setEmployeeCode(employee.getCode());
		} else { // Trường hợp ko chọn nhân viên
			invoiceDetail.setEmployeeCode(null);
		}

		AccountGroup departmentCashier = cbDepartment.getSelectedDepartment();
		if (departmentCashier != null) {
			invoiceDetail.setDepartmentCode(departmentCashier.getPath());
		} else {
			invoiceDetail.setDepartmentCode(HRModelManager.getInstance().getDepartmentOther().getPath());
		}

		if (customer != null) { // Trường hợp khách hàng được chọn
			invoiceDetail.setCustomerCode(customer.getCode());

			// Tạo contributor nhóm khách hàng
			List<AccountMembership> accMemberShips = AccountModelManager.getInstance().findMembershipByAccountLoginId(
			    customer.getLoginId());
			if (accMemberShips != null && accMemberShips.size() > 0) {
				invoiceDetail.setGroupCustomerCode(accMemberShips.get(0).getGroupPath());
			} else {
				invoiceDetail.setGroupCustomerCode(CustomerModelManager.getInstance().getGroupCustomerOther().getPath());
			}

		} else { // Trường hợp ko chọn khách hàng
			invoiceDetail.setCustomerCode(null);
			invoiceDetail.setGroupCustomerCode(CustomerModelManager.getInstance().getGroupCustomerOther().getPath());
			invoiceDetail.setStartDate(dcStartDate.getDate());
		}

		invoiceItem.setCurrencyRate(invoiceDetail.getCurrencyRate());
		invoiceItem.setCurrencyUnit(invoiceDetail.getCurrencyUnit());
		invoiceItem.setDiscount(0);
		invoiceItem.setDiscountByInvoice(invoiceDetail.getDiscount());
		invoiceItem.setDiscountRate(0);
		invoiceItem.setDiscountRateByInvoice(invoiceDetail.getDiscountRate());
		invoiceItem.setItemCode(df.format(new Date()));
		invoiceItem.setItemName(txtNghiepVu.getText());
		invoiceItem.setItemCode(txtMaNghiepVu.getText());
		invoiceItem.setQuantity(1);
		invoiceItem.setTax(invoiceDetail.getTotalTax());
		if (activityType.equals(Invoice.ActivityType.Payment)) {
			invoiceItem.setActivityType(InvoiceItem.ActivityType.Payment);
		} else {
			invoiceItem.setActivityType(InvoiceItem.ActivityType.Receipt);
		}

		// Cộng thêm tiền nếu invoice của khách hàng là chi và trả trước
		if (cbTypeInvoice.getSelectedType().getCode().equals(AccountingModelManager.typeTraTruoc) && customer != null
		    && activityType.equals(ActivityType.Payment)) {
			invoiceItem.setUnitPrice(invoiceItem.getUnitPrice() + invoiceDetail.getTotal());
			invoiceItem.setTotal(invoiceItem.getTotal() + invoiceDetail.getTotal());
			invoiceItem.setFinalCharge(invoiceItem.getFinalCharge() + invoiceDetail.getTotal());
			invoiceDetail.setTotal(MyDouble.parseDouble(txtTongTien.getText()) + invoiceDetail.getTotal());
			invoiceDetail.setFinalCharge(MyDouble.parseDouble(txtSauThue.getText()) + invoiceDetail.getFinalCharge());
			// Tạo CreditTransaction và cập nhật số tiền khách hàng đưa trước
			credit = CustomerModelManager.getInstance().getCreditByCustomerId(customer.getId());
			credit.setCredit(credit.getCredit() - (MyDouble.parseDouble(txtSauThue.getText())));
			credit.setLoginId(customer.getLoginId());
			CreditTransaction transaction = new CreditTransaction();
			transaction.setAmount((MyDouble.parseDouble(txtSauThue.getText())) * (-1));
			transaction.setCreditId(credit.getId());
			transaction.setLoginId(credit.getLoginId());
			transaction.setDateExecute(new Date());
			transaction.setInvoiceId(invoiceDetail.getId());
			CustomerModelManager.getInstance().saveCredit(credit);
			CustomerModelManager.getInstance().saveCreditTransaction(transaction);
		} else {

			invoiceItem.setUnitPrice(MyDouble.parseDouble(txtTongTien.getText()));
			invoiceItem.setTotal(MyDouble.parseDouble(txtTongTien.getText()));
			invoiceItem.setFinalCharge(MyDouble.parseDouble(txtTongTien.getText()));
		}
		if (invoiceDetail.getId() == null) {
			invoiceDetail.add(invoiceItem);
		}

		if (!chbTraSau.isSelected()) {
			invoiceDetail.setStatus(Status.Paid);
			if (MyDouble.parseDouble(txtPhaiTra.getText()) != 0) {
				InvoiceTransaction invoiceTransaction = new InvoiceTransaction();
				invoiceTransaction.setCreatedBy(txtNhanVien.getText());
				invoiceTransaction.setCurrencyRate(invoiceDetail.getCurrencyRate());
				invoiceTransaction.setCurrencyUnit(invoiceDetail.getCurrencyUnit());
				invoiceTransaction.setTotal(MyDouble.parseDouble(txtPhaiTra.getText()));
				invoiceTransaction.setTransactionDate(dcStartDate.getDate());
				invoiceTransaction.setTransactionType(TransactionType.Cash);
				if (activityType.equals(ActivityType.Receipt)) {
					invoiceTransaction.setActivityType(InvoiceTransaction.ActivityType.Receipt);
				} else {
					invoiceTransaction.setActivityType(InvoiceTransaction.ActivityType.Payment);
				}
				invoiceDetail.add(invoiceTransaction);
			}

		} else {
			invoiceDetail.setStatus(Status.PostPaid);
		}

		invoiceDetail = invoiceDetail.calculate(new DefaultInvoiceCalculator());
		invoiceDetail.setTotal(MyDouble.parseDouble(txtTongTien.getText()));
		invoiceDetail.setFinalCharge(MyDouble.parseDouble(txtSauThue.getText()));
		if (!chbTraSau.isSelected()) {
			invoiceDetail.setTotalPaid(invoiceDetail.getFinalCharge());
		}
		invoiceDetail = AccountingModelManager.getInstance().saveInvoiceDetail(invoiceDetail);

		if (cbTypeInvoice.getSelectedType().getCode().equals(AccountingModelManager.typeTraTruoc)
		    && activityType.equals(ActivityType.Receipt)) {
			if (customer != null) {
				// Tạo CreditTransaction và cập nhật số tiền khách hàng đưa trước
				credit = CustomerModelManager.getInstance().getCreditByCustomerId(customer.getId());
				credit.setLoginId(customer.getLoginId());
				credit.setCredit(credit.getCredit() + (MyDouble.parseDouble(txtSauThue.getText())));
				CreditTransaction transaction = new CreditTransaction();
				transaction.setAmount((MyDouble.parseDouble(txtSauThue.getText())));
				transaction.setCreditId(credit.getId());
				transaction.setLoginId(credit.getLoginId());
				transaction.setDateExecute(new Date());
				transaction.setInvoiceId(invoiceDetail.getId());
				CustomerModelManager.getInstance().saveCredit(credit);
				CustomerModelManager.getInstance().saveCreditTransaction(transaction);
				InvoiceDetail invoice = AccountingModelManager.getInstance().getInvoiceDetailByCredit(customer.getCode());
				if (invoice == null) {
					invoice = new InvoiceDetail(true);
					invoice.setType(cbTypeInvoice.getSelectedType().getCode());
					invoice.setActivityType(ActivityType.Payment);
					invoice.setCurrencyRate(currency.getRate());
					invoice.setCurrencyUnit(currency.getCode());
					invoice.setDiscount(0);
					invoice.setDiscountRate(0);
					invoice.setTotal(0);
					invoice.setCustomerCode(customer.getCode());
					invoice.setInvoiceName("KH " + customer.toString() + " sử dụng tiền ứng trước");
					invoice.setTotalTax(0);
					invoice.setStartDate(dcStartDate.getDate());
					invoice.setEndDate(dcStartDate.getDate());
					invoice.setInvoiceCode("TCTT" + df.format(new Date()));
					invoice.setFinalCharge(0);
					invoice.setStatus(Status.Paid);
					if (employee != null) {
						invoice.setEmployeeCode(employee.getCode());
					}

					invoice.setDepartmentCode(HRModelManager.getInstance().getDepartmentOther().getPath());

					List<AccountMembership> accMemberShips = AccountModelManager.getInstance().findMembershipByAccountLoginId(
					    customer.getLoginId());
					if (accMemberShips != null && accMemberShips.size() > 0) {
						invoice.setGroupCustomerCode(accMemberShips.get(0).getGroupPath());
					} else {
						invoice.setGroupCustomerCode(CustomerModelManager.getInstance().getGroupCustomerOther().getPath());
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
					invoiceItem1.setItemName(txtNghiepVu.getText());
					invoiceItem1.setQuantity(1);
					invoiceItem1.setActivityType(InvoiceItem.ActivityType.Payment);
					invoiceItem1.setTax(0);
					invoiceItem1.setTotal(0);
					invoiceItem1.setUnitPrice(0);
					invoiceItem1.setFinalCharge(0);
					invoice.add(invoiceItem1);
					AccountingModelManager.getInstance().saveInvoiceDetail(invoice);
				}
			}
		}
		if (reset && exit) {
			(((JDialog) getRootPane().getParent())).dispose();
			exit = false;
		} else if (reset && !exit)
			reset();

	}

	public String getPathProjectCode(Project projectSelected) {
		Project p = projectSelected;
		if (p != null) {
			p = RestaurantModelManager.getInstance().getProjectById(p.getId());
			String path = p.getCode();
			while (p.getParentCode() != null && !p.getParentCode().equals("")) {
				try {
					Project pNext = RestaurantModelManager.getInstance().getProjectByCode(p.getParentCode());
					if (pNext != null) {
						path = pNext.getCode() + "/" + path;
						p = pNext;
					} else {
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return path = "/" + path;
		} else {
			return null;
		}
	}

	public void reset() {
		cbThuePercent.setSelectedIndex(-1);
		txtChietKhauPercent.setText("0");
		txtTongTien.setText("0");
		txtDaTra.setText("0");
		txtPhaiTra.setText("0");
		try {
			Employee e = HRModelManager.getInstance().getBydLoginId(ManagerAuthenticate.getInstance().getLoginId());
			if (e != null) {
				txtNhanVien.setItem(e);
			} else {
				txtNhanVien.setItem(null);
			}
		} catch (Exception e) {

		}
		txtKhachHang.setItem(null);
		txtNVPhucVu.setItem(null);
		txtGhiChu.setText("");
		txtNghiepVu.setText(DateUtil.asCompactDateTimeId(new Date()));

		lblMoneySpell.setText("không");
		if (!comboBoxVariable[0].trim().isEmpty()) {
			cbDepartment.setSelectDepartmentByCode(comboBoxVariable[0]);
		} else {
			cbDepartment.setSelectDepartmentByIndex(-1);
		}

		if (!comboBoxVariable[1].trim().isEmpty()) {
			cbStore.setSelectStoreByCode(comboBoxVariable[1]);
		} else {
			cbStore.setSelectedIndex(-1);
		}

		if (!comboBoxVariable[2].equals(RestaurantModelManager.getInstance().getProjectOther().getCode())
		    && !comboBoxVariable[2].equals("")) {
			Project project = RestaurantModelManager.getInstance().getProjectByCode(comboBoxVariable[2]);
			txtProject.setItem(project);
		} else {
			txtProject.setItem(null);
		}

		txtNVPhucVu.setText("");
		txtNVPhucVu.setObject(null);
		txtNVPhucVu.setItem(null);
		txtPTPhucVu.setText("0");
		invoiceDetail = null;
		canceEdit(true);
		if (cbTypeInvoice.getSelectedType().getLabel().toLowerCase().indexOf("lương") >= 0
		    || cbTypeInvoice.getSelectedType().getLabel().toLowerCase().indexOf("thưởng") >= 0) {
			txtKhachHang.setEnabled(false);
		} else {
			txtKhachHang.setEnabled(true);
		}
		loadCode();
	}

	private void F12() {
		if (txtTypeInvoice.isVisible()) {
			F12 = 0;
			txtTypeInvoice.setVisible(false);
			cbTypeInvoice.setVisible(true);
		} else {
			F12 = 1;
			txtTypeInvoice.setVisible(true);
			cbTypeInvoice.setVisible(false);
		}

	}

	private void addKeyBindings(JComponent jc) {
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0, false), "F12");
		jc.getActionMap().put("F12", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				F12();
			}
		});

		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0, false), "F10");
		jc.getActionMap().put("F10", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				Profile profile = AccountModelManager.getInstance().getProfileConfigAdmin();
				if (profile.get("PrintReceipt") == null || profile.get("PrintReceipt").equals("A4")) {
					PanelChoise pnPanel = new PanelChoise("Thay đổi khổ giấy in từ A4 sang A5?");
					DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
					dialog1.setName("dlXoa");
					dialog1.setTitle("Thay đổi khổ giấy");
					dialog1.setLocationRelativeTo(null);
					dialog1.setModal(true);
					dialog1.setVisible(true);
					if (pnPanel.isDelete()) {
						profile.put("PrintReceipt", "A5");
						AccountModelManager.getInstance().saveProfileConfig(
						    ManagerAuthenticate.getInstance().getOrganizationLoginId(), profile);
					}
				} else {
					PanelChoise pnPanel = new PanelChoise("Thay đổi khổ giấy in từ A5 sang A4?");
					DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
					dialog1.setName("dlXoa");
					dialog1.setTitle("Thay đổi khổ giấy");
					dialog1.setLocationRelativeTo(null);
					dialog1.setModal(true);
					dialog1.setVisible(true);
					if (pnPanel.isDelete()) {
						profile.put("PrintReceipt", "A4");
						AccountModelManager.getInstance().saveProfileConfig(
						    ManagerAuthenticate.getInstance().getOrganizationLoginId(), profile);
					}
				}

			}
		});

		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "ESC");
		jc.getActionMap().put("ESC", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				(((JDialog) getRootPane().getParent())).dispose();
			}
		});
	}

	private boolean checkData() throws Exception {
		double point = 0;
		int count = 0;

		if (txtTypeInvoice.getItem() == null && txtTypeInvoice.isVisible() == true) {
			lbF12.setForeground(Color.RED);
			count++;

		} else {
			lbF12.setForeground(Color.black);
		}

		if (txtNghiepVu.getText().equals("")) {
			lbNghiepVu.setForeground(Color.RED);
			count++;

		} else {
			lbNghiepVu.setForeground(Color.black);
		}

		try {
			Integer.parseInt(txtPTPhucVu.getText());
			lbPTPV.setForeground(Color.black);
		} catch (Exception e) {
			count++;
			lbMessenger.setText("Kiểm tra lỗi báo đỏ!");
			lbPTPV.setForeground(Color.red);
		}

		try {
			point = MyDouble.parseDouble(txtTongTien.getText());
			if (point > 0) {
				lbTongTien.setForeground(Color.black);
			} else {
				count++;
				lbMessenger.setText("Kiểm tra lỗi báo đỏ!");
				lbTongTien.setForeground(Color.red);
			}
		} catch (Exception e) {
			lbMessenger.setText("Kiểm tra lỗi báo đỏ!");
			lbTongTien.setForeground(Color.red);
			count++;
		}

		if (count > 0) {
			lbMessenger.setText("Kiểm tra lỗi báo đỏ!");
			lbMessenger.setForeground(Color.red);
			return false;
		} else
			lbMessenger.setText(" ");
		return true;
	}

	private void loadMoneyDiscout() {
		if (rbtChietKhauPercent.isSelected()) {
			double moneyDiscout;
			try {
				moneyDiscout = (MyDouble.parseDouble(txtTongTien.getText()) * MyDouble.parseDouble(txtChietKhauPercent
				    .getText())) / 100;
			} catch (Exception e) {
				moneyDiscout = 0;
			}
			txtTienChietKhau.setText(MyDouble.valueOf(moneyDiscout).toString());
		}
		loadSumMoney();
	}

	private void loadSumMoney() {
		double moneyDiscount;

		try {
			moneyDiscount = MyDouble.parseDouble(txtTienChietKhau.getText());
		} catch (Exception e) {
			moneyDiscount = 0;
			txtTienChietKhau.setText("0");
		}

		try {
			double totalMoneyAfterDiscount = MyDouble.parseDouble(txtTongTien.getText()) - moneyDiscount;
			double s = MyDouble.valueOf(cbThuePercent.getSelectedItem().toString()).doubleValue();
			double moneyTax = s * totalMoneyAfterDiscount / 100;
			txtTienThue.setText(MyDouble.valueOf(moneyTax).toString());
		} catch (Exception e) {
			txtTienThue.setText("0");
		}

		if (invoiceDetail != null && invoiceDetail.getId() != null) {
			txtDaTra.setText(MyDouble.valueOf(invoiceDetail.getTotalPaid()).toString());
		} else {
			txtDaTra.setText("0");
		}

		try {
			double moneyAfterTax = MyDouble.parseDouble(txtTongTien.getText())
			    - MyDouble.parseDouble(txtTienChietKhau.getText()) + MyDouble.parseDouble(txtTienThue.getText());
			txtSauThue.setText(MyDouble.valueOf(moneyAfterTax).toString());
			double moneyMissing = MyDouble.parseDouble(txtSauThue.getText()) - MyDouble.parseDouble(txtDaTra.getText());
			txtPhaiTra.setText(MyDouble.valueOf(moneyMissing).toString());
		} catch (Exception e) {
			txtSauThue.setText("0");
			txtPhaiTra.setText("0");
		}

		try {
			ManagerConvert convertMoney = new ManagerConvert();
			String strMoney = txtPhaiTra.getText();
			String str = "";
			for (int i = 0; i < strMoney.length(); i++) {
				String s1 = String.valueOf(strMoney.charAt(i));
				if (!s1.trim().isEmpty()) {
					str = str + s1;
				}
			}
			String moneyText = convertMoney.readNumber(str);
			lblMoneySpell.setText(moneyText);
		} catch (Exception e) {
		}
	}

	public void setInvoice(Invoice invoice) throws Exception {
		exit = true;
		invoiceDetail = AccountingModelManager.getInstance().getInvoiceDetail(invoice.getId());
		for (int i = 0; i < cbTypeInvoice.getItemCount(); i++) {
			if (((Option) cbTypeInvoice.getItemAt(i)).getCode().equals(invoiceDetail.getType())) {
				cbTypeInvoice.setSelectedIndex(i);
				break;
			}
		}
		txtNghiepVu.setText(invoiceDetail.getInvoiceName());
		txtMaNghiepVu.setText(invoiceDetail.codeView());
		txtTongTien.setText(MyDouble.valueOf(invoiceDetail.getTotal()).toString());
		cbStore.setSelectStoreByCode(invoiceDetail.getStoreCode());
		if (invoiceDetail.getProjectCode() != null && !invoiceDetail.getProjectCode().equals("")
		    && !invoiceDetail.getProjectCode().endsWith("project-other")) {
			if (invoiceDetail.getProjectCode().indexOf("/") == -1) {
				Project project = RestaurantModelManager.getInstance().getProjectByCode(invoiceDetail.getProjectCode());
				txtProject.setItem(project);
			} else {
				String[] projectCode = invoiceDetail.getProjectCode().split("/");
				Project project = RestaurantModelManager.getInstance().getProjectByCode(projectCode[projectCode.length - 1]);
				txtProject.setItem(project);
			}
		}
		dcStartDate.setDate(invoiceDetail.getStartDate());
		cbDepartment.setSelectDepartmentByPath(invoiceDetail.getDepartmentCode());

		try {
			Employee emp = HRModelManager.getInstance().getEmployeeByCode(invoiceDetail.getEmployeeCode());
			txtNhanVien.setItem(emp);
		} catch (Exception e) {
			txtNhanVien.setItem(null);
		}
		try {
			Customer cus = CustomerModelManager.getInstance().getCustomerByCode(invoiceDetail.getCustomerCode());
			txtKhachHang.setItem(cus);
		} catch (Exception e) {
			try {
				Supplier cus = SupplierModelManager.getInstance().getSupplierByCode(invoiceDetail.getSupplierCode());
				txtKhachHang.setItem(cus);
			} catch (Exception e2) {
			}
		}
		double a = 0;
		for (InvoiceTransaction tr : invoiceDetail.getTransactions()) {
			a = a + tr.getTotal();
		}
		if (invoiceDetail.getTotalPaid() != a) {
			invoiceDetail.setTotalPaid(a);
			invoiceDetail = AccountingModelManager.getInstance().saveInvoice(invoiceDetail);
		}

		txtChietKhauPercent.setText(MyDouble.valueOf(invoiceDetail.getDiscountRate()).toString());
		txtTienThue.setText(MyDouble.valueOf(invoiceDetail.getTotalTax()).toString());
		txtTienChietKhau.setText(MyDouble.valueOf(invoiceDetail.getDiscount()).toString());
		txtSauThue.setText(MyDouble.valueOf(invoiceDetail.getFinalCharge()).toString());
		txtPhaiTra.setText(MyDouble.valueOf(invoiceDetail.getFinalCharge() - invoiceDetail.getTotalPaid()).toString());
		txtDaTra.setText(MyDouble.valueOf(invoiceDetail.getTotalPaid()).toString());
		cbUnitMoney.setSelectedCurrency(invoiceDetail.getCurrencyUnit());
		txtGhiChu.setText(invoiceDetail.getDescription());
		List<Contributor> contributors = invoiceDetail.getContributors();
		if (contributors != null) {
			List<Employee> es = new ArrayList<Employee>();
			for (Contributor c : contributors) {
				if (c.getRole().equals(Contributor.nhanVienPV)) {
					try {
						Employee e = HRModelManager.getInstance().getBydLoginId(c.getIdentifierId());
						txtNVPhucVu.setItem(e);
						txtPTPhucVu.setText(Integer.valueOf(c.getPercent()).toString());
						es.add(e);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		if (invoice.getStatus().equals(Status.Paid)) {
			chbTraSau.setEnabled(false);
		} else {
			if (invoice.getStatus().equals(Status.PostPaid)) {
				chbTraSau.setSelected(true);
			}
		}

		canceEdit(false);
		try {
			ManagerConvert convertMoney = new ManagerConvert();
			String strMoney = txtPhaiTra.getText();
			String str = "";
			for (int i = 0; i < strMoney.length(); i++) {
				String s1 = String.valueOf(strMoney.charAt(i));
				if (!s1.trim().isEmpty()) {
					str = str + s1;
				}
			}
			String moneyText = convertMoney.readNumber(str);
			lblMoneySpell.setText(moneyText);
		} catch (Exception e) {
		}
	}

	private void printPhieuThuReport() throws Exception {
		ReportPhieuChi reportGuiBillPhieuThu = new ReportPhieuChi();
		String donvi = AccountModelManager.getInstance().getNameByLoginId(
		    ManagerAuthenticate.getInstance().getOrganizationLoginId());
		String diachi = AccountModelManager.getInstance().getAddressByLoginId(
		    ManagerAuthenticate.getInstance().getOrganizationLoginId());
		String nguoinoptien = "";
		String diachinguoinoptien = "";
		String maKH = "";
		try {
			Customer c = (Customer) txtKhachHang.getItem();
			nguoinoptien = c.toString();
			diachinguoinoptien = AccountModelManager.getInstance().getAddressByLoginId(c.getLoginId());
			maKH = c.getCode();
		} catch (Exception e) {
			try {
				Supplier c = (Supplier) txtKhachHang.getItem();
				nguoinoptien = c.toString();
				maKH = c.getCode();
				diachinguoinoptien = AccountModelManager.getInstance().getAddressByLoginId(c.getLoginId());
			} catch (Exception e2) {
			}
		}

		Option option = cbTypeInvoice.getSelectedType();
		if (txtTypeInvoice.isVisible() && txtTypeInvoice.getItem() != null) {
			option = txtTypeInvoice.getItem();
		}
		if (option.getLabel().toLowerCase().indexOf("lương") >= 0 || option.getLabel().toLowerCase().indexOf("thưởng") >= 0) {
			try {
				Employee c = txtNVPhucVu.getItem();
				nguoinoptien = c.toString();
				diachinguoinoptien = AccountModelManager.getInstance().getAddressByLoginId(c.getLoginId());
				maKH = c.getCode();
			} catch (Exception e) {
			}

		}
		String lydothutien = txtNghiepVu.getText();
		MyDouble myBigTienNop = new MyDouble(txtSauThue.getText());
		myBigTienNop.setNumDot(0);
		String sotiennop = myBigTienNop.toString();
		String unitMoney = cbUnitMoney.getSelectedCurrency().toString();

		String str = "";
		for (int i = 0; i < sotiennop.length(); i++) {
			String s1 = String.valueOf(sotiennop.charAt(i));
			if (!s1.trim().isEmpty()) {
				str = str + s1;
			}
		}
		String str1 = "1";
		if (activityType.equals(Invoice.ActivityType.Payment)) {
			str1 = "0";
		}
		String vietbangchu = ManagerConvert.getInstance().convertMoney(str) + " đồng chẵn";
		String departmentLabel = "";
		if (cbDepartment.getSelectedDepartment() != null) {
			departmentLabel = cbDepartment.getSelectedDepartment().getLabel();
		}
		String projectCode = "";
		if (txtProject.getItem() != null) {
			projectCode = txtProject.getItem().codeView();
		}
		String maSoThue = AccountModelManager.getInstance().getRegistrationCodeByLoginId(
		    ManagerAuthenticate.getInstance().getOrganizationLoginId());
		String[] parametersReportPhieuThu = new String[] { donvi, diachi, "", txtMaNghiepVu.getText(), "", "",
		    nguoinoptien, diachinguoinoptien, lydothutien, sotiennop, vietbangchu, txtGhiChu.getText(),
		    new SimpleDateFormat("dd").format(dcStartDate.getDate()),
		    new SimpleDateFormat("MM").format(dcStartDate.getDate()),
		    new SimpleDateFormat("yyyy").format(dcStartDate.getDate()), unitMoney, "", "", str1, departmentLabel, maSoThue,
		    projectCode, maKH, txtNhanVien.getText() };
		reportGuiBillPhieuThu.setParameter(parametersReportPhieuThu);
		Profile profile = AccountModelManager.getInstance().getProfileConfigAdmin();
		if (profile.get("PrintReceipt") != null) {
			String strP = profile.get("PrintReceipt").toString();
			reportGuiBillPhieuThu.setPaperSize(strP);
		}
		reportGuiBillPhieuThu.printReport();
		;
	}

	@Override
	public void update(Object o, Object arg) {
		if (arg instanceof Employee || o instanceof Employee) {
			if (arg != null) {
				Employee emp = (Employee) arg;
				// try {
				// List<AccountMembership> accMemberShips =
				// AccountModelManager.getInstance().findMembershipByAccountLoginId(emp.getLoginId());
				// if (accMemberShips != null && accMemberShips.size() > 0) {
				// List<AccountGroup> accountGroups = new ArrayList<AccountGroup>();
				// for (AccountMembership am : accMemberShips) {
				// AccountGroup ag =
				// AccountModelManager.getInstance().getGroupByPath(am.getGroupPath());
				// accountGroups.add(ag);
				// }
				// cbPhongBan.setData(accountGroups, "");
				// if (cbPhongBan.getModel().getSize() > 0)
				// cbPhongBan.setSelectDepartmentIndex(1);
				// } else {
				// cbPhongBan.setData(new ArrayList<AccountGroup>(), null);
				// }
				// } catch (Exception e) {
				// e.printStackTrace();
				// }
			} else {
				cbDepartment.resetData();
			}
		}
		if (arg instanceof Option) {
			Option option = (Option) arg;
			if (option.getLabel().toLowerCase().indexOf("lương") >= 0
			    || option.getLabel().toLowerCase().indexOf("thưởng") >= 0) {
				txtKhachHang.setEnabled(false);
			} else {
				txtKhachHang.setEnabled(true);
			}
		}
	}

	// set dữ liệu trả thưởng
	public void setTraThuong(String tongTien, String ghiChu, boolean xuly) {
		txtTongTien.setText(tongTien);
		txtGhiChu.setText(ghiChu);
		this.exit = xuly;
	}

	// Tạo dữ liệu tự động test phân trang
	@Override
	public void createDataTest() {
		for (int i = 1; i <= 50; i++) {
			try {
				InvoiceDetail invoiceDetail = new InvoiceDetail();
				InvoiceItem invoiceItem = new InvoiceItem();

				invoiceDetail.setType("Loại HktTest" + i);
				invoiceDetail.setActivityType(activityType);
				invoiceDetail.setCurrencyUnit("VND");
				invoiceDetail.setInvoiceName("Nghiệp vụ HktTest" + i);
				invoiceDetail.setStatus(Status.Paid);
				invoiceDetail.setStartDate(new Date());
				invoiceDetail.setInvoiceCode("Mã Nghiệp vụ HktTest" + i);
				invoiceDetail.setFinalCharge(1000000);
				invoiceItem.setCurrencyUnit("VND HktTest" + i);

				AccountingModelManager.getInstance().saveInvoiceDetail(invoiceDetail);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteDataTest() {
		if (!PanelTestAll.runAll) {
			DialogTest.getInstance().show("Xóa toàn bộ dữ liệu test ?");
			if (DialogTest.getInstance().isTest()) {
				try {
					LocaleModelManager.getInstance().deleteTest("HktTest");
					AccountingModelManager.getInstance().deleteTest("HktTest");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void save() throws Exception {
		if (activityType == ActivityType.Receipt) {
			if (UIConfigModelManager.getInstance().getPermission(permission1) == Capability.READ) {
				JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");

			} else {
				if (!checkData()) {
					return;
				}

				checkSave(true);
			}
		} else {
			if (UIConfigModelManager.getInstance().getPermission(permission2) == Capability.READ) {
				JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");

			} else {
				if (!checkData()) {
					return;
				}
				checkSave(true);
			}
		}
		if (exit == true)
			((JDialog) getRootPane().getParent()).dispose();

	}

	@Override
	public void edit() throws Exception {
		if (activityType.equals(ActivityType.Receipt)) {
			if (UIConfigModelManager.getInstance().getPermission(permission1) == Capability.READ) {
				JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			} else {

				canceEdit(true);
				txtDaTra.setEnabled(false);
				txtPhaiTra.setEnabled(false);
				txtSauThue.setEnabled(false);
				txtMaNghiepVu.setEnabled(false);
			}
		} else {

			if (UIConfigModelManager.getInstance().getPermission(permission2) == Capability.READ) {
				JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			} else {
				canceEdit(true);
				txtDaTra.setEnabled(false);
				txtPhaiTra.setEnabled(false);
				txtSauThue.setEnabled(false);
				txtMaNghiepVu.setEnabled(false);
			}
		}

	}

	@Override
	public void delete() throws Exception {
		if (activityType.equals(ActivityType.Receipt)) {
			if (UIConfigModelManager.getInstance().getPermission(permission1) != Capability.ADMIN) {
				JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
				return;
			} else {
				try {
					AccountingModelManager.getInstance().deleteInvoice(invoiceDetail);
					((JDialog) getRootPane().getParent()).dispose();
				} catch (Exception e2) {
				}
			}
		} else {
			if (UIConfigModelManager.getInstance().getPermission(permission2) != Capability.ADMIN) {
				JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
				return;
			} else {
				try {
					AccountingModelManager.getInstance().deleteInvoice(invoiceDetail);
					((JDialog) getRootPane().getParent()).dispose();
				} catch (Exception e2) {
				}
			}
		}

	}

	@Override
	public void refresh() throws Exception {
		setInvoice(invoiceDetail);

	}

	public ActionListener getActionListener() {
		ActionListener a = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					printPhieuThuReport();
					txtDaTra.setEnabled(false);
					txtPhaiTra.setEnabled(false);
					txtSauThue.setEnabled(false);
					txtMaNghiepVu.setEnabled(false);
					chbTraSau.setSelected(false);

				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}
		};
		return a;
	}
}
