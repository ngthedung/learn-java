package com.hkt.swingexp.app.report.modeltable;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.module.accounting.entity.Invoice;
import com.hkt.swingexp.app.report.entity.ReportProjectMember;

public class ModelTableReportProjectMember extends DefaultTableModel{
	private List<ReportProjectMember> listReport = new ArrayList<ReportProjectMember>();
	private String[] COLUMN_NAMES;
	private HashMap<String, String> conds;
	private String a = "";
	public ModelTableReportProjectMember(int columnType, List<ReportProjectMember> listData, HashMap<String, String> conds)
	{
		this.conds = conds;
		if(conds.containsKey("project"))
		{
			if(columnType == 2)
				COLUMN_NAMES = new String[6];
			else
				COLUMN_NAMES = new String[5];
			
			switch (columnType) {
			case 0:{
				COLUMN_NAMES[0] = "Danh mục";
				COLUMN_NAMES[1] = "Tổng thu";
				COLUMN_NAMES[2] = "Thực thu";
				COLUMN_NAMES[3] = "Tỉ lệ(%)";
				COLUMN_NAMES[4] = "Xử lý";
			} break;
			case 1: {
				COLUMN_NAMES[0] = "Danh mục";
				COLUMN_NAMES[1] = "Tổng chi";
				COLUMN_NAMES[2] = "Thực chi";
				COLUMN_NAMES[3] = "Tỉ lệ(%)";
				COLUMN_NAMES[4] = "Xử lý";
			} break;
			case 2: {
				COLUMN_NAMES[0] = "Danh mục";
				COLUMN_NAMES[1] = "Tổng thu";
				COLUMN_NAMES[2] = "Tổng chi";
				COLUMN_NAMES[3] = "Doanh thu thuần";
				COLUMN_NAMES[4] = "Tỉ lệ(%)";
				COLUMN_NAMES[5] = "Xử lý";
			} break;
			case 3: {
				COLUMN_NAMES[0] = "Danh mục";
				COLUMN_NAMES[1] = "Doanh thu thuần";
				COLUMN_NAMES[2] = "Thưởng";
				COLUMN_NAMES[3] = "Tỉ lệ(%)";
				COLUMN_NAMES[4] = "Xử lý";
			} break;
			default: {
				COLUMN_NAMES[0] = "A";
				COLUMN_NAMES[1] = "B";
				COLUMN_NAMES[2] = "C";
				COLUMN_NAMES[3] = "D";
				COLUMN_NAMES[4] = "E";
			}break;
			}
		}
		else
		{
			if(columnType == 2)
				COLUMN_NAMES = new String[5];
			else
				COLUMN_NAMES = new String[4];
			
			switch (columnType) {
			case 0:{
				COLUMN_NAMES[0] = "Danh mục";
				COLUMN_NAMES[1] = "Tổng thu";
				COLUMN_NAMES[2] = "Thực thu";
				COLUMN_NAMES[3] = "Xử lý";
			} break;
			case 1: {
				COLUMN_NAMES[0] = "Danh mục";
				COLUMN_NAMES[1] = "Tổng chi";
				COLUMN_NAMES[2] = "Thực chi";
				COLUMN_NAMES[3] = "Xử lý";
			} break;
			case 2: {
				COLUMN_NAMES[0] = "Danh mục";
				COLUMN_NAMES[1] = "Tổng thu";
				COLUMN_NAMES[2] = "Tổng chi";
				COLUMN_NAMES[3] = "Doanh thu thuần";
				COLUMN_NAMES[4] = "Xử lý";
			} break;
			case 3: {
				COLUMN_NAMES[0] = "Danh mục";
				COLUMN_NAMES[1] = "Doanh thu thuần";
				COLUMN_NAMES[2] = "Thưởng";
				COLUMN_NAMES[3] = "Xử lý";
			} break;
			default: {
				COLUMN_NAMES[0] = "A";
				COLUMN_NAMES[1] = "B";
				COLUMN_NAMES[2] = "C";
				COLUMN_NAMES[3] = "D";
				COLUMN_NAMES[4] = "E";
			}break;
			}
		}
		listReport.addAll(listData);
		dataVector = convertToVector(listReport.toArray());
		fireTableDataChanged();
		
		
	}
	
