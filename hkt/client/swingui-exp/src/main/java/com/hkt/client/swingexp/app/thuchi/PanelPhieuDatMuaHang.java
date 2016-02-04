package com.hkt.client.swingexp.app.thuchi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.hkt.client.swing.widget.GridBagLayoutPanel;
import com.hkt.client.swingexp.app.banhang.PanelProductPriceType;
import com.hkt.client.swingexp.app.banhang.list.ChooseDelProduct;
import com.hkt.client.swingexp.app.banhang.list.PanelChoosePrice;
import com.hkt.client.swingexp.app.banhang.list.PanelChooseProductExport;
import com.hkt.client.swingexp.app.banhang.list.PanelChooseQuantity;
import com.hkt.client.swingexp.app.banhang.list.PanelUpfrontPayment;
import com.hkt.client.swingexp.app.banhang.screen.often.DialogConfig;
import com.hkt.client.swingexp.app.banhang.screen.often.DialogPayOnce;
import com.hkt.client.swingexp.app.banhang.screen.often.DialogPrint;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelListPartnerOrganization;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelListSupplierOrganization;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelPayment;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelPaymentProduct;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelProduct;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelTextMoneyPayment;
import com.hkt.client.swingexp.app.banhang.screen.often.TableEat;
import com.hkt.client.swingexp.app.banhang.screen.often.TableModelInvoiceItem;
import com.hkt.client.swingexp.app.component.ExtendJCheckBox;
import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.component.PanelBackground;
import com.hkt.client.swingexp.app.component.TextPopup;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.IMyObserver;
import com.hkt.client.swingexp.app.core.IScreenSales;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDialogPlus;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDown;
import com.hkt.client.swingexp.app.core.MouseEventMove;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.core.UnitMoneyJComboBox;
import com.hkt.client.swingexp.app.core.WarehousesJComboBox;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.app.khohang.CatalogProductTag;
import com.hkt.client.swingexp.app.khohang.PanelRepositoryProducts2;
import com.hkt.client.swingexp.app.khuvucbanhang.PanelManageCodes;
import com.hkt.client.swingexp.app.khuyenmai.DialogChoiseMenuItem;
import com.hkt.client.swingexp.app.print.ReportPrint;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.ManagerConvert;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.PromotionModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.SupplierModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.client.swingexp.model.WarehouseModelManager;
import com.hkt.module.account.entity.Account;
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
import com.hkt.module.config.locale.Currency;
import com.hkt.module.config.locale.ProductUnit;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.supplier.entity.Supplier;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductPrice;
import com.hkt.module.product.entity.ProductPriceType;
import com.hkt.module.product.entity.ProductTag;
import com.hkt.module.promotion.entity.Menu;
import com.hkt.module.restaurant.entity.Table;
import com.hkt.module.warehouse.entity.IdentityProduct;
import com.hkt.module.warehouse.entity.IdentityProduct.ExportType;
import com.hkt.module.warehouse.entity.IdentityProduct.ImportType;
import com.hkt.module.warehouse.entity.Warehouse;
import com.hkt.util.text.DateUtil;

/*
 * @author: Trung Long
 * @edit: Phan Anh
 * @date: 25/11/2014
 */

public class PanelPhieuDatMuaHang extends MyPanel implements IDialogResto, IMyObserver, IScreenSales {
	public static final int PAID = 1;
	public static final int UNPAID = 2;
	public static final int UNPAID_AND_NOTIMPORT = 3;
	public static final int PAID_AND_NOTIMPORT = 4;
	private PanelProduct panelListProduct;
	private List<ProductTag> listProductGroup = new ArrayList<ProductTag>();
	private JComboBox cbNhomSP;
	private List<ProductTag> groupProducts;
	private JScrollPane scrollPaneProduct;
	private JComboBox cbBangGia;
	private WarehousesJComboBox cbKho;
	private UnitMoneyJComboBox cbDVTien;
	private TextPopup<Employee> txtNhanVien;
	private TextPopup txtKhachHang;
	private boolean printAgain;
	private JTextField txtTenPhieu, txtPrice;
	private JButton btnInHoaDon, btnThanhToanLe, btnLuu, btnThemSP, btnCTKM, btnNhapKho, btnThanhToan;
	private JButton btnXoa, btnHuy, btnSoLuong, btnDoiGia, btnNgayHetHan, btnThoat;
	private JCheckBox chbKho;
	private int statusInvoice = 0;
	private boolean flagEdit = true;
	private TextPopup<Product> txtTimKiem;
	private JLabel lbKM, lbPrice, lbKhachHang;
	private JTextField lableDate, lableTime;
	private ActivityType activityType;
	private String type;
	private boolean flag;
	private TableModelInvoiceItem tableModel;
	private PanelPayment panelPayment;
	private double quantity = 1;
	public String CHECKIMPORT = "checkImport";
	public boolean flagChangeMoney = false;
	public static String permission1, permission2, permission3, permission4;
	private String currencyCode;
	private JPanel background;
	private Profile profile;
	private InvoiceDetail invoiceDetail;
	private String name;
	private GridBagLayoutPanel bagLayoutPanel;
	private boolean exit;
	private Product product;
	private double price = -1;
	private DateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat dfTime = new SimpleDateFormat("HH:mm");
	private JTextField txtSoLuong;

