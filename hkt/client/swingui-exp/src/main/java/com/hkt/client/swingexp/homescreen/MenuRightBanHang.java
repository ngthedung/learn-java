package com.hkt.client.swingexp.homescreen;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.basic.BasicBorders;

import com.hkt.client.swingexp.app.banhang.PanelFilterGroupProduct;
import com.hkt.client.swingexp.app.banhang.PanelProductPriceType;
import com.hkt.client.swingexp.app.banhang.PanelSearchInvoice;
import com.hkt.client.swingexp.app.banhang.PanelTax;
import com.hkt.client.swingexp.app.banhang.PanelUsingManager;
import com.hkt.client.swingexp.app.banhang.RecalculatorReport;
import com.hkt.client.swingexp.app.banhang.ReportStatistics;
import com.hkt.client.swingexp.app.banhang.list.PaneGroupProduct;
import com.hkt.client.swingexp.app.banhang.screen.market.DialogSuperMarket;
import com.hkt.client.swingexp.app.banhang.screen.often.DialogConfig;
import com.hkt.client.swingexp.app.banhang.screen.often.ScreenOften;
import com.hkt.client.swingexp.app.banhang.screen.pos.DialogScreenOftenPos;
import com.hkt.client.swingexp.app.banhang.screen.pos.DialogSuperMarketPos;
import com.hkt.client.swingexp.app.banhang.screen.pos.PanelScreenSaleLocationPos;
import com.hkt.client.swingexp.app.core.DialogContain;
import com.hkt.client.swingexp.app.core.DialogJurisdiction;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogReport;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.MouseEventClickButton;
import com.hkt.client.swingexp.app.hethong.DialogInfo;
import com.hkt.client.swingexp.app.hethong.PaneSetDate;
import com.hkt.client.swingexp.app.hethong.PanelOKRestore;
import com.hkt.client.swingexp.app.khohang.PanelFilterPatternProduction;
import com.hkt.client.swingexp.app.license.LicenseManager;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.restaurant.entity.Recipe;

public class MenuRightBanHang extends JPanel {
	public MenuRightBanHang() {
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
		panelBot.setBackground(Color.WHITE);
		panelBot.setLayout(new BorderLayout());
		panelBot.add(menuRightUnder, BorderLayout.CENTER);
		add(panelBot);
	}
	
