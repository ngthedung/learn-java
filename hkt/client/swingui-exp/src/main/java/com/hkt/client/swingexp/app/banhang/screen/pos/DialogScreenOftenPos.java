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
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

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

import org.jdesktop.swingx.WrapLayout;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.util.StringUtils;

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
import com.hkt.client.swingexp.app.banhang.screen.often.PanelHouseholdProducts;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelListPartnerOrganization;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelListPartnerOrganizationWork;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelMemberServe;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelPayment;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelPaymentProduct;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelRounding;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelTextMoneyPayment;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelTimed;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelToDoWork;
import com.hkt.client.swingexp.app.banhang.screen.often.ScreenOften;
import com.hkt.client.swingexp.app.banhang.screen.often.TableEat;
import com.hkt.client.swingexp.app.banhang.screen.often.TableModelInvoiceItem;
import com.hkt.client.swingexp.app.banhang.screen.pos.PanelScreenSaleLocationPos.ComponentTable;
import com.hkt.client.swingexp.app.component.ExtendJCheckBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.ImageTool;
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
import com.hkt.client.swingexp.app.khohang.CatalogProductTag;
import com.hkt.client.swingexp.app.khohang.PanelRepositoryProducts2;
import com.hkt.client.swingexp.app.khuvucbanhang.PanelManageCodes;
import com.hkt.client.swingexp.app.khuyenmai.DialogChoiseMenuItem;
import com.hkt.client.swingexp.app.khuyenmai.list.TableListViewPromotion;
import com.hkt.client.swingexp.app.print.ReportPrint;
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
import com.hkt.module.core.entity.AbstractPersistable;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.partner.customer.entity.Credit;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.customer.entity.Point;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductImage;
import com.hkt.module.product.entity.ProductPrice;
import com.hkt.module.product.entity.ProductTag;
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

public class DialogScreenOftenPos extends JDialog implements IScreenSales, IMyObserver {
	private javax.swing.JButton btnSoLuong;
	private javax.swing.JButton btnTraSau;
	private javax.swing.JButton btnCancel;
	private javax.swing.JButton btnChangePrice;
	private javax.swing.JButton btnDatGio;
	private javax.swing.JButton btnDelete;
	private TextPopup<Employee> txtEmployee;
	private TextPopup<Employee> txtTVPV;
	private javax.swing.JButton btnLichTrinh;
	private TextPopup<Customer> txtPartner;
	private javax.swing.JButton btnPayOnce;
	private javax.swing.JButton btnPrintInvoice;
	private javax.swing.JButton btnInCheBien;
	private javax.swing.JButton btnPromotions;
	private javax.swing.JButton btnInHuyMon;
	private javax.swing.JButton btnTraGop;
	private javax.swing.JButton btnDiscountProduct;
	private javax.swing.JButton btnThemSP;
	private javax.swing.JButton btnThemNhomSP;
	private javax.swing.JButton btnXoaSP;
	private javax.swing.JButton btnMayIn;
	private javax.swing.JButton btnPayment;
	private javax.swing.JButton buttonC;
	private javax.swing.JButton btnExit;
	private javax.swing.JButton btnTVPV;
	private javax.swing.JButton btnLamTron;
	private javax.swing.JCheckBox chbLichTrinh;
	private javax.swing.JCheckBox chbPoint;
	private javax.swing.JCheckBox chbTraGop;
	private javax.swing.JCheckBox chbCredit;
	private javax.swing.JLabel lblKeyShortcutF8;
	private javax.swing.JLabel jLabel11;
	private javax.swing.JLabel jLabel17;
	private javax.swing.JLabel jLabel18;
	private javax.swing.JLabel jLabel19;
	private javax.swing.JLabel jLabel20;
	private javax.swing.JLabel jLabel21;
	private javax.swing.JLabel jLabel22;
	private javax.swing.JLabel jLabel23;
	private boolean printAgain;
	private javax.swing.JLabel jLabel24;
	private javax.swing.JLabel lblKeyShortcutF3;
	private javax.swing.JLabel lblKeyShortcutF5;
	private javax.swing.JLabel lblKeyShortcutF7;
	private javax.swing.JLabel lblKeyShortcutF9;
	private javax.swing.JLabel jLabel29;
	private javax.swing.JLabel lblKeyShortcutF2;
	private javax.swing.JLabel lblKeyShortcutF10;
	private javax.swing.JLabel lblKeyShortcutF4;
	private javax.swing.JLabel lblKeyShortcutF6;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JPanel panelDateTime;
	private javax.swing.JPanel CONTAINER_CENTER;
	private javax.swing.JPanel CONTAINER_LEFT;
	private javax.swing.JPanel CONTAINER_RIGHT;
	private javax.swing.JScrollPane ScrollPane_PanelPayment;
	private javax.swing.JTextField lableDate;
	private javax.swing.JTextField lableTime;
	private javax.swing.JLabel lbMuaHo;
	private javax.swing.JLabel lbNote;
	private javax.swing.JLabel lbNameTable;
	private PanelBackground PanelBackground;
	private javax.swing.JPanel panelColor;
	private javax.swing.JPanel panelNotes;
	private javax.swing.JPanel panelTopRight;
	private JPanel panelTop2;
	private javax.swing.JTextField txtGuest;
	private TableModelInvoiceItem tableModel;
	private double quantity = 1;
	private PanelPayment panelPayment;
	private DateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat dfTime = new SimpleDateFormat("HH:mm");
	private DateFormat dfCode = new SimpleDateFormat("yyyyMMddHHmmss");
	private boolean flag;
	private String pricingType = "";
	private Table table;
	private String currencyCode = "VND";
	private PanelTextKeyboard panelTextKeyboard;
	private JButton btnBanPhimAo;
	private JLabel lbDiscount;
	private boolean reset;

	public void setReset(boolean reset) {
		this.reset = reset;
	}

	// Variable Panel Left
	private JCheckBox chbTimKiem;
	private ExtendJTextField txtTextFieldTimKiem;
	private TextPopup<Product> txtTextPopupTimKiem;
	private ExtendJTextField txtQuantity;
	private JPanel panelProductTagRoot;
	private JPanel panelNumber;
	private JPanel panelProducts;
	private JScrollPane scrollPane_Product;
	private JPanel panelButtonLeft;
	private JPanel panelSearchTextPopup;

	// Toolket ScreenSize
	private Dimension screenSize;
	private Dimension sizeScrollPaneLeft;
	private int mouseCountClick = 2;

	// Xu ly nghiep vu
	private ComponentTable componentTable;
	private Color colorProductTag = new Color(252, 204, 4);
	private Color colorProduct = new Color(152, 200, 8);
	private Color colorProductSelect = new Color(252, 124, 4);

	public HashMap<String, ComponentProduct> hashMapProducts = new HashMap<String, ComponentProduct>();;
	private HashMap<String, Object> hashMapProductTagRoot = new HashMap<String, Object>();;
	private JLabel selectedProductTag2;
	private ComponentProduct selectedProduct;

	public static final String LichTrinh = "lichtrinh";
	public static final String TraGop = "tragop";
	public static final String ManagerCredit = "credit";
	public static final String ManagerPoint = "point";
	public static final String ComboBoxCuaHang = "cbCuaHang";
	private Profile profile;
	public static DialogScreenOftenPos screenOften1;
	private static boolean flagScreenOften = true;
	private PanelScreenSaleLocationPos panelSalePOS = PanelScreenSaleLocationPos.getInstance();
	private double priceKara;

	public static DialogScreenOftenPos getInstance() {
		if (screenOften1 == null) {
			screenOften1 = new DialogScreenOftenPos();
		}
		return screenOften1;
	}

	public DialogScreenOftenPos() {
		this.setUndecorated(true);
		this.setModal(true);
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());

		panelPayment = new PanelPayment(false);
		tableModel = panelPayment.getTableModel();

		initComponents();

		ScrollPane_PanelPayment.setViewportView(panelPayment);
		ScrollPane_PanelPayment.setOpaque(false);
		ScrollPane_PanelPayment.getViewport().setOpaque(false);
		ScrollPane_PanelPayment.setWheelScrollingEnabled(false);

		txtPartner.addObserver(this);
		txtEmployee.addObserver(this);

