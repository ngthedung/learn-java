package com.hkt.client.swingexp.app.hethong;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.module.account.entity.Profile;

@SuppressWarnings("serial")
public class PanelSetupPermission extends JPanel implements IDialogResto {

	private JLabel				lbdelete;
	private boolean				delete;
	private JCheckBox			chbdelete;
	private String name;

	public boolean isDelete() {
		return delete;
	}

	public PanelSetupPermission(String name) {
		try {
			this.name = name.substring(0, name.indexOf("*"));
    } catch (Exception e) {
    	this.name = name;
    }
		
		setOpaque(false);

		lbdelete = new javax.swing.JLabel();
		chbdelete = new javax.swing.JCheckBox();
		chbdelete.setName("chbdelete");
		lbdelete.setFont(new java.awt.Font("Tahoma", 1, 14));
		chbdelete.setFont(new java.awt.Font("Tahoma", 1, 14));
		chbdelete.setOpaque(false);

		lbdelete.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		lbdelete.setText("");

		chbdelete.setText("Chỉ sử dụng dữ liệu mình tạo ra!");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout
						.createSequentialGroup()
						.addGap(100, 100, 100)
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(chbdelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
										.addComponent(lbdelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)).addContainerGap(35, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
				layout.createSequentialGroup().addComponent(lbdelete, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(chbdelete).addContainerGap(41, Short.MAX_VALUE)));
		Profile p = AccountModelManager.getInstance().getProfileConfigAdmin();
		
		if (p.get(name) != null && p.get(name).equals("true")) {
			chbdelete.setSelected(true);
		} else {
			chbdelete.setSelected(false);
		}
	}

	@Override
	public void Save() throws Exception {
		delete = true;
		Profile p = AccountModelManager.getInstance().getProfileConfigAdmin();
		if (chbdelete.isSelected()) {
			p.put(name, "true");
		} else {
			p.put(name, "false");
		}

		AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(), p);
		((JDialog) getRootPane().getParent()).dispose();

	}

}
