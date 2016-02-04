package com.hkt.client.swingexp.homescreen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.basic.BasicBorders;

import com.hkt.client.swingexp.app.banhang.screen.often.PaneSetInvoice;
import com.hkt.client.swingexp.app.convertdata.excel.FileFilterExtension;
import com.hkt.client.swingexp.app.core.DialogJurisdiction;
import com.hkt.client.swingexp.app.core.DialogList;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MouseEventClickButton;
import com.hkt.client.swingexp.app.hethong.DataExcel;
import com.hkt.client.swingexp.app.hethong.DialogInfo;
import com.hkt.client.swingexp.app.hethong.PaneSetDate;
import com.hkt.client.swingexp.app.hethong.PanelAutoSendEmail;
import com.hkt.client.swingexp.app.hethong.PanelCity;
import com.hkt.client.swingexp.app.hethong.PanelCountry;
import com.hkt.client.swingexp.app.hethong.PanelCurrency;
import com.hkt.client.swingexp.app.hethong.PanelDepartment;
import com.hkt.client.swingexp.app.hethong.PanelOption;
import com.hkt.client.swingexp.app.hethong.PanelProductUnit;
import com.hkt.client.swingexp.app.hethong.PanelSetupPermission;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.app.hethong.PanelSystemConfig;
import com.hkt.client.swingexp.app.hethong.list.TableListDepartment;
import com.hkt.client.swingexp.app.khachhang.PartnerIsOrganization;
import com.hkt.client.swingexp.app.license.LicenseManager;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.Account;
import com.hkt.module.partner.customer.entity.Customer;


@SuppressWarnings("serial")
public class MenuRightHeThong extends JPanel {

