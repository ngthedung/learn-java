package com.hkt.client.swingexp.app.banhang.screen.pos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
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
import com.hkt.client.swingexp.app.banhang.screen.often.DialogConfig;
import com.hkt.client.swingexp.app.banhang.screen.often.DialogPayOnce;
import com.hkt.client.swingexp.app.banhang.screen.often.DialogPrint;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelDiscountProduct;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelListPartnerOrganization;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelPayment;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelPaymentProduct;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelRounding;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelTextMoneyPayment;
import com.hkt.client.swingexp.app.banhang.screen.often.TableEat;
import com.hkt.client.swingexp.app.banhang.screen.often.TableModelInvoiceItem;
import com.hkt.client.swingexp.app.banhang.screen.pos.PanelScreenSaleLocationPos.ComponentTable;
import com.hkt.client.swingexp.app.banhang.screen.pos.DialogScreenOftenPos.ComponentProduct;
import com.hkt.client.swingexp.app.component.ExtendJCheckBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
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
import com.hkt.client.swingexp.app.core.WarehousesJComboBox;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.app.khohang.CatalogProductTag;
import com.hkt.client.swingexp.app.khohang.PanelRepositoryProducts2;
import com.hkt.client.swingexp.app.khuvucbanhang.PanelManageCodes;
import com.hkt.client.swingexp.app.khuyenmai.DialogChoiseMenuItem;
import com.hkt.client.swingexp.app.khuyenmai.list.TableListViewPromotion;
import com.hkt.client.swingexp.app.print.ReportPrint;
import com.hkt.client.swingexp.app.thuchi.PanelPhieuDatMuaHang;
import com.hkt.client.swingexp.app.virtualkey.text.PanelTextKeyboard;
import com.hkt.client.swingexp.homescreen.HomeScreen;
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
import com.hkt.module.accounting.entity.Invoice.ActivityType;
import com.hkt.module.accounting.entity.Invoice.Status;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.accounting.entity.InvoiceItemAttribute;
import com.hkt.module.accounting.entity.InvoiceTransaction;
import com.hkt.module.accounting.entity.InvoiceTransaction.TransactionType;
import com.hkt.module.config.generic.Option;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.partner.customer.entity.Credit;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.customer.entity.Point;
import com.hkt.module.partner.supplier.entity.Supplier;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductPrice;
import com.hkt.module.product.entity.ProductTag;
import com.hkt.module.promotion.entity.Menu;
import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.LocationAttribute;
import com.hkt.module.restaurant.entity.Project;
import com.hkt.module.restaurant.entity.Table;
import com.hkt.module.warehouse.entity.IdentityProduct;
import com.hkt.module.warehouse.entity.IdentityProduct.ExportType;
import com.hkt.module.warehouse.entity.IdentityProduct.ImportType;
import com.hkt.module.warehouse.entity.IdentityProductAttribute;
import com.hkt.module.warehouse.entity.Warehouse;
import com.hkt.module.warehouse.entity.WarehouseInventory;
import com.hkt.util.text.DateUtil;

/*
 * @author: Phan Anh
 * @edit: Phan Anh
 * @date: 15/06/2015
 */

public class DialogPhieuDatMuaHangPos extends JDialog implements IScreenSales, IMyObserver {
	private javax.swing.JButton								btnSoLuong;
	private javax.swing.JButton								btnPromotions;
	private javax.swing.JButton								btnCancel;
	private javax.swing.JButton								btnChangePrice;
	private javax.swing.JButton								btnDelete;
	private TextPopup<Employee>								txtEmployee;
	private WarehousesJComboBox								cbWarehouse;
	private TextPopup								txtCustomer;
	private javax.swing.JButton								btnPayOnce;
	private javax.swing.JButton								btnPrintInvoice;
	private javax.swing.JButton								btnLuuHoaDon;
	private javax.swing.JButton								btnThietLapBh;
	private javax.swing.JButton								btnNhapXuatKho;
	private javax.swing.JButton								btnDiscountProduct;
	private javax.swing.JButton								btnThemSP;
	private javax.swing.JButton								btnThemNhomSP;
	private javax.swing.JButton								btnXoaSP;
	private javax.swing.JButton								btnMayIn;
	private javax.swing.JButton								btnPayment;
	private javax.swing.JButton								buttonC;
	private javax.swing.JButton								btnExit;
	private javax.swing.JButton								btnLamTron;
	private javax.swing.JCheckBox							chbPoint;
	private javax.swing.JCheckBox							chbCredit;
	private javax.swing.JCheckBox							chbWarehouse;
	private javax.swing.JPanel								CONTAINER_CENTER;
	private javax.swing.JPanel								CONTAINER_LEFT;
	private javax.swing.JPanel								CONTAINER_RIGHT;
	private javax.swing.JScrollPane						ScrollPane_PanelPayment;
	private javax.swing.JPanel								panelTop2;
	private javax.swing.JTextField						lableDate;
	private javax.swing.JTextField						lableTime;
	private javax.swing.JLabel								lbNote;
	private javax.swing.JLabel								lblKM;
	private PanelBackground										PanelBackground;
	private javax.swing.JPanel								panelColor;
	private javax.swing.JPanel								panelNotes;
	private javax.swing.JPanel								panelTopRight;
	private javax.swing.JTextField						txtNameInvoice;
	private javax.swing.JLabel								lblHkt;
	private TableModelInvoiceItem							tableModel;
	private double														quantity						= 1;
	private PanelPayment											panelPayment;
	private DateFormat												dfDate							= new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat												dfTime							= new SimpleDateFormat("HH:mm");
	private DateFormat												dfCode							= new SimpleDateFormat("yyyyMMddHHmmss");
	private boolean														flag;
	private String														pricingType					= "";
	private String														currencyCode				= "VND";
	private PanelTextKeyboard									panelTextKeyboard;
	private JButton														btnBanPhimAo;
	private JLabel														lbDiscount;
	
	// Variable Panel Left
	private JCheckBox													chbTimKiem;
	private ExtendJTextField									txtTextFieldTimKiem;
	private TextPopup<Product>								txtTextPopupTimKiem;
	private ExtendJTextField									txtQuantity;
	private JPanel														panelProductTagRoot;
	private JPanel														panelNumber;
	private JPanel														panelProducts;
	private JScrollPane												scrollPane_Product;
	private JPanel														panelButtonLeft;
	private JPanel														panelSearchTextPopup;
	
	// Toolket ScreenSize
	private Dimension													screenSize;
	private Dimension													sizeScrollPaneLeft;
	private int																mouseCountClick			= 2;
	
	// Xu ly nghiep vu
	private ComponentTable										componentTable;
	private Color															colorProductTag			= new Color(252, 204, 4);
	private Color															colorProduct				= new Color(152, 200, 8);
	private Color															colorProductSelect	= new Color(252, 124, 4);
	
	public HashMap<String, ComponentProduct>	hashMapProducts;
	private HashMap<String, Object>						hashMapProductTagRoot;
	private JLabel														selectedProductTag2;
	private ComponentProduct									selectedProduct;
	private Table															table;
	private int																statusInvoice				= 0;
	public String															CHECKIMPORT					= "checkImport";
	private InvoiceDetail											invoiceDetail;
	
	private String														type;
	private String														name;
	private ActivityType											activityType;
	
	public static final String								LichTrinh						= "lichtrinh";
	public static final String								TraGop							= "tragop";
	public static final String								ManagerCredit				= "credit";
	public static final String								ManagerPoint				= "point";
	public static final String								ComboBoxCuaHang			= "cbCuaHang";
	private Profile														profile;
	private DialogScreenOftenPos											screenSalePOS				= DialogScreenOftenPos.getInstance();
	private static DialogPhieuDatMuaHangPos		instance;
	private boolean exit;
	private boolean printAgain;
	
	public static DialogPhieuDatMuaHangPos getInstance() {
		if (instance == null) {
			instance = new DialogPhieuDatMuaHangPos();
		}
		return instance;
	}
	
	public void setTypeInvoice(InvoiceDetail invoiceDetail, int statusInvoice, String name) throws Exception {
		if(invoiceDetail!=null && invoiceDetail.getId()!=null){
			exit = true;
		}else {
			exit = false;
		}
		this.name = name;
		this.type = invoiceDetail.getType();
		this.activityType = invoiceDetail.getActivityType();
		
		if (name.equals("PDH")) {
			lblHkt.setText("PHIẾU MUA HÀNG");
			chbWarehouse.setText("Nhập");
			btnNhapXuatKho.setText("<html><p align='center'>Nhập kho</p></html>");
		} else if (name.equals("KDH")) {
			lblHkt.setText("KHÁCH ĐẶT HÀNG");
			chbWarehouse.setText("Xuất");
			btnNhapXuatKho.setText("<html><p align='center'>Xuất kho</p></html>");
		} else if (name.equals("Phiếu trả hàng")) {
			lblHkt.setText("PHIẾU TRẢ HÀNG");
			chbWarehouse.setText("Xuất");
			btnNhapXuatKho.setText("<html><p align='center'>Xuất kho</p></html>");
		} else {
			lblHkt.setText("KHÁCH TRẢ HÀNG");
			chbWarehouse.setText("Nhập");
			btnNhapXuatKho.setText("<html><p align='center'>Nhập kho</p></html>");
		}
		
		if (invoiceDetail.getId() != null) {
			panelPayment.getTxtInvoiceCode().setText(invoiceDetail.codeView());
			this.statusInvoice = statusInvoice;
			this.invoiceDetail = invoiceDetail;
			
			currencyCode = invoiceDetail.getCurrencyUnit();
			panelPayment.setCurrencyCode(currencyCode);
			
			Table table = RestaurantModelManager.getInstance().getTableByCode(invoiceDetail.getTableCode());
			tableModel.setData(invoiceDetail);
			tableModel.changeCaculate("", "");
			tableModel.setInfoInvoice("", pricingType, table);
			setInfoInvoice();
		} else {
			pricingType = screenSalePOS.getPricingType();
			currencyCode = screenSalePOS.getCurrencyCode();
			initInvoiceDetail();
		}
		resetGuiConfig();
		loadData();
	}
	
