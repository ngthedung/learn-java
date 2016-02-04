package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.LocaleServiceClient;
import com.hkt.client.swing.robot.console.JVMEnv;
import com.hkt.client.swingexp.app.banhang.PanelInstituteLocation;
import com.hkt.client.swingexp.app.banhang.list.PanelPriceLocation;
import com.hkt.client.swingexp.app.banhang.screen.pos.PanelScreenSaleLocationPos;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ImageTool;
import com.hkt.client.swingexp.app.component.PanelBackground;
import com.hkt.client.swingexp.app.component.PanelTrans;
import com.hkt.client.swingexp.app.component.PanelTrans100;
import com.hkt.client.swingexp.app.core.DepartmentJComboBox;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.HKTFile;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDialog;
import com.hkt.client.swingexp.app.core.ProjectJComboBox;
import com.hkt.client.swingexp.app.core.StoreJComboBox;
import com.hkt.client.swingexp.app.hethong.PanelCurrency;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.GuiModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.config.locale.Currency;
import com.hkt.module.product.entity.ProductPriceType;
import com.hkt.module.product.entity.ProductTag;
import com.hkt.module.restaurant.entity.Project;

public class DialogConfig extends javax.swing.JDialog {
	public static final String InHuyMon = "inhuymon";
	public static final String InCheBien = "inchebien";
	public static final String InCheBienSP = "inchebiensp";
	public static final String SoLuong = "soluong";
	public static final String Datgio = "datgio";
	public static final String GioHoaDon = "gioHoaDon";
	public static final String CK = "ck";
	public static final String Thue = "thue";
	public static final String DichVu = "dichvu";
	public static final String TTLe = "ttle";
	public static final String SLBP = "slbp";
	public static final String ThietLapBH = "thietlapbh";
	public static final String TraSau = "trasau";
	public static final String HuySP = "huysp";
	public static final String TaoSP = "taosp";
	public static final String SuaGia = "suagia";
	public static final String XoaSP = "xoasp";
	public static final String CTKM = "ctkm";
	public static final String DoiGia = "doigia";
	public static final String SuaSL = "suasl";
	public static final String MuaHo = "muaho";
	public static final String Paymen = "paymen";
	public static final String PaymenNow = "paymennow";
	public static final String PaymenView = "paymenview";
	public static final String PrintReceip = "printreceip";
	public static final String PrintNow = "printnow";
	public static final String PrintView = "printview";
	public static final String Area = "area";
	public static final String SingleArea = "singlearea";
	public static final String AllArea = "allarea";
	public static final String TbArea = "tbarea";
	public static final String Resto = "resto";
	public static final String Cafe = "cafe";
	public static final String Kara = "kara";
	public static final String Bia = "bia";
	public static final String Maket = "maket";
	public static final String Shop = "shop";
	public static final String Spa = "spa";
	public static final String TapHoa = "taphoa";
	public static final String Quay = "quay";
	public static final String NhieuQuay = "nhieuquay";
	public static final String GDPos = "gdpos";
	public static final String GDThuong = "gdthuong";
	public static final String One = "01";
	public static final String Two = "02";
	public static final String Three = "03";
	public static final String Four = "04";
	public static final String Five = "05";
	public static final String Six = "06";
	public static final String Seven = "07";
	public static final String Eight = "08";
	public static final String Null = "null";
	public static final String Background = "background";
	public static final String ProductPriceType = "productPriceType";
	public static final String ProductTag = "productTag";
	public static final String ImageOption = "ImageOption";
	public static final String Keyboard = "keyboard";
	public static final String Currency = "currency";
	public static final String CurrencyQD = "currencyQD";
	public static String Table = "bàn";
	private boolean print = false;

	private boolean flagChange;
	private List<CheckBoxP> listCheckBoxPs = new ArrayList<CheckBoxP>();
	private ClientContext clientContext = ClientContext.getInstance();
	private LocaleServiceClient clientLocale = clientContext.getBean(LocaleServiceClient.class);
	private List<ProductPriceType> listProductPriceType = new ArrayList<ProductPriceType>();

	@SuppressWarnings("rawtypes")
	private DefaultComboBoxModel listPriceType;
	private PanelInstituteLocation panelInstituteLocation;
	private IDialogResto iDialogResto;
	private boolean full;
	@SuppressWarnings("unused")
	private int sizeHeight = 172;
	@SuppressWarnings("unused")
	private int width = 600;
	private JRadioButton rbt09;
	private String url;
	public static String permission;
	private boolean changeComboBox = false;
	private JCheckBox chbProjectCode;
	private JCheckBox chbProduct;
	private JCheckBox chbUSB;
	private JCheckBox chbExel, chbPdf;
	private JCheckBox chbForRent;
	private JCheckBox chbProductPayment;
	private JCheckBox chbDailyWork;
	private JCheckBox chbExelMail;
	private JTextField txtMail;

	private JButton btnDongY1, btnDongY2, btnDongY3, btnDongY4, btnDongY5, btnPricingTable, btnThoat1, btnThoat2,
	    btnThoat3, btnThoat4, btnThoat5;
	private ButtonGroup buttonGroup3, buttonGroup4, buttonGroup5, buttonGroup6, buttonGroup7, buttonGroup8, buttonGroup9;
	private JCheckBox chbArea, chbCTKM, chbChietKhau, chbDatgio, chbDichVu, chbDoiGia, chbHuymon, chbInCBNSP,
	    chbInCheBien, chbInHuyMon, chbMuaHo, chbPaymen, chbPricingTableArea, chbPrintReceip, chbSLBP, chbSoluong,
	    chbSuaSL, chbSuagia, chbTaoSP, chbThanhToanLe, chbThietLapBan, chbThue, chbTraSau, chbXoamon;
	private JLabel jLabel13, jLabel15, jLabel16, jLabel22, jLabel24, jLabel25, jLabel32, jLabel34, jLabel35, jLabel36,
	    jLabel37, jLabel38;
	private JLabel lb01, lb02, lb03, lb04, lb05, lb06, lb07, lb08, lbGiaoDienSuDung, lbHKT, lbHKT1, lbHKT3, lbHKT5,
	    lbHinhNen, lbLoaiHinhBanHang, lbPhanQuyenKhuVuc, lbThietLapGiaoDien, lbThietLapPhienBan;
	private JPanel jPanel3;
	private JTabbedPane jTabbedPane1;
	private PanelBackground panelBackground1, panelBackground2, panelBackground3, panelBackground4, panelBackground5;

	private PanelTrans PanelTrans2;
	private PanelTrans PanelTrans1;
	private PanelTrans PanelTrans10;
	private PanelTrans PanelTrans3;
	private PanelTrans PanelTrans4;
	private PanelTrans PanelTrans5;
	private PanelTrans PanelTrans6;
	private PanelTrans PanelTrans9;
	private PanelTrans PanelTrans7;
	private PanelTrans PanelTrans8;

	private DepartmentJComboBox cbDepartment;
	private StoreJComboBox cbStore;
	private ProjectJComboBox cbProject;

	private javax.swing.JRadioButton rbt01;
	private javax.swing.JRadioButton rbt02;
	private javax.swing.JRadioButton rbt03;
	private javax.swing.JRadioButton rbt04;
	private javax.swing.JRadioButton rbt05;
	private javax.swing.JRadioButton rbt06;
	private javax.swing.JRadioButton rbt07;
	private javax.swing.JRadioButton rbt08;
	private javax.swing.JRadioButton rbt1quay;
	private javax.swing.JRadioButton rbtAll;

	private javax.swing.JRadioButton rbtBia;
	private javax.swing.JRadioButton rbtCafe;
	private javax.swing.JRadioButton rbtKara;
	private javax.swing.JRadioButton rbtMaket;
	private javax.swing.JRadioButton rbtNull;
	private javax.swing.JRadioButton rbtPaymenNow;
	private javax.swing.JRadioButton rbtPaymenView;
	private javax.swing.JRadioButton rbtPoss;
	private javax.swing.JRadioButton rbtPrintNow;
	private javax.swing.JRadioButton rbtPrintView;
	private javax.swing.JRadioButton rbtResto;

	private javax.swing.JRadioButton rbtShop;
	private javax.swing.JRadioButton rbtSingleton;
	private javax.swing.JRadioButton rbtSpa;
	private javax.swing.JRadioButton rbtTapHoa;
	private javax.swing.JRadioButton rbtThuong;
	private javax.swing.JRadioButton rbtnhieuquay;

	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JRadioButton rdbPrcingTable1;
	private javax.swing.JRadioButton rdbPrcingTable2;
	private javax.swing.JRadioButton rdbPrcingTable3;
	private javax.swing.JLabel lbLoaiBangGia1;
	private javax.swing.JLabel lbLoaiBangGia2;
	private javax.swing.JLabel lbLoaiBangGia3;

	private javax.swing.JCheckBox chbKeyboard;
	private javax.swing.JLabel lbThietLapMuaBanHang;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JRadioButton rbtAllSystem;
	private javax.swing.JRadioButton rbtSalse;
	private javax.swing.JLabel lbNhomSanPham;
	private javax.swing.JPanel panelGrop;

	private javax.swing.JLabel lbDonViTien;
	private javax.swing.JComboBox cboQuyDoi, cboUnitMoney, cboPriceType1, cboPriceType2, cboPriceType3;
	private javax.swing.JLabel lbQuyDoi;
	private javax.swing.ButtonGroup buttonGroup1;
	private javax.swing.ButtonGroup buttonGroup2;

	private javax.swing.JTextArea txtArea;
	private String type = "";

