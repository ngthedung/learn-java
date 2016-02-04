package com.hkt.module.warehouse.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.hkt.module.core.entity.AbstractPersistable;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "warehouseId" }))
public class Warehouse extends AbstractPersistable<Long> {
  private static final long serialVersionUID = 1L;
  
  @NotNull
  @Column(unique = true)
  private String warehouseId;
  private String name;
  private String ownerId;
  private String ownerGroup;
  private String address;
  private String description;
  
  public Warehouse() { }
  
  public Warehouse(Long id) { this.setId(id); }
  
  public String getWarehouseId() { return warehouseId; }
  
  public void setWarehouseId(String warehouseId) { this.warehouseId = warehouseId; }
  
  public String getName() { return name; }
  
  public void setName(String name) { this.name = name; }
  
  public String getOwnerId() { return ownerId; }
  
  public void setOwnerId(String ownerId) { this.ownerId = ownerId; }
  
  public String getOwnerGroup() { return ownerGroup; }
  
  public void setOwnerGroup(String ownerGroup) { this.ownerGroup = ownerGroup; }
  
  public String getAddress() { return address; }
  
  public void setAddress(String address) { this.address = address; }
  
  public String getDescription() { return description; }

  public void setDescription(String description) { this.description = description; }

  @Override
  public String toString() {
    return name;
  }

  
}
