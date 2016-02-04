package com.hkt.client.swingexp.homescreen;

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
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.basic.BasicBorders;

import com.hkt.client.swingexp.app.banhang.PanelBank;
import com.hkt.client.swingexp.app.banhang.PanelSearchInvoice;
import com.hkt.client.swingexp.app.banhang.PanelTax;
import com.hkt.client.swingexp.app.banhang.ReportStatistics;
import com.hkt.client.swingexp.app.banhang.screen.often.DialogConfig;
import com.hkt.client.swingexp.app.banhang.screen.pos.DialogPhieuDatMuaHangPos;
import com.hkt.client.swingexp.app.banhang.screen.pos.DialogScreenOftenPos;
import com.hkt.client.swingexp.app.core.DialogContain;
import com.hkt.client.swingexp.app.core.DialogJurisdiction;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogReport;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.MouseEventClickButton;
import com.hkt.client.swingexp.app.hethong.DialogInfo;
import com.hkt.client.swingexp.app.hethong.PaneSetDate;
import com.hkt.client.swingexp.app.hethong.PanelOKRestore;
import com.hkt.client.swingexp.app.license.LicenseManager;
import com.hkt.client.swingexp.app.thuchi.PanelPaymentReceipt;
import com.hkt.client.swingexp.app.thuchi.PanelPhieuDatMuaHang;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.accounting.entity.Invoice.ActivityType;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.restaurant.entity.Recipe;

@SuppressWarnings("serial")
public class MenuRightThuChiMuaHang extends JPanel {
	public static Profile profile;