	public static class MenuRightTop extends JPanel {
		public MenuRightTop() {
			setBackground(Color.WHITE);
			setLayout(new GridLayout(3, 3));
			setName("MenuRightTop");
			setBorder(BorderFactory.createTitledBorder(null, "Chức năng bán hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 16), new java.awt.Color(204, 0, 0)));
			final Profile profile = AccountModelManager.getInstance().getProfileConfigAdmin();
			
			if (profile.get(DialogConfig.GDPos) != null && profile.get(DialogConfig.GDPos).toString().equals("true")) {
				Thread t = new Thread() {
					public void run() {
						DialogScreenOftenPos.getInstance();
					};
				};
				t.start();
			}
			
			// MÀN HÌNH BÁN HÀNG
			JButton label1 = CreateButton("<html>Màn hình<br>bán hàng</br></html>");
			label1.setIcon(new ImageIcon(getClass().getResource("icon/sale1.png")));
			label1.setFocusPainted(false);
			ScreenOften.permission = (UIConfigModelManager.getInstance().getPlainText(label1.getText()));
			label1.setName("ScreenOften");						
			label1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Profile profile = AccountModelManager.getInstance().getProfileConfigAdmin();
					try {
						if (profile.get(DialogConfig.Maket).toString().equals("true") || profile.get(DialogConfig.Shop).toString().equals("true")) {
							if (profile.get(DialogConfig.Maket).toString().equals("true")){
								if (profile.get(DialogConfig.GDPos).toString().equals("true")) {
									DialogSuperMarketPos marketPos = DialogSuperMarketPos.getInstance();
									marketPos.loadData();
									marketPos.setEnableKhuVuc(true);
									marketPos.setVisible(true);
								} else {
									DialogSuperMarket dialogSuperMarket = DialogSuperMarket.getInstance();
									dialogSuperMarket.loadData();
									dialogSuperMarket.setVisible(true);
								}
							} else {
								DialogScreenOftenPos dialog = DialogScreenOftenPos.getInstance();
								dialog.setTable(PanelScreenSaleLocationPos.getInstance().getComponentTableOther());
								dialog.setReset(true);
								if(!DialogScreenOftenPos.isFlagScreenOften()){
  								DialogScreenOftenPos.setFlagScreenOften(true);
									dialog.getPanelTop2().add(dialog.getPanelProductTagRoot(), 1);
									dialog.getScrollPane_Product().setViewportView(dialog.getPanelProducts());
								}
								if (dialog.hashMapProducts != null) {
									dialog.resetGui();
									dialog.loadData();
								}
								dialog.setVisible(true);
							}
						} else {
							if (profile.get(DialogConfig.GDPos).toString().equals("true")) {
								PanelScreenSaleLocationPos panel = PanelScreenSaleLocationPos.getInstance();
								panel.resetTable();
								DialogContain dialog1 = new DialogContain(panel);
								dialog1.getContentPane().add(panel);
								dialog1.setName("dlPOS");
								dialog1.setSize(Toolkit.getDefaultToolkit().getScreenSize());
								dialog1.dispose();
								dialog1.setUndecorated(true);
								dialog1.setLocationRelativeTo(null);
								dialog1.setModal(true);
								dialog1.setVisible(true);
							} else {
								ScreenOften dialog = ScreenOften.getInstance();
								dialog.setName("dlScreenOften");
								dialog.loadData();
								dialog.setVisible(true);
							}
						}
					} catch (Exception e2) {
						e2.printStackTrace();
						ScreenOften dialog = ScreenOften.getInstance();
						dialog.setName("dlScreenOften");
						dialog.loadData();
						dialog.setVisible(true);
					}
				}
			});
			label1.addMouseListener(new MouseEventClickButton("sale1.png", "sale2.png", "sale3.png"));
			add(label1);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label1.getText()));
			
			// DS CÔNG NỢ
			JButton label2;
			if (profile.get("ForRent") == null) {
				label2 = CreateButton("<html>Danh sách<br>công nợ</br></html>");
			} else {
				label2 = CreateButton("<html>Danh sách<br>cho thuê</br></html>");
			}
			label2.setName("btnSearchInvoice");
			label2.setFocusPainted(false);
			label2.setIcon(new ImageIcon(getClass().getResource("icon/invoiceds1.png")));
			label2.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent evt) {
					if (LicenseManager.getInstance().getLicense(LicenseManager.module.Restaurant.name())) {
						try {
							JButton btn = (JButton) evt.getSource();
							if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
								DialogJurisdiction.getInstace().setVisible(true);
								return;
							}
							PanelSearchInvoice panel = new PanelSearchInvoice();
							panel.loadDate(PaneSetDate.DateInvoiceCN);
							panel.setPermissionEmployee(UIConfigModelManager.getInstance().getPlainText(btn.getText()));
							panel.setName("PanelSearchInvoice");
							DialogResto dialog = new DialogResto(panel, false, 0, 200);
							dialog.setIcon("invoiceds1.png");
							dialog.setName("dlPanelSearchInvoice");
							dialog.setLocationRelativeTo(null);
							if (profile.get("ForRent") == null) {
								dialog.setTitle("Danh sách công nợ");
							} else {
								dialog.setTitle("Danh sách cho thuê");
							}
							
							dialog.setVisible(true);
							if (panel.isSave()) {
								panel.createTableDebt();
							}
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						PanelOKRestore panel = new PanelOKRestore("Bạn chưa đăng ký bản quyền với module chức năng này!", "Hãy liên hệ với công ty HKTConsultant!");
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
					
				}
			});
			label2.addMouseListener(new MouseEventClickButton("invoiceds1.png", "invoiceds2.png", "invoiceds3.png"));
			add(label2);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label2.getText()));
			
			// QUẢN LÝ HÓA ĐƠN
			JButton label3 = CreateButton("<html>Quản lý<br>hóa đơn</br></html>");
			label3.setName("label3");
			label3.setName("PanelSearchInvoice");
			label3.setFocusPainted(false);
			label3.setIcon(new ImageIcon(getClass().getResource("icon/invoiceds1.png")));
			label3.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent evt) {
					System.out.println(LicenseManager.getInstance().getLicense(LicenseManager.module.Restaurant.name()));
					if (LicenseManager.getInstance().getLicense(LicenseManager.module.Restaurant.name())) {
						try {
							JButton btn = (JButton) evt.getSource();
							if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
								DialogJurisdiction.getInstace().setVisible(true);
								return;
							}
							PanelSearchInvoice panel = new PanelSearchInvoice();
							panel.setName("PanelSearchInvoice");
							panel.loadDate(PaneSetDate.DateInvoiceBH);
							panel.setPermissionEmployee(UIConfigModelManager.getInstance().getPlainText(btn.getText()));
							DialogResto dialog = new DialogResto(panel, false, 0, 200);
							dialog.setIcon("invoiceds1.png");
							dialog.setName("dlPanelSearchInvoice");
							dialog.setTitle("Quản lý hóa đơn");
							dialog.setVisible(true);
							if (panel.isSave()) {
								panel.createTablePayment();
							}
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						PanelOKRestore panel = new PanelOKRestore("Bạn chưa đăng ký bản quyền với module chức năng này!", "Hãy liên hệ với công ty HKTConsultant!");
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
				}
			});
			label3.addMouseListener(new MouseEventClickButton("invoiceds1.png", "invoiceds2.png", "invoiceds3.png"));
			add(label3);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label3.getText()));
			
			// QUẢN LÝ SỬ DỤNG
			JButton label4 = CreateButton("<html>Quản lý <br>sử dụng</html>");
			label4.setName("label4");
			label4.setName("PanelUsingManager");
			label4.setFocusPainted(false);
			label4.setIcon(new ImageIcon(getClass().getResource("icon/dungsau1.png")));
			label4.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						PanelUsingManager panelUsing = new PanelUsingManager();
						panelUsing.setName("QuanLySuDung");
						DialogResto dialog = new DialogResto(panelUsing, true, 600, 150);
						dialog.setIcon("dungsau1.png");
						dialog.dispose();
						dialog.setUndecorated(true);
						dialog.setModal(true);
						dialog.setTitle("Quản lý sử dụng");
						dialog.setName("dlQuanLySuDung");
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			label4.addMouseListener(new MouseEventClickButton("dungsau1.png", "dungsau2.png", "dungsau3.png"));
			add(label4);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label4.getText()));
			
			// THUẾ HÀNG HÓA
			JButton label5 = CreateButton("<html>Thuế<br>hàng hóa</br></html>");
			label5.setActionCommand("1");
			label5.setFocusPainted(false);
			label5.setName("ThueHangHoa");
			PanelTax.permission = (UIConfigModelManager.getInstance().getPlainText(label5.getText()));
			label5.setIcon(new ImageIcon(getClass().getResource("icon/tax1.png")));
			label5.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						PanelTax panelTax = new PanelTax();
						panelTax.setName("ThueHangHoa");
						DialogMain dialog = new DialogMain(panelTax);
						dialog.setIcon("tax1.png");
						dialog.setTitle("Thuế hàng hóa");
						dialog.setName("dlThueHangHoa");
						dialog.setVisible(true);
						
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			label5.addMouseListener(new MouseEventClickButton("tax1.png", "tax2.png", "tax3.png"));
			add(label5);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label5.getText()));
			
			// BẢNG GIÁ HIỆN HÀNH
			JButton label6 = CreateButton("<html>Bảng giá<br>hiện hành</br></html>");
			label6.setName("btnBangGiaHienHanh");
			label6.setFocusPainted(false);
			label6.setIcon(new ImageIcon(getClass().getResource("icon/banggia1.png")));
			label6.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					try {
						JButton btn = (JButton) evt.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						PanelFilterGroupProduct panel = new PanelFilterGroupProduct("BangGiaHienHanh");
						panel.setName("pnLocSanPham");
						int h = 0;
						if (panel.getH() > h) {
							h = panel.getH();
						}
						DialogResto dialog = new DialogResto(panel, false, 0, h);
						dialog.setIcon("banggia1.png");
						dialog.setName("dlLocSanPham");
						// dialog.setSize(600, 400);
						dialog.setLocationRelativeTo(null);
						dialog.setTitle("Bảng giá hiện hành");
						dialog.setVisible(true);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			label6.addMouseListener(new MouseEventClickButton("banggia1.png", "banggia2.png", "banggia3.png"));
			add(label6);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label6.getText()));
			
			// QUẢN LÝ BẢNG GIÁ
			JButton label7 = CreateButton("<html>Quản lý<br>bảng giá</br></html>");
			label7.setActionCommand("1");
			label7.setFocusPainted(false);
			label7.setName("QuanLyBangGia");
			PanelProductPriceType.permission = (UIConfigModelManager.getInstance().getPlainText(label7.getText()));
			label7.setIcon(new ImageIcon(getClass().getResource("icon/price1.png")));
			label7.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					try {
						JButton btn = (JButton) evt.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						PanelProductPriceType pnProductPriceType = new PanelProductPriceType();
						pnProductPriceType.setName("QuanLyBangGia");
						DialogMain dialog = new DialogMain(pnProductPriceType);
						dialog.setIcon("price1.png");
						dialog.setName("dlQuanLyBangGia");
						dialog.setTitle("Quản lý bảng giá");
						dialog.setVisible(true);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			label7.addMouseListener(new MouseEventClickButton("price1.png", "price2.png", "price3.png"));
			add(label7);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label7.getText()));
			
			// QUẢN LÝ SẢN PHẨM
			JButton label8 = CreateButton("<html>Quản lý<br>sản phẩm</br></html>");
			label8.setIcon(new ImageIcon(getClass().getResource("icon/dssp1.png")));
			PaneGroupProduct.permission = (UIConfigModelManager.getInstance().getPlainText(label8.getText()));
			label8.setFocusPainted(false);
			label8.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					try {
						JButton btn = (JButton) evt.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						PaneGroupProduct panel = new PaneGroupProduct();
						DialogResto dialog = new DialogResto(panel, false, 0, 290);
						dialog.setIcon("dssp1.png");
						dialog.setName("dlQuanLySanPham");
						dialog.setTitle("Quản lý sản phẩm");
						dialog.setLocationRelativeTo(null);
						dialog.setModal(true);
						dialog.setVisible(true);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				
			});
			label8.addMouseListener(new MouseEventClickButton("dssp1.png", "dssp2.png", "dssp3.png"));
			add(label8);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label8.getText()));
			
			// THIẾT LẬP BÁN HÀNG
			JButton label9 = CreateButton("<html>Thiết lập<br>bán hàng</br></html>");
			label9.setIcon(new ImageIcon(getClass().getResource("icon/thietlap1.png")));
			label9.setName("ThietLapBH");
			label9.setFocusPainted(false);
			DialogConfig.permission = (UIConfigModelManager.getInstance().getPlainText(label9.getText()));
			System.out.println(UIConfigModelManager.getInstance().getPlainText(label9.getText()));
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label9.getText()));
			label9.addMouseListener(new MouseEventClickButton("thietlap1.png", "thietlap2.png", "thietlap3.png"));
			add(label9);
			label9.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JButton btn = (JButton) e.getSource();
