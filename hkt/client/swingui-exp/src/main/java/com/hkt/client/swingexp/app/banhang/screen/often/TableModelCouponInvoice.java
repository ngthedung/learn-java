package com.hkt.client.swingexp.app.banhang.screen.often;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.module.promotion.entity.Coupon;
import com.hkt.module.promotion.entity.CouponUsed;

public class TableModelCouponInvoice extends DefaultTableModel {

	private String[] header = { "Mã", "Tên", "Giá trị", "Đơn vị" };
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat df1 = new SimpleDateFormat("HH:mm");

	public TableModelCouponInvoice(List<CouponUsed> listCoupon) {
		dataVector = convertToVector(listCoupon.toArray());
	}

	public void setData(List<CouponUsed> listCoupon) {
		dataVector = convertToVector(listCoupon.toArray());
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
				return ((CouponUsed) dataVector.get(row)).getCouponCode();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 1:
			try {
				return ((CouponUsed) dataVector.get(row)).getCouponName();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 2:
			try {
					return new MyDouble(((CouponUsed) dataVector.get(row)).getTotal());
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 3:
			try {
				return ((CouponUsed) dataVector.get(row)).getUnit();

			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		default:
			return "";

		}
	}

}
