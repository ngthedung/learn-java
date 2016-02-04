package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.component.ExtendJButton;
import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDialogPlus;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.virtualkey.text.OpenVitualkeyboard;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.accounting.entity.Invoice;
import com.hkt.module.accounting.entity.Invoice.Status;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.Table;
import com.hkt.util.text.DateUtil;

public class PanelMoveTable extends JPanel implements IDialogResto {
	private TableInvoiceItems	tableViewLeft, tableViewRight;
	private JScrollPane				scrollPaneLeft, scrollPaneRight;
	private ExtendJButton			btnAdd, btnAddAll, btnRemove, btnRemoveAll, btnGross;
	private ExtendJLabel			lblNameTable, lblNumvisitors, lblTextTable, lblTotalMoney, lblNumTotalMoney1, lblNumTotalMoney2;
	private ExtendJTextField	txtNumvisitors;
	private ExtendJComboBox		cbLocation, cbTable;
	private Table							tableSource, tableDestination;
	private InvoiceDetail			invoiceDetail;
	private List<InvoiceItem>	invoiceItemsRight, invoiceItemsLeft;
	private InvoiceItem				invoiceItem;
	private boolean						isSave;
	private Profile						profile;

	public boolean isSave() {
		return isSave;
	}

	public void setSave(boolean isSave) {
		this.isSave = isSave;
	}

	public PanelMoveTable(Table _table) {
		this.tableSource = _table;
		init();
	}

	public void init() {

		this.setOpaque(false);
		this.setLayout(new BorderLayout(10, 10));
		PanelLeft panelL = new PanelLeft();
		PanelCenter panelC = new PanelCenter();
		PanelRight panelR = new PanelRight();

		this.add(panelL, BorderLayout.LINE_START);
		this.add(panelC, BorderLayout.CENTER);
		this.add(panelR, BorderLayout.LINE_END);
	}

