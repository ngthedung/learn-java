package com.hkt.client.swingexp.app.banhang.screen.pos;

import javax.swing.JDialog;
import javax.swing.JPanel;

import com.hkt.client.swingexp.app.banhang.screen.pos.PanelScreenSaleLocationPos.ComponentTable;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.module.restaurant.entity.Table;

public class PanelRenameTable extends JPanel implements IDialogResto {
	private ExtendJLabel	lblPriceOld, lblPriceNew;
	private ExtendJTextField	txtTableOld, txtTableNew;
	private Table 					table;
	private boolean						edit;

	public PanelRenameTable(ComponentTable componentTable) {
		table = componentTable.getTable();
		
		lblPriceOld = new ExtendJLabel("Tên cũ");
		lblPriceNew = new ExtendJLabel("Tên mới");
		txtTableOld = new ExtendJTextField();
		txtTableNew = new ExtendJTextField();
		init();
		
		txtTableOld.setText(table.getName());
	}

	public void init() {
		this.setOpaque(false);
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(lblPriceOld).addComponent(lblPriceNew)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(txtTableOld, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE).addComponent(txtTableNew))));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lblPriceOld).addComponent(txtTableOld, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(4, 4, 4)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lblPriceNew, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(txtTableNew))));
		txtTableOld.setEditable(false);
	}

	@Override
	public void Save() {
		try {
			table.setName(txtTableNew.getText());
			table = RestaurantModelManager.getInstance().saveTable(table);
			edit = true;
			((JDialog) getRootPane().getParent()).dispose();
		} catch(Exception ex){
			edit = false;
		}
	}

	public boolean isEdit() {
		return edit;
	}

	public Table getTable() {
		return table;
	}
	
}
