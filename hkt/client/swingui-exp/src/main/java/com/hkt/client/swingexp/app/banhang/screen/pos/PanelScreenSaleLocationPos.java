package com.hkt.client.swingexp.app.banhang.screen.pos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import org.jdesktop.swingx.WrapLayout;

import com.hkt.client.swingexp.app.banhang.PanelSearchInvoice;
import com.hkt.client.swingexp.app.banhang.screen.often.DialogConfig;
import com.hkt.client.swingexp.app.banhang.screen.often.DialogSplitTable;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelDeleteTableLocation;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelMoveTable;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelOpenTableArea;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelTableGroup;
import com.hkt.client.swingexp.app.banhang.screen.often.ScreenOften;
import com.hkt.client.swingexp.app.banhang.screen.often.TableListChoosetable;
import com.hkt.client.swingexp.app.component.ExtendJButton;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJRadioButton;
import com.hkt.client.swingexp.app.component.PanelBackground;
import com.hkt.client.swingexp.app.core.DialogList;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MouseEventMove;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.core.UnitMoneyJComboBox;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.config.locale.Currency;
import com.hkt.module.core.entity.AbstractPersistable;
import com.hkt.module.product.entity.ProductPriceType;
import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.Table;
import com.hkt.module.restaurant.entity.Table.Status;

public class PanelScreenSaleLocationPos extends PanelBackground {
	private javax.swing.JPanel									PanelC_Left;
	private javax.swing.JPanel									PanelC_Right;
	private javax.swing.JScrollPane							ScrollPane_Left;
	private javax.swing.JScrollPane							ScrollPane_Right;
	private javax.swing.JButton									btnQuanLyChung;
	private javax.swing.JButton									btnThietLapBan;
	private javax.swing.JButton									btnBanNhanh;
	private javax.swing.JButton									btnChuyenBan;
	private javax.swing.JButton									btnDatBan;
	private javax.swing.JButton									btnGopTachBan;
	private javax.swing.JButton									btnMoBan;
	private javax.swing.JButton									btnTuyChonHTBan;
	private javax.swing.JButton									btnQlBep;
	private javax.swing.JButton									btnQlDatTruoc;
	private javax.swing.JButton									btnQlKhachNo;
	private javax.swing.JButton									btnSuaTen;
	private javax.swing.JButton									btnThietLapBh;
	private javax.swing.JButton									btnThoat;
	private javax.swing.JButton									btnXoa;
	private UnitMoneyJComboBox									cboDvTien;
	private javax.swing.JLabel									lblDvTien;
	private javax.swing.JLabel									lblGhiChu;
	private javax.swing.JLabel									lblIsGuest;
	private javax.swing.JLabel									lblIsTableFree;
	public javax.swing.JLabel										lblNumTableBussy;
	public javax.swing.JLabel										lblNumTableFree;
	private javax.swing.JLabel									lblHKT;
	private ExtendJRadioButton									rdBangGia1;
	private ExtendJRadioButton									rdBangGia2;
	private ExtendJRadioButton									rdBangGia3;
	private ButtonGroup													groupRadioButton;
	private HashMap<String, ComponentLocation>	listLocationTables;
	private ComponentTable											panelTableSelected		= null;
	private ComponentLocation										panelLocationSelected	= null;
	public HashMap<String, ComponentTable>			listTableBussy;
	private List<Object>												listTemp							= new ArrayList<Object>();
	private int																	widthScreen, heightMax = 0, widthMax = 0, heightMore = 0, zz = 15;

	private Profile															profile;
	private String															currencyCode;
	private String															priceType;
	private int																	mouseCountClick				= 1;
	private JPanel															panelNote;
	public boolean															switchScreen					= false;
	public static PanelScreenSaleLocationPos									salePOS;

	public PanelScreenSaleLocationPos() {
		initComponents();
		createAndShowGui();
		addKeyBindings(this);
	}

	public static PanelScreenSaleLocationPos getInstance() {
		if (salePOS == null) {
			salePOS = new PanelScreenSaleLocationPos();
		}
		return salePOS;
	}

