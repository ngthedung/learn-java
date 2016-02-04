package com.hkt.client.swingexp.homescreen;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDialogPlus;
import com.hkt.client.swingexp.app.nhansu.SupplierIsOrganization;
import com.hkt.client.swingexp.app.nhansu.SupplierIsUser;
import com.hkt.client.swingexp.model.UIConfigModelManager;

@SuppressWarnings("serial")
public class DialogAddSupplier extends JPanel implements IDialogResto {
	private JButton btnDN, btnCN;
	public static String permission;

	public DialogAddSupplier() {

		init();
	}

	private void init() {

		btnDN = new javax.swing.JButton();
		btnDN.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		btnDN.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/q1.png")));
		btnDN.addMouseListener(new MouseEventClickButtonDialogPlus("q1.png", "q1.png", "q1.png"));
		btnDN.setIconTextGap(11);
		btnDN.setFocusPainted(false);
		
		btnDN.setName("btnDN");
		btnCN = new javax.swing.JButton();
		btnCN.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		btnCN.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/usernhom.png")));
		btnCN.setName("btnCN");
		btnCN.setFocusPainted(false);
		btnCN.addMouseListener(new MouseEventClickButtonDialogPlus("usernhom.png", "usernhom.png", "usernhom.png"));
		btnCN.setIconTextGap(23);
		SupplierIsUser.permission = (UIConfigModelManager.getInstance().getPlainText(DialogAddSupplier.permission));
		btnCN.setText("NCC Cá Nhân");
		btnCN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					SupplierIsUser panel = new SupplierIsUser(null, true);
					panel.setSize(100, 100);
					panel.setName("SupplierIsUser");
					panel.setPreferredSize(new Dimension(100, 100));
					DialogMain dialog = new DialogMain(panel, true);
					dialog.showButton(true);
					dialog.setIcon("usernhom1.png");
					dialog.setName("dlSupplierIsUser");
					dialog.setTitle("Nhà cung cấp cá nhân");
					dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);

				} catch (Exception e2) {
					e2.printStackTrace();
				}
				disposeForm();
			}
		});

		btnDN.setText("NCC Doanh Nghiệp");
		SupplierIsOrganization.permission = (UIConfigModelManager.getInstance().getPlainText(DialogAddSupplier.permission));
		btnDN.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					SupplierIsOrganization supplier = new SupplierIsOrganization(null, true);
					supplier.setSize(100, 100);
					supplier.setName("SupplierIsOrganization");
					supplier.setPreferredSize(new Dimension(100, 100));
					DialogMain dialog = new DialogMain(supplier, true);
					dialog.showButton(true);
					dialog.setIcon("usernhom1.png");
					dialog.setName("dlSupplierIsOrganization");
					dialog.setTitle("Nhà cung cấp doanh nghiệp");
					dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);

				} catch (Exception e2) {
					e2.printStackTrace();
				}
				disposeForm();
			}
		});

		setOpaque(false);
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);

		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addGap(30, 30, 30).addComponent(btnDN, GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
						.addGap(30, 30, 30).addComponent(btnCN, GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
						.addContainerGap(20, javax.swing.GroupLayout.PREFERRED_SIZE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addGap(10, 10, 10)
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(btnCN, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(btnDN, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(45, Short.MAX_VALUE)));

	}

	public void disposeForm() {
		(((JDialog) getRootPane().getParent())).dispose();
	}

	@Override
	public void Save() throws Exception {
		(((JDialog) getRootPane().getParent())).dispose();
		
	}
}
