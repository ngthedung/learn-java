package com.hkt.client.swingexp.app.banhang;

/**
 * author Duy Khanh
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.hkt.client.swingexp.app.component.ExtendJButton;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.khuvucbanhang.list.TableModelInstituteLocation;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.LocationAttribute;

public class PanelInstituteLocation extends JPanel implements IDialogResto {

	private JPanel panelLeft, panelRight, panelCenter, panelTop;
	private JPanel panelButton;
	private ExtendJButton btnAddOne, btnAddAll, btnRemoveOne, btnRemoveAll;
	private JScrollPane scrollListLeft, scrollListRight;
	private TableModelInstituteLocation pnTableLeft, pnTableRight;
	private Location location;
	private List<Location> listLocationLeft = new ArrayList<Location>();
	private List<Location> listLocationRight = new ArrayList<Location>();
	private List<Employee> listEmployee = new ArrayList<Employee>();
	private IDialogResto iDialogResto;
	private String str;
	private javax.swing.JTable table1;
	private javax.swing.JTable table2;
	private javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();;
	private javax.swing.JScrollPane jScrollPane2 = new javax.swing.JScrollPane();;
	private javax.swing.JLabel lbEmployee;
	private javax.swing.JComboBox cboEmployee;
	private Employee employee;
	private List<Location> listLocationTemp = new ArrayList<Location>();

	public PanelInstituteLocation() {
		this.setLayout(new BorderLayout(6, 5));
		setOpaque(false);
		init();

		// lấy danh sách employee đổ vào cbo
		try {
			listEmployee = new ArrayList<Employee>();
			listEmployee = HRModelManager.getInstance().getEmployees();
			DefaultComboBoxModel lstEmp = new DefaultComboBoxModel(listEmployee.toArray());
			cboEmployee.setModel(lstEmp);

		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		employee = (Employee) cboEmployee.getSelectedItem();
		// lấy ra danh sách location với dkien có locaAtt giống với cboEmployee được
		// chọn
		try {
			listLocationRight = RestaurantModelManager.getInstance().findByAttribute("Employee",
			    ((Employee) cboEmployee.getSelectedItem()).getLoginId());
			// listLocationRight = new ArrayList<Location>();
			pnTableRight = new TableModelInstituteLocation(listLocationRight);
			table2 = new JTable(pnTableRight);
			table2.setFont(new Font("Tahoma", Font.PLAIN, 14));
			table2.setRowHeight(23);
			table2.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
			table2.getColumnModel().getColumn(0).setMaxWidth(50);
			jScrollPane2.setViewportView(table2);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			// Lấy danh sách Khu vực add vào đối tượng listLocationLeft
			listLocationLeft = RestaurantModelManager.getInstance().getLocations();
			// Đẩy danh sách listLocation vào TableModelInstituteLocation
			pnTableLeft = new TableModelInstituteLocation(listLocationLeft);
			table1 = new JTable(pnTableLeft);
			table1.setFont(new Font("Tahoma", Font.PLAIN, 14));
			table1.setRowHeight(23);
			table1.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
			table1.getColumnModel().getColumn(0).setMaxWidth(50);
			jScrollPane1.setViewportView(table1);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Bỏ 1 khu vực mà nhân viên có quyền vào
		btnRemoveOne.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				buttonRemove();
			}
		});
		// Thêm 1 khu vực cho nhân viên
		btnAddOne.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addProduct();
			}
		});
		// Thêm toàn bộ khu vực
		btnAddAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addAllProducts();
			}
		});
		// Bỏ toàn bộ khu vực
		btnRemoveAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeAllProducts();
			}
		});
	}

	private void init() {

		panelTop = new JPanel();
		panelLeft = new JPanel();
		panelRight = new JPanel();
		panelCenter = new JPanel();
		panelButton = new JPanel();

		panelCenter.setOpaque(false);
		panelButton.setOpaque(false);
		panelTop.setOpaque(false);

		// panelTop.setLayout(new GridLayout(1,0,50,10));
		panelTop.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelLeft.setLayout(new BorderLayout(0, 0));
		panelCenter.setLayout(new GridLayout(2, 0, 50, 0));
		panelRight.setLayout(new BorderLayout(0, 0));
		panelButton.setLayout(new GridLayout(4, 0, 0, 8));

		panelCenter.setPreferredSize(new Dimension(80, 100));
		panelTop.setPreferredSize(new Dimension(200, 40));

		lbEmployee = new JLabel("Nhân Viên");
		lbEmployee.setPreferredSize(new Dimension(70, 25));
		lbEmployee.setFont(new Font("Tahoma", Font.PLAIN, 14));

		cboEmployee = new JComboBox();
		cboEmployee.setPreferredSize(new Dimension(165, 25));
		cboEmployee.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAddOne = new ExtendJButton(">");
		btnAddAll = new ExtendJButton(">>");
		btnRemoveOne = new ExtendJButton("<");
		btnRemoveAll = new ExtendJButton("<<");
		btnAddOne.setName("btnAddOne");
		btnAddAll.setName("btnAddAll");
		btnRemoveOne.setName("btnRemoveOne");
		btnRemoveAll.setName("btnRemoveAll");

		cboEmployee.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				try {
					listLocationRight = RestaurantModelManager.getInstance().findByAttribute("Employee",
					    ((Employee) cboEmployee.getSelectedItem()).getLoginId());
				} catch (Exception e) {
					e.printStackTrace();
				}
				pnTableRight = new TableModelInstituteLocation(listLocationRight);
				table2 = new JTable(pnTableRight);
				table2.setFont(new Font("Tahoma", Font.PLAIN, 14));
				table2.setRowHeight(23);
				table2.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
				table2.getColumnModel().getColumn(0).setMaxWidth(50);
				jScrollPane2.setViewportView(table2);
			}

		});

		jScrollPane1.setPreferredSize(new Dimension(320, 80));
		jScrollPane2.setPreferredSize(new Dimension(320, 80));

		panelCenter.add(panelButton);
		panelButton.add(btnAddAll);
		panelButton.add(btnAddOne);
		panelButton.add(btnRemoveOne);
		panelButton.add(btnRemoveAll);

		panelRight.add(jScrollPane2, BorderLayout.CENTER);
		panelLeft.add(jScrollPane1, BorderLayout.CENTER);

		panelTop.add(lbEmployee);
		panelTop.add(cboEmployee);

		this.add(panelTop, BorderLayout.NORTH);
		this.add(panelLeft, BorderLayout.LINE_START);
		this.add(panelCenter, BorderLayout.CENTER);
		this.add(panelRight, BorderLayout.LINE_END);
	}

	private void buttonRemove() {
		if (cboEmployee.getSelectedIndex() >= 0) {
			try {
				Location row = ((Location) table2.getValueAt(table2.getSelectedRow(), 2));
				listLocationRight.remove(row);
				listLocationTemp.add(row);

				loadTable(listLocationLeft, listLocationRight);
			} catch (Exception e) {
			}
		}
	}

	private void addProduct() {
		if (cboEmployee.getSelectedIndex() >= 0) {
			try {
				Location row = (Location) table1.getValueAt(table1.getSelectedRow(), 2);
				if (listLocationRight.size() > 0) {
					boolean check = true;
					for (int i = 0; i < listLocationRight.size(); i++) {
						Location loca = listLocationRight.get(i);
						if (loca.getCode().equals(row.getCode())) {
							check = false;
							break;
						}
					}
					if (check) {
						listLocationRight.add(row);
						listLocationTemp.remove(row);

					}
					loadTable(listLocationLeft, listLocationRight);
				} else {
					listLocationRight.add(row);
					listLocationTemp.remove(row);

					loadTable(listLocationLeft, listLocationRight);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void addAllProducts() {
		if (cboEmployee.getSelectedIndex() >= 0) {
			if (listLocationRight.size() > 0) {
				for (int j = 0; j < listLocationLeft.size(); j++) {
					boolean check = true;
					Location locationLeft = listLocationLeft.get(j);
					for (int i = 0; i < listLocationRight.size(); i++) {
						Location locationRight = listLocationRight.get(i);
						if (locationRight.getCode().equals(locationLeft.getCode())) {
							check = false;
						}
					}
					if (check) {
						listLocationRight.add(locationLeft);
					}
				}
				loadTable(listLocationLeft, listLocationRight);
			} else {
				listLocationRight.addAll(listLocationLeft);
				loadTable(listLocationLeft, listLocationRight);
			}
		}
	}

	private void removeAllProducts() {
		if (cboEmployee.getSelectedIndex() >= 0) {
			listLocationRight.removeAll(listLocationRight);
			loadTable(listLocationLeft, listLocationRight);
		}
	}

	public void setParentForm(IDialogResto iDialogResto) {
		this.iDialogResto = iDialogResto;
	}

	public void loadTable(List<Location> listTableLeft, List<Location> listTableRight) {
		pnTableLeft = new TableModelInstituteLocation(listTableLeft);
		pnTableRight = new TableModelInstituteLocation(listTableRight);
		table1.setModel(pnTableLeft);
		table2.setModel(pnTableRight);
		table1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table1.setRowHeight(23);
		table1.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		table1.getColumnModel().getColumn(0).setMaxWidth(50);
		
		table2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table2.setRowHeight(23);
		table2.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		table2.getColumnModel().getColumn(0).setMaxWidth(50);
	}

	@Override
	public void Save() throws Exception {
		try {
			if (listLocationRight.size() > 0) {
				for (int i = 0; i < listLocationRight.size(); i++) {
					// nhân viên dc chọn
					employee = (Employee) cboEmployee.getSelectedItem();
					// danh sách 1 vị trí được chọn
					Location loca = listLocationRight.get(i);
					// danh sách các listAttribute của 1 Location
					List<LocationAttribute> listLocalAttribute = loca.getLocationAttributes();
					boolean check = false;
					int flag = 1;

					List<Location> listLocationFindByAttribute = new ArrayList<Location>();
					listLocationFindByAttribute = RestaurantModelManager.getInstance().findByAttribute("Employee",
					    employee.getLoginId());
					for (int lc = 0; lc < listLocationFindByAttribute.size(); lc++) {
						if (listLocationFindByAttribute.get(lc).getCode().equals(loca.getCode())) {
							flag = 0;
						}
						check = true;
					}

					if (check && flag == 1) {
						LocationAttribute locationtAtt = new LocationAttribute();
						locationtAtt.setName("Employee");
						locationtAtt.setValue(employee.getLoginId());
						listLocalAttribute.add(locationtAtt);
						loca.setLocationAttributes(listLocalAttribute);
						RestaurantModelManager.getInstance().saveLocation(loca);
					}

					if (!check) {
						LocationAttribute locationtAtt = new LocationAttribute();
						locationtAtt.setName("Employee");
						locationtAtt.setValue(employee.getLoginId());
						if (listLocalAttribute.size() == 0) {
							listLocalAttribute = new ArrayList<LocationAttribute>();
						}
						listLocalAttribute.add(locationtAtt);
						loca.setLocationAttributes(listLocalAttribute);
						RestaurantModelManager.getInstance().saveLocation(loca);

					}
				}
				if (listLocationTemp.size() > 0) {
					for (int k = 0; k < listLocationTemp.size(); k++) {
						Location locaTemp = listLocationTemp.get(k);
						// danh sách các listAttribute của 1 Location
						List<LocationAttribute> listLocalAttributeTemp = locaTemp.getLocationAttributes();
						//
						for (int l = 0; l < listLocalAttributeTemp.size(); l++) {
							LocationAttribute locationAtt = listLocalAttributeTemp.get(l);
							if (locationAtt != null) {
								String nameEmp = locationAtt.getName();
								String d = locationAtt.getValue();
								String loginIdEmp = employee.getLoginId();
								if (locationAtt.getName().equals(nameEmp) && locationAtt.getValue().equals(loginIdEmp)) {
									listLocalAttributeTemp.remove(locationAtt);
								}
							}
						}
						locaTemp.setLocationAttributes(listLocalAttributeTemp);
						RestaurantModelManager.getInstance().saveLocation(locaTemp);

					}
				}
				// ((JDialog)getRootPane().getParent()).dispose();
			} else {
				// trường hợp xóa tất khu vực của nhân viên
				employee = (Employee) cboEmployee.getSelectedItem();
				for (int i = 0; i < listLocationLeft.size(); i++) {
					Location loca = listLocationLeft.get(i);

					List<LocationAttribute> listAtt = loca.getLocationAttributes();
					for (int j = 0; j < listAtt.size(); j++) {
						LocationAttribute locationAtt = listAtt.get(j);
						if (locationAtt != null) {
							String nameEmp = locationAtt.getName();
							String d = locationAtt.getValue();
							String loginIdEmp = employee.getLoginId();
							if (locationAtt.getName().equals(nameEmp) && locationAtt.getValue().equals(loginIdEmp)) {
								listAtt.remove(locationAtt);
							}
						}
					}
					loca.setLocationAttributes(listAtt);
					RestaurantModelManager.getInstance().saveLocation(loca);
				}
			}
			DialogNotice.getInstace().setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
