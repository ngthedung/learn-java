package com.hkt.swingexp.app.report.table;

import java.awt.Component;
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

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.hibernate.id.CompositeNestedGeneratedValueGenerator.GenerationContextLocator;
import org.jfree.data.general.DefaultPieDataset;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.AccountingServiceClient;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.hethong.Processing;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.GenericOptionModelManager;
import com.hkt.module.accounting.entity.Bank;
import com.hkt.module.accounting.entity.BankTransaction;
import com.hkt.module.accounting.entity.Contributor;
import com.hkt.module.accounting.entity.Invoice.ActivityType;
import com.hkt.module.config.generic.OptionGroup;
import com.hkt.module.core.entity.ReportTable;
import com.hkt.module.core.entity.SQLSelectQuery;
import com.hkt.swingexp.app.report.entity.DemoEntity;
import com.hkt.swingexp.app.report.entity.PanelTableCell;
import com.hkt.swingexp.app.report.entity.ReportBankEntity;
import com.hkt.swingexp.app.report.entity.ReportBankEntity;
import com.hkt.swingexp.app.report.entity.ReportBankEntity.Type;
import com.hkt.swingexp.app.report.modeltable.ModelTableReportBank;
import com.hkt.swingexp.app.report.modeltable.ModelTableReportBank;

public class TableRerenderBank extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
	private PanelTableCell cell;
	private HashMap<String, String> conds;
	private String viewMoney;
	private String typeReport;
	private ModelTableReportBank model;
	private Processing processing;
	private String dateEnd = null;
	private String dateStart = null;
	private long num_DateEnd = 0;
	private long num_DateStart = 0;
	private long num_DateStart_old = 0;
	private long num_DateEnd_old = 0;
	private String pathParent = "";
	private ImageIcon nodeIconAdd, nodeIconMulti, nodeIconPerson, nodeIconProduct, nodeIconInvoice;
	private static ClientContext clientContext = ClientContext.getInstance();
	private static AccountingServiceClient accountingServiceClient = clientContext.getBean(AccountingServiceClient.class);
	private boolean isCheck;

	public TableRerenderBank(ModelTableReportBank model, String typeReport, HashMap<String, String> hash,
	    Date dateStart, Date dateEnd, String viewMoney, boolean isCheck) {
		nodeIconAdd = new ImageIcon(HomeScreen.class.getResource("icon/square_add_16.png"));
		nodeIconMulti = new ImageIcon(HomeScreen.class.getResource("icon/square_multi_16.png"));
		nodeIconPerson = new ImageIcon(HomeScreen.class.getResource("icon/user_16.png"));
		nodeIconProduct = new ImageIcon(HomeScreen.class.getResource("icon/square_product_16.png"));
		nodeIconInvoice = new ImageIcon(HomeScreen.class.getResource("icon/document_edit_16.png"));

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar calendarStart = Calendar.getInstance();
		if (dateStart != null) {
			calendarStart.setTime(dateStart);
			calendarStart.set(Calendar.HOUR_OF_DAY, 00);
			calendarStart.set(Calendar.MINUTE, 00);
			calendarStart.set(Calendar.SECOND, 00);
		}
		Calendar calendarEnd = Calendar.getInstance();
		if (dateEnd != null) {
			calendarEnd.setTime(dateEnd);
			calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
			calendarEnd.set(Calendar.MINUTE, 59);
			calendarEnd.set(Calendar.SECOND, 59);
		}
		this.dateStart = dateFormat.format(calendarStart.getTime());
		this.dateEnd = dateFormat.format(calendarEnd.getTime());
		
		num_DateEnd = Long.parseLong(this.dateEnd);
		num_DateStart = Long.parseLong(this.dateStart);
		
		num_DateStart_old = Long.parseLong(this.dateStart) - 10000000000L;
		num_DateEnd_old = Long.parseLong(this.dateEnd) - 10000000000L;

		this.model = model;
		this.conds = hash;
		this.viewMoney = viewMoney;
		this.typeReport = typeReport;
		this.isCheck = isCheck;
		cell = new PanelTableCell();
		this.processing = new Processing();
//		showReport();
	}
	
