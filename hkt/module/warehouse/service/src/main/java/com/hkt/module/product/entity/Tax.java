package com.hkt.module.product.entity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.hkt.module.core.entity.AbstractPersistable;

@Entity
@Table(name = "warehouse_tax", indexes={
    @Index(columnList="code")
})
public class Tax extends AbstractPersistable<Long> {
	private static final long serialVersionUID = 1L;

	private String name;
	private int priority;
	private double percent;
	private String description;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return priority;
	}

	public void setIndex(int index) {
		this.priority = index;
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return name;
	}
}
