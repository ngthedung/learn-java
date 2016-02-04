package com.hkt.client.swingexp.app.khohang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.component.TextPopup;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.IMyObserver;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.core.UnitMoneyJComboBox;
import com.hkt.client.swingexp.app.core.WarehousesJComboBox;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.khohang.list.TableColumn0;
import com.hkt.client.swingexp.app.khohang.list.TableModelChangeIdentityProduct;
import com.hkt.client.swingexp.app.khohang.list.TableModelIdentityProduct;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.client.swingexp.model.WarehouseModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.warehouse.entity.IdentityProduct;
import com.hkt.module.warehouse.entity.Warehouse;
import com.hkt.module.warehouse.entity.IdentityProduct.ImportType;
import com.hkt.module.warehouse.entity.IdentityProductAttribute;

/**
 * @name PHIẾU CHUYỀN KHO
 * @author Phan Anh
 * @date 10/03/2015
 */
public class ChangeWarehouse extends JPanel implements IMyObserver, IDialogMain {
	public static String										permission;
	private UnitMoneyJComboBox							cbUnitMoney;
	private WarehousesJComboBox							cbWarehouseSource, cbWarehouseDestination;
	private List<Employee>									employees;
	private TextPopup<Employee>							txtNhanVien;
	private TextPopup<IdentityProduct>			txtTimKiem;
	private MyJDateChooser									dcDateChange;
	private JTextField											textExportCode;
	private TableModelIdentityProduct				modelLeft;
	private JTable													table_Left;
	private JScrollPane											scrollPane;
	private TableColumn0										tableColumn_Left, tableColumn_Right;
	private TableModelChangeIdentityProduct	modelRight;
	private JTable													table_Right;
	private JScrollPane											scrollPaneExport;
	private List<IdentityProduct>						identityProductsLeft, identityProductsRight;
	private ExtendJTextField								txtNghiepVu, txtTongTien, txtDonGia, txtSoLuong;
	private String													warehouseCode;
	private double													totalPrice	= 0;

