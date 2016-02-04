package com.hkt.module.promotion.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import com.hkt.module.core.entity.AbstractPersistable;

@Entity
@Table(indexes={
  @Index(columnList = "code")
})
public class Menu extends AbstractPersistable<Long> {

  private static final long serialVersionUID = 1L;

  static public enum Type {
    Menu, Voucher
  }

  static public enum Status {
    Fixed, Options
  }

  private String name;
  private double total;
  @Enumerated(EnumType.STRING)
  private Type type;
  @Enumerated(EnumType.STRING)
  private Status status;
  private String currencyUnit;
  private String description;

  @Embedded
  @OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "menuId", nullable = false, updatable = true)
  @OrderColumn
  private List<MenuItem> menuItems;

  public Menu() {

  }

  public Menu(boolean initDefault) {
    menuItems = new ArrayList<MenuItem>();

  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getTotal() {
    return total;
  }

  public void setTotal(double total) {
    this.total = total;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public String getCurrencyUnit() {
    return currencyUnit;
  }

  public void setCurrencyUnit(String currencyUnit) {
    this.currencyUnit = currencyUnit;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<MenuItem> getMenuItems() {
    return menuItems;
  }

  public void setMenuItems(List<MenuItem> menuItems) {
    this.menuItems = menuItems;
  }

	@Override
	public String toString() {
		return name;
	}   
}
