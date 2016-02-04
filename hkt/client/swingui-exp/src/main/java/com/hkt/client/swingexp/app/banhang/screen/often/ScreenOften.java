package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.hkt.client.swing.robot.console.JVMEnv;
import com.hkt.client.swingexp.app.banhang.list.ChooseDelProduct;
import com.hkt.client.swingexp.app.banhang.list.PanelChooseDesProduct;
import com.hkt.client.swingexp.app.banhang.list.PanelChoosePrice;
import com.hkt.client.swingexp.app.banhang.list.PanelChooseProductExport;
import com.hkt.client.swingexp.app.banhang.list.PanelChooseQuantity;
import com.hkt.client.swingexp.app.banhang.list.PanelUpfrontPayment;
import com.hkt.client.swingexp.app.component.PanelBackground;
import com.hkt.client.swingexp.app.component.TextPopup;
import com.hkt.client.swingexp.app.core.DialogList;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.HKTFile;
import com.hkt.client.swingexp.app.core.IMyObserver;
import com.hkt.client.swingexp.app.core.IScreenSales;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDialogPlus;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDown;
import com.hkt.client.swingexp.app.core.MouseEventMove;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.app.khohang.PanelRepositoryProducts2;
import com.hkt.client.swingexp.app.khuvucbanhang.PanelManageCodes;
import com.hkt.client.swingexp.app.khuyenmai.DialogChoiseMenuItem;
import com.hkt.client.swingexp.app.khuyenmai.list.TableListViewPromotion;
import com.hkt.client.swingexp.app.print.ReportPrint;
import com.hkt.client.swingexp.app.virtualkey.text.PanelTextKeyboard;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.GenericOptionModelManager;
import com.hkt.client.swingexp.model.GuiModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.ManagerConvert;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.PromotionModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.SupplierModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.client.swingexp.model.WarehouseModelManager;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.accounting.DefaultInvoiceCalculator;
import com.hkt.module.accounting.entity.Contributor;
import com.hkt.module.accounting.entity.Invoice;
import com.hkt.module.accounting.entity.Invoice.Status;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.accounting.entity.InvoiceItemAttribute;
import com.hkt.module.accounting.entity.InvoiceTransaction;
import com.hkt.module.accounting.entity.InvoiceTransaction.TransactionType;
import com.hkt.module.config.locale.Currency;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.partner.customer.entity.Credit;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.customer.entity.Point;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductAttribute;
import com.hkt.module.product.entity.ProductPrice;
import com.hkt.module.promotion.entity.Menu;
import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.LocationAttribute;
import com.hkt.module.restaurant.entity.Project;
import com.hkt.module.restaurant.entity.Reservation;
import com.hkt.module.restaurant.entity.Table;
import com.hkt.module.warehouse.entity.IdentityProduct;
import com.hkt.module.warehouse.entity.IdentityProduct.ExportType;
import com.hkt.module.warehouse.entity.WarehouseInventory;
import com.hkt.util.text.DateUtil;

/*
 * author: longnt
 */

public class ScreenOften extends JDialog implements IScreenSales, IMyObserver {

	public static final String LichTrinh = "lichtrinh";
	public static final String TraGop = "tragop";
	public static final String ManagerCredit = "credit";
	public static final String ManagerPoint = "point";
	public static final String ComboBoxCuaHang = "cbCuaHang";
	public static boolean LamTron = true;
	private Profile profile;
	public static String permission;
	public static ScreenOften screenOften1;

	public static ScreenOften getInstance() {
		if (screenOften1 == null) {
			screenOften1 = new ScreenOften();
			screenOften1.loadData();
		}
		return screenOften1;
	}

	private TableModelInvoiceItem tableModel;
	private TableEat tableEat;
	private double quantity = 1;
	private PanelProduct panelProduct;
	private PanelPayment panelPayment;
	private PanelTable panelTable;
	private DateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat dfTime = new SimpleDateFormat("HH:mm");
	private DateFormat dfCode = new SimpleDateFormat("yyyyMMddHHmmss");
	private boolean flag1;
	private boolean flag;
	private JCheckBox chbServer;
	private String pricingType = "";
	private Table table;
	private JPanel panelN;
	private String currencyCode = "VND";
	private boolean resetProduct;
	private InvoiceDetail detail;
	private PanelTextKeyboard panelTextKeyboard;
	private JLabel lbVituaKey;
	private JLabel lbDiscount;
	private boolean printAgain;
	private double priceKara;

	public void setResetProduct(boolean flag) {
		panelProduct.setListProductTag(null);
		this.resetProduct = flag;
	}

	public void setPanelProduct(PanelProduct panelProduct) {
		this.panelProduct = panelProduct;
	}

	public PanelTable getPanelTable() {
		return panelTable;
	}

	public void setPanelTable(PanelTable panelTable) {
		this.panelTable = panelTable;
	}

	public InvoiceDetail getDetail() {
		return detail;
	}

	public void setDetail(InvoiceDetail detail) {
		this.detail = detail;
	}

	public ScreenOften() {
		setUndecorated(true);
		panelPayment = new PanelPayment(false);
		initComponents();
		setModal(true);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());

		tableModel = panelPayment.getTableModel();
		jScrollPane3.setViewportView(panelPayment);
		jScrollPane3.setOpaque(false);
		jScrollPane3.getViewport().setOpaque(false);
		jScrollPane3.setWheelScrollingEnabled(false);
		panelProduct = new PanelProduct(this);
		panelN.setVisible(false);
		panelTable = new PanelTable(this);
		panelTable.setName("panelTable");
		if (Toolkit.getDefaultToolkit().getScreenSize().width <= 1024) {
			panelPayment.setSize(100, 50);
			panelTable.setSize(200, 50);
			panelTable.setPreferredSize(panelTable.getSize());
			panelProduct.setSize(200, 50);
			panelProduct.setPreferredSize(panelTable.getSize());
		} else {
			panelTable.setSize(250, 50);
			panelTable.setPreferredSize(panelTable.getSize());
			panelProduct.setSize(250, 50);
			panelProduct.setPreferredSize(panelTable.getSize());
		}

		jScrollPane1.setViewportView(panelTable);

