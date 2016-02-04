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
import com.hkt.module.accounting.entity.BankTransaction;
import com.hkt.module.accounting.entity.Invoice.ActivityType;
import com.hkt.module.config.generic.OptionGroup;
import com.hkt.module.core.entity.ReportTable;
import com.hkt.module.core.entity.SQLSelectQuery;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.swingexp.app.report.entity.ReportBankEntity;
import com.hkt.swingexp.app.report.entity.ReportBankEntity.Type;
import com.hkt.swingexp.app.report.modeltable.ModelTableReportBank;

public class TableReportBank extends JTable {
	private ModelTableReportBank	model;
	private HashMap<String, String>	conds;
	private String									viewMoney;
	private String									nameNode;
	private String									typeReport;
	private String									dateStart					= null;
	private String									dateEnd						= null;
//	private long 									num_DateEnd 				= 0;
//	private long 									num_DateStart 				= 0;
//	private long 									num_DateStart_old 			= 0;
//	private long 									num_DateEnd_old 			= 0;
	private boolean 								isCheck;

	public TableReportBank(HashMap<String, String> conds, String typeReport, Date dateStart, Date dateEnd, String viewMoney, boolean isCheck) {
		this.conds = conds;
		this.viewMoney = viewMoney;
		this.typeReport = typeReport;
		this.isCheck = isCheck;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
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
		
//		num_DateEnd = Long.parseLong(this.dateEnd);
//		num_DateStart = Long.parseLong(this.dateStart);
//		num_DateStart_old = Long.parseLong(this.dateStart) - 10000000000L;
//		num_DateEnd_old = Long.parseLong(this.dateEnd) - 10000000000L;
		
		try {
			nameNode = AccountModelManager.getInstance().getNameByLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
			System.out.println("NAME NODE   " + nameNode);
		} catch(Exception e){
			nameNode = "";
		}
		
		try {
			model = new ModelTableReportBank(getNodeParent());
			this.setModel(model);
			for (int i = 0; i < this.getColumnCount(); i++) {
				this.getColumnModel().getColumn(i).setCellRenderer(new TableRerenderBank(model, typeReport, conds, dateStart, dateEnd, viewMoney,isCheck));
				this.getColumnModel().getColumn(i).setCellEditor(new TableRerenderBank(model, typeReport, conds, dateStart, dateEnd, viewMoney, isCheck));
			}
			model.fireTableDataChanged();
			this.setModel(model);
		} catch (Exception e) {
			model = new ModelTableReportBank(new ArrayList<ReportBankEntity>());
			this.setModel(model);
		}		
		this.setRowHeight(23);
		this.getColumnModel().getColumn(1).setMinWidth(270);
		this.setFont(new Font("Tahoma", 0, 14));
		this.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		this.setBackground(Color.WHITE);
		this.setFillsViewportHeight(true);
	}
	
	public void setReportGui(ReportStatistics reportStatistics){
		model.setReportStatistics(reportStatistics);
	}

	private List<ReportBankEntity> getNodeParent() throws Exception {
		List<ReportBankEntity> employees = new ArrayList<ReportBankEntity>();
			employees.add(new ReportBankEntity("Thống kê kế toán", "", "", "", 0));
		return employees;
	}
//	
//	private String sumBank(long dateStart, long dateEnd){
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//		double iSum = 0;
//		List <BankTransaction> lsBank = new ArrayList<BankTransaction>();
//		lsBank = AccountingModelManager.getInstance().getAllBankTransaction();
//		for (int i=0; i<lsBank.size(); i++){
//			long dateNow = Long.parseLong(dateFormat.format(lsBank.get(i).getCreatedTime()));
//			if (dateNow >= dateStart && dateNow <= dateEnd){
//				iSum = iSum + lsBank.get(i).getTotal();
//			}
//		}
//		if (viewMoney.equals("Nghìn")){
//			iSum = iSum / 1000;
//		} else if (viewMoney.equals("Triệu")) {
//			iSum = iSum / 1000000;
//		}
//		return "" + iSum;
//	}
}