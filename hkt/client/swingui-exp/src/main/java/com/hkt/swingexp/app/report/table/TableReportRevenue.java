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
import com.hkt.swingexp.app.report.entity.ReportRevenue;
import com.hkt.swingexp.app.report.entity.ReportRevenue.Type;
import com.hkt.swingexp.app.report.modeltable.ModelTableReportRevenue;

public class TableReportRevenue extends JTable {
	private ModelTableReportRevenue	model;
	private HashMap<String, String>	conds;
	private String									viewMoney;
	private String									nameNode;
	private String									typeReport;
	private String									dateStart					= null;
	private String									dateEnd						= null;
	private boolean 								isCheck;

	public TableReportRevenue(HashMap<String, String> conds, String typeReport, Date dateStart, Date dateEnd, String viewMoney, boolean isCheck) {
		this.conds = conds;
		this.viewMoney = viewMoney;
		this.typeReport = typeReport;
		this.isCheck = isCheck;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendarStart = Calendar.getInstance();
		Calendar calendarEnd = Calendar.getInstance();
		if (dateStart != null) {
			calendarStart.setTime(dateStart);
			calendarStart.set(Calendar.HOUR_OF_DAY, 00);
			calendarStart.set(Calendar.MINUTE, 00);
			calendarStart.set(Calendar.SECOND, 00);
		}
		if (dateEnd != null) {
			calendarEnd.setTime(dateEnd);
			calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
			calendarEnd.set(Calendar.MINUTE, 59);
			calendarEnd.set(Calendar.SECOND, 59);
		}
		
		this.dateStart = dateFormat.format(calendarStart.getTime());
		this.dateEnd = dateFormat.format(calendarEnd.getTime());
		try {
			nameNode = AccountModelManager.getInstance().getNameByLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
		} catch(Exception e){
			nameNode = "";
		}
		
		try {
			if (typeReport.equals("Chi phí")) {
				model = new ModelTableReportRevenue(1, getNodeParent());
				this.setModel(model);
			} else if (typeReport.equals("Doanh thu")) {
				model = new ModelTableReportRevenue(0, getNodeParent());
				this.setModel(model);
			} else if (typeReport.equals("Thu - Chi - Lãi")) {
				if (conds.containsKey("product1")) {
					model = new ModelTableReportRevenue(4, getNodeParent());
					this.setModel(model);
				} else {
					model = new ModelTableReportRevenue(2, getNodeParent());
					this.setModel(model);
				}
			} else {
				model = new ModelTableReportRevenue(3, getNodeParent());
				this.setModel(model);
			}
			for (int i = 0; i < this.getColumnCount(); i++) {
				this.getColumnModel().getColumn(i).setCellRenderer(new TableRerenderRevenue(model, typeReport, conds, dateStart, dateEnd, viewMoney,isCheck));
				this.getColumnModel().getColumn(i).setCellEditor(new TableRerenderRevenue(model, typeReport, conds, dateStart, dateEnd, viewMoney, isCheck));
			}
			model.fireTableDataChanged();
			this.setModel(model);
		} catch (Exception e) {
			model = new ModelTableReportRevenue(2,new ArrayList<ReportRevenue>());
			this.setModel(model);
			
			if (typeReport.equals("Chi phí")) {
				model = new ModelTableReportRevenue(1, new ArrayList<ReportRevenue>());
				this.setModel(model);
			} else if (typeReport.equals("Doanh thu")) {
				model = new ModelTableReportRevenue(0,  new ArrayList<ReportRevenue>());
				this.setModel(model);
			} else if (typeReport.equals("Thu - Chi - Lãi")) {
				if (conds.containsKey("product1")) {
					model = new ModelTableReportRevenue(4, new ArrayList<ReportRevenue>());
					this.setModel(model);
				} else {
					model = new ModelTableReportRevenue(2, new ArrayList<ReportRevenue>());
					this.setModel(model);
				}
			} else {
				model = new ModelTableReportRevenue(3,new ArrayList<ReportRevenue>());
				this.setModel(model);
			}
			
		}		
		this.setRowHeight(23);
		this.getColumnModel().getColumn(0).setMinWidth(300);
		this.setFont(new Font("Tahoma", 0, 14));
		this.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		this.setBackground(Color.WHITE);
		this.setFillsViewportHeight(true);
	}
	
	public void setReportGui(ReportStatistics reportStatistics){
		model.setReportStatistics(reportStatistics);
	}

	private List<ReportRevenue> getNodeParent() throws Exception {
		List<ReportRevenue> employees = new ArrayList<ReportRevenue>();
		if (typeReport.equals("Doanh thu")) {	
			String[] row = runQuery(querySQL_DoanhThu());
			employees.add(getRowFromQuery(row));
		} else if (typeReport.equals("Chi phí")) {
			String[] row = runQuery(querySQL_ChiPhi());
			employees.add(getRowFromQuery(row));
		} else if (typeReport.equals("Thu - Chi - Lãi")) {
			String[] row = runQuery(querySQL_ThuChiLai());
			employees.add(getRowFromQuery(row));
		} else if (typeReport.equals("Thu - Chi - Công nợ")) {
			String[] row1 = runQuery(querySQL_DoanhThu());
			String[] row2 = runQuery(querySQL_ChiPhi());
			String[] row = new String[] {row1[0], row1[1], row2[0], row2[1]};
			employees.add(getRowFromQuery(row));
		}
		return employees;
	}
	
