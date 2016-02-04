package com.hkt.module.restaurant.rest;

import java.util.List;

import org.codehaus.jackson.type.TypeReference;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hkt.module.account.AccountService;
import com.hkt.module.account.util.AccountScenario;
import com.hkt.module.cms.CMSService;
import com.hkt.module.core.rest.Request;
import com.hkt.module.core.rest.Response;
import com.hkt.module.core.rest.RestService;
import com.hkt.module.restaurant.AbstractUnitTest;
import com.hkt.module.restaurant.RestaurantService;
import com.hkt.module.restaurant.entity.Table;
import com.hkt.module.restaurant.util.RestaurantScenario;
import com.hkt.util.json.JSONSerializer;

public class RestaurantServiceDispatcherUnitTest extends AbstractUnitTest {

  @Autowired
  RestService restService;

  @Autowired
  RestaurantService service;

  @Autowired
  private AccountService accountService;


  private AccountScenario accountScenario;

  
  @Autowired
  private CMSService cmsService;

  @Before
  public void setup() throws Exception {
    accountScenario = AccountScenario.loadTest();
    accountService.createScenario(accountScenario);


    RestaurantScenario scenario = RestaurantScenario.loadTest();
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
    Request req = new Request("restaurant", "RestaurantService", "getTables");
    Response res = restService.dispatch(req);
    List<Table> tables = res.getDataAs(new TypeReference<List<Table>>() {
    });
    
    Request req1 = new Request("restaurant", "RestaurantService", "saveTables");
    req1.addParam("tables", tables);
    restService.dispatch(req);
    System.out.println(JSONSerializer.INSTANCE.toString(res));
  }
}