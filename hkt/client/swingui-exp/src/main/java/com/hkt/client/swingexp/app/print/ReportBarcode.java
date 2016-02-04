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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.hkt.client.swingexp.app.component.ExtendJButton;
import com.hkt.client.swingexp.app.component.PanelBackground;
import com.hkt.client.swingexp.app.component.PanelTrans;
import com.hkt.client.swingexp.app.component.TableFillterSorter;
import com.hkt.client.swingexp.app.core.HKTFile;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDialog;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.virtualkey.text.PanelTextKeyboard;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.module.account.entity.Profile;

@SuppressWarnings("serial")
public class ReportBarcode extends javax.swing.JDialog{
  @SuppressWarnings("unused")
private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
  private String fileNamePath;
  @SuppressWarnings("rawtypes")
private HashMap hashMap;
  private PrintService printService;
  private String size;
  private boolean flagExtension;
  private DefaultTableModel model1, model2, model3;
  private String[] header = new String[]{"Mã Barcode", "Tên sản phẩm", "Đơn giá", "ĐV Tiền"};
  private boolean flagEditTable;
  @SuppressWarnings("unused")
private boolean firstClick;
  private TableFillterSorter tableFillterSorter;
  private String strSearch;
  private PanelBackground panelBackground;
  private JLabel labelIcon, labelTitle, jLabel30, lbMeseg;
  private JScrollPane btnExt1, btnExt2, btnExt3;
  private PanelTrans panel2;
  private PanelTextKeyboard panelTextKeyboard;
  private JButton btnKeyboard, btnDelete;
  @SuppressWarnings("unused")
private Profile profile;
  private JPanel panelComp;
  private JCheckBox chbAll;

