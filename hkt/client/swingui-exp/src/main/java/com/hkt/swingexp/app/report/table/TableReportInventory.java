package com.hkt.swingexp.app.report.table;

import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.AccountingServiceClient;
import com.hkt.client.swingexp.app.banhang.ReportStatistics;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.WarehouseModelManager;
import com.hkt.module.core.entity.ReportTable;
import com.hkt.module.core.entity.SQLSelectQuery;
import com.hkt.module.warehouse.entity.WarehouseInventory;
import com.hkt.swingexp.app.report.entity.InventoryReportEntity;
import com.hkt.swingexp.app.report.modeltable.TableModelReportInventory;

/**
 * @author Phan Anh
 * @date 11/07/2014
 * @edit 23/01/2015
 */

public class TableReportInventory extends JTable {
	private TableModelReportInventory				model;
	private static ClientContext						clientContext						= ClientContext.getInstance();
	private static AccountingServiceClient	accountingServiceClient	= clientContext.getBean(AccountingServiceClient.class);
	
	public TableReportInventory(String isInventory, Date startDate, Date endDate, String valueMoney, String warehouse, boolean showAll) {
		try {
			model = new TableModelReportInventory(getRoot(isInventory, startDate, endDate, valueMoney, warehouse, showAll));
			this.setModel(model);
			for (int i = 0; i < this.getColumnCount(); i++) {
				if (isInventory.equals("true")) {
					this.setModel(model);
					this.getColumnModel().getColumn(i).setCellRenderer(new TableRerenderInventoryReport(model, startDate, endDate, valueMoney, warehouse, showAll));
					this.getColumnModel().getColumn(i).setCellEditor(new TableRerenderInventoryReport(model, startDate, endDate, valueMoney, warehouse, showAll));
				} else {
					this.setModel(model);
					this.getColumnModel().getColumn(i).setCellRenderer(new TableRerenderQuantitativeReport(model, startDate, endDate, valueMoney, warehouse, showAll));
					this.getColumnModel().getColumn(i).setCellEditor(new TableRerenderQuantitativeReport(model, startDate, endDate, valueMoney, warehouse, showAll));
				}
			}
			this.getColumnModel().getColumn(0).setMinWidth(300);
			model.fireTableDataChanged();
			this.setModel(model);
		} catch (Exception e) {
			e.printStackTrace();
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
	
	private List<InventoryReportEntity> getRoot(String isInventory, Date startDate, Date endDate, String valueMoney, String warehouse, boolean showAll) throws Exception {
		String nameOrganization = AccountModelManager.getInstance().getNameByLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
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
		
		SQLSelectQuery rQuery1 = new SQLSelectQuery();
		SQLSelectQuery rQuery2 = new SQLSelectQuery();
		SQLSelectQuery rQuery3 = new SQLSelectQuery();
		
		List<InventoryReportEntity> objects1 = null;
		List<InventoryReportEntity> objects2 = null;
		List<InventoryReportEntity> objects3 = null;
		
		/*********************************************
		 *********************************************
		 ************ [THỐNG KÊ TỒN KHO] *************
		 *********************************************
		 *********************************************
		 */
		if (isInventory.equals("true")) {
			/***********************/
			/** Thống kê kho tổng */
			/*********************/
			if (warehouse == null) {
				//Lấy giá trị và số lượng đầu kỳ
				rQuery1.table("WarehouseInventory i JOIN Product p ON p.code = i.productCode LEFT JOIN ProductAttribute a ON a.productId = p.id")
						.field("COALESCE(SUM(i.increaseQuantity-i.reductionQuantity), 0) AS `SLDauKy`", "value0")
						.field("COALESCE(SUM(i.increaseValue-i.reductionValue), 0) AS `GTDauKy`", "value1")
						.cond("i.startDate <= '" + dateFormat.format(calendarStart.getTime()) + "'")
						.cond("a.name = '" + AccountingModelManager.product + "'")
						.cond("p.isCalculateReport = 1");
				
				//Lấy giá trị, số lượng tăng giảm trong kỳ
				rQuery2.table("WarehouseInventory i JOIN Product p ON p.code = i.productCode LEFT JOIN ProductAttribute a ON a.productId = p.id")
						.field("COALESCE(SUM(i.increaseQuantity), 0) AS `SLTrongKyTang`", "value0")
						.field("COALESCE(SUM(i.increaseValue), 0) AS `GTTrongKyTang`", "value1")
						.field("COALESCE(SUM(i.reductionQuantity), 0) AS `SLTrongKyGiam`", "value2")
						.field("COALESCE(SUM(i.reductionValue), 0) AS `GTTrongKyGiam`", "value3")
						.cond("i.startDate >= '" + dateFormat.format(calendarStart.getTime()) + "'")
						.cond("i.startDate <= '" + dateFormat.format(calendarEnd.getTime()) + "'")
						.cond("a.name = '" + AccountingModelManager.product + "'")
						.cond("p.isCalculateReport = 1");
				
				//lấy số lượng và giá trị còn tại
				rQuery3.table("WarehouseInventory i JOIN Product p ON p.code = i.productCode LEFT JOIN ProductAttribute a ON a.productId = p.id")
						.field("COALESCE(SUM(i.increaseQuantity-i.reductionQuantity), 0) AS `SLConLai`", "value0")
						.field("COALESCE(SUM(i.increaseValue-i.reductionValue), 0) AS `GTConLai`", "value1")
						.cond("i.startDate >= '" + dateFormat.format(calendarEnd.getTime()) + "'")
						.cond("i.startDate <= '" + dateFormat.format(calendarNow.getTime()) + "'")
						.cond("a.name = '" + AccountingModelManager.product + "'")
						.cond("p.isCalculateReport = 1");
				
				objects1 = runQuery(rQuery1);
				objects2 = runQuery(rQuery2);
				objects3 = runQuery(rQuery3);
			}
			/*********************/
			/** Thống kê kho lẻ */
			/*******************/
			else {
				//Lấy giá trị và số lượng đầu kỳ
				rQuery1.table("WarehouseProfile i JOIN Product p ON p.code = i.productCode LEFT JOIN ProductAttribute a ON a.productId = p.id")
						.field("COALESCE(SUM(i.increaseQuantity-i.reductionQuantity), 0) AS `SLDauKy`", "value0")
						.field("COALESCE(SUM(i.increaseValue-i.reductionValue), 0) AS `GTDauKy`", "value1")
						.cond("i.warehouseCode = '" + warehouse + "'")
						.cond("i.startDate <= '" + dateFormat.format(calendarStart.getTime()) + "'")
						.cond("a.name = '" + AccountingModelManager.product + "'")
						.cond("p.isCalculateReport = 1");
				
				//Lấy giá trị, số lượng tăng giảm trong kỳ
				rQuery2.table("WarehouseProfile i JOIN Product p ON p.code = i.productCode LEFT JOIN ProductAttribute a ON a.productId = p.id")
						.field("COALESCE(SUM(i.increaseQuantity), 0) AS `SLTrongKyTang`", "value0")
						.field("COALESCE(SUM(i.increaseValue), 0) AS `GTTrongKyTang`", "value1")
						.field("COALESCE(SUM(i.reductionQuantity), 0) AS `SLTrongKyGiam`", "value2")
						.field("COALESCE(SUM(i.reductionValue), 0) AS `GTTrongKyGiam`", "value3")
						.cond("i.warehouseCode = '" + warehouse + "'")
						.cond("i.startDate >= '" + dateFormat.format(calendarStart.getTime()) + "'")
						.cond("i.startDate <= '" + dateFormat.format(calendarEnd.getTime()) + "'")
						.cond("a.name = '" + AccountingModelManager.product + "'")
						.cond("p.isCalculateReport = 1");
				
				//lấy số lượng và giá trị còn tại
				rQuery3.table("WarehouseProfile i JOIN Product p ON p.code = i.productCode LEFT JOIN ProductAttribute a ON a.productId = p.id")
						.field("COALESCE(SUM(i.increaseQuantity-i.reductionQuantity), 0) AS `SLConLai`", "value0")
						.field("COALESCE(SUM(i.increaseValue-i.reductionValue), 0) AS `GTConLai`", "value1")
						.cond("i.warehouseCode = '" + warehouse + "'").cond("i.startDate >= '" + dateFormat.format(calendarEnd.getTime()) + "'")
						.cond("i.startDate <= '" + dateFormat.format(calendarNow.getTime()) + "'")
						.cond("a.name = '" + AccountingModelManager.product + "'")
						.cond("p.isCalculateReport = 1");
				
				objects1 = runQuery(rQuery1);
				objects2 = runQuery(rQuery2);
				objects3 = runQuery(rQuery3);
			}
		}
		
		/*********************************************
		 *********************************************
		 ********* [THỐNG KÊ ĐỊNH LƯỢNG] *************
		 *********************************************
		 *********************************************
		 */
		else {
			/**********************************/
			/** Thống kê định lượng kho tổng */
			/********************************/
			if (warehouse == null) {
				//Lấy giá trị và số lượng đầu kỳ
				rQuery1.table("WarehouseInventory i JOIN Product p ON p.code = i.productCode LEFT JOIN ProductAttribute a ON a.productId = p.id")
						.field("COALESCE(SUM(i.increaseQuantity-i.reductionVitualQuantity), 0) AS `SLDauKy`", "value0")
						.field("COALESCE(SUM(i.increaseValue-i.reductionVitualValue), 0) AS `GTDauKy`", "value1")
						.cond("i.startDate <= '" + dateFormat.format(calendarStart.getTime()) + "'")
						.cond("a.name = '" + AccountingModelManager.product + "'")
						.cond("p.isCalculateReport = 1");
				
				//Lấy giá trị, số lượng tăng giảm trong kỳ
				rQuery2.table("WarehouseInventory i JOIN Product p ON p.code = i.productCode LEFT JOIN ProductAttribute a ON a.productId = p.id")
						.field("COALESCE(SUM(i.increaseQuantity), 0) AS `SLTrongKyTang`", "value0")
						.field("COALESCE(SUM(i.increaseValue), 0) AS `GTTrongKyTang`", "value1")
						.field("COALESCE(SUM(i.reductionVitualQuantity), 0) AS `SLTrongKyGiam`", "value2")
						.field("COALESCE(SUM(i.reductionVitualValue), 0) AS `GTTrongKyGiam`", "value3")
						.cond("i.startDate >= '" + dateFormat.format(calendarStart.getTime()) + "'")
						.cond("i.startDate <= '" + dateFormat.format(calendarEnd.getTime()) + "'")
						.cond("a.name = '" + AccountingModelManager.product + "'")
						.cond("p.isCalculateReport = 1");
				
				//Lấy số lượng và giá trị còn tại
				rQuery3.table("WarehouseInventory i JOIN Product p ON p.code = i.productCode LEFT JOIN ProductAttribute a ON a.productId = p.id")
						.field("COALESCE(SUM(i.increaseQuantity-i.reductionVitualQuantity), 0) AS `SLConLai`", "value0")
						.field("COALESCE(SUM(i.increaseValue-i.reductionVitualValue), 0) AS `GTConLai`", "value1")
						.cond("i.startDate >= '" + dateFormat.format(calendarEnd.getTime()) + "'")
						.cond("i.startDate <= '" + dateFormat.format(calendarNow.getTime()) + "'")
						.cond("a.name = '" + AccountingModelManager.product + "'")
						.cond("p.isCalculateReport = 1");
				
				objects1 = runQuery(rQuery1);
				objects2 = runQuery(rQuery2);
				objects3 = runQuery(rQuery3);
			}
			/****************************/
			/** Thống kê định lượng lẻ */
			/**************************/
			else {
				//Lấy giá trị và số lượng đầu kỳ
				rQuery1.table("WarehouseProfile i  JOIN Product p ON p.code = i.productCode LEFT JOIN ProductAttribute a ON a.productId = p.id")
						.field("COALESCE(SUM(i.increaseQuantity-i.reductionVitualQuantity), 0) AS `SLDauKy`", "value0")
						.field("COALESCE(SUM(i.increaseValue-i.reductionVitualValue), 0) AS `GTDauKy`", "value1")
						.cond("i.warehouseCode = '" + warehouse + "'")
						.cond("i.startDate <= '" + dateFormat.format(calendarStart.getTime()) + "'")
						.cond("a.name = '" + AccountingModelManager.product + "'")
						.cond("p.isCalculateReport = 1");
				
				//Lấy giá trị, số lượng tăng giảm trong kỳ
				rQuery2.table("WarehouseProfile i JOIN Product p ON p.code = i.productCode LEFT JOIN ProductAttribute a ON a.productId = p.id")
						.field("COALESCE(SUM(i.increaseQuantity), 0) AS `SLTrongKyTang`", "value0")
						.field("COALESCE(SUM(i.increaseValue), 0) AS `GTTrongKyTang`", "value1")
						.field("COALESCE(SUM(i.reductionVitualQuantity), 0) AS `SLTrongKyGiam`", "value2")
						.field("COALESCE(SUM(i.reductionVitualValue), 0) AS `GTTrongKyGiam`", "value3")
						.cond("i.warehouseCode = '" + warehouse + "'")
						.cond("i.startDate >= '" + dateFormat.format(calendarStart.getTime()) + "'")
						.cond("i.startDate <= '" + dateFormat.format(calendarEnd.getTime()) + "'")
						.cond("a.name = '" + AccountingModelManager.product + "'")
						.cond("p.isCalculateReport = 1");
				
				//Lấy số lượng và giá trị còn tại
				rQuery3.table("WarehouseProfile i JOIN Product p ON p.code = i.productCode LEFT JOIN ProductAttribute a ON a.productId = p.id")
						.field("COALESCE(SUM(i.increaseQuantity-i.reductionVitualQuantity), 0) AS `SLConLai`", "value0")
						.field("COALESCE(SUM(i.increaseValue-i.reductionVitualValue), 0) AS `GTConLai`", "value1")
						.cond("i.warehouseCode = '" + warehouse + "'")
						.cond("i.startDate >= '" + dateFormat.format(calendarEnd.getTime()) + "'")
						.cond("i.startDate <= '" + dateFormat.format(calendarNow.getTime()) + "'")
						.cond("a.name = '" + AccountingModelManager.product + "'")
						.cond("p.isCalculateReport = 1");
				
				objects1 = runQuery(rQuery1);
				objects2 = runQuery(rQuery2);
				objects3 = runQuery(rQuery3);
			}
		}
		
		List<InventoryReportEntity> rows = new ArrayList<InventoryReportEntity>();
		
		int rate = 1;
		double SLDauKy = 0;
		double GTDauKy = 0;
		double SLTang = 0;
		double SLGiam = 0;
		double GTTang = 0;
		double GTGiam = 0;
		double SLConLai = 0;
		double GTConLai = 0;
		
		if (objects1.size() != 0) {
			SLDauKy = Double.parseDouble(objects1.get(0).getCol1_DanhMuc());
			GTDauKy = Double.parseDouble(objects1.get(0).getCol2_SLDauKy());
		}
		if (objects2.size() != 0) {
			SLTang = Double.parseDouble(objects2.get(0).getCol1_DanhMuc());
			GTTang = Double.parseDouble(objects2.get(0).getCol2_SLDauKy());
			SLGiam = Double.parseDouble(objects2.get(0).getCol3_GTDauKy());
			GTGiam = Double.parseDouble(objects2.get(0).getCol4_SLTang());
			
		}
		if (objects3.size() != 0) {
			SLConLai = Double.parseDouble(objects3.get(0).getCol1_DanhMuc());
			GTConLai = Double.parseDouble(objects3.get(0).getCol2_SLDauKy());
		}
		
		if (valueMoney.equals("Nghìn")) {
			rate = 1000;
		} else if (valueMoney.equals("Triệu")) {
			rate = 1000000;
		}
		
		GTDauKy = GTDauKy / rate;
		GTTang = GTTang / rate;
		GTGiam = GTGiam / rate;
		GTConLai = GTConLai / rate;
		
		InventoryReportEntity row = new InventoryReportEntity(nameOrganization, MyDouble.valueOf(SLDauKy).toString(), MyDouble.valueOf(GTDauKy).toString(), MyDouble.valueOf(SLTang).toString(), MyDouble.valueOf(GTTang).toString(), MyDouble.valueOf(SLGiam).toString(), MyDouble.valueOf(GTGiam).toString(),
				MyDouble.valueOf(SLConLai).toString(), MyDouble.valueOf(GTConLai).toString());
		row.setType(InventoryReportEntity.ORGANIZATION);
		row.setIndex(0);
		rows.add(row);
		
		return rows;
	}
	
	private List<InventoryReportEntity> runQuery(SQLSelectQuery rQuery) throws Exception {
		List<InventoryReportEntity> listObjects = new ArrayList<InventoryReportEntity>();
		System.out.println(rQuery.createSQLQuery());
		ReportTable[] reportTable = accountingServiceClient.reportQuery(new SQLSelectQuery[] { rQuery });
		String[] field = new String[rQuery.getFields().size()];
		for (int i = 0; i < rQuery.getFields().size(); i++) {
			field[i] = "value" + i;
		}
		reportTable[0].dump(field);
		List<Map<String, Object>> records = reportTable[0].getRecords();
		for (int i = 0; i < records.size(); i++) {
			Map<String, Object> record = records.get(i);
			Object[] values = new Object[field.length];
			for (int j = 0; j < field.length; j++) {
				values[j] = record.get(field[j]);
			}
			InventoryReportEntity object = null;
			if (rQuery.getFields().size() == 4)
				object = new InventoryReportEntity(values[0].toString(), values[1].toString(), values[2].toString(), values[3].toString(), "", "", "", "", "");
			else
				object = new InventoryReportEntity(values[0].toString(), values[1].toString(), "", "", "", "", "", "", "");
			listObjects.add(object);
		}
		return listObjects;
	}
}
