package com.hkt.client.swingexp.app.banhang.screen.often;


import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.Profile;

@SuppressWarnings("serial")
public class PanelRounding extends JPanel implements IDialogResto {

	public static final String TronLen = "tronlen";
	public static final String TronPhay = "tronphay";

	@SuppressWarnings("rawtypes")
	private JComboBox cbLamTronLen, cbLamTronSauDauPhay;
	private JRadioButton rdTronLen, rdTronSauDauPhay;
	private ButtonGroup buttonGroup1;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PanelRounding() {
		setOpaque(false);
		buttonGroup1 = new javax.swing.ButtonGroup();
		rdTronLen = new JRadioButton();
		rdTronLen.setSelected(true);
		rdTronSauDauPhay = new JRadioButton();
		cbLamTronLen = new JComboBox();
		cbLamTronLen.setModel(new DefaultComboBoxModel(new String[] { "0",
				"10", "100", "1000","10000" }));
		cbLamTronSauDauPhay = new JComboBox();
		cbLamTronSauDauPhay.setModel(new DefaultComboBoxModel(new String[] {
				"0", "1", "2", "3", "4", "5", "6", "7", "8", "9" }));

		buttonGroup1.add(rdTronLen);
		rdTronLen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		rdTronLen.setText("Làm tròn lên");
		rdTronLen
				.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

		buttonGroup1.add(rdTronSauDauPhay);
		rdTronSauDauPhay.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		rdTronSauDauPhay.setText("Làm tròn sau dấu phẩy");
		rdTronLen.setOpaque(false);
		rdTronSauDauPhay.setOpaque(false);
		rdTronSauDauPhay.setCursor(new java.awt.Cursor(
				java.awt.Cursor.DEFAULT_CURSOR));

		try {
//			Account account = AccountModelManager.getInstance()
//					.getAccountByLoginId(
//							ManagerAuthenticate.getInstance().getLoginId());
			Profile profile = AccountModelManager.getInstance().getProfileConfigAdmin();
			Object str = profile.get(TronLen);
			if (str != null) {
				rdTronLen.setSelected(true);
				for (int i = 0; i < cbLamTronLen.getItemCount(); i++) {
					if (cbLamTronLen.getItemAt(i).toString()
							.equals(str.toString())) {
						cbLamTronLen.setSelectedIndex(i);
						break;
					}
				}

			} else {
				rdTronLen.setSelected(false);
			}

			Object str1 = profile.get(TronPhay);
			if (str1 != null) {
				rdTronSauDauPhay.setSelected(true);
				for (int i = 0; i < cbLamTronSauDauPhay.getItemCount(); i++) {
					if (cbLamTronSauDauPhay.getItemAt(i).toString()
							.equals(str1.toString())) {
						cbLamTronSauDauPhay.setSelectedIndex(i);
						break;
					}
				}

			} else {
				rdTronSauDauPhay.setSelected(false);
			}
		} catch (Exception e) {
		}

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(rdTronLen)
												.addComponent(rdTronSauDauPhay))
								.addGap(5, 5, 5)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														cbLamTronSauDauPhay,
														0, 250, Short.MAX_VALUE)
												.addComponent(
														cbLamTronLen,
														0,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE))
								.addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(10, 10, 10)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														rdTronLen,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														23,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														cbLamTronLen,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														23,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(18, 18, 18)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														rdTronSauDauPhay,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														23,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														cbLamTronSauDauPhay,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														23,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addContainerGap(22, Short.MAX_VALUE)));
	}

	private void close() {
		((JDialog) getRootPane().getParent()).dispose();

	}

	@Override
	public void Save() throws Exception {
		Account account;
		try {
//			account = AccountModelManager.getInstance().getAccountByLoginId(
//					ManagerAuthenticate.getInstance().getLoginId());
			Profile profile = AccountModelManager.getInstance().getProfileConfigAdmin();

			if (rdTronLen.isSelected()) {
				profile.put(TronLen, cbLamTronLen.getSelectedItem().toString());
			} else {
				profile.put(TronLen, null);

			}

			if (rdTronSauDauPhay.isSelected()) {
				profile.put(TronPhay, cbLamTronSauDauPhay.getSelectedItem().toString());

			} else {
				profile.put(TronPhay, null);
			}

//			AccountModelManager.getInstance().saveAccount(account);
			AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(), profile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ScreenOften.LamTron=false;
		close();
	}

}