	protected class PanelCenter extends JPanel {
		public PanelCenter() {
			init();

			InvoiceDetail invoiceDe = getInvoiceDetailTable(tableSource);
			invoiceItemsLeft = invoiceDe.getItems();
			btnAdd.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					invoiceItem = tableViewLeft.getSelectedBean();
					if (invoiceItem != null && tableViewLeft.getSelectedRow() >= 0) {

						invoiceItemsRight.add(invoiceItem);
						tableViewRight.setTableInvoiceItems(invoiceItemsRight);

						invoiceItemsLeft.remove(invoiceItem);
						tableViewLeft.setTableInvoiceItems(invoiceItemsLeft);

						lblNumTotalMoney1.setText(MyDouble.valueOf((MyDouble.parseDouble(lblNumTotalMoney1.getText()) - invoiceItem.getTotal())).toString());
						lblNumTotalMoney2.setText(MyDouble.valueOf((MyDouble.parseDouble(lblNumTotalMoney2.getText()) + invoiceItem.getTotal())).toString());

						invoiceItem = null;
					}
				}
			});
			btnAddAll.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < invoiceItemsLeft.size(); i++) {
						invoiceItemsRight.add(invoiceItemsLeft.get(i));
					}
					invoiceItemsLeft.clear();
					lblNumTotalMoney2.setText("0");
					lblNumTotalMoney1.setText("0");
					tableViewRight.setTableInvoiceItems(invoiceItemsRight);
					tableViewLeft.setTableInvoiceItems(invoiceItemsLeft);
					invoiceDetail.setTableCode(((Table) cbTable.getSelectedItem()).getCode());
					invoiceDetail.setLocationCode(((Table) cbTable.getSelectedItem()).getLocationCode());
					for (int i = 0; i < invoiceItemsRight.size(); i++) {
						lblNumTotalMoney2.setText(MyDouble.valueOf((MyDouble.parseDouble(lblNumTotalMoney2.getText()) + invoiceItemsRight.get(i).getTotal())).toString());
					}
				}
			});
			btnRemove.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					invoiceItem = tableViewRight.getSelectedBean();
					if (invoiceItem != null && tableViewRight.getSelectedRow() >= 0) {
						invoiceItemsLeft.add(invoiceItem);
						invoiceItemsRight.remove(invoiceItem);
						tableViewRight.setTableInvoiceItems(invoiceItemsRight);
						tableViewLeft.setTableInvoiceItems(invoiceItemsLeft);

						lblNumTotalMoney1.setText(MyDouble.valueOf(MyDouble.parseDouble(lblNumTotalMoney1.getText()) + invoiceItem.getTotal()).toString());
						lblNumTotalMoney2.setText(MyDouble.valueOf(MyDouble.parseDouble(lblNumTotalMoney2.getText()) - invoiceItem.getTotal()).toString());

						invoiceItem = null;
					}

				}
			});
			btnRemoveAll.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < invoiceItemsRight.size(); i++) {
						invoiceItemsLeft.add(invoiceItemsRight.get(i));
						invoiceItemsRight.remove(i);
						i--;
					}
					lblNumTotalMoney2.setText("0");
					lblNumTotalMoney1.setText("0");
					tableViewRight.setTableInvoiceItems(invoiceItemsRight);
					tableViewLeft.setTableInvoiceItems(invoiceItemsLeft);
					for (int i = 0; i < invoiceItemsLeft.size(); i++) {
						lblNumTotalMoney1.setText(MyDouble.valueOf(MyDouble.parseDouble(lblNumTotalMoney1.getText()) + invoiceItemsLeft.get(i).getTotal()).toString());
					}
				}
			});
		}

		public void init() {
			this.setOpaque(false);
			// this.setPreferredSize(new Dimension(100, 500));
			this.setLayout(new GridLayout(9, 1, 10, 14));

			btnAdd = new ExtendJButton(">");
			btnAdd.addMouseListener(new MouseEventClickButtonDialogPlus("", "", ""));
			btnAddAll = new ExtendJButton(">>");
			btnAddAll.addMouseListener(new MouseEventClickButtonDialogPlus("", "", ""));
			btnAddAll.setName(">>");
			btnRemove = new ExtendJButton("<");
			btnRemove.addMouseListener(new MouseEventClickButtonDialogPlus("", "", ""));
			btnRemoveAll = new ExtendJButton("<<");
			btnRemoveAll.addMouseListener(new MouseEventClickButtonDialogPlus("", "", ""));
			btnGross = new ExtendJButton("Gộp SP");
			btnGross.addMouseListener(new MouseEventClickButtonDialogPlus("", "", ""));

			btnAdd.setPreferredSize(new Dimension(80, 35));
			btnAddAll.setPreferredSize(new Dimension(80, 35));
			btnRemove.setPreferredSize(new Dimension(80, 35));
			btnRemoveAll.setPreferredSize(new Dimension(80, 35));
			btnGross.setPreferredSize(new Dimension(80, 80));

			JPanel pal = new JPanel();
			pal.setSize(new Dimension(10, 160));
			pal.setOpaque(false);

			JPanel pal1 = new JPanel();
			pal1.setSize(new Dimension(10, 160));
			pal1.setOpaque(false);

			this.add(pal);
			this.add(pal1);
			this.add(btnAddAll);
			this.add(btnAdd);
			this.add(btnRemove);
			this.add(btnRemoveAll);
			this.add(btnGross);
		}
	}

	protected class PanelLeft extends JPanel {
		public PanelLeft() {
			invoiceItemsLeft = getInvoiceDetailTable(tableSource).getItems();
			init();

			lblTextTable.setText(tableSource.getName());
			lblNumTotalMoney1.setText(MyDouble.valueOf(getInvoiceDetailTable(tableSource).getTotal()).toString());
		}

		public void init() {
			this.setLayout(new BorderLayout(0, 10));
			this.setOpaque(false);
//			if (Toolkit.getDefaultToolkit().getScreenSize().width <= 1024) {
//				this.setPreferredSize(new Dimension(320, 300));
//			} else {
				this.setPreferredSize(new Dimension(380, 300));
//			}

			scrollPaneLeft = new JScrollPane();
			lblNameTable = new ExtendJLabel("Tên bàn/quầy");
			lblTextTable = new ExtendJLabel("");
			lblNumvisitors = new ExtendJLabel("Số khách");
			lblTotalMoney = new ExtendJLabel("Tổng tiền");
			lblNumTotalMoney1 = new ExtendJLabel("");
			txtNumvisitors = new ExtendJTextField();

			try {
				profile = AccountModelManager.getInstance().getProfileConfigAdmin();
				if (profile.get(DialogConfig.Keyboard) != null && profile.get(DialogConfig.Keyboard).toString().equals("true")) {
					txtNumvisitors.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							OpenVitualkeyboard.getInstancce().vitualTextKeyboard(txtNumvisitors);
						}
					});
				}

			} catch (Exception e) {
			}

			lblNameTable.setPreferredSize(new Dimension(110, 22));
			lblNumvisitors.setPreferredSize(new Dimension(110, 22));
			lblTotalMoney.setPreferredSize(new Dimension(110, 22));
			lblNumTotalMoney1.setPreferredSize(new Dimension(150, 22));
			txtNumvisitors.setPreferredSize(new Dimension(80, 22));

			txtNumvisitors.setHorizontalAlignment(JTextField.RIGHT);
			lblTextTable.setForeground(Color.WHITE);
			lblTotalMoney.setForeground(Color.WHITE);
			lblNumTotalMoney1.setForeground(Color.WHITE);
			lblTotalMoney.setFont(new Font("Tahoma", Font.BOLD, 20));
			lblNumTotalMoney1.setFont(new Font("Tahoma", Font.BOLD, 20));

			JPanel LINE1 = new JPanel();
			LINE1.setLayout(new FlowLayout(FlowLayout.LEFT));
			LINE1.setOpaque(false);
			LINE1.add(lblNameTable);
			LINE1.add(lblTextTable);

			JPanel LINE2 = new JPanel();
			LINE2.setLayout(new FlowLayout(FlowLayout.LEFT));
			LINE2.setOpaque(false);
			LINE2.add(lblNumvisitors);
			LINE2.add(txtNumvisitors);

			JPanel LINE3 = new JPanel();
			LINE3.setLayout(new FlowLayout(FlowLayout.LEFT));
			LINE3.setBackground(Color.BLACK);
			LINE3.add(lblTotalMoney);
			LINE3.add(lblNumTotalMoney1);

			JPanel LINE = new JPanel();
			LINE.setLayout(new GridLayout(3, 1, 5, 1));
			LINE.setOpaque(false);
			LINE.add(LINE1);
			LINE.add(LINE2);
			LINE.add(LINE3);

			tableViewLeft = new TableInvoiceItems(invoiceItemsLeft);
			scrollPaneLeft.setViewportView(tableViewLeft);
			// tableViewLeft.setPreferredSize(new Dimension(300, 300));
			tableViewLeft.getColumnModel().getColumn(0).setMaxWidth(50);
			PanelContents panel = new PanelContents(LINE, scrollPaneLeft);
			this.add(panel, BorderLayout.CENTER);

		}

	}

	protected class PanelRight extends JPanel {
		private ExtendJLabel	lblLocation, lblTable, lblTotalMoney;
		private JPanel				LINE, LINE1, LINE2, LINE3;

		public PanelRight() {
			init();
			List<Location> locations;
			try {
				locations = RestaurantModelManager.getInstance().getLocations();
			} catch (Exception e1) {
				locations = new ArrayList<Location>();
				e1.printStackTrace();
			}

			setDataCbLocation(locations);
			setDataCbTableWithLocation(((Location) cbLocation.getSelectedItem()));
			actionCbTable();

			cbLocation.addItemListener(new ItemListener() {				
				@Override
				public void itemStateChanged(ItemEvent e) {
					setDataCbTableWithLocation(((Location) cbLocation.getSelectedItem()));
				}
			});

			cbTable.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					InvoiceDetail invoiceDe = getInvoiceDetailTable(tableSource);
					invoiceItemsLeft = invoiceDe.getItems();
					tableViewLeft.setTableInvoiceItems(invoiceItemsLeft);
					actionCbTable();
				}
			});

		}

		public void init() {
			this.setLayout(new BorderLayout(0, 10));
//
//			if (Toolkit.getDefaultToolkit().getScreenSize().width <= 1024) {
//				this.setPreferredSize(new Dimension(320, 300));
//			} else {
				this.setPreferredSize(new Dimension(380, 300));
//			}

			LINE = new JPanel();
			LINE1 = new JPanel();
			LINE2 = new JPanel();
			LINE3 = new JPanel();

			scrollPaneRight = new JScrollPane();

			lblLocation = new ExtendJLabel("Khu vực");
			lblTable = new ExtendJLabel("Bàn/quầy");
			lblTotalMoney = new ExtendJLabel("Tổng tiền");
			lblNumTotalMoney2 = new ExtendJLabel("");
			cbLocation = new ExtendJComboBox();
			cbLocation.setName("cbLocation");
			cbTable = new ExtendJComboBox();
			cbTable.setName("cbTable");

			lblLocation.setPreferredSize(new Dimension(110, 22));
			lblTable.setPreferredSize(new Dimension(110, 22));
			lblTotalMoney.setPreferredSize(new Dimension(110, 22));
			lblNumTotalMoney2.setPreferredSize(new Dimension(150, 22));
			lblTotalMoney.setFont(new Font("Tahoma", Font.BOLD, 20));
			lblNumTotalMoney2.setFont(new Font("Tahoma", Font.BOLD, 20));
			lblTotalMoney.setForeground(Color.WHITE);
			lblNumTotalMoney2.setForeground(Color.WHITE);

			LINE1.setLayout(new FlowLayout(FlowLayout.LEFT));
			LINE1.add(lblLocation);
			LINE1.add(cbLocation);
			LINE1.setOpaque(false);

			LINE2.setLayout(new FlowLayout(FlowLayout.LEFT));
			LINE2.add(lblTable);
			LINE2.add(cbTable);
			LINE2.setOpaque(false);

			LINE3.setLayout(new FlowLayout(FlowLayout.LEFT));
			LINE3.add(lblTotalMoney);
			LINE3.add(lblNumTotalMoney2);
			LINE3.setBackground(Color.BLACK);

			LINE.setLayout(new GridLayout(3, 1, 5, 5));
			LINE.setOpaque(false);
			LINE.add(LINE1);
			LINE.add(LINE2);
			LINE.add(LINE3);

			tableViewRight = new TableInvoiceItems(new ArrayList<InvoiceItem>());
			scrollPaneRight.setViewportView(tableViewRight);
			tableViewRight.getColumnModel().getColumn(0).setMaxWidth(50);
			tableViewRight.getColumnModel().getColumn(2).setMaxWidth(50);

			PanelContents panel = new PanelContents(LINE, scrollPaneRight);
			this.add(panel, BorderLayout.CENTER);
		}

	}

	protected class PanelContents extends JPanel {
		private JPanel			panelTop;
		private JScrollPane	scrollPane;

		public PanelContents(JPanel _panelTop, JScrollPane _scrollPane) {
			this.scrollPane = _scrollPane;
			this.panelTop = _panelTop;
			init();
		}

		public void init() {
			this.setBackground(new Color(153, 153, 153));
			this.setLayout(new BorderLayout(3, 3));
			this.setBorder(BorderFactory.createLineBorder(new Color(153, 153, 153), 5));
			// this.setPreferredSize(new Dimension(550, 550));

			this.add(panelTop, BorderLayout.PAGE_START);
			this.add(scrollPane, BorderLayout.CENTER);
		}
	}

	private InvoiceDetail getInvoiceDetailTable(Table _table) {
		if(_table != null){
			try {
				return AccountingModelManager.getInstance().getInvoiceDetail(_table.invoiceId());
			} catch (Exception e) {
				return null;
			}
		} else 		
			return null;
	}

	private void setDataCbLocation(List<Location> listData) {
		DefaultComboBoxModel ChooseListLocation = new DefaultComboBoxModel(listData.toArray());
		cbLocation.setModel(ChooseListLocation);
	}

	private void setDataCbTableWithLocation(Location location) {
		List<Table> listData;
		try {
			listData = RestaurantModelManager.getInstance().findTableByLocation(location.getCode());
			for (int i = 0; i < listData.size();) {
				if (listData.get(i).getId() == tableSource.getId()) {
					listData.remove(i);
				} else {
					i++;
				}
			}
		} catch (Exception e) {
			listData = new ArrayList<Table>();
			e.printStackTrace();
		}
		DefaultComboBoxModel ViewListTable = new DefaultComboBoxModel(listData.toArray());
		cbTable.setModel(ViewListTable);
	}

	private void actionCbTable() {
		Table table = (Table) cbTable.getSelectedItem();
		invoiceDetail = getInvoiceDetailTable(table);

		if (invoiceDetail != null) {
			invoiceItemsRight = invoiceDetail.getItems();
			if (invoiceItemsRight != null) {
				tableViewRight.setTableInvoiceItems(invoiceItemsRight);
			} else {
				invoiceItemsRight = new ArrayList<InvoiceItem>();
			}

		} else {
			invoiceDetail = new InvoiceDetail(true);
			invoiceDetail.setInvoiceCode(DateUtil.asCompactDateTimeId(new Date()));
			invoiceDetail.setActivityType(Invoice.ActivityType.Receipt);
			invoiceDetail.setStatus(Status.WaitingPaid);
			invoiceDetail.setType(AccountingModelManager.typeBanHang);
			invoiceDetail.setCurrencyUnit("VND");
			invoiceDetail.setDiscount(0);
			invoiceDetail.setStartDate(new Date());
			if(table != null){
				invoiceDetail.setInvoiceName(table.getName());
				invoiceDetail.setTableCode(table.getCode());
				invoiceDetail.setLocationCode(table.getLocationCode());
			}
			
			invoiceItemsRight = new ArrayList<InvoiceItem>();
			invoiceDetail.setItems(invoiceItemsRight);
		}

		lblNumTotalMoney2.setText(MyDouble.valueOf(invoiceDetail.getTotal()).toString());
		tableViewRight.setTableInvoiceItems(invoiceItemsRight);
	}

	@Override
	public void Save() throws Exception {
		InvoiceDetail invoiceDe = getInvoiceDetailTable(tableSource);
		tableDestination = (Table) cbTable.getSelectedItem();
		if (invoiceItemsLeft.isEmpty()) {
			invoiceDe.setTableCode(tableDestination.getCode());
			invoiceDe.setInvoiceName(tableDestination.getName());
			invoiceDe.setLocationCode(tableDestination.getLocationCode());
			invoiceDe.setItems(invoiceItemsRight);
			AccountingModelManager.getInstance().saveInvoiceDetail(invoiceDe);
			tableSource.setStatus(Table.Status.TableFree);
			tableSource.setInvoiceCode("");
			tableSource = RestaurantModelManager.getInstance().saveTable(tableSource);
			tableDestination.setInvoiceCode(String.valueOf(invoiceDe.getId()));
			tableDestination.setStatus(Table.Status.TableBusy);
			tableDestination = RestaurantModelManager.getInstance().saveTable(tableDestination);
		} else {
			if (!invoiceItemsRight.isEmpty()) {
				List<InvoiceItem> invoiceItems = new ArrayList<InvoiceItem>();
				for (InvoiceItem bean : invoiceItemsRight) {
					InvoiceItem invoiceItem = new InvoiceItem();
					invoiceItem.setItemCode(DateUtil.asCompactDateTimeId(new Date()));
					invoiceItem.setItemName(bean.getItemName());
					invoiceItem.setUnitPrice(bean.getUnitPrice());
					invoiceItem.setQuantity(bean.getQuantity());
					invoiceItem.setTotal(bean.getTotal());
					invoiceItem.setDiscountRate(bean.getDiscountRate());
					invoiceItem.setTaxRate(bean.getTaxRate());
					invoiceItem.setCurrencyUnit(bean.getCurrencyUnit());
					invoiceItem.setStatus(bean.getStatus());
					invoiceItem.setProductCode(bean.getProductCode());
					invoiceItem.setActivityType(bean.getActivityType());
					invoiceItem.setStartDate(bean.getStartDate());

					invoiceItems.add(invoiceItem);
					invoiceDe.remove(bean);
				}
				AccountingModelManager.getInstance().saveInvoiceDetail(invoiceDe);
				invoiceDetail.setItems(invoiceItems);
				invoiceDetail = AccountingModelManager.getInstance().saveInvoiceDetail(invoiceDetail);
				tableDestination.setInvoiceCode(String.valueOf(invoiceDetail.getId()));
				tableDestination.setStatus(Table.Status.TableBusy);
				tableDestination = RestaurantModelManager.getInstance().saveTable(tableDestination);
			}
		}
		setSave(true);
		((JDialog) getRootPane().getParent()).dispose();
	}

	public Table getTableDestination() {
		return tableDestination;
	}

	public Table getTableSource() {
		return tableSource;
	}

}
