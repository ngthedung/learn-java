package com.hkt.module.school.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.hkt.module.core.entity.AbstractPersistable;
import com.hkt.module.school.repository.StudentRepositoryListener;

@Table
@Entity
//@EntityListeners({ StudentRepositoryListener.class })
public class Student extends AbstractPersistable<Long> {
  private static final long serialVersionUID = 1L;
  
  @NotNull
  @Column(unique = true)
  private String            loginId;
  private String            firstName;
  private String            lastName;
  
  public Student() {
    
  }
  
  public Student(String loginId, String firstName, String lastName) {
    this.loginId = loginId;
    this.firstName = firstName;
    this.lastName = lastName;
  }
  
  public String getLoginId() {
    return loginId;
  }
  
  public void setLoginId(String loginId) {
    this.loginId = loginId;
  }
  
  public String getFirstName() {
    return firstName;
  }
  
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  
  public String getLastName() {
    return lastName;
  }
  
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  
}
