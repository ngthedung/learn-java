package com.hkt.module.school.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.hkt.module.core.entity.AbstractPersistable;

@Table
@Entity
public class Course extends AbstractPersistable<Long> {
  private static final long serialVersionUID = 1L;
  @NotNull

  private String            name;
  
  public Course() {
  }
  
  public Course(String code, String name) {
    this.name = name;
  }

  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
}