		jScrollPane2.setViewportView(panelProduct);
		txtTimKiem.addObserver(this);
		txtTable.addObserver(this);
		try {
			txtTable.setData(RestaurantModelManager.getInstance().getTables());
		} catch (Exception e) {
		}
		txtPartner.addObserver(this);
		txtEmployee.addObserver(this);
		txtTable.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				if (txtTable.getItem() != null) {
					txtTable.setItem(null);
					txtTable.setText("");
					txtTimKiem.requestFocus();
				}
			}
		});

		txtTimKiem.setComponentFocus(txtSoluong);
		lbOpenALl.addMouseListener(new MouseEventMove());
		lbClossAll.addMouseListener(new MouseEventMove());
		lblKM.addMouseListener(new MouseEventMove());
		lblKM.setVisible(false);
		lableDate.setEnabled(false);
		lableTime.setEnabled(false);
		lableDate.setText(dfDate.format(new Date()));
		lableTime.setText(dfTime.format(new Date()));
		addKeyBindings(panelBackground1);
		resetGui();
		addVituarKey(panelBackground1);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				try {
					panelTable.refeshGui();
					lbNameTable.setMaximumSize(lbNameTable.getPreferredSize());
					lbNameTable.setMinimumSize(lbNameTable.getPreferredSize());
					System.out.println("opendScreen   " + new JVMEnv().getMemoryInfo());
					System.out.println(jPanel8.getWidth());
					jPanel8.setSize(new Dimension(jPanel8.getWidth(), jPanel8.getHeight()));
					jPanel8.setPreferredSize(new Dimension(jPanel8.getWidth(), jPanel8.getHeight()));
					jPanel8.setMaximumSize(new Dimension(jPanel8.getWidth(), jPanel8.getHeight()));
					jPanel8.setMinimumSize(new Dimension(jPanel8.getWidth(), jPanel8.getHeight()));

				} catch (Exception e2) {
				}

			}

			@Override
			public void windowClosed(WindowEvent e) {
				new GuiModelManager().getObservable().deleteObservers();
				System.out.println("closeScreen1   " + new JVMEnv().getMemoryInfo());
			}
		});
	}

	public void loadData() {
		try {
			List<Employee> list1 = HRModelManager.getInstance().getEmployees();
			txtEmployee.setData(list1);
			txtTVPV.setData(list1);
		} catch (Exception e) {
		}
		try {
			List<Customer> customers = CustomerModelManager.getInstance().getCustomers(false);
			txtPartner.setData(customers);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			txtTable.setData(RestaurantModelManager.getInstance().getTables());
		} catch (Exception e) {
		}

		if (resetProduct) {
			try {
				panelProduct.loadGui();
				resetProduct = false;
			} catch (Exception e) {
				resetProduct = false;
			}
		}

		panelPayment.loadCboTax();
		if (profile.get(DialogConfig.Keyboard) != null && profile.get(DialogConfig.Keyboard).toString().equals("true")) {
			panelTextKeyboard.setVisible(true);
			lbVituaKey.setVisible(true);
		} else {
			panelTextKeyboard.setVisible(false);
			lbVituaKey.setVisible(false);
		}
		if (tableEat != null && tableEat.getTable() != null
		    && tableEat.getTable().getCode().equals(RestaurantModelManager.getInstance().getTablePaymentAfter().getCode())) {
			tableEat = null;
			reset();
		}
		if (!LamTron) {
			panelPayment.refeshGui();
		}

	}

	private void addVituarKey(Container c) {
		Component[] comps = c.getComponents();
		for (Component comp : comps) {
			if (comp instanceof JTextField) {
				if (((JTextField) comp).isFocusOwner()) {
					panelTextKeyboard.setText((JComponent) comp);
				}
			}
			comp.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					Component comp = (Component) e.getSource();
					if (comp instanceof JComponent) {
						panelTextKeyboard.setText((JComponent) comp);
					}
				}
			});
			if (comp instanceof Container) {
				addVituarKey((Container) comp);
			}
		}
	}

	// Load lại giao diện sau khi thiết lập
	public void resetGui() {

		try {
			profile = AccountModelManager.getInstance().getProfileConfigAdmin();
			try {
				if (!profile.get(DialogConfig.TbArea).equals(true)) {
					pricingType = profile.get(DialogConfig.ProductPriceType).toString();
					tableModel.setInfoInvoice("", pricingType, table);
				}

			} catch (Exception e) {
			}
			if (profile.get(DialogConfig.Currency) != null) {
				try {
					currencyCode = tableModel.getInvoiceDetail().getCurrencyUnit();
				} catch (Exception e) {
					currencyCode = profile.get(DialogConfig.Currency).toString();
				}

				panelPayment.setCurrencyCode(currencyCode);
			}
			if (!ManagerAuthenticate.getInstance().getLoginId().equals("admin")) {
				if (profile.get(DialogConfig.InHuyMon).toString().equals("true")) {
					btnInHuyMon.setEnabled(true);
				} else {
					btnInHuyMon.setEnabled(false);
				}

				if (profile.get(DialogConfig.InCheBien).toString().equals("true")) {
					btnInCheBien.setEnabled(true);
				} else {
					btnInCheBien.setEnabled(false);
				}

				if (profile.get(DialogConfig.TraSau).toString().equals("true")) {
					btnTraSau.setEnabled(true);
				} else {
					btnTraSau.setEnabled(false);
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
					btnPaymen.setEnabled(true);
				} else {
					btnPaymen.setEnabled(false);
				}

				if (profile.get(DialogConfig.SoLuong).toString().equals("true")) {
					btnSoLuong.setEnabled(true);
				} else {
					btnSoLuong.setEnabled(false);
				}

				if (profile.get(DialogConfig.Datgio).toString().equals("true")) {
					btnDatGio.setVisible(true);
				} else {
					btnDatGio.setVisible(false);
				}
				if (profile.get(DialogConfig.GioHoaDon).toString().equals("true")) {
					btnGioHoaDon.setVisible(true);
				} else {
					btnGioHoaDon.setVisible(false);
				}

				if (profile.get(DialogConfig.DoiGia).toString().equals("true")) {
					btnChangePrice.setEnabled(true);
				} else {
					btnChangePrice.setEnabled(false);
				}

				boolean[] canEdit = new boolean[] { false, false, btnSoLuong.isEnabled(), btnChangePrice.isEnabled(), true,
				    false, true };
				tableModel.setCanEdit(canEdit);

				if (profile.get(DialogConfig.XoaSP).toString().equals("true")) {
					btnDelete.setEnabled(true);
				} else {
					btnDelete.setEnabled(false);
				}

				if (profile.get(DialogConfig.HuySP).toString().equals("true")) {
					btnCancel.setEnabled(true);
				} else {
					btnCancel.setEnabled(false);
				}

				if (profile.get(DialogConfig.MuaHo).toString().equals("true")) {
					lbMuaHo.setEnabled(true);

				} else {
					lbMuaHo.setEnabled(false);
				}

				if (profile.get(ManagerCredit) != null && profile.get(ManagerCredit).toString().equals("true")) {
					chbCredit.setSelected(true);
				} else {
					chbCredit.setSelected(false);
				}

				if (profile.get(ManagerPoint) != null && profile.get(ManagerCredit).toString().equals("true")) {
					chbPoint.setSelected(true);
				} else {
					chbPoint.setSelected(false);
				}

				if (profile.get(LichTrinh) != null && profile.get(LichTrinh).toString().equals("true")) {
					chbLichTrinh.setSelected(true);
				} else {
					chbLichTrinh.setSelected(false);
				}

				if (profile.get(TraGop) != null && profile.get(TraGop).toString().equals("true")) {
					chbTraGop.setSelected(true);
				} else {
					chbTraGop.setSelected(false);
				}
			}
			panelPayment.refeshGui();
		} catch (Exception e) {

		}
		// System.out.println("opend   " + new JVMEnv().getMemoryInfo());
	}

	private void initComponents() {
		panelBackground1 = new PanelBackground();
		button_C = new javax.swing.JButton();
		lbOpenALl = new javax.swing.JLabel();
		lbClossAll = new javax.swing.JLabel();
		jPanel5 = new javax.swing.JPanel();
		btnDelete = new javax.swing.JButton();
		btnCancel = new javax.swing.JButton();
		btnSoLuong = new javax.swing.JButton();
		btnChangePrice = new javax.swing.JButton();
		btnDatGio = new javax.swing.JButton();
		btnGioHoaDon = new javax.swing.JButton();
		lbKH = new javax.swing.JLabel();
		lblKM = new javax.swing.JLabel();
		btnLichTrinh = new javax.swing.JButton();
		btnTraGop = new javax.swing.JButton();
		btnDiscountProduct = new javax.swing.JButton();
		lbMuaHo = new javax.swing.JLabel();
		panelMain = new javax.swing.JPanel();
		jPanel2 = new javax.swing.JPanel();
		jScrollPane2 = new javax.swing.JScrollPane();
		jPanel3 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		panelNotes = new javax.swing.JPanel();
		jLabel3 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		jLabel7 = new javax.swing.JLabel();
		jLabel10 = new javax.swing.JLabel();
		jLabel25 = new javax.swing.JLabel();
		jLabel26 = new javax.swing.JLabel();
		jLabel27 = new javax.swing.JLabel();
		jLabel28 = new javax.swing.JLabel();
		chbPoint = new javax.swing.JCheckBox();
		jLabel33 = new javax.swing.JLabel();
		panelColor = new javax.swing.JPanel();
		jLabel8 = new javax.swing.JLabel();
		jLabel9 = new javax.swing.JLabel();
		jLabel11 = new javax.swing.JLabel();
		jLabel17 = new javax.swing.JLabel();
		jLabel18 = new javax.swing.JLabel();
		jLabel19 = new javax.swing.JLabel();
		jLabel20 = new javax.swing.JLabel();
		jLabel21 = new javax.swing.JLabel();
		jLabel22 = new javax.swing.JLabel();
		jLabel23 = new javax.swing.JLabel();
		jLabel24 = new javax.swing.JLabel();
		jLabel29 = new javax.swing.JLabel();
		chbLichTrinh = new javax.swing.JCheckBox();
		chbTraGop = new javax.swing.JCheckBox();
		chbCredit = new javax.swing.JCheckBox();
		btnLamTron = new javax.swing.JButton();
		jLabel4 = new javax.swing.JLabel();
		txtTimKiem = new TextPopup<Product>(Product.class);
		jLabel31 = new javax.swing.JLabel();
		txtSoluong = new javax.swing.JTextField();
		jLabel32 = new javax.swing.JLabel();
		txtTable = new TextPopup<Table>(Table.class);
		jPanel8 = new javax.swing.JPanel();
		btnPaymen = new javax.swing.JButton();
		btnPrint = new javax.swing.JButton();
		btnPromotions = new javax.swing.JButton();
		btnPayOnce = new javax.swing.JButton();
		btnThemSP = new javax.swing.JButton();
		btnThemSP.setEnabled(false);
		btnInCheBien = new javax.swing.JButton();
		btnInHuyMon = new javax.swing.JButton();
		btnTraSau = new javax.swing.JButton();
		jPanel1 = new javax.swing.JPanel();
		txtGuest = new javax.swing.JTextField();
		jLabel2 = new javax.swing.JLabel();
		lableEmployee = new javax.swing.JLabel();
		lbNameTable = new javax.swing.JLabel("Bàn:");
		lablePartner = new javax.swing.JLabel();
		btnExit = new javax.swing.JButton();
		jLabel30 = new javax.swing.JLabel();
		btnTVPV = new javax.swing.JButton();
		lbNode = new javax.swing.JLabel();
		jPanel4 = new javax.swing.JPanel();
		lableDate = new javax.swing.JTextField();
		lableTime = new javax.swing.JTextField();
		txtEmployee = new TextPopup<Employee>(Employee.class);
		txtTVPV = new TextPopup<Employee>(Employee.class);
		txtPartner = new TextPopup<Customer>(Customer.class);
		jScrollPane3 = new javax.swing.JScrollPane();

		panelBackground1.setOpaque(false);

		button_C.setFont(new java.awt.Font("Tahoma", 1, 12));
		button_C.setText("C"); // NOI18N
		button_C.setContentAreaFilled(false);
		button_C.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				button_CActionPerformed(evt);
			}
		});

		lbOpenALl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lbOpenALl.setText("-"); // NOI18N
		lbOpenALl.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				lbClossAllMouseClicked(evt);
			}
		});

		lbClossAll.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lbClossAll.setText("+"); // NOI18N
		lbClossAll.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				lbOpenALlMouseClicked(evt);
			}
		});

		jPanel5.setOpaque(false);

		btnDelete.setFont(new java.awt.Font("Tahoma", 1, 12));
		btnDelete.addMouseListener(new MouseEventClickButtonDialogPlus(null, null, null));
		btnDelete.setText("Xóa");
		btnDelete.setName("btnDelete");
		btnDelete.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				deleteProduct();
			}
		});

		btnCancel.setFont(new java.awt.Font("Tahoma", 1, 12));
		btnCancel.setText("Hủy"); // NOI18N
		btnCancel.addMouseListener(new MouseEventClickButtonDialogPlus(null, null, null));
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				canceProduct();
			}
		});
		btnSoLuong.setFont(new java.awt.Font("Tahoma", 1, 12));
		btnSoLuong.setText("Số lượng"); // NOI18N
		btnSoLuong.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnSoLuong.addMouseListener(new MouseEventClickButtonDown(null, null, null));
		btnSoLuong.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				changeQuantityProduct();
			}
		});

		btnChangePrice.setFont(new java.awt.Font("Tahoma", 1, 12));
		btnChangePrice.setText("Đổi giá"); // NOI18N
		btnChangePrice.setName("btnChangePrice");
		btnChangePrice.addMouseListener(new MouseEventClickButtonDialogPlus(null, null, null));
		btnChangePrice.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnChangePrice.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				changePriceProduct();
			}
		});

		btnDatGio.setFont(new java.awt.Font("Tahoma", 1, 12));
		btnDatGio.setText("Đặt giờ"); // NOI18N
		btnDatGio.addMouseListener(new MouseEventClickButtonDialogPlus(null, null, null));
		btnDatGio.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnDatGio.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setupTimeProduct();
			}
		});

		btnGioHoaDon.setFont(new java.awt.Font("Tahoma", 1, 12));
		btnGioHoaDon.setText("Sửa giờ"); // NOI18N
		// btnGioHoaDon.addMouseListener(new MouseEventClickButtonDialogPlus(null,
		// null, null));
		btnGioHoaDon.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnGioHoaDon.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setupEditTime();
			}
		});

		lbKH.setFont(new java.awt.Font("Tahoma", 0, 12));
		lbKH.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lbKH.setText("Ghi chú"); // NOI18N
		lbKH.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				viewKH();
			}
		});

		lblKM.setFont(new java.awt.Font("Tahoma", 0, 12));
		lblKM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblKM.setText("KM"); // NOI18N
		lblKM.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				lblKMMouseClicked(evt);
			}
		});
		lbVituaKey = new JLabel();
		lbVituaKey.setFont(new java.awt.Font("Tahoma", 0, 12));
		lbVituaKey.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lbVituaKey.setText("Ẩn"); // NOI18N
		lbVituaKey.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				lbVituaKeyMouseClicked(evt);
			}

			private void lbVituaKeyMouseClicked(MouseEvent evt) {
				if (lbVituaKey.getText().equals("Hiện")) {
					lbVituaKey.setText("Ẩn");
					panelTextKeyboard.setVisible(true);
				} else {
					lbVituaKey.setText("Hiện");
					panelTextKeyboard.setVisible(false);
				}

			}
		});
		lbDiscount = new JLabel();
		lbDiscount.setFont(new java.awt.Font("Tahoma", 0, 12));
		lbDiscount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lbDiscount.setText("Hiện CK"); // NOI18N
		lbDiscount.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				lbVituaKeyMouseClicked(evt);
			}

			private void lbVituaKeyMouseClicked(MouseEvent evt) {
				if (lbDiscount.getText().equals("Hiện CK")) {
					lbDiscount.setText("Ẩn CK");
					panelPayment.setTableDiscount(true);
				} else {
					lbDiscount.setText("Hiện CK");
					panelPayment.setTableDiscount(false);
				}

			}
		});
		btnLichTrinh.setFont(new java.awt.Font("Tahoma", 1, 12));
		btnLichTrinh.setText("Lịch trình"); // NOI18N
		btnLichTrinh.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnLichTrinh.setPreferredSize(new java.awt.Dimension(69, 25));
		btnLichTrinh.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnLichTrinhActionPerformed(evt);
			}
		});

		btnTraGop.setFont(new java.awt.Font("Tahoma", 1, 12));
		btnTraGop.setText("Trả góp"); // NOI18N
		btnTraGop.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// btnTraGopActionPerformed(evt);
			}
		});
		btnDiscountProduct.setFont(new java.awt.Font("Tahoma", 1, 12));
		btnDiscountProduct.setText("CK SP"); // NOI18N
		btnDiscountProduct.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				discountProduct();
			}
		});
		btnLichTrinh.setVisible(true);
		btnTraGop.setVisible(false);

		javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
		jPanel5.setLayout(jPanel5Layout);
		jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
		    .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
		    .addComponent(btnSoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
		    .addComponent(btnChangePrice, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
		    .addComponent(btnDiscountProduct, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
		    .addComponent(btnDatGio, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
		    .addComponent(btnGioHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
		    .addComponent(btnTraGop, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
		    .addComponent(lbKH, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
		    .addComponent(lblKM, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
		    .addComponent(lbVituaKey, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
		    .addComponent(lbDiscount, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE));
		jPanel5Layout
		    .setVerticalGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		        .addGroup(
		            jPanel5Layout
		                .createSequentialGroup()
		                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
		                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 31,
		                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                .addComponent(btnSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
		                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                .addComponent(btnChangePrice, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
		                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                .addComponent(btnDatGio, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
		                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                .addComponent(btnDiscountProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
		                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                .addComponent(btnGioHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
		                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                .addComponent(btnTraGop, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
		                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                .addComponent(lbKH, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
		                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                .addComponent(lblKM, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
		                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addComponent(lbVituaKey, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
		                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addComponent(lbDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
		                    javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(51, Short.MAX_VALUE)));

		lbMuaHo.setFont(new java.awt.Font("Tahoma", 0, 12));
		lbMuaHo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lbMuaHo.setText("Mua hộ"); // NOI18N
		lbMuaHo.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				muaHoSanPham();
			}
		});

		panelMain.setOpaque(false);

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE,
		        Short.MAX_VALUE));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE,
		        Short.MAX_VALUE));

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE,
		        Short.MAX_VALUE));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE,
		        Short.MAX_VALUE));

		panelNotes.setBackground(new java.awt.Color(255, 255, 255));

		jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12));
		jLabel3.setText("F2: In chế biến"); // NOI18N

		jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12));
		jLabel5.setText("F4: In hóa đơn"); // NOI18N

		jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12));
		jLabel7.setText("F6: Thanh toán lẻ"); // NOI18N

		jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12));
		jLabel10.setText("F8: Hiện ghi chú"); // NOI18N

		jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12));
		jLabel25.setText("F3: In hủy món"); // NOI18N

		jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12));
		jLabel26.setText("F5: Thanh toán"); // NOI18N

		jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12));
		jLabel27.setText("F7: Trả sau"); // NOI18N

		jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12));
		jLabel28.setText("F9: Mở bàn"); // NOI18N

		chbPoint.setFont(new java.awt.Font("Tahoma", 0, 12));
		chbPoint.setText("Dùng điểm"); // NOI18N
		chbPoint.setOpaque(false);
		chbPoint.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				chbPointItemStateChanged(evt);
			}
		});
		chbServer = new JCheckBox();
		chbServer.setFont(new java.awt.Font("Tahoma", 0, 12));
		chbServer.setText("Nhiều máy"); // NOI18N
		chbServer.setOpaque(false);
		chbServer.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				if (chbServer.isSelected()) {
					profile.put("AllServer", "true");
				} else {
					profile.put("AllServer", null);
				}
				AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(),
				    profile);
			}
		});

		jLabel33.setFont(new java.awt.Font("Tahoma", 1, 12));
		jLabel33.setText("F10: Mã tự sinh"); // NOI18N

		javax.swing.GroupLayout panelNotesLayout = new javax.swing.GroupLayout(panelNotes);
		panelNotes.setLayout(panelNotesLayout);
		panelNotesLayout.setHorizontalGroup(panelNotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        panelNotesLayout
		            .createSequentialGroup()
		            .addGroup(
		                panelNotesLayout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addGroup(
		                        panelNotesLayout.createSequentialGroup()
		                            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
		                            .addGap(10, 10, 10)
		                            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
		                            .addGap(10, 10, 10)
		                            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
		                    .addGroup(
		                        panelNotesLayout
		                            .createSequentialGroup()
		                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
		                            .addGap(10, 10, 10)
		                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
		                            .addGap(10, 10, 10)
		                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 109,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)))
		            .addGap(10, 10, 10)
		            .addGroup(
		                panelNotesLayout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
		                    .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.TRAILING,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		            .addGroup(
		                panelNotesLayout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(chbPoint, javax.swing.GroupLayout.PREFERRED_SIZE, 109,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(chbServer, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
		            .addContainerGap()));
		panelNotesLayout.setVerticalGroup(panelNotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        panelNotesLayout
		            .createSequentialGroup()
		            .addGap(11, 11, 11)
		            .addGroup(
		                panelNotesLayout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addGroup(
		                        panelNotesLayout
		                            .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                                javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(chbPoint))
		                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                        javax.swing.GroupLayout.PREFERRED_SIZE))
		            .addGap(7, 7, 7)
		            .addGroup(
		                panelNotesLayout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addGroup(
		                        panelNotesLayout
		                            .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)
		                            .addComponent(chbServer, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)))));

		panelColor.setBackground(new java.awt.Color(255, 255, 255));

		jLabel8.setBackground(new java.awt.Color(0, 0, 0));
		jLabel8.setText(""); // NOI18N
		jLabel8.setOpaque(true);

		jLabel9.setBackground(new java.awt.Color(36, 77, 126));
		jLabel9.setText(""); // NOI18N
		jLabel9.setOpaque(true);

		jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11));
		jLabel11.setText("Gọi đồ"); // NOI18N

		jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11));
		jLabel17.setForeground(new java.awt.Color(36, 77, 126));
		jLabel17.setText("Đã phục vụ"); // NOI18N

		jLabel18.setBackground(new java.awt.Color(102, 0, 102));
		jLabel18.setText(""); // NOI18N
		jLabel18.setOpaque(true);

		jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11));
		jLabel19.setForeground(new java.awt.Color(102, 0, 102));
		jLabel19.setText("Đang chế biến"); // NOI18N

		jLabel20.setBackground(new java.awt.Color(0, 72, 0));
		jLabel20.setText(""); // NOI18N
		jLabel20.setOpaque(true);

		jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11));
		jLabel21.setForeground(new java.awt.Color(0, 72, 0));
		jLabel21.setText("Đã thanh toán"); // NOI18N

		jLabel22.setBackground(new java.awt.Color(252, 124, 4));
		jLabel22.setText(""); // NOI18N
		jLabel22.setOpaque(true);

		jLabel23.setFont(new java.awt.Font("Tahoma", 1, 11));
		jLabel23.setForeground(new java.awt.Color(252, 124, 4));
		jLabel23.setText("Chế biến xong"); // NOI18N

		jLabel24.setFont(new java.awt.Font("Tahoma", 1, 11));
		jLabel24.setForeground(new java.awt.Color(155, 43, 42));
		jLabel24.setText("Món hủy"); // NOI18N

		jLabel29.setBackground(new java.awt.Color(155, 43, 42));
		jLabel29.setText(""); // NOI18N
		jLabel29.setOpaque(true);

		chbLichTrinh.setSelected(true);
		chbLichTrinh.setText("Lịch trình"); // NOI18N
		chbLichTrinh.setMaximumSize(new java.awt.Dimension(81, 20));
		chbLichTrinh.setMinimumSize(new java.awt.Dimension(81, 20));
		chbLichTrinh.setOpaque(false);
		chbLichTrinh.setPreferredSize(new java.awt.Dimension(81, 20));
		chbLichTrinh.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				chbLichTrinhItemStateChanged(evt);
			}
		});

		chbTraGop.setSelected(true);
		chbTraGop.setText("Trả góp"); // NOI18N
		chbTraGop.setMaximumSize(new java.awt.Dimension(81, 20));
		chbTraGop.setMinimumSize(new java.awt.Dimension(81, 20));
		chbTraGop.setOpaque(false);
		chbTraGop.setPreferredSize(new java.awt.Dimension(81, 20));
		chbTraGop.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				chbTraGopItemStateChanged(evt);
			}
		});

		chbCredit.setText("Trả trước"); // NOI18N
		chbCredit.setMaximumSize(new java.awt.Dimension(81, 20));
		chbCredit.setMinimumSize(new java.awt.Dimension(81, 20));
		chbCredit.setOpaque(false);
		chbCredit.setPreferredSize(new java.awt.Dimension(81, 20));
		chbCredit.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				chbCreditItemStateChanged(evt);
			}
		});

		btnLamTron.setFont(new java.awt.Font("Tahoma", 1, 12));
		btnLamTron.setText("Làm tròn"); // NOI18N
		btnLamTron.addMouseListener(new MouseEventClickButtonDown(null, null, null));
		btnLamTron.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnLamTron.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				lamTronSo();
			}
		});

		javax.swing.GroupLayout panelColorLayout = new javax.swing.GroupLayout(panelColor);
		panelColor.setLayout(panelColorLayout);
		panelColorLayout.setHorizontalGroup(panelColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        panelColorLayout
		            .createSequentialGroup()
		            .addGroup(
		                panelColorLayout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addGroup(
		                        panelColorLayout
		                            .createSequentialGroup()
		                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)
		                            .addGap(10, 10, 10)
		                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
		                            .addGap(22, 22, 22)
		                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)
		                            .addGap(10, 10, 10)
		                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
		                            .addGap(32, 32, 32)
		                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
		                                javax.swing.GroupLayout.PREFERRED_SIZE).addGap(10, 10, 10)
		                            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE))
		                    .addGroup(
		                        panelColorLayout
		                            .createSequentialGroup()
		                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)
		                            .addGap(10, 10, 10)
		                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
		                            .addGap(22, 22, 22)
		                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)
		                            .addGap(10, 10, 10)
		                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
		                            .addGap(32, 32, 32)
		                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
		                                javax.swing.GroupLayout.PREFERRED_SIZE).addGap(10, 10, 10)
		                            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)))
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		            .addGroup(
		                panelColorLayout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addGroup(
		                        panelColorLayout
		                            .createSequentialGroup()
		                            .addComponent(chbLichTrinh, javax.swing.GroupLayout.PREFERRED_SIZE,
		                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                            .addGap(18, 18, 18)
		                            .addComponent(chbCredit, javax.swing.GroupLayout.PREFERRED_SIZE,
		                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
		                    .addGroup(
		                        panelColorLayout
		                            .createSequentialGroup()
		                            .addComponent(chbTraGop, javax.swing.GroupLayout.PREFERRED_SIZE,
		                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                            .addGap(18, 18, 18)
		                            .addComponent(btnLamTron, javax.swing.GroupLayout.PREFERRED_SIZE, 75,
		                                javax.swing.GroupLayout.PREFERRED_SIZE))).addGap(17, 17, 17)));
		panelColorLayout.setVerticalGroup(panelColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        panelColorLayout
		            .createSequentialGroup()
		            .addGroup(
		                panelColorLayout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 15,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 15,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addGroup(
		                        panelColorLayout
		                            .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 15,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)
		                            .addComponent(chbLichTrinh, javax.swing.GroupLayout.PREFERRED_SIZE, 19,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)
		                            .addComponent(chbCredit, javax.swing.GroupLayout.PREFERRED_SIZE, 19,
		                                javax.swing.GroupLayout.PREFERRED_SIZE))
		                    .addGroup(
		                        panelColorLayout
		                            .createSequentialGroup()
		                            .addGap(1, 1, 1)
		                            .addGroup(
		                                panelColorLayout
		                                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
		                                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
		                                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
		                                        javax.swing.GroupLayout.PREFERRED_SIZE))))
		            .addGap(4, 4, 4)
		            .addGroup(
		                panelColorLayout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 15,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 15,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addGroup(
		                        panelColorLayout
		                            .createSequentialGroup()
		                            .addGap(1, 1, 1)
		                            .addGroup(
		                                panelColorLayout
		                                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
		                                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
		                                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
		                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
		                    .addGroup(
		                        panelColorLayout
		                            .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
		                            .addGroup(
		                                panelColorLayout
		                                    .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		                                    .addComponent(chbTraGop, javax.swing.GroupLayout.PREFERRED_SIZE, 19,
		                                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                                    .addComponent(btnLamTron, javax.swing.GroupLayout.PREFERRED_SIZE, 14,
		                                        javax.swing.GroupLayout.PREFERRED_SIZE))
		                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 15,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)))));

		jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12));
		jLabel4.setText("SP"); // NOI18N

		txtTimKiem.setText(""); // NOI18N
		txtTimKiem.setFont(new java.awt.Font("Tahoma", 0, 14));

		jLabel31.setFont(new java.awt.Font("Tahoma", 0, 12));
		jLabel31.setText("Số lg"); // NOI18N

		txtSoluong.setText(""); // NOI18N

		jLabel32.setFont(new java.awt.Font("Tahoma", 0, 12));
		jLabel32.setText("Bàn/Quầy"); // NOI18N
		txtTable.setText(""); // NOI18N
		txtTable.setFont(new java.awt.Font("Tahoma", 0, 14));
		txtSoluong.setPreferredSize(new Dimension(60, 23));
		txtSoluong.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

		JPanel panelSearch = new JPanel();
		panelSearch.setLayout(new GridLayout(1, 2, 5, 5));
		panelSearch.setOpaque(false);

		JPanel panelSearch1 = new JPanel(new BorderLayout(5, 5));
		panelSearch1.setOpaque(false);
		panelSearch1.add(jLabel32, BorderLayout.LINE_START);
		panelSearch1.add(txtTable, BorderLayout.CENTER);
		panelSearch.add(panelSearch1);

		JPanel panelSearch2 = new JPanel(new BorderLayout(5, 5));
		panelSearch2.setOpaque(false);
		panelSearch2.add(jLabel4, BorderLayout.WEST);
		panelSearch2.add(txtTimKiem, BorderLayout.CENTER);

		JPanel paJPanel = new JPanel(new BorderLayout(5, 5));
		paJPanel.setOpaque(false);
		paJPanel.add(jLabel31, BorderLayout.WEST);
		paJPanel.add(txtSoluong, BorderLayout.CENTER);
		panelSearch2.add(paJPanel, BorderLayout.EAST);

		panelSearch.add(panelSearch2);

		panelMain.setLayout(new BorderLayout(5, 5));
		panelMain.add(panelSearch, BorderLayout.NORTH);
		JPanel panel1 = new JPanel(new GridLayout(1, 2, 5, 5));
		panel1.setOpaque(flag);
		panel1.add(jPanel3);
		panel1.add(jPanel2);
		panelMain.add(panel1, BorderLayout.CENTER);

		panelMain.setOpaque(false);
		panelN = new JPanel(new GridLayout(2, 1, 5, 5));
		panelN.add(panelNotes);
		panelN.add(panelColor);
		panelMain.add(panelN, BorderLayout.SOUTH);

		if (Toolkit.getDefaultToolkit().getScreenSize().width <= 1024) {
			panelNotes.setSize(10, 10);
			panelNotes.setPreferredSize(new Dimension(10, 10));
			panelColor.setSize(10, 10);
			panelColor.setPreferredSize(new Dimension(10, 10));
		} else {
			panelNotes.setSize(20, 70);
			panelNotes.setPreferredSize(new Dimension(20, 70));
			panelColor.setSize(20, 70);
			panelColor.setPreferredSize(new Dimension(20, 70));
		}

		jPanel8.setOpaque(false);
		jPanel2.setOpaque(true);
		jPanel2.setBackground(Color.blue);

		jPanel3.setOpaque(true);
		jPanel3.setBackground(Color.blue);

		btnPaymen.setFont(new java.awt.Font("Tahoma", 1, 14));
		btnPaymen.setText("Thanh toán"); // NOI18N
		btnPaymen.setName("btnPaymen");
		btnPaymen.addMouseListener(new MouseEventClickButtonDown(null, null, null));
		btnPaymen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnPaymen.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnPaymen.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					payment();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		btnPrint.setFont(new java.awt.Font("Tahoma", 1, 14));
		btnPrint.setText("In hóa đơn"); // NOI18N
		btnPrint.setName("btnPrint");
		btnPrint.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnPrint.addMouseListener(new MouseEventClickButtonDown(null, null, null));
		btnPrint.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnPrint.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				printInvoice();
			}
		});

		btnPromotions.setFont(new java.awt.Font("Tahoma", 1, 11));
		btnPromotions.setText("Xem KM"); // NOI18N
		btnPromotions.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnPromotions.addMouseListener(new MouseEventClickButtonDown(null, null, null));
		btnPromotions.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				viewListPromotion();
			}

		});

		btnPayOnce.setFont(new java.awt.Font("Tahoma", 1, 11));
		btnPayOnce.setText("Thanh toán lẻ"); // NOI18N
		btnPayOnce.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnPayOnce.addMouseListener(new MouseEventClickButtonDown(null, null, null));
		btnPayOnce.setName("btnPayOnce");
		btnPayOnce.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				paymentSlip();
			}
		});

		btnThemSP.setFont(new java.awt.Font("Tahoma", 1, 11));
		btnThemSP.setText("Lưu"); // NOI18N
		btnThemSP.setName("btnThemSP");
		btnThemSP.addMouseListener(new MouseEventClickButtonDown(null, null, null));
		btnThemSP.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnThemSP.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				buttonAddProductActionPerformed(evt);
			}
		});

		btnInCheBien.setFont(new java.awt.Font("Tahoma", 1, 11));
		btnInCheBien.setText("In chế biến"); // NOI18N

		btnInCheBien.addMouseListener(new MouseEventClickButtonDialogPlus(null, null, null));
		btnInCheBien.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnInCheBien.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				printKitchent();
			}
		});

		btnInHuyMon.setFont(new java.awt.Font("Tahoma", 1, 11));
		btnInHuyMon.setText("In hủy món"); // NOI18N
		btnInHuyMon.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnInHuyMon.addMouseListener(new MouseEventClickButtonDialogPlus(null, null, null));
		btnInHuyMon.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				printCanceProduct();
			}
		});

		btnTraSau.setFont(new java.awt.Font("Tahoma", 1, 11));
		btnTraSau.setText("Trả sau"); // NOI18N
		btnTraSau.setName("btnTraSau");
		btnTraSau.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnTraSau.addMouseListener(new MouseEventClickButtonDown(null, null, null));
		btnTraSau.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				paymentAfter();
			}
		});

		jPanel1.setBackground(new java.awt.Color(255, 255, 255));

		txtGuest.setText("0"); // NOI18N
		txtGuest.setHorizontalAlignment(JTextField.RIGHT);
		txtGuest.setOpaque(false);
		txtGuest.addCaretListener(new javax.swing.event.CaretListener() {
			public void caretUpdate(javax.swing.event.CaretEvent evt) {
				// txtGuestCaretUpdate(evt);
			}
		});

		jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12));
		jLabel2.setText("Số khách"); // NOI18N

		lableEmployee.setFont(new java.awt.Font("Tahoma", 0, 12));
		lableEmployee.setText("Thu ngân"); // NOI18N

		lbNameTable.setFont(new java.awt.Font("Tahoma", 1, 14));
		lbNameTable.setHorizontalTextPosition(JLabel.CENTER);
		lbNameTable.setVerticalTextPosition(JLabel.CENTER);
		lbNameTable.setHorizontalAlignment(JLabel.CENTER);
		lbNameTable.setText(""); // NOI18N

		lablePartner.setFont(new java.awt.Font("Tahoma", 0, 12));
		lablePartner.setText("Khách hàng"); // NOI18N

		btnExit.setFont(new java.awt.Font("Tahoma", 1, 11));
		btnExit.setText("Thoát"); // NOI18N
		btnExit.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnExit.addMouseListener(new MouseEventClickButtonDialogPlus(null, null, null));
		btnExit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				exitSystem();
			}
		});

		jLabel30.setFont(new java.awt.Font("Tahoma", 0, 12));
		jLabel30.setText("Cửa hàng"); // NOI18N

		btnTVPV.setFont(new java.awt.Font("Tahoma", 1, 11));
		btnTVPV.setText("NV phục vụ"); // NOI18N
		btnTVPV.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnTVPV.addMouseListener(new MouseEventClickButtonDialogPlus(null, null, null));
		btnTVPV.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				choiseEmployeeServer();
			}
		});

		lbNode.setFont(new java.awt.Font("Tahoma", 0, 12));
		lbNode.setText("Phòng ban"); // NOI18N

		lableDate.setFont(new java.awt.Font("Tahoma", 0, 12));
		lableDate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		lableDate.setText("dd/MM/yyyy"); // NOI18N
		lableDate.setBorder(null);
		lableDate.setDisabledTextColor(new java.awt.Color(0, 0, 0));
		lableDate.setMargin(new java.awt.Insets(0, 0, 0, 0));
		lableDate.setOpaque(false);

		lableTime.setFont(new java.awt.Font("Tahoma", 0, 12));
		lableTime.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		lableTime.setText("HH:mm"); // NOI18N
		lableTime.setBorder(null);
		lableTime.setDisabledTextColor(new java.awt.Color(0, 0, 0));
		lableTime.setOpaque(false);

		jPanel4.setLayout(new GridLayout(1, 2, 0, 0));
		jPanel4.setOpaque(false);
		jPanel4.add(lableDate);
		jPanel4.add(lableTime);

		txtEmployee.setText("");
		txtTVPV.setText("");
		txtPartner.setText("");
		txtPartner.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				if (evt.getClickCount() >= 2) {
					choiseCustomer();
				}
			}
		});

		JPanel panelExit = new JPanel();
		panelExit.setLayout(new BorderLayout(5, 0));
		panelExit.setOpaque(false);
		btnExit.setPreferredSize(new Dimension(100, 22));
		txtGuest.setOpaque(false);
		panelExit.add(txtGuest, BorderLayout.CENTER);
		panelExit.add(btnExit, BorderLayout.LINE_END);

		jPanel1.setLayout(new GridLayout(3, 1, 0, 5));
		jPanel1.setBackground(Color.WHITE);
		jPanel1.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		JPanel panelTop1 = new JPanel();
		panelTop1.setBackground(Color.WHITE);
		panelTop1.setLayout(new GridLayout(1, 2, 5, 0));
		JPanel panelT1 = new JPanel();
		panelT1.setBackground(Color.WHITE);
		panelT1.setLayout(new BorderLayout());
		lableEmployee.setPreferredSize(new Dimension(80, 22));
		panelT1.add(lableEmployee, BorderLayout.WEST);
		panelT1.add(txtEmployee, BorderLayout.CENTER);
		JPanel panelT11 = new JPanel();
		panelT11.setBackground(Color.WHITE);
		panelT11.setLayout(new BorderLayout());
		lablePartner.setPreferredSize(new Dimension(80, 22));
		panelT11.add(lablePartner, BorderLayout.WEST);
		panelT11.add(txtPartner, BorderLayout.CENTER);

		panelTop1.add(panelT1);
		panelTop1.add(panelT11);
		jPanel1.add(panelTop1);
		JPanel panelTop2 = new JPanel();
		panelTop2.setBackground(Color.WHITE);
		panelTop2.setLayout(new GridLayout(1, 2, 5, 0));
		JPanel panelT2 = new JPanel();
		panelT2.setBackground(Color.WHITE);
		panelT2.setLayout(new BorderLayout());
		JLabel labelT2 = new JLabel("TV Phục vụ");
		labelT2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelT2.setPreferredSize(new Dimension(80, 22));
		panelT2.add(labelT2, BorderLayout.WEST);
		panelT2.add(txtTVPV, BorderLayout.CENTER);
		JPanel panelT22 = new JPanel();
		panelT22.setBackground(Color.WHITE);
		panelT22.setLayout(new BorderLayout());
		JLabel labelT22 = new JLabel("Thời gian");
		labelT22.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelT22.setPreferredSize(new Dimension(80, 22));
		panelT22.add(labelT22, BorderLayout.WEST);
		panelT22.add(jPanel4, BorderLayout.CENTER);
		panelTop2.add(panelT2);
		panelTop2.add(panelT22);
		jPanel1.add(panelTop2);
		JPanel panelTop3 = new JPanel();
		panelTop3.setBackground(Color.WHITE);
		panelTop3.setLayout(new GridLayout(1, 2, 5, 0));
		JPanel panelT3 = new JPanel();
		panelT3.setBackground(Color.WHITE);
		panelT3.setLayout(new BorderLayout());
		lbNameTable.setPreferredSize(new Dimension(80, 22));
		panelT3.add(lbNameTable, BorderLayout.WEST);
		panelT3.add(panelPayment.getTxtInvoiceCode(), BorderLayout.CENTER);
		JPanel panelT33 = new JPanel();
		panelT33.setBackground(Color.WHITE);
		panelT33.setLayout(new BorderLayout());
		jLabel2.setPreferredSize(new Dimension(80, 22));
		panelT33.add(jLabel2, BorderLayout.WEST);
		panelT33.add(panelExit, BorderLayout.CENTER);
		panelTop3.add(panelT3);
		panelTop3.add(panelT33);
		jPanel1.add(panelTop3);

		javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
		jPanel8.setLayout(jPanel8Layout);
		jPanel8Layout.setHorizontalGroup(jPanel8Layout
		    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
		        Short.MAX_VALUE)
		    .addGroup(
		        jPanel8Layout
		            .createSequentialGroup()
		            .addGroup(
		                jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(btnInCheBien, javax.swing.GroupLayout.PREFERRED_SIZE, 75, Short.MAX_VALUE)
		                    .addComponent(btnThemSP, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
		            .addGap(6, 6, 6)
		            .addGroup(
		                jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(btnInHuyMon, javax.swing.GroupLayout.PREFERRED_SIZE, 75, Short.MAX_VALUE)
		                    .addComponent(btnPromotions, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
		            .addGap(6, 6, 6)
		            .addGroup(
		                jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(btnTraSau, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
		                    .addComponent(btnPayOnce, javax.swing.GroupLayout.PREFERRED_SIZE, 75, Short.MAX_VALUE))
		            .addGap(6, 6, 6).addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 75, Short.MAX_VALUE)
		            .addGap(6, 6, 6).addComponent(btnPaymen, javax.swing.GroupLayout.PREFERRED_SIZE, 75, Short.MAX_VALUE))
		    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE,
		        javax.swing.GroupLayout.DEFAULT_SIZE));
		jPanel8Layout.setVerticalGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        jPanel8Layout
		            .createSequentialGroup()
		            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		            .addGroup(
		                jPanel8Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addGroup(
		                        javax.swing.GroupLayout.Alignment.TRAILING,
		                        jPanel8Layout
		                            .createSequentialGroup()
		                            .addGroup(
		                                jPanel8Layout
		                                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                                    .addComponent(btnInCheBien, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
		                                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                                    .addComponent(btnInHuyMon, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
		                                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                                    .addComponent(btnTraSau, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
		                                        javax.swing.GroupLayout.PREFERRED_SIZE))
		                            .addGap(4, 4, 4)
		                            .addGroup(
		                                jPanel8Layout
		                                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                                    .addComponent(btnThemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
		                                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                                    .addComponent(btnPromotions, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
		                                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                                    .addComponent(btnPayOnce, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
		                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
		                    .addGroup(
		                    // javax.swing.GroupLayout.Alignment.TRAILING,
		                        jPanel8Layout
		                            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                            .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 54,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)
		                            .addComponent(btnPaymen, javax.swing.GroupLayout.PREFERRED_SIZE, 54,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)))));

		javax.swing.GroupLayout panelBackground1Layout = new javax.swing.GroupLayout(panelBackground1);
		panelBackground1.setLayout(panelBackground1Layout);
		panelBackground1Layout.setHorizontalGroup(panelBackground1Layout.createParallelGroup(
		    javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		    panelBackground1Layout
		        .createSequentialGroup()
		        .addGap(16, 16, 16)
		        .addComponent(panelMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
		            Short.MAX_VALUE)
		        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		        .addGroup(
		            panelBackground1Layout
		                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
		                .addComponent(button_C, javax.swing.GroupLayout.PREFERRED_SIZE, 55,
		                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addComponent(lbOpenALl, javax.swing.GroupLayout.PREFERRED_SIZE, 16,
		                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addComponent(lbClossAll, javax.swing.GroupLayout.PREFERRED_SIZE, 16,
		                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addGroup(
		                    panelBackground1Layout
		                        .createSequentialGroup()
		                        .addGap(1, 1, 1)
		                        .addComponent(lbMuaHo, javax.swing.GroupLayout.PREFERRED_SIZE, 58,
		                            javax.swing.GroupLayout.PREFERRED_SIZE))
		                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
		                    Short.MAX_VALUE))
		        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
		            javax.swing.GroupLayout.DEFAULT_SIZE).addContainerGap()));
		panelBackground1Layout.setVerticalGroup(panelBackground1Layout.createParallelGroup(
		    javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		    panelBackground1Layout
		        .createSequentialGroup()
		        .addGroup(
		            panelBackground1Layout
		                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
		                .addGroup(
		                    panelBackground1Layout
		                        .createSequentialGroup()
		                        .addGap(11, 11, 11)
		                        .addComponent(panelMain, javax.swing.GroupLayout.DEFAULT_SIZE,
		                            javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		                .addGroup(
		                    javax.swing.GroupLayout.Alignment.LEADING,
		                    panelBackground1Layout
		                        .createSequentialGroup()
		                        .addContainerGap()
		                        .addComponent(button_C, javax.swing.GroupLayout.PREFERRED_SIZE, 32,
		                            javax.swing.GroupLayout.PREFERRED_SIZE)
		                        .addGap(9, 9, 9)
		                        .addComponent(lbOpenALl)
		                        .addGap(2, 2, 2)
		                        .addComponent(lbClossAll)
		                        .addGap(38, 38, 38)
		                        .addComponent(lbMuaHo, javax.swing.GroupLayout.PREFERRED_SIZE, 22,
		                            javax.swing.GroupLayout.PREFERRED_SIZE)
		                        .addGap(6, 6, 6)
		                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE,
		                            javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		                .addGroup(
		                    panelBackground1Layout
		                        .createSequentialGroup()
		                        .addGap(21, 21, 21)
		                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE,
		                            javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE)))
		        .addGap(14, 14, 14)));

		this.setLayout(new BorderLayout());
		this.add(panelBackground1, BorderLayout.CENTER);
		panelTextKeyboard = new PanelTextKeyboard();
		this.add(panelTextKeyboard, BorderLayout.SOUTH);
	}

	protected void discountProduct() {
		PanelDiscountProduct panel = new PanelDiscountProduct(panelPayment.getSelectedInvoiceItem());
		DialogResto dialog = new DialogResto(panel, false, 0, 170);
		dialog.setTitle("Chiết khấu sản phẩm");
		dialog.setLocationRelativeTo(null);
		dialog.setModal(true);
		dialog.setVisible(true);
		tableModel.updateItem(panel.getInvoiceItem());
	}

	protected void btnLichTrinhActionPerformed(ActionEvent evt) {
		// System.out.println(tableModel.getInvoiceDetail());
		// if(tableModel.getInvoiceDetail()==null)
		// {
		// System.out.println("abc" + tableModel.getInvoiceDetail());
		// JOptionPane.showMessageDialog(null, "Bạn chưa chọn bàn");
		// }
		// else
		// {
		// System.out.println(tableModel.getInvoiceDetail() + "abc1");
		if (tableModel.getInvoiceDetail().getCustomerCode() == null
		    || tableModel.getInvoiceDetail().getCustomerCode().trim().isEmpty()) {
			PanelListPartnerOrganizationWork panel = new PanelListPartnerOrganizationWork();
			panel.setName("PaymentbyPointWork");
			DialogResto dialog = new DialogResto(panel, false, 1000, 350);
			dialog.setName("dlPaymentbyPoint");
			dialog.setTitle("DS đối tác DN");
			dialog.setModal(true);
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
			Customer customer = panel.getCustomer();
			if (customer != null) { // Trường hợp khách hàng được chọn
				tableModel.getInvoiceDetail().setCustomerCode(customer.getCode());
				txtPartner.setItem(customer);
				try {
					PanelToDoWork panelToDoWork = new PanelToDoWork(tableModel.getInvoiceDetail());
					panelToDoWork.setName("lichtrinh");
					DialogMain dialogMain = new DialogMain(panelToDoWork, true);
					dialogMain.showButton(true);
					dialogMain.setName("dlLichTrinh");
					dialogMain.setTitle("Lịch trình");
					dialogMain.setModal(true);
					dialogMain.setLocationRelativeTo(null);
					dialogMain.setVisible(true);
				} catch (Exception e) {
				}
			}

		} else {
			try {
				PanelToDoWork panelToDoWork = new PanelToDoWork(tableModel.getInvoiceDetail());
				panelToDoWork.setName("lichtrinh");
				DialogMain dialogMain = new DialogMain(panelToDoWork, true);
				dialogMain.showButton(true);
				dialogMain.setName("dlLichTrinh");
				dialogMain.setTitle("Lịch trình");
				dialogMain.setModal(true);
				dialogMain.setLocationRelativeTo(null);
				dialogMain.setVisible(true);
			} catch (Exception e2) {
			}
		}
		// }
	}

	// Thiết lập ẩn hiện dùng trả góp
	protected void chbTraGopItemStateChanged(ItemEvent evt) {

		try {
			// account =
			// AccountModelManager.getInstance().getAccountByLoginId(ManagerAuthenticate.getInstance().getLoginId());
			Profile profile = AccountModelManager.getInstance().getProfileConfigAdmin();
			if (chbTraGop.isSelected()) {
				profile.put(TraGop, true);
				btnTraGop.setVisible(true);
			} else {
				profile.put(TraGop, false);
				btnTraGop.setVisible(false);
			}
			// AccountModelManager.getInstance().saveAccount(account);
			AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(),
			    profile);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Thiết lập ẩn hiện dùng lịch trình
	protected void chbLichTrinhItemStateChanged(ItemEvent evt) {

		try {
			// account =
			// AccountModelManager.getInstance().getAccountByLoginId(ManagerAuthenticate.getInstance().getLoginId());
			Profile profile = AccountModelManager.getInstance().getProfileConfigAdmin();
			if (chbLichTrinh.isSelected()) {
				profile.put(LichTrinh, true);
				btnLichTrinh.setVisible(true);
			} else {
				profile.put(LichTrinh, false);
				btnLichTrinh.setVisible(false);
			}
			// AccountModelManager.getInstance().saveAccount(account);
			AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(),
			    profile);
		} catch (Exception e) {
		}
	}

	// Thêm mới hàng hóa
	protected void buttonAddProductActionPerformed(ActionEvent evt) {
		try {
			saveContributorOther();
			AccountingModelManager.getInstance().saveInvoiceDetail(tableModel.getInvoiceDetail());
			tableModel.setData(new InvoiceDetail(true));
			tableModel.changeCaculate("", "");
			tableEat = null;
			lbNameTable.setText("");
			setEditorGui(true);
			dispose();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	private void saveContributorOther() {
		// Tạo contributor phòng ban nhân viên
		if (tableModel.getInvoiceDetail().getDepartmentCode() == null) {
			if (panelPayment.getDepartmentJComboBox().getSelectedDepartment() != null)
				tableModel.getInvoiceDetail().setDepartmentCode(
				    panelPayment.getDepartmentJComboBox().getSelectedDepartment().getPath());
			else
				tableModel.getInvoiceDetail().setDepartmentCode(HRModelManager.getInstance().getDepartmentOther().getPath());
		}
		
		Customer customer = txtPartner.getItem();
		if(customer!=null){
			try {
				// Tạo contributor nhóm khách hàng
				tableModel.getInvoiceDetail().setCustomerCode(customer.getCode());
				List<AccountMembership> accMemberShips = AccountModelManager.getInstance().findMembershipByAccountLoginId(
				    customer.getLoginId());
				if (accMemberShips != null && accMemberShips.size() > 0) {
					tableModel.getInvoiceDetail().setGroupCustomerCode(accMemberShips.get(0).getGroupPath());
				} else {
					tableModel.getInvoiceDetail().setGroupCustomerCode(
					    CustomerModelManager.getInstance().getGroupCustomerOther().getPath());
				}
      } catch (Exception e) {
      }
	
		}
		

		// Tạo contributor nhóm khách hàng
		if (tableModel.getInvoiceDetail().getGroupCustomerCode() == null) {
			tableModel.getInvoiceDetail().setGroupCustomerCode(
			    CustomerModelManager.getInstance().getGroupCustomerOther().getPath());
		}

		// Tạo contributor cửa hàng
		if (tableModel.getInvoiceDetail().getStoreCode() == null) {
			if (panelPayment.getStoreJComboBox().getSelectedStore() != null) {
				tableModel.getInvoiceDetail().setStoreCode(panelPayment.getStoreJComboBox().getSelectedStore().getCode());
			} else {
				tableModel.getInvoiceDetail().setStoreCode(null);
			}
		}
		if (panelPayment.getTxtProject().getItem() != null) {
			String pathProject = panelPayment.getPathProjectCode(panelPayment.getTxtProject().getItem());
			tableModel.getInvoiceDetail().setProjectCode(pathProject);
		} else {
			String pathProject = panelPayment.getPathProjectCode(RestaurantModelManager.getInstance().getProjectOther());
			tableModel.getInvoiceDetail().setProjectCode(pathProject);
		}

		tableModel.getInvoiceDetail().setDescription(panelPayment.getNote());
		List<Contributor> contributors = tableModel.getInvoiceDetail().getContributors();
		if (contributors == null) {
			contributors = new ArrayList<Contributor>();
		}

		String groupDepartmentOther = HRModelManager.getInstance().getDepartmentOther().getPath();
		// Tạo contributor thành viên phục vụ
		Employee nhanVienPv = (Employee) txtTVPV.getItem();
		if (nhanVienPv != null) {
			boolean flag = false;
			for (Contributor c : contributors) {
				if (c.getIdentifierId().equals(nhanVienPv.getLoginId()) && c.getRole().equals(Contributor.nhanVienPV)) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				Contributor contributorNVPV = new Contributor();
				contributorNVPV.setType(Contributor.Type.User);
				contributorNVPV.setRole(Contributor.nhanVienPV);
				contributorNVPV.setIdentifierId(nhanVienPv.getLoginId());
				try {
					List<AccountMembership> accMemberShips = AccountModelManager.getInstance().findMembershipByAccountLoginId(
					    nhanVienPv.getLoginId());
					if (accMemberShips != null && accMemberShips.size() > 0) {
						for (AccountMembership a : accMemberShips) {
							if (a.getGroupPath().indexOf(AccountModelManager.Department) > 0) {
								contributorNVPV.setIdentifierValue(a.getGroupPath());
							}
						}

					} else {
						contributorNVPV.setIdentifierValue(groupDepartmentOther);
					}
				} catch (Exception e) {
					e.printStackTrace();
					contributorNVPV.setIdentifierValue(groupDepartmentOther);
				}
				contributors.add(contributorNVPV);
			}
		}

		List<Employee> employees = panelPayment.getWaiters();
		if (employees != null && employees.size() > 0) {
			boolean flag;
			for (Employee e : employees) {
				flag = false;
				for (Contributor c : contributors) {
					if (c.getIdentifierId().equals(e.getLoginId()) && c.getRole().equals(Contributor.nhanVienPV)) {
						try {
							c.setPercent(Integer.parseInt(e.getModifiedBy()));
						} catch (Exception ex) {
							c.setPercent(100);
						}
						flag = true;
						break;
					}
				}
				if (!flag) {
					Contributor contributorNVPV = new Contributor();
					contributorNVPV.setType(Contributor.Type.User);
					contributorNVPV.setRole(Contributor.nhanVienPV);
					contributorNVPV.setIdentifierId(e.getLoginId());
					try {
						List<AccountMembership> accMemberShips = AccountModelManager.getInstance().findMembershipByAccountLoginId(
						    e.getLoginId());
						if (accMemberShips != null && accMemberShips.size() > 0) {
							contributorNVPV.setIdentifierValue(accMemberShips.get(0).getGroupPath());
						} else {
							contributorNVPV.setIdentifierValue(groupDepartmentOther);
						}
					} catch (Exception e1) {
						e1.printStackTrace();
						contributorNVPV.setIdentifierValue(groupDepartmentOther);
					}
					try {
						contributorNVPV.setPercent(Integer.parseInt(e.getModifiedBy()));
					} catch (Exception ex) {
						contributorNVPV.setPercent(100);
					}
					contributors.add(contributorNVPV);
				}
			}
		}

		// Tạo contributor nhân viên
		if (txtEmployee.getItem() != null) {
			Employee nhanVien = (Employee) txtEmployee.getItem();
			tableModel.getInvoiceDetail().setEmployeeCode(nhanVien.getCode());
			boolean flag = false;
			for (Contributor c : contributors) {
				if (c.getIdentifierId().equals(nhanVien.getLoginId()) && c.getRole().equals(Contributor.thuNgan)) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				Contributor contributorNV = new Contributor();
				contributorNV.setType(Contributor.Type.User);
				contributorNV.setRole(Contributor.thuNgan);
				contributorNV.setIdentifierId(nhanVien.getLoginId());
				try {
					List<AccountMembership> accMemberShips = AccountModelManager.getInstance().findMembershipByAccountLoginId(
					    nhanVien.getLoginId());
					if (accMemberShips != null && accMemberShips.size() > 0) {
						contributorNV.setIdentifierValue(accMemberShips.get(0).getGroupPath());
					} else {
						contributorNV.setIdentifierValue(groupDepartmentOther);
					}
				} catch (Exception e) {
					e.printStackTrace();
					contributorNV.setIdentifierValue(groupDepartmentOther);
				}
				contributors.add(contributorNV);
			}

		}

		tableModel.getInvoiceDetail().setContributors(contributors);
	}

	// thanh toán toàn bộ hóa đơn
	protected void payment() throws Exception {
		PanelTextMoneyPayment panel = new PanelTextMoneyPayment();
		panel.setName("panelPayment");
		panel.initEvent(
		    getMoney(tableModel.getInvoiceDetail().getFinalCharge() - tableModel.getInvoiceDetail().getTotalPaid())
		        .toString(), currencyCode);
		DialogResto dialog = new DialogResto(panel, false, 400, 350);
		dialog.setName("dlPayment");
		dialog.setTitle("Thanh toán");
		dialog.setLocationRelativeTo(null);
		dialog.setModal(true);
		dialog.setVisible(true);
		if (!panel.isFlagPayment()) {
			return;
		}
		// long endTime = System.currentTimeMillis();
		if (!updateTime()) {
			lableDate.setForeground(Color.red);
			lableTime.setForeground(Color.red);
			return;
		} else {
			lableDate.setForeground(Color.black);
			lableTime.setForeground(Color.black);
		}
		saveContributorOther();
		InvoiceDetail invoice = tableModel.getInvoiceDetail();
		invoice.setCode(AccountingModelManager.pay);

		// invoice.setStartDate(new Date());
		// invoice.setEndDate(new Date());
		// if (invoice.getFinalCharge() != invoice.getTotalPaid()) {
		InvoiceTransaction transaction = new InvoiceTransaction();
		transaction.setTransactionType(TransactionType.Cash);
		transaction.setCreatedBy(ManagerAuthenticate.getInstance().getLoginId());
		transaction.setCurrencyUnit(currencyCode);
		transaction.setTotal(invoice.getFinalCharge() - invoice.getTotalPaid());
		transaction.setTransactionDate(new Date());
		invoice.add(transaction);

		boolean b = false;
		if (!invoice.getStatus().equals(Status.Paid) && !invoice.getStatus().equals(Status.PostPaid)) {
			b = true;
			if (invoice.getEndDate() == null) {
				invoice.setEndDate(new Date());
			}
			String code = AccountingModelManager.getInstance().getCodeObject(PanelManageCodes.InvoiceBH,
			    AccountingModelManager.typeBanHang, invoice.getStartDate(), true);
			if (code != null) {
				invoice.setInvoiceCode(code);
			} else {
				invoice.setInvoiceCode(panelPayment.getTxtInvoiceCode().getText());
			}
		}

		// }

		// long endTime1 = System.currentTimeMillis();
		// System.out.println("payment111111: "+ new
		// DecimalFormat("#0.00000").format((endTime1 - endTime) / 1000d));
		if (invoice.getTotalPaid() == 0 || !txtTable.isEnabled()) {
			for (InvoiceItem invoiceItem : invoice.getItems()) {
				if (!invoiceItem.getStatus().equals(AccountingModelManager.isCance)
				    && !invoiceItem.getStatus().equals(AccountingModelManager.isForRent)
				    && !invoiceItem.getStatus().equals(AccountingModelManager.isPromotion)) {
					invoiceItem.setStatus(AccountingModelManager.isPayment);
				}
			}
			tableModel.updateStatus(AccountingModelManager.isPayment);
			if (invoice.getTotalPaid() > 0 && b) {
				try {
					printAgain = true;
					if (!printReceipt(true, true, null)) {
						DialogNotice.getInstace().setVisible(true);
					}
					printAgain = false;
				} catch (Exception e) {

				}
			} else {
				try {
					if (!printReceipt(true, true, null)) {
						DialogNotice.getInstace().setVisible(true);
					}
				} catch (Exception e) {

				}
			}

		} else {
			List<InvoiceItem> invoiceItems = new ArrayList<InvoiceItem>();
			boolean a = false;
			for (InvoiceItem invoiceItem : invoice.getItems()) {
				if (!invoiceItem.getStatus().equals(AccountingModelManager.isPayment)) {
					invoiceItems.add(invoiceItem);
				} else {
					a = true;
				}
			}
			if (a) {
				if (!printReceipt(true, false, getInvoiceDetailPayment(invoiceItems))) {
					DialogNotice.getInstace().setVisible(true);
				}
			} else {
				try {
					printAgain = true;
					if (!printReceipt(true, true, null)) {
						DialogNotice.getInstace().setVisible(true);
					}
					printAgain = false;
				} catch (Exception e) {

				}
			}

			for (InvoiceItem invoiceItem : invoice.getItems()) {
				if (!invoiceItem.getStatus().equals(AccountingModelManager.isCance)
				    && !invoiceItem.getStatus().equals(AccountingModelManager.isForRent)
				    && !invoiceItem.getStatus().equals(AccountingModelManager.isPromotion)) {
					invoiceItem.setStatus(AccountingModelManager.isPayment);
				}
			}
			tableModel.updateStatus(AccountingModelManager.isPayment);

		}

		if (!invoice.getStatus().equals(Status.Paid)) {
			invoice.setStatus(Status.Paid);
			try {
				invoice.setGuest(Integer.parseInt(txtGuest.getText()));
			} catch (Exception e) {
				invoice.setGuest(0);
			}
		}
		// long endTime2 = System.currentTimeMillis();
		// System.out.println("payment2: "+ new
		// DecimalFormat("#0.00000").format((endTime2 - endTime1) / 1000d));
		AccountingModelManager.pay = "Tiền mặt";
		invoice.setTotalPaid(invoice.getFinalCharge());
		invoice = AccountingModelManager.getInstance().saveInvoiceDetail(invoice);
		final long idInvoice = invoice.getId();
		// long endTime3 = System.currentTimeMillis();
		// System.out.println("payment3: "+ new
		// DecimalFormat("#0.00000").format((endTime3 - endTime2) / 1000d));
		final Thread t = new Thread() {

			@Override
			public void run() {
				try {
					// long endTime3 = System.currentTimeMillis();
					// System.out.println(idInvoice);
					String code = DateUtil.asCompactDateTimeId(new Date());
					InvoiceDetail invoice = AccountingModelManager.getInstance().getInvoiceDetail(idInvoice);
					for (int i = 0; i < invoice.getItems().size(); i++) {
						InvoiceItem invoiceItem = invoice.getItems().get(i);
						String productCode = invoiceItem.getProductCode();

						double quantity = invoiceItem.getQuantity() - invoiceItem.getQuantityReal();
						List<IdentityProduct> identityProducts = new ArrayList<IdentityProduct>();
						if (invoice.getWarehouseId() == null) {
							identityProducts = WarehouseModelManager.getInstance().findByProductCodeAndExportType(productCode);
						} else {
							identityProducts = WarehouseModelManager.getInstance().findByProductCodeAndExportTypeAndWarehoseCode(
							    productCode, invoice.getWarehouseId());
						}
						int count = 0;
						if (quantity > identityProducts.size()) {
							if (AccountModelManager.getInstance().getProfileConfigAdmin().get(PanelChooseProductExport.EXPORT) != null
							    && AccountModelManager.getInstance().getProfileConfigAdmin().get(PanelChooseProductExport.EXPORT)
							        .equals("true")) {

							} else {
								PanelChooseProductExport panel1 = new PanelChooseProductExport(invoiceItem.getItemName());
								panel1.setName("Kho");
								DialogResto dialog1 = new DialogResto(panel1, false, 0, 120);
								dialog1.setName("dlKho");
								dialog1.setTitle("Kho");
								dialog1.setLocationRelativeTo(null);
								dialog1.setModal(true);
								dialog1.setVisible(true);
								if (panel1.isDelete()) {
									tableModel.deleteAllIdentityProductExport(invoice);
									for (InvoiceItem bean : invoice.getItems()) {
										bean.setQuantityReal(0);
									}
								}
							}

						} else {
							if (quantity > 0) {
								for (IdentityProduct identityProduct : identityProducts) {
									identityProduct.setExportDate(invoiceItem.getStartDate());
									identityProduct.setShipmentExportCode(code);
									identityProduct.setExportType(ExportType.Export);
									identityProduct.setInvoiceExportCode(invoice.getInvoiceCode());
									identityProduct.setInvoiceItemIdExport(invoiceItem.getId());
									identityProduct.setCurrencyExportRate(invoiceItem.getCurrencyRate());
									identityProduct.setPriceExport(invoiceItem.getUnitPrice());
									WarehouseModelManager.getInstance().saveIdentityProduct(identityProduct);
									double quantityUpdate = invoiceItem.getQuantityReal() + 1;
									invoiceItem.setQuantityReal(quantityUpdate);
									count++;
									if (count == quantity) {
										break;
									}
								}
							} else {
								if (quantity < 0) {
									identityProducts = WarehouseModelManager.getInstance().getByInvoiceItemIdExport(invoiceItem.getId());
									for (IdentityProduct identityProduct : identityProducts) {
										WarehouseModelManager.getInstance().deleteIdentityProductExport(identityProduct);
										double quantityUpdate = invoiceItem.getQuantityReal() - 1;
										invoiceItem.setQuantityReal(quantityUpdate);
										count--;
										if (count == quantity) {
											break;
										}
									}
								}
							}
						}
					}
					AccountingModelManager.getInstance().saveInvoice(invoice);
					// long endTime4 = System.currentTimeMillis();
					// System.out.println("long ka ka: "+ new
					// DecimalFormat("#0.00000").format((endTime4 - endTime3) / 1000d));
				} catch (Exception e) {
					System.out.println("bb");
				}
			}
		};
		t.start();
		// long endTime4 = System.currentTimeMillis();
		// System.out.println("payment4: "+ new
		// DecimalFormat("#0.00000").format((endTime4 - endTime3) / 1000d));

		if (tableEat != null && tableEat.getTable() != null) {
			tableEat.setStatus(Table.Status.TableFree);
			resetGrossTable();

		}

		if (!txtTable.isEnabled()) {
			if (tableEat != null
			    && tableEat.getTable() != null
			    && tableEat.getTable().getCode()
			        .equals(RestaurantModelManager.getInstance().getTablePaymentAfter().getCode())
			    && tableModel.getInvoiceDetail().getProjectCode() != null
			    && tableModel.getInvoiceDetail().getProjectCode().indexOf("other") < 0) {
				reset();
				TableEat tableEat = new TableEat(RestaurantModelManager.getInstance().getTablePaymentAfter());
				setTable(tableEat);

			} else {
				reset();
				setEditorGui(true);
				dispose();
			}

		} else {
			reset();
		}

	}

	private InvoiceDetail getInvoiceDetailPayment(List<InvoiceItem> invoiceItems) {
		InvoiceDetail invoiceDetail2 = new InvoiceDetail(true);
		for (InvoiceItem invoiceItem : invoiceItems) {
			invoiceDetail2.add(invoiceItem);
		}
		invoiceDetail2.calculate(new DefaultInvoiceCalculator());
		return invoiceDetail2;
	}

	private void resetGrossTable() {
		try {
			if (tableEat.getTable().getReservations() != null && tableEat.getTable().getReservations().size() > 0) {

				for (int i = 0; i < tableEat.getTable().getReservations().size(); i++) {
					try {
						Table table = RestaurantModelManager.getInstance().getTableByCode(
						    tableEat.getTable().getReservations().get(i).getDescription());
						table.setStatus(Table.Status.TableFree);
						table.setInvoiceCode(null);
						table = RestaurantModelManager.getInstance().saveTable(table);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				List<Reservation> li = new ArrayList<Reservation>();
				tableEat.getTable().setReservations(li);
				RestaurantModelManager.getInstance().saveTable(tableEat.getTable());
				panelTable.refeshGui();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Trả sau hóa đơn
	protected void paymentAfter() {
		PanelTextMoneyPayment.setSotienKD("0");
		PanelTextMoneyPayment.setSotienTK("0");
		
		final Date date = tableModel.getInvoiceDetail().getStartDate();
		try {
			for (int i = 0; i < tableModel.getInvoiceDetail().getItems().size(); i++) {
				InvoiceItem sel = tableModel.getInvoiceDetail().getItems().get(i);
				sel.setWarehouseCode(tableModel.getInvoiceDetail().getWarehouseId());
				sel.setCreatedTime(tableModel.getInvoiceDetail().getStartDate());
				sel.setStartDate(tableModel.getInvoiceDetail().getStartDate());
				sel.setCurrencyRate(tableModel.getInvoiceDetail().getCurrencyRate());
				sel.setInvoiceCode(tableModel.getInvoiceDetail().getInvoiceCode());
				sel.setTypeReport(InvoiceItem.Report);
				sel.setType(tableModel.getInvoiceDetail().getType());
			}
			tableModel.caculator();
			try {
				Currency c = LocaleModelManager.getInstance()
				    .getCurrencyByCode(tableModel.getInvoiceDetail().getCurrencyUnit());
				tableModel.getInvoiceDetail().setCurrencyRate(c.getRate());
			} catch (Exception e) {
				tableModel.getInvoiceDetail().setCurrencyRate(1);
			}
			saveContributorOther();
			if (!tableModel.getInvoiceDetail().getStatus().equals(Status.Paid)
			    && !tableModel.getInvoiceDetail().getStatus().equals(Status.PostPaid)) {
				if (tableModel.getInvoiceDetail().getEndDate() == null) {
					tableModel.getInvoiceDetail().setEndDate(new Date());
				}
				String code = AccountingModelManager.getInstance().getCodeObject(PanelManageCodes.InvoiceBH,
				    AccountingModelManager.typeBanHang, date, true);
				if (code != null) {
					tableModel.getInvoiceDetail().setInvoiceCode(code);
				} else {
					tableModel.getInvoiceDetail().setInvoiceCode(panelPayment.getTxtInvoiceCode().getText());
				}
			}
			tableModel.getInvoiceDetail().setStatus(Status.PostPaid);
			AccountingModelManager.getInstance().saveInvoiceDetail(tableModel.getInvoiceDetail());
		} catch (Exception e) {
		}
		PanelChoise pnPanel = new PanelChoise("Bạn có muốn in hóa đơn?");
		pnPanel.setName("Xoa");
		DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
		dialog1.setName("dlXoa");
		dialog1.setTitle("In hóa đơn");
		dialog1.setLocationRelativeTo(null);
		dialog1.getBtnExit().setText("Không");
		dialog1.setModal(true);
		dialog1.setVisible(true);
		if (pnPanel.isDelete()) {
			printAgain = true;
			if (!printReceipt(true, false, null)) {
				DialogNotice.getInstace().setVisible(true);
			}
			printAgain = false;
		}
		if (tableEat != null && tableEat.getTable() != null) {
			tableEat.setStatus(Table.Status.TableFree);
			try {
				resetGrossTable();
			} catch (Exception e) {
			}

		}
		tableModel.setData(new InvoiceDetail(true));
		tableModel.changeCaculate("", "");
		tableEat = null;
		lbNameTable.setText("");

		if (!txtTable.isEnabled()) {
			setEditorGui(true);
			dispose();

		}
	}

	// In những món bị hủy
	protected void printCanceProduct() {
		String loginId = ManagerAuthenticate.getInstance().getOrganizationLoginId();
		String enterpriseName = AccountModelManager.getInstance().getNameByLoginId(loginId);
		String adres = AccountModelManager.getInstance().getAddressByLoginId(loginId);
		String fone = AccountModelManager.getInstance().getPhoneByLoginId(loginId);
		String fax = AccountModelManager.getInstance().getFaxByLoginId(loginId);
		String location = "";
		String table = lbNameTable.getText();
		if (tableEat != null) {
			location = tableEat.getTable().getLocationCode();
			table = tableEat.getTable().getName();
		}
		String[] str = { enterpriseName, adres, fone, fax, "Phiếu Hủy Món", location, table, lableTime.getText(),
		    txtEmployee.getText(), lableDate.getText(), dfTime.format(new Date()) };
		try {
			ReportPrint.getInstance().printHuyMon(tableModel, str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// In chế biến
	protected void printKitchent() {
		String loginId = ManagerAuthenticate.getInstance().getOrganizationLoginId();
		String enterpriseName = AccountModelManager.getInstance().getNameByLoginId(loginId);
		String adres = AccountModelManager.getInstance().getAddressByLoginId(loginId);
		String fone = AccountModelManager.getInstance().getPhoneByLoginId(loginId);
		String fax = AccountModelManager.getInstance().getFaxByLoginId(loginId);
		String location = "";
		String table = lbNameTable.getText();
		if (tableEat != null) {
			location = RestaurantModelManager.getInstance()
			    .getLocationByCode(tableModel.getInvoiceDetail().getLocationCode()).toString();
			table = tableEat.getTable().getName();
		}
		String[] str = { enterpriseName, adres, fone, fax, "Phiếu Chế Biến", location, table, lableTime.getText(),
		    txtEmployee.getText(), lableDate.getText(), dfTime.format(new Date()) };
		try {
			ReportPrint.getInstance().printCheBien(tableModel, str);
			tableModel.updateStatus(AccountingModelManager.isKitchen);
			if (tableEat != null) {
				tableEat.setStatus(com.hkt.module.restaurant.entity.Table.Status.TableKitchen);
				tableEat.setColorTable();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Thiết lập ẩn hiện dùng trả trước
	protected void chbCreditItemStateChanged(ItemEvent evt) {

		try {
			Profile profile = AccountModelManager.getInstance().getProfileConfigAdmin();
			if (chbCredit.isSelected()) {
				profile.put(ManagerCredit, true);
			} else {
				profile.put(ManagerCredit, false);
			}
			AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(),
			    profile);
			panelPayment.refeshGui();
		} catch (Exception e) {
		}
	}

	// Thiết lập ẩn hiện dùng điểm
	protected void chbPointItemStateChanged(ItemEvent evt) {

		try {
			Profile profile = AccountModelManager.getInstance().getProfileConfigAdmin();
			if (chbPoint.isSelected()) {
				profile.put(ManagerPoint, true);
			} else {
				profile.put(ManagerPoint, false);
			}
			AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(),
			    profile);
			panelPayment.refeshGui();
		} catch (Exception e) {
		}
	}

	// Sét ẩn hiện khuyến mãi
	protected void lblKMMouseClicked(MouseEvent evt) {
		if (flag) {
			lblKM.setText("Ẩn KM");
			flag = !flag;
			panelPayment.getScrPomostion().setVisible(true);
		} else {
			lblKM.setText("Hiện KM");
			flag = !flag;
			panelPayment.getScrPomostion().setVisible(false);
		}

	}

	public JLabel getLbKM() {
		return lblKM;
	}

	public void showDiscoutProduct(boolean a) {
		btnDiscountProduct.setVisible(a);
		lbDiscount.setText("Hiện CK");
		lbDiscount.setVisible(a);
	}

	// Sét ẩn hiện ghi chú
	private void viewKH() {
		if (flag1) {
			panelN.setVisible(true);
			flag1 = !flag1;
		} else {
			panelN.setVisible(false);
			flag1 = !flag1;
		}
	}

	// Reset
	public void reset() {
		if (tableEat != null) {
			tableEat.setColorTable();
		}
		tableModel.setData(new InvoiceDetail(true));
		tableModel.changeCaculate("", "");
		tableEat = null;
		lbNameTable.setText("");
		txtPartner.setText("");
		txtPartner.setObject(null);
		txtPartner.setItem(null);
		txtEmployee.setText("");
		txtEmployee.setObject(null);
		txtEmployee.setItem(null);
		txtTVPV.setText("");
		txtTVPV.setObject(null);
		txtTVPV.setItem(null);
		panelPayment.getTxtInvoiceCode().setText("");
		panelPayment.isResetGUIWaiters(true);
	}

	// Mở toàn bộ sản phẩm
	protected void lbOpenALlMouseClicked(MouseEvent evt) {
		panelProduct.opendAll(true);

	}

	// Xóa toàn bộ bàn, xóa hóa đơn
	protected void button_CActionPerformed(ActionEvent evt) {

		if (tableModel.getInvoiceDetail().getId() != null) {
			PanelChoise pnPanel = new PanelChoise("Xóa hết toàn bộ sản phẩm trong bàn?");
			pnPanel.setName("Xoa");
			DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
			dialog1.setName("dlXoa");
			dialog1.setTitle("Xóa sản phẩm");
			dialog1.setLocationRelativeTo(null);
			dialog1.setModal(true);
			dialog1.setVisible(true);
			if (pnPanel.isDelete()) {
				try {
					InvoiceDetail invoiceDetail = tableModel.getInvoiceDetail();
					invoiceDetail.setRecycleBin(true);
					AccountingModelManager.getInstance().deleteInvoice(invoiceDetail);
					if (tableEat != null) {
						tableEat.setStatus(com.hkt.module.restaurant.entity.Table.Status.TableFree);
						resetGrossTable();
					}
					if (!txtTable.isEnabled()) {
						reset();
						setEditorGui(true);
						dispose();
					} else {
						reset();
					}
				} catch (Exception e) {
				}
			}
		} else {
			if (tableEat != null) {
				tableEat.setColorTable();
			}
		}

	}

	private javax.swing.JButton btnSoLuong;
	private javax.swing.JButton btnTraSau;
	private javax.swing.JButton btnCancel;
	private javax.swing.JButton btnChangePrice;
	private javax.swing.JButton btnDatGio;
	private javax.swing.JButton btnGioHoaDon;
	private javax.swing.JButton btnDelete;
	private TextPopup<Employee> txtEmployee;
	private TextPopup<Employee> txtTVPV;
	private javax.swing.JButton btnLichTrinh;
	private TextPopup<Customer> txtPartner;
	private javax.swing.JButton btnPayOnce;
	private javax.swing.JButton btnPrint;
	private javax.swing.JButton btnInCheBien;
	private javax.swing.JButton btnPromotions;
	private javax.swing.JButton btnInHuyMon;
	private javax.swing.JButton btnTraGop;
	private javax.swing.JButton btnDiscountProduct;
	private javax.swing.JButton btnThemSP;
	private javax.swing.JButton btnPaymen;
	private javax.swing.JButton button_C;
	private javax.swing.JButton btnExit;
	private javax.swing.JButton btnTVPV;
	private javax.swing.JButton btnLamTron;
	private javax.swing.JCheckBox chbLichTrinh;
	private javax.swing.JCheckBox chbPoint;
	private javax.swing.JCheckBox chbTraGop;
	private javax.swing.JCheckBox chbCredit;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JLabel jLabel11;
	private javax.swing.JLabel jLabel17;
	private javax.swing.JLabel jLabel18;
	private javax.swing.JLabel jLabel19;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel20;
	private javax.swing.JLabel jLabel21;
	private javax.swing.JLabel jLabel22;
	private javax.swing.JLabel jLabel23;
	private javax.swing.JLabel jLabel24;
	private javax.swing.JLabel jLabel25;
	private javax.swing.JLabel jLabel26;
	private javax.swing.JLabel jLabel27;
	private javax.swing.JLabel jLabel28;
	private javax.swing.JLabel jLabel29;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel30;
	private javax.swing.JLabel jLabel31;
	private javax.swing.JLabel jLabel32;
	private javax.swing.JLabel jLabel33;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JPanel panelMain;
	private javax.swing.JPanel jPanel8;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JTextField lableDate;
	private javax.swing.JLabel lableEmployee;
	private javax.swing.JLabel lablePartner;
	private javax.swing.JTextField lableTime;
	private javax.swing.JLabel lbClossAll;
	private javax.swing.JLabel lbMuaHo;
	private javax.swing.JLabel lbKH;
	private javax.swing.JLabel lbNameTable;
	private javax.swing.JLabel lbNode;
	private javax.swing.JLabel lbOpenALl;
	private javax.swing.JLabel lblKM;
	private PanelBackground panelBackground1;
	private javax.swing.JPanel panelColor;
	private javax.swing.JPanel panelNotes;
	private javax.swing.JTextField txtGuest;
	private javax.swing.JTextField txtSoluong;
	private TextPopup<Table> txtTable;
	private TextPopup<Product> txtTimKiem;

	// Lưu trạng thái hóa đơn khi thanh toán
	private void saveStatusInvoice() {
		if (this.tableEat != null && this.tableEat.getTable() != null) {
			
			if (this.tableEat.getStatus() != com.hkt.module.restaurant.entity.Table.Status.TableFree
			    && tableModel.getRowCount() > 0) {
				try {
					try {
						tableModel.getInvoiceDetail().setGuest(Integer.parseInt(txtGuest.getText()));
					} catch (Exception e) {
						tableModel.getInvoiceDetail().setGuest(0);
					}
					saveContributorOther();
					String code = AccountingModelManager.getInstance().getCodeObject(PanelManageCodes.InvoiceBH,
					    AccountingModelManager.typeBanHang, new Date(), false);
					if (tableModel.getInvoiceDetail().getEndDate() == null && code == null) {
						tableModel.getInvoiceDetail().setInvoiceCode(panelPayment.getTxtInvoiceCode().getText());
					}
					AccountingModelManager.getInstance().saveInvoice(tableModel.getInvoiceDetail());
					tableEat.getTable().setInvoiceCode(String.valueOf(tableModel.getInvoiceDetail().getId()));
					try {
						RestaurantModelManager.getInstance().saveTable(tableEat.getTable());
					} catch (Exception e) {
					}
					tableModel.getTablePromotion().saveAllPromotionProduct();
				} catch (Exception e) {
				}
			} else {
				tableEat.reset();
				if(tableEat.getTable().getInvoiceCode()!=null && !tableEat.getTable().getInvoiceCode().trim().isEmpty()){
					try {
						AccountingModelManager.getInstance().deleteInvoice(tableModel.getInvoiceDetail());
						resetGrossTable();
						tableEat.setStatus(Table.Status.TableFree);
						tableEat.setColorTable();
					} catch (Exception e) {
					}
				}
			
			}
		} else {
			tableModel.setData(new InvoiceDetail(true));
			tableModel.changeCaculate("", "");
			lbNameTable.setText("");
		}
	}

	protected void exitSystem() {
		if (txtTable.isEnabled()) {
			if (tableModel.getInvoiceDetail() != null && tableModel.getInvoiceDetail().getId() != null) {
				updateTime();
				try {
					AccountingModelManager.getInstance().saveInvoice(tableModel.getInvoiceDetail());
				} catch (Exception e) {
				}
			}
			saveStatusInvoice();
			setEditorGui(true);
		} else {
			if (tableModel.getInvoiceDetail().getId() != null
			    && tableModel.getInvoiceDetail().getStatus().equals(Status.WaitingPaid)) {
				try {
					AccountingModelManager.getInstance().saveInvoice(tableModel.getInvoiceDetail());
				} catch (Exception e) {
				}
				try {
					table.setInvoiceCode(String.valueOf(tableModel.getInvoiceDetail().getId()));
					RestaurantModelManager.getInstance().saveTable(table);
				} catch (Exception e) {
				}
			}

			setEditorGui(true);
			reset();
		}
		dispose();

	}

	public void setEditorGui(boolean a) {
		panelTable.canceEdit(a);
		txtTable.setEnabled(a);
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			canceEdit(a);
		} else {
			if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.WRITE) {
				button_C.setEnabled(a);
			}
		}
		if (a) {
			resetGui();
		}
		btnThemSP.setEnabled(!a);
	}

	public void setEditorGuiBN(boolean a) {
		panelTable.canceEdit(a);
		txtTable.setEnabled(a);
		if (a) {
			resetGui();
		}
	}

	private void canceEdit(boolean edit) {
		List<Component> list = getAllComponents(this);
		for (Component c : list) {
			if (!c.equals(btnExit) && !c.equals(btnPaymen) && !c.equals(btnPayOnce) && !c.equals(btnTraSau)
			    && !c.equals(btnPrint) && !(c instanceof JTabbedPane)) {
				c.setEnabled(edit);
			}
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
		return compList;
	}

	// Mở 1 bàn mới
	public void setTable(TableEat tableEat) {
		tableEat.reset();
		txtTimKiem.requestFocus();
		try {
			panelPayment.getTable_Sales1().getCellEditor().stopCellEditing();
		} catch (Exception e) {
		}
		saveStatusInvoice();
		this.tableEat = tableEat;

		panelPayment.setTable(tableEat.getTable());
		lbNameTable.setText(tableEat.toString());
		InvoiceDetail invoiceDetail = AccountingModelManager.getInstance()
		    .getInvoiceDetail(tableEat.getTable().invoiceId());
		if (invoiceDetail == null) {
			try {
				currencyCode = profile.get(DialogConfig.Currency).toString();
				panelPayment.setCurrencyCode(currencyCode);
			} catch (Exception e) {
			}
			invoiceDetail = new InvoiceDetail(true);
			invoiceDetail.setInvoiceCode(DateUtil.asCompactDateTimeId(new Date()));
			String code = AccountingModelManager.getInstance().getCodeObject(PanelManageCodes.InvoiceBH,
			    AccountingModelManager.typeBanHang, new Date(), false);
			if (code != null) {
				panelPayment.getTxtInvoiceCode().setText("");
			} else {
				panelPayment.getTxtInvoiceCode().setText(invoiceDetail.codeView());
			}

			invoiceDetail.setActivityType(Invoice.ActivityType.Receipt);
			invoiceDetail.setStatus(Status.WaitingPaid);
			invoiceDetail.setType(AccountingModelManager.typeBanHang);
			try {
				invoiceDetail.setCurrencyRate(LocaleModelManager.getInstance().getCurrencyByCode(currencyCode).getRate());
			} catch (Exception e) {
				invoiceDetail.setCurrencyRate(1);
			}

			invoiceDetail.setCurrencyUnit(currencyCode);
			invoiceDetail.setStartDate(new Date());
			invoiceDetail.setDiscount(0);
			invoiceDetail.setInvoiceName(tableEat.getTable().getName());
			invoiceDetail.setTableCode(tableEat.getTable().getCode());
			invoiceDetail.setLocationCode(tableEat.getTable().getLocationCode());
			Location location = RestaurantModelManager.getInstance().getLocationByCode(invoiceDetail.getLocationCode());
			if (location == null) {
				invoiceDetail.setWarehouseId(null);
			} else {
				if (location.getLocationAttributes() != null && location.getLocationAttributes().size() > 0) {
					for (LocationAttribute a : location.getLocationAttributes()) {
						if (a.getName().equals("Warehouse")) {
							invoiceDetail.setWarehouseId(a.getValue());
						} else {
							if (a.getName().equals("ProductPriceType")) {
								pricingType = a.getValue();
							}
						}
					}

				}
			}
			try {
				if (!profile.get(DialogConfig.TbArea).equals(true)) {
					pricingType = profile.get(DialogConfig.ProductPriceType).toString();
				}
			} catch (Exception e) {
			}

			String departmentCode = "";
			String storeCode = "";
			String projectCode = RestaurantModelManager.getInstance().getProjectOther().getCode();
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
				departmentCode = (String) obj.get("departmentCode");
				storeCode = (String) obj.get("storeCode");
				projectCode = (String) obj.get("projectCode");
			} catch (Exception ex) {
			}

			if (!departmentCode.equals("")) {
				panelPayment.getDepartmentJComboBox().setSelectDepartmentByCode(departmentCode);
			} else {
				panelPayment.getDepartmentJComboBox().setSelectDepartmentByIndex(0);
			}

			if (!projectCode.equals(RestaurantModelManager.getInstance().getProjectOther().getCode())) {
				Project project = RestaurantModelManager.getInstance().getProjectByCode(projectCode);
				panelPayment.getTxtProject().setItem(project);
			} else {
				panelPayment.getTxtProject().setItem(null);
			}

			if (!storeCode.equals("")) {
				panelPayment.getStoreJComboBox().setSelectStoreByCode(storeCode);
			} else {
				panelPayment.getStoreJComboBox().setSelectedIndex(0);
			}

			panelPayment.isResetGUIWaiters(true);

			tableModel.setData(invoiceDetail);
			tableModel.setInfoInvoice("", pricingType, tableEat.getTable());
			resetTable();
		} else {
			panelPayment.getTxtInvoiceCode().setText(invoiceDetail.codeView());
			Location location = RestaurantModelManager.getInstance().getLocationByCode(invoiceDetail.getLocationCode());
			if (location != null) {
				if (location.getLocationAttributes() != null && location.getLocationAttributes().size() > 0) {
					for (LocationAttribute a : location.getLocationAttributes()) {
						if (a.getName().equals("ProductPriceType")) {
							pricingType = a.getValue();
						}
					}

				}
			}
			try {
				if (!profile.get(DialogConfig.TbArea).equals(true)) {
					pricingType = profile.get(DialogConfig.ProductPriceType).toString();
				}
			} catch (Exception e) {
			}

			currencyCode = invoiceDetail.getCurrencyUnit();
			panelPayment.setCurrencyCode(currencyCode);
			lableDate.setText(dfDate.format(invoiceDetail.getStartDate()));
			lableTime.setText(dfTime.format(invoiceDetail.getStartDate()));
			txtGuest.setText(String.valueOf(invoiceDetail.getGuest()));

			if (invoiceDetail.getDepartmentCode() != null
			    && !invoiceDetail.getDepartmentCode().equals(HRModelManager.getInstance().getDepartmentOther().getPath()))
				panelPayment.getDepartmentJComboBox().setSelectDepartmentByPath(invoiceDetail.getDepartmentCode());
			else
				panelPayment.getDepartmentJComboBox().setSelectedIndex(0);

			String projectOther = RestaurantModelManager.getInstance().getProjectOther().getCode();
			if (invoiceDetail.getProjectCode() != null && !invoiceDetail.getProjectCode().equals(projectOther)) {

				String[] pathProject = invoiceDetail.getProjectCode().split("/");
				Project project = RestaurantModelManager.getInstance().getProjectByCode(pathProject[pathProject.length - 1]);
				panelPayment.getTxtProject().setItem(project);

			} else
				panelPayment.getTxtProject().setItem(null);

			if (invoiceDetail.getStoreCode() != null && !invoiceDetail.getStoreCode().equals(""))
				panelPayment.getStoreJComboBox().setSelectStoreByCode(invoiceDetail.getStoreCode());
			else
				panelPayment.getStoreJComboBox().setSelectedIndex(0);

			List<Contributor> contributors = invoiceDetail.getContributors();
			if (contributors != null && contributors.size() != 0) {
				List<Employee> es = new ArrayList<Employee>();
				for (Contributor c : contributors) {
					if (c.getRole().equals(Contributor.nhanVienPV)) {
						try {
							Employee e = HRModelManager.getInstance().getBydLoginId(c.getIdentifierId());
							txtTVPV.setItem(e);
							e.setModifiedBy(Integer.valueOf(c.getPercent()).toString());
							es.add(e);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
				panelPayment.setRefeshGUIWaiters(es);
			} else {
				txtTVPV.setItem(null);
				List<Employee> es = new ArrayList<Employee>();
				panelPayment.setRefeshGUIWaiters(es);
			}
			tableModel.setData(invoiceDetail);
			loadEmployeeViewText();
			loadCustomerViewText();
			if (tableEat.getTable().getStatus().equals(Table.Status.TableGross)) {
				tableModel.updateStatus(AccountingModelManager.isRecord);
				tableModel.updateStatus(AccountingModelManager.isKitchen);
				tableModel.updateStatus(AccountingModelManager.isPayment);
				tableModel.updateStatus(AccountingModelManager.isPromotion);
				tableModel.updateStatus(AccountingModelManager.isRecord);
			}

		}

	}

	private boolean updateTime() {
		try {
			String strDate = lableDate.getText();
			String strTime = lableTime.getText();
			String dateTime = "";
			List<String> mang = new ArrayList<String>();
			StringTokenizer st = new StringTokenizer(strDate, "/");
			while (st.hasMoreTokens()) {
				mang.add(st.nextToken());
			}
			if (mang.get(0).length() == 1) {
				dateTime = dateTime + "0" + mang.get(0);
			} else {
				dateTime = dateTime + mang.get(0);
			}
			if (mang.get(1).length() == 1) {
				dateTime = dateTime + "0" + mang.get(1);
			} else {
				dateTime = dateTime + mang.get(1);
			}
			if (mang.get(2).length() == 2) {
				dateTime = dateTime + "20" + mang.get(2);
			} else {
				dateTime = dateTime + mang.get(2);
			}
			for (int i = 0; i < strTime.length(); i++) {
				if (!String.valueOf(strTime.charAt(i)).equals(":")) {
					dateTime = dateTime + String.valueOf(strTime.charAt(i));
				}
			}
			tableModel.getInvoiceDetail().setStartDate(new SimpleDateFormat("ddMMyyyyHHmm").parse(dateTime));
			return true;
		} catch (Exception e) {
			tableModel.getInvoiceDetail().setStartDate(new Date());
			return false;
		}
	}

	private void resetTable() {
		try {
			Employee e = HRModelManager.getInstance().getBydLoginId(ManagerAuthenticate.getInstance().getLoginId());
			if (e != null) {
				txtEmployee.setItem(e);
			} else {
				txtEmployee.setItem(null);
			}
			txtPartner.setItem(null);
			txtTVPV.setItem(null);
		} catch (Exception e) {
			txtEmployee.setItem(null);
			txtPartner.setItem(null);
			txtTVPV.setItem(null);
		}
		lableDate.setText(dfDate.format(new Date()));
		lableTime.setText(dfTime.format(new Date()));
		txtGuest.setText("0");
	}

	// Xem lại hóa đơn, load data
	public void setInvoiceDetail(InvoiceDetail invoiceDetail) {
		tableEat = null;
		try {
			table = RestaurantModelManager.getInstance().getTableByCode(invoiceDetail.getTableCode());
			lbNameTable.setText(table.getName());
		} catch (Exception e) {
			table = new Table();
			table.setCode("a");
			table.setLocationCode("b");
		}

		if (invoiceDetail != null) {
			tableModel.setData(invoiceDetail);
			panelPayment.getTxtInvoiceCode().setText(invoiceDetail.codeView());

			try {
				currencyCode = invoiceDetail.getCurrencyUnit();
				panelPayment.setCurrencyCode(currencyCode);
			} catch (Exception e) {
			}
			try {
				loadCustomerViewText();
			} catch (Exception e) {
				tableModel.setInfoInvoice("", pricingType, table);
			}
			try {
				if (invoiceDetail.getEmployeeCode() != null) {
					Employee employee = HRModelManager.getInstance().getEmployeeByCode(invoiceDetail.getEmployeeCode());
					txtEmployee.setItem(employee);
				}
			} catch (Exception e) {
			}
			try {
				for (Contributor c : invoiceDetail.getContributors()) {
					if (c.getRole().equals(Contributor.nhanVienPV)) {
						Employee employee = HRModelManager.getInstance().getBydLoginId(c.getIdentifierId());
						txtTVPV.setItem(employee);
					}
				}
			} catch (Exception e) {
			}
			panelPayment.refershData();

			lableDate.setText(dfDate.format(invoiceDetail.getStartDate()));
			lableTime.setText(dfTime.format(invoiceDetail.getStartDate()));
			txtGuest.setText(String.valueOf(invoiceDetail.getGuest()));
			setEditorGui(false);

		}
	}

	// Hiển thị khách hàng đã chọn lên text
	private void loadCustomerViewText() {
		try {
			Customer c = CustomerModelManager.getInstance()
			    .getCustomerByCode(tableModel.getInvoiceDetail().getCustomerCode());
			txtPartner.setItem(c);
			try {
				tableModel.setInfoInvoice(c.getLoginId(), pricingType, tableEat.getTable());
			} catch (Exception e) {
				tableModel.setInfoInvoice(c.getLoginId(), pricingType, table);
			}
		} catch (Exception e) {
			txtPartner.setItem(null);
			tableModel.setInfoInvoice("", pricingType, tableEat.getTable());
		}

	}

	// Hiển thị nhân viên đã chọn lên text
	private void loadEmployeeViewText() {
		try {
			Employee c = HRModelManager.getInstance().getEmployeeByCode(tableModel.getInvoiceDetail().getEmployeeCode());
			txtEmployee.setItem(c);
		} catch (Exception e) {
			txtEmployee.setItem(null);
		}

	}

	public TableModelInvoiceItem getTableModle() {
		return tableModel;
	}

	// Thêm mới 1 sản phẩm vào bàn
	@Override
	public void addProduct(Product product) {
		// System.out.println(product);
		//TODO
		if (!txtTimKiem.isEnabled()) {
			return;
		}
		if (tableModel.getInvoiceDetail().getId() == null) {
			if (tableEat == null || tableEat.getTable() == null) {
				String str = "Bạn chưa chọn bàn!";
				PanelChoise panel = new PanelChoise(str);
				panel.setName("Xoa");
				DialogResto dialog = new DialogResto(panel, false, 0, 80);
				dialog.setName("dlXoa");
				dialog.setTitle("Chọn bàn");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
				return;
			}
			try {
				InvoiceDetail invoiceDetail = AccountingModelManager.getInstance().saveInvoice(tableModel.getInvoiceDetail());
				tableModel.setData(invoiceDetail);
			} catch (Exception e) {
			}
		}
		try {
			String stt = AccountingModelManager.isRecord;
			if (product.getCode().equals("kara")) {
				stt = AccountingModelManager.isKitchen;
			}
			if (product.getModifiedBy() != null && product.getModifiedBy().equals(AccountingModelManager.isForRent)) {
				stt = AccountingModelManager.isForRent;
			}
			InvoiceItem invoiceItem = AccountingModelManager.getInstance().getInvoiceItem(tableModel.getInvoiceDetail(),
			    product.getCode(), stt);
			Menu menu = PromotionModelManager.getInstance().getMenuByCode(product.getCode());
			if (menu != null) {
				invoiceItem = null;
			}
			if (invoiceItem == null) {
				invoiceItem = new InvoiceItem();
				invoiceItem.setItemName(product.getName());
				if (menu != null) {
					invoiceItem.setItemCode("menu" + dfCode.format(new Date()));
				} else {
					invoiceItem.setItemCode(String.valueOf(new Date().getTime()));
				}

				invoiceItem.setQuantity(quantity);
				invoiceItem.setUnitPrice(0);
				invoiceItem.setActivityType(InvoiceItem.ActivityType.Receipt);
				invoiceItem.setTotal(0);
				invoiceItem.setCurrencyUnit(currencyCode);
				invoiceItem.setProductCode(product.getCode());
				invoiceItem.setWarehouseCode(tableModel.getInvoiceDetail().getWarehouseId());
				// if (!product.getCode().equals("kara")) {
				invoiceItem.setStatus(stt);
				// } else {
				// invoiceItem.setStatus(AccountingModelManager.isKitchen);
				// }

				// create INvoiceItemAttribute

				if (menu != null) {
					DialogChoiseMenuItem item = new DialogChoiseMenuItem(menu);
					DialogResto dialog = new DialogResto(item, false, 810, 500);
					dialog.setLocationRelativeTo(null);
					dialog.setModal(true);
					dialog.setTitle("Thêm sản phẩm tùy chọn");
					dialog.setVisible(true);
					if (item.getListStr() != null && !item.getListStr().isEmpty()) {
						for (int i = 0; i < item.getListStr().size(); i++) {
							InvoiceItemAttribute a = new InvoiceItemAttribute();
							a.setName("Menu");
							a.setValue(item.getListStr().get(i));
							a.setAuthor(item.getListGroup().get(i));
							invoiceItem.add(a);
						}
					}
				}
				if (product.getCode().equals("kara")) {
					tableModel.addItem(invoiceItem, priceKara);
				} else {
					tableModel.addItem(invoiceItem);
				}

				tableModel.changeCaculate("", "");
				if (tableEat != null && tableEat.getTable() != null) {
					if (tableEat.getStatus() == com.hkt.module.restaurant.entity.Table.Status.TableSet
					    || tableEat.getStatus() == com.hkt.module.restaurant.entity.Table.Status.TableFree) {
						tableEat.setStatus(Table.Status.TableBusy);
					}
				}
			} else {
				if (!product.getCode().equals("kara")) {
					invoiceItem.setQuantity(invoiceItem.getQuantity() + quantity);
				} else {
					invoiceItem.setQuantity(quantity);
				}

				tableModel.updateItem(invoiceItem);
			}
			quantity = 1;
			if (profile.get("ForRent") != null && invoiceItem.getStatus().equals(AccountingModelManager.isRecord)) {
				product.setModifiedBy(AccountingModelManager.isForRent);
				addProduct(product);
			} else {
				product.setModifiedBy(null);
			}
		} catch (Exception e) {
			quantity = 1;
			e.printStackTrace();
		}

	}

	@Override
	public void update(Object o, Object arg) {
		try {
			// Nếu [arg] = null thì đẩy kiểu đối tượng vào [o]
			if (arg instanceof String || arg instanceof Product) {
				if (txtTimKiem.getItem() != null) {
					try {
						quantity = Double.parseDouble(arg.toString());
					} catch (Exception e) {
						quantity = 1;
					}
					addProduct(txtTimKiem.getItem());
				}
			}

			if (arg instanceof Table) {
				panelTable.setTable((Table) arg);
			}

			if (arg instanceof Customer || o instanceof Customer) {
				InvoiceDetail invoiceDetail = tableModel.getInvoiceDetail();
				// Tạo contributor nhóm khách hàng
				// Nếu có chọn khách hàng
				if (arg != null) {
					Customer customer = (Customer) arg;
					// Tạo contributor nhóm khách hàng
					tableModel.getInvoiceDetail().setCustomerCode(customer.getCode());
					List<AccountMembership> accMemberShips = AccountModelManager.getInstance().findMembershipByAccountLoginId(
					    customer.getLoginId());
					if (accMemberShips != null && accMemberShips.size() > 0) {
						tableModel.getInvoiceDetail().setGroupCustomerCode(accMemberShips.get(0).getGroupPath());
					} else {
						tableModel.getInvoiceDetail().setGroupCustomerCode(
						    CustomerModelManager.getInstance().getGroupCustomerOther().getPath());
					}
					if (tableModel.getInvoiceDetail().getId() == null) {
						try {
							AccountingModelManager.getInstance().saveInvoice(tableModel.getInvoiceDetail());
						} catch (Exception e) {
						}
					}
					try {
						tableModel.setInfoInvoice(customer.getLoginId(), pricingType, tableEat.getTable());
						tableModel.setData(invoiceDetail);
					} catch (Exception e) {
						tableModel.setInfoInvoice("", pricingType, table);
						tableModel.setData(invoiceDetail);
					}
					tableModel.loadPromotionProduct();
				} else { // Nếu không chọn khách hàng
					tableModel.getInvoiceDetail().setCustomerCode(null);
					tableModel.getInvoiceDetail().setGroupCustomerCode(
					    CustomerModelManager.getInstance().getGroupCustomerOther().getPath());
					try {
						tableModel.setInfoInvoice("", pricingType, tableEat.getTable());
						tableModel.setData(invoiceDetail);
					} catch (Exception e) {
						tableModel.setInfoInvoice("", pricingType, table);
						tableModel.setData(invoiceDetail);
					}
					tableModel.loadPromotionProduct();
				}
			}

			// Tạo contributor phòng ban nhân viên
			if (arg instanceof Employee || o instanceof Employee) {
				InvoiceDetail invoiceDetail = tableModel.getInvoiceDetail();
				// Nếu chọn nhân viên
				if (arg != null) {
					Employee employee = (Employee) arg;
					tableModel.getInvoiceDetail().setEmployeeCode(employee.getCode());

					try {
						tableModel.setData(invoiceDetail);
					} catch (Exception e) {
					}
					// Nếu ko chọn nhân viên
				} else {
					try {
						AccountGroup departmentCashier = panelPayment.getDepartmentJComboBox().getSelectedDepartment();
						if (departmentCashier == null) {
							tableModel.getInvoiceDetail().setDepartmentCode(
							    HRModelManager.getInstance().getDepartmentOther().getPath());
						} else {
							tableModel.getInvoiceDetail().setDepartmentCode(departmentCashier.getPath());
						}
					} catch (Exception e) {
					}
				}
			}
		} catch (Exception e) {
		}
		try {
			if (arg.equals(TextPopup.LOAD) && o.equals(Product.class)) {
				panelProduct.loadGui();
			}
			if (arg.equals(TextPopup.LOAD) && o.equals(Table.class)) {
				panelTable.refeshGui();
			}
		} catch (Exception e) {
		}

	}

	// In toàn bộ hóa đơn
	private boolean printReceipt(boolean view, boolean exel, InvoiceDetail paymentSlip) throws HeadlessException {
		InvoiceDetail invoiceDetail = tableModel.getInvoiceDetail();
		if (paymentSlip != null) {
			invoiceDetail = paymentSlip;
		}
		String loginId = ManagerAuthenticate.getInstance().getOrganizationLoginId();
		String enterpriseName = AccountModelManager.getInstance().getNameByLoginId(loginId);
		String adres = AccountModelManager.getInstance().getAddressByLoginId(loginId);
		String fone = AccountModelManager.getInstance().getPhoneByLoginId(loginId);
		String fax = AccountModelManager.getInstance().getFaxByLoginId(loginId);
		String projectTable = lbNameTable.getText();

		String tongSL = "";
		double sl = 0;
		for (InvoiceItem invoiceItem : invoiceDetail.getItems()) {
			sl = sl + invoiceItem.getQuantity();
		}
		try {
			tongSL = new MyDouble(sl).toString();
		} catch (Exception e) {
		}
		String nameArea;
		try {
			nameArea = RestaurantModelManager.getInstance()
			    .getLocationByCode(tableModel.getInvoiceDetail().getLocationCode()).toString();
		} catch (Exception e) {
			nameArea = " ";
		}
		if (panelPayment.getTxtProject().getItem() != null
		    && !panelPayment.getTxtProject().getItem().getCode().equals("project-other")) {
			nameArea = panelPayment.getTxtProject().getItem().getCode();
		}
		if (nameArea.equals(RestaurantModelManager.getInstance().getLocationPaymentAfter().getName())) {
			nameArea = "";
		}
		String currencyCode = LocaleModelManager.getInstance().getCurrencyByCode(this.currencyCode).getName();

		String ratio = "";
		String moneyQuyDoi = "";
		if (!this.currencyCode.equals("VND")) {
			ratio = new MyDouble(LocaleModelManager.getInstance().getCurrencyByCode(this.currencyCode).getRate()).toString();
			double a = LocaleModelManager.getInstance().getCurrencyByCode(this.currencyCode).getRate()
			    * (invoiceDetail.getFinalCharge() - invoiceDetail.getTotalPaid());
			moneyQuyDoi = new MyDouble(a).toString();
		}
		String nvpv = txtTVPV.getText();
		String lienHoaDon = "1", solanIn = "1";
		String tongSoDiem = "", soDiemConLai = "", tongSoTien = "", soTienConLai = "";
		String credit = "";
		String gioVao = lableTime.getText(), gioRa = dfTime.format(new Date());
		String diaChi = "", sdt = "";
		if (txtPartner.getItem() != null) {
			diaChi = AccountModelManager.getInstance().getAddressByLoginId(txtPartner.getItem().getLoginId());
			sdt = AccountModelManager.getInstance().getPhoneByLoginId(txtPartner.getItem().getLoginId());
			try {
				Credit c = CustomerModelManager.getInstance().getCreditByCustomerId(txtPartner.getItem().getId());
				credit = new MyDouble(c.getCredit()).toString();
			} catch (Exception e) {
			}

		}

		String date = "";
		try {
			date = dfDate.format(invoiceDetail.getStartDate());
		} catch (Exception e) {
			date = dfDate.format(new Date());
		}
		String ptThue = "";
		if (panelPayment.getCboTax().getSelectedItem() != null) {
			ptThue = MyDouble.valueOf(panelPayment.getCboTax().getSelectedItem().toString()).toString();
		}
		String code = tableModel.getInvoiceDetail().getInvoiceCode();
		if (code.indexOf(":") > 0) {
			code = code.substring(code.indexOf(":") + 1);
		}
		String strStt = "Phiếu Thanh Toán";
		Customer c = txtPartner.getItem();
		String soDiemDung = "";
		if (c != null) {
			Point p = CustomerModelManager.getInstance().getPointByCustomerId(c.getId());
			if (p != null && p.getPoint() != 0) {
				tongSoDiem = new MyDouble(p.getPoint()).toString();
				if (!invoiceDetail.getStatus().equals(Status.Paid)) {
					soDiemDung = panelPayment.getTxtTotalMark().getText();
					try {
						MyDouble a1 = new MyDouble(p.getPoint());
						a1.setNumDot(0);
						MyDouble a2 = new MyDouble(a1.toString());
						soDiemConLai = new MyDouble(a2.doubleValue() - MyDouble.parseDouble(soDiemDung)).toString();
					} catch (Exception e) {
						soDiemConLai = "0";
					}
					try {
						tongSoTien = new MyDouble(p.getPoint()
						    * CustomerModelManager.getInstance().getConversionRuleMoneyToPoint().numRatioPointToCredit())
						    .toString();
					} catch (Exception e) {
						tongSoTien = "0";
					}
					try {
						double a = MyDouble.parseDouble(tongSoTien) - invoiceDetail.getPoint();
						if (a < 0) {
							a = 0;
						}
						soTienConLai = new MyDouble(a).toString();
					} catch (Exception e) {
						soTienConLai = "0";
					}
				}

			}
		}
		String datCoc = getMoney(invoiceDetail.getFinalCharge()).toString(), // Da
		// tra
		tienThue = getMoney(invoiceDetail.getFinalCharge()).toString(); // Tong cong
		// String phaiTra = getMoney(invoiceDetail.getFinalCharge() -
		// invoiceDetail.getTotalPaid()).toString();
		String phaiTra = "0";
		if (printAgain || paymentSlip != null) {
			phaiTra = getMoney(invoiceDetail.getFinalCharge() - invoiceDetail.getTotalPaid()).toString();

			tienThue = getMoney(invoiceDetail.getFinalCharge()).toString();
			datCoc = getMoney(invoiceDetail.getTotalPaid()).toString();
		}
		Date dateEnd = new Date();
		String strMoney = ManagerConvert.getInstance().convertMoney(phaiTra);
		if (invoiceDetail.getStatus().equals(Status.Paid)) {
			if (tableModel.getInvoiceDetail().getEndDate() != null) {
				if (tableModel.getInvoiceDetail().getTransactions() != null
				    && !tableModel.getInvoiceDetail().getTransactions().isEmpty()) {
					dateEnd = tableModel.getInvoiceDetail().getTransactions()
					    .get(tableModel.getInvoiceDetail().getTransactions().size() - 1).getTransactionDate();
				} else {
					dateEnd = tableModel.getInvoiceDetail().getEndDate();
				}
			}
			datCoc="0";
			phaiTra="0";
			strMoney= ManagerConvert.getInstance().convertMoney(tienThue);
		}

		String discount="";
		String discountRate ="";
		if(panelPayment.getRbtnDiscountMoney().isSelected()){
			discount=MyDouble.valueOf(invoiceDetail.getDiscount()).toString();
		}else{
			discount=MyDouble.valueOf(invoiceDetail.getDiscount()).toString();
			discountRate=MyDouble.valueOf(invoiceDetail.getDiscountRate()).toString();
		}
		String[] str = new String[] { enterpriseName, adres, fone, fax, date, code, nameArea, projectTable, gioVao,
		    txtEmployee.getText(), MyDouble.valueOf(invoiceDetail.getTotal()).toString(),
		    discountRate,
		    discount, ptThue,
		    MyDouble.valueOf(invoiceDetail.getTotalTax()).toString(), tienThue, datCoc, phaiTra, strStt,
		    dfDate.format(dateEnd), gioRa, currencyCode, MyDouble.valueOf(invoiceDetail.getService()).toString(), ratio,
		    moneyQuyDoi, MyDouble.valueOf(invoiceDetail.getServiceRate()).toString(), strMoney, soDiemDung,
		    MyDouble.valueOf(invoiceDetail.getPoint()).toString(), txtPartner.getText(), txtGuest.getText(), nvpv, credit,
		    lienHoaDon, solanIn, tongSoDiem, soDiemConLai, tongSoTien, soTienConLai, credit,
		    MyDouble.valueOf(invoiceDetail.getCredit()).toString(), tongSL, PanelTextMoneyPayment.getSoTienKhachDua(),
		    PanelTextMoneyPayment.getSoTienLeTraKhach(), diaChi, sdt, AccountingModelManager.pay };
		try {
			String name = invoiceDetail.getInvoiceCode() + "_"+String.valueOf(invoiceDetail.getTransactions().size());
			if (profile.get("Exel") == null && profile.get("ExelMail")==null) {
				name = null;
			}
			if (!exel) {
				name = null;
			}
			Table table = this.table;
			if (tableEat != null && tableEat.getTable() != null) {
				table = tableEat.getTable();
			}
			if (paymentSlip != null) {
				return ReportPrint.getInstance().printReceiptSlip(table, tableModel, invoiceDetail.getItems(), str, view);
			} else {

				return ReportPrint.getInstance().printReceipt(table, tableModel, tableModel.getTablePromotion(), str, view,
				    name);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	private MyDouble getMoney(double moneyMissing1) {
		MyDouble a = new MyDouble(moneyMissing1);
		if (panelPayment.getRounding() != 0) {
			if (panelPayment.getRounding() > 0) {
				moneyMissing1 = moneyMissing1 / panelPayment.getRounding();
				MyDouble b = new MyDouble(moneyMissing1);
				b.setNumDot(0);

				if (moneyMissing1 > new MyDouble(b.toString()).doubleValue()) {
					moneyMissing1 = moneyMissing1 + 1;
				}
				MyDouble d = new MyDouble(moneyMissing1);
				d.setNumDot(0);
				MyDouble c = new MyDouble(d.toString());
				moneyMissing1 = c.doubleValue() * panelPayment.getRounding();
				a = new MyDouble(moneyMissing1);
				a.setNumDot(0);
				moneyMissing1 = a.doubleValue();
			} else {
				try {
					int k = MyDouble.valueOf(panelPayment.getRounding()).intValue() * (-1);
					a = new MyDouble(moneyMissing1);
					a.setNumDot(k);
				} catch (Exception e) {
				}
			}
		}
		return a;
	}

	// Thanh toán lẻ
	private void paymentSlip() {
		DialogPayOnce dialogPrintReceipt = new DialogPayOnce(tableModel.getInvoiceDetail());
		DialogResto dialog1 = new DialogResto(dialogPrintReceipt, false, 0, 120);
		dialog1.setTitle("Thanh toán lẻ");
		dialog1.setBtnSave(false);
		// dialog1.setBtnExit(false);
		dialog1.setLocationRelativeTo(null);
		dialog1.setModal(true);
		dialog1.setVisible(true);
		/** Thanh toán lẻ bằng sản phẩm */
		if (dialogPrintReceipt.getReceipt() == 0) {
			InvoiceDetail invoiceDetail = tableModel.getInvoiceDetail();
			try {
				invoiceDetail = AccountingModelManager.getInstance().saveInvoice(tableModel.getInvoiceDetail());
			} catch (Exception e) {
			}
			PanelPaymentProduct panel = new PanelPaymentProduct(invoiceDetail);
			panel.setName("ScreenOften_PaymenOdd");
			if (Toolkit.getDefaultToolkit().getScreenSize().width <= 1024) {
				DialogResto dialog = new DialogResto(panel, false, 800, 500);
				dialog.setName("dlScreenOften_PaymenOdd");
				dialog.setTitle("Thanh toán lẻ");
				dialog.dispose();
				dialog.setUndecorated(true);
				dialog.setSize(new Dimension(java.awt.Toolkit.getDefaultToolkit().getScreenSize()));
				dialog.setModal(true);
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
			} else {
				DialogResto dialog = new DialogResto(panel, false, 1050, 550);
				dialog.setName("dlScreenOften_PaymenOdd");
				dialog.setTitle("Thanh toán lẻ");
				dialog.dispose();
				dialog.setUndecorated(true);
				dialog.setSize(new Dimension(java.awt.Toolkit.getDefaultToolkit().getScreenSize()));
				dialog.setModal(true);
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
			}
			if (panel.isSave()) {
				tableModel.setData(panel.getInvoiceDetail());
				tableModel.caculator();
				tableModel.updateStatus(AccountingModelManager.isPayment);
				tableModel.changeCaculate(null, "");
				PanelTextMoneyPayment panelP = new PanelTextMoneyPayment();
				panelP.setName("panelPayment");
				panelP.initEvent(panel.getTxtMoneyBeforeTax().getText(), currencyCode);
				DialogResto dialog = new DialogResto(panelP, false, 400, 350);
				dialog.setName("dlPayment");
				dialog.setTitle("Thanh toán");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
				if (!panelP.isFlagPayment()) {
					return;
				}
				printReceipt(true, false, getInvoiceDetailPayment(panel.getInvoiceItemPayment()));
			}
		} else {
			/** thanh toán lẻ bằng tiền */
			if (dialogPrintReceipt.getReceipt() == 1) {
				try {
					PanelUpfrontPayment panel = new PanelUpfrontPayment();
					panel.initEvent(tableModel.getInvoiceDetail());
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
						tableModel.changeCaculate(null, null);
						InvoiceDetail invoice = AccountingModelManager.getInstance().saveInvoice(tableModel.getInvoiceDetail());
						tableModel.setData(invoice);
						PanelTextMoneyPayment panelP = new PanelTextMoneyPayment();
						panelP.setName("panelPayment");
						panelP.initEvent(panel.getTxtBeforPayment().getText(), currencyCode);
						DialogResto dialogP = new DialogResto(panelP, false, 400, 350);
						dialogP.setName("dlPayment");
						dialogP.setTitle("Thanh toán");
						dialogP.setLocationRelativeTo(null);
						dialogP.setModal(true);
						dialogP.setVisible(true);
						if (!panelP.isFlagPayment()) {
							return;
						}
						printAgain = true;
						printReceipt(true, false, null);
						printAgain = false;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				if (dialogPrintReceipt.getReceipt() == 2) {
					try {
						InvoiceDetail invoiceDetail = AccountingModelManager.getInstance().getInvoiceDetail(
						    tableModel.getInvoiceDetail().getId());
						tableModel.setData(invoiceDetail);
						tableModel.changeCaculate(null, null);
					} catch (Exception e) {
					}
				}
			}
		}
	}

	// Xóa sản phẩm khỏi hóa đơn
	private void deleteProduct() {
		try {
			if (panelPayment.getSelectedInvoiceItem() != null) {

				ChooseDelProduct panel = new ChooseDelProduct(panelPayment.getSelectedInvoiceItem().getItemName());
				panel.setName("Xoa");
				DialogResto dialog = new DialogResto(panel, false, 0, 80);
				dialog.setName("dlXoa");
				// dialog.setSize(500, 280);
				// dialog.setPreferredSize(new Dimension(500, 230));
				dialog.setTitle("Xóa sản phẩm");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
				if (panel.isDelete()) {
					panelPayment.deleteItem();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Hủy sản phẩm khỏi hóa đơn
	private void canceProduct() {
		try {
			PanelChooseDesProduct panel = new PanelChooseDesProduct(panelPayment.getSelectedInvoiceItem());
			DialogResto dialog = new DialogResto(panel, false, 0, 150);
			dialog.setTitle("Hủy sản phẩm");
			// dialog.setSize(650, 350);
			// dialog.setPreferredSize(new Dimension(650, 350));
			dialog.setLocationRelativeTo(null);
			dialog.setModal(true);
			dialog.setVisible(true);
			if (panel.isEdit()) {

				if (!panel.getInvoiceItem().getStatus().equals(AccountingModelManager.isRecord)) {
					if (panel.getInvoiceItem().getQuantity() == 0) {
						panel.getInvoiceItem().setQuantity(panel.getQuantityCance());
						panel.getInvoiceItem().setStatus(AccountingModelManager.isCance);
					} else {
						InvoiceItem invoiceItem = new InvoiceItem();
						invoiceItem.setItemName(panel.getInvoiceItem().getItemName());
						invoiceItem.setItemCode(dfCode.format(new Date()));
						invoiceItem.setQuantity(panel.getQuantityCance());
						invoiceItem.setUnitPrice(panel.getInvoiceItem().getUnitPrice());
						invoiceItem.setTotal(invoiceItem.getQuantity() * invoiceItem.getUnitPrice());
						invoiceItem.setCurrencyUnit(currencyCode);
						invoiceItem.setStatus(AccountingModelManager.isCance);
						tableModel.getInvoiceDetail().add(invoiceItem);
					}
					tableModel.updateStatus(AccountingModelManager.isCance);
				} else {
					if (panel.getInvoiceItem().getQuantity() == 0) {
						tableModel.removeRow(tableModel.getInvoiceDetail().getItems().indexOf(panel.getInvoiceItem()));
					} else {
						tableModel.updateItem(panel.getInvoiceItem());
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Sửa số lượng sản phẩm
	private void changeQuantityProduct() {
		try {
			PanelChooseQuantity panel = new PanelChooseQuantity(panelPayment.getSelectedInvoiceItem());
			DialogResto dialog = new DialogResto(panel, false, 0, 100);
			dialog.setTitle("Số lượng");
			dialog.setLocationRelativeTo(null);
			dialog.setModal(true);
			dialog.setVisible(true);
			if (panel.isEdit()) {
				tableModel.updateItem(panelPayment.getSelectedInvoiceItem());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Sửa giá sản phẩm
	private void changePriceProduct() {
		try {
			if (panelPayment.getSelectedInvoiceItem() != null) {
				PanelChoosePrice choosePrice = new PanelChoosePrice(panelPayment.getSelectedInvoiceItem());
				choosePrice.setName("choosePrice");
				DialogResto dialog = new DialogResto(choosePrice, false, 0, 100);
				dialog.setName("dlchoosePrice");
				dialog.setTitle("Đổi giá");
				dialog.setVisible(true);
				dialog.setLocationRelativeTo(null);
				if (choosePrice.isEdit()) {
					tableModel.updateItem(panelPayment.getSelectedInvoiceItem());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setupEditTime() {

		PanelEditTimed panel = new PanelEditTimed();

		panel.initEvent(tableModel.getInvoiceDetail());
		DialogResto dialog = new DialogResto(panel, false, 450, 220);
		dialog.setTitle("Chỉnh sửa giờ");
		dialog.setLocationRelativeTo(null);
		dialog.setModal(true);
		dialog.setVisible(true);
		if (panel.isSave()) {
			tableModel.setData(panel.getInvoice());
			lableDate.setText(dfDate.format(tableModel.getInvoiceDetail().getStartDate()));
			lableTime.setText(dfTime.format(tableModel.getInvoiceDetail().getStartDate()));
		}
	}

	// Đặt giờ cho sản phẩm (Ka ra)
	private void setupTimeProduct() {
		try {
			PanelTimed panel = new PanelTimed();
			try {
				panel.setInfo(pricingType, tableEat.getTable().getLocationCode(), tableEat.getTable().getCode());
			} catch (Exception e) {
				panel.setInfo(pricingType, tableModel.getInvoiceDetail().getLocationCode(), tableModel.getInvoiceDetail()
				    .getTableCode());
			}

			panel.initEvent(tableModel.getInvoiceDetail());
			DialogResto dialog = new DialogResto(panel, false, 450, 220);
			dialog.setTitle("Thêm ngày giờ kara");
			dialog.setBtnExt3(panel.getBtnPrint());
			dialog.setLocationRelativeTo(null);
			dialog.setModal(true);
			dialog.setVisible(true);
			if (panel.isFlagUpdate()) {
				Product kara = ProductModelManager.getInstance().getProductKara();
				for (int i = 0; i < tableModel.getDataVector().size();) {
					if (((InvoiceItem) tableModel.getDataVector().get(i)).getProductCode().equals(kara.getCode())) {
						tableModel.removeRow(i);
					} else {
						i++;
					}
				}

				Iterator<Long> inIterator = panel.getHashMap().keySet().iterator();
				while (inIterator.hasNext()) {
					Long a = inIterator.next();
					ProductPrice pp = ProductModelManager.getInstance().getProductPriceById(a);
					if (pp != null) {
						priceKara = pp.getPrice();
						MyDouble d = panel.getHashMap().get(a);
						quantity = d.doubleValue();
						MyDouble m = MyDouble.valueOf(quantity);
						m.setNumDot(2);
						quantity = new MyDouble(m.toString()).doubleValue();
						Product p = ProductModelManager.getInstance().getProductKara();
						int index = pp.getCreatedBy().indexOf(":");
						p.setName(p.getName() + " KG " + pp.getCreatedBy().substring(0, index) + "-"
						    + pp.getModifiedBy().substring(0, index));
						addProduct(p);
					} else {
						MyDouble d = panel.getHashMap().get(a);
						quantity = d.doubleValue();
						MyDouble m = MyDouble.valueOf(quantity);
						m.setNumDot(2);
						quantity = new MyDouble(m.toString()).doubleValue();
						Product p = ProductModelManager.getInstance().getProductKara();
						addProduct(p);
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Mua hộ sản phẩm khách hàng
	private void muaHoSanPham() {
		try {
			if (profile.get(DialogConfig.MuaHo).toString().equals("true")) {
				PanelHouseholdProducts panel = new PanelHouseholdProducts();
				DialogResto dialog = new DialogResto(panel, false, 650, 380);
				dialog.setTitle("Mua hộ");
				dialog.setPreferredSize(new Dimension());
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Chọn đối tác
	private void choiseCustomer() {
		// System.out.println( tableModel.getInvoiceDetail());
		PanelListPartnerOrganization panel = new PanelListPartnerOrganization();
		panel.setName("PaymentbyPoint");
		DialogResto dialog = new DialogResto(panel, true, 1000, 350);
		dialog.dispose();
		dialog.setUndecorated(true);
		dialog.setName("dlPaymentbyPoint");
		dialog.setTitle("DS đối tác DN");
		dialog.setModal(true);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);

		Customer customer = panel.getCustomer();
		if (customer != null) { // Trường hợp khách hàng được chọn
			tableModel.getInvoiceDetail().setCustomerCode(customer.getCode());

			// Tạo contributor nhóm khách hàng
			List<AccountMembership> accMemberShips = null;
			try {
				accMemberShips = AccountModelManager.getInstance().findMembershipByAccountLoginId(customer.getLoginId());
			} catch (Exception e) {
			}

			if (accMemberShips != null && accMemberShips.size() > 0) {
				tableModel.getInvoiceDetail().setGroupCustomerCode(accMemberShips.get(0).getGroupPath());
			} else {
				tableModel.getInvoiceDetail().setGroupCustomerCode(
				    CustomerModelManager.getInstance().getGroupCustomerOther().getPath());
			}
			txtPartner.setItem(customer);
			tableModel.setInfoInvoice(customer.getLoginId(), pricingType, tableEat.getTable());
			tableModel.loadPromotionProduct();
		}
	}

	// Chọn thành viên phục vụ
	private void choiseEmployeeServer() {
		if (tableModel.getInvoiceDetail() == null) {
			JOptionPane.showMessageDialog(null, "Phải chọn bàn trước");
		} else {
			try {
				PanelMemberServe panelMemberServe = new PanelMemberServe(tableModel.getInvoiceDetail());
				DialogMain dialog = new DialogMain(panelMemberServe);
				dialog.setPreferredSize(new Dimension(900, 500));
				dialog.setLocationRelativeTo(null);
				dialog.setTitle("Nhân viên phục vụ");
				dialog.setModal(true);
				dialog.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// In hóa đơn
	private void printInvoice() {
		DialogPrint dialogPrint = new DialogPrint(getMoney(
		    tableModel.getInvoiceDetail().getFinalCharge() - tableModel.getInvoiceDetail().getTotalPaid()).toString(),
		    currencyCode);
		DialogResto dialog = new DialogResto(dialogPrint, false, 0, 120);
		dialog.setTitle("In hóa đơn");
		dialog.setBtnSave(false);
		// dialog.setBtnExit(false);
		dialog.setLocationRelativeTo(null);
		dialog.setModal(true);
		dialog.setVisible(true);

		boolean view;
		Status status = tableModel.getInvoiceDetail().getStatus();
		if (dialogPrint.getPrint() == 1) {
			view = true;
		} else {
			if (dialogPrint.getPrint() == 3) {
				PanelTextMoneyPayment.setSotienKD("0");
				PanelTextMoneyPayment.setSotienTK("0");
				view = false;
			} else {
				return;
			}
		}
		double totalP = tableModel.getInvoiceDetail().getTotalPaid();

		if (tableModel.getInvoiceDetail().getStatus().equals(Status.Paid)) {
			tableModel.getInvoiceDetail().setTotalPaid(0);
		}
		boolean print = false;
		if (totalP > 0) {
			printAgain = true;
			print = printReceipt(view, false, null);
			printAgain = false;
		} else {
			tableModel.getInvoiceDetail().setStatus(Status.Paid);
			print = printReceipt(view, false, null);
		}

		if (tableModel.getInvoiceDetail().getStatus().equals(Status.Paid)) {
			tableModel.getInvoiceDetail().setTotalPaid(totalP);
		}
		if (tableEat != null && print) {
			tableEat.setStatus(com.hkt.module.restaurant.entity.Table.Status.TableServe);
			tableEat.setColorTable();
		}
		tableModel.getInvoiceDetail().setStatus(status);
		AccountingModelManager.pay = "Tiền mặt";
	}

	// Hiển thị danh sách khuyến mãi
	private void viewListPromotion() {
		TableListViewPromotion tbviewpromotion = new TableListViewPromotion();
		tbviewpromotion.setName("tbviewpromotion");
		DialogList dialog = new DialogList(tbviewpromotion);

		dialog.setTitle("Xem khuyến mại");
		dialog.setName("tbviewpromotion");
		dialog.setVisible(true);
	}

	// Thiết lập làm tròn số
	private void lamTronSo() {
		try {
			PanelRounding panel = new PanelRounding();
			DialogResto dialog = new DialogResto(panel, false, 0, 120);
			dialog.setTitle("Làm tròn số");
			dialog.setLocationRelativeTo(null);
			dialog.setModal(true);
			dialog.setVisible(true);
			panelPayment.refeshGui();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addKeyBindings(JComponent jc) {

		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0, false), "F3");
		jc.getActionMap().put("F3", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				printCanceProduct();
			}
		});
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0, false), "F4");
		jc.getActionMap().put("F4", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				printInvoice();
			}
		});
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0, false), "abc");
		jc.getActionMap().put("abc", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				printKitchent();
			}
		});
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0, false), "F5");
		jc.getActionMap().put("F5", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					payment();
				} catch (Exception e) {
				}

			}
		});
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0, false), "F6");
		jc.getActionMap().put("F6", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				paymentSlip();
			}
		});
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0, false), "F7");
		jc.getActionMap().put("F7", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				paymentAfter();
			}
		});
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0, false), "F11");
		jc.getActionMap().put("F11", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					PanelChooseProductExport panel1 = new PanelChooseProductExport("");
					DialogResto dialog1 = new DialogResto(panel1, false, 600, 300);
					dialog1.setTitle("Thiết lập kho");
					dialog1.setLocationRelativeTo(null);
					dialog1.setModal(true);
					dialog1.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0, false), "F10");
		jc.getActionMap().put("F10", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					PaneSetInvoice invoice = new PaneSetInvoice();
					invoice.setName("ThemMaTuSinh");

					DialogResto dialog = new DialogResto(invoice, true, 2000, 900);

					dialog.dispose();
					dialog.setUndecorated(true);
					dialog.setIcon("cauhinh1.png");
					dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
					dialog.setTitle("Q.Lý Mã hóa đơn");
					dialog.setModal(true);
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		});

		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "ESC");
		jc.getActionMap().put("ESC", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				exitSystem();
			}
		});
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0, false), "F8");
		jc.getActionMap().put("F8", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				viewKH();
			}
		});
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0, false), "F9");
		jc.getActionMap().put("F9", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				txtTable.requestFocus();
			}
		});

		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD0, 0, false), "0");
		jc.getActionMap().put("0", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				numberAction("0");
			}
		});

		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_0, 0, false), "0");
		jc.getActionMap().put("0", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				numberAction("0");
			}
		});

		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD1, 0, false), "1");
		jc.getActionMap().put("1", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				numberAction("1");
			}
		});

		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0, false), "1");
		jc.getActionMap().put("1", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				numberAction("1");
			}
		});

		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_2, 0, false), "2");
		jc.getActionMap().put("2", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				numberAction("2");
			}
		});

		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD2, 0, false), "2");
		jc.getActionMap().put("2", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				numberAction("2");
			}
		});

		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_3, 0, false), "3");
		jc.getActionMap().put("3", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				numberAction("3");
			}
		});
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD3, 0, false), "3");
		jc.getActionMap().put("3", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				numberAction("3");
			}
		});

		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_4, 0, false), "4");
		jc.getActionMap().put("4", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				numberAction("4");
			}
		});

		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD4, 0, false), "4");
		jc.getActionMap().put("4", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				numberAction("4");
			}
		});

		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_5, 0, false), "5");
		jc.getActionMap().put("5", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				numberAction("5");
			}
		});
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0, false), "F12");
		jc.getActionMap().put("F12", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
			}
		});

		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD5, 0, false), "5");
		jc.getActionMap().put("5", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				numberAction("5");
			}
		});

		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_6, 0, false), "6");
		jc.getActionMap().put("6", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				numberAction("6");
			}
		});

		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD6, 0, false), "6");
		jc.getActionMap().put("6", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				numberAction("6");
			}
		});

		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_7, 0, false), "7");
		jc.getActionMap().put("7", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				numberAction("7");
			}
		});

		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD7, 0, false), "7");
		jc.getActionMap().put("7", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				numberAction("7");
			}
		});
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_8, 0, false), "8");
		jc.getActionMap().put("8", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				numberAction("8");
			}
		});

		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD8, 0, false), "8");
		jc.getActionMap().put("8", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				numberAction("8");
			}
		});

		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_9, 0, false), "9");
		jc.getActionMap().put("9", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				numberAction("9");
			}
		});

		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD9, 0, false), "9");
		jc.getActionMap().put("9", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				numberAction("9");
			}
		});
	}

	protected void numberAction(String string) {
	}

	// Đóng toàn bộ sản phẩm
	protected void lbClossAllMouseClicked(MouseEvent evt) {
		panelProduct.opendAll(false);
	}

	public void deleteDataTest() {
		if (!PanelTestAll.runAll) {
			DialogTest.getInstance().show("Xóa toàn bộ dữ liệu test ?");
			if (DialogTest.getInstance().isTest()) {
				try {
					AccountModelManager.getInstance().deleteTest("HktTest");
					HRModelManager.getInstance().deleteTest("HktTest");
					CustomerModelManager.getInstance().deleteTest("HktTest");
					SupplierModelManager.getInstance().deleteTest("HktTest");
					ProductModelManager.getInstance().deleteTest("HktTest");
					WarehouseModelManager.getInstance().deleteTest("HktTest");
					RestaurantModelManager.getInstance().deleteTest("HktTest");
					LocaleModelManager.getInstance().deleteTest("HktTest");
					GenericOptionModelManager.getInstance().deleteTest("HktTest");
					AccountingModelManager.getInstance().deleteTest("HktTest");
					PromotionModelManager.getInstance().deleteTest("HktTest");
					panelTable.refeshGui();
					panelProduct.loadGui();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void setListProduct(List<Product> list) {
		txtTimKiem.setData(list);

	}

}
