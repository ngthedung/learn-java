package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellRenderer;

import com.hkt.client.swingexp.app.banhang.list.PanelChooseQuantity;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.khuyenmai.DialogChoiseMenuItem;
import com.hkt.client.swingexp.app.thuchi.ChangeQuantityPriceItem;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.PromotionModelManager;
import com.hkt.client.swingexp.model.WarehouseModelManager;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.accounting.entity.Invoice.ActivityType;
import com.hkt.module.promotion.entity.Menu;
import com.hkt.module.warehouse.entity.IdentityProduct;
import com.hkt.util.text.DateUtil;

public class TableSales extends JTable {
	private boolean flagEditTable;
	private TableModelInvoiceItem tableModel;

	public TableSales(TableModelInvoiceItem tableModelInvoiceItem) {
		this.tableModel = tableModelInvoiceItem;
		setModel(tableModelInvoiceItem);
		setRowHeight(25);
		setShowGrid(false);
		getColumnModel().getColumn(0).setMaxWidth(30);
		getColumnModel().getColumn(4).setMaxWidth(40);
		getColumnModel().getColumn(6).setMaxWidth(50);
		getColumnModel().getColumn(2).setMaxWidth(40);
		getColumnModel().getColumn(5).setMaxWidth(130);
		getColumnModel().getColumn(3).setMaxWidth(120);
		getColumnModel().getColumn(5).setMinWidth(100);
		getColumnModel().getColumn(3).setMinWidth(100);
		getTableHeader().setOpaque(true);
		setBackground(Color.white);
		for (int i = 0; i < getColumnCount(); i++) {
			getColumnModel().getColumn(i).setHeaderRenderer(new TableHeaderRerender());

		}
		for (int i = 0; i < getColumnCount(); i++) {
			getColumnModel().getColumn(i).setCellRenderer(new TableRerenderSale(true));
		}

		JCheckBox chBox = new JCheckBox();
		chBox.setHorizontalAlignment(JLabel.CENTER);
		getColumn("Tặng").setCellEditor(new DefaultCellEditor(chBox));
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 3) {
					if(!isEnabled()){
						return;
					}
					Profile profile = AccountModelManager.getInstance().getProfileConfigAdmin();

					if (!profile.get(DialogConfig.HuySP).toString().equals("true")) {
						return;
					}
					int r = rowAtPoint(e.getPoint());
					if (r >= 0 && r < getRowCount()) {
						setRowSelectionInterval(r, r);
						try {
							InvoiceItem invoiceItem = (InvoiceItem) getValueAt(getSelectedRow(), 1);
							if (!invoiceItem.getStatus().equals(AccountingModelManager.isRecord)) {
								if (invoiceItem.getQuantity() == 1) {
									invoiceItem.setStatus(AccountingModelManager.isCance);
								} else {
									InvoiceItem invoiceItem1 = new InvoiceItem();
									invoiceItem1.setActivityType(invoiceItem.getActivityType());
									invoiceItem1.setStartDate(invoiceItem.getStartDate());
									invoiceItem1.setItemName(invoiceItem.getItemName());
									invoiceItem1.setProductCode(invoiceItem.getProductCode());
									invoiceItem1.setItemCode(DateUtil.asCompactDateId(new Date()));
									invoiceItem1.setQuantity(1);
									invoiceItem1.setUnitPrice(invoiceItem.getUnitPrice());
									invoiceItem1.setTotal(invoiceItem1.getQuantity() * invoiceItem.getUnitPrice());
									invoiceItem1.setCurrencyUnit("VND");
									invoiceItem1.setStatus(AccountingModelManager.isCance);
									tableModel.getInvoiceDetail().add(invoiceItem1);
									double a = invoiceItem.getQuantity() - 1;
									invoiceItem.setQuantity(a);
								}
								tableModel.updateItem(invoiceItem);
								tableModel.updateStatus(AccountingModelManager.isCance);
							} else {
								if (invoiceItem.getQuantity() <= 1) {
									tableModel.removeRow(getSelectedRow());
								} else {
									double a = invoiceItem.getQuantity() - 1;
									invoiceItem.setQuantity(a);
									tableModel.updateItem(invoiceItem);
								}
							}

						} catch (Exception e2) {
						}
					}
				} else {
					if (e.getClickCount() >= 2) {
						InvoiceItem invoiceItem = (InvoiceItem) getValueAt(getSelectedRow(), 1);
						flagEditTable = true;
						try {
							Menu menu = PromotionModelManager.getInstance().getMenuByCode(invoiceItem.getProductCode());

							if (menu != null) {
								DialogChoiseMenuItem item = new DialogChoiseMenuItem(menu);
								item.setData(invoiceItem);
								DialogResto dialog = new DialogResto(item, false, 810, 500);
								dialog.setLocationRelativeTo(null);
								dialog.setModal(true);
								dialog.setTitle("Thêm sản phẩm tùy chọn");
								dialog.setVisible(true);

								tableModel.updateItem(item.getInvoiceItem());
							} else {
								if (getSelectedColumn() != 2 && getSelectedColumn() != 3 && getSelectedColumn() != 4) {
									try {
										PanelDescription panel = new PanelDescription(invoiceItem);
										DialogResto dialog = new DialogResto(panel, false, 0, 175);
										dialog.setTitle("Ghi chú");
										dialog.setLocationRelativeTo(null);
										dialog.setModal(true);
										dialog.setVisible(true);
										if (panel.isEdit()) {
											tableModel.updateItem(invoiceItem);
										}
									} catch (Exception e1) {
										e1.printStackTrace();
									}
								}
							}
						} catch (Exception e2) {
							e2.printStackTrace();
						}

					}

				}
			}

		});

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				try {
					if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
						tableModel.removeRow(getSelectedRow());
					}
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}
			}
		});
	}
}
