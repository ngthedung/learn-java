package com.hkt.client.swingexp.homescreen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.hkt.client.swingexp.app.banhang.screen.often.ScreenOften;
import com.hkt.client.swingexp.app.component.ImageTool;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.khachhang.OrganizationBasic;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.module.account.entity.Account;

@SuppressWarnings("serial")
public class PanelMenu extends javax.swing.JPanel {

	/** Creates new form PanelMenu */
	public PanelMenu() {
		initComponents();
		setBackground(Color.white);
		loadInfo();
		if (ManagerAuthenticate.getInstance().getLoginId().equals("admin")) {
			lableUser.setText("Admin");
		} else {
			lableUser.setText(AccountModelManager.getInstance()
					.getNameByLoginId(
							ManagerAuthenticate.getInstance().getLoginId()));
		}
		lableEnterprise.setText(AccountModelManager.getInstance()
				.getNameByLoginId(
						ManagerAuthenticate.getInstance()
								.getOrganizationLoginId()));
		try {
			Account acc = AccountModelManager.getInstance()
					.getAccountByLoginId(
							ManagerAuthenticate.getInstance()
									.getOrganizationLoginId());
			String image = acc.getProfiles().getBasic()
					.get(OrganizationBasic.LOGOURL).toString();
			if (image != null && !image.trim().isEmpty()) {
				ImageIcon icon = ImageTool.getInstance().decodeToImage(image);
				lableLogo.setIcon(icon);
			} else {
				ImageIcon icon = new javax.swing.ImageIcon(
						HomeScreen.class.getResource("icon/logohkt.png"));
				lableLogo.setIcon(icon);
			}
		} catch (Exception e) {
			ImageIcon icon = new javax.swing.ImageIcon(
					HomeScreen.class.getResource("icon/logohkt.png"));
			lableLogo.setIcon(icon);
		}

	}

	private JPanel panel;

