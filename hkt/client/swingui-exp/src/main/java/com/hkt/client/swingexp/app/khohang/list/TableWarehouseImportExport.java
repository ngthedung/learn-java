package com.hkt.client.swingexp.app.khohang.list;

import java.awt.Font;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.banhang.screen.often.DialogConfig;
import com.hkt.client.swingexp.app.banhang.screen.pos.DialogPhieuDatMuaHangPos;
import com.hkt.client.swingexp.app.banhang.screen.pos.DialogScreenOftenPos;
import com.hkt.client.swingexp.app.core.DialogContain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.ITableMainExt;
import com.hkt.client.swingexp.app.core.TableObservable;
import com.hkt.client.swingexp.app.thuchi.PanelPhieuDatMuaHang;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.accounting.entity.Invoice;
import com.hkt.module.accounting.entity.Invoice.ActivityType;
import com.hkt.module.accounting.entity.Invoice.Status;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterQuery.Operator;

@SuppressWarnings("serial")
public class TableWarehouseImportExport extends TableObservable implements ITableMainExt {
	private TableModelImportExport modelThuChi;
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
	private List<Invoice> invoices;
	private Date start, end;
	@SuppressWarnings("unused")
	private String currency;
	private ActivityType activityType;
	public String warehouse;

	public TableWarehouseImportExport(Date start, Date end, String warehouseCode, ActivityType activityType) {

		this.start = start;
		this.end = end;
		this.warehouse = warehouseCode;
		this.activityType = activityType;
		invoices = new ArrayList<Invoice>();
		modelThuChi = new TableModelImportExport(invoices);
		setModel(modelThuChi);

		Calendar c = Calendar.getInstance();
		c.setTime(start);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 01);
		Calendar c1 = Calendar.getInstance();
		c1.setTime(end);
		c1.set(Calendar.HOUR_OF_DAY, 23);
		c1.set(Calendar.MINUTE, 59);
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setFont(new Font("Tahoma", 1, 14));
		String dateStart = df.format(c.getTime());
		String dateEnd = df.format(c1.getTime());
		FilterQuery filterQuery = new FilterQuery();

