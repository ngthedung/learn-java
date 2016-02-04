package com.hkt.client.swingexp.app.khachhang.list;

import java.util.ArrayList;
import java.util.List;

import com.hkt.client.swingexp.app.core.TableObservable;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.module.partner.customer.entity.Customer;

public class TableListModelCustomer extends TableObservable{
	 private List<Customer> cusConfigs;
	  private TableModelCustomers   mdTbCustomer;
	  public TableListModelCustomer(List<Customer> customers) {
		  this.cusConfigs = customers;
	    try {
	      load();
	    } catch (Exception e) {
	      e.printStackTrace();
	      cusConfigs = new ArrayList<Customer>();
	    }
	    mdTbCustomer = new TableModelCustomers(cusConfigs);
	    setModel(mdTbCustomer);

	  }
	  public void load() throws Exception {
		  cusConfigs = CustomerModelManager.getInstance().getCustomers(false);
	  }
	  public List<Customer> getCustomers() {
			return cusConfigs;
		}

		public void setCustomers(List<Customer> cusConfigs) {
			this.cusConfigs = cusConfigs;
			mdTbCustomer.setData(cusConfigs, 0);
		}
		
	public Customer getSelectBean(){
		int row = getSelectedRow();
		return cusConfigs.get(row);
	}
}
