package com.hkt.module.accounting.repository;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hkt.module.accounting.entity.Attribute;
import com.hkt.module.accounting.entity.Contributor;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.accounting.entity.InvoiceItemAttribute;
import com.hkt.module.accounting.entity.InvoiceTransaction;
import com.hkt.module.core.entity.FilterQuery;

public class InvoiceDetailRepositoryQueryUnitTest extends AbstractInvoiceDetailRepositoryQueryUnitTest {

  static {
    System.setProperty("hibernate.show_sql", "false");
    System.setProperty("hibernate.format_sql", "false");
  }

  AccountConfig[] accountConfigs           = { new AccountConfig(1l, "tuan", "Tuan", 10000000),
      new AccountConfig(2l, "long", "Long", 5000000) };

  ProductConfig[] restaurantProductConfigs = { new ProductConfig("cafe", "cafe", 15000),
      new ProductConfig("cam-ep", "cam-ep", 10000) };

  int             YEAR                     = 2013;

  @Before
  public void setup() {
//     createInvoiceDetail();

    for (AccountConfig sel : accountConfigs) {
      for (int i = 1; i <= 12; i++) {
        createSalaryInvoice(YEAR, i, sel);
      }
    }

    for (int i = 1; i <= 12; i++) {
      for (ProductConfig sel : restaurantProductConfigs) {
        if (i == 6) {
          for (int j = 1; j <= 6; j++) {
            createProductInvoice(2013, i, j, sel);
          }
        } else {
          createProductInvoice(2013, i, 1, sel);
        }

      }
    }

    assertEquals(58, detailRepo.count(InvoiceDetail.class));
    assertEquals(58, detailRepo.count(InvoiceItem.class));
    assertEquals(92, detailRepo.count(InvoiceTransaction.class));
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
  public void test1() {
    System.out.println("......"+detailRepo.findInvoiceDetailByCode("1-12-2013","Trả Trước"));
  }

  @Test
  public void testQuerySalaryInvoice() {
    FilterQuery query = new FilterQuery();
    query.add("invoice.type", FilterQuery.Operator.STRINGEQ, "SalaryPayment");
    List<InvoiceDetail> invoices = detailRepo.query(query).getData();
    Assert.assertEquals(24, invoices.size());

    query = new FilterQuery();
    query.add("item.itemCode", FilterQuery.Operator.STRINGEQ, ("SAL-tuan-1-" + YEAR));
    invoices = detailRepo.query(query).getData();
    Assert.assertEquals(1, invoices.size());

    query = new FilterQuery();
    query.add("reference.name", FilterQuery.Operator.STRINGEQ, "Account");
    invoices = detailRepo.query(query).getData();
    Assert.assertEquals(24, invoices.size());

    query = new FilterQuery();
    query.add("reference.name", FilterQuery.Operator.STRINGEQ, "Account");
    query.add("reference.value", FilterQuery.Operator.STRINGEQ, "long");
    invoices = detailRepo.query(query).getData();
    Assert.assertEquals(12, invoices.size());
  }

  @Test
  public void testQuerySalaryInvoiceItem() {
    FilterQuery query = new FilterQuery();
    query.add("item.itemCode", FilterQuery.Operator.STRINGEQ, ("SAL-tuan-1-" + YEAR));
    List<InvoiceItem> items = detailRepo.queryInvoiceItems(query).getData();
    Assert.assertEquals(1, items.size());

    query = new FilterQuery();
    query.add("reference.name", FilterQuery.Operator.STRINGEQ, "Account");
    items = detailRepo.queryInvoiceItems(query).getData();
    Assert.assertEquals(24, items.size());

    query = new FilterQuery();
    query.add("reference.name", FilterQuery.Operator.STRINGEQ, "Account");
    query.add("reference.value", FilterQuery.Operator.STRINGEQ, "long");
    items = detailRepo.queryInvoiceItems(query).getData();
    Assert.assertEquals(12, items.size());
  }

  @Test
  public void testQueryInvoiceItems() {
    FilterQuery query = new FilterQuery();
    query.add("item.quantityReal", FilterQuery.Operator.LT, ("item.quantity"));
    List<InvoiceDetail> itemTests = detailRepo.query(query).getData();
    List<InvoiceItem> invoiceItems = new ArrayList<InvoiceItem>();
    for (InvoiceDetail invoiceDetail : itemTests) {
      invoiceItems.addAll(invoiceDetail.getItems());
    }
    Assert.assertEquals(34, invoiceItems.size());
  }

  @Test
  public void testQueryProductInvoice() {
    FilterQuery query = new FilterQuery();
    query.add("invoice.type", FilterQuery.Operator.STRINGEQ, "ProductPayment");
    List<InvoiceDetail> invoices = detailRepo.query(query).getData();
    Assert.assertEquals(34, invoices.size());

    query = new FilterQuery();
    query.add("item.itemCode", FilterQuery.Operator.STRINGEQ, ("BAA -cafe-1-1-" + YEAR));
    invoices = detailRepo.query(query).getData();
    Assert.assertEquals(1, invoices.size());

    query = new FilterQuery();
    query.add("reference.name", FilterQuery.Operator.STRINGEQ, "Product");
    invoices = detailRepo.query(query).getData();
    Assert.assertEquals(34, invoices.size());

    query = new FilterQuery();
    query.add("reference.value", FilterQuery.Operator.STRINGEQ, "cafe");
    invoices = detailRepo.query(query).getData();
    Assert.assertEquals(17, invoices.size());

    query = new FilterQuery();
    query.add("attribute.value", FilterQuery.Operator.STRINGEQ, "A1");
    invoices = detailRepo.query(query).getData();
    Assert.assertEquals(2, invoices.size());

    query = new FilterQuery();
    query.add("contributor.identifierId", FilterQuery.Operator.STRINGEQ, "long");
    invoices = detailRepo.query(query).getData();
    Assert.assertEquals(34, invoices.size());

    query = new FilterQuery();
    query.add("contributor.role", FilterQuery.Operator.STRINGEQ, "waiter");
    query.add("contributor.identifierId", FilterQuery.Operator.STRINGEQ, "long");
    invoices = detailRepo.query(query).getData();
    Assert.assertEquals(34, invoices.size());

    query = new FilterQuery();
    query.add("startDate", FilterQuery.Operator.GT, getDate(2013, 6, 1));
    query.add("startDate", FilterQuery.Operator.LT, getDate(2013, 6, 6));
    query.add("type", FilterQuery.Operator.EQUAL, ("ProductPayment"));
    query.add("invoiceCode", FilterQuery.Operator.LIKE, ("BAA -cafe*"));
    invoices = detailRepo.search(query).getData();
    Assert.assertEquals(5, invoices.size());
  }

}
