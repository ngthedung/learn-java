package com.hkt.client.swingexp.app.hethong.list;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.hr.entity.Employee;

@SuppressWarnings("serial")
public class DepartmentChildren extends JComboBox<AccountGroup> implements IComboBoxDelete<AccountGroup> {
	private List<AccountGroup> accountGroups;
	@SuppressWarnings("unused")
	private AccountGroup accOld;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DepartmentChildren(AccountGroup accOld) {
		this.accOld = accOld;

		accountGroups = new ArrayList<AccountGroup>();
		List<AccountGroup> accChilden;
		try {
			AccountGroup groupHKT = AccountModelManager.getInstance().getRootGroupDetail().getChildren().get(0);
			AccountGroup groupDepartment = AccountModelManager.getInstance().getGroupByPath(
			    groupHKT.getPath() + "/" + AccountModelManager.Department);
			accountGroups = AccountModelManager.getInstance().getAllChildrensByPath(groupDepartment.getPath());
			accChilden = AccountModelManager.getInstance().getAllChildrensByPath(accOld.getPath());
		} catch (Exception e) {
			accChilden = new ArrayList<AccountGroup>();
		}
		for (int i = 0; i < accountGroups.size();) {
			boolean children = false;
			for (AccountGroup aGroup : accChilden) {
				if (accountGroups.get(i).getName().equals(aGroup.getName())
				    || accountGroups.get(i).getName().equals(accOld.getName())) {
					children = true;
					break;
				}
			}
			if (children) {
				accountGroups.remove(i);
			} else {
				i++;
			}
		}
		accountGroups.add(0, null);
		setModel(new DefaultComboBoxModel(accountGroups.toArray()));
	}

	public AccountGroup getSelectedDepartment() {
		try {
			return (AccountGroup) getSelectedItem();
		} catch (Exception e) {
			return null;
		}

	}

	public void setSelectedAccountGroup(String codeAccountGroup) {
		for (int i = 0; i < accountGroups.size(); i++) {
			if (accountGroups.get(i).getPath().equals(codeAccountGroup)) {
				this.setSelectedIndex(i);
				break;
			}
		}
	}

	@Override
	public boolean delete(List<AccountGroup> list) {
		try {
			for (int i = 0; i < list.size(); i++) {
				List<AccountGroup> children = new ArrayList<AccountGroup>();
				children = AccountModelManager.getInstance().getAllChildrensByPath(list.get(i).getPath());
				List<Employee> emp = new ArrayList<Employee>();
				emp = HRModelManager.getInstance().findEmployeesByAccountGroup(list.get(i));
				if ((children.isEmpty() || children == null) && (emp.isEmpty() || emp == null)) {
					AccountModelManager.getInstance().deleteGroup(list.get(i));
				} else {
					JOptionPane.showMessageDialog(null, "Không được phép xóa!");
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void changeGroup(List<AccountGroup> list1) {
		try {
			for (int i = 0; i < list1.size(); i++) {
				AccountGroup accOld = list1.get(i);
				List<AccountGroup> list = AccountModelManager.getInstance().getAllChildrensByPath(accOld.getPath());
				AccountGroup parOld = AccountModelManager.getInstance().getGroupByPath(accOld.getParentPath());
				String partNew;
				try {
					partNew = getSelectedDepartment().getPath();
				} catch (Exception e) {
					partNew = "hkt/Phòng ban";
				}
				try {
					for (int j = 0; j < list.size(); j++) {
						AccountGroup acc = list.get(j);
						List<AccountMembership> listemp = AccountModelManager.getInstance()
						    .findMembershipByGroupPath(acc.getPath());
						String strParent = acc.getParentPath().replaceAll(parOld.getPath(), partNew);
						acc.setParentPath(strParent);
						String strPath = acc.getPath().replaceAll(parOld.getPath(), partNew);
						acc.setPath(strPath);
						acc = AccountModelManager.getInstance().saveGroup(acc);
						for (int k = 0; k < listemp.size(); k++) {
							AccountMembership emp = listemp.get(k);
							emp.setGroupPath(acc.getPath());
							AccountModelManager.getInstance().saveAccountMembership(emp);
						}
					}
				} catch (Exception e) {
				}
				try {
					List<AccountMembership> listemp = AccountModelManager.getInstance().findMembershipByGroupPath(
					    accOld.getPath());
					String strParent = accOld.getParentPath().replaceAll(parOld.getPath(), partNew);
					accOld.setParentPath(strParent);
					String strPath = accOld.getPath().replaceAll(parOld.getPath(), partNew);
					accOld.setPath(strPath);
					accOld = AccountModelManager.getInstance().saveGroup(accOld);
					for (int j = 0; j < listemp.size(); j++) {
						AccountMembership emp = listemp.get(j);
						emp.setGroupPath(accOld.getPath());
						AccountModelManager.getInstance().saveAccountMembership(emp);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
		}
	}

}
