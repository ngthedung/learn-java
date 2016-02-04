package com.hkt.client.swingexp.app.khohang.list;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.ITableMainExt;
import com.hkt.client.swingexp.app.core.TableObservable;
import com.hkt.client.swingexp.app.khohang.PanelPatternProduction;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.module.accounting.entity.Invoice.ActivityType;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.core.entity.FilterQuery;

@SuppressWarnings("serial")
public class TablePatternProduction extends TableObservable implements ITableMainExt {
	private TableModelPatternProduction	modelTable;
	private List<InvoiceDetail>					invoices;
	private Date start, end;

	public TablePatternProduction(Date start, Date end) {
		
		this.start = start;
		this.end = end;
		Calendar dateStart = Calendar.getInstance();
		dateStart.setTime(start);
		dateStart.set(Calendar.HOUR_OF_DAY, 00);
		dateStart.set(Calendar.MINUTE, 00);
		dateStart.set(Calendar.SECOND, 00);
		Calendar dateEnd = Calendar.getInstance();
		dateEnd.setTime(end);
		dateEnd.set(Calendar.HOUR_OF_DAY, 23);
		dateEnd.set(Calendar.MINUTE, 59);
		dateEnd.set(Calendar.SECOND, 59);

		try {
			invoices = getPatternProductions(dateStart.getTime(), dateEnd.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			invoices = new ArrayList<InvoiceDetail>();
		} finally {
			modelTable = new TableModelPatternProduction(invoices);
			this.setModel(modelTable);
		}
	}

	@Override
	public void click() {
		try {
			InvoiceDetail invoiceName = (InvoiceDetail)this.getValueAt(this.getSelectedRow(), 2);
			PanelPatternProduction panel = new PanelPatternProduction(invoiceName);
			DialogResto dialog = new DialogResto(panel, true, 600, 150);
			dialog.setBtnExt1(panel.getButtonEdit());
			dialog.setBtnExt2(panel.getButtonDelete());
			dialog.setIcon("thuchi1.png");
			dialog.dispose();
			dialog.setUndecorated(true);
			dialog.setModal(true);
			dialog.setTitle("Phiếu sản xuất");
			dialog.setName("dlPhieuSanXuat");
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		loadDataByTime(start, end);
	}

	@Override
	public int getListSize() {
		return invoices.size();
	}

	@Override
	public DefaultTableModel loadTable(int index, int pageSize) {
		try {
			modelTable.setData(invoices.subList(index, pageSize), index);
		} catch (Exception e) {
			modelTable = new TableModelPatternProduction(new ArrayList<InvoiceDetail>());
		}
		return modelTable;
	}

	@Override
	public List<Integer> getColumnSum() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(4);
		return list;
	}

	private List<InvoiceDetail> getPatternProductions(Date start, Date end) {
		FilterQuery filterQuery = new FilterQuery();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
		try {
			filterQuery.add("invoice.startDate", FilterQuery.Operator.GT, "'" + df.format(start) + "'");
			filterQuery.add("invoice.startDate", FilterQuery.Operator.LT, "'" + df.format(end) + "'");
			filterQuery.add("invoice.type", FilterQuery.Operator.EQUAL, "'" + AccountingModelManager.typeSanXuat + "'");
			filterQuery.add("invoice.note", FilterQuery.Operator.EQUAL, "'" + AccountingModelManager.typeSanXuat + "'");
			filterQuery.add("invoice.activityType", FilterQuery.Operator.EQUAL, "'" + ActivityType.Payment + "'");
			invoices = AccountingModelManager.getInstance().searchInvoiceDetail(filterQuery);
			System.out.println(invoices);
		} catch (Exception e1) {
			invoices = new ArrayList<InvoiceDetail>();
			e1.printStackTrace();
		}
		return invoices;
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
		
		this.start = startDate;
		this.end = endDate;
		Calendar dateStart = Calendar.getInstance();
		dateStart.setTime(startDate);
		dateStart.set(Calendar.HOUR_OF_DAY, 00);
		dateStart.set(Calendar.MINUTE, 00);
		dateStart.set(Calendar.SECOND, 00);
		Calendar dateEnd = Calendar.getInstance();
		dateEnd.setTime(endDate);
		dateEnd.set(Calendar.HOUR_OF_DAY, 23);
		dateEnd.set(Calendar.MINUTE, 59);
		dateEnd.set(Calendar.SECOND, 59);
		
		try {
			invoices = getPatternProductions(dateStart.getTime(), dateEnd.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			invoices = new ArrayList<InvoiceDetail>();
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
