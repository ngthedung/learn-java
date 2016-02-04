package com.hkt.client.swingexp.app.banhang.screen.often;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.module.accounting.DefaultInvoiceCalculator;
import com.hkt.module.accounting.entity.Invoice;
import com.hkt.module.accounting.entity.Invoice.Status;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.restaurant.entity.Reservation;
import com.hkt.module.restaurant.entity.Table;
import com.hkt.util.text.DateUtil;

public class PanelTableGroup extends JPanel implements IDialogResto {
	private ExtendJLabel			lblMessageGrossTable, lblSelectTableGross;
	private ExtendJComboBox		cbSelectTableGross;
	private List<Table>				tables;
	private Reservation				reservation;
	private List<Reservation>	reservations;
	private Table							tableGross;
	private boolean						isSave;

	public boolean isSave() {
		return isSave;
	}

	public void setSave(boolean isSave) {
		this.isSave = isSave;
	}

	public PanelTableGroup(List<Table> listTables) {
		this.tables = listTables;

		lblMessageGrossTable = new ExtendJLabel("");
		lblSelectTableGross = new ExtendJLabel("Gộp chung vào bàn/quầy");
		cbSelectTableGross = new ExtendJComboBox();

		String messageGrossTable = "Bàn muốn gộp các bàn: ";
		for (Table t : tables) {
			messageGrossTable = messageGrossTable + " " + t.getName();
		}

		lblMessageGrossTable.setText(messageGrossTable);
		cbSelectTableGross.setModel(new DefaultComboBoxModel(tables.toArray()));

		init();
	}

	public void init() {
		setOpaque(false);
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup().addComponent(lblSelectTableGross).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(cbSelectTableGross, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup().addGap(0, 0, 0).addComponent(lblMessageGrossTable, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblMessageGrossTable, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lblSelectTableGross, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(cbSelectTableGross, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
	}

	@Override
	public void Save() throws Exception {
		if (tables.size() > 0) {
//			boolean flag = false;
			reservations = new ArrayList<Reservation>();
			tableGross = ((Table) cbSelectTableGross.getSelectedItem());
			for (Table t : tables) {
				if (t.getId().equals(tableGross.getId())) {
					t.setStatus(Table.Status.TableGross);
				} else {
					reservation = new Reservation();
					reservation.setDescription(t.getCode());
					reservations.add(reservation);
					t.setStatus(Table.Status.InActivate);
					RestaurantModelManager.getInstance().saveTable(t);
				}
//				if(t.getInvoiceCode() != null && !t.getInvoiceCode().equals("")){
//					flag = true;
//				}
			}
			tableGross.setReservations(reservations);
			RestaurantModelManager.getInstance().saveTable(tableGross);
//			if(flag)
				grossItemTable();
			setSave(true);
			((JDialog) getRootPane().getParent()).dispose();
		}
	}

	public void grossItemTable() throws Exception {
		tableGross = ((Table) cbSelectTableGross.getSelectedItem());
		InvoiceDetail invoiceDetail = AccountingModelManager.getInstance().getInvoiceDetail(tableGross.invoiceId());
		if (invoiceDetail == null) {
			invoiceDetail = new InvoiceDetail(true);
			invoiceDetail.setInvoiceCode(DateUtil.asCompactDateTimeId(new Date()));
			invoiceDetail.setActivityType(Invoice.ActivityType.Receipt);
			invoiceDetail.setStatus(Status.WaitingPaid);
			invoiceDetail.setType(AccountingModelManager.typeBanHang);
			invoiceDetail.setInvoiceName(tableGross.getName());
			invoiceDetail.setCurrencyUnit("VND");
			invoiceDetail.setDiscount(0);
			invoiceDetail.setStartDate(new Date());
			invoiceDetail.setTableCode(tableGross.getCode());
			invoiceDetail.setLocationCode(tableGross.getLocationCode());
		}

		if (invoiceDetail != null) {
			for (Table t : tables) {
				if (!t.getCode().equals(tableGross.getCode())) {
					InvoiceDetail invoiceTableChild = AccountingModelManager.getInstance().getInvoiceDetail(t.invoiceId());
					
					if (invoiceTableChild != null) {
						List<InvoiceItem> invoiceTableChildItems = invoiceTableChild.getItems();
						System.out.println(invoiceTableChildItems+".............");
						for (int i = 0; i < invoiceTableChildItems.size(); i++) {
							InvoiceItem in = new InvoiceItem();
							in.setItemCode(invoiceTableChildItems.get(i).getItemCode());
							in.setItemName(invoiceTableChildItems.get(i).getItemName());
							in.setUnitPrice(invoiceTableChildItems.get(i).getUnitPrice());
							in.setTotal(invoiceTableChildItems.get(i).getTotal());
							in.setPoint(invoiceTableChildItems.get(i).getPoint());
							in.setQuantity(invoiceTableChildItems.get(i).getQuantity());
							in.setStatus(invoiceTableChildItems.get(i).getStatus());
							in.setCurrencyUnit(invoiceTableChildItems.get(i).getCurrencyUnit());
							in.setStartDate(invoiceTableChildItems.get(i).getStartDate());
							in.setActivityType(invoiceTableChildItems.get(i).getActivityType());
							in.setProductCode(invoiceTableChildItems.get(i).getProductCode());
							invoiceDetail.add(in);
						}
						AccountingModelManager.getInstance().deleteInvoice(invoiceTableChild);
						t.setInvoiceCode("");
						RestaurantModelManager.getInstance().saveTable(t);
					}
				}
			}
			System.out.println(invoiceDetail.getItems()+"   g");
			invoiceDetail = invoiceDetail.calculate(new DefaultInvoiceCalculator());
			invoiceDetail = AccountingModelManager.getInstance().saveInvoiceDetail(invoiceDetail);
			System.out.println(invoiceDetail.getItems()+"     h");
			tableGross.setInvoiceCode(String.valueOf(invoiceDetail.getId()));
			RestaurantModelManager.getInstance().saveTable(tableGross);
		}
	}

	public Table getTableGross() {
		return tableGross;
	}

}
