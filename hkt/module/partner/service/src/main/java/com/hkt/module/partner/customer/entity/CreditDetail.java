package com.hkt.module.partner.customer.entity;

import java.io.Serializable;
import java.util.List;

public class CreditDetail implements Serializable {

  private Credit credit;
  private List<CreditTransaction> creditTransactions;
  
  public CreditDetail() { }
  
  public CreditDetail(Credit credit, List<CreditTransaction> creditTransactions) {
   this.credit = credit;
   this.creditTransactions = creditTransactions;
  }

  public Credit getCredit() {
    return credit;
  }

  public void setCredit(Credit credit) {
    this.credit = credit;
  }

  public List<CreditTransaction> getCreditTransactions() {
    return creditTransactions;
  }

  public void setCreditTransactions(List<CreditTransaction> creditTransactions) {
    this.creditTransactions = creditTransactions;
  }
}
