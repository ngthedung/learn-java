package com.hkt.client.swingexp.homescreen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.basic.BasicBorders;

import com.hkt.client.swingexp.app.banhang.screen.often.PanelOpenTableArea;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelRounding;
import com.hkt.client.swingexp.app.core.DialogJurisdiction;
import com.hkt.client.swingexp.app.core.DialogList;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.MouseEventClickButton;
import com.hkt.client.swingexp.app.hethong.PanelOption;
import com.hkt.client.swingexp.app.khuvucbanhang.PanelLocalTable;
import com.hkt.client.swingexp.app.khuvucbanhang.PanelLocation;
import com.hkt.client.swingexp.app.khuvucbanhang.PanelProject;
import com.hkt.client.swingexp.app.khuvucbanhang.PanelWorkingShift;
import com.hkt.client.swingexp.app.khuvucbanhang.PrintMachineDepartment;
import com.hkt.client.swingexp.app.khuvucbanhang.PrintMachineLocation;
import com.hkt.client.swingexp.app.khuvucbanhang.PrintMachineProducts;
import com.hkt.client.swingexp.app.khuvucbanhang.PrintMachineTable;
import com.hkt.client.swingexp.app.khuvucbanhang.list.TableListLocalTable;
import com.hkt.client.swingexp.app.khuvucbanhang.list.TableListLocation;
import com.hkt.client.swingexp.app.khuvucbanhang.list.TableListProject;
import com.hkt.client.swingexp.model.UIConfigModelManager;

@SuppressWarnings("serial")
public class MenuRightKhuVucBanHang extends JPanel {

