package com.hkt.module.restaurant.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.hkt.module.core.entity.AbstractPersistable;

@Table(name = "restaurant_projectmember")
@Entity
public class ProjectMember extends AbstractPersistable<Long> {
	private static final long	serialVersionUID	= 1;

	@NotNull
	private String						employeeCode;
	@NotNull
	private String						role;
	@NotNull
	private double						percent;
	private double						directAward;
	private int								priority;
	private String						status;
	private String 						employeePath;

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	public double getDirectAward() {
		return directAward;
	}

	public void setDirectAward(double directAward) {
		this.directAward = directAward;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmployeePath() {
		return employeePath;
	}

	public void setEmployeePath(String employeePath) {
		this.employeePath = employeePath;
	}

	
}
