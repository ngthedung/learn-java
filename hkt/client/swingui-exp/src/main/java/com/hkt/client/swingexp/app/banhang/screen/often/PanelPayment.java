package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.hkt.client.swingexp.app.banhang.PanelTax;
import com.hkt.client.swingexp.app.banhang.screen.market.DialogSuperMarket;
import com.hkt.client.swingexp.app.banhang.screen.market.PanelButton;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.component.TextPopup;
import com.hkt.client.swingexp.app.core.DepartmentJComboBox;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.IMyObserver;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.core.StoreJComboBox;
import com.hkt.client.swingexp.app.khuvucbanhang.PanelManageCodes;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.ManagerConvert;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.PromotionModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.accounting.entity.Contributor;
import com.hkt.module.accounting.entity.Invoice.Status;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.accounting.entity.InvoiceTransaction;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.partner.customer.entity.Credit;
import com.hkt.module.partner.customer.entity.CreditTransaction;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.customer.entity.Point;
import com.hkt.module.partner.customer.entity.PointTransaction;
import com.hkt.module.product.entity.Tax;
import com.hkt.module.promotion.entity.Coupon;
import com.hkt.module.promotion.entity.CouponUsed;
import com.hkt.module.restaurant.entity.Project;
import com.hkt.module.restaurant.entity.Table;

public class PanelPayment extends JPanel implements IMyObserver {
	private TableModelInvoiceItem tableModel;
	private Point point;
	private Credit credit;
	private TableModelDiscountItem tableModelDiscountItem;
	private TableModelPayment TbModelPayment;
	private Table table;
	private JScrollPane scrollPanePay, scroll, scrollWaiter, s1;
	private JTabbedPane tabbedPane;
	protected boolean pressCtrl;
	private double rounding;
	private JTextArea txtNote;
	private JComboBox<Tax> cboTax;
	private JCheckBox chbPoint;
	private JCheckBox chbCredit;
	private JLabel jLabel14;
	private JLabel lbDaTra;
	private JLabel lbPhaiTra;
	private JLabel jLabel7;
	private JPanel jPanel6;
	private JPanel jPanel9;
	private JPanel panel;
	private JLabel lableUnitMoney1;
	private JLabel lbMark;
	private JLabel lbMark1;
	private JLabel lbMoneyTax;
	private JLabel lbTax;
	private JLabel lbUnitMoney;
	private JLabel lblMoneySpell;
	private JPanel panelSumMoney1;
	private JRadioButton rbtnDiscountMoney;
	private JRadioButton rbtnDiscountPercent;
	private JRadioButton rbtnDiscountService;
	private JRadioButton rbtnServiceMoney;
	private JScrollPane scrPomostion;
	private JScrollPane scrPomostion2;
	private JScrollPane scrPomostion1;
	private JTable tableDiscountItem;
	private TableSales table_Sales1;
	private JTextField txtMoneyDiscount;
	private JTextField txtMoneyMark;
	private JTextField txtMoneyCredit;
	private JTextField txtMoneyMissing;
	private JTextField txtMoneyPerformed;
	private JTextField txtPercenDiscount;
	private JTextField txtTotalMark;
	private JTextField txtTotaltCredit;
	private JTextField txtTotalMoneyAfterTax;
	private JTextField txtTotalMoneyBeforDiscount1;
	private JTextField txtTotalMoneyTax;
	private JTextField txtMoneyService;
	private JTextField txtPercenService;
	private boolean rest;
	private String currencyCode = "VND";
	private JPanel panelTabInfoMore, panelTabWaiter;
	private DepartmentJComboBox cboDepartment;
	private StoreJComboBox cboStore;
	private TextPopup<Project> txtProject;
	private boolean isSuperMarket;
	private boolean sl = true, gia = true;
	private PanelButton panelButton;
	private TableModelCouponInvoice tableModelCouponInvoice;
	private TextPopup<Coupon> txtCoupon;
	private MyJDateChooser dateForRent;
	private JCheckBox chbForRent;
	private JCheckBox chbPayment;
	private JLabel lbForRent;
	private Profile profile;
	private JTextField txtInvoiceCode;
	private JPanel panelC;

	public double getRounding() {
		return rounding;
	}

	public JTextField getTxtInvoiceCode() {
		return txtInvoiceCode;
	}

	public MyJDateChooser getDateForRent() {
		return dateForRent;
	}

	public JCheckBox getChbForRent() {
		return chbForRent;
	}

	public String getNote() {
		return txtNote.getText();
	}

	public TextPopup<Project> getTxtProject() {
		return txtProject;
	}

	public PanelButton getPanelButton() {
		return panelButton;
	}

	private PanelWaiters panelWaiters;

	public void setSuperMarket(boolean isSuperMarket, DialogSuperMarket dialog) {
		this.isSuperMarket = isSuperMarket;
		panelButton.setDialogMaket(dialog);
	}

	public JComboBox getCboTax() {
		return cboTax;
	}

	public void setTableDiscount(boolean a) {
		scrPomostion2.setVisible(a);
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
		try {
			String name = LocaleModelManager.getInstance().getCurrencyByCode(currencyCode).getName();
			lbUnitMoney.setText(name);
			lableUnitMoney1.setText(name);
		} catch (Exception e) {
			lbUnitMoney.setText("VND");
			lableUnitMoney1.setText("VND");
		}
	}

	public void setRounding(double rounding) {
		this.rounding = rounding;
	}

	public JTextField getTxtMoneyMissing() {
		return txtMoneyMissing;
	}

	private boolean viewInvoiceCode = true;

	public boolean isViewInvoiceCode() {
		return viewInvoiceCode;
	}

	public void setViewInvoiceCode(boolean viewInvoiceCode) {
		this.viewInvoiceCode = viewInvoiceCode;
	}

	public PanelPayment() {
		init();
	}

	public PanelPayment(boolean isView) {
		viewInvoiceCode = isView;
		init();
	}

	private void init() {
		profile = AccountModelManager.getInstance().getProfileConfigAdmin();
		panelButton = new PanelButton();
		initComponents();
		scrPomostion.setVisible(false);
		scrPomostion2.setVisible(false);
		setOpaque(false);
		scrPomostion1.setViewportView(table_Sales1);

		tableModelDiscountItem = tableModel.getTableModelDiscountItem();
		tableDiscountItem.setModel(tableModelDiscountItem);
		// tableDiscountItem.setTableHeader(null);
		tableDiscountItem.setRowHeight(25);
		tableDiscountItem.setShowGrid(false);
		tableDiscountItem.getColumnModel().getColumn(0).setMaxWidth(30);
		tableDiscountItem.getColumnModel().getColumn(4).setMaxWidth(50);
		tableDiscountItem.getColumnModel().getColumn(6).setMaxWidth(100);
		tableDiscountItem.getColumnModel().getColumn(2).setMaxWidth(100);
		tableDiscountItem.getTableHeader().setOpaque(true);
		setBackground(Color.white);
		for (int i = 0; i < tableDiscountItem.getColumnCount(); i++) {
			tableDiscountItem.getColumnModel().getColumn(i).setHeaderRenderer(new TableHeaderRerender());

		}
		for (int i = 0; i < tableDiscountItem.getColumnCount(); i++) {
			tableDiscountItem.getColumnModel().getColumn(i).setCellRenderer(new TableRerenderSale(false));
		}

		cboTax.addMouseListener(new MouseAdapter() {
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
						loadCboTax();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		try {
			txtProject.setData(RestaurantModelManager.getInstance().getAllProject());
		} catch (Exception e) {
		}
		txtCoupon.addObserver(this);
		txtCoupon.setClearText(true);
		reset();
		chbForRent.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (chbForRent.isSelected()) {
					loadProductForRent();
				}

			}
		});
		// txtInvoiceCode.setEnabled(false);
		txtInvoiceCode.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// if (e.getClickCount() >= 2) {
				String code = AccountingModelManager.getInstance().getCodeObject(PanelManageCodes.InvoiceBH,
				    AccountingModelManager.typeBanHang, new Date(), false);
				if (tableModel.getInvoiceDetail().getEndDate() == null && code == null) {
					txtInvoiceCode.setEnabled(true);
					txtInvoiceCode.selectAll();
				} else {
					txtInvoiceCode.setEnabled(false);
				}

