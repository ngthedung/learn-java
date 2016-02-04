package com.hkt.client.swingexp.homescreen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.basic.BasicBorders;

import com.hkt.client.swingexp.app.core.DialogJurisdiction;
import com.hkt.client.swingexp.app.core.DialogList;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.MouseEventClickButton;
import com.hkt.client.swingexp.app.hethong.DialogInfo;
import com.hkt.client.swingexp.app.hethong.PanelOKRestore;
import com.hkt.client.swingexp.app.khuyenmai.DialogShowMenu;
import com.hkt.client.swingexp.app.khuyenmai.DialogShowVoucher;
import com.hkt.client.swingexp.app.khuyenmai.PanelCoupon;
import com.hkt.client.swingexp.app.khuyenmai.PanelGiveProduct;
import com.hkt.client.swingexp.app.khuyenmai.PanelPromotion;
import com.hkt.client.swingexp.app.khuyenmai.list.TableListCoupon;
import com.hkt.client.swingexp.app.khuyenmai.list.TableListCouponUsed;
import com.hkt.client.swingexp.app.khuyenmai.list.TableListMenu;
import com.hkt.client.swingexp.app.khuyenmai.list.TableListPromotion;
import com.hkt.client.swingexp.app.khuyenmai.list.TableListPromotionPartners;
import com.hkt.client.swingexp.app.khuyenmai.list.TableListPromotionUsed;
import com.hkt.client.swingexp.app.khuyenmai.list.TableListVoucher;
import com.hkt.client.swingexp.app.license.LicenseManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;

@SuppressWarnings("serial")
public class MenuRightKhuyenMai extends JPanel {
	public MenuRightKhuyenMai() {
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
			setLayout(new GridLayout(3, 3));
			setBackground(Color.WHITE);
			setBorder(BorderFactory.createTitledBorder(null, "Chức năng cơ bản", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 16), new java.awt.Color(204, 0, 0)));

