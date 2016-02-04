package com.hkt.module.restaurant.entity;

import java.io.Serializable;
import java.util.Date;

public class Reservation implements Serializable {
  private static final long serialVersionUID = 1L;

  private Date   fromTime;
  private Date   toTime;
  private String description;

  public Date getFromTime() { return fromTime; }
  public void setFromTime(Date fromTime) { this.fromTime = fromTime; }

  public Date getToTime() { return toTime; }
  public void setToTime(Date toTime) { this.toTime = toTime; }

  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description ; }
}
