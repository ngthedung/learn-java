package com.hkt.module.partner.customer.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.hkt.module.core.entity.AbstractPersistable;

@Table
@Entity
public class CreditTransaction extends AbstractPersistable<Long> {
  private static final long serialVersionUID = 1L;
  
  private String loginId;
  private long   creditId;
  private long invoiceId;
  private double amount;
  private String description;
  private Date   dateExecute;
  
  @Override
public String toString() {
	return loginId;
}

public long getInvoiceId() { return invoiceId; }

  public void setInvoiceId(long invoiceId) { this.invoiceId = invoiceId; }

  public String getLoginId() { return loginId; }

  public void setLoginId(String loginId) { this.loginId = loginId; }

  public long getCreditId() { return creditId; }
  
  public void setCreditId(long creditId) { this.creditId = creditId; }
  
  public double getAmount() { return amount; }
  
  public void setAmount(double amount) { this.amount = amount; }
  
  public String getDescription() { return description; }
  
  public void setDescription(String description) { this.description = description; }
  
  public Date getDateExecute() { return dateExecute; }
  
  public void setDateExecute(Date dateExecute) { this.dateExecute = dateExecute; }

}
