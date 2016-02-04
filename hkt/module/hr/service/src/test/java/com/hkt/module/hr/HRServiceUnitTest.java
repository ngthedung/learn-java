package com.hkt.module.hr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hkt.module.account.AccountService;
import com.hkt.module.account.util.AccountScenario;
import com.hkt.module.cms.CMSService;
import com.hkt.module.core.ServiceCallback;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.hr.entity.WorkPosition;
import com.hkt.module.hr.entity.WorkPosition.SalaryType;
import com.hkt.module.hr.entity.WorkPosition.Status;
import com.hkt.module.hr.util.HRScenario;
import com.hkt.util.json.JSONSerializer;

public class HRServiceUnitTest extends AbstractUnitTest {
  static ServiceCallback<HRService> FAIL_CALLBACK = new ServiceCallback<HRService>() {
    public void callback(HRService service) {
      throw new RuntimeException("Fail. Expect a rollback if method annotate with the Transactionnal");
    }
  };
  @Autowired
  private HRService service;

  @Autowired
  private CMSService             cmsService;

  private HRScenario scenario;
  
  @Autowired
  private AccountService  accountService ;

  @Before
  public void setup() throws Exception {
    accountService.createScenario(AccountScenario.loadTest()) ;
    scenario = HRScenario.loadTest();
    service.createScenario(scenario);
  }

  @After
  public void cleandb() throws Exception {
    service.deleteAll();
    accountService.deleteAll();
    cmsService.deleteAll();
  }

  @Test
  public void testSerialization() throws Exception {
    System.out.println(JSONSerializer.INSTANCE.toString(scenario));
    assertTrue(scenario.getEmployees().size() > 0);
  }

//  @Test
//  public void testEmployeeCrud() {
//    List<Employee> employees = service.searchEmployee(null).getData();
//    assertNotNull(employees);
//    try {
//      for (Employee employee : employees) {
//        service.deleteEmployeeCallBack(employee, FAIL_CALLBACK);
//      }
//    } catch (Throwable t) {
//      System.out.println("Fail callback exception: " + t.getMessage());
//    }
//    Employee emp = employees.get(0);
//    assertEquals(1, service.findWorkPositionByEmployeeId(emp.getId()).size());
//  }

  @Test
  public void testWorkPosition() {
    Employee empLong = service.getByOrgLoginIdAndLoginId("long", "hkt");
    
    WorkPosition position = new WorkPosition();
    position.setLoginId(empLong.getLoginId());
    position.setPositionTitle("Manager");
    position.setGroup("hkt/employees/it");
    position.setFromDate(new Date());
    position.setToDate(null);
    position.setStatus(Status.Planed);
    position.setSalaryType(SalaryType.Monthly);
    position.setSalary(10000000);

    position = service.saveWorkPosition(empLong, position);
    assertNotNull(position);
    assertEquals(2, service.findWorkPositionByEmployeeId(empLong.getId()).size());
    
    service.deleteWorkPosition(position);
    
    assertEquals(1, service.findWorkPositionByEmployeeId(empLong.getId()).size());
    assertNull(service.getWorkPositionById(position.getId()));
  }
}