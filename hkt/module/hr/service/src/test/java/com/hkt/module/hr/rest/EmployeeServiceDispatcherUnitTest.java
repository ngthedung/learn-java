package com.hkt.module.hr.rest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hkt.module.account.AccountService;
import com.hkt.module.account.util.AccountScenario;
import com.hkt.module.cms.CMSService;
import com.hkt.module.core.ServiceCallback;
import com.hkt.module.core.rest.Request;
import com.hkt.module.core.rest.Response;
import com.hkt.module.core.rest.RestService;
import com.hkt.module.hr.AbstractUnitTest;
import com.hkt.module.hr.HRService;
import com.hkt.module.hr.util.HRScenario;
import com.hkt.util.json.JSONSerializer;

public class EmployeeServiceDispatcherUnitTest extends AbstractUnitTest {

  static ServiceCallback<HRService> FAIL_CALLBACK = new ServiceCallback<HRService>() {
    public void callback(HRService service) {
      throw new RuntimeException("Fail. Expect a rollback if method annotate with the Transactionnal");
    }
  };

  @Autowired
  RestService restService;

  @Autowired
  HRService service;
  
  @Autowired
  private AccountService  accountService ;

  @Autowired
  private CMSService             cmsService;

  @Before
  public void setup() throws Exception {
    accountService.createScenario(AccountScenario.loadTest()) ;
    HRScenario scenario = HRScenario.loadTest();
    service.createScenario(scenario);
  }

  @After
  public void tearDown() throws Exception {
    service.deleteAll();
    accountService.deleteAll();
    cmsService.deleteAll();
  }

  @Test
  public void testRestService() throws Exception {
    Request req = new Request("hr", "HRService", "getByOrgLoginIdAndLoginId");
    req.addParam("loginId", "long");
    req.addParam("orgLoginId", "hkt");
    Response res = restService.dispatch(req);
    System.out.println(JSONSerializer.INSTANCE.toString(res));
  }
}