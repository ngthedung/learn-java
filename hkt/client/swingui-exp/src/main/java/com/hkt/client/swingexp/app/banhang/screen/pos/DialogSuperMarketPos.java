package com.hkt.client.swingexp.app.banhang.screen.pos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
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
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.hkt.client.swingexp.app.banhang.list.ChooseDelProduct;
import com.hkt.client.swingexp.app.banhang.list.PanelChoosePrice;
import com.hkt.client.swingexp.app.banhang.list.PanelChooseProductExport;
import com.hkt.client.swingexp.app.banhang.list.PanelChooseQuantity;
import com.hkt.client.swingexp.app.banhang.list.PanelUpfrontPayment;
import com.hkt.client.swingexp.app.banhang.screen.often.DialogConfig;
import com.hkt.client.swingexp.app.banhang.screen.often.DialogPayOnce;
import com.hkt.client.swingexp.app.banhang.screen.often.DialogPrint;
import com.hkt.client.swingexp.app.banhang.screen.often.PaneSetInvoice;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelDiscountProduct;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelEditTimed;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelListPartnerOrganizationWork;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelMemberServe;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelPayment;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelPaymentProduct;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelTextMoneyPayment;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelToDoWork;
import com.hkt.client.swingexp.app.banhang.screen.often.ScreenOften;
import com.hkt.client.swingexp.app.banhang.screen.often.TableModelInvoiceItem;
import com.hkt.client.swingexp.app.component.ImageTool;
import com.hkt.client.swingexp.app.component.PanelBackground;
import com.hkt.client.swingexp.app.component.TextPopup;
import com.hkt.client.swingexp.app.core.DialogList;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.HKTFile;
import com.hkt.client.swingexp.app.core.IMyObserver;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MouseEventMove;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.khuvucbanhang.PanelManageCodes;
import com.hkt.client.swingexp.app.khuyenmai.DialogChoiseMenuItem;
import com.hkt.client.swingexp.app.khuyenmai.list.TableListViewPromotion;
import com.hkt.client.swingexp.app.print.ReportPrint;
import com.hkt.client.swingexp.app.virtualkey.text.PanelTextKeyboard;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.ManagerConvert;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.PromotionModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
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
import com.hkt.module.promotion.entity.Menu;
import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.LocationAttribute;
import com.hkt.module.restaurant.entity.Project;
import com.hkt.module.restaurant.entity.Table;
import com.hkt.module.warehouse.entity.IdentityProduct;
import com.hkt.module.warehouse.entity.IdentityProduct.ExportType;
import com.hkt.util.text.DateUtil;

public class DialogSuperMarketPos extends JDialog implements IMyObserver {

	private PanelTextKeyboard panelTextKeyboard;
	private static DialogSuperMarketPos instance;
	private PanelPayment panelPayment;
	private TextPopup<Product> txtTimKiem;
	private TextPopup<Employee> txtThuNgan;
	private TextPopup<Customer> txtKhachHang;
	private JButton btnExit;
	private JButton buttonClear;
	private JButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9, buttonDot,
	    buttonDown, buttonUp;
	private JLabel lblTraSau, lblKhuyenMai, lblThietLapBan, lblChietKhauSP, lblSoLuong, lblThanhVienPV, lblXoa,
	    lblDoiGia, lblLichTrinh, lblKhuVucQuay, lblThue, lblInHoaDon, lblThanhToanLe, lblThanhToanSec, lblThanhToanThe,
	    lblThanhToan;
	private JLabel lableDate, lableTime;
	private JLabel lblKM;
	private DateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat dfTime = new SimpleDateFormat("HH:mm");
	private Dimension screenSize;
	private TableModelInvoiceItem tableModel;
	private Table table;
	private Profile profile;
	private String currencyCode = "VND";
	private String pricingType = "";
	private boolean reset;
	private String textKhuVucQuay = "Bán nhanh";
	private PanelChooseLocationTable chooseLocationTable;
	private boolean exit;
	private JScrollPane scrollPane;
	private JCheckBox chbCheck;
	private JPanel panelSearch;
	private JTextField txtSearch;

	public DialogSuperMarketPos() {
		initComponents();
	}

	public static DialogSuperMarketPos getInstance() {
		if (instance == null)
			instance = new DialogSuperMarketPos();
		return instance;
	}

	private void initComponents() {
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		PanelBackground panelBackground = new PanelBackground();
		panelBackground.setLayout(new BorderLayout(5, 0));
		panelBackground.setOpaque(true);
		panelBackground.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelBackground.add(new ContainerLeft(), BorderLayout.WEST);
		panelBackground.add(new ContainerRight(), BorderLayout.CENTER);

		this.setLayout(new BorderLayout());
		this.add(panelBackground, BorderLayout.CENTER);
		panelTextKeyboard = new PanelTextKeyboard();
		panelTextKeyboard.setVisible(false);
		this.add(panelTextKeyboard, BorderLayout.SOUTH);

		this.setUndecorated(true);
		this.setModal(true);
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		addKeyBindings(panelBackground);
	}

