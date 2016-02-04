package com.hkt.module.hr.util;

import java.util.List;

import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.cms.entity.NodeDetail;
import com.hkt.module.hr.entity.DailyWork;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.hr.entity.WorkPosition;
import com.hkt.util.IOUtil;
import com.hkt.util.json.JSONReader;

public class HRScenario {
  private List<EmployeeScenario> employees;

  public List<EmployeeScenario> getEmployees() {
    return employees;
  }

  public void setEmployees(List<EmployeeScenario> employees) {
    this.employees = employees;
  }

  static public class EmployeeScenario {
    private List<AccountMembership>       memberships;
    private Employee                      employee;
    private List<WorkPositionScenario>    positions;
    private NodeDetail                    contract ;

    public List<AccountMembership> getMemberships() {
      return memberships;
    }

    public void setMemberships(List<AccountMembership> memberships) {
      this.memberships = memberships;
    }

    public Employee getEmployee() {
      return employee;
    }

    public void setEmployee(Employee employee) {
      this.employee = employee;
    }

    public List<WorkPositionScenario> getPositions() {
      return positions;
    }

    public void setPositions(List<WorkPositionScenario> positions) {
      this.positions = positions;
    }
    
    public NodeDetail getContract() { return this.contract ; }
    public void       setContract(NodeDetail contract) {
      this.contract = contract ;
    }
  }

  static public class WorkPositionScenario {
    private WorkPosition              position;
    private List<DailyWork>           dailyWorks ;

    public WorkPosition getPosition() {
      return position;
    }

    public void setPosition(WorkPosition salary) {
      this.position = salary;
    }

    public List<DailyWork> getDailyWorks() {
      return dailyWorks;
    }

    public void setDailyWorks(List<DailyWork> dailyWorks) {
      this.dailyWorks = dailyWorks;
    }
  }
  
  static public HRScenario load(String res) throws Exception {
    JSONReader reader = new JSONReader(IOUtil.loadRes(res)) ;
    HRScenario scenario = reader.read(HRScenario.class) ;
    return scenario ;
  }
  
  static public HRScenario loadTest() throws Exception {
    return load("classpath:scenario/hr.json") ;
  }
}