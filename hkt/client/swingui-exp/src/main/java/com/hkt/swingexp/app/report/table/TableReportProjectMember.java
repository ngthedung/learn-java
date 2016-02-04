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
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.module.accounting.entity.Invoice.ActivityType;
import com.hkt.module.core.entity.ReportTable;
import com.hkt.module.core.entity.SQLSelectQuery;
import com.hkt.module.hr.entity.Employee;
import com.hkt.swingexp.app.report.entity.ReportProjectMember;
import com.hkt.swingexp.app.report.entity.ReportProjectMember.Type;
import com.hkt.swingexp.app.report.modeltable.ModelTableReportProjectMember;

public class TableReportProjectMember extends JTable {
	private ModelTableReportProjectMember model;
	private TableRerenderProjectMember projectMember;
	private HashMap<String, String> conds;
	private String viewMoney;
	private String nameNode;
	private String typeReport;
	private String dateStart;
	private String dateEnd;
	private boolean isCheck;
	
	

	public ModelTableReportProjectMember getModel() {
		return model;
	}
	public TableReportProjectMember(HashMap<String, String> conds,
			String typeReport, Date dateStart, Date dateEnd, String viewMoney, boolean isCheck) {
		this.conds = conds;
		this.viewMoney = viewMoney;
		this.typeReport = typeReport;
		this.isCheck = isCheck;
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Calendar calendarStart = Calendar.getInstance();
		Calendar calendarEnd = Calendar.getInstance();
		if (dateStart != null) {
			calendarStart.setTime(dateStart);
			calendarStart.set(Calendar.HOUR_OF_DAY, 00);
			calendarStart.set(Calendar.MINUTE, 00);
			calendarStart.set(Calendar.MILLISECOND, 00);
		}
		if (dateEnd != null) {
			calendarEnd.setTime(dateEnd);
			calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
			calendarEnd.set(Calendar.MINUTE, 59);
			calendarEnd.set(Calendar.MILLISECOND, 59);
		}
		this.dateStart = dateFormat.format(calendarStart.getTime());
		this.dateEnd = dateFormat.format(calendarEnd.getTime());
		try {
			nameNode = AccountModelManager.getInstance().getNameByLoginId(
					ManagerAuthenticate.getInstance().getOrganizationLoginId());
		} catch (Exception e) {
			nameNode = "";
		}
		
		try {
			if (typeReport.equals("Chi phí")) {
				model = new ModelTableReportProjectMember(1, getNodeParent(),conds);
				this.setModel(model);

			} else if (typeReport.equals("Doanh thu")) {
				model = new ModelTableReportProjectMember(0, getNodeParent(),conds);
				this.setModel(model);
			} else if (typeReport.equals("Doanh thu thuần")) {
				model = new ModelTableReportProjectMember(2, getNodeParent(),conds);
				this.setModel(model);
			} else if (typeReport.equals("Thưởng")) {
				model = new ModelTableReportProjectMember(3, getNodeParent(), conds);
				this.setModel(model);
			}
			for (int i = 0; i < this.getColumnCount(); i++) {
				System.out.println(isCheck);
				projectMember = new TableRerenderProjectMember(model,typeReport, conds, dateStart, dateEnd,viewMoney, isCheck);
				this.getColumnModel()
						.getColumn(i)
						.setCellRenderer(projectMember);
								
				this.getColumnModel()
						.getColumn(i)
						.setCellEditor(projectMember);
						
			}
			model.fireTableDataChanged();
			this.setModel(model);
		} catch (Exception e) {
			model = new ModelTableReportProjectMember(2,
					new ArrayList<ReportProjectMember>(), conds);
			this.setModel(model);

			if (typeReport.equals("Chi phí")) {
				model = new ModelTableReportProjectMember(1,
						new ArrayList<ReportProjectMember>(), conds);
				this.setModel(model);
			} else if (typeReport.equals("Doanh thu")) {
				model = new ModelTableReportProjectMember(0,
						new ArrayList<ReportProjectMember>(), conds);
				this.setModel(model);
			} else if (typeReport.equals("Doanh thu thuần")) {
				model = new ModelTableReportProjectMember(2,
						new ArrayList<ReportProjectMember>(),conds);
				this.setModel(model);
			} else {
				model = new ModelTableReportProjectMember(3,
						new ArrayList<ReportProjectMember>(),conds);
				this.setModel(model);
			}
		}
		this.setRowHeight(23);
		this.getColumnModel().getColumn(0).setMinWidth(300);
		this.setFont(new Font("Tahoma", 0, 14));
		this.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(JLabel.CENTER);
		this.setBackground(Color.WHITE);
		this.setFillsViewportHeight(true);
	}
	private List<ReportProjectMember> getNodeParent() throws Exception {
		List<ReportProjectMember> projectMembers = new ArrayList<ReportProjectMember>();
		String sql = "";
		if(conds.containsKey("project") && isCheck == false)
			sql = "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' ";
		else if(conds.containsKey("project") && isCheck == true)
			sql = "";
		if (typeReport.equals("Doanh thu")) {
			String[] row = runQuery(querySQL_DoanhThu(sql));
			projectMembers.add(getRowFromQuery(row));
		} else if (typeReport.equals("Chi phí")) {
			String[] row = runQuery(querySQL_ChiPhi(sql));
			projectMembers.add(getRowFromQuery(row));
		} else if (typeReport.equals("Doanh thu thuần")) {
			String[] row = runQuery(querySQL_DoanhThuThuan(sql));
			projectMembers.add(getRowFromQuery(row));
		} else if (typeReport.equals("Thưởng")) {
			String[] row = runQuery(querySQL_Thuong(sql));
			projectMembers.add(getRowFromQuery(row));
		}
		return projectMembers;
	}

