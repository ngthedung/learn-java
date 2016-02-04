package com.hkt.client.swingexp.app.khachhang.list;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.ITableMain;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.TableObservable;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.PanelOption;
import com.hkt.client.swingexp.app.khachhang.PartnerIsOrganization;
import com.hkt.client.swingexp.app.khachhang.PartnerIsUser;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.GenericOptionModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.config.generic.Option;
import com.hkt.module.partner.customer.entity.Customer;

/*
 * @author Phan Anh
 */

@SuppressWarnings("serial")
public class TableListCustomer extends TableObservable implements ITableMain {
	private List<Customer> cusConfigs;
	private TableModelCustomer mdTbCustomer;
	public static String permission;
	private String typeEntity;
	private boolean interactive = false;

	public TableListCustomer() {
		try {
			load();
		} catch (Exception e) {
			e.printStackTrace();
			cusConfigs = new ArrayList<Customer>();
		}
		mdTbCustomer = new TableModelCustomer(cusConfigs);
		setModel(mdTbCustomer);

	}

	public void load() {
		cusConfigs = CustomerModelManager.getInstance().getCustomers(interactive);
	}

	public JCheckBox getCheckBox() {
		JCheckBox cbo = new JCheckBox("Khách hàng đã giao dịch");
		cbo.setFont(new Font("Tahoma", 1, 14));
		cbo.setOpaque(false);
		cbo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				JCheckBox a = (JCheckBox) e.getSource();
				interactive = a.isSelected();
				load();
				change();
				Profile p = AccountModelManager.getInstance().getProfileConfigAdmin();
				if (a.isSelected()) {
					p.put("GiaoDich", "true");
				} else {
					p.put("GiaoDich", null);
				}
				AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(),
				    p);
			}
		});
		Profile p = AccountModelManager.getInstance().getProfileConfigAdmin();
		if (p.get("GiaoDich") != null) {
			cbo.setSelected(true);
		}
		return cbo;
	}

	@Override
	public void click() {
		try {
			Customer customerConfig = (Customer) this.getValueAt(this.getSelectedRow(), 2);

			if (customerConfig.getType().equals("Doanh nghiệp")) {
				PartnerIsOrganization partner = new PartnerIsOrganization(customerConfig, false);
				partner.setEdit(false);
				partner.setPreferredSize(new Dimension(100, 100));
				DialogMain dialog = new DialogMain(partner, true);
				dialog.showButton(false);
				dialog.setName("dlPartnerIsOrganization");
				dialog.setIcon("doanhnghiep1.png");
				dialog.setTitle("Khách hàng doanh nghiệp");
				dialog.setComponent3(CustomerModelManager.getInstance().getBtnCustomer(customerConfig));
				dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
			} else {
				PartnerIsUser partner = new PartnerIsUser(customerConfig, false);
				partner.setName("PartnerIsUser");
				partner.setPreferredSize(new Dimension(100, 100));
				partner.setEdit(false);
				DialogMain dialog = new DialogMain(partner, true);
				dialog.setTitle("Khách hàng cá nhân");
				dialog.setComponent3(CustomerModelManager.getInstance().getBtnCustomer(customerConfig));
				dialog.setIcon("doanhnghiep1.png");
				dialog.setName("dlPartnerIsUser");
				dialog.showButton(false);
				dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
			}
			try {
				customerConfig = CustomerModelManager.getInstance().getCustomerByCode(customerConfig.getCode());
				if (customerConfig == null || customerConfig.isRecycleBin()) {
					mdTbCustomer.removeRow(this.getSelectedRow());
				} else {
					mdTbCustomer.fireTableRowsUpdated(this.getSelectedRow(), this.getSelectedRow());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public int getListSize() {
		return cusConfigs.size();
	}

	@Override
	public List<Integer> getColumnSum() {
		return null;
	}

	@Override
	public DefaultTableModel loadTable(int index, int pageSize) {
		try {
			mdTbCustomer.setData(cusConfigs.subList(index, pageSize), index);
		} catch (Exception ex) {
			mdTbCustomer = new TableModelCustomer(new ArrayList<Customer>());
		}

		return mdTbCustomer;
	}

	@Override
	public boolean delete() {
		if (UIConfigModelManager.getInstance().getPermission(PartnerIsUser.permission) == Capability.ADMIN) {
			try {
				String str = "Xóa tất cả danh sách đã chọn ";
				PanelChoise panel = new PanelChoise(str + " ?");
				panel.setName("Xoa");
				DialogResto dialog = new DialogResto(panel, false, 0, 80);
				dialog.setName("dlXoa");
				dialog.setTitle("Xóa khách hàng");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
				if (panel.isDelete()) {
					for (int i = 0; i < cusConfigs.size();) {
						Customer customer = cusConfigs.get(i);
						if (customer.isRecycleBin()) {
							customer.setRecycleBin(true);
							cusConfigs.remove(i);
							CustomerModelManager.getInstance().deleteCustomer(customer);

						} else {
							i++;
						}
					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}

			change();

		} else {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			return false;
		}
		return false;
	}

	@Override
	public boolean isDelete() {
		return true;
	}

	public List<Customer> getCusConfigs() {
		return cusConfigs;
	}

	public void setCusConfigs(List<Customer> cusConfigs) {
		this.cusConfigs = cusConfigs;
	}

}
