package com.hkt.client.swingexp.app.khachhang;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.ITableMain;
import com.hkt.client.swingexp.app.core.TableObservable;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.nhansu.AccountIsUser;
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
public class TableListindividual extends TableObservable implements ITableMain {
	private List<Account> listgroup = new ArrayList<Account>();
	private TableModelindividual tbindividual;

	public TableListindividual() {
		try {
			listgroup = AccountModelManager.getInstance().findAccountByType(Account.Type.USER);
			for (int i = 0; i < listgroup.size(); i++) {
				if (listgroup.get(i).getEmail().indexOf("admin") >= 0) {
					listgroup.remove(i);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			listgroup = new ArrayList<Account>();
		}
		tbindividual = new TableModelindividual(listgroup);
		setModel(tbindividual);

	}

	@Override
	public void click() {
		Account account = (Account) this.getValueAt(this.getSelectedRow(), 1);

		try {

			AccountIsUser screenEmployee = new AccountIsUser(account, false);
			screenEmployee.setName("EmployeeIsUser");
			screenEmployee.setPreferredSize(new Dimension(100, 100));
			DialogMain dialog = new DialogMain(screenEmployee, true);
			dialog.setName("dlEmployeeIsUser");
			Employee employee = HRModelManager.getInstance().getBydLoginId(account.getLoginId());

			Customer customer = CustomerModelManager.getInstance().getBydLoginId(account.getLoginId());
			Supplier supplier = SupplierModelManager.getInstance().getBydLoginId(account.getLoginId());
			if (supplier != null) {
				dialog.setComponent3(CustomerModelManager.getInstance().getBtnSupplier(supplier));
			}
			if (customer != null) {
				if (supplier != null) {
					dialog.setComponent2(CustomerModelManager.getInstance().getBtnCustomer(customer));
				} else {
					dialog.setComponent3(CustomerModelManager.getInstance().getBtnCustomer(customer));
				}
			}
			if (employee != null) {
				if (customer == null && supplier == null) {
					dialog.setComponent3(CustomerModelManager.getInstance().getBtnEmployee(employee));
				} else {
					if (customer == null || supplier == null) {
						dialog.setComponent2(CustomerModelManager.getInstance().getBtnEmployee(employee));
					} else {
						dialog.setComponent1(CustomerModelManager.getInstance().getBtnEmployee(employee));
					}
				}

			}

			dialog.setIcon("canhan1.png");
			dialog.showButton(false);
			dialog.setTitle("Nhân sự mới");
			dialog.setModal(true);
			dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);

			account = AccountModelManager.getInstance().getAccountById(account.getId());
			if (account == null || account.isRecycleBin()) {
				tbindividual.removeRow(this.getSelectedRow());
			} else {
				tbindividual.fireTableRowsUpdated(this.getSelectedRow(), this.getSelectedRow());
			}

		} catch (Exception e1) {
			e1.printStackTrace();
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
			tbindividual.setData(listgroup.subList(index, pageSize), index);
		} catch (Exception ex) {
			tbindividual = new TableModelindividual(new ArrayList<Account>());
		}
		return tbindividual;
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
				dialog.setTitle("Xóa danh sách cá nhân");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
				if (panel.isDelete()) {
					for (int i = 0; i < listgroup.size();) {
						Account ac = listgroup.get(i);
						if (ac.isRecycleBin()) {
							Employee employee = HRModelManager.getInstance().getBydLoginId(ac.getLoginId());
							Customer customer = CustomerModelManager.getInstance().getBydLoginId(ac.getLoginId());
							Supplier supplier = SupplierModelManager.getInstance().getBydLoginId(ac.getLoginId());
							if ((employee != null && !employee.isRecycleBin()) || (customer != null && !customer.isRecycleBin())
							    || (supplier != null && !supplier.isRecycleBin())) {
								PanelChoise pnPanel = new PanelChoise("Xóa nhân viên, khách hàng, nhà cung cấp đi kèm?");
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
									if (employee != null) {
										HRModelManager.getInstance().deleteEmployee(employee.getId());
									}
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