  @SuppressWarnings({ "rawtypes", "unchecked" })
public ReportBarcode() {
      initComponents();
      setModal(true);
      dispose();
      setUndecorated(true);
      addKeyBindings(panelBackground);
      addVituarKey(panelBackground);
      
      DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
      PrintService[] printService = PrintServiceLookup.lookupPrintServices(flavor, null);
      DefaultComboBoxModel modelPrinter = new DefaultComboBoxModel(printService);
      jComboBox1.setModel(modelPrinter);
      jComboBox1.addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(jComboBox1.getSelectedItem().toString().indexOf("Xprinter")>=0){
						chbAll.setSelected(true);
					}else {
						chbAll.setSelected(false);
					}
					
				}
			});
      if(jComboBox1.getSelectedItem().toString().indexOf("Xprinter")>=0){
				chbAll.setSelected(true);
			}else {
				chbAll.setSelected(false);
			}
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
  
  @SuppressWarnings({ "unchecked", "rawtypes" })
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
		btnKeyboard.setIcon(new ImageIcon(HomeScreen.class
				.getResource("icon/refresh1.png")));
		btnKeyboard.addMouseListener(new MouseEventClickButtonDialog(
				"refresh1.png", "refresh1.png", "refresh1.png"));
		btnDelete = new JButton();
		btnDelete.setVisible(false);
		lbMeseg = new JLabel();
		lbMeseg.setPreferredSize(new Dimension(WIDTH, 23));

    jPanel3 = new javax.swing.JPanel();
    jPanel1 = new javax.swing.JPanel();
    jScrollPane1 = new javax.swing.JScrollPane();
    tableDateReport = new javax.swing.JTable();
    jPanel4 = new javax.swing.JPanel();
    jLabel2 = new javax.swing.JLabel();
    txtSearch = new javax.swing.JTextField();
    jLabel3 = new javax.swing.JLabel();
    cbSearch = new javax.swing.JComboBox();
    jButton1 = new ExtendJButton(">");
    jButton2 = new ExtendJButton(">>");
    jButton3 = new ExtendJButton("<");
    jButton4 = new ExtendJButton("<<");
    jPanel2 = new javax.swing.JPanel();
    jScrollPane2 = new javax.swing.JScrollPane();
    tableDateReport1 = new javax.swing.JTable();
    jLabel7 = new javax.swing.JLabel();
    jComboBox1 = new javax.swing.JComboBox();
    jButton5 = new javax.swing.JButton();
    jButton6 = new javax.swing.JButton();
    jLabel1 = new javax.swing.JLabel();
    jComboBox2 = new javax.swing.JComboBox();
    chbName = new javax.swing.JCheckBox();
    chbGia = new javax.swing.JCheckBox();

    jPanel3.setBackground(new java.awt.Color(255, 255, 255));

    jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(204, 204, 204), null, null), "Chọn sản phẩm in", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(124, 0, 0))); // NOI18N
    jPanel1.setOpaque(false);

    tableDateReport.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null}
        },
        new String [] {
            "Title 1", "Title 2", "Title 3", "Title 4"
        }
    ));
    tableDateReport.setRowHeight(23);
    tableDateReport.setOpaque(false);
    tableDateReport.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
    tableDateReport.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
    
    jScrollPane1.setViewportView(tableDateReport);

    jPanel4.setOpaque(false);

    jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    jLabel2.setText("Tìm"); // NOI18N

    txtSearch.setFont(new java.awt.Font("Tahoma", 0, 14));
    txtSearch.setText(""); // NOI18N
    txtSearch.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            txtSearchMouseClicked(evt);
        }
    });
    txtSearch.addCaretListener(new javax.swing.event.CaretListener() {
        public void caretUpdate(javax.swing.event.CaretEvent evt) {
            txtSearchCaretUpdate(evt);
        }
    });

    jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14));
    jLabel3.setText("Theo"); // NOI18N

    cbSearch.setFont(new java.awt.Font("Tahoma", 0, 14));
    cbSearch.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    cbSearch.addItemListener(new java.awt.event.ItemListener() {
        public void itemStateChanged(java.awt.event.ItemEvent evt) {
            cbSearchItemStateChanged(evt);
        }
    });

    javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
    jPanel4.setLayout(jPanel4Layout);
    jPanel4Layout.setHorizontalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel4Layout.createSequentialGroup()
            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(4, 4, 4)
            .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
            .addGap(10, 10, 10)
            .addComponent(jLabel3)
            .addGap(5, 5, 5)
            .addComponent(cbSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
    );
    jPanel4Layout.setVerticalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addComponent(cbSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
    );

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
    );
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE))
    );


    jButton1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
        }
    });


    jButton2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton2ActionPerformed(evt);
        }
    });


    jButton3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton3ActionPerformed(evt);
        }
    });


    jButton4.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton4ActionPerformed(evt);
        }
    });
    
    jButton1.addMouseListener(new MouseEventClickButtonDialog("", "", ""));
    jButton2.addMouseListener(new MouseEventClickButtonDialog("", "", ""));
    jButton3.addMouseListener(new MouseEventClickButtonDialog("", "", ""));
    jButton4.addMouseListener(new MouseEventClickButtonDialog("", "", ""));

    jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(204, 204, 204), null, null), "Thiết lập in", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(124, 0, 0))); // NOI18N
    jPanel2.setOpaque(false);

    tableDateReport1.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Lần in", "Mã Barcode", "Tên sản phẩm", "Đơn giá", "ĐV tiền"
        }
    ) {
        boolean[] canEdit = new boolean [] {
            true,  false, true, true, false
        };

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    
    tableDateReport1.setRowHeight(23);
    tableDateReport1.setOpaque(false);
    tableDateReport1.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
    tableDateReport1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
    tableDateReport1.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            tableDateReport1MouseClicked(evt);
        }
    });
    jScrollPane2.setViewportView(tableDateReport1);
    tableDateReport1.getColumnModel().getColumn(0).setMaxWidth(50);
    tableDateReport1.getColumnModel().getColumn(0).setHeaderValue("Lần in"); // NOI18N
    tableDateReport1.getColumnModel().getColumn(1).setResizable(false);
    tableDateReport1.getColumnModel().getColumn(1).setHeaderValue("Mã Barcode"); // NOI18N
    tableDateReport1.getColumnModel().getColumn(2).setHeaderValue("Tên sản phẩm"); // NOI18N
    tableDateReport1.getColumnModel().getColumn(3).setMaxWidth(160);
    tableDateReport1.getColumnModel().getColumn(3).setHeaderValue("Đơn giá"); // NOI18N
    tableDateReport1.getColumnModel().getColumn(4).setMaxWidth(70);
    tableDateReport1.getColumnModel().getColumn(4).setHeaderValue("ĐV tiền"); // NOI18N

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
    );
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE))
    );

    jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14));
    jLabel7.setText("Máy in"); // NOI18N

    jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 14));
    jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

    jButton5.setFont(new Font("Tahoma", 1, 14));
    jButton5.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/print1.png")));
    jButton5.addMouseListener(new MouseEventClickButtonDialog("print1.png", "print1.png", "print1.png"));
    jButton5.setText("In");
    jButton5.setIconTextGap(10);
    jButton5.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton5ActionPerformed(evt);
        }
    });

    jButton6.setFont(new Font("Tahoma", 1, 14));
    jButton6.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/iconBack.png")));
    jButton6.setText("Thoát");
    jButton6.addMouseListener(new MouseEventClickButtonDialog("iconBack.png", "iconBack.png", "iconBack.png"));
    jButton6.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton6ActionPerformed(evt);
        }
    });

    jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14));
    jLabel1.setText("Khổ giấy"); // NOI18N

    jComboBox2.setFont(new java.awt.Font("Tahoma", 0, 14));
    jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "104-22", "106-22", "70-22", "104-32" ,"70-22-xprinter" }));

    chbName.setFont(new java.awt.Font("Tahoma", 0, 14));
    chbName.setSelected(true);
    chbName.setText("In tên sản phẩm"); // NOI18N
    chbName.setOpaque(false);
    chbAll= new JCheckBox();
    chbAll.setFont(new java.awt.Font("Tahoma", 0, 14));
