package com.hkt.client.swingexp.homescreen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.basic.BasicBorders;

import com.hkt.client.swingexp.app.banhang.ReportStatistics;
import com.hkt.client.swingexp.app.core.DialogJurisdiction;
import com.hkt.client.swingexp.app.core.DialogList;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogReport;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.MouseEventClickButton;
import com.hkt.client.swingexp.app.hethong.DialogInfo;
import com.hkt.client.swingexp.app.hethong.PanelOKRestore;
import com.hkt.client.swingexp.app.khachhang.TableListCustomerGroup;
import com.hkt.client.swingexp.app.khachhang.TableListindividual;
import com.hkt.client.swingexp.app.license.LicenseManager;
import com.hkt.client.swingexp.app.nhansu.AccountIsOrganization;
import com.hkt.client.swingexp.app.nhansu.AccountIsUser;
import com.hkt.client.swingexp.app.nhansu.AddEmployeePane;
import com.hkt.client.swingexp.app.nhansu.DialogAuthorization;
import com.hkt.client.swingexp.app.nhansu.EmployeeIsUser;
import com.hkt.client.swingexp.app.nhansu.PanelEmployeeGroupProduct;
import com.hkt.client.swingexp.app.nhansu.PanelToDoCalendar;
import com.hkt.client.swingexp.app.nhansu.list.PanelSortToDoCalendar;
import com.hkt.client.swingexp.app.nhansu.list.TableListEmployees;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.restaurant.entity.Recipe;