//					System.out.println("??  "+UIConfigModelManager.getInstance().getPlainText(btn.getText()));
//					if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
//						DialogJurisdiction.getInstace().setVisible(true);
//						return;
//					}
						DialogConfig dialog = new DialogConfig(false, 600, 100);
						dialog.setName("dlThietLapBH");
						dialog.setTitle("Thiết lập bán hàng");
						dialog.setVisible(true);
						if (PanelScreenSaleLocationPos.salePOS != null)
							PanelScreenSaleLocationPos.getInstance().getProfileConfigData();
				}
			});
		}
	}
	
	public static class MenuRightUnder extends JPanel {
		public MenuRightUnder() {
			setBackground(Color.WHITE);
			setLayout(new GridLayout(3, 3));
			setBorder(BorderFactory.createTitledBorder(null, "Báo cáo thống kê", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 16), new java.awt.Color(204, 0, 0)));
			
			/**
			 * THỐNG KÊ THU CHI
			 */
			JButton label1 = CreateButton("<html>Thống kê<br>thu chi</br></html>");
			label1.setFocusPainted(false);
			label1.setIcon(new ImageIcon(getClass().getResource("icon/statistic1.png")));
			ReportStatistics.permission = (UIConfigModelManager.getInstance().getPlainText(label1.getText()));
			label1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (LicenseManager.getInstance().getLicense(LicenseManager.module.Report.name())) {
						try {
							JButton btn = (JButton) e.getSource();
							if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
								DialogJurisdiction.getInstace().setVisible(true);
								return;
							}
							
							DialogReport dialog = new DialogReport(new ReportStatistics(new JTable(), ReportStatistics.TypeReport.TKThuChi));
							dialog.dispose();
							dialog.setUndecorated(true);
							dialog.setIcon("statistic1.png");
							dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
							dialog.setModal(true);
							dialog.setLocationRelativeTo(null);
							dialog.setTitle("Thống kê thu chi");
							dialog.setVisible(true);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} else {
						PanelOKRestore panel = new PanelOKRestore("Bạn chưa đăng ký bản quyền với module chức năng này!", "Hãy liên hệ với công ty HKTConsultant!");
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
							PanelOKRestore panel = new PanelOKRestore("Bạn chưa đăng ký bản quyền với module chức năng này!", "Hãy liên hệ với công ty HKTConsultant!");
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
							PanelOKRestore panel = new PanelOKRestore("Bạn chưa đăng ký bản quyền với module chức năng này!", "Hãy liên hệ với công ty HKTConsultant!");
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
							dialog.setIcon("tktime1.png");
							dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
							dialog.setModal(true);
							dialog.setLocationRelativeTo(null);
							dialog.setTitle("Thống kê theo thời gian");
							dialog.setVisible(true);
						} else {
							PanelOKRestore panel = new PanelOKRestore("Bạn chưa đăng ký bản quyền với module chức năng này!", "Hãy liên hệ với công ty HKTConsultant!");
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
			
//			/**
//			 * THỐNG KÊ TỒN KHO
//			 */
//			JButton label5 = CreateButton("<html>Thống kê<br>kế toán</br></html>");
//			try {
//				List<Recipe> recipes = RestaurantModelManager.getInstance().getRecipes();
//				if (recipes.size() > 0) {
//					label5.setEnabled(true);
//					RestaurantModelManager.getInstance().setRecipe(true);
//				}
//      } catch (Exception e) {
//      }
//			label5.setFocusPainted(false);
//			label5.setIcon(new ImageIcon(getClass().getResource("icon/tkban1.png")));
//			label5.addMouseListener(new MouseEventClickButton("tkban1.png", "tkban2.png", "tkban3.png"));
//			add(label5);
//			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label5.getText()));
//			label5.addActionListener(new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					try {
//						JButton btn = (JButton) e.getSource();
//						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
//							DialogJurisdiction.getInstace().setVisible(true);
//							return;
//						}
//						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Report.name())) {
//							DialogReport dialog = new DialogReport(new ReportStatistics(new JTable(), ReportStatistics.TypeReport.TKKeToan));
//							dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
//							dialog.setModal(true);
//							dialog.setIcon("tkban1.png");
//							dialog.setLocationRelativeTo(null);
//							dialog.setTitle("Thống kê kế toán");
//							dialog.setVisible(true);
//						} else {
//							PanelOKRestore panel = new PanelOKRestore("Bạn chưa đăng ký bản quyền với module chức năng này!", "Hãy liên hệ với công ty HKTConsultant!");
//							DialogResto dialog1 = new DialogResto(panel, false, 0, 120);
//							dialog1.setTitle("Bản quyền");
//							dialog1.setLocationRelativeTo(null);
//							dialog1.setModal(true);
//							dialog1.setVisible(true);
//							
//							DialogInfo di = new DialogInfo();
//							DialogResto dialog = new DialogResto(di, false, 200, 450);
//							dialog.setName("dlInfo");
//							dialog.setLocationRelativeTo(null);
//							dialog.setTitle("Liên hệ");
//							dialog.setVisible(true);
//						}
//					} catch (Exception e2) {
//						e2.printStackTrace();
//					}
//				}
//			});
			
			/**
			 * THỐNG KÊ KẾ TOÁN
			 */
			JButton label5 = CreateButton("<html>Thống kê<br>kế toán</br></html>");
			label5.setFocusPainted(false);
			label5.setIcon(new ImageIcon(getClass().getResource("icon/statistic1.png")));
			ReportStatistics.permission = (UIConfigModelManager.getInstance().getPlainText(label5.getText()));
			label5.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (LicenseManager.getInstance().getLicense(LicenseManager.module.Report.name())) {
						try {
							JButton btn = (JButton) e.getSource();
							if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
								DialogJurisdiction.getInstace().setVisible(true);
								return;
							}
							
							DialogReport dialog = new DialogReport(new ReportStatistics(new JTable(), ReportStatistics.TypeReport.TKKeToan));
							dialog.dispose();
							dialog.setUndecorated(true);
							dialog.setIcon("statistic1.png");
							dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
							dialog.setModal(true);
							dialog.setLocationRelativeTo(null);
							dialog.setTitle("Thống kê kế toán");
							dialog.setVisible(true);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} else {
						PanelOKRestore panel = new PanelOKRestore("Bạn chưa đăng ký bản quyền với module chức năng này!", "Hãy liên hệ với công ty HKTConsultant!");
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
					
					
				}
			});
			label5.addMouseListener(new MouseEventClickButton("statistic1.png", "statistic2.png", "statistic3.png"));
			add(label5);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label5.getText()));
			
			
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
							PanelOKRestore panel = new PanelOKRestore("Bạn chưa đăng ký bản quyền với module chức năng này!", "Hãy liên hệ với công ty HKTConsultant!");
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
							PanelOKRestore panel = new PanelOKRestore("Bạn chưa đăng ký bản quyền với module chức năng này!", "Hãy liên hệ với công ty HKTConsultant!");
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
			
			// /**
			// * PHIẾU SẢN XUẤT
			// */
			// JButton label7 =
			// CreateButton("<html>Phiếu <br> sản xuất</br></html>");
			// label7.setFocusPainted(false);
			// label7.setIcon(new
			// ImageIcon(getClass().getResource("icon/thuchi1.png")));
			// label7.addMouseListener(new MouseEventClickButton("thuchi1.png",
			// "thuchi1.png", "thuchi1.png"));
			// add(label7);
			// label7.addActionListener(new ActionListener() {
			// @Override
			// public void actionPerformed(ActionEvent e) {
			// try {
			// JButton btn = (JButton) e.getSource();
			// if (UIConfigModelManager.getInstance().getPermission(
			// UIConfigModelManager.getInstance().getPlainText(btn.getText()))
			// == null) {
			// JOptionPane.showMessageDialog(null,
			// "Bạn chưa được phân quyền này !");
			// return;
			// }
			// if
			// (LicenseManager.getInstance().getLicense(LicenseManager.module.Report.name()))
			// {
			// PanelPatternProduction panel = new PanelPatternProduction();
			// panel.setName("PhieuSanXuat");
			// DialogResto dialog = new DialogResto(panel, true, 600, 150);
			// dialog.setIcon("thuchi1.png");
			// dialog.dispose();
			// dialog.setUndecorated(true);
			// dialog.setModal(true);
			// dialog.setTitle("Phiếu sản xuất");
			// dialog.setName("dlPhieuSanXuat");
			// dialog.setLocationRelativeTo(null);
			// dialog.setVisible(true);
			// } else {
			// PanelOKRestore panel = new
			// PanelOKRestore("Bạn chưa đăng ký bản quyền với module chức năng này!",
			// "Hãy liên hệ với công ty HKTConsultant!");
			// DialogResto dialog1 = new DialogResto(panel, false, 0, 120);
			// dialog1.setTitle("Bản quyền");
			// dialog1.setLocationRelativeTo(null);
			// dialog1.setModal(true);
			// dialog1.setVisible(true);
			//
			// DialogInfo di = new DialogInfo();
			// DialogResto dialog = new DialogResto(di, false, 200, 450);
			// dialog.setName("dlInfo");
			// dialog.setLocationRelativeTo(null);
			// dialog.setTitle("Liên hệ");
			// dialog.setVisible(true);
			// }
			// } catch (Exception e2) {
			// e2.printStackTrace();
			// }
			// }
			// });
			
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
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
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
							PanelOKRestore panel = new PanelOKRestore("Bạn chưa đăng ký bản quyền với module chức năng này!", "Hãy liên hệ với công ty HKTConsultant!");
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
			JButton label9 = CreateButton("<html>Thống kê<br>Hóa đơn</br></html>");
			label9.setFocusPainted(false);
			label9.setIcon(new ImageIcon(getClass().getResource("icon/datban1.png")));
			label9.addMouseListener(new MouseEventClickButton("datban1.png", "datban2.png", "datban3.png"));
			add(label9);
			label9.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						PanelSearchInvoice panel = new PanelSearchInvoice();
						panel.setName("PanelSearchInvoice");
						DialogResto dialog = new DialogResto(panel, false, 0, 200);
						dialog.setIcon("invoiceds1.png");
						dialog.setName("dlPanelSearchInvoice");
						dialog.setTitle("Thống kê hóa đơn");
						dialog.setVisible(true);
						if (panel.isSave()) {
							panel.createTablePaymentItem();
						}
						
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					
				}
			});
			
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
			// filterQuery.add("startDate", FilterQuery.Operator.GT, "'" +
			// dateStart +
			// "'");
			// filterQuery.add("startDate", FilterQuery.Operator.LT, "'" +
			// dateEnd +
			// "'");
			// filterQuery.add("status", FilterQuery.Operator.STRINGEQ,
			// Status.Paid);
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
		}
	}
	
	static public JLabel CreateLabel(String text) {
		JLabel label = new JLabel(text);
		// label.setPreferredSize(new Dimension(150, 40));
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
