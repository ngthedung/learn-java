package com.hkt.client.swingexp.app.khachhang.list;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.ITableModel;
import com.hkt.module.partner.supplier.entity.Supplier;

public class TableModelSuppliers extends DefaultTableModel implements ITableModel{
	 static String[] header = { "STT", "Tên đối tác", "Là DN/cá nhân", "Nhóm hiện tại"};
	 private HashMap<String, Integer> numberSize = new HashMap<String, Integer>();
	 private int size;
	@Override
	public HashMap<String, Integer> getColumnSize() {
		// TODO Auto-generated method stub
		return numberSize;
	}
	public TableModelSuppliers(List<Supplier> list){
		dataVector = convertToVector(list.toArray());
		
		numberSize.put(header[0], 50);
	}
	
	public void setData(List<Supplier> list, int size){
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
  
  @Override
	public void setValueAt(Object aValue, int row, int column) {
	  if(column==header.length-1){
	    ((Supplier) dataVector.get(row)).setRecycleBin(Boolean.parseBoolean(aValue.toString()));
	  }
	}
  
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
				return ((Supplier) dataVector.get(row)).getName();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 2:
			try {
				return ((Supplier) dataVector.get(row)).getType();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 3:
		      try {
		    	  return ((Supplier) dataVector.get(row));
		      } catch (Exception e) {

		        return "";
		      }

		default:
			return ((Supplier) dataVector.get(row)).isRecycleBin();
		}
	}
	@Override
	public String getNameTableModel() {
		// TODO Auto-generated method stub
		return "Table Suppliers";
	}
}
