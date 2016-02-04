package com.hkt.client.swingexp.model;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JComboBox;

import com.hkt.client.swingexp.app.component.TextPopup;
import com.hkt.client.swingexp.app.core.CustomerGroupJComboBox;
import com.hkt.client.swingexp.app.core.DepartmentJComboBox;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.HRJComboBox;
import com.hkt.client.swingexp.app.core.StoreJComboBox;
import com.hkt.client.swingexp.app.core.TextFieldObservable;
import com.hkt.client.swingexp.app.core.TypeInvoiceJComboBox;
import com.hkt.client.swingexp.app.core.TypeOrganizationJComboBox;
import com.hkt.client.swingexp.app.hethong.PanelCurrency;
import com.hkt.client.swingexp.app.hethong.PanelDepartment;
import com.hkt.client.swingexp.app.hethong.PanelOption;
import com.hkt.client.swingexp.app.khachhang.PanelGroupCustomers;
import com.hkt.client.swingexp.app.khachhang.PanelGroupSupplier;
import com.hkt.client.swingexp.app.khachhang.list.SupplierGroupJComboBox;
import com.hkt.client.swingexp.app.khohang.CatalogProductTag;
import com.hkt.client.swingexp.app.khohang.PanelRepositoryProducts2;
import com.hkt.client.swingexp.app.khohang.PanelWarehouse;
import com.hkt.client.swingexp.app.khuvucbanhang.PanelLocalTable;
import com.hkt.client.swingexp.app.khuvucbanhang.PanelLocation;
import com.hkt.client.swingexp.app.khuvucbanhang.PanelProject;
import com.hkt.client.swingexp.app.nhansu.EmployeeIsUser;
import com.hkt.client.swingexp.homescreen.DialogAddCustomer;
import com.hkt.client.swingexp.homescreen.DialogAddSupplier;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.config.generic.Option;
import com.hkt.module.config.locale.Currency;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.supplier.entity.Supplier;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductTag;
import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.Project;
import com.hkt.module.restaurant.entity.Table;
import com.hkt.module.warehouse.entity.Warehouse;

public class GuiModelManager<E> {
	private static TextFieldObservable iObservable;

	public GuiModelManager() {
	}

	public TextFieldObservable getObservable() {
		if (iObservable == null) {
			iObservable = new TextFieldObservable();
		}
		return iObservable;
	}

