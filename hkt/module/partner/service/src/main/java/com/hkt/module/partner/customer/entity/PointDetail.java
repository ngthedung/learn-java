package com.hkt.module.partner.customer.entity;

import java.io.Serializable;
import java.util.List;

public class PointDetail implements Serializable{
  private Point point;
  private List<PointTransaction> pointTransactions;
  
  public PointDetail() { }
  
  public PointDetail(Point point, List<PointTransaction> pointTransactions) {
    this.point = point;
    this.pointTransactions = pointTransactions;
  }

  public Point getPoint() {
    return point;
  }

  public void setPoint(Point point) {
    this.point = point;
  }

  public List<PointTransaction> getPointTransactions() {
    return pointTransactions;
  }

  public void setPointTransactions(List<PointTransaction> pointTransactions) {
    this.pointTransactions = pointTransactions;
  }
}
