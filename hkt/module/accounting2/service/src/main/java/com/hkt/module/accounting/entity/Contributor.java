package com.hkt.module.accounting.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.hkt.module.core.entity.AbstractPersistable;

@Table(indexes = { 
    @Index(columnList = "role"), 
    @Index(columnList = "identifierId") })
@Entity
public class Contributor extends AbstractPersistable<Long> {
  private static final long serialVersionUID = 1L;
  
  static public enum Type { User, Group }  
  static public String thuNgan = "ThuNgan";
  static public String nhanVienPV = "NhanVienPV";

  @NotNull
  @Enumerated(EnumType.STRING)
  private Type              type = Type.User;
  private String 						identifierValue;
  private String            identifierId ;
  @NotNull
  private String            role;
  @NotNull
  private int               percent = 100;
  
  public Type getType() { return this.type ; }
  public void setType(Type type) { this.type = type ; }
  
  public String getIdentifierId() { return identifierId; }
  public void   setIdentifierId(String id) {
    this.identifierId = id;
  }

  public String getRole() { return role; }
  public void setRole(String role) {
    this.role = role;
  }
  public int getPercent() { return percent; }
  public void setPercent(int percent) { this.percent = percent; }
  
  public String getIdentifierValue() {
		return identifierValue;
	}
	public void setIdentifierValue(String identifierValue) {
		this.identifierValue = identifierValue;
	}
	@Override
  public String toString() {
	return identifierId;
}
  
}