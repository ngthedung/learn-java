package com.hkt.client.swingexp.homescreen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.basic.BasicBorders;

import com.hkt.client.swingexp.app.banhang.PanelBank;
import com.hkt.client.swingexp.app.banhang.ReportStatistics;
import com.hkt.client.swingexp.app.core.DialogJurisdiction;
import com.hkt.client.swingexp.app.core.DialogList;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogReport;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.MouseEventClickButton;
import com.hkt.client.swingexp.app.hethong.DialogInfo;
import com.hkt.client.swingexp.app.hethong.PanelOKRestore;
import com.hkt.client.swingexp.app.khachhang.PanelBankTransaction;
import com.hkt.client.swingexp.app.khachhang.PanelCreditTransaction;
import com.hkt.client.swingexp.app.khachhang.PanelGroupCustomers;
import com.hkt.client.swingexp.app.khachhang.PanelGroupPartnerByMark;
import com.hkt.client.swingexp.app.khachhang.PanelGroupSupplier;
import com.hkt.client.swingexp.app.khachhang.PanelListPartnerPoint;
import com.hkt.client.swingexp.app.khachhang.PanelManageCustomer;
import com.hkt.client.swingexp.app.khachhang.PanelManagePartnerMoney;
import com.hkt.client.swingexp.app.khachhang.PanelManagePartnerPoint;
import com.hkt.client.swingexp.app.khachhang.PanelManageProvider;
import com.hkt.client.swingexp.app.khachhang.PanelManageSupplier;
import com.hkt.client.swingexp.app.khachhang.PanelPointTransaction;
import com.hkt.client.swingexp.app.khachhang.PartnerIsOrganization;
import com.hkt.client.swingexp.app.khachhang.PartnerIsUser;
import com.hkt.client.swingexp.app.khachhang.list.TableListCreditTransaction;
import com.hkt.client.swingexp.app.khachhang.list.TableListCustomer;
import com.hkt.client.swingexp.app.khachhang.list.TableListPointTransaction;
import com.hkt.client.swingexp.app.license.LicenseManager;
import com.hkt.client.swingexp.app.nhansu.SupplierIsOrganization;
import com.hkt.client.swingexp.app.nhansu.SupplierIsUser;
import com.hkt.client.swingexp.app.nhansu.list.TableListSupplier;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.swingexp.app.report.table.TableDemo;

@SuppressWarnings("serial")
public class MenuRightKhachHangDoiTac extends JPanel {

	public MenuRightKhachHangDoiTac() {
		setName("MenuRightKhachHangDoiTac");
		setLayout(new GridLayout(2, 1));
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()));
		MenuRightTop menuRightTop = new MenuRightTop();
		JPanel panelTop = new JPanel();
		panelTop.setBackground(Color.WHITE);
		panelTop.setLayout(new BorderLayout());
		panelTop.add(menuRightTop, BorderLayout.CENTER);
		add(panelTop);

		MenuRightUnder menuRightUnder = new MenuRightUnder();
		JPanel panelBot = new JPanel();
		panelBot.setLayout(new BorderLayout());
		panelBot.setBackground(Color.WHITE);
		panelBot.add(menuRightUnder, BorderLayout.CENTER);
		add(panelBot);
	}

	public static class MenuRightTop extends JPanel {

		public MenuRightTop() {
			setLayout(new GridLayout(3, 3));
			setBackground(Color.WHITE);
			setBorder(BorderFactory.createTitledBorder(null, "Chức năng cơ bản", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 16), new java.awt.Color(204, 0, 0)));

			// THÊM KHÁCH HÀNG
			JButton label1 = CreateButton("<html>Thêm<br>Khách</br><br>hàng</br></html>");
			label1.setActionCommand("2");
			label1.setFocusPainted(false);
			label1.setForeground(Color.black);
			label1.setName("AddCustomer");
			DialogAddCustomer.permission = (UIConfigModelManager.getInstance().getPlainText(label1.getText()));
			PartnerIsOrganization.permission = (UIConfigModelManager.getInstance().getPlainText(label1.getText()));
			PartnerIsUser.permission = (UIConfigModelManager.getInstance().getPlainText(label1.getText()));
			label1.setIcon(new ImageIcon(getClass().getResource("icon/doanhnghiep1.png")));
			label1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JButton btn = (JButton) e.getSource();
					if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
						DialogJurisdiction.getInstace().setVisible(true);
						return;
					}
				
					DialogAddCustomer dialogAddCustome = new DialogAddCustomer();
					DialogResto dialog = new DialogResto(dialogAddCustome, false, 0, 120);
					dialog.setBtnSave(false);
