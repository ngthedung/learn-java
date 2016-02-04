package com.hkt.client.swingexp.app.banhang.list;

import java.util.HashMap;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.ITableModel;
import com.hkt.client.swingexp.app.core.MyDate;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.model.GenericOptionModelManager;

@SuppressWarnings("serial")
public class TableModelInvoiceTransaction extends DefaultTableModel implements ITableModel {
	private int size;
	private HashMap<String, Integer> numberSize = new HashMap<String, Integer>();
	private String[] header = { "Stt", "Mã hóa đơn", "Tên hóa đơn", "Loại", "Kiểu","Ngày", "Người thực hiện", "Mã dự án", "Dự án", "Phòng ban", "Tổng tiền hóa đơn", "Tiền thanh toán", "Tiền còn lại" };
	private HashMap<String, String> list4 = new HashMap<String, String>();
	
	public TableModelInvoiceTransaction() {
		numberSize.put(header[0], 50);
		numberSize.put(header[3], 60);
		numberSize.put(header[5], 100);
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
		if (column < header.length - 1) {
			return false;
		} else {
			return true;
		}
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
		case 4:
			try {
				if (list4.get(super.getValueAt(row, 1).toString()) == null) {
					if (!super.getValueAt(row, 1).toString().trim().isEmpty()) {
						String str1 = GenericOptionModelManager.getInstance().getOptionGroup("accounting", "AccountingService", "type_invoice").getOption(
								super.getValueAt(row, column).toString()).getLabel();
						list4.put(super.getValueAt(row, 1).toString(), str1);
					} else {
						list4.put(super.getValueAt(row, 1).toString(), "");
					}

				}

			} catch (Exception e) {
				list4.put(super.getValueAt(row, 1).toString(), "");
			}
			return list4.get(super.getValueAt(row, 1).toString());
		
		case 10:
			return new MyDouble(super.getValueAt(row, column).toString());
		case 11:
			return new MyDouble(super.getValueAt(row, column).toString());
		case 12:
			return new MyDouble(super.getValueAt(row, column).toString());
			
		default:
				return super.getValueAt(row, column);
			}
	}

	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return Integer.class;
		case 5:
			return MyDate.class;
		case 10:
			return MyDouble.class;
		case 11:
			return MyDouble.class;
		case 12:
			return MyDouble.class;
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
		return "Danh sách biên lai";
	}

}
