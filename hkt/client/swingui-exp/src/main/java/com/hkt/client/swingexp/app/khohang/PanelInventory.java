package com.hkt.client.swingexp.app.khohang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.GroupLayoutPanel;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.component.TableFillterSorter;
import com.hkt.client.swingexp.app.component.TextPopup;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.IMyObserver;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.core.WarehousesJComboBox;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.khohang.list.TableModelInventoryWarehouse;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.WarehouseModelManager;
import com.hkt.module.accounting.DefaultInvoiceCalculator;
import com.hkt.module.accounting.entity.Invoice.ActivityType;
import com.hkt.module.accounting.entity.Invoice.Status;
import com.hkt.module.accounting.entity.InvoiceTransaction.TransactionType;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.accounting.entity.InvoiceItemAttribute;
import com.hkt.module.accounting.entity.InvoiceTransaction;
import com.hkt.module.config.locale.ProductUnit;
import com.hkt.module.core.entity.ReportTable;
import com.hkt.module.core.entity.SQLSelectQuery;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductPrice;
import com.hkt.module.product.entity.ProductPriceType;
import com.hkt.module.restaurant.entity.Recipe;
import com.hkt.module.restaurant.entity.RecipeIngredient;
import com.hkt.module.warehouse.entity.IdentityProduct;
import com.hkt.module.warehouse.entity.IdentityProductAttribute;
import com.hkt.module.warehouse.entity.Warehouse;
import com.hkt.module.warehouse.entity.IdentityProduct.ExportType;
import com.hkt.module.warehouse.entity.IdentityProduct.ImportType;
import com.hkt.swingexp.app.report.entity.InventoryReportEntity;
import com.hkt.util.text.DateUtil;