//    chbAll.setSelected(true);
    chbAll.setText("Máy in Xprinter"); // NOI18N
    chbAll.setOpaque(false);
    chbAll.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(chbAll.isSelected()){
					jComboBox2.setSelectedIndex(4);
					jComboBox2.setEnabled(false);    
				}else {
					jComboBox2.setSelectedIndex(0);
					jComboBox2.setEnabled(true);   
				}
				
			}
		});

    chbGia.setFont(new java.awt.Font("Tahoma", 0, 14));
    chbGia.setSelected(true);
    chbGia.setText("In giá sản phẩm"); // NOI18N
    chbGia.setOpaque(false);

    jPanel3.setOpaque(false);
    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addComponent(jLabel7)
                    .addGap(18, 18, 18)
                    .addComponent(jComboBox1, 0, 220, Short.MAX_VALUE)
                    .addGap(4, 4, 4)
                    .addComponent(jLabel1)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                		.addComponent(chbAll, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10, 10, 10)
                    .addComponent(chbName, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10, 10, 10)
                    .addComponent(chbGia))
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap())
    );
    jPanel3Layout.setVerticalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGap(4, 4, 4)
                                    .addComponent(jLabel7))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGap(1, 1, 1)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGap(4, 4, 4)
                                    .addComponent(jLabel1))))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(7, 7, 7)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                 .addComponent(chbAll)
                                .addComponent(chbName)
                                .addComponent(chbGia)))))
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(159, 159, 159)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jButton2)
                    .addGap(6, 6, 6)
                    .addComponent(jButton3)
                    .addGap(6, 6, 6)
                    .addComponent(jButton4)))
            .addContainerGap())
    );

    jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jButton2, jButton3, jButton4});
    
    labelIcon = new javax.swing.JLabel();
    labelIcon.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/description.png")));
    labelTitle = new javax.swing.JLabel("In Barcode");
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
    
    panel2.add(jPanel3, BorderLayout.CENTER);
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
                .addGap(4, 4, 4).addComponent(jLabel30, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE).addGap(4, 4, 4)
                .addComponent(btnExt3, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE).addGap(4, 4, 4)
                .addComponent(btnExt2, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE).addGap(4, 4, 4)
                .addComponent(btnExt1, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE).addGap(4, 4, 4)
                .addComponent(btnKeyboard, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE).addGap(4, 4, 4)
                .addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE).addGap(4, 4, 4)
                .addComponent(jButton5, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE).addGap(4, 4, 4)
                .addComponent(jButton6, GroupLayout.DEFAULT_SIZE, 103, GroupLayout.PREFERRED_SIZE).addGap(22, 22, 22)));
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
                    .addComponent(jLabel30, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExt3, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExt2, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExt1, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnKeyboard, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
            .addGap(11, 11, 11)));
    
    panelBackground.setLayout(new BorderLayout());
    panelBackground.add(panel, BorderLayout.CENTER);
    panelBackground.add(lbHKT, BorderLayout.NORTH);
    panelBackground.add(createLable(), BorderLayout.EAST);
    panelBackground.add(createLable(), BorderLayout.WEST);
    panelBackground.add(panelComp, BorderLayout.SOUTH);
    
    
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(panelBackground,
        GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(panelBackground,
        GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
    
}// </editor-fold>                        

  
  
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
private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {                                         
printReport(true);
}                                        

private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {                                         
this.dispose();
}                                        

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
int k = tableDateReport.getSelectedRow();
if (k >= 0) {
    List<String> str = new ArrayList<String>();
    str.add("1");
    for (int i = 0; i < tableDateReport.getColumnCount(); i++) {
        str.add(tableDateReport.getValueAt(k, i).toString());
    }
    model2.addRow(str.toArray());
    txtSearch.setText("");
    int h = tableDateReport.getSelectedRow();
    model1.removeRow(h);
}
}                                        

