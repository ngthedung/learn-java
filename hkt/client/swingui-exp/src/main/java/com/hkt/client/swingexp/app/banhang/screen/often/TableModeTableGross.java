package com.hkt.client.swingexp.app.banhang.screen.often;

import java.util.HashMap;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import antlr.collections.impl.Vector;

import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.Table;

@SuppressWarnings("serial")
public class TableModeTableGross extends DefaultTableModel {
	private String[] header = { "STT", "Tên bàn", "Khu vực", "Trạng thái" };
	private HashMap<String, String> list = new HashMap<String, String>();

	public TableModeTableGross(List<Table> tables) {
		dataVector = convertToVector(tables.toArray());
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
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
	public void fireTableRowsUpdated(int firstRow, int lastRow) {
		super.fireTableRowsUpdated(firstRow, lastRow);
		try {
			String locationCode = ((Table) dataVector.get(firstRow)).getLocationCode();
			String str = RestaurantModelManager.getInstance().getLocationByCode(locationCode).getName();
			list.put(locationCode, str);
		} catch (Exception e) {
			list.put(((Table) dataVector.get(firstRow)).getLocationCode(), "");
		}
	}

	@Override
	public Object getValueAt(int row, int column) {
		switch (column) {
		case 0:
			return row + 1;
		case 1:
			try {
				return ((Table) dataVector.get(row));
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 2:
			try {
				try {
					if(list.get(((Table) dataVector.get(row)).getLocationCode()) ==null)
					{
					String locationCode = ((Table) dataVector.get(row)).getLocationCode();
					String str = RestaurantModelManager.getInstance().getLocationByCode(locationCode).getName();
					list.put(locationCode, str);
					}
				} catch (Exception e) {
					list.put(((Table) dataVector.get(row)).getLocationCode(), "");
				}
				return list.get(((Table) dataVector.get(row)).getLocationCode());
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 3:
			try {
				String status = ((Table) dataVector.get(row)).getStatus().toString();
				if (status.equals("TableFree")){
					return "Bàn trống";
				} else if (status.equals("TableGross")){
					return "Bàn gộp";
				} else if (status.equals("TableSet")){
					return "Bàn đặt trước";
				}  else if (status.equals("TableBusy")){
					return "Bàn bận";
				} else if (status.equals("TableServe")){
					return "Bàn đã phục vụ";
				} else if (status.equals("TableKitchen")){
					return "Bàn đã in chế biến"; 
				} else if (status.equals("InActivate")){
					return "Bàn chưa mở"; 
				}
//				return ((Table) dataVector.get(row)).getStatus();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		default:
			return null;
		}
	}

}
