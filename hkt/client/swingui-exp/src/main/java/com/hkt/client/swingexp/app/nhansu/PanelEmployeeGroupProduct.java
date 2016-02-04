package com.hkt.client.swingexp.app.nhansu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.component.TextPopup;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.app.nhansu.list.TableModelGroupEmployeeProduct;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.Account.Type;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.product.entity.ProductTag;

@SuppressWarnings("serial")
public class PanelEmployeeGroupProduct extends MyPanel implements IDialogMain {
	private JLabel lbMessenger, lbProduct, lbEmployee, lbDescription;
	private JTextArea txtDescription;
	private JScrollPane scrollPaneTable;
	private TextPopup<ProductTag> txtProduct;
	private TextPopup<Employee> txtEmployee;
	private Employee employee;
	private List<ProductTag> listAllProducts;
	private List<Employee> listAllEmployee;
	private List<ProductTag> listTable = new ArrayList<ProductTag>();
	private TableModelGroupEmployeeProduct tbGroup;
	private ProductTag productTag = new ProductTag();
	public static String permission;

	public PanelEmployeeGroupProduct() throws Exception {
		init();
		loadTable();
		txtProduct.setData(listAllProducts);
		txtEmployee.setData(listAllEmployee);
		setOpaque(false);
		reset();
	}

