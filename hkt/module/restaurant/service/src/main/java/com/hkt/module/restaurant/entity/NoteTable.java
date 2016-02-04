package com.hkt.module.restaurant.entity;

import java.io.Serializable;
import java.util.Date;

public class NoteTable implements Serializable {
  private static final long serialVersionUID = 1L;

  private String name;
  private String quantity;
  private String description;
  
  public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description ; }
  
  @Override
  public String toString() {
    return name;
  }
}
