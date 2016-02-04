package com.hkt.swingexp.app.report.table;

import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import com.hkt.client.swingexp.model.PromotionModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.WarehouseModelManager;
import com.hkt.module.core.entity.ReportTable;
import com.hkt.module.core.entity.SQLSelectQuery;
import com.hkt.module.promotion.entity.Menu;
import com.hkt.module.promotion.entity.MenuItem;
import com.hkt.module.restaurant.entity.Recipe;
import com.hkt.module.restaurant.entity.RecipeIngredient;
import com.hkt.swingexp.app.report.entity.InventoryReportEntity;
import com.hkt.swingexp.app.report.modeltable.TableModelReportInventory;

/**
 * @author Phan Anh
 * @date 11/07/2014
 * @edit 23/01/2015
 */

public class TableInventory extends JTable {
	private TableModelReportInventory model;
	private static ClientContext clientContext = ClientContext.getInstance();
	private static AccountingServiceClient accountingServiceClient = clientContext.getBean(AccountingServiceClient.class);
	private HashMap<String, InventoryReportEntity> row_All;

	public TableInventory(String isInventory, Date startDate, Date endDate, String valueMoney, String warehouse,
	    boolean showAll) {
		try {
			model = new TableModelReportInventory(getRoot(isInventory, startDate, endDate, valueMoney, warehouse, showAll));
			this.setModel(model);
			for (int i = 0; i < this.getColumnCount(); i++) {
				if (isInventory.equals("true")) {
					this.setModel(model);
					this.getColumnModel().getColumn(i)
					    .setCellRenderer(new TableRerenderInventory(model, startDate, endDate, valueMoney, warehouse, showAll,row_All));
					this.getColumnModel().getColumn(i)
					    .setCellEditor(new TableRerenderInventory(model, startDate, endDate, valueMoney, warehouse, showAll,row_All));
				} else {
					this.setModel(model);
					this.getColumnModel()
					    .getColumn(i)
					    .setCellRenderer(
					        new TableRerenderQuantitative(model, startDate, endDate, valueMoney, warehouse, showAll, row_All));
					this.getColumnModel()
					    .getColumn(i)
					    .setCellEditor(
					        new TableRerenderQuantitative(model, startDate, endDate, valueMoney, warehouse, showAll, row_All));
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

	private List<InventoryReportEntity> getRoot(String isInventory, Date startDate, Date endDate, String valueMoney,
	    String warehouse, boolean showAll) throws Exception {
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

		SQLSelectQuery rQuery1 = new SQLSelectQuery();

		List<InventoryReportEntity> rows = new ArrayList<InventoryReportEntity>();

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
		
			long startTime1 = System.currentTimeMillis();
			SQLSelectQuery rQuery2 = new SQLSelectQuery();
			SQLSelectQuery rQuery3 = new SQLSelectQuery();
			SQLSelectQuery rQuery4 = new SQLSelectQuery();
			SQLSelectQuery rQuery5 = new SQLSelectQuery();
			SQLSelectQuery rQuery6 = new SQLSelectQuery();

			List<InventoryReportEntity> objects1 = null;
			List<InventoryReportEntity> objects2 = null;
			List<InventoryReportEntity> objects3 = null;
			List<InventoryReportEntity> objects4 = null;
			List<InventoryReportEntity> objects5 = null;
			List<InventoryReportEntity> objects6 = null;
			rQuery1.table("IdentityProduct i JOIN Product p ON p.code = i.productCode")
			    .field("i.productCode AS `productCode`", "value0").field("COALESCE(count(i.id), 0) AS `SLDauKy`", "value1")
			    .field("COALESCE(sum(i.price), 0) AS `value2`", "value2").field("p.recipe AS `value3`", "value3")
			    .cond("i.importDate <= '" + dateFormat.format(calendarStart.getTime()) + "'").cond("p.isCalculateReport = 1")
			    .groupBy("`productCode`");
			
			rQuery2.table("IdentityProduct i JOIN Product p ON p.code = i.productCode")
			    .field("i.productCode AS `productCode`", "value0").field("COALESCE(count(i.id), 0) AS `SLDauKy`", "value1")
			    .field("COALESCE(sum(i.price), 0) AS `value2`", "value2").field("p.recipe AS `value3`", "value3")
			    .cond("i.exportDate <= '" + dateFormat.format(calendarStart.getTime()) + "'").cond("i.exportType = 'Export'")
			    .cond("p.isCalculateReport = 1").groupBy("`productCode`");

			// Lấy giá trị, số lượng tăng giảm trong kỳ
			rQuery3.table("IdentityProduct i JOIN Product p ON p.code = i.productCode")
			    .field("i.productCode AS `productCode`", "value0").field("COALESCE(count(i.id), 0) AS `SLDauKy`", "value1")
			    .field("COALESCE(sum(i.price), 0) AS `value2`", "value2").field("p.recipe AS `value3`", "value3")
			    .cond("i.importDate >= '" + dateFormat.format(calendarStart.getTime()) + "'")
			    .cond("i.importDate <= '" + dateFormat.format(calendarEnd.getTime()) + "'").cond("p.isCalculateReport = 1")
			    .groupBy("`productCode`");

			rQuery4.table("IdentityProduct i JOIN Product p ON p.code = i.productCode")
			    .field("i.productCode AS `productCode`", "value0").field("COALESCE(count(i.id), 0) AS `SLDauKy`", "value1")
			    .field("COALESCE(sum(i.price), 0) AS `value2`", "value2").field("p.recipe AS `value3`", "value3")
			    .cond("i.exportDate >= '" + dateFormat.format(calendarStart.getTime()) + "'")
			    .cond("i.exportDate <= '" + dateFormat.format(calendarEnd.getTime()) + "'").cond("i.exportType = 'Export'")
			    .cond("p.isCalculateReport = 1").groupBy("`productCode`");

			// lấy số lượng và giá trị còn tại
			rQuery5.table("IdentityProduct i JOIN Product p ON p.code = i.productCode")
			    .field("i.productCode AS `productCode`", "value0").field("COALESCE(count(i.id), 0) AS `SLDauKy`", "value1")
			    .field("COALESCE(sum(i.price), 0) AS `value2`", "value2").field("p.recipe AS `value3`", "value3")
			    .cond("i.importDate >= '" + dateFormat.format(calendarEnd.getTime()) + "'")
			    .cond("i.importDate <= '" + dateFormat.format(calendarNow.getTime()) + "'").cond("p.isCalculateReport = 1")
			    .groupBy("`productCode`");

			rQuery6.table("IdentityProduct i JOIN Product p ON p.code = i.productCode")
			    .field("p.code AS `productCode`", "value0").field("COALESCE(count(i.id), 0) AS `SLDauKy`", "value1")
			    .field("COALESCE(sum(i.price), 0) AS `value2`", "value2").field("p.recipe AS `value3`", "value3")
			    .cond("i.exportDate >= '" + dateFormat.format(calendarEnd.getTime()) + "'")
			    .cond("i.exportDate <= '" + dateFormat.format(calendarNow.getTime()) + "'").cond("i.exportType = 'Export'")
			    .cond("p.isCalculateReport = 1").groupBy("`productCode`");

			objects1 = runQuery(rQuery1, true);
			objects2 = runQuery(rQuery2, true);
			objects3 = runQuery(rQuery3, true);
			objects4 = runQuery(rQuery4, true);
			objects5 = runQuery(rQuery5, true);
			objects6 = runQuery(rQuery6, true);
			long endTime = System.currentTimeMillis();
			NumberFormat formatter = new DecimalFormat("#0.00000");
			String time = formatter.format((endTime - startTime1) / 1000d);
			System.out.println("Time SQL : " + time);
			HashMap<String, InventoryReportEntity> row_hash = new HashMap<String, InventoryReportEntity>();
			row_All = new HashMap<String, InventoryReportEntity>();
			if (objects1 != null && objects1.size() > 0) {
				for (int i = 0; i < objects1.size(); i++) {
					InventoryReportEntity obj = new InventoryReportEntity(objects1.get(i).getCol1_DanhMuc(), objects1.get(i)
					    .getCol2_SLDauKy(), objects1.get(i).getCol3_GTDauKy(), "0", objects1.get(i).getCol2_SLDauKy(), "0", "0",
					    "0", "0");
					obj.setParent(objects1.get(i).getParent());
					obj.setProductCode(objects1.get(i).getProductCode());
					obj.setRecipe(objects1.get(i).isRecipe());
					row_hash.put(objects1.get(i).getParent(), obj);
				}
			}
			if (objects2 != null && objects2.size() > 0) {
				for (int i = 0; i < objects2.size(); i++) {
					if (row_hash.containsKey(objects2.get(i).getParent())) {
						InventoryReportEntity r = row_hash.get(objects2.get(i).getParent());
						r.setCol2_SLDauKy(new MyDouble(MyDouble.parseDouble(r.getCol2_SLDauKy())
						    - MyDouble.parseDouble(objects2.get(i).getCol2_SLDauKy())).toString());
					} else {
						String SLDK = "0";
						try {
							SLDK = new MyDouble(0 - MyDouble.parseDouble(objects2.get(i).getCol2_SLDauKy())).toString();
						} catch (Exception e) {
						}
						InventoryReportEntity obj = new InventoryReportEntity(objects2.get(i).getCol1_DanhMuc(), SLDK, objects2
						    .get(i).getCol3_GTDauKy(), "0", "0", "0", objects2.get(i).getCol2_SLDauKy(), "0", "0");
						obj.setProductCode(objects2.get(i).getProductCode());
						obj.setParent(objects2.get(i).getParent());
						obj.setRecipe(objects2.get(i).isRecipe());
						row_hash.put(objects2.get(i).getParent(), obj);
					}
				}
			}
			if (objects3 != null && objects3.size() > 0) {
				for (int i = 0; i < objects3.size(); i++) {
					if (row_hash.containsKey(objects3.get(i).getParent())) {
						InventoryReportEntity r = row_hash.get(objects3.get(i).getParent());
						r.setCol4_SLTang(objects3.get(i).getCol2_SLDauKy());
					} else {
						InventoryReportEntity obj = new InventoryReportEntity(objects3.get(i).getCol1_DanhMuc(), "0", objects3.get(
						    i).getCol3_GTDauKy(), objects3.get(i).getCol2_SLDauKy(), "0", "0", "0", "0", "0");
						obj.setParent(objects3.get(i).getParent());
						obj.setProductCode(objects3.get(i).getProductCode());
						obj.setRecipe(objects3.get(i).isRecipe());
						row_hash.put(objects3.get(i).getParent(), obj);
					}
				}
			}
			if (objects4 != null && objects4.size() > 0) {
				for (int i = 0; i < objects4.size(); i++) {
					if (row_hash.containsKey(objects4.get(i).getParent())) {
						InventoryReportEntity r = row_hash.get(objects4.get(i).getParent());
						r.setCol6_SLGiam(objects4.get(i).getCol2_SLDauKy());
					} else {
						InventoryReportEntity obj = new InventoryReportEntity(objects4.get(i).getCol1_DanhMuc(), "0", objects4.get(
						    i).getCol3_GTDauKy(), "0", "0", objects4.get(i).getCol2_SLDauKy(), "0", "0", "0");
						obj.setProductCode(objects4.get(i).getProductCode());
						obj.setParent(objects4.get(i).getParent());
						obj.setRecipe(objects4.get(i).isRecipe());
						row_hash.put(objects4.get(i).getParent(), obj);
					}
				}
			}
			if (objects5 != null && objects5.size() > 0) {
				for (int i = 0; i < objects5.size(); i++) {
					if (row_hash.containsKey(objects5.get(i).getParent())) {
						InventoryReportEntity r = row_hash.get(objects5.get(i).getParent());
						r.setSLConLai(objects5.get(i).getCol2_SLDauKy());
					} else {
						InventoryReportEntity obj = new InventoryReportEntity(objects5.get(i).getCol1_DanhMuc(), "0", objects5.get(
						    i).getCol3_GTDauKy(), objects5.get(i).getCol2_SLDauKy(), "0", "0", "0", objects5.get(i)
						    .getCol2_SLDauKy(), "0");
						obj.setParent(objects5.get(i).getParent());
						obj.setProductCode(objects5.get(i).getProductCode());
						obj.setRecipe(objects5.get(i).isRecipe());
						row_hash.put(objects5.get(i).getParent(), obj);
					}
				}
			}
			if (objects6 != null && objects6.size() > 0) {
				for (int i = 0; i < objects6.size(); i++) {
					if (row_hash.containsKey(objects6.get(i).getParent())) {
						InventoryReportEntity r = row_hash.get(objects6.get(i).getParent());
						r.setSLConLai(new MyDouble(MyDouble.parseDouble(r.getSLConLai())
						    - MyDouble.parseDouble(objects6.get(i).getCol2_SLDauKy())).toString());
					} else {
						String SLDK = "0";
						try {
							SLDK = new MyDouble(0 - MyDouble.parseDouble(objects6.get(i).getSLConLai())).toString();
						} catch (Exception e) {
						}
						InventoryReportEntity obj = new InventoryReportEntity(objects6.get(i).getCol1_DanhMuc(), "0", objects6.get(
						    i).getCol3_GTDauKy(), "0", "0", "0", SLDK, SLDK, "0");
						obj.setParent(objects6.get(i).getParent());
						obj.setProductCode(objects6.get(i).getProductCode());
						obj.setRecipe(objects6.get(i).isRecipe());
						row_hash.put(objects6.get(i).getParent(), obj);
					}
				}
			}

			long endTime1 = System.currentTimeMillis();
			String time1 = formatter.format((endTime1 - endTime) / 1000d);
			System.out.println("Time JAVA1 : " + time1);
			int rate = 1;
			if (valueMoney.equals("Nghìn")) {
				rate = 1000;
			} else if (valueMoney.equals("Triệu")) {
				rate = 1000000;
			}
			Iterator<InventoryReportEntity> in = row_hash.values().iterator();
			InventoryReportEntity obj = new InventoryReportEntity(nameOrganization, "0", "0", "0", "0", "0", "0", "0", "0");
			obj.setType(InventoryReportEntity.ORGANIZATION);
			obj.setIndex(0);
			rows.add(obj);
			while (in.hasNext()) {
				InventoryReportEntity i = in.next();

				double price = MyDouble.parseDouble(i.getCol3_GTDauKy());
				
				double SLDauKy = MyDouble.parseDouble(i.getCol2_SLDauKy());
				double GTDauKy = price / rate;
				if (GTDauKy < 0) {
					GTDauKy = GTDauKy * (-1);
				}
				double SLTang = MyDouble.parseDouble(i.getCol4_SLTang());
				double GTTang = MyDouble.parseDouble(i.getCol5_GTTang()) / rate;
				double SLGiam = MyDouble.parseDouble(i.getCol6_SLGiam());
				double GTGiam = MyDouble.parseDouble(i.getCol7_GTGiam()) / rate;
				double SLConLai = MyDouble.parseDouble(i.getSLConLai());
				double GTConLai = MyDouble.parseDouble(i.getGTConLai()) / rate;
				obj.setCol2_SLDauKy(new MyDouble(MyDouble.parseDouble(obj.getCol2_SLDauKy()) + SLDauKy).toString());
				obj.setCol3_GTDauKy(new MyDouble(MyDouble.parseDouble(obj.getCol3_GTDauKy()) + GTDauKy).toString());
				obj.setCol4_SLTang(new MyDouble(MyDouble.parseDouble(obj.getCol4_SLTang()) + SLTang).toString());
				obj.setCol5_GTTang(new MyDouble(MyDouble.parseDouble(obj.getCol5_GTTang()) + GTTang).toString());
				obj.setCol6_SLGiam(new MyDouble(MyDouble.parseDouble(obj.getCol6_SLGiam()) + SLGiam).toString());
				obj.setCol7_GTGiam(new MyDouble(MyDouble.parseDouble(obj.getCol7_GTGiam()) + GTGiam).toString());
				obj.setSLConLai(new MyDouble(MyDouble.parseDouble(obj.getSLConLai()) + SLConLai).toString());
				obj.setGTConLai(new MyDouble(MyDouble.parseDouble(obj.getGTConLai()) + GTConLai).toString());

				InventoryReportEntity obj1 = row_All.get(i.getProductCode());
				if (obj1 == null) {
					obj1 = new InventoryReportEntity("", "0", "0", "0", "0", "0", "0", "0", "0");
					obj1.setCol2_SLDauKy(new MyDouble(SLDauKy).toString());
					obj1.setCol3_GTDauKy(new MyDouble(GTDauKy).toString());
					obj1.setCol4_SLTang(new MyDouble(SLTang).toString());
					obj1.setCol5_GTTang(new MyDouble(GTTang).toString());
					obj1.setCol6_SLGiam(new MyDouble(SLGiam).toString());
					obj1.setCol7_GTGiam(new MyDouble(GTGiam).toString());
					obj1.setSLConLai(new MyDouble(SLConLai).toString());
					obj1.setGTConLai(new MyDouble(GTConLai).toString());
					obj1.setProductCode(i.getProductCode());
					row_All.put(i.getProductCode(), obj1);
				} else {
					obj1.setCol2_SLDauKy(new MyDouble(MyDouble.parseDouble(obj1.getCol2_SLDauKy()) + SLDauKy).toString());
					obj1.setCol3_GTDauKy(new MyDouble(MyDouble.parseDouble(obj1.getCol3_GTDauKy()) + GTDauKy).toString());
					obj1.setCol4_SLTang(new MyDouble(MyDouble.parseDouble(obj1.getCol4_SLTang()) + SLTang).toString());
					obj1.setCol5_GTTang(new MyDouble(MyDouble.parseDouble(obj1.getCol5_GTTang()) + GTTang).toString());
					obj1.setCol6_SLGiam(new MyDouble(MyDouble.parseDouble(obj1.getCol6_SLGiam()) + SLGiam).toString());
					obj1.setCol7_GTGiam(new MyDouble(MyDouble.parseDouble(obj1.getCol7_GTGiam()) + GTGiam).toString());
					obj1.setSLConLai(new MyDouble(MyDouble.parseDouble(obj1.getSLConLai()) + SLConLai).toString());
					obj1.setGTConLai(new MyDouble(MyDouble.parseDouble(obj1.getGTConLai()) + GTConLai).toString());
					obj1.setProductCode(i.getProductCode());
					row_All.put(i.getProductCode(), obj1);
				}
			}
			long endTime2 = System.currentTimeMillis();
			String time2 = formatter.format((endTime2 - endTime1) / 1000d);
			System.out.println("Time JAVA2 : " + time2);
		} else {
			long startTime1 = System.currentTimeMillis();
			SQLSelectQuery rQuery2 = new SQLSelectQuery();
			SQLSelectQuery rQuery3 = new SQLSelectQuery();
			SQLSelectQuery rQuery4 = new SQLSelectQuery();
			SQLSelectQuery rQuery5 = new SQLSelectQuery();
			SQLSelectQuery rQuery6 = new SQLSelectQuery();

			List<InventoryReportEntity> objects1 = null;
			List<InventoryReportEntity> objects2 = null;
			List<InventoryReportEntity> objects3 = null;
			List<InventoryReportEntity> objects4 = null;
			List<InventoryReportEntity> objects5 = null;
			List<InventoryReportEntity> objects6 = null;
			rQuery1.table("InvoiceItem i JOIN Product p ON p.code = i.productCode")
			    .field("i.productCode AS `productCode`", "value0")
			    .field("COALESCE(SUM(i.quantity), 0) AS `SLDauKy`", "value1").field("p.price AS `value2`", "value2")
			    .field("p.recipe AS `value3`", "value3")
			    .field("p.unit AS `value4`", "value4")
			    .cond("i.startDate <= '" + dateFormat.format(calendarStart.getTime()) + "'").cond("i.activityType='Payment'")
			    .cond("p.isCalculateReport = 1").groupBy("`productCode`");

			rQuery2.table("InvoiceItem i JOIN Product p ON p.code = i.productCode")
			    .field("i.productCode AS `productCode`", "value0")
			    .field("COALESCE(SUM(i.quantity), 0) AS `SLDauKy`", "value1").field("p.price AS `value2`", "value2")
			    .field("p.recipe AS `value3`", "value3")
          .field("p.unit AS `value4`", "value4")
          .cond("i.startDate <= '" + dateFormat.format(calendarStart.getTime()) + "'").cond("i.activityType='Receipt'")
			    .cond("p.isCalculateReport = 1").groupBy("`productCode`");

			// Lấy giá trị, số lượng tăng giảm trong kỳ
			rQuery3.table("InvoiceItem i JOIN Product p ON p.code = i.productCode")
			    .field("i.productCode AS `productCode`", "value0")
			    .field("COALESCE(SUM(i.quantity), 0) AS `SLDauKy`", "value1").field("p.price AS `value2`", "value2")
			    .field("p.recipe AS `value3`", "value3")
			    .field("p.unit AS `value4`", "value4")
			    .cond("i.startDate >= '" + dateFormat.format(calendarStart.getTime()) + "'")
			    .cond("i.startDate <= '" + dateFormat.format(calendarEnd.getTime()) + "'").cond("i.activityType='Payment'")
			    .cond("p.isCalculateReport = 1").groupBy("`productCode`");

			rQuery4.table("InvoiceItem i JOIN Product p ON p.code = i.productCode")
			    .field("i.productCode AS `productCode`", "value0")
			    .field("COALESCE(SUM(i.quantity), 0) AS `SLDauKy`", "value1").field("p.price AS `value2`", "value2")
			    .field("p.recipe AS `value3`", "value3")
			    .field("p.unit AS `value4`", "value4")
			    .cond("i.startDate >= '" + dateFormat.format(calendarStart.getTime()) + "'")
			    .cond("i.startDate <= '" + dateFormat.format(calendarEnd.getTime()) + "'").cond("i.activityType='Receipt'")
			    .cond("p.isCalculateReport = 1").groupBy("`productCode`");

			// lấy số lượng và giá trị còn tại
			rQuery5.table("InvoiceItem i JOIN Product p ON p.code = i.productCode")
			    .field("i.productCode AS `productCode`", "value0")
			    .field("COALESCE(SUM(i.quantity), 0) AS `SLDauKy`", "value1").field("p.price AS `value2`", "value2")
			    .field("p.recipe AS `value3`", "value3")
			    .field("p.unit AS `value4`", "value4")
			    .cond("i.startDate >= '" + dateFormat.format(calendarEnd.getTime()) + "'")
			    .cond("i.startDate <= '" + dateFormat.format(calendarNow.getTime()) + "'").cond("i.activityType='Payment'")
			    .cond("p.isCalculateReport = 1").groupBy("`productCode`");

			rQuery6.table("InvoiceItem i JOIN Product p ON p.code = i.productCode")
			    .field("p.code AS `productCode`", "value0").field("COALESCE(SUM(i.quantity), 0) AS `SLConLai`", "value1")
			    .field("p.price AS `value2`", "value2").field("p.recipe AS `value3`", "value3")
			    .field("p.unit AS `value4`", "value4")
			    .cond("i.startDate >= '" + dateFormat.format(calendarEnd.getTime()) + "'")
			    .cond("i.startDate <= '" + dateFormat.format(calendarNow.getTime()) + "'").cond("i.activityType='Receipt'")
			    .cond("p.isCalculateReport = 1").groupBy("`productCode`");

			objects1 = runQuery(rQuery1, true);
			objects2 = runQuery(rQuery2, true);
			objects3 = runQuery(rQuery3, true);
			objects4 = runQuery(rQuery4, true);
			objects5 = runQuery(rQuery5, true);
			objects6 = runQuery(rQuery6, true);
			long endTime = System.currentTimeMillis();
			NumberFormat formatter = new DecimalFormat("#0.00000");
			String time = formatter.format((endTime - startTime1) / 1000d);
			System.out.println("Time SQL : " + time);
			HashMap<String, InventoryReportEntity> row_hash = new HashMap<String, InventoryReportEntity>();
			row_All = new HashMap<String, InventoryReportEntity>();
			if (objects1 != null && objects1.size() > 0) {
				for (int i = 0; i < objects1.size(); i++) {
					InventoryReportEntity obj = new InventoryReportEntity(objects1.get(i).getCol1_DanhMuc(), objects1.get(i)
					    .getCol2_SLDauKy(), objects1.get(i).getCol3_GTDauKy(), "0", objects1.get(i).getCol2_SLDauKy(), "0", "0",
					    "0", "0");
					obj.setParent(objects1.get(i).getParent());
					obj.setProductCode(objects1.get(i).getProductCode());
					obj.setRecipe(objects1.get(i).isRecipe());
					obj.setUnit(objects1.get(i).getUnit());
					row_hash.put(objects1.get(i).getParent(), obj);
				}
			}
			if (objects2 != null && objects2.size() > 0) {
				for (int i = 0; i < objects2.size(); i++) {
					if (row_hash.containsKey(objects2.get(i).getParent())) {
						InventoryReportEntity r = row_hash.get(objects2.get(i).getParent());
						r.setCol2_SLDauKy(new MyDouble(MyDouble.parseDouble(r.getCol2_SLDauKy())
						    - MyDouble.parseDouble(objects2.get(i).getCol2_SLDauKy())).toString());
					} else {
						String SLDK = "0";
						try {
							SLDK = new MyDouble(0 - MyDouble.parseDouble(objects2.get(i).getCol2_SLDauKy())).toString();
						} catch (Exception e) {
						}
						InventoryReportEntity obj = new InventoryReportEntity(objects2.get(i).getCol1_DanhMuc(), SLDK, objects2
						    .get(i).getCol3_GTDauKy(), "0", "0", "0", objects2.get(i).getCol2_SLDauKy(), "0", "0");
						obj.setProductCode(objects2.get(i).getProductCode());
						obj.setParent(objects2.get(i).getParent());
						obj.setRecipe(objects2.get(i).isRecipe());
						obj.setUnit(objects2.get(i).getUnit());
						row_hash.put(objects2.get(i).getParent(), obj);
					}
				}
			}
			if (objects3 != null && objects3.size() > 0) {
				for (int i = 0; i < objects3.size(); i++) {
					if (row_hash.containsKey(objects3.get(i).getParent())) {
						InventoryReportEntity r = row_hash.get(objects3.get(i).getParent());
						r.setCol4_SLTang(objects3.get(i).getCol2_SLDauKy());
					} else {
						InventoryReportEntity obj = new InventoryReportEntity(objects3.get(i).getCol1_DanhMuc(), "0", objects3.get(
						    i).getCol3_GTDauKy(), objects3.get(i).getCol2_SLDauKy(), "0", "0", "0", "0", "0");
						obj.setParent(objects3.get(i).getParent());
						obj.setProductCode(objects3.get(i).getProductCode());
						obj.setRecipe(objects3.get(i).isRecipe());
						obj.setUnit(objects3.get(i).getUnit());
						row_hash.put(objects3.get(i).getParent(), obj);
					}
				}
			}
			if (objects4 != null && objects4.size() > 0) {
				for (int i = 0; i < objects4.size(); i++) {
					if (row_hash.containsKey(objects4.get(i).getParent())) {
						InventoryReportEntity r = row_hash.get(objects4.get(i).getParent());
						r.setCol6_SLGiam(objects4.get(i).getCol2_SLDauKy());
					} else {
						InventoryReportEntity obj = new InventoryReportEntity(objects4.get(i).getCol1_DanhMuc(), "0", objects4.get(
						    i).getCol3_GTDauKy(), "0", "0", objects4.get(i).getCol2_SLDauKy(), "0", "0", "0");
						obj.setProductCode(objects4.get(i).getProductCode());
						obj.setParent(objects4.get(i).getParent());
						obj.setRecipe(objects4.get(i).isRecipe());
						obj.setUnit(objects4.get(i).getUnit());
						row_hash.put(objects4.get(i).getParent(), obj);
					}
				}
			}
			if (objects5 != null && objects5.size() > 0) {
				for (int i = 0; i < objects5.size(); i++) {
					if (row_hash.containsKey(objects5.get(i).getParent())) {
						InventoryReportEntity r = row_hash.get(objects5.get(i).getParent());
						r.setSLConLai(objects5.get(i).getCol2_SLDauKy());
					} else {
						InventoryReportEntity obj = new InventoryReportEntity(objects5.get(i).getCol1_DanhMuc(), "0", objects5.get(
						    i).getCol3_GTDauKy(), objects5.get(i).getCol2_SLDauKy(), "0", "0", "0", objects5.get(i)
						    .getCol2_SLDauKy(), "0");
						obj.setParent(objects5.get(i).getParent());
						obj.setProductCode(objects5.get(i).getProductCode());
						obj.setRecipe(objects5.get(i).isRecipe());
						obj.setUnit(objects5.get(i).getUnit());
						row_hash.put(objects5.get(i).getParent(), obj);
					}
				}
			}
			if (objects6 != null && objects6.size() > 0) {
				for (int i = 0; i < objects6.size(); i++) {
					if (row_hash.containsKey(objects6.get(i).getParent())) {
						InventoryReportEntity r = row_hash.get(objects6.get(i).getParent());
						r.setSLConLai(new MyDouble(MyDouble.parseDouble(r.getSLConLai())
						    - MyDouble.parseDouble(objects6.get(i).getCol2_SLDauKy())).toString());
					} else {
						String SLDK = "0";
						try {
							SLDK = new MyDouble(0 - MyDouble.parseDouble(objects6.get(i).getSLConLai())).toString();
						} catch (Exception e) {
						}
						InventoryReportEntity obj = new InventoryReportEntity(objects6.get(i).getCol1_DanhMuc(), "0", objects6.get(
						    i).getCol3_GTDauKy(), "0", "0", "0", SLDK, SLDK, "0");
						obj.setParent(objects6.get(i).getParent());
						obj.setProductCode(objects6.get(i).getProductCode());
						obj.setRecipe(objects6.get(i).isRecipe());
						obj.setUnit(objects6.get(i).getUnit());
						row_hash.put(objects6.get(i).getParent(), obj);
					}
				}
			}

			long endTime1 = System.currentTimeMillis();
			String time1 = formatter.format((endTime1 - endTime) / 1000d);
			System.out.println("Time JAVA1 : " + time1);
			int rate = 1;
			if (valueMoney.equals("Nghìn")) {
				rate = 1000;
			} else if (valueMoney.equals("Triệu")) {
				rate = 1000000;
			}
			Iterator<InventoryReportEntity> in = row_hash.values().iterator();
			InventoryReportEntity obj = new InventoryReportEntity(nameOrganization, "0", "0", "0", "0", "0", "0", "0", "0");
			obj.setType(InventoryReportEntity.ORGANIZATION);
			obj.setIndex(0);
			rows.add(obj);
			while (in.hasNext()) {
				InventoryReportEntity i = in.next();
				Recipe recipe = null;
				if (i.isRecipe()) {
					recipe = RestaurantModelManager.getInstance().getRecipeByProductCode().get(i.getProductCode());
				}
				if (recipe == null) {
					double price = MyDouble.parseDouble(i.getCol3_GTDauKy());
					double SLDauKy = MyDouble.parseDouble(i.getCol2_SLDauKy());
					double GTDauKy = SLDauKy * price / rate;
					if (GTDauKy < 0) {
						GTDauKy = GTDauKy * (-1);
					}
					double SLTang = MyDouble.parseDouble(i.getCol4_SLTang());
					double GTTang = SLTang * price / rate;
					double SLGiam = MyDouble.parseDouble(i.getCol6_SLGiam());
					double GTGiam = SLGiam * price / rate;
					double SLConLai = MyDouble.parseDouble(i.getSLConLai());
					double GTConLai = SLConLai * price / rate;
					obj.setCol2_SLDauKy(new MyDouble(MyDouble.parseDouble(obj.getCol2_SLDauKy()) + SLDauKy).toString());
					obj.setCol3_GTDauKy(new MyDouble(MyDouble.parseDouble(obj.getCol3_GTDauKy()) + GTDauKy).toString());
					obj.setCol4_SLTang(new MyDouble(MyDouble.parseDouble(obj.getCol4_SLTang()) + SLTang).toString());
					obj.setCol5_GTTang(new MyDouble(MyDouble.parseDouble(obj.getCol5_GTTang()) + GTTang).toString());
					obj.setCol6_SLGiam(new MyDouble(MyDouble.parseDouble(obj.getCol6_SLGiam()) + SLGiam).toString());
					obj.setCol7_GTGiam(new MyDouble(MyDouble.parseDouble(obj.getCol7_GTGiam()) + GTGiam).toString());
					obj.setSLConLai(new MyDouble(MyDouble.parseDouble(obj.getSLConLai()) + SLConLai).toString());
					obj.setGTConLai(new MyDouble(MyDouble.parseDouble(obj.getGTConLai()) + GTConLai).toString());

					InventoryReportEntity obj1 = row_All.get(i.getProductCode());
					if (obj1 == null) {
						obj1 = new InventoryReportEntity("", "0", "0", "0", "0", "0", "0", "0", "0");
						obj1.setCol2_SLDauKy(new MyDouble(SLDauKy).toString());
						obj1.setCol3_GTDauKy(new MyDouble(GTDauKy).toString());
						obj1.setCol4_SLTang(new MyDouble(SLTang).toString());
						obj1.setCol5_GTTang(new MyDouble(GTTang).toString());
						obj1.setCol6_SLGiam(new MyDouble(SLGiam).toString());
						obj1.setCol7_GTGiam(new MyDouble(GTGiam).toString());
						obj1.setSLConLai(new MyDouble(SLConLai).toString());
						obj1.setGTConLai(new MyDouble(GTConLai).toString());
						obj1.setProductCode(i.getProductCode());
						row_All.put(i.getProductCode(), obj1);
					} else {
						obj1.setCol2_SLDauKy(new MyDouble(MyDouble.parseDouble(obj1.getCol2_SLDauKy()) + SLDauKy).toString());
						obj1.setCol3_GTDauKy(new MyDouble(MyDouble.parseDouble(obj1.getCol3_GTDauKy()) + GTDauKy).toString());
						obj1.setCol4_SLTang(new MyDouble(MyDouble.parseDouble(obj1.getCol4_SLTang()) + SLTang).toString());
						obj1.setCol5_GTTang(new MyDouble(MyDouble.parseDouble(obj1.getCol5_GTTang()) + GTTang).toString());
						obj1.setCol6_SLGiam(new MyDouble(MyDouble.parseDouble(obj1.getCol6_SLGiam()) + SLGiam).toString());
						obj1.setCol7_GTGiam(new MyDouble(MyDouble.parseDouble(obj1.getCol7_GTGiam()) + GTGiam).toString());
						obj1.setSLConLai(new MyDouble(MyDouble.parseDouble(obj1.getSLConLai()) + SLConLai).toString());
						obj1.setGTConLai(new MyDouble(MyDouble.parseDouble(obj1.getGTConLai()) + GTConLai).toString());
						obj1.setProductCode(i.getProductCode());
						row_All.put(i.getProductCode(), obj1);
					}
					if(i.getUnit().equals("menu")){
						try {
							Menu menu = PromotionModelManager.getInstance().getMenuByCode(i.getProductCode());
							for(MenuItem menuItem: menu.getMenuItems()){
								double price1 = 0;
								double dk1 = MyDouble.parseDouble(i.getCol5_GTTang());
								double dk2 = MyDouble.parseDouble(i.getCol7_GTGiam())*menuItem.getQuantity();
								if (dk2 < 0) {
									dk2 = dk2 * (-1);
								}
								double SLDauKy1 = dk1 - dk2;
								double GTDauKy1 = SLDauKy1 * price1 / rate;
								if (GTDauKy1 < 0) {
									GTDauKy1 = GTDauKy1 * (-1);
								}
								double SLTang1 = MyDouble.parseDouble(i.getCol4_SLTang());
								double GTTang1 = SLTang1 * price1 / rate;
								double SLGiam1 = MyDouble.parseDouble(i.getCol6_SLGiam()) * menuItem.getQuantity();
								double GTGiam1 = SLGiam1 * price1 / rate;
								double cl1 = MyDouble.parseDouble(i.getCol5_GTTang());
								double cl2 = MyDouble.parseDouble(i.getSLConLai()) * menuItem.getQuantity();
								if (cl2 < 0) {
									cl2 = cl2 * (-1);
								}
								double SLConLai1 = cl1 - cl2;
								double GTConLai1 = SLConLai1 * price1 / rate;
								obj.setCol2_SLDauKy(new MyDouble(MyDouble.parseDouble(obj.getCol2_SLDauKy()) + SLDauKy1).toString());
								obj.setCol3_GTDauKy(new MyDouble(MyDouble.parseDouble(obj.getCol3_GTDauKy()) + GTDauKy1).toString());
								obj.setCol4_SLTang(new MyDouble(MyDouble.parseDouble(obj.getCol4_SLTang()) + SLTang1).toString());
								obj.setCol5_GTTang(new MyDouble(MyDouble.parseDouble(obj.getCol5_GTTang()) + GTTang1).toString());
								obj.setCol6_SLGiam(new MyDouble(MyDouble.parseDouble(obj.getCol6_SLGiam()) + SLGiam1).toString());
								obj.setCol7_GTGiam(new MyDouble(MyDouble.parseDouble(obj.getCol7_GTGiam()) + GTGiam1).toString());
								obj.setSLConLai(new MyDouble(MyDouble.parseDouble(obj.getSLConLai()) + SLConLai1).toString());
								obj.setGTConLai(new MyDouble(MyDouble.parseDouble(obj.getGTConLai()) + GTConLai1).toString());

								InventoryReportEntity obj11 = row_All.get(menuItem.getIdentifierId());
								if (obj11 == null) {
									obj11 = new InventoryReportEntity("", "0", "0", "0", "0", "0", "0", "0", "0");
									obj11.setCol2_SLDauKy(new MyDouble(SLDauKy1).toString());
									obj11.setCol3_GTDauKy(new MyDouble(GTDauKy1).toString());
									obj11.setCol4_SLTang(new MyDouble(SLTang1).toString());
									obj11.setCol5_GTTang(new MyDouble(GTTang1).toString());
									obj11.setCol6_SLGiam(new MyDouble(SLGiam1).toString());
									obj11.setCol7_GTGiam(new MyDouble(GTGiam1).toString());
									obj11.setSLConLai(new MyDouble(SLConLai1).toString());
									obj11.setGTConLai(new MyDouble(GTConLai1).toString());
									obj11.setProductCode(menuItem.getIdentifierId());
									row_All.put(menuItem.getIdentifierId(), obj11);
								} else {
									obj11.setCol2_SLDauKy(new MyDouble(MyDouble.parseDouble(obj11.getCol2_SLDauKy()) + SLDauKy1).toString());
									obj11.setCol3_GTDauKy(new MyDouble(MyDouble.parseDouble(obj11.getCol3_GTDauKy()) + GTDauKy1).toString());
									obj11.setCol4_SLTang(new MyDouble(MyDouble.parseDouble(obj11.getCol4_SLTang()) + SLTang1).toString());
									obj11.setCol5_GTTang(new MyDouble(MyDouble.parseDouble(obj11.getCol5_GTTang()) + GTTang1).toString());
									obj11.setCol6_SLGiam(new MyDouble(MyDouble.parseDouble(obj11.getCol6_SLGiam()) + SLGiam1).toString());
									obj11.setCol7_GTGiam(new MyDouble(MyDouble.parseDouble(obj11.getCol7_GTGiam()) + GTGiam1).toString());
									obj11.setSLConLai(new MyDouble(MyDouble.parseDouble(obj11.getSLConLai()) + SLConLai1).toString());
									obj11.setGTConLai(new MyDouble(MyDouble.parseDouble(obj11.getGTConLai()) + GTConLai1).toString());
									obj11.setProductCode(menuItem.getIdentifierId());
									row_All.put(menuItem.getIdentifierId(), obj11);
								}


							}

						} catch (Exception e) {
							// TODO: handle exception
						}
											}
				} else {
					List<RecipeIngredient> ingredients = recipe.getRecipeIngredients();
					for (RecipeIngredient recipeIngredient : ingredients) {
						Recipe recipe1 = RestaurantModelManager.getInstance().getRecipeByProductCode().get(recipeIngredient.getProductCode());
						if (recipe1 != null) {
							List<RecipeIngredient> ingredients1 = recipe1.getRecipeIngredients();
							for (RecipeIngredient recipeIngredient1 : ingredients1) {
								double price = MyDouble.parseDouble(recipeIngredient1.getCreatedBy());
								System.out.println("hien    "+price);
								double dk1 = MyDouble.parseDouble(i.getCol5_GTTang());
								double dk2 = MyDouble.parseDouble(i.getCol7_GTGiam()) * recipeIngredient1.getQuantity()
								    * recipeIngredient.getQuantity();
								if (dk2 < 0) {
									dk2 = dk2 * (-1);
								}
								double SLDauKy = dk1 - dk2;
								double GTDauKy = SLDauKy * price / rate;
								if (GTDauKy < 0) {
									GTDauKy = GTDauKy * (-1);
								}
								double SLTang = MyDouble.parseDouble(i.getCol4_SLTang());
								double GTTang = SLTang * price / rate;
								double SLGiam = MyDouble.parseDouble(i.getCol6_SLGiam()) * recipeIngredient1.getQuantity()
								    * recipeIngredient.getQuantity();
								double GTGiam = SLGiam * price / rate;
								double cl1 = MyDouble.parseDouble(i.getCol5_GTTang());
								double cl2 = MyDouble.parseDouble(i.getSLConLai()) * recipeIngredient1.getQuantity()
								    * recipeIngredient.getQuantity();
								if (cl2 < 0) {
									cl2 = cl2 * (-1);
								}
								double SLConLai = cl1 - cl2;
								double GTConLai = SLConLai * price / rate;
								obj.setCol2_SLDauKy(new MyDouble(MyDouble.parseDouble(obj.getCol2_SLDauKy()) + SLDauKy).toString());
								obj.setCol3_GTDauKy(new MyDouble(MyDouble.parseDouble(obj.getCol3_GTDauKy()) + GTDauKy).toString());
								obj.setCol4_SLTang(new MyDouble(MyDouble.parseDouble(obj.getCol4_SLTang()) + SLTang).toString());
								obj.setCol5_GTTang(new MyDouble(MyDouble.parseDouble(obj.getCol5_GTTang()) + GTTang).toString());
								obj.setCol6_SLGiam(new MyDouble(MyDouble.parseDouble(obj.getCol6_SLGiam()) + SLGiam).toString());
								obj.setCol7_GTGiam(new MyDouble(MyDouble.parseDouble(obj.getCol7_GTGiam()) + GTGiam).toString());
								obj.setSLConLai(new MyDouble(MyDouble.parseDouble(obj.getSLConLai()) + SLConLai).toString());
								obj.setGTConLai(new MyDouble(MyDouble.parseDouble(obj.getGTConLai()) + GTConLai).toString());

								InventoryReportEntity obj1 = row_All.get(recipeIngredient1.getProductCode());
								if (obj1 == null) {
									obj1 = new InventoryReportEntity("", "0", "0", "0", "0", "0", "0", "0", "0");
									obj1.setCol2_SLDauKy(new MyDouble(SLDauKy).toString());
									obj1.setCol3_GTDauKy(new MyDouble(GTDauKy).toString());
									obj1.setCol4_SLTang(new MyDouble(SLTang).toString());
									obj1.setCol5_GTTang(new MyDouble(GTTang).toString());
									obj1.setCol6_SLGiam(new MyDouble(SLGiam).toString());
									obj1.setCol7_GTGiam(new MyDouble(GTGiam).toString());
									obj1.setSLConLai(new MyDouble(SLConLai).toString());
									obj1.setGTConLai(new MyDouble(GTConLai).toString());
									obj1.setProductCode(recipeIngredient1.getProductCode());
									row_All.put(recipeIngredient1.getProductCode(), obj1);
								} else {
									obj1.setCol2_SLDauKy(new MyDouble(MyDouble.parseDouble(obj1.getCol2_SLDauKy()) + SLDauKy).toString());
									obj1.setCol3_GTDauKy(new MyDouble(MyDouble.parseDouble(obj1.getCol3_GTDauKy()) + GTDauKy).toString());
									obj1.setCol4_SLTang(new MyDouble(MyDouble.parseDouble(obj1.getCol4_SLTang()) + SLTang).toString());
									obj1.setCol5_GTTang(new MyDouble(MyDouble.parseDouble(obj1.getCol5_GTTang()) + GTTang).toString());
									obj1.setCol6_SLGiam(new MyDouble(MyDouble.parseDouble(obj1.getCol6_SLGiam()) + SLGiam).toString());
									obj1.setCol7_GTGiam(new MyDouble(MyDouble.parseDouble(obj1.getCol7_GTGiam()) + GTGiam).toString());
									obj1.setSLConLai(new MyDouble(MyDouble.parseDouble(obj1.getSLConLai()) + SLConLai).toString());
									obj1.setGTConLai(new MyDouble(MyDouble.parseDouble(obj1.getGTConLai()) + GTConLai).toString());
									obj1.setProductCode(recipeIngredient1.getProductCode());
									row_All.put(recipeIngredient1.getProductCode(), obj1);
								}

							}
						} else {
							System.out.println("Jun  "+recipeIngredient.getCreatedBy());
							double price = MyDouble.parseDouble(recipeIngredient.getCreatedBy());
							System.out.println("long   "+price);
							double dk1 = MyDouble.parseDouble(i.getCol5_GTTang());
							double dk2 = MyDouble.parseDouble(i.getCol7_GTGiam()) * recipeIngredient.getQuantity();
							if (dk2 < 0) {
								dk2 = dk2 * (-1);
							}
							double SLDauKy = dk1 - dk2;
							double GTDauKy = SLDauKy * price / rate;
							if (GTDauKy < 0) {
								GTDauKy = GTDauKy * (-1);
							}
							double SLTang = MyDouble.parseDouble(i.getCol4_SLTang());
							double GTTang = SLTang * price / rate;
							double SLGiam = MyDouble.parseDouble(i.getCol6_SLGiam()) * recipeIngredient.getQuantity();
							double GTGiam = SLGiam * price / rate;
							double cl1 = MyDouble.parseDouble(i.getCol5_GTTang());
							double cl2 = MyDouble.parseDouble(i.getSLConLai()) * recipeIngredient.getQuantity();
							if (cl2 < 0) {
								cl2 = cl2 * (-1);
							}
							double SLConLai = cl1 - cl2;
							double GTConLai = SLConLai * price / rate;
							obj.setCol2_SLDauKy(new MyDouble(MyDouble.parseDouble(obj.getCol2_SLDauKy()) + SLDauKy).toString());
							obj.setCol3_GTDauKy(new MyDouble(MyDouble.parseDouble(obj.getCol3_GTDauKy()) + GTDauKy).toString());
							obj.setCol4_SLTang(new MyDouble(MyDouble.parseDouble(obj.getCol4_SLTang()) + SLTang).toString());
							obj.setCol5_GTTang(new MyDouble(MyDouble.parseDouble(obj.getCol5_GTTang()) + GTTang).toString());
							obj.setCol6_SLGiam(new MyDouble(MyDouble.parseDouble(obj.getCol6_SLGiam()) + SLGiam).toString());
							obj.setCol7_GTGiam(new MyDouble(MyDouble.parseDouble(obj.getCol7_GTGiam()) + GTGiam).toString());
							obj.setSLConLai(new MyDouble(MyDouble.parseDouble(obj.getSLConLai()) + SLConLai).toString());
							obj.setGTConLai(new MyDouble(MyDouble.parseDouble(obj.getGTConLai()) + GTConLai).toString());

							InventoryReportEntity obj1 = row_All.get(recipeIngredient.getProductCode());
							if (obj1 == null) {
								obj1 = new InventoryReportEntity("", "0", "0", "0", "0", "0", "0", "0", "0");
							//	obj1.setPrice(price);
								obj1.setCol2_SLDauKy(new MyDouble(SLDauKy).toString());
								obj1.setCol3_GTDauKy(new MyDouble(GTDauKy).toString());
								obj1.setCol4_SLTang(new MyDouble(SLTang).toString());
								obj1.setCol5_GTTang(new MyDouble(GTTang).toString());
								obj1.setCol6_SLGiam(new MyDouble(SLGiam).toString());
								obj1.setCol7_GTGiam(new MyDouble(GTGiam).toString());
								obj1.setSLConLai(new MyDouble(SLConLai).toString());
								obj1.setGTConLai(new MyDouble(GTConLai).toString());
								obj1.setProductCode(recipeIngredient.getProductCode());
								row_All.put(recipeIngredient.getProductCode(), obj1);
							} else {
								obj1.setCol2_SLDauKy(new MyDouble(MyDouble.parseDouble(obj1.getCol2_SLDauKy()) + SLDauKy).toString());
								obj1.setCol3_GTDauKy(new MyDouble(MyDouble.parseDouble(obj1.getCol3_GTDauKy()) + GTDauKy).toString());
								obj1.setCol4_SLTang(new MyDouble(MyDouble.parseDouble(obj1.getCol4_SLTang()) + SLTang).toString());
								obj1.setCol5_GTTang(new MyDouble(MyDouble.parseDouble(obj1.getCol5_GTTang()) + GTTang).toString());
								obj1.setCol6_SLGiam(new MyDouble(MyDouble.parseDouble(obj1.getCol6_SLGiam()) + SLGiam).toString());
								obj1.setCol7_GTGiam(new MyDouble(MyDouble.parseDouble(obj1.getCol7_GTGiam()) + GTGiam).toString());
								obj1.setSLConLai(new MyDouble(MyDouble.parseDouble(obj1.getSLConLai()) + SLConLai).toString());
								obj1.setGTConLai(new MyDouble(MyDouble.parseDouble(obj1.getGTConLai()) + GTConLai).toString());
								obj1.setProductCode(recipeIngredient.getProductCode());
								row_All.put(recipeIngredient.getProductCode(), obj1);
							}
						}
					}
				}

			}
			long endTime2 = System.currentTimeMillis();
			String time2 = formatter.format((endTime2 - endTime1) / 1000d);
			System.out.println("Time JAVA2 : " + time2);
			// }
		}
		return rows;
	}

	private List<InventoryReportEntity> runQuery(SQLSelectQuery rQuery, boolean product) throws Exception {
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

			InventoryReportEntity object = new InventoryReportEntity("", values[1].toString(), values[2].toString(), "", "",
			    "", "", "", "");
			object.setParent(values[0].toString());
			object.setProductCode(values[0].toString());
			try {
				object.setRecipe(Boolean.parseBoolean(values[3].toString()));
			} catch (Exception e) {
				object.setRecipe(false);
			}
			try {
				object.setUnit(values[4].toString());
			} catch (Exception e) {
				object.setUnit("");
			}
			listObjects.add(object);
		}
		return listObjects;
	}
}
