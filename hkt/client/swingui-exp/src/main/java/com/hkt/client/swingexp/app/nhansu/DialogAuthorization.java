package com.hkt.client.swingexp.app.nhansu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.hkt.client.swingexp.app.component.PanelBackground;
import com.hkt.client.swingexp.app.component.PanelTrans;
import com.hkt.client.swingexp.app.core.DialogJurisdiction;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.HRJComboBox;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDialogPlus;
import com.hkt.client.swingexp.app.hethong.PanelSetupPermission;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.client.swingexp.homescreen.HomeScreen.MenuLeft;
import com.hkt.client.swingexp.homescreen.HomeScreen.MenuLeft.MenuLeftTop;
import com.hkt.client.swingexp.homescreen.MenuRightBanHang;
import com.hkt.client.swingexp.homescreen.MenuRightHeThong;
import com.hkt.client.swingexp.homescreen.MenuRightKhachHangDoiTac;
import com.hkt.client.swingexp.homescreen.MenuRightKhoHangHoa;
import com.hkt.client.swingexp.homescreen.MenuRightKhuVucBanHang;
import com.hkt.client.swingexp.homescreen.MenuRightKhuyenMai;
import com.hkt.client.swingexp.homescreen.MenuRightNhanSu;
import com.hkt.client.swingexp.homescreen.MenuRightThuChiMuaHang;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.GenericOptionModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.config.generic.Option;
import com.hkt.module.config.generic.OptionGroup;

@SuppressWarnings("serial")
public class DialogAuthorization extends JPanel implements IDialogResto {

	private DefaultTableModel model;
	private HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>();
	private List<String> menus = new ArrayList<String>();
	private RadioButtonRenderer radioButtonRenderer;
	private RadioButtonEditor radioButtonEditor;
	public static String permission;
	private Profile p = AccountModelManager.getInstance().getProfileConfigAdmin();