	public void a(){
		for(int i = 0; i< dataVector.size(); i++){
			System.out.println((((ReportProjectMember) dataVector.get(i)).getXuly()));
		}
	}

	public void addRowChild(List<ReportProjectMember> list, int i){
		listReport.addAll(i, list);
		dataVector = convertToVector(listReport.toArray());
		fireTableDataChanged();
	}
	
	@Override
	public String getColumnName(int col){
		return COLUMN_NAMES[col];
	}
	
	@Override
	public int getColumnCount(){
		return COLUMN_NAMES.length;
	}
	
	@Override
	public void setValueAt(Object aValue, int row, int col){
		if(col==getColumnCount()-1)
		{
			ReportProjectMember member = (ReportProjectMember) dataVector.get(row);
			member.setXuly(aValue.toString());
		}
		else
		{
		try {
			super.setValueAt(aValue, row, col);
		} catch (Exception e) {
		}
		}
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		ReportProjectMember rpm = ((ReportProjectMember) dataVector.get(row));
		if(col==getColumnCount()-1)
		{
			return rpm.getXuly();
		}
		else
		{
		try {
			switch (col) {
			case 0:
				try {
					return rpm;
				} catch (Exception ex) {
					Vector rowVector = (Vector) dataVector.elementAt(row);
					return rowVector.elementAt(col);
				}
			case 1:
				try {
					if(rpm.getCol1() == "")
						return a;
					else
						return MyDouble.valueOf(rpm.getCol1());
				} catch (Exception ex) {
					Vector rowVector = (Vector) dataVector.elementAt(row);
					return rowVector.elementAt(col);
				}
			case 2:
				try {
					if(rpm.getCol1() == "")
						return a;
					else
						return MyDouble.valueOf(rpm.getCol2());
				} catch (Exception ex) {
					Vector rowVector = (Vector) dataVector.elementAt(row);
					return rowVector.elementAt(col);
				}
			case 3:
				try {
					if(rpm.getCol1() == "")
						return a;
					else
						return MyDouble.valueOf(rpm.getCol3());
				} catch (Exception ex) {
					Vector rowVector = (Vector) dataVector.elementAt(row);
					return rowVector.elementAt(col);
				}
			case 4:
				try {
					if(rpm.getCol1() == "")
						return a;
					else
						return MyDouble.valueOf(rpm.getCol4());
				} catch (Exception ex) {
					Vector rowVector = (Vector) dataVector.elementAt(row);
					return rowVector.elementAt(col);
				}
		}
		} catch (Exception e) {
		}
		}
		
		return null;
	}
	
	public void removeRowReport(int row){
		ReportProjectMember object = (ReportProjectMember) dataVector.get(row);
		int k = row +1;
		while(((ReportProjectMember) dataVector.get(k)).getIndex() > object.getIndex()){
			if(k == dataVector.size() -1){
				k++;
				break;
			}
			k++;
		}
		for(ReportProjectMember rpm : listReport.subList(row + 1, k)){
			if(rpm.getType() != ReportProjectMember.Type.CHILD){
				rpm.setWiden(false);
			}
		}
		listReport.removeAll(listReport.subList(row +1, k));
		dataVector = convertToVector(listReport.toArray());
		fireTableDataChanged();
	}

	public String[] getCOLUMN_NAMES() {
		return COLUMN_NAMES;
	}

	public void setCOLUMN_NAMES(String[] cOLUMN_NAMES) {
		COLUMN_NAMES = cOLUMN_NAMES;
	}

	
}
