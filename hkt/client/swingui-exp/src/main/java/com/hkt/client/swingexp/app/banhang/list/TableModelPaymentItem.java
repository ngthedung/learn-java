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
public class TableModelPaymentItem extends DefaultTableModel implements ITableModel {
	private DateFormat dfTime = new SimpleDateFormat("HH:mm");
	private String name;
	private int size;
	private HashMap<String, Integer> numberSize = new HashMap<String, Integer>();
	private HashMap<String, String> list4 = new HashMap<String, String>();
	private HashMap<String, String> list5 = new HashMap<String, String>();

	private String[] header = { "Stt", "Mã", "Tên NV", "SL","Đơn giá","Thành tiền","Loại", "Kiểu", "Tình trạng", "Người TH", "Đối tác", "SDT",
	    "Ngày TH", "Giờ TH", "SK", "Mã dự án", "Dự án", "Phòng ban", "NVPV", "Loại hình", "Tiền hàng", "CK", "Tiền thuế",
	    "Phí DV", "Tổng tiền", "Đã trả", "Phải trả", "ĐV", "Chọn" };

	public TableModelPaymentItem() {

		numberSize.put(header[0], 40);
		numberSize.put(header[1], 120);
		numberSize.put(header[6], 80);
		numberSize.put(header[3], 40);
		numberSize.put(header[7], 60);
		numberSize.put(header[12], 95);
		numberSize.put(header[13], 60);
		numberSize.put(header[14], 40);
		numberSize.put(header[27], 40);
		numberSize.put(header[28], 50);
		Profile profile = AccountModelManager.getInstance().getProfileConfigAdmin();
		if (profile.get("ForRent") != null) {
			header[22] = "Tiền Ship";
			header[17] = "Shipper";
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

	@Override
	public Object getValueAt(int row, int column) {

		switch (column) {
		case 6:
			if(super.getValueAt(row, 0).toString().isEmpty()){
				return "";
			}else{
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
			}
			
		case 26:
			if(super.getValueAt(row, 0).toString().isEmpty()){
				return "";
			}else{
				return new MyDouble(super.getValueAt(row, column).toString());
			}
			
		case 20:
			if(super.getValueAt(row, 0).toString().isEmpty()){
				return "";
			}else{
				return new MyDouble(super.getValueAt(row, column).toString());
			}
		case 21:
			if(super.getValueAt(row, 0).toString().isEmpty()){
				return "";
			}else{
				return new MyDouble(super.getValueAt(row, column).toString());
			}
		case 22:
			if(super.getValueAt(row, 0).toString().isEmpty()){
				return "";
			}else{
				return new MyDouble(super.getValueAt(row, column).toString());
			}
		case 23:
			if(super.getValueAt(row, 0).toString().isEmpty()){
				return "";
			}else{
				return new MyDouble(super.getValueAt(row, column).toString());
			}
		case 24:
			if(super.getValueAt(row, 0).toString().isEmpty()){
				return "";
			}else{
				return new MyDouble(super.getValueAt(row, column).toString());
			}
		case 25:
			if(super.getValueAt(row, 0).toString().isEmpty()){
				return "";
			}else{
				return new MyDouble(super.getValueAt(row, column).toString());
			}
		case 27:
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
		case 12:
			return MyDate.class;
		case 13:
			return MyDate.class;
		case 3:
			return MyDouble.class;
		case 4:
			return MyDouble.class;
		case 5:
			return MyDouble.class;
		case 25:
			return MyDouble.class;
		case 26:
			return MyDouble.class;
		case 20:
			return MyDouble.class;
		case 21:
			return MyDouble.class;
		case 22:
			return MyDouble.class;
		case 23:
			return MyDouble.class;
		case 24:
			return MyDouble.class;
		case 28:
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
