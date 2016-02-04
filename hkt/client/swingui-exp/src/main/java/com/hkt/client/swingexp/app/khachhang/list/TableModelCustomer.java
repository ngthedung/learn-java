package com.hkt.client.swingexp.app.khachhang.list;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.ITableModel;
import com.hkt.client.swingexp.app.core.MyDate;
import com.hkt.module.partner.customer.entity.Customer;

@SuppressWarnings("serial")
public class TableModelCustomer extends DefaultTableModel implements ITableModel{

	private String[] header = { "STT", "Mã", "Tên", "Loại", "Số điện thoại", "Ngày sinh (Ngày bắt đầu)","Địa chỉ","Ghi chú", "Xóa" };
	private int size;
	private HashMap<String, Integer> numberSize = new HashMap<String, Integer>();

	public TableModelCustomer(List<Customer> list) {
		dataVector = convertToVector(list.toArray());
		
		numberSize.put(header[0], 50);
		numberSize.put(header[3], 120);
		numberSize.put(header[5], 200);
		numberSize.put(header[8], 50);
	}

	public void setData(List<Customer> list, int size) {
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
	    ((Customer) dataVector.get(row)).setRecycleBin(Boolean.parseBoolean(aValue.toString()));
	  }
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getValueAt(int row, int column) {
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
				return ((Customer) dataVector.get(row)).codeView();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 2:
			try {
				return dataVector.get(row);
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 3:
			try {
				return ((Customer) dataVector.get(row)).getType();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 4:
			try {
				return ((Customer) dataVector.get(row)).getMobile();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 5:
			try {
				return new MyDate(((Customer) dataVector.get(row)).getBirthDay());
			} catch (Exception e) {
				return new MyDate(null);
			}
			
		case 6:
			try {
				return ((Customer) dataVector.get(row)).getAddress();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}	
		case 7:
			try {
				return ((Customer) dataVector.get(row)).getDescription();
			} catch (Exception e) {
				return "";
			}

		default:
			return  ((Customer) dataVector.get(row)).isRecycleBin();
		}
	}
	
	@Override
	  public Class<?> getColumnClass(int columnIndex) {
	    switch (columnIndex) {
	    case 0:
	      return Integer.class;
	    case 2:
	      return Customer.class;
	    case 5:
		      return MyDate.class;
	    case 8:
		      return Boolean.class;
	    default:
	      return String.class;
	    }
	  }

	@Override
	public HashMap<String, Integer> getColumnSize() {
		return numberSize;
	}

	@Override
	public String getNameTableModel() {
		return "DS Khách hàng";
	}

}
