package com.hkt.client.swingexp.app.khuyenmai.list;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.ITableModel;
import com.hkt.client.swingexp.app.core.MyDate;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.module.promotion.entity.CouponUsed;

@SuppressWarnings("serial")
public class TableModelCouponUsed extends DefaultTableModel implements ITableModel{
	private String[] header = {"STT", "Mã phiếu", "Tên phiếu", "Ngày sử dụng", "Giá trị", "Đơn vị"};
	private int size;
	private HashMap<String, Integer> numberSize = new HashMap<String, Integer>();
	
	public TableModelCouponUsed(List<CouponUsed> listCouponUsed){
		dataVector = convertToVector(listCouponUsed.toArray());
		numberSize.put(header[0], 50);
		numberSize.put(header[3], 120);
		numberSize.put(header[5], 80);
	}
	
	public void setData(List<CouponUsed> listCouponUsed, int size){
		this.size = size;
	  dataVector = convertToVector(listCouponUsed.toArray());
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
	    return false;
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
	        return ((CouponUsed) dataVector.get(row)).getCouponCode();
	      } catch (Exception e) {
	        Vector rowVector = (Vector) dataVector.elementAt(row);
	        return rowVector.elementAt(column);
	      }
	    case 2:
	      try {
	        return ((CouponUsed) dataVector.get(row));
	      } catch (Exception e) {
	        Vector rowVector = (Vector) dataVector.elementAt(row);
	        return rowVector.elementAt(column);
	      }

	    case 3:
	      try {
	    	  Date date = ((CouponUsed) dataVector.get(row)).getUsedDate();
	    	  if(date!=null){
	    		  return new MyDate(date);
	    	  }else{
	    		  return new MyDate(null);
	    	  }
		      	
	      } catch (Exception e) {
	        Vector rowVector = (Vector) dataVector.elementAt(row);
	        return rowVector.elementAt(column);
	      }

	    case 4:
	      try {
	      	return ((CouponUsed) dataVector.get(row)).getTotal();
	      } catch (Exception e) {
	        Vector rowVector = (Vector) dataVector.elementAt(row);
	        return rowVector.elementAt(column);
	      }

	    case 5:
	      try {
	      	return ((CouponUsed) dataVector.get(row)).getUnit();
	      } catch (Exception e) {
	        Vector rowVector = (Vector) dataVector.elementAt(row);
	        return rowVector.elementAt(column);
	      }
	      
	    default: return "";
	    }
	  }
	  
	  @Override
	  public Class<?> getColumnClass(int columnIndex) {
	    switch (columnIndex) {
	    case 0:
		      return Integer.class;
	    case 2:
		      return CouponUsed.class;
	    case 3:
	      return MyDate.class;
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
		return "DS sử dụng phiếu";
	}
	
}
