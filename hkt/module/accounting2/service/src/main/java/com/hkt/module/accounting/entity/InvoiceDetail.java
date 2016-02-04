package com.hkt.module.accounting.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import com.hkt.module.accounting.InvoiceCalculator;

@Entity
@Table(indexes = { @Index(columnList = "activityType", name = "activityType_Index"),
    							 @Index(columnList = "startDate", name = "startDate_Index")
//                 @Index(columnList = "type", name = "type_Index"), 
//                 @Index(columnList = "status", name = "status_Index"),
})

public class InvoiceDetail extends Invoice {

  @OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "invoiceId", nullable = false, updatable = true)
  @OrderColumn
  private List<InvoiceItem>        items;

  @OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "invoiceId", nullable = false, updatable = true)
  @OrderColumn
  private List<InvoiceTransaction> transactions;

  @OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "invoiceId", nullable = false, updatable = true)
  @OrderColumn
  private List<Attribute>          attributes;

  @OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "invoiceId", nullable = false, updatable = true)
  @OrderColumn
  private List<Contributor>        contributors;
  
  public InvoiceDetail() {

  }

  public InvoiceDetail(boolean initDefault) {
    if (initDefault) {
      items = new ArrayList<InvoiceItem>();
      attributes = new ArrayList<Attribute>();
      contributors = new ArrayList<Contributor>();
      transactions = new ArrayList<InvoiceTransaction>();
    }
  }

  public List<InvoiceItem> getItems() {
    return items;
  }

  public void setItems(List<InvoiceItem> items) {
    this.items = items;
  }

  public boolean remove(InvoiceItem item) {
    return items.remove(item);
  }

  public void add(InvoiceItem item) {
    if (isNew() && items == null) {
      items = new ArrayList<InvoiceItem>();
    }
    items.add(item);
  }

  public List<InvoiceTransaction> getTransactions() {
    return transactions;
  }

  public void setTransactions(List<InvoiceTransaction> transactions) {
    this.transactions = transactions;
  }

  public void add(InvoiceTransaction transaction) {
    if (transactions == null)
      transactions = new ArrayList<InvoiceTransaction>();
    transactions.add(transaction);
  }

  public void add(Contributor contributor) {
    if (contributors == null)
      contributors = new ArrayList<Contributor>();
    contributors.add(contributor);
  }

  public void add(Attribute attribute) {
    if (attributes == null)
      attributes = new ArrayList<Attribute>();
    attributes.add(attribute);
  }

  public Attribute getAttributeByNameAndValue(String name, String value) {
    if (attributes == null)
      return null;

    for (int i = 0; i < attributes.size(); i++) {
      Attribute attr = attributes.get(i);
      if (name.equals(attr.getName()) && value.equals(attr.getValue()))
        return attr;
    }
    return null;
  }

  public List<Attribute> getAttributeByName(String name) {
    if (attributes == null) {
      return null;
    }
    List<Attribute> attributesResult = new ArrayList<Attribute>();
    for (int i = 0; i < attributes.size(); i++) {
      Attribute attr = attributes.get(i);
      if (name.equals(attr.getName())) {
        attributesResult.add(attr);
      }
    }
    return attributesResult;
  }

  public List<String> getAttributeValue(String name) {
    List<Attribute> attributesByName = getAttributeByName(name);
    List<String> strings = new ArrayList<String>();
    for (int i = 0; i < attributesByName.size(); i++) {
      Attribute attribute = attributesByName.get(i);
      String value = attribute.getValue();
      strings.add(value);
    }
    return strings;
  }

  public List<Attribute> getAttributes() {
    return attributes;
  }

  public void setAttributes(List<Attribute> attributes) {
    this.attributes = attributes;
  }

  public void setAttribute(String name, String value, boolean create) {
    if (attributes == null && create)
      attributes = new ArrayList<Attribute>();
    if (attributes == null)
      return;
    for (int i = 0; i < attributes.size(); i++) {
      Attribute attr = attributes.get(i);
      if (attr.getName().equals(name)) {
        attr.setValue(value);
        return;
      }
    }
    if (create)
      attributes.add(new Attribute(name, value));
  }

  public List<Contributor> getContributors() {
    return contributors;
  }

  public List<Contributor> getContributorByRole(String role) {
    List<Contributor> list = new ArrayList<Contributor>();
    if (contributors == null || contributors.size() == 0)
      return list;
    for (int i = 0; i < contributors.size(); i++) {
      Contributor c = contributors.get(i);
      if (role.equals(c.getRole()))
        list.add(c);
    }
    return list;
  }


public void setContributors(List<Contributor> contributors) {
    this.contributors = contributors;
  }

  public Contributor setContributor(Contributor contributor) {
    if (contributors == null)
      contributors = new ArrayList<Contributor>();
    for (int i = 0; i < contributors.size(); i++) {
      Contributor sel = contributors.get(i);
      if (sel.getIdentifierId().equals(contributor.getIdentifierId()) && sel.getRole().equals(contributor.getRole())) {
        sel.setType(contributor.getType());
        sel.setPercent(contributor.getPercent());
        return sel;
      }
    }
    contributors.add(contributor);
    return contributor;
  }


  public InvoiceDetail calculate(InvoiceCalculator calculator) {
    return calculator.calculate(this);
  }

  public void set(InvoiceDetail detail) {
    super.set(detail);
  }

  @Override
  public String toString() {
    return getInvoiceName();
  }
}