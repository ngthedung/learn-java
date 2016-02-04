package com.hkt.client.swingexp.app.khuvucbanhang.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.ITableModel;
import com.hkt.client.swingexp.app.core.MyDate;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.Table;

public class TableModelLocalTable extends DefaultTableModel implements ITableModel{
	private String[] header = {"STT", "Mã bàn", "Tên bàn", "Nhóm quản lý", "Khu vực", "Miêu tả", "Xóa"};
	private List<Location> lstAllLocation;
	private int size;
	private HashMap<String, Integer> numberSize = new HashMap<String, Integer>();
	
	public TableModelLocalTable(List<Table> localTable){
		dataVector = convertToVector(localTable.toArray());
		lstAllLocation = new ArrayList<Location>();
		numberSize.put(header[0], 50);
		numberSize.put(header[6], 50);
	}
	
	public void setData(List<Table> localTable, int size){
		this.size = size;
		dataVector = convertToVector(localTable.toArray());
		fireTableDataChanged();
	}
	
	@Override
	public int getColumnCount(){
		return header == null ? 0 : header.length;
	}
	
	@Override
	public String getColumnName(int column){
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

	  @SuppressWarnings("rawtypes")
	@Override
	  public Object getValueAt(int row, int column) {
	    switch (column) {
	    case 0:
			try {
				return row + 1+size;
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
	    case 1:
	      try {
		        return ((Table) dataVector.get(row)).getCode();
		      } catch (Exception e) {
		        Vector rowVector = (Vector) dataVector.elementAt(row);
		        return rowVector.elementAt(column);
		      }
	   
	    case 2:
	    	try {
		        return ((Table) dataVector.get(row));
		      } catch (Exception e) {
		        Vector rowVector = (Vector) dataVector.elementAt(row);
		        return rowVector.elementAt(column);
		      }
	      
	    case 3:
	      try {
	        return ((Table) dataVector.get(row)).getResponsibleGroup();
	      } catch (Exception e) {
	        Vector rowVector = (Vector) dataVector.elementAt(row);
	        return rowVector.elementAt(column);
	      }

	    case 4:
	      try {
	    	  
	    	  String nameLocation="";
	    	  String codeLocation = ((Table) dataVector.get(row)).getLocationCode();
	    	  lstAllLocation = RestaurantModelManager.getInstance().getLocations();
	    	  for(int i =0;i< lstAllLocation.size();i++ ){
	    		  if(lstAllLocation.get(i).getCode().toString().equals((codeLocation).toString())){
	    			  nameLocation = lstAllLocation.get(i).getName();
	    		  }  
	    	  }
	    	  
	      	return nameLocation;
	      } catch (Exception e) {
	        Vector rowVector = (Vector) dataVector.elementAt(row);
	        return rowVector.elementAt(column);
	      }

	    case 5:
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
	    case 0:
		      return Integer.class;
	    case 2:
		      return Table.class;
	    case 6:
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
		return "DS bàn quầy";
	}
}