	public void createAndShowGui() {
		// Thiết lập các kích thước mặc định màn hình
		widthMax = widthScreen - 135;
		heightMax = Toolkit.getDefaultToolkit().getScreenSize().height - 110;
		ScrollPane_Right.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				widthMax = ScrollPane_Right.getSize().width;
				heightMax = ScrollPane_Right.getSize().height;
			}
		});

		List<Location> locations;
		listTableBussy = new HashMap<String, PanelScreenSaleLocationPos.ComponentTable>();
		try {
			locations = RestaurantModelManager.getInstance().getLocations();
		} catch (Exception e) {
			locations = new ArrayList<Location>();
		}
		ScrollPane_Left.setPreferredSize(new Dimension(125, heightMax));
		PanelC_Left.setSize(new Dimension(100, heightMax));
		PanelC_Left.setLayout(new GridLayout(9, 1, 5, 5));
		PanelC_Left.setBackground(Color.LIGHT_GRAY);
		PanelC_Left.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		PanelC_Right.setLayout(new BoxLayout(PanelC_Right, BoxLayout.Y_AXIS));
		PanelC_Right.setSize(new Dimension(widthMax, heightMax));
		PanelC_Right.setBackground(Color.LIGHT_GRAY);		
		
		listLocationTables = new HashMap<String, ComponentLocation>();
		ScrollPane_Left.setViewportView(PanelC_Left);
		ScrollPane_Right.setViewportView(PanelC_Right);
		if (widthScreen > 800) {
			JScrollBar sb = ScrollPane_Right.getVerticalScrollBar();
			sb.setPreferredSize(new Dimension(25, 0));
			zz = 28;
		}

		if (locations != null && locations.size() > 0) {
			if (locations.size() >= 9) {
				PanelC_Left.setPreferredSize(new Dimension(100, (65 * (locations.size() + 1))));
				PanelC_Left.setLayout(new GridLayout((locations.size() + 1), 1, 5, 5));
			}

			ComponentLocation panelLocationAll = new ComponentLocation("Tất cả");
			PanelC_Left.add(panelLocationAll);
			panelLocationAll.getChkLocation().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					boolean flagSelected = ((JCheckBox) e.getSource()).isSelected();
					for (Entry<String, ComponentLocation> entry : listLocationTables.entrySet()) {
						ComponentLocation row = (ComponentLocation) listLocationTables.get(entry.getKey());
						JPanel panelTables = row.getRowPanelTables();
						if (panelTables != null)
							panelTables.setVisible(flagSelected);
						if (flagSelected)
							row.getChkLocation().setSelected(!flagSelected);
					}
					if (flagSelected) {
						PanelC_Right.setPreferredSize(new Dimension(widthMax - zz, heightMore));
						PanelC_Right.setSize(new Dimension(widthMax - zz, heightMore));
					} else {
						PanelC_Right.setPreferredSize(new Dimension(widthMax - zz, 0));
						PanelC_Right.setSize(new Dimension(widthMax - zz, 0));
					}
					PanelC_Right.updateUI();
					updateUI();
				}
			});

			for (int i = 0; i < locations.size(); i++) {
				final Location l = locations.get(i);
				ComponentLocation panelLocation = new ComponentLocation(l);
				PanelC_Left.add(panelLocation);

				JPanel rowLocation = new JPanel();
				rowLocation.setOpaque(false);
				rowLocation.setLayout(new WrapLayout(WrapLayout.LEFT));
				rowLocation.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
				List<Table> tables;
				try {
					tables = RestaurantModelManager.getInstance().findTableByLocation(l.getCode());
				} catch (Exception ex) {
					tables = new ArrayList<Table>();
				}

				// Add các Panel Table vào trong Panel cha
				for (Table ta : tables) {
					ComponentTable panelTable = new ComponentTable(ta);
					panelTable.setKhuVuc(l);
					rowLocation.add(panelTable);
				}
				// Tính toán kích thước cho Panel chứa Tables
				int n = (widthScreen <= 800) ? 4 : 7;
				int rowCount = (tables.size() % n) != 0 ? (tables.size() / n + 1) : (tables.size() / n);
				int height = rowCount * 88;
				heightMore = heightMore + height;
				rowLocation.setSize(new Dimension(widthMax - zz, height));
				rowLocation.setPreferredSize(new Dimension(widthMax - zz, height));
				// Chọn hiển thị khu vực đầu tiên
				if (i > 0) {
					rowLocation.setVisible(false);
				} else {
					PanelC_Right.setSize(new Dimension(widthMax - zz, height));
					PanelC_Right.setPreferredSize(new Dimension(widthMax - zz, height));
					panelLocation.getChkLocation().setSelected(true);
				}
				// Add vào Panel cha và List HashMap
				PanelC_Right.add(rowLocation);
				panelLocation.setRowPanelTables(rowLocation);
				listLocationTables.put(l.getCode(), panelLocation);
			}
		}

		getProfileConfigData();

		// Set giá trị khởi tạo bàn trống và bàn có khách
		/*
		 * Bàn có khách = tổng (số bàn bận + số bàn bận cũ)
		 * Bàn trống = tổng số bàn hiện tại - số bàn bận
		 */
		try {
			List<Table> tables = RestaurantModelManager.getInstance().getTables();
			lblNumTableBussy.setText((listTableBussy.size() + Integer.parseInt(lblNumTableBussy.getText())) + "");
			lblNumTableFree.setText((tables.size() - listTableBussy.size()) + "");
		} catch (Exception ex) {

		}
	}
	
	public void createPanelNote() {
		int size = 50; 
		panelNote = new JPanel(new BorderLayout(0, 0));
		panelNote.setPreferredSize(new Dimension(100, size));
		
		JPanel panelStatusTable = new JPanel(new GridLayout(1, 6, 5, 0));
		panelStatusTable.setOpaque(false);
		
		JPanel panelShortkey = new JPanel(new GridLayout(2, 1, 0, 0));
		panelShortkey.setOpaque(false);
		panelShortkey.setPreferredSize(new Dimension(200, size));
		panelShortkey.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		
		// Khoi tao set chu ky tu
		JLabel table_free = new JLabel("<html><p align='center'>Bàn trống, chưa có khách</p></html>");
		JLabel table_taken = new JLabel("<html><p align='center'>Bàn có khách</p></html>");
		JLabel table_recorded = new JLabel("<html><p align='center'>Khách đang và đã gọi đồ</p></html>");
		JLabel table_kitchen = new JLabel("<html><p align='center'>Bếp đang và đã xử lý</p></html>");
		JLabel table_served = new JLabel("<html><p align='center'>Bàn đã in hóa đơn</p></html>");
		JLabel table_desert = new JLabel("<html><p align='center'>Bàn đã có khách đặt trước</p></html>");
		
		size = size - 10;
		// Set anh dai dien
		try {
			table_free.setIcon(new ImageIcon(ImageIO.read(HomeScreen.class.getResource("icon/table_free.jpg")).getScaledInstance(size, size, Image.SCALE_SMOOTH)));
			table_taken.setIcon(new ImageIcon(ImageIO.read(HomeScreen.class.getResource("icon/table_taken.jpg")).getScaledInstance(size, size, Image.SCALE_SMOOTH)));
			table_recorded.setIcon(new ImageIcon(ImageIO.read(HomeScreen.class.getResource("icon/table_recorded.png")).getScaledInstance(size, size, Image.SCALE_SMOOTH)));
			table_kitchen.setIcon(new ImageIcon(ImageIO.read(HomeScreen.class.getResource("icon/table_kitchen.png")).getScaledInstance(size, size, Image.SCALE_SMOOTH)));
			table_served.setIcon(new ImageIcon(ImageIO.read(HomeScreen.class.getResource("icon/table_served.jpg")).getScaledInstance(size, size, Image.SCALE_SMOOTH)));
			table_desert.setIcon(new ImageIcon(ImageIO.read(HomeScreen.class.getResource("icon/table_desert.jpg")).getScaledInstance(size, size, Image.SCALE_SMOOTH)));
		
			table_free.setHorizontalTextPosition(SwingConstants.RIGHT);
			table_taken.setHorizontalTextPosition(SwingConstants.RIGHT);
			table_recorded.setHorizontalTextPosition(SwingConstants.RIGHT);
			table_kitchen.setHorizontalTextPosition(SwingConstants.RIGHT);
			table_served.setHorizontalTextPosition(SwingConstants.RIGHT);
			table_desert.setHorizontalTextPosition(SwingConstants.RIGHT);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		JLabel lblF1 = new JLabel("F1: Mở bàn & khu vực mới");
		JLabel lblF10 = new JLabel("F10: Bán mang về");
		
		panelStatusTable.add(table_free);
		panelStatusTable.add(table_taken);
		panelStatusTable.add(table_recorded);
		panelStatusTable.add(table_kitchen);
		panelStatusTable.add(table_served);
		panelStatusTable.add(table_desert);
		
		panelShortkey.add(lblF1);
		panelShortkey.add(lblF10);
		
		panelNote.add(panelStatusTable, BorderLayout.CENTER);
		panelNote.add(panelShortkey, BorderLayout.LINE_START);
		panelNote.setVisible(false);
	}

	public void getProfileConfigData() {
		// Lấy các giá trị config
		profile = AccountModelManager.getInstance().getProfileConfigAdmin();

		try {
			// Load lại đơn vị tiền
			if (profile.get(DialogConfig.Currency) != null && !profile.get(DialogConfig.Currency).toString().equals(currencyCode)) {
				currencyCode = profile.get(DialogConfig.Currency).toString();
				cboDvTien.setSelectedCurrency(currencyCode);
				Currency currency = LocaleModelManager.getInstance().getCurrencyByCode(currencyCode);
				for (Entry<String, ComponentLocation> e : listLocationTables.entrySet()) {
					JPanel panelRow = e.getValue().getRowPanelTables();
					for (int i = 0; i < panelRow.getComponentCount(); i++) {
						ComponentTable ct = (ComponentTable) panelRow.getComponent(i);
						if (ct.getTable().getInvoiceCode() == null || ct.getTable().getInvoiceCode().equals(""))
							ct.getLblUnitMoney().setText(currency != null ? currency.getName() : "");
					}
				}
			}

			// Load lại bảng giá lựa chọn
			if (profile.get(DialogConfig.ProductPriceType) != null && !profile.get(DialogConfig.ProductPriceType).toString().equals(priceType)) {
				priceType = profile.get(DialogConfig.ProductPriceType).toString();
				if (rdBangGia1 != null && rdBangGia1.getName().equals(priceType)) {
					rdBangGia1.setSelected(true);
				} else {
					if (rdBangGia2 != null && rdBangGia2.getName().equals(priceType)) {
						rdBangGia2.setSelected(true);
					} else {
						if (rdBangGia3 != null && rdBangGia3.getName().equals(priceType)) {
							rdBangGia3.setSelected(true);
						}
					}
				}
			}

			// Load lại các bảng giá
			int priceType1 = Integer.parseInt(profile.get("cbo1").toString());
			int priceType2 = Integer.parseInt(profile.get("cbo2").toString());
			int priceType3 = Integer.parseInt(profile.get("cbo3").toString());

			List<ProductPriceType> listProductPriceType = ProductModelManager.getInstance().getProductPriceTypes();
			if (listProductPriceType != null && listProductPriceType.size() > 0) {
				if (priceType1 > 0) {
					ProductPriceType ppt = listProductPriceType.get(priceType1 - 1);
					rdBangGia1.setText("<html><p align='center'>" + ppt.getName() + "</p></html>");
					rdBangGia1.setName(ppt.getType());
					rdBangGia1.setVisible(true);
					if (priceType.equals(ppt.getType()))
						rdBangGia1.setSelected(true);
				} else {
					rdBangGia1.setText("");
					rdBangGia1.setName("");
					rdBangGia1.setVisible(false);
				}
				if (priceType2 > 0) {
					ProductPriceType ppt = listProductPriceType.get(priceType2 - 1);
					rdBangGia2.setText("<html><p align='center'>" + ppt.getName() + "</p></html>");
					rdBangGia2.setName(ppt.getType());
					rdBangGia2.setVisible(true);
					if (priceType.equals(ppt.getType()))
						rdBangGia2.setSelected(true);
				} else {
					rdBangGia2.setText("");
					rdBangGia2.setName("");
					rdBangGia2.setVisible(false);
				}
				if (priceType3 > 0) {
					ProductPriceType ppt = listProductPriceType.get(priceType3 - 1);
					rdBangGia3.setText("<html><p align='center'>" + ppt.getName() + "</p></html>");
					rdBangGia3.setName(ppt.getType());
					rdBangGia3.setVisible(true);
					if (priceType.equals(ppt.getType()))
						rdBangGia3.setSelected(true);
				} else {
					rdBangGia3.setText("");
					rdBangGia3.setName("");
					rdBangGia3.setVisible(false);
				}
			}

			// Hiển thị bàn phím ảo
			if (profile.get(DialogConfig.Keyboard) != null) {
				String value = profile.get(DialogConfig.Keyboard).toString();
				if (value.equals("true")) {
					mouseCountClick = 1;
				} else {
					mouseCountClick = 2;
				}
			}
		} catch (Exception ex) {
//			ex.printStackTrace();
		}
	}

	// Cập nhật lại giao diện Khu vực
	public void refeshGuiLocation(Location loca) {
		ComponentLocation panelLocation = listLocationTables.get(loca.getCode());
		// Trường hợp xóa - cập nhật lại giao diện
		if (loca.getPersistableState().equals(AbstractPersistable.State.DELETED)) {
			if (panelLocation != null) {
				PanelC_Left.remove(panelLocation);
				listLocationTables.remove(loca.getCode());
			}
		}
		// Trường hợp thêm mới hoặc sửa lại
		else {
			// Nếu là sửa lại
			if (panelLocation != null) {
				panelLocation.setLoca(loca);
			}
			// Nếu là thêm mới
			else {
				List<Location> locations;
				try {
					locations = RestaurantModelManager.getInstance().getLocations();
				} catch (Exception e) {
					locations = new ArrayList<Location>();
				}
				int countLocation = locations.size();
				if (loca != null && countLocation > 0) {
					if (countLocation == 1) {
						ComponentLocation panelLocationAll = new ComponentLocation("Tất cả");
						PanelC_Left.add(panelLocationAll);
						panelLocationAll.getChkLocation().addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								boolean flagSelected = ((JCheckBox) e.getSource()).isSelected();
								for (Entry<String, ComponentLocation> entry : listLocationTables.entrySet()) {
									ComponentLocation row = (ComponentLocation) listLocationTables.get(entry.getKey());
									JPanel panelTables = row.getRowPanelTables();
									if (panelTables != null)
										panelTables.setVisible(flagSelected);
									if (flagSelected)
										row.getChkLocation().setSelected(!flagSelected);
								}
								if (flagSelected) {
									PanelC_Right.setPreferredSize(new Dimension(widthMax - zz, heightMore));
									PanelC_Right.setSize(new Dimension(widthMax - zz, heightMore));
								}
								PanelC_Right.updateUI();
								updateUI();
							}
						});
					}

					if (countLocation >= 9) {
						PanelC_Left.setPreferredSize(new Dimension(100, (65 * (locations.size() + 1))));
						PanelC_Left.setLayout(new GridLayout((locations.size() + 1), 1, 5, 5));
					}

					ComponentLocation panelLocationNew = new ComponentLocation(loca);
					PanelC_Left.add(panelLocationNew);
					listLocationTables.put(loca.getCode(), panelLocationNew);

					PanelC_Left.updateUI();
					updateUI();
				}
			}
		}

	}

	// Cập nhật lại giao diện bàn của khu vực
	public void refeshGuiTable(List<Table> tables) {
		if (tables != null && tables.size() > 0) {
			String locationCode = tables.get(0).getLocationCode();
			ComponentLocation panelLocation = listLocationTables.get(locationCode);
			JPanel panelRowTables = panelLocation.getRowPanelTables();
			if (panelRowTables == null) {
				panelRowTables = new JPanel();
				panelRowTables.setOpaque(false);
				panelRowTables.setLayout(new WrapLayout(WrapLayout.LEFT));
				panelRowTables.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
				panelLocation.setRowPanelTables(panelRowTables);
				PanelC_Right.add(panelRowTables);
			} else {
				if (panelLocation.getChkLocation().isSelected()) {
					int h = PanelC_Right.getPreferredSize().height - panelRowTables.getPreferredSize().height;
					PanelC_Right.setPreferredSize(new Dimension(widthMax, h));
					PanelC_Right.setSize(new Dimension(widthMax, h));
				}
				heightMore = heightMore - panelRowTables.getPreferredSize().height;
			}
			for (int i = 0; i < tables.size(); i++) {
				ComponentTable panelTable = new ComponentTable(tables.get(i));
				panelRowTables.add(panelTable);
			}

			int n = (widthScreen <= 800) ? 4 : 7;
			int totalComp = panelRowTables.getComponentCount();
			int rowCount = (totalComp % n) != 0 ? (totalComp / n + 1) : (totalComp / n);
			int height = rowCount * 88;
			heightMore = heightMore + height;
			panelRowTables.setSize(new Dimension(widthMax, height));
			panelRowTables.setPreferredSize(new Dimension(widthMax, height));
			if (panelLocation.getChkLocation().isSelected()) {
				int h = PanelC_Right.getPreferredSize().height + panelRowTables.getPreferredSize().height;
				PanelC_Right.setPreferredSize(new Dimension(widthMax, h));
				PanelC_Right.setSize(new Dimension(widthMax, h));
			} else {
				panelRowTables.setVisible(false);
			}
			lblNumTableFree.setText((tables.size() + Integer.parseInt(lblNumTableFree.getText())) + "");
			PanelC_Right.updateUI();
			updateUI();
		}
	}

	public class ComponentTable extends JPanel {
		private JPanel		container;
		private JLabel		lblNameTable;
		private JLabel		lblPicture;
		private JLabel		lblNumMoney;
		private JLabel		lblUnitMoney;
		private JLabel		lblTime, lblGuess;
		private Table			ban;
		private Location	khuvuc;
		private boolean		tableSelected	= false;

		public ComponentTable(Table _table) {
			int sizeWidth = 165;
			if (widthScreen <= 800) {
				sizeWidth = 155;
			}

			this.ban = _table;
			JPanel glassPane = new JPanel();
			glassPane.setOpaque(false);
			glassPane.setBounds(0, 0, sizeWidth, 80);
			container = new JPanel();
			container.setLayout(new BorderLayout(0, 0));
			container.setBounds(0, 0, sizeWidth, 80);

			lblPicture = new JLabel();
			lblPicture.setPreferredSize(new Dimension(50, 50));
			lblPicture.setHorizontalAlignment(SwingConstants.CENTER);

			JPanel lblInfo = new JPanel(new BorderLayout(0, 0));
			lblInfo.setOpaque(false);

			lblNameTable = new JLabel("<html><p align='center'>" + _table.getName() + "</p></html>");
			lblNameTable.setHorizontalAlignment(SwingConstants.CENTER);
			lblNameTable.setPreferredSize(new Dimension(85, 30));
			lblNameTable.setFont(new Font("Tahoma", Font.BOLD, 14));

			JPanel panelTime = new JPanel(new BorderLayout(0, 0));
			panelTime.setOpaque(false);
			lblTime = new JLabel("");
			lblTime.setHorizontalAlignment(SwingConstants.CENTER);
			lblTime.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblGuess = new JLabel("");
			lblGuess.setHorizontalAlignment(SwingConstants.CENTER);
			lblGuess.setPreferredSize(new Dimension(25, 25));
			lblGuess.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panelTime.add(lblTime, BorderLayout.CENTER);
			panelTime.add(lblGuess, BorderLayout.LINE_END);

			lblInfo.add(lblNameTable, BorderLayout.CENTER);
			lblInfo.add(panelTime, BorderLayout.SOUTH);

			JPanel infoPriceTable = new JPanel(new BorderLayout(3, 0));
			infoPriceTable.setOpaque(false);
			JLabel lblTextMoney = new JLabel("Tiền");
			lblTextMoney.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblNumMoney = new JLabel("0");
			lblNumMoney.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblNumMoney.setPreferredSize(new Dimension(85, 22));
			lblNumMoney.setHorizontalAlignment(SwingConstants.RIGHT);
			lblUnitMoney = new JLabel(cboDvTien.getSelectedCurrency().getName());
			lblUnitMoney.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblUnitMoney.setHorizontalAlignment(SwingConstants.RIGHT);

			infoPriceTable.add(lblTextMoney, BorderLayout.LINE_START);
			infoPriceTable.add(lblNumMoney, BorderLayout.CENTER);
			infoPriceTable.add(lblUnitMoney, BorderLayout.LINE_END);

			glassPane.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// Sự kiện chuột phải chọn nhiều bàn
					if (e.getButton() == MouseEvent.BUTTON3) {
						setTableSelected(!isTableSelected());
						if (isTableSelected())
							listTemp.add(ComponentTable.this);
						else
							listTemp.remove(ComponentTable.this);
					} else {
						// Sự kiện chuột trái chọn một bàn
						if (e.getButton() == MouseEvent.BUTTON1) {
							if (listTemp.size() != 0) {
								for (Object obj : listTemp) {
									ComponentTable ct = (ComponentTable) obj;
									ct.setTableSelected(false);
								}
								listTemp.clear();
							}
							if (panelTableSelected != null)
								panelTableSelected.setTableSelected(false);
							panelTableSelected = ComponentTable.this;
							panelTableSelected.setTableSelected(true);

							if (e.getClickCount() == mouseCountClick) {
								DialogScreenOftenPos dialog = DialogScreenOftenPos.getInstance();
								if(!DialogScreenOftenPos.isFlagScreenOften()){
									DialogScreenOftenPos.setFlagScreenOften(true);
									dialog.getPanelTop2().add(dialog.getPanelProductTagRoot(), 1);
									dialog.getScrollPane_Product().setViewportView(dialog.getPanelProducts());
								}
								dialog.setTable(ComponentTable.this);
								dialog.resetGui();
								dialog.loadData();
								dialog.setVisible(true);
							}
						}
					}
				}
			});

			container.add(lblPicture, BorderLayout.LINE_START);
			container.add(lblInfo, BorderLayout.CENTER);
			container.add(infoPriceTable, BorderLayout.PAGE_END);
			this.setLayout(null);
			this.setPreferredSize(new Dimension(sizeWidth, 80));
			this.add(container, 0);
			this.add(glassPane, 1);

			if (ban.getInvoiceCode() != null && !ban.getInvoiceCode().equals("")) {				
				InvoiceDetail invoice = AccountingModelManager.getInstance().getInvoiceDetail(Long.parseLong(ban.getInvoiceCode()));
				if (invoice != null) {
					listTableBussy.put(ban.getCode(), this);
					lblGuess.setText(invoice.getGuest() + "");
					lblTime.setText(new SimpleDateFormat("HH:mm").format(invoice.getStartDate()) + "");
					lblNumMoney.setText(MyDouble.valueOf(invoice.getFinalCharge()-invoice.getTotalPaid()).toString());
					lblUnitMoney.setText(invoice.getCurrencyUnit() + "");
				}
			}
			setTableStatus(ban.getStatus());
			// Tính toán số bàn trống và số bàn có khach
			if (getTable().getStatus().equals(Table.Status.TableGross))
				lblNumTableBussy.setText((getTable().getReservations().size() + Integer.parseInt(lblNumTableBussy.getText())) + "");
		}

		public void setTable(Table ban) {
			this.ban = ban;

			lblNameTable.setText(ban.getName());
			if (ban.getInvoiceCode() != null && !ban.getInvoiceCode().equals("")) {
				InvoiceDetail invoice = AccountingModelManager.getInstance().getInvoiceDetail(Long.parseLong(ban.getInvoiceCode()));
				if (invoice != null) {
					lblGuess.setText(invoice.getGuest() + "");
					lblTime.setText(new SimpleDateFormat("HH:mm").format(invoice.getStartDate()) + "");
					lblNumMoney.setText(MyDouble.valueOf(invoice.getFinalCharge()-invoice.getTotalPaid()).toString());
					lblUnitMoney.setText(invoice.getCurrencyUnit() + "");
				}
			} else {
				lblGuess.setText("");
				lblTime.setText("");
				lblNumMoney.setText("0");
			}
			setTableStatus(ban.getStatus());
		}

		public Location getKhuVuc() {
			return khuvuc;
		}

		public void setKhuVuc(Location khuvuc) {
			this.khuvuc = khuvuc;
			this.setName(khuvuc.getCode());
		}

		public JLabel getLblNameTable() {
			return lblNameTable;
		}

		public void setLblNameTable(JLabel lblNameTable) {
			this.lblNameTable = lblNameTable;
		}

		public JLabel getLblTime() {
			return lblTime;
		}

		public void setLblTime(JLabel lblTime) {
			this.lblTime = lblTime;
		}

		public boolean isTableSelected() {
			return tableSelected;
		}

		public JLabel getLblGuess() {
			return lblGuess;
		}

		public void setLblGuess(JLabel lblGuess) {
			this.lblGuess = lblGuess;
		}

		public void setTableSelected(boolean tableSelected) {
			this.tableSelected = tableSelected;
			if (tableSelected) {
				container.setBackground(new Color(255, 128, 0));
				// Kiem tra trang thai cua ban de Load text button
				if (getTable().getStatus().equals(Table.Status.TableSet)) {
					btnDatBan.setText("<html><p align='center'>Hủy đặt<br>bàn/quầy</br></p></html>");
				} else {
					btnDatBan.setText("<html><p align='center'>Đặt trước<br>bàn/quầy</br></p></html>");
				}
				if (getTable().getStatus().equals(Table.Status.TableGross)) {
					btnGopTachBan.setText("<html><p align='center'>Tách bàn/quầy</p></html>");
				} else {
					btnGopTachBan.setText("<html><p align='center'>Gộp bàn/quầy</p></html>");
				}
			} else {
				setTableStatus(ban.getStatus());
			}
		}

		public JLabel getLblPicture() {
			return lblPicture;
		}

		public void setLblPicture(JLabel lblPicture) {
			this.lblPicture = lblPicture;
		}

		public JLabel getLblNumMoney() {
			return lblNumMoney;
		}

		public void setLblNumMoney(JLabel lblNumMoney) {
			this.lblNumMoney = lblNumMoney;
		}

		public JLabel getLblUnitMoney() {
			return lblUnitMoney;
		}

		public void setLblUnitMoney(JLabel lblUnitMoney) {
			this.lblUnitMoney = lblUnitMoney;
		}

		public Table getTable() {
			return ban;
		}

		public void setTableStatus(Status status) {
			String urlImage = "icon/table_free.jpg";
			// Màu xanh lá cây
			Color colorGreen = new Color(51, 153, 51);
			// Màu đỏ
			Color colorRed = new Color(182, 89, 73);
			// Màu vàng
			Color colorYellow = new Color(232, 232, 50);
			// Màu nâu
			Color colorBrown = new Color(191, 143, 0);
			if (status.equals(Status.TableFree)) {
				urlImage = "icon/table_free.jpg";
				container.setBackground(colorGreen);
				lblNumMoney.setText("0");
				lblGuess.setText("");
				lblTime.setText("");
			}
			if (status.equals(Status.TableKitchen)) {
				urlImage = "icon/table_kitchen.png";
				container.setBackground(colorRed);
			}
			if (status.equals(Status.TableServe)) {
				urlImage = "icon/table_served.jpg";
				container.setBackground(colorRed);
			}
			if (status.equals(Status.TableBusy)) {
				urlImage = "icon/table_recorded.png";
				container.setBackground(colorRed);
			}
			if (status.equals(Status.TableSet)) {
				urlImage = "icon/table_desert.jpg";
				container.setBackground(colorBrown);
			}
			if (status.equals(Status.TableGross)) {
				if (getTable().getInvoiceCode() != null && !getTable().getInvoiceCode().equals("")) {
					InvoiceDetail invoice = AccountingModelManager.getInstance().getInvoiceDetail(Long.parseLong(ban.getInvoiceCode()));
					if (invoice != null) {
						urlImage = "icon/table_recorded.png";
						container.setBackground(colorRed);
						lblTime.setText(new SimpleDateFormat("HH:mm").format(invoice.getStartDate()) + "");
						lblNumMoney.setText(MyDouble.valueOf(invoice.getFinalCharge()).toString());
						lblUnitMoney.setText(invoice.getCurrencyUnit() + "");
					}
				} else {
					container.setBackground(colorGreen);
					urlImage = "icon/table_free.jpg";
					lblNumMoney.setText("0");
					lblTime.setText("");
				}
				lblGuess.setText("Gộp");
			}
			if (status.equals(Status.InActivate)) {
				urlImage = "icon/table_free.jpg";
				container.setBackground(colorGreen);
				lblNumMoney.setText("0");
				lblGuess.setText("");
				lblTime.setText("");
			}
			ban.setStatus(status);
			try {
				BufferedImage img = ImageIO.read(HomeScreen.class.getResource(urlImage));
				Image dimg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
				ImageIcon imageIcon = new ImageIcon(dimg);
				lblPicture.setIcon(imageIcon);
			} catch (Exception ex) {
				ex.printStackTrace();
				lblPicture.setBackground(Color.ORANGE);
			}
			lblPicture.updateUI();
			this.updateUI();
			try {
				((JPanel)this.getParent()).updateUI();
      } catch (Exception e) {
      }
			
			PanelC_Right.updateUI();
		}

		public JPanel getContainer() {
			return container;
		}

		public void setContainer(JPanel container) {
			this.container = container;
		}

		@Override
		public String toString() {
			return ban.getName();
		}
	}

	private class ComponentLocation extends JPanel {
		private JCheckBox	chkLocation;
		private Location	loca						= null;
		private JPanel		rowPanelTables	= null;

		public ComponentLocation(String nameLocation) {
			init();
			chkLocation.setText(nameLocation);
		}

		public ComponentLocation(Location location) {
			this.loca = location;
			init();
			chkLocation.setText(location.getName());
			chkLocation.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					panelLocationSelected = ComponentLocation.this;
					boolean flagSelected = ((JCheckBox) e.getSource()).isSelected();
					if (rowPanelTables != null) {
						JCheckBox chkAll = ((ComponentLocation) PanelC_Left.getComponent(0)).getChkLocation();
						boolean flagLocationAll = chkAll.isSelected();
						// Nếu nút Tất cả khu vực được check thì bỏ check và ẩn tất cả bàn
						// đi
						if (flagSelected && flagLocationAll) {
							((ComponentLocation) PanelC_Left.getComponent(0)).getChkLocation().setSelected(!flagSelected);
							for (Entry<String, ComponentLocation> entry : listLocationTables.entrySet()) {
								ComponentLocation panelLocation = listLocationTables.get(entry.getKey());
								JPanel panelRowTables = panelLocation.getRowPanelTables();
								if (panelRowTables != null)
									panelRowTables.setVisible(false);
							}
							PanelC_Right.setPreferredSize(new Dimension(widthMax, 0));
							PanelC_Right.setSize(new Dimension(widthMax, 0));
						}
						int h = rowPanelTables.getPreferredSize().height;
						if (flagSelected) {
							h = PanelC_Right.getPreferredSize().height + h;
						} else {
							h = PanelC_Right.getPreferredSize().height - h;
						}
						rowPanelTables.setVisible(flagSelected);
						PanelC_Right.setPreferredSize(new Dimension(widthMax - zz, h));
						PanelC_Right.setSize(new Dimension(widthMax - zz, h));
						PanelC_Right.updateUI();
						PanelScreenSaleLocationPos.this.updateUI();
					}
				}
			});
		}

		private void init() {
			this.setLayout(new BorderLayout());
			this.setBackground(new Color(255, 200, 0));
			// this.setMaximumSize(new Dimension(100, 60));

			chkLocation = new JCheckBox();
			chkLocation.setOpaque(false);
			this.add(chkLocation, BorderLayout.CENTER);
		}

		public JCheckBox getChkLocation() {
			return chkLocation;
		}

		public Location getLoca() {
			return loca;
		}

		public void setLoca(Location location) {
			this.loca = location;
			chkLocation.setText(loca.getName());
			this.updateUI();
		}

		public JPanel getRowPanelTables() {
			return rowPanelTables;
		}

		public void setRowPanelTables(JPanel rowPanelTables) {
			this.rowPanelTables = rowPanelTables;
		}

		public void setCancelStatusTables() {
			for (int i = 0; i < rowPanelTables.getComponentCount(); i++) {
				ComponentTable panelTable = (ComponentTable) rowPanelTables.getComponent(i);
				panelTable.setTableStatus(Status.TableFree);
			}
			this.updateUI();
		}

		public void setCancelSelectedTables() {
			for (int i = 0; i < rowPanelTables.getComponentCount(); i++) {
				ComponentTable panelTable = (ComponentTable) rowPanelTables.getComponent(i);
				panelTable.setTableSelected(false);
			}
			this.updateUI();
		}
	}

	private void initComponents() {
		/** Khởi tạo các thành phần giao diện */
		// Các components chứa
		JPanel PANEL_TOP = new JPanel();
		JPanel PANEL_CENTER = new JPanel();
		JPanel PANEL_BOTTOM = new JPanel();
		PanelC_Left = new JPanel();
		PanelC_Right = new JPanel();
		ScrollPane_Left = new JScrollPane();
		ScrollPane_Right = new JScrollPane();
//		ScrollPane_Right.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		// Các components trên PANEL_TOP
		btnQlBep = new ExtendJButton("<html><p align='center'>Quản lý bếp</p></html>");
		btnQlKhachNo = new ExtendJButton("<html><p align='center'>Quản lý<br>khách nợ</br></p></html>");
		btnQlDatTruoc = new ExtendJButton("<html><p align='center'>Quản lý<br>đặt trước</br></p></html>");
		btnDatBan = new ExtendJButton("<html><p align='center'>Đặt trước<br>bàn/quầy</br></p></html>");
		btnBanNhanh = new ExtendJButton("<html><p align='center'>Bán nhanh</p></html>");
		cboDvTien = new UnitMoneyJComboBox();
		lblDvTien = new ExtendJLabel("Đơn vị tiền");
		lblHKT = new ExtendJLabel("HKT RESTO");
		rdBangGia1 = new ExtendJRadioButton("<html>Bảng giá 1</html>");
		rdBangGia2 = new ExtendJRadioButton("<html>Bảng giá 2</html>");
		rdBangGia3 = new ExtendJRadioButton("<html>Bảng giá 3</html>");
		rdBangGia1.setName("");
		rdBangGia2.setName("");
		rdBangGia3.setName("");
		groupRadioButton = new ButtonGroup();
		groupRadioButton.add(rdBangGia1);
		groupRadioButton.add(rdBangGia2);
		groupRadioButton.add(rdBangGia3);

		lblHKT.setFont(new java.awt.Font("Tahoma", 1, 16));
		lblHKT.setHorizontalAlignment(SwingConstants.CENTER);
		lblHKT.setPreferredSize(new Dimension(125, lblHKT.getHeight()));
		btnQlBep.setFont(new java.awt.Font("Tahoma", 0, 12));
		btnQlKhachNo.setFont(new java.awt.Font("Tahoma", 0, 12));
		btnQlDatTruoc.setFont(new java.awt.Font("Tahoma", 0, 12));
		btnDatBan.setFont(new java.awt.Font("Tahoma", 0, 12));
		btnBanNhanh.setFont(new java.awt.Font("Tahoma", 0, 12));
		lblDvTien.setFont(new java.awt.Font("Tahoma", 0, 12));
		lblDvTien.setHorizontalAlignment(SwingConstants.CENTER);
		rdBangGia1.setFont(new Font("Tahoma", 0, 14));
		rdBangGia2.setFont(new Font("Tahoma", 0, 14));
		rdBangGia3.setFont(new Font("Tahoma", 0, 14));
		rdBangGia1.setPreferredSize(new Dimension(120, 45));
		rdBangGia2.setPreferredSize(new Dimension(120, 45));
		rdBangGia3.setPreferredSize(new Dimension(120, 45));
		rdBangGia1.setVisible(false);
		rdBangGia2.setVisible(false);
		rdBangGia3.setVisible(false);

		// Các components trên PANEL_BOTTOM
		btnTuyChonHTBan = new ExtendJButton("<html><p align='center'>Ẩn bàn/quầy<br>có khách</br></p></html>");
		lblIsTableFree = new ExtendJLabel("Bàn trống");
		lblIsGuest = new ExtendJLabel("Có khách");
		lblNumTableFree = new ExtendJLabel("0");
		lblNumTableBussy = new ExtendJLabel("0");
		btnThoat = new ExtendJButton("<html><p align='center'>Thoát</p></html>");
		btnThietLapBh = new ExtendJButton("<html><p align='center'>Thiết lập<br>bán hàng</br></p></html>");
		btnXoa = new ExtendJButton("<html><p align='center'>Xóa bàn</p></html>");
		btnSuaTen = new ExtendJButton("<html><p align='center'>Sửa tên</p></html>");
		btnMoBan = new ExtendJButton("<html><p align='center'>Mở khu vực<br>bàn/quầy</br></p></html>");
		btnGopTachBan = new ExtendJButton("<html><p align='center'>Gộp bàn/quầy</p></html>");
		btnChuyenBan = new ExtendJButton("<html><p align='center'>Chuyển bàn/quầy</p></html>");
		lblGhiChu = new ExtendJLabel("Ghi chú");

		btnTuyChonHTBan.setFont(new java.awt.Font("Tahoma", 0, 12));
		lblIsTableFree.setFont(new java.awt.Font("Tahoma", 1, 12));
		lblIsGuest.setFont(new java.awt.Font("Tahoma", 1, 12));
		lblNumTableFree.setFont(new java.awt.Font("Tahoma", 1, 12));
		lblNumTableBussy.setFont(new java.awt.Font("Tahoma", 1, 12));
		lblIsTableFree.setPreferredSize(new Dimension(70, 20));
		lblIsGuest.setPreferredSize(new Dimension(70, 20));
		lblNumTableFree.setPreferredSize(new Dimension(30, 20));
		lblNumTableBussy.setPreferredSize(new Dimension(30, 20));
		btnThoat.setFont(new java.awt.Font("Tahoma", 0, 12));
		btnThietLapBh.setFont(new java.awt.Font("Tahoma", 0, 12));
		btnXoa.setFont(new java.awt.Font("Tahoma", 0, 12));
		btnSuaTen.setFont(new java.awt.Font("Tahoma", 0, 12));
		btnMoBan.setFont(new java.awt.Font("Tahoma", 0, 12));
		btnGopTachBan.setFont(new java.awt.Font("Tahoma", 0, 12));
		btnChuyenBan.setFont(new java.awt.Font("Tahoma", 0, 12));
		lblGhiChu.setFont(new java.awt.Font("Tahoma", 0, 12));
		lblGhiChu.setPreferredSize(new Dimension(120, 45));
		lblGhiChu.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumTableBussy.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNumTableFree.setHorizontalAlignment(SwingConstants.RIGHT);

		/** Add các thành phần vào giao diện */
		// Panel tổng
		widthScreen = Toolkit.getDefaultToolkit().getScreenSize().width;
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setLayout(new BorderLayout(0, 0));
		this.setOpaque(false);
		this.add(PANEL_TOP, BorderLayout.NORTH);
		this.add(PANEL_CENTER, BorderLayout.CENTER);
		this.add(PANEL_BOTTOM, BorderLayout.SOUTH);

		createPanelNote();	
		JPanel panel = new JPanel(new BorderLayout(0, 0));
		panel.setOpaque(false);
		panel.add(ScrollPane_Right, BorderLayout.CENTER);
		panel.add(panelNote, BorderLayout.PAGE_END);
		
		// Panel giữa
		PANEL_CENTER.setLayout(new BorderLayout(3, 0));
		PANEL_CENTER.setOpaque(false);
		PANEL_CENTER.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 3));
		PANEL_CENTER.add(ScrollPane_Left, BorderLayout.WEST);		
		PANEL_CENTER.add(panel, BorderLayout.CENTER);

		/**
		 * TRƯỜNG HỢP MÀN HÌNH <= 800 * 600
		 */
		if (widthScreen <= 1024) {
			// Khởi tạo các button thu nhỏ
			btnQuanLyChung = new ExtendJButton("<html><p align='center'>Thiết lập chung</p></html>");
			btnQuanLyChung.setFont(new java.awt.Font("Tahoma", 0, 12));
			btnQuanLyChung.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					List<JButton> buttons = new ArrayList<JButton>();
					buttons.add(btnDatBan);
					buttons.add(btnQlDatTruoc);
					buttons.add(btnQlKhachNo);
					DialogResto dialog = new DialogResto(new PanelGroupButton(buttons), false, 0, 120);
					dialog.setTitle("Quản lý chung");
					dialog.setBtnSave(false);
					dialog.setLocationRelativeTo(null);
					dialog.setModal(true);
					dialog.setVisible(true);
				}
			});
			btnThietLapBan = new ExtendJButton("<html><p align='center'>Thiết lập bàn</p></html>");
			btnThietLapBan.setFont(new java.awt.Font("Tahoma", 0, 12));
			btnThietLapBan.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					List<JButton> buttons = new ArrayList<JButton>();
					buttons.add(btnMoBan);
					buttons.add(btnSuaTen);
					buttons.add(btnXoa);
					if (widthScreen <= 800) {
						buttons.add(btnGopTachBan);
						buttons.add(btnChuyenBan);
					}
					DialogResto dialog = new DialogResto(new PanelGroupButton(buttons), false, 0, 120);
					dialog.setTitle("Thiết lập bàn");
					dialog.setBtnSave(false);
					dialog.setLocationRelativeTo(null);
					dialog.setModal(true);
					dialog.setVisible(true);
				}
			});

			// Panel trên
			PANEL_TOP.setLayout(new BorderLayout(0, 0));
			PANEL_TOP.setOpaque(false);
			PANEL_TOP.add(lblHKT, BorderLayout.WEST);
			JPanel panelTop1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			panelTop1.setOpaque(false);
			panelTop1.add(rdBangGia1);
			panelTop1.add(rdBangGia2);
			panelTop1.add(rdBangGia3);
			JPanel panelTopC1 = new JPanel(new GridLayout(2, 1));
			panelTopC1.setPreferredSize(new Dimension(60, 45));
			panelTopC1.setOpaque(false);
			panelTopC1.add(lblDvTien);
			panelTopC1.add(cboDvTien);
			panelTop1.add(panelTopC1);
			panelTop1.add(btnBanNhanh);
			panelTop1.add(btnQuanLyChung);
			PANEL_TOP.add(panelTop1, BorderLayout.CENTER);

			// Thiết lập lại các kích thước components TopPanel
			PANEL_TOP.setPreferredSize(new Dimension(widthScreen, 55));
			rdBangGia1.setPreferredSize(new Dimension(100, 45));
			rdBangGia2.setPreferredSize(new Dimension(100, 45));
			rdBangGia3.setPreferredSize(new Dimension(100, 45));

			// Panel dưới
			PANEL_BOTTOM.setLayout(new BorderLayout(0, 0));
			PANEL_BOTTOM.setOpaque(false);
			JPanel panelBottom1 = new JPanel();
			panelBottom1.setOpaque(false);
			panelBottom1.setPreferredSize(new Dimension(125, PANEL_BOTTOM.getHeight()));
			panelBottom1.add(btnTuyChonHTBan);
			PANEL_BOTTOM.add(panelBottom1, BorderLayout.WEST);
			JPanel panelBottom2 = new JPanel(new BorderLayout(0, 0));
			panelBottom2.setOpaque(false);
			JPanel panelBottom2Left = new JPanel(new FlowLayout(FlowLayout.LEFT));
			panelBottom2Left.setOpaque(false);
			panelBottom2Left.setPreferredSize(new Dimension(140, 55));
			panelBottom2Left.add(lblIsTableFree);
			panelBottom2Left.add(lblNumTableFree);
			panelBottom2Left.add(lblIsGuest);
			panelBottom2Left.add(lblNumTableBussy);
			JPanel panelBottom2Right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			panelBottom2Right.setOpaque(false);
			if (widthScreen > 800) {
				panelBottom2Right.add(btnGopTachBan);
				panelBottom2Right.add(btnChuyenBan);
			}
			panelBottom2Right.add(btnThietLapBan);
			panelBottom2Right.add(lblGhiChu);
			panelBottom2Right.add(btnThietLapBh);
			panelBottom2Right.add(btnThoat);
			panelBottom2.add(panelBottom2Left, BorderLayout.WEST);
			panelBottom2.add(panelBottom2Right, BorderLayout.EAST);
			PANEL_BOTTOM.add(panelBottom2, BorderLayout.CENTER);

			// Thiết lập lại các kích thước components TopPanel
			PANEL_BOTTOM.setPreferredSize(new Dimension(widthScreen, 55));
		}
		/**
		 * TRƯỜNG HỢP MÀN HÌNH LỚN HƠN
		 */
		else {
			// Panel trên
			PANEL_TOP.setLayout(new BorderLayout(0, 0));
			PANEL_TOP.setOpaque(false);
			PANEL_TOP.setPreferredSize(new Dimension(widthScreen, 55));
			PANEL_TOP.add(lblHKT, BorderLayout.WEST);
			JPanel panelTop1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			panelTop1.setOpaque(false);
			panelTop1.add(rdBangGia1);
			panelTop1.add(rdBangGia2);
			panelTop1.add(rdBangGia3);
			JPanel panelTopC1 = new JPanel(new GridLayout(2, 1));
			panelTopC1.setOpaque(false);
			panelTopC1.add(lblDvTien);
			panelTopC1.add(cboDvTien);
			panelTop1.add(panelTopC1);
			panelTop1.add(btnBanNhanh);
			panelTop1.add(btnDatBan);
			panelTop1.add(btnQlDatTruoc);
			panelTop1.add(btnQlKhachNo);
			PANEL_TOP.add(panelTop1, BorderLayout.CENTER);

			// Panel dưới
			PANEL_BOTTOM.setLayout(new BorderLayout(0, 0));
			PANEL_BOTTOM.setOpaque(false);
			PANEL_BOTTOM.setPreferredSize(new Dimension(widthScreen, 55));
			JPanel panelBottom1 = new JPanel();
			panelBottom1.setOpaque(false);
			panelBottom1.setPreferredSize(new Dimension(125, PANEL_BOTTOM.getHeight()));
			panelBottom1.add(btnTuyChonHTBan);
			PANEL_BOTTOM.add(panelBottom1, BorderLayout.WEST);
			JPanel panelBottom2 = new JPanel(new BorderLayout(0, 0));
			panelBottom2.setOpaque(false);
			JPanel panelBottom2Left = new JPanel(new FlowLayout(FlowLayout.LEFT));
			panelBottom2Left.setOpaque(false);
			panelBottom2Left.setPreferredSize(new Dimension(140, 55));
			panelBottom2Left.add(lblIsTableFree);
			panelBottom2Left.add(lblNumTableFree);
			panelBottom2Left.add(lblIsGuest);
			panelBottom2Left.add(lblNumTableBussy);
			JPanel panelBottom2Right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			panelBottom2Right.setOpaque(false);
			panelBottom2Right.add(btnGopTachBan);
			panelBottom2Right.add(btnChuyenBan);
			panelBottom2Right.add(btnMoBan);
			panelBottom2Right.add(btnSuaTen);
			panelBottom2Right.add(btnXoa);
			panelBottom2Right.add(lblGhiChu);
			panelBottom2Right.add(btnThietLapBh);
			panelBottom2Right.add(btnThoat);
			panelBottom2.add(panelBottom2Left, BorderLayout.WEST);
			panelBottom2.add(panelBottom2Right, BorderLayout.EAST);
			PANEL_BOTTOM.add(panelBottom2, BorderLayout.CENTER);
		}

		btnDatBan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (panelTableSelected != null) {
					if (panelTableSelected.getTable().getInvoiceCode() == null || panelTableSelected.getTable().getInvoiceCode().equals("")) {
						if (panelTableSelected.getTable().getStatus().equals(Table.Status.TableSet)) {
							panelTableSelected.setTableStatus(Table.Status.TableFree);
							try {
								RestaurantModelManager.getInstance().saveTable(panelTableSelected.getTable());
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						} else {
							try {
								PanelTableSet panel = new PanelTableSet(panelTableSelected.getTable());
								DialogResto dialog = new DialogResto(panel, false, 0, 250);
								dialog.setTitle("Đặt trước " + DialogConfig.Table + "");
								dialog.setLocationRelativeTo(null);
								dialog.setModal(true);
								dialog.setVisible(true);
								// Cap nhat lai giao dien
								if (panel.isFlagSave())
									panelTableSelected.setTableStatus(Table.Status.TableSet);
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}
					} else {
						PanelChoise pnPanel = new PanelChoise("Bàn " + panelTableSelected.getTable().getName() + " đã có khách!");
						pnPanel.setName("ThongBao");
						DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
						dialog1.setName("dlThongBao");
						dialog1.setTitle("Thông báo");
						dialog1.setLocationRelativeTo(null);
						dialog1.setModal(true);
						dialog1.setVisible(true);
						if (pnPanel.isDelete()) {
							return;
						}
					}
				} else {
					PanelChoise pnPanel = new PanelChoise("Bạn phải chọn bàn trước!");
					pnPanel.setName("ThongBao");
					DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
					dialog1.setName("dlThongBao");
					dialog1.setTitle("Thông báo");
					dialog1.setLocationRelativeTo(null);
					dialog1.setModal(true);
					dialog1.setVisible(true);
					if (pnPanel.isDelete()) {
						return;
					}
				}
				if (widthScreen <= 1024) {
					(((JDialog) btnDatBan.getRootPane().getParent())).dispose();
				}
			}
		});

		btnQlDatTruoc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
				dialog.setName("dlQLDatbanQuay");
				dialog.setIcon("home/datban1.png");
				dialog.setTitle("Quản lý đặt bàn");
				dialog.setModal(true);
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
				if (widthScreen <= 1024) {
					(((JDialog) btnQlDatTruoc.getRootPane().getParent())).dispose();
				}
			}
		});

		btnQlKhachNo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PanelSearchInvoice panel = new PanelSearchInvoice();
				panel.setName("PanelSearchInvoice");
				DialogResto dialog = new DialogResto(panel, false, 0, 160);
				dialog.setIcon("invoiceds1.png");
				dialog.setName("dlPanelSearchInvoice");
				dialog.setLocationRelativeTo(null);
				dialog.setTitle("Danh sách công nợ");
				dialog.setVisible(true);
				if (panel.isSave()) {
					panel.createTableDebt();
				}
				if (widthScreen <= 1024) {
					(((JDialog) btnQlKhachNo.getRootPane().getParent())).dispose();
				}
			}
		});

		lblGhiChu.addMouseListener(new MouseEventMove() {
			public void mouseClicked(MouseEvent e) {
				if(panelNote.isVisible()){
					panelNote.setVisible(false);
				} else {
					panelNote.setVisible(true);
				}
			}
		});

		btnXoa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (panelTableSelected != null) {
					PanelDeleteTableLocation panel = new PanelDeleteTableLocation(panelTableSelected.getTable());
					DialogResto dialog = new DialogResto(panel, false, 0, 240);
					dialog.setTitle("Xóa bàn - khu vực");
					dialog.setLocationRelativeTo(null);
					dialog.setModal(true);
					dialog.setVisible(true);
					if (panel.isSave()) {
						try {
							// Load lai giao dien
							if (panel.isDeleteTable()) {
								// Neu xoa tat ca ban trong khu vuc
								if (panel.isDeleteAllTableInLocation()) {
									Location l = panel.getLocationDeleteAllTable();
									PanelC_Right.remove(listLocationTables.get(l.getCode()).getRowPanelTables());
									listLocationTables.remove(l.getCode());
									// panelTableSelected = null;
								}
								// Neu xoa 1 ban
								else {
									ComponentLocation cl = listLocationTables.get(panelTableSelected.getTable().getLocationCode());
									for (int j = 0; j < cl.getRowPanelTables().getComponentCount(); j++) {
										if (((ComponentTable) cl.getRowPanelTables().getComponent(j)).getTable().getCode().equals(panelTableSelected.getTable().getCode())) {
											cl.getRowPanelTables().remove(j);
											cl.getRowPanelTables().updateUI();
											panelTableSelected = null;
											break;
										}
									}
								}
							} else {
								// Neu xoa khu vuc
								if (panel.isDeleteLocation()) {
									PanelC_Right.remove(listLocationTables.get(panel.getDeleteLocation().getCode()).getRowPanelTables());
									PanelC_Left.remove(listLocationTables.get(panel.getDeleteLocation().getCode()));
									listLocationTables.remove(panel.getDeleteLocation().getCode());
									PanelC_Left.updateUI();
								}
							}
							PanelC_Right.updateUI();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
				if (widthScreen <= 1024) {
					(((JDialog) btnXoa.getRootPane().getParent())).dispose();
				}
			}
		});

		btnSuaTen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (panelTableSelected != null) {
					PanelRenameTable renameTable = new PanelRenameTable(panelTableSelected);
					DialogResto dialog = new DialogResto(renameTable, false, 0, 100);
					dialog.setTitle("Đổi tên bàn");
					dialog.setVisible(true);
					dialog.setLocationRelativeTo(null);
					if (renameTable.isEdit()) {
						panelTableSelected.setTable(renameTable.getTable());
					}
				}
				if (widthScreen <= 1024) {
					(((JDialog) btnSuaTen.getRootPane().getParent())).dispose();
				}
			}
		});

		btnChuyenBan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (panelTableSelected != null) {
					Table table = panelTableSelected.getTable();
					try {
						InvoiceDetail invoiceDetail = AccountingModelManager.getInstance().saveInvoice(ScreenOften.getInstance().getTableModle().getInvoiceDetail());
						table.setInvoiceCode(String.valueOf(invoiceDetail.getId()));
						table = RestaurantModelManager.getInstance().saveTable(table);
					} catch (Exception e1) {
					}

					PanelMoveTable panelMoveTable = new PanelMoveTable(table);
					panelMoveTable.setName("panelMoveTable");
					DialogResto dialog;
					if (widthScreen <= 1024) {
						dialog = new DialogResto(panelMoveTable, true, 800, 500);
					} else {
						dialog = new DialogResto(panelMoveTable, false, 1050, 550);
					}
					dialog.setName("dlMoveTable");
					dialog.setTitle("Chuyển bàn/quầy");
					dialog.dispose();
					dialog.setUndecorated(true);
					dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
					dialog.setLocationRelativeTo(null);
					dialog.setModal(true);
					dialog.setVisible(true);
					if (panelMoveTable.isSave()) {
						try {
							Table tableDestination = panelMoveTable.getTableDestination();
							ComponentLocation cl = listLocationTables.get(tableDestination.getLocationCode());
							for (int j = 0; j < cl.getRowPanelTables().getComponentCount(); j++) {
								ComponentTable ct = (ComponentTable) cl.getRowPanelTables().getComponent(j);
								if (ct.getTable().getCode().equals(tableDestination.getCode())) {
									ct.setTable(tableDestination);
									panelTableSelected.setTable(panelMoveTable.getTableSource());
									break;
								}
							}
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				}

			}
		});

		btnGopTachBan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Nếu là gộp bàn
				if (listTemp != null && listTemp.size() > 0) {
					int countTableFree = 0;
					List<Table> tables = new ArrayList<Table>();
					boolean flag = false;
					for (Object o : listTemp) {
						Table table = ((ComponentTable) o).getTable();
						tables.add(table);
						if (panelTableSelected != null && panelTableSelected.getTable().getCode().equals(table.getCode())) {
							flag = true;
						}
						if(table.getStatus().equals(Table.Status.TableFree)){
							countTableFree++;
						}
					}
					if (panelTableSelected != null && !flag) {
						Table table = panelTableSelected.getTable();
						tables.add(table);
						listTemp.add(panelTableSelected);
						if(table.getStatus().equals(Table.Status.TableFree)){
							countTableFree++;
						}
					}
					try {
						PanelTableGroup panelTableGroup = new PanelTableGroup(tables);
						panelTableGroup.setName("panelTableGroup");
						DialogResto dialog = new DialogResto(panelTableGroup, false, 0, 130);
						dialog.dispose();
						dialog.setUndecorated(true);
						dialog.setName("dlTableGroup");
						dialog.setTitle("Gộp bàn/quầy");
						dialog.setLocationRelativeTo(null);
						dialog.setModal(true);
						dialog.setVisible(true);
						if (panelTableGroup.isSave()) {
							String codetableGross = panelTableGroup.getTableGross().getCode();
							for (Object o : listTemp) {
								ComponentTable ct = (ComponentTable) o;
								if (ct.getTable().getCode().equals(codetableGross)) {
									ct.setTableStatus(Status.TableGross);
									listTableBussy.put(codetableGross, ct);
								} else {
									ct.setTableStatus(Status.InActivate);
									ct.setVisible(false);
									listTableBussy.remove(ct.getTable().getCode());
								}
							}
							lblNumTableBussy.setText((countTableFree + Integer.parseInt(lblNumTableBussy.getText())) + "");
							lblNumTableFree.setText((Integer.parseInt(lblNumTableFree.getText()) - countTableFree) + "");
							panelTableSelected = null;
							listTemp.clear();
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				// Nếu là tách bàn
				else {
					if (panelTableSelected != null && panelTableSelected.getTable().getStatus().equals(Table.Status.TableGross) && panelTableSelected.getTable().getReservations() != null && panelTableSelected.getTable().getReservations().size() > 0) {
						DialogSplitTable dialog = new DialogSplitTable(panelTableSelected.getTable());
						DialogResto dialog1 = new DialogResto(dialog, false, 0, 110);
						dialog1.setTitle("Tách bàn");
						dialog1.setBtnSave(false);
						dialog1.setLocationRelativeTo(null);
						dialog1.setModal(true);
						dialog1.setVisible(true);
						// Khi ấn tách tất cả hoặc tách riêng rẽ
						if (dialog.isSuccsess()) {
							panelTableSelected.setTable(dialog.getTableGross());
							List<Table> listSplitTables = dialog.getListSplitTables();
							for (Table t : listSplitTables) {
								JPanel row = listLocationTables.get(t.getLocationCode()).getRowPanelTables();
								boolean flag = false;
								for (int j = 0; j < row.getComponentCount(); j++) {
									ComponentTable cell = (ComponentTable) row.getComponent(j);
									if (cell.getTable().getCode().equals(t.getCode())) {
										cell.setVisible(true);
										flag = true;
										break;
									}
								}
								if (!flag) {
									// Nếu không tìm thấy bàn thì tạo mới ComponentTable và add
									// vào
									ComponentLocation cl = listLocationTables.get(t.getLocationCode());
									if (cl.getChkLocation().isSelected()) {
										int h = PanelC_Right.getPreferredSize().height - row.getPreferredSize().height;
										PanelC_Right.setPreferredSize(new Dimension(widthMax - 3, h));
										PanelC_Right.setSize(new Dimension(widthMax - 3, h));
									}
									heightMore = heightMore - row.getPreferredSize().height;

									ComponentTable panelTable = new ComponentTable(t);
									row.add(panelTable);

									int n = (widthScreen <= 800) ? 4 : 7;
									int totalComp = row.getComponentCount();
									int rowCount = (totalComp % n) != 0 ? (totalComp / n + 1) : (totalComp / n);
									int height = rowCount * 88;
									heightMore = heightMore + height;
									row.setSize(new Dimension(widthMax, height));
									row.setPreferredSize(new Dimension(widthMax, height));
									if (cl.getChkLocation().isSelected()) {
										int h = PanelC_Right.getPreferredSize().height + row.getPreferredSize().height;
										PanelC_Right.setPreferredSize(new Dimension(widthMax - 3, h));
										PanelC_Right.setSize(new Dimension(widthMax - 3, h));
									} else {
										row.setVisible(false);
									}
									PanelC_Right.updateUI();
									updateUI();
								}
								if (panelTableSelected.getTable().getStatus().equals(Table.Status.TableBusy)) {
									if (panelTableSelected.getTable().getInvoiceCode() != null && !panelTableSelected.getTable().getInvoiceCode().equals("")) {
										InvoiceDetail invoice = AccountingModelManager.getInstance().getInvoiceDetail(Long.parseLong(panelTableSelected.getTable().getInvoiceCode()));
										if (invoice != null) {
											panelTableSelected.getLblGuess().setText(invoice.getGuest() + "");
											panelTableSelected.getLblTime().setText(new SimpleDateFormat("HH:mm").format(invoice.getStartDate()) + "");
											panelTableSelected.getLblNumMoney().setText(MyDouble.valueOf(invoice.getFinalCharge()).toString());
											panelTableSelected.getLblUnitMoney().setText(invoice.getCurrencyUnit() + "");
										}
									}
								} else {
									panelTableSelected.setTableStatus(panelTableSelected.getTable().getStatus());
								}
							}
							lblNumTableFree.setText((listSplitTables.size() + Integer.parseInt(lblNumTableFree.getText())) + "");
							lblNumTableBussy.setText((Integer.parseInt(lblNumTableBussy.getText()) - listSplitTables.size()) + "");
						}
					}
				}
			}
		});

		btnThoat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				((JDialog) getRootPane().getParent()).dispose();
			}
		});

		btnMoBan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PanelOpenTableArea panel = new PanelOpenTableArea();
				if (panelLocationSelected != null) {
					Location locationSelected = panelLocationSelected.getLoca();
					if (locationSelected != null) {
						panel.setComboBoxLocationOther(locationSelected.getCode());
					}
				}
				DialogResto dialog = new DialogResto(panel, false, 0, 280);
				dialog.setTitle("Mở khu vực " + DialogConfig.Table + "");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
				if (widthScreen <= 1024) {
					(((JDialog) btnMoBan.getRootPane().getParent())).dispose();
				}
			}
		});

		btnThietLapBh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DialogConfig dialog = new DialogConfig(false, 600, 100);
				dialog.setName("dlThietLapBH");
				dialog.setTitle("Thiết lập bán hàng");
				dialog.setVisible(true);
				getProfileConfigData();
			}
		});

		btnTuyChonHTBan.setName("An ban quay co khach");
		btnTuyChonHTBan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (btnTuyChonHTBan.getName().equals("An ban quay co khach")) {
					for (Entry<String, ComponentTable> entry : listTableBussy.entrySet()) {
						ComponentTable ct = entry.getValue();
						if (!ct.getTable().getStatus().equals(Table.Status.InActivate)) {
							ct.setVisible(false);
						}
					}
					btnTuyChonHTBan.setText("<html><p align='center'>Hiện bàn/quầy<br>có khách</br></p></html>");
					btnTuyChonHTBan.setName("Hien ban quay co khach");
				} else {
					if (btnTuyChonHTBan.getName().equals("Hien ban quay co khach")) {
						for (Entry<String, ComponentLocation> entry : listLocationTables.entrySet()) {
							JPanel row = entry.getValue().getRowPanelTables();
							for (int j = 0; j < row.getComponentCount(); j++) {
								ComponentTable ct = (ComponentTable) row.getComponent(j);
								if (!ct.getTable().getStatus().equals(Table.Status.InActivate)) {
									if (ct.getTable().getInvoiceCode() != null && !ct.getTable().getInvoiceCode().equals("")) {
										ct.setVisible(true);
									} else {
										ct.setVisible(false);
									}
								}
							}
						}
						btnTuyChonHTBan.setText("<html><p align='center'>Hiện thị tất cả bàn/quầy</p></html>");
						btnTuyChonHTBan.setName("Hien thi tat ca");
					} else {
						if (btnTuyChonHTBan.getName().equals("Hien thi tat ca")) {
							for (Entry<String, ComponentLocation> entry : listLocationTables.entrySet()) {
								JPanel row = entry.getValue().getRowPanelTables();
								for (int j = 0; j < row.getComponentCount(); j++) {
									ComponentTable ct = (ComponentTable) row.getComponent(j);
									if (!ct.getTable().getStatus().equals(Table.Status.InActivate)) {
										ct.setVisible(true);
									}
								}
							}
							btnTuyChonHTBan.setText("<html><p align='center'>Ẩn bàn/quầy<br>có khách</br></p></html>");
							btnTuyChonHTBan.setName("An ban quay co khach");
						}
					}
				}
			}
		});

		btnBanNhanh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Table tableOther = RestaurantModelManager.getInstance().getTableOther();
				DialogScreenOftenPos dialog = DialogScreenOftenPos.getInstance();
				ComponentTable tablePanelOther = new ComponentTable(tableOther);
				dialog.setTable(tablePanelOther);
				if (dialog.hashMapProducts != null) {
					dialog.resetGui();
					dialog.loadData();
				}
				dialog.setVisible(true);
			}
		});

		// btnQlBep.addActionListener(new ActionListener() {
		// @Override
		// public void actionPerformed(ActionEvent e) {
		//
		// }
		// });

		cboDvTien.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				Currency currency = cboDvTien.getSelectedCurrency();
				if (currency != null && !currencyCode.equals(currency.getCode())) {
					currencyCode = cboDvTien.getSelectedCurrency().getCode();
					profile.put(DialogConfig.Currency, currencyCode);
					AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(), profile);
					for (Entry<String, ComponentLocation> entry : listLocationTables.entrySet()) {
						JPanel panelRow = entry.getValue().getRowPanelTables();
						for (int i = 0; i < panelRow.getComponentCount(); i++) {
							ComponentTable ct = (ComponentTable) panelRow.getComponent(i);
							if (ct.getTable().getInvoiceCode() == null || ct.getTable().getInvoiceCode().equals(""))
								ct.getLblUnitMoney().setText(currency != null ? currency.getName() : "");
						}
					}
				}
			}
		});

		rdBangGia1.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				priceType = rdBangGia1.getName();
				profile.put(DialogConfig.ProductPriceType, priceType);
				AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(), profile);
			}
		});

		rdBangGia2.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				priceType = rdBangGia2.getName();
				profile.put(DialogConfig.ProductPriceType, priceType);
				AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(), profile);
			}
		});

		rdBangGia3.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				priceType = rdBangGia3.getName();
				profile.put(DialogConfig.ProductPriceType, priceType);
				AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(), profile);
			}
		});
	}
	
	public HashMap<String, ComponentTable> getListTableBussy() {
		return listTableBussy;
	}
	
	public ComponentTable getComponentTableOther(){
		Table tableOther = RestaurantModelManager.getInstance().getTablePaymentAfter();
		ComponentTable tablePanelOther = new ComponentTable(tableOther);
		return tablePanelOther;
	}
	
	public void resetTable(){
		if(switchScreen){
			try {
				int count = 0;
				List<Table> tables = RestaurantModelManager.getInstance().getTables();
				for(int i = 0; i < PanelC_Right.getComponentCount(); i++){
					JPanel panelTable = (JPanel)PanelC_Right.getComponent(i);
					for(int j= 0; j < panelTable.getComponentCount(); j++){
						ComponentTable componentTable = (ComponentTable)panelTable.getComponent(j);
						Table table = tables.get(count);
						componentTable.setTable(table);
						count++;
					}
				}
				PanelC_Right.updateUI();
				switchScreen = false;
			} catch(Exception ex) {
				ex.printStackTrace();
			}				
		}
	}

	public ComponentTable getPanelTableSelected() {
		return panelTableSelected;
	}

	public void setPanelTableSelected(ComponentTable panelTableSelected) {
		this.panelTableSelected = panelTableSelected;
	}

	public void addKeyBindings(JComponent jc) {
		// Phím tắt mở bàn và khu vực mới
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0, false), "F1");
		jc.getActionMap().put("F1", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				PanelOpenTableArea panel = new PanelOpenTableArea();
				if (panelLocationSelected != null) {
					Location locationSelected = panelLocationSelected.getLoca();
					if (locationSelected != null) {
						panel.setComboBoxLocationOther(locationSelected.getCode());
					}
				}
				DialogResto dialog = new DialogResto(panel, false, 0, 280);
				dialog.setTitle("Mở khu vực " + DialogConfig.Table + "");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
				if (widthScreen <= 1024) {
					(((JDialog) btnMoBan.getRootPane().getParent())).dispose();
				}
			}
		});
		
		// Phím tắt bán nhanh
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0, false), "F10");
		jc.getActionMap().put("F10", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				Table tableOther = RestaurantModelManager.getInstance().getTableOther();
				DialogScreenOftenPos dialog = DialogScreenOftenPos.getInstance();
				ComponentTable tablePanelOther = new ComponentTable(tableOther);
				dialog.setTable(tablePanelOther);
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
		});
	}
}
