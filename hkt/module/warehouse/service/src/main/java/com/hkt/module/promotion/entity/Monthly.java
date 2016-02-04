package com.hkt.module.promotion.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.hkt.module.core.entity.AbstractPersistable;

@Entity
@Table
public class Monthly extends AbstractPersistable<Long> {
  private static final long serialVersionUID = 1L;
  
  private long              idTimeOption;
  private boolean           noLevel;              // CÃ³ pháº£i tÃ¹y chá»�n lÃ  khÃ´ng phÃ¢n cáº¥p
  private int               dayRepeat;            // ngay lap lai la ngay nao vd: ngay mung 10
  /**
   * level :
   *  1: dau tien
   *  2: thu 2
   *  3: thu 3
   *  4: thu 4
   *  5: cuoi cung
   */
  private int level;
  /**
   * thu trong tuan 
   * VD: thu 2, thu 3...
   * di kem voi level
   */
  private int dayOfWeek;
  /**
   * step lap lai sau bao nhieu thang
   */
  private int step;

  public Monthly() {
  }

  public long getIdTimeOption() {
    return idTimeOption;
  }

  public void setIdTimeOption(long idTimeOption) {
    this.idTimeOption = idTimeOption;
  }

  public boolean isNoLevel() {
    return noLevel;
  }

  public void setNoLevel(boolean noLevel) {
    this.noLevel = noLevel;
  }

  public int getDayRepeat() {
    return dayRepeat;
  }

  public void setDayRepeat(int dayRepeat) {
    this.dayRepeat = dayRepeat;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public int getDayOfWeek() {
    return dayOfWeek;
  }

  public void setDayOfWeek(int dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
  }

  public int getStep() {
    return step;
  }

  public void setStep(int step) {
    this.step = step;
  }

}