	public PanelPhieuDatMuaHang(InvoiceDetail invoiceDetail, int statusInvoice, String name) {
		try {
			txtTenPhieu = new ExtendJTextField();
			exit = true;
			this.name = name;
			flagChangeMoney = true;
			try {
				panelPayment.getTxtInvoiceCode().setText(invoiceDetail.codeView());
			} catch (Exception e) {
			}

			this.type = invoiceDetail.getType();
			panelListProduct = new PanelProduct(this);
			this.activityType = invoiceDetail.getActivityType();
			this.statusInvoice = statusInvoice;
			this.invoiceDetail = invoiceDetail;
			initBackground();
			addKeyBindings(background);
			tableModel.setData(invoiceDetail);
			tableModel.changeCaculate("", "");
			refresh();
			currencyCode = invoiceDetail.getCurrencyUnit();
			cbDVTien.setSelectedCurrency(currencyCode);
			cbDVTien.setEnabled(false);
			panelPayment.setCurrencyCode(currencyCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PanelPhieuDatMuaHang(ActivityType activityType, String type, String name) {
		exit = false;
		this.type = type;
		txtTenPhieu = new ExtendJTextField();
		this.activityType = activityType;
		this.name = name;

		panelListProduct = new PanelProduct(this);
		createBeginTest();
		try {
			init();
			addKeyBindings(background);
		} catch (Exception e) {
		}
		InvoiceDetail invoiceDetail = new InvoiceDetail(true);
		invoiceDetail.setInvoiceCode(DateUtil.asCompactDateTimeId(new Date()));
		String code = AccountingModelManager.getInstance().getCodeObject(PanelManageCodes.InvoiceDMH,
		    AccountingModelManager.typeBanHang, new Date(), false);
		if (code != null) {
			panelPayment.getTxtInvoiceCode().setText("");
		} else {
			panelPayment.getTxtInvoiceCode().setText(invoiceDetail.codeView());
		}

		invoiceDetail.setActivityType(this.activityType);
		invoiceDetail.setStatus(Status.WaitingPaid);
		invoiceDetail.setType(type);
		invoiceDetail.setCurrencyUnit(cbDVTien.getSelectedCurrency().getCode());
		invoiceDetail.setStartDate(new Date());
		invoiceDetail.setDiscount(0);
		invoiceDetail.setInvoiceName("");

		tableModel.setData(invoiceDetail);
		currencyCode = cbDVTien.getSelectedCurrency().getCode();
	}

	private void initBackground() {

		try {
			profile = AccountModelManager.getInstance().getProfileConfigAdmin();
			if (profile.get(DialogConfig.Keyboard) != null && profile.get(DialogConfig.Keyboard).toString().equals("true")) {

				background = new JPanel();
				background.setOpaque(false);
			} else {
				background = new PanelBackground();
				background.setOpaque(false);
				if (name.equals("PDH")) {
					if (type.equals(AccountingModelManager.typeSanXuat)) {
						background.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,
						    null, new Color(204, 204, 204), null, null), "Phiếu nhập kho", TitledBorder.DEFAULT_JUSTIFICATION,
						    TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 1, 14), new Color(126, 0, 0)));
						txtTenPhieu.setText("Phiếu nhập kho");
					} else {
						background.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,
						    null, new Color(204, 204, 204), null, null), "Phiếu mua hàng", TitledBorder.DEFAULT_JUSTIFICATION,
						    TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 1, 14), new Color(126, 0, 0)));
						txtTenPhieu.setText("Cty đặt hàng");
					}

				} else if (name.equals("KDH")) {
					if (type.equals(AccountingModelManager.typeSanXuat)) {
						background.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,
						    null, new Color(204, 204, 204), null, null), "Phiếu xuất kho", TitledBorder.DEFAULT_JUSTIFICATION,
						    TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 1, 14), new Color(126, 0, 0)));
						txtTenPhieu.setText("Phiếu xuất kho");
					} else {
						background.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,
						    null, new Color(204, 204, 204), null, null), "Khách đặt hàng", TitledBorder.DEFAULT_JUSTIFICATION,
						    TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 1, 14), new Color(126, 0, 0)));
						txtTenPhieu.setText("Khách đặt hàng");
					}

				} else if (name.equals("Phiếu trả hàng")) {
					background.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,
					    null, new Color(204, 204, 204), null, null), "Phiếu trả hàng", TitledBorder.DEFAULT_JUSTIFICATION,
					    TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 1, 14), new Color(126, 0, 0)));
					txtTenPhieu.setText("Cty trả hàng");
				} else {
					background.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,
					    null, new Color(204, 204, 204), null, null), "Khách trả hàng", TitledBorder.DEFAULT_JUSTIFICATION,
					    TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 1, 14), new Color(126, 0, 0)));
					txtTenPhieu.setText("Khách trả hàng");
				}

			}
		} catch (Exception e) {
		}

		background.setLayout(new BorderLayout(0, 0));
		background.add(new PANEL_LEFT_CONTENT(), BorderLayout.LINE_START);
		background.add(new PANEL_RIGHT_CONTENT(), BorderLayout.CENTER);
		JLabel label = new ExtendJLabel("");
		label.setPreferredSize(new Dimension(10, 0));
		background.add(label, BorderLayout.PAGE_END);

		this.setLayout(new BorderLayout(0, 0));
		this.add(background, BorderLayout.CENTER);
		this.setOpaque(false);
		getItemPriceType();

		if (AccountModelManager.getInstance().getProfileConfigAdmin().get(CHECKIMPORT) != null
		    && AccountModelManager.getInstance().getProfileConfigAdmin().get(CHECKIMPORT).equals("true")) {
			chbKho.setSelected(true);
			// statusInvoice = PAID;
		} else {
			chbKho.setSelected(false);
			// statusInvoice = PAID_AND_NOTIMPORT;
		}

		chbKho.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				Profile p = AccountModelManager.getInstance().getProfileConfigAdmin();
				if (chbKho.isSelected()) {
					p.put(CHECKIMPORT, "true");
					statusInvoice = PAID;
				} else {
					p.put(CHECKIMPORT, "false");
					statusInvoice = PAID_AND_NOTIMPORT;
				}
				AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(),
				    p);
			}
		});

		txtKhachHang.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				try {
					if (event.getClickCount() >= 2) {
						choiseCustomer();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		txtTimKiem.addObserver(this);
		txtNhanVien.addObserver(this);

		panelPayment.getTxtMoneyMissing().addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				if (tableModel.getRowCount() > 0) {
					cbDVTien.setEnabled(false);
				} else {
					cbDVTien.setEnabled(true);
				}
			}
		});
		cbDVTien.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				try {
					currencyCode = cbDVTien.getSelectedCurrency().getCode();
					panelPayment.setCurrencyCode(currencyCode);
					tableModel.getInvoiceDetail().setCurrencyUnit(currencyCode);
				} catch (Exception e2) {
				}

			}
		});
		txtPrice.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (product != null) {
						try {
							price = MyDouble.parseDouble(txtPrice.getText());
						} catch (Exception e2) {
							price = 0;
						}
						addProduct(product);
						txtSoLuong.setText("");
						txtPrice.setText("");
						txtTimKiem.setItem(null);
						txtTimKiem.requestFocus();
						price = -1;
						product = null;
					}
				}
			}
		});
	}

	private void init() {
		try {
			profile = AccountModelManager.getInstance().getProfileConfigAdmin();
			if (profile.get(DialogConfig.Keyboard) != null && profile.get(DialogConfig.Keyboard).toString().equals("true")) {

				background = new JPanel();
				background.setOpaque(false);
			} else {
				background = new PanelBackground();
				background.setOpaque(false);
				if (name.equals("PDH")) {
					if (type.equals(AccountingModelManager.typeSanXuat)) {
						background.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,
						    null, new Color(204, 204, 204), null, null), "Phiếu nhập kho", TitledBorder.DEFAULT_JUSTIFICATION,
						    TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 1, 14), new Color(126, 0, 0)));
						txtTenPhieu.setText("Phiếu nhập kho");
					} else {
						background.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,
						    null, new Color(204, 204, 204), null, null), "Phiếu mua hàng", TitledBorder.DEFAULT_JUSTIFICATION,
						    TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 1, 14), new Color(126, 0, 0)));
						txtTenPhieu.setText("Cty đặt hàng");
					}

				} else if (name.equals("KDH")) {
					if (type.equals(AccountingModelManager.typeSanXuat)) {
						background.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,
						    null, new Color(204, 204, 204), null, null), "Phiếu xuất kho", TitledBorder.DEFAULT_JUSTIFICATION,
						    TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 1, 14), new Color(126, 0, 0)));
						txtTenPhieu.setText("Phiếu xuất kho");
					} else {
						background.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,
						    null, new Color(204, 204, 204), null, null), "Khách đặt hàng", TitledBorder.DEFAULT_JUSTIFICATION,
						    TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 1, 14), new Color(126, 0, 0)));
						txtTenPhieu.setText("Khách đặt hàng");
					}

				} else if (name.equals("Phiếu trả hàng")) {
					background.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,
					    null, new Color(204, 204, 204), null, null), "Phiếu trả hàng", TitledBorder.DEFAULT_JUSTIFICATION,
					    TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 1, 14), new Color(126, 0, 0)));
					txtTenPhieu.setText("Cty trả hàng");
				} else {
					background.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,
					    null, new Color(204, 204, 204), null, null), "Khách trả hàng", TitledBorder.DEFAULT_JUSTIFICATION,
					    TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 1, 14), new Color(126, 0, 0)));
					txtTenPhieu.setText("Khách trả hàng");
				}
			}
		} catch (Exception e) {
		}

		background.setLayout(new BorderLayout(0, 0));
		background.add(new PANEL_LEFT_CONTENT(), BorderLayout.LINE_START);
		background.add(new PANEL_RIGHT_CONTENT(), BorderLayout.CENTER);
		JLabel label = new ExtendJLabel("");
		label.setPreferredSize(new Dimension(10, 0));
		background.add(label, BorderLayout.PAGE_END);

		this.setLayout(new BorderLayout(0, 0));
		this.add(background, BorderLayout.CENTER);
		this.setOpaque(false);
		getItemPriceType();

		if (AccountModelManager.getInstance().getProfileConfigAdmin().get(CHECKIMPORT) != null
		    && AccountModelManager.getInstance().getProfileConfigAdmin().get(CHECKIMPORT).equals("true")) {
			chbKho.setSelected(true);
			// statusInvoice = PAID;
		} else {
			chbKho.setSelected(false);
			// statusInvoice = PAID_AND_NOTIMPORT;
		}

		chbKho.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				Profile p = AccountModelManager.getInstance().getProfileConfigAdmin();
				if (chbKho.isSelected()) {
					p.put(CHECKIMPORT, "true");
					statusInvoice = PAID;
				} else {
					p.put(CHECKIMPORT, "false");
					statusInvoice = PAID_AND_NOTIMPORT;
				}
				AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(),
				    p);
			}
		});

		txtKhachHang.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				try {
					if (event.getClickCount() >= 2) {
						choiseCustomer();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		txtTimKiem.addObserver(this);
		txtNhanVien.addObserver(this);

		panelPayment.getTxtMoneyMissing().addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				if (flagChangeMoney) {
					double m = MyDouble.parseDouble(panelPayment.getTxtMoneyMissing().getText().toString());
					if (m == 0) {
						btnThanhToan.setEnabled(false);
					} else {
						btnThanhToan.setEnabled(true);
					}
				}
				if (tableModel.getRowCount() > 0) {
					cbDVTien.setEnabled(false);
				} else {
					cbDVTien.setEnabled(true);
				}
			}
		});

		cbDVTien.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				try {
					currencyCode = cbDVTien.getSelectedCurrency().getCode();
					panelPayment.setCurrencyCode(currencyCode);
					tableModel.getInvoiceDetail().setCurrencyUnit(currencyCode);
				} catch (Exception e2) {
				}
			}
		});

		if (type.equals(AccountingModelManager.typeSanXuat)) {
			chbKho.setSelected(true);
			chbKho.setEnabled(false);
			btnInHoaDon.setEnabled(false);
			btnThanhToan.setEnabled(false);
			btnThanhToanLe.setEnabled(false);
			btnLuu.setEnabled(false);
			btnCTKM.setEnabled(false);
			panelPayment.getTabbedPane().setVisible(false);
		}
		txtPrice.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (product != null) {
						try {
							price = MyDouble.parseDouble(txtPrice.getText());
						} catch (Exception e2) {
							price = 0;
						}
						addProduct(product);
						txtSoLuong.setText("");
						txtPrice.setText("");
						txtTimKiem.setItem(null);
						txtTimKiem.requestFocus();
						price = -1;
						product = null;
					}
				}
			}
		});
	}

	/**
	 * Nội dung Panel trái
	 */
	private class PANEL_LEFT_CONTENT extends JPanel {

		public PANEL_LEFT_CONTENT() {
			setOpaque(false);
			setLayout(new BorderLayout(10, 10));
			add(new LEFT_PAGESTART_PanelSearch(), BorderLayout.PAGE_START);
			JPanel main = new JPanel(new GridLayout(1, 2, 5, 0));
			main.setOpaque(false);
			main.add(new LEFT_LINESTART_PanelCategory());
			main.add(new LEFT_LINEEND_PanelListProduct());
			add(main, BorderLayout.CENTER);
		}
	}

	private class LEFT_PAGESTART_PanelSearch extends JPanel {
		private JLabel lbTimKiem, lbSoLuong;

		public LEFT_PAGESTART_PanelSearch() {
			setOpaque(false);
			setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

			lbTimKiem = new ExtendJLabel("Sản phẩm");
			lbTimKiem.setPreferredSize(new Dimension(80, 23));

			lbSoLuong = new ExtendJLabel("Số lượng");
			lbSoLuong.setHorizontalAlignment(JLabel.CENTER);
			lbSoLuong.setPreferredSize(new Dimension(80, 23));

			lbPrice = new ExtendJLabel("Đơn giá");
			lbPrice.setHorizontalAlignment(JLabel.CENTER);
			lbPrice.setPreferredSize(new Dimension(80, 23));

			txtTimKiem = new TextPopup<Product>(Product.class);
			txtTimKiem.setName("txtTimKiem");
			txtTimKiem.setPreferredSize(new Dimension(300, 23));

			txtSoLuong = new ExtendJTextField(5);
			txtSoLuong.setHorizontalAlignment(JTextField.RIGHT);
			txtSoLuong.setName("txtSoLuong");
			txtTimKiem.setComponentFocus(txtSoLuong);

			txtPrice = new ExtendJTextField(5);
			txtPrice.setHorizontalAlignment(JTextField.RIGHT);
			txtTimKiem.setComponentFocus2(txtPrice);

			add(lbTimKiem);
			add(txtTimKiem);
			add(lbSoLuong);
			add(txtSoLuong);
			add(lbPrice);
			add(txtPrice);
		}
	}

	private class LEFT_LINESTART_PanelCategory extends JPanel {
		public LEFT_LINESTART_PanelCategory() {
			this.setOpaque(false);
			this.setLayout(new BorderLayout(0, 10));
			this.add(new LLS_PageStart_ChoiceComboBox(), BorderLayout.NORTH);
			this.add(new LLS_Center_ListGroupProduct(), BorderLayout.CENTER);
		}

		private class LLS_PageStart_ChoiceComboBox extends GridBagLayoutPanel {
			private JLabel lbNhomSP, lbBangGia, lbDVTien;

			public LLS_PageStart_ChoiceComboBox() {
				setOpaque(false);

				groupProducts = new ArrayList<ProductTag>();
				groupProducts.add(0, null);

				lbNhomSP = new ExtendJLabel("Nhóm SP ");
				lbNhomSP.setPreferredSize(new Dimension(55, 23));

				lbBangGia = new ExtendJLabel("Bảng giá ");
				lbBangGia.setPreferredSize(new Dimension(55, 23));

				lbDVTien = new ExtendJLabel("ĐV Tiền ");
				lbDVTien.setPreferredSize(new Dimension(55, 23));

				cbNhomSP = new JComboBox();

				cbNhomSP.setName("cbNhomSanPham");
				cbNhomSP.setModel(new DefaultComboBoxModel(groupProducts.toArray()));

				cbNhomSP.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						if (e.getButton() == MouseEvent.BUTTON3) {
							CatalogProductTag hh;
							try {
								hh = new CatalogProductTag();
								hh.setName("QuanLyNhomHang_KhoHangHoa");
								DialogMain dialog = new DialogMain(hh);
								dialog.setName("dlNhomhang");
								dialog.setIcon("dssp1.png");
								dialog.setTitle("Quản lý nhóm hàng");
								dialog.setVisible(true);
								groupProducts.clear();
								groupProducts.add(0, null);
								loadlistTags();
								panelListProduct.setListProductTag(groupProducts);
								try {
									panelListProduct.loadGui();
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					}
				});

				List<ProductPriceType> productPrices;
				try {
					productPrices = ProductModelManager.getInstance().getProductPriceTypes();
				} catch (Exception e) {
					productPrices = new ArrayList<ProductPriceType>();
				}
				cbBangGia = new ExtendJComboBox();
				cbBangGia.setModel(new DefaultComboBoxModel(productPrices.toArray()));
				cbBangGia.setName("cbBangGia");

				cbBangGia.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						if (e.getButton() == MouseEvent.BUTTON3) {
							PanelProductPriceType pnProductPriceType;
							try {
								pnProductPriceType = new PanelProductPriceType();
								pnProductPriceType.setName("QuanLyBangGia");
								DialogMain dialog = new DialogMain(pnProductPriceType);
								dialog.setName("dlQuanLyBangGia");
								dialog.setIcon("price1.png");
								dialog.setTitle("Quản lý bảng giá");
								dialog.setVisible(true);
								try {
									cbBangGia.setModel(new DefaultComboBoxModel(ProductModelManager.getInstance().getProductPriceTypes()
									    .toArray()));
								} catch (Exception e1) {
									cbBangGia.setModel(new DefaultComboBoxModel());
								}
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					}
				});

				cbDVTien = new UnitMoneyJComboBox();
				cbDVTien.setName("cbDonViTien");
				add(0, 0, lbNhomSP);
				add(0, 1, cbNhomSP);
				add(1, 0, lbBangGia);
				add(1, 1, cbBangGia);
				add(2, 0, lbDVTien);
				add(2, 1, cbDVTien);

				cbNhomSP.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						ProductTag productTag = (ProductTag) cbNhomSP.getSelectedItem();
						List<ProductTag> list = new ArrayList<ProductTag>();
						if (productTag != null) {
							list.add(productTag);
						} else {
							try {
								List<ProductTag> tags = ProductModelManager.getInstance().getProductTags();
								for (ProductTag pt : tags) {
									if (pt.getTag().split(":").length == 2) {
										list.add(pt);
									}
								}
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}
						panelListProduct.setListProductTag(list);
						try {
							panelListProduct.loadGui();
						} catch (Exception e2) {
						}
					}
				});

				cbBangGia.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						getItemPriceType();
					}
				});
			}
		}

		private class LLS_Center_ListGroupProduct extends JPanel {
			public LLS_Center_ListGroupProduct() {
				this.setOpaque(false);
				this.setLayout(new BorderLayout());
				bagLayoutPanel = new GridBagLayoutPanel();
				bagLayoutPanel.setOpaque(false);
				loadlistTags();
				this.add(bagLayoutPanel, BorderLayout.PAGE_START);
			}
		}
	}

	private void loadlistTags() {
		List<ProductTag> productTags = new ArrayList<ProductTag>();
		try {
			productTags = ProductModelManager.getInstance().getProductTags();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < productTags.size(); i++) {
			final ProductTag productTag = productTags.get(i);
			if (productTag != null) {
				String tg = productTag.getTag();
				if (!tg.contains(":")) {
					final ExtendJCheckBox checkBox = new ExtendJCheckBox(productTag.getLabel());
					checkBox.setName("chbNhomSanPham" + i);
					checkBox.setSelected(true);
					checkBox.setOpaque(false);
					bagLayoutPanel.add(i, 0, checkBox);
					// Truyền productTag cha tìm tất cả con của nó add
					// vào list
					// groupProducts
					loadProductTagByCheckBox(productTag);
					cbNhomSP.setModel(new DefaultComboBoxModel(groupProducts.toArray()));

					checkBox.addItemListener(new ItemListener() {
						@Override
						public void itemStateChanged(ItemEvent e) {
							if (checkBox.isSelected()) { // Nếu click
								// chọn
								// (true)
								// Load lại list danh sách con của
								// 'productTag' khi checkBox
								// được chọn
								loadProductTagByCheckBox(productTag);
								cbNhomSP.setModel(new DefaultComboBoxModel(groupProducts.toArray()));
							} else { // Nếu click bỏ tích (false)
								// Xóa bỏ các nhóm con của nó trong list
								// 'groupProducts'
								removeProductTagByCheckBox(productTag);
								cbNhomSP.setModel(new DefaultComboBoxModel(groupProducts.toArray()));
							}
							panelListProduct.setListProductTag(groupProducts);
							try {
								panelListProduct.loadGui();
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					});
				}
			}
		}
	}

	private class LEFT_LINEEND_PanelListProduct extends JPanel {
		public LEFT_LINEEND_PanelListProduct() {
			this.setOpaque(false);
			this.setBackground(Color.WHITE);
			this.setLayout(new BorderLayout());
			List<ProductTag> allProductTags;
			try {
				allProductTags = ProductModelManager.getInstance().getProductTags();
			} catch (Exception e) {
				allProductTags = new ArrayList<ProductTag>();
			}
			List<ProductTag> listProductG = new ArrayList<ProductTag>();
			for (ProductTag pt : allProductTags) {
				if (pt.getTag().split(":").length == 2) {
					listProductG.add(pt);
				}
			}
			panelListProduct.setListProductTag(listProductG);
			try {
				panelListProduct.loadGui();
			} catch (Exception e) {
			}

			scrollPaneProduct = new JScrollPane();
			scrollPaneProduct.setViewportView(panelListProduct);

			this.add(scrollPaneProduct, BorderLayout.CENTER);
		}
	}

	/**
	 * Nội dung Panel phải
	 */
	private class PANEL_RIGHT_CONTENT extends JPanel {
		public PANEL_RIGHT_CONTENT() {
			this.setOpaque(false);
			this.setLayout(new BorderLayout());

			RIGHT_LINESTART_ControlButton RIGHT_LINESTART = new RIGHT_LINESTART_ControlButton();
			RIGHT_LINESTART.setPreferredSize(new Dimension(90, 300));

			this.add(RIGHT_LINESTART, BorderLayout.LINE_START);
			this.add(new RIGHT_CENTER_InfomationBill(), BorderLayout.CENTER);
		}

	}

	private class RIGHT_LINESTART_ControlButton extends JPanel {
		private JLabel lbTru, lbCong, lbKho;
		private JButton btnC;

		public RIGHT_LINESTART_ControlButton() {
			setOpaque(false);
			setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

			btnC = new JButton("C");
			btnC.setFont(new java.awt.Font("Tahoma", 1, 14));
			btnC.setPreferredSize(new Dimension(40, 23));
			btnC.setFont(new Font("Tahoma", Font.BOLD, 8));
			btnC.setContentAreaFilled(false);
			btnC.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					deleteInvoice();
				}
			});
			lbTru = new ExtendJLabel("-");
			lbTru.setFont(new java.awt.Font("Tahoma", 1, 12));
			lbTru.setHorizontalAlignment(JLabel.CENTER);
			lbTru.setVerticalAlignment(JLabel.CENTER);
			lbTru.setPreferredSize(new Dimension(15, 15));
			lbTru.addMouseListener(new MouseEventMove());
			lbTru.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					panelListProduct.opendAll(false);
				}
			});

			lbCong = new ExtendJLabel("+");
			lbCong.setFont(new java.awt.Font("Tahoma", 1, 12));
			lbCong.setHorizontalAlignment(JLabel.CENTER);
			lbCong.setVerticalAlignment(JLabel.CENTER);
			lbCong.setPreferredSize(new Dimension(15, 15));
			lbCong.addMouseListener(new MouseEventMove());
			lbCong.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					panelListProduct.opendAll(true);
				}
			});

			lbKho = new ExtendJLabel("Kho");
			lbKho.setHorizontalAlignment(JLabel.CENTER);
			lbKho.setPreferredSize(new Dimension(80, 23));

			lbKM = new ExtendJLabel("KM");
			lbKM.setHorizontalAlignment(JLabel.CENTER);
			lbKM.setPreferredSize(new Dimension(80, 23));
			lbKM.addMouseListener(new MouseEventMove());
			lbKM.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					lblKMMouseClicked(evt);
				}
			});

			btnXoa = new JButton("Xóa");
			btnXoa.setFont(new java.awt.Font("Tahoma", 1, 11));
			btnXoa.setName("btnXoa");
			btnXoa.setPreferredSize(new Dimension(80, 33));
			btnXoa.addMouseListener(new MouseEventClickButtonDialogPlus("", "", ""));
			btnXoa.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					deleteProduct();
				}
			});

			btnHuy = new JButton("Hủy");
			btnHuy.setPreferredSize(new Dimension(80, 33));
			btnHuy.setFont(new java.awt.Font("Tahoma", 1, 11));

			btnSoLuong = new JButton("S.lượng");
			btnSoLuong.setFont(new java.awt.Font("Tahoma", 1, 11));
			btnSoLuong.setPreferredSize(new Dimension(80, 33));
			btnSoLuong.addMouseListener(new MouseEventClickButtonDown(null, null, null));
			btnSoLuong.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						changeQuantityProduct();
					} catch (Exception e2) {
					}
				}
			});

			btnDoiGia = new JButton("Đổi giá");
			btnDoiGia.setFont(new java.awt.Font("Tahoma", 1, 11));
			btnDoiGia.addMouseListener(new MouseEventClickButtonDialogPlus(null, null, null));
			btnDoiGia.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						changePriceProduct();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			});
			btnDoiGia.setPreferredSize(new Dimension(80, 33));

			btnNgayHetHan = new JButton("Ngày hết hạn");
			btnNgayHetHan.setPreferredSize(new Dimension(80, 33));
			btnNgayHetHan.addMouseListener(new MouseEventClickButtonDialogPlus(null, null, null));
			JPanel panel = new JPanel();
			panel.setOpaque(false);
			panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
			panel.setPreferredSize(new Dimension(80, 300));

			JPanel panel2 = new JPanel();
			panel2.setOpaque(false);
			panel2.setPreferredSize(new Dimension(80, 75));

			panel.add(panel2);
			panel.add(btnXoa);
			panel.add(btnSoLuong);
			panel.add(btnDoiGia);
			panel.add(lbKM);

			JLabel lbl1 = new JLabel();
			lbl1.setPreferredSize(new Dimension(45, 23));
			JLabel lbl2 = new JLabel();
			lbl2.setPreferredSize(new Dimension(70, 23));
			JLabel lbl3 = new JLabel();
			lbl3.setPreferredSize(new Dimension(70, 23));

			add(btnC);
			add(lbl1);
			add(lbTru);
			add(lbl2);
			add(lbCong);
			add(lbl3);
			add(panel);
		}
	}

	private class RIGHT_CENTER_InfomationBill extends JPanel {
		public RIGHT_CENTER_InfomationBill() {
			this.setOpaque(false);
			this.setLayout(new BorderLayout(5, 5));

			panelPayment = new PanelPayment();
			tableModel = panelPayment.getTableModel();
			panelPayment.setSize(new Dimension(200, 300));
			panelPayment.setPreferredSize(new Dimension(200, 300));

			JScrollPane Right_Center_Center = new JScrollPane();
			Right_Center_Center.setOpaque(false);
			Right_Center_Center.getViewport().setOpaque(false);
			Right_Center_Center.setViewportView(panelPayment);

			this.add(new Right_Center_PageStart_Infomation(), BorderLayout.NORTH);
			this.add(Right_Center_Center, BorderLayout.CENTER);
			this.add(new Right_Center_PageEnd_ButtonControl(), BorderLayout.SOUTH);
		}
	}

	private class Right_Center_PageStart_Infomation extends JPanel {
		private JLabel lbNhanVien, lbThoiGian, lbTenPhieu;

		public Right_Center_PageStart_Infomation() {
			/* Khởi tạo các components và thiết lập giá trị ban đầu */
			lbNhanVien = new ExtendJLabel("Thu ngân");
			lbTenPhieu = new ExtendJLabel("Tên phiếu");
			lbKhachHang = new ExtendJLabel("Đối tác");
			lbThoiGian = new ExtendJLabel("Thời gian");

			Date now = new Date();
			lableDate = new JTextField();
			lableDate.setText((new SimpleDateFormat("dd/MM/yyyy").format(now)));
			lableTime = new JTextField();
			lableTime.setText(new SimpleDateFormat("HH:mm").format(now));
			lableTime.setPreferredSize(new Dimension(50, 23));
			cbKho = new WarehousesJComboBox();

			if (activityType.equals(ActivityType.Receipt)) {
				chbKho = new ExtendJCheckBox("Xuất kho");
			} else {
				chbKho = new ExtendJCheckBox("Nhập kho");
			}
			chbKho.setName("chbNhapKho");
			chbKho.setOpaque(false);

			txtNhanVien = new TextPopup<Employee>(Employee.class);
			txtNhanVien.setName("txtThuNgan");

			try {
				List<Employee> employees = HRModelManager.getInstance().getEmployees();
				txtNhanVien.setData(employees);
				Employee employee = HRModelManager.getInstance().getBydLoginId(ManagerAuthenticate.getInstance().getLoginId());
				txtNhanVien.setItem(employee);
			} catch (Exception e) {
			}

			if (activityType.equals(Invoice.ActivityType.Payment)) {
				if (name.equals("Khách trả hàng")) {
					txtKhachHang = new TextPopup<Customer>(Customer.class);
					lbKhachHang.setText("Khách hàng");
					try {
						txtKhachHang.setData(CustomerModelManager.getInstance().getCustomers(false));
					} catch (Exception e) {
					}
				} else {
					lbKhachHang.setText("Nhà c.cấp");
					txtKhachHang = new TextPopup<Supplier>(Supplier.class);
					try {
						txtKhachHang.setData(SupplierModelManager.getInstance().getAllSuppliers());
					} catch (Exception e) {
					}
				}
			} else {
				lbKhachHang.setText("Khách hàng");
				txtKhachHang = new TextPopup<Customer>(Customer.class);
				try {
					txtKhachHang.setData(CustomerModelManager.getInstance().getCustomers(false));
				} catch (Exception e) {
				}
			}

			txtTenPhieu.setName("txtNghiepVu");

			btnThoat = new JButton("Thoát");
			btnThoat.setFont(new java.awt.Font("Tahoma", 1, 11));
			btnThoat.setName("btnThoat");
			btnThoat.addMouseListener(new MouseEventClickButtonDialogPlus(null, null, null));
			btnThoat.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						if (tableModel.getInvoiceDetail().getId() != null
						    && tableModel.getInvoiceDetail().getStatus().equals(Status.WaitingPaid)) {
							tableModel.getInvoiceDetail().setStatus(Status.PostPaid);
							AccountingModelManager.getInstance().saveInvoice(tableModel.getInvoiceDetail());
						}
					} catch (Exception e2) {
					}
					((JDialog) getRootPane().getParent()).dispose();
				}
			});

			/* Sắp xếp các components vào vị trí */
			JPanel panel_Ngay = new JPanel();
			panel_Ngay.setLayout(new BorderLayout(0, 0));
			panel_Ngay.setOpaque(false);
			lableDate.setHorizontalAlignment(JTextField.CENTER);
			lableTime.setHorizontalAlignment(JTextField.CENTER);
			panel_Ngay.add(lableDate, BorderLayout.CENTER);
			panel_Ngay.add(lableTime, BorderLayout.LINE_END);

			MyGroupLayout myLayout = new MyGroupLayout(this);
			myLayout.add(0, 0, lbTenPhieu);
			myLayout.add(0, 1, txtTenPhieu);
			myLayout.add(0, 2, lbNhanVien);
			myLayout.add(0, 3, txtNhanVien);
			myLayout.add(1, 0, lbKhachHang);
			myLayout.add(1, 1, txtKhachHang);
			myLayout.add(1, 2, lbThoiGian);
			myLayout.add(1, 3, panel_Ngay);
			myLayout.add(2, 0, chbKho);
			myLayout.add(2, 1, cbKho);
			myLayout.add(2, 2, new JLabel(""));
			myLayout.add(2, 3, btnThoat);
			myLayout.updateGui();

			this.setBackground(Color.WHITE);
			this.setOpaque(true);
			this.setPreferredSize(new Dimension(50, 100));
			this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			lableDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lableTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		}
	}

	private class Right_Center_PageEnd_ButtonControl extends JPanel {
		public Right_Center_PageEnd_ButtonControl() {
			setOpaque(false);

			btnInHoaDon = new JButton("In hóa đơn");
			btnInHoaDon.setFont(new java.awt.Font("Tahoma", 1, 14));
			btnInHoaDon.addMouseListener(new MouseEventClickButtonDialogPlus(null, null, null));
			btnInHoaDon.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					printInvoice();
				}
			});

			btnThanhToanLe = new JButton("Thanh toán lẻ");
			btnThanhToanLe.setFont(new java.awt.Font("Tahoma", 1, 14));
			btnThanhToanLe.addMouseListener(new MouseEventClickButtonDialogPlus(null, null, null));
			btnThanhToanLe.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					paymentSlip();
				}
			});

			btnLuu = new JButton("Lưu");
			btnLuu.setFont(new java.awt.Font("Tahoma", 1, 14));
			btnLuu.addMouseListener(new MouseEventClickButtonDialogPlus(null, null, null));
			btnLuu.setName("btnLuu");
			btnLuu.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					statusInvoice = UNPAID_AND_NOTIMPORT;
					saveInvoice(UNPAID_AND_NOTIMPORT);
				}
			});

			btnThemSP = new JButton("Thêm SP");
			btnThemSP.setFont(new java.awt.Font("Tahoma", 1, 14));
			btnThemSP.addMouseListener(new MouseEventClickButtonDialogPlus(null, null, null));
			btnThemSP.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						PanelRepositoryProducts2 panel = new PanelRepositoryProducts2();
						panel.setName("ThemHangHoaMoi");
						DialogMain dialog = new DialogMain(panel, true);
						dialog.setIcon("hanghoa1.png");
						dialog.setTitle("Thêm hàng hóa mới");
						dialog.setName("dlThemHangHoaMoi");
						dialog.dispose();
						dialog.setUndecorated(true);
						dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
						dialog.setModal(true);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);
						panelListProduct.loadGui();
					} catch (Exception e2) {
					}
				}
			});

			btnCTKM = new JButton("CT KM");
			btnCTKM.setFont(new java.awt.Font("Tahoma", 1, 14));
			btnCTKM.addMouseListener(new MouseEventClickButtonDialogPlus(null, null, null));
			String str = "Nhập kho";
			if (activityType.equals(ActivityType.Receipt)) {
				str = "Xuất kho";
			}
			btnNhapKho = new JButton(str);
			btnNhapKho.setFont(new java.awt.Font("Tahoma", 1, 14));
			btnNhapKho.setName("btnNhapKho");
			btnNhapKho.addMouseListener(new MouseEventClickButtonDialogPlus(null, null, null));
			btnNhapKho.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					statusInvoice = UNPAID;
					saveInvoice(UNPAID);
				}
			});

			btnThanhToan = new JButton("Thanh toán");
			btnThanhToan.setFont(new java.awt.Font("Tahoma", 1, 14));
			btnThanhToan.setName("btnThanhToan");
			btnThanhToan.addMouseListener(new MouseEventClickButtonDialogPlus(null, null, null));
			btnThanhToan.setPreferredSize(new Dimension(23, 43));
			btnThanhToan.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (chbKho.isSelected()) {
						statusInvoice = PAID;
						saveInvoice(PAID);
					} else {
						statusInvoice = PAID_AND_NOTIMPORT;
						saveInvoice(PAID_AND_NOTIMPORT);
					}
				}
			});

			setLayout(new GridLayout(0, 4, 5, 0));
			JPanel panel = new JPanel();
			panel.setOpaque(false);
			panel.setLayout(new GridLayout(2, 1, 5, 5));
			panel.add(btnInHoaDon);
			panel.add(btnThemSP);
			add(panel);

			JPanel panel1 = new JPanel();
			panel1.setOpaque(false);
			panel1.setLayout(new GridLayout(2, 1, 5, 5));
			panel1.add(btnThanhToanLe);
			panel1.add(btnCTKM);
			add(panel1);

			JPanel panel2 = new JPanel();
			panel2.setOpaque(false);
			panel2.setLayout(new GridLayout(2, 1, 5, 5));
			panel2.add(btnLuu);
			panel2.add(btnNhapKho);
			add(panel2);

			add(btnThanhToan);
		}

	}

	private void getItemPriceType() {
		String type = "";
		try {
			type = ((ProductPriceType) cbBangGia.getSelectedItem()).getType();
		} catch (Exception e1) {
			type = "";
		}
		tableModel.setInfoInvoice("", type, null);
	}

	// Sét ẩn hiện khuyến mãi
	protected void lblKMMouseClicked(MouseEvent evt) {
		if (flag) {
			lbKM.setText("Ẩn KM");
			flag = !flag;
		} else {
			lbKM.setText("Hiện KM");
			flag = !flag;
		}
	}

	// Xóa sản phẩm khỏi hóa đơn
	private void deleteProduct() {
		try {
			if (panelPayment.getSelectedInvoiceItem() != null) {

				ChooseDelProduct panel = new ChooseDelProduct(panelPayment.getSelectedInvoiceItem().getItemName());
				DialogResto dialog = new DialogResto(panel, false, 0, 80);
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

	// Sửa số lượng sản phẩm
	private void changeQuantityProduct() {
		try {
			PanelChooseQuantity panel = new PanelChooseQuantity(panelPayment.getSelectedInvoiceItem());
			DialogResto dialog = new DialogResto(panel, false, 0, 100);
			// dialog.setSize(650, 250);
			// dialog.setPreferredSize(new Dimension(650, 250));
			dialog.setTitle("Số lượng");
			dialog.setLocationRelativeTo(null);
			dialog.setModal(true);
			dialog.setVisible(true);
			if (panel.isEdit()) {
				tableModel.updateItem(panelPayment.getSelectedInvoiceItem());
				// khanhdd
				if (!btnNhapKho.isEnabled()) {
					btnNhapKho.setEnabled(true);
				}
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
				DialogResto dialog = new DialogResto(choosePrice, false, 0, 100);
				// dialog.setSize(650, 250);
				// dialog.setPreferredSize(new Dimension(650, 250));
				dialog.setLocationRelativeTo(null);
				dialog.setTitle("Đổi giá");
				dialog.setVisible(true);
				if (choosePrice.isEdit()) {
					tableModel.updateItem(panelPayment.getSelectedInvoiceItem());
					// khanhdd
					if (!btnNhapKho.isEnabled()) {
						btnNhapKho.setEnabled(true);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Chọn khách hàng */
	private void choiseCustomer() {
		if (activityType.equals(Invoice.ActivityType.Receipt)) {
			PanelListPartnerOrganization panel = new PanelListPartnerOrganization();
			DialogResto dialog = new DialogResto(panel, false, 1000, 350);
			dialog.setTitle("Danh sách khách hàng");
			// dialog.setSize(new Dimension(1150, 600));
			dialog.setModal(true);
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
			Customer c = panel.getCustomer();
			if (c != null) {
				txtKhachHang.setObject(c);
				txtKhachHang.setText(c.toString());
				txtKhachHang.setItem(c);
				String type = "";
				try {
					type = ((ProductPriceType) cbBangGia.getSelectedItem()).getType();
				} catch (Exception e) {
					type = "";
				}
				tableModel.setInfoInvoice(c.getLoginId(), type, null);
				// khanhdd
				if (!btnNhapKho.isEnabled()) {
					btnNhapKho.setEnabled(true);
				}
			}
		} else {
			PanelListSupplierOrganization panel = new PanelListSupplierOrganization();
			DialogResto dialog = new DialogResto(panel, false, 1000, 350);
			dialog.setTitle("Danh sách nhà cung cấp");
			dialog.setModal(true);
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
			Supplier c = panel.getSupplier();
			if (c != null) {
				txtKhachHang.setObject(c);
				txtKhachHang.setText(c.toString());
				txtKhachHang.setItem(c);
				String type = "";
				try {
					type = ((ProductPriceType) cbBangGia.getSelectedItem()).getType();
				} catch (Exception e) {
					type = "";
				}
				tableModel.setInfoInvoice(c.getLoginId(), type, null);
				// khanhdd
				if (!btnNhapKho.isEnabled()) {
					btnNhapKho.setEnabled(true);
				}
			}
		}

	}

	public void deleteInvoice() {

		if (tableModel.getInvoiceDetail().getId() != null) {
			if (UIConfigModelManager.getInstance().getPermission(permission1) == Capability.ADMIN) {
				CheckC();
			} else {
				JOptionPane.showMessageDialog(null, "Bạn chưa được phân quyền này !");
				return;
			}
		} else if (invoiceDetail.getActivityType().equals(ActivityType.Receipt)
		    && invoiceDetail.getType().equals(AccountingModelManager.typeDatHang)) {
			if (UIConfigModelManager.getInstance().getPermission(permission2) == Capability.ADMIN) {
				CheckC();
			} else {
				JOptionPane.showMessageDialog(null, "Bạn chưa được phân quyền này !");
				return;
			}
		} else if (invoiceDetail.getActivityType().equals(ActivityType.Receipt)
		    && invoiceDetail.getType().equals(AccountingModelManager.typeTraHang)) {
			if (UIConfigModelManager.getInstance().getPermission(permission3) == Capability.ADMIN) {
				CheckC();
			} else {
				JOptionPane.showMessageDialog(null, "Bạn chưa được phân quyền này !");
				return;
			}
		} else if (invoiceDetail.getActivityType().equals(ActivityType.Payment)
		    && invoiceDetail.getType().equals(AccountingModelManager.typeTraHang)) {
			if (UIConfigModelManager.getInstance().getPermission(permission4) == Capability.ADMIN) {
				CheckC();
			} else {
				JOptionPane.showMessageDialog(null, "Bạn chưa được phân quyền này !");
				return;
			}
		} else {
			reset();
			return;
		}

		if (flagEdit == false) {
			String str = "Bạn không thể xóa hóa đơn này!";
			PanelChoise panel = new PanelChoise(str);
			panel.setName("Xoa");
			DialogResto dialog = new DialogResto(panel, false, 0, 80);
			dialog.setName("dlXoa");
			dialog.setTitle("Xóa hóa đơn");
			dialog.setLocationRelativeTo(null);
			dialog.setModal(true);
			dialog.setVisible(true);
			if (panel.isDelete()) {
				return;
			}
		}

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
						((JDialog) getRootPane().getParent()).dispose();
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

	@SuppressWarnings({ "unused" })
	private void loadListProductByCBB() {
		try {
			ProductTag productGroups = (ProductTag) cbNhomSP.getSelectedItem();
			List<ProductTag> listProductG = new ArrayList<ProductTag>();
			listProductG.add(productGroups);
			if (productGroups != null) {
				panelListProduct.setListProductTag(listProductG);
				panelListProduct.loadGui();
			} else {
				panelListProduct.setListProductTag(listProductGroup);
				panelListProduct.loadGui();
			}
		} catch (Exception e) {
			panelListProduct.setListProductTag(listProductGroup);
			try {
				panelListProduct.loadGui();
			} catch (Exception e1) {
			}
			;
		}
	}

	private void loadProductTagByCheckBox(ProductTag productTagParent) {
		try {
			String tagParent = productTagParent.getTag();
			List<ProductTag> productTags = ProductModelManager.getInstance().findProductTagRoot(tagParent);
			groupProducts.addAll(productTags);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void removeProductTagByCheckBox(ProductTag productTagParent) {
		try {
			String tagParent = productTagParent.getTag();
			List<ProductTag> productTags = ProductModelManager.getInstance().findProductTagRoot(tagParent);
			for (ProductTag productTag : productTags) {
				groupProducts.remove(productTag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void reset() {
		InvoiceDetail invoiceDetail = new InvoiceDetail(true);
		invoiceDetail.setInvoiceCode(DateUtil.asCompactDateTimeId(new Date()));
		String code = AccountingModelManager.getInstance().getCodeObject(PanelManageCodes.InvoiceDMH,
		    AccountingModelManager.typeBanHang, new Date(), false);
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
		txtKhachHang.setText("");
		if (name.equals("PDH")) {
			if (type.equals(AccountingModelManager.typeSanXuat)) {
				txtTenPhieu.setText("Phiếu nhập kho");
			} else {
				txtTenPhieu.setText("Cty đặt hàng");
			}

		} else if (name.equals("KDH")) {
			if (type.equals(AccountingModelManager.typeSanXuat)) {
				txtTenPhieu.setText("Phiếu xuất kho");
			} else {
				txtTenPhieu.setText("Khách đặt hàng");
			}

		} else if (name.equals("Phiếu trả hàng")) {
			txtTenPhieu.setText("Cty trả hàng");
		} else {
			txtTenPhieu.setText("Khách trả hàng");
		}
		btnThoat.setEnabled(true);
		btnThanhToan.setEnabled(true);
		btnLuu.setEnabled(true);
		btnNhapKho.setEnabled(true);
		statusInvoice = 0;
		if (type.equals(AccountingModelManager.typeSanXuat)) {
			chbKho.setSelected(true);
			chbKho.setEnabled(false);
			btnInHoaDon.setEnabled(false);
			btnThanhToan.setEnabled(false);
			btnThanhToanLe.setEnabled(false);
			btnLuu.setEnabled(false);
			btnCTKM.setEnabled(false);
			panelPayment.getTabbedPane().setVisible(false);
		}
	}

	public void refresh() {
		if (statusInvoice == PAID) {
			btnThanhToanLe.setEnabled(false);
		} else if (statusInvoice == UNPAID || statusInvoice == PAID_AND_NOTIMPORT) {
			// btnLuu.setEnabled(false);
			chbKho.setEnabled(true);
			btnThanhToanLe.setEnabled(true);
		}

		try {
			Employee e = HRModelManager.getInstance().getEmployeeByCode(tableModel.getInvoiceDetail().getEmployeeCode());
			txtNhanVien.setItem(e);
		} catch (Exception e) {
			txtNhanVien.setItem(null);
		}

		Customer c;
		try {
			c = CustomerModelManager.getInstance().getCustomerByCode(tableModel.getInvoiceDetail().getCustomerCode());
			txtKhachHang.setItem(c);
		} catch (Exception e) {
			try {
				Supplier a = SupplierModelManager.getInstance().getSupplierByCode(
				    tableModel.getInvoiceDetail().getSupplierCode());
				txtKhachHang.setItem(a);
			} catch (Exception e2) {
			}
		}
		panelPayment.refershData();

		lableDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(tableModel.getInvoiceDetail().getStartDate()));
		lableTime.setText(new SimpleDateFormat("HH:mm").format(tableModel.getInvoiceDetail().getStartDate()));
		String nv = tableModel.getInvoiceDetail().getInvoiceName();
		if (nv != null)
			txtTenPhieu.setText(tableModel.getInvoiceDetail().getInvoiceName());

		try {
			if (tableModel.getInvoiceDetail().getWarehouseId() != null
			    && !tableModel.getInvoiceDetail().getWarehouseId().equals("")) {
				cbKho.setSelectWarehouse(tableModel.getInvoiceDetail().getWarehouseId());
			}
		} catch (Exception e) {
		}

		// try {
		// List<IdentityProduct> identityProducts =
		// WarehouseModelManager.getInstance().getByInvoiceCode(
		// tableModel.getInvoiceDetail().getInvoiceCode());
		// for (IdentityProduct identityProduct : identityProducts) {
		// if
		// (identityProduct.getExportType().equals(IdentityProduct.ExportType.Export))
		// {
		// getAllComponents(this);
		// JOptionPane.showMessageDialog(null,
		// "Phiếu nhập đã được xuất, không thể chỉnh sửa!");
		// return;
		// }
		// }
		// if (identityProducts.size() > 0) {
		// chbKho.setEnabled(false);
		// chbKho.setSelected(true);
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		if (type.equals(AccountingModelManager.typeSanXuat)) {
			chbKho.setSelected(true);
			chbKho.setEnabled(false);
			btnInHoaDon.setEnabled(false);
			btnThanhToan.setEnabled(false);
			btnThanhToanLe.setEnabled(false);
			btnLuu.setEnabled(false);
			btnCTKM.setEnabled(false);
			panelPayment.getTabbedPane().setVisible(false);
		}
	}

	@Override
	public void addProduct(Product product) {
		if (flagEdit == false) {
			JOptionPane.showMessageDialog(null, "Bạn không thể thay đổi hóa đơn này !");
			return;
		}
		// btnThanhToan.setEnabled(true);
		btnNhapKho.setEnabled(true);
		// if (tableModel.getInvoiceDetail().getId() == null) {
		// try {
		// InvoiceDetail invoiceDetail =
		// AccountingModelManager.getInstance().saveInvoiceDetail(tableModel.getInvoiceDetail());
		// tableModel.setData(invoiceDetail);
		// } catch (Exception e) {
		// }
		// }
		try {
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
				if (price >= 0) {
					tableModel.addItem(invoiceItem, price);
				} else {
					tableModel.addItem(invoiceItem);
				}
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

	private void saveInvoice(int invoiceStatus) {
		if (name == "PDH") {
			if (UIConfigModelManager.getInstance().getPermission(permission1) == Capability.READ) {
				JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			} else {
				checkSave(invoiceStatus);
			}
		} else if (name == "KDH") {
			if (UIConfigModelManager.getInstance().getPermission(permission2) == Capability.READ) {
				JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			} else {
				checkSave(invoiceStatus);
			}
		} else if (name == "Phiếu trả hàng") {
			if (UIConfigModelManager.getInstance().getPermission(permission3) == Capability.READ) {
				JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			} else {
				checkSave(invoiceStatus);
			}
		} else if (name == "Khách trả hàng") {
			if (UIConfigModelManager.getInstance().getPermission(permission4) == Capability.READ) {
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
			Customer customer = (Customer) txtKhachHang.getItem();
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
			Supplier c = (Supplier) txtKhachHang.getItem();
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
							for (AccountMembership a : accMemberShips) {
								if (a.getGroupPath().indexOf(AccountModelManager.Department) > 0) {
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
		if (txtNhanVien.getItem() != null) {
			Employee nhanVien = (Employee) txtNhanVien.getItem();
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
			invoice.setInvoiceName(txtTenPhieu.getText());
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
			if (invoiceStatus == PAID || invoiceStatus == PAID_AND_NOTIMPORT) {
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
			if (invoiceStatus == PAID || invoiceStatus == PAID_AND_NOTIMPORT) {
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

				if (type.equals(AccountingModelManager.typeSanXuat)) {
					invoice.setStatus(Status.Paid);
					for (InvoiceItem invoiceItem : invoice.getItems()) {
						if (!invoiceItem.getStatus().equals(AccountingModelManager.isCance)
						    && !invoiceItem.getStatus().equals(AccountingModelManager.isPromotion)) {
							invoiceItem.setStatus(AccountingModelManager.isPayment);
						}
					}
					if (!printReceipt(true, true, null)) {
						DialogNotice.getInstace().setVisible(true);
					}
				} else {
					if (!invoice.getStatus().equals(Status.Paid)) {
						invoice.setStatus(Status.PostPaid);
						for (InvoiceTransaction invoiceTransaction : invoice.getTransactions()) {
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
			}

			/** Thêm kho */
			if (cbKho.getSelectedWarehouse() != null) {
				invoice.setWarehouseId(cbKho.getSelectedWarehouse().getWarehouseId());
			} else {
				invoice.setWarehouseId(null);
			}

			if (invoice.getEndDate() == null) {
				invoice.setEndDate(new Date());
				String code = AccountingModelManager.getInstance().getCodeObject(PanelManageCodes.InvoiceDMH, type,
				    invoice.getStartDate(), true);
				if (code != null) {
					invoice.setInvoiceCode(code);
				} else {
					invoice.setInvoiceCode(panelPayment.getTxtInvoiceCode().getText());
				}
			}
			if (invoiceStatus == UNPAID_AND_NOTIMPORT) {
				invoice = AccountingModelManager.getInstance().saveInvoice(invoice);
			} else {
				invoice = AccountingModelManager.getInstance().saveInvoiceDetail(invoice);
			}

			if (chbKho.isSelected() && (invoiceStatus == PAID || invoiceStatus == UNPAID)) {
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
										if (cbKho.getSelectedWarehouse() != null) {
											wh = cbKho.getSelectedWarehouse();
										}
										quantity = quantity - 1;
										for (int j = 0; j <= quantity; j++) {
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
											if (j > quantity - 1 && j < quantity) {
												identityProduct.setProfit(quantity - j);
											}
											identityProduct = WarehouseModelManager.getInstance().saveIdentityProduct(identityProduct);
										}
									} else {
										List<IdentityProduct> identityProducts = WarehouseModelManager.getInstance()
										    .getByInvoiceItemIdImport(invoiceItem.getId());
										double q = quantity * -1;
										for (int j = 0; j < q; j++) {
											WarehouseModelManager.getInstance().deleteIdentityProduct(identityProducts.get(j));
										}
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
										identityProducts = WarehouseModelManager.getInstance()
										    .findByProductCodeAndExportTypeAndWarehoseCode(productCode, invoice.getWarehouseId());
									}
									int count = 0;
									// So sánh số lượng SP được chọn trong bảng
									// với số lượng có
									// trong kho
									if (quantity > identityProducts.size()) {

										if (type.equals(AccountingModelManager.typeSanXuat)
										    && !RestaurantModelManager.getInstance().isRecipe()) {
											ChooseDelProduct panel1 = new ChooseDelProduct(
											    "Sản phẩm trong kho không đủ, phiếu sẽ tự động xóa!");
											DialogResto dialog1 = new DialogResto(panel1, false, 0, 100);
											dialog1.setTitle("Kho");
											dialog1.setLocationRelativeTo(null);
											dialog1.setModal(true);
											dialog1.setVisible(true);
											AccountingModelManager.getInstance().deleteInvoice(invoice);
											return;
										}
										if (AccountModelManager.getInstance().getProfileConfigAdmin().get(PanelChooseProductExport.EXPORT) != null
										    && AccountModelManager.getInstance().getProfileConfigAdmin()
										        .get(PanelChooseProductExport.EXPORT).equals("true")) {
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
												btnThoat.setEnabled(false);
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
												identityProducts = WarehouseModelManager.getInstance().getByInvoiceItemIdExport(
												    invoiceItem.getId());
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
							System.out.println("time: "
							    + new DecimalFormat("#0.00000").format((System.currentTimeMillis() - endTime2) / 1000d));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				};
				t.start();
			}
			reset();
			if (exit) {
				((JDialog) getRootPane().getParent()).dispose();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createBeginTest() {
		if (MyPanel.isTest) {
			// Tạo đơn vị tiền tệ (3)
			for (int i = 1; i <= 3; i++) {
				Currency currency = new Currency();
				currency.setCode("Mã TT HktTest" + i);
				currency.setName("TT HktTest" + i);
				try {
					LocaleModelManager.getInstance().saveCurrency(currency);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// Tạo nhóm hàng hóa
			List<ProductTag> productTags = new ArrayList<ProductTag>();
			for (int i = 1; i <= 3; i++) {
				// Nhóm cha 1
				ProductTag productTag1 = new ProductTag();
				productTag1.setCode("Mã NSP HktTest" + i);
				productTag1.setLabel("Nhóm SP HktTest" + i);
				productTag1.setTag("Mã NSP HktTest" + i);
				// Nhóm con 1 - 1
				ProductTag productTag11 = new ProductTag();
				productTag11.setCode("Mã NSP HktTest" + (i + i * 10));
				productTag11.setLabel("Nhóm SP HktTest" + (i + i * 10));
				productTag11.setTag(productTag1.getTag() + ":Mã NSP HktTest" + (i + i * 10));
				// Nhóm con 1 - 1 - 1
				ProductTag productTag111 = new ProductTag();
				productTag111.setCode("Mã NSP HktTest" + (i + i * 110));
				productTag111.setLabel("Nhóm SP HktTest" + (i + i * 110));
				productTag111.setTag(productTag11.getTag() + ":Mã NSP HktTest" + (i + i * 110));
				try {
					ProductModelManager.getInstance().saveProductTag(productTag1);
					ProductModelManager.getInstance().saveProductTag(productTag11);
					productTag111 = ProductModelManager.getInstance().saveProductTag(productTag111);
					productTags.add(productTag111);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			// Tạo đơn vị sản phẩm
			ProductUnit productUnit = new ProductUnit();
			productUnit.setCode("Mã ĐVSP HktTest1");
			productUnit.setName("ĐVSP HktTest1");
			productUnit.setRate(20);
			try {
				LocaleModelManager.getInstance().saveProductUnit(productUnit);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// Tạo hàng hóa
			for (int i = 1; i <= 3; i++) {
				try {
					Product product = new Product();
					product.setName("Sản phẩm HktTest" + i);
					product.setCode("Mã SP HktTest" + i);
					product.setCreatedTime(new Date());
					product.setNameOther("Ngôn ngữ HktTest" + i);
					product.setProductTags(productTags);
					product.setUnit("ĐVSP HktTest1");
					product.setMaker(ManagerAuthenticate.getInstance().getOrganizationLoginId());
					product = ProductModelManager.getInstance().saveProduct(product);

					// Tạo bảng giá sản phẩm
					ProductPriceType priceType = new ProductPriceType();
					priceType.setType("Kiểu BG HktTest" + i);
					priceType.setName("BG HktTest" + i);
					priceType.setOrganizationLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
					priceType = ProductModelManager.getInstance().saveProductPriceType(priceType);

					// Tạo giá sản phẩm
					ProductPrice productPrice = new ProductPrice();
					productPrice.setProduct(product);
					productPrice.setOrganizationLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
					productPrice.setProductPriceType(priceType);
					productPrice.setUnit("Mã ĐVSP HktTest" + i);
					productPrice.setPrice(i * 111000);
					productPrice.setCurrencyUnit("Mã TT HktTest" + i);
					ProductModelManager.getInstance().saveProductPrice(productPrice);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// Tạo nhân viên
			Account account = new Account();
			account.setLoginId("HktTest1");
			account.setPassword("HktTest1");
			account.setEmail("HktTest1@gmail.com");
			try {
				AccountModelManager.getInstance().saveAccount(account);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			Employee employee = new Employee();
			employee.setLoginId("HktTest1");
			employee.setOrganizationLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
			employee.setCode("Mã NV HktTest1");
			employee.setName("NV HktTest1");
			try {
				HRModelManager.getInstance().saveEmployee(employee);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			// Tạo kho hàng
			Warehouse warehouse = new Warehouse();
			warehouse.setWarehouseId("Mã kho HktTest1");
			warehouse.setName("Kho HktTest1");
			try {
				WarehouseModelManager.getInstance().saveWarehouse(warehouse);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		// super.createBeginTest();
	}

	@Override
	public void deleteDataTest() {
		if (!PanelTestAll.runAll) {
			DialogTest.getInstance().show("Xóa toàn bộ dữ liệu test ?");
			if (DialogTest.getInstance().isTest()) {
				try {
					LocaleModelManager.getInstance().deleteTest("HktTest");
					AccountModelManager.getInstance().deleteTest("HktTest");
					HRModelManager.getInstance().deleteTest("HktTest");
					WarehouseModelManager.getInstance().deleteTest("HktTest");
					ProductModelManager.getInstance().deleteTest("HktTest");
					AccountingModelManager.getInstance().deleteTest("HktTest");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * [Thanh toán lẻ]
	 */
	private void paymentSlip() {
		if (name == "PDH") {
			if (UIConfigModelManager.getInstance().getPermission(permission1) == Capability.READ) {
				JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			} else {
				checkpaymentSlip();
			}
		} else if (name == "KDH")
			if (UIConfigModelManager.getInstance().getPermission(permission2) == Capability.READ) {
				JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			} else {
				checkpaymentSlip();
			}
		else if (name == "Phiếu trả hàng") {
			if (UIConfigModelManager.getInstance().getPermission(permission3) == Capability.READ) {
				JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			} else {
				checkpaymentSlip();
			}
		} else if (name == "Khách trả hàng") {
			if (UIConfigModelManager.getInstance().getPermission(permission4) == Capability.READ) {
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
						InvoiceDetail invoice =AccountingModelManager.getInstance().saveInvoiceDetail(tableModel.getInvoiceDetail());
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

	private InvoiceDetail getInvoiceDetailPayment(List<InvoiceItem> invoiceItems) {
		InvoiceDetail invoiceDetail2 = new InvoiceDetail(true);
		for (InvoiceItem invoiceItem : invoiceItems) {
			invoiceDetail2.add(invoiceItem);
		}
		invoiceDetail2.calculate(new DefaultInvoiceCalculator());
		return invoiceDetail2;
	}

	// In hóa đơn
	private void printInvoice() {
		if (name == "PDH") {
			if (UIConfigModelManager.getInstance().getPermission(permission1) == Capability.READ) {
				JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			} else {
				checkprint();
			}
		} else if (name == "KDH") {
			if (UIConfigModelManager.getInstance().getPermission(permission2) == Capability.READ) {
				JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			} else {
				checkprint();
			}
		} else if (name == "Phiếu trả hàng") {
			if (UIConfigModelManager.getInstance().getPermission(permission3) == Capability.READ) {
				JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			} else {
				checkprint();
			}
		} else if (name == "Khách trả hàng") {
			if (UIConfigModelManager.getInstance().getPermission(permission4) == Capability.READ) {
				JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			} else {
				checkprint();
			}
		}
	}

	private void checkprint() {
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
			printReceipt(false, false, null);
		} else {
			printAgain = true;
			boolean print = printReceipt(true, false, null);
			printAgain = false;
		}
		AccountingModelManager.pay = "Tiền mặt";
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
		invoiceDetail.setDiscount(invoiceDetail.getDiscount() + invoiceDetail.getDiscountByItem());
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
			double a = LocaleModelManager.getInstance().getCurrencyByCode(this.currencyCode).getRate()
			    * (invoiceDetail.getFinalCharge() - invoiceDetail.getTotalPaid());
			moneyQuyDoi = new MyDouble(a).toString();
		}

		String lienHoaDon = "1", solanIn = "1";
		String tongSoDiem = "", soDiemConLai = "", tongSoTien = "", soTienConLai = "";
		String credit = "";
		String gioVao = lableTime.getText();
		String diaChi = "", sdt = "";
		if (txtKhachHang.getItem() != null) {
			try {
				diaChi = AccountModelManager.getInstance()
				    .getAddressByLoginId(((Customer) txtKhachHang.getItem()).getLoginId());
				sdt = ((Customer) txtKhachHang.getItem()).getMobile();
			} catch (Exception e) {
				diaChi = AccountModelManager.getInstance()
				    .getAddressByLoginId(((Supplier) txtKhachHang.getItem()).getLoginId());
				sdt = ((Supplier) txtKhachHang.getItem()).getMobile();
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
		String strStt = "";
		if (name.equals("PDH")) {
			if (type.equals(AccountingModelManager.typeSanXuat)) {
				strStt = "Phiếu Nhập Kho";
			} else {
				strStt = "Phiếu Đặt Hàng";
			}
		} else if (name.equals("KDH")) {
			if (type.equals(AccountingModelManager.typeSanXuat)) {
				strStt = "Phiếu Nhập Kho";
			} else {
				strStt = "Phiếu Đặt Hàng";
			}
		} else if (name.equals("Phiếu trả hàng")) {
			if (type.equals(AccountingModelManager.typeSanXuat)) {
				strStt = "Phiếu Xuất Kho";
			} else {
				strStt = "Phiếu Đặt Hàng";
			}
		} else {
			if (type.equals(AccountingModelManager.typeSanXuat)) {
				strStt = "Phiếu Xuất Kho";
			} else {
				strStt = "Phiếu Đặt Hàng";
			}
		}
		String nameArea="";
		if(panelPayment.getTxtProject().getItem()!=null && !panelPayment.getTxtProject().getItem().getCode().equals("project-other")){
			nameArea= panelPayment.getTxtProject().getItem().getCode();
		}
		if(nameArea.equals(RestaurantModelManager.getInstance().getLocationPaymentAfter().getName())){
			nameArea="";
		}
		String[] str = new String[] { enterpriseName, adres, fone, fax, date, code, nameArea, txtTenPhieu.getText(), gioVao,
		    txtNhanVien.getText(), MyDouble.valueOf(invoiceDetail.getTotal()).toString(),
		    MyDouble.valueOf(invoiceDetail.getDiscountRate()).toString(),
		    MyDouble.valueOf(invoiceDetail.getDiscount()).toString(), ptThue,
		    MyDouble.valueOf(invoiceDetail.getTotalTax()).toString(), tienThue, datCoc, phaiTra, strStt,
		    dfDate.format(new Date()), "", currencyCode, MyDouble.valueOf(invoiceDetail.getService()).toString(), ratio,
		    moneyQuyDoi, MyDouble.valueOf(invoiceDetail.getServiceRate()).toString(), strMoney,
		    MyDouble.valueOf(invoiceDetail.getPoint()).toString(), MyDouble.valueOf(invoiceDetail.getPoint()).toString(),
		    txtKhachHang.getText(), "", "", credit, lienHoaDon, solanIn, tongSoDiem, soDiemConLai, tongSoTien,
		    soTienConLai, credit, MyDouble.valueOf(invoiceDetail.getCredit()).toString(), "", stkd, sttl, diaChi, sdt, AccountingModelManager.pay };
		try {
			Table table = RestaurantModelManager.getInstance().getTableOther();
			String name = invoiceDetail.getInvoiceCode() + "_" + String.valueOf(invoiceDetail.getTransactions().size());
			if (!exel) {
				name = null;
			}
			if (profile.get("Exel") == null && profile.get("Pdf") == null) {
				name = null;
			}
			invoiceDetail.setDiscount(invoiceDetail.getDiscount() - invoiceDetail.getDiscountByItem());
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

	// private boolean printReceipt(boolean view) throws HeadlessException {
	// InvoiceDetail invoiceDetail = tableModel.getInvoiceDetail();
	// DateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");
	// String strMessenger =
	// "Bản sao phiếu thanh toán, có thể khác phiếu cuối cùng";
	// String loginId =
	// ManagerAuthenticate.getInstance().getOrganizationLoginId();
	// String enterpriseName =
	// AccountModelManager.getInstance().getNameByLoginId(loginId);
	// String adres =
	// AccountModelManager.getInstance().getAddressByLoginId(loginId);
	// String fone = AccountModelManager.getInstance().getPhoneByLoginId(loginId);
	// String fax = AccountModelManager.getInstance().getFaxByLoginId(loginId);
	//
	// String currencyCode =
	// LocaleModelManager.getInstance().getCurrencyByCode(this.currencyCode).getName();
	//
	// String ratio = "";
	// String moneyQuyDoi = "";
	// if (!this.currencyCode.equals("VND")) {
	// ratio = new
	// MyDouble(LocaleModelManager.getInstance().getCurrencyByCode(this.currencyCode).getRate()).toString();
	// double a =
	// LocaleModelManager.getInstance().getCurrencyByCode(this.currencyCode).getRate()
	// * (invoiceDetail.getFinalCharge() - invoiceDetail.getTotalPaid());
	// moneyQuyDoi = new MyDouble(a).toString();
	// }
	// String nvpv = "";
	// String lienHoaDon = "1", solanIn = "1";
	// String tongSoDiem = "", soDiemConLai = "", tongSoTien = "", soTienConLai =
	// "";
	// String credit = "";
	// String gioVao = lableTime.getText(), gioRa = new
	// SimpleDateFormat("HH:mm").format(new Date());
	// String diaChi = "", sdt = "";
	// if (txtKhachHang.getItem() != null) {
	// if (txtKhachHang.getItem() instanceof Customer) {
	// Customer c = (Customer) txtKhachHang.getItem();
	// diaChi =
	// AccountModelManager.getInstance().getAddressByLoginId(c.getLoginId());
	// sdt = AccountModelManager.getInstance().getPhoneByLoginId(c.getLoginId());
	// try {
	// Point p =
	// CustomerModelManager.getInstance().getPointByCustomerId(c.getId());
	// if (p != null) {
	// soDiemConLai = new MyDouble(p.getPoint() -
	// invoiceDetail.getPoint()).toString();
	// soTienConLai = new MyDouble(p.getPoint()
	// *
	// CustomerModelManager.getInstance().getConversionRuleMoneyToPoint().getPointToCreditRatio())
	// .toString();
	// }
	// Credit c1 =
	// CustomerModelManager.getInstance().getCreditByCustomerId(c.getId());
	// credit = new MyDouble(c1.getCredit()).toString();
	// } catch (Exception e) {
	// }
	//
	// }
	//
	// }
	//
	// String date = "";
	// try {
	// date = dfDate.format(invoiceDetail.getStartDate());
	// } catch (Exception e) {
	// date = dfDate.format(new Date());
	// }
	// String ptThue = "";
	// if (panelPayment.getCboTax().getSelectedItem() != null) {
	// ptThue =
	// MyDouble.valueOf(panelPayment.getCboTax().getSelectedItem().toString()).toString();
	// }
	//
	// String[] str = new String[] { enterpriseName, adres, fone, fax, date,
	// tableModel.getInvoiceDetail().getInvoiceCode(), "", txtTenPhieu.getText(),
	// gioVao, txtNhanVien.getText(),
	// MyDouble.valueOf(invoiceDetail.getTotal()).toString(),
	// MyDouble.valueOf(invoiceDetail.getDiscountRate()).toString(),
	// MyDouble.valueOf(invoiceDetail.getDiscount()).toString(), ptThue,
	// MyDouble.valueOf(invoiceDetail.getTotalTax()).toString(),
	// MyDouble.valueOf(invoiceDetail.getFinalCharge()).toString(),
	// MyDouble.valueOf(invoiceDetail.getTotalPaid()).toString(),
	// MyDouble.valueOf(invoiceDetail.getFinalCharge() -
	// invoiceDetail.getTotalPaid()).toString(), strMessenger,
	// dfDate.format(new Date()), gioRa, currencyCode, "", ratio, moneyQuyDoi, "",
	// panelPayment.getLblMoneySpell().getText(),
	// MyDouble.valueOf(invoiceDetail.getPoint()).toString(),
	// MyDouble.valueOf(invoiceDetail.getPoint()).toString(),
	// txtKhachHang.getText(), "", nvpv, credit, lienHoaDon,
	// solanIn, tongSoDiem, soDiemConLai, tongSoTien, soTienConLai, credit,
	// MyDouble.valueOf(invoiceDetail.getCredit()).toString(), "",
	// PanelTextMoneyPayment.getSoTienKhachDua(),
	// PanelTextMoneyPayment.getSoTienLeTraKhach(), diaChi, sdt };
	// try {
	// String path = "";
	// if (panelPayment.getDepartmentJComboBox().getSelectedDepartment() != null)
	// {
	// path =
	// panelPayment.getDepartmentJComboBox().getSelectedDepartment().getPath();
	// }
	// return
	// ReportPrint.getInstance().printOder(RestaurantModelManager.getInstance().getLocationOther().getCode(),
	// path, invoiceDetail, str, view);
	// } catch (Exception e) {
	// return false;
	// }
	//
	// }

	private List<Component> getAllComponents(final Container c) {
		Component[] comps = c.getComponents();
		List<Component> compList = new ArrayList<Component>();
		for (Component comp : comps) {
			compList.add(comp);
			if (!comp.equals(btnThoat) && !comp.equals(btnThanhToan) && !comp.equals(lableDate) && !comp.equals(btnLuu)) {
				comp.setEnabled(false);
			}
			if (comp instanceof Container) {
				compList.addAll(getAllComponents((Container) comp));
			}
		}
		if (type.equals(AccountingModelManager.typeSanXuat)) {
			btnThanhToan.setEnabled(false);
		}
		return compList;
	}

	@Override
	public void update(Object o, Object arg) {
		if (arg instanceof String || arg instanceof Product) {
			if (arg instanceof String) {
				try {
					quantity = Double.parseDouble(arg.toString());
				} catch (Exception e) {
					quantity = 1;
				}
				product = txtTimKiem.getItem();
				try {
					ProductPrice p = ProductModelManager.getInstance().getByProductAndProductPriceType(product.getCode(),
					    ((ProductPriceType) cbBangGia.getSelectedItem()).getType(),
					    ((Currency) cbDVTien.getSelectedItem()).getCode());
					txtPrice.setText(new MyDouble(p.getPrice()).toString());
				} catch (Exception e) {
					txtPrice.setText("0");
				}
				txtPrice.requestFocus();
				txtPrice.selectAll();
			} else {
				addProduct(txtTimKiem.getItem());
			}
		}
		// if (arg instanceof Employee || o instanceof Employee) {
		//
		// if (arg != null) {
		// Employee emp = (Employee) arg;
		// try {
		// List<AccountMembership> accMemberShips =
		// AccountModelManager.getInstance().findMembershipByAccountLoginId(
		// emp.getLoginId());
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
		// }
		// } else {
		// cbPhongBan.resetData();
		// }
		// }

		try {
			if (arg.equals(TextPopup.LOAD) && o.equals(Product.class)) {
				panelListProduct.loadGui();
			}
		} catch (Exception e) {
		}
	}

	@Override
	public void setTable(TableEat tableEat) {
	}

	@Override
	public void setListProduct(List<Product> list) {
		txtTimKiem.setData(list);

	}

	@Override
	public void Save() throws Exception {
	}

	private void addKeyBindings(JComponent jc) {
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "ESC");
		jc.getActionMap().put("ESC", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				((JDialog) getRootPane().getParent()).dispose();
			}
		});
	}
}