				// }
			}

		});
		txtInvoiceCode.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				// txtInvoiceCode.setEnabled(false);
				tableModel.getInvoiceDetail().setInvoiceCode(txtInvoiceCode.getText());
				try {
					AccountingModelManager.getInstance().saveInvoice(tableModel.getInvoiceDetail());
				} catch (Exception e2) {
				}

			}

			@Override
			public void focusGained(FocusEvent e) {

			}
		});
	}

	public JScrollPane getScrPomostion() {
		return scrPomostion;
	}

	public InvoiceItem getSelectedInvoiceItem() {
		return (InvoiceItem) table_Sales1.getValueAt(table_Sales1.getSelectedRow(), 1);
	}

	public void deleteItem() {
		tableModel.removeRow(table_Sales1.getSelectedRow());
	}

	public void setTable_Sales1(TableSales table_Sales1) {
		this.table_Sales1 = table_Sales1;
	}

	public TableModelInvoiceItem getTableModel() {
		return tableModel;
	}

	private void initComponents() {
		try {
			tableModel = new TableModelInvoiceItem(new InvoiceDetail(true));
			table_Sales1 = new TableSales(tableModel);
			table_Sales1.setName("tbSale");
			tableModel.addObserver(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		chbForRent = new JCheckBox("Nhận lại hàng");
		chbPayment = new JCheckBox("Bán hàng");
		chbPayment.setOpaque(false);
		lbForRent = new JLabel("Ngày hẹn trả: ");
		dateForRent = new MyJDateChooser();
		panelTabInfoMore = new JPanel();
		panelTabWaiter = new JPanel();
		cboDepartment = new DepartmentJComboBox();
		txtInvoiceCode = new JTextField();
		txtProject = new TextPopup<Project>(Project.class);
		cboStore = new StoreJComboBox();
		cboStore.setFont(new java.awt.Font("Tahoma", 0, 12));
		txtNote = new JTextArea();
		txtNote.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jPanel9 = new JPanel();
		panelSumMoney1 = new JPanel();
		lableUnitMoney1 = new JLabel();
		txtTotalMoneyBeforDiscount1 = new JTextField();
		jLabel7 = new JLabel();
		jPanel6 = new JPanel();
		scrPomostion1 = new JScrollPane();
		scrPomostion2 = new JScrollPane();
		scrPomostion = new JScrollPane();
		tableDiscountItem = new JTable();
		lbTax = new JLabel();
		lbMoneyTax = new JLabel();
		txtTotalMoneyTax = new JTextField();
		jLabel14 = new JLabel();
		txtTotalMoneyAfterTax = new JTextField();
		lbDaTra = new JLabel();
		txtMoneyPerformed = new JTextField();
		lbPhaiTra = new JLabel();
		txtMoneyMissing = new JTextField();
		rbtnDiscountPercent = new JRadioButton();
		txtPercenDiscount = new JTextField();
		txtPercenService = new JTextField();
		txtMoneyService = new JTextField();
		rbtnDiscountMoney = new JRadioButton();
		txtMoneyDiscount = new JTextField();
		lblMoneySpell = new JLabel();
		lbUnitMoney = new JLabel();
		rbtnDiscountService = new JRadioButton();
		rbtnServiceMoney = new JRadioButton();
		cboTax = new JComboBox();
		txtTotalMark = new JTextField();
		lbMark = new JLabel();
		txtMoneyMark = new JTextField();
		chbPoint = new JCheckBox();
		chbCredit = new JCheckBox();
		txtTotaltCredit = new JTextField();
		lbMark1 = new JLabel();
		txtMoneyCredit = new JTextField();
		scrollPanePay = new JScrollPane();
		s1 = new JScrollPane();
		scrollWaiter = new JScrollPane();
		scroll = new JScrollPane();

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rbtnDiscountMoney);
		buttonGroup.add(rbtnDiscountPercent);

		ButtonGroup buttonGroup2 = new ButtonGroup();
		buttonGroup2.add(rbtnDiscountService);
		buttonGroup2.add(rbtnServiceMoney);
		rbtnDiscountService.setSelected(true);
		jPanel9.setOpaque(false);
		tabbedPane = new JTabbedPane();
		panelSumMoney1.setBackground(new java.awt.Color(0, 0, 0));

		lableUnitMoney1.setFont(new java.awt.Font("Tahoma", 0, 24));
		lableUnitMoney1.setForeground(new java.awt.Color(255, 255, 255));
		lableUnitMoney1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lableUnitMoney1.setText(currencyCode); // NOI18N
		lableUnitMoney1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

		txtTotalMoneyBeforDiscount1.setFont(new java.awt.Font("Tahoma", 0, 24));
		txtTotalMoneyBeforDiscount1.setForeground(new java.awt.Color(255, 255, 255));
		txtTotalMoneyBeforDiscount1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtTotalMoneyBeforDiscount1.setText(""); // NOI18N
		txtTotalMoneyBeforDiscount1.setBorder(null);
		txtTotalMoneyBeforDiscount1.setCaretColor(new java.awt.Color(255, 255, 255));
		txtTotalMoneyBeforDiscount1.setDisabledTextColor(new java.awt.Color(255, 255, 255));
		txtTotalMoneyBeforDiscount1.setOpaque(false);

		jLabel7.setFont(new java.awt.Font("Tahoma", 0, 24));
		jLabel7.setForeground(new java.awt.Color(255, 255, 255));
		jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel7.setText("Tổng Tiền"); // NOI18N
		jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

		javax.swing.GroupLayout panelSumMoney1Layout = new javax.swing.GroupLayout(panelSumMoney1);
		panelSumMoney1.setLayout(panelSumMoney1Layout);
		panelSumMoney1Layout.setHorizontalGroup(panelSumMoney1Layout.createParallelGroup(
		    javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		    panelSumMoney1Layout
		        .createSequentialGroup()
		        .addComponent(jLabel7)
		        .addGap(10, 10, 10)
		        .addComponent(txtTotalMoneyBeforDiscount1, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
		        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		        .addComponent(lableUnitMoney1, javax.swing.GroupLayout.PREFERRED_SIZE, 81,
		            javax.swing.GroupLayout.PREFERRED_SIZE)));
		panelSumMoney1Layout.setVerticalGroup(panelSumMoney1Layout
		    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
		    .addComponent(lableUnitMoney1, javax.swing.GroupLayout.PREFERRED_SIZE, 41,
		        javax.swing.GroupLayout.PREFERRED_SIZE)
		    .addComponent(txtTotalMoneyBeforDiscount1, javax.swing.GroupLayout.PREFERRED_SIZE, 41,
		        javax.swing.GroupLayout.PREFERRED_SIZE));

		jPanel6.setOpaque(false);
		jPanel6.setPreferredSize(new java.awt.Dimension(320, 124));

		scrPomostion1.setPreferredSize(new java.awt.Dimension(445, 402));
		scrPomostion1.setBackground(Color.white);
		scrPomostion2.setViewportView(tableDiscountItem);

		javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
		jPanel6.setLayout(jPanel6Layout);
		jPanel6Layout.setHorizontalGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addComponent(scrPomostion1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
		    .addComponent(scrPomostion, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
		    .addComponent(scrPomostion2, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE));
		jPanel6Layout.setVerticalGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        jPanel6Layout.createSequentialGroup()
		            .addComponent(scrPomostion1, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		            .addComponent(scrPomostion, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
		            .addComponent(scrPomostion2, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)));

		JLabel labelT1 = new JLabel("Phòng ban");

		JLabel labelT11 = new JLabel("Cửa hàng");

		JLabel labelT2 = new JLabel("Dự án");

		JLabel labelT3 = new JLabel("Mã hóa đơn");

		MyGroupLayout layout = new MyGroupLayout(panelTabInfoMore);
		layout.add(0, 0, labelT1);
		layout.add(0, 1, cboDepartment);
		if (viewInvoiceCode) {
			layout.add(0, 2, labelT3);
			layout.add(0, 3, txtInvoiceCode);
		}

		layout.add(1, 0, labelT2);
		layout.add(1, 1, txtProject);
		layout.add(1, 2, labelT11);
		layout.add(1, 3, cboStore);
		txtCoupon = new TextPopup<Coupon>(Coupon.class);
		try {
			txtCoupon.setData(PromotionModelManager.getInstance().getCoupons());
		} catch (Exception e) {
		}

		JScrollPane sPane = new JScrollPane();
		try {
			List<CouponUsed> couponUseds = new ArrayList<CouponUsed>();
			tableModelCouponInvoice = new TableModelCouponInvoice(couponUseds);
			JTable table = new JTable(tableModelCouponInvoice);
			table.setTableHeader(null);
			sPane.setViewportView(table);

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		layout.add(2, 0, new JLabel("Phiếu giảm giá"));
		layout.add(2, 1, txtCoupon);
		layout.add(3, 0, new JLabel());
		layout.add(3, 1, sPane);
		layout.updateGui();

		try {
			cboStore.setConfig();
			cboDepartment.setConfig();
			// cboProject.setConfig();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		cboDepartment.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (cboDepartment.getSelectedDepartment() != null) {
					tableModel.getInvoiceDetail().setDepartmentCode(cboDepartment.getSelectedDepartment().getPath());
				} else {
					tableModel.getInvoiceDetail().setDepartmentCode(HRModelManager.getInstance().getDepartmentOther().getPath());
				}
				tableModel.loadPromotionProduct();
			}
		});

		cboStore.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (cboStore.getSelectedStore() != null) {
					tableModel.getInvoiceDetail().setStoreCode(cboStore.getSelectedStore().getCode());
				} else {
					tableModel.getInvoiceDetail().setStoreCode(null);
				}
				tableModel.loadPromotionProduct();
			}
		});

		panelWaiters = new PanelWaiters();

		lbTax.setFont(new java.awt.Font("Tahoma", 0, 13));
		lbTax.setText("Thuế"); // NOI18N

		lbMoneyTax.setFont(new java.awt.Font("Tahoma", 0, 13));
		lbMoneyTax.setText("Tiền thuế"); // NOI18N

		txtTotalMoneyTax.setFont(new java.awt.Font("Tahoma", 0, 13));
		txtTotalMoneyTax.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtTotalMoneyTax.setText(""); // NOI18N
		txtTotalMoneyTax.setDisabledTextColor(new java.awt.Color(0, 0, 0));
		txtTotalMoneyTax.setEnabled(false);
		txtTotalMoneyTax.setOpaque(false);

		jLabel14.setFont(new java.awt.Font("Tahoma", 0, 13));
		jLabel14.setText("Tổng cộng"); // NOI18N

		txtTotalMoneyAfterTax.setFont(new java.awt.Font("Tahoma", 0, 13));
		txtTotalMoneyAfterTax.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtTotalMoneyAfterTax.setText(""); // NOI18N
		txtTotalMoneyAfterTax.setDisabledTextColor(new java.awt.Color(0, 0, 0));
		txtTotalMoneyAfterTax.setEnabled(false);
		txtTotalMoneyAfterTax.setOpaque(false);

		lbDaTra.setFont(new java.awt.Font("Tahoma", 0, 13));
		lbDaTra.setText("Đã trả"); // NOI18N

		txtMoneyPerformed.setFont(new java.awt.Font("Tahoma", 0, 13));
		txtMoneyPerformed.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtMoneyPerformed.setText(""); // NOI18N
		txtMoneyPerformed.setDisabledTextColor(new java.awt.Color(0, 0, 0));
		txtMoneyPerformed.setEnabled(false);
		txtMoneyPerformed.setOpaque(false);

		lbPhaiTra.setFont(new java.awt.Font("Tahoma", 1, 13));
		lbPhaiTra.setForeground(new java.awt.Color(124, 0, 0));
		lbPhaiTra.setText("Phải trả"); // NOI18N

		txtMoneyMissing.setFont(new java.awt.Font("Tahoma", 1, 13));
		txtMoneyMissing.setForeground(new java.awt.Color(124, 0, 0));
		txtMoneyMissing.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtMoneyMissing.setText(""); // NOI18N
		txtMoneyMissing.setDisabledTextColor(new java.awt.Color(0, 0, 0));
		txtMoneyMissing.setEnabled(false);
		txtMoneyMissing.setOpaque(false);

		rbtnDiscountPercent.setFont(new java.awt.Font("Tahoma", 0, 13));
		rbtnDiscountPercent.setSelected(true);
		rbtnDiscountPercent.setText("% CK"); // NOI18N
		rbtnDiscountPercent.setOpaque(false);

		txtPercenDiscount.setFont(new java.awt.Font("Tahoma", 0, 13));
		txtPercenDiscount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtPercenDiscount.setText(""); // NOI18N
		txtPercenDiscount.setDisabledTextColor(new java.awt.Color(0, 0, 0));
		txtPercenDiscount.setOpaque(false);

		txtPercenDiscount.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_0 || e.getKeyCode() == KeyEvent.VK_1 || e.getKeyCode() == KeyEvent.VK_2
				    || e.getKeyCode() == KeyEvent.VK_3 || e.getKeyCode() == KeyEvent.VK_4 || e.getKeyCode() == KeyEvent.VK_5
				    || e.getKeyCode() == KeyEvent.VK_6 || e.getKeyCode() == KeyEvent.VK_7 || e.getKeyCode() == KeyEvent.VK_8
				    || e.getKeyCode() == KeyEvent.VK_9 || e.getKeyCode() == KeyEvent.VK_BACK_SPACE
				    || e.getKeyCode() == KeyEvent.VK_NUMPAD0 || e.getKeyCode() == KeyEvent.VK_NUMPAD1
				    || e.getKeyCode() == KeyEvent.VK_NUMPAD2 || e.getKeyCode() == KeyEvent.VK_NUMPAD3
				    || e.getKeyCode() == KeyEvent.VK_NUMPAD4 || e.getKeyCode() == KeyEvent.VK_NUMPAD5
				    || e.getKeyCode() == KeyEvent.VK_NUMPAD6 || e.getKeyCode() == KeyEvent.VK_NUMPAD7
				    || e.getKeyCode() == KeyEvent.VK_NUMPAD8 || e.getKeyCode() == KeyEvent.VK_NUMPAD9
				    || e.getKeyCode() == KeyEvent.VK_DELETE) {
					txtPercenDiscountCaretUpdate();
				}
			}

		});

		txtPercenService.setFont(new java.awt.Font("Tahoma", 0, 13));
		txtPercenService.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtPercenService.setText(""); // NOI18N
		txtPercenService.setDisabledTextColor(new java.awt.Color(0, 0, 0));
		txtPercenService.setOpaque(false);

		txtPercenService.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_0 || e.getKeyCode() == KeyEvent.VK_1 || e.getKeyCode() == KeyEvent.VK_2
				    || e.getKeyCode() == KeyEvent.VK_3 || e.getKeyCode() == KeyEvent.VK_4 || e.getKeyCode() == KeyEvent.VK_5
				    || e.getKeyCode() == KeyEvent.VK_6 || e.getKeyCode() == KeyEvent.VK_7 || e.getKeyCode() == KeyEvent.VK_8
				    || e.getKeyCode() == KeyEvent.VK_9 || e.getKeyCode() == KeyEvent.VK_BACK_SPACE
				    || e.getKeyCode() == KeyEvent.VK_NUMPAD0 || e.getKeyCode() == KeyEvent.VK_NUMPAD1
				    || e.getKeyCode() == KeyEvent.VK_NUMPAD2 || e.getKeyCode() == KeyEvent.VK_NUMPAD3
				    || e.getKeyCode() == KeyEvent.VK_NUMPAD4 || e.getKeyCode() == KeyEvent.VK_NUMPAD5
				    || e.getKeyCode() == KeyEvent.VK_NUMPAD6 || e.getKeyCode() == KeyEvent.VK_NUMPAD7
				    || e.getKeyCode() == KeyEvent.VK_NUMPAD8 || e.getKeyCode() == KeyEvent.VK_NUMPAD9
				    || e.getKeyCode() == KeyEvent.VK_DELETE) {
					txtPercenServiceCaretUpdate();
				}
			}

		});

		rbtnDiscountMoney.setFont(new java.awt.Font("Tahoma", 0, 13));
		rbtnDiscountMoney.setText("Tiền CK"); // NOI18N
		rbtnDiscountMoney.setOpaque(false);
		rbtnDiscountMoney.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				rbtnDiscountMoneyItemStateChanged(evt);
			}
		});

		txtMoneyDiscount.setFont(new java.awt.Font("Tahoma", 0, 13));
		txtMoneyDiscount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtMoneyDiscount.setText(""); // NOI18N
		txtMoneyDiscount.setDisabledTextColor(new java.awt.Color(0, 0, 0));
		txtMoneyDiscount.setOpaque(false);

		txtMoneyDiscount.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_0 || e.getKeyCode() == KeyEvent.VK_1 || e.getKeyCode() == KeyEvent.VK_2
				    || e.getKeyCode() == KeyEvent.VK_3 || e.getKeyCode() == KeyEvent.VK_4 || e.getKeyCode() == KeyEvent.VK_5
				    || e.getKeyCode() == KeyEvent.VK_6 || e.getKeyCode() == KeyEvent.VK_7 || e.getKeyCode() == KeyEvent.VK_8
				    || e.getKeyCode() == KeyEvent.VK_9 || e.getKeyCode() == KeyEvent.VK_BACK_SPACE
				    || e.getKeyCode() == KeyEvent.VK_NUMPAD0 || e.getKeyCode() == KeyEvent.VK_NUMPAD1
				    || e.getKeyCode() == KeyEvent.VK_NUMPAD2 || e.getKeyCode() == KeyEvent.VK_NUMPAD3
				    || e.getKeyCode() == KeyEvent.VK_NUMPAD4 || e.getKeyCode() == KeyEvent.VK_NUMPAD5
				    || e.getKeyCode() == KeyEvent.VK_NUMPAD6 || e.getKeyCode() == KeyEvent.VK_NUMPAD7
				    || e.getKeyCode() == KeyEvent.VK_NUMPAD8 || e.getKeyCode() == KeyEvent.VK_NUMPAD9
				    || e.getKeyCode() == KeyEvent.VK_DELETE) {
					txtMoneyDiscountCaretUpdate();
				}
			}

		});

		txtMoneyService.setFont(new java.awt.Font("Tahoma", 0, 13));
		txtMoneyService.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtMoneyService.setText(""); // NOI18N
		txtMoneyService.setDisabledTextColor(new java.awt.Color(0, 0, 0));
		txtMoneyService.setOpaque(false);

		txtMoneyService.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_0 || e.getKeyCode() == KeyEvent.VK_1 || e.getKeyCode() == KeyEvent.VK_2
				    || e.getKeyCode() == KeyEvent.VK_3 || e.getKeyCode() == KeyEvent.VK_4 || e.getKeyCode() == KeyEvent.VK_5
				    || e.getKeyCode() == KeyEvent.VK_6 || e.getKeyCode() == KeyEvent.VK_7 || e.getKeyCode() == KeyEvent.VK_8
				    || e.getKeyCode() == KeyEvent.VK_9 || e.getKeyCode() == KeyEvent.VK_BACK_SPACE
				    || e.getKeyCode() == KeyEvent.VK_NUMPAD0 || e.getKeyCode() == KeyEvent.VK_NUMPAD1
				    || e.getKeyCode() == KeyEvent.VK_NUMPAD2 || e.getKeyCode() == KeyEvent.VK_NUMPAD3
				    || e.getKeyCode() == KeyEvent.VK_NUMPAD4 || e.getKeyCode() == KeyEvent.VK_NUMPAD5
				    || e.getKeyCode() == KeyEvent.VK_NUMPAD6 || e.getKeyCode() == KeyEvent.VK_NUMPAD7
				    || e.getKeyCode() == KeyEvent.VK_NUMPAD8 || e.getKeyCode() == KeyEvent.VK_NUMPAD9
				    || e.getKeyCode() == KeyEvent.VK_DELETE) {
					txtMoneyServiceCaretUpdate();
				}
			}

		});

		lblMoneySpell.setFont(new java.awt.Font("Tahoma", 2, 12));
		lblMoneySpell.setForeground(new java.awt.Color(124, 0, 0));
		lblMoneySpell.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblMoneySpell.setText("Tiền bằng chữ"); // NOI18N

		lbUnitMoney.setFont(new java.awt.Font("Tahoma", 1, 13));
		lbUnitMoney.setText(currencyCode); // NOI18N

		rbtnDiscountService.setFont(new java.awt.Font("Tahoma", 0, 13));
		if (profile.get("ForRent") == null) {
			rbtnDiscountService.setText("% DV"); // NOI18N
		} else {
			rbtnDiscountService.setText("% Ship"); // NOI18N
		}
		rbtnDiscountService.setOpaque(false);

		rbtnServiceMoney.setFont(new java.awt.Font("Tahoma", 0, 13));
		if (profile.get("ForRent") == null) {
			rbtnServiceMoney.setText("Dịch vụ"); // NOI18N
		} else {
			rbtnServiceMoney.setText("Ship"); // NOI18N
		}

		rbtnServiceMoney.setOpaque(false);
		rbtnServiceMoney.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				rbtnServiceMoneyItemStateChanged(evt);
			}
		});

		cboTax.setFont(new java.awt.Font("Tahoma", 0, 14));
		cboTax.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
		cboTax.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				cboTaxItemStateChanged(evt);
			}
		});

		txtTotalMark.setFont(new java.awt.Font("Tahoma", 0, 13));
		txtTotalMark.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtTotalMark.setText(""); // NOI18N
		txtTotalMark.setDisabledTextColor(new java.awt.Color(0, 0, 0));
		txtTotalMark.setOpaque(false);
		txtTotalMark.addCaretListener(new javax.swing.event.CaretListener() {
			public void caretUpdate(javax.swing.event.CaretEvent evt) {
				try {
					txtTotalMarkCaretUpdate(evt);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		lbMark.setFont(new java.awt.Font("Tahoma", 0, 13));
		lbMark.setText("Thành tiền"); // NOI18N

		txtMoneyMark.setFont(new java.awt.Font("Tahoma", 0, 13));
		txtMoneyMark.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtMoneyMark.setText(""); // NOI18N
		txtMoneyMark.setDisabledTextColor(new java.awt.Color(0, 0, 0));
		txtMoneyMark.setEnabled(false);
		txtMoneyMark.setOpaque(false);

		chbPoint.setFont(new java.awt.Font("Tahoma", 0, 12));
		chbPoint.setText("Điểm"); // NOI18N
		chbPoint.setName("chbPoint");
		chbPoint.setOpaque(false);
		chbPoint.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				chbPointItemStateChanged(evt);
			}
		});

		chbCredit.setFont(new java.awt.Font("Tahoma", 0, 12));
		chbCredit.setText("Tiền TT"); // NOI18N
		chbCredit.setName("chbCredit");
		chbCredit.setOpaque(false);
		chbCredit.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				try {
					chbCreditItemStateChanged(evt);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		txtTotaltCredit.setFont(new java.awt.Font("Tahoma", 0, 13));
		txtTotaltCredit.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtTotaltCredit.setText(""); // NOI18N
		txtTotaltCredit.setDisabledTextColor(new java.awt.Color(0, 0, 0));
		txtTotaltCredit.setEnabled(false);
		txtTotaltCredit.setOpaque(false);

		lbMark1.setFont(new java.awt.Font("Tahoma", 0, 13));
		lbMark1.setText("Tiền dùng"); // NOI18N

		txtMoneyCredit.setFont(new java.awt.Font("Tahoma", 0, 13));
		txtMoneyCredit.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtMoneyCredit.setText(""); // NOI18N
		txtMoneyCredit.setDisabledTextColor(new java.awt.Color(0, 0, 0));
		txtMoneyCredit.setEnabled(false);
		txtMoneyCredit.setOpaque(false);
		txtMoneyCredit.addCaretListener(new javax.swing.event.CaretListener() {
			public void caretUpdate(javax.swing.event.CaretEvent evt) {
				txtMoneyCreditCaretUpdate(evt);
			}
		});

		refeshGui();
	}

	protected void rbtnServiceMoneyItemStateChanged(ItemEvent evt) {
		if (rbtnServiceMoney.isSelected()) {
			// txtPercenDiscount.setText("0");
			txtPercenService.setEnabled(false);
			txtMoneyService.setEnabled(true);
		} else {
			// txtMoneyDiscount.setText("0");
			txtPercenService.setEnabled(true);
			txtMoneyService.setEnabled(false);
		}

	}

	protected void txtMoneyServiceCaretUpdate() {
		try {
			double rate = MyDouble.parseDouble(txtMoneyService.getText())
			    / MyDouble.parseDouble(txtTotalMoneyBeforDiscount1.getText()) * 100;
			tableModel.getInvoiceDetail().setServiceRate(rate);
			refeshCaculate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void txtPercenServiceCaretUpdate() {
		try {
			tableModel.getInvoiceDetail().setServiceRate(MyDouble.parseDouble(txtPercenService.getText()));
			refeshCaculate();
		} catch (Exception e) {

		}

	}

	protected void txtMoneyCreditCaretUpdate(CaretEvent evt) {
		if (chbCredit.isSelected()) {
			double a = MyDouble.parseDouble(txtMoneyCredit.getText());
			tableModel.getInvoiceDetail().setCredit(a);
			refeshCaculate();
		} else {
			chbCredit.setEnabled(true);
			txtMoneyCredit.setEnabled(true);
		}

	}

	protected void chbCreditItemStateChanged(ItemEvent evt) throws Exception {
		if (chbCredit.isSelected() && tableModel.getInvoiceDetail().getCustomerCode() != null) {
			CreditTransaction pointTransaction = CustomerModelManager.getInstance().getCreditTransactionByInvoiceId(
			    tableModel.getInvoiceDetail().getId());
			if (pointTransaction == null) {
				Customer c = CustomerModelManager.getInstance().getCustomerByCode(
				    tableModel.getInvoiceDetail().getCustomerCode());
				credit = CustomerModelManager.getInstance().getCreditByCustomerId(c.getId());
				double money = tableModel.getInvoiceDetail().getCredit();
				if (money == 0) {
					money = MyDouble.parseDouble(txtMoneyMissing.getText());
				}
				if (money <= credit.getCredit()) {
					txtMoneyCredit.setText(MyDouble.valueOf(money).toString());
				} else {
					txtMoneyCredit.setText(MyDouble.valueOf(credit.getCredit()).toString());
				}
			} else {
				chbCredit.setEnabled(false);
				txtMoneyCredit.setEnabled(false);
				txtMoneyCredit.setText(MyDouble.valueOf(pointTransaction.getAmount() * (-1)).toString());
			}

		} else {
			txtMoneyCredit.setText("0");
			tableModel.getInvoiceDetail().setCredit(0);
			refeshCaculate();
		}

	}

	protected void txtTotalMarkCaretUpdate(CaretEvent evt) throws Exception {
		if (chbPoint.isSelected()) {
			double b = MyDouble.parseDouble(txtTotalMark.getText());
			PointTransaction pointTransaction = CustomerModelManager.getInstance().getByInvoiceId(
			    tableModel.getInvoiceDetail().getId());
			MyDouble b1 = new MyDouble(b);
			b1.setNumDot(0);
			MyDouble b2 = new MyDouble(b1.toString());
			if (pointTransaction == null) {
				MyDouble a1 = new MyDouble(point.getPoint());
				a1.setNumDot(0);
				MyDouble a2 = new MyDouble(a1.toString());
				if (b2.doubleValue() <= a2.doubleValue()) {
					double a = b2.doubleValue()
					    * CustomerModelManager.getInstance().getConversionRulePointToMoney(point.getPoint())
					        .numRatioPointToCredit();
					txtMoneyMark.setText(MyDouble.valueOf(a).toString());
					tableModel.getInvoiceDetail().setPoint(a);
					refeshCaculate();
					chbPoint.setForeground(Color.black);
				} else {
					chbPoint.setForeground(Color.red);
				}
			} else {
				double a = b2.doubleValue()
				    * CustomerModelManager.getInstance().getConversionRuleById(pointTransaction.getPointConversionRuleId())
				        .numRatioPointToCredit();
				txtMoneyMark.setText(MyDouble.valueOf(a).toString());
				tableModel.getInvoiceDetail().setPoint(a);
				refeshCaculate();
				chbPoint.setForeground(Color.black);
			}

		}
	}

	protected void chbPointItemStateChanged(ItemEvent evt) {
		if (chbPoint.isSelected()) {
			refeshPoint();
		} else {
			point = null;
			chbPoint.setEnabled(true);
			txtTotalMark.setEnabled(true);
			txtTotalMark.setText("0");
			txtMoneyMark.setText("0");
			tableModel.getInvoiceDetail().setPoint(0);
			refeshCaculate();
		}
	}

	public DepartmentJComboBox getDepartmentJComboBox() {
		return cboDepartment;
	}

	public StoreJComboBox getStoreJComboBox() {
		return cboStore;
	}

	public JCheckBox getChbPayment() {
		return chbPayment;
	}

	public void refeshGui() {
		Profile profile = AccountModelManager.getInstance().getProfileConfigAdmin();
		if (isSuperMarket) {
			if (panelC == null) {
				panelC = new JPanel();
			}
			panelC.setOpaque(false);
			panelC.setLayout(new GridLayout(1, 2, 5, 5));
			javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
			jPanel9.setLayout(jPanel9Layout);
			jPanel9Layout.setHorizontalGroup(jPanel9Layout
			    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			    .addComponent(panelSumMoney1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
			        Short.MAX_VALUE).addComponent(panelC, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
			    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE));
			jPanel9Layout.setVerticalGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			    .addGroup(
			        jPanel9Layout
			            .createSequentialGroup()
			            .addComponent(panelSumMoney1, javax.swing.GroupLayout.PREFERRED_SIZE,
			                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
			            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
			            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			            .addComponent(panelC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
			                javax.swing.GroupLayout.PREFERRED_SIZE)));
			panelC.add(tabbedPane);
			panelC.add(panelButton);
			panelButton.resetGui();
		} else {
			if (panel == null) {
				panel = new JPanel();
			}
			panel.setOpaque(false);
			if (profile.get("ForRent") != null) {
				panel.setLayout(new BorderLayout(2, 2));
				JPanel panel2 = new JPanel(new GridLayout(1, 2));
				panel2.setOpaque(false);
				JPanel panel5 = new JPanel(new GridLayout(1, 2));
				panel5.setOpaque(false);
				panel5.add(chbForRent);
				panel5.add(chbPayment);

				chbForRent.setOpaque(false);
				panel2.add(panel5);
				JPanel panel3 = new JPanel();
				panel3.setOpaque(false);
				panel3.setLayout(new BorderLayout());
				panel3.add(lbForRent, BorderLayout.WEST);
				panel3.add(dateForRent, BorderLayout.CENTER);
				panel2.add(panel3);
				panel.add(panel2, BorderLayout.NORTH);
				panel.add(jPanel6, BorderLayout.CENTER);
			} else {
				panel.setLayout(new GridLayout());
				panel.add(jPanel6);
			}
			javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
			jPanel9.setLayout(jPanel9Layout);
			jPanel9Layout.setHorizontalGroup(jPanel9Layout
			    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			    .addComponent(panelSumMoney1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
			        Short.MAX_VALUE).addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
			    .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE));
			jPanel9Layout.setVerticalGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			    .addGroup(
			        jPanel9Layout
			            .createSequentialGroup()
			            .addComponent(panelSumMoney1, javax.swing.GroupLayout.PREFERRED_SIZE,
			                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
			            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
			            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			            .addComponent(tabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE,
			                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
		}

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
		    jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel9,
		    javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		tabbedPane.add(scroll, "Thanh toán");
		TbModelPayment = new TableModelPayment(new ArrayList<InvoiceTransaction>());
		JTable table = new JTable(TbModelPayment);

		scrollPanePay.setViewportView(table);
		scrollPanePay.setSize(50, 50);
		scrollPanePay.setPreferredSize(new Dimension(50, 50));
		s1.setSize(50, 50);
		s1.setPreferredSize(new Dimension(50, 50));
		tabbedPane.add(scrollPanePay, "LS Thanh toán");
		s1.setViewportView(panelTabInfoMore);
		tabbedPane.add(s1, "Mở rộng");
		tabbedPane.add(scrollWaiter, "TV Phục vụ");
		tabbedPane.add(txtNote, "Ghi chú");

		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
				if (tabbedPane.getSelectedIndex() == 4) {
					if (PanelPayment.this.table != null && PanelPayment.this.table.getStatus().equals(Table.Status.TableSet)) {
						txtNote.setText(PanelPayment.this.table.getDescription());
					} else {
						txtNote.setText(tableModel.getInvoiceDetail().getDescription());
					}
				} else {
					TbModelPayment.setData(tableModel.getInvoiceDetail());
					tableModel.getInvoiceDetail().setDescription(txtNote.getText());
				}
			}
		});
		JPanel panelMain = new JPanel();
		panelMain.setBackground(Color.white);

		int k = 8;
		try {
			if (profile.get(DialogConfig.DoiGia).toString().equals("true")) {
				gia = true;
			} else {
				gia = false;
			}
			if (profile.get(DialogConfig.SoLuong).toString().equals("true")) {
				sl = true;
			} else {
				sl = false;
			}
		} catch (Exception e) {
			profile = null;
		}
		if (profile != null) {
			if (profile.get(PanelRounding.TronLen) != null) {
				rounding = MyDouble.valueOf(profile.get(PanelRounding.TronLen).toString()).doubleValue();
			} else {
				if (profile.get(PanelRounding.TronPhay) != null) {
					rounding = MyDouble.valueOf(profile.get(PanelRounding.TronPhay).toString()).doubleValue() * -1;
				}
			}
			if (profile.get(DialogConfig.Thue) != null && profile.get(DialogConfig.Thue).toString().equals("true")) {
				JPanel panel = createChildPanel(lbTax, cboTax, lbMoneyTax, txtTotalMoneyTax);
				panelMain.add(panel);
			} else {
				k--;
			}
		} else {
			JPanel panel = createChildPanel(lbTax, cboTax, lbMoneyTax, txtTotalMoneyTax);
			panelMain.add(panel);
		}

		if (profile != null) {
			if (profile.get(DialogConfig.CK) != null && profile.get(DialogConfig.CK).toString().equals("true")) {
				if(profile.get("CKTIEN")==null){
					JPanel panel1 = createChildPanel(rbtnDiscountPercent, txtPercenDiscount, rbtnDiscountMoney, txtMoneyDiscount);
					panelMain.add(panel1);
				}else{
					if(profile.get("CKTIEN").toString().equals("true")&&profile.get("CKPHANTRAM").toString().equals("true")){
						JPanel panel1 = createChildPanel(rbtnDiscountPercent, txtPercenDiscount, rbtnDiscountMoney, txtMoneyDiscount);
						panelMain.add(panel1);
					}else{
						if(profile.get("CKPHANTRAM").toString().equals("true")){
							JPanel panel = new JPanel();
							panel.setOpaque(false);
							panel.setLayout(new BorderLayout());
							JLabel label = new JLabel(profile.get("NAMECKPHANTRAM").toString());
							label.setFont(new java.awt.Font("Tahoma", 0, 13));
							label.setSize(new Dimension(70, 23));
							label.setPreferredSize(new Dimension(70, 23));
							panel.add(label, BorderLayout.WEST);
							rbtnDiscountPercent.setSelected(true);
							panel.add(txtPercenDiscount, BorderLayout.CENTER);
							panelMain.add(panel);
						}else{
							JPanel panel = new JPanel();
							panel.setOpaque(false);
							panel.setLayout(new BorderLayout());
							JLabel label = new JLabel(profile.get("NAMECKTIEN").toString());
							label.setFont(new java.awt.Font("Tahoma", 0, 13));
							label.setSize(new Dimension(70, 23));
							label.setPreferredSize(new Dimension(70, 23));
							panel.add(label, BorderLayout.WEST);
							rbtnDiscountMoney.setSelected(true);
							panel.add(txtMoneyDiscount, BorderLayout.CENTER);
							panelMain.add(panel);
						}
					}
				}
				
			} else {
				k--;
			}
		} else {
			JPanel panel1 = createChildPanel(rbtnDiscountPercent, txtPercenDiscount, rbtnDiscountMoney, txtMoneyDiscount);
			panelMain.add(panel1);
		}

		if (profile != null) {
			if (profile.get(DialogConfig.DichVu) != null && profile.get(DialogConfig.DichVu).toString().equals("true")) {
				JPanel panel1 = createChildPanel(rbtnDiscountService, txtPercenService, rbtnServiceMoney, txtMoneyService);
				panelMain.add(panel1);
			} else {
				k--;
			}
		} else {
			JPanel panel1 = createChildPanel(rbtnDiscountPercent, txtPercenDiscount, rbtnDiscountMoney, txtMoneyDiscount);
			panelMain.add(panel1);
		}

		if (profile != null) {
			if (profile.get(ScreenOften.ManagerPoint) != null
			    && profile.get(ScreenOften.ManagerPoint).toString().equals("true")) {
				// Điểm
				JPanel panel2 = createChildPanel(chbPoint, txtTotalMark, lbMark, txtMoneyMark);
				panelMain.add(panel2);
			} else {
				k--;
			}
		} else {
			// Điểm
			JPanel panel2 = createChildPanel(chbPoint, txtTotalMark, lbMark, txtMoneyMark);
			panelMain.add(panel2);
		}

		if (profile != null) {
			if (profile.get(ScreenOften.ManagerCredit) != null
			    && profile.get(ScreenOften.ManagerCredit).toString().equals("true")) {
				// Trả trước
				JPanel panel3 = new JPanel();
				panel3.setOpaque(false);
				panel3.setPreferredSize(new Dimension(WIDTH, 23));
				panel3.setLayout(new BorderLayout());
				chbCredit.setSize(new Dimension(70, 23));
				chbCredit.setPreferredSize(new Dimension(70, 23));
				panel3.add(chbCredit, BorderLayout.WEST);
				panel3.add(txtMoneyCredit, BorderLayout.CENTER);
				panelMain.add(panel3);
			} else {
				k--;
			}
		} else {
			// Trả trước
			JPanel panel3 = new JPanel();
			panel3.setOpaque(false);
			panel3.setPreferredSize(new Dimension(WIDTH, 23));
			panel3.setLayout(new BorderLayout());
			chbCredit.setSize(new Dimension(70, 23));
			chbCredit.setPreferredSize(new Dimension(70, 23));
			panel3.add(chbCredit, BorderLayout.WEST);
			panel3.add(txtMoneyCredit, BorderLayout.CENTER);
			panelMain.add(panel3);
		}

		JPanel panel5 = createChildPanel(jLabel14, txtTotalMoneyAfterTax, lbDaTra, txtMoneyPerformed);
		panelMain.add(panel5);

		JPanel panel6 = new JPanel();
		panel6.setOpaque(false);
		panel6.setPreferredSize(new Dimension(WIDTH, 23));
		panel6.setLayout(new BorderLayout());
		lbPhaiTra.setSize(new Dimension(70, 23));
		lbPhaiTra.setPreferredSize(new Dimension(70, 23));
		panel6.add(lbPhaiTra, BorderLayout.WEST);
		panel6.add(txtMoneyMissing, BorderLayout.CENTER);
		panel6.add(lbUnitMoney, BorderLayout.EAST);
		lbUnitMoney.setSize(new Dimension(50, 23));
		lbUnitMoney.setPreferredSize(new Dimension(50, 23));
		lbUnitMoney.setHorizontalAlignment(JLabel.CENTER);
		panelMain.add(panel6);

		panelMain.add(lblMoneySpell);

		panelMain.setLayout(new GridLayout(k, 1, 3, 3));
		scroll.setViewportView(panelMain);

		try {
			if (profile.get(DialogConfig.Keyboard) != null && profile.get(DialogConfig.Keyboard).toString().equals("true")) {
				if (txtPercenDiscount.getCaretListeners().length == 0) {
					txtPercenDiscount.addCaretListener(new javax.swing.event.CaretListener() {
						public void caretUpdate(javax.swing.event.CaretEvent evt) {
							if (evt.getMark() > 0) {
								// txtPercenDiscountCaretUpdate();
							}
						}
					});
					txtMoneyDiscount.addCaretListener(new javax.swing.event.CaretListener() {
						public void caretUpdate(javax.swing.event.CaretEvent evt) {
							if (evt.getMark() > 0) {
								// txtMoneyDiscountCaretUpdate();
							}
						}
					});
				}
			} else {
				try {
					txtPercenDiscount.removeCaretListener(txtPercenDiscount.getCaretListeners()[0]);
					txtMoneyDiscount.removeCaretListener(txtMoneyDiscount.getCaretListeners()[0]);
				} catch (Exception e) {
				}

			}

			if (profile.get(DialogConfig.Keyboard) != null && profile.get(DialogConfig.Keyboard).toString().equals("true")) {
				if (txtPercenService.getCaretListeners().length == 0) {
					txtPercenService.addCaretListener(new javax.swing.event.CaretListener() {
						public void caretUpdate(javax.swing.event.CaretEvent evt) {
							if (evt.getMark() > 0) {
								// txtPercenServiceCaretUpdate();
							}
						}
					});
					txtMoneyService.addCaretListener(new javax.swing.event.CaretListener() {
						public void caretUpdate(javax.swing.event.CaretEvent evt) {
							if (evt.getMark() > 0) {
								// txtMoneyServiceCaretUpdate();
							}
						}
					});
				}
			} else {
				try {
					txtPercenService.removeCaretListener(txtPercenService.getCaretListeners()[0]);
					txtMoneyService.removeCaretListener(txtMoneyService.getCaretListeners()[0]);
				} catch (Exception e) {
				}
			}
		} catch (Exception ex) {
			System.out.println("Null Config profile -- An vao Thiet lap ban hang truoc");
		}

		int hh = 23 * k + k * 3 + 50;
		tabbedPane.setSize(hh, hh);
		tabbedPane.setPreferredSize(tabbedPane.getSize());
		loadCboTax();
	}

	public JPanel createChildPanel(JComponent label, JComponent component, JComponent label2, JTextField textField) {
		JPanel panelMain1 = new JPanel();
		panelMain1.setSize(WIDTH, 23);
		panelMain1.setPreferredSize(new Dimension(WIDTH, 23));
		panelMain1.setLayout(new GridLayout(1, 2, 10, 10));
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		label.setSize(new Dimension(70, 23));
		label.setPreferredSize(new Dimension(70, 23));
		panel.add(label, BorderLayout.WEST);
		panel.add(component, BorderLayout.CENTER);
		panelMain1.add(panel);

		JPanel panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());
		label2.setSize(new Dimension(70, 23));
		label2.setPreferredSize(new Dimension(70, 23));
		panel1.add(label2, BorderLayout.WEST);
		panel1.add(textField, BorderLayout.CENTER);
		textField.setSize(new Dimension(WIDTH, 23));
		textField.setPreferredSize(new Dimension(WIDTH, 23));
		panelMain1.add(panel1);
		panelMain1.setOpaque(false);
		panel.setOpaque(false);
		panel1.setOpaque(false);
		return panelMain1;
	}

	protected void rbtnDiscountMoneyItemStateChanged(ItemEvent evt) {
		if (rbtnDiscountMoney.isSelected()) {
			// txtPercenDiscount.setText("0");
			txtPercenDiscount.setEnabled(false);
			txtMoneyDiscount.setEnabled(true);
		} else {
			// txtMoneyDiscount.setText("0");
			txtPercenDiscount.setEnabled(true);
			txtMoneyDiscount.setEnabled(false);
		}
	}

	protected void cboTaxItemStateChanged(ItemEvent evt) {
		try {
			refeshCaculate();
		} catch (Exception e) {
		}

	}

	protected void txtPercenDiscountCaretUpdate() {

		try {
			tableModel.getInvoiceDetail().setDiscountRate(MyDouble.parseDouble(txtPercenDiscount.getText()));
			refeshCaculate();
		} catch (Exception e) {

		}
	}

	protected void txtMoneyDiscountCaretUpdate() {
		try {
			double rate = MyDouble.parseDouble(txtMoneyDiscount.getText())
			    / MyDouble.parseDouble(txtTotalMoneyBeforDiscount1.getText()) * 100;
			tableModel.getInvoiceDetail().setDiscountRate(rate);
			refeshCaculate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void refeshCaculate() {
		if (cboTax.getSelectedItem() != null) {
			double totalMoneyAfterDiscount = MyDouble.parseDouble(txtTotalMoneyBeforDiscount1.getText());
			double s = Double.valueOf(((Double) cboTax.getSelectedItem()));
			double moneyTax = s * totalMoneyAfterDiscount / 100;
			tableModel.getInvoiceDetail().setTotalTax(moneyTax);
		} else {
			tableModel.getInvoiceDetail().setTotalTax(0);
		}
		InvoiceDetail invoiceDetail = tableModel.caculator();
		double promotion = 0;
		try {
			promotion = PromotionModelManager.getInstance().getPromotion(table.getCode(), table.getLocationCode(),
			    tableModel.getCustomerLoginId(), invoiceDetail.getTotal(), invoiceDetail.getStoreCode());
		} catch (Exception e) {
			promotion = 0;
		}
		boolean a = PromotionModelManager.getInstance().isPromotionDiscount;
		if (promotion == 0) {
			for (int i = 0; i < tableModelCouponInvoice.getRowCount(); i++) {
				promotion = promotion + new MyDouble(tableModelCouponInvoice.getValueAt(i, 2).toString()).doubleValue();
			}
			a = true;
		}
		if (promotion > 0) {
			if (invoiceDetail.getTotal() > 0) {
				invoiceDetail.setDiscountRate(promotion / invoiceDetail.getTotal() * 100d);
			}
			rbtnDiscountMoney.setSelected(a);

			rbtnDiscountMoney.setEnabled(false);
			rbtnDiscountPercent.setEnabled(false);
			txtMoneyDiscount.setEnabled(false);
			txtPercenDiscount.setEnabled(false);
			tableModel.setData(invoiceDetail);
			invoiceDetail = tableModel.caculator();
		} else {
			if (!rbtnDiscountMoney.isEnabled()) {
				rbtnDiscountMoney.setEnabled(true);
				rbtnDiscountPercent.setEnabled(true);
				txtMoneyDiscount.setEnabled(true);
				txtPercenDiscount.setEnabled(true);
				invoiceDetail.setDiscountRate(0);
				invoiceDetail.setDiscount(0);
				tableModel.setData(invoiceDetail);
				invoiceDetail = tableModel.caculator();
			}
		}
		txtTotalMoneyBeforDiscount1.setText(MyDouble.valueOf(invoiceDetail.getTotal()).toString());
		try {
			if (!rbtnDiscountPercent.isSelected()) {
				if (!rbtnDiscountMoney.isEnabled() || rest) {
					txtMoneyDiscount.setText(MyDouble.valueOf(invoiceDetail.getDiscount()).toString());
				}
				if (MyDouble.valueOf(txtMoneyDiscount.getText()).doubleValue() > 0) {
					double rate = MyDouble.parseDouble(txtMoneyDiscount.getText())
					    / MyDouble.parseDouble(txtTotalMoneyBeforDiscount1.getText()) * 100;
					txtPercenDiscount.setText(MyDouble.valueOf(rate).toString());
					tableModel.getInvoiceDetail().setDiscountRate(rate);
					invoiceDetail = tableModel.caculator();
					if (rate == 0) {
						txtMoneyDiscount.setText("0");
						txtPercenDiscount.setText("0");
					} else {
						if (MyDouble.valueOf(txtMoneyDiscount.getText()).doubleValue() == 0) {
							txtMoneyDiscount.setText(MyDouble.valueOf(invoiceDetail.getDiscount()).toString());
						}
					}
				} else {
					txtPercenDiscount.setText("0");
				}
			}
		} catch (Exception e) {
		}
		try {

			if (!rbtnDiscountMoney.isSelected()) {
				txtMoneyDiscount.setText(MyDouble.valueOf(invoiceDetail.getDiscount()).toString());
				if (invoiceDetail.getDiscount() == 0) {
					txtPercenDiscount.setText("0");
				} else {
					txtPercenDiscount.setText(MyDouble.valueOf(invoiceDetail.getDiscountRate()).toString());
				}
			}
		} catch (Exception e) {
		}

		try {
			if (!rbtnDiscountService.isSelected()) {

				if (!rbtnServiceMoney.isEnabled() || rest) {
					txtMoneyService.setText(MyDouble.valueOf(invoiceDetail.getService()).toString());
				}
				if (MyDouble.valueOf(txtMoneyService.getText()).doubleValue() > 0) {
					double rate = MyDouble.parseDouble(txtMoneyService.getText())
					    / MyDouble.parseDouble(txtTotalMoneyBeforDiscount1.getText()) * 100;
					txtPercenService.setText(MyDouble.valueOf(rate).toString());
					tableModel.getInvoiceDetail().setServiceRate(rate);
					invoiceDetail = tableModel.caculator();
					if (rate == 0) {
						txtMoneyService.setText("0");
						txtPercenService.setText("0");
					} else {
						if (MyDouble.valueOf(txtMoneyService.getText()).doubleValue() == 0) {
							txtMoneyService.setText(MyDouble.valueOf(invoiceDetail.getService()).toString());
						}
					}
				} else {
					txtPercenService.setText("0");
				}
			}
		} catch (Exception e) {
		}
		try {
			if (!rbtnServiceMoney.isSelected()) {
				txtMoneyService.setText(MyDouble.valueOf(invoiceDetail.getService()).toString());
				if (invoiceDetail.getService() == 0) {
					txtPercenService.setText("0");
				} else {
					txtPercenService.setText(MyDouble.valueOf(invoiceDetail.getServiceRate()).toString());
				}
			}
		} catch (Exception e) {
		}
		txtTotalMoneyTax.setText(MyDouble.valueOf(invoiceDetail.getTotalTax()).toString());
		txtTotalMoneyAfterTax.setText(getMoney(invoiceDetail.getFinalCharge() - invoiceDetail.getCredit()).toString());
		if (invoiceDetail.getStatus() != null && invoiceDetail.getStatus().equals(Status.Paid)) {
			txtMoneyPerformed.setText(getMoney(invoiceDetail.getTotalPaid() - invoiceDetail.getCredit()).toString());
			txtMoneyMissing.setText(getMoney(invoiceDetail.getFinalCharge() - invoiceDetail.getTotalPaid()).toString());
		} else {
			txtMoneyPerformed.setText(getMoney(invoiceDetail.getTotalPaid()).toString());
			txtMoneyMissing.setText(getMoney(
			    invoiceDetail.getFinalCharge() - invoiceDetail.getTotalPaid() - invoiceDetail.getCredit()).toString());
		}

		String strMoney = txtMoneyMissing.getText();
		try {
			if (strMoney.indexOf("-") == 0) {
				strMoney = strMoney.substring(1, strMoney.indexOf("."));
				if (profile.get("ForRent") != null) {
					txtMoneyMissing.setText(new MyDouble(strMoney).toString());
				} else {
					txtMoneyMissing.setText("-" + new MyDouble(strMoney).toString());
				}
			}
		} catch (Exception e) {
		}
		System.out.println(txtMoneyMissing.getText()+"   a");
		try {
//			ManagerConvert convertMoney = new ManagerConvert();
//			strMoney = txtMoneyMissing.getText();
//			if (strMoney.indexOf("-") == 0) {
//				strMoney = strMoney.substring(1, strMoney.indexOf("."));
//			}
//			String str = "";
//			for (int i = 0; i < strMoney.length(); i++) {
//				String s1 = String.valueOf(strMoney.charAt(i));
//				if (!s1.trim().isEmpty()) {
//					str = str + s1;
//				}
//			}
//			String moneyText = convertMoney.readNumber(str);
			lblMoneySpell.setText(ManagerConvert.getInstance().convertMoney(txtMoneyMissing.getText()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JLabel getLblMoneySpell() {
		return lblMoneySpell;
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

	public javax.swing.JRadioButton getRbtnDiscountMoney() {
		return rbtnDiscountMoney;
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

	private void loadTableCouponUsed() {
		try {
			List<CouponUsed> couponUseds = PromotionModelManager.getInstance().getCouponUsedsByInvoiceId(
			    tableModel.getInvoiceDetail().getId());
			tableModelCouponInvoice.setData(couponUseds);
		} catch (Exception e) {
			tableModelCouponInvoice.setData(new ArrayList<CouponUsed>());
		}

	}

	private void loadData() {
		try {
			txtProject.setData(RestaurantModelManager.getInstance().getAllProject());
		} catch (Exception e) {
		}
	}

	@Override
	public void update(Object o, Object arg) {
		if (o instanceof Coupon) {
			try {
				Coupon coupon = (Coupon) o;
				CouponUsed couponUsed = null;
				try {
					couponUsed = PromotionModelManager.getInstance().getCouponUsed(coupon.getCode(),
					    tableModel.getInvoiceDetail().getId());
				} catch (Exception e) {
				}
				if (couponUsed == null) {
					couponUsed = new CouponUsed();
					couponUsed.setCouponCode(coupon.getCode());
					couponUsed.setCouponName(coupon.getName());
					couponUsed.setInvoiceId(tableModel.getInvoiceDetail().getId());
					couponUsed.setUnit(tableModel.getInvoiceDetail().getCurrencyUnit());
					if (coupon.getDiscount() > 0) {
						couponUsed.setTotal(coupon.getDiscount());
					} else {
						couponUsed.setTotal(coupon.getDiscountRate() * tableModel.getInvoiceDetail().getFinalCharge() / 100);
					}
					PromotionModelManager.getInstance().saveCouponUsed(couponUsed);
					loadTableCouponUsed();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			loadTableCouponUsed();
		}
		rest = true;
		if (tableModel.getInvoiceDetail().getTotalTax() != 0) {
			try {
				cboTax.setSelectedIndex(1);
			} catch (Exception ex) {
				cboTax.setSelectedIndex(0);
			}
		} else {
			cboTax.setSelectedIndex(0);
		}
		refeshCaculate();
		if (tableModel.getInvoiceDetail().getPoint() > 0) {
			chbPoint.setSelected(true);
		} else {
			chbPoint.setSelected(false);
		}
		if (tableModel.getInvoiceDetail().getCredit() > 0) {
			chbCredit.setSelected(true);
		} else {
			chbCredit.setSelected(false);
		}
		TbModelPayment.setData(tableModel.getInvoiceDetail());
		TbModelPayment.fireTableDataChanged();
		rest = false;
	}

	public javax.swing.JTextField getTxtMoneyCredit() {
		return txtMoneyCredit;
	}

	public javax.swing.JTextField getTxtTotalMark() {
		return txtTotalMark;
	}

	public void loadLableForRent(boolean forRent) {
		if (profile.get("ForRent") != null) {
			if (forRent) {
				lbDaTra.setText("Đặt cọc");
				lbPhaiTra.setText("Trả lại");
			} else {
				lbDaTra.setText("Đã trả");
				lbPhaiTra.setText("Phải trả");
			}
		}
	}

	private void refeshPoint() {
		try {
			PointTransaction pointTransaction = CustomerModelManager.getInstance().getByInvoiceId(
			    tableModel.getInvoiceDetail().getId());
			if (pointTransaction == null) {
				Customer c = CustomerModelManager.getInstance().getCustomerByCode(
				    tableModel.getInvoiceDetail().getCustomerCode());
				point = CustomerModelManager.getInstance().getPointByCustomerId(c.getId());
				double a = MyDouble.parseDouble(txtMoneyMissing.getText())
				    / CustomerModelManager.getInstance().getConversionRulePointToMoney(point.getPoint())
				        .numRatioPointToCredit();
				double b = point.getPoint();
				if (b > a) {
					MyDouble b1 = new MyDouble(a);
					b1.setNumDot(0);
					MyDouble b2 = new MyDouble(b1.toString());
					txtTotalMark.setText(b2.toString());
				} else {
					if (b > 0) {
						MyDouble b1 = new MyDouble(b);
						b1.setNumDot(0);
						MyDouble b2 = new MyDouble(b1.toString());
						txtTotalMark.setText(b2.toString());
					} else {
						txtTotalMark.setText("0");
					}
				}
			} else {
				chbPoint.setEnabled(false);
				txtTotalMark.setEnabled(false);
				txtTotalMark.setText(MyDouble.valueOf(pointTransaction.getPoint() * (-1)).toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
			txtTotalMark.setText("0");
		}
	}

	private void reset() {
		txtTotalMark.setText("0");
		txtMoneyMark.setText("0");
		txtMoneyDiscount.setText("0");
		txtMoneyService.setText("0");
		txtTotalMoneyBeforDiscount1.setText("0");
		chbCredit.setSelected(false);
		chbPoint.setSelected(false);
		txtPercenDiscount.setText("0");
		txtPercenService.setText("0");
		rbtnDiscountPercent.setSelected(true);
		rbtnDiscountService.setSelected(true);
		cboTax.setSelectedIndex(0);
		rbtnDiscountPercent.setSelected(true);
		rbtnDiscountService.setSelected(true);
		txtMoneyDiscount.setEnabled(false);
		chbCredit.setEnabled(true);
		txtMoneyCredit.setEnabled(true);
		chbPoint.setEnabled(true);
		txtTotalMark.setEnabled(true);
		txtCoupon.setItem(null);
	}

	public void loadCboTax() {
		loadData();
		try {
			List<Tax> list = ProductModelManager.getInstance().getTaxs();
			List<Double> list2 = new ArrayList<Double>();
			list2.add(0, null);
			for (Tax tax : list) {
				list2.add(tax.getPercent());
			}
			cboTax.setModel(new DefaultComboBoxModel(list2.toArray()));
			if (tableModel.getInvoiceDetail().getTotalTax() > 0) {
				cboTax.setSelectedIndex(1);
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	public TableSales getTable_Sales1() {
		return table_Sales1;
	}

	public void setTable(Table table2) {
		table = table2;
	}

	public List<Employee> getWaiters() {
		if (panelWaiters != null)
			return panelWaiters.getWaiters();
		else
			return null;
	}

	public void isResetGUIWaiters(boolean value) {
		if (value) {
			panelWaiters.resetGUI();
		}
		txtNote.setText("");
	}

	public void setRefeshGUIWaiters(List<Employee> employees) {
		panelWaiters.panelWaiters(employees);
	}

	// Thiết lập Panel TAB thành viên phục vụ mở rộng
	private class PanelWaiters {
		public PanelWaiters() {
			panelTabWaiter.setLayout(new GridLayout(5, 1, 0, 2));
			panelTabWaiter.add(drawRow());
			scrollWaiter.setViewportView(panelTabWaiter);
			scrollWaiter.setPreferredSize(new Dimension(panelTabWaiter.getWidth(), 120));
		}

		private void panelWaiters(List<Employee> employees) {
			panelTabWaiter.removeAll();
			if (employees.size() >= 5) {
				panelTabWaiter.setLayout(new GridLayout(employees.size(), 1, 0, 2));
			} else {
				panelTabWaiter.setLayout(new GridLayout(5, 1, 0, 2));
			}
			if (employees.size() == 0) {
				panelTabWaiter.add(drawRow());
				panelTabWaiter.updateUI();
				scrollWaiter.updateUI();
			} else {
				for (int i = 0; i < employees.size(); i++) {
					JPanel pa1 = new JPanel(new BorderLayout(3, 0));
					pa1.setOpaque(false);
					pa1.setPreferredSize(new Dimension(200, 22));
					JLabel lblThanhVienPV = new JLabel("Thành viên PV");
					lblThanhVienPV.setFont(new Font("Tahoma", Font.PLAIN, 12));
					TextPopup<Employee> txtNhanVienPV = new TextPopup<Employee>(Employee.class);
					try {
						List<Employee> list = HRModelManager.getInstance().getEmployees();
						if (list != null) {
							txtNhanVienPV.setData(list);
						}
					} catch (Exception ex) {
					}
					txtNhanVienPV.setItem(employees.get(i));
					txtNhanVienPV.setObject(employees.get(i));
					pa1.add(lblThanhVienPV, BorderLayout.LINE_START);
					pa1.add(txtNhanVienPV, BorderLayout.CENTER);

					JPanel pa2 = new JPanel(new BorderLayout(3, 0));
					pa2.setOpaque(false);
					JLabel lblPhanTram = new JLabel("% PV");
					lblPhanTram.setFont(new Font("Tahoma", Font.PLAIN, 12));
					JTextField txtPercentWaiter = new JTextField();
					txtPercentWaiter.setHorizontalAlignment(JTextField.RIGHT);
					txtPercentWaiter.setPreferredSize(new Dimension(100, 22));
					txtPercentWaiter.setText(employees.get(i).getModifiedBy());
					final JLabel buttonCancel = new JLabel("X");
					buttonCancel.setFont(new Font("Tahoma", Font.ITALIC, 12));
					buttonCancel.setForeground(Color.GRAY);
					buttonCancel.setHorizontalAlignment(JLabel.CENTER);
					buttonCancel.setMaximumSize(new Dimension(30, 22));
					buttonCancel.setPreferredSize(new Dimension(22, 22));
					pa2.add(lblPhanTram, BorderLayout.LINE_START);
					pa2.add(txtPercentWaiter, BorderLayout.CENTER);
					pa2.add(buttonCancel, BorderLayout.LINE_END);

					final JPanel panelRow = new JPanel();
					panelRow.setLayout(new BorderLayout(10, 0));

					panelRow.add(pa1, BorderLayout.CENTER);
					panelRow.add(pa2, BorderLayout.LINE_END);

					buttonCancel.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseEntered(MouseEvent e) {
							buttonCancel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
						}

						@Override
						public void mouseExited(MouseEvent e) {
							buttonCancel.setBorder(null);
						}

						@Override
						public void mouseClicked(MouseEvent e) {
							int count = panelTabWaiter.getComponentCount();
							if (count > 1) {
								if (count <= 5) {
									panelTabWaiter.setLayout(new GridLayout(5, 1, 0, 2));
								}
								JPanel panelChild = (JPanel) panelRow.getComponents()[0];
								TextPopup<Employee> txtEmployee = ((TextPopup<Employee>) panelChild.getComponents()[1]);
								Employee emp = txtEmployee.getItem();
								InvoiceDetail invoice = tableModel.getInvoiceDetail();
								if (invoice != null && emp != null) {
									List<Contributor> contributors = invoice.getContributors();
									if (contributors != null) {
										for (Contributor c : contributors) {
											if (c.getRole().equals(Contributor.nhanVienPV) && c.getIdentifierId().equals(emp.getLoginId())) {
												contributors.remove(c);
												break;
											}
										}
									}
								}
								panelTabWaiter.remove(panelRow);
								panelTabWaiter.updateUI();
								scrollWaiter.updateUI();
							}
						}
					});

					txtNhanVienPV.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							int count = panelTabWaiter.getComponentCount();
							int currentClick = panelTabWaiter.getComponentZOrder(panelRow);
							if (count >= 2 && currentClick != 0) {
								JPanel panelBefore = (JPanel) panelTabWaiter.getComponents()[count - 2];
								JPanel panelChild = (JPanel) panelBefore.getComponents()[0];
								TextPopup<Employee> txtEmployee = ((TextPopup<Employee>) panelChild.getComponents()[1]);
								if (txtEmployee.getItem() != null) {
									if (count >= 5) {
										panelTabWaiter.setLayout(new GridLayout(++count, 1, 0, 2));
									}
									panelTabWaiter.add(drawRow());

									panelTabWaiter.updateUI();
									scrollWaiter.updateUI();
								}
							} else {
								if (count == 1 && currentClick == 0) {
									panelTabWaiter.add(drawRow());

									panelTabWaiter.updateUI();
									scrollWaiter.updateUI();
								}
							}
						}
					});
					panelTabWaiter.add(panelRow);
					panelTabWaiter.updateUI();
					scrollWaiter.updateUI();
					updateUI();
				}
			}
		}

		protected void resetGUI() {
			panelTabWaiter.removeAll();
			panelTabWaiter.add(drawRow());
			panelTabWaiter.updateUI();
			scrollWaiter.updateUI();
			updateUI();
		}

		protected List<Employee> getWaiters() {
			List<Employee> employees = new ArrayList<Employee>();
			int count = panelTabWaiter.getComponentCount();
			for (int i = 0; i < count; i++) {
				try {
					JPanel panelRow = (JPanel) panelTabWaiter.getComponents()[i];
					JPanel panelChild1 = (JPanel) panelRow.getComponents()[0];
					TextPopup<Employee> txtEmployee = ((TextPopup<Employee>) panelChild1.getComponents()[1]);
					JPanel panelChild2 = (JPanel) panelRow.getComponents()[1];
					JTextField txtPercent = ((JTextField) panelChild2.getComponents()[1]);
					Employee e = txtEmployee.getItem();
					if (e != null) {
						e.setModifiedBy(txtPercent.getText());
						employees.add(e);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			return employees;
		}

		protected JPanel drawRow() {
			JPanel pa1 = new JPanel(new BorderLayout(3, 0));
			pa1.setOpaque(false);
			pa1.setPreferredSize(new Dimension(200, 22));
			JLabel lblThanhVienPV = new JLabel("Thành viên PV");
			lblThanhVienPV.setFont(new Font("Tahoma", Font.PLAIN, 12));
			TextPopup<Employee> txtNhanVienPV = new TextPopup<Employee>(Employee.class);
			try {
				List<Employee> employees = HRModelManager.getInstance().getEmployees();
				if (employees != null) {
					txtNhanVienPV.setData(employees);
				}
			} catch (Exception ex) {
			}
			pa1.add(lblThanhVienPV, BorderLayout.LINE_START);
			pa1.add(txtNhanVienPV, BorderLayout.CENTER);

			JPanel pa2 = new JPanel(new BorderLayout(3, 0));
			pa2.setOpaque(false);
			JTextField txtPercentWaiter = new JTextField();
			txtPercentWaiter.setHorizontalAlignment(JTextField.RIGHT);
			txtPercentWaiter.setPreferredSize(new Dimension(100, 22));
			JLabel lblPhanTram = new JLabel("% PV");
			lblPhanTram.setFont(new Font("Tahoma", Font.PLAIN, 12));
			final JLabel buttonCancel = new JLabel("X");
			buttonCancel.setFont(new Font("Tahoma", Font.ITALIC, 12));
			buttonCancel.setForeground(Color.GRAY);
			buttonCancel.setHorizontalAlignment(JLabel.CENTER);
			buttonCancel.setMaximumSize(new Dimension(30, 22));
			buttonCancel.setPreferredSize(new Dimension(22, 22));
			pa2.add(lblPhanTram, BorderLayout.LINE_START);
			pa2.add(txtPercentWaiter, BorderLayout.CENTER);
			pa2.add(buttonCancel, BorderLayout.LINE_END);

			final JPanel panelRow = new JPanel();
			panelRow.setLayout(new BorderLayout(10, 0));

			panelRow.add(pa1, BorderLayout.CENTER);
			panelRow.add(pa2, BorderLayout.LINE_END);

			buttonCancel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					buttonCancel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					buttonCancel.setBorder(null);
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					int count = panelTabWaiter.getComponentCount();
					if (count > 1) {
						if (count <= 5) {
							panelTabWaiter.setLayout(new GridLayout(5, 1, 0, 2));
						}
						JPanel panelChild = (JPanel) panelRow.getComponents()[0];
						TextPopup<Employee> txtEmployee = ((TextPopup<Employee>) panelChild.getComponents()[1]);
						Employee emp = txtEmployee.getItem();
						InvoiceDetail invoice = tableModel.getInvoiceDetail();
						if (invoice != null && emp != null) {
							List<Contributor> contributors = invoice.getContributors();
							if (contributors != null) {
								for (Contributor c : contributors) {
									if (c.getRole().equals(Contributor.nhanVienPV) && c.getIdentifierId().equals(emp.getLoginId())) {
										contributors.remove(c);
										break;
									}
								}
							}
						}
						panelTabWaiter.remove(panelRow);
						panelTabWaiter.updateUI();
						scrollWaiter.updateUI();
					}
				}
			});

			txtNhanVienPV.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int count = panelTabWaiter.getComponentCount();
					int currentClick = panelTabWaiter.getComponentZOrder(panelRow);
					if (count >= 2 && currentClick != 0) {
						JPanel panelBefore = (JPanel) panelTabWaiter.getComponents()[count - 2];
						JPanel panelChild = (JPanel) panelBefore.getComponents()[0];
						TextPopup<Employee> txtEmployee = ((TextPopup<Employee>) panelChild.getComponents()[1]);
						if (txtEmployee.getItem() != null) {
							if (count >= 5) {
								panelTabWaiter.setLayout(new GridLayout(++count, 1, 0, 2));
							}
							panelTabWaiter.add(drawRow());

							panelTabWaiter.updateUI();
							scrollWaiter.updateUI();
						}
					} else {
						if (count == 1 && currentClick == 0) {
							panelTabWaiter.add(drawRow());

							panelTabWaiter.updateUI();
							scrollWaiter.updateUI();
						}
					}
				}
			});

			return panelRow;
		}
	}

	public void setTax(int index) {
		cboTax.setSelectedIndex(index);
	}

	public int getCboTaxSelectedIndex() {
		return this.cboTax.getSelectedIndex();
	}

	public String getTotalMoneyTax() {
		return txtTotalMoneyTax.getText();
	}

	public void refershData() {
		InvoiceDetail invoiceDetail = tableModel.getInvoiceDetail();
		try {

			List<Contributor> contributors = invoiceDetail.getContributors();
			if (contributors != null) {
				List<Employee> es = new ArrayList<Employee>();
				for (Contributor c : contributors) {
					if (c.getRole().equals(Contributor.nhanVienPV)) {
						try {
							Employee e = HRModelManager.getInstance().getBydLoginId(c.getIdentifierId());
							e.setModifiedBy(Integer.valueOf(c.getPercent()).toString());
							es.add(e);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
				setRefeshGUIWaiters(es);
			}
		} catch (Exception e) {
		}

		if (invoiceDetail.getDepartmentCode() != null
		    && !invoiceDetail.getDepartmentCode().equals(HRModelManager.getInstance().getDepartmentOther().getPath()))
			cboDepartment.setSelectDepartmentByPath(invoiceDetail.getDepartmentCode());
		else
			cboDepartment.setSelectedIndex(0);

		String projectOther = RestaurantModelManager.getInstance().getProjectOther().getCode();
		if (invoiceDetail.getProjectCode() != null && !invoiceDetail.getProjectCode().equals(projectOther)) {
			if (invoiceDetail.getProjectCode().indexOf("/") == -1) {
				Project project = RestaurantModelManager.getInstance().getProjectByCode(invoiceDetail.getProjectCode());
				txtProject.setItem(project);
			} else {
				String[] pathProject = invoiceDetail.getProjectCode().split("/");
				Project project = RestaurantModelManager.getInstance().getProjectByCode(pathProject[pathProject.length - 1]);
				txtProject.setItem(project);
			}
		} else
			txtProject.setItem(null);

		if (invoiceDetail.getStoreCode() != null && !invoiceDetail.getStoreCode().equals(""))
			cboStore.setSelectStoreByCode(invoiceDetail.getStoreCode());
		else
			cboStore.setSelectedIndex(0);
	}

	private void loadProductForRent() {
		InvoiceDetail invoiceDetail = tableModel.getInvoiceDetail();
		if (!invoiceDetail.getStatus().equals(Status.Paid)) {
			for (int i = 0; i < invoiceDetail.getItems().size();) {
				if (invoiceDetail.getItems().get(i).getStatus().equals(AccountingModelManager.isForRent)) {
					invoiceDetail.getItems().get(i).setStatus(AccountingModelManager.isRecord);
					i++;
				} else {
					invoiceDetail.getItems().remove(i);
				}
			}
			tableModel.setData(invoiceDetail);
			refeshCaculate();
		}

	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

}