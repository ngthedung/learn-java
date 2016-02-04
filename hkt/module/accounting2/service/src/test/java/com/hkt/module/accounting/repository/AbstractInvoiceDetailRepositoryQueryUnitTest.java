package com.hkt.module.accounting.repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hkt.module.accounting.AbstractUnitTest;
import com.hkt.module.accounting.entity.Attribute;
import com.hkt.module.accounting.entity.Contributor;
import com.hkt.module.accounting.entity.Contributor.Type;
import com.hkt.module.accounting.entity.Invoice;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.accounting.entity.InvoiceItemAttribute;
import com.hkt.module.accounting.entity.InvoiceTransaction;
import com.hkt.module.accounting.entity.Invoice.ActivityType;
import com.hkt.module.accounting.entity.Invoice.Status;
import com.hkt.module.accounting.entity.InvoiceTransaction.TransactionType;

public class AbstractInvoiceDetailRepositoryQueryUnitTest extends AbstractUnitTest {
  @Autowired
  InvoiceDetailRepository detailRepo;
  
  @Test
  public void test(){
    
  }
  void createSalaryInvoice(int year, int month, AccountConfig config) {
    InvoiceDetail invoice = new InvoiceDetail();
    invoice.setInvoiceCode("SAL-" + config.loginId + "-" + month + "-" + year);
    invoice.setType("SalaryPayment");
    invoice.setActivityType(Invoice.ActivityType.Payment);
    invoice.setTotal(config.salary);
    invoice.setFinalCharge(config.salary);
    invoice.setCurrencyUnit("VND");
    invoice.setDescription(String.format("Tra luong thang %d cho %s", month, config.name));
    invoice.setStatus(Invoice.Status.Paid);
    invoice.setStartDate(getDate(year, month, 15));
    invoice.setEndDate(getDate(year, month, 15));

    
    InvoiceItem salItem = new InvoiceItem();
    salItem.setItemCode("SAL-" + config.loginId + "-" + month + "-" + year);
    salItem.setItemName(String.format("Salary For %s", config.name));
    salItem.setCurrencyUnit("VND");
    salItem.setUnitPrice(config.salary);
    salItem.setDescription(String.format("Tra luong thang %d cho %s", month, config.name));
    invoice.add(salItem);

    InvoiceItemAttribute salItemRef = new InvoiceItemAttribute();
    salItemRef.setValue(config.loginId);
    salItemRef.setName("Account");
    salItem.add(salItemRef);
    
    InvoiceItemAttribute salItemRef1 = new InvoiceItemAttribute();
    salItemRef1.setValue(config.loginId);
    salItemRef1.setName("Account");
    salItem.add(salItemRef1);
    
    InvoiceTransaction transaction = new InvoiceTransaction();
    transaction.setCreatedBy(config.loginId);
    transaction.setCurrencyUnit("VND");
    transaction.setTotal(config.salary);
    transaction.setTransactionType(TransactionType.Wire);
    invoice.add(transaction);
    
    Attribute attr = new Attribute() ;
    attr.setName("employeeLoginId") ;
    attr.setValue(config.loginId) ;
    invoice.add(attr) ;
    detailRepo.save(invoice);
  }

