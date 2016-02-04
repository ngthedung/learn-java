package com.hkt.client.swingexp.app.banhang.screen.market;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
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
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

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
import com.hkt.client.swingexp.app.banhang.screen.often.PaneSetInvoice;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelDiscountProduct;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelListPartnerOrganization;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelListPartnerOrganizationWork;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelMemberServe;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelPayment;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelPaymentProduct;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelTextMoneyPayment;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelToDoWork;
import com.hkt.client.swingexp.app.banhang.screen.often.ScreenOften;
import com.hkt.client.swingexp.app.banhang.screen.often.TableModelInvoiceItem;
import com.hkt.client.swingexp.app.component.PanelBackground;
import com.hkt.client.swingexp.app.component.TextPopup;
import com.hkt.client.swingexp.app.core.AreaJComboBox;
import com.hkt.client.swingexp.app.core.DialogList;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.HKTFile;
import com.hkt.client.swingexp.app.core.IMyObserver;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MouseEventMove;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.core.TableJComboBox;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.khuvucbanhang.PanelManageCodes;
import com.hkt.client.swingexp.app.khuyenmai.DialogChoiseMenuItem;
import com.hkt.client.swingexp.app.khuyenmai.list.TableListViewPromotion;
import com.hkt.client.swingexp.app.print.ReportPrint;
import com.hkt.client.swingexp.app.virtualkey.text.PanelTextKeyboard;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.GuiModelManager;
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
import com.hkt.module.accounting.entity.Invoice;
import com.hkt.module.accounting.entity.Invoice.Status;
import com.hkt.module.accounting.entity.Contributor;
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
import com.hkt.module.partner.supplier.entity.Supplier;
import com.hkt.module.product.entity.Product;
import com.hkt.module.promotion.entity.Menu;
import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.LocationAttribute;
import com.hkt.module.restaurant.entity.Project;
import com.hkt.module.restaurant.entity.Table;
import com.hkt.module.warehouse.entity.IdentityProduct;
import com.hkt.module.warehouse.entity.WarehouseInventory;
import com.hkt.module.warehouse.entity.IdentityProduct.ExportType;
import com.hkt.util.text.DateUtil;

/**
 * 
 * @author longnt
 */
public class DialogSuperMarket extends javax.swing.JDialog implements IMyObserver {

	/** Creates new form DialogSuperMarket */
	private PanelPayment panelPayment;
	private TableModelInvoiceItem tableModel;
	private DateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat dfTime = new SimpleDateFormat("HH:mm");
	private PanelBackground panelBackground;
	private PanelTextKeyboard panelTextKeyboard;
	private String pricingType = "";
	private Table table;
	private Profile profile;
	private String currencyCode = "VND";
	private boolean flag;
	public static DialogSuperMarket dialogSuperMarket;
	private boolean reset;
	private JScrollPane scrollPane;
	private JCheckBox chbCheck;
	private JPanel panelSearch;

	public void setReset(boolean reset) {
		this.reset = reset;
	}

	public static DialogSuperMarket getInstance() {
		if (dialogSuperMarket == null) {
			dialogSuperMarket = new DialogSuperMarket();
		}
		return dialogSuperMarket;
	}

	public JLabel getLbKM() {
		return lblKM;
	}

	protected void updateItem(double quantity) {
		InvoiceItem invoiceItem = panelPayment.getSelectedInvoiceItem();
		invoiceItem.setQuantity(quantity);
		tableModel.updateItem(invoiceItem);
	}

