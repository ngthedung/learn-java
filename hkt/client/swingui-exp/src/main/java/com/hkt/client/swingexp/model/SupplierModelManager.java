package com.hkt.client.swingexp.model;

import java.util.ArrayList;
import java.util.List;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.SupplierServiceClient;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.partner.supplier.entity.Supplier;

public class SupplierModelManager {
	private static SupplierModelManager instance;
	private ClientContext clientContext = ClientContext.getInstance();
	private SupplierServiceClient clientCore = clientContext
			.getBean(SupplierServiceClient.class);

	public SupplierModelManager() {

	}

	static public SupplierModelManager getInstance() {
		if (instance == null) {
			instance = new SupplierModelManager();
		}
		return instance;
	}

	public boolean deleteAll() throws Exception {
		return clientCore.deleteAll();
	}
	
	public List<Supplier> findSupplierByCode(String code) {
		try {
	    return clientCore.findSupplierByCode(code);
    } catch (Exception e) {
	    return new ArrayList<Supplier>();
    }
	}

	public Supplier saveSupplier(Supplier supplier) throws Exception {
		return clientCore.saveSupplier(supplier);
	}

	public boolean deleteSupplier(Supplier supplier) throws Exception {
		return clientCore.deleteSupplier(supplier);
	}

	public List<Supplier> getSuppliers(boolean interactive){
		try {
			return clientCore.getSuppliers(interactive);
    } catch (Exception e) {
    	return new ArrayList<Supplier>();
    }
		
	}
	
	public List<Supplier> findSuppliersByAccountGroup(AccountGroup accountGroup) throws Exception {
	  List<Supplier> supplier = new ArrayList<Supplier>();
    List<AccountMembership> accountMembership = AccountModelManager.getInstance().findMembershipByGroupPath(accountGroup.getPath());
    for(AccountMembership am : accountMembership){
      Supplier s = getBydLoginId(am.getLoginId());
      supplier.add(s);
    }
    return supplier;
	}
	
	public AccountGroup getRootGroupSupplier() {
		try {
			AccountGroup rootGroupSupplier = AccountModelManager.getInstance().getGroupByPath(ManagerAuthenticate.getInstance().getOrganizationLoginId() + "/" + AccountModelManager.Supplier);
			if (rootGroupSupplier == null) {
				AccountGroup rootOrganization = AccountModelManager.getInstance().getRootGroupDetail().getChildren().get(0);
				rootGroupSupplier = new AccountGroup();
				rootGroupSupplier.setLabel("Nhóm nhà cung cấp");
				rootGroupSupplier.setName(AccountModelManager.Supplier);
				rootGroupSupplier.setOwner(ManagerAuthenticate.getInstance().getOrganizationLoginId());
				rootGroupSupplier.setParent(rootOrganization);
				rootGroupSupplier.setDescription("Nút gốc nhóm nhà cung cấp");
				rootGroupSupplier = AccountModelManager.getInstance().saveGroup(rootGroupSupplier);
			}
			return rootGroupSupplier;
		} catch (Exception ex) {
			return null;
		}
	}
	
	public Supplier getBydLoginId(String supplierLoginId) throws Exception {
	  return clientCore.getBydLoginId(supplierLoginId, ManagerAuthenticate.getInstance().getOrganizationLoginId());
	}
	
	public Supplier getSupplierByCode(String code) throws Exception {
	  return clientCore.getSupplierByCode(code);
	}

	public List<Supplier> getAllSuppliers() throws Exception {
		return clientCore.getAllSuppliers();
	}

	public void deleteTest(String test) throws Exception {
		clientCore.deleteTest(test);
	}
}
