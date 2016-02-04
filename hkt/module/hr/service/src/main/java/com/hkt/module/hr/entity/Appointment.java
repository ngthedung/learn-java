package com.hkt.module.hr.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.hkt.module.core.entity.AbstractPersistable;

@Table
@Entity
public class Appointment extends AbstractPersistable<Long> {
	private static final long	serialVersionUID	= 1L;

	static public enum Status {
		UNACCOMPLISHED, ONGOING, COMPLETE
	}

	private String	orgLoginId;
	private String	groupPath;
	private String	employeeLoginId;
	private String	partnerLoginId;
	@NotNull
	private String	name;
	private String	content;
	private Date		dateStart;
	private Status	status;
	private String	description;

	public String getOrgLoginId() {
		return orgLoginId;
	}

	public void setOrgLoginId(String orgLoginId) {
		this.orgLoginId = orgLoginId;
	}

	public String getGroupPath() {
		return groupPath;
	}

	public void setGroupPath(String groupPath) {
		this.groupPath = groupPath;
	}

	public String getEmployeeLoginId() {
		return employeeLoginId;
	}

	public void setEmployeeLoginId(String employeeLoginId) {
		this.employeeLoginId = employeeLoginId;
	}

	public String getPartnerLoginId() {
		return partnerLoginId;
	}

	public void setPartnerLoginId(String partnerLoginId) {
		this.partnerLoginId = partnerLoginId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date date) {
		this.dateStart = date;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return this.name;
	}

}
