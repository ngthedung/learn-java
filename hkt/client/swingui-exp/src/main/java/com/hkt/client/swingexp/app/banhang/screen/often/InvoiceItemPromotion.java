package com.hkt.client.swingexp.app.banhang.screen.often;

import java.util.List;

import com.hkt.module.accounting.entity.InvoiceItem;

public class InvoiceItemPromotion {
  private double quantity;
  private String name;
  private List<InvoiceItem> invoiceItems;
  public double getQuantity() {
    return quantity;
  }
  public void setQuantity(double quantity) {
    this.quantity = quantity;
  }
  
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public List<InvoiceItem> getInvoiceItems() {
    return invoiceItems;
  }
  public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
    this.invoiceItems = invoiceItems;
  }
  public InvoiceItemPromotion(double quantity, List<InvoiceItem> invoiceItems) {
    super();
    this.quantity = quantity;
    this.invoiceItems = invoiceItems;
    try {
      this.name = invoiceItems.get(0).getItemName();
    } catch (Exception e) {
      // TODO: handle exception
    }
  }
  
  
}