	private void initComponents() {

		panelResto1 = new PanelResto();
		lbUser = new javax.swing.JLabel();
		lbUser.setForeground(Color.black);
		lableUser = new javax.swing.JLabel();
		lableUser.setForeground(Color.black);
		lableDate = new javax.swing.JLabel();
		lableDate.setForeground(Color.black);
		lblHour = new javax.swing.JLabel();
		lblHour.setForeground(Color.black);
		jPanel4 = new javax.swing.JPanel();
		lableLogo = new javax.swing.JLabel();
		lableEnterprise = new javax.swing.JLabel();
		lableEnterprise.setPreferredSize(new Dimension(25, 25));
		lableEnterprise.setMaximumSize(new Dimension(25, 25));
		lableEnterprise.setSize(new Dimension(25, 25));
		lableEnterprise.setForeground(Color.black);
		jLabel7 = new javax.swing.JLabel();
		jLabel7.setForeground(Color.black);
		jLabel5 = new javax.swing.JLabel();
		jLabel5.setForeground(Color.black);
		jLabel6 = new javax.swing.JLabel();
		jLabel6.setForeground(Color.black);

		panelResto1.setOpaque(false);

		javax.swing.GroupLayout panelResto1Layout = new javax.swing.GroupLayout(
				panelResto1);
		panelResto1.setLayout(panelResto1Layout);
		panelResto1Layout.setHorizontalGroup(panelResto1Layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 88, Short.MAX_VALUE));
		panelResto1Layout.setVerticalGroup(panelResto1Layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 116, Short.MAX_VALUE));

		lbUser.setFont(new java.awt.Font("Tahoma", 1, 12));
		lbUser.setText("User:"); // NOI18N

		lableUser.setFont(new java.awt.Font("Tahoma", 1, 12));
		lableUser.setText(""); // NOI18N

		lableDate.setFont(new java.awt.Font("Tahoma", 1, 12));
		lableDate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lableDate
				.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date())); // NOI18N
		lableDate.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

		lblHour.setFont(new java.awt.Font("Tahoma", 1, 12));
		lblHour.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblHour.setText(""); // NOI18N
		lblHour.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

		jPanel4.setOpaque(false);

		lableLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lableLogo.setText(""); // NOI18N
		lableLogo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		lableLogo.addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentResized(java.awt.event.ComponentEvent evt) {
				lableLogoComponentResized(evt);
			}
		});

		lableEnterprise.setFont(new java.awt.Font("Tahoma", 1, 14));
		lableEnterprise.setForeground(new java.awt.Color(124, 0, 0));
		lableEnterprise
				.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lableEnterprise.setText(""); // NOI18N
		lableEnterprise
				.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

		javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(
				jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout.setHorizontalGroup(jPanel4Layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(lableLogo, javax.swing.GroupLayout.DEFAULT_SIZE,
						198, Short.MAX_VALUE)
				.addComponent(lableEnterprise,
						javax.swing.GroupLayout.PREFERRED_SIZE, 198,
						Short.MAX_VALUE));
		jPanel4Layout
				.setVerticalGroup(jPanel4Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel4Layout
										.createSequentialGroup()
										.addComponent(
												lableEnterprise,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												33,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												lableLogo,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												0, Short.MAX_VALUE)));

		jLabel7.setFont(new java.awt.Font("Tahoma", 3, 14));
		jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel7.setText("Phần Mềm Quản Trị Bán Hàng"); // NOI18N

		jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12));
		jLabel5.setText("Bản quyền HKT Consultant"); // NOI18N
		jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		jLabel6.setFont(new java.awt.Font("Tahoma", 3, 12));
		jLabel6.setText("Version 4.0"); // NOI18N
		jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		panel = new JPanel();
		panel.add(panelResto1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(panel,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(10, 10, 10)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		lbUser)
																.addGap(6, 6, 6)
																.addComponent(
																		lableUser,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		139,
																		javax.swing.GroupLayout.PREFERRED_SIZE))
												.addComponent(
														jPanel4,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		lableDate,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		88,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		lblHour,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		98,
																		javax.swing.GroupLayout.PREFERRED_SIZE))))
				.addGroup(
						layout.createParallelGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								false)
								.addComponent(
										jLabel7,
										javax.swing.GroupLayout.Alignment.LEADING,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addGroup(
										javax.swing.GroupLayout.Alignment.LEADING,
										layout.createSequentialGroup()
												.addComponent(jLabel5)
												.addGap(61, 61, 61)
												.addComponent(
														jLabel6,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														80,
														javax.swing.GroupLayout.PREFERRED_SIZE))));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														panel,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addGroup(
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(
																						lbUser)
																				.addComponent(
																						lableUser))
																.addGap(6, 6, 6)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																				.addComponent(
																						lableDate)
																				.addComponent(
																						lblHour))
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																		jPanel4,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jLabel7,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										20,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														jLabel5,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														20,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														jLabel6,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														20,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addContainerGap()));
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout());
		panel.add(new JLabel(" "), BorderLayout.CENTER);
		panelResto1.setMaximumSize(panel.getPreferredSize());
		panel.add(panelResto1, BorderLayout.SOUTH);
	}

	private void lableLogoComponentResized(java.awt.event.ComponentEvent evt) {
	}

	// Variables declaration - do not modify
	private javax.swing.JLabel lbUser;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JLabel lableDate;
	private javax.swing.JLabel lableEnterprise;
	private javax.swing.JLabel lableLogo;
	private javax.swing.JLabel lableUser;
	private javax.swing.JLabel lblHour;
	private PanelResto panelResto1;

	private class loadInformation implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Calendar calendar = Calendar.getInstance();
			Date d = calendar.getTime();
			DateFormat format = new SimpleDateFormat("HH:mm:ss");
			String time = format.format(d);
			lblHour.setText(time);
			// if(time.substring(4,5).equals("5")||time.substring(4,5).equals("0")){
			// if(ScreenOften.screenOften!=null){
			// if(ScreenOften.screenOften.getListProductCode().size()>0){
			// try {
			// AccountingModelManager.getInstance().caculateReport(ScreenOften.screenOften.getListProductCode().get(0),
			// new Date(), false, true);
			// ScreenOften.screenOften.getListProductCode().remove(0);
			// } catch (Exception e2) {
			// }
			//
			// }
			// }
			//
			// }
		}
	}

	private void loadInfo() {
		Timer timer = new Timer(1000, new loadInformation());
		timer.start();
	}

}
