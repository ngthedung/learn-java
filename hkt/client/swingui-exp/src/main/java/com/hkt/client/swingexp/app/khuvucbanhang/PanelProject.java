package com.hkt.client.swingexp.app.khuvucbanhang;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.hkt.client.swingexp.app.component.ExtendJButton;
import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.component.TextPopup;
import com.hkt.client.swingexp.app.core.DepartmentJComboBox;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.hethong.PanelManageDelete;
import com.hkt.client.swingexp.app.hethong.PanelOKRestore;
import com.hkt.client.swingexp.app.hethong.PanelRecybin;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.app.khuvucbanhang.list.ContractLease;
import com.hkt.client.swingexp.app.khuvucbanhang.list.ContractSuplySoftware;
import com.hkt.client.swingexp.app.khuvucbanhang.list.ProjectComboBoxDelete;
import com.hkt.client.swingexp.app.khuvucbanhang.list.ReportProject;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.accounting.entity.ManageCodes;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.supplier.entity.Supplier;
import com.hkt.module.restaurant.entity.Project;
import com.hkt.module.restaurant.entity.ProjectMember;
import com.hkt.util.text.DateUtil;

@SuppressWarnings("serial")
public class PanelProject extends MyPanel implements IDialogMain {
	public static String permission;
	public static Customer name;
	public static boolean choose = true;
	private ExtendJLabel lbNameProject, lbCodeProject, lbDepartments, lbParentProject, lbStartDate, lbStatus, lbEndDate,
	    lbDescription, lbMessenger, lbCustomer, lbPrint, lbReport;
	private ExtendJTextField txtNameProject, txtCodeProject;
	private ExtendJTextField txtDescription;
	private ExtendJComboBox cbStatus, cbReport;
	private TextPopup<Project> txtParentProject;
	private DepartmentJComboBox cbDepartments;
	private TextPopup<Customer> txtCustomer;
	private MyJDateChooser dcStartDate, dcEndDate;
	private Project project;
	private boolean isNew = true;
	private TableListEmployee tableProjectMember;
	private JScrollPane scrollPaneTable;
	private AccountGroup groupDepartment;
	private AccountGroup groupHKT;
	private List<Project> listSubProject;
	private JButton btnAdd, btnPrint;
	private PanelProjectMember panelEmployee;
	private List<ProjectMember> projectMembers = new ArrayList<ProjectMember>();
	private boolean reset = false;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PanelProject() {
		init();

		try {
			groupHKT = AccountModelManager.getInstance().getRootGroupDetail().getChildren().get(0);

			groupDepartment = new AccountGroup();
			groupDepartment.setLabel("Nhóm Phòng ban");
			groupDepartment.setName(AccountModelManager.Department);
			groupDepartment.setOwner("hkt");
			groupDepartment.setParent(groupHKT);
			groupDepartment.setDescription("Nhóm Phòng ban group hkt");

			if (AccountModelManager.getInstance().getGroupByPath(groupHKT.getPath() + "/" + AccountModelManager.Department) == null) {
				groupDepartment = AccountModelManager.getInstance().saveGroup(groupDepartment);
			} else {
				groupDepartment = AccountModelManager.getInstance().getGroupByPath(groupDepartment.getPath());
			}
		} catch (Exception ex) {
		}

		project = new Project();
		listSubProject = new ArrayList<Project>();
		dcStartDate.getDateEditor().addCaretListener(new CaretListener() {
			
			@Override
			public void caretUpdate(CaretEvent e) {
			loadCode();
				
			}
		});
	}

