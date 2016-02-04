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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.component.TextPopup;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.IMyObserver;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.core.WarehousesJComboBox;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.module.accounting.DefaultInvoiceCalculator;
import com.hkt.module.accounting.entity.Invoice.ActivityType;
import com.hkt.module.accounting.entity.Invoice.Status;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.config.locale.ProductUnit;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductPrice;
import com.hkt.module.product.entity.ProductPriceType;
import com.hkt.util.text.DateUtil;

public class PanelPatternProduction extends JPanel implements IMyObserver, IDialogResto {
	private MyJDateChooser dcDate;
	private ExtendJLabel lblMoneyTOP, lblMoneyBOTTOM;
	private JPanel panelBoxTOP, panelBoxBOTTOM;
	private JScrollPane scrollTOP, scrollBOTTOM;
	private List<Product> products;
	private JButton btnEdit, btnDelete;
	private int indexMouseClick = -1;
	private double totalPriceTop = 0, totalPriceBottom = 0;
	private boolean onTop = true;
	private int gridyAuto = 0;
	private boolean deleteRow = false;
	private InvoiceDetail invoiceDetailPayment = null;
	private InvoiceDetail invoiceDetailReceipt = null;
	private boolean isEdit = false;
	private boolean reset = false;
	private JTextField txtName;

	// Hàm khởi tạo ban đầu
	public PanelPatternProduction() {
		dcDate = new MyJDateChooser();
		dcDate.setPreferredSize(new Dimension(150, 23));
		lblMoneyTOP = new ExtendJLabel("0"); // Tổng tiền
		lblMoneyBOTTOM = new ExtendJLabel("0"); // Tổng tiền

		try {
			products = ProductModelManager.getInstance().findAllProducts();
		} catch (Exception e) {
			products = new ArrayList<Product>();
		}
		init();
	}

	// Hàm tạo đẩy dữ liệu xem lại từ danh sách
	public PanelPatternProduction(InvoiceDetail invoiceDetail) {
		this.invoiceDetailPayment = invoiceDetail;
		this.invoiceDetailReceipt = AccountingModelManager.getInstance().getInvoiceDetail(invoiceDetail.getId()-1);
		dcDate = new MyJDateChooser();
		dcDate.setPreferredSize(new Dimension(150, 23));
		lblMoneyTOP = new ExtendJLabel("0");
		lblMoneyBOTTOM = new ExtendJLabel("0");

		try {
			products = ProductModelManager.getInstance().findAllProducts();
		} catch (Exception e) {
			products = new ArrayList<Product>();
		}
		try {
			dcDate.setDate(invoiceDetail.getStartDate());
		} catch (Exception ex) {
		}

		init();
		txtName.setText(invoiceDetail.getInvoiceName());
	}

	private void init() {
		this.setLayout(new BorderLayout(0, 0));
		this.setOpaque(false);

		JPanel containerTOP = new JPanel();
		containerTOP.setOpaque(false);
		containerTOP.setLayout(new FlowLayout(FlowLayout.RIGHT));
		txtName= new ExtendJTextField(DateUtil.asCompactDateTimeId(new Date()));
		containerTOP.add(new ExtendJLabel("Tên phiếu"));
		containerTOP.add(txtName);
		containerTOP.add(new ExtendJLabel("Ngày tạo"));
		containerTOP.add(dcDate);
		

		JPanel containerCENTER = new JPanel();
		containerCENTER.setLayout(new GridLayout(2, 1, 0, 0));
		containerCENTER.setOpaque(false);
		containerCENTER.add(PANEL_BOX_TOP());
		containerCENTER.add(PANEL_BOX_BOTTOM());

		this.add(containerTOP, BorderLayout.PAGE_START);
		this.add(containerCENTER, BorderLayout.CENTER);
	}

