package com.hkt.module.promotion.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;

import com.hkt.module.core.entity.AbstractPersistable;

@Table(indexes={
    @Index(columnList = "code")
  })
@Entity
public class MenuItem extends AbstractPersistable<Long> {
  private static final long serialVersionUID = 1L;

  static public enum GroupType {
    Appetizer, MainDishes, Dessert
  }

  static public enum MenuItemType {
    ByMenuItemRef, ByProduct
  }

  private String       name;
  @Enumerated(EnumType.STRING)
  private GroupType    groupType;

  @Enumerated(EnumType.STRING)
  private MenuItemType menuItemType;
  private String       identifierId;
  private String       description;
  private double quantity;

  public double getQuantity() {
    return quantity;
  }

  public void setQuantity(double quantity) {
    this.quantity = quantity;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public GroupType getGroupType() {
    return groupType;
  }

  public void setGroupType(GroupType groupType) {
    this.groupType = groupType;
  }

  public MenuItemType getMenuItemType() {
    return menuItemType;
  }

  public void setMenuItemType(MenuItemType menuItemType) {
    this.menuItemType = menuItemType;
  }

  public String getIdentifierId() {
    return identifierId;
  }

  public void setIdentifierId(String identifierId) {
    this.identifierId = identifierId;
  }

  @Override
  public String toString() {
    return name;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

}