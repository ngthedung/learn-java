package com.hkt.client.swingexp.app.khuvucbanhang.list;

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import com.hkt.client.swing.widget.model.AutoCompleteItem;
import com.hkt.client.swingexp.app.hethong.list.IComboBoxDelete;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.partner.customer.entity.Customer;

@SuppressWarnings("serial")
public class GroupComboBoxDelete extends JComboBox<AccountGroup> implements
		IComboBoxDelete<Customer> {
	private List<AccountGroup> accountGroups;
	private AccountGroup accOld;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GroupComboBoxDelete(AccountGroup accOld) {
		this.accOld = accOld;
		accountGroups = new ArrayList<AccountGroup>();
		try {
			AccountGroup groupHKT = AccountModelManager.getInstance()
					.getRootGroupDetail().getChildren().get(0);
			AccountGroup groupPartners = AccountModelManager.getInstance()
					.getGroupByPath(
							groupHKT.getPath() + "/"
									+ AccountModelManager.Customer);
			accountGroups = AccountModelManager.getInstance()
					.getChildrensByPath(groupPartners.getPath());

		} catch (Exception e) {
		}
		for (int i = 0; i < accountGroups.size();) {
		      boolean children = false;
		        if (accountGroups.get(i).getName().equals(accOld.getName())) {
		          children = true;
		        }
		      if(children){
		        accountGroups.remove(i);
		      } else {
		        i++;
		      }
		    }
		    accountGroups.add(0, null);

		setModel(new DefaultComboBoxModel(accountGroups.toArray()));
	}

	public AccountGroup getSelectedType() {
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
	public boolean delete(List<Customer> list) {
		try {

			for (int i = 0; i < list.size(); i++) {
				AccountModelManager.getInstance()
						.deleteMembershipByLoginIdAndGroupPath(list.get(i).getLoginId(),
								accOld.getPath());
				 CustomerModelManager.getInstance().deleteCustomer(list.get(i));
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void changeGroup(List<Customer> list) {
		
		try {
			for (int i = 0; i < list.size(); i++) {
				AccountMembership ac = AccountModelManager.getInstance()
						.getMembershipByAccountAndGroup(
								list.get(i).getLoginId(), accOld.getPath());
				if(getSelectedType()!=null){
				ac.setGroupPath(getSelectedType().getPath());
				AccountModelManager.getInstance().saveAccountMembership(ac);
				}else {
					AccountModelManager.getInstance().deleteMembershipByLoginIdAndGroupPath(ac.getLoginId(), ac.getGroupPath());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