	public DialogAuthorization() {

		initComponents();
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		ButtonGroup btn = new ButtonGroup();
		btn.add(rbtEmployee);
		btn.add(rbtGroup);
		rbtEmployee.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (rbtEmployee.isSelected()) {
					cboPermission.setVisible(false);
					cboEmployee.setVisible(true);
					cboEmployee.resetDataPermission();
					loadGuiPermission();
				} else {
					cboEmployee.setVisible(false);
					cboPermission.setVisible(true);
					loadGuiPermission1();
				}

			}
		});
		rbtEmployee.setSelected(true);
		try {
			HomeScreen hom = new HomeScreen();
			MenuLeft men = (MenuLeft) hom.getComponent(1);
			MenuLeftTop menuRightTop = (MenuLeftTop) men.getComponent(0);
			for (int i = 0; i < menuRightTop.getComponentCount(); i++) {
				menus.add(((JButton) menuRightTop.getComponent(i)).getText().trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<String> banhang = new ArrayList<String>();
		try {
			MenuRightBanHang.MenuRightTop menuRightTop = MenuRightBanHang.MenuRightTop.class.newInstance();
			for (int i = 0; i < menuRightTop.getComponentCount(); i++) {
				String str = getPlainText(((JButton) menuRightTop.getComponent(i)).getText());
				if (!str.trim().isEmpty()) {
					banhang.add(str);
				}
			}
			MenuRightBanHang.MenuRightUnder menuRightUnder = MenuRightBanHang.MenuRightUnder.class.newInstance();
			for (int i = 0; i < menuRightUnder.getComponentCount(); i++) {
				String str = getPlainText(((JButton) menuRightUnder.getComponent(i)).getText());
				if (!str.trim().isEmpty()) {
					banhang.add(str);
				}
			}

		} catch (Exception e) {
		}
		hashMap.put(menus.get(0), banhang);

		List<String> thuchi = new ArrayList<String>();
		try {
			MenuRightThuChiMuaHang.MenuRightTop menuRightTop = MenuRightThuChiMuaHang.MenuRightTop.class.newInstance();
			for (int i = 0; i < menuRightTop.getComponentCount(); i++) {
				String str = getPlainText(((JButton) menuRightTop.getComponent(i)).getText());
				if (!str.trim().isEmpty()) {
					thuchi.add(str);
				}
			}
			MenuRightThuChiMuaHang.MenuRightUnder menuRightUnder = MenuRightThuChiMuaHang.MenuRightUnder.class.newInstance();
			for (int i = 0; i < menuRightUnder.getComponentCount(); i++) {
				String str = getPlainText(((JButton) menuRightUnder.getComponent(i)).getText());
				if (!str.trim().isEmpty() && !banhang.contains(str)) {
					thuchi.add(str);
				}
			}
		} catch (Exception e) {
		}
		hashMap.put(menus.get(1), thuchi);

		List<String> km = new ArrayList<String>();
		try {
			MenuRightKhuyenMai.MenuRightTop menuRightTop = MenuRightKhuyenMai.MenuRightTop.class.newInstance();
			for (int i = 0; i < menuRightTop.getComponentCount(); i++) {
				String str = getPlainText(((JButton) menuRightTop.getComponent(i)).getText());
				if (!str.trim().isEmpty()) {
					km.add(str);
				}
			}
			MenuRightKhuyenMai.MenuRightUnder menuRightUnder = MenuRightKhuyenMai.MenuRightUnder.class.newInstance();
			for (int i = 0; i < menuRightUnder.getComponentCount(); i++) {
				String str = getPlainText(((JButton) menuRightUnder.getComponent(i)).getText());
				if (!str.trim().isEmpty() && !banhang.contains(str)) {
					km.add(str);
				}
			}
		} catch (Exception e) {
		}
		hashMap.put(menus.get(2), km);

		List<String> kho = new ArrayList<String>();
		try {
			MenuRightKhoHangHoa.MenuRightTop menuRightTop = MenuRightKhoHangHoa.MenuRightTop.class.newInstance();
			for (int i = 0; i < menuRightTop.getComponentCount(); i++) {
				String str = getPlainText(((JButton) menuRightTop.getComponent(i)).getText());
				if (!str.trim().isEmpty()) {
					kho.add(str);
				}
			}
			MenuRightKhoHangHoa.MenuRightUnder menuRightUnder = MenuRightKhoHangHoa.MenuRightUnder.class.newInstance();
			for (int i = 0; i < menuRightUnder.getComponentCount(); i++) {
				String str = getPlainText(((JButton) menuRightUnder.getComponent(i)).getText());
				if (!str.trim().isEmpty() && !banhang.contains(str)) {
					kho.add(str);
				}
			}
		} catch (Exception e) {
		}
		hashMap.put(menus.get(3), kho);

		List<String> khachHang = new ArrayList<String>();
		try {
			MenuRightKhachHangDoiTac.MenuRightTop menuRightTop = MenuRightKhachHangDoiTac.MenuRightTop.class.newInstance();
			for (int i = 0; i < menuRightTop.getComponentCount(); i++) {
				String str = getPlainText(((JButton) menuRightTop.getComponent(i)).getText());
				if (!str.trim().isEmpty()) {
					khachHang.add(str);
				}
			}
			MenuRightKhachHangDoiTac.MenuRightUnder menuRightUnder = MenuRightKhachHangDoiTac.MenuRightUnder.class
			    .newInstance();
			for (int i = 0; i < menuRightUnder.getComponentCount(); i++) {
				String str = getPlainText(((JButton) menuRightUnder.getComponent(i)).getText());
				if (!str.trim().isEmpty() && !banhang.contains(str)) {
					khachHang.add(str);
				}
			}

		} catch (Exception e) {
		}
		hashMap.put(menus.get(4), khachHang);

		List<String> nhanSu = new ArrayList<String>();
		try {
			MenuRightNhanSu.MenuRightTop menuRightTop = MenuRightNhanSu.MenuRightTop.class.newInstance();
			for (int i = 0; i < menuRightTop.getComponentCount(); i++) {
				String str = getPlainText(((JButton) menuRightTop.getComponent(i)).getText());
				if (!str.trim().isEmpty()) {
					nhanSu.add(str);
				}
			}
			MenuRightNhanSu.MenuRightUnder menuRightUnder = MenuRightNhanSu.MenuRightUnder.class.newInstance();
			for (int i = 0; i < menuRightUnder.getComponentCount(); i++) {
				String str = getPlainText(((JButton) menuRightUnder.getComponent(i)).getText());
				if (!str.trim().isEmpty() && !banhang.contains(str)) {
					nhanSu.add(str);
				}
			}
		} catch (Exception e) {
		}
		hashMap.put(menus.get(5), nhanSu);

		List<String> khuvucban = new ArrayList<String>();
		try {
			MenuRightKhuVucBanHang.MenuRightTop menuRightTop = MenuRightKhuVucBanHang.MenuRightTop.class.newInstance();
			for (int i = 0; i < menuRightTop.getComponentCount(); i++) {
				String str = getPlainText(((JButton) menuRightTop.getComponent(i)).getText());
				if (!str.trim().isEmpty()) {
					khuvucban.add(str);
				}
			}
			MenuRightKhuVucBanHang.MenuRightUnder menuRightUnder = MenuRightKhuVucBanHang.MenuRightUnder.class.newInstance();
			for (int i = 0; i < menuRightUnder.getComponentCount(); i++) {
				String str = getPlainText(((JButton) menuRightUnder.getComponent(i)).getText());
				if (!str.trim().isEmpty() && !banhang.contains(str)) {
					khuvucban.add(str);
				}
			}
		} catch (Exception e) {
		}
		hashMap.put(menus.get(6), khuvucban);

		List<String> hethong = new ArrayList<String>();
		try {
			MenuRightHeThong.MenuRightTop menuRightTop = MenuRightHeThong.MenuRightTop.class.newInstance();
			for (int i = 0; i < menuRightTop.getComponentCount(); i++) {
				String str = getPlainText(((JButton) menuRightTop.getComponent(i)).getText());
				if (!str.trim().isEmpty()) {
					hethong.add(str);
				}
			}
			MenuRightHeThong.MenuRightUnder menuRightUnder = MenuRightHeThong.MenuRightUnder.class.newInstance();
			for (int i = 0; i < menuRightUnder.getComponentCount(); i++) {
				String str = getPlainText(((JButton) menuRightUnder.getComponent(i)).getText());
				if (!str.trim().isEmpty()) {
					hethong.add(str);
				}
			}
		}

		catch (Exception e) {
		}
		hashMap.put(menus.get(7), hethong);

		String[] header = { "Menu", "Tên giao diện", "Quyền" };
		model = new DefaultTableModel(header, 0) {

			boolean[] canEdit = new boolean[] { false, false, true };

			@Override
			public boolean isCellEditable(int row, int column) {
				return canEdit[column];
			}
		};

		jTable1.setModel(model);
		String[] answer = { "Quyền đọc", "Quyền ghi", "Toàn quyền", "Không quyền" };
		radioButtonRenderer = new RadioButtonRenderer(answer);
		jTable1.getColumn("Quyền").setCellRenderer(radioButtonRenderer);
		radioButtonEditor = new RadioButtonEditor(new JCheckBox(), new RadioButtonPanel(answer));
		jTable1.getColumn("Quyền").setCellEditor(radioButtonEditor);
		
		cboEmployee.removeItemListener(cboEmployee.getItemListeners()[0]);
		cboEmployee.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				loadGuiPermission();
			}
		});
		cboPermission.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				loadGuiPermission1();
			}
		});
		loadGuiPermission();
	}

	private void loadGuiPermission() {
		int k = 3;
		String[] header = { "Menu", "Tên giao diện", "Quyền" };
		model = new DefaultTableModel(header, 0) {

			boolean[] canEdit = new boolean[] { false, false, true };

			@Override
			public boolean isCellEditable(int row, int column) {
				return canEdit[column];
			}
		};
		for (int i = 0; i < menus.size(); i++) {
			k = 3;
			try {
				String path = ManagerAuthenticate.getInstance().getOrganizationLoginId() + "/"
				    + cboEmployee.getSelectedEmployee().getLoginId() + "/" + menus.get(i);
				AccountMembership membership = AccountModelManager.getInstance().getMembershipByAccountAndGroup(
				    cboEmployee.getSelectedEmployee().getLoginId(), path);
				if (membership.getCapability().equals(Capability.READ)) {
					k = 0;
				} else {
					if (membership.getCapability().equals(Capability.WRITE)) {
						k = 1;
					} else {
						k = 2;
					}
				}
			} catch (Exception e) {
				k = 3;
			}
			Object[] row = { menus.get(i), "", k };
			model.addRow(row);

			String str1 = "";
			if (rbtEmployee.isSelected()) {
				try {
					str1 = cboEmployee.getSelectedEmployee().getLoginId();
				} catch (Exception e) {
				}
				
			} else {
				try {
					str1 = ((Option) cboPermission.getSelectedItem()).getCode();
				} catch (Exception e) {
				}

			}
			for (int ii = 0; ii < hashMap.get(menus.get(i)).size(); ii++) {
				int kk = k;
				try {
					String path = ManagerAuthenticate.getInstance().getOrganizationLoginId() + "/" + str1 + "/"
					    + hashMap.get(menus.get(i)).get(ii);
					AccountMembership membership = AccountModelManager.getInstance().getMembershipByAccountAndGroup(str1, path);
					if (membership.getCapability().equals(Capability.READ)) {
						kk = 0;
					} else {
						if (membership.getCapability().equals(Capability.WRITE)) {
							kk = 1;
						} else {
							kk = 2;
						}
					}
				} catch (Exception e) {
				}
				String str = hashMap.get(menus.get(i)).get(ii);
				// if(p.get(str)!=null&&p.get(str).equals("true")){
				// str = str +"*";
				// }
				Object[] row1 = { "", str, kk };
				model.addRow(row1);
			}
		}
		jTable1.setModel(model);
		jTable1.getColumn("Quyền").setCellRenderer(radioButtonRenderer);
		jTable1.getColumn("Quyền").setCellEditor(radioButtonEditor);
jTable1.getColumn("Tên giao diện").setCellRenderer(new TableCellRenderer() {
			
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			    int row, int column)
			{
				JLabel label = new JLabel(value.toString());
				String str="";
				if (rbtEmployee.isSelected()) {
					try {
						str = cboEmployee.getSelectedEmployee().getLoginId();
					} catch (Exception e) {
					}
					
				} else {
					str = ((Option)cboPermission.getSelectedItem()).getCode();
				}
				str = str+value.toString();
				if (p.get(str) != null && p.get(str).equals("true")) {
					label.setText(value.toString()+"*");
				}
				if(isSelected){
					label.setOpaque(true);
					label.setBackground(jTable1.getSelectionBackground());
				}
				return label;
			}
		});
	}

	private void loadGuiPermission1() {
		int k = 3;
		String[] header = { "Menu", "Tên giao diện", "Quyền" };
		model = new DefaultTableModel(header, 0) {

			boolean[] canEdit = new boolean[] { false, false, true };

			@Override
			public boolean isCellEditable(int row, int column) {
				return canEdit[column];
			}
		};
		String str = ((Option) cboPermission.getSelectedItem()).getCode();
		for (int i = 0; i < menus.size(); i++) {
			k = 3;
			try {
				String path = ManagerAuthenticate.getInstance().getOrganizationLoginId() + "/" + str + "/" + menus.get(i);
				AccountMembership membership = AccountModelManager.getInstance().getMembershipByAccountAndGroup(str, path);
				if (membership.getCapability().equals(Capability.READ)) {
					k = 0;
				} else {
					if (membership.getCapability().equals(Capability.WRITE)) {
						k = 1;
					} else {
						k = 2;
					}
				}
			} catch (Exception e) {
				k = 3;
			}
			Object[] row = { menus.get(i), "", k };
			model.addRow(row);

			String str1 = "";
			if (rbtEmployee.isSelected()) {
				str1 = cboEmployee.getSelectedEmployee().getLoginId();
			} else {
				try {
					str1 = ((Option) cboPermission.getSelectedItem()).getCode();
				} catch (Exception e) {
				}

			}
			// if (index == jTable1.getRowCount() || !jTable1.getValueAt(rowSelect +
			// 1, 0).toString().trim().isEmpty()) {
			for (int ii = 0; ii < hashMap.get(menus.get(i)).size(); ii++) {
				int kk = k;
				try {
					String path = ManagerAuthenticate.getInstance().getOrganizationLoginId() + "/" + str1 + "/"
					    + hashMap.get(menus.get(i)).get(ii);
					AccountMembership membership = AccountModelManager.getInstance().getMembershipByAccountAndGroup(str1, path);
					if (membership.getCapability().equals(Capability.READ)) {
						kk = 0;
					} else {
						if (membership.getCapability().equals(Capability.WRITE)) {
							kk = 1;
						} else {
							kk = 2;
						}
					}
				} catch (Exception e) {
				}
				String str2 = hashMap.get(menus.get(i)).get(ii);
				// if(p.get(str2)!=null&&p.get(str2).equals("true")){
				// str2 = str2 +"*";
				// }
				Object[] row1 = { "", str2, kk };
				model.addRow(row1);
			}
			// }
		}
		jTable1.setModel(model);
		jTable1.getColumn("Quyền").setCellRenderer(radioButtonRenderer);
		jTable1.getColumn("Quyền").setCellEditor(radioButtonEditor);
jTable1.getColumn("Tên giao diện").setCellRenderer(new TableCellRenderer() {
			
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			    int row, int column)
			{
				JLabel label = new JLabel(value.toString());
				String str="";
				if (rbtEmployee.isSelected()) {
					str = cboEmployee.getSelectedEmployee().getLoginId();
				} else {
					str = ((Option)cboPermission.getSelectedItem()).getCode();
				}
				str = str+value.toString();
				if (p.get(str) != null && p.get(str).equals("true")) {
					label.setText(value.toString()+"*");
				}
				if(isSelected){
					label.setOpaque(true);
					label.setBackground(jTable1.getSelectionBackground());
				}
				return label;
			}
		});
	}

	private void initComponents() {

		jScrollPane1 = new JScrollPane();
		jTable1 = new JTable();
		rbtEmployee = new JRadioButton();
		rbtGroup = new JRadioButton();
		cboEmployee = new HRJComboBox();
		cboPermission = new JComboBox();
		jButton1 = new JButton();

		jPanel1 = new PanelBackground();
		jPanel2 = new PanelBackground();
		jPanel3 = new PanelTrans();

		jPanel1.setBackground(new Color(255, 255, 255));
		jPanel2.setBackground(new Color(255, 255, 255));
		jPanel3.setBackground(new Color(255, 255, 255));

		jPanel1.setOpaque(false);
		jPanel2.setOpaque(false);
		jPanel3.setOpaque(false);

		jPanel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, null,
		    new java.awt.Color(204, 204, 204), null, null), "Phân quyền chi tiết", TitledBorder.DEFAULT_JUSTIFICATION,
		    TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(104, 0, 0))); // NOI18N
		jPanel2.setOpaque(false);

		jTable1.setFont(new java.awt.Font("Tahoma", 0, 14));
		jTable1.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null }, { null, null, null, null },
		    { null, null, null, null }, { null, null, null, null } }, new String[] { "Title 1", "Title 2", "Title 3",
		    "Title 4" }));
		jTable1.setRowHeight(23);
		jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jTable1MouseClicked(evt);
			}
		});
		jScrollPane1.setViewportView(jTable1);

		rbtEmployee.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		rbtEmployee.setText("Nhân viên");
		rbtEmployee.setOpaque(false);

		rbtGroup.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		rbtGroup.setText("Nhóm");
		rbtGroup.setOpaque(false);

		cboEmployee.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		cboPermission.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		cboPermission.setPreferredSize(new Dimension(200, 23));
		try {
			OptionGroup group = GenericOptionModelManager.getInstance().getOptionGroup("accounting", "AccountingService",
			    "contributor_role");
			cboPermission.setModel(new DefaultComboBoxModel(group.getOptions().toArray()));
		} catch (Exception e) {
		}

		jButton1.setFont(new java.awt.Font("Tahoma", 1, 14));
		jButton1.setText("Thoát");
		jButton1.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/cancel1.png")));
		jButton1.addMouseListener(new MouseEventClickButtonDialogPlus("cancel1.png", "cancel1.png", "cancel1.png"));
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		setOpaque(false);
		GroupLayout jPanel2Layout = new GroupLayout(this);
		this.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout
		    .createParallelGroup(GroupLayout.Alignment.LEADING)
		    .addGroup(
		        jPanel2Layout.createSequentialGroup().addGap(44, 44, 44).addComponent(rbtEmployee).addGap(18, 18, 18)
		            .addComponent(rbtGroup).addGap(31, 31, 31)
		            .addComponent(cboEmployee, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
		            .addComponent(cboPermission, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
		            .addContainerGap(407, Short.MAX_VALUE))
		    .addComponent(jScrollPane1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 832, Short.MAX_VALUE));

		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
		    jPanel2Layout
		        .createSequentialGroup()
		        .addContainerGap()
		        .addGroup(
		            jPanel2Layout
		                .createParallelGroup(GroupLayout.Alignment.BASELINE)
		                .addComponent(rbtEmployee)
		                .addComponent(rbtGroup)
		                .addComponent(cboEmployee, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
		                    GroupLayout.DEFAULT_SIZE)
		                .addComponent(cboPermission, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
		                    GroupLayout.DEFAULT_SIZE)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
		        .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)));

	}

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		((JDialog) getRootPane().getParent()).dispose();
	}

	private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {
		if (evt.getClickCount() < 2) {
			return;
		}
		int row = jTable1.getSelectedRow();
		if (row > 0) {
			String str="";
			if (rbtEmployee.isSelected()) {
				str = cboEmployee.getSelectedEmployee().getLoginId();
			} else {
				str = ((Option)cboPermission.getSelectedItem()).getCode();
			}
			str = str+jTable1.getValueAt(row, 1).toString();
			PanelSetupPermission panel1 = new PanelSetupPermission(str);
			panel1.setName("Phân quyền");
			DialogResto dialog1 = new DialogResto(panel1, false, 0, 120);
			dialog1.setName("dlKho");
			dialog1.setTitle("Phân quyền");
			dialog1.setLocationRelativeTo(null);
			dialog1.setModal(true);
			dialog1.setVisible(true);
			if (panel1.isDelete()) {
				 p = AccountModelManager.getInstance().getProfileConfigAdmin();
			}
		}
	}

	private void jButton2ActionPerformed() {
		if (rbtEmployee.isSelected()) {
			if (cboEmployee.getItemCount() == 0) {
				return;
			}
		} else {
			if (cboPermission.getItemCount() == 0) {
				return;
			}else {
				try {
					Option option=(Option)cboPermission.getSelectedItem();
					OptionGroup group = GenericOptionModelManager.getInstance()
							.getOptionGroup("accounting", "AccountingService",
									"contributor_role");
					group.getOption(option.getCode()).setPermission(true);
					GenericOptionModelManager.getInstance()
							.saveOptionGroup(group);
				} catch (Exception e) {
				}
				
			}
		}
		try {
			for (int i = 0; i < jTable1.getRowCount(); i++) {
				int k = (Integer) jTable1.getValueAt(i, 2);
				if (-1 < k && k < 3) {
					String str;
					if (!jTable1.getValueAt(i, 0).toString().trim().isEmpty()) {
						str = jTable1.getValueAt(i, 0).toString();
						try {
							if ((!jTable1.getValueAt(i + 1, 0).toString().trim().isEmpty())) {
								for (int j = 0; j < hashMap.get(jTable1.getValueAt(i, 0).toString()).size(); j++) {
									String name = hashMap.get(str).get(j);

									savePermission(name, k);
								}
							}
						} catch (Exception e) {
							for (int j = 0; j < hashMap.get(jTable1.getValueAt(i, 0).toString()).size(); j++) {
								String name = hashMap.get(jTable1.getValueAt(i, 0).toString()).get(j);

								savePermission(name, k);
							}
						}
					} else {
						str = jTable1.getValueAt(i, 1).toString();
					}
					savePermission(str, k);
				} else {
					String str;
					if (!jTable1.getValueAt(i, 0).toString().trim().isEmpty()) {
						str = jTable1.getValueAt(i, 0).toString();
					} else {
						str = jTable1.getValueAt(i, 1).toString();
					}
					String str1 = "";
					if (rbtEmployee.isSelected()) {
						str1 = cboEmployee.getSelectedEmployee().getLoginId();
					} else {
						try {
							str1 = ((Option) cboPermission.getSelectedItem()).getCode();
						} catch (Exception e) {
						}

					}
					String path = ManagerAuthenticate.getInstance().getOrganizationLoginId() + "/" + str1 + "/" + str;
					AccountGroup group = AccountModelManager.getInstance().getGroupByPath(path);
					if (group != null) {
						AccountModelManager.getInstance().deleteGroup(group);
					}
				}
			}
			DialogNotice.getInstace().setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void savePermission(String name, int k) throws Exception {
		String str = "";
		if (rbtEmployee.isSelected()) {
			str = cboEmployee.getSelectedEmployee().getLoginId();
		} else {
			str = ((Option) cboPermission.getSelectedItem()).getCode();
		}
		String path = ManagerAuthenticate.getInstance().getOrganizationLoginId() + "/" + str + "/" + name;
		AccountGroup group = AccountModelManager.getInstance().getGroupByPath(path);
		if (group == null) {
			group = new AccountGroup();
			group.setLabel(name);
			group.setName(name);
			group.setPath(path);
			AccountModelManager.getInstance().saveGroup(group);
		}

		AccountMembership m = AccountModelManager.getInstance().getMembershipByAccountAndGroup(str, path);
		if (m == null) {
			m = new AccountMembership();
		}
		m.setLoginId(str);
		m.setGroupPath(path);
		if (k == 0) {
			m.setCapability(Capability.READ);
		} else {
			if (k == 1) {
				m.setCapability(Capability.WRITE);
			} else {
				m.setCapability(Capability.ADMIN);
			}
		}
		AccountModelManager.getInstance().saveAccountMembership(m);
	}

	// Variables declaration - do not modify
	private JButton jButton1;
	private HRJComboBox cboEmployee;
	private JComboBox cboPermission;
	private JPanel jPanel1, jPanel3;
	private JPanel jPanel2;
	private JRadioButton rbtEmployee;
	private JRadioButton rbtGroup;
	private JScrollPane jScrollPane1;
	private JTable jTable1;

	// End of variables declaration

	// Cell base

	public class RadioButtonPanel extends JPanel {

		JRadioButton[] buttons;

		RadioButtonPanel(String[] str) {
			setLayout(new GridLayout(1, 3));
			buttons = new JRadioButton[str.length];
			for (int i = 0; i < buttons.length; i++) {
				buttons[i] = new JRadioButton(str[i]);
				buttons[i].setFocusPainted(false);
				add(buttons[i]);

			}
		}

		public void setSelectedIndex(int index) {
			for (int i = 0; i < buttons.length; i++) {
				buttons[i].setSelected(i == index);
			}
		}

		public void setFont1(Font font) {
			for (int i = 0; i < buttons.length; i++) {
				buttons[i].setFont(font);
			}
		}

		public int getSelectedIndex() {
			for (int i = 0; i < buttons.length; i++) {
				if (buttons[i].isSelected()) {
					return i;
				}
			}
			return -1;
		}

		public JRadioButton[] getButtons() {
			return buttons;
		}
	}

	public class RadioButtonRenderer extends RadioButtonPanel implements TableCellRenderer {

		private static final long serialVersionUID = 1L;

		RadioButtonRenderer(String[] strs) {
			super(strs);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
		    int row, int column)
		{
			if (value instanceof Integer) {
				setSelectedIndex(((Integer) value).intValue());
			}
			return this;
		}
	}

	public class RadioButtonEditor extends DefaultCellEditor implements ActionListener {

		RadioButtonPanel panel;
		JTable table;

		public RadioButtonEditor(JCheckBox checkBox, RadioButtonPanel panel) {
			super(checkBox);
			this.panel = panel;
			ButtonGroup buttonGroup = new ButtonGroup();
			JRadioButton[] buttons = panel.getButtons();
			for (int i = 0; i < buttons.length; i++) {
				buttonGroup.add(buttons[i]);
				buttons[i].addActionListener(this);
			}
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			this.table = table;
			if (value instanceof Integer) {
				panel.setSelectedIndex(((Integer) value).intValue());
			}
			return panel;
		}

		@Override
		public Object getCellEditorValue() {
			return new Integer(panel.getSelectedIndex());
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JRadioButton rbt = (JRadioButton) e.getSource();
			try {
				if (rbt.isSelected()) {
					table.setValueAt((Integer) getCellEditorValue(), table.getSelectedRow(), 2);
					int rowSelect = jTable1.getSelectedRow();
					if (rowSelect >= 0) {
						String str = jTable1.getValueAt(rowSelect, 0).toString();
						if (!str.trim().isEmpty()) {
							int kk = 3;
							if (rbt.getText().equals("Quyền đọc")) {
								kk = 0;
							}
							if (rbt.getText().equals("Quyền ghi")) {
								kk = 1;
							}
							if (rbt.getText().equals("Toàn quyền")) {
								kk = 2;
							}

							if (kk < 4) {
								for (int i = rowSelect + 1; i < jTable1.getRowCount(); i++) {
									if (jTable1.getValueAt(i, 0).toString().trim().isEmpty()) {
										jTable1.setValueAt(kk, i, 2);

									} else {
										return;
									}
								}
							}
						} else {
							for (int i = rowSelect; i >= 0; i--) {
								if (!jTable1.getValueAt(i, 0).toString().trim().isEmpty()) {
									jTable1.setValueAt(4, i, 2);
									return;
								}

							}
						}
					}
				}
			} catch (Exception e2) {
			}

		}
	}

	public String getPlainText(String html) {
		String htmlBody = html.replaceAll("<br>", " ");
		htmlBody = htmlBody.replaceAll("</br>", "");
		String plainTextBody = htmlBody.replaceAll("<[^<>]+>([^<>]*)<[^<>]+>", "$1");
		return plainTextBody;
	}

	@Override
	public void Save() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			DialogJurisdiction.getInstace().setVisible(true);
			return;
		}
		jButton2ActionPerformed();

	}
}
