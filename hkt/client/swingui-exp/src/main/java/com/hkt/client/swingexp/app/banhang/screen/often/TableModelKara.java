package com.hkt.client.swingexp.app.banhang.screen.often;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.module.product.entity.ProductPrice;

public class TableModelKara extends DefaultTableModel {

	private String[] header = { "Loại", "Khu vực", "Phòng", "Giá", "Giờ bắt đầu", "Giờ kết thúc" };
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat df1 = new SimpleDateFormat("HH:mm");

	public TableModelKara(List<ProductPrice> listProductPrice) {
		dataVector = convertToVector(listProductPrice.toArray());
	}

	public void setData(List<ProductPrice> listProductPrice) {
		dataVector = convertToVector(listProductPrice.toArray());
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
		return false;
	}

	@Override
	public Object getValueAt(int row, int column) {
		switch (column) {
		case 0:
			try {
				return ((ProductPrice) dataVector.get(row));
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 1:
			try {
				return RestaurantModelManager.getInstance().getLocationByCode(
				    ((ProductPrice) dataVector.get(row)).getOrganizationLoginId());
			} catch (Exception e) {
				return "";
			}

		case 2:
			try {
				return RestaurantModelManager.getInstance().getTableByCode(
				    ((ProductPrice) dataVector.get(row)).getDescription());
			} catch (Exception e) {
				return "";
			}

		case 3:
			try {
				return new MyDouble(((ProductPrice) dataVector.get(row)).getPrice()).toString();
			} catch (Exception e) {
				return "";
			}

		case 4:
			try {
				return ((ProductPrice) dataVector.get(row)).getCreatedBy();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 5:
			try {
				return ((ProductPrice) dataVector.get(row)).getModifiedBy();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		default:
			return "";
		}
	}

}
