package com.hkt.module.hr.entity;

import java.util.List;
import com.hkt.module.account.entity.AccountDetail;

public class EmployeeDetail extends AccountDetail {
  private Employee employee;
  private List<WorkPosition> positions;

  public EmployeeDetail() {
  }

  public EmployeeDetail(AccountDetail accDetail, Employee emp, List<WorkPosition> salaries) {
    this.employee = emp;
    this.positions = salaries;
    setAccount(accDetail.getAccount());
    setMemberships(accDetail.getMemberships());
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public List<WorkPosition> getPositions() {
    return positions;
  }

  public void setPositions(List<WorkPosition> positions) {
    this.positions = positions;
  }
}
