package com.hkt.client.rest.service;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.hkt.client.rest.ClientContext;
import com.hkt.module.accounting.entity.Bank;
import com.hkt.module.accounting.entity.BankTransaction;
import com.hkt.module.core.entity.ReportTable;
import com.hkt.module.core.entity.SQLSelectQuery;
import com.hkt.module.core.entity.SQLSelectQuery.Field;
import com.hkt.util.text.TabularPrinter;

public class AccountingServiceClientIntegrationTest extends JdbcDaoSupport{
  ClientContext clientContext = ClientContext.getInstance() ;
  @Test
  public void testA(){
  	try {
  		 WarehouseServiceClient client = clientContext.getBean(WarehouseServiceClient.class);
    	 System.out.println("long  :"+client.getPriceReport("shs"));
    } catch (Exception e) {
	   e.printStackTrace();
    }
  	
  }
  
  @Test
  public void testReport1() throws Exception {
  	 AccountingServiceClient client = clientContext.getBean(AccountingServiceClient.class);
  	
	  String  dateStart, dateEnd;
	  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendarStart = Calendar.getInstance();
		Calendar calendarEnd = Calendar.getInstance();
		//if (dateStart != null) {
			calendarStart.add(Calendar.DAY_OF_MONTH, -30);
			calendarStart.set(Calendar.HOUR_OF_DAY, 00);
			calendarStart.set(Calendar.MINUTE, 00);
			calendarStart.set(Calendar.SECOND, 00);
		//}
		//if (dateEnd != null) {
			calendarEnd.setTime(new Date());
			calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
			calendarEnd.set(Calendar.MINUTE, 59);
			calendarEnd.set(Calendar.SECOND, 59);
		//}
		
		dateStart = dateFormat.format(calendarStart.getTime());
		dateEnd = dateFormat.format(calendarEnd.getTime());
   
    SQLSelectQuery rQuery = new SQLSelectQuery();
//    rQuery.table("InvoiceTransaction i")
//                    .field("(select concat(`invoiceCode`,'--',`invoiceName`,'--',`finalCharge`,'--',`totalPaid`) from `InvoiceDetail` where `id`= i.invoiceId) AS `invoice`", "invoice")
//                    .field("i.transactionDate AS `date`", "date")
//					.field("(select `name` from `employee` where `code`= i.employeeCode) AS `employee`", "employee")
//					.field("i.projectCode AS `projectCode`", "projectCode")
//					.field("(select `name` from `restaurant_project` where `code`= SUBSTRING_INDEX(i.projectCode,'/', -1)) AS `project`", "project")
//					.field("(select `label` from `accountGroup` where `name`= SUBSTRING_INDEX(i.departmentCode,'/', -1)) AS `department`", "department")
//					.field("i.total AS `total`", "total");
    rQuery
    .field(
        "(select SUM(`price`) + '--' + count(`id`) from `IdentityProduct` where `importDate` <= '"
            + dateFormat.format(calendarStart.getTime()) + "'"
            + " AND `importType` = 'Import'"
            + " AND `exportType` = 'NotExport') AS `SLDauKy`", "value0")
    .field(
        "(select concat(SUM(`price`),'--',count(`id`)) from `IdentityProduct` where `importDate` >= '"
            + dateFormat.format(calendarStart.getTime()) + "' AND `importDate` <= '"
            + dateFormat.format(calendarEnd.getTime()) + "'"
            + " AND `importType` = 'Import'"
            + ")  AS `SLTrongKyTang`", "concat-value1")
    .field(
        "(select concat(SUM(`price`) , '--' , count(`id`)) from `IdentityProduct` where `importDate` >= '"
            + dateFormat.format(calendarStart.getTime()) + "' AND `importDate` <= '"
            + dateFormat.format(calendarEnd.getTime()) + "'"
            + " AND `exportType` = 'Export') AS `SLTrongKyGiam`", "concat-value2")
    .field(
        "(select SUM(`price`) + '--' + count(`id`) from `IdentityProduct` where `importDate` >= '"
            + dateFormat.format(calendarEnd.getTime()) + "' AND `importDate` <= '"
            + dateFormat.format(calendarEnd.getTime()) + "'"
            + " AND `importType` = 'Import'"
            + " AND `exportType` = 'NotExport') AS `SLConLai`", "value3");
                    //.cond("i.transactionDate >= '" + dateStart + "' AND i.transactionDate <= '" + dateEnd + "' ");
    System.out.println(rQuery.createSQLQuery());
//    JdbcTemplate tmpl = getJdbcTemplate();
//    String sqlQuery = rQuery.createSQLQuery();
//    try {
//    	MapMapper mapper = new MapMapper(rQuery);
//    	System.out.println(tmpl);
//      List<Map<String, Object>> records = tmpl.query(sqlQuery,mapper);
//     
//    } catch (Exception e) {
//	    e.printStackTrace();
//    }
  
    ReportTable[] reportTable = client.reportQuery(new SQLSelectQuery[] { rQuery }) ;
    reportTable[0].dump(new String[] {"value0","concat-value1","concat-value2","value3"}) ;

  }
  