	private JPanel PANEL_BOX_TOP() {
		panelBoxTOP = new JPanel();
		scrollTOP = new JScrollPane();
		JPanel container = new JPanel();
		JPanel panelBoxCenter = new JPanel();
		JPanel panelBoxPageEnd = new JPanel();

		panelBoxTOP.setOpaque(false);
		panelBoxTOP.setLayout(new GridBagLayout());
		scrollTOP.setOpaque(false);
		scrollTOP.setBorder(BorderFactory.createEmptyBorder());
		container.setLayout(new BorderLayout());
		container.setOpaque(false);
		container.setBorder(BorderFactory.createTitledBorder(panelBoxTOP.getBorder(), "Sản phẩm nhập kho"));
		panelBoxCenter.setOpaque(false);
		panelBoxPageEnd.setOpaque(false);
		panelBoxPageEnd.setLayout(new FlowLayout(FlowLayout.RIGHT));

		GridBagConstraints gridBag = new GridBagConstraints();

		JPanel header = new JPanel();
		header.setOpaque(false);
		header.setLayout(new GridBagLayout());
		ExtendJLabel title_h1 = new ExtendJLabel("Tên thực phẩm");
		ExtendJLabel title_h2 = new ExtendJLabel("Kho nhập");
		ExtendJLabel title_h3 = new ExtendJLabel("Số lượng");
		ExtendJLabel title_h4 = new ExtendJLabel("ĐVSP");
		ExtendJLabel title_h5 = new ExtendJLabel("Đơn giá");
		ExtendJLabel title_h6 = new ExtendJLabel("Tổng tiền");
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
		gridBag.weightx = 0.5;
		gridBag.ipadx = 5;
		header.add(title_h2, gridBag);
		gridBag.gridx = 2;
		gridBag.gridy = 0;
		gridBag.weightx = 0.5;
		gridBag.ipadx = 5;
		header.add(title_h3, gridBag);
		gridBag.gridx = 3;
		gridBag.gridy = 0;
		gridBag.weightx = 0.5;
		gridBag.ipadx = 5;
		header.add(title_h4, gridBag);
		gridBag.gridx = 4;
		gridBag.gridy = 0;
		gridBag.weightx = 0.5;
		gridBag.ipadx = 5;
		header.add(title_h5, gridBag);
		gridBag.gridx = 5;
		gridBag.gridy = 0;
		gridBag.weightx = 1;
		gridBag.ipadx = 10;
		header.add(title_h6, gridBag);
		gridBag.gridx = 6;
		gridBag.gridy = 0;
		gridBag.weightx = 0.3;
		gridBag.ipadx = 5;
		header.add(new JLabel(""), gridBag);

		scrollTOP.setColumnHeaderView(header);
		scrollTOP.getColumnHeader().setOpaque(false);
		scrollTOP.setViewportView(panelBoxTOP);
		scrollTOP.getViewport().setOpaque(false);
		panelBoxPageEnd.add(new ExtendJLabel("Tổng tiền: "));
		panelBoxPageEnd.add(lblMoneyTOP);
		
		List<InvoiceItem> items = null;
		if(invoiceDetailPayment!=null){
			items=invoiceDetailPayment.getItems();
		}

		if (items != null && items.size() > 0) {
			gridyAuto = items.size();
			for (int i = 0; i < items.size(); i++) {
				gridBag.fill = GridBagConstraints.HORIZONTAL;
				gridBag.weightx = 0.5;
				gridBag.gridx = 0;
				gridBag.gridy = i + 1;
				panelBoxTOP.add(Panel_Row(panelBoxTOP, scrollTOP, true, items.get(i)), gridBag);
			}
			lblMoneyTOP.setText(MyDouble.valueOf(totalPriceTop).toString());
		} else {
			gridBag.fill = GridBagConstraints.HORIZONTAL;
			gridBag.weightx = 0.5;
			gridBag.gridx = 0;
			gridBag.gridy = 1;
			panelBoxTOP.add(Panel_Row(panelBoxTOP, scrollTOP, true, null), gridBag);
		}

		container.add(scrollTOP, BorderLayout.PAGE_START);
		container.add(panelBoxCenter, BorderLayout.CENTER);
		container.add(panelBoxPageEnd, BorderLayout.PAGE_END);
		return container;
	}

