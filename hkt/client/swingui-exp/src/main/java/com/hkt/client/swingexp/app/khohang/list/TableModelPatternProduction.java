package com.hkt.client.swingexp.app.khohang.list;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.ITableModel;
import com.hkt.client.swingexp.app.core.MyDate;
import com.hkt.module.accounting.entity.InvoiceDetail;

@SuppressWarnings("serial")
public class TableModelPatternProduction extends DefaultTableModel implements ITableModel{
	private int					size;
	private String[]		header	= new String[] { "STT", "Mã phiếu sản xuất","Tên phiếu", "Ngày tạo phiếu" };
	private HashMap<String, Integer> numberSize = new HashMap<String, Integer>();

	public TableModelPatternProduction(List<InvoiceDetail> data) {
		dataVector = convertToVector(data.toArray());
	}

	public void setData(List<InvoiceDetail> data, int size) {
		this.size = size;
		dataVector = convertToVector(data.toArray());
		fireTableDataChanged();
		
		numberSize.put(header[0], 50);
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
		return false;
	}

	@Override
	public Object getValueAt(int row, int column) {
		switch (column) {
			case 0:
				try {
					return row + 1 + size;
				} catch (Exception e) {
					return "";
				}
			case 1:
				try {
					return ((InvoiceDetail) dataVector.get(row)).getInvoiceCode();
				} catch (Exception e) {
					return "";
				}
			case 2:
				try {
					return ((InvoiceDetail) dataVector.get(row));
				} catch (Exception e) {
					return "";
				}
			case 3:
				try {
					Date dateCreated = ((InvoiceDetail) dataVector.get(row)).getStartDate();
					return new MyDate(dateCreated);
				} catch (Exception e) {
					return "";
				}
			default:
				return "";
		}
	}
	
	@Override
	  public Class<?> getColumnClass(int columnIndex) {
	    switch (columnIndex) {
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
		return "Danh sách sản xuất";
	}

}
