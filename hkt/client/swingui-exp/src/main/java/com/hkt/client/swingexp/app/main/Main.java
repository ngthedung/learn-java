package com.hkt.client.swingexp.app.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.LocaleServiceClient;
import com.hkt.client.swingexp.app.banhang.screen.often.DialogConfig;
import com.hkt.client.swingexp.app.component.ImageTool;
import com.hkt.client.swingexp.app.component.PanelBackground;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.GoogleMail;
import com.hkt.client.swingexp.app.core.HKTFile;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MyFrame;
import com.hkt.client.swingexp.app.hethong.AdminConfig;
import com.hkt.client.swingexp.app.hethong.LoginDialog;
import com.hkt.client.swingexp.app.hethong.PanelSystemConfig;
import com.hkt.client.swingexp.app.license.LicenseManager;
import com.hkt.client.swingexp.app.print.ReportGeneral;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.account.entity.Profiles;

public class Main {
	private static DialogLoad dialog = new DialogLoad();
	private static int k = 20;
	static public JLabel labelStatus = new JLabel();
	static public JLabel labelMessenger = new JLabel();

	static public void main(String[] args) throws Exception {

		try {
			// Metal
			// Nimbus
			// CDE/Motif
			// Windows
			// Windows Classic
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Metal".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			// java.util.logging.Logger.getLogger(NewJDialog.class.getName()).log(java.util.logging.Level.SEVERE,
			// null, ex);
		} catch (InstantiationException ex) {
			// java.util.logging.Logger.getLogger(NewJDialog.class.getName()).log(java.util.logging.Level.SEVERE,
			// null, ex);
		} catch (IllegalAccessException ex) {
			// java.util.logging.Logger.getLogger(NewJDialog.class.getName()).log(java.util.logging.Level.SEVERE,
			// null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			// java.util.logging.Logger.getLogger(NewJDialog.class.getName()).log(java.util.logging.Level.SEVERE,
			// null, ex);
		}

		// UIManager.setLookAndFeel("javax.swing.plaf.basic");

		// System.out.println(UIManager.getLookAndFeel());

		load();
		Thread.currentThread().join();
	}
	
	private static String readDataOnline() {
		try {
			FileInputStream fi = new FileInputStream(HKTFile.getFile("Database", "online"));
			ObjectInputStream of = new ObjectInputStream(fi);
			String str = of.readObject().toString();
			of.close();
			return str;
		} catch (Exception e) {
			return "";
		}
	}

