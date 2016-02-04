package com.hkt.client.swingexp.app.hethong.list;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class TableModelDelete<E> extends DefaultTableModel {

	private String[] header = { "STT", "Tên", "Xóa" };

	public String[] getHeader() {
		return header;
	}

	private List<Boolean> list;

	public void removeAllData() {
		dataVector.removeAllElements();
		list = new ArrayList<Boolean>();
		fireTableDataChanged();
	}

	public void addAllData(List<E> customers) {
		dataVector = convertToVector(customers.toArray());
		list = new ArrayList<Boolean>();
		for (int i = 0; i < customers.size(); i++) {
			list.add(false);
		}
		fireTableDataChanged();
	}

	public TableModelDelete(List<E> customers) {
		dataVector = convertToVector(customers.toArray());
		list = new ArrayList<Boolean>();
		for (int i = 0; i < customers.size(); i++) {
			list.add(false);
		}
	}

	@Override
	public int getColumnCount() {
		return header.length;
	}

	@Override
	public String getColumnName(int column) {
		return header[column];
	}

	public List<E> getItemsSelect() {
		List<E> listE = new ArrayList<E>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).booleanValue() == true) {
				listE.add((E) dataVector.get(i));
			}
		}
		return listE;
	}

	public void deleteData() {
		for (int i = 0; i < list.size();) {
			if (list.get(i).booleanValue() == true) {
				list.remove(i);
				dataVector.removeElementAt(i);
			} else {
				i++;
			}
		}
		fireTableDataChanged();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		if (column == 2) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		if (column == 2) {
			list.set(row, Boolean.valueOf(aValue.toString()));
		} else {
			super.setValueAt(aValue, row, column);
		}

	}

	@Override
	public Object getValueAt(int row, int column) {
		switch (column) {
		case 0:
			return row + 1;
		case 1:
			try {
				return dataVector.get(row);
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 2:
			try {
				return list.get(row);
			} catch (Exception e) {
				return false;
			}
		default:
			return "";
		}
	}

	public void addRow(E elementAt) {
		dataVector.addElement(elementAt);
		list.add(false);
		fireTableDataChanged();
	}
}
