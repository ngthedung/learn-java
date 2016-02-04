package com.hkt.module.accounting.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hkt.module.core.entity.AbstractPersistable;

@Entity
@Table
public class ManageCodes extends AbstractPersistable<Long> {
	private static final long serialVersionUID = 1L;

	static public enum ManageType {
		Circle, Increase
	};

	static public enum RotationType {
		ByDay, ByMonth, ByYear
	};

	@Enumerated(EnumType.STRING)
	private ManageType manageType;
	@Enumerated(EnumType.STRING)
	private RotationType rotationType;
	private int priority;


	private String typeCode;
	private String description;
	
	

	public ManageCodes() {
    priority = 1;
    setModifiedTime(new Date());
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

	public ManageType getManageType() {
		return manageType;
	}

	public void setManageType(ManageType manageType) {
		this.manageType = manageType;
	}

	public RotationType getRotationType() {
		return rotationType;
	}

	public void setRotationType(RotationType rotationType) {
		this.rotationType = rotationType;
	}

//	public int getIndex() {
//		return index;
//	}
//
//	public void setIndex(int index) {
//		this.index = index;
//	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		if (getCode() == null) {
			return getCode();
		} else {
			return typeCode;
		}
	}

	public void set(ManageCodes codes) {
		super.set(codes);
		setManageType(manageType);
		setRotationType(rotationType);
//		setIndex(index);
		setTypeCode(typeCode);
		setDescription(description);
	}

}