//					dialog.setBtnExit(false);
					dialog.setName("dlInfo");
					dialog.setIcon("doanhnghiep1.png");
					dialog.setLocationRelativeTo(null);
					dialog.setTitle("Thêm khách hàng");
					dialog.setVisible(true);
				}
			});
			label1.addMouseListener(new MouseEventClickButton("doanhnghiep1.png", "doanhnghiep2.png", "doanhnghiep3.png"));
			add(label1);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label1.getText()));

			// THÊM NCC
			JButton label2 = CreateButton("<html>Thêm nhà<br>Cung cấp</br></html>");
			label2.setForeground(Color.black);
			label2.setName("AddSupplier");
			label2.setFocusPainted(false);
			DialogAddSupplier.permission = (UIConfigModelManager.getInstance().getPlainText(label2.getText()));
			SupplierIsOrganization.permission = (UIConfigModelManager.getInstance().getPlainText(label2.getText()));
			SupplierIsUser.permission = (UIConfigModelManager.getInstance().getPlainText(label2.getText()));
			label2.setIcon(new ImageIcon(getClass().getResource("icon/usernhom1.png")));
			label2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JButton btn = (JButton) e.getSource();
					if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
						DialogJurisdiction.getInstace().setVisible(true);
						return;
					}
					DialogAddSupplier dialogAddSupplier = new DialogAddSupplier();
					DialogResto dialog = new DialogResto(dialogAddSupplier, false, 0, 120);
					dialog.setBtnSave(false);
					dialog.setIcon("usernhom1.png");
					dialog.setName("dlInfo");
					dialog.setLocationRelativeTo(null);
					dialog.setTitle("Thêm nhà cung cấp");
					dialog.setVisible(true);
				}
			});
			label2.addMouseListener(new MouseEventClickButton("usernhom1.png", "usernhom2.png", "usernhom3.png"));
			add(label2);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label2.getText()));

			// QL NHÓM NHÀ CUNG CẤP
			JButton label5 = CreateButton("<html>QL nhóm<br>Nhà cung</br><br>cấp</br></html>");
			label5.setName("QLNhomNhaPhanPhoi");
			label5.setActionCommand("1");
			label5.setFocusPainted(false);
			PanelGroupSupplier.permission = (UIConfigModelManager.getInstance().getPlainText(label5.getText()));
			label5.setIcon(new ImageIcon(getClass().getResource("icon/canhan1.png")));
			label5.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						PanelGroupSupplier panelGroupSupplier = new PanelGroupSupplier();
						panelGroupSupplier.setName("QLNhomNhaPhanPhoi");
						DialogMain dialog = new DialogMain(panelGroupSupplier);
						dialog.setIcon("canhan1.png");
						dialog.setName("dialogGroupSupplier");
						dialog.setTitle("Quản lý nhóm nhà cung cấp");
						dialog.setModal(true);
						dialog.setVisible(true);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			label5.addMouseListener(new MouseEventClickButton("canhan1.png", "canhan2.png", "canhan3.png"));
			add(label5);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label5.getText()));

			// DS KHÁCH HÀNG
			JButton label4 = CreateButton("<html>Danh sách<br>Khách</br><br>hàng</br></html>");
			label4.setForeground(Color.black);
			label4.setFocusPainted(false);
			label4.setName("listPartner");
			TableListCustomer.permission = (UIConfigModelManager.getInstance().getPlainText(label4.getText()));
			label4.setIcon(new ImageIcon(getClass().getResource("icon/partners1.png")));
			label4.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						TableListCustomer tbCustomer = new TableListCustomer();
						tbCustomer.setToolTipText(UIConfigModelManager.getInstance().getPlainText(btn.getText()));
						tbCustomer.setName("tblistPartner");
						DialogList dialog = new DialogList(tbCustomer);
						dialog.setIcon("partners1.png");
						dialog.setName("dllistPartner");
						dialog.setComponent4(tbCustomer.getCheckBox(), "");
						dialog.setTitle("Danh sách khách hàng");
						dialog.setModal(true);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);

					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			label4.addMouseListener(new MouseEventClickButton("partners1.png", "partners2.png", "partners3.png"));
			add(label4);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label4.getText()));

			// DS NHÀ CC
			JButton label50 = CreateButton("<html>Danh sách<br>Nhà cung</br><br>cấp</br></html>");
			label50.setForeground(Color.BLACK);
			label50.setFocusPainted(false);
			label50.setName("ListSuppliers");
			TableListSupplier.permission = (UIConfigModelManager.getInstance().getPlainText(label1.getText()));
			label50.setIcon(new ImageIcon(getClass().getResource("icon/quyen1.png")));
			label50.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						TableListSupplier tbSupplier = new TableListSupplier();
						tbSupplier.setToolTipText(UIConfigModelManager.getInstance().getPlainText(btn.getText()));
						tbSupplier.setName("tbListSuppliers");
						DialogList dialog = new DialogList(tbSupplier);
						dialog.setIcon("quyen1.png");
						dialog.setComponent4(tbSupplier.getCheckBox(), "");
						dialog.setName("dlListSuppliers");
						dialog.setTitle("Danh sách nhà cung cấp");
						dialog.setModal(true);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);

					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			label50.addMouseListener(new MouseEventClickButton("quyen1.png", "quyen2.png", "quyen3.png"));
			add(label50);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label50.getText()));

			// QL NHÓM KH
			JButton label80 = CreateButton("<html>QL nhóm<br>Khách</br<br>hàng</br></html>");
			label80.setActionCommand("1");
			label80.setFocusPainted(false);
			label80.setIcon(new ImageIcon(getClass().getResource("icon/nhom1.png")));
			label80.setName("QLNhomKhachHang");
			PanelGroupCustomers.permission = (UIConfigModelManager.getInstance().getPlainText(label5.getText()));
			label80.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						PanelGroupCustomers panelGroupCustomers = new PanelGroupCustomers();
						panelGroupCustomers.setName("QLNhomKhachHang");
						DialogMain dialog = new DialogMain(panelGroupCustomers);
						dialog.setIcon("nhom1.png");
						dialog.setTitle("Quản lý nhóm khách hàng");
						dialog.setName("dlGroupCustomer");
						dialog.setModal(true);
						dialog.setVisible(true);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			label80.addMouseListener(new MouseEventClickButton("nhom1.png", "nhom2.png", "nhom3.png"));
			add(label80);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label80.getText()));

			// QUẢN LÝ KH
			JButton label7 = CreateButton("<html>QL Khách<br>hàng</br></html>");
			label7.setActionCommand("3");
			label7.setFocusPainted(false);
			label7.setName("QuanLyKhachHang");
			label7.setIcon(new ImageIcon(getClass().getResource("icon/partner1.png")));
			label7.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						PanelManageCustomer panel = new PanelManageCustomer();
						panel.setName("QuanLyKhachHang");
						panel.setToolTipText(UIConfigModelManager.getInstance().getPlainText(btn.getText()));
						DialogResto dialog = new DialogResto(panel, false, 950, 550);
						dialog.dispose();
				        dialog.setUndecorated(true);
						dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
						dialog.setIcon("partner1.png");
						dialog.setName("dlQuanLyKhachHang");
						dialog.setTitle("Quản lý khách hàng");
						dialog.setLocationRelativeTo(null);
						dialog.setModal(true);
						dialog.setVisible(true);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			label7.addMouseListener(new MouseEventClickButton("partner1.png", "partner2.png", "partner3.png"));
			add(label7);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label7.getText()));

			// QL NHÀ CUNG CẤP
			JButton label8 = CreateButton("<html>QL Nhà<br>cung cấp</br></html>");
			label8.setActionCommand("3");
			label8.setFocusPainted(false);
			label8.setName("QuanLyNhaPhanPhoi");
			label8.setIcon(new ImageIcon(getClass().getResource("icon/doanhnghiep1.png")));
			label8.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						PanelManageSupplier panel = new PanelManageSupplier();
						panel.setName("QuanLyNhaPhanPhoi");
						panel.setToolTipText(UIConfigModelManager.getInstance().getPlainText(btn.getText()));
						DialogResto dialog = new DialogResto(panel, false, 950, 550);
						dialog.dispose();
				        dialog.setUndecorated(true);
						dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
						dialog.setIcon("doanhnghiep1.png");
						dialog.setName("dlQuanLyNhaPhanPhoi");
						dialog.setTitle("Quản lý nhà cung cấp");
						dialog.setLocationRelativeTo(null);
						dialog.setModal(true);
						dialog.setVisible(true);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			label8.addMouseListener(new MouseEventClickButton("doanhnghiep1.png", "doanhnghiep2.png", "doanhnghiep3.png"));
			add(label8);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label8.getText()));

			// THIẾT LẬP ĐT CHUNG
			JButton label9 = CreateButton("<html>Thiết lập<br>KH chung</br></html>");
			label9.setActionCommand("3");
			label9.setFocusPainted(false);
			label9.setName("ThietLapDoiTacChung");
			label9.setIcon(new ImageIcon(getClass().getResource("icon/lappartner1.png")));
			label9.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						PanelManageProvider panel = new PanelManageProvider();
						panel.setName("ThietLapDoiTacChung");
						panel.setToolTipText(UIConfigModelManager.getInstance().getPlainText(btn.getText()));
						DialogResto dialog = new DialogResto(panel, false, 950, 550);
						dialog.dispose();
				        dialog.setUndecorated(true);
						dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
						dialog.setIcon("lappartner1.png");
						dialog.setName("dlThietLapDoiTacChung");
						dialog.setTitle("Thiết lập khách hàng chung");
						dialog.setLocationRelativeTo(null);
						dialog.setModal(true);
						dialog.setVisible(true);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			label9.addMouseListener(new MouseEventClickButton("lappartner1.png", "lappartner2.png", "lappartner3.png"));
			add(label9);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label9.getText()));

		}
	}

	public static class MenuRightUnder extends JPanel {
		public MenuRightUnder() {
			setLayout(new GridLayout(3, 3));
			setBackground(Color.WHITE);
			setBorder(BorderFactory.createTitledBorder(null, "Chức năng nâng cao", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 16), new java.awt.Color(204, 0, 0)));
			// LẬP CƠ CHẾ ĐIỂM
			JButton label1 = CreateButton("<html>Lập cơ<br>chế điểm</br></html>");
			label1.setActionCommand("1");
			label1.setFocusPainted(false);
			label1.setIcon(new ImageIcon(getClass().getResource("icon/dskm1.png")));
			PanelListPartnerPoint.permission = (UIConfigModelManager.getInstance().getPlainText(label1.getText()));
			label1.setName("LapCoCheDiem");
			label1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Point.name())) {
							PanelListPartnerPoint panelList = new PanelListPartnerPoint();
							panelList.setName("LapCoCheDiem");
							DialogMain dialog = new DialogMain(panelList);
							dialog.setIcon("dskm1.png");
							dialog.setTitle("Lập cơ chế điểm");
							dialog.setName("dlQuanLyDiemKH");
							dialog.setVisible(true);
						} else {
							PanelOKRestore panel = new PanelOKRestore(
									"Bạn chưa đăng ký bản quyền với module chức năng này!",
									"Hãy liên hệ với công ty HKTConsultant!");
							DialogResto dialog1 = new DialogResto(panel, false, 0, 120);
							dialog1.setTitle("Bản quyền");
							dialog1.setLocationRelativeTo(null);
							dialog1.setModal(true);	
							dialog1.setVisible(true);
							
							DialogInfo di = new DialogInfo();
							DialogResto dialog = new DialogResto(di, false, 200, 450);
							dialog.setName("dlInfo");
							dialog.setLocationRelativeTo(null);
							dialog.setTitle("Liên hệ");
							dialog.setVisible(true);
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			label1.addMouseListener(new MouseEventClickButton("dskm1.png", "dskm2.png", "dskm3.png"));
			add(label1);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label1.getText()));
			
			// SỬ DỤNG TRẢ TRƯỚC
						JButton label8 = CreateButton("<html>Sử dụng<br>trả trước</br></html>");
						label8.setActionCommand("3");
						label8.setFocusPainted(false);
						label8.setIcon(new ImageIcon(getClass().getResource("icon/tratruoccon1.png")));
						label8.setName("SuDungTraTruoc");
						PanelCreditTransaction.permisson = (UIConfigModelManager.getInstance().getPlainText(label8.getText()));
						label8.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								try {
									JButton btn = (JButton) e.getSource();
									if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
										DialogJurisdiction.getInstace().setVisible(true);
										return;
									}
									if (LicenseManager.getInstance().getLicense(LicenseManager.module.Point.name())) {
										PanelCreditTransaction panel = new PanelCreditTransaction();
										panel.setName("SuDungTraTruoc");
										DialogMain dialog = new DialogMain(panel);
										dialog.setIcon("tratruoccon1.png");
										dialog.setTitle("Sử dụng trả trước");
										dialog.setName("dlSuDungTraTruoc");
										dialog.setModal(true);
										dialog.setLocationRelativeTo(null);
										dialog.setVisible(true);
									} else {
										PanelOKRestore panel = new PanelOKRestore(
												"Bạn chưa đăng ký bản quyền với module chức năng này!",
												"Hãy liên hệ với công ty HKTConsultant!");
										DialogResto dialog1 = new DialogResto(panel, false, 0, 120);
										dialog1.setTitle("Bản quyền");
										dialog1.setLocationRelativeTo(null);
										dialog1.setModal(true);	
										dialog1.setVisible(true);
										
										DialogInfo di = new DialogInfo();
										DialogResto dialog = new DialogResto(di, false, 200, 450);
										dialog.setName("dlInfo");
										dialog.setLocationRelativeTo(null);
										dialog.setTitle("Liên hệ");
										dialog.setVisible(true);
									}
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}
						});
						label8.addMouseListener(new MouseEventClickButton("tratruoccon1.png", "tratruoccon2.png", "tratruoccon3.png"));
						add(label8);
						UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label8.getText()));

			// DÙNG ĐIỂM
			JButton label2 = CreateButton("<html>Dùng<br>điểm</br></html>");
			label2.setActionCommand("3");
			label2.setFocusPainted(false);
			label2.setIcon(new ImageIcon(getClass().getResource("icon/pointsused1.png")));
			label2.setName("DungDiemTrucTiep");
			PanelPointTransaction.permission = (UIConfigModelManager.getInstance().getPlainText(label2.getText()));
			label2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Point.name())) {
							PanelPointTransaction panel = new PanelPointTransaction();
							panel.setName("DungDiemTrucTiep");
							DialogMain dialog = new DialogMain(panel);
							dialog.setIcon("pointsused1.png");
							dialog.setTitle("Dùng điểm");
							dialog.setName("dlDungDiemTrucTiep");
							dialog.setModal(true);
							dialog.setLocationRelativeTo(null);
							dialog.setVisible(true);
						} else {
							PanelOKRestore panel = new PanelOKRestore(
									"Bạn chưa đăng ký bản quyền với module chức năng này!",
									"Hãy liên hệ với công ty HKTConsultant!");
							DialogResto dialog1 = new DialogResto(panel, false, 0, 120);
							dialog1.setTitle("Bản quyền");
							dialog1.setLocationRelativeTo(null);
							dialog1.setModal(true);	
							dialog1.setVisible(true);
							
							DialogInfo di = new DialogInfo();
							DialogResto dialog = new DialogResto(di, false, 200, 450);
							dialog.setName("dlInfo");
							dialog.setLocationRelativeTo(null);
							dialog.setTitle("Liên hệ");
							dialog.setVisible(true);
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			label2.addMouseListener(new MouseEventClickButton("pointsused1.png", "pointsused2.png", "pointsused3.png"));
			add(label2);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label2.getText()));

			

			// QUẨN LÝ ĐIỂM ĐT
			JButton label7 = CreateButton("<html>Quản lý<br>điểm ĐT</br></html>");
			label7.setForeground(Color.black);
			label7.setFocusPainted(false);
			label7.setIcon(new ImageIcon(getClass().getResource("icon/sudungtratruoc1.png")));
			label7.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Point.name())) {
							PanelManagePartnerPoint panelManagePartnerPoint = new PanelManagePartnerPoint();
							DialogResto dialog = new DialogResto(panelManagePartnerPoint, true, 600, 100);
							dialog.dispose();
							dialog.setIcon("sudungtratruoc1.png");
							dialog.setIcon("sudungtratruoc1.png");
					        dialog.setUndecorated(true);
							dialog.setTitle("Quản lý điểm đối tác");
							dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
							dialog.setModal(true);
							dialog.setLocationRelativeTo(null);
							dialog.setVisible(true);
						} else {
							PanelOKRestore panel = new PanelOKRestore(
									"Bạn chưa đăng ký bản quyền với module chức năng này!",
									"Hãy liên hệ với công ty HKTConsultant!");
							DialogResto dialog1 = new DialogResto(panel, false, 0, 120);
							dialog1.setTitle("Bản quyền");
							dialog1.setLocationRelativeTo(null);
							dialog1.setModal(true);	
							dialog1.setVisible(true);
							
							DialogInfo di = new DialogInfo();
							DialogResto dialog = new DialogResto(di, false, 200, 450);
							dialog.setName("dlInfo");
							dialog.setLocationRelativeTo(null);
							dialog.setTitle("Liên hệ");
							dialog.setVisible(true);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			label7.addMouseListener(new MouseEventClickButton("sudungtratruoc1.png", "sudungtratruoc2.png", "sudungtratruoc3.png"));
			add(label7);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label7.getText()));
			
			// QL TRẢ TRƯỚC
						JButton label4 = CreateButton("<html>Quản lý<br>trả trước</br></html>");
						label4.setForeground(Color.black);
						label4.setFocusPainted(false);
						label4.setIcon(new ImageIcon(getClass().getResource("icon/pointsql1.png")));
						label4.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								try {
									JButton btn = (JButton) e.getSource();
									if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
										DialogJurisdiction.getInstace().setVisible(true);
										return;
									}
									if (LicenseManager.getInstance().getLicense(LicenseManager.module.Point.name())) {
										PanelManagePartnerMoney panelManagePartnerMoney = new PanelManagePartnerMoney();
										DialogResto dialog = new DialogResto(panelManagePartnerMoney, true, 600, 100);
										dialog.dispose();
								        dialog.setUndecorated(true);
								        dialog.setIcon("pointsql1.png");
										dialog.setTitle("Quản lý trả trước");
										dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
										dialog.setModal(true);
										dialog.setLocationRelativeTo(null);
										dialog.setVisible(true);
									} else {
										PanelOKRestore panel = new PanelOKRestore(
												"Bạn chưa đăng ký bản quyền với module chức năng này!",
												"Hãy liên hệ với công ty HKTConsultant!");
										DialogResto dialog1 = new DialogResto(panel, false, 0, 120);
										dialog1.setTitle("Bản quyền");
										dialog1.setLocationRelativeTo(null);
										dialog1.setModal(true);	
										dialog1.setVisible(true);
										
										DialogInfo di = new DialogInfo();
										DialogResto dialog = new DialogResto(di, false, 200, 450);
										dialog.setName("dlInfo");
										dialog.setLocationRelativeTo(null);
										dialog.setTitle("Liên hệ");
									}
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}
						});
						label4.addMouseListener(new MouseEventClickButton("pointsql1.png", "pointsql2.png", "pointsql3.png"));
						add(label4);
						UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label4.getText()));

			// DS ĐIỂM DÙNG
			JButton label3 = CreateButton("<html>Danh sách<br>điểm dùng</br></html>");
			label3.setName("DanhSachDiemDung");
			label3.setFocusPainted(false);
			label3.setIcon(new ImageIcon(getClass().getResource("icon/pointsds1.png")));
			label3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Point.name())) {
							TableListPointTransaction table = new TableListPointTransaction();
							table.setName("tbDanhSachDiemDung");
							DialogList dialog = new DialogList(table);
							dialog.setIcon("pointsds1.png");
							dialog.setTitle("Danh sách điểm dùng");
							dialog.setName("dlDanhSachDiemDung");
							dialog.setModal(true);
							dialog.setLocationRelativeTo(null);
							dialog.setVisible(true);
						} else {
							PanelOKRestore panel = new PanelOKRestore(
									"Bạn chưa đăng ký bản quyền với module chức năng này!",
									"Hãy liên hệ với công ty HKTConsultant!");
							DialogResto dialog1 = new DialogResto(panel, false, 0, 120);
							dialog1.setTitle("Bản quyền");
							dialog1.setLocationRelativeTo(null);
							dialog1.setModal(true);	
							dialog1.setVisible(true);
							
							DialogInfo di = new DialogInfo();
							DialogResto dialog = new DialogResto(di, false, 200, 450);
							dialog.setName("dlInfo");
							dialog.setLocationRelativeTo(null);
							dialog.setTitle("Liên hệ");
							dialog.setVisible(true);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			label3.addMouseListener(new MouseEventClickButton("pointsds1.png", "pointsds2.png", "pointsds3.png"));
			add(label3);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label3.getText()));

			

			// LẬP ĐIỂM NHÓM ĐT
			JButton label5 = CreateButton("<html>Lập điểm<br>nhóm KH</br></html>");
			label5.setActionCommand("2");
			label5.setFocusPainted(false);
			label5.setForeground(Color.black);
			label5.setIcon(new ImageIcon(getClass().getResource("icon/pointgroup1.png")));
			PanelGroupPartnerByMark.permission = (UIConfigModelManager.getInstance().getPlainText(label5.getText()));
			label5.setName("LapDiemNhomDoiTac");
			label5.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Point.name())) {
							PanelGroupPartnerByMark pndialog = new PanelGroupPartnerByMark();
							pndialog.setName("LapDiemNhomDoiTac");
							DialogMain dialog = new DialogMain(pndialog);
							dialog.getBtnExt().setVisible(true);
							dialog.getBtnExt().setText("Cập nhật");
							dialog.getBtnExt().setIcon(null);
							dialog.getBtnExt().addActionListener(pndialog.getActionListener());
							dialog.setIcon("pointgroup1.png");
							dialog.setTitle("Lập điểm nhóm khách hàng");
							dialog.setName("dlLapDiemNhomDoiTac");
							dialog.setModal(true);
							dialog.setLocationRelativeTo(null);
							dialog.setVisible(true);
						} else {
							PanelOKRestore panel = new PanelOKRestore(
									"Bạn chưa đăng ký bản quyền với module chức năng này!",
									"Hãy liên hệ với công ty HKTConsultant!");
							DialogResto dialog1 = new DialogResto(panel, false, 0, 120);
							dialog1.setTitle("Bản quyền");
							dialog1.setLocationRelativeTo(null);
							dialog1.setModal(true);	
							dialog1.setVisible(true);
							
							DialogInfo di = new DialogInfo();
							DialogResto dialog = new DialogResto(di, false, 200, 450);
							dialog.setName("dlInfo");
							dialog.setLocationRelativeTo(null);
							dialog.setTitle("Liên hệ");
							dialog.setVisible(true);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			label5.addMouseListener(new MouseEventClickButton("pointgroup1.png", "pointgroup2.png", "pointgroup3.png"));
			add(label5);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label5.getText()));
			

			// DS TRẢ TRƯỚC
			JButton label9 = CreateButton("<html>DS trả<br>trước</br></html>");
			label9.setName("DanhSachTraTruoc");
			label9.setFocusPainted(false);
			label9.setIcon(new ImageIcon(getClass().getResource("icon/tratruoc1.png")));
			label9.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						TableListCreditTransaction panel = new TableListCreditTransaction();
						panel.setName("tbDanhSachTraTruoc");
						DialogList dialog = new DialogList(panel);
						dialog.setIcon("tratruoc1.png");
						dialog.setTitle("Danh sách trả trước");
						dialog.setName("dlDanhSachTraTruoc");
						dialog.setModal(true);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			label9.addMouseListener(new MouseEventClickButton("tratruoc1.png", "tratruoc2.png", "tratruoc3.png"));
			add(label9);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label9.getText()));
		
			// CHƯA CÓ