			// KHUYẾN MÃI TẶNG SP
			JButton label1 = CreateButton("<html>Khuyến mãi<br>tặng SP</br></html>");
			label1.setName("KhuyenMaiTangSanPham");
			label1.setFocusPainted(false);
			label1.setForeground(Color.black);
			label1.setIcon(new ImageIcon(getClass().getResource("icon/promosanpham1.png")));
			PanelGiveProduct.permission = (UIConfigModelManager.getInstance().getPlainText(label1.getText()));
			label1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					try {
						JButton btn = (JButton) evt.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Promotion.name())) {
							PanelGiveProduct pnGiveProduct = new PanelGiveProduct();
							pnGiveProduct.setName("KhuyenMaiTangSanPham");
							DialogMain dialog = new DialogMain(pnGiveProduct);
							dialog.setName("dlKhuyenMaiTangSanPham");
							dialog.setIcon("promosanpham1.png");
							dialog.setTitle("Khuyến Mãi Tặng Sản Phẩm");
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
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			label1.addMouseListener(new MouseEventClickButton("promosanpham1.png", "promosanpham2.png", "promosanpham3.png"));
			add(label1);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label1.getText()));

			// LẬP KM
			JButton label2 = CreateButton("<html>Lập<br>khuyến mãi</br></html>");
			label2.setActionCommand("3");
			label2.setFocusPainted(false);
			label2.setName("LapKhuyenMai");
			label2.setForeground(Color.black);
			label2.setIcon(new ImageIcon(getClass().getResource("icon/promobophan1.png")));
			PanelPromotion.permission = (UIConfigModelManager.getInstance().getPlainText(label2.getText()));
			label2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Promotion.name())) {
							PanelPromotion panelPromotion = new PanelPromotion();
							panelPromotion.setName("LapKhuyenMai");
							DialogMain dialog = new DialogMain(panelPromotion);
							dialog.setIcon("promobophan1.png");
							dialog.setName("dlLapKM");
							dialog.setTitle("Lập khuyến mại");
							dialog.setResizable(true);
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
					} catch (Exception e2) {
					}
				}
			});
			label2.addMouseListener(new MouseEventClickButton("promobophan1.png", "promobophan2.png", "promobophan1.png"));
			add(label2);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label2.getText()));

			// QL KM
			JButton label3 = CreateButton("<html>Quản lý<br>khuyến mãi</br></html>");
			label3.setName("QuanLyKhuyenMai");
			label3.setFocusPainted(false);
			label3.setForeground(Color.black);
			TableListPromotion.permission = (UIConfigModelManager.getInstance().getPlainText(label3.getText()));
			label3.setIcon(new ImageIcon(getClass().getResource("icon/promo1.png")));
			label3.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Promotion.name())) {
							TableListPromotion tbPromotion = new TableListPromotion();
							tbPromotion.setName("tbQuanLyKhuyenMai");
							DialogList dialog = new DialogList(tbPromotion);
							dialog.setIcon("promo1.png");
							dialog.setName("dlQuanLyKhuyenMai");
							dialog.setTitle("Quản lý khuyến mãi");
							dialog.setResizable(true);
							dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
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
			label3.addMouseListener(new MouseEventClickButton("promo1.png", "promo2.png", "promo3.png"));
			add(label3);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label3.getText()));

			// DS SD KM TẶNG SP
			JButton label5 = CreateButton("<html>DS SD KM<br>tặng SP</br></html>");
			label5.setForeground(Color.black);
			label5.setFocusPainted(false);
			label5.setIcon(new ImageIcon(getClass().getResource("icon/promodoitac1.png")));
			TableListPromotionPartners.permission = (UIConfigModelManager.getInstance().getPlainText(label5.getText()));
			label5.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Promotion.name())) {
							TableListPromotionPartners tbpromotionPartners = new TableListPromotionPartners();
							tbpromotionPartners.setName("tbpromotionPartners");
							DialogList dialog = new DialogList(tbpromotionPartners);
							dialog.setIcon("promodoitac1.png");
							dialog.setName("dlPromotionpartners");
							dialog.setTitle("Danh sách sử dụng KM Tặng SP");
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

			label5.addMouseListener(new MouseEventClickButton("promodoitac1.png", "promodoitac2.png", "promodoitac3.png"));
			add(label5);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label5.getText()));

			// DS SỬ DỰNG KM
			JButton label4 = CreateButton("<html>DS sử<br>dụng KM</br></html>");
			label4.setForeground(Color.black);
			label4.setFocusPainted(false);
			TableListPromotionUsed.permission = (UIConfigModelManager.getInstance().getPlainText(label4.getText()));
			label4.setIcon(new ImageIcon(getClass().getResource("icon/promosanpham1.png")));
			label4.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Promotion.name())) {
							TableListPromotionUsed tbPromotion = new TableListPromotionUsed();
							tbPromotion.setName("tbPromotionUsed");
							DialogList dialog = new DialogList(tbPromotion);
							dialog.setIcon("promosanpham1.png");
							dialog.setName("dlListPromotionUsed");
							dialog.setTitle("Danh sách sử dụng khuyến mãi");
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

			label4.addMouseListener(new MouseEventClickButton("promosanpham1.png", "promosanpham2.png", "promosanpham3.png"));
			add(label4);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label4.getText()));

			// CHƯA CÓ
			JButton label6 = CreateButton("<html></html>");
			label6.setForeground(Color.red);
			label6.setFocusPainted(false);
			label6.setIcon(null);
