package com.hkt.client.swingexp.app.core;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.GuiModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.module.account.entity.AccountGroup;

public class DepartmentJComboBox extends JComboBox<AccountGroup> {
	private List<AccountGroup>	accounts;

	public DepartmentJComboBox() {
		super();
		setFont(new Font("Tahoma", 0, 14));
		setOpaque(false);
		setPreferredSize(new Dimension(200, 23));
		resetData();
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 3) {
					new GuiModelManager<AccountGroup>().viewDialog(DepartmentJComboBox.this, AccountGroup.class);
					resetData();
				}
			}
		});
	}
	
	public void setConfig(){
		String departmentCode = "";
		try {
			FileReader fr = new FileReader(HKTFile.getFile("Database", "configPayment.json"));
			BufferedReader br = new BufferedReader(fr);
			String s;
			String jsonString = "";
			while((s = br.readLine()) != null){
				jsonString = jsonString + s;
			}
			br.close();
			fr.close();
			
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(jsonString);
			departmentCode = (String) obj.get("departmentCode");
		} catch(Exception ex){
//			ex.printStackTrace();
		}
		
		if(!departmentCode.equals("")){
			setSelectDepartmentByCode(departmentCode);
		} else {
			setSelectedIndex(0);
		}
	}

	public void resetData() {
		accounts = new ArrayList<AccountGroup>();

		try {
			AccountGroup rootDepartment = HRModelManager.getInstance().getRootDepartment();
			List<AccountGroup> getChildDepartment = AccountModelManager.getInstance().getAllChildrensByPath(rootDepartment.getPath());
			if (getChildDepartment != null)
				accounts.addAll(getChildDepartment);
		} catch (Exception e) {
			e.printStackTrace();
		}
		accounts.add(0, null);
		setModel(new DefaultComboBoxModel(accounts.toArray()));
		setEnabled(true);
	}

	public void setData(List<AccountGroup> accountGroups, String valueDisplayForAll) {
		accounts = accountGroups;
		AccountGroup accountGroup = new AccountGroup();
		accountGroup.setLabel(valueDisplayForAll);
		accounts.add(0, accountGroup);
		setModel(new DefaultComboBoxModel(accounts.toArray()));
		setEnabled(false);
	}

	public AccountGroup getSelectedDepartment() {
		try {
			return (AccountGroup) getSelectedItem();
		} catch (Exception e) {
			return null;
		}
	}

	public AccountGroup getItemAt(int index) {
		try {
			return accounts.get(index);
		} catch (Exception e) {
			return null;
		}
	}

	public void setSelectDepartmentByPath(String groupPart) {
		for (int i = 0; i < accounts.size(); i++) {
			if ((accounts.get(i) != null && accounts.get(i).getPath().equals(groupPart))) {
				setSelectedItem(accounts.get(i));
				break;
			}
		}
	}
	
	public void setSelectDepartmentByCode(String code) {
		for (int i = 0; i < accounts.size(); i++) {
			if ((accounts.get(i) != null && accounts.get(i).getName().equals(code))) {
				setSelectedItem(accounts.get(i));
				break;
			}
		}
	}

	public void setSelectDepartmentByIndex(int index) {
		setSelectedIndex(index);
	}

}
