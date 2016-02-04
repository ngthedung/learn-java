package com.hkt.client.swingexp.app.khuvucbanhang;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.ProductServiceClient;
import com.hkt.client.swing.widget.BeanBindingJTable;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.restaurant.entity.ProjectMember;

public class TableListEmployee extends BeanBindingJTable<ProjectMember>{

	static String[] HEADERS = {"STT", "Tên", "Quyền hạn", "%", "Thưởng"};
	static String[] PROPERTIES = {"id", "employeeCode", "role", "percent", "directAward" };
	private List<ProjectMember> projectMembers;
//	private Project clientCore = ClientContext.getInstance().getBean(ProductServiceClient.class);
	
	public void setEmployee(List<ProjectMember> members){
		init(HEADERS,PROPERTIES,members);
		setRowHeight(23);
		setFont(new Font("Tahoma", 0, 14));
		getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		getColumnModel().getColumn(0).setMaxWidth(50);
		getColumnModel().getColumn(3).setMaxWidth(50);
	}
	public void setEmployee(){
		init(HEADERS,PROPERTIES,projectMembers);
		setRowHeight(23);
		setFont(new Font("Tahoma", 0, 14));
		getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		getColumnModel().getColumn(0).setMaxWidth(50);
		getColumnModel().getColumn(3).setMaxWidth(50);
	}
	public TableListEmployee() {
	
	}

	public List<ProjectMember> getProjectMembers() {
		return projectMembers;
	}
	public void setProjectMembers(List<ProjectMember> projectMembers) {
		this.projectMembers = projectMembers;
	}
	public TableListEmployee(List<ProjectMember> members){
		init(HEADERS, PROPERTIES, members);
		setFont(new Font("Tahoma", 0, 14));
		getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
	}
	
	@Override
	protected ProjectMember newBean() {
	
		return new ProjectMember();
	}

	@Override
	protected boolean isBeanEditableAt(int row, int col) {
		
		return false;
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		if(column == 1)
		{
			try {
				Employee e = HRModelManager.getInstance().getEmployeeByCode(super.getValueAt(row, column).toString());
				return e.getName();
			} catch (Exception e) {
				return super.getValueAt(row, column);
			}
		}
		{
		return super.getValueAt(row, column);
		}
	}
	
	public void saveIndex(){
		try {
			for(int i = 0; i<getBeans().size();i++)
			{
				getBeans().get(i).setPriority(i);
//				clientCore.sa
			}
		} catch (Exception e) {
			
		}
	}

	

}
