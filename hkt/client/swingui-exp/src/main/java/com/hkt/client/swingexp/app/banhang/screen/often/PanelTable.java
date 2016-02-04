package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.hkt.client.swingexp.app.banhang.list.DialogTestScreenOften;
import com.hkt.client.swingexp.app.banhang.screen.market.DialogSuperMarket;
import com.hkt.client.swingexp.app.banhang.screen.pos.PanelScreenSaleLocationPos;
import com.hkt.client.swingexp.app.banhang.screen.pos.DialogScreenOftenPos;
import com.hkt.client.swingexp.app.component.PanelBackground;
import com.hkt.client.swingexp.app.core.DialogList;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.IScreenSales;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDialog;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDown;
import com.hkt.client.swingexp.app.khuvucbanhang.PanelLocalTable;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.LocationAttribute;
import com.hkt.module.restaurant.entity.Table;
import com.hkt.module.restaurant.entity.Table.Status;

/*
 * author: longnt
 */
@SuppressWarnings("serial")
public class PanelTable extends JPanel {

	private List<TableEat> listTableGross;
	private List<Location> listLocations;
	private TableEat tableDesign;
	private IScreenSales screenSales;
	public static String permission;

	public PanelTable(IScreenSales screenSales) {
		initComponents();
		this.screenSales = screenSales;

//		Profile p = AccountModelManager.getInstance().getProfileConfigAdmin();
//		if (p.get("AllServer") != null) {
//
//			Timer timer = new Timer(3000, new ActionListener() {
//
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					// long startTime = System.currentTimeMillis();
//					Thread t = new Thread() {
//						public void run() {
//							try {
//								
//								// long endTime = System.currentTimeMillis();
//								// NumberFormat formatter = new DecimalFormat("#0.00000");
//								// String time = formatter.format((endTime - startTime) /
//								// 1000d);
//								// System.out.println("TIME : " + time);
//							} catch (Exception e2) {
//							}
//						};
//					};
//					t.start();
//				}
//			});
//			timer.start();
//		}

	}

