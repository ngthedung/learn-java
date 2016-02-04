package com.hkt.module.promotion.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.hkt.module.core.entity.AbstractPersistable;

@Entity
@Table
public class Yearly extends AbstractPersistable<Long> {
  private static final long serialVersionUID = 1L;

  private long              idTimeOption;
  private boolean           everyDay;
  private int               dayOfMonth;
  private int               month;
  private int               level;
  private int               dayLevel;

  public Yearly() {

  }

  public long getIdTimeOption() {
    return idTimeOption;
  }

  public void setIdTimeOption(long idTimeOption) {
    this.idTimeOption = idTimeOption;
  }

  public boolean isEveryDay() {
    return everyDay;
  }

  public void setEveryDay(boolean everyDay) {
    this.everyDay = everyDay;
  }

  public int getDayOfMonth() {
    return dayOfMonth;
  }

  public void setDayOfMonth(int dayOfMonth) {
    this.dayOfMonth = dayOfMonth;
  }

  public int getMonth() {
    return month;
  }

  public void setMonth(int month) {
    this.month = month;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public int getDayLevel() {
    return dayLevel;
  }

  public void setDayLevel(int dayLevel) {
    this.dayLevel = dayLevel;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

}
