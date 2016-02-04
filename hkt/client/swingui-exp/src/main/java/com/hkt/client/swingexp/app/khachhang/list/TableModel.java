package com.hkt.client.swingexp.app.khachhang.list;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.ITableModel;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.supplier.entity.Supplier;

public class TableModel<E> extends DefaultTableModel implements ITableModel{
	 static String[] header = { "STT", "Tên đối tác", "Là DN/cá nhân" };

	  
	  

	private int size = 20;
	private HashMap<String, Integer> numberSize = new HashMap<String, Integer>();
	public TableModel(List<E> list){
		dataVector = convertToVector(list.toArray());
		numberSize.put(header[0], 50);
		fireTableDataChanged();
	}
	
	public void setData(List<E> list, int size){
		this.size = size;
		dataVector = convertToVector(list.toArray());
		fireTableDataChanged();
	}
	@Override
	public int getColumnCount() {
		return header == null ? 0 : header.length;
	}

	@Override
	public String getColumnName(int column) {
		return header[column];
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		if (column < header.length-1) {
			return false;
		} else {
			return true;
		}
	}
 
// @Override
//	public void setValueAt(Object aValue, int row, int column) {
//	  if(column==header.length-1){
//	    ((Supplier) dataVector.get(row)).setRecycleBin(Boolean.parseBoolean(aValue.toString()));
//	  }
//	}
 
	@SuppressWarnings("rawtypes")
	@Override
	public Object getValueAt(int row, int column){
		switch (column) {
		case 0:
			try {
				return row + 1 + size;
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 1:
			try {
//				System.out.println(((E) dataVector.get(row)));
				return ((E) dataVector.get(row));
				
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 2:
			try {
				 if (((E) dataVector.get(row)) instanceof Employee)
					    return ((Employee) dataVector.get(row)).getOrganizationLoginId();
					  if (((E) dataVector.get(row)) instanceof Customer)
		          return ((Customer) dataVector.get(row)).getOrganizationLoginId();
					  else
					    return ((Supplier) dataVector.get(row)).getOrganizationLoginId();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		default:
			return "";
		}
	}
 
	@Override
	public HashMap<String, Integer> getColumnSize() {
	
		return numberSize;
	}
	@Override
	public String getNameTableModel() {
		// TODO Auto-generated method stub
		return "Test Table";
	}
}