	public DialogPhieuDatMuaHangPos() {
		this.setUndecorated(true);
		this.setModal(true);
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		DialogScreenOftenPos.setFlagScreenOften(false);
		this.name = "";
		panelPayment = new PanelPayment();
		tableModel = panelPayment.getTableModel();
		
		initComponents();
		
		ScrollPane_PanelPayment.setViewportView(panelPayment);
		ScrollPane_PanelPayment.setOpaque(false);
		ScrollPane_PanelPayment.getViewport().setOpaque(false);
		ScrollPane_PanelPayment.setWheelScrollingEnabled(false);
		
		txtCustomer.addObserver(this);
		txtEmployee.addObserver(this);
		
		lableDate.setText(dfDate.format(new Date()));
		lableTime.setText(dfTime.format(new Date()));
		
		if (screenSalePOS.getSelectedProduct() != null)
			this.selectedProduct = screenSalePOS.getSelectedProduct();
		if (screenSalePOS.getSelectedProductTag2() != null)
			this.selectedProductTag2 = screenSalePOS.getSelectedProductTag2();
		
		addKeyBindings(PanelBackground);
		addVituarKey(PanelBackground);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				try {
					System.out.println("opendScreen   " + new JVMEnv().getMemoryInfo());
				} catch (Exception e2) {
				}
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				new GuiModelManager().getObservable().deleteObservers();
				System.out.println("closeScreen   " + new JVMEnv().getMemoryInfo());
			}
		});
		resetGuiConfig();
		loadData();
		if (name.equals("PDH")) {
      txtNameInvoice.setText("Cty đặt hàng");
		} else if (name.equals("KDH")) {
			txtNameInvoice.setText("Khách đặt hàng");
		} else if (name.equals("Phiếu trả hàng")) {
			txtNameInvoice.setText("Cty trả hàng");
		} else {
			txtNameInvoice.setText("Khách trả hàng");
		}
	}
	
	public DialogPhieuDatMuaHangPos(ActivityType activityType, String type, String name) {
		this.setUndecorated(true);
		this.setModal(true);
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		
		this.type = type;
		this.activityType = activityType;
		this.name = name;
		DialogScreenOftenPos.setFlagScreenOften(false);
		
		panelPayment = new PanelPayment();
		tableModel = panelPayment.getTableModel();
		pricingType = screenSalePOS.getPricingType();
		currencyCode = screenSalePOS.getCurrencyCode();
		
		initComponents();
		
		ScrollPane_PanelPayment.setViewportView(panelPayment);
		ScrollPane_PanelPayment.setOpaque(false);
		ScrollPane_PanelPayment.getViewport().setOpaque(false);
		ScrollPane_PanelPayment.setWheelScrollingEnabled(false);
		
		txtCustomer.addObserver(this);
		txtEmployee.addObserver(this);
		
		lableDate.setText(dfDate.format(new Date()));
		lableTime.setText(dfTime.format(new Date()));
		
		if (screenSalePOS.getSelectedProduct() != null)
			this.selectedProduct = screenSalePOS.getSelectedProduct();
		if (screenSalePOS.getSelectedProductTag2() != null)
			this.selectedProductTag2 = screenSalePOS.getSelectedProductTag2();
		
		addKeyBindings(PanelBackground);
		addVituarKey(PanelBackground);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				try {
					System.out.println("opendScreen   " + new JVMEnv().getMemoryInfo());
				} catch (Exception e2) {
				}
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				new GuiModelManager().getObservable().deleteObservers();
				System.out.println("closeScreen   " + new JVMEnv().getMemoryInfo());
			}
		});
		resetGuiConfig();
		loadData();
		initInvoiceDetail();
		if (name.equals("PDH")) {
      txtNameInvoice.setText("Cty đặt hàng");
		} else if (name.equals("KDH")) {
			txtNameInvoice.setText("Khách đặt hàng");
		} else if (name.equals("Phiếu trả hàng")) {
			txtNameInvoice.setText("Cty trả hàng");
		} else {
			txtNameInvoice.setText("Khách trả hàng");
		}
	}
	
	public DialogPhieuDatMuaHangPos(InvoiceDetail invoiceDetail, int statusInvoice, String name) {
		this.setUndecorated(true);
		this.setModal(true);
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		
		this.name = name;
		this.type = invoiceDetail.getType();
		this.activityType = invoiceDetail.getActivityType();
		this.statusInvoice = statusInvoice;
		this.invoiceDetail = invoiceDetail;
		DialogScreenOftenPos.setFlagScreenOften(false);
		try {
			panelPayment = new PanelPayment();
			tableModel = panelPayment.getTableModel();
			pricingType = screenSalePOS.getPricingType();
			
			initComponents();
			
			ScrollPane_PanelPayment.setViewportView(panelPayment);
			ScrollPane_PanelPayment.setOpaque(false);
			ScrollPane_PanelPayment.getViewport().setOpaque(false);
			ScrollPane_PanelPayment.setWheelScrollingEnabled(false);
			
			txtCustomer.addObserver(this);
			txtEmployee.addObserver(this);
			
			lableDate.setText(dfDate.format(new Date()));
			lableTime.setText(dfTime.format(new Date()));
			
			addKeyBindings(PanelBackground);
			addVituarKey(PanelBackground);
			
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowOpened(WindowEvent e) {
					try {
						System.out.println("opendScreen   " + new JVMEnv().getMemoryInfo());
					} catch (Exception e2) {
					}
				}
				
				@Override
				public void windowClosed(WindowEvent e) {
					new GuiModelManager().getObservable().deleteObservers();
					System.out.println("closeScreen   " + new JVMEnv().getMemoryInfo());
				}
			});
			
			currencyCode = invoiceDetail.getCurrencyUnit();
			panelPayment.setCurrencyCode(currencyCode);
			
			resetGuiConfig();
			loadData();
			
			Table table = RestaurantModelManager.getInstance().getTableByCode(invoiceDetail.getTableCode());
			tableModel.setData(invoiceDetail);
			tableModel.changeCaculate("", "");
			tableModel.setInfoInvoice("", pricingType, table);
			setInfoInvoice();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadData() {
		Thread t = new Thread() {
			public void run() {
				try {
					List<Employee> list1 = HRModelManager.getInstance().getEmployees();
					txtEmployee.setData(list1);
					List<Product> list2 = ProductModelManager.getInstance().findAllProducts();
					txtTextPopupTimKiem.setData(list2);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if (activityType.equals(Invoice.ActivityType.Payment)) {
					txtCustomer = new TextPopup<Supplier>(Supplier.class);
					try {
						txtCustomer.setData(SupplierModelManager.getInstance().getAllSuppliers());
					} catch (Exception e) {
					}
				} else {
					txtCustomer = new TextPopup<Customer>(Customer.class);
					try {
						txtCustomer.setData(CustomerModelManager.getInstance().getCustomers(false));
					} catch (Exception e) {
					}
				}
				
				panelPayment.loadCboTax();
			};
		};
		t.start();
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
	public void resetGuiConfig() {
		try {
			// Lấy thiết lập chọn bảng giá
			boolean flagRefeshProduct = false;
			profile = AccountModelManager.getInstance().getProfileConfigAdmin();
			try {
				// Nếu thiết lập chọn bảng giá theo khu vực
				if (profile.get(DialogConfig.TbArea).equals(true)) {
					if (tableModel != null && tableModel.getInvoiceDetail() != null && !tableModel.getInvoiceDetail().equals("")) {
						Location location = RestaurantModelManager.getInstance().getLocationByCode(tableModel.getInvoiceDetail().getLocationCode());
						if (location != null && location.getLocationAttributes() != null && location.getLocationAttributes().size() > 0) {
							for (LocationAttribute a : location.getLocationAttributes()) {
								if (a.getName().equals("ProductPriceType")) {
									if (pricingType != null && !pricingType.equals(a.getValue())) {
										flagRefeshProduct = true;
										pricingType = a.getValue();
										break;
									}
								}
							}
							if (!flagRefeshProduct) {
								String pricingTypeNew = profile.get(DialogConfig.ProductPriceType).toString();
								if (pricingType != null && !pricingType.equals(pricingTypeNew)) {
									flagRefeshProduct = true;
									pricingType = pricingTypeNew;
								}
							}
						}
					}
				}
				// Nếu mặc định chọn bảng giá thiết lập
				else {
					String pricingTypeNew = profile.get(DialogConfig.ProductPriceType).toString();
					if (pricingType != null && !pricingType.equals(pricingTypeNew)) {
						flagRefeshProduct = true;
						pricingType = pricingTypeNew;
					}
				}
			} catch (Exception e) {
				//				e.printStackTrace();
			}
			
			// Lấy thiết lập chọn tiền tệ
			if (profile.get(DialogConfig.Currency) != null) {
				try {
					String currencyCodeNew = profile.get(DialogConfig.Currency).toString();
					if (!currencyCodeNew.equals(currencyCode)) {
						currencyCode = currencyCodeNew;
						panelPayment.setCurrencyCode(currencyCode);
						flagRefeshProduct = true;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// Load lại giá giao diện Panel giá sản phẩm nếu có thay đổi giá và tiền tệ trong thiết lập config
			if (flagRefeshProduct && panelProducts != null) {
				panelPayment.setCurrencyCode(currencyCode);
				tableModel.setInfoInvoice("", pricingType, table);
				
				Thread t = new Thread() {
					public void run() {
						try {
							List<ProductPrice> listProductPricePpt = ProductModelManager.getInstance().findByProductPriceByType(pricingType);
							List<Product> productsNotPpt = ProductModelManager.getInstance().findByNotProductPriceType(pricingType);
							
							for (ProductPrice pp : listProductPricePpt) {
								ComponentProduct componentProduct = hashMapProducts.get(pp.getProduct().getCode());
								if (componentProduct != null) {
									componentProduct.setProductPrice(pp);
								} else {
									// Tao ra no va set vao gia
								}
							}
							
							for (Product pp : productsNotPpt) {
								ComponentProduct componentProduct = hashMapProducts.get(pp.getCode());
								if (componentProduct != null) {
									componentProduct.setProduct(pp);
									componentProduct.getLblMoney().setText(componentProduct.getLblMoney().getText() + " " + currencyCode);
								} else {
									// Tao ra no va set vao gia
								}
							}
							
							// Cập nhật lại giao diện hiển thị
							panelProductTagRoot.updateUI();
							panelProducts.updateUI();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					};
				};
				t.start();
			}
			
			// Load lại nhóm sản phẩm cấp 2 khi chọn checkBox nhóm cấp 1 ở config
			if (hashMapProductTagRoot != null && panelProductTagRoot != null) {
				// Lấy ra list danh sách mới hiện tại
				List<String> tagProductTag = (ArrayList<String>) profile.get(DialogConfig.ProductTag);
				// Xem file config có nhóm nào được chọn hay không?
				// Nếu có thì load nhóm đó vào
				if (tagProductTag != null && tagProductTag.size() > 0) {
					// Lấy ra list danh sách cũ
					List<String> list = (ArrayList<String>) hashMapProductTagRoot.get(DialogConfig.ProductTag);
					// Nếu list danh sách cũ khác list mới hiện tại thì thay đổi lại giao
					// diện
					if (list == null || list.size() != tagProductTag.size() || list != tagProductTag) {
						int countCell = (screenSize.width <= 1024) ? 6 : 9;
						panelProductTagRoot.removeAll();
						panelProductTagRoot.add((JLabel) hashMapProductTagRoot.get("allProduct"));
						for (String tag : tagProductTag) {
							List<JLabel> labelProductTagC2 = (ArrayList<JLabel>) hashMapProductTagRoot.get(tag);
							for (JLabel l : labelProductTagC2) {
								panelProductTagRoot.add(l);
								if (panelProductTagRoot.getComponentCount() <= countCell) {
									l.setVisible(true);
								} else {
									l.setVisible(false);
								}
							}
						}
						if (panelProductTagRoot.getComponentCount() < countCell) {
							panelProductTagRoot.setLayout(new FlowLayout(FlowLayout.LEADING, 2, 0));
						} else {
							panelProductTagRoot.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 0));
						}
						panelProductTagRoot.setName("1:" + (countCell - 1));
						panelProductTagRoot.updateUI();
					}
					
				}
				// Nếu không nhóm nào được chọn thì xóa hết khỏi Panel
				else {
					panelProductTagRoot.removeAll();
					panelProductTagRoot.updateUI();
				}
			}
			
//			if (profile.get(DialogConfig.InHuyMon).toString().equals("true")) {
//				btnNhapXuatKho.setEnabled(true);
//			} else {
//				btnNhapXuatKho.setEnabled(false);
//			}
			
//			if (profile.get(DialogConfig.InCheBien).toString().equals("true")) {
//				btnLuuHoaDon.setEnabled(true);
//			} else {
//				btnLuuHoaDon.setEnabled(false);
//			}
			
//			if (profile.get(DialogConfig.TraSau).toString().equals("true")) {
//				btnPromotions.setEnabled(true);
//			} else {
//				btnPromotions.setEnabled(false);
//			}
			
			if (profile.get(DialogConfig.TaoSP).toString().equals("true")) {
				btnThemSP.setEnabled(true);
			} else {
				btnThemSP.setEnabled(false);
			}
			
			if (profile.get(DialogConfig.TTLe).toString().equals("true")) {
				btnPayOnce.setEnabled(true);
			} else {
				btnPayOnce.setEnabled(false);
			}
			
//			if (profile.get(DialogConfig.PrintReceip).toString().equals("true")) {
//				btnPrintInvoice.setEnabled(true);
//			} else {
//				btnPrintInvoice.setEnabled(false);
//			}
			
			if (profile.get(DialogConfig.Paymen).toString().equals("true")) {
				btnPayment.setEnabled(true);
			} else {
				btnPayment.setEnabled(false);
			}
			
//			if (profile.get(DialogConfig.SoLuong).toString().equals("true")) {
//				btnSoLuong.setEnabled(true);
//			} else {
//				btnSoLuong.setEnabled(false);
//			}
//			
//			if (profile.get(DialogConfig.DoiGia).toString().equals("true")) {
//				btnChangePrice.setEnabled(true);
//			} else {
//				btnChangePrice.setEnabled(false);
//			}
//			
//			if (profile.get(DialogConfig.XoaSP).toString().equals("true")) {
//				btnDelete.setEnabled(true);
//			} else {
//				btnDelete.setEnabled(false);
//			}
//			
//			if (profile.get(DialogConfig.HuySP).toString().equals("true")) {
//				btnCancel.setEnabled(true);
//			} else {
//				btnCancel.setEnabled(false);
//			}
			
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
			
			if (profile.get(DialogConfig.Keyboard) != null && profile.get(DialogConfig.Keyboard).toString().equals("true")) {
				btnBanPhimAo.setEnabled(true);
				mouseCountClick = 1;
			} else {
				btnBanPhimAo.setEnabled(false);
				mouseCountClick = 2;
			}
			panelPayment.refeshGui();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void INIT_RIGHT_CONTAINER() {
		ScrollPane_PanelPayment = new javax.swing.JScrollPane();
		txtNameInvoice = new javax.swing.JTextField();
		btnPayment = new javax.swing.JButton();
		btnPrintInvoice = new javax.swing.JButton();
		btnPayOnce = new javax.swing.JButton();
		btnLuuHoaDon = new javax.swing.JButton();
		btnNhapXuatKho = new javax.swing.JButton();
		btnPromotions = new javax.swing.JButton();
		lableDate = new javax.swing.JTextField();
		lableTime = new javax.swing.JTextField();
		txtEmployee = new TextPopup<Employee>(Employee.class);
		cbWarehouse = new WarehousesJComboBox();
		txtCustomer = new TextPopup<Customer>(Customer.class);
		txtEmployee.setText("");
		txtCustomer.setText("");
		
		txtCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				if (evt.getClickCount() >= 2) {
					choiseCustomer();
				}
			}
		});
		
		lableDate.setFont(new java.awt.Font("Tahoma", 0, 12));
		lableDate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		lableDate.setText("dd/MM/yyyy");
		
		lableTime.setFont(new java.awt.Font("Tahoma", 0, 12));
		lableTime.setPreferredSize(new Dimension(40, 22));
		lableTime.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		lableTime.setText("HH:mm");
		
		JPanel panelDateTime = new javax.swing.JPanel();
		panelDateTime.setLayout(new BorderLayout(5, 0));
		panelDateTime.setOpaque(false);
		panelDateTime.add(lableDate, BorderLayout.CENTER);
		panelDateTime.add(lableTime, BorderLayout.LINE_END);
		
		btnPayment.setText("<html><p align='center'>Thanh toán</p></html>");
		btnPayment.setName("btnPaymen");
		btnPayment.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnPayment.addMouseListener(new MouseEventClickButtonDown(null, null, null));
		btnPayment.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnPayment.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (chbWarehouse.isSelected()) {
					statusInvoice = PanelPhieuDatMuaHang.PAID;
					saveInvoice(PanelPhieuDatMuaHang.PAID);
				} else {
					statusInvoice = PanelPhieuDatMuaHang.PAID_AND_NOTIMPORT;
					saveInvoice(PanelPhieuDatMuaHang.PAID_AND_NOTIMPORT);
				}
			}
		});
		
		btnPrintInvoice.setText("<html><p align='center'>In hóa đơn</p></html>");
		btnPrintInvoice.setName("btnPrintInvoice");
		btnPrintInvoice.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnPrintInvoice.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnPrintInvoice.addMouseListener(new MouseEventClickButtonDown(null, null, null));
		btnPrintInvoice.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnActionPrintInvoice();
			}
		});
		
		btnPayOnce.setText("<html><p align='center'>Thanh toán lẻ</p></html>");
		btnPayOnce.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnPayOnce.addMouseListener(new MouseEventClickButtonDown(null, null, null));
		btnPayOnce.setName("btnPayOnce");
		btnPayOnce.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnActionPayOnce();
			}
		});
		
		btnLuuHoaDon.setText("<html><p align='center'>Lưu</p></html>");
		btnLuuHoaDon.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnLuuHoaDon.addMouseListener(new MouseEventClickButtonDialogPlus(null, null, null));
		btnLuuHoaDon.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				statusInvoice = PanelPhieuDatMuaHang.UNPAID_AND_NOTIMPORT;
				saveInvoice(PanelPhieuDatMuaHang.UNPAID_AND_NOTIMPORT);
			}
		});
		
		btnNhapXuatKho.setText("<html><p align='center'>" + (activityType == ActivityType.Payment ? "Nhập" : "Xuất") + " kho</p></html>");
		btnNhapXuatKho.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnNhapXuatKho.addMouseListener(new MouseEventClickButtonDialogPlus(null, null, null));
		btnNhapXuatKho.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				statusInvoice = PanelPhieuDatMuaHang.UNPAID;
				saveInvoice(PanelPhieuDatMuaHang.UNPAID);
			}
		});
		
		btnPromotions.setText("<html><p align='center'>Xem KM</p></html>");
		btnPromotions.setName("btnPromotions");
		btnPromotions.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnPromotions.addMouseListener(new MouseEventClickButtonDown(null, null, null));
		btnPromotions.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				TableListViewPromotion tbviewpromotion = new TableListViewPromotion();
				tbviewpromotion.setName("tbviewpromotion");
				DialogList dialog = new DialogList(tbviewpromotion);
				dialog.setTitle("Xem khuyến mại");
				dialog.setName("tbviewpromotion");
				dialog.setVisible(true);
			}
		});
		
		PanelColor();
		
		panelTopRight = new JPanel();
		panelTopRight.setLayout(new GridLayout(3, 1, 0, 5));
		panelTopRight.setBackground(Color.WHITE);
		panelTopRight.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		JPanel panelTop3 = new JPanel();
		panelTop3.setBackground(Color.WHITE);
		panelTop3.setLayout(new BorderLayout(0, 0));
		JLabel lblNameInvoice = new JLabel("Tên phiếu");
		lblNameInvoice.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNameInvoice.setPreferredSize(new Dimension(70, 22));
		panelTop3.add(lblNameInvoice, BorderLayout.WEST);
		panelTop3.add(txtNameInvoice, BorderLayout.CENTER);
		panelTopRight.add(panelTop3);
		JPanel panelTop1 = new JPanel();
		panelTop1.setBackground(Color.WHITE);
		panelTop1.setLayout(new GridLayout(1, 2, 5, 0));
		JPanel panelT1 = new JPanel();
		panelT1.setBackground(Color.WHITE);
		panelT1.setLayout(new BorderLayout());
		JLabel lableEmployee = new JLabel("Thu ngân");
		lableEmployee.setFont(new Font("Tahoma", 0, 12));
		lableEmployee.setPreferredSize(new Dimension(70, 22));
		panelT1.add(lableEmployee, BorderLayout.WEST);
		panelT1.add(txtEmployee, BorderLayout.CENTER);
		JPanel panelT11 = new JPanel();
		panelT11.setBackground(Color.WHITE);
		panelT11.setLayout(new BorderLayout());
		JLabel lablePartner = new JLabel("Khách hàng");
		lablePartner.setFont(new Font("Tahoma", 0, 12));
		lablePartner.setPreferredSize(new Dimension(70, 22));
		panelT11.add(lablePartner, BorderLayout.WEST);
		panelT11.add(txtCustomer, BorderLayout.CENTER);
		panelTop1.add(panelT1);
		panelTop1.add(panelT11);
		panelTopRight.add(panelTop1);
		JPanel panelTop2 = new JPanel();
		panelTop2.setBackground(Color.WHITE);
		panelTop2.setLayout(new GridLayout(1, 2, 5, 0));
		JPanel panelT2 = new JPanel();
		panelT2.setBackground(Color.WHITE);
		panelT2.setLayout(new BorderLayout());
		chbWarehouse = new JCheckBox((activityType == ActivityType.Payment) ? "Nhập" : "Xuất");
		chbWarehouse.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chbWarehouse.setPreferredSize(new Dimension(70, 22));
		chbWarehouse.setOpaque(false);
		panelT2.add(chbWarehouse, BorderLayout.WEST);
		panelT2.add(cbWarehouse, BorderLayout.CENTER);
		JPanel panelT22 = new JPanel();
		panelT22.setBackground(Color.WHITE);
		panelT22.setLayout(new BorderLayout());
		JLabel labelDateTime = new JLabel("Thời gian");
		labelDateTime.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelDateTime.setPreferredSize(new Dimension(70, 22));
		panelT22.add(labelDateTime, BorderLayout.WEST);
		panelT22.add(panelDateTime, BorderLayout.CENTER);
		panelTop2.add(panelT2);
		panelTop2.add(panelT22);
		panelTopRight.add(panelTop2);
		
		JPanel GrossPanelTop = new JPanel(new BorderLayout(0, 0));
		GrossPanelTop.add(panelTopRight, BorderLayout.NORTH);
		GrossPanelTop.add(panelColor, BorderLayout.SOUTH);
		panelColor.setVisible(false);
		
		JPanel Panel_Bottom = new JPanel(new GridLayout(2, 3, 3, 3));
		Panel_Bottom.setOpaque(false);
		Panel_Bottom.setPreferredSize(new Dimension(Panel_Bottom.getSize().width, 90));
		Panel_Bottom.add(btnLuuHoaDon);
		Panel_Bottom.add(btnNhapXuatKho);
		Panel_Bottom.add(btnPrintInvoice);
		Panel_Bottom.add(btnPromotions);
		Panel_Bottom.add(btnPayOnce);
		Panel_Bottom.add(btnPayment);
		
		CONTAINER_RIGHT.setLayout(new BorderLayout(0, 0));
		CONTAINER_RIGHT.setOpaque(false);
		CONTAINER_RIGHT.add(GrossPanelTop, BorderLayout.NORTH);
		CONTAINER_RIGHT.add(ScrollPane_PanelPayment, BorderLayout.CENTER);
		CONTAINER_RIGHT.add(Panel_Bottom, BorderLayout.SOUTH);
		
		if (AccountModelManager.getInstance().getProfileConfigAdmin().get(CHECKIMPORT) != null && AccountModelManager.getInstance().getProfileConfigAdmin().get(CHECKIMPORT).equals("true")) {
			chbWarehouse.setSelected(true);
			// statusInvoice = PAID;
		} else {
			chbWarehouse.setSelected(false);
			// statusInvoice = PAID_AND_NOTIMPORT;
		}
		
		chbWarehouse.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (chbWarehouse.isSelected()) {
					profile.put(CHECKIMPORT, "true");
					statusInvoice = PanelPhieuDatMuaHang.PAID;
				} else {
					profile.put(CHECKIMPORT, "false");
					statusInvoice = PanelPhieuDatMuaHang.PAID_AND_NOTIMPORT;
				}
				AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(), profile);
			}
		});
	}
	
	private void INIT_LEFT_CONTAINER() {
		/** Thiết lập các phần tử chứa */
		CONTAINER_LEFT.setLayout(new BorderLayout(5, 5));
		CONTAINER_LEFT.setOpaque(false);
		
		/********************************************************************************
		 ************************* Khởi tạo các phần tử con PANEL TOP *******************
		 ********************************************************************************/
		
		final JPanel PanelTOP = new JPanel(new BorderLayout(0, 5));
		// ------------------------------------------------------------------
		// ----------------------PANEL TOP 1---------------------------------
		// ------------------------------------------------------------------
		JPanel panelTop1 = new JPanel(new BorderLayout(0, 0));
		panelTop1.setOpaque(false);
		JPanel panelTop1_child1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelTop1_child1.setOpaque(false);
		lblHkt = new ExtendJLabel("HKT RESTO");
		lblHkt.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblHkt.setPreferredSize(new Dimension(140, 23));
		chbTimKiem = new ExtendJCheckBox("Tìm(F12)");
		txtTextFieldTimKiem = new ExtendJTextField();
		txtTextFieldTimKiem.setPreferredSize(new Dimension(300, 23));
		txtTextPopupTimKiem = new TextPopup<Product>(Product.class);
		txtTextPopupTimKiem.setPreferredSize(new Dimension(170, 23));
		txtQuantity = new ExtendJTextField();
		txtQuantity.setPreferredSize(new Dimension(70, 23));
		txtQuantity.setHorizontalAlignment(SwingConstants.RIGHT);
		ExtendJLabel lblSoLuong = new ExtendJLabel("Số lượng");
		lblSoLuong.setPreferredSize(new Dimension(60, 23));
		lblSoLuong.setHorizontalAlignment(SwingConstants.CENTER);
		lblSoLuong.setFont(new Font("Tahoma", 0, 12));
		panelSearchTextPopup = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panelSearchTextPopup.setOpaque(false);
		panelSearchTextPopup.add(txtTextPopupTimKiem);
		panelSearchTextPopup.add(lblSoLuong);
		panelSearchTextPopup.add(txtQuantity);
		panelSearchTextPopup.setPreferredSize(new Dimension(300, 23));
		JPanel pp = new JPanel(new BorderLayout(0, 0));
		pp.setOpaque(false);
		pp.add(panelSearchTextPopup, BorderLayout.CENTER);
		pp.add(txtTextFieldTimKiem, BorderLayout.WEST);
		pp.setPreferredSize(new Dimension(300, 23));
		panelTop1_child1.add(chbTimKiem);
		panelTop1_child1.add(pp);
		panelTop1.add(lblHkt, BorderLayout.WEST);
		panelTop1.add(panelTop1_child1, BorderLayout.CENTER);
		
		panelSearchTextPopup.setVisible(false);
		txtTextPopupTimKiem.addObserver(this);
		txtTextPopupTimKiem.setComponentFocus(txtQuantity);
		
		chbTimKiem.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (chbTimKiem.isSelected()) {
					txtTextFieldTimKiem.setVisible(false);
					panelSearchTextPopup.setVisible(true);
					
				} else {
					txtTextFieldTimKiem.setVisible(true);
					panelSearchTextPopup.setVisible(false);
				}
			}
		});
		
		txtTextFieldTimKiem.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				if (selectedProductTag2 != null) {
					selectedProductTag2.setBackground(Color.ORANGE);
					selectedProductTag2 = null;
				}
				
				int totalCell = 0;
				for (int i = 0; i < panelProducts.getComponentCount(); i++) {
					ComponentProduct panel = (ComponentProduct) panelProducts.getComponent(i);
					if (panel.isProduct() && panel.getLblNameProduct().getText().trim().toLowerCase().contains(txtTextFieldTimKiem.getText().trim().toLowerCase())) {
						panel.setVisible(true);
						totalCell++;
					} else {
						panel.setVisible(false);
					}
				}
				
				// Tính toán lại kích thước cho PanelProducts trong scrollPane
				loadSizePanelProduct(totalCell);
			}
		});
		
		// -----------------------------------------------------------------------------------
		// -----------------------------------PANEL TOP 2------------------------------------
		// -----------------------------------------------------------------------------------
		panelTop2 = new JPanel(new BorderLayout(0, 0));
		panelTop2.setOpaque(false);
		panelTop2.setPreferredSize(new Dimension(100, 50));
		hashMapProductTagRoot = screenSalePOS.getHashMapProductTagRoot();
		panelProductTagRoot = screenSalePOS.getPanelProductTagRoot();
		final ExtendJLabel lblNextProduct = new ExtendJLabel("");
		lblNextProduct.setPreferredSize(new Dimension(30, 50));
		lblNextProduct.setHorizontalAlignment(SwingConstants.CENTER);
		try {
			BufferedImage img = ImageIO.read(HomeScreen.class.getResource("icon/button_next.jpg"));
			Image dimg = img.getScaledInstance(30, 50, Image.SCALE_SMOOTH);
			ImageIcon imageIcon = new ImageIcon(dimg);
			lblNextProduct.setIcon(imageIcon);
		} catch (Exception ex) {
			lblNextProduct.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/button_next.jpg")));
		}
		final ExtendJLabel lblBackProduct = new ExtendJLabel("");
		lblBackProduct.setPreferredSize(new Dimension(30, 50));
		lblBackProduct.setHorizontalAlignment(SwingConstants.CENTER);
		try {
			BufferedImage img = ImageIO.read(HomeScreen.class.getResource("icon/button_back.jpg"));
			Image dimg = img.getScaledInstance(30, 50, Image.SCALE_SMOOTH);
			ImageIcon imageIcon = new ImageIcon(dimg);
			lblBackProduct.setIcon(imageIcon);
		} catch (Exception ex) {
			lblBackProduct.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/button_back.jpg")));
		}
		panelTop2.add(lblBackProduct, BorderLayout.WEST);
		panelTop2.add(panelProductTagRoot, BorderLayout.CENTER);
		panelTop2.add(lblNextProduct, BorderLayout.EAST);
		
		lblBackProduct.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					BufferedImage img = ImageIO.read(HomeScreen.class.getResource("icon/button_back.jpg"));
					Image dimg = img.getScaledInstance(30, 50, Image.SCALE_SMOOTH);
					ImageIcon imageIcon = new ImageIcon(dimg);
					lblBackProduct.setIcon(imageIcon);
				} catch (Exception ex) {
					lblBackProduct.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/button_back.jpg")));
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					BufferedImage img = ImageIO.read(HomeScreen.class.getResource("icon/button_back2.jpg"));
					Image dimg = img.getScaledInstance(30, 50, Image.SCALE_SMOOTH);
					ImageIcon imageIcon = new ImageIcon(dimg);
					lblBackProduct.setIcon(imageIcon);
				} catch (Exception ex) {
					lblBackProduct.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/button_back2.jpg")));
				}
				String[] split = panelProductTagRoot.getName().toString().split(":");
				int indexFrom = Integer.parseInt(split[0]);
				int indexTo = Integer.parseInt(split[1]);
				if (indexFrom > 1) {
					panelProductTagRoot.getComponent(indexFrom - 1).setVisible(true);
					panelProductTagRoot.getComponent(indexTo).setVisible(false);
					panelProductTagRoot.setName(((indexFrom - 1) + ":" + (indexTo - 1)).toString());
				}
			}
		});
		
		lblNextProduct.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					BufferedImage img = ImageIO.read(HomeScreen.class.getResource("icon/button_next.jpg"));
					Image dimg = img.getScaledInstance(30, 50, Image.SCALE_SMOOTH);
					ImageIcon imageIcon = new ImageIcon(dimg);
					lblNextProduct.setIcon(imageIcon);
				} catch (Exception ex) {
					lblNextProduct.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/button_next.jpg")));
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					BufferedImage img = ImageIO.read(HomeScreen.class.getResource("icon/btt_next2.jpg"));
					Image dimg = img.getScaledInstance(30, 50, Image.SCALE_SMOOTH);
					ImageIcon imageIcon = new ImageIcon(dimg);
					lblNextProduct.setIcon(imageIcon);
				} catch (Exception ex) {
					lblNextProduct.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/btt_next2.jpg")));
				}
				String[] split = panelProductTagRoot.getName().toString().split(":");
				int indexFrom = Integer.parseInt(split[0]);
				int indexTo = Integer.parseInt(split[1]);
				if (indexTo < (panelProductTagRoot.getComponentCount() - 1)) {
					panelProductTagRoot.getComponent(indexFrom).setVisible(false);
					panelProductTagRoot.getComponent(indexTo + 1).setVisible(true);
					panelProductTagRoot.setName(((indexFrom + 1) + ":" + (indexTo + 1)).toString());
				}
			}
		});
		
		// ------------------------------------------------------------------
		// ----------------------------PANEL TOP 3---------------------------
		// ------------------------------------------------------------------
		panelNumber = new JPanel(new GridLayout(1, 10, 5, 0));
		panelNumber.setOpaque(false);
		panelNumber.setPreferredSize(new Dimension(100, 30));
		
		JButton btnNumber1 = new JButton("1");
		JButton btnNumber2 = new JButton("2");
		JButton btnNumber3 = new JButton("3");
		JButton btnNumber4 = new JButton("4");
		JButton btnNumber5 = new JButton("5");
		JButton btnNumber6 = new JButton("6");
		JButton btnNumber7 = new JButton("7");
		JButton btnNumber8 = new JButton("8");
		JButton btnNumber9 = new JButton("9");
		JButton btnNumberC = new JButton("C");
		
		btnNumberC.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (panelPayment.getTable_Sales1().getSelectedRow() >= 0) {
					tableModel.setValueAt(1, panelPayment.getTable_Sales1().getSelectedRow(), 2);
				}
			}
		});
		
		btnNumber1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (panelPayment.getTable_Sales1().getSelectedRow() >= 0) {
					tableModel.setValueAt(1, panelPayment.getTable_Sales1().getSelectedRow(), 2);
				}
			}
		});
		
		btnNumber2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (panelPayment.getTable_Sales1().getSelectedRow() >= 0) {
					tableModel.setValueAt(2, panelPayment.getTable_Sales1().getSelectedRow(), 2);
				}
			}
		});
		
		btnNumber3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (panelPayment.getTable_Sales1().getSelectedRow() >= 0) {
					tableModel.setValueAt(3, panelPayment.getTable_Sales1().getSelectedRow(), 2);
				}
			}
		});
		
		btnNumber4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (panelPayment.getTable_Sales1().getSelectedRow() >= 0) {
					tableModel.setValueAt(4, panelPayment.getTable_Sales1().getSelectedRow(), 2);
				}
			}
		});
		
		btnNumber5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (panelPayment.getTable_Sales1().getSelectedRow() >= 0) {
					tableModel.setValueAt(5, panelPayment.getTable_Sales1().getSelectedRow(), 2);
				}
			}
		});
		
		btnNumber6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (panelPayment.getTable_Sales1().getSelectedRow() >= 0) {
					tableModel.setValueAt(6, panelPayment.getTable_Sales1().getSelectedRow(), 2);
				}
			}
		});
		
		btnNumber7.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (panelPayment.getTable_Sales1().getSelectedRow() >= 0) {
					tableModel.setValueAt(7, panelPayment.getTable_Sales1().getSelectedRow(), 2);
				}
			}
		});
		
		btnNumber8.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (panelPayment.getTable_Sales1().getSelectedRow() >= 0) {
					tableModel.setValueAt(8, panelPayment.getTable_Sales1().getSelectedRow(), 2);
				}
			}
		});
		
		btnNumber9.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (panelPayment.getTable_Sales1().getSelectedRow() >= 0) {
					tableModel.setValueAt(9, panelPayment.getTable_Sales1().getSelectedRow(), 2);
				}
			}
		});
		
		btnNumber1.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNumber2.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNumber3.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNumber4.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNumber5.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNumber6.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNumber7.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNumber8.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNumber9.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNumberC.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		btnNumber1.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnNumber2.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnNumber3.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnNumber4.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnNumber5.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnNumber6.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnNumber7.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnNumber8.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnNumber9.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnNumberC.setMargin(new java.awt.Insets(0, 0, 0, 0));
		
		panelNumber.add(btnNumber1);
		panelNumber.add(btnNumber2);
		panelNumber.add(btnNumber3);
		panelNumber.add(btnNumber4);
		panelNumber.add(btnNumber5);
		panelNumber.add(btnNumber6);
		panelNumber.add(btnNumber7);
		panelNumber.add(btnNumber8);
		panelNumber.add(btnNumber9);
		panelNumber.add(btnNumberC);
		
		// ---Add các phần tử trên
		PanelTOP.add(panelTop1, BorderLayout.NORTH);
		PanelTOP.add(panelTop2, BorderLayout.CENTER);
		PanelTOP.add(panelNumber, BorderLayout.SOUTH);
		PanelTOP.setOpaque(false);
		
		/************************************************************************
		 **************** Khởi tạo các phần tử con PANEL CENTER ******************
		 *************************************************************************/
		
		hashMapProducts = screenSalePOS.getHashMapProducts();
		panelProducts = screenSalePOS.getPanelProducts();
		scrollPane_Product = new JScrollPane(panelProducts);
		scrollPane_Product.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollBar sb = scrollPane_Product.getVerticalScrollBar();
		sb.setPreferredSize(new Dimension(((screenSize.width > 1024) ? 25 : 15), 0));
		
		for (Entry<String, ComponentProduct> e : hashMapProducts.entrySet()) {
			ComponentProduct componentProduct = e.getValue();
			if (componentProduct.getLblNameProduct().getMouseListeners().length > 1) {
				break;
			} else {
				componentProduct.getLblNameProduct().addMouseListener(new EventMouseClickProduct());
				if(componentProduct.getLblMoney() != null)
					componentProduct.getLblMoney().addMouseListener(new EventMouseClickProduct());
				if(componentProduct.getLbAvatar() != null)
					componentProduct.getLbAvatar().addMouseListener(new EventMouseClickProduct());
			}
		}
		
		/*********************************************
		 ********************************************/
		/** Khởi tạo các phần tử con PANEL BOTTOM */
		
		JPanel grossPanel = new JPanel(new BorderLayout(0, 0));
		grossPanel.setOpaque(false);
		PanelNotes();
		panelButtonLeft = new JPanel(new GridLayout(1, 6, 5, 5));
		panelButtonLeft.setOpaque(false);
		grossPanel.add(panelNotes, BorderLayout.NORTH);
		grossPanel.add(panelButtonLeft, BorderLayout.SOUTH);
		panelNotes.setVisible(false);
		
		btnThemNhomSP = new JButton("<html><p align='center'>Thêm nhóm SP</p></html>");
		btnXoaSP = new JButton("<html><p align='center'>Xóa sản phẩm</p></html>");
		btnMayIn = new JButton("<html><p align='center'>Máy in</p></html>");
		btnThemSP = new JButton("<html><p align='center'>Thêm sản phẩm</p></html>");
		btnThietLapBh = new JButton("<html><p align='center'>Thiết lập BH</p></html>");
		btnBanPhimAo = new JButton("<html><p align='center'>Hiện bàn phím</p></html>");
		
		btnThemNhomSP.setPreferredSize(new Dimension(120, 45));
		btnXoaSP.setPreferredSize(new Dimension(120, 45));
		btnMayIn.setPreferredSize(new Dimension(120, 45));
		btnThemSP.setPreferredSize(new Dimension(120, 45));
		btnThietLapBh.setPreferredSize(new Dimension(120, 45));
		btnBanPhimAo.setPreferredSize(new Dimension(120, 45));
		
		btnThemNhomSP.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnXoaSP.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnMayIn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnThemSP.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnThietLapBh.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnBanPhimAo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		btnThemNhomSP.setHorizontalTextPosition(SwingConstants.CENTER);
		btnXoaSP.setHorizontalTextPosition(SwingConstants.CENTER);
		btnMayIn.setHorizontalTextPosition(SwingConstants.CENTER);
		btnThemSP.setHorizontalTextPosition(SwingConstants.CENTER);
		btnThietLapBh.setHorizontalTextPosition(SwingConstants.CENTER);
		btnBanPhimAo.setHorizontalTextPosition(SwingConstants.CENTER);
		
		btnThemNhomSP.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnXoaSP.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnMayIn.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnThemSP.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnThietLapBh.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnBanPhimAo.setMargin(new java.awt.Insets(0, 0, 0, 0));
		
		panelButtonLeft.add(btnThemNhomSP);
		panelButtonLeft.add(btnThemSP);
		panelButtonLeft.add(btnXoaSP);
		panelButtonLeft.add(btnMayIn);
		panelButtonLeft.add(btnBanPhimAo);
		panelButtonLeft.add(btnThietLapBh);
		
		btnThemNhomSP.setName("btnThemNhomSP");
		btnThemNhomSP.addMouseListener(new MouseEventClickButtonDown(null, null, null));
		btnThemNhomSP.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					CatalogProductTag cpt = new CatalogProductTag();
					cpt.setName("QuanLyNhomHang_KhoHangHoa");
					DialogMain dialog = new DialogMain(cpt);
					dialog.setIcon("nhomsp1.png");
					dialog.setName("dlNhomhang");
					dialog.setTitle("Quản lý nhóm hàng");
					dialog.setVisible(true);
					// Load gui o day
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		btnThemSP.setName("btnThemSP");
		btnThemSP.addMouseListener(new MouseEventClickButtonDown(null, null, null));
		btnThemSP.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					PanelRepositoryProducts2 panel = new PanelRepositoryProducts2();
					panel.setName("ThemHangHoaMoi");
					DialogMain dialog = new DialogMain(panel, true);
					dialog.setName("dlThemHangHoaMoi");
					dialog.setIcon("hanghoa1.png");
					dialog.setTitle("Thêm hàng hóa mới");
					dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
					dialog.setModal(true);
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
					// Load data sanpham o day
					List<Product> products = ProductModelManager.getInstance().findAllProducts();
					txtTextPopupTimKiem.setData(products);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		btnXoaSP.setName("btnXoaSP");
		btnXoaSP.addMouseListener(new MouseEventClickButtonDown(null, null, null));
		btnXoaSP.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (selectedProduct != null) {
					String productCode = selectedProduct.getProduct().getCode();
					try {
						Product product = ProductModelManager.getInstance().getProductByCode(productCode);
						List<ProductTag> productTags = new ArrayList<ProductTag>();
						if (product.getProductTags() != null) {
							productTags.addAll(product.getProductTags());
						}
						
						String str = "Xóa hàng hóa ";
						PanelChoise panel = new PanelChoise(str + product.getName() + " ?");
						panel.setName("Xoa");
						DialogResto dialog = new DialogResto(panel, false, 0, 80);
						dialog.setName("dlXoa");
						dialog.setTitle("Xóa hàng hóa");
						dialog.setLocationRelativeTo(null);
						dialog.setModal(true);
						dialog.setVisible(true);
						
						// Xóa trong CSDL
						ProductModelManager.getInstance().deleteProductByCode(productCode);
						// Xóa khỏi màn hình
						ComponentProduct componentProduct = hashMapProducts.get(productCode);
						panelProducts.remove(componentProduct);
						hashMapProducts.remove(componentProduct);
						// Cap nhat giao dien
						panelProducts.updateUI();
						scrollPane_Product.updateUI();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		
		btnMayIn.setName("btnMayIn");
		btnMayIn.addMouseListener(new MouseEventClickButtonDown(null, null, null));
		btnMayIn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
			}
		});
		
		btnBanPhimAo.setName("btnBanPhimAo");
		btnBanPhimAo.addMouseListener(new MouseEventClickButtonDown(null, null, null));
		btnBanPhimAo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (panelTextKeyboard.isVisible()) {
					btnBanPhimAo.setText("<html><p align='center'>Hiện bàn phím</p></html>");
					panelTextKeyboard.setVisible(false);
				} else {
					btnBanPhimAo.setText("<html><p align='center'>Ẩn bàn phím</p></html>");
					panelTextKeyboard.setVisible(true);
				}
			}
		});
		
		btnThietLapBh.setName("btnThietLapBh");
		btnThietLapBh.addMouseListener(new MouseEventClickButtonDown(null, null, null));
		btnThietLapBh.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				DialogConfig dialog = new DialogConfig(false, 600, 100);
				dialog.setName("dlThietLapBH");
				dialog.setTitle("Thiết lập bán hàng");
				dialog.setVisible(true);
				// Load lại giao diện bên PanelSalePOS
				if (PanelScreenSaleLocationPos.salePOS != null)
					PanelScreenSaleLocationPos.getInstance().getProfileConfigData();
				// Load lại giao diện này
				resetGuiConfig();
			}
		});
		
		CONTAINER_LEFT.add(PanelTOP, BorderLayout.NORTH);
		CONTAINER_LEFT.add(scrollPane_Product, BorderLayout.CENTER);
		CONTAINER_LEFT.add(grossPanel, BorderLayout.SOUTH);
		
	}
	
	private class EventMouseClickProduct extends MouseAdapter {
		public EventMouseClickProduct() {
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			actionMouseProduct(e);
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			actionMouseProduct(e);
		}
		
		private void actionMouseProduct(MouseEvent e) {
			if (!DialogScreenOftenPos.isFlagScreenOften()) {
				// Doi mau khi click
				if (selectedProduct != null) {
					if (selectedProduct.isProduct()) {
						selectedProduct.setBackground(colorProduct);
						selectedProduct.setBorder(BorderFactory.createLineBorder(colorProduct, 5));
					} else {
						selectedProduct.setBackground(colorProductTag);
						selectedProduct.setBorder(BorderFactory.createLineBorder(colorProductTag, 5));
					}
				}
				Container container = (((JLabel) e.getSource()).getParent());
				if (container instanceof ComponentProduct) {
					selectedProduct = (ComponentProduct) (((JLabel) e.getSource()).getParent());			
				} else {
					selectedProduct = (ComponentProduct)((JPanel) (((JLabel) e.getSource()).getParent())).getParent();	
				}
				selectedProduct.setBackground(colorProductSelect);
				selectedProduct.setBorder(BorderFactory.createLineBorder(colorProductSelect, 5));
				// Add product panel tableModel
				if (e.getClickCount() == mouseCountClick) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						try {
							addProduct(selectedProduct.getProduct());
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					} else {
						if (e.getButton() == MouseEvent.BUTTON3) {
							
						}
					}
				}
			}
		}
	}
	
	private void loadSizePanelProduct(int totalCell) {
		// Tính toán lại kích thước cho PanelProducts trong scrollPane
		Dimension sizeCell;
		int totalColumn;
		// Thiết lập số ô cell hiển thị cho kích thước màn hình
		if (screenSize.width < 1024) {
			totalColumn = 4;
			int w = sizeScrollPaneLeft.width / totalColumn;
			int h = 65;
			sizeCell = new Dimension(w - 7, h);
		} else {
			totalColumn = 5;
			int w = sizeScrollPaneLeft.width / totalColumn;
			int h = 65;
			sizeCell = new Dimension(w - 7, h);
			
			JScrollBar sb = scrollPane_Product.getVerticalScrollBar();
			sb.setPreferredSize(new Dimension(((screenSize.width > 1024) ? 25 : 15), 0));
		}
		
		int totalRow = (totalCell % totalColumn) != 0 ? ((totalCell / totalColumn) + 1) : (totalCell / totalColumn);
		int heightPanel = 5 * (totalRow + 1) + sizeCell.height * totalRow;
		if (heightPanel > sizeScrollPaneLeft.height) {
			Dimension newSize = new Dimension((sizeScrollPaneLeft.width - ((screenSize.width > 1024) ? 28 : 18)), heightPanel);
			panelProducts.setSize(newSize);
		} else {
			Dimension newSize = new Dimension(sizeScrollPaneLeft.width - 3, sizeScrollPaneLeft.height - 3);
			panelProducts.setSize(newSize);
		}
	}
	
	private void INIT_CENTER_CONTAINER() {
		buttonC = new JButton();
		btnDelete = new JButton();
		btnCancel = new JButton();
		btnSoLuong = new JButton();
		btnChangePrice = new JButton();
		btnDiscountProduct = new JButton();
		btnExit = new JButton();
		lbNote = new JLabel();
		lblKM = new JLabel();
		
		buttonC.setFont(new java.awt.Font("Tahoma", 1, 12));
		buttonC.setText("<html><p align='center'>C</p></html>");
		buttonC.setMargin(new java.awt.Insets(0, 0, 0, 0));
		buttonC.setContentAreaFilled(false);
		buttonC.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				button_CActionPerformed(evt);
			}
		});
		
		btnDelete.setFont(new java.awt.Font("Tahoma", 1, 12));
		btnDelete.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnDelete.addMouseListener(new MouseEventClickButtonDialogPlus(null, null, null));
		btnDelete.setText("<html><p align='center'>Xóa</p></html>");
		btnDelete.setName("btnDelete");
		btnDelete.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				deleteProduct();
			}
		});
		
		btnCancel.setFont(new java.awt.Font("Tahoma", 1, 12));
		btnCancel.setText("<html><p align='center'>Hủy</p></html>");
		btnCancel.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnCancel.addMouseListener(new MouseEventClickButtonDialogPlus(null, null, null));
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				canceProduct();
			}
		});
		
		btnSoLuong.setFont(new java.awt.Font("Tahoma", 1, 12));
		btnSoLuong.setText("<html><p align='center'>Số lượng</p></html>");
		btnSoLuong.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnSoLuong.addMouseListener(new MouseEventClickButtonDown(null, null, null));
		btnSoLuong.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				changeQuantityProduct();
			}
		});
		
		btnChangePrice.setFont(new java.awt.Font("Tahoma", 1, 12));
		btnChangePrice.setText("<html><p align='center'>Đổi giá</p></html>");
		btnChangePrice.setName("btnChangePrice");
		btnChangePrice.addMouseListener(new MouseEventClickButtonDialogPlus(null, null, null));
		btnChangePrice.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnChangePrice.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				changePriceProduct();
			}
		});
		
		lbNote.setFont(new java.awt.Font("Tahoma", 0, 12));
		lbNote.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lbNote.setText("<html><p align='center'>Ghi chú</p></html>");
		lbNote.addMouseListener(new MouseEventMove() {
			public void mouseClicked(MouseEvent evt) {
				boolean flagVisible = panelColor.isVisible();
				if (flagVisible) {
					panelColor.setVisible(false);
					panelNotes.setVisible(false);
					panelTopRight.setVisible(true);
					panelButtonLeft.setVisible(true);
				} else {
					panelColor.setVisible(true);
					panelNotes.setVisible(true);
					panelTopRight.setVisible(false);
					panelButtonLeft.setVisible(false);
				}
			}
		});
		
		lblKM.setFont(new java.awt.Font("Tahoma", 0, 12));
		lblKM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblKM.setText("KM");
		lblKM.addMouseListener(new MouseEventMove() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				lblKMMouseClicked(evt);
			}
		});
		
		lbDiscount = new JLabel();
		lbDiscount.setFont(new java.awt.Font("Tahoma", 0, 12));
		lbDiscount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lbDiscount.setText("Hiện CK");
		lbDiscount.addMouseListener(new MouseEventMove() {
			public void mouseClicked(MouseEvent evt) {
				if (lbDiscount.getText().equals("Hiện CK")) {
					lbDiscount.setText("Ẩn CK");
					panelPayment.setTableDiscount(true);
				} else {
					lbDiscount.setText("Hiện CK");
					panelPayment.setTableDiscount(false);
				}
			}
		});
		
		btnDiscountProduct.setFont(new java.awt.Font("Tahoma", 1, 12));
		btnDiscountProduct.setText("<html><p align='center'>CK SP</p></html>");
		btnDiscountProduct.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnDiscountProduct.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDiscountProduct();
			}
		});
		
		btnExit.setFont(new java.awt.Font("Tahoma", 1, 12));
		btnExit.setPreferredSize(new Dimension(120, 45));
		btnExit.setText("<html><p align='center'>Thoát</p></html>");
		btnExit.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnExit.addMouseListener(new MouseEventClickButtonDialogPlus(null, null, null));
		btnExit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnExitSystem();
			}
		});
		
		/** Thay đổi size panel theo kích thước màn hình */
		if (screenSize.width > 1024)
			CONTAINER_CENTER.setPreferredSize(new Dimension(90, 100));
		else if (screenSize.width == 1024)
			CONTAINER_CENTER.setPreferredSize(new Dimension(80, 100));
		else if (screenSize.width < 1024)
			CONTAINER_CENTER.setPreferredSize(new Dimension(70, 100));
		
		CONTAINER_CENTER.setLayout(new BorderLayout(5, 5));
		CONTAINER_CENTER.setOpaque(false);
		
		JPanel topCenter = new JPanel();
		topCenter.setLayout(new GridLayout(2, 1, 5, 5));
		topCenter.setPreferredSize(new Dimension(topCenter.getSize().width, 130));
		topCenter.setBackground(Color.RED);
		topCenter.setOpaque(false);
		topCenter.add(buttonC);
		topCenter.add(lbNote);
		
		JPanel centerCenter = new JPanel(new GridLayout(12, 1, 5, 5));
		centerCenter.setOpaque(false);
		centerCenter.add(btnDelete);
		centerCenter.add(btnCancel);
		centerCenter.add(btnSoLuong);
		centerCenter.add(btnChangePrice);
		centerCenter.add(btnDiscountProduct);
		centerCenter.add(lblKM);
		centerCenter.add(lbDiscount);
		
		JPanel bottomCenter = new JPanel(new GridLayout(1, 1));
		bottomCenter.setOpaque(false);
		bottomCenter.add(btnExit);
		
		CONTAINER_CENTER.add(topCenter, BorderLayout.NORTH);
		CONTAINER_CENTER.add(centerCenter, BorderLayout.CENTER);
		CONTAINER_CENTER.add(bottomCenter, BorderLayout.SOUTH);
	}
	
	private void PanelColor() {
		panelColor = new javax.swing.JPanel();
		JLabel lblColorGoiDo = new javax.swing.JLabel();
		JLabel lblColorDaPhucVu = new javax.swing.JLabel();
		JLabel lblTextGoiDo = new javax.swing.JLabel();
		JLabel lblTextDaPhucVu = new javax.swing.JLabel();
		JLabel lblColorDangCheBien = new javax.swing.JLabel();
		JLabel lblTextDangCheBien = new javax.swing.JLabel();
		JLabel lblColorDaThanhToan = new javax.swing.JLabel();
		JLabel lblTextDaThanhToan = new javax.swing.JLabel();
		JLabel lblColorCheBienXong = new javax.swing.JLabel();
		JLabel lblTextCheBienXong = new javax.swing.JLabel();
		JLabel lblTextMonHuy = new javax.swing.JLabel();
		JLabel lblColorMonHuy = new javax.swing.JLabel();
		
		lblColorGoiDo.setBackground(new java.awt.Color(0, 0, 0));
		lblColorGoiDo.setText("COLOR");
		lblColorGoiDo.setOpaque(true);
		lblTextGoiDo.setFont(new java.awt.Font("Tahoma", 1, 11));
		lblTextGoiDo.setText(" Gọi đồ");
		
		lblColorDaPhucVu.setBackground(new java.awt.Color(36, 77, 126));
		lblColorDaPhucVu.setText("COLOR");
		lblColorDaPhucVu.setForeground(new java.awt.Color(36, 77, 126));
		lblColorDaPhucVu.setOpaque(true);
		lblTextDaPhucVu.setFont(new java.awt.Font("Tahoma", 1, 11));
		lblTextDaPhucVu.setForeground(new java.awt.Color(36, 77, 126));
		lblTextDaPhucVu.setText(" Đã phục vụ");
		
		lblColorDangCheBien.setBackground(new java.awt.Color(102, 0, 102));
		lblColorDangCheBien.setText("COLOR");
		lblColorDangCheBien.setForeground(new java.awt.Color(102, 0, 102));
		lblColorDangCheBien.setOpaque(true);
		lblTextDangCheBien.setFont(new java.awt.Font("Tahoma", 1, 11));
		lblTextDangCheBien.setForeground(new java.awt.Color(102, 0, 102));
		lblTextDangCheBien.setText(" Đang chế biến");
		
		lblColorDaThanhToan.setBackground(new java.awt.Color(0, 72, 0));
		lblColorDaThanhToan.setText("COLOR");
		lblColorDaThanhToan.setForeground(new java.awt.Color(0, 72, 0));
		lblColorDaThanhToan.setOpaque(true);
		lblTextDaThanhToan.setFont(new java.awt.Font("Tahoma", 1, 11));
		lblTextDaThanhToan.setForeground(new java.awt.Color(0, 72, 0));
		lblTextDaThanhToan.setText(" Đã thanh toán");
		
		lblColorCheBienXong.setBackground(new java.awt.Color(252, 124, 4));
		lblColorCheBienXong.setText("COLOR");
		lblColorCheBienXong.setForeground(new java.awt.Color(252, 124, 4));
		lblColorCheBienXong.setOpaque(true);
		lblTextCheBienXong.setFont(new java.awt.Font("Tahoma", 1, 11));
		lblTextCheBienXong.setForeground(new java.awt.Color(252, 124, 4));
		lblTextCheBienXong.setText(" Chế biến xong");
		
		lblColorMonHuy.setBackground(new java.awt.Color(155, 43, 42));
		lblColorMonHuy.setText("COLOR");
		lblColorMonHuy.setForeground(new java.awt.Color(155, 43, 42));
		lblColorMonHuy.setOpaque(true);
		lblTextMonHuy.setFont(new java.awt.Font("Tahoma", 1, 11));
		lblTextMonHuy.setForeground(new java.awt.Color(155, 43, 42));
		lblTextMonHuy.setText(" Món hủy");
		
		Box textColor1 = Box.createHorizontalBox();
		textColor1.add(Box.createHorizontalStrut(15));
		textColor1.add(lblColorGoiDo);
		textColor1.add(lblTextGoiDo);
		Box textColor2 = Box.createHorizontalBox();
		textColor2.add(lblColorDangCheBien);
		textColor2.add(lblTextDangCheBien);
		Box textColor3 = Box.createHorizontalBox();
		textColor3.add(lblColorCheBienXong);
		textColor3.add(lblTextCheBienXong);
		Box textColor4 = Box.createHorizontalBox();
		textColor4.add(Box.createHorizontalStrut(15));
		textColor4.add(lblColorDaPhucVu);
		textColor4.add(lblTextDaPhucVu);
		Box textColor5 = Box.createHorizontalBox();
		textColor5.add(lblColorDaThanhToan);
		textColor5.add(lblTextDaThanhToan);
		Box textColor6 = Box.createHorizontalBox();
		textColor6.add(lblColorMonHuy);
		textColor6.add(lblTextMonHuy);
		
		panelColor.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		panelColor.setLayout(new GridLayout(2, 3, 3, 3));
		panelColor.add(textColor1);
		panelColor.add(textColor2);
		panelColor.add(textColor3);
		panelColor.add(textColor4);
		panelColor.add(textColor5);
		panelColor.add(textColor6);
		
	}
	
	private void PanelNotes() {
		panelNotes = new javax.swing.JPanel();
		panelNotes.setPreferredSize(new Dimension(panelNotes.getSize().width, 45));
		JLabel lblKeyShortcutF2 = new javax.swing.JLabel();
		JLabel lblKeyShortcutF4 = new javax.swing.JLabel();
		JLabel lblKeyShortcutF6 = new javax.swing.JLabel();
		JLabel lblKeyShortcutF8 = new javax.swing.JLabel();
		JLabel lblKeyShortcutF3 = new javax.swing.JLabel();
		JLabel lblKeyShortcutF5 = new javax.swing.JLabel();
		JLabel lblKeyShortcutF7 = new javax.swing.JLabel();
		JLabel lblKeyShortcutF9 = new javax.swing.JLabel();
		JLabel lblKeyShortcutF10 = new javax.swing.JLabel();
		chbPoint = new javax.swing.JCheckBox();
		chbCredit = new javax.swing.JCheckBox();
		btnLamTron = new javax.swing.JButton();
		
		chbPoint.setFont(new java.awt.Font("Tahoma", 0, 12));
		chbPoint.setText("Dùng điểm");
		chbPoint.setOpaque(false);
		chbPoint.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				chbPointItemStateChanged(evt);
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
		
		lblKeyShortcutF2.setFont(new java.awt.Font("Tahoma", 1, 12));
		lblKeyShortcutF2.setText("F2: In chế biến");
		
		lblKeyShortcutF4.setFont(new java.awt.Font("Tahoma", 1, 12));
		lblKeyShortcutF4.setText("F4: In hóa đơn");
		
		lblKeyShortcutF6.setFont(new java.awt.Font("Tahoma", 1, 12));
		lblKeyShortcutF6.setText("F6: Thanh toán lẻ");
		
		lblKeyShortcutF8.setFont(new java.awt.Font("Tahoma", 1, 12));
		lblKeyShortcutF8.setText("F8: Hiện ghi chú");
		
		lblKeyShortcutF3.setFont(new java.awt.Font("Tahoma", 1, 12));
		lblKeyShortcutF3.setText("F3: In hủy món");
		
		lblKeyShortcutF5.setFont(new java.awt.Font("Tahoma", 1, 12));
		lblKeyShortcutF5.setText("F5: Thanh toán");
		
		lblKeyShortcutF7.setFont(new java.awt.Font("Tahoma", 1, 12));
		lblKeyShortcutF7.setText("F7: Trả sau");
		
		lblKeyShortcutF9.setFont(new java.awt.Font("Tahoma", 1, 12));
		lblKeyShortcutF9.setText("F9: Mở bàn");
		
		lblKeyShortcutF10.setFont(new java.awt.Font("Tahoma", 1, 12));
		lblKeyShortcutF10.setText("F10: Mã tự sinh");
		
		panelNotes.setLayout(new GridLayout(2, 7, 0, 0));
		panelNotes.add(lblKeyShortcutF2);
		panelNotes.add(lblKeyShortcutF3);
		panelNotes.add(lblKeyShortcutF4);
		panelNotes.add(lblKeyShortcutF5);
		panelNotes.add(lblKeyShortcutF6);
		panelNotes.add(lblKeyShortcutF7);
		panelNotes.add(lblKeyShortcutF8);
		panelNotes.add(lblKeyShortcutF9);
		panelNotes.add(lblKeyShortcutF10);
		panelNotes.add(chbPoint);
		panelNotes.add(chbCredit);
		panelNotes.add(btnLamTron);
	}
	
	private void initComponents() {
		long startTime = System.currentTimeMillis();
		
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		PanelBackground = new PanelBackground();
		CONTAINER_LEFT = new JPanel();
		CONTAINER_CENTER = new JPanel();
		CONTAINER_RIGHT = new JPanel();
		
		INIT_CENTER_CONTAINER();
		INIT_RIGHT_CONTAINER();
		JPanel container = new JPanel(new BorderLayout(5, 0));
		container.setOpaque(false);
		container.add(CONTAINER_CENTER, BorderLayout.WEST);
		container.add(CONTAINER_RIGHT, BorderLayout.CENTER);
		
		/** Thay đổi size panel container theo kích thước màn hình */
		if (screenSize.width > 1024)
			container.setPreferredSize(new Dimension(520, 100));
		else {
			CONTAINER_CENTER.setPreferredSize(new Dimension(65, 100));
			if (screenSize.width == 1024)
				container.setPreferredSize(new Dimension(450, 100));
			else if (screenSize.width < 1024)
				container.setPreferredSize(new Dimension(350, 100));
		}
		/** Set kích thước size cho Panel chứa các Products */
		sizeScrollPaneLeft = new Dimension((screenSize.width - (container.getPreferredSize().width + 25)), screenSize.height - 200);
		INIT_LEFT_CONTAINER();
		PanelBackground.setLayout(new BorderLayout(5, 0));
		PanelBackground.setOpaque(false);
		PanelBackground.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		PanelBackground.add(CONTAINER_LEFT, BorderLayout.CENTER);
		PanelBackground.add(container, BorderLayout.EAST);
		
		this.setLayout(new BorderLayout());
		this.add(PanelBackground, BorderLayout.CENTER);
		panelTextKeyboard = new PanelTextKeyboard();
		panelTextKeyboard.setVisible(false);
		this.add(panelTextKeyboard, BorderLayout.SOUTH);
		
		System.out.println("###$$$ TIME RUN QUERY1: " + new DecimalFormat("#0.00000").format((System.currentTimeMillis() - startTime) / 1000d));
	}
	
	// Set cac thong tin hoa don khi xem lai
	public void setInfoInvoice() {
		//		if (statusInvoice == PanelPhieuDatMuaHang.PAID) {
		//			btnPayOnce.setEnabled(false);
		//		} else if (statusInvoice == PanelPhieuDatMuaHang.UNPAID || statusInvoice == PanelPhieuDatMuaHang.PAID_AND_NOTIMPORT) {
		//			btnLuuHoaDon.setEnabled(false);
		//			chbWarehouse.setEnabled(true);
		//			btnPayOnce.setEnabled(true);
		//		}
		
		try {
			Employee e = HRModelManager.getInstance().getEmployeeByCode(tableModel.getInvoiceDetail().getEmployeeCode());
			txtEmployee.setItem(e);
		} catch (Exception e) {
		}
		
		try {
			Customer c = CustomerModelManager.getInstance().getCustomerByCode(tableModel.getInvoiceDetail().getCustomerCode());
			txtCustomer.setItem(c);
		} catch (Exception e) {
		}
		panelPayment.refershData();
		lableDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(tableModel.getInvoiceDetail().getStartDate()));
		lableTime.setText(new SimpleDateFormat("HH:mm").format(tableModel.getInvoiceDetail().getStartDate()));
		String nv = tableModel.getInvoiceDetail().getInvoiceName();
		if (nv != null)
			txtNameInvoice.setText(tableModel.getInvoiceDetail().getInvoiceName());
		
		try {
			if (tableModel.getInvoiceDetail().getWarehouseId() != null && !tableModel.getInvoiceDetail().getWarehouseId().equals("")) {
				cbWarehouse.setSelectWarehouse(tableModel.getInvoiceDetail().getWarehouseId());
			}
		} catch (Exception e) {
		}
		
		try {
			List<IdentityProduct> identityProducts = WarehouseModelManager.getInstance().getByInvoiceCode(tableModel.getInvoiceDetail().getInvoiceCode());
			for (IdentityProduct identityProduct : identityProducts) {
				if (identityProduct.getExportType().equals(IdentityProduct.ExportType.Export)) {
					//					getAllComponents(this);
					JOptionPane.showMessageDialog(null, "Phiếu nhập đã được xuất, không thể chỉnh sửa!");
					return;
				}
			}
			if (identityProducts.size() > 0) {
				chbWarehouse.setEnabled(false);
				chbWarehouse.setSelected(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (type.equals(AccountingModelManager.typeSanXuat)) {
			chbWarehouse.setSelected(true);
			chbWarehouse.setEnabled(false);
			btnPrintInvoice.setEnabled(false);
			btnPayment.setEnabled(false);
			btnPayOnce.setEnabled(false);
			btnLuuHoaDon.setEnabled(false);
		}
	}
	
	//	private List<Component> getAllComponents(final Container c) {
	//		Component[] comps = c.getComponents();
	//		List<Component> compList = new ArrayList<Component>();
	//		for (Component comp : comps) {
	//			compList.add(comp);
	//			if (!comp.equals(btnExit) && !comp.equals(btnPayment)) {
	//				comp.setEnabled(false);
	//			}
	//			if (comp instanceof Container) {
	//				compList.addAll(getAllComponents((Container) comp));
	//			}
	//		}
	//		return compList;
	//	}
	
	protected void btnDiscountProduct() {
		PanelDiscountProduct panel = new PanelDiscountProduct(panelPayment.getSelectedInvoiceItem());
		DialogResto dialog = new DialogResto(panel, false, 0, 170);
		dialog.setTitle("Chiết khấu sản phẩm");
		dialog.setLocationRelativeTo(null);
		dialog.setModal(true);
		dialog.setVisible(true);
		tableModel.updateItem(panel.getInvoiceItem());
	}

	
	// In những món bị hủy
	protected void btnActionNhapXuatKho() {
	}
	
	// Action cho nut Luu Hoa Don
	protected void btnActionLuuHoaDon() {
		statusInvoice = PanelPhieuDatMuaHang.UNPAID_AND_NOTIMPORT;
		saveInvoice(PanelPhieuDatMuaHang.UNPAID_AND_NOTIMPORT);
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
			AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(), profile);
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
			AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(), profile);
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
	
	// Reset lai giao dien va doi tuong
	public void reset() {
		InvoiceDetail invoiceDetail = new InvoiceDetail(true);
		invoiceDetail.setInvoiceCode(DateUtil.asCompactDateTimeId(new Date()));
		String code = AccountingModelManager.getInstance().getCodeObject(PanelManageCodes.InvoiceDMH, AccountingModelManager.typeBanHang, new Date(), false);
		if (code != null) {
			panelPayment.getTxtInvoiceCode().setText("");           
		} else {
			panelPayment.getTxtInvoiceCode().setText(invoiceDetail.codeView());
		}
		invoiceDetail.setActivityType(this.activityType);
		invoiceDetail.setStatus(Status.WaitingPaid);
		invoiceDetail.setType(type);
		invoiceDetail.setCurrencyUnit(currencyCode);
		invoiceDetail.setStartDate(new Date());
		invoiceDetail.setDiscount(0);
		invoiceDetail.setInvoiceName("");
		tableModel.setData(invoiceDetail);
		tableModel.changeCaculate("", "");
		lableDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		lableTime.setText(new SimpleDateFormat("HH:mm").format(new Date()));
		txtCustomer.setText("");
		txtCustomer.setObject(null);
		if (name.equals("PDH")) {
      txtNameInvoice.setText("Cty đặt hàng");
		} else if (name.equals("KDH")) {
			txtNameInvoice.setText("Khách đặt hàng");
		} else if (name.equals("Phiếu trả hàng")) {
			txtNameInvoice.setText("Cty trả hàng");
		} else {
			txtNameInvoice.setText("Khách trả hàng");
		}
		txtEmployee.setText("");
		txtEmployee.setObject(null);
		
		btnExit.setEnabled(true);
		btnPayment.setEnabled(true);
		btnLuuHoaDon.setEnabled(true);
		btnNhapXuatKho.setEnabled(true);
		statusInvoice = 0;
		if (type.equals(AccountingModelManager.typeSanXuat)) {
			chbWarehouse.setSelected(true);
			chbWarehouse.setEnabled(false);
			btnPrintInvoice.setEnabled(false);
			btnPayment.setEnabled(false);
			btnPayOnce.setEnabled(false);
			btnLuuHoaDon.setEnabled(false);
		}
	}
	
	// Xóa hoa don
	protected void button_CActionPerformed(ActionEvent evt) {
		if (tableModel.getInvoiceDetail().getId() != null) {
			if (invoiceDetail.getActivityType().equals(ActivityType.Payment) && invoiceDetail.getType().equals(AccountingModelManager.typeDatHang)) {
				if (UIConfigModelManager.getInstance().getPermission(PanelPhieuDatMuaHang.permission1) == Capability.ADMIN) {
					CheckC();
				} else {
					JOptionPane.showMessageDialog(null, "Bạn chưa được phân quyền này !");
					return;
				}
			} else if (invoiceDetail.getActivityType().equals(ActivityType.Receipt) && invoiceDetail.getType().equals(AccountingModelManager.typeDatHang)) {
				if (UIConfigModelManager.getInstance().getPermission(PanelPhieuDatMuaHang.permission2) == Capability.ADMIN) {
					CheckC();
				} else {
					JOptionPane.showMessageDialog(null, "Bạn chưa được phân quyền này !");
					return;
				}
			} else if (invoiceDetail.getActivityType().equals(ActivityType.Receipt) && invoiceDetail.getType().equals(AccountingModelManager.typeTraHang)) {
				if (UIConfigModelManager.getInstance().getPermission(PanelPhieuDatMuaHang.permission3) == Capability.ADMIN) {
					CheckC();
				} else {
					JOptionPane.showMessageDialog(null, "Bạn chưa được phân quyền này !");
					return;
				}
			} else if (invoiceDetail.getActivityType().equals(ActivityType.Payment) && invoiceDetail.getType().equals(AccountingModelManager.typeTraHang)) {
				if (UIConfigModelManager.getInstance().getPermission(PanelPhieuDatMuaHang.permission4) == Capability.ADMIN) {
					CheckC();
				} else {
					JOptionPane.showMessageDialog(null, "Bạn chưa được phân quyền này !");
					return;
				}
			}
		} else {
			reset();
			return;
		}
		
		// if (false) {
		// String str = "Bạn không thể xóa hóa đơn này!";
		// PanelRemove panel = new PanelRemove(str);
		// panel.setName("Xoa");
		// DialogResto dialog = new DialogResto(panel, false, 0, 80);
		// dialog.setName("dlXoa");
		// dialog.setTitle("Xóa hóa đơn");
		// dialog.setLocationRelativeTo(null);
		// dialog.setModal(true);
		// dialog.setVisible(true);
		// if (panel.isDelete()) {
		// return;
		// }
		// }
		
		if (tableModel.getInvoiceDetail() == null) {
			String str2 = "Không có hóa đơn!";
			PanelChoise panel2 = new PanelChoise(str2);
			panel2.setName("Xoa");
			DialogResto dialog1 = new DialogResto(panel2, false, 0, 80);
			dialog1.setName("dlXoa");
			dialog1.setTitle("Xóa hóa đơn");
			dialog1.setLocationRelativeTo(null);
			dialog1.setModal(true);
			dialog1.setVisible(true);
			if (panel2.isDelete()) {
				return;
			}
		}
		
		if (selectedProduct != null) {
			if (selectedProduct.isProduct()) {
				selectedProduct.setBackground(colorProduct);
				selectedProduct.setBorder(BorderFactory.createLineBorder(colorProduct, 5));
			} else {
				selectedProduct.setBackground(colorProductTag);
				selectedProduct.setBorder(BorderFactory.createLineBorder(colorProductTag, 5));
			}
		}
	}
	
	// Lay thoi gian lai tu giao dien cap nhat vao tableModel
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
			if(mang.get(0).length()==1){
				dateTime= dateTime+"0"+mang.get(0);
			}else {
				dateTime= dateTime+mang.get(0);
			}
			if(mang.get(1).length()==1){
				dateTime= dateTime+"0"+mang.get(1);
			}else {
				dateTime= dateTime+mang.get(1);
			}
			if(mang.get(2).length()==2){
				dateTime= dateTime+"20"+mang.get(2);
			}else {
				dateTime= dateTime+mang.get(2);
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
	
	// Luu hoa don
	private void saveInvoice(int invoiceStatus) {
		if (name == "PDH") {
			if (UIConfigModelManager.getInstance().getPermission(PanelPhieuDatMuaHang.permission1) == Capability.READ) {
				JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			} else {
				checkSave(invoiceStatus);
			}
		} else if (name == "KDH") {
			if (UIConfigModelManager.getInstance().getPermission(PanelPhieuDatMuaHang.permission2) == Capability.READ) {
				JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			} else {
				checkSave(invoiceStatus);
			}
		} else if (name == "Phiếu trả hàng") {
			if (UIConfigModelManager.getInstance().getPermission(PanelPhieuDatMuaHang.permission3) == Capability.READ) {
				JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			} else {
				checkSave(invoiceStatus);
			}
		} else if (name == "Khách trả hàng") {
			if (UIConfigModelManager.getInstance().getPermission(PanelPhieuDatMuaHang.permission4) == Capability.READ) {
				JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			} else {
				checkSave(invoiceStatus);
			}
		} else {
			checkSave(invoiceStatus);
		}
	}
	private void saveContributorOther() {
		/** Bàn + Khu vực */
		tableModel.getInvoiceDetail().setLocationCode(RestaurantModelManager.getInstance().getLocationOther().getCode());
		tableModel.getInvoiceDetail().setTableCode(RestaurantModelManager.getInstance().getTableOther().getCode());

		/** Khách hàng */
		try {
			Customer customer = (Customer) txtCustomer.getItem();
			// Trường hợp khách hàng được chọn
			if (customer != null) {
				tableModel.getInvoiceDetail().setCustomerCode(customer.getCode());
				// Thêm nhóm khách hàng
				List<AccountMembership> accMemberShips = AccountModelManager.getInstance().findMembershipByAccountLoginId(
				    customer.getLoginId());
				if (accMemberShips != null && accMemberShips.size() > 0) {
					tableModel.getInvoiceDetail().setGroupCustomerCode(accMemberShips.get(0).getGroupPath());
				} else {
					tableModel.getInvoiceDetail().setGroupCustomerCode(
					    CustomerModelManager.getInstance().getGroupCustomerOther().getPath());
				}
			}
			// Trường hợp ko chọn khách hàng
			else {
				tableModel.getInvoiceDetail().setCustomerCode(null);
				tableModel.getInvoiceDetail().setGroupCustomerCode(
				    CustomerModelManager.getInstance().getGroupCustomerOther().getPath());
			}
		} catch (Exception e) {
			Supplier c = (Supplier) txtCustomer.getItem();
			if (c != null) {
				tableModel.getInvoiceDetail().setSupplierCode(c.getCode());
			}
		}
		// Tạo contributor phòng ban nhân viên
		if (tableModel.getInvoiceDetail().getDepartmentCode() == null
		    || tableModel.getInvoiceDetail().getDepartmentCode()
		        .equals(HRModelManager.getInstance().getDepartmentOther().getPath())) {
			if (panelPayment.getDepartmentJComboBox().getSelectedDepartment() != null) {
				tableModel.getInvoiceDetail().setDepartmentCode(
				    panelPayment.getDepartmentJComboBox().getSelectedDepartment().getPath());
			} else {
				if (tableModel.getInvoiceDetail().getDepartmentCode() == null) {
					tableModel.getInvoiceDetail().setDepartmentCode(HRModelManager.getInstance().getDepartmentOther().getPath());
				}
			}
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

		List<Contributor> contributors = tableModel.getInvoiceDetail().getContributors();
		if (contributors == null) {
			contributors = new ArrayList<Contributor>();
		}

		String groupDepartmentOther = HRModelManager.getInstance().getDepartmentOther().getPath();
		// Tạo contributor thành viên phục vụ

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
							for(AccountMembership a: accMemberShips){
								if(a.getGroupPath().indexOf(AccountModelManager.Department)>0){
									contributorNVPV.setIdentifierValue(a.getGroupPath());
								}
							}
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
	// Kiem tra hoa don
	private void checkSave(int invoiceStatus) {
		try {
			if (!updateTime()) {
				lableDate.setForeground(Color.red);
				lableTime.setForeground(Color.red);
				return;
			} else {
				lableDate.setForeground(Color.black);
				lableTime.setForeground(Color.black);
			}
			saveContributorOther();
			if (type.equals(AccountingModelManager.typeSanXuat)) {
				tableModel.getInvoiceDetail().setDiscountRate(100);
				tableModel.getInvoiceDetail().setDiscount(tableModel.getInvoiceDetail().getFinalCharge());
				tableModel.caculator();
			}
			InvoiceDetail invoice = tableModel.getInvoiceDetail();
			invoice.setInvoiceName(txtNameInvoice.getText());
			if (invoice.getItems().size() == 0) {
				PanelChoise panel = new PanelChoise("Hóa đơn không có sản phẩm !");
				DialogResto dialog = new DialogResto(panel, false, 0, 80);
				dialog.setTitle("Thông báo");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
				if (panel.isDelete()) {
					return;
				}
			}
			
			/** Kiểm tra nếu chỉ lưu hoặc nhập kho thì không cần hiện bảng thanh toán */
			if (invoiceStatus == PanelPhieuDatMuaHang.PAID || invoiceStatus == PanelPhieuDatMuaHang.PAID_AND_NOTIMPORT) {
				PanelTextMoneyPayment panel = new PanelTextMoneyPayment();
				panel.initEvent(MyDouble.valueOf(invoice.getFinalCharge() - invoice.getTotalPaid()).toString(), currencyCode);
				DialogResto dialog = new DialogResto(panel, false, 400, 350);
				dialog.setTitle("Thanh toán");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
				if (!panel.isFlagPayment()) {
					return;
				}
			}
			
			if (invoiceStatus == PanelPhieuDatMuaHang.PAID || invoiceStatus == PanelPhieuDatMuaHang.PAID_AND_NOTIMPORT) {
				invoice.setStatus(Status.Paid);
				/** Thanh toán lẻ InvoiceTransaction */
				// invoice = invoice.calculate(new DefaultInvoiceCalculator());
				if (invoice.getFinalCharge() != invoice.getTotalPaid()) {
					InvoiceTransaction transaction = new InvoiceTransaction();
					transaction.setTransactionType(TransactionType.Cash);
					transaction.setCreatedBy(ManagerAuthenticate.getInstance().getLoginId());
					transaction.setCurrencyUnit(currencyCode);
					transaction.setTotal(invoice.getFinalCharge() - invoice.getTotalPaid());
					transaction.setTransactionDate(new Date());
					invoice.add(transaction);
					invoice.setTotalPaid(invoice.getTotalPaid() + transaction.getTotal());
				}
				for (InvoiceItem invoiceItem : invoice.getItems()) {
					if (!invoiceItem.getStatus().equals(AccountingModelManager.isCance) 
							&& !invoiceItem.getStatus().equals(AccountingModelManager.isPromotion)) {
						invoiceItem.setStatus(AccountingModelManager.isPayment);
					}

				}
				if (!printReceipt(true, true, null)) {
					DialogNotice.getInstace().setVisible(true);
				}
				// tableModel.updateStatus(AccountingModelManager.isPayment);
			} else {
				if (!invoice.getStatus().equals(Status.Paid)) {
					invoice.setStatus(Status.PostPaid);
					for(InvoiceTransaction invoiceTransaction : invoice.getTransactions()){
						invoiceTransaction.setCreatedTime(invoiceTransaction.getTransactionDate());
		        invoiceTransaction.setEmployeeCode(invoice.getEmployeeCode());
		        invoiceTransaction.setCustomerCode(invoice.getCustomerCode());
		        invoiceTransaction.setDepartmentCode(invoice.getDepartmentCode());
		        invoiceTransaction.setCurrencyRate(invoice.getCurrencyRate());
		        invoiceTransaction.setGroupCustomerCode(invoice.getGroupCustomerCode());
		        invoiceTransaction.setTableCode(invoice.getTableCode());
		        invoiceTransaction.setLocationCode(invoice.getLocationCode());
		        invoiceTransaction.setWarehouseId(invoice.getWarehouseId());
		        invoiceTransaction.setStoreCode(invoice.getStoreCode());
		        invoiceTransaction.setProjectCode(invoice.getProjectCode());
		        invoiceTransaction.setSupplierCode(invoice.getSupplierCode());
		        invoiceTransaction.setType(invoice.getType());
		        if (invoice.getActivityType().equals(ActivityType.Payment)) {
		          invoiceTransaction.setActivityType(InvoiceTransaction.ActivityType.Payment);
		        } else {
		          invoiceTransaction.setActivityType(InvoiceTransaction.ActivityType.Receipt);
		        }
					}
				}
			}
			
			/** Thêm kho */
			if (cbWarehouse.getSelectedWarehouse() != null) {
				invoice.setWarehouseId(cbWarehouse.getSelectedWarehouse().getWarehouseId());
			} else {
				invoice.setWarehouseId(null);
			}
			
			if (invoice.getEndDate() == null) {
				invoice.setEndDate(new Date());
				String code = AccountingModelManager.getInstance().getCodeObject(
						PanelManageCodes.InvoiceDMH,type, invoice.getStartDate(), true);
				if (code != null) {
					invoice.setInvoiceCode(code);
				} else {
					invoice.setInvoiceCode(panelPayment.getTxtInvoiceCode().getText());
				}
			}
			if(invoiceStatus == PanelPhieuDatMuaHang.UNPAID_AND_NOTIMPORT ){
				invoice = AccountingModelManager.getInstance().saveInvoice(invoice);
			}else {
				invoice = AccountingModelManager.getInstance().saveInvoiceDetail(invoice);
			}
			if (chbWarehouse.isSelected() && (invoiceStatus == PanelPhieuDatMuaHang.PAID || invoiceStatus == PanelPhieuDatMuaHang.UNPAID)) {
				final long id = invoice.getId();
				final Thread t = new Thread() {
					@Override
					public void run() {
						try {
							long endTime2 = System.currentTimeMillis();
							InvoiceDetail invoice = AccountingModelManager.getInstance().getInvoiceDetail(id);
							String code = DateUtil.asCompactDateTimeId(new Date());
							for (int i = 0; i < invoice.getItems().size(); i++) {
								InvoiceItem invoiceItem = invoice.getItems().get(i);
								String productCode = invoiceItem.getProductCode();
								String provider = "";
								Date dateMenufacture = null;
								Date dateExpiry = null;
								double quantity = invoiceItem.getQuantity() - invoiceItem.getQuantityReal();
								/** Phiếu mua hàng - nhập kho */
								if (activityType.equals(ActivityType.Payment)) {
									// quantity > 0 là thêm mới
									if (quantity > 0) {
										Warehouse wh = null;
										if (cbWarehouse.getSelectedWarehouse() != null) {
											wh = cbWarehouse.getSelectedWarehouse();
										}
										for (int j = 0; j < quantity; j++) {
											IdentityProduct identityProduct = new IdentityProduct();
											identityProduct.setInvoiceCode(invoice.getInvoiceCode());
											identityProduct.setInvoiceItemIdImport(invoiceItem.getId());
											if (wh != null) {
												identityProduct.setWarehouseCode(wh.getWarehouseId());
											} else {
												identityProduct.setWarehouseCode(null);
											}
											identityProduct.setProductCode(productCode);
											identityProduct.setProvider(provider);
											identityProduct.setDateExpiry(dateExpiry);
											identityProduct.setDateMenufacture(dateMenufacture);
											identityProduct.setPrice(invoiceItem.getUnitPrice());
											identityProduct.setUnitPrice(invoiceItem.getCurrencyUnit());
											identityProduct.setCurrencyRate(invoiceItem.getCurrencyRate());
											identityProduct.setShipmentImportCode(code);
											identityProduct.setImportDate(invoiceItem.getStartDate());
											identityProduct.setImportType(ImportType.Import);
											identityProduct.setExportType(ExportType.NotExport);
											identityProduct = WarehouseModelManager.getInstance().saveIdentityProduct(identityProduct);
										}
									} else {
										List<IdentityProduct> identityProducts = WarehouseModelManager.getInstance().getByInvoiceItemIdImport(invoiceItem.getId());
										double q = quantity * -1;
										for (int j = 0; j < q; j++) {
											WarehouseModelManager.getInstance().deleteIdentityProduct(identityProducts.get(j));
										}
										// quantity <=0 là sửa lại,
										identityProducts = WarehouseModelManager.getInstance().getByInvoiceItemIdImport(invoiceItem.getId());
									}
									invoiceItem.setQuantityReal(invoiceItem.getQuantity());
									Product p = ProductModelManager.getInstance().getProductByCode(invoiceItem.getProductCode());
									if (p.isUpdatePrice()) {
										p.setPrice(invoiceItem.getUnitPrice());
										ProductModelManager.getInstance().saveProduct(p);
									}
								} else {
									/** Phiếu bán hàng - xuất kho */
									// Tìm List sản phẩm có trong kho theo mã
									// sản phẩm,
									// List.size() là số lượng SP đó có trong
									// kho
									List<IdentityProduct> identityProducts = new ArrayList<IdentityProduct>();
									if (invoice.getWarehouseId() == null) {
										identityProducts = WarehouseModelManager.getInstance().findByProductCodeAndExportType(productCode);
									} else {
										identityProducts = WarehouseModelManager.getInstance().findByProductCodeAndExportTypeAndWarehoseCode(productCode, invoice.getWarehouseId());
									}
									int count = 0;
									// So sánh số lượng SP được chọn trong bảng
									// với số lượng có
									// trong kho
									if (quantity > identityProducts.size()) {
										if (type.equals(AccountingModelManager.typeSanXuat)) {
											ChooseDelProduct panel1 = new ChooseDelProduct("Sản phẩm trong kho không đủ, phiếu sẽ tự động xóa!");
											DialogResto dialog1 = new DialogResto(panel1, false, 0, 100);
											dialog1.setTitle("Kho");
											dialog1.setLocationRelativeTo(null);
											dialog1.setModal(true);
											dialog1.setVisible(true);
											AccountingModelManager.getInstance().deleteInvoice(invoice);
											return;
										}
										if (AccountModelManager.getInstance().getProfileConfigAdmin().get(PanelChooseProductExport.EXPORT) != null && AccountModelManager.getInstance().getProfileConfigAdmin().get(PanelChooseProductExport.EXPORT).equals("true")) {
										} else {
											
											PanelChooseProductExport panel1 = new PanelChooseProductExport(invoiceItem.getItemName());
											DialogResto dialog1 = new DialogResto(panel1, false, 0, 120);
											dialog1.setTitle("Kho");
											dialog1.setLocationRelativeTo(null);
											dialog1.setModal(true);
											dialog1.setVisible(true);
											if (panel1.isDelete()) {
												tableModel.deleteAllIdentityProductExport(invoice);
												for (InvoiceItem bean : invoice.getItems()) {
													bean.setQuantityReal(0);
												}
												invoice = AccountingModelManager.getInstance().saveInvoice(invoice);
											} else {
												btnExit.setEnabled(false);
												return;
											}
											
										}
										
									} else {
										if (quantity > 0) {
											for (IdentityProduct identityProduct : identityProducts) {
												identityProduct.setExportDate(invoiceItem.getStartDate());
												identityProduct.setShipmentExportCode(code);
												identityProduct.setExportType(ExportType.Export);
												identityProduct.setInvoiceExportCode(invoice.getInvoiceCode());
												identityProduct.setInvoiceItemIdExport(invoice.getItems().get(i).getId());
												identityProduct.setProvider(provider);
												identityProduct.setDateMenufacture(dateMenufacture);
												identityProduct.setDateExpiry(dateExpiry);
												identityProduct.setCurrencyExportRate(invoiceItem.getCurrencyRate());
												identityProduct.setPriceExport(invoiceItem.getUnitPrice());
												WarehouseModelManager.getInstance().saveIdentityProduct(identityProduct);
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
													count--;
													if (count == quantity) {
														break;
													}
												}
											}
										}
										invoiceItem.setQuantityReal(invoiceItem.getQuantity());
									}
								}
							}
							invoice = AccountingModelManager.getInstance().saveInvoice(invoice);
							System.out.println("time: " + new DecimalFormat("#0.00000").format((System.currentTimeMillis() - endTime2) / 1000d));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				};
				t.start();
			}
			reset();	
			if(exit){
				((JDialog)getRootPane().getParent()).dispose();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void btnExitSystem() {
		try {
			if (tableModel.getInvoiceDetail().getEndDate() == null) {
				AccountingModelManager.getInstance().deleteInvoice(tableModel.getInvoiceDetail());
			}
		} catch (Exception e2) {
			//			e2.printStackTrace();
		}
		if (selectedProduct != null)
			screenSalePOS.setSelectedProduct(selectedProduct);
		if (selectedProductTag2 != null)
			screenSalePOS.setSelectedProductTag2(selectedProductTag2);
		
		reset();
		setVisible(false);
		//		this.dispose();
	}
	
	// Khoi tao invoiceDetail cho phieu hoa don moi
	public void initInvoiceDetail() {
		table = RestaurantModelManager.getInstance().getTableOther();
		InvoiceDetail invoiceDetail = new InvoiceDetail(true);
		invoiceDetail.setInvoiceCode(DateUtil.asCompactDateTimeId(new Date()));
		String code = AccountingModelManager.getInstance().getCodeObject(PanelManageCodes.InvoiceDMH, AccountingModelManager.typeBanHang, new Date(), false);
		if (code != null) {
			panelPayment.getTxtInvoiceCode().setText("");           
		} else {
			panelPayment.getTxtInvoiceCode().setText(invoiceDetail.codeView());
		}
		invoiceDetail.setActivityType(this.activityType);
		invoiceDetail.setStatus(Status.WaitingPaid);
		invoiceDetail.setType(this.type);
		invoiceDetail.setCurrencyUnit(currencyCode);
		invoiceDetail.setStartDate(new Date());
		invoiceDetail.setDiscount(0);
		invoiceDetail.setDiscountRate(0);
		invoiceDetail.setInvoiceName("");
		invoiceDetail.setTableCode(table.getCode());
		invoiceDetail.setLocationCode("");
		
		try {
			panelPayment.setCurrencyCode(currencyCode);
			tableModel.setInfoInvoice("", pricingType, table);
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
		
		tableModel.setData(invoiceDetail);
	}
	
	public TableModelInvoiceItem getTableModel() {
		return tableModel;
	}
	
	// Thêm mới 1 sản phẩm vào bàn
	@Override
	public void addProduct(Product product) {
		try {
			tableModel = getTableModel();
			InvoiceItem invoiceItem = AccountingModelManager.getInstance().getInvoiceItem(tableModel.getInvoiceDetail(), 
					product.getCode(), AccountingModelManager.isRecord);
			Menu menu = PromotionModelManager.getInstance().getMenuByCode(product.getCode());
			if (menu != null) {
				invoiceItem = null;
			}
			if (invoiceItem == null) {
				invoiceItem = new InvoiceItem();
				invoiceItem.setItemName(product.getName());
				if (menu != null) {
					invoiceItem.setItemCode(Menu.Type.Menu.toString() + DateUtil.asCompactDateTimeId(new Date()));
				} else {
					invoiceItem.setItemCode(DateUtil.asCompactDateTimeId(new Date()));
				}
				
				invoiceItem.setQuantity(quantity);
				invoiceItem.setUnitPrice(0);
				invoiceItem.setTotal(0);
				invoiceItem.setCurrencyUnit(currencyCode);
				invoiceItem.setStatus(AccountingModelManager.isRecord);
				if (activityType.equals(Invoice.ActivityType.Payment)) {
					invoiceItem.setActivityType(InvoiceItem.ActivityType.Payment);
				} else {
					invoiceItem.setActivityType(InvoiceItem.ActivityType.Receipt);
				}
				invoiceItem.setProductCode(product.getCode());
				if (menu != null) {
					if (menu.getStatus().equals(Menu.Status.Options)) {
						DialogChoiseMenuItem item = new DialogChoiseMenuItem(menu);
						item.setVisible(true);
						if (item.getListStr() != null && !item.getListStr().isEmpty()) {
							for (int i = 0; i < item.getListStr().size(); i++) {
								InvoiceItemAttribute a = new InvoiceItemAttribute();
								a.setName(Menu.Type.Menu.toString());
								a.setValue(item.getListStr().get(i));
								a.setAuthor(item.getListGroup().get(i));
								invoiceItem.add(a);
							}
						}
					}
				}
				tableModel.addItem(invoiceItem);
				tableModel.changeCaculate("", "");
			} else {
				invoiceItem.setQuantity(invoiceItem.getQuantity() + quantity);
				tableModel.updateItem(invoiceItem);
			}
			quantity = 1;
		} catch (Exception e) {
			quantity = 1;
			e.printStackTrace();
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
	
	private String convertMoney(String strMoney) {
		ManagerConvert convertMoney = new ManagerConvert();
		if (strMoney.indexOf("-") == 0) {
			strMoney = strMoney.substring(1, strMoney.indexOf("."));
		}
		String str = "";
		for (int i = 0; i < strMoney.length(); i++) {
			String s1 = String.valueOf(strMoney.charAt(i));
			if (!s1.trim().isEmpty()) {
				str = str + s1;
			}
		}
		String moneyText = convertMoney.readNumber(str);
		return moneyText;
	}
	
	private boolean printReceipt(boolean view, boolean exel, InvoiceDetail paymentSlip) {
		InvoiceDetail invoiceDetail = tableModel.getInvoiceDetail();
		if (paymentSlip != null) {
			invoiceDetail = paymentSlip;
		}
		invoiceDetail.setDiscount(invoiceDetail.getDiscount()+invoiceDetail.getDiscountByItem());
		// String strMessenger =
		// "Bản sao phiếu thanh toán, có thể khác phiếu cuối cùng";
		String loginId = ManagerAuthenticate.getInstance().getOrganizationLoginId();
		String enterpriseName = AccountModelManager.getInstance().getNameByLoginId(loginId);
		String adres = AccountModelManager.getInstance().getAddressByLoginId(loginId);
		String fone = AccountModelManager.getInstance().getPhoneByLoginId(loginId);
		String fax = AccountModelManager.getInstance().getFaxByLoginId(loginId);
		

		String currencyCode = LocaleModelManager.getInstance().getCurrencyByCode(this.currencyCode).getName();

		String ratio = "";
		String moneyQuyDoi = "";
		if (!this.currencyCode.equals("VND")) {
			ratio = new MyDouble(LocaleModelManager.getInstance().getCurrencyByCode(this.currencyCode).getRate()).toString();
			double a = LocaleModelManager.getInstance().getCurrencyByCode(this.currencyCode).getRate() * (invoiceDetail.getFinalCharge() - invoiceDetail.getTotalPaid());
			moneyQuyDoi = new MyDouble(a).toString();
		}

		String lienHoaDon = "1", solanIn = "1";
		String tongSoDiem = "", soDiemConLai = "", tongSoTien = "", soTienConLai = "";
		String credit = "";
		String gioVao = lableTime.getText();
		String diaChi = "", sdt = "";
		if (txtCustomer.getItem() != null) {
			try {
				diaChi = AccountModelManager.getInstance().getAddressByLoginId(((Customer)txtCustomer.getItem()).getLoginId());
				sdt = ((Customer)txtCustomer.getItem()).getMobile();
      } catch (Exception e) {
      	diaChi = AccountModelManager.getInstance().getAddressByLoginId(((Supplier)txtCustomer.getItem()).getLoginId());
  			sdt = ((Supplier)txtCustomer.getItem()).getMobile();
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

		// String datCoc = getMoney(invoiceDetail.getTotalPaid()).toString(), // Da
		// tra
		String datCoc = getMoney(invoiceDetail.getFinalCharge()).toString(), // Da
																																					// tra
		tienThue = getMoney(invoiceDetail.getFinalCharge()).toString(); // Tong cong
		// String phaiTra = getMoney(invoiceDetail.getFinalCharge() -
		// invoiceDetail.getTotalPaid()).toString();
		String phaiTra = "0";
		if (printAgain) {
			phaiTra = getMoney(invoiceDetail.getFinalCharge() - invoiceDetail.getTotalPaid()).toString();
			tienThue = getMoney(invoiceDetail.getFinalCharge()).toString();
			datCoc = getMoney(invoiceDetail.getTotalPaid()).toString();
		}
		
		String stkd = PanelTextMoneyPayment.getSoTienKhachDua();
		String sttl = PanelTextMoneyPayment.getSoTienLeTraKhach();
		if (paymentSlip != null) {
			stkd = getMoney(invoiceDetail.getFinalCharge()).toString();
			sttl = "0";
		}
		String strMoney = convertMoney(getMoney(invoiceDetail.getFinalCharge()).toString());

		String strStt="";
		if (name.equals("PDH")) {
			strStt = "Phiếu Đặt Hàng";
		} else if (name.equals("KDH")) {
			strStt = "Phiếu Đặt Hàng";
		} else if (name.equals("Phiếu trả hàng")) {
			strStt = "Phiếu Trả Hàng";
		} else {
			strStt = "Phiếu Trả Hàng";
		}
		String nameArea = "";
		if(panelPayment.getTxtProject().getItem()!=null && !panelPayment.getTxtProject().getItem().getCode().equals("project-other")){
			nameArea= panelPayment.getTxtProject().getItem().getCode();
		}
		if(nameArea.equals(RestaurantModelManager.getInstance().getLocationPaymentAfter().getName())){
			nameArea="";
		}

		String[] str = new String[] { enterpriseName, adres, fone, fax, date, code, nameArea, txtNameInvoice.getText(), gioVao, txtEmployee.getText(), MyDouble.valueOf(invoiceDetail.getTotal()).toString(),
				MyDouble.valueOf(invoiceDetail.getDiscountRate()).toString(), MyDouble.valueOf(invoiceDetail.getDiscount()).toString(), ptThue, MyDouble.valueOf(invoiceDetail.getTotalTax()).toString(),
				tienThue, datCoc, phaiTra, strStt, dfDate.format(new Date()), "", currencyCode, MyDouble.valueOf(invoiceDetail.getService()).toString(), ratio, moneyQuyDoi,
				MyDouble.valueOf(invoiceDetail.getServiceRate()).toString(), strMoney, MyDouble.valueOf(invoiceDetail.getPoint()).toString(), MyDouble.valueOf(invoiceDetail.getPoint()).toString(),
				txtCustomer.getText(), "", "", credit, lienHoaDon, solanIn, tongSoDiem, soDiemConLai, tongSoTien, soTienConLai, credit,
				MyDouble.valueOf(invoiceDetail.getCredit()).toString(), "", stkd, sttl, diaChi, sdt };
		try {
			Table table = RestaurantModelManager.getInstance().getTableOther();
			String name = invoiceDetail.getInvoiceCode() + "_" + String.valueOf(invoiceDetail.getTransactions().size());
			if (!exel) {
				name = null;
			}
			if (profile.get("Exel") == null && profile.get("Pdf") == null) {
				name = null;
			}
			invoiceDetail.setDiscount(invoiceDetail.getDiscount()-invoiceDetail.getDiscountByItem());
			if (paymentSlip != null) {
				return ReportPrint.getInstance().printReceiptSlip(table, tableModel, invoiceDetail.getItems(), str, view);
			} else {
				return ReportPrint.getInstance().printReceipt(table, tableModel, tableModel.getTablePromotion(), str, view, name);
			}
			
		} catch (Exception e) {
			return false;
		}

	}
	
	// Thanh toán lẻ
	private void btnActionPayOnce() {
		if (name == "PDH") {
			if (UIConfigModelManager.getInstance().getPermission(PanelPhieuDatMuaHang.permission1) == Capability.READ) {
				JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			} else {
				checkpaymentSlip();
			}
		} else if (name == "KDH")
			if (UIConfigModelManager.getInstance().getPermission(PanelPhieuDatMuaHang.permission2) == Capability.READ) {
				JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			} else {
				checkpaymentSlip();
			}
		else if (name == "Phiếu trả hàng") {
			if (UIConfigModelManager.getInstance().getPermission(PanelPhieuDatMuaHang.permission3) == Capability.READ) {
				JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			} else {
				checkpaymentSlip();
			}
		} else if (name == "Khách trả hàng") {
			if (UIConfigModelManager.getInstance().getPermission(PanelPhieuDatMuaHang.permission4) == Capability.READ) {
				JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			} else {
				checkpaymentSlip();
			}
		}
	}
	
	private void checkpaymentSlip() {
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
						InvoiceDetail invoiceDetail = AccountingModelManager.getInstance().getInvoiceDetail(tableModel.getInvoiceDetail().getId());
						tableModel.setData(invoiceDetail);
						tableModel.changeCaculate(null, null);
					} catch (Exception e) {
					}
				}
			}
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
	
	// Xóa sản phẩm khỏi hóa đơn
	private void deleteProduct() {
		try {
			if (panelPayment.getSelectedInvoiceItem() != null) {
				ChooseDelProduct panel = new ChooseDelProduct(panelPayment.getSelectedInvoiceItem().getItemName());
				panel.setName("Xoa");
				DialogResto dialog = new DialogResto(panel, false, 0, 80);
				dialog.setName("dlXoa");
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
	
	private void CheckC() {
		String str = "Bạn muốn xóa hóa đơn này?";
		PanelChoise panel1 = new PanelChoise(str);
		panel1.setName("Xoa");
		DialogResto dialog = new DialogResto(panel1, false, 0, 80);
		dialog.setName("dlXoa");
		dialog.setTitle("Xóa hóa đơn");
		dialog.setLocationRelativeTo(null);
		dialog.setModal(true);
		dialog.setVisible(true);
		if (panel1.isDelete()) {
			try {
				if (tableModel.getInvoiceDetail().getId() != null) {
					AccountingModelManager.getInstance().deleteInvoice(tableModel.getInvoiceDetail());
					if (exit) {
						reset();
						((JDialog)getRootPane().getParent()).dispose();
					} else {
						reset();
					}
				} else {
					reset();
				}
			} catch (Exception e1) {
				reset();
				e1.printStackTrace();
			}
			
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
				tableModel.getInvoiceDetail().setGroupCustomerCode(CustomerModelManager.getInstance().getGroupCustomerOther().getPath());
			}
			txtCustomer.setItem(customer);
			tableModel.setInfoInvoice(customer.getLoginId(), pricingType, componentTable.getTable());
			tableModel.loadPromotionProduct();
		}
	}
	
	// In hóa đơn
	private void btnActionPrintInvoice() {
		if (name == "PDH") {
			if (UIConfigModelManager.getInstance().getPermission(PanelPhieuDatMuaHang.permission1) == Capability.READ) {
				JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			} else {
				checkprint();
			}
		} else if (name == "KDH") {
			if (UIConfigModelManager.getInstance().getPermission(PanelPhieuDatMuaHang.permission2) == Capability.READ) {
				JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			} else {
				checkprint();
			}
		} else if (name == "Phiếu trả hàng") {
			if (UIConfigModelManager.getInstance().getPermission(PanelPhieuDatMuaHang.permission3) == Capability.READ) {
				JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			} else {
				checkprint();
			}
		} else if (name == "Khách trả hàng") {
			if (UIConfigModelManager.getInstance().getPermission(PanelPhieuDatMuaHang.permission4) == Capability.READ) {
				JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			} else {
				checkprint();
			}
		}
	}
	private void checkprint() {
		DialogPrint dialogPrint = new DialogPrint(getMoney(tableModel.getInvoiceDetail().getFinalCharge() - tableModel.getInvoiceDetail().getTotalPaid()).toString(), currencyCode);
		DialogResto dialog = new DialogResto(dialogPrint, false, 0, 120);
		dialog.setTitle("In hóa đơn");
		dialog.setBtnSave(false);
		// dialog.setBtnExit(false);
		dialog.setLocationRelativeTo(null);
		dialog.setModal(true);
		dialog.setVisible(true);

		if (dialogPrint.getPrint() == 3) {
			printReceipt(false, false, null);
		} else {
			printAgain = true;
			boolean print = printReceipt(true, false, null);
			printAgain = false;
		}
		AccountingModelManager.pay = "Tiền mặt";
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
		// Phím tắt in hủy món
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0, false), "F3");
		jc.getActionMap().put("F3", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				btnActionNhapXuatKho();
			}
		});
		
		// Phím tắt in hóa đơn
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0, false), "F4");
		jc.getActionMap().put("F4", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				btnActionPrintInvoice();
			}
		});
		
		// Phím tắt in chế biến
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0, false), "F2");
		jc.getActionMap().put("F2", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				btnActionLuuHoaDon();
			}
		});
		
		// Phím tắt thanh toán
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0, false), "F5");
		jc.getActionMap().put("F5", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					if (chbWarehouse.isSelected()) {
						statusInvoice = PanelPhieuDatMuaHang.PAID;
						saveInvoice(PanelPhieuDatMuaHang.PAID);
					} else {
						statusInvoice = PanelPhieuDatMuaHang.PAID_AND_NOTIMPORT;
						saveInvoice(PanelPhieuDatMuaHang.PAID_AND_NOTIMPORT);
					}				} catch (Exception e) {
				}
			}
		});
		
		// Phím tắt thanh toán lẻ
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0, false), "F6");
		jc.getActionMap().put("F6", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				btnActionPayOnce();
			}
		});
		
		// Phím tắt trả sau
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0, false), "F7");
		jc.getActionMap().put("F7", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
			}
		});
		
		// jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F11,
		// 0, false), "F11");
		// jc.getActionMap().put("F11", new AbstractAction() {
		// @Override
		// public void actionPerformed(ActionEvent ae) {
		// try {
		// ChooseProductExport panel1 = new ChooseProductExport("");
		// DialogResto dialog1 = new DialogResto(panel1, false, 600, 300);
		// dialog1.setTitle("Thiết lập kho");
		// dialog1.setLocationRelativeTo(null);
		// dialog1.setModal(true);
		// dialog1.setVisible(true);
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// }
		// }
		// });
		
		// Phím tắt mã tự sinh
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0, false), "F10");
		jc.getActionMap().put("F10", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					// PaneSetInvoice invoice = new PaneSetInvoice();
					// invoice.setName("ThemMaTuSinh");
					// DialogMain dialog = new DialogMain(invoice, true);
					// dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
					// dialog.setName("dlThemMaTuSinh");
					// dialog.setTitle("Thiết lập mã hóa đơn");
					// dialog.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "ESC");
		jc.getActionMap().put("ESC", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				btnExitSystem();
			}
		});
		
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0, false), "F8");
		jc.getActionMap().put("F8", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				boolean flagVisible = panelColor.isVisible();
				if (flagVisible) {
					panelColor.setVisible(false);
					panelNotes.setVisible(false);
					panelTopRight.setVisible(true);
					panelButtonLeft.setVisible(true);
				} else {
					panelColor.setVisible(true);
					panelNotes.setVisible(true);
					panelTopRight.setVisible(false);
					panelButtonLeft.setVisible(false);
				}
			}
		});
		
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0, false), "F12");
		jc.getActionMap().put("F12", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (chbTimKiem.isSelected()) {
					chbTimKiem.setSelected(false);
					txtTextFieldTimKiem.setVisible(true);
					panelSearchTextPopup.setVisible(false);
				} else {
					chbTimKiem.setSelected(true);
					txtTextFieldTimKiem.setVisible(false);
					panelSearchTextPopup.setVisible(true);
				}
			}
		});
		
		// jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F9,
		// 0, false), "F9");
		// jc.getActionMap().put("F9", new AbstractAction() {
		// @Override
		// public void actionPerformed(ActionEvent ae) {
		// txtTable.requestFocus();
		// }
		// });
		
		// jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD0,
		// 0, false), "0");
		// jc.getActionMap().put("0", new AbstractAction() {
		// @Override
		// public void actionPerformed(ActionEvent ae) {
		// numberAction("0");
		// }
		// });
		//
		// jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_0,
		// 0, false), "0");
		// jc.getActionMap().put("0", new AbstractAction() {
		// @Override
		// public void actionPerformed(ActionEvent ae) {
		// numberAction("0");
		// }
		// });
		//
		// jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD1,
		// 0, false), "1");
		// jc.getActionMap().put("1", new AbstractAction() {
		// @Override
		// public void actionPerformed(ActionEvent ae) {
		// numberAction("1");
		// }
		// });
		//
		// jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_1,
		// 0, false), "1");
		// jc.getActionMap().put("1", new AbstractAction() {
		// @Override
		// public void actionPerformed(ActionEvent ae) {
		// numberAction("1");
		// }
		// });
		//
		// jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_2,
		// 0, false), "2");
		// jc.getActionMap().put("2", new AbstractAction() {
		// @Override
		// public void actionPerformed(ActionEvent ae) {
		// numberAction("2");
		// }
		// });
		//
		// jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD2,
		// 0, false), "2");
		// jc.getActionMap().put("2", new AbstractAction() {
		// @Override
		// public void actionPerformed(ActionEvent ae) {
		// numberAction("2");
		// }
		// });
		//
		// jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_3,
		// 0, false), "3");
		// jc.getActionMap().put("3", new AbstractAction() {
		// @Override
		// public void actionPerformed(ActionEvent ae) {
		// numberAction("3");
		// }
		// });
		// jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD3,
		// 0, false), "3");
		// jc.getActionMap().put("3", new AbstractAction() {
		// @Override
		// public void actionPerformed(ActionEvent ae) {
		// numberAction("3");
		// }
		// });
		//
		// jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_4,
		// 0, false), "4");
		// jc.getActionMap().put("4", new AbstractAction() {
		// @Override
		// public void actionPerformed(ActionEvent ae) {
		// numberAction("4");
		// }
		// });
		//
		// jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD4,
		// 0, false), "4");
		// jc.getActionMap().put("4", new AbstractAction() {
		// @Override
		// public void actionPerformed(ActionEvent ae) {
		// numberAction("4");
		// }
		// });
		//
		// jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_5,
		// 0, false), "5");
		// jc.getActionMap().put("5", new AbstractAction() {
		// @Override
		// public void actionPerformed(ActionEvent ae) {
		// numberAction("5");
		// }
		// });
		//
		// jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD5,
		// 0, false), "5");
		// jc.getActionMap().put("5", new AbstractAction() {
		// @Override
		// public void actionPerformed(ActionEvent ae) {
		// numberAction("5");
		// }
		// });
		//
		// jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_6,
		// 0, false), "6");
		// jc.getActionMap().put("6", new AbstractAction() {
		// @Override
		// public void actionPerformed(ActionEvent ae) {
		// numberAction("6");
		// }
		// });
		//
		// jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD6,
		// 0, false), "6");
		// jc.getActionMap().put("6", new AbstractAction() {
		// @Override
		// public void actionPerformed(ActionEvent ae) {
		// numberAction("6");
		// }
		// });
		//
		// jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_7,
		// 0, false), "7");
		// jc.getActionMap().put("7", new AbstractAction() {
		// @Override
		// public void actionPerformed(ActionEvent ae) {
		// numberAction("7");
		// }
		// });
		//
		// jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD7,
		// 0, false), "7");
		// jc.getActionMap().put("7", new AbstractAction() {
		// @Override
		// public void actionPerformed(ActionEvent ae) {
		// numberAction("7");
		// }
		// });
		//
		// jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_8,
		// 0, false), "8");
		// jc.getActionMap().put("8", new AbstractAction() {
		// @Override
		// public void actionPerformed(ActionEvent ae) {
		// numberAction("8");
		// }
		// });
		//
		// jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD8,
		// 0, false), "8");
		// jc.getActionMap().put("8", new AbstractAction() {
		// @Override
		// public void actionPerformed(ActionEvent ae) {
		// numberAction("8");
		// }
		// });
		//
		// jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_9,
		// 0, false), "9");
		// jc.getActionMap().put("9", new AbstractAction() {
		// @Override
		// public void actionPerformed(ActionEvent ae) {
		// numberAction("9");
		// }
		// });
		//
		// jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD9,
		// 0, false), "9");
		// jc.getActionMap().put("9", new AbstractAction() {
		// @Override
		// public void actionPerformed(ActionEvent ae) {
		// numberAction("9");
		// }
		// });
	}
	
	protected void numberAction(String string) {
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
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public String getNameType() {
		return name;
	}

	public javax.swing.JPanel getPanelTop2() {
		return panelTop2;
	}

	public JScrollPane getScrollPane_Product() {
		return scrollPane_Product;
	}

	@Override
	public void update(Object o, Object arg) {
		if (arg instanceof String || arg instanceof Product) {
			if (txtTextPopupTimKiem.getItem() != null) {
				try {
					quantity = Double.parseDouble(arg.toString());
				} catch (Exception e) {
					quantity = 1;
				}
				addProduct(txtTextPopupTimKiem.getItem());
			}
		}
	}
	
	@Override
	public void setListProduct(List<Product> list) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setTable(TableEat tableEat) {
		// TODO Auto-generated method stub
		
	}
	
}
