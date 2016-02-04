package com.hkt.client.swingexp.app.nhansu.list;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.ITableMain;
import com.hkt.client.swingexp.app.core.TableObservable;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.nhansu.EmployeeIsUser;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.hr.entity.Employee;

@SuppressWarnings("serial")
public class TableListEmployees extends TableObservable implements ITableMain {

  private TableModelEmployees  empConfigTable;
  private List<Employee> Employees;
  private DateFormat           df = new SimpleDateFormat("dd/MM/yyyy");

  public TableListEmployees() throws Exception {
    setName("empConfigTable");
    load();
    empConfigTable = new TableModelEmployees(new ArrayList<Employee>());
    setModel(empConfigTable);
    
  }

  public void load() throws Exception {
	  Employees = HRModelManager.getInstance().getEmployees();
  }

  @Override
  public void click() {
    try {
      Employee Employee = (Employee) this.getValueAt(this.getSelectedRow(), 2);
      EmployeeIsUser screenEmployee = new EmployeeIsUser(Employee, false);
      screenEmployee.setName("EmployeeIsUser");
      screenEmployee.setPreferredSize(new Dimension(100, 100));
      DialogMain dialog = new DialogMain(screenEmployee, true);
      dialog.setName("dlEmployeeIsUser");
      dialog.setIcon("nhansu1.png");
      dialog.setComponent3(CustomerModelManager.getInstance().getBtnEmployee(Employee));
      dialog.showButton(false);
      dialog.setTitle("Nhân sự mới");
      dialog.setModal(true);
      dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
      dialog.setLocationRelativeTo(null);
      dialog.setVisible(true);
      try {
          Employee = (Employee) HRModelManager.getInstance().getEmployeeByCode(Employee.getCode());
			if (Employee == null || Employee.isRecycleBin()) {
				empConfigTable.removeRow(this.getSelectedRow());
			} else {
				empConfigTable.getDataVector().set(this.getSelectedRow(), Employee);
				empConfigTable.fireTableRowsUpdated(this.getSelectedRow(), this.getSelectedRow());
			}
        } catch (Exception e) {
          e.printStackTrace();
        }
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }

  @Override
  public int getListSize() {
    return Employees.size();
  }

  @Override
  public List<Integer> getColumnSum() {
    return null;
  }

  @Override
  public DefaultTableModel loadTable(int index, int pageSize) {
    try {
      empConfigTable.setData(Employees.subList(index, pageSize), index);
    } catch (Exception ex) {
      empConfigTable = new TableModelEmployees(new ArrayList<Employee>());
    }
    return empConfigTable;
  }

  @Override
  public boolean delete() {
	  if (UIConfigModelManager.getInstance().getPermission(
				EmployeeIsUser.permission) == Capability.ADMIN) {
			try {
				String str = "Xóa tất cả danh sách đã chọn ";
				PanelChoise panel = new PanelChoise(str + " ?");
				panel.setName("Xoa");
				DialogResto dialog = new DialogResto(panel, false, 0, 80);
				dialog.setName("dlXoa");
				dialog.setTitle("Xóa nhân sự");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
				if (panel.isDelete()) {
					for (int i = 0; i < Employees.size();) {
						Employee acc = Employees.get(i);
						
						if (acc.isRecycleBin()) {
							acc.setRecycleBin(true);
							Employees.remove(i);
							HRModelManager.getInstance().deleteEmployee(acc.getId());
							
						} else {
							i++;
						}
					}
				}

			} catch (Exception ex) {
			}

			change();

		} else {
			JOptionPane
					.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			return false;
		}
    return false;
  }
  
  @Override
  public boolean isDelete() {
    return true;
  }
}
