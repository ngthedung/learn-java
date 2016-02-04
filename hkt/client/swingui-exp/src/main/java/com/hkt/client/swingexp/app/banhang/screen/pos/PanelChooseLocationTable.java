package com.hkt.client.swingexp.app.banhang.screen.pos;

import javax.swing.JDialog;
import javax.swing.JPanel;

import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.core.AreaJComboBox;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.core.TableJComboBox;
import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.Table;

public class PanelChooseLocationTable extends JPanel implements IDialogResto {
	private AreaJComboBox		cbLocation;
	private TableJComboBox	cbTable;
	private boolean					isSave	= false;
	
	public PanelChooseLocationTable() {
		init();
	}
	
	private void init() {
		cbLocation = new AreaJComboBox();
		cbLocation.dataMarket();
		cbTable = new TableJComboBox();
		cbTable.dataMarket();
		cbLocation.setCboTablesInLocation(cbTable);		
		ExtendJLabel lblLocation = new ExtendJLabel("Khu vực");
		ExtendJLabel lblTable = new ExtendJLabel("Bàn quầy");
		
		MyGroupLayout groupLayout = new MyGroupLayout(this);
		groupLayout.add(0, 0, lblLocation);
		groupLayout.add(0, 1, cbLocation);
		groupLayout.add(1, 0, lblTable);
		groupLayout.add(1, 1, cbTable);
		groupLayout.updateGui();
	}
	
	protected Location getLocationSelected() {
		return cbLocation.getSelectedLocation();
	}
	
	protected Table getTableSelected() {
		return cbTable.getSelectedTable();
	}
	
	protected boolean isClickSave(){
		return isSave;
	}
	
	@Override
	public void Save() throws Exception {
		isSave = true;
		((JDialog)(this.getRootPane().getParent())).dispose();
	}
	
}
