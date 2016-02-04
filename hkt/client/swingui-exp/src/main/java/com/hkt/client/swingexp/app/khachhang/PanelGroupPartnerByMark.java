package com.hkt.client.swingexp.app.khachhang;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;

import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.customer.entity.Point;

@SuppressWarnings("serial")
public class PanelGroupPartnerByMark extends MyPanel implements IDialogMain {

	public static String permission;
	private JLabel lbNhomDoiTac, lbMucDiem, lbDescription, lbMessenger;
	private JTextField txtMucDiem;
	private JTextArea txtDescription;
	@SuppressWarnings("rawtypes")
	private JComboBox cbNhomDT;
	private JScrollPane scrollPaneTable;
	private TableGroupPartnerByMark tbMark;
	private AccountGroup root = AccountModelManager.getInstance().getRootGroupDetail().getChildren().get(0);
	private AccountGroup accountgroup = new AccountGroup();
	@SuppressWarnings("unused")
	private AccountGroup parent = new AccountGroup();
	private List<AccountGroup> listaccountgroup = new ArrayList<AccountGroup>();

	public PanelGroupPartnerByMark() throws Exception {

		init();
		loadTable();
		setOpaque(false);
		reset();
	}

	// Hàm tạo giao diện
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void init() throws Exception {
		createBeginTest();
		lbMessenger = new JLabel("");
		scrollPaneTable = new JScrollPane();
		scrollPaneTable.setOpaque(false);
		scrollPaneTable.getViewport().setOpaque(false);
		scrollPaneTable.setBorder(null);

		tbMark = new TableGroupPartnerByMark(new ArrayList<AccountGroup>());
		tbMark.setName("tbMark");
		tbMark.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				try {
					tbMarkMouseClicked(event);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		try {
			listaccountgroup = AccountModelManager.getInstance()
			    .getGroupDetailByPath(root.getPath() + "/" + AccountModelManager.Customer).getChildren();
		} catch (Exception e) {
		}

		tbMark.setPreferredScrollableViewportSize(new Dimension(200, 290));
		scrollPaneTable.setViewportView(tbMark);
		lbDescription = new JLabel("Miêu tả");
		txtDescription = new JTextArea();
		txtDescription.setPreferredSize(new Dimension(250, 53));
		txtDescription.setName("txtDescription");
		txtDescription.setDisabledTextColor(Color.black);
		lbNhomDoiTac = new JLabel("Nhóm KH");
		lbMucDiem = new JLabel("Mức điểm");

		cbNhomDT = new JComboBox();
		cbNhomDT.setName("cbNhomDT");
		DefaultComboBoxModel listNhomDoiTac = new DefaultComboBoxModel(listaccountgroup.toArray());
		cbNhomDT.setModel(listNhomDoiTac);
		cbNhomDT.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					PanelGroupCustomers panelGroupCustomers;
					try {
						panelGroupCustomers = new PanelGroupCustomers();
						panelGroupCustomers.setName("QLNhomKhachHang");
						DialogMain dialog = new DialogMain(panelGroupCustomers);
						dialog.setTitle("Quản lý nhóm khách hàng");
						dialog.setName("dlGroupCustomer");
						dialog.setModal(true);
						dialog.setVisible(true);
						try {
							cbNhomDT.setModel(new DefaultComboBoxModel(AccountModelManager.getInstance()
							    .getGroupDetailByPath(root.getPath() + "/" + AccountModelManager.Customer).getChildren().toArray()));
						} catch (Exception e1) {
							cbNhomDT.setModel(new DefaultComboBoxModel());
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		UIManager.put("ComboBox.disabledBackground", Color.white);
		UIManager.put("ComboBox.disabledForeground", Color.black);

		scrollPaneTable.setViewportView(tbMark);

		txtMucDiem = new JTextField();
		txtMucDiem.setName("txtMucDiem");
		txtMucDiem.setDisabledTextColor(Color.black);

		MyGroupLayout layout = new MyGroupLayout(this);
		layout.add(0, 0, lbNhomDoiTac);
		layout.add(0, 1, cbNhomDT);
		layout.add(0, 2, lbMucDiem);
		layout.add(0, 3, txtMucDiem);

		layout.add(1, 0, lbDescription);
		layout.add(1, 1, txtDescription);
		layout.addMessenger(lbMessenger);

		layout.add(2, 0, new JLabel());
		layout.add(2, 1, scrollPaneTable);
		layout.updateGui();

	}

	// Hàm click vào 1 dòng để chỉnh sửa đối tượng
	private void tbMarkMouseClicked(MouseEvent event) {
		try {
			accountgroup = tbMark.getSelectedBean();
			cbNhomDT.setSelectedItem(accountgroup);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int click = 2;
		if (accountgroup.getName().indexOf("HktTest") > 0 && event.getButton() == 3) {
			click = 1;
		}

		if (event.getClickCount() >= click) {
			setData(accountgroup);
			setEnableCompoment(false);
			((DialogMain) getRootPane().getParent()).showButton(false);
		}
	}

	// Hàm lưu đối tượng
	@Override
	public void save() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
		} else {
			if (checkData()) {
				accountgroup = getData();
				AccountModelManager.getInstance().saveGroup(accountgroup);
				loadTable();
				reset();
			}
		}
	}

	public ActionListener getActionListener() {
		ActionListener a = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					List<Customer> customers = CustomerModelManager.getInstance().getCustomers(false);
					for (Customer c : customers) {
						Point p = CustomerModelManager.getInstance().getPointByCustomerId(c.getId());
						if (p != null && p.getPoint() != 0) {
							List<AccountGroup> acs = CustomerModelManager.getInstance().getCustomerGroup();
							for (AccountGroup ac : acs) {
								try {
									double d = MyDouble.parseDouble(ac.getOwner());
									if (p.getPoint() >= d) {
										List<AccountMembership> accountMemberships = AccountModelManager.getInstance()
										    .findMembershipByAccountLoginId(c.getLoginId());
										AccountMembership accountMembership = null;
										if (accountMemberships.size() > 0) {
											for (AccountMembership ac1 : accountMemberships) {
												if (ac1.getRole().equals(AccountModelManager.Customer)) {
													accountMembership = ac1;
													break;
												}
											}

										}
										if (accountMembership == null) {
											accountMembership = new AccountMembership();
											accountMembership.setLoginId(c.getLoginId());
											accountMembership.setRole(AccountModelManager.Customer);
											accountMembership.setGroupPath(ac.getPath());
											AccountModelManager.getInstance().saveAccountMembership(accountMembership);
										} else {
											accountMembership.setGroupPath(ac.getPath());
											AccountModelManager.getInstance().saveAccountMembership(accountMembership);
										}
									}
								} catch (Exception e2) {
								}
							}
						}
					}
					DialogNotice.getInstace().setVisible(true);
				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}
		};
		return a;
	}

