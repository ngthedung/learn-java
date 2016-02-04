package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.hkt.client.swingexp.app.component.ExtendJCheckBox;
import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.Table;

public class PanelDeleteTableLocation extends JPanel implements IDialogResto {
	private JPanel	PANEL1, PANEL2;
	private JPanel	LINE1, LINE2, LINE3;
	private ExtendJLabel	lblDeleteTable, lblDeleteLocation;
	private ExtendJCheckBox	chbDeleteAllTableInLocation, chbDeleteLocationOther;
	private ExtendJComboBox	cbDeleteAllTableInLocation, cbDeleteLocation;
	private JRadioButton		rdTable, rdLocation;
	private ButtonGroup			grpRadioButton;
	private Table						table;
	private List<Location>	locations;
	private boolean					isSave;
	public static String		permission;

	public PanelDeleteTableLocation(Table _table) {
		this.table = _table;
		grpRadioButton = new ButtonGroup();
		init();

		try {
			locations = RestaurantModelManager.getInstance().getLocations();
		} catch (Exception e1) {
			locations = new ArrayList<Location>();
		}
		cbDeleteAllTableInLocation.setModel(new DefaultComboBoxModel(locations.toArray()));
		cbDeleteLocation.setModel(new DefaultComboBoxModel(locations.toArray()));

		cbDeleteAllTableInLocation.setSelectedItem(getLocationByTable());
		cbDeleteLocation.setSelectedItem(getLocationByTable());

		rdTable.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (rdTable.isSelected()) {
					setEnabledDelTable(true);
					chbDeleteLocationOther.setSelected(false);
					cbDeleteLocation.setEnabled(false);
				} else {
					setEnabledDelTable(false);
				}
			}
		});

		chbDeleteAllTableInLocation.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (chbDeleteAllTableInLocation.isSelected())
					cbDeleteAllTableInLocation.setEnabled(true);
				else
					cbDeleteAllTableInLocation.setEnabled(false);
			}
		});

		rdLocation.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (rdLocation.isSelected()) {
					setEnabledDelLocation(true);
					cbDeleteAllTableInLocation.setEnabled(false);
					chbDeleteAllTableInLocation.setSelected(false);
				} else {
					setEnabledDelLocation(false);
				}
			}
		});

		chbDeleteLocationOther.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (chbDeleteLocationOther.isSelected())
					cbDeleteLocation.setEnabled(true);
				else
					cbDeleteLocation.setEnabled(false);
			}
		});
	}

	public void init() {
		this.setLayout(new GridLayout(2, 1, 0, 0));
		this.setOpaque(false);
		this.setPreferredSize(new Dimension(500, 100));

		PANEL1 = new JPanel();
		PANEL2 = new JPanel();

		LINE1 = new JPanel();
		LINE2 = new JPanel();
		LINE3 = new JPanel();

		lblDeleteTable = new ExtendJLabel("Bạn có muốn xóa bàn " + table.getName().toUpperCase() + " ?");
		lblDeleteLocation = new ExtendJLabel("Bạn có muốn xóa khu vực " + getLocationByTable().getName() + " ?");
		chbDeleteAllTableInLocation = new ExtendJCheckBox("Xóa tất cả bàn/quầy trong khu vực");
		chbDeleteLocationOther = new ExtendJCheckBox("Xóa khu vực khác");
		cbDeleteAllTableInLocation = new ExtendJComboBox();
		cbDeleteLocation = new ExtendJComboBox();
		rdTable = new JRadioButton("Xóa bàn/quầy");
		rdLocation = new JRadioButton("Xóa khu vực");
		grpRadioButton.add(rdTable);
		grpRadioButton.add(rdLocation);

		rdTable.setFont(new Font("Tahoma", Font.BOLD, 14));
		rdLocation.setFont(new Font("Tahoma", Font.BOLD, 14));
		rdTable.setOpaque(false);
		rdLocation.setOpaque(false);
		lblDeleteTable.setPreferredSize(new Dimension(300, 22));
		lblDeleteLocation.setPreferredSize(new Dimension(300, 22));

		LINE1.setLayout(new BorderLayout(0, 0));
		LINE1.setOpaque(false);
		LINE1.add(rdTable, BorderLayout.LINE_START);

		LINE2.setLayout(new BorderLayout(0, 0));
		LINE2.setOpaque(false);
		LINE2.add(lblDeleteTable, BorderLayout.LINE_START);

		LINE3.setLayout(new BorderLayout(0, 0));
		LINE3.setOpaque(false);
		LINE3.add(chbDeleteAllTableInLocation, BorderLayout.LINE_START);
		LINE3.add(cbDeleteAllTableInLocation, BorderLayout.LINE_END);

		PANEL1.setLayout(new GridLayout(4, 1, 5, 5));
		PANEL1.setOpaque(false);
		PANEL1.setPreferredSize(new Dimension(450, 100));
		PANEL1.add(LINE1);
		PANEL1.add(LINE2);
		PANEL1.add(LINE3);

		this.add(PANEL1);

		LINE1 = new JPanel();
		LINE2 = new JPanel();
		LINE3 = new JPanel();

		LINE1.setLayout(new BorderLayout(0, 0));
		LINE1.setOpaque(false);
		LINE1.add(rdLocation, BorderLayout.LINE_START);

		LINE2.setLayout(new BorderLayout(0, 0));
		LINE2.setOpaque(false);
		LINE2.add(lblDeleteLocation, BorderLayout.LINE_START);

		LINE3.setLayout(new BorderLayout(0, 0));
		LINE3.setOpaque(false);
		LINE3.add(chbDeleteLocationOther, BorderLayout.LINE_START);
		LINE3.add(cbDeleteLocation, BorderLayout.LINE_END);

		PANEL2.setLayout(new GridLayout(4, 1, 5, 5));
		PANEL2.setOpaque(false);
		PANEL2.setPreferredSize(new Dimension(450, 100));
		PANEL2.add(LINE1);
		PANEL2.add(LINE2);
		PANEL2.add(LINE3);

		this.add(PANEL2);

		setEnabledDelLocation(false);
		setEnabledDelTable(false);
		cbDeleteAllTableInLocation.setEnabled(false);
		cbDeleteLocation.setEnabled(false);
	}

	private void setEnabledDelTable(boolean value) {
		lblDeleteTable.setEnabled(value);
		chbDeleteAllTableInLocation.setEnabled(value);
	}

	private void setEnabledDelLocation(boolean value) {
		lblDeleteLocation.setEnabled(value);
		chbDeleteLocationOther.setEnabled(value);
	}

	private Location getLocationByTable() {
		try {
			return RestaurantModelManager.getInstance().getLocationByCode(table.getLocationCode());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void Save() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.ADMIN) {
			// Xóa bàn quầy
			if (rdTable.isSelected()) {
				if (chbDeleteAllTableInLocation.isSelected()) {
					List<Table> tables = RestaurantModelManager.getInstance().findTableByLocation(((Location) cbDeleteAllTableInLocation.getSelectedItem()).getCode());
					for (Table t : tables) {
						RestaurantModelManager.getInstance().deleteTable(t);
					}
				} else {
					RestaurantModelManager.getInstance().deleteTable(table);
				}
				setSave(true);
				((JDialog) getRootPane().getParent()).dispose();
			} else {
				// Xóa khu vực
				if (rdLocation.isSelected()) {
					RestaurantModelManager.getInstance().deleteLocation((Location) cbDeleteLocation.getSelectedItem());
					setSave(true);
					((JDialog) getRootPane().getParent()).dispose();
				} else
					setSave(false);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			return;
		}
	}

	public boolean isSave() {
		return isSave;
	}

	public void setSave(boolean isSave) {
		this.isSave = isSave;
	}

	public boolean isDeleteTable() {
		return rdTable.isSelected();
	}

	public boolean isDeleteLocation() {
		return rdLocation.isSelected();
	}

	public boolean isDeleteAllTableInLocation() {
		return chbDeleteAllTableInLocation.isSelected();
	}

	public Location getDeleteLocation() {
		return (Location)cbDeleteLocation.getSelectedItem();
	}
	
	public Location getLocationDeleteAllTable() {
		return (Location)cbDeleteAllTableInLocation.getSelectedItem();
	}
}
