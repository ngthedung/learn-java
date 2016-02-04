package com.hkt.module.promotion.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.hkt.module.core.entity.AbstractPersistable;
import com.hkt.module.product.entity.Product;

@Table(indexes={
    @Index(columnList = "code")
  })
@Entity
public class MenuItemRef extends AbstractPersistable<Long>{
  private static final long serialVersionUID = 1L;
  
  private String name;
  private double quantityMax;
  
  @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH })
  @JoinTable(
    name = "Product_MenuItemRef",
    joinColumns = {
      @JoinColumn(name = "productId", referencedColumnName = "id", nullable = false)
    },
    inverseJoinColumns = {
      @JoinColumn(name = "menuItemRefId", referencedColumnName = "id", nullable = false)
    })
  private List<Product>  products;
  
  private String description;

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

  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
  }

  public double getQuantityMax() {
    return quantityMax;
  }

  public void setQuantityMax(double quantityMax) {
    this.quantityMax = quantityMax;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }  
  
}