	public DialogSuperMarket() {
		try {

			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Metal".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception ex) {

		}
		setUndecorated(true);
		initComponents();
		setModal(true);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		panelPayment = new PanelPayment();
		tableModel = panelPayment.getTableModel();
		panelPayment.setSuperMarket(true, this);
		panelPayment.refeshGui();
		jScrollPane1.setViewportView(panelPayment);
		txtEmployee.setSize(txtEmployee.getWidth(), 25);
		txtPartner.setSize(txtEmployee.getWidth(), 25);
		txtTimKiem.addObserver(this);
		txtTimKiem.setClearText(true);
		txtPartner.addObserver(this);
		txtEmployee.addObserver(this);
		lblKM.addMouseListener(new MouseEventMove());
		// lblKM.setVisible(false);
		lableDate.setEnabled(false);
		lableTime.setEnabled(false);
		lableDate.setText(dfDate.format(new Date()));
		lableTime.setText(dfTime.format(new Date()));
		jScrollPane1.setOpaque(false);
		jScrollPane1.getViewport().setOpaque(false);
		addKeyBindings(panelBackground);
		// loadData();

		addVituarKey(panelBackground);
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

		cboTable.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				Table table = cboTable.getSelectedTable();
				saveStatusInvoice();
				setTable(table);
			}
		});

		cboArea.setEnabled(false);
		cboTable.setEnabled(false);
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

	private void setEditorGui(boolean a) {
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
			if (!c.equals(btnExit) && !c.equals(cboArea) && !c.equals(cboTable)) {
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

	public void loadData() {
		// resetGui();
		profile = AccountModelManager.getInstance().getProfileConfigAdmin();
		try {
			List<Employee> list1 = HRModelManager.getInstance().getEmployees();
			txtEmployee.setData(list1);
		} catch (Exception e) {
		}
		try {
			List<Customer> customers = CustomerModelManager.getInstance().getCustomers(false);
			txtPartner.setData(customers);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			txtTimKiem.setData(ProductModelManager.getInstance().findAllProducts());
		} catch (Exception e) {
		}

		panelPayment.loadCboTax();
		if (profile.get(DialogConfig.Keyboard) != null && profile.get(DialogConfig.Keyboard).toString().equals("true")) {
			panelTextKeyboard.setVisible(true);
		} else {
			panelTextKeyboard.setVisible(false);
		}
		setEditorGui(true);
		setTable(RestaurantModelManager.getInstance().getTablePaymentAfter());
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {
		panelTextKeyboard = new PanelTextKeyboard();
		panelBackground = new PanelBackground();
		jLabel4 = new javax.swing.JLabel();
		txtTimKiem = new TextPopup<Product>(Product.class);
		btnProjectMember = new javax.swing.JButton();
		btnExit = new javax.swing.JButton();
		jPanel4 = new javax.swing.JPanel();
		lableEmployee = new javax.swing.JLabel();
		lablePartner = new javax.swing.JLabel();
		lableDate = new JLabel();
		lableTime = new JLabel();
		lblKM = new javax.swing.JLabel();
		panelQ = new javax.swing.JPanel();
		lableNameArea = new javax.swing.JLabel();
		cboTable = new TableJComboBox();
		cboTable.dataMarket();
		panelKV = new javax.swing.JPanel();
		lableNameTable = new javax.swing.JLabel();
		cboArea = new AreaJComboBox();
		cboArea.dataMarket();
		txtEmployee = new TextPopup<Employee>(Employee.class);
		txtPartner = new TextPopup<Customer>(Customer.class);
		jScrollPane1 = new javax.swing.JScrollPane();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12));
		jLabel4.setText("Tìm kiếm"); // NOI18N

		txtTimKiem.setText(""); // NOI18N
		txtTimKiem.setFont(new java.awt.Font("Tahoma", 0, 14));
		txtSearch = new JTextField();
		txtSearch.setText(""); // NOI18N
		txtSearch.setFont(new java.awt.Font("Tahoma", 0, 14));
		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		chbCheck= new JCheckBox();
		panelSearch = new JPanel();
		panelSearch.setOpaque(false);
		chbCheck.setOpaque(false);
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
		btnProjectMember.setFont(new java.awt.Font("Tahoma", 1, 14));
		btnProjectMember.setText("Thành viên phục vụ"); // NOI18N
		btnProjectMember.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnProjectMember.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});

		btnExit.setFont(new java.awt.Font("Tahoma", 1, 12));
		btnExit.setText("Thoát"); // NOI18N
		btnExit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnExitActionPerformed(evt);
			}
		});

		jPanel4.setBackground(new java.awt.Color(255, 255, 255));
		jPanel4.setPreferredSize(new java.awt.Dimension(624, 23));

		lableEmployee.setFont(new java.awt.Font("Tahoma", 0, 12));
		lableEmployee.setText("Thu ngân"); // NOI18N

		lablePartner.setFont(new java.awt.Font("Tahoma", 0, 12));
		lablePartner.setText("Khách hàng"); // NOI18N

		lableDate.setFont(new java.awt.Font("Tahoma", 0, 12));
		lableDate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		lableDate.setText("dd/MM/yyyy"); // NOI18N
		lableDate.setBorder(null);
		// lableDate.setDisabledTextColor(new java.awt.Color(0, 0, 0));
		lableDate.setEnabled(false);
		lableDate.setOpaque(false);

		lableTime.setFont(new java.awt.Font("Tahoma", 0, 12));
		lableTime.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		lableTime.setText("HH:ss"); // NOI18N
		lableTime.setBorder(null);
		// lableTime.setDisabledTextColor(new java.awt.Color(0, 0, 0));
		lableTime.setEnabled(false);
		lableTime.setOpaque(false);

		lblKM.setFont(new java.awt.Font("Tahoma", 0, 12));
		lblKM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblKM.setText("Hiện"); // NOI18N
		lblKM.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				lblKMMouseClicked(evt);
			}
		});

		panelQ.setOpaque(false);

		lableNameArea.setFont(new java.awt.Font("Tahoma", 0, 12));
		lableNameArea.setText("Quầy"); // NOI18N

		cboTable.setFont(new java.awt.Font("Tahoma", 0, 14));

		javax.swing.GroupLayout panelQLayout = new javax.swing.GroupLayout(panelQ);
		panelQ.setLayout(panelQLayout);
		panelQLayout.setHorizontalGroup(panelQLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        panelQLayout
		            .createSequentialGroup()
		            .addComponent(lableNameArea, javax.swing.GroupLayout.PREFERRED_SIZE, 41,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		            .addComponent(cboTable, 0, 77, Short.MAX_VALUE)));
		panelQLayout.setVerticalGroup(panelQLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		    panelQLayout
		        .createSequentialGroup()
		        .addGroup(
		            panelQLayout
		                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                .addComponent(lableNameArea, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
		                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addComponent(cboTable, javax.swing.GroupLayout.PREFERRED_SIZE,
		                    javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
		        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		panelKV.setOpaque(false);

		lableNameTable.setFont(new java.awt.Font("Tahoma", 0, 12));
		lableNameTable.setText("Khu vực"); // NOI18N

		cboArea.setFont(new java.awt.Font("Tahoma", 0, 14));

		javax.swing.GroupLayout panelKVLayout = new javax.swing.GroupLayout(panelKV);
		panelKV.setLayout(panelKVLayout);
		panelKVLayout.setHorizontalGroup(panelKVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        panelKVLayout
		            .createSequentialGroup()
		            .addComponent(lableNameTable, javax.swing.GroupLayout.PREFERRED_SIZE, 54,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		            .addComponent(cboArea, 0, 74, Short.MAX_VALUE)));
		panelKVLayout.setVerticalGroup(panelKVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        panelKVLayout
		            .createSequentialGroup()
		            .addGroup(
		                panelKVLayout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		                    .addComponent(lableNameTable, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(cboArea, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
		            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		txtEmployee.setText(""); // NOI18N
		txtEmployee.setFont(new java.awt.Font("Tahoma", 0, 12));

		txtPartner.setText(""); // NOI18N
		txtPartner.setFont(new java.awt.Font("Tahoma", 0, 12));

		txtPartner.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				txtPartnerMouseClicked(evt);
			}
		});

		javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout
		    .setHorizontalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		        .addGroup(
		            jPanel4Layout
		                .createSequentialGroup()
		                .addComponent(panelKV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
		                    Short.MAX_VALUE)
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		                .addComponent(panelQ, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
		                    Short.MAX_VALUE)
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		                .addComponent(lableEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 59,
		                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                .addComponent(txtEmployee, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		                .addComponent(lablePartner)
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		                .addComponent(txtPartner, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                .addComponent(lableDate, javax.swing.GroupLayout.PREFERRED_SIZE, 78,
		                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                .addComponent(lableTime, javax.swing.GroupLayout.PREFERRED_SIZE, 46,
		                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                .addComponent(lblKM, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
		                    javax.swing.GroupLayout.PREFERRED_SIZE)));
		jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        jPanel4Layout
		            .createSequentialGroup()
		            .addContainerGap()
		            .addGroup(
		                jPanel4Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(panelQ, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
		                    .addComponent(panelKV, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
		                    .addGroup(
		                        jPanel4Layout
		                            .createSequentialGroup()
		                            .addGroup(
		                                jPanel4Layout
		                                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
		                                    .addComponent(lblKM, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
		                                    .addComponent(lablePartner, javax.swing.GroupLayout.DEFAULT_SIZE, 22,
		                                        Short.MAX_VALUE)
		                                    .addGroup(
		                                        jPanel4Layout
		                                            .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		                                            .addComponent(lableDate, javax.swing.GroupLayout.DEFAULT_SIZE, 22,
		                                                Short.MAX_VALUE)
		                                            .addComponent(txtPartner, javax.swing.GroupLayout.PREFERRED_SIZE,
		                                                javax.swing.GroupLayout.DEFAULT_SIZE,
		                                                javax.swing.GroupLayout.PREFERRED_SIZE))
		                                    .addComponent(lableTime, javax.swing.GroupLayout.DEFAULT_SIZE, 22,
		                                        Short.MAX_VALUE)
		                                    .addGroup(
		                                        jPanel4Layout
		                                            .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		                                            .addComponent(lableEmployee, javax.swing.GroupLayout.DEFAULT_SIZE, 22,
		                                                Short.MAX_VALUE)
		                                            .addComponent(txtEmployee, javax.swing.GroupLayout.PREFERRED_SIZE,
		                                                javax.swing.GroupLayout.DEFAULT_SIZE,
		                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
		                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))));

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(panelBackground);
		panelBackground.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        javax.swing.GroupLayout.Alignment.TRAILING,
		        jPanel1Layout
		            .createSequentialGroup()
		            .addContainerGap()
		            .addGroup(
		                jPanel1Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
		                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, 835, Short.MAX_VALUE)
		                    .addGroup(
		                        javax.swing.GroupLayout.Alignment.LEADING,
		                        jPanel1Layout
		                            .createSequentialGroup()
		                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)
		                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                            .addComponent(panelSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
		                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                            .addComponent(btnProjectMember, javax.swing.GroupLayout.PREFERRED_SIZE, 148,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)
		                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                            .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 77,
		                                javax.swing.GroupLayout.PREFERRED_SIZE))
		                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 835, Short.MAX_VALUE))
		            .addContainerGap()));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        jPanel1Layout
		            .createSequentialGroup()
		            .addContainerGap()
		            .addGroup(
		                jPanel1Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
		                    .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(panelSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 28,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(btnProjectMember, javax.swing.GroupLayout.PREFERRED_SIZE, 31,
		                        javax.swing.GroupLayout.PREFERRED_SIZE))
		            .addGap(12, 12, 12)
		            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 44,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
		            .addContainerGap()));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
		    panelBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
		    panelBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		pack();
	}// </editor-fold>

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
		choiseEmployeeServer();
	}

	private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {
		exitSystem();
	}

	private void txtPartnerMouseClicked(java.awt.event.MouseEvent evt) {
		if (evt.getClickCount() >= 2) {
			choiseCustomer();
		}
	}

	// Variables declaration - do not modify
	private TextPopup<Employee> txtEmployee;
	private javax.swing.JButton btnExit;
	private TextPopup<Customer> txtPartner;
	private AreaJComboBox cboArea;
	private TableJComboBox cboTable;
	private javax.swing.JButton btnProjectMember;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JScrollPane jScrollPane1;
	private JLabel lableDate;
	private javax.swing.JLabel lableEmployee;
	private javax.swing.JLabel lableNameArea;
	private javax.swing.JLabel lableNameTable;
	private javax.swing.JLabel lablePartner;
	private JLabel lableTime;
	private javax.swing.JLabel lblKM;
	private javax.swing.JPanel panelKV;
	private javax.swing.JPanel panelQ;
	private TextPopup<Product> txtTimKiem;
	private JTextField txtSearch;

	// End of variables declaration
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
				printInvoice();
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

	protected void exitSystem() {
		saveStatusInvoice();
		dispose();

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
						if (tableModel.getInvoiceDetail().getId() != null) {
							InvoiceDetail invoiceDetail = tableModel.getInvoiceDetail();
							invoiceDetail.setRecycleBin(true);
							AccountingModelManager.getInstance().deleteInvoice(invoiceDetail);
							if (reset) {
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
	}

	// Reset
	public void reset() {

		tableModel.setData(new InvoiceDetail(true));
		tableModel.changeCaculate("", "");
		txtPartner.setText("");
		txtPartner.setObject(null);
		txtPartner.setItem(null);
		txtEmployee.setText("");
		txtEmployee.setObject(null);
		txtEmployee.setItem(null);
		table.setInvoiceCode("");
		try {
			table = RestaurantModelManager.getInstance().saveTable(table);
		} catch (Exception e) {
		}
		setTable(table);
	}

	protected void lichTrinh() {
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
									contributorNVPV.setIdentifierValue(accMemberShips.get(0).getGroupPath());
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
		    MyDouble.valueOf(tableModel.getInvoiceDetail().getFinalCharge() - tableModel.getInvoiceDetail().getTotalPaid())
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
		DialogNotice.getInstace().setVisible(true);
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

		if (reset) {
			dispose();
		}
		reset();
	}

	private InvoiceDetail getInvoiceDetailPayment(List<InvoiceItem> invoiceItems) {
		InvoiceDetail invoiceDetail2 = new InvoiceDetail(true);
		for (InvoiceItem invoiceItem : invoiceItems) {
			invoiceDetail2.add(invoiceItem);
		}
		invoiceDetail2.calculate(new DefaultInvoiceCalculator());
		return invoiceDetail2;
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

	// Trả sau hóa đơn
	protected void paymentAfter() {
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

	// Mở 1 bàn mới
	private void setTable(Table tableEat) {
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

	}

	// Xem lại hóa đơn, load data
	public void setInvoiceDetail(InvoiceDetail invoiceDetail) {
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

	// Hiển thị khách hàng đã chọn lên text
	private void loadCustomerViewText() {
		try {
			Customer c = CustomerModelManager.getInstance()
			    .getCustomerByCode(tableModel.getInvoiceDetail().getCustomerCode());
			txtPartner.setItem(c);
			try {
				tableModel.setInfoInvoice(c.getLoginId(), pricingType, table);
			} catch (Exception e) {
				tableModel.setInfoInvoice(c.getLoginId(), pricingType, table);
			}
		} catch (Exception e) {
			txtPartner.setItem(null);
			tableModel.setInfoInvoice("", pricingType, table);
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

	private void resetTable() {
		try {
			Employee e = HRModelManager.getInstance().getBydLoginId(ManagerAuthenticate.getInstance().getLoginId());
			if (e != null) {
				txtEmployee.setItem(e);
			} else {
				txtEmployee.setItem(null);
			}
			txtPartner.setItem(null);
		} catch (Exception e) {
			txtEmployee.setItem(null);
			txtPartner.setItem(null);
		}
		lableDate.setText(dfDate.format(new Date()));
		lableTime.setText(dfTime.format(new Date()));
	}

	protected void numberAction(String string) {
	}

	// Sét ẩn hiện khuyến mãi
	protected void lblKMMouseClicked(MouseEvent evt) {
		if (flag) {
			lblKM.setText("Ẩn");
			flag = !flag;

			if (tableModel.getTablePromotion().getRowCount() > 0) {
				panelPayment.getScrPomostion().setVisible(true);
			} else {
				panelPayment.setTableDiscount(true);
			}
		} else {
			lblKM.setText("Hiện");
			flag = !flag;
			if (tableModel.getTablePromotion().getRowCount() > 0) {
				panelPayment.getScrPomostion().setVisible(false);
			} else {
				panelPayment.setTableDiscount(false);
			}
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
		    txtEmployee.getText(), MyDouble.valueOf(invoiceDetail.getTotal()).toString(),
		   discountRate,
		    discount, ptThue,
		    MyDouble.valueOf(invoiceDetail.getTotalTax()).toString(), tienThue, datCoc, phaiTra, strStt,
		    dfDate.format(dateEnd), gioRa, currencyCode, MyDouble.valueOf(invoiceDetail.getService()).toString(), ratio,
		    moneyQuyDoi, MyDouble.valueOf(invoiceDetail.getServiceRate()).toString(), strMoney, soDiemDung,
		    MyDouble.valueOf(invoiceDetail.getPoint()).toString(), txtPartner.getText(), "", nvpv, credit, lienHoaDon,
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

	// Xóa sản phẩm khỏi hóa đơn
	protected void deleteProduct() {
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
	protected void canceProduct() {
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
						invoiceItem.setItemCode(DateUtil.asCompactDateTimeId(new Date()));
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
	protected void changeQuantityProduct() {
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
	protected void changePriceProduct() {
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
			tableModel.setInfoInvoice(customer.getLoginId(), pricingType, table);
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
	protected void printInvoice() {
		DialogPrint dialogPrint = new DialogPrint(MyDouble.valueOf(
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

	// Hiển thị danh sách khuyến mãi
	protected void viewListPromotion() {
		TableListViewPromotion tbviewpromotion = new TableListViewPromotion();
		tbviewpromotion.setName("tbviewpromotion");
		DialogList dialog = new DialogList(tbviewpromotion);

		dialog.setTitle("Xem khuyến mại");
		dialog.setName("tbviewpromotion");
		dialog.setVisible(true);
	}

	@Override
	public void update(Object o, Object arg) {
		try {
			// Nếu [arg] = null thì đẩy kiểu đối tượng vào [o]
			if (arg instanceof String || arg instanceof Product) {
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
}