	public MenuRightHeThong() {

		setLayout(new GridLayout(2, 1));
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()));
		MenuRightTop menuRightTop = new MenuRightTop();
		menuRightTop.setBackground(Color.WHITE);
		JPanel panelTop = new JPanel();
		panelTop.setBackground(Color.WHITE);
		panelTop.setLayout(new BorderLayout());
		panelTop.add(menuRightTop, BorderLayout.CENTER);
		add(panelTop);

		MenuRightUnder menuRightUnder = new MenuRightUnder();
		menuRightUnder.setBackground(Color.WHITE);
		JPanel panelBot = new JPanel();
		panelBot.setBackground(Color.WHITE);
		panelBot.setLayout(new BorderLayout());
		panelBot.add(menuRightUnder, BorderLayout.CENTER);
		add(panelBot);
	}

	public static class MenuRightTop extends JPanel {

		public MenuRightTop() {
			setBorder(BorderFactory.createTitledBorder(null, "Chức năng cơ bản", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 16), new java.awt.Color(204, 0, 0)));
			setLayout(new GridLayout(3, 3));

			// THÔNG TIN DN
			JButton label1 = CreateButton("<html>Thông tin<br>doanh<br> nghiệp</br></html>");
			label1.setFocusPainted(false);
			label1.setIcon(new ImageIcon(getClass().getResource("icon/doanhnghiep1.png")));
			label1.setFocusPainted(false);
			label1.addMouseListener(new MouseEventClickButton("doanhnghiep1.png", "doanhnghiep2.png", "doanhnghiep3.png"));
			add(label1);
			PartnerIsOrganization.permission = (UIConfigModelManager.getInstance().getPlainText(label1.getText()));
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label1.getText()));
			label1.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						Account acc = AccountModelManager.getInstance().getAccountByLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
						Customer customer = new Customer();
						customer.setLoginId(acc.getLoginId());
						PartnerIsOrganization partner = new PartnerIsOrganization(customer, false);
						partner.setSize(100, 100);
						partner.setName("PartnerIsOrganization");
						partner.setPreferredSize(new Dimension(100, 100));
						DialogMain dialog = new DialogMain(partner, true);
						dialog.showButton(false);
						dialog.setIcon("doanhnghiep1.png");
						dialog.setName("dlPartnerIsOrganization");
						dialog.setTitle("Thông tin doanh nghiệp");
						dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);
					} catch (Exception e2) {
						e2.printStackTrace();
					}

				}
			});
			
			// PHÒNG BAN BP
						JButton label4 = CreateButton("<html>Phòng ban<br>bộ phận</br></html>");
						label4.setActionCommand("1");
						label4.setFocusPainted(false);
						label4.setIcon(new ImageIcon(getClass().getResource("icon/kho1.png")));
						label4.addMouseListener(new MouseEventClickButton("kho1.png", "kho2.png", "kho3.png"));
						label4.setName("PhongBanBoPhan");
						PanelDepartment.permission = (UIConfigModelManager.getInstance().getPlainText(label4.getText()));
						label4.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent evt) {
								try {
									JButton btn = (JButton) evt.getSource();
									if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
										DialogJurisdiction.getInstace().setVisible(true);
										return;
									}
									PanelDepartment panel = new PanelDepartment();
									panel.setName("PhongBanBoPhan");
									DialogMain dialog = new DialogMain(panel);
									dialog.setIcon("kho1.png");
									dialog.setName("dlPhongBanBoPhan");
									dialog.setTitle("Phòng ban bộ phận");
									dialog.setVisible(true);

								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						add(label4);
						UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label4.getText()));

						// DS PHÒNG BAN
						JButton label7 = CreateButton("<html>Danh sách<br>phòng ban</br></html>");
						label7.setName("DanhSachPhongBanBoPhan");
						label7.setFocusPainted(false);
						label7.setIcon(new ImageIcon(getClass().getResource("icon/kho1.png")));
						label7.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent evt) {
								JButton btn = (JButton) evt.getSource();
								if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
									DialogJurisdiction.getInstace().setVisible(true);
									return;
								}
								final TableListDepartment tbDepartment = new TableListDepartment();
								tbDepartment.setName("tbDanhSachPhongBanBoPhan");
								DialogList dialog = new DialogList(tbDepartment);
								dialog.setIcon("kho1.png");
								dialog.setTitle("Danh sách phòng ban");
								dialog.setName("dlDanhSachPhongBanBoPhan");
								dialog.setVisible(true);

							}
						});
						label7.addMouseListener(new MouseEventClickButton("kho1.png", "kho2.png", "kho3.png"));
						add(label7);
						UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label7.getText()));
						
			// BẢN QUYỀN
			JButton label2 = CreateButton("<html>Bản Quyền</html>");
			label2.setFocusPainted(false);
			label2.setIcon(new ImageIcon(getClass().getResource("icon/key1.png")));
			label2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					try {
						JButton btn = (JButton) evt.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						LicenseManager.getInstance().registryLicense();
					} catch (Exception e) {
						e.printStackTrace();
						DialogInfo di = new DialogInfo();
						DialogResto dialog = new DialogResto(di, false, 170, 150);
						dialog.setName("dlInfo");
						dialog.setIcon("lienhe1.png");
						dialog.setLocationRelativeTo(null);
						dialog.setTitle("Liên hệ");
						dialog.setVisible(true);
					}
				}
			});
			label2.addMouseListener(new MouseEventClickButton("key1.png", "key2.png", "key3.png"));
			add(label2);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label2.getText()));

			// Thông tin<br>liên hệ</br>
						JButton label5 = CreateButton("<html>Liên hệ</html>");
						label5.setFocusPainted(false);
						label5.setIcon(new ImageIcon(getClass().getResource("icon/lienhe1.png")));
						label5.addMouseListener(new MouseEventClickButton("lienhe1.png", "lienhe2.png", "lienhe3.png"));
						add(label5);
						label5.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent evt) {
								try {
									DialogInfo di = new DialogInfo();
									DialogResto dialog = new DialogResto(di, false, 200, 450);
									dialog.setBtnSave(false);
//									dialog.setBtnExit(true);
									dialog.setIcon("lienhe1.png");
									dialog.setName("dlInfo");
									dialog.setLocationRelativeTo(null);
									dialog.setTitle("Liên hệ");
									dialog.setVisible(true);

								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						
						
				//		 XỬ LÝ DỮ LIỆU
						JButton label3 = CreateButton("<html>Xử lý<br>dữ liệu</br></html>");
						label3.setIcon(new ImageIcon(getClass().getResource("icon/saoluu1.png")));
						label3.setFocusPainted(false);
						label3.addMouseListener(new MouseEventClickButton("saoluu1.png", "saoluu2.png", "saoluu3.png"));
						PanelDataProcessing.permission = UIConfigModelManager.getInstance().getPlainText(label3.getText());
						label3.addActionListener(new ActionListener() {
			
							@Override
							public void actionPerformed(ActionEvent e) {
								JButton btn = (JButton) e.getSource();
								if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
									DialogJurisdiction.getInstace().setVisible(true);
									return;
								}
								PanelDataProcessing dataProcessing = new PanelDataProcessing();
								DialogResto dialog = new DialogResto(dataProcessing, false, 0, 200);
								dialog.setIcon("saoluu1.png");
								dialog.setName("dlInfo");
								dialog.setLocationRelativeTo(null);
								dialog.setTitle("Xử lý dữ liệu");
								dialog.setVisible(true);
			
							}
						});
						add(label3);
						UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label3.getText()));

						// LOẠI HÓA ĐƠN
						JButton label16 = CreateButton("<html>Loại<br>hóa đơn</br></html>");
						label16.setActionCommand("1");
						label16.setFocusPainted(false);
						label16.setIcon(new ImageIcon(getClass().getResource("icon/invoice1.png")));
						PanelOption.permission4 = (UIConfigModelManager.getInstance().getPlainText(label16.getText()));
						label16.setForeground(Color.black);
						label16.addActionListener(new ActionListener() {
							@SuppressWarnings("unused")
							@Override
							public void actionPerformed(ActionEvent e) {
								try {
									JButton btn = (JButton) e.getSource();
									if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
										DialogJurisdiction.getInstace().setVisible(true);
										return;
									}
									PanelOption pnOption = new PanelOption("accounting", "AccountingService", "type_invoice");
									DialogMain dialog = new DialogMain(new PanelOption("accounting", "AccountingService", "type_invoice"));
									dialog.setTitle("Loại hóa đơn");
									dialog.setIcon("invoice1.png");
									dialog.setModal(true);
									dialog.setLocationRelativeTo(null);
									dialog.setVisible(true);

								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}
						});
						label16.addMouseListener(new MouseEventClickButton("invoice1.png", "invoice2.png", "invoice3.png"));
						add(label16);
						UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label16.getText()));

						// VỊ TRÍ PHÒNG BAN
						JButton label14 = CreateButton("<html>vị trí<br>phòng ban</br></html>");
						label14.setActionCommand("1");
						label14.setFocusPainted(false);
						label14.setIcon(new ImageIcon(getClass().getResource("icon/donvi1.png")));
						PanelOption.permission2 = (UIConfigModelManager.getInstance().getPlainText(label14.getText()));
						label14.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								try {
									JButton btn = (JButton) evt.getSource();
									if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
										DialogJurisdiction.getInstace().setVisible(true);
										return;
									}
									PanelOption pnOption = new PanelOption("accounting", "AccountingService", "contributor_role");
									pnOption.setName("ViTriPhongBan");
									DialogMain dialog = new DialogMain(pnOption);
									dialog.setIcon("donvi1.png");
									dialog.setName("dlViTriPhongBan");
									dialog.setTitle("Vị trí phòng ban");
									dialog.setModal(true);
									dialog.setLocationRelativeTo(null);
									dialog.setVisible(true);

								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						label14.addMouseListener(new MouseEventClickButton("donvi1.png", "donvi2.png", "donvi3.png"));
						add(label14);
						UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label14.getText()));
	
						// ĐƠN VỊ SP
						JButton label17 = CreateButton("<html>Đơn vị<br>sản phẩm</br></html>");
						label17.setActionCommand("1");
						label17.setFocusPainted(false);
						label17.setIcon(new ImageIcon(getClass().getResource("icon/donvi1.png")));
						PanelProductUnit.permission = (UIConfigModelManager.getInstance().getPlainText(label17.getText()));
						label17.setName("DonViSanPham");
						label17.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								try {
									JButton btn = (JButton) evt.getSource();
									if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
										DialogJurisdiction.getInstace().setVisible(true);
										return;
									}
									PanelProductUnit pnlProductUnit = new PanelProductUnit();
									pnlProductUnit.setName("DonViSanPham");
									DialogMain dialog = new DialogMain(pnlProductUnit);
									dialog.setIcon("donvi1.png");
									dialog.setTitle("Đơn vị sản phẩm");
									dialog.setName("dlDonViSanPham");
									dialog.setVisible(true);

								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						label17.addMouseListener(new MouseEventClickButton("donvi1.png", "donvi2.png", "donvi3.png"));
						add(label17);
						UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label17.getText()));

		}	
	}

	public static class MenuRightUnder extends JPanel {

		public MenuRightUnder() {
			setLayout(new GridLayout(3, 3));
			setBorder(BorderFactory.createTitledBorder(null, "Chức năng hỗ trợ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 16), new java.awt.Color(204, 0, 0)));
			JButton label1 = CreateButton("<html>Quốc gia</html>");
			label1.setActionCommand("1");
			label1.setFocusPainted(false);
			label1.setIcon(new ImageIcon(getClass().getResource("icon/country1.png")));
			PanelCountry.permisson = UIConfigModelManager.getInstance().getPlainText(label1.getText());
			label1.setName("QuocGia");
			label1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					try {
						JButton btn = (JButton) evt.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						PanelCountry pnlCountry = new PanelCountry();
						pnlCountry.setToolTipText(UIConfigModelManager.getInstance().getPlainText(btn.getText()));
						pnlCountry.setName("QuocGia");
						DialogMain dialog = new DialogMain(pnlCountry);
						dialog.setIcon("country1.png");
						dialog.setTitle("Quốc gia");
						dialog.setName("dlQuocGia");
						dialog.setModal(true);
						dialog.setVisible(true);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			label1.addMouseListener(new MouseEventClickButton("country1.png", "country2.png", "country3.png"));
			add(label1);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label1.getText()));

			// THÀNH PHỐ
			JButton label2 = CreateButton("<html>Thành phố</html>");
			label2.setActionCommand("2");
			label2.setFocusPainted(false);
			label2.setIcon(new ImageIcon(getClass().getResource("icon/country1.png")));
			label2.setName("ThanhPho");
			PanelCity.permission = (UIConfigModelManager.getInstance().getPlainText(label2.getText()));
			label2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					try {
						JButton btn = (JButton) evt.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						PanelCity pnCity = new PanelCity();
						pnCity.setName("ThanhPho");
						DialogMain dialog = new DialogMain(pnCity);
						dialog.setIcon("country1.png");
						dialog.setTitle("Thành phố");
						dialog.setName("dlThanhPho");
						dialog.setModal(true);
						dialog.setVisible(true);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			label2.addMouseListener(new MouseEventClickButton("country1.png", "country2.png", "country3.png"));
			add(label2);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label2.getText()));

			// NGÔN NGỮ
			JButton label3 = CreateButton("<html>Ngôn ngữ</html>");
			label3.setActionCommand("1");
			label3.setFocusPainted(false);
			label3.setName("Ngon Ngu");
			label3.setIcon(new ImageIcon(getClass().getResource("icon/honnhan1.png")));
			PanelOption.permission1 = (UIConfigModelManager.getInstance().getPlainText(label3.getText()));
			label3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					try {
						JButton btn = (JButton) evt.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						PanelOption pnOption = new PanelOption("config", "LocaleService", "language");
						pnOption.setName("Ngon Ngu");
						DialogMain dialog = new DialogMain(pnOption);
						dialog.setIcon("honnhan1.png");
						dialog.setName("dlNgonNgu");
						dialog.setTitle("Ngôn ngữ");
						dialog.setModal(true);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			label3.addMouseListener(new MouseEventClickButton("honnhan1.png", "honnhan2.png", "honnhan3.png"));
			add(label3);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label3.getText()));

			// TIỀN TỆ
						JButton label6 = CreateButton("<html>Tiền tệ</html>");
						label6.setActionCommand("1");
						label6.setFocusPainted(false);
						label6.setIcon(new ImageIcon(getClass().getResource("icon/tien1.png")));
						label6.setName("TienTe");
						PanelCurrency.permission = (UIConfigModelManager.getInstance().getPlainText(label6.getText()));
						label6.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								try {
									JButton btn = (JButton) evt.getSource();
									if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
										DialogJurisdiction.getInstace().setVisible(true);
										return;
									}
									PanelCurrency pnCurrencyr = new PanelCurrency();
									pnCurrencyr.setName("TienTe");
									DialogMain dialog = new DialogMain(pnCurrencyr);
									dialog.setIcon("tien1.png");
									dialog.setName("dlTienTe");
									dialog.setTitle("Tiền tệ");
									dialog.setModal(true);
									dialog.setLocationRelativeTo(null);
									dialog.setVisible(true);

								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						label6.addMouseListener(new MouseEventClickButton("tien1.png", "tien2.png", "tien3.png"));
						add(label6);
						UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label6.getText()));

						

			// THÊM MÃ TỰ SINH
			JButton label5 = CreateButton("<html>Q.Lý Mã<br>hóa đơn</br></html>");
			label5.setIcon(new ImageIcon(getClass().getResource("icon/cauhinh1.png")));
			label5.setName("ThemMaTuSinh");
			label5.setFocusPainted(false);
			label5.addMouseListener(new MouseEventClickButton("cauhinh1.png", "cauhinh2.png", "cauhinh3.png"));
			add(label5);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label5.getText()));
			label5.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						PaneSetInvoice invoice = new PaneSetInvoice();
						invoice.setName("ThemMaTuSinh");
						
						DialogResto dialog = new DialogResto(invoice, true, 2000, 900);

						dialog.dispose();
						dialog.setUndecorated(true);
						dialog.setIcon("cauhinh1.png");
						dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
						dialog.setTitle("Q.Lý Mã hóa đơn");
						dialog.setModal(true);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			
			//QUẢN LÝ MÃ
			JButton label11 = CreateButton("<html>Tự động<br>gửi mail</br></html>");
			label11.setActionCommand("3");
			label11.setFocusPainted(false);
			label11.setForeground(Color.black);
			label11.setIcon(new ImageIcon(getClass().getResource("icon/cauhinh1.png")));
			label11.addMouseListener(new MouseEventClickButton("cauhinh1.png", "cauhinh2.png", "cauhinh3.png"));
			add(label11);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label11.getText()));
			label11.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						try {
							PanelAutoSendEmail autoBackupData = new PanelAutoSendEmail();
							DialogMain dialogMain = new DialogMain(autoBackupData, 245, 160);
							dialogMain.setModal(true);
							dialogMain.setIcon("saoluu1.png");
							dialogMain.setTitle("Tự động gửi mail quản lý hóa đơn");
							dialogMain.setLocationRelativeTo(null);
							dialogMain.setVisible(true);
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					
				}
			});

			
			// CẤU HÌNH HỆ THỐNG
			JButton label8 = CreateButton("<html>Cấu hình<br>hệ thống</br></html>");
			label8.setIcon(new ImageIcon(getClass().getResource("icon/cauhinh1.png")));
			label8.setFocusPainted(false);
			label8.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					try {
						JButton btn = (JButton) evt.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						PanelSystemConfig systemConfig = new PanelSystemConfig();
						DialogResto dialog = new DialogResto(systemConfig, false, 0, 110);
						dialog.setIcon("cauhinh1.png");
						dialog.setLocationRelativeTo(null);
						dialog.setTitle("Cấu hình hệ thống");
						dialog.setVisible(true);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			label8.addMouseListener(new MouseEventClickButton("cauhinh1.png", "cauhinh2.png", "cauhinh3.png"));
			add(label8);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label8.getText()));

			// TEST DEMO
			JButton label9 = CreateButton("<html>Test<br>& Demo</br></html>");
			label9.setEnabled(false);
			label9.setFocusPainted(false);
			label9.setIcon(new ImageIcon(getClass().getResource("icon/tkbophan1.png")));
			label9.addMouseListener(new MouseEventClickButton("tkbophan1.png", "tkbophan2.png", "tkbophan3.png"));
			label9.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JButton btn = (JButton) e.getSource();
					if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
						DialogJurisdiction.getInstace().setVisible(true);
						return;
					}
					PanelTestAll panelTestAll = new PanelTestAll();
					DialogResto dialogResto = new DialogResto(panelTestAll, true, 650, 100);
					dialogResto.setIcon("tkbophan1.png");
					dialogResto.setBtnExt1(panelTestAll.getBtnEdit());
					dialogResto.setBtnExt2(panelTestAll.getBtnRemove());
					dialogResto.setSize(Toolkit.getDefaultToolkit().getScreenSize());
					dialogResto.setTitle("Test & Demo");
					dialogResto.setAlwaysOnTop(true);
					dialogResto.setLocationRelativeTo(null);
					dialogResto.setVisible(true);
				}
			});
			add(label9);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label9.getText()));
		
			//NHẬP DỮ LIỆU TỪ FILE EXCEL
			JButton label10 = CreateButton("<html>Thiết lập<br>& ngày DS</br></html>");
			label10.setFocusPainted(false);
			label10.setIcon(new ImageIcon(getClass().getResource("icon/cauhinh1.png")));
			label10.addMouseListener(new MouseEventClickButton("cauhinh1.png", "cauhinh2.png", "cauhinh3.png"));
			add(label10);
			label10.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//doInputDataExcel();
					PaneSetDate panel = new PaneSetDate();
					DialogResto dialog = new DialogResto(panel, false, 550, 330);
					dialog.setIcon("thuchi1.png");
					panel.setName("panelInstituteWarehouse");
					dialog.setTitle("Thiết lập ngày xem danh sách");
					dialog.setModal(true);
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
					
				}

				private void doInputDataExcel() {
					final DataExcel data = new DataExcel();
 
						      JFileChooser fileChooser = new JFileChooser();
						      String[] extension = {".xlsx", ".xls"};
						      fileChooser.setFileFilter(new FileFilterExtension(extension));
						      int i = fileChooser.showOpenDialog(MenuRightUnder.this);
						      if (i == JFileChooser.APPROVE_OPTION) {
						        File file = fileChooser.getSelectedFile();
						        final String fileURL = file.getAbsolutePath();
						        try {
						          new SwingWorker<Void, Void>() {

						            @Override
						            protected Void doInBackground() throws Exception {
						              data.runImport(fileURL);
						              return null;
						            };

						            @Override
						            protected void done() {
						              DialogNotice.getInstace().show();
						            };
						          }.execute();

						        } catch (Exception ex) {

						          JOptionPane.showMessageDialog(null, ex);
						          JOptionPane.showMessageDialog(null, "Failed!");
						        }
						      }
						    }
			});
		}
	}

	static public JButton CreateButton(String text) {
		JButton label = new JButton();
		label.setFont(new Font("Tahoma", Font.BOLD, 12));
		label.setText(text);
		label.setForeground(Color.black);
		label.setContentAreaFilled(false);
		label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		label.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		label.setOpaque(false);
		label.setIconTextGap(10);
		Border border = new BorderUIResource.CompoundBorderUIResource(new BasicBorders.ButtonBorder(Color.white, Color.white, Color.white, Color.white), new BasicBorders.MarginBorder());
		label.setBorder(border);
		return label;
	}
}
