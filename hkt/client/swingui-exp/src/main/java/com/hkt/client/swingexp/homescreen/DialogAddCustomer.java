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
import com.hkt.client.swingexp.app.khachhang.PartnerIsOrganization;
import com.hkt.client.swingexp.app.khachhang.PartnerIsUser;
import com.hkt.client.swingexp.model.UIConfigModelManager;

@SuppressWarnings("serial")
public class DialogAddCustomer extends JPanel implements IDialogResto {
	private JButton btnDN, btnCN;
	public static String permission;

	public DialogAddCustomer() {
		init();
	}


	private void init() {

		btnDN = new javax.swing.JButton();
		btnDN.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		btnDN.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/pner.png")));
		btnDN.addMouseListener(new MouseEventClickButtonDialogPlus("pner.png", "pner.png", "pner.png"));
		btnDN.setIconTextGap(11);
		btnDN.setFocusPainted(false);
		
		btnCN = new javax.swing.JButton();
		btnCN.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		btnCN.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/cn.png")));
		btnCN.setIconTextGap(23);
		btnCN.setFocusPainted(false);
		btnCN.addMouseListener(new MouseEventClickButtonDialogPlus("cn.png", "cn.png", "cn.png"));
		PartnerIsUser.permission = (UIConfigModelManager.getInstance().getPlainText(DialogAddCustomer.permission));
		btnCN.setText("KH Cá Nhân");
		btnCN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					PartnerIsUser partner = new PartnerIsUser(null, true);
					partner.setPreferredSize(new Dimension(100, 100));
					partner.setName("PartnerIsUser");
					DialogMain dialog = new DialogMain(partner, true);
					dialog.showButton(true);
					dialog.setIcon("doanhnghiep1.png");
					dialog.setTitle("Khách hàng cá nhân");
					dialog.setName("dlPartnerIsUser");
					dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				disposeForm();
			}
		});

		btnDN.setText("KH Doanh Nghiệp");
		PartnerIsOrganization.permission = (UIConfigModelManager.getInstance().getPlainText(DialogAddCustomer.permission));
		btnDN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					PartnerIsOrganization partner = new PartnerIsOrganization(null, true);
					partner.setSize(100, 100);
					partner.setName("PartnerIsOrganization");
					partner.setPreferredSize(new Dimension(100, 100));
					DialogMain dialog = new DialogMain(partner, true);
					dialog.showButton(true);
					dialog.setIcon("doanhnghiep1.png");
					dialog.setName("dlPartnerIsOrganization");
					dialog.setTitle("Khách hàng doanh nghiệp");
					dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				disposeForm();
			}
		});

		GroupLayout layout = new GroupLayout(this);
		setOpaque(false);
		this.setLayout(layout);

		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
				.addGap(30, 30, 30).addComponent(btnDN, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
						.addGap(50, 50, 50).addComponent(btnCN, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
						.addContainerGap(45, javax.swing.GroupLayout.PREFERRED_SIZE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addGap(10, 10, 10)
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(btnCN, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(btnDN, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(45, javax.swing.GroupLayout.PREFERRED_SIZE)));
	}


	public void disposeForm() {
		(((JDialog) getRootPane().getParent())).dispose();
	}

	@Override
	public void Save() throws Exception {
		(((JDialog) getRootPane().getParent())).dispose();
		
	}

}
