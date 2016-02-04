package com.hkt.client.swingexp.app.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
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
import java.util.HashMap;

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
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.hkt.client.swing.robot.console.JVMEnv;
import com.hkt.client.swing.widget.BeanBindingJTable;
import com.hkt.client.swingexp.app.banhang.list.ChooseDelProduct;
import com.hkt.client.swingexp.app.banhang.screen.often.DialogConfig;
import com.hkt.client.swingexp.app.component.ImageTool;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.component.PanelBackground;
import com.hkt.client.swingexp.app.component.PanelTrans;
import com.hkt.client.swingexp.app.component.PanelTrans100;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.app.test.ScriptRunnerExp;
import com.hkt.client.swingexp.app.virtualkey.text.PanelTextKeyboard;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.GuiModelManager;
import com.hkt.module.account.entity.Profile;
import com.hkt.util.IOUtil;

@SuppressWarnings("serial")
public class DialogMain extends JDialog {

	private JPanel jPanel1, jPanel2;
	private JPanel panelComp;
	private PanelTrans panelTrans;
	private JButton btnReset, btnAdd, btnRefresh, btnEdit, btnRemove, btnExit, btnKeyboard, btnExt;
	private JLabel labelTitle;
	private IDialogMain iDialogMain;
	private int sizeHeight = 550;
	private int weight = 600;
	@SuppressWarnings("unused")
	private HashMap<Component, String> hashMap;
	private boolean full = false;
	private boolean print = false;
	private JLabel labelIcon, lbMeseg;
	private PanelTextKeyboard panelTextKeyboard;
	private Profile profile;
	public static final String Keyboard = "keyboard";
	private JScrollPane component1, component2, component3;

	public DialogMain() {

	}
	
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

	public JButton getBtnReset() {
		return btnReset;
	}



	public void setIcon(String icon) {
		ImageIcon imageIcon = ImageTool.getInstance().resize(new ImageIcon(HomeScreen.class.getResource("icon/" + icon)),
		    labelIcon.getPreferredSize());
		labelIcon.setIcon(imageIcon);
	}

	private JLabel createLable() {
		JLabel label = new JLabel();
		label.setSize(20, 20);
		label.setPreferredSize(label.getSize());
		return label;
	}

	public DialogMain(IDialogMain dialogMain) throws Exception {
		setUndecorated(true);
		ImageIcon icon = new ImageIcon(HomeScreen.class.getResource("icon/icon.png"));
		Image im = icon.getImage();
		setIconImage(im);
		init();
		setName("DialogMain");
		setModal(true);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setLocationRelativeTo(this);
		this.iDialogMain = dialogMain;
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(null);
		scrollPane.setViewportView((JPanel) dialogMain);
		JPanel panel = (JPanel) dialogMain;
		panel.setPreferredSize(new Dimension(scrollPane.getWidth() - 5, scrollPane.getHeight() - 5));
		scrollPane.setViewportView(panel);
		panelTrans.setLayout(new BorderLayout());
		panelTrans.add(scrollPane, BorderLayout.CENTER);
		panelTrans.add(createLable(), BorderLayout.NORTH);
		panelTrans.add(createLable(), BorderLayout.EAST);
		panelTrans.add(createLable(), BorderLayout.WEST);
		panelTrans.add(createLable(), BorderLayout.SOUTH);
		addActionButton();
		addKeyBindings(jPanel1);

	}

	public DialogMain(IDialogMain dialogMain, boolean full) throws Exception {
		this.full = full;
		setUndecorated(true);
		ImageIcon icon = new ImageIcon(HomeScreen.class.getResource("icon/icon.png"));
		Image im = icon.getImage();
		setIconImage(im);
		init();
		setName("DialogMain");
		setModal(true);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setLocationRelativeTo(this);
		this.iDialogMain = dialogMain;
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(null);
		JPanel panel = (JPanel) dialogMain;
		panel.setSize(50, 50);
		panel.setPreferredSize(panel.getSize());
		scrollPane.setViewportView(panel);
		panelTrans.setLayout(new BorderLayout());

		panelTrans.add(scrollPane, BorderLayout.CENTER);
		panelTrans.add(createLable(), BorderLayout.NORTH);
		panelTrans.add(createLable(), BorderLayout.EAST);
		panelTrans.add(createLable(), BorderLayout.WEST);
		panelTrans.add(createLable(), BorderLayout.SOUTH);

		addActionButton();
		addKeyBindings(jPanel1);

	}

