package com.hkt.swingexp.app.report.modeltable;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.banhang.ReportStatistics;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.swingexp.app.report.entity.ReportBankEntity;
import com.hkt.swingexp.app.report.table.TableRerenderBank;

public class ModelTableReportBank  extends DefaultTableModel {
	private List<ReportBankEntity>	listReport	= new ArrayList<ReportBankEntity>();
	private String[]						COLUMN_NAMES;
	private ReportStatistics		reportStatistics;
	
	public ModelTableReportBank(List<ReportBankEntity> listData) {
		COLUMN_NAMES = new String[4];
		COLUMN_NAMES[0] = "Mã";
		COLUMN_NAMES[1] = "Chỉ Tiêu";
		COLUMN_NAMES[2] = "Trước";
		COLUMN_NAMES[3] = "Sau";
		
		listReport.addAll(listData);
		dataVector = convertToVector(listReport.toArray());
		fireTableDataChanged();
	}
	
	public void addRowChild(List<ReportBankEntity> list, int index) {
		listReport.addAll(index, list);
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
	
	@Override
	public void setValueAt(Object aValue, int row, int column) {
		try {
			super.setValueAt(aValue, row, column);
		} catch (Exception ex) {
		}
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		ReportBankEntity emp = ((ReportBankEntity) dataVector.get(row));
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
					return emp.getColumn1();
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
		}
		return null;
	}
	
	public void removeRowReport(int row) {
		ReportBankEntity object = (ReportBankEntity) dataVector.get(row);
		int k = row + 1;
		while (((ReportBankEntity) dataVector.get(k)).getIndex() > object.getIndex()) {
			if (k == dataVector.size() - 1) {
				k++;
				break;
			}
			k++;
		}
		for (ReportBankEntity se : listReport.subList(row + 1, k)) {
			if (se.getType() != ReportBankEntity.Type.CHILD) {
				se.setWiden(false);
			}
		}
		listReport.removeAll(listReport.subList(row + 1, k));
		dataVector = convertToVector(listReport.toArray());
		fireTableDataChanged();
	}
	
	public ReportStatistics getReportStatistics() {
		return reportStatistics;
	}
	
	public void setReportStatistics(ReportStatistics reportStatistics) {
		this.reportStatistics = reportStatistics;
	}
	
}
