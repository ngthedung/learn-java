package com.hkt.client.swingexp.app.khachhang.list;

import java.util.ArrayList;
import java.util.List;

import com.hkt.client.swingexp.app.core.TableObservable;
import com.hkt.client.swingexp.model.SupplierModelManager;
import com.hkt.module.partner.supplier.entity.Supplier;

public class TableListModelSupplier extends TableObservable{
	private List<Supplier> suppliers;
	 private TableModelSuppliers   mdTbSupplier;
	 public TableListModelSupplier(List<Supplier> suppliers) {
		  this.suppliers = suppliers;
	    try {
	      load();
	    } catch (Exception e) {
	      e.printStackTrace();
	      suppliers = new ArrayList<Supplier>();
	    }
	    mdTbSupplier = new TableModelSuppliers(suppliers);
	    setModel(mdTbSupplier);

	  }
	  public void load() throws Exception {
		  suppliers = SupplierModelManager.getInstance().getAllSuppliers();
	  }
	public List<Supplier> getSuppliers() {
		return suppliers;
	}
	public void setSuppliers(List<Supplier> suppliers) {
		this.suppliers = suppliers;
		mdTbSupplier.setData(suppliers, 0);
	}
	  
	public Supplier getSelectedBean(){
		int row = getSelectedRow();
		return suppliers.get(row);
	}
	  
}
