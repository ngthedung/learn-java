package com.hkt.client.swingexp.app.khachhang.list;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.ITableMain;
import com.hkt.client.swingexp.app.core.TableObservable;
import com.hkt.module.partner.customer.entity.Customer;

public class TableListModel<E> extends TableObservable{
	
	private List<E> configs;
	private TableModel<E> mTbCustomer;
	public TableListModel(List<E> configs) {
		this.configs = configs;
		mTbCustomer = new TableModel<E>(configs);
		setModel(mTbCustomer);
		
	}

	public void setData(List<E> configs) {
		this.configs = configs;
		mTbCustomer.setData(configs, 0);
		
	}

}
