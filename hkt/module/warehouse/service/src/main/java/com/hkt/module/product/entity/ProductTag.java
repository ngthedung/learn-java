package com.hkt.module.product.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.core.entity.AbstractPersistable;

@Entity
@Table(name = "warehouse_productTag", indexes={
    @Index(columnList = "code"),
    @Index(columnList = "parentTag")
})
public class ProductTag extends AbstractPersistable<Long> {
  private static final long serialVersionUID = 1L;

  public ProductTag() {
  }
  
  private String            label;
  @NotNull
  @Column(unique = true)
  private String            tag;
  private String            parentTag;
  private String            description;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  private int priority;

  public int getPriority() {
    return priority;
  }

  public void setPriority(int index) {
    this.priority = index;
  }

  private String loginId;

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public String getLoginId() {
    return loginId;
  }

  public void setLoginId(String loginId) {
    this.loginId = loginId;
  }

  public String getParentTag() {
    return parentTag;
  }

  public void setParentTag(String parentTag) {
    this.parentTag = parentTag;
  }

  @Override
  public String toString() {
    return label;
  }

  public void setParent(ProductTag parent) {
    if (parent == null) {
      tag = label;
      parentTag = null;
    } else {
      tag = parent.getTag() + ":" + label;
      parentTag = parent.getTag();
    }
  }

  public ProductTag(String label) {
    this.label = label;
    this.tag = label;
  }
}
