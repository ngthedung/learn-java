package com.hkt.module.school.rest;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hkt.module.account.AccountService;
import com.hkt.module.account.util.AccountScenario;
import com.hkt.module.core.ServiceCallback;
import com.hkt.module.core.rest.Request;
import com.hkt.module.core.rest.Response;
import com.hkt.module.core.rest.RestService;
import com.hkt.module.school.AbstractUnitTest;
import com.hkt.module.school.SchoolService;
import com.hkt.module.school.entity.Student;
import com.hkt.module.school.util.SchoolScenario;
import com.hkt.util.json.JSONSerializer;

public class SchoolServiceDispatcherUnitTest extends AbstractUnitTest {
  static ServiceCallback<SchoolService> FAIL_CALLBACK = new ServiceCallback<SchoolService>() {
    @Override
    public void callback(SchoolService service) {
      throw new RuntimeException("Fail. Expect a rollback if method annotate with the Transactionnal");
    }
  };

  @Autowired
  SchoolService service;
  
  @Autowired
  AccountService accountService;

  @Autowired
  RestService restService;

  @Before
  public void setup() throws Exception {
    accountService.createScenario(AccountScenario.loadTest());
    SchoolScenario senario = SchoolScenario.loadTest();
    service.createScenario(senario);
  }

  @After
  public void tearDown() throws Exception {
    service.deleteAll();
    accountService.deleteAll();
  }

  @Test
  public void testRestService() throws Exception {
    Request req = new Request("school", "SchoolService", "getStudentByLoginId");
    req.addParam("studentLoginId", "school.manh.student");
    Response res = restService.dispatch(req);
    System.out.println(JSONSerializer.INSTANCE.toString(res));
    Student student = res.getDataAs(Student.class);
    assertNotNull(student);
  }

}
