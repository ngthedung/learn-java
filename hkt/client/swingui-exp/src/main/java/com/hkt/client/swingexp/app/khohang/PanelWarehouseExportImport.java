package com.hkt.client.swingexp.app.khohang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.swingexp.app.component.ExtendJCheckBox;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.component.TextPopup;
import com.hkt.client.swingexp.app.core.DialogMain;
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
import com.hkt.client.swingexp.app.khohang.list.TableModelIdentityProductExport;
import com.hkt.client.swingexp.app.khohang.list.TableModelInvoiceItemExport;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.client.swingexp.model.WarehouseModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.accounting.entity.Invoice.ActivityType;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.core.entity.AbstractPersistable.State;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.product.entity.Product;
import com.hkt.module.warehouse.entity.IdentityProduct;
import com.hkt.module.warehouse.entity.IdentityProduct.ExportType;
import com.hkt.module.warehouse.entity.IdentityProduct.ImportType;
import com.hkt.module.warehouse.entity.IdentityProductAttribute;

/**
 * @author Phan Anh
 * @date 19/12/2014
 * @name PHIẾU NHẬP KHO - PHIẾU XUẤT KHO
 */

public class PanelWarehouseExportImport extends JPanel implements IMyObserver, IDialogMain {
	private UnitMoneyJComboBox							cbUnitMoney;
	private WarehousesJComboBox							cbWarehouse;
	private List<Employee>									employees;
	private TextPopup<Employee>							txtNhanVien;
	private MyJDateChooser									dcDateCreate;
	private ExtendJTextField								txtMaPhieuKho;
	private TableModelInvoiceItemExport			modelInvoiceItem_Left;
	private ExtendJCheckBox									chbGross;
	private JTable													tableLeft;
	private JScrollPane											scrollPane;
	private TableColumn0										tableColumn_Left, tableColumn_Right;
	private TableModelIdentityProductExport	modelIdentity_Right;
	private JTable													tableRight;
	private JScrollPane											scrollTableExport;
	private List<IdentityProduct>						identityProducts, identityProductUnGross;
	private ExtendJTextField								txtNghiepVu, txtTongTien;
	private String													warehouseCode					= null;
	private TextPopup<InvoiceItem>					txtTimKiem;
	private ExtendJTextField								txtSoLuong, txtDonGia;
	private boolean													flagGross							= true;
	private boolean													isNew									= true;
	private List<InvoiceItem>								invoiceItems					= new ArrayList<InvoiceItem>();
	private int															quantity							= 1;
	private ActivityType										activityType;
	private List<IdentityProduct>						identityProductReview	= new ArrayList<IdentityProduct>();
	public static String										permission;
	public IDialogMain											IParent;
	private boolean reset = false;

