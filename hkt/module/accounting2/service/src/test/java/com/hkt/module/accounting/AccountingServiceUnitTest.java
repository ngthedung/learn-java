package com.hkt.module.accounting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hkt.module.accounting.entity.Attribute;
import com.hkt.module.accounting.entity.Bank;
import com.hkt.module.accounting.entity.BankAccount;
import com.hkt.module.accounting.entity.BankTransaction;
import com.hkt.module.accounting.entity.Contributor;
import com.hkt.module.accounting.entity.Invoice;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.accounting.entity.InvoiceTransaction;
import com.hkt.module.accounting.util.AccountingScenario;
import com.hkt.module.core.ServiceCallback;
import com.hkt.module.core.entity.AbstractPersistable.State;
import com.hkt.module.core.entity.ReportTable;
import com.hkt.module.core.entity.SQLSelectQuery;
import com.hkt.util.json.JSONSerializer;

public class AccountingServiceUnitTest extends AbstractUnitTest {
  static ServiceCallback<AccountingService> FAIL_CALLBACK = new ServiceCallback<AccountingService>() {
    public void callback(AccountingService service) {
      throw new RuntimeException("Fail. Expect a rollback if method annotate with the Transactionnal");
    }
  };

  @Autowired
  AccountingService service;
  
  @PersistenceContext
  private EntityManager em;

  private AccountingScenario scenario;

  @Before
  public void setup() throws Exception {
    scenario = AccountingScenario.loadTest();
    service.createScenario(scenario);
  }

  @After
  public void cleandb() throws Exception {
    service.deleteAll();
  }

  @Test
  public void testJSONSerialization() throws Exception {
    assertTrue(scenario.getBankAccounts().size() > 0);
    assertTrue(scenario.getInvoices().size() > 0);
    System.out.println(JSONSerializer.INSTANCE.toString(scenario));
  }

  @Test
  public void testBankAccount() {
    BankAccount bankAccountCNQ = service.getBankAccountById("vcb-credit-ngoquyen");
    assertNotNull(bankAccountCNQ);
    assertNotNull(service.getBankAccountById("vcb-credit-ngoquyen"));
    service.deleteBankAccountByAccountId(bankAccountCNQ.getBankAccountId());
    assertNull(service.getBankAccountById("vcb-credit-ngoquyen"));
  }

  @Test
  public void testInvoiceDetail() throws Exception {
    Invoice invoice = service.searchInvoice(null).getData().get(0);
    InvoiceDetail invoiceDetail = service.getInvoiceDetail(invoice.getId());
    try {
      service.deleteInvoice(invoiceDetail, FAIL_CALLBACK);
    } catch (Throwable t) {
      System.out.println("Fail callback exception: " + t.getMessage());
    }
    assertEquals(2, invoiceDetail.getItems().size());
    assertEquals(3, invoiceDetail.getAttributes().size());
    assertEquals(4, invoiceDetail.getContributors().size());
    assertEquals(1, invoiceDetail.getTransactions().size());

    assertEquals(21, count(InvoiceItem.class));
    assertEquals(46, count(Attribute.class));
    assertEquals(43, count(Contributor.class));
    assertEquals(11, count(InvoiceTransaction.class));
    
    invoiceDetail.getItems().get(0).setPersistableState(State.DELETED);
    assertEquals(2, invoiceDetail.getItems().size());
    invoiceDetail=service.updateInvoiceDetail(invoiceDetail);
    assertEquals(1, invoiceDetail.getItems().size());

    service.deleteTest("SA");

    assertEquals(20, count(InvoiceItem.class));
    assertEquals(46, count(Attribute.class));
    assertEquals(43, count(Contributor.class));
    assertEquals(11, count(InvoiceTransaction.class));
  }

  @Test
  public void testSearchInvoiceDetails() throws Exception {
    System.out.println(service.findInvoiceDetailByAttributeItemValue("a:b:c"));
  }
  
  @Test
  public void testInvoiceProductReport() {
    SQLSelectQuery rQuery = new SQLSelectQuery() ;
    rQuery.
      table("InvoiceDetail", "InvoiceItem", "InvoiceItemAttribute").
      field("InvoiceItemAttribute.name", "Product").
      field("InvoiceItemAttribute.value", "").
      field("sum(InvoiceItem.quantity)", "Quantity").
      field("sum(InvoiceItem.total)", "Total").
      field("InvoiceDetail.activityType", "Activity Type").
      cond("InvoiceItem.invoiceId = InvoiceDetail.id").
      cond("InvoiceItemAttribute.invoiceItemId = InvoiceItem.id").
      cond("InvoiceItemAttribute.name = 'Product'").
      groupBy("InvoiceItemAttribute.name, InvoiceItemAttribute.value, InvoiceDetail.activityType").
      orderBy("InvoiceItemAttribute.name") ;
    System.out.println(rQuery.createSQLQuery());
    ReportTable[] reportTable = service.reportQuery(new SQLSelectQuery[] { rQuery }) ;
    reportTable[0].dump(new String[] {"Product","Quantity","Total", "Activity Type"}) ;
  }
  
