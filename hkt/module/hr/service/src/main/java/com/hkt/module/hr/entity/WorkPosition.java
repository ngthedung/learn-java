package com.hkt.module.hr.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.hkt.module.core.entity.AbstractPersistable;
import com.hkt.module.hr.repository.WorkPositionRepositoryListener;

@Table
@Entity
@EntityListeners({WorkPositionRepositoryListener.class})
public class WorkPosition extends AbstractPersistable<Long>{
  private static final long serialVersionUID = 1L;

  static public enum Status {
    Planed, Active, Ended
  };

  static public enum SalaryType {
    Hourly, Daily, Weekly, Monthly, Yearly, NA
  };

  @NotNull
  private Long employeeId;
  @NotNull
  private String loginId;
  private String positionTitle;
  @Column(name = "inGroup")
  private String group;
  private Date fromDate;
  private Date toDate;
  private Status status = Status.Active;
  private SalaryType salaryType;
  private double salary;

  public Long getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(Long employeeId) {
    this.employeeId = employeeId;
  }

  public String getLoginId() {
    return loginId;
  }

  public void setLoginId(String loginId) {
    this.loginId = loginId;
  }

  public String getPositionTitle() {
    return positionTitle;
  }

  public void setPositionTitle(String positionTitle) {
    this.positionTitle = positionTitle;
  }

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public Date getFromDate() {
    return fromDate;
  }

  public void setFromDate(Date fromDate) {
    this.fromDate = fromDate;
  }

  public Date getToDate() {
    return toDate;
  }

  public void setToDate(Date toDate) {
    this.toDate = toDate;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public SalaryType getSalaryType() {
    return salaryType;
  }

  public void setSalaryType(SalaryType salaryType) {
    this.salaryType = salaryType;
  }

  public double getSalary() {
    return salary;
  }

  public void setSalary(double salary) {
    this.salary = salary;
  }

  public String getInvoiceReference() {
    if (getId() == null) {
      return null;
    }
    return getClass().getSimpleName() + ":" + getId();
  }

  public void setInvoiceReference(String ref) {
  }
}