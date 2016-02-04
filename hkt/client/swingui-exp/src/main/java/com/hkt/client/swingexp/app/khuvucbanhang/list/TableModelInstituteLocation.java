package com.hkt.client.swingexp.app.khuvucbanhang.list;


import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.module.restaurant.entity.Location;

@SuppressWarnings("serial")
public class TableModelInstituteLocation extends DefaultTableModel {
	private String[] header = { "STT", "Mã khu vực", "Tên khu vực" };


	public TableModelInstituteLocation(List<Location> listLocation) {
		dataVector = convertToVector(listLocation.toArray());

	}

	public void setData(List<Location> listLocation, int size) {
		dataVector = convertToVector(listLocation.toArray());
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


	@SuppressWarnings("rawtypes")
	@Override
	public Object getValueAt(int row, int column) {
		switch (column) {
		case 0:
			try {
				return row + 1;
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 1:
			try {
				return ((Location) dataVector.get(row)).getCode();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 2:
			try {
				return ((Location) dataVector.get(row));
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		
		default:
			
			try {
				return ((Location) dataVector.get(row)).isRecycleBin();
			} catch (Exception e) {
				return "";
			}
			
		}
	}

}
