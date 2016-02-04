package com.hkt.client.swingexp.model;

import java.util.HashMap;

import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.license.LicenseManager;
import com.hkt.client.swingexp.app.nhansu.PanelPermission;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.hr.entity.Employee;

public class UIConfigModelManager {
	private static UIConfigModelManager instance;

	private HashMap<String, Capability> hashMap = new HashMap<String, Capability>();
	private String permission;
	private String str;

	public void loadPermission() {
		try {
			Employee employee = HRModelManager.getInstance().getBydLoginId(ManagerAuthenticate.getInstance().getLoginId());
			if (employee.getPermissionGroup() != null) {
				permission = employee.getPermissionGroup();
			} else {
				permission = ManagerAuthenticate.getInstance().getLoginId();
			}
		} catch (Exception e) {
			permission = ManagerAuthenticate.getInstance().getLoginId();
		}
		str = AccountModelManager.getInstance().getEmailByLoginId(ManagerAuthenticate.getInstance().getLoginId());
	}

	private UIConfigModelManager() {

	}

	static public UIConfigModelManager getInstance() {
		if (instance == null) {
			instance = new UIConfigModelManager();
		}
		return instance;
	}

	public void setPermission(String screen) {
		try {
			String path = ManagerAuthenticate.getInstance().getOrganizationLoginId() + "/"+permission+"/"+screen;
			AccountGroup group = AccountModelManager.getInstance().getGroupByPath(path);
			if(group.getPath()!=null && !group.getPath().trim().isEmpty()){
				AccountMembership a = AccountModelManager.getInstance().getMembershipByAccountAndGroup(permission, path);
				hashMap.put(screen, a.getCapability());
			}
//			 
//			System.out.println(AccountModelManager.getInstance().findMembershipByAccountLoginId(permission));
//			Capability c = clientCore.getPermission(ManagerAuthenticate.getInstance().getOrganizationLoginId(), permission,
//			    screen);
//			System.out.println(screen+"   "+c);
			
		} catch (Exception e) {
			hashMap.put(screen, null);
		}
	}

	public Capability getPermission(String screen) {
		if (str.equals("admin@gmail.com")) {
			return Capability.ADMIN;
		}else {
			if (hashMap.get(screen) == null) {
				if (AccountModelManager.getInstance().getProfileConfigAdmin().get("Usb") != null) {
					if (LicenseManager.getInstance().isAdmin()) {
						return Capability.ADMIN;
					}
				} else {
					PanelPermission.getInstance().isPermisson(screen);
					return PanelPermission.getInstance().getC();
				}
			}
			
			return hashMap.get(screen);
		}
		
	}

	public String getPlainText(String html) {
		String htmlBody = html.replaceAll("<br>", " ");
		htmlBody = htmlBody.replaceAll("</br>", "");
		String plainTextBody = htmlBody.replaceAll("<[^<>]+>([^<>]*)<[^<>]+>", "$1");
		return plainTextBody;
	}

}
