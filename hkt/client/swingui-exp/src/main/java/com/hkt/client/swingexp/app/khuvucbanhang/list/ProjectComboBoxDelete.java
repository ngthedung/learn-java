package com.hkt.client.swingexp.app.khuvucbanhang.list;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import com.hkt.client.swingexp.app.hethong.list.IComboBoxDelete;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.restaurant.entity.Project;

@SuppressWarnings("serial")
public class ProjectComboBoxDelete extends JComboBox<Project> implements IComboBoxDelete<Project> {

	private List<Project> lstProject;
	private List<Project> lstAllProject;
	@SuppressWarnings("unused")
	private Project project;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ProjectComboBoxDelete(Project pro) {
		this.project = pro;
		lstProject = new ArrayList<Project>();
		lstAllProject = new ArrayList<Project>();
		try {
			lstAllProject = RestaurantModelManager.getInstance().getAllProject();
			for (int i = 0; i < lstAllProject.size(); i++) {
				if (lstAllProject.get(i).getParentCode().toString().equals("")
				    && !(lstAllProject.get(i).getCode()).equals(pro.getCode().toString())) {
					lstProject.add(lstAllProject.get(i));
				}
			}
		} catch (Exception e) {

		}

		for (int i = 0; i < lstProject.size();) {
			boolean children = false;
			if (lstProject.get(i).getCode().equals(pro.getCode())) {
				children = true;
				break;
			}
			if (children) {
				lstProject.remove(i);
			} else {
				i++;
			}
		}
		lstProject.add(0, null);
		setModel(new DefaultComboBoxModel(lstProject.toArray()));
	}

	public Project getSelectedType() {
		try {
			return (Project) getSelectedItem();
		} catch (Exception e) {
			return null;
		}

	}

	public void setSelectedProject(String codeProject) {
		for (int i = 0; i < lstProject.size(); i++) {
			if (lstProject.get(i).getCode().equals(codeProject)) {
				this.setSelectedIndex(i);
				break;
			}
		}
	}

	@Override
	public boolean delete(List<Project> list) {

		try {
			boolean flag9 = false;
			for (int i = 0; i < list.size(); i++) {
				for (int j = 1; j < lstProject.size(); j++) {
					if (list.get(i).getCode().toString().trim().equals(lstProject.get(j).getParentCode().toString().trim())) {
						flag9 = true;
						break;
					}
				}

				if (flag9 == true) {
					JOptionPane.showMessageDialog(null, "Dự án này có dự án con hãy xem lại trong danh sách!");
				} else {
					RestaurantModelManager.getInstance().deleteProject(list.get(i));
					return true;
				}
			}

			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public void changeGroup(List<Project> list) {
		// kiểm tra xem dự án sắp chuyển có dự án con ko, nếu có thì sẽ ko cho
		// chuyển và
		// ko cho phép chuyển con cha lẫn lộn

		Project project = getSelectedType();
		try {
			for (int i = 0; i < list.size(); i++) {
				if (project != null) {
					list.get(i).setParentCode(project.getCode());
				} else {
					list.get(i).setParentCode(null);
				}
				System.out.println("........");
				RestaurantModelManager.getInstance().saveProject(list.get(i));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
