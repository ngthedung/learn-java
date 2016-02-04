package com.hkt.client.swingexp.app.banhang.screen.often;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.ITableModel;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.Table;

@SuppressWarnings("serial")
public class TableModelChossetable extends DefaultTableModel implements ITableModel{
	private String[] header = { "STT", "Mã", "Tên Bàn/Quầy", "Tên khu vực", "Ghi chú", "Xóa" };
	private int size;
	private HashMap<String, Integer> numberSize = new HashMap<String, Integer>();
	private HashMap<String, String> list = new HashMap<String, String>();
	public TableModelChossetable(List<Table> listtable) {
		dataVector = convertToVector(listtable.toArray());
		numberSize.put(header[0], 50);
		numberSize.put(header[5], 50);
	}

	public void setData(List<Table> table, int size) {
		this.size = size;
		dataVector = convertToVector(table.toArray());
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
	    ((Table) dataVector.get(row)).setRecycleBin(Boolean.parseBoolean(aValue.toString()));
	  }
	}

	@Override
public void fireTableRowsUpdated(int firstRow, int lastRow) {
	super.fireTableRowsUpdated(firstRow, lastRow);
	try {
		String str = RestaurantModelManager.getInstance().getLocationByCode(((Table) dataVector.get(firstRow)).getLocationCode()).getName();
		list.put(((Table) dataVector.get(firstRow)).getLocationCode(), str);
	} catch (Exception e) {
		list.put(((Table) dataVector.get(firstRow)).getLocationCode(), "");
	}
	
}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getValueAt(int row, int column) {
		switch (column) {
		case 0:
			return row + 1+size;

		case 1:
			try {
				return ((Table) dataVector.get(row)).getCode();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 2:
			try {
				return ((Table) dataVector.get(row)).getName();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 3:
			try {
				try {
					if(list.get(((Table) dataVector.get(row)).getLocationCode()) ==null)
					{
						String str =  RestaurantModelManager.getInstance().getLocationByCode(((Table) dataVector.get(row)).getLocationCode()).getName().toString();
						list.put(((Table) dataVector.get(row)).getLocationCode(), str);
					}
				} catch (Exception e) {
					list.put(((Table) dataVector.get(row)).getLocationCode(), "");
				}
				return list.get(((Table) dataVector.get(row)).getLocationCode());
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 4:
			try {
				return ((Table) dataVector.get(row)).getDescription();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		default:
			return ((Table) dataVector.get(row)).isRecycleBin();
		}
	}
	
	@Override
	  public Class<?> getColumnClass(int columnIndex) {
	    switch (columnIndex) {
	    case 5:
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
		return "Quản lý đặt bàn";
	}
	

}
