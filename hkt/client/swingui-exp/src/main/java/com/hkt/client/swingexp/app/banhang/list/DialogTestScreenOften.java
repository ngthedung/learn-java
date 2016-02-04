package com.hkt.client.swingexp.app.banhang.list;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import com.hkt.client.swing.robot.console.JVMEnv;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.component.PanelBackground;
import com.hkt.client.swingexp.app.component.PanelTrans;
import com.hkt.client.swingexp.app.component.PanelTrans100;
import com.hkt.client.swingexp.app.core.FrameUI;
import com.hkt.client.swingexp.app.core.HKTFile;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.app.test.ScriptRunnerExp;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.util.IOUtil;


@SuppressWarnings("serial")
public class DialogTestScreenOften extends JDialog {
	private JButton btnBHChuan, btnDatcho, btnTTTien, btnTTSP, btnKM, btnHD, btnTTDiem, btnMoBQ, btnTTTT, btnKH;
	private JPanel jPanel1, jPanel2;
	protected boolean full;
	@SuppressWarnings("unused")
	private int sizeHeight = 172;
	@SuppressWarnings("unused")
	private int width = 600;

	public DialogTestScreenOften(boolean full, int width, int height) {
		setUndecorated(true);
        init();
		ImageIcon icon = new ImageIcon(HomeScreen.class.getResource("icon/icon.png"));
		Image im = icon.getImage();
		setIconImage(im);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
	}
	
		private void init() {
		btnBHChuan = new javax.swing.JButton();
		btnBHChuan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		btnDatcho = new javax.swing.JButton();
		btnDatcho.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		btnTTTien = new javax.swing.JButton();
		btnTTTien.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		btnKM = new javax.swing.JButton();
		btnKM.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		btnTTSP = new javax.swing.JButton();
		btnTTSP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		btnHD = new javax.swing.JButton();
		btnHD.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		btnTTSP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		btnTTDiem = new javax.swing.JButton();
		btnTTDiem.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		btnMoBQ = new javax.swing.JButton();
		btnMoBQ.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		btnTTTT = new javax.swing.JButton();
		btnTTTT.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		btnKH = new javax.swing.JButton();
		btnKH.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new GridLayout(1, 0));