  void createProductInvoice(int year, int month, int day, ProductConfig config) {
    InvoiceDetail invoice = new InvoiceDetail();
    invoice.setInvoiceCode("BAA -" + config.pcode + "-"+ day + "-" + month + "-" + year);
    invoice.setInvoiceName(invoice.getInvoiceCode());
    invoice.setType("Trả Trước");
    invoice.setActivityType(Invoice.ActivityType.Payment);
    invoice.setTotal(config.price);
    invoice.setFinalCharge(config.price);
    invoice.setTotalPaid(config.price);
    invoice.setCurrencyUnit("VND");
    invoice.setDescription(String.format("Ban hang %d cho %s", month, config.name));
    invoice.setStatus(Invoice.Status.Paid);
    invoice.setStartDate(getDate(year, month, day));
    invoice.setEndDate(getDate(year, month, day));

    InvoiceItem salItem = new InvoiceItem();
    salItem.setItemCode("BAA -" + config.pcode + "-"+ day + "-" + month + "-" + year);
    salItem.setItemName(String.format("Product For %s", config.name));
    salItem.setCurrencyUnit("VND");
    salItem.setUnitPrice(config.price);
    salItem.setQuantity(3);
    salItem.setQuantityReal(0);
    salItem.setDescription(String.format("Ban Product %d cho %s", month, config.name));
    invoice.add(salItem);

    InvoiceItemAttribute salItemRef = new InvoiceItemAttribute();
    salItemRef.setValue(config.pcode);
    salItemRef.setName("Product");
    salItem.add(salItemRef);
    
    Attribute attribute = new Attribute();
    attribute.setName("restaurant:table");
    attribute.setValue("A"+month);
    invoice.add(attribute);
    
    Contributor contributor1 = new Contributor();
    
    contributor1.setIdentifierId("long"+config.pcode + "-"+ day + "-" + month + "-" + year);
    contributor1.setRole("waiter");
    contributor1.setType(Type.User);
    contributor1.setPercent(100);
    invoice.add(contributor1);
    
    Contributor contributor2 = new Contributor();
    contributor2.setIdentifierId("tuan");
    contributor2.setRole("cashier");
    contributor2.setType(Type.User);
    contributor2.setPercent(100);
    invoice.add(contributor2);
    
    InvoiceTransaction transaction = new InvoiceTransaction();
    transaction.setCurrencyUnit("VND");
    transaction.setTotal(config.price);
    transaction.setCreatedBy("long");
    transaction.setTransactionType(TransactionType.Wire);
    invoice.add(transaction);
    
    InvoiceTransaction transaction1 = new InvoiceTransaction();
    transaction1.setCreatedBy("long");
    transaction1.setCurrencyUnit("VND");
    transaction1.setTotal(config.price);
    transaction1.setTransactionType(TransactionType.Wire);
    invoice.add(transaction1);
    detailRepo.save(invoice);
  }
  
  public void createInvoiceDetail() {
    InvoiceDetail detail = new InvoiceDetail();
    List<InvoiceItem> items = new ArrayList<InvoiceItem>();
    
    InvoiceItem item = new InvoiceItem();
    item.setItemCode("item-test-1");
    item.setItemName("test");
    item.setQuantity(3);
    item.setQuantityReal(0);
    item.setUnitPrice(5000000);
    item.setTotal(15000000);
    item.setCurrencyUnit("VND");
    item.setFinalCharge(15000000);
    List<InvoiceItemAttribute> references = new ArrayList<InvoiceItemAttribute>();
    InvoiceItemAttribute attribute = new InvoiceItemAttribute();
    attribute.setName("product");
    attribute.setValue("iphone5s");
    attribute.setAuthor("a:b:c");
    references.add(attribute);
    item.setReferences(references);
    items.add(item);
    
    detail.setInvoiceCode("001");
    detail.setCurrencyUnit("VND");
    detail.setActivityType(ActivityType.Payment);
    detail.setStatus(Status.Paid);
    detail.setTotal(15000000);
    detail.setFinalCharge(15000000);
    detail.setStartDate(new Date());
    detail.setEndDate(new Date());
    detail.setItems(items);
    detail.setContributors(new ArrayList<Contributor>());
    detail.setAttributes(new ArrayList<Attribute>());
    detail.setTransactions(new ArrayList<InvoiceTransaction>());
    detailRepo.save(detail);
  }

  
  Date getDate(int year, int month, int day) {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.YEAR, year);
    cal.set(Calendar.MONTH, month);
    cal.set(Calendar.DAY_OF_MONTH, day);
    return cal.getTime();
  }

  static public class AccountConfig {
    long accountId;
    String loginId;
    String name;
    double salary;

    AccountConfig(long accountId, String loginId, String name, double salary) {
      this.accountId = accountId;
      this.loginId = loginId;
      this.name = name;
      this.salary = salary;
    }
  }

  static public class ProductConfig {
    String name;
    String pcode;
    double price;

    ProductConfig(String name, String pcode, double price) {
      this.name = name;
      this.pcode = pcode;
      this.price = price;
    }
  }
    
}
