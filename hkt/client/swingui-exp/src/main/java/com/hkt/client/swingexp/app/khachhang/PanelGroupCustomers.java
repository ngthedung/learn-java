package com.hkt.client.swingexp.app.khachhang;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.hethong.PanelManageDelete;
import com.hkt.client.swingexp.app.hethong.PanelRecybin;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.app.khuvucbanhang.list.GroupComboBoxDelete;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.util.text.DateUtil;

@SuppressWarnings("serial")
public class PanelGroupCustomers extends MyPanel implements IDialogMain {

	private ExtendJLabel lbName, lbOwner, lbLabel, lbDescription, lbMessenger;
	private ExtendJTextField txtName, txtOwner, txtLabel, txtDescription;
	private JScrollPane scrollPaneTable, scrollIndex;
	private TableGroupPartner accountGroupTable;
	private AccountGroup rootGroupCustomer;
	private AccountGroup group = new AccountGroup();
	public static String permission;
	private boolean restore = true;

	public PanelGroupCustomers() throws Exception {
		init();
		setOpaque(false);
		reset();
	}

	private void init() throws Exception {
		lbMessenger = new ExtendJLabel("");
		scrollPaneTable = new JScrollPane();
		scrollPaneTable.setOpaque(false);
		scrollPaneTable.setBorder(null);

		rootGroupCustomer = CustomerModelManager.getInstance().getRootGroupCustomer();

		accountGroupTable = new TableGroupPartner();
		accountGroupTable.setOpaque(false);

		scrollPaneTable.getViewport().setOpaque(false);
		accountGroupTable.setName("accountGroupTable");
		accountGroupTable.setAccountGroups(getObjects());

		accountGroupTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				try {
					accountGroupListMouseClicked(event);
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		});

		accountGroupTable.setPreferredScrollableViewportSize(new Dimension(200, 290));
		scrollPaneTable.setViewportView(accountGroupTable);

		lbOwner = new ExtendJLabel("D.Nghiệp");
		lbName = new ExtendJLabel("Mã");
		lbLabel = new ExtendJLabel("Tên");
		lbDescription = new ExtendJLabel("Miêu tả");

		txtOwner = new ExtendJTextField();
		txtOwner.setName("txtOwner");
		txtOwner.setEnabled(false);
		txtOwner.setText(AccountModelManager.getInstance().getNameByLoginId(
		    ManagerAuthenticate.getInstance().getOrganizationLoginId()));

		txtName = new ExtendJTextField();
		txtName.setName("txtName");

		txtLabel = new ExtendJTextField();
		txtLabel.setName("txtLabel");

		txtDescription = new ExtendJTextField();
		txtDescription.setName("txtDescription");

		MyGroupLayout layout = new MyGroupLayout(this);

		layout.add(0, 0, lbOwner);
		layout.add(0, 1, txtOwner);

		layout.add(0, 2, lbName);
		layout.add(0, 3, txtName);

		layout.add(1, 0, lbLabel);
		layout.add(1, 1, txtLabel);

		layout.add(1, 2, lbDescription);
		layout.add(1, 3, txtDescription);

		layout.addMessenger(lbMessenger);

		// Thêm button cập nhật index
		scrollIndex = new JScrollPane();
		scrollIndex.setOpaque(false);
		scrollIndex.getViewport().setOpaque(false);
		scrollIndex.setBorder(null);
		scrollIndex.setViewportView(accountGroupTable.getPanleButton());