	private void initComponents() {

		panelTable = new PanelBackground();
		btnTableGross = new javax.swing.JButton();
		btnMove = new javax.swing.JButton();
		btnOpenTableArea = new javax.swing.JButton();
		btnOpenTableArea.setName("ScreenOften_OpenTableArea");
		btnDelete = new javax.swing.JButton();
		btnManagerSetTable = new javax.swing.JButton();
		btnSetTable = new javax.swing.JButton();
		btnThietLapBan = new javax.swing.JButton();
		jButton5 = new javax.swing.JButton();
		scrollPane = new javax.swing.JScrollPane();
		jPanel1 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		buttonFree = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		buttonNoFree = new javax.swing.JLabel();

		// addComponentListener(new java.awt.event.ComponentAdapter() {
		// public void componentResized(java.awt.event.ComponentEvent evt) {
		// if (flag) {
		// try {
		// refeshGui();
		// flag = false;
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// }
		// });

		panelTable.setBackground(new java.awt.Color(153, 153, 153));

		btnTableGross.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		btnTableGross.setText("<html><p align = 'center'>Gộp " + DialogConfig.Table + "</p> </html>");
		btnTableGross.setName("btnTableGross");
		btnTableGross.addMouseListener(new MouseEventClickButtonDown(null, null, null));
		btnTableGross.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnTableGross.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (tableDesign.getTable().getStatus() != Table.Status.TableGross) {
					try {
						InvoiceDetail invoiceDetail = AccountingModelManager.getInstance().saveInvoice(
						    ((ScreenOften) screenSales).getTableModle().getInvoiceDetail());
						System.out.println(invoiceDetail);
						long id = invoiceDetail.getId();
						tableDesign.getTable().setInvoiceCode(String.valueOf(id));
						RestaurantModelManager.getInstance().saveTable(tableDesign.getTable());
					} catch (Exception e) {
						e.printStackTrace();
					}

					PanelTableGross panelTableGross = new PanelTableGross();
					panelTableGross.setName("panelTableGross");
					DialogResto dialog = new DialogResto(panelTableGross, false, 950, 500);
					dialog.setName("dlTableGross");
					dialog.dispose();
					dialog.setUndecorated(true);
					dialog.setTitle("Gộp " + DialogConfig.Table + "");
					dialog.setLocationRelativeTo(null);
					dialog.setModal(true);
					dialog.setVisible(true);
					if (panelTableGross.isSave()) {
						try {
							refeshGui();
							ScreenOften.getInstance().reset();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					// }
				} else {
					if (tableDesign.getTable().getReservations() != null && tableDesign.getTable().getReservations().size() > 0) {

						DialogSplitTable dialog = new DialogSplitTable(tableDesign.getTable());
						DialogResto dialog1 = new DialogResto(dialog, false, 0, 110);
						dialog1.setTitle("Tách " + DialogConfig.Table + "");
						dialog1.setBtnSave(false);
						// dialog1.setBtnExit(false);
						dialog1.setLocationRelativeTo(null);
						dialog1.setModal(true);
						dialog1.setVisible(true);
						if (dialog.isSuccsess()) {
							try {
								if (tableDesign.getTable().getReservations() == null
								    || tableDesign.getTable().getReservations().size() == 0) {
									tableDesign.getTable().setStatus(Table.Status.TableBusy);
									tableDesign.getTable().setReservations(null);
									RestaurantModelManager.getInstance().saveTable(tableDesign.getTable());
								}
								refeshGui();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		});

		btnMove.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		btnMove.setName("btnMove");
		btnMove.setText("<html><p align = 'center'>Chuyển " + DialogConfig.Table + "</p> </html>");
		btnMove.addMouseListener(new MouseEventClickButtonDown(null, null, null));
		btnMove.setMargin(new java.awt.Insets(0, 0, 0, 4));
		btnMove.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Table table = tableDesign.getTable();
				try {
					InvoiceDetail invoiceDetail = AccountingModelManager.getInstance().saveInvoice(
					    ((ScreenOften) screenSales).getTableModle().getInvoiceDetail());
					table.setInvoiceCode(String.valueOf(invoiceDetail.getId()));
					table = RestaurantModelManager.getInstance().saveTable(table);
				} catch (Exception e) {
				}

				PanelMoveTable panelMoveTable = new PanelMoveTable(table);
				panelMoveTable.setName("panelMoveTable");
				if (Toolkit.getDefaultToolkit().getScreenSize().width <= 1024) {
					DialogResto dialog = new DialogResto(panelMoveTable, false, 800, 500);
					dialog.setName("dlMoveTable");
					dialog.setTitle("Chuyển " + DialogConfig.Table + "");
					dialog.dispose();
					dialog.setUndecorated(true);
					dialog.setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit()
					    .getScreenSize().height));
					dialog.setLocationRelativeTo(null);
					dialog.setModal(true);
					dialog.setVisible(true);
				} else {
					DialogResto dialog = new DialogResto(panelMoveTable, false, 1050, 550);
					dialog.setName("dlMoveTable");
					dialog.setTitle("Chuyển " + DialogConfig.Table + "");
					dialog.dispose();
					dialog.setUndecorated(true);
					dialog.setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit()
					    .getScreenSize().height));
					dialog.setLocationRelativeTo(null);
					dialog.setModal(true);
					dialog.setVisible(true);
				}
				if (panelMoveTable.isSave()) {
					try {
						refeshGui();
						ScreenOften.getInstance().reset();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

		btnOpenTableArea.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		btnOpenTableArea.setText("<html><p align = 'center'>Mở KV " + DialogConfig.Table + "</p> </html>");
		PanelOpenTableArea.permission = (UIConfigModelManager.getInstance().getPlainText(PanelLocalTable.permission));
		btnOpenTableArea.addMouseListener(new MouseEventClickButtonDown(null, null, null));
		btnOpenTableArea.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnOpenTableArea.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				PanelOpenTableArea panel = new PanelOpenTableArea();
				panel.setName("ScreenOften_OpenTableArea");
				DialogResto dialog = new DialogResto(panel, false, 0, 280);
				dialog.setName("dlScreenOften_OpenTableArea");
				dialog.setTitle("Mở khu vực " + DialogConfig.Table + "");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
			}
		});

