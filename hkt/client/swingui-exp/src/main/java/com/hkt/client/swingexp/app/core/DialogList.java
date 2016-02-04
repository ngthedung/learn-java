package com.hkt.client.swingexp.app.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.hkt.client.swing.robot.console.JVMEnv;
import com.hkt.client.swingexp.app.banhang.screen.often.DialogConfig;
import com.hkt.client.swingexp.app.banhang.screen.often.TableRerenderSale;
import com.hkt.client.swingexp.app.component.ImageTool;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.component.PanelBackground;
import com.hkt.client.swingexp.app.component.PanelTrans;
import com.hkt.client.swingexp.app.component.TableFillterSorter;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.print.ReportGeneral;
import com.hkt.client.swingexp.app.virtualkey.text.PanelTextKeyboard;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.GuiModelManager;
import com.hkt.client.swingexp.model.TableSumAVG;
import com.hkt.module.account.entity.Profile;

@SuppressWarnings("serial")
public class DialogList extends JDialog implements ActionListener, IMyObserver {

	// Variables declaration - do not modify
	private JButton btnShowDetail;
	@SuppressWarnings("rawtypes")
	private JComboBox cbSearch, cboSize;
	private JButton btnExit;
	private JButton btnPrint;
	private JCheckBox chbSum;
	private JCheckBox chbAVG;
	private JLabel jLabel1, lbMeseg;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JPanel botPanel, panelComp, panelSearch;
	private JLabel labelS1, lableS2;
	private MyJDateChooser myJDateChooser1, myJDateChooser2;
	private JButton btnView;
	private JScrollPane jScrollPane1;
	private PanelBackground panelBackground1;
	private JTable table;
	private JTextField txtSearch;
	private ITableModel iTableModel;
	private ITableModelExt iTableModelExt;
	private DefaultTableModel tableModel;
	private TableFillterSorter tableFillterSorter;
	private int tableSelectedRow;
	private JButton btnPrevIndex, btnKeyboard, btnDelete;
	private JButton btnNextIndex;
	private JButton btnLast;
	private JButton btnFirts;
	private JButton[] button = new JButton[5];
	private JScrollPane btnExt1, btnExt2, btnExt3, component;
	private JLabel a, labelIcon, labelTitle;
	private PanelTextKeyboard panelTextKeyboard;
	private PanelTrans panel2;
	private Profile profile;
	private TableColumn t;
	private VisibleColumnTable visibleColumnTable;
	private List<String> listHeader = new ArrayList<String>();
	
	

	public JComboBox getCbSearch() {
		return cbSearch;
	}

	public JTextField getTxtSearch() {
		return txtSearch;
	}

	public void setComponent1(JComponent btn) {
		btn.setSize(btnExt1.getWidth() - 5, btnExt1.getHeight() - 5);
		btn.setPreferredSize(btn.getSize());
		btnExt1.setViewportView(btn);
	}

	public void setComponent2(JComponent btn) {
		btn.setSize(btnExt2.getWidth() - 5, btnExt2.getHeight() - 5);
		btn.setPreferredSize(btn.getSize());
		btnExt2.setViewportView(btn);
	}

	public void setComponent3(JComponent btn) {
		btn.setSize(btnExt3.getWidth() - 5, btnExt3.getHeight() - 5);
		btn.setPreferredSize(btn.getSize());
		btnExt3.setViewportView(btn);
	}

	public void setComponent4(JComponent btn, String txt) {
		btn.setSize(component.getWidth() - 5, component.getHeight() - 5);
		btn.setPreferredSize(btn.getSize());
		component.setViewportView(btn);
		if (txt != null) {
			if (txt.isEmpty()) {
				a.setVisible(false);
			} else {
				a.setText(txt);
			}

		} else {
			a.setText("Lựa chọn");
		}
	}

	private int currentPage = 1;
	private int pageSize = 22;
	private ITableMain tableMain;
	private boolean print;
	private ITableMainExt tableMainExt;

