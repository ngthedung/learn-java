package com.hkt.client.swingexp.homescreen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.basic.BasicBorders;

import com.hkt.client.swing.widget.GridBagLayoutPanel;
import com.hkt.client.swingexp.app.banhang.PanelSearchInvoice;
import com.hkt.client.swingexp.app.banhang.list.PaneGroupProduct;
import com.hkt.client.swingexp.app.banhang.screen.market.DialogSuperMarket;
import com.hkt.client.swingexp.app.banhang.screen.often.DialogConfig;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelRemoteControl;
import com.hkt.client.swingexp.app.banhang.screen.often.ScreenOften;
import com.hkt.client.swingexp.app.banhang.screen.often.TableEat;
import com.hkt.client.swingexp.app.banhang.screen.often.TableListChoosetable;
import com.hkt.client.swingexp.app.banhang.screen.often.TableListDeleteInvoice;
import com.hkt.client.swingexp.app.banhang.screen.often.UserInformation;
import com.hkt.client.swingexp.app.banhang.screen.pos.DialogSuperMarketPos;
import com.hkt.client.swingexp.app.banhang.screen.pos.PanelScreenSaleLocationPos;
import com.hkt.client.swingexp.app.banhang.screen.pos.PanelScreenSaleLocationPos.ComponentTable;
import com.hkt.client.swingexp.app.banhang.screen.pos.DialogScreenOftenPos;
import com.hkt.client.swingexp.app.core.DialogJurisdiction;
import com.hkt.client.swingexp.app.core.DialogList;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.HKTFile;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDialogMenuTop;
import com.hkt.client.swingexp.app.core.MouseEventMenuSales;
import com.hkt.client.swingexp.app.core.MyFrame;
import com.hkt.client.swingexp.app.hethong.AutoRunSendMail;
import com.hkt.client.swingexp.app.hethong.DialogInfo;
import com.hkt.client.swingexp.app.hethong.PaneSetDate;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.PanelOKRestore;
import com.hkt.client.swingexp.app.khohang.list.TableListRecipe;
import com.hkt.client.swingexp.app.khuvucbanhang.PanelManageCodes;
import com.hkt.client.swingexp.app.license.LicenseManager;
import com.hkt.client.swingexp.app.main.Main;
import com.hkt.client.swingexp.app.nhansu.PanelPermission;
import com.hkt.client.swingexp.app.nhansu.dailywork.CalendarAppointment;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.GenericOptionModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.accounting.entity.ManageCodes;
import com.hkt.module.config.generic.Option;
import com.hkt.module.config.generic.OptionGroup;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.hr.entity.Appointment;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductAttribute;
import com.hkt.module.product.entity.ProductTag;
import com.hkt.module.promotion.AutoRunBackup;
import com.hkt.module.promotion.CheckOS;
import com.hkt.module.restaurant.entity.Project;
import com.hkt.module.restaurant.entity.Table;

@SuppressWarnings("serial")
public class HomeScreen extends GridBagLayoutPanel {
	private MenuTop										menuTop;
	private MenuLeft									menuLeft;
	private MenuRightBanHang					menuRightBanHang;
	private MenuRightThuChiMuaHang		menuRightThuChiMuaHang;
	private MenuRightKhuyenMai				menuRightKhuyenMai;
	private MenuRightKhoHangHoa				menuRightKhoHangHoa;
	private MenuRightKhachHangDoiTac	menuRightKhachHangDoiTac;
	private MenuRightNhanSu						menuRightNhanSu;
	private JScrollPane								scrollPane;
	private MenuRightKhuVucBanHang		menuRightKhuVucBanHang;
	private MenuRightHeThong					menuRightHeThong;

