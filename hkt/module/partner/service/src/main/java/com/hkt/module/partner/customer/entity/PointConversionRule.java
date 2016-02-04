package com.hkt.module.partner.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.hkt.module.core.entity.AbstractPersistable;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
public class PointConversionRule extends AbstractPersistable<Long> {
  private static final long serialVersionUID = 1L;
  
  static public enum Status {
    Active, InActive
  }
  
  @NotNull
  @Column(unique = true)
  private String name;
  private String organizationLoginId;
  private Status status;
  private double minPointToTrigger;
  private double total;
  private double pointToCreditRatio;
  private double creditToPointRatio;
  private Date dateFrom;
  private Date dateTo;

  public double numRatioPointToCredit(){
    return creditToPointRatio/pointToCreditRatio;
  }
  
  public double numRatioCreditToPoint(){
    return pointToCreditRatio/total;
  }

  public double getTotal() { return total; }

  public void setTotal(double total) { this.total = total; }

  public String getName() { return name; }
  
  public void setName(String name) { this.name = name; }
  
  public String getOrganizationLoginId() { return organizationLoginId; }
  
  public void setOrganizationLoginId(String organizationLoginId) { this.organizationLoginId = organizationLoginId; }
  
  public Status getStatus() { return status; }
  
  public void setStatus(Status status) { this.status = status; }
  
  public double getMinPointToTrigger() { return minPointToTrigger; }
  
  public void setMinPointToTrigger(double minPointToTrigger) { this.minPointToTrigger = minPointToTrigger; }
  
  public double getPointToCreditRatio() { return pointToCreditRatio; }
  
  public void setPointToCreditRatio(double pointToCreditRatio) { this.pointToCreditRatio = pointToCreditRatio; }
  
  
  public double getCreditToPointRatio() { return creditToPointRatio; }

  public void setCreditToPointRatio(double creditToPointRatio) { this.creditToPointRatio = creditToPointRatio; }

  public Date getDateFrom() { return dateFrom; }

  public void setDateFrom(Date dateFrom) { this.dateFrom = dateFrom; }

  public Date getDateTo() { return dateTo; }

  public void setDateTo(Date dateTo) { this.dateTo = dateTo; }

  @Override
  public String toString() { return name; }
  
}
