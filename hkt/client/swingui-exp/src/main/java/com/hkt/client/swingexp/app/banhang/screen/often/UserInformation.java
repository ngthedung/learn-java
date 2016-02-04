package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.Timer;

import com.hkt.client.swingexp.app.component.ImageTool;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.app.khachhang.BasicInformation;
import com.hkt.module.account.entity.Account;

@SuppressWarnings("serial")
public class UserInformation extends javax.swing.JPanel implements IDialogResto {

	public UserInformation() {
		initComponents();

		loadInfo();
		if (ManagerAuthenticate.getInstance().getLoginId().equals("admin")) {
			jLabel2.setText("Admin");
		} else {
			jLabel2.setText(AccountModelManager.getInstance().getNameByLoginId(
					ManagerAuthenticate.getInstance().getLoginId()));
		}
		try {
			Account ac = AccountModelManager.getInstance().getAccountByLoginId(
					ManagerAuthenticate.getInstance().getLoginId());
			String image = ac.getProfiles().getBasic()
					.get(BasicInformation.AVATAR).toString();

			if (image != null) {
				ImageIcon icon = ImageTool.getInstance().decodeToImage(image);
				lbAvatar.setText("");
				lbAvatar.setIcon(icon);
			} else {
				lbAvatar.setIcon(null);
				lbAvatar.setText("");
			}
		} catch (Exception e) {
			lbAvatar.setIcon(null);
			lbAvatar.setText("");
		}
	}

	private void viewKH() {

		if (jCheckBox1.isSelected()) {
			jPanel2.setVisible(true);
			jTextField1.setEnabled(true);
			jTextField2.setEnabled(true);
			jTextField3.setEnabled(true);
		} else {
			jPanel2.setVisible(false);
			jTextField1.setEnabled(false);
			jTextField2.setEnabled(false);
			jTextField3.setEnabled(false);
		}

	}

	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jPanel3 = new JPanel();
		jLabel5 = new javax.swing.JLabel();
		jPanel2 = new javax.swing.JPanel();
		jLabel6 = new javax.swing.JLabel();
		jLabel7 = new javax.swing.JLabel();
		jLabel8 = new javax.swing.JLabel();
		jTextField1 = new javax.swing.JPasswordField();
		jTextField2 = new javax.swing.JPasswordField();
		jTextField3 = new javax.swing.JPasswordField();
		jCheckBox1 = new javax.swing.JCheckBox();
		jPanel2.setVisible(false);
		lbMS = new JLabel();
		lbMS.setText(" ");
		lbMS.setFont(new Font("Tahoma", 0, 12));
		jPanel2.add(lbMS);
		
