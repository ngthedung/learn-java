package com.hkt.client.swingexp.app.hethong;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextArea;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.core.DepartmentJComboBox;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.hethong.list.DepartmentChildren;
import com.hkt.client.swingexp.app.hethong.list.DepartmentComboBoxDelete;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.hr.entity.Employee;
import com.hkt.util.text.DateUtil;

@SuppressWarnings("serial")
public class PanelDepartment extends MyPanel implements IDialogMain {

  public static String permission;
  private ExtendJLabel lbMa, lbPhong, lbDoanhNghiep, lbTen, lbMieuTa, lbMessage;
  private ExtendJTextField txtMa, txtTen;
  private ExtendJTextArea txtMieuTa;
  private DepartmentJComboBox cbGroupDepartment;
  private ExtendJTextField cbDoanhNghiep;
  private JScrollPane scrollPane;
  @SuppressWarnings("unused")
private List<Account> listOrganization;
  private AccountGroup department;
  private boolean isNew = true;
  private boolean restore = true;
  private boolean 	reset = false;

  public PanelDepartment() {
    init();
    department = new AccountGroup();
  }

  // Khởi tạo components và xắp sếp trên giao diện
  private void init() {
    createBeginTest();
    lbMa = new ExtendJLabel("Mã");
    lbPhong = new ExtendJLabel("Phòng cha");
    lbTen = new ExtendJLabel("Tên phòng");
    lbDoanhNghiep = new ExtendJLabel("Doanh nghiệp");
    lbMieuTa = new ExtendJLabel("Miêu tả");
    lbMessage = new ExtendJLabel("");
    lbMessage.setForeground(Color.RED);

    txtMa = new ExtendJTextField();
    txtMa.setText(DateUtil.asCompactDateTimeId(new Date()));
    txtTen = new ExtendJTextField();
    txtMieuTa = new ExtendJTextArea();
    scrollPane = new JScrollPane();
    scrollPane.setViewportView(txtMieuTa);

    cbGroupDepartment = new DepartmentJComboBox();
    cbDoanhNghiep = new ExtendJTextField();

    txtMa.setName("txtMa");
    txtTen.setName("txtTen");
    txtMieuTa.setName("txtMieuTa");
    cbDoanhNghiep.setName("cbDoanhNghiep");
    cbGroupDepartment.setName("cbPhongTrucThuoc");
    cbDoanhNghiep.setEnabled(false);
    cbDoanhNghiep.setText(AccountModelManager.getInstance().getNameByLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId()));

