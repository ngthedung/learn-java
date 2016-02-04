package com.hkt.client.swingexp.app.banhang.list;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.AccountingServiceClient;
import com.hkt.client.swingexp.app.core.ITableMainExt;
import com.hkt.client.swingexp.app.core.TableObservable;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.module.core.entity.ReportTable;
import com.hkt.module.core.entity.SQLSelectQuery;

@SuppressWarnings("serial")
public class TableListGrossProduct extends TableObservable implements ITableMainExt {
	private List<GrossProduct> listGrossProducts;
	private TableModelGrossProduct tableModelGrossProduct;
	private HashMap<String, String> conds;
	private Date start, end;
	private SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	
	private static ClientContext           clientContext           = ClientContext.getInstance();
	private static AccountingServiceClient accountingServiceClient = clientContext.getBean(AccountingServiceClient.class);

	public TableListGrossProduct(HashMap<String, String> conds){
		this.conds = conds;
		listGrossProducts = new ArrayList<GrossProduct>();
		try {
			listGrossProducts = getListData();
		} catch (Exception e) {
			listGrossProducts = new ArrayList<GrossProduct>();
		}
		tableModelGrossProduct = new TableModelGrossProduct(listGrossProducts);
		setModel(tableModelGrossProduct);
		try {
			start = dateFormat.parse(conds.get("firtDate"));
			end = dateFormat.parse(conds.get("endDate"));
		} catch (Exception e) {
			start = new Date();
			end = new Date();
		}
		
	}
	
	private List<GrossProduct> getListData() throws Exception{
//		INNER JOIN customer c on c.code = i.customerCode INNER JOIN employee e on e.code = i.employeeCode
		SQLSelectQuery rQuery = new SQLSelectQuery() ;
		rQuery.table("InvoiceDetail i "
    		+ "INNER JOIN InvoiceItem it ON it.invoiceId = i.id "
    		+ "LEFT JOIN Product p ON p.code = it.productCode "
        + "LEFT JOIN customer c ON c.code = i.customerCode "
        + "LEFT JOIN employee e ON e.code = i.employeeCode").
	    field("it.itemName AS `nameProduct`", "business").
	    field("SUM(it.quantity) AS `quantity`", "quantity").
	    field("p.unit", "unitProduct").
	    field("SUM(it.finalCharge) AS `finalCharge`", "finalCharge").
	    field("it.currencyUnit", "unitMoney").
	    field("it.status AS `statusItem`", "status").
	    field("i.startDate", "startDate").
	    field("i.endDate", "endDate").
	    cond("it.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'").
	    cond("it.currencyUnit = '" + conds.get("moneyUnit") + "'").
	    cond("i.activityType = '" + conds.get("type") + "'").
	    groupBy("nameProduct", "statusItem")
	    .orderBy("it.id");
		rQuery.cond("it.productCode IS NOT NULL ");
		if (!conds.get("firtDate").equals(""))
			rQuery.cond("i.startDate > '" + conds.get("firtDate") + "'");
		if (!conds.get("endDate").equals(""))
			rQuery.cond("i.startDate <= '" + conds.get("endDate") + "'");
		if (!conds.get("status").equals("Tất cả"))
			rQuery.cond("it.status = '" + conds.get("status") + "'");
		if (!conds.get("employee").equals(""))			
			rQuery.cond(" (SELECT ii.employeeCode FROM InvoiceDetail ii INNER JOIN employee e on e.code = ii.employeeCode WHERE e.loginId = '" + conds.get("employee") + "' and ii.id = i.id) ");
		if (!conds.get("customer").equals("")) 
			rQuery.cond(" c.loginId = '" + conds.get("customer") + "'  ");
		
		System.out.println(rQuery.createSQLQuery());
		ReportTable[] reportTable = accountingServiceClient.reportQuery(new SQLSelectQuery[] { rQuery });
		reportTable[0].dump(new String[] { "business", "quantity", "unitProduct", "finalCharge", "unitMoney", "status", "startDate", "endDate" });
		String[] field = new String[] { "business", "quantity", "unitProduct", "finalCharge", "unitMoney", "status", "startDate", "endDate" };
		List<Map<String, Object>> records = reportTable[0].getRecords();
		List<GrossProduct> grossProducts = new ArrayList<GrossProduct>();
		for (int i = 0; i < records.size(); i++) {
			Map<String, Object> record = records.get(i);
			Object[] values = new Object[field.length];
			for (int j = 0; j < field.length; j++) {
				values[j] = record.get(field[j]);
				if (values[j] == null)
					values[j] = "";
			}
			GrossProduct grossProduct = new GrossProduct(values[0].toString(), values[1].toString(), values[2].toString(), values[3].toString(), values[4].toString(), values[5].toString(), values[6].toString(), values[7].toString());
			grossProducts.add(grossProduct);
		}
		return grossProducts;
	}
	