		this.setOpaque(false);
		jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
				"Thông tin cá nhân",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(204, 0,0))); // NOI18N
		jPanel1.setOpaque(false);
		jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		jLabel1.setForeground(new java.awt.Color(204, 0, 0));
		jLabel1.setText("Chào");

		jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		jLabel2.setText("jLabel2");

		jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		jLabel3.setForeground(new java.awt.Color(204, 0, 0));
		jLabel3.setText("Giờ hiện tại");

		jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		jLabel4.setText("jLabel4");
		lbAvatar = new JLabel();
		jPanel3.setOpaque(false);
		jPanel3.add(lbAvatar);
		lbAvatar.setBorder(BorderFactory.createEtchedBorder());
		jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
				"Ảnh", javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.DEFAULT_POSITION));

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)//
				.addGap(10, 180, Short.MAX_VALUE).addComponent(lbAvatar));
		jPanel3Layout.setVerticalGroup(jPanel3Layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
				.addGap(0, 120, Short.MAX_VALUE).addComponent(lbAvatar));

		jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
		jLabel5.setForeground(new java.awt.Color(204, 0, 0));
		jLabel5.setText("Bạn đang đăng nhập với vai trò quản trị viên");

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(jLabel1)
																		.addGap(18,18,18)
																		.addComponent(jLabel2)
																		.addGap(35,35,35)
																		.addComponent(jLabel3)
																		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(jLabel4))
														.addComponent(jLabel5))
										.addGap(39, 39, 39)
										.addComponent(
												jPanel3,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(100, Short.MAX_VALUE)));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addGap(24,
																				24,
																				24)
																		.addGroup(
																				jPanel1Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								jLabel3)
																						.addComponent(
																								jLabel1)
																						.addComponent(
																								jLabel2)
																						.addComponent(
																								jLabel4))
																		.addGap(82,
																				82,
																				82)
																		.addComponent(
																				jLabel5,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				14,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(0,
																				0,
																				Short.MAX_VALUE))
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																jPanel1Layout
																		.createSequentialGroup()
																		.addGap(0,
																				0,
																				Short.MAX_VALUE)
																		.addComponent(
																				jPanel3,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										));

		jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
				"Thông tin tài khoản",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(204, 0,
						0))); // NOI18N
		jPanel2.setOpaque(false);
		jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		jLabel6.setText("Mật khẩu cũ");

		jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		jLabel7.setText("Mật khẩu mới");

		jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		jLabel8.setText("Xác nhận");

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(
				jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout
				.setHorizontalGroup(jPanel2Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel2Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(jLabel6)
														.addComponent(jLabel7)
														.addComponent(jLabel8)
														)
										.addGap(46, 46, 46)
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jTextField2,
																javax.swing.GroupLayout.Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE,Short.MAX_VALUE)
														.addComponent(lbMS)							
														.addComponent(jTextField1,javax.swing.GroupLayout.Alignment.LEADING)														
														.addComponent(jTextField3)
														)));
		jPanel2Layout
				.setVerticalGroup(jPanel2Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel2Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																jTextField1,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel6))
										.addGap(9, 9, 9)
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																jTextField2,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel7))
										.addGap(9, 9, 9)
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jTextField3,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE)
														.addComponent(jLabel8))
														.addGap(9, 9, 9)
														.addComponent(lbMS)

										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		jCheckBox1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
		jCheckBox1.setText("Đổi mật khẩu");
		jCheckBox1.setOpaque(false);
		jCheckBox1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				viewKH();
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
						0, Short.MAX_VALUE)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(jCheckBox1)
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE) .addGap(9, 9, 9))
				.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(jPanel1,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										177,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jPanel2,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jCheckBox1)
								.addContainerGap(9, Short.MAX_VALUE)));
	}// </editor-fold>

	// Variables declaration - do not modify
	private javax.swing.JCheckBox jCheckBox1;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private JPanel jPanel3;
	private JPasswordField jTextField1;
	private JPasswordField jTextField2;
	private JPasswordField jTextField3;
	private JLabel lbAvatar;
	private JLabel lbMS;
	

	private class loadInformation implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Calendar calendar = Calendar.getInstance();
			Date d = calendar.getTime();
			DateFormat format = new SimpleDateFormat("HH:mm:ss");
			String time = format.format(d);
			jLabel4.setText(time);
		}

	}

	private void loadInfo() {
		Timer timer = new Timer(1000, new loadInformation());
		timer.start();
	}

	@Override
	public void Save() throws Exception {
		try {
			Account acc = AccountModelManager.getInstance()
					.getAccountByLoginId(
							ManagerAuthenticate.getInstance().getLoginId());
			String ps = acc.getPassword();
			String pwOld = jTextField1.getText();
			String pwNew1 = jTextField2.getText();
			String pwNew2 = jTextField3.getText();
			if((pwOld.equals("")) || (pwNew1.equals("") ) || (pwNew2.equals("")))
			{
				lbMS.setText("Bạn phải nhập đầy đủ thông tin.");
				lbMS.setForeground(Color.red);
			}else if(!jTextField1.getText().equals(ps))
			{
				jTextField1.requestFocus();
				lbMS.setText("Mật khẩu cũ không đúng.");
				lbMS.setForeground(Color.red);
			}else if(!jTextField3.getText().equals(jTextField2.getText()))
			{
				jTextField3.requestFocus();
				lbMS.setText("Mật khẩu xác nhận sai.");
				lbMS.setForeground(Color.red);
			}else if(jTextField2.getText().equals(jTextField1.getText()))
			{
				jTextField2.requestFocus();
				lbMS.setText("Mật khẩu cũ và mật khẩu mới phải khác nhau.");
				lbMS.setForeground(Color.red);
			}
			else 
			{
				acc.setPassword(pwNew1);
				JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công!");
				lbMS.setForeground(Color.red);
				acc = AccountModelManager.getInstance().saveAccount(acc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