	// Hàm tạo giao diện
	private void init() throws Exception {
		createBeginTest();
		lbMessenger = new JLabel("");
		scrollPaneTable = new JScrollPane();
		scrollPaneTable.setOpaque(false);
		scrollPaneTable.getViewport().setOpaque(false);
		scrollPaneTable.setBorder(null);

		tbGroup = new TableModelGroupEmployeeProduct(listTable);
		tbGroup.setName("tbGroup");
		tbGroup.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				try {
					tbGroupMouseClicked(event);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		tbGroup.setPreferredScrollableViewportSize(new Dimension(200, 290));
		scrollPaneTable.setViewportView(tbGroup);
		lbDescription = new JLabel("Miêu tả");
		txtDescription = new JTextArea();
		txtDescription.setPreferredSize(new Dimension(250, 53));
		txtDescription.setName("txtDescription");
		txtDescription.setDisabledTextColor(Color.black);
		lbProduct = new JLabel("Nhóm SP");
		lbEmployee = new JLabel("Nhân viên");

		txtProduct = new TextPopup<ProductTag>(ProductTag.class);
		txtProduct.setName("txtProduct");
		txtProduct.setDisabledTextColor(Color.black);

		txtEmployee = new TextPopup<Employee>(Employee.class);
		txtEmployee.setName("txtEmployee");
		txtEmployee.setDisabledTextColor(Color.black);

		MyGroupLayout layout = new MyGroupLayout(this);
		layout.add(0, 0, lbProduct);
		layout.add(0, 1, txtProduct);
		layout.add(0, 2, lbEmployee);
		layout.add(0, 3, txtEmployee);

		layout.add(1, 0, lbDescription);
		layout.add(1, 1, txtDescription);
		layout.addMessenger(lbMessenger);

		// Giao diện Button cập nhật index đi cùng với scrollPaneTable
		layout.add(2, 0, new JLabel());
		layout.add(2, 1, scrollPaneTable);
		layout.updateGui();

	}

	// Hàm click vào 1 dòng để chỉnh sửa đối tượng
	protected void tbGroupMouseClicked(MouseEvent event) throws Exception {
		productTag = tbGroup.getSelectedBean();

		int click = 2;
		if (productTag.getLabel().indexOf("HktTest") > 0 && event.getButton() == 3) {
			click = 1;
		}
		if (event.getClickCount() >= click) {
			setData();
			setEnableCompoment(false);
			((DialogMain) getRootPane().getParent()).showButton(false);
		}
		refresh();
	}

	// Hàm lưu đối tượng
	@Override
	public void save() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được phân quyền này !");
		} else {
			if (checkData()) {
				productTag = getData();
				ProductModelManager.getInstance().saveProductTag(productTag);
				loadTable();
				reset();
			}
		}

	}

	// Hàm kiểm tra các trường dữ liệu
	public boolean checkData() {

		boolean check = true;
		if (txtProduct.getItem() == null || txtProduct.getText() != null && txtProduct.getForeground() == Color.red) {
			lbProduct.setForeground(Color.red);
		} else {
			lbProduct.setForeground(Color.black);
		}

		if (txtEmployee.getItem() == null || txtEmployee.getText() != null && txtEmployee.getForeground() == Color.red) {

			lbEmployee.setForeground(Color.red);
			check = false;

		} else {
			lbEmployee.setForeground(Color.black);
		}

		if (!check) {
			lbMessenger.setText("Dữ liệu vùng đỏ bị lỗi");
			lbMessenger.setForeground(Color.red);
			lbMessenger.setVisible(true);
			return false;
		} else {
			lbMessenger.setText(" ");
			return true;
		}
	}

	// Hàm cho phép sửa giao diện (enable các giao diện đã disible trước đó)
	@Override
	public void edit() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được phân quyền này !");
		} else {
			setEnableCompoment(true);
			txtProduct.setEnabled(false);
		}
	}

	// Hàm xóa đối tượng
	@Override
	public void delete() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.ADMIN) {
			productTag = tbGroup.getSelectedBean();
			if (productTag != null)
				productTag.setLoginId("");

			String str = "Xóa nhân viên ";
			PanelChoise pnPanel = new PanelChoise(str + txtEmployee.getText() + "?");
			pnPanel.setName("Xoa");
			DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
			dialog1.setName("dlXoa");
			dialog1.setTitle("Xóa nhân viên nhóm sản phẩm");
			dialog1.setLocationRelativeTo(null);
			dialog1.setModal(true);
			dialog1.setVisible(true);

			if (pnPanel.isDelete()) {
				ProductModelManager.getInstance().saveProductTag(productTag);
				loadTable();
				reset();
			} else if (pnPanel.isDelete() == false) {
				reset();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Bạn chưa được phân quyền này !");
		}
	}

	// Hàm reset lại giao diện
	@Override
	public void reset() throws Exception {
		setEnableCompoment(true);
		lbProduct.setForeground(Color.black);
		lbEmployee.setForeground(Color.black);
		lbMessenger.setForeground(Color.black);

		txtProduct.setText("");
		txtProduct.setItem(null);
		txtEmployee.setText("");
		txtEmployee.setItem(null);
		txtDescription.setText("");

		lbMessenger.setText("");

		productTag = new ProductTag();
		loadTable();

	}

	@Override
	public void refresh() throws Exception {
		if (tbGroup.getSelectedBean() != null)
			setData();
		setEnableCompoment(false);
		lbProduct.setForeground(Color.black);
		lbEmployee.setForeground(Color.black);
		lbMessenger.setText("");
	}

	private void loadTable() throws Exception {
		listAllProducts = ProductModelManager.getInstance().getProductTags();
		listAllEmployee = HRModelManager.getInstance().getEmployees();
		listTable.clear();
		for (ProductTag pt : listAllProducts) {
			if (pt.getLoginId() != "" && pt.getLoginId() != null) {
				listTable.add(pt);
			}
		}
		tbGroup.setData(listTable);
	}

	private ProductTag getData() {
		try {
			productTag = txtProduct.getItem();
			employee = txtEmployee.getItem();
			productTag.setDescription(txtDescription.getText());
			productTag.setLoginId(employee.getName());

			lbMessenger.setText(" ");
			return productTag;
		} catch (Exception e) {
			lbMessenger.setVisible(true);
			lbMessenger.setText("Lỗi định dạng dữ liệu");
			e.printStackTrace();
			return new ProductTag();
		}
	}

	private void setData() {

		try {
			txtProduct.setItem(productTag);
			try {
				employee = HRModelManager.getInstance().getBydLoginId(productTag.getLoginId());
				txtEmployee.setItem(employee);
			} catch (Exception e) {
				e.printStackTrace();
			}

			txtDescription.setText(productTag.getDescription());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void setEnableCompoment(boolean value) {
		txtProduct.setEnabled(value);
		txtEmployee.setEnabled(value);
		txtDescription.setEnabled(value);
	}

	// Tạo dữ liệu mẫu liên quan
	@Override
	public void createBeginTest() {
		if (MyPanel.isTest) {
			// Tạo nhóm hàng hóa (tạo ra 3 nhóm hàng)

			for (int j = 1; j <= 3; j++) {
				ProductTag productTag = new ProductTag();
				if (j % 2 == 0) {
					productTag.setTag("Mã NHH HktTest" + (j - 1) + ":" + "Mã NHH HktTest" + j);
				} else {
					productTag.setTag("Mã NHH HktTest" + j);
				}
				productTag.setTag("Mã NHH HktTest" + j);
				productTag.setCode("Mã NHH HktTest" + j);
				productTag.setLabel("Mã NHH HktTest" + j);
				try {
					ProductModelManager.getInstance().saveProductTag(productTag);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// Tạo nhân viên (tạo ra 3 nhân viên)
			for (int i = 1; i <= 3; i++) {
				Account account = new Account();
				Employee emp = new Employee();

				account.setLoginId("Mã NV HktTest" + i);
				account.setPassword("hkt");
				account.setType(Type.USER);
				account.setEmail("hkt@gmail.com" + i);
				account.setLastLoginTime(new Date());

				emp.setLoginId(account.getLoginId());
				emp.setCode("NV HktTest" + i);
				emp.setName("Tên NV HktTest" + i);
				emp.setOrganizationLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
				try {
					emp.setStartDate(null);
				} catch (Exception e2) {
					emp.setStartDate(null);
				}

				try {
					emp.setLeaveDate(null);
				} catch (Exception e2) {
					emp.setLeaveDate(null);
				}

				try {
					AccountModelManager.getInstance().saveAccount(account);
					HRModelManager.getInstance().saveEmployee(emp);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			super.createBeginTest();
		}
	}

	@Override
	public void deleteDataTest() {
		if (!PanelTestAll.runAll) {
			DialogTest.getInstance().show("Xóa toàn bộ dữ liệu test ?");
			if (DialogTest.getInstance().isTest()) {
				try {
					ProductModelManager.getInstance().deleteTest("HktTest");
					AccountModelManager.getInstance().deleteTest("HktTest");
					HRModelManager.getInstance().deleteTest("HktTest");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