	public MenuRightKhuVucBanHang() {
		setLayout(new GridLayout(2, 1));
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()));
		MenuRightTop menuRightTop = new MenuRightTop();
		menuRightTop.setBackground(Color.WHITE);
		JPanel panelTop = new JPanel();
		panelTop.setBackground(Color.WHITE);
		panelTop.setLayout(new BorderLayout());
		panelTop.add(menuRightTop, BorderLayout.CENTER);
		add(panelTop);

		MenuRightUnder menuRightUnder = new MenuRightUnder();
		menuRightUnder.setBackground(Color.WHITE);
		JPanel panelBot = new JPanel();
		panelBot.setBackground(Color.WHITE);
		panelBot.setLayout(new BorderLayout());
		panelBot.add(menuRightUnder, BorderLayout.CENTER);
		add(panelBot);
	}

	public static class MenuRightTop extends JPanel {

		public MenuRightTop() {
			setLayout(new GridLayout(3, 3));
			setBorder(BorderFactory.createTitledBorder(null, "Chức năng cơ bản", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 16), new java.awt.Color(204, 0, 0)));

			// THÊM MỚI BÀN/QUẦY
			JButton label1 = CreateButton("<html>Thêm mới<br>bàn-quầy</br></html>");
			label1.setActionCommand("2");
			label1.setFocusPainted(false);
			label1.setIcon(new ImageIcon(getClass().getResource("icon/ban1.png")));
			label1.setName("ThemMoiBanQuay");
			PanelLocalTable.permission = (UIConfigModelManager.getInstance().getPlainText(label1.getText()));
			PanelOpenTableArea.permission = (UIConfigModelManager.getInstance().getPlainText(label1.getText()));
			label1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						PanelLocalTable panel = new PanelLocalTable();
						panel.setName("ThemMoiBanQuay");
						DialogMain dialog = new DialogMain(panel);
						dialog.setIcon("ban1.png");
						dialog.setTitle("Thêm mới bàn quầy");
						dialog.setName("dlThemMoiBanQuay");
						dialog.setModal(true);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			label1.addMouseListener(new MouseEventClickButton("ban1.png", "ban2.png", "ban3.png"));
			add(label1);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label1.getText()));

			// KHU VỰC MỚI
			JButton label2 = CreateButton("<html>Khu vực<br>mới</br></html>");
			label2.setActionCommand("1");
			label2.setFocusPainted(false);
			label2.setName("KhuVucMoi");
			label2.setIcon(new ImageIcon(getClass().getResource("icon/khuvuc1.png")));
			PanelLocation.permission = (UIConfigModelManager.getInstance().getPlainText(label2.getText()));
			label2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					try {
						JButton btn = (JButton) evt.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						PanelLocation panel = new PanelLocation();
						DialogMain dialog = new DialogMain(panel);
						dialog.setIcon("khuvuc1.png");
						panel.setName("KhuVucMoi");
						dialog.setName("dlKhuVucMoi");
						dialog.setTitle("Khu vực mới");
						dialog.setModal(true);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			label2.addMouseListener(new MouseEventClickButton("khuvuc1.png", "khuvuc2.png", "khuvuc3.png"));
			add(label2);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label2.getText()));

			// DỰ ÁN
			JButton label7 = CreateButton("<html>Dự án mới</html>");
			label7.setName("Duan");
			label7.setFocusPainted(false);
			label7.setIcon(new ImageIcon(getClass().getResource("icon/duan1.png")));
			PanelProject.permission = (UIConfigModelManager.getInstance().getPlainText(label7.getText()));
			label7.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						PanelProject panel = new PanelProject();
						panel.setName("Duan");
						DialogMain dialog = new DialogMain(panel);
						dialog.setIcon("duan1.png");
						dialog.setTitle("Dự án mới");
						dialog.setName("dlThemMoiDuAn");
						dialog.setModal(true);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);

					} catch (Exception ex) {
						ex.printStackTrace();
					}

				}
			});
			label7.addMouseListener(new MouseEventClickButton("duan1.png", "duan2.png", "duan3.png"));
			add(label7);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label7.getText()));

			// DS BÀN/QUẦY
			JButton label4 = CreateButton("<html>Danh sách<br>bàn/quầy</br></html>");
			label4.setName("DanhSachBanQuay");
			label4.setFocusPainted(false);
			label4.setIcon(new ImageIcon(getClass().getResource("icon/bands1.png")));
			label4.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						TableListLocalTable table = new TableListLocalTable();
						table.setName("tbDanhSachBanQuay");
						table.setToolTipText(UIConfigModelManager.getInstance().getPlainText(btn.getText()));
						DialogList dialog = new DialogList(table);
						dialog.setIcon("bands1.png");
						dialog.setTitle("Danh sách bàn/quầy");
						dialog.setName("dlDanhSachBanQuay");
						dialog.setModal(true);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			label4.addMouseListener(new MouseEventClickButton("bands1.png", "bands2.png", "bands3.png"));
			add(label4);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label4.getText()));

			// DS KHU VỰC
			JButton label5 = CreateButton("<html>Danh sách<br>khu vực</br></html>");
			label5.setName("DanhSachKhuvuc");
			label5.setFocusPainted(false);
			label5.setIcon(new ImageIcon(getClass().getResource("icon/bands1.png")));
			label5.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						TableListLocation table = new TableListLocation();
						table.setToolTipText(UIConfigModelManager.getInstance().getPlainText(btn.getText()));
						table.setName("tbDanhSachKhuvuc");
						DialogList dialog = new DialogList(table);
						dialog.setIcon("bands1.png");
						dialog.setName("dlDanhSachKhuvuc");
						dialog.setTitle("Danh sách khu vực");
						dialog.setModal(true);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			label5.addMouseListener(new MouseEventClickButton("bands1.png", "bands2.png", "bands3.png"));
			add(label5);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label5.getText()));

			// DS DỰ ÁN
			JButton label8 = CreateButton("<html>Danh sách<br> Dự án</br></html>");
			label8.setName("DanhSachDuAn");
			label8.setFocusPainted(false);
			label8.setIcon(new ImageIcon(getClass().getResource("icon/duan1.png")));
			label8.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						TableListProject table = new TableListProject();
						table.setName("tbDanhSachDuAn");
						table.setToolTipText(UIConfigModelManager.getInstance().getPlainText(btn.getText()));
						DialogList dialog = new DialogList(table);
						dialog.setIcon("duan1.png");
						dialog.setTitle("Danh sách dự án");
						dialog.setName("dlDanhSachDuAn");
						dialog.setComponent4(table.getPanelCheckBox(), "");
						dialog.setModal(true);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			label8.addMouseListener(new MouseEventClickButton("duan1.png", "duan2.png", "duan3.png"));
			add(label8);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label8.getText()));

			// QL CỬA HÀNG
			JButton label17 = CreateButton("<html>Quản lý<br>Cửa hàng</br></html>");
			label17.setActionCommand("1");
			label17.setFocusPainted(false);
			label17.setIcon(new ImageIcon(getClass().getResource("icon/doanhnghiep1.png")));
			label17.setName("QuanLyCuaHang");
			PanelOption.permission3 = (UIConfigModelManager.getInstance().getPlainText(label17.getText()));
			label17.setForeground(Color.black);
			label17.addActionListener(new ActionListener() {
				@SuppressWarnings("unused")
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JButton btn = (JButton) e.getSource();
						if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
							DialogJurisdiction.getInstace().setVisible(true);
							return;
						}
						PanelOption pnOption = new PanelOption("restaurant", "RestaurantService", "store");
						pnOption.setName("QuanLyCuaHang");
						DialogMain dialog = new DialogMain(pnOption);
						dialog.setIcon("doanhnghiep1.png");
						dialog.setTitle("Quản lý cửa hàng");
						dialog.setName("dlQuanLyCuaHang");
						dialog.setModal(true);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			label17.addMouseListener(new MouseEventClickButton("doanhnghiep1.png", "doanhnghiep2.png", "doanhnghiep3.png"));
			add(label17);
			
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label17.getText()));
			JButton label18 = CreateButton("<html>Làm tròn<br>số</br></html>");
			label18.setActionCommand("1");
			label18.setFocusPainted(false);
			label18.setIcon(new ImageIcon(getClass().getResource("icon/doanhnghiep1.png")));
			PanelOption.permission3 = (UIConfigModelManager.getInstance().getPlainText(label18.getText()));
			label18.setForeground(Color.black);
			label18.addActionListener(new ActionListener() {
				@SuppressWarnings("unused")
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						
						  PanelRounding panel = new PanelRounding();
					      DialogResto dialog = new DialogResto(panel, false, 0, 120);
					      dialog.setTitle("Làm tròn số");
					      dialog.setLocationRelativeTo(null);
					      dialog.setModal(true);
					      dialog.setVisible(true);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			label18.addMouseListener(new MouseEventClickButton("doanhnghiep1.png", "doanhnghiep2.png", "doanhnghiep3.png"));
			add(label18);
		}	
	}
	
	public static class MenuRightUnder extends JPanel {

		public MenuRightUnder() {
			setBorder(BorderFactory.createTitledBorder(null, "Báo cáo thống kê", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 16), new java.awt.Color(204, 0, 0)));
			setLayout(new GridLayout(3, 3));

			// MÁY IN BÀN
						JButton label9 = CreateButton("<html>Máy in<br>bàn</br></html>");
						label9.setActionCommand("3");
						label9.setFocusPainted(false);
						label9.setName("btnMayInBan");
						PrintMachineTable.permisson = UIConfigModelManager.getInstance().getPlainText(label9.getText());
						label9.setIcon(new ImageIcon(getClass().getResource("icon/mayin1.png")));
						label9.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								try {
									JButton btn = (JButton) e.getSource();
									if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
										DialogJurisdiction.getInstace().setVisible(true);
										return;
									}
									PrintMachineTable printMachineTable = new PrintMachineTable();
									DialogResto dialog = new DialogResto(printMachineTable, true, 2000, 900);
									printMachineTable.setName("panelPrintMachineTable");
									dialog.setName("dialogPrintMachineTable");
									dialog.dispose();
									dialog.setUndecorated(true);
									dialog.setIcon("mayin1.png");
									dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
									dialog.setTitle("Máy in bàn");
									dialog.setModal(true);
									dialog.setLocationRelativeTo(null);
									dialog.setVisible(true);

								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}
						});
						label9.addMouseListener(new MouseEventClickButton("mayin1.png", "mayin2.png", "mayin3.png"));
						add(label9);
						UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label9.getText()));

						// MÁY IN KHU VỰC
						JButton label18 = CreateButton("<html>Máy in<br>khu vực</br></html>");
						label18.setActionCommand("2");
						label18.setFocusPainted(false);
						label18.setName("MayInKhuVuc");
						//PrintMachineLocation.permisson = UIConfigModelManager.getInstance().getPlainText(label18.getText());
						label18.setIcon(new ImageIcon(getClass().getResource("icon/mayin1.png")));
						label18.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								try {
									JButton btn = (JButton) e.getSource();
									if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
										DialogJurisdiction.getInstace().setVisible(true);
										return;
									}
									PrintMachineLocation printMachineLocation = new PrintMachineLocation();
									DialogResto dialog = new DialogResto(printMachineLocation, true, 700, 500);
									printMachineLocation.setName("MayInKhuVuc");
									dialog.setName("dlMayInKhuVuc");
									dialog.dispose();
									dialog.setUndecorated(true);
									dialog.setIcon("mayin1.png");
									dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
									dialog.setTitle("Máy in khu vực");
									dialog.setModal(true);
									dialog.setLocationRelativeTo(null);
									dialog.setVisible(true);

								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}
						});
						label18.addMouseListener(new MouseEventClickButton("mayin1.png", "mayin2.png", "mayin3.png"));
						add(label18);
						UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label18.getText()));

						// MÁY IN SP
						JButton label3 = CreateButton("<html>Máy in<br>sản phẩm</br></html>");
						label3.setActionCommand("3");
						label3.setFocusPainted(false);
						label3.setName("btnMayInSanPham");
						label3.setIcon(new ImageIcon(getClass().getResource("icon/mayin1.png")));
						PrintMachineProducts.permisson = UIConfigModelManager.getInstance().getPlainText(label3.getText());
						label3.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								try {
									JButton btn = (JButton) e.getSource();
									if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
										DialogJurisdiction.getInstace().setVisible(true);
										return;
									}
									PrintMachineProducts printMachineProducts = new PrintMachineProducts();
									DialogResto dialog = new DialogResto(printMachineProducts, true, 700, 500);
									printMachineProducts.setName("panelPrintMachineProduct");
									dialog.setIcon("mayin1.png");
									dialog.setName("dialogPrintMachineProduct");
									dialog.dispose();
									dialog.setUndecorated(true);
									dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
									dialog.setTitle("Máy in sản phẩm");
									dialog.setModal(true);
									dialog.setLocationRelativeTo(null);
									dialog.setVisible(true);

								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}
						});
						label3.addMouseListener(new MouseEventClickButton("mayin1.png", "mayin2.png", "mayin3.png"));
						add(label3);
						UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label3.getText()));

					
						// MÁY IN PHÒNG BAN
						JButton label19 = CreateButton("<html>Máy in <br> Phòng ban</html>");
						label19.setFocusPainted(false);
						label19.setIcon(new ImageIcon(getClass().getResource("icon/mayin1.png")));
						PrintMachineDepartment.permisson = UIConfigModelManager.getInstance().getPlainText(label19.getText());
						label19.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								try {
									JButton btn = (JButton) e.getSource();
									if (UIConfigModelManager.getInstance().getPermission(UIConfigModelManager.getInstance().getPlainText(btn.getText())) == null) {
										DialogJurisdiction.getInstace().setVisible(true);
										return;
									}
									PrintMachineDepartment printMachinedepartment = new PrintMachineDepartment();
									DialogResto dialog = new DialogResto(printMachinedepartment, true, 700, 500);
									printMachinedepartment.setName("panelPrintMachineDepartment");
									dialog.setName("dialogPrintMachineDepartment");
									dialog.setIcon("mayin1.png");
									dialog.dispose();
									dialog.setUndecorated(true);
									dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
									dialog.setTitle("Máy in phòng ban");
									dialog.setModal(true);
									dialog.setLocationRelativeTo(null);
									dialog.setVisible(true);

								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}
						});
						
						add(label19);

			
			//THÊM CA LÀM VIỆC
			JButton label10 = CreateButton("<html>Thêm ca<br>làm việc</br></html>");
			label10.setActionCommand("3");
			label10.setFocusPainted(false);
			label10.setForeground(Color.black);
			label10.setName("ThemCaLamViec");
			label10.setIcon(new ImageIcon(getClass().getResource("icon/congviec1.png")));
			label10.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						PanelWorkingShift panel = new PanelWorkingShift();
						DialogMain dialog = new DialogMain(panel);
						dialog.setIcon("congviec1.png");
						dialog.setTitle("Thêm ca làm việc");
						dialog.setName("dlThemCaLamViec");
						dialog.setModal(true);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);
						
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					
				}
			});
			
			label10.addMouseListener(new MouseEventClickButton("congviec1.png", "congviec2.png", "congviec3.png"));
			add(label10);
			UIConfigModelManager.getInstance().setPermission(UIConfigModelManager.getInstance().getPlainText(label10.getText()));

			JButton label7 = CreateButton("<html></html>");
			label7.setIcon(null);
			add(label7);
			
			JButton label17 = CreateButton("<html></html>");
			label17.setIcon(null);
			add(label17);
			
			label3.setForeground(Color.black);
			label9.setForeground(Color.black);
		}
	}

	static public JButton CreateButton(String text) {
		JButton label = new JButton();
		label.setFont(new Font("Tahoma", Font.BOLD, 12));
		label.setForeground(Color.black);
		label.setText(text);
		label.setContentAreaFilled(false);
		label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		label.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		label.setOpaque(false);
		label.setIconTextGap(10);
		Border border = new BorderUIResource.CompoundBorderUIResource(new BasicBorders.ButtonBorder(Color.white, Color.white, Color.white, Color.white), new BasicBorders.MarginBorder());
		label.setBorder(border);
		return label;
	}
}
