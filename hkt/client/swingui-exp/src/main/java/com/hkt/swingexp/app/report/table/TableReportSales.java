package com.hkt.swingexp.app.report.table;

import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.swingexp.app.banhang.ReportStatistics;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.module.accounting.entity.Invoice.ActivityType;
import com.hkt.module.core.entity.ReportTable;
import com.hkt.module.core.entity.SQLSelectQuery;
import com.hkt.swingexp.app.report.entity.ReportSalesEntity;
import com.hkt.swingexp.app.report.modeltable.TableModelReportSales;

/*
 * @author: Phan Anh
 * @date: 11/07/2014
 * @editDate: 24/12/2014
 * @editDate: 12/06/2015 - Thêm điều kiện loại bỏ phiếu sản xuất (line 98)
 */

public class TableReportSales extends JTable {
	private TableModelReportSales model;
	private String valueMoney;
	private HashMap<String, ReportSalesEntity> row_All;
	private ActivityType activityType;

	public TableReportSales(Date dateStart, Date dateEnd, String valueMoney, String warehouse, ActivityType activityType,
	    boolean showAll) {
		this.valueMoney = valueMoney;
		this.activityType = activityType;
		try {
			ReportStatistics.TypeReport typeReport = null;
			if (activityType.equals(ActivityType.Payment))
				typeReport = ReportStatistics.TypeReport.TKMuaHang;
			else
				typeReport = ReportStatistics.TypeReport.TKBanHang;
			model = new TableModelReportSales(typeReport, getRoot(dateStart, dateEnd, warehouse, activityType));
			this.setModel(model);
			for (int i = 0; i < this.getColumnCount(); i++) {
				this.getColumnModel()
				    .getColumn(i)
				    .setCellRenderer(
				        new TableRerenderSales(model, dateStart, dateEnd, valueMoney, warehouse, activityType, showAll, row_All));
				this.getColumnModel()
				    .getColumn(i)
				    .setCellEditor(
				        new TableRerenderSales(model, dateStart, dateEnd, valueMoney, warehouse, activityType, showAll, row_All));
			}
			this.getColumnModel().getColumn(0).setMinWidth(300);
			model.fireTableDataChanged();
			this.setModel(model);
		} catch (Exception e) {
			model = new TableModelReportSales(ReportStatistics.TypeReport.TKBanHang, new ArrayList<ReportSalesEntity>());
			this.setModel(model);
		}

		this.setRowHeight(23);
		this.setFont(new Font("Tahoma", 0, 14));
		this.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		this.getColumnModel().getColumn(0).setMinWidth(300);
		((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		this.setBackground(Color.WHITE);
		this.setFillsViewportHeight(true);
	}

	public void setReportGui(ReportStatistics reportStatistics) {
		model.setReportStatistics(reportStatistics);
	}

	private List<ReportSalesEntity> getRoot(Date startDate, Date endDate, String warehouse, ActivityType activityType)
	    throws Exception {
		String nameOrganization = AccountModelManager.getInstance().getNameByLoginId(
		    ManagerAuthenticate.getInstance().getOrganizationLoginId());
		if (startDate == null)
			startDate = new Date();
		if (endDate == null)
			endDate = new Date();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendarNow = Calendar.getInstance();
		calendarNow.set(Calendar.HOUR_OF_DAY, 23);
		calendarNow.set(Calendar.MINUTE, 59);
		calendarNow.set(Calendar.SECOND, 59);

		Calendar calendarStart = Calendar.getInstance();
		calendarStart.setTime(startDate);
		calendarStart.set(Calendar.HOUR_OF_DAY, 00);
		calendarStart.set(Calendar.MINUTE, 00);
		calendarStart.set(Calendar.SECOND, 00);

		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.setTime(endDate);
		calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
		calendarEnd.set(Calendar.MINUTE, 59);
		calendarEnd.set(Calendar.SECOND, 59);
		SQLSelectQuery rQuery4 = new SQLSelectQuery();

		rQuery4.table("InvoiceItem i JOIN Product p ON p.code = i.productCode")
		    .field("i.productCode AS `productCode`", "value0").field("COALESCE(SUM(i.quantity), 0) AS `SLDauKy`", "value1")
		    .field("COALESCE(SUM(i.finalCharge), 0) AS `value2`", "value2")
		    .cond("i.startDate >= '" + dateFormat.format(calendarStart.getTime()) + "'")
		    .cond("i.startDate <= '" + dateFormat.format(calendarEnd.getTime()) + "'")
		    .cond("i.type NOT LIKE '" + AccountingModelManager.typeSanXuat + "'")
		    .cond("i.type NOT LIKE '" + AccountingModelManager.typeTraHang + "'")
		    .cond("i.activityType='" + activityType + "'").groupBy("`productCode`");
		row_All = new HashMap<String, ReportSalesEntity>();

		List<ReportSalesEntity> listObjects = new ArrayList<ReportSalesEntity>();
		System.out.println(rQuery4.createSQLQuery());
		ReportTable[] reportTable = AccountingModelManager.getInstance().reportQuery(new SQLSelectQuery[] { rQuery4 });
		String[] field = new String[rQuery4.getFields().size()];
		for (int i = 0; i < rQuery4.getFields().size(); i++) {
			field[i] = "value" + i;
		}
		reportTable[0].dump(field);
		List<Map<String, Object>> records = reportTable[0].getRecords();
		double quantity = 0;
		double total = 0;
		HashMap<String, String[]> hashMap = getQuantityReturn(startDate, endDate);
		for (int i = 0; i < records.size(); i++) {
			Map<String, Object> record = records.get(i);
			Object[] values = new Object[field.length];
			for (int j = 0; j < field.length; j++) {
				values[j] = record.get(field[j]);
			}
			double returnQ = 0;
			try {
				returnQ = MyDouble.parseDouble(hashMap.get(values[0].toString())[0]);
			} catch (Exception e) {
				returnQ = 0;
			}
			double returnP = 0;
			try {
				returnP = MyDouble.parseDouble(hashMap.get(values[0].toString())[1]);
			} catch (Exception e) {
				returnP = 0;
			}
			quantity = quantity + MyDouble.parseDouble(values[1].toString()) - returnQ;
			total = total + MyDouble.parseDouble(values[2].toString()) - returnP;
			int rate = 1;
			if (valueMoney.equals("Nghìn")) {
				rate = 1000;
			} else if (valueMoney.equals("Triệu")) {
				rate = 1000000;
			}

			double q = MyDouble.parseDouble(values[1].toString()) - returnQ;
			double finalChare = (MyDouble.parseDouble(values[2].toString())-returnP) / rate;
			
			ReportSalesEntity reportRestaurantEntity = new ReportSalesEntity(nameOrganization, values[0].toString(),
			    new MyDouble(q).toString(), new MyDouble(finalChare).toString(), 0);
			row_All.put(values[0].toString(), reportRestaurantEntity);
		}
		int rate = 1;
		if (valueMoney.equals("Nghìn")) {
			rate = 1000;
		} else if (valueMoney.equals("Triệu")) {
			rate = 1000000;
		}
		double finalChare = total / rate;
		ReportSalesEntity reportRestaurantEntity = new ReportSalesEntity(nameOrganization, "",
		    new MyDouble(quantity).toString(), new MyDouble(finalChare).toString(), 0);
		listObjects.add(reportRestaurantEntity);

		return listObjects;
	}

	private HashMap<String, String[]> getQuantityReturn(Date startDate, Date endDate) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar calendarNow = Calendar.getInstance();
			calendarNow.set(Calendar.HOUR_OF_DAY, 23);
			calendarNow.set(Calendar.MINUTE, 59);
			calendarNow.set(Calendar.SECOND, 59);

			Calendar calendarStart = Calendar.getInstance();
			calendarStart.setTime(startDate);
			calendarStart.set(Calendar.HOUR_OF_DAY, 00);
			calendarStart.set(Calendar.MINUTE, 00);
			calendarStart.set(Calendar.SECOND, 00);

			Calendar calendarEnd = Calendar.getInstance();
			calendarEnd.setTime(endDate);
			calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
			calendarEnd.set(Calendar.MINUTE, 59);
			calendarEnd.set(Calendar.SECOND, 59);
			SQLSelectQuery rQuery4 = new SQLSelectQuery();
			ActivityType at;
			if (activityType.equals(ActivityType.Payment)) {
				at = ActivityType.Receipt;
			} else {
				at = ActivityType.Payment;
			}

			rQuery4.table("InvoiceItem i JOIN Product p ON p.code = i.productCode")
			    .field("i.productCode AS `productCode`", "value0")
			    .field("COALESCE(SUM(i.quantity), 0) AS `SLDauKy`", "value1")
			    .field("COALESCE(SUM(i.finalCharge), 0) AS `value2`", "value2")
			    .cond("i.startDate >= '" + dateFormat.format(calendarStart.getTime()) + "'")
			    .cond("i.startDate <= '" + dateFormat.format(calendarEnd.getTime()) + "'")
			    .cond("i.type = '" + AccountingModelManager.typeTraHang + "'").cond("i.activityType='" + at + "'")
			    .groupBy("`productCode`");
			System.out.println(rQuery4.createSQLQuery());
			ReportTable[] reportTable = AccountingModelManager.getInstance().reportQuery(new SQLSelectQuery[] { rQuery4 });
			String[] field = new String[rQuery4.getFields().size()];
			for (int i = 0; i < rQuery4.getFields().size(); i++) {
				field[i] = "value" + i;
			}
			reportTable[0].dump(field);
			List<Map<String, Object>> records = reportTable[0].getRecords();
			HashMap<String, String[]> hashMap = new HashMap<String, String[]>();
			for (int i = 0; i < records.size(); i++) {
				Map<String, Object> record = records.get(i);
				Object[] values = new Object[field.length];
				String[] strs = new String[2];
				for (int j = 0; j < field.length; j++) {
					values[j] = record.get(field[j]);
				}
				strs[0] = values[1].toString();
				strs[1] = values[2].toString();
				hashMap.put(values[0].toString(), strs);
			}
			return hashMap;
		} catch (Exception e) {
			e.printStackTrace();
			return new HashMap<String, String[]>();
		}

	}
}
