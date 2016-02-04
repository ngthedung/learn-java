package com.hkt.module.hr.entity;

import java.util.List;

public class DailyWorkDetail {
  private List<DailyWork> dailyWorks;
  private WorkPosition position;

  public List<DailyWork> getDailyWorks() {
    return dailyWorks;
  }

  public void setDailyWorks(List<DailyWork> dailyWorks) {
    this.dailyWorks = dailyWorks;
  }

  public WorkPosition getPosition() {
    return position;
  }

  public void setPosition(WorkPosition position) {
    this.position = position;
  }

}
