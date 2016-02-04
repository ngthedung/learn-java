package com.hkt.swingexp.app.report.modeltable;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.banhang.ReportStatistics;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.swingexp.app.report.entity.InventoryReportEntity;
import com.hkt.swingexp.app.report.entity.ReportSalesEntity;

/**
 * @author Phan Anh
 * @date 28/01/2015
 */

public class TableModelReportInventory extends DefaultTableModel {
	private List<InventoryReportEntity>	reportMains		= new ArrayList<InventoryReportEntity>();
	private final static String[]				COLUMN_NAMES	= { "Danh mục", "SL đầu kỳ", "GT đầu kỳ", "SL tăng", "GT tăng", "SL giảm", "GT giảm", "SL cuối kỳ", "GT cuối kỳ", "SL hiện tại", "GT hiện tại" };
	private ReportStatistics						reportStatistics;
	
	public TableModelReportInventory(List<InventoryReportEntity> departments) {
		reportMains.addAll(departments);
		dataVector = convertToVector(reportMains.toArray());
	}
	
	public void addRow(List<InventoryReportEntity> inventoryReportByOrgsChild, int index) {
		reportMains.addAll(index, inventoryReportByOrgsChild);
		dataVector = convertToVector(reportMains.toArray());
		fireTableDataChanged();
	}
	
	public TableModelReportInventory() {
	}
	
	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}
	
	@Override
	public String getColumnName(int column) {
		return COLUMN_NAMES[column];
	}
	
	@Override
	public void setValueAt(Object aValue, int row, int column) {
		try {
			super.setValueAt(aValue, row, column);
		} catch (Exception e) {
		}
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		InventoryReportEntity inventoryReport = ((InventoryReportEntity) dataVector.get(rowIndex));
		switch (columnIndex) {
			case 0:
				try {
					return inventoryReport;
				} catch (Exception e) {
					Vector rowVector = (Vector) dataVector.elementAt(rowIndex);
					return rowVector.elementAt(columnIndex);
				}
			case 1:
				try {
					return MyDouble.valueOf(inventoryReport.getCol2_SLDauKy());
				} catch (Exception e) {
					return "0";
				}
				
			case 2:
				try {
					return MyDouble.valueOf(inventoryReport.getCol3_GTDauKy());
				} catch (Exception e) {
					return "0";
				}
				
			case 3:
				try {
					return MyDouble.valueOf(inventoryReport.getCol4_SLTang());
				} catch (Exception e) {
					return "0";
				}
				
			case 4:
				try {
					return MyDouble.valueOf(inventoryReport.getCol5_GTTang());
				} catch (Exception e) {
					return "0";
				}
				
			case 5:
				try {
					return MyDouble.valueOf(inventoryReport.getCol6_SLGiam());
				} catch (Exception e) {
					return "0";
				}
				
			case 6:
				try {
					
//					if(inventoryReport.getPrice()>0){
//						System.out.println(inventoryReport.getPrice()+inventoryReport.getCol1_DanhMuc());
//						return MyDouble.valueOf(MyDouble.valueOf(inventoryReport.getCol6_SLGiam()).doubleValue()*inventoryReport.getPrice());
//					}
					return MyDouble.valueOf(inventoryReport.getCol7_GTGiam()).toString().replace("-", "");
				} catch (Exception e) {
					return "0";
				}
				
			case 7:
				try {
					return MyDouble.valueOf(inventoryReport.getCol8_SLCuoiKy());
				} catch (Exception e) {
					return "0";
				}
				
			case 8:
				try {
					return MyDouble.valueOf(inventoryReport.getCol9_GTCuoiKy()).toString().replace("-", "");
				} catch (Exception e) {
					return "0";
				}
				
			case 9:
				try {
					return MyDouble.valueOf(inventoryReport.getCol10_SLHienTai());
				} catch (Exception e) {
					return "0";
				}
				
			case 10:
				try {
					return MyDouble.valueOf(inventoryReport.getCol11_GTHienTai()).toString().replace("-", "");
				} catch (Exception e) {
					return "0";
				}
		}
		return null;
	}
	
	public void removeRowReport(int row) {
		InventoryReportEntity object = (InventoryReportEntity) dataVector.get(row);
		int k = row + 1;
		while (((InventoryReportEntity) dataVector.get(k)).getIndex() > object.getIndex()) {
			if (k == dataVector.size() - 1) {
				k++;
				break;
			}
			k++;
		}
		for (InventoryReportEntity se : reportMains.subList(row + 1, k)) {
			if (se.getType() != ReportSalesEntity.PRODUCT) {
				se.setWiden(false);
			}
		}
		reportMains.removeAll(reportMains.subList(row + 1, k));
		dataVector = convertToVector(reportMains.toArray());
		fireTableDataChanged();
	}
	
	public ReportStatistics getReportStatistics() {
		return reportStatistics;
	}
	
	public void setReportStatistics(ReportStatistics reportStatistics) {
		this.reportStatistics = reportStatistics;
	}
	
}
