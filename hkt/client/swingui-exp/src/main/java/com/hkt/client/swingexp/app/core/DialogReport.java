package com.hkt.client.swingexp.app.core;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.hkt.client.swing.robot.console.JVMEnv;
import com.hkt.client.swingexp.app.component.ImageTool;
import com.hkt.client.swingexp.app.component.PanelBackground;
import com.hkt.client.swingexp.app.component.PanelTrans;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.client.swingexp.model.GuiModelManager;

public class DialogReport extends JDialog {
	private JButton					btnExit;
	private JButton					btnPrint;
	private JScrollPane			scrollPane;
	private PanelBackground	panelBackground;
	private IDialogReport		iDialogReport;
	protected boolean				print;
	private JLabel					lblTitleDialog;
	private JButton					btnExt1 = null, btnExt2 = null, btnExt3 = null;
	private JButton					btnChartOption;
	public static String		permission;
	
	public DialogReport(IDialogReport iDialogReport1) {
		ImageIcon icon = new ImageIcon(HomeScreen.class.getResource("icon/icon.png"));
		Image im = icon.getImage();
		setIconImage(im);
		init();
		addKeyBindings(panelBackground);
		setModal(true);
		dispose();
		setUndecorated(true);
		setFullSize();
		this.iDialogReport = iDialogReport1;
		scrollPane.setViewportView((JPanel) iDialogReport1);
		addWindowListener(new WindowAdapter() {			
			@Override
			public void windowOpened(WindowEvent e) {
				print = true;
				iDialogReport.caculateReport();
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
	
	private void init() {
		//    UIManager.put("TabbedPane.contentOpaque", Boolean.FALSE);
		//    UIManager.put("TabbedPane.tabsOpaque", Boolean.FALSE);    
		panelBackground = new PanelBackground();
		scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(null);
		
		btnExit = new JButton("Thoát");
		btnExit.setName("btnExit");
		btnExit.setFont(new Font("Tahoma", 1, 14));
		btnExit.setPreferredSize(new Dimension(120, 35));
		btnExit.setIcon(ImageTool.getInstance().resize(new ImageIcon(HomeScreen.class.getResource("icon/iconBack.png")), new Dimension(25, 25)));
		btnExit.setIconTextGap(10);	
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnExitActionPerformed(e);
			}
		});
		
		btnPrint = new JButton("In");
		btnPrint.setName("btnPrint");
		btnPrint.setFont(new Font("Tahoma", 1, 14));
		btnPrint.setPreferredSize(new Dimension(120, 35));
		btnPrint.setIcon(ImageTool.getInstance().resize(new ImageIcon(HomeScreen.class.getResource("icon/print1.png")), new Dimension(25, 25)));
		btnPrint.setIconTextGap(10);	
		btnPrint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnPrintActionPerformed(e);
			}
		});
		
		btnChartOption = new JButton("Đồ thị");
		btnChartOption.setName("btnChartOption");
		btnChartOption.setFont(new Font("Tahoma", 1, 14));
		btnChartOption.setPreferredSize(new Dimension(120, 35));
		btnChartOption.setIcon(ImageTool.getInstance().resize(new ImageIcon(HomeScreen.class.getResource("icon/statistic1.png")), new Dimension(25, 25)));
		btnChartOption.setIconTextGap(10);
		btnChartOption.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnChartActionPerformed(e);
			}
		});
		
		JLabel lblTitleHKTVersion = new JLabel();
		lblTitleHKTVersion.setFont(new java.awt.Font("Tahoma", 1, 16));
		lblTitleHKTVersion.setForeground(new java.awt.Color(126, 0, 0));
		lblTitleHKTVersion.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTitleHKTVersion.setText("HKT Sofware");
		lblTitleHKTVersion.setPreferredSize(new Dimension(WIDTH, 50));

		lblTitleDialog = new javax.swing.JLabel("Dialog Report");
		lblTitleDialog.setPreferredSize(new Dimension(100, 40));
		lblTitleDialog.setFont(new java.awt.Font("Tahoma", 1, 14));
		lblTitleDialog.setForeground(new java.awt.Color(126, 0, 0));
		lblTitleDialog.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/statistic1.png")));
		lblTitleDialog.setIconTextGap(10);
		lblTitleDialog.setHorizontalTextPosition(SwingConstants.RIGHT);
		lblTitleDialog.setHorizontalAlignment(SwingConstants.LEFT);
		
		// Giao diện chính panel2 add giao diện chính jPanel1 và tạo khoảng cách các bên
		PanelTrans panelTransLayout2 = new PanelTrans();
		panelTransLayout2.setOpaque(false);
		panelTransLayout2.setLayout(new BorderLayout());
		panelTransLayout2.add(scrollPane, BorderLayout.CENTER);
		panelTransLayout2.setBorder(BorderFactory.createEmptyBorder(0, 20, 11, 20));
		
		JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelButton.setOpaque(false);
		panelButton.add(btnPrint);
		panelButton.add(btnChartOption);
		panelButton.add(btnExit);
		
		// Panel trong suốt thứ 2 add giao diện chính panel2
		PanelTrans panelTransLayout1 = new PanelTrans();
		panelTransLayout1.setOpaque(false);
		panelTransLayout1.setLayout(new BorderLayout(0, 0));
		panelTransLayout1.add(lblTitleDialog, BorderLayout.PAGE_START);
		panelTransLayout1.add(panelTransLayout2, BorderLayout.CENTER);
		panelTransLayout1.add(panelButton, BorderLayout.PAGE_END);
		panelTransLayout1.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		
		// Panel thứ nhất add panel trong suốt thứ 2 + lable HKT Software
		panelBackground.setLayout(new BorderLayout());
		panelBackground.add(panelTransLayout1, BorderLayout.CENTER);
		panelBackground.add(lblTitleHKTVersion, BorderLayout.NORTH);
		panelBackground.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
		panelBackground.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0, false), "F12");
		panelBackground.getActionMap().put("F12", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnPrintActionPerformed(e);
			}
		});
		
		// Giao diện dialog add Panel thứ nhất
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(panelBackground, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(panelBackground, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		
		btnExit.addMouseListener(new MouseEventClickButtonDialog("iconBack.png", "iconBack.png", "iconBack.png"));
		btnPrint.addMouseListener(new MouseEventClickButtonDialog("print1.png", "print1.png", "print1.png"));
		pack();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	
	private void setFullSize() {
		Dimension fullsize = Toolkit.getDefaultToolkit().getScreenSize();
		setPreferredSize(fullsize);
	}
	
	@Override
	public void setTitle(String title) {
		lblTitleDialog.setText(title);
		super.setTitle(title);
	}
	
	public void setIcon(String icon) {
		ImageIcon imageIcon = ImageTool.getInstance().resize(new ImageIcon(HomeScreen.class.getResource("icon/" + icon)), new Dimension(30, 30));
		lblTitleDialog.setIcon(imageIcon);
	}
	
	private void btnPrintActionPerformed(ActionEvent evt) {
		try {
			iDialogReport.Print();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void btnChartActionPerformed(ActionEvent evt) {
		try {
			iDialogReport.Chart();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setBtnButton1(JButton button){
		this.btnExt1 = button;
	}
	
	public void setBtnButton2(JButton button){
		this.btnExt2 = button;
	}
	
	public void setBtnButton3(JButton button){
		this.btnExt3 = button;
	}
	
	private void btnExitActionPerformed(ActionEvent evt) {
		System.gc();
		this.dispose();
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
}
