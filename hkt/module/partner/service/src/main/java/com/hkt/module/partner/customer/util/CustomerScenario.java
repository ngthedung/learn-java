package com.hkt.module.partner.customer.util;

import java.util.List;

import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.partner.customer.entity.Credit;
import com.hkt.module.partner.customer.entity.CreditTransaction;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.customer.entity.Point;
import com.hkt.module.partner.customer.entity.PointConversionRule;
import com.hkt.module.partner.customer.entity.PointTransaction;
import com.hkt.util.IOUtil;
import com.hkt.util.json.JSONReader;

public class CustomerScenario {
  
  private PointConversionRule ratio;
  private List<Customers> customers;

  public PointConversionRule getRatio() {
    return ratio;
  }

  public void setRatio(PointConversionRule ratio) {
    this.ratio = ratio;
  }

  public List<Customers> getCustomers() {
    return customers;
  }

  public void setCustomers(List<Customers> customers) {
    this.customers = customers;
  }

  static public class Customers {
    private List<AccountMembership> memberships;
    private Customer Customer;
    private PointScenario points;
    private CreditScenario credits;
    
    public List<AccountMembership> getMemberships() {
      return memberships;
    }

    public void setMemberships(List<AccountMembership> memberships) {
      this.memberships = memberships;
    }

    public Customer getCustomer() {
      return Customer;
    }

    public void setCustomer(Customer Customer) {
      this.Customer = Customer;
    }
    
    public PointScenario getPoints() {
      return points;
    }

    public void setPoints(PointScenario point) {
      this.points = point;
    }
    

    public CreditScenario getCredits() {
      return credits;
    }

    public void setCredits(CreditScenario credits) {
      this.credits = credits;
    }


    static public class PointScenario {
      private Point point;
      private List<PointTransaction> transactions;
      
      public Point getPoint() {
        return point;
      }

      public void setPoint(Point point) {
        this.point = point;
      }

      public List<PointTransaction> getTransactions() {
        return transactions;
      }

      public void setTransactions(List<PointTransaction> PointTransactions) {
        this.transactions = PointTransactions;
      }
    }
    static public class CreditScenario {
      private Credit credit;
      private List<CreditTransaction> transactions;
      
      public Credit getCredit() {
        return credit;
      }
      
      public void setCredit(Credit credit) {
        this.credit = credit;
      }
      
      public List<CreditTransaction> getTransactions() {
        return transactions;
      }
      
      public void setTransactions(List<CreditTransaction> transactions) {
        this.transactions = transactions;
      }
      
    }
  }
  
  static public CustomerScenario load(String res) throws Exception {
    JSONReader reader = new JSONReader(IOUtil.loadRes(res)) ;
    CustomerScenario scenario = reader.read(CustomerScenario.class) ;
    return scenario ;
  }
  
  static public CustomerScenario loadTest() throws Exception {
    return load("classpath:scenario/partner-customer.json") ;
  }
}