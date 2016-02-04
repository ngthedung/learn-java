package com.hkt.client.swingexp.app.nhansu.dailywork;

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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.basic.BasicBorders.FieldBorder;

import com.hkt.client.swingexp.add.lunacalendar.LunarCalendar;
import com.hkt.client.swingexp.app.component.PanelBackground;
import com.hkt.client.swingexp.app.component.PanelTrans;
import com.hkt.client.swingexp.app.core.CustomerJComboBox;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.HRJComboBox;
import com.hkt.client.swingexp.app.nhansu.PanelToDoCalendar;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.module.hr.entity.Appointment;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.partner.customer.entity.Customer;
import com.toedter.calendar.JYearChooser;

public class CalendarAppointment extends JDialog {
	private JComboBox cbbMonth;
	private HRJComboBox cboEmployee;
	private CustomerJComboBox cbbKhach;
	private JButton jButton1;
	private JButton jButton2;
	private JButton jButton3;
	private JLabel jlbMonth;
	private JLabel backweek;
	private JLabel jlbDay;
	private JLabel jlbWeek;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JLabel jLabel6;
	private JLabel nextDay;
	private JLabel jLabel8;
	private JLabel jLabel9;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JPanel jPanel3;
	private JPanel jpndateweekmonth;
	private JScrollPane jsViewDate;
	private JYearChooser jyChoose;
	private JLabel lbSpa;
	private JPanel panelChangeDay;
	private JPanel panelChangeMonth;
	private JPanel panelChangeWeek;
	private JPanel panelBackground1;
	private Date date;
	private JPanel panel;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private List<PanelDayOfMonth> panelChild = new ArrayList<PanelDayOfMonth>();
	private List<PanelWorkOfWeek> panelChildWeek = new ArrayList<PanelWorkOfWeek>();
	private List<PanelWorkOfDay> panelChildDay = new ArrayList<PanelWorkOfDay>();
	private Calendar calendar;
	private boolean flagPanel = true;
	private int flagUI = 2;
	private Date dateMonth;
	private String url;
	private String data = "KeyApp.txt";
	private boolean flagImput = false;
	private boolean flagCbb = false;
	private boolean flagYear = false;
	private String idEmployee;
	private String idEmploy;
	private int l = 0;
	private List<Appointment> appointment;
	private Appointment appointment2;
	private Appointment.Status status;
	private JLabel lbContent;
	private Employee employee;
	private Customer customer;
	private JLabel labelIcon,labelTitle;
	private JButton         btnExit;

