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

public class TableListSplitProduct extends TableObservable implements ITableMainExt {
	private List<SplitProduct> listSplitProduct;
	private TableModelSplitProduct tableModelSplitProduct;
	private HashMap<String, String> tranfer;
	private Date start, end;
	private SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private static ClientContext clientContext = ClientContext.getInstance();
	private static AccountingServiceClient accountingServiceClient = clientContext
			.getBean(AccountingServiceClient.class);

	public TableListSplitProduct(HashMap<String, String> _tranfer) {
		this.tranfer = _tranfer;
		try {
			listSplitProduct = getListData();
		} catch (Exception e) {
			e.printStackTrace();
			listSplitProduct = new ArrayList<SplitProduct>();
		}
		tableModelSplitProduct = new TableModelSplitProduct(listSplitProduct);
		setModel(tableModelSplitProduct);
		getColumnModel().getColumn(0).setMaxWidth(50);
		getColumnModel().getColumn(2).setMaxWidth(80);
		getColumnModel().getColumn(3).setMaxWidth(80);
		getColumnModel().getColumn(5).setMaxWidth(80);
		getColumnModel().getColumn(8).setMaxWidth(100);
		getColumnModel().getColumn(8).setMinWidth(100);
		getColumnModel().getColumn(7).setMaxWidth(100);
		getColumnModel().getColumn(7).setMinWidth(100);
		try {
			start = dateFormat.parse(_tranfer.get("firtDate"));
			end = dateFormat.parse(_tranfer.get("endDate"));
		} catch (Exception e) {
			start = new Date();
			end = new Date();
		}
		
	}

	private List<SplitProduct> getListData() throws Exception {
		SQLSelectQuery rQuery = new SQLSelectQuery();
		rQuery.table("InvoiceItem it ", "InvoiceDetail i")
				.field("it.itemName AS `invoiceItemName`")
				.field("i.invoiceCode AS `invoiceCode`", "invoiceCode")
				.field("(SELECT name FROM Product WHERE code = it.productCode) AS `productName`",
						"product")
				.field("it.productCode  AS `itemCode`", "itemCode")
				.field("it.quantity", "quantity")
				.field("(SELECT unit FROM Product WHERE code = it.productCode) AS `unitProduct`",
						"unitProduct")
				.field("it.finalCharge", "finalCharge")
				.field("it.currencyUnit", "unitMoney")
				.field("it.createdTime", "date")
				.field("(SELECT rt.name FROM restaurant_table rt INNER JOIN invoiceDetail ii ON ii.tableCode = rt.code where ii.id = i.id) AS `table`",
						"table")
				.field("it.status AS `statusItem`", "status")
				.field("it.description", "note")
				.cond("it.invoiceId = i.id")
				.cond("it.type NOT LIKE '" + AccountingModelManager.typeSanXuat
						+ "'")
				.cond("it.currencyUnit = '" + tranfer.get("moneyUnit") + "'")
				.cond("i.activityType = " + "'" + tranfer.get("type") + "'");
		rQuery.cond("it.productCode IS NOT NULL ");
		if (!tranfer.get("firtDate").equals(""))
			rQuery.cond("i.startDate > '" + tranfer.get("firtDate") + "'");
		if (!tranfer.get("endDate").equals(""))
			rQuery.cond("i.startDate <= '" + tranfer.get("endDate") + "'");
		if (!tranfer.get("employee").equals("")) {
			rQuery.field(
					"(SELECT e.name FROM invoiceDetail ii INNER JOIN Employee e on ii.employeeCode = e.code  WHERE e.loginId = '"
							+ tranfer.get("employee")
							+ "' and ii.id = i.id ) AS `employee`", "employee");
			rQuery.cond("EXISTS (SELECT ii.employeeCode FROM InvoiceDetail ii INNER JOIN employee e on e.code = ii.employeeCode WHERE e.loginId = '"
					+ tranfer.get("employee") + "' and ii.id = i.id ) ");
		} else {
			rQuery.field(
					"(SELECT e.name FROM invoiceDetail ii INNER JOIN Employee e ON ii.employeeCode = e.code where ii.id = i.id) AS `employee`",
					"employee");
		}
		if (!tranfer.get("customer").equals("")) {
			rQuery.field(
					"(SELECT c.name FROM invoiceDetail ii INNER JOIN Customer c ON ii.customerCode = c.code WHERE c.loginId = '"
							+ tranfer.get("customer")
							+ "' and ii.id = i.id ) AS `customer`", "partner");
			rQuery.cond("EXISTS (SELECT ii.customerCode FROM invoiceDetail ii INNER JOIN Customer c ON ii.customerCode = c.code WHERE c.loginId = '"
					+ tranfer.get("customer") + "' and ii.id = i.id )");
		} else {
			rQuery.field(
					"(SELECT c.name FROM invoiceDetail ii INNER JOIN Customer c ON ii.customerCode = c.code where ii.id = i.id ) AS `customer`",
					"partner");
		}
		if (!tranfer.get("status").equals("Tất cả"))
			rQuery.cond("it.status = '" + tranfer.get("status") + "'");
		rQuery.orderBy("it.id");
		System.out.println(rQuery.createSQLQuery());
		ReportTable[] reportTable = accountingServiceClient
				.reportQuery(new SQLSelectQuery[] { rQuery });
		reportTable[0].dump(new String[] { "product","invoiceCode", "quantity",
				"unitProduct", "finalCharge", "unitMoney", "date", "employee",
				"table", "partner", "status", "note" });
		String[] field = new String[] { "product","invoiceCode", "quantity", "unitProduct",
				"finalCharge", "unitMoney", "date", "employee", "table",
				"partner", "status", "note" };
		List<Map<String, Object>> records = reportTable[0].getRecords();
		List<SplitProduct> splitProducts = new ArrayList<SplitProduct>();
		for (int i = 0; i < records.size(); i++) {
			Map<String, Object> record = records.get(i);
			Object[] values = new Object[field.length];
			for (int j = 0; j < field.length; j++) {
				values[j] = record.get(field[j]);
				if (values[j] == null)
					values[j] = "";
			}
			SplitProduct splitProduct = new SplitProduct(values[0].toString(),
					values[1].toString(), values[2].toString(),
					values[3].toString(), values[4].toString(),
					values[5].toString(), values[6].toString(),
					values[7].toString(), values[8].toString(),
					values[9].toString(), values[10].toString(),values[11].toString());
			splitProducts.add(splitProduct);
		}
		return splitProducts;
	}

	@Override
	public void click() {
	}

	@Override
	public int getListSize() {
		return listSplitProduct.size();
	}

	@Override
	public List<Integer> getColumnSum() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(3);
		list.add(5);
		return list;
	}

	@Override
	public DefaultTableModel loadTable(int currentPage, int pageSize) {
		try {
			tableModelSplitProduct.setData(listSplitProduct.subList(
					currentPage, pageSize));
		} catch (Exception e) {
			tableModelSplitProduct.setData(new ArrayList<SplitProduct>());
		}
		return tableModelSplitProduct;
	}

	@Override
	public boolean delete() {
		return false;
	}

	@Override
	public boolean isDelete() {
		return false;
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
		tranfer.put("firtDate", dateFormat.format(calendarStart.getTime()));
		tranfer.put("endDate", dateFormat.format(calendarEnd.getTime()));
		try {
			listSplitProduct = getListData();
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
