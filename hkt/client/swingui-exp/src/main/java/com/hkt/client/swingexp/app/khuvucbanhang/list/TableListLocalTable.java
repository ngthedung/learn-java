package com.hkt.client.swingexp.app.khuvucbanhang.list;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.ITableMain;
import com.hkt.client.swingexp.app.core.TableObservable;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.khuvucbanhang.PanelLocalTable;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.restaurant.entity.Table;

@SuppressWarnings("serial")
public class TableListLocalTable extends TableObservable implements ITableMain {
	private List<Table> tables;
	private TableModelLocalTable modelTable;

	//Hàm tạo class
	public TableListLocalTable() {
		try {
			tables = RestaurantModelManager.getInstance().getTables();
		} catch (Exception e) {
			e.printStackTrace();
			tables = new ArrayList<Table>();
		}
		modelTable = new TableModelLocalTable(new ArrayList<Table>());
		setModel(modelTable);
	}
	

	//Phương thức cho sự kiện click chuột vào dòng trong bảng
	@Override
	public void click() {
		Table table = (Table) this.getValueAt(this.getSelectedRow(), 2);
		try {
			PanelLocalTable panel = new PanelLocalTable();
			panel.setData(table);
			panel.setName("ThemMoiBanQuay");
			DialogMain dialog = new DialogMain(panel);
			dialog.setIcon("bands1.png");
			dialog.setName("dlThemMoiBanQuay");
			dialog.setTitle("Thêm mới bàn/quầy");
			dialog.showButton(false);
			dialog.setModal(true);
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			table = RestaurantModelManager.getInstance().getTableByCode(table.getCode());
			if (table == null || table.isRecycleBin()) {
				modelTable.removeRow(this.getSelectedRow());
			} else {
				modelTable.fireTableRowsUpdated(this.getSelectedRow(), this.getSelectedRow());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	//Kích thước danh sách Table
	@Override
	public int getListSize() {
		return tables.size();
	}

	@Override
	public List<Integer> getColumnSum() {
		// List<Integer> list = new ArrayList<Integer>();
		// list.add(7);
		// list.add(8);
		// return list;
		return null;
	}

	//Load lại dữ liệu lên bảng
	@Override
	public DefaultTableModel loadTable(int index, int pageSize) {
		try {
			modelTable.setData(tables.subList(index, pageSize), index);
		} catch (Exception ex) {
			modelTable = new TableModelLocalTable(new ArrayList<Table>());
		}
		return modelTable;
	}


  @Override
  public boolean delete() {
	  if (UIConfigModelManager.getInstance().getPermission(
				PanelLocalTable.permission) == Capability.ADMIN) {
			try {
				String str = "Xóa tất cả danh sách đã chọn ";
				PanelChoise panel = new PanelChoise(str + " ?");
				panel.setName("Xoa");
				DialogResto dialog = new DialogResto(panel, false, 0, 80);
				dialog.setName("dlXoa");
				dialog.setTitle("Xóa bàn/quầy");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
				if (panel.isDelete()) {
					for (int i = 0; i < tables.size();) {
						Table tab = tables.get(i);
						
						if (tab.isRecycleBin()) {
							tab.setRecycleBin(false);
							tables.remove(i);
							RestaurantModelManager.getInstance().deleteTable(tab);
							
						} else {
							i++;
						}
					}
				}

			} catch (Exception ex) {
			}

			change();

		} else {
			JOptionPane
					.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			return false;
		}
    return false;
  }
  
  @Override
  public boolean isDelete() {
    return true;
  }

}