	@SuppressWarnings("unchecked")
	public void init() {
		createBeginTest();

		tableProjectMember = new TableListEmployee();
		tableProjectMember.setName("listEmployee");
		tableProjectMember.setEmployee(projectMembers);
		tableProjectMember.setPreferredScrollableViewportSize(new Dimension(200, 290));
		scrollPaneTable = new JScrollPane();
		scrollPaneTable.setOpaque(false);
		scrollPaneTable.getViewport().setOpaque(false);
		scrollPaneTable.setBorder(null);
		scrollPaneTable.setViewportView(tableProjectMember);
		btnAdd = new JButton("Thêm");
		btnAdd.setName("btnAdd");
		btnAdd.setPreferredSize(new Dimension(130, 40));
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
						JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
						return;
					} else {
						List<ProjectMember> members = new ArrayList<ProjectMember>();
						if (isNew == false) {
							members = project.getProjectMembers();
						} else {
							members = projectMembers;
						}
						panelEmployee = new PanelProjectMember(members);
						panelEmployee.setPanelProject(PanelProject.this);
						panelEmployee.setName("panel");
						DialogMain dialog = new DialogMain(panelEmployee);
						dialog.setName("dialog");
						dialog.setTitle("Chọn nhân viên");
						dialog.setVisible(true);

					}
				} catch (Exception e2) {

				}
			}
		});

		lbNameProject = new ExtendJLabel("Tên dự án");
		lbCodeProject = new ExtendJLabel("Mã");
		lbDepartments = new ExtendJLabel("Phòng ban");
		lbParentProject = new ExtendJLabel("Dự án cha");
		lbStartDate = new ExtendJLabel("Bắt đầu");
		lbStatus = new ExtendJLabel("Trạng thái");
		lbEndDate = new ExtendJLabel("Hoàn thành");
		lbDescription = new ExtendJLabel("Miêu tả");
		lbMessenger = new ExtendJLabel("");
		lbMessenger.setForeground(Color.RED);
		lbCustomer = new ExtendJLabel("Khách hàng");
		lbPrint = new ExtendJLabel("In phiếu");
		lbReport = new ExtendJLabel("Mẫu phiếu");

		cbDepartments = new DepartmentJComboBox();
		cbDepartments.setName("cbDepartments");
		txtParentProject = new TextPopup<Project>(Project.class);
		try {
			txtParentProject.setData(RestaurantModelManager.getInstance().getAllProject());
		} catch (Exception e) {
		}
		cbStatus = new ExtendJComboBox();
		cbStatus.setName("cbStatus");
		cbStatus.addItem("Đang thực hiện");
		cbStatus.addItem("Đã hoàn thành");

		cbReport = new ExtendJComboBox();
		cbReport.setModel(new DefaultComboBoxModel(new String[] { "Mẫu phiếu 1", "Mẫu phiếu 2" }));
		cbReport.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (cbReport.getSelectedItem().equals("Mẫu phiếu 1")) {
					choose = true;
				} else {
					choose = false;
				}

			}
		});

		btnPrint = new ExtendJButton("In");
		btnPrint.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/print1.png")));
		btnPrint.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				printReport();

			}
		});

		txtNameProject = new ExtendJTextField();
		txtCodeProject = new ExtendJTextField();
		
		txtDescription = new ExtendJTextField();
		txtCustomer = new TextPopup<Customer>(Customer.class);
		try {
			txtCustomer.setData(CustomerModelManager.getInstance().getCustomers(false));
		} catch (Exception e) {
		}
		dcStartDate = new MyJDateChooser();
		dcStartDate.setDateFormatString("dd/MM/yyyy");
		dcEndDate = new MyJDateChooser();
		dcEndDate.setDateFormatString("dd/MM/yyyy");
		dcStartDate.setDate(new Date());
		dcEndDate.setDate(null);

		txtNameProject.setName("txtNameProject");
		txtCodeProject.setName("txtCodeProject");
		txtDescription.setName("txtDescription");
		loadCode();
		UIManager.put("ComboBox.background", Color.WHITE);
		UIManager.put("ComboBox.disabledBackground", Color.WHITE);
		UIManager.put("ComboBox.disabledForeground", new Color(51, 51, 51));
		UIManager.put("TextField.background", Color.WHITE);
		UIManager.put("TextField.inactiveBackground", Color.WHITE);
		UIManager.put("TextField.inactiveForeground", new Color(51, 51, 51));
		MyGroupLayout layout = new MyGroupLayout(this);
		layout.add(0, 0, lbNameProject);
		layout.add(0, 1, txtNameProject);
		layout.add(0, 2, lbCodeProject);
		layout.add(0, 3, txtCodeProject);

		layout.add(1, 0, lbDepartments);
		layout.add(1, 1, cbDepartments);
		layout.add(1, 2, lbParentProject);
		layout.add(1, 3, txtParentProject);

		layout.add(2, 0, lbStartDate);
		layout.add(2, 1, dcStartDate);
		layout.add(2, 3, lbStatus);
		layout.add(2, 4, cbStatus);

		layout.add(3, 0, lbEndDate);
		layout.add(3, 1, dcEndDate);
		layout.add(3, 2, lbCustomer);
		layout.add(3, 4, txtCustomer);

		layout.add(4, 0, lbReport);
		layout.add(4, 1, cbReport);
		layout.add(4, 2, lbPrint);
		layout.add(4, 3, btnPrint);

		layout.add(5, 0, lbDescription);
		layout.add(5, 1, txtDescription);

		layout.add(6, 0, btnAdd);
		layout.add(6, 1, scrollPaneTable);
		layout.addMessenger(lbMessenger);
		layout.updateGui();
	}

	private void loadCode() {
		if (project==null ||project.getId() == null) {
			try {
				if(dcStartDate.getDate()!=null){
					String code = AccountingModelManager.getInstance().getCodeObject(
							PanelManageCodes.DuAn,null, dcStartDate.getDate(), false);
					code = code.substring(code.indexOf(":")+1);
					txtCodeProject.setText(code);
					txtCodeProject.setEnabled(false);
					}else {
						String code = AccountingModelManager.getInstance().getCodeObject(
								PanelManageCodes.DuAn,null, new Date(), false);
						code = code.substring(code.indexOf(":")+1);
						txtCodeProject.setText(code);
						txtCodeProject.setEnabled(false);
					}
	    } catch (Exception e) {
	    	txtCodeProject.setText(DateUtil.asCompactDateTimeId(new Date()));
	    }
		}
		
		

	}

	// Thiết lập có khả năng edit components hay không?
	private void setEnableCompoment(boolean value) {
		txtCodeProject.setEnabled(false);
		txtNameProject.setEnabled(value);
		cbDepartments.setEnabled(value);
		txtParentProject.setEnabled(value);
		dcEndDate.setEnabled(value);
		dcStartDate.setEnabled(value);
		cbStatus.setEnabled(value);
		txtDescription.setEnabled(value);
		txtCustomer.setEnabled(value);
		btnAdd.setEnabled(value);
	}

	// Lấy dữ liệu từ đối tượng hiển thị ra giao diện
	public void setData(Project project) {
		// Khi em chỉnh sửa em đã gán project bằng cái em lấy đc từ danh sách

		this.project = project;
		try {
			txtCodeProject.setText(project.codeView());
			txtNameProject.setText(project.getName());
			if (project.getDepartmentPart() != null) {

				cbDepartments.setSelectDepartmentByPath(project.getDepartmentPart());
			}

			for (int i = 0; i < cbStatus.getItemCount(); i++) {
				if (cbStatus.getItemAt(i).equals(project.getStatus())) {
					cbStatus.setSelectedIndex(i);
					break;
				}
			}
			try {
				Customer c = CustomerModelManager.getInstance().getCustomerByCode(project.getCustomerCode());
				txtCustomer.setItem(c);
			} catch (Exception e) {

			}

			if (project.getParentCode() == null) {
				txtParentProject.setItem(null);
			} else {
				try {
					Project parentProject = RestaurantModelManager.getInstance().getProjectByCode(project.getParentCode());
					txtParentProject.setItem(parentProject);
				} catch (Exception e) {
				}
			}

			txtDescription.setText(project.getDescription());
			dcStartDate.setDate(project.getFormDate());
			dcEndDate.setDate(project.getToDate());

			setEnableCompoment(false);

		} catch (Exception e) {
			e.printStackTrace();
		}
		tableProjectMember.setEmployee(project.getProjectMembers());
	}

	@Override
	public void save() throws Exception {

		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được phân quyền này !");
		} else {
			if (checkData()) {
				getData();
				RestaurantModelManager.getInstance().saveProject(project);
				reset();
			}
			if (reset == true)
				((JDialog) getRootPane().getParent()).dispose();
		}
	}

	// Lấy dữ liệu từ giao diện cho vào đối tượng
	private Project getData() {

		Project pro = ((Project) txtParentProject.getItem());
		if (project.getId() == null) {
			String code = AccountingModelManager.getInstance().getCodeObject(PanelManageCodes.DuAn,null,
					dcStartDate.getDate(), true);
			if (code != null) {
				project.setCode(code);
			} else {
				project.setCode(txtCodeProject.getText());
			}
		}
		project.setName(txtNameProject.getText());
		try {
			if (cbDepartments.getSelectedDepartment() != null)
				project.setDepartmentPart(cbDepartments.getSelectedDepartment().getPath());
			else
				project.setDepartmentPart(null);
		} catch (Exception e) {
		}
		if (txtCustomer.getItem() != null) {
			project.setCustomerCode(txtCustomer.getItem().getCode());
		} else {
			project.setCustomerCode(null);
		}

		if (pro == null) {
			project.setParentCode("");

		} else {
			project.setParentCode(pro.getCode());
		}
		project.setStatus(cbStatus.getSelectedItem().toString());
		project.setFormDate(dcStartDate.getDate());
		project.setToDate(dcEndDate.getDate());
		if(cbStatus.getSelectedItem().toString().equals("Đã hoàn thành") && dcEndDate.getDate()==null){
			project.setToDate(new Date());
		}
		project.setDescription(txtDescription.getText());
		project.setProjectMembers(projectMembers);
		return project;
	}

	// Kiểm tra dữ liệu trước khi lưu
	private boolean checkData() {

		boolean check = true;
		boolean codeError = false;

		if (txtCodeProject.getText().equals("")) {
			check = false;
			lbCodeProject.setForeground(Color.RED);
		} else {
			lbCodeProject.setForeground(new Color(51, 51, 51));
			if (isNew) {
				try {
					Project pr = RestaurantModelManager.getInstance().getProjectByCode(txtCodeProject.getText());
					if (pr != null) {
						check = false;
						codeError = true;
						lbCodeProject.setForeground(Color.RED);
						if (pr.isRecycleBin()) {
							PanelRecybin panel = new PanelRecybin("Mã đã bị xóa trước đó!",
							    " Chọn ĐỒNG Ý để lấy lại thông tin ban đầu hoặc THOÁT và nhập mã khác!");
							panel.setName("Xoa");
							DialogResto dialog = new DialogResto(panel, false, 0, 120);
							dialog.setName("dlXoa");
							dialog.setTitle("Dự án mới");
							dialog.setLocationRelativeTo(null);
							dialog.setModal(true);
							dialog.setVisible(true);
							if (panel.isDelete()) {
								pr.setRecycleBin(false);
								RestaurantModelManager.getInstance().saveProject(pr);
								return true;
							}

						}
					}
				} catch (Exception e) {
					return false;
				}
			}
		}

		if (txtNameProject.getText().equals("")) {
			check = false;
			lbNameProject.setForeground(Color.RED);
		} else {
			lbNameProject.setForeground(new Color(51, 51, 51));
		}

		if (!check) {
			lbMessenger.setText("Kiểm tra lỗi báo đỏ !");
			if (codeError)
				lbMessenger.setText("Lỗi trùng mã phòng ban !");
			return false;
		} else {
			lbMessenger.setText("");
			return true;
		}
	}

	// Phương thức cho nút [Sửa]
	@Override
	public void edit() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được phân quyền này !");
		} else {
			reset = true;
			setEnableCompoment(true);
			isNew = false;
		}
	}

	// Phương thức cho nút [Xóa]
	@Override
	public void delete() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.ADMIN) {

			String str = "Xóa Dự án ";
			PanelChoise pnPanel = new PanelChoise(str + project + "?");
			pnPanel.setName("Xoa");
			DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
			dialog1.setName("dlXoa");
			dialog1.setTitle("Xóa nhóm dự án");
			dialog1.setLocationRelativeTo(null);
			dialog1.setModal(true);
			dialog1.setVisible(true);

			if (pnPanel.isDelete()) {
				showProject();
			} else if (pnPanel.isDelete() == false) {
				reset();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Bạn chưa được phân quyền này !");
		}
	}

	public void showProject() throws Exception {

		List<Project> listProject = new ArrayList<Project>();
		listProject = RestaurantModelManager.getInstance().getAllProject();
		for (int i = 0; i < listProject.size(); i++) {
			if (listProject.get(i).getParentCode() != null
			    && (listProject.get(i).getParentCode().toString().trim()).equals(project.getCode().toString().trim())) {
				listSubProject.add(listProject.get(i));
			}
		}

		if (listSubProject.size() == 0) {
			RestaurantModelManager.getInstance().deleteProject(project);
			((JDialog) getRootPane().getParent()).dispose();
		} else {
			try {
				String nameProject = txtNameProject.getText();
				PanelManageDelete<Project> panel = new PanelManageDelete<Project>(nameProject, listSubProject,
				    new ProjectComboBoxDelete(project));
				panel.setName("XoaDuAn");
				DialogResto dialog = new DialogResto(panel, true, 600, 100);
				dialog.setName("dlXoaDuAn");
				dialog.dispose();
				dialog.setUndecorated(true);
				dialog.dispose();
				dialog.setUndecorated(true);
				dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
				dialog.setTitle("Quản lý xóa dự án");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
			} catch (Exception e2) {
				e2.printStackTrace();
			}

			List<Project> lstCheckParentProject = RestaurantModelManager.getInstance().findProjectByParentCode(
			    project.getCode());

			if (lstCheckParentProject.isEmpty()) {
				RestaurantModelManager.getInstance().deleteProject(project);
				((JDialog) getRootPane().getParent()).dispose();
			}
		}
	}

	// Phương thức cho nút [Viết lại]
	@Override
	public void reset() throws Exception {
		project = new Project();
		projectMembers = new ArrayList<ProjectMember>();
		tableProjectMember.setEmployee(projectMembers);
		txtNameProject.setText("");
		txtDescription.setText("");
		txtParentProject.setItem(null);
		// refershComboBox();
		lbMessenger.setText("");
		lbNameProject.setForeground(new Color(51, 51, 51));
		lbCodeProject.setForeground(new Color(51, 51, 51));
		lbDescription.setForeground(new Color(51, 51, 51));
		setEnableCompoment(true);
		loadCode();

		txtCustomer.setText("");
	}

	@Override
	public void refresh() throws Exception {
		setData(project);

	}

	public void setProjectMembers(List<ProjectMember> projectMembers) {
		this.projectMembers = projectMembers;
		tableProjectMember.setEmployee(projectMembers);
	}

	private void printReport() {
		name = txtCustomer.getItem();
		if (cbReport.getSelectedItem().equals("HD1")) {
			ContractSuplySoftware panel = new ContractSuplySoftware();
			DialogResto dialog1 = new DialogResto(panel, true, 0, 400);
			dialog1.setTitle("Hợp đồng cung cấp phần mềm");
			dialog1.setIcon("duan1.png");
			dialog1.setSize(Toolkit.getDefaultToolkit().getScreenSize());
			dialog1.dispose();
			dialog1.setBtnExt1(panel.getBtnPrint());
			dialog1.setBtnSave(false);
			dialog1.setUndecorated(true);
			dialog1.setLocationRelativeTo(null);
			dialog1.setModal(true);
			dialog1.setVisible(true);
		} else if (cbReport.getSelectedItem().equals("HD2")) {
			ContractLease panel = new ContractLease();
			DialogResto dialog1 = new DialogResto(panel, true, 0, 400);
			dialog1.setTitle("Hợp đồng khung thuê khoán chuyên môn");
			dialog1.setIcon("duan1.png");
			dialog1.setSize(Toolkit.getDefaultToolkit().getScreenSize());
			dialog1.dispose();
			dialog1.setBtnExt1(panel.getBtnPrint());
			dialog1.setBtnSave(false);
			dialog1.setUndecorated(true);
			dialog1.setLocationRelativeTo(null);
			dialog1.setModal(true);
			dialog1.setVisible(true);
		}

	}

	// Tạo dữ liệu mẫu liên quan
	@Override
	public void createBeginTest() {
		if (isTest) {
			for (int i = 1; i <= 3; i++) {
				try {
					AccountGroup ag = new AccountGroup();
					ag.setPath("hkt/Phòng ban/Mã phòng ban HktTest" + i);
					ag.setParentPath("hkt/Phòng ban");
					ag.setName("Tên phòng ban HktTest" + i);
					ag.setLabel("Mã phòng ban HktTest" + i);
					@SuppressWarnings("unused")
					AccountGroup a = AccountModelManager.getInstance().saveGroup(ag);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	// Tạo dữ liệu mẫu sinh tự động test chuyển trang
	@Override
	public void createDataTest() {
		for (int i = 10; i <= 50; i++) {
			Project pro = new Project();

			dcStartDate = new MyJDateChooser();
			dcStartDate.setDateFormatString("dd/MM/yyyy");
			dcEndDate = new MyJDateChooser();
			dcEndDate.setDateFormatString("dd/MM/yyyy");
			dcStartDate.setDate(new Date());
			dcEndDate.setDate(new Date());

			pro.setName("Tên dự án HktTest" + i);
			pro.setCode("Mã dự án HktTest" + i);
			pro.setToDate(dcEndDate.getDate());
			pro.setFormDate(dcStartDate.getDate());
			pro.setParentCode("Mã dự án HktTest1");
			pro.setDepartmentPart("IT");
			pro.setDescription("Miêu tả dự án HktTest" + i);
			pro.setStatus("Đang hoạt động");
			try {
				RestaurantModelManager.getInstance().saveProject(pro);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// Xóa tất cả dữ liệu mẫu tự động sinh
	@Override
	public void deleteDataTest() {
		if (!PanelTestAll.runAll) {
			DialogTest.getInstance().show("Xóa toàn bộ dữ liệu test ?");
			if (DialogTest.getInstance().isTest()) {
				try {
					RestaurantModelManager.getInstance().deleteTest("HktTest");
					AccountModelManager.getInstance().deleteTest("HktTest");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
