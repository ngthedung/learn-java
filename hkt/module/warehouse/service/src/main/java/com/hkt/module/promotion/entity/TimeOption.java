package com.hkt.module.promotion.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.hkt.module.core.entity.AbstractPersistable;

@Entity
@Table
public class TimeOption extends AbstractPersistable<Long> {
  private static final long serialVersionUID = 1L;
  
  private boolean           dailyChoose;
  private boolean           weeklyChoose;
  private boolean           monthlyChoose;
  private boolean           yearlyChoose;
  
  public TimeOption() {
  }


  public boolean isDailyChoose() {
    return dailyChoose;
  }

  public void setDailyChoose(boolean dailyChoose) {
    this.dailyChoose = dailyChoose;
  }

  public boolean isWeeklyChoose() {
    return weeklyChoose;
  }

  public void setWeeklyChoose(boolean weeklyChoose) {
    this.weeklyChoose = weeklyChoose;
  }

  public boolean isMonthlyChoose() {
    return monthlyChoose;
  }

  public void setMonthlyChoose(boolean monthlyChoose) {
    this.monthlyChoose = monthlyChoose;
  }

  public boolean isYearlyChoose() {
    return yearlyChoose;
  }

  public void setYearlyChoose(boolean yearlyChoose) {
    this.yearlyChoose = yearlyChoose;
  }

}
