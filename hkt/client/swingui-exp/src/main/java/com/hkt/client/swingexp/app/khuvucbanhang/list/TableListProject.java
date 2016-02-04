package com.hkt.client.swingexp.app.khuvucbanhang.list;

import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.ITableMain;
import com.hkt.client.swingexp.app.core.TableObservable;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.khuvucbanhang.PanelProject;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.core.entity.ReportTable;
import com.hkt.module.core.entity.SQLSelectQuery;
import com.hkt.module.restaurant.entity.Project;

@SuppressWarnings("serial")
public class TableListProject extends TableObservable implements ITableMain {

	private TableModeProjectTable modelTable;
	private JCheckBox c, c1;
	private List<Map<String, Object>> records = new ArrayList<Map<String, Object>>();

	// hàm tạo class
	public TableListProject() {

		modelTable = new TableModeProjectTable();
		setModel(modelTable);

	}

	public void loadData() {

		try {
			SQLSelectQuery rQuery = new SQLSelectQuery();

			rQuery.table("restaurant_project i").field("i.id AS `id`", "id").field("i.code AS `code`", "code")
			    .field("i.name AS `name`", "name")
			    .field("(select `name` from `restaurant_project` where `code`= i.parentCode) AS `project`", "project")
			    .field("(select `label` from `accountGroup` where `path`= i.departmentPart) AS `department`", "department")
			    .field("i.description AS `description`", "description").field("i.formDate AS `formDate`", "formDate")
			    .field("i.toDate AS `toDate`", "toDate").field("i.status AS `status`", "status");
			if (c!=null && c1!=null && !c.isSelected() && !c1.isSelected()) {
				rQuery.cond("i.recycleBin = 1");
			}else {
				rQuery.cond("i.recycleBin = 0");
			}
			
			if (c!=null && c1!=null && c.isSelected() && !c1.isSelected()) {
				rQuery.cond("i.status = 'Đang thực hiện' ORDER BY i.id DESC");
			}

			if (c!=null && c1!=null && c1.isSelected() && !c.isSelected()) {
				rQuery.cond("i.status = 'Đã hoàn thành' ORDER BY i.id DESC");
			}
			if (c!=null && c1!=null && c1.isSelected() && c.isSelected()) {
				rQuery.cond("i.status is not null ORDER BY i.id DESC");
			}
			System.out.println(rQuery.createSQLQuery());
			ReportTable[] reportTable = AccountingModelManager.getInstance().reportQuery(new SQLSelectQuery[] { rQuery });

			records = reportTable[0].getRecords();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void cutData(int index, int pageSize) {
		// "STT", "Mã dự án", "Tên dự án", "Dự án cha", "Phòng ban", "Miêu tả",
		// "Ngày bắt đầu",
		// "Ngày kết thúc", "Tình trạng",
		String[] field = new String[] { "Stt", "code", "name", "project", "department", "description", "formDate",
		    "toDate", "status" };
		for (int i = index; i < pageSize; i++) {
			Map<String, Object> record = records.get(i);
			Object[] values = new Object[field.length];
			for (int j = 0; j < field.length; j++) {
				if (j == 0) {
					values[j] = i + 1;
				} else {
					if (j == 1) {
						values[j] = record.get(field[j]).toString();
						if (values[j].toString().indexOf(":") > 0) {
							values[j] = values[j].toString().substring(values[j].toString().indexOf(":") + 1);
						}
					} else {
						if(j == 6 || j==7){
							try {
								values[j] = record.get(field[j]).toString().substring(0, 10);
              } catch (Exception e) {
              	values[j] = record.get(field[j]);
              }
							
						}else {
							values[j] = record.get(field[j]);
						}
						
					}
				}
			}
			modelTable.addRow(values);
		}
	}

	public JPanel getPanelCheckBox() {
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new GridLayout(1, 2));
		c = new JCheckBox("Đang thực hiện");
		c.setSelected(true);
		c1 = new JCheckBox("Đã hoàn thành");
		c.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				loadData();
				change();
			}
		});
		c.setOpaque(false);
		c1.setOpaque(false);
		panel.add(c);
		c1.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				loadData();
				change();
			}
		});
		panel.add(c1);
		loadData();
		change();
		return panel;
	}

	// Phương thức cho sự kiện click chuột vào dòng trong bảng
	@Override
	public void click() {
		Map<String, Object> record = records.get(Integer.parseInt(this.getValueAt(getSelectedRow(), 0).toString()) - 1);
		String code = record.get("id").toString();
		Project pro = RestaurantModelManager.getInstance().getProjectById(Long.parseLong(code));
		try {
			PanelProject panel = new PanelProject();
			panel.setData(pro);
			panel.setName("Duan");
			DialogMain dialog = new DialogMain(panel);
			dialog.setName("dlThemMoiDuAn");
			dialog.setIcon("duan1.png");
			dialog.setTitle("Dự án mới");
			dialog.showButton(false);
			dialog.setModal(true);
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
			loadData();
			change();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public int getListSize() {
		return records.size();
	}

	@Override
	public List<Integer> getColumnSum() {
		return null;
	}

	@Override
	public DefaultTableModel loadTable(int index, int pageSize) {
		int k = modelTable.getRowCount();
		while (k > 0) {
			modelTable.removeRow(k - 1);
			k--;

		}
		cutData(index, pageSize);
		modelTable.setSize(index);
		return modelTable;
	}

	@Override
	public boolean delete() {
		if (UIConfigModelManager.getInstance().getPermission(PanelProject.permission) == Capability.ADMIN) {
			try {
				String str = "Xóa tất cả danh sách đã chọn ";
				PanelChoise panel = new PanelChoise(str + " ?");
				panel.setName("Xoa");
				DialogResto dialog = new DialogResto(panel, false, 0, 80);
				dialog.setName("dlXoa");
				dialog.setTitle("Xóa dự án");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
				if (panel.isDelete()) {
					int colum = this.getColumnCount() - 1;
					for (int i = 0; i < this.getRowCount(); i++) {
						if (this.getValueAt(i, colum).toString().equals("true")) {
							Map<String, Object> record = records.get(i);
							String code = record.get("id").toString();
							Project invoiceDetail = RestaurantModelManager.getInstance().getProjectById(Long.parseLong(code));
							RestaurantModelManager.getInstance().deleteProject(invoiceDetail);
						}
					}
				}

			} catch (Exception ex) {
			}
			loadData();
			change();

		} else {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			return false;
		}
		return false;
	}

	@Override
	public boolean isDelete() {
		return true;
	}

}