private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                         
if (tableDateReport1.getSelectedRow() >= 0) {
    List<String> str = new ArrayList<String>();
    for (int i = 1; i < model2.getColumnCount(); i++) {
        str.add(model2.getValueAt(tableDateReport1.getSelectedRow(), i).toString());
    }
    model1.addRow(str.toArray());
    model2.removeRow(tableDateReport1.getSelectedRow());
}
}                                        

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
int k = model2.getRowCount();
while (k > 0) {
    model2.removeRow(k - 1);
    k--;
}
int h = model1.getRowCount();
while (h > 0) {
    model1.removeRow(h - 1);
    h--;
}
for (int i = 0; i < model3.getRowCount(); i++) {
    String[] values = new String[]{"1", model3.getValueAt(i, 0).toString(),
        model3.getValueAt(i, 1).toString(), model3.getValueAt(i, 2).toString(), model3.getValueAt(i, 3).toString(),};
    model2.addRow(values);
}
tableDateReport1.setModel(model2);
}                                        

private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {                                         
int k = model2.getRowCount();
while (k > 0) {
    model2.removeRow(k - 1);
    k--;
}
int h = model1.getRowCount();
while (h > 0) {
    model1.removeRow(h - 1);
    h--;
}
for (int i = 0; i < model3.getRowCount(); i++) {
    String[] values = new String[]{model3.getValueAt(i, 0).toString(),
        model3.getValueAt(i, 1).toString(), model3.getValueAt(i, 2).toString(), model3.getValueAt(i, 3).toString(),};
    model1.addRow(values);
}
tableDateReport.setModel(model1);
}                                        

private void tableDateReport1MouseClicked(java.awt.event.MouseEvent evt) {                                              
if (tableDateReport1.getSelectedColumn() == 0) {
    flagEditTable = true;
}
}                                             