@SuppressWarnings("serial")
public class MenuRightNhanSu extends JPanel {
	public MenuRightNhanSu() {
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
		@SuppressWarnings("unused")
		private DialogMain dialog;

		public MenuRightTop() {
			setLayout(new GridLayout(3, 3));
			setBorder(BorderFactory.createTitledBorder(null, "Chức năng cơ bản", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 16), new java.awt.Color(204, 0, 0)));

			// NHÂN SỰ MỚI
			JButton label1 = CreateButton("<html>Nhân sự<br>mới</br></html>");
			label1.setActionCommand("2");
			label1.setFocusPainted(false);
			label1.setForeground(Color.black);
			label1.setName("EmployeeIsUser");
			label1.setIcon(new ImageIcon(getClass().getResource("icon/canhan1.png")));
			EmployeeIsUser.permission = (UIConfigModelManager.getInstance().getPlainText(label1.getText()));
			label1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						EmployeeIsUser screenEmployee = new EmployeeIsUser(null, true);
						screenEmployee.setName("EmployeeIsUser");
						screenEmployee.setPreferredSize(new Dimension(100, 100));
						DialogMain dialog = new DialogMain(screenEmployee, true);
						dialog.setIcon("canhan1.png");
						dialog.setName("dlEmployeeIsUser");
						dialog.setTitle("Nhân sự mới");
						dialog.setModal(true);
						dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);

					} catch (Exception ex) {
						ex.printStackTrace();

					}
				}
			});
			label1.addMouseListener(new MouseEventClickButton("canhan1.png", "canhan2.png", "canhan3.png"));
			add(label1);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label1.getText()));

			// THÊM NHÂN SỰ
			JButton label2 = CreateButton("<html>Chuyển<br>thành</br><br>Nhân sự</br></html>");
			label2.setActionCommand("3");
			label2.setFocusPainted(false);
			label2.setForeground(Color.black);
			label2.setIcon(new ImageIcon(getClass().getResource("icon/canhan1.png")));
			label2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						AddEmployeePane screenEmployee = new AddEmployeePane();
						DialogResto dialog = new DialogResto(screenEmployee, false, 0, 170);
						dialog.setIcon("canhan1.png");
						dialog.setTitle("Chuyển thành Nhân sự");
						try {
							screenEmployee.setToolTipText(UIConfigModelManager.getInstance().getPlainText(btn.getText()));
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						dialog.setModal(true);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			label2.addMouseListener(new MouseEventClickButton("canhan1.png", "canhan2.png", "canhan3.png"));
			add(label2);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label2.getText()));

			
			// DS CÁ NHÂN
			JButton label6 = CreateButton("<html>Danh sách<br>cá nhân</br></html>");
			label6.setForeground(Color.black);
			label6.setFocusPainted(false);
			label6.setName("btnDSCaNhan");
			label6.setIcon(new ImageIcon(getClass().getResource("icon/canhan1.png")));
			AccountIsUser.permission = (UIConfigModelManager.getInstance().getPlainText(label6.getText()));
			label6.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						TableListindividual tbLindividual = new TableListindividual();
						tbLindividual.setToolTipText(UIConfigModelManager.getInstance().getPlainText(btn.getText()));
						tbLindividual.setName("pnDSCaNhan");
						DialogList dialog = new DialogList(tbLindividual);
						dialog.setIcon("canhan1.png");
						dialog.setName("dlDSCaNhan");
						dialog.setTitle("Danh sách cá nhân");
						dialog.setModal(true);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			label6.addMouseListener(new MouseEventClickButton("canhan1.png", "canhan2.png", "canhan3.png"));
			add(label6);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label6.getText()));

			// DS NHÂN SỰ
			JButton label3 = CreateButton("<html>Danh sách<br>nhân sự</br></html>");
			label3.setForeground(Color.black);
			label3.setFocusPainted(false);
			label3.setName("PanelListEmployee");
			label3.setIcon(new ImageIcon(getClass().getResource("icon/nhansu1.png")));
			label3.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						TableListEmployees tbLocation = new TableListEmployees();
						tbLocation.setName("tableEmployee");
						DialogList dialog = new DialogList(tbLocation);
						dialog.setIcon("nhansu1.png");
						dialog.setName("dlPanelListEmployee");
						dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
						dialog.setTitle("Danh sách nhân sự");
						dialog.setModal(true);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			label3.addMouseListener(new MouseEventClickButton("nhansu1.png", "nhansu2.png", "nhansu3.png"));
			add(label3);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label3.getText()));

			// PHÂN QUYỀN CHI TIẾT
			JButton label60 = CreateButton("<html>P.Quyền<br>chi tiết</br></html>");
			label60.setForeground(Color.black);
			label60.setFocusPainted(false);
			label60.setIcon(new ImageIcon(getClass().getResource("icon/quyen1.png")));
			DialogAuthorization.permission = (UIConfigModelManager.getInstance().getPlainText(label60.getText()));
			label60.setForeground(Color.black);
			label60.addMouseListener(new MouseEventClickButton("quyen1.png", "quyen2.png", "quyen3.png"));
			label60.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						DialogAuthorization dialogAuthorization = new DialogAuthorization();
						DialogResto dialog = new DialogResto(dialogAuthorization, true, 600, 360);
						dialog.setName("dlPanelSearchInvoice");
						dialog.setTitle("Phân quyền chi tiết");
						dialog.setIcon("quyen1.png");
						dialog.dispose();
				        dialog.setUndecorated(true);
						dialog.setModal(true);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);

					} catch (Exception ex) {
						ex.printStackTrace();
					}

				}
			});
			add(label60);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label60.getText()));

			// DS DOANH NGHIỆP
			JButton label30 = CreateButton("<html>Danh sách<br>Doanh</br><br>nghiệp</br></html>");
			label30.setForeground(Color.black);
			label30.setFocusPainted(false);
			label30.setIcon(new ImageIcon(getClass().getResource("icon/doanhnghiep1.png")));
			label30.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						final TableListCustomerGroup tbCustomerGroup = new TableListCustomerGroup();
						tbCustomerGroup.setToolTipText(UIConfigModelManager.getInstance().getPlainText(btn.getText()));
						tbCustomerGroup.setName("pnDSDoanhNghiep");
						DialogList dialog = new DialogList(tbCustomerGroup);
						dialog.setIcon("doanhnghiep1.png");
						dialog.setName("dlDSDoanhNghiep");
						dialog.setTitle("Danh sách doanh nghiệp");
						dialog.setModal(true);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			label30.addMouseListener(new MouseEventClickButton("doanhnghiep1.png", "doanhnghiep2.png", "doanhnghiep3.png"));
			add(label30);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label30.getText()));

			// NV NHÓM SP
			JButton label10 = CreateButton("<html>Nhân viên<br>Nhóm SP</br></html>");
			label10.setActionCommand("3");
			label10.setFocusPainted(false);
			label10.setForeground(Color.black);
			label10.setName("NhanVienNhomSanPham");
			PanelEmployeeGroupProduct.permission = (UIConfigModelManager.getInstance().getPlainText(label1.getText()));
			label10.setIcon(new ImageIcon(getClass().getResource("icon/hanghoa1.png")));
			label10.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						PanelEmployeeGroupProduct employeeproduct = new PanelEmployeeGroupProduct();
						employeeproduct.setName("NhanVienNhomSanPham");
						DialogMain dialog = new DialogMain(employeeproduct, 450, 630);
						dialog.setIcon("hanghoa1.png");
						dialog.setName("dlNhanVienNhomSanPham");
						dialog.setTitle("Nhân viên nhóm SP");
						dialog.setVisible(true);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});

			label10.addMouseListener(new MouseEventClickButton("hanghoa1.png", "hanghoa2.png", "hanghoa3.png"));
			add(label10);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label10.getText()));

			// CV HÀNG NGÀY
			JButton label16 = CreateButton("<html>Công việc<br>hàng ngày</br></html>");
			label16.setActionCommand("3");
			label16.setFocusPainted(false);
			label16.setForeground(Color.black);
			label16.setName("CongViecHangNgay");
			label16.setIcon(new ImageIcon(getClass().getResource("icon/congviec1.png")));
			PanelToDoCalendar.permisson = (UIConfigModelManager.getInstance().getPlainText(label16.getText()));
			label16.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					try {
						JButton btn = (JButton) evt.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						PanelToDoCalendar panel = new PanelToDoCalendar(false);
						panel.setName("CongViecHangNgay");
						DialogMain dialog = new DialogMain(panel);
						dialog.setIcon("congviec1.png");
						dialog.setName("dlCongViecHangNgay");
						dialog.setTitle("Công việc hàng ngày");
						dialog.setModal(true);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			label16.addMouseListener(new MouseEventClickButton("congviec1.png", "congviec2.png", "congviec3.png"));
			add(label16);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label16.getText()));

			// THEO DÕI CV HÀNG NGÀY
			JButton label9 = CreateButton("<html>T.dõi C.việc<br>hàng ngày</br></html>");
			label9.setForeground(Color.black);
			label9.setFocusPainted(false);
			label9.setName("TheoDoiCongViecHangNgay");
			label9.setIcon(new ImageIcon(getClass().getResource("icon/congviecnu1.png")));
			label9.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						PanelSortToDoCalendar panel = new PanelSortToDoCalendar();
						panel.setToolTipText(UIConfigModelManager.getInstance().getPlainText(btn.getText()));
						panel.setName("TheoDoiCongViecHangNgay");
						DialogResto dialog = new DialogResto(panel, false, 0, 160);
						dialog.setName("dlTheoDoiCongViecHangNgay");
						dialog.setTitle("Theo dõi - CV hàng ngày");
						dialog.setIcon("congviecnu1.png");
						dialog.setLocationRelativeTo(null);
						dialog.setModal(true);
						dialog.setVisible(true);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			label9.addMouseListener(new MouseEventClickButton("congviecnu1.png", "congviecnu2.png", "congviecnu3.png"));
			add(label9);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label9.getText()));
		}
	}

	public static class MenuRightUnder extends JPanel {
		public MenuRightUnder() {
			setLayout(new GridLayout(3, 3));
			setBorder(BorderFactory.createTitledBorder(null, "Báo cáo thống kê", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 16), new java.awt.Color(204, 0, 0)));

			/**
			 * THỐNG KÊ THU CHI
			 */
			JButton label1 = CreateButton("<html>Thống kê<br>thu chi</br></html>");
			label1.setFocusPainted(false);
			label1.setIcon(new ImageIcon(getClass().getResource("icon/statistic1.png")));
			label1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JButton btn = (JButton) e.getSource();
					if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
						DialogJurisdiction.getInstace().setVisible(true);
						return;
					}

					DialogReport dialog = new DialogReport(new ReportStatistics(new JTable(), ReportStatistics.TypeReport.TKThuChi));
					dialog.dispose();
					dialog.setUndecorated(true);
					dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
					dialog.setModal(true);
					dialog.setIcon("statistic1.png");
					dialog.setLocationRelativeTo(null);
					dialog.setTitle("Thống kê thu chi");
					dialog.setVisible(true);
				}
			});
			label1.addMouseListener(new MouseEventClickButton("statistic1.png", "statistic2.png", "statistic3.png"));
			add(label1);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label1.getText()));

			/**
			 * THỐNG KÊ BÁN HÀNG
			 */
			JButton label2 = CreateButton("<html>Thống kê<br>bán hàng</br></html>");
			label2.setIcon(new ImageIcon(getClass().getResource("icon/tknhansu1.png")));
			label2.setFocusPainted(false);
			label2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Report.name())) {
							DialogReport dialog = new DialogReport(new ReportStatistics(new JTable(), ReportStatistics.TypeReport.TKBanHang));
							dialog.dispose();
							dialog.setUndecorated(true);
							dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
							dialog.setModal(true);
							dialog.setIcon("tknhansu1.png");
							dialog.setLocationRelativeTo(null);
							dialog.setTitle("Thống kê bán hàng");
							
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
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			});
			label2.addMouseListener(new MouseEventClickButton("tknhansu1.png", "tknhansu2.png", "tknhansu3.png"));
			add(label2);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label2.getText()));

			/**
			 * THỐNG KÊ MUA HÀNG
			 */

			JButton label3 = CreateButton("<html>Thống kê <Br>mua hàng</Br></html>");
			label3.setFocusPainted(false);
			label3.setIcon(new ImageIcon(getClass().getResource("icon/tksanpham1.png")));
			label3.addMouseListener(new MouseEventClickButton("tksanpham1.png", "tksanpham2.png", "tksanpham3.png"));
			add(label3);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label3.getText()));
			label3.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Report.name())) {
							DialogReport dialog = new DialogReport(new ReportStatistics(new JTable(), ReportStatistics.TypeReport.TKMuaHang));
							dialog.dispose();
							dialog.setUndecorated(true);
							dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
							dialog.setModal(true);
							dialog.setIcon("tksanpham1.png");
							dialog.setLocationRelativeTo(null);
							dialog.setTitle("Thống kê mua hàng");
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
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			});

			/**
			 * THỐNG KÊ THEO THỜI GIAN
			 */
			JButton label4 = CreateButton("<html>TK theo<br>thời gian</br></html>");
			label4.setIcon(new ImageIcon(getClass().getResource("icon/tktime1.png")));
			label4.setFocusPainted(false);
			label4.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Report.name())) {
							DialogReport dialog = new DialogReport(new ReportStatistics(new JTable(), ReportStatistics.TypeReport.TKThoiGian));
							dialog.dispose();
							dialog.setUndecorated(true);
							dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
							dialog.setModal(true);
							dialog.setIcon("tktime1.png");
							dialog.setLocationRelativeTo(null);
							dialog.setTitle("Thống kê theo thời gian");
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
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			});
			label4.addMouseListener(new MouseEventClickButton("tktime1.png", "tktime2.png", "tktime3.png"));
			add(label4);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label4.getText()));

			/**
			 * THỐNG KÊ TỒN KHO
			 */
			JButton label5 = CreateButton("<html>Thống kê<br>tồn kho</br></html>");
			try {
				List<Recipe> recipes = RestaurantModelManager.getInstance().getRecipes();
				if (recipes.size() > 0) {
					label5.setEnabled(false);
				}
      } catch (Exception e) {
      }
			label5.setFocusPainted(false);
			label5.setIcon(new ImageIcon(getClass().getResource("icon/tkban1.png")));
			label5.addMouseListener(new MouseEventClickButton("tkban1.png", "tkban2.png", "tkban3.png"));
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
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Report.name())) {
							DialogReport dialog = new DialogReport(new ReportStatistics(new JTable(), ReportStatistics.TypeReport.TKTonKho));
							dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
							dialog.setModal(true);
							dialog.setIcon("tkban1.png");
							dialog.setLocationRelativeTo(null);
							dialog.setTitle("Thống kê tồn kho");
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
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			});

			/**
			 * THỐNG KÊ ĐỊNH LƯỢNG
			 */
			JButton label6 = CreateButton("<html>Thống kê<br>định lượng</br></html>");
			label6.setFocusPainted(false);
			label6.setIcon(new ImageIcon(getClass().getResource("icon/tkbophan1.png")));
			label6.addMouseListener(new MouseEventClickButton("tkbophan1.png", "tkbophan2.png", "tkbophan3.png"));
			add(label6);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label6.getText()));
			label6.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Report.name())) {
							DialogReport dialog = new DialogReport(new ReportStatistics(new JTable(), ReportStatistics.TypeReport.TKDinhLuong));
							dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
							dialog.setModal(true);
							dialog.setIcon("tkbophan1.png");
							dialog.setLocationRelativeTo(null);
							dialog.setTitle("Thống kê định lượng");
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
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			});
			/**
			 * THỐNG KÊ THEO NHÂN VIÊN DỰ ÁN
			 */
			JButton label7 = CreateButton("<html>Thống kê<br>dự án</br></html>");
			label7.setFocusPainted(false);
			label7.setIcon(new ImageIcon(getClass().getResource("icon/tkbophan1.png")));
			label7.addMouseListener(new MouseEventClickButton("tkbophan1.png", "tkbophan2.png", "tkbophan3.png"));
			add(label7);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label7.getText()));
			label7.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Report.name())) {
							DialogReport dialog = new DialogReport(new ReportStatistics(new JTable(), ReportStatistics.TypeReport.TKNhanVien));
							dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
							dialog.setModal(true);
							dialog.setIcon("tkbophan1.png");
							dialog.setLocationRelativeTo(null);
							dialog.setTitle("Thống kê nhân viên dự án");
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
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			});
			/**
			 * CHƯA CÓ
			 */
			JButton label9 = CreateButton("<html></html>");
			label9.setForeground(Color.red);
			label9.setFocusPainted(false);
//			label9.setIcon(new ImageIcon(getClass().getResource("icon/tksanpham1.png")));
//			label9.addMouseListener(new MouseEventClickButton("tksanpham1.png", "tksanpham2.png", "tksanpham3.png"));
			add(label9);
		}
	}

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
		label.setForeground(Color.black);
		label.setText(text);
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