	@Override
	public void click() {
	}

	@Override
	public int getListSize() {
		return listGrossProducts.size();
	}

	@Override
	public List<Integer> getColumnSum() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(2);
		list.add(4);
		return list;
	}

	@Override
	public DefaultTableModel loadTable(int index, int pageSize) {
		try {
			tableModelGrossProduct.setData(listGrossProducts.subList(index, pageSize));
		} catch (Exception e) {
			tableModelGrossProduct = new TableModelGrossProduct(new ArrayList<GrossProduct>());
		}
		return tableModelGrossProduct;
	}

  @Override
  public boolean delete() {
    return false;
  }
  
  @Override
  public boolean isDelete() {
    return false;
  }
  
  private void loadData() throws Exception{
	  SQLSelectQuery rQuery = new SQLSelectQuery() ;
		rQuery.table("InvoiceDetail i "
  		+ "INNER JOIN InvoiceItem it ON it.invoiceId = i.id "
  		+ "LEFT JOIN Product p ON p.code = it.productCode "
      + "LEFT JOIN customer c ON c.code = i.customerCode "
      + "LEFT JOIN employee e ON e.code = i.employeeCode").
	    field("it.itemName AS `nameProduct`", "business").
	    field("SUM(it.quantity) AS `quantity`", "quantity").
	    field("p.unit", "unitProduct").
	    field("SUM(it.finalCharge) AS `finalCharge`", "finalCharge").
	    field("it.currencyUnit", "unitMoney").
	    field("it.status AS `statusItem`", "status").
	    field("i.startDate", "startDate").
	    field("i.endDate", "endDate").
	    cond("it.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"'").
	    cond("it.currencyUnit = '" + conds.get("moneyUnit") + "'").
	    cond("i.activityType = '" + conds.get("type") + "'").
	    groupBy("nameProduct", "statusItem")
	    .orderBy("it.id");
	    
		if (!conds.get("firtDate").equals(""))
			rQuery.cond("i.startDate > '" + conds.get("firtDate") + "'");
		if (!conds.get("endDate").equals(""))
			rQuery.cond("i.startDate <= '" + conds.get("endDate") + "'");
		if (!conds.get("status").equals("Tất cả"))
			rQuery.cond("it.status = '" + conds.get("status") + "'");
		if (!conds.get("employee").equals(""))			
			rQuery.cond(" (SELECT ii.employeeCode FROM InvoiceDetail ii INNER JOIN employee e on e.code = ii.employeeCode WHERE e.loginId = '" + conds.get("employee") + "' and ii.id = i.id) ");
		if (!conds.get("customer").equals("")) 
			rQuery.cond(" c.loginId = '" + conds.get("customer") + "'  ");

		System.out.println(rQuery.createSQLQuery());
		ReportTable[] reportTable = accountingServiceClient.reportQuery(new SQLSelectQuery[] { rQuery });
		reportTable[0].dump(new String[] { "business", "quantity", "unitProduct", "finalCharge", "unitMoney", "status", "startDate", "endDate" });
		String[] field = new String[] { "business", "quantity", "unitProduct", "finalCharge", "unitMoney", "status", "startDate", "endDate" };
		List<Map<String, Object>> records = reportTable[0].getRecords();
		List<GrossProduct> grossProducts = new ArrayList<GrossProduct>();
		for (int i = 0; i < records.size(); i++) {
			Map<String, Object> record = records.get(i);
			Object[] values = new Object[field.length];
			for (int j = 0; j < field.length; j++) {
				values[j] = record.get(field[j]);
				if (values[j] == null)
					values[j] = "";
			}
			GrossProduct grossProduct = new GrossProduct(values[0].toString(), values[1].toString(), values[2].toString(), values[3].toString(), values[4].toString(), values[5].toString(), values[6].toString(), values[7].toString());
			grossProducts.add(grossProduct);
		}
  }

  @Override
	public void loadDataByTime(Date dateStart, Date dateEnd) {
		this.start = dateStart;
		this.end = dateEnd;
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
		conds.put("firtDate", dateFormat.format(calendarStart.getTime()));
		conds.put("endDate", dateFormat.format(calendarEnd.getTime()));
		try {
			listGrossProducts = getListData();
			change();
		} catch (Exception e) {
		}
		
	}

	@Override
	public Date getDateStart() {
		return start;
	}

	@Override
	public Date getDateEnd() {
		return end;
	}

}