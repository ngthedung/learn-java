package com.hkt.module.warehouse.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.hkt.module.core.entity.AbstractPersistable;


public class ProductCodes {

	private List<ProductCode> list;

	public List<ProductCode> getList() {
		return list;
	}
	
	public void add(ProductCode code){
		if(list==null){
			list = new ArrayList<ProductCode>();
		}
		list.add(code);
	}

	public void setList(List<ProductCode> list) {
		this.list = list;
	}

	public ProductCodes(List<ProductCode> list) {
		this.list = list;
	}
	
	public ProductCodes() {
	}

}