//			label6.addMouseListener(new MouseEventClickButton("promoban1.png", "promoban2.png", "promoban3.png"));
			add(label6);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label6.getText()));

			// PHIẾU GIẢM GIÁ
			JButton label7 = CreateButton("<html>Lập Phiếu<br>giảm giá</br></html>");
			label7.setForeground(Color.black);
			label7.setFocusPainted(false);
			label7.setIcon(new ImageIcon(getClass().getResource("icon/coupon1.png")));
			PanelCoupon.permission = (UIConfigModelManager.getInstance().getPlainText(label7.getText()));
			label7.setName("PhieuGiamGia");
			label7.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					try {
						JButton btn = (JButton) evt.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Promotion.name())) {
							PanelCoupon panel = new PanelCoupon();
							panel.setName("PhieuGiamGia");
							DialogMain dialog = new DialogMain(panel);
							dialog.setIcon("coupon1.png");
							dialog.setName("dlPhieuGiamGia");
							dialog.setTitle("Lập phiếu giảm giá");
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
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			label7.addMouseListener(new MouseEventClickButton("coupon1.png", "coupon2.png", "coupon3.png"));
			add(label7);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label7.getText()));

			// DS PHIẾU GIẢM GIÁ
			JButton label8 = CreateButton("<html>DS Phiếu<br>giảm giá</br></html>");
			label8.setForeground(Color.black);
			label8.setFocusPainted(false);
			label8.setIcon(new ImageIcon(getClass().getResource("icon/dskm1.png")));
			label8.setName("DanhSachPhieuGiamGia");
			label8.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Promotion.name())) {
							TableListCoupon panel = new TableListCoupon();
							panel.setName("tbDanhSachPhieuGiamGia");
							DialogList dialog = new DialogList(panel);
							dialog.setComponent1(panel.getBtnPrint());
							dialog.setComponent2(panel.getCboKM());
							dialog.setComponent3(panel.getLbKM());
							dialog.setIcon("dskm1.png");
							dialog.setName("dlDanhSachPhieuGiamGia");
							dialog.setTitle("Danh sách phiếu giảm giá");
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
			label8.addMouseListener(new MouseEventClickButton("dskm1.png", "dskm2.png", "dskm3.png"));
			add(label8);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label8.getText()));

			// DS SD PHIẾU
			JButton label9 = CreateButton("<html>DS SD<br>Phiếu</br></html>");
			label9.setForeground(Color.black);
			label9.setFocusPainted(false);
			label9.setIcon(new ImageIcon(getClass().getResource("icon/dskm1.png")));
			label9.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Promotion.name())) {
							TableListCouponUsed tbPromotionUsed = new TableListCouponUsed();
							tbPromotionUsed.setName("tbPromotionUsed");
							DialogList dialog = new DialogList(tbPromotionUsed);
							dialog.setIcon("dskm1.png");
							dialog.setName("dlListPromotionUsed");
							dialog.setTitle("DS SD Phiếu");
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
			label9.addMouseListener(new MouseEventClickButton("dskm1.png", "dskm2.png", "dskm3.png"));
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

			// MENU
			JButton label1 = CreateButton("<html>Lập Menu</html>");
			label1.setName("Menu");
			label1.setForeground(Color.black);
			label1.setFocusPainted(false);
			label1.setIcon(new ImageIcon(getClass().getResource("icon/menucon1.png")));
			DialogShowMenu.permission = (UIConfigModelManager.getInstance().getPlainText(label1.getText()));
			label1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.MenuVoucher.name())) {
							DialogShowMenu dialogShowMenu = new DialogShowMenu();
							dialogShowMenu.setName("Menu");
							dialogShowMenu.setSize(100, 100);
							dialogShowMenu.setPreferredSize(new Dimension(100, 100));
							DialogMain dialog = new DialogMain(dialogShowMenu, true);
							dialog.setIcon("menucon1.png");
							dialog.setName("dlMenu");
							dialog.setResizable(false);
							dialog.setTitle("Lập menu");
							dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
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
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			});
			label1.addMouseListener(new MouseEventClickButton("menucon1.png", "menucon2.png", "menucon3.png"));
			add(label1);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label1.getText()));

			// DS MENU
			JButton label2 = CreateButton("<html>Danh sách<br>Menu</br></html>");
			label2.setName("btnMenuList");
			label2.setForeground(Color.black);
			label2.setFocusPainted(false);
			label2.setIcon(new ImageIcon(getClass().getResource("icon/menu1.png")));
			label2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.MenuVoucher.name())) {
							TableListMenu tbMenu = new TableListMenu();
							tbMenu.setName("tbQuanLyMenu");
							DialogList dialog = new DialogList(tbMenu);
							dialog.setIconImage(null);
							dialog.setIcon("menu1.png");
							dialog.setName("dlTable");
							dialog.setTitle("Danh sách Menu");
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
			label2.addMouseListener(new MouseEventClickButton("menu1.png", "menu2.png", "menu3.png"));
			add(label2);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label2.getText()));

			// CHƯA CÓ
			JButton label3 = CreateButton("<html></html>");
			label3.setForeground(Color.red);
			label3.setFocusPainted(false);
			label3.setIcon(null);
