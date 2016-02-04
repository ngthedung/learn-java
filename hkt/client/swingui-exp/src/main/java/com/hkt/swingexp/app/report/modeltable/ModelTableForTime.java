package com.hkt.swingexp.app.report.modeltable;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.banhang.ReportStatistics;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.swingexp.app.report.entity.ReportForTimeEntity;

/*
 * @author Phan Anh
 * @date 01/09/2014
 */

public class ModelTableForTime extends DefaultTableModel {
	private List<ReportForTimeEntity>	listReport	= new ArrayList<ReportForTimeEntity>();
	private String[]									COLUMN_NAMES;
	private ReportStatistics					reportStatistics;
	
	public ModelTableForTime(int columnType, List<ReportForTimeEntity> listData) {
		//Set columnType = 0,1,2 TK theo doanh thu, chi phí, thu chi lãi (4 cột)
		//Set columnType = 3 TK theo thu chi công nợ (8 cột)
		//Set columnType = 4 TK thu chi lãi cho TAB sản phẩm
		if (columnType == 3)
			COLUMN_NAMES = new String[8];
		else if (columnType == 5)
			COLUMN_NAMES = new String[3];
		else
			COLUMN_NAMES = new String[4];
		
		switch (columnType) {
			case 0: {
				COLUMN_NAMES[0] = "Danh mục";
				COLUMN_NAMES[1] = "Tổng chi";
				COLUMN_NAMES[2] = "Thực chi";
				COLUMN_NAMES[3] = "Tỉ lệ(%)";
			}
				break;
			case 1: {
				COLUMN_NAMES[0] = "Danh mục";
				COLUMN_NAMES[1] = "Tổng thu";
				COLUMN_NAMES[2] = "Thực thu";
				COLUMN_NAMES[3] = "Tỉ lệ(%)";
			}
				break;
			case 2: {
				COLUMN_NAMES[0] = "Danh mục";
				COLUMN_NAMES[1] = "Thực thu";
				COLUMN_NAMES[2] = "Thực chi";
				COLUMN_NAMES[3] = "Lãi";
			}
				break;
			case 3: {
				COLUMN_NAMES[0] = "Danh mục";
				COLUMN_NAMES[1] = "Tổng thu";
				COLUMN_NAMES[2] = "Tổng chi";
				COLUMN_NAMES[3] = "Thực thu";
				COLUMN_NAMES[4] = "Thực chi";
				COLUMN_NAMES[5] = "Phải thu";
				COLUMN_NAMES[6] = "Phải chi";
				COLUMN_NAMES[7] = "Lãi(Lỗ)";
			}
				break;
			case 4: {
				COLUMN_NAMES[0] = "Danh mục";
				COLUMN_NAMES[1] = "Tổng thu";
				COLUMN_NAMES[2] = "Tổng chi";
				COLUMN_NAMES[3] = "Lãi";
			}
				break;
			case 5: {
				COLUMN_NAMES[0] = "Danh mục";
				COLUMN_NAMES[1] = "Số năm nay";
				COLUMN_NAMES[2] = "Số năm trước";
				COLUMN_NAMES[3] = "Lãi";
			}
				break;
			default: {
				COLUMN_NAMES[0] = "A";
				COLUMN_NAMES[1] = "B";
				COLUMN_NAMES[2] = "C";
				COLUMN_NAMES[3] = "D";
			}
				break;
		}
		listReport.addAll(listData);
		dataVector = convertToVector(listReport.toArray());
		fireTableDataChanged();
	}
	
	public void setData(List<ReportForTimeEntity> list) {
		this.listReport = list;
		dataVector = convertToVector(list.toArray());
		fireTableDataChanged();
	}
	
	public void addRowChild(List<ReportForTimeEntity> childs, int index) {
		listReport.addAll(index, childs);
		dataVector = convertToVector(listReport.toArray());
		fireTableDataChanged();
	}
	
	@Override
	public String getColumnName(int column) {
		return COLUMN_NAMES[column];
	}
	
	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}
	
	// @Override
	// public boolean isCellEditable(int row, int column) {
	// return false;
	// }
	
	@Override
	public void setValueAt(Object aValue, int row, int column) {
		try {
			super.setValueAt(aValue, row, column);
		} catch (Exception ex) {
		}
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		ReportForTimeEntity emp = ((ReportForTimeEntity) dataVector.get(row));
		switch (column) {
			case 0:
				try {
					return emp;
				} catch (Exception ex) {
					Vector rowVector = (Vector) dataVector.elementAt(row);
					return rowVector.elementAt(column);
				}
			case 1:
				try {
					return MyDouble.valueOf(emp.getColumn1());
				} catch (Exception ex) {
					Vector rowVector = (Vector) dataVector.elementAt(row);
					return rowVector.elementAt(column);
				}
			case 2:
				try {
					return MyDouble.valueOf(emp.getColumn2());
				} catch (Exception ex) {
					Vector rowVector = (Vector) dataVector.elementAt(row);
					return rowVector.elementAt(column);
				}
			case 3:
				try {
					return MyDouble.valueOf(emp.getColumn3());
				} catch (Exception ex) {
					Vector rowVector = (Vector) dataVector.elementAt(row);
					return rowVector.elementAt(column);
				}
			case 4:
				try {
					return MyDouble.valueOf(emp.getColumn4());
				} catch (Exception ex) {
					Vector rowVector = (Vector) dataVector.elementAt(row);
					return rowVector.elementAt(column);
				}
			case 5:
				try {
					return MyDouble.valueOf(emp.getColumn5());
				} catch (Exception ex) {
					Vector rowVector = (Vector) dataVector.elementAt(row);
					return rowVector.elementAt(column);
				}
			case 6:
				try {
					return MyDouble.valueOf(emp.getColumn6());
				} catch (Exception ex) {
					Vector rowVector = (Vector) dataVector.elementAt(row);
					return rowVector.elementAt(column);
				}
			case 7:
				try {
					return MyDouble.valueOf(emp.getColumn7());
				} catch (Exception ex) {
					Vector rowVector = (Vector) dataVector.elementAt(row);
					return rowVector.elementAt(column);
				}
		}
		return null;
	}
	
	public void removeRowReport(int row) {
		ReportForTimeEntity object = (ReportForTimeEntity) dataVector.get(row);
		int k = row + 1;
		while (((ReportForTimeEntity) dataVector.get(k)).getIndex() > object.getIndex()) {
			if (k == dataVector.size() - 1) {
				k++;
				break;
			}
			k++;
		}
		for (ReportForTimeEntity se : listReport.subList(row + 1, k)) {
			if (se.getType() != ReportForTimeEntity.Type.NODE_DAY) {
				se.setWiden(false);
			}
		}
		listReport.removeAll(listReport.subList(row + 1, k));
		dataVector = convertToVector(listReport.toArray());
		fireTableDataChanged();
	}
	
	public void setReportStatistics(ReportStatistics reportStatistics) {
		this.reportStatistics = reportStatistics;
	}

	public ReportStatistics getReportStatistics() {
		return reportStatistics;
	}
	
}
