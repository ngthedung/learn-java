package com.hkt.module.accounting.repository;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hkt.module.accounting.entity.Attribute;
import com.hkt.module.accounting.entity.Contributor;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.accounting.entity.InvoiceItemAttribute;
import com.hkt.module.accounting.entity.InvoiceTransaction;
import com.hkt.module.core.entity.ReportTable;
import com.hkt.module.core.entity.SQLSelectQuery;

public class InvoiceReportUnitTest extends AbstractInvoiceDetailRepositoryQueryUnitTest {
  static {
    System.setProperty("hibernate.show_sql", "false");
    System.setProperty("hibernate.format_sql", "false");
  }

  AccountConfig[] accountConfigs = { new AccountConfig(1l, "tuan", "Tuan", 10000000),
      new AccountConfig(2l, "long", "Long", 5000000) };

  ProductConfig[] mobileProductConfigs = { new ProductConfig("iphone5", "iphone5", 15000000),
      new ProductConfig("Samsung Galaxy S4", "samsung-galaxy-s4", 10000000) };

  int YEAR = 2013;

  @Before
  public void setup() {
    for (AccountConfig sel : accountConfigs) {
      for (int i = 0; i < 12; i++) {
        createSalaryInvoice(YEAR, i, sel);
      }
    }
    for (int i = 1; i <= 12; i++) {
      for (ProductConfig sel : mobileProductConfigs) {
        createProductInvoice(2013, i, 1, sel);
      }
    }

    assertEquals(48, detailRepo.count(InvoiceDetail.class));
    assertEquals(48, detailRepo.count(InvoiceItem.class));
    assertEquals(72, detailRepo.count(InvoiceTransaction.class));
  }

  @After
  public void teardown() {
    detailRepo.deleteAll();
    assertEquals(0, detailRepo.count(InvoiceDetail.class));
    assertEquals(0, detailRepo.count(InvoiceItem.class));
    assertEquals(0, detailRepo.count(InvoiceItemAttribute.class));
    assertEquals(0, detailRepo.count(InvoiceTransaction.class));
    assertEquals(0, detailRepo.count(Contributor.class));
    assertEquals(0, detailRepo.count(Attribute.class));
  }

  @Test
  public void testSalaryReport() {
    SQLSelectQuery rQuery = new SQLSelectQuery() ;
    rQuery.
      table("InvoiceDetail", "Attribute").
      field("Attribute.value", "Employee").
      field("EXTRACT(YEAR FROM InvoiceDetail.startDate) AS year", "Year").
      field("EXTRACT(MONTH FROM InvoiceDetail.startDate) AS month", "Month").
      field("count(InvoiceDetail.type)", "Payment").
      cond("Attribute.invoiceId = InvoiceDetail.id").
      cond("Attribute.name = 'employeeLoginId'").
      cond("InvoiceDetail.type = 'SalaryPayment'").
      groupBy("year", "month", "Attribute.value").
      orderBy("year", "month") ;
    System.out.println(rQuery.createSQLQuery());
    ReportTable[] reportTable = detailRepo.reportQuery(new SQLSelectQuery[] { rQuery }) ;
    reportTable[0].dump(new String[] {"Year", "Month", "Employee", "Payment"}) ;
  }
  
  @Test
  public void testRevenueEmployeeReport() {
    SQLSelectQuery rQuery = new SQLSelectQuery() ;
    rQuery.
      table("InvoiceDetail", "Contributor").
      field("Contributor.role", "Role").
      field("Contributor.identifierId", "Employee").
      field("sum(InvoiceDetail.finalCharge)", "Grand Total").
      field("sum(InvoiceDetail.totalPaid)", "Total Paid").
      cond("Contributor.invoiceId = InvoiceDetail.id").
      groupBy("Contributor.role, Contributor.identifierId").
      orderBy("Contributor.identifierId") ;
    System.out.println(rQuery.createSQLQuery());
    ReportTable[] reportTable = detailRepo.reportQuery(new SQLSelectQuery[] { rQuery }) ;
    reportTable[0].dump(new String[] {"Employee","Role","Grand Total", "Total Paid"}) ;
  }
  
  @Test
  public void testInvoiceProductReport(){
	  SQLSelectQuery rQuery = new SQLSelectQuery();
	  rQuery.
	  	table("InvoiceDetail", "InvoiceItem", "InvoiceItemAttribute").
	  	field("InvoiceItemAttribute.value", "Product Code").
	  	field("SUM(InvoiceItem.quantity)", "Quantity").
	  	field("SUM(InvoiceItem.finalCharge)", "Grand Total").
	  	field("InvoiceDetail.activityType", "Activity Type").
	  	cond("InvoiceDetail.id = InvoiceItem.invoiceId").
	  	cond("InvoiceItem.id = InvoiceItemAttribute.invoiceItemId").
	  	groupBy("InvoiceItemAttribute.value", "InvoiceDetail.activityType");
  	  System.out.println(rQuery.createSQLQuery());
      ReportTable[] reportTable = detailRepo.reportQuery(new SQLSelectQuery[] { rQuery }) ;
      reportTable[0].dump(new String[] {"Product Code","Quantity", "Grand Total", "Activity Type"}) ;
  }
  
  @Test
  public void testRPEmployeeReport(){
	  SQLSelectQuery rQuery = new SQLSelectQuery();
	  rQuery.
	  	table("InvoiceDetail", "InvoiceItem", "Contributor").
	  	field("Contributor.identifierId", "Name").
	  	field("SUM(InvoiceItem.finalCharge)", "Grand Receipt").
	  	field("SUM(InvoiceItem.finalCharge)", "Grand Payment").
	  	cond("InvoiceItem.invoiceId = InvoiceDetail.id").
	  	cond("Contributor.invoiceId = InvoiceDetail.id").
	  	groupBy("InvoiceDetail.activityType", "Contributor.identifierId");
	  System.out.println(rQuery.createSQLQuery());
      ReportTable[] reportTable = detailRepo.reportQuery(new SQLSelectQuery[] { rQuery }) ;
      reportTable[0].dump(new String[] {"Name","Grand Receipt", "Grand Payment"}) ;
  }
  
  @Test
  public void testInvoiceTransactionReport() {
    SQLSelectQuery rQuery = new SQLSelectQuery() ;
    rQuery.
      table("InvoiceDetail", "InvoiceTransaction").
      field("InvoiceTransaction.createdBy", "Employee").
      field("sum(InvoiceTransaction.total)", "Total").
      field("InvoiceDetail.activityType", "Activity Type").
      field("EXTRACT(YEAR FROM InvoiceDetail.startDate) AS year", "Year").
      field("EXTRACT(MONTH FROM InvoiceDetail.startDate) AS month", "Month").
      cond("InvoiceTransaction.invoiceId = InvoiceDetail.id").
      groupBy("year", "month", "InvoiceTransaction.createdBy", "InvoiceDetail.activityType").
      orderBy("year", "month") ;
    System.out.println(rQuery.createSQLQuery());
    ReportTable[] reportTable = detailRepo.reportQuery(new SQLSelectQuery[] { rQuery }) ;
    reportTable[0].dump(new String[] {"Employee","Total", "Activity Type","Year", "Month"}) ;
  }
}