package com.hkt.swingexp.app.report.table;

import java.awt.Component;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
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

import org.jfree.data.general.DefaultPieDataset;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.AccountingServiceClient;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.hethong.Processing;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.module.accounting.entity.Invoice.ActivityType;
import com.hkt.module.core.entity.ReportTable;
import com.hkt.module.core.entity.SQLSelectQuery;
import com.hkt.swingexp.app.report.entity.PanelTableCell;
import com.hkt.swingexp.app.report.entity.ReportForTimeEntity;
import com.hkt.swingexp.app.report.entity.ReportForTimeEntity.Type;
import com.hkt.swingexp.app.report.modeltable.ModelTableForTime;

/**
 * @author Phan Anh
 * @date 01/09/2014
 * @editDate 23/01/2015
 */

public class TableRerenderForTime extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
	private PanelTableCell									cell;
	private ModelTableForTime								model;
	private HashMap<String, String>					conds;
	private String													viewMoney;
	private int															typeReport;
	private int															setView;
	private Processing											processing;
	private ImageIcon												nodeIconAdd, nodeImageMulti, nodeIconChild;
	private static ClientContext						clientContext						= ClientContext.getInstance();
	private static AccountingServiceClient	accountingServiceClient	= clientContext.getBean(AccountingServiceClient.class);
	private String dateFormat = "";

  public TableRerenderForTime(ModelTableForTime model, int typeReport, int setView, HashMap<String, String> hash, String viewMoney) {
		nodeIconAdd = new ImageIcon(HomeScreen.class.getResource("icon/square_add_16.png"));
		nodeImageMulti = new ImageIcon(HomeScreen.class.getResource("icon/square_multi_16.png"));
		nodeIconChild = new ImageIcon(HomeScreen.class.getResource("icon/Circle_Grey_16.png"));
  	
    this.model = model;
    this.conds = hash;
    this.viewMoney = viewMoney;
    this.typeReport = typeReport;
    this.setView = setView;
    
    this.cell = new PanelTableCell();
		this.processing = new Processing();

		System.out.println("LOOPPPPPPPPPPPPPPPPPPPPPPPP");
  }
  
	public void mouseClick_CellTable() {
		try {
			// ---------------START RUN TIME QUERY----------------
			long startTime = System.currentTimeMillis();
			// ---------------------------------------------------
			
			Object parent = cell.getObject();
			int index = 0;
			if (cell.getRow() < model.getRowCount() - 1) {
				index = ((ReportForTimeEntity) model.getValueAt(cell.getRow() + 1, 0)).getIndex();
			}
			try {
				ReportForTimeEntity rowCurrent = (ReportForTimeEntity) parent;
				if (index <= rowCurrent.getIndex()) {
					int i = cell.getRow() + 1;
					if (rowCurrent.getListChild() == null) {
						List<ReportForTimeEntity> listChilds = new ArrayList<ReportForTimeEntity>();
						listChilds = getRows(rowCurrent, ((ReportForTimeEntity) parent).getType());
						rowCurrent.setListChild(listChilds);
					}
					model.addRowChild(rowCurrent.getListChild(), i);
					rowCurrent.getLabelIcon().setIcon(nodeImageMulti);
					rowCurrent.setWiden(true);
				} else {
					model.removeRowReport(cell.getRow());
					rowCurrent.getLabelIcon().setIcon(nodeIconAdd);
					rowCurrent.setWiden(false);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// ---------------RETURN TIME EXECUTE QUERY------------------
			long endTime = System.currentTimeMillis();
			NumberFormat formatter = new DecimalFormat("#0.00000");
			String time = formatter.format((endTime - startTime) / 1000d);
			System.out.println("###$$$ TIME RUN QUERY: " + time);
			// -----------------------------------------------------------
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Quá trình thao tác phát sinh lỗi!" + ex.toString());
		}
	}
  
  /**
   * @param activityType - Truyền loại hóa đơn
   * @param parameter - Phân biệt loại thống kê
   */
  private SQLSelectQuery SQLQueryString_GetYear(ActivityType activityType, String parameter){
  	SQLSelectQuery rQuery = new SQLSelectQuery();
  	/**
  	 ****************************************
  	 **************************************** 
  	 *** THỐNG KÊ [DOANH THU] + [CHI PHÍ] ***
  	 ****************************************
  	 **************************************** 
  	 */
  	if(setView !=2 && setView !=3)
  	{
  	  	if(activityType != null){
  	  		if(conds.containsKey("product1")){
  	  			String addQuery = "";
  					if (!conds.get("product1").toString().equals("")) {
  						addQuery = " INNER JOIN product p ON p.code = i.productCode INNER JOIN product_productTag t ON t.productId = p.Id INNER JOIN warehouse_productTag w ON w.id = t.productTagId ";
  						rQuery.cond("w.code = '" + conds.get("product1").toString() + "'");
  					}
  	  			if (!conds.get("product2").toString().equals(""))
  	  				rQuery.cond("i.productCode = '" + conds.get("product2").toString() + "'");
  	  			else rQuery.cond("i.productCode IS NOT NULL");
  	  			rQuery.table("invoiceItem i" + addQuery)
  	  						.field("EXTRACT(YEAR FROM i.startDate) AS `node`", "node")
  		  					.field("COALESCE(SUM(i.finalCharge), 0) AS `final`", "final")
  		  					.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
  		  					.cond("i.activityType = '" + activityType + "'")
  		  					.groupBy("`node` DESC");
  	    	} else {
  	    		String addQuery = "";
  	      	if(conds.containsKey("department") && !conds.get("department").toString().isEmpty()){
  	      		addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("department").toString() +"'";
  	      	} else {
  	      		if(conds.containsKey("cashier1")){
  	      			if(!conds.get("cashier1").toString().isEmpty())     				
  	      				addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("cashier1").toString() +"'";
  	      			if(!conds.get("cashier2").toString().isEmpty())
  	      				addQuery = addQuery + " AND i.employeeCode = '"+ conds.get("cashier2").toString() +"'";
  	      		} else {
  	      			if(conds.containsKey("partner1")){
  	      				if(!conds.get("partner1").toString().isEmpty())     				
  	        				addQuery = " AND SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '"+ conds.get("partner1").toString() +"'";
  	        			if(!conds.get("partner2").toString().isEmpty())
  	        				addQuery = addQuery + " AND i.customerCode = '"+ conds.get("partner2").toString() +"'";
  	      			} else {
  	      				if(conds.containsKey("location1")){
  	      					if(!conds.get("location1").toString().isEmpty())     				
  	          				addQuery = " AND i.locationCode = '"+ conds.get("location1").toString() +"'";
  	          			if(!conds.get("location2").toString().isEmpty())
  	          				addQuery = addQuery + " AND i.tableCode = '"+ conds.get("location2").toString() +"'";
  	      				}
  	      				else
  	      				{
  	      					if(conds.containsKey("store") && !conds.get("store").toString().isEmpty())
  	      						addQuery = " AND SUBSTRING_INDEX(i.storeCode, '/', -1) = '"+ conds.get("store").toString() +"'";
  	      					else
  	      					{
  	      						if(conds.containsKey("project") && !conds.get("project").toString().isEmpty())
  	      				      		addQuery = " AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"+ conds.get("project").toString() +"'";  	      				      	
  	      					}
  	      				}
  	      			}
  	      		}
  	      	}
  	      	rQuery.table("(SELECT "
  	      			+ "a.node AS `node1`, b.node AS `node2`, a.final, b.total "
  	      			+ "FROM "
  	      			+ "((SELECT EXTRACT(YEAR FROM i.startDate) AS `node`, COALESCE(SUM(i.finalCharge), 0) AS `final` " 
  											+ "FROM invoiceDetail i "
  											+ "WHERE i.activityType = '" + activityType + "' AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` "
  												+ "LEFT JOIN "
  											+ "(SELECT EXTRACT(YEAR FROM i.transactionDate) AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
  											+ "FROM invoiceTransaction i "
  											+ "WHERE i.activityType = '" + activityType + "' AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node)"
  													+ "UNION "
  													+ "(SELECT "
  								  	      			+ "a.node AS `node1`, b.node AS `node2`, a.final, b.total "
  								  	      			+ "FROM "
  													+ "(SELECT EXTRACT(YEAR FROM i.startDate) AS `node`, COALESCE(SUM(i.finalCharge), 0) AS `final` " 
  		  											+ "FROM invoiceDetail i "
  		  											+ "WHERE i.activityType = '" + activityType + "' AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` "
  		  												+ "RIGHT JOIN "
  		  											+ "(SELECT EXTRACT(YEAR FROM i.transactionDate) AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
  		  											+ "FROM invoiceTransaction i "
  		  											+ "WHERE i.activityType = '" + activityType + "' AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node))"
  		  													+ "AS `e` ORDER BY `node` DESC")
  		            .field("(CASE WHEN (e.node1 IS NULL) THEN e.node2 ELSE e.node1 END) AS `node`", "node")
  		            .field("COALESCE(e.final, 0) AS `totalReceipt`", "final")
  		            .field("COALESCE(e.total, 0) AS `totalPayment`", "total");
  	    	}
  	  	} 
  	  	/**
  	  	 ************************************************** 
  	  	 ************************************************** 
  	  	 *** THỐNG KÊ [THU CHI LÃI] + [THU CHI CÔNG NỢ] ***
  	  	 **************************************************
  	  	 ************************************************** 
  	  	 */
  	  	else {
  	  		if(parameter.equals("ThuChiLai")){
  	  			if(conds.containsKey("product1")){  	  				
  	  				String addQuery = "";
  	  				if (!conds.get("product1").toString().equals("")) {
  							addQuery = "INNER JOIN product p ON p.code = i.productCode INNER JOIN product_productTag t ON t.productId = p.Id INNER JOIN warehouse_productTag w ON w.id = t.productTagId ";
  							rQuery.cond("w.code = '" + conds.get("product1").toString() + "'");
  	  				}
  		  			if (!conds.get("product2").toString().equals(""))
  		  				rQuery.cond("i.productCode = '" + conds.get("product2").toString() + "'");
  		  			rQuery.table("InvoiceItem i " + addQuery)
  		  			 			.field("EXTRACT(YEAR FROM i.startDate) AS `node`", "node")
  		  			 			.field("SUM(CASE WHEN i.activityType = 'Receipt' THEN (i.finalCharge * i.currencyRate) ELSE 0 END) AS `finalReceipt`", "final")
  		  			 			.field("SUM(CASE WHEN i.activityType = 'Payment' THEN (i.finalCharge * i.currencyRate) ELSE 0 END) AS `finalPayment`", "total")
  		  						.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
  		  			 			.cond("i.productCode IS NOT NULL")
  		  			 			.groupBy("`node` DESC");
  	    		} else {
  	    			String addQuery = "";
  	        	if(conds.containsKey("department") && !conds.get("department").toString().isEmpty()){
  	        		addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("department").toString() +"'";
  	        	} else {
  	        		if(conds.containsKey("cashier1")){
  	        			if(!conds.get("cashier1").toString().isEmpty())     				
  	        				addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("cashier1").toString() +"'";
  	        			if(!conds.get("cashier2").toString().isEmpty())
  	        				addQuery = addQuery + " AND i.employeeCode = '"+ conds.get("cashier2").toString() +"'";
  	        		} else {
  	        			if(conds.containsKey("partner1")){
  	        				if(!conds.get("partner1").toString().isEmpty())     				
  	          				addQuery = " AND SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '"+ conds.get("partner1").toString() +"'";
  	          			if(!conds.get("partner2").toString().isEmpty())
  	          				addQuery = addQuery + " AND i.customerCode = '"+ conds.get("partner2").toString() +"'";
  	        			} else {
  	        				if(conds.containsKey("location1")){
  	        					if(!conds.get("location1").toString().isEmpty())     				
  	            				addQuery = " AND i.locationCode = '"+ conds.get("location1").toString() +"'";
  	            			if(!conds.get("location2").toString().isEmpty())
  	            				addQuery = addQuery + " AND i.tableCode = '"+ conds.get("location2").toString() +"'";
  	        				}
  	        				else
  	        				{
  	        					if(conds.containsKey("store") && !conds.get("store").toString().isEmpty())
  	        						addQuery = " AND SUBSTRING_INDEX(i.storeCode, '/', -1) = '"+ conds.get("store").toString() +"'";
  	        					else
  	        					{
  	        						if(conds.containsKey("project") && !conds.get("project").toString().isEmpty())
  	        			        		addQuery = " AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"+ conds.get("project").toString() +"'";
  	        			        	
  	        					}
  	        				}
  	        			}
  	        		}
  	        	}   	
  	      	rQuery.table("((SELECT " +
  												     "a.node AS `node1`, " +
  												     "a.final AS `final`, " +
  												     "b.node AS `node2`, " +
  												     "b.total AS `total` " +
  									       "FROM " +
  									       		 "((SELECT EXTRACT(YEAR FROM i.transactionDate) AS `node`, COALESCE(SUM(i.total), 0) AS `final` "
  									       		 + "FROM invoiceTransaction i "
  									       		 + "WHERE i.activityType = '" + ActivityType.Receipt + "' AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` " + 
  									       		 "RIGHT JOIN " + 
  									       		 "(SELECT EXTRACT(YEAR FROM i.transactionDate) AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
  									       		 + "FROM invoiceTransaction i "
  									       		 + "WHERE i.activityType = '" + ActivityType.Payment + "' AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node)) " + 
  									       "UNION " +
  									       "(SELECT " +
  												  	"a.node AS `node1`, " +
  												    "a.final AS `final`, " +
  												    "b.node AS `node2`, " +
  												    "b.total AS `total` " +
  												 "FROM " +
  												 		"((SELECT EXTRACT(YEAR FROM i.transactionDate) AS `node`, COALESCE(SUM(i.total), 0) AS `final` "
  												 		+ "FROM invoiceTransaction i "
  												 		+ "WHERE i.activityType = '" + ActivityType.Receipt + "' AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` " + 
  												 		"LEFT JOIN " + 
  									  				"(SELECT EXTRACT(YEAR FROM i.transactionDate) AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
  									  				+ "FROM invoiceTransaction i "
  									  				+ "WHERE i.activityType = '" + ActivityType.Payment + "' AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node))) AS `e` ORDER BY `node` DESC")
  				        .field("(CASE WHEN (e.node1 IS NULL) THEN e.node2 ELSE e.node1 END) AS `node`", "node")
  				        .field("COALESCE(e.final, 0) AS `totalReceipt`", "final")
  				        .field("COALESCE(e.total, 0) AS `totalPayment`", "total");
  	    		}	
  	  		} else {
  	  			if(parameter.equals("ThuChiCongNo")){
  	  				String addQuery = "";
  	        	if(conds.containsKey("department") && !conds.get("department").toString().isEmpty()){
  	        		addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("department").toString() +"' ";
  	        	} else {
  	        		if(conds.containsKey("cashier1")){
  	        			if(!conds.get("cashier1").toString().isEmpty())     				
  	        				addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("cashier1").toString() +"' ";
  	        			if(!conds.get("cashier2").toString().isEmpty())
  	        				addQuery = addQuery + " AND i.employeeCode = '"+ conds.get("cashier2").toString() +"'";
  	        		} else {
  	        			if(conds.containsKey("partner1")){
  	        				if(!conds.get("partner1").toString().isEmpty())     				
  	          				addQuery = " AND SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '"+ conds.get("partner1").toString() +"' ";
  	          			if(!conds.get("partner2").toString().isEmpty())
  	          				addQuery = addQuery + " AND i.customerCode = '"+ conds.get("partner2").toString() +"' ";
  	        			} else {
  	        				if(conds.containsKey("location1")){
  	        					if(!conds.get("location1").toString().isEmpty())     				
  	            				addQuery = " AND i.locationCode = '"+ conds.get("location1").toString() +"' ";
  	            			if(!conds.get("location2").toString().isEmpty())
  	            				addQuery = addQuery + " AND i.tableCode = '"+ conds.get("location2").toString() +"' ";
  	        				} else if(conds.containsKey("store") && !conds.get("store").toString().isEmpty())
  	        		        addQuery = " AND SUBSTRING_INDEX(i.storeCode, '/', -1) = '"+ conds.get("store").toString() +"' ";
  	        					else if(conds.containsKey("project") && !conds.get("project").toString().isEmpty())
  	        			      addQuery = " AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"+ conds.get("project").toString() +"' ";   	        				
  	        			}
  	        		}
  	        	}
  	    			rQuery.table("((SELECT " +
  	    					" a.node AS `node1`,b.node AS `node2`,a.finalReceipt AS `finalReceipt`,a.finalPayment AS `finalPayment`,b.totalReceipt AS `totalReceipt`,b.totalPayment AS `totalPayment`" +
  	    				"FROM ( "+
  		    											" (SELECT EXTRACT(YEAR FROM i.startDate) AS `node`, " +
  		    											"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.finalCharge ELSE 0 END) AS `finalReceipt`, " +
  		    											"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.finalCharge ELSE 0 END) AS `finalPayment` " +
  	    										 "FROM `invoiceDetail` i WHERE i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
  	    										 "GROUP BY `node`) AS `a` " +
  	    										 " LEFT JOIN " +
  	    										 "(SELECT " +
  	    										 		"EXTRACT(YEAR FROM i.transactionDate) AS `node`, " +
  	    										 		"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.total ELSE 0 END) AS `totalReceipt`, " +
  	    										 		"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.total ELSE 0 END) AS `totalPayment` " +
  	    										 "FROM `invoiceTransaction` i WHERE i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
  	    										 "GROUP BY `node`) AS `b` ON a.node = b.node) " +
  	    										 "ORDER BY node DESC) " +
  	    										 "UNION " + 
  	    										 "(SELECT  a.node AS `node1`,b.node AS `node2`,a.finalReceipt AS `finalReceipt`,a.finalPayment AS `finalPayment`,b.totalReceipt AS `totalReceipt`,b.totalPayment AS `totalPayment`"+
  	    										 "FROM ("+
  	    										" (SELECT EXTRACT(YEAR FROM i.startDate) AS `node`, " +
	    											"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.finalCharge ELSE 0 END) AS `finalReceipt`, " +
	    											"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.finalCharge ELSE 0 END) AS `finalPayment` " +
  										 "FROM `invoiceDetail` i WHERE i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
  										 "GROUP BY `node`) AS `a` " +
  										 " RIGHT JOIN " +
  										 "(SELECT " +
  										 		"EXTRACT(YEAR FROM i.transactionDate) AS `node`, " +
  										 		"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.total ELSE 0 END) AS `totalReceipt`, " +
  										 		"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.total ELSE 0 END) AS `totalPayment` " +
  										 "FROM `invoiceTransaction` i WHERE i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
  										 "GROUP BY `node`) AS `b` ON a.node = b.node) " +
  										 "ORDER BY node DESC)) " +
  										 "AS `e` ORDER BY `node` DESC"
  	    					)
  	    	        .field("(CASE WHEN (e.node1 IS NULL) THEN e.node2 ELSE e.node1 END) AS `node`", "node")
  	    	        .field("e.finalReceipt AS `final Receipt`", "final")
  	    	        .field("e.finalPayment AS `final Payment`", "total")
  	    	        .field("e.totalReceipt AS `total Receipt`", "final1")
  	    	        .field("e.totalPayment AS `total Payment`", "total1");
  	    		}
  	  		}
  	  	}
  	}
  	else
  	{
  	  	if(activityType != null){
  	  		if(conds.containsKey("product1")){
  	  			String addQuery = "";
  					if (!conds.get("product1").toString().equals("")) {
  						addQuery = " INNER JOIN product p ON p.code = ii.productCode INNER JOIN product_productTag t ON t.productId = p.Id INNER JOIN warehouse_productTag w ON w.id = t.productTagId ";
  						rQuery.cond("w.code = '" + conds.get("product1").toString() + "'");
  					}
  	  			if (!conds.get("product2").toString().equals(""))
  	  				rQuery.cond("ii.productCode = '" + conds.get("product2").toString() + "'");
  	  			else rQuery.cond("ii.productCode IS NOT NULL");
  	  			rQuery.table("invoiceItem ii INNER JOIN invoicedetail id on ii.invoiceCode = id.invoiceCode" + addQuery)
  	  						.field("EXTRACT(YEAR FROM id.shiftDate) AS `node`", "node")
  		  					.field("COALESCE(SUM(ii.finalCharge), 0) AS `final`", "final")
  		  					.cond("ii.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
  		  					.cond("ii.activityType = '" + activityType + "'")
  		  					.groupBy("`node` DESC");
  	    	} else {
  	    		String addQuery = "";
  	      	if(conds.containsKey("department") && !conds.get("department").toString().isEmpty()){
  	      		addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("department").toString() +"'";
  	      	} else {
  	      		if(conds.containsKey("cashier1")){
  	      			if(!conds.get("cashier1").toString().isEmpty())     				
  	      				addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("cashier1").toString() +"'";
  	      			if(!conds.get("cashier2").toString().isEmpty())
  	      				addQuery = addQuery + " AND i.employeeCode = '"+ conds.get("cashier2").toString() +"'";
  	      		} else {
  	      			if(conds.containsKey("partner1")){
  	      				if(!conds.get("partner1").toString().isEmpty())     				
  	        				addQuery = " AND SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '"+ conds.get("partner1").toString() +"'";
  	        			if(!conds.get("partner2").toString().isEmpty())
  	        				addQuery = addQuery + " AND i.customerCode = '"+ conds.get("partner2").toString() +"'";
  	      			} else {
  	      				if(conds.containsKey("location1")){
  	      					if(!conds.get("location1").toString().isEmpty())     				
  	          				addQuery = " AND i.locationCode = '"+ conds.get("location1").toString() +"'";
  	          			if(!conds.get("location2").toString().isEmpty())
  	          				addQuery = addQuery + " AND i.tableCode = '"+ conds.get("location2").toString() +"'";
  	      				}
  	      				else
  	      				{
  	      					if(conds.containsKey("store") && !conds.get("store").toString().isEmpty())
  	      						addQuery = " AND SUBSTRING_INDEX(i.storeCode, '/', -1) = '"+ conds.get("store").toString() +"'";
  	      					else
  	      					{
  	      						if(conds.containsKey("project") && !conds.get("project").toString().isEmpty())
  	      				      		addQuery = " AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"+ conds.get("project").toString() +"'";
  	      				      	
  	      					}
  	      				}
  	      			}
  	      		}
  	      	}
  	      	rQuery.table("((SELECT a.node AS `node1`, b.node AS `node2`, a.final, b.total "
  	      			+ "FROM "
  	      			+ "(SELECT EXTRACT(YEAR FROM i.shiftDate) AS `node`, COALESCE(SUM(i.finalCharge), 0) AS `final` " 
  	      			+ "FROM invoiceDetail i "
  					+ "WHERE i.activityType = '" + activityType + "' AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` "
  					+ "LEFT JOIN "
  					+ "(SELECT EXTRACT(YEAR FROM i.shiftDate) AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
  					+ "FROM invoiceTransaction i "
  					+ "WHERE i.activityType = '" + activityType + "' AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node) "
  					+ "UNION "
  					+ "(SELECT a.node AS `node1`, b.node AS `node2`, a.final, b.total "
  					+ "FROM "
  					+ "(SELECT EXTRACT(YEAR FROM i.shiftDate) AS `node`, COALESCE(SUM(i.finalCharge), 0) AS `final` " 
  					+ "FROM invoiceDetail i "
  					+ "WHERE i.activityType = '" + activityType + "' AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` "
  					+ "RIGHT JOIN "
  					+ "(SELECT EXTRACT(YEAR FROM i.shiftDate) AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
  					+ "FROM invoiceTransaction i "
  					+ "WHERE i.activityType = '" + activityType + "' AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node)) "
  					+ "AS `e` ORDER BY `node` DESC"
  	      			)
  		            .field("(CASE WHEN (e.node1 IS NULL) THEN e.node2 ELSE e.node1 END) AS `node`", "node")
  		            .field(" COALESCE(e.final, 0) AS `totalReceipt`", "final")
  		            .field(" COALESCE(e.total, 0) AS `totalPayment`", "total");
  	    	}
  	  	} 
  	  	/**
  	  	 ************************************************** 
  	  	 ************************************************** 
  	  	 *** THỐNG KÊ [THU CHI LÃI] + [THU CHI CÔNG NỢ] ***
  	  	 **************************************************
  	  	 ************************************************** 
  	  	 */
  	  	else {
  	  		if(parameter.equals("ThuChiLai")){
  	  			if(conds.containsKey("product1")){  	  				
  	  				String addQuery = "";
  	  				if (!conds.get("product1").toString().equals("")) {
  							addQuery = "INNER JOIN product p ON p.code = ii.productCode INNER JOIN product_productTag t ON t.productId = p.Id INNER JOIN warehouse_productTag w ON w.id = t.productTagId ";
  							rQuery.cond("w.code = '" + conds.get("product1").toString() + "'");
  	  				}
  		  			if (!conds.get("product2").toString().equals(""))
  		  				rQuery.cond("ii.productCode = '" + conds.get("product2").toString() + "'");
  		  			rQuery.table("InvoiceItem ii INNER JOIN invoicedetail id on ii.invoiceCode = id.invoiceCode " + addQuery)
  		  			 			.field("EXTRACT(YEAR FROM id.shiftDate) AS `node`", "node")
  		  			 			.field("SUM(CASE WHEN ii.activityType = 'Receipt' THEN (ii.finalCharge * ii.currencyRate) ELSE 0 END) AS `finalReceipt`", "final")
  		  			 			.field("SUM(CASE WHEN ii.activityType = 'Payment' THEN (ii.finalCharge * ii.currencyRate) ELSE 0 END) AS `finalPayment`", "total")
  		  						.cond("ii.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
  		  			 			.cond("ii.productCode IS NOT NULL")
  		  			 			.groupBy("`node` DESC");
  	    		} else {
  	    			String addQuery = "";
  	        	if(conds.containsKey("department") && !conds.get("department").toString().isEmpty()){
  	        		addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("department").toString() +"'";
  	        	} else {
  	        		if(conds.containsKey("cashier1")){
  	        			if(!conds.get("cashier1").toString().isEmpty())     				
  	        				addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("cashier1").toString() +"'";
  	        			if(!conds.get("cashier2").toString().isEmpty())
  	        				addQuery = addQuery + " AND i.employeeCode = '"+ conds.get("cashier2").toString() +"'";
  	        		} else {
  	        			if(conds.containsKey("partner1")){
  	        				if(!conds.get("partner1").toString().isEmpty())     				
  	          				addQuery = " AND SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '"+ conds.get("partner1").toString() +"'";
  	          			if(!conds.get("partner2").toString().isEmpty())
  	          				addQuery = addQuery + " AND i.customerCode = '"+ conds.get("partner2").toString() +"'";
  	        			} else {
  	        				if(conds.containsKey("location1")){
  	        					if(!conds.get("location1").toString().isEmpty())     				
  	            				addQuery = " AND i.locationCode = '"+ conds.get("location1").toString() +"'";
  	            			if(!conds.get("location2").toString().isEmpty())
  	            				addQuery = addQuery + " AND i.tableCode = '"+ conds.get("location2").toString() +"'";
  	        				} else if(conds.containsKey("store") && !conds.get("store").toString().isEmpty())
  	        						addQuery = " AND SUBSTRING_INDEX(i.storeCode, '/', -1) = '"+ conds.get("store").toString() +"'";
  	        					else if(conds.containsKey("project") && !conds.get("project").toString().isEmpty())
  	        			      addQuery = " AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"+ conds.get("project").toString() +"'";  	        				
  	        			}
  	        		}
  	        	}   	
  	      	rQuery.table("((SELECT " +
  												     "a.node AS `node1`, " +
  												     "a.final AS `final`, " +
  												     "b.node AS `node2`, " +
  												     "b.total AS `total` " +
  									       "FROM " +
  									       		 "((SELECT EXTRACT(YEAR FROM i.shiftDate) AS `node`, COALESCE(SUM(i.total), 0) AS `final` "
  									       		 + "FROM invoiceTransaction i "
  									       		 + "WHERE i.activityType = '" + ActivityType.Receipt + "' AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` " + 
  									       		 "RIGHT JOIN " + 
  									       		 "(SELECT EXTRACT(YEAR FROM i.shiftDate) AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
  									       		 + "FROM invoiceTransaction i "
  									       		 + "WHERE i.activityType = '" + ActivityType.Payment + "' AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node)) " + 
  									       "UNION " +
  									       "(SELECT " +
  												  	"a.node AS `node1`, " +
  												    "a.final AS `final`, " +
  												    "b.node AS `node2`, " +
  												    "b.total AS `total` " +
  												 "FROM " +
  												 		"((SELECT EXTRACT(YEAR FROM i.shiftDate) AS `node`, COALESCE(SUM(i.total), 0) AS `final` "
  												 		+ "FROM invoiceTransaction i "
  												 		+ "WHERE i.activityType = '" + ActivityType.Receipt + "' AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` " + 
  												 		"LEFT JOIN " + 
  									  				"(SELECT EXTRACT(YEAR FROM i.shiftDate) AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
  									  				+ "FROM invoiceTransaction i "
  									  				+ "WHERE i.activityType = '" + ActivityType.Payment + "' AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node))) AS `e` ORDER BY `node` DESC")
  				        .field("(CASE WHEN (e.node1 IS NULL) THEN e.node2 ELSE e.node1 END) AS `node`", "node")
  				        .field("COALESCE(e.final, 0) AS `totalReceipt`", "final")
  				        .field("COALESCE(e.total, 0) AS `totalPayment`", "total");
  	    		}	
  	  		} else {
  	  			if(parameter.equals("ThuChiCongNo")){
  	  				String addQuery = "";
  	        	if(conds.containsKey("department") && !conds.get("department").toString().isEmpty()){
  	        		addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("department").toString() +"' ";
  	        	} else {
  	        		if(conds.containsKey("cashier1")){
  	        			if(!conds.get("cashier1").toString().isEmpty())     				
  	        				addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("cashier1").toString() +"' ";
  	        			if(!conds.get("cashier2").toString().isEmpty())
  	        				addQuery = addQuery + " AND i.employeeCode = '"+ conds.get("cashier2").toString() +"'";
  	        		} else {
  	        			if(conds.containsKey("partner1")){
  	        				if(!conds.get("partner1").toString().isEmpty())     				
  	          				addQuery = " AND SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '"+ conds.get("partner1").toString() +"' ";
  	          			if(!conds.get("partner2").toString().isEmpty())
  	          				addQuery = addQuery + " AND i.customerCode = '"+ conds.get("partner2").toString() +"' ";
  	        			} else {
  	        				if(conds.containsKey("location1")){
  	        					if(!conds.get("location1").toString().isEmpty())     				
  	            				addQuery = " AND i.locationCode = '"+ conds.get("location1").toString() +"' ";
  	            			if(!conds.get("location2").toString().isEmpty())
  	            				addQuery = addQuery + " AND i.tableCode = '"+ conds.get("location2").toString() +"' ";
  	        				} else if(conds.containsKey("store") && !conds.get("store").toString().isEmpty())
		        		        addQuery = " AND SUBSTRING_INDEX(i.storeCode, '/', -1) = '"+ conds.get("store").toString() +"' ";
		        					else if(conds.containsKey("project") && !conds.get("project").toString().isEmpty())
		        						addQuery = " AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"+ conds.get("project").toString() +"' ";	
  	        			}
  	        		}
  	        	}
  	    			rQuery.table("((SELECT " +
  	    							"a.node AS `node1`,b.node AS `node2`,a.finalReceipt AS `finalReceipt`,a.finalPayment AS `finalPayment`,b.totalReceipt AS `totalReceipt`,b.totalPayment AS `totalPayment`"+
  	    							" FROM (" +
  		    											"(SELECT EXTRACT(YEAR FROM i.shiftDate) AS `node`, " +
  		    											"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.finalCharge ELSE 0 END) AS `finalReceipt`, " +
  		    											"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.finalCharge ELSE 0 END) AS `finalPayment` " +
  	    										 "FROM `invoiceDetail` i WHERE i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
  	    										 "GROUP BY `node`) AS `a` " +
  	    										 "LEFT JOIN " +
  	    										 "(SELECT " +
  	    										 		"EXTRACT(YEAR FROM i.shiftDate) AS `node`, " +
  	    										 		"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.total ELSE 0 END) AS `totalReceipt`, " +
  	    										 		"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.total ELSE 0 END) AS `totalPayment` " +
  	    										 "FROM `invoiceTransaction` i WHERE i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
  	    										 "GROUP BY `node`) AS `b` ON a.node = b.node) " +
  	    										 "ORDER BY node DESC)" +
  	    										 "UNION"+
  	    										"(SELECT " +
  	    	  	    							"a.node AS `node1`,b.node AS `node2`,a.finalReceipt AS `finalReceipt`,a.finalPayment AS `finalPayment`,b.totalReceipt AS `totalReceipt`,b.totalPayment AS `totalPayment`"+
  	    	  	    							" FROM (" +
  	    	  		    											"(SELECT EXTRACT(YEAR FROM i.shiftDate) AS `node`, " +
  	    	  		    											"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.finalCharge ELSE 0 END) AS `finalReceipt`, " +
  	    	  		    											"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.finalCharge ELSE 0 END) AS `finalPayment` " +
  	    	  	    										 "FROM `invoiceDetail` i WHERE i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
  	    	  	    										 "GROUP BY `node`) AS `a` " +
  	    	  	    										 "RIGHT JOIN " +
  	    	  	    										 "(SELECT " +
  	    	  	    										 		"EXTRACT(YEAR FROM i.shiftDate) AS `node`, " +
  	    	  	    										 		"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.total ELSE 0 END) AS `totalReceipt`, " +
  	    	  	    										 		"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.total ELSE 0 END) AS `totalPayment` " +
  	    	  	    										 "FROM `invoiceTransaction` i WHERE i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
  	    	  	    										 "GROUP BY `node`) AS `b` ON a.node = b.node) " +
  	    	  	    										 "ORDER BY node DESC))"+
  	    	  	    										 "AS `e` ORDER BY `node` DESC"
  	    					)
  	    	        .field("(CASE WHEN (e.node1 IS NULL) THEN e.node2 ELSE e.node1 END) AS `node`", "node")
  	    	        .field("e.finalReceipt AS `final Receipt`", "final")
  	    	        .field("e.finalPayment AS `final Payment`", "total")
  	    	        .field("e.totalReceipt AS `total Receipt`", "final1")
  	    	        .field("e.totalPayment AS `total Payment`", "total1");
  	    		}
  	  		}
  	  	}
  	}
  	
  	return rQuery;
  }
  
  private SQLSelectQuery SQLQueryString_GetMonth(ReportForTimeEntity objectParent, ActivityType activityType, String parameter){
  	SQLSelectQuery rQuery = new SQLSelectQuery();
  	/**
  	 ****************************************
  	 **************************************** 
  	 *** THỐNG KÊ [DOANH THU] + [CHI PHÍ] ***
  	 ****************************************
  	 **************************************** 
  	 */
  	if(setView !=2 && setView !=3){
  		if(activityType != null){
  	  		if(conds.containsKey("product1")){
  	  			String addQuery = "";
  					if (!conds.get("product1").toString().equals("")) {
  						addQuery = " INNER JOIN product p ON p.code = i.productCode INNER JOIN product_productTag t ON t.productId = p.Id INNER JOIN warehouse_productTag w ON w.id = t.productTagId ";
  						rQuery.cond("w.code = '" + conds.get("product1").toString() + "'");
  					}
  	  			if (!conds.get("product2").toString().equals(""))
  	  				rQuery.cond("i.productCode = '" + conds.get("product2").toString() + "'");
  	  			else rQuery.cond("i.productCode IS NOT NULL");
  	  			rQuery.table("invoiceItem i" + addQuery)
  	  						.field("DATE_FORMAT(i.startDate, '%Y-%m') AS `node`", "node")
  		  					.field("COALESCE(SUM(i.finalCharge), 0) AS `final`", "final")
  		  					.cond("i.activityType = '" + activityType + "'")
  		  					.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
  		  					.cond("EXTRACT(YEAR FROM i.startDate) = '"+ objectParent.getParent() +"'")
  		  					.groupBy("`node` DESC");
  	    	} else {
  	    		String addQuery = "";
  	      	if(conds.containsKey("department") && !conds.get("department").toString().isEmpty()){
  	      		addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("department").toString() +"'";
  	      	} else {
  	      		if(conds.containsKey("cashier1")){
  	      			if(!conds.get("cashier1").toString().isEmpty())     				
  	      				addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("cashier1").toString() +"'";
  	      			if(!conds.get("cashier2").toString().isEmpty())
  	      				addQuery = addQuery + " AND i.employeeCode = '"+ conds.get("cashier2").toString() +"'";
  	      		} else {
  	      			if(conds.containsKey("partner1")){
  	      				if(!conds.get("partner1").toString().isEmpty())     				
  	        				addQuery = " AND SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '"+ conds.get("partner1").toString() +"'";
  	        			if(!conds.get("partner2").toString().isEmpty())
  	        				addQuery = addQuery + " AND i.customerCode = '"+ conds.get("partner2").toString() +"'";
  	      			} else {
  	      				if(conds.containsKey("location1")){
  	      					if(!conds.get("location1").toString().isEmpty())     				
  	          				addQuery = " AND i.locationCode = '"+ conds.get("location1").toString() +"'";
  	          			if(!conds.get("location2").toString().isEmpty())
  	          				addQuery = addQuery + " AND i.tableCode = '"+ conds.get("location2").toString() +"'";
  	      				}else if(conds.containsKey("store") && !conds.get("store").toString().isEmpty())
	  	      			   addQuery = " AND SUBSTRING_INDEX(i.storeCode, '/', -1) = '"+ conds.get("store").toString() +"'";
	  	      			 else if(conds.containsKey("project") && !conds.get("project").toString().isEmpty())
	  	      				 addQuery = " AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"+ conds.get("project").toString() +"'";  	      				      	  	      					  	      				
  	      			}
  	      		}
  	      	}
  	      	rQuery.table("((SELECT a.node AS `node1`, b.node AS `node2`, a.final, b.total "
  	      			+ "FROM  "
  	      			+ "(SELECT DATE_FORMAT(i.startDate, '%Y-%m') AS `node`, COALESCE(SUM(i.finalCharge), 0) AS `final` " 
  					+ "FROM invoiceDetail i "
  					+ "WHERE i.activityType = '" + activityType + "' "
  					+ "AND EXTRACT(YEAR FROM i.startDate) = '"+ objectParent.getParent() +"' "
  					+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` "
  					+ "LEFT JOIN "
  					+ "(SELECT DATE_FORMAT(i.transactionDate, '%Y-%m') AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
  					+ "FROM invoiceTransaction i "
  					+ "WHERE i.activityType = '" + activityType + "' "
  					+ "AND EXTRACT(YEAR FROM i.transactionDate) = '"+ objectParent.getParent() +"' "
  					+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node) "
  					+ "UNION "
  					+ "(SELECT a.node AS `node1`, b.node AS `node2`, a.final, b.total "
  					+ "FROM  "
  	      			+ "(SELECT DATE_FORMAT(i.startDate, '%Y-%m') AS `node`, COALESCE(SUM(i.finalCharge), 0) AS `final` " 
  					+ "FROM invoiceDetail i "
  					+ "WHERE i.activityType = '" + activityType + "' "
  					+ "AND EXTRACT(YEAR FROM i.startDate) = '"+ objectParent.getParent() +"' "
  					+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` "
  					+ "RIGHT JOIN "
  					+ "(SELECT DATE_FORMAT(i.transactionDate, '%Y-%m') AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
  					+ "FROM invoiceTransaction i "
  					+ "WHERE i.activityType = '" + activityType + "' "
  					+ "AND EXTRACT(YEAR FROM i.transactionDate) = '"+ objectParent.getParent() +"' "
  					+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node)) "
  					+ "  AS `e` ORDER BY `node` DESC"
  					)
  		            .field("(CASE WHEN (e.node1 IS NULL) THEN e.node2 ELSE e.node1 END) AS `node`", "node")
  		            .field(" COALESCE(e.final, 0) AS `totalReceipt`", "final")
  		            .field(" COALESCE(e.total, 0) AS `totalPayment`", "total");
  	    	}
  	  	} 
  	  	/**
  	  	 ************************************************** 
  	  	 ************************************************** 
  	  	 *** THỐNG KÊ [THU CHI LÃI] + [THU CHI CÔNG NỢ] ***
  	  	 **************************************************
  	  	 ************************************************** 
  	  	 */
  	  	else {
  	  		if(parameter.equals("ThuChiLai")){
  	  			if(conds.containsKey("product1")){  				
  	  				String addQuery = "";
  	  				if (!conds.get("product1").toString().equals("")) {
  							addQuery = "INNER JOIN product p ON p.code = i.productCode INNER JOIN product_productTag t ON t.productId = p.Id INNER JOIN warehouse_productTag w ON w.id = t.productTagId ";
  							rQuery.cond("w.code = '" + conds.get("product1").toString() + "'");
  	  				}
  		  			if (!conds.get("product2").toString().equals(""))
  		  				rQuery.cond("i.productCode = '" + conds.get("product2").toString() + "'");
  		  			rQuery.table("InvoiceItem i " + addQuery)
  		  			 			.field("DATE_FORMAT(i.startDate, '%Y-%m') AS `node`", "node")
  		  			 			.field("SUM(CASE WHEN i.activityType = 'Receipt' THEN (i.finalCharge * i.currencyRate) ELSE 0 END) AS `finalReceipt`", "final")
  		  			 			.field("SUM(CASE WHEN i.activityType = 'Payment' THEN (i.finalCharge * i.currencyRate) ELSE 0 END) AS `finalPayment`", "total")
  		  						.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
  		  			 			.cond("i.productCode IS NOT NULL")
  		  						.cond("EXTRACT(YEAR FROM i.startDate) = '"+ objectParent.getParent() +"'")
  		  			 			.groupBy("`node` DESC");
  	    		} else {
  	    			String addQuery = "";
  	        	if(conds.containsKey("department") && !conds.get("department").toString().isEmpty()){
  	        		addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("department").toString() +"'";
  	        	} else {
  	        		if(conds.containsKey("cashier1")){
  	        			if(!conds.get("cashier1").toString().isEmpty())     				
  	        				addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("cashier1").toString() +"'";
  	        			if(!conds.get("cashier2").toString().isEmpty())
  	        				addQuery = addQuery + " AND i.employeeCode = '"+ conds.get("cashier2").toString() +"'";
  	        		} else {
  	        			if(conds.containsKey("partner1")){
  	        				if(!conds.get("partner1").toString().isEmpty())     				
  	          				addQuery = " AND SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '"+ conds.get("partner1").toString() +"'";
  	          			if(!conds.get("partner2").toString().isEmpty())
  	          				addQuery = addQuery + " AND i.customerCode = '"+ conds.get("partner2").toString() +"'";
  	        			} else {
  	        				if(conds.containsKey("location1")){
  	        					if(!conds.get("location1").toString().isEmpty())     				
  	            				addQuery = " AND i.locationCode = '"+ conds.get("location1").toString() +"'";
  	            			if(!conds.get("location2").toString().isEmpty())
  	            				addQuery = addQuery + " AND i.tableCode = '"+ conds.get("location2").toString() +"'";
  	        				}
  	        				else
  	        				{
  	        					if(conds.containsKey("store") && !conds.get("store").toString().isEmpty())
  	        		        		addQuery = " AND SUBSTRING_INDEX(i.storeCode, '/', -1) = '"+ conds.get("store").toString() +"'";
  	        					else
  	        					{
  	        						if(conds.containsKey("project") && !conds.get("project").toString().isEmpty())
  	        			        		addQuery = " AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"+ conds.get("project").toString() +"'";
  	        					}
  	        				}
  	        			}
  	        		}
  	        	}   	
  	      	rQuery.table("((SELECT " +
  												     "a.node AS `node1`, " +
  												     "a.final AS `final`, " +
  												     "b.node AS `node2`, " +
  												     "b.total AS `total` " +
  									       "FROM " +
  									       		 "((SELECT DATE_FORMAT(i.transactionDate, '%Y-%m') AS `node`, COALESCE(SUM(i.total), 0) AS `final` "
  									       		 + "FROM invoiceTransaction i "
  									       		 + "WHERE i.activityType = '" + ActivityType.Receipt + "' "
  									       		 + "AND EXTRACT(YEAR FROM i.transactionDate) = '"+ objectParent.getParent() +"' "
  									       		 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` " + 
  									       		 "LEFT JOIN " + 
  									       		 "(SELECT DATE_FORMAT(i.transactionDate, '%Y-%m') AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
  									       		 + "FROM invoiceTransaction i "
  									       		 + "WHERE i.activityType = '" + ActivityType.Payment + "' "
  									       		 + "AND EXTRACT(YEAR FROM i.transactionDate) = '"+ objectParent.getParent() +"' "
  									       		 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node)) " + 
  									       "UNION " +
  									       "(SELECT " +
  												  	"a.node AS `node1`, " +
  												    "a.final AS `final`, " +
  												    "b.node AS `node2`, " +
  												    "b.total AS `total` " +
  												 "FROM " +
  												 		"((SELECT DATE_FORMAT(i.transactionDate, '%Y-%m') AS `node`, COALESCE(SUM(i.total), 0) AS `final` "
  												 		+ "FROM invoiceTransaction i "
  												 		+ "WHERE i.activityType = '" + ActivityType.Receipt + "' "
  												 	  + "AND EXTRACT(YEAR FROM i.transactionDate) = '"+ objectParent.getParent() +"' "
  												 	  + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` " + 
  												 		"RIGHT JOIN " + 
  									  				"(SELECT DATE_FORMAT(i.transactionDate, '%Y-%m') AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
  									  				+ "FROM invoiceTransaction i "
  									  				+ "WHERE i.activityType = '" + ActivityType.Payment + "' "
  									  				+ "AND EXTRACT(YEAR FROM i.transactionDate) = '"+ objectParent.getParent() +"' "
  									  				+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node))) AS `e` ORDER BY `node` DESC")
  				        .field("(CASE WHEN (e.node1 IS NULL) THEN e.node2 ELSE e.node1 END) AS `node`", "node")
  				        .field("COALESCE(e.final, 0) AS `totalReceipt`", "final")
  				        .field("COALESCE(e.total, 0) AS `totalPayment`", "total");
  	    		}	
  	  		} else {
  	  			if(parameter.equals("ThuChiCongNo")){
  	  				String addQuery = "";
  	        	if(conds.containsKey("department") && !conds.get("department").toString().isEmpty()){
  	        		addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("department").toString() +"' ";
  	        	} else {
  	        		if(conds.containsKey("cashier1")){
  	        			if(!conds.get("cashier1").toString().isEmpty())     				
  	        				addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("cashier1").toString() +"' ";
  	        			if(!conds.get("cashier2").toString().isEmpty())
  	        				addQuery = " AND i.employeeCode = '"+ conds.get("cashier2").toString() +"'";
  	        		} else {
  	        			if(conds.containsKey("partner1")){
  	        				if(!conds.get("partner1").toString().isEmpty())     				
  	          				addQuery = " AND SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '"+ conds.get("partner1").toString() +"' ";
  	          			if(!conds.get("partner2").toString().isEmpty())
  	          				addQuery = " AND i.customerCode = '"+ conds.get("partner2").toString() +"' ";
  	        			} else {
  	        				if(conds.containsKey("location1")){
  	        					if(!conds.get("location1").toString().isEmpty())     				
  	            				addQuery = " AND i.locationCode = '"+ conds.get("location1").toString() +"' ";
  	            			if(!conds.get("location2").toString().isEmpty())
  	            				addQuery = " AND i.tableCode = '"+ conds.get("location2").toString() +"' ";
  	        				}
  	        				else
  	        				{
  	        					if(conds.containsKey("store") && !conds.get("store").toString().isEmpty())
  	        		        		addQuery = " AND SUBSTRING_INDEX(i.storeCode, '/', -1) = '"+ conds.get("storeCode").toString() +"' ";
  	        					else
  	        					{
  	        						if(conds.containsKey("project") && !conds.get("project").toString().isEmpty())
  	        			        		addQuery = " AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"+ conds.get("project").toString() +"'";
  	        					}
  	        				}
  	        			}
  	        		}
  	        	}
  	    			rQuery.table("((SELECT " +
  	    						"a.node AS `node1`,b.node AS `node2`,a.finalReceipt AS `finalReceipt`,a.finalPayment AS `finalPayment`,b.totalReceipt AS `totalReceipt`,b.totalPayment AS `totalPayment`"+
  	    							"FROM ("+
  		    											"(SELECT DATE_FORMAT(i.startDate, '%Y-%m') AS `node`, " +
  		    											"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.finalCharge ELSE 0 END) AS `finalReceipt`, " +
  		    											"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.finalCharge ELSE 0 END) AS `finalPayment` " +
  	    										 "FROM `invoiceDetail` i "
  	    										 + "WHERE EXTRACT(YEAR FROM i.startDate) = '"+ objectParent.getParent() +"' "
  	    										 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
  	    										 "GROUP BY `node`) AS `a` " +
  	    										 " LEFT JOIN " +
  	    										 "(SELECT " +
  	    										 		"DATE_FORMAT(i.transactionDate, '%Y-%m') AS `node`, " +
  	    										 		"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.total ELSE 0 END) AS `totalReceipt`, " +
  	    										 		"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.total ELSE 0 END) AS `totalPayment` " +
  	    										 "FROM `invoiceTransaction` i "
  	    										 + "WHERE EXTRACT(YEAR FROM i.transactionDate) = '"+ objectParent.getParent() +"' "
  	    										 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
  	    										 "GROUP BY `node`) AS `b` ON a.node = b.node) ORDER BY `node` DESC)"+
  	    										 "UNION"+
  	    										 "(SELECT  a.node AS `node1`,b.node AS `node2`,a.finalReceipt AS `finalReceipt`,a.finalPayment AS `finalPayment`,b.totalReceipt AS `totalReceipt`,b.totalPayment AS `totalPayment`"+
  	    										"FROM ("+
	    											"(SELECT DATE_FORMAT(i.startDate, '%Y-%m') AS `node`, " +
	    											"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.finalCharge ELSE 0 END) AS `finalReceipt`, " +
	    											"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.finalCharge ELSE 0 END) AS `finalPayment` " +
  										 "FROM `invoiceDetail` i "
  										 + "WHERE EXTRACT(YEAR FROM i.startDate) = '"+ objectParent.getParent() +"' "
  										 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
  										 "GROUP BY `node`) AS `a` " +
  										 " RIGHT JOIN " +
  										 "(SELECT " +
  										 		"DATE_FORMAT(i.transactionDate, '%Y-%m') AS `node`, " +
  										 		"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.total ELSE 0 END) AS `totalReceipt`, " +
  										 		"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.total ELSE 0 END) AS `totalPayment` " +
  										 "FROM `invoiceTransaction` i "
  										 + "WHERE EXTRACT(YEAR FROM i.transactionDate) = '"+ objectParent.getParent() +"' "
  										 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
  										 "GROUP BY `node`) AS `b` ON a.node = b.node) ORDER BY `node` DESC))"+
  										 "AS `e` ORDER BY `node` DESC"
  	    					)
  	    	        .field("(CASE WHEN (e.node1 IS NULL) THEN e.node2 ELSE e.node1 END) AS `node`", "node")
  	    	        .field("COALESCE(e.finalReceipt, 0) AS `final Receipt`", "final")
  	    	        .field("COALESCE(e.finalPayment, 0) AS `final Payment`", "total")
  	    	        .field("COALESCE(e.totalReceipt, 0) AS `total Receipt`", "final1")
  	    	        .field("COALESCE(e.totalPayment, 0) AS `total Payment`", "total1");
  	    		}
  	  		}
  	  	}
  	}
  	else{
  		if(activityType != null){
  	  		if(conds.containsKey("product1")){
  	  			String addQuery = "";
  					if (!conds.get("product1").toString().equals("")) {
  						addQuery = " INNER JOIN product p ON p.code = ii.productCode INNER JOIN product_productTag t ON t.productId = p.Id INNER JOIN warehouse_productTag w ON w.id = t.productTagId ";
  						rQuery.cond("w.code = '" + conds.get("product1").toString() + "'");
  					}
  	  			if (!conds.get("product2").toString().equals(""))
  	  				rQuery.cond("ii.productCode = '" + conds.get("product2").toString() + "'");
  	  			else rQuery.cond("ii.productCode IS NOT NULL");
  	  			rQuery.table("invoiceItem ii INNER JOIN invoicedetail id on ii.invoiceCode = id.invoiceCode" + addQuery)
  	  						.field("DATE_FORMAT(id.shiftDate, '%Y-%m') AS `node`", "node")
  		  					.field("COALESCE(SUM(ii.finalCharge), 0) AS `final`", "final")
  		  					.cond("ii.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
  		  					.cond("ii.activityType = '" + activityType + "'")
  		  					.cond("EXTRACT(YEAR FROM id.shiftDate) = '"+ objectParent.getParent() +"'")
  		  					.groupBy("`node` DESC");
  	    	} else {
  	    		String addQuery = "";
  	      	if(conds.containsKey("department") && !conds.get("department").toString().isEmpty()){
  	      		addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("department").toString() +"'";
  	      	} else {
  	      		if(conds.containsKey("cashier1")){
  	      			if(!conds.get("cashier1").toString().isEmpty())     				
  	      				addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("cashier1").toString() +"'";
  	      			if(!conds.get("cashier2").toString().isEmpty())
  	      				addQuery = addQuery + " AND i.employeeCode = '"+ conds.get("cashier2").toString() +"'";
  	      		} else {
  	      			if(conds.containsKey("partner1")){
  	      				if(!conds.get("partner1").toString().isEmpty())     				
  	        				addQuery = " AND SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '"+ conds.get("partner1").toString() +"'";
  	        			if(!conds.get("partner2").toString().isEmpty())
  	        				addQuery = addQuery + " AND i.customerCode = '"+ conds.get("partner2").toString() +"'";
  	      			} else {
  	      				if(conds.containsKey("location1")){
  	      					if(!conds.get("location1").toString().isEmpty())     				
  	          				addQuery = " AND i.locationCode = '"+ conds.get("location1").toString() +"'";
  	          			if(!conds.get("location2").toString().isEmpty())
  	          				addQuery = addQuery + " AND i.tableCode = '"+ conds.get("location2").toString() +"'";
  	      				}
  	      				else
  	      				{
  	      					if(conds.containsKey("store") && !conds.get("store").toString().isEmpty())
  	      			      		addQuery = " AND SUBSTRING_INDEX(i.storeCode, '/', -1) = '"+ conds.get("store").toString() +"'";
  	      					else
  	      					{
  	      						if(conds.containsKey("project") && !conds.get("project").toString().isEmpty()){
  	      				      		addQuery = " AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"+ conds.get("project").toString() +"'";
  	      				      	}
  	      					}
  	      				}
  	      			}
  	      		}
  	      	}
  	      	rQuery.table("((SELECT a.node AS `node1`, b.node AS `node2`, a.final, b.total "
  	      			+ "FROM "
  	      			+ "(SELECT DATE_FORMAT(i.shiftDate, '%Y-%m') AS `node`, COALESCE(SUM(i.finalCharge), 0) AS `final` " 
  					+ "FROM invoiceDetail i "
  					+ "WHERE i.activityType = '" + activityType + "' "
  					+ "AND EXTRACT(YEAR FROM i.shiftDate) = '"+ objectParent.getParent() +"' "
  					+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` "
  					+ "LEFT JOIN "
  					+ "(SELECT DATE_FORMAT(i.shiftDate, '%Y-%m') AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
  					+ "FROM invoiceTransaction i "
  					+ "WHERE i.activityType = '" + activityType + "' "
  					+ "AND EXTRACT(YEAR FROM i.shiftDate) = '"+ objectParent.getParent() +"' "
  					+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node) "
  					+ "UNION "
  					+ "(SELECT a.node AS `node1`, b.node AS `node2`, a.final, b.total "
  					+ "FROM "
  	      			+ "(SELECT DATE_FORMAT(i.shiftDate, '%Y-%m') AS `node`, COALESCE(SUM(i.finalCharge), 0) AS `final` " 
  					+ "FROM invoiceDetail i "
  					+ "WHERE i.activityType = '" + activityType + "' "
  					+ "AND EXTRACT(YEAR FROM i.shiftDate) = '"+ objectParent.getParent() +"' "
  					+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` "
  					+ "RIGHT JOIN "
  					+ "(SELECT DATE_FORMAT(i.shiftDate, '%Y-%m') AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
  					+ "FROM invoiceTransaction i "
  					+ "WHERE i.activityType = '" + activityType + "' "
  					+ "AND EXTRACT(YEAR FROM i.shiftDate) = '"+ objectParent.getParent() +"' "
  					+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node)) "
  					+ " AS `e` ORDER BY `node` DESC "
  					)
  		            .field("(CASE WHEN (e.node1 IS NULL) THEN e.node2 ELSE e.node1 END) AS `node`", "node")
  		            .field("COALESCE(e.final, 0) AS `totalReceipt`", "final")
  		            .field("COALESCE(e.total, 0) AS `totalPayment`", "total");
  	    	}
  	  	} 
  	  	/**
  	  	 ************************************************** 
  	  	 ************************************************** 
  	  	 *** THỐNG KÊ [THU CHI LÃI] + [THU CHI CÔNG NỢ] ***
  	  	 **************************************************
  	  	 ************************************************** 
  	  	 */
  	  	else {
  	  		if(parameter.equals("ThuChiLai")){
  	  			if(conds.containsKey("product1")){  				
  	  				String addQuery = "";
  	  				if (!conds.get("product1").toString().equals("")) {
  							addQuery = "INNER JOIN product p ON p.code = ii.productCode INNER JOIN product_productTag t ON t.productId = p.Id INNER JOIN warehouse_productTag w ON w.id = t.productTagId ";
  							rQuery.cond("w.code = '" + conds.get("product1").toString() + "'");
  	  				}
  		  			if (!conds.get("product2").toString().equals(""))
  		  				rQuery.cond("ii.productCode = '" + conds.get("product2").toString() + "'");
  		  			rQuery.table("InvoiceItem ii INNER JOIN invoicedetail id on ii.invoiceCode = id.invoiceCode " + addQuery)
  		  			 			.field("DATE_FORMAT(id.shiftDate, '%Y-%m') AS `node`", "node")
  		  			 			.field("SUM(CASE WHEN ii.activityType = 'Receipt' THEN (ii.finalCharge * ii.currencyRate) ELSE 0 END) AS `finalReceipt`", "final")
  		  			 			.field("SUM(CASE WHEN ii.activityType = 'Payment' THEN (ii.finalCharge * ii.currencyRate) ELSE 0 END) AS `finalPayment`", "total")
  		  						.cond("ii.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
  		  			 			.cond("ii.productCode IS NOT NULL")
  		  						.cond("EXTRACT(YEAR FROM id.shiftDate) = '"+ objectParent.getParent() +"'")
  		  			 			.groupBy("`node` DESC");
  	    		} else {
  	    			String addQuery = "";
  	        	if(conds.containsKey("department") && !conds.get("department").toString().isEmpty()){
  	        		addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("department").toString() +"'";
  	        	} else {
  	        		if(conds.containsKey("cashier1")){
  	        			if(!conds.get("cashier1").toString().isEmpty())     				
  	        				addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("cashier1").toString() +"'";
  	        			if(!conds.get("cashier2").toString().isEmpty())
  	        				addQuery = addQuery + " AND i.employeeCode = '"+ conds.get("cashier2").toString() +"'";
  	        		} else {
  	        			if(conds.containsKey("partner1")){
  	        				if(!conds.get("partner1").toString().isEmpty())     				
  	          				addQuery = " AND SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '"+ conds.get("partner1").toString() +"'";
  	          			if(!conds.get("partner2").toString().isEmpty())
  	          				addQuery = addQuery + " AND i.customerCode = '"+ conds.get("partner2").toString() +"'";
  	        			} else {
  	        				if(conds.containsKey("location1")){
  	        					if(!conds.get("location1").toString().isEmpty())     				
  	            				addQuery = " AND i.locationCode = '"+ conds.get("location1").toString() +"'";
  	            			if(!conds.get("location2").toString().isEmpty())
  	            				addQuery = addQuery + " AND i.tableCode = '"+ conds.get("location2").toString() +"'";
  	        				}
  	        				else
  	        				{
  	        					if(conds.containsKey("store") && !conds.get("store").toString().isEmpty())
  	        		        		addQuery = " AND SUBSTRING_INDEX(i.storeCode, '/', -1) = '"+ conds.get("store").toString() +"'";
  	        					else
  	        					{
  	        						if(conds.containsKey("project") && !conds.get("project").toString().isEmpty())
  	        			        		addQuery = " AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"+ conds.get("project").toString() +"'";
  	        					}
  	        				}
  	        			}
  	        		}
  	        	}   	
  	      	rQuery.table("((SELECT " +
  												     "a.node AS `node1`, " +
  												     "a.final AS `final`, " +
  												     "b.node AS `node2`, " +
  												     "b.total AS `total` " +
  									       "FROM " +
  									       		 "((SELECT DATE_FORMAT(i.shiftDate, '%Y-%m') AS `node`, COALESCE(SUM(i.total), 0) AS `final` "
  									       		 + "FROM invoiceTransaction i "
  									       		 + "WHERE i.activityType = '" + ActivityType.Receipt + "' "
  									       		 + "AND EXTRACT(YEAR FROM i.shiftDate) = '"+ objectParent.getParent() +"' "
  									       		 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` " + 
  									       		 "LEFT JOIN " + 
  									       		 "(SELECT DATE_FORMAT(i.shiftDate, '%Y-%m') AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
  									       		 + "FROM invoiceTransaction i "
  									       		 + "WHERE i.activityType = '" + ActivityType.Payment + "' "
  									       		 + "AND EXTRACT(YEAR FROM i.shiftDate) = '"+ objectParent.getParent() +"' "
  									       		 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'"+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node)) " + 
  									       "UNION " +
  									       "(SELECT " +
  												  	"a.node AS `node1`, " +
  												    "a.final AS `final`, " +
  												    "b.node AS `node2`, " +
  												    "b.total AS `total` " +
  												 "FROM " +
  												 		"((SELECT DATE_FORMAT(i.shiftDate, '%Y-%m') AS `node`, COALESCE(SUM(i.total), 0) AS `final` "
  												 		+ "FROM invoiceTransaction i "
  												 		+ "WHERE i.activityType = '" + ActivityType.Receipt + "' "
  												 		+ "AND EXTRACT(YEAR FROM i.shiftDate) = '"+ objectParent.getParent() +"' "
  												 		+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` " + 
  												 		"RIGHT JOIN " + 
  									  				"(SELECT DATE_FORMAT(i.shiftDate, '%Y-%m') AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
  									  				+ "FROM invoiceTransaction i "
  									  				+ "WHERE i.activityType = '" + ActivityType.Payment + "' "
  									  				+ "AND EXTRACT(YEAR FROM i.shiftDate) = '"+ objectParent.getParent() +"' "
  									  				+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node))) AS `e` ORDER BY `node` DESC")
  				        .field("(CASE WHEN (e.node1 IS NULL) THEN e.node2 ELSE e.node1 END) AS `node`", "node")
  				        .field("COALESCE(e.final, 0) AS `totalReceipt`", "final")
  				        .field("COALESCE(e.total, 0) AS `totalPayment`", "total");
  	    		}	
  	  		} else {
  	  			if(parameter.equals("ThuChiCongNo")){
  	  				String addQuery = "";
  	        	if(conds.containsKey("department") && !conds.get("department").toString().isEmpty()){
  	        		addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("department").toString() +"' ";
  	        	} else {
  	        		if(conds.containsKey("cashier1")){
  	        			if(!conds.get("cashier1").toString().isEmpty())     				
  	        				addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("cashier1").toString() +"' ";
  	        			if(!conds.get("cashier2").toString().isEmpty())
  	        				addQuery = " AND i.employeeCode = '"+ conds.get("cashier2").toString() +"'";
  	        		} else {
  	        			if(conds.containsKey("partner1")){
  	        				if(!conds.get("partner1").toString().isEmpty())     				
  	          				addQuery = " AND SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '"+ conds.get("partner1").toString() +"' ";
  	          			if(!conds.get("partner2").toString().isEmpty())
  	          				addQuery = " AND i.customerCode = '"+ conds.get("partner2").toString() +"' ";
  	        			} else {
  	        				if(conds.containsKey("location1")){
  	        					if(!conds.get("location1").toString().isEmpty())     				
  	            				addQuery = " AND i.locationCode = '"+ conds.get("location1").toString() +"' ";
  	            			if(!conds.get("location2").toString().isEmpty())
  	            				addQuery = " AND i.tableCode = '"+ conds.get("location2").toString() +"' ";
  	        				}
  	        				else
  	        				{
  	        					if(conds.containsKey("store") && !conds.get("store").toString().isEmpty())
  	        		        		addQuery = " AND SUBSTRING_INDEX(i.storeCode, '/', -1) = '"+ conds.get("storeCode").toString() +"' ";
  	        					else
  	        					{
  	        						if(conds.containsKey("project") && !conds.get("project").toString().isEmpty())
  	        			        		addQuery = " AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"+ conds.get("project").toString() +"'";
  	        					}
  	        				}
  	        			}
  	        		}
  	        	}
  	    			rQuery.table("((SELECT " +
  	        	"a.node AS `node1`,b.node AS `node2`,a.finalReceipt AS `finalReceipt`,a.finalPayment AS `finalPayment`,b.totalReceipt AS `totalReceipt`,b.totalPayment AS `totalPayment`"+
  	    					"FROM ((SELECT"+
  		    											"DATE_FORMAT(i.shiftDate, '%Y-%m') AS `node`, " +
  		    											"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.finalCharge ELSE 0 END) AS `finalReceipt`, " +
  		    											"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.finalCharge ELSE 0 END) AS `finalPayment` " +
  	    										 "FROM `invoiceDetail` i "
  	    										 + "WHERE EXTRACT(YEAR FROM i.shiftDate) = '"+ objectParent.getParent() +"' "
  	    										 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
  	    										 "GROUP BY `node`) AS `a` " +
  	    										 "LEFT JOIN " +
  	    										 "(SELECT " +
  	    										 		"DATE_FORMAT(i.shiftDate, '%Y-%m') AS `node`, " +
  	    										 		"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.total ELSE 0 END) AS `totalReceipt`, " +
  	    										 		"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.total ELSE 0 END) AS `totalPayment` " +
  	    										 "FROM `invoiceTransaction` i "
  	    										 + "WHERE EXTRACT(YEAR FROM i.shiftDate) = '"+ objectParent.getParent() +"' "
  	    										 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
  	    										 "GROUP BY `node`) AS `b` ON a.node = b.node) " +
  	    										 "ORDER BY `node` DESC)"+
  	    										 "UNION"+
  	    										 "( SELECT"+
  	    										"a.node AS `node1`,b.node AS `node2`,a.finalReceipt AS `finalReceipt`,a.finalPayment AS `finalPayment`,b.totalReceipt AS `totalReceipt`,b.totalPayment AS `totalPayment`"+
  	    			  	    					"FROM ((SELECT"+
  	    			  		    											"DATE_FORMAT(i.shiftDate, '%Y-%m') AS `node`, " +
  	    			  		    											"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.finalCharge ELSE 0 END) AS `finalReceipt`, " +
  	    			  		    											"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.finalCharge ELSE 0 END) AS `finalPayment` " +
  	    			  	    										 "FROM `invoiceDetail` i "
  	    			  	    										 + "WHERE EXTRACT(YEAR FROM i.shiftDate) = '"+ objectParent.getParent() +"' "
  	    			  	    										 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
  	    			  	    										 "GROUP BY `node`) AS `a` " +
  	    			  	    										 "RIGHT JOIN " +
  	    			  	    										 "(SELECT " +
  	    			  	    										 		"DATE_FORMAT(i.shiftDate, '%Y-%m') AS `node`, " +
  	    			  	    										 		"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.total ELSE 0 END) AS `totalReceipt`, " +
  	    			  	    										 		"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.total ELSE 0 END) AS `totalPayment` " +
  	    			  	    										 "FROM `invoiceTransaction` i "
  	    			  	    										 + "WHERE EXTRACT(YEAR FROM i.shiftDate) = '"+ objectParent.getParent() +"' "
  	    			  	    										 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
  	    			  	    										 "GROUP BY `node`) AS `b` ON a.node = b.node) " +
  	    			  	    										 "ORDER BY `node` DESC))"+
  	    			  	    										 " AS `e` ORDER BY `node` DESC"
  	    										 
  	    					)
  	    	        .field("(CASE WHEN (e.node1 IS NULL) THEN e.node2 ELSE e.node1 END) AS `node`", "node")
  	    	        .field("COALESCE(e.finalReceipt, 0) AS `final Receipt`", "final")
  	    	        .field("COALESCE(e.finalPayment, 0) AS `final Payment`", "total")
  	    	        .field("COALESCE(e.totalReceipt, 0) AS `total Receipt`", "final1")
  	    	        .field("COALESCE(e.totalPayment, 0) AS `total Payment`", "total1");
  	    		}
  	  		}
  	  	}
  	}
  	return rQuery;
  }
  
  private SQLSelectQuery SQLQueryString_GetDay(ReportForTimeEntity objectParent, ActivityType activityType, String parameter){
  	SQLSelectQuery rQuery = new SQLSelectQuery();
  	/**
  	 ****************************************
  	 **************************************** 
  	 *** THỐNG KÊ [DOANH THU] + [CHI PHÍ] ***
  	 ****************************************
  	 **************************************** 
  	 */
  	if(setView ==2 || setView == 3){
  		if(activityType != null){
  	  		if(conds.containsKey("product1")){
  	  			String addQuery = "";
  					if (!conds.get("product1").toString().equals("")) {
  						addQuery = " INNER JOIN product p ON p.code = ii.productCode INNER JOIN product_productTag t ON t.productId = p.Id INNER JOIN warehouse_productTag w ON w.id = t.productTagId ";
  						rQuery.cond("w.code = '" + conds.get("product1").toString() + "'");
  					}
  	  			if (!conds.get("product2").toString().equals(""))
  	  				rQuery.cond("ii.productCode = '" + conds.get("product2").toString() + "'");
  	  			else rQuery.cond("ii.productCode IS NOT NULL");
  	  			rQuery.table("invoiceItem ii INNER JOIN invoicedetail id on ii.invoiceCode = id.invoiceCode" + addQuery)
  	  						.field("DATE_FORMAT(id.shiftDate, '%Y-%m-%d') AS `node`", "node")
  		  					.field("COALESCE(SUM(ii.finalCharge), 0) AS `final`", "final")
  		  					.cond("ii.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
  		  					.cond("ii.activityType = '" + activityType + "'")
  		  					.cond("DATE_FORMAT(id.shiftDate, '%Y-%m') = '"+ objectParent.getParent() +"'")
  		  					.groupBy("`node` DESC");
  	    	} else {
  	    		String addQuery = "";
  	      	if(conds.containsKey("department") && !conds.get("department").toString().isEmpty()){
  	      		addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("department").toString() +"'";
  	      	} else {
  	      		if(conds.containsKey("cashier1")){
  	      			if(!conds.get("cashier1").toString().isEmpty())     				
  	      				addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("cashier1").toString() +"'";
  	      			if(!conds.get("cashier2").toString().isEmpty())
  	      				addQuery = addQuery + " AND i.employeeCode = '"+ conds.get("cashier2").toString() +"'";
  	      		} else {
  	      			if(conds.containsKey("partner1")){
  	      				if(!conds.get("partner1").toString().isEmpty())     				
  	        				addQuery = " AND SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '"+ conds.get("partner1").toString() +"'";
  	        			if(!conds.get("partner2").toString().isEmpty())
  	        				addQuery = addQuery + " AND i.customerCode = '"+ conds.get("partner2").toString() +"'";
  	      			} else {
  	      				if(conds.containsKey("location1")){
  	      					if(!conds.get("location1").toString().isEmpty())     				
  	          				addQuery = " AND i.locationCode = '"+ conds.get("location1").toString() +"'";
  	          			if(!conds.get("location2").toString().isEmpty())
  	          				addQuery = addQuery + " AND i.tableCode = '"+ conds.get("location2").toString() +"'";
  	      				}
  	      				else
  	      				{
  	      					if(conds.containsKey("store") && !conds.get("store").toString().isEmpty())
  	      			      		addQuery = " AND SUBSTRING_INDEX(i.storeCode, '/', -1) = '"+ conds.get("store").toString() +"'";
  	      					else
  	    					{
  	    						if(conds.containsKey("project") && !conds.get("project").toString().isEmpty())
  	    			        		addQuery = " AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"+ conds.get("project").toString() +"'";
  	    					}
  	      				}
  	      			}
  	      		}
  	      	}
  	      	rQuery.table("((SELECT a.node AS `node1`, b.node AS `node2`, a.final, b.total "
  	      			+ "FROM "
  	      			+ "(SELECT DATE_FORMAT(i.shiftDate, '%Y-%m-%d') AS `node`, COALESCE(SUM(i.finalCharge), 0) AS `final` " 
  					+ "FROM invoiceDetail i "
  					+ "WHERE i.activityType = '" + activityType + "' "
  					+ "AND DATE_FORMAT(i.shiftDate, '%Y-%m') = '"+ objectParent.getParent() +"' "
  					+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` "
  					+ "LEFT JOIN "
  					+ "(SELECT DATE_FORMAT(i.shiftDate, '%Y-%m-%d') AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
  					+ "FROM invoiceTransaction i "
  					+ "WHERE i.activityType = '" + activityType + "' "
  					+ "AND DATE_FORMAT(i.shiftDate, '%Y-%m') = '"+ objectParent.getParent() +"' "
  					+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node) "
  					+ "UNION "
  					+ "(SELECT a.node AS `node1`, b.node AS `node2`, a.final, b.total "
  					+ "FROM "
  	      			+ "(SELECT DATE_FORMAT(i.shiftDate, '%Y-%m-%d') AS `node`, COALESCE(SUM(i.finalCharge), 0) AS `final` " 
  					+ "FROM invoiceDetail i "
  					+ "WHERE i.activityType = '" + activityType + "' "
  					+ "AND DATE_FORMAT(i.shiftDate, '%Y-%m') = '"+ objectParent.getParent() +"' "
  					+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` "
  					+ "RIGHT JOIN "
  					+ "(SELECT DATE_FORMAT(i.shiftDate, '%Y-%m-%d') AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
  					+ "FROM invoiceTransaction i "
  					+ "WHERE i.activityType = '" + activityType + "' "
  					+ "AND DATE_FORMAT(i.shiftDate, '%Y-%m') = '"+ objectParent.getParent() +"' "
  					+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node)) "
  					+ " AS `e` ORDER BY `node` DESC"
  	      			)
  		            .field("(CASE WHEN (e.node1 IS NULL) THEN e.node2 ELSE e.node1 END) AS `node`", "node")
  		            .field("COALESCE(e.final, 0) AS `totalReceipt`", "final")
  		            .field("COALESCE(e.total, 0) AS `totalPayment`", "total");
  	    	}
  	  	} 
  	  	/**
  	  	 ************************************************** 
  	  	 ************************************************** 
  	  	 *** THỐNG KÊ [THU CHI LÃI] + [THU CHI CÔNG NỢ] ***
  	  	 **************************************************
  	  	 ************************************************** 
  	  	 */
  	  	else {
  	  		if(parameter.equals("ThuChiLai")){
  	  			if(conds.containsKey("product1")){  				
  	  				String addQuery = "";
  	  				if (!conds.get("product1").toString().equals("")) {
  							addQuery = "INNER JOIN product p ON p.code = ii.productCode INNER JOIN product_productTag t ON t.productId = p.Id INNER JOIN warehouse_productTag w ON w.id = t.productTagId ";
  							rQuery.cond("w.code = '" + conds.get("product1").toString() + "'");
  	  				}
  		  			if (!conds.get("product2").toString().equals(""))
  		  				rQuery.cond("ii.productCode = '" + conds.get("product2").toString() + "'");
  		  			rQuery.table("InvoiceItem ii INNER JOIN invoicedetail id on ii.invoiceCode = id.invoiceCode " + addQuery)
  		  			 			.field("DATE_FORMAT(id.shiftDate, '%Y-%m-%d') AS `node`", "node")
  		  			 			.field("SUM(CASE WHEN ii.activityType = 'Receipt' THEN (ii.finalCharge * ii.currencyRate) ELSE 0 END) AS `finalReceipt`", "final")
  		  			 			.field("SUM(CASE WHEN ii.activityType = 'Payment' THEN (ii.finalCharge * ii.currencyRate) ELSE 0 END) AS `finalPayment`", "total")
  		  						.cond("ii.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' ")
  		  			 			.cond("ii.productCode IS NOT NULL")
  		  						.cond("DATE_FORMAT(id.shiftDate, '%Y-%m') = '"+ objectParent.getParent() +"'")
  		  			 			.groupBy("`node` DESC");
  	    		} else {
  	    			String addQuery = "";
  	        	if(conds.containsKey("department") && !conds.get("department").toString().isEmpty()){
  	        		addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("department").toString() +"'";
  	        	} else {
  	        		if(conds.containsKey("cashier1")){
  	        			if(!conds.get("cashier1").toString().isEmpty())     				
  	        				addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("cashier1").toString() +"'";
  	        			if(!conds.get("cashier2").toString().isEmpty())
  	        				addQuery = addQuery + " AND i.employeeCode = '"+ conds.get("cashier2").toString() +"'";
  	        		} else {
  	        			if(conds.containsKey("partner1")){
  	        				if(!conds.get("partner1").toString().isEmpty())     				
  	          				addQuery = " AND SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '"+ conds.get("partner1").toString() +"'";
  	          			if(!conds.get("partner2").toString().isEmpty())
  	          				addQuery = addQuery + " AND i.customerCode = '"+ conds.get("partner2").toString() +"'";
  	        			} else {
  	        				if(conds.containsKey("location1")){
  	        					if(!conds.get("location1").toString().isEmpty())     				
  	            				addQuery = " AND i.locationCode = '"+ conds.get("location1").toString() +"'";
  	            			if(!conds.get("location2").toString().isEmpty())
  	            				addQuery = addQuery + " AND i.tableCode = '"+ conds.get("location2").toString() +"'";
  	        				}
  	        				else
  	        				{
  	        					if(conds.containsKey("store") && !conds.get("store").toString().isEmpty())
  	        		        		addQuery = " AND SUBSTRING_INDEX(i.storeCode, '/', -1) = '"+ conds.get("storeCode").toString() +"'";
  	        					else
  	        					{
  	        						if(conds.containsKey("project") && !conds.get("project").toString().isEmpty())
  	        			        		addQuery = " AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"+ conds.get("project").toString() +"'";
  	        					}
  	        				}
  	        			}
  	        		}
  	        	}   	
  	      	rQuery.table("((SELECT " +
  												     "a.node AS `node1`, " +
  												     "a.final AS `final`, " +
  												     "b.node AS `node2`, " +
  												     "b.total AS `total` " +
  									       "FROM " +
  									       		 "((SELECT DATE_FORMAT(i.shiftDate, '%Y-%m-%d') AS `node`, COALESCE(SUM(i.total), 0) AS `final` "
  									       		 + "FROM invoiceTransaction i "
  									       		 + "WHERE i.activityType = '" + ActivityType.Receipt + "' "
  									       		 + "AND DATE_FORMAT(i.shiftDate, '%Y-%m') = '"+ objectParent.getParent() +"' "
  									       		 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` " + 
  									       		 "RIGHT JOIN " + 
  									       		 "(SELECT DATE_FORMAT(i.shiftDate, '%Y-%m-%d') AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
  									       		 + "FROM invoiceTransaction i "
  									       		 + "WHERE i.activityType = '" + ActivityType.Payment + "' "
  									       		 + "AND DATE_FORMAT(i.shiftDate, '%Y-%m') = '"+ objectParent.getParent() +"' "
  									       		 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node)) " + 
  									       "UNION " +
  									       "(SELECT " +
  												  	"a.node AS `node1`, " +
  												    "a.final AS `final`, " +
  												    "b.node AS `node2`, " +
  												    "b.total AS `total` " +
  												 "FROM " +
  												 		"((SELECT DATE_FORMAT(i.shiftDate, '%Y-%m-%d') AS `node`, COALESCE(SUM(i.total), 0) AS `final` "
  												 		+ "FROM invoiceTransaction i "
  												 		+ "WHERE i.activityType = '" + ActivityType.Receipt + "' "
  												 		+ "AND DATE_FORMAT(i.shiftDate, '%Y-%m') = '"+ objectParent.getParent() +"' "
  												 		+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` " + 
  												 		"LEFT JOIN " + 
  									  				"(SELECT DATE_FORMAT(i.shiftDate, '%Y-%m-%d') AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
  									  				+ "FROM invoiceTransaction i "
  									  				+ "WHERE i.activityType = '" + ActivityType.Payment + "' "
  									  				+ "AND DATE_FORMAT(i.shiftDate, '%Y-%m') = '"+ objectParent.getParent() +"' "
  									  				+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node))) AS `e` ORDER BY `node` DESC")
  				        .field("(CASE WHEN (e.node1 IS NULL) THEN e.node2 ELSE e.node1 END) AS `node`", "node")
  				        .field("COALESCE(e.final, 0) AS `totalReceipt`", "final")
  				        .field("COALESCE(e.total, 0) AS `totalPayment`", "total");
  	    		}	
  	  		} else {
  	  			if(parameter.equals("ThuChiCongNo")){
  	  				String addQuery = "";
  	        	if(conds.containsKey("department") && !conds.get("department").toString().isEmpty()){
  	        		addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("department").toString() +"' ";
  	        	} else {
  	        		if(conds.containsKey("cashier1")){
  	        			if(!conds.get("cashier1").toString().isEmpty())     				
  	        				addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("cashier1").toString() +"' ";
  	        			if(!conds.get("cashier2").toString().isEmpty())
  	        				addQuery = " AND i.employeeCode = '"+ conds.get("cashier2").toString() +"'";
  	        		} else {
  	        			if(conds.containsKey("partner1")){
  	        				if(!conds.get("partner1").toString().isEmpty())     				
  	          				addQuery = " AND SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '"+ conds.get("partner1").toString() +"' ";
  	          			if(!conds.get("partner2").toString().isEmpty())
  	          				addQuery = " AND i.customerCode = '"+ conds.get("partner2").toString() +"' ";
  	        			} else {
  	        				if(conds.containsKey("location1")){
  	        					if(!conds.get("location1").toString().isEmpty())     				
  	            				addQuery = " AND i.locationCode = '"+ conds.get("location1").toString() +"' ";
  	            			if(!conds.get("location2").toString().isEmpty())
  	            				addQuery = " AND i.tableCode = '"+ conds.get("location2").toString() +"' ";
  	        				}
  	        				else
  	        				{
  	        					if(conds.containsKey("store") && !conds.get("store").toString().isEmpty())
  	        		        		addQuery = " AND SUBSTRING_INDEX(i.storeCode, '/', -1) = '"+ conds.get("store").toString() +"' ";
  	        					else
  	        					{
  	        						if(conds.containsKey("project") && !conds.get("project").toString().isEmpty())
  	        			        		addQuery = " AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"+ conds.get("project").toString() +"'";
  	        					}
  	        				}
  	        			}
  	        		}
  	        	}
  	    			rQuery.table("((SELECT " +
  	        	"a.node AS `node1`,b.node AS `node2`,a.finalReceipt AS `finalReceipt`,a.finalPayment AS `finalPayment`,b.totalReceipt AS `totalReceipt`,b.totalPayment AS `totalPayment`"+
  	    					"FROM (( SELECT"+
  		    											"DATE_FORMAT(i.shiftDate, '%Y-%m-%d') AS `node`, " +
  		    											"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.finalCharge ELSE 0 END) AS `finalReceipt`, " +
  		    											"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.finalCharge ELSE 0 END) AS `finalPayment` " +
  	    										 "FROM `invoiceDetail` i "
  	    										 + "WHERE DATE_FORMAT(i.shiftDate, '%Y-%m') = '"+ objectParent.getParent() +"' "
  	    										 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
  	    										 "GROUP BY `node`) AS `a` " +
  	    										 "LEFT JOIN " +
  	    										 "(SELECT " +
  	    										 		"DATE_FORMAT(i.shiftDate, '%Y-%m-%d') AS `node`, " +
  	    										 		"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.total ELSE 0 END) AS `totalReceipt`, " +
  	    										 		"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.total ELSE 0 END) AS `totalPayment` " +
  	    										 "FROM `invoiceTransaction` i "
  	    										 + "WHERE DATE_FORMAT(i.shiftDate, '%Y-%m') = '"+ objectParent.getParent() +"' "
  	    										 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
  	    										 "GROUP BY `node`) AS `b` ON a.node = b.node) " +
  	    										 "ORDER BY `node` DESC)"+
  	    										 "UNION "+
  	    										 "(SELECT"+
  	    										"a.node AS `node1`,b.node AS `node2`,a.finalReceipt AS `finalReceipt`,a.finalPayment AS `finalPayment`,b.totalReceipt AS `totalReceipt`,b.totalPayment AS `totalPayment`"+
  	    			  	    					"FROM (( SELECT"+
  	    			  		    											"DATE_FORMAT(i.shiftDate, '%Y-%m-%d') AS `node`, " +
  	    			  		    											"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.finalCharge ELSE 0 END) AS `finalReceipt`, " +
  	    			  		    											"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.finalCharge ELSE 0 END) AS `finalPayment` " +
  	    			  	    										 "FROM `invoiceDetail` i "
  	    			  	    										 + "WHERE DATE_FORMAT(i.shiftDate, '%Y-%m') = '"+ objectParent.getParent() +"' "
  	    			  	    										 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
  	    			  	    										 "GROUP BY `node`) AS `a` " +
  	    			  	    										 "RIGHT JOIN " +
  	    			  	    										 "(SELECT " +
  	    			  	    										 		"DATE_FORMAT(i.shiftDate, '%Y-%m-%d') AS `node`, " +
  	    			  	    										 		"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.total ELSE 0 END) AS `totalReceipt`, " +
  	    			  	    										 		"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.total ELSE 0 END) AS `totalPayment` " +
  	    			  	    										 "FROM `invoiceTransaction` i "
  	    			  	    										 + "WHERE DATE_FORMAT(i.shiftDate, '%Y-%m') = '"+ objectParent.getParent() +"' "
  	    			  	    										 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
  	    			  	    										 "GROUP BY `node`) AS `b` ON a.node = b.node) " +
  	    			  	    										 "ORDER BY `node` DESC))"+
  	    			  	    										 "AS `e` ORDER BY `node` DESC"
  	    					)
  	    	        .field("(CASE WHEN (e.node1 IS NULL) THEN e.node2 ELSE e.node1 END) AS `node`", "node")
  	    	        .field("COALESCE(e.finalReceipt, 0) AS `final Receipt`", "final")
  	    	        .field("COALESCE(e.finalPayment, 0) AS `final Payment`", "total")
  	    	        .field("COALESCE(e.totalReceipt, 0) AS `total Receipt`", "final1")
  	    	        .field("COALESCE(e.totalPayment, 0) AS `total Payment`", "total1");
  	    		}
  	  		}
  	  	}
  	}
  	else{
  		if(activityType != null){
  	  		if(conds.containsKey("product1")){
  	  			String addQuery = "";
  					if (!conds.get("product1").toString().equals("")) {
  						addQuery = " INNER JOIN product p ON p.code = i.productCode INNER JOIN product_productTag t ON t.productId = p.Id INNER JOIN warehouse_productTag w ON w.id = t.productTagId ";
  						rQuery.cond("w.code = '" + conds.get("product1").toString() + "'");
  					}
  	  			if (!conds.get("product2").toString().equals(""))
  	  				rQuery.cond("i.productCode = '" + conds.get("product2").toString() + "'");
  	  			else rQuery.cond("i.productCode IS NOT NULL");
  	  			rQuery.table("invoiceItem i" + addQuery)
  	  						.field("DATE_FORMAT(i.startDate, '%Y-%m-%d') AS `node`", "node")
  		  					.field("COALESCE(SUM(i.finalCharge), 0) AS `final`", "final")
  		  					.cond("i.activityType = '" + activityType + "'")
  		  					.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
  		  					.cond("DATE_FORMAT(i.startDate, '%Y-%m') = '"+ objectParent.getParent() +"'")
  		  					.groupBy("`node` DESC");
  	    	} else {
  	    		String addQuery = "";
  	      	if(conds.containsKey("department") && !conds.get("department").toString().isEmpty()){
  	      		addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("department").toString() +"'";
  	      	} else {
  	      		if(conds.containsKey("cashier1")){
  	      			if(!conds.get("cashier1").toString().isEmpty())     				
  	      				addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("cashier1").toString() +"'";
  	      			if(!conds.get("cashier2").toString().isEmpty())
  	      				addQuery = addQuery + " AND i.employeeCode = '"+ conds.get("cashier2").toString() +"'";
  	      		} else {
  	      			if(conds.containsKey("partner1")){
  	      				if(!conds.get("partner1").toString().isEmpty())     				
  	        				addQuery = " AND SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '"+ conds.get("partner1").toString() +"'";
  	        			if(!conds.get("partner2").toString().isEmpty())
  	        				addQuery = addQuery + " AND i.customerCode = '"+ conds.get("partner2").toString() +"'";
  	      			} else {
  	      				if(conds.containsKey("location1")){
  	      					if(!conds.get("location1").toString().isEmpty())     				
  	          				addQuery = " AND i.locationCode = '"+ conds.get("location1").toString() +"'";
  	          			if(!conds.get("location2").toString().isEmpty())
  	          				addQuery = addQuery + " AND i.tableCode = '"+ conds.get("location2").toString() +"'";
  	      				}
  	      				else
  	      				{
  	      					if(conds.containsKey("store") && !conds.get("store").toString().isEmpty())
  	      			      		addQuery = " AND SUBSTRING_INDEX(i.storeCode, '/', -1) = '"+ conds.get("store").toString() +"'";
  	      					else
  	    					{
  	    						if(conds.containsKey("project") && !conds.get("project").toString().isEmpty())
  	    			        		addQuery = " AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"+ conds.get("project").toString() +"'";
  	    					}
  	      				}
  	      			}
  	      		}
  	      	}
  	      	rQuery.table("((SELECT a.node AS `node1`, b.node AS `node2`, a.final, b.total "
  	      			+ "FROM "
  	      			+ "(SELECT DATE_FORMAT(i.startDate, '%Y-%m-%d') AS `node`, COALESCE(SUM(i.finalCharge), 0) AS `final` " 
  					+ "FROM invoiceDetail i "
  					+ "WHERE i.activityType = '" + activityType + "' "
  					+ "AND DATE_FORMAT(i.startDate, '%Y-%m') = '"+ objectParent.getParent() +"' "
  					+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` "
  					+ "LEFT JOIN "
  					+ "(SELECT DATE_FORMAT(i.transactionDate, '%Y-%m-%d') AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
  					+ "FROM invoiceTransaction i "
  					+ "WHERE i.activityType = '" + activityType + "' "
  					+ "AND DATE_FORMAT(i.transactionDate, '%Y-%m') = '"+ objectParent.getParent() +"' "
  					+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node) "
  							+ "UNION "
  							+ "(SELECT a.node AS `node1`, b.node AS `node2`, a.final, b.total "
  							+ "FROM "
  		  	      			+ "(SELECT DATE_FORMAT(i.startDate, '%Y-%m-%d') AS `node`, COALESCE(SUM(i.finalCharge), 0) AS `final` " 
  		  					+ "FROM invoiceDetail i "
  		  					+ "WHERE i.activityType = '" + activityType + "' "
  		  					+ "AND DATE_FORMAT(i.startDate, '%Y-%m') = '"+ objectParent.getParent() +"' "
  		  					+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` "
  		  					+ "RIGHT JOIN "
  		  					+ "(SELECT DATE_FORMAT(i.transactionDate, '%Y-%m-%d') AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
  		  					+ "FROM invoiceTransaction i "
  		  					+ "WHERE i.activityType = '" + activityType + "' "
  		  					+ "AND DATE_FORMAT(i.transactionDate, '%Y-%m') = '"+ objectParent.getParent() +"' "
  		  					+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node)) "
  		  							+ "AS `e` ORDER BY `node` DESC"
  	      			)
  		            .field("(CASE WHEN (e.node1 IS NULL) THEN e.node2 ELSE e.node1 END) AS `node`", "node")
  		            .field("COALESCE(e.final, 0) AS `totalReceipt`", "final")
  		            .field("COALESCE(e.total, 0) AS `totalPayment`", "total");
  	    	}
  	  	} 
  	  	/**
  	  	 ************************************************** 
  	  	 ************************************************** 
  	  	 *** THỐNG KÊ [THU CHI LÃI] + [THU CHI CÔNG NỢ] ***
  	  	 **************************************************
  	  	 ************************************************** 
  	  	 */
  	  	else {
  	  		if(parameter.equals("ThuChiLai")){
  	  			if(conds.containsKey("product1")){  				
  	  				String addQuery = "";
  	  				if (!conds.get("product1").toString().equals("")) {
  							addQuery = "INNER JOIN product p ON p.code = i.productCode INNER JOIN product_productTag t ON t.productId = p.Id INNER JOIN warehouse_productTag w ON w.id = t.productTagId ";
  							rQuery.cond("w.code = '" + conds.get("product1").toString() + "'");
  	  				}
  		  			if (!conds.get("product2").toString().equals(""))
  		  				rQuery.cond("i.productCode = '" + conds.get("product2").toString() + "'");
  		  			rQuery.table("InvoiceItem i " + addQuery)
  		  			 			.field("DATE_FORMAT(i.startDate, '%Y-%m-%d') AS `node`", "node")
  		  			 			.field("SUM(CASE WHEN i.activityType = 'Receipt' THEN (i.finalCharge * i.currencyRate) ELSE 0 END) AS `finalReceipt`", "final")
  		  			 			.field("SUM(CASE WHEN i.activityType = 'Payment' THEN (i.finalCharge * i.currencyRate) ELSE 0 END) AS `finalPayment`", "total")
  		  						.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
  		  			 			.cond("i.productCode IS NOT NULL")
  		  						.cond("DATE_FORMAT(i.startDate, '%Y-%m') = '"+ objectParent.getParent() +"'")
  		  			 			.groupBy("`node` DESC");
  	    		} else {
  	    			String addQuery = "";
  	        	if(conds.containsKey("department") && !conds.get("department").toString().isEmpty()){
  	        		addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("department").toString() +"'";
  	        	} else {
  	        		if(conds.containsKey("cashier1")){
  	        			if(!conds.get("cashier1").toString().isEmpty())     				
  	        				addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("cashier1").toString() +"'";
  	        			if(!conds.get("cashier2").toString().isEmpty())
  	        				addQuery = addQuery + " AND i.employeeCode = '"+ conds.get("cashier2").toString() +"'";
  	        		} else {
  	        			if(conds.containsKey("partner1")){
  	        				if(!conds.get("partner1").toString().isEmpty())     				
  	          				addQuery = " AND SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '"+ conds.get("partner1").toString() +"'";
  	          			if(!conds.get("partner2").toString().isEmpty())
  	          				addQuery = addQuery + " AND i.customerCode = '"+ conds.get("partner2").toString() +"'";
  	        			} else {
  	        				if(conds.containsKey("location1")){
  	        					if(!conds.get("location1").toString().isEmpty())     				
  	            				addQuery = " AND i.locationCode = '"+ conds.get("location1").toString() +"'";
  	            			if(!conds.get("location2").toString().isEmpty())
  	            				addQuery = addQuery + " AND i.tableCode = '"+ conds.get("location2").toString() +"'";
  	        				}
  	        				else
  	        				{
  	        					if(conds.containsKey("store") && !conds.get("store").toString().isEmpty())
  	        		        		addQuery = " AND SUBSTRING_INDEX(i.storeCode, '/', -1) = '"+ conds.get("storeCode").toString() +"'";
  	        					else
  	        					{
  	        						if(conds.containsKey("project") && !conds.get("project").toString().isEmpty())
  	        			        		addQuery = " AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"+ conds.get("project").toString() +"'";
  	        					}
  	        				}
  	        			}
  	        		}
  	        	}   	
  	      	rQuery.table("((SELECT " +
  												     "a.node AS `node1`, " +
  												     "a.final AS `final`, " +
  												     "b.node AS `node2`, " +
  												     "b.total AS `total` " +
  									       "FROM " +
  									       		 "((SELECT DATE_FORMAT(i.transactionDate, '%Y-%m-%d') AS `node`, COALESCE(SUM(i.total), 0) AS `final` "
  									       		 + "FROM invoiceTransaction i "
  									       		 + "WHERE i.activityType = '" + ActivityType.Receipt + "' "
  									       		 + "AND DATE_FORMAT(i.transactionDate, '%Y-%m') = '"+ objectParent.getParent() +"' "
  									       		 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` " + 
  									       		 "RIGHT JOIN " + 
  									       		 "(SELECT DATE_FORMAT(i.transactionDate, '%Y-%m-%d') AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
  									       		 + "FROM invoiceTransaction i "
  									       		 + "WHERE i.activityType = '" + ActivityType.Payment + "' "
  									       		 + "AND DATE_FORMAT(i.transactionDate, '%Y-%m') = '"+ objectParent.getParent() +"' "
  									       		 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node)) " + 
  									       "UNION " +
  									       "(SELECT " +
  												  	"a.node AS `node1`, " +
  												    "a.final AS `final`, " +
  												    "b.node AS `node2`, " +
  												    "b.total AS `total` " +
  												 "FROM " +
  												 		"((SELECT DATE_FORMAT(i.transactionDate, '%Y-%m-%d') AS `node`, COALESCE(SUM(i.total), 0) AS `final` "
  												 		+ "FROM invoiceTransaction i "
  												 		+ "WHERE i.activityType = '" + ActivityType.Receipt + "' "
  												 		+ "AND DATE_FORMAT(i.transactionDate, '%Y-%m') = '"+ objectParent.getParent() +"' "
  												 		+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` " + 
  												 		"LEFT JOIN " + 
  									  				"(SELECT DATE_FORMAT(i.transactionDate, '%Y-%m-%d') AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
  									  				+ "FROM invoiceTransaction i "
  									  				+ "WHERE i.activityType = '" + ActivityType.Payment + "' "
  									  				+ "AND DATE_FORMAT(i.transactionDate, '%Y-%m') = '"+ objectParent.getParent() +"' "
  									  				+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node))) AS `e` ORDER BY `node` DESC")
  				        .field("(CASE WHEN (e.node1 IS NULL) THEN e.node2 ELSE e.node1 END) AS `node`", "node")
  				        .field("COALESCE(e.final, 0) AS `totalReceipt`", "final")
  				        .field("COALESCE(e.total, 0) AS `totalPayment`", "total");
  	    		}	
  	  		} else {
  	  			if(parameter.equals("ThuChiCongNo")){
  	  				String addQuery = "";
  	        	if(conds.containsKey("department") && !conds.get("department").toString().isEmpty()){
  	        		addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("department").toString() +"' ";
  	        	} else {
  	        		if(conds.containsKey("cashier1")){
  	        			if(!conds.get("cashier1").toString().isEmpty())     				
  	        				addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("cashier1").toString() +"' ";
  	        			if(!conds.get("cashier2").toString().isEmpty())
  	        				addQuery = " AND i.employeeCode = '"+ conds.get("cashier2").toString() +"'";
  	        		} else {
  	        			if(conds.containsKey("partner1")){
  	        				if(!conds.get("partner1").toString().isEmpty())     				
  	          				addQuery = " AND SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '"+ conds.get("partner1").toString() +"' ";
  	          			if(!conds.get("partner2").toString().isEmpty())
  	          				addQuery = " AND i.customerCode = '"+ conds.get("partner2").toString() +"' ";
  	        			} else {
  	        				if(conds.containsKey("location1")){
  	        					if(!conds.get("location1").toString().isEmpty())     				
  	            				addQuery = " AND i.locationCode = '"+ conds.get("location1").toString() +"' ";
  	            			if(!conds.get("location2").toString().isEmpty())
  	            				addQuery = " AND i.tableCode = '"+ conds.get("location2").toString() +"' ";
  	        				}
  	        				else
  	        				{
  	        					if(conds.containsKey("store") && !conds.get("store").toString().isEmpty())
  	        		        		addQuery = " AND SUBSTRING_INDEX(i.storeCode, '/', -1) = '"+ conds.get("store").toString() +"' ";
  	        					else
  	        					{
  	        						if(conds.containsKey("project") && !conds.get("project").toString().isEmpty())
  	        			        		addQuery = " AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"+ conds.get("project").toString() +"'";
  	        					}
  	        				}
  	        			}
  	        		}
  	        	}
  	    			rQuery.table("((SELECT " +
  	        	"a.node AS `node1`,b.node AS `node2`,a.finalReceipt AS `finalReceipt`,a.finalPayment AS `finalPayment`,b.totalReceipt AS `totalReceipt`,b.totalPayment AS `totalPayment`"+
  	    					"FROM ((SELECT "+ 
  		    											"DATE_FORMAT(i.startDate, '%Y-%m-%d') AS `node`, " +
  		    											"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.finalCharge ELSE 0 END) AS `finalReceipt`, " +
  		    											"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.finalCharge ELSE 0 END) AS `finalPayment` " +
  	    										 "FROM `invoiceDetail` i "
  	    										 + "WHERE DATE_FORMAT(i.startDate, '%Y-%m') = '"+ objectParent.getParent() +"' "
  	    										 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
  	    										 "GROUP BY `node`) AS `a` " +
  	    										 " LEFT JOIN " +
  	    										 "(SELECT " +
  	    										 		"DATE_FORMAT(i.transactionDate, '%Y-%m-%d') AS `node`, " +
  	    										 		"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.total ELSE 0 END) AS `totalReceipt`, " +
  	    										 		"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.total ELSE 0 END) AS `totalPayment` " +
  	    										 "FROM `invoiceTransaction` i "
  	    										 + "WHERE DATE_FORMAT(i.transactionDate, '%Y-%m') = '"+ objectParent.getParent() +"' "
  	    										 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
  	    										 "GROUP BY `node`) AS `b` ON a.node = b.node) " +
  	    										 "ORDER BY `node` DESC) "+
  	    										 "UNION "+
  	    										 "( SELECT "+
  	    										"a.node AS `node1`,b.node AS `node2`,a.finalReceipt AS `finalReceipt`,a.finalPayment AS `finalPayment`,b.totalReceipt AS `totalReceipt`,b.totalPayment AS `totalPayment`"+
  	    			  	    					"FROM ((SELECT "+ 
  	    			  		    											"DATE_FORMAT(i.startDate, '%Y-%m-%d') AS `node`, " +
  	    			  		    											"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.finalCharge ELSE 0 END) AS `finalReceipt`, " +
  	    			  		    											"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.finalCharge ELSE 0 END) AS `finalPayment` " +
  	    			  	    										 "FROM `invoiceDetail` i "
  	    			  	    										 + "WHERE DATE_FORMAT(i.startDate, '%Y-%m') = '"+ objectParent.getParent() +"' "
  	    			  	    										 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
  	    			  	    										 "GROUP BY `node`) AS `a` " +
  	    			  	    										 " RIGHT JOIN " +
  	    			  	    										 "(SELECT " +
  	    			  	    										 		"DATE_FORMAT(i.transactionDate, '%Y-%m-%d') AS `node`, " +
  	    			  	    										 		"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.total ELSE 0 END) AS `totalReceipt`, " +
  	    			  	    										 		"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.total ELSE 0 END) AS `totalPayment` " +
  	    			  	    										 "FROM `invoiceTransaction` i "
  	    			  	    										 + "WHERE DATE_FORMAT(i.transactionDate, '%Y-%m') = '"+ objectParent.getParent() +"' "
  	    			  	    										 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
  	    			  	    										 "GROUP BY `node`) AS `b` ON a.node = b.node) " +
  	    			  	    										 "ORDER BY `node` DESC))"+
  	    			  	    										 "AS `e` ORDER BY `node` DESC"
  	    					)
  	    	        .field("(CASE WHEN (e.node1 IS NULL) THEN e.node2 ELSE e.node1 END) AS `node`", "node")
  	    	        .field("COALESCE(e.finalReceipt, 0) AS `final Receipt`", "final")
  	    	        .field("COALESCE(e.finalPayment, 0) AS `final Payment`", "total")
  	    	        .field("COALESCE(e.totalReceipt, 0) AS `total Receipt`", "final1")
  	    	        .field("COALESCE(e.totalPayment, 0) AS `total Payment`", "total1");
  	    		}
  	  		}
  	  	}
  	}
  	
  	return rQuery;
  }
  
  private SQLSelectQuery SQLQueryString_GetHour(ReportForTimeEntity objectParent, ActivityType activityType, String parameter){
	  	SQLSelectQuery rQuery = new SQLSelectQuery();
	  	if(setView !=3 && setView !=2){
		  	/**
		  	 ****************************************
		  	 **************************************** 
		  	 *** THỐNG KÊ [DOANH THU] + [CHI PHÍ] ***
		  	 ****************************************
		  	 **************************************** 
		  	 */
	  		if(activityType != null){
	  	  		if(conds.containsKey("product1")){
	  	  			String addQuery = "";
	  					if (!conds.get("product1").toString().equals("")) {
	  						addQuery = " INNER JOIN product p ON p.code = i.productCode INNER JOIN product_productTag t ON t.productId = p.Id INNER JOIN warehouse_productTag w ON w.id = t.productTagId ";
	  						rQuery.cond("w.code = '" + conds.get("product1").toString() + "'");
	  					}
	  	  			if (!conds.get("product2").toString().equals(""))
	  	  				rQuery.cond("i.productCode = '" + conds.get("product2").toString() + "'");
	  	  			else rQuery.cond("i.productCode IS NOT NULL");
	  	  			rQuery.table("invoiceItem i" + addQuery)
	  	  						.field("DATE_FORMAT(i.startDate, '%Y-%m-%d-%H') AS `node`", "node")
	  		  					.field("COALESCE(SUM(i.finalCharge), 0) AS `final`", "final")
	  		  					.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
	  		  					.cond("i.activityType = '" + activityType + "'")
	  		  					.cond("DATE_FORMAT(i.startDate, '%Y-%m-%d') = '"+ objectParent.getParent() +"'")
	  		  					.groupBy("`node`");
	  	    	} else {
	  	    		String addQuery = "";
	  	      	if(conds.containsKey("department") && !conds.get("department").toString().isEmpty()){
	  	      		addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("department").toString() +"'";
	  	      	} else {
	  	      		if(conds.containsKey("cashier1")){
	  	      			if(!conds.get("cashier1").toString().isEmpty())     				
	  	      				addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("cashier1").toString() +"'";
	  	      			if(!conds.get("cashier2").toString().isEmpty())
	  	      				addQuery = addQuery + " AND i.employeeCode = '"+ conds.get("cashier2").toString() +"'";
	  	      		} else {
	  	      			if(conds.containsKey("partner1")){
	  	      				if(!conds.get("partner1").toString().isEmpty())     				
	  	        				addQuery = " AND SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '"+ conds.get("partner1").toString() +"'";
	  	        			if(!conds.get("partner2").toString().isEmpty())
	  	        				addQuery = addQuery + " AND i.customerCode = '"+ conds.get("partner2").toString() +"'";
	  	      			} else {
	  	      				if(conds.containsKey("location1")){
	  	      					if(!conds.get("location1").toString().isEmpty())     				
	  	          				addQuery = " AND i.locationCode = '"+ conds.get("location1").toString() +"'";
	  	          			if(!conds.get("location2").toString().isEmpty())
	  	          				addQuery = addQuery + " AND i.tableCode = '"+ conds.get("location2").toString() +"'";
	  	      				}
	  	      				else
	  	      				{
	  	      					if(conds.containsKey("store") && !conds.get("store").toString().isEmpty())
	  	      			      		addQuery = " AND SUBSTRING_INDEX(i.storeCode, '/', -1) = '"+ conds.get("store").toString() +"'";
	  	      					else
	  	    					{
	  	    						if(conds.containsKey("project") && !conds.get("project").toString().isEmpty())
	  	    			        		addQuery = " AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"+ conds.get("project").toString() +"'";
	  	    					}
	  	      				}
	  	      			}
	  	      		}
	  	      	}
	  	      	rQuery.table("((SELECT a.node AS `node1`, b.node AS `node2`, a.final, b.total "
	  	      			+ "FROM "
	  	      			+ "(SELECT DATE_FORMAT(i.startDate, '%Y-%m-%d-%H') AS `node`, COALESCE(SUM(i.finalCharge), 0) AS `final` " 
	  					+ "FROM invoiceDetail i "
	  					+ "WHERE i.activityType = '" + activityType + "' "
	  					+ "AND DATE_FORMAT(i.startDate, '%Y-%m-%d') = '"+ objectParent.getParent() +"' "
	  					+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` "
	  					+ "LEFT JOIN "
	  					+ "(SELECT DATE_FORMAT(i.transactionDate, '%Y-%m-%d-%H') AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
	  					+ "FROM invoiceTransaction i "
	  					+ "WHERE i.activityType = '" + activityType + "' "
	  					+ "AND DATE_FORMAT(i.transactionDate, '%Y-%m-%d') = '"+ objectParent.getParent() +"' "
	  					+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node) "
	  							+ "UNION "
	  							+ "(SELECT a.node AS `node1`, b.node AS `node2`, a.final, b.total "
	  							+ "FROM "
	  		  	      			+ "(SELECT DATE_FORMAT(i.startDate, '%Y-%m-%d-%H') AS `node`, COALESCE(SUM(i.finalCharge), 0) AS `final` " 
	  		  					+ "FROM invoiceDetail i "
	  		  					+ "WHERE i.activityType = '" + activityType + "' "
	  		  					+ "AND DATE_FORMAT(i.startDate, '%Y-%m-%d') = '"+ objectParent.getParent() +"' "
	  		  					+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` "
	  		  					+ "RIGHT JOIN "
	  		  					+ "(SELECT DATE_FORMAT(i.transactionDate, '%Y-%m-%d-%H') AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
	  		  					+ "FROM invoiceTransaction i "
	  		  					+ "WHERE i.activityType = '" + activityType + "' "
	  		  					+ "AND DATE_FORMAT(i.transactionDate, '%Y-%m-%d') = '"+ objectParent.getParent() +"' "
	  		  					+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node)) "
	  		  							+ "AS `e` ORDER BY `node` DESC"
	  	      			)
	  		            .field("(CASE WHEN (e.node1 IS NULL) THEN e.node2 ELSE e.node1 END) AS `node`", "node")
	  		            .field(" COALESCE(e.final, 0) AS `totalReceipt`", "final")
	  		            .field(" COALESCE(e.total, 0) AS `totalPayment`", "total");
	  	    	}
	  	  	} 
	  	  	/**
	  	  	 ************************************************** 
	  	  	 ************************************************** 
	  	  	 *** THỐNG KÊ [THU CHI LÃI] + [THU CHI CÔNG NỢ] ***
	  	  	 **************************************************
	  	  	 ************************************************** 
	  	  	 */
	  	  	else {
	  	  		if(parameter.equals("ThuChiLai")){
	  	  			if(conds.containsKey("product1")){  				
	  	  				String addQuery = "";
	  	  				if (!conds.get("product1").toString().equals("")) {
	  							addQuery = "INNER JOIN product p ON p.code = i.productCode INNER JOIN product_productTag t ON t.productId = p.Id INNER JOIN warehouse_productTag w ON w.id = t.productTagId ";
	  							rQuery.cond("w.code = '" + conds.get("product1").toString() + "'");
	  	  				}
	  		  			if (!conds.get("product2").toString().equals(""))
	  		  				rQuery.cond("i.productCode = '" + conds.get("product2").toString() + "'");
	  		  			rQuery.table("InvoiceItem i " + addQuery)
	  		  			 			.field("DATE_FORMAT(i.startDate, '%Y-%m-%d-%H') AS `node`", "node")
	  		  			 			.field("SUM(CASE WHEN i.activityType = 'Receipt' THEN (i.finalCharge * i.currencyRate) ELSE 0 END) AS `finalReceipt`", "final")
	  		  			 			.field("SUM(CASE WHEN i.activityType = 'Payment' THEN (i.finalCharge * i.currencyRate) ELSE 0 END) AS `finalPayment`", "total")
	  		  						.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
	  		  			 			.cond("i.productCode IS NOT NULL")
	  		  						.cond("DATE_FORMAT(i.startDate, '%Y-%m-%d') = '"+ objectParent.getParent() +"'")
	  		  			 			.groupBy("`node`");
	  	    		} else {
	  	    			String addQuery = "";
	  	        	if(conds.containsKey("department") && !conds.get("department").toString().isEmpty()){
	  	        		addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("department").toString() +"'";
	  	        	} else {
	  	        		if(conds.containsKey("cashier1")){
	  	        			if(!conds.get("cashier1").toString().isEmpty())     				
	  	        				addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("cashier1").toString() +"'";
	  	        			if(!conds.get("cashier2").toString().isEmpty())
	  	        				addQuery = addQuery + " AND i.employeeCode = '"+ conds.get("cashier2").toString() +"'";
	  	        		} else {
	  	        			if(conds.containsKey("partner1")){
	  	        				if(!conds.get("partner1").toString().isEmpty())     				
	  	          				addQuery = " AND SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '"+ conds.get("partner1").toString() +"'";
	  	          			if(!conds.get("partner2").toString().isEmpty())
	  	          				addQuery = addQuery + " AND i.customerCode = '"+ conds.get("partner2").toString() +"'";
	  	        			} else {
	  	        				if(conds.containsKey("location1")){
	  	        					if(!conds.get("location1").toString().isEmpty())     				
	  	            				addQuery = " AND i.locationCode = '"+ conds.get("location1").toString() +"'";
	  	            			if(!conds.get("location2").toString().isEmpty())
	  	            				addQuery = addQuery + " AND i.tableCode = '"+ conds.get("location2").toString() +"'";
	  	        				}
	  	        				else
	  	        				{
	  	        					if(conds.containsKey("store") && !conds.get("store").toString().isEmpty())
	  	        		        		addQuery = " AND SUBSTRING_INDEX(i.storeCode, '/', -1) = '"+ conds.get("storeCode").toString() +"'";
	  	        					else
	  	        					{
	  	        						if(conds.containsKey("project") && !conds.get("project").toString().isEmpty())
	  	        			        		addQuery = " AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"+ conds.get("project").toString() +"'";
	  	        					}
	  	        				}
	  	        			}
	  	        		}
	  	        	}   	
	  	      	rQuery.table("((SELECT " +
	  												     "a.node AS `node1`, " +
	  												     "a.final AS `final`, " +
	  												     "b.node AS `node2`, " +
	  												     "b.total AS `total` " +
	  									       "FROM " +
	  									       		 "((SELECT DATE_FORMAT(i.transactionDate, '%Y-%m-%d-%H') AS `node`, COALESCE(SUM(i.total), 0) AS `final` "
	  									       		 + "FROM invoiceTransaction i "
	  									       		 + "WHERE i.activityType = '" + ActivityType.Receipt + "' "
	  									       		 + "AND DATE_FORMAT(i.transactionDate, '%Y-%m-%d') = '"+ objectParent.getParent() +"' "
	  									       		 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` " + 
	  									       		 "RIGHT JOIN " + 
	  									       		 "(SELECT DATE_FORMAT(i.transactionDate, '%Y-%m-%d-%H') AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
	  									       		 + "FROM invoiceTransaction i "
	  									       		 + "WHERE i.activityType = '" + ActivityType.Payment + "' "
	  									       		 + "AND DATE_FORMAT(i.transactionDate, '%Y-%m-%d') = '"+ objectParent.getParent() +"' "
	  									       		 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node)) " + 
	  									       "UNION " +
	  									       "(SELECT " +
	  												  	"a.node AS `node1`, " +
	  												    "a.final AS `final`, " +
	  												    "b.node AS `node2`, " +
	  												    "b.total AS `total` " +
	  												 "FROM " +
	  												 		"((SELECT DATE_FORMAT(i.transactionDate, '%Y-%m-%d-%H') AS `node`, COALESCE(SUM(i.total), 0) AS `final` "
	  												 		+ "FROM invoiceTransaction i "
	  												 		+ "WHERE i.activityType = '" + ActivityType.Receipt + "' "
	  												 		+ "AND DATE_FORMAT(i.transactionDate, '%Y-%m-%d') = '"+ objectParent.getParent() +"' "
	  												 		+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` " + 
	  												 		"LEFT JOIN " + 
	  									  				"(SELECT DATE_FORMAT(i.transactionDate, '%Y-%m-%d-%H') AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
	  									  				+ "FROM invoiceTransaction i "
	  									  				+ "WHERE i.activityType = '" + ActivityType.Payment + "' "
	  									  				+ "AND DATE_FORMAT(i.transactionDate, '%Y-%m-%d') = '"+ objectParent.getParent() +"' "
	  									  				+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node))) AS `e` ORDER BY `node`")
	  				        .field("(CASE WHEN (e.node1 IS NULL) THEN e.node2 ELSE e.node1 END) AS `node`", "node")
	  				        .field("COALESCE(e.final, 0) AS `totalReceipt`", "final")
	  				        .field("COALESCE(e.total, 0) AS `totalPayment`", "total");
	  	    		}	
	  	  		} else {
	  	  			if(parameter.equals("ThuChiCongNo")){
	  	  				String addQuery = "";
	  	        	if(conds.containsKey("department") && !conds.get("department").toString().isEmpty()){
	  	        		addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("department").toString() +"' ";
	  	        	} else {
	  	        		if(conds.containsKey("cashier1")){
	  	        			if(!conds.get("cashier1").toString().isEmpty())     				
	  	        				addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("cashier1").toString() +"' ";
	  	        			if(!conds.get("cashier2").toString().isEmpty())
	  	        				addQuery = " AND i.employeeCode = '"+ conds.get("cashier2").toString() +"'";
	  	        		} else {
	  	        			if(conds.containsKey("partner1")){
	  	        				if(!conds.get("partner1").toString().isEmpty())     				
	  	          				addQuery = " AND SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '"+ conds.get("partner1").toString() +"' ";
	  	          			if(!conds.get("partner2").toString().isEmpty())
	  	          				addQuery = " AND i.customerCode = '"+ conds.get("partner2").toString() +"' ";
	  	        			} else {
	  	        				if(conds.containsKey("location1")){
	  	        					if(!conds.get("location1").toString().isEmpty())     				
	  	            				addQuery = " AND i.locationCode = '"+ conds.get("location1").toString() +"' ";
	  	            			if(!conds.get("location2").toString().isEmpty())
	  	            				addQuery = " AND i.tableCode = '"+ conds.get("location2").toString() +"' ";
	  	        				}
	  	        				else
	  	        				{
	  	        					if(conds.containsKey("store") && !conds.get("store").toString().isEmpty())
	  	        		        		addQuery = " AND SUBSTRING_INDEX(i.storeCode, '/', -1) = '"+ conds.get("store").toString() +"' ";
	  	        					else
	  	        					{
	  	        						if(conds.containsKey("project") && !conds.get("project").toString().isEmpty())
	  	        			        		addQuery = " AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"+ conds.get("project").toString() +"'";
	  	        					}
	  	        				}
	  	        			}
	  	        		}
	  	        	}
	  	    			rQuery.table("((SELECT " +
	  	    					"a.node AS `node1`,b.node AS `node2`,a.finalReceipt AS `finalReceipt`,a.finalPayment AS `finalPayment`,b.totalReceipt AS `totalReceipt`,b.totalPayment AS `totalPayment` "+
	  	    					"FROM ((SELECT "+
	  		    											"DATE_FORMAT(i.startDate, '%Y-%m-%d-%H') AS `node`, " +
	  		    											"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.finalCharge ELSE 0 END) AS `finalReceipt`, " +
	  		    											"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.finalCharge ELSE 0 END) AS `finalPayment` " +
	  	    										 "FROM `invoiceDetail` i "
	  	    										 + "WHERE DATE_FORMAT(i.startDate, '%Y-%m-%d') = '"+ objectParent.getParent() +"' "
	  	    										 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
	  	    										 "GROUP BY `node`) AS `a` " +
	  	    										 "LEFT JOIN " +
	  	    										 "(SELECT " +
	  	    										 		"DATE_FORMAT(i.transactionDate, '%Y-%m-%d-%H') AS `node`, " +
	  	    										 		"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.total ELSE 0 END) AS `totalReceipt`, " +
	  	    										 		"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.total ELSE 0 END) AS `totalPayment` " +
	  	    										 "FROM `invoiceTransaction` i "
	  	    										 + "WHERE DATE_FORMAT(i.transactionDate, '%Y-%m-%d') = '"+ objectParent.getParent() +"' "
	  	    										 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
	  	    										 "GROUP BY `node`) AS `b` ON a.node = b.node) " +
	  	    										 "ORDER BY `node`)"+
	  	    										 "UNION "+
	  	    										 "( SELECT "+
	  	    										"a.node AS `node1`,b.node AS `node2`,a.finalReceipt AS `finalReceipt`,a.finalPayment AS `finalPayment`,b.totalReceipt AS `totalReceipt`,b.totalPayment AS `totalPayment` "+
	  	    			  	    					"FROM ((SELECT "+
	  	    			  		    											"DATE_FORMAT(i.startDate, '%Y-%m-%d-%H') AS `node`, " +
	  	    			  		    											"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.finalCharge ELSE 0 END) AS `finalReceipt`, " +
	  	    			  		    											"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.finalCharge ELSE 0 END) AS `finalPayment` " +
	  	    			  	    										 "FROM `invoiceDetail` i "
	  	    			  	    										 + "WHERE DATE_FORMAT(i.startDate, '%Y-%m-%d') = '"+ objectParent.getParent() +"' "
	  	    			  	    										 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
	  	    			  	    										 "GROUP BY `node`) AS `a` " +
	  	    			  	    										 "RIGHT JOIN " +
	  	    			  	    										 "(SELECT " +
	  	    			  	    										 		"DATE_FORMAT(i.transactionDate, '%Y-%m-%d-%H') AS `node`, " +
	  	    			  	    										 		"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.total ELSE 0 END) AS `totalReceipt`, " +
	  	    			  	    										 		"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.total ELSE 0 END) AS `totalPayment` " +
	  	    			  	    										 "FROM `invoiceTransaction` i "
	  	    			  	    										 + "WHERE DATE_FORMAT(i.transactionDate, '%Y-%m-%d') = '"+ objectParent.getParent() +"' "
	  	    			  	    										 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
	  	    			  	    										 "GROUP BY `node`) AS `b` ON a.node = b.node) " +
	  	    			  	    										 "ORDER BY `node`)) "+
	  	    			  	    										 "AS `e` ORDER BY `node` DESC"
	  	    					)
	  	    	        .field("(CASE WHEN (e.node1 IS NULL) THEN e.node2 ELSE e.node1 END) AS `node`", "node")
	  	    	        .field("COALESCE(e.finalReceipt, 0) AS `final Receipt`", "final")
	  	    	        .field("COALESCE(e.finalPayment, 0) AS `final Payment`", "total")
	  	    	        .field("COALESCE(e.totalReceipt, 0) AS `total Receipt`", "final1")
	  	    	        .field("COALESCE(e.totalPayment, 0) AS `total Payment`", "total1");
	  	    		}
	  	  		}
	  	  	}
	  	}
	  	else{
		  	/**
		  	 ****************************************
		  	 **************************************** 
		  	 *** THỐNG KÊ [DOANH THU] + [CHI PHÍ] ***
		  	 ****************************************
		  	 **************************************** 
		  	 */
	  		if(activityType != null){
	  	  		if(conds.containsKey("product1")){
	  	  			String addQuery = "";
	  					if (!conds.get("product1").toString().equals("")) {
	  						addQuery = " INNER JOIN product p ON p.code = ii.productCode INNER JOIN product_productTag t ON t.productId = p.Id INNER JOIN warehouse_productTag w ON w.id = t.productTagId ";
	  						rQuery.cond("w.code = '" + conds.get("product1").toString() + "'");
	  					}
	  	  			if (!conds.get("product2").toString().equals(""))
	  	  				rQuery.cond("ii.productCode = '" + conds.get("product2").toString() + "'");
	  	  			else rQuery.cond("ii.productCode IS NOT NULL");
	  	  			rQuery.table("invoiceItem ii INNER JOIN invoicedetail id on ii.invoiceCode = id.invoiceCode inner join restaurant_shift r on id.shiftCode = r.code" + addQuery)
	  	  						.field("DATE_FORMAT(id.shiftDate, '%Y-%m-%d-%H') AS `node`", "node")
	  		  					.field("COALESCE(SUM(ii.finalCharge), 0) AS `final`", "final")
	  		  					.cond("ii.activityType = '" + activityType + "'")
	  		  					.cond("ii.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
	  		  					.cond("r.name = '"+ objectParent.getParent() +"'")
	  		  					.groupBy("`node`");
	  	    	} else {
	  	    		String addQuery = "";
	  	      	if(conds.containsKey("department") && !conds.get("department").toString().isEmpty()){
	  	      		addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("department").toString() +"'";
	  	      	} else {
	  	      		if(conds.containsKey("cashier1")){
	  	      			if(!conds.get("cashier1").toString().isEmpty())     				
	  	      				addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("cashier1").toString() +"'";
	  	      			if(!conds.get("cashier2").toString().isEmpty())
	  	      				addQuery = addQuery + " AND i.employeeCode = '"+ conds.get("cashier2").toString() +"'";
	  	      		} else {
	  	      			if(conds.containsKey("partner1")){
	  	      				if(!conds.get("partner1").toString().isEmpty())     				
	  	        				addQuery = " AND SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '"+ conds.get("partner1").toString() +"'";
	  	        			if(!conds.get("partner2").toString().isEmpty())
	  	        				addQuery = addQuery + " AND i.customerCode = '"+ conds.get("partner2").toString() +"'";
	  	      			} else {
	  	      				if(conds.containsKey("location1")){
	  	      					if(!conds.get("location1").toString().isEmpty())     				
	  	          				addQuery = " AND i.locationCode = '"+ conds.get("location1").toString() +"'";
	  	          			if(!conds.get("location2").toString().isEmpty())
	  	          				addQuery = addQuery + " AND i.tableCode = '"+ conds.get("location2").toString() +"'";
	  	      				}
	  	      				else
	  	      				{
	  	      					if(conds.containsKey("store") && !conds.get("store").toString().isEmpty())
	  	      			      		addQuery = " AND SUBSTRING_INDEX(i.storeCode, '/', -1) = '"+ conds.get("store").toString() +"'";
	  	      					else
	  	    					{
	  	    						if(conds.containsKey("project") && !conds.get("project").toString().isEmpty())
	  	    			        		addQuery = " AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"+ conds.get("project").toString() +"'";
	  	    					}
	  	      				}
	  	      			}
	  	      		}
	  	      	}
	  	      	rQuery.table("((SELECT a.node AS `node1`, b.node AS `node2`, a.final, b.total "
	  	      			+ "FROM "
	  	      			+ "(SELECT DATE_FORMAT(i.shiftDate, '%Y-%m-%d-%H') AS `node`, COALESCE(SUM(i.finalCharge), 0) AS `final` " 
	  					+ "FROM invoiceDetail i INNER JOIN restaurant_shift r ON i.shiftCode  = r.code "
	  					+ "WHERE i.activityType = '" + activityType + "' "
	  					+ "AND r.name = '"+ objectParent.getParent() +"' "
	  					+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` "
	  					+ "LEFT JOIN "
	  					+ "(SELECT DATE_FORMAT(i.shiftDate, '%Y-%m-%d-%H') AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
	  					+ "FROM invoiceTransaction i INNER JOIN restaurant_shift r ON i.shiftCode  = r.code "
	  					+ "WHERE i.activityType = '" + activityType + "' "
	  					+ "AND r.name = '"+ objectParent.getParent() +"' "
	  					+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node) "
	  							+ "UNION "
	  							+ "(SELECT a.node AS `node1`, b.node AS `node2`, a.final, b.total "
	  							+ "FROM "
	  		  	      			+ "(SELECT DATE_FORMAT(i.shiftDate, '%Y-%m-%d-%H') AS `node`, COALESCE(SUM(i.finalCharge), 0) AS `final` " 
	  		  					+ "FROM invoiceDetail i INNER JOIN restaurant_shift r ON i.shiftCode  = r.code "
	  		  					+ "WHERE i.activityType = '" + activityType + "' "
	  		  					+ "AND r.name = '"+ objectParent.getParent() +"' "
	  		  					+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` "
	  		  					+ "RIGHT JOIN "
	  		  					+ "(SELECT DATE_FORMAT(i.shiftDate, '%Y-%m-%d-%H') AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
	  		  					+ "FROM invoiceTransaction i INNER JOIN restaurant_shift r ON i.shiftCode  = r.code "
	  		  					+ "WHERE i.activityType = '" + activityType + "' "
	  		  					+ "AND r.name = '"+ objectParent.getParent() +"' "
	  		  					+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node)) "
	  		  							+ "AS `e` ORDER BY `node` DESC"
	  	      			)
	  		            .field("(CASE WHEN (e.node1 IS NULL) THEN e.node2 ELSE e.node1 END) AS `node`", "node")
	  		            .field("COALESCE(e.final, 0) AS `totalReceipt`", "final")
	  		            .field("COALESCE(e.total, 0) AS `totalPayment`", "total");
	  	    	}
	  	  	} 
	  	  	/**
	  	  	 ************************************************** 
	  	  	 ************************************************** 
	  	  	 *** THỐNG KÊ [THU CHI LÃI] + [THU CHI CÔNG NỢ] ***
	  	  	 **************************************************
	  	  	 ************************************************** 
	  	  	 */
	  	  	else {
	  	  		if(parameter.equals("ThuChiLai")){
	  	  			if(conds.containsKey("product1")){  				
	  	  				String addQuery = "";
	  	  				if (!conds.get("product1").toString().equals("")) {
	  							addQuery = "INNER JOIN product p ON p.code = ii.productCode INNER JOIN product_productTag t ON t.productId = p.Id INNER JOIN warehouse_productTag w ON w.id = t.productTagId ";
	  							rQuery.cond("w.code = '" + conds.get("product1").toString() + "'");
	  	  				}
	  		  			if (!conds.get("product2").toString().equals(""))
	  		  				rQuery.cond("ii.productCode = '" + conds.get("product2").toString() + "'");
	  		  			rQuery.table("InvoiceItem ii INNER JOIN invoicedetail id on ii.invoiceCode = id.invoiceCode inner join restaurant_shift r on id.shiftCode = r.code " + addQuery)
	  		  			 			.field("DATE_FORMAT(id.shiftDate, '%Y-%m-%d-%H') AS `node`", "node")
	  		  			 			.field("SUM(CASE WHEN ii.activityType = 'Receipt' THEN (ii.finalCharge * ii.currencyRate) ELSE 0 END) AS `finalReceipt`", "final")
	  		  			 			.field("SUM(CASE WHEN ii.activityType = 'Payment' THEN (ii.finalCharge * ii.currencyRate) ELSE 0 END) AS `finalPayment`", "total")
	  		  						.cond("ii.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
	  		  			 			.cond("ii.productCode IS NOT NULL")
	  		  						.cond("r.name = '"+ objectParent.getParent() +"'")
	  		  			 			.groupBy("`node`");
	  	    		} else {
	  	    			String addQuery = "";
	  	        	if(conds.containsKey("department") && !conds.get("department").toString().isEmpty()){
	  	        		addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("department").toString() +"'";
	  	        	} else {
	  	        		if(conds.containsKey("cashier1")){
	  	        			if(!conds.get("cashier1").toString().isEmpty())     				
	  	        				addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("cashier1").toString() +"'";
	  	        			if(!conds.get("cashier2").toString().isEmpty())
	  	        				addQuery = addQuery + " AND i.employeeCode = '"+ conds.get("cashier2").toString() +"'";
	  	        		} else {
	  	        			if(conds.containsKey("partner1")){
	  	        				if(!conds.get("partner1").toString().isEmpty())     				
	  	          				addQuery = " AND SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '"+ conds.get("partner1").toString() +"'";
	  	          			if(!conds.get("partner2").toString().isEmpty())
	  	          				addQuery = addQuery + " AND i.customerCode = '"+ conds.get("partner2").toString() +"'";
	  	        			} else {
	  	        				if(conds.containsKey("location1")){
	  	        					if(!conds.get("location1").toString().isEmpty())     				
	  	            				addQuery = " AND i.locationCode = '"+ conds.get("location1").toString() +"'";
	  	            			if(!conds.get("location2").toString().isEmpty())
	  	            				addQuery = addQuery + " AND i.tableCode = '"+ conds.get("location2").toString() +"'";
	  	        				}
	  	        				else
	  	        				{
	  	        					if(conds.containsKey("store") && !conds.get("store").toString().isEmpty())
	  	        		        		addQuery = " AND SUBSTRING_INDEX(i.storeCode, '/', -1) = '"+ conds.get("storeCode").toString() +"'";
	  	        					else
	  	        					{
	  	        						if(conds.containsKey("project") && !conds.get("project").toString().isEmpty())
	  	        			        		addQuery = " AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"+ conds.get("project").toString() +"'";
	  	        					}
	  	        				}
	  	        			}
	  	        		}
	  	        	}   	
	  	      	rQuery.table("((SELECT " +
	  												     "a.node AS `node1`, " +
	  												     "a.final AS `final`, " +
	  												     "b.node AS `node2`, " +
	  												     "b.total AS `total` " +
	  									       "FROM " +
	  									       		 "((SELECT DATE_FORMAT(i.shiftDate, '%Y-%m-%d-%H') AS `node`, COALESCE(SUM(i.total), 0) AS `final` "
	  									       		 + "FROM invoiceTransaction i INNER JOIN restaurant_shift r ON i.shiftCode  = r.code "
	  									       		 + "WHERE i.activityType = '" + ActivityType.Receipt + "' "
	  									       		 + "AND r.name = '"+ objectParent.getParent() +"' "
	  									       		 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` " + 
	  									       		 "RIGHT JOIN " + 
	  									       		 "(SELECT DATE_FORMAT(i.shiftDate, '%Y-%m-%d-%H') AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
	  									       		 + "FROM invoiceTransaction i INNER JOIN restaurant_shift r ON i.shiftCode  = r.code "
	  									       		 + "WHERE i.activityType = '" + ActivityType.Payment + "' "
	  									       		 + "AND r.name= '"+ objectParent.getParent() +"' "
	  									       		 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node)) " + 
	  									       "UNION " +
	  									       "(SELECT " +
	  												  	"a.node AS `node1`, " +
	  												    "a.final AS `final`, " +
	  												    "b.node AS `node2`, " +
	  												    "b.total AS `total` " +
	  												 "FROM " +
	  												 		"((SELECT DATE_FORMAT(i.shiftDate, '%Y-%m-%d-%H') AS `node`, COALESCE(SUM(i.total), 0) AS `final` "
	  												 		+ "FROM invoiceTransaction i INNER JOIN restaurant_shift r ON i.shiftCode  = r.code "
	  												 		+ "WHERE i.activityType = '" + ActivityType.Receipt + "' "
	  												 		+ "AND r.name = '"+ objectParent.getParent() +"' "
	  												 		+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` " + 
	  												 		"LEFT JOIN " + 
	  									  				"(SELECT DATE_FORMAT(i.shiftDate, '%Y-%m-%d-%H') AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
	  									  				+ "FROM invoiceTransaction i INNER JOIN restaurant_shift r ON i.shiftCode  = r.code "
	  									  				+ "WHERE i.activityType = '" + ActivityType.Payment + "' "
	  									  				+ "AND r.name = '"+ objectParent.getParent() +"' "
	  									  				+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node))) AS `e` ORDER BY `node`")
	  				        .field("(CASE WHEN (e.node1 IS NULL) THEN e.node2 ELSE e.node1 END) AS `node`", "node")
	  				        .field("COALESCE(e.final, 0) AS `totalReceipt`", "final")
	  				        .field("COALESCE(e.total, 0) AS `totalPayment`", "total");
	  	    		}	
	  	  		} else {
	  	  			if(parameter.equals("ThuChiCongNo")){
	  	  				String addQuery = "";
	  	        	if(conds.containsKey("department") && !conds.get("department").toString().isEmpty()){
	  	        		addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("department").toString() +"' ";
	  	        	} else {
	  	        		if(conds.containsKey("cashier1")){
	  	        			if(!conds.get("cashier1").toString().isEmpty())     				
	  	        				addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("cashier1").toString() +"' ";
	  	        			if(!conds.get("cashier2").toString().isEmpty())
	  	        				addQuery = " AND i.employeeCode = '"+ conds.get("cashier2").toString() +"'";
	  	        		} else {
	  	        			if(conds.containsKey("partner1")){
	  	        				if(!conds.get("partner1").toString().isEmpty())     				
	  	          				addQuery = " AND SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '"+ conds.get("partner1").toString() +"' ";
	  	          			if(!conds.get("partner2").toString().isEmpty())
	  	          				addQuery = " AND i.customerCode = '"+ conds.get("partner2").toString() +"' ";
	  	        			} else {
	  	        				if(conds.containsKey("location1")){
	  	        					if(!conds.get("location1").toString().isEmpty())     				
	  	            				addQuery = " AND i.locationCode = '"+ conds.get("location1").toString() +"' ";
	  	            			if(!conds.get("location2").toString().isEmpty())
	  	            				addQuery = " AND i.tableCode = '"+ conds.get("location2").toString() +"' ";
	  	        				}
	  	        				else
	  	        				{
	  	        					if(conds.containsKey("store") && !conds.get("store").toString().isEmpty())
	  	        		        		addQuery = " AND SUBSTRING_INDEX(i.storeCode, '/', -1) = '"+ conds.get("store").toString() +"' ";
	  	        					else
	  	        					{
	  	        						if(conds.containsKey("project") && !conds.get("project").toString().isEmpty())
	  	        			        		addQuery = " AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"+ conds.get("project").toString() +"'";
	  	        					}
	  	        				}
	  	        			}
	  	        		}
	  	        	}
	  	    			rQuery.table("((SELECT " +
	  	        	"a.node AS `node1`,b.node AS `node2`,a.finalReceipt AS `finalReceipt`,a.finalPayment AS `finalPayment`,b.totalReceipt AS `totalReceipt`,b.totalPayment AS `totalPayment`"+
	  	    					"FROM ((SELECT "+
	  		    											"DATE_FORMAT(i.shiftDate, '%Y-%m-%d-%H') AS `node`, " +
	  		    											"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.finalCharge ELSE 0 END) AS `finalReceipt`, " +
	  		    											"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.finalCharge ELSE 0 END) AS `finalPayment` " +
	  	    										 "FROM `invoiceDetail` i INNER JOIN restaurant_shift r ON i.shiftCode  = r.code "
	  	    										 + "WHERE r.name = '"+ objectParent.getParent() +"' "
	  	    										 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
	  	    										 "GROUP BY `node`) AS `a` " +
	  	    										 "LEFT JOIN " +
	  	    										 "(SELECT " +
	  	    										 		"DATE_FORMAT(i.shiftDate, '%Y-%m-%d-%H') AS `node`, " +
	  	    										 		"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.total ELSE 0 END) AS `totalReceipt`, " +
	  	    										 		"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.total ELSE 0 END) AS `totalPayment` " +
	  	    										 "FROM `invoiceTransaction` i INNER JOIN restaurant_shift r ON i.shiftCode  = r.code "
	  	    										 + "WHERE r.name = '"+ objectParent.getParent() +"' "
	  	    										 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
	  	    										 "GROUP BY `node`) AS `b` ON a.node = b.node) " +
	  	    										 "ORDER BY `node`)" +
	  	    										 "UNION "+
	  	    										 "(SELECT "+
	  	    										"a.node AS `node1`,b.node AS `node2`,a.finalReceipt AS `finalReceipt`,a.finalPayment AS `finalPayment`,b.totalReceipt AS `totalReceipt`,b.totalPayment AS `totalPayment`"+
	  	    			  	    					"FROM ((SELECT "+
	  	    			  		    											"DATE_FORMAT(i.shiftDate, '%Y-%m-%d-%H') AS `node`, " +
	  	    			  		    											"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.finalCharge ELSE 0 END) AS `finalReceipt`, " +
	  	    			  		    											"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.finalCharge ELSE 0 END) AS `finalPayment` " +
	  	    			  	    										 "FROM `invoiceDetail` i INNER JOIN restaurant_shift r ON i.shiftCode  = r.code "
	  	    			  	    										 + "WHERE r.name = '"+ objectParent.getParent() +"' "
	  	    			  	    										 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
	  	    			  	    										 "GROUP BY `node`) AS `a` " +
	  	    			  	    										 "RIGHT JOIN " +
	  	    			  	    										 "(SELECT " +
	  	    			  	    										 		"DATE_FORMAT(i.shiftDate, '%Y-%m-%d-%H') AS `node`, " +
	  	    			  	    										 		"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.total ELSE 0 END) AS `totalReceipt`, " +
	  	    			  	    										 		"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.total ELSE 0 END) AS `totalPayment` " +
	  	    			  	    										 "FROM `invoiceTransaction` i INNER JOIN restaurant_shift r ON i.shiftCode  = r.code "
	  	    			  	    										 + "WHERE r.name = '"+ objectParent.getParent() +"' "
	  	    			  	    										 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
	  	    			  	    										 "GROUP BY `node`) AS `b` ON a.node = b.node) " +
	  	    			  	    										 "ORDER BY `node`))" +
	  	    			  	    										 "AS `e` ORDER BY `node` DESC"
	  	    					)
	  	    	        .field("(CASE WHEN (e.node1 IS NULL) THEN e.node2 ELSE e.node1 END) AS `node`", "node")
	  	    	        .field("COALESCE(e.finalReceipt, 0) AS `final Receipt`", "final")
	  	    	        .field("COALESCE(e.finalPayment, 0) AS `final Payment`", "total")
	  	    	        .field("COALESCE(e.totalReceipt, 0) AS `total Receipt`", "final1")
	  	    	        .field("COALESCE(e.totalPayment, 0) AS `total Payment`", "total1");
	  	    		}
	  	  		}
	  	  	}
	  	}
	  	return rQuery;
	  }
  private SQLSelectQuery SQLQueryString_GetShift(ReportForTimeEntity objectParent, ActivityType activityType, String parameter){
	  	SQLSelectQuery rQuery = new SQLSelectQuery();
	  	/**
	  	 ****************************************
	  	 **************************************** 
	  	 *** THỐNG KÊ [DOANH THU] + [CHI PHÍ] ***
	  	 ****************************************
	  	 **************************************** 
	  	 */
	  	if(activityType != null){
	  		if(conds.containsKey("product1")){
	  			String addQuery = "";
					if (!conds.get("product1").toString().equals("")) {
						addQuery = " INNER JOIN product p ON p.code = ii.productCode INNER JOIN product_productTag t ON t.productId = p.Id INNER JOIN warehouse_productTag w ON w.id = t.productTagId ";
						rQuery.cond("w.code = '" + conds.get("product1").toString() + "'");
					}
	  			if (!conds.get("product2").toString().equals(""))
	  				rQuery.cond("ii.productCode = '" + conds.get("product2").toString() + "'");
	  			else rQuery.cond("ii.productCode IS NOT NULL");
	  			rQuery.table("invoiceItem ii INNER JOIN invoicedetail id on ii.invoiceCode = id.invoiceCode inner join restaurant_shift r on id.shiftCode = r.code" + addQuery)
	  						.field("r.name AS `node`", "node")
		  					.field("COALESCE(SUM(ii.finalCharge), 0) AS `final`", "final")
		  					.cond("ii.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
		  					.cond("ii.activityType = '" + activityType + "'")
		  					.cond("DATE_FORMAT(id.shiftDate, '%Y-%m-%d') = '"+ objectParent.getParent() +"'")
		  					.groupBy("`node`");
	    	} else {
	    		String addQuery = "";
	      	if(conds.containsKey("department") && !conds.get("department").toString().isEmpty()){
	      		addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("department").toString() +"'";
	      	} else {
	      		if(conds.containsKey("cashier1")){
	      			if(!conds.get("cashier1").toString().isEmpty())     				
	      				addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("cashier1").toString() +"'";
	      			if(!conds.get("cashier2").toString().isEmpty())
	      				addQuery = addQuery + " AND i.employeeCode = '"+ conds.get("cashier2").toString() +"'";
	      		} else {
	      			if(conds.containsKey("partner1")){
	      				if(!conds.get("partner1").toString().isEmpty())     				
	        				addQuery = " AND SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '"+ conds.get("partner1").toString() +"'";
	        			if(!conds.get("partner2").toString().isEmpty())
	        				addQuery = addQuery + " AND i.customerCode = '"+ conds.get("partner2").toString() +"'";
	      			} else {
	      				if(conds.containsKey("location1")){
	      					if(!conds.get("location1").toString().isEmpty())     				
	          				addQuery = " AND i.locationCode = '"+ conds.get("location1").toString() +"'";
	          			if(!conds.get("location2").toString().isEmpty())
	          				addQuery = addQuery + " AND i.tableCode = '"+ conds.get("location2").toString() +"'";
	      				}
	      				else
	      				{
	      					if(conds.containsKey("store") && !conds.get("store").toString().isEmpty())
	      			      		addQuery = " AND SUBSTRING_INDEX(i.storeCode, '/', -1) = '"+ conds.get("store").toString() +"'";
	      					else
	    					{
	    						if(conds.containsKey("project") && !conds.get("project").toString().isEmpty())
	    			        		addQuery = " AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"+ conds.get("project").toString() +"'";
	    					}
	      				}
	      			}
	      		}
	      	}
	      	rQuery.table("((SELECT a.node AS `node1`, b.node AS `node2`, a.final, b.total "
	      			+ "FROM "
	      			+ "(SELECT r.name AS `node`, COALESCE(SUM(i.finalCharge), 0) AS `final` " 
					+ "FROM invoiceDetail i INNER JOIN restaurant_shift r on i.shiftCode = r.code "
					+ "WHERE i.activityType = '" + activityType + "' "
					+ "AND DATE_FORMAT(i.shiftDate, '%Y-%m-%d') = '"+ objectParent.getParent() +"' "
					+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` "
					+ "LEFT JOIN "
					+ "(SELECT r.name AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
					+ "FROM invoiceTransaction i INNER JOIN restaurant_shift r on i.shiftCode = r.code "
					+ "WHERE i.activityType = '" + activityType + "' "
					+ "AND DATE_FORMAT(i.shiftDate, '%Y-%m-%d') = '"+ objectParent.getParent() +"' "
					+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node) "
							+ "UNION "
							+ "(SELECT a.node AS `node1`, b.node AS `node2`, a.final, b.total "
							+ "FROM "
			      			+ "(SELECT r.name AS `node`, COALESCE(SUM(i.finalCharge), 0) AS `final` " 
							+ "FROM invoiceDetail i INNER JOIN restaurant_shift r on i.shiftCode = r.code "
							+ "WHERE i.activityType = '" + activityType + "' "
							+ "AND DATE_FORMAT(i.shiftDate, '%Y-%m-%d') = '"+ objectParent.getParent() +"' "
							+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` "
							+ "RIGHT JOIN "
							+ "(SELECT r.name AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
							+ "FROM invoiceTransaction i INNER JOIN restaurant_shift r on i.shiftCode = r.code "
							+ "WHERE i.activityType = '" + activityType + "' "
							+ "AND DATE_FORMAT(i.shiftDate, '%Y-%m-%d') = '"+ objectParent.getParent() +"' "
							+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node)) "
									+ "AS `e` ORDER BY `node` DESC"
	      			)
		            .field("(CASE WHEN (e.node1 IS NULL) THEN e.node2 ELSE e.node1 END) AS `node`", "node")
		            .field("COALESCE(e.final, 0) AS `totalReceipt`", "final")
		            .field("COALESCE(e.total, 0) AS `totalPayment`", "total");
	    	}
	  	} 
	  	/**
	  	 ************************************************** 
	  	 ************************************************** 
	  	 *** THỐNG KÊ [THU CHI LÃI] + [THU CHI CÔNG NỢ] ***
	  	 **************************************************
	  	 ************************************************** 
	  	 */
	  	else {
	  		if(parameter.equals("ThuChiLai")){
	  			if(conds.containsKey("product1")){  				
	  				String addQuery = "";
	  				if (!conds.get("product1").toString().equals("")) {
							addQuery = "INNER JOIN product p ON p.code = ii.productCode INNER JOIN product_productTag t ON t.productId = p.Id INNER JOIN warehouse_productTag w ON w.id = t.productTagId ";
							rQuery.cond("w.code = '" + conds.get("product1").toString() + "'");
	  				}
		  			if (!conds.get("product2").toString().equals(""))
		  				rQuery.cond("ii.productCode = '" + conds.get("product2").toString() + "'");
		  			rQuery.table("InvoiceItem ii INNER JOIN invoicedetail id on ii.invoiceCode = id.invoiceCode inner join restaurant_shift r on id.shiftCode = r.code " + addQuery)
		  			 			.field("r.name AS `node`", "node")
		  			 			.field("SUM(CASE WHEN ii.activityType = 'Receipt' THEN (ii.finalCharge * ii.currencyRate) ELSE 0 END) AS `finalReceipt`", "final")
		  			 			.field("SUM(CASE WHEN ii.activityType = 'Payment' THEN (ii.finalCharge * ii.currencyRate) ELSE 0 END) AS `finalPayment`", "total")
		  						.cond("ii.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
		  			 			.cond("ii.productCode IS NOT NULL")
		  						.cond("DATE_FORMAT(id.shiftDate, '%Y-%m-%d') = '"+ objectParent.getParent() +"'")
		  			 			.groupBy("`node`");
	    		} else {
	    			String addQuery = "";
	        	if(conds.containsKey("department") && !conds.get("department").toString().isEmpty()){
	        		addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("department").toString() +"'";
	        	} else {
	        		if(conds.containsKey("cashier1")){
	        			if(!conds.get("cashier1").toString().isEmpty())     				
	        				addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("cashier1").toString() +"'";
	        			if(!conds.get("cashier2").toString().isEmpty())
	        				addQuery = addQuery + " AND i.employeeCode = '"+ conds.get("cashier2").toString() +"'";
	        		} else {
	        			if(conds.containsKey("partner1")){
	        				if(!conds.get("partner1").toString().isEmpty())     				
	          				addQuery = " AND SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '"+ conds.get("partner1").toString() +"'";
	          			if(!conds.get("partner2").toString().isEmpty())
	          				addQuery = addQuery + " AND i.customerCode = '"+ conds.get("partner2").toString() +"'";
	        			} else {
	        				if(conds.containsKey("location1")){
	        					if(!conds.get("location1").toString().isEmpty())     				
	            				addQuery = " AND i.locationCode = '"+ conds.get("location1").toString() +"'";
	            			if(!conds.get("location2").toString().isEmpty())
	            				addQuery = addQuery + " AND i.tableCode = '"+ conds.get("location2").toString() +"'";
	        				}
	        				else
	        				{
	        					if(conds.containsKey("store") && !conds.get("store").toString().isEmpty())
	        		        		addQuery = " AND SUBSTRING_INDEX(i.storeCode, '/', -1) = '"+ conds.get("storeCode").toString() +"'";
	        					else
	        					{
	        						if(conds.containsKey("project") && !conds.get("project").toString().isEmpty())
	        			        		addQuery = " AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"+ conds.get("project").toString() +"'";
	        					}
	        				}
	        			}
	        		}
	        	}   	
	      	rQuery.table("((SELECT " +
												     "a.node AS `node1`, " +
												     "a.final AS `final`, " +
												     "b.node AS `node2`, " +
												     "b.total AS `total` " +
									       "FROM " +
									       		 "((SELECT r.name AS `node`, COALESCE(SUM(i.total), 0) AS `final` "
									       		 + "FROM invoiceTransaction i  INNER JOIN restaurant_shift r on i.shiftCode = r.code "
									       		 + "WHERE i.activityType = '" + ActivityType.Receipt + "' "
									       		 + "AND DATE_FORMAT(i.shiftDate, '%Y-%m-%d') = '"+ objectParent.getParent() +"' "
									       		 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` " + 
									       		 "RIGHT JOIN " + 
									       		 "(SELECT r.name AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
									       		 + "FROM invoiceTransaction i INNER JOIN restaurant_shift r on i.shiftCode = r.code "
									       		 + "WHERE i.activityType = '" + ActivityType.Payment + "' "
									       		 + "AND DATE_FORMAT(i.shiftDate, '%Y-%m-%d') = '"+ objectParent.getParent() +"' "
									       		 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node)) " + 
									       "UNION " +
									       "(SELECT " +
												  	"a.node AS `node1`, " +
												    "a.final AS `final`, " +
												    "b.node AS `node2`, " +
												    "b.total AS `total` " +
												 "FROM " +
												 		"((SELECT r.name AS `node`, COALESCE(SUM(i.total), 0) AS `final` "
												 		+ "FROM invoiceTransaction i INNER JOIN restaurant_shift r on i.shiftCode = r.code "
												 		+ "WHERE i.activityType = '" + ActivityType.Receipt + "' "
												 		+ "AND DATE_FORMAT(i.shiftDate, '%Y-%m-%d') = '"+ objectParent.getParent() +"' "
												 		+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` " + 
												 		"LEFT JOIN " + 
									  				"(SELECT r.name AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
									  				+ "FROM invoiceTransaction i INNER JOIN restaurant_shift r on i.shiftCode = r.code "
									  				+ "WHERE i.activityType = '" + ActivityType.Payment + "' "
									  				+ "AND DATE_FORMAT(i.shiftDate, '%Y-%m-%d') = '"+ objectParent.getParent() +"' "
									  				+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node))) AS `e` ORDER BY `node`")
				        .field("(CASE WHEN (e.node1 IS NULL) THEN e.node2 ELSE e.node1 END) AS `node`", "node")
				        .field("COALESCE(e.final, 0) AS `totalReceipt`", "final")
				        .field("COALESCE(e.total, 0) AS `totalPayment`", "total");
	    		}	
	  		} else {
	  			if(parameter.equals("ThuChiCongNo")){
	  				String addQuery = "";
	        	if(conds.containsKey("department") && !conds.get("department").toString().isEmpty()){
	        		addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("department").toString() +"' ";
	        	} else {
	        		if(conds.containsKey("cashier1")){
	        			if(!conds.get("cashier1").toString().isEmpty())     				
	        				addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("cashier1").toString() +"' ";
	        			if(!conds.get("cashier2").toString().isEmpty())
	        				addQuery = " AND i.employeeCode = '"+ conds.get("cashier2").toString() +"'";
	        		} else {
	        			if(conds.containsKey("partner1")){
	        				if(!conds.get("partner1").toString().isEmpty())     				
	          				addQuery = " AND SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '"+ conds.get("partner1").toString() +"' ";
	          			if(!conds.get("partner2").toString().isEmpty())
	          				addQuery = " AND i.customerCode = '"+ conds.get("partner2").toString() +"' ";
	        			} else {
	        				if(conds.containsKey("location1")){
	        					if(!conds.get("location1").toString().isEmpty())     				
	            				addQuery = " AND i.locationCode = '"+ conds.get("location1").toString() +"' ";
	            			if(!conds.get("location2").toString().isEmpty())
	            				addQuery = " AND i.tableCode = '"+ conds.get("location2").toString() +"' ";
	        				}
	        				else
	        				{
	        					if(conds.containsKey("store") && !conds.get("store").toString().isEmpty())
	        		        		addQuery = " AND SUBSTRING_INDEX(i.storeCode, '/', -1) = '"+ conds.get("store").toString() +"' ";
	        					else
	        					{
	        						if(conds.containsKey("project") && !conds.get("project").toString().isEmpty())
	        			        		addQuery = " AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"+ conds.get("project").toString() +"'";
	        					}
	        				}
	        			}
	        		}
	        	}
	    			rQuery.table("((SELECT " +
	    						"a.node AS `node1`,b.node AS `node2`,a.finalReceipt AS `finalReceipt`,a.finalPayment AS `finalPayment`,b.totalReceipt AS `totalReceipt`,b.totalPayment AS `totalPayment`"+
	    					"FROM ((SELECT "+
		    											" r.name AS `node`, " +
		    											"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.finalCharge ELSE 0 END) AS `finalReceipt`, " +
		    											"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.finalCharge ELSE 0 END) AS `finalPayment` " +
	    										 "FROM `invoiceDetail` i INNER JOIN `restaurant_shift` r on i.shiftCode = r.code "
	    										 + "WHERE DATE_FORMAT(i.shiftDate, '%Y-%m-%d') = '"+ objectParent.getParent() +"' "
	    										 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
	    										 "GROUP BY `node`) AS `a` " +
	    										 "LEFT JOIN " +
	    										 "(SELECT " +
	    										 		"r.name AS `node`, " +
	    										 		"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.total ELSE 0 END) AS `totalReceipt`, " +
	    										 		"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.total ELSE 0 END) AS `totalPayment` " +
	    										 "FROM `invoiceTransaction` i INNER JOIN `restaurant_shift` r on i.shiftCode = r.code "
	    										 + "WHERE DATE_FORMAT(i.shiftDate, '%Y-%m-%d') = '"+ objectParent.getParent() +"' "
	    										 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
	    										 "GROUP BY `node`) AS `b` ON a.node = b.node) " +
	    										 "ORDER BY `node`) "+
	    										 "UNION "+
	    										 "(SELECT "+
	    										 "a.node AS `node1`,b.node AS `node2`,a.finalReceipt AS `finalReceipt`,a.finalPayment AS `finalPayment`,b.totalReceipt AS `totalReceipt`,b.totalPayment AS `totalPayment`"+
	    					    					"FROM ((SELECT "+
	    						    											" r.name AS `node`, " +
	    						    											"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.finalCharge ELSE 0 END) AS `finalReceipt`, " +
	    						    											"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.finalCharge ELSE 0 END) AS `finalPayment` " +
	    					    										 "FROM `invoiceDetail` i INNER JOIN `restaurant_shift` r on i.shiftCode = r.code "
	    					    										 + "WHERE DATE_FORMAT(i.shiftDate, '%Y-%m-%d') = '"+ objectParent.getParent() +"' "
	    					    										 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
	    					    										 "GROUP BY `node`) AS `a` " +
	    					    										 "RIGHT JOIN " +
	    					    										 "(SELECT " +
	    					    										 		"r.name AS `node`, " +
	    					    										 		"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.total ELSE 0 END) AS `totalReceipt`, " +
	    					    										 		"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.total ELSE 0 END) AS `totalPayment` " +
	    					    										 "FROM `invoiceTransaction` i INNER JOIN `restaurant_shift` r on i.shiftCode = r.code "
	    					    										 + "WHERE DATE_FORMAT(i.shiftDate, '%Y-%m-%d') = '"+ objectParent.getParent() +"' "
	    					    										 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
	    					    										 "GROUP BY `node`) AS `b` ON a.node = b.node) " +
	    					    										 "ORDER BY `node`)) "+
	    					    										 "AS `e` ORDER BY `node` DESC"
	    										 
	    					)
	    	        .field("(CASE WHEN (e.node1 IS NULL) THEN e.node2 ELSE e.node1 END) AS `node`", "node")
	    	        .field("COALESCE(e.finalReceipt, 0) AS `final Receipt`", "final")
	    	        .field("COALESCE(e.finalPayment, 0) AS `final Payment`", "total")
	    	        .field("COALESCE(e.totalReceipt, 0) AS `total Receipt`", "final1")
	    	        .field("COALESCE(e.totalPayment, 0) AS `total Payment`", "total1");
	    		}
	  		}
	  	}
	  	
	  	return rQuery;
	  }
  	
  private SQLSelectQuery SQLQueryString_GetInvoice(ReportForTimeEntity objectParent, ActivityType activityType, String parameter){
	  	SQLSelectQuery rQuery = new SQLSelectQuery();
	  	/**
	  	 ****************************************
	  	 **************************************** 
	  	 *** THỐNG KÊ [DOANH THU] + [CHI PHÍ] ***
	  	 ****************************************
	  	 **************************************** 
	  	 */
  		if(activityType != null){
  	  		if(conds.containsKey("product1")){
  	  			String addQuery = "";
  					if (!conds.get("product1").toString().equals("")) {
  						addQuery = " INNER JOIN product p ON p.code = i.productCode INNER JOIN product_productTag t ON t.productId = p.Id INNER JOIN warehouse_productTag w ON w.id = t.productTagId ";
  						rQuery.cond("w.code = '" + conds.get("product1").toString() + "'");
  					}
  	  			if (!conds.get("product2").toString().equals(""))
  	  				rQuery.cond("i.productCode = '" + conds.get("product2").toString() + "'");
  	  			else rQuery.cond("i.productCode IS NOT NULL");
  	  			rQuery.table("InvoiceDetail id INNER JOIN invoiceItem i ON id.id = i.invoiceId" + addQuery)
  	  						.field("concat(id.invoiceCode,'--',id.invoiceName) AS `node`", "node")
  		  					.field("COALESCE(SUM(i.finalCharge), 0) AS `final`", "final")
  		  					.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
  		  					.cond("i.activityType = '" + activityType + "'")
  		  					.cond("DATE_FORMAT(i.startDate, '"+ dateFormat+"') = '"+ objectParent.getParent() +"'")
  		  					.groupBy("`node`");
  	    	} else {
  	    		String addQuery = "";
  	      	if(conds.containsKey("department") && !conds.get("department").toString().isEmpty()){
  	      		addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("department").toString() +"'";
  	      	} else {
  	      		if(conds.containsKey("cashier1")){
  	      			if(!conds.get("cashier1").toString().isEmpty())     				
  	      				addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("cashier1").toString() +"'";
  	      			if(!conds.get("cashier2").toString().isEmpty())
  	      				addQuery = addQuery + " AND i.employeeCode = '"+ conds.get("cashier2").toString() +"'";
  	      		} else {
  	      			if(conds.containsKey("partner1")){
  	      				if(!conds.get("partner1").toString().isEmpty())     				
  	        				addQuery = " AND SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '"+ conds.get("partner1").toString() +"'";
  	        			if(!conds.get("partner2").toString().isEmpty())
  	        				addQuery = addQuery + " AND i.customerCode = '"+ conds.get("partner2").toString() +"'";
  	      			} else {
  	      				if(conds.containsKey("location1")){
  	      					if(!conds.get("location1").toString().isEmpty())     				
  	          				addQuery = " AND i.locationCode = '"+ conds.get("location1").toString() +"'";
  	          			if(!conds.get("location2").toString().isEmpty())
  	          				addQuery = addQuery + " AND i.tableCode = '"+ conds.get("location2").toString() +"'";
  	      				}
  	      				else
  	      				{
  	      					if(conds.containsKey("store") && !conds.get("store").toString().isEmpty())
  	      			      		addQuery = " AND SUBSTRING_INDEX(i.storeCode, '/', -1) = '"+ conds.get("store").toString() +"'";
  	      					else
  	    					{
  	    						if(conds.containsKey("project") && !conds.get("project").toString().isEmpty())
  	    			        		addQuery = " AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"+ conds.get("project").toString() +"'";
  	    					}
  	      				}
  	      			}
  	      		}
  	      	}
  	      	rQuery.table("((SELECT a.node AS `node1`, b.node AS `node2`, a.final, b.total "
  	      			+ "FROM "
  	      			+ "(SELECT concat(i.invoiceCode,'--',i.invoiceName) AS `node`, COALESCE(SUM(i.finalCharge), 0) AS `final` " 
  					+ "FROM invoiceDetail i "
  					+ "WHERE i.activityType = '" + activityType + "' "
  					+ "AND DATE_FORMAT(i.startDate, '"+ dateFormat+"') = '"+ objectParent.getParent() +"' "
  					+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` "
  					+ "LEFT JOIN "
  					+ "(SELECT concat(i.invoiceCode,'--',i.invoiceName) AS `node`, COALESCE(SUM(it.total), 0) AS `total` "
  					+ "FROM invoiceTransaction it INNER JOIN Invoicedetail i on it.invoiceId = i.id "
  					+ "WHERE it.activityType = '" + activityType + "' "
  					+ "AND DATE_FORMAT(it.transactionDate, '"+ dateFormat+"') = '"+ objectParent.getParent() +"' "
  					+ "AND it.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node) "
  							+ "UNION "
  							+ "(SELECT a.node AS `node1`, b.node AS `node2`, a.final, b.total "
  							+ "FROM "
  		  	      			+ "(SELECT concat(i.invoiceCode,'--',i.invoiceName) AS `node`, COALESCE(SUM(i.finalCharge), 0) AS `final` " 
  		  					+ "FROM invoiceDetail i "
  		  					+ "WHERE i.activityType = '" + activityType + "' "
  		  					+ "AND DATE_FORMAT(i.startDate, '"+ dateFormat+"') = '"+ objectParent.getParent() +"' "
  		  					+ "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` "
  		  					+ "RIGHT JOIN "
  		  					+ "(SELECT concat(i.invoiceCode,'--',i.invoiceName) AS `node`, COALESCE(SUM(i.total), 0) AS `total` "
  		  					+ "FROM invoiceTransaction it INNER JOIN Invoicedetail i on it.invoiceId = i.id "
  		  					+ "WHERE it.activityType = '" + activityType + "' "
  		  					+ "AND DATE_FORMAT(it.transactionDate, '"+ dateFormat+"') = '"+ objectParent.getParent() +"' "
  		  					+ "AND it.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node)) "
  		  							+ "AS `e` ORDER BY `node` DESC"
  	      			)
  		            .field("(CASE WHEN (e.node1 IS NULL) THEN e.node2 ELSE e.node1 END) AS `node`", "node")
  		            .field(" COALESCE(e.final, 0) AS `totalReceipt`", "final")
  		            .field(" COALESCE(e.total, 0) AS `totalPayment`", "total");
  	    	}
  	  	} 
  	  	/**
  	  	 ************************************************** 
  	  	 ************************************************** 
  	  	 *** THỐNG KÊ [THU CHI LÃI] + [THU CHI CÔNG NỢ] ***
  	  	 **************************************************
  	  	 ************************************************** 
  	  	 */
  	  	else {
  	  		if(parameter.equals("ThuChiLai")){
  	  			if(conds.containsKey("product1")){  				
  	  				String addQuery = "";
  	  				if (!conds.get("product1").toString().equals("")) {
  							addQuery = "INNER JOIN product p ON p.code = i.productCode INNER JOIN product_productTag t ON t.productId = p.Id INNER JOIN warehouse_productTag w ON w.id = t.productTagId ";
  							rQuery.cond("w.code = '" + conds.get("product1").toString() + "'");
  	  				}
  		  			if (!conds.get("product2").toString().equals(""))
  		  				rQuery.cond("i.productCode = '" + conds.get("product2").toString() + "'");
  		  			rQuery.table("InvoiceDetail id INNER JOIN InvoiceItem i ON id.id = i.invoiceId " + addQuery)
  		  			 			.field("concat(id.invoiceCode,'--',id.invoiceName) AS `node`", "node")
  		  			 			.field("SUM(CASE WHEN i.activityType = 'Receipt' THEN (i.finalCharge * i.currencyRate) ELSE 0 END) AS `finalReceipt`", "final")
  		  			 			.field("SUM(CASE WHEN i.activityType = 'Payment' THEN (i.finalCharge * i.currencyRate) ELSE 0 END) AS `finalPayment`", "total")
  		  						.cond("i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'")
  		  			 			.cond("i.productCode IS NOT NULL")
  		  						.cond("DATE_FORMAT(i.startDate, '"+ dateFormat+"') = '"+ objectParent.getParent() +"'")
  		  			 			.groupBy("`node`");
  	    		} else {
  	    			String addQuery = "";
  	        	if(conds.containsKey("department") && !conds.get("department").toString().isEmpty()){
  	        		addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("department").toString() +"'";
  	        	} else {
  	        		if(conds.containsKey("cashier1")){
  	        			if(!conds.get("cashier1").toString().isEmpty())     				
  	        				addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("cashier1").toString() +"'";
  	        			if(!conds.get("cashier2").toString().isEmpty())
  	        				addQuery = addQuery + " AND i.employeeCode = '"+ conds.get("cashier2").toString() +"'";
  	        		} else {
  	        			if(conds.containsKey("partner1")){
  	        				if(!conds.get("partner1").toString().isEmpty())     				
  	          				addQuery = " AND SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '"+ conds.get("partner1").toString() +"'";
  	          			if(!conds.get("partner2").toString().isEmpty())
  	          				addQuery = addQuery + " AND i.customerCode = '"+ conds.get("partner2").toString() +"'";
  	        			} else {
  	        				if(conds.containsKey("location1")){
  	        					if(!conds.get("location1").toString().isEmpty())     				
  	            				addQuery = " AND i.locationCode = '"+ conds.get("location1").toString() +"'";
  	            			if(!conds.get("location2").toString().isEmpty())
  	            				addQuery = addQuery + " AND i.tableCode = '"+ conds.get("location2").toString() +"'";
  	        				}
  	        				else
  	        				{
  	        					if(conds.containsKey("store") && !conds.get("store").toString().isEmpty())
  	        		        		addQuery = " AND SUBSTRING_INDEX(i.storeCode, '/', -1) = '"+ conds.get("storeCode").toString() +"'";
  	        					else
  	        					{
  	        						if(conds.containsKey("project") && !conds.get("project").toString().isEmpty())
  	        			        		addQuery = " AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"+ conds.get("project").toString() +"'";
  	        					}
  	        				}
  	        			}
  	        		}
  	        	}   	
  	      	rQuery.table("((SELECT " +
  												     "a.node AS `node1`, " +
  												     "a.final AS `final`, " +
  												     "b.node AS `node2`, " +
  												     "b.total AS `total` " +
  									       "FROM " +
  									       		 "((SELECT concat(i.invoiceCode,'--',i.invoiceName) AS `node`, COALESCE(SUM(it.total), 0) AS `final` "
  									       		 + "FROM invoiceTransaction it INNER JOIN Invoicedetail i on it.invoiceId = i.id "
  									       		 + "WHERE it.activityType = '" + ActivityType.Receipt + "' "
  									       		 + "AND DATE_FORMAT(it.transactionDate, '"+ dateFormat+"') = '"+ objectParent.getParent() +"' "
  									       		 + "AND it.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` " + 
  									       		 "RIGHT JOIN " + 
  									       		 "(SELECT concat(i.invoiceCode,'--',i.invoiceName) AS `node`, COALESCE(SUM(it.total), 0) AS `total` "
  									       		 + "FROM invoiceTransaction it INNER JOIN Invoicedetail i on it.invoiceId = i.id "
  									       		 + "WHERE it.activityType = '" + ActivityType.Payment + "' "
  									       		 + "AND DATE_FORMAT(it.transactionDate, '"+ dateFormat+"') = '"+ objectParent.getParent() +"' "
  									       		 + "AND it.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node)) " + 
  									       "UNION " +
  									       "(SELECT " +
  												  	"a.node AS `node1`, " +
  												    "a.final AS `final`, " +
  												    "b.node AS `node2`, " +
  												    "b.total AS `total` " +
  												 "FROM " +
  												 		"((SELECT concat(i.invoiceCode,'--',i.invoiceName) AS `node`, COALESCE(SUM(it.total), 0) AS `final` "
  												 		+ "FROM invoiceTransaction it INNER JOIN Invoicedetail i on it.invoiceId = i.id "
  												 		+ "WHERE it.activityType = '" + ActivityType.Receipt + "' "
  												 		+ "AND DATE_FORMAT(it.transactionDate, '"+ dateFormat+"') = '"+ objectParent.getParent() +"' "
  												 		+ "AND it.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `a` " + 
  												 		"LEFT JOIN " + 
  									  				"(SELECT concat(i.invoiceCode,'--',i.invoiceName) AS `node`, COALESCE(SUM(it.total), 0) AS `total` "
  									  				+ "FROM invoiceTransaction it INNER JOIN Invoicedetail i on it.invoiceId = i.id "
  									  				+ "WHERE it.activityType = '" + ActivityType.Payment + "' "
  									  				+ "AND DATE_FORMAT(it.transactionDate, '"+ dateFormat+"') = '"+ objectParent.getParent() +"' "
  									  				+ "AND it.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' "+ addQuery +" GROUP BY `node`) AS `b` ON a.node = b.node))) AS `e` ORDER BY `node`")
  				        .field("(CASE WHEN (e.node1 IS NULL) THEN e.node2 ELSE e.node1 END) AS `node`", "node")
  				        .field("COALESCE(e.final, 0) AS `totalReceipt`", "final")
  				        .field("COALESCE(e.total, 0) AS `totalPayment`", "total");
  	    		}	
  	  		} else {
  	  			if(parameter.equals("ThuChiCongNo")){
  	  				String addQuery = "";
  	        	if(conds.containsKey("department") && !conds.get("department").toString().isEmpty()){
  	        		addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("department").toString() +"' ";
  	        	} else {
  	        		if(conds.containsKey("cashier1")){
  	        			if(!conds.get("cashier1").toString().isEmpty())     				
  	        				addQuery = " AND SUBSTRING_INDEX(i.departmentCode, '/', -1) = '"+ conds.get("cashier1").toString() +"' ";
  	        			if(!conds.get("cashier2").toString().isEmpty())
  	        				addQuery = " AND i.employeeCode = '"+ conds.get("cashier2").toString() +"'";
  	        		} else {
  	        			if(conds.containsKey("partner1")){
  	        				if(!conds.get("partner1").toString().isEmpty())     				
  	          				addQuery = " AND SUBSTRING_INDEX(i.groupCustomerCode, '/', -1) = '"+ conds.get("partner1").toString() +"' ";
  	          			if(!conds.get("partner2").toString().isEmpty())
  	          				addQuery = " AND i.customerCode = '"+ conds.get("partner2").toString() +"' ";
  	        			} else {
  	        				if(conds.containsKey("location1")){
  	        					if(!conds.get("location1").toString().isEmpty())     				
  	            				addQuery = " AND i.locationCode = '"+ conds.get("location1").toString() +"' ";
  	            			if(!conds.get("location2").toString().isEmpty())
  	            				addQuery = " AND i.tableCode = '"+ conds.get("location2").toString() +"' ";
  	        				}
  	        				else
  	        				{
  	        					if(conds.containsKey("store") && !conds.get("store").toString().isEmpty())
  	        		        		addQuery = " AND SUBSTRING_INDEX(i.storeCode, '/', -1) = '"+ conds.get("store").toString() +"' ";
  	        					else
  	        					{
  	        						if(conds.containsKey("project") && !conds.get("project").toString().isEmpty())
  	        			        		addQuery = " AND SUBSTRING_INDEX(i.projectCode, '/', -1) = '"+ conds.get("project").toString() +"'";
  	        					}
  	        				}
  	        			}
  	        		}
  	        	}
  	    			rQuery.table("((SELECT " +
  	    					"a.node AS `node1`,b.node AS `node2`,a.finalReceipt AS `finalReceipt`,a.finalPayment AS `finalPayment`,b.totalReceipt AS `totalReceipt`,b.totalPayment AS `totalPayment` "+
  	    					"FROM ((SELECT "+
  		    											"concat(i.invoiceCode,'--',i.invoiceName) AS `node`, " +
  		    											"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.finalCharge ELSE 0 END) AS `finalReceipt`, " +
  		    											"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.finalCharge ELSE 0 END) AS `finalPayment` " +
  	    										 "FROM `invoiceDetail` i "
  	    										 + "WHERE DATE_FORMAT(i.startDate, '"+ dateFormat+"') = '"+ objectParent.getParent() +"' "
  	    										 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
  	    										 "GROUP BY `node`) AS `a` " +
  	    										 "LEFT JOIN " +
  	    										 "(SELECT " +
  	    										 		"concat(i.invoiceCode,'--',i.invoiceName) AS `node`, " +
  	    										 		"SUM(CASE WHEN (it.activityType = 'Receipt') THEN it.total ELSE 0 END) AS `totalReceipt`, " +
  	    										 		"SUM(CASE WHEN (it.activityType = 'Payment') THEN it.total ELSE 0 END) AS `totalPayment` " +
  	    										 "FROM `invoiceTransaction` it INNER JOIN Invoicedetail i on it.invoiceId = i.id "
  	    										 + "WHERE DATE_FORMAT(it.transactionDate, '"+ dateFormat+"') = '"+ objectParent.getParent() +"' "
  	    										 + "AND it.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
  	    										 "GROUP BY `node`) AS `b` ON a.node = b.node) " +
  	    										 "ORDER BY `node`)"+
  	    										 "UNION "+
  	    										 "( SELECT "+
  	    										"a.node AS `node1`,b.node AS `node2`,a.finalReceipt AS `finalReceipt`,a.finalPayment AS `finalPayment`,b.totalReceipt AS `totalReceipt`,b.totalPayment AS `totalPayment` "+
  	    			  	    					"FROM ((SELECT "+
  	    			  		    											"concat(i.invoiceCode,'--',i.invoiceName) AS `node`, " +
  	    			  		    											"SUM(CASE WHEN (i.activityType = 'Receipt') THEN i.finalCharge ELSE 0 END) AS `finalReceipt`, " +
  	    			  		    											"SUM(CASE WHEN (i.activityType = 'Payment') THEN i.finalCharge ELSE 0 END) AS `finalPayment` " +
  	    			  	    										 "FROM `invoiceDetail` i "
  	    			  	    										 + "WHERE DATE_FORMAT(i.startDate, '"+ dateFormat+"') = '"+ objectParent.getParent() +"' "
  	    			  	    										 + "AND i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
  	    			  	    										 "GROUP BY `node`) AS `a` " +
  	    			  	    										 "RIGHT JOIN " +
  	    			  	    										 "(SELECT " +
  	    			  	    										 		"concat(i.invoiceCode,'--',i.invoiceName) AS `node`, " +
  	    			  	    										 		"SUM(CASE WHEN (it.activityType = 'Receipt') THEN it.total ELSE 0 END) AS `totalReceipt`, " +
  	    			  	    										 		"SUM(CASE WHEN (it.activityType = 'Payment') THEN it.total ELSE 0 END) AS `totalPayment` " +
  	    			  	    										 "FROM `invoiceTransaction` it INNER JOIN Invoicedetail i on it.invoiceId = i.id "
  	    			  	    										 + "WHERE DATE_FORMAT(it.transactionDate, '"+ dateFormat+"') = '"+ objectParent.getParent() +"' "
  	    			  	    										 + "AND it.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' " + addQuery +
  	    			  	    										 "GROUP BY `node`) AS `b` ON a.node = b.node) " +
  	    			  	    										 "ORDER BY `node`)) "+
  	    			  	    										 "AS `e` ORDER BY `node` DESC"
  	    					)
  	    	        .field("(CASE WHEN (e.node1 IS NULL) THEN e.node2 ELSE e.node1 END) AS `node`", "node")
  	    	        .field("COALESCE(e.finalReceipt, 0) AS `final Receipt`", "final")
  	    	        .field("COALESCE(e.finalPayment, 0) AS `final Payment`", "total")
  	    	        .field("COALESCE(e.totalReceipt, 0) AS `total Receipt`", "final1")
  	    	        .field("COALESCE(e.totalPayment, 0) AS `total Payment`", "total1");
  	    		}
  	  		}
  	  	}
	  	
	  	return rQuery;
  		}
  
  private List<ReportForTimeEntity> getRows(ReportForTimeEntity entityForTime, Type type) throws Exception {
	  List<ReportForTimeEntity> nodes = new ArrayList<ReportForTimeEntity>();
		int index = 1;
		if (entityForTime != null) {
			index = index + entityForTime.getIndex();
		}

		SQLSelectQuery rQuery = new SQLSelectQuery();
		
		if(setView == 0)
		{
			if(type.equals(Type.ROOT)){
				if (typeReport == 0)
					rQuery = SQLQueryString_GetYear(ActivityType.Payment, "");
				if (typeReport == 1)
					rQuery = SQLQueryString_GetYear(ActivityType.Receipt, "");
				if (typeReport == 2)
					rQuery = SQLQueryString_GetYear(null, "ThuChiLai");
				if (typeReport == 3)
					rQuery = SQLQueryString_GetYear(null, "ThuChiCongNo");
			} else {
				if(type.equals(Type.NODE_YEAR)){
					if (typeReport == 0)
						rQuery = SQLQueryString_GetMonth(entityForTime, ActivityType.Payment, "");
					if (typeReport == 1)
						rQuery = SQLQueryString_GetMonth(entityForTime, ActivityType.Receipt, "");
					if (typeReport == 2)
						rQuery = SQLQueryString_GetMonth(entityForTime, null, "ThuChiLai");
					if (typeReport == 3)
						rQuery = SQLQueryString_GetMonth(entityForTime, null, "ThuChiCongNo");
				} 
			}
		}
		else if( setView == 1)
		{
			dateFormat = "%Y-%m-%d";
			if(type.equals(Type.ROOT)){
				if (typeReport == 0)
					rQuery = SQLQueryString_GetYear(ActivityType.Payment, "");
				if (typeReport == 1)
					rQuery = SQLQueryString_GetYear(ActivityType.Receipt, "");
				if (typeReport == 2)
					rQuery = SQLQueryString_GetYear(null, "ThuChiLai");
				if (typeReport == 3)
					rQuery = SQLQueryString_GetYear(null, "ThuChiCongNo");
			} else {
				if(type.equals(Type.NODE_YEAR)){
					if (typeReport == 0)
						rQuery = SQLQueryString_GetMonth(entityForTime, ActivityType.Payment, "");
					if (typeReport == 1)
						rQuery = SQLQueryString_GetMonth(entityForTime, ActivityType.Receipt, "");
					if (typeReport == 2)
						rQuery = SQLQueryString_GetMonth(entityForTime, null, "ThuChiLai");
					if (typeReport == 3)
						rQuery = SQLQueryString_GetMonth(entityForTime, null, "ThuChiCongNo");
				} else {
					if(type.equals(Type.NODE_MONTH)){
						if (typeReport == 0)
							rQuery = SQLQueryString_GetDay(entityForTime, ActivityType.Payment, "");
						if (typeReport == 1)
							rQuery = SQLQueryString_GetDay(entityForTime, ActivityType.Receipt, "");
						if (typeReport == 2)
							rQuery = SQLQueryString_GetDay(entityForTime, null, "ThuChiLai");
						if (typeReport == 3)
							rQuery = SQLQueryString_GetDay(entityForTime, null, "ThuChiCongNo");
					}
					else{
						if(type.equals(Type.NODE_DAY)){
							if (typeReport == 0)
								rQuery = SQLQueryString_GetInvoice(entityForTime, ActivityType.Payment, "");
							if (typeReport == 1)
								rQuery = SQLQueryString_GetInvoice(entityForTime, ActivityType.Receipt, "");
							if (typeReport == 2)
								rQuery = SQLQueryString_GetInvoice(entityForTime, null, "ThuChiLai");
							if (typeReport == 3)
								rQuery = SQLQueryString_GetInvoice(entityForTime, null, "ThuChiCongNo");
						}
					}
				}
			}
  			} else if(setView == 2)
  			{
			if(type.equals(Type.ROOT)){
				if (typeReport == 0)
					rQuery = SQLQueryString_GetYear(ActivityType.Payment, "");
				if (typeReport == 1)
					rQuery = SQLQueryString_GetYear(ActivityType.Receipt, "");
				if (typeReport == 2)
					rQuery = SQLQueryString_GetYear(null, "ThuChiLai");
				if (typeReport == 3)
					rQuery = SQLQueryString_GetYear(null, "ThuChiCongNo");
			} else {
				if(type.equals(Type.NODE_YEAR)){
					if (typeReport == 0)
						rQuery = SQLQueryString_GetMonth(entityForTime, ActivityType.Payment, "");
					if (typeReport == 1)
						rQuery = SQLQueryString_GetMonth(entityForTime, ActivityType.Receipt, "");
					if (typeReport == 2)
						rQuery = SQLQueryString_GetMonth(entityForTime, null, "ThuChiLai");
					if (typeReport == 3)
						rQuery = SQLQueryString_GetMonth(entityForTime, null, "ThuChiCongNo");
				} else {
					if(type.equals(Type.NODE_MONTH)){
						if (typeReport == 0)
							rQuery = SQLQueryString_GetDay(entityForTime, ActivityType.Payment, "");
						if (typeReport == 1)
							rQuery = SQLQueryString_GetDay(entityForTime, ActivityType.Receipt, "");
						if (typeReport == 2)
							rQuery = SQLQueryString_GetDay(entityForTime, null, "ThuChiLai");
						if (typeReport == 3)
							rQuery = SQLQueryString_GetDay(entityForTime, null, "ThuChiCongNo");
					} else{
						if(type.equals(Type.NODE_DAY)){
							if (typeReport == 0)
								rQuery = SQLQueryString_GetShift(entityForTime, ActivityType.Payment, "");
							if (typeReport == 1)
								rQuery = SQLQueryString_GetShift(entityForTime, ActivityType.Receipt, "");
							if (typeReport == 2)
								rQuery = SQLQueryString_GetShift(entityForTime, null, "ThuChiLai");
							if (typeReport == 3)
								rQuery = SQLQueryString_GetShift(entityForTime, null, "ThuChiCongNo");
						}
					}
				}
			}
  			}else if( setView == 3)
  			{
			if(type.equals(Type.ROOT)){
				if (typeReport == 0)
					rQuery = SQLQueryString_GetYear(ActivityType.Payment, "");
				if (typeReport == 1)
					rQuery = SQLQueryString_GetYear(ActivityType.Receipt, "");
				if (typeReport == 2)
					rQuery = SQLQueryString_GetYear(null, "ThuChiLai");
				if (typeReport == 3)
					rQuery = SQLQueryString_GetYear(null, "ThuChiCongNo");
			} else {
				if(type.equals(Type.NODE_YEAR)){
					if (typeReport == 0)
						rQuery = SQLQueryString_GetMonth(entityForTime, ActivityType.Payment, "");
					if (typeReport == 1)
						rQuery = SQLQueryString_GetMonth(entityForTime, ActivityType.Receipt, "");
					if (typeReport == 2)
						rQuery = SQLQueryString_GetMonth(entityForTime, null, "ThuChiLai");
					if (typeReport == 3)
						rQuery = SQLQueryString_GetMonth(entityForTime, null, "ThuChiCongNo");
				} else {
					if(type.equals(Type.NODE_MONTH)){
						if (typeReport == 0)
							rQuery = SQLQueryString_GetDay(entityForTime, ActivityType.Payment, "");
						if (typeReport == 1)
							rQuery = SQLQueryString_GetDay(entityForTime, ActivityType.Receipt, "");
						if (typeReport == 2)
							rQuery = SQLQueryString_GetDay(entityForTime, null, "ThuChiLai");
						if (typeReport == 3)
							rQuery = SQLQueryString_GetDay(entityForTime, null, "ThuChiCongNo");
					} else{
						if(type.equals(Type.NODE_DAY)){
							if (typeReport == 0)
								rQuery = SQLQueryString_GetShift(entityForTime, ActivityType.Payment, "");
							if (typeReport == 1)
								rQuery = SQLQueryString_GetShift(entityForTime, ActivityType.Receipt, "");
							if (typeReport == 2)
								rQuery = SQLQueryString_GetShift(entityForTime, null, "ThuChiLai");
							if (typeReport == 3)
								rQuery = SQLQueryString_GetShift(entityForTime, null, "ThuChiCongNo");
						}
						else{
							if(type.equals(Type.NODE_SHIFT)){
								if (typeReport == 0)
									rQuery = SQLQueryString_GetHour(entityForTime, ActivityType.Payment, "");
								if (typeReport == 1)
									rQuery = SQLQueryString_GetHour(entityForTime, ActivityType.Receipt, "");
								if (typeReport == 2)
									rQuery = SQLQueryString_GetHour(entityForTime, null, "ThuChiLai");
								if (typeReport == 3)
									rQuery = SQLQueryString_GetHour(entityForTime, null, "ThuChiCongNo");
							}
							else{
								if(type.equals(Type.NODE_HOUR)){
									if (typeReport == 0)
										rQuery = SQLQueryString_GetInvoice(entityForTime, ActivityType.Payment, "");
									if (typeReport == 1)
										rQuery = SQLQueryString_GetInvoice(entityForTime, ActivityType.Receipt, "");
									if (typeReport == 2)
										rQuery = SQLQueryString_GetInvoice(entityForTime, null, "ThuChiLai");
									if (typeReport == 3)
										rQuery = SQLQueryString_GetInvoice(entityForTime, null, "ThuChiCongNo");
								}
							}
						}
					}
				}
			}
  			}else if( setView == 4)
  			{
  				dateFormat = "%Y-%m-%d-%H";
			if(type.equals(Type.ROOT)){
				if (typeReport == 0)
					rQuery = SQLQueryString_GetYear(ActivityType.Payment, "");
				if (typeReport == 1)
					rQuery = SQLQueryString_GetYear(ActivityType.Receipt, "");
				if (typeReport == 2)
					rQuery = SQLQueryString_GetYear(null, "ThuChiLai");
				if (typeReport == 3)
					rQuery = SQLQueryString_GetYear(null, "ThuChiCongNo");
			} else {
				if(type.equals(Type.NODE_YEAR)){
					if (typeReport == 0)
						rQuery = SQLQueryString_GetMonth(entityForTime, ActivityType.Payment, "");
					if (typeReport == 1)
						rQuery = SQLQueryString_GetMonth(entityForTime, ActivityType.Receipt, "");
					if (typeReport == 2)
						rQuery = SQLQueryString_GetMonth(entityForTime, null, "ThuChiLai");
					if (typeReport == 3)
						rQuery = SQLQueryString_GetMonth(entityForTime, null, "ThuChiCongNo");
				} else {
					if(type.equals(Type.NODE_MONTH)){
						if (typeReport == 0)
							rQuery = SQLQueryString_GetDay(entityForTime, ActivityType.Payment, "");
						if (typeReport == 1)
							rQuery = SQLQueryString_GetDay(entityForTime, ActivityType.Receipt, "");
						if (typeReport == 2)
							rQuery = SQLQueryString_GetDay(entityForTime, null, "ThuChiLai");
						if (typeReport == 3)
							rQuery = SQLQueryString_GetDay(entityForTime, null, "ThuChiCongNo");
					}
					else{
						if(type.equals(Type.NODE_DAY)){
							if (typeReport == 0)
								rQuery = SQLQueryString_GetHour(entityForTime, ActivityType.Payment, "");
							if (typeReport == 1)
								rQuery = SQLQueryString_GetHour(entityForTime, ActivityType.Receipt, "");
							if (typeReport == 2)
								rQuery = SQLQueryString_GetHour(entityForTime, null, "ThuChiLai");
							if (typeReport == 3)
								rQuery = SQLQueryString_GetHour(entityForTime, null, "ThuChiCongNo");
						}
						else{
							if(type.equals(Type.NODE_HOUR)){
								if (typeReport == 0)
									rQuery = SQLQueryString_GetInvoice(entityForTime, ActivityType.Payment, "");
								if (typeReport == 1)
									rQuery = SQLQueryString_GetInvoice(entityForTime, ActivityType.Receipt, "");
								if (typeReport == 2)
									rQuery = SQLQueryString_GetInvoice(entityForTime, null, "ThuChiLai");
								if (typeReport == 3)
									rQuery = SQLQueryString_GetInvoice(entityForTime, null, "ThuChiCongNo");
							}
						}
					}
				}
			}
		}

		System.out.println(rQuery.createSQLQuery());
		ReportTable[] reportTable = accountingServiceClient.reportQuery(new SQLSelectQuery[] { rQuery });
		String[] field;
		if(typeReport == 3){
			reportTable[0].dump(new String[] { "node", "final", "total", "final1", "total1" });
			field = new String[] { "node", "final", "total", "final1", "total1" };
		} else {
			if((conds.containsKey("product1") && typeReport == 2) || !conds.containsKey("product1")){
				reportTable[0].dump(new String[] { "node", "final", "total" });
				field = new String[] { "node", "final", "total" };
			} else {
				reportTable[0].dump(new String[] { "node", "final" });
				field = new String[] { "node", "final" };
			}
		}
		List<Map<String, Object>> records = reportTable[0].getRecords();
		for (int i = 0; i < records.size(); i++) {
			Map<String, Object> record = records.get(i);
			Object[] values = new Object[field.length];
			for (int j = 0; j < field.length; j++) {
		      	if(field[j].toString().equals("node")){
		      		String str = record.get(field[j]).toString();
		      		if(str.indexOf(":")>0){
		      			values[j] = str.substring(str.indexOf(":")+1);
		      		}else {
		      			values[j] = record.get(field[j]);
							}
		      	}else {
		      		values[j] = record.get(field[j]);
						}
			}

			double sumFinalCharge; 
			double sumTotalTransaction = 0; 
			double sumTotalTransactionReceipt = 0; 
			double sumTotalTransactionPayment = 0;
			double sumPhaiThu = 0; //Phải thu = Tổng thu - Thực thu
			double sumPhaiChi = 0; //Phải chi = Tổng chi - Thực chi
			double ratio = 0; //Lãi lỗ (% hoặc -+)
			if (viewMoney.equals("Nghìn")) {
				sumFinalCharge = Double.parseDouble(values[1].toString()) / 1000; //Tổng thu
				if(values.length > 2)
					sumTotalTransaction = Double.parseDouble(values[2].toString()) / 1000; //Với kiểu [Thu chi công nợ] là : Tổng chi | Với kiểu [Doanh thu-Chi phí-Thu chi lãi] là: Thực chi
				if(typeReport == 3){
					sumTotalTransactionReceipt = Double.parseDouble(values[3].toString()) / 1000; //Thực thu 
					sumTotalTransactionPayment = Double.parseDouble(values[4].toString()) / 1000; //Thực chi
					sumPhaiThu = sumFinalCharge - sumTotalTransactionReceipt; //Phải thu
					sumPhaiChi = sumTotalTransaction - sumTotalTransactionPayment; //Phải chi
				}
			} else if (viewMoney.equals("Triệu")) {
				sumFinalCharge = Double.parseDouble(values[1].toString()) / 1000000;
				if(values.length > 2)
					sumTotalTransaction = Double.parseDouble(values[2].toString()) / 1000000;
				if(typeReport == 3){
					sumTotalTransactionReceipt = Double.parseDouble(values[3].toString()) / 1000000;
					sumTotalTransactionPayment = Double.parseDouble(values[4].toString()) / 1000000;
					sumPhaiThu = sumFinalCharge - sumTotalTransactionReceipt;
					sumPhaiChi = sumTotalTransaction - sumTotalTransactionPayment;
				}
			} else {
				sumFinalCharge = Double.parseDouble(values[1].toString());
				if(values.length > 2)
					sumTotalTransaction = Double.parseDouble(values[2].toString());
				if(typeReport == 3){
					sumTotalTransactionReceipt = Double.parseDouble(values[3].toString());
					sumTotalTransactionPayment = Double.parseDouble(values[4].toString());
					sumPhaiThu = sumFinalCharge - sumTotalTransactionReceipt;
					sumPhaiChi = sumTotalTransaction - sumTotalTransactionPayment;
				}
			}
			ReportForTimeEntity row;
			if(typeReport == 3){ //Nếu là kiểu thu chi công nợ
				ratio = sumTotalTransactionReceipt - sumTotalTransactionPayment;
				row = new ReportForTimeEntity(values[0].toString(), new MyDouble(sumFinalCharge).toString(), new MyDouble(sumTotalTransaction).toString(), new MyDouble(sumTotalTransactionReceipt).toString(), new MyDouble(sumTotalTransactionPayment).toString(), new MyDouble(sumPhaiThu).toString(), new MyDouble(sumPhaiChi).toString(), new MyDouble(ratio).toString(), index);
				row.setParent(values[0].toString());
				if(setView == 2 || setView == 3)
				{
				if(type.equals(Type.ROOT)){
					row.setType(Type.NODE_YEAR);
				} else {
					if(type.equals(Type.NODE_YEAR)){
						row.setType(Type.NODE_MONTH);
					} else {
						if(type.equals(Type.NODE_MONTH)){
							row.setType(Type.NODE_DAY);
						}
						else{
							if(type.equals(Type.NODE_DAY)){
								row.setType(Type.NODE_SHIFT);
							}
							else{
								if(type.equals(Type.NODE_SHIFT)){
									row.setType(Type.NODE_HOUR);
								}
							}
						}
					}
				}
				}
				else
				{
					if(type.equals(Type.ROOT)){
						row.setType(Type.NODE_YEAR);
					} else {
						if(type.equals(Type.NODE_YEAR)){
							row.setType(Type.NODE_MONTH);
						} else {
							if(type.equals(Type.NODE_MONTH)){
								row.setType(Type.NODE_DAY);
							}
							else{
								if(type.equals(Type.NODE_DAY)){
									row.setType(Type.NODE_HOUR);
								}
							}
						}
					}
				}
				
				if (sumFinalCharge != 0 || sumTotalTransaction != 0 || sumTotalTransactionReceipt != 0 || sumTotalTransactionPayment != 0) {
					if (values[0].toString().equals("Phòng khác") || values[0].toString().equals("Nhóm khác")) {
							nodes.add(nodes.size(), row);
					} else {
						nodes.add(row);
					}
				}
			} else { //Nếu là kiểu doanh thu, chi phí, thu chi lãi
				ratio = (sumTotalTransaction / sumFinalCharge) * 100;
				if (typeReport == 2)
					ratio = sumFinalCharge - sumTotalTransaction;
				row = new ReportForTimeEntity(values[0].toString(), new MyDouble(sumFinalCharge).toString(), new MyDouble(sumTotalTransaction).toString(), new MyDouble(ratio).toString(), index);
				if(setView == 2 || setView == 3)
				{
				if(type.equals(Type.ROOT)){
					row.setType(Type.NODE_YEAR);
				} else {
					if(type.equals(Type.NODE_YEAR)){
						row.setType(Type.NODE_MONTH);
					} else {
						if(type.equals(Type.NODE_MONTH)){
							row.setType(Type.NODE_DAY);
						}
						else{
							if(type.equals(Type.NODE_DAY)){
								row.setType(Type.NODE_SHIFT);
							}
						}
					}
				}
				}
				else
				{
					if(type.equals(Type.ROOT)){
						row.setType(Type.NODE_YEAR);
					} else {
						if(type.equals(Type.NODE_YEAR)){
							row.setType(Type.NODE_MONTH);
						} else {
							if(type.equals(Type.NODE_MONTH)){
								row.setType(Type.NODE_DAY);
							}
						}
					}
				}
				if(setView ==2 || setView ==3){
					if(type.equals(Type.NODE_SHIFT))
					{
						String str = values[0].toString().substring(values[0].toString().length() -2);
						row = new ReportForTimeEntity(str+ "h", new MyDouble(sumFinalCharge).toString(), new MyDouble(sumTotalTransaction).toString(), new MyDouble(ratio).toString(), index);
						row.setType(Type.NODE_HOUR);

					}
					
				}
				else if(setView == 4){
					if(type.equals(Type.NODE_DAY)){
						String str = values[0].toString().substring(values[0].toString().length() -2);
						row = new ReportForTimeEntity(str+ "h", new MyDouble(sumFinalCharge).toString(), new MyDouble(sumTotalTransaction).toString(), new MyDouble(ratio).toString(), index);
						row.setType(Type.NODE_HOUR);
						row.setParent(values[0].toString());
					}
				}
				row.setParent(values[0].toString());

				if (sumFinalCharge != 0 || sumTotalTransaction != 0) {
					if (values[0].toString().equals("Phòng khác") || values[0].toString().equals("Nhóm khác")) {
						nodes.add(nodes.size(), row);
					} else {
						nodes.add(row);
					}
				}
			}				
		}
		return nodes;
	}
  
  @Override
  public Object getCellEditorValue() {
    return cell.getObject();
  }

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		cell.setRow(row);
		if(column == 0){
			JLabel labelIcon = new JLabel(" ");
			cell.setIcon(labelIcon);
			String result = " ";
			((ReportForTimeEntity) value).setLabelIcon(labelIcon);
			int index = ((ReportForTimeEntity) value).getIndex();
			try {
	      cell.setObject(value);
				if(((ReportForTimeEntity) value).isWiden())
					labelIcon.setIcon(nodeImageMulti);
				else
					labelIcon.setIcon(nodeIconAdd);
				
				if(index == 2 && setView == 0)
					labelIcon.setIcon(nodeIconChild);
				if(index == 3 && setView == 1)
					labelIcon.setIcon(nodeIconChild);
				
				for(int space = 0; space < index; space++){
					result = result + "      ";
				}
				labelIcon.setText(result);
				cell.getText().setText(value.toString());
	    } catch (Exception e) {
				e.printStackTrace();
				cell.getText().setText("");
	    }
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
		if (column == 0) {
			JLabel labelIcon = ((ReportForTimeEntity) value).getLabelIcon();
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
							List<ReportForTimeEntity> list = ((ReportForTimeEntity) c).getListChild();
							DefaultPieDataset dataset = new DefaultPieDataset();
							if (typeReport == 0 || typeReport == 1){
								String type = model.getReportStatistics().getColumnViewPie().toString();
								if(type.equals("Tổng thu") || type.equals("Tổng chi")){
									for(ReportForTimeEntity r : list){
										Double d = (new MyDouble(r.getColumn1()).doubleValue()/new MyDouble(((ReportForTimeEntity) c).getColumn1()).doubleValue() + new MyDouble(r.getColumn1()).doubleValue()%new MyDouble(((ReportForTimeEntity) c).getColumn1()).doubleValue())*100;
										dataset.setValue(r.getNode(), d);
									}
								} else {
									if(type.equals("Thực thu") || type.equals("Thực chi")){
										for(ReportForTimeEntity r : list){
											Double d = (new MyDouble(r.getColumn2()).doubleValue()/new MyDouble(((ReportForTimeEntity) c).getColumn2()).doubleValue() + new MyDouble(r.getColumn2()).doubleValue()%new MyDouble(((ReportForTimeEntity) c).getColumn2()).doubleValue())*100;
											dataset.setValue(r.getNode(), d);
										}
									} else {
										if(type.equals("Tỉ lệ(%)")){
											for(ReportForTimeEntity r : list){
												Double d = (new MyDouble(r.getColumn3()).doubleValue()/new MyDouble(((ReportForTimeEntity) c).getColumn3()).doubleValue() + new MyDouble(r.getColumn3()).doubleValue()%new MyDouble(((ReportForTimeEntity) c).getColumn3()).doubleValue())*100;
												dataset.setValue(r.getNode(), d);
											}
										}
									}
								}
							}
							if (typeReport == 2){
								String type = model.getReportStatistics().getColumnViewPie().toString();
								if(type.equals("Thực thu")){
									for(ReportForTimeEntity r : list){
										Double d = (new MyDouble(r.getColumn1()).doubleValue()/new MyDouble(((ReportForTimeEntity) c).getColumn1()).doubleValue() + new MyDouble(r.getColumn1()).doubleValue()%new MyDouble(((ReportForTimeEntity) c).getColumn1()).doubleValue())*100;
										dataset.setValue(r.getNode(), d);
									}
								} else {
									if(type.equals("Thực chi")){
										for(ReportForTimeEntity r : list){
											Double d = (new MyDouble(r.getColumn2()).doubleValue()/new MyDouble(((ReportForTimeEntity) c).getColumn2()).doubleValue() + new MyDouble(r.getColumn2()).doubleValue()%new MyDouble(((ReportForTimeEntity) c).getColumn2()).doubleValue())*100;
											dataset.setValue(r.getNode(), d);
										}
									} else {
										if(type.equals("Lãi(lỗ)")){
											for(ReportForTimeEntity r : list){
												Double d = (new MyDouble(r.getColumn3()).doubleValue()/new MyDouble(((ReportForTimeEntity) c).getColumn3()).doubleValue() + new MyDouble(r.getColumn3()).doubleValue()%new MyDouble(((ReportForTimeEntity) c).getColumn3()).doubleValue())*100;
												dataset.setValue(r.getNode(), d);
											}
										}
									}
								}
							}
							if (typeReport == 3){
								String type = model.getReportStatistics().getColumnViewPie().toString();
								if(type.equals("Tổng thu")){
									for(ReportForTimeEntity r : list){
										Double d = (new MyDouble(r.getColumn1()).doubleValue()/new MyDouble(((ReportForTimeEntity) c).getColumn1()).doubleValue() + new MyDouble(r.getColumn1()).doubleValue()%new MyDouble(((ReportForTimeEntity) c).getColumn1()).doubleValue())*100;
										dataset.setValue(r.getNode(), d);
									}
								} else {
									if(type.equals("Tổng chi")){
										for(ReportForTimeEntity r : list){
											Double d = (new MyDouble(r.getColumn2()).doubleValue()/new MyDouble(((ReportForTimeEntity) c).getColumn2()).doubleValue() + new MyDouble(r.getColumn2()).doubleValue()%new MyDouble(((ReportForTimeEntity) c).getColumn2()).doubleValue())*100;
											dataset.setValue(r.getNode(), d);
										}
									} else {
										if(type.equals("Thực thu")){
											for(ReportForTimeEntity r : list){
												Double d = (new MyDouble(r.getColumn3()).doubleValue()/new MyDouble(((ReportForTimeEntity) c).getColumn3()).doubleValue() + new MyDouble(r.getColumn3()).doubleValue()%new MyDouble(((ReportForTimeEntity) c).getColumn3()).doubleValue())*100;
												dataset.setValue(r.getNode(), d);
											}
										} else {
											if(type.equals("Thực chi")){
												for(ReportForTimeEntity r : list){
													Double d = (new MyDouble(r.getColumn4()).doubleValue()/new MyDouble(((ReportForTimeEntity) c).getColumn4()).doubleValue() + new MyDouble(r.getColumn4()).doubleValue()%new MyDouble(((ReportForTimeEntity) c).getColumn4()).doubleValue())*100;
													dataset.setValue(r.getNode(), d);
												}
											} else {
												if(type.equals("Phải thu")){
													for(ReportForTimeEntity r : list){
														Double d = (new MyDouble(r.getColumn5()).doubleValue()/new MyDouble(((ReportForTimeEntity) c).getColumn5()).doubleValue() + new MyDouble(r.getColumn5()).doubleValue()%new MyDouble(((ReportForTimeEntity) c).getColumn5()).doubleValue())*100;
														dataset.setValue(r.getNode(), d);
													}
												} else {
													if(type.equals("Phải chi")){
														for(ReportForTimeEntity r : list){
															Double d = (new MyDouble(r.getColumn6()).doubleValue()/new MyDouble(((ReportForTimeEntity) c).getColumn6()).doubleValue() + new MyDouble(r.getColumn6()).doubleValue()%new MyDouble(((ReportForTimeEntity) c).getColumn6()).doubleValue())*100;
															dataset.setValue(r.getNode(), d);
														}
													} else {
														if(type.equals("Lãi(lỗ)")){
															for(ReportForTimeEntity r : list){
																Double d = (new MyDouble(r.getColumn7()).doubleValue()/new MyDouble(((ReportForTimeEntity) c).getColumn7()).doubleValue() + new MyDouble(r.getColumn7()).doubleValue()%new MyDouble(((ReportForTimeEntity) c).getColumn7()).doubleValue())*100;
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
				 
		    } catch (Exception e) {
					e.printStackTrace();
					cell.getText().setText("");
		    }
				cell.setIcon(labelIcon);
		} else {
			cell.getText().setHorizontalAlignment(JLabel.RIGHT);
		}
		cell.getText().setText(value.toString());
		cell.getText().setFont(new Font(cell.getFont().getFontName(), Font.BOLD, 13));
    return cell;
  }
}
