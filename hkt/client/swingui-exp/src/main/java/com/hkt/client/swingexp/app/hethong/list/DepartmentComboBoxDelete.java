package com.hkt.client.swingexp.app.hethong.list;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.hr.entity.Employee;

@SuppressWarnings("serial")
public class DepartmentComboBoxDelete extends JComboBox<AccountGroup> implements IComboBoxDelete<Employee> {
	private List<AccountGroup> accountGroups;
	private AccountGroup accOld;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DepartmentComboBoxDelete(AccountGroup accOld) {
		this.accOld = accOld;
		accountGroups = new ArrayList<AccountGroup>();
		try {
			AccountGroup groupHKT = AccountModelManager.getInstance().getRootGroupDetail().getChildren().get(0);
			AccountGroup groupDepartment = AccountModelManager.getInstance().getGroupByPath(
			    groupHKT.getPath() + "/" + AccountModelManager.Department);
			accountGroups = AccountModelManager.getInstance().getAllChildrensByPath(groupDepartment.getPath());

		} catch (Exception e) {
		}
		for (int i = 0; i < accountGroups.size();) {
			boolean children = false;
			if (accountGroups.get(i).getName().equals(accOld.getName())) {
				children = true;
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
	public boolean delete(List<Employee> list) {
		try {
			for (int i = 0; i < list.size(); i++) {
				AccountModelManager.getInstance().deleteMembershipByLoginIdAndGroupPath(list.get(i).getLoginId(),
				    accOld.getPath());
				HRModelManager.getInstance().deleteEmployee(list.get(i).getId());
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void changeGroup(List<Employee> list) {
		try {
			for (int i = 0; i < list.size(); i++) {

				AccountMembership ac = AccountModelManager.getInstance().getMembershipByAccountAndGroup(
				    list.get(i).getLoginId(), accOld.getPath());
				if (getSelectedDepartment() != null) {
					ac.setGroupPath(getSelectedDepartment().getPath());
					AccountModelManager.getInstance().saveAccountMembership(ac);
				} else {
					AccountModelManager.getInstance().deleteMembershipByLoginIdAndGroupPath(ac.getLoginId(), ac.getGroupPath());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