	// Hàm kiểm tra các trường dữ liệu
	@SuppressWarnings("unused")
	public boolean checkData() {
		double point = 0;
		int count = 0;
		if (cbNhomDT.getSelectedItem() == null) {
			lbNhomDoiTac.setForeground(Color.RED);
			count++;
		} else {
			lbNhomDoiTac.setForeground(Color.black);
		}
		if (txtMucDiem.getText().equals("")) {
			lbMucDiem.setForeground(Color.RED);
			count++;

		} else {
			lbMucDiem.setForeground(Color.black);
		}

		try {
			point = Double.parseDouble(txtMucDiem.getText());
			lbMucDiem.setForeground(Color.black);

		} catch (Exception e) {
			lbMessenger.setText("Dữ liệu vùng đỏ bị lỗi");
			lbMucDiem.setForeground(Color.red);
			count++;
		}

		if (count > 0) {
			lbMessenger.setText("Dữ liệu vùng đỏ bị lỗi");
			lbMessenger.setForeground(Color.red);
			return false;
		} else
			lbMessenger.setText(" ");
		return true;

	}

	// Hàm cho phép sửa giao diện (enable các giao diện đã disible trước đó)
	@Override
	public void edit() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
		} else {
			setEnableCompoment(true);
			cbNhomDT.setEnabled(false);
		}
	}

	// Hàm xóa đối tượng
	@Override
	public void delete() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.ADMIN) {

			String str = "Xóa điểm nhóm đối tác ";
			PanelChoise pnPanel = new PanelChoise(str + accountgroup + "?");
			pnPanel.setName("Xoa");
			DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
			dialog1.setName("dlXoa");
			dialog1.setTitle("Xóa điểm nhóm đối tác");
			dialog1.setLocationRelativeTo(null);
			dialog1.setModal(true);
			dialog1.setVisible(true);

			if (pnPanel.isDelete()) {
				accountgroup.setOwner("");
				AccountModelManager.getInstance().saveGroup(accountgroup);
				loadTable();
				reset();
			} else if (pnPanel.isDelete() == false) {
				reset();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Bạn chươ được cấp quyền này !");
		}
	}

	// Hàm reset lại giao diện
	@Override
	public void reset() throws Exception {
		setEnableCompoment(true);
		lbNhomDoiTac.setForeground(Color.black);
		lbMucDiem.setForeground(Color.black);
		lbMessenger.setForeground(Color.black);

		cbNhomDT.setSelectedItem(null);
		txtMucDiem.setText("");
		txtDescription.setText("");

		lbMessenger.setText("");
		loadTable();
	}

	@Override
	public void refresh() throws Exception {
		if (tbMark.getSelectedBean() != null)
			setData(accountgroup);
		setEnableCompoment(false);
	}

	public List<AccountGroup> getObjects() throws Exception {
		try {
			return AccountModelManager.getInstance()
			    .getGroupDetailByPath(root.getPath() + "/" + AccountModelManager.Customer).getChildren();
		} catch (Exception e) {
			return new ArrayList<AccountGroup>();
		}

	}

	public void setObject(AccountGroup object) throws Exception {
		this.accountgroup = object;
		if (object == null) {
			accountgroup = new AccountGroup();
		}
		setData(accountgroup);
	}

	private AccountGroup getData() {
		try {
			accountgroup = (AccountGroup) cbNhomDT.getSelectedItem();
			accountgroup.setOwner(txtMucDiem.getText());
			accountgroup.setDescription(txtDescription.getText());
			lbMessenger.setText(" ");
			return accountgroup;
		} catch (Exception e) {
			lbMessenger.setVisible(true);
			lbMessenger.setText("Lỗi định dạng dữ liệu");
			e.printStackTrace();
			return new AccountGroup();
		}

	}

	public void setData(AccountGroup accountpro) {

		cbNhomDT.setSelectedItem(accountpro.getLabel());
		txtMucDiem.setText(accountpro.getOwner());
		txtDescription.setText(accountpro.getDescription());

	}

	private void loadTable() throws Exception {
		listaccountgroup = getObjects();
		List<AccountGroup> list = new ArrayList<AccountGroup>();
		for (AccountGroup ac : listaccountgroup) {
			if (ac.getOwner() != null && !ac.getOwner().equals("") && !ac.getOwner().equals("hkt")) {
				list.add(ac);
			}
		}
		tbMark.setAccountGroups(list);
	}

	private void setEnableCompoment(boolean value) {
		cbNhomDT.setEnabled(value);
		txtMucDiem.setEnabled(value);
		txtDescription.setEnabled(value);
	}

	// Tạo dữ liệu mẫu liên quan
	@Override
	public void createBeginTest() {
		// Tạo nhóm đối tác
		if (isTest) {
			try {
				AccountGroup root = AccountModelManager.getInstance().getRootGroupDetail().getChildren().get(0);
				AccountGroup parent = new AccountGroup(root, AccountModelManager.Customer);
				parent.setLabel(AccountModelManager.Customer);
				if (AccountModelManager.getInstance().getGroupByPath(root.getPath() + "/" + AccountModelManager.Customer) == null) {
					parent = AccountModelManager.getInstance().saveGroup(parent);
				} else {
					parent = AccountModelManager.getInstance().getGroupByPath(parent.getPath());
				}
				for (int i = 1; i <= 3; i++) {
					AccountGroup accountGroup = new AccountGroup();
					accountGroup.setName("Mã ĐT HktTest" + i);
					accountGroup.setLabel("NĐT HktTest" + i);
					accountGroup.setParent(parent);
					AccountModelManager.getInstance().saveGroup(accountGroup);
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		super.createBeginTest();
	}

	@Override
	public void deleteDataTest() {
		if (!PanelTestAll.runAll) {
			DialogTest.getInstance().show("Xóa toàn bộ dữ liệu test ?");
			if (DialogTest.getInstance().isTest()) {
				try {
					AccountModelManager.getInstance().deleteTest("HktTest");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