	public DialogMain(IDialogMain dialogMain, int sizeHeight, int weight) throws Exception {
		setUndecorated(true);
		this.weight = weight;
		this.sizeHeight = sizeHeight;
		ImageIcon icon = new ImageIcon(HomeScreen.class.getResource("icon/icon.png"));
		Image im = icon.getImage();
		setIconImage(im);
		init();
		addKeyBindings(jPanel1);
		setModal(true);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setLocationRelativeTo(this);
		this.iDialogMain = dialogMain;
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(null);
		scrollPane.setViewportView((JPanel) dialogMain);
		panelTrans.setLayout(new BorderLayout());

		panelTrans.add(scrollPane, BorderLayout.CENTER);
		panelTrans.add(createLable(), BorderLayout.NORTH);
		panelTrans.add(createLable(), BorderLayout.EAST);
		panelTrans.add(createLable(), BorderLayout.WEST);
		panelTrans.add(createLable(), BorderLayout.SOUTH);

		addActionButton();

	}

	@Override
	public void setTitle(String title) {
		labelTitle.setText(title);
		super.setTitle(title);
	}

	private void init() throws Exception {

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {
				print = true;
				// System.out.println("opend   " + new JVMEnv().getMemoryInfo());
				addVituarKey(jPanel2);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				if (print) {
					new GuiModelManager().getObservable().deleteObservers();
					// System.out.println("close   "
					// + new JVMEnv().getMemoryInfo());
					print = false;

				}
			}

		});
		jPanel1 = new PanelTrans();
		jPanel2 = new PanelBackground();
		panelTrans = new PanelTrans();
		btnAdd = new JButton("Lưu");
		btnAdd.setName("btnSave");
		btnAdd.setIconTextGap(10);
		btnAdd.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/save1.png")));
		btnEdit = new JButton("Sửa");
		btnEdit.setName("btnEdit");
		btnEdit.setIconTextGap(10);
		btnEdit.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/modify1.png")));
		btnRefresh = new JButton("Xem lại");
		btnRefresh.setName("btnRefresh");
		btnRefresh.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/refresh1.png")));
		btnRemove = new JButton("Xóa");
		btnRemove.setName("btnDelete");
		btnRemove.setIconTextGap(10);
		btnRemove.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/delete1.png")));
		btnReset = new JButton("Điền mới");
		btnReset.setName("btnReset");
		btnReset.setMargin(new Insets(0, 0, 0, 0));
		btnReset.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/reset1.png")));
		btnExt = new JButton("In");
		btnExt.setName("btnExt");
		btnExt.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/print1.png")));
		btnExit = new JButton("Thoát");
		btnExit.setName("btnExit");
		btnExit.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/cancel1.png")));
		btnKeyboard = new JButton();
		btnKeyboard.setVisible(false);
		btnKeyboard.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/refresh1.png")));
		btnKeyboard.addMouseListener(new MouseEventClickButtonDialog("refresh1.png", "refresh1.png", "refresh1.png"));
		panelTrans.setBorder(null);
		panelTrans.setOpaque(false);
		// jScrollPane1.getViewport().setOpaque(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new GridLayout(1, 0));
		jPanel2.setBackground(new Color(255, 255, 255));
		jPanel1.setOpaque(false);
		groupLayout();
		showButton(true);
		btnRemove.setEnabled(false);
		btnRefresh.setEnabled(false);

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

		btnAdd.addMouseListener(new MouseEventClickButtonDialog("save1.png", "save2.png", "save3.png"));
		btnEdit.addMouseListener(new MouseEventClickButtonDialog("modify1.png", "modify2.png", "modify3.png"));
		btnRefresh.addMouseListener(new MouseEventClickButtonDialog("refresh1.png", "refresh2.png", "refresh3.png"));
		btnRemove.addMouseListener(new MouseEventClickButtonDialog("delete1.png", "delete2.png", "delete3.png"));
		//btnReset.addMouseListener(new MouseEventClickButtonDialog("reset1.png", "reset2.png", "reset3.png"));
	//	btnExt.addMouseListener(new MouseEventClickButtonDialog("print1.png", "print1.png", "print1.png"));
		btnExit.addMouseListener(new MouseEventClickButtonDialog("cancel1.png", "cancel1.png", "cancel1.png"));
		pack();
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

	private void groupLayout() {
		labelIcon = new javax.swing.JLabel();
		labelIcon.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/description.png")));
		labelTitle = new javax.swing.JLabel("Dialog Resto");
		labelTitle.setFont(new java.awt.Font("Tahoma", 1, 14));
		labelTitle.setForeground(new java.awt.Color(126, 0, 0));
		component1 = new JScrollPane();
		component1.setSize(140, 30);
		component1.setPreferredSize(component1.getSize());
		component2 = new JScrollPane();
		component3 = new JScrollPane();
		component2.setSize(140, 30);
		component3.setSize(142, 30);
		component2.setPreferredSize(component2.getSize());
		component3.setPreferredSize(component3.getSize());
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		panel.add(labelTitle);
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout(FlowLayout.TRAILING, 5, 0));
		panel1.add(component1);
		panel1.add(component2);
		panel1.add(component3);
		
		JLabel lb = new JLabel();
		lb.setSize(14, 30);
		lb.setPreferredSize(lb.getSize());
		panel1.add(lb);
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
		GroupLayout panelBackgroundResto1Layout = new GroupLayout(jPanel1);
		jPanel1.setLayout(panelBackgroundResto1Layout);
		panelBackgroundResto1Layout
		    .setHorizontalGroup(panelBackgroundResto1Layout
		        .createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addGroup(
		            panelBackgroundResto1Layout.createSequentialGroup().addGap(28, 28, 28)
		                .addComponent(labelIcon, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
		                .addGap(6, 6, 6).addComponent(panel, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))
		        .addGroup(
		            panelBackgroundResto1Layout.createSequentialGroup().addGap(25, 25, 25)
		                .addComponent(panelTrans, GroupLayout.DEFAULT_SIZE, weight, Short.MAX_VALUE).addGap(25, 25, 25))
		        .addGroup(
		            GroupLayout.Alignment.TRAILING,
		            panelBackgroundResto1Layout.createSequentialGroup().addGap(43, 43, 43)
		                .addComponent(btnKeyboard, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
		                .addGap(7, 7, 7)
		                .addComponent(btnExt, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
		                .addGap(7, 7, 7)
		                .addComponent(btnReset, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
		                .addGap(7, 7, 7).addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
		                .addGap(7, 7, 7)
		                .addComponent(btnRefresh, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
		                .addGap(7, 7, 7).addComponent(btnEdit, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
		                .addGap(7, 7, 7)
		                .addComponent(btnRemove, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
		                .addGap(7, 7, 7).addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
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
		        .addComponent(panelTrans, GroupLayout.DEFAULT_SIZE, sizeHeight, Short.MAX_VALUE)
		        .addGap(11, 11, 11)
		        .addGroup(
		            panelBackgroundResto1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		            .addComponent(btnExt, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
		                .addComponent(btnKeyboard, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
		                .addComponent(btnReset, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
		                .addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
		                .addComponent(btnRefresh, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
		                .addComponent(btnEdit, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
		                .addComponent(btnRemove, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
		                .addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
		        .addGap(11, 11, 11)));

		JLabel lbHKT = new JLabel();
		lbHKT.setFont(new java.awt.Font("Tahoma", 1, 16));
		lbHKT.setForeground(new java.awt.Color(126, 0, 0));
		lbHKT.setHorizontalAlignment(SwingConstants.RIGHT);
		lbHKT.setText("HKT Sofware   ");
		lbHKT.setPreferredSize(new Dimension(WIDTH, 50));

		lbMeseg = new JLabel();
		lbMeseg.setPreferredSize(new Dimension(WIDTH, 23));

		panelTextKeyboard = new PanelTextKeyboard();
		panelTextKeyboard.setVisible(false);
		panelComp = new JPanel();
		panelComp.setOpaque(false);
		panelComp.setLayout(new BorderLayout());
		panelComp.add(lbMeseg, BorderLayout.CENTER);
		panelComp.add(panelTextKeyboard, BorderLayout.SOUTH);

		jPanel2.setLayout(new BorderLayout());
		jPanel2.add(jPanel1, BorderLayout.CENTER);
		jPanel2.add(lbHKT, BorderLayout.NORTH);
		jPanel2.add(panelComp, BorderLayout.SOUTH);
		jPanel2.add(createLable(), BorderLayout.EAST);
		jPanel2.add(createLable(), BorderLayout.WEST);

		// setSize(874, this.getHeight());

		if (full) {
			getContentPane().add(jPanel2);
		} else {
			PanelTrans100 panelTrans = new PanelTrans100();
			panelTrans.setOpaque(false);
			panelTrans.setBackground(Color.BLACK);
			getContentPane().add(panelTrans);
			// code trong suot
			getRootPane().setOpaque(false);
			getContentPane().setBackground(new Color(0, 0, 0, 0));
			setBackground(new Color(0, 0, 0, 0));
			// code co gian man hinh
			JLabel jLabel1 = new JLabel(" ");
			JLabel jLabel2 = new JLabel(" ");
			JLabel jLabel3 = new JLabel(" ");
			JLabel jLabel4 = new JLabel(" ");
			javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panelTrans);
			panelTrans.setLayout(layout);
			layout.setHorizontalGroup(layout
			    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 410,
			        Short.MAX_VALUE)
			    .addGroup(
			        javax.swing.GroupLayout.Alignment.TRAILING,
			        layout
			            .createSequentialGroup()
			            .addGroup(
			                layout
			                    .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
			                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
			                    .addGroup(
			                        layout
			                            .createSequentialGroup()
			                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
			                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
			                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
			                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)))
			            .addGap(2, 2, 2)));
			layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
			    layout
			        .createSequentialGroup()
			        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
			        .addGap(2, 2, 2)
			        .addGroup(
			            layout
			                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
			                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
			                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
			                    javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
			        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)));
		}
		btnExt.setVisible(false);

	}
	
	

	public JButton getBtnExt() {
		return btnExt;
	}

	private void addActionButton() {

		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					iDialogMain.save();
					hashMap = null;
					btnRefresh.setEnabled(false);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					showButton(true);
					iDialogMain.edit();
					hashMap = new HashMap<Component, String>();
					// edit = true;
					// addActionAllComponents(jPanel2);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});

		btnRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					iDialogMain.refresh();
					showButton(false);
					hashMap = null;
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});

		btnRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					iDialogMain.delete();
					showButton(true);
					btnRemove.setEnabled(false);
					btnRefresh.setEnabled(false);
					hashMap = null;
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					iDialogMain.reset();
					showButton(true);
					btnRemove.setEnabled(false);
					btnRefresh.setEnabled(false);
					hashMap = null;
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
	}

	private void exit() {
		try {
			// if (hashMap != null && !removeActionAllComponents(jPanel2)) {
			// ChooseDelProduct panel = new
			// ChooseDelProduct("Dữ liệu đã bị thay đổi, bạn muốn lưu lại?");
			// DialogResto dialog = new DialogResto(panel);
			// dialog.setSize(500, 220);
			// dialog.setPreferredSize(new Dimension(500, 230));
			// dialog.setTitle("Lưu dữ liệu");
			// dialog.setLocationRelativeTo(null);
			// dialog.setModal(true);
			// dialog.setVisible(true);
			// if (panel.isDelete()) {
			// iDialogMain.save();
			// }
			// }
			loadChangeUpdateIndex(jPanel2);
			System.gc();
			dispose();
		} catch (Exception e1) {
			System.gc();
			dispose();
		}
	}

	@SuppressWarnings("rawtypes")
	private void loadChangeUpdateIndex(Container c) {
		Component[] comps = c.getComponents();
		for (Component comp : comps) {
			if (comp instanceof BeanBindingJTable) {
				if (((BeanBindingJTable) comp).isEdit()) {
					ChooseDelProduct panel = new ChooseDelProduct("Vị trí ưu tiên bị thay đổi, bạn muốn lưu lại?");
					DialogResto dialog = new DialogResto(panel, false, 0, 80);
					dialog.setTitle("Lưu dữ liệu");
					dialog.setLocationRelativeTo(null);
					dialog.setModal(true);
					dialog.setVisible(true);
					if (panel.isDelete()) {
						((BeanBindingJTable) comp).saveIndex();
					}
				}
			}
			if (comp instanceof Container) {
				loadChangeUpdateIndex((Container) comp);
			}
		}
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

	// private boolean removeActionAllComponents(Container c) {
	// Component[] comps = c.getComponents();
	//
	// for (Component comp : comps) {
	// if (comp instanceof JTextComponent) {
	// if (!hashMap.get(comp).toString().equals(((JTextComponent)
	// comp).getText())) {
	// edit = false;
	// }
	// }
	// if (comp instanceof JSpinner) {
	// if (!hashMap.get(comp).equals(((JSpinner) comp).getValue().toString())) {
	// edit = false;
	// }
	// }
	// if (comp instanceof MyJDateChooser) {
	// if (!hashMap.get(comp).equals(((MyJDateChooser)
	// comp).getDate().toString())) {
	// edit = false;
	// }
	// }
	// if (comp instanceof JTable) {
	// if (!hashMap.get(comp).equals(String.valueOf(((JTable)
	// comp).getRowCount()))) {
	// edit = false;
	// }
	//
	// }
	// if (comp instanceof JComboBox) {
	// if (!hashMap.get(comp).equals(((JComboBox)
	// comp).getSelectedItem().toString())) {
	// edit = false;
	// }
	// }
	// if (comp instanceof JToggleButton) {
	// if (!hashMap.get(comp).equals(String.valueOf(((JToggleButton)
	// comp).isSelected()))) {
	// edit = false;
	// }
	// }
	// if (comp instanceof Container) {
	// removeActionAllComponents((Container) comp);
	// }
	// }
	// return edit;
	// }

	public void showButton(boolean bool) {
		btnAdd.setEnabled(bool);
		btnEdit.setEnabled(!bool);
		btnRefresh.setEnabled(bool);
		btnRemove.setEnabled(bool);
	}

	private void addKeyBindings(JComponent jc) {

		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "ESC");
		jc.getActionMap().put("ESC", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				exit();
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
						// System.out.println("Bắt đầu chạy test giao diện "
						// + ((JPanel) iDialogMain).getName());
						// System.out.println(new JVMEnv().getMemoryInfo());
						FrameUI frameUI = FrameUI.getInstance();
						try {
							File file = HKTFile.getFile("test", ((JPanel) iDialogMain).getName() + ".js");
							InputStream is;
							if (file.exists()) {
								is = new FileInputStream(file);
							} else {
								System.out.println(((JPanel) iDialogMain).getName());
								is = ScriptRunnerExp.class.getResourceAsStream(((JPanel) iDialogMain).getName() + ".js");
							}
							System.out.println(is);
							String script = IOUtil.getStreamContentAsString(is, "UTF-8");
							ScriptRunnerExp scriptRunnerExp = new ScriptRunnerExp(frameUI);
							scriptRunnerExp.eval(script);
							MyPanel.isTest = false;
							// System.out.println(new JVMEnv().getMemoryInfo());
							// System.out
							// .println("...........................................................");
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

	public void clear() {
		jPanel1 = null;
		jPanel2 = null;
		panelTrans = null;
		btnAdd = null;
		btnEdit = null;
		btnExit = null;
		btnRefresh = null;
		btnRemove = null;
		btnReset = null;
		iDialogMain = null;
		hashMap = null;
		sizeHeight = -1;
		weight = -1;
		System.gc();
	}
}
