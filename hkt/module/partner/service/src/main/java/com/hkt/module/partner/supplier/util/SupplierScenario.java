package com.hkt.module.partner.supplier.util;

import java.util.List;

import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.account.util.AccountScenario;
import com.hkt.module.partner.supplier.entity.PriceHistory;
import com.hkt.module.partner.supplier.entity.SuppliedProduct;
import com.hkt.module.partner.supplier.entity.Supplier;
import com.hkt.util.IOUtil;
import com.hkt.util.json.JSONReader;

public class SupplierScenario {
  
  private List<SupplierDetail> suppliers;
  
  public List<SupplierDetail> getSuppliers() {
    return suppliers;
  }

  public void setSuppliers(List<SupplierDetail> suppliers) {
    this.suppliers = suppliers;
  }

  static public class SupplierDetail {
    private List<AccountMembership> memberships;
    private Supplier supplier;
    private List<SuppliedProductScenario> suppliedProducts;

    public List<AccountMembership> getMemberships() {
      return memberships;
    }

    public void setMemberships(List<AccountMembership> memberships) {
      this.memberships = memberships;
    }

    public Supplier getSupplier() {
      return supplier;
    }

    public void setSupplier(Supplier supplier) {
      this.supplier = supplier;
    }

    public List<SuppliedProductScenario> getSuppliedProducts() {
      return suppliedProducts;
    }

    public void setSuppliedProducts(List<SuppliedProductScenario> suppliedProducts) {
      this.suppliedProducts = suppliedProducts;
    }

    static public class SuppliedProductScenario {
      
      private SuppliedProduct suppliedProduct;
      private List<PriceHistory> priceHistories;
      
      public SuppliedProduct getSuppliedProduct() {
        return suppliedProduct;
      }
      
      public void setSuppliedProduct(SuppliedProduct suppliedProduct) {
        this.suppliedProduct = suppliedProduct;
      }
      
      public List<PriceHistory> getPriceHistories() {
        return priceHistories;
      }
      
      public void setPriceHistories(List<PriceHistory> priceHistories) {
        this.priceHistories = priceHistories;
      }
    }
  }
  
  static public SupplierScenario load(String res) throws Exception {
    JSONReader reader = new JSONReader(IOUtil.loadRes(res)) ;
    SupplierScenario scenario = reader.read(SupplierScenario.class) ;
    return scenario ;
  }
  
  static public SupplierScenario loadTest() throws Exception {
    return load("classpath:scenario/partner-supplier.json") ;
  }
}