	public ChangeWarehouse() {
		try {
			this.setOpaque(false);
			cbWarehouseSource = new WarehousesJComboBox(false);
			cbWarehouseDestination = new WarehousesJComboBox(false);
			identityProductsLeft = new ArrayList<IdentityProduct>();
			identityProductsRight = new ArrayList<IdentityProduct>();

			if (cbWarehouseSource.getSelectedWarehouse() != null) {
				warehouseCode = cbWarehouseSource.getSelectedWarehouse().getWarehouseId();
			}

			employees = HRModelManager.getInstance().getEmployees();

			cbWarehouseSource.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (cbWarehouseSource.getSelectedWarehouse() != null) {
						warehouseCode = cbWarehouseSource.getSelectedWarehouse().getWarehouseId();
					}
					calculatorIdentityProductLeft();
				}
			});

			this.setLayout(new GridLayout(1, 2, 5, 5));
			this.add(new PANEL_LEFT());
			this.add(new PANEL_RIGHT());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class PANEL_LEFT extends JPanel {
		public PANEL_LEFT() {
			this.setOpaque(false);
			this.setLayout(new BorderLayout());
			this.setLayout(new BorderLayout(0, 5));
			this.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
			//			this.add(PanelSearch(), BorderLayout.PAGE_START);
			this.add(PanelProducts(), BorderLayout.CENTER);
		}

		private JPanel PanelSearch() {
			JPanel panel = new JPanel();
			panel.setOpaque(false);
			panel.setLayout(new BorderLayout(5, 0));

			txtSoLuong = new ExtendJTextField();
			txtDonGia = new ExtendJTextField();
			txtDonGia.setEnabled(false);
			txtDonGia.setHorizontalAlignment(JTextField.RIGHT);
			txtDonGia.setPreferredSize(new Dimension(80, 22));
			txtTimKiem = new TextPopup<IdentityProduct>(IdentityProduct.class);
			txtTimKiem.addObserver(ChangeWarehouse.this);
			txtTimKiem.setComponentFocus(txtSoLuong);
			txtSoLuong.setPreferredSize(new Dimension(50, 22));
			txtSoLuong.setHorizontalAlignment(JTextField.RIGHT);

			JPanel panel1 = new JPanel();
			panel1.setOpaque(false);
			panel1.setLayout(new BorderLayout(5, 2));
			panel1.add(createLabel("Tìm kiếm"), BorderLayout.LINE_START);
			panel1.add(txtTimKiem, BorderLayout.CENTER);

			JPanel panel2 = new JPanel();
			panel2.setOpaque(false);
			panel2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
			panel2.setPreferredSize(new Dimension(268, panel2.getHeight()));
			panel2.add(createLabel("Số lượng"));
			panel2.add(txtSoLuong);
			panel2.add(createLabel("Đơn giá"));
			panel2.add(txtDonGia);

			panel.add(panel1, BorderLayout.CENTER);
			panel.add(panel2, BorderLayout.LINE_END);
			return panel;
		}

		private JPanel PanelProducts() {
			JPanel panel = new JPanel();
			try {
				panel.setOpaque(false);
				panel.setLayout(new BorderLayout());
				scrollPane = new JScrollPane();
				modelLeft = new TableModelIdentityProduct(identityProductsLeft, warehouseCode);
				calculatorIdentityProductLeft();
				table_Left = new JTable(modelLeft);
				table_Left.setRowHeight(23);
				table_Left.setFont(new Font("Tahoma", Font.PLAIN, 14));
				table_Left.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
				((DefaultTableCellRenderer) table_Left.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
				table_Left.setBackground(Color.WHITE);
				table_Left.setFillsViewportHeight(true);
				tableColumn_Left = new TableColumn0(table_Left);
				tableColumn_Left.hideColumn0();
				table_Left.getColumnModel().getColumn(0).setMaxWidth(50);
				table_Left.getColumnModel().getColumn(1).setMinWidth(150);

				table_Left.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (e.getClickCount() > 1) {
							IdentityProduct identityProductSelected = null;
							try {
								tableColumn_Left.showColumn0();
								identityProductSelected = (IdentityProduct) table_Left.getValueAt(table_Left.getSelectedRow(), 0);
								tableColumn_Left.hideColumn0();
								calculatorIdentityProductRight(identityProductSelected);
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}
					}
				});

				table_Left.setFont(new Font("Tahoma", Font.PLAIN, 14));
				scrollPane.setViewportView(table_Left);
				scrollPane.getViewport().setBackground(Color.white);
				panel.add(scrollPane, BorderLayout.CENTER);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return panel;
		}
	}

	private class PANEL_RIGHT extends JPanel {
		public PANEL_RIGHT() {
			this.setLayout(new BorderLayout());
			this.setOpaque(false);
			this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()));
			this.add(Infomation(), BorderLayout.PAGE_START);
			scrollPaneExport = new JScrollPane();
			txtTongTien = new ExtendJTextField();
			txtTongTien.setText("0");
			modelRight = new TableModelChangeIdentityProduct(identityProductsRight);
			table_Right = new JTable(modelRight);
			table_Right.setRowHeight(23);
			table_Right.setFont(new Font("Tahoma", 0, 14));
			table_Right.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
			table_Right.setBackground(Color.WHITE);
			table_Right.setFillsViewportHeight(true);
			((DefaultTableCellRenderer) table_Right.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
			tableColumn_Right = new TableColumn0(table_Right);
			tableColumn_Right.hideColumn0();
			table_Right.getColumnModel().getColumn(0).setMaxWidth(50);
			table_Right.getColumnModel().getColumn(1).setMinWidth(150);
			scrollPaneExport.setViewportView(table_Right);
			this.add(scrollPaneExport, BorderLayout.CENTER);
			JPanel panelMoney = new JPanel();
			panelMoney.setOpaque(false);
			panelMoney.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			panelMoney.setLayout(new BorderLayout(3, 0));
			panelMoney.add(createLabel("Tổng tiền"), BorderLayout.LINE_START);
			cbUnitMoney = new UnitMoneyJComboBox();
			txtTongTien.setHorizontalAlignment(JTextField.RIGHT);
			panelMoney.add(txtTongTien, BorderLayout.CENTER);
			panelMoney.add(cbUnitMoney, BorderLayout.LINE_END);
			this.add(panelMoney, BorderLayout.PAGE_END);

			table_Right.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() >= 2) {
						if (e.getButton() == MouseEvent.BUTTON3) {
							int r = table_Right.rowAtPoint(e.getPoint());
							if (r >= 0 && r < table_Right.getRowCount()) {
								table_Right.setRowSelectionInterval(r, r);
								tableColumn_Right.showColumn0();
								IdentityProduct identityProductSelected = (IdentityProduct) table_Right.getValueAt(table_Right.getSelectedRow(), 0);
								tableColumn_Right.hideColumn0();

								if (Integer.parseInt(identityProductSelected.getModifiedBy()) > 1) {
									for (IdentityProduct id : identityProductsLeft) {
										if (id.getProductCode().equals(identityProductSelected.getProductCode())) {
											id.setCreatedBy((Integer.parseInt(id.getCreatedBy()) + 1) + "");
											identityProductSelected.setModifiedBy((Integer.parseInt(identityProductSelected.getModifiedBy()) - 1) + "");
											modelLeft.fireTableDataChanged();
											modelRight.fireTableDataChanged();
											break;
										}
									}
								} else {
									if (Integer.parseInt(identityProductSelected.getModifiedBy()) > 0) {
										for (IdentityProduct id : identityProductsLeft) {
											if (id.getProductCode().equals(identityProductSelected.getProductCode())) {
												id.setCreatedBy((Integer.parseInt(id.getCreatedBy()) + 1) + "");
												for (IdentityProduct i : identityProductsRight) {
													if (i.getProductCode().equals(identityProductSelected.getProductCode())) {
														identityProductSelected.setModifiedBy("1");
														identityProductsRight.remove(i);
														break;
													}
												}
												modelLeft.fireTableDataChanged();
												modelRight.setDataList(identityProductsRight);
												break;
											}
										}
									}
								}
								totalPrice = totalPrice - identityProductSelected.getPrice();
								txtTongTien.setText(MyDouble.valueOf(totalPrice).toString());
							}
						}
					}
				}
			});
		}

		private JPanel Infomation() {
			JPanel panel = new JPanel();
			panel.setOpaque(false);
			panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 10));
			textExportCode = new JTextField();
			SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
			textExportCode.setText(dateFormat.format(new Date()));

			txtNghiepVu = new ExtendJTextField();
			txtNhanVien = new TextPopup<Employee>(Employee.class);
			try {
				txtNhanVien.setData(employees);
				for (Employee e : employees) {
					if (e.getLoginId().equals(ManagerAuthenticate.getInstance().getLoginId())) {
						txtNhanVien.setItem(e);
						txtNhanVien.setText(e.getName());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			dcDateChange = new MyJDateChooser();
			dcDateChange.setDate(new Date());

			MyGroupLayout layoutPanel = new MyGroupLayout(panel);

			layoutPanel.add(0, 0, createLabel("Mã phiếu"));
			layoutPanel.add(0, 1, textExportCode);
			layoutPanel.add(1, 0, createLabel("Nghiệp vụ"));
			layoutPanel.add(1, 1, txtNghiepVu);
			layoutPanel.add(2, 0, createLabel("Kho nguồn"));
			layoutPanel.add(2, 1, cbWarehouseSource);
			layoutPanel.add(2, 2, createLabel("Kho đích"));
			layoutPanel.add(2, 3, cbWarehouseDestination);
			layoutPanel.add(3, 0, createLabel("Nhân viên"));
			layoutPanel.add(3, 1, txtNhanVien);
			layoutPanel.add(3, 2, createLabel("Ngày chuyển"));
			layoutPanel.add(3, 3, dcDateChange);
			layoutPanel.updateGui();
			return panel;
		}
	}

	public void calculatorIdentityProductLeft() {
		try {
			boolean flagExist = false;
			identityProductsLeft.clear();
			List<IdentityProduct> productInWarehouse = new ArrayList<IdentityProduct>();
			;
			if (warehouseCode != null)
				productInWarehouse = WarehouseModelManager.getInstance().getByImportTypeAndWarehouse(ImportType.Import, warehouseCode);

			for (IdentityProduct id : productInWarehouse) {
				for (IdentityProduct i : identityProductsLeft) {
					if (i.getProductCode().equals(id.getProductCode())) {
						flagExist = true;
						break;
					}
				}
				if (!flagExist) {
					identityProductsLeft.add(id);
				}
				flagExist = false;
			}
			modelLeft.setListData(identityProductsLeft, warehouseCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void calculatorIdentityProductRight(IdentityProduct identityProduct) {
		if (Integer.parseInt(identityProduct.getCreatedBy()) > 0) {
			boolean flagExits = false;
			if (identityProductsRight.size() > 0) {
				for (int i = 0; i < identityProductsRight.size(); i++) {
					IdentityProduct id = identityProductsRight.get(i);
					if (id.getProductCode().equals(identityProduct.getProductCode())) {
						id.setModifiedBy((Integer.parseInt(id.getModifiedBy()) + 1) + "");
						flagExits = true;
						break;
					}
				}
				if (!flagExits) {
					identityProduct.setModifiedBy("1");
					identityProductsRight.add(identityProduct);
				}
			} else {
				identityProduct.setModifiedBy("1");
				identityProductsRight.add(identityProduct);
			}
			totalPrice = totalPrice + identityProduct.getPrice();
			txtTongTien.setText(MyDouble.valueOf(totalPrice).toString());
			modelRight.setDataList(identityProductsRight);

			identityProduct.setCreatedBy((Integer.parseInt(identityProduct.getCreatedBy()) - 1) + "");
			modelLeft.fireTableDataChanged();
		} else {
			showPanelMessageBox("Số lượng hiện tại đã hết !");
			return;
		}
	}

	public JLabel createLabel(String name) {
		JLabel label = new JLabel(name);
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		return label;
	}

	public JTextField createTextField() {
		JTextField field = new JTextField();
		field.setFont(new Font("Tahoma", Font.PLAIN, 14));
		return field;
	}

	public void reset() {
		identityProductsRight.clear();
		modelRight.setDataList(identityProductsRight);
		calculatorIdentityProductLeft();
		textExportCode.setText(new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date()));
		totalPrice = 0;
		txtTongTien.setText("0");
		txtNghiepVu.setText("");
	}

	public void edit() {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này!");
			return;
		}
	}

	@Override
	public void save() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			return;
		}
		try {
			if (cbWarehouseSource.getSelectedWarehouse().getWarehouseId().equals(cbWarehouseDestination.getSelectedWarehouse().getWarehouseId())) {
				showPanelMessageBox("Vui lòng chọn kho cần chuyển khác nhau !");
				return;
			}
			for (IdentityProduct id : identityProductsRight) {
				List<IdentityProduct> identityProducts = WarehouseModelManager.getInstance().getByImportTypeAndProduct(ImportType.Import, id.getProductCode(), warehouseCode);
				int quantity = Integer.parseInt(id.getModifiedBy());
				for (int i = 0; i < quantity; i++) {
					identityProducts.get(i).setWarehouseCode(cbWarehouseDestination.getSelectedWarehouse().getWarehouseId());
					List<IdentityProductAttribute> attributes = identityProducts.get(i).getIdentityProductAttributes();
					if (attributes == null) {
						attributes = new ArrayList<IdentityProductAttribute>();
						identityProducts.get(i).setIdentityProductAttributes(attributes);
					} else {
						if (attributes.size() > 0) {
							for (int j = (attributes.size() - 1); j >= 0; j--) {
								if (attributes.get(j).getName().equals(WarehouseModelManager.WAREHOUSE)) {
									attributes.get(j).setEndDate(dcDateChange.getDate());
									break;
								}
							}
						}
					}
					IdentityProductAttribute attribute = new IdentityProductAttribute();
					attribute.setName(WarehouseModelManager.WAREHOUSE);
					attribute.setValue(cbWarehouseDestination.getSelectedWarehouse().getWarehouseId());
					attribute.setStartDate(dcDateChange.getDate());
					attributes.add(attribute);
					if(txtNhanVien.getItem() != null){
						IdentityProductAttribute attribute2 = new IdentityProductAttribute();
						attribute2.setName(WarehouseModelManager.EMPLOYEE_CHANGE);
						attribute2.setValue(txtNhanVien.getItem().getLoginId());
						attributes.add(attribute2);
					}
					WarehouseModelManager.getInstance().saveIdentityProduct(identityProducts.get(i));
				}
			}
			reset();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void delete() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) != Capability.ADMIN) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			return;
		}
	}

	@Override
	public void refresh() throws Exception {

	}

	@Override
	public void update(Object o, Object arg) {

	}

	private void showPanelMessageBox(String showMessage) {
		PanelChoise pnPanel = new PanelChoise(showMessage);
		DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
		dialog1.setTitle("Thông báo");
		dialog1.setLocationRelativeTo(null);
		dialog1.setModal(true);
		dialog1.setVisible(true);
		if (pnPanel.isDelete()) {
			return;
		}
	}
}
