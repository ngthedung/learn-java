package com.hkt.client.swingexp.app.banhang.screen.often;

import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.module.accounting.entity.Contributor;

@SuppressWarnings("serial")
public class TableModelContributor extends DefaultTableModel{
	
	private String[] header = {"STT", "Tên thành viên", "Chức vụ", "Tỷ lệ đóng góp", "Miêu tả"};

	public TableModelContributor(List<Contributor> listData) {
		dataVector = convertToVector(listData.toArray());
	}

	@Override
	public int getColumnCount() {
		return header.length;
	}

	@Override
	public String getColumnName(int column) {
		return header[column];
	}
	
	 @Override
	  public boolean isCellEditable(int row, int column) {
	    return false;
	  }

	@Override
	public Object getValueAt(int row, int column) {
		switch(column){
			case 0: return row + 1;
			case 1: 
				try {
			        return ((Contributor) dataVector.get(row));
			      } catch (Exception e) {
			        Vector rowVector = (Vector) dataVector.elementAt(row);
			        return rowVector.elementAt(column);
			      }
			case 2: 
				try {
			        return ((Contributor) dataVector.get(row)).getRole();
			      } catch (Exception e) {
			        Vector rowVector = (Vector) dataVector.elementAt(row);
			        return rowVector.elementAt(column);
			      }
			case 3: 
				try {
			        return ((Contributor) dataVector.get(row)).getPercent();
			      } catch (Exception e) {
			        Vector rowVector = (Vector) dataVector.elementAt(row);
			        return rowVector.elementAt(column);
			      }
			case 4:
				try {
			        return ((Contributor) dataVector.get(row)).getType();
			      } catch (Exception e) {
			        Vector rowVector = (Vector) dataVector.elementAt(row);
			        return rowVector.elementAt(column);
			      }
			default: return "";
		}
	}
}
