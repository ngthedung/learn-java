package com.hkt.client.swingexp.app.hethong.list;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import com.hkt.client.swingexp.app.banhang.screen.often.ScreenOften;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.Table;

@SuppressWarnings("serial")
public class AreaJComboBoxDelete extends JComboBox<Location> implements IComboBoxDelete<Table> {
	private List<Location> locations;
	@SuppressWarnings("unused")
	private Location local;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AreaJComboBoxDelete(Location local) {
		this.local = local;

		locations = new ArrayList<Location>();

		try {
			locations = RestaurantModelManager.getInstance().getLocations();
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 1; i < locations.size();) {
			boolean children = false;
			if (locations.get(i).getCode().equals(local.getCode())) {
				children = true;
			}
			if (children) {
				locations.remove(i);
			} else {
				i++;
			}
		}
		setModel(new DefaultComboBoxModel(locations.toArray()));
	}

	public Location getSelectedType() {
		try {
			return (Location) getSelectedItem();
		} catch (Exception e) {
			return null;
		}

	}

	public void setSelectedLocation(String codeLocation) {
		for (int i = 0; i < locations.size(); i++) {
			if (locations.get(i).getCode().equals(codeLocation)) {
				this.setSelectedIndex(i);
				break;
			}
		}
	}

	@Override
	public boolean delete(List<Table> list) {
		try {
			for (int i = 0; i < list.size(); i++) {
				RestaurantModelManager.getInstance().deleteTable(list.get(i));
			}
			Thread t = new Thread() {
				public void run() {
					try {
						ScreenOften.getInstance().getPanelTable().refeshGui();
					} catch (Exception e) {
						System.out.println("loi load Table");
					}

				};
			};
			t.start();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void changeGroup(List<Table> list) {
		Location location = getSelectedType();
		try {
			for (int i = 0; i < list.size(); i++) {
				if (location != null) {
					list.get(i).setLocationCode(location.getCode());
				} else {
					list.get(i).setLocationCode(null);
				}
				RestaurantModelManager.getInstance().saveTable(list.get(i));
			}
			Thread t = new Thread() {
				public void run() {
					try {
						ScreenOften.getInstance().getPanelTable().refeshGui();
					} catch (Exception e) {
						System.out.println("loi load Table");
					}

				};
			};
			t.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
