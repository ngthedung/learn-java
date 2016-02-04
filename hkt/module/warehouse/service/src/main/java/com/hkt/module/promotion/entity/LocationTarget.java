package com.hkt.module.promotion.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.hkt.module.core.entity.AbstractPersistable;

@Table
@Entity
public class LocationTarget extends AbstractPersistable<Long> {
  static public enum LocationTargetType { ByArea, ByTable }
  private String location ;
  private LocationTargetType locationTargetType;

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public LocationTargetType getLocationTargetType() {
    return locationTargetType;
  }

  public void setLocationTargetType(LocationTargetType locationTargetType) {
    this.locationTargetType = locationTargetType;
  }

  
}
