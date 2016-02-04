package com.hkt.client.rest.service;

import java.util.List;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hkt.client.rest.RESTClient;
import com.hkt.module.core.rest.Request;
import com.hkt.module.core.rest.Response;
import com.hkt.module.partner.customer.entity.Credit;
import com.hkt.module.partner.customer.entity.CreditTransaction;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.customer.entity.Point;
import com.hkt.module.partner.customer.entity.PointConversionRule;
import com.hkt.module.partner.customer.entity.PointTransaction;

@Component
public class CustomerServiceClient {
  @Autowired
  private RESTClient client;

  public Customer saveCustomer(Customer customer) throws Exception {
    Request req = create("saveCustomer");
    req.addParam("customer", customer);
    Response res = client.POST(req);
    return res.getDataAs(Customer.class);
  }
  
  public List<Customer> findCustomerByCode(String code) throws Exception{
 	 Request req = create("findCustomerByCode");
    req.addParam("code", code);
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<Customer>>() {
    });
 }

  public boolean deleteCustomer(Customer customer) throws Exception {
    Request req = create("deleteCustomer");
    req.addParam("customer", customer);
    client.POST(req);
    return true;
  }

  public List<Customer> searchCustomer(boolean interactive) throws Exception {
    Request req = create("searchCustomer");
    req.addParam("interactive", interactive);
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<Customer>>() {
    });
  }

  public Customer getCustomerByCode(String code) throws Exception {
    Request req = create("getCustomerByCode");
    req.addParam("code", code);
    Response res = client.POST(req);
    return res.getDataAs(Customer.class);
  }

  public Customer findCustomerByLoginIdAndOrganId(String loginId, String organizationLoginId) throws Exception {
    Request req = create("findCustomerByLoginIdAndOrganId");
    req.addParam("customerLoginId", loginId);
    req.addParam("organizationLoginId", organizationLoginId);
    Response res = client.POST(req);
    return res.getDataAs(Customer.class);
  }

  public Customer getBydLoginId(String loginId, String organizationLoginId) throws Exception {
    Request req = create("findCustomerByLoginIdAndOrganId");
    req.addParam("loginId", loginId);
    req.addParam("organizationLoginId", organizationLoginId);
    Response res = client.POST(req);
    return res.getDataAs(Customer.class);
  }

  public Credit getCreditByCustomerId(long customerId) throws Exception {
    Request req = create("getCreditByCustomerId");
    req.addParam("customerId", customerId);
    Response res = client.POST(req);
    return res.getDataAs(Credit.class);
  }

  public Credit saveCredit(Credit credit) throws Exception {
    Request req = create("saveCredit");
    req.addParam("credit", credit);
    Response res = client.POST(req);
    return res.getDataAs(Credit.class);
  }

  public List<Credit> getAllCredits() throws Exception {
    Request req = create("getAllCredits");
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<Credit>>() {
    });
  }

  public CreditTransaction saveCreditTransaction(CreditTransaction creditTransaction) throws Exception {
    Request req = create("saveCreditTransaction");
    req.addParam("creditTransaction", creditTransaction);
    Response res = client.POST(req);
    return res.getDataAs(CreditTransaction.class);
  }

  public boolean deleteCreditTransaction(CreditTransaction creditTransaction) throws Exception {
    Request req = create("deleteCreditTransaction");
    req.addParam("creditTransaction", creditTransaction);
    client.POST(req);
    return true;
  }

  public boolean deleteCreditTransactionByInvoiceId(long invoiceId) throws Exception {
    Request req = create("deleteCreditTransactionByInvoiceId");
    req.addParam("invoiceId", invoiceId);
    client.POST(req);
    return true;
  }

  public List<CreditTransaction> findCreditTransactionByCreditId(long creditId) throws Exception {
    Request req = create("findCreditTransactionByCreditId");
    req.addParam("creditId", creditId);
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<CreditTransaction>>() {
    });
  }

  public Point getPointByCustomerId(long customerId) throws Exception {
    Request req = create("getPointByCustomerId");
    req.addParam("customerId", customerId);
    Response res = client.POST(req);
    return res.getDataAs(Point.class);
  }

  public Credit getCreditById(long id) throws Exception {
    Request req = create("getCreditById");
    req.addParam("id", id);
    Response res = client.POST(req);
    return res.getDataAs(Credit.class);
  }

  public Point getPointById(long id) throws Exception {
    Request req = create("getPointById");
    req.addParam("id", id);
    Response res = client.POST(req);
    return res.getDataAs(Point.class);
  }

  public List<PointConversionRule> getPointConversionRules() throws Exception {
    Request req = create("getPointConversionRules");
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<PointConversionRule>>() {
    });
  }

  public PointConversionRule savePointConversionRatio(PointConversionRule pointConversionRule) throws Exception {
    Request req = create("savePointConversionRatio");
    req.addParam("pointConversionRule", pointConversionRule);
    Response res = client.POST(req);
    return res.getDataAs(PointConversionRule.class);
  }

  public PointConversionRule getConversionRuleById(long id) throws Exception {
    Request req = create("getConversionRuleById");
    req.addParam("id", id);
    Response res = client.POST(req);
    return res.getDataAs(PointConversionRule.class);
  }

  public boolean deletePointConversionRule(PointConversionRule pointConversionRule) throws Exception {
    Request req = create("deletePointConversionRule");
    req.addParam("pointConversionRule", pointConversionRule);
    client.POST(req);
    return true;
  }

  public Point savePoint(Point point) throws Exception {
    Request req = create("savePoint");
    req.addParam("point", point);
    Response res = client.POST(req);
    return res.getDataAs(Point.class);
  }

  public List<Point> getAllPoints() throws Exception {
    Request req = create("getAllPoints");
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<Point>>() {
    });
  }

  public PointTransaction savePointTransaction(PointTransaction pointTransaction) throws Exception {
    Request req = create("savePointTransaction");
    req.addParam("pointTransaction", pointTransaction);
    Response res = client.POST(req);
    return res.getDataAs(PointTransaction.class);
  }

  public PointTransaction getByInvoiceId(long invoiceId) throws Exception {
    Request req = create("getByInvoiceId");
    req.addParam("invoiceId", invoiceId);
    Response res = client.POST(req);
    return res.getDataAs(PointTransaction.class);
  }

  public CreditTransaction getCreditTransactionByInvoiceId(long invoiceId) throws Exception {
    Request req = create("getCreditTransactionByInvoiceId");
    req.addParam("invoiceId", invoiceId);
    Response res = client.POST(req);
    return res.getDataAs(CreditTransaction.class);
  }

  public boolean deletePointTransaction(PointTransaction pointTransaction) throws Exception {
    Request req = create("deletePointTransaction");
    req.addParam("pointTransaction", pointTransaction);
    client.POST(req);
    return true;
  }
  
  public boolean deletePointTransactionByInvoiceId(long invoiceId) throws Exception {
    Request req = create("deletePointTransactionByInvoiceId");
    req.addParam("invoiceId", invoiceId);
    client.POST(req);
    return true;
  }

  public List<PointTransaction> findPointTransactionByPointId(long pointId) throws Exception {
    Request req = create("findPointTransactionByPointId");
    req.addParam("pointId", pointId);
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<PointTransaction>>() {
    });
  }

  public PointConversionRule getConversionRulePointToMoney(double pointToTrigger) throws Exception {
    Request req = create("getConversionRulePointToMoney");
    req.addParam("pointToTrigger", pointToTrigger);
    Response res = client.POST(req);
    return res.getDataAs(PointConversionRule.class);
  }

  public PointConversionRule getConversionRuleMoneyToPoint() throws Exception {
    Request req = create("getConversionRuleMoneyToPoint");
    Response res = client.POST(req);
    return res.getDataAs(PointConversionRule.class);
  }

  public void deleteTest(String test) throws Exception {
    Request req = create("deleteTest");
    req.addParam("test", test);
    client.POST(req);
  }

  Request create(String method) {
    return new Request("partner", "CustomerService", method);
  }
}