  static public class MapMapper implements
	ParameterizedRowMapper<Map<String, Object>> {
private SQLSelectQuery query;

public MapMapper(SQLSelectQuery query) {
	this.query = query;
}

public Map<String, Object> mapRow(ResultSet rs, int rowNum)
		throws SQLException {
	try {
		List<Field> fields = query.getFields();
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < fields.size(); i++) {
			String name = fields.get(i).getAlias();
			Object val = rs.getObject(i + 1);
			System.out.println(name+"    "+val);
			map.put(name, val);
		}
		return map;
	} catch (Throwable t) {
		throw new SQLException(t);
	}
}
}
  
  public void dump(String[] field,List<Map<String, Object>> records) {
    int[] width = new int[field.length] ;
    for(int i = 0; i < width.length; i++) width[i] = 15 ;
    TabularPrinter p = new TabularPrinter(System.out, width) ;
    p.printHeader(field) ;
    for(int i = 0; i < records.size(); i++) {
      Map<String, Object> record = records.get(i) ;
      Object[] values = new Object[field.length] ;
      for(int j = 0; j < field.length; j++) {
        values[j] = record.get(field[j]) ;
      }
      p.printRow(values) ;
    }
  }
  
  @Test
  public void testReport() throws Exception {
    AccountingServiceClient client = clientContext.getBean(AccountingServiceClient.class);
    SQLSelectQuery rQuery = new SQLSelectQuery();
    rQuery.table("InvoiceDetail i")
					.field("(select `name` from `employee` where `code`= i.employeeCode) AS `employee`", "employee")
					.field("concat(SUM(i.total),'--',i.invoiceName) AS `customer`", "customer")
					.field("(select concat(`name`,'--',`mobile`) from `supplier` where `code`= i.supplierCode) AS `customer`", "supplier")
					.field("(select `name` from `restaurant_project` where `code`= SUBSTRING_INDEX(i.projectCode,'/', -1)) AS `project`", "project")
					.field("(select `label` from `accountGroup` where `name`= SUBSTRING_INDEX(i.departmentCode,'/', -1) group by `name`) AS `department`", "department");
    System.out.println(rQuery.createSQLQuery());
    ReportTable[] reportTable = client.reportQuery(new SQLSelectQuery[] { rQuery }) ;
    reportTable[0].dump(new String[] {"employee","customer","supplier","project","department"}) ;
    
    
//    JDialog dialog = new JDialog();
//    dialog.setModal(true);
//    dialog.setSize(500, 500);
//    dialog.setLayout(new GridLayout());
//    JTable table = new JTable();
//    String[] field = new String[] {"employee","customer","supplier","project","department"};
//    DefaultTableModel model = new DefaultTableModel(field, 0);
//    List<Map<String, Object>> records = reportTable[0].getRecords();
//    for(int i = 0; i < records.size(); i++) {
//      Map<String, Object> record = records.get(i) ;
//      Object[] values = new Object[field.length] ;
//      for(int j = 0; j < field.length; j++) {
//        values[j] = record.get(field[j]) ; 
//      }
//      
//      model.addRow(values);
//    }
//    table.setModel(model);
//    System.out.println(table.getRowCount());
//    dialog.add(table);
//    dialog.setVisible(true);
  }
  
//  @Test
//  public void testBank() throws Exception {
//	  assertEquals(2, client.getAllBank().size());
//
//	  Bank bank = new Bank();
//	  bank.setName("TestBank");
//	  bank = client.saveBank(bank);
//	  
//	  assertEquals(2, client.getAllBank().size());
//	  
//	  client.deleteBank(bank);
//	  assertEquals(1, client.getAllBank().size());
//  }
//  
//	@Test
//	public void testBankTransaction() throws Exception{
//		BankTransaction bankTransaction = new BankTransaction();
//		bankTransaction.setName("BankTransaction");
//		bankTransaction.setBankCode("BankCode");
//		bankTransaction.setTotal(50000);
//		bankTransaction.setTransactionCode("TransactionCode");
//		bankTransaction.setType(false);
//		
//		bankTransaction = client.saveBankTransaction(bankTransaction);
//		
//		assertEquals(1, client.getAllBankTransaction().size());
//		assertEquals(1, client.getByBankCode("ank").size());
//		assertEquals(1, client.getTracsactionCode("nsa").size());
//		assertEquals(1, client.getByBankType(false).size());
//		assertEquals(1, client.getByBankRangeTotal(30000).size());
//	}
}