//			JButton label50 = CreateButton("<html></html>");
//			label50.setFocusPainted(false);
//			add(label50);
			
//			/**
//			 * GIAO DỊCH NGÂN HÀNG
//			 */
			JButton label50 = CreateButton("<html>Giao dịch<br>ngân hàng</br></html>");
			label50.setFocusPainted(false);
			label50.setName("GiaoDichNganHang");
			PanelBank.permission = (UIConfigModelManager.getInstance().getPlainText(label50.getText()));
			label50.setIcon(new ImageIcon(getClass().getResource("icon/bank1.png")));
			label50.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						PanelBankTransaction panelBankTransaction = new PanelBankTransaction();
						panelBankTransaction.setName("GiaoDichNganHang");
						DialogResto dialog = new DialogResto(panelBankTransaction, true, 700, 500);
						dialog.setName("dlGiaoDichNganHang");
						dialog.dispose();
						dialog.setUndecorated(true);
						dialog.setIcon("bank1.png");
						dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
						dialog.setTitle("Giao Dịch Ngân Hàng");
						dialog.setModal(true);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			label50.addMouseListener(new MouseEventClickButton("bank1.png", "bank2.png", "bank3.png"));
			add(label50);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label8.getText()));
		}
	}
			/**
			 * Table Demo
			 */
