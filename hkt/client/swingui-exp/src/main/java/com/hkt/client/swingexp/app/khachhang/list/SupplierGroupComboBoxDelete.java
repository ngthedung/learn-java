package com.hkt.client.swingexp.app.khachhang.list;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import com.hkt.client.swingexp.app.hethong.list.IComboBoxDelete;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.SupplierModelManager;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.supplier.entity.Supplier;

public class SupplierGroupComboBoxDelete extends JComboBox<AccountGroup> implements IComboBoxDelete<Supplier> {
	private List<AccountGroup> accountGroups;
	private AccountGroup accOld;
	
	public SupplierGroupComboBoxDelete(AccountGroup accOld){
		this.accOld = accOld;
		accountGroups = new ArrayList<AccountGroup>();
		try {
			AccountGroup groupHKT = AccountModelManager.getInstance()
					.getRootGroupDetail().getChildren().get(0);
			AccountGroup groupPartners = AccountModelManager.getInstance()
					.getGroupByPath(
							groupHKT.getPath() + "/"
									+ AccountModelManager.Supplier);
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
	public boolean delete(List<Supplier> list) {
		try {

			for (int i = 0; i < list.size(); i++) {
				AccountModelManager.getInstance()
						.deleteMembershipByLoginIdAndGroupPath(list.get(i).getLoginId(),
								accOld.getPath());
				 SupplierModelManager.getInstance().deleteSupplier(list.get(i));
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public void changeGroup(List<Supplier> list) {
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
