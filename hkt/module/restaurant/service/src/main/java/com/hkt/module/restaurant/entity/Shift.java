package com.hkt.module.restaurant.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import com.hkt.module.core.entity.AbstractPersistable;

@Entity
@javax.persistence.Table(name = "restaurant_shift", uniqueConstraints = @UniqueConstraint(columnNames = { "code" }))
public class Shift extends AbstractPersistable<Long> {
	private static final long serialVersionUID = 1L;

	private String name;
	private String store;
	private Date hourStart;
	private Date hourEnd;
	private String description;
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getStore(){
		return store;
	}
	
	public void setStore(String store){
		this.store = store;
	}

	public Date getHourStart(){
		return hourStart;
	}
	
	public void setHourStart(Date hourStart){
		this.hourStart = hourStart;
	}
	
	public Date getHourEnd(){
		return hourEnd;
	}
	
	public void setHourEnd(Date hourEnd){
		this.hourEnd = hourEnd;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	
	@Override
	public String toString() {	return name; }
	
	public void set(Shift shift) {
	    super.set(shift) ;
	    setName(shift.getName());;
	    setStore(shift.getStore());
	    setHourStart(shift.getHourStart());
	    setHourEnd(shift.getHourEnd());
	    setDescription(shift.getDescription());
	  }
	
	
	
	
	
	
	
	
	
	
	
}