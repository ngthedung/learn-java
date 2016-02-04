package com.hkt.module.accounting;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hkt.module.accounting.entity.Attribute;
import com.hkt.module.accounting.entity.Contributor;
import com.hkt.module.accounting.entity.Invoice;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.accounting.entity.InvoiceTransaction;
import com.hkt.module.core.search.IndexDocument;
import com.hkt.module.core.search.SearchPlugin;

@Component
public class InvoiceDetailSearchPlugin extends SearchPlugin<InvoiceDetail> {
  @Autowired
  AccountingService          wService;
  
  public String getName() { return InvoiceDetail.class.getSimpleName() ; }
  
  public Class<InvoiceDetail> getType() { return InvoiceDetail.class ; } 
  
  public IndexDocument create(InvoiceDetail Invoice) throws IOException {
    IndexDocument idoc = new IndexDocument() ;
    idoc.addTitle(getTitle(Invoice)) ;
    idoc.addSummary("") ;
    idoc.addText(getText(Invoice)) ;
    idoc.addSource(Invoice) ;
    return idoc ;
  }
  
  private String getTitle(InvoiceDetail invoice) { 
    StringBuilder title = new StringBuilder() ;
    add(title, invoice.getInvoiceCode()) ;
    add(title, invoice.getType()) ;
    return title.toString() ;
  }
  
  private String getText(Invoice invoice) {
    StringBuilder b = new StringBuilder() ;
    add(b, invoice.getType()) ;
    add(b, invoice.getFinalCharge()) ;
    add(b, invoice.getTotal()) ;
    add(b, invoice.getInvoiceCode()) ;
    add(b, invoice.getActivityType()) ;
    add(b, invoice.getStatus()) ;
    add(b, invoice.getCreatedBy()) ;
    add(b, invoice.getDescription()) ;
    InvoiceDetail invoiceDetail = wService.getInvoiceDetail(invoice.getId());
    List<InvoiceItem> items = invoiceDetail.getItems();
    if(items!=null){
      for(InvoiceItem invoiceItem: items) {
        add(b, invoiceItem.getItemName()) ;
        add(b, invoiceItem.getItemCode()) ;
      }
    }
    List<InvoiceTransaction> transactions = invoiceDetail.getTransactions();
    if(transactions!=null){
      for(InvoiceTransaction transaction: transactions) {
        add(b, transaction.getTransactionType()) ;
        add(b, transaction.getTransactionDate()) ;
        add(b, transaction.getBankAccountId()) ;
      }
    }
    List<Attribute> attributes = invoiceDetail.getAttributes();
    if(attributes!=null){
      for(Attribute attribute: attributes) {
        add(b, attribute.getName()) ;
        add(b, attribute.getValue()) ;
      }
    }
    List<Contributor> contributors = invoiceDetail.getContributors();
    if(contributors!=null){
      for(Contributor contributor: contributors) {
        add(b, contributor.getRole()) ;
        add(b, contributor.getIdentifierId()) ;
      }
    }
    return b.toString() ;
  }
  
  private void add(StringBuilder b, Object obj) {
    if(obj == null) return ;
    if(obj instanceof String[]) {
      for(String val : (String[]) obj) {
        b.append(' ').append(val) ;
      }
    } else {
      b.append(' ').append(obj) ;
    }
  }
}