	private class ContainerLeft extends JPanel {
		public ContainerLeft() {
			panelPayment = new PanelPayment();
			tableModel = panelPayment.getTableModel();
			txtTimKiem = new TextPopup<Product>(Product.class);
			txtTimKiem.addObserver(DialogSuperMarketPos.this);
			txtTimKiem.setClearText(true);
			
			scrollPane = new JScrollPane();
			scrollPane.setBorder(null);
			chbCheck= new JCheckBox();
			panelSearch = new JPanel();
			panelSearch.setOpaque(false);
			chbCheck.setOpaque(false);
			txtSearch = new JTextField();
			txtSearch.setText(""); // NOI18N
			txtSearch.setFont(new java.awt.Font("Tahoma", 0, 14));
			
			panelSearch.setLayout(new BorderLayout());
			panelSearch.add(scrollPane, BorderLayout.CENTER);
			panelSearch.add(chbCheck, BorderLayout.EAST);
			scrollPane.setViewportView(txtSearch);
			chbCheck.addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(chbCheck.isSelected()){
						scrollPane.setViewportView(txtTimKiem);
					}else{
						scrollPane.setViewportView(txtSearch);
					}
					
				}
			});
			txtSearch.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==KeyEvent.VK_ENTER){
						try {
							List<Product> products = ProductModelManager.getInstance().getProductByBarcode(txtSearch.getText());	
							if(products.isEmpty()){
								Product p = ProductModelManager.getInstance().getProductByCode(txtSearch.getText());
								if(p!=null){
									addProduct(p);
									txtSearch.setText("");
								}
							}else{
								addProduct(products.get(0));
								txtSearch.setText("");
							}
						} catch (Exception e2) {
						}
						
					}
				}
			});
			
			JLabel lblSearch = new JLabel("Tìm kiếm");
			lblSearch.setFont(new Font("Tahoma", 1, 12));

			JPanel panelTopLeft = new JPanel(new BorderLayout(5, 0));
			panelTopLeft.setOpaque(false);
			panelTopLeft.add(lblSearch, BorderLayout.WEST);
			panelTopLeft.add(panelSearch, BorderLayout.CENTER);

			this.setLayout(new BorderLayout(0, 5));
			this.setOpaque(false);
			this.add(panelTopLeft, BorderLayout.NORTH);
			this.add(panelPayment, BorderLayout.CENTER);
			if (screenSize.width < 1024)
				this.setPreferredSize(new Dimension(400, 100));
			else if (screenSize.width > 1280)
				this.setPreferredSize(new Dimension(700, 100));
			else
				this.setPreferredSize(new Dimension(500, 100));
		}
	}

	private class ContainerRight extends JPanel implements ActionListener {
		private JPanel panelTop, panelCenter;

		public ContainerRight() {
			// Panel top container components
			drawTopContaner();

			// Panel center container components
			drawCenterContainer();

			this.setLayout(new BorderLayout(0, 1));
			this.setOpaque(false);
			this.add(panelTop, BorderLayout.NORTH);
			this.add(panelCenter, BorderLayout.CENTER);
		}

		private void drawTopContaner() {
			panelTop = new JPanel(new BorderLayout(0, 0));
			panelTop.setOpaque(false);

			JLabel lblHkt = new JLabel("HKT SOFT");
			lblHkt.setFont(new java.awt.Font("Tahoma", 1, 18));

			JPanel panelTop1 = new JPanel();
			panelTop1.setOpaque(false);
			lableDate = new JLabel();
			lableDate.setFont(new java.awt.Font("Tahoma", 0, 12));
			lableDate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
			lableDate.setText("dd/MM/yyyy");
			lableDate.setBorder(null);
			lableDate.setEnabled(false);
			lableDate.setOpaque(false);
			lableTime = new JLabel();
			lableTime.setFont(new java.awt.Font("Tahoma", 0, 12));
			lableTime.setHorizontalAlignment(javax.swing.JTextField.CENTER);
			lableTime.setText("HH:ss");
			lableTime.setBorder(null);
			lableTime.setEnabled(false);
			lableTime.setOpaque(false);
			lableDate.setText(dfDate.format(new Date()));
			lableTime.setText(dfTime.format(new Date()));
			panelTop1.add(lableDate);
			panelTop1.add(lableTime);

			panelTop.add(panelTop1, BorderLayout.WEST);
			panelTop.add(lblHkt, BorderLayout.EAST);
		}

		private void drawCenterContainer() {
			panelCenter = new JPanel(new BorderLayout(0, 5));
			panelCenter.setOpaque(false);

			// Top PanelContainer1
			JPanel panelButtonExit = new JPanel();
			panelButtonExit.setOpaque(false);
			btnExit = new JButton("Thoát");
			btnExit.setFont(new java.awt.Font("Tahoma", 1, 12));
			btnExit.setMargin(new java.awt.Insets(0, 0, 0, 0));
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					exitSystem();
				}
			});
			lblKM = new JLabel("Hiện");
			lblKM.setFont(new java.awt.Font("Tahoma", 0, 12));
			lblKM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			lblKM.setPreferredSize(new Dimension(40, 23));
			lblKM.addMouseListener(new MouseEventMove() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (lblKM.getText().equals("Hiện")) {
						lblKM.setText("Ẩn");
						if (tableModel.getTablePromotion().getRowCount() > 0) {
							panelPayment.getScrPomostion().setVisible(true);
						} else {
							panelPayment.setTableDiscount(true);
						}
					} else {
						lblKM.setText("Hiện");
						if (tableModel.getTablePromotion().getRowCount() > 0) {
							panelPayment.getScrPomostion().setVisible(false);
						} else {
							panelPayment.setTableDiscount(false);
						}
					}
				}
			});
			panelButtonExit.add(lblKM);
			panelButtonExit.add(btnExit);

			JPanel panelContainer1 = new JPanel(new GridBagLayout());
			panelContainer1.setBackground(Color.WHITE);
			panelContainer1.setPreferredSize(new Dimension(100, 40));
			GridBagConstraints bagConstraints = new GridBagConstraints();
			bagConstraints.fill = GridBagConstraints.HORIZONTAL;

			JLabel lblThuNgan = new JLabel("Thu ngân");
			lblThuNgan.setFont(new Font("Tahoma", 1, 12));
			bagConstraints.weightx = 0;
			bagConstraints.gridx = 0;
			bagConstraints.gridy = 0;
			bagConstraints.insets = new Insets(0, 5, 0, 5);
			panelContainer1.add(lblThuNgan, bagConstraints);

			txtThuNgan = new TextPopup<Employee>(Employee.class);
			txtThuNgan.addObserver(DialogSuperMarketPos.this);
			txtThuNgan.setPreferredSize(new Dimension(50, 23));
			bagConstraints.weightx = 0.5;
			bagConstraints.gridx = 1;
			bagConstraints.gridy = 0;
			panelContainer1.add(txtThuNgan, bagConstraints);

			JLabel lblKhachHang = new JLabel("Khách hàng");
			lblKhachHang.setFont(new Font("Tahoma", 1, 12));
			bagConstraints.weightx = 0;
			bagConstraints.gridx = 2;
			bagConstraints.gridy = 0;
			panelContainer1.add(lblKhachHang, bagConstraints);

			txtKhachHang = new TextPopup<Customer>(Customer.class);
			txtKhachHang.addObserver(DialogSuperMarketPos.this);
			txtKhachHang.setPreferredSize(new Dimension(50, 23));
			bagConstraints.weightx = 0.5;
			bagConstraints.gridx = 3;
			bagConstraints.gridy = 0;
			panelContainer1.add(txtKhachHang, bagConstraints);

			bagConstraints.insets = new Insets(0, 0, 0, 0);
			bagConstraints.weightx = 0;
			bagConstraints.gridx = 4;
			bagConstraints.gridy = 0;
			panelContainer1.add(panelButtonExit, bagConstraints);

			// Center panelContainer2
			JPanel panelContainer2 = new JPanel(new BorderLayout(0, 10));
			panelContainer2.setOpaque(false);

			JPanel panelButtonFunction = new JPanel(new GridLayout(4, 3, 10, 10));
			panelButtonFunction.setOpaque(false);
			panelButtonFunction.setPreferredSize(new Dimension(100, 300));

			createButtonFunction();
			panelButtonFunction.add(lblKhuVucQuay);
			panelButtonFunction.add(lblLichTrinh);
			panelButtonFunction.add(lblThue);
			panelButtonFunction.add(lblXoa);
			panelButtonFunction.add(lblThanhVienPV);
			panelButtonFunction.add(lblChietKhauSP);
			panelButtonFunction.add(lblSoLuong);
			panelButtonFunction.add(lblThietLapBan);
			panelButtonFunction.add(lblKhuyenMai);
			panelButtonFunction.add(lblDoiGia);
			panelButtonFunction.add(lblTraSau);
			panelButtonFunction.add(lblInHoaDon);

			JPanel panelButtonNumber = new JPanel(new BorderLayout(10, 0));
			panelButtonNumber.setOpaque(false);
			JPanel panelButton1 = new JPanel(new GridLayout(5, 3, 10, 10));
			panelButton1.setOpaque(false);
			final JPanel panelButton2 = new JPanel(new GridLayout(5, 1, 0, 10));
			panelButton2.setOpaque(false);
			panelButtonNumber.add(panelButton1, BorderLayout.CENTER);
			panelButtonNumber.add(panelButton2, BorderLayout.EAST);

			lblInHoaDon.addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent e) {
					panelButton2.setPreferredSize(new Dimension(lblInHoaDon.getSize().width, 100));
					panelButton2.updateUI();
				}
			});

			createButtonNumbers1();
			panelButton1.add(button7);
			panelButton1.add(button8);
			panelButton1.add(button9);
			panelButton1.add(button4);
			panelButton1.add(button5);
			panelButton1.add(button6);
			panelButton1.add(button1);
			panelButton1.add(button2);
			panelButton1.add(button3);
			panelButton1.add(buttonUp);
			panelButton1.add(buttonDot);
			panelButton1.add(button0);
			panelButton1.add(buttonDown);
			panelButton1.add(buttonClear);

			createButtonNumbers2();
			panelButton2.add(lblThanhToanLe);
			panelButton2.add(lblThanhToanSec);
			panelButton2.add(lblThanhToanThe);
			panelButton2.add(lblThanhToan);

			panelContainer2.add(panelButtonFunction, BorderLayout.NORTH);
			panelContainer2.add(panelButtonNumber, BorderLayout.CENTER);

			// Add Top, Centre, Bottom
			panelCenter.add(panelContainer1, BorderLayout.NORTH);
			panelCenter.add(panelContainer2, BorderLayout.CENTER);
		}

		private void createButtonFunction() {
			lblKhuVucQuay = new JLabelCustom(
			    "<html><p align='center'>Khu vực/quầy<br/><font color='rgb(184,184,184)' size='4'>" + textKhuVucQuay
			        + "</font></p></html>");
			lblLichTrinh = new JLabelCustom("Lịch trình");
			lblThue = new JLabelCustom("Thuế");
			lblXoa = new JLabelCustom("Xóa");
			lblThanhVienPV = new JLabelCustom("Thành viên PV");
			lblChietKhauSP = new JLabelCustom("Sửa ngày hóa đơn");
			lblSoLuong = new JLabelCustom("Số lượng");
			lblThietLapBan = new JLabelCustom("Thiết lập bán");
			lblKhuyenMai = new JLabelCustom("Khuyến mại");
			lblDoiGia = new JLabelCustom("Đổi giá");
			lblTraSau = new JLabelCustom("Trả sau");
			lblInHoaDon = new JLabelCustom("In hóa đơn");

			lblLichTrinh.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
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
							txtKhachHang.setObject(customer);
							txtKhachHang.setText(customer.toString());
							// txtPartner.setItem(customer);
							txtKhachHang.setObject(null);
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
						} catch (Exception e1) {
						}
					}
				}
			});

			lblDoiGia.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
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
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});

			lblXoa.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
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
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});

			lblThanhVienPV.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
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
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			});

			lblSoLuong.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
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
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});

			lblThue.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					PanelChooseTaxPos chooseTaxPos = new PanelChooseTaxPos(panelPayment);
					chooseTaxPos.setTaxSelectedByIndex(panelPayment.getCboTaxSelectedIndex());
					DialogResto dialog = new DialogResto(chooseTaxPos, false, 0, 100);
					dialog.setName("dlChooseTaxPos");
					dialog.setTitle("Nhập thuế");
					dialog.setVisible(true);
					dialog.setLocationRelativeTo(null);
					if (!chooseTaxPos.isClickSave()) {
						panelPayment.setTax(0);
					}
				}
			});

			lblKhuVucQuay.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (lblKhuVucQuay.isEnabled()) {
						if (chooseLocationTable == null) {
							chooseLocationTable = new PanelChooseLocationTable();
						}
						DialogResto dialog = new DialogResto(chooseLocationTable, false, 0, 100);
						dialog.setName("dlchooseLocationTable");
						dialog.setTitle("Chọn khu vực bàn quầy");
						dialog.setVisible(true);
						dialog.setLocationRelativeTo(null);
						if (chooseLocationTable.isClickSave()) {
							table = chooseLocationTable.getTableSelected();
							String newText = chooseLocationTable.getLocationSelected().getName() + "/" + table.getName();
							if (newText.contains("Bán nhanh")) {
								newText = "Bán nhanh";
							}
							lblKhuVucQuay.setText(lblKhuVucQuay.getText().replaceAll(textKhuVucQuay, newText));
							textKhuVucQuay = newText;
							setTable(table);
							saveStatusInvoice();
							lblKhuVucQuay.setEnabled(true);
						}
					}
				}
			});

			lblChietKhauSP.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					setupEditTime();
				}
			});

			lblThietLapBan.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					DialogConfig dialog = new DialogConfig(false, 600, 100);
					dialog.setName("dlThietLapBH");
					dialog.setTitle("Thiết lập bán hàng");
					dialog.setVisible(true);
				}
			});

			lblKhuyenMai.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					TableListViewPromotion tbviewpromotion = new TableListViewPromotion();
					tbviewpromotion.setName("tbviewpromotion");
					DialogList dialog = new DialogList(tbviewpromotion);

					dialog.setTitle("Xem khuyến mại");
					dialog.setName("tbviewpromotion");
					dialog.setVisible(true);
				}
			});

			lblTraSau.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					paymentAfter();
				}
			});

			lblInHoaDon.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					DialogPrint dialogPrint = new DialogPrint(
					    getMoney(tableModel.getInvoiceDetail().getFinalCharge() - tableModel.getInvoiceDetail().getTotalPaid())
					        .toString(), currencyCode);
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
							;
							if (tableModel.getInvoiceDetail().getStatus().equals(Status.Paid)) {
								tableModel.getInvoiceDetail().setTotalPaid(0);
							}
							printAgain = true;
							printReceipt(true, false, null);
							printAgain = false;
							if (tableModel.getInvoiceDetail().getStatus().equals(Status.Paid)) {
								tableModel.getInvoiceDetail().setTotalPaid(totalP);
							}
						}
					}
				}
			});
		}

		private void createButtonNumbers1() {
			button0 = new JButton("0");
			button1 = new JButton("1");
			button2 = new JButton("2");
			button3 = new JButton("3");
			button4 = new JButton("4");
			button5 = new JButton("5");
			button6 = new JButton("6");
			button7 = new JButton("7");
			button8 = new JButton("8");
			button9 = new JButton("9");
			buttonDot = new JButton(".");
			buttonClear = new JButton("Hủy");
			buttonUp = new JButton("");
			buttonDown = new JButton("");

			button0.setFont(new Font("Tahoma", 1, 30));
			button1.setFont(new Font("Tahoma", 1, 30));
			button2.setFont(new Font("Tahoma", 1, 30));
			button3.setFont(new Font("Tahoma", 1, 30));
			button4.setFont(new Font("Tahoma", 1, 30));
			button5.setFont(new Font("Tahoma", 1, 30));
			button6.setFont(new Font("Tahoma", 1, 30));
			button7.setFont(new Font("Tahoma", 1, 30));
			button8.setFont(new Font("Tahoma", 1, 30));
			button9.setFont(new Font("Tahoma", 1, 30));
			buttonDot.setFont(new Font("Tahoma", 1, 30));
			buttonClear.setFont(new Font("Tahoma", 1, 18));
			buttonUp.setFont(new Font("Tahoma", 1, 30));
			buttonDown.setFont(new Font("Tahoma", 1, 30));

			buttonUp.setIcon(ImageTool.getInstance().resize(new ImageIcon(HomeScreen.class.getResource("icon/arrow_up.png")),
			    new Dimension(50, 50)));
			buttonUp.setHorizontalAlignment(SwingConstants.CENTER);
			buttonUp.setHorizontalTextPosition(SwingConstants.CENTER);

			buttonDown.setIcon(ImageTool.getInstance().resize(
			    new ImageIcon(HomeScreen.class.getResource("icon/arrow_down.png")), new Dimension(50, 50)));
			buttonDown.setHorizontalAlignment(SwingConstants.CENTER);
			buttonDown.setHorizontalTextPosition(SwingConstants.CENTER);

			button0.addActionListener(this);
			button1.addActionListener(this);
			button2.addActionListener(this);
			button3.addActionListener(this);
			button4.addActionListener(this);
			button5.addActionListener(this);
			button6.addActionListener(this);
			button7.addActionListener(this);
			button8.addActionListener(this);
			button9.addActionListener(this);

			buttonUp.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int rowSelected = panelPayment.getTable_Sales1().getSelectedRow() - 1;
					int countRow = panelPayment.getTable_Sales1().getRowCount();
					if (rowSelected >= 0) {
						panelPayment.getTable_Sales1().setRowSelectionInterval(rowSelected, rowSelected);
					} else {
						panelPayment.getTable_Sales1().setRowSelectionInterval(countRow - 1, countRow - 1);
					}
				}
			});

			buttonDown.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int rowSelected = panelPayment.getTable_Sales1().getSelectedRow() + 1;
					if (rowSelected >= 1 && rowSelected < panelPayment.getTable_Sales1().getRowCount()) {
						panelPayment.getTable_Sales1().setRowSelectionInterval(rowSelected, rowSelected);
					} else {
						panelPayment.getTable_Sales1().setRowSelectionInterval(0, 0);
					}
				}
			});

			buttonClear.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (UIConfigModelManager.getInstance().getPermission(ScreenOften.permission) != Capability.ADMIN) {
						JOptionPane.showMessageDialog(null, "Bạn chưa được phân quyền này !");
						return;
					} else {

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
								if (tableModel.getInvoiceDetail().getId() != null) {
									InvoiceDetail invoiceDetail = tableModel.getInvoiceDetail();
									invoiceDetail.setRecycleBin(true);
									AccountingModelManager.getInstance().deleteInvoice(invoiceDetail);

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
				}
			});
		}

		private void createButtonNumbers2() {
			lblThanhToanLe = new JLabelCustom("Thanh toán lẻ");
			lblThanhToanSec = new JLabelCustom("Thanh toán Séc");
			lblThanhToanThe = new JLabelCustom("Thanh toán thẻ Credit");
			lblThanhToan = new JLabelCustom("Thanh toán");

			lblThanhToanLe.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					paymentSlip();
				}
			});

			lblThanhToanSec.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {

				}
			});

			lblThanhToanThe.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						PanelTextMoneyPayment panel = new PanelTextMoneyPayment();
						panel.setName("panelPayment");
						panel.getjRadioButton2().setSelected(true);
						panel.initEvent(
						    MyDouble.valueOf(
						        tableModel.getInvoiceDetail().getFinalCharge() - tableModel.getInvoiceDetail().getTotalPaid())
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
						payment();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});

			lblThanhToan.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						PanelTextMoneyPayment panel = new PanelTextMoneyPayment();
						panel.setName("panelPayment");
						panel.initEvent(
						    MyDouble.valueOf(
						        tableModel.getInvoiceDetail().getFinalCharge() - tableModel.getInvoiceDetail().getTotalPaid())
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
						payment();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btn = (JButton) e.getSource();
			try {
				double a = Double.parseDouble(btn.getText());
				InvoiceItem invoiceItem = panelPayment.getSelectedInvoiceItem();
				invoiceItem.setQuantity(a);
				tableModel.updateItem(invoiceItem);
			} catch (Exception e2) {
			}
		}
	}

	public void setReset(boolean reset) {
		this.reset = reset;
	}

	// thanh toán toàn bộ hóa đơn
	protected void payment() {
		// long endTime = System.currentTimeMillis();
		saveContributorOther();
		InvoiceDetail invoice = tableModel.getInvoiceDetail();
		// if (invoice.getFinalCharge() != invoice.getTotalPaid()) {
		InvoiceTransaction transaction = new InvoiceTransaction();
		transaction.setTransactionType(TransactionType.Cash);
		transaction.setCreatedBy(ManagerAuthenticate.getInstance().getLoginId());
		transaction.setCurrencyUnit(currencyCode);
		transaction.setTotal(invoice.getFinalCharge() - invoice.getTotalPaid());
		transaction.setTransactionDate(new Date());
		invoice.add(transaction);
		// }
		if (invoice.getEndDate() == null) {
			invoice.setEndDate(new Date());
			String code = AccountingModelManager.getInstance().getCodeObject(PanelManageCodes.InvoiceBH,
			    AccountingModelManager.typeBanHang, invoice.getStartDate(), true);
			if (code != null) {
				invoice.setInvoiceCode(code);
			} else {
				invoice.setInvoiceCode(panelPayment.getTxtInvoiceCode().getText());
			}
		}

		// long endTime1 = System.currentTimeMillis();
		// System.out.println("payment111111: "+ new
		// DecimalFormat("#0.00000").format((endTime1 - endTime) / 1000d));
		if (invoice.getTotalPaid() == 0) {
			for (InvoiceItem invoiceItem : invoice.getItems()) {
				if (!invoiceItem.getStatus().equals(AccountingModelManager.isCance)
				    && !invoiceItem.getStatus().equals(AccountingModelManager.isForRent)
				    && !invoiceItem.getStatus().equals(AccountingModelManager.isPromotion)) {
					invoiceItem.setStatus(AccountingModelManager.isPayment);
				}

			}
			tableModel.updateStatus(AccountingModelManager.isPayment);

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
			if (!invoice.getStatus().equals(Status.Paid)) {
				if (!printReceipt(true, false, getInvoiceDetailPayment(invoiceItems))) {
					DialogNotice.getInstace().setVisible(true);
				}
			} else {
				printAgain = true;
				if (!printReceipt(true, false, null)) {
					DialogNotice.getInstace().setVisible(true);
				}
				printAgain = false;
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
			invoice.setGuest(0);
		}
		invoice.setTotalPaid(invoice.getFinalCharge());
		// long endTime2 = System.currentTimeMillis();
		// System.out.println("payment2: "+ new
		// DecimalFormat("#0.00000").format((endTime2 - endTime1) / 1000d));
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
										count--;
										if (count == quantity) {
											break;
										}
									}
								}
							}
						}
						invoiceItem.setQuantityReal(invoiceItem.getQuantity());
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

		if (reset) {
			dispose();
		}
		reset();
	}

	// Trả sau hóa đơn
	private void paymentAfter() {
		saveContributorOther();
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
		} catch (Exception e) {

		}

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
		try {
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
		if (reset) {
			dispose();
		}
		reset();

	}

	// Hiển thị khách hàng đã chọn lên text
	private void loadCustomerViewText() {
		try {
			Customer c = CustomerModelManager.getInstance()
			    .getCustomerByCode(tableModel.getInvoiceDetail().getCustomerCode());
			txtKhachHang.setItem(c);
			try {
				tableModel.setInfoInvoice(c.getLoginId(), pricingType, table);
			} catch (Exception e) {
				tableModel.setInfoInvoice(c.getLoginId(), pricingType, table);
			}
		} catch (Exception e) {
			txtKhachHang.setItem(null);
			tableModel.setInfoInvoice("", pricingType, table);
		}

	}

	// Hiển thị nhân viên đã chọn lên text
	private void loadEmployeeViewText() {
		try {
			Employee c = HRModelManager.getInstance().getEmployeeByCode(tableModel.getInvoiceDetail().getEmployeeCode());
			txtThuNgan.setItem(c);
		} catch (Exception e) {
			txtThuNgan.setItem(null);
		}

	}

	private void resetGui() {
		try {
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
			panelPayment.refeshGui();
		} catch (Exception e) {

		}
		// System.out.println("opend   " + new JVMEnv().getMemoryInfo());
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
	public void loadData() {
		profile = AccountModelManager.getInstance().getProfileConfigAdmin();
		try {
			List<Employee> list1 = HRModelManager.getInstance().getEmployees();
			txtThuNgan.setData(list1);
		} catch (Exception e) {
		}

		try {
			List<Customer> customers = CustomerModelManager.getInstance().getCustomers(false);
			txtKhachHang.setData(customers);
		} catch (Exception e) {
		}

		try {
			List<Product> ps = ProductModelManager.getInstance().findAllProducts();
			txtTimKiem.setData(ps);
		} catch (Exception e) {
		}

		panelPayment.loadCboTax();
		// if (profile.get(DialogConfig.Keyboard) != null &&
		// profile.get(DialogConfig.Keyboard).toString().equals("true")) {
		// panelTextKeyboard.setVisible(true);
		// } else {
		// panelTextKeyboard.setVisible(false);
		// }
		setEditorGui(true);
		setTable(RestaurantModelManager.getInstance().getTablePaymentAfter());
	}

	private void setEditorGui(boolean a) {
		System.out.println(UIConfigModelManager.getInstance().getPermission(ScreenOften.permission));
		if (UIConfigModelManager.getInstance().getPermission(ScreenOften.permission) == Capability.READ) {
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

	// Xem lại hóa đơn, load data
	public void setInvoiceDetail(InvoiceDetail invoiceDetail) {
		exit = true;
		try {
			table = RestaurantModelManager.getInstance().getTableByCode(invoiceDetail.getTableCode());
		} catch (Exception e) {
			table = new Table();
			table.setCode("a");
			table.setLocationCode("b");
		}

		if (invoiceDetail != null) {
			panelPayment.getTxtInvoiceCode().setText(invoiceDetail.codeView());
			tableModel.setData(invoiceDetail);
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
			loadEmployeeViewText();

			panelPayment.refershData();

			lableDate.setText(dfDate.format(invoiceDetail.getStartDate()));
			lableTime.setText(dfTime.format(invoiceDetail.getStartDate()));
			setEditorGui(false);
		}
	}

	// Lưu trạng thái hóa đơn khi thanh toán
	private void saveStatusInvoice() {
		if (this.table != null && tableModel.getInvoiceDetail().getEndDate() == null) {
			if (tableModel.getRowCount() > 0) {
				try {
					tableModel.getInvoiceDetail().setGuest(0);
					saveContributorOther();
					AccountingModelManager.getInstance().saveInvoice(tableModel.getInvoiceDetail());
					table.setInvoiceCode(String.valueOf(tableModel.getInvoiceDetail().getId()));
					try {
						RestaurantModelManager.getInstance().saveTable(table);
					} catch (Exception e) {
					}
					tableModel.getTablePromotion().saveAllPromotionProduct();
				} catch (Exception e) {
				}
			} else {
				try {
					AccountingModelManager.getInstance().deleteInvoice(tableModel.getInvoiceDetail());
				} catch (Exception e) {
				}
			}
		} else {
			System.out.println(table.getInvoiceCode());
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
		String projectTable = table.getName();
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
		String nvpv = "";
		String lienHoaDon = "1", solanIn = "1";
		String tongSoDiem = "", soDiemConLai = "", tongSoTien = "", soTienConLai = "";
		String credit = "";
		String gioVao = lableTime.getText(), gioRa = dfTime.format(new Date());
		String diaChi = "", sdt = "";
		if (txtKhachHang.getItem() != null) {
			diaChi = AccountModelManager.getInstance().getAddressByLoginId(txtKhachHang.getItem().getLoginId());
			sdt = txtKhachHang.getItem().getMobile();
			try {
				Credit c = CustomerModelManager.getInstance().getCreditByCustomerId(txtKhachHang.getItem().getId());
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
		Customer c = txtKhachHang.getItem();
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
			if (tableModel.getInvoiceDetail().getEndDate() != null) {
				if (tableModel.getInvoiceDetail().getTransactions() != null
				    && !tableModel.getInvoiceDetail().getTransactions().isEmpty()) {
					dateEnd = tableModel.getInvoiceDetail().getTransactions()
					    .get(tableModel.getInvoiceDetail().getTransactions().size() - 1).getTransactionDate();
				} else {
					dateEnd = tableModel.getInvoiceDetail().getEndDate();
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
		    txtThuNgan.getText(), MyDouble.valueOf(invoiceDetail.getTotal()).toString(),
		    discountRate,
		    discount, ptThue,
		    MyDouble.valueOf(invoiceDetail.getTotalTax()).toString(), tienThue, datCoc, phaiTra, strStt,
		    dfDate.format(dateEnd), gioRa, currencyCode, MyDouble.valueOf(invoiceDetail.getService()).toString(), ratio,
		    moneyQuyDoi, MyDouble.valueOf(invoiceDetail.getServiceRate()).toString(), strMoney, soDiemDung,
		    MyDouble.valueOf(invoiceDetail.getPoint()).toString(), txtKhachHang.getText(), "", nvpv, credit, lienHoaDon,
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
			if (table != null) {
				table.setDescription("Market");
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

	private boolean printAgain;

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
	protected void paymentSlip() {
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

	private InvoiceDetail getInvoiceDetailPayment(List<InvoiceItem> invoiceItems) {
		InvoiceDetail invoiceDetail2 = new InvoiceDetail(true);
		for (InvoiceItem invoiceItem : invoiceItems) {
			invoiceDetail2.add(invoiceItem);
		}
		invoiceDetail2.calculate(new DefaultInvoiceCalculator());
		return invoiceDetail2;
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

		Customer customer = txtKhachHang.getItem();
		if (customer != null) {
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
		if (txtThuNgan.getItem() != null) {
			Employee nhanVien = (Employee) txtThuNgan.getItem();
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

	public void reset() {
		tableModel.setData(new InvoiceDetail(true));
		tableModel.changeCaculate("", "");
		txtKhachHang.setText("");
		txtKhachHang.setObject(null);
		txtKhachHang.setItem(null);
		txtThuNgan.setText("");
		txtThuNgan.setObject(null);
		txtThuNgan.setItem(null);
		table.setInvoiceCode("");
		try {
			table = RestaurantModelManager.getInstance().saveTable(table);
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean flag = lblKhuVucQuay.isEnabled();
		setTable(table);
		lblKhuVucQuay.setEnabled(flag);
	}

	public void setEnableKhuVuc(boolean value) {
		lblKhuVucQuay.setEnabled(value);
	}

	// Mở 1 bàn mới
	public void setTable(Table tableEat) {
		if (tableEat == null) {
			return;
		}
		txtTimKiem.requestFocus();
		try {
			panelPayment.getTable_Sales1().getCellEditor().stopCellEditing();
		} catch (Exception e) {
		}
		this.table = tableEat;

		panelPayment.setTable(table);
		InvoiceDetail invoiceDetail = AccountingModelManager.getInstance().getInvoiceDetail(table.invoiceId());
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
				Currency c = LocaleModelManager.getInstance()
				    .getCurrencyByCode(tableModel.getInvoiceDetail().getCurrencyUnit());
				invoiceDetail.setCurrencyRate(c.getRate());
			} catch (Exception e) {
				invoiceDetail.setCurrencyRate(1);
			}
			invoiceDetail.setCurrencyUnit(currencyCode);
			invoiceDetail.setStartDate(new Date());
			invoiceDetail.setDiscount(0);
			invoiceDetail.setInvoiceName(table.getName());
			invoiceDetail.setTableCode(table.getCode());
			invoiceDetail.setLocationCode(table.getLocationCode());
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
			String projectCode = "";
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

			if (!projectCode.equals("")) {
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
			tableModel.setInfoInvoice("", pricingType, table);
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
			if (invoiceDetail.getDepartmentCode() != null
			    && !invoiceDetail.getDepartmentCode().equals(HRModelManager.getInstance().getDepartmentOther().getPath()))
				panelPayment.getDepartmentJComboBox().setSelectDepartmentByPath(invoiceDetail.getDepartmentCode());
			else
				panelPayment.getDepartmentJComboBox().setSelectedIndex(0);
			if (invoiceDetail.getProjectCode() != null && !invoiceDetail.getProjectCode().equals("")) {
				String[] pathProject = invoiceDetail.getProjectCode().split("/");
				Project project = RestaurantModelManager.getInstance().getProjectByCode(pathProject[pathProject.length - 1]);
				panelPayment.getTxtProject().setItem(project);
			} else
				panelPayment.getTxtProject().setItem(null);
			if (invoiceDetail.getStoreCode() != null && !invoiceDetail.getStoreCode().equals(""))
				panelPayment.getStoreJComboBox().setSelectStoreByCode(invoiceDetail.getStoreCode());
			else
				panelPayment.getStoreJComboBox().setSelectedIndex(0);
			tableModel.setData(invoiceDetail);
			loadEmployeeViewText();
			loadCustomerViewText();

			if (table.getStatus().equals(Table.Status.TableGross)) {
				tableModel.updateStatus(AccountingModelManager.isRecord);
				tableModel.updateStatus(AccountingModelManager.isKitchen);
				tableModel.updateStatus(AccountingModelManager.isPayment);
				tableModel.updateStatus(AccountingModelManager.isPromotion);
				tableModel.updateStatus(AccountingModelManager.isRecord);
			}

		}

		lblKhuVucQuay.setEnabled(false);
	}

	private void resetTable() {
		try {
			Employee e = HRModelManager.getInstance().getBydLoginId(ManagerAuthenticate.getInstance().getLoginId());
			if (e != null) {
				txtThuNgan.setItem(e);
			} else {
				txtThuNgan.setItem(null);
			}
			txtKhachHang.setItem(null);
		} catch (Exception e) {
			txtThuNgan.setItem(null);
			txtKhachHang.setItem(null);
		}
		lableDate.setText(dfDate.format(new Date()));
		lableTime.setText(dfTime.format(new Date()));
	}

	private void addProduct(Product product) {
		// System.out.println(product);
		if (tableModel.getInvoiceDetail().getId() == null) {
			try {
				InvoiceDetail invoiceDetail = AccountingModelManager.getInstance().saveInvoice(tableModel.getInvoiceDetail());
				tableModel.setData(invoiceDetail);
			} catch (Exception e) {
			}
		}
		try {
			String stt = AccountingModelManager.isRecord;
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
					invoiceItem.setItemCode("menu" + DateUtil.asCompactDateTimeId(new Date()));
				} else {
					invoiceItem.setItemCode(String.valueOf(new Date().getTime()));
				}

				invoiceItem.setQuantity(1);
				invoiceItem.setUnitPrice(0);
				invoiceItem.setActivityType(InvoiceItem.ActivityType.Receipt);
				invoiceItem.setTotal(0);
				invoiceItem.setCurrencyUnit(currencyCode);
				invoiceItem.setProductCode(product.getCode());
				invoiceItem.setWarehouseCode(tableModel.getInvoiceDetail().getWarehouseId());
				if (!product.getCode().equals("kara")) {
					invoiceItem.setStatus(AccountingModelManager.isRecord);
				} else {
					invoiceItem.setStatus(AccountingModelManager.isKitchen);
				}

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

				tableModel.addItem(invoiceItem);
				tableModel.changeCaculate("", "");

			} else {
				invoiceItem.setQuantity(invoiceItem.getQuantity() + 1);
				tableModel.updateItem(invoiceItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private class JLabelCustom extends JLabel {
		public JLabelCustom(String name) {
			super(name);
			this.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			this.setFont(new java.awt.Font("Tahoma", 0, 18));
			this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
			this.setOpaque(true);
			this.setBackground(new Color(149, 185, 199));
			this.setForeground(Color.WHITE);
			this.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseExited(MouseEvent e) {
					JLabel button = (JLabel) e.getSource();
					button.setOpaque(true);
					button.setBackground(new Color(149, 185, 199));
					button.setFont(new Font("Tahoma", Font.PLAIN, 18));
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					JLabel button = (JLabel) e.getSource();
					button.setOpaque(true);
					button.setBackground(new Color(252, 124, 4));
					button.setFont(new Font("Tahoma", Font.BOLD, 18));
				}

				@Override
				public void mouseMoved(MouseEvent e) {
					JLabel button = (JLabel) e.getSource();
					button.setOpaque(true);
					button.setBackground(new Color(252, 124, 4));
					button.setFont(new Font("Tahoma", Font.BOLD, 18));
				}
			});
		}
	}

	protected void exitSystem() {
		saveStatusInvoice();
		dispose();

	}

	public void addKeyBindings(JComponent jc) {

		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0, false), "F3");
		jc.getActionMap().put("F3", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
			}
		});
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0, false), "F4");
		jc.getActionMap().put("F4", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				// printInvoice();
			}
		});
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0, false), "abc");
		jc.getActionMap().put("abc", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {

			}
		});
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0, false), "F5");
		jc.getActionMap().put("F5", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					// payment();
				} catch (Exception e) {
				}

			}
		});
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0, false), "F6");
		jc.getActionMap().put("F6", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				// paymentSlip();
			}
		});
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0, false), "F7");
		jc.getActionMap().put("F7", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				// paymentAfter();
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
				// viewKH();
			}
		});
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0, false), "F9");
		jc.getActionMap().put("F9", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				// txtTable.requestFocus();
			}
		});
	}

	@Override
	public void update(Object o, Object arg) {
		try {
			// Nếu [arg] = null thì đẩy kiểu đối tượng vào [o]
			if (arg instanceof Product) {
				if (txtTimKiem.getItem() != null) {
					addProduct(txtTimKiem.getItem());
				}
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
						tableModel.setInfoInvoice(customer.getLoginId(), pricingType, table);
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
						tableModel.setInfoInvoice("", pricingType, table);
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

					AccountGroup departmentCashier = panelPayment.getDepartmentJComboBox().getSelectedDepartment();
					if (departmentCashier != null) {
						tableModel.getInvoiceDetail().setDepartmentCode(departmentCashier.getPath());
					} else {
						tableModel.getInvoiceDetail()
						    .setDepartmentCode(HRModelManager.getInstance().getDepartmentOther().getPath());
					}

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
	}
}
