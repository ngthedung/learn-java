package com.hkt.module.promotion.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hkt.module.core.entity.AbstractPersistable;

@Entity
@Table
public class Weekly extends AbstractPersistable<Long> {
  private static final long serialVersionUID = 1L;

  private long              idTimeOption;
  private int               step;
  /**
   * Danh sach cac ngay ap dung trong tuan VD: 0: chu nhat 1: thu 2 2: thu 3
   */
  private String            daysOfWeek;

  public Weekly() {
  }

  public long getIdTimeOption() {
    return idTimeOption;
  }

  public void setIdTimeOption(long idTimeOption) {
    this.idTimeOption = idTimeOption;
  }

  public int getStep() {
    return step;
  }

  public void setStep(int step) {
    this.step = step;
  }

  public List<Integer> dayOfWeeks() {
    String[] days = daysOfWeek.substring(1, daysOfWeek.length() - 1).split(",");
    List<Integer> list = new ArrayList<Integer>();
    for (int i = 0; i < days.length; i++) {
      if (!list.contains(i))
        list.add(Integer.parseInt(days[i].trim()));
    }
    return list;
  }

  public void add(Integer in) {
    List<Integer> list = new ArrayList<Integer>();
    if (daysOfWeek != null) {
      String[] days = daysOfWeek.substring(1, daysOfWeek.length() - 1).split(",");
      for (int i = 0; i < days.length; i++) {
        if (!list.contains(i))
          list.add(Integer.parseInt(days[i]));
      }
    }

    list.add(in);
    daysOfWeek = list.toString();
  }

  public String getDaysOfWeek() {
    return daysOfWeek;
  }

  public void setDaysOfWeek(String daysOfWeek) {
    this.daysOfWeek = daysOfWeek;
  }

}
