package com.hkt.client.swingexp.app.khachhang;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.ITableModel;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.module.account.entity.Account;

@SuppressWarnings("serial")
public class TableModelindividual extends DefaultTableModel implements ITableModel{
	private String[] header = { "STT", "Họ Tên", "Ngày sinh", "Email", "Hôn nhân", "SDT", "Địa chỉ", "Xóa" };
	private int size;
	private HashMap<String, Integer> numberSize = new HashMap<String, Integer>();
	public TableModelindividual(List<Account> listAccount) {
		dataVector = convertToVector(listAccount.toArray());
		numberSize.put(header[0], 50);
		numberSize.put(header[2], 150);
		numberSize.put(header[3], 150);
		numberSize.put(header[7], 50);
	}

	public void setData(List<Account> listAccount, int size) {
		this.size = size;
		dataVector = convertToVector(listAccount.toArray());
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
	    ((Account) dataVector.get(row)).setRecycleBin(Boolean.parseBoolean(aValue.toString()));
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
				return ((Account) dataVector.get(row));
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 2:
			try {
				return ((Account) dataVector.get(row)).getProfiles().getBasic().get(BasicInformation.BIRTHDAY).toString();
			} catch (Exception e) {
				return "";
			}

		case 3:
			try {
				return ((Account) dataVector.get(row)).getEmail();
			} catch (Exception e) {
				return "";
			}

		case 4:
			try {
				return ((Account) dataVector.get(row)).getProfiles().getBasic().get(BasicInformation.MARITAL_STATUS).toString();
			} catch (Exception e) {
				return "";
			}

		case 5:
			try {
				return ((Account) dataVector.get(row)).getContacts().get(0).getPhone()[0];
			} catch (Exception e) {
				return "";
			}

		case 6:
			try {
				return ((Account) dataVector.get(row)).getContacts().get(0).getAddressNumber();
			} catch (Exception e) {
				return "";
			}

		default:
			return ((Account) dataVector.get(row)).isRecycleBin();
		}
	}
	
	@Override
	  public Class<?> getColumnClass(int columnIndex) {
	    switch (columnIndex) {
	    case 0:
		      return Integer.class;
	    case 1:
		      return Account.class;
	    case 7:
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
		return "DS cá nhân";
	}

}