  @Test
  public void testRevenueEmployeeReport() {
    SQLSelectQuery rQuery = new SQLSelectQuery() ;
    rQuery.
      table("InvoiceDetail", "Contributor").
      field("Contributor.role", "Role").
      field("InvoiceDetail.activityType",  "Activity Type").
      field("Contributor.identifierId", "Employee").
      field("sum(InvoiceDetail.finalCharge)", "Grand Total").
      field("sum(InvoiceDetail.totalPaid)", "Total Paid").
      field("count(InvoiceDetail.type)", "Payment").
      cond("Contributor.invoiceId = InvoiceDetail.id").
      groupBy("Contributor.role, Contributor.identifierId", "InvoiceDetail.activityType").
      orderBy("Contributor.identifierId") ;
    System.out.println(rQuery.createSQLQuery());
    ReportTable[] reportTable = service.reportQuery(new SQLSelectQuery[] { rQuery }) ;
    reportTable[0].dump(new String[] {"Employee","Role","Grand Total", "Total Paid", "Payment","Activity Type"}) ;
  }
  
  @Test
  public void testProjectReport(){
    SQLSelectQuery rQuery = new SQLSelectQuery();
    rQuery.
      table("InvoiceDetail", "Attribute").
      field("Attribute.value", "Name").
      field("EXTRACT(YEAR FROM InvoiceDetail.startDate) AS year", "Year").
      field("EXTRACT(MONTH FROM InvoiceDetail.startDate) AS month", "Month").
      field("sum(InvoiceDetail.finalCharge)", "Grand Total").
      field("sum(InvoiceDetail.totalPaid)", "Total Paid").
      cond("Attribute.name = 'restaurant:name'").
      cond("Attribute.invoiceId = InvoiceDetail.id").
      groupBy("year", "month", "Attribute.value").
      orderBy("year", "month") ;
    System.out.println(rQuery.createSQLQuery());
    ReportTable[] reportTable = service.reportQuery(new SQLSelectQuery[] { rQuery }) ;
    reportTable[0].dump(new String[] {"Name", "Year", "Month", "Grand Total", "Total Paid"});
  }
  
//  @Test
//  public void testInvoiceCalculator() throws Exception{
//    Invoice invoice = service.searchInvoice(null).getData().get(0);
//    InvoiceDetail invoiceDetail = service.getInvoiceDetail(invoice.getId());
//
//    assertEquals(0, invoiceDetail.getFinalCharge(), 0);
//    assertEquals(90000, invoiceDetail.getTotalPaid(), 0);
//    assertEquals(2000, invoiceDetail.getItems().get(0).getDiscountByInvoice(), 0);
//    assertEquals(18000, invoiceDetail.getItems().get(0).getFinalCharge(), 0);
//    
//    for(InvoiceItem item : invoiceDetail.getItems()){
//      item.setDiscount(10000);
//      item.setTaxRate(10);
//    }
//    invoiceDetail.calculate(new DefaultInvoiceCalculator());
//    service.updateInvoiceDetail(invoiceDetail);
//    invoiceDetail = service.getInvoiceDetail(invoice.getId());
//    assertEquals(20000, invoiceDetail.getDiscountByItem(), 0);
//    assertEquals(800, invoiceDetail.getItems().get(0).getTax(), 0);
//  }
  
  protected <T> List<T> select(Class<T> type) {
    Query q = em.createQuery("SELECT o FROM " + type.getSimpleName() + " o");
    List<T> result = q.getResultList();
    return result;
  }

  protected <T> int count(Class<T> type) {
    return select(type).size();
  }
  
	@Test
	public void testBank() throws Exception{
		Bank bank = new Bank();
		bank.setName("Bank");
		bank = service.saveBank(bank);
		
		assertEquals("Bank", bank.getName());
		assertEquals(1, service.getAllBank().size());
		
		try {
			service.deleteBankCallBack(bank);
		} catch (Throwable t) {
			System.out.println("Fail callback exception: " + t.getMessage());
		}

		assertEquals(1, service.getAllBank().size());
	}
	
	@Test
	public void testBankTransaction() throws Exception{
		BankTransaction bankTransaction = new BankTransaction();
		bankTransaction.setName("BankTransaction");
		bankTransaction.setBankCode("125");
		bankTransaction.setTotal(50000);
		bankTransaction.setTransactionCode("TransactionCode");
		bankTransaction.setType(false);
		
		bankTransaction = service.saveBankTransaction(bankTransaction);
		
		assertEquals(1, service.getAllBankTransaction().size());
		assertEquals(1, service.getByBankCode("125").size());
		assertEquals(1, service.getTracsactionCode("nsa").size());
		assertEquals(1, service.getByBankType(false).size());
		assertEquals(1, service.getByBankRangeTotal(30000).size());
	}
}
