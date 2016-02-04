package com.hkt.module.accounting.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.JOptionPane;

import org.junit.After;
import org.junit.Before;
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
import com.hkt.module.accounting.entity.InvoiceTransaction.TransactionType;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.util.json.JSONSerializer;

public class InvoiceDetailRepositoryBehaviorUnitTest extends AbstractUnitTest {
  static {
    System.setProperty("hibernate.show_sql", "true") ;
    System.setProperty("hibernate.format_sql", "true") ;
  }
  
  @Autowired
  InvoiceDetailRepository detailRepo;
  
  @PersistenceContext 
  private EntityManager em;
  
  InvoiceDetail instance ;
  
  @Before
  public void setup() {
    System.out.println("*setup***************************************");
    instance = createInvoiceDetail("invoice-1"); 
    InvoiceItem item = createItem("item-1");
    instance.add(item) ;
    instance.add(createItem("item-2")) ;
    instance.add(createItem("item-3")) ;
    instance.add(createTransaction()) ;
    instance.add(createTransaction()) ;
    instance.add(createAttribute("table", "A01"));
    instance.add(createAttribute("table", "A02"));
    List<Contributor> contributors = new ArrayList<Contributor>();
    contributors.add(createContributor("long", "thu ngan"));
    instance.setContributors(contributors);
    instance = detailRepo.save(instance) ;
    System.out.println("long "+ detailRepo.getInvoiceItemById(1l));
    assertEquals(3, instance.getItems().size()) ;
    assertEquals(2, instance.getTransactions().size()) ;
    assertEquals(1, instance.getContributors().size()) ;
    assertEquals(1, detailRepo.count(InvoiceDetail.class)) ;
    assertEquals(3, detailRepo.count(InvoiceItem.class)) ;
    assertEquals(3, detailRepo.count(InvoiceItemAttribute.class)) ;
    InvoiceDetail detail = detailRepo.findOneByInvoiceItem(item.getId());
    assertEquals(1, detail.getItems().size()) ;
    assertEquals("A01", instance.getAttributeByName("table").get(0).getValue()) ;
    System.out.println("*end setup************************************");
  }
  
  @After
  public void teardown() {
    System.out.println("*teardown***************************************");
    detailRepo.delete(instance.getId()) ;
    assertEquals(0, detailRepo.count(InvoiceDetail.class)) ;
    assertEquals(0, detailRepo.count(InvoiceItem.class)) ;
    assertEquals(0, detailRepo.count(InvoiceItemAttribute.class)) ;
    assertEquals(0, detailRepo.count(InvoiceTransaction.class)) ;
    assertEquals(0, detailRepo.count(Contributor.class)) ;
    assertEquals(0, detailRepo.count(Attribute.class)) ;
    System.out.println("*end teardown**********************************");
  }
  
  @Test
  public void testGetInvoiceById() throws IOException {
    System.out.println("\n\n");
    System.out.println("#Test testGetInvoiceDetailById####");
    InvoiceDetail iDetail = detailRepo.findOne(instance.getId()) ;
    assertEquals(3, iDetail.getItems().size()) ;
    assertEquals(2, iDetail.getTransactions().size()) ;
    assertEquals(1, iDetail.getContributors().size()) ;
    assertEquals(1, detailRepo.count(InvoiceDetail.class)) ;
    assertEquals(3, detailRepo.count(InvoiceItem.class)) ;
    assertEquals(3, detailRepo.count(InvoiceItemAttribute.class)) ;
    System.out.println("##########################################################");
    System.out.println("\n\n");
  }
  
  @Test
  public void testUpdateBehavior() throws IOException {
    System.out.println("\n\n");
    System.out.println("#Test Update Invoice And InvoiceItem####");
    InvoiceDetail iDetail = detailRepo.findOne(instance.getId()) ;
    iDetail.add(createContributor("hkt/Phòng ban/E/F/B/C/D", "a"));
    detailRepo.save(iDetail);
   // testUpdateBehavior(iDetail) ;
    for(Contributor c: detailRepo.findInvoiceDetailByIdentifierId("hkt/Phòng ban/E/F/B/C/D").get(0).getContributors()){
      JOptionPane.showMessageDialog(null, c);
    }
    System.out.println("##########################################################");
    System.out.println("\n\n");
  }
  
  @Test
  public void testUpdateSerializationBehavior() throws IOException {
    System.out.println("\n\n");
    System.out.println("#Test Update Serialization Invoice And InvoiceItem####");
    InvoiceDetail iDetail = detailRepo.findOne(instance.getId()) ;
    String jsonData = JSONSerializer.INSTANCE.toString(iDetail) ;
    iDetail = JSONSerializer.INSTANCE.fromString(jsonData, InvoiceDetail.class) ;
    testUpdateBehavior(iDetail) ;
    System.out.println("##########################################################");
    System.out.println("\n\n");
  }
  
