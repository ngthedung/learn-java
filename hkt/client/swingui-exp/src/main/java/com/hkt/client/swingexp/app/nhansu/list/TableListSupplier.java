package com.hkt.client.swingexp.app.nhansu.list;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.ITableMain;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.TableObservable;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.khachhang.list.TableModelSupplier;
import com.hkt.client.swingexp.app.nhansu.SupplierIsOrganization;
import com.hkt.client.swingexp.app.nhansu.SupplierIsUser;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.SupplierModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.partner.supplier.entity.Supplier;

/*
 * @author Phan Anh
 */

@SuppressWarnings("serial")
public class TableListSupplier extends TableObservable implements ITableMain {
	private List<Supplier> supplierConfig;
	private TableModelSupplier modelTable;
	public static String permission;
	private String typeEntity;
	private boolean interactive;

	public TableListSupplier() {
		try {
			load();
		} catch (Exception e) {
			e.printStackTrace();
			supplierConfig = new ArrayList<Supplier>();
		}
		modelTable = new TableModelSupplier(supplierConfig);
		setModel(modelTable);
	}

	public void load(){
		supplierConfig = SupplierModelManager.getInstance().getSuppliers(interactive);
	}
	
	public JCheckBox getCheckBox() {
		JCheckBox cbo = new JCheckBox("Nhà cung cấp đã giao dịch");
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
					p.put("NCCGiaoDich", "true");
				} else {
					p.put("NCCGiaoDich", null);
				}
				AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(),
				    p);
			}
		});
		Profile p = AccountModelManager.getInstance().getProfileConfigAdmin();
		if (p.get("NCCGiaoDich") != null) {
			cbo.setSelected(true);
		}
		return cbo;
	}

	@Override
	public void click() {
		try {
			Supplier Supplier = (Supplier) this.getValueAt(this.getSelectedRow(), 2);
			if (Supplier.getType().equals("Doanh nghiệp")) {
				SupplierIsOrganization panel = new SupplierIsOrganization(Supplier, false);
				panel.setEdit(false);
				panel.setName("SupplierIsOrganization");
				panel.setPreferredSize(new Dimension(100, 100));
				DialogMain dialog = new DialogMain(panel, true);
				dialog.showButton(false);
				dialog.setName("dlSupplierIsOrganization");
				dialog.setComponent3(CustomerModelManager.getInstance().getBtnSupplier(Supplier));
				dialog.setIcon("usernhom1.png");
				dialog.setTitle("Nhà cung cấp doanh nghiệp");
				dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
			} else {
				SupplierIsUser panel = new SupplierIsUser(Supplier, false);
				panel.setEdit(false);
				panel.setName("SupplierIsUser");
				panel.setPreferredSize(new Dimension(100, 100));
				DialogMain dialog = new DialogMain(panel, true);
				dialog.setComponent3(CustomerModelManager.getInstance().getBtnSupplier(Supplier));
				dialog.setTitle("Nhà cung cấp cá nhân");
				dialog.setIcon("usernhom1.png");
				dialog.setName("dlSupplierIsUser");
				dialog.showButton(false);
				dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
			}
			try {
				// load();
				Supplier = SupplierModelManager.getInstance().getSupplierByCode(Supplier.getCode());
				if (Supplier == null || Supplier.isRecycleBin()) {
					modelTable.removeRow(this.getSelectedRow());
				} else {
					modelTable.fireTableRowsUpdated(this.getSelectedRow(), this.getSelectedRow());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
			supplierConfig = new ArrayList<Supplier>();
		}

	}

	@Override
	public int getListSize() {
		return supplierConfig.size();
	}

	@Override
	public List<Integer> getColumnSum() {
		return null;
	}

	@Override
	public DefaultTableModel loadTable(int index, int pageSize) {
		try {
			modelTable.setData(supplierConfig.subList(index, pageSize), index);
		} catch (Exception ex) {
			modelTable = new TableModelSupplier(new ArrayList<Supplier>());
		}
		return modelTable;
	}

	@Override
	public boolean delete() {
		if (UIConfigModelManager.getInstance().getPermission(SupplierIsUser.permission) == Capability.ADMIN) {
			try {
				String str = "Xóa tất cả danh sách đã chọn ";
				PanelChoise panel = new PanelChoise(str + " ?");
				panel.setName("Xoa");
				DialogResto dialog = new DialogResto(panel, false, 0, 80);
				dialog.setName("dlXoa");
				dialog.setTitle("Xóa nhà cung cấp");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
				if (panel.isDelete()) {
					for (int i = 0; i < supplierConfig.size();) {
						Supplier supplier = supplierConfig.get(i);

						if (supplier.isRecycleBin()) {
							supplier.setRecycleBin(true);
							Supplier sup = SupplierModelManager.getInstance().getSupplierByCode(supplier.getCode());
							supplierConfig.remove(i);
							SupplierModelManager.getInstance().deleteSupplier(sup);

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
}