	private JScrollPane btnExt1, btnExt2, btnExt3;
	private JLabel jLabel30;
	private JPanel jPanelNhanVien, jPanelKhachhang;
	private JLabel jLabelNhanVien, jLabelKhachhang;
	private JPanel jPanelLoc;
	private JLabel jLabelLoc;
	private JPopupMenu popupMenu;
	private JLabel jLabel10, jLabel11, jLabel12, jLabel13;
	private JPanel panelCheckBox;
	private JCheckBox checkBoxkh,checkBoxncc, checkBoxns;
	private String checkkh, checkncc, checkns;
	public CalendarAppointment() {
		ImageIcon icon = new ImageIcon(
		HomeScreen.class.getResource("icon/icon.png"));
		Image im = icon.getImage();
		setIconImage(im);
		initComponents();
		setModal(true);
	    dispose();
	    setUndecorated(true);
	    setFullSize();
		addKeyBindings(panelBackground1);
		int h = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 30;
		int w = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();

		this.setSize(new Dimension(w, h));
		jsViewDate.setSize(new Dimension(w, h - 105));
		date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH);
		int year = c.get(Calendar.YEAR);
		cbbMonth.setSelectedIndex(month);
		jyChoose.setYear(year);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {
				loadPanelMonth();
			}
			
		});
	}

	private void initComponents() {
		jPanel1 = new javax.swing.JPanel();
		jPanel1.setOpaque(false);
		panelBackground1 = new PanelBackground();
		jPanel3 = new javax.swing.JPanel();
		jPanel3.setOpaque(false);
		jpndateweekmonth = new javax.swing.JPanel();
		jpndateweekmonth.setOpaque(false);
		jlbMonth = new javax.swing.JLabel("Tháng");
		jlbDay = new javax.swing.JLabel("Ngày");
		jlbWeek = new javax.swing.JLabel("Tuần");
		jPanel2 = new javax.swing.JPanel();
		panelChangeMonth = new javax.swing.JPanel();
		jLabel4 = new javax.swing.JLabel("Năm");
		jLabel5 = new javax.swing.JLabel("Tháng");
		jyChoose = new com.toedter.calendar.JYearChooser();
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		cbbMonth = new javax.swing.JComboBox();
		panelChangeDay = new javax.swing.JPanel();
		nextDay = new javax.swing.JLabel();
		jLabel8 = new javax.swing.JLabel();
		panelChangeWeek = new javax.swing.JPanel();
		jLabel9 = new javax.swing.JLabel();
		backweek = new javax.swing.JLabel();
		lbSpa = new javax.swing.JLabel("Spa");
		jButton3 = new javax.swing.JButton("Hiện tại");
		jLabel6 = new javax.swing.JLabel("Nhân viên");
		cboEmployee = new HRJComboBox();
		jsViewDate = new javax.swing.JScrollPane();
		cbbKhach = new CustomerJComboBox();
		jPanelKhachhang = new JPanel();
		jPanelKhachhang.setOpaque(false);
		jPanelNhanVien = new JPanel();
		jPanelNhanVien.setOpaque(false);
		jLabelKhachhang = new JLabel("Khách hàng");
		jLabelNhanVien = new JLabel("Nhân viên");
		jPanelLoc = new JPanel();
		jPanelLoc.setOpaque(false);
		jLabelLoc = new JLabel("Chọn danh sách");
		jLabel10 = new javax.swing.JLabel("Tuần trước");
		jLabel11 = new javax.swing.JLabel("Tuần sau");
		jLabel12 = new javax.swing.JLabel("Hôm trước");
		jLabel13 = new javax.swing.JLabel("Hôm sau");
		checkkh = new String();
		checkncc = new String();
		checkns = new String();
		panelCheckBox = new JPanel();
		checkBoxkh = new JCheckBox("Quản lý khách hàng", false);
		checkBoxkh.setOpaque(false);
		checkBoxncc = new JCheckBox("Quản lý nhà cung cấp",false);
		checkBoxncc.setOpaque(false);
		checkBoxns = new JCheckBox("Quản lý nhân sự",false);
		checkBoxns.setOpaque(false);
		panelCheckBox.setOpaque(false);
		panelCheckBox.setLayout(new GridLayout(3,1));
		panelCheckBox.add(checkBoxkh);
		panelCheckBox.add(checkBoxncc);
		panelCheckBox.add(checkBoxns);;
		checkBoxkh.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				checkBoxkhItemStateChanged(e);
				
			}
		});
		checkBoxncc.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				checkBoxnccItemStateChanged(e);
				
			}
		});
		checkBoxns.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				checkBoxnsItemStateChanged(e);
				
			}
		});
		  btnExit = new JButton("Thoát");
		  btnExit.addActionListener(new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		        btnExitActionPerformed(e);
		      }
		    });
		    btnExit.setName("btnExit");
		    btnExit.setFont(new Font("Tahoma", 1, 14));
		    btnExit.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/iconBack.png")));
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
		    
		    jLabel30 = new JLabel();
		    jLabel30.setText("");


		jPanel3.setOpaque(false);

		jlbMonth.setFont(new java.awt.Font("Tahoma", 1, 14));
		jlbMonth.setForeground(new java.awt.Color(51, 51, 51));
		jlbMonth.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jlbMonth.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		jlbMonth.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jlbMonthMouseClicked(evt);
			}
		});

		jlbDay.setFont(new java.awt.Font("Tahoma", 1, 14));
		jlbDay.setForeground(new java.awt.Color(51, 51, 51));
		jlbDay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jlbDay.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		jlbDay.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jlbDayMouseClicked(evt);
			}
		});

		jlbWeek.setFont(new java.awt.Font("Tahoma", 1, 14));
		jlbWeek.setForeground(new java.awt.Color(51, 51, 51));
		jlbWeek.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jlbWeek.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		jlbWeek.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jlbWeekMouseClicked(evt);
			}
		});

		javax.swing.GroupLayout jpndateweekmonthLayout = new javax.swing.GroupLayout(
				jpndateweekmonth);
		jpndateweekmonth.setLayout(jpndateweekmonthLayout);
		jpndateweekmonthLayout.setHorizontalGroup(jpndateweekmonthLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jpndateweekmonthLayout
						.createSequentialGroup()
						.addComponent(jlbDay,
								javax.swing.GroupLayout.PREFERRED_SIZE, 77,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jlbWeek,
								javax.swing.GroupLayout.PREFERRED_SIZE, 77,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jlbMonth,
								javax.swing.GroupLayout.PREFERRED_SIZE,77,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jButton3,
								javax.swing.GroupLayout.PREFERRED_SIZE, 77,
								javax.swing.GroupLayout.PREFERRED_SIZE)));
		jpndateweekmonthLayout.setVerticalGroup(jpndateweekmonthLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jlbDay, javax.swing.GroupLayout.PREFERRED_SIZE,
						30, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addComponent(jlbWeek, javax.swing.GroupLayout.PREFERRED_SIZE,
						30, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addComponent(jlbMonth, javax.swing.GroupLayout.PREFERRED_SIZE,
						30, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE,
						30, javax.swing.GroupLayout.PREFERRED_SIZE));


		panelChangeMonth.setBackground(new java.awt.Color(255, 255, 255));
		panelChangeMonth.setOpaque(false);

		jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14));

		jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14));

		jyChoose.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		jyChoose.setOpaque(false);

		jyChoose.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
			public void propertyChange(java.beans.PropertyChangeEvent evt) {
				jyChoosePropertyChange(evt);
			}
		});

		jButton1.setBorder(null);
		jButton1.setBackground(null);

		jButton1.setOpaque(false);
		jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("icon/back.png")));
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("icon/next.png")));
		jButton2.setOpaque(false);
		jButton2.setBackground(null);
		jButton2.setBorder(null);
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});

		cbbMonth.setFont(new java.awt.Font("Tahoma", 0, 14));
		cbbMonth.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
				"01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
				"11", "12" }));
		((JLabel)cbbMonth.getRenderer()).setHorizontalAlignment(SwingConstants.RIGHT);
		cbbMonth.setOpaque(false);
		cbbMonth.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				cbbMonthItemStateChanged(evt);
			}
		});

		javax.swing.GroupLayout panelChangeMonthLayout = new javax.swing.GroupLayout(
				panelChangeMonth);
		panelChangeMonth.setLayout(panelChangeMonthLayout);
		panelChangeMonthLayout
				.setHorizontalGroup(panelChangeMonthLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								panelChangeMonthLayout
										.createSequentialGroup()
										.addComponent(
												jButton1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												32,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(5, 5, 5)
										.addComponent(
												jLabel5,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												40,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(
												cbbMonth,
												55,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												jLabel4,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												30,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(6, 6, 6)
										.addComponent(
												jyChoose,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												55, Short.MAX_VALUE)
										.addGap(3, 3, 3)
										.addComponent(
												jButton2,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												32,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(20, 20, 20)));
		panelChangeMonthLayout
				.setVerticalGroup(panelChangeMonthLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								panelChangeMonthLayout
										.createSequentialGroup()
										.addGroup(
												panelChangeMonthLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																false)
														.addComponent(
																jButton1,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																32,
																Short.MAX_VALUE)
														.addComponent(
																jLabel5,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																25,
																Short.MAX_VALUE)
														.addComponent(
																cbbMonth,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																25,
																Short.MAX_VALUE)
														.addComponent(
																jLabel4,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																25,
																Short.MAX_VALUE)
														.addComponent(
																jButton2,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																32,
																Short.MAX_VALUE)
														.addComponent(
																jyChoose,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		panelChangeDay.setBackground(new java.awt.Color(255, 255, 255));
		panelChangeDay.setOpaque(false);

		nextDay.setFont(new java.awt.Font("Tahoma", 0, 14));
		 nextDay.setIcon(new javax.swing.ImageIcon(getClass().getResource(
		 "icon/nextWeek.png"))); // NOI18N
		nextDay.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		nextDay.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
		nextDay.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				nextDayMouseClicked(evt);
			}
		});

		jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14));
		 jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource(
		 "icon/backWeek.png"))); // NOI18N
		jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jLabel8MouseClicked(evt);
			}
		});
		jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14));
		jLabel12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14));
		jLabel13.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		javax.swing.GroupLayout panelChangeDayLayout = new javax.swing.GroupLayout(
				panelChangeDay);
		panelChangeDay.setLayout(panelChangeDayLayout);
		panelChangeDayLayout.setHorizontalGroup(panelChangeDayLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
				.addGroup(
						panelChangeDayLayout
								.createSequentialGroup()
								.addComponent(jLabel8,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										32,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel12,javax.swing.GroupLayout.PREFERRED_SIZE,
										100,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel13,javax.swing.GroupLayout.PREFERRED_SIZE,
										100,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(nextDay,javax.swing.GroupLayout.PREFERRED_SIZE,32,javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18)
								));
		panelChangeDayLayout
				.setVerticalGroup(panelChangeDayLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.CENTER)
						.addGroup(
								panelChangeDayLayout
										.createSequentialGroup()
										.addGroup(
												panelChangeDayLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jLabel8,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																32,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel12,javax.swing.GroupLayout.PREFERRED_SIZE,
										32,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel13,javax.swing.GroupLayout.PREFERRED_SIZE,
										32,
										javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																nextDay,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																32,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		panelChangeWeek.setBackground(new java.awt.Color(255, 255, 255));
		panelChangeWeek.setOpaque(false);

		jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14));
		 jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource(
		 "icon/nextWeek.png"))); // NOI18N
		jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
		jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jLabel9MouseClicked(evt);
			}
		});

		backweek.setFont(new java.awt.Font("Tahoma", 0, 14));
		 backweek.setIcon(new javax.swing.ImageIcon(getClass().getResource(
		 "icon/backWeek.png"))); // NOI18N
		backweek.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		backweek.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				backweekMouseClicked(evt);
			}
		});
		jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14));
		jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14));
		jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		javax.swing.GroupLayout panelChangeWeekLayout = new javax.swing.GroupLayout(
				panelChangeWeek);
		panelChangeWeek.setLayout(panelChangeWeekLayout);
		panelChangeWeekLayout.setHorizontalGroup(panelChangeWeekLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						panelChangeWeekLayout
								.createSequentialGroup()
								.addComponent(backweek,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										32,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel10,javax.swing.GroupLayout.PREFERRED_SIZE,
										100,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel11,javax.swing.GroupLayout.PREFERRED_SIZE,
										100,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel9)
								));
		panelChangeWeekLayout
				.setVerticalGroup(panelChangeWeekLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								panelChangeWeekLayout
										.createSequentialGroup()
										.addGroup(
												panelChangeWeekLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																backweek,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																32,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jLabel10,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																32,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jLabel11,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																32,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jLabel9,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																32,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		jButton3.setFont(new java.awt.Font("Tahoma", 1, 14));
		jButton3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		jButton3.setContentAreaFilled(false);
		jButton3.setMargin(new java.awt.Insets(2, 0, 2, 0));
		jButton3.setPreferredSize(new java.awt.Dimension(57, 25));
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton3ActionPerformed(evt);
			}
		});
		jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14));
		cboEmployee.setFont(new java.awt.Font("Tahoma", 0, 14));
		try {
			
			 if (appointment2.getEmployeeLoginId() != null) {
				 cboEmployee.setSelectedEmployee(appointment2.getEmployeeLoginId());
			    }	
		} catch (Exception e) {
		}
		cboEmployee.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				cboEmployeeItemStateChanged(evt);
			}
		});
		cbbKhach.setFont(new java.awt.Font("Tahoma", 0, 14));
		try {	
				 cbbKhach.setSelectItemByID(appointment2.getPartnerLoginId());		
			   
		} catch (Exception e) {
		}
		cbbKhach.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(java.awt.event.ItemEvent e) {
				cbbKhachItemStateChanged(e);
				
			}
		});
		
		jPanel2.setBackground(new java.awt.Color(255, 255, 255));
		jPanel2.setOpaque(false);
		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(
				jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout
				.setHorizontalGroup(jPanel2Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel2Layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												panelChangeMonth,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												panelChangeWeek,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												panelChangeDay,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												));
		jPanel2Layout
				.setVerticalGroup(jPanel2Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(panelChangeMonth,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(panelChangeWeek,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(panelChangeDay,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE));
		jLabelNhanVien.setFont(new java.awt.Font("Tahoma", 1, 14));
		jLabelNhanVien.setForeground(new java.awt.Color(51, 51, 51));
		javax.swing.GroupLayout jPanelNhanVienLayout = new javax.swing.GroupLayout(
				jPanelNhanVien);
		jPanelNhanVien.setLayout(jPanelNhanVienLayout);
		jPanelNhanVienLayout.setHorizontalGroup(jPanelNhanVienLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.CENTER).addGroup(
						jPanelNhanVienLayout
						.createSequentialGroup()
						.addComponent(jLabelNhanVien,
								javax.swing.GroupLayout.PREFERRED_SIZE,95,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(cboEmployee,
								javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE,
								Short.MAX_VALUE)
								)
								);
		jPanelNhanVienLayout.setVerticalGroup(jPanelNhanVienLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
				.addComponent(jLabelNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE,
						30, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addComponent(cboEmployee, javax.swing.GroupLayout.PREFERRED_SIZE,
						30, javax.swing.GroupLayout.PREFERRED_SIZE)
						);
		jLabelKhachhang.setFont(new java.awt.Font("Tahoma", 1, 14));
		jLabelKhachhang.setForeground(new java.awt.Color(51, 51, 51));
		javax.swing.GroupLayout jPanelKhachhangLayout = new javax.swing.GroupLayout(
				jPanelKhachhang);
		jPanelKhachhang.setLayout(jPanelKhachhangLayout);
		jPanelKhachhangLayout.setHorizontalGroup(jPanelKhachhangLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.CENTER).addGroup(
						jPanelKhachhangLayout
						.createSequentialGroup()
						.addComponent(jLabelKhachhang,
								javax.swing.GroupLayout.PREFERRED_SIZE,95,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(cbbKhach,
								javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE,
								Short.MAX_VALUE)
								)
								);
		jPanelKhachhangLayout.setVerticalGroup(jPanelKhachhangLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jLabelKhachhang, javax.swing.GroupLayout.PREFERRED_SIZE,
						30, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addComponent(cbbKhach, javax.swing.GroupLayout.PREFERRED_SIZE,
						30, javax.swing.GroupLayout.PREFERRED_SIZE)
						);
		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(
				jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
						.addGroup(jPanel2Layout.createSequentialGroup()
						.addGroup(
								jPanel3Layout
										.createParallelGroup()
										.addComponent(
												jPanel2,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE)
										.addComponent(
												jpndateweekmonth,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE)
										)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addGroup(jPanel3Layout.createParallelGroup()
									.addComponent(panelCheckBox,javax.swing.GroupLayout.PREFERRED_SIZE,
											100,
											Short.MAX_VALUE)
										)	
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(jPanel3Layout.createParallelGroup()
										.addComponent(
												jPanelNhanVien,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												45,
												javax.swing.GroupLayout.DEFAULT_SIZE)
										.addComponent(
												jPanelKhachhang,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												45,
												javax.swing.GroupLayout.DEFAULT_SIZE)
												)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										));
		jPanel3Layout
				.setVerticalGroup(jPanel3Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.CENTER)
						.addGroup(jPanel3Layout.createSequentialGroup()
										.addComponent(
												jPanel2,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(
												jpndateweekmonth,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)

								)
								.addGroup(jPanel3Layout.createSequentialGroup()
										.addComponent(panelCheckBox,javax.swing.GroupLayout.PREFERRED_SIZE,
									javax.swing.GroupLayout.DEFAULT_SIZE,
									javax.swing.GroupLayout.PREFERRED_SIZE)
										)
										.addGroup(jPanel3Layout.createSequentialGroup()
										.addComponent(
												jPanelNhanVien,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												35,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(
												jPanelKhachhang,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												35,
												javax.swing.GroupLayout.PREFERRED_SIZE)		
										)
										);

		jsViewDate.setBackground(new java.awt.Color(255, 255, 255));
	
		GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
	    jPanel1.setLayout(jPanel1Layout);
	    jPanel1Layout.setHorizontalGroup(jPanel1Layout
	        .createParallelGroup(GroupLayout.Alignment.LEADING)
	        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
			.addComponent(jsViewDate, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE));
	    jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
	        jPanel1Layout
	            .createSequentialGroup()
	            
	            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
	            .addComponent(
						jPanel3,
						javax.swing.GroupLayout.PREFERRED_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addPreferredGap(
						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(jsViewDate, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)));
		
		 JLabel lbHKT = new JLabel();
		    lbHKT.setFont(new java.awt.Font("Tahoma", 1, 16));
		    lbHKT.setForeground(new java.awt.Color(126, 0, 0));
		    lbHKT.setHorizontalAlignment(SwingConstants.RIGHT);
		    lbHKT.setText("HKT Sofware   ");
		    lbHKT.setPreferredSize(new Dimension(WIDTH, 50));
		    
		    labelIcon = new javax.swing.JLabel();
		    labelIcon.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/home/datban1.png")));
		    labelTitle = new javax.swing.JLabel("Dialog Resto");
		    labelTitle.setFont(new java.awt.Font("Tahoma", 1, 14));
		    labelTitle.setForeground(new java.awt.Color(126, 0, 0));
		    
		 // Giao diện chính panel2 add giao diện chính jPanel1 và tạo khoảng cách các bên
		    PanelTrans panel2 = new PanelTrans();
		    panel2.setOpaque(false);
		    panel2.setLayout(new BorderLayout());
		    panel2.add(jPanel1,BorderLayout.CENTER);
		    panel2.add(createLable1(), BorderLayout.SOUTH);
		    panel2.add(createLable(), BorderLayout.EAST);
		    panel2.add(createLable(), BorderLayout.WEST);
		    
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
		                .addComponent(labelTitle, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
		               .addGap(45, 45, 45))
		        .addGroup(
		            panelBackgroundResto1Layout.createSequentialGroup().addGap(25, 25, 25)
		                .addComponent(panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE).addGap(25, 25, 25))
		        .addGroup(
		            panelBackgroundResto1Layout.createSequentialGroup()
		                .addGap(4, 4, 4).addComponent(jLabel30, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE).addGap(4, 4, 4)
		                .addComponent(btnExt3, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE).addGap(4, 4, 4)
		                .addComponent(btnExt2, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE).addGap(4, 4, 4)
		                .addComponent(btnExt1, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE).addGap(4, 4, 4)
		                .addComponent(btnExit).addGap(22, 22, 22)));
		    panelBackgroundResto1Layout.setVerticalGroup(panelBackgroundResto1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
		        panelBackgroundResto1Layout
		            .createSequentialGroup().addGap(6, 6, 6)
		            .addGroup(
		                panelBackgroundResto1Layout
		                    .createParallelGroup(GroupLayout.Alignment.LEADING)
		                    .addComponent(labelIcon, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
		                    .addGroup(
		                        panelBackgroundResto1Layout.createSequentialGroup().addGap(11, 11, 11)
		                            .addComponent(labelTitle, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
		                            ))
		            .addGap(6, 6, 6)
		            .addComponent(panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
		            .addGap(11, 11, 11)
		            .addGroup(
		                panelBackgroundResto1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		                    .addComponent(jLabel30, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
		                    .addComponent(btnExt3, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
		                    .addComponent(btnExt2, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
		                    .addComponent(btnExt1, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
		                    .addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)).addGap(11, 11, 11)));

		// Panel thứ nhất add panel trong suốt thứ 2 + lable HKT Software
	    panelBackground1.setLayout(new BorderLayout());
	    panelBackground1.add(panel,BorderLayout.CENTER);
	    panelBackground1.add(lbHKT, BorderLayout.NORTH);
	    panelBackground1.add(createLable(), BorderLayout.SOUTH);
	    panelBackground1.add(createLable(), BorderLayout.EAST);
	    panelBackground1.add(createLable(), BorderLayout.WEST);
		
		// Giao diện dialog add Panel thứ nhất
	    GroupLayout layout = new GroupLayout(getContentPane());
	    getContentPane().setLayout(layout);
	    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(panelBackground1,
	        GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
	    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(panelBackground1,
	        GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
	    
		pack();
	}


protected void checkBoxkhItemStateChanged(ItemEvent e) {
	int state = e.getStateChange();
	if(state == ItemEvent.SELECTED)
	{
		checkkh = "khachhang";
	}
	else
	{
		checkkh = "";
	}
	if (flagUI == 0) {
		loadPanelWeek();
	} else if (flagUI == 1) {
		loadPanelDay();
	} else if (flagUI == 2) {
		loadPanelMonth();
	}
	}

protected void checkBoxnsItemStateChanged(ItemEvent e) {
	checkns = (checkBoxns.isSelected()) ? "nhansu" : "";
	if (flagUI == 0) {
		loadPanelWeek();
	} else if (flagUI == 1) {
		loadPanelDay();
	} else if (flagUI == 2) {
		loadPanelMonth();
	}
	

	}

protected void checkBoxnccItemStateChanged(ItemEvent e) {
	checkncc = (checkBoxncc.isSelected()) ? "nhacungcap" : "";
	if (flagUI == 0) {
		loadPanelWeek();
	} else if (flagUI == 1) {
		loadPanelDay();
	} else if (flagUI == 2) {
		loadPanelMonth();
	}
		
	}

	protected void cbbKhachItemStateChanged(ItemEvent e) {
		customer = cbbKhach.getSelectedCustomer();
		if (flagUI == 0) {
			loadPanelWeek();
		} else if (flagUI == 1) {
			loadPanelDay();
		} else if (flagUI == 2) {
			loadPanelMonth();
		}
	}

	private void btnExitActionPerformed(ActionEvent e) {
		    this.dispose();
		  }

	private JLabel createLable(){
	    JLabel label = new JLabel();
	    label.setSize(20, 20);
	    label.setPreferredSize(label.getSize());
	    return label;
	  }
	  private JLabel createLable1(){
	    JLabel label = new JLabel();
	    label.setSize(11, 11);
	    label.setPreferredSize(label.getSize());
	    return label;
	  }
	  
	  
	  @Override
	  public void setTitle(String title) {
	    labelTitle.setText(title);
	    super.setTitle(title);
	  }

	private void jlbMonthMouseClicked(java.awt.event.MouseEvent evt) {
		loadPanelMonth();
	}

	private void jlbDayMouseClicked(java.awt.event.MouseEvent evt) {
		loadPanelDay();
	}

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		if (cbbMonth.getSelectedIndex() < cbbMonth.getItemCount() - 1) {
			cbbMonth.setSelectedIndex(cbbMonth.getSelectedIndex() + 1);
			c.add(Calendar.MONTH, 1);
		} else {
			cbbMonth.setSelectedIndex(0);
			c.add(Calendar.MONTH, 1);
			jyChoose.setYear(c.get(Calendar.YEAR));
		}
		date = c.getTime();
		if (flagUI == 0) {
			loadPanelWeek();
		} else if (flagUI == 1) {
			loadPanelDay();
		} else if (flagUI == 2) {
			loadPanelMonth();
		}
	}

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		if (cbbMonth.getSelectedIndex() > 0) {
			cbbMonth.setSelectedIndex(cbbMonth.getSelectedIndex() - 1);
			c.add(Calendar.MONTH, -1);
		} else {
			cbbMonth.setSelectedIndex(cbbMonth.getItemCount() - 1);
			c.add(Calendar.MONTH, -1);
			jyChoose.setYear(c.get(Calendar.YEAR));
		}
		date = c.getTime();
		if (flagUI == 0) {
			loadPanelWeek();
		} else if (flagUI == 1) {
			loadPanelDay();
		} else if (flagUI == 2) {
			loadPanelMonth();
		}
	}

	private void cbbMonthItemStateChanged(java.awt.event.ItemEvent evt) {
		try {
			int month = Integer.parseInt(cbbMonth.getSelectedItem().toString());
			int year = jyChoose.getYear();
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.YEAR, year);
			c.set(Calendar.MONTH, month -1);
			date = c.getTime();
			loadPanelMonth();
		} catch (Exception e) {
		}
	}
	private void jyChoosePropertyChange(java.beans.PropertyChangeEvent evt) {
		try {
			int month = Integer.parseInt(cbbMonth.getSelectedItem().toString());
			int year = jyChoose.getYear();
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.YEAR, year);
			c.set(Calendar.MONTH, month -1);
			date = c.getTime();
				loadPanelMonth();
		} catch (Exception e) {
		}
	}

	private void backweekMouseClicked(java.awt.event.MouseEvent evt) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, -7);
		date = c.getTime();
		if (flagUI == 0) {
			loadPanelWeek();
		} else if (flagUI == 1) {
			loadPanelDay();
		} else if (flagUI == 2) {
			loadPanelMonth();
		}
	}

	private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, 7);
		date = c.getTime();
		if (flagUI == 0) {
			loadPanelWeek();
		} else if (flagUI == 1) {
			loadPanelDay();
		} else if (flagUI == 2) {
			loadPanelMonth();
		}
	}

	private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, -1);
		date = c.getTime();
		if (flagUI == 0) {
			loadPanelWeek();
		} else if (flagUI == 1) {
			loadPanelDay();
		} else if (flagUI == 2) {
			loadPanelMonth();
		}
	}

	private void nextDayMouseClicked(java.awt.event.MouseEvent evt) {

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, 1);
		date = c.getTime();
		if (flagUI == 0) {
			loadPanelWeek();
		} else if (flagUI == 1) {
			loadPanelDay();
		} else if (flagUI == 2) {
			loadPanelMonth();
		}
	}


	private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH);
		int year = c.get(Calendar.YEAR);
		cbbMonth.setSelectedIndex(month);
		jyChoose.setYear(year);
		date = c.getTime();
		if (flagUI == 0) {
			loadPanelWeek();
		} else if (flagUI == 1) {
			loadPanelDay();
		} else if (flagUI == 2) {
			loadPanelMonth();
		}
	}

	private void cboEmployeeItemStateChanged(java.awt.event.ItemEvent evt) 
	{
			employee = cboEmployee.getSelectedEmployee();
			if (flagUI == 0) {
				loadPanelWeek();
			} else if (flagUI == 1) {
				loadPanelDay();
			} else if (flagUI == 2) {
				loadPanelMonth();
			}
	}

	private void jlbWeekMouseClicked(java.awt.event.MouseEvent evt) {
		loadPanelWeek();
	}

	public void loadPanelDay() {
		flagUI = 1;
		panelChangeDay.setVisible(true);
		panelChangeMonth.setVisible(false);
		panelChangeWeek.setVisible(false);

		panel = new JPanel();
		panel.setLayout(new FlowLayout(3));
		panel.setOpaque(false);
		panel.setSize(jsViewDate.getWidth() - 10, jsViewDate.getHeight() - 10);
		jsViewDate.getViewport().setOpaque(false);
		jsViewDate.setOpaque(false);
		for (int i = 0; i < panelChildDay.size(); i++) {
			int w = panel.getWidth() / 7 - 5;
			int h = panel.getHeight() / 6 - 8;
			PanelWorkOfDay panelWorkOfDay = panelChildDay.get(i);
			panelWorkOfDay.setSize(w, h);
			panelWorkOfDay.setMaximumSize(new Dimension(w, h));
			panelWorkOfDay.setPreferredSize(new Dimension(w, h));
			panelWorkOfDay.repaint();
			panelWorkOfDay.updateUI();
			panelWorkOfDay.setBorder(new FieldBorder(Color.lightGray,
					Color.lightGray, Color.lightGray, Color.lightGray));
			panel.add(panelWorkOfDay);
			panelWorkOfDay.setVisible(true);

		}
		panel.setPreferredSize(new Dimension(jsViewDate.getWidth() - 20,
				jsViewDate.getHeight() - 20));
		jsViewDate.setViewportView(panel);
		loadCalendarDay();

	}

	protected void loadCalendarDay() {
		resetCalentdarDay();
		JPanel panelParent = new JPanel();
		panelParent.setSize(jsViewDate.getWidth() - 10,
				jsViewDate.getHeight() - 15);
		panelParent.setLayout(new FlowLayout(3));
		final PanelWorkOfDay panelWorkOfDay = new PanelWorkOfDay();
		JLabel lbDate = new JLabel();
		lbDate.setText(df.format(date));
		lbDate.setFont(new Font("Tahoma", 1, 14));
		panelParent.add(lbDate);
		// Button Thêm công việc
		JButton button = new JButton();
		button.setText("Thêm công việc");
		button.setFont(new Font("Tahoma", 1, 14));

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					PanelToDoCalendar panelToDoCalendar = new PanelToDoCalendar(
							true);
					panelToDoCalendar.setDate(date);
					DialogMain main = new DialogMain(panelToDoCalendar);
					main.setTitle("Công việc hàng ngày");
					main.setVisible(true);
					panelWorkOfDay.load();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		panelParent.add(button);
		panelParent.setBackground(Color.WHITE);
		for (int i = 0; i < 24; i++) {
			panelWorkOfDay.setBackground(Color.white);
			panelWorkOfDay.setBorder(new FieldBorder(Color.lightGray,
					Color.lightGray, Color.lightGray, Color.lightGray));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			panelWorkOfDay.loadAppointment(this, calendar, employee, customer, checkkh, checkncc, checkns);
			panelWorkOfDay.setOpaque(false);
			int w = panelParent.getWidth() - 10;
			int h = panelParent.getHeight() - 30;
			panelWorkOfDay.getJspWorkLeft().setPreferredSize(new Dimension((w-4) /2 , h));
			panelWorkOfDay.getJspWorkLeft().setMaximumSize(new Dimension((w-4) /2, h));
			panelWorkOfDay.getJspWorkRight().setPreferredSize(new Dimension((w-4) /2 , h));
			panelWorkOfDay.getJspWorkRight().setMaximumSize(new Dimension((w-4) /2, h));
			
			panelWorkOfDay.setPreferredSize(new Dimension(w, h));
			panelWorkOfDay.setMaximumSize(new Dimension(w, h));
			panelWorkOfDay.setSize(w, h);
			panelParent.add(panelWorkOfDay);
			panelChildDay.add(panelWorkOfDay);
		}
		panelParent.setPreferredSize(new Dimension(jsViewDate.getWidth() - 20,
				jsViewDate.getHeight() - 30));
		panelParent.setOpaque(false);
		jsViewDate.getViewport().setOpaque(false);
		jsViewDate.setOpaque(false);
		jsViewDate.setViewportView(panelParent);
		jsViewDate.updateUI();
		jsViewDate.repaint();
		this.repaint();
	}
	
	public void setDate(Date date){
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	private void resetCalentdarDay() {
		panelChildDay = new ArrayList<PanelWorkOfDay>();
	}

	public void loadPanelWeek() {
		flagUI = 0;
		panelChangeDay.setVisible(false);
		panelChangeMonth.setVisible(false);
		panelChangeWeek.setVisible(true);
		panel = new JPanel();
		panel.setLayout(new FlowLayout(3));
		panel.setOpaque(false);
		panel.setSize(jsViewDate.getWidth() - 10, jsViewDate.getHeight() - 10);
		jsViewDate.getViewport().setOpaque(false);
		jsViewDate.setOpaque(false);
		for (int i = 0; i < panelChildWeek.size(); i++) {
			int w = panel.getWidth() / 7 - 5;
			int h = panel.getHeight() / 6 - 8;
			PanelWorkOfWeek panelWorkOfWeek = panelChildWeek.get(i);
			panelWorkOfWeek.setSize(w, h);
			panelWorkOfWeek.setMaximumSize(new Dimension(w, h));
			panelWorkOfWeek.setPreferredSize(new Dimension(w, h));
			panelWorkOfWeek.repaint();
			panelWorkOfWeek.updateUI();
			panelWorkOfWeek.setBorder(new FieldBorder(Color.lightGray,
					Color.lightGray, Color.lightGray, Color.lightGray));
			panel.add(panelWorkOfWeek);
			panelWorkOfWeek.setVisible(true);
		}
		panel.setPreferredSize(new Dimension(jsViewDate.getWidth() - 20,
				jsViewDate.getHeight() - 20));
		jsViewDate.setViewportView(panel);
		loadCalendarWeek();
	}

	public void loadPanelMonth() {
		flagUI = 2;
		panelChangeDay.setVisible(false);
		panelChangeMonth.setVisible(true);
		panelChangeWeek.setVisible(false);
		loadCalendar();
		panel = new JPanel();
		panel.setLayout(new FlowLayout(3));
		panel.setOpaque(false);
		panel.setSize(jsViewDate.getWidth(), jsViewDate.getHeight());
		jsViewDate.getViewport().setOpaque(false);
		jsViewDate.setOpaque(false);
		for (int i = 0; i < panelChild.size(); i++) {
			PanelDayOfMonth panelDayOfMonth = panelChild.get(i);
			int w = panel.getWidth() / 7 - 6;
			int h = panel.getHeight() / 6 - 6;
			panelDayOfMonth.setSize(w, h);
			panelDayOfMonth.setMaximumSize(new Dimension(w, h));
			panelDayOfMonth.setPreferredSize(new Dimension(w, h));
			panelDayOfMonth.getPnWork().setSize(w - 45, h - 10);
			panelDayOfMonth.getPnWork()
					.setMaximumSize(new Dimension(w - 45, h));
			panelDayOfMonth.getPnWork().setPreferredSize(
					new Dimension(w - 45, h));
			if (h < 70) {
				panelDayOfMonth.getLbPositive().setFont(
						new Font("Tahoma", 0, 12));
				panelDayOfMonth.getLbPositive().setPreferredSize(
						new Dimension(40, 10));
				panelDayOfMonth.getLbAdd().setPreferredSize(
						new Dimension(40, 20));
				panelDayOfMonth.getLbNegative().setFont(
						new Font("Tahoma", 0, 12));
				panelDayOfMonth.getLbNegative().setPreferredSize(
						new Dimension(40, 10));
			}
			panelDayOfMonth.repaint();
			panelDayOfMonth.updateUI();
			panelDayOfMonth.setBorder(new FieldBorder(Color.lightGray,
					Color.lightGray, Color.lightGray, Color.lightGray));
			panel.add(panelDayOfMonth);
			panelDayOfMonth.setVisible(true);
			// doi mau cho ngay hien tai
			Date dateChild = panelDayOfMonth.getDate();
			if (dateChild != null) {
				Calendar cChild = Calendar.getInstance();
				cChild.setTime(dateChild);
				int dayChild = cChild.get(Calendar.DAY_OF_MONTH);
				int monthChild = cChild.get(Calendar.MONTH);
				int yearChild = cChild.get(Calendar.YEAR);
				Calendar cNow = Calendar.getInstance();
				cNow.setTime(new Date());
				int dayNow = cNow.get(cNow.DAY_OF_MONTH);
				int monthNow = cNow.get(cNow.MONTH);
				int yearNow = cNow.get(cNow.YEAR);
				if (dayChild == dayNow && monthChild == monthNow
						&& yearChild == yearNow) {
					panelDayOfMonth.setOpaque(true);
					panelDayOfMonth.setBackground(Color.WHITE);
					panelDayOfMonth.setBorder(new EtchedBorder(4, new Color(
							204, 0, 204), new Color(204, 0, 204)));
				}
			}

		}

		// continues
		panel.setBackground(Color.WHITE);
		panel.setPreferredSize(new Dimension(jsViewDate.getWidth() - 20,
				jsViewDate.getHeight() - 20));
		jsViewDate.setViewportView(panel);
	}

	protected void loadCalendarWeek() {
		resetCalentdarWeek();

		JPanel panelParent = new JPanel();
		panelParent.setLayout(new FlowLayout(3));
		panelParent.setSize(jsViewDate.getWidth() - 10,
				jsViewDate.getHeight() - 15);
		List<PanelWorkOfWeek> listPanel = new ArrayList<PanelWorkOfWeek>();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		for (int i = 0; i < 7; i++) {
			PanelWorkOfWeek panelWorkOfWeek = new PanelWorkOfWeek();
			if (i == 0) {
				panelWorkOfWeek.getLbDate().setText("CN");
			} else if (i == 1) {
				panelWorkOfWeek.getLbDate().setText("Thứ 2");
			} else if (i == 2) {
				panelWorkOfWeek.getLbDate().setText("Thứ 3");
			} else if (i == 3) {
				panelWorkOfWeek.getLbDate().setText("Thứ 4");
			} else if (i == 4) {
				panelWorkOfWeek.getLbDate().setText("Thứ 5");
			} else if (i == 5) {
				panelWorkOfWeek.getLbDate().setText("Thứ 6");
			} else if (i == 6) {
				panelWorkOfWeek.getLbDate().setText("Thứ 7");
			}
			panelWorkOfWeek.loadAppointment(this, c, i, employee, customer, checkkh, checkncc, checkns);
			int w = panelParent.getWidth() / 7 - 7;
			int h = panelParent.getHeight();
			panelWorkOfWeek.setPreferredSize(new Dimension(w, h));
			panelWorkOfWeek.setMaximumSize(new Dimension(w, h));
			panelWorkOfWeek.setSize(w, h);
			panelWorkOfWeek.getPnWork().setPreferredSize(
					new Dimension(w - 15, h - 70));
			panelWorkOfWeek.getPnWork().setMaximumSize(
					new Dimension(w - 15, h - 70));
			panelWorkOfWeek.getPnWork().setSize(w - 15, h - 70);

			panelWorkOfWeek.getLbDate().setPreferredSize(
					new Dimension(w - 10, 50));
			panelWorkOfWeek.getLbDate().setMaximumSize(
					new Dimension(w - 10, 50));
			panelWorkOfWeek.getLbDate().setSize(w - 10, h - 60);
			listPanel.add(panelWorkOfWeek);
			panelChildWeek.add(panelWorkOfWeek);
		}
		for (int i = 0; i < listPanel.size(); i++) {
			PanelWorkOfWeek panelWorkOfWeek = listPanel.get(i);
			panelParent.add(panelWorkOfWeek);
			panelWorkOfWeek.setBackground(Color.white);
			panelWorkOfWeek.setBorder(new FieldBorder(Color.lightGray,
					Color.lightGray, Color.lightGray, Color.lightGray));
			panelWorkOfWeek.setVisible(true);
			panelParent.repaint();
		}

		LunarCalendar lunarCalendar = new LunarCalendar();
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		listPanel
				.get(dayOfWeek)
				.getLbDate()
				.setText(
						"<html><b>"
								+ listPanel.get(dayOfWeek).getLbDate()
										.getText() + " - " + df.format(date)
								+ "</b><br><center>("
								+ lunarCalendar.getLunarDate(date)
								+ ")</center></br></html>");
		for (int i = dayOfWeek; i >= 0; i--) {
			listPanel
					.get(i)
					.getLbDate()
					.setText(
							"<html><b>"
									+ listPanel.get(i).getLbDate().getText()
									+ " - " + df.format(c.getTime())
									+ "</b><br><center>("
									+ lunarCalendar.getLunarDate(c.getTime())
									+ ")</center></br></html>");
			c.add(Calendar.DAY_OF_WEEK, -1);
		}
		c.setTime(date);
		for (int i = dayOfWeek; i < listPanel.size(); i++) {
			if (i > dayOfWeek) {
				c.add(Calendar.DAY_OF_WEEK, 1);
				listPanel
						.get(i)
						.getLbDate()
						.setText(
								"<html><b>"
										+ listPanel.get(i).getLbDate()
												.getText()
										+ " - "
										+ df.format(c.getTime())
										+ "</b><br><center>("
										+ lunarCalendar.getLunarDate(c
												.getTime())
										+ ")</center></br></html>");
			}
		}

		panelParent.setOpaque(false);
		jsViewDate.getViewport().setOpaque(false);
		jsViewDate.setOpaque(false);
		jsViewDate.setViewportView(panelParent);
		jsViewDate.updateUI();
		jsViewDate.repaint();
		this.repaint();

	}

	protected void resetCalentdarWeek() {
		panelChildWeek = new ArrayList<PanelWorkOfWeek>();
	}

	protected void loadCalendar() {
		resetCalendar();
		for (int i = 0; i < 42; i++) {
			PanelDayOfMonth dayOfMonth = new PanelDayOfMonth();
			panelChild.add(dayOfMonth);
		}
		setColorDay();
		LunarCalendar lunarCalendar = new LunarCalendar();
		int dd = 1;
		int mm = Integer.parseInt(cbbMonth.getSelectedItem().toString());
		int yyyy = jyChoose.getYear();
		setupStyle(dd, mm, yyyy);
		int i;
		int m = calendar.get(Calendar.MONTH);
		int y = calendar.get(Calendar.YEAR);
		calendar.set(y, m, 1);
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		final int n = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		int t = day - 2;
		if (t < 0) {
			t = 6;
		}
		int f = t - 1;
		for (i = 0; i <= n - 1; i++) {
			PanelDayOfMonth dayOfMonth = panelChild.get(t);
			dayOfMonth.loadAppointment(this, calendar, employee, customer, checkkh, checkncc, checkns);
			t++;
			calendar.add(Calendar.DATE, 1);
		}
		// them ngay vao cac o trong phia truoc

		if (f >= 0) {
			calendar.add(Calendar.MONTH, -1);
			int prewMonth = calendar.get(Calendar.MONTH);
			Calendar calendarDay = calendar;
			calendarDay.add(Calendar.MONTH, -1);
			int maxday = calendarDay.getActualMaximum(Calendar.DAY_OF_MONTH);
			calendar.set(y, prewMonth, maxday);
			if (prewMonth == 0) {
				prewMonth = 12;
			}
			for (int k = f; k >= 0; k--) {
				String middle = String.valueOf(maxday) + "/"
						+ String.valueOf(prewMonth);
				String lunar = lunarCalendar.getLunarDate(calendar.getTime())
						.toString();
				String[] lunarDD = lunar.split("/");
				String lunarDay = "";
				if (lunarDD[0].equals("1")) {
					lunarDay = "" + lunarDD[0] + "/" + lunarDD[1];
				} else {
					lunarDay = "" + lunarDD[0];
				}
				PanelDayOfMonth dayOfMonth = panelChild.get(k);
				dayOfMonth.getLbAdd().setVisible(false);
				dayOfMonth.getLbPositive().setForeground(Color.lightGray);
				dayOfMonth.getLbNegative().setForeground(Color.lightGray);
				dayOfMonth.getLbPositive().setText(String.valueOf(middle));
				dayOfMonth.getLbNegative().setText(lunarDay);
				maxday--;
			}
		}
		// set text cho cac panel trong phia sau
		int index = 0;
		for (int j = 0; j < panelChild.size(); j++) {
			PanelDayOfMonth panelDayOfMonth = panelChild.get(j);
			index++;
			if (panelDayOfMonth.getLbNegative().getText().trim().length() == 0) {
				break;
			}
		}
		int day_of_month = 1;
		for (int k = index - 1; k < panelChild.size(); k++) {
			calendar.set(y, m + 1, day_of_month);
			String middle = String.valueOf(day_of_month) + "/"
					+ String.valueOf(calendar.get(Calendar.MONTH) + 1);
			String lunar = lunarCalendar.getLunarDate(calendar.getTime())
					.toString();
			String[] lunarDD = lunar.split("/");
			String lunarDay = "";
			if (lunarDD[0].equals("1")) {
				lunarDay = "" + lunarDD[0] + "/" + lunarDD[1];
			} else {
				lunarDay = "" + lunarDD[0];
			}
			PanelDayOfMonth dayOfMonth = panelChild.get(k);
			dayOfMonth.getLbPositive().setForeground(Color.lightGray);
			dayOfMonth.getLbNegative().setForeground(Color.lightGray);
			dayOfMonth.getLbPositive().setText(String.valueOf(middle));
			dayOfMonth.getLbNegative().setText(lunarDay);
			dayOfMonth.getLbAdd().setVisible(false);
			day_of_month++;
		}

	}

	protected void resetCalendar() {
		int n = panelChild.size();
		int i;
		for (i = 0; i < n; i++) {
			PanelDayOfMonth dayOfMonth = panelChild.get(i);
			dayOfMonth.getLbNegative().setText("");
			dayOfMonth.getLbPositive().setText("");
		}
		panelChild = new ArrayList<PanelDayOfMonth>();
	}

	protected void setupStyle(int dd, int mm, int yyyy) {

		String str = "";
		str = String.valueOf(dd) + "/" + String.valueOf(mm) + "/"
				+ String.valueOf(yyyy);
		calendar = Calendar.getInstance();
		try {
			dateMonth = (Date) df.parse(str);
		} catch (ParseException ex) {
		}

		calendar.setTime(dateMonth);
	}

	public int getFlagUI() {
		return flagUI;
	}

	public void setFlagUI(int flagUI) {
		this.flagUI = flagUI;
	}

	protected void setColorDay() {
		int i, j;
		for (i = 5, j = 6; (i < panelChild.size()) && (j < panelChild.size()); i += 7, j += 7) {
			panelChild.get(j).getLbNegative().setForeground(Color.RED);
			panelChild.get(j).getLbPositive().setForeground(Color.RED);
			panelChild.get(i).getLbNegative().setForeground(Color.BLUE);
			panelChild.get(i).getLbPositive().setForeground(Color.BLUE);
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
	private void setFullSize() {
	    Dimension fullsize = Toolkit.getDefaultToolkit().getScreenSize();
	    setPreferredSize(fullsize);
	  }
}