package com.hkt.client.swingexp.app.khuvucbanhang.list;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.ITableMain;
import com.hkt.client.swingexp.app.core.TableObservable;
import com.hkt.client.swingexp.app.hethong.PanelManageDelete;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.list.AreaJComboBoxDelete;
import com.hkt.client.swingexp.app.khuvucbanhang.PanelLocation;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.Table;


@SuppressWarnings("serial")
public class TableListLocation extends TableObservable implements ITableMain{
	private List<Location> locations;
	private TableModelLocation modelTable;

	
	public TableListLocation() {
		try {
			locations = RestaurantModelManager.getInstance().getLocations();
		} catch (Exception e) {
			e.printStackTrace();
			locations = new ArrayList<Location>();
		}
		modelTable = new TableModelLocation(new ArrayList<Location>());
		setModel(modelTable);
	}
	@Override
  public boolean isDelete() {
    return true;
  }
	@Override
	public void click() {
		Location location = (Location) this.getValueAt(this.getSelectedRow(), 2);
		try {
			PanelLocation panel = new PanelLocation();
			panel.setName("KhuVucMoi");
			panel.setData(location);
			DialogMain dialog = new DialogMain(panel);
			dialog.setName("dlKhuVucMoi");
			dialog.setIcon("bands1.png");
			dialog.showButton(false);
			dialog.setTitle("Khu vực mới");
			dialog.setModal(true);
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			location = RestaurantModelManager.getInstance().getLocationByCode(location.getCode());
			if (location == null || location.isRecycleBin()) {
				modelTable.removeRow(this.getSelectedRow());
			} else {
				modelTable.fireTableRowsUpdated(this.getSelectedRow(), this.getSelectedRow());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getListSize() {
		return locations.size();
	}

	@Override
	public List<Integer> getColumnSum() {
		return null;
	}

	@Override
	public DefaultTableModel loadTable(int index, int pageSize) {
		try {
			modelTable.setData(locations.subList(index, pageSize), index);
		} catch (Exception ex) {
			modelTable= new TableModelLocation(new ArrayList<Location>());
		}
		return modelTable;
	}

  @Override
  public boolean delete() {
	  if (UIConfigModelManager.getInstance().getPermission(
				PanelLocation.permission) == Capability.ADMIN) {
			try {
				String str = "Xóa tất cả danh sách đã chọn ";
				PanelChoise panel = new PanelChoise(str + " ?");
				panel.setName("Xoa");
				DialogResto dialog = new DialogResto(panel, false, 0, 80);
				dialog.setName("dlXoa");
				dialog.setTitle("Xóa khu vực");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
				if (panel.isDelete()) {
					for (int i = 0; i < locations.size();) {
						Location local = locations.get(i);
						if (local.isRecycleBin()) {
							local.setRecycleBin(false);
							showLocation(local);

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
  
  public void showLocation(Location location) throws Exception {
		if (RestaurantModelManager.getInstance().findTableByLocation(location.getCode()) == null || RestaurantModelManager.getInstance().findTableByLocation(location.getCode()).isEmpty()) {
			RestaurantModelManager.getInstance().deleteLocation(location);
			((JDialog) getRootPane().getParent()).dispose();
		} else {
			try {
				PanelManageDelete<Table> panel = new PanelManageDelete<Table>(location.getName(), RestaurantModelManager.getInstance().findTableByLocation(location.getCode()), new AreaJComboBoxDelete(
						location));
				panel.setName("XoaKhuVuc");
				DialogResto dialog = new DialogResto(panel, true, 600, 100);
				dialog.setName("dlXoaKhuVuc");
				dialog.dispose();
				dialog.setUndecorated(true);
				dialog.setTitle("Quản lý xóa đối tượng");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
			} catch (Exception e2) {
				e2.printStackTrace();
			}

			if (RestaurantModelManager.getInstance().findTableByLocation(location.getCode()) == null || RestaurantModelManager.getInstance().findTableByLocation(location.getCode()).isEmpty()) {
				RestaurantModelManager.getInstance().deleteLocation(location);
				((JDialog) getRootPane().getParent()).dispose();
			}
		}
	}
	
}