private void txtSearchMouseClicked(java.awt.event.MouseEvent evt) {                                       
}                                      

private void txtSearchCaretUpdate(javax.swing.event.CaretEvent evt) {                                      
//if (!firstClick) {
//    firstClick = true;
//    txtSearch.setText("");
//}
searchData();
}                                     

private void cbSearchItemStateChanged(java.awt.event.ItemEvent evt) {                                          
strSearch = cbSearch.getSelectedItem().toString();
searchData();
}                                         
// Variables declaration - do not modify                     
@SuppressWarnings("rawtypes")
private javax.swing.JComboBox cbSearch;
private javax.swing.JCheckBox chbGia;
private javax.swing.JCheckBox chbName;
private javax.swing.JButton jButton1;
private javax.swing.JButton jButton2;
private javax.swing.JButton jButton3;
private javax.swing.JButton jButton4;
private javax.swing.JButton jButton5;
private javax.swing.JButton jButton6;
@SuppressWarnings("rawtypes")
private javax.swing.JComboBox jComboBox1, jComboBox2;
private javax.swing.JLabel jLabel1;
private javax.swing.JLabel jLabel2;
private javax.swing.JLabel jLabel3;
private javax.swing.JLabel jLabel7;
private javax.swing.JPanel jPanel1;
private javax.swing.JPanel jPanel2;
private javax.swing.JPanel jPanel3;
private javax.swing.JPanel jPanel4;
private javax.swing.JScrollPane jScrollPane1;
private javax.swing.JScrollPane jScrollPane2;
private javax.swing.JTable tableDateReport;
private javax.swing.JTable tableDateReport1;
private javax.swing.JTextField txtSearch;
// End of variables declaration                   

@SuppressWarnings({ "rawtypes", "unchecked" })
public void setTableModel(DefaultTableModel model) {
   // this.model3 = model;
    model1 = new DefaultTableModel(header, 0) {

        boolean[] canEdit = new boolean[]{
            false, false, false, false
        };

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit[columnIndex];
        }
    };
    model3 = new DefaultTableModel(header, 0) {

        boolean[] canEdit = new boolean[]{
            false, false, false, false
        };

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit[columnIndex];
        }
    };
    for (int i = 0; i < model.getRowCount(); i++) {
        String[] values = new String[]{model.getValueAt(i, 1).toString(),
            model.getValueAt(i, 2).toString(), model.getValueAt(i, 3).toString(), model.getValueAt(i, 4).toString()};
      
        model1.addRow(values);
        model3.addRow(values);
    }
    tableDateReport.setOpaque(false);
    tableDateReport.setModel(model1);
    tableDateReport.getColumnModel().getColumn(0).setMaxWidth(100);
    tableDateReport.getColumnModel().getColumn(0).setMinWidth(100);
    tableDateReport.getColumnModel().getColumn(3).setMaxWidth(70);
    model2 = (DefaultTableModel) tableDateReport1.getModel();
    tableFillterSorter = new TableFillterSorter(tableDateReport);
    tableFillterSorter.createTableSorter();
    tableFillterSorter.createTableFillter();
    cbSearch.setModel(new DefaultComboBoxModel(tableFillterSorter.getHashtable().keySet().toArray()));
    loadCboSearch();
}

