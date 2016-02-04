package com.hkt.module.promotion.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table
@Entity
public class Coupon extends Promotion {
	
	public Coupon() {
	}

	@Override
	public String toString() {
		return getName();
	}

}