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
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.basic.BasicBorders;

import com.hkt.client.swingexp.app.banhang.PanelFilterGroupProduct;
import com.hkt.client.swingexp.app.banhang.ReportStatistics;
import com.hkt.client.swingexp.app.core.DialogContain;
import com.hkt.client.swingexp.app.core.DialogJurisdiction;
import com.hkt.client.swingexp.app.core.DialogList;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogReport;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.MouseEventClickButton;
import com.hkt.client.swingexp.app.hethong.DialogInfo;
import com.hkt.client.swingexp.app.hethong.PanelOKRestore;
import com.hkt.client.swingexp.app.khohang.CatalogProductTag;
import com.hkt.client.swingexp.app.khohang.ChangeWarehouse;
import com.hkt.client.swingexp.app.khohang.PanelFilterPatternProduction;
import com.hkt.client.swingexp.app.khohang.PanelInstituteWarehouse;
import com.hkt.client.swingexp.app.khohang.PanelInventory;
import com.hkt.client.swingexp.app.khohang.PanelPatternProduction;
import com.hkt.client.swingexp.app.khohang.PanelRecipe;
import com.hkt.client.swingexp.app.khohang.PanelRepositoryProducts2;
import com.hkt.client.swingexp.app.khohang.PanelWarehouse;
import com.hkt.client.swingexp.app.khohang.PanelWarehouseExportImport;
import com.hkt.client.swingexp.app.khohang.list.PanelFilterWarehouseImportExport;
import com.hkt.client.swingexp.app.khohang.list.PanelSortbarcode;
import com.hkt.client.swingexp.app.khohang.list.TableListRecipe;
import com.hkt.client.swingexp.app.license.LicenseManager;
import com.hkt.client.swingexp.app.thuchi.PanelPhieuDatMuaHang;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.accounting.entity.Invoice.ActivityType;
import com.hkt.module.restaurant.entity.Recipe;

@SuppressWarnings("serial")
public class MenuRightKhoHangHoa extends JPanel {