  @Test
  public void testSearchBehavior() {
    System.out.println("#Search Entity###########################################");
    FilterQuery query = new FilterQuery();
    query.add("status", FilterQuery.Operator.STRINGEQ, "Paid");
    List<InvoiceDetail> searchList = detailRepo.search(query).getData() ;
    assertTrue(searchList.size() == 1);
    assertTrue(detailRepo.searchInvoices(query).getData().size() == 1);
    InvoiceDetail searchInvoiceDetail = searchList.get(0) ;
    assertEquals(3, searchInvoiceDetail.getItems().size());
    assertEquals(2, searchInvoiceDetail.getTransactions().size());
    assertEquals(1, searchInvoiceDetail.getContributors().size());
    System.out.println("##########################################################");
  }
  
  @Test
  public void testSearchInvoicesBehavior() {
    System.out.println("#Search Entity###########################################");
    FilterQuery query = new FilterQuery();
    query.add("status", FilterQuery.Operator.STRINGEQ, "Paid");
    List<Invoice> searchList = detailRepo.searchInvoices(query).getData() ;
    assertEquals(1, searchList.size());
    System.out.println("##########################################################");
  }
  
  void testUpdateBehavior(InvoiceDetail iDetail) throws IOException {
    iDetail.setDescription("Update Invoice") ;
    InvoiceItem item1 = iDetail.getItems().get(0);
    item1.setDescription("Update InvoiceItem") ;
    InvoiceItem item2 =  iDetail.getItems().get(1) ;
    item2.setDescription("description") ;
    iDetail.getItems().remove(2) ;
    iDetail.add(createTransaction()) ;
    iDetail = detailRepo.save(iDetail) ;
    assertEquals(2, iDetail.getItems().size()) ;
    assertEquals(3, iDetail.getTransactions().size()) ;
    assertEquals(1, instance.getContributors().size()) ;
    assertEquals("Update Invoice", iDetail.getDescription()) ;
    assertEquals("Update InvoiceItem", iDetail.getItems().get(0).getDescription()) ;
    
    iDetail = detailRepo.getInvoiceDetailById(iDetail.getId()) ;
    assertEquals("Update Invoice", iDetail.getDescription()) ;
    assertEquals(2, detailRepo.count(InvoiceItem.class)) ;
    assertEquals(2, iDetail.getItems().size()) ;
    assertEquals(3, iDetail.getTransactions().size()) ;
    assertEquals(1, instance.getContributors().size()) ;
    System.out.println(JSONSerializer.INSTANCE.toString(iDetail));
    assertEquals("Update InvoiceItem", iDetail.getItems().get(0).getDescription()) ;
    assertEquals(1, detailRepo.count(InvoiceDetail.class)) ;
  }
  
  public InvoiceDetail createInvoiceDetail(String invoiceCode) {
    InvoiceDetail invoice = new InvoiceDetail();
    invoice.setInvoiceCode(invoiceCode);
    invoice.setActivityType(Invoice.ActivityType.Payment);
    invoice.setStartDate(new Date());
    invoice.setEndDate(new Date());
    invoice.setTotal(100000);
    invoice.setTotalTax(0);
    invoice.setDiscount(0);
    invoice.setDiscountByItem(0);
    invoice.setDiscountRate(0);
    invoice.setFinalCharge(100000);
    invoice.setCurrencyUnit("VND");
    invoice.setDescription("Tra luong thang 1 nhan vien " + invoiceCode);
    invoice.setNote("Tra luong thang 1 nhan vien" + invoiceCode);
    invoice.setStatus(Invoice.Status.Paid);
    invoice.setType(invoiceCode);
    invoice.setDescription("description") ;
    return invoice;
  }
  
  public InvoiceItem createItem(String itemCode) {
    InvoiceItem item = new InvoiceItem();
    item.setItemCode(itemCode);
    item.setItemName(itemCode + "-name");
    item.setCurrencyUnit("VND");
    item.setUnitPrice(1000);
    item.setDiscount(100);
    item.setTax(1000);
    item.setTaxRate(0);
    item.setDiscountRateByInvoice(0);
    item.setFinalCharge(10000);
    item.setQuantity(10);
    item.setTotal(10000);
    item.setDescription("description") ;
    item.add(createItemRef(item.getItemName(), "iphone5s"));
    return item;
  }
  
  private InvoiceTransaction createTransaction(){
    InvoiceTransaction transaction = new InvoiceTransaction();
    transaction.setCurrencyUnit("VND");
    transaction.setTotal(100000);
    transaction.setTransactionType(TransactionType.Wire);
    return transaction;
  }
  
  private Attribute createAttribute(String name, String value){
    Attribute attribute = new Attribute();
    attribute.setName(name);
    attribute.setValue(value);
    return attribute;
  }
  
  private Contributor createContributor(String loginId, String role){
    Contributor contributor = new Contributor();
    contributor.setType(Type.User);
    contributor.setIdentifierId(loginId);
    contributor.setRole(role);
    contributor.setPercent(100);
    return contributor;
  }
  
  public InvoiceItemAttribute createItemRef(String name, String code) {
    InvoiceItemAttribute item = new InvoiceItemAttribute();
    item.setValue(code);
    item.setName(name);
    return item;
  }
}