	private JPanel PANEL_BOX_BOTTOM() {
		panelBoxBOTTOM = new JPanel();
		scrollBOTTOM = new JScrollPane();
		JPanel container = new JPanel();
		JPanel panelBoxCenter = new JPanel();
		JPanel panelBoxPageEnd = new JPanel();

		panelBoxBOTTOM.setOpaque(false);
		panelBoxBOTTOM.setLayout(new GridBagLayout());
		scrollBOTTOM.setOpaque(false);
		scrollBOTTOM.setBorder(BorderFactory.createEmptyBorder());
		container.setLayout(new BorderLayout());
		container.setOpaque(false);
		container.setBorder(BorderFactory.createTitledBorder(panelBoxBOTTOM.getBorder(), "Sản phẩm xuất kho"));
		panelBoxCenter.setOpaque(false);
		panelBoxPageEnd.setOpaque(false);
		panelBoxPageEnd.setLayout(new FlowLayout(FlowLayout.RIGHT));

		GridBagConstraints gridBag = new GridBagConstraints();

		JPanel header = new JPanel();
		header.setOpaque(false);
		header.setLayout(new GridBagLayout());
		ExtendJLabel title_h1 = new ExtendJLabel("Tên thực phẩm");
		ExtendJLabel title_h2 = new ExtendJLabel("Kho xuất");
		ExtendJLabel title_h3 = new ExtendJLabel("Số lượng");
		ExtendJLabel title_h4 = new ExtendJLabel("ĐVSP");
		ExtendJLabel title_h5 = new ExtendJLabel("Đơn giá");
		ExtendJLabel title_h6 = new ExtendJLabel("Tổng tiền");
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
		gridBag.weightx = 0.5;
		gridBag.ipadx = 5;
		header.add(title_h2, gridBag);
		gridBag.gridx = 2;
		gridBag.gridy = 0;
		gridBag.weightx = 0.5;
		gridBag.ipadx = 5;
		header.add(title_h3, gridBag);
		gridBag.gridx = 3;
		gridBag.gridy = 0;
		gridBag.weightx = 0.5;
		gridBag.ipadx = 5;
		header.add(title_h4, gridBag);
		gridBag.gridx = 4;
		gridBag.gridy = 0;
		gridBag.weightx = 0.5;
		gridBag.ipadx = 5;
		header.add(title_h5, gridBag);
		gridBag.gridx = 5;
		gridBag.gridy = 0;
		gridBag.weightx = 1;
		gridBag.ipadx = 10;
		header.add(title_h6, gridBag);
		gridBag.gridx = 6;
		gridBag.gridy = 0;
		gridBag.weightx = 0.3;
		gridBag.ipadx = 5;
		header.add(new JLabel(""), gridBag);

		scrollBOTTOM.setColumnHeaderView(header);
		scrollBOTTOM.getColumnHeader().setOpaque(false);
		scrollBOTTOM.setViewportView(panelBoxBOTTOM);
		scrollBOTTOM.getViewport().setOpaque(false);
		panelBoxPageEnd.add(new ExtendJLabel("Tổng tiền: "));
		panelBoxPageEnd.add(lblMoneyBOTTOM);

		List<InvoiceItem> items = null;
		if(invoiceDetailPayment!=null){
			items=invoiceDetailReceipt.getItems();
		}

		if (items != null && items.size() > 0) {
			gridyAuto = items.size();
			for (int i = 0; i < items.size(); i++) {
				gridBag.fill = GridBagConstraints.HORIZONTAL;
				gridBag.weightx = 0.5;
				gridBag.gridx = 0;
				gridBag.gridy = i + 1;
				panelBoxBOTTOM.add(Panel_Row(panelBoxBOTTOM, scrollBOTTOM, false, items.get(i)), gridBag);
			}
			lblMoneyBOTTOM.setText(MyDouble.valueOf(totalPriceBottom).toString());
		} else {
			gridBag.fill = GridBagConstraints.HORIZONTAL;
			gridBag.weightx = 0.5;
			gridBag.gridx = 0;
			gridBag.gridy = 1;
			panelBoxBOTTOM.add(Panel_Row(panelBoxBOTTOM, scrollBOTTOM, false, null), gridBag);
		}

		container.add(scrollBOTTOM, BorderLayout.PAGE_START);
		container.add(panelBoxCenter, BorderLayout.CENTER);
		container.add(panelBoxPageEnd, BorderLayout.PAGE_END);
		return container;
	}