    MyGroupLayout layout = new MyGroupLayout(this);
    layout.add(0, 0, lbTen);
    layout.add(0, 1, txtTen);
    layout.add(0, 2, lbMa);
    layout.add(0, 3, txtMa);
    layout.add(1, 0, lbPhong);
    layout.add(1, 1, cbGroupDepartment);
    layout.add(1, 2, lbDoanhNghiep);
    layout.add(1, 3, cbDoanhNghiep);
    layout.add(2, 0, lbMieuTa);
    layout.add(2, 1, scrollPane);
    layout.addMessenger(lbMessage);
    layout.updateGui();
  }

  // Lấy dữ liệu từ giao diện cho vào đối tượng
  private AccountGroup getData() {
	  restore = true;
    department.setName(txtMa.getText());
    department.setLabel(txtTen.getText());
    department.setOwner(ManagerAuthenticate.getInstance().getOrganizationLoginId());
    department.setDescription(txtMieuTa.getText());
    if (cbGroupDepartment.getSelectedDepartment() == null) {
      department.setParentPath(ManagerAuthenticate.getInstance().getOrganizationLoginId() + "/"
          + AccountModelManager.Department);
      department.setPath(department.getParentPath() + "/" + txtMa.getText());
    } else
      department.setParent(cbGroupDepartment.getSelectedDepartment());

    return department;
  }

  // Kiểm tra dữ liệu trước khi lưu
  public boolean checkData() {

	boolean check = true;
	boolean codeError = false;
    if (txtMa.getText().toString().trim().equals("")) {
    	lbMa.setForeground(Color.red);
    	check = false;
    } else {
    	lbMa.setForeground(new Color(51, 51, 51));
      if (isNew) {
        try {
        		AccountGroup ag = AccountModelManager.getInstance().getGroupByName(txtMa.getText());
        		if(ag != null)
        		{
        			check = false;
					codeError = true;
        			lbMa.setForeground(Color.red);
        			if (ag.isRecycleBin()){
        				PanelRecybin panel = new PanelRecybin(
								"Mã đã bị xóa trước đó!",
								" Chọn ĐỒNG Ý để lấy lại thông tin ban đầu hoặc THOÁT và nhập mã khác!");
						panel.setName("Xoa");
						DialogResto dialog = new DialogResto(panel, false,
								0, 120);
						dialog.setName("dlXoa");
						dialog.setTitle("Phòng ban bộ phận");
						dialog.setLocationRelativeTo(null);
						dialog.setModal(true);
						dialog.setVisible(true);
						if (panel.isDelete()) {
							restore = false;
							ag.setRecycleBin(false);
							AccountModelManager.getInstance().saveGroup(ag);
							return true;
						}
        			}
        		}
        	} catch (Exception e) {
        		return false;
        	}
      	}
    }

    if (txtTen.getText().equals("")) {
      check = false;
      lbTen.setForeground(Color.RED);
    } else
      lbTen.setForeground(new Color(51, 51, 51));

    if (!check) {
      lbMessage.setText("Kiểm tra lỗi báo đỏ !");
      if (codeError)
    	  lbMessage.setText("Lỗi trùng mã khu vực!");
      return false;
    } else{
    	lbMessage.setText("");
		return true;
    }
  
  }

  // Lấy dữ liệu từ đối tượng hiển thị ra giao diện
  public void setData(AccountGroup department) {
    this.department = department;
    txtMa.setText(department.getName());
    txtTen.setText(department.getLabel());
    txtMieuTa.setText(department.getDescription());
    cbGroupDepartment.setSelectDepartmentByPath(department.getParentPath());
    txtMa.setEnabled(false);
    setEnableCompoment(false);
  }

  // Thiết lập khả năng chỉnh sửa cho người dùng
  public void setEnableCompoment(boolean value) {
    txtTen.setEnabled(value);
    txtMieuTa.setEnabled(value);
    cbGroupDepartment.setEnabled(value);
    cbDoanhNghiep.setEnabled(value);
  }

  // Phương thức cho nút [Lưu]
  @Override
  public void save() throws Exception {
    if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
      JOptionPane.showMessageDialog(null, "Bạn chưa được phân quyền này !");
      return;
    } else {

		
      AccountGroup accOld = null;
      if (department != null && department.getId() != null) {
        accOld = AccountModelManager.getInstance().getGroupByPath(department.getPath());
      }
      if (accOld != null) {
        List<AccountGroup> list = AccountModelManager.getInstance().getAllChildrensByPath(accOld.getPath());
        AccountGroup parOld = AccountModelManager.getInstance().getGroupByPath(accOld.getParentPath());
        String partNew;
        try {
          partNew = cbGroupDepartment.getSelectedDepartment().getPath();
        } catch (Exception e) {
          partNew = "hkt/Phòng ban";
        }
        try {
          for (int i = 0; i < list.size(); i++) {
            AccountGroup acc = list.get(i);
            List<AccountMembership> listemp = AccountModelManager.getInstance()
                .findMembershipByGroupPath(acc.getPath());
            String strParent = acc.getParentPath().replaceAll(parOld.getPath(), partNew);
            acc.setParentPath(strParent);
            String strPath = acc.getPath().replaceAll(parOld.getPath(), partNew);
            acc.setPath(strPath);
            acc = AccountModelManager.getInstance().saveGroup(acc);
            for (int j = 0; j < listemp.size(); j++) {
              AccountMembership emp = listemp.get(j);
              emp.setGroupPath(acc.getPath());
              AccountModelManager.getInstance().saveAccountMembership(emp);
            }
          }
        } catch (Exception e) {
        }
        try {
          List<AccountMembership> listemp = AccountModelManager.getInstance().findMembershipByGroupPath(
              accOld.getPath());
          for (int j = 0; j < listemp.size(); j++) {
            AccountMembership emp = listemp.get(j);
            emp.setGroupPath(department.getPath());
            AccountModelManager.getInstance().saveAccountMembership(emp);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }

      }
      
  	
  	if (checkData()) {
  		if (restore)
			getData();
			
			AccountModelManager.getInstance().saveGroup(department);
			reset();
		} else {
			return;
		}
  	if(reset == true)
  		((DialogMain) getRootPane().getParent()).dispose();
    }
   
  }

  // Phương thức cho nút [Sửa]
  @Override
  public void edit() throws Exception {
    if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
      JOptionPane.showMessageDialog(null, "Bạn chưa được phân quyền này !");
      return;
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
      try {
        String str = "Xóa phòng ban ";
        PanelChoise panel = new PanelChoise(str + department + "?");
        panel.setName("Xoa");
        DialogResto dialog = new DialogResto(panel, false, 0, 80);
        dialog.setName("dlXoa");
        dialog.setTitle("Xóa phòng ban");
        dialog.setLocationRelativeTo(null);
        dialog.setModal(true);
        dialog.setVisible(true);
        if (panel.isDelete()) {
          List<InvoiceDetail> invoiceDetails = AccountingModelManager.getInstance().findInvoiceDetailByIdentifierId(
              department.getPath());
          if (!invoiceDetails.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Phòng ban đã có doanh thu không thể xóa!");
            return;
          }
          showDepartment();
        }
        	
      } catch (Exception e) {
      }
    } else
      JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
    	return;
  }

  public void showDepartment() throws Exception {

    String namedepartment = txtTen.getText();
    List<AccountGroup> listchildren = new ArrayList<AccountGroup>();
    listchildren = AccountModelManager.getInstance().getAllChildrensByPath(department.getPath());

    if (listchildren.isEmpty() || listchildren == null) {

      if (HRModelManager.getInstance().findEmployeesByAccountGroup(department) == null
          || HRModelManager.getInstance().findEmployeesByAccountGroup(department).isEmpty()) {
        AccountModelManager.getInstance().deleteGroup(department);
        ((JDialog) getRootPane().getParent()).dispose();
      } else {
        try {
          PanelManageDelete<Employee> panel = new PanelManageDelete<Employee>(namedepartment, HRModelManager
              .getInstance().findEmployeesByAccountGroup(department), new DepartmentComboBoxDelete(department));
          DialogResto dialog = new DialogResto(panel, true, 600, 300);
          dialog.dispose();
          dialog.setUndecorated(true);
          dialog.setTitle("Quản lý xóa phòng ban");
          dialog.setLocationRelativeTo(null);
          dialog.setModal(true);
          dialog.setVisible(true);
        } catch (Exception e2) {
          e2.printStackTrace();
        }

        if (HRModelManager.getInstance().findEmployeesByAccountGroup(department) == null
            || HRModelManager.getInstance().findEmployeesByAccountGroup(department).isEmpty()) {
          AccountModelManager.getInstance().deleteGroup(department);
          reset();
          ((JDialog) getRootPane().getParent()).dispose();
        }
      }
    } else {
      try {
        PanelManageDelete<AccountGroup> panel = new PanelManageDelete<AccountGroup>(namedepartment, AccountModelManager
            .getInstance().getChildrensByPath(department.getPath()), new DepartmentChildren(department));
        DialogResto dialog = new DialogResto(panel, true, 600, 100);
        dialog.dispose();
        dialog.setUndecorated(true);
        dialog.setTitle("Quản lý xóa phòng ban");
        dialog.setIcon("kho1.png");
        dialog.setLocationRelativeTo(null);
        dialog.setModal(true);
        dialog.setVisible(true);
      } catch (Exception e2) {
        e2.printStackTrace();
      }

      if ((AccountModelManager.getInstance().getAllChildrensByPath(department.getPath()).isEmpty())
          && (HRModelManager.getInstance().findEmployeesByAccountGroup(department) == null || HRModelManager
              .getInstance().findEmployeesByAccountGroup(department).isEmpty())) {
        AccountModelManager.getInstance().deleteGroup(department);

        reset();
        ((DialogMain) getRootPane().getParent()).dispose();
      }
    }
  }

  // Phương thức cho nút [Viết lại]
  @Override
  public void reset() throws Exception {
    department = new AccountGroup();
    txtMa.setText(DateUtil.asCompactDateTimeId(new Date()));
    txtTen.setText("");
    txtMieuTa.setText("");
    cbGroupDepartment.resetData();
    lbMa.setForeground(new Color(51, 51, 51));
    lbTen.setForeground(new Color(51, 51, 51));
    lbMessage.setText("");
    setEnableCompoment(true);
    txtMa.setEnabled(true);
    isNew = true;
  }

  // Phương thức cho nút [Xem lại]
  @Override
  public void refresh() throws Exception {
    setData(department);
  }

  @Override
  public void createBeginTest() {
    super.createBeginTest();
  }

  // Tự động thêm 50 bản ghi test sang trang
  @Override
  public void createDataTest() {
    for (int i = 4; i <= 50; i++) {
      try {
        AccountGroup ag = new AccountGroup();
        ag.setPath("hkt/Phòng ban/Mã phòng ban HktTest" + i);
        ag.setParentPath("hkt/Phòng ban");
        ag.setName("Mã phòng ban HktTest" + i);
        ag.setLabel("Tên phòng ban HktTest" + i);
        @SuppressWarnings("unused")
        AccountGroup a = AccountModelManager.getInstance().saveGroup(ag);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  // Xóa tất cả các bản ghi tự động sinh ra test
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
