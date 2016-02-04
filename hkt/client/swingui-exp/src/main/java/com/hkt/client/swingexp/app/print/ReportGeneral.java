/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ShowReport.java
 *
 * Created on Jul 19, 2012, 8:48:41 AM
 */
package com.hkt.client.swingexp.app.print;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.hkt.client.swingexp.app.banhang.screen.often.DialogConfig;
import com.hkt.client.swingexp.app.component.ExtendJButton;
import com.hkt.client.swingexp.app.component.ExtendJDateChooser;
import com.hkt.client.swingexp.app.component.PanelBackground;
import com.hkt.client.swingexp.app.component.PanelTrans;
import com.hkt.client.swingexp.app.core.HKTFile;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDialog;
import com.hkt.client.swingexp.app.virtualkey.text.PanelTextKeyboard;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.module.account.entity.Profile;

@SuppressWarnings("serial")
public class ReportGeneral extends javax.swing.JDialog {

	@SuppressWarnings("rawtypes")
	private DefaultListModel listHeader1, listHeader2;
	private int indexPrimary;
	private int indexFinal;
	private String filePathResource;
	@SuppressWarnings("rawtypes")
	private HashMap hashMap;
	private JTable table;
	private TableModel model;
	private int numberColumnMax = 0;
	private String[] headerReport;
	private ImageIcon logo = new ImageIcon();
	private boolean flagExtension;
	private ExtendJDateChooser dc;