public class PanelInventory extends JPanel implements IMyObserver, IDialogResto {
	private JCheckBox chbAll;
	private GroupLayoutPanel panelBoxTOP;
	private JScrollPane scrollTOP;
	private List<Product> products;
	private int indexMouseClick = -1;
	private HashMap<String, String> hashMap;
	private JPanel header;
	private TableModelInventoryWarehouse tableModel;
	private List<Warehouse> warehouses;
	private TableFillterSorter tableFillterSorter;
	private ExtendJTextField txtSearch;
	private ExtendJLabel label;
	// Hàm khởi tạo ban đầu
	public PanelInventory() {
		warehouses = WarehouseModelManager.getInstance().findWarehouses();
		txtSearch = new ExtendJTextField();
		label = new ExtendJLabel("Tìm kiếm");
		chbAll = new JCheckBox("Tất cả");
		chbAll.setPreferredSize(new Dimension(150, 23));
		chbAll.setFont(new Font("Tahoma", Font.BOLD, 14));
		try {
			products = ProductModelManager.getInstance().findAllProducts();
		} catch (Exception e) {
			products = new ArrayList<Product>();
		}
		try {
			hashMap = getQuantity();
		} catch (Exception e) {
		}
		txtSearch.setVisible(false);
		label.setVisible(false);
		init();
		chbAll.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (chbAll.isSelected()) {
					txtSearch.setVisible(true);
					label.setVisible(true);
					JTable table = new JTable();
					tableModel = new TableModelInventoryWarehouse();
					table.setModel(tableModel);
					table.setRowHeight(23);
					table.setFont(new Font("Tahoma", Font.PLAIN, 14));
					table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
					((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
					    .setHorizontalAlignment(JLabel.CENTER);
					table.getColumn("Kho hàng").setCellEditor(new DefaultCellEditor(new WarehousesJComboBox()));
					scrollTOP.setViewportView(table);
					tableFillterSorter = new TableFillterSorter(table);
					tableFillterSorter.createTableSorter();
					tableFillterSorter.createTableFillter();
					for (Product p : products) {
						String productTag = "";
						if (p.getProductTags().size() > 0) {
							productTag = p.getProductTags().get(0).toString();
						}
						ProductUnit unit = LocaleModelManager.getInstance().getProductUnitByCode(p.getUnit());
						String q = "0";
						try {
							q = new MyDouble(hashMap.get(p.getCode())).toString();
						} catch (Exception e2) {
						}
						String unitX="";
						try {
							unitX = unit.getName();
            } catch (Exception e2) {
            }
						String[] row = { p.getCode(), p.getName(), "", productTag, q, "", unitX };
						tableModel.addRow(row);
					}
				} else {
					txtSearch.setVisible(false);
					label.setVisible(false);
					scrollTOP.setViewportView(PANEL_BOX_TOP());
					scrollTOP.setBorder(null);
				}

			}
		});
		txtSearch.addCaretListener(new CaretListener() {
			
			@Override
			public void caretUpdate(CaretEvent e) {
				tableFillterSorter.fillter(txtSearch.getText(), "Sản phẩm");
			}
		});
	}

	private void init() {
		this.setLayout(new BorderLayout(5, 5));
		this.setOpaque(false);
		chbAll.setOpaque(false);
		chbAll.setHorizontalAlignment(JLabel.RIGHT);
		JPanel p = new JPanel();
		p.setOpaque(false);
		p.setLayout(new BorderLayout());
		p.add(label, BorderLayout.WEST);
		p.add(txtSearch);
		
		JPanel p1 = new JPanel();
		p1.setOpaque(false);
		p1.setLayout(new GridLayout(1, 2));
		p1.add(p);
		p1.add(chbAll);
		
		this.add(p1, BorderLayout.PAGE_START);
		scrollTOP = new JScrollPane();
		scrollTOP.setViewportView(PANEL_BOX_TOP());
		scrollTOP.setBorder(null);
		this.add(scrollTOP, BorderLayout.CENTER);
	}

	private JPanel PANEL_BOX_TOP() {
		panelBoxTOP = new GroupLayoutPanel();
		JScrollPane scrollTOP = new JScrollPane();
		JPanel container = new JPanel();
		container.setLayout(new GridLayout());
		container.setOpaque(false);
		panelBoxTOP.setOpaque(false);

		GridBagConstraints gridBag = new GridBagConstraints();

		header = new JPanel();
		header.setOpaque(false);
		header.setLayout(new GridBagLayout());
		ExtendJLabel title_h1 = new ExtendJLabel("Sản phẩm");
		ExtendJLabel title_h2 = new ExtendJLabel("Kho");
		ExtendJLabel title_h3 = new ExtendJLabel("Nhóm SP");
		ExtendJLabel title_h4 = new ExtendJLabel("SL hiện tại");
		ExtendJLabel title_h5 = new ExtendJLabel("SL thực tế");
		ExtendJLabel title_h6 = new ExtendJLabel("Đơn vị");
		title_h1.setHorizontalAlignment(SwingConstants.CENTER);
		title_h2.setHorizontalAlignment(SwingConstants.CENTER);
		title_h3.setHorizontalAlignment(SwingConstants.CENTER);
		title_h4.setHorizontalAlignment(SwingConstants.CENTER);
		title_h5.setHorizontalAlignment(SwingConstants.CENTER);
		title_h6.setHorizontalAlignment(SwingConstants.CENTER);

		gridBag.fill = GridBagConstraints.HORIZONTAL;
		gridBag.gridx = 0;
		gridBag.gridy = 0;
		gridBag.weightx = 2;
		gridBag.ipadx = 20;
		header.add(title_h1, gridBag);
		gridBag.gridx = 1;
		gridBag.gridy = 0;
		gridBag.weightx = 1;
		gridBag.ipadx = 10;
		header.add(title_h2, gridBag);
		gridBag.gridx = 2;
		gridBag.gridy = 0;
		gridBag.weightx = 1;
		gridBag.ipadx = 10;
		header.add(title_h3, gridBag);
		gridBag.gridx = 3;
		gridBag.gridy = 0;
		gridBag.weightx = 0.5;
		gridBag.ipadx = 10;
		header.add(title_h4, gridBag);
		gridBag.gridx = 4;
		gridBag.gridy = 0;
		gridBag.weightx = 1;
		gridBag.ipadx = 10;
		header.add(title_h5, gridBag);
		gridBag.gridx = 5;
		gridBag.gridy = 0;
		gridBag.weightx = 0.5;
		gridBag.ipadx = 5;
		header.add(title_h6, gridBag);

		scrollTOP.setColumnHeaderView(header);
		scrollTOP.getColumnHeader().setOpaque(false);
		scrollTOP.setViewportView(panelBoxTOP);
		scrollTOP.getViewport().setOpaque(false);
		panelBoxTOP.addComponent(Panel_Row());

		container.add(scrollTOP);
		return container;
	}

	private JPanel Panel_Row() {
		final JPanel panel = new JPanel(new BorderLayout(2, 0));
		panel.setOpaque(false);

		final TextPopup<Product> txtProduct = new TextPopup<Product>(Product.class);
		txtProduct.addObserver(this);
		txtProduct.setData(products);
		txtProduct.setPreferredSize(new Dimension(340, 23));
		panel.add(txtProduct, BorderLayout.WEST);
		txtProduct.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				indexMouseClick = panelBoxTOP.getComponentZOrder(panel);
				int countRow = panelBoxTOP.getComponentCount();
				if (countRow >= 2) {
					if (indexMouseClick - 1 >= 0 && countRow - indexMouseClick == 1) {
						JPanel panelBefore = (JPanel) panelBoxTOP.getComponents()[indexMouseClick - 1];
						TextPopup<Product> txtProductSelected = (TextPopup<Product>) panelBefore.getComponents()[0];
						if (txtProductSelected.getItem() != null) {
							panelBoxTOP.addComponent(Panel_Row());
							panelBoxTOP.updateUI();
						}
					}
				} else {
					panelBoxTOP.addComponent(Panel_Row());
					panelBoxTOP.updateUI();
				}
			}
		});
		JPanel panelCenter = new JPanel(new GridLayout(1, 4, 3, 0));
		panelCenter.setOpaque(false);
		panelCenter.setPreferredSize(new Dimension(200, 22));
		WarehousesJComboBox cbWarehouse = new WarehousesJComboBox();
		panelCenter.add(cbWarehouse);
		ExtendJTextField txtProductGroup = new ExtendJTextField();

		panelCenter.add(txtProductGroup);
		txtProductGroup.setEnabled(false);
		txtProductGroup.setDisabledTextColor(Color.black);

		ExtendJTextField txtQuantity = new ExtendJTextField();
		txtQuantity.setHorizontalAlignment(JTextField.RIGHT);
		txtQuantity.setEnabled(false);
		txtQuantity.setDisabledTextColor(Color.black);
		panelCenter.add(txtQuantity);
		ExtendJTextField txtQuantityReal = new ExtendJTextField();
		txtQuantityReal.setHorizontalAlignment(JTextField.RIGHT);
		panelCenter.add(txtQuantityReal);
		panel.add(panelCenter, BorderLayout.CENTER);

		JComboBox<ProductUnit> cbUnitProduct = new JComboBox<ProductUnit>();
		cbUnitProduct.setPreferredSize(new Dimension(140, 23));
		try {
			DefaultComboBoxModel<ProductUnit> modelProductUnits = new DefaultComboBoxModel(LocaleModelManager.getInstance()
			    .getProductUnits().toArray());
			cbUnitProduct.setModel(modelProductUnits);
		} catch (Exception e) {
		}

		panel.add(cbUnitProduct, BorderLayout.EAST);

		return panel;
	}

	@Override
	public void Save() throws Exception {
		if (!chbAll.isSelected()) {
			boolean a = false;
			for (int i = panelBoxTOP.getComponentCount(); i > 0; i--) {
				JPanel panel1 = (JPanel) (panelBoxTOP).getComponent(i - 1);
				JPanel panel2 = (JPanel) panel1.getComponent(1);
				TextPopup<Product> txt = (TextPopup<Product>) panel1.getComponent(0);
				ExtendJTextField txtSLTT = ((ExtendJTextField) panel2.getComponent(3));
				if (txt.getItem() != null) {
					try {
						MyDouble.parseDouble(txtSLTT.getText());
					} catch (Exception e) {
						a = true;
					}
				}
			}
			if (a) {
				JOptionPane.showMessageDialog(null, "Sai định dạng");
				return;
			}
			for (int i = panelBoxTOP.getComponentCount(); i > 0; i--) {
				JPanel panel1 = (JPanel) (panelBoxTOP).getComponent(i - 1);
				JPanel panel2 = (JPanel) panel1.getComponent(1);
				TextPopup<Product> txt = (TextPopup<Product>) panel1.getComponent(0);
				WarehousesJComboBox cbKho = ((WarehousesJComboBox) panel2.getComponent(0));
				ExtendJTextField txtSLHT = ((ExtendJTextField) panel2.getComponent(2));
				ExtendJTextField txtSLTT = ((ExtendJTextField) panel2.getComponent(3));
				if (txt.getItem() != null) {
					double h = MyDouble.parseDouble(txtSLHT.getText());
					double k = MyDouble.parseDouble(txtSLTT.getText());
					Product product = txt.getItem();
					String ware = null;
					try {
						ware = cbKho.getSelectedWarehouse().getWarehouseId();
					} catch (Exception e) {
						ware = null;
					}
					addProduct(product, h, k, ware);
				}
			}
		} else {
			boolean a = false;
			for (int i = 0; i < tableModel.getRowCount(); i++) {
				if (!tableModel.getValueAt(i, 5).toString().trim().isEmpty()) {
					try {
						MyDouble.parseDouble(tableModel.getValueAt(i, 5).toString());
					} catch (Exception e) {
						a = true;
					}
				}
			}
			if (a) {
				JOptionPane.showMessageDialog(null, "Sai định dạng");
				return;
			}
			for (int i = 0; i < tableModel.getRowCount(); i++) {
				if (!tableModel.getValueAt(i, 5).toString().trim().isEmpty()) {
					double h = MyDouble.parseDouble(tableModel.getValueAt(i, 4).toString());
					double k = MyDouble.parseDouble(tableModel.getValueAt(i, 5).toString());
					Product p = new Product();
					p.setCode(tableModel.getValueAt(i, 0).toString());
					p.setName(tableModel.getValueAt(i, 1).toString());
					String ware = null;
					for (Warehouse w : warehouses) {
						if (w.getName().equals(tableModel.getValueAt(i, 2).toString())) {
							ware = w.getWarehouseId();
							break;
						}
					}
					addProduct(p, h, k, ware);
				}
			}
		}
		DialogNotice.getInstace().setVisible(true);
		hashMap = getQuantity();
		if(chbAll.isSelected()){
			chbAll.setSelected(false);
			chbAll.setSelected(true);
			txtSearch.setText("");
		}else {
			scrollTOP.setViewportView(PANEL_BOX_TOP());
			scrollTOP.setBorder(null);
		}
	}

	private void addProduct(Product product, double h, double k, String warehouseId) throws Exception {
		if (h > k) {
			InvoiceDetail invoice = new InvoiceDetail(true);
			invoice.setInvoiceCode(DateUtil.asCompactDateTimeId(new Date()));
			invoice.setActivityType(ActivityType.Receipt);
			invoice.setType(AccountingModelManager.typeSanXuat);
			invoice.setCurrencyUnit("VND");
			invoice.setStartDate(new Date());
			invoice.setDiscount(0);
			invoice.setInvoiceName("");
			try {
				invoice.setWarehouseId(warehouseId);
			} catch (Exception e) {
			}

			invoice.setDepartmentCode(HRModelManager.getInstance().getDepartmentOther().getPath());
			Employee em = HRModelManager.getInstance().getBydLoginId(ManagerAuthenticate.getInstance().getLoginId());
			if (em != null) {
				invoice.setEmployeeCode(em.getCode());
			}
			invoice.setInvoiceName("Cân bằng kho SP " + product.getName());

			InvoiceItem invoiceItem = new InvoiceItem();
			invoiceItem.setItemName(product.getName());
			invoiceItem.setItemCode(DateUtil.asCompactDateTimeId(new Date()));

			invoiceItem.setQuantity(h - k);
			invoiceItem.setUnitPrice(0);
			invoiceItem.setTotal(invoiceItem.getQuantity() * invoiceItem.getUnitPrice());
			invoiceItem.setCurrencyUnit("VND");
			invoiceItem.setStatus(AccountingModelManager.isRecord);

			invoiceItem.setStartDate(new Date());
			invoiceItem.setActivityType(InvoiceItem.ActivityType.Receipt);
			invoiceItem.setProductCode(product.getCode());
			invoiceItem.setStatus(AccountingModelManager.isPayment);
			invoice.add(invoiceItem);

			invoice.setStatus(Status.Paid);
			// transaction
			invoice = invoice.calculate(new DefaultInvoiceCalculator());
			if (invoice.getFinalCharge() != invoice.getTotalPaid()) {
				InvoiceTransaction transaction = new InvoiceTransaction();
				transaction.setTransactionType(TransactionType.Cash);
				transaction.setCreatedBy(ManagerAuthenticate.getInstance().getLoginId());
				transaction.setCurrencyUnit("VND");
				transaction.setTotal(invoice.getFinalCharge() - invoice.getTotalPaid());
				transaction.setTransactionDate(new Date());
				invoice.add(transaction);
				invoice = invoice.calculate(new DefaultInvoiceCalculator());
			}
			invoice = AccountingModelManager.getInstance().saveInvoiceDetail(invoice);
			String code = DateUtil.asCompactDateTimeId(new Date());
			List<IdentityProduct> identityProducts = new ArrayList<IdentityProduct>();
			if (warehouseId== null) {
				identityProducts = WarehouseModelManager.getInstance().findByProductCodeAndExportType(product.getCode());
			} else {
				identityProducts = WarehouseModelManager.getInstance().findByProductCodeAndExportTypeAndWarehoseCode(
				    product.getCode(), warehouseId);
			}
			double quantity = invoiceItem.getQuantity();
			int count = 0;
			if (identityProducts.size() > quantity) {
				for (IdentityProduct identityProduct : identityProducts) {
					identityProduct.setExportDate(invoice.getStartDate());
					identityProduct.setShipmentExportCode(code);
					identityProduct.setExportType(ExportType.Export);
					identityProduct.setInvoiceExportCode(code);
					identityProduct.setInvoiceItemIdExport(invoice.getItems().get(0).getId());
					identityProduct.setCurrencyExportRate(1);
					identityProduct.setPriceExport(0);
					WarehouseModelManager.getInstance().saveIdentityProduct(identityProduct);
					count++;
					if (count == quantity) {
						break;
					}
				}
			}
		} else {

			InvoiceDetail invoice = new InvoiceDetail(true);
			invoice.setInvoiceCode(DateUtil.asCompactDateTimeId(new Date()));
			invoice.setActivityType(ActivityType.Payment);
			invoice.setType(AccountingModelManager.typeSanXuat);
			invoice.setCurrencyUnit("VND");
			invoice.setStartDate(new Date());
			invoice.setDiscount(0);
			invoice.setInvoiceName("");
			try {
				invoice.setWarehouseId(warehouseId);
			} catch (Exception e) {
			}

			invoice.setDepartmentCode(HRModelManager.getInstance().getDepartmentOther().getPath());
			Employee em = HRModelManager.getInstance().getBydLoginId(ManagerAuthenticate.getInstance().getLoginId());
			if (em != null) {
				invoice.setEmployeeCode(em.getCode());
			}
			invoice.setInvoiceName("Cân bằng kho SP " + product.getName());

			InvoiceItem invoiceItem = new InvoiceItem();
			invoiceItem.setItemName(product.getName());
			invoiceItem.setItemCode(DateUtil.asCompactDateTimeId(new Date()));

			invoiceItem.setQuantity(k - h);
			invoiceItem.setUnitPrice(0);
			invoiceItem.setTotal(invoiceItem.getQuantity() * invoiceItem.getUnitPrice());
			invoiceItem.setCurrencyUnit("VND");
			invoiceItem.setStatus(AccountingModelManager.isRecord);

			invoiceItem.setStartDate(new Date());
			invoiceItem.setActivityType(InvoiceItem.ActivityType.Payment);
			invoiceItem.setProductCode(product.getCode());
			invoiceItem.setStatus(AccountingModelManager.isPayment);
			invoice.add(invoiceItem);

			invoice.setStatus(Status.Paid);
			// transaction
			invoice = invoice.calculate(new DefaultInvoiceCalculator());
			if (invoice.getFinalCharge() != invoice.getTotalPaid()) {
				InvoiceTransaction transaction = new InvoiceTransaction();
				transaction.setTransactionType(TransactionType.Cash);
				transaction.setCreatedBy(ManagerAuthenticate.getInstance().getLoginId());
				transaction.setCurrencyUnit("VND");
				transaction.setTotal(invoice.getFinalCharge() - invoice.getTotalPaid());
				transaction.setTransactionDate(new Date());
				invoice.add(transaction);
				invoice = invoice.calculate(new DefaultInvoiceCalculator());
			}
			invoice = AccountingModelManager.getInstance().saveInvoiceDetail(invoice);

			String productCode = product.getCode();
			String provider = "";
			Date dateMenufacture = null;
			Date dateExpiry = null;
			double quantity = invoiceItem.getQuantity();

			Date shipmentImportCode = new Date();
			invoiceItem = invoice.getItems().get(0);
			if (quantity > 0) {

				for (int t = 0; t < quantity; t++) {
					IdentityProduct identityProduct = new IdentityProduct();
					identityProduct.setInvoiceCode(invoice.getInvoiceCode());
					identityProduct.setInvoiceItemIdImport(invoiceItem.getId());
					if (warehouseId != null) {
						identityProduct.setWarehouseCode(warehouseId);
					} else {
						identityProduct.setWarehouseCode(null);
					}
					identityProduct.setProductCode(productCode);
					identityProduct.setProvider(provider);
					identityProduct.setDateExpiry(dateExpiry);
					identityProduct.setDateMenufacture(dateMenufacture);
					identityProduct.setPrice(invoiceItem.getUnitPrice());

					SimpleDateFormat fomatCode = new SimpleDateFormat("ddMMyyyyHHmmssSSS");
					identityProduct.setShipmentImportCode(fomatCode.format(shipmentImportCode));
					identityProduct.setImportDate(shipmentImportCode);
					identityProduct.setImportType(ImportType.Import);
					identityProduct.setExportType(ExportType.NotExport);

					identityProduct = WarehouseModelManager.getInstance().saveIdentityProduct(identityProduct);
				}
				invoiceItem.setQuantityReal(invoiceItem.getQuantity());
			}

			invoice.setEndDate(new Date());
			AccountingModelManager.getInstance().saveInvoiceDetail(invoice);
		}
	}

	@Override
	public void update(Object o, Object arg) {
		if (o != null) {
			Product product = ((Product) o);

			JPanel panel1 = (JPanel) (panelBoxTOP).getComponent(indexMouseClick);
			JPanel panel2 = (JPanel) panel1.getComponent(1);
			ExtendJTextField txtNhom = ((ExtendJTextField) panel2.getComponent(1));
			ExtendJTextField txtSLHT = ((ExtendJTextField) panel2.getComponent(2));
			txtSLHT.setText(new MyDouble(hashMap.get(product.getCode())).toString());
			try {
				txtNhom.setText(product.getProductTags().get(0).toString());
			} catch (Exception e) {
			}
			try {
				JComboBox cbKho = ((JComboBox) panel1.getComponent(2));
				for (int i = 0; i < cbKho.getItemCount(); i++) {
					if (((ProductUnit) cbKho.getItemAt(i)).getCode().equals(product.getUnit())) {
						cbKho.setSelectedIndex(i);
						break;
					}
				}
			} catch (Exception e) {

			}

		}
	}

	private HashMap<String, String> getQuantity() throws Exception {
		SQLSelectQuery rQuery1 = new SQLSelectQuery();
		SQLSelectQuery rQuery2 = new SQLSelectQuery();

		rQuery1.table("InvoiceItem i").field("i.productCode AS `productCode`", "value")
		    .field("COALESCE(SUM(i.quantity), 0) AS `SLDauKy`", "value1").cond("i.activityType='Payment'")
		    .groupBy("i.productCode");

		rQuery2.table("InvoiceItem i").field("i.productCode AS `productCode`", "value")
		    .field("COALESCE(SUM(i.quantity), 0) AS `SLDauKy`", "value1").cond("i.activityType='Receipt'")
		    .groupBy("i.productCode");

		HashMap<String, String> objects1 = runQuery(rQuery1);
		HashMap<String, String> objects2 = runQuery(rQuery2);
		Iterator<String> in = objects2.keySet().iterator();
		while (in.hasNext()) {
			String code = in.next();
			Recipe recipe = RestaurantModelManager.getInstance().getRecipeByProductCode(code);
			if (recipe == null) {
				String obj1 = objects1.get(code);
				if (obj1 == null) {
					objects1.put(code, objects2.get(code));
				} else {
					objects1.put(code,
					    new MyDouble(MyDouble.parseDouble(objects1.get(code)) - MyDouble.parseDouble(objects2.get(code)))
					        .toString());
				}
			} else {
				List<RecipeIngredient> ingredients = recipe.getRecipeIngredients();
				for (RecipeIngredient recipeIngredient : ingredients) {
					Recipe recipe1 = RestaurantModelManager.getInstance().getRecipeByProductCode(
					    recipeIngredient.getProductCode());
					if (recipe1 != null) {
						List<RecipeIngredient> ingredients1 = recipe1.getRecipeIngredients();
						for (RecipeIngredient recipeIngredient1 : ingredients1) {
							String obj1 = objects1.get(recipeIngredient1.getProductCode());
							if (obj1 == null) {
								objects1.put(recipeIngredient1.getProductCode(), objects2.get(recipeIngredient1.getProductCode()));
							} else {
								objects1.put(
								    code,
								    new MyDouble(MyDouble.parseDouble(objects1.get(recipeIngredient1.getProductCode()))
								        - (MyDouble.parseDouble(objects2.get(recipeIngredient1.getProductCode())) * recipeIngredient1
								            .getQuantity())).toString());
							}

						}
					} else {
						String obj1 = objects1.get(recipeIngredient.getProductCode());
						if (obj1 == null) {
							objects1.put(recipeIngredient.getProductCode(), objects2.get(recipeIngredient.getProductCode()));
						} else {
							objects1.put(
							    code,
							    new MyDouble(MyDouble.parseDouble(objects1.get(recipeIngredient.getProductCode()))
							        - (MyDouble.parseDouble(objects2.get(recipeIngredient.getProductCode())) * recipeIngredient
							            .getQuantity())).toString());
						}
					}
				}
			}

		}
		return objects1;
	}

	private HashMap<String, String> runQuery(SQLSelectQuery rQuery) throws Exception {
		System.out.println(rQuery.createSQLQuery());
		ReportTable[] reportTable = AccountingModelManager.getInstance().reportQuery(new SQLSelectQuery[] { rQuery });
		String[] field = { "value", "value1" };
		reportTable[0].dump(field);
		List<Map<String, Object>> records = reportTable[0].getRecords();
		HashMap<String, String> hashMap = new HashMap<String, String>();
		for (int i = 0; i < records.size(); i++) {
			Map<String, Object> record = records.get(i);
			Object[] values = new Object[field.length];
			for (int j = 0; j < field.length; j++) {
				values[j] = record.get(field[j]);
			}
			if (values[0] != null) {
				hashMap.put(values[0].toString(), values[1].toString());
			}
		}
		return hashMap;
	}

}
