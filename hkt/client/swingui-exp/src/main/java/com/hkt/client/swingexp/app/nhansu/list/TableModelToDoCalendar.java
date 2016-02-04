package com.hkt.client.swingexp.app.nhansu.list;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.ITableModel;
import com.hkt.client.swingexp.app.core.MyDate;

import com.hkt.module.hr.entity.Appointment;

@SuppressWarnings("serial")
public class TableModelToDoCalendar extends DefaultTableModel implements ITableModel{
	private String[]	header	= { "STT", "Công việc", "Nội dung", "Ngày tạo", "Trạng thái", "Mô tả" };
	private int				size;
	private HashMap<String, Integer> numberSize = new HashMap<String, Integer>();

	public TableModelToDoCalendar(List<Appointment> listData) {
		dataVector = convertToVector(listData.toArray());
		
		numberSize.put(header[0], 50);
		numberSize.put(header[3], 120);
		numberSize.put(header[4], 120);
	}

	public void setData(List<Appointment> listData, int size) {
		this.size = size;
		dataVector = convertToVector(listData.toArray());
		fireTableDataChanged();
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
		if (column < header.length) {
			return false;
		} else {
			return true;
		}
	}
  
  @Override
	public void setValueAt(Object aValue, int row, int column) {
	  if(column==header.length){
	    ((Appointment) dataVector.get(row)).setRecycleBin(Boolean.parseBoolean(aValue.toString()));
	  }
	}

	@Override
	public Object getValueAt(int row, int column) {
		switch (column) {
			case 0:
				try {
					return row + 1 + size;
				} catch (Exception e) {
					return "0";
				}
			case 1:
				try {
					return ((Appointment) dataVector.get(row));
				} catch (Exception e) {
					return null;
				}

			case 2:
				try {
					return ((Appointment) dataVector.get(row)).getContent();
				} catch (Exception e) {
					return "";
				}

			case 3:
				try {
					Date date = ((Appointment) dataVector.get(row)).getDateStart();
					return new MyDate(date);
				} catch (Exception e) {
					return new MyDate(null);
				}

			case 4:
				try {
					Appointment.Status s = ((Appointment) dataVector.get(row)).getStatus();
					if (s == Appointment.Status.UNACCOMPLISHED)
						return "Chưa làm";
					if (s == Appointment.Status.ONGOING)
						return "Đang làm";
					if (s == Appointment.Status.COMPLETE)
						return "Hoàn thành";
				} catch (Exception e) {
					return "";
				}
			case 5:
				try {
					return ((Appointment) dataVector.get(row)).getDescription();
				} catch (Exception e) {
					return "";
				}
			default:
				return ((Appointment) dataVector.get(row)).isRecycleBin();
		}
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return Integer.class;
		case 1:
			return Appointment.class;
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
		return "Công việc hàng ngày";
	}
}
