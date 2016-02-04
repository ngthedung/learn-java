package com.hkt.module.restaurant.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.hkt.module.core.entity.AbstractPersistable;

@Entity
@javax.persistence.Table(name = "restaurant_project", uniqueConstraints = @UniqueConstraint(columnNames = { "code" }))
public class Project extends AbstractPersistable<Long> {
  private static final long serialVersionUID = 1L;
  private String name;
  private Date toDate;
  private Date formDate;
  private String departmentPart;
  private String parentCode;
  private int priority;
  private String description;
  private String status;
  private String customerCode;
  public static boolean nameProject = true;

  @OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "projectId", nullable = false, updatable = true)
  @OrderColumn
  private List<ProjectMember> projectMembers;

  public Project() {

  }

  public String codeView() {
    if(getCode().indexOf(":")>0){
      return getCode().substring(getCode().indexOf(":")+1);
    }else {
      return getCode();  
    }
  }

  public Date getToDate() {
    return toDate;
  }

  public void setToDate(Date toDate) {
    this.toDate = toDate;
  }

  public Date getFormDate() {
    return formDate;
  }

  public void setFormDate(Date formDate) {
    this.formDate = formDate;
  }

  public String getDepartmentPart() {
    return departmentPart;
  }

  public void setDepartmentPart(String departmentPart) {
    this.departmentPart = departmentPart;
  }

  public String getParentCode() {
    return parentCode;
  }

  public void setParentCode(String parentCode) {
    this.parentCode = parentCode;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  @Override
  public String toString() {
    if (nameProject) {
      return name;
    } else {
      return getCode();
    }
  }

  public String getCustomerCode() {
    return customerCode;
  }

  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  public List<ProjectMember> getProjectMembers() {
    return projectMembers;
  }

  public void setProjectMembers(List<ProjectMember> projectMembers) {
    this.projectMembers = projectMembers;
  }

  public void add(ProjectMember projectMember) {
    if (projectMember == null)
      projectMembers = new ArrayList<ProjectMember>();
    projectMembers.add(projectMember);
  }

}
