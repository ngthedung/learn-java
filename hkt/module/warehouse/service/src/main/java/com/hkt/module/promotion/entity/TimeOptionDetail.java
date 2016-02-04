package com.hkt.module.promotion.entity;

public class TimeOptionDetail {

  private TimeOption timeOption;
  private Daily      daily;
  private Weekly     weekly;
  private Monthly    monthly;
  private Yearly     yearly;

  public TimeOptionDetail() {

  }

  public TimeOption getTimeOption() {
    return timeOption;
  }

  public void setTimeOption(TimeOption timeOption) {
    this.timeOption = timeOption;
  }

  public Daily getDaily() {
    return daily;
  }

  public void setDaily(Daily daily) {
    this.daily = daily;
  }

  public Weekly getWeekly() {
    return weekly;
  }

  public void setWeekly(Weekly weekly) {
    this.weekly = weekly;
  }

  public Monthly getMonthly() {
    return monthly;
  }

  public void setMonthly(Monthly monthly) {
    this.monthly = monthly;
  }

  public Yearly getYearly() {
    return yearly;
  }

  public void setYearly(Yearly yearly) {
    this.yearly = yearly;
  }

}
