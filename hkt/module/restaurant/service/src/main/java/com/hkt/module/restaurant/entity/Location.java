package com.hkt.module.restaurant.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.hkt.module.core.entity.AbstractPersistable;

@Entity
@javax.persistence.Table(name = "restaurant_location", uniqueConstraints=@UniqueConstraint(columnNames={"code"}))
public class Location extends AbstractPersistable<Long> {
  private static final long serialVersionUID = 1L;

  static public enum Status { Active, InActive };

  private String organizationLoginId;
  private String name;
  private int priority;
  private String description;
  private Status status = Status.Active;
  
  @Embedded
  @OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "locationId", nullable = false, updatable = false)
  @OrderColumn
  private List<LocationAttribute>  locationAttributes;

  public List<LocationAttribute> getLocationAttributes() {
    return locationAttributes;
  }

  public void setLocationAttributes(List<LocationAttribute> locationAttributes) {
    this.locationAttributes = locationAttributes;
  }
  
  public void add(LocationAttribute locationAttribute) {
    if (locationAttributes == null || locationAttributes.isEmpty()) {
      locationAttributes = new ArrayList<LocationAttribute>();
    }
    locationAttributes.add(locationAttribute);
  }

  public String getOrganizationLoginId() { return organizationLoginId; }

  public void setOrganizationLoginId(String organizationLoginId) { this.organizationLoginId = organizationLoginId; }
  
  public String getName() { return name; }

  public void setName(String name) { this.name = name; }

  public String getDescription() { return description; }

  public void setDescription(String description) { this.description = description; }

  public Status getStatus() { return status; }

  public void setStatus(Status status) { this.status = status; }

	public int getPriority() { return priority; }

  public void setPriority(int priority) { this.priority = priority; }

  @Override
	public String toString() {	return name; }

}