		btnDelete.setFont(new java.awt.Font("Tahoma", 1, 11));
		btnDelete.setText("<html><p align = 'center'>Xóa " + DialogConfig.Table + " khu vực</p> </html>");
		PanelDeleteTableLocation.permission = (UIConfigModelManager.getInstance().getPlainText(PanelLocalTable.permission));
		btnDelete.addMouseListener(new MouseEventClickButtonDown(null, null, null));
		btnDelete.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnDelete.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (tableDesign.getTable() != null) {
					PanelDeleteTableLocation panel = new PanelDeleteTableLocation(tableDesign.getTable());
					DialogResto dialog = new DialogResto(panel, false, 0, 240);
					dialog.setTitle("Xóa " + DialogConfig.Table + " khu vực");
					dialog.setLocationRelativeTo(null);
					dialog.setModal(true);
					dialog.setVisible(true);
					if (panel.isSave()) {
						try {
							refeshGui();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else
					JOptionPane.showMessageDialog(null, "Vui lòng chọn " + DialogConfig.Table + " cần xóa");
			}
		});

		btnManagerSetTable.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		btnManagerSetTable.setText("<html><p align = 'center'>QL đặt " + DialogConfig.Table + "</p> </html>");
		btnManagerSetTable.setName("btnManagerSetTable");
		btnManagerSetTable.addMouseListener(new MouseEventClickButtonDown(null, null, null));
		btnManagerSetTable.setMargin(new java.awt.Insets(0, 4, 0, 0));
		btnManagerSetTable.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
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
				dialog.setTitle("Quản lý đặt " + DialogConfig.Table + "");
				dialog.setModal(true);
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
			}
		});
		btnSetTable.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		btnSetTable.setText("<html><p align = 'center'>Đặt " + DialogConfig.Table + "</p> </html>");
		btnSetTable.addMouseListener(new MouseEventClickButtonDown(null, null, null));
		btnSetTable.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnSetTable.setName("ScreenOften_SetGrossTable");
		btnSetTable.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (tableDesign.getTable().getStatus().equals(Table.Status.TableSet)) {
					tableDesign.getTable().setStatus(Table.Status.TableFree);
					JOptionPane.showMessageDialog(null, "Delete successful", "", JOptionPane.PLAIN_MESSAGE);
					try {
						RestaurantModelManager.getInstance().saveTable(tableDesign.getTable());
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
					try {
						PanelReserveTable panel = new PanelReserveTable(tableDesign);
						panel.setName("ScreenOften_SetGrossTable");
						DialogResto dialog = new DialogResto(panel, false, 0, 350);
						dialog.setName("dlScreenOften_SetGrossTable");
						dialog.setTitle("Đặt trước " + DialogConfig.Table + "");
						dialog.setLocationRelativeTo(null);
						dialog.setModal(true);
						dialog.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

		btnThietLapBan.setFont(new java.awt.Font("Tahoma", 1, 11));
		btnThietLapBan.setText("Cập nhật");
		btnThietLapBan.addMouseListener(new MouseEventClickButtonDialog(null, null, null));
		btnThietLapBan.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btnThietLapBan.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				for (TableEat tableEat : listTableGross) {
					tableEat.setColorTable();
				}
			}
		});

		jButton5.setFont(new java.awt.Font("Tahoma", 1, 11));
		jButton5.setText("<html><p align = 'center'>Bán nhanh</p> </html>");
		jButton5.addMouseListener(new MouseEventClickButtonDialog(null, null, null));
		jButton5.setMargin(new java.awt.Insets(0, 0, 0, 0));
		jButton5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					Profile profile = AccountModelManager.getInstance().getProfileConfigAdmin();
					if (profile.get(DialogConfig.Maket).toString().equals("true")
					    || profile.get(DialogConfig.Shop).toString().equals("true")) {
						DialogSuperMarket dialogSuperMarket = DialogSuperMarket.getInstance();
						dialogSuperMarket.loadData();
						dialogSuperMarket.setVisible(true);
					} else {
						if (profile.get(DialogConfig.GDPos).toString().equals("true")) {

							DialogScreenOftenPos dialog = DialogScreenOftenPos.getInstance();
							dialog.setTable(PanelScreenSaleLocationPos.getInstance().getComponentTableOther());
							if (dialog.hashMapProducts != null) {
								dialog.resetGui();
								dialog.loadData();
							}
							dialog.setVisible(true);
						} else {
							ScreenOften dialog = ScreenOften.getInstance();
							TableEat tableEat = new TableEat(RestaurantModelManager.getInstance().getTablePaymentAfter());
							dialog.setTable(tableEat);

						}
					}
				} catch (Exception e2) {
					e2.printStackTrace();
					ScreenOften dialog = ScreenOften.getInstance();
					dialog.loadData();
					TableEat tableEat = new TableEat(RestaurantModelManager.getInstance().getTablePaymentAfter());
					dialog.setTable(tableEat);
					dialog.setEditorGuiBN(false);
					dialog.setVisible(true);
				}
			}
		});

		jPanel1.setOpaque(false);

		jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
		jLabel1.setText("Trống:");

		buttonFree.setFont(new java.awt.Font("Tahoma", 1, 11));

		jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11));
		jLabel3.setText("Có khách:");

		buttonNoFree.setFont(new java.awt.Font("Tahoma", 1, 11));

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        jPanel1Layout
		            .createSequentialGroup()
		            .addContainerGap()
		            .addComponent(jLabel1)
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		            .addComponent(buttonFree, javax.swing.GroupLayout.PREFERRED_SIZE, 37,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(29, 29, 29)
		            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 70,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		            .addComponent(buttonNoFree, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
		            .addContainerGap()));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        jPanel1Layout
		            .createSequentialGroup()
		            .addGroup(
		                jPanel1Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(buttonNoFree, javax.swing.GroupLayout.PREFERRED_SIZE, 14,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(jLabel3)
		                    .addComponent(jLabel1)
		                    .addComponent(buttonFree, javax.swing.GroupLayout.PREFERRED_SIZE, 14,
		                        javax.swing.GroupLayout.PREFERRED_SIZE))
		            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		javax.swing.GroupLayout panelTableLayout = new javax.swing.GroupLayout(panelTable);
		panelTable.setLayout(panelTableLayout);
		panelTableLayout.setHorizontalGroup(panelTableLayout
		    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        panelTableLayout
		            .createSequentialGroup()
		            .addGroup(
		                panelTableLayout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addGroup(
		                        panelTableLayout
		                            .createSequentialGroup()
		                            .addContainerGap()
		                            .addGroup(
		                                panelTableLayout
		                                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                                    .addComponent(btnManagerSetTable, javax.swing.GroupLayout.DEFAULT_SIZE,
		                                        javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
		                                    .addComponent(btnOpenTableArea, javax.swing.GroupLayout.DEFAULT_SIZE, 10,
		                                        Short.MAX_VALUE)
		                                    .addComponent(btnTableGross, javax.swing.GroupLayout.Alignment.TRAILING,
		                                        javax.swing.GroupLayout.DEFAULT_SIZE,
		                                        javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
		                                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE,
		                                        javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
		                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                            .addGroup(
		                                panelTableLayout
		                                    .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
		                                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE,
		                                        javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
		                                    .addComponent(btnMove, javax.swing.GroupLayout.DEFAULT_SIZE,
		                                        javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
		                                    .addComponent(btnSetTable, javax.swing.GroupLayout.Alignment.LEADING,
		                                        javax.swing.GroupLayout.DEFAULT_SIZE,
		                                        javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
		                                    .addComponent(btnThietLapBan, javax.swing.GroupLayout.DEFAULT_SIZE, 10,
		                                        Short.MAX_VALUE)))
		                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGap(0, 0, 0))
		    .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE,
		        Short.MAX_VALUE));
		panelTableLayout.setVerticalGroup(panelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        javax.swing.GroupLayout.Alignment.TRAILING,
		        panelTableLayout
		            .createSequentialGroup()
		            .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE,
		                Short.MAX_VALUE)
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 13,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		            .addGroup(
		                panelTableLayout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
		                    .addComponent(btnMove, javax.swing.GroupLayout.Alignment.LEADING,
		                        javax.swing.GroupLayout.PREFERRED_SIZE, 24, Short.MAX_VALUE)
		                    .addComponent(btnTableGross, javax.swing.GroupLayout.Alignment.LEADING,
		                        javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		            .addGroup(
		                panelTableLayout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
		                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 24, Short.MAX_VALUE)
		                    .addComponent(btnOpenTableArea, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
		                        javax.swing.GroupLayout.PREFERRED_SIZE))
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		            .addGroup(
		                panelTableLayout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(btnSetTable, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(btnManagerSetTable, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
		                        javax.swing.GroupLayout.PREFERRED_SIZE))
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		            .addGroup(
		                panelTableLayout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(btnThietLapBan, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
		                        javax.swing.GroupLayout.PREFERRED_SIZE))));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
		    panelTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
		    panelTable, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE,
		    javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
	}

	private javax.swing.JButton btnDelete;
	private javax.swing.JButton btnManagerSetTable;
	private javax.swing.JButton btnMove;
	private javax.swing.JButton btnOpenTableArea;
	private javax.swing.JButton btnSetTable;
	private javax.swing.JButton btnTableGross;
	private javax.swing.JButton btnThietLapBan;
	private javax.swing.JLabel buttonFree;
	private javax.swing.JLabel buttonNoFree;
	private javax.swing.JButton jButton5;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel panelTable;
	private javax.swing.JScrollPane scrollPane;
	protected boolean flagSetTable;
	protected boolean flagTableGross;
	private Color colorPressed = Color.GRAY;

	public void canceEdit(boolean edit) {
		List<Component> list = getAllComponents(this);
		for (Component c : list) {
			c.setEnabled(edit);
		}
	}

	private List<Component> getAllComponents(final Container c) {
		Component[] comps = c.getComponents();
		List<Component> compList = new ArrayList<Component>();
		for (Component comp : comps) {
			compList.add(comp);
			if (comp instanceof Container) {
				compList.addAll(getAllComponents((Container) comp));
			}
		}
		return compList;
	}

	private void addActionMouseTable(TableEat tableEat) {

		if (tableEat.getMouseListeners().length < 1) {
			tableEat.addMouseListener(new MouseAdapter() {

				boolean flagSelect = false;

				@Override
				public void mouseExited(MouseEvent e) {
					flagSelect = false;
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					TableEat productDesign = (TableEat) e.getSource();
					if (e.getButton() == 3) {
						PanelNoteTable noteTable = new PanelNoteTable(productDesign.getTable());
						DialogResto dialog = new DialogResto(noteTable, false, 0, 314);
						dialog.setTitle("Ghi chú phòng " + productDesign.getTable().getName());
						dialog.setLocationRelativeTo(null);
						dialog.setModal(true);
						dialog.setVisible(true);
					} else {
						if (!productDesign.isEnabled()) {
							return;
						}
						tableDesign = productDesign;
						productDesign.setColor(colorPressed);
						if (!flagSelect) {
							flagSelect = true;
							screenSales.setTable(productDesign);
							viewNumberGuest();
						}
					}

				}

				@Override
				public void mousePressed(MouseEvent e) {
					TableEat productDesign = (TableEat) e.getSource();
					if (e.getButton() == 3) {
						PanelNoteTable noteTable = new PanelNoteTable(productDesign.getTable());
						DialogResto dialog = new DialogResto(noteTable, false, 0, 314);
						dialog.setTitle("Ghi chú phòng " + productDesign.getTable().getName());
						dialog.setLocationRelativeTo(null);
						dialog.setModal(true);
						dialog.setVisible(true);
					} else {
						if (!productDesign.isEnabled()) {
							return;
						}
						if (tableDesign != null) {
							tableDesign.setColorTable();
						}
						tableDesign = productDesign;
						if (!flagSelect) {
							flagSelect = true;
							productDesign.setColor(colorPressed);
							screenSales.setTable(productDesign);
							viewNumberGuest();
						} else {
							productDesign.setColor(colorPressed);
						}
					}
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					if (!tableDesign.isEnabled()) {
						return;
					}
					if (tableDesign != null) {
						tableDesign.setColor(colorPressed);
						if (tableDesign.getStatus() == Status.TableGross) {
							btnTableGross.setText("<html><p align = 'center'>Tách " + DialogConfig.Table + "</p> </html>");
							flagTableGross = true;
						} else {
							btnTableGross.setText("<html><p align = 'center'>Gộp " + DialogConfig.Table + "</p> </html>");
							flagTableGross = false;
						}
						if (tableDesign.getStatus() == Status.TableSet) {
							btnSetTable.setText("<html><p align = 'center'>Hủy đặt </p> <p align = 'center'>" + DialogConfig.Table
							    + "</p></html>");
							flagSetTable = true;
						} else {
							btnSetTable.setText("<html><p align = 'center'>Đặt " + DialogConfig.Table + "</p> </html>");
							flagSetTable = false;
						}
					}
				}
			});
		}
	}

	public void refeshGui() throws Exception {
		listLocations = RestaurantModelManager.getInstance().getLocations();
		for (int i = 0; i < listLocations.size();) {
			boolean dele = true;
			if (listLocations.get(i).getLocationAttributes() != null) {
				for (LocationAttribute at : listLocations.get(i).getLocationAttributes()) {
					if(at.getName().equals("Employee")&&at.getValue().equals(ManagerAuthenticate.getInstance().getLoginId())){
						dele=false;
					}
				}
			}
			if(dele){
				listLocations.remove(i);
			}else {
				i++;
			}
		}
		if(listLocations.isEmpty()){
			listLocations = RestaurantModelManager.getInstance().getLocations();
		}
		listTableGross = new ArrayList<TableEat>();
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEADING));
		panel.setPreferredSize(new Dimension(scrollPane.getWidth() - 20, scrollPane.getHeight() - 5));
		panel.setBackground(Color.white);
		int size = 0;
		for (int i = 0; i < listLocations.size(); i++) {
			JPanel panel2 = new JPanel(new BorderLayout());
			final JLabel label3 = new JLabel("-");
			label3.setOpaque(true);
			label3.setBackground(Color.PINK);
			label3.setPreferredSize(new Dimension(10, 25));
			panel2.add(label3, BorderLayout.WEST);
			JLabel label = new JLabel(" " + listLocations.get(i).toString());
			panel2.setPreferredSize(new Dimension(250, 25));
			panel2.setOpaque(false);
			panel2.add(label, BorderLayout.CENTER);
			label.setFont(new Font("Tahoma", Font.BOLD, 14));
			panel.add(panel2);
			List<Table> list1 = RestaurantModelManager.getInstance().findTableByLocation(listLocations.get(i).getCode());
			int k;
			if (list1.size() % 5 > 0) {
				k = list1.size() / 5 + 1;
			} else {
				k = list1.size() / 5;
			}

			final JPanel panel1 = new JPanel(new GridLayout(k, 5, 5, 5));
			panel1.setOpaque(false);
			for (int j = 0; j < list1.size(); j++) {
				TableEat label2 = new TableEat(list1.get(j));
				addActionMouseTable(label2);
				label2.setPreferredSize(new Dimension(50, 30));
				label2.setSize(new Dimension(50, 30));
				panel1.add(label2);
				listTableGross.add(label2);
			}
			if (list1.size() % 5 > 0) {
				for (int h = list1.size() % 5; h < 5; h++) {
					JLabel label1 = new JLabel();
					panel1.add(label1);
				}
			}
			panel1.setPreferredSize(new Dimension(scrollPane.getWidth() - 25, 30 * k));
			panel.add(panel1);
			size = size + panel2.getPreferredSize().height + panel1.getPreferredSize().height + 10;
			label3.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					if (label3.getText().equals("+")) {
						panel1.setVisible(true);
						label3.setText("-");
					} else {
						panel1.setVisible(false);
						label3.setText("+");
					}
				}
			});
			label.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					if (label3.getText().equals("+")) {
						panel1.setVisible(true);
						label3.setText("-");
					} else {
						panel1.setVisible(false);
						label3.setText("+");
					}
				}
			});
		}
		panel.setPreferredSize(new Dimension(scrollPane.getWidth() - 20, size));
		scrollPane.setViewportView(panel);
		viewNumberGuest();
	}

	public void setTable(Table table) {
		if (tableDesign != null) {
			tableDesign.setColorTable();
		}
		for (TableEat tableEat : listTableGross) {
			if (tableEat.getTable().getId() == table.getId()) {
				tableDesign = tableEat;
				tableDesign.setColor(colorPressed);
				screenSales.setTable(tableDesign);
			}
		}

	}

	// Hiển thị số bàn có khách và số bàn trống
	private void viewNumberGuest() {
		if (listTableGross != null) {
			int t = 0;
			for (int h = 0; h < listTableGross.size(); h++) {
				if (listTableGross.get(h).isHaveGuest()) {
					t = t + 1;
				}
			}
			buttonNoFree.setText("  " + String.valueOf(t));
			buttonFree.setText("  " + String.valueOf(listTableGross.size() - t));
		}
	}

	public void setVisiblButton(boolean flag) {
		btnThietLapBan.setVisible(flag);
	}
}