		btnBHChuan.setText("Bán hàng chuẩn");
		btnBHChuan.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				runBHChuan();

			}
		});

		btnDatcho.setText("Đặt chỗ, gộp/tách bàn");
		btnDatcho.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				runDatcho();

			}
		});

		btnTTTien.setText("Bán hàng TT lẻ bằng tiền");
		btnTTTien.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				runTTTien();

			}
		});

		btnKM.setText("KM, đổi giá SP");
		btnKM.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				runKhuyenMai();

			}

		});

		btnTTSP.setText("Bán hàng TT lẻ bằng SP");
		btnTTSP.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				runTTSP();

			}
		});

		btnHD.setText("Sửa hóa đơn TT");
		btnHD.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				runHD();

			}
		});

		btnTTDiem.setText("Bán hàng TT bằng Điểm");
		btnTTDiem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				runTTDiem();

			}
		});

		btnMoBQ.setText("Mở KV/Bàn, thêm nhanh SP");

		btnTTTT.setText("Bán hàng TT bằng tiền trả trước");
		btnTTTT.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				runTTTT();

			}
		});

		btnKH.setText("Tìm, tạo mới KH");
		btnKH.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				runKH();

			}
		});

		jPanel1 = new PanelTrans();
		jPanel2 = new PanelBackground();
		jPanel2.setBackground(new Color(255, 255, 255));
		jPanel1.setOpaque(false);

		GroupLayout layout = new GroupLayout(jPanel1);
		jPanel1.setLayout(layout);

		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addComponent(btnTTTT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(btnBHChuan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(btnTTTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(btnTTSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(btnTTDiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE))
						.addGap(35, 35, 35)
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
										.addComponent(btnMoBQ, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 183,
												Short.MAX_VALUE)
										.addComponent(btnDatcho, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnKM, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnHD, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(btnBHChuan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(btnDatcho, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(16, 16, 16)
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addComponent(btnTTTien, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
										.addComponent(btnKM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE))
						.addGap(18, 18, 18)
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(btnTTSP, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(btnHD, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addComponent(btnMoBQ, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
										.addComponent(btnTTTT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE))
						.addGap(18, 18, 18)
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addComponent(btnTTDiem, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
										.addComponent(btnKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				jPanel2Layout.createSequentialGroup().addContainerGap()
						.addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				jPanel2Layout.createSequentialGroup().addContainerGap()
						.addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()));

		if (full) {
			getContentPane().add(jPanel2);
		} else {
			setUndecorated(true);
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
		                                  .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
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
		                      .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
		                      .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
		                      .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
		                          javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
		              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		              .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)));
		    }
		    pack();
		addKeyBindings(jPanel1);
	}


	@Override
	public void setTitle(String title) {
		jPanel1.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createBevelBorder(BevelBorder.RAISED, null, new java.awt.Color(204, 204, 204), null, null), title,
				TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(126, 0, 0)));
		super.setTitle(title);
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

	public void runBHChuan() {
		dispose();
		Thread thread = new Thread() {
			public void run() {
				PanelTestAll.runAll = false;
				MyPanel.isTest = true;
				System.out.println("Bắt đầu chạy test giao diện " + "ScreenOften_OpenTableArea");
				System.out.println(new JVMEnv().getMemoryInfo());
				FrameUI frameUI = FrameUI.getInstance();
				try {
					File file = HKTFile.getFile("test", "ScreenOften_OpenTableArea" + ".js");
					InputStream is;
					if (file.exists()) {
						is = new FileInputStream(file);
					} else {
						is = ScriptRunnerExp.class.getResourceAsStream("ScreenOften_OpenTableArea" + ".js");
					}
					String script = IOUtil.getStreamContentAsString(is, "UTF-8");
					ScriptRunnerExp scriptRunnerExp = new ScriptRunnerExp(frameUI);
					// System.out.println(script);
					scriptRunnerExp.eval(script);
					MyPanel.isTest = false;
					System.out.println(new JVMEnv().getMemoryInfo());
					System.out.println("...........................................................");
				} catch (Exception ex) {
					ex.printStackTrace();
					frameUI.destroy();
					frameUI = null;
					System.gc();
					MyPanel.isTest = false;
				} finally {

				}
			}
		};
		thread.start();
	}

	public void runTTTien() {
		dispose();
		Thread thread = new Thread() {
			public void run() {
				PanelTestAll.runAll = false;
				MyPanel.isTest = true;
				System.out.println("Bắt đầu chạy test giao diện " + "ScreenOften_PaymentbyMoney");
				System.out.println(new JVMEnv().getMemoryInfo());
				FrameUI frameUI = FrameUI.getInstance();
				try {
					File file = HKTFile.getFile("test", "ScreenOften_PaymentbyMoney" + ".js");
					InputStream is;
					if (file.exists()) {
						is = new FileInputStream(file);
					} else {
						is = ScriptRunnerExp.class.getResourceAsStream("ScreenOften_PaymentbyMoney" + ".js");
					}
					String script = IOUtil.getStreamContentAsString(is, "UTF-8");
					ScriptRunnerExp scriptRunnerExp = new ScriptRunnerExp(frameUI);
					// System.out.println(script);
					scriptRunnerExp.eval(script);
					MyPanel.isTest = false;
					System.out.println(new JVMEnv().getMemoryInfo());
					System.out.println("...........................................................");
				} catch (Exception ex) {
					ex.printStackTrace();
					frameUI.destroy();
					frameUI = null;
					System.gc();
					MyPanel.isTest = false;
				} finally {

				}
			}
		};
		thread.start();
	}

	public void runDatcho() {
		dispose();
		Thread thread = new Thread() {
			public void run() {
				PanelTestAll.runAll = false;
				MyPanel.isTest = true;
				System.out.println("Bắt đầu chạy test giao diện " + "ScreenOften_SetGrossTable");
				System.out.println(new JVMEnv().getMemoryInfo());
				FrameUI frameUI = FrameUI.getInstance();
				try {
					File file = HKTFile.getFile("test", "ScreenOften_SetGrossTable" + ".js");
					InputStream is;
					if (file.exists()) {
						is = new FileInputStream(file);
					} else {
						is = ScriptRunnerExp.class.getResourceAsStream("ScreenOften_SetGrossTable" + ".js");
					}
					String script = IOUtil.getStreamContentAsString(is, "UTF-8");
					ScriptRunnerExp scriptRunnerExp = new ScriptRunnerExp(frameUI);
					// System.out.println(script);
					scriptRunnerExp.eval(script);
					MyPanel.isTest = false;
					System.out.println(new JVMEnv().getMemoryInfo());
					System.out.println("...........................................................");
				} catch (Exception ex) {
					ex.printStackTrace();
					frameUI.destroy();
					frameUI = null;
					System.gc();
					MyPanel.isTest = false;
				} finally {

				}
			}
		};
		thread.start();
	}

	public void runTTSP() {
		dispose();
		Thread thread = new Thread() {
			public void run() {
				PanelTestAll.runAll = false;
				MyPanel.isTest = true;
				System.out.println("Bắt đầu chạy test giao diện " + "ScreenOften_PaymenOdd");
				System.out.println(new JVMEnv().getMemoryInfo());
				FrameUI frameUI = FrameUI.getInstance();
				try {
					File file = HKTFile.getFile("test", "ScreenOften_PaymenOdd" + ".js");
					InputStream is;
					if (file.exists()) {
						is = new FileInputStream(file);
					} else {
						is = ScriptRunnerExp.class.getResourceAsStream("ScreenOften_PaymenOdd" + ".js");
					}
					String script = IOUtil.getStreamContentAsString(is, "UTF-8");
					ScriptRunnerExp scriptRunnerExp = new ScriptRunnerExp(frameUI);
					// System.out.println(script);
					scriptRunnerExp.eval(script);
					MyPanel.isTest = false;
					System.out.println(new JVMEnv().getMemoryInfo());
					System.out.println("...........................................................");
				} catch (Exception ex) {
					ex.printStackTrace();
					frameUI.destroy();
					frameUI = null;
					System.gc();
					MyPanel.isTest = false;
				} finally {

				}
			}
		};
		thread.start();
	}

	public void runTTDiem() {
		dispose();
		Thread thread = new Thread() {
			public void run() {
				PanelTestAll.runAll = false;
				MyPanel.isTest = true;
				System.out.println("Bắt đầu chạy test giao diện " + "ScreenOften_PaymentbyPoint");
				System.out.println(new JVMEnv().getMemoryInfo());
				FrameUI frameUI = FrameUI.getInstance();
				try {
					File file = HKTFile.getFile("test", "ScreenOften_PaymentbyPoint" + ".js");
					InputStream is;
					if (file.exists()) {
						is = new FileInputStream(file);
					} else {
						is = ScriptRunnerExp.class.getResourceAsStream("ScreenOften_PaymentbyPoint" + ".js");
					}
					String script = IOUtil.getStreamContentAsString(is, "UTF-8");
					ScriptRunnerExp scriptRunnerExp = new ScriptRunnerExp(frameUI);
					// System.out.println(script);
					scriptRunnerExp.eval(script);
					MyPanel.isTest = false;
					System.out.println(new JVMEnv().getMemoryInfo());
					System.out.println("...........................................................");
				} catch (Exception ex) {
					ex.printStackTrace();
					frameUI.destroy();
					frameUI = null;
					System.gc();
					MyPanel.isTest = false;
				} finally {

				}
			}
		};
		thread.start();
	}

	public void runTTTT() {
		dispose();
		Thread thread = new Thread() {
			public void run() {
				PanelTestAll.runAll = false;
				MyPanel.isTest = true;
				System.out.println("Bắt đầu chạy test giao diện " + "ScreenOften_CreditTransaction");
				System.out.println(new JVMEnv().getMemoryInfo());
				FrameUI frameUI = FrameUI.getInstance();
				try {
					File file = HKTFile.getFile("test", "ScreenOften_CreditTransaction" + ".js");
					InputStream is;
					if (file.exists()) {
						is = new FileInputStream(file);
					} else {
						is = ScriptRunnerExp.class.getResourceAsStream("ScreenOften_CreditTransaction" + ".js");
					}
					String script = IOUtil.getStreamContentAsString(is, "UTF-8");
					ScriptRunnerExp scriptRunnerExp = new ScriptRunnerExp(frameUI);
					// System.out.println(script);
					scriptRunnerExp.eval(script);
					MyPanel.isTest = false;
					System.out.println(new JVMEnv().getMemoryInfo());
					System.out.println("...........................................................");
				} catch (Exception ex) {
					ex.printStackTrace();
					frameUI.destroy();
					frameUI = null;
					System.gc();
					MyPanel.isTest = false;
				} finally {

				}
			}
		};
		thread.start();
	}

	public void runHD() {
		dispose();
		Thread thread = new Thread() {
			public void run() {
				PanelTestAll.runAll = false;
				MyPanel.isTest = true;
				System.out.println("Bắt đầu chạy test giao diện " + "ScreenOften_EditInvoice");
				System.out.println(new JVMEnv().getMemoryInfo());
				FrameUI frameUI = FrameUI.getInstance();
				try {
					File file = HKTFile.getFile("test", "ScreenOften_EditInvoice" + ".js");
					InputStream is;
					if (file.exists()) {
						is = new FileInputStream(file);
					} else {
						is = ScriptRunnerExp.class.getResourceAsStream("ScreenOften_EditInvoice" + ".js");
					}
					String script = IOUtil.getStreamContentAsString(is, "UTF-8");
					ScriptRunnerExp scriptRunnerExp = new ScriptRunnerExp(frameUI);
					// System.out.println(script);
					scriptRunnerExp.eval(script);
					MyPanel.isTest = false;
					System.out.println(new JVMEnv().getMemoryInfo());
					System.out.println("...........................................................");
				} catch (Exception ex) {
					ex.printStackTrace();
					frameUI.destroy();
					frameUI = null;
					System.gc();
					MyPanel.isTest = false;
				} finally {

				}
			}
		};
		thread.start();
	}

	public void runKH() {
		dispose();
		Thread thread = new Thread() {
			public void run() {
				PanelTestAll.runAll = false;
				MyPanel.isTest = true;
				System.out.println("Bắt đầu chạy test giao diện " + "ScreenOften_AddCustomer");
				System.out.println(new JVMEnv().getMemoryInfo());
				FrameUI frameUI = FrameUI.getInstance();
				try {
					File file = HKTFile.getFile("test", "ScreenOften_AddCustomer" + ".js");
					InputStream is;
					if (file.exists()) {
						is = new FileInputStream(file);
					} else {
						is = ScriptRunnerExp.class.getResourceAsStream("ScreenOften_AddCustomer" + ".js");
					}
					String script = IOUtil.getStreamContentAsString(is, "UTF-8");
					ScriptRunnerExp scriptRunnerExp = new ScriptRunnerExp(frameUI);
					// System.out.println(script);
					scriptRunnerExp.eval(script);
					MyPanel.isTest = false;
					System.out.println(new JVMEnv().getMemoryInfo());
					System.out.println("...........................................................");
				} catch (Exception ex) {
					ex.printStackTrace();
					frameUI.destroy();
					frameUI = null;
					System.gc();
					MyPanel.isTest = false;
				} finally {

				}
			}
		};
		thread.start();
	}

	public void runKhuyenMai() {
		dispose();
		Thread thread = new Thread() {
			public void run() {
				PanelTestAll.runAll = false;
				MyPanel.isTest = true;
				System.out.println("Bắt đầu chạy test giao diện " + "ScreenOften_PromotionAndChangePrice");
				System.out.println(new JVMEnv().getMemoryInfo());
				FrameUI frameUI = FrameUI.getInstance();
				try {
					File file = HKTFile.getFile("test", "ScreenOften_PromotionAndChangePrice" + ".js");
					InputStream is;
					if (file.exists()) {
						is = new FileInputStream(file);
					} else {
						is = ScriptRunnerExp.class.getResourceAsStream("ScreenOften_PromotionAndChangePrice" + ".js");
					}
					String script = IOUtil.getStreamContentAsString(is, "UTF-8");
					ScriptRunnerExp scriptRunnerExp = new ScriptRunnerExp(frameUI);
					// System.out.println(script);
					scriptRunnerExp.eval(script);
					MyPanel.isTest = false;
					System.out.println(new JVMEnv().getMemoryInfo());
					System.out.println("...........................................................");
				} catch (Exception ex) {
					ex.printStackTrace();
					frameUI.destroy();
					frameUI = null;
					System.gc();
					MyPanel.isTest = false;
				} finally {

				}
			}
		};
		thread.start();
	}
}