	private SQLSelectQuery querySQL_DoanhThu() throws Exception {
		/**
		 ********************************
		 * ******************************
		 * ******************************
		 * PHIÊN BẢN MỚI - ĐÃ GỘP BẢNG  *
		 * ******************************
		 * ******************************
		 ******************************** 
		 */
		
		SQLSelectQuery rQuery = new SQLSelectQuery();
		
		/** TAB phòng ban */
		if (conds.containsKey("department")) {
			if (conds.get("department").toString().isEmpty()) {
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
				// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "'")
				.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Receipt + "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien 
						+ "AND t.transactionDate >= '" + dateStart + "' "
						+ "AND t.transactionDate <= '" + dateEnd + "')");
			} else {
				nameNode = AccountModelManager.getInstance().getGroupByName(conds.get("department").toString()).getLabel();
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
				// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("SUBSTRING_INDEX(i.departmentCode, '/', -1) = '" + conds.get("department").toString() + "'")
				.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "'")
				.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Receipt + "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien
						+ "AND SUBSTRING_INDEX(t.departmentCode, '/', -1) = '" + conds.get("department").toString() + "' "
						+ "AND t.transactionDate >= '" + dateStart + "' "
						+ "AND t.transactionDate <= '" + dateEnd + "')");
			}
		}
		
		/** TAB sản phẩm */
		if (conds.containsKey("product1")) {
			if (conds.get("product1").toString().isEmpty() && conds.get("product2").toString().isEmpty()) {
				rQuery.table("InvoiceItem i")
				.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
				// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.activityType  = '" + ActivityType.Receipt + "' AND i.productCode IS NOT NULL")
				.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "' ");
			} else {
				rQuery.table("InvoiceItem i " +
										 "INNER JOIN product p ON p.code = i.productCode " +
										 "INNER JOIN product_productTag t ON t.productId = p.Id " +
										 "INNER JOIN warehouse_productTag w ON w.id = t.productTagId")
				.field("COALESCE(SUM(i.finalCharge * i.currencyRate ),0) AS `finalCharge`", "column")
				.cond("i.activityType  = '" + ActivityType.Receipt + "' AND i.productCode IS NOT NULL")
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "' ");
				if (!conds.get("product1").toString().isEmpty()) {
					nameNode = ProductModelManager.getInstance().getProductTagByCode(conds.get("product1").toString()).getLabel();
					rQuery.cond("w.code = '" + conds.get("product1").toString() + "'");
				}
				if(!conds.get("product2").toString().isEmpty()) {
					nameNode = ProductModelManager.getInstance().getProductByCode(conds.get("product2").toString()).getName();
					rQuery.cond("i.productCode = '" + conds.get("product2").toString() + "'");
				}
			}
		}
		
		/** TAB khu vực */		
		if (conds.containsKey("location1")) {
			if(conds.get("location1").toString().isEmpty() && conds.get("location2").toString().isEmpty()){
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
				// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "'")
				.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Receipt + "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien 
						+ "AND t.transactionDate >= '" + dateStart + "' "
						+ "AND t.transactionDate <= '" + dateEnd + "')");
			}else {			
				if(!conds.get("location1").toString().isEmpty() && conds.get("location2").toString().isEmpty()){
					nameNode = RestaurantModelManager.getInstance().getLocationByCode(conds.get("location1").toString()).getName();
					rQuery.table("InvoiceDetail i")
					.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
					// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
					.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
					.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "'")
					.cond("i.ActivityType = '" + ActivityType.Receipt + "' ")
					.cond("i.locationCode = '"+ conds.get("location1").toString() +"' " 
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Receipt + "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien 
						+ "AND t.locationCode = '"+ conds.get("location1").toString() +"' "
						+ "AND t.transactionDate >= '" + dateStart + "' "
						+ "AND t.transactionDate <= '" + dateEnd + "')");
				}else {
					if(!conds.get("location2").toString().isEmpty() && !conds.get("location1").toString().isEmpty()){
						nameNode = RestaurantModelManager.getInstance().getTableByCode(conds.get("location2").toString()).getName();						
						rQuery.table("InvoiceDetail i")
						.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
						// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
						.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
						.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "'")
						.cond("i.ActivityType = '" + ActivityType.Receipt + "' ")
						.cond("i.locationCode = '"+ conds.get("location1").toString() +"' " )
						.cond("i.tableCode = '"+ conds.get("location2").toString() +"' " 
								+ "UNION ALL "
								+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
								+ "WHERE t.ActivityType = '" + ActivityType.Receipt + "' "
								+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien 
								+ "AND t.locationCode = '"+ conds.get("location1").toString() +"' "
								+ "AND t.tableCode = '"+ conds.get("location2").toString()  +"' "
								+ "AND t.transactionDate >= '" + dateStart + "' "
								+ "AND t.transactionDate <= '" + dateEnd + "')");
					}else {
						nameNode = RestaurantModelManager.getInstance().getTableByCode(conds.get("location2").toString()).getName();						
						rQuery.table("InvoiceDetail i")
						.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
						// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
						.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
						.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "'")
						.cond("i.ActivityType = '" + ActivityType.Receipt + "' ")						
						.cond("i.tableCode = '"+ conds.get("location2").toString() +"' " 
								+ "UNION ALL "
								+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
								+ "WHERE t.ActivityType = '" + ActivityType.Receipt + "' "
								+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien 
								+ "AND t.tableCode = '"+ conds.get("location2").toString() +"' "
								+ "AND t.transactionDate >= '" + dateStart + "' "
								+ "AND t.transactionDate <= '" + dateEnd + "')");
					}
				}				
			}
		}
		
		/** TAB khách hàng */
		if (conds.containsKey("partner1")) {
			if(conds.get("partner1").toString().isEmpty() && conds.get("partner2").toString().isEmpty()){
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
				// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "'")
				.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Receipt + "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien
						+ "AND t.transactionDate >= '" + dateStart + "' "
						+ "AND t.transactionDate <= '" + dateEnd + "')");
			}else {
				if(!conds.get("partner1").toString().isEmpty() && conds.get("partner2").toString().isEmpty()){
					nameNode = AccountModelManager.getInstance().getGroupByName(conds.get("partner1").toString()).getLabel();
					rQuery.table("InvoiceDetail i")
					.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
					// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
					.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
					.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "'")
					.cond("SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '" + conds.get("partner1").toString() + "'")
					.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
							+ "UNION ALL "
							+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
							+ "WHERE t.ActivityType = '" + ActivityType.Receipt + "' "
							+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien
							+ "AND SUBSTRING_INDEX(t.groupCustomerCode, '/', -1) = '" + conds.get("partner1").toString() + "' "
							+ "AND t.transactionDate >= '" + dateStart + "' AND t.transactionDate <= '" + dateEnd + "')");
				}else {
					Customer c = CustomerModelManager.getInstance().getBydLoginId(conds.get("partner2").toString());
					nameNode = c.getName();	
					String	empCode = c.getCode();
					if(!conds.get("partner1").toString().isEmpty() && !conds.get("partner2").toString().isEmpty()){
						rQuery.table("InvoiceDetail i")
						.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
						// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
						.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
						.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "'")
						.cond("SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '" + conds.get("partner1").toString() + "'")
						.cond("i.ActivityType = '" + ActivityType.Receipt + "' ")
						.cond("i.customerCode = '" + empCode + "' "
								+ "UNION ALL "
								+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
								+ "WHERE t.ActivityType = '" + ActivityType.Receipt + "' "
								+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien
								+ "AND SUBSTRING_INDEX(t.groupCustomerCode, '/', -1) = '" + conds.get("partner1").toString() + "' "
								+ "AND t.transactionDate >= '" + dateStart + "' AND t.customerCode = '" + empCode + "' "
								+ "AND t.transactionDate <= '" + dateEnd + "')");
					} else {						
						rQuery.table("InvoiceDetail i")
						.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
						// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
						.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
						.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "' ")
						.cond("i.ActivityType = '" + ActivityType.Receipt + "' ")
						.cond("i.customerCode = '" + empCode + "' "
								+ "UNION ALL "
								+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
								+ "WHERE t.ActivityType = '" + ActivityType.Receipt + "' "
								+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien
								+ "AND t.transactionDate >= '" + dateStart + "' "
								+ "AND t.customerCode = '" + empCode + "' "
								+ "AND t.transactionDate <= '" + dateEnd + "')");
					}
				}
			}			
		}
		
		/** TAB nhân viên */		
		if (conds.containsKey("cashier1")) {
			if(conds.get("cashier1").toString().isEmpty() && conds.get("cashier2").toString().isEmpty()){
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
				// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "'")
				.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Receipt + "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien
						+ "AND t.transactionDate >= '" + dateStart + "' "
						+ "AND t.transactionDate <= '" + dateEnd + "')");
			} else {				
				if(!conds.get("cashier1").toString().isEmpty() && conds.get("cashier2").toString().isEmpty()){
					nameNode = AccountModelManager.getInstance().getGroupByName(conds.get("cashier1").toString()).getLabel();
					rQuery.table("InvoiceDetail i")
					.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
					// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
					.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
					.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "'")
					.cond("SUBSTRING_INDEX(i.departmentCode, '/', -1) = '" + conds.get("cashier1").toString() + "'")
					.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
							+ "UNION ALL "
							+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
							+ "WHERE t.ActivityType = '" + ActivityType.Receipt + "' "
							+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien
							+ "AND SUBSTRING_INDEX(t.departmentCode, '/', -1) = '" + conds.get("cashier1").toString() + "' "
							+ "AND t.transactionDate >= '" + dateStart + "' "
							+ "AND t.transactionDate <= '" + dateEnd + "')");
					
				} else {					
					Employee e = HRModelManager.getInstance().getBydLoginId(conds.get("cashier2").toString());
					nameNode = e.getName();
					if(!conds.get("cashier1").toString().isEmpty() && !conds.get("cashier2").toString().isEmpty()){						
						rQuery.table("InvoiceDetail i")
						.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
						// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
						.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
						.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "'")
						.cond("SUBSTRING_INDEX(i.departmentCode, '/', -1) = '" + conds.get("cashier1").toString() + "'")
						.cond("i.employeeCode = '" + e.getCode() + "' ")
						.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
								+ "UNION ALL "
								+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
								+ "WHERE t.ActivityType = '" + ActivityType.Receipt + "' "
								+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien
								+ "AND SUBSTRING_INDEX(t.departmentCode, '/', -1) = '" + conds.get("cashier1").toString() + "' "
								+ "AND t.transactionDate >= '" + dateStart + "' AND t.employeeCode = '"+ e.getCode() +"' "
								+ "AND t.transactionDate <= '" + dateEnd + "')");
					} else {						
						rQuery.table("InvoiceDetail i")
						.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
						// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
						.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
						.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "' ")
						.cond("i.employeeCode = '" + e.getCode() + "' ")
						.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
								+ "UNION ALL "
								+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
								+ "WHERE t.ActivityType = '" + ActivityType.Receipt + "' "
								+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien
								+ "AND t.transactionDate >= '" + dateStart + "' AND t.employeeCode = '"+ e.getCode() +"' "
								+ "AND t.transactionDate <= '" + dateEnd + "')");
					}
				}
			}	
		}
		
		/** TAB cửa hàng */
		if (conds.containsKey("store")) {
			if (conds.get("store").toString().isEmpty()) {
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
				// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "'")
				.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Receipt + "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien
						+ "AND t.transactionDate >= '" + dateStart + "' "
						+ "AND t.transactionDate <= '" + dateEnd + "')");
			} else {
				OptionGroup o = GenericOptionModelManager.getInstance().getOptionGroup("accounting", "AccountingService", "type_invoice");
				nameNode = o.getOption(conds.get("store").toString()).getLabel();
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
				// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "'")
				.cond("SUBSTRING_INDEX(i.type, '/', -1) = '" + conds.get("store").toString() + "'")
				.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Receipt + "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien
						+ "AND SUBSTRING_INDEX(t.type, '/', -1) = '" + conds.get("store").toString() + "' "
						+ "AND t.transactionDate >= '" + dateStart + "' "
						+ "AND t.transactionDate <= '" + dateEnd + "')");
			}
		}
		
		/** TAB dự án */
		if (conds.containsKey("project")) {
			if (conds.get("project").toString().isEmpty()) {
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
				// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "'")
				.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Receipt + "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien
						+ "AND t.transactionDate >= '" + dateStart + "' "
					  + "AND t.transactionDate <= '" + dateEnd + "')");
			} else {
				nameNode = RestaurantModelManager.getInstance().getProjectByCode(conds.get("project").toString()).getName();
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
				// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "'")
				.cond("SUBSTRING_INDEX(i.projectCode, '/', -1) = '" + conds.get("project").toString() + "'")
				.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Receipt + "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien
						+ "AND SUBSTRING_INDEX(t.projectCode, '/', -1) = '" + conds.get("project").toString() + "' "
						+ "AND t.transactionDate >= '" + dateStart + "' "
						+ "AND t.transactionDate <= '" + dateEnd + "')");
			}
		}
		
		return rQuery;
	}

	private SQLSelectQuery querySQL_ChiPhi() throws Exception{
		/**
		 ********************************
		 * ******************************
		 * ******************************
		 * PHIÊN BẢN MỚI - ĐÃ GỘP BẢNG  *
		 * ******************************
		 * ******************************
		 ******************************** 
		 */
		
		SQLSelectQuery rQuery = new SQLSelectQuery();
		
		/** TAB phòng ban */
		if (conds.containsKey("department")) {
			if (conds.get("department").toString().isEmpty()) {
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
				// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "'")
				.cond("i.ActivityType = '" + ActivityType.Payment + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total* t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien
						+ "AND t.transactionDate >= '" + dateStart + "' "
						+ "AND t.transactionDate <= '" + dateEnd + "')");
			} else {
				nameNode = AccountModelManager.getInstance().getGroupByName(conds.get("department").toString()).getLabel();
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
				// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "'")
				.cond("SUBSTRING_INDEX(i.departmentCode, '/', -1) = '" + conds.get("department").toString() + "'")
				.cond("i.ActivityType = '" + ActivityType.Payment + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien
						+ "AND SUBSTRING_INDEX(t.departmentCode, '/', -1) = '" + conds.get("department").toString() + "' "
						+ "AND t.transactionDate >= '" + dateStart + "' "
						+ "AND t.transactionDate <= '" + dateEnd + "')");
			}
		}
		
		/** TAB sản phẩm */
		if (conds.containsKey("product1")) {
			if (conds.get("product1").toString().isEmpty() && conds.get("product2").toString().isEmpty()) {
				rQuery.table("InvoiceItem i")
				.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
				// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.activityType  = '" + ActivityType.Payment + "' AND i.productCode IS NOT NULL")
				.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "' ");
			} else {
				rQuery.table("InvoiceItem i " +
										 "INNER JOIN product p ON p.code = i.productCode " +
										 "INNER JOIN product_productTag t ON t.productId = p.Id " +
										 "INNER JOIN warehouse_productTag w ON w.id = t.productTagId");
				rQuery.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column");
				rQuery.cond("i.activityType  = '" + ActivityType.Payment + "' AND i.productCode IS NOT NULL");
				rQuery.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'");
				rQuery.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "' ");
				if (!conds.get("product1").toString().isEmpty()) {
					nameNode = ProductModelManager.getInstance().getProductTagByCode(conds.get("product1").toString()).getLabel();
					rQuery.cond("w.code = '" + conds.get("product1").toString() + "'");
				}
				if(!conds.get("product2").toString().isEmpty()) {
					nameNode = ProductModelManager.getInstance().getProductByCode(conds.get("product2").toString()).getName();
					rQuery.cond("i.productCode = '" + conds.get("product2").toString() + "'");
				}
			}
		}
		
		/** TAB khu vực */		
		if (conds.containsKey("location1")) {
			if(conds.get("location1").toString().isEmpty() && conds.get("location2").toString().isEmpty()){
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
				// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "'")
				.cond("i.ActivityType = '" + ActivityType.Payment + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien
						+ "AND t.transactionDate >= '" + dateStart + "' "
						+ "AND t.transactionDate <= '" + dateEnd + "')");
			}else {			
				if(!conds.get("location1").toString().isEmpty() && conds.get("location2").toString().isEmpty()){
					nameNode = RestaurantModelManager.getInstance().getLocationByCode(conds.get("location1").toString()).getName();
					rQuery.table("InvoiceDetail i")
					.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
					// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
					.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
					.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "'")
					.cond("i.ActivityType = '" + ActivityType.Payment + "' ")
					.cond("i.locationCode = '"+ conds.get("location1").toString() +"' " 
							+ "UNION ALL "
							+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
							+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
							+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien
							+ "AND t.locationCode = '"+ conds.get("location1").toString() +"' "
							+ "AND t.transactionDate >= '" + dateStart + "' "
							+ "AND t.transactionDate <= '" + dateEnd + "')");
				} else {
					if(!conds.get("location2").toString().isEmpty() && !conds.get("location1").toString().isEmpty()){
						nameNode = RestaurantModelManager.getInstance().getTableByCode(conds.get("location2").toString()).getName();						
						rQuery.table("InvoiceDetail i")
						.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
						// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
						.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
						.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "'")
						.cond("i.ActivityType = '" + ActivityType.Payment + "' ")
						.cond("i.locationCode = '"+ conds.get("location1").toString() +"' " )
						.cond("i.tableCode = '"+ conds.get("location2").toString() +"' " 
								+ "UNION ALL "
								+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
								+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
								+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien
								+ "AND t.locationCode = '"+ conds.get("location1").toString() +"' "
								+ "AND t.tableCode = '"+ conds.get("location2").toString()  +"' "
								+ "AND t.transactionDate >= '" + dateStart + "' "
								+ "AND t.transactionDate <= '" + dateEnd + "')");
					} else {
						nameNode = RestaurantModelManager.getInstance().getTableByCode(conds.get("location2").toString()).getName();						
						rQuery.table("InvoiceDetail i")
						.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
						// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
						.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
						.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "'")
						.cond("i.ActivityType = '" + ActivityType.Payment + "' ")						
						.cond("i.tableCode = '"+ conds.get("location2").toString() +"' " 
								+ "UNION ALL "
								+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
								+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
								+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien
								+ "AND t.tableCode = '"+ conds.get("location2").toString() +"' "
								+ "AND t.transactionDate >= '" + dateStart + "' "
								+ "AND t.transactionDate <= '" + dateEnd + "')");
					}
				}				
			}
		}
		
		/** TAB khách hàng */
		if (conds.containsKey("partner1")) {
			if(conds.get("partner1").toString().isEmpty() && conds.get("partner2").toString().isEmpty()){
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
				// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "'")
				.cond("i.ActivityType = '" + ActivityType.Payment + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien
						+ "AND t.transactionDate >= '" + dateStart + "' "
						+ "AND t.transactionDate <= '" + dateEnd + "')");
			}else {
				if(!conds.get("partner1").toString().isEmpty() && conds.get("partner2").toString().isEmpty()){
					nameNode = AccountModelManager.getInstance().getGroupByName(conds.get("partner1").toString()).getLabel();
					rQuery.table("InvoiceDetail i")
					.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
					// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
					.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
					.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "' AND SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '" + conds.get("partner1").toString() + "'")
					.cond("i.ActivityType = '" + ActivityType.Payment + "' "
							+ "UNION ALL "
							+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
							+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
							+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien
							+ "AND SUBSTRING_INDEX(t.groupCustomerCode, '/', -1) = '" + conds.get("partner1").toString() + "' "
							+ "AND t.transactionDate >= '" + dateStart + "' "
							+ "AND t.transactionDate <= '" + dateEnd + "')");
				} else {
					Customer c = CustomerModelManager.getInstance().getBydLoginId(conds.get("partner2").toString());
					nameNode = c.getName();	
					String empCode = c.getCode();					
					if(!conds.get("partner1").toString().isEmpty() && !conds.get("partner2").toString().isEmpty()){						
						rQuery.table("InvoiceDetail i")
						.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
						// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
						.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
						.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "' AND SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '" + conds.get("partner1").toString() + "'")
						.cond("i.ActivityType = '" + ActivityType.Payment + "' ")
						.cond("i.customerCode = '" + empCode + "' "
								+ "UNION ALL "
								+ "(SELECT COALESCE(SUM(t.total * t.currencyRate), 0) AS `total` FROM InvoiceTransaction t "
								+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
								+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien
								+ "AND SUBSTRING_INDEX(t.groupCustomerCode, '/', -1) = '" + conds.get("partner1").toString() + "' "
								+ "AND t.transactionDate >= '" + dateStart + "' "
								+ "AND t.customerCode = '" + empCode + "' "
								+ "AND t.transactionDate <= '" + dateEnd + "')");
					} else {					
						rQuery.table("InvoiceDetail i")
						.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
						// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
						.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
						.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "' ")
						.cond("i.ActivityType = '" + ActivityType.Payment + "' ")
						.cond("i.customerCode = '" + empCode + "' "
								+ "UNION ALL "
								+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
								+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
								+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien
								+ "AND t.transactionDate >= '" + dateStart + "' AND t.customerCode = '" + empCode + "' "
								+ "AND t.transactionDate <= '" + dateEnd + "')");
					}
				}
			}			
		}
		
		/** TAB nhân viên */		
		if (conds.containsKey("cashier1")) {
			if(conds.get("cashier1").toString().isEmpty() && conds.get("cashier2").toString().isEmpty()){
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
				// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "'")
				.cond("i.ActivityType = '" + ActivityType.Payment + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien
						+ "AND t.transactionDate >= '" + dateStart + "' "
						+ "AND t.transactionDate <= '" + dateEnd + "')");
			} else {			
				if(!conds.get("cashier1").toString().isEmpty() && conds.get("cashier2").toString().isEmpty()){
					nameNode = AccountModelManager.getInstance().getGroupByName(conds.get("cashier1").toString()).getLabel();					
					rQuery.table("InvoiceDetail i")
					.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
					// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
					.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
					.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "' AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '" + conds.get("cashier1").toString() + "' ")
					.cond("i.ActivityType = '" + ActivityType.Payment + "' "
							+ "UNION ALL "
							+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
							+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
							+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien
							+ "AND SUBSTRING_INDEX(t.departmentCode, '/', -1) = '" + conds.get("cashier1").toString() + "' "
							+ "AND t.transactionDate >= '" + dateStart + "' "
							+ "AND t.transactionDate <= '" + dateEnd + "')");					
				} else {
					Employee e = HRModelManager.getInstance().getBydLoginId(conds.get("cashier2").toString());
					nameNode = e.getName();					
					if(!conds.get("cashier1").toString().isEmpty() && !conds.get("cashier2").toString().isEmpty()){						
						rQuery.table("InvoiceDetail i")
						.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
						// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
						.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
						.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "'")
						.cond("SUBSTRING_INDEX(i.departmentCode, '/', -1) = '" + conds.get("cashier1").toString() + "'")
						.cond("i.employeeCode = '" + e.getCode() + "' ")
						.cond("i.ActivityType = '" + ActivityType.Payment + "' "
								+ "UNION ALL "
								+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
								+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' AND "
								+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien
								+ "SUBSTRING_INDEX(t.departmentCode, '/', -1) = '" + conds.get("cashier1").toString() + "' "
								+ "AND t.transactionDate >= '" + dateStart + "' "
								+ "AND t.employeeCode = '"+ e.getCode() +"' "
								+ "AND t.transactionDate <= '" + dateEnd + "')");
					}else {						
						rQuery.table("InvoiceDetail i")
						.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
						// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
						.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
						.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "' ")
						.cond("i.employeeCode = '" + e.getCode() + "' ")
						.cond("i.ActivityType = '" + ActivityType.Payment + "' "
								+ "UNION ALL "
								+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
								+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
								+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien
								+ "AND t.transactionDate >= '" + dateStart + "' "
								+ "AND t.employeeCode = '"+ e.getCode() +"' "
								+ "AND t.transactionDate <= '" + dateEnd + "')");
					}
				}
			}		
		}
		
		/** TAB cửa hàng */
		if (conds.containsKey("store")) {
			if (conds.get("store").toString().isEmpty()) {
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
				// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "'")
				.cond("i.ActivityType = '" + ActivityType.Payment + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total* t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien
						+ "AND t.transactionDate >= '" + dateStart + "' "
						+ "AND t.transactionDate <= '" + dateEnd + "')");
			} else {
				OptionGroup o = GenericOptionModelManager.getInstance().getOptionGroup("accounting", "AccountingService", "type_invoice");
				nameNode = o.getOption(conds.get("store").toString()).getLabel();
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
				// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "'")
				.cond("SUBSTRING_INDEX(i.type, '/', -1) = '" + conds.get("store").toString() + "'")
				.cond("i.ActivityType = '" + ActivityType.Payment + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien
						+ "AND SUBSTRING_INDEX(t.type, '/', -1) = '" + conds.get("store").toString() + "' "
						+ "AND t.transactionDate >= '" + dateStart + "' "
						+ "AND t.transactionDate <= '" + dateEnd + "')");
			}
		}
		
		/** TAB dự án */
		if (conds.containsKey("project")) {
			if (conds.get("project").toString().isEmpty()) {
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
				// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "'")
				.cond("i.ActivityType = '" + ActivityType.Payment + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total* t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien
						+ "AND t.transactionDate >= '" + dateStart + "' "
						+ "AND t.transactionDate <= '" + dateEnd + "')");
			} else {
				nameNode = RestaurantModelManager.getInstance().getProjectByCode(conds.get("project").toString()).getName();
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column")
				// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "'")
				.cond("SUBSTRING_INDEX(i.projectCode, '/', -1) = '" + conds.get("project").toString() + "'")
				.cond("i.ActivityType = '" + ActivityType.Payment + "' "
						+ "UNION ALL (SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien
						+ "AND SUBSTRING_INDEX(t.projectCode, '/', -1) = '" + conds.get("project").toString() + "' "
						+ "AND t.transactionDate >= '" + dateStart + "' "
						+ "AND t.transactionDate <= '" + dateEnd + "')");
			}
		}
		
		return rQuery;
	}
	
	private SQLSelectQuery querySQL_ThuChiLai() throws Exception {
		/**
		 ********************************
		 * ******************************
		 * ******************************
		 * PHIÊN BẢN MỚI - ĐÃ GỘP BẢNG  *
		 * ******************************
		 * ******************************
		 ******************************** 
		 */
		
		SQLSelectQuery rQuery = new SQLSelectQuery();
		
		/** TAB phòng ban */
		if (conds.containsKey("department")) {
			if (conds.get("department").toString().isEmpty()) {
				rQuery.table("InvoiceTransaction i")
				.field("COALESCE(SUM(i.total * i.currencyRate),0) AS `totalRec`", "column")
				// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.transactionDate >= '" + dateStart + "' AND i.transactionDate <= '" + dateEnd + "'")
				.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total * t.currencyRate), 0) AS `totalPay` FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien
						+ "AND t.transactionDate >= '" + dateStart + "' "
						+ "AND t.transactionDate <= '" + dateEnd + "')");
			} else {
				nameNode = AccountModelManager.getInstance().getGroupByName(conds.get("department").toString()).getLabel();
				rQuery.table("InvoiceTransaction i")
				.field("COALESCE(SUM(i.total * i.currencyRate),0) AS `totalRec`", "column")
				// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.transactionDate >= '" + dateStart + "' AND i.transactionDate <= '" + dateEnd + "'")
				.cond("SUBSTRING_INDEX(i.departmentCode, '/', -1) = '" + conds.get("department").toString() + "'")
				.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `totalPay` FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " // Add dieu kien
						+ "AND SUBSTRING_INDEX(t.departmentCode, '/', -1) = '" + conds.get("department").toString() + "' "
						+ "AND t.transactionDate >= '" + dateStart + "' AND t.transactionDate <= '" + dateEnd + "')");
			}
		}
		
		/** TAB sản phẩm */
		if (conds.containsKey("product1")) {
			if (conds.get("product1").toString().isEmpty() && conds.get("product2").toString().isEmpty()) {
				rQuery.table("InvoiceItem i");
				rQuery.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalChargeReceipt`", "column");
				rQuery.cond("i.activityType  = '" + ActivityType.Receipt + "' AND i.productCode IS NOT NULL");
				rQuery.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'");
				rQuery.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(it.finalCharge * it.currencyRate),0) AS `finalChargePayment` "
						+ "FROM InvoiceItem it "
						+ "WHERE it.activityType = '"+ ActivityType.Payment +"' "
								+ "AND it.productCode IS NOT NULL "
								+ "AND it.startDate >= '" + dateStart + "' "
								+ "AND it.startDate <= '" + dateEnd + "' "
								+ "AND it.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"')");
			} else {
				if (!conds.get("product1").toString().isEmpty() && conds.get("product2").toString().isEmpty() ) {	
					nameNode = ProductModelManager.getInstance().getProductTagByCode(conds.get("product1").toString()).getLabel();
					rQuery.table("InvoiceItem i " +
							 "INNER JOIN product p ON p.code = i.productCode " +
							 "INNER JOIN product_productTag t ON t.productId = p.Id " +
							 "INNER JOIN warehouse_productTag w ON w.id = t.productTagId");
							rQuery.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column");
							rQuery.cond("i.activityType  = '" + ActivityType.Receipt + "' AND i.productCode IS NOT NULL");
							rQuery.cond("w.code = '" + conds.get("product1").toString() + "'");
							rQuery.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'");
							rQuery.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "' "
								+ "UNION ALL "
								+ "(SELECT COALESCE(SUM(it.finalCharge * it.currencyRate),0) AS `finalChargePayment` "
								+ "FROM InvoiceItem it "
								+ "INNER JOIN product p ON p.code = it.productCode "
								+ "INNER JOIN product_productTag t ON t.productId = p.Id "
								+ "INNER JOIN warehouse_productTag w ON w.id = t.productTagId "
								+ "WHERE it.activityType = '"+ ActivityType.Payment +"' "
								+ "AND it.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
								+ "AND it.productCode IS NOT NULL "
								+ "AND it.startDate >= '" + dateStart + "' "
								+ "AND it.startDate <= '" + dateEnd + "' "
								+ "AND w.code = '" + conds.get("product1").toString() + "')");					
				} else {
					nameNode = ProductModelManager.getInstance().getProductByCode(conds.get("product2").toString()).getName();
					if(!conds.get("product1").toString().isEmpty() && !conds.get("product2").toString().isEmpty()){
						rQuery.table("InvoiceItem i " +
								 "INNER JOIN product p ON p.code = i.productCode " +
								 "INNER JOIN product_productTag t ON t.productId = p.Id " +
								 "INNER JOIN warehouse_productTag w ON w.id = t.productTagId");
								rQuery.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column");
								rQuery.cond("i.activityType  = '" + ActivityType.Receipt + "'");
								rQuery.cond("i.productCode IS NOT NULL");
								rQuery.cond("w.code = '" + conds.get("product1").toString() + "'");
								rQuery.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'");
								rQuery.cond("i.productCode = '" + conds.get("product2").toString() + "'");
								rQuery.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "' "
									+ "UNION ALL "
									+ "(SELECT COALESCE(SUM(it.finalCharge * it.currencyRate),0) AS `finalChargePayment` "
									+ "FROM InvoiceItem it "
									+ "INNER JOIN product p ON p.code = it.productCode "
									+ "INNER JOIN product_productTag t ON t.productId = p.Id "
									+ "INNER JOIN warehouse_productTag w ON w.id = t.productTagId"
									+ "WHERE it.activityType = '"+ ActivityType.Payment +"' "
											+ "AND it.productCode IS NOT NULL "
											+ "AND it.startDate >= '" + dateStart + "' "
											+ "AND it.startDate <= '" + dateEnd + "' "
											+ "AND it.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
											+ "AND w.code = '" + conds.get("product1").toString() + "' "
											+ "AND it.productCode = '" + conds.get("product2").toString() + "')");
					} else {
						rQuery.table("InvoiceItem i " );							 
								rQuery.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`", "column");
								rQuery.cond("i.activityType  = '" + ActivityType.Receipt + "'");
								rQuery.cond("i.productCode IS NOT NULL");
								rQuery.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'");
								rQuery.cond("i.productCode = '" + conds.get("product2").toString() + "'");
								rQuery.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "' "
									+ "UNION ALL "
									+ "(SELECT COALESCE(SUM(it.finalCharge * it.currencyRate),0) AS `finalChargePayment` "
									+ "FROM InvoiceItem it "
									+ "WHERE it.activityType = '"+ ActivityType.Payment +"' "
									+ "AND it.productCode IS NOT NULL "
									+ "AND it.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
									+ "AND it.startDate >= '" + dateStart + "' "
									+ "AND it.startDate <= '" + dateEnd + "' "
									+ "AND it.productCode = '" + conds.get("product2").toString() + "')");
					}
				}
			}
		}
		
		/** TAB khu vực */		
		if (conds.containsKey("location1")) {
			if(conds.get("location1").toString().isEmpty() && conds.get("location2").toString().isEmpty()){
				rQuery.table("InvoiceTransaction i")
				.field("COALESCE(SUM(i.total * i.currencyRate),0) AS `finalCharge`", "column")
				// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.transactionDate >= '" + dateStart + "' AND i.transactionDate <= '" + dateEnd + "'")
				.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
						+ "AND t.transactionDate >= '" + dateStart + "' "
						+ "AND t.transactionDate <= '" + dateEnd + "')");
			} else {					
				if(!conds.get("location1").toString().isEmpty() && conds.get("location2").toString().isEmpty()){
					nameNode = RestaurantModelManager.getInstance().getLocationByCode(conds.get("location1").toString()).getName();
					rQuery.table("InvoiceTransaction i")
					.field("COALESCE(SUM(i.total * i.currencyRate),0) AS `finalCharge`", "column")
					// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
					.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
					.cond("i.transactionDate >= '" + dateStart + "' AND i.transactionDate <= '" + dateEnd + "'")
					.cond("i.ActivityType = '" + ActivityType.Receipt + "' ")
					.cond("i.locationCode = '"+ conds.get("location1").toString() +"' " 
							+ "UNION ALL "
							+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
							+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
							+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
							+ "AND t.locationCode = '"+ conds.get("location1").toString() +"' "
							+ "AND t.transactionDate >= '" + dateStart + "' "
							+ "AND t.transactionDate <= '" + dateEnd + "')");
				} else {
					if(!conds.get("location2").toString().isEmpty() && !conds.get("location1").toString().isEmpty()){
						nameNode = RestaurantModelManager.getInstance().getTableByCode(conds.get("location2").toString()).getName();						
						rQuery.table("InvoiceTransaction i")
						.field("COALESCE(SUM(i.total * i.currencyRate),0) AS `finalCharge`", "column")
						// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
						.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
						.cond("i.transactionDate >= '" + dateStart + "' AND i.transactionDate <= '" + dateEnd + "'")
						.cond("i.ActivityType = '" + ActivityType.Receipt + "' ")
						.cond("i.locationCode = '"+ conds.get("location1").toString() +"' " )
						.cond("i.tableCode = '"+ conds.get("location2").toString() +"' " 
								+ "UNION ALL "
								+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
								+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
								+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
								+ "AND t.locationCode = '"+ conds.get("location1").toString() +"' "
								+ "AND t.tableCode = '"+ conds.get("location2").toString()  +"' "
								+ "AND t.transactionDate >= '" + dateStart + "' "
								+ "AND t.transactionDate <= '" + dateEnd + "')");
					} else {
						nameNode = RestaurantModelManager.getInstance().getTableByCode(conds.get("location2").toString()).getName();						
						rQuery.table("InvoiceTransaction i")
						.field("COALESCE(SUM(i.total * i.currencyRate),0) AS `finalCharge`", "column")
						// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
						.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
						.cond("i.transactionDate >= '" + dateStart + "' AND i.transactionDate <= '" + dateEnd + "'")
						.cond("i.ActivityType = '" + ActivityType.Receipt + "' ")						
						.cond("i.tableCode = '"+ conds.get("location2").toString() +"' " 
								+ "UNION ALL "
								+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
								+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
								+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
								+ "AND t.tableCode = '"+ conds.get("location2").toString() +"' "
								+ "AND t.transactionDate >= '" + dateStart + "' "
								+ "AND t.transactionDate <= '" + dateEnd + "')");
					}
				}			
			}
		}
		
		/** TAB khách hàng */
		if (conds.containsKey("partner1")) {
			if(conds.get("partner1").toString().isEmpty() && conds.get("partner2").toString().isEmpty()){
				rQuery.table("InvoiceTransaction i")
				.field("COALESCE(SUM(i.total * i.currencyRate),0) AS `finalCharge`", "column")
				// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.transactionDate >= '" + dateStart + "' AND i.transactionDate <= '" + dateEnd + "'")
				.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
						+ "AND t.transactionDate >= '" + dateStart + "' "
						+ "AND t.transactionDate <= '" + dateEnd + "')");
			} else {
				if(!conds.get("partner1").toString().isEmpty() && conds.get("partner2").toString().isEmpty()){
					nameNode = AccountModelManager.getInstance().getGroupByName(conds.get("partner1").toString()).getLabel();
					rQuery.table("InvoiceTransaction i")
					.field("COALESCE(SUM(i.total * i.currencyRate),0) AS `finalCharge`", "column")
					// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
					.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
					.cond("i.transactionDate >= '" + dateStart + "' AND i.transactionDate <= '" + dateEnd + "'")
					.cond("SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '" + conds.get("partner1").toString() + "'")
					.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
							+ "UNION ALL "
							+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
							+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
							+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
							+ "AND SUBSTRING_INDEX(t.groupCustomerCode, '/', -1) = '" + conds.get("partner1").toString() + "' "
							+ "AND t.transactionDate >= '" + dateStart + "' AND t.transactionDate <= '" + dateEnd + "')");
				} else {
					Customer c = CustomerModelManager.getInstance().getBydLoginId(conds.get("partner2").toString());
					nameNode = c.getName();	
					String	empCode = c.getCode();
					if(!conds.get("partner1").toString().isEmpty() && !conds.get("partner2").toString().isEmpty()){
						rQuery.table("InvoiceTransaction i")	
						.field("COALESCE(SUM(i.total * i.currencyRate),0) AS `finalCharge`", "column")
						// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
						.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
						.cond("i.transactionDate >= '" + dateStart + "' AND i.transactionDate <= '" + dateEnd + "'")
						.cond("SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '" + conds.get("partner1").toString() + "'")
						.cond("i.ActivityType = '" + ActivityType.Receipt + "' ")
						.cond("i.customerCode = '" + empCode + "' "
								+ "UNION ALL "
								+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
								+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
								+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
								+ "AND SUBSTRING_INDEX(t.groupCustomerCode, '/', -1) = '" + conds.get("partner1").toString() + "' "
								+ "AND t.transactionDate >= '" + dateStart + "' AND t.customerCode = '" + empCode + "' "
								+ "AND t.transactionDate <= '" + dateEnd + "')");
					} else {
						rQuery.table("InvoiceTransaction i")
						.field("COALESCE(SUM(i.total * i.currencyRate),0) AS `finalCharge`", "column")
						// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
						.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
						.cond("i.transactionDate >= '" + dateStart + "' AND i.transactionDate <= '" + dateEnd + "' ")
						.cond("i.ActivityType = '" + ActivityType.Receipt + "' ")
						.cond("i.customerCode = '" + empCode + "' "
								+ "UNION ALL "
								+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
								+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
								+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
								+ "AND t.transactionDate >= '" + dateStart + "' AND t.customerCode = '" + empCode + "' "
								+ "AND t.transactionDate <= '" + dateEnd + "')");
					}
				}
			}			
		}
		
		/** TAB nhân viên */
		if (conds.containsKey("cashier1")) {
			if(conds.get("cashier1").toString().isEmpty() && conds.get("cashier2").toString().isEmpty()){
				rQuery.table("InvoiceTransaction i")
				.field("COALESCE(SUM(i.total * i.currencyRate),0) AS `finalCharge`", "column")
				// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.transactionDate >= '" + dateStart + "' AND i.transactionDate <= '" + dateEnd + "'")
				.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
						+ "AND t.transactionDate >= '" + dateStart + "' "
						+ "AND t.transactionDate <= '" + dateEnd + "')");
			} else {
				if(!conds.get("cashier1").toString().isEmpty() && conds.get("cashier2").toString().isEmpty()){
					nameNode = AccountModelManager.getInstance().getGroupByName(conds.get("cashier1").toString()).getLabel();
					rQuery.table("InvoiceTransaction i")
					.field("COALESCE(SUM(i.total * i.currencyRate),0) AS `finalCharge`", "column")
					// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
					.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
					.cond("i.transactionDate >= '" + dateStart + "' AND i.transactionDate <= '" + dateEnd + "'")
					.cond("SUBSTRING_INDEX(i.departmentCode, '/', -1) = '" + conds.get("cashier1").toString() + "'")
					.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
							+ "UNION ALL "
							+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
							+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
							+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
							+ "AND SUBSTRING_INDEX(t.departmentCode, '/', -1) = '" + conds.get("cashier1").toString() + "' "
							+ "AND t.transactionDate >= '" + dateStart + "' "
							+ "AND t.transactionDate <= '" + dateEnd + "')");					
				} else {
					Employee e = HRModelManager.getInstance().getBydLoginId(conds.get("cashier2").toString());
					nameNode = e.getName();
					if(!conds.get("cashier1").toString().isEmpty() && !conds.get("cashier2").toString().isEmpty()){											
						rQuery.table("InvoiceTransaction i ")
						.field("COALESCE(SUM(i.total * i.currencyRate),0) AS `finalCharge`", "column")
						// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
						.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
						.cond("i.transactionDate >= '" + dateStart + "' AND i.transactionDate <= '" + dateEnd + "'")
						.cond("SUBSTRING_INDEX(i.departmentCode, '/', -1) = '" + conds.get("cashier1").toString() + "'")
						.cond("i.employeeCode = '" + e.getCode() + "' ")
						.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
								+ "UNION ALL "
								+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
								+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
								+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
								+ "AND SUBSTRING_INDEX(t.departmentCode, '/', -1) = '" + conds.get("cashier1").toString() + "' "
								+ "AND t.transactionDate >= '" + dateStart + "' "
								+ "AND t.employeeCode = '"+ e.getCode() +"' "
								+ "AND t.transactionDate <= '" + dateEnd + "')");
					} else {
						rQuery.table("InvoiceTransaction i ")
						.field("COALESCE(SUM(i.total * i.currencyRate),0) AS `finalCharge`", "column")
						// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
						.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
						.cond("i.transactionDate >= '" + dateStart + "' AND i.transactionDate <= '" + dateEnd + "' ")
						.cond("i.employeeCode = '" + e.getCode() + "' ")
						.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
								+ "UNION ALL "
								+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t "
								+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
								+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
								+ "AND t.transactionDate >= '" + dateStart + "' "
								+ "AND t.employeeCode = '"+ e.getCode() +"' "
								+ "AND t.transactionDate <= '" + dateEnd + "')");
					}
				}
			}	
		}
		
		/** TAB cửa hàng */ 
		if (conds.containsKey("store")) {
			if (conds.get("store").toString().isEmpty()) {
				rQuery.table("InvoiceTransaction i")
				.field("COALESCE(SUM(i.total * i.currencyRate),0) AS `totalRec`", "column")
				// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.transactionDate >= '" + dateStart + "' AND i.transactionDate <= '" + dateEnd + "'")
				.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `totalPay` FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
						+ "AND t.transactionDate >= '" + dateStart + "' "
						+ "AND t.transactionDate <= '" + dateEnd + "')");
			} else {
				OptionGroup o = GenericOptionModelManager.getInstance().getOptionGroup("accounting", "AccountingService", "type_invoice");
				nameNode = o.getOption(conds.get("store").toString()).getLabel();
				rQuery.table("InvoiceTransaction i")
				.field("COALESCE(SUM(i.total * i.currencyRate),0) AS `totalRec`", "column")
				// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.transactionDate >= '" + dateStart + "' AND i.transactionDate <= '" + dateEnd + "'")
				.cond("SUBSTRING_INDEX(i.type, '/', -1) = '" + conds.get("store").toString() + "'")
				.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `totalPay` FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
						+ "AND SUBSTRING_INDEX(t.type, '/', -1) = '" + conds.get("store").toString() + "' "
						+ "AND t.transactionDate >= '" + dateStart + "' "
						+ "AND t.transactionDate <= '" + dateEnd + "')");
			}
		}
		
		/** TAB dự án */ 
		if (conds.containsKey("project")) {
			if (conds.get("project").toString().isEmpty()) {
				rQuery.table("InvoiceTransaction i")
				.field("COALESCE(SUM(i.total * i.currencyRate),0) AS `totalRec`", "column")
				// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.transactionDate >= '" + dateStart + "' AND i.transactionDate <= '" + dateEnd + "'")
				.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `totalPay` FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
						+ "AND t.transactionDate >= '" + dateStart + "' "
						+ "AND t.transactionDate <= '" + dateEnd + "')");
			} else {
				nameNode = RestaurantModelManager.getInstance().getProjectByCode(conds.get("project").toString()).getName();
				rQuery.table("InvoiceTransaction i")
				.field("COALESCE(SUM(i.total * i.currencyRate),0) AS `totalRec`", "column")
				// 12/06/2015 - Thêm điều kiện loại bỏ hóa đơn sản xuất
				.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				.cond("i.transactionDate >= '" + dateStart + "' AND i.transactionDate <= '" + dateEnd + "'")
				.cond("SUBSTRING_INDEX(i.projectCode, '/', -1) = '" + conds.get("project").toString() + "'")
				.cond("i.ActivityType = '" + ActivityType.Receipt + "' "
						+ "UNION ALL "
						+ "(SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `totalPay` FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = '" + ActivityType.Payment + "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
						+ "AND SUBSTRING_INDEX(t.projectCode, '/', -1) = '" + conds.get("project").toString() + "' "
						+ "AND t.transactionDate >= '" + dateStart + "' "
						+ "AND t.transactionDate <= '" + dateEnd + "')");
			}
		}
		
		return rQuery;
	}
	
	private ReportRevenue getRowFromQuery(String[] row) {
		double sumFinal = 0;
		double sumTotal = 0;
		double sumFinal0 = 0;
		double sumTotal0 = 0;
		double sumPhaiThu = 0;
		double sumPhaiChi = 0;
		double ratio = 0;
		if (viewMoney.equals("Nghìn")) {
			sumFinal = Double.parseDouble(row[0].toString()) / 1000;
			if (row.length == 2) {
				sumTotal = Double.parseDouble(row[1].toString()) / 1000;
			}
			if (row.length > 2) {
				sumTotal = Double.parseDouble(row[1].toString()) / 1000;
				sumFinal0 = Double.parseDouble(row[2].toString()) / 1000;
				sumTotal0 = Double.parseDouble(row[3].toString()) / 1000;
				sumPhaiThu = sumFinal - sumTotal;
				sumPhaiChi = sumFinal0 - sumTotal0;
			}
		} else if (viewMoney.equals("Triệu")) {
			sumFinal = Double.parseDouble(row[0].toString()) / 1000000;
			if (row.length == 2) {
				sumTotal = Double.parseDouble(row[1].toString()) / 1000000;
			}
			if (row.length > 2) {
				sumTotal = Double.parseDouble(row[1].toString()) / 1000000;
				sumFinal0 = Double.parseDouble(row[2].toString()) / 1000000;
				sumTotal0 = Double.parseDouble(row[3].toString()) / 1000000;
				sumPhaiThu = sumFinal - sumTotal;
				sumPhaiChi = sumFinal0 - sumTotal0;
			}
		} else {
			sumFinal = Double.parseDouble(row[0].toString());
			if (row.length == 2) {
				sumTotal = Double.parseDouble(row[1].toString());
			}
			if (row.length > 2) {
				sumTotal = Double.parseDouble(row[1].toString());
				sumFinal0 = Double.parseDouble(row[2].toString());
				sumTotal0 = Double.parseDouble(row[3].toString());
				sumPhaiThu = sumFinal - sumTotal;
				sumPhaiChi = sumFinal0 - sumTotal0;
			}
		}
		ReportRevenue reportEmployee = null;
		if (row.length <= 2) {
			if (typeReport.equals("Thu - Chi - Lãi"))
				ratio = sumFinal - sumTotal;
			else
				ratio = (sumTotal / sumFinal) * 100;
			reportEmployee = new ReportRevenue(nameNode, Double.toString(sumFinal), Double.toString(sumTotal), Double.toString(ratio), 0);
		} else {
			ratio = sumTotal - sumTotal0;
			reportEmployee = new ReportRevenue(nameNode, Double.toString(sumFinal), Double.toString(sumFinal0), Double.toString(sumTotal), Double.toString(sumTotal0), Double.toString(sumPhaiThu), Double.toString(sumPhaiChi), Double.toString(ratio), 0);
		}

		/** Thống kê theo phòng ban */
		if (conds.containsKey("department") && !conds.get("department").toString().isEmpty()) {
			reportEmployee.setType(Type.PARENT);
			reportEmployee.setParent(conds.get("department").toString());
		} else {
			/** Thống kê theo khu vực */
			if (conds.containsKey("location1") && (!conds.get("location1").toString().isEmpty() || !conds.get("location2").toString().isEmpty())) {
				if (!conds.get("location2").toString().isEmpty()) {
					reportEmployee.setType(Type.CHILD);
					reportEmployee.setParent(conds.get("location2").toString());
				} else {
					reportEmployee.setType(Type.PARENT);
					reportEmployee.setParent(conds.get("location1").toString());
				}
			} else {
				/** Thống kê theo khách hàng */
				if (conds.containsKey("partner1") && (!conds.get("partner1").toString().isEmpty() || !conds.get("partner2").toString().isEmpty())) {
					if (!conds.get("partner2").toString().isEmpty())
						reportEmployee.setType(Type.CHILD);
					else
						reportEmployee.setType(Type.PARENT);
					reportEmployee.setParent(conds.get("partner1").toString());
				} else {
					/** Thống kê theo nhân viên */
					if (conds.containsKey("cashier1") && (!conds.get("cashier1").toString().isEmpty() || !conds.get("cashier2").toString().isEmpty())) {
						if (!conds.get("cashier2").toString().isEmpty())
							reportEmployee.setType(Type.CHILD);
						else
							reportEmployee.setType(Type.PARENT);
						reportEmployee.setParent(conds.get("cashier1").toString());
					} else {
						/** Thống kê theo sản phẩm */
						if (conds.containsKey("product1") && (!conds.get("product1").toString().isEmpty() || !conds.get("product2").toString().isEmpty())) {
							if (!conds.get("product2").toString().isEmpty())
								reportEmployee.setType(Type.CHILD);
							else
								reportEmployee.setType(Type.PARENT);
							reportEmployee.setParent(conds.get("product1").toString());
						} else {
							/** Thống kê theo cửa hàng */
							if (conds.containsKey("store") && !conds.get("store").toString().isEmpty()) {
								reportEmployee.setType(Type.PARENT);
								reportEmployee.setParent(conds.get("store").toString());
							} else {
								/** Thống kê theo dự án */
								if (conds.containsKey("project") && !conds.get("project").toString().isEmpty()) {
									reportEmployee.setType(Type.PARENT);
									reportEmployee.setParent(conds.get("project").toString());
								} else
									reportEmployee.setType(Type.ROOT);
							}
						}
					}
				}
			}
		}
		return reportEmployee;
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
