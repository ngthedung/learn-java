package com.hkt.module.warehouse.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.hkt.module.core.entity.AbstractPersistable;


public class ProductCode {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
