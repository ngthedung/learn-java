package com.hkt.module.accounting;

import com.hkt.module.accounting.entity.InvoiceDetail;

public interface InvoiceCalculator {
  public InvoiceDetail calculate(InvoiceDetail invoiceDetail) ;
}