		lableDate.setEnabled(false);
		lableTime.setEnabled(false);
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
				try {
					if (componentTable != null && componentTable.getTable().getInvoiceCode() != null
					    && !componentTable.getTable().getInvoiceCode().equals("")
					    && panelSalePOS.getListTableBussy().get(componentTable.getTable().getCode()) == null) {
						panelSalePOS.getListTableBussy().put(componentTable.getTable().getCode(), componentTable);
						JLabel lblNumBussy = panelSalePOS.lblNumTableBussy;
						JLabel lblNumFree = panelSalePOS.lblNumTableFree;
						lblNumBussy.setText((Integer.parseInt(lblNumBussy.getText()) + 1) + "");
						lblNumFree.setText((Integer.parseInt(lblNumFree.getText()) - 1) + "");
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		loadData();

		resetGui();
	}

	public void loadData() {
		panelPayment.getChbForRent().setEnabled(true);
		panelPayment.getDateForRent().setEnabled(true);
		panelPayment.getChbForRent().setSelected(false);
		Thread t = new Thread() {
			public void run() {
				try {
					List<Employee> list1 = HRModelManager.getInstance().getEmployees();
					txtEmployee.setData(list1);
					txtTVPV.setData(list1);
					List<Product> list2 = ProductModelManager.getInstance().findAllProducts();
					txtTextPopupTimKiem.setData(list2);
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					List<Customer> customers = CustomerModelManager.getInstance().getCustomers(false);
					txtPartner.setData(customers);
				} catch (Exception e) {
					e.printStackTrace();
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
	public void resetGui() {
		try {
			// Lấy profile thiết lập trong config
			profile = AccountModelManager.getInstance().getProfileConfigAdmin();
			// Lấy thiết lập chọn bảng giá
			boolean flagRefeshProduct = false;
			try {
				// Nếu thiết lập chọn bảng giá theo khu vực
				if (profile.get(DialogConfig.TbArea).equals(true)) {
					if (tableModel != null && tableModel.getInvoiceDetail() != null && !tableModel.getInvoiceDetail().equals("")) {
						Location location = RestaurantModelManager.getInstance().getLocationByCode(
						    tableModel.getInvoiceDetail().getLocationCode());
						if (location != null && location.getLocationAttributes() != null
						    && location.getLocationAttributes().size() > 0) {
							for (LocationAttribute a : location.getLocationAttributes()) {
								if (a.getName().equals("ProductPriceType")) {
									if (pricingType != null && !pricingType.equals(a.getValue())) {
										flagRefeshProduct = true;
										pricingType = a.getValue();
										break;
									}
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
					if (tableModel != null)
						tableModel.setInfoInvoice("", pricingType, table);
				}
			} catch (Exception e) {
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
			// Load lại giá giao diện Panel giá sản phẩm nếu có thay đổi giá và tiền
			// tệ trong thiết lập config
			if (flagRefeshProduct && panelProducts != null) {
				Thread t = new Thread() {
					public void run() {
						try {
							List<ProductPrice> listProductPricePpt = ProductModelManager.getInstance().findByProductPriceByType(
							    pricingType);
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

			// Lấy thiết lập cho admin
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

			if (profile.get(DialogConfig.TaoSP).toString().equals("true")) {
				btnThemSP.setEnabled(true);
			} else {
				btnThemSP.setEnabled(false);
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
				btnPrintInvoice.setEnabled(true);
			} else {
				btnPrintInvoice.setEnabled(false);
			}

			if (profile.get(DialogConfig.Paymen).toString().equals("true")) {
				btnPayment.setEnabled(true);
			} else {
				btnPayment.setEnabled(false);
			}

			if (profile.get(DialogConfig.SoLuong).toString().equals("true")) {
				btnSoLuong.setEnabled(true);
			} else {
				btnSoLuong.setEnabled(false);
			}

			if (profile.get(DialogConfig.Datgio).toString().equals("true")) {
				btnDatGio.setEnabled(true);
			} else {
				btnDatGio.setEnabled(false);
			}

			if (profile.get(DialogConfig.DoiGia).toString().equals("true")) {
				btnChangePrice.setEnabled(true);
			} else {
				btnChangePrice.setEnabled(false);
			}

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

			if (profile.get(DialogConfig.Keyboard) != null && profile.get(DialogConfig.Keyboard).toString().equals("true")) {
				btnBanPhimAo.setEnabled(true);
				mouseCountClick = 1;
			} else {
				btnBanPhimAo.setEnabled(false);
				mouseCountClick = 2;
			}
			panelPayment.refeshGui();
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	private void INIT_RIGHT_CONTAINER() {
		ScrollPane_PanelPayment = new javax.swing.JScrollPane();
		txtGuest = new javax.swing.JTextField();
		lbNameTable = new javax.swing.JLabel("Bàn:");
		btnTVPV = new javax.swing.JButton();
		btnPayment = new javax.swing.JButton();
		btnPrintInvoice = new javax.swing.JButton();
		btnPayOnce = new javax.swing.JButton();
		btnInCheBien = new javax.swing.JButton();
		btnInHuyMon = new javax.swing.JButton();
		btnTraSau = new javax.swing.JButton();
		panelDateTime = new javax.swing.JPanel();
		lableDate = new javax.swing.JTextField();
		lableTime = new javax.swing.JTextField();
		txtEmployee = new TextPopup<Employee>(Employee.class);
		txtTVPV = new TextPopup<Employee>(Employee.class);
		txtPartner = new TextPopup<Customer>(Customer.class);

		txtPartner.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				try {
					txtPartnerMouseClicked(event);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		txtGuest.setText("0");
		txtGuest.setHorizontalAlignment(JTextField.RIGHT);
		txtGuest.setOpaque(false);
		txtGuest.addCaretListener(new javax.swing.event.CaretListener() {
			public void caretUpdate(javax.swing.event.CaretEvent evt) {
				// txtGuestCaretUpdate(evt);
			}
		});

		lbNameTable.setFont(new java.awt.Font("Tahoma", 1, 11));
		lbNameTable.setText("");

		btnTVPV.setFont(new java.awt.Font("Tahoma", 1, 11));
		btnTVPV.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnTVPV.addMouseListener(new MouseEventClickButtonDialogPlus(null, null, null));
		btnTVPV.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				choiseEmployeeServer();
			}
		});

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

		lableDate.setFont(new java.awt.Font("Tahoma", 0, 12));
		lableDate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		lableDate.setText("dd/MM/yyyy");
		lableDate.setBorder(null);
		lableDate.setDisabledTextColor(new java.awt.Color(0, 0, 0));
		lableDate.setMargin(new java.awt.Insets(0, 0, 0, 0));
		lableDate.setOpaque(false);

		lableTime.setFont(new java.awt.Font("Tahoma", 0, 12));
		lableTime.setPreferredSize(new Dimension(40, 22));
		lableTime.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		lableTime.setText("HH:mm");
		lableTime.setBorder(null);
		lableTime.setDisabledTextColor(new java.awt.Color(0, 0, 0));
		lableTime.setMargin(new java.awt.Insets(0, 0, 0, 0));
		lableTime.setOpaque(false);

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
				try {
					actionBtnPayment();
				} catch (Exception e) {
					e.printStackTrace();
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
				actionBtnPrintInvoice();
			}
		});

		btnPayOnce.setText("<html><p align='center'>Thanh toán lẻ</p></html>");
		btnPayOnce.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnPayOnce.addMouseListener(new MouseEventClickButtonDown(null, null, null));
		btnPayOnce.setName("btnPayOnce");
		btnPayOnce.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				actionBtnPayOnce();
			}
		});

		btnInCheBien.setText("<html><p align='center'>In chế biến</p></html>");
		btnInCheBien.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnInCheBien.addMouseListener(new MouseEventClickButtonDialogPlus(null, null, null));
		btnInCheBien.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				actionBtnInCheBien();
			}
		});

		btnInHuyMon.setText("<html><p align='center'>In hủy món</p></html>");
		btnInHuyMon.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnInHuyMon.addMouseListener(new MouseEventClickButtonDialogPlus(null, null, null));
		btnInHuyMon.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				actionBtnInHuyMon();
			}
		});

		btnTraSau.setText("<html><p align='center'>Trả sau</p></html>");
		btnTraSau.setName("btnTraSau");
		btnTraSau.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnTraSau.addMouseListener(new MouseEventClickButtonDown(null, null, null));
		btnTraSau.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				actionBtnTraSau();
			}
		});

		PanelColor();

		panelTopRight = new JPanel();
		panelTopRight.setLayout(new GridLayout(3, 1, 0, 5));
		panelTopRight.setBackground(Color.WHITE);
		panelTopRight.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		JPanel panelTop1 = new JPanel();
		panelTop1.setBackground(Color.WHITE);
		panelTop1.setLayout(new GridLayout(1, 2, 5, 0));
		JPanel panelT1 = new JPanel();
		panelT1.setBackground(Color.WHITE);
		panelT1.setLayout(new BorderLayout());
		JLabel lblEmployee = new javax.swing.JLabel();
		lblEmployee.setFont(new java.awt.Font("Tahoma", 0, 12));
		lblEmployee.setText("Thu ngân");
		lblEmployee.setPreferredSize(new Dimension(70, 22));
		panelT1.add(lblEmployee, BorderLayout.WEST);
		panelT1.add(txtEmployee, BorderLayout.CENTER);
		JPanel panelT11 = new JPanel();
		panelT11.setBackground(Color.WHITE);
		panelT11.setLayout(new BorderLayout());
		JLabel lblCustomer = new javax.swing.JLabel();
		lblCustomer.setFont(new java.awt.Font("Tahoma", 0, 12));
		lblCustomer.setText("Khách hàng");
		lblCustomer.setPreferredSize(new Dimension(70, 22));
		panelT11.add(lblCustomer, BorderLayout.WEST);
		panelT11.add(txtPartner, BorderLayout.CENTER);
		panelTop1.add(panelT1);
		panelTop1.add(panelT11);
		panelTopRight.add(panelTop1);
		JPanel panelTop2 = new JPanel();
		panelTop2.setBackground(Color.WHITE);
		panelTop2.setLayout(new GridLayout(1, 2, 5, 0));
		JPanel panelT2 = new JPanel();
		panelT2.setBackground(Color.WHITE);
		panelT2.setLayout(new BorderLayout());
		JLabel labelT2 = new JLabel("TV Phục vụ");
		profile = AccountModelManager.getInstance().getProfileConfigAdmin();
		if (profile.get("ForRent") != null) {
			labelT2.setText("Shipper");
		}
		labelT2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelT2.setPreferredSize(new Dimension(70, 22));
		panelT2.add(labelT2, BorderLayout.WEST);
		panelT2.add(txtTVPV, BorderLayout.CENTER);
		JPanel panelT22 = new JPanel();
		panelT22.setBackground(Color.WHITE);
		panelT22.setLayout(new BorderLayout());
		JLabel labelT22 = new JLabel("Thời gian");
		labelT22.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelT22.setPreferredSize(new Dimension(70, 22));
		panelT22.add(labelT22, BorderLayout.WEST);
		panelT22.add(panelDateTime, BorderLayout.CENTER);
		panelTop2.add(panelT2);
		panelTop2.add(panelT22);
		panelTopRight.add(panelTop2);
		JPanel panelTop3 = new JPanel();
		panelTop3.setBackground(Color.WHITE);
		panelTop3.setLayout(new GridLayout(1, 2, 5, 0));
		JPanel panelT3 = new JPanel();
		panelT3.setBackground(Color.WHITE);
		panelT3.setLayout(new BorderLayout());
		lbNameTable.setFont(new java.awt.Font("Tahoma", 1, 16));
		lbNameTable.setHorizontalTextPosition(JLabel.CENTER);
		lbNameTable.setVerticalTextPosition(JLabel.CENTER);
		lbNameTable.setHorizontalAlignment(JLabel.CENTER);
		lbNameTable.setText(""); // NOI18N
		lbNameTable.setPreferredSize(new Dimension(70, 22));
		panelT3.add(lbNameTable, BorderLayout.WEST);

		panelT3.add(panelPayment.getTxtInvoiceCode(), BorderLayout.CENTER);
		JPanel panelT33 = new JPanel();
		panelT33.setBackground(Color.WHITE);
		panelT33.setLayout(new BorderLayout());
		JLabel lblGuest = new javax.swing.JLabel();
		lblGuest.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 12));
		lblGuest.setText("Số khách");
		lblGuest.setPreferredSize(new Dimension(70, 22));
		panelT33.add(lblGuest, BorderLayout.WEST);
		panelT33.add(txtGuest, BorderLayout.CENTER);
		panelTop3.add(panelT3);
		panelTop3.add(panelT33);
		panelTopRight.add(panelTop3);

		JPanel GrossPanelTop = new JPanel(new BorderLayout(0, 0));
		GrossPanelTop.add(panelTopRight, BorderLayout.NORTH);
		GrossPanelTop.add(panelColor, BorderLayout.SOUTH);
		panelColor.setVisible(false);

		JPanel Panel_Bottom = new JPanel(new GridLayout(2, 3, 3, 3));
		Panel_Bottom.setOpaque(false);
		Panel_Bottom.setPreferredSize(new Dimension(Panel_Bottom.getSize().width, 90));
		Panel_Bottom.add(btnInCheBien);
		Panel_Bottom.add(btnPayOnce);
		Panel_Bottom.add(btnPayment);
		Panel_Bottom.add(btnInHuyMon);
		Panel_Bottom.add(btnTraSau);
		Panel_Bottom.add(btnPrintInvoice);

		CONTAINER_RIGHT.setLayout(new BorderLayout(0, 0));
		CONTAINER_RIGHT.setOpaque(false);
		CONTAINER_RIGHT.add(GrossPanelTop, BorderLayout.NORTH);
		CONTAINER_RIGHT.add(ScrollPane_PanelPayment, BorderLayout.CENTER);
		CONTAINER_RIGHT.add(Panel_Bottom, BorderLayout.SOUTH);
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
		ExtendJLabel lblHkt = new ExtendJLabel("HKT RESTO");
		lblHkt.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblHkt.setPreferredSize(new Dimension(100, 23));
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
					if (panel.isProduct()
					    && panel.getLblNameProduct().getText().trim().toLowerCase()
					        .contains(txtTextFieldTimKiem.getText().trim().toLowerCase())) {
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
		// -----------------------------------PANEL
		// TOP---------------------------------------
		// -----------------------------------------------------------------------------------
		panelTop2 = new JPanel(new BorderLayout(0, 0));
		panelTop2.setOpaque(false);
		panelTop2.setPreferredSize(new Dimension(100, 50));
		panelProductTagRoot = new JPanel();
		panelProductTagRoot.setBackground(Color.LIGHT_GRAY);
		panelProductTagRoot.setBorder(BorderFactory.createLineBorder(Color.GRAY));
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
			private Dimension dimension = new Dimension(30, 50);

			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					ImageIcon imageIcon = ImageTool.getInstance().resize(
					    new ImageIcon(HomeScreen.class.getResource("icon/button_back.jpg")), dimension);
					lblBackProduct.setIcon(imageIcon);
				} catch (Exception ex) {
					lblBackProduct.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/button_back.jpg")));
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				try {
					ImageIcon imageIcon = ImageTool.getInstance().resize(
					    new ImageIcon(HomeScreen.class.getResource("icon/button_back2.jpg")), dimension);
					lblBackProduct.setIcon(imageIcon);
				} catch (Exception ex) {
					lblBackProduct.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/button_back2.jpg")));
				}
				if (panelProductTagRoot.getName() != null) {
					String[] split = panelProductTagRoot.getName().toString().split(":");
					if (split != null && split.length > 0) {
						int indexFrom = Integer.parseInt(split[0]);
						int indexTo = Integer.parseInt(split[1]);
						if (indexFrom > 1) {
							panelProductTagRoot.getComponent(indexFrom - 1).setVisible(true);
							panelProductTagRoot.getComponent(indexTo).setVisible(false);
							panelProductTagRoot.setName(((indexFrom - 1) + ":" + (indexTo - 1)).toString());
						}
					}
				}
			}
		});

		lblNextProduct.addMouseListener(new MouseAdapter() {
			private Dimension dimension = new Dimension(30, 50);

			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					ImageIcon imageIcon = ImageTool.getInstance().resize(
					    new ImageIcon(HomeScreen.class.getResource("icon/button_next.jpg")), dimension);
					lblNextProduct.setIcon(imageIcon);
				} catch (Exception ex) {
					lblNextProduct.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/button_next.jpg")));
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				try {
					ImageIcon imageIcon = ImageTool.getInstance().resize(
					    new ImageIcon(HomeScreen.class.getResource("icon/btt_next2.jpg")), dimension);
					lblNextProduct.setIcon(imageIcon);
				} catch (Exception ex) {
					lblNextProduct.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/btt_next2.jpg")));
				}
				if (panelProductTagRoot.getName() != null) {
					String[] split = panelProductTagRoot.getName().toString().split(":");
					if (split != null && split.length > 0) {
						int indexFrom = Integer.parseInt(split[0]);
						int indexTo = Integer.parseInt(split[1]);
						if (indexTo < (panelProductTagRoot.getComponentCount() - 1)) {
							panelProductTagRoot.getComponent(indexFrom).setVisible(false);
							panelProductTagRoot.getComponent(indexTo + 1).setVisible(true);
							panelProductTagRoot.setName(((indexFrom + 1) + ":" + (indexTo + 1)).toString());
						}
					}
				}
			}
		});

		// Vòng lặp tạo ra JLabel đại diện cho nhóm sản phẩm cấp 2
		Dimension sizeProductTagRoot = new Dimension(sizeScrollPaneLeft.width - 60, 50);
		int countCell;
		/** Chỉnh kích thước phù hợp màn hình */
		if (screenSize.width <= 1024) {
			countCell = 6;
		} else {
			countCell = 9;
		}
		int widthCell = (sizeProductTagRoot.getSize().width) / countCell;
		int heightCell = sizeProductTagRoot.getSize().height;
		// Bắt đầu tạo JLabel cho từng nhóm cấp 2
		try {
			// Tìm tất cả nhóm cấp 1 => nhóm cấp 2, tạo JLabel nhóm cấp 2
			List<ProductTag> productTagsRoot = ProductModelManager.getInstance().getRootTags();
			for (ProductTag pt : productTagsRoot) {
				List<ProductTag> productTagsChild2 = ProductModelManager.getInstance().getChildProductTag(pt.getTag());
				List<JLabel> labelProductTagC2 = new ArrayList<JLabel>();
				for (int i = 1; i <= productTagsChild2.size(); i++) {
					JLabel lblProduct = new ExtendJLabel("<html><p align='center'>" + productTagsChild2.get(i - 1).getLabel()
					    + "</p></html>");
					lblProduct.setName(productTagsChild2.get(i - 1).getTag());
					lblProduct.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, Color.LIGHT_GRAY));
					lblProduct.setHorizontalAlignment(SwingConstants.CENTER);
					lblProduct.setBackground(Color.ORANGE);
					lblProduct.setOpaque(true);
					lblProduct.setPreferredSize(new Dimension(widthCell - 2, heightCell - 2));
					lblProduct.addMouseListener(new EventMouseClickProductTagRoot());
					labelProductTagC2.add(lblProduct);
				}
				hashMapProductTagRoot.put(pt.getTag(), labelProductTagC2);
			}

			// Tạo JLabel tất cả
			JLabel lblProduct = new ExtendJLabel("<html><p align='center'>Tất cả</p></html>");
			lblProduct.setName("allProduct");
			lblProduct.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, Color.LIGHT_GRAY));
			lblProduct.setHorizontalAlignment(SwingConstants.CENTER);
			lblProduct.setBackground(Color.ORANGE);
			lblProduct.setOpaque(true);
			lblProduct.setPreferredSize(new Dimension(widthCell - 2, heightCell - 2));
			lblProduct.addMouseListener(new EventMouseClickProductTagRoot());
			hashMapProductTagRoot.put("allProduct", lblProduct);

			// Xem config xem nhóm cấp 1 nào được chọn => Hiển thị nhóm cấp 2 của nó
			profile = AccountModelManager.getInstance().getProfileConfigAdmin();
			List<String> listProductTag = (ArrayList<String>) profile.get(DialogConfig.ProductTag);
			if (listProductTag != null && listProductTag.size() > 0) {
				hashMapProductTagRoot.put(DialogConfig.ProductTag, listProductTag);
				panelProductTagRoot.add(lblProduct); // Add JLabel tất cả
				for (String s : listProductTag) { // Duyệt qua code productTag các nhóm
					                                // cấp 2 được chọn
					List<JLabel> labelProductTagC2 = (ArrayList<JLabel>) hashMapProductTagRoot.get(s); // Lấy
					                                                                                   // ra
					                                                                                   // nhóm
					                                                                                   // cấp
					                                                                                   // 2
					for (JLabel l : labelProductTagC2) { // Lấy ra JLabel cấp 2 trên
						panelProductTagRoot.add(l); // Add vào
						if (panelProductTagRoot.getComponentCount() > countCell) { // Nếu số
							                                                         // lượng
							                                                         // lớn
							                                                         // hơn
							                                                         // quy
							                                                         // định
							                                                         // thì ẩn
							                                                         // JLabel
							                                                         // đi
							l.setVisible(false);
						}
					}
				}
			}

			// Set nhóm cấp 2 đầu tiên được chọn
			if (panelProductTagRoot != null && panelProductTagRoot.getComponentCount() > 0) {
				selectedProductTag2 = (JLabel) panelProductTagRoot.getComponent(0);
				selectedProductTag2.setVisible(true);
				selectedProductTag2.setBackground(Color.RED);
				panelProductTagRoot.setName("1:" + (countCell - 1)); // Thiết lập khoảng
				                                                     // JLabel hiển thị
			}

			if (panelProductTagRoot.getComponentCount() < countCell) {
				panelProductTagRoot.setLayout(new FlowLayout(FlowLayout.LEADING, 2, 0));
			} else {
				panelProductTagRoot.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 0));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

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

		btnNumber1.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnNumber2.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnNumber3.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnNumber4.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnNumber5.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnNumber6.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnNumber7.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnNumber8.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnNumber9.setMargin(new java.awt.Insets(0, 0, 0, 0));

		panelNumber.add(btnNumber1);
		panelNumber.add(btnNumber2);
		panelNumber.add(btnNumber3);
		panelNumber.add(btnNumber4);
		panelNumber.add(btnNumber5);
		panelNumber.add(btnNumber6);
		panelNumber.add(btnNumber7);
		panelNumber.add(btnNumber8);
		panelNumber.add(btnNumber9);

		// ---Add các phần tử trên
		PanelTOP.add(panelTop1, BorderLayout.NORTH);
		PanelTOP.add(panelTop2, BorderLayout.CENTER);
		PanelTOP.add(panelNumber, BorderLayout.SOUTH);
		PanelTOP.setOpaque(false);

		/************************************************************************
		 **************** Khởi tạo các phần tử con PANEL CENTER ******************
		 *************************************************************************/

		panelProducts = new JPanel(new WrapLayout(WrapLayout.LEADING, 5, 5)) {
			@Override
			public void setSize(Dimension d) {
				super.setSize(d);
				setPreferredSize(d);
				setMaximumSize(d);
			}
		};
		panelProducts.setBackground(Color.LIGHT_GRAY);
		scrollPane_Product = new JScrollPane(panelProducts);
		scrollPane_Product.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		Thread t = new Thread() {
			public void run() {
				loadGuiProduct();
			};
		};
		t.start();

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
		btnPromotions = new JButton("<html><p align='center'>Xem khuyến mại</p></html>");
		btnBanPhimAo = new JButton("<html><p align='center'>Hiện bàn phím</p></html>");

		btnThemNhomSP.setPreferredSize(new Dimension(120, 45));
		btnXoaSP.setPreferredSize(new Dimension(120, 45));
		btnMayIn.setPreferredSize(new Dimension(120, 45));
		btnThemSP.setPreferredSize(new Dimension(120, 45));
		btnPromotions.setPreferredSize(new Dimension(120, 45));
		btnBanPhimAo.setPreferredSize(new Dimension(120, 45));

		btnThemNhomSP.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnXoaSP.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnMayIn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnThemSP.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPromotions.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnBanPhimAo.setFont(new Font("Tahoma", Font.PLAIN, 14));

		btnThemNhomSP.setHorizontalTextPosition(SwingConstants.CENTER);
		btnXoaSP.setHorizontalTextPosition(SwingConstants.CENTER);
		btnMayIn.setHorizontalTextPosition(SwingConstants.CENTER);
		btnThemSP.setHorizontalTextPosition(SwingConstants.CENTER);
		btnPromotions.setHorizontalTextPosition(SwingConstants.CENTER);
		btnBanPhimAo.setHorizontalTextPosition(SwingConstants.CENTER);

		btnThemNhomSP.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnXoaSP.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnMayIn.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnThemSP.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnPromotions.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnBanPhimAo.setMargin(new java.awt.Insets(0, 0, 0, 0));

		panelButtonLeft.add(btnThemNhomSP);
		panelButtonLeft.add(btnThemSP);
		panelButtonLeft.add(btnXoaSP);
		panelButtonLeft.add(btnMayIn);
		panelButtonLeft.add(btnBanPhimAo);
		panelButtonLeft.add(btnPromotions);

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

		btnPromotions.setName("btnPromotions");
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

		CONTAINER_LEFT.add(PanelTOP, BorderLayout.NORTH);
		CONTAINER_LEFT.add(scrollPane_Product, BorderLayout.CENTER);
		CONTAINER_LEFT.add(grossPanel, BorderLayout.SOUTH);

	}

	public void loadGuiProduct() {
		long startTime = System.currentTimeMillis();

		panelProducts.setSize(new Dimension(sizeScrollPaneLeft.width - 3, sizeScrollPaneLeft.height - 3));
		Dimension sizeCell = null;
		int numberColumn = 0;
		int w = 0;
		int h = 65;

		// Thiết lập số ô cell hiển thị cho kích thước màn hình
		if (screenSize.width < 1024) {
			numberColumn = 4;
		} else {
			numberColumn = 5;
			JScrollBar sb = scrollPane_Product.getVerticalScrollBar();
			sb.setPreferredSize(new Dimension(((screenSize.width > 1024) ? 25 : 15), 0));
		}
		w = sizeScrollPaneLeft.width / numberColumn;
		sizeCell = new Dimension(w - 7, h);

		// Lấy các thiết lập giá và tiền tệ mặc định trong config
		Currency currency = null;
		try {
			// Config chọn bảng giá mặc định
			profile = AccountModelManager.getInstance().getProfileConfigAdmin();
			pricingType = profile.get(DialogConfig.ProductPriceType).toString();
			if (profile.get(DialogConfig.Currency) != null) {
				currencyCode = profile.get(DialogConfig.Currency).toString();
				currency = LocaleModelManager.getInstance().getCurrencyByCode(currencyCode);
			}
		} catch (Exception e1) {
		}

		try {
			List<ProductPrice> listProductPricePpt = ProductModelManager.getInstance().findByProductPriceByType(pricingType);
			List<Product> productsNotPpt = ProductModelManager.getInstance().findByNotProductPriceType(pricingType);
			List<ProductTag> productTags = ProductModelManager.getInstance().findProductTagsLevel3();

			// Tinh toan kich thuoc cho cell
			int totalCell = listProductPricePpt.size() + productTags.size();
			int numberRow = (totalCell % numberColumn) == 0 ? (totalCell / numberColumn) : (totalCell / numberColumn + 1);

			int heightPanel = 5 * (numberRow + 1) + sizeCell.height * numberRow;
			if (heightPanel > panelProducts.getSize().height) {
				Dimension newSize = new Dimension((sizeScrollPaneLeft.width - ((screenSize.width > 1024) ? 28 : 18)),
				    heightPanel);
				sizeCell = new Dimension((newSize.width / numberColumn) - 7, 65);
				panelProducts.setSize(new Dimension(panelProducts.getWidth(), heightPanel));
			}

			// Tạo product có bảng giá
			for (int i = 0; i < listProductPricePpt.size(); i++) {
				ComponentProduct panel;
				if (currency != null && listProductPricePpt.get(i).getCurrencyUnit().equals(currency.getCode())) {
					panel = new ComponentProduct(listProductPricePpt.get(i));
					panel.getLblMoney().setText(
					    (MyDouble.valueOf(listProductPricePpt.get(i).getPrice()).toString()) + "  " + currency.getName());
				} else {
					panel = new ComponentProduct(listProductPricePpt.get(i).getProduct());
					panel.getLblMoney().setText("0  " + currency.getName());
				}
				panel.setPreferredSize(sizeCell);
				panelProducts.add(panel);
				hashMapProducts.put(listProductPricePpt.get(i).getProduct().getCode(), panel);
			}

			// Tạo product không có bảng giá set giá = 0
			for (int i = 0; i < productsNotPpt.size(); i++) {
				ComponentProduct panel = new ComponentProduct(productsNotPpt.get(i));
				panel.setPreferredSize(sizeCell);
				panel.getLblMoney().setText("0  " + currency.getName());
				panelProducts.add(panel);
				hashMapProducts.put(productsNotPpt.get(i).getCode(), panel);
			}

			// Tạo productTag
			for (int i = 0; i < productTags.size(); i++) {
				ComponentProduct panel = new ComponentProduct(productTags.get(i));
				panel.setPreferredSize(sizeCell);
				panelProducts.add(panel);
				hashMapProducts.put(productTags.get(i).getCode(), panel);
			}

			panelProducts.updateUI();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.out.println("###$$$ TIME RUN QUERY1: "
		    + new DecimalFormat("#0.00000").format((System.currentTimeMillis() - startTime) / 1000d));
	}

	private String getCodeProductTagByTag(String tag) {
		return tag.substring(tag.lastIndexOf(":"));
	}

	public class ComponentProduct extends JPanel {
		private Product product = null;
		private ProductTag productTag = null;
		private ProductPrice productPrice = null;
		private JLabel lblMoney = null;
		private JLabel lblNameProduct;
		private JLabel lbAvatar = null;
		private ImageIcon iconProduct;

		public ComponentProduct(Object object) {
			if (object instanceof ProductPrice) {
				this.productPrice = (ProductPrice) object;
				this.product = productPrice.getProduct();
				this.setName(product.getCode());
				this.setBackground(colorProduct);
				this.setLayout(new BorderLayout(0, 0));
				this.setBorder(BorderFactory.createLineBorder(colorProduct, 5));
				lblNameProduct = new JLabel("<html><p align='center'>" + product.getName() + "</p></html>");
				lblNameProduct.setHorizontalAlignment(SwingConstants.CENTER);
				lblNameProduct.setFont(new Font("Tahoma", 1, 14));
				lblNameProduct.setName(product.getCode());
				lblMoney = new JLabel((MyDouble.valueOf(productPrice.getPrice()).toString()) + "  "
				    + productPrice.getCurrencyUnit());
				lblMoney.setFont(new Font("Tahoma", 0, 14));
				lblMoney.setHorizontalAlignment(SwingConstants.RIGHT);
				ProductImage productimage = product.getProImage();
				if (productimage != null && productimage.get("image") != null) {
					iconProduct = ImageTool.getInstance().decodeToImage(productimage.get("image").toString());
					lbAvatar = new JLabel("");
					lbAvatar.setBorder(BorderFactory.createEtchedBorder());
					JPanel jPanel = new JPanel(new BorderLayout(0, 0));
					jPanel.setOpaque(false);
					jPanel.add(lbAvatar, BorderLayout.WEST);
					jPanel.add(lblNameProduct, BorderLayout.CENTER);
					this.add(jPanel, BorderLayout.CENTER);
				} else
					this.add(lblNameProduct, BorderLayout.CENTER);
				this.add(lblMoney, BorderLayout.SOUTH);

				lblNameProduct.addMouseListener(new EventMouseClickProduct());
				lblMoney.addMouseListener(new EventMouseClickProduct());
				if (lbAvatar != null) {
					lbAvatar.addMouseListener(new EventMouseClickProduct());
				}
			} else {
				if (object instanceof ProductTag) {
					this.productTag = (ProductTag) object;
					this.setLayout(new BorderLayout(0, 0));
					this.setBackground(colorProductTag);
					this.setBorder(BorderFactory.createLineBorder(colorProductTag, 5));
					lblNameProduct = new JLabel("<html><p align='center'>" + productTag.getLabel() + "</p></html>");
					lblNameProduct.setHorizontalAlignment(SwingConstants.CENTER);
					lblNameProduct.setFont(new Font("Tahoma", 1, 14));
					lblNameProduct.setName(productTag.getTag());
					this.add(lblNameProduct);
					this.setName(productTag.getTag());

					lblNameProduct.addMouseListener(new EventMouseClickProductTag());
				} else {
					if (object instanceof Product) {
						this.productPrice = null;
						this.product = (Product) object;
						this.setName(product.getCode());
						this.setBackground(colorProduct);
						this.setLayout(new BorderLayout(0, 0));
						this.setBorder(BorderFactory.createLineBorder(colorProduct, 5));
						lblNameProduct = new JLabel("<html><p align='center'>" + product.getName() + "</p></html>");
						lblNameProduct.setHorizontalAlignment(SwingConstants.CENTER);
						lblNameProduct.setFont(new Font("Tahoma", 1, 14));
						lblNameProduct.setName(product.getCode());
						lblMoney = new JLabel("0  ");
						lblMoney.setFont(new Font("Tahoma", 0, 14));
						lblMoney.setHorizontalAlignment(SwingConstants.RIGHT);
						ProductImage productimage = product.getProImage();
						if (productimage != null && productimage.get("image") != null) {
							iconProduct = ImageTool.getInstance().decodeToImage(productimage.get("image").toString());
							lbAvatar = new JLabel("");
							lbAvatar.setBorder(BorderFactory.createEtchedBorder());
							JPanel jPanel = new JPanel(new BorderLayout(0, 0));
							jPanel.setOpaque(false);
							jPanel.add(lbAvatar, BorderLayout.WEST);
							jPanel.add(lblNameProduct, BorderLayout.CENTER);
							this.add(jPanel, BorderLayout.CENTER);
						} else
							this.add(lblNameProduct, BorderLayout.CENTER);
						this.add(lblMoney, BorderLayout.SOUTH);

						lblNameProduct.addMouseListener(new EventMouseClickProduct());
						lblMoney.addMouseListener(new EventMouseClickProduct());
						if (lbAvatar != null) {
							lbAvatar.addMouseListener(new EventMouseClickProduct());
						}
					}
				}
			}
		}

		@Override
		public void setPreferredSize(Dimension preferredSize) {
			super.setPreferredSize(preferredSize);
			setSize(preferredSize);
			if (lbAvatar != null && lblMoney != null) {
				Dimension dimension = new Dimension(preferredSize.width / 3, preferredSize.height - 23);
				lbAvatar.setPreferredSize(dimension);
				ImageIcon iconResize = ImageTool.getInstance().resize(iconProduct, dimension);
				lbAvatar.setIcon(iconResize);
				if (product.getName().length() > 30) {
					lblNameProduct.setFont(new Font("Tahoma", 1, 10));
				} else {
					if (product.getName().length() > 20) {
						lblNameProduct.setFont(new Font("Tahoma", 1, 12));
					}
				}
			}
		}

		public boolean isProductTag(String tag) {
			boolean flag = false;
			if (productPrice != null || product != null) {
				List<ProductTag> list = product.getProductTags();
				if (list != null && list.size() > 0) {
					for (ProductTag pt : list) {
						if (pt.getTag().equals(tag)) {
							flag = true;
							break;
						}
					}
				}
			} else {
				if (productTag != null && productTag.getParentTag().equals(tag)) {
					flag = true;
				}
			}
			return flag;
		}

		public boolean isCurrentUnit(Currency currency) {
			if (currency != null && currency.getCode().equals(productPrice.getCurrencyUnit())) {
				lblMoney.setText((MyDouble.valueOf(productPrice.getPrice()).toString()) + "  " + currency.getName());
				return true;
			} else
				return false;
		}

		public Product getProduct() {
			return product;
		}

		public void setProduct(Product product) {
			this.product = product;
			this.productPrice = null;
			lblMoney.setText("0  ");
		}

		public ProductPrice getProductPrice() {
			return productPrice;
		}

		public void setProductPrice(ProductPrice productPrice) {
			if (productPrice != null) {
				String currencyUnit = productPrice.getCurrencyUnit();
				if (this.productPrice != null && !this.productPrice.getCurrencyUnit().equals(productPrice.getCurrencyUnit())) {
					currencyUnit = LocaleModelManager.getInstance().getCurrencyByCode(productPrice.getCurrencyUnit()).getName();
				}
				this.productPrice = productPrice;
				this.product = productPrice.getProduct();
				lblMoney.setText((MyDouble.valueOf(productPrice.getPrice()).toString()) + "  " + currencyUnit);
			} else {
				this.productPrice = null;
				lblMoney.setText("0");
			}
			this.updateUI();
		}

		public JLabel getLbAvatar() {
			return lbAvatar;
		}

		public void setLbAvatar(JLabel lbAvatar) {
			this.lbAvatar = lbAvatar;
		}

		public JLabel getLblMoney() {
			return lblMoney;
		}

		public JLabel getLblNameProduct() {
			return lblNameProduct;
		}

		public ProductTag getProductTag() {
			return productTag;
		}

		public void setProductTag(ProductTag productTag) {
			this.productTag = productTag;
			lblNameProduct.setText("<html><p align='center'>" + productTag.getLabel() + "</p></html>");
		}

		public boolean isProduct() {
			if (product != null)
				return true;
			else
				return false;
		}
	}

	private class EventMouseClickProductTagRoot extends MouseAdapter {
		public EventMouseClickProductTagRoot() {
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (selectedProductTag2 != null) {
				selectedProductTag2.setBackground(Color.ORANGE);
			}
			selectedProductTag2 = (JLabel) e.getSource();
			selectedProductTag2.setBackground(Color.RED);

			if (selectedProduct != null) {
				if (selectedProduct.isProduct()) {
					selectedProduct.setBackground(colorProduct);
					selectedProduct.setBorder(BorderFactory.createLineBorder(colorProduct, 5));
				} else {
					selectedProduct.setBackground(colorProductTag);
					selectedProduct.setBorder(BorderFactory.createLineBorder(colorProductTag, 5));
				}
				selectedProduct = null;
			}

			// Duyệt toàn bộ sản phẩm trên giao diện nếu thuộc nhóm trên thì hiển thị
			int totalCell = 0;
			if (selectedProductTag2.getName().equals("allProduct"))
				totalCell = panelProducts.getComponentCount();
			for (int j = 0; j < panelProducts.getComponentCount(); j++) {
				ComponentProduct compProduct = ((ComponentProduct) panelProducts.getComponent(j));
				if (selectedProductTag2.getName().equals("allProduct")) {
					compProduct.setVisible(true);
				} else {
					if (compProduct.isProductTag(selectedProductTag2.getName())) {
						compProduct.setVisible(true);
						totalCell++;
					} else {
						compProduct.setVisible(false);
					}
				}
			}

			// Tính toán lại kích thước cho PanelProducts trong scrollPane
			loadSizePanelProduct(totalCell);
		}
	}

	private class EventMouseClickProductTag extends MouseAdapter {
		public EventMouseClickProductTag() {
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == mouseCountClick) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					if (selectedProduct != null) {
						if (selectedProduct.isProduct()) {
							selectedProduct.setBackground(colorProduct);
							selectedProduct.setBorder(BorderFactory.createLineBorder(colorProduct, 5));
						} else {
							selectedProduct.setBackground(colorProductTag);
							selectedProduct.setBorder(BorderFactory.createLineBorder(colorProductTag, 5));
						}
					}
					selectedProduct = (ComponentProduct) (((JLabel) e.getSource()).getParent());
					selectedProduct.setBackground(colorProductSelect);
					selectedProduct.setBorder(BorderFactory.createLineBorder(colorProductSelect, 5));
					// Duyệt toàn bộ sản phẩm trên giao diện nếu thuộc nhóm trên thì hiển
					// thị
					int totalCell = 0;
					for (int j = 0; j < panelProducts.getComponentCount(); j++) {
						ComponentProduct compProduct = ((ComponentProduct) panelProducts.getComponent(j));
						if (compProduct.isProductTag(selectedProduct.getName())) {
							compProduct.setVisible(true);
							totalCell++;
						} else {
							compProduct.setVisible(false);
						}
					}

					// Load lai kich thuoc
					loadSizePanelProduct(totalCell);
				} else {
					if (e.getButton() == MouseEvent.BUTTON3) {

					}
				}
			}
		}
	}

	private class EventMouseClickProduct extends MouseAdapter {
		public EventMouseClickProduct() {
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (flagScreenOften) {
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
					selectedProduct = (ComponentProduct) ((JPanel) (((JLabel) e.getSource()).getParent())).getParent();
				}
				// selectedProduct = (ComponentProduct) (((JLabel)
				// e.getSource()).getParent());
				selectedProduct.setBackground(colorProductSelect);
				selectedProduct.setBorder(BorderFactory.createLineBorder(colorProductSelect, 5));
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
		btnDatGio = new JButton();
		btnLichTrinh = new JButton();
		btnTraGop = new JButton();
		btnDiscountProduct = new JButton();
		btnExit = new JButton();
		lbMuaHo = new JLabel();
		lbNote = new JLabel();
		lbDiscount = new JLabel();

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

		btnDatGio.setFont(new java.awt.Font("Tahoma", 1, 12));
		btnDatGio.setText("<html><p align='center'>Đặt giờ</p></html>");
		btnDatGio.addMouseListener(new MouseEventClickButtonDialogPlus(null, null, null));
		btnDatGio.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnDatGio.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setupTimeProduct();
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

		btnLichTrinh.setFont(new java.awt.Font("Tahoma", 1, 12));
		btnLichTrinh.setText("<html><p align='center'>Lịch trình</p></html>");
		btnLichTrinh.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnLichTrinh.setPreferredSize(new java.awt.Dimension(69, 25));
		btnLichTrinh.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnLichTrinhActionPerformed(evt);
			}
		});

		btnTraGop.setFont(new java.awt.Font("Tahoma", 1, 12));
		btnTraGop.setText("<html><p align='center'>Trả góp</p></html>");
		btnTraGop.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnTraGop.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// btnTraGopActionPerformed(evt);
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

		lbMuaHo.setFont(new java.awt.Font("Tahoma", 0, 12));
		lbMuaHo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lbMuaHo.setText("<html><p align='center'>Mua hộ</p></html>");
		lbMuaHo.addMouseListener(new MouseEventMove() {
			public void mouseClicked(MouseEvent evt) {
				btnMuaHoSanPham();
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
		centerCenter.add(btnDatGio);
		centerCenter.add(btnLichTrinh);
		centerCenter.add(btnTraGop);
		centerCenter.add(lbDiscount);
		centerCenter.add(lbMuaHo);

		JPanel bottomCenter = new JPanel(new GridLayout(1, 1));
		bottomCenter.setOpaque(false);
		bottomCenter.add(btnExit);

		CONTAINER_CENTER.add(topCenter, BorderLayout.NORTH);
		CONTAINER_CENTER.add(centerCenter, BorderLayout.CENTER);
		CONTAINER_CENTER.add(bottomCenter, BorderLayout.SOUTH);
	}

	private void PanelColor() {
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

		jLabel8.setBackground(new java.awt.Color(0, 0, 0));
		jLabel8.setText("COLOR");
		jLabel8.setOpaque(true);
		jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11));
		jLabel11.setText(" Gọi đồ");

		jLabel9.setBackground(new java.awt.Color(36, 77, 126));
		jLabel9.setText("COLOR");
		jLabel9.setForeground(new java.awt.Color(36, 77, 126));
		jLabel9.setOpaque(true);
		jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11));
		jLabel17.setForeground(new java.awt.Color(36, 77, 126));
		jLabel17.setText(" Đã phục vụ");

		jLabel18.setBackground(new java.awt.Color(102, 0, 102));
		jLabel18.setText("COLOR");
		jLabel18.setForeground(new java.awt.Color(102, 0, 102));
		jLabel18.setOpaque(true);
		jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11));
		jLabel19.setForeground(new java.awt.Color(102, 0, 102));
		jLabel19.setText(" Đang chế biến");

		jLabel20.setBackground(new java.awt.Color(0, 72, 0));
		jLabel20.setText("COLOR");
		jLabel20.setForeground(new java.awt.Color(0, 72, 0));
		jLabel20.setOpaque(true);
		jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11));
		jLabel21.setForeground(new java.awt.Color(0, 72, 0));
		jLabel21.setText(" Đã thanh toán");

		jLabel22.setBackground(new java.awt.Color(252, 124, 4));
		jLabel22.setText("COLOR");
		jLabel22.setForeground(new java.awt.Color(252, 124, 4));
		jLabel22.setOpaque(true);
		jLabel23.setFont(new java.awt.Font("Tahoma", 1, 11));
		jLabel23.setForeground(new java.awt.Color(252, 124, 4));
		jLabel23.setText(" Chế biến xong");

		jLabel29.setBackground(new java.awt.Color(155, 43, 42));
		jLabel29.setText("COLOR");
		jLabel29.setForeground(new java.awt.Color(155, 43, 42));
		jLabel29.setOpaque(true);
		jLabel24.setFont(new java.awt.Font("Tahoma", 1, 11));
		jLabel24.setForeground(new java.awt.Color(155, 43, 42));
		jLabel24.setText(" Món hủy");

		Box textColor1 = Box.createHorizontalBox();
		textColor1.add(Box.createHorizontalStrut(15));
		textColor1.add(jLabel8);
		textColor1.add(jLabel11);
		Box textColor2 = Box.createHorizontalBox();
		textColor2.add(jLabel18);
		textColor2.add(jLabel19);
		Box textColor3 = Box.createHorizontalBox();
		textColor3.add(jLabel22);
		textColor3.add(jLabel23);
		Box textColor4 = Box.createHorizontalBox();
		textColor4.add(Box.createHorizontalStrut(15));
		textColor4.add(jLabel9);
		textColor4.add(jLabel17);
		Box textColor5 = Box.createHorizontalBox();
		textColor5.add(jLabel20);
		textColor5.add(jLabel21);
		Box textColor6 = Box.createHorizontalBox();
		textColor6.add(jLabel29);
		textColor6.add(jLabel24);

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
		lblKeyShortcutF2 = new javax.swing.JLabel();
		lblKeyShortcutF4 = new javax.swing.JLabel();
		lblKeyShortcutF6 = new javax.swing.JLabel();
		lblKeyShortcutF8 = new javax.swing.JLabel();
		lblKeyShortcutF3 = new javax.swing.JLabel();
		lblKeyShortcutF5 = new javax.swing.JLabel();
		lblKeyShortcutF7 = new javax.swing.JLabel();
		lblKeyShortcutF9 = new javax.swing.JLabel();
		lblKeyShortcutF10 = new javax.swing.JLabel();
		chbPoint = new javax.swing.JCheckBox();
		chbLichTrinh = new javax.swing.JCheckBox();
		chbTraGop = new javax.swing.JCheckBox();
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
		panelNotes.add(chbLichTrinh);
		panelNotes.add(chbTraGop);
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
		if (screenSize.width > 1024) {
			container.setPreferredSize(new Dimension(620, 100));
		} else {
			CONTAINER_CENTER.setPreferredSize(new Dimension(65, 100));
			if (screenSize.width == 1024)
				container.setPreferredSize(new Dimension(450, 100));
			else if (screenSize.width < 1024)
				container.setPreferredSize(new Dimension(350, 100));
		}
		/** Set kích thước size cho Panel chứa các Products */
		sizeScrollPaneLeft = new Dimension((screenSize.width - (container.getPreferredSize().width + 25)),
		    screenSize.height - 200);
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

		System.out.println("###$$$ TIME RUN QUERY1: "
		    + new DecimalFormat("#0.00000").format((System.currentTimeMillis() - startTime) / 1000d));
	}

	protected void btnDiscountProduct() {
		PanelDiscountProduct panel = new PanelDiscountProduct(panelPayment.getSelectedInvoiceItem());
		DialogResto dialog = new DialogResto(panel, false, 0, 170);
		dialog.setTitle("Chiết khấu sản phẩm");
		dialog.setLocationRelativeTo(null);
		dialog.setModal(true);
		dialog.setVisible(true);
		tableModel.updateItem(panel.getInvoiceItem());
	}

	protected void btnLichTrinhActionPerformed(ActionEvent evt) {
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
				txtPartner.setObject(customer);
				txtPartner.setText(customer.toString());
				// txtPartner.setItem(customer);
				txtPartner.setObject(null);
			}
		} else {
			// System.out.println(2);
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

	@SuppressWarnings("unused")
	protected void txtPartnerMouseClicked(MouseEvent event) {
		//
		// int click = 2;
		// if (txtPartner.getName().indexOf("") > 0 && event.getButton() == 3) {
		// click = 1;
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

	// Load lại giao diện sản phẩm
	public void loadGuiProduct(List<Product> products) {
		try {
			String unitMoney = currencyCode;
			Currency currency = LocaleModelManager.getInstance().getCurrencyByCode(currencyCode);
			if (currency != null) {
				unitMoney = currency.getName();
			}
			for (Product p : products) {
				ProductPrice pp = ProductModelManager.getInstance().getByProductAndProductPriceType(p.getCode(), pricingType,
				    currencyCode);
				// Nếu là thêm mới
				if (p.getPersistableState().equals(AbstractPersistable.State.NEW)) {
					// Tính toán kích thước cho Cell Product
					Dimension sizeCell;
					int numberColumn, w, h;

					// Nếu trong panelProducts chưa có Cell nào thì ta tính toán kích
					// thước
					if (screenSize.width < 1024) {
						numberColumn = 4;
						w = sizeScrollPaneLeft.width / numberColumn;
						h = 65;
						sizeCell = new Dimension(w - 7, h);
					} else {
						numberColumn = 5;
						w = sizeScrollPaneLeft.width / numberColumn;
						h = 65;
						sizeCell = new Dimension(w - 7, h);

						JScrollBar sb = scrollPane_Product.getVerticalScrollBar();
						sb.setPreferredSize(new Dimension(((screenSize.width > 1024) ? 25 : 15), 0));
					}
					// Nếu trong panelProducts đã có Cell rồi thì lấy kích thước Cell đó
					// và kiểm tra chiều cao Panel
					if (panelProducts.getComponentCount() > 0) {
						sizeCell = panelProducts.getComponent(0).getPreferredSize();
						int numberRow = (panelProducts.getComponentCount() + 1) / numberColumn;
						int heightPanel = 5 * (numberRow + 1) + sizeCell.height * numberRow;
						if (heightPanel > sizeScrollPaneLeft.height) {
							Dimension newSize = new Dimension((sizeScrollPaneLeft.width - ((screenSize.width > 1024) ? 28 : 18)),
							    heightPanel);
							sizeCell = new Dimension((newSize.width / numberColumn) - 7, 65);
							panelProducts.setSize(newSize);
							for (int j = 0; j < panelProducts.getComponentCount(); j++) {
								ComponentProduct panelCell = (ComponentProduct) panelProducts.getComponent(j);
								panelCell.setPreferredSize(sizeCell);
								panelCell.updateUI();
							}
						}
					}
					ComponentProduct panel;
					// Nếu sản phẩm có bảng giá thì tạo Panel truyền vào bảng giá đó
					if (pp != null) {
						panel = new ComponentProduct(pp);
						panel.getLblMoney().setText((MyDouble.valueOf(pp.getPrice()).toString()) + "  " + unitMoney);
					}
					// Nếu sản phẩm không có bảng giá thì tạo Panel sản phẩm với giá = 0
					else {
						panel = new ComponentProduct(p);
						panel.getLblMoney().setText("0  " + unitMoney);
					}

					panel.setPreferredSize(sizeCell);
					panelProducts.add(panel, 0);
					hashMapProducts.put(p.getCode(), panel);
					// Xem nhóm của sản phẩm có được chọn hay không?
					if ((selectedProductTag2 != null && !selectedProductTag2.getName().equals("allProduct") && !panel
					    .isProductTag(selectedProductTag2.getName()))) {
						panel.setVisible(false);
						if (selectedProduct != null && !selectedProduct.isProduct()
						    && panel.isProductTag(selectedProduct.getName()))
							panel.setVisible(true);
					} else
						panel.setVisible(true);
					// Cập nhật lại giao diện hiển thị
					panelProducts.updateUI();
				}
				// Nếu là sửa hoặc xóa
				else {
					ComponentProduct componentProduct = hashMapProducts.get(p.getCode());
					// Nếu trong giao diện đã có Panel cho Product này
					if (componentProduct != null) {
						// Trường hợp sửa Product
						if (p.getPersistableState().equals(AbstractPersistable.State.MODIFIED)) {
							if (pp != null) { // Nếu bảng giá khác null thì sửa giá đó
								componentProduct.setProductPrice(pp);
								componentProduct.getLblNameProduct().setText(p.getName());
								componentProduct.getLblMoney().setText(pp.getPrice() + "  " + unitMoney);
								componentProduct.updateUI();
							} else { // Nếu bảng giá null thì set giá = 0
								componentProduct.setProduct(p);
								componentProduct.getLblNameProduct().setText(p.getName());
								componentProduct.getLblMoney().setText("0  " + unitMoney);
								componentProduct.updateUI();
							}
						}
						// Trường hợp xóa Product
						else {
							if (p.getPersistableState().equals(AbstractPersistable.State.DELETED)) {
								panelProducts.remove(componentProduct);
								hashMapProducts.remove(componentProduct);
							}
						}
						// Load lai giao dien hien thi
						panelProducts.updateUI();
					}
					// Nếu giao diện chưa có Panel cho Product này
					else {
						// Trường hợp sửa Product mà giao diện chưa có Product này thì tạo
						// Panel Product đó
						if (p.getPersistableState().equals(AbstractPersistable.State.MODIFIED)) {
							// Tính toán kích thước cho Cell Product
							Dimension sizeCell;
							int numberColumn, w, h;

							// Nếu trong panelProducts chưa có Cell nào thì ta tính toán kích
							// thước
							if (screenSize.width < 1024) {
								numberColumn = 4;
								w = sizeScrollPaneLeft.width / numberColumn;
								h = 65;
								sizeCell = new Dimension(w - 7, h);
							} else {
								numberColumn = 5;
								w = sizeScrollPaneLeft.width / numberColumn;
								h = 65;
								sizeCell = new Dimension(w - 7, h);

								JScrollBar sb = scrollPane_Product.getVerticalScrollBar();
								sb.setPreferredSize(new Dimension(((screenSize.width > 1024) ? 25 : 15), 0));
							}
							// Nếu trong panelProducts đã có Cell rồi thì lấy kích thước Cell
							// đó và kiểm tra chiều cao Panel
							if (panelProducts.getComponentCount() > 0) {
								sizeCell = panelProducts.getComponent(0).getPreferredSize();
								int numberRow = (panelProducts.getComponentCount() + 1) / numberColumn;
								int heightPanel = 5 * (numberRow + 1) + sizeCell.height * numberRow;
								if (heightPanel > sizeScrollPaneLeft.height) {
									Dimension newSize = new Dimension((sizeScrollPaneLeft.width - ((screenSize.width > 1024) ? 28 : 18)),
									    heightPanel);
									sizeCell = new Dimension((newSize.width / numberColumn) - 7, 65);
									panelProducts.setSize(newSize);
									for (int j = 0; j < panelProducts.getComponentCount(); j++) {
										ComponentProduct panelCell = (ComponentProduct) panelProducts.getComponent(j);
										panelCell.setPreferredSize(sizeCell);
										panelCell.updateUI();
									}
								}
							}

							ComponentProduct panel;
							if (pp != null) {
								panel = new ComponentProduct(pp);
								panel.getLblMoney().setText((MyDouble.valueOf(pp.getPrice()).toString()) + "  " + unitMoney);
							} else {
								panel = new ComponentProduct(p);
								panel.getLblMoney().setText("0  " + unitMoney);
							}
							panel.setPreferredSize(sizeCell);
							panelProducts.add(panel, 0);
							hashMapProducts.put(p.getCode(), panel);

							// Xem nhóm của sản phẩm có được chọn hay không?
							panel.setVisible(false);
							if ((selectedProduct != null && panel.isProductTag(selectedProduct.getName()))) {
								panel.setVisible(true);
							} else {
								if (selectedProductTag2 != null
								    && (selectedProductTag2.getName().equals("allProduct") || panel.isProductTag(selectedProductTag2
								        .getName()))) {
									panel.setVisible(true);
								}
							}
							// Load lai giao dien hien thi
							panelProducts.updateUI();
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Load lại giao diện nhom sản phẩm
	public void loadGuiProductTag(ProductTag productTag) {
		// Đếm số : xem nó là nhóm cấp độ thứ mấy?
		int occurance = StringUtils.countOccurrencesOf(productTag.getTag(), ":");
		// Nếu là thêm mới nhóm sản phẩm
		if (productTag.getPersistableState().equals(AbstractPersistable.State.NEW)) {
			// Nhom cha cap 3
			if (occurance > 1) {
				// Tính toán kích thước cho Cell ProductTag
				Dimension sizeCell;
				int numberColumn, w, h;

				// Nếu trong panelProducts chưa có Cell nào thì ta tính toán kích thước
				if (screenSize.width < 1024) {
					numberColumn = 4;
					w = sizeScrollPaneLeft.width / numberColumn;
					h = 65;
					sizeCell = new Dimension(w - 7, h);
				} else {
					numberColumn = 5;
					w = sizeScrollPaneLeft.width / numberColumn;
					h = 65;
					sizeCell = new Dimension(w - 7, h);

					JScrollBar sb = scrollPane_Product.getVerticalScrollBar();
					sb.setPreferredSize(new Dimension(((screenSize.width > 1024) ? 25 : 15), 0));
				}
				// Nếu trong panelProducts đã có Cell rồi thì lấy kích thước Cell đó và
				// kiểm tra chiều cao Panel
				if (panelProducts.getComponentCount() > 0) {
					sizeCell = panelProducts.getComponent(0).getPreferredSize();
					int numberRow = (panelProducts.getComponentCount() + 1) / numberColumn;
					int heightPanel = 5 * (numberRow + 1) + sizeCell.height * numberRow;
					if (heightPanel > sizeScrollPaneLeft.height) {
						Dimension newSize = new Dimension((sizeScrollPaneLeft.width - ((screenSize.width > 1024) ? 28 : 18)),
						    heightPanel);
						sizeCell = new Dimension((newSize.width / numberColumn) - 7, 65);
						panelProducts.setSize(newSize);
						for (int j = 0; j < panelProducts.getComponentCount(); j++) {
							ComponentProduct panelCell = (ComponentProduct) panelProducts.getComponent(j);
							panelCell.setPreferredSize(sizeCell);
							panelCell.updateUI();
						}
					}
				}

				// Tạo ra Panel đại diện cho Product vừa thêm mới add vào
				ComponentProduct panel = new ComponentProduct(productTag);
				panel.setPreferredSize(sizeCell);
				panelProducts.add(panel);
				hashMapProducts.put(productTag.getCode(), panel);

				// Xem nhóm cha của nhóm này có được chọn hay không?
				if ((selectedProductTag2 != null && !selectedProductTag2.getName().equals("allProduct") && !panel
				    .isProductTag(selectedProductTag2.getName()))) {
					panel.setVisible(false);
					if (selectedProduct != null && !selectedProduct.isProduct() && panel.isProductTag(selectedProduct.getName()))
						panel.setVisible(true);
				} else
					panel.setVisible(true);
				// Cập nhật lại giao diện hiển thị
				panelProducts.updateUI();
			}
			// Nhom cha cap 2
			else {
				if (occurance == 1) {
					Dimension dimensionSizeCell;
					JLabel labelProductTagAll = (JLabel) hashMapProductTagRoot.get("allProduct");
					if (labelProductTagAll != null) { // Nếu trong hashMap đã có các phần
						                                // tử rồi thì lấy kích thước mặc
						                                // định
						dimensionSizeCell = labelProductTagAll.getPreferredSize();
					} else { // Nếu trong panel chưa có phần tử nào thì tạo kích thước
						// Tính toán kích thước mặc định cho phần tử đầu tiên
						Dimension sizeProductTagRoot = new Dimension(sizeScrollPaneLeft.width - 60, 50);
						int countCell;
						/** Chỉnh kích thước phù hợp màn hình */
						if (screenSize.width <= 1024) {
							countCell = 6;
						} else {
							countCell = 9;
						}
						int widthCell = (sizeProductTagRoot.getSize().width) / countCell;
						int heightCell = sizeProductTagRoot.getSize().height;
						dimensionSizeCell = new Dimension(widthCell, heightCell);
					}

					// Tạo JLabel đại diện cho nhóm sản phẩm đó
					JLabel lblProduct = new ExtendJLabel("<html><p align='center'>" + productTag.getLabel() + "</p></html>");
					lblProduct.setName(productTag.getTag());
					lblProduct.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, Color.LIGHT_GRAY));
					lblProduct.setHorizontalAlignment(SwingConstants.CENTER);
					lblProduct.setBackground(Color.ORANGE);
					lblProduct.setOpaque(true);
					lblProduct.setPreferredSize(dimensionSizeCell);
					lblProduct.addMouseListener(new EventMouseClickProductTagRoot());
					// Add vào hashMap
					List<JLabel> listJLabel = (List<JLabel>) hashMapProductTagRoot.get(productTag.getParentTag());
					if (listJLabel == null) {
						listJLabel = new ArrayList<JLabel>();
						hashMapProductTagRoot.put(productTag.getParentTag(), listJLabel);
					}
					listJLabel.add(lblProduct);
					// Kiểm tra nhóm cha nó có được chọn trong config ko? Nếu có add vào
					// panelProductTagRoot
					List<String> productTagConfig = (List<String>) hashMapProductTagRoot.get(DialogConfig.ProductTag);
					if (productTagConfig != null) {
						for (String s : productTagConfig) {
							if (s.equals(productTag.getParentTag())) {
								if (panelProductTagRoot.getComponentCount() < 1)
									panelProductTagRoot.add(labelProductTagAll);
								panelProductTagRoot.add(lblProduct, 1);
								int countCell = (screenSize.width <= 1024) ? 6 : 9;
								if (panelProductTagRoot.getComponentCount() < countCell) {
									panelProductTagRoot.setLayout(new FlowLayout(FlowLayout.LEADING, 2, 0));
								} else {
									if (panelProductTagRoot.getComponentCount() == countCell)
										lblProduct.setVisible(true);
									else {
										String[] numberShow = panelProductTagRoot.getName().toString().split(":");
										panelProductTagRoot.getComponent(Integer.parseInt(numberShow[1]) + 1).setVisible(false);
									}
									panelProductTagRoot.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 0));
								}
								panelProductTagRoot.updateUI();
								break;
							}
						}
					}
				}
			}
		}
		// Nếu là sửa hoặc xóa nhóm
		else {
			// Nếu là sửa
			if (productTag.getPersistableState().equals(AbstractPersistable.State.MODIFIED)) {
				// Nhom cha cap 3
				if (occurance > 1) {
					ComponentProduct componentProduct = hashMapProducts.get(productTag.getCode());
					if (componentProduct != null) {
						componentProduct.setProductTag(productTag);
						componentProduct.updateUI();
						panelProducts.updateUI();
						// Viet lenh hien thi khi nhom do dang dc chon va an khi ko dc chon
					}
				}
				// Nhom cha cap 2
				else {
					if (occurance == 1) {
						List<JLabel> listJLabel = (List<JLabel>) hashMapProductTagRoot.get(productTag.getParentTag());
						if (listJLabel != null) {
							for (JLabel label : listJLabel) {
								if (label.getName().equals(productTag.getTag())) {
									label.setText("<html><p align='center'>" + productTag.getLabel() + "</p></html>");
									label.updateUI();
									panelProductTagRoot.updateUI();
									break;
								}
							}
						}
					}
				}
			} else {
				// Nếu là xóa
				if (productTag.getPersistableState().equals(AbstractPersistable.State.DELETED)) {
					// Nhom cha cap 3
					if (occurance > 1) {
						ComponentProduct componentProduct = hashMapProducts.get(productTag.getCode());
						if (componentProduct != null) {
							panelProducts.remove(componentProduct);
							hashMapProducts.remove(hashMapProducts);
							panelProducts.updateUI();
						}
					}
					// Nhom cha cap 2
					else {
						if (occurance == 1) {
							List<JLabel> listJLabel = (List<JLabel>) hashMapProductTagRoot.get(productTag.getParentTag());
							if (listJLabel != null) {
								for (JLabel label : listJLabel) {
									if (label.getName().equals(productTag.getTag())) {
										int index = panelProductTagRoot.getComponentZOrder(label);
										// Co nam trong panelProductTagRoot thi moi remove
										if (index > 0) {
											String[] strArr = panelProductTagRoot.getName().toString().split(":");
											int indexStart = Integer.parseInt(strArr[0]);
											int indexEnd = Integer.parseInt(strArr[1]);
											// Neu nam trong khoang hien thi
											if (index >= indexStart && index <= indexEnd) {
												// Neu tren cung van con phan tu
												if (indexStart > 1) {
													panelProductTagRoot.getComponent(indexStart - 1).setVisible(true);
													indexStart = indexStart - 1;
													indexEnd = indexEnd - 1;
												}
												// Neu tren cung ko con phan tu. Kiem tra sau con ko?
												else {
													if (indexEnd < (panelProductTagRoot.getComponentCount() - 1)) {
														panelProductTagRoot.getComponent(indexEnd + 1).setVisible(true);
													} else {
														indexEnd = indexEnd - 1;
													}
												}
												panelProductTagRoot.setName(indexStart + ":" + indexEnd);
											} else {
												if (index < indexStart) {
													panelProductTagRoot.setName((indexStart - 1) + ":" + (indexEnd - 1));
												}
											}
											panelProductTagRoot.remove(label);
										}
										listJLabel.remove(label);
										panelProductTagRoot.updateUI();
										break;
									}
								}
							}
						}
					}
				}
			}
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
	protected void actionBtnPayment() throws Exception {
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
		final List<String> listProductCode = new ArrayList<String>();
		final Date dates = invoice.getStartDate();
		// if (invoice.getFinalCharge() != invoice.getTotalPaid()) {
		InvoiceTransaction transaction = new InvoiceTransaction();
		transaction.setTransactionType(TransactionType.Cash);
		transaction.setCreatedBy(ManagerAuthenticate.getInstance().getLoginId());
		transaction.setCurrencyUnit(currencyCode);
		transaction.setTotal(invoice.getFinalCharge() - invoice.getTotalPaid());
		transaction.setTransactionDate(new Date());
		invoice.add(transaction);
		if (invoice.getEndDate() == null) {
			if (profile.get("ForRent") != null) {
				invoice.setEndDate(panelPayment.getDateForRent().getDate());
			} else {
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

		if (!invoice.getStatus().equals(Status.Paid)) {
			if (profile.get("ForRent") != null && !panelPayment.getChbForRent().isSelected()
			    && !panelPayment.getChbPayment().isSelected()) {
				invoice.setStatus(Status.ForRent);
			} else {
				invoice.setStatus(Status.Paid);
			}

			try {
				invoice.setGuest(Integer.parseInt(txtGuest.getText()));
			} catch (Exception e) {
				invoice.setGuest(0);
			}
		}
		if (invoice.getTotalPaid() == 0) {
			for (InvoiceItem invoiceItem : invoice.getItems()) {
				if (!invoiceItem.getStatus().equals(AccountingModelManager.isCance)
				    && !invoiceItem.getStatus().equals(AccountingModelManager.isForRent)
				    && !invoiceItem.getStatus().equals(AccountingModelManager.isPromotion)) {
					invoiceItem.setStatus(AccountingModelManager.isPayment);
				}
				if (componentTable != null && componentTable.getTable() != null) {
					if (listProductCode.indexOf(invoiceItem.getProductCode()) < 0) {
						listProductCode.add(invoiceItem.getProductCode());
					}
				}
			}

			if (!printReceipt(true, true, null)) {
				DialogNotice.getInstace().setVisible(true);
			}
		} else {
			List<InvoiceItem> invoiceItems = new ArrayList<InvoiceItem>();
			for (InvoiceItem invoiceItem : invoice.getItems()) {
				if (!invoiceItem.getStatus().equals(AccountingModelManager.isPayment)) {
					invoiceItems.add(invoiceItem);
				}
			}
			if (!printReceipt(true, false, getInvoiceDetailPayment(invoiceItems))) {
				DialogNotice.getInstace().setVisible(true);
			}
			for (InvoiceItem invoiceItem : invoice.getItems()) {
				if (!invoiceItem.getStatus().equals(AccountingModelManager.isCance)
				    && !invoiceItem.getStatus().equals(AccountingModelManager.isForRent)
				    && !invoiceItem.getStatus().equals(AccountingModelManager.isPromotion)) {
					invoiceItem.setStatus(AccountingModelManager.isPayment);
				}
				if (componentTable != null && componentTable.getTable() != null) {
					if (listProductCode.indexOf(invoiceItem.getProductCode()) < 0) {
						listProductCode.add(invoiceItem.getProductCode());
					}
				}
			}
		}

		// long endTime1 = System.currentTimeMillis();
		// System.out.println("payment111111: "+ new
		// DecimalFormat("#0.00000").format((endTime1 - endTime) / 1000d));

		// long endTime2 = System.currentTimeMillis();
		// System.out.println("payment2: "+ new
		// DecimalFormat("#0.00000").format((endTime2 - endTime1) / 1000d));
		tableModel.updateStatus(AccountingModelManager.isPayment);
		invoice.setTotalPaid(invoice.getFinalCharge());
		invoice = AccountingModelManager.getInstance().saveInvoiceDetail(invoice);
		Thread thread = new Thread() {
			@Override
			public void run() {
				AccountingModelManager.getInstance().setList(new ArrayList<WarehouseInventory>());
				try {
					for (String str : listProductCode) {
						AccountingModelManager.getInstance().caculateReport(str, dates, false, true);
					}

					// TODO phải làm lại
				} catch (Exception e) {
				}
			}

		};
		thread.start();
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
						List<InvoiceItemAttribute> attributes = invoiceItem.getReferences();
						String productCode = invoiceItem.getProductCode();
						String provider = "";
						Date dateMenufacture = null;
						Date dateExpiry = null;
						double quantity = invoiceItem.getQuantity() - invoiceItem.getQuantityReal();
						for (InvoiceItemAttribute invoiceItemAttribute : attributes) {
							if (invoiceItemAttribute.getName().equals(WarehouseModelManager.PROVIDER)) {
								provider = invoiceItemAttribute.getValue();
							}
							if (invoiceItemAttribute.getName().equals("dateMenufacture")) {
								dateMenufacture = new SimpleDateFormat("dd/MM/yyyy").parse(invoiceItemAttribute.getValue());
							}
							if (invoiceItemAttribute.getName().equals("dateExpiry")) {
								dateExpiry = new SimpleDateFormat("dd/MM/yyyy").parse(invoiceItemAttribute.getValue());
							}
						}
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
									identityProduct.setProvider(provider);
									identityProduct.setCurrencyExportRate(invoiceItem.getCurrencyRate());
									identityProduct.setDateMenufacture(dateMenufacture);
									identityProduct.setDateExpiry(dateExpiry);
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

		reset();
		try {

			JLabel lblNumBussy = panelSalePOS.lblNumTableBussy;
			JLabel lblNumFree = panelSalePOS.lblNumTableFree;
			lblNumBussy.setText((Integer.parseInt(lblNumBussy.getText()) - 1) + "");
			lblNumFree.setText((Integer.parseInt(lblNumFree.getText()) + 1) + "");
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (!reset) {
			setEditorGui(true);
			dispose();
		}
	}

	private void resetGrossTable() throws Exception {
		if (componentTable.getTable().getReservations() != null && componentTable.getTable().getReservations().size() > 0) {
			for (int i = 0; i < componentTable.getTable().getReservations().size(); i++) {
				try {
					Table table = RestaurantModelManager.getInstance().getTableByCode(
					    componentTable.getTable().getReservations().get(i).getDescription());
					// if (table.getInvoiceCode() == null &&
					// componentTable.getTable().getStatus().equals(Table.Status.InActivate))
					// {
					table.setStatus(Table.Status.TableFree);
					table.setInvoiceCode(null);
					RestaurantModelManager.getInstance().saveTable(table);
					// }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			List<Reservation> li = new ArrayList<Reservation>();
			componentTable.getTable().setReservations(li);
			RestaurantModelManager.getInstance().saveTable(componentTable.getTable());
		}
	}

	// Trả sau hóa đơn
	protected void actionBtnTraSau() {
		
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
			try {
				tableModel.getInvoiceDetail().setCurrencyRate(
				    LocaleModelManager.getInstance().getCurrencyByCode(currencyCode).getRate());
			} catch (Exception e) {
				tableModel.getInvoiceDetail().setCurrencyRate(1);
			}
			tableModel.caculator();
			if (!tableModel.getInvoiceDetail().getStatus().equals(Status.Paid)
			    && !tableModel.getInvoiceDetail().getStatus().equals(Status.PostPaid)) {
				if (tableModel.getInvoiceDetail().getEndDate() == null) {
					tableModel.getInvoiceDetail().setEndDate(new Date());
				}
				String code = AccountingModelManager.getInstance().getCodeObject(PanelManageCodes.InvoiceBH,
				    AccountingModelManager.typeBanHang, tableModel.getInvoiceDetail().getStartDate(), true);
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
		saveStatusInvoice();

		reset();
		try {
			JLabel lblNumBussy = panelSalePOS.lblNumTableBussy;
			JLabel lblNumFree = panelSalePOS.lblNumTableFree;
			lblNumBussy.setText((Integer.parseInt(lblNumBussy.getText()) - 1) + "");
			lblNumFree.setText((Integer.parseInt(lblNumFree.getText()) + 1) + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		setEditorGui(true);
		dispose();
	}

	// In những món bị hủy
	protected void actionBtnInHuyMon() {
		String loginId = ManagerAuthenticate.getInstance().getOrganizationLoginId();
		String enterpriseName = AccountModelManager.getInstance().getNameByLoginId(loginId);
		String adres = AccountModelManager.getInstance().getAddressByLoginId(loginId);
		String fone = AccountModelManager.getInstance().getPhoneByLoginId(loginId);
		String fax = AccountModelManager.getInstance().getFaxByLoginId(loginId);
		String location = "";
		String table = lbNameTable.getText();
		if (componentTable != null) {
			location = componentTable.getTable().getLocationCode();
			table = componentTable.getTable().getName();
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
	protected void actionBtnInCheBien() {
		String loginId = ManagerAuthenticate.getInstance().getOrganizationLoginId();
		String enterpriseName = AccountModelManager.getInstance().getNameByLoginId(loginId);
		String adres = AccountModelManager.getInstance().getAddressByLoginId(loginId);
		String fone = AccountModelManager.getInstance().getPhoneByLoginId(loginId);
		String fax = AccountModelManager.getInstance().getFaxByLoginId(loginId);
		String location = "";
		String strNameTable = lbNameTable.getText();
		if (componentTable != null) {
			location = RestaurantModelManager.getInstance()
			    .getLocationByCode(tableModel.getInvoiceDetail().getLocationCode()).toString();
			strNameTable = componentTable.getTable().getName();
		}
		String[] str = { enterpriseName, adres, fone, fax, "Phiếu Chế Biến", location, strNameTable, lableTime.getText(),
		    txtEmployee.getText(), lableDate.getText(), dfTime.format(new Date()) };
		try {
			ReportPrint.getInstance().printCheBien(tableModel, str);
			tableModel.updateStatus(AccountingModelManager.isKitchen);
			componentTable.getTable().setStatus(Table.Status.TableKitchen);
			Table table = RestaurantModelManager.getInstance().saveTable(componentTable.getTable());
			componentTable.setTable(table);

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

	public void showDiscoutProduct(boolean a) {
		btnDiscountProduct.setVisible(a);
		lbDiscount.setText("Hiện CK");
		lbDiscount.setVisible(a);
	}

	// Reset
	public void reset() {

		if (componentTable != null) {
			componentTable.setTableStatus(Table.Status.TableFree);
			componentTable.getTable().setInvoiceCode(null);
			componentTable.getLblNumMoney().setText("0");
			componentTable.getLblTime().setText("");
			componentTable.getLblGuess().setText("");
			try {
				Table table = RestaurantModelManager.getInstance().saveTable(componentTable.getTable());
				componentTable.setTable(table);
				resetGrossTable();
			} catch (Exception ex) {
				// ex.printStackTrace();
			}
			try {
				panelSalePOS.getListTableBussy().remove(componentTable.getTable().getCode());
				panelSalePOS.getPanelTableSelected().setTableSelected(false);
				panelSalePOS.setPanelTableSelected(null);
			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		tableModel.setData(new InvoiceDetail(true));
		tableModel.changeCaculate("", "");
		componentTable = null;
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
		panelPayment.isResetGUIWaiters(true);
		panelPayment.getDateForRent().setDate(new Date());
		panelPayment.getChbForRent().setSelected(false);
		panelPayment.loadLableForRent(false);
		if (reset) {
			setTable(panelSalePOS.getComponentTableOther());
			setReset(true);
		}
	}

	// Xóa toàn bộ bàn
	protected void button_CActionPerformed(ActionEvent evt) {
		if (UIConfigModelManager.getInstance().getPermission(ScreenOften.permission) != Capability.ADMIN) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được phân quyền này !");
			return;
		} else {
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

						try {
							if (componentTable != null
							    && panelSalePOS.listTableBussy.get(componentTable.getTable().getCode()) != null) {
								JLabel lblNumBussy = panelSalePOS.lblNumTableBussy;
								JLabel lblNumFree = panelSalePOS.lblNumTableFree;
								lblNumBussy.setText((Integer.parseInt(lblNumBussy.getText()) - 1) + "");
								lblNumFree.setText((Integer.parseInt(lblNumFree.getText()) + 1) + "");
							}
						} catch (Exception e) {
							// TODO: handle exception
						}

						reset();
						if (!reset) {
							setEditorGui(true);
							dispose();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				reset();
				// if (componentTable != null) {
				// componentTable.setColorTable();
				// }
			}
		}
	}

	// Lưu trạng thái hóa đơn khi thanh toán
	private void saveStatusInvoice() {
		if (this.componentTable != null && this.componentTable.getTable() != null) {
			if (!this.componentTable.getTable().getStatus().equals(Table.Status.TableFree) && tableModel.getRowCount() > 0) {
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
					componentTable.getTable().setInvoiceCode(String.valueOf(tableModel.getInvoiceDetail().getId()));
					if (componentTable.getTable().getStatus().equals(Table.Status.TableGross)) {
						componentTable.setTableStatus(Table.Status.TableGross);
						componentTable.getLblGuess().setText("Gộp");
					} else {
						if (componentTable.getTable().getStatus().equals(Table.Status.TableKitchen)) {

						} else {
							componentTable.setTableStatus(Table.Status.TableBusy);
							componentTable.getLblGuess().setText(txtGuest.getText());
						}
					}
					componentTable.getLblTime().setText(lableTime.getText());
					componentTable.getLblNumMoney().setText(
					    new MyDouble(tableModel.getInvoiceDetail().getFinalCharge()).toString());
					try {
						Table table = RestaurantModelManager.getInstance().saveTable(componentTable.getTable());
						componentTable.setTable(table);
					} catch (Exception e) {
					}
					tableModel.getTablePromotion().saveAllPromotionProduct();
				} catch (Exception e) {
				}
			} else {
				try {
					if (tableModel.getInvoiceDetail().getId() != null) {
						PanelChoise pnPanel = new PanelChoise("Xin kiểm tra lại hóa đơn có chắc chắn xóa?");
						pnPanel.setName("Xoa");
						DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
						dialog1.setName("dlXoa");
						dialog1.setTitle("Xóa hóa đơn");
						dialog1.setLocationRelativeTo(null);
						dialog1.setModal(true);
						dialog1.setVisible(true);
						if (pnPanel.isDelete()) {
							AccountingModelManager.getInstance().deleteInvoice(tableModel.getInvoiceDetail());
						}
					}
				} catch (Exception exception) {
					exception.printStackTrace();
				} finally {
					componentTable.setTableStatus(Table.Status.TableFree);
					reset();
				}
			}
		} else {
			tableModel.setData(new InvoiceDetail(true));
			tableModel.changeCaculate("", "");
			lbNameTable.setText("");
			reset();
		}
	}

	protected void btnExitSystem() {
		if (profile.get("ForRent") != null && tableModel.getInvoiceDetail().getEndDate() != null) {
			setEditorGui(true);
			dispose();
			return;
		}
		if (tableModel.getInvoiceDetail() != null && tableModel.getInvoiceDetail().getId() != null) {
			updateTime();
			try {
				AccountingModelManager.getInstance().saveInvoice(tableModel.getInvoiceDetail());
			} catch (Exception e) {
			}
		}
		saveStatusInvoice();

		setEditorGui(true);
		dispose();
	}

	private void setEditorGui(boolean a) {
		if (UIConfigModelManager.getInstance().getPermission(ScreenOften.permission) != Capability.ADMIN) {
			canceEdit(a);
		}
		if (a) {
			resetGui();
		}
	}

	private void canceEdit(boolean edit) {
		List<Component> list = getAllComponents(this);
		for (Component c : list) {
			if (!c.equals(btnExit)) {
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
	public void setTable(ComponentTable componentTable) {
		// Set con trỏ chuột vào textfield này
		txtTextFieldTimKiem.requestFocus();
		try {
			// Không cho chỉnh sửa cell trong bảng
			panelPayment.getTable_Sales1().getCellEditor().stopCellEditing();
		} catch (Exception e) {
		}
		// Lưu lại hóa đơn nếu tồn tại
		// saveStatusInvoice();
		// Set bàn hiện tại được trỏ đến
		this.componentTable = componentTable;
		// Thiết lập các thông số hiển thị về bàn này
		panelPayment.setTable(componentTable.getTable());
		lbNameTable.setText(componentTable.toString());
		// Lấy ra hóa đơn của bàn này
		InvoiceDetail invoiceDetail = AccountingModelManager.getInstance().getInvoiceDetail(
		    componentTable.getTable().invoiceId());
		// Nếu hóa đơn null thì tạo mới nó
		if (invoiceDetail == null) {
			// Lấy ra tiền tệ được chọn trong file config để set vào hóa đơn
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
			invoiceDetail.setCurrencyUnit(currencyCode);
			invoiceDetail.setStartDate(new Date());
			invoiceDetail.setDiscount(0);
			invoiceDetail.setDiscountRate(0);
			try {
				invoiceDetail.setCurrencyRate(LocaleModelManager.getInstance().getCurrencyByCode(currencyCode).getRate());
			} catch (Exception e) {
				invoiceDetail.setCurrencyRate(1);
			}
			invoiceDetail.setInvoiceName(componentTable.getTable().getName());
			invoiceDetail.setTableCode(componentTable.getTable().getCode());
			invoiceDetail.setLocationCode(componentTable.getTable().getLocationCode());
			Location location = RestaurantModelManager.getInstance().getLocationByCode(invoiceDetail.getLocationCode());
			// Lấy bảng giá trong khu vực nếu thiết lập bán hàng chọn bảng giá khu vực
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
			// Lấy bảng giá mặc định chọn trong thiết lập bán hàng
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
			tableModel.setInfoInvoice("", pricingType, componentTable.getTable());
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
				if (invoiceDetail.getProjectCode().indexOf("/") == -1) {
					Project project = RestaurantModelManager.getInstance().getProjectByCode(invoiceDetail.getProjectCode());
					panelPayment.getTxtProject().setItem(project);
				} else {
					String[] pathProject = invoiceDetail.getProjectCode().split("/");
					Project project = RestaurantModelManager.getInstance().getProjectByCode(pathProject[pathProject.length - 1]);
					panelPayment.getTxtProject().setItem(project);
				}
			} else
				panelPayment.getTxtProject().setItem(null);

			if (invoiceDetail.getStoreCode() != null && !invoiceDetail.getStoreCode().equals(""))
				panelPayment.getStoreJComboBox().setSelectStoreByCode(invoiceDetail.getStoreCode());
			else
				panelPayment.getStoreJComboBox().setSelectedIndex(0);

			List<Contributor> contributors = invoiceDetail.getContributors();
			if (contributors != null) {
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

			if (componentTable.getTable().getStatus().equals(Table.Status.TableGross)) {
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
		componentTable = null;
		if (invoiceDetail != null) {
			panelPayment.getTxtInvoiceCode().setText(invoiceDetail.codeView());
			try {
				table = RestaurantModelManager.getInstance().getTableByCode(invoiceDetail.getTableCode());
				lbNameTable.setText(table.getName());
			} catch (Exception e) {
				table = new Table();
				table.setCode("a");
				table.setLocationCode("b");
			}

			tableModel.setData(invoiceDetail);
			try {
				currencyCode = invoiceDetail.getCurrencyUnit();
				panelPayment.setCurrencyCode(currencyCode);
			} catch (Exception e) {
			}
			try {
				if (invoiceDetail.getEmployeeCode() != null) {
					Employee employee = HRModelManager.getInstance().getEmployeeByCode(invoiceDetail.getEmployeeCode());
					txtEmployee.setItem(employee);
				}

			} catch (Exception e) {
				tableModel.setInfoInvoice("", pricingType, table);
			}
			panelPayment.refershData();

			lableDate.setText(dfDate.format(invoiceDetail.getStartDate()));
			lableTime.setText(dfTime.format(invoiceDetail.getStartDate()));
			txtGuest.setText(String.valueOf(invoiceDetail.getGuest()));
			// setEditorGui(false);
		}
		panelPayment.getDateForRent().setDate(invoiceDetail.getEndDate());
		panelPayment.getChbForRent().setSelected(false);
		if (invoiceDetail.getStatus().equals(Status.Paid)) {
			panelPayment.getChbForRent().setEnabled(false);
			panelPayment.getDateForRent().setEnabled(false);
		} else {
			panelPayment.getChbForRent().setEnabled(true);
			panelPayment.getDateForRent().setEnabled(true);
			panelPayment.loadLableForRent(true);
		}
		panelPayment.refeshCaculate();
		setEditorGui(false);
	}

	// Hiển thị khách hàng đã chọn lên text
	private void loadCustomerViewText() {
		try {
			Customer c = CustomerModelManager.getInstance()
			    .getCustomerByCode(tableModel.getInvoiceDetail().getCustomerCode());
			txtPartner.setItem(c);
			try {
				tableModel.setInfoInvoice(c.getLoginId(), pricingType, componentTable.getTable());
			} catch (Exception e) {
				tableModel.setInfoInvoice(c.getLoginId(), pricingType, table);
			}
		} catch (Exception e) {
			txtPartner.setItem(null);
			tableModel.setInfoInvoice("", pricingType, componentTable.getTable());
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
		// Kiểm tra invoice đã được lưu chưa?
		if (tableModel.getInvoiceDetail().getId() == null) {
			if (componentTable == null || componentTable.getTable() == null) {
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
			// Nếu invoice chưa lưu thì save lại
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
				invoiceItem.setStatus(stt);

				// create INvoiceItemAttribute

				if (menu != null) {
					DialogChoiseMenuItem item = new DialogChoiseMenuItem(menu);
					if (Toolkit.getDefaultToolkit().getScreenSize().width <= 1024) {
						DialogResto dialog = new DialogResto(item, false, 880, 500);
						dialog.setLocationRelativeTo(null);
						dialog.setModal(true);
						dialog.setTitle("Thêm sản phẩm tùy chọn");
						dialog.setVisible(true);
					} else {
						DialogResto dialog = new DialogResto(item, false, 810, 500);
						dialog.setLocationRelativeTo(null);
						dialog.setModal(true);
						dialog.setTitle("Thêm sản phẩm tùy chọn");
						dialog.setVisible(true);
					}
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
				if (!product.getCode().equals("kara")) {
					tableModel.addItem(invoiceItem);
				} else {
					tableModel.addItem(invoiceItem, priceKara);
				}

				tableModel.changeCaculate("", "");
				if (componentTable != null && componentTable.getTable() != null) {
					if (componentTable.getTable().getStatus().equals(Table.Status.TableSet)
					    || componentTable.getTable().getStatus().equals(Table.Status.TableFree)) {
						componentTable.getTable().setStatus(Table.Status.TableBusy);
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
			if (profile.get("ForRent") != null && invoiceItem.getStatus().equals(AccountingModelManager.isRecord)
			    && !panelPayment.getChbPayment().isSelected()) {
				product.setModifiedBy(AccountingModelManager.isForRent);
				addProduct(product);
			} else {
				product.setModifiedBy(null);
			}
			quantity = 1;
		} catch (Exception e) {
			quantity = 1;
			e.printStackTrace();
		}
	}

	// In toàn bộ hóa đơn

	private InvoiceDetail getInvoiceDetailPayment(List<InvoiceItem> invoiceItems) {
		InvoiceDetail invoiceDetail2 = new InvoiceDetail(true);
		for (InvoiceItem invoiceItem : invoiceItems) {
			invoiceDetail2.add(invoiceItem);
		}
		invoiceDetail2.calculate(new DefaultInvoiceCalculator());
		return invoiceDetail2;
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
		String tongSL="";
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
		String nvpv = "";
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
		String strMoney = ManagerConvert.getInstance().convertMoney(phaiTra);
		Date dateEnd = new Date();
		if (invoiceDetail.getStatus().equals(Status.Paid)) {
			if(tableModel.getInvoiceDetail().getEndDate()!=null){
				if(tableModel.getInvoiceDetail().getTransactions()!=null
						&&!tableModel.getInvoiceDetail().getTransactions().isEmpty()){
					dateEnd = tableModel.getInvoiceDetail().getTransactions().get(tableModel.getInvoiceDetail().getTransactions().size()-1).getTransactionDate();
				}else{
					dateEnd=tableModel.getInvoiceDetail().getEndDate();
				}
			}
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
		    dfDate.format(dateEnd), gioRa, currencyCode, MyDouble.valueOf(invoiceDetail.getService()).toString(), ratio, moneyQuyDoi, 
		    MyDouble.valueOf(invoiceDetail.getServiceRate()).toString(), strMoney, soDiemDung,
		    MyDouble.valueOf(invoiceDetail.getPoint()).toString(), txtPartner.getText(), txtGuest.getText(), nvpv, credit, lienHoaDon,
		    solanIn, tongSoDiem, soDiemConLai, tongSoTien, soTienConLai, credit,
		    MyDouble.valueOf(invoiceDetail.getCredit()).toString(), tongSL, PanelTextMoneyPayment.getSoTienKhachDua(),
		    PanelTextMoneyPayment.getSoTienLeTraKhach(), diaChi, sdt, AccountingModelManager.pay };
		try {
			String name = invoiceDetail.getInvoiceCode() + String.valueOf(invoiceDetail.getTransactions().size());
			if (profile.get("Exel") != null && profile.get("Exel").equals("true")) {
				name = null;
			}
			if (!exel) {
				name = null;
			}
			Table table = this.table;
			if(componentTable!=null && componentTable.getTable()!=null){
				table = componentTable.getTable();
			}
			if (paymentSlip != null) {
				return ReportPrint.getInstance().printReceiptSlip(table, tableModel, invoiceDetail.getItems(), str, view);
			} else {
				return ReportPrint.getInstance().printReceipt(table, tableModel, tableModel.getTablePromotion(), str, view,
				    name);
			}
		} catch (Exception e) {
			return false;
		}

	}

	// Thanh toán lẻ
	private void actionBtnPayOnce() {
		// Thanh toán lẻ
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

	// Đặt giờ cho sản phẩm (Ka ra)
	private void setupTimeProduct() {
		try {
			PanelTimed panel = new PanelTimed();
			try {
				panel.setInfo(pricingType, componentTable.getTable().getLocationCode(), componentTable.getTable().getCode());
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
	private void btnMuaHoSanPham() {
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
			tableModel.setInfoInvoice(customer.getLoginId(), pricingType, componentTable.getTable());
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
	private void actionBtnPrintInvoice() {
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
		if (dialogPrint.getPrint() == 3) {
			printAgain = true;
			printReceipt(false, false, null);
			printAgain = false;
		} else {
			if (dialogPrint.getPrint() == 1) {
				double totalP = tableModel.getInvoiceDetail().getTotalPaid();
				
				if (tableModel.getInvoiceDetail().getStatus().equals(Status.Paid)) {
					tableModel.getInvoiceDetail().setTotalPaid(0);
				}
				printAgain = true;
		   	boolean print=	printReceipt(true, false, null);
				printAgain = false;
				if (tableModel.getInvoiceDetail().getStatus().equals(Status.Paid)) {
					tableModel.getInvoiceDetail().setTotalPaid(totalP);
				}
				if (componentTable != null && print) {
					try {
						Table table = RestaurantModelManager.getInstance().saveTable(componentTable.getTable());
						componentTable.setTable(table);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
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
				actionBtnInHuyMon();
			}
		});

		// Phím tắt in hóa đơn
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0, false), "F4");
		jc.getActionMap().put("F4", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				actionBtnPrintInvoice();
			}
		});

		// Phím tắt in chế biến
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0, false), "F2");
		jc.getActionMap().put("F2", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				actionBtnInCheBien();
			}
		});

		// Phím tắt thanh toán
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0, false), "F5");
		jc.getActionMap().put("F5", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					actionBtnPayment();
				} catch (Exception e) {
				}
			}
		});

		// Phím tắt thanh toán lẻ
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0, false), "F6");
		jc.getActionMap().put("F6", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				actionBtnPayOnce();
			}
		});

		// Phím tắt trả sau
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0, false), "F7");
		jc.getActionMap().put("F7", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				actionBtnTraSau();
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

	public HashMap<String, ComponentProduct> getHashMapProducts() {
		return hashMapProducts;
	}

	public HashMap<String, Object> getHashMapProductTagRoot() {
		return hashMapProductTagRoot;
	}

	public JPanel getPanelProducts() {
		return panelProducts;
	}

	public JPanel getPanelProductTagRoot() {
		return panelProductTagRoot;
	}

	public JPanel getPanelTop2() {
		return panelTop2;
	}

	public JScrollPane getScrollPane_Product() {
		return scrollPane_Product;
	}

	public String getPricingType() {
		return pricingType;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public static boolean isFlagScreenOften() {
		return flagScreenOften;
	}

	public static void setFlagScreenOften(boolean flagScreenOften) {
		DialogScreenOftenPos.flagScreenOften = flagScreenOften;
	}

	public JLabel getSelectedProductTag2() {
		return selectedProductTag2;
	}

	public void setSelectedProductTag2(JLabel selectedProductTag2) {
		this.selectedProductTag2 = selectedProductTag2;
	}

	public ComponentProduct getSelectedProduct() {
		return selectedProduct;
	}

	public void setSelectedProduct(ComponentProduct selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

	@Override
	public void setListProduct(List<Product> list) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTable(TableEat tableEat) {
		// TODO Auto-generated method stub

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

}