//	private void showReport(){
//		cell.setRow(0);
////		int i = 0;
//		int indexs = 1;
//		for (int i=1; i<5; i++) {
//			Bank banks = new Bank();
//			List <Bank> lsBankName = new ArrayList<Bank>();
//			List <Bank> lsBank = new ArrayList<Bank>();
//			Object parent = cell.getObject();
//			System.out.println("------------------------------------------------------" );
//			ReportBankEntity rowCurrent = new ReportBankEntity();
////			i = i + 1;
//	//		System.out.println("WHYYYYY   " + cell.getObject().toString());
//			System.out.println("iiiiiiiiiiiiiiiiii  "  + i);
//			System.out.println("CELLLLL ROWWWWW  "  + cell.getRow());
//			System.out.println("GET VALUEEEE  "  + model.getValueAt(cell.getRow(), 0).toString());
//			System.out.println("INDEXXXXXXX  "  + indexs);
//			List<ReportBankEntity> listChilds = new ArrayList<ReportBankEntity>();
//			try {
//						if (model.getValueAt(0, 0).toString().equals("Thống kê kế toán")){
////							System.out.println("CELL GET ROWWW   " + model.getValueAt(cell.getRow(), 0).toString());
//							lsBank = AccountingModelManager.getInstance().getBanks(1);
//							listChilds.add(new ReportBankEntity(lsBank.get(0).getBankCode(), lsBank.get(0).getName(), 
//									returnTotal("ts",num_DateStart, num_DateEnd), returnTotal("ts", num_DateStart_old, num_DateEnd_old), indexs));
//							listChilds.add(new ReportBankEntity(lsBank.get(1).getBankCode(), lsBank.get(1).getName(), 
//									returnTotal("nv", num_DateStart, num_DateEnd), returnTotal("nv", num_DateStart_old, num_DateEnd_old), indexs));
//							rowCurrent.setListChild(listChilds);
//							model.addRowChild(rowCurrent.getListChild(), i);
//							rowCurrent.getLabelIcon().setIcon(nodeIconMulti);
//							rowCurrent.setWiden(true);
//							cell.setRow(i);
//////						}}
////						if (rowCurrent.getNode().equals("Thống kê kế toán")){
////							lsBank = AccountingModelManager.getInstance().getBanks(1);
////								listChilds.add(new ReportBankEntity(lsBank.get(0).getBankCode(), lsBank.get(0).getName(), 
////										returnTotal("ts",num_DateStart, num_DateEnd), returnTotal("ts", num_DateStart_old, num_DateEnd_old), indexs));
////								listChilds.add(new ReportBankEntity(lsBank.get(1).getBankCode(), lsBank.get(1).getName(), 
////										returnTotal("nv", num_DateStart, num_DateEnd), returnTotal("nv", num_DateStart_old, num_DateEnd_old), indexs));
//						} else {
//							System.out.println("TIEPPPPPPPPP");}
////							lsBankName = AccountingModelManager.getInstance().getByBankName(model.getValueAt(cell.getRow(), 0).toString());
////							for (int k=0; k<lsBankName.size(); k++){
////								if (lsBankName.get(k).getBankCode().equals(rowCurrent.getNode())){
////									lsBank = AccountingModelManager.getInstance().getBanks(returnIndex(lsBankName.get(k).getCode()));
////									for (int j=0; j<lsBank.size(); j++){
////										listChilds.add(new ReportBankEntity(lsBank.get(j).getBankCode(), lsBank.get(j).getName(), 
////												returnTotal(lsBankName.get(k).getCode() + "." + lsBank.get(j).getBankCode(), num_DateStart, num_DateEnd), 
////												returnTotal(lsBankName.get(k).getCode() + "." + lsBank.get(j).getBankCode(), num_DateStart_old, num_DateEnd_old), indexs));
//////										rowCurrent.setListChild(listChilds);
////										model.addRowChild(listChilds, i);
////										rowCurrent.getLabelIcon().setIcon(nodeIconMulti);
////										rowCurrent.setWiden(true);
////										cell.setRow(i);
//////									}
////								}
////							}
////						}
////						rowCurrent.setListChild(listChilds);
////					}
////					model.addRowChild(rowCurrent.getListChild(), i);
////					rowCurrent.getLabelIcon().setIcon(nodeIconMulti);
////					rowCurrent.setWiden(true);
////					cell.setRow(i);
////				} 
////				else {
////					model.removeRowReport(cell.getRow());
////					rowCurrent.getLabelIcon().setIcon(nodeIconAdd);
////					rowCurrent.setWiden(false);
////				}
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
//			if (rowCurrent.getListChild() == null) {
//				indexs = 1;
//				if (rowCurrent != null) {
//					indexs = indexs + rowCurrent.getIndex();
//			}}
//			cell.setRow(i);
//			System.out.println("CELLLL ROWWWWW  "  + cell.getRow());}
//	}

	private void mouseClick_CellTable() {
		// ---------------START RUN TIME QUERY----------------
		long startTime = System.currentTimeMillis();
		// ---------------------------------------------------
		Bank banks = new Bank();
		List <Bank> lsBankName = new ArrayList<Bank>();
		List <Bank> lsBank = new ArrayList<Bank>();
		Object parent = cell.getObject();
		int indexRowAfter = 0;
		if (cell.getRow() < model.getRowCount() - 1)
			indexRowAfter = ((ReportBankEntity) model.getValueAt(cell.getRow() + 1, 0)).getIndex();
			try {
				ReportBankEntity rowCurrent = (ReportBankEntity) parent;
				if (indexRowAfter <= rowCurrent.getIndex()) {
					int i = cell.getRow() + 1;
					if (rowCurrent.getListChild() == null) {
						int indexs = 1;
						if (rowCurrent != null) {
							indexs = indexs + rowCurrent.getIndex();
						}
						List<ReportBankEntity> listChilds = new ArrayList<ReportBankEntity>();
						banks = AccountingModelManager.getInstance().getBankByCode(rowCurrent.getNode());
						if (rowCurrent.getNode().equals("Thống kê kế toán")){
							lsBank = AccountingModelManager.getInstance().getBanks(1);
								listChilds.add(new ReportBankEntity(lsBank.get(0).getCode(), lsBank.get(0).getName(), 
										returnTotal("ts",num_DateStart, num_DateEnd), returnTotal("ts", num_DateStart_old, num_DateEnd_old), indexs));
								listChilds.add(new ReportBankEntity(lsBank.get(1).getCode(), lsBank.get(1).getName(), 
										returnTotal("nv", num_DateStart, num_DateEnd), returnTotal("nv", num_DateStart_old, num_DateEnd_old), indexs));
						} else {
							lsBankName = AccountingModelManager.getInstance().getByBankName(rowCurrent.getColumn1());
							for (int k=0; k<lsBankName.size(); k++){
								if (lsBankName.get(k).getCode().equals(rowCurrent.getNode())){
									lsBank = AccountingModelManager.getInstance().getBanks(returnIndex(lsBankName.get(k).getBankCode()));
									for (int j=0; j<lsBank.size(); j++){
										listChilds.add(new ReportBankEntity(lsBank.get(j).getCode(), lsBank.get(j).getName(), 
												returnTotal(lsBankName.get(k).getCode() + "." + lsBank.get(j).getCode(), num_DateStart, num_DateEnd), 
												returnTotal(lsBankName.get(k).getCode() + "." + lsBank.get(j).getCode(), num_DateStart_old, num_DateEnd_old), indexs));
									}
								}
							}
						}
						rowCurrent.setListChild(listChilds);
					}
					model.addRowChild(rowCurrent.getListChild(), i);
					rowCurrent.getLabelIcon().setIcon(nodeIconMulti);
					rowCurrent.setWiden(true);
				} else {
					model.removeRowReport(cell.getRow());
					rowCurrent.getLabelIcon().setIcon(nodeIconAdd);
					rowCurrent.setWiden(false);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		// ---------------RETURN TIME EXECUTE QUERY------------------
		long endTime = System.currentTimeMillis();
		NumberFormat formatter = new DecimalFormat("#0.00000");
		String time = formatter.format((endTime - startTime) / 1000d);
		System.out.println("###$$$ TIME RUN QUERY: " + time);
		// -----------------------------------------------------------
//		}
	}
	
	private int returnIndex(String bankCode){
		int indexChild = 20;
		if (bankCode.equals("ts"))
			indexChild = 2;
		if (bankCode.equals("nv"))
			indexChild = 3;
		if (bankCode.equals("ts.A"))
			indexChild = 4;
		if (bankCode.equals("ts.B"))
			indexChild = 5;
		if (bankCode.equals("nv.A"))
			indexChild = 6;
		if (bankCode.equals("nv.B"))
			indexChild = 7;
		if (bankCode.equals("ts.AI"))
			indexChild = 8;
		if (bankCode.equals("ts.AII"))
			indexChild = 9;
		if (bankCode.equals("ts.AIII"))
			indexChild = 10;
		if (bankCode.equals("ts.AIV"))
			indexChild = 11;
		if (bankCode.equals("ts.AV"))
			indexChild = 12;
		if (bankCode.equals("ts.BI"))
			indexChild = 13;
		if (bankCode.equals("ts.BII"))
			indexChild = 14;
		if (bankCode.equals("ts.BIII"))
			indexChild = 15;
		if (bankCode.equals("ts.BIV"))
			indexChild = 16;
		if (bankCode.equals("nv.AI"))
			indexChild = 17;
		if (bankCode.equals("nv.AII"))
			indexChild = 18;
		if (bankCode.equals("nv.BI"))
			indexChild = 19;
		return indexChild;
	}
	
	private String returnTotal(String code, long dateStart, long dateEnd){
//		System.out.println("CODEEEEEEE  " + code);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		double sum = 0;
		String [] arr = code.split("[.]");
		String total = "";
		if (code.equals("ts")){
		List<BankTransaction> lsBankTotal = AccountingModelManager.getInstance().getByBankCode("ts");
		for (int i=0; i<lsBankTotal.size(); i++){
			long dateNow = Long.parseLong(dateFormat.format(lsBankTotal.get(i).getCreatedTime()));
			if (dateNow >= dateStart && dateNow <= dateEnd){
				sum = sum + lsBankTotal.get(i).getTotal();
			}
			}
		} else if (code.equals("nv")){
			List<BankTransaction> lsBankTotal = AccountingModelManager.getInstance().getByBankCode("nv");
			for (int i=0; i<lsBankTotal.size(); i++){
				long dateNow = Long.parseLong(dateFormat.format(lsBankTotal.get(i).getCreatedTime()));
				if (dateNow >= dateStart && dateNow <= dateEnd){
					sum = sum + lsBankTotal.get(i).getTotal();
				}
			}
		} else if (code.equals("ts.A")){
			List<BankTransaction> lsBankTotal = AccountingModelManager.getInstance().getByBankCode("ts.A");
			for (int i=0; i<lsBankTotal.size(); i++){
				long dateNow = Long.parseLong(dateFormat.format(lsBankTotal.get(i).getCreatedTime()));
				if (dateNow >= dateStart && dateNow <= dateEnd){
					sum = sum + lsBankTotal.get(i).getTotal();
				}
			}
		} else if (code.equals("ts.B")){
			List<BankTransaction> lsBankTotal = AccountingModelManager.getInstance().getByBankCode("ts.B");
			for (int i=0; i<lsBankTotal.size(); i++){
				long dateNow = Long.parseLong(dateFormat.format(lsBankTotal.get(i).getCreatedTime()));
				if (dateNow >= dateStart && dateNow <= dateEnd){
					sum = sum + lsBankTotal.get(i).getTotal();
				}
				}
		} else if (code.equals("ts.A.AI")){
			List<BankTransaction> lsBankTotal = AccountingModelManager.getInstance().getByBankCode("ts.AI");
			for (int i=0; i<lsBankTotal.size(); i++){
				if (lsBankTotal.get(i).getBankCode().equals("ts.AI")){
					long dateNow = Long.parseLong(dateFormat.format(lsBankTotal.get(i).getCreatedTime()));
					if (dateNow >= dateStart && dateNow <= dateEnd){
						sum = sum + lsBankTotal.get(i).getTotal();
					}}
			}
		} else if (code.equals("ts.A.AII")){
			List<BankTransaction> lsBankTotal = AccountingModelManager.getInstance().getByBankCode("ts.AII");
			for (int i=0; i<lsBankTotal.size(); i++){
				if (lsBankTotal.get(i).getBankCode().equals("ts.AII")){
					long dateNow = Long.parseLong(dateFormat.format(lsBankTotal.get(i).getCreatedTime()));
					if (dateNow >= dateStart && dateNow <= dateEnd){
						sum = sum + lsBankTotal.get(i).getTotal();
					}}
			}
		} else if (code.equals("ts.A.AIII")){
			List<BankTransaction> lsBankTotal = AccountingModelManager.getInstance().getByBankCode("ts.AIII");
			for (int i=0; i<lsBankTotal.size(); i++){
				long dateNow = Long.parseLong(dateFormat.format(lsBankTotal.get(i).getCreatedTime()));
				if (dateNow >= dateStart && dateNow <= dateEnd){
					sum = sum + lsBankTotal.get(i).getTotal();
				}
			}
		} else if (code.equals("ts.A.AIV")){
			List<BankTransaction> lsBankTotal = AccountingModelManager.getInstance().getByBankCode("ts.AIV");
			for (int i=0; i<lsBankTotal.size(); i++){
				long dateNow = Long.parseLong(dateFormat.format(lsBankTotal.get(i).getCreatedTime()));
				if (dateNow >= dateStart && dateNow <= dateEnd){
					sum = sum + lsBankTotal.get(i).getTotal();
				}
			}
		} else if (code.equals("ts.A.AV")){
			List<BankTransaction> lsBankTotal = AccountingModelManager.getInstance().getByBankCode("ts.AV");
			for (int i=0; i<lsBankTotal.size(); i++){
				long dateNow = Long.parseLong(dateFormat.format(lsBankTotal.get(i).getCreatedTime()));
				if (dateNow >= dateStart && dateNow <= dateEnd){
					sum = sum + lsBankTotal.get(i).getTotal();
				}
			}
		} else if (code.equals("ts.B.BI")){
			List<BankTransaction> lsBankTotal = AccountingModelManager.getInstance().getByBankCode("ts.BI");
			for (int i=0; i<lsBankTotal.size(); i++){
				if (lsBankTotal.get(i).getBankCode().equals("ts.BI")){
					long dateNow = Long.parseLong(dateFormat.format(lsBankTotal.get(i).getCreatedTime()));
					if (dateNow >= dateStart && dateNow <= dateEnd){
						sum = sum + lsBankTotal.get(i).getTotal();
					}}
			}
		} else if (code.equals("ts.B.BII")){
			List<BankTransaction> lsBankTotal = AccountingModelManager.getInstance().getByBankCode("ts.BII");
			for (int i=0; i<lsBankTotal.size(); i++){
				if (lsBankTotal.get(i).getBankCode().equals("ts.BII")){
					long dateNow = Long.parseLong(dateFormat.format(lsBankTotal.get(i).getCreatedTime()));
					if (dateNow >= dateStart && dateNow <= dateEnd){
						sum = sum + lsBankTotal.get(i).getTotal();
					}}
			}
		} else if (code.equals("ts.B.BIII")){
			List<BankTransaction> lsBankTotal = AccountingModelManager.getInstance().getByBankCode("ts.BIII");
			for (int i=0; i<lsBankTotal.size(); i++){
				long dateNow = Long.parseLong(dateFormat.format(lsBankTotal.get(i).getCreatedTime()));
				if (dateNow >= dateStart && dateNow <= dateEnd){
					sum = sum + lsBankTotal.get(i).getTotal();
				}
			}
		} else if (code.equals("ts.B.BIV")){
			List<BankTransaction> lsBankTotal = AccountingModelManager.getInstance().getByBankCode("ts.BIV");
			for (int i=0; i<lsBankTotal.size(); i++){
				long dateNow = Long.parseLong(dateFormat.format(lsBankTotal.get(i).getCreatedTime()));
				if (dateNow >= dateStart && dateNow <= dateEnd){
					sum = sum + lsBankTotal.get(i).getTotal();
				}
			}
		} else if (code.equals("nv.A")){
			List<BankTransaction> lsBankTotal = AccountingModelManager.getInstance().getByBankCode("nv.A");
			for (int i=0; i<lsBankTotal.size(); i++){
				long dateNow = Long.parseLong(dateFormat.format(lsBankTotal.get(i).getCreatedTime()));
				if (dateNow >= dateStart && dateNow <= dateEnd){
					sum = sum + lsBankTotal.get(i).getTotal();
				}
			}
		} else if (code.equals("nv.B")){
			List<BankTransaction> lsBankTotal = AccountingModelManager.getInstance().getByBankCode("nv.B");
			for (int i=0; i<lsBankTotal.size(); i++){
				long dateNow = Long.parseLong(dateFormat.format(lsBankTotal.get(i).getCreatedTime()));
				if (dateNow >= dateStart && dateNow <= dateEnd){
					sum = sum + lsBankTotal.get(i).getTotal();
				}
			}
		} else if (code.equals("nv.A.AI")){
			List<BankTransaction> lsBankTotal = AccountingModelManager.getInstance().getByBankCode("nv.AI");
			for (int i=0; i<lsBankTotal.size(); i++){
				if (lsBankTotal.get(i).getBankCode().equals("nv.AI")){
					long dateNow = Long.parseLong(dateFormat.format(lsBankTotal.get(i).getCreatedTime()));
					if (dateNow >= dateStart && dateNow <= dateEnd){
						sum = sum + lsBankTotal.get(i).getTotal();
					}}
			}
		} else if (code.equals("nv.A.AII")){
			List<BankTransaction> lsBankTotal = AccountingModelManager.getInstance().getByBankCode("nv.AII");
			for (int i=0; i<lsBankTotal.size(); i++){
				long dateNow = Long.parseLong(dateFormat.format(lsBankTotal.get(i).getCreatedTime()));
				if (dateNow >= dateStart && dateNow <= dateEnd){
					sum = sum + lsBankTotal.get(i).getTotal();
				}
			}
		} else if (code.equals("nv.B.BI")){
			List<BankTransaction> lsBankTotal = AccountingModelManager.getInstance().getByBankCode("nv.BI");
			for (int i=0; i<lsBankTotal.size(); i++){
				long dateNow = Long.parseLong(dateFormat.format(lsBankTotal.get(i).getCreatedTime()));
				if (dateNow >= dateStart && dateNow <= dateEnd){
					sum = sum + lsBankTotal.get(i).getTotal();
				}
			}
		} else {
			List<BankTransaction> lsBankTotal = AccountingModelManager.getInstance().getBankTransactionByCode(arr[arr.length-1]);
			for (int i=0; i<lsBankTotal.size(); i++){
//				System.out.println(lsBankTotal.get(i).getCode());
//				System.out.println("STARTTTT  " + dateStart + "   NOWWWWWWW   " + dateNow + "  ENDDDDDDD "+ dateEnd);
				long dateNow = Long.parseLong(dateFormat.format(lsBankTotal.get(i).getCreatedTime()));
				if (dateNow >= dateStart && dateNow <= dateEnd){
					sum = sum + lsBankTotal.get(i).getTotal();
				}
			}
		}
		if (viewMoney.equals("Nghìn")){
			sum = sum / 1000;
		} else if (viewMoney.equals("Triệu")) {
			sum = sum / 1000000;
		}
		return total = "" + sum;
	}

	
	/**
	 * @param truyền
	 *          vào nhóm cha được click
	 * @return trả về danh sách các nhóm con của nó
	 */
	private List<ReportBankEntity> getParentByPath(ReportBankEntity ReportBankEntity) throws Exception {
		List<ReportBankEntity> nodes = new ArrayList<ReportBankEntity>();
		String parent = null;
		int index = 1;
		if (ReportBankEntity != null) {
			parent = ReportBankEntity.getParent();
			index = index + ReportBankEntity.getIndex();
		}

		/**
		 ********************************************************** 
		 * PARENT: THỐNG KÊ DOANH THU || CHI PHÍ || THU CHI LÃI *
		 ********************************************************** 
		 */

		SQLSelectQuery rQuery = new SQLSelectQuery();
		SQLSelectQuery rQueryR = new SQLSelectQuery();
		SQLSelectQuery rQueryP = new SQLSelectQuery();
		if (typeReport.equals("Doanh thu"))
			rQuery = query_Parent_DoanhThu_ChiPhi(ReportBankEntity, parent, index, ActivityType.Receipt);
		if (typeReport.equals("Chi phí"))
			rQuery = query_Parent_DoanhThu_ChiPhi(ReportBankEntity, parent, index, ActivityType.Payment);
		if (typeReport.equals("Thu - Chi - Lãi"))
			rQuery = query_Parent_ThuChiLai(parent, index, false);
		if (typeReport.equals("Thu - Chi - Công nợ")) {
			rQueryR = query_Parent_DoanhThu_ChiPhi(ReportBankEntity, parent, index, ActivityType.Receipt);
			rQueryP = query_Parent_DoanhThu_ChiPhi(ReportBankEntity, parent, index, ActivityType.Payment);
		}

		if (!typeReport.equals("Thu - Chi - Công nợ")) {
			System.out.println(rQuery.createSQLQuery());
			ReportTable[] reportTable = accountingServiceClient.reportQuery(new SQLSelectQuery[] { rQuery });
			String[] field;
			if (typeReport.equals("Thu - Chi - Công nợ")) {
				reportTable[0].dump(new String[] { "node", "final1", "total1", "final", "total", "parent" });
				field = new String[] { "node", "final1", "total1", "final", "total", "parent" };
			} else {
				reportTable[0].dump(new String[] { "node", "final", "total", "parent" });
				field = new String[] { "node", "final", "total", "parent" };
			}
			List<Map<String, Object>> records = reportTable[0].getRecords();
			for (int i = 0; i < records.size(); i++) {
				Map<String, Object> record = records.get(i);
				Object[] values = new Object[field.length];
				for (int j = 0; j < field.length; j++) {
					if (!typeReport.equals("Thu - Chi - Lãi") && conds.containsKey("product1") && j == 2) {
						values[j] = 0;
					} else {

						values[j] = record.get(field[j]);
					}
				}

				double sumFinalCharge;
				double sumTotalTransaction;
				double sumTotalTransactionReceipt = 0;
				double sumTotalTransactionPayment = 0;
				double sumPhaiThu = 0;
				double sumPhaiChi = 0;
				double ratio = 0;
				if (viewMoney.equals("Nghìn")) {
					sumFinalCharge = Double.parseDouble(values[1].toString()) / 1000;
					sumTotalTransaction = Double.parseDouble(values[2].toString()) / 1000;
					if (typeReport.equals("Thu - Chi - Công nợ")) {
						sumTotalTransactionReceipt = Double.parseDouble(values[3].toString()) / 1000;
						sumTotalTransactionPayment = Double.parseDouble(values[4].toString()) / 1000;
						sumPhaiThu = sumFinalCharge - sumTotalTransactionReceipt;
						sumPhaiChi = sumTotalTransaction - sumTotalTransactionPayment;
					}
				} else if (viewMoney.equals("Triệu")) {
					sumFinalCharge = Double.parseDouble(values[1].toString()) / 1000000;
					sumTotalTransaction = Double.parseDouble(values[2].toString()) / 1000000;
					if (typeReport.equals("Thu - Chi - Công nợ")) {
						sumTotalTransactionReceipt = Double.parseDouble(values[3].toString()) / 1000000;
						sumTotalTransactionPayment = Double.parseDouble(values[4].toString()) / 1000000;
						sumPhaiThu = sumFinalCharge - sumTotalTransactionReceipt;
						sumPhaiChi = sumTotalTransaction - sumTotalTransactionPayment;
					}
				} else {
					sumFinalCharge = Double.parseDouble(values[1].toString());
					sumTotalTransaction = Double.parseDouble(values[2].toString());
					if (typeReport.equals("Thu - Chi - Công nợ")) {
						sumTotalTransactionReceipt = Double.parseDouble(values[3].toString());
						sumTotalTransactionPayment = Double.parseDouble(values[4].toString());
						sumPhaiThu = sumFinalCharge - sumTotalTransactionReceipt;
						sumPhaiChi = sumTotalTransaction - sumTotalTransactionPayment;
					}
				}
				ReportBankEntity row;
				if (typeReport.equals("Thu - Chi - Công nợ")) {
					ratio = sumTotalTransactionReceipt - sumTotalTransactionPayment;
					row = new ReportBankEntity(values[0].toString(), Double.toString(sumFinalCharge),
					    Double.toString(sumTotalTransaction), Double.toString(sumTotalTransactionReceipt),
					    Double.toString(sumTotalTransactionPayment), Double.toString(sumPhaiThu), Double.toString(sumPhaiChi),
					    Double.toString(ratio), index);
					row.setParent(values[5].toString());
					row.setType(Type.PARENT);
					pathParent = values[5].toString();
					if (sumFinalCharge != 0 || sumTotalTransaction != 0 || sumTotalTransactionReceipt != 0
					    || sumTotalTransactionPayment != 0) {
						if (values[0].toString().equals("Phòng khác") || values[0].toString().equals("Nhóm khác")) {
							if (sumFinalCharge != 0 || sumTotalTransactionReceipt != 0 || sumTotalTransaction != 0
							    || sumTotalTransactionPayment != 0)
								nodes.add(nodes.size(), row);
						} else {
							if (parent == null || row.getParent().indexOf(parent) == 0) {
								nodes.add(0, row);
							}
						}
					}
				} else {
					ratio = (sumTotalTransaction / sumFinalCharge) * 100;
					if (typeReport.equals("Thu - Chi - Lãi"))
						ratio = sumFinalCharge - sumTotalTransaction;
					if (values[0] != null) {
						if (conds.containsKey("store")) {
							OptionGroup o = GenericOptionModelManager.getInstance().getOptionGroup("accounting", "AccountingService", "type_invoice");
							String name = o.getOption(values[0].toString()).getLabel();
							row = new ReportBankEntity(name, Double.toString(sumFinalCharge), Double.toString(sumTotalTransaction),
							    Double.toString(ratio), index);
						} else {
							row = new ReportBankEntity(values[0].toString(), Double.toString(sumFinalCharge),
							    Double.toString(sumTotalTransaction), Double.toString(ratio), index);
						}
						row.setParent(values[3].toString());
						row.setType(Type.PARENT);
						pathParent = values[3].toString();

						if (sumFinalCharge != 0 || sumTotalTransaction != 0) {
							if (values[0].toString().equals("Phòng khác") || values[0].toString().equals("Nhóm khác")) {
								nodes.add(nodes.size(), row);
							} else {
								nodes.add(row);
							}
						}
					}
				}
			}
			return nodes;
		} else {
			List<ReportBankEntity> lstReceipt = null;
			List<ReportBankEntity> lstPyament = null;

			lstReceipt = runQuery(rQueryR);
			lstPyament = runQuery(rQueryP);

			HashMap<String, ReportBankEntity> row_hash = new HashMap<String, ReportBankEntity>();
			if (lstReceipt != null && lstReceipt.size() > 0) {
				for (int i = 0; i < lstReceipt.size(); i++) {
					ReportBankEntity obj = new ReportBankEntity(lstReceipt.get(i).getNode(), lstReceipt.get(i).getColumn1(), lstReceipt
					    .get(i).getColumn2(), "0", "0", "0", "0", "0", index);
					obj.setParent(lstReceipt.get(i).getParent());
					row_hash.put(lstReceipt.get(i).getParent(), obj);
				}

			}

			if (lstPyament != null && lstPyament.size() > 0) {
				for (int i = 0; i < lstPyament.size(); i++) {
					if (!row_hash.containsKey(lstPyament.get(i).getParent())) {
						ReportBankEntity obj = new ReportBankEntity(lstPyament.get(i).getNode(), "0", "0",
						    lstPyament.get(i).getColumn1(), lstPyament.get(i).getColumn2(), "0", "0", "0", index);
						obj.setParent(lstPyament.get(i).getParent());
						row_hash.put(lstPyament.get(i).getParent(), obj);
					} else {
						ReportBankEntity re = row_hash.get(lstPyament.get(i).getParent());
						re.setColumn3(lstPyament.get(i).getColumn1());
						re.setColumn4(lstPyament.get(i).getColumn2());

					}
				}
			}

			int rate = 1;
			if (viewMoney.equals("Nghìn")) {
				rate = 1000;
			} else if (viewMoney.equals("Triệu")) {
				rate = 1000000;
			}

			Iterator<ReportBankEntity> rr = row_hash.values().iterator();
			while (rr.hasNext()) {
				ReportBankEntity r = rr.next();
				Double tongThu = MyDouble.parseDouble(r.getColumn1()) / rate;
				Double tongChi = MyDouble.parseDouble(r.getColumn3()) / rate;
				Double thucThu = MyDouble.parseDouble(r.getColumn2()) / rate;
				Double thucChi = MyDouble.parseDouble(r.getColumn4()) / rate;

				Double phaiThu = tongThu - thucThu;
				Double phaiChi = tongChi - thucChi;
				Double lai = thucThu - thucChi;
				if (conds.containsKey("store")) {
					OptionGroup o = GenericOptionModelManager.getInstance().getOptionGroup("accounting", "AccountingService", "type_invoice");
					String name = o.getOption(r.getNode()).getLabel();
					ReportBankEntity row = new ReportBankEntity(name, Double.toString(tongThu), Double.toString(tongChi),
					    Double.toString(thucThu), Double.toString(thucChi), Double.toString(phaiThu), Double.toString(phaiChi),
					    Double.toString(lai), index);
					row.setParent(r.getParent().toString());
					row.setType(Type.PARENT);
					pathParent = r.getParent().toString();
					if (tongThu != 0 || tongChi != 0 || thucThu != 0 || thucChi != 0) {
						if (r.getNode().toString().equals("Phòng khác") || r.getNode().toString().equals("Nhóm khác")) {
							if (tongThu != 0 || thucThu != 0 || tongChi != 0 || thucChi != 0)
								nodes.add(nodes.size(), row);
						} else {
							if (parent == null || row.getParent().indexOf(parent) == 0) {
								nodes.add(0, row);
							}
						}
					}
				} else {
					ReportBankEntity row = new ReportBankEntity(r.getNode(), Double.toString(tongThu), Double.toString(tongChi),
					    Double.toString(thucThu), Double.toString(thucChi), Double.toString(phaiThu), Double.toString(phaiChi),
					    Double.toString(lai), index);
					row.setParent(r.getParent().toString());
					row.setType(Type.PARENT);
					pathParent = r.getParent().toString();
					if (tongThu != 0 || tongChi != 0 || thucThu != 0 || thucChi != 0) {
						if (r.getNode().toString().equals("Phòng khác") || r.getNode().toString().equals("Nhóm khác")) {
							if (tongThu != 0 || thucThu != 0 || tongChi != 0 || thucChi != 0)
								nodes.add(nodes.size(), row);
						} else {
							if (parent == null || row.getParent().indexOf(parent) == 0) {
								nodes.add(0, row);
							}
						}
					}
				}

			}
			return nodes;
		}
	}

	private SQLSelectQuery query_Parent_DoanhThu_ChiPhi(ReportBankEntity ReportBankEntity, String parent, int index,
	    ActivityType activityType) {
		SQLSelectQuery rQuery = new SQLSelectQuery();
		String sql = "";
		if (isCheck == true)
			sql = "(Contributor.role = '" + Contributor.nhanVienPV + "' OR Contributor.role = '" + Contributor.thuNgan + "')";
		else
			sql = "Contributor.role = '" + Contributor.nhanVienPV + "'";
		String valueCode = "";
		if (conds.containsKey("partner1"))
			valueCode = "groupCustomerCode";
		else if (conds.containsKey("location1"))
			valueCode = "locationCode";
		else if (conds.containsKey("department") || conds.containsKey("cashier1"))
			valueCode = "departmentCode";
		else if (conds.containsKey("store"))
			valueCode = "type";
		else if (conds.containsKey("project"))
			valueCode = "projectCode";
		/** TAB nhân viên */
		// ĐÃ SỬA 25/04/2015
		if (conds.containsKey("cashier1")) {
			if (parent == null) {
				rQuery.table("InvoiceDetail " + "INNER JOIN Contributor ON Contributor.invoiceId = InvoiceDetail.id "
				    + "LEFT JOIN InvoiceTransaction ON InvoiceTransaction.invoiceId = InvoiceDetail.id");
				rQuery
				    .field(
				        "SUM(CASE WHEN (InvoiceDetail.startDate >= '"
				            + dateStart
				            + "' AND InvoiceDetail.startDate < '"
				            + dateEnd
				            + "' AND (InvoiceTransaction.transactions_ORDER = 0 OR InvoiceTransaction.transactions_ORDER IS NULL)) THEN InvoiceDetail.finalCharge ELSE 0 END) AS `finalCharge`",
				        "final");
				rQuery.field("SUM(CASE WHEN (InvoiceTransaction.transactionDate >= '" + dateStart
				    + "' AND InvoiceTransaction.transactionDate < '" + dateEnd
				    + "') THEN InvoiceTransaction.total ELSE 0 END) AS `totalTransaction`", "total");
				rQuery.cond("InvoiceDetail.ActivityType = '" + ActivityType.Receipt + "'");
				rQuery.cond(sql);
				rQuery.field("SUBSTRING_INDEX(Contributor.identifierValue, '/', 2) AS `path1`", "parent");
				rQuery
				    .cond("(LENGTH(SUBSTRING_INDEX(Contributor.identifierValue, '/', 2)) - LENGTH(REPLACE(SUBSTRING_INDEX(Contributor.identifierValue, '/', 2), '/', '')))  >= 1");
				rQuery.cond("InvoiceTransaction.type NOT LIKE '" + AccountingModelManager.typeSanXuat + "' ");
				rQuery.cond("InvoiceDetail.type NOT LIKE '" + AccountingModelManager.typeSanXuat + "' ");
				rQuery
				    .field(
				        "(SELECT accountGroup.label FROM accountGroup WHERE accountGroup.name = SUBSTRING_INDEX(`path1`, '/', -1)) AS `node`",
				        "node");
				rQuery.groupBy("`path1` ORDER BY `node`");
			} else {
				rQuery.table("InvoiceDetail " + "INNER JOIN Contributor ON Contributor.invoiceId = InvoiceDetail.id "
				    + "LEFT JOIN InvoiceTransaction ON InvoiceTransaction.invoiceId = InvoiceDetail.id");
				rQuery
				    .field(
				        "SUM(CASE WHEN (InvoiceDetail.startDate >= '"
				            + dateStart
				            + "' AND InvoiceDetail.startDate < '"
				            + dateEnd
				            + "' AND (InvoiceTransaction.transactions_ORDER = 0 OR InvoiceTransaction.transactions_ORDER IS NULL)) THEN InvoiceDetail.finalCharge ELSE 0 END) AS `finalCharge`",
				        "final");
				rQuery.field("SUM(CASE WHEN (InvoiceTransaction.transactionDate >= '" + dateStart
				    + "' AND InvoiceTransaction.transactionDate < '" + dateEnd
				    + "') THEN InvoiceTransaction.total ELSE 0 END) AS `totalTransaction`", "total");
				rQuery.field("SUBSTRING_INDEX(Contributor.identifierValue, '/', " + (index + 1) + ") AS `path1`", "parent");
				rQuery
				    .field(
				        "(SELECT accountGroup.label FROM accountGroup WHERE accountGroup.name = SUBSTRING_INDEX(`path1`, '/', -1)) AS `node`",
				        "node");
				rQuery.cond("InvoiceDetail.ActivityType = '" + ActivityType.Receipt + "'");
				rQuery.cond(sql);
				rQuery.cond("(LENGTH(SUBSTRING_INDEX(Contributor.identifierValue, '/', " + (index + 1)
				    + ")) - LENGTH(REPLACE(SUBSTRING_INDEX(Contributor.identifierValue, '/', " + (index + 1)
				    + "), '/', '')))  >= " + index + " ");
				rQuery.cond("SUBSTRING_INDEX(Contributor.identifierValue, '/', " + (index + 1) + ") LIKE '" + parent + "%'");
				rQuery.cond("InvoiceTransaction.type NOT LIKE '" + AccountingModelManager.typeSanXuat + "' ");
				rQuery.cond("InvoiceDetail.type NOT LIKE '" + AccountingModelManager.typeSanXuat + "' ");
				rQuery.groupBy("`path1` ORDER BY `node`");
			}
		}

		if (conds.containsKey("department") || conds.containsKey("partner1")) {
			// Trường hợp CLICK: ROOT
			if (parent == null) {
				rQuery
				    .table(
				        "(SELECT " + "SUBSTRING_INDEX(i." + valueCode
				            + ", '/', 2) AS `path1`, "
				            + "SUM(i.finalCharge * i.currencyRate) AS `finalCharge` "
				            + " FROM "
				            + " InvoiceDetail i "
				            + " WHERE "
				            + " i.ActivityType = '"
				            + activityType
				            + "' AND "
				            + " i.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND "
				            + // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            " i.startDate >= '" + dateStart + "' AND " + " i.startDate <= '" + dateEnd + "' AND "
				            + "(LENGTH(SUBSTRING_INDEX(i." + valueCode + ", '/', 2)) - LENGTH(REPLACE(SUBSTRING_INDEX(i."
				            + valueCode + ", '/', 2), '/', '')))  >= 1 " + "GROUP BY `path1`) AS `a` " + "JOIN " + "(SELECT "
				            + "SUBSTRING_INDEX(t." + valueCode + ", '/', 2) AS `path1`, "
				            + "SUM(t.total * t.currencyRate) AS `totalTransaction` " + "FROM " + "InvoiceTransaction t "
				            + "WHERE " + "t.ActivityType = '" + activityType + "' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND "
				            + // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            "t.transactionDate >= '" + dateStart + "' AND " + "t.transactionDate <= '" + dateEnd + "' AND "
				            + "(LENGTH(SUBSTRING_INDEX(t." + valueCode + ", '/', 2)) - LENGTH(REPLACE(SUBSTRING_INDEX(t."
				            + valueCode + ", '/', 2), '/', '')))  >= 1 "
				            + "GROUP BY `path1`) AS `b` ON b.path1 = a.path1 ORDER BY `node`")
				    .field("a.path1", "parent")
				    .field("(SELECT g.label FROM accountGroup g WHERE g.name = SUBSTRING_INDEX(a.path1, '/', -1)) AS `node`",
				        "node").field("COALESCE(a.finalCharge,0)", "final").field("COALESCE(b.totalTransaction,0)", "total");
			}
			// Trường hợp CLICK: PARENT
			else {
				rQuery
				    .table(
				        "(SELECT " + "SUBSTRING_INDEX(i."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + ") AS `path1`, "
				            + "SUM(i.finalCharge * i.currencyRate) AS `finalCharge` "
				            + "FROM "
				            + "InvoiceDetail i "
				            + "WHERE "
				            + "i.ActivityType = '"
				            + activityType
				            + "' AND "
				            + "i.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND "
				            + // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            "i.startDate >= '" + dateStart + "' AND " + "i.startDate <= '" + dateEnd + "' AND "
				            + "SUBSTRING_INDEX(i." + valueCode + ", '/', " + (index + 1) + ") LIKE '" + parent + "%' AND "
				            + "(LENGTH(SUBSTRING_INDEX(i." + valueCode + ", '/', " + (index + 1)
				            + ")) - LENGTH(REPLACE(SUBSTRING_INDEX(i." + valueCode + ", '/', " + (index + 1)
				            + "), '/', '')))  >= " + index + " " + "GROUP BY `path1`) AS `a` " + "JOIN " + "(SELECT "
				            + "SUBSTRING_INDEX(t." + valueCode + ", '/', " + (index + 1) + ") AS `path1`, "
				            + "SUM(t.total * t.currencyRate) AS `totalTransaction` " + "FROM " + "InvoiceTransaction t "
				            + "WHERE " + "t.ActivityType = '"
				            + activityType
				            + "' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND "
				            + // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            "t.transactionDate >= '" + dateStart + "' AND " + "t.transactionDate <= '" + dateEnd + "' AND "
				            + "SUBSTRING_INDEX(t." + valueCode + ", '/', " + (index + 1) + ") LIKE '" + parent + "%' AND "
				            + "(LENGTH(SUBSTRING_INDEX(t." + valueCode + ", '/', " + (index + 1)
				            + ")) - LENGTH(REPLACE(SUBSTRING_INDEX(t." + valueCode + ", '/', " + (index + 1)
				            + "), '/', '')))  >= " + index + " "
				            + "GROUP BY `path1`) AS `b` ON b.path1 = a.path1 ORDER BY `node`")
				    .field("a.path1", "parent")
				    .field("(SELECT g.label FROM accountGroup g WHERE g.name = SUBSTRING_INDEX(a.path1, '/', -1)) AS `node`",
				        "node").field("COALESCE(a.finalCharge,0)", "final").field("COALESCE(b.totalTransaction,0)", "total");
			}
		}

		/** TAB dự án */
		// ĐÃ SỬA
		if (conds.containsKey("project")) {
			// Trường hợp CLICK: ROOT
			if (parent == null) {
				rQuery
				    .table(
				        "(SELECT " + "SUBSTRING_INDEX(i." + valueCode
				            + ", '/', 2) AS `path1`, "
				            + "SUM(i.finalCharge * i.currencyRate) AS `finalCharge` "
				            + " FROM "
				            + " InvoiceDetail i "
				            + " WHERE "
				            + " i.ActivityType = '"
				            + activityType
				            + "' AND "
				            + " i.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND "
				            + // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            " i.startDate >= '" + dateStart + "' AND " + " i.startDate <= '" + dateEnd + "' AND "
				            + "(LENGTH(SUBSTRING_INDEX(i." + valueCode + ", '/', 2)) - LENGTH(REPLACE(SUBSTRING_INDEX(i."
				            + valueCode + ", '/', 2), '/', '')))  >= 1 " + "GROUP BY `path1`) AS `a` " + "JOIN " + "(SELECT "
				            + "SUBSTRING_INDEX(t." + valueCode + ", '/', 2) AS `path1`, "
				            + "SUM(t.total * t.currencyRate) AS `totalTransaction` " + "FROM " + "InvoiceTransaction t "
				            + "WHERE " + "t.ActivityType = '" + activityType + "' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND "
				            + // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            "t.transactionDate >= '" + dateStart + "' AND " + "t.transactionDate <= '" + dateEnd + "' AND "
				            + "(LENGTH(SUBSTRING_INDEX(t." + valueCode + ", '/', 2)) - LENGTH(REPLACE(SUBSTRING_INDEX(t."
				            + valueCode + ", '/', 2), '/', '')))  >= 1 "
				            + "GROUP BY `path1`) AS `b` ON b.path1 = a.path1 ORDER BY `node`")
				    .field("a.path1", "parent")
				    .field(
				        "(SELECT p.name FROM  restaurant_project p WHERE p.code = SUBSTRING_INDEX(a.path1, '/', -1)) AS `node`",
				        "node").field("COALESCE(a.finalCharge,0)", "final").field("COALESCE(b.totalTransaction,0)", "total");
			}
			// Trường hợp CLICK: PARENT
			else {
				rQuery
				    .table(
				        "(SELECT " + "SUBSTRING_INDEX(i."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + ") AS `path1`, "
				            + "SUM(i.finalCharge * i.currencyRate) AS `finalCharge` "
				            + "FROM "
				            + "InvoiceDetail i "
				            + "WHERE "
				            + "i.ActivityType = '"
				            + activityType
				            + "' AND "
				            + "i.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND "
				            + // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            "i.startDate >= '" + dateStart + "' AND " + "i.startDate <= '" + dateEnd + "' AND "
				            + "SUBSTRING_INDEX(i." + valueCode + ", '/', " + (index + 1) + ") LIKE '" + parent + "%' AND "
				            + "(LENGTH(SUBSTRING_INDEX(i." + valueCode + ", '/', " + (index + 1)
				            + ")) - LENGTH(REPLACE(SUBSTRING_INDEX(i." + valueCode + ", '/', " + (index + 1)
				            + "), '/', '')))  >= " + index + " " + "GROUP BY `path1`) AS `a` " + "JOIN " + "(SELECT "
				            + "SUBSTRING_INDEX(t." + valueCode + ", '/', " + (index + 1) + ") AS `path1`, "
				            + "SUM(t.total * t.currencyRate) AS `totalTransaction` " + "FROM " + "InvoiceTransaction t "
				            + "WHERE " + "t.ActivityType = '"
				            + activityType
				            + "' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND "
				            + // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            "t.transactionDate >= '" + dateStart + "' AND " + "t.transactionDate <= '" + dateEnd + "' AND "
				            + "SUBSTRING_INDEX(t." + valueCode + ", '/', " + (index + 1) + ") LIKE '" + parent + "%' AND "
				            + "(LENGTH(SUBSTRING_INDEX(t." + valueCode + ", '/', " + (index + 1)
				            + ")) - LENGTH(REPLACE(SUBSTRING_INDEX(t." + valueCode + ", '/', " + (index + 1)
				            + "), '/', '')))  >= " + index + " "
				            + "GROUP BY `path1`) AS `b` ON b.path1 = a.path1 ORDER BY `node`")
				    .field("a.path1", "parent")
				    .field(
				        "(SELECT p.name FROM restaurant_project p WHERE p.code = SUBSTRING_INDEX(a.path1, '/', -1)) AS `node`",
				        "node").field("COALESCE(a.finalCharge,0)", "final").field("COALESCE(b.totalTransaction,0)", "total");
			}
		}

		/** TAB khu vực */
		if (conds.containsKey("location1")) {
			if (parent == null) {
				rQuery
				    .table(
				        "(SELECT " + " i." + valueCode + " AS `path1`,"
				            + " SUM(i.finalCharge * i.currencyRate) as `finalCharge` FROM InvoiceDetail i "
				            + " WHERE "
				            + " i.ActivityType = '"
				            + activityType
				            + "' AND "
				            + " i.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + " i.startDate >= '" + dateStart + "' AND " + " i.startDate <= '" + dateEnd + "' "
				            + " GROUP BY `path1`) AS `a`" + " JOIN " + " (SELECT " + " t." + valueCode
				            + " as `path1` , SUM(t.total * t.currencyRate) AS `totalTransaction`"
				            + " FROM InvoiceTransaction t " + " WHERE t.ActivityType = '" + activityType + "' AND "
				            + " t.type NOT LIKE '" + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + " t.transactionDate >= '" + dateStart + "' AND " + " t.transactionDate <= '" + dateEnd + "' "
				            + " GROUP BY `path1` ) AS `b` ON b.path1 = a.path1 ORDER BY `node`")
				    .field("a.path1 as `path2`", "parent")
				    .field("(SELECT rl.name from restaurant_location rl where rl.code = `path2`) as `node`", "node")
				    .field("COALESCE(a.finalCharge,0)", "final").field("COALESCE(b.totalTransaction,0)", "total");
			} else {
				rQuery
				    .table(
				        "(SELECT " + " i." + valueCode + " as `path1`, "
				            + " SUM(i.finalCharge * i.currencyRate) as `finalCharge` " + " FROM InvoiceDetail i "
				            + " WHERE "
				            + " i.ActivityType = '"
				            + activityType
				            + "' AND "
				            + " i.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + " i.startDate >= '" + dateStart + "' AND " + " i.startDate <= '" + dateEnd + "' "
				            + " GROUP BY `path1`) AS `a`" + " JOIN " + " (SELECT " + " t." + valueCode + " as `path1` , "
				            + " SUM(t.total * t.currencyRate) AS `totalTransaction`" + " FROM InvoiceTransaction t "
				            + " WHERE " + " t.ActivityType = '" + activityType + "' AND " + " t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + " t.transactionDate >= '" + dateStart + "' AND " + " t.transactionDate <= '" + dateEnd + "' "
				            + " GROUP BY `path1` ) AS `b` ON b.path1 = a.path1 ORDER BY `node`")
				    .field("a.path1 as `path2`", "parent")
				    .field("(SELECT rl.name from restaurant_location rl where rl.code = `path2`) as `node`", "node")
				    .field("COALESCE(a.finalCharge,0)", "final").field("COALESCE(b.totalTransaction,0)", "total");
			}
		}

		/** TAB cửa hàng */
		if (conds.containsKey("store")) {
			if (parent == null) {
				rQuery
				    .table(
				        "(SELECT " + " i." + valueCode + " AS `path1`,"
				            + " SUM(i.finalCharge * i.currencyRate) as `finalCharge` " + " FROM InvoiceDetail i "
				            + " WHERE "
				            + " i.ActivityType = '"
				            + activityType
				            + "' AND "
				            + " i.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + " i.startDate >= '" + dateStart + "' AND " + " i.startDate <= '" + dateEnd + "' "
				            + " GROUP BY `path1`) AS `a`" + " JOIN " + " (SELECT " + " t." + valueCode + " as `path1` , "
				            + " SUM(t.total * t.currencyRate) AS `totalTransaction`" + " FROM InvoiceTransaction t "
				            + " WHERE " + " t.ActivityType = '" + activityType + "' AND " + " t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + " t.transactionDate >= '" + dateStart + "' AND " + " t.transactionDate <= '" + dateEnd + "' "
				            + " GROUP BY `path1` ) AS `b` ON b.path1 = a.path1 ORDER BY `node`").field("a.path1", "parent")
				    .field("b.path1 as `node`", "node").field("COALESCE(a.finalCharge,0)", "final")
				    .field("COALESCE(b.totalTransaction,0)", "total");
			} else {
				rQuery
				    .table(
				        "(SELECT " + " i." + valueCode + " as `path1`,"
				            + " SUM(i.finalCharge * i.currencyRate) as `finalCharge` " + " FROM InvoiceDetail i "
				            + " WHERE "
				            + " i.ActivityType = '"
				            + activityType
				            + "' AND "
				            + " i.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + " i.startDate >= '" + dateStart + "' AND " + " i.startDate <= '" + dateEnd + "' "
				            + " GROUP BY `path1`) AS `a`" + " JOIN " + " (SELECT " + " t." + valueCode + " as `path1` , "
				            + " SUM(t.total * t.currencyRate) AS `totalTransaction`" + " FROM InvoiceTransaction t "
				            + " WHERE " + " t.ActivityType = '" + activityType + "' AND " + " t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + " t.transactionDate >= '" + dateStart + "' AND " + " t.transactionDate <= '" + dateEnd + "' "
				            + " GROUP BY `path1` ) AS `b` ON b.path1 = a.path1 ORDER BY `node`").field("a.path1", "parent")
				    .field("b.path1 as `node`", "node").field("COALESCE(a.finalCharge,0)", "final")
				    .field("COALESCE(b.totalTransaction,0)", "total");
			}
		}

		/** TAB sản phẩm */
		if (conds.containsKey("product1")) {
			if (parent == null) {
				// rQuery.table("InvoiceItem i " +
				// "INNER JOIN product p ON p.code = i.productCode " +
				// "INNER JOIN product_productTag t ON t.productId = p.Id " +
				// "INNER JOIN warehouse_productTag w ON w.id = t.productTagId")
				// .field("SUBSTRING_INDEX(w.tag, ':', " + (index) + ") AS `path1`",
				// "parent")
				// .field("w.label AS `node`", "node")
				// .field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `final`",
				// "final")
				// .cond("i.activityType  = '" + activityType +
				// "' AND i.productCode IS NOT NULL")
				// .cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
				// .cond("i.startDate >= '" + dateStart + "' AND i.startDate  <= '" +
				// dateEnd + "' ")
				// .groupBy("`path1` ORDER BY `node`");
				rQuery
				    .table(
				        "InvoiceItem i JOIN Product p ON p.code = i.productCode LEFT JOIN ProductAttribute a ON a.productId = p.id")
				    .field("SUBSTRING_INDEX(a.value, ':', " + (index) + ") AS `parent`", "parent")
				    .field(
				        "(SELECT w.label FROM warehouse_productTag w WHERE w.code = SUBSTRING_INDEX(`parent`, ':', -1)) AS `node`",
				        "node").field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `final`", "final")
				    .cond("i.activityType  = '" + activityType + "' AND i.productCode IS NOT NULL")
				    .cond("i.type NOT LIKE '" + AccountingModelManager.typeSanXuat + "'")
				    .cond("i.startDate >= '" + dateStart + "' AND i.startDate  <= '" + dateEnd + "' ").groupBy("`parent`");
			} else {
				rQuery
				    .table(
				        "InvoiceItem i " + "INNER JOIN product p ON p.code = i.productCode "
				            + "INNER JOIN product_productTag t ON t.productId = p.Id "
				            + "INNER JOIN warehouse_productTag w ON w.id = t.productTagId")
				    .field("SUBSTRING_INDEX(w.tag, ':', " + (index + 1) + ") AS `path1`", "parent")
				    .field("w.label AS `node`", "node")
				    .field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `final`", "final")
				    .cond("i.type NOT LIKE '" + AccountingModelManager.typeSanXuat + "'")
				    .cond("i.activityType  = '" + activityType + "' AND i.productCode IS NOT NULL")
				    .cond("i.startDate >= '" + dateStart + "' AND i.startDate  <= '" + dateEnd + "' ")
				    .groupBy("`path1` ORDER BY `node`");
				if (parent != null) {
					rQuery.cond("SUBSTRING_INDEX(w.tag, ':', " + (index - 1) + ") = '" + parent + "'").cond(
					    "(LENGTH(SUBSTRING_INDEX(w.tag, ':', " + index + ")) - LENGTH(REPLACE(SUBSTRING_INDEX(w.tag, ':', "
					        + index + "), ':', '')))  >= " + (index - 1));
				}
			}
		}

		/* TAB dự án */
		if (conds.containsKey("project"))
			if (parent == null) {
				rQuery.table();
			}

		return rQuery;
	}

	private SQLSelectQuery query_Parent_ThuChiLai(String parent, int index, boolean thuChiCongNo) {
		SQLSelectQuery rQuery = new SQLSelectQuery();
		String sql = "";
		if (isCheck == true) {
			sql = "(Contributor.role = '" + Contributor.nhanVienPV + "' OR Contributor.role = '" + Contributor.thuNgan + "')";
		} else
			sql = "Contributor.role = '" + Contributor.nhanVienPV + "'";
		String valueCode = "";
		if (conds.containsKey("partner1"))
			valueCode = "groupCustomerCode";
		else if (conds.containsKey("location1"))
			valueCode = "locationCode";
		else if (conds.containsKey("department") || conds.containsKey("cashier1"))
			valueCode = "departmentCode";
		else if (conds.containsKey("store"))
			valueCode = "type";
		else if (conds.containsKey("project"))
			valueCode = "projectCode";

		/** TAB nhân viên */
		if (conds.containsKey("cashier1")) {
			// Trường hợp CLICK: ROOT
			if (parent == null) {
				rQuery.table("InvoiceDetail " + "INNER JOIN Contributor ON Contributor.invoiceId = InvoiceDetail.id "
				    + "LEFT JOIN InvoiceTransaction ON InvoiceTransaction.invoiceId = InvoiceDetail.id");
				rQuery.field("SUM(CASE WHEN (InvoiceTransaction.transactionDate >= '" + dateStart
				    + "' AND InvoiceTransaction.transactionDate < '" + dateEnd + "' AND InvoiceDetail.activityType = '"
				    + ActivityType.Receipt + "') THEN InvoiceTransaction.total ELSE 0 END) AS `finalCharge`", "final");
				rQuery.field("SUM(CASE WHEN (InvoiceTransaction.transactionDate >= '" + dateStart
				    + "' AND InvoiceTransaction.transactionDate < '" + dateEnd + "' AND InvoiceDetail.activityType = '"
				    + ActivityType.Payment + "') THEN InvoiceTransaction.total ELSE 0 END) AS `totalTransaction`", "total");
				rQuery.field("SUBSTRING_INDEX(Contributor.identifierValue, '/', 2) AS `path1`", "parent");
				rQuery
				    .cond("(LENGTH(SUBSTRING_INDEX(Contributor.identifierValue, '/', 2)) - LENGTH(REPLACE(SUBSTRING_INDEX(Contributor.identifierValue, '/', 2), '/', '')))  >= 1");
				rQuery.cond("InvoiceTransaction.type NOT LIKE '" + AccountingModelManager.typeSanXuat + "' ");
				rQuery.cond("InvoiceDetail.type NOT LIKE '" + AccountingModelManager.typeSanXuat + "' ");
				rQuery
				    .field(
				        "(SELECT accountGroup.label FROM accountGroup WHERE accountGroup.name = SUBSTRING_INDEX(`path1`, '/', -1)) AS `node`",
				        "node");
				rQuery.groupBy("path1 ORDER BY `node`");
			}
			// Trường hợp CLICK: PARENT
			else {
				rQuery.table("InvoiceDetail " + "INNER JOIN Contributor ON Contributor.invoiceId = InvoiceDetail.id "
				    + "LEFT JOIN InvoiceTransaction ON InvoiceTransaction.invoiceId = InvoiceDetail.id");
				rQuery.field("SUM(CASE WHEN (InvoiceTransaction.transactionDate >= '" + dateStart
				    + "' AND InvoiceTransaction.transactionDate < '" + dateEnd + "' AND InvoiceDetail.activityType = '"
				    + ActivityType.Receipt + "') THEN InvoiceTransaction.total ELSE 0 END) AS `finalCharge`", "final");
				rQuery.field("SUM(CASE WHEN (InvoiceTransaction.transactionDate >= '" + dateStart
				    + "' AND InvoiceTransaction.transactionDate < '" + dateEnd + "' AND InvoiceDetail.activityType = '"
				    + ActivityType.Payment + "') THEN InvoiceTransaction.total ELSE 0 END) AS `totalTransaction`", "total");
				rQuery.field("SUBSTRING_INDEX(Contributor.identifierValue, '/', " + (index + 1) + ") AS `path1`", "parent");
				rQuery.cond("(LENGTH(SUBSTRING_INDEX(Contributor.identifierValue, '/', " + (index + 1)
				    + ")) - LENGTH(REPLACE(SUBSTRING_INDEX(Contributor.identifierValue, '/', " + (index + 1)
				    + "), '/', '')))  >= " + index + " ");
				rQuery.cond("SUBSTRING_INDEX(Contributor.identifierValue, '/', " + (index + 1) + ") LIKE '" + parent + "%'");
				rQuery.cond("InvoiceTransaction.type NOT LIKE '" + AccountingModelManager.typeSanXuat + "' ");
				rQuery.cond("InvoiceDetail.type NOT LIKE '" + AccountingModelManager.typeSanXuat + "' ");
				rQuery
				    .field(
				        "(SELECT accountGroup.label FROM accountGroup WHERE accountGroup.name = SUBSTRING_INDEX(`path1`, '/', -1)) AS `node`",
				        "node");
				rQuery.groupBy("path1 ORDER BY `node`");
			}
		}
		/** TAB phòng ban || khách hàng */
		if (conds.containsKey("department") || conds.containsKey("partner1")) {
			// Trường hợp CLICK: ROOT
			if (parent == null) {
				rQuery
				    .table(
				        "((SELECT " + "a.path1 AS `path1`, " + "b.final AS `final`, " + "b.path1 AS `path2`, "
				            + "a.total AS `total` " + "FROM " + "((SELECT " + "SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', 2) AS `path1`, "
				            + "SUM(t.total * t.currencyRate) AS `total` "
				            + "FROM InvoiceTransaction t "
				            + "WHERE t.ActivityType = 'Payment' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "' AND "
				            + "(LENGTH(SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', 2)) - LENGTH(REPLACE(SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', 2), '/', '')))  >= 1 "
				            + "GROUP BY `path1`) AS `a` "
				            + "RIGHT JOIN "
				            + "(SELECT "
				            + "SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', 2) AS `path1`, "
				            + "SUM(t.total * t.currencyRate) AS `final` "
				            + "FROM InvoiceTransaction t "
				            + "WHERE t.ActivityType = 'Receipt' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "' AND "
				            + "(LENGTH(SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', 2)) - LENGTH(REPLACE(SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', 2), '/', '')))  >= 1 GROUP BY `path1`) AS `b` ON a.path1 = b.path1)) "
				            + "UNION "
				            + "(SELECT "
				            + "a.path1 AS `path1`, "
				            + "b.final AS `final`, "
				            + "b.path1 AS `path2`, "
				            + "a.total AS `total` "
				            + "FROM "
				            + "((SELECT "
				            + "SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', 2) AS `path1`, "
				            + "SUM(t.total * t.currencyRate) AS `total` "
				            + "FROM InvoiceTransaction t "
				            + "WHERE t.ActivityType = 'Payment' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "' AND "
				            + "(LENGTH(SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', 2)) - LENGTH(REPLACE(SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', 2), '/', '')))  >= 1 "
				            + "GROUP BY `path1`) AS `a` "
				            + "LEFT JOIN "
				            + "(SELECT "
				            + "SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', 2) AS `path1`, "
				            + "SUM(t.total * t.currencyRate) AS `final` "
				            + "FROM InvoiceTransaction t "
				            + "WHERE t.ActivityType = 'Receipt' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "' AND "
				            + "(LENGTH(SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', 2)) - LENGTH(REPLACE(SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', 2), '/', '')))  >= 1 GROUP BY `path1`) AS `b` ON a.path1 = b.path1))) AS `e` ORDER BY `node`")
				    .field("(CASE WHEN (e.path1 IS NULL) THEN e.path2 ELSE e.path1 END) AS `path3`", "parent")
				    .field("(SELECT g.label FROM accountGroup g WHERE g.name = SUBSTRING_INDEX(path3, '/', -1)) AS `node`",
				        "node").field("COALESCE(e.final, 0) AS `finalRec`", "final")
				    .field("COALESCE(e.total, 0) AS `finalPay`", "total");
			}
			// Trường hợp CLICK: PARENT
			else {
				rQuery
				    .table(
				        "((SELECT " + "a.path1 AS `path1`, " + "b.final AS `final`, " + "b.path1 AS `path2`, "
				            + "a.total AS `total` " + "FROM " + "((SELECT " + "SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + ") AS `path1`, "
				            + "SUM(t.total * t.currencyRate) AS `total` "
				            + "FROM InvoiceTransaction t "
				            + "WHERE t.ActivityType = 'Payment' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "' AND "
				            + "SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + ") LIKE '"
				            + parent
				            + "%' AND "
				            + "(LENGTH(SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + ")) - LENGTH(REPLACE(SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + "), '/', '')))  >= "
				            + index
				            + " "
				            + "GROUP BY `path1`) AS `a` "
				            + "RIGHT JOIN "
				            + "(SELECT SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + ") AS `path1`, "
				            + "SUM(t.total * t.currencyRate) AS `final` "
				            + "FROM InvoiceTransaction t "
				            + "WHERE t.ActivityType = 'Receipt' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "' AND "
				            + "SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + ") LIKE '"
				            + parent
				            + "%' AND "
				            + "(LENGTH(SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + ")) - LENGTH(REPLACE(SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + "), '/', '')))  >= "
				            + index
				            + " "
				            + "GROUP BY `path1`) AS `b` ON b.path1 = a.path1)) "
				            + "UNION "
				            + "(SELECT "
				            + "a.path1 AS `path1`, "
				            + "b.final AS `final`, "
				            + "b.path1 AS `path2`, "
				            + "a.total AS `total` "
				            + "FROM "
				            + "((SELECT "
				            + "SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + ") AS `path1`, "
				            + "SUM(t.total * t.currencyRate) AS `total` "
				            + "FROM InvoiceTransaction t "
				            + "WHERE t.ActivityType = 'Payment' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "' AND "
				            + "SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + ") LIKE '"
				            + parent
				            + "%' AND "
				            + "(LENGTH(SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + ")) - LENGTH(REPLACE(SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + "), '/', '')))  >= "
				            + index
				            + " "
				            + "GROUP BY `path1`) AS `a` "
				            + "LEFT JOIN "
				            + "(SELECT SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + ") AS `path1`, "
				            + "SUM(t.total * t.currencyRate) AS `final` "
				            + "FROM InvoiceTransaction t "
				            + "WHERE t.ActivityType = 'Receipt' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "' AND "
				            + "SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + ") LIKE '"
				            + parent
				            + "%' AND "
				            + "(LENGTH(SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + ")) - LENGTH(REPLACE(SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + "), '/', '')))  >= "
				            + index
				            + " "
				            + "GROUP BY `path1`) AS `b` ON b.path1 = a.path1))) AS `e` ORDER BY `node`")
				    .field("(CASE WHEN (e.path1 IS NULL) THEN e.path2 ELSE e.path1 END) AS `path3`", "parent")
				    .field("(SELECT p.label FROM accountGroup p WHERE p.name = SUBSTRING_INDEX(path3, '/', -1)) AS `node`",
				        "node").field("COALESCE(e.final, 0) AS `finalRec`", "final")
				    .field("COALESCE(e.total, 0) AS `finalPay`", "total");
			}
		}

		/** TAB dự án */
		if (conds.containsKey("project")) {
			// Trường hợp CLICK: ROOT
			if (parent == null) {
				rQuery
				    .table(
				        "((SELECT " + "a.path1 AS `path1`, " + "b.final AS `final`, " + "b.path1 AS `path2`, "
				            + "a.total AS `total` " + "FROM " + "((SELECT " + "SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', 2) AS `path1`, "
				            + "SUM(t.total * t.currencyRate) AS `total` "
				            + "FROM InvoiceTransaction t "
				            + "WHERE t.ActivityType = 'Payment' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "' AND "
				            + "(LENGTH(SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', 2)) - LENGTH(REPLACE(SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', 2), '/', '')))  >= 1 "
				            + "GROUP BY `path1`) AS `a` "
				            + "RIGHT JOIN "
				            + "(SELECT "
				            + "SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', 2) AS `path1`, "
				            + "SUM(t.total * t.currencyRate) AS `final` "
				            + "FROM InvoiceTransaction t "
				            + "WHERE t.ActivityType = 'Receipt' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "' AND "
				            + "(LENGTH(SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', 2)) - LENGTH(REPLACE(SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', 2), '/', '')))  >= 1 GROUP BY `path1`) AS `b` ON a.path1 = b.path1)) "
				            + "UNION "
				            + "(SELECT "
				            + "a.path1 AS `path1`, "
				            + "b.final AS `final`, "
				            + "b.path1 AS `path2`, "
				            + "a.total AS `total` "
				            + "FROM "
				            + "((SELECT "
				            + "SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', 2) AS `path1`, "
				            + "SUM(t.total * t.currencyRate) AS `total` "
				            + "FROM InvoiceTransaction t "
				            + "WHERE t.ActivityType = 'Payment' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "' AND "
				            + "(LENGTH(SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', 2)) - LENGTH(REPLACE(SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', 2), '/', '')))  >= 1 "
				            + "GROUP BY `path1`) AS `a` "
				            + "LEFT JOIN "
				            + "(SELECT "
				            + "SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', 2) AS `path1`, "
				            + "SUM(t.total * t.currencyRate) AS `final` "
				            + "FROM InvoiceTransaction t "
				            + "WHERE t.ActivityType = 'Receipt' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "' AND "
				            + "(LENGTH(SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', 2)) - LENGTH(REPLACE(SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', 2), '/', '')))  >= 1 GROUP BY `path1`) AS `b` ON a.path1 = b.path1))) AS `e` ORDER BY `node`")
				    .field("(CASE WHEN (e.path1 IS NULL) THEN e.path2 ELSE e.path1 END) AS `path3`", "parent")
				    .field(
				        "(SELECT p.name FROM  restaurant_project p WHERE p.code = SUBSTRING_INDEX(path3, '/', -1)) AS `node`",
				        "node").field("COALESCE(e.final, 0) AS `finalRec`", "final")
				    .field("COALESCE(e.total, 0) AS `finalPay`", "total");
			}
			// Trường hợp CLICK: PARENT
			else {
				rQuery
				    .table(
				        "((SELECT " + "a.path1 AS `path1`, " + "b.final AS `final`, " + "b.path1 AS `path2`, "
				            + "a.total AS `total` " + "FROM " + "((SELECT " + "SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + ") AS `path1`, "
				            + "SUM(t.total * t.currencyRate) AS `total` "
				            + "FROM InvoiceTransaction t "
				            + "WHERE t.ActivityType = 'Payment' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "' AND "
				            + "SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + ") LIKE '"
				            + parent
				            + "%' AND "
				            + "(LENGTH(SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + ")) - LENGTH(REPLACE(SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + "), '/', '')))  >= "
				            + index
				            + " "
				            + "GROUP BY `path1`) AS `a` "
				            + "RIGHT JOIN "
				            + "(SELECT SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + ") AS `path1`, "
				            + "SUM(t.total * t.currencyRate) AS `final` "
				            + "FROM InvoiceTransaction t "
				            + "WHERE t.ActivityType = 'Receipt' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "' AND "
				            + "SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + ") LIKE '"
				            + parent
				            + "%' AND "
				            + "(LENGTH(SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + ")) - LENGTH(REPLACE(SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + "), '/', '')))  >= "
				            + index
				            + " "
				            + "GROUP BY `path1`) AS `b` ON b.path1 = a.path1)) "
				            + "UNION "
				            + "(SELECT "
				            + "a.path1 AS `path1`, "
				            + "b.final AS `final`, "
				            + "b.path1 AS `path2`, "
				            + "a.total AS `total` "
				            + "FROM "
				            + "((SELECT "
				            + "SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + ") AS `path1`, "
				            + "SUM(t.total * t.currencyRate) AS `total` "
				            + "FROM InvoiceTransaction t "
				            + "WHERE t.ActivityType = 'Payment' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "' AND "
				            + "SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + ") LIKE '"
				            + parent
				            + "%' AND "
				            + "(LENGTH(SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + ")) - LENGTH(REPLACE(SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + "), '/', '')))  >= "
				            + index
				            + " "
				            + "GROUP BY `path1`) AS `a` "
				            + "LEFT JOIN "
				            + "(SELECT SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + ") AS `path1`, "
				            + "SUM(t.total * t.currencyRate) AS `final` "
				            + "FROM InvoiceTransaction t "
				            + "WHERE t.ActivityType = 'Receipt' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "' AND "
				            + "SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + ") LIKE '"
				            + parent
				            + "%' AND "
				            + "(LENGTH(SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + ")) - LENGTH(REPLACE(SUBSTRING_INDEX(t."
				            + valueCode
				            + ", '/', "
				            + (index + 1)
				            + "), '/', '')))  >= "
				            + index
				            + " "
				            + "GROUP BY `path1`) AS `b` ON b.path1 = a.path1))) AS `e` ORDER BY `node`")
				    .field("(CASE WHEN (e.path1 IS NULL) THEN e.path2 ELSE e.path1 END) AS `path3`", "parent")
				    .field(
				        "(SELECT p.name FROM  restaurant_project p WHERE p.code = SUBSTRING_INDEX(path3, '/', -1)) AS `node`",
				        "node").field("COALESCE(e.final, 0) AS `finalRec`", "final")
				    .field("COALESCE(e.total, 0) AS `finalPay`", "total");
			}
		}

		/** TAB sản phẩm */
		if (conds.containsKey("product1")) {
			// rQuery
			// .table("InvoiceItem i JOIN Product p ON p.code = i.productCode LEFT JOIN ProductAttribute a ON a.productId = p.id")
			// .field("SUBSTRING_INDEX(a.value, ':', " + (index) + ") AS `parent`",
			// "parent")
			// .field("(SELECT w.label FROM warehouse_productTag w WHERE w.code = SUBSTRING_INDEX(`parent`, ':', -1)) AS `node`"
			// , "node")
			// .field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `final`",
			// "final")
			// .cond("i.activityType  = '" + activityType +
			// "' AND i.productCode IS NOT NULL")
			// .cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
			// .cond("i.startDate >= '" + dateStart + "' AND i.startDate  <= '" +
			// dateEnd + "' ")
			// .groupBy("`parent`");
			if (parent == null) {
				rQuery
				    .table(
				        "(SELECT "
				            + "SUBSTRING_INDEX(a.value, ':', 1) AS `path1`, "
				            + "(SELECT w.label FROM warehouse_productTag w WHERE w.code = SUBSTRING_INDEX(`path1`, ':', -1)) AS `node`, "
				            + "COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `totalTransaction` "
				            + "FROM "
				            + "InvoiceItem i JOIN Product p ON p.code = i.productCode LEFT JOIN ProductAttribute a ON a.productId = p.id "
				            + "WHERE " + "i.activityType  = 'Payment' AND i.productCode IS NOT NULL " + "AND i.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' "
				            + "AND i.startDate >= '"
				            + dateStart
				            + "' "
				            + "AND i.startDate <= '"
				            + dateEnd
				            + "' "
				            + "GROUP BY `path1`) AS `a` "
				            + "RIGHT JOIN "
				            + "(SELECT "
				            + "SUBSTRING_INDEX(a.value, ':', 1) AS `path1`, "
				            + "(SELECT w.label FROM warehouse_productTag w WHERE w.code = SUBSTRING_INDEX(`path1`, ':', -1)) AS `node`, "
				            + "COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `totalTransaction` "
				            + "FROM "
				            + "InvoiceItem i JOIN Product p ON p.code = i.productCode LEFT JOIN ProductAttribute a ON a.productId = p.id "
				            + "WHERE "
				            + "i.activityType  = 'Receipt' AND i.productCode IS NOT NULL "
				            + "AND i.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' "
				            + "AND i.startDate >= '"
				            + dateStart
				            + "' "
				            + "AND i.startDate <= '"
				            + dateEnd
				            + "' "
				            + "GROUP BY `path1`) AS `b` ON b.path1 = a.path1 ORDER BY `node`")
				    .field("(SELECT CASE when a.node is null then b.node else a.node end) as `node`", "node")
				    .field("(SELECT CASE when a.path1 is null then b.path1 else a.path1 end) AS `path1`", "parent")
				    .field("COALESCE(a.totalTransaction,0) AS `totalPay`", "total")
				    .field("COALESCE(b.totalTransaction,0) AS `totalRec`", "final");
			} else {
				rQuery
				    .table(
				        "(SELECT " + "SUBSTRING_INDEX(w.tag, ':', " + (index + 1) + ") AS `path1`, " + "w.label AS `node`, "
				            + "SUM(i.finalCharge * i.currencyRate) AS `totalTransaction` " + "FROM InvoiceItem i "
				            + "INNER JOIN product p ON p.code = i.productCode "
				            + "INNER JOIN product_productTag t ON t.productId = p.Id "
				            + "INNER JOIN warehouse_productTag w ON w.id = t.productTagId "
				            + "WHERE i.ActivityType = 'Payment' " + "AND i.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat + "' " + "AND i.startDate >= '" + dateStart + "' "
				            + "AND i.startDate <= '" + dateEnd + "' " + "GROUP BY `path1`) AS `a` " + "RIGHT JOIN "
				            + "(SELECT " + "SUBSTRING_INDEX(w.tag, ':', " + (index + 1) + ") AS `path1`, "
				            + "w.label AS `node`, " + "SUM(i.finalCharge * i.currencyRate) AS `totalTransaction` "
				            + "FROM InvoiceItem i " + "INNER JOIN product p ON p.code = i.productCode "
				            + "INNER JOIN product_productTag t ON t.productId = p.Id "
				            + "INNER JOIN warehouse_productTag w ON w.id = t.productTagId "
				            + "WHERE i.ActivityType = 'Receipt' " + "AND i.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat + "' " + "AND i.startDate >= '" + dateStart + "' "
				            + "AND i.startDate <= '" + dateEnd + "' " + "AND SUBSTRING_INDEX(w.tag, ':', " + (index - 1)
				            + ") = '" + parent + "' " + "AND (LENGTH(SUBSTRING_INDEX(w.tag, ':', " + index
				            + ")) - LENGTH(REPLACE(SUBSTRING_INDEX(w.tag, ':', " + index + "), ':', '')))  >= " + (index - 1)
				            + " GROUP BY `path1`) AS `b` ON b.path1 = a.path1 ORDER BY `node`")
				    .field("(SELECT CASE when a.node is null then b.node else a.node end) as `node`", "node")
				    .field("(SELECT CASE when a.path1 is null then b.path1 else a.path1 end) AS `path1`", "parent")
				    .field("COALESCE(a.totalTransaction,0) AS `totalPay`", "total")
				    .field("COALESCE(b.totalTransaction,0) AS `totalRec`", "final");
			}
		}

		/** TAB khu vực */
		if (conds.containsKey("location1")) {
			if (parent == null) {
				rQuery
				    .table(
				        "((SELECT " + "a.path1 AS `path1`, " + "b.final AS `final`, " + "b.path1 AS `path2`, "
				            + "a.total AS `total` " + "FROM " + "((SELECT t."
				            + valueCode
				            + " AS `path1`,"
				            + "SUM(t.total * t.currencyRate) AS `total` "
				            + "FROM InvoiceTransaction t "
				            + "WHERE t.ActivityType = 'Payment' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "' "
				            + " GROUP BY `path1`) AS `a` "
				            + "RIGHT JOIN "
				            + "(SELECT t."
				            + valueCode
				            + " as `path1` , "
				            + "SUM(t.total * t.currencyRate) AS `final` "
				            + "FROM InvoiceTransaction t "
				            + "WHERE t.ActivityType = 'Receipt' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "' AND t."
				            + valueCode
				            + " IS NOT NULL "
				            + " GROUP BY `path1`) AS `b` ON a.path1 = b.path1)) "
				            + "UNION "
				            + "(SELECT "
				            + "a.path1 AS `path1`, "
				            + "b.final AS `final`, "
				            + "b.path1 AS `path2`, "
				            + "a.total AS `total` "
				            + "FROM "
				            + "((SELECT t."
				            + valueCode
				            + " AS `path1`,"
				            + "SUM(t.total * t.currencyRate) AS `total` "
				            + "FROM InvoiceTransaction t "
				            + "WHERE t.ActivityType = 'Payment' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "' "
				            + "GROUP BY `path1`) AS `a` "
				            + "LEFT JOIN "
				            + "(SELECT t."
				            + valueCode
				            + " as `path1` , "
				            + "SUM(t.total * t.currencyRate) AS `final` "
				            + "FROM InvoiceTransaction t "
				            + "WHERE t.ActivityType = 'Receipt' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "' AND t."
				            + valueCode
				            + " IS NOT NULL "
				            + " GROUP BY `path1`) AS `b` ON a.path1 = b.path1))) AS `e` ORDER BY `node`")
				    .field("(SELECT CASE when (e.path1 is null) then e.path2 else e.path1 end) as `path3`", "parent")
				    .field("(SELECT rl.name from restaurant_location rl where rl.code = `path3`) as `node`", "node")
				    .field("COALESCE(e.final, 0) AS `totalReceipt`", "final")
				    .field("COALESCE(e.total, 0) AS `totalPayment`", "total");
			} else {
				rQuery
				    .table(
				        "((SELECT " + "a.path1 AS `path1`, " + "b.final AS `final`, " + "b.path1 AS `path2`, "
				            + "a.total AS `total` " + "FROM " + "((SELECT " + " t.tableCode AS `path1`,"
				            + "SUM(t.total * t.currencyRate) AS `total` " + "FROM InvoiceTransaction t "
				            + "WHERE t.ActivityType = 'Payment' AND " + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "' "
				            + "GROUP BY `path1`) AS `a` "
				            + "RIGHT JOIN "
				            + "(SELECT t.tableCode as `path1` ,"
				            + "SUM(t.total * t.currencyRate) AS `final` "
				            + "FROM InvoiceTransaction t "
				            + "WHERE t.ActivityType = 'Receipt' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "'  AND t."
				            + valueCode
				            + " IS NOT NULL "
				            + "GROUP BY `path1`) AS `b` ON a.path1 = b.path1)) "
				            + "UNION "
				            + "(SELECT "
				            + "a.node AS `node1`, "
				            + "b.final AS `final`, "
				            + "b.node AS `node2`, "
				            + "a.total AS `total` "
				            + "FROM "
				            + "((SELECT "
				            + " t.tableCode AS `path1`,"
				            + "SUM(t.total * t.currencyRate) AS `total` "
				            + "FROM InvoiceTransaction t "
				            + "WHERE t.ActivityType = 'Payment' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "' "
				            + "GROUP BY `path1`) AS `a` "
				            + "LEFT JOIN "
				            + "(SELECT t.tableCode as `path1` ,"
				            + "SUM(t.total * t.currencyRate) AS `final` "
				            + "FROM InvoiceTransaction t "
				            + "WHERE t.ActivityType = 'Receipt' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "'  AND t."
				            + valueCode
				            + " IS NOT NULL "
				            + "GROUP BY `path1`) AS `b` ON a.path1 = b.path1))) AS `e` ORDER BY `node`")
				    .field("(SELECT CASE when (e.path1 is null) then e.path2 else e.path1 end) as `path3`", "parent")
				    .field("(SELECT rt.name from restaurant_table rt where rt.code = `path3`) as `node`", "node")
				    .field("COALESCE(e.final, 0) AS `totalReceipt`", "final")
				    .field("COALESCE(e.total, 0) AS `totalPayment`", "total");
			}
		}

		/** TAB cửa hàng */
		if (conds.containsKey("store")) {
			System.out.println(parent);
			if (parent == null) {
				rQuery
				    .table(
				        "((SELECT " + "a.path1 AS `path1`, " + "b.final AS `final`, " + "b.path1 AS `path2`, "
				            + "a.total AS `total` " + "FROM " + "((SELECT t."
				            + valueCode
				            + " AS `path1`,"
				            + "SUM(t.total * t.currencyRate) AS `total` "
				            + "FROM InvoiceTransaction t "
				            + "WHERE t.ActivityType = 'Payment' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "' "
				            + "GROUP BY `path1`) AS `a` "
				            + "RIGHT JOIN "
				            + "(SELECT t."
				            + valueCode
				            + " as `path1` , "
				            + "SUM(t.total * t.currencyRate) AS `final` "
				            + "FROM InvoiceTransaction t "
				            + "WHERE t.ActivityType = 'Receipt' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "' AND t."
				            + valueCode
				            + " IS NOT NULL "
				            + " GROUP BY `path1`) AS `b` ON a.path1 = b.path1)) "
				            + "UNION "
				            + "(SELECT "
				            + "a.path1 AS `path1`, "
				            + "b.final AS `final`, "
				            + "b.path1 AS `path2`, "
				            + "a.total AS `total` "
				            + "FROM "
				            + "((SELECT t."
				            + valueCode
				            + " AS `path1`,"
				            + "SUM(t.total * t.currencyRate) AS `total` "
				            + "FROM InvoiceTransaction t "
				            + "WHERE t.ActivityType = 'Payment' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "' "
				            + "GROUP BY `path1`) AS `a` "
				            + "LEFT JOIN "
				            + "(SELECT t."
				            + valueCode
				            + " as `path1` , "
				            + "SUM(t.total * t.currencyRate) AS `final` "
				            + "FROM InvoiceTransaction t "
				            + "WHERE t.ActivityType = 'Receipt' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "' AND t."
				            + valueCode
				            + " IS NOT NULL "
				            + "GROUP BY `path1`) AS `b` ON a.path1 = b.path1))) AS `e` ORDER BY `node`")
				    .field("(SELECT CASE when (e.path1 is null) then e.path2 else e.path1 end) as `path3`", "parent")
				    .field("(SELECT CASE when (e.path1 is null) then e.path2 else e.path1 end) as `node`", "node")
				    .field("COALESCE(e.final, 0) AS `totalReceipt`", "final")
				    .field("COALESCE(e.total, 0) AS `totalPayment`", "total");
			} else {
				rQuery
				    .table(
				        "((SELECT " + "a.path1 AS `path1`, " + "b.final AS `final`, " + "b.path1 AS `path2`, "
				            + "a.total AS `total` " + "FROM " + "((SELECT " + " t.tableCode AS `path1`,"
				            + "SUM(t.total * t.currencyRate) AS `total` " + "FROM InvoiceTransaction t "
				            + "WHERE t.ActivityType = 'Payment' AND " + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "' "
				            + " GROUP BY `path1`) AS `a` "
				            + "RIGHT JOIN "
				            + "(SELECT t.tableCode as `path1` ,"
				            + "SUM(t.total * t.currencyRate) AS `final` "
				            + "FROM InvoiceTransaction t "
				            + "WHERE t.ActivityType = 'Receipt' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "'  AND t."
				            + valueCode
				            + " IS NOT NULL "
				            + "GROUP BY `path1`) AS `b` ON a.path1 = b.path1)) "
				            + "UNION "
				            + "(SELECT "
				            + "a.node AS `node1`, "
				            + "b.final AS `final`, "
				            + "b.node AS `node2`, "
				            + "a.total AS `total` "
				            + "FROM "
				            + "((SELECT "
				            + " t.tableCode AS `path1`,"
				            + "SUM(t.total * t.currencyRate) AS `total` "
				            + "FROM InvoiceTransaction t "
				            + "WHERE t.ActivityType = 'Payment' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "' "
				            + "GROUP BY `path1`) AS `a` "
				            + "LEFT JOIN "
				            + "(SELECT t.tableCode as `path1` ,"
				            + "SUM(t.total * t.currencyRate) AS `final` "
				            + "FROM InvoiceTransaction t "
				            + "WHERE t.ActivityType = 'Receipt' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            + "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "'  AND t."
				            + valueCode
				            + " IS NOT NULL "
				            + "GROUP BY `path1`) AS `b` ON a.path1 = b.path1))) AS `e` ORDER BY `node`")
				    .field("(SELECT CASE when (e.path1 is null) then e.path2 else e.path1 end) as `path3`", "parent")
				    .field("(SELECT rt.name from restaurant_table rt where rt.code = `path3`) as `node`", "node")
				    .field("COALESCE(e.final, 0) AS `totalReceipt`", "final")
				    .field("COALESCE(e.total, 0) AS `totalPayment`", "total");
			}
		}

		return rQuery;
	}

	/**
	 * @param truyền
	 *          vào nhóm cha được click
	 * @return trả về list các đối tượng con
	 */
	private List<ReportBankEntity> getChildByPath(ReportBankEntity ReportBankEntity) throws Exception {
		List<ReportBankEntity> nodes = new ArrayList<ReportBankEntity>();
		String parent = null;
		int index = 1;
		if (ReportBankEntity != null) {
			parent = ReportBankEntity.getParent();
			index = index + ReportBankEntity.getIndex();
		}

		/******************************************************
		 * CHILD: THỐNG KÊ DOANH THU || CHI PHÍ || THU CHI LÃI
		 ******************************************************/
		if (typeReport.equals("Doanh thu") || typeReport.equals("Chi phí") || typeReport.equals("Thu - Chi - Lãi")) {
			SQLSelectQuery rQuery = new SQLSelectQuery();
			if (typeReport.equals("Doanh thu"))
				rQuery = query_Child_DoanhThu_ChiPhi(ReportBankEntity, parent, index, ActivityType.Receipt);
			if (typeReport.equals("Chi phí"))
				rQuery = query_Child_DoanhThu_ChiPhi(ReportBankEntity, parent, index, ActivityType.Payment);
			if (typeReport.equals("Thu - Chi - Lãi"))
				rQuery = query_Child_ThuChiLai(ReportBankEntity, parent, index, false);

			System.out.println(rQuery.createSQLQuery());
			ReportTable[] reportTable = accountingServiceClient.reportQuery(new SQLSelectQuery[] { rQuery });
			reportTable[0].dump(new String[] { "node", "final", "total", "parent" });
			String[] field = new String[] { "node", "final", "total", "parent" };
			List<Map<String, Object>> records = reportTable[0].getRecords();
			for (int i = 0; i < records.size(); i++) {
				Map<String, Object> record = records.get(i);
				Object[] values = new Object[field.length];
				for (int j = 0; j < field.length; j++) {
					if (conds.containsKey("product1") && (j == 2 || j == 3)) {
						if (!typeReport.equals("Thu - Chi - Lãi") && j == 2) {
							values[j] = 0;
						} else {
							values[j] = record.get(field[j]);
						}
						if (j == 3) {
							values[j] = "";
						}
					} else if (conds.containsKey("cashier1") && j == 3) {
						values[j] = "";
					} else {
						values[j] = record.get(field[j]);
					}
				}

				double sumFinalCharge;
				double sumTotalTransaction;
				double ratio;
				if (viewMoney.equals("Nghìn")) {
					sumFinalCharge = Double.parseDouble(values[1].toString()) / 1000;
					sumTotalTransaction = Double.parseDouble(values[2].toString()) / 1000;
				} else if (viewMoney.equals("Triệu")) {
					sumFinalCharge = Double.parseDouble(values[1].toString()) / 1000000;
					sumTotalTransaction = Double.parseDouble(values[2].toString()) / 1000000;
				} else {
					sumFinalCharge = Double.parseDouble(values[1].toString());
					sumTotalTransaction = Double.parseDouble(values[2].toString());
				}
				ratio = (sumTotalTransaction / sumFinalCharge) * 100;
				if (typeReport.equals("Thu - Chi - Lãi"))
					ratio = sumFinalCharge - sumTotalTransaction;

				ReportBankEntity n = new ReportBankEntity(values[0].toString(), MyDouble.valueOf(sumFinalCharge).toString(), MyDouble
				    .valueOf(sumTotalTransaction).toString(), MyDouble.valueOf(ratio).toString(), index);
				n.setType(Type.CHILD);
				if (parent != null) {
					if (parent.indexOf("/") > 0) {
						n.setParent(parent + "/");
					} else if (parent.indexOf(":") > 0) {
						n.setParent(parent + ":");
					} else {
						if (values[3] != null) {
							n.setParent(values[3].toString());
						} else {
							n.setParent("");
						}
					}

				} else
					n.setParent(pathParent);
				nodes.add(n);
			}
		} else
		/******************************
		 * CHILD: THU - CHI - CÔNG NỢ
		 ******************************/
		if (typeReport.equals("Thu - Chi - Công nợ")) {
			SQLSelectQuery rQueryR = query_Child_DoanhThu_ChiPhi(ReportBankEntity, parent, index, ActivityType.Receipt);
			SQLSelectQuery rQueryP = query_Child_DoanhThu_ChiPhi(ReportBankEntity, parent, index, ActivityType.Payment);

			List<ReportBankEntity> lstReceipt = null;
			List<ReportBankEntity> lstPayment = null;

			lstReceipt = runQuery(rQueryR);
			lstPayment = runQuery(rQueryP);

			HashMap<String, ReportBankEntity> row_hash = new HashMap<String, ReportBankEntity>();
			if (lstReceipt != null && lstReceipt.size() > 0) {
				for (int i = 0; i < lstReceipt.size(); i++) {
					ReportBankEntity obj = new ReportBankEntity(lstReceipt.get(i).getNode(), lstReceipt.get(i).getColumn1(), lstReceipt
					    .get(i).getColumn2(), "0", "0", "0", "0", "0", index);
					obj.setParent(lstReceipt.get(i).getParent());
					row_hash.put(lstReceipt.get(i).getNode(), obj);
				}
			}

			if (lstPayment != null && lstPayment.size() > 0) {
				for (int i = 0; i < lstPayment.size(); i++) {
					if (!row_hash.containsKey(lstPayment.get(i).getNode())) {
						ReportBankEntity obj = new ReportBankEntity(lstPayment.get(i).getNode(), "0", "0",
						    lstPayment.get(i).getColumn1(), lstPayment.get(i).getColumn2(), "0", "0", "0", index);
						obj.setParent(lstPayment.get(i).getParent());
						row_hash.put(lstPayment.get(i).getParent(), obj);
					} else {
						ReportBankEntity re = row_hash.get(lstPayment.get(i).getNode());
						re.setColumn3(lstPayment.get(i).getColumn1());
						re.setColumn4(lstPayment.get(i).getColumn2());
					}
				}
			}

			int rate = 1;
			if (viewMoney.equals("Nghìn")) {
				rate = 1000;
			} else if (viewMoney.equals("Triệu")) {
				rate = 1000000;
			}

			Iterator<ReportBankEntity> rr = row_hash.values().iterator();
			while (rr.hasNext()) {
				ReportBankEntity r = rr.next();
				Double tongThu = MyDouble.parseDouble(r.getColumn1()) / rate;
				Double tongChi = MyDouble.parseDouble(r.getColumn3()) / rate;
				Double thucThu = MyDouble.parseDouble(r.getColumn2()) / rate;
				Double thucChi = MyDouble.parseDouble(r.getColumn4()) / rate;

				Double phaiThu = tongThu - thucThu;
				Double phaiChi = tongChi - thucChi;
				Double lai = thucThu - thucChi;

				ReportBankEntity row = new ReportBankEntity(r.getNode(), Double.toString(tongThu), Double.toString(tongChi),
				    Double.toString(thucThu), Double.toString(thucChi), Double.toString(phaiThu), Double.toString(phaiChi),
				    Double.toString(lai), index);
				row.setParent(r.getParent().toString());
				row.setType(Type.CHILD);
				if (parent != null) {
					if (parent.indexOf("/") > 0)
						row.setParent(parent + "/");
					else if (parent.indexOf(":") > 0)
						row.setParent(parent + ":");
					else
						row.setParent(r.getParent().toString());
				} else
					row.setParent(pathParent);
				nodes.add(row);
			}
		}
		return nodes;
	}

	private SQLSelectQuery query_Child_DoanhThu_ChiPhi(ReportBankEntity ReportBankEntity, String parent, int index,
	    ActivityType activityType) {
		SQLSelectQuery rQuery = new SQLSelectQuery();
		String sql = "";
		if (isCheck == true) {
			sql = "(Contributor.role = '" + Contributor.nhanVienPV + "' OR Contributor.role = '" + Contributor.thuNgan + "')";
		} else
			sql = "Contributor.role = '" + Contributor.nhanVienPV + "'";
		/** TAB khu vực */
		if (conds.containsKey("location1")) {
			rQuery
			    .table(
			        "(SELECT " + " i.tableCode as `path1` ," + " SUM(i.finalCharge) as `finalCharge` FROM InvoiceDetail i "
			            + " WHERE i.ActivityType = '"
			            + activityType
			            + "' "
			            + " AND i.type NOT LIKE '"
			            + AccountingModelManager.typeSanXuat
			            + "' " // Add dieu kien 12/06/2015 - loai bo hoa don SX
			            + " AND i.startDate >= '"
			            + dateStart
			            + "' "
			            + " AND i.startDate <= '"
			            + dateEnd
			            + "' "
			            + " AND i.locationCode = '"
			            + parent
			            + "' "
			            + " GROUP BY `path1`) AS `a`"
			            + " JOIN "
			            + " (SELECT "
			            + " t.tableCode as `path1`, "
			            + " SUM(t.total) AS `totalTransaction`"
			            + " FROM InvoiceTransaction t "
			            + " WHERE t.ActivityType = '"
			            + activityType
			            + "' "
			            + " AND t.type NOT LIKE '"
			            + AccountingModelManager.typeSanXuat
			            + "' " // Add dieu kien 12/06/2015 - loai bo hoa don SX
			            + " AND t.transactionDate >= '"
			            + dateStart
			            + "' "
			            + " AND t.transactionDate <= '"
			            + dateEnd
			            + "' "
			            + " AND t.locationCode = '"
			            + parent
			            + "' "
			            + " GROUP BY `path1` ) AS `b` ON b.path1 = a.path1 ORDER BY `node`")
			    .field("a.path1 as `path2`", "parent")
			    .field("(SELECT rt.name from restaurant_table rt where rt.code = `path2`) as `node`", "node")
			    .field("COALESCE(a.finalCharge,0)", "final").field("COALESCE(b.totalTransaction,0)", "total");
		}

		/** TAB sản phẩm */
		if (conds.containsKey("product1")) {
			rQuery
			    .table(
			        "InvoiceItem i " + "INNER JOIN product p ON p.code = i.productCode "
			            + "INNER JOIN product_productTag t ON t.productId = p.Id "
			            + "INNER JOIN warehouse_productTag w ON w.id = t.productTagId").field("p.name AS `node`", "node")
			    .field("COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `final`", "final")
			    .cond("i.activityType  = '" + activityType + "' AND i.productCode IS NOT NULL")
			    .cond("i.type NOT LIKE '" + AccountingModelManager.typeSanXuat + "'")
			    .cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "' ")
			    .groupBy("i.productCode ORDER BY `node`");
			if (parent == null) {
				rQuery.cond("w.tag IS NULL");
			} else {
				if (!conds.get("product1").toString().isEmpty())
					rQuery.cond("SUBSTRING_INDEX(w.tag, ':', -1) = '" + parent + "' ");
				else
					rQuery.cond("w.tag = '" + parent + "'");
			}
		}

		/** TAB khách hàng */
		if (conds.containsKey("partner1")) {
			String str = parent.replaceAll("/", " ");
			String qrrCustomer = "";
			if (str.endsWith("groupCustomer-other")) {
				if (!conds.get("partner1").toString().isEmpty())
					qrrCustomer = " EXISTS (select c.id from  customer c  where SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '"
					    + parent + "' and c.code = i.customerCode)";
				else
					qrrCustomer = " EXISTS (select c.id from  customer c  where  i.groupCustomerCode = '" + parent
					    + "' and c.code = i.customerCode)";
			} else {
				if (!conds.get("partner1").toString().isEmpty())
					qrrCustomer = "EXISTS (select am.id from accountmembership am left join customer c on c.loginId = am.loginId where  SUBSTRING_INDEX(am.groupPath, '/', -1) = '"
					    + parent + "' and c.code = i.customerCode and am.recycleBin = 0)";
				else
					qrrCustomer = "EXISTS (select am.id from accountmembership am left join customer c on c.loginId = am.loginId where  am.groupPath = '"
					    + parent + "' and c.code = i.customerCode and am.recycleBin = 0)";
			}
			rQuery
			    .table(
			        "(SELECT " + "SUM(i.finalCharge * i.currencyRate) AS `finalCharge`, " + "i.customerCode AS `aa` "
			            + "FROM " + "InvoiceDetail i " + "WHERE " + "i.ActivityType = '"
			            + activityType
			            + "' AND "
			            + "i.type NOT LIKE '"
			            + AccountingModelManager.typeSanXuat
			            + "' AND "
			            + // Add dieu kien 12/06/2015 - loai bo hoa don SX
			            "i.startDate >= '"
			            + dateStart
			            + "' AND "
			            + "i.startDate <= '"
			            + dateEnd
			            + "' AND "
			            + qrrCustomer
			            + " group by `aa` ) AS `a` "
			            + "JOIN "
			            + "(SELECT "
			            + "SUM(i.total * i.currencyRate) AS `totalTransaction`, i.customerCode as `bb` "
			            + "FROM "
			            + "InvoiceTransaction i "
			            + "WHERE "
			            + "i.ActivityType = '"
			            + activityType
			            + "' AND "
			            + "i.type NOT LIKE '"
			            + AccountingModelManager.typeSanXuat
			            + "' AND "
			            + // Add dieu kien 12/06/2015 - loai bo hoa don SX
			            "i.transactionDate >= '"
			            + dateStart
			            + "' AND "
			            + "i.transactionDate <= '"
			            + dateEnd
			            + "' AND "
			            + qrrCustomer + " group by `bb`  ) AS `b` on a.aa = b.bb ORDER BY `node`")
			    .field("(SELECT c.name FROM customer c WHERE c.code = a.aa  )AS `node`", "node")
			    .field("COALESCE(a.finalCharge,0)", "final").field("COALESCE(b.totalTransaction,0)", "total");
		}

		/** TAB nhân viên */
		if (conds.containsKey("cashier1")) {
			// if (parent != null) { //Nhân viên thuộc 1 phòng ban nào đó
			rQuery.table("InvoiceDetail " + "INNER JOIN Contributor ON Contributor.invoiceId = InvoiceDetail.id "
			    + "LEFT JOIN InvoiceTransaction ON InvoiceTransaction.invoiceId = InvoiceDetail.id "
			    + "INNER JOIN Employee ON Employee.loginId = Contributor.identifierId");
			rQuery.field("Contributor.identifierValue AS `path`", "parent");
			rQuery.field("Employee.name AS `node`", "node");
			rQuery
			    .field(
			        "SUM(CASE WHEN (InvoiceDetail.startDate >= '"
			            + dateStart
			            + "' AND InvoiceDetail.startDate < '"
			            + dateEnd
			            + "' AND (InvoiceTransaction.transactions_ORDER = "
			            + "0 OR InvoiceTransaction.transactions_ORDER IS NULL)) THEN InvoiceDetail.finalCharge *Contributor.percent/100  ELSE 0 END) "
			            + "AS `finalCharge`", "final");
			rQuery
			    .field("SUM(CASE WHEN (InvoiceTransaction.transactionDate >= '" + dateStart + "' "
			        + "AND InvoiceTransaction.transactionDate < '" + dateEnd
			        + "') THEN InvoiceTransaction.total *Contributor.percent/100 ELSE 0 END) AS" + " `totalTransaction`",
			        "total");
			rQuery.cond("Contributor.identifierValue = '" + parent + "' ");
			rQuery.cond(sql);
			rQuery.cond("InvoiceTransaction.type NOT LIKE '" + AccountingModelManager.typeSanXuat + "' ");
			rQuery.cond("InvoiceDetail.type NOT LIKE '" + AccountingModelManager.typeSanXuat + "' ");
			rQuery.groupBy("Employee.loginId ORDER BY `node`");
		}

		/** TAB cửa hàng */
		if (conds.containsKey("store")) {
			rQuery
			    .table(
			        "(SELECT " + " i.tableCode as `path1` ," + " SUM(i.finalCharge) as `finalCharge` FROM InvoiceDetail i "
			            + " WHERE i.ActivityType = '"
			            + activityType
			            + "' "
			            + " AND i.type NOT LIKE '"
			            + AccountingModelManager.typeSanXuat
			            + "' " // Add dieu kien 12/06/2015 - loai bo hoa don SX
			            + " AND i.startDate >= '"
			            + dateStart
			            + "' "
			            + " AND i.startDate <= '"
			            + dateEnd
			            + "' "
			            + " AND i.locationCode = '"
			            + parent
			            + "' "
			            + " GROUP BY `path1`) AS `a`"
			            + " JOIN "
			            + " (SELECT t.tableCode as `path1`, "
			            + " SUM(t.total) AS `totalTransaction` "
			            + " FROM InvoiceTransaction t "
			            + " WHERE t.ActivityType = '"
			            + activityType
			            + "' "
			            + " AND t.type NOT LIKE '"
			            + AccountingModelManager.typeSanXuat
			            + "' " // Add dieu kien 12/06/2015 - loai bo hoa don SX
			            + " AND t.transactionDate >= '"
			            + dateStart
			            + "' "
			            + " AND t.transactionDate <= '"
			            + dateEnd
			            + "' "
			            + " AND t.locationCode = '"
			            + parent
			            + "' "
			            + " GROUP BY `path1` ) AS `b` ON b.path1 = a.path1 ORDER BY `node`")
			    .field("a.path1 as `path2`", "parent")
			    .field("(SELECT rt.name from restaurant_table rt where rt.code = `path2`) as `node`", "node")
			    .field("COALESCE(a.finalCharge,0)", "final").field("COALESCE(b.totalTransaction,0)", "total");
		}

		return rQuery;
	}

	private SQLSelectQuery query_Child_ThuChiLai(ReportBankEntity ReportBankEntity, String parent, int index,
	    boolean thuChiCongNo) {
		SQLSelectQuery rQuery = new SQLSelectQuery();
		String addqrr = "";
		String sql = "";
		if (isCheck == true) {
			sql = "(Contributor.role = '" + Contributor.nhanVienPV + "' OR Contributor.role = '" + Contributor.thuNgan + "')";
		} else
			sql = "Contributor.role = '" + Contributor.nhanVienPV + "'";
		/** TAB nhân viên */
		if (conds.containsKey("cashier1")) {
			System.out.println(parent);
			rQuery.table("InvoiceDetail " + "INNER JOIN Contributor ON Contributor.invoiceId = InvoiceDetail.id "
			    + "LEFT JOIN InvoiceTransaction ON InvoiceTransaction.invoiceId = InvoiceDetail.id "
			    + "INNER JOIN Employee ON Employee.loginId = Contributor.identifierId");
			rQuery.field("SUM(CASE WHEN (InvoiceTransaction.transactionDate >= '" + dateStart
			    + "' AND InvoiceTransaction.transactionDate < '" + dateEnd + "' AND InvoiceDetail.activityType = '"
			    + ActivityType.Receipt + "') THEN InvoiceTransaction.total ELSE 0 END) AS `finalCharge`", "final");
			rQuery.field("SUM(CASE WHEN (InvoiceTransaction.transactionDate >= '" + dateStart
			    + "' AND InvoiceTransaction.transactionDate < '" + dateEnd + "' AND InvoiceDetail.activityType = '"
			    + ActivityType.Payment + "') THEN InvoiceTransaction.total ELSE 0 END) AS `totalTransaction`", "total");
			rQuery.field("Contributor.identifierValue AS `path`", "parent");
			rQuery.field("Employee.name AS `node`", "node");
			if (!conds.get("cashier1").toString().isEmpty())
				rQuery.cond("SUBSTRING_INDEX(Contributor.identifierValue, '/', -1) = '" + parent + "'");
			else
				rQuery.cond("Contributor.identifierValue = '" + parent + "'");
			rQuery.cond(sql);
			rQuery.cond("InvoiceTransaction.type NOT LIKE '" + AccountingModelManager.typeSanXuat + "' ");
			rQuery.cond("InvoiceDetail.type NOT LIKE '" + AccountingModelManager.typeSanXuat + "' ");
			rQuery.groupBy("Employee.loginId ORDER BY `node`");
		}

		/** TAB sản phẩm */
		if (conds.containsKey("product1")) {
			if (parent == null) {
				addqrr = "w.tag IS NULL";
			} else {
				if (!conds.get("product1").toString().isEmpty())
					addqrr = "SUBSTRING_INDEX(w.tag, ':', -1) = '" + parent + "'";
				else
					addqrr = "w.tag = '" + parent + "'";
			}
			rQuery
			    .table(
			        "(SELECT p.name AS `node`, " + "SUM(i.finalCharge * i.currencyRate) AS `totalTransaction` "
			            + "FROM InvoiceItem i " + "INNER JOIN product p ON p.code = i.productCode "
			            + "INNER JOIN product_productTag t ON t.productId = p.Id "
			            + "INNER JOIN warehouse_productTag w ON w.id = t.productTagId "
			            + "WHERE i.ActivityType = 'Payment' AND " + "i.type NOT LIKE '"
			            + AccountingModelManager.typeSanXuat
			            + "' AND "
			            + "i.startDate >= '"
			            + dateStart
			            + "' AND "
			            + "i.startDate <= '"
			            + dateEnd
			            + "' AND "
			            + addqrr
			            + " GROUP BY i.productCode) AS `a`"
			            + " RIGHT JOIN "
			            + " (SELECT p.name AS `node`, "
			            + "SUM(i.finalCharge * i.currencyRate) AS `totalTransaction` "
			            + "FROM InvoiceItem i "
			            + "INNER JOIN product p ON p.code = i.productCode "
			            + "INNER JOIN product_productTag t ON t.productId = p.Id "
			            + "INNER JOIN warehouse_productTag w ON w.id = t.productTagId "
			            + "WHERE i.ActivityType = 'Receipt' AND "
			            + "i.type NOT LIKE '"
			            + AccountingModelManager.typeSanXuat
			            + "' AND "
			            + "i.startDate >= '"
			            + dateStart
			            + "' AND "
			            + "i.startDate <= '"
			            + dateEnd
			            + "' AND "
			            + addqrr + " GROUP BY i.productCode ) AS `b` ON b.node = a.node ORDER BY `node`")
			    .field("(SELECT CASE when a.node is null then b.node else a.node end) as `node`", "node")
			    .field("COALESCE(a.totalTransaction,0) AS `totalPay`", "total")
			    .field("COALESCE(b.totalTransaction,0) AS `totalRec`", "final");
		}

		/** TAB khu vực */
		if (conds.containsKey("location1")) {
			System.out.println(1);
			rQuery
			    .table(
			        "((SELECT " + "(CASE when a.path1 is null then b.path1 else a.path1 end) as `path2`, "
			            + "(SELECT rt.name from restaurant_table rt where rt.code = `path2`) as `node`, "
			            + "COALESCE(a.totalTransaction, 0) AS `totalPay`, " + "COALESCE(b.totalTransaction,0) AS `totalRec` "
			            + "FROM " + "(SELECT t.tableCode AS `path1`,"
			            + "SUM(t.total * t.currencyRate) AS `totalTransaction` " + "FROM InvoiceTransaction t "
			            + "WHERE t.ActivityType = 'Payment' AND " + "t.type NOT LIKE '"
			            + AccountingModelManager.typeSanXuat
			            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
			            + "t.transactionDate >= '"
			            + dateStart
			            + "' AND "
			            + "t.transactionDate <= '"
			            + dateEnd
			            + "' and t.locationCode = '"
			            + parent
			            + "' "
			            + "GROUP BY `path1`) AS `a` "
			            + "RIGHT JOIN "
			            + "(SELECT t.tableCode as `path1` ,"
			            + "SUM(t.total * t.currencyRate) AS `totalTransaction` "
			            + "FROM InvoiceTransaction t "
			            + "WHERE t.ActivityType = 'Receipt' AND "
			            + "t.type NOT LIKE '"
			            + AccountingModelManager.typeSanXuat
			            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
			            + "t.transactionDate >= '"
			            + dateStart
			            + "' AND "
			            + "t.transactionDate <= '"
			            + dateEnd
			            + "'  AND t.tableCode IS NOT NULL and t.locationCode = '"
			            + parent
			            + "' "
			            + "GROUP BY `path1`) AS `b` ON b.path1 = a.path1) "
			            + "UNION "
			            +

			            "(SELECT "
			            + "(CASE when a.path1 is null then b.path1 else a.path1 end) as `path2`, "
			            + "(SELECT rt.name from restaurant_table rt where rt.code = `path2`) as `node`, "
			            + "COALESCE(a.totalTransaction, 0) AS `totalPay`, "
			            + "COALESCE(b.totalTransaction,0) AS `totalRec` "
			            + "FROM "
			            + "((SELECT "
			            + " t.tableCode AS `path1`,"
			            + "SUM(t.total * t.currencyRate) AS `totalTransaction` "
			            + "FROM InvoiceTransaction t "
			            + "WHERE t.ActivityType = 'Payment' AND "
			            + "t.type NOT LIKE '"
			            + AccountingModelManager.typeSanXuat
			            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
			            + "t.transactionDate >= '"
			            + dateStart
			            + "' AND "
			            + "t.transactionDate <= '"
			            + dateEnd
			            + "' and t.locationCode = '"
			            + parent
			            + "' "
			            + "GROUP BY `path1`) AS `a` "
			            + "LEFT JOIN "
			            + "(SELECT t.tableCode as `path1` ,"
			            + "SUM(t.total * t.currencyRate) AS `totalTransaction` "
			            + "FROM InvoiceTransaction t "
			            + "WHERE t.ActivityType = 'Receipt' AND "
			            + "t.type NOT LIKE '"
			            + AccountingModelManager.typeSanXuat
			            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
			            + "t.transactionDate >= '"
			            + dateStart
			            + "' AND "
			            + "t.transactionDate <= '"
			            + dateEnd
			            + "'  AND t.tableCode IS NOT NULL and t.locationCode = '"
			            + parent
			            + "' "
			            + "GROUP BY `path1` ) AS `b` ON b.path1 = a.path1))) AS `c` ORDER BY `node`")
			    .field("c.path2", "parent").field("c.node", "node").field("c.totalPay", "total").field("c.totalRec", "final");
		}

		/** TAB khách hàng */
		if (conds.containsKey("partner1")) {
			String str = parent.replaceAll("/", " ");
			String qrrCustomer = "";
			if (str.endsWith("groupCustomer-other")) {
				if (!conds.get("partner1").toString().isEmpty())
					qrrCustomer = " EXISTS (select c.id from  customer c  where SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '"
					    + parent + "' and c.code = i.customerCode)";
				else
					qrrCustomer = " EXISTS (select c.id from  customer c  where  i.groupCustomerCode = '" + parent
					    + "' and c.code = i.customerCode)";
			} else {
				if (!conds.get("partner1").toString().isEmpty())
					qrrCustomer = "EXISTS (select am.id from accountmembership am left join customer c on c.loginId = am.loginId where  SUBSTRING_INDEX(am.groupPath, '/',  -1) = '"
					    + parent + "' and c.code = i.customerCode and am.recycleBin = 0 )";
				else
					qrrCustomer = "EXISTS (select am.id from accountmembership am left join customer c on c.loginId = am.loginId where  am.groupPath = '"
					    + parent + "' and c.code = i.customerCode and am.recycleBin = 0 )";
			}

			rQuery
			    .table(
			        "((SELECT " + "a.cCode AS `cCode1`, " + "b.final AS `final`, " + "b.cCode AS `cCode2`, "
			            + "a.total AS `total` " + "FROM " + "((SELECT "
			            + "SUM(i.total * i.currencyRate) AS `total` , i.customerCode as `cCode` " + "FROM "
			            + "InvoiceTransaction i " + "WHERE " + "i.ActivityType = 'Payment' AND " + "i.type NOT LIKE '"
			            + AccountingModelManager.typeSanXuat
			            + "' AND "
			            + // Add dieu kien 12/06/2015 - loai bo hoa don SX
			            "i.transactionDate >= '"
			            + dateStart
			            + "' AND "
			            + "i.transactionDate <= '"
			            + dateEnd
			            + "' AND "
			            + qrrCustomer
			            + " group by `cCode` ) AS `a` "
			            + "RIGHT JOIN "
			            + "(SELECT "
			            + "SUM(i.total * i.currencyRate) AS `final`, i.customerCode as `cCode` "
			            + "FROM "
			            + "InvoiceTransaction i "
			            + "WHERE "
			            + "i.ActivityType = 'Receipt' AND "
			            + "i.type NOT LIKE '"
			            + AccountingModelManager.typeSanXuat
			            + "' AND "
			            + // Add dieu kien 12/06/2015 - loai bo hoa don SX
			            "i.transactionDate >= '"
			            + dateStart
			            + "' AND "
			            + "i.transactionDate <= '"
			            + dateEnd
			            + "' AND "
			            + qrrCustomer
			            + " group by `cCode`  ) AS `b` ON b.cCode = a.cCode)) "
			            + "UNION "
			            + "(SELECT "
			            + "a.cCode AS `cCode1`, "
			            + "b.final AS `final`, "
			            + "b.cCode AS `cCode2`, "
			            + "a.total AS `total` "
			            + "FROM "
			            + "((SELECT "
			            + "SUM(i.total * i.currencyRate) AS `total` , i.customerCode as `cCode` "
			            + "FROM "
			            + "InvoiceTransaction i "
			            + "WHERE "
			            + "i.ActivityType = 'Payment' AND "
			            + "i.type NOT LIKE '"
			            + AccountingModelManager.typeSanXuat
			            + "' AND "
			            + // Add dieu kien 12/06/2015 - loai bo hoa don SX
			            "i.transactionDate >= '"
			            + dateStart
			            + "' AND "
			            + "i.transactionDate <= '"
			            + dateEnd
			            + "' AND "
			            + qrrCustomer
			            + " group by `cCode` ) AS `a` "
			            + "LEFT JOIN "
			            + "(SELECT "
			            + "SUM(i.total * i.currencyRate) AS `final`, i.customerCode as `cCode` "
			            + "FROM "
			            + "InvoiceTransaction i "
			            + "WHERE "
			            + "i.ActivityType = 'Receipt' AND "
			            + "i.type NOT LIKE '"
			            + AccountingModelManager.typeSanXuat
			            + "' AND "
			            + // Add dieu kien 12/06/2015 - loai bo hoa don SX
			            "i.transactionDate >= '"
			            + dateStart
			            + "' AND "
			            + "i.transactionDate <= '"
			            + dateEnd
			            + "' AND "
			            + qrrCustomer + " group by `cCode` ) AS `b` ON b.cCode = a.cCode))) AS `e` ORDER BY `node`")
			    .field("(SELECT c.name FROM customer c WHERE c.code = e.cCode1 or c.code = e.cCode2 ) AS `node`", "node")
			    .field("COALESCE(e.final, 0) AS `totalReceipt`", "final")
			    .field("COALESCE(e.total, 0) AS `totalPayment`", "total");
		}

		/** TAB cửa hàng */
		if (conds.containsKey("store")) {
			rQuery
			    .table(
			        "((SELECT " + "(CASE when a.path1 is null then b.path1 else a.path1 end) as `path2`, "
			            + "(SELECT rt.name from restaurant_table rt where rt.code = `path2`) as `node`, "
			            + "COALESCE(a.totalTransaction, 0) AS `totalPay`, " + "COALESCE(b.totalTransaction,0) AS `totalRec` "
			            + "FROM " + "(SELECT t.tableCode AS `path1`,"
			            + "SUM(t.total * t.currencyRate) AS `totalTransaction` " + "FROM InvoiceTransaction t "
			            + "WHERE t.ActivityType = 'Payment' AND " + "t.type NOT LIKE '"
			            + AccountingModelManager.typeSanXuat
			            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
			            + "t.transactionDate >= '"
			            + dateStart
			            + "' AND "
			            + "t.transactionDate <= '"
			            + dateEnd
			            + "' AND t.locationCode = '"
			            + parent
			            + "' "
			            + "GROUP BY `path1`) AS `a` "
			            + "RIGHT JOIN "
			            + "(SELECT t.tableCode as `path1` ,"
			            + "SUM(t.total * t.currencyRate) AS `totalTransaction` "
			            + "FROM InvoiceTransaction t "
			            + "WHERE t.ActivityType = 'Receipt' AND "
			            + "t.type NOT LIKE '"
			            + AccountingModelManager.typeSanXuat
			            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
			            + "t.transactionDate >= '"
			            + dateStart
			            + "' AND "
			            + "t.transactionDate <= '"
			            + dateEnd
			            + "'  AND t.tableCode IS NOT NULL and t.locationCode = '"
			            + parent
			            + "' "
			            + "GROUP BY `path1`) AS `b` ON b.path1 = a.path1) "
			            + "UNION "
			            +

			            "(SELECT "
			            + "(CASE when a.path1 is null then b.path1 else a.path1 end) as `path2`, "
			            + "(SELECT rt.name from restaurant_table rt where rt.code = `path2`) as `node`, "
			            + "COALESCE(a.totalTransaction, 0) AS `totalPay`, "
			            + "COALESCE(b.totalTransaction,0) AS `totalRec` "
			            + "FROM "
			            + "((SELECT "
			            + " t.tableCode AS `path1`,"
			            + "SUM(t.total * t.currencyRate) AS `totalTransaction` "
			            + "FROM InvoiceTransaction t "
			            + "WHERE t.ActivityType = 'Payment' AND "
			            + "t.type NOT LIKE '"
			            + AccountingModelManager.typeSanXuat
			            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
			            + "t.transactionDate >= '"
			            + dateStart
			            + "' AND "
			            + "t.transactionDate <= '"
			            + dateEnd
			            + "' and t.locationCode = '"
			            + parent
			            + "' "
			            + "GROUP BY `path1`) AS `a` "
			            + "LEFT JOIN "
			            + "(SELECT t.tableCode as `path1` ,"
			            + "SUM(t.total * t.currencyRate) AS `totalTransaction` "
			            + "FROM InvoiceTransaction t "
			            + "WHERE t.ActivityType = 'Receipt' AND "
			            + "t.type NOT LIKE '"
			            + AccountingModelManager.typeSanXuat
			            + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
			            + "t.transactionDate >= '"
			            + dateStart
			            + "' AND "
			            + "t.transactionDate <= '"
			            + dateEnd
			            + "'  AND t.tableCode IS NOT NULL and t.locationCode = '"
			            + parent
			            + "' "
			            + "GROUP BY `path1` ) AS `b` ON b.path1 = a.path1))) AS `c` ORDER BY `node`")
			    .field("c.path2", "parent").field("c.node", "node").field("c.totalPay", "total").field("c.totalRec", "final");
		}

		return rQuery;
	}

	//TODO
	private List<ReportBankEntity> getInvoice(ReportBankEntity ReportBankEntity) throws Exception {
		List<ReportBankEntity> nodes = new ArrayList<ReportBankEntity>();

		/**********************
		 * THỐNG KÊ DOANH THU *
		 **********************/
		String value= "tableCode";
		if (conds.containsKey("store")) {
			value = "type";
		}
		if (typeReport.equals("Doanh thu")) {
			String parent = null;
			int index = 0;
			if (ReportBankEntity != null) {
				parent = ReportBankEntity.getParent();
				index = ReportBankEntity.getIndex();
			}
			
			SQLSelectQuery rQuery = new SQLSelectQuery();
			// Trường hợp có chọn điều kiện [bàn]
			if (conds.containsKey("location2") && !conds.get("location2").isEmpty()) {
				rQuery
				    .table(
				        "(SELECT "
				            + "SUM(i.finalCharge) AS `finalCharge` , concat(i.invoiceCode,'--',i.invoiceName) as `node`, i.id as `id` "
				            + "FROM " + "InvoiceDetail i LEFT JOIN InvoiceTransaction t ON t.invoiceId = i.id " + "WHERE "
				            + "i.ActivityType = 'Receipt' AND " + "i.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND "
				            + // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            "i.startDate >= '"
				            + dateStart
				            + "' AND "
				            + "i.startDate <= '"
				            + dateEnd
				            + "' AND i.tableCode = '"
				            + conds.get("location2").toString()
				            + "' "
				            + "GROUP BY `InvoiceCode`) AS `a` "
				            + "JOIN "
				            + "(SELECT "
				            + "SUM(t.total) AS `totalTransaction` ,t.invoiceId as `id`, concat(i.invoiceCode,'--',i.invoiceName) as `node`  "
				            + "FROM "
				            + "InvoiceDetail i LEFT JOIN InvoiceTransaction t ON t.invoiceId = i.id "
				            + "WHERE "
				            + "t.ActivityType = 'Receipt' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND "
				            + // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "' AND t.tableCode = '"
				            + conds.get("location2").toString()
				            + "' "
				            + "GROUP BY `InvoiceCode`) AS `b` ON b.id = a.id ORDER BY `node` DESC")
				    .field("a.finalCharge", "sumFinalCharge").field("b.totalTransaction", "sumTotalTransaction")
				    .field("(CASE WHEN (a.node IS NULL) THEN b.node ELSE a.node END) AS `node`", "node");
			} else {
				rQuery
				    .table(
				        "(SELECT "
				            + "SUM(i.finalCharge) AS `finalCharge` , concat(i.invoiceCode,'--',i.invoiceName) as `node`, i.id as `id` "
				            + "FROM " + "InvoiceDetail i LEFT JOIN InvoiceTransaction t ON t.invoiceId = i.id " + "WHERE "
				            + "i.ActivityType = 'Receipt' AND " + "i.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND "
				            + // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            "i.startDate >= '"
				            + dateStart
				            + "' AND "
				            + "i.startDate <= '"
				            + dateEnd
				            + "' AND i."+value+" = '"
				            + parent
				            + "' "
				            + "GROUP BY `InvoiceCode`) AS `a` "
				            + "JOIN "
				            + "(SELECT "
				            + "SUM(t.total) AS `totalTransaction` ,t.invoiceId as `id`, concat(i.invoiceCode,'--',i.invoiceName) as `node`  "
				            + "FROM "
				            + "InvoiceDetail i LEFT JOIN InvoiceTransaction t ON t.invoiceId = i.id "
				            + "WHERE "
				            + "t.ActivityType = 'Receipt' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND "
				            + // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "' AND t."+value+" = '"
				            + parent
				            + "' "
				            + "GROUP BY `InvoiceCode`) AS `b` ON b.id = a.id ORDER BY `node` DESC")
				    .field("a.finalCharge", "sumFinalCharge").field("b.totalTransaction", "sumTotalTransaction")
				    .field("(CASE WHEN (a.node IS NULL) THEN b.node ELSE a.node END) AS `node`", "node");
			}

			System.out.println(rQuery.createSQLQuery());
			ReportTable[] reportTable = accountingServiceClient.reportQuery(new SQLSelectQuery[] { rQuery });
			reportTable[0].dump(new String[] { "node", "sumFinalCharge", "sumTotalTransaction" });
			String[] field = new String[] { "node", "sumFinalCharge", "sumTotalTransaction" };
			List<Map<String, Object>> records = reportTable[0].getRecords();
			for (int i = 0; i < records.size(); i++) {
				Map<String, Object> record = records.get(i);
				Object[] values = new Object[field.length];
				for (int j = 0; j < field.length; j++) {
					if (field[j].toString().equals("node")) {
						String str = record.get(field[j]).toString();
						if (str.indexOf(":") > 0) {
							values[j] = str.substring(str.indexOf(":") + 1);
						} else {
							values[j] = record.get(field[j]);
						}
					} else {
						values[j] = record.get(field[j]);
					}

				}

				double sumFinalCharge;
				double sumTotalTransaction;
				if (viewMoney.equals("Nghìn")) {
					sumFinalCharge = Double.parseDouble(values[1].toString()) / 1000;
					sumTotalTransaction = Double.parseDouble(values[2].toString()) / 1000;
				} else if (viewMoney.equals("Triệu")) {
					sumFinalCharge = Double.parseDouble(values[1].toString()) / 1000000;
					sumTotalTransaction = Double.parseDouble(values[2].toString()) / 1000000;
				} else {
					sumFinalCharge = Double.parseDouble(values[1].toString());
					sumTotalTransaction = Double.parseDouble(values[2].toString());
				}
				double ratio = (sumTotalTransaction / sumFinalCharge) * 100;
				ReportBankEntity n = new ReportBankEntity("Hóa đơn " + values[0].toString(), Double.toString(sumFinalCharge),
				    Double.toString(sumTotalTransaction), Double.toString(ratio), index + 1);
				if (parent.indexOf("/") > 0)
					n.setParent(parent + "/");
				else if (parent.indexOf(":") > 0)
					n.setParent(parent + ":");
				else
					n.setParent(parent);
				n.setType(Type.INVOICE);
				nodes.add(n);
			}
		}

		/**********************
		 * THỐNG KÊ CHI PHÍ *
		 **********************/
		else if (typeReport.equals("Chi phí")) {
			String parent = null;
			int index = 0;
			if (ReportBankEntity != null) {
				parent = ReportBankEntity.getParent();
				index = ReportBankEntity.getIndex();
			}
			SQLSelectQuery rQuery = new SQLSelectQuery();

			if (conds.containsKey("location2") && !conds.get("location2").isEmpty()) {
				rQuery
				    .table(
				        "(SELECT "
				            + "SUM(i.finalCharge * i.currencyRate) AS `finalCharge` , concat(i.invoiceCode,'--',i.invoiceName) as `node`, i.id as `id` "
				            + "FROM " + "InvoiceDetail i LEFT JOIN InvoiceTransaction t ON t.invoiceId = i.id " + "WHERE "
				            + "i.ActivityType = 'Payment' AND " + "i.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND "
				            + // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            "i.startDate >= '"
				            + dateStart
				            + "' AND "
				            + "i.startDate <= '"
				            + dateEnd
				            + "' AND i.tableCode = '"
				            + conds.get("location2").toString()
				            + "' "
				            + "GROUP BY `InvoiceCode`) AS `a` "
				            + "JOIN "
				            + "(SELECT "
				            + "SUM(t.total * t.currencyRate) AS `totalTransaction` ,t.invoiceId as `id`, concat(i.invoiceCode,'--',i.invoiceName) as `node`  "
				            + "FROM "
				            + "InvoiceDetail i LEFT JOIN InvoiceTransaction t ON t.invoiceId = i.id "
				            + "WHERE "
				            + "t.ActivityType = 'Payment' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND "
				            + // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "' AND t.tableCode = '"
				            + conds.get("location2").toString()
				            + "' "
				            + "GROUP BY `InvoiceCode`) AS `b` ON b.id = a.id ORDER BY `node` DESC")
				    .field("a.finalCharge", "sumFinalCharge").field("b.totalTransaction", "sumTotalTransaction")
				    .field("a.node AS `node`", "node");
			} else {
				rQuery
				    .table(
				        "(SELECT "
				            + "SUM(i.finalCharge * i.currencyRate) AS `finalCharge` , concat(i.invoiceCode,'--',i.invoiceName) as `node`, i.id as `id` "
				            + "FROM " + "InvoiceDetail i LEFT JOIN InvoiceTransaction t ON t.invoiceId = i.id " + "WHERE "
				            + "i.ActivityType = 'Payment' AND " + "i.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND "
				            + // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            "i.startDate >= '"
				            + dateStart
				            + "' AND "
				            + "i.startDate <= '"
				            + dateEnd
				            + "' AND i."+value+" = '"
				            + parent
				            + "' "
				            + "GROUP BY `InvoiceCode`) AS `a` "
				            + "JOIN "
				            + "(SELECT "
				            + "SUM(t.total * t.currencyRate) AS `totalTransaction` ,t.invoiceId as `id`, concat(i.invoiceCode,'--',i.invoiceName) as `node`  "
				            + "FROM "
				            + "InvoiceDetail i LEFT JOIN InvoiceTransaction t ON t.invoiceId = i.id "
				            + "WHERE "
				            + "t.ActivityType = 'Payment' AND "
				            + "t.type NOT LIKE '"
				            + AccountingModelManager.typeSanXuat
				            + "' AND "
				            + // Add dieu kien 12/06/2015 - loai bo hoa don SX
				            "t.transactionDate >= '"
				            + dateStart
				            + "' AND "
				            + "t.transactionDate <= '"
				            + dateEnd
				            + "' AND t."+value+" = '"
				            + parent
				            + "' "
				            + "GROUP BY `InvoiceCode`) AS `b` ON b.id = a.id ORDER BY `node` DESC")
				    .field("a.finalCharge", "sumFinalCharge").field("b.totalTransaction", "sumTotalTransaction")
				    .field("a.node AS `node`", "node");
			}

			System.out.println(rQuery.createSQLQuery());
			ReportTable[] reportTable = accountingServiceClient.reportQuery(new SQLSelectQuery[] { rQuery });
			reportTable[0].dump(new String[] { "node", "sumFinalCharge", "sumTotalTransaction" });
			String[] field = new String[] { "node", "sumFinalCharge", "sumTotalTransaction" };
			List<Map<String, Object>> records = reportTable[0].getRecords();
			for (int i = 0; i < records.size(); i++) {
				Map<String, Object> record = records.get(i);
				Object[] values = new Object[field.length];
				for (int j = 0; j < field.length; j++) {
					if (field[j].toString().equals("node")) {
						String str = record.get(field[j]).toString();
						if (str.indexOf(":") > 0) {
							values[j] = str.substring(str.indexOf(":") + 1);
						} else {
							values[j] = record.get(field[j]);
						}
					} else {
						values[j] = record.get(field[j]);
					}

				}

				double sumFinalCharge;
				double sumTotalTransaction;
				if (viewMoney.equals("Nghìn")) {
					sumFinalCharge = Double.parseDouble(values[1].toString()) / 1000;
					sumTotalTransaction = Double.parseDouble(values[2].toString()) / 1000;
				} else if (viewMoney.equals("Triệu")) {
					sumFinalCharge = Double.parseDouble(values[1].toString()) / 1000000;
					sumTotalTransaction = Double.parseDouble(values[2].toString()) / 1000000;
				} else {
					sumFinalCharge = Double.parseDouble(values[1].toString());
					sumTotalTransaction = Double.parseDouble(values[2].toString());
				}
				double ratio = (sumTotalTransaction / sumFinalCharge) * 100;

				ReportBankEntity n = new ReportBankEntity("Hóa đơn " + values[0].toString(), Double.toString(sumFinalCharge),
				    Double.toString(sumTotalTransaction), Double.toString(ratio), index + 1);
				if (parent.indexOf("/") > 0)
					n.setParent(parent + "/");
				else if (parent.indexOf(":") > 0)
					n.setParent(parent + ":");
				else
					n.setParent(parent);
				n.setType(Type.INVOICE);
				nodes.add(n);
			}
		}

		/****************************
		 * THỐNG KÊ THU - CHI - LÃI *
		 ****************************/
		else if (typeReport.equals("Thu - Chi - Lãi")) {
			String parent = null;
			int index = 0;
			if (ReportBankEntity != null) {
				parent = ReportBankEntity.getParent();
				index = ReportBankEntity.getIndex();
			}
			SQLSelectQuery rQuery = new SQLSelectQuery();

			// Trường hợp combobox chọn Bàn -> Ấn thống kê
			if (conds.containsKey("location2") && !conds.get("location2").isEmpty()) {
				rQuery.table("(SELECT " + " concat(i.invoiceCode,'--',i.invoiceName) AS `InvoiceCode`,	"
				    + " SUM(t.total) AS `total`, " + " t.activityType AS `type` "
				    + " FROM InvoiceTransaction t JOIN InvoiceDetail i ON i.id = t.invoiceId " + " WHERE "
				    + "t.activityType = 'Receipt' AND " + "t.type NOT LIKE '"
				    + AccountingModelManager.typeSanXuat
				    + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				    + "t.transactionDate >= '"
				    + dateStart
				    + "' AND "
				    + "t.transactionDate <= '"
				    + dateEnd
				    + "' AND "
				    + "i.tableCode = '"
				    + conds.get("location2").toString()
				    + "' "
				    + "GROUP BY `InvoiceCode` "

				    + " UNION ALL "

				    + " SELECT "
				    + "	concat(i.invoiceCode,'--',i.invoiceName) AS `InvoiceCode`, "
				    + " SUM(t.total) AS `total`, "
				    + " t.ActivityType AS `type` "
				    + " FROM InvoiceTransaction t JOIN InvoiceDetail i ON i.id = t.invoiceId "
				    + " WHERE "
				    + "t.activityType = 'Payment' AND "
				    + "t.type NOT LIKE '"
				    + AccountingModelManager.typeSanXuat
				    + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				    + "t.transactionDate >= '"
				    + dateStart
				    + "' AND "
				    + "t.transactionDate <= '"
				    + dateEnd
				    + "' AND "
				    + "t.tableCode = '"
				    + conds.get("location2").toString()
				    + "' "
				    + "GROUP BY `InvoiceCode`) AS `e` ORDER BY e.InvoiceCode DESC");
				rQuery.field(" e.InvoiceCode as `node` ", "node");
				rQuery.field("(CASE WHEN e.type = 'Receipt' THEN e.total ELSE 0 END) AS `sumFinalCharge` ", "sumFinalCharge");
				rQuery.field("(CASE WHEN e.type = 'Payment' THEN e.total ELSE 0 END) AS `sumTotalTransaction` ",
				    "sumTotalTransaction");
			}
			// Trường hợp click lần lượt vào bàn -> Lấy tất cả hóa đơn của bàn đó
			else {
				rQuery.table("(SELECT " + " concat(i.invoiceCode,'--',i.invoiceName) AS `InvoiceCode`,	"
				    + " SUM(t.total) AS `total`, " + " t.activityType AS `type` "
				    + " FROM InvoiceTransaction t JOIN InvoiceDetail i ON i.id = t.invoiceId " + " WHERE "
				    + " t.activityType = 'Receipt' AND " + " t.type NOT LIKE '"
				    + AccountingModelManager.typeSanXuat
				    + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				    + " t.transactionDate >= '"
				    + dateStart
				    + "' AND "
				    + " t.transactionDate <= '"
				    + dateEnd
				    + "' AND "
				    + " i."+value+" = '"
				    + parent
				    + "' "
				    + " GROUP BY `InvoiceCode` "
				    + " UNION ALL "
				    + " SELECT "
				    + "	concat(i.invoiceCode,'--',i.invoiceName) AS `InvoiceCode`, "
				    + " SUM(t.total) AS `total`, "
				    + " t.ActivityType AS `type` "
				    + " FROM InvoiceTransaction t JOIN InvoiceDetail i ON i.id = t.invoiceId "
				    + " WHERE "
				    + " t.activityType = 'Payment' AND "
				    + " t.type NOT LIKE '"
				    + AccountingModelManager.typeSanXuat
				    + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				    + " t.transactionDate >= '"
				    + dateStart
				    + "' AND "
				    + " t.transactionDate <= '"
				    + dateEnd
				    + "' AND "
				    + " t."+value+" ='" + parent + "' " + " GROUP BY `InvoiceCode`) AS `e` ORDER BY e.InvoiceCode DESC");
				rQuery.field(" e.InvoiceCode as `node` ", "node");
				rQuery.field(" ( CASE WHEN e.type = 'Receipt' THEN e.total ELSE 0 END) AS `sumFinalCharge` ", "sumFinalCharge");
				rQuery.field("( CASE WHEN e.type = 'Payment' THEN e.total ELSE 0 END) AS `sumTotalTransaction` ",
				    "sumTotalTransaction");
			}

			System.out.println(rQuery.createSQLQuery());
			ReportTable[] reportTable = accountingServiceClient.reportQuery(new SQLSelectQuery[] { rQuery });
			reportTable[0].dump(new String[] { "node", "sumFinalCharge", "sumTotalTransaction" });
			String[] field = new String[] { "node", "sumFinalCharge", "sumTotalTransaction" };
			List<Map<String, Object>> records = reportTable[0].getRecords();
			for (int i = 0; i < records.size(); i++) {
				Map<String, Object> record = records.get(i);
				Object[] values = new Object[field.length];
				for (int j = 0; j < field.length; j++) {
					if (field[j].toString().equals("node")) {
						String str = record.get(field[j]).toString();
						if (str.indexOf(":") > 0) {
							values[j] = str.substring(str.indexOf(":") + 1);
						} else {
							values[j] = record.get(field[j]);
						}
					} else {
						values[j] = record.get(field[j]);
					}

				}

				double sumFinalCharge;
				double sumTotalTransaction;
				if (viewMoney.equals("Nghìn")) {
					sumFinalCharge = Double.parseDouble(values[1].toString()) / 1000;
					sumTotalTransaction = Double.parseDouble(values[2].toString()) / 1000;
				} else if (viewMoney.equals("Triệu")) {
					sumFinalCharge = Double.parseDouble(values[1].toString()) / 1000000;
					sumTotalTransaction = Double.parseDouble(values[2].toString()) / 1000000;
				} else {
					sumFinalCharge = Double.parseDouble(values[1].toString());
					sumTotalTransaction = Double.parseDouble(values[2].toString());
				}
				double ratio = sumFinalCharge - sumTotalTransaction;

				ReportBankEntity n = new ReportBankEntity("Hóa đơn " + values[0].toString(), Double.toString(sumFinalCharge),
				    Double.toString(sumTotalTransaction), Double.toString(ratio), index + 1);
				if (parent.indexOf("/") > 0)
					n.setParent(parent + "/");
				else if (parent.indexOf(":") > 0)
					n.setParent(parent + ":");
				else
					n.setParent(parent);
				n.setType(Type.INVOICE);
				nodes.add(n);
			}
		} else if (typeReport.equals("Thu - Chi - Công nợ")) {
			String parent = null;
			int index = 0;
			if (ReportBankEntity != null) {
				parent = ReportBankEntity.getParent();
				index = ReportBankEntity.getIndex();
			}
			SQLSelectQuery rQuery = new SQLSelectQuery();
			if (conds.containsKey("location2") && !conds.get("location2").isEmpty()) {

				rQuery.table("((SELECT * FROM " + " ((SELECT " + " concat(i.invoiceCode,'--',i.invoiceName) AS `InvoiceCode`,	"
				    + " SUM(i.finalCharge) AS `finalR`, " + " i.activityType AS `type1` " + " FROM InvoiceDetail i "
				    + " WHERE i.activityType = 'Receipt' " + " AND i.type NOT LIKE '"
				    + AccountingModelManager.typeSanXuat
				    + "' " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				    + " AND i.startDate >= '"
				    + dateStart
				    + "' "
				    + " AND i.startDate <= '"
				    + dateEnd
				    + "' "
				    + " AND i.tableCode = '"
				    + conds.get("location2").toString()
				    + "' "
				    + " GROUP BY `InvoiceCode`) as `a` "
				    + " JOIN "
				    + " (SELECT "
				    + "	concat(i.invoiceCode,'--',i.invoiceName) AS `InvoiceCode1`, "
				    + " SUM(t.total) AS `totalR`, "
				    + " t.ActivityType AS `type2` "
				    + " FROM InvoiceTransaction t JOIN InvoiceDetail i ON i.id = t.invoiceId "
				    + " WHERE t.activityType = 'Receipt' AND "
				    + " t.type NOT LIKE '"
				    + AccountingModelManager.typeSanXuat
				    + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				    + " t.transactionDate >= '"
				    + dateStart
				    + "' AND "
				    + " t.transactionDate <= '"
				    + dateEnd
				    + "' AND "
				    + " t.tableCode = '"
				    + conds.get("location2").toString()
				    + "' "
				    + " GROUP BY `InvoiceCode`) AS `b` on b.InvoiceCode1 = a.InvoiceCode ))"
				    + " UNION ALL "
				    + " (SELECT * FROM "
				    + " ((SELECT "
				    + " concat(i.invoiceCode,'--',i.invoiceName) AS `InvoiceCode`,	"
				    + " SUM(i.finalCharge) AS `finalR`, "
				    + " i.activityType AS `type1` "
				    + " FROM InvoiceDetail i "
				    + " WHERE i.activityType = 'payment' AND "
				    + " i.type NOT LIKE '"
				    + AccountingModelManager.typeSanXuat
				    + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				    + " i.startDate >= '"
				    + dateStart
				    + "' AND "
				    + " i.startDate <= '"
				    + dateEnd
				    + "' AND "
				    + " i.tableCode = '"
				    + conds.get("location2").toString()
				    + "' "
				    + " GROUP BY `InvoiceCode`) as `c` "
				    + " JOIN "
				    + " (SELECT "
				    + " concat(i.invoiceCode,'--',i.invoiceName) AS `InvoiceCode1`,	"
				    + " SUM(t.total) AS `totalR`, "
				    + " i.activityType AS `type2` "
				    + " FROM InvoiceTransaction t JOIN InvoiceDetail i ON i.id = t.invoiceId "
				    + " WHERE t.activityType = 'Payment' "
				    + " AND t.type NOT LIKE '"
				    + AccountingModelManager.typeSanXuat
				    + "' " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				    + " AND t.transactionDate >= '"
				    + dateStart
				    + "' "
				    + " AND t.transactionDate <= '"
				    + dateEnd
				    + "' "
				    + " AND t.tableCode = '"
				    + conds.get("location2").toString()
				    + "' "
				    + " GROUP BY `InvoiceCode`) as `d` on d.InvoiceCode1 = c.InvoiceCode))) AS `e` ORDER BY `node` DESC ");
				rQuery.field(" e.InvoiceCode as `node` ", "node");
				rQuery
				    .field("( CASE WHEN e.type1 = 'Receipt' THEN e.finalR ELSE 0 END) AS `sumFinalCharge` ", "sumFinalCharge");
				rQuery.field("( CASE WHEN e.type2 = 'Payment' THEN e.totalR ELSE 0 END) AS `sumTotalTransactionPyament` ",
				    "sumTotalTransactionPyament");
				rQuery.field("( CASE WHEN e.type2 = 'Receipt' THEN e.totalR ELSE 0 END) AS `sumTotalTransactionReceipt` ",
				    "sumTotalTransactionReceipt");
				rQuery.field("( CASE WHEN e.type1 = 'Payment' THEN e.finalR ELSE 0 END) AS `sumTotalTransaction` ",
				    "sumTotalTransaction");
			} else {
				rQuery.table("((SELECT * FROM " + " ((SELECT " + " concat(i.invoiceCode,'--',i.invoiceName) AS `InvoiceCode`,	"
				    + " SUM(i.finalCharge) AS `finalR`, " + " i.activityType AS `type1` " + " FROM InvoiceDetail i "
				    + " WHERE i.activityType = 'Receipt' " + " AND i.type NOT LIKE '"
				    + AccountingModelManager.typeSanXuat
				    + "' " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				    + " AND i.startDate >= '"
				    + dateStart
				    + "' "
				    + " AND i.startDate <= '"
				    + dateEnd
				    + "' "
				    + " AND i."+value+" '"
				    + parent
				    + "' "
				    + " GROUP BY `InvoiceCode`) as `a` "
				    + " JOIN "
				    + " (SELECT "
				    + "	concat(i.invoiceCode,'--',i.invoiceName) AS `InvoiceCode1`, "
				    + " SUM(t.total) AS `totalR`, "
				    + " t.ActivityType AS `type2` "
				    + " FROM InvoiceTransaction t JOIN InvoiceDetail i ON i.id = t.invoiceId "
				    + " WHERE t.activityType = 'Receipt' AND "
				    + " t.type NOT LIKE '"
				    + AccountingModelManager.typeSanXuat
				    + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				    + " t.transactionDate >= '"
				    + dateStart
				    + "' AND "
				    + " t.transactionDate <= '"
				    + dateEnd
				    + "' AND "
				    + " t."+value+" '"
				    + parent
				    + "' "
				    + " GROUP BY `InvoiceCode`) AS `b` on b.InvoiceCode1 = a.InvoiceCode ))"
				    + " UNION ALL "
				    + " (SELECT * FROM "
				    + " ((SELECT "
				    + " concat(i.invoiceCode,'--',i.invoiceName) AS `InvoiceCode`,	"
				    + " SUM(i.finalCharge) AS `finalR`, "
				    + " i.activityType AS `type1` "
				    + " FROM InvoiceDetail i "
				    + " WHERE i.activityType = 'payment' AND "
				    + " i.type NOT LIKE '"
				    + AccountingModelManager.typeSanXuat
				    + "' AND " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				    + " i.startDate >= '"
				    + dateStart
				    + "' AND "
				    + " i.startDate <= '"
				    + dateEnd
				    + "' AND "
				    + " i."+value+" = '"
				    + parent
				    + "' "
				    + " GROUP BY `InvoiceCode`) as `c` "
				    + " JOIN "
				    + " (SELECT "
				    + " concat(i.invoiceCode,'--',i.invoiceName) AS `InvoiceCode1`,	"
				    + " SUM(t.total) AS `totalR`, "
				    + " i.activityType AS `type2` "
				    + " FROM InvoiceTransaction t JOIN InvoiceDetail i ON i.id = t.invoiceId "
				    + " WHERE t.activityType = 'Payment' "
				    + " AND t.type NOT LIKE '"
				    + AccountingModelManager.typeSanXuat
				    + "' " // Add dieu kien 12/06/2015 - loai bo hoa don SX
				    + " AND t.transactionDate >= '"
				    + dateStart
				    + "' "
				    + " AND t.transactionDate <= '"
				    + dateEnd
				    + "' "
				    + " AND t."+value+" = '"
				    + parent
				    + "' "
				    + " GROUP BY `InvoiceCode`) as `d` on d.InvoiceCode1 = c.InvoiceCode))) AS `e` ORDER BY `node` DESC ");
				rQuery.field(" e.InvoiceCode as `node` ", "node");
				rQuery
				    .field("( CASE WHEN e.type1 = 'Receipt' THEN e.finalR ELSE 0 END) AS `sumFinalCharge` ", "sumFinalCharge");
				rQuery.field("( CASE WHEN e.type2 = 'Payment' THEN e.totalR ELSE 0 END) AS `sumTotalTransactionPyament` ",
				    "sumTotalTransactionPyament");
				rQuery.field("( CASE WHEN e.type2 = 'Receipt' THEN e.totalR ELSE 0 END) AS `sumTotalTransactionReceipt` ",
				    "sumTotalTransactionReceipt");
				rQuery.field("( CASE WHEN e.type1 = 'Payment' THEN e.finalR ELSE 0 END) AS `sumTotalTransaction` ",
				    "sumTotalTransaction");
			}

			System.out.println(rQuery.createSQLQuery());
			ReportTable[] reportTable = accountingServiceClient.reportQuery(new SQLSelectQuery[] { rQuery });
			String[] field = new String[] { "node", "sumFinalCharge", "sumTotalTransaction", "sumTotalTransactionReceipt",
			    "sumTotalTransactionPyament" };
			reportTable[0].dump(new String[] { "node", "sumFinalCharge", "sumTotalTransaction", "sumTotalTransactionReceipt",
			    "sumTotalTransactionPyament" });
			List<Map<String, Object>> records = reportTable[0].getRecords();
			for (int i = 0; i < records.size(); i++) {
				Map<String, Object> record = records.get(i);
				Object[] values = new Object[field.length];
				for (int j = 0; j < field.length; j++) {
					if (field[j].toString().equals("node")) {
						String str = record.get(field[j]).toString();
						if (str.indexOf(":") > 0) {
							values[j] = str.substring(str.indexOf(":") + 1);
						} else {
							values[j] = record.get(field[j]);
						}
					} else {
						values[j] = record.get(field[j]);
					}

				}

				double sumFinalCharge;
				double sumTotalTransaction;
				double sumTotalTransactionReceipt;
				double sumTotalTransactionPyament;
				double sumTotalReceivables;// phải thu
				double sumTotalPayable;// phải chi

				if (viewMoney.equals("Nghìn")) {
					// tổng thu, thực thu
					sumFinalCharge = Double.parseDouble(values[1].toString()) / 1000;
					sumTotalTransaction = Double.parseDouble(values[3].toString()) / 1000;
					// tổng chi, thực chi
					sumTotalTransactionReceipt = Double.parseDouble(values[2].toString()) / 1000;
					sumTotalTransactionPyament = Double.parseDouble(values[4].toString()) / 1000;
					// phải thu, phải chi
					sumTotalReceivables = sumFinalCharge - sumTotalTransaction;
					sumTotalPayable = sumTotalTransactionReceipt - sumTotalTransactionPyament;
				} else if (viewMoney.equals("Triệu")) {
					sumFinalCharge = Double.parseDouble(values[1].toString()) / 1000000;
					sumTotalTransaction = Double.parseDouble(values[3].toString()) / 1000000;

					sumTotalTransactionReceipt = Double.parseDouble(values[2].toString()) / 1000000;
					sumTotalTransactionPyament = Double.parseDouble(values[4].toString()) / 1000000;
					// phải thu, phải chi
					sumTotalReceivables = sumFinalCharge - sumTotalTransaction;
					sumTotalPayable = sumTotalTransactionReceipt - sumTotalTransactionPyament;
				} else {
					sumFinalCharge = Double.parseDouble(values[1].toString());
					sumTotalTransaction = Double.parseDouble(values[3].toString());
					sumTotalTransactionReceipt = Double.parseDouble(values[2].toString());
					sumTotalTransactionPyament = Double.parseDouble(values[4].toString());
					// phải thu, phải chi
					sumTotalReceivables = sumFinalCharge - sumTotalTransaction;
					sumTotalPayable = sumTotalTransactionReceipt - sumTotalTransactionPyament;
				}

				double ratio = sumTotalTransaction - sumTotalTransactionPyament;
				ReportBankEntity n = new ReportBankEntity("Hóa đơn " + values[0].toString(), Double.toString(sumFinalCharge),
				    Double.toString(sumTotalTransactionReceipt), Double.toString(sumTotalTransaction),
				    Double.toString(sumTotalTransactionPyament), Double.toString(sumTotalReceivables),
				    Double.toString(sumTotalPayable), Double.toString(ratio), index + 1);
				if (parent.indexOf("/") > 0)
					n.setParent(parent + "/");
				else if (parent.indexOf(":") > 0)
					n.setParent(parent + ":");
				else
					n.setParent(parent);
				n.setType(Type.INVOICE);
				nodes.add(n);
			}
		}
		return nodes;
	}

	private List<ReportBankEntity> runQuery(SQLSelectQuery rQuery) throws Exception {
		List<ReportBankEntity> listObjects = new ArrayList<ReportBankEntity>();
		System.out.println(rQuery.createSQLQuery());
		ReportTable[] reportTable = accountingServiceClient.reportQuery(new SQLSelectQuery[] { rQuery });
		String[] field = new String[] { "node", "final", "total", "parent" };
		reportTable[0].dump(field);
		List<Map<String, Object>> records = reportTable[0].getRecords();
		for (int i = 0; i < records.size(); i++) {
			Map<String, Object> record = records.get(i);
			Object[] values = new Object[field.length];
			for (int j = 0; j < field.length; j++) {
				values[j] = record.get(field[j]);
			}

			if (values[3] != null) {
				ReportBankEntity object = new ReportBankEntity(values[0].toString(), values[1].toString(), values[2].toString(),
				    values[3].toString(), 0);
				object.setParent(values[3].toString());
				listObjects.add(object);
			} else {
				ReportBankEntity object = new ReportBankEntity(values[0].toString(), values[1].toString(), values[2].toString(), "",
				    0);
				object.setParent("");
				listObjects.add(object);
			}

		}
		return listObjects;
	}

	@Override
	public Object getCellEditorValue() {
		return cell.getObject();
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
	    int row, int column) {
		cell.setRow(row);
		cell.setObject(value);
		if (column == 0) {
			JLabel labelIcon = new JLabel(" ");
			cell.setIcon(labelIcon);
			String result = " ";
			((ReportBankEntity) value).setLabelIcon(labelIcon);
			try {
				int index = ((ReportBankEntity) value).getIndex();
				if (((ReportBankEntity) value).isWiden())
					labelIcon.setIcon(nodeIconMulti);
				else
					labelIcon.setIcon(nodeIconAdd);

				for (int l = 0; l < index; l++) {
					result = result + "      ";
				}

//				if (((ReportBankEntity) value).getType().equals(Type.CHILD)) {
//					if (conds.containsKey("product1"))
//						labelIcon.setIcon(nodeIconProduct);
//					else {
//						if (conds.containsKey("location1")) {
//							// Set Icon cho bàn nếu muốn if(...)
//						} else {
//							labelIcon.setIcon(nodeIconPerson); // Set Icon cho đối tác hoặc
//																								 // nhân viên
//						}
//					}
//				}
//				if (((ReportBankEntity) value).getType().equals(Type.INVOICE))
//					labelIcon.setIcon(nodeIconInvoice);

				labelIcon.setText(result);
				cell.getText().setText(value.toString());

			} catch (Exception e) {
				e.printStackTrace();
				cell.getText().setText("");
			}
		} else if (column == 1){
			cell.getText().setHorizontalAlignment(JLabel.LEFT);
				cell.getText().setText(value.toString());
		} else {
			cell.getText().setHorizontalAlignment(JLabel.RIGHT);
			cell.getText().setText(value.toString());
		}

		if (table.getSelectedRow() == row) {
			cell.getText().setFont(new Font(cell.getFont().getFontName(), Font.BOLD, 13));
		} else {
			cell.getText().setFont(new Font(cell.getFont().getFontName(), Font.PLAIN, 14));
		}

		return cell;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		cell.setRow(row);
		cell.setObject(value);
		final Object c = value;
		if (column < 4) {
			JLabel labelIcon = ((ReportBankEntity) value).getLabelIcon();
			try {
				processing.setVisible(true);
				new SwingWorker<Void, Void>() {
					@Override
					protected Void doInBackground() throws Exception {
						mouseClick_CellTable();
						return null;
					};

					@Override
					protected void done() {
						processing.setVisible(false);
						List<ReportBankEntity> list = ((ReportBankEntity) c).getListChild();
						DefaultPieDataset dataset = new DefaultPieDataset();
						if (typeReport.equals("Doanh thu") || typeReport.equals("Chi phí")) {
							String type = model.getReportStatistics().getColumnViewPie().toString();
							if (type.equals("Tổng thu") || type.equals("Tổng chi")) {
								for (ReportBankEntity r : list) {
									Double d = (new MyDouble(r.getColumn1()).doubleValue()
									    / new MyDouble(((ReportBankEntity) c).getColumn1()).doubleValue() + new MyDouble(r.getColumn1())
									    .doubleValue() % new MyDouble(((ReportBankEntity) c).getColumn1()).doubleValue()) * 100;
									dataset.setValue(r.getNode(), d);
								}
							} else {
								if (type.equals("Thực thu") || type.equals("Thực chi")) {
									for (ReportBankEntity r : list) {
										Double d = (new MyDouble(r.getColumn2()).doubleValue()
										    / new MyDouble(((ReportBankEntity) c).getColumn2()).doubleValue() + new MyDouble(r.getColumn2())
										    .doubleValue() % new MyDouble(((ReportBankEntity) c).getColumn2()).doubleValue()) * 100;
										dataset.setValue(r.getNode(), d);
									}
								} else {
									if (type.equals("Tỉ lệ(%)")) {
										for (ReportBankEntity r : list) {
											Double d = (new MyDouble(r.getColumn3()).doubleValue()
											    / new MyDouble(((ReportBankEntity) c).getColumn3()).doubleValue() + new MyDouble(r.getColumn3())
											    .doubleValue() % new MyDouble(((ReportBankEntity) c).getColumn3()).doubleValue()) * 100;
											dataset.setValue(r.getNode(), d);
										}
									}
								}
							}
						}
						if (typeReport.equals("Thu - Chi - Lãi")) {
							String type = model.getReportStatistics().getColumnViewPie().toString();
							if (type.equals("Thực thu")) {
								for (ReportBankEntity r : list) {
									Double d = (new MyDouble(r.getColumn1()).doubleValue()
									    / new MyDouble(((ReportBankEntity) c).getColumn1()).doubleValue() + new MyDouble(r.getColumn1())
									    .doubleValue() % new MyDouble(((ReportBankEntity) c).getColumn1()).doubleValue()) * 100;
									dataset.setValue(r.getNode(), d);
								}
							} else {
								if (type.equals("Thực chi")) {
									for (ReportBankEntity r : list) {
										Double d = (new MyDouble(r.getColumn2()).doubleValue()
										    / new MyDouble(((ReportBankEntity) c).getColumn2()).doubleValue() + new MyDouble(r.getColumn2())
										    .doubleValue() % new MyDouble(((ReportBankEntity) c).getColumn2()).doubleValue()) * 100;
										dataset.setValue(r.getNode(), d);
									}
								} else {
									if (type.equals("Lãi(lỗ)")) {
										for (ReportBankEntity r : list) {
											Double d = (new MyDouble(r.getColumn3()).doubleValue()
											    / new MyDouble(((ReportBankEntity) c).getColumn3()).doubleValue() + new MyDouble(r.getColumn3())
											    .doubleValue() % new MyDouble(((ReportBankEntity) c).getColumn3()).doubleValue()) * 100;
											dataset.setValue(r.getNode(), d);
										}
									}
								}
							}
						}
						if (typeReport.equals("Thu - Chi - Công nợ")) {
							String type = model.getReportStatistics().getColumnViewPie().toString();
							if (type.equals("Tổng thu")) {
								for (ReportBankEntity r : list) {
									Double d = (new MyDouble(r.getColumn1()).doubleValue()
									    / new MyDouble(((ReportBankEntity) c).getColumn1()).doubleValue() + new MyDouble(r.getColumn1())
									    .doubleValue() % new MyDouble(((ReportBankEntity) c).getColumn1()).doubleValue()) * 100;
									dataset.setValue(r.getNode(), d);
								}
							} else {
								if (type.equals("Tổng chi")) {
									for (ReportBankEntity r : list) {
										Double d = (new MyDouble(r.getColumn2()).doubleValue()
										    / new MyDouble(((ReportBankEntity) c).getColumn2()).doubleValue() + new MyDouble(r.getColumn2())
										    .doubleValue() % new MyDouble(((ReportBankEntity) c).getColumn2()).doubleValue()) * 100;
										dataset.setValue(r.getNode(), d);
									}
								} else {
									if (type.equals("Thực thu")) {
										for (ReportBankEntity r : list) {
											Double d = (new MyDouble(r.getColumn3()).doubleValue()
											    / new MyDouble(((ReportBankEntity) c).getColumn3()).doubleValue() + new MyDouble(r.getColumn3())
											    .doubleValue() % new MyDouble(((ReportBankEntity) c).getColumn3()).doubleValue()) * 100;
											dataset.setValue(r.getNode(), d);
										}
									} else {
										if (type.equals("Thực chi")) {
											for (ReportBankEntity r : list) {
												Double d = (new MyDouble(r.getColumn4()).doubleValue()
												    / new MyDouble(((ReportBankEntity) c).getColumn4()).doubleValue() + new MyDouble(
												    r.getColumn4()).doubleValue()
												    % new MyDouble(((ReportBankEntity) c).getColumn4()).doubleValue()) * 100;
												dataset.setValue(r.getNode(), d);
											}
										} else {
											if (type.equals("Phải thu")) {
												for (ReportBankEntity r : list) {
													Double d = (new MyDouble(r.getColumn5()).doubleValue()
													    / new MyDouble(((ReportBankEntity) c).getColumn5()).doubleValue() + new MyDouble(
													    r.getColumn5()).doubleValue()
													    % new MyDouble(((ReportBankEntity) c).getColumn5()).doubleValue()) * 100;
													dataset.setValue(r.getNode(), d);
												}
											} else {
												if (type.equals("Phải chi")) {
													for (ReportBankEntity r : list) {
														Double d = (new MyDouble(r.getColumn6()).doubleValue()
														    / new MyDouble(((ReportBankEntity) c).getColumn6()).doubleValue() + new MyDouble(
														    r.getColumn6()).doubleValue()
														    % new MyDouble(((ReportBankEntity) c).getColumn6()).doubleValue()) * 100;
														dataset.setValue(r.getNode(), d);
													}
												} else {
													if (type.equals("Lãi(lỗ)")) {
														for (ReportBankEntity r : list) {
															Double d = (new MyDouble(r.getColumn7()).doubleValue()
															    / new MyDouble(((ReportBankEntity) c).getColumn7()).doubleValue() + new MyDouble(
															    r.getColumn7()).doubleValue()
															    % new MyDouble(((ReportBankEntity) c).getColumn7()).doubleValue()) * 100;
															dataset.setValue(r.getNode(), d);
														}
													}
												}
											}
										}
									}
								}
							}
						}

						model.getReportStatistics().viewChart(dataset);
					};
				}.execute();

				cell.setIcon(labelIcon);
			} catch (Exception e) {
				e.printStackTrace();
				cell.getText().setText("");
			}
		} else {
			cell.getText().setHorizontalAlignment(JLabel.RIGHT);
		}
		cell.getText().setText(value.toString());
		cell.getText().setFont(new Font(cell.getFont().getFontName(), Font.BOLD, 13));
		return cell;
	}

}