package com.hkt.client.swingexp.app.core;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import com.hkt.client.swingexp.model.GuiModelManager;
import com.hkt.client.swingexp.model.WarehouseModelManager;
import com.hkt.module.warehouse.entity.Warehouse;

public class WarehousesJComboBox extends JComboBox<Warehouse> implements IMyObserver {

	public WarehousesJComboBox() {
		super();
		setFont(new Font("Tahoma", 0, 14));
		setOpaque(false);
		setPreferredSize(new Dimension(200, 23));

		resetData(true);
		final GuiModelManager<Warehouse> g = new GuiModelManager<Warehouse>();
		g.getObservable().addObserver(this);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 3) {
					g.viewDialog(WarehousesJComboBox.this, Warehouse.class);
					resetData(true);
				}
			}
		});
	}

	public WarehousesJComboBox(final boolean isNull) {
		super();
		setFont(new Font("Tahoma", 0, 14));
		setOpaque(false);
		setPreferredSize(new Dimension(200, 23));

		resetData(isNull);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 3) {
					new GuiModelManager<Warehouse>().viewDialog(WarehousesJComboBox.this, Warehouse.class);
					resetData(isNull);
				}
			}
		});
	}

	public void resetData(boolean isNull) {
		try {
			List<Warehouse> warehouses = WarehouseModelManager.getInstance().findWarehouses();
			if (isNull) {
				warehouses.add(0, null);
			}
			setModel(new DefaultComboBoxModel(warehouses.toArray()));
		} catch (Exception e) {
			setModel(new DefaultComboBoxModel(new ArrayList<Warehouse>().toArray()));
		}
	}

	public Warehouse getSelectedWarehouse() {
		try {
			return ((Warehouse) getSelectedItem());
		} catch (Exception e) {
			return null;
		}
	}

	public void setSelectWarehouse(String warehouseId) {
		for (int i = 1; i < getItemCount(); i++) {
			if ((((Warehouse) getItemAt(i)).getWarehouseId().equals(warehouseId))) {
				setSelectedIndex(i);
				break;
			}
		}
	}

	@Override
	public void update(Object o, Object arg) {
		resetData(true);

	}
}