	public MenuRightThuChiMuaHang() {
		setLayout(new GridLayout(2, 1));
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()));
		MenuRightTop menuRightTop = new MenuRightTop();
		JPanel panelTop = new JPanel();
		panelTop.setLayout(new GridLayout());
		panelTop.setBackground(Color.WHITE);
		panelTop.add(menuRightTop);
		add(panelTop);

		MenuRightUnder menuRightUnder = new MenuRightUnder();
		JPanel panelBot = new JPanel();
		panelBot.setBackground(Color.WHITE);
		panelBot.setLayout(new GridLayout());
		panelBot.add(menuRightUnder);
		add(panelBot);

	}

	public static class MenuRightTop extends JPanel {
		public MenuRightTop() {
			setLayout(new GridLayout(3, 3));
			setBackground(Color.WHITE);
			setBorder(BorderFactory.createTitledBorder(null, "Chức năng cơ bản",
			    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
			    new java.awt.Font("Tahoma", 1, 16), new java.awt.Color(204, 0, 0)));

			// PHIẾU THU TIỀN
			JButton label1 = CreateButton("<html>Phiếu thu<br>tiền</br></html>");
			label1.setActionCommand("3");
			label1.setFocusPainted(false);
			label1.setName("PhieuThuTien");
			PanelPaymentReceipt.permission1 = (UIConfigModelManager.getInstance().getPlainText(label1.getText()));
			label1.setIcon(new ImageIcon(getClass().getResource("icon/invoice1.png")));
			label1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance()
			          .getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						PanelPaymentReceipt panel = new PanelPaymentReceipt(ActivityType.Receipt);
						panel.setName("PhieuThuTien");
						DialogMain dialog = new DialogMain(panel, 525, 280);
						dialog.getBtnExt().setVisible(true);
						dialog.getBtnExt().addActionListener(panel.getActionListener());
						dialog.setIcon("invoice1.png");
						dialog.setName("dlPhieuThuTien");
						dialog.setLocationRelativeTo(null);
						dialog.setTitle("Phiếu thu tiền");
						dialog.setVisible(true);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			label1.addMouseListener(new MouseEventClickButton("invoice1.png", "invoice2.png", "invoice3.png"));
			add(label1);
			UIConfigModelManager.getInstance()
			    .setPermission(UIConfigModelManager.getInstance().getPlainText(label1.getText()));

			// PHIẾU CHI TIỀN
			JButton label2 = CreateButton("<html>Phiếu chi<br>tiền</br></html>");
			label1.setActionCommand("3");
			label2.setFocusPainted(false);
			PanelPaymentReceipt.permission2 = (UIConfigModelManager.getInstance().getPlainText(label2.getText()));
			label2.setIcon(new ImageIcon(getClass().getResource("icon/invoice1.png")));
			label2.setName("PhieuChiTien");
			label2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance()
			          .getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						PanelPaymentReceipt panel = new PanelPaymentReceipt(ActivityType.Payment);
						panel.setName("PhieuChiTien");
						DialogMain dialog = new DialogMain(panel, 525, 280);
						dialog.getBtnExt().setVisible(true);
						dialog.getBtnExt().addActionListener(panel.getActionListener());
						dialog.setIcon("invoice1.png");
						dialog.setName("dlPhieuThuTien");
						dialog.setLocationRelativeTo(null);
						dialog.setTitle("Phiếu chi tiền");
						dialog.setVisible(true);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			label2.addMouseListener(new MouseEventClickButton("invoice1.png", "invoice2.png", "invoice3.png"));
			add(label2);
			UIConfigModelManager.getInstance()
			    .setPermission(UIConfigModelManager.getInstance().getPlainText(label2.getText()));

			// DS THU CHI
			JButton label3 = CreateButton("<html>DS thu<br>chi tiền</br></html>");
			label3.setName("DSThuchi");
			label3.setFocusPainted(false);
			label3.setIcon(new ImageIcon(getClass().getResource("icon/datban1.png")));
			PanelSearchInvoice.permission = (UIConfigModelManager.getInstance().getPlainText(label3.getText()));
			label3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					if (LicenseManager.getInstance().getLicense(LicenseManager.module.Restaurant.name())) {
						try {
							JButton btn = (JButton) evt.getSource();
							if (UIConfigModelManager.getInstance()
			            .getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
								DialogJurisdiction.getInstace().setVisible(true);
								return;
							}
							PanelSearchInvoice panel = new PanelSearchInvoice();
							panel.loadDate(PaneSetDate.DateInvoiceTC);
							panel.setName("DSThuchi");
							panel.setPermissionEmployee(UIConfigModelManager.getInstance().getPlainText(btn.getText()));
							DialogResto dialog = new DialogResto(panel, false, 0, 200);
							dialog.setName("dlDSThuchi");
							dialog.setTitle("Danh sách thu chi tiền");
							dialog.setIcon("datban1.png");
							// dialog.setSize(600, 370);
							dialog.setLocationRelativeTo(null);
							dialog.setVisible(true);
							if (panel.isSave()) {
								panel.createTableReceipt();
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
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
				}

			});
			label3.addMouseListener(new MouseEventClickButton("datban1.png", "datban2.png", "datban3.png"));
			add(label3);
			UIConfigModelManager.getInstance()
			    .setPermission(UIConfigModelManager.getInstance().getPlainText(label3.getText()));

			// PHIẾU ĐẶT MUA HÀNG
			JButton label4 = CreateButton("<html>Phiếu đặt<br>mua hàng</br></html>");
			label4.setActionCommand("3");
			PanelPhieuDatMuaHang.permission1 = (UIConfigModelManager.getInstance().getPlainText(label4.getText()));
			label4.setName("PhieuDatMuaHang");
			label4.setFocusPainted(false);
			label4.setIcon(new ImageIcon(getClass().getResource("icon/order1.png")));
			label4.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance()
			          .getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						//if (profile.get(DialogConfig.Keyboard) != null && profile.get(DialogConfig.Keyboard).toString().equals("true")) {
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Warehouse.name())) {
							profile = AccountModelManager.getInstance().getProfileConfigAdmin();
							if (profile.get(DialogConfig.GDPos).toString().equals("true")
									&&profile.get(DialogConfig.Keyboard) != null && profile.get(DialogConfig.Keyboard).toString().equals("true")) {
								DialogPhieuDatMuaHangPos panel = DialogPhieuDatMuaHangPos.getInstance();
								if (DialogScreenOftenPos.isFlagScreenOften()) {
									DialogScreenOftenPos.setFlagScreenOften(false);
									DialogScreenOftenPos pos = DialogScreenOftenPos.getInstance();
									panel.getPanelTop2().add(pos.getPanelProductTagRoot(), 1);
									panel.getScrollPane_Product().setViewportView(pos.getPanelProducts());
								}
								if (!panel.getNameType().equals("PDH")) {
									InvoiceDetail detail = new InvoiceDetail();
									detail.setType(AccountingModelManager.typeDatHang);
									detail.setActivityType(ActivityType.Payment);
									panel.setTypeInvoice(detail, 0, "PDH");
								}
								panel.setVisible(true);
							} else {
								PanelPhieuDatMuaHang panel = new PanelPhieuDatMuaHang(ActivityType.Payment,
			              AccountingModelManager.typeDatHang, "PDH");
								panel.setName("PhieuDatMuaHang");
								try {
									if (profile.get(DialogConfig.Keyboard) != null
			                && profile.get(DialogConfig.Keyboard).toString().equals("true")) {
										DialogResto dialog = new DialogResto(panel, true, 0, 0);
										dialog.setTitle("Phiếu đặt mua hàng");
										dialog.setIcon("order1.png");
										dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
										dialog.dispose();
										dialog.setUndecorated(true);
										dialog.setLocationRelativeTo(null);
										dialog.setBtnSave(false);
										dialog.setBtnExit(false);
										dialog.setModal(true);
										dialog.setVisible(true);
									} else {
										DialogContain dialog1 = new DialogContain(panel);
										dialog1.getContentPane().add(panel);
										dialog1.setName("dlPhieuDatMuaHang");
										dialog1.setSize(Toolkit.getDefaultToolkit().getScreenSize());
										dialog1.dispose();
										dialog1.setUndecorated(true);
										dialog1.setLocationRelativeTo(null);
										dialog1.setModal(true);
										dialog1.setVisible(true);
									}
								} catch (Exception e1) {
								}
							}
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
			label4.addMouseListener(new MouseEventClickButton("order1.png", "order2.png", "order3.png"));
			add(label4);
			UIConfigModelManager.getInstance()
			    .setPermission(UIConfigModelManager.getInstance().getPlainText(label4.getText()));

			// KHÁCH ĐẶT HÀNG
			JButton label5 = CreateButton("<html>Khách<br>đặt hàng</br></html>");
			label5.setName("PhieuKhachDatHang");
			label5.setActionCommand("3");
			label5.setFocusPainted(false);
			PanelPhieuDatMuaHang.permission2 = (UIConfigModelManager.getInstance().getPlainText(label5.getText()));
			label5.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance()
			          .getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Warehouse.name())) {
							profile = AccountModelManager.getInstance().getProfileConfigAdmin();
							if (profile.get(DialogConfig.GDPos).toString().equals("true")
									&&profile.get(DialogConfig.Keyboard) != null && profile.get(DialogConfig.Keyboard).toString().equals("true")) {
								DialogPhieuDatMuaHangPos panel = DialogPhieuDatMuaHangPos.getInstance();
								if (DialogScreenOftenPos.isFlagScreenOften()) {
									DialogScreenOftenPos.setFlagScreenOften(false);
									DialogScreenOftenPos pos = DialogScreenOftenPos.getInstance();
									panel.getPanelTop2().add(pos.getPanelProductTagRoot(), 1);
									panel.getScrollPane_Product().setViewportView(pos.getPanelProducts());
								}
								if (!panel.getNameType().equals("KDH")) {
									InvoiceDetail detail = new InvoiceDetail();
									detail.setType(AccountingModelManager.typeDatHang);
									detail.setActivityType(ActivityType.Receipt);
									panel.setTypeInvoice(detail, 0, "KDH");
								}
								panel.setVisible(true);
							} else {
								PanelPhieuDatMuaHang panel = new PanelPhieuDatMuaHang(ActivityType.Receipt,
			              AccountingModelManager.typeDatHang, "KDH");
								panel.setName("PhieuKhachDatHang");
								try {
									if (profile.get(DialogConfig.Keyboard) != null
			                && profile.get(DialogConfig.Keyboard).toString().equals("true")) {
										DialogResto dialog = new DialogResto(panel, true, 0, 0);
										dialog.setTitle("Khách đặt hàng");
										dialog.setIcon("order1.png");
										dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
										dialog.dispose();
										dialog.setUndecorated(true);
										dialog.setLocationRelativeTo(null);
										dialog.setBtnSave(false);
										dialog.setBtnExit(false);
										dialog.setModal(true);
										dialog.setVisible(true);
									} else {
										DialogContain dialog1 = new DialogContain(panel);
										dialog1.getContentPane().add(panel);
										dialog1.setName("dlPhieuDatMuaHang");
										dialog1.setSize(Toolkit.getDefaultToolkit().getScreenSize());
										dialog1.dispose();
										dialog1.setUndecorated(true);
										dialog1.setLocationRelativeTo(null);
										dialog1.setModal(true);
										dialog1.setVisible(true);
									}

								} catch (Exception e1) {
								}
							}
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
			label5.setIcon(new ImageIcon(getClass().getResource("icon/order1.png")));
			label5.addMouseListener(new MouseEventClickButton("order1.png", "order2.png", "order3.png"));
			add(label5);
			UIConfigModelManager.getInstance()
			    .setPermission(UIConfigModelManager.getInstance().getPlainText(label5.getText()));

			// QL ĐƠN ĐẶT HÀNG
			JButton label6 = CreateButton("<html>Q.Lý đơn<br>đặt hàng</br></html>");
			label6.setIcon(new ImageIcon(getClass().getResource("icon/dsorder1.png")));
			label6.setName("QuanLyDonDatHang");
			label6.setFocusPainted(false);
			label6.addMouseListener(new MouseEventClickButton("dsorder1.png", "dsorder2.png", "dsorder3.png"));
			label6.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					try {
						JButton btn = (JButton) evt.getSource();
						if (UIConfigModelManager.getInstance()
			          .getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Warehouse.name())) {
							PanelSearchInvoice panel = new PanelSearchInvoice();
							panel.loadDate(PaneSetDate.DateInvoiceDMH);
							panel.setPermissionEmployee(UIConfigModelManager.getInstance().getPlainText(btn.getText()));
							panel.setName("DSDMH");
							DialogResto dialog = new DialogResto(panel, false, 0, 200);
							dialog.setIcon("dsorder1.png");
							dialog.setName("dlDSThuchi");
							dialog.setTitle("Quản lý đơn đặt hàng");
							// dialog.setSize(600, 370);
							dialog.setLocationRelativeTo(null);
							dialog.setVisible(true);
							if (panel.isSave()) {
								panel.createTableOrder(AccountingModelManager.typeDatHang);
							}
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
			add(label6);
			UIConfigModelManager.getInstance()
			    .setPermission(UIConfigModelManager.getInstance().getPlainText(label6.getText()));

			// PHIẾU TRẢ HÀNG
			JButton label7 = CreateButton("<html>Phiếu<br>trả hàng</br></html>");
			label7.setName("PhieuTraHang");
			label7.setActionCommand("3");
			label7.setFocusPainted(false);
			PanelPhieuDatMuaHang.permission3 = (UIConfigModelManager.getInstance().getPlainText(label7.getText()));
			label7.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance()
			          .getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Warehouse.name())) {
							profile = AccountModelManager.getInstance().getProfileConfigAdmin();
							if (profile.get(DialogConfig.GDPos).toString().equals("true")
									&&profile.get(DialogConfig.Keyboard) != null && profile.get(DialogConfig.Keyboard).toString().equals("true")) {
								DialogPhieuDatMuaHangPos panel = DialogPhieuDatMuaHangPos.getInstance();
								if (DialogScreenOftenPos.isFlagScreenOften()) {
									DialogScreenOftenPos.setFlagScreenOften(false);
									DialogScreenOftenPos pos = DialogScreenOftenPos.getInstance();
									panel.getPanelTop2().add(pos.getPanelProductTagRoot(), 1);
									panel.getScrollPane_Product().setViewportView(pos.getPanelProducts());
								}
								if (!panel.getNameType().equals("Phiếu trả hàng")) {
									InvoiceDetail detail = new InvoiceDetail();
									detail.setType(AccountingModelManager.typeTraHang);
									detail.setActivityType(ActivityType.Receipt);
									panel.setTypeInvoice(detail, 0, "Phiếu trả hàng");
								}
								panel.setVisible(true);
							} else {
								PanelPhieuDatMuaHang panel = new PanelPhieuDatMuaHang(ActivityType.Receipt,
			              AccountingModelManager.typeTraHang, "Phiếu trả hàng");
								panel.setName("PhieuTraHang");
								try {
									if (profile.get(DialogConfig.Keyboard) != null
			                && profile.get(DialogConfig.Keyboard).toString().equals("true")) {
										DialogResto dialog = new DialogResto(panel, true, 0, 0);
										dialog.setTitle("Phiếu trả hàng");
										dialog.setIcon("tra1.png");
										dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
										dialog.dispose();
										dialog.setUndecorated(true);
										dialog.setLocationRelativeTo(null);
										dialog.setBtnSave(false);
										dialog.setBtnExit(false);
										dialog.setModal(true);
										dialog.setVisible(true);
									} else {
										DialogContain dialog1 = new DialogContain(panel);
										dialog1.getContentPane().add(panel);
										dialog1.setName("dlPhieuDatMuaHang");
										dialog1.setSize(Toolkit.getDefaultToolkit().getScreenSize());
										dialog1.dispose();
										dialog1.setUndecorated(true);
										dialog1.setLocationRelativeTo(null);
										dialog1.setModal(true);
										dialog1.setVisible(true);
									}
								} catch (Exception e1) {
								}
							}
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
			label7.setIcon(new ImageIcon(getClass().getResource("icon/tra1.png")));
			label7.addMouseListener(new MouseEventClickButton("tra1.png", "tra2.png", "tra3.png"));
			add(label7);
			UIConfigModelManager.getInstance()
			    .setPermission(UIConfigModelManager.getInstance().getPlainText(label7.getText()));

			// KHÁCH TRẢ HÀNG
			JButton label8 = CreateButton("<html>Khách<br>trả hàng</br></html>");
			label8.setName("PhieuKhachTraHang");
			label1.setActionCommand("3");
			label8.setFocusPainted(false);
			PanelPhieuDatMuaHang.permission4 = (UIConfigModelManager.getInstance().getPlainText(label8.getText()));
			label8.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance()
			          .getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Warehouse.name())) {
							profile = AccountModelManager.getInstance().getProfileConfigAdmin();
							if (profile.get(DialogConfig.GDPos).toString().equals("true")
									&&profile.get(DialogConfig.Keyboard) != null && profile.get(DialogConfig.Keyboard).toString().equals("true")) {
								DialogPhieuDatMuaHangPos panel = DialogPhieuDatMuaHangPos.getInstance();
								if (DialogScreenOftenPos.isFlagScreenOften()) {
									DialogScreenOftenPos.setFlagScreenOften(false);
									DialogScreenOftenPos pos = DialogScreenOftenPos.getInstance();
									panel.getPanelTop2().add(pos.getPanelProductTagRoot(), 1);
									panel.getScrollPane_Product().setViewportView(pos.getPanelProducts());
								}
								if (!panel.getNameType().equals("Khách trả hàng")) {
									InvoiceDetail detail = new InvoiceDetail();
									detail.setType(AccountingModelManager.typeTraHang);
									detail.setActivityType(ActivityType.Payment);
									panel.setTypeInvoice(detail, 0, "Khách trả hàng");
								}
								panel.setVisible(true);
							} else {
								PanelPhieuDatMuaHang panel = new PanelPhieuDatMuaHang(ActivityType.Payment,
			              AccountingModelManager.typeTraHang, "Khách trả hàng");
								panel.setName("PhieuKhachTraHang");
								try {
									profile = AccountModelManager.getInstance().getProfileConfigAdmin();
									if (profile.get(DialogConfig.Keyboard) != null
			                && profile.get(DialogConfig.Keyboard).toString().equals("true")) {
										DialogResto dialog = new DialogResto(panel, true, 0, 0);
										dialog.setTitle("Khách trả hàng");
										dialog.setIcon("tra1.png");
										dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
										dialog.dispose();
										dialog.setUndecorated(true);
										dialog.setLocationRelativeTo(null);
										dialog.setBtnSave(false);
										dialog.setBtnExit(false);
										dialog.setModal(true);
										dialog.setVisible(true);
									} else {
										DialogContain dialog1 = new DialogContain(panel);
										dialog1.getContentPane().add(panel);
										dialog1.setName("dlPhieuDatMuaHang");
										dialog1.setSize(Toolkit.getDefaultToolkit().getScreenSize());
										dialog1.dispose();
										dialog1.setUndecorated(true);
										dialog1.setLocationRelativeTo(null);
										dialog1.setModal(true);
										dialog1.setVisible(true);
									}

								} catch (Exception e1) {
								}
							}
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
			label8.setIcon(new ImageIcon(getClass().getResource("icon/tra1.png")));
			label8.addMouseListener(new MouseEventClickButton("tra1.png", "tra2.png", "tra3.png"));
			add(label8);
			UIConfigModelManager.getInstance()
			    .setPermission(UIConfigModelManager.getInstance().getPlainText(label8.getText()));

			// QUẢN LÝ TRẢ HÀNG
			JButton label9 = CreateButton("<html>Quản lý<br>trả hàng</br></html>");
			label9.setFocusPainted(false);
			label9.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					try {
						JButton btn = (JButton) evt.getSource();
						if (UIConfigModelManager.getInstance()
			          .getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Warehouse.name())) {
							PanelSearchInvoice panel = new PanelSearchInvoice();
							panel.loadDate(PaneSetDate.DateInvoiceDMH);
							panel.setPermissionEmployee(UIConfigModelManager.getInstance().getPlainText(btn.getText()));
							panel.setName("DSDMH");
							DialogResto dialog = new DialogResto(panel, false, 0, 200);
							dialog.setIcon("dsorder1.png");
							dialog.setName("dlDSThuchi");
							dialog.setTitle("Quản lý trả hàng");
							// dialog.setSize(600, 370);
							dialog.setLocationRelativeTo(null);
							dialog.setVisible(true);
							if (panel.isSave()) {
								panel.createTableOrder(AccountingModelManager.typeTraHang);
							}
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
			label9.setIcon(new ImageIcon(getClass().getResource("icon/dsorder1.png")));
			label9.addMouseListener(new MouseEventClickButton("dsorder1.png", "dsorder2.png", "dsorder3.png"));
			add(label9);
			UIConfigModelManager.getInstance()
			    .setPermission(UIConfigModelManager.getInstance().getPlainText(label9.getText()));
		}
	}

	public static class MenuRightUnder extends JPanel {
		public MenuRightUnder() {
			setLayout(new GridLayout(3, 3));
			setBackground(Color.WHITE);
			setBorder(BorderFactory.createTitledBorder(null, "Báo cáo thống kê",
			    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
			    new java.awt.Font("Tahoma", 1, 16), new java.awt.Color(204, 0, 0)));

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
					if (UIConfigModelManager.getInstance()
			        .getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
						DialogJurisdiction.getInstace().setVisible(true);
						return;
					}

					DialogReport dialog = new DialogReport(
			        new ReportStatistics(new JTable(), ReportStatistics.TypeReport.TKThuChi));
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
			UIConfigModelManager.getInstance()
			    .setPermission(UIConfigModelManager.getInstance().getPlainText(label1.getText()));

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
						if (UIConfigModelManager.getInstance()
			          .getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Report.name())) {
							DialogReport dialog = new DialogReport(
			            new ReportStatistics(new JTable(), ReportStatistics.TypeReport.TKBanHang));
							dialog.dispose();
							dialog.setUndecorated(true);
							dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
							dialog.setModal(true);
							dialog.setIcon("tknhansu1.png");
							dialog.setLocationRelativeTo(null);
							dialog.setTitle("Thống kê bán hàng");
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
			label2.addMouseListener(new MouseEventClickButton("tknhansu1.png", "tknhansu2.png", "tknhansu3.png"));
			add(label2);
			UIConfigModelManager.getInstance()
			    .setPermission(UIConfigModelManager.getInstance().getPlainText(label2.getText()));

			/**
			 * THỐNG KÊ MUA HÀNG
			 */

			JButton label3 = CreateButton("<html>Thống kê <Br>mua hàng</Br></html>");
			label3.setFocusPainted(false);
			label3.setIcon(new ImageIcon(getClass().getResource("icon/tksanpham1.png")));
			label3.addMouseListener(new MouseEventClickButton("tksanpham1.png", "tksanpham2.png", "tksanpham3.png"));
			add(label3);
			UIConfigModelManager.getInstance()
			    .setPermission(UIConfigModelManager.getInstance().getPlainText(label3.getText()));
			label3.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance()
			          .getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Report.name())) {
							DialogReport dialog = new DialogReport(
			            new ReportStatistics(new JTable(), ReportStatistics.TypeReport.TKMuaHang));
							dialog.dispose();
							dialog.setUndecorated(true);
							dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
							dialog.setModal(true);
							dialog.setIcon("tksanpham1.png");
							dialog.setLocationRelativeTo(null);
							dialog.setTitle("Thống kê mua hàng");
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
						if (UIConfigModelManager.getInstance()
			          .getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Report.name())) {
							DialogReport dialog = new DialogReport(
			            new ReportStatistics(new JTable(), ReportStatistics.TypeReport.TKThoiGian));
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
			UIConfigModelManager.getInstance()
			    .setPermission(UIConfigModelManager.getInstance().getPlainText(label4.getText()));

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
			UIConfigModelManager.getInstance()
			    .setPermission(UIConfigModelManager.getInstance().getPlainText(label5.getText()));
			label5.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance()
			          .getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Report.name())) {
							DialogReport dialog = new DialogReport(
			            new ReportStatistics(new JTable(), ReportStatistics.TypeReport.TKTonKho));
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
			UIConfigModelManager.getInstance()
			    .setPermission(UIConfigModelManager.getInstance().getPlainText(label6.getText()));
			label6.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance()
			          .getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Report.name())) {
							DialogReport dialog = new DialogReport(
			            new ReportStatistics(new JTable(), ReportStatistics.TypeReport.TKDinhLuong));
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
			 * THỐNG KÊ THEO NHÂN VIÊN DỰ ÁN
			 */
			JButton label7 = CreateButton("<html>Thống kê<br>dự án</br></html>");
			label7.setFocusPainted(false);
			label7.setIcon(new ImageIcon(getClass().getResource("icon/tkbophan1.png")));
			label7.addMouseListener(new MouseEventClickButton("tkbophan1.png", "tkbophan2.png", "tkbophan3.png"));
			add(label7);
			UIConfigModelManager.getInstance()
			    .setPermission(UIConfigModelManager.getInstance().getPlainText(label7.getText()));
			label7.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance()
			          .getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						if (LicenseManager.getInstance().getLicense(LicenseManager.module.Report.name())) {
							DialogReport dialog = new DialogReport(
			            new ReportStatistics(new JTable(), ReportStatistics.TypeReport.TKNhanVien));
							dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
							dialog.setModal(true);
							dialog.setIcon("tkbophan1.png");
							dialog.setLocationRelativeTo(null);
							dialog.setTitle("Thống kê nhân viên dự án");
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

//			JButton label8 = CreateButton("<html></html>");
			// label7.setIcon(new
			// ImageIcon(getClass().getResource("icon/tkbophan1.png")));
			// label7.addMouseListener(new MouseEventClickButton("tkbophan1.png",
			// "tkbophan2.png", "tkbophan3.png"));
//			add(label8);
//			label8.addActionListener(new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
					// PanelSalePOS panel = PanelSalePOS.getInstance();
			    // DialogContain dialog1 = new DialogContain(panel);
			    // dialog1.getContentPane().add(panel);
			    // dialog1.setName("dlPhieuDatMuaHang");
			    // dialog1.setSize(Toolkit.getDefaultToolkit().getScreenSize());
			    // dialog1.dispose();
			    // dialog1.setUndecorated(true);
			    // dialog1.setLocationRelativeTo(null);
			    // dialog1.setModal(true);
			    // dialog1.setVisible(true);

//				}
//			});
			
			/**
			 * NGÂN HÀNG
			 */
			JButton label8 = CreateButton("<html>Ngân hàng</html>");
			label8.setActionCommand("3");
			label8.setFocusPainted(false);
			label8.setName("NganHang");
			PanelBank.permission = (UIConfigModelManager.getInstance().getPlainText(label8.getText()));
			label8.setIcon(new ImageIcon(getClass().getResource("icon/bank1.png")));
			label8.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						PanelBank panelBank = new PanelBank();
						panelBank.setName("NganHang");
						DialogMain dialog = new DialogMain(panelBank);
						dialog.setIcon("bank1.png");
						dialog.setTitle("Ngân Hàng");
						dialog.setName("dlNganHang");
						dialog.setVisible(true);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			label8.addMouseListener(new MouseEventClickButton("bank1.png", "bank2.png", "bank3.png"));
			add(label8);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label8.getText()));
			
			
			JButton label9 = CreateButton("<html>Danh sách<br>biên lai</br></html>");
			label9.setIcon(new ImageIcon(getClass().getResource("icon/invoiceds1.png")));
			label9.addMouseListener(new MouseEventClickButton("invoiceds1.png", "invoiceds2.png", "invoiceds3.png"));
			add(label9);
			UIConfigModelManager.getInstance()
			    .setPermission(UIConfigModelManager.getInstance().getPlainText(label9.getText()));
			label9.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JButton btn = (JButton) e.getSource();
					if (UIConfigModelManager.getInstance()
			        .getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
						DialogJurisdiction.getInstace().setVisible(true);
						return;
					}
					PanelSearchInvoice panel = new PanelSearchInvoice();
					panel.loadDate(PaneSetDate.DateInvoiceCN);
					DialogResto dialog = new DialogResto(panel, false, 0, 200);
					dialog.setIcon("invoiceds1.png");
					dialog.setName("dlPanelSearchInvoice");
					dialog.setLocationRelativeTo(null);
					dialog.setTitle("Danh sách biên lai");
					dialog.setVisible(true);
					if (panel.isSave()) {
						panel.createTableTransaction();
						;
					}

				}
			});
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
		label.setIconTextGap(10);
		Border border = new BorderUIResource.CompoundBorderUIResource(
		    new BasicBorders.ButtonBorder(Color.white, Color.white, Color.white, Color.white),
		    new BasicBorders.MarginBorder());
		label.setBorder(border);
		return label;
	}
}
