package com.hkt.client.swingexp.app.banhang.list;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.module.account.entity.Profile;

@SuppressWarnings("serial")
public class PanelChooseProductExport extends JPanel implements IDialogResto {

	private JLabel				lbdelete;
	private boolean				delete;
	private JCheckBox			chbdelete;
	public static String	EXPORT	= "Xuất Kho";

	public boolean isDelete() {
		return delete;
	}

	public PanelChooseProductExport(String invoiceItem) {
		setOpaque(false);

		lbdelete = new javax.swing.JLabel();
		chbdelete = new javax.swing.JCheckBox();
		chbdelete.setName("chbdelete");
		lbdelete.setFont(new java.awt.Font("Tahoma", 1, 14));
		chbdelete.setFont(new java.awt.Font("Tahoma", 1, 14));
		chbdelete.setOpaque(false);

		lbdelete.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		lbdelete.setText("      Sản phẩm trong kho không đủ!");

		chbdelete.setText("Tiếp tục xuất không cần hỏi lại?");
		chbdelete.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				//TODO: Event handler cho checkBox không hỏi lại
			}
		});

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
		
		if (p.get(EXPORT) != null && p.get(EXPORT).equals("true")) {
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
			p.put(EXPORT, "true");
		} else {
			p.put(EXPORT, "false");
		}

		AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(), p);
		((JDialog) getRootPane().getParent()).dispose();

	}

}
