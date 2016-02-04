package com.hkt.module.restaurant.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.hkt.module.core.entity.AbstractPersistable;

@Entity
@Table(name = "restaurant_recipeIngredient")
public class RecipeIngredient extends AbstractPersistable<Long> {
  private static final long serialVersionUID = 1L;

  @NotNull
  private String productCode;
  private double quantity;
  private double percent;
  private String unit;

  public String getProductCode() { return productCode; }

  public void setProductCode(String productCode) { this.productCode = productCode; }

  public double getQuantity() { return quantity; }

  public void setQuantity(double quantity) { this.quantity = quantity; }

  public double getPercent() { return percent; }

  public void setPercent(double percent) { this.percent = percent; }

  public String getUnit() { return unit; }

  public void setUnit(String unit) { this.unit = unit; }

}
