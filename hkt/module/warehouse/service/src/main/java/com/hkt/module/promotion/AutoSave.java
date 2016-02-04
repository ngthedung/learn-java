package com.hkt.module.promotion;

import java.util.Date;

public class AutoSave {
  private Date   dateStart;
  private Date   dateEnd;
  private int    stepHour     = 0;
  private int    quantitySave = 0;
  private String location     = "";
  private long   idTimeOption = 0;
  private int    hourStart    = 0;
  private int    minuteStart  = 0;

  public AutoSave() {
  }

  public Date getDateStart() {
    return dateStart;
  }

  public void setDateStart(Date dateStart) {
    this.dateStart = dateStart;
  }

  public Date getDateEnd() {
    return dateEnd;
  }

  public void setDateEnd(Date dateEnd) {
    this.dateEnd = dateEnd;
  }

  public int getStepHour() {
    return stepHour;
  }

  public void setStepHour(int stepHour) {
    this.stepHour = stepHour;
  }

  public int getQuantitySave() {
    return quantitySave;
  }

  public void setQuantitySave(int quantitySave) {
    this.quantitySave = quantitySave;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public long getIdTimeOption() {
    return idTimeOption;
  }

  public void setIdTimeOption(long idTimeOption) {
    this.idTimeOption = idTimeOption;
  }

  public int getHourStart() {
    return hourStart;
  }

  public void setHourStart(int hourStart) {
    this.hourStart = hourStart;
  }

  public int getMinuteStart() {
    return minuteStart;
  }

  public void setMinuteStart(int minuteStart) {
    this.minuteStart = minuteStart;
  }

}