	private SQLSelectQuery querySQL_Thuong(String sql) throws Exception {
		SQLSelectQuery rQuery = new SQLSelectQuery();

		/** TAB dự án */
		
		if (conds.containsKey("project")) {
			if (conds.get("project").toString().isEmpty()) {
				rQuery.table("InvoiceTransaction i")
						.field("COALESCE(SUM(i.total * i.currencyRate),0) AS `totalRec`",
								"column")
						.cond("i.transactionDate >= '" + dateStart
								+ "' AND i.transactionDate <= '" + dateEnd
								+ "' "
								+ sql
								)
						.cond("i.ActivityType = '"
								+ ActivityType.Receipt
								+ "' UNION ALL (SELECT COALESCE(SUM(i.total * i.currencyRate),0) AS `totalPay` FROM InvoiceTransaction i WHERE i.ActivityType = '"
								+ ActivityType.Payment
								+ "' AND i.transactionDate >= '" + dateStart
								+ "' AND i.transactionDate <= '" + dateEnd
								+ "' "
								+ sql
								+ ")");
			} else {
				nameNode = RestaurantModelManager.getInstance()
						.getProjectByCode(conds.get("project").toString())
						.getName();
				rQuery.table("InvoiceTransaction i")
						.field("COALESCE(SUM(i.total * i.currencyRate),0) AS `totalRec`",
								"column")
						.cond("i.transactionDate >= '"
								+ dateStart
								+ "' AND i.transactionDate <= '"
								+ dateEnd
								+ "' AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"
								+ conds.get("project").toString() + "' "
								+ sql)
						.cond("i.ActivityType = '"
								+ ActivityType.Receipt
								+ "' UNION ALL (SELECT COALESCE(SUM(i.total * i.currencyRate),0) AS `totalPay` FROM InvoiceTransaction i WHERE i.ActivityType = '"
								+ ActivityType.Payment
								+ "' AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"
								+ conds.get("project").toString()
								+ "' AND i.transactionDate >= '" + dateStart
								+ "' AND i.transactionDate <= '" + dateEnd
								+ "' " 
								+ sql
								+ ")");
			}
		}
		/** TAB nhân viên */
		if (conds.containsKey("employee")) {
			if (conds.get("employee").toString().isEmpty()) {
				rQuery.table("InvoiceTransaction i")
				.field("COALESCE(SUM(i.total * i.currencyRate),0) AS `totalRec`",
						"column")
				.cond("i.transactionDate >= '" + dateStart
						+ "' AND i.transactionDate <= '" + dateEnd
						+ "' "
						+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' ")
				.cond("i.ActivityType = '"
						+ ActivityType.Receipt
						+ "' UNION ALL (SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `totalPay` FROM InvoiceTransaction t WHERE t.ActivityType = '"
						+ ActivityType.Payment
						+ "' AND t.transactionDate >= '" + dateStart
						+ "' AND t.transactionDate <= '" + dateEnd
						+ "' " 
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
						+ ")");
			} else {
				
				nameNode = RestaurantModelManager.getInstance()
						.getProjectByCode(conds.get("project").toString())
						.getName();
				rQuery.table("InvoiceTransaction i")
						.field("COALESCE(SUM(i.total * i.currencyRate),0) AS `totalRec`",
								"column")
						.cond("i.transactionDate >= '"
								+ dateStart
								+ "' AND i.transactionDate <= '"
								+ dateEnd
								+ "' AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"
								+ conds.get("project").toString() + "' "
								+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' ")
						.cond("i.ActivityType = '"
								+ ActivityType.Receipt
								+ "' UNION ALL (SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `totalPay` FROM InvoiceTransaction t WHERE t.ActivityType = '"
								+ ActivityType.Payment
								+ "' AND SUBSTRING_INDEX(t.projectCode, '/', -1) = '"
								+ conds.get("project").toString()
								+ "' AND t.transactionDate >= '" + dateStart
								+ "' AND t.transactionDate <= '" + dateEnd
								+ "' " 
								+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
								+ ")");
			}
		}
		return rQuery;
	}

	private SQLSelectQuery querySQL_DoanhThuThuan(String sql) throws Exception {
		SQLSelectQuery rQuery = new SQLSelectQuery();
		
		/** TAB dự án */
		if (conds.containsKey("project")) {
			if (conds.get("project").toString().isEmpty()) {
				rQuery.table("InvoiceTransaction i")
						.field("COALESCE(SUM(i.total * i.currencyRate),0) AS `totalRec`",
								"column")
						.cond("i.transactionDate >= '" + dateStart
								+ "' AND i.transactionDate <= '" + dateEnd
								+ "' "
								+ sql)
						.cond("i.ActivityType = '"
								+ ActivityType.Receipt
								+ "' UNION ALL (SELECT COALESCE(SUM(i.total * i.currencyRate),0) AS `totalPay` FROM InvoiceTransaction i WHERE i.ActivityType = '"
								+ ActivityType.Payment
								+ "' AND i.transactionDate >= '" + dateStart
								+ "' AND i.transactionDate <= '" + dateEnd
								+ "' "
								+ sql
								+ ")");
			} else {
				nameNode = RestaurantModelManager.getInstance()
						.getProjectByCode(conds.get("project").toString())
						.getName();
				rQuery.table("InvoiceTransaction i")
						.field("COALESCE(SUM(i.total * i.currencyRate),0) AS `totalRec`",
								"column")
						.cond("i.transactionDate >= '"
								+ dateStart
								+ "' AND i.transactionDate <= '"
								+ dateEnd
								+ "' AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"
								+ conds.get("project").toString() + "' "
								+ sql)
						.cond("i.ActivityType = '"
								+ ActivityType.Receipt
								+ "' UNION ALL (SELECT COALESCE(SUM(i.total * i.currencyRate),0) AS `totalPay` FROM InvoiceTransaction i WHERE i.ActivityType = '"
								+ ActivityType.Payment
								+ "' AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"
								+ conds.get("project").toString()
								+ "' AND i.transactionDate >= '" + dateStart
								+ "' AND i.transactionDate <= '" + dateEnd
								+ "' "
								+ sql
								+ ")");
			}
		}

		/** TAB nhân viên */
		if (conds.containsKey("employee")) {
			if (conds.get("employee").toString().isEmpty()) {
				rQuery.table("InvoiceTransaction i")
						.field("COALESCE(SUM(i.total * i.currencyRate),0) AS `totalRec`",
								"column")
						.cond("i.transactionDate >= '" + dateStart
								+ "' AND i.transactionDate <= '" + dateEnd
								+ "' "
								+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' ")
						.cond("i.ActivityType = '"
								+ ActivityType.Receipt
								+ "' UNION ALL (SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `totalPay` FROM InvoiceTransaction t WHERE t.ActivityType = '"
								+ ActivityType.Payment
								+ "' AND t.transactionDate >= '" + dateStart
								+ "' AND t.transactionDate <= '" + dateEnd + "'"
								+ " AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
								+")");
			} else {
				Employee e = HRModelManager.getInstance().getBydLoginId(conds.get("employee").toString());
				nameNode = e.getName();
				rQuery.table("((SELECT "
						+ "a.total  as `total`, b.final as `final`, "
						+ "(CASE WHEN (a.path1 IS NULL) THEN b.path1 ELSE a.path1 END) AS `path3` "
						+ "FROM "
						+ "(SELECT SUBSTRING_INDEX(t.projectCode, '/', -1) AS `path1`, "
						+ "SUM(t.total * t.currencyRate) AS `total` "
						+ " FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = 'Payment' AND t.transactionDate >= '" +dateStart +"' AND t.transactionDate <= '" +dateEnd +"' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
						+ "GROUP BY  path1 ) AS `a` "
						+ "RIGHT JOIN "
						+ "(SELECT SUBSTRING_INDEX(t.projectCode, '/', -1) AS `path1`, "
						+ "SUM(t.total * t.currencyRate) AS `final` "
						+ "FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = 'Receipt' AND t.transactionDate >= '" +dateStart +"' AND t.transactionDate <= '" +dateEnd +"' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
						+ "GROUP BY  path1 ) AS `b` on a.path1 = b.path1) "
						+ "UNION "
						+ "(SELECT "
						+ "a.total  as `total`, b.final as `final`, "
						+ "(CASE WHEN (a.path1 IS NULL) THEN b.path1 ELSE a.path1 END) AS `path3` "
						+ "FROM "
						+ "(SELECT SUBSTRING_INDEX(t.projectCode, '/', -1) AS `path1`, "
						+ "SUM(t.total * t.currencyRate) AS `total` "
						+ " FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = 'Payment' AND t.transactionDate >= '" +dateStart +"' AND t.transactionDate <= '" +dateEnd +"' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
						+ "GROUP BY  path1 ) AS `a` "
						+ "LEFT JOIN "
						+ "(SELECT SUBSTRING_INDEX(t.projectCode, '/', -1) AS `path1`, "
						+ "SUM(t.total * t.currencyRate) AS `final` "
						+ "FROM InvoiceTransaction t "
						+ "WHERE t.ActivityType = 'Receipt' AND t.transactionDate >= '" +dateStart +"' AND t.transactionDate <= '" +dateEnd +"' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
						+ "GROUP BY  path1 ) AS `b` on a.path1 = b.path1))  AS `ec` "
						+ "INNER JOIN restaurant_project rp ON ec.path3 = rp.code "
						+ "INNER JOIN restaurant_projectMember rpm ON rpm.projectId = rp.id "
						+ "WHERE ec.path3 NOT LIKE 'project-other' "
						+ "GROUP BY rpm.employeeCode"
						)
						.field("rpm.employeeCode AS `code3`", "parent")
						.field("(SELECT e.name FROM employee e WHERE e.code = `code3` ) AS `node`", "node")
						.field("COALESCE(sum(ec.final * (rpm.percent/100)), 0) as `final`", "final")
						.field("COALESCE(sum(ec.total * (rpm.percent/100)), 0) as `total`", "total")
						.field("COALESCE(sum(ec.final * (rpm.directAward/100)), 0) as `final1`", "finaldirectAward")
						.field("COALESCE(sum(ec.total * (rpm.directAward/100)), 0) as `total1`", "totaldirectAward");
			}
		}
		return rQuery;
	}

	private SQLSelectQuery querySQL_ChiPhi(String sql) throws Exception {
		SQLSelectQuery rQuery = new SQLSelectQuery();

		/** TAB dự án */
		if (conds.containsKey("project")) {
			if (conds.get("project").toString().isEmpty()) {
				rQuery.table("InvoiceDetail i")
						.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`",
								"column")
						.cond("i.startDate >= '" + dateStart
								+ "' AND i.startDate <= '" + dateEnd + "' "
								+ sql)
						.cond("i.ActivityType = '"
								+ ActivityType.Payment
								+ "' UNION ALL (SELECT COALESCE(SUM(i.total* i.currencyRate),0) AS `total` FROM InvoiceTransaction i WHERE i.ActivityType = '"
								+ ActivityType.Payment
								+ "' AND i.transactionDate >= '" + dateStart
								+ "' AND i.transactionDate <= '" + dateEnd
								+ "' "
								+sql
								+ ")");
			} else {
				nameNode = RestaurantModelManager.getInstance()
						.getProjectByCode(conds.get("project").toString())
						.getName();
				rQuery.table("InvoiceDetail i")
						.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`",
								"column")
						.cond("i.startDate >= '"
								+ dateStart
								+ "' AND i.startDate <= '"
								+ dateEnd
								+ "' AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"
								+ conds.get("project").toString() + "' "
								+ sql)
						.cond("i.ActivityType = '"
								+ ActivityType.Payment
								+ "' UNION ALL (SELECT COALESCE(SUM(i.total * i.currencyRate),0) AS `total` FROM InvoiceTransaction i WHERE i.ActivityType = '"
								+ ActivityType.Payment
								+ "' AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"
								+ conds.get("project").toString()
								+ "' AND i.transactionDate >= '" + dateStart
								+ "' AND i.transactionDate <= '" + dateEnd
								+ "' "
								+ sql
								+ ")");
			}
		}
		/** TAB nhân viên */
		if (conds.containsKey("employee")) {
			if (conds.get("employee").toString().isEmpty()) {
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`",
						"column")
				.cond("i.startDate >= '" + dateStart
						+ "' AND i.startDate <= '" + dateEnd + "' "
						+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' ")
				.cond("i.ActivityType = '"
						+ ActivityType.Payment
						+ "' UNION ALL (SELECT COALESCE(SUM(t.total* t.currencyRate),0) AS `total` FROM InvoiceTransaction t WHERE t.ActivityType = '"
						+ ActivityType.Payment
						+ "' AND t.transactionDate >= '" + dateStart
						+ "' AND t.transactionDate <= '" + dateEnd
						+ "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
						+")"
						);
			} else {
				
				Employee e = HRModelManager.getInstance().getBydLoginId(conds.get("employee").toString());
				nameNode = e.getName();
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`",
						"column")
				.cond("i.startDate >= '"
						+ dateStart
						+ "' AND i.startDate <= '"
						+ dateEnd
						+ "' AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"
						+ conds.get("project").toString() + "' "
						+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' ")
				.cond("i.ActivityType = '"
						+ ActivityType.Payment
						+ "' UNION ALL (SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t WHERE t.ActivityType = '"
						+ ActivityType.Payment
						+ "' AND SUBSTRING_INDEX(t.projectCode, '/', -1) = '"
						+ conds.get("project").toString()
						+ "' AND t.transactionDate >= '" + dateStart
						+ "' AND t.transactionDate <= '" + dateEnd
						+ "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
						+")");
			}
		}

		return rQuery;
	}

	private SQLSelectQuery querySQL_DoanhThu(String sql) throws Exception {
		SQLSelectQuery rQuery = new SQLSelectQuery();
	
		/** TAB dự án */
		if (conds.containsKey("project")) {
			if (conds.get("project").toString().isEmpty()) {
				rQuery.table("InvoiceDetail i")
						.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`",
								"column")
						.cond("i.startDate >= '" + dateStart
								+ "' AND i.startDate <= '" + dateEnd + "' "
								+ sql)
						.cond("i.ActivityType = '"
								+ ActivityType.Receipt
								+ "' UNION ALL (SELECT COALESCE(SUM(i.total * i.currencyRate),0) AS `total` FROM InvoiceTransaction i WHERE i.ActivityType = '"
								+ ActivityType.Receipt
								+ "' AND i.transactionDate >= '" + dateStart
								+ "' AND i.transactionDate <= '" + dateEnd
								+ "' "
								+ sql
								+")");
			} else {
				nameNode = RestaurantModelManager.getInstance()
						.getProjectByCode(conds.get("project").toString())
						.getName();
				rQuery.table("InvoiceDetail i")
						.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`",
								"column")
						.cond("i.startDate >= '"
								+ dateStart
								+ "' AND i.startDate <= '"
								+ dateEnd
								+ "' AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"
								+ conds.get("project").toString() + "' "
								+ sql)
						.cond("i.ActivityType = '"
								+ ActivityType.Receipt
								+ "' UNION ALL (SELECT COALESCE(SUM(i.total * i.currencyRate),0) AS `total` FROM InvoiceTransaction i WHERE i.ActivityType = '"
								+ ActivityType.Receipt
								+ "' AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"
								+ conds.get("project").toString()
								+ "' AND i.transactionDate >= '" + dateStart
								+ "' AND i.transactionDate <= '" + dateEnd
								+ "' "
								+ sql
								+")");
			}
		}
		/** TAB nhân viên */
		if (conds.containsKey("employee")) {
			if (conds.get("employee").toString().isEmpty()) {
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`",
						"column")
				.cond("i.startDate >= '" + dateStart
						+ "' AND i.startDate <= '" + dateEnd + "' "
						+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' ")
				.cond("i.ActivityType = '"
						+ ActivityType.Receipt
						+ "' UNION ALL (SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t WHERE t.ActivityType = '"
						+ ActivityType.Receipt
						+ "' AND t.transactionDate >= '" + dateStart
						+ "' AND t.transactionDate <= '" + dateEnd
						+ "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
						+")");
			} else {
				
				Employee e = HRModelManager.getInstance().getBydLoginId(conds.get("employee").toString());
				nameNode = e.getName();
				
				rQuery.table("InvoiceDetail i")
				.field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `finalCharge`",
						"column")
				.cond("i.startDate >= '"
						+ dateStart
						+ "' AND i.startDate <= '"
						+ dateEnd
						+ "' AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"
						+ conds.get("project").toString() + "' "
						+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' ")
				.cond("i.ActivityType = '"
						+ ActivityType.Receipt
						+ "' UNION ALL (SELECT COALESCE(SUM(t.total * t.currencyRate),0) AS `total` FROM InvoiceTransaction t WHERE t.ActivityType = '"
						+ ActivityType.Receipt
						+ "' AND SUBSTRING_INDEX(t.projectCode, '/', -1) = '"
						+ conds.get("project").toString()
						+ "' AND t.transactionDate >= '" + dateStart
						+ "' AND t.transactionDate <= '" + dateEnd
						+ "' "
						+ "AND t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "
						+")");
			
			}
		}

		return rQuery;
	}

	private ReportProjectMember getRowFromQuery(String[] row) {
		double sumFinal = 0;
		double sumTotal = 0;
		double sumFinal0 = 0;
		double sumTotal0 = 0;
		double ratio = 0;
		if (viewMoney.equals("Nghìn")) {
			sumFinal = Double.parseDouble(row[0].toString()) / 1000;
			if (row.length == 2)
				sumTotal = Double.parseDouble(row[1].toString()) / 1000;
			if (row.length > 2) {
				sumTotal = Double.parseDouble(row[1].toString()) / 1000;
				sumFinal0 = Double.parseDouble(row[2].toString()) / 1000;
				sumTotal0 = Double.parseDouble(row[3].toString()) / 1000;
			}
		} else if (viewMoney.equals("Triệu")) {
			sumFinal = Double.parseDouble(row[0].toString()) / 1000000;
			if (row.length == 2)
				sumTotal = Double.parseDouble(row[1].toString()) / 1000000;
			if (row.length > 2) {
				sumTotal = Double.parseDouble(row[1].toString()) / 1000000;
				sumFinal0 = Double.parseDouble(row[2].toString()) / 1000000;
				sumTotal0 = Double.parseDouble(row[3].toString()) / 1000000;
			}
		} else {
			sumFinal = Double.parseDouble(row[0].toString());
			if (row.length == 2)
				sumTotal = Double.parseDouble(row[1].toString());
			if (row.length > 2) {
				sumTotal = Double.parseDouble(row[1].toString());
				sumFinal0 = Double.parseDouble(row[2].toString());
				sumTotal0 = Double.parseDouble(row[3].toString());
			}
		}
		ReportProjectMember rpm = null;
		if(conds.containsKey("project"))
		{
			if (typeReport.equals("Doanh thu thuần")){

				ratio = ((sumFinal - sumTotal) /sumFinal) *100;
				rpm = new ReportProjectMember(nameNode, Double.toString(sumFinal),
						Double.toString(sumTotal), Double.toString(sumFinal - sumTotal),Double.toString(ratio), 0);
			}
			else if(typeReport.equals("Thưởng"))
			{
				rpm = new ReportProjectMember(nameNode, Double.toString(sumFinal),null, null, 0);
			}
			else
			{
				ratio = (sumTotal / sumFinal) * 100;
				rpm = new ReportProjectMember(nameNode, Double.toString(sumFinal),Double.toString(sumTotal), Double.toString(ratio), 0);
			}
		}
		else if(conds.containsKey("employee"))
		{
			if (typeReport.equals("Doanh thu thuần")){
				rpm = new ReportProjectMember(nameNode, "",
						"", "", 0);
			}
//			else if(typeReport.equals("Thưởng"))
//			{
//				rpm = new ReportProjectMember(nameNode, null,
//						null, 0);
//			}
			else
			{
				rpm = new ReportProjectMember(nameNode, "",
						"", 0);
			}
		}
		
			/** Thống kê theo dự án */
			if (conds.containsKey("project")) {
				if(!conds.get("project").toString().isEmpty())
				{
				rpm.setType(Type.PARENT);
				rpm.setParent(conds.get("project").toString());
				}
				else
				{
					rpm.setType(Type.ROOT);
				}

			} else if (conds.containsKey("employee"))  {
				/** Thống kê theo nhân viên */
					if(!conds.get("employee").toString().isEmpty())
					{
					rpm.setType(Type.PARENT);
					rpm.setParent(conds.get("employee").toString());
					}
				 else
				{
					rpm.setType(Type.ROOT);

				}
			}
		return rpm;
	}

	private String[] runQuery(SQLSelectQuery rQuery) throws Exception {
		System.out.println(rQuery.createSQLQuery());
		ReportTable[] reportTable = AccountingModelManager.getInstance()
				.reportQuery(new SQLSelectQuery[] { rQuery });
		reportTable[0].dump(new String[] { "column" });
		List<Map<String, Object>> records = reportTable[0].getRecords();
		String[] value = new String[records.size()];
		for (int i = 0; i < records.size(); i++) {
			Object object = records.get(i).get("column");
			value[i] = object.toString();
		}
		return value;
	}
}