	public DialogList(ITableMain tableMain1) {
		setUndecorated(true);
		ImageIcon icon = new ImageIcon(HomeScreen.class.getResource("icon/icon.png"));
		Image im = icon.getImage();
		setIconImage(im);
		initComponents();
		addKeyBindings(panelBackground1);
		setModal(true);
		setFullSize();
		this.tableMain = tableMain1;
		this.table = (JTable) tableMain1;
		tableModel = (DefaultTableModel) table.getModel();
		try {
			iTableModel = (ITableModel) tableModel;
		} catch (Exception e) {
		}
		try {
			iTableModelExt = (ITableModelExt) tableModel;
		} catch (Exception e) {
			iTableModelExt=null;
		}
		if (tableMain instanceof ITableMainExt) {
			tableMainExt = (ITableMainExt) tableMain1;
			myJDateChooser1.setDate(tableMainExt.getDateStart());
			myJDateChooser2.setDate(tableMainExt.getDateEnd());
		} else {
			panelSearch.setVisible(false);
		}

		if (tableMain instanceof TableObservable) {
			((TableObservable) tableMain).addObserver(this);
		}
		init();
		this.table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int click = 2;
				try {
					JTable ta = (JTable) e.getSource();
					String value = ta.getValueAt(ta.getSelectedRow(), ta.getSelectedColumn()).toString();
					if (value.indexOf("HktTest") >= 0 && e.getButton() == 3) {
						click = 1;
					}
				} catch (Exception e2) {
				}

				if (e.getClickCount() >= click) {
					if (chbSum.isSelected()) {
						chbSum.setSelected(false);
					}
					if (chbAVG.isSelected()) {
						chbAVG.setSelected(false);
					}
					tableMain.click();
					// update("", "");

				}
			}
		});

		if (tableMain.getColumnSum() == null) {
			chbAVG.setVisible(false);
			chbSum.setVisible(false);

		}
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {
				print = true;
				currentPage = 1;
				if (tableMain.getListSize() <= 500) {
					cboSize.setSelectedIndex(cboSize.getItemCount() - 1);
				} else {
					cboSize.setSelectedIndex(0);
				}
				System.out.println("opendList   " + new JVMEnv().getMemoryInfo());
				addVituarKey(panelBackground1);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				if (print) {
					new GuiModelManager().getObservable().deleteObservers();
					System.out.println("closeList   " + new JVMEnv().getMemoryInfo());
					print = false;
				}
			}

		});
		panelTextKeyboard.setText(table);

		btnView.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (tableMainExt != null) {
					chbAVG.setSelected(false);
					chbSum.setSelected(false);
					tableMainExt.loadDataByTime(myJDateChooser1.getDate(), myJDateChooser2.getDate());
				}

			}
		});

		Profile profileTable = AccountModelManager.getInstance().getProfileConfigTable();
		try {
			if (profileTable.get(iTableModel.getNameTableModel()) != null) {
				listHeader = (List<String>) profileTable.get(iTableModel.getNameTableModel());
			}
		} catch (Exception e) {
		}
		cbSearch.setModel(new DefaultComboBoxModel(tableFillterSorter.getHashtable().keySet().toArray()));
		for(int i = 0; i< cbSearch.getItemCount(); i++){
			if(cbSearch.getItemAt(i).toString().indexOf("Tên")>=0
					||cbSearch.getItemAt(i).toString().indexOf("Barcode")>=0){
				cbSearch.setSelectedIndex(i);
				break;
			}
		}
	}

	@Override
	public void setTitle(String title) {
		labelTitle.setText(title);
		super.setTitle(title);
	}

	public void setIcon(String icon) {
		ImageIcon imageIcon = ImageTool.getInstance().resize(new ImageIcon(HomeScreen.class.getResource("icon/" + icon)),
		    labelIcon.getPreferredSize());
		labelIcon.setIcon(imageIcon);
	}

	private void init() {
		jScrollPane1.setViewportView(table);
		panelBackground1.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
		    KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0, false), "F12");
		panelBackground1.getActionMap().put("F12", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				printData();
			}
		});
		jScrollPane1.getViewport().setBackground(Color.white);
		table.setRowHeight(23);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

		cboSize.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (chbSum.isSelected()) {
					chbSum.setSelected(false);
				}
				if (chbAVG.isSelected()) {
					chbAVG.setSelected(false);
				}
				if (cboSize.getSelectedItem().toString().equals("Tất cả")) {
					pageSize = tableMain.getListSize();
					currentPage = getMaxPage();
					loadDataIntoTable(currentPage, pageSize);
					for (int i = 0; i < 5; i++) {
						button[i].setVisible(false);
					}
					btnPrevIndex.setVisible(false);
					btnNextIndex.setVisible(false);
					btnLast.setVisible(false);
					if (tableMain.getColumnSum() != null) {
						chbAVG.setVisible(true);
						chbSum.setVisible(true);

					}
				} else {
					pageSize = Integer.parseInt(cboSize.getSelectedItem().toString());
					currentPage = 1;
					drawButtonIndex(currentPage);
					chbAVG.setVisible(false);
					chbSum.setVisible(false);
				}
			}
		});
		botPanel.setOpaque(false);
		botPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 2, 8));

		btnPrevIndex = new JButton("<<");
		btnPrevIndex.setName("btnPrevIndex");
		btnPrevIndex.addActionListener(this);
		botPanel.add(btnPrevIndex);
		btnFirts = new JButton("Đầu");
		botPanel.add(btnFirts);
		for (int i = 0; i < 5; i++) {
			button[i] = new JButton(i + 1 + "");
			button[i].setSize(60, 26);
			button[i].setMaximumSize(new Dimension(60, 26));
			button[i].setPreferredSize(new Dimension(60, 26));
			button[i].addActionListener(this);
			botPanel.add(button[i]);
		}
		button[currentPage - 1].setForeground(Color.RED);
		btnLast = new JButton("Cuối");
		botPanel.add(btnLast);
		btnNextIndex = new JButton(">>");
		btnNextIndex.setName("btnNextIndex");
		btnNextIndex.addActionListener(this);
		btnFirts.addActionListener(this);
		btnFirts.setVisible(false);
		btnLast.addActionListener(this);
		botPanel.add(btnNextIndex);
		setButtonVisible();
		loadDataIntoTable(currentPage, pageSize);
		btnNextIndex.addMouseListener(new MouseEventClickButtonDialog(null, null, null));
		btnPrevIndex.addMouseListener(new MouseEventClickButtonDialog(null, null, null));

		try {
			profile = AccountModelManager.getInstance().getProfileConfigAdmin();
			if (profile.get(DialogConfig.Keyboard) != null && profile.get(DialogConfig.Keyboard).toString().equals("true")) {
				btnKeyboard.setVisible(true);
				panelTextKeyboard.setVisible(true);
				lbMeseg.setVisible(false);
				btnKeyboard.setText("Ẩn");
				setBtn();
			} else {
				btnKeyboard.setVisible(false);
				panelTextKeyboard.setVisible(false);
				lbMeseg.setVisible(true);
			}
		} catch (Exception e) {
		}

	}

	private void setFullSize() {
		Dimension fullsize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(fullsize);
	}

	public void setBtn() {
		if (btnKeyboard.isVisible() == true) {
			btnKeyboard.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (btnKeyboard.getText().equals("Ẩn")) {
						panelTextKeyboard.setVisible(false);
						btnKeyboard.setText("Hiện");
						lbMeseg.setVisible(true);
					} else {
						panelTextKeyboard.setVisible(true);
						btnKeyboard.setText("Ẩn");
						lbMeseg.setVisible(false);
					}
				}
			});
		}
	}

	private void printData() {
		try {
			ReportGeneral report = new ReportGeneral();
			report.setTable(table);
			String loginId = ManagerAuthenticate.getInstance().getOrganizationLoginId();
			String nameEnterprise = AccountModelManager.getInstance().getNameByLoginId(loginId);
			String address = AccountModelManager.getInstance().getAddressByLoginId(loginId);
			report.setEnterprise(nameEnterprise);
			report.setAddress(address);
			report.setLogoEnterprise(new ImageIcon());
			report.setReportName("Danh sách in");
			report.setVisible(true);
		} catch (Exception e) {
			String str = "Chức năng in lỗi!";
			PanelChoise pnPanel = new PanelChoise(str);
			pnPanel.setName("Xoa");
			DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
			dialog1.setName("dlXoa");
			dialog1.setTitle("In báo cáo");
			dialog1.setLocationRelativeTo(null);
			dialog1.setModal(true);
			dialog1.setVisible(true);

			e.printStackTrace();
		}
	}

	private void initComponents() {
		panelBackground1 = new PanelBackground();
		jPanel1 = new JPanel();
		jScrollPane1 = new JScrollPane();
		btnExit = new JButton();
		btnExit.setName("btnExit");
		btnPrint = new JButton();
		jPanel2 = new JPanel();
		chbSum = new JCheckBox();
		chbAVG = new JCheckBox();
		cbSearch = new JComboBox();
		cbSearch.setName("cbSearch");

		btnKeyboard = new JButton();
		btnKeyboard.setVisible(false);
		btnKeyboard.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/refresh1.png")));
		btnKeyboard.addMouseListener(new MouseEventClickButtonDialog("refresh1.png", "refresh1.png", "refresh1.png"));

		jLabel2 = new JLabel();
		txtSearch = new JTextField();
		txtSearch.setName("txtSearch");
		jLabel1 = new JLabel();
		jLabel4 = new JLabel();
		cboSize = new JComboBox();
		btnShowDetail = new JButton();
		jLabel3 = new JLabel();
		botPanel = new JPanel();
		btnExt1 = new JScrollPane();
		btnExt1.setBorder(null);
		btnExt2 = new JScrollPane();
		btnExt2.setBorder(null);
		btnExt3 = new JScrollPane();
		btnExt3.setBorder(null);
		btnExt1.setOpaque(false);
		btnExt1.getViewport().setOpaque(false);
		btnExt2.setOpaque(false);
		btnExt2.getViewport().setOpaque(false);
		btnExt3.setOpaque(false);
		btnExt3.getViewport().setOpaque(false);
		component = new JScrollPane();
		component.setBorder(null);
		component.setOpaque(false);
		component.getViewport().setOpaque(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		jPanel1.setOpaque(false);

		btnExit.setFont(new Font("Tahoma", 1, 14));
		btnExit.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/iconBack.png")));
		btnExit.setText("Thoát");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnExitActionPerformed(evt);
			}
		});
		btnDelete = new JButton();
		btnDelete.setFont(new Font("Tahoma", 1, 14));
		btnDelete.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/iconBack.png")));
		btnDelete.setText("Xóa");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnDeleteActionPerformed(evt);
			}
		});

		btnPrint.setFont(new Font("Tahoma", 1, 14));
		btnPrint.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/print1.png")));
		btnPrint.setText("In");
		btnPrint.setIconTextGap(10);
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnPrintActionPerformed(evt);
			}
		});

		jPanel2.setOpaque(false);
		chbSum.setFont(new Font("Tahoma", 1, 14));
		chbSum.setText("Tổng");
		chbSum.setOpaque(false);
		chbSum.setVisible(false);
		chbSum.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				chbSumItemStateChanged(evt);
			}
		});

		chbAVG.setFont(new Font("Tahoma", 1, 14));
		chbAVG.setText("Trung bình");
		chbAVG.setOpaque(false);
		chbAVG.setVisible(false);
		chbAVG.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				chbAVGItemStateChanged(evt);
			}
		});

		cbSearch.setFont(new Font("Tahoma", 0, 14));
		cbSearch.setModel(new DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
		cbSearch.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				cbSearchItemStateChanged(evt);
			}
		});

		jLabel2.setFont(new Font("Tahoma", 1, 14));
		jLabel2.setText("Theo");

		txtSearch.setFont(new Font("Tahoma", 0, 14));
		txtSearch.setText("");
		txtSearch.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent evt) {
				txtSearchCaretUpdate(evt);
			}
		});

		jLabel1.setFont(new Font("Tahoma", 1, 14));
		jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel1.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/search.png")));
		jLabel1.setText("");

		jLabel4.setFont(new Font("Tahoma", 1, 14));
		jLabel4.setText("Hiển thị");

		cboSize.setFont(new Font("Tahoma", 0, 14));
		cboSize.setModel(new DefaultComboBoxModel(new String[] { "22", "50", "100", "500", "Tất cả" }));
		a = new JLabel("");
		a.setFont(new Font("Tahoma", 1, 14));
		GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
		    jPanel2Layout.createSequentialGroup()
		        .addComponent(chbSum, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE).addGap(2, 2, 2)
		        .addComponent(chbAVG, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
		        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		        .addComponent(a, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE).addGap(2, 2, 2)
		        .addComponent(component, GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE)
		        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		        .addComponent(jLabel4, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE).addGap(15, 15, 15)
		        .addComponent(cboSize, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE).addGap(2, 2, 2)
		        .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE).addGap(2, 2, 2)
		        .addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, 85, Short.MAX_VALUE).addGap(2, 2, 2)
		        .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE).addGap(2, 2, 2)
		        .addComponent(cbSearch, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE).addGap(2, 2, 2)));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
		    jPanel2Layout
		        .createSequentialGroup()
		        .addContainerGap()
		        .addGroup(
		            jPanel2Layout
		                .createParallelGroup(GroupLayout.Alignment.LEADING, false)
		                .addComponent(cbSearch, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
		                .addGroup(
		                    jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		                        .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
		                        .addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
		                        .addComponent(jLabel2))
		                .addComponent(chbSum, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                .addComponent(chbAVG, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                .addComponent(a, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                .addComponent(component, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
		                .addGroup(
		                    jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		                        .addComponent(jLabel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                        .addComponent(cboSize, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)))
		        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		btnShowDetail.setFont(new Font("Tahoma", 1, 14));
		btnShowDetail.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/refresh1.png")));
		btnShowDetail.setText("Xem");
		btnShowDetail.setIconTextGap(10);
		btnShowDetail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnShowDetailActionPerformed(evt);
			}
		});

		jLabel3.setText("");

		GroupLayout jPanel3Layout = new GroupLayout(botPanel);
		botPanel.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 0,
		    Short.MAX_VALUE));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 37,
		    Short.MAX_VALUE));

		JPanel panelB = new JPanel(new BorderLayout());
		panelB.setOpaque(false);
		panelB.add(jPanel2, BorderLayout.CENTER);

		JButton btn = new JButton("...");

		btn.setSize(30, 2);
		btn.setPreferredSize(new Dimension(30, 2));
		btn.setMaximumSize(new Dimension(30, 2));
		btn.setMinimumSize(new Dimension(30, 2));
		JPanel bbb = new JPanel();
		bbb.setOpaque(false);
		bbb.setLayout(new BorderLayout());
		bbb.add(btn, BorderLayout.CENTER);
		bbb.add(createLable3(), BorderLayout.SOUTH);
		bbb.add(createLable2(), BorderLayout.NORTH);
		panelB.add(bbb, BorderLayout.EAST);
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				editColumn();
			}
		});

		GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		    .addComponent(panelB, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
		    .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
		    jPanel1Layout.createSequentialGroup()
		        .addComponent(panelB, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
		        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
		        .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)));

		JLabel lbHKT = new JLabel();
		lbHKT.setFont(new java.awt.Font("Tahoma", 1, 16));
		lbHKT.setForeground(new java.awt.Color(126, 0, 0));
		lbHKT.setHorizontalAlignment(SwingConstants.RIGHT);
		lbHKT.setText("HKT Sofware   ");
		lbHKT.setPreferredSize(new Dimension(WIDTH, 50));

		labelIcon = new javax.swing.JLabel();
		labelIcon.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/description.png")));
		labelTitle = new javax.swing.JLabel("Dialog Resto");
		labelTitle.setFont(new java.awt.Font("Tahoma", 1, 14));
		labelTitle.setForeground(new java.awt.Color(126, 0, 0));

		// Giao diện chính panel2 add giao diện chính jPanel1 và tạo khoảng cách các
		// bên
		panel2 = new PanelTrans();
		panel2.setOpaque(false);
		panel2.setLayout(new BorderLayout());
		panelTextKeyboard = new PanelTextKeyboard();
		panelTextKeyboard.setVisible(false);
		jPanel1.setSize(50, 50);
		jLabel1.setPreferredSize(jPanel1.getSize());
		panel2.add(jPanel1, BorderLayout.CENTER);
		// panel2.add(createLable1(), BorderLayout.SOUTH);
		panel2.add(createLable(), BorderLayout.EAST);
		panel2.add(createLable(), BorderLayout.WEST);

		// add Panel Search
		JPanel panelT = new JPanel(new GridLayout(1, 2));
		panelT.setOpaque(false);
		panelSearch = new JPanel();
		panelSearch.setOpaque(false);
		labelS1 = new JLabel("Từ");
		myJDateChooser1 = new MyJDateChooser();
		lableS2 = new JLabel("Đến");
		myJDateChooser2 = new MyJDateChooser();
		panelSearch.setLayout(new FlowLayout(FlowLayout.LEFT, 3, 8));
		panelSearch.add(labelS1);
		panelSearch.add(myJDateChooser1);
		panelSearch.add(lableS2);
		panelSearch.add(myJDateChooser2);
		btnView = new JButton("Xem");
		panelSearch.add(btnView);
		btnView.setSize(60, 26);
		btnView.setMaximumSize(new Dimension(60, 26));
		btnView.setPreferredSize(new Dimension(60, 26));

		labelS1.setSize(30, 26);
		labelS1.setMaximumSize(new Dimension(30, 26));
		labelS1.setPreferredSize(new Dimension(30, 26));

		lableS2.setSize(30, 26);
		lableS2.setMaximumSize(new Dimension(30, 26));
		lableS2.setPreferredSize(new Dimension(30, 26));

		myJDateChooser1.setSize(120, 26);
		myJDateChooser1.setMaximumSize(new Dimension(120, 26));
		myJDateChooser1.setPreferredSize(new Dimension(120, 26));

		myJDateChooser2.setSize(120, 26);
		myJDateChooser2.setMaximumSize(new Dimension(120, 26));
		myJDateChooser2.setPreferredSize(new Dimension(120, 26));

		panelT.add(panelSearch);
		panelT.add(botPanel);
		// panelT.setSize(50, 50);
		// panelT.setPreferredSize(panelT.getSize());

		// Panel trong suốt thứ 2 add giao diện chính panel2
		PanelTrans panel = new PanelTrans();
		panel.setOpaque(false);
		GroupLayout panelBackgroundResto1Layout = new GroupLayout(panel);
		panel.setLayout(panelBackgroundResto1Layout);
		panelBackgroundResto1Layout.setHorizontalGroup(panelBackgroundResto1Layout
		    .createParallelGroup(GroupLayout.Alignment.LEADING)
		    .addGroup(
		        panelBackgroundResto1Layout.createSequentialGroup().addGap(28, 28, 28)
		            .addComponent(labelIcon, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE).addGap(6, 6, 6)
		            .addComponent(labelTitle, GroupLayout.DEFAULT_SIZE, 100, GroupLayout.PREFERRED_SIZE).addGap(6, 6, 6)
		            .addComponent(panelT, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE).addGap(45, 45, 45))
		    .addGroup(
		        panelBackgroundResto1Layout.createSequentialGroup().addGap(25, 25, 25)
		            .addComponent(panel2, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE).addGap(25, 25, 25))
		    .addGroup(
		        panelBackgroundResto1Layout.createSequentialGroup().addGap(23, 23, 23)
		            .addComponent(btnShowDetail, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
		            .addGap(4, 4, 4).addComponent(jLabel3, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE).addGap(4, 4, 4)
		            .addComponent(btnExt3, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE).addGap(4, 4, 4)
		            .addComponent(btnExt2, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE).addGap(4, 4, 4)
		            .addComponent(btnExt1, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE).addGap(4, 4, 4)
		            .addComponent(btnKeyboard, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE).addGap(4, 4, 4)
		            .addComponent(btnPrint, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE).addGap(4, 4, 4)
		            .addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE).addGap(4, 4, 4)
		            .addComponent(btnExit, GroupLayout.DEFAULT_SIZE, 103, GroupLayout.PREFERRED_SIZE).addGap(22, 22, 22)));
		panelBackgroundResto1Layout.setVerticalGroup(panelBackgroundResto1Layout.createParallelGroup(
		    GroupLayout.Alignment.LEADING).addGroup(
		    panelBackgroundResto1Layout
		        .createSequentialGroup()
		        .addGap(6, 6, 6)
		        .addGroup(
		            panelBackgroundResto1Layout
		                .createParallelGroup(GroupLayout.Alignment.LEADING)
		                .addComponent(labelIcon, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
		                .addGroup(
		                    panelBackgroundResto1Layout.createSequentialGroup().addGap(6, 6, 6)
		                        .addComponent(labelTitle, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
		                .addComponent(panelT, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
		        .addGap(4, 4, 4)
		        .addComponent(panel2, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
		        .addGap(10, 10, 10)
		        .addGroup(
		            panelBackgroundResto1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		                .addComponent(btnShowDetail, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
		                .addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
		                .addComponent(btnExt3, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
		                .addComponent(btnExt2, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
		                .addComponent(btnExt1, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
		                .addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
		                .addComponent(btnKeyboard, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
		                .addComponent(btnPrint, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
		                .addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
		        .addGap(10, 10, 10)));

		// Panel thứ nhất add panel trong suốt thứ 2 + lable HKT Software

		lbMeseg = new JLabel();
		lbMeseg.setPreferredSize(new Dimension(WIDTH, 23));

		panelTextKeyboard = new PanelTextKeyboard();
		panelTextKeyboard.setVisible(false);
		panelComp = new JPanel();
		panelComp.setOpaque(false);
		panelComp.setLayout(new BorderLayout());
		panelComp.add(lbMeseg, BorderLayout.CENTER);
		panelComp.add(panelTextKeyboard, BorderLayout.SOUTH);
		panel.setSize(35, 35);
		panel.setPreferredSize(panel.getSize());
		panelComp.setSize(35, 35);
		panelComp.setPreferredSize(panel.getSize());
		panelBackground1.setLayout(new BorderLayout());
		panelBackground1.add(panel, BorderLayout.CENTER);
		panelBackground1.add(lbHKT, BorderLayout.NORTH);
		panelBackground1.add(panelComp, BorderLayout.SOUTH);
		panelBackground1.add(createLable(), BorderLayout.EAST);
		panelBackground1.add(createLable(), BorderLayout.WEST);
		panelBackground1.setSize(50, 50);
		panelBackground1.setPreferredSize(panelBackground1.getSize());

		// Giao diện dialog add Panel thứ nhất
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(panelBackground1,
		    GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(panelBackground1,
		    GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		btnPrint.addMouseListener(new MouseEventClickButtonDialog("print1.png", "print1.png", "print1.png"));
		btnExit.addMouseListener(new MouseEventClickButtonDialog("iconBack.png", "iconBack.png", "iconBack.png"));
		btnShowDetail.addMouseListener(new MouseEventClickButtonDialog("refresh1.png", "refresh1.png", "refresh1.png"));
		pack();
	}// </editor-fold>

	protected void btnDeleteActionPerformed(ActionEvent evt) {
		tableMain.delete();
	}

	private JLabel createLable() {
		JLabel label = new JLabel();
		label.setSize(20, 20);
		label.setPreferredSize(label.getSize());
		return label;
	}

	private JLabel createLable1() {
		JLabel label = new JLabel();
		label.setSize(8, 8);
		label.setPreferredSize(label.getSize());
		return label;
	}

	private JLabel createLable2() {
		JLabel label = new JLabel();
		label.setSize(12, 12);
		label.setPreferredSize(label.getSize());
		return label;
	}

	private JLabel createLable3() {
		JLabel label = new JLabel();
		label.setSize(8, 8);
		label.setPreferredSize(label.getSize());
		return label;
	}

	private void btnShowDetailActionPerformed(ActionEvent evt) {
		try {
			tableSelectedRow = table.getSelectedRow();
		} catch (Exception e) {
			tableSelectedRow = -1;
		}
		if (tableSelectedRow >= 0) {
			try {
				tableMain.click();
				// update("", "");
			} catch (Exception e) {
			}

		}
	}

	private void txtSearchCaretUpdate(CaretEvent evt) {
		searchData();
	}

	private void chbAVGItemStateChanged(ItemEvent evt) {
		if (chbAVG.isSelected()) {
			if (chbSum.isSelected()) {
				tableModel.removeRow(tableModel.getRowCount() - 1);
				tableModel.removeRow(tableModel.getRowCount() - 1);
				List<String> list = new ArrayList<String>();
				for (int i = 0; i < tableModel.getColumnCount(); i++) {
					list.add(tableModel.getColumnName(i));
				}
				for (int i = 0; i < listHeader.size(); i++) {
					visibleColumnTable.showColumn(list.indexOf(listHeader.get(i)));
				}
				Hashtable<String, String> hashtable = new Hashtable<String, String>();
				hashtable.put(txtSearch.getText(), cbSearch.getSelectedItem().toString());
				TableSumAVG.getInstance().setSumAVGTable(table, tableMain.getColumnSum(), "", hashtable);
				for (int i = 0; i < listHeader.size(); i++) {
					visibleColumnTable.hideColumn(list.indexOf(listHeader.get(i)));
				}
			} else {
				List<String> list = new ArrayList<String>();
				for (int i = 0; i < tableModel.getColumnCount(); i++) {
					list.add(tableModel.getColumnName(i));
				}
				for (int i = 0; i < listHeader.size(); i++) {
					visibleColumnTable.showColumn(list.indexOf(listHeader.get(i)));
				}
				Hashtable<String, String> hashtable = new Hashtable<String, String>();
				hashtable.put(txtSearch.getText(), cbSearch.getSelectedItem().toString());
				TableSumAVG.getInstance().setAVGTable(table, tableMain.getColumnSum(), "", hashtable);
				for (int i = 0; i < listHeader.size(); i++) {
					visibleColumnTable.hideColumn(list.indexOf(listHeader.get(i)));
				}
			}
		} else {
			if (chbSum.isSelected()) {
				tableModel.removeRow(tableModel.getRowCount() - 1);
			} else {
				tableModel.removeRow(tableModel.getRowCount() - 1);
				tableModel.removeRow(tableModel.getRowCount() - 1);
			}
		}
	}

	private void chbSumItemStateChanged(ItemEvent evt) {
		if (chbSum.isSelected()) {
			if (chbAVG.isSelected()) {
				tableModel.removeRow(tableModel.getRowCount() - 1);
				tableModel.removeRow(tableModel.getRowCount() - 1);
				List<String> list = new ArrayList<String>();
				for (int i = 0; i < tableModel.getColumnCount(); i++) {
					list.add(tableModel.getColumnName(i));
				}
				for (int i = 0; i < listHeader.size(); i++) {
					visibleColumnTable.showColumn(list.indexOf(listHeader.get(i)));
				}
				Hashtable<String, String> hashtable = new Hashtable<String, String>();
				hashtable.put(txtSearch.getText(), cbSearch.getSelectedItem().toString());
				TableSumAVG.getInstance().setSumAVGTable(table, tableMain.getColumnSum(), "", hashtable);
				for (int i = 0; i < listHeader.size(); i++) {
					visibleColumnTable.hideColumn(list.indexOf(listHeader.get(i)));
				}
			} else {
				List<String> list = new ArrayList<String>();
				for (int i = 0; i < tableModel.getColumnCount(); i++) {
					list.add(tableModel.getColumnName(i));
				}
				for (int i = 0; i < listHeader.size(); i++) {
					visibleColumnTable.showColumn(list.indexOf(listHeader.get(i)));
				}
				Hashtable<String, String> hashtable = new Hashtable<String, String>();
				hashtable.put(txtSearch.getText(), cbSearch.getSelectedItem().toString());
				TableSumAVG.getInstance().setSumTable(table, tableMain.getColumnSum(), "", hashtable);
				for (int i = 0; i < listHeader.size(); i++) {
					visibleColumnTable.hideColumn(list.indexOf(listHeader.get(i)));
				}
			}
		} else {
			if (chbAVG.isSelected()) {
				tableModel.removeRow(tableModel.getRowCount() - 2);
			} else {
				tableModel.removeRow(tableModel.getRowCount() - 1);
				tableModel.removeRow(tableModel.getRowCount() - 1);
			}
		}
	}

	private void btnPrintActionPerformed(ActionEvent evt) {
		printData();
	}

	private void btnExitActionPerformed(ActionEvent evt) {
		this.dispose();
	}

	private void cbSearchItemStateChanged(ItemEvent evt) {
		
		searchData();
	}

	private void searchData() {
		tableFillterSorter.fillter(txtSearch.getText(), cbSearch.getSelectedItem().toString());
	}

	private void addVituarKey(Container c) {
		Component[] comps = c.getComponents();

		for (Component comp : comps) {

			if (comp instanceof JTextField) {
				if (((JTextField) comp).isFocusOwner()) {
					panelTextKeyboard.setText((JComponent) comp);
				}
			}
			comp.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					Component comp = (Component) e.getSource();
					if (comp instanceof JComponent) {
						panelTextKeyboard.setText((JComponent) comp);
					}
				}
			});
			if (comp instanceof Container) {
				addVituarKey((Container) comp);
			}
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

		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0, false), "F6");
		jc.getActionMap().put("F6", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (panelTextKeyboard.isVisible()) {
					panelTextKeyboard.setVisible(false);
					btnKeyboard.setVisible(false);
					lbMeseg.setVisible(true);
				} else {
					panelTextKeyboard.setVisible(true);
					btnKeyboard.setVisible(false);
				}
			}
		});
		
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0, false), "VK_F10");
		jc.getActionMap().put("VK_F10", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if(iTableModelExt!=null){
					iTableModelExt.loadDataColum(cbSearch.getSelectedItem().toString());
				}
			}
		});
		
		
	}

	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		if (button == btnNextIndex) {
			if (currentPage < getMaxPage()) {
				currentPage++;
				drawButtonIndex(currentPage);
			} else {
				btnNextIndex.setEnabled(false);
			}

		}
		if (button == btnPrevIndex) {
			if (currentPage > 1) {
				currentPage--;
				drawButtonIndex(currentPage);
			} else {

				btnPrevIndex.setEnabled(false);
			}

		}
		if (button == btnFirts) {
			currentPage = 1;
			drawButtonIndex(currentPage);
		}
		if (button == btnLast) {
			currentPage = getMaxPage();
			drawButtonIndex(currentPage);
		}
		for (int i = 0; i < 5; i++) {
			if (button == this.button[i]) {
				currentPage = Integer.parseInt(this.button[i].getText() + "");
				drawButtonIndex(currentPage);
			}
		}
		if (!this.button[0].getText().equals("1")) {
			btnFirts.setVisible(true);
		} else {
			btnFirts.setVisible(false);
		}
		// if(!cboSize.getSelectedItem().toString().equals("Tất cả")){
		// int n = ((currentPage-1)*pageSize)-1;
		// for(int i = 1;i<=pageSize; i++){
		// tableModel.setValueAt(n+i, i, 0);
		// }
		// }

	}

	private void drawButtonIndex(int currentPage) {
		if (chbSum.isSelected()) {
			chbSum.setSelected(false);
		}
		loadDataIntoTable(currentPage, pageSize);
		setButtonVisible();
		btnPrevIndex.setVisible(true);
		btnNextIndex.setVisible(true);
		if (currentPage == getMaxPage()) {
			btnNextIndex.setEnabled(false);
		} else {
			btnNextIndex.setEnabled(true);
		}
		if (currentPage == 1) {
			btnPrevIndex.setEnabled(false);
			btnFirts.setVisible(false);
		} else {
			btnPrevIndex.setEnabled(true);
		}
		if (getMaxPage() <= 5) {
			for (int i = 0; i < 5; i++) {
				button[i].setText(i + 1 + "");
				if (i == currentPage - 1) {
					this.button[i].setForeground(Color.RED);
				} else {
					this.button[i].setForeground(Color.BLACK);
				}
			}
		} else {
			if (currentPage <= 3) {
				for (int i = 0; i < 5; i++) {
					button[i].setText(i + 1 + "");
					if (i == currentPage - 1) {
						this.button[i].setForeground(Color.RED);
					} else {
						this.button[i].setForeground(Color.BLACK);
					}
				}
			} else if (currentPage > getMaxPage() - 2) {
				for (int i = 0; i < 5; i++) {
					button[i].setText(getMaxPage() - 4 + i + "");
					if (Integer.parseInt(button[i].getText() + "") == currentPage) {
						button[i].setForeground(Color.RED);
					} else {
						button[i].setForeground(Color.BLACK);
					}
				}
			} else {
				for (int i = 0; i < 5; i++) {
					button[i].setText(currentPage + i - 2 + "");
					if (i == 2) {
						button[i].setForeground(Color.RED);
					} else {
						button[i].setForeground(Color.BLACK);
					}
				}
			}
		}
	}

	private void setButtonVisible() {
		if (getMaxPage() < 6) {
			btnLast.setVisible(false);
			for (int i = 0; i < 5; i++) {
				if (i >= getMaxPage()) {
					button[i].setVisible(false);
				} else {
					button[i].setVisible(true);
				}
			}
		} else {
			btnLast.setVisible(true);
			for (int i = 0; i < 5; i++) {
				button[i].setVisible(true);
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void loadDataIntoTable(int currentPage, int pageSize) {
		int size = currentPage * pageSize;
		if (size > tableMain.getListSize()) {
			size = tableMain.getListSize();
		}
		tableModel = tableMain.loadTable((currentPage - 1) * pageSize, size);
		((DefaultTableModel) table.getModel()).fireTableDataChanged();
		loadGuiTable();

	}

	private void loadGuiTable() {
		tableFillterSorter = new TableFillterSorter(table);
		tableFillterSorter.createTableSorter();
		tableFillterSorter.createTableFillter();
	//	cbSearch.setModel(new DefaultComboBoxModel(tableFillterSorter.getHashtable().keySet().toArray()));
		if(!cbSearch.isEnabled()){
			for(int i = 0; i< cbSearch.getItemCount(); i++){
				if(cbSearch.getItemAt(i).toString().equals("NVPV")){
					cbSearch.setSelectedIndex(i);
					break;
				}
			}
		}else {
			for(int i = 0; i< cbSearch.getItemCount(); i++){
				if(cbSearch.getItemAt(i).toString().indexOf("Tên")>=0
						||cbSearch.getItemAt(i).toString().indexOf("Barcode")>=0){
					cbSearch.setSelectedIndex(i);
					break;
				}
			}
		}
		
		visibleColumnTable = new VisibleColumnTable(table);
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < tableModel.getColumnCount(); i++) {
			list.add(tableModel.getColumnName(i));
		}
		for (int i = 0; i < listHeader.size(); i++) {

			visibleColumnTable.hideColumn(list.indexOf(listHeader.get(i)));
		}
		if (iTableModel != null) {
			boolean dele = false;
			for (int i = 0; i < table.getColumnCount(); i++) {
				try {
					table.getColumnModel().getColumn(i).setMaxWidth(iTableModel.getColumnSize().get(table.getColumnName(i)));
					table.getColumnModel().getColumn(i).setMinWidth(iTableModel.getColumnSize().get(table.getColumnName(i)));
				} catch (Exception e) {
				}
				if(tableMain.getListSize()==-1){
					for (int h = 0; h < table.getColumnCount(); h++) {
						table.getColumnModel().getColumn(h).setCellRenderer(new DefaultTableCellRenderer() {

							@Override
							public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
							    boolean hasFocus, int row, int column)
							{
								JLabel label = new JLabel();
								try {
									label.setText(value.toString());
								} catch (Exception e) {
									label.setText("");
								}
								if(table.getValueAt(row, 0).toString().isEmpty()){
									label.setFont(new Font("Tahoma", 0, 14));
								}else{
									label.setFont(new Font("Tahoma", 1, 14));
								}
								if (table.getColumnClass(column).equals(MyDouble.class)) {
									label.setHorizontalAlignment(JLabel.RIGHT);
								}
								if (isSelected) {
									label.setOpaque(true);
									label.setBackground(table.getSelectionBackground());
								}
								return label;
							}
						});
					}
				}
				if (table.getColumnClass(i).equals(MyDate.class)) {
					table.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {

						@Override
						public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						    boolean hasFocus, int row, int column)
						{
							JLabel label = new JLabel();
							try {
								label.setText(value.toString());
							} catch (Exception e) {
								label.setText("");
							}

							label.setFont(new Font("Tahoma", 0, 14));
							label.setHorizontalAlignment(JLabel.CENTER);
							if (isSelected) {
								label.setOpaque(true);
								label.setBackground(table.getSelectionBackground());
							}
							return label;
						}
					});
				}
				if (table.getColumnClass(i).equals(Boolean.class)) {
					dele = true;
					table.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {

						@Override
						public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						    boolean hasFocus, int row, int column)
						{
							JCheckBox chb = new JCheckBox();
							chb.setHorizontalAlignment(JLabel.CENTER);
							chb.setSelected(Boolean.parseBoolean(value.toString()));
							return chb;
						}
					});
				}
				
			}
			if (!dele) {
				btnDelete.setVisible(false);
			}
		}
	}

	private int getBonusPage() {
		if (tableMain.getListSize() % pageSize == 0) {
			return 0;
		} else {
			return 1;
		}
	}

	private int getMaxPage() {
		try {
			return (tableMain.getListSize() / pageSize + getBonusPage());
		} catch (Exception e) {
			return 0;
		}

	}

	private void editColumn() {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < tableModel.getColumnCount(); i++) {
			list.add(tableModel.getColumnName(i));
		}
		PanelSortColumnTable panel = new PanelSortColumnTable(list, iTableModel.getNameTableModel());
		int h = 0;
		if (panel.getH() > h) {
			h = panel.getH();
		}
		DialogResto dialog = new DialogResto(panel, false, 0, h);
		dialog.setTitle("Quản lý cột");
		dialog.setLocationRelativeTo(null);
		dialog.setModal(true);
		dialog.setVisible(true);
		if (panel.isFlagSort()) {
			listHeader = panel.getListSelect();
			loadGuiTable();
		}
	}

	@Override
	public void update(Object o, Object arg) {
		
		if (cboSize.getSelectedItem().toString().equals("Tất cả") && tableMain.getListSize()<=500) {
			pageSize = tableMain.getListSize();
			currentPage = getMaxPage();
			loadDataIntoTable(currentPage, pageSize);
		} else {
			cboSize.setSelectedIndex(0);
			drawButtonIndex(1);
		}
		String a = txtSearch.getText();
    txtSearch.setText("");
    txtSearch.setText(a);
	}
}