	public HomeScreen() {
		createBorder();
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		menuTop = new MenuTop();
		menuLeft = new MenuLeft();
		menuRightBanHang = new MenuRightBanHang();
		menuRightKhuyenMai = new MenuRightKhuyenMai();
		menuRightThuChiMuaHang = new MenuRightThuChiMuaHang();
		menuRightKhoHangHoa = new MenuRightKhoHangHoa();
		menuRightKhachHangDoiTac = new MenuRightKhachHangDoiTac();
		menuRightNhanSu = new MenuRightNhanSu();
		menuRightKhuVucBanHang = new MenuRightKhuVucBanHang();
		menuRightHeThong = new MenuRightHeThong();
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(menuRightBanHang);
		add(menuTop, BorderLayout.PAGE_START);
		add(menuLeft, BorderLayout.WEST);
		add(scrollPane, BorderLayout.CENTER);

		AutoRunSendMail sen = new AutoRunSendMail();
		sen.startTimer();

		UIManager.put("FormattedTextField.inactiveBackground", Color.WHITE);
		UIManager.put("FormattedTextField.inactiveForeground", new Color(51, 51, 51));
		UIManager.put("TextComponent.selectionBackgroundInactive", Color.WHITE);
		UIManager.put("TextField.inactiveBackground", Color.WHITE);
		UIManager.put("TextField.inactiveForeground", new Color(51, 51, 51));
		UIManager.put("TextArea.inactiveBackground", Color.WHITE);
		UIManager.put("TextArea.inactiveForeground", new Color(51, 51, 51));
		UIManager.put("ComboBox.background", Color.WHITE);
		UIManager.put("ComboBox.disabledBackground", Color.WHITE);
		UIManager.put("ComboBox.disabledForeground", new Color(51, 51, 51));
		UIManager.put("CheckBox.foreground", new Color(51, 51, 51));
		UIManager.put("CheckBox.disabledText", new Color(51, 51, 51));

		loadManagerCode(PanelManageCodes.InvoiceBH);
		loadManagerCode(PanelManageCodes.DuAn);
		loadManagerCode(PanelManageCodes.InvoiceDMH);
		loadManagerCode(PanelManageCodes.InvoiceTC);
		loadManagerCode(PanelManageCodes.KhachHang);
		loadManagerCode(PanelManageCodes.SanPham);
		Profile profile = AccountModelManager.getInstance().getProfileConfigAdmin();
		if (profile.get("nameTable") != null) {
			DialogConfig.Table = profile.get("nameTable").toString();
		}
		if (profile.get("Project") != null && profile.get("Project").toString().equals("true")) {
			Project.nameProject = false;

		}
		try {
			Runtime.getRuntime().exec("taskkill /F /IM hktsoft.exe *32");
			Runtime.getRuntime().exec("taskkill /F /IM hktserver.exe *32");
			Runtime.getRuntime().exec("taskkill /F /IM hktsoft.exe");
			Runtime.getRuntime().exec("taskkill /F /IM hktserver.exe");
			Runtime.getRuntime().exec("taskkill /F /IM HKTSoft.exe");
			Runtime.getRuntime().exec("taskkill /F /IM HKTServer.exe");
		} catch (Exception e) {
		}
		addKeyBinding(this);
		if (profile.get("BarcodePrice1") == null) {
			AccountingModelManager.getInstance().updatePriceProduct();
			profile.put("BarcodePrice1", "true");
			AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(),
			    profile);

		}
		if (profile.get("BarcodePrice222") == null) {
			AccountingModelManager.getInstance().updateTypeTransaction();
			profile.put("BarcodePrice222", "true");
			AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(),
			    profile);

		}
		RestaurantModelManager.getInstance();
	}

	private void addKeyBinding(JComponent jc) {
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0, false), "F10");
		jc.getActionMap().put("F10", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (PanelPermission.getInstance().onlineSetup()) {
						PanelRemoteControl panel = new PanelRemoteControl();
						DialogMain dialog = new DialogMain(panel, 500, 0);
						dialog.setTitle("Remote Control");
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);
					}
				} catch (Exception e2) {
				}

			}
		});
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0, false), "F1");
		jc.getActionMap().put("F1", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					TableListDeleteInvoice tbrepice = new TableListDeleteInvoice();
					tbrepice.setName("tbrecipe");
					DialogList dialog = new DialogList(tbrepice);
					dialog.setName("dlListRecipe");
					dialog.setIcon("tkquanti1.png");
					dialog.setTitle("Danh sách hóa đơn bị xóa");
					dialog.setVisible(true);
				} catch (Exception e2) {
				}

			}
		});

	}

	private boolean loadManagerCode(String code) {
		try {
			ManageCodes codes = AccountingModelManager.getInstance().getCodesByCode(code);
			if (codes != null) {
				if (codes.getManageType().equals(ManageCodes.ManageType.Circle)) {
					DateFormat df = new SimpleDateFormat("yyyyMMdd");
					DateFormat df1 = new SimpleDateFormat("yyyyMM");
					DateFormat df2 = new SimpleDateFormat("yyyy");
					if (codes.getRotationType().equals(ManageCodes.RotationType.ByDay)) {
						if (!df.format(codes.getModifiedTime()).equals(df.format(new Date()))) {
							codes.setModifiedTime(new Date());
							codes.setPriority(1);
							AccountingModelManager.getInstance().saveCodes(codes);
						}
					} else {
						if (codes.getRotationType().equals(ManageCodes.RotationType.ByYear)) {
							if (!df2.format(codes.getModifiedTime()).equals(df2.format(new Date()))) {
								codes.setModifiedTime(new Date());
								codes.setPriority(1);
								AccountingModelManager.getInstance().saveCodes(codes);
							}
						} else {
							if (codes.getRotationType().equals(ManageCodes.RotationType.ByMonth)) {
								if (!df1.format(codes.getModifiedTime()).equals(df1.format(new Date()))) {
									codes.setModifiedTime(new Date());
									codes.setPriority(1);
									AccountingModelManager.getInstance().saveCodes(codes);
								}
							}
						}
					}
				}
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public class MenuLeft extends JPanel {
		public MenuLeft() {
			setLayout(new BorderLayout());
			setBackground(Color.WHITE);
			MenuLeftTop leftTop = new MenuLeftTop();
			MenuLeftUnder leftUnder = new MenuLeftUnder();
			leftTop.setPreferredSize(new Dimension(280, 320));
			add(leftTop, BorderLayout.PAGE_START);
			add(leftUnder, BorderLayout.CENTER);
		}

		public class MenuLeftTop extends GridBagLayoutPanel {

			public MenuLeftTop() {
				setName("MenuLeftTop");
				createBorder();
				setInsets(new Insets(0, 0, 0, 0));
				setBackground(Color.WHITE);
				JButton lbBanHang = CreateButton("Bán hàng                   ");
				lbBanHang.setName("menuRightBanHang");
				lbBanHang.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						scrollPane.setViewportView(menuRightBanHang);
					}
				});
				add(0, 0, lbBanHang);

				JButton lbBanNhanh = CreateButton("Thu chi & Mua hàng   ");
				lbBanNhanh.setName("menuRightThuChiMuaHang");
				lbBanNhanh.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						scrollPane.setViewportView(menuRightThuChiMuaHang);
					}
				});
				add(1, 0, lbBanNhanh);

				JButton lbQuanLyDatBan = CreateButton("Khuyến mãi               ");
				lbQuanLyDatBan.setName("menuRightKhuyenMai");
				lbQuanLyDatBan.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						scrollPane.setViewportView(menuRightKhuyenMai);
					}
				});
				add(2, 0, lbQuanLyDatBan);

				JButton lbQLSanPham = CreateButton("Kho & Hàng hóa        ");
				lbQLSanPham.setName("menuRightKhoHangHoa");
				lbQLSanPham.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						scrollPane.setViewportView(menuRightKhoHangHoa);
					}
				});
				add(3, 0, lbQLSanPham);

				JButton lbLichHen = CreateButton("Khách hàng & Đối tác");
				lbLichHen.setName("menuRightKhachHangDoiTac");
				lbLichHen.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						scrollPane.setViewportView(menuRightKhachHangDoiTac);
					}
				});
				add(4, 0, lbLichHen);

				JButton lbThongTinUser = CreateButton("Nhân sự & Quyền       ");
				lbThongTinUser.setName("menuRightNhanSu");
				lbThongTinUser.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						scrollPane.setViewportView(menuRightNhanSu);
					}
				});
				add(5, 0, lbThongTinUser);

				JButton lbPhongTo = CreateButton("Khu vực bán hàng      ");
				lbPhongTo.setName("menuRightKhuVucBanHang");
				lbPhongTo.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						scrollPane.setViewportView(menuRightKhuVucBanHang);
					}
				});
				add(6, 0, lbPhongTo);

				JButton lbKhoiDongLai = CreateButton("Hệ thống                     ");
				lbKhoiDongLai.setName("menuRightHeThong");
				lbKhoiDongLai.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						scrollPane.setViewportView(menuRightHeThong);
					}
				});
				add(7, 0, lbKhoiDongLai);
			}
		}

		public JButton CreateButton(String text) {
			JButton label = new JButton();
			label.setFont(new Font("Tahoma", Font.BOLD, 16));
			label.setText(text);
			label.setIcon(new ImageIcon(getClass().getResource("icon/home/menu1.png")));
			label.setContentAreaFilled(false);
			label.setPreferredSize(new Dimension(150, 40));
			label.setForeground(Color.black);
			label.setSize(new Dimension(150, 40));
			label.setMinimumSize(new Dimension(150, 40));
			label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			label.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			label.setOpaque(false);
			label.setMargin(new java.awt.Insets(0, 0, 0, 0));
			label.setIconTextGap(0);
			Border border = new BorderUIResource.CompoundBorderUIResource(
			    new BasicBorders.ButtonBorder(Color.white, Color.white, Color.white, Color.white),
			    new BasicBorders.MarginBorder());
			label.setBorder(border);
			MouseEventMenuSales eventMenuSalesBanHang = new MouseEventMenuSales("menu1.png", "menu2.png", "menu3.png");
			label.addMouseListener(eventMenuSalesBanHang);
			return label;
		}

		class MenuLeftUnder extends JPanel {
			public MenuLeftUnder() {
				setLayout(new GridLayout());
				setBackground(Color.WHITE);
				JScrollPane scr = new JScrollPane();
				scr.setViewportView(new PanelMenu());
				add(scr);
			}
		}
	}

	class MenuTop extends JPanel {
		private JLabel	dem;
		private int			i;

		public MenuTop() {
			setLayout(new GridLayout(1, 9));
			setBackground(Color.WHITE);
			JButton lbBanHang = CreateLabel("<html>Bán<br>hàng</br></html>");
			lbBanHang.setIcon(new ImageIcon(getClass().getResource("icon/home/sale1.png")));
			lbBanHang.setFocusPainted(false);
			lbBanHang.addMouseListener(new MouseEventClickButtonDialogMenuTop("sale1.png", "sale1.png", "sale1.png"));
			lbBanHang.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JButton btn = (JButton) e.getSource();
					if (UIConfigModelManager.getInstance()
			        .getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
						DialogJurisdiction.getInstace().setVisible(true);
						return;
					}
					try {
						Profile profile = AccountModelManager.getInstance().getProfileConfigAdmin();
						if (profile.get(DialogConfig.Maket).toString().equals("true")
			          || profile.get(DialogConfig.Shop).toString().equals("true")) {
							DialogSuperMarket dialogSuperMarket = DialogSuperMarket.getInstance();
							dialogSuperMarket.loadData();
							dialogSuperMarket.setVisible(true);
						} else {
							ScreenOften dialog = ScreenOften.getInstance();
							dialog.loadData();
							dialog.setVisible(true);
						}
					} catch (Exception e2) {
						ScreenOften dialog = ScreenOften.getInstance();
						dialog.loadData();
						dialog.setVisible(true);
					}

				}
			});
			add(lbBanHang);
			UIConfigModelManager.getInstance()
			    .setPermission(UIConfigModelManager.getInstance().getPlainText(lbBanHang.getText()));

			JButton lbBanNhanh = CreateLabel("<html>Bán<br>nhanh</br></html>");
			lbBanNhanh.setIcon(new ImageIcon(getClass().getResource("icon/home/mangve1.png")));
			lbBanNhanh.setFocusPainted(false);
			lbBanNhanh.addMouseListener(new MouseEventClickButtonDialogMenuTop("mangve1.png", "mangve1.png", "mangve1.png"));
			lbBanNhanh.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						Profile profile = AccountModelManager.getInstance().getProfileConfigAdmin();
						if (profile.get(DialogConfig.Maket).toString().equals("true")
			          || profile.get(DialogConfig.Shop).toString().equals("true")) {
							if (profile.get(DialogConfig.Maket).toString().equals("true")) {
								if (profile.get(DialogConfig.GDPos).toString().equals("true")) {
									DialogSuperMarketPos marketPos = DialogSuperMarketPos.getInstance();
									marketPos.loadData();
									marketPos.setVisible(true);
								} else {
									DialogSuperMarket dialogSuperMarket = DialogSuperMarket.getInstance();
									dialogSuperMarket.loadData();
									dialogSuperMarket.setVisible(true);
								}
							} else {
								DialogScreenOftenPos dialog = DialogScreenOftenPos.getInstance();
								dialog.setTable(PanelScreenSaleLocationPos.getInstance().getComponentTableOther());
								if (!DialogScreenOftenPos.isFlagScreenOften()) {
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
								DialogScreenOftenPos dialog = DialogScreenOftenPos.getInstance();
								dialog.setTable(PanelScreenSaleLocationPos.getInstance().getComponentTableOther());
								if (!DialogScreenOftenPos.isFlagScreenOften()) {
									DialogScreenOftenPos.setFlagScreenOften(true);
									dialog.getPanelTop2().add(dialog.getPanelProductTagRoot(), 1);
									dialog.getScrollPane_Product().setViewportView(dialog.getPanelProducts());
								}
								if (dialog.hashMapProducts != null) {
									dialog.resetGui();
									dialog.loadData();
								}
								dialog.setVisible(true);
							} else {
								ScreenOften dialog = ScreenOften.getInstance();
								dialog.loadData();
								TableEat tableEat = new TableEat(RestaurantModelManager.getInstance().getTablePaymentAfter());
								dialog.setTable(tableEat);
								dialog.setEditorGuiBN(false);
								dialog.setVisible(true);
							}
						}
					} catch (Exception e2) {
						e2.printStackTrace();
						ScreenOften dialog = ScreenOften.getInstance();
						dialog.loadData();
						TableEat tableEat = new TableEat(RestaurantModelManager.getInstance().getTablePaymentAfter());
						dialog.setTable(tableEat);
						dialog.setEditorGuiBN(false);
						dialog.setVisible(true);
					}
				}
			});
			add(lbBanNhanh);

			JButton lbQuanLyDatBan = CreateLabel("<html>Quản lý<br>đặt bàn</br></html>");
			lbQuanLyDatBan.setIcon(new ImageIcon(getClass().getResource("icon/home/datban1.png")));
			lbQuanLyDatBan.setFocusPainted(false);
			lbQuanLyDatBan
			    .addMouseListener(new MouseEventClickButtonDialogMenuTop("datban1.png", "datban1.png", "datban1.png"));
			lbQuanLyDatBan.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					JButton btn = (JButton) evt.getSource();
					// if (UIConfigModelManager.getInstance().getPermission(
			    // UIConfigModelManager.getInstance().getPlainText(btn.getText())) ==
			    // null) {
			    // DialogJurisdiction.getInstace().setVisible(true);
			    // return;
			    // }
					final TableListChoosetable tbChoosetable = new TableListChoosetable();
					tbChoosetable.setName("pnQLDatbanQuay");
					tbChoosetable.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if (e.getClickCount() > 1)
								tbChoosetable.click();
						}
					});
					DialogList dialog = new DialogList(tbChoosetable);
					dialog.setIcon("home/datban1.png");
					dialog.setName("dlQLDatbanQuay");
					dialog.setTitle("Quản lý đặt bàn/quầy");
					dialog.setModal(true);
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
				}
			});
			add(lbQuanLyDatBan);
			UIConfigModelManager.getInstance()
			    .setPermission(UIConfigModelManager.getInstance().getPlainText(lbQuanLyDatBan.getText()));

			JButton lbQuanLyHoaDon = CreateLabel("<html>Quản lý<br>hóa đơn</br></html>");
			lbQuanLyHoaDon.setIcon(new ImageIcon(getClass().getResource("icon/home/loan1.png")));
			lbQuanLyHoaDon.setFocusPainted(false);
			lbQuanLyHoaDon.addMouseListener(new MouseEventClickButtonDialogMenuTop("loan1.png", "loan1.png", "loan1.png"));
			lbQuanLyHoaDon.addActionListener(new ActionListener() {
				@Override
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
							panel.setName("PanelSearchInvoice");
							panel.loadDate(PaneSetDate.DateInvoiceBH);
							panel.setPermissionEmployee(UIConfigModelManager.getInstance().getPlainText(btn.getText()));
							DialogResto dialog = new DialogResto(panel, false, 0, 200);
							dialog.setName("dlPanelSearchInvoice");
							dialog.setTitle("Quản lý hóa đơn");
							dialog.setIcon("home/loan1.png");
							dialog.setLocationRelativeTo(null);
							dialog.setVisible(true);
							if (panel.isSave()) {
								panel.createTablePayment();
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
			add(lbQuanLyHoaDon);
			UIConfigModelManager.getInstance()
			    .setPermission(UIConfigModelManager.getInstance().getPlainText(lbQuanLyHoaDon.getText()));

			JButton lbQLSanPham = CreateLabel("<html>Tắt Server</br></html>");
			lbQLSanPham.setIcon(new ImageIcon(getClass().getResource("icon/home/product_defaut.png")));
			lbQLSanPham.setFocusPainted(false);
			lbQLSanPham.addMouseListener(
			    new MouseEventClickButtonDialogMenuTop("product_defaut.png", "product_defaut.png", "product_defaut.png"));
			lbQLSanPham.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					try {
						if (CheckOS.isWindows()) {
							Runtime.getRuntime().exec("taskkill /F /IM HKTServer.exe");
							Runtime.getRuntime().exec("taskkill /F /IM java.exe");
							System.exit(0);
						}
					} catch (Exception e) {
					}
				}
			});

			// UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(lbQLSanPham.getText()));

			JPanel panelLichHen = new JPanel();
			panelLichHen.setOpaque(false);
			panelLichHen.setLayout(new GridLayout(1, 2));
			final JLabel lbLichHen = new JLabel("<html>Lịch<br>hẹn</br></html>");

			lbLichHen.setForeground(Color.BLACK);
			lbLichHen.setFont(new java.awt.Font("Tahoma", 1, 12));
			lbLichHen.setIcon(new ImageIcon(getClass().getResource("icon/home/datban1.png")));
			dem = new JLabel();
			panelLichHen.add(lbLichHen);
			panelLichHen.add(dem);
			load();
			add(panelLichHen);

			panelLichHen.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseEntered(MouseEvent arg0) {
					JPanel btn = (JPanel) arg0.getSource();
					btn.setOpaque(true);
					btn.setBackground(new Color(248, 160, 60));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					JPanel btn = (JPanel) e.getSource();
					btn.setOpaque(true);
					btn.setBackground(new Color(255, 255, 255));
				}

				@Override
				public void mouseClicked(MouseEvent evt) {

					try {
						// if (UIConfigModelManager.getInstance().getPermission(
			      // UIConfigModelManager.getInstance().getPlainText(lbLichHen.getText()))
			      // == null) {
			      // DialogJurisdiction.getInstace().setVisible(true);
			      // return;
			      // }

						UIConfigModelManager.getInstance()
			          .setPermission(UIConfigModelManager.getInstance().getPlainText(lbLichHen.getText()));
						CalendarAppointment calendar = new CalendarAppointment();
						calendar.setTitle("Lịch hẹn");
						calendar.setVisible(true);
						load();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			});

			JButton lbThongTinUser = CreateLabel("<html>Thông<br>tin user</br></html>");
			lbThongTinUser.setIcon(new ImageIcon(getClass().getResource("icon/home/user1.png")));
			lbThongTinUser.setFocusable(false);
			lbThongTinUser.addMouseListener(new MouseEventClickButtonDialogMenuTop("user1.png", "user1.png", "user1.png"));
			add(lbThongTinUser);
			lbThongTinUser.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						UserInformation user = new UserInformation();
						DialogResto dialog = new DialogResto(user, false, 320, 400);
						user.setName("dlUser");
						dialog.setIcon("home/user1.png");
						dialog.setTitle("Thông tin người dùng");
						dialog.setLocationRelativeTo(null);
						dialog.setModal(true);
						dialog.setVisible(true);
					} catch (Exception e2) {
						e2.printStackTrace();
					}

				}
			});
			add(lbQLSanPham);
			JButton lbPhongTo = CreateLabel("<html>Đăng xuất</html>");
			lbPhongTo.setIcon(new ImageIcon(getClass().getResource("icon/home/user1.png")));
			lbPhongTo.setFocusable(false);
			lbPhongTo.addMouseListener(new MouseEventClickButtonDialogMenuTop("user1.png", "user1.png", "user1.png"));
			lbPhongTo.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					MyFrame.getInstance().dispose();
					MyFrame.setMyFrame(null);
					Main.load();
					try {
						ScreenOften.getInstance().getPanelTable().refeshGui();
					} catch (Exception e2) {
					}

				}
			});
			add(lbPhongTo);

			JButton lbKhoiDongLai = CreateLabel("<html>Khởi<br>động lại</br></html>");
			lbKhoiDongLai.setIcon(new ImageIcon(getClass().getResource("icon/home/restart1.png")));
			lbKhoiDongLai.setFocusable(false);
			lbKhoiDongLai
			    .addMouseListener(new MouseEventClickButtonDialogMenuTop("restart1.png", "restart1.png", "restart1.png"));
			add(lbKhoiDongLai);
			lbKhoiDongLai.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					try {

						String str = this.getClass().getResource("").getPath().substring(5);
						File file = new File(str.substring(0, str.indexOf("lib")) + "bin\\HKTSoft.exe");
						String aa = file.getAbsolutePath().replaceAll("%20", " ");
						String[] processCommand = { "cmd", "/c", aa };

						Runtime.getRuntime().exec(processCommand);
						System.exit(0);
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}
			});
		}

		public void load() {
			dem.removeAll();
			i = 0;
			Date date = new Date();
			FilterQuery filterQuery = new FilterQuery();
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(date);
			calendar2.set(Calendar.HOUR_OF_DAY, 23);
			calendar2.set(Calendar.MINUTE, 59);
			filterQuery.add("dateStart", FilterQuery.Operator.LT, calendar2.getTime());
			List<Appointment> appointments;
			try {
				appointments = HRModelManager.getInstance().searchAppointmentById(filterQuery);

				for (int j = 0; j < appointments.size(); j++) {
					Appointment.Status status = appointments.get(j).getStatus();
					if (status == Appointment.Status.UNACCOMPLISHED || status == Appointment.Status.ONGOING) {
						i++;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				appointments = new ArrayList<Appointment>();
			}
			dem.setForeground(Color.red);
			dem.setFont(new java.awt.Font("Tahoma", 1, 16));

			if (i == 0) {
				dem.setText("");
			} else {
				dem.setText("" + i);
			}

			dem.updateUI();
			dem.setOpaque(false);
			dem.setHorizontalAlignment(SwingConstants.CENTER);
		}

		@SuppressWarnings("unused")
		private String readSetup() {
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

		JButton CreateLabel(String text) {
			JButton label = new JButton();
			label.setFont(new Font("Tahoma", Font.BOLD, 12));
			label.setText(text);
			label.setForeground(Color.black);
			label.setContentAreaFilled(false);
			label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			label.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			label.setOpaque(false);
			label.setPreferredSize(new Dimension(82, 65));
			label.setSize(new Dimension(82, 65));
			label.setMinimumSize(new Dimension(82, 65));
			label.setMargin(new Insets(0, 0, 0, 0));
			label.setIconTextGap(4);
			Border border = new BorderUIResource.CompoundBorderUIResource(
			    new BasicBorders.ButtonBorder(Color.white, Color.white, Color.white, Color.white),
			    new BasicBorders.MarginBorder());
			label.setBorder(border);
			return label;
		}
	}

}