	public static boolean runSystemCommand(String command) {
		boolean flag = false;
		try {

			Process p = Runtime.getRuntime().exec("ping " + command);
			BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String s = "";
			// reading output stream of the command
			while ((s = inputStream.readLine()) != null) {
				try {
					if (Double.parseDouble(s.substring(s.indexOf("TTL=") + 4, (s.length()))) <= 255) {
						flag = true;
					}
				} catch (Exception e) {
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	private static void loadGui() {
		Thread t2 = new Thread() {
			public void run() {
				try {
					ReportGeneral report = new ReportGeneral();
					report.setTable(new JTable());
					report.printReport1(true);
					LicenseManager.getInstance().getLicense(LicenseManager.module.Restaurant.name());
				} catch (Exception e) {
				}

			};
		};

		try {
			ManagerAuthenticate.getInstance().setOrganizationLoginId("hkt");
			Profile profile = AccountModelManager.getInstance().getProfileConfigAdmin();

			try {
				if (profile.get(DialogConfig.ImageOption) != null) {
					String image = profile.get(DialogConfig.ImageOption).toString();
					if (image != null) {
						PanelBackground.backgroundIcon = ImageTool.getInstance().decodeToImage(image);
					}
				} else {
					PanelBackground.backgroundIcon = new ImageIcon(HomeScreen.class.getResource("icon/"
					    + profile.get(DialogConfig.Background)));
				}
			} catch (Exception e) {
				PanelBackground.backgroundIcon = new ImageIcon(HomeScreen.class.getResource("icon/KaraBackgroud.jpg"));
			}

			try {
				PanelSystemConfig.getInstance().showConfig();
			} catch (Exception e) {
			}
			try {
					String str = "";
					DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = builderFactory.newDocumentBuilder();
					Document doc = builder.parse(HKTFile.getFile("Database", "ip.xml").getAbsolutePath());
					NodeList list = doc.getElementsByTagName("Account");
					Node accountNode = list.item(0);
					NodeList child = accountNode.getChildNodes();
					Node linkNode = child.item(1);
					boolean local = false;
					try {
						if (readDataOnline().equals("false")) {
							local = true;
						}
					} catch (Exception e) {
					}
					if (!linkNode.getTextContent().equals("localhost") && !local) {
						PanelSystemConfig systemConfig = new PanelSystemConfig();
  		      DialogResto dialog = new DialogResto(systemConfig, false, 0, 110);
  		      dialog.setLocationRelativeTo(null);
  		      dialog.setTitle("Cấu hình hệ thống");
  		      dialog.setVisible(true);
					} 

      } catch (Exception e) {
			}
			Account admin = AccountModelManager.getInstance().getByEmail("admin@gmail.com");
		  
			if (admin == null) {
				if (readData().trim().isEmpty()) {
					AdminConfig systemConfig = new AdminConfig();
					DialogResto dialog = new DialogResto(systemConfig, false, 0, 130);
					dialog.setModal(true);
					dialog.setLocationRelativeTo(null);
					dialog.setTitle("Cấu hình hệ thống");
					dialog.setVisible(true);
				}

				String str = readData().split("/")[0];
				String pass = readData().split("/")[1];
				admin = new Account();
				admin.setLoginId(str);
				admin.setPassword(pass);
				admin.setType(com.hkt.module.account.entity.Account.Type.USER);
				admin.setEmail("admin@gmail.com");

				Profile profileOrgBasic1 = new Profile();
				profileOrgBasic1.put("firstName", "Admin");
				Profiles profiles1 = new Profiles();
				profiles1.setBasic(profileOrgBasic1);
				admin.setProfiles(profiles1);
				AccountModelManager.getInstance().saveAccount(admin);
				ManagerAuthenticate.getInstance().setLoginIdAdmin(admin.getLoginId());
			} else {
				System.out.println(admin.getLoginId()+"   "+admin.getPassword());
				ManagerAuthenticate.getInstance().setLoginIdAdmin(admin.getLoginId());
			}
		} catch (Exception e) {
			saveData(e.getMessage());
		}
		try {
			// try {
			// Thread t = new Thread(){
			// @Override
			// public void run() {
			// Calendar c = Calendar.getInstance();
			// c.add(Calendar.DAY_OF_MONTH, -1);
			// AccountingModelManager.getInstance().caculate(c.getTime());
			// }
			// };
			// t.start();
			// } catch (Exception e) {
			// System.out.println("Loi tinh lại thong ke");
			// }
			t2.start();
			LoginDialog loginDialog = new LoginDialog();
			DialogResto dialog = new DialogResto(loginDialog, false, 0, 155);
			dialog.setName("dlPanelSearchInvoice");
			dialog.setLocationRelativeTo(null);
			dialog.setTitle("Đăng nhập");
			dialog.setVisible(true);
			if (!loginDialog.isLogin()) {
				
				System.exit(0);
			}

		} catch (Exception e) {
			saveData(e.getMessage());
		}
		dialog.setValue(99);

		MyFrame frame = MyFrame.getInstance();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("HKT Software");
		JPanel panel = new JPanel();
		panel.setName("panelHKT");
		panel.setLayout(new BorderLayout());
		HomeScreen homeScreen = new HomeScreen();
		homeScreen.setName("homeScreen");
		panel.add(homeScreen, BorderLayout.CENTER);
		JPanel panel2 = new JPanel();
		panel2.setPreferredSize(new Dimension(100, 15));
		panel2.setBackground(Color.white);
		panel2.setLayout(new BorderLayout(10, 10));
		labelStatus.setForeground(Color.red);
		labelMessenger.setForeground(Color.red);
		panel2.add(labelStatus, BorderLayout.WEST);
		panel2.add(labelMessenger, BorderLayout.CENTER);
		panel.add(panel2, BorderLayout.SOUTH);
		frame.getContentPane().setLayout(new GridLayout());
		frame.add(panel);
		frame.setSize(835, 645);
		frame.setMinimumSize(frame.getSize());
		frame.setLocationRelativeTo(null);
		ClientContext.getInstance();
		File a = HKTFile.getFile("Database", "ok");
		a.delete();
		frame.setVisible(true);
		dialog.dispose();
	}

	public static void load() {
		dialog.setVisible(true);
		dialog.setValue(k);
		final Thread t = new Thread() {

			@Override
			public void run() {
				try {
					try {
						String str2 = readDataRest();
						String str1 = str2.split("/")[2].split(":")[0];
						if (!str1.equals("localhost")) {
							if (!runSystemCommand(str1)) {
								JOptionPane.showMessageDialog(null, "Không thể kết nối đến " + str1);
								try {
									Runtime.getRuntime().exec("taskkill /F /IM HKTSoft.exe");
									PanelBackground.backgroundIcon = new ImageIcon(HomeScreen.class.getResource("icon/KaraBackgroud.jpg"));
					        PanelSystemConfig systemConfig = new PanelSystemConfig();
					        DialogResto dialog = new DialogResto(systemConfig, false, 0, 110);
					        dialog.setLocationRelativeTo(null);
					        dialog.setTitle("Cấu hình hệ thống");
					        dialog.setVisible(true);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}else {
							try {
								String str = this.getClass().getResource("").getPath().substring(5);
								File file = new File(str.substring(0, str.indexOf("lib")) + "bin\\HKTServer.exe");
								String aa = file.getAbsolutePath().replaceAll("%20", " ");
								String cmdarray[] = new String[] { "cmd", "/c", aa };
								Runtime.getRuntime().exec(cmdarray);
							} catch (Exception e) {
								saveData(e.getMessage());
							}
						}
					} catch (Exception e) {
						PanelSystemConfig.getInstance().showConfig();
					}

					Thread.sleep(1000);
					loadMain();
				} catch (Exception ex) {
					saveData(ex.getMessage());
				}
			}

			private void loadMain() {
				try {
					k = k + 5;
					dialog.setValue(k);
					AccountModelManager.getInstance().getAccountById(1);
					loadGui();
				} catch (Exception e) {
					saveData(e.getMessage());

					try {
						Thread.sleep(1000);
						loadMain();
					} catch (Exception e2) {
						saveData(e2.getMessage());
					}

				}

			}
		};
		try {
			ClientContext clientContext = ClientContext.getInstance();
			LocaleServiceClient client = clientContext.getBean(LocaleServiceClient.class);
			client.getCountries();
			loadGui();
		} catch (Exception e) {
			saveData(e.getMessage());
			try {
				Runtime.getRuntime().exec("cscript //NoLogo " + HKTFile.getFile("Database", "mysql.vbs").getPath());
			} catch (IOException e1) {
				saveData(e1.getMessage());
			}
			t.start();
		}
	}

	private static String readData() {
		try {
			FileInputStream fi = new FileInputStream(HKTFile.getFile("Database", "admin"));
			ObjectInputStream of = new ObjectInputStream(fi);
			String str = of.readObject().toString();
			of.close();
			return str;
		} catch (Exception e) {
			return "";
		}
	}

	private static void saveData(String a) {
		try {
			FileOutputStream fStream = new FileOutputStream(HKTFile.getFile("Loi", a));
			ObjectOutputStream oStream = new ObjectOutputStream(fStream);
			oStream.writeObject(a);
			oStream.reset();

			fStream.close();
		} catch (Exception e) {
		}

	}

	private static String readDataRest() {
		try {
			FileInputStream fi = new FileInputStream(HKTFile.getFile("Database", "rest"));
			ObjectInputStream of = new ObjectInputStream(fi);
			String str = of.readObject().toString();
			of.close();
			return str;
		} catch (Exception e) {
			return "";
		}
	}

	@SuppressWarnings("unused")
	private static String readSetup() {
		try {
			FileInputStream fi = new FileInputStream(HKTFile.getFile("Database", "setup"));
			ObjectInputStream of = new ObjectInputStream(fi);
			String str = of.readObject().toString();
			of.close();
			return str;
		} catch (Exception e) {
			return "";
		}
	}

}
