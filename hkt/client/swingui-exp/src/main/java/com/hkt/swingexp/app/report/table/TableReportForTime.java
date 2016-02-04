package com.hkt.swingexp.app.report.table;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
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
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.GenericOptionModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.module.accounting.entity.Invoice.ActivityType;
import com.hkt.module.config.generic.OptionGroup;
import com.hkt.module.core.entity.ReportTable;
import com.hkt.module.core.entity.SQLSelectQuery;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.swingexp.app.report.entity.ReportForTimeEntity;
import com.hkt.swingexp.app.report.entity.ReportForTimeEntity.Type;
import com.hkt.swingexp.app.report.modeltable.ModelTableForTime;

/**
 * @author Phan Anh
 * @date 01/09/2014
 * @editDate: 25/12/2014
 */

public class TableReportForTime extends JTable {
	private ModelTableForTime								model;
	private HashMap<String, String>					conds;
	private String													viewMoney;
	private String													nameNode;
	private int 														typeReport;

	public TableReportForTime(int typeReport, int setView, HashMap<String, String> hash, String viewUnitMoney) {
		this.conds = hash;
		this.viewMoney = viewUnitMoney;
		this.typeReport = typeReport;
		try {
			nameNode = AccountModelManager.getInstance().getNameByLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
		} catch (Exception e) {
			nameNode = "";
		}

		try {
			if (typeReport == 2) {
				if (conds.containsKey("product1"))
					model = new ModelTableForTime(4, getNodeParent());
				else
					model = new ModelTableForTime(typeReport, getNodeParent());
			} else
				model = new ModelTableForTime(typeReport, getNodeParent());

			this.setModel(model);
			for (int i = 0; i < this.getColumnCount(); i++) {
				this.getColumnModel().getColumn(i).setCellRenderer(new TableRerenderForTime(model, typeReport, setView, hash, viewUnitMoney));
				this.getColumnModel().getColumn(i).setCellEditor(new TableRerenderForTime(model, typeReport, setView, hash, viewUnitMoney));
			}
			model.fireTableDataChanged();
			this.setModel(model);
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.setRowHeight(23);
		this.setFont(new Font("Tahoma", 0, 14));
		this.getColumnModel().getColumn(0).setMinWidth(300);
		this.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		this.setBackground(Color.WHITE);
		this.setFillsViewportHeight(true);
	}
	
	public void setReportGui(ReportStatistics reportStatistics){
		model.setReportStatistics(reportStatistics);
	}

	private List<ReportForTimeEntity> getNodeParent() throws Exception {
		List<ReportForTimeEntity> rows = new ArrayList<ReportForTimeEntity>();
		if (typeReport == 0) {	
			String[] row = runQuery(sqlQuery_DoanhThu_ChiPhi(ActivityType.Payment));
			rows.add(getRowFromQuery(row));
		} else if (typeReport == 1) {
			String[] row = runQuery(sqlQuery_DoanhThu_ChiPhi(ActivityType.Receipt));
			rows.add(getRowFromQuery(row));
		} else if (typeReport == 2) {
			String[] row = runQuery(sqlQuery_ThuChiLai());
			rows.add(getRowFromQuery(row));
		} else if (typeReport == 3) {
			String[] row1 = runQuery(sqlQuery_DoanhThu_ChiPhi(ActivityType.Receipt));
			String[] row2 = runQuery(sqlQuery_DoanhThu_ChiPhi(ActivityType.Payment));
			String[] row = new String[] {row1[0], row1[1], row2[0], row2[1]};
			rows.add(getRowFromQuery(row));
		}
		return rows;
	}

	private SQLSelectQuery sqlQuery_DoanhThu_ChiPhi(ActivityType activeType) throws Exception {
		SQLSelectQuery rQuery = new SQLSelectQuery();
		
		/** TAB phòng ban */
		if (conds.containsKey("department")) {
			if (conds.get("department").toString().isEmpty()) {
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.finalCharge), 0) AS `finalCharge`", "column")
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.ActivityType = '" + activeType + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total), 0) AS `total` "
						+ "FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + activeType + "' AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"')");
			} else {
				nameNode = AccountModelManager.getInstance().getGroupByName(conds.get("department").toString()).getLabel();
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.finalCharge), 0) AS `finalCharge`", "column")
				.cond("SUBSTRING_INDEX(i.departmentCode, '/', -1) = '" + conds.get("department").toString() + "'")
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.ActivityType = '" + activeType + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total ), 0) AS `total` "
						+ "FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + activeType + "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
						+ "AND SUBSTRING_INDEX(t.departmentCode, '/', -1) = '" + conds.get("department").toString() + "')");
			}
		}
		
		/** TAB sản phẩm */
		if (conds.containsKey("product1")) {
			if (conds.get("product1").toString().isEmpty() && conds.get("product2").toString().isEmpty()) {
				rQuery.table("InvoiceItem i")
				.field("COALESCE(SUM(i.finalCharge), 0) AS `finalCharge`", "column")
				.cond("i.activityType  = '" + activeType + "' AND i.productCode IS NOT NULL")
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'");
			} else {
				String addQuery = "";
				if (!conds.get("product1").toString().isEmpty()) {
					addQuery = " INNER JOIN product p ON p.code = i.productCode INNER JOIN product_productTag t ON t.productId = p.Id INNER JOIN warehouse_productTag w ON w.id = t.productTagId";
					nameNode = ProductModelManager.getInstance().getProductTagByCode(conds.get("product1").toString()).getLabel();
					rQuery.cond("w.code = '" + conds.get("product1").toString() + "'");
				}
				if(!conds.get("product2").toString().isEmpty()) {
					nameNode = ProductModelManager.getInstance().getProductByCode(conds.get("product2").toString()).getName();
					rQuery.cond("i.productCode = '" + conds.get("product2").toString() + "'");
				}
				rQuery.table("InvoiceItem i" + addQuery)
							.field("COALESCE(SUM(i.finalCharge), 0) AS `finalCharge`", "column")
							.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
							.cond("i.activityType  = '" + activeType + "' AND i.productCode IS NOT NULL");
			}
		}
		
		/** TAB khu vực */
		if (conds.containsKey("location1")) {
			if(conds.get("location1").toString().isEmpty() && conds.get("location2").toString().isEmpty()){
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.finalCharge), 0) AS `finalCharge`", "column")
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.ActivityType = '" + activeType + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total ), 0) AS `total` FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + activeType + "' AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"')");
			} else {	
				if(!conds.get("location1").toString().isEmpty() && !conds.get("location2").toString().isEmpty()){
					nameNode = RestaurantModelManager.getInstance().getTableByCode(conds.get("location2").toString()).getName();			
					rQuery.table("InvoiceDetail i")
					.field("COALESCE(SUM(i.finalCharge), 0) AS `finalCharge`", "column")
					.cond("i.ActivityType = '" + activeType + "' ")
					.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
					.cond("i.locationCode = '"+ conds.get("location1").toString() +"' " )
					.cond("i.tableCode = '"+ conds.get("location2").toString() +"' " 
								+ "UNION ALL "
								+ "(SELECT COALESCE(SUM(t.total ), 0) AS `total` "
								+ "FROM InvoiceTransaction t "
								+ "WHERE t.ActivityType = '" + activeType + "' "
								+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
								+ "AND t.locationCode = '"+ conds.get("location1").toString() + "' "
								+ "AND t.tableCode = '"+ conds.get("location2").toString() + "')");
				} else {
					if(!conds.get("location1").toString().isEmpty()){
						nameNode = RestaurantModelManager.getInstance().getLocationByCode(conds.get("location1").toString()).getName();
						rQuery.table("InvoiceDetail i")
						.field("COALESCE(SUM(i.finalCharge), 0) AS `finalCharge`", "column")
						.cond("i.ActivityType = '" + activeType + "' ")
						.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
						.cond("i.locationCode = '"+ conds.get("location1").toString() +"' " 
									+ "UNION ALL "
									+ "(SELECT COALESCE(SUM(t.total ), 0) AS `total` "
									+ "FROM InvoiceTransaction t "
									+ "WHERE t.ActivityType = '" + activeType + "' "
									+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
									+ "AND t.locationCode = '"+ conds.get("location1").toString() +"')");
					} else {
						nameNode = RestaurantModelManager.getInstance().getTableByCode(conds.get("location2").toString()).getName();
						rQuery.table("InvoiceDetail i")
						.field("COALESCE(SUM(i.finalCharge), 0) AS `finalCharge`", "column")
						.cond("i.ActivityType = '" + activeType + "' ")			
						.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
						.cond("i.tableCode = '"+ conds.get("location2").toString() +"' " 
									+ "UNION ALL "
									+ "(SELECT COALESCE(SUM(t.total ), 0) AS `total` "
									+ "FROM InvoiceTransaction t "
									+ "WHERE t.ActivityType = '" + activeType + "' "
									+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
									+ "AND t.tableCode = '"+ conds.get("location2").toString() +"')");
					}
				}	
			}
		}
		
		/** TAB khách hàng */
		if (conds.containsKey("partner1")) {
			if(conds.get("partner1").toString().isEmpty() && conds.get("partner2").toString().isEmpty()){
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.finalCharge), 0) AS `finalCharge`", "column")
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.ActivityType = '" + activeType + "' "
							+ "UNION ALL "
							+ "(SELECT COALESCE(SUM(t.total ), 0) AS `total` "
							+ "FROM InvoiceTransaction t "
							+ "WHERE t.ActivityType = '" + activeType + "' AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"')");
			} else {
				if(!conds.get("partner1").toString().isEmpty() && conds.get("partner2").toString().isEmpty()){
					nameNode = AccountModelManager.getInstance().getGroupByName(conds.get("partner1").toString()).getLabel();
					rQuery.table("InvoiceDetail i")
								.field("COALESCE(SUM(i.finalCharge), 0) AS `finalCharge`", "column")
								.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
								.cond("SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '" + conds.get("partner1").toString() + "'")
								.cond("i.ActivityType = '" + activeType + "' "
										+ "UNION ALL "
										+ "(SELECT COALESCE(SUM(t.total ), 0) AS `total` "
										+ "FROM InvoiceTransaction t "
										+ "WHERE t.ActivityType = '" + activeType + "' "
										+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
										+ "AND SUBSTRING_INDEX(t.groupCustomerCode, '/', -1) = '" + conds.get("partner1").toString() + "')");
				} else {
					Customer c = CustomerModelManager.getInstance().getBydLoginId(conds.get("partner2").toString());
					nameNode = c.getName();	
					String	customerCode = c.getCode();
					if(!conds.get("partner1").toString().isEmpty() && !conds.get("partner2").toString().isEmpty()) {		
						rQuery.table("InvoiceDetail i")
									.field("COALESCE(SUM(i.finalCharge), 0) AS `finalCharge`", "column")
									.cond("SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '" + conds.get("partner1").toString() + "'")
									.cond("i.ActivityType = '" + activeType + "' ")
									.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
									.cond("i.employeeCode = '" + customerCode + "' "
											+ "UNION ALL "
											+ "(SELECT COALESCE(SUM(t.total ), 0) AS `total` "
											+ "FROM InvoiceTransaction t "
											+ "WHERE t.ActivityType = '" + activeType + "' "
											+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
											+ "AND SUBSTRING_INDEX(t.groupCustomerCode, '/', -1) = '" + conds.get("partner1").toString() + "' "
											+ "AND t.employeeCode = '" + customerCode + "')");
					} 
					if(conds.get("partner1").toString().isEmpty() && !conds.get("partner2").toString().isEmpty()) {	
						rQuery.table("InvoiceDetail i")
									.field("COALESCE(SUM(i.finalCharge), 0) AS `finalCharge`", "column")
									.cond("i.ActivityType = '" + activeType + "' ")
									.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
									.cond("i.employeeCode = '" + customerCode + "' "
											+ "UNION ALL "
											+ "(SELECT COALESCE(SUM(t.total ), 0) AS `total` "
											+ "FROM InvoiceTransaction t "
											+ "WHERE t.ActivityType = '" + activeType + "' "
											+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
											+ "AND t.employeeCode = '" + customerCode + "')");
					}
				}
			}
		}
		
		/** TAB nhân viên */		
		if (conds.containsKey("cashier1")) {
			if(conds.get("cashier1").toString().isEmpty() && conds.get("cashier2").toString().isEmpty()){
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.finalCharge), 0) AS `finalCharge`", "column")
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.ActivityType = '" + activeType + "' "
							+ "UNION ALL "
							+ "(SELECT COALESCE(SUM(t.total ), 0) AS `total` "
							+ "FROM InvoiceTransaction t "
							+ "WHERE t.ActivityType = '" + activeType + "' AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"')");
			} else {
				if(!conds.get("cashier1").toString().isEmpty() && conds.get("cashier2").toString().isEmpty()){
					nameNode = AccountModelManager.getInstance().getGroupByName(conds.get("cashier1").toString()).getLabel();
					rQuery.table("InvoiceDetail i")
								.field("COALESCE(SUM(i.finalCharge), 0) AS `finalCharge`", "column")
								.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
								.cond("SUBSTRING_INDEX(i.departmentCode, '/', -1) = '" + conds.get("cashier1").toString() + "' ")
								.cond("i.ActivityType = '" + activeType + "' "
											+ "UNION ALL "
											+ "(SELECT COALESCE(SUM(t.total ), 0) AS `total` "
											+ "FROM InvoiceTransaction t "
											+ "WHERE t.ActivityType = '" + activeType + "' "
											+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
											+ "AND SUBSTRING_INDEX(t.departmentCode, '/', -1) = '" + conds.get("cashier1").toString() + "')");
				} else {
					Employee e = HRModelManager.getInstance().getBydLoginId(conds.get("cashier2").toString());
					nameNode = e.getName();	
					String	employeeCode = e.getCode();
					if(!conds.get("cashier1").toString().isEmpty() && !conds.get("cashier2").toString().isEmpty()){
						rQuery.table("InvoiceDetail i")
									.field("COALESCE(SUM(i.finalCharge), 0) AS `finalCharge`", "column")
									.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
									.cond("SUBSTRING_INDEX(i.departmentCode, '/', -1) = '" + conds.get("cashier1").toString() + "' ")
									.cond("i.employeeCode = '" + employeeCode + "' ")
									.cond("i.ActivityType = '" + activeType + "' "
												+ "UNION ALL "
												+ "(SELECT COALESCE(SUM(t.total ), 0) AS `total` FROM InvoiceTransaction t "
												+ "WHERE t.ActivityType = '" + activeType + "' "
												+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
												+ "AND SUBSTRING_INDEX(t.departmentCode, '/', -1) = '" + conds.get("cashier1").toString() + "' "
												+ "AND t.employeeCode = '"+ employeeCode +"')");
					} else {
						rQuery.table("InvoiceDetail i")
									.field("COALESCE(SUM(i.finalCharge), 0) AS `finalCharge`", "column")
									.cond("i.employeeCode = '" + employeeCode + "' ")
									.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
									.cond("i.ActivityType = '" + activeType + "' "
												+ "UNION ALL "
												+ "(SELECT COALESCE(SUM(t.total ), 0) AS `total` "
												+ "FROM InvoiceTransaction t "
												+ "WHERE t.ActivityType = '" + activeType + "' "
												+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
												+ "AND t.employeeCode = '"+ employeeCode +"')");
					}
				}
			}	
		}
		
		/** TAB cửa hàng */
		if (conds.containsKey("store")) {
			if (conds.get("store").toString().isEmpty()) {
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.finalCharge), 0) AS `finalCharge`", "column")
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.ActivityType = '" + activeType + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total ), 0) AS `total` "
						+ "FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + activeType + "' AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"')");
			} else {
				OptionGroup o = GenericOptionModelManager.getInstance().getOptionGroup("restaurant","RestaurantService", "store");
				nameNode = o.getOption(conds.get("store").toString()).getLabel();
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.finalCharge), 0) AS `finalCharge`", "column")
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("SUBSTRING_INDEX(i.storeCode, '/', -1) = '" + conds.get("store").toString() + "'")
				.cond("i.ActivityType = '" + activeType + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total ), 0) AS `total` "
						+ "FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + activeType + "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
						+ "AND SUBSTRING_INDEX(t.storeCode, '/', -1) = '" + conds.get("store").toString() + "')");
			}
		}
		
		/** TAB dự án */
		if (conds.containsKey("project")) {
			if (conds.get("project").toString().isEmpty()) {
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.finalCharge), 0) AS `finalCharge`", "column")
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.ActivityType = '" + activeType + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total ), 0) AS `total` "
						+ "FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + activeType + "' AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"')");
			} else {
				nameNode = RestaurantModelManager.getInstance().getProjectByCode(conds.get("project").toString()).getName();
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.finalCharge), 0) AS `finalCharge`", "column")
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("SUBSTRING_INDEX(i.projectCode, '/', -1) = '" + conds.get("project").toString() + "'")
				.cond("i.ActivityType = '" + activeType + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total ), 0) AS `total` "
						+ "FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + activeType + "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
						+ "AND SUBSTRING_INDEX(t.projectCode, '/', -1) = '" + conds.get("project").toString() + "')");
			}
		}
		
		return rQuery;
	}

	private SQLSelectQuery sqlQuery_ThuChiLai() throws Exception {
		SQLSelectQuery rQuery = new SQLSelectQuery();
		
		/** TAB phòng ban */
		if (conds.containsKey("department")) {
			if (conds.get("department").toString().isEmpty()) {
				rQuery.table("InvoiceTransaction i")
				.field("COALESCE(SUM(i.total), 0) AS `totalRec`", "column")
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total ), 0) AS `totalPay` "
						+ "FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"')");
			} else {
				nameNode = AccountModelManager.getInstance().getGroupByName(conds.get("department").toString()).getLabel();
				rQuery.table("InvoiceTransaction i")
				.field("COALESCE(SUM(i.total), 0) AS `totalRec`", "column")
				.cond("SUBSTRING_INDEX(i.departmentCode, '/', -1) = '" + conds.get("department").toString() + "'")
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total ), 0) AS `totalPay` "
						+ "FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
						+ "AND SUBSTRING_INDEX(t.departmentCode, '/', -1) = '" + conds.get("department").toString() + "')");
			}
		}
		
		/** TAB sản phẩm */
		if (conds.containsKey("product1")) {
			if (conds.get("product1").toString().isEmpty() && conds.get("product2").toString().isEmpty()) {
				rQuery.table("InvoiceItem i")
				.field("COALESCE(SUM(i.finalCharge), 0) AS `finalChargeReceipt`", "column")
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.activityType  = '" + ActivityType.Receipt + "' AND i.productCode IS NOT NULL "
							+ "UNION ALL "
							+ "(SELECT COALESCE(SUM(it.finalCharge * it.currencyRate), 0) AS `finalChargePayment` "
							+ "FROM InvoiceItem it "
							+ "WHERE it.activityType = '"+ ActivityType.Payment +"' "
							+ "AND it.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
							+ "AND it.productCode IS NOT NULL)");
			} else {
				if (!conds.get("product1").toString().isEmpty() && conds.get("product2").toString().isEmpty() ) {	
					nameNode = ProductModelManager.getInstance().getProductTagByCode(conds.get("product1").toString()).getLabel();
					rQuery.table("InvoiceItem i " +
											 "INNER JOIN product p ON p.code = i.productCode " +
											 "INNER JOIN product_productTag t ON t.productId = p.Id " +
											 "INNER JOIN warehouse_productTag w ON w.id = t.productTagId")
								.field("COALESCE(SUM(i.finalCharge), 0) AS `finalCharge`", "column")
								.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
								.cond("i.activityType  = '" + ActivityType.Receipt + "' AND i.productCode IS NOT NULL")
								.cond("w.code = '" + conds.get("product1").toString() + "' "
									+ "UNION ALL "
									+ "(SELECT COALESCE(SUM(it.finalCharge * it.currencyRate), 0) AS `finalChargePayment` "
									+ "FROM InvoiceItem it "
									+ "INNER JOIN product p ON p.code = it.productCode "
									+ "INNER JOIN product_productTag t ON t.productId = p.Id "
									+ "INNER JOIN warehouse_productTag w ON w.id = t.productTagId "
									+ "WHERE it.activityType = '"+ ActivityType.Payment +"' "
									+ "AND it.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
									+ "AND it.productCode IS NOT NULL "
									+ "AND w.code = '" + conds.get("product1").toString() + "')");					
				} else {
					nameNode = ProductModelManager.getInstance().getProductByCode(conds.get("product2").toString()).getName();
					if(!conds.get("product1").toString().isEmpty() && !conds.get("product2").toString().isEmpty()){
						rQuery.table("InvoiceItem i " +
												 "INNER JOIN product p ON p.code = i.productCode " +
												 "INNER JOIN product_productTag t ON t.productId = p.Id " +
												 "INNER JOIN warehouse_productTag w ON w.id = t.productTagId")
						.field("COALESCE(SUM(i.finalCharge), 0) AS `finalCharge`", "column")
						.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
						.cond("i.activityType  = '" + ActivityType.Receipt + "' AND i.productCode IS NOT NULL")
						.cond("w.code = '" + conds.get("product1").toString() + "'")
						.cond("i.productCode = '" + conds.get("product2").toString() + "' "
									+ "UNION ALL "
									+ "(SELECT COALESCE(SUM(it.finalCharge * it.currencyRate), 0) AS `finalChargePayment` "
									+ "FROM InvoiceItem it "
									+ "INNER JOIN product p ON p.code = it.productCode "
									+ "INNER JOIN product_productTag t ON t.productId = p.Id "
									+ "INNER JOIN warehouse_productTag w ON w.id = t.productTagId "
									+ "WHERE it.activityType = '"+ ActivityType.Payment +"' "
									+ "AND it.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
									+ "AND it.productCode IS NOT NULL "
									+ "AND w.code = '" + conds.get("product1").toString() + "' "
									+ "AND it.productCode = '" + conds.get("product2").toString() + "')");
					} else {
						rQuery.table("InvoiceItem i")
						.field("COALESCE(SUM(i.finalCharge), 0) AS `finalCharge`", "column")
						.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
						.cond("i.activityType  = '" + ActivityType.Receipt + "' AND i.productCode IS NOT NULL")
						.cond("i.productCode = '" + conds.get("product2").toString() + "' "
									+ "UNION ALL "
									+ "(SELECT COALESCE(SUM(it.finalCharge * it.currencyRate), 0) AS `finalChargePayment` "
									+ "FROM InvoiceItem it "
									+ "WHERE it.activityType = '"+ ActivityType.Payment +"' "
									+ "AND it.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
									+ "AND it.productCode IS NOT NULL "
									+ "AND it.productCode = '" + conds.get("product2").toString() + "')");
					}
				}
			}
		}
		
		/** TAB khu vực */
		if (conds.containsKey("location1")) {
			if(conds.get("location1").toString().isEmpty() && conds.get("location2").toString().isEmpty()){
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.total), 0) AS `finalCharge`", "column")
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total ), 0) AS `total` "
						+ "FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"')");
			} else {			
				if(!conds.get("location1").toString().isEmpty() && conds.get("location2").toString().isEmpty()){
					nameNode = RestaurantModelManager.getInstance().getLocationByCode(conds.get("location1").toString()).getName();
					rQuery.table("InvoiceDetail i")
					.field("COALESCE(SUM(i.total), 0) AS `finalCharge`", "column")
					.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
					.cond("i.ActivityType = '" + ActivityType.Receipt + "' ")
					.cond("i.locationCode = '"+ conds.get("location1").toString() +"' " 
							+ "UNION ALL "
							+ "(SELECT COALESCE(SUM(t.total ), 0) AS `total` "
							+ "FROM InvoiceTransaction t "
							+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
							+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
							+ "AND t.locationCode = '"+ conds.get("location1").toString() +"')");
				} else {
					if(!conds.get("location2").toString().isEmpty() && !conds.get("location1").toString().isEmpty()){
						nameNode = RestaurantModelManager.getInstance().getTableByCode(conds.get("location2").toString()).getName();						
						rQuery.table("InvoiceDetail i")
						.field("COALESCE(SUM(i.total), 0) AS `finalCharge`", "column")
						.cond("i.ActivityType = '" + ActivityType.Receipt + "' ")
						.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
						.cond("i.locationCode = '"+ conds.get("location1").toString() +"' " )
						.cond("i.tableCode = '"+ conds.get("location2").toString() +"' " 
								+ "UNION ALL "
								+ "(SELECT COALESCE(SUM(t.total ), 0) AS `total` "
								+ "FROM InvoiceTransaction t "
								+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
								+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
								+ "AND t.locationCode = '"+ conds.get("location1").toString() +"' "
								+ "AND t.tableCode = '"+ conds.get("location2").toString()  +"')");
					} else {
						nameNode = RestaurantModelManager.getInstance().getTableByCode(conds.get("location2").toString()).getName();
						rQuery.table("InvoiceDetail i")
						.field("COALESCE(SUM(i.total), 0) AS `finalCharge`", "column")
						.cond("i.ActivityType = '" + ActivityType.Receipt + "' ")			
						.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
						.cond("i.tableCode = '"+ conds.get("location2").toString() +"' " 
								+ "UNION ALL "
								+ "(SELECT COALESCE(SUM(t.total ), 0) AS `total` "
								+ "FROM InvoiceTransaction t "
								+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
								+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
								+ "AND t.tableCode = '"+ conds.get("location2").toString() +"')");
					}
				}			
			}
		}
		
		/** TAB khách hàng */
		if (conds.containsKey("partner1")) {
			if(conds.get("partner1").toString().isEmpty() && conds.get("partner2").toString().isEmpty()){
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.total), 0) AS `finalCharge`", "column")
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total ), 0) AS `total` "
						+ "FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"')");
			} else {
				if(!conds.get("partner1").toString().isEmpty() && conds.get("partner2").toString().isEmpty()){
					nameNode = AccountModelManager.getInstance().getGroupByName(conds.get("partner1").toString()).getLabel();
					rQuery.table("InvoiceDetail i")
					.field("COALESCE(SUM(i.total), 0) AS `finalCharge`", "column")
					.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
					.cond("SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '" + conds.get("partner1").toString() + "'")
					.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
							+ "UNION ALL "
							+ "(SELECT COALESCE(SUM(t.total ), 0) AS `total` "
							+ "FROM InvoiceTransaction t "
							+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
							+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
							+ "AND SUBSTRING_INDEX(t.groupCustomerCode, '/', -1) = '" + conds.get("partner1").toString() + "')");
				} else {
					Customer c = CustomerModelManager.getInstance().getBydLoginId(conds.get("partner2").toString());
					nameNode = c.getName();	
					String	empCode = c.getCode();
					if(!conds.get("partner1").toString().isEmpty() && !conds.get("partner2").toString().isEmpty()){
						rQuery.table("InvoiceDetail i")	
						.field("COALESCE(SUM(i.total), 0) AS `finalCharge`", "column")
						.cond("SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '" + conds.get("partner1").toString() + "'")
						.cond("i.ActivityType = '" + ActivityType.Receipt + "' ")
						.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
						.cond("i.employeeCode = '" + empCode + "' "
								+ " UNION ALL "
								+ "(SELECT COALESCE(SUM(t.total ), 0) AS `total` "
								+ "FROM InvoiceTransaction t "
								+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
								+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
								+ "AND SUBSTRING_INDEX(t.groupCustomerCode, '/', -1) = '" + conds.get("partner1").toString() + "' "
								+ "AND t.employeeCode = '" + empCode + "')");
					} else {
						rQuery.table("InvoiceDetail i")
						.field("COALESCE(SUM(i.total), 0) AS `finalCharge`", "column")
						.cond("i.ActivityType = '" + ActivityType.Receipt + "' ")
						.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
						.cond("i.employeeCode = '" + empCode + "' "
								+ " UNION ALL "
								+ "(SELECT COALESCE(SUM(t.total ), 0) AS `total` "
								+ "FROM InvoiceTransaction t "
								+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
								+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
								+ "AND t.employeeCode = '" + empCode + "')");
					}
				}
			}			
		}
		
		/** TAB nhân viên */
		if (conds.containsKey("cashier1")) {
			if(conds.get("cashier1").toString().isEmpty() && conds.get("cashier2").toString().isEmpty()){
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.total), 0) AS `finalCharge`", "column")
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total ), 0) AS `total` "
						+ "FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"')");
			} else {
				if(!conds.get("cashier1").toString().isEmpty() && conds.get("cashier2").toString().isEmpty()){
					nameNode = AccountModelManager.getInstance().getGroupByName(conds.get("cashier1").toString()).getLabel();
					rQuery.table("InvoiceDetail i")
					.field("COALESCE(SUM(i.total), 0) AS `finalCharge`", "column")
					.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
					.cond("SUBSTRING_INDEX(i.departmentCode, '/', -1) = '" + conds.get("cashier1").toString() + "' ")
					.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
							+ "UNION ALL "
							+ "(SELECT COALESCE(SUM(t.total ), 0) AS `total` "
							+ "FROM InvoiceTransaction t "
							+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
							+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
							+ "AND SUBSTRING_INDEX(t.departmentCode, '/', -1) = '" + conds.get("cashier1").toString() + "')");					
				} else {
					nameNode = HRModelManager.getInstance().getBydLoginId(conds.get("cashier2").toString()).getName();
					if(!conds.get("cashier1").toString().isEmpty() && !conds.get("cashier2").toString().isEmpty()){											
						rQuery.table("InvoiceDetail i INNER JOIN InvoiceTransaction it on it.invoiceId = i.id")
						.field("COALESCE(SUM(i.total), 0) AS `finalCharge`", "column")
						.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
						.cond("SUBSTRING_INDEX(i.departmentCode, '/', -1) = '" + conds.get("cashier1").toString() + "' ")
						.cond("i.employeeCode = '" + conds.get("cashier2").toString() + "' ")
						.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
								+ "UNION ALL "
								+ "(SELECT COALESCE(SUM(t.total ), 0) AS `total` "
								+ "FROM InvoiceTransaction t "
								+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
								+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
								+ "AND SUBSTRING_INDEX(t.departmentCode, '/', -1) = '" + conds.get("cashier1").toString() + "' "
								+ "AND t.employeeCode = '"+ conds.get("cashier2").toString() +"')");
					} else {
						rQuery.table("InvoiceDetail i INNER JOIN InvoiceTransaction it on it.invoiceId = i.id")
						.field("COALESCE(SUM(i.total), 0) AS `finalCharge`", "column")
						.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
						.cond("i.employeeCode = '" + conds.get("cashier2").toString() + "' ")
						.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
								+ "UNION ALL "
								+ "(SELECT COALESCE(SUM(t.total ), 0) AS `total` "
								+ "FROM InvoiceTransaction t "
								+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
								+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
								+ "AND t.employeeCode = '"+ conds.get("cashier2").toString() +"')");
					}
				}
			}	
		}
		
		/** TAB cửa hàng */
		if (conds.containsKey("store")) {
			if (conds.get("store").toString().isEmpty()) {
				rQuery.table("InvoiceTransaction i")
				.field("COALESCE(SUM(i.total), 0) AS `totalRec`", "column")
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total ), 0) AS `totalPay` "
						+ "FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"')");
			} else {
				OptionGroup o = GenericOptionModelManager.getInstance().getOptionGroup("restaurant","RestaurantService", "store");
				nameNode = o.getOption(conds.get("store").toString()).getLabel();
				rQuery.table("InvoiceTransaction i")
				.field("COALESCE(SUM(i.total), 0) AS `totalRec`", "column")
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("SUBSTRING_INDEX(i.storeCode, '/', -1) = '" + conds.get("store").toString() + "'")
				.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total ), 0) AS `totalPay` "
						+ "FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
						+ "AND SUBSTRING_INDEX(t.storeCode, '/', -1) = '" + conds.get("store").toString() + "')");
			}
		}
		
		/** TAB dự án */
		if (conds.containsKey("project")) {
			if (conds.get("project").toString().isEmpty()) {
				rQuery.table("InvoiceTransaction i")
				.field("COALESCE(SUM(i.total), 0) AS `totalRec`", "column")
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total ), 0) AS `totalPay` "
						+ "FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"')");
			} else {
				nameNode = RestaurantModelManager.getInstance().getProjectByCode(conds.get("project").toString()).getName();
				rQuery.table("InvoiceTransaction i")
				.field("COALESCE(SUM(i.total), 0) AS `totalRec`", "column")
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("SUBSTRING_INDEX(i.projectCode, '/', -1) = '" + conds.get("project").toString() + "'")
				.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total ), 0) AS `totalPay` "
						+ "FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
					  + "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
					  + "AND SUBSTRING_INDEX(t.projectCode, '/', -1) = '" + conds.get("project").toString() + "')");
			}
		}
		
		return rQuery;
	}
	
	private ReportForTimeEntity getRowFromQuery(String[] row){	
		double sumFinal = 0;
		double sumTotal = 0;
		double sumFinal0 = 0;
		double sumTotal0 = 0;
		double sumPhaiThu = 0;
		double sumPhaiChi = 0;
		double ratio = 0;
		if (viewMoney.equals("Nghìn")) {
			sumFinal = Double.parseDouble(row[0].toString()) / 1000;
			if(row.length == 2){
				sumTotal = Double.parseDouble(row[1].toString()) / 1000;
			}	
			if(row.length > 2){
				sumTotal = Double.parseDouble(row[1].toString()) / 1000;
				sumFinal0 = Double.parseDouble(row[2].toString()) / 1000;
				sumTotal0 = Double.parseDouble(row[3].toString()) / 1000;
				sumPhaiThu = sumFinal - sumTotal;
				sumPhaiChi = sumFinal0 - sumTotal0;
			}
		} else if (viewMoney.equals("Triệu")) {
			sumFinal = Double.parseDouble(row[0].toString()) / 1000000;
			if(row.length == 2){
				sumTotal = Double.parseDouble(row[1].toString()) / 1000000;
			}
			if(row.length > 2){
				sumTotal = Double.parseDouble(row[1].toString()) / 1000000;
				sumFinal0 = Double.parseDouble(row[2].toString()) / 1000000;
				sumTotal0 = Double.parseDouble(row[3].toString()) / 1000000;
				sumPhaiThu = sumFinal - sumTotal;
				sumPhaiChi = sumFinal0 - sumTotal0;
			}
		} else {
			sumFinal = Double.parseDouble(row[0].toString());
			if(row.length == 2){
				sumTotal = Double.parseDouble(row[1].toString());
			}
			if(row.length > 2){
				sumTotal = Double.parseDouble(row[1].toString());
				sumFinal0 = Double.parseDouble(row[2].toString());
				sumTotal0 = Double.parseDouble(row[3].toString());
				sumPhaiThu = sumFinal - sumTotal;
				sumPhaiChi = sumFinal0 - sumTotal0;
			}
		}
		ReportForTimeEntity entity = null;
		if(row.length <= 2){
			if (typeReport == 2)
				ratio = sumFinal - sumTotal; 
			else ratio = (sumTotal / sumFinal) * 100;
			entity = new ReportForTimeEntity(nameNode, new MyDouble(sumFinal).toString(), new MyDouble(sumTotal).toString(), new MyDouble(ratio).toString(), 0);
		} else {
			ratio = sumTotal - sumTotal0;
			entity = new ReportForTimeEntity(nameNode, new MyDouble(sumFinal).toString(), new MyDouble(sumFinal0).toString(), new MyDouble(sumTotal).toString(), new MyDouble(sumTotal0).toString(), new MyDouble(sumPhaiThu).toString(), new MyDouble(sumPhaiChi).toString(), new MyDouble(ratio).toString(), 0);
		}
		entity.setType(Type.ROOT);
	
		return entity;
	}

	private String[] runQuery(SQLSelectQuery rQuery) throws Exception{
		System.out.println(rQuery.createSQLQuery());
		ReportTable[] reportTable = AccountingModelManager.getInstance().reportQuery(new SQLSelectQuery[] { rQuery });
		reportTable[0].dump(new String[] { "column" });
		List<Map<String, Object>> records = reportTable[0].getRecords();
		String[] value = new String[records.size()];
		for (int i = 0; i < records.size(); i++) {
			Object obj = records.get(i).get("column");
			value[i] = obj.toString();
		}
		return value;
	}
}
