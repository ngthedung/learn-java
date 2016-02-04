package com.hkt.client.swingexp.app.hethong;

/* 
 * KhanhDD
 * Thêm checkbox remember và action
 * */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.AccountServiceClient;
import com.hkt.client.swingexp.app.component.ImageTool;
import com.hkt.client.swingexp.app.core.HKTFile;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDialogPlus;
import com.hkt.client.swingexp.app.khachhang.OrganizationBasic;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.account.entity.Profiles;

@SuppressWarnings("serial")
public class LoginDialog extends JPanel implements IDialogResto {

	private boolean login;
	private JLabel labelStt = new JLabel();
	private JLabel label;
	private int i = 15;
	private ClientContext clientContext = ClientContext.getInstance();
	private AccountServiceClient accountServiceClient = clientContext.getBean(AccountServiceClient.class);

	public boolean isLogin() {
		return login;
	}

	public LoginDialog() {
		initComponents();
		ManagerAuthenticate.getInstance().setOrganizationLoginId("hkt");
		// ------------------------
		try {
			String str = readData();
			if (str.indexOf("/") > 0) {
				txtUserName.setText(str.substring(0, str.indexOf("/")));
			
				txtPassWord.setText(str.substring(str.indexOf("/")+1, str.length()));
				checkRemember.setSelected(true);
			}
		} catch (Exception e) {
			saveData("");
		}
		txtUserName.setFocusTraversalKeysEnabled(false);
		txtPassWord.setFocusTraversalKeysEnabled(false);
		txtUserName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_TAB) {
					txtPassWord.requestFocus();
					txtPassWord.selectAll();
				}
			}
		});
		txtPassWord.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_TAB) {

					txtUserName.requestFocus();   
					txtUserName.selectAll();
				}
			}
		});
		if (checkRemember.isSelected()) {
			loadInfo();
		}
		txtUserName.selectAll();
	}

	private javax.swing.JButton btnCancel;
	private javax.swing.JButton btnOk;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JLabel lblPassWord;
	private javax.swing.JLabel lblUserName;
	private javax.swing.JPasswordField txtPassWord;
	private javax.swing.JTextField txtUserName;
	private JCheckBox checkRemember;

	private void initComponents() {
		setOpaque(false);
		btnOk = new javax.swing.JButton();
		btnCancel = new javax.swing.JButton();
		jLabel8 = new javax.swing.JLabel();
		jLabel1 = new javax.swing.JLabel();
		jPanel3 = new javax.swing.JPanel();
		jLabel2 = new javax.swing.JLabel();
		lblUserName = new javax.swing.JLabel();
		lblPassWord = new javax.swing.JLabel();
		txtUserName = new javax.swing.JTextField();
		txtPassWord = new javax.swing.JPasswordField();
		checkRemember = new JCheckBox();
		setPreferredSize(new java.awt.Dimension(545, 276));
		addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentShown(java.awt.event.ComponentEvent evt) {
				formComponentShown(evt);
			}
		});

		btnOk.setFont(new java.awt.Font("Tahoma", 1, 14));
		btnOk.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/iconOk.png"))); // NOI18N
		btnOk.setText("OK"); // NOI18N
		btnOk.setIconTextGap(10);
		btnOk.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnOkActionPerformed(evt);
			}
		});
		btnOk.addMouseListener(new MouseEventClickButtonDialogPlus("iconOk.png", "iconOk.png", "iconOk.png"));
		btnCancel.setFont(new java.awt.Font("Tahoma", 1, 14));
		btnCancel.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/iconBack.png"))); // NOI18N
		btnCancel.setText("Hủy"); // NOI18N
		btnCancel.setIconTextGap(10);
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnCancelActionPerformed(evt);
			}
		});
		btnCancel.addMouseListener(new MouseEventClickButtonDialogPlus("iconBack.png", "iconBack.png", "iconBack.png"));
		// setTitle("HKT Software");
		jLabel8.setFont(new java.awt.Font("Tahoma", 1, 16));
		jLabel8.setForeground(new java.awt.Color(126, 0, 0));
		jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		jLabel8.setText("HKT Consultant"); // NOI18N

		jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14));
		jLabel1.setForeground(new java.awt.Color(126, 0, 0));
		jLabel1.setText("Đăng nhập"); // NOI18N

		jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14));
		jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel2.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/logohkt.png"))); // NOI18N
		jLabel2.setText(""); // NOI18N

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 100,
		        Short.MAX_VALUE));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE));

		lblUserName.setFont(new java.awt.Font("Tahoma", 1, 16));
		lblUserName.setText("Tài khoản"); // NOI18N

		lblPassWord.setFont(new java.awt.Font("Tahoma", 1, 16));
		lblPassWord.setText("Mật khẩu"); // NOI18N

		checkRemember.setFont(new java.awt.Font("Tahoma", 1, 14));
		checkRemember.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		checkRemember.setOpaque(false);
		checkRemember.setText("Nhớ mật khẩu");

		txtUserName.setFont(new java.awt.Font("Tahoma", 2, 16));
		txtUserName.setForeground(new java.awt.Color(126, 0, 0));
		txtUserName.setText(""); // NOI18N
		txtUserName.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				txtUserNameKeyPressed(evt);
			}
		});

		txtPassWord.setFont(new java.awt.Font("Tahoma", 2, 16));
		txtPassWord.setForeground(new java.awt.Color(126, 0, 0));
		txtPassWord.setText(""); // NOI18N
		txtPassWord.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				txtPassWordKeyPressed(evt);
			}
		});
		JPanel panel = new JPanel();
		label = new JLabel();
		label.setFont(new java.awt.Font("Tahoma", 2, 16));
		label.setForeground(new java.awt.Color(126, 0, 0));
		label.setText("15 ");
		panel.setLayout(new BorderLayout(10, 10));
		checkRemember.setMaximumSize(new Dimension(20, 20));
		label.setMaximumSize(new Dimension(20, 20));
		label.setHorizontalAlignment(JLabel.CENTER);
		panel.setMaximumSize(new Dimension(30, 30));
		panel.add(checkRemember, BorderLayout.CENTER);
		panel.add(label, BorderLayout.EAST);
		panel.setOpaque(false);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		    layout
		        .createSequentialGroup()
		        .addGap(15, 15, 15)
		        .addGroup(
		            layout
		                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                .addComponent(labelStt)
		                .addGroup(
		                    layout
		                        .createSequentialGroup()
		                        .addGroup(
		                            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                                .addComponent(lblUserName).addComponent(lblPassWord))
		                        .addGap(9, 9, 9)
		                        .addGroup(
		                            layout
		                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                                .addGroup(
		                                    layout
		                                        .createSequentialGroup()
		                                        .addComponent(panel)
		                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 169,
		                                            javax.swing.GroupLayout.PREFERRED_SIZE))
		                                .addGroup(
		                                    layout
		                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
		                                        .addComponent(txtPassWord, javax.swing.GroupLayout.Alignment.LEADING)
		                                        .addComponent(txtUserName, javax.swing.GroupLayout.Alignment.LEADING,
		                                            javax.swing.GroupLayout.PREFERRED_SIZE, 260,
		                                            javax.swing.GroupLayout.PREFERRED_SIZE))).addGap(9, 9, 9)
		                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)))
		        .addContainerGap()));

		layout.setVerticalGroup(layout
		    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
		    .addGap(9, 9, 9)
		    .addGroup(
		        layout.createSequentialGroup().addGroup(
		            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		                layout
		                    .createSequentialGroup()
		                    .addGap(8, 8, 8)
		                    .addGroup(
		                        layout
		                            .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
		                            .addComponent(lblUserName)
		                            .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE,
		                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
		                    .addGap(8, 8, 8)
		                    .addGroup(
		                        layout
		                            .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
		                            .addComponent(lblPassWord)
		                            .addComponent(txtPassWord, javax.swing.GroupLayout.PREFERRED_SIZE,
		                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
		                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(panel)
		                    .addGap(2, 2, 2).addComponent(labelStt)))));

	}

	private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {
		doLogin();
	}

	@SuppressWarnings("deprecation")
	private void doLogin() {

		// ---------------------------------------------------------------------
		try {
			Account accountL = accountServiceClient.login(txtUserName.getText(), txtPassWord.getText());
			if (accountL != null && !accountL.isRecycleBin()) {
				login = true;
				ManagerAuthenticate.getInstance().setLoginId(txtUserName.getText());

				Account account = AccountModelManager.getInstance().getAccountByLoginId("hkt");
				if (account == null) {
					account = new Account();
					account.setLoginId("hkt");
					account.setPassword("hkt");
					account.setType(com.hkt.module.account.entity.Account.Type.ORGANIZATION);
					account.setEmail("hkt@gmail.com");
					account.setLastLoginTime(new Date());

					Profile profileOrgBasic = new Profile();
					profileOrgBasic.put("name", "HKT Consultant");
					ImageIcon image = new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/logohkt.png"));
					profileOrgBasic.put("IMAGE", ImageTool.getInstance().encodeToString(image.getImage()));
					profileOrgBasic.put(OrganizationBasic.FULLNAME, "HKT Consultant");
					profileOrgBasic.put("manager", "true");
					Profiles profiles = new Profiles();

					Profile profileConfig = null;

					if (checkRemember.isSelected()) {
						profileConfig = new Profile();
						profileConfig.put("IdLogin", txtUserName.getText().toString());
						profileConfig.put("pass", txtPassWord.getText().toString());
						profiles.setConfig(profileConfig);
					}

					profiles.setBasic(profileOrgBasic);

					account.setProfiles(profiles);
					AccountModelManager.getInstance().saveAccount(account);

					AccountGroup accountGroup = AccountModelManager.getInstance().getGroupByPath(account.getLoginId());
					if (accountGroup == null) {
						accountGroup = new AccountGroup();
					}
					accountGroup.setParent(null);
					accountGroup.setPath(account.getLoginId());
					accountGroup.setLabel(account.getLoginId());
					accountGroup.setName(account.getLoginId());
					accountGroup.setOwner(account.getLoginId());
					accountGroup = AccountModelManager.getInstance().saveGroup(accountGroup);
				} else {
					if (checkRemember.isSelected()) {
						String str = txtUserName.getText().toString() + "/" + txtPassWord.getText().toString();
						saveData(str);
					} else {
						saveData("");

					}
				}
				UIConfigModelManager.getInstance().loadPermission();
				((JDialog) getRootPane().getParent()).dispose();
			} else {
				labelStt.setText("Sai tên đăng nhập hoặc mật khẩu!");
				labelStt.setForeground(Color.red);
			}
		} catch (Exception e2) {
			labelStt.setText("Sai tên đăng nhập hoặc mật khẩu!");
			labelStt.setForeground(Color.red);
		}

		// --------------------------------------------------------------------
	}

	private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {

		System.exit(0);

	}

	private void txtPassWordKeyPressed(java.awt.event.KeyEvent evt) {

		if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
			doLogin();
		}

	}

	private void txtUserNameKeyPressed(java.awt.event.KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
			doLogin();
		}
	}

	private void formComponentShown(java.awt.event.ComponentEvent evt) {

		txtUserName.requestFocus();
		txtUserName.selectAll();

	}

	private class loadInformation implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (i >= 1) {
				i--;
				String a = "";
				if (i < 10) {
					a = "0" + String.valueOf(i) + " ";
				} else {
					a = String.valueOf(i) + " ";
				}
				label.setText(a);
				if (i == 0) {
					doLogin();
				}
			}

		}
	}

	private void loadInfo() {
		Timer timer = new Timer(1000, new loadInformation());
		timer.start();
	}

	@Override
	public void Save() throws Exception {

		doLogin();

	}

	private String readData() {
		try {
			FileInputStream fi = new FileInputStream(HKTFile.getFile("Database", "login"));
			ObjectInputStream of = new ObjectInputStream(fi);
			String str = of.readObject().toString();
			of.close();
			return str;
		} catch (Exception e) {
			return "";
		}
	}

	private void saveData(String a) {
		try {
			FileOutputStream fStream = new FileOutputStream(HKTFile.getFile("Database", "login"));
			ObjectOutputStream oStream = new ObjectOutputStream(fStream);
			oStream.writeObject(a);
			oStream.reset();

			fStream.close();
		} catch (Exception e) {
		}

	}

}