	/**
	 * Creates new form ShowReport
	 */
	public ReportGeneral() {
		initComponents();
		addKeyBindings(panelBackground);
		addVituarKey(panelBackground);
		setModal(true);
		dispose();
		setUndecorated(true);
		setFullSize();
		resetData();
		txtPersonCreate.setText(ManagerAuthenticate.getInstance().getLoginId());
		txtEnterprise.setText("Công ty TNHH HKT Consultant");
		cboSize.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				Profile p = AccountModelManager.getInstance().getProfileConfigAdmin();
				p.put("Report" + cboSize.getSelectedItem().toString(), cboSize.getSelectedItem().toString());
				AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(),
				    p);

			}
		});
		Profile p = AccountModelManager.getInstance().getProfileConfigAdmin();
		if (p.get("ReportA5") != null) {
			cboSize.setSelectedIndex(1);
		} else {
			if (p.get("ReportK80") != null) {
				cboSize.setSelectedIndex(2);
			}
		}
	}

	private void setFullSize() {
		Dimension fullsize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(fullsize);
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
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		panelBackground = new PanelBackground();
		jLabel30 = new JLabel();
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
		btnKeyboard = new JButton();
		btnKeyboard.setVisible(false);
		btnKeyboard.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/refresh1.png")));
		btnKeyboard.addMouseListener(new MouseEventClickButtonDialog("refresh1.png", "refresh1.png", "refresh1.png"));
		btnDelete = new JButton();
		btnDelete.setVisible(false);
		lbMeseg = new JLabel();
		lbMeseg.setPreferredSize(new Dimension(WIDTH, 23));

		jPanel1 = new javax.swing.JPanel();
		jPanel4 = new javax.swing.JPanel();
		jPanel2 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		lstPrimary = new javax.swing.JList();
		jScrollPane2 = new javax.swing.JScrollPane();
		lstFinal = new javax.swing.JList();
		btnAddOne = new ExtendJButton(">");
		btnAddAll = new ExtendJButton(">>");
		btnRemoveOne = new ExtendJButton("<");
		btnRemoveAll = new ExtendJButton("<<");
		cbTotalColumn = new javax.swing.JComboBox();
		cbTotalColumn.setName("cbTotalColumn");
		jLabel6 = new javax.swing.JLabel();
		txtAddress = new javax.swing.JTextField();
		txtAddress.setName("txtAddress");
		txtPersonCreate = new javax.swing.JTextField();
		txtPersonCreate.setName("txtPersonCreate");
		txtReportName = new javax.swing.JTextField();
		txtReportName.setName("txtReportName");
		jLabel5 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel1 = new javax.swing.JLabel();
		txtEnterprise = new javax.swing.JTextField();
		jLabel2 = new javax.swing.JLabel();
		btnPrintReport = new javax.swing.JButton();
		btnPrint = new javax.swing.JButton();
		jLabel7 = new javax.swing.JLabel();
		cbSelectedPrinter = new javax.swing.JComboBox();
		cboSize = new javax.swing.JComboBox();
		cbSelectedPrinter.setName("cbSelectedPrinter");
		btnExit = new javax.swing.JButton();
		lbShowReport = new javax.swing.JLabel();
		panelReport = new javax.swing.JPanel();
		jScrollPane3 = new javax.swing.JScrollPane();
		tableDateReport = new javax.swing.JTable();
		lbImageReport = new javax.swing.JLabel();
		lbEnterpriseReport = new javax.swing.JLabel();
		lbAddressReport = new javax.swing.JLabel();
		lbReportNameReport = new javax.swing.JLabel();
		lbTimeCreateReport = new javax.swing.JLabel();
		jLabel = new javax.swing.JLabel();
		lbPersonCreateReport = new javax.swing.JLabel();
		dc = new ExtendJDateChooser();
		dc.setDate(new Date());
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

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		jPanel1.setBackground(new java.awt.Color(255, 255, 255));
		jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		jPanel4.setOpaque(false);

		jPanel2.setOpaque(false);

		lstPrimary.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		lstPrimary.setModel(new javax.swing.AbstractListModel() {
			String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };

			public int getSize() {
				return strings.length;
			}

			public Object getElementAt(int i) {
				return strings[i];
			}
		});
		lstPrimary.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				lstPrimaryMouseClicked(evt);
			}
		});
		jScrollPane1.setViewportView(lstPrimary);

		lstFinal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		lstFinal.setModel(new javax.swing.AbstractListModel() {
			String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };

			public int getSize() {
				return strings.length;
			}

			public Object getElementAt(int i) {
				return strings[i];
			}
		});
		lstFinal.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				lstFinalMouseClicked(evt);
			}
		});
		jScrollPane2.setViewportView(lstFinal);

		btnAddOne.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnAddOneActionPerformed(evt);
			}
		});

		btnAddAll.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnAddAllActionPerformed(evt);
			}
		});

		btnRemoveOne.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnRemoveOneActionPerformed(evt);
			}
		});

		btnRemoveAll.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnRemoveAllActionPerformed(evt);
			}
		});

		btnAddAll.addMouseListener(new MouseEventClickButtonDialog("", "", ""));
		btnAddOne.addMouseListener(new MouseEventClickButtonDialog("", "", ""));
		btnRemoveOne.addMouseListener(new MouseEventClickButtonDialog("", "", ""));
		btnRemoveAll.addMouseListener(new MouseEventClickButtonDialog("", "", ""));

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        jPanel2Layout
		            .createSequentialGroup()
		            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		            .addGroup(
		                jPanel2Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
		                    .addComponent(btnRemoveOne, javax.swing.GroupLayout.DEFAULT_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                    .addComponent(btnRemoveAll, javax.swing.GroupLayout.DEFAULT_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                    .addComponent(btnAddAll, javax.swing.GroupLayout.DEFAULT_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                    .addComponent(btnAddOne, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
		                        javax.swing.GroupLayout.PREFERRED_SIZE))
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)));

		jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] { btnAddAll, btnAddOne,
		    btnRemoveAll, btnRemoveOne });

		jPanel2Layout.setVerticalGroup(jPanel2Layout
		    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        jPanel2Layout
		            .createSequentialGroup()
		            .addComponent(btnAddOne, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		            .addComponent(btnAddAll, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		            .addComponent(btnRemoveOne, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		            .addComponent(btnRemoveAll, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
		                javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap())
		    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
		    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE));

		cbTotalColumn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		cbTotalColumn
		    .setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
		cbTotalColumn.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				cbTotalColumnItemStateChanged(evt);
			}
		});

		jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		jLabel6.setText("Địa chỉ"); // NOI18N

		txtAddress.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		txtAddress.setText(""); // NOI18N
		txtAddress.addCaretListener(new javax.swing.event.CaretListener() {
			public void caretUpdate(javax.swing.event.CaretEvent evt) {
				txtAddressCaretUpdate(evt);
			}
		});

		txtPersonCreate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		txtPersonCreate.setText(""); // NOI18N
		txtPersonCreate.addCaretListener(new javax.swing.event.CaretListener() {
			public void caretUpdate(javax.swing.event.CaretEvent evt) {
				txtPersonCreateCaretUpdate(evt);
			}
		});

		txtReportName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		txtReportName.setText(""); // NOI18N
		txtReportName.addCaretListener(new javax.swing.event.CaretListener() {
			public void caretUpdate(javax.swing.event.CaretEvent evt) {
				txtReportNameCaretUpdate(evt);
			}
		});

		jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		jLabel5.setText("Ngày lập"); // NOI18N

		jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		jLabel4.setText("Tên báo cáo"); // NOI18N

		jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		jLabel3.setText("Công ty"); // NOI18N

		jLabel1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
		jLabel1.setForeground(new java.awt.Color(0, 0, 255));
		jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel1.setText("Chọn các thông tin cho báo cáo"); // NOI18N

		txtEnterprise.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		txtEnterprise.setText(""); // NOI18N
		txtEnterprise.setEnabled(false);
		txtEnterprise.addCaretListener(new javax.swing.event.CaretListener() {
			public void caretUpdate(javax.swing.event.CaretEvent evt) {
				txtEnterpriseCaretUpdate(evt);
			}
		});

		jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		jLabel2.setText("Người lập"); // NOI18N

		btnPrintReport.setFont(new Font("Tahoma", 1, 14));
		btnPrintReport.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/refresh1.png")));
		btnPrintReport.addMouseListener(new MouseEventClickButtonDialog("refresh1.png", "refresh1.png", "refresh1.png"));
		btnPrintReport.setText("Xem");
		btnPrintReport.setIconTextGap(10);
		btnPrintReport.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnPrintReportActionPerformed(evt);
			}
		});

		btnPrint.setFont(new Font("Tahoma", 1, 14));
		btnPrint.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/print1.png")));
		btnPrint.addMouseListener(new MouseEventClickButtonDialog("print1.png", "print1.png", "print1.png"));
		btnPrint.setText("In");
		btnPrint.setIconTextGap(10);
		btnPrint.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnPrintActionPerformed(evt);
			}
		});

		jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		jLabel7.setText("Máy in"); // NOI18N

		cbSelectedPrinter.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		cbSelectedPrinter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3",
		    "Item 4" }));
		cboSize.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		cboSize.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A4", "A5", "K80" }));
		cboSize.setOpaque(false);
		JPanel panelCbo = new JPanel(new BorderLayout(0, 5));
		panelCbo.setOpaque(false);
		panelCbo.add(cbSelectedPrinter, BorderLayout.CENTER);
		panelCbo.add(cboSize, BorderLayout.EAST);

		btnExit.setFont(new Font("Tahoma", 1, 14));
		btnExit.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/iconBack.png")));
		btnExit.setText("Thoát");
		btnExit.addMouseListener(new MouseEventClickButtonDialog("iconBack.png", "iconBack.png", "iconBack.png"));
		btnExit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnExitActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout
		    .setHorizontalGroup(jPanel4Layout
		        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		        .addGroup(
		            jPanel4Layout
		                .createSequentialGroup()
		                .addContainerGap()
		                .addGroup(
		                    jPanel4Layout
		                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
		                        .addGroup(
		                            jPanel4Layout
		                                .createSequentialGroup()
		                                .addGroup(
		                                    jPanel4Layout
		                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
		                                        .addGroup(
		                                            javax.swing.GroupLayout.Alignment.LEADING,
		                                            jPanel4Layout
		                                                .createSequentialGroup()
		                                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 92,
		                                                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                                                .addComponent(txtAddress, javax.swing.GroupLayout.DEFAULT_SIZE,
		                                                    145, Short.MAX_VALUE))
		                                        .addGroup(
		                                            javax.swing.GroupLayout.Alignment.LEADING,
		                                            jPanel4Layout
		                                                .createSequentialGroup()
		                                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 92,
		                                                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                                                .addComponent(txtEnterprise, javax.swing.GroupLayout.DEFAULT_SIZE,
		                                                    145, Short.MAX_VALUE)))
		                                .addGap(18, 18, 18)
		                                .addGroup(
		                                    jPanel4Layout
		                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 90,
		                                            javax.swing.GroupLayout.PREFERRED_SIZE)
		                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 95,
		                                            javax.swing.GroupLayout.PREFERRED_SIZE))
		                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                                .addGroup(
		                                    jPanel4Layout
		                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                                        .addComponent(txtPersonCreate, javax.swing.GroupLayout.DEFAULT_SIZE, 146,
		                                            Short.MAX_VALUE)
		                                        .addComponent(dc, javax.swing.GroupLayout.DEFAULT_SIZE, 146,
		                                            Short.MAX_VALUE)))
		                        .addGroup(
		                            jPanel4Layout
		                                .createSequentialGroup()
		                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 92,
		                                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                                .addComponent(txtReportName, javax.swing.GroupLayout.DEFAULT_SIZE, 408,
		                                    Short.MAX_VALUE))
		                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
		                            javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                        .addComponent(cbTotalColumn, 0, 507, Short.MAX_VALUE)
		                        .addGroup(
		                            jPanel4Layout
		                                .createSequentialGroup()
		                                .addComponent(jLabel7)
		                                .addGap(18, 18, 18)
		                                .addGroup(
		                                    jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                                        .addComponent(panelCbo, 0, 406, Short.MAX_VALUE)).addGap(44, 44, 44)))
		                .addGap(0, 0, 0)));

		jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] { jLabel2, jLabel3, jLabel4,
		    jLabel5, jLabel6 });

		jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        jPanel4Layout
		            .createSequentialGroup()
		            .addContainerGap()
		            .addComponent(jLabel1)
		            .addGap(9, 9, 9)
		            .addGroup(
		                jPanel4Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
		                    .addComponent(txtEnterprise, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(jLabel3)
		                    .addComponent(jLabel5)
		                    .addComponent(dc, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		            .addGroup(
		                jPanel4Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
		                    .addComponent(jLabel6)
		                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(jLabel2)
		                    .addComponent(txtPersonCreate, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		            .addGroup(
		                jPanel4Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
		                    .addComponent(jLabel4)
		                    .addComponent(txtReportName, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		            .addComponent(cbTotalColumn, javax.swing.GroupLayout.PREFERRED_SIZE,
		                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
		                Short.MAX_VALUE)
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		            .addGroup(
		                jPanel4Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		                    .addComponent(jLabel7)
		                    .addComponent(panelCbo, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)));

		lbShowReport.setText(">>"); // NOI18N
		lbShowReport.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				lbShowReportMouseClicked(evt);
			}
		});

		jPanel1.setOpaque(false);
		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        jPanel1Layout
		            .createSequentialGroup()
		            .addContainerGap()
		            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
		                Short.MAX_VALUE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		            .addComponent(lbShowReport)));
		jPanel1Layout.setVerticalGroup(jPanel1Layout
		    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
		        Short.MAX_VALUE).addComponent(lbShowReport, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE));

		panelReport.setBackground(new java.awt.Color(255, 255, 255));
		panelReport.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		panelReport.setOpaque(false);
		jScrollPane3.setBorder(null);

		tableDateReport.setModel(new javax.swing.table.DefaultTableModel(new Object[][] { { null, null, null, null },
		    { null, null, null, null }, { null, null, null, null }, { null, null, null, null } }, new String[] { "Title 1",
		    "Title 2", "Title 3", "Title 4" }));
		tableDateReport.setRowHeight(23);
		tableDateReport.setOpaque(false);
		setFont(new Font("Tahoma", 0, 14));
		tableDateReport.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		tableDateReport.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		jScrollPane3.setViewportView(tableDateReport);

		lbImageReport.setText(""); // NOI18N
		lbImageReport.setPreferredSize(new java.awt.Dimension(150, 100));

		lbEnterpriseReport.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		lbEnterpriseReport.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lbEnterpriseReport.setText(""); // NOI18N

		lbAddressReport.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lbAddressReport.setText(""); // NOI18N
		lbAddressReport.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

		lbReportNameReport.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
		lbReportNameReport.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lbReportNameReport.setText(""); // NOI18N

		lbTimeCreateReport.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lbTimeCreateReport.setText(""); // NOI18N

		jLabel.setText("Người lập báo cáo"); // NOI18N

		lbPersonCreateReport.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lbPersonCreateReport.setText(""); // NOI18N

		javax.swing.GroupLayout panelReportLayout = new javax.swing.GroupLayout(panelReport);
		panelReport.setLayout(panelReportLayout);
		panelReportLayout.setHorizontalGroup(panelReportLayout
		    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE,
		        485, Short.MAX_VALUE)
		    .addGroup(
		        javax.swing.GroupLayout.Alignment.TRAILING,
		        panelReportLayout
		            .createSequentialGroup()
		            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		            .addComponent(lbPersonCreateReport, javax.swing.GroupLayout.PREFERRED_SIZE, 204,
		                javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap())
		    .addGroup(
		        panelReportLayout
		            .createSequentialGroup()
		            .addContainerGap()
		            .addGroup(
		                panelReportLayout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addGroup(
		                        panelReportLayout
		                            .createSequentialGroup()
		                            .addComponent(lbImageReport, javax.swing.GroupLayout.PREFERRED_SIZE,
		                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		                            .addGroup(
		                                panelReportLayout
		                                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                                    .addComponent(lbAddressReport, javax.swing.GroupLayout.Alignment.TRAILING,
		                                        javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
		                                    .addComponent(lbEnterpriseReport, javax.swing.GroupLayout.Alignment.TRAILING,
		                                        javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
		                                    .addComponent(lbReportNameReport, javax.swing.GroupLayout.DEFAULT_SIZE, 315,
		                                        Short.MAX_VALUE)
		                                    .addComponent(lbTimeCreateReport, javax.swing.GroupLayout.Alignment.TRAILING,
		                                        javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)))
		                    .addGroup(
		                        javax.swing.GroupLayout.Alignment.TRAILING,
		                        panelReportLayout
		                            .createSequentialGroup()
		                            .addGap(0, 0, Short.MAX_VALUE)
		                            .addComponent(jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 160,
		                                javax.swing.GroupLayout.PREFERRED_SIZE)))));
		panelReportLayout.setVerticalGroup(panelReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        panelReportLayout
		            .createSequentialGroup()
		            .addContainerGap()
		            .addGroup(
		                panelReportLayout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(lbImageReport, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addGroup(
		                        panelReportLayout.createSequentialGroup().addComponent(lbEnterpriseReport)
		                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                            .addComponent(lbAddressReport).addGap(18, 18, 18).addComponent(lbReportNameReport)
		                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                            .addComponent(lbTimeCreateReport)))
		            .addGap(10, 10, 10)
		            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		            .addComponent(jLabel)
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		            .addComponent(lbPersonCreateReport, javax.swing.GroupLayout.PREFERRED_SIZE, 14,
		                javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()));

		labelIcon = new javax.swing.JLabel();
		labelIcon.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/description.png")));
		labelTitle = new javax.swing.JLabel("Báo cáo");
		labelTitle.setFont(new java.awt.Font("Tahoma", 1, 14));
		labelTitle.setForeground(new java.awt.Color(126, 0, 0));

		JLabel lbHKT = new JLabel();
		lbHKT.setFont(new java.awt.Font("Tahoma", 1, 16));
		lbHKT.setForeground(new java.awt.Color(126, 0, 0));
		lbHKT.setHorizontalAlignment(SwingConstants.RIGHT);
		lbHKT.setText("HKT Sofware   ");
		lbHKT.setPreferredSize(new Dimension(WIDTH, 50));

		panel2 = new PanelTrans();
		panel2.setOpaque(false);
		panel2.setLayout(new BorderLayout());
		panelTextKeyboard = new PanelTextKeyboard();
		panelTextKeyboard.setVisible(false);
		panelComp = new JPanel();
		panelComp.setOpaque(false);
		panelComp.setLayout(new BorderLayout());
		panelComp.add(lbMeseg, BorderLayout.CENTER);
		panelComp.add(panelTextKeyboard, BorderLayout.SOUTH);

		panel2.add(jPanel1, BorderLayout.CENTER);
		panel2.add(createLable(), BorderLayout.SOUTH);
		panel2.add(createLable(), BorderLayout.EAST);
		panel2.add(createLable(), BorderLayout.WEST);
		panel2.add(createLable(), BorderLayout.NORTH);

		PanelTrans panel = new PanelTrans();
		panel.setOpaque(false);
		GroupLayout panelBackgroundResto1Layout = new GroupLayout(panel);
		panel.setLayout(panelBackgroundResto1Layout);
		panelBackgroundResto1Layout.setHorizontalGroup(panelBackgroundResto1Layout
		    .createParallelGroup(GroupLayout.Alignment.LEADING)
		    .addGroup(
		        panelBackgroundResto1Layout.createSequentialGroup().addGap(28, 28, 28)
		            .addComponent(labelIcon, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE).addGap(6, 6, 6)
		            .addComponent(labelTitle, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))
		    .addGroup(
		        panelBackgroundResto1Layout.createSequentialGroup().addGap(25, 25, 25)
		            .addComponent(panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
		            .addGap(25, 25, 25))
		    .addGroup(
		        panelBackgroundResto1Layout.createSequentialGroup().addGap(23, 23, 23)
		            .addComponent(btnPrintReport, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
		            .addGap(4, 4, 4).addComponent(jLabel30, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE).addGap(4, 4, 4)
		            .addComponent(btnExt3, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE).addGap(4, 4, 4)
		            .addComponent(btnExt2, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE).addGap(4, 4, 4)
		            .addComponent(btnExt1, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE).addGap(4, 4, 4)
		            .addComponent(btnKeyboard, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE).addGap(4, 4, 4)
		            .addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE).addGap(4, 4, 4)
		            .addComponent(btnPrint, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE).addGap(4, 4, 4)
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
		                    panelBackgroundResto1Layout.createSequentialGroup().addGap(11, 11, 11)
		                        .addComponent(labelTitle, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)))
		        .addGap(6, 6, 6)
		        .addComponent(panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
		        .addGap(11, 11, 11)
		        .addGroup(
		            panelBackgroundResto1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		                .addComponent(btnPrintReport, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
		                .addComponent(jLabel30, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
		                .addComponent(btnExt3, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
		                .addComponent(btnExt2, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
		                .addComponent(btnExt1, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
		                .addComponent(btnKeyboard, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
		                .addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
		                .addComponent(btnPrint, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
		                .addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
		        .addGap(11, 11, 11)));

		panelBackground.setLayout(new BorderLayout());
		panelBackground.add(panel, BorderLayout.CENTER);
		panelBackground.add(lbHKT, BorderLayout.NORTH);
		panelBackground.add(panelReport, BorderLayout.EAST);
		panelBackground.add(createLable(), BorderLayout.WEST);
		panelBackground.add(panelComp, BorderLayout.SOUTH);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(panelBackground,
		    GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(panelBackground,
		    GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private JLabel createLable() {
		JLabel label = new JLabel();
		label.setSize(20, 20);
		label.setPreferredSize(label.getSize());
		return label;
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

	private void lbShowReportMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_lbShowReportMouseClicked
		panelReport.setVisible(!panelReport.isVisible());
		if (panelReport.isVisible()) {
			lbShowReport.setText("<<");
		} else {
			lbShowReport.setText(">>");
		}
	}// GEN-LAST:event_lbShowReportMouseClicked

	private void txtEnterpriseCaretUpdate(javax.swing.event.CaretEvent evt) {// GEN-FIRST:event_txtEnterpriseCaretUpdate
		lbEnterpriseReport.setText(txtEnterprise.getText());
	}// GEN-LAST:event_txtEnterpriseCaretUpdate

	private void txtAddressCaretUpdate(javax.swing.event.CaretEvent evt) {// GEN-FIRST:event_txtAddressCaretUpdate
		lbAddressReport.setText(txtAddress.getText());
	}// GEN-LAST:event_txtAddressCaretUpdate

	private void txtPersonCreateCaretUpdate(javax.swing.event.CaretEvent evt) {// GEN-FIRST:event_txtPersonCreateCaretUpdate
		lbPersonCreateReport.setText(txtPersonCreate.getText());
	}// GEN-LAST:event_txtPersonCreateCaretUpdate

	private void txtReportNameCaretUpdate(javax.swing.event.CaretEvent evt) {// GEN-FIRST:event_txtReportNameCaretUpdate
		lbReportNameReport.setText(txtReportName.getText());
	}// GEN-LAST:event_txtReportNameCaretUpdate

	@SuppressWarnings("unchecked")
	private void btnAddOneActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAddOneActionPerformed
		if (listHeader2.size() < numberColumnMax) {
			listHeader2.addElement(listHeader1.get(indexPrimary));
			listHeader1.remove(indexPrimary);
			loadTableReport();
		}
	}// GEN-LAST:event_btnAddOneActionPerformed

	@SuppressWarnings("unchecked")
	private void btnAddAllActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAddAllActionPerformed
		for (int i = 0; i < listHeader1.size();) {
			if (listHeader2.size() >= numberColumnMax) {
				break;
			} else {
				listHeader2.addElement(listHeader1.get(i));
				listHeader1.remove(i);
			}
		}
		loadTableReport();
	}// GEN-LAST:event_btnAddAllActionPerformed

	@SuppressWarnings("unchecked")
	private void btnRemoveOneActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnRemoveOneActionPerformed
		listHeader1.addElement(listHeader2.get(indexFinal));
		listHeader2.remove(indexFinal);
		loadTableReport();
	}// GEN-LAST:event_btnRemoveOneActionPerformed

	@SuppressWarnings("unchecked")
	private void btnRemoveAllActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnRemoveAllActionPerformed
		for (int i = 0; i < listHeader2.size();) {
			listHeader1.addElement(listHeader2.get(i));
			listHeader2.remove(i);
		}
		loadTableReport();
	}// GEN-LAST:event_btnRemoveAllActionPerformed

	private void cbTotalColumnItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_cbTotalColumnItemStateChanged
		numberColumnMax = cbTotalColumn.getSelectedIndex() + 1;
		removeColumeOver();
	}// GEN-LAST:event_cbTotalColumnItemStateChanged

	private void lstPrimaryMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_lstPrimaryMouseClicked
		indexPrimary = lstPrimary.getSelectedIndex();
	}// GEN-LAST:event_lstPrimaryMouseClicked

	private void lstFinalMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_lstFinalMouseClicked
		indexFinal = lstFinal.getSelectedIndex();
	}// GEN-LAST:event_lstFinalMouseClicked

	private void btnPrintReportActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnPrintReportActionPerformed
		printReport(false);
	}// GEN-LAST:event_btnPrintReportActionPerformed

	private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnPrintActionPerformed
		printReport(true);
	}// GEN-LAST:event_btnPrintActionPerformed

	private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnExitActionPerformed
		this.dispose();
	}// GEN-LAST:event_btnExitActionPerformed
	 // Variables declaration - do not modify//GEN-BEGIN:variables

	private PanelBackground panelBackground;
	private JLabel labelIcon, labelTitle, jLabel30, lbMeseg;
	private JScrollPane btnExt1, btnExt2, btnExt3;
	private PanelTrans panel2;
	private PanelTextKeyboard panelTextKeyboard;
	private JButton btnKeyboard, btnDelete;
	private Profile profile;

	private javax.swing.JButton btnAddAll;
	private javax.swing.JButton btnAddOne;
	private javax.swing.JButton btnPrintReport;
	private javax.swing.JButton btnRemoveAll;
	private javax.swing.JButton btnRemoveOne;
	@SuppressWarnings("rawtypes")
	private JComboBox cbTotalColumn, cbSelectedPrinter, cboSize;
	private javax.swing.JButton btnPrint;
	private javax.swing.JButton btnExit;
	private javax.swing.JLabel jLabel;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JLabel lbAddressReport;
	private javax.swing.JLabel lbEnterpriseReport;
	private javax.swing.JLabel lbImageReport;
	private javax.swing.JLabel lbPersonCreateReport;
	private javax.swing.JLabel lbReportNameReport;
	private javax.swing.JLabel lbShowReport;
	private javax.swing.JLabel lbTimeCreateReport;
	@SuppressWarnings("rawtypes")
	private javax.swing.JList lstFinal, lstPrimary;
	private javax.swing.JPanel panelReport, panelComp;
	private javax.swing.JTable tableDateReport;
	private javax.swing.JTextField txtAddress;
	private javax.swing.JTextField txtEnterprise;
	private javax.swing.JTextField txtPersonCreate;
	private javax.swing.JTextField txtReportName;

	// End of variables declaration//GEN-END:variables

	public void setPersonCreate(String personName) {
		txtPersonCreate.setText(personName);
	}

	public void setDateCreate(Date dateCreate) {
		// dcDateCreate.setDate(dateCreate);
	}

	public void setEnterprise(String enterpriseName) {
		txtEnterprise.setText(enterpriseName);
	}

	public void setLogoEnterprise(ImageIcon image) {
		// try {
		// if (image == null) {
		// this.logo = new
		// ImageIcon(getClass().getResource("/com/hkt/client/swingexp/app/print/template/logo.jpg"));
		// } else {
		// this.logo = image;
		// }
		// } catch (Exception e) {
		// this.logo = new
		// ImageIcon(getClass().getResource("/com/hkt/client/swingexp/app/print/template/logo.jpg"));
		// }
		lbImageReport.setIcon(new ImageIcon());
	}

	public void setAddress(String address) {
		txtAddress.setText(address);
	}

	public void setReportName(String reportName) {
		txtReportName.setText(reportName);
	}

	public void setTable(JTable table) {
		this.table = table;
		loadColumeTable();
	}

	@Override
	public void setVisible(boolean b) {
		resetData();
		super.setVisible(b);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void resetData() {
		DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
		PrintService[] printService = PrintServiceLookup.lookupPrintServices(flavor, null);
		DefaultComboBoxModel modelPrinter = new DefaultComboBoxModel(printService);
		cbSelectedPrinter.setModel(modelPrinter);
		// dcDateCreate.setDate(new Date());
		cbTotalColumn.removeAllItems();
		for (int i = 0; i < 10; i++) {
			cbTotalColumn.addItem("Báo cáo có " + (i + 1) + "cột");
		}
		model = new javax.swing.table.DefaultTableModel();
		tableDateReport.setModel(model);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void loadColumeTable() {
		listHeader1 = new DefaultListModel();
		for (int i = 0; i < table.getColumnCount(); i++) {
			listHeader1.addElement(table.getColumnName(i));
		}
		listHeader2 = new DefaultListModel();
		lstPrimary.setModel(listHeader1);
		lstFinal.setModel(listHeader2);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void printReport(boolean view_print) {
		loadTableReport();
		filePathResource = getPathResource(numberColumnMax);
		String[] keys = new String[] { "prTenDoanhNghiep", "prDiaChi", "prNgayLap", "prNguoiLapBaoCao", "prTenBaoCao" };
		String[] values = new String[] { txtEnterprise.getText(), txtAddress.getText(),
		    new SimpleDateFormat("dd/MM/yyyy").format(dc.getDate()), txtPersonCreate.getText(), txtReportName.getText() };
		hashMap = new HashMap();
		for (int i = 0; i < numberColumnMax; i++) {
			hashMap.put("prColumn_" + i, headerReport[i]);
		}
		if (keys.length == values.length) {
			for (int i = 0; i < keys.length; i++) {
				hashMap.put(keys[i], values[i]);
			}
		}
		hashMap.put("logo", logo.getImage());
		System.out.println(filePathResource+"  "+hashMap+"  "+model);
		reportExport(filePathResource, hashMap, model, view_print);
		this.dispose();
	}

	// Chạy phiếu in khi khởi động
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void printReport1(boolean view_print) {
		loadTableReport();
		filePathResource = getPathResource(numberColumnMax);
		String[] keys = new String[] { "prTenDoanhNghiep", "prDiaChi", "prNgayLap", "prNguoiLapBaoCao", "prTenBaoCao" };
		String[] values = new String[] { txtEnterprise.getText(), txtAddress.getText(),
		    new SimpleDateFormat("dd/MM/yyyy").format(dc.getDate()), txtPersonCreate.getText(), txtReportName.getText() };
		hashMap = new HashMap();
		for (int i = 0; i < numberColumnMax; i++) {
			hashMap.put("prColumn_" + i, headerReport[i]);
		}
		if (keys.length == values.length) {
			for (int i = 0; i < keys.length; i++) {
				hashMap.put(keys[i], values[i]);
			}
		}
		hashMap.put("logo", logo.getImage());
		ReportManager.getInstance().printReport1(filePathResource, hashMap, model, null, false);
		this.dispose();
	}

	private String getPathResource(int numberColum) {
		String jas = ".jasper";
		if (cboSize.getSelectedItem().toString().equals("A5")) {
			jas = "A5.jasper";
		}
		if (cboSize.getSelectedItem().toString().equals("K80")) {
			jas = "K80.jasper";
		}
		String name = "rpt" + numberColum + jas;
		String str;
		File file = HKTFile.getFile("TemplatePrint", name);
		if (file.exists()) {
			str = file.getAbsolutePath();
			flagExtension = true;
		} else {
			str = "template/rpt" + numberColum + jas;
			flagExtension = false;
		}

		return str;
	}

	@SuppressWarnings("rawtypes")
	private void reportExport(String filePathResource, HashMap hashMap, TableModel model, boolean view_print) {
		if (!view_print) {
			ReportManager.getInstance().viewReport(filePathResource, hashMap, model, flagExtension);
		} else {
			ReportManager.getInstance().printReportSize(filePathResource, hashMap, model,
			    (PrintService) cbSelectedPrinter.getSelectedItem(), flagExtension, cboSize.getSelectedItem().toString());
		}
	}

	public void exelReport(DefaultTableModel model, String name) {
		headerReport = new String[model.getColumnCount()];
		for (int i = 0; i < model.getColumnCount(); i++) {
			headerReport[i] = model.getColumnName(i);
		}
		filePathResource = getPathResource(10);
		String[] keys = new String[] { "prTenDoanhNghiep", "prDiaChi", "prNgayLap", "prNguoiLapBaoCao", "prTenBaoCao" };
		String[] values = new String[] { txtEnterprise.getText(), txtAddress.getText(),
		    new SimpleDateFormat("dd/MM/yyyy").format(dc.getDate()), txtPersonCreate.getText(), txtReportName.getText() };
		hashMap = new HashMap();
		for (int i = 0; i < 10; i++) {
			hashMap.put("prColumn_" + i, headerReport[i]);
		}
		if (keys.length == values.length) {
			for (int i = 0; i < keys.length; i++) {
				hashMap.put(keys[i], values[i]);
			}
		}
		hashMap.put("logo", logo.getImage());
		reportExel(filePathResource, hashMap, model, name);
	}
	
	public void exelReportMail(DefaultTableModel model, String name) {
		headerReport = new String[model.getColumnCount()];
		for (int i = 0; i < model.getColumnCount(); i++) {
			headerReport[i] = model.getColumnName(i);
		}
		filePathResource = getPathResource(10);
		String[] keys = new String[] { "prTenDoanhNghiep", "prDiaChi", "prNgayLap", "prNguoiLapBaoCao", "prTenBaoCao" };
		String[] values = new String[] { txtEnterprise.getText(), txtAddress.getText(),
		    new SimpleDateFormat("dd/MM/yyyy").format(dc.getDate()), txtPersonCreate.getText(), txtReportName.getText() };
		hashMap = new HashMap();
		for (int i = 0; i < 10; i++) {
			hashMap.put("prColumn_" + i, headerReport[i]);
		}
		if (keys.length == values.length) {
			for (int i = 0; i < keys.length; i++) {
				hashMap.put(keys[i], values[i]);
			}
		}
		hashMap.put("logo", logo.getImage());
		reportExelMail(filePathResource, hashMap, model, name);
	}

	private void reportExel(String filePathResource, HashMap hashMap, TableModel model, String name) {
		ReportManager.getInstance().exelReport(filePathResource, hashMap, model, flagExtension, name);
	}
	
	private void reportExelMail(String filePathResource, HashMap hashMap, TableModel model, String name) {
		ReportManager.getInstance().exelReportMail(filePathResource, hashMap, model, flagExtension, name);
	}

	private void loadTableReport() {
		headerReport = new String[numberColumnMax];
		for (int i = 0; i < numberColumnMax; i++) {
			if (i < listHeader2.size()) {
				headerReport[i] = listHeader2.getElementAt(i).toString();
			} else {
				headerReport[i] = "";
			}
		}
		model = new TableModeConvert(table, headerReport);
		tableDateReport.setModel(model);
	}

	@SuppressWarnings("unchecked")
	private void removeColumeOver() {
		if (listHeader2 == null || listHeader1 == null) {
			return;
		}
		for (int i = 0; i < listHeader2.size() - numberColumnMax; i++) {
			listHeader1.addElement(listHeader2.get(listHeader2.size() - 1));
			listHeader2.remove(listHeader2.getSize() - 1);
		}
		loadTableReport();
	}
}
