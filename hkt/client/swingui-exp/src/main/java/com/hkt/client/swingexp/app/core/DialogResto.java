package com.hkt.client.swingexp.app.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

import com.hkt.client.swing.robot.console.JVMEnv;
import com.hkt.client.swingexp.app.banhang.screen.often.DialogConfig;
import com.hkt.client.swingexp.app.component.ImageTool;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.component.PanelBackground;
import com.hkt.client.swingexp.app.component.PanelTrans;
import com.hkt.client.swingexp.app.component.PanelTrans100;
import com.hkt.client.swingexp.app.component.TextPopup;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.app.test.ScriptRunnerExp;
import com.hkt.client.swingexp.app.virtualkey.text.PanelTextKeyboard;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.GuiModelManager;
import com.hkt.module.account.entity.Profile;
import com.hkt.util.IOUtil;

@SuppressWarnings("serial")
public class DialogResto extends JDialog {

	private JPanel panelBackgroundResto1, panelBackground1;
	private JPanel panelComp;
	private JLabel labelIcon, labelTitle, lbHKT, lbRight, lbLeft, lbMeseg;
	private PanelTrans jScrollPane1;
	private JButton btnSave, btnExit, btnExt1, btnExt2, btnExt3, btnKeyboard;;
	private IDialogResto iDialogResto;
	protected boolean print, full;
	private int sizeHeight = 172;
	private int width = 600;
	private boolean enter;
	private PanelTextKeyboard panelTextKeyboard;
	private Profile profile;
	boolean check;
	public static final String Keyboard = "keyboard";
	private JScrollPane component1, component2, component3;

	public void setComponent1(JComponent btn) {
		btn.setSize(component1.getWidth() - 5, component1.getHeight() - 5);
		btn.setPreferredSize(btn.getSize());
		component1.setViewportView(btn);
	}
	
	

	public void setComponent2(JComponent btn) {
		btn.setSize(component1.getWidth() - 5, component1.getHeight() - 5);
		btn.setPreferredSize(btn.getSize());
		component2.setViewportView(btn);
	}

	public void setComponent3(JComponent btn) {
		btn.setSize(component1.getWidth() - 5, component1.getHeight() - 5);
		btn.setPreferredSize(btn.getSize());
		component3.setViewportView(btn);
	}

	public JButton getBtnSave() {
		return btnSave;
	}
	
	

	public JButton getBtnExit() {
		return btnExit;
	}



	public void setBtnExt1(JButton btnExt1) {
		this.btnExt1.setVisible(true);
		this.btnExt1.setIcon(btnExt1.getIcon());
		this.btnExt1.setText(btnExt1.getText());
		this.btnExt1.addActionListener(btnExt1.getActionListeners()[0]);
		this.btnExt1.setName("btnSua");
		try {
			String img = btnExt1.getIcon().toString();
			String im;
			String[] tokens = img.split("/");
			im = tokens[tokens.length - 1].toString();
			this.btnExt1.addMouseListener(new MouseEventClickButtonDialogPlus(im, im, im));
			panelBackgroundResto1.updateUI();
		} catch (Exception e) {

		}

	}

	public void setBtnExt2(JButton btnExt2) {
		this.btnExt2.setVisible(true);
		this.btnExt2.setIcon(btnExt2.getIcon());
		this.btnExt2.setText(btnExt2.getText());
		this.btnExt2.addActionListener(btnExt2.getActionListeners()[0]);
		String img = btnExt2.getIcon().toString();
		String im;
		String[] tokens = img.split("/");
		im = tokens[tokens.length - 1].toString();
		this.btnExt2.addMouseListener(new MouseEventClickButtonDialogPlus(im, im, im));
		panelBackgroundResto1.updateUI();
	}

	public void setBtnExt3(JButton btnExt3) {
		this.btnExt3.setVisible(true);
		this.btnExt3.setIcon(btnExt3.getIcon());
		this.btnExt3.setText(btnExt3.getText());
		this.btnExt3.addActionListener(btnExt3.getActionListeners()[0]);
		String img = btnExt3.getIcon().toString();
		String im;
		String[] tokens = img.split("/");
		im = tokens[tokens.length - 1].toString();
		this.btnExt3.addMouseListener(new MouseEventClickButtonDialogPlus(im, im, im));
		panelBackgroundResto1.updateUI();
	}