	public DialogConfig(boolean full, int width, int height) {
		this.width = width;
		this.sizeHeight = height;
		this.full = full;
		ImageIcon icon = new ImageIcon(HomeScreen.class.getResource("icon/icon.png"));
		Image im = icon.getImage();
		setIconImage(im);
		initComponents();
		loadIconLableDeception();
		setModal(true);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setLocationRelativeTo(this);
		addKeyBindings(panelBackground1);
		addKeyBindings(panelBackground2);
		addKeyBindings(panelBackground3);
		addKeyBindings(panelBackground4);
		addKeyBindings(panelBackground5);

		try {
			List<ProductTag> tags = ProductModelManager.getInstance().getProductTags();
			List<ProductTag> listP = new ArrayList<ProductTag>();
			for (ProductTag productTag : tags) {
				if (productTag.getTag().split(":").length == 1 && !productTag.getCode().equals("MenuVoucher")) {
					listP.add(productTag);
				}
			}
			if (listP.size() < 4) {
				panelGrop.setLayout(new GridLayout(1, 3));
			} else {
				panelGrop.setLayout(new GridLayout(2, 3, 5, 0));
			}
			for (int i = 0; i < listP.size(); i++) {
				CheckBoxP checkBoxP = new CheckBoxP(listP.get(i));
				checkBoxP.setFont(new Font("Tahoma", Font.PLAIN, 14));
				checkBoxP.setForeground(new Color(124, 0, 0));
				checkBoxP.setOpaque(false);
				listCheckBoxPs.add(checkBoxP);
				panelGrop.add(checkBoxP);
			}
		} catch (Exception e) {
		}
		try {
			Profile profile = AccountModelManager.getInstance().getProfileConfigAdmin();

			// Thiet lap GD
			if (profile.get(Keyboard).toString().equals("true")) {
				chbKeyboard.setSelected(true);
			} else {
				chbKeyboard.setSelected(false);
			}

			if (profile.get(InHuyMon).toString().equals("true"))

			{
				chbInHuyMon.setSelected(true);
			} else {
				chbInHuyMon.setSelected(false);
			}

			if (profile.get(GioHoaDon).toString().equals("true")) {
				chbInCBNSP.setSelected(true);
			} else {
				chbInCBNSP.setSelected(false);
			}

			if (profile.get(Datgio).toString().equals("true")) {
				chbDatgio.setSelected(true);
			} else {
				chbDatgio.setSelected(false);
			}

			if (profile.get(Thue).toString().equals("true")) {
				chbThue.setSelected(true);
			} else {
				chbThue.setSelected(false);
			}

			if (profile.get(InCheBien).toString().equals("true")) {
				chbInCheBien.setSelected(true);
			} else {
				chbInCheBien.setSelected(false);
			}

			if (profile.get(InCheBien).toString().equals("true")) {
				chbInCheBien.setSelected(true);
			} else {
				chbInCheBien.setSelected(false);
			}

			if (profile.get(SLBP).toString().equals("true")) {
				chbSLBP.setSelected(true);
			} else {
				chbSLBP.setSelected(false);
			}

			if (profile.get(CK).toString().equals("true")) {
				chbChietKhau.setSelected(true);
			} else {
				chbChietKhau.setSelected(false);
			}

			if (profile.get(DichVu).toString().equals("true")) {
				chbDichVu.setSelected(true);
			} else {
				chbDichVu.setSelected(false);
			}

			if (profile.get(TTLe).toString().equals("true")) {
				chbThanhToanLe.setSelected(true);
			} else {
				chbThanhToanLe.setSelected(false);
			}

			if (profile.get(SoLuong).toString().equals("true")) {
				chbSoluong.setSelected(true);
			} else {

				chbSoluong.setSelected(false);
			}

			if (profile.get(SuaSL).toString().equals("true")) {
				chbSuaSL.setSelected(true);
			} else {
				chbSuaSL.setSelected(false);
			}

			if (profile.get(ThietLapBH).toString().equals("true")) {
				chbThietLapBan.setSelected(true);
			} else {
				chbThietLapBan.setSelected(false);
			}

			if (profile.get(TraSau).toString().equals("true")) {
				chbTraSau.setSelected(true);
			} else {
				chbTraSau.setSelected(false);
			}

			if (profile.get(HuySP).toString().equals("true")) {
				chbHuymon.setSelected(true);
			} else {
				chbHuymon.setSelected(false);
			}

			if (profile.get(TaoSP).toString().equals("true")) {
				chbTaoSP.setSelected(true);
			} else {
				chbTaoSP.setSelected(false);
			}

			if (profile.get(SuaGia).toString().equals("true")) {
				chbSuagia.setSelected(true);
			} else {
				chbSuagia.setSelected(false);
			}

			if (profile.get(XoaSP).toString().equals("true")) {
				chbXoamon.setSelected(true);
			} else {
				chbXoamon.setSelected(false);
			}

			if (profile.get(CTKM).toString().equals("true")) {
				chbCTKM.setSelected(true);
			} else {
				chbCTKM.setSelected(false);
			}

			if (profile.get(DoiGia).toString().equals("true")) {
				chbDoiGia.setSelected(true);
			} else {
				chbDoiGia.setSelected(false);
			}

			if (profile.get(MuaHo).toString().equals("true")) {
				chbMuaHo.setSelected(true);
			} else {
				chbMuaHo.setSelected(false);
			}

			if (profile.get(Paymen).toString().equals("true")) {
				chbPaymen.setSelected(true);
			} else {
				chbPaymen.setSelected(false);
			}

			if (profile.get(PaymenNow).toString().equals("true")) {
				rbtPaymenNow.setSelected(true);
			} else {
				rbtPaymenNow.setSelected(false);
			}

			if (profile.get(PaymenView).toString().equals("true")) {
				rbtPaymenView.setSelected(true);
			} else {
				rbtPaymenView.setSelected(false);
			}

			if (profile.get(PrintReceip).toString().equals("true")) {
				chbPrintReceip.setSelected(true);
			} else {

				chbPrintReceip.setSelected(false);
			}

			if (profile.get(PrintNow).toString().equals("true")) {
				rbtPrintNow.setSelected(true);
			} else {
				rbtPrintNow.setSelected(false);
			}

			if (profile.get(PrintView).toString().equals("true")) {
				rbtPrintView.setSelected(true);
			} else {
				rbtPrintView.setSelected(false);
			}

			// Thiet lap KV
			if (profile.get(Area).toString().equals("true")) {
				chbArea.setSelected(true);
			} else {
				chbArea.setSelected(false);
			}

			if (profile.get(SingleArea).toString().equals("true")) {
				rbtSingleton.setSelected(true);
			} else {
				rbtSingleton.setSelected(false);
			}

			if (profile.get(AllArea).toString().equals("true")) {
				rbtAll.setSelected(true);
			} else {
				rbtAll.setSelected(false);
			}

			if (profile.get(TbArea).toString().equals("true")) {
				chbPricingTableArea.setSelected(true);
			} else {
				chbPricingTableArea.setSelected(false);
			}

			// Phien ban
			if (profile.get(Resto).toString().equals("true")) {
				rbtResto.setSelected(true);
			} else {
				rbtResto.setSelected(false);
			}

			if (profile.get(Cafe).toString().equals("true")) {
				rbtCafe.setSelected(true);
			} else {
				rbtCafe.setSelected(false);
			}

			if (profile.get(Kara).toString().equals("true")) {
				rbtKara.setSelected(true);
			} else {
				rbtKara.setSelected(false);
			}

			if (profile.get(Bia).toString().equals("true")) {
				rbtBia.setSelected(true);
			} else {
				rbtBia.setSelected(false);
			}

			if (profile.get(Maket).toString().equals("true")) {
				rbtMaket.setSelected(true);
			} else {
				rbtMaket.setSelected(false);
			}

			if (profile.get(Shop).toString().equals("true")) {
				rbtShop.setSelected(true);
			} else {
				rbtShop.setSelected(false);
			}

			if (profile.get(Spa).toString().equals("true")) {
				rbtSpa.setSelected(true);
			} else {
				rbtSpa.setSelected(false);
			}

			if (profile.get(TapHoa).toString().equals("true")) {
				rbtTapHoa.setSelected(true);
			} else {
				rbtTapHoa.setSelected(false);
			}

			if (profile.get(Quay).toString().equals("true")) {
				rbt1quay.setSelected(true);
			} else {
				rbt1quay.setSelected(false);
			}

			if (profile.get(NhieuQuay).toString().equals("true")) {
				rbtnhieuquay.setSelected(true);
			} else {
				rbtnhieuquay.setSelected(false);
			}

			if (profile.get(GDPos).toString().equals("true")) {
				rbtPoss.setSelected(true);
			} else {
				rbtPoss.setSelected(false);
			}

			if (profile.get(GDThuong).toString().equals("true")) {
				rbtThuong.setSelected(true);
			} else {
				rbtThuong.setSelected(false);
			}

			if (profile.get(One).toString().equals("true")) {
				rbt01.setSelected(true);
			} else {
				rbt01.setSelected(false);
			}

			if (profile.get(Two).toString().equals("true")) {
				rbt02.setSelected(true);
			} else {
				rbt02.setSelected(false);
			}

			if (profile.get(Three).toString().equals("true")) {
				rbt03.setSelected(true);
			} else {
				rbt03.setSelected(false);
			}

			if (profile.get(Four).toString().equals("true")) {
				rbt04.setSelected(true);
			} else {
				rbt04.setSelected(false);
			}

			if (profile.get(Five).toString().equals("true")) {
				rbt05.setSelected(true);
			} else {
				rbt05.setSelected(false);
			}

			if (profile.get(Six).toString().equals("true")) {
				rbt06.setSelected(true);
			} else {
				rbt06.setSelected(false);
			}

			if (profile.get(Seven).toString().equals("true")) {
				rbt07.setSelected(true);
			} else {
				rbt07.setSelected(false);
			}

			if (profile.get(Eight).toString().equals("true")) {
				rbt08.setSelected(true);
			} else {
				rbt08.setSelected(false);
			}

			if (profile.get(Null).toString().equals("true")) {
				rbtNull.setSelected(true);
			} else {
				rbtNull.setSelected(false);
			}
			if (profile.get(ImageOption) != null) {
				rbt09.setSelected(true);
			} else {
				rbt09.setSelected(false);
			}

			if (profile.get(ProductPriceType) != null) {
				type = profile.get(ProductPriceType).toString();
			} else {
				type = "";
			}

			if (profile.get(cboPriceType1.getName()) != null) {
				int i = Integer.parseInt(profile.get(cboPriceType1.getName()).toString());
				if (i >= 0) {
					cboPriceType1.setSelectedIndex(i);
				} else {
					rdbPrcingTable1.setVisible(false);
				}
			} else {
				rdbPrcingTable1.setVisible(false);
			}

			if (profile.get(cboPriceType2.getName()) != null) {
				int i = Integer.parseInt(profile.get(cboPriceType2.getName()).toString());
				if (i >= 0) {
					cboPriceType2.setSelectedIndex(i);
				} else {
					rdbPrcingTable2.setVisible(false);
				}
			} else {
				rdbPrcingTable2.setVisible(false);
			}

			if (profile.get(cboPriceType3.getName()) != null) {
				int i = Integer.parseInt(profile.get(cboPriceType3.getName()).toString());
				if (i >= 0) {
					cboPriceType3.setSelectedIndex(i);
				} else {
					rdbPrcingTable3.setVisible(false);
				}
			} else {
				rdbPrcingTable3.setVisible(false);
			}

			for (int i = 0; i < cboUnitMoney.getItemCount(); i++) {
				if (((Currency) cboUnitMoney.getItemAt(i)).getCode().equals(profile.get(Currency).toString())) {
					cboUnitMoney.setSelectedIndex(i);
					break;
				}
			}

			for (int i = 0; i < cboQuyDoi.getItemCount(); i++) {
				if (((Currency) cboQuyDoi.getItemAt(i)).getCode().equals(profile.get(CurrencyQD).toString())) {
					cboQuyDoi.setSelectedIndex(i);
					break;
				}
			}
			if (profile.get("Project") != null && profile.get("Project").equals("true")) {
				chbProjectCode.setSelected(true);
			}

			if (profile.get("Product") != null && profile.get("Product").equals("true")) {
				chbProduct.setSelected(true);
			}
			if (profile.get("Usb") != null && profile.get("Usb").equals("true")) {
				chbUSB.setSelected(true);
			}
			if (profile.get("ForRent") != null && profile.get("ForRent").equals("true")) {
				chbForRent.setSelected(true);
			}

			if (profile.get("PaymentSlip") != null && profile.get("PaymentSlip").equals("true")) {
				chbProductPayment.setSelected(true);
			}
			if (profile.get("DailyWork") != null && profile.get("DailyWork").equals("true")) {
				chbDailyWork.setSelected(true);
			}
			if (profile.get("ExelMail") != null) {
				chbExelMail.setSelected(true);
				txtMail.setText(profile.get("ExelMail").toString());
			}
			if (profile.get("Exel") != null && profile.get("Exel").equals("true")) {
				chbExel.setSelected(true);
			}
			if (profile.get("Pdf") != null && profile.get("Pdf").equals("true")) {
				chbPdf.setSelected(true);
			}

			@SuppressWarnings("unchecked")
			List<String> productTag = (List<String>) profile.get(ProductTag);
			if (productTag != null) {
				for (CheckBoxP chb : listCheckBoxPs) {
					if (productTag.indexOf(chb.getProductTag().getTag()) >= 0) {
						chb.setSelected(true);
					}
				}
			}

		} catch (Exception e) {
			rdbPrcingTable1.setVisible(false);
			rdbPrcingTable2.setVisible(false);
			rdbPrcingTable3.setVisible(false);
		}
		
		chbChietKhau.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(chbChietKhau.isSelected()){
					PanelDiscount panel = new PanelDiscount();
					DialogResto dialog = new DialogResto(panel, false,0,300);
					dialog.setTitle("Chỉnh sửa chiết khấu");
					dialog.setLocationRelativeTo(null);
					dialog.setModal(true);
					dialog.setVisible(true);
					
				}
				
			}
		});

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {
				print = true;
				System.out.println("opend   " + new JVMEnv().getMemoryInfo());
			}

			@Override
			public void windowClosed(WindowEvent e) {
				if (print) {
					new GuiModelManager().getObservable().deleteObservers();
					System.out.println("close   " + new JVMEnv().getMemoryInfo());
					print = false;

				}
			}

		});
	}

	class CheckBoxP extends JCheckBox {
		private ProductTag productTag;

		public CheckBoxP(ProductTag productTag) {
			super(productTag.toString());
			this.productTag = productTag;
		}

		public ProductTag getProductTag() {
			return productTag;
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initComponents() {

		panelBackground2 = new PanelBackground();
		PanelTrans3 = new PanelTrans();
		btnThoat2 = new javax.swing.JButton();
		btnDongY2 = new javax.swing.JButton();
		PanelTrans4 = new PanelTrans();
		chbMuaHo = new javax.swing.JCheckBox();
		chbXoamon = new javax.swing.JCheckBox();
		chbHuymon = new javax.swing.JCheckBox();
		chbSoluong = new javax.swing.JCheckBox();
		chbDatgio = new javax.swing.JCheckBox();
		chbTraSau = new javax.swing.JCheckBox();
		chbDoiGia = new javax.swing.JCheckBox();
		chbInCBNSP = new javax.swing.JCheckBox();
		chbInCheBien = new javax.swing.JCheckBox();
		chbInHuyMon = new javax.swing.JCheckBox();
		chbThanhToanLe = new javax.swing.JCheckBox();
		chbCTKM = new javax.swing.JCheckBox();
		chbPaymen = new javax.swing.JCheckBox();
		chbPrintReceip = new javax.swing.JCheckBox();
		rbtPaymenNow = new javax.swing.JRadioButton();
		rbtPaymenView = new javax.swing.JRadioButton();
		rbtPrintNow = new javax.swing.JRadioButton();
		rbtPrintView = new javax.swing.JRadioButton();
		chbSuaSL = new javax.swing.JCheckBox();
		chbSuagia = new javax.swing.JCheckBox();
		jLabel36 = new javax.swing.JLabel();
		chbTaoSP = new javax.swing.JCheckBox();
		chbThietLapBan = new javax.swing.JCheckBox();
		chbSLBP = new javax.swing.JCheckBox();
		chbThue = new javax.swing.JCheckBox();
		chbChietKhau = new javax.swing.JCheckBox();
		chbDichVu = new javax.swing.JCheckBox();
		lbThietLapGiaoDien = new javax.swing.JLabel();
		jLabel13 = new javax.swing.JLabel();
		lbHKT1 = new javax.swing.JLabel();
		jLabel15 = new javax.swing.JLabel();
		jLabel16 = new javax.swing.JLabel();
		panelBackground3 = new PanelBackground();
		PanelTrans5 = new PanelTrans();
		btnThoat3 = new javax.swing.JButton();
		btnDongY3 = new javax.swing.JButton();
		btnDongY4 = new javax.swing.JButton();
		btnThoat4 = new javax.swing.JButton();
		PanelTrans6 = new PanelTrans();
		chbArea = new javax.swing.JCheckBox();
		txtArea = new javax.swing.JTextArea();
		rbtSingleton = new javax.swing.JRadioButton();
		rbtAll = new javax.swing.JRadioButton();
		chbPricingTableArea = new javax.swing.JCheckBox();
		btnPricingTable = new javax.swing.JButton();
		jLabel37 = new javax.swing.JLabel();
		jLabel38 = new javax.swing.JLabel();
		lbPhanQuyenKhuVuc = new javax.swing.JLabel();
		jLabel22 = new javax.swing.JLabel();
		lbHKT3 = new javax.swing.JLabel();
		jLabel24 = new javax.swing.JLabel();
		jLabel25 = new javax.swing.JLabel();
		panelBackground4 = new PanelBackground();
		cbDepartment = new DepartmentJComboBox();
		cbProject = new ProjectJComboBox();
		cbStore = new StoreJComboBox();
		panelBackground5 = new PanelBackground();
		PanelTrans9 = new PanelTrans();
		PanelTrans7 = new PanelTrans();
		PanelTrans8 = new PanelTrans();
		btnThoat5 = new javax.swing.JButton();
		btnDongY5 = new javax.swing.JButton();
		PanelTrans10 = new PanelTrans();
		jPanel3 = new javax.swing.JPanel();
		lbGiaoDienSuDung = new javax.swing.JLabel();
		rbtPoss = new javax.swing.JRadioButton();
		rbtThuong = new javax.swing.JRadioButton();
		lbLoaiHinhBanHang = new javax.swing.JLabel();
		lbHinhNen = new javax.swing.JLabel();
		rbtResto = new javax.swing.JRadioButton();
		rbtCafe = new javax.swing.JRadioButton();
		rbtKara = new javax.swing.JRadioButton();
		rbtBia = new javax.swing.JRadioButton();
		rbtMaket = new javax.swing.JRadioButton();
		rbtShop = new javax.swing.JRadioButton();
		rbtSpa = new javax.swing.JRadioButton();
		rbtTapHoa = new javax.swing.JRadioButton();
		rbt01 = new javax.swing.JRadioButton();
		rbt05 = new javax.swing.JRadioButton();
		rbt02 = new javax.swing.JRadioButton();
		rbt06 = new javax.swing.JRadioButton();
		rbt03 = new javax.swing.JRadioButton();
		rbt07 = new javax.swing.JRadioButton();
		rbt04 = new javax.swing.JRadioButton();
		rbt08 = new javax.swing.JRadioButton();
		lb06 = new javax.swing.JLabel();
		lb01 = new javax.swing.JLabel();
		lb05 = new javax.swing.JLabel();
		lb02 = new javax.swing.JLabel();
		lb04 = new javax.swing.JLabel();
		lb03 = new javax.swing.JLabel();
		lb08 = new javax.swing.JLabel();
		lb07 = new javax.swing.JLabel();
		rbt1quay = new javax.swing.JRadioButton();
		rbtnhieuquay = new javax.swing.JRadioButton();
		rbtNull = new javax.swing.JRadioButton();
		lbThietLapPhienBan = new javax.swing.JLabel();
		jLabel32 = new javax.swing.JLabel();
		lbHKT5 = new javax.swing.JLabel();
		jLabel34 = new javax.swing.JLabel();
		jLabel35 = new javax.swing.JLabel();
		buttonGroup3 = new javax.swing.ButtonGroup();
		buttonGroup4 = new javax.swing.ButtonGroup();
		buttonGroup5 = new javax.swing.ButtonGroup();
		buttonGroup6 = new javax.swing.ButtonGroup();
		buttonGroup7 = new javax.swing.ButtonGroup();
		buttonGroup8 = new javax.swing.ButtonGroup();
		buttonGroup9 = new javax.swing.ButtonGroup();
		jTabbedPane1 = new javax.swing.JTabbedPane();
		panelBackground1 = new PanelBackground();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
		jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 14));
		jTabbedPane1.setOpaque(true);
		buttonGroup1 = new javax.swing.ButtonGroup();
		buttonGroup2 = new javax.swing.ButtonGroup();
		PanelTrans1 = new PanelTrans();
		rdbPrcingTable1 = new javax.swing.JRadioButton();
		rdbPrcingTable2 = new javax.swing.JRadioButton();
		rdbPrcingTable3 = new javax.swing.JRadioButton();
		btnThoat1 = new javax.swing.JButton();
		btnDongY1 = new javax.swing.JButton();
		PanelTrans2 = new PanelTrans();
		cboPriceType1 = new javax.swing.JComboBox();
		lbLoaiBangGia1 = new javax.swing.JLabel();
		lbNhomSanPham = new javax.swing.JLabel();
		lbLoaiBangGia2 = new javax.swing.JLabel();
		cboPriceType3 = new javax.swing.JComboBox();
		lbLoaiBangGia3 = new javax.swing.JLabel();
		cboPriceType2 = new javax.swing.JComboBox();
		panelGrop = new javax.swing.JPanel();
		lbDonViTien = new javax.swing.JLabel();
		cboUnitMoney = new javax.swing.JComboBox();
		chbKeyboard = new javax.swing.JCheckBox();
		rbtAllSystem = new javax.swing.JRadioButton();
		rbtSalse = new javax.swing.JRadioButton();
		lbQuyDoi = new javax.swing.JLabel();
		cboQuyDoi = new javax.swing.JComboBox();
		lbThietLapMuaBanHang = new javax.swing.JLabel();
		jLabel9 = new javax.swing.JLabel();
		lbHKT = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		jLabel9.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/description.png"))); // NOI18N
		jLabel9.setSize(new Dimension(5, 5));
		jLabel9.setText(""); // NOI18N
		jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

		lbHKT.setFont(new java.awt.Font("Tahoma", 1, 16));
		lbHKT.setForeground(new java.awt.Color(126, 0, 0));
		lbHKT.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lbHKT.setText("HKT Sofware"); // NOI18N

		jLabel2.setText(""); // NOI18N

		jLabel3.setText(""); // NOI18N
		PanelTrans1.setOpaque(false);
		PanelTrans1.setPreferredSize(new java.awt.Dimension(593, 387));

		buttonGroup1.add(rdbPrcingTable1);
		rdbPrcingTable1.setFont(new java.awt.Font("Tahoma", 0, 14));
		rdbPrcingTable1.setForeground(new java.awt.Color(124, 0, 0));
		rdbPrcingTable1.setText("Bảng giá 1"); // NOI18N
		rdbPrcingTable1.setOpaque(false);
		rdbPrcingTable1.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				rdbPrcingTable1ItemStateChanged(evt);
			}
		});

		buttonGroup1.add(rdbPrcingTable2);
		rdbPrcingTable2.setFont(new java.awt.Font("Tahoma", 0, 14));
		rdbPrcingTable2.setForeground(new java.awt.Color(124, 0, 0));
		rdbPrcingTable2.setText("Bảng giá 2"); // NOI18N
		rdbPrcingTable2.setOpaque(false);
		rdbPrcingTable2.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				rdbPrcingTable2ItemStateChanged(evt);
			}
		});

		buttonGroup1.add(rdbPrcingTable3);
		rdbPrcingTable3.setFont(new java.awt.Font("Tahoma", 0, 14));
		rdbPrcingTable3.setForeground(new java.awt.Color(124, 0, 0));
		rdbPrcingTable3.setText("Bảng giá 3"); // NOI18N
		rdbPrcingTable3.setOpaque(false);
		rdbPrcingTable3.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				rdbPrcingTable3ItemStateChanged(evt);
			}
		});

		cboPriceType1.setName("cbo1");
		cboPriceType2.setName("cbo2");
		cboPriceType3.setName("cbo3");

		btnThoat1.setFont(new java.awt.Font("Tahoma", 1, 14));
		btnThoat1.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/iconBack.png"))); // NOI18N
		btnThoat1.setText("Thoát"); // NOI18N
		btnThoat1.setIconTextGap(10);
		btnThoat1.addMouseListener(new MouseEventClickButtonDialog("iconBack.png", "iconBack.png", "iconBack.png"));
		btnThoat1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnThoat1ActionPerformed(evt);
			}
		});

		btnDongY1.setFont(new java.awt.Font("Tahoma", 1, 14));
		btnDongY1.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/iconOk.png"))); // NOI18N
		btnDongY1.setText("Đồng ý"); // NOI18N
		btnDongY1.setIconTextGap(4);
		btnDongY1.setPreferredSize(new java.awt.Dimension(115, 35));
		btnDongY1.addMouseListener(new MouseEventClickButtonDialog("iconOk.png", "iconOk.png", "iconOk.png"));
		btnDongY1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDongY1ActionPerformed(evt);
			}
		});

		PanelTrans2.setOpaque(false);

		cboPriceType1.setFont(new java.awt.Font("Tahoma", 0, 14));
		cboPriceType1.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				cboPriceType1ItemStateChanged(evt);
			}
		});

		try {
			listProductPriceType = ProductModelManager.getInstance().getProductPriceTypes();
			listProductPriceType.add(0, null);
			listPriceType = new DefaultComboBoxModel(listProductPriceType.toArray());
			cboPriceType1.setModel(listPriceType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		lbLoaiBangGia1.setFont(new java.awt.Font("Tahoma", 1, 14));
		lbLoaiBangGia1.setText("Loại bảng giá 1"); // NOI18N

		lbNhomSanPham.setFont(new java.awt.Font("Tahoma", 1, 14));
		lbNhomSanPham.setText("Nhóm sản phẩm"); // NOI18N

		lbLoaiBangGia2.setFont(new java.awt.Font("Tahoma", 1, 14));
		lbLoaiBangGia2.setText("Loại bảng giá 2"); // NOI18N

		cboPriceType3.setFont(new java.awt.Font("Tahoma", 0, 14));
		cboPriceType3.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				cboPriceType3ItemStateChanged(evt);

			}
		});

		lbLoaiBangGia3.setFont(new java.awt.Font("Tahoma", 1, 14));
		lbLoaiBangGia3.setText("Loại bảng giá 3"); // NOI18N

		try {
			listProductPriceType = ProductModelManager.getInstance().getProductPriceTypes();
			listProductPriceType.add(0, null);
			listPriceType = new DefaultComboBoxModel(listProductPriceType.toArray());
			cboPriceType3.setModel(listPriceType);
		} catch (Exception e) {
			e.printStackTrace();
		}

		cboPriceType2.setFont(new java.awt.Font("Tahoma", 0, 14));
		cboPriceType2.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				cboPriceType2ItemStateChanged(evt);
			}
		});

		try {
			listProductPriceType = ProductModelManager.getInstance().getProductPriceTypes();
			listProductPriceType.add(0, null);
			listPriceType = new DefaultComboBoxModel(listProductPriceType.toArray());
			cboPriceType2.setModel(listPriceType);
		} catch (Exception e) {
			e.printStackTrace();
		}

		panelGrop.setOpaque(false);

		lbDonViTien.setFont(new java.awt.Font("Tahoma", 1, 14));
		lbDonViTien.setText("Đơn vị tiền"); // NOI18N

		cboUnitMoney.setFont(new java.awt.Font("Tahoma", 0, 14));

		cboUnitMoney.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					PanelCurrency pnCurrencyr;
					try {
						pnCurrencyr = new PanelCurrency();
						pnCurrencyr.setName("TienTe");
						DialogMain dialog = new DialogMain(pnCurrencyr);
						dialog.setIcon("tien1.png");
						dialog.setName("dlTienTe");
						dialog.setTitle("Tiền tệ");
						dialog.setModal(true);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);
						try {
							cboUnitMoney.setModel(new DefaultComboBoxModel(clientLocale.getCurrencies().toArray()));
						} catch (Exception e1) {
							cboUnitMoney.setModel(new DefaultComboBoxModel());
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		cbDepartment.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbStore.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbProject.setFont(new Font("Tahoma", Font.PLAIN, 14));

		String departmentCode = "";
		String storeCode = "";
		String projectCode = "";
		try {
			FileReader fr = new FileReader(HKTFile.getFile("Database", "configPayment.json"));
			BufferedReader br = new BufferedReader(fr);
			String s;
			String jsonString = "";
			while ((s = br.readLine()) != null) {
				jsonString = jsonString + s;
			}
			br.close();
			fr.close();

			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(jsonString);
			departmentCode = (String) obj.get("departmentCode");
			storeCode = (String) obj.get("storeCode");
			projectCode = (String) obj.get("projectCode");
		} catch (Exception ex) {
			// ex.printStackTrace();
		}

		if (!departmentCode.equals("")) {
			cbDepartment.setSelectDepartmentByCode(departmentCode);
		} else {
			cbDepartment.setSelectDepartmentByIndex(0);
		}

		if (!projectCode.equals("")) {
			cbProject.setSelectProjectByCode(projectCode);
		} else {
			cbProject.setSelectedIndex(0);
		}

		if (!storeCode.equals("")) {
			cbStore.setSelectStoreByCode(storeCode);
		} else {
			cbStore.setSelectedIndex(0);
		}

		cbDepartment.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				changeComboBox = true;
			}
		});

		cbProject.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				changeComboBox = true;
			}
		});

		cbStore.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				changeComboBox = true;
			}
		});

		// Lấy danh sách Đơn vị tiền tệ add vào Combobox btnDVTein
		try {
			cboUnitMoney.setModel(new DefaultComboBoxModel(clientLocale.getCurrencies().toArray()));
		} catch (Exception e) {
			cboUnitMoney.setModel(new DefaultComboBoxModel());
		}

		chbKeyboard.setFont(new java.awt.Font("Tahoma", 0, 14));
		chbKeyboard.setText("Bàn phím ảo"); // NOI18N
		chbKeyboard.setOpaque(false);
		chbKeyboard.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				chbKeyboardItemStateChanged(evt);
			}
		});

		buttonGroup2.add(rbtAllSystem);
		rbtAllSystem.setFont(new java.awt.Font("Tahoma", 0, 14));
		rbtAllSystem.setSelected(true);
		rbtAllSystem.setText("Dùng cả hệ thống"); // NOI18N
		rbtAllSystem.setEnabled(false);
		rbtAllSystem.setOpaque(false);

		buttonGroup2.add(rbtSalse);
		rbtSalse.setFont(new java.awt.Font("Tahoma", 0, 14));
		rbtSalse.setText("Dùng trên màn hình bán hàng"); // NOI18N
		rbtSalse.setEnabled(false);
		rbtSalse.setOpaque(false);

		lbQuyDoi.setFont(new java.awt.Font("Tahoma", 1, 14));
		lbQuyDoi.setText("Quy đổi"); // NOI18N

		cboQuyDoi.setFont(new java.awt.Font("Tahoma", 0, 14));
		cboQuyDoi.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					PanelCurrency pnCurrencyr;
					try {
						pnCurrencyr = new PanelCurrency();
						pnCurrencyr.setName("TienTe");
						DialogMain dialog = new DialogMain(pnCurrencyr);
						dialog.setIcon("tien1.png");
						dialog.setName("dlTienTe");
						dialog.setTitle("Tiền tệ");
						dialog.setModal(true);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);
						// Lấy danh sách Đơn vị tiền tệ add vào Combobox btnDVTein
						try {
							cboQuyDoi.setModel(new DefaultComboBoxModel(clientLocale.getCurrencies().toArray()));
						} catch (Exception e1) {
							cboQuyDoi.setModel(new DefaultComboBoxModel());
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		// Lấy danh sách Đơn vị tiền tệ add vào Combobox btnDVTein
		try {
			cboQuyDoi.setModel(new DefaultComboBoxModel(clientLocale.getCurrencies().toArray()));
		} catch (Exception e) {
			cboQuyDoi.setModel(new DefaultComboBoxModel());
		}

		// TAB1: Thêm các components giao diện TAB thiết lập bán hàng
		lbThietLapMuaBanHang.setFont(new java.awt.Font("Tahoma", 1, 14));
		lbThietLapMuaBanHang.setForeground(new java.awt.Color(126, 0, 0));
		lbThietLapMuaBanHang.setText("Thiết lập bán hàng");

		javax.swing.GroupLayout PanelTrans2Layout = new javax.swing.GroupLayout(PanelTrans2);
		PanelTrans2.setLayout(PanelTrans2Layout);
		PanelTrans2Layout.setHorizontalGroup(PanelTrans2Layout.createParallelGroup(
		    javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		    PanelTrans2Layout
		        .createSequentialGroup()
		        .addGap(24, 24, 24)
		        .addGroup(
		            PanelTrans2Layout
		                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                .addGroup(
		                    PanelTrans2Layout
		                        .createSequentialGroup()
		                        .addGroup(
		                            PanelTrans2Layout
		                                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
		                                .addComponent(chbKeyboard, javax.swing.GroupLayout.Alignment.LEADING,
		                                    javax.swing.GroupLayout.PREFERRED_SIZE, 182,
		                                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                                .addGroup(
		                                    PanelTrans2Layout
		                                        .createSequentialGroup()
		                                        .addGap(46, 46, 46)
		                                        .addComponent(rbtAllSystem, javax.swing.GroupLayout.PREFERRED_SIZE, 144,
		                                            javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18)))
		                        .addComponent(rbtSalse).addContainerGap())
		                .addGroup(
		                    PanelTrans2Layout
		                        .createSequentialGroup()
		                        .addGroup(
		                            PanelTrans2Layout
		                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                                .addComponent(lbNhomSanPham)
		                                .addComponent(panelGrop, javax.swing.GroupLayout.DEFAULT_SIZE,
		                                    javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                                .addGroup(
		                                    PanelTrans2Layout
		                                        .createSequentialGroup()
		                                        .addGroup(
		                                            PanelTrans2Layout
		                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                                                .addComponent(lbLoaiBangGia3,
		                                                    javax.swing.GroupLayout.PREFERRED_SIZE, 143,
		                                                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                                                .addComponent(lbDonViTien))
		                                        .addGroup(
		                                            PanelTrans2Layout
		                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                                                .addGroup(
		                                                    PanelTrans2Layout.createSequentialGroup().addGap(18, 18, 18)
		                                                        .addComponent(cboPriceType3, 0, 332, Short.MAX_VALUE))
		                                                .addGroup(
		                                                    PanelTrans2Layout
		                                                        .createSequentialGroup()
		                                                        .addGap(19, 19, 19)
		                                                        .addComponent(cboUnitMoney,
		                                                            javax.swing.GroupLayout.PREFERRED_SIZE, 137,
		                                                            javax.swing.GroupLayout.PREFERRED_SIZE)
		                                                        .addPreferredGap(
		                                                            javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		                                                        .addComponent(lbQuyDoi)
		                                                        .addPreferredGap(
		                                                            javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		                                                        .addComponent(cboQuyDoi, 0, 121, Short.MAX_VALUE))))
		                                .addGroup(
		                                    PanelTrans2Layout
		                                        .createSequentialGroup()
		                                        .addGroup(
		                                            PanelTrans2Layout
		                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                                                .addComponent(lbLoaiBangGia1,
		                                                    javax.swing.GroupLayout.PREFERRED_SIZE, 143,
		                                                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                                                .addComponent(lbLoaiBangGia2,
		                                                    javax.swing.GroupLayout.PREFERRED_SIZE, 129,
		                                                    javax.swing.GroupLayout.PREFERRED_SIZE))
		                                        .addGap(19, 19, 19)
		                                        .addGroup(
		                                            PanelTrans2Layout
		                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                                                .addComponent(cboPriceType2, 0, 331, Short.MAX_VALUE)
		                                                .addComponent(cboPriceType1, 0, 331, Short.MAX_VALUE))))
		                        .addGap(26, 26, 26)))));
		PanelTrans2Layout.setVerticalGroup(PanelTrans2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        PanelTrans2Layout
		            .createSequentialGroup()
		            .addComponent(lbNhomSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(11, 11, 11)
		            .addComponent(panelGrop, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(11, 11, 11)
		            .addGroup(
		                PanelTrans2Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(lbLoaiBangGia1, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(cboPriceType1, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
		                        javax.swing.GroupLayout.PREFERRED_SIZE))
		            .addGroup(
		                PanelTrans2Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addGroup(
		                        PanelTrans2Layout
		                            .createSequentialGroup()
		                            .addGap(8, 8, 8)
		                            .addComponent(lbLoaiBangGia2, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
		                                javax.swing.GroupLayout.PREFERRED_SIZE))
		                    .addGroup(
		                        PanelTrans2Layout
		                            .createSequentialGroup()
		                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                            .addComponent(cboPriceType2, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)))
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		            .addGroup(
		                PanelTrans2Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		                    .addComponent(lbLoaiBangGia3, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(cboPriceType3, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
		                        javax.swing.GroupLayout.PREFERRED_SIZE))
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		            .addGroup(
		                PanelTrans2Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addGroup(
		                        PanelTrans2Layout
		                            .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		                            .addComponent(cboUnitMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)
		                            .addComponent(lbQuyDoi, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)
		                            .addComponent(cboQuyDoi, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
		                                javax.swing.GroupLayout.PREFERRED_SIZE))
		                    .addComponent(lbDonViTien, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
		                        javax.swing.GroupLayout.PREFERRED_SIZE))
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 3,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addComponent(chbKeyboard)
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		            .addGroup(
		                PanelTrans2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		                    .addComponent(rbtSalse).addComponent(rbtAllSystem))));

		javax.swing.GroupLayout PanelTrans1Layout = new javax.swing.GroupLayout(PanelTrans1);
		PanelTrans1.setLayout(PanelTrans1Layout);
		PanelTrans1Layout.setHorizontalGroup(PanelTrans1Layout.createParallelGroup(
		    javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        PanelTrans1Layout
		            .createSequentialGroup()
		            .addGroup(
		                PanelTrans1Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addGroup(
		                        PanelTrans1Layout
		                            .createSequentialGroup()
		                            .addGap(27, 27, 27)
		                            .addGroup(
		                                PanelTrans1Layout
		                                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                                    .addGroup(
		                                        PanelTrans1Layout
		                                            .createSequentialGroup()
		                                            .addGap(46, 46, 46)
		                                            .addComponent(rdbPrcingTable1, javax.swing.GroupLayout.PREFERRED_SIZE,
		                                                123, javax.swing.GroupLayout.PREFERRED_SIZE))
		                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
		                                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                                    .addGroup(
		                                        PanelTrans1Layout.createSequentialGroup().addGap(39, 39, 39)
		                                            .addComponent(lbThietLapMuaBanHang)))
		                            .addGap(18, 18, 18)
		                            .addComponent(rdbPrcingTable2, javax.swing.GroupLayout.PREFERRED_SIZE, 129,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)
		                            .addGap(33, 33, 33)
		                            .addComponent(rdbPrcingTable3, javax.swing.GroupLayout.PREFERRED_SIZE, 124,
		                                javax.swing.GroupLayout.PREFERRED_SIZE))
		                    .addGroup(
		                        PanelTrans1Layout
		                            .createSequentialGroup()
		                            .addGap(20, 20, 20)
		                            .addGroup(
		                                PanelTrans1Layout
		                                    .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
		                                    .addGroup(
		                                        PanelTrans1Layout
		                                            .createSequentialGroup()
		                                            .addComponent(btnDongY1, javax.swing.GroupLayout.PREFERRED_SIZE, 118,
		                                                javax.swing.GroupLayout.PREFERRED_SIZE)
		                                            .addGap(10, 10, 10)
		                                            .addComponent(btnThoat1, javax.swing.GroupLayout.PREFERRED_SIZE, 118,
		                                                javax.swing.GroupLayout.PREFERRED_SIZE))
		                                    .addComponent(PanelTrans2, javax.swing.GroupLayout.PREFERRED_SIZE, 552,
		                                        javax.swing.GroupLayout.PREFERRED_SIZE))))
		            .addContainerGap(30, Short.MAX_VALUE)));
		PanelTrans1Layout.setVerticalGroup(PanelTrans1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        PanelTrans1Layout
		            .createSequentialGroup()
		            .addGroup(
		                PanelTrans1Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addGroup(
		                        PanelTrans1Layout
		                            .createSequentialGroup()
		                            .addGap(33, 33, 33)
		                            .addComponent(rdbPrcingTable1, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
		                                javax.swing.GroupLayout.PREFERRED_SIZE))
		                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addGroup(
		                        PanelTrans1Layout
		                            .createSequentialGroup()
		                            .addGap(11, 11, 11)
		                            .addComponent(lbThietLapMuaBanHang, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
		                                javax.swing.GroupLayout.PREFERRED_SIZE))
		                    .addGroup(
		                        PanelTrans1Layout
		                            .createSequentialGroup()
		                            .addGap(33, 33, 33)
		                            .addComponent(rdbPrcingTable2, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
		                                javax.swing.GroupLayout.PREFERRED_SIZE))
		                    .addGroup(
		                        PanelTrans1Layout
		                            .createSequentialGroup()
		                            .addGap(33, 33, 33)
		                            .addComponent(rdbPrcingTable3, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)))
		            .addGap(7, 7, 7)
		            .addComponent(PanelTrans2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(10, 10, 10)
		            .addGroup(
		                PanelTrans1Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(btnDongY1, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(btnThoat1, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap()));

		javax.swing.GroupLayout thisLayout1 = new javax.swing.GroupLayout(panelBackground1);
		panelBackground1.setLayout(thisLayout1);
		thisLayout1.setHorizontalGroup(thisLayout1
		    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        thisLayout1
		            .createSequentialGroup()
		            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(4, 4, 4)
		            .addComponent(PanelTrans1, javax.swing.GroupLayout.PREFERRED_SIZE, 592,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(4, 4, 4)
		            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
		                javax.swing.GroupLayout.PREFERRED_SIZE))
		    .addGroup(
		        javax.swing.GroupLayout.Alignment.TRAILING,
		        thisLayout1
		            .createSequentialGroup()
		            .addContainerGap(481, javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addComponent(lbHKT, javax.swing.GroupLayout.PREFERRED_SIZE, 150,
		                javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()));
		thisLayout1.setVerticalGroup(thisLayout1.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		    thisLayout1
		        .createSequentialGroup()
		        .addComponent(lbHKT)
		        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		        .addGroup(
		            thisLayout1
		                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                .addGroup(
		                    thisLayout1
		                        .createSequentialGroup()
		                        .addGap(74, 74, 74)
		                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
		                            javax.swing.GroupLayout.PREFERRED_SIZE))
		                .addComponent(PanelTrans1, javax.swing.GroupLayout.PREFERRED_SIZE, 386,
		                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addGroup(
		                    thisLayout1
		                        .createSequentialGroup()
		                        .addGap(73, 73, 73)
		                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
		                            javax.swing.GroupLayout.PREFERRED_SIZE)))));

		jTabbedPane1.addTab("Thiết lập mua bán", panelBackground1); // NOI18N

		// TAB2: Thêm các components giao diện TAB thiết lập chung

		JPanel jpanelLeft = new JPanel();
		jpanelLeft.setLayout(new GridLayout(9, 1, 5, 5));
		jpanelLeft.setOpaque(false);
		jpanelLeft.setPreferredSize(new Dimension(100, 22));
		jpanelLeft.add(new ExtendJLabel("Phòng ban"));
		jpanelLeft.add(new ExtendJLabel("Dự án"));
		jpanelLeft.add(new ExtendJLabel("Hiển thị"));

		JPanel jpanelRight = new JPanel();
		jpanelRight.setLayout(new GridLayout(9, 1, 5, 5));
		jpanelRight.setOpaque(false);
		jpanelRight.add(cbDepartment);
		jpanelRight.add(cbProject);
		// TODO
		JPanel panel1 = new JPanel(new GridLayout(1,2));
		panel1.setOpaque(false);
		chbProjectCode = new JCheckBox("Mã dự án");
		chbProjectCode.setOpaque(false);
		chbProjectCode.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel1.add(chbProjectCode);

		chbProduct = new JCheckBox("Hiển thị giá sản phẩm");
		chbProduct.setOpaque(false);
		chbProduct.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel1.add(chbProduct);
		jpanelRight.add(panel1);
		
		JPanel panel2 = new JPanel(new GridLayout(1,2));
		panel2.setOpaque(false);
		chbForRent = new JCheckBox("Cho thuê sản phẩm");
		chbForRent.setOpaque(false);
		chbForRent.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel2.add(chbForRent);
		chbProductPayment = new JCheckBox("Xóa SP khi thanh toán lẻ");
		chbProductPayment.setOpaque(false);
		chbProductPayment.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel2.add(chbProductPayment);
		jpanelRight.add(panel2);
		
		JPanel panel3 = new JPanel(new GridLayout(1,2));
		panel3.setOpaque(false);
		chbUSB = new JCheckBox("Sử dụng USB phân quyền");
		chbUSB.setOpaque(false);
		chbUSB.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel3.add(chbUSB);
		chbDailyWork = new JCheckBox("Tạo sinh nhật");
		chbDailyWork.setOpaque(false);
		chbDailyWork.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel3.add(chbDailyWork);
		jpanelRight.add(panel3);

		chbExel = new JCheckBox("Xuất hóa đơn ra exel khi thanh toán");
		chbExel.setOpaque(false);
		chbExel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jpanelRight.add(chbExel);
		chbPdf = new JCheckBox("Xuất hóa đơn ra pdf khi thanh toán");
		chbPdf.setOpaque(false);
		chbPdf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jpanelRight.add(chbPdf);
		JPanel mail = new JPanel();
		mail.setSize(23, 23);
		mail.setPreferredSize(mail.getSize());
		mail.setOpaque(false);
		mail.setLayout(new GridLayout(1,2));
	
		chbExelMail = new JCheckBox("Gửi HD vào mail khi TT");
		chbExelMail.setOpaque(false);
		chbExelMail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		mail.add(chbExelMail);
		txtMail = new JTextField();
		txtMail.setOpaque(false);
		txtMail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		mail.add(txtMail);
		jpanelRight.add(mail);


		PanelTrans8.setOpaque(false);
		PanelTrans8.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
		PanelTrans8.setLayout(new BorderLayout());
		PanelTrans8.add(jpanelLeft, BorderLayout.LINE_START);
		PanelTrans8.add(jpanelRight, BorderLayout.CENTER);

		JLabel lbThietLapChung = new JLabel("Thiết lập chung");
		lbThietLapChung.setFont(new java.awt.Font("Tahoma", 1, 14));
		lbThietLapChung.setForeground(new java.awt.Color(126, 0, 0));

		JLabel lblIconThietLapChung = new JLabel("");
		lblIconThietLapChung.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		lblIconThietLapChung.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/description.png")));
		lblIconThietLapChung.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

		PanelTrans7.setOpaque(false);
		javax.swing.GroupLayout PanelTrans7Layout = new javax.swing.GroupLayout(PanelTrans7);
		PanelTrans7.setLayout(PanelTrans7Layout);
		PanelTrans7Layout.setHorizontalGroup(PanelTrans7Layout.createParallelGroup(
		    javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		    PanelTrans7Layout
		        .createSequentialGroup()
		        .addGap(0, 1, Short.MAX_VALUE)
		        .addGroup(
		            PanelTrans7Layout
		                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                .addGroup(
		                    PanelTrans7Layout
		                        .createSequentialGroup()
		                        .addGap(27, 27, 27)
		                        .addGroup(
		                            PanelTrans7Layout
		                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                                .addComponent(lblIconThietLapChung, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
		                                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                                .addGroup(
		                                    PanelTrans7Layout.createSequentialGroup().addGap(39, 39, 39)
		                                        .addComponent(lbThietLapChung))))
		                .addGroup(
		                    PanelTrans7Layout
		                        .createSequentialGroup()
		                        .addGap(20, 20, 20)
		                        .addGroup(
		                            PanelTrans7Layout
		                                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
		                                .addGroup(
		                                    PanelTrans7Layout
		                                        .createSequentialGroup()
		                                        .addComponent(btnDongY4, javax.swing.GroupLayout.PREFERRED_SIZE, 118,
		                                            javax.swing.GroupLayout.PREFERRED_SIZE)
		                                        .addGap(10, 10, 10)
		                                        .addComponent(btnThoat4, javax.swing.GroupLayout.PREFERRED_SIZE, 118,
		                                            javax.swing.GroupLayout.PREFERRED_SIZE))
		                                .addComponent(PanelTrans8, javax.swing.GroupLayout.PREFERRED_SIZE, 552,
		                                    javax.swing.GroupLayout.PREFERRED_SIZE)))).addGap(30, 30, 30)));
		PanelTrans7Layout.setVerticalGroup(PanelTrans7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        PanelTrans7Layout
		            .createSequentialGroup()
		            .addGroup(
		                PanelTrans7Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(lblIconThietLapChung, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addGroup(
		                        PanelTrans7Layout
		                            .createSequentialGroup()
		                            .addGap(11, 11, 11)
		                            .addComponent(lbThietLapChung, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)))
		            .addGap(10, 10, 10)
		            .addComponent(PanelTrans8, javax.swing.GroupLayout.DEFAULT_SIZE, 250,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		           .addGap(5, 5, 5)
		            
		            .addGroup(
		                PanelTrans7Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(btnDongY4, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(btnThoat4, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap()));

		JLabel lbl1 = new JLabel("");
		JLabel lbl2 = new JLabel("");
		JLabel lblHkt3 = new JLabel("HKT Sofware");
		lblHkt3.setFont(new java.awt.Font("Tahoma", 1, 16));
		lblHkt3.setForeground(new java.awt.Color(126, 0, 0));
		lblHkt3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

		javax.swing.GroupLayout thisLayout4 = new javax.swing.GroupLayout(panelBackground4);
		panelBackground4.setLayout(thisLayout4);
		thisLayout4
		    .setHorizontalGroup(thisLayout4
		        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		        .addGroup(
		            thisLayout4
		                .createSequentialGroup()
		                .addComponent(lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
		                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addGap(4, 4, 4)
		                .addComponent(PanelTrans7, javax.swing.GroupLayout.PREFERRED_SIZE, 592,
		                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addGap(4, 4, 4)
		                .addComponent(lbl2, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
		                    javax.swing.GroupLayout.PREFERRED_SIZE))
		        .addGroup(
		            javax.swing.GroupLayout.Alignment.TRAILING,
		            thisLayout4
		                .createSequentialGroup()
		                .addContainerGap(481, javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addComponent(lblHkt3, javax.swing.GroupLayout.PREFERRED_SIZE, 150,
		                    javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()));
		thisLayout4.setVerticalGroup(thisLayout4.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		    thisLayout4
		        .createSequentialGroup()
		        .addComponent(lblHkt3)
		        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		        .addGroup(
		            thisLayout4
		                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                .addGroup(
		                    thisLayout4
		                        .createSequentialGroup()
		                        .addGap(74, 74, 74)
		                        .addComponent(lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
		                            javax.swing.GroupLayout.PREFERRED_SIZE))
		                .addComponent(PanelTrans7, javax.swing.GroupLayout.PREFERRED_SIZE, 386,
		                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addGroup(
		                    thisLayout4
		                        .createSequentialGroup()
		                        .addGap(73, 73, 73)
		                        .addComponent(lbl2, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
		                            javax.swing.GroupLayout.PREFERRED_SIZE)))));

		jTabbedPane1.addTab("Thiết lập chung", panelBackground4);

		PanelTrans3.setMinimumSize(new java.awt.Dimension(593, 387));
		PanelTrans3.setOpaque(false);
		PanelTrans3.setPreferredSize(new java.awt.Dimension(593, 387));

		btnThoat2.setFont(new java.awt.Font("Tahoma", 1, 14));
		btnThoat2.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/iconBack.png"))); // NOI18N
		btnThoat2.setText("Thoát"); // NOI18N
		btnThoat2.setIconTextGap(10);
		btnThoat2.addMouseListener(new MouseEventClickButtonDialog("iconBack.png", "iconBack.png", "iconBack.png"));
		btnThoat2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnThoat2ActionPerformed(evt);
			}
		});

		btnDongY2.setFont(new java.awt.Font("Tahoma", 1, 14));
		btnDongY2.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/iconOk.png"))); // NOI18N
		btnDongY2.setText("Đồng ý"); // NOI18N
		btnDongY2.setIconTextGap(4);
		btnDongY2.setPreferredSize(new java.awt.Dimension(109, 35));
		btnDongY2.addMouseListener(new MouseEventClickButtonDialog("iconOk.png", "iconOk.png", "iconOk.png"));
		btnDongY2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDongY2ActionPerformed(evt);
			}
		});

		PanelTrans4.setOpaque(false);

		chbMuaHo.setFont(new java.awt.Font("Tahoma", 0, 14));
		chbMuaHo.setText("Mua hộ sản phẩm"); // NOI18N
		chbMuaHo.setOpaque(false);

		chbXoamon.setFont(new java.awt.Font("Tahoma", 0, 14));
		chbXoamon.setSelected(true);
		chbXoamon.setText("Xóa sản phẩm đã gọi"); // NOI18N
		chbXoamon.setOpaque(false);

		chbHuymon.setFont(new java.awt.Font("Tahoma", 0, 14));
		chbHuymon.setSelected(true);
		chbHuymon.setText("Hủy sản phẩm đã gọi"); // NOI18N
		chbHuymon.setOpaque(false);

		chbSoluong.setFont(new java.awt.Font("Tahoma", 0, 14));
		chbSoluong.setSelected(true);
		chbSoluong.setText("Thay đổi số lượng"); // NOI18N
		chbSoluong.setOpaque(false);
		chbSoluong.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				chbSoluongItemStateChanged(evt);
			}
		});

		chbDatgio.setFont(new java.awt.Font("Tahoma", 0, 14));
		chbDatgio.setText("Đặt giờ"); // NOI18N
		chbDatgio.setMargin(new java.awt.Insets(0, 0, 0, 0));
		chbDatgio.setOpaque(false);

		chbTraSau.setFont(new java.awt.Font("Tahoma", 0, 14));
		chbTraSau.setSelected(true);
		chbTraSau.setText("Trả sau"); // NOI18N
		chbTraSau.setOpaque(false);

		chbDoiGia.setFont(new java.awt.Font("Tahoma", 0, 14));
		chbDoiGia.setText("Đổi giá sản phẩm"); // NOI18N
		chbDoiGia.setOpaque(false);
		chbDoiGia.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				chbDoiGiaItemStateChanged(evt);
			}
		});

		chbInCBNSP.setFont(new java.awt.Font("Tahoma", 0, 14));
		chbInCBNSP.setText("Sửa giờ hóa đơn"); // NOI18N
		chbInCBNSP.setOpaque(false);
		chbInCBNSP.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {

			}
		});

		chbInCheBien.setFont(new java.awt.Font("Tahoma", 0, 14));
		chbInCheBien.setSelected(true);
		chbInCheBien.setText("In chế biến"); // NOI18N
		chbInCheBien.setOpaque(false);
		chbInCheBien.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				chbInCheBienItemStateChanged(evt);
			}
		});

		chbInHuyMon.setFont(new java.awt.Font("Tahoma", 0, 14));
		chbInHuyMon.setSelected(true);
		chbInHuyMon.setText("In hủy món"); // NOI18N
		chbInHuyMon.setOpaque(false);

		chbThanhToanLe.setFont(new java.awt.Font("Tahoma", 0, 14));
		chbThanhToanLe.setSelected(true);
		chbThanhToanLe.setText("Thanh toán lẻ"); // NOI18N
		chbThanhToanLe.setOpaque(false);

		chbCTKM.setFont(new java.awt.Font("Tahoma", 0, 14));
		chbCTKM.setText("Chương trình KM"); // NOI18N
		chbCTKM.setOpaque(false);

		chbPaymen.setFont(new java.awt.Font("Tahoma", 0, 14));
		chbPaymen.setSelected(true);
		chbPaymen.setText("Thanh toán"); // NOI18N
		chbPaymen.setOpaque(false);
		chbPaymen.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				chbPaymenItemStateChanged(evt);
			}
		});

		chbPrintReceip.setFont(new java.awt.Font("Tahoma", 0, 14));
		chbPrintReceip.setSelected(true);
		chbPrintReceip.setText("In hóa đơn"); // NOI18N
		chbPrintReceip.setOpaque(false);
		chbPrintReceip.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				chbPrintReceipItemStateChanged(evt);
			}
		});

		buttonGroup3.add(rbtPaymenNow);
		rbtPaymenNow.setFont(new java.awt.Font("Tahoma", 0, 14));
		rbtPaymenNow.setText("Thanh toán luôn"); // NOI18N
		rbtPaymenNow.setOpaque(false);

		buttonGroup3.add(rbtPaymenView);
		rbtPaymenView.setFont(new java.awt.Font("Tahoma", 0, 14));
		rbtPaymenView.setSelected(true);
		rbtPaymenView.setText("Hiển thị giao diện thanh toán"); // NOI18N
		rbtPaymenView.setOpaque(false);

		buttonGroup4.add(rbtPrintNow);
		rbtPrintNow.setFont(new java.awt.Font("Tahoma", 0, 14));
		rbtPrintNow.setText("In luôn hóa đơn"); // NOI18N
		rbtPrintNow.setOpaque(false);

		buttonGroup4.add(rbtPrintView);
		rbtPrintView.setFont(new java.awt.Font("Tahoma", 0, 14));
		rbtPrintView.setSelected(true);
		rbtPrintView.setText("Cho phép chọn bản sao, bản chính"); // NOI18N
		rbtPrintView.setOpaque(false);

		chbSuaSL.setFont(new java.awt.Font("Tahoma", 0, 14));
		chbSuaSL.setText("Sửa SL trực tiếp"); // NOI18N
		chbSuaSL.setOpaque(false);

		chbSuagia.setFont(new java.awt.Font("Tahoma", 0, 14));
		chbSuagia.setText("Sửa giá trực tiếp"); // NOI18N
		chbSuagia.setOpaque(false);

		jLabel36.setBackground(new java.awt.Color(204, 0, 0));
		jLabel36.setText(""); // NOI18N
		jLabel36.setOpaque(true);

		chbTaoSP.setFont(new java.awt.Font("Tahoma", 0, 14));
		chbTaoSP.setSelected(true);
		chbTaoSP.setText("Tạo mới, xóa SP"); // NOI18N
		chbTaoSP.setOpaque(false);

		chbThietLapBan.setFont(new java.awt.Font("Tahoma", 0, 14));
		chbThietLapBan.setText("Thiết lập bán hàng"); // NOI18N
		chbThietLapBan.setOpaque(false);
		chbThietLapBan.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				// chbThietLapBanItemStateChanged(evt);
			}
		});

		chbSLBP.setFont(new java.awt.Font("Tahoma", 0, 14));
		chbSLBP.setSelected(true);
		chbSLBP.setText("Đánh SL từ bàn phím"); // NOI18N
		chbSLBP.setOpaque(false);

		chbThue.setFont(new java.awt.Font("Tahoma", 0, 14));
		chbThue.setSelected(true);
		chbThue.setText("Thuế"); // NOI18N
		chbThue.setOpaque(false);

		chbChietKhau.setFont(new java.awt.Font("Tahoma", 0, 14));
		chbChietKhau.setSelected(true);
		chbChietKhau.setText("CK"); // NOI18N
		chbChietKhau.setMargin(new java.awt.Insets(0, 0, 0, 0));
		chbChietKhau.setOpaque(false);

		chbDichVu.setFont(new java.awt.Font("Tahoma", 0, 14));
		chbDichVu.setText("Dịch vụ"); // NOI18N
		chbDichVu.setOpaque(false);

		javax.swing.GroupLayout PanelTrans4Layout = new javax.swing.GroupLayout(PanelTrans4);
		PanelTrans4.setLayout(PanelTrans4Layout);
		PanelTrans4Layout.setHorizontalGroup(PanelTrans4Layout.createParallelGroup(
		    javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		    PanelTrans4Layout
		        .createSequentialGroup()
		        .addGroup(
		            PanelTrans4Layout
		                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                .addGroup(
		                    PanelTrans4Layout
		                        .createSequentialGroup()
		                        .addGap(32, 32, 32)
		                        .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 493,
		                            javax.swing.GroupLayout.PREFERRED_SIZE))
		                .addGroup(
		                    PanelTrans4Layout
		                        .createSequentialGroup()
		                        .addGap(32, 32, 32)
		                        .addComponent(chbThanhToanLe, javax.swing.GroupLayout.PREFERRED_SIZE, 160,
		                            javax.swing.GroupLayout.PREFERRED_SIZE)
		                        .addGap(2, 2, 2)
		                        .addComponent(chbSoluong, javax.swing.GroupLayout.PREFERRED_SIZE, 160,
		                            javax.swing.GroupLayout.PREFERRED_SIZE)
		                        .addGap(2, 2, 2)
		                        .addComponent(chbThietLapBan, javax.swing.GroupLayout.PREFERRED_SIZE, 160,
		                            javax.swing.GroupLayout.PREFERRED_SIZE))
		                .addGroup(
		                    PanelTrans4Layout
		                        .createSequentialGroup()
		                        .addGap(32, 32, 32)
		                        .addComponent(chbTraSau, javax.swing.GroupLayout.PREFERRED_SIZE, 100,
		                            javax.swing.GroupLayout.PREFERRED_SIZE)
		                        .addGap(62, 62, 62)
		                        .addComponent(chbHuymon, javax.swing.GroupLayout.PREFERRED_SIZE, 160,
		                            javax.swing.GroupLayout.PREFERRED_SIZE).addGap(2, 2, 2).addComponent(chbTaoSP))
		                .addGroup(
		                    PanelTrans4Layout
		                        .createSequentialGroup()
		                        .addGap(32, 32, 32)
		                        .addComponent(chbSuagia, javax.swing.GroupLayout.PREFERRED_SIZE, 150,
		                            javax.swing.GroupLayout.PREFERRED_SIZE)
		                        .addGap(12, 12, 12)
		                        .addComponent(chbXoamon, javax.swing.GroupLayout.PREFERRED_SIZE, 160,
		                            javax.swing.GroupLayout.PREFERRED_SIZE)
		                        .addGap(2, 2, 2)
		                        .addComponent(chbCTKM, javax.swing.GroupLayout.PREFERRED_SIZE, 160,
		                            javax.swing.GroupLayout.PREFERRED_SIZE))
		                .addGroup(
		                    PanelTrans4Layout
		                        .createSequentialGroup()
		                        .addGap(32, 32, 32)
		                        .addComponent(chbDoiGia, javax.swing.GroupLayout.PREFERRED_SIZE, 140,
		                            javax.swing.GroupLayout.PREFERRED_SIZE)
		                        .addGap(22, 22, 22)
		                        .addComponent(chbSuaSL, javax.swing.GroupLayout.PREFERRED_SIZE, 160,
		                            javax.swing.GroupLayout.PREFERRED_SIZE)
		                        .addGap(2, 2, 2)
		                        .addComponent(chbMuaHo, javax.swing.GroupLayout.PREFERRED_SIZE, 160,
		                            javax.swing.GroupLayout.PREFERRED_SIZE))
		                .addGroup(
		                    PanelTrans4Layout
		                        .createSequentialGroup()
		                        .addGap(32, 32, 32)
		                        .addComponent(chbPaymen, javax.swing.GroupLayout.PREFERRED_SIZE, 160,
		                            javax.swing.GroupLayout.PREFERRED_SIZE))
		                .addGroup(
		                    PanelTrans4Layout
		                        .createSequentialGroup()
		                        .addGap(79, 79, 79)
		                        .addComponent(rbtPaymenNow, javax.swing.GroupLayout.PREFERRED_SIZE, 143,
		                            javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(rbtPaymenView))
		                .addGroup(
		                    PanelTrans4Layout
		                        .createSequentialGroup()
		                        .addGap(32, 32, 32)
		                        .addComponent(chbPrintReceip, javax.swing.GroupLayout.PREFERRED_SIZE, 182,
		                            javax.swing.GroupLayout.PREFERRED_SIZE))
		                .addGroup(
		                    PanelTrans4Layout
		                        .createSequentialGroup()
		                        .addGap(78, 78, 78)
		                        .addComponent(rbtPrintNow, javax.swing.GroupLayout.PREFERRED_SIZE, 144,
		                            javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(rbtPrintView))
		                .addGroup(
		                    PanelTrans4Layout
		                        .createSequentialGroup()
		                        .addGap(32, 32, 32)
		                        .addGroup(
		                            PanelTrans4Layout
		                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                                .addGroup(
		                                    PanelTrans4Layout
		                                        .createSequentialGroup()
		                                        .addComponent(chbInHuyMon)
		                                        .addGap(65, 65, 65)
		                                        .addComponent(chbInCBNSP, javax.swing.GroupLayout.PREFERRED_SIZE, 160,
		                                            javax.swing.GroupLayout.PREFERRED_SIZE))
		                                .addGroup(
		                                    PanelTrans4Layout
		                                        .createSequentialGroup()
		                                        .addComponent(chbInCheBien, javax.swing.GroupLayout.PREFERRED_SIZE, 160,
		                                            javax.swing.GroupLayout.PREFERRED_SIZE)
		                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		                                        .addComponent(chbSLBP, javax.swing.GroupLayout.PREFERRED_SIZE, 162,
		                                            javax.swing.GroupLayout.PREFERRED_SIZE)))
		                        .addGap(2, 2, 2)
		                        .addGroup(
		                            PanelTrans4Layout
		                                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
		                                .addGroup(
		                                    javax.swing.GroupLayout.Alignment.LEADING,
		                                    PanelTrans4Layout
		                                        .createSequentialGroup()
		                                        .addComponent(chbDatgio)
		                                        .addGap(18, 18, 18)
		                                        .addComponent(chbThue, javax.swing.GroupLayout.PREFERRED_SIZE, 81,
		                                            javax.swing.GroupLayout.PREFERRED_SIZE))
		                                .addGroup(
		                                    javax.swing.GroupLayout.Alignment.LEADING,
		                                    PanelTrans4Layout
		                                        .createSequentialGroup()
		                                        .addComponent(chbChietKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 81,
		                                            javax.swing.GroupLayout.PREFERRED_SIZE)
		                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
		                                            javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                                        .addComponent(chbDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, 81,
		                                            javax.swing.GroupLayout.PREFERRED_SIZE)))))
		        .addContainerGap(17, javax.swing.GroupLayout.PREFERRED_SIZE)));
		PanelTrans4Layout.setVerticalGroup(PanelTrans4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        PanelTrans4Layout
		            .createSequentialGroup()
		            .addGap(7, 7, 7)
		            .addGroup(
		                PanelTrans4Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(chbInHuyMon)
		                    .addGroup(
		                        PanelTrans4Layout
		                            .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		                            .addComponent(chbInCBNSP)
		                            .addComponent(chbDatgio, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                                javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(chbThue)))
		            .addGap(3, 3, 3)
		            .addGroup(
		                PanelTrans4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		                    .addComponent(chbInCheBien).addComponent(chbSLBP).addComponent(chbChietKhau)
		                    .addComponent(chbDichVu))
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 2,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(7, 7, 7)
		            .addGroup(
		                PanelTrans4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(chbThanhToanLe).addComponent(chbSoluong).addComponent(chbThietLapBan))
		            .addGroup(
		                PanelTrans4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(chbTraSau).addComponent(chbHuymon).addComponent(chbTaoSP))
		            .addGroup(
		                PanelTrans4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(chbSuagia).addComponent(chbXoamon).addComponent(chbCTKM))
		            .addGroup(
		                PanelTrans4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(chbDoiGia).addComponent(chbSuaSL).addComponent(chbMuaHo))
		            .addGap(3, 3, 3)
		            .addComponent(chbPaymen)
		            .addGroup(
		                PanelTrans4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(rbtPaymenNow).addComponent(rbtPaymenView))
		            .addComponent(chbPrintReceip)
		            .addGroup(
		                PanelTrans4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(rbtPrintNow).addComponent(rbtPrintView))));

		lbThietLapGiaoDien.setFont(new java.awt.Font("Tahoma", 1, 14));
		lbThietLapGiaoDien.setForeground(new java.awt.Color(126, 0, 0));
		lbThietLapGiaoDien.setText("Thiết lập giao diện "); // NOI18N

		jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		jLabel13.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/description.png"))); // NOI18N
		jLabel13.setText(""); // NOI18N
		jLabel13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

		javax.swing.GroupLayout PanelTrans3Layout = new javax.swing.GroupLayout(PanelTrans3);
		PanelTrans3.setLayout(PanelTrans3Layout);
		PanelTrans3Layout.setHorizontalGroup(PanelTrans3Layout.createParallelGroup(
		    javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		    PanelTrans3Layout
		        .createSequentialGroup()
		        .addGap(0, 1, Short.MAX_VALUE)
		        .addGroup(
		            PanelTrans3Layout
		                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                .addGroup(
		                    PanelTrans3Layout
		                        .createSequentialGroup()
		                        .addGap(27, 27, 27)
		                        .addGroup(
		                            PanelTrans3Layout
		                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
		                                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                                .addGroup(
		                                    PanelTrans3Layout.createSequentialGroup().addGap(39, 39, 39)
		                                        .addComponent(lbThietLapGiaoDien))))
		                .addGroup(
		                    PanelTrans3Layout
		                        .createSequentialGroup()
		                        .addGap(20, 20, 20)
		                        .addGroup(
		                            PanelTrans3Layout
		                                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
		                                .addGroup(
		                                    PanelTrans3Layout
		                                        .createSequentialGroup()
		                                        .addComponent(btnDongY2, javax.swing.GroupLayout.PREFERRED_SIZE, 118,
		                                            javax.swing.GroupLayout.PREFERRED_SIZE)
		                                        .addGap(10, 10, 10)
		                                        .addComponent(btnThoat2, javax.swing.GroupLayout.PREFERRED_SIZE, 118,
		                                            javax.swing.GroupLayout.PREFERRED_SIZE))
		                                .addComponent(PanelTrans4, javax.swing.GroupLayout.PREFERRED_SIZE, 552,
		                                    javax.swing.GroupLayout.PREFERRED_SIZE)))).addGap(30, 30, 30)));
		PanelTrans3Layout.setVerticalGroup(PanelTrans3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        PanelTrans3Layout
		            .createSequentialGroup()
		            .addGroup(
		                PanelTrans3Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addGroup(
		                        PanelTrans3Layout
		                            .createSequentialGroup()
		                            .addGap(11, 11, 11)
		                            .addComponent(lbThietLapGiaoDien, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)))
		            .addGap(10, 10, 10)
		            .addComponent(PanelTrans4, javax.swing.GroupLayout.DEFAULT_SIZE, 290,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(5, 5, 5)
		            .addGroup(
		                PanelTrans3Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(btnDongY2, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(btnThoat2, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap()));

		lbHKT1.setFont(new java.awt.Font("Tahoma", 1, 16));
		lbHKT1.setForeground(new java.awt.Color(126, 0, 0));
		lbHKT1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lbHKT1.setText("HKT Sofware"); // NOI18N

		jLabel15.setText(""); // NOI18N
		jLabel16.setText(""); // NOI18N

		javax.swing.GroupLayout thisLayout = new javax.swing.GroupLayout(panelBackground2);
		panelBackground2.setLayout(thisLayout);
		thisLayout.setHorizontalGroup(thisLayout
		    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        thisLayout
		            .createSequentialGroup()
		            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(4, 4, 4)
		            .addComponent(PanelTrans3, javax.swing.GroupLayout.PREFERRED_SIZE, 592,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(4, 4, 4)
		            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
		                javax.swing.GroupLayout.PREFERRED_SIZE))
		    .addGroup(
		        javax.swing.GroupLayout.Alignment.TRAILING,
		        thisLayout
		            .createSequentialGroup()
		            .addContainerGap(481, javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addComponent(lbHKT1, javax.swing.GroupLayout.PREFERRED_SIZE, 150,
		                javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()));
		thisLayout.setVerticalGroup(thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		    thisLayout
		        .createSequentialGroup()
		        .addComponent(lbHKT1)
		        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		        .addGroup(
		            thisLayout
		                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                .addGroup(
		                    thisLayout
		                        .createSequentialGroup()
		                        .addGap(74, 74, 74)
		                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
		                            javax.swing.GroupLayout.PREFERRED_SIZE))
		                .addComponent(PanelTrans3, javax.swing.GroupLayout.PREFERRED_SIZE, 386,
		                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addGroup(
		                    thisLayout
		                        .createSequentialGroup()
		                        .addGap(73, 73, 73)
		                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
		                            javax.swing.GroupLayout.PREFERRED_SIZE)))));

		jTabbedPane1.addTab("Thiết lập giao diện ", panelBackground2); // NOI18N

		btnThoat3.setFont(new java.awt.Font("Tahoma", 1, 14));
		btnThoat3.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/iconBack.png"))); // NOI18N
		btnThoat3.setText("Thoát"); // NOI18N
		btnThoat3.setIconTextGap(10);
		btnThoat3.addMouseListener(new MouseEventClickButtonDialog("iconBack.png", "iconBack.png", "iconBack.png"));
		btnThoat3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnThoat3ActionPerformed(evt);
			}
		});

		btnDongY3.setFont(new java.awt.Font("Tahoma", 1, 14));
		btnDongY3.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/iconOk.png"))); // NOI18N
		btnDongY3.setText("Đồng ý"); // NOI18N
		btnDongY3.setIconTextGap(4);
		btnDongY3.setPreferredSize(new java.awt.Dimension(109, 35));
		btnDongY3.addMouseListener(new MouseEventClickButtonDialog("iconOk.png", "iconOk.png", "iconOk.png"));
		btnDongY3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDongY3ActionPerformed(evt);
			}
		});

		btnThoat4.setFont(new java.awt.Font("Tahoma", 1, 14));
		btnThoat4.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/iconBack.png")));
		btnThoat4.setText("Thoát");
		btnThoat4.setIconTextGap(10);
		btnThoat4.addMouseListener(new MouseEventClickButtonDialog("iconBack.png", "iconBack.png", "iconBack.png"));
		btnThoat4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnThoat3ActionPerformed(evt);
			}
		});

		btnDongY4.setFont(new java.awt.Font("Tahoma", 1, 14));
		btnDongY4.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/iconOk.png")));
		btnDongY4.setText("Đồng ý");
		btnDongY4.setIconTextGap(4);
		btnDongY4.setPreferredSize(new java.awt.Dimension(109, 35));
		btnDongY4.addMouseListener(new MouseEventClickButtonDialog("iconOk.png", "iconOk.png", "iconOk.png"));
		btnDongY4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDongY3ActionPerformed(evt);
			}
		});

		chbArea.setFont(new java.awt.Font("Tahoma", 0, 14));
		chbArea.setText("  Kích hoạt phân quyền khu vực"); // NOI18N
		chbArea.setOpaque(false);

		chbArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					panelInstituteLocation = new PanelInstituteLocation();
					DialogResto dialog = new DialogResto(panelInstituteLocation, false, 600, 350);
					panelInstituteLocation.setName("panelInstituteLocation");
					dialog.setName("panelInstituteLocation");
					dialog.setTitle("Thiết lập khu vực");
					// dialog.setSize(700, 500);
					panelInstituteLocation.setParentForm(iDialogResto);
					dialog.setModal(true);
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		chbArea.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				chbAreaItemStateChanged(evt);
			}
		});

		txtArea.setColumns(20);
		txtArea.setEditable(false);
		txtArea.setFont(new java.awt.Font("Monospaced", 0, 16));
		txtArea.setRows(5);
		txtArea.setText("Khi chức năng phân quyền khu vực được kích \n"
		    + "hoạt, một tài khoản chỉ vào được các khu vực \n " + "mà tài khoản đó đã được phân quyền."); // NOI18N
		txtArea.setOpaque(false);

		buttonGroup5.add(rbtSingleton);
		rbtSingleton.setFont(new java.awt.Font("Tahoma", 0, 14));
		rbtSingleton.setText("Chỉ vào 1 khu vực"); // NOI18N
		rbtSingleton.setEnabled(false);
		rbtSingleton.setOpaque(false);

		buttonGroup5.add(rbtAll);
		rbtAll.setFont(new java.awt.Font("Tahoma", 0, 14));
		rbtAll.setText("Vào nhiều khu vực"); // NOI18N
		rbtAll.setEnabled(false);
		rbtAll.setOpaque(false);

		chbPricingTableArea.setFont(new java.awt.Font("Tahoma", 0, 14));
		chbPricingTableArea.setText("  Kích hoạt bảng giá khu vực"); // NOI18N
		chbPricingTableArea.setOpaque(false);
		chbPricingTableArea.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				chbPricingTableAreaItemStateChanged(evt);
			}
		});

		btnPricingTable.setFont(new java.awt.Font("Tahoma", 1, 12));
		btnPricingTable.setText("Thiết lập bảng giá cho khu vực"); // NOI18N
		btnPricingTable.setEnabled(false);
		btnPricingTable.addMouseListener(new MouseEventClickButtonDialog(null, null, null));
		btnPricingTable.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				PanelPriceLocation panelPriceLocation = new PanelPriceLocation();
				DialogResto dialog = new DialogResto(panelPriceLocation, false, 200, 300);
				dialog.setTitle("Thiết lập bảng giá khu vực");
				// dialog.setSize(580, 470);
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
			}
		});

		jLabel37.setText(""); // NOI18N
		jLabel38.setText(""); // NOI18N

		PanelTrans6.setOpaque(false);
		javax.swing.GroupLayout PanelTrans6Layout = new javax.swing.GroupLayout(PanelTrans6);
		PanelTrans6.setLayout(PanelTrans6Layout);
		PanelTrans6Layout.setHorizontalGroup(PanelTrans6Layout.createParallelGroup(
		    javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		    PanelTrans6Layout
		        .createSequentialGroup()
		        .addGroup(
		            PanelTrans6Layout
		                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                .addGroup(
		                    PanelTrans6Layout
		                        .createSequentialGroup()
		                        .addGap(36, 36, 36)
		                        .addGroup(
		                            PanelTrans6Layout
		                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                                .addGroup(
		                                    PanelTrans6Layout
		                                        .createSequentialGroup()
		                                        .addGap(43, 43, 43)
		                                        .addComponent(rbtSingleton, javax.swing.GroupLayout.PREFERRED_SIZE, 187,
		                                            javax.swing.GroupLayout.PREFERRED_SIZE)
		                                        .addGap(18, 18, 18)
		                                        .addComponent(rbtAll, javax.swing.GroupLayout.PREFERRED_SIZE, 171,
		                                            javax.swing.GroupLayout.PREFERRED_SIZE))
		                                .addComponent(chbArea, javax.swing.GroupLayout.PREFERRED_SIZE, 329,
		                                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                                .addComponent(chbPricingTableArea, javax.swing.GroupLayout.PREFERRED_SIZE, 329,
		                                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                                .addComponent(txtArea, javax.swing.GroupLayout.PREFERRED_SIZE, 452,
		                                    javax.swing.GroupLayout.PREFERRED_SIZE)))
		                .addGroup(
		                    PanelTrans6Layout.createSequentialGroup().addGap(89, 89, 89).addComponent(btnPricingTable))
		                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 486,
		                    javax.swing.GroupLayout.PREFERRED_SIZE))
		        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel38)
		        .addContainerGap(39, Short.MAX_VALUE)));
		PanelTrans6Layout.setVerticalGroup(PanelTrans6Layout
		    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        PanelTrans6Layout
		            .createSequentialGroup()
		            .addGap(11, 11, 11)
		            .addComponent(chbArea)
		            .addGap(18, 18, 18)
		            .addGroup(
		                PanelTrans6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		                    .addComponent(rbtSingleton).addComponent(rbtAll))
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
		            .addComponent(txtArea, javax.swing.GroupLayout.PREFERRED_SIZE, 70,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(18, 18, 18)
		            .addComponent(chbPricingTableArea)
		            .addGap(18, 18, 18)
		            .addComponent(btnPricingTable, javax.swing.GroupLayout.PREFERRED_SIZE, 31,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel37))
		    .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE));

		lbPhanQuyenKhuVuc.setFont(new java.awt.Font("Tahoma", 1, 14));
		lbPhanQuyenKhuVuc.setForeground(new java.awt.Color(126, 0, 0));
		lbPhanQuyenKhuVuc.setText("Phân quyền khu vực"); // NOI18N

		jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		jLabel22.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/description.png"))); // NOI18N
		jLabel22.setText(""); // NOI18N
		jLabel22.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

		PanelTrans5.setOpaque(false);
		javax.swing.GroupLayout PanelTrans5Layout = new javax.swing.GroupLayout(PanelTrans5);
		PanelTrans5.setLayout(PanelTrans5Layout);
		PanelTrans5Layout.setHorizontalGroup(PanelTrans5Layout.createParallelGroup(
		    javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		    PanelTrans5Layout
		        .createSequentialGroup()
		        .addGroup(
		            PanelTrans5Layout
		                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                .addGroup(
		                    PanelTrans5Layout
		                        .createSequentialGroup()
		                        .addGap(27, 27, 27)
		                        .addGroup(
		                            PanelTrans5Layout
		                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
		                                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                                .addGroup(
		                                    PanelTrans5Layout.createSequentialGroup().addGap(39, 39, 39)
		                                        .addComponent(lbPhanQuyenKhuVuc))))
		                .addGroup(
		                    PanelTrans5Layout
		                        .createSequentialGroup()
		                        .addGap(20, 20, 20)
		                        .addGroup(
		                            PanelTrans5Layout
		                                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
		                                .addGroup(
		                                    PanelTrans5Layout
		                                        .createSequentialGroup()
		                                        .addComponent(btnDongY3, javax.swing.GroupLayout.PREFERRED_SIZE, 118,
		                                            javax.swing.GroupLayout.PREFERRED_SIZE)
		                                        .addGap(10, 10, 10)
		                                        .addComponent(btnThoat3, javax.swing.GroupLayout.PREFERRED_SIZE, 118,
		                                            javax.swing.GroupLayout.PREFERRED_SIZE))
		                                .addComponent(PanelTrans6, javax.swing.GroupLayout.PREFERRED_SIZE, 552,
		                                    javax.swing.GroupLayout.PREFERRED_SIZE)))).addGap(30, 30, 30)));
		PanelTrans5Layout.setVerticalGroup(PanelTrans5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        PanelTrans5Layout
		            .createSequentialGroup()
		            .addGroup(
		                PanelTrans5Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addGroup(
		                        PanelTrans5Layout
		                            .createSequentialGroup()
		                            .addGap(11, 11, 11)
		                            .addComponent(lbPhanQuyenKhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)))
		            .addGap(10, 10, 10)
		            .addComponent(PanelTrans6, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
		            .addGap(0, 0, 0)
		            .addGroup(
		                PanelTrans5Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(btnDongY3, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(btnThoat3, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap()));

		lbHKT3.setFont(new java.awt.Font("Tahoma", 1, 16));
		lbHKT3.setForeground(new java.awt.Color(126, 0, 0));
		lbHKT3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lbHKT3.setText("HKT Sofware"); // NOI18N

		jLabel24.setText(""); // NOI18N

		jLabel25.setText(""); // NOI18N

		javax.swing.GroupLayout thisLayout3 = new javax.swing.GroupLayout(panelBackground3);
		panelBackground3.setLayout(thisLayout3);
		thisLayout3.setHorizontalGroup(thisLayout3
		    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        thisLayout3
		            .createSequentialGroup()
		            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(4, 4, 4)
		            .addComponent(PanelTrans5, javax.swing.GroupLayout.PREFERRED_SIZE, 592,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(4, 4, 4)
		            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
		                javax.swing.GroupLayout.PREFERRED_SIZE))
		    .addGroup(
		        javax.swing.GroupLayout.Alignment.TRAILING,
		        thisLayout3
		            .createSequentialGroup()
		            .addContainerGap(481, Short.MAX_VALUE)
		            .addComponent(lbHKT3, javax.swing.GroupLayout.PREFERRED_SIZE, 150,
		                javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()));
		thisLayout3.setVerticalGroup(thisLayout3.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		    thisLayout3
		        .createSequentialGroup()
		        .addComponent(lbHKT3)
		        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		        .addGroup(
		            thisLayout3
		                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                .addGroup(
		                    thisLayout3
		                        .createSequentialGroup()
		                        .addGap(74, 74, 74)
		                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
		                            javax.swing.GroupLayout.PREFERRED_SIZE))
		                .addComponent(PanelTrans5, javax.swing.GroupLayout.PREFERRED_SIZE, 386,
		                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addGroup(
		                    thisLayout3
		                        .createSequentialGroup()
		                        .addGap(73, 73, 73)
		                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
		                            javax.swing.GroupLayout.PREFERRED_SIZE)))));

		jTabbedPane1.addTab("Thiết lập KV", panelBackground3); // NOI18N

		PanelTrans9.setOpaque(false);
		PanelTrans9.setPreferredSize(new java.awt.Dimension(593, 387));

		btnThoat5.setFont(new java.awt.Font("Tahoma", 1, 14));
		btnThoat5.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/iconBack.png"))); // NOI18N
		btnThoat5.setText("Thoát"); // NOI18N
		btnThoat5.setIconTextGap(10);
		btnThoat5.addMouseListener(new MouseEventClickButtonDialog("iconBack.png", "iconBack.png", "iconBack.png"));
		btnThoat5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnThoat5ActionPerformed(evt);
			}
		});

		btnDongY5.setFont(new java.awt.Font("Tahoma", 1, 14));
		btnDongY5.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/iconOk.png"))); // NOI18N
		btnDongY5.setText("Đồng ý"); // NOI18N
		btnDongY5.setIconTextGap(4);
		btnDongY5.setPreferredSize(new java.awt.Dimension(109, 35));
		btnDongY5.addMouseListener(new MouseEventClickButtonDialog("iconOk.png", "iconOk.png", "iconOk.png"));
		btnDongY5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDongY5ActionPerformed(evt);
			}
		});

		PanelTrans10.setOpaque(false);

		jPanel3.setOpaque(false);
		jPanel3.setPreferredSize(new java.awt.Dimension(543, 283));

		lbGiaoDienSuDung.setFont(new java.awt.Font("Tahoma", 1, 14));
		lbGiaoDienSuDung.setText("Giao diện sử dụng"); // NOI18N

		buttonGroup6.add(rbtPoss);
		rbtPoss.setFont(new java.awt.Font("Tahoma", 0, 14));
		rbtPoss.setSelected(true);
		rbtPoss.setText("Giao diện pos"); // NOI18N
		rbtPoss.setOpaque(false);

		buttonGroup6.add(rbtThuong);
		rbtThuong.setFont(new java.awt.Font("Tahoma", 0, 14));
		rbtThuong.setText("Giao diện thường"); // NOI18N
		rbtThuong.setOpaque(false);

		lbLoaiHinhBanHang.setFont(new java.awt.Font("Tahoma", 1, 14));
		lbLoaiHinhBanHang.setText("Loại hình bán hàng"); // NOI18N

		lbHinhNen.setFont(new java.awt.Font("Tahoma", 1, 14));
		lbHinhNen.setText("Hình nền"); // NOI18N

		buttonGroup7.add(rbtResto);
		rbtResto.setFont(new java.awt.Font("Tahoma", 0, 14));
		rbtResto.setSelected(true);
		rbtResto.setText("Resto"); // NOI18N
		rbtResto.setOpaque(false);
		rbtResto.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				rbtRestoItemStateChanged(evt);
			}
		});

		buttonGroup7.add(rbtCafe);
		rbtCafe.setFont(new java.awt.Font("Tahoma", 0, 14));
		rbtCafe.setText("Cafe/Bars"); // NOI18N
		rbtCafe.setOpaque(false);
		rbtCafe.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				rbtCafeItemStateChanged(evt);
			}
		});

		buttonGroup7.add(rbtKara);
		rbtKara.setFont(new java.awt.Font("Tahoma", 0, 14));
		rbtKara.setText("Kara"); // NOI18N
		rbtKara.setOpaque(false);
		rbtKara.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				rbtKaraItemStateChanged(evt);
			}
		});

		buttonGroup7.add(rbtBia);
		rbtBia.setFont(new java.awt.Font("Tahoma", 0, 14));
		rbtBia.setText("Bi a/Billards/Khác ..."); // NOI18N
		rbtBia.setOpaque(false);
		rbtBia.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				rbtBiaItemStateChanged(evt);
			}
		});

		buttonGroup7.add(rbtMaket);
		rbtMaket.setFont(new java.awt.Font("Tahoma", 0, 14));
		rbtMaket.setText("Siêu thị"); // NOI18N
		rbtMaket.setOpaque(false);
		rbtMaket.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				rbtMaketItemStateChanged(evt);
			}
		});

		buttonGroup7.add(rbtShop);
		rbtShop.setFont(new java.awt.Font("Tahoma", 0, 14));
		rbtShop.setText("Shop"); // NOI18N
		rbtShop.setOpaque(false);
		rbtShop.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				rbtShopItemStateChanged(evt);
			}
		});

		buttonGroup7.add(rbtSpa);
		rbtSpa.setFont(new java.awt.Font("Tahoma", 0, 14));
		rbtSpa.setText("Spa/Thể hình"); // NOI18N
		rbtSpa.setOpaque(false);
		rbtSpa.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				rbtSpaItemStateChanged(evt);
			}
		});

		buttonGroup7.add(rbtTapHoa);
		rbtTapHoa.setFont(new java.awt.Font("Tahoma", 0, 14));
		rbtTapHoa.setText("Tạp hóa/Hiệu thuốc/Khác..."); // NOI18N
		rbtTapHoa.setOpaque(false);
		rbtTapHoa.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				rbtTapHoaItemStateChanged(evt);
			}
		});

		buttonGroup8.add(rbt01);
		rbt01.setFont(new java.awt.Font("Tahoma", 0, 14));
		rbt01.setText("01"); // NOI18N
		rbt01.setOpaque(false);

		buttonGroup8.add(rbt05);
		rbt05.setFont(new java.awt.Font("Tahoma", 0, 14));
		rbt05.setText("05"); // NOI18N
		rbt05.setOpaque(false);

		buttonGroup8.add(rbt02);
		rbt02.setFont(new java.awt.Font("Tahoma", 0, 14));
		rbt02.setText("02"); // NOI18N
		rbt02.setOpaque(false);

		buttonGroup8.add(rbt06);
		rbt06.setFont(new java.awt.Font("Tahoma", 0, 14));
		rbt06.setText("06"); // NOI18N
		rbt06.setOpaque(false);

		buttonGroup8.add(rbt03);
		rbt03.setFont(new java.awt.Font("Tahoma", 0, 14));
		rbt03.setText("03"); // NOI18N
		rbt03.setOpaque(false);

		buttonGroup8.add(rbt07);
		rbt07.setFont(new java.awt.Font("Tahoma", 0, 14));
		rbt07.setText("07"); // NOI18N
		rbt07.setOpaque(false);

		buttonGroup8.add(rbt04);
		rbt04.setFont(new java.awt.Font("Tahoma", 0, 14));
		rbt04.setText("04"); // NOI18N
		rbt04.setOpaque(false);

		buttonGroup8.add(rbt08);
		rbt08.setFont(new java.awt.Font("Tahoma", 0, 14));
		rbt08.setText("08"); // NOI18N
		rbt08.setOpaque(false);

		lb06.setText(""); // NOI18N
		lb06.setPreferredSize(new java.awt.Dimension(52, 23));

		lb01.setText(""); // NOI18N
		lb01.setPreferredSize(new java.awt.Dimension(52, 23));

		lb05.setText(""); // NOI18N
		lb05.setPreferredSize(new java.awt.Dimension(52, 23));

		lb02.setText(""); // NOI18N
		lb02.setPreferredSize(new java.awt.Dimension(52, 23));

		lb04.setText(""); // NOI18N
		lb04.setPreferredSize(new java.awt.Dimension(52, 23));

		lb03.setText(""); // NOI18N
		lb03.setPreferredSize(new java.awt.Dimension(52, 23));

		lb08.setText(""); // NOI18N
		lb08.setPreferredSize(new java.awt.Dimension(52, 23));

		lb07.setText(""); // NOI18N
		lb07.setPreferredSize(new java.awt.Dimension(52, 23));

		buttonGroup9.add(rbt1quay);
		rbt1quay.setFont(new java.awt.Font("Tahoma", 0, 14));
		rbt1quay.setSelected(true);
		rbt1quay.setText("Một Quầy"); // NOI18N
		rbt1quay.setEnabled(false);
		rbt1quay.setOpaque(false);

		buttonGroup9.add(rbtnhieuquay);
		rbtnhieuquay.setFont(new java.awt.Font("Tahoma", 0, 14));
		rbtnhieuquay.setText("Nhiều quầy"); // NOI18N
		rbtnhieuquay.setEnabled(false);
		rbtnhieuquay.setOpaque(false);

		buttonGroup8.add(rbtNull);
		rbtNull.setFont(new java.awt.Font("Tahoma", 0, 14));
		rbtNull.setText("không nền"); // NOI18N
		rbtNull.setOpaque(false);
		rbt09 = new JRadioButton("Tự chọn");
		rbt09.setOpaque(false);
		rbt09.setFont(new java.awt.Font("Tahoma", 0, 14));
		rbt09.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ImageIcon image = ImageTool.getInstance().getImage();
				try {
					url = ImageTool.getInstance().encodeToString(image.getImage());
				} catch (Exception e2) {
					url = null;
				}
			}
		});
		rbt09.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				JRadioButton b = (JRadioButton) e.getSource();
				if (!b.isSelected()) {
					url = null;
				}
			}
		});
		buttonGroup8.add(rbt09);

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        jPanel3Layout
		            .createSequentialGroup()
		            .addGap(10, 10, 10)
		            .addGroup(
		                jPanel3Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(lbLoaiHinhBanHang)
		                    .addGroup(
		                        jPanel3Layout
		                            .createSequentialGroup()
		                            .addComponent(rbtResto, javax.swing.GroupLayout.PREFERRED_SIZE, 105,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)
		                            .addGap(18, 18, 18)
		                            .addComponent(rbtCafe)
		                            .addGap(10, 10, 10)
		                            .addComponent(rbtKara, javax.swing.GroupLayout.PREFERRED_SIZE, 61,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)
		                            .addGap(48, 48, 48)
		                            .addComponent(rbtBia, javax.swing.GroupLayout.PREFERRED_SIZE, 167,
		                                javax.swing.GroupLayout.PREFERRED_SIZE))
		                    .addGroup(
		                        jPanel3Layout.createSequentialGroup().addComponent(rbtMaket).addGap(18, 18, 18)
		                            .addComponent(rbtShop).addGap(36, 36, 36).addComponent(rbtSpa).addGap(2, 2, 2)
		                            .addComponent(rbtTapHoa))
		                    .addGroup(
		                        jPanel3Layout
		                            .createSequentialGroup()
		                            .addComponent(rbt1quay)
		                            .addGap(38, 38, 38)
		                            .addComponent(rbtnhieuquay, javax.swing.GroupLayout.PREFERRED_SIZE, 390,
		                                javax.swing.GroupLayout.PREFERRED_SIZE))
		                    .addComponent(lbGiaoDienSuDung, javax.swing.GroupLayout.PREFERRED_SIZE, 277,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addGroup(
		                        jPanel3Layout
		                            .createSequentialGroup()
		                            .addComponent(rbtPoss, javax.swing.GroupLayout.PREFERRED_SIZE, 204,
		                                javax.swing.GroupLayout.PREFERRED_SIZE).addGap(10, 10, 10).addComponent(rbtThuong))
		                    .addComponent(lbHinhNen, javax.swing.GroupLayout.PREFERRED_SIZE, 529,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addGroup(
		                        jPanel3Layout
		                            .createSequentialGroup()
		                            .addComponent(rbt01, javax.swing.GroupLayout.PREFERRED_SIZE, 105,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)
		                            .addGap(18, 18, 18)
		                            .addComponent(rbt02, javax.swing.GroupLayout.PREFERRED_SIZE, 83,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)
		                            .addGap(10, 10, 10)
		                            .addComponent(rbt03, javax.swing.GroupLayout.PREFERRED_SIZE, 61,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)
		                            .addGap(48, 48, 48)
		                            .addComponent(rbt04, javax.swing.GroupLayout.PREFERRED_SIZE, 64,
		                                javax.swing.GroupLayout.PREFERRED_SIZE).addGap(38, 38, 38).addComponent(rbtNull))
		                    .addGroup(
		                        jPanel3Layout
		                            .createSequentialGroup()
		                            .addComponent(lb01, javax.swing.GroupLayout.PREFERRED_SIZE,
		                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                            .addGap(71, 71, 71)
		                            .addComponent(lb02, javax.swing.GroupLayout.PREFERRED_SIZE,
		                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                            .addGap(41, 41, 41)
		                            .addComponent(lb03, javax.swing.GroupLayout.PREFERRED_SIZE,
		                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                            .addGap(57, 57, 57)
		                            .addComponent(lb04, javax.swing.GroupLayout.PREFERRED_SIZE,
		                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
		                    .addGroup(
		                        jPanel3Layout
		                            .createSequentialGroup()
		                            .addComponent(rbt05, javax.swing.GroupLayout.PREFERRED_SIZE, 105,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)
		                            .addGap(18, 18, 18)
		                            .addComponent(rbt06, javax.swing.GroupLayout.PREFERRED_SIZE, 57,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)
		                            .addGap(36, 36, 36)
		                            .addComponent(rbt07, javax.swing.GroupLayout.PREFERRED_SIZE, 107,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)
		                            .addComponent(rbt08, javax.swing.GroupLayout.PREFERRED_SIZE, 107,
		                                javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(rbt09))
		                    .addGroup(
		                        jPanel3Layout
		                            .createSequentialGroup()
		                            .addComponent(lb05, javax.swing.GroupLayout.PREFERRED_SIZE,
		                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                            .addGap(71, 71, 71)
		                            .addComponent(lb06, javax.swing.GroupLayout.PREFERRED_SIZE,
		                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                            .addGap(41, 41, 41)
		                            .addComponent(lb07, javax.swing.GroupLayout.PREFERRED_SIZE,
		                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                            .addGap(57, 57, 57)
		                            .addComponent(lb08, javax.swing.GroupLayout.PREFERRED_SIZE,
		                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        jPanel3Layout
		            .createSequentialGroup()
		            .addComponent(lbLoaiHinhBanHang)
		            .addGap(7, 7, 7)
		            .addGroup(
		                jPanel3Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(rbtResto, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(rbtCafe, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(rbtKara, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(rbtBia, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                        javax.swing.GroupLayout.PREFERRED_SIZE))
		            .addGap(7, 7, 7)
		            .addGroup(
		                jPanel3Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(rbtMaket, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(rbtShop, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(rbtSpa, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(rbtTapHoa, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                        javax.swing.GroupLayout.PREFERRED_SIZE))
		            .addGap(7, 7, 7)
		            .addGroup(
		                jPanel3Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(rbt1quay, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(rbtnhieuquay, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                        javax.swing.GroupLayout.PREFERRED_SIZE))
		            .addGap(7, 7, 7)
		            .addComponent(lbGiaoDienSuDung)
		            .addGap(7, 7, 7)
		            .addGroup(
		                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(rbtPoss)
		                    .addComponent(rbtThuong))
		            .addGap(2, 2, 2)
		            .addComponent(lbHinhNen)
		            .addGap(7, 7, 7)
		            .addGroup(
		                jPanel3Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(rbt01, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(rbt02, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(rbt03, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addGroup(
		                        jPanel3Layout
		                            .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		                            .addComponent(rbt04, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)
		                            .addComponent(rbtNull, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)))
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		            .addGroup(
		                jPanel3Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(lb01, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(lb02, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(lb03, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(lb04, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
		            .addGap(2, 2, 2)
		            .addGroup(
		                jPanel3Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(rbt05, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(rbt06, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(rbt07, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(rbt08, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(rbt09, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
		                        javax.swing.GroupLayout.PREFERRED_SIZE))
		            .addGap(2, 2, 2)
		            .addGroup(
		                jPanel3Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(lb05, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(lb06, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(lb07, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(lb08, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))));

		javax.swing.GroupLayout PanelTrans10Layout = new javax.swing.GroupLayout(PanelTrans10);
		PanelTrans10.setLayout(PanelTrans10Layout);
		PanelTrans10Layout.setHorizontalGroup(PanelTrans10Layout.createParallelGroup(
		    javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 543,
		    javax.swing.GroupLayout.PREFERRED_SIZE));
		PanelTrans10Layout.setVerticalGroup(PanelTrans10Layout.createParallelGroup(
		    javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE,
		    javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE));

		lbThietLapPhienBan.setFont(new java.awt.Font("Tahoma", 1, 14));
		lbThietLapPhienBan.setForeground(new java.awt.Color(126, 0, 0));
		lbThietLapPhienBan.setText("Thiết lập phiên bản"); // NOI18N

		jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		jLabel32.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/description.png"))); // NOI18N
		jLabel32.setText(""); // NOI18N
		jLabel32.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

		javax.swing.GroupLayout PanelTrans9Layout = new javax.swing.GroupLayout(PanelTrans9);
		PanelTrans9.setLayout(PanelTrans9Layout);
		PanelTrans9Layout.setHorizontalGroup(PanelTrans9Layout.createParallelGroup(
		    javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        PanelTrans9Layout
		            .createSequentialGroup()
		            .addGroup(
		                PanelTrans9Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addGroup(
		                        PanelTrans9Layout
		                            .createSequentialGroup()
		                            .addGap(27, 27, 27)
		                            .addGroup(
		                                PanelTrans9Layout
		                                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
		                                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                                    .addGroup(
		                                        PanelTrans9Layout.createSequentialGroup().addGap(39, 39, 39)
		                                            .addComponent(lbThietLapPhienBan))))
		                    .addGroup(
		                        PanelTrans9Layout
		                            .createSequentialGroup()
		                            .addGap(20, 20, 20)
		                            .addGroup(
		                                PanelTrans9Layout
		                                    .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
		                                    .addGroup(
		                                        PanelTrans9Layout
		                                            .createSequentialGroup()
		                                            .addComponent(btnDongY5, javax.swing.GroupLayout.PREFERRED_SIZE, 118,
		                                                javax.swing.GroupLayout.PREFERRED_SIZE)
		                                            .addGap(10, 10, 10)
		                                            .addComponent(btnThoat5, javax.swing.GroupLayout.PREFERRED_SIZE, 118,
		                                                javax.swing.GroupLayout.PREFERRED_SIZE))
		                                    .addComponent(PanelTrans10, javax.swing.GroupLayout.PREFERRED_SIZE, 552,
		                                        javax.swing.GroupLayout.PREFERRED_SIZE))))
		            .addContainerGap(30, Short.MAX_VALUE)));
		PanelTrans9Layout.setVerticalGroup(PanelTrans9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        PanelTrans9Layout
		            .createSequentialGroup()
		            .addGroup(
		                PanelTrans9Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addGroup(
		                        PanelTrans9Layout
		                            .createSequentialGroup()
		                            .addGap(11, 11, 11)
		                            .addComponent(lbThietLapPhienBan, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)))
		            .addGap(7, 7, 7)
		            .addComponent(PanelTrans10, javax.swing.GroupLayout.PREFERRED_SIZE, 282, Short.MAX_VALUE)
		            
		            .addGroup(
		                PanelTrans9Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(btnDongY5, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(btnThoat5, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap()));

		lbHKT5.setFont(new java.awt.Font("Tahoma", 1, 16));
		lbHKT5.setForeground(new java.awt.Color(126, 0, 0));
		lbHKT5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lbHKT5.setText("HKT Sofware"); // NOI18N

		jLabel34.setText(""); // NOI18N

		jLabel35.setText(""); // NOI18N

		javax.swing.GroupLayout thisLayout5 = new javax.swing.GroupLayout(panelBackground5);
		panelBackground5.setLayout(thisLayout5);
		thisLayout5.setHorizontalGroup(thisLayout5
		    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        thisLayout5
		            .createSequentialGroup()
		            .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(4, 4, 4)
		            .addComponent(PanelTrans9, javax.swing.GroupLayout.PREFERRED_SIZE, 592,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(4, 4, 4)
		            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
		                javax.swing.GroupLayout.PREFERRED_SIZE))
		    .addGroup(
		        javax.swing.GroupLayout.Alignment.TRAILING,
		        thisLayout5
		            .createSequentialGroup()
		            .addContainerGap(481, Short.MAX_VALUE)
		            .addComponent(lbHKT5, javax.swing.GroupLayout.PREFERRED_SIZE, 150,
		                javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()));
		thisLayout5.setVerticalGroup(thisLayout5.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		    thisLayout5
		        .createSequentialGroup()
		        .addComponent(lbHKT5)
		        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		        .addGroup(
		            thisLayout5
		                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                .addGroup(
		                    thisLayout5
		                        .createSequentialGroup()
		                        .addGap(74, 74, 74)
		                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
		                            javax.swing.GroupLayout.PREFERRED_SIZE))
		                .addComponent(PanelTrans9, javax.swing.GroupLayout.PREFERRED_SIZE, 386,
		                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addGroup(
		                    thisLayout5
		                        .createSequentialGroup()
		                        .addGap(73, 73, 73)
		                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
		                            javax.swing.GroupLayout.PREFERRED_SIZE)))));

		jTabbedPane1.addTab("Phiên bản", panelBackground5); // NOI18N

		// jTabbedPane1.setSize(100, 100);
		this.setLayout(new GridLayout());
		this.add(jTabbedPane1);
		// getContentPane().add(panelBackground5);
		if (full) {
			getContentPane().add(jTabbedPane1);
		} else {
			setUndecorated(true);
			PanelTrans100 panelTrans = new PanelTrans100();
			panelTrans.setOpaque(false);
			getContentPane().add(panelTrans);
			panelTrans.setBackground(Color.BLACK);
			getRootPane().setOpaque(false);
			getContentPane().setBackground(new Color(0, 0, 0, 0));
			setBackground(new Color(0, 0, 0, 0));
			JLabel jLabel1 = new JLabel(" ");
			JLabel jLabel2 = new JLabel(" ");
			JLabel jLabel3 = new JLabel(" ");
			JLabel jLabel4 = new JLabel(" ");
			javax.swing.GroupLayout layout2 = new javax.swing.GroupLayout(panelTrans);
			panelTrans.setLayout(layout2);
			layout2.setHorizontalGroup(layout2
			    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 210,
			        Short.MAX_VALUE)
			    .addGroup(
			        javax.swing.GroupLayout.Alignment.TRAILING,
			        layout2
			            .createSequentialGroup()
			            .addGroup(
			                layout2
			                    .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
			                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
			                    .addGroup(
			                        layout2
			                            .createSequentialGroup()
			                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
			                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			                            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE,
			                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
			                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)))
			            .addGap(2, 2, 2)));
			layout2.setVerticalGroup(layout2.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
			    layout2
			        .createSequentialGroup()
			        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
			        .addGap(2, 2, 2)
			        .addGroup(
			            layout2
			                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
			                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
			                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE,
			                    javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
			        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)));
		}
		pack();
	}// </editor-fold>

	private void btnThoat2ActionPerformed(java.awt.event.ActionEvent evt) {
		flagChange = false;
		this.dispose();
	}

	private void btnDongY2ActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			save();
		} catch (Exception e) {
		}

	}

	private void chbPaymenItemStateChanged(java.awt.event.ItemEvent evt) {
		if (chbPaymen.isSelected()) {
			rbtPaymenNow.setEnabled(true);
			rbtPaymenView.setEnabled(true);
		} else {
			rbtPaymenNow.setEnabled(false);
			rbtPaymenView.setEnabled(false);
		}
	}

	private void chbPrintReceipItemStateChanged(java.awt.event.ItemEvent evt) {
		if (chbPrintReceip.isSelected()) {
			rbtPrintNow.setEnabled(true);
			rbtPrintView.setEnabled(true);
		} else {
			rbtPrintNow.setEnabled(false);
			rbtPrintView.setEnabled(false);
		}
	}

	private void btnThoat3ActionPerformed(java.awt.event.ActionEvent evt) {
		flagChange = false;
		this.dispose();
	}

	private void btnDongY3ActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			save();
		} catch (Exception e) {
		}
	}

	private void chbAreaItemStateChanged(java.awt.event.ItemEvent evt) {
		if (chbArea.isSelected()) {
			rbtAll.setEnabled(true);
			rbtSingleton.setEnabled(true);
		} else {
			rbtAll.setEnabled(false);
			rbtSingleton.setEnabled(false);
		}
	}

	private void btnThoat5ActionPerformed(java.awt.event.ActionEvent evt) {
		flagChange = false;
		this.dispose();
	}

	private void btnDongY5ActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			save();
		} catch (Exception e) {
		}
	}

	private void rbtRestoItemStateChanged(java.awt.event.ItemEvent evt) {
		if (rbtResto.isSelected()) {
			rbt1quay.setEnabled(false);
			rbtnhieuquay.setEnabled(false);
			loadGui(true);
		}
	}

	private void rbtCafeItemStateChanged(java.awt.event.ItemEvent evt) {
		if (rbtCafe.isSelected()) {
			rbt1quay.setEnabled(false);
			rbtnhieuquay.setEnabled(false);
			loadGui(true);
		}
	}

	private void rbtKaraItemStateChanged(java.awt.event.ItemEvent evt) {
		if (rbtKara.isSelected()) {
			rbt1quay.setEnabled(false);
			rbtnhieuquay.setEnabled(false);
			loadGui(true);
		}
	}

	private void rbtBiaItemStateChanged(java.awt.event.ItemEvent evt) {
		if (rbtBia.isSelected()) {
			rbt1quay.setEnabled(false);
			rbtnhieuquay.setEnabled(false);
			loadGui(false);
		}
	}

	private void rbtMaketItemStateChanged(java.awt.event.ItemEvent evt) {
		if (rbtMaket.isSelected()) {
			rbt1quay.setEnabled(true);
			rbtnhieuquay.setEnabled(true);
			loadGui(false);
		}
	}

	private void rbtShopItemStateChanged(java.awt.event.ItemEvent evt) {
		if (rbtShop.isSelected()) {
			rbt1quay.setEnabled(true);
			rbtnhieuquay.setEnabled(true);
			loadGui(false);
		}
	}

	private void rbtSpaItemStateChanged(java.awt.event.ItemEvent evt) {
		if (rbtSpa.isSelected()) {
			rbt1quay.setEnabled(true);
			rbtnhieuquay.setEnabled(true);
			loadGui(false);
		}
	}

	private void rbtTapHoaItemStateChanged(java.awt.event.ItemEvent evt) {
		if (rbtTapHoa.isSelected()) {
			rbt1quay.setEnabled(true);
			rbtnhieuquay.setEnabled(true);
			loadGui(false);
		}
	}

	private void chbDoiGiaItemStateChanged(java.awt.event.ItemEvent evt) {
		if (chbDoiGia.isSelected()) {
			chbSuagia.setEnabled(true);
		} else {
			chbSuagia.setEnabled(false);
			chbSuagia.setSelected(false);
		}
	}

	private void chbSoluongItemStateChanged(java.awt.event.ItemEvent evt) {
		if (chbSoluong.isSelected()) {
			chbSuaSL.setEnabled(true);
		} else {
			chbSuaSL.setEnabled(false);
			chbSuaSL.setSelected(false);
		}
	}

	private void chbPricingTableAreaItemStateChanged(java.awt.event.ItemEvent evt) {
		if (chbPricingTableArea.isSelected()) {
			btnPricingTable.setEnabled(true);
		} else {
			btnPricingTable.setEnabled(false);
		}
	}

	private void chbInCheBienItemStateChanged(java.awt.event.ItemEvent evt) {

	}

	private void rdbPrcingTable1ItemStateChanged(java.awt.event.ItemEvent evt) {
		if (rdbPrcingTable1.isVisible()) {
			try {
				if (rdbPrcingTable1.isSelected()) {
					rdbPrcingTable1.setFont(new Font("Tahoma", Font.BOLD, 13));
					type = ((ProductPriceType) cboPriceType1.getSelectedItem()).getType();
				} else {
					rdbPrcingTable1.setFont(new Font("Tahoma", Font.PLAIN, 14));
				}
			} catch (Exception e) {
			}
		}
	}

	private void rdbPrcingTable2ItemStateChanged(java.awt.event.ItemEvent evt) {
		if (rdbPrcingTable2.isVisible()) {
			try {
				if (rdbPrcingTable2.isSelected()) {
					rdbPrcingTable2.setFont(new Font("Tahoma", Font.BOLD, 13));
					type = ((ProductPriceType) cboPriceType2.getSelectedItem()).getType();
				} else {
					rdbPrcingTable2.setFont(new Font("Tahoma", Font.PLAIN, 14));
				}
			} catch (Exception e) {
			}
		}
	}

	private void rdbPrcingTable3ItemStateChanged(java.awt.event.ItemEvent evt) {
		if (rdbPrcingTable3.isVisible()) {
			try {
				if (rdbPrcingTable3.isSelected()) {
					rdbPrcingTable3.setFont(new Font("Tahoma", Font.BOLD, 13));
					type = ((ProductPriceType) cboPriceType3.getSelectedItem()).getType();
				} else {
					rdbPrcingTable3.setFont(new Font("Tahoma", Font.PLAIN, 14));
				}
			} catch (Exception e) {
			}
		}
	}

	private void btnThoat1ActionPerformed(java.awt.event.ActionEvent evt) {
		flagChange = false;
		this.dispose();
	}

	private void btnDongY1ActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			save();
		} catch (Exception e) {
		}
	}

	private void cboPriceType1ItemStateChanged(java.awt.event.ItemEvent evt) {
		if (cboPriceType1.getSelectedItem() != null) {
			rdbPrcingTable1.setVisible(true);
			ProductPriceType ppt = ((ProductPriceType) cboPriceType1.getSelectedItem());
			String name = "<html><p align='center'>" + ppt.getName() + "</p></html>";
			rdbPrcingTable1.setText(name);
			rdbPrcingTable1.setName(ppt.getType());
			if (type.equals(ppt.getType())) {
				rdbPrcingTable1.setSelected(true);
			} else {
				rdbPrcingTable1.setSelected(false);
			}
		} else {
			rdbPrcingTable1.setText("");
			if (rdbPrcingTable1.isSelected()) {
				type = "";
			}
			rdbPrcingTable1.setVisible(false);
		}
	}

	private void cboPriceType2ItemStateChanged(java.awt.event.ItemEvent evt) {
		if (cboPriceType2.getSelectedItem() != null) {
			rdbPrcingTable2.setVisible(true);
			ProductPriceType ppt = ((ProductPriceType) cboPriceType2.getSelectedItem());
			String name = "<html><p align='center'>" + ppt.getName() + "</p></html>";
			rdbPrcingTable2.setText(name);
			rdbPrcingTable2.setName(ppt.getType());
			if (type.equals(((ProductPriceType) cboPriceType2.getSelectedItem()).getType())) {
				rdbPrcingTable2.setSelected(true);
			} else {
				rdbPrcingTable2.setSelected(false);
			}
		} else {
			rdbPrcingTable2.setText("");
			if (rdbPrcingTable2.isSelected()) {
				type = "";
			}
			rdbPrcingTable2.setVisible(false);
		}
	}

	private void cboPriceType3ItemStateChanged(java.awt.event.ItemEvent evt) {
		if (cboPriceType3.getSelectedItem() != null) {
			rdbPrcingTable3.setVisible(true);
			ProductPriceType ppt = ((ProductPriceType) cboPriceType3.getSelectedItem());
			String name = "<html><p align='center'>" + ppt.getName() + "</p></html>";
			rdbPrcingTable3.setText(name);
			if (type.equals(((ProductPriceType) cboPriceType3.getSelectedItem()).getType())) {
				rdbPrcingTable3.setSelected(true);
			} else {
				rdbPrcingTable3.setSelected(false);
			}
		} else {
			rdbPrcingTable3.setText("");
			if (rdbPrcingTable3.isSelected()) {
				type = "";
			}
			rdbPrcingTable3.setVisible(false);
		}
	}

	private void chbKeyboardItemStateChanged(java.awt.event.ItemEvent evt) {
		if (chbKeyboard.isSelected()) {
			rbtAllSystem.setEnabled(true);
			rbtSalse.setEnabled(true);
		} else {
			rbtAllSystem.setEnabled(false);
			rbtSalse.setEnabled(false);
		}
	}

	private void loadGui(boolean b) {
		chbInCheBien.setSelected(b);
		chbInHuyMon.setSelected(b);
		chbDatgio.setSelected(b);
		chbInCheBien.setEnabled(b);
		chbInHuyMon.setEnabled(b);
		chbDatgio.setEnabled(b);
	}

	private void save() throws Exception {

		// Thiết lập chung
	//	if (changeComboBox) {
			HashMap<String, String> list1 = new HashMap<String, String>();
			if (cbDepartment.getSelectedDepartment() != null)
				list1.put("departmentCode", cbDepartment.getSelectedDepartment().getName());
			else
				list1.put("departmentCode", "");
			if (cbStore.getSelectedStore() != null)
				list1.put("storeCode", cbStore.getSelectedStore().getCode());
			else
				list1.put("storeCode", "");
			if (cbProject.getSelectedProject() != null)
				list1.put("projectCode", cbProject.getSelectedProject().getCode());
			else
				list1.put("projectCode", "");
			saveFile(list1);
		//}

		Profile profileConfig = AccountModelManager.getInstance().getProfileConfigAdmin();

		// Thiết lập giao diện
		if (chbKeyboard.isSelected()) {
			if (rbtAllSystem.isSelected()) {
				profileConfig.put(Keyboard, true);
			}
		} else {
			profileConfig.put(Keyboard, false);
		}

		if (chbProjectCode.isSelected()) {
			profileConfig.put("Project", "true");
			Project.nameProject = false;
		} else {
			Project.nameProject = true;
			profileConfig.put("Project", null);
		}

		if (chbProduct.isSelected()) {
			profileConfig.put("Product", "true");
		} else {
			profileConfig.put("Product", null);
		}

		if (chbUSB.isSelected()) {
			profileConfig.put("Usb", "true");
		} else {
			profileConfig.put("Usb", null);
		}
		
		if (chbForRent.isSelected()) {
			profileConfig.put("ForRent", "true");
		} else {
			profileConfig.put("ForRent", null);
		}
		
		if (chbProductPayment.isSelected()) {
			profileConfig.put("PaymentSlip", "true");
		} else {
			profileConfig.put("PaymentSlip", null);
		}
		
		if (chbDailyWork.isSelected()) {
			profileConfig.put("DailyWork", "true");
		} else {
			profileConfig.put("DailyWork", null);
		}

		if (chbExel.isSelected()) {
			profileConfig.put("Exel", "true");
		} else {
			profileConfig.put("Exel", null);
		}

		if (chbPdf.isSelected()) {
			profileConfig.put("Pdf", "true");
		} else {
			profileConfig.put("Pdf", null);
		}
		
		if (chbExelMail.isSelected()) {
			profileConfig.put("ExelMail", txtMail.getText());
			saveData(txtMail.getText());
		} else {
			profileConfig.put("ExelMail", null);
		}

		if (chbInHuyMon.isSelected()) {
			profileConfig.put(InHuyMon, true);
		} else {
			profileConfig.put(InHuyMon, false);
		}

		if (chbInCheBien.isSelected()) {
			profileConfig.put(InCheBien, true);
		} else {
			profileConfig.put(InCheBien, false);
		}

		if (chbInCBNSP.isSelected()) {
			profileConfig.put(GioHoaDon, true);
		} else {
			profileConfig.put(GioHoaDon, false);
		}

		if (chbSLBP.isSelected()) {
			profileConfig.put(SLBP, true);
		} else {
			profileConfig.put(SLBP, false);
		}

		if (chbDatgio.isSelected()) {
			profileConfig.put(Datgio, true);
		} else {
			profileConfig.put(Datgio, false);
		}

		if (chbThue.isSelected()) {
			profileConfig.put(Thue, true);
		} else {
			profileConfig.put(Thue, false);
		}

		if (chbChietKhau.isSelected()) {
			profileConfig.put(CK, true);
		} else {
			profileConfig.put(CK, false);
		}

		if (chbDichVu.isSelected()) {
			profileConfig.put(DichVu, true);
		} else {
			profileConfig.put(DichVu, false);
		}

		if (chbThanhToanLe.isSelected()) {
			profileConfig.put(TTLe, true);
		} else {
			profileConfig.put(TTLe, false);
		}

		if (chbSoluong.isSelected()) {
			profileConfig.put(SoLuong, true);
		} else {
			profileConfig.put(SoLuong, false);
		}

		if (chbSuaSL.isSelected()) {
			profileConfig.put(SuaSL, true);
		} else {
			profileConfig.put(SuaSL, false);
		}

		if (chbThietLapBan.isSelected()) {
			profileConfig.put(ThietLapBH, true);
		} else {
			profileConfig.put(ThietLapBH, false);
		}

		if (chbTraSau.isSelected()) {
			profileConfig.put(TraSau, true);
		} else {
			profileConfig.put(TraSau, false);
		}

		if (chbHuymon.isSelected()) {
			profileConfig.put(HuySP, true);
		} else {
			profileConfig.put(HuySP, false);
		}

		if (chbTaoSP.isSelected()) {
			profileConfig.put(TaoSP, true);
		} else {
			profileConfig.put(TaoSP, false);
		}

		if (chbSuagia.isSelected()) {
			profileConfig.put(SuaGia, true);
		} else {
			profileConfig.put(SuaGia, false);
		}

		if (chbXoamon.isSelected()) {
			profileConfig.put(XoaSP, true);
		} else {
			profileConfig.put(XoaSP, false);
		}

		if (chbCTKM.isSelected()) {
			profileConfig.put(CTKM, true);
		} else {
			profileConfig.put(CTKM, false);
		}

		if (chbDoiGia.isSelected()) {
			profileConfig.put(DoiGia, true);
		} else {
			profileConfig.put(DoiGia, false);
		}

		if (chbMuaHo.isSelected()) {
			profileConfig.put(MuaHo, true);
		} else {
			profileConfig.put(MuaHo, false);
		}

		if (chbPaymen.isSelected()) {
			profileConfig.put(Paymen, true);
		} else {

			profileConfig.put(Paymen, false);
		}

		if (rbtPaymenNow.isSelected()) {
			profileConfig.put(PaymenNow, true);
		} else {
			profileConfig.put(PaymenNow, false);
		}

		if (rbtPaymenView.isSelected()) {
			profileConfig.put(PaymenView, true);
		} else {
			profileConfig.put(PaymenView, false);
		}

		if (chbPrintReceip.isSelected()) {

			profileConfig.put(PrintReceip, true);

		} else {

			profileConfig.put(PrintReceip, false);
		}

		if (rbtPrintNow.isSelected()) {
			profileConfig.put(PrintNow, true);
		} else {
			profileConfig.put(PrintNow, false);
		}

		if (rbtPrintView.isSelected()) {
			profileConfig.put(PrintView, true);
		} else {
			profileConfig.put(PrintView, false);
		}

		// Thiết lập khu vực
		if (chbArea.isSelected()) {
			profileConfig.put(Area, true);
		} else {
			profileConfig.put(Area, false);
		}

		if (rbtSingleton.isSelected()) {
			profileConfig.put(SingleArea, true);
		} else {
			profileConfig.put(SingleArea, false);
		}

		if (rbtAll.isSelected()) {
			profileConfig.put(AllArea, true);
		} else {
			profileConfig.put(AllArea, false);
		}

		if (chbPricingTableArea.isSelected()) {
			profileConfig.put(TbArea, true);
		} else {
			profileConfig.put(TbArea, false);
		}

		// Thiết lập phiên bản
		if (rbtResto.isSelected()) {
			profileConfig.put(Resto, true);
			Table = "bàn";
		} else {
			profileConfig.put(Resto, false);
		}

		if (rbtCafe.isSelected()) {
			profileConfig.put(Cafe, true);
			Table = "bàn";
		} else {
			profileConfig.put(Cafe, false);
		}

		if (rbtKara.isSelected()) {
			profileConfig.put(Kara, true);
			Table = "phòng";
		} else {
			profileConfig.put(Kara, false);
		}

		if (rbtBia.isSelected()) {
			profileConfig.put(Bia, true);
			Table = "bàn";
		} else {
			profileConfig.put(Bia, false);
		}

		if (rbtMaket.isSelected()) {
			profileConfig.put(Maket, true);
			Table = "quầy";
		} else {
			profileConfig.put(Maket, false);
		}

		if (rbtShop.isSelected()) {
			profileConfig.put(Shop, true);
			Table = "quầy";
		} else {
			profileConfig.put(Shop, false);
		}

		if (rbtSpa.isSelected()) {
			profileConfig.put(Spa, true);
			Table = "phòng";
		} else {
			profileConfig.put(Spa, false);
		}

		if (rbtTapHoa.isSelected()) {
			profileConfig.put(TapHoa, true);
			Table = "quầy";
		} else {
			profileConfig.put(TapHoa, false);
		}

		if (rbt1quay.isSelected()) {
			profileConfig.put(Quay, true);
		} else {
			profileConfig.put(Quay, false);
		}

		if (rbtnhieuquay.isSelected()) {
			profileConfig.put(NhieuQuay, true);
		} else {
			profileConfig.put(NhieuQuay, false);
		}

		if (rbtPoss.isSelected()) {
			profileConfig.put(GDPos, true);
		} else {
			profileConfig.put(GDPos, false);
		}

		if (rbtThuong.isSelected()) {
			profileConfig.put(GDThuong, true);
			if(PanelScreenSaleLocationPos.salePOS != null){
				PanelScreenSaleLocationPos.getInstance().switchScreen = true;
			}
		} else {
			profileConfig.put(GDThuong, false);
		}

		if (rbt01.isSelected()) {
			profileConfig.put(One, true);
			profileConfig.put(Background, "Background.jpg");
		} else {
			profileConfig.put(One, false);
		}

		if (rbt02.isSelected()) {
			profileConfig.put(Two, true);
			profileConfig.put(Background, "CafeBackgroud.jpg");
		} else {
			profileConfig.put(Two, false);
		}

		if (rbt03.isSelected()) {
			profileConfig.put(Three, true);
			profileConfig.put(Background, "KaraBackgroud.jpg");
		} else {
			profileConfig.put(Three, false);
		}

		if (rbt04.isSelected()) {
			profileConfig.put(Four, true);
			profileConfig.put(Background, "Fond.jpg");
		} else {
			profileConfig.put(Four, false);
		}

		if (rbt05.isSelected()) {
			profileConfig.put(Five, true);
			profileConfig.put(Background, "FondBordeaux.jpg");
		} else {
			profileConfig.put(Five, false);
		}

		if (rbt06.isSelected()) {
			profileConfig.put(Six, true);
			profileConfig.put(Background, "FondGris.jpg");
		} else {
			profileConfig.put(Six, false);
		}

		if (rbt07.isSelected()) {
			profileConfig.put(Seven, true);
			profileConfig.put(Background, "FondJaune.jpg");
		} else {
			profileConfig.put(Seven, false);
		}

		if (rbt08.isSelected()) {
			profileConfig.put(Eight, true);
			profileConfig.put(Background, "FondVert.jpg");
		} else {
			profileConfig.put(Eight, false);
		}

		if (rbtNull.isSelected()) {
			profileConfig.put(Null, true);
		} else {
			profileConfig.put(Null, false);
		}

		if (rbtNull.isSelected()) {
			profileConfig.put(Null, true);
		} else {
			profileConfig.put(Null, false);
		}
		if (rbt09.isSelected()) {
			profileConfig.put(ImageOption, url);
		} else {
			profileConfig.put(ImageOption, null);
		}

		// Thiết lập mua bán
		profileConfig.put(cboPriceType1.getName(), cboPriceType1.getSelectedIndex());
		profileConfig.put(cboPriceType2.getName(), cboPriceType2.getSelectedIndex());
		profileConfig.put(cboPriceType3.getName(), cboPriceType3.getSelectedIndex());
		profileConfig.put(ProductPriceType, type);
		List<String> list = new ArrayList<String>();
		for (CheckBoxP checkBoxP : listCheckBoxPs) {
			if (checkBoxP.isSelected()) {
				list.add(checkBoxP.getProductTag().getTag());
			}
		}
		profileConfig.put(ProductTag, list);

		if (cboUnitMoney.getSelectedItem() != null) {
			profileConfig.put(Currency, ((Currency) cboUnitMoney.getSelectedItem()).getCode());
		}
		if (cboQuyDoi.getSelectedItem() != null) {
			profileConfig.put(CurrencyQD, ((Currency) cboQuyDoi.getSelectedItem()).getCode());
		}
		profileConfig.put("nameTable", Table);
		AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(),
		    profileConfig);
		dispose();
		ScreenOften.getInstance().resetGui();
		ScreenOften.getInstance().setResetProduct(true);
	}

	private void loadIconLableDeception() {
		ImageIcon icon = new ImageIcon(HomeScreen.class.getResource("icon/Background.jpg"));
		lb01.setIcon(reSizeImage(icon, lb01.getPreferredSize()));

		ImageIcon icon2 = new ImageIcon(HomeScreen.class.getResource("icon/CafeBackgroud.jpg"));
		lb02.setIcon(reSizeImage(icon2, lb02.getPreferredSize()));

		ImageIcon icon5 = new ImageIcon(HomeScreen.class.getResource("icon/KaraBackgroud.jpg"));
		lb03.setIcon(reSizeImage(icon5, lb03.getPreferredSize()));

		ImageIcon icon3 = new ImageIcon(HomeScreen.class.getResource("icon/Fond.jpg"));
		lb04.setIcon(reSizeImage(icon3, lb04.getPreferredSize()));

		ImageIcon icon4 = new ImageIcon(HomeScreen.class.getResource("icon/FondBordeaux.jpg"));
		lb05.setIcon(reSizeImage(icon4, lb05.getPreferredSize()));

		ImageIcon icon1 = new ImageIcon(HomeScreen.class.getResource("icon/FondGris.jpg"));
		lb06.setIcon(reSizeImage(icon1, lb06.getPreferredSize()));
		ImageIcon icon7 = new ImageIcon(HomeScreen.class.getResource("icon/FondJaune.jpg"));
		lb07.setIcon(reSizeImage(icon7, lb07.getPreferredSize()));
		ImageIcon icon8 = new ImageIcon(HomeScreen.class.getResource("icon/FondVert.jpg"));
		lb08.setIcon(reSizeImage(icon8, lb08.getPreferredSize()));

	}

	private void saveFile(HashMap<String, String> list) {
		JSONObject obj = new JSONObject();
		Set<String> keys = list.keySet();
		for (String key : keys) {
			obj.put(key, list.get(key));
		}

		try {
			FileWriter fw = new FileWriter(HKTFile.getFile("Database", "configPayment.json"), false);
			fw.write(obj.toJSONString());
			fw.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

	private void addKeyBindings(JComponent jc) {
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "ESC");
		jc.getActionMap().put("ESC", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				dispose();
			}
		});
	}

	private ImageIcon reSizeImage(ImageIcon imageIcon, Dimension dimension) {
		Image image = imageIcon.getImage();
		Image image1 = image.getScaledInstance(dimension.width, dimension.height, Image.SCALE_SMOOTH);
		return new ImageIcon(image1);
	}
	

	private void saveData(String a) {
		try {
			FileOutputStream fStream = new FileOutputStream(HKTFile.getFile("AutoSave", "adMail"));
			ObjectOutputStream oStream = new ObjectOutputStream(fStream);
			oStream.writeObject(a);
			oStream.reset();

			fStream.close();
		} catch (Exception e) {
		}

	}

}