	public MenuRightKhoHangHoa() {
		setLayout(new GridLayout(2, 1));
		setBackground(Color.WHITE);
		MenuRightTop menuRightTop = new MenuRightTop();
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()));
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
			setLayout(new GridLayout(3, 3));
			setBorder(BorderFactory.createTitledBorder(null, "Chức năng cơ bản",
			    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
			    new java.awt.Font("Tahoma", 1, 16), new java.awt.Color(204, 0, 0)));

			// PHIẾU NHẬP KHO
			JButton label1 = CreateButton("<html>Phiếu<br>nhập kho</br></html>");
			label1.setActionCommand("3");
			label1.setFocusPainted(false);
			label1.setForeground(Color.black);
			label1.setIcon(new ImageIcon(getClass().getResource("icon/xuatnhap1.png")));
			PanelWarehouseExportImport.permission = (UIConfigModelManager.getInstance().getPlainText(label1.getText()));
			label1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(
						    UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Warehouse.name())) {
							// PanelWarehouseExportImport importWarehouse = new
							// PanelWarehouseExportImport(null, ActivityType.Payment);
							// importWarehouse.setPreferredSize(new Dimension(100, 100));
							// DialogMain dialog = new DialogMain(importWarehouse, true);
							// dialog.setModal(true);
							// dialog.setResizable(false);
							// dialog.setIcon("xuatnhap1.png");
							// dialog.setTitle("Phiếu nhập kho");
							// dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
							// dialog.setLocationRelativeTo(null);
							// dialog.setVisible(true);
							PanelPhieuDatMuaHang panel = new PanelPhieuDatMuaHang(ActivityType.Payment,
							    AccountingModelManager.typeSanXuat, "PDH");
							DialogContain dialog1 = new DialogContain(panel);
							dialog1.getContentPane().add(panel);
							dialog1.setSize(Toolkit.getDefaultToolkit().getScreenSize());
							dialog1.dispose();
							dialog1.setUndecorated(true);
							dialog1.setLocationRelativeTo(null);
							dialog1.setModal(true);
							dialog1.setVisible(true);
						} else {
							PanelOKRestore panel = new PanelOKRestore("Bạn chưa đăng ký bản quyền với module chức năng này!",
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
			label1.addMouseListener(new MouseEventClickButton("xuatnhap1.png", "xuatnhap2.png", "xuatnhap3.png"));
			add(label1);
			UIConfigModelManager.getInstance().setPermission(
			    UIConfigModelManager.getInstance().getPlainText(label1.getText()));

			// PHIẾU XUẤT KHO
			JButton label2 = CreateButton("<html>Phiếu<br>xuất kho</br></html>");
			label2.setForeground(Color.black);
			PanelWarehouseExportImport.permission = (UIConfigModelManager.getInstance().getPlainText(label2.getText()));
			label2.setFocusPainted(false);
			label2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(
						    UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Warehouse.name())) {
							// PanelWarehouseExportImport exportWarehouse = new
							// PanelWarehouseExportImport(null, ActivityType.Receipt);
							// exportWarehouse.setPreferredSize(new Dimension(100, 100));
							// DialogMain dialog = new DialogMain(exportWarehouse, true);
							// dialog.setModal(true);
							// dialog.setResizable(false);
							// dialog.setIcon("xuatnhap1.png");
							// dialog.setTitle("Phiếu xuất kho");
							// dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
							// dialog.setLocationRelativeTo(null);
							// dialog.setVisible(true);
							PanelPhieuDatMuaHang panel = new PanelPhieuDatMuaHang(ActivityType.Receipt,
							    AccountingModelManager.typeSanXuat, "KDH");
							DialogContain dialog1 = new DialogContain(panel);
							dialog1.getContentPane().add(panel);
							dialog1.setSize(Toolkit.getDefaultToolkit().getScreenSize());
							dialog1.dispose();
							dialog1.setUndecorated(true);
							dialog1.setLocationRelativeTo(null);
							dialog1.setModal(true);
							dialog1.setVisible(true);
						} else {
							PanelOKRestore panel = new PanelOKRestore("Bạn chưa đăng ký bản quyền với module chức năng này!",
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
			label2.setIcon(new ImageIcon(getClass().getResource("icon/xuatnhap1.png")));
			label2.addMouseListener(new MouseEventClickButton("xuatnhap1.png", "xuatnhap2.png", "xuatnhap3.png"));
			add(label2);
			UIConfigModelManager.getInstance().setPermission(
			    UIConfigModelManager.getInstance().getPlainText(label2.getText()));

			// QL XUẤT NHẬP KHO
			JButton label3 = CreateButton("<html>QL xuất<br>nhập</br></html>");
			label3.setForeground(Color.black);
			label3.setFocusPainted(false);
			label3.setIcon(new ImageIcon(getClass().getResource("icon/xnds1.png")));
			label3.addMouseListener(new MouseEventClickButton("xnds1.png", "xnds2.png", "xnds3.png"));
			label3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					try {
						JButton btn = (JButton) evt.getSource();
						if (UIConfigModelManager.getInstance().getPermission(
						    UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Warehouse.name())) {
							PanelFilterWarehouseImportExport panel = new PanelFilterWarehouseImportExport();
							DialogResto dialog = new DialogResto(panel, false, 0, 160);
							dialog.setIcon("xnds1.png");
							dialog.setLocationRelativeTo(null);
							dialog.setTitle("Quản lý xuất nhập");
							dialog.setModal(true);
							dialog.setVisible(true);
						} else {
							PanelOKRestore panel = new PanelOKRestore("Bạn chưa đăng ký bản quyền với module chức năng này!",
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
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			add(label3);
			UIConfigModelManager.getInstance().setPermission(
			    UIConfigModelManager.getInstance().getPlainText(label3.getText()));

			// CHUYỂN KHO
			JButton label4 = CreateButton("<html>Chuyển<br>Kho</br></html>");
			label4.setEnabled(false);
			label4.setForeground(Color.black);
			label4.setFocusPainted(false);
			label4.setIcon(new ImageIcon(getClass().getResource("icon/chuyen1.png")));
			ChangeWarehouse.permission = (UIConfigModelManager.getInstance().getPlainText(label4.getText()));
			label4.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(
						    UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Warehouse.name())) {
							ChangeWarehouse dialogMain = new ChangeWarehouse();
							dialogMain.setSize(100, 100);
							dialogMain.setPreferredSize(new Dimension(100, 100));
							DialogMain dialog = new DialogMain(dialogMain, true);
							dialog.setModal(true);
							dialog.setResizable(false);
							dialog.setIcon("chuyen1.png");
							dialog.setTitle("Chuyển kho");
							dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
							dialog.setLocationRelativeTo(null);
							dialog.setVisible(true);
						} else {
							PanelOKRestore panel = new PanelOKRestore("Bạn chưa đăng ký bản quyền với module chức năng này!",
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
			label4.addMouseListener(new MouseEventClickButton("chuyen1.png", "chuyen2.png", "chuyen3.png"));
			add(label4);
			UIConfigModelManager.getInstance().setPermission(
			    UIConfigModelManager.getInstance().getPlainText(label4.getText()));

			// KHAI BÁO ĐỊNH LƯỢNG
			JButton label5 = CreateButton("<html>Khai báo<br>định lượng</br></html>");
			label5.setName("declarationQuantitative");
			label5.setActionCommand("3");
			label5.setFocusPainted(false);
			label5.setForeground(Color.black);
			label5.setIcon(new ImageIcon(getClass().getResource("icon/ctquanti1.png")));
			PanelRecipe.permission = (UIConfigModelManager.getInstance().getPlainText(label5.getText()));
			label5.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(
						    UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						PanelRecipe declarationQuantitative = new PanelRecipe(false);
						// declarationQuantitative.setSize(100, 100);
						// declarationQuantitative.setPreferredSize(new Dimension(100,
						// 100));
						declarationQuantitative.setName("declarationQuantitative");
						DialogMain dialog = new DialogMain(declarationQuantitative, true);
						dialog.setName("dlDeclarationQuantitative");
						dialog.setTitle("Khai báo định lượng");
						dialog.setIcon("ctquanti1.png");
						dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
						dialog.dispose();
						dialog.setUndecorated(true);
						dialog.setModal(true);
						dialog.setLocationRelativeTo(null);
						// dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
						dialog.setVisible(true);

					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			});
			label5.addMouseListener(new MouseEventClickButton("ctquanti1.png", "ctquanti2.png", "ctquanti3.png"));
			add(label5);
			UIConfigModelManager.getInstance().setPermission(
			    UIConfigModelManager.getInstance().getPlainText(label5.getText()));

			// QUẢN LÝ ĐỊNH LƯỢNG
			JButton label6 = CreateButton("<html>Quản lý<br>định lượng</br></html>");
			label6.setName("dlListRecipe");
			label6.setForeground(Color.black);
			label6.setFocusPainted(false);
			label6.setIcon(new ImageIcon(getClass().getResource("icon/tkquanti1.png")));
			label6.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(
						    UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						final TableListRecipe tbrepice = new TableListRecipe();
						tbrepice.setName("tbrecipe");
						DialogList dialog = new DialogList(tbrepice);
						dialog.setName("dlListRecipe");
						dialog.setIcon("tkquanti1.png");
						dialog.setTitle("Quản lý định lượng");
						dialog.setVisible(true);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			label6.addMouseListener(new MouseEventClickButton("tkquanti1.png", "tkquanti2.png", "tkquanti3.png"));
			add(label6);
			UIConfigModelManager.getInstance().setPermission(
			    UIConfigModelManager.getInstance().getPlainText(label6.getText()));

			// THÊM HÀNG HÓA MỚI
			JButton label7 = CreateButton("<html>Thêm hàng<br>hóa mới</br></html>");
			label7.setActionCommand("2");
			label7.setFocusPainted(false);
			label7.setForeground(Color.black);
			label7.setName("ThemHangHoaMoi");
			label7.setIcon(new ImageIcon(getClass().getResource("icon/hanghoa1.png")));
			PanelRepositoryProducts2.permission = (UIConfigModelManager.getInstance().getPlainText(label7.getText()));
			label7.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(
						    UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						PanelRepositoryProducts2 panel = new PanelRepositoryProducts2();
						panel.setPreferredSize(new Dimension(100, 100));
						panel.setName("ThemHangHoaMoi");
						DialogMain dialog = new DialogMain(panel, true);
						dialog.setResizable(false);
						dialog.setIcon("hanghoa1.png");
						dialog.setTitle("Thêm hàng hóa mới");
						dialog.setName("dlThemHangHoaMoi");
						dialog.setModal(true);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);

					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			label7.addMouseListener(new MouseEventClickButton("hanghoa1.png", "hanghoa2.png", "hanghoa3.png"));
			add(label7);
			UIConfigModelManager.getInstance().setPermission(
			    UIConfigModelManager.getInstance().getPlainText(label7.getText()));

			// QL NHÓM HÀNG
			JButton label8 = CreateButton("<html>Quản lý <br>nhóm <br>hàng</br></html>");
			label8.setActionCommand("1");
			label8.setFocusPainted(false);
			label8.setIcon(new ImageIcon(getClass().getResource("icon/nhomsp1.png")));
			CatalogProductTag.permission = (UIConfigModelManager.getInstance().getPlainText(label7.getText()));
			label8.setName("QuanLyNhomHang_KhoHangHoa");
			label8.setForeground(Color.black);
			label8.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(
						    UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						CatalogProductTag hh = new CatalogProductTag();
						hh.setName("QuanLyNhomHang_KhoHangHoa");
						DialogMain dialog = new DialogMain(hh);
						dialog.setIcon("nhomsp1.png");
						dialog.setName("dlNhomhang");
						dialog.setTitle("Quản lý nhóm hàng");
						dialog.setVisible(true);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			label8.addMouseListener(new MouseEventClickButton("nhomsp1.png", "nhomsp2.png", "nhomsp3.png"));
			add(label8);
			UIConfigModelManager.getInstance().setPermission(
			    UIConfigModelManager.getInstance().getPlainText(label8.getText()));

			// QUẢN LÝ HÀNG HÓA
			JButton label9 = CreateButton("<html>Quản lý<br>hàng hóa</br></html>");
			label9.setForeground(Color.black);
			label9.setFocusPainted(false);
			label9.setName("btnQuanLyHangHoa");
			label9.setIcon(new ImageIcon(getClass().getResource("icon/dssp1.png")));
			label9.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(
						    UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						PanelFilterGroupProduct panel = new PanelFilterGroupProduct("QuanLyHangHoa");
						panel.setName("pnLocSanPham");
						int h = 0;
						if (panel.getH() > h) {
							h = panel.getH();
						}
						DialogResto dialog = new DialogResto(panel, false, 0, h);
						dialog.setIcon("dssp1.png");
						dialog.setName("dlLocSanPham");
						dialog.setTitle("Quản lý hàng hóa");
						dialog.setLocationRelativeTo(null);
						dialog.setModal(true);
						dialog.setVisible(true);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			label9.addMouseListener(new MouseEventClickButton("dssp1.png", "dssp2.png", "dssp3.png"));
			add(label9);
			UIConfigModelManager.getInstance().setPermission(
			    UIConfigModelManager.getInstance().getPlainText(label9.getText()));

			label3.setForeground(Color.black);
			label4.setForeground(Color.black);
			label5.setForeground(Color.black);
			label6.setForeground(Color.black);
		}
	}

	public static class MenuRightUnder extends JPanel {

		public MenuRightUnder() {
			setLayout(new GridLayout(3, 3));
			setBorder(BorderFactory.createTitledBorder(null, "Báo cáo thống kê",
			    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
			    new java.awt.Font("Tahoma", 1, 16), new java.awt.Color(204, 0, 0)));

			/**
			 * PHIẾU SẢN XUẤT
			 */
			JButton label7 = CreateButton("<html>Phiếu <br> sản xuất</br></html>");
			label7.setFocusPainted(false);
			label7.setIcon(new ImageIcon(getClass().getResource("icon/thuchi1.png")));
			label7.addMouseListener(new MouseEventClickButton("thuchi1.png", "thuchi1.png", "thuchi1.png"));
			add(label7);
			label7.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(
						    UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Report.name())) {
							PanelPatternProduction panel = new PanelPatternProduction();
							panel.setName("PhieuSanXuat");
							DialogResto dialog = new DialogResto(panel, true, 600, 150);
							dialog.setIcon("thuchi1.png");
							dialog.dispose();
							dialog.setUndecorated(true);
							dialog.setModal(true);
							dialog.setTitle("Phiếu sản xuất");
							dialog.setName("dlPhieuSanXuat");
							dialog.setLocationRelativeTo(null);
							dialog.setVisible(true);
						} else {
							PanelOKRestore panel = new PanelOKRestore("Bạn chưa đăng ký bản quyền với module chức năng này!",
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
			 * DANH SÁCH SẢN XUẤT
			 */
			JButton label8 = CreateButton("<html>Danh sách<br> sản xuất</br></html>");
			label8.setFocusPainted(false);
			label8.setIcon(new ImageIcon(getClass().getResource("icon/datban1.png")));
			label8.addMouseListener(new MouseEventClickButton("datban1.png", "datban2.png", "datban3.png"));
			add(label8);
			label8.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(
						    UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Report.name())) {
							PanelFilterPatternProduction panel = new PanelFilterPatternProduction();
							DialogResto dialog = new DialogResto(panel, false, 0, 100);
							dialog.setIcon("datban1.png");
							dialog.setLocationRelativeTo(null);
							dialog.setTitle("Danh sách sản xuất");
							dialog.setModal(true);
							dialog.setVisible(true);
						} else {
							PanelOKRestore panel = new PanelOKRestore("Bạn chưa đăng ký bản quyền với module chức năng này!",
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
			 * QUẢN LÝ MÃ BARCODE
			 */
			JButton label30 = CreateButton("<html>QL mã<br>Barcode</br></html>");
			label30.setIcon(new ImageIcon(getClass().getResource("icon/barcode1.png")));
			label30.setFocusPainted(false);
			label30.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(
						    UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						PanelSortbarcode panel = new PanelSortbarcode();
						DialogResto dialog = new DialogResto(panel, false, 0, 100);
						// dialog.setSize(new Dimension(650, 270));
						dialog.setIcon("barcode1.png");
						dialog.setTitle("Quản lý mã barcode");
						dialog.setLocationRelativeTo(null);
						dialog.setModal(true);
						dialog.setVisible(true);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});

			label30.addMouseListener(new MouseEventClickButton("barcode1.png", "barcode2.png", "barcode3.png"));
			add(label30);
			UIConfigModelManager.getInstance().setPermission(
			    UIConfigModelManager.getInstance().getPlainText(label30.getText()));

			

			// JButton label9 =
			// CreateButton("<html>Tính lại<br> thống kê</br></html>");
			// label9.setFocusPainted(false);
			// label9.setIcon(new
			// ImageIcon(getClass().getResource("icon/datban1.png")));
			// label9.addMouseListener(new MouseEventClickButton("datban1.png",
			// "datban2.png", "datban3.png"));
			// add(label9);
			// label9.addActionListener(new ActionListener() {
			// @Override
			// public void actionPerformed(ActionEvent e) {
			// FilterQuery filterQuery = new FilterQuery();
			// List<Invoice> invoices;
			// try {
			// Calendar c = Calendar.getInstance();
			// c.setTime(new Date());
			// c.set(Calendar.HOUR_OF_DAY, 0);
			// c.set(Calendar.MINUTE, 01);
			// Calendar c1 = Calendar.getInstance();
			// c1.setTime(new Date());
			// c1.set(Calendar.HOUR_OF_DAY, 23);
			// c1.set(Calendar.MINUTE, 59);
			// String dateStart = new
			// SimpleDateFormat("yyyy-MM-dd HH:ss:mm").format(c.getTime());
			// String dateEnd = new
			// SimpleDateFormat("yyyy-MM-dd HH:ss:mm").format(c1.getTime());
			// filterQuery.add("startDate", FilterQuery.Operator.GT, "'" + dateStart +
			// "'");
			// filterQuery.add("startDate", FilterQuery.Operator.LT, "'" + dateEnd +
			// "'");
			// filterQuery.add("status", FilterQuery.Operator.STRINGEQ, Status.Paid);
			// invoices =
			// AccountingModelManager.getInstance().searchInvoice(filterQuery);
			// } catch (Exception e1) {
			// invoices = new ArrayList<Invoice>();
			// }
			// for(Invoice invoice: invoices){
			// InvoiceDetail invoiceDetail =
			// AccountingModelManager.getInstance().getInvoiceDetail(invoice.getId());
			// for(InvoiceItem sel: invoiceDetail.getItems()){
			// try {
			// System.out.println(sel.getProductCode()+"     "+sel.getStartDate());
			// AccountingModelManager.getInstance().caculateReport(sel.getProductCode(),
			// sel.getStartDate());
			// } catch (Exception e2) {
			// e2.printStackTrace();
			// }
			//
			// }
			// }
			// }
			// });

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
						if (UIConfigModelManager.getInstance().getPermission(
						    UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Report.name())) {
							DialogReport dialog = new DialogReport(new ReportStatistics(new JTable(),
							    ReportStatistics.TypeReport.TKThoiGian));
							dialog.dispose();
							dialog.setUndecorated(true);
							dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
							dialog.setModal(true);
							dialog.setIcon("tktime1.png");
							dialog.setLocationRelativeTo(null);
							dialog.setTitle("Thống kê theo thời gian");
							dialog.setVisible(true);
						} else {
							PanelOKRestore panel = new PanelOKRestore("Bạn chưa đăng ký bản quyền với module chức năng này!",
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
			UIConfigModelManager.getInstance().setPermission(
			    UIConfigModelManager.getInstance().getPlainText(label4.getText()));

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
			UIConfigModelManager.getInstance().setPermission(
			    UIConfigModelManager.getInstance().getPlainText(label5.getText()));
			label5.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(
						    UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Report.name())) {
							DialogReport dialog = new DialogReport(new ReportStatistics(new JTable(),
							    ReportStatistics.TypeReport.TKTonKho));
							dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
							dialog.setModal(true);
							dialog.setIcon("tkban1.png");
							dialog.setLocationRelativeTo(null);
							dialog.setTitle("Thống kê tồn kho");
							dialog.setVisible(true);
						} else {
							PanelOKRestore panel = new PanelOKRestore("Bạn chưa đăng ký bản quyền với module chức năng này!",
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
			UIConfigModelManager.getInstance().setPermission(
			    UIConfigModelManager.getInstance().getPlainText(label6.getText()));
			label6.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(
						    UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Report.name())) {
							DialogReport dialog = new DialogReport(new ReportStatistics(new JTable(),
							    ReportStatistics.TypeReport.TKDinhLuong));
							dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
							dialog.setModal(true);
							dialog.setIcon("tkbophan1.png");
							dialog.setLocationRelativeTo(null);
							dialog.setTitle("Thống kê định lượng");
							dialog.setVisible(true);
						} else {
							PanelOKRestore panel = new PanelOKRestore("Bạn chưa đăng ký bản quyền với module chức năng này!",
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
			 * TÍNH LẠI THỐNG KÊ
			 */
			JButton label9 = CreateButton("<html>Kiểm kê<br>Kho hàng</br></html>");
			label9.setFocusPainted(false);
			label9.setIcon(new ImageIcon(getClass().getResource("icon/datban1.png")));
			label9.addMouseListener(new MouseEventClickButton("datban1.png", "datban2.png", "datban3.png"));
			add(label9);
			label9.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						PanelInventory recalculatorReport = new PanelInventory();
						DialogResto dialog = new DialogResto(recalculatorReport, true, 0, 140);
						dialog.setIcon("datban1.png");
						dialog.setLocationRelativeTo(null);
						dialog.setTitle("Kiểm kê kho hàng");
						dialog.setModal(true);
						dialog.setVisible(true);

					} catch (Exception e2) {
						e2.printStackTrace();
					}

				}
			});

			/**
			 * THIẾT LẬP KHO
			 */
			JButton label10 = CreateButton("<html>Thiết<br>lập kho</br></html>");
			label10.setEnabled(false);
			label10.setFocusPainted(false);
			label10.setIcon(new ImageIcon(getClass().getResource("icon/thuchi1.png")));
			PanelInstituteWarehouse.permission = (UIConfigModelManager.getInstance().getPlainText(label5.getText()));
			label10.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(
						    UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Warehouse.name())) {
							PanelInstituteWarehouse panel = new PanelInstituteWarehouse();
							DialogResto dialog = new DialogResto(panel, false, 950, 380);
							dialog.setIcon("thuchi1.png");
							panel.setName("panelInstituteWarehouse");
							dialog.setTitle("Thiết lập kho");
							// dialog.setSize(900, 600);
							dialog.setModal(true);
							dialog.setLocationRelativeTo(null);
							dialog.setVisible(true);

						} else {
							PanelOKRestore panel = new PanelOKRestore("Bạn chưa đăng ký bản quyền với module chức năng này!",
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
			label10.addMouseListener(new MouseEventClickButton("thuchi1.png", "thuchi2.png", "thuchi3.png"));
			add(label10);
			UIConfigModelManager.getInstance().setPermission(
			    UIConfigModelManager.getInstance().getPlainText(label10.getText()));

			/**
			 * THÊM MỚI KHO
			 */
			JButton label20 = CreateButton("<html>Tạo kho<br>hàng</br></html>");
			label20.setActionCommand("1");
			label20.setName("ThemMoiKho");
			label20.setFocusPainted(false);
			label20.setIcon(new ImageIcon(getClass().getResource("icon/soche1.png")));
			PanelWarehouse.permission = (UIConfigModelManager.getInstance().getPlainText(label20.getText()));
			label20.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(
						    UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Warehouse.name())) {
							PanelWarehouse add = new PanelWarehouse();
							add.setName("ThemMoiKho");
							DialogMain dialog = new DialogMain(add);
							dialog.setIcon("soche1.png");
							dialog.setName("dlThemMoiKho");
							dialog.setTitle("Tạo kho hàng");
							dialog.setResizable(false);
							dialog.setModal(true);
							dialog.setLocationRelativeTo(null);
							dialog.setVisible(true);
						} else {
							PanelOKRestore panel = new PanelOKRestore("Bạn chưa đăng ký bản quyền với module chức năng này!",
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
			label20.addMouseListener(new MouseEventClickButton("soche1.png", "soche2.png", "soche2.png"));
			add(label20);
			UIConfigModelManager.getInstance().setPermission(
			    UIConfigModelManager.getInstance().getPlainText(label20.getText()));

			

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
		label.setIconTextGap(8);
		Border border = new BorderUIResource.CompoundBorderUIResource(new BasicBorders.ButtonBorder(Color.white,
		    Color.white, Color.white, Color.white), new BasicBorders.MarginBorder());
		label.setBorder(border);
		return label;
	}

}
