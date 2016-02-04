package com.hkt.client.swingexp.app.core;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import com.hkt.client.swingexp.model.GuiModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.Table;

public class AreaJComboBox extends JComboBox<Location> implements ItemListener {
	private List<Location>	locations;
	private TableJComboBox	tableJComboBox;
	
	public AreaJComboBox() {
		super();
		setFont(new Font("Tahoma", 0, 14));
		setOpaque(false);
		setPreferredSize(new Dimension(200, 23));
		resetData(false);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 3) {
					new GuiModelManager<Location>().viewDialog(AreaJComboBox.this, Location.class);
					resetData(false);
				}
			}
		});
	}
	
	public AreaJComboBox(final boolean notNull) {
		super();
		setFont(new Font("Tahoma", 0, 14));
		setOpaque(false);
		setPreferredSize(new Dimension(200, 23));
		resetData(notNull);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 3) {
					new GuiModelManager<Location>().viewDialog(AreaJComboBox.this, Location.class);
					resetData(notNull);
				}
			}
		});
	}
	
	public void dataMarket() {
		locations = new ArrayList<Location>();
		try {
			locations = RestaurantModelManager.getInstance().getLocations();
		} catch (Exception e) {
		}
		locations.add(0, RestaurantModelManager.getInstance().getLocationPaymentAfter());
		setModel(new DefaultComboBoxModel(locations.toArray()));
	}
	
	public void setCboTablesInLocation(TableJComboBox tableJComboBox) {
		this.tableJComboBox = tableJComboBox;
		this.addItemListener(this);
		if(this.getSelectedIndex() == 0 && this.getSelectedItem() != null){
			List<Table> tables = new ArrayList<Table>();
			tables.add(0, RestaurantModelManager.getInstance().getTablePaymentAfter());
			tableJComboBox.setModel(new DefaultComboBoxModel(tables.toArray()));
		} else {
			try {
				List<Table> tables = RestaurantModelManager.getInstance().findTableByLocation(this.getSelectedLocation().getCode());
				tableJComboBox.setModel(new DefaultComboBoxModel(tables.toArray()));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void resetData(boolean notNull) {
		locations = new ArrayList<Location>();
		try {
			locations = RestaurantModelManager.getInstance().getLocations();
		} catch (Exception e) {
		}
		if (!notNull)
			locations.add(0, null);
		setModel(new DefaultComboBoxModel(locations.toArray()));
	}
	
	public void setData(List<Location> listLocation) {
		this.locations = listLocation;
		setModel(new DefaultComboBoxModel(this.locations.toArray()));
	}
	
	public void removeIndex(int index) {
		this.locations.remove(index);
		setModel(new DefaultComboBoxModel(this.locations.toArray()));
	}
	
	@Override
	public Location getItemAt(int index) {
		return locations.get(index);
	}
	
	public Location getSelectedLocation() {
		try {
			return (Location) getSelectedItem();
		} catch (Exception ex) {
			return null;
		}
	}
	
	public void setSelectedLocation(String codeLocation) {
		for (int i = 1; i < locations.size(); i++) {
			if (locations.get(i).getCode().equals(codeLocation)) {
				this.setSelectedIndex(i);
				break;
			}
		}
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (tableJComboBox != null) {
			if(this.getSelectedIndex() == 0 && this.getSelectedItem() != null){
				List<Table> tables = new ArrayList<Table>();
				tables.add(0, RestaurantModelManager.getInstance().getTablePaymentAfter());
				tableJComboBox.setModel(new DefaultComboBoxModel(tables.toArray()));
			} else {
				try {
					List<Table> tables = RestaurantModelManager.getInstance().findTableByLocation(this.getSelectedLocation().getCode());
					tableJComboBox.setModel(new DefaultComboBoxModel(tables.toArray()));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
		}
	}
}
