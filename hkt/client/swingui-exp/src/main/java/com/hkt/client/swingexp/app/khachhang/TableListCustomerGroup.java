package com.hkt.client.swingexp.app.khachhang;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.AccountingServiceClient;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.ITableMain;
import com.hkt.client.swingexp.app.core.TableObservable;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.nhansu.SupplierIsOrganization;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.SupplierModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.supplier.entity.Supplier;

@SuppressWarnings("serial")
public class TableListCustomerGroup extends TableObservable implements ITableMain {
	private List<Account> listgroup;
	private TableModelCustomerGroup mdTbgroup;
	private ClientContext clientContext = ClientContext.getInstance();
	@SuppressWarnings("unused")
	private AccountingServiceClient restaurantClient = clientContext.getBean(AccountingServiceClient.class);

	public TableListCustomerGroup() {
		try {
			listgroup = AccountModelManager.getInstance().findAccountByType(Account.Type.ORGANIZATION);
		} catch (Exception e) {
			listgroup = new ArrayList<Account>();
		}
		mdTbgroup = new TableModelCustomerGroup(listgroup);
		setModel(mdTbgroup);

	}

	@Override
	public void click() {

		Account account = (Account) this.getValueAt(this.getSelectedRow(), 1);
		try {
			if (CustomerModelManager.getInstance().getBydLoginId(account.getLoginId()) != null) {

				Customer customer = CustomerModelManager.getInstance().getBydLoginId(account.getLoginId());

				PartnerIsOrganization partner = new PartnerIsOrganization(customer, false);
				partner.setEdit(false);
				partner.setPreferredSize(new Dimension(100, 100));
				DialogMain dialog = new DialogMain(partner, true);
				dialog.showButton(false);
				dialog.setComponent3(CustomerModelManager.getInstance().getBtnCustomer(customer));
				dialog.setName("dlPartnerIsOrganization");
				dialog.setTitle("Khác hàng doanh nghiệp");
				dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
			} else {
				if (SupplierModelManager.getInstance().getBydLoginId(account.getLoginId()) != null) {
					Supplier supplier = SupplierModelManager.getInstance().getBydLoginId(account.getLoginId());

					SupplierIsOrganization panel = new SupplierIsOrganization(supplier, false);
					panel.setEdit(false);
					panel.setName("SupplierIsOrganization");
					panel.setPreferredSize(new Dimension(100, 100));
					DialogMain dialog = new DialogMain(panel, true);
					dialog.showButton(false);
					dialog.setComponent3(CustomerModelManager.getInstance().getBtnSupplier(supplier));
					dialog.setName("dlSupplierIsOrganization");
					dialog.setTitle("Nhà cung cấp doanh nghiệp");
					dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);

				}
			}

			try {
				account = AccountModelManager.getInstance().getAccountById(account.getId());
				if (account == null || account.isRecycleBin()) {
					mdTbgroup.removeRow(this.getSelectedRow());
				} else {
					mdTbgroup.getDataVector().set(this.getSelectedRow(), account);
					mdTbgroup.fireTableRowsUpdated(this.getSelectedRow(), this.getSelectedRow());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
		}

	}

	@Override
	public int getListSize() {
		return listgroup.size();
	}

	@Override
	public List<Integer> getColumnSum() {
		return null;
	}

	@Override
	public DefaultTableModel loadTable(int index, int pageSize) {
		try {
			mdTbgroup.setData(listgroup.subList(index, pageSize), index);
		} catch (Exception ex) {
			mdTbgroup = new TableModelCustomerGroup(new ArrayList<Account>());
		}
		return mdTbgroup;

	}

	@Override
	public boolean delete() {
		if (UIConfigModelManager.getInstance().getPermission(SupplierIsOrganization.permission) == Capability.ADMIN) {
			try {
				String str = "Xóa tất cả danh sách đã chọn ";
				PanelChoise panel = new PanelChoise(str + " ?");
				panel.setName("Xoa");
				DialogResto dialog = new DialogResto(panel, false, 0, 80);
				dialog.setName("dlXoa");
				dialog.setTitle("Xóa danh sách doanh nghiệp");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
				if (panel.isDelete()) {
					for (int i = 0; i < listgroup.size();) {
						Account ac = listgroup.get(i);
						if (ac.isRecycleBin()) {
					
							Customer customer = CustomerModelManager.getInstance().getBydLoginId(ac.getLoginId());
							Supplier supplier = SupplierModelManager.getInstance().getBydLoginId(ac.getLoginId());
							if ( (customer != null && !customer.isRecycleBin())
							    || (supplier != null && !supplier.isRecycleBin())) {
								PanelChoise pnPanel = new PanelChoise("Xóa khách hàng, nhà cung cấp đi kèm?");
								pnPanel.setName("Xoa");
								DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
								dialog1.setName("dlXoa");
								dialog1.setTitle("Xóa cá nhân");
								dialog1.setLocationRelativeTo(null);
								dialog1.setModal(true);
								dialog1.setVisible(true);
								if (pnPanel.isDelete()) {
									ac.setRecycleBin(true);
									listgroup.remove(i);
									if (customer != null) {
										CustomerModelManager.getInstance().deleteCustomer(customer);
									}
									if (supplier != null) {
										SupplierModelManager.getInstance().deleteSupplier(supplier);
									}
									AccountModelManager.getInstance().deleteAccountByLoginId(ac.getLoginId());
								} else {
									i++;
								}
							} else {
								listgroup.remove(i);
								AccountModelManager.getInstance().deleteAccountByLoginId(ac.getLoginId());
							}

						} else {
							i++;
						}
					}
				}

			} catch (Exception ex) {
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

}
