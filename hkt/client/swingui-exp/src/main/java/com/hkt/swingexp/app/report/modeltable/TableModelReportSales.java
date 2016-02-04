package com.hkt.swingexp.app.report.modeltable;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.banhang.ReportStatistics;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.swingexp.app.report.entity.ReportSalesEntity;

/**
 * @author: Phan Anh
 * @dateCreate: 11/07/2014
 * @dateEdit: 25/07/2015 - Thêm function cho đồ thị
 */

public class TableModelReportSales extends DefaultTableModel {
	private List<ReportSalesEntity> salesEntitys;
	private String[] header = new String[] { "A", "B", "C", "D" };
	private ReportStatistics reportStatistics;

	public TableModelReportSales(ReportStatistics.TypeReport typeReport, List<ReportSalesEntity> salesEntitys) {
		this.salesEntitys = salesEntitys;

		if (typeReport.equals(ReportStatistics.TypeReport.TKMuaHang))
			header = new String[] { "Danh mục", "Số lượng", "Đơn giá trung bình", "Tổng chi" };
		if (typeReport.equals(ReportStatistics.TypeReport.TKBanHang))
			header = new String[] { "Danh mục", "Số lượng", "Đơn giá trung bình", "Tổng thu" };

		dataVector = convertToVector(salesEntitys.toArray());
	}

	public void setData(List<ReportSalesEntity> eventorys) {
		salesEntitys = eventorys;
		dataVector = convertToVector(salesEntitys.toArray());
		fireTableDataChanged();
	}

	public void addRowChild(List<ReportSalesEntity> childs, int index) {
		salesEntitys.addAll(index, childs);
		dataVector = convertToVector(salesEntitys.toArray());
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
	public void setValueAt(Object aValue, int row, int column) {
		try {
			super.setValueAt(aValue, row, column);
		} catch (Exception e) {
		}
	}

	@Override
	public Object getValueAt(int row, int column) {
		switch (column) {
		case 0:
			try {
				return dataVector.get(row);
			} catch (Exception e) {
				e.printStackTrace();
			}
		case 1:
			try {
				String quantity = ((ReportSalesEntity) dataVector.get(row)).getQuantity();
				if (quantity != null) {
					MyDouble a = new MyDouble(quantity);
					a.setNumDot(0);
					return a.toString();
				} else
					return 0;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		case 2:
			try {
				String priceAverage = ((ReportSalesEntity) dataVector.get(row)).getPriceAverage();
				if (priceAverage != null) {
					MyDouble a = new MyDouble(priceAverage);
					a.setNumDot(0);
					return a.toString();
				}
				else
					return 0;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		case 3:
			try {
				String totalPrice = ((ReportSalesEntity) dataVector.get(row)).getTotalPrice();
				if (totalPrice != null) {
					MyDouble a = new MyDouble(totalPrice);
					a.setNumDot(0);
					return a.toString();
				}
				else
					return 0;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		default:
			return "";
		}
	}

	public void removeRowReport(int row) {
		ReportSalesEntity object = (ReportSalesEntity) dataVector.get(row);
		int k = row + 1;
		while (((ReportSalesEntity) dataVector.get(k)).getIndex() > object.getIndex()) {
			if (k == dataVector.size() - 1) {
				k++;
				break;
			}
			k++;
		}
		for (ReportSalesEntity se : salesEntitys.subList(row + 1, k)) {
			if (se.getType() != ReportSalesEntity.PRODUCT) {
				// se.getLabelIcon().setIcon(new
				// ImageIcon(HomeScreen.class.getResource("icon/square_add_16.png")));
				se.setWiden(false);
			}
		}
		salesEntitys.removeAll(salesEntitys.subList(row + 1, k));
		dataVector = convertToVector(salesEntitys.toArray());
		fireTableDataChanged();
	}

	public ReportStatistics getReportStatistics() {
		return reportStatistics;
	}

	public void setReportStatistics(ReportStatistics reportStatistics) {
		this.reportStatistics = reportStatistics;
	}

}