	public void setBtnSave(Boolean stt) {
		this.btnSave.setVisible(false);
		this.btnExt3.setText("");
		this.btnExt3.setIcon(null);
		this.btnExt3.setBorder(null);
		this.btnExt3.setVisible(true);
		this.btnExt3.setContentAreaFilled(false);
		this.btnExt3.setOpaque(false);
	}

	public void setTextBtnSave(String stt) {
		this.btnSave.setText(stt);
		this.btnSave.setFocusPainted(false);
	}

	public void setBtnExit(Boolean stt) {
		this.btnExit.setVisible(false);
		this.btnExt3.setText("");
		this.btnExt3.setIcon(null);
		this.btnExt3.setBorder(null);
		this.btnExt3.setVisible(true);
		this.btnExt3.setContentAreaFilled(false);
		this.btnExt3.setOpaque(false);
	}

	public void setIcon(String icon) {
		ImageIcon imageIcon = ImageTool.getInstance().resize(new ImageIcon(HomeScreen.class.getResource("icon/" + icon)),
		    labelIcon.getPreferredSize());
		labelIcon.setIcon(imageIcon);
	}

	public DialogResto(IDialogResto iDialogResto, boolean full, int width, int height) {
		this.width = width;
		this.sizeHeight = height;
		this.full = full;
		ImageIcon icon = new ImageIcon(HomeScreen.class.getResource("icon/icon.png"));
		Image im = icon.getImage();
		setIconImage(im);
		init();
		setModal(true);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setLocationRelativeTo(null);

		this.iDialogResto = iDialogResto;
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(null);
		JPanel panel = (JPanel) iDialogResto;
		panel.setPreferredSize(new Dimension(scrollPane.getWidth() - 5, scrollPane.getHeight() - 5));
		scrollPane.setViewportView(panel);
		jScrollPane1.setLayout(new BorderLayout());
		jScrollPane1.add(scrollPane, BorderLayout.CENTER);
		jScrollPane1.add(createLable(), BorderLayout.NORTH);
		jScrollPane1.add(createLable(), BorderLayout.EAST);
		jScrollPane1.add(createLable(), BorderLayout.WEST);
		jScrollPane1.add(createLable(), BorderLayout.SOUTH);
		addActionButton();

		try {
			profile = AccountModelManager.getInstance().getProfileConfigAdmin();
			if (profile.get(DialogConfig.Keyboard) != null && profile.get(DialogConfig.Keyboard).toString().equals("true")) {
				btnKeyboard.setVisible(true);
				panelTextKeyboard.setVisible(true);
				check = true;
				btnKeyboard.setText("Ẩn");
				setBtn();
			} else {
				btnKeyboard.setVisible(false);
				panelTextKeyboard.setVisible(false);
				check = false;
			}

		} catch (Exception e) {
		}

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {
				print = true;
				addActionAllComponents(jScrollPane1);
				if (!enter) {
					addKeyBindings(panelBackground1);
				}
				System.out.println("opendList   " + new JVMEnv().getMemoryInfo());
				addVituarKey(jScrollPane1);
				// if (check){
				// panelTextKeyboard.setVisible(false);
				// }
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

	}

	public void setBtn() {
		if (btnKeyboard.isVisible() == true) {
			btnKeyboard.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (btnKeyboard.getText().equals("Ẩn")) {
						panelTextKeyboard.setVisible(false);
						btnKeyboard.setText("Hiện");
					} else {
						panelTextKeyboard.setVisible(true);
						btnKeyboard.setText("Ẩn");
					}
				}
			});
		}
	}

	private boolean addActionAllComponents(Container c) {
		Component[] comps = c.getComponents();
		for (Component comp : comps) {
			if (comp instanceof TextPopup) {
				enter = true;
			}

			if (comp instanceof Container) {
				addActionAllComponents((Container) comp);
			}
		}
		return enter;
	}

	private JLabel createLable() {
		JLabel label = new JLabel();
		label.setSize(20, 20);
		label.setPreferredSize(label.getSize());
		return label;
	}

	public DialogResto(IDialogResto iDialogResto) {
		ImageIcon icon = new ImageIcon(HomeScreen.class.getResource("icon/icon.png"));
		Image im = icon.getImage();
		setIconImage(im);
		init();
		setModal(true);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setLocationRelativeTo(null);
		addKeyBindings(panelBackground1);
		this.iDialogResto = iDialogResto;
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(null);
		JPanel panel = (JPanel) iDialogResto;
		panel.setPreferredSize(new Dimension(scrollPane.getWidth() - 5, scrollPane.getHeight() - 5));
		scrollPane.setViewportView(panel);
		jScrollPane1.setLayout(new BorderLayout());
		jScrollPane1.add(scrollPane, BorderLayout.CENTER);
		jScrollPane1.add(createLable(), BorderLayout.NORTH);
		jScrollPane1.add(createLable(), BorderLayout.EAST);
		jScrollPane1.add(createLable(), BorderLayout.WEST);
		jScrollPane1.add(createLable(), BorderLayout.SOUTH);
		addActionButton();

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {
				print = true;
				System.out.println("opendList   " + new JVMEnv().getMemoryInfo());
				addVituarKey(jScrollPane1);
				// if (check){
				// panelTextKeyboard.setVisible(false);
				// }
			}

			@Override
			public void windowClosed(WindowEvent e) {
				if (print) {
					System.out.println("closeList   " + new JVMEnv().getMemoryInfo());
					print = false;
				}
			}

		});
	}

	protected class JPanelLayout extends JPanel {

		public JPanelLayout(JComponent comp1, String pos1, JComponent comp2, String pos2) {
			this.setLayout(new BorderLayout(0, 0));
			this.add(comp1, pos1);
			this.add(comp2, pos2);

		}
	}

	private void init() {
		JPanel panel = new JPanel();
		panelBackground1 = new PanelBackground();
		panelBackgroundResto1 = new PanelTrans();
		btnSave = new javax.swing.JButton("Đồng ý");
		btnSave.setName("btnSave");
		btnSave.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/iconOk.png")));
		btnExit = new javax.swing.JButton("Thoát");
		btnExit.setName("btnExit");
		btnExit.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/iconBack.png")));
		labelIcon = new javax.swing.JLabel();
		labelIcon.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/description.png")));
		labelTitle = new javax.swing.JLabel("Dialog Resto");
		labelTitle.setFont(new java.awt.Font("Tahoma", 1, 14));
		labelTitle.setForeground(new java.awt.Color(126, 0, 0));
		component1 = new JScrollPane();
		component1.setSize(130, 25);
		component1.setPreferredSize(component1.getSize());
		component2 = new JScrollPane();
		component3 = new JScrollPane();
		component2.setSize(130, 25);
		component3.setSize(130, 25);
		component2.setPreferredSize(component2.getSize());
		component3.setPreferredSize(component3.getSize());
		panel.setLayout(new GridLayout(1,2));
		panel.add(labelTitle);
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout(FlowLayout.TRAILING, 5, 0));
		panel1.add(component1);
		panel1.add(component2);
	//	panel1.add(component3, FlowLayout.RIGHT);
		panel.add(panel1);
		panel.setOpaque(false);
		panel1.setOpaque(false);
		component1.setBorder(null);
		component1.setOpaque(false);
		component1.getViewport().setOpaque(false);
		component2.setBorder(null);
		component2.setOpaque(false);
		component2.getViewport().setOpaque(false);
		component3.setBorder(null);
		component3.setOpaque(false);
		component3.getViewport().setOpaque(false);
		
		jScrollPane1 = new PanelTrans();
		jScrollPane1.setBorder(null);
		jScrollPane1.setOpaque(false);
		btnExt1 = new javax.swing.JButton();
		btnExt1.setName("btnSua");
		btnExt2 = new javax.swing.JButton();
		btnExt3 = new javax.swing.JButton();
		lbHKT = new javax.swing.JLabel();
		lbRight = new javax.swing.JLabel();
		lbLeft = new javax.swing.JLabel();
		btnExt1.setVisible(false);
		btnExt2.setVisible(false);
		btnExt3.setVisible(false);
		btnKeyboard = new JButton("Ẩn");
		btnKeyboard.setVisible(false);
		btnKeyboard.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/refresh1.png")));
		btnKeyboard.addMouseListener(new MouseEventClickButtonDialog("refresh1.png", "refresh1.png", "refresh1.png"));
		lbMeseg = new JLabel("");
		panelBackgroundResto1.setOpaque(false);
		GroupLayout panelBackgroundResto1Layout = new GroupLayout(panelBackgroundResto1);
		panelBackgroundResto1.setLayout(panelBackgroundResto1Layout);
		panelBackgroundResto1Layout
		    .setHorizontalGroup(panelBackgroundResto1Layout
		        .createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addGroup(
		            panelBackgroundResto1Layout.createSequentialGroup().addGap(28, 28, 28)
		                .addComponent(labelIcon, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
		                .addGap(6, 6, 6).addComponent(panel, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))
		        .addGroup(
		            panelBackgroundResto1Layout.createSequentialGroup().addGap(25, 25, 25)
		                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, width, Short.MAX_VALUE).addGap(25, 25, 25))
		        .addGroup(
		            GroupLayout.Alignment.TRAILING,
		            panelBackgroundResto1Layout.createSequentialGroup().addGap(43, 43, 43)
		                .addComponent(btnKeyboard, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
		                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
		                .addComponent(btnExt3, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
		                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
		                .addComponent(btnExt2, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
		                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
		                .addComponent(btnExt1, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
		                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
		                .addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
		                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
		                .addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
		                .addGap(25, 25, 25)));
		panelBackgroundResto1Layout.setVerticalGroup(panelBackgroundResto1Layout.createParallelGroup(
		    GroupLayout.Alignment.LEADING).addGroup(
		    panelBackgroundResto1Layout
		        .createSequentialGroup()
		        .addGroup(
		            panelBackgroundResto1Layout
		                .createParallelGroup(GroupLayout.Alignment.LEADING)
		                .addComponent(labelIcon, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
		                .addGroup(
		                    panelBackgroundResto1Layout.createSequentialGroup().addGap(8, 8, 8)
		                        .addComponent(panel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
		        .addGap(6, 6, 6)
		        .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, sizeHeight, Short.MAX_VALUE)
		        .addGap(9, 9, 9)
		        .addGroup(
		            panelBackgroundResto1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		                .addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
		                .addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
		                .addComponent(btnExt1, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
		                .addComponent(btnExt2, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
		                .addComponent(btnExt3, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
		                .addComponent(btnKeyboard, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
		        .addGap(11, 11, 11)));

		lbHKT.setFont(new java.awt.Font("Tahoma", 1, 16));
		lbHKT.setForeground(new java.awt.Color(126, 0, 0));
		lbHKT.setHorizontalAlignment(SwingConstants.RIGHT);
		lbHKT.setText("HKT Sofware   ");
		lbHKT.setPreferredSize(new Dimension(WIDTH, 50));

		panelTextKeyboard = new PanelTextKeyboard();
		panelTextKeyboard.setVisible(false);
		lbLeft.setPreferredSize(new Dimension(23, HEIGHT));
		lbRight.setPreferredSize(new Dimension(23, HEIGHT));
		lbMeseg.setPreferredSize(new Dimension(WIDTH, 23));

		panelComp = new JPanel();
		panelComp.setOpaque(false);
		panelComp.setLayout(new BorderLayout());
		panelComp.add(lbMeseg, BorderLayout.CENTER);
		panelComp.add(panelTextKeyboard, BorderLayout.SOUTH);

		panelBackground1.setLayout(new BorderLayout());
		panelBackground1.add(lbHKT, BorderLayout.NORTH);
		panelBackground1.add(panelBackgroundResto1, BorderLayout.CENTER);
		panelBackground1.add(lbLeft, BorderLayout.EAST);
		panelBackground1.add(lbRight, BorderLayout.WEST);
		panelBackground1.add(panelComp, BorderLayout.SOUTH);

		// jScrollPane1.setSize(width, 100);`
		// panelBackground1.setPreferredSize(panelBackground1.getSize());
		// setSize(874, 100);

		btnExit.addMouseListener(new MouseEventClickButtonDialogPlus("iconBack.png", "iconBack.png", "iconBack.png"));
		btnSave.addMouseListener(new MouseEventClickButtonDialogPlus("iconOk.png", "iconOk.png", "iconOk.png"));
		btnExt1.addMouseListener(new MouseEventClickButtonDialogPlus("print1.png", "print1.png", "print1.png"));
		if (full) {
			getContentPane().add(panelBackground1);
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
			javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panelTrans);
			panelTrans.setLayout(layout);
			layout.setHorizontalGroup(layout
			    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 210,
			        Short.MAX_VALUE)
			    .addGroup(
			        javax.swing.GroupLayout.Alignment.TRAILING,
			        layout
			            .createSequentialGroup()
			            .addGroup(
			                layout
			                    .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
			                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
			                    .addGroup(
			                        layout
			                            .createSequentialGroup()
			                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
			                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			                            .addComponent(panelBackground1, javax.swing.GroupLayout.PREFERRED_SIZE,
			                                javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
			                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)))
			            .addGap(2, 2, 2)));
			layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
			    layout
			        .createSequentialGroup()
			        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
			        .addGap(2, 2, 2)
			        .addGroup(
			            layout
			                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
			                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
			                .addComponent(panelBackground1, javax.swing.GroupLayout.PREFERRED_SIZE,
			                    javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
			        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)));
		}
		pack();
	}

	@Override
	public void setTitle(String title) {
		labelTitle.setText(title);
		super.setTitle(title);
	}

	private void addActionButton() {

		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					iDialogResto.Save();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					dispose();
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
	}

	private void addVituarKey(Container c) {
		Component[] comps = c.getComponents();

		for (Component comp : comps) {

			if (comp instanceof JTextField) {
				if (((JTextField) comp).isFocusOwner()) {
					panelTextKeyboard.setText((JComponent) comp);
				}
				check = false;
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
					lbMeseg.setVisible(true);

				} else {
					panelTextKeyboard.setVisible(true);
					lbMeseg.setVisible(false);
				}
			}
		});

		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "Enter");
		jc.getActionMap().put("Enter", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					iDialogResto.Save();
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0, false), "F11");
		jc.getActionMap().put("F11", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				dispose();
				Thread thread = new Thread() {
					public void run() {
						// for (int i = 1; i < 10; i++) {
						PanelTestAll.runAll = false;
						MyPanel.isTest = true;
						System.out.println("Bắt đầu chạy test giao diện " + ((JPanel) iDialogResto).getName());
						// System.out.println(new JVMEnv().getMemoryInfo());
						FrameUI frameUI = FrameUI.getInstance();
						try {
							File file = HKTFile.getFile("test", ((JPanel) iDialogResto).getName() + ".js");
							InputStream is;
							if (file.exists()) {
								is = new FileInputStream(file);
							} else {
								is = ScriptRunnerExp.class.getResourceAsStream(((JPanel) iDialogResto).getName() + ".js");
							}
							String script = IOUtil.getStreamContentAsString(is, "UTF-8");
							ScriptRunnerExp scriptRunnerExp = new ScriptRunnerExp(frameUI);
							scriptRunnerExp.eval(script);
							MyPanel.isTest = false;
							// System.out.println(new JVMEnv().getMemoryInfo());
							// System.out.println("...........................................................");
						} catch (Exception ex) {
							ex.printStackTrace();
							frameUI.destroy();
							frameUI = null;
							System.gc();
							MyPanel.isTest = false;
						} finally {

						}
					}

					// }
				};
				thread.start();
			}
		});
	}
}