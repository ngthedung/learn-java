package com.hkt.client.swingexp.app.hethong.list;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.ITableMain;
import com.hkt.client.swingexp.app.core.TableObservable;
import com.hkt.client.swingexp.app.hethong.PanelDepartment;
import com.hkt.client.swingexp.app.hethong.PanelManageDelete;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.hr.entity.Employee;

@SuppressWarnings("serial")
public class TableListDepartment extends TableObservable implements ITableMain {
	private TableModelDepartment	modelTable;
	private AccountGroup					rootDepartment;
	private List<AccountGroup>		departments;

	public TableListDepartment() {
		departments = new ArrayList<AccountGroup>();
		try {
			rootDepartment = HRModelManager.getInstance().getRootDepartment();
			departments.addAll(AccountModelManager.getInstance().getAllChildrensByPath(rootDepartment.getPath()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		modelTable = new TableModelDepartment(departments);
		setModel(modelTable);
	}

	@Override
	public void click() {
		AccountGroup accountGroup = (AccountGroup) this.getValueAt(this.getSelectedRow(), 2);
		try {
			PanelDepartment panel = new PanelDepartment();
			panel.setData(accountGroup);
			panel.setName("PhongBanBoPhan");
			DialogMain dialog = new DialogMain(panel);
			dialog.setIcon("kho1.png");
			dialog.setName("dlPhongBanBoPhan");
			dialog.setTitle("Phòng ban bộ phận");
			dialog.showButton(false);
			dialog.setModal(true);
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);

			departments = AccountModelManager.getInstance().getAllChildrensByPath(rootDepartment.getPath());
			change();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getListSize() {
		return departments.size();
	}

	@Override
	public List<Integer> getColumnSum() {
		return null;
	}

	@Override
	public DefaultTableModel loadTable(int index, int pageSize) {
		try {
			modelTable.setData(departments.subList(index, pageSize), index);
		} catch (Exception ex) {
			modelTable = new TableModelDepartment(new ArrayList<AccountGroup>());
		}
		return modelTable;
	}

  @Override
  public boolean delete() {
	  if (UIConfigModelManager.getInstance().getPermission(
				PanelDepartment.permission) == Capability.ADMIN) {
		  try {
				String str = "Xóa tất cả danh sách đã chọn ";
				PanelChoise panel = new PanelChoise(str + " ?");
				panel.setName("Xoa");
				DialogResto dialog = new DialogResto(panel, false, 0, 80);
				dialog.setName("dlXoa");
				dialog.setTitle("Xóa phòng ban");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
				if (panel.isDelete()) {
					for (int i = 0; i < departments.size();) {
						AccountGroup acc = departments.get(i);
						if (acc.isRecycleBin()) {
							acc.setRecycleBin(false);
							showDepartment(acc);

						} else {
							i++;
						}
						
					}
				}

			} catch (Exception ex) {
			}

		} else {
			JOptionPane
					.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			return false;
		}
    return false;
  }
  
  public void showDepartment(AccountGroup department) throws Exception {
		  String namedepartment = department.getLabel();
		  List<AccountGroup> listchildren = new ArrayList<AccountGroup>();
		    listchildren = AccountModelManager.getInstance().getAllChildrensByPath(department.getPath());
		    if (listchildren.isEmpty() || listchildren == null) {
		    	 if (HRModelManager.getInstance().findEmployeesByAccountGroup(department) == null
		   	          || HRModelManager.getInstance().findEmployeesByAccountGroup(department).isEmpty()) {
		   	        AccountModelManager.getInstance().deleteGroup(department);
		   	        ((JDialog) getRootPane().getParent()).dispose();
		   	      } else {
		   	        try {
		   	          PanelManageDelete<Employee> panel = new PanelManageDelete<Employee>(namedepartment, HRModelManager
		   	              .getInstance().findEmployeesByAccountGroup(department), new DepartmentComboBoxDelete(department));
		   	          DialogResto dialog = new DialogResto(panel, true, 600, 300);
		   	          dialog.dispose();
		   	          dialog.setUndecorated(true);
		   	          dialog.setTitle("Quản lý xóa phòng ban");
		   	          dialog.setLocationRelativeTo(null);
		   	          dialog.setModal(true);
		   	          dialog.setVisible(true);
		   	        } catch (Exception e2) {
		   	          e2.printStackTrace();
		   	        }
		   	     if (HRModelManager.getInstance().findEmployeesByAccountGroup(department) == null
		 	            || HRModelManager.getInstance().findEmployeesByAccountGroup(department).isEmpty()) {
		 	          AccountModelManager.getInstance().deleteGroup(department);
		 	          change();
		 	          ((JDialog) getRootPane().getParent()).dispose();
		 	        }
		 	      }
		    } else {
			      try {
			        PanelManageDelete<AccountGroup> panel = new PanelManageDelete<AccountGroup>(namedepartment, AccountModelManager
			            .getInstance().getChildrensByPath(department.getPath()), new DepartmentChildren(department));
			        DialogResto dialog = new DialogResto(panel, true, 600, 100);
			        dialog.dispose();
			        dialog.setUndecorated(true);
			        dialog.setTitle("Quản lý xóa phòng ban");
			        dialog.setIcon("kho1.png");
			        dialog.setLocationRelativeTo(null);
			        dialog.setModal(true);
			        dialog.setVisible(true);
			      } catch (Exception e2) {
			        e2.printStackTrace();
			      }

			      if ((AccountModelManager.getInstance().getAllChildrensByPath(department.getPath()).isEmpty())
			          && (HRModelManager.getInstance().findEmployeesByAccountGroup(department) == null || HRModelManager
			              .getInstance().findEmployeesByAccountGroup(department).isEmpty())) {
			        AccountModelManager.getInstance().deleteGroup(department);
			        change();
			        ((JDialog) getRootPane().getParent()).dispose();
			      }
			    }
	  
	  }
  
  @Override
  public boolean isDelete() {
    return true;
  }
}
