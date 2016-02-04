package com.hkt.module.promotion.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hkt.module.core.entity.AbstractPersistable;

@Table
@Entity
public class ProductTarget extends AbstractPersistable<Long> {
	static public enum ProductTargetType {
		ByProductTag, ByProduct
	}

	@Enumerated(EnumType.STRING)
	private ProductTargetType	productTargetType;
	private String						productIdentifierId;

	public ProductTargetType getProductTargetType() {
		return productTargetType;
	}

	public void setProductTargetType(ProductTargetType productTargetType) {
		this.productTargetType = productTargetType;
	}

	public String getProductIdentifierId() {
		return productIdentifierId;
	}

	public void setProductIdentifierId(String productIdentifierId) {
		this.productIdentifierId = productIdentifierId;
	}
}