@SuppressWarnings({ "unchecked", "rawtypes" })
private void printReport(boolean view_print) {
    fileNamePath = getFileNamePath();
   
    String[] keys = new String[]{"prTenSanPham", "prMaSP",
        "prLbGia", "prGia", "prDV"};
    DefaultTableModel model4 = new DefaultTableModel(keys, 0) {

      boolean[] canEdit = new boolean[]{
          false, false, false, false, false
      };

      @Override
      public boolean isCellEditable(int rowIndex, int columnIndex) {
          return canEdit[columnIndex];
      }
  };
  int size =0;
    for (int i = 0; i < model2.getRowCount(); i++) {
        int k = 0;
        try {
            k = Integer.parseInt(model2.getValueAt(i, 0).toString());
        } catch (Exception e) {
        }
        for (int j = 0; j < k; j++) {
            String strName, strGia, strLb, strDV;
            if (chbGia.isSelected()) {
                strGia = model2.getValueAt(i, 3).toString();
                strDV = model2.getValueAt(i, 4).toString();
                strLb = "Giá";
            } else {
                strGia = "";
                strDV = "";
                strLb = "";
            }
            if (chbName.isSelected()) {
                strName = model2.getValueAt(i, 2).toString();
            } else {
                strName = "";
            }
            String[] values = new String[]{strName,
                model2.getValueAt(i, 1).toString(), strLb, strGia, strDV};
            model4.addRow(values);
            size++;
            hashMap = new HashMap();
            if (keys.length == values.length) {
                for (int h = 0; h < keys.length; h++) {
                    hashMap.put(keys[h], values[h]);
                }
            }
            if(!chbAll.isSelected()){
            	 reportExport(fileNamePath, hashMap, model2, view_print, -1);
            }
           
        }
    }
    if(chbAll.isSelected()){
    	reportExport(fileNamePath, hashMap, model4, view_print, size);
   }
    

}

private String getFileNamePath() {
    String str = null;
    size = jComboBox2.getSelectedItem().toString();
    if (size.equals("104-22")) {
        File file = HKTFile.getFile("TemplatePrint", "BarCode10422.jasper");
        if (file.exists()) {
            str = file.getAbsolutePath();
            flagExtension = true;
        } else {
            str = "template/BarCode10422.jasper";
            flagExtension = false;
        }
    }
    if (size.equals("70-22")) {
        File file = HKTFile.getFile("TemplatePrint", "BarCode7022.jasper");
        if (file.exists()) {
            str = file.getAbsolutePath();
            flagExtension = true;
        } else {
            str = "template/BarCode7022.jasper";
            flagExtension = false;
        }
    }
    if (size.equals("70-22-xprinter")) {
      File file = HKTFile.getFile("TemplatePrint", "BarCodeX7022.jasper");
      if (file.exists()) {
          str = file.getAbsolutePath();
          flagExtension = true;
      } else {
          str = "template/BarCodeX7022.jasper";
          flagExtension = false;
      }
  }
    if (size.equals("106-22")) {
        File file = HKTFile.getFile("TemplatePrint", "BarCode10622.jasper");
        if (file.exists()) {
            str = file.getAbsolutePath();
            flagExtension = true;
        } else {
            str = "template/BarCode10622.jasper";
            flagExtension = false;
        }
    }
    if (size.equals("104-32")) {
        File file = HKTFile.getFile("TemplatePrint", "BarCode10432.jasper");
        if (file.exists()) {
            str = file.getAbsolutePath();
            flagExtension = true;
        } else {
            str = "template/BarCode10432.jasper";
            flagExtension = false;
        }
    }
    return str;
}

@SuppressWarnings("rawtypes")
private void reportExport(String filePathResource, HashMap hashMap, TableModel model, boolean view_print, int size) {
    printService = (PrintService) jComboBox1.getSelectedItem();
    if (!view_print) {
        ReportManager.getInstance().viewReport(filePathResource, hashMap, model, flagExtension);
    } else {
        ReportManager.getInstance().printReportBarcode(filePathResource, hashMap, model, printService, flagExtension, size);
    }
}

private void searchData() {
    tableFillterSorter.fillter(txtSearch.getText(), cbSearch.getSelectedItem().toString());
}

private void loadCboSearch() {
    for (int i = 0; i < cbSearch.getItemCount(); i++) {
        if (cbSearch.getItemAt(i) != null
                && (cbSearch.getItemAt(i).toString()).equals(strSearch)) {
            cbSearch.setSelectedIndex(i);
            break;
        }
    }
}
}

