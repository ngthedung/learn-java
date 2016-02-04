package com.hkt.module.accounting.util;

import java.util.List;

import com.hkt.module.accounting.entity.BankAccount;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.util.IOUtil;
import com.hkt.util.json.JSONReader;

public class AccountingScenario {
  private List<BankAccount> bankAccounts;
  private List<InvoiceDetail> invoices;

  public AccountingScenario() {
  }

  public List<BankAccount> getBankAccounts() {
    return bankAccounts;
  }

  public void setBankAccounts(List<BankAccount> bankAccounts) {
    this.bankAccounts = bankAccounts;
  }

  public List<InvoiceDetail> getInvoices() {
    return invoices;
  }

  public void setInvoices(List<InvoiceDetail> invoices) {
    this.invoices = invoices;
  }
  
  static public AccountingScenario load(String res) throws Exception {
    JSONReader reader = new JSONReader(IOUtil.loadRes(res)) ;
    AccountingScenario scenario = reader.read(AccountingScenario.class) ;
    return scenario ;
  }
  
  static public AccountingScenario loadTest() throws Exception {
    return load("classpath:scenario/accounting.json") ;
  }

}
