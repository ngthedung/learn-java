package com.hkt.module.accounting.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hkt.module.accounting.AbstractUnitTest;
import com.hkt.module.accounting.AccountingService;
import com.hkt.module.accounting.entity.Invoice;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.util.AccountingScenario;
import com.hkt.module.core.ServiceCallback;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.rest.Request;
import com.hkt.module.core.rest.Response;
import com.hkt.module.core.rest.RestService;
import com.hkt.util.json.JSONSerializer;

public class AccountingServiceDispatcherUnitTest extends AbstractUnitTest {
  static ServiceCallback<AccountingService> FAIL_CALLBACK = new ServiceCallback<AccountingService>() {
    public void callback(AccountingService service) {
      throw new RuntimeException("Fail. Expect a rollback if method annotate with the Transactionnal");
    }
  };

  @Autowired
  RestService restService;

  @Autowired
  AccountingService service;

  @Before
  public void setup() throws Exception {
    AccountingScenario scenario = AccountingScenario.loadTest() ;
    service.createScenario(scenario);
   // System.out.println(JSONSerializer.INSTANCE.toString(scenario));
  }

  @After
  public void tearDown() throws Exception{
    service.deleteAll();
  }
  Date getDate(int year, int month, int day) {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.YEAR, year);
    cal.set(Calendar.MONTH, month);
    cal.set(Calendar.DAY_OF_MONTH, day);
    return cal.getTime();
  }
  @Test
  public void testRestService() throws Exception {
    FilterQuery query = new FilterQuery();
    query.add("startDate", FilterQuery.Operator.GT, getDate(2013, 6, 1));
    query.add("startDate", FilterQuery.Operator.LT, getDate(2013, 6, 6));
    Request req = new Request("accounting", "AccountingService", "query");
    req.addParam("query", query);
    req.setLogEnable(false);
    Response res = restService.dispatch(req);
    System.out.println(JSONSerializer.INSTANCE.toString(res));
//    assertNotNull(res.getData());
//    InvoiceDetail retInvoice = res.getDataAs(InvoiceDetail.class);
//    assertNotNull(retInvoice);
//    assertEquals(invoiceDetail, retInvoice);
  }
}
