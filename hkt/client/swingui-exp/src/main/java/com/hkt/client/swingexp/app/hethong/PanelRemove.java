package com.hkt.client.swingexp.app.hethong;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.hkt.client.swingexp.app.core.IDialogResto;

@SuppressWarnings("serial")
public class PanelRemove extends JPanel implements IDialogResto {
	private JLabel lbdelete;
	private boolean delete;
	
	public boolean isDelete() {
		return delete;
	}
	public PanelRemove(String str) {
		setOpaque(false);
		lbdelete = new javax.swing.JLabel();
		lbdelete.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		lbdelete.setText(str);
		lbdelete.setHorizontalAlignment(JLabel.CENTER);
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				lbdelete, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				lbdelete, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
				javax.swing.GroupLayout.PREFERRED_SIZE));
	}

	@Override
	public void Save() throws Exception {
		delete = true;
		((JDialog) getRootPane().getParent()).dispose();
	}
}