		// Giao diện Button cập nhật index đi cùng với scrollPaneTable
		layout.add(2, 0, scrollIndex);
		layout.add(2, 1, scrollPaneTable);
		layout.updateGui();
	}

	protected void accountGroupListMouseClicked(MouseEvent event) throws Exception {
		group = accountGroupTable.getSelectedBean();
		int click = 2;
		if (group.getLabel().indexOf("HktTest") > 0 && event.getButton() == 3) {
			click = 1;
		}
		if (event.getClickCount() >= click) {
			setData();
			setEnableCompoment(false);
			((DialogMain) getRootPane().getParent()).showButton(false);
		}
		refresh();
	}

	private void setEnableCompoment(boolean value) {
		txtName.setEnabled(value);
		txtLabel.setEnabled(value);
		txtDescription.setEnabled(value);
	}

	@Override
	public void save() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			return;
		} else {
			if (checkData()) {
				if (restore) {
					getData();
					AccountModelManager.getInstance().saveGroup(group);
				}
				reset();
			}
		}
	}

	public boolean checkData() {
		// getData();
		boolean checkdata = true;

		if (txtName.getText().equals("")) {
			lbName.setForeground(Color.red);
			checkdata = false;
		} else {
			lbName.setForeground(Color.black);
		}
		if (txtLabel.getText().equals("")) {
			lbLabel.setForeground(Color.red);
			checkdata = false;
		} else {
			lbLabel.setForeground(Color.black);
		}

		// Kiểm tra xem có bị trùng code hay không
		if (txtName.isEnabled()) {
			try {
				AccountGroup acc = AccountModelManager.getInstance().getGroupByName(txtName.getText());
				if (acc != null) {
					checkdata = false;
					lbName.setForeground(Color.red);
					if (acc.isRecycleBin()) {

						PanelRecybin panel = new PanelRecybin("Mã đã bị xóa trước đó!",
						    " Chọn ĐỒNG Ý để lấy lại thông tin ban đầu hoặc THOÁT và nhập mã khác!");
						panel.setName("Xoa");
						DialogResto dialog = new DialogResto(panel, false, 0, 120);
						dialog.setName("dlXoa");
						dialog.setTitle("Quản lý nhóm khách hàng");
						dialog.setLocationRelativeTo(null);
						dialog.setModal(true);
						dialog.setVisible(true);
						if (panel.isDelete()) {
							restore = false;
							acc.setRecycleBin(false);
							AccountModelManager.getInstance().saveGroup(acc);
							return true;
						}
					}
				}
			} catch (Exception e) {
			}
		}
		if (!checkdata) {
			lbMessenger.setText("Dữ liệu vùng đỏ bị lỗi");
			lbMessenger.setForeground(Color.red);
			return false;
		} else {
			lbMessenger.setText("");
			return true;
		}
	}

	@Override
	public void edit() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này!");
			return;
		} else {
			setEnableCompoment(true);
			txtName.setEnabled(false);
		}
	}

	@Override
	public void delete() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.ADMIN) {
			try {
				String str = "Xóa nhóm khách hàng ";
				PanelChoise panel = new PanelChoise(str + group + " ?");
				panel.setName("Xoa");
				DialogResto dialog = new DialogResto(panel, false, 0, 80);
				dialog.setName("dlXoa");
				dialog.setTitle("Xóa nhóm khách hàng");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
				if (panel.isDelete()) {
					showGroup();
				}
			} catch (Exception e) {
			}
		} else {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
		}
	}

	public void showGroup() throws Exception {
		if (CustomerModelManager.getInstance().findCustomersByAccountGroup(group) == null
		    || CustomerModelManager.getInstance().findCustomersByAccountGroup(group).isEmpty()) {
			AccountModelManager.getInstance().deleteGroup(group);
			reset();
		} else {
			try {
				String nameGroup = txtLabel.getText();
				PanelManageDelete<Customer> panel = new PanelManageDelete<Customer>(nameGroup, CustomerModelManager
				    .getInstance().findCustomersByAccountGroup(group), new GroupComboBoxDelete(group));
				panel.setName("XoaKH");
				DialogResto dialog = new DialogResto(panel, true, 0, 80);
				dialog.setName("dlXoaKH");
				dialog.dispose();
				dialog.setUndecorated(true);
				dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
				dialog.setTitle("Quản lý xóa nhóm khách hàng");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			if (CustomerModelManager.getInstance().findCustomersByAccountGroup(group) == null
			    || CustomerModelManager.getInstance().findCustomersByAccountGroup(group).isEmpty()) {
				AccountModelManager.getInstance().deleteGroup(group);
				reset();
			}
		}
	}

	@Override
	public void reset() throws Exception {
		txtName.setText(DateUtil.asCompactDateTimeId(new Date()));
		txtLabel.setText("");
		txtDescription.setText("");

		lbName.setForeground(Color.black);
		lbLabel.setForeground(Color.black);
		lbOwner.setForeground(Color.black);
		lbDescription.setForeground(Color.black);
		lbMessenger.setText(" ");
		group = new AccountGroup();
		loadTable();
		setEnableCompoment(true);
	}

	@Override
	public void refresh() throws Exception {
		setData();
		setEnableCompoment(false);
		lbName.setForeground(Color.black);
		lbLabel.setForeground(Color.black);
		lbOwner.setForeground(Color.black);
		lbDescription.setForeground(Color.black);
		lbMessenger.setText(" ");
	}

	private void loadTable() throws Exception {
		accountGroupTable.setAccountGroups(getObjects());
		scrollPaneTable.setViewportView(accountGroupTable);
	}

	public void setObject(AccountGroup object) throws Exception {
		this.group = object;
		if (object == null) {
			group = new AccountGroup();
		}
		setData();
	}

	public List<AccountGroup> getObjects() throws Exception {
		return AccountModelManager.getInstance().getGroupDetailByPath(rootGroupCustomer.getPath()).getChildren();
	}

	private void setData() {
		try {
			txtName.setText(group.getName());
			txtLabel.setText(group.getLabel());
			txtDescription.setText(group.getDescription());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean getData() {
		try {
			group.setName(txtName.getText());
			group.setOwner(ManagerAuthenticate.getInstance().getOrganizationLoginId());
			group.setParentPath(rootGroupCustomer.getPath());
			group.setPath(group.getParentPath() + "/" + group.getName());
			group.setLabel(txtLabel.getText());
			group.setDescription(txtDescription.getText());
			lbMessenger.setText("");
			return true;
		} catch (Exception e) {
			lbMessenger.setText("Lỗi định dạng dữ liệu:" + e.toString());
			return false;
		}
	}

	@Override
	public void createDataTest() {
		for (int i = 1; i <= 3; i++) {
			// Tạo đối tác
			Account account = new Account();
			account.setLoginId("HktTest" + i);
			account.setPassword("HktTest" + i);
			account.setEmail("HktTest" + i + "@gmail.com");

			try {
				AccountModelManager.getInstance().saveAccount(account);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			Customer customer = new Customer();
			customer.setLoginId("HktTest" + i);
			customer.setOrganizationLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
			customer.setCode("Mã NKH HktTest" + i);
			customer.setName("Tên NKH HktTest" + i);
			try {
				CustomerModelManager.getInstance().saveCustomer(customer);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			// Tạo nhóm đối tác
			try {
				AccountGroup root = AccountModelManager.getInstance().getRootGroupDetail().getChildren().get(0);
				AccountGroup parent = new AccountGroup(root, AccountModelManager.Customer);
				parent.setLabel(AccountModelManager.Customer);
				if (AccountModelManager.getInstance().getGroupByPath(root.getPath() + "/" + AccountModelManager.Customer) == null) {
					parent = AccountModelManager.getInstance().saveGroup(parent);
				} else {
					parent = AccountModelManager.getInstance().getGroupByPath(parent.getPath());
				}
				AccountGroup accountGroup = new AccountGroup();
				accountGroup.setName("Mã NKH HktTest" + i);
				accountGroup.setLabel("Tên NKH HktTest" + i);
				accountGroup.setParent(parent);

				accountGroup = AccountModelManager.getInstance().saveGroup(accountGroup);
				AccountMembership accountMembership = new AccountMembership();
				accountMembership.setLoginId("HktTest" + i);
				accountMembership.setGroupPath(accountGroup.getPath());
				accountMembership.setRole("Đối tác");
				accountMembership = AccountModelManager.getInstance().saveAccountMembership(accountMembership);

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
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