	public PanelWarehouseExportImport(List<IdentityProduct> identityProducts, final ActivityType activityType) {
		this.activityType = activityType;
		this.IParent = this;
		cbWarehouse = new WarehousesJComboBox();
		try {
			if (identityProducts != null) {
				this.identityProducts = identityProducts;
				identityProductReview.addAll(identityProducts);
				isNew = false;
			} else {
				this.identityProducts = new ArrayList<IdentityProduct>();
			}
			employees = HRModelManager.getInstance().getEmployees();

			if (cbWarehouse.getSelectedWarehouse() != null)
				warehouseCode = cbWarehouse.getSelectedWarehouse().getWarehouseId();

			this.setOpaque(false);
			this.setLayout(new GridLayout(0, 2, 10, 0));
			this.add(new PANEL_LEFT());
			this.add(new PANEL_RIGHT());

			cbWarehouse.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if (cbWarehouse.getSelectedWarehouse() != null) {
						warehouseCode = cbWarehouse.getSelectedWarehouse().getWarehouseId();
						tableColumn_Left.hideColumnX(8);
					} else {
						warehouseCode = null;
						tableColumn_Left.showColumnX(8);
					}
					loadDataTableLeft(activityType);
				}
			});

			if (identityProducts != null)
				loadDataGUI();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected class PANEL_LEFT extends JPanel {
		public PANEL_LEFT() {
			this.setOpaque(false);
			this.setLayout(new BorderLayout(0, 5));
			this.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

			//			this.add(SearchPanel(), BorderLayout.PAGE_START);
			this.add(PanelProducts(), BorderLayout.CENTER);
		}

		private JPanel SearchPanel() {
			JPanel panel = new JPanel();
			panel.setOpaque(false);
			panel.setLayout(new BorderLayout(5, 0));

			txtSoLuong = new ExtendJTextField();
			txtSoLuong.setPreferredSize(new Dimension(50, 22));
			txtSoLuong.setHorizontalAlignment(JTextField.RIGHT);
			txtDonGia = new ExtendJTextField();
			txtDonGia.setHorizontalAlignment(JTextField.RIGHT);
			txtDonGia.setPreferredSize(new Dimension(80, 22));
			txtTimKiem = new TextPopup<InvoiceItem>(InvoiceItem.class);
			txtTimKiem.addObserver(PanelWarehouseExportImport.this);
			txtTimKiem.setComponentFocus(txtSoLuong);
			txtTimKiem.setData(invoiceItems);

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
			panel.setOpaque(false);
			panel.setLayout(new BorderLayout(5, 0));

			scrollPane = new JScrollPane();
			tableLeft = new JTable();
			tableLeft.setRowHeight(23);
			tableLeft.setFont(new Font("Tahoma", Font.PLAIN, 14));
			tableLeft.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
			((DefaultTableCellRenderer) tableLeft.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
			tableLeft.setBackground(Color.WHITE);
			tableLeft.setFillsViewportHeight(true);

			modelInvoiceItem_Left = new TableModelInvoiceItemExport(invoiceItems);
			loadDataTableLeft(activityType);

			tableLeft.setModel(modelInvoiceItem_Left);
			tableColumn_Left = new TableColumn0(tableLeft);
			tableColumn_Left.hideColumn0();
			tableLeft.getColumnModel().getColumn(0).setMinWidth(10);
			tableLeft.getColumnModel().getColumn(1).setMinWidth(120);
			tableLeft.getColumnModel().getColumn(2).setMinWidth(180);
			scrollPane.setViewportView(tableLeft);
			scrollPane.getViewport().setBackground(Color.WHITE);
			panel.add(scrollPane, BorderLayout.CENTER);

			tableLeft.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() >= 2 && e.getButton() == MouseEvent.BUTTON1) {
						try {
							if (!isNew) {
								showPanelMessageBox("Chọn 'Sửa' để thay đổi thông tin !");
								return;
							}
							tableColumn_Left.showColumn0();
							InvoiceItem invoiceItem = (InvoiceItem) tableLeft.getValueAt(tableLeft.getSelectedRow(), 0);
							tableColumn_Left.hideColumn0();
							addIdentityProduct(invoiceItem);
							calculatorTotalTableViewRight();
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				}
			});

			return panel;
		}
	}

	protected class PANEL_RIGHT extends JPanel {
		public PANEL_RIGHT() {
			this.setOpaque(false);
			this.setLayout(new BorderLayout());
			this.setBorder(BorderFactory.createEtchedBorder());

			modelIdentity_Right = new TableModelIdentityProductExport(identityProducts, null, activityType);
			tableRight = new JTable(modelIdentity_Right);
			tableRight.setRowHeight(23);
			tableRight.setFont(new Font("Tahoma", 0, 14));
			tableRight.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
			((DefaultTableCellRenderer) tableRight.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
			tableRight.setBackground(Color.WHITE);
			tableRight.setFillsViewportHeight(true);
			tableColumn_Right = new TableColumn0(tableRight);
			tableColumn_Right.hideColumn0();
			tableRight.getColumnModel().getColumn(0).setMinWidth(20);
			tableRight.getColumnModel().getColumn(1).setMinWidth(200);
			scrollTableExport = new JScrollPane();
			scrollTableExport.setViewportView(tableRight);

			this.add(Infomation(), BorderLayout.PAGE_START);
			this.add(scrollTableExport, BorderLayout.CENTER);
			this.add(PanelMoney(), BorderLayout.PAGE_END);

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

			tableRight.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					/** Khi ấn nút Delete */
					if (e.getKeyCode() == KeyEvent.VK_DELETE) {
						if (!isNew) {
							showPanelMessageBox("Chọn 'Sửa' để thay đổi thông tin !");
							return;
						}
						/** Trường hợp xuất kho */
						if (activityType.equals(ActivityType.Receipt)) {
							tableColumn_Right.showColumn0();
							IdentityProduct identityProductSelected = (IdentityProduct) (tableRight.getValueAt(tableRight.getSelectedRow(), 0));
							tableColumn_Right.hideColumn0();
							String warehouseIdIdentityProductSelected = identityProductSelected.getWarehouseCode();
							if (warehouseIdIdentityProductSelected == null)
								warehouseIdIdentityProductSelected = "";
							boolean flagOn = true;
							//Tìm kiếm danh sách identityProduct để xóa SP đã chọn <Bảng phải>
							for (int j = 0; j < identityProducts.size(); j++) {
								String warehouseIdIdentityProduct = identityProducts.get(j).getWarehouseCode();
								if (warehouseIdIdentityProduct == null)
									warehouseIdIdentityProduct = "";
								if (identityProducts.get(j).getProductCode().equals(identityProductSelected.getProductCode()) && warehouseIdIdentityProduct.equals(warehouseIdIdentityProductSelected) && !identityProducts.get(j).getPersistableState().equals(State.DELETED)) {
									identityProducts.get(j).setPersistableState(State.DELETED);
									//Tìm kiếm danh sách InvoiceItem để + SP vào <Bảng trái>
									for (InvoiceItem item : invoiceItems) {
										if (item.getId() == identityProducts.get(j).getInvoiceItemIdExport()) {
											item.setQuantityReal(item.getQuantityReal() - 1);
											flagOn = false;
											break;
										}
									}
									if (flagOn) {
										try {
											InvoiceDetail invoiceDetail = AccountingModelManager.getInstance().findOneByInvoiceItem(identityProductSelected.getInvoiceItemIdImport());
											invoiceItems = invoiceDetail.getItems();
											for (InvoiceItem item : invoiceItems) {
												if (item != null)
													if (item.getId() == identityProducts.get(j).getInvoiceItemIdExport()) {
														item.setQuantityReal(item.getQuantityReal() - 1);
													}
											}
										} catch (Exception e1) {
											e1.printStackTrace();
										}
									}
								}
							}
						}
						/** Trường hợp nhập kho */
						else {
							if (activityType.equals(ActivityType.Payment)) {
								tableColumn_Right.showColumn0();
								IdentityProduct identityProductSelected = (IdentityProduct) tableRight.getValueAt(tableRight.getSelectedRow(), 0);
								tableColumn_Right.hideColumn0();
								//Kiểm tra lô hàng này đã được xuất chưa?
								if (!isNew) {
									try {
										InvoiceDetail invoiceDetail = AccountingModelManager.getInstance().getInvoiceDetailByCode(identityProductSelected.getInvoiceCode());
										for (InvoiceItem item : invoiceDetail.getItems()) {
											List<IdentityProduct> identityList = WarehouseModelManager.getInstance().getByInvoiceItemIdImport(item.getId());
											for (IdentityProduct ip : identityList) {
												if (ip.getExportType() == ExportType.Export) {
													showPanelMessageBox("Lô hàng này đã được xuất, không thể xóa !");
													return;
												}
											}
										}
									} catch (Exception e2) {
										e2.printStackTrace();
									}
								}
								//Thực hiện thao tác xóa
								boolean flagOn = true;
								String warehouseIdIdentityProductSelected = "";
								if (identityProductSelected.getWarehouseCode() != null)
									warehouseIdIdentityProductSelected = identityProductSelected.getWarehouseCode();
								for (int i = 0; i < identityProducts.size(); i++) {
									String warehouseIdIdentityProduct = "";
									if (identityProducts.get(i).getWarehouseCode() != null)
										warehouseIdIdentityProduct = identityProducts.get(i).getWarehouseCode();
									if (identityProducts.get(i).getProductCode().equals(identityProductSelected.getProductCode()) && warehouseIdIdentityProduct.equals(warehouseIdIdentityProductSelected) && !identityProducts.get(i).getPersistableState().equals(State.DELETED)) {
										identityProducts.get(i).setPersistableState(State.DELETED);
										for (InvoiceItem item : invoiceItems) {
											if (item.getId() == identityProducts.get(i).getInvoiceItemIdImport()) {
												item.setQuantityReal(item.getQuantityReal() - 1);
												flagOn = false;
												break;
											}
										}
										if (flagOn) {
											try {
												InvoiceDetail invoiceDetail = AccountingModelManager.getInstance().findOneByInvoiceItem(identityProductSelected.getInvoiceItemIdImport());
												invoiceItems = invoiceDetail.getItems();
												for (InvoiceItem item : invoiceItems) {
													if (item != null)
														if (item.getId() == identityProducts.get(i).getInvoiceItemIdImport()) {
															item.setQuantityReal(item.getQuantityReal() - 1);
														}
												}
											} catch (Exception e1) {
												e1.printStackTrace();
											}
										}
									}
								}
							}
						}
						calculatorTotalTableViewRight();
						refeshDataTableLeft();
					}
				}
			});

			tableRight.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() >= 2) {
						if (!isNew) {
							showPanelMessageBox("Chọn 'Sửa' để thay đổi thông tin !");
							return;
						}
						if (e.getButton() == MouseEvent.BUTTON1) {
							tableColumn_Right.showColumn0();
							IdentityProduct identityProductSelected = (IdentityProduct) tableRight.getValueAt(tableRight.getSelectedRow(), 0);
							tableColumn_Right.hideColumn0();

							InvoiceItem invoiceItem = null;
							tableColumn_Left.showColumn0();
							for (int j = 0; j < tableLeft.getRowCount(); j++) {
								InvoiceItem item = ((InvoiceItem) tableLeft.getValueAt(j, 0));
								if (item.getProductCode().equals(identityProductSelected.getProductCode()) && (item.getId() == identityProductSelected.getInvoiceItemIdExport())) {
									invoiceItem = item;
									break;
								}
							}
							tableColumn_Left.hideColumn0();

							PanelEditIdentityImportExport panelEditIdentity = new PanelEditIdentityImportExport(identityProductSelected, identityProducts, invoiceItem, IParent);
							DialogResto dialog = new DialogResto(panelEditIdentity, false, 400, 250);
							dialog.setLocationRelativeTo(null);
							dialog.setTitle("Sửa thông tin chi tiết");
							dialog.setModal(true);
							dialog.setVisible(true);
						} else {
							if (e.getButton() == MouseEvent.BUTTON3) {
								int r = tableRight.rowAtPoint(e.getPoint());
								if (r >= 0 && r < tableRight.getRowCount()) {
									tableRight.setRowSelectionInterval(r, r);
									tableColumn_Right.showColumn0();
									IdentityProduct identityProductSelect = (IdentityProduct) (tableRight.getValueAt(tableRight.getSelectedRow(), 0));
									tableColumn_Right.hideColumn0();

									/** Trường hợp xuất kho */
									if (activityType.equals(ActivityType.Receipt)) {
										for (IdentityProduct ip : identityProducts) {
											if ((ip.getInvoiceItemIdExport() == identityProductSelect.getInvoiceItemIdExport()) && !ip.getPersistableState().equals(State.DELETED)) {
												ip.setPersistableState(State.DELETED);
												for (InvoiceItem item : invoiceItems) {
													if (item.getId().equals(identityProductSelect.getInvoiceItemIdExport())) {
														item.setQuantityReal(item.getQuantityReal() - 1);
														break;
													}
												}
												break;
											}
										}
									}
									/** Trường hợp nhập kho */
									else {
										if (activityType.equals(ActivityType.Payment)) {
											for (IdentityProduct ip : identityProducts) {
												if ((ip.getInvoiceItemIdImport() == identityProductSelect.getInvoiceItemIdImport()) && !ip.getPersistableState().equals(State.DELETED)) {
													ip.setPersistableState(State.DELETED);
													for (InvoiceItem item : invoiceItems) {
														if (item.getId().equals(identityProductSelect.getInvoiceItemIdImport())) {
															item.setQuantityReal(item.getQuantityReal() - 1);
															break;
														}
													}
													break;
												}
											}
										}
									}
									calculatorTotalTableViewRight();
									refeshDataTableLeft();
								}
							}
						}
					}
				}
			});

			if (chbGross.isSelected() == true) {
				flagGross = true;
			}
			if (!isNew) {
				calculatorTotalTableViewRight();
			}
		}

		private JPanel Infomation() {
			JPanel panel = new JPanel();
			panel.setOpaque(false);
			panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 10));
			txtMaPhieuKho = new ExtendJTextField();
			SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
			txtMaPhieuKho.setText(dateFormat.format(new Date()));
			txtNghiepVu = new ExtendJTextField();
			txtNhanVien = new TextPopup<Employee>(Employee.class);
			dcDateCreate = new MyJDateChooser();
			dcDateCreate.setDateFormatString("dd/MM/yyyy");
			dcDateCreate.setDate(new Date());
			chbGross = new ExtendJCheckBox("Nhóm sản phẩm");
			chbGross.setSelected(true);
			chbGross.setOpaque(false);
			chbGross.setEnabled(false);

			MyGroupLayout layoutPanel = new MyGroupLayout(panel);
			layoutPanel.add(0, 0, createLabel("Mã phiếu"));
			layoutPanel.add(0, 1, txtMaPhieuKho);
			layoutPanel.add(1, 0, createLabel("Nghiệp vụ"));
			layoutPanel.add(1, 1, txtNghiepVu);
			layoutPanel.add(2, 0, createLabel("Nhân viên"));
			layoutPanel.add(2, 1, txtNhanVien);
			layoutPanel.add(2, 2, createLabel("Ngày tạo"));
			layoutPanel.add(2, 3, dcDateCreate);
			if (activityType.equals(ActivityType.Payment))
				layoutPanel.add(3, 0, createLabel("Kho nhập"));
			else
				layoutPanel.add(3, 0, createLabel("Kho xuất"));
			layoutPanel.add(3, 1, cbWarehouse);
			//			layoutPanel.add(4, 0, createLabel(""));
			//			layoutPanel.add(4, 1, chbGross);
			layoutPanel.updateGui();
			return panel;
		}

		private JPanel PanelMoney() {
			JPanel panel = new JPanel();
			panel.setOpaque(false);
			panel.setLayout(new BorderLayout(5, 0));
			panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
			panel.add(createLabel("Tổng tiền"), BorderLayout.LINE_START);
			txtTongTien = new ExtendJTextField();
			txtTongTien.setHorizontalAlignment(JTextField.RIGHT);
			cbUnitMoney = new UnitMoneyJComboBox();
			txtTongTien.setText("0");
			panel.add(txtTongTien, BorderLayout.CENTER);
			panel.add(cbUnitMoney, BorderLayout.LINE_END);
			return panel;
		}
	}

	public void loadDataGUI() {
		if (UIConfigModelManager.getInstance().getPermission(getToolTipText()) == Capability.READ) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này!");
			return;
		}

		String shipmentImportCode = null;
		String warehouseCode = null;
		String employee = null;
		String tenPhieu = null;
		Date dateCreated = new Date();
		boolean flag = false;

		for (int i = 0; i < identityProducts.size(); i++) {
			if(activityType.equals(ActivityType.Payment))
				shipmentImportCode = identityProducts.get(i).getShipmentImportCode();
			else 
				shipmentImportCode = identityProducts.get(i).getShipmentExportCode();
			dateCreated = identityProducts.get(i).getImportDate();
			tenPhieu = identityProducts.get(i).getName();
			List<IdentityProductAttribute> attributes = identityProducts.get(i).getIdentityProductAttributes();
			for (IdentityProductAttribute identityProductAttribute : attributes) {
				if ((identityProductAttribute.getName().equals(WarehouseModelManager.EMPLOYEE_IMPORT) && activityType.equals(ActivityType.Payment)) || (identityProductAttribute.getName().equals(WarehouseModelManager.EMPLOYEE_EXPORT) && activityType.equals(ActivityType.Receipt))) {
					employee = identityProductAttribute.getValue();
					break;
				}
			}
			if (identityProducts.get(i).getWarehouseCode() != null)
				if (i >= 1) {
					if (warehouseCode != null && !warehouseCode.equals(identityProducts.get(i).getWarehouseCode())) {
						flag = true;
						break;
					}
				} else
					warehouseCode = identityProducts.get(i).getWarehouseCode();
		}

		if (flag)
			cbWarehouse.setSelectedIndex(0);
		else
			cbWarehouse.setSelectWarehouse(warehouseCode);

		try {
			if (employee != null) {
				Employee emp = HRModelManager.getInstance().getBydLoginId(employee);
				txtNhanVien.setItem(emp);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		txtMaPhieuKho.setText(shipmentImportCode);
		dcDateCreate.setDate(dateCreated);
		txtNghiepVu.setText(tenPhieu);
		txtNhanVien.setEnabled(false);
		txtMaPhieuKho.setEnabled(false);
		cbWarehouse.setEnabled(false);
		dcDateCreate.setEnabled(false);
		txtNghiepVu.setEnabled(false);
		//			txtTimKiem.setEnabled(false);
		//			txtSoLuong.setEnabled(false);
		//			txtDonGia.setEnabled(false);

		loadDataTableLeft(activityType);
		calculatorTotalTableViewRight();
	}

	public JLabel createLabel(String name) {
		JLabel label = new JLabel(name);
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		return label;
	}

	public void reset() {
		identityProducts.clear();
		loadDataTableLeft(activityType);
		refeshDataTableLeft();
		refeshDataTableRight();
		txtMaPhieuKho.setText(new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date()));
		txtTongTien.setText("0");
		txtNghiepVu.setText("");
		txtNhanVien.setItem(null);
		txtNhanVien.setText("");
		cbWarehouse.setEnabled(true);
		//		txtSoLuong.setText("");
		//		txtDonGia.setText("");
		//		txtTimKiem.setItem(null);
		//		txtTimKiem.setText("");
		isNew = true;
	}

	@Override
	public void save() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này!");
			return;
		}
		try {
			if (!isNew) {
				showPanelMessageBox("Chọn 'Sửa' để thay đổi thông tin !");
				return;
			}
			if (activityType.equals(ActivityType.Payment)) {
				if (identityProducts.size() <= 0) {
					showPanelMessageBox("Không có lô hàng nào !");
					return;
				}
				try {
					for (int i = 0; i < identityProducts.size(); i++) {
						IdentityProduct identityProduct = identityProducts.get(i);
						if (identityProduct.getPersistableState().equals(State.DELETED)) {
							WarehouseModelManager.getInstance().deleteIdentityProduct(identityProduct);
							InvoiceDetail detail = AccountingModelManager.getInstance().findOneByInvoiceItem(identityProduct.getInvoiceItemIdImport());
							detail = AccountingModelManager.getInstance().getInvoiceDetailByCode(detail.getInvoiceCode());
							List<InvoiceItem> items = detail.getItems();
							for (InvoiceItem invoiceItem : items) {
								List<IdentityProduct> identityProducts = WarehouseModelManager.getInstance().getByInvoiceItemIdImport(invoiceItem.getId());
								invoiceItem.setQuantityReal(identityProducts.size());
							}
							AccountingModelManager.getInstance().saveInvoiceDetail(detail);
						} else {
							if (!new SimpleDateFormat("dd/MM/yyyy").format(dcDateCreate.getDate()).equals(new SimpleDateFormat("dd/MM/yyyy").format(identityProduct.getImportDate()))) {
								identityProduct.setModifiedTime(identityProduct.getImportDate());
								identityProduct.setImportDate(dcDateCreate.getDate());
							}
							identityProduct.setName(txtNghiepVu.getText());
							Employee employee = txtNhanVien.getItem();
							List<IdentityProductAttribute> attributes = new ArrayList<IdentityProductAttribute>();
							if (employee != null) {
								IdentityProductAttribute attribute1 = new IdentityProductAttribute();
								attribute1.setName(WarehouseModelManager.EMPLOYEE_IMPORT);
								attribute1.setValue(employee.getLoginId());
								attributes.add(attribute1);
							}
							if(identityProduct.getWarehouseCode() != null && !identityProduct.getWarehouseCode().equals("")){
								IdentityProductAttribute attribute2 = new IdentityProductAttribute();
								attribute2.setName(WarehouseModelManager.WAREHOUSE);
								attribute2.setValue(identityProduct.getWarehouseCode());
								attribute2.setStartDate(dcDateCreate.getDate());
								attributes.add(attribute2);
							}
							identityProduct.setIdentityProductAttributes(attributes);
							WarehouseModelManager.getInstance().saveIdentityProduct(identityProduct);
							InvoiceDetail detail = AccountingModelManager.getInstance().findOneByInvoiceItem(identityProduct.getInvoiceItemIdImport());
							detail = AccountingModelManager.getInstance().getInvoiceDetailByCode(detail.getInvoiceCode());
							List<InvoiceItem> items = detail.getItems();
							for (InvoiceItem invoiceItem : items) {
								List<IdentityProduct> identityProducts = WarehouseModelManager.getInstance().getByInvoiceItemIdImport(invoiceItem.getId());
								invoiceItem.setQuantityReal(identityProducts.size());
							}
							AccountingModelManager.getInstance().saveInvoiceDetail(detail);
						}
					}
					reset();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else {
				if (activityType.equals(ActivityType.Receipt)) {
					for (IdentityProduct identityProduct : identityProducts) {
						IdentityProduct product = null;
						String invoiceEdit = null;
						long itemEdit = -1;
						//Trường hợp sửa lại xóa bớt các SP  
						if (identityProduct.getPersistableState().equals(State.DELETED)) {
							if(identityProduct.getId() != null){
								invoiceEdit = identityProduct.getInvoiceExportCode();
								itemEdit = identityProduct.getInvoiceItemIdExport();
								identityProduct.setPersistableState(State.ORIGIN);
								identityProduct.setShipmentExportCode(null);
								identityProduct.setInvoiceItemIdExport(0);
								identityProduct.setInvoiceExportCode(null);
								identityProduct.setExportDate(null);
								identityProduct.setExportType(ExportType.NotExport);
								identityProduct.setProfit(-identityProduct.getPriceExport());
								identityProduct.setPriceExport(0);
								identityProduct.setCurrencyExportRate(0);
								product = WarehouseModelManager.getInstance().saveIdentityProduct(identityProduct); 
							}
						} 
						//Trường hợp thêm mới các SP xuất kho
						else {
							if(identityProduct.getId() == null){
								List<IdentityProduct> identityProductInWarehouse = getByWarehouseAndProductImportNotExport(identityProduct.getProductCode(), identityProduct.getWarehouseCode());
								if(identityProductInWarehouse != null && identityProductInWarehouse.size() > 0){
									identityProductInWarehouse.get(0).setInvoiceExportCode(identityProduct.getInvoiceExportCode());
									identityProductInWarehouse.get(0).setInvoiceItemIdExport(identityProduct.getInvoiceItemIdExport());
									identityProductInWarehouse.get(0).setCurrencyExportRate(identityProduct.getCurrencyExportRate());
									identityProductInWarehouse.get(0).setPriceExport(identityProduct.getPriceExport());							
									identityProductInWarehouse.get(0).setExportType(ExportType.Export);
									identityProductInWarehouse.get(0).setExportDate(dcDateCreate.getDate());
									identityProductInWarehouse.get(0).setShipmentExportCode(txtMaPhieuKho.getText());
									Employee employee = txtNhanVien.getItem();
									if (employee != null) {
										IdentityProductAttribute attribute = new IdentityProductAttribute();
										attribute.setName(WarehouseModelManager.EMPLOYEE_EXPORT);
										attribute.setValue(employee.getLoginId());
										List<IdentityProductAttribute> attributes = identityProductInWarehouse.get(0).getIdentityProductAttributes();
										if (attributes == null) {
											attributes = new ArrayList<IdentityProductAttribute>();
										}
										attributes.add(attribute);
										identityProductInWarehouse.get(0).setIdentityProductAttributes(attributes);
									}
									product = WarehouseModelManager.getInstance().saveIdentityProduct(identityProductInWarehouse.get(0));
								}
							}
						}
						
						if(product != null || invoiceEdit != null){
						//TH thêm mới
							if (product.getInvoiceExportCode() != null) {
								InvoiceDetail detail = AccountingModelManager.getInstance().getInvoiceDetailByCode(product.getInvoiceExportCode());
								List<InvoiceItem> items = detail.getItems();
								for (InvoiceItem invoiceItem : items) {
									if (invoiceItem.getId() == product.getInvoiceItemIdExport()) {
										List<IdentityProduct> identityProductSaved = WarehouseModelManager.getInstance().getByInvoiceItemIdExport(invoiceItem.getId());
										invoiceItem.setQuantityReal(identityProductSaved.size());
									}
								}
								AccountingModelManager.getInstance().saveInvoiceDetail(detail);
							} 
							//TH Sửa không xóa nữa
							else {
								InvoiceDetail detail = AccountingModelManager.getInstance().getInvoiceDetailByCode(invoiceEdit);
								List<InvoiceItem> items = detail.getItems();
								for (InvoiceItem invoiceItem : items) {
									if (invoiceItem.getId() == itemEdit) {
										List<IdentityProduct> identityProductSaved = WarehouseModelManager.getInstance().getByInvoiceItemIdExport(invoiceItem.getId());
										invoiceItem.setQuantityReal(identityProductSaved.size());
									}
								}
								AccountingModelManager.getInstance().saveInvoiceDetail(detail);
							}
						}
					}
					reset();
				}
			}
				((JDialog) getRootPane().getParent()).dispose();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void delete() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) != Capability.ADMIN) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này!");
			return;
		}
		if (!isNew) {
			showPanelMessageBox("Chọn 'Sửa' để thay đổi thông tin !");
			return;
		}
		if (activityType.equals(ActivityType.Payment)) {
			for (int i = 0; i < identityProducts.size(); i++) {
				IdentityProduct identityProduct = identityProducts.get(i);
				if (identityProduct.getId() != null) {
					try {
						String invoiceCode = identityProduct.getInvoiceCode();
						InvoiceDetail detail = AccountingModelManager.getInstance().getInvoiceDetailByCode(invoiceCode);
						List<InvoiceItem> invoiceItems = detail.getItems();
						for (InvoiceItem invoiceItem : invoiceItems) {
							if (invoiceItem.getId() == identityProduct.getInvoiceItemIdImport()) {
								invoiceItem.setQuantityReal(invoiceItem.getQuantityReal() - 1);
							}
						}
						AccountingModelManager.getInstance().saveInvoiceDetail(detail);
						WarehouseModelManager.getInstance().deleteIdentityProduct(identityProduct);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			reset();
		} else {
			if (activityType.equals(ActivityType.Receipt)) {
				for (IdentityProduct identityProduct : identityProducts) {
					if (identityProduct.getId() != null && identityProduct.getExportType() == ExportType.Export) {
						InvoiceDetail detail = AccountingModelManager.getInstance().getInvoiceDetailByCode(identityProduct.getInvoiceExportCode());
						List<InvoiceItem> items = detail.getItems();
						for (InvoiceItem invoiceItem : items) {
							if (invoiceItem.getId() == identityProduct.getInvoiceItemIdExport()) {
								invoiceItem.setQuantityReal(invoiceItem.getQuantityReal() - 1);
							}
						}
						AccountingModelManager.getInstance().saveInvoiceDetail(detail);
						WarehouseModelManager.getInstance().deleteIdentityProductExport(identityProduct);
					}
				}
				reset();
			}
		}
		((JDialog) getRootPane().getParent()).dispose();
	}

	@Override
	public void edit() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
		} else {
			if (isNew == false && activityType.equals(ActivityType.Payment)) {
				for (IdentityProduct identityProduct : identityProducts) {
					try {
						InvoiceDetail invoiceDetail = AccountingModelManager.getInstance().getInvoiceDetailByCode(identityProduct.getInvoiceCode());
						for (InvoiceItem item : invoiceDetail.getItems()) {
							List<IdentityProduct> identityList = WarehouseModelManager.getInstance().getByInvoiceItemIdImport(item.getId());
							for (IdentityProduct identityProduct2 : identityList) {
								if (identityProduct2.getExportType() == ExportType.Export) {
									showPanelMessageBox("Lô hàng này đã được xuất, không được sửa !");
									((DialogMain) this.getRootPane().getParent()).showButton(false);
									return;
								}
							}
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
			isNew = true;
			reset = true;
			txtNghiepVu.setEnabled(true);
			dcDateCreate.setEnabled(true);
			cbWarehouse.setEnabled(true);
			txtNhanVien.setEnabled(true);
			//			chbGross.setEnabled(true);
			//			txtTimKiem.setEnabled(true);
			//			txtSoLuong.setEnabled(true);
		}
	}

	@Override
	public void refresh() throws Exception {
		identityProducts = identityProductReview;
		String shipmentImportCode = null;
		String warehouseCode = null;
		String employee = null;
		String tenPhieu = null;
		Date dateCreated = new Date();
		boolean flag = false;

		for (int i = 0; i < identityProducts.size(); i++) {
			if(identityProducts.get(i).getPersistableState().equals(State.DELETED)){
				identityProducts.get(i).setPersistableState(State.ORIGIN);
			}
			shipmentImportCode = identityProducts.get(i).getShipmentImportCode();
			dateCreated = identityProducts.get(i).getImportDate();
			tenPhieu = identityProducts.get(i).getName();
			List<IdentityProductAttribute> attributes = identityProducts.get(i).getIdentityProductAttributes();
			for (IdentityProductAttribute identityProductAttribute : attributes) {
				if ((identityProductAttribute.getName().equals(WarehouseModelManager.EMPLOYEE_IMPORT) && activityType.equals(ActivityType.Payment)) || (identityProductAttribute.getName().equals(WarehouseModelManager.EMPLOYEE_EXPORT) && activityType.equals(ActivityType.Receipt))) {
					employee = identityProductAttribute.getValue();
					break;
				}
			}
			if (identityProducts.get(i).getWarehouseCode() != null)
				if (i >= 1) {
					if (warehouseCode != null && !warehouseCode.equals(identityProducts.get(i).getWarehouseCode())) {
						flag = true;
						break;
					}
				} else
					warehouseCode = identityProducts.get(i).getWarehouseCode();
		}

		if (flag)
			cbWarehouse.setSelectedIndex(0);
		else
			cbWarehouse.setSelectWarehouse(warehouseCode);

		try {
			if (employee != null) {
				Employee emp = HRModelManager.getInstance().getBydLoginId(employee);
				txtNhanVien.setItem(emp);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		txtMaPhieuKho.setText(shipmentImportCode);
		dcDateCreate.setDate(dateCreated);
		txtNghiepVu.setText(tenPhieu);
		txtNhanVien.setEnabled(false);
		txtMaPhieuKho.setEnabled(false);
		cbWarehouse.setEnabled(false);
		dcDateCreate.setEnabled(false);
		txtNghiepVu.setEnabled(false);
		//			txtTimKiem.setEnabled(false);
		//			txtSoLuong.setEnabled(false);
		//			txtDonGia.setEnabled(false);

		loadDataTableLeft(activityType);
		calculatorTotalTableViewRight();
		isNew = false;
	}

	//Thêm sản phẩm vào list danh sách bảng phải và bớt quantity từ InvoiceItem
	public void addIdentityProduct(InvoiceItem item) {
		/** Trường hợp xuất kho */
		if (activityType.equals(ActivityType.Receipt)) {
			cbWarehouse.setEnabled(false);
			if (identityProducts == null) {
				identityProducts = new ArrayList<IdentityProduct>();
			}
			String invoiceCode = item.getInvoiceCode();
			String productCode = item.getProductCode();
			double quantityAlive = item.getQuantity() - (item.getQuantityReal() + quantity);
			List<IdentityProduct> identityProductInWarehouse = getByWarehouseAndProductImportNotExport(productCode, item.getWarehouseCode());
			int quantityImportCurrent = identityProductInWarehouse.size();
			if (quantityAlive < 0 || quantityImportCurrent == 0) {
				String errorMessage = "";
				try {
					Product product = ProductModelManager.getInstance().getProductByCode(productCode);
					errorMessage = "Sản phẩm '" + product + "' trong kho đã hết !";
				} catch (Exception e) {
					errorMessage = "Số lượng sản phẩm trong kho đã hết !";
				}
				showPanelMessageBox(errorMessage);
				return;
			} else {
				int countQuantity = 0;
				String warehouseCode = item.getWarehouseCode();
				if(warehouseCode == null) warehouseCode = "";
				for (IdentityProduct ip : identityProducts) {
					String warehouseIp = ip.getWarehouseCode();
					if(warehouseIp == null) warehouseIp = "";
					if (ip.getProductCode().equals(productCode) && warehouseIp.equals(warehouseCode)) {
						countQuantity++;
					}
				}
				if (countQuantity == quantityImportCurrent) {
					showPanelMessageBox("Số lượng trong kho đã hết !");
					return;
				}
			}
			for (int i = 0; i < quantity; i++) {
				boolean flagExist = false;
				for (IdentityProduct ip : identityProducts) {
					if ((item.getId() == ip.getInvoiceItemIdExport()) && ip.getPersistableState().equals(State.DELETED)) {
						ip.setPersistableState(State.ORIGIN);
						item.setQuantityReal(item.getQuantityReal() + 1);
						flagExist = true;
						break;
					}
				}

				if (!flagExist) {
					IdentityProduct identityProduct = new IdentityProduct();
					identityProduct.setInvoiceCode(invoiceCode);
					identityProduct.setInvoiceExportCode(invoiceCode);
					identityProduct.setShipmentExportCode(txtMaPhieuKho.getText());
					identityProduct.setProductCode(productCode);
					identityProduct.setInvoiceItemIdExport(item.getId());
					identityProduct.setExportDate(dcDateCreate.getDate());
					identityProduct.setPriceExport(item.getUnitPrice());
					identityProduct.setCurrencyExportRate(item.getCurrencyRate());
					identityProduct.setUnitPrice(item.getCurrencyUnit());
					identityProduct.setWarehouseCode(item.getWarehouseCode());
					identityProducts.add(identityProduct);
					item.setQuantityReal(item.getQuantityReal() + 1);
				}
			}
		}
		/** Trường hợp nhập kho */
		else {
			if (activityType.equals(ActivityType.Payment)) {
				cbWarehouse.setEnabled(false);
				if (identityProducts == null) {
					identityProducts = new ArrayList<IdentityProduct>();
				}
				if (item.getQuantity() == item.getQuantityReal()) {
					showPanelMessageBox("Sản phẩm nhập kho đã hết !");
					return;
				}

				for (int i = 0; i < quantity; i++) {
					boolean flagExist = false;
					for (IdentityProduct identityProductDisplay : identityProducts) {
						if ((item.getId() == identityProductDisplay.getInvoiceItemIdImport()) && identityProductDisplay.getPersistableState().equals(State.DELETED)) {
							identityProductDisplay.setPersistableState(State.ORIGIN);
							item.setQuantityReal(item.getQuantityReal() + 1);
							flagExist = true;
							break;
						}
					}

					if (!flagExist) {
						IdentityProduct identityProduct = new IdentityProduct();
						String productCode = item.getProductCode();
						String warehouseCode = item.getWarehouseCode();
						identityProduct.setShipmentImportCode(txtMaPhieuKho.getText());
						identityProduct.setName(txtNghiepVu.getText());
						identityProduct.setProductCode(productCode);
						identityProduct.setInvoiceItemIdImport(item.getId());
						identityProduct.setImportDate(dcDateCreate.getDate());
						identityProduct.setImportType(ImportType.Import);
						identityProduct.setExportType(ExportType.NotExport);
						identityProduct.setPrice(item.getUnitPrice());
						identityProduct.setCurrencyRate(item.getCurrencyRate());
						identityProduct.setUnitPrice(item.getCurrencyUnit());
						identityProduct.setInvoiceCode(item.getInvoiceCode());
						identityProduct.setWarehouseCode(warehouseCode);
						identityProduct.setProvider("");
						identityProducts.add(identityProduct);
						item.setQuantityReal(item.getQuantityReal() + 1);
					}
				}
			}
		}
	}

	//Tính lại kết quả hiển thị bảng phải
	public void calculatorTotalTableViewRight() {
		if (flagGross == true) {
			double totalPrice = 0;
			List<IdentityProduct> identityProductGross = new ArrayList<IdentityProduct>();
			//Trường hợp xuất kho
			if (activityType.equals(ActivityType.Receipt)) {
				for (int i = 0; i < identityProducts.size(); i++) {
					IdentityProduct identityProduct = identityProducts.get(i);
					String warehouseCode = identityProduct.getWarehouseCode();
					if (warehouseCode == null)
						warehouseCode = "";
					if (!identityProduct.getPersistableState().equals(State.DELETED)) {
						totalPrice = totalPrice + identityProduct.getPriceExport();
						if (identityProductGross.size() == 0) {
							identityProductGross.add(identityProduct);
						} else {
							boolean existFlag = false;
							for (int j = 0; j < identityProductGross.size(); j++) {
								String warehouseCodeG = identityProductGross.get(j).getWarehouseCode();
								if (warehouseCodeG == null)
									warehouseCodeG = "";
								if (identityProduct.getProductCode().equals(identityProductGross.get(j).getProductCode()) && warehouseCode.equals(warehouseCodeG)) {
									existFlag = true;
									break;
								}
							}
							if (!existFlag) {
								identityProductGross.add(identityProduct);
							}
						}
					}
				}
			}
			//Trường hợp nhập kho
			else {
				if (activityType.equals(ActivityType.Payment)) {
					for (int i = 0; i < identityProducts.size(); i++) {
						IdentityProduct identityProduct = identityProducts.get(i);
						String warehouseCode = identityProduct.getWarehouseCode();
						if (warehouseCode == null)
							warehouseCode = "";
						if (!identityProduct.getPersistableState().equals(State.DELETED)) {
							totalPrice = totalPrice + identityProduct.getPrice();
							if (identityProductGross.size() == 0) {
								identityProductGross.add(identityProduct);
							} else {
								boolean existFlag = false;
								for (int j = 0; j < identityProductGross.size(); j++) {
									String warehouseCodeG = identityProductGross.get(j).getWarehouseCode();
									if (warehouseCodeG == null)
										warehouseCodeG = "";
									if (identityProduct.getProductCode().equals(identityProductGross.get(j).getProductCode()) && warehouseCode.equals(warehouseCodeG)) {
										existFlag = true;
										break;
									}
								}
								if (!existFlag) {
									identityProductGross.add(identityProduct);
								}
							}
						}
					}
				}
			}
			modelIdentity_Right.setListData(identityProductGross, identityProducts);
			txtTongTien.setText(MyDouble.valueOf(totalPrice).toString());
			if (txtTongTien.getText().equals("0"))
				cbWarehouse.setEnabled(true);
		}
	}

	public List<IdentityProduct> getIdentityPriductList() {
		return identityProductUnGross;
	}

	//Tìm toàn bộ invoiceItem có sản phẩm chưa được nhập hoặc xuất
	private void loadDataTableLeft(ActivityType activityType) {
		try {
			invoiceItems.clear();
			FilterQuery query = new FilterQuery();
			query.add("item.activityType", FilterQuery.Operator.STRINGEQ, activityType);
			query.add("item.quantityReal", FilterQuery.Operator.LT, "item.quantity");
			query.add("item.productCode", FilterQuery.Operator.NOTNULL, "");
			if (warehouseCode != null)
				query.add("item.warehouseCode", FilterQuery.Operator.STRINGEQ, warehouseCode);

			List<InvoiceDetail> details = AccountingModelManager.getInstance().searchInvoiceDetail(query);
			for (InvoiceDetail invoiceDetail : details) {
				if (invoiceDetail.getItems() != null) {
					invoiceItems.addAll(invoiceDetail.getItems());
				}
			}
			modelInvoiceItem_Left.setListData(invoiceItems);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void refeshDataTableLeft() {
		modelInvoiceItem_Left.setListData(invoiceItems);
	}

	public void refeshDataTableRight() {
		modelIdentity_Right.setListData(identityProducts, null);
	}

	//Lấy toàn bộ sản phẩm có mã [productCode] ở trong kho
	private List<IdentityProduct> getByWarehouseAndProductImportNotExport(String productCode, String warehouseCode) {
		try {
				return WarehouseModelManager.getInstance().getByImportTypeAndProduct(ImportType.Import, productCode, warehouseCode);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<IdentityProduct>();
		}
	}

	public List<InvoiceItem> getInvoiceItems() {
		return invoiceItems;
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

	@Override
	public void update(Object o, Object arg) {
		//		if (arg instanceof String || arg instanceof InvoiceItem) {
		//			try {
		//				quantity = Integer.parseInt(arg.toString());
		//			} catch (Exception e) {
		//				quantity = 1;
		//			}
		//			InvoiceItem item = txtTimKiem.getItem();
		//			tableColumn_Left.showColumn0();
		//			for (int j = 0; j < tableLeft.getRowCount(); j++) {
		//				if (item.getId() == ((InvoiceItem) tableLeft.getValueAt(j, 0)).getId()) {
		//					item = (InvoiceItem) tableLeft.getValueAt(j, 0);
		//					tableColumn_Left.hideColumn0();
		//					addIdentityProduct(item);
		//					calculatorTotalExport();
		//					break;
		//				}
		//			}
		//		}
	}

}
