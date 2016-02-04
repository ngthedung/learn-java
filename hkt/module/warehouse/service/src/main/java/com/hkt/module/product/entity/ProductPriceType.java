package com.hkt.module.product.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.hkt.module.core.entity.AbstractPersistable;

@Entity
@Table(name = "warehouse_productPriceType")
public class ProductPriceType extends AbstractPersistable<Long> {
	private static final long	serialVersionUID	= 1L;

	private String						name;
	@NotNull
	@Column(unique = true)
	private String						type;
	private String						organizationLoginId;
	private String						description;
	private int								priority;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrganizationLoginId() {
		return organizationLoginId;
	}

	public void setOrganizationLoginId(String organizationLoginId) {
		this.organizationLoginId = organizationLoginId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return name;
	}

}