//			JButton label50 = CreateButton("<html>Table<br>Demo</br></html>");
//			label50.setFocusPainted(false);
//			label50.setName("TableDemo");
//			PanelBank.permission = (UIConfigModelManager.getInstance().getPlainText(label50.getText()));
//			label50.setIcon(new ImageIcon(getClass().getResource("icon/bank1.png")));
//			label50.addActionListener(new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					try {
//						JButton btn = (JButton) e.getSource();
//						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
//							DialogJurisdiction.getInstace().setVisible(true);
//							return;
//						}
						DialogReport dialog = new DialogReport(new ReportStatistics(new JTable(), ReportStatistics.TypeReport.TKThoiGian));
//						dialog.setName("dlTableDemo");
//						dialog.dispose();
//						dialog.setUndecorated(true);
//						dialog.setIcon("bank1.png");
//						dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
//						dialog.setTitle("Table Demo");
//						dialog.setModal(true);
//						dialog.setLocationRelativeTo(null);
//						dialog.setVisible(true);
//					} catch (Exception e1) {
//						e1.printStackTrace();
//					}
//				}
//			});
//			label50.addMouseListener(new MouseEventClickButton("bank1.png", "bank2.png", "bank3.png"));
//			add(label50);
//			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label8.getText()));
//		}
//	}

	static public JLabel CreateLabel(String text) {
		JLabel label = new JLabel(text);
		label.setBorder(BorderFactory.createEtchedBorder());
		label.setFont(new Font("Tahoma", Font.BOLD, 16));
		label.setBorder(null);
		return label;
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
		Border border = new BorderUIResource.CompoundBorderUIResource(new BasicBorders.ButtonBorder(Color.white, Color.white, Color.white,
				Color.white), new BasicBorders.MarginBorder());
		label.setBorder(border);
		return label;
	}

}
