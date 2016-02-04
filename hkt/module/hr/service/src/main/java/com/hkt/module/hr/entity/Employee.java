package com.hkt.module.hr.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import com.hkt.module.core.entity.AbstractPersistable;
import com.hkt.module.hr.repository.EmployeeRepositoryListener;

@Table
@Entity
@EntityListeners({ EmployeeRepositoryListener.class })
public class Employee extends AbstractPersistable<Long> {
  private static final long serialVersionUID = 1L;

  private String            loginId;
  private String            organizationLoginId;
  private Date              startDate;
  private Date              birthDay;
  private Date              leaveDate;
  private String            mobile;
  private String            name;
  private String permissionGroup;

  public Employee() {
  }
  
  

  public Date getBirthDay() {
	return birthDay;
}



public void setBirthDay(Date birthDay) {
	this.birthDay = birthDay;
}



public String getPermissionGroup() {
    return permissionGroup;
  }



  public void setPermissionGroup(String permissionGroup) {
    this.permissionGroup = permissionGroup;
  }



  public Employee(Long id) {
    this.setId(id);
  }

  public String getLoginId() {
    return loginId;
  }

  public void setLoginId(String loginId) {
    this.loginId = loginId;
  }

  public String getOrganizationLoginId() {
    return organizationLoginId;
  }

  public void setOrganizationLoginId(String organizationLoginId) {
    this.organizationLoginId = organizationLoginId;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getLeaveDate() {
    return leaveDate;
  }

  public void setLeaveDate(Date leaveDate) {
    this.leaveDate = leaveDate;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  @Override
  public String toString() {
    if (name == null) {
      name = loginId;
    }
    return name;
  }

}
