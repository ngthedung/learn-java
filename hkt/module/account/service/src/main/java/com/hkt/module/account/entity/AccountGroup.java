package com.hkt.module.account.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.hkt.module.core.entity.AbstractPersistable;

@Entity
@Table
public class AccountGroup extends AbstractPersistable<Long> {
  private static final long serialVersionUID = 1L;

  @NotNull
  private String name;
  
  @NotNull
  private String path;
  
  private String parentPath;
  
  private String label ;
  
  public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	private String owner ;
  private String description ;
  private int priority;
  
  public AccountGroup() { }

  public AccountGroup(String name) { 
    this.name = name ;
    this.path = name ;
  }
  
  public AccountGroup(AccountGroup parent, String name) { 
    this.name = name ;
    setParent(parent) ;
  }
  
  public AccountGroup(String parentPath, String name) { 
    this.name = name ;
    if(parentPath == null) {
      path = name ;
    } else {
      path = parentPath + "/" + name ;
      this.parentPath = parentPath ;
    }
  }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  
  
  public String getPath() { return path; }
  public void setPath(String path) { this.path = path; }
  
  public String getParentPath() { return parentPath; }
  public void setParentPath(String parentPath) { this.parentPath = parentPath; }

  public void setParent(AccountGroup parent) {
    if(parent == null) {
      path = name ;
      parentPath = null ;
    } else {
      path = parent.getPath() + "/" + name ;
      parentPath = parent.getPath() ;
    }
  }
  
  public String getLabel() { 
    if(label == null) return name ;
    return this.label ; 
  }
  public void   setLabel(String label) { this.label = label ; }
  
  public String getOwner() { return this.owner ; }
  public void   setOwner(String owner) { this.owner = owner ; }
  
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }

  @Override
  public String toString() {if(label == null) return name ;
  return this.label ;  }
}