	public void getDialog(TextPopup<E> txt, Class<E> class1) {
		try {
			if (class1.getCanonicalName().equals(Employee.class.getCanonicalName())) {
				EmployeeIsUser screenEmployee = new EmployeeIsUser(null, true);
				screenEmployee.setName("EmployeeIsUser");
				screenEmployee.setPreferredSize(new Dimension(100, 100));
				DialogMain dialogMain = new DialogMain(screenEmployee, true);
				dialogMain.setIcon("canhan1.png");
				dialogMain.setName("dlEmployeeIsUser");
				dialogMain.setTitle("Nhân sự mới");
				dialogMain.setModal(true);
				dialogMain.setSize(Toolkit.getDefaultToolkit().getScreenSize());
				dialogMain.setLocationRelativeTo(null);
				dialogMain.setVisible(true);
				txt.setData((List<E>) HRModelManager.getInstance().getEmployees());
				return;
			}
			if (class1.getCanonicalName().equals(AccountGroup.class.getCanonicalName())) {
				PanelDepartment panel = new PanelDepartment();
				panel.setName("PhongBanBoPhan");
				DialogMain dialog = new DialogMain(panel);
				dialog.setIcon("kho1.png");
				dialog.setName("dlPhongBanBoPhan");
				dialog.setTitle("Phòng ban bộ phận");
				dialog.setVisible(true);
				AccountGroup rootDepartment = HRModelManager.getInstance().getRootDepartment();
				List<AccountGroup> getChildDepartment = AccountModelManager.getInstance().getAllChildrensByPath(
				    rootDepartment.getPath());
				txt.setData((List<E>) getChildDepartment);
				return;
			}
			// Them Mới Hàng Hóa
			if (class1.getCanonicalName().equals(Product.class.getCanonicalName())) {
				PanelRepositoryProducts2 panel = new PanelRepositoryProducts2();
				panel.setPreferredSize(new Dimension(100, 100));
				panel.setName("ThemHangHoaMoi");
				DialogMain dialog = new DialogMain(panel, true);
				dialog.setIcon("hanghoa1.png");
				dialog.setResizable(false);
				dialog.setTitle("Thêm hàng hóa mới");
				dialog.setName("dlThemHangHoaMoi");
				dialog.setModal(true);
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
				txt.setData((List<E>) ProductModelManager.getInstance().findAllProducts());
				return;
			}
			if (class1.getCanonicalName().equals(Table.class.getCanonicalName())) {
				PanelLocalTable panel = new PanelLocalTable();
				panel.setName("ThemMoiBanQuay");
				DialogMain dialog = new DialogMain(panel);
				dialog.setTitle("Thêm mới bàn quầy");
				dialog.setIcon("ban1.png");
				dialog.setName("dlThemMoiBanQuay");
				dialog.setModal(true);
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
				txt.setData((List<E>) RestaurantModelManager.getInstance().getTables());
				return;
			}

			if (class1.getCanonicalName().equals(Location.class.getCanonicalName())) {
				PanelLocation panel = new PanelLocation();
				panel.setName("KhuVucMoi");
				DialogMain dialog = new DialogMain(panel);
				dialog.setName("dlKhuVucMoi");
				dialog.setIcon("khuvuc1.png");
				dialog.setTitle("Khu vực mới");
				dialog.setModal(true);
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
				txt.setData((List<E>) RestaurantModelManager.getInstance().getLocations());
				return;
			}

			if (class1.getCanonicalName().equals(ProductTag.class.getCanonicalName())) {
				CatalogProductTag hh = new CatalogProductTag();
				hh.setName("QuanLyNhomHang_KhoHangHoa");
				DialogMain dialog = new DialogMain(hh);
				dialog.setIcon("nhomsp1.png");
				dialog.setName("dlNhomhang");
				dialog.setTitle("Quản lý nhóm hàng");
				dialog.setVisible(true);
				txt.setData((List<E>) ProductModelManager.getInstance().getProductTags());
				return;
			}
			if (class1.getCanonicalName().equals(Customer.class.getCanonicalName())) {
				DialogAddCustomer dialogAddCustome = new DialogAddCustomer();
				DialogResto dialog = new DialogResto(dialogAddCustome, false, 0, 120);
				dialog.setBtnSave(false);
				dialog.setIcon("doanhnghiep1.png");
				dialog.setLocationRelativeTo(null);
				dialog.setTitle("Thêm khách hàng");
				dialog.setVisible(true);
				txt.setData((List<E>) CustomerModelManager.getInstance().getCustomers(false));
				return;
			}
			if (class1.getCanonicalName().equals(Supplier.class.getCanonicalName())) {
				DialogAddSupplier dialogAddSupplier = new DialogAddSupplier();
				DialogResto dialog = new DialogResto(dialogAddSupplier, false, 0, 120);
				dialog.setBtnSave(false);
				dialog.setIcon("usernhom1.png");
				dialog.setName("dlInfo");
				dialog.setLocationRelativeTo(null);
				dialog.setTitle("Thêm nhà cung cấp");
				dialog.setVisible(true);
				txt.setData((List<E>) SupplierModelManager.getInstance().getSuppliers(false));
				return;
			}
			if (class1.getCanonicalName().equals(Project.class.getCanonicalName())) {
				PanelProject panel = new PanelProject();
				panel.setName("Duan");
				DialogMain dialog = new DialogMain(panel);
				dialog.setIcon("duan1.png");
				dialog.setTitle("Dự án mới");
				dialog.setName("dlThemMoiDuAn");
				dialog.setModal(true);
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
				txt.setData((List<E>) RestaurantModelManager.getInstance().getAllProject());
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void viewDialog(JComboBox cbo, Class<E> class1) {
		try {
			// Thêm mới hàng hóa
			if (class1.getCanonicalName().equals(Product.class.getCanonicalName())) {
				PanelRepositoryProducts2 panel = new PanelRepositoryProducts2();
				panel.setPreferredSize(new Dimension(100, 100));
				panel.setName("ThemHangHoaMoi");
				DialogMain dialog = new DialogMain(panel, true);
				dialog.setResizable(false);
				dialog.setTitle("Thêm hàng hóa mới");
				dialog.setIcon("hanghoa1.png");
				dialog.setName("dlThemHangHoaMoi");
				dialog.setModal(true);
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
				updateData();
				return;
			}
			// Thêm mới bàn quầy
			if (class1.getCanonicalName().equals(Table.class.getCanonicalName())) {
				PanelLocalTable panel = new PanelLocalTable();
				panel.setName("ThemMoiBanQuay");
				DialogMain dialog = new DialogMain(panel);
				dialog.setTitle("Thêm mới bàn quầy");
				dialog.setIcon("ban1.png");
				dialog.setName("dlThemMoiBanQuay");
				dialog.setModal(true);
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
				updateData();
				return;
			}
			// Thêm nhóm hàng
			if (class1.getCanonicalName().equals(ProductTag.class.getCanonicalName())) {
				CatalogProductTag hh = new CatalogProductTag();
				hh.setName("QuanLyNhomHang_KhoHangHoa");
				DialogMain dialog = new DialogMain(hh);
				dialog.setName("dlNhomhang");
				dialog.setIcon("nhomsp1.png");
				dialog.setTitle("Quản lý nhóm hàng");
				dialog.setVisible(true);
				updateData();
				return;
			}
			// Thêm phòng ban
			if (class1.getCanonicalName().equals(AccountGroup.class.getCanonicalName()) && cbo instanceof DepartmentJComboBox) {
				PanelDepartment panel = new PanelDepartment();
				panel.setName("PhongBanBoPhan");
				DialogMain dialog = new DialogMain(panel);
				dialog.setIcon("kho1.png");
				dialog.setName("dlPhongBanBoPhan");
				dialog.setTitle("Phòng ban bộ phận");
				dialog.setVisible(true);
				updateData();
				return;
			}
			// Thêm nhân sự mới
			if (class1.getCanonicalName().equals(AccountGroup.class.getCanonicalName()) && cbo instanceof HRJComboBox) {
				EmployeeIsUser screenEmployee = new EmployeeIsUser(null, true);
				screenEmployee.setName("EmployeeIsUser");
				screenEmployee.setPreferredSize(new Dimension(100, 100));
				DialogMain dialogMain = new DialogMain(screenEmployee, true);
				dialogMain.setName("dlEmployeeIsUser");
				dialogMain.setIcon("canhan1.png");
				dialogMain.setTitle("Nhân sự mới");
				dialogMain.setModal(true);
				dialogMain.setSize(Toolkit.getDefaultToolkit().getScreenSize());
				dialogMain.setLocationRelativeTo(null);
				dialogMain.setVisible(true);
				updateData();
				return;
			}
			// Thêm nhân sự mới
			if (class1.getCanonicalName().equals(Employee.class.getCanonicalName())) {
				EmployeeIsUser screenEmployee = new EmployeeIsUser(null, true);
				screenEmployee.setName("EmployeeIsUser");
				screenEmployee.setPreferredSize(new Dimension(100, 100));
				DialogMain dialogMain = new DialogMain(screenEmployee, true);
				dialogMain.setName("dlEmployeeIsUser");
				dialogMain.setIcon("canhan1.png");
				dialogMain.setTitle("Nhân sự mới");
				dialogMain.setModal(true);
				dialogMain.setSize(Toolkit.getDefaultToolkit().getScreenSize());
				dialogMain.setLocationRelativeTo(null);
				dialogMain.setVisible(true);
				updateData();
				return;
			}
			// Thêm khách hàng
			if (class1.getCanonicalName().equals(Customer.class.getCanonicalName())) {
				DialogAddCustomer dialogAddCustome = new DialogAddCustomer();
				DialogResto dialog = new DialogResto(dialogAddCustome, false, 0, 120);
				dialog.setBtnSave(false);
				dialog.setIcon("doanhnghiep1.png");
				dialog.setLocationRelativeTo(null);
				dialog.setTitle("Thêm khách hàng");
				dialog.setVisible(true);
				updateData();
				return;
			}

			// Quản lý nhóm khách hàng
			if (class1.getCanonicalName().equals(AccountGroup.class.getCanonicalName())
			    && cbo instanceof CustomerGroupJComboBox) {
				PanelGroupCustomers panelGroupCustomers = new PanelGroupCustomers();
				panelGroupCustomers.setName("QLNhomKhachHang");
				DialogMain dialog = new DialogMain(panelGroupCustomers);
				dialog.setTitle("Quản lý nhóm khách hàng");
				dialog.setIcon("nhomsp1.png");
				dialog.setName("dlGroupCustomer");
				dialog.setModal(true);
				dialog.setVisible(true);
				updateData();
				return;
			}

			// Quản lý nhóm ncc
			if (class1.getCanonicalName().equals(AccountGroup.class.getCanonicalName())
			    && cbo instanceof SupplierGroupJComboBox) {
				PanelGroupSupplier panel = new PanelGroupSupplier();
				panel.setName("QLNhomKhachHang");
				DialogMain dialog = new DialogMain(panel);
				dialog.setTitle("Quản lý nhóm nhà cung cấp");
				dialog.setIcon("quyen1.png");
				dialog.setName("dlGroupCustomer");
				dialog.setModal(true);
				dialog.setVisible(true);
				updateData();
				return;
			}

			// Thêm khu vực mới
			if (class1.getCanonicalName().equals(Location.class.getCanonicalName())) {
				PanelLocation panel = new PanelLocation();
				DialogMain dialog = new DialogMain(panel);
				panel.setName("KhuVucMoi");
				dialog.setName("dlKhuVucMoi");
				dialog.setIcon("khuvuc1.png");
				dialog.setTitle("Khu vực mới");
				dialog.setModal(true);
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
				updateData();
				return;
			}
			// Thêm mới bàn quầy
			if (class1.getCanonicalName().equals(Table.class.getCanonicalName())) {
				PanelLocation panel = new PanelLocation();
				DialogMain dialog = new DialogMain(panel);
				panel.setName("KhuVucMoi");
				dialog.setName("dlKhuVucMoi");
				dialog.setTitle("Khu vực mới");
				dialog.setIcon("khuvuc1.png");
				dialog.setModal(true);
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
				updateData();
				return;
			}
			// Thêm tiền tệ
			if (class1.getCanonicalName().equals(Currency.class.getCanonicalName())) {
				PanelCurrency pnCurrencyr = new PanelCurrency();
				pnCurrencyr.setName("TienTe");
				DialogMain dialog = new DialogMain(pnCurrencyr);
				dialog.setName("dlTienTe");
				dialog.setIcon("tien1.png");
				dialog.setTitle("Tiền tệ");
				dialog.setModal(true);
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
				updateData();
				return;
			}
			// Thêm kho hàng hóa
			if (class1.getCanonicalName().equals(Warehouse.class.getCanonicalName())) {
				PanelWarehouse add = new PanelWarehouse();
				add.setName("ThemMoiKho");
				DialogMain dialog = new DialogMain(add);
				dialog.setName("dlThemMoiKho");
				dialog.setIcon("soche1.png");
				dialog.setTitle("Tạo kho hàng");
				dialog.setResizable(false);
				dialog.setModal(true);
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
				updateData();
				return;
			}
			// Thêm mới dự án
			if (class1.getCanonicalName().equals(Project.class.getCanonicalName())) {
				DialogMain dialog = new DialogMain(new PanelProject());
				dialog.setIcon("duan1.png");
				dialog.setTitle("Dự án mới");
				dialog.setModal(true);
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
				updateData();
				return;
			}
			// Thêm mới cửa hàng
			if (class1.getCanonicalName().equals(Option.class.getCanonicalName())) {
				if (cbo instanceof StoreJComboBox) {
					DialogMain dialog = new DialogMain(new PanelOption("restaurant", "RestaurantService", "store"));
					dialog.setIcon("doanhnghiep1.png");
					dialog.setTitle("Quản lý của hàng");
					dialog.setModal(true);
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
					updateData();
					return;
				} else {
					if (cbo instanceof TypeOrganizationJComboBox) {
						DialogMain dialog = new DialogMain(new PanelOption("config", "LocaleService", "type_restaurant"));
						dialog.setIcon("doanhnghiep1.png");
						dialog.setTitle("Quản lý của hàng");
						dialog.setModal(true);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);
						updateData();
						return;
					}
				}
			}
			// Loại hóa đơn
			if (class1.getCanonicalName().equals(Option.class.getCanonicalName())) {
				if (cbo instanceof TypeInvoiceJComboBox) {
					DialogMain dialog = new DialogMain(new PanelOption("accounting", "AccountingService", "type_invoice"));
					dialog.setTitle("Loại hóa đơn");
					dialog.setIcon("invoice1.png");
					dialog.setModal(true);
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
					updateData();
					return;
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateData() {
		try {
			if (iObservable != null) {
				iObservable.setChanged();
				iObservable.notifyObservers(null, null);
			}
		} catch (Exception e) {
			System.out.println("loi guiModel");
		}

	}

}