	private JPanel Panel_Row(final JPanel container, final JScrollPane scrollPane, final boolean isOnTop, InvoiceItem item) {
		final JPanel panel = new JPanel(new BorderLayout(2, 0));
		panel.setOpaque(false);
		panel.setBorder(BorderFactory.createEmptyBorder(2, 5, 0, 5));

		final TextPopup<Product> txtProduct = new TextPopup<Product>(Product.class);
		txtProduct.addObserver(this);
		txtProduct.setData(products);
		txtProduct.setPreferredSize(new Dimension(340, 23));
		panel.add(txtProduct, BorderLayout.WEST);

		JPanel panelCenter = new JPanel(new GridLayout(1, isOnTop ? 4 : 5, 3, 0));
		panelCenter.setOpaque(false);
		panelCenter.setPreferredSize(new Dimension(200, 22));
		final WarehousesJComboBox cbWarehouse = new WarehousesJComboBox(false);
		panelCenter.add(cbWarehouse);
		cbWarehouse.setEnabled(false);
		final ExtendJTextField txtQuantity = new ExtendJTextField("1");
		txtQuantity.setHorizontalAlignment(JTextField.RIGHT);
		panelCenter.add(txtQuantity);
		ExtendJTextField txtUnitProduct = new ExtendJTextField();
		txtUnitProduct.setEnabled(false);
		panelCenter.add(txtUnitProduct);
		final ExtendJTextField txtUnitPrice = new ExtendJTextField();
		txtUnitPrice.setHorizontalAlignment(JTextField.RIGHT);
		panelCenter.add(txtUnitPrice);
		panel.add(panelCenter, BorderLayout.CENTER);

		JPanel panelEnd = new JPanel(new BorderLayout(0, 0));
		panelEnd.setOpaque(false);
		final ExtendJTextField txtTotalPrice = new ExtendJTextField();
		txtTotalPrice.setEnabled(false);
		txtTotalPrice.setHorizontalAlignment(JTextField.RIGHT);
		txtTotalPrice.setPreferredSize(new Dimension(220, 23));
		panelEnd.add(txtTotalPrice, BorderLayout.CENTER);
		final ExtendJLabel lblRemove = new ExtendJLabel("X");
		lblRemove.setPreferredSize(new Dimension(30, 23));
		lblRemove.setForeground(Color.GRAY);
		lblRemove.setFont(new Font("Tahoma", Font.ITALIC, 13));
		lblRemove.setHorizontalAlignment(SwingConstants.CENTER);
		panelEnd.add(lblRemove, BorderLayout.EAST);
		panel.add(panelEnd, BorderLayout.EAST);

		if (item != null) {
			Product product = null;
			ProductUnit productUnit = null;
			try {
				product = ProductModelManager.getInstance().getProductByCode(item.getProductCode());
				productUnit = LocaleModelManager.getInstance().getProductUnitByCode(product.getUnit());
				txtProduct.setItem(product);
				txtQuantity.setText(new MyDouble(item.getQuantity()).toString());
				txtUnitProduct.setText(productUnit.getName());
				txtUnitPrice.setText(MyDouble.valueOf(item.getUnitPrice()).toString());
				double sumPrice = MyDouble.parseDouble(txtQuantity.getText()) * MyDouble.parseDouble(txtUnitPrice.getText());
				txtTotalPrice.setText(MyDouble.valueOf(sumPrice).toString());
				loadSum();
			} catch (Exception e1) {
				e1.printStackTrace();
			} finally {
				txtProduct.setEnabled(false);
				cbWarehouse.setEnabled(false);
				txtQuantity.setEnabled(false);
				txtUnitPrice.setEnabled(false);
			}
		}

		txtProduct.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (invoiceDetailPayment == null || isEdit == true) {
					int countRow = container.getComponentCount();
					indexMouseClick = container.getComponentZOrder(panel);
					if (isOnTop)
						onTop = true;
					else
						onTop = false;

					GridBagConstraints gridBag = new GridBagConstraints();
					gridBag.fill = GridBagConstraints.HORIZONTAL;
					gridBag.insets = new Insets(2, 0, 2, 0);
					gridBag.weightx = 0.5;
					gridBag.gridx = 0;
					gridBag.gridy = deleteRow ? (++gridyAuto) : (countRow + 1);

					if (countRow >= 2) {
						if (indexMouseClick - 1 >= 0 && countRow - indexMouseClick == 1) {
							JPanel panelBefore = (JPanel) container.getComponents()[indexMouseClick - 1];
							TextPopup<Product> txtProductSelected = (TextPopup<Product>) panelBefore.getComponents()[0];
							if (txtProductSelected.getItem() != null) {
								if (countRow >= 5) {
									scrollPane.setPreferredSize(new Dimension(100, 200));
								} else {
									int j = 56 + (countRow * 30);
									scrollPane.setPreferredSize(new Dimension(100, j));
								}
								container.add(Panel_Row(container, scrollPane, isOnTop, null), gridBag);
								container.updateUI();
								scrollPane.updateUI();
								updateUI();
							}
						}
					} else {
						scrollPane.setPreferredSize(new Dimension(100, 86));
						container.add(Panel_Row(container, scrollPane, isOnTop, null), gridBag);
						container.updateUI();
						scrollPane.updateUI();
						updateUI();
					}
				}
			}
		});

		txtQuantity.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (txtProduct.getItem() != null) {
					try {
						txtTotalPrice.setText(new MyDouble((MyDouble.parseDouble(txtQuantity.getText()) * MyDouble
						    .parseDouble(txtUnitPrice.getText()))).toString());
						loadSum();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		txtUnitPrice.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					txtTotalPrice.setText(new MyDouble((MyDouble.parseDouble(txtQuantity.getText()) * MyDouble
					    .parseDouble(txtUnitPrice.getText()))).toString());
					loadSum();
				} catch (Exception ex) {
				}
			}
		});

		lblRemove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = container.getComponentCount();
				if (i != 1) {
					container.remove(container.getComponentZOrder(panel));
					deleteRow = true;
					if (i <= 6) {
						int j = 56 + ((i - 2) * 30);
						scrollPane.setPreferredSize(new Dimension(100, j));
					}

					loadSum();

					container.updateUI();
					scrollPane.updateUI();
					updateUI();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				lblRemove.setBorder(BorderFactory.createLineBorder(Color.gray));
				lblRemove.setFont(new Font("Tahoma", Font.BOLD, 14));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblRemove.setBorder(BorderFactory.createEmptyBorder());
				lblRemove.setFont(new Font("Tahoma", Font.ITALIC, 13));
			}
		});

		return panel;
	}

	public void loadSum() {

		// if (onTop) {
		totalPriceTop = 0;
		for (int i = panelBoxTOP.getComponentCount(); i > 0; i--) {
			JPanel panel1 = (JPanel) panelBoxTOP.getComponent(i - 1);
			JPanel panel2 = (JPanel) panel1.getComponent(2);
			ExtendJTextField txtDonGia = ((ExtendJTextField) panel2.getComponent(0));
			totalPriceTop = totalPriceTop + MyDouble.parseDouble(txtDonGia.getText());
		}
		lblMoneyTOP.setText(MyDouble.valueOf(totalPriceTop).toString());
		// } else {
		if(panelBoxBOTTOM!=null){
			totalPriceBottom = 0;
			for (int i = panelBoxBOTTOM.getComponentCount(); i > 0; i--) {
				JPanel panel1 = (JPanel) panelBoxBOTTOM.getComponent(i - 1);
				JPanel panel2 = (JPanel) panel1.getComponent(2);
				ExtendJTextField txtDonGia = ((ExtendJTextField) panel2.getComponent(0));
				totalPriceBottom = totalPriceBottom + MyDouble.parseDouble(txtDonGia.getText());
			}
			lblMoneyBOTTOM.setText(MyDouble.valueOf(totalPriceBottom).toString());
		}
	
		// }
	}

	@Override
	public void Save() throws Exception {

		String dateCode = new SimpleDateFormat("ddMMyyyyHHmmssSSS").format(dcDate.getDate());
		// Xuất kho
		invoiceDetailReceipt=actionSave(panelBoxBOTTOM, false, dateCode, invoiceDetailReceipt);
		// Nhập kho
		invoiceDetailPayment=actionSave(panelBoxTOP, true, dateCode, invoiceDetailPayment);
		if (reset == true)
			((JDialog) getRootPane().getParent()).dispose();

	}

	private InvoiceDetail actionSave(JPanel container, boolean isImportWarehouse, String codeDate, InvoiceDetail invoiceDetail) {
		InvoiceDetail invoice = invoiceDetail;
		if (invoice == null) {
			invoice = new InvoiceDetail(true);
			invoice.setInvoiceCode(DateUtil.asCompactDateTimeId(dcDate.getDate()));
			invoice.setStartDate(dcDate.getDate());
		}

		invoice.setDiscountRate(100);
		invoice.setInvoiceName(txtName.getText());
		invoice.setNote(AccountingModelManager.typeSanXuat);
		invoice.setStatus(Status.Paid);
		invoice.setType(AccountingModelManager.typeSanXuat);
		List<InvoiceItem> items = new ArrayList<InvoiceItem>();
		invoice.setItems(items);
		boolean isFlag = false;
		for (int i = container.getComponentCount(); i > 0; i--) {
			JPanel panel1 = (JPanel) container.getComponent(i - 1);
			JPanel panel2 = (JPanel) panel1.getComponent(1);
			TextPopup<Product> txtProduct = ((TextPopup<Product>) panel1.getComponent(0));
			// WarehousesJComboBox cbKho = ((WarehousesJComboBox)
			// panel2.getComponent(0));
			ExtendJTextField txtSoLuong = ((ExtendJTextField) panel2.getComponent(1));
			ExtendJTextField txtDonGia = ((ExtendJTextField) panel2.getComponent(3));
			if (txtProduct.getItem() != null) {
				isFlag = true;
				try {
					MyDouble.parseDouble(txtSoLuong.getText());
				} catch (Exception ex) {
					PanelChoise pnPanel = new PanelChoise("Vui lòng kiểm tra lại số lượng sản phẩm !");
					DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
					dialog1.setTitle("Thông báo");
					dialog1.setLocationRelativeTo(null);
					dialog1.setModal(true);
					dialog1.setVisible(true);
					if (pnPanel.isDelete()) {
						txtSoLuong.requestFocus();
					}
					return null;
				}
				try {
					MyDouble.parseDouble(txtDonGia.getText());
				} catch (Exception ex) {
					PanelChoise pnPanel = new PanelChoise("Vui lòng kiểm tra lại đơn giá sản phẩm !");
					DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
					dialog1.setTitle("Thông báo");
					dialog1.setLocationRelativeTo(null);
					dialog1.setModal(true);
					dialog1.setVisible(true);
					if (pnPanel.isDelete()) {
						txtDonGia.requestFocus();
					}
					return null;
				}
				InvoiceItem item = new InvoiceItem();
				item.setItemCode(DateUtil.asCompactDateTimeId(new Date()));
				item.setItemName(txtProduct.getItem().getName());
				item.setProductCode(txtProduct.getItem().getCode());
				item.setStatus(AccountingModelManager.isPayment);
				item.setQuantity(MyDouble.parseDouble(txtSoLuong.getText()));
				item.setQuantityReal(item.getQuantity());
				// item.setDescription(cbKho.getSelectedWarehouse().getWarehouseId());
				item.setStartDate(dcDate.getDate());
				item.setUnitPrice(MyDouble.parseDouble(txtDonGia.getText()));
				item.setTotal(0);

				if (isImportWarehouse) {
					// Tạo hóa đơn nhập
					invoice.setActivityType(ActivityType.Payment);
					item.setActivityType(InvoiceItem.ActivityType.Payment);
				} else {
					// Tạo hóa đơn xuất
					invoice.setActivityType(ActivityType.Receipt);
					item.setActivityType(InvoiceItem.ActivityType.Receipt);
				}
				items.add(item);
			}

		}

		if (isFlag) {
			try {
				invoice.setDiscountRate(100);
				invoice.setDiscount(invoice.getFinalCharge());
				invoice = invoice.calculate(new DefaultInvoiceCalculator());
				invoice = AccountingModelManager.getInstance().saveInvoiceDetail(invoice);
			} catch (Exception e) {
			}
			// for (InvoiceItem ii : invoice.getItems()) {
			// if (isImportWarehouse) {
			// //Tạo sản phẩm trong kho - Nhập kho
			// for (int j = 0; j < ii.getQuantity(); j++) {
			// IdentityProduct ip = new IdentityProduct();
			// ip.setInvoiceCode(invoice.getInvoiceCode());
			// ip.setInvoiceItemIdImport(ii.getId());
			// ip.setShipmentImportCode(new
			// SimpleDateFormat("ddMMyyyyHHmmssSSS").format(new Date()));
			// ip.setProductCode(ii.getProductCode());
			// ip.setWarehouseCode(ii.getDescription());
			// ip.setCurrencyRate(ii.getCurrencyRate());
			// ip.setImportDate(ii.getStartDate());
			// ip.setPrice(ii.getUnitPrice());
			// ip.setImportType(ImportType.Import);
			// ip.setExportType(ExportType.NotExport);
			// try {
			// WarehouseModelManager.getInstance().saveIdentityProduct(ip);
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			// }
			// } else {
			// //Xuất sản phẩm trong kho
			// try {
			// List<IdentityProduct> identityProducts =
			// WarehouseModelManager.getInstance().getByImportTypeAndProduct(ImportType.Import,
			// ii.getProductCode(), ii.getDescription());
			// if (identityProducts.size() > 0) {
			// for (IdentityProduct ip : identityProducts) {
			// ip.setExportType(ExportType.Export);
			// ip.setExportDate(dcDate.getDate());
			// ip.setShipmentExportCode(DateUtil.asCompactDateTimeId(new Date()));
			// ip.setInvoiceExportCode(invoice.getInvoiceCode());
			// ip.setInvoiceItemIdExport(ii.getId());
			// ip.setPriceExport(ii.getUnitPrice());
			// WarehouseModelManager.getInstance().saveIdentityProduct(ip);
			// }
			// }
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			// }
			// }
			container.removeAll();
			GridBagConstraints gridBag = new GridBagConstraints();
			gridBag.fill = GridBagConstraints.HORIZONTAL;
			gridBag.insets = new Insets(2, 0, 2, 0);
			gridBag.weightx = 0.5;
			gridBag.gridx = 0;
			gridBag.gridy = ++gridyAuto;
			if (isImportWarehouse) {
				lblMoneyTOP.setText("0");
				scrollTOP.setPreferredSize(new Dimension(100, 56));
				panelBoxTOP.add(Panel_Row(panelBoxTOP, scrollTOP, true, null), gridBag);
				panelBoxTOP.updateUI();
				scrollTOP.updateUI();
			} else {
				lblMoneyBOTTOM.setText("0");
				scrollBOTTOM.setPreferredSize(new Dimension(100, 56));
				panelBoxBOTTOM.add(Panel_Row(panelBoxBOTTOM, scrollBOTTOM, false, null), gridBag);
				panelBoxBOTTOM.updateUI();
				scrollBOTTOM.updateUI();
			}
			updateUI();
			deleteRow = true;

		} else {
			PanelChoise pnPanel = null;
			if (isImportWarehouse)
				pnPanel = new PanelChoise("Nhập kho không chọn sản phẩm nào !");
			else
				pnPanel = new PanelChoise("Xuất kho không chọn sản phẩm nào !");
			DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
			dialog1.setTitle("Thông báo");
			dialog1.setLocationRelativeTo(null);
			dialog1.setModal(true);
			dialog1.setVisible(true);
			if (!pnPanel.isDelete()) {
				return null;
			}
		}
		return invoice;
	}

	private void showDialogMessage(String messageNote) {
		PanelChoise pnPanel = new PanelChoise(messageNote);
		DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
		dialog1.setTitle("Thông báo");
		dialog1.setLocationRelativeTo(null);
		dialog1.setModal(true);
		dialog1.setVisible(true);
		if (pnPanel.isDelete()) {
			return;
		}
	}

	public JButton getButtonEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton("Sửa");
			btnEdit.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/modify1.png")));

			btnEdit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					reset = true;
					isEdit = true;
					JPanel container = null;
					for (int j = 0; j < 2; j++) {
						if (j == 0)
							container = panelBoxTOP;
						else
							container = panelBoxBOTTOM;
						for (int i = 0; i < container.getComponentCount(); i++) {
							JPanel panel1 = (JPanel) container.getComponent(i);
							JPanel panel2 = (JPanel) panel1.getComponent(1);
							TextPopup<Product> txtProduct = ((TextPopup<Product>) panel1.getComponent(0));
							WarehousesJComboBox cbKho = ((WarehousesJComboBox) panel2.getComponent(0));
							ExtendJTextField txtSoLuong = ((ExtendJTextField) panel2.getComponent(1));
							ExtendJTextField txtDonGia = ((ExtendJTextField) panel2.getComponent(3));
							txtProduct.setEnabled(isEdit);
							cbKho.setEnabled(isEdit);
							txtSoLuong.setEnabled(isEdit);
							txtDonGia.setEnabled(isEdit);
						}
					}
				}
			});

		}
		return btnEdit;
	}

	public JButton getButtonDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton("Xóa");
			btnDelete.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/delete1.png")));
			btnDelete.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						AccountingModelManager.getInstance().deleteInvoice(invoiceDetailPayment);
						AccountingModelManager.getInstance().deleteInvoice(invoiceDetailReceipt);
					} catch (Exception e1) {
						e1.printStackTrace();
					}

					((JDialog) (PanelPatternProduction.this.getRootPane().getParent())).dispose();
				}

			});
		}
		return btnDelete;
	}

	@Override
	public void update(Object o, Object arg) {
		if (o != null) {
			try {
				Product product = ((Product) o);
				ProductPrice productPrice = null;
				ProductUnit productUnit = null;

				JPanel panel1 = (JPanel) (onTop ? panelBoxTOP : panelBoxBOTTOM).getComponent(indexMouseClick);
				JPanel panel2 = (JPanel) panel1.getComponent(1);
				JPanel panel3 = (JPanel) panel1.getComponent(2);
				ExtendJTextField txtSoLuong = ((ExtendJTextField) panel2.getComponent(1));
				ExtendJTextField txtDVSP = ((ExtendJTextField) panel2.getComponent(2));
				ExtendJTextField txtDonGia = ((ExtendJTextField) panel2.getComponent(3));
				ExtendJTextField txtTongTien = ((ExtendJTextField) panel3.getComponent(0));

				try {
					productUnit = LocaleModelManager.getInstance().getProductUnitByCode(product.getUnit());
					List<ProductPriceType> productPriceTypes = ProductModelManager.getInstance().getAllProductPriceType();
					if (productPriceTypes != null && productPriceTypes.size() > 0)
						for (ProductPriceType ppt : productPriceTypes) {
							productPrice = ProductModelManager.getInstance().getByProductAndProductPriceType(product.getCode(),
							    ppt.getType(), "VND");
							if (productPrice != null && productPrice.getPrice() != 0)
								break;
						}
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				if (productUnit != null)
					txtDVSP.setText(productUnit.getName());
				if (productPrice != null && productPrice.getPrice() != 0)
					txtDonGia.setText(productPrice.getPrice() + "");
				else
					txtDonGia.setText("0");
				if (txtSoLuong.getText().equals("") || Double.parseDouble(txtSoLuong.getText()) == 0)
					txtSoLuong.setText("1");
				txtTongTien.setText(MyDouble.valueOf(
				    Double.parseDouble(txtDonGia.getText()) * MyDouble.parseDouble(txtSoLuong.getText())).toString());
				loadSum();
			} catch (Exception e) {
			}

		}
	}

}
