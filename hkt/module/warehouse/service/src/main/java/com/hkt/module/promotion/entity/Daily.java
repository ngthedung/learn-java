package com.hkt.module.promotion.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.hkt.module.core.entity.AbstractPersistable;

@Entity
@Table
public class Daily extends AbstractPersistable<Long> {
  private static final long serialVersionUID = 1L;
  private long              idTimeOption;
  private boolean           everyWeekDay;
  private int               step;

  public Daily() {
  }


  public long getIdTimeOption() {
    return idTimeOption;
  }

  public void setIdTimeOption(long idTimeOption) {
    this.idTimeOption = idTimeOption;
  }

  public boolean isEveryWeekDay() {
    return everyWeekDay;
  }

  public void setEveryWeekDay(boolean everyWeekDay) {
    this.everyWeekDay = everyWeekDay;
  }

  public int getStep() {
    return step;
  }

  public void setStep(int step) {
    this.step = step;
  }

}
