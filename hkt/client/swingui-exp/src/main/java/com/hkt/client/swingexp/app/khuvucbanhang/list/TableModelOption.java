package com.hkt.client.swingexp.app.khuvucbanhang.list;

import java.awt.Font;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.hkt.module.config.generic.Option;
import com.hkt.module.product.entity.ProductTag;
import com.hkt.module.restaurant.entity.Table;

public class TableModelOption extends DefaultTableModel{
	private String[] header = {"Mã khu vực", "Tên khu vực", "Độ ưu tiên", "Miêu tả"};
	
	
	public TableModelOption(List<Option> option){
		dataVector = convertToVector(option.toArray());
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

	  @Override
	  public Object getValueAt(int row, int column) {
	    switch (column) {
	    case 0:
	      try {
	        return ((Option) dataVector.get(row)).getCode();
	      } catch (Exception e) {
	        Vector rowVector = (Vector) dataVector.elementAt(row);
	        return rowVector.elementAt(column);
	      }
	    case 1:
	      try {
	        return ((Option) dataVector.get(row));
	      } catch (Exception e) {
	        Vector rowVector = (Vector) dataVector.elementAt(row);
	        return rowVector.elementAt(column);
	      }

	    case 2:
	      try {
	        return ((Option) dataVector.get(row)).getPriority();
	      } catch (Exception e) {
	        Vector rowVector = (Vector) dataVector.elementAt(row);
	        return rowVector.elementAt(column);
	      }

	    case 3:
	      try {
	      	return ((Option) dataVector.get(row)).getDescription();
	      } catch (Exception e) {
	        Vector rowVector = (Vector) dataVector.elementAt(row);
	        return rowVector.elementAt(column);
	      }
	   
	    default: return "";
	    }
	  }
}
