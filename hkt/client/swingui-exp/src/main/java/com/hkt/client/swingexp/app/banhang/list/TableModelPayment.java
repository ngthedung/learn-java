package com.hkt.client.swingexp.app.banhang.list;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.ITableModel;
import com.hkt.client.swingexp.app.core.MyDate;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.GenericOptionModelManager;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.accounting.entity.Invoice;

@SuppressWarnings("serial")
public class TableModelPayment extends DefaultTableModel implements ITableModel {
	private DateFormat dfTime = new SimpleDateFormat("HH:mm");
	private String name;
	private int size;
	private HashMap<String, Integer> numberSize = new HashMap<String, Integer>();
	private HashMap<String, String> list4 = new HashMap<String, String>();
	private HashMap<String, String> list5 = new HashMap<String, String>();

	private String[] header = { "Stt", "Mã", "Tên NV", "Loại", "Kiểu", "Tình trạng", "Người TH", "Đối tác", "SDT",
	    "Ngày TH", "Giờ TH", "SK", "Mã dự án", "Dự án", "Phòng ban", "NVPV", "Loại hình", "Tiền hàng", "CK", "Tiền thuế",
	    "Phí DV", "Tổng tiền", "Đã trả", "Phải trả", "ĐV", "Chọn" };

	public TableModelPayment() {

		numberSize.put(header[0], 40);
		numberSize.put(header[1], 120);
		numberSize.put(header[3], 80);
		numberSize.put(header[4], 60);
		numberSize.put(header[9], 80);
		numberSize.put(header[10], 60);
		numberSize.put(header[11], 40);
		numberSize.put(header[24], 40);
		numberSize.put(header[25], 50);
		Profile profile = AccountModelManager.getInstance().getProfileConfigAdmin();
		if (profile.get("ForRent") != null) {
			header[19] = "Tiền Ship";
			header[14] = "Shipper";
		}

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
		if (column < header.length - 2) {
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
		case 3:
			try {
				if (list4.get(super.getValueAt(row, 1).toString()) == null) {
					if (!super.getValueAt(row, 1).toString().trim().isEmpty()) {
						String str1 = GenericOptionModelManager.getInstance()
						    .getOptionGroup("accounting", "AccountingService", "type_invoice")
						    .getOption(super.getValueAt(row, column).toString()).getLabel();
						list4.put(super.getValueAt(row, 1).toString(), str1);
					} else {
						list4.put(super.getValueAt(row, 1).toString(), "");
					}

				}

			} catch (Exception e) {
				list4.put(super.getValueAt(row, 1).toString(), "");
			}
			return list4.get(super.getValueAt(row, 1).toString());
		case 17:
			return new MyDouble(super.getValueAt(row, column).toString());
		case 18:
			return new MyDouble(super.getValueAt(row, column).toString());
		case 19:
			return new MyDouble(super.getValueAt(row, column).toString());
		case 20:
			return new MyDouble(super.getValueAt(row, column).toString());
		case 21:
			return new MyDouble(super.getValueAt(row, column).toString());
		case 22:
			return new MyDouble(super.getValueAt(row, column).toString());
		case 23:
			return new MyDouble(super.getValueAt(row, column).toString());
		case 25:
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
	

		default:
			return super.getValueAt(row, column);
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return Integer.class;
		case 9:
			return MyDate.class;
		case 10:
			return MyDate.class;
		case 23:
			return MyDouble.class;
		case 17:
			return MyDouble.class;
		case 18:
			return MyDouble.class;
		case 19:
			return MyDouble.class;
		case 20:
			return MyDouble.class;
		case 21:
			return MyDouble.class;
		case 22:
			return MyDouble.class;
		case 25:
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
		return name;
	}

	public void setNameModel(String name) {
		this.name = name;
	}

}
