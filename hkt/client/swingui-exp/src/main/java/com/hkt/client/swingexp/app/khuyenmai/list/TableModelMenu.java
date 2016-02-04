package com.hkt.client.swingexp.app.khuyenmai.list;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.ITableModel;
import com.hkt.client.swingexp.app.core.MyDate;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.module.promotion.entity.Menu;

@SuppressWarnings("serial")
public class TableModelMenu extends DefaultTableModel implements ITableModel{
	private String[] header = {"STT", "Mã","Tên","Loại","Tổng tiền","ĐV tiền","Trạng thái","Miêu tả", "Xóa"};
	private int size;
	private HashMap<String, Integer> numberSize = new HashMap<String, Integer>();
	
	public TableModelMenu(List<Menu> listMenu){
		dataVector = convertToVector(listMenu.toArray());
		numberSize.put(header[0], 50);
		numberSize.put(header[3], 80);
		numberSize.put(header[5], 80);
		numberSize.put(header[6], 120);
		numberSize.put(header[8], 50);
	}
	
	public void setData(List<Menu> listMenu, int size){
		this.size = size;
	  dataVector = convertToVector(listMenu.toArray());
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
	    ((Menu) dataVector.get(row)).setRecycleBin(Boolean.parseBoolean(aValue.toString()));
	  }
	}

	  @SuppressWarnings("rawtypes")
	@Override
	  public Object getValueAt(int row, int column) {
	    switch (column) {
	    case 0:
			try {
				return row + 1 +size;
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
	    case 1:
	      try {
	        return ((Menu) dataVector.get(row)).getCode();
	      } catch (Exception e) {
	        Vector rowVector = (Vector) dataVector.elementAt(row);
	        return rowVector.elementAt(column);
	      }
	    case 2:
	      try {
	    	  try {
	    		  return ((Menu) dataVector.get(row));
			} catch (Exception e) {
				return new Menu();
			}
	        
	      } catch (Exception e) {
	        Vector rowVector = (Vector) dataVector.elementAt(row);
	        return rowVector.elementAt(column);
	      }

	    case 3:
	      try {
	        return ((Menu) dataVector.get(row)).getType();
	      } catch (Exception e) {
	        Vector rowVector = (Vector) dataVector.elementAt(row);
	        return rowVector.elementAt(column);
	      }

	    case 4:
	      try {
	      	return new MyDouble(((Menu) dataVector.get(row)).getTotal()).toString();
	      } catch (Exception e) {
	        Vector rowVector = (Vector) dataVector.elementAt(row);
	        return rowVector.elementAt(column);
	      }

	    case 5:
	      try {
	      	return ((Menu) dataVector.get(row)).getCurrencyUnit();
	      } catch (Exception e) {
	        Vector rowVector = (Vector) dataVector.elementAt(row);
	        return rowVector.elementAt(column);
	      }
	    case 6:
	      try {
	    	  String str = ((Menu) dataVector.get(row)).getStatus().toString();
	    	  if (str.equals("Options")){
	    		  return "Tùy chọn";
	    	  } else {
	    		  return "Cố định";
	    	  }

	      } catch (Exception e) {
	        Vector rowVector = (Vector) dataVector.elementAt(row);
	        return rowVector.elementAt(column);
	      }
	    case 7:
	    	try {
	      	return ((Menu) dataVector.get(row)).getDescription();
	      } catch (Exception e) {
	        Vector rowVector = (Vector) dataVector.elementAt(row);
	        return rowVector.elementAt(column);
	      }
	    default: 
	    	try {
	    		return  ((Menu) dataVector.get(row)).isRecycleBin();
			} catch (Exception e) {
				return "";
			}
	    	
	    }
	  }
	  
	  @Override
	  public Class<?> getColumnClass(int columnIndex) {
	    switch (columnIndex) {
	    case 0:
		      return Integer.class;
	    case 2:
		      return Menu.class;
	    case 4:
	      return MyDouble.class;
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
		return "DS Menu";
	}
}