//			label3.addMouseListener(new MouseEventClickButton("tktime1.png", "tktime2.png", "tktime3.png"));
			add(label3);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label3.getText()));

			// VOUCHER
			JButton label4 = CreateButton("<html>Lập<br>Voucher</br></html>");
			label4.setName("Voucher");
			label4.setForeground(Color.black);
			label4.setFocusPainted(false);
			label4.setIcon(new ImageIcon(getClass().getResource("icon/voucher1.png")));
			DialogShowVoucher.permission = (UIConfigModelManager.getInstance().getPlainText(label4.getText()));
			label4.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.MenuVoucher.name())) {
							DialogShowVoucher dialogShowVoucher = new DialogShowVoucher();
							dialogShowVoucher.setName("Voucher");
							dialogShowVoucher.setSize(100, 100);
							dialogShowVoucher.setPreferredSize(new Dimension(100, 100));
							DialogMain dialog = new DialogMain(dialogShowVoucher, true);
							dialog.setName("dlVoucher");
							dialog.setIcon("voucher1.png");
							dialog.setResizable(false);
							dialog.setTitle("Thiết lập voucher");
							dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
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
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					;
				}
			});
			label4.addMouseListener(new MouseEventClickButton("voucher1.png", "voucher2.png", "voucher3.png"));
			add(label4);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label4.getText()));

			// DS VOUCHER
			JButton label5 = CreateButton("<html>DS<br>Voucher</br></html>");
			label5.setName("btnVoucherList");
			label5.setFocusPainted(false);
			label5.setIcon(new ImageIcon(getClass().getResource("icon/dskm1.png")));
			label5.setForeground(Color.black);
			label5.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.MenuVoucher.name())) {
							TableListVoucher tbVoucher = new TableListVoucher();
							tbVoucher.setName("tbQuanLyVoucher");
							DialogList dialog = new DialogList(tbVoucher);
							dialog.setIcon("dskm1.png");
							dialog.setName("dlTable");
							dialog.setTitle("Danh Sách Voucher");
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

					}
				}
			});
			label5.addMouseListener(new MouseEventClickButton("dskm1.png", "dskm2.png", "dskm3.png"));
			add(label5);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label5.getText()));

			// CHƯA CÓ
			JButton label6 = CreateButton("<html></html>");
			label6.setForeground(Color.red);
			label6.setFocusPainted(false);
			label6.setIcon(null);
//			label6.addMouseListener(new MouseEventClickButton("tknhansu1.png", "tknhansu2.png", "tknhansu3.png"));
			add(label6);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label6.getText()));


			// CHƯA CÓ
			JButton label16 = CreateButton("<html></html>");
			label16.setForeground(Color.red);
			label16.setFocusPainted(false);
			label16.setIcon(null);
			add(label16);


		}
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
		label.setIconTextGap(7);
		Border border = new BorderUIResource.CompoundBorderUIResource(new BasicBorders.ButtonBorder(Color.white, Color.white, Color.white,
				Color.white), new BasicBorders.MarginBorder());
		label.setBorder(border);
		return label;
	}

}
