package com.hkt.client.swingexp.app.core;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.HRServiceClient;
import com.hkt.client.swing.widget.model.AutoCompleteItem;
import com.hkt.client.swingexp.model.GenericOptionModelManager;
import com.hkt.client.swingexp.model.GuiModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.config.generic.Option;
import com.hkt.module.config.generic.OptionGroup;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.hr.entity.Employee;

public class HRJComboBox extends JComboBox<Employee> {
	private List<Employee> employees;

	public HRJComboBox() {
		super();
		setFont(new Font("Tahoma", 0, 14));
		setOpaque(false);
		setPreferredSize(new Dimension(200, 23));
		resetData();
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 3) {
					new GuiModelManager<AccountGroup>().viewDialog(
							HRJComboBox.this, AccountGroup.class);
					resetData();
				}
			}
		});
	}

	public void resetDataPermission() {

		employees = new ArrayList<Employee>();
		try {
			employees = HRModelManager.getInstance().getEmployees();
			for (int i = 0; i < employees.size();) {
				if (employees.get(i).getPermissionGroup() != null) {
					OptionGroup group = GenericOptionModelManager.getInstance()
							.getOptionGroup("accounting", "AccountingService",
									"contributor_role");
					Option option = group.getOption(employees.get(i)
							.getPermissionGroup());
					if (option.isPermission()) {
						employees.remove(i);
					} else {
						i++;
					}
				} else {
					i++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setModel(new DefaultComboBoxModel(employees.toArray()));
	}

	public void resetData() {

		employees = new ArrayList<Employee>();
		try {
			employees = HRModelManager.getInstance().getEmployees();
		} catch (Exception e) {
			e.printStackTrace();
		}
		employees.add(0, null);

		setModel(new DefaultComboBoxModel(employees.toArray()));
	}

	public void setData(List<Employee> employees) {
		this.employees = employees;
		this.employees.add(0, null);
		setModel(new DefaultComboBoxModel(this.employees.toArray()));
	}

	public void removeNull() {
		DefaultComboBoxModel model = (DefaultComboBoxModel) getModel();
		model.removeElementAt(0);
	}

	public Employee getSelectedEmployee() {
		try {
			return (Employee) getSelectedItem();
		} catch (Exception ex) {
			return null;
		}
	}

	public void setSelectedEmployee(String loginId) {
		for (int i = 1; i < employees.size(); i++) {
			if (employees.get(i) != null) {
				if (employees.get(i).getLoginId().equals(loginId)) {
					setSelectedIndex(i);
					break;
				}
			} else {
				setSelectedIndex(0);
				break;
			}
		}
	}
}