		filterQuery.add("type", FilterQuery.Operator.STRINGEQ, AccountingModelManager.typeSanXuat);
		try {
			filterQuery.add("startDate", FilterQuery.Operator.GT, "'" + dateStart + "'");
			filterQuery.add("startDate", FilterQuery.Operator.LT, "'" + dateEnd + "'");
			if (warehouse != null) {
				filterQuery.add("warehouseCode", FilterQuery.Operator.STRINGEQ, warehouse);
			}
			if (activityType != null) {
				filterQuery.add("activityType", Operator.STRINGEQ, activityType);
			}
			invoices = AccountingModelManager.getInstance().searchInvoice(filterQuery);
		} catch (Exception e) {
			e.printStackTrace();
			invoices = new ArrayList<Invoice>();
		}
	}

	@Override
	public void click() {
		Invoice invoice = (Invoice) getValueAt(getSelectedRow(), 2);
		InvoiceDetail invoiceDetail = AccountingModelManager.getInstance().getInvoiceDetail(invoice.getId());
		int statusInvoice = 0;
		String name = "PDH";
		System.out.println(invoiceDetail.getType());
		if (invoiceDetail.getType().equals(AccountingModelManager.typeDatHang)
		    && invoiceDetail.getActivityType().equals(ActivityType.Receipt)) {
			name = "KDH";
		}
		if (invoiceDetail.getType().equals(AccountingModelManager.typeTraHang)) {
			if (invoiceDetail.getActivityType().equals(ActivityType.Receipt)) {
				name = "Khách trả hàng";
			} else {
				name = "Phiếu trả hàng";
			}
		}
		try {
			List<InvoiceItem> lstItems = invoiceDetail.getItems();
			if (lstItems.size() == 0 && invoiceDetail.getStatus() == Status.PostPaid) {
				statusInvoice = PanelPhieuDatMuaHang.UNPAID_AND_NOTIMPORT;
			}
			if (lstItems.size() == 0 && invoiceDetail.getStatus() == Status.Paid) {
				statusInvoice = PanelPhieuDatMuaHang.PAID_AND_NOTIMPORT;
			}
			if (lstItems.size() > 0 && invoiceDetail.getStatus() == Status.PostPaid) {
				statusInvoice = PanelPhieuDatMuaHang.UNPAID;
			}
			if (lstItems.size() > 0 && invoiceDetail.getStatus() == Status.Paid) {
				statusInvoice = PanelPhieuDatMuaHang.PAID;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Profile profile = AccountModelManager.getInstance().getProfileConfigAdmin();
		// if (profile.get(DialogConfig.GDPos).toString().equals("true")) {
		// DialogPhieuDatMuaHangPos panel = DialogPhieuDatMuaHangPos.getInstance();
		// if(DialogScreenOftenPos.isFlagScreenOften()){
		// DialogScreenOftenPos.setFlagScreenOften(false);
		// DialogScreenOftenPos pos = DialogScreenOftenPos.getInstance();
		// panel.getPanelTop2().add(pos.getPanelProductTagRoot(), 1);
		// panel.getScrollPane_Product().setViewportView(pos.getPanelProducts());
		// }
		// try {
		// panel.setTypeInvoice(invoiceDetail, statusInvoice, name);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// panel.setVisible(true);
		// } else {
		PanelPhieuDatMuaHang panel = new PanelPhieuDatMuaHang(invoiceDetail, statusInvoice, name);
		panel.setName("pnPhieuDatMuaHang");
		try {
			profile = AccountModelManager.getInstance().getProfileConfigAdmin();
			if (profile.get(DialogConfig.Keyboard) != null && profile.get(DialogConfig.Keyboard).toString().equals("true")) {
				DialogResto dialog = new DialogResto(panel, true, 0, 0);
				dialog.setTitle("Khách trả hàng");
				dialog.setIcon("tra1.png");
				dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
				dialog.dispose();
				dialog.setUndecorated(true);
				dialog.setLocationRelativeTo(null);
				dialog.setBtnSave(false);
				dialog.setBtnExit(false);
				dialog.setModal(true);
				dialog.setVisible(true);
			} else {
				DialogContain dialog1 = new DialogContain(panel);
				dialog1.getContentPane().add(panel);
				dialog1.setName("dlPhieuDatMuaHang");
				dialog1.setSize(Toolkit.getDefaultToolkit().getScreenSize());
				dialog1.dispose();
				dialog1.setUndecorated(true);
				dialog1.setLocationRelativeTo(null);
				dialog1.setModal(true);
				dialog1.setVisible(true);
			}

		} catch (Exception e1) {
		}
		// }

		try {
			invoiceDetail = AccountingModelManager.getInstance().getInvoiceDetail(invoiceDetail.getId());
			if (invoiceDetail == null) {
				modelThuChi.removeRow(this.getSelectedRow());
			} else {
				modelThuChi.fireTableRowsUpdated(this.getSelectedRow(), this.getSelectedRow());
			}
		} catch (Exception e) {
		}
	}

	@Override
	public int getListSize() {
		return invoices.size();
	}

	@Override
	public DefaultTableModel loadTable(int index, int pageSize) {
		try {
			modelThuChi.setData(invoices.subList(index, pageSize), index);
		} catch (Exception e) {
			modelThuChi = new TableModelImportExport(new ArrayList<Invoice>());
		}

		return modelThuChi;
	}

	@Override
	public List<Integer> getColumnSum() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(4);
		return list;
	}

	@Override
	public boolean delete() {
		return false;
	}

	@Override
	public boolean isDelete() {
		return false;
	}

	@Override
	public void loadDataByTime(Date startDate, Date endDate) {

		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 01);
		Calendar c1 = Calendar.getInstance();
		c1.setTime(endDate);
		c1.set(Calendar.HOUR_OF_DAY, 23);
		c1.set(Calendar.MINUTE, 59);
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setFont(new Font("Tahoma", 1, 14));
		String dateStart = df.format(c.getTime());
		String dateEnd = df.format(c1.getTime());
		FilterQuery filterQuery = new FilterQuery();
		filterQuery.add("type", FilterQuery.Operator.STRINGEQ, AccountingModelManager.typeSanXuat);
		try {
			filterQuery.add("startDate", FilterQuery.Operator.GT, "'" + dateStart + "'");
			filterQuery.add("startDate", FilterQuery.Operator.LT, "'" + dateEnd + "'");
			if (warehouse != null) {
				filterQuery.add("warehouseCode", FilterQuery.Operator.STRINGEQ, warehouse);
			}
			if (activityType != null) {
				filterQuery.add("activityType", Operator.STRINGEQ, activityType);
			}
			invoices = AccountingModelManager.getInstance().searchInvoice(filterQuery);
		} catch (Exception e) {
			e.printStackTrace();
			invoices = new ArrayList<Invoice>();
		}
		change();
	}

	@Override
	public Date getDateStart() {
		return start;

	}

	@Override
	public Date getDateEnd() {

		return end;
	}
}
