package com.hkt.client.swingexp.app.khuvucbanhang.list;

import java.util.HashMap;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.ITableModel;
import com.hkt.client.swingexp.app.core.MyDate;
import com.hkt.module.restaurant.entity.Project;

@SuppressWarnings("serial")
public class TableModeProjectTable extends DefaultTableModel implements ITableModel {

	private String[] header = { "STT", "Mã dự án", "Tên dự án", "Dự án cha", "Phòng ban", "Miêu tả", "Ngày bắt đầu",
	    "Ngày kết thúc", "Tình trạng", "Xóa" };
	private int size;
	private HashMap<String, String> list5 = new HashMap<String, String>();
	private HashMap<String, Integer> numberSize = new HashMap<String, Integer>();

	public TableModeProjectTable() {
		numberSize.put(header[0], 50);
		numberSize.put(header[6], 120);
		numberSize.put(header[7], 120);
		numberSize.put(header[8], 120);
		numberSize.put(header[9], 50);

	}
	
	

	public void setSize(int size) {
		this.size = size;
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

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		if (column == header.length - 1) {
			list5.put(super.getValueAt(row, 1).toString() + String.valueOf(row), aValue.toString());
			fireTableRowsUpdated(row, row);
		}
	}

	@Override
	public Object getValueAt(int row, int column) {
		if (column == 0) {
			return row + 1 + size;
		} else {
			if (column == 9) {
				try {
					if (list5.get(super.getValueAt(row, 1).toString() + String.valueOf(row)) == null) {
						if (!super.getValueAt(row, 1).toString().trim().isEmpty()) {
							list5.put(super.getValueAt(row, 1).toString() + String.valueOf(row), super.getValueAt(row, column)
							    .toString());
						} else {
							list5.put(super.getValueAt(row, 1).toString() + String.valueOf(row), "false");
						}

					}

				} catch (Exception e) {
					list5.put(super.getValueAt(row, 1).toString() + String.valueOf(row), "false");
				}
				return Boolean.parseBoolean(list5.get(super.getValueAt(row, 1).toString() + String.valueOf(row)));
			} else {
				return super.getValueAt(row, column);
			}

		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return Integer.class;
		case 2:
			return Project.class;
		case 6:
			return MyDate.class;
		case 7:
			return MyDate.class;
		case 9:
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
		return "DS dự án";
	}

}
