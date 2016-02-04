package com.hkt.module.warehouse.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import com.hkt.module.core.entity.AbstractPersistable;

@Entity
@Table(indexes = { @Index(columnList = "productCode", name = "productCode_Index"), 
									 @Index(columnList = "startDate", name = "startDate_Index") 
})

public class WarehouseInventory extends AbstractPersistable<Long> {
	private static final long	serialVersionUID	= 1L;

	private String						productCode;
	private Date							startDate;
	private double						increaseQuantity; // số lượng tăng
	private double						increaseValue;    // giá trị tăng
	
	private double						reductionQuantity;// Số lượng giảm
	private double						reductionValue;   // Giá trị giảm
	
	private double						profitQuantity;   // Lợi nhuận số lượng
	private double						profitValue;      // Lợi nhuận giá trị
	
	private double            reductionVitualQuantity; // Số lượng ảo giảm
  private double            reductionVitualValue;   // Giá trị ảo giảm
  private double            profitVitualQuantity;   // Lợi nhuận ảo số lượng
  private double            profitVitualValue;      // Lợi nhuận ảo giá trị
	@OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "warehouseInventoryId", nullable = false, updatable = false)
  @OrderColumn
  private List<WarehouseInventoryAttribute>  references  = new ArrayList<WarehouseInventoryAttribute>();

	public List<WarehouseInventoryAttribute> getReferences() {
    return references;
  }

  public void setReferences(List<WarehouseInventoryAttribute> references) {
    this.references = references;
  }
  
  public void add(WarehouseInventoryAttribute item) {
    if (isNew() && references == null) {
      references = new ArrayList<WarehouseInventoryAttribute>();
    }
    references.add(item);
  }

  public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public double getIncreaseQuantity() {
		return increaseQuantity;
	}

	public void addIncrease(double quantity, double value) {
		this.increaseQuantity = this.increaseQuantity + quantity;
		this.increaseValue = this.increaseValue + value;
		
//		this.profitQuantity = this.increaseQuantity - this.reductionQuantity;
//		this.profitValue = this.increaseValue - this.reductionValue;
//		
//		this.profitVitualQuantity = this.increaseQuantity - this.reductionVitualQuantity;
//    this.profitVitualValue = this.increaseValue - this.reductionVitualValue;
	}

	public void addReduction(double quantity, double value) {
		this.reductionQuantity = this.reductionQuantity + quantity;
//		this.profitQuantity = this.increaseQuantity - this.reductionQuantity;
		this.reductionValue = this.reductionValue + value;
//		this.profitValue = this.increaseValue - this.reductionValue;
	}
	
	public void addVitualReduction(double quantity, double value) {
    this.reductionVitualQuantity = this.reductionVitualQuantity + quantity;
//    this.profitVitualQuantity = this.increaseQuantity - this.reductionVitualQuantity;
    this.reductionVitualValue = this.reductionVitualValue + value;
//    this.profitVitualValue = this.increaseValue - this.reductionVitualValue;
  }

	public void setIncreaseQuantity(double increaseQuantity) {
		this.increaseQuantity = increaseQuantity;
	}

	public double getIncreaseValue() {
		return increaseValue;
	}

	public void setIncreaseValue(double increaseValue) {
		this.increaseValue = increaseValue;
	}

	public double getReductionQuantity() {
		return reductionQuantity;
	}

	public void setReductionQuantity(double reductionQuantity) {
		this.reductionQuantity = reductionQuantity;
	}

	public double getReductionValue() {
		return reductionValue;
	}

	public void setReductionValue(double reductionValue) {
		this.reductionValue = reductionValue;
	}

	public double getProfitQuantity() {
		return profitQuantity;
	}

	public void setProfitQuantity(double profitQuantity) {
		this.profitQuantity = profitQuantity;
	}

	public double getProfitValue() {
		return profitValue;
	}

	public void setProfitValue(double profitValue) {
		this.profitValue = profitValue;
	}

  public double getReductionVitualQuantity() {
    return reductionVitualQuantity;
  }

  public void setReductionVitualQuantity(double reductionVitualQuantity) {
    this.reductionVitualQuantity = reductionVitualQuantity;
  }

  public double getReductionVitualValue() {
    return reductionVitualValue;
  }

  public void setReductionVitualValue(double reductionVitualValue) {
    this.reductionVitualValue = reductionVitualValue;
  }

  public double getProfitVitualQuantity() {
    return profitVitualQuantity;
  }

  public void setProfitVitualQuantity(double profitVitualQuantity) {
    this.profitVitualQuantity = profitVitualQuantity;
  }

  public double getProfitVitualValue() {
    return profitVitualValue;
  }

  public void setProfitVitualValue(double profitVitualValue) {
    this.profitVitualValue = profitVitualValue;
  }
	
	

}
