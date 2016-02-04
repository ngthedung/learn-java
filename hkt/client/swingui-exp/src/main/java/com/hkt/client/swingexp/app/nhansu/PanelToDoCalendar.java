package com.hkt.client.swingexp.app.nhansu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DateEditor;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextArea;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.component.TextPopup;
import com.hkt.client.swingexp.app.core.DepartmentJComboBox;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.HRJComboBox;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.hr.entity.Appointment;
import com.hkt.module.hr.entity.Appointment.Status;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.partner.customer.entity.Customer;

@SuppressWarnings("serial")
public class PanelToDoCalendar extends MyPanel implements IDialogMain {
  public static String        permisson;
  private JScrollPane         scrollPaneMieuta;
  @SuppressWarnings("unused")
private ExtendJLabel        lblCongViec, lblNhanVien, lblTrangThai, lblNgay, lblDoiTac, lblPhongBan, lblGio,
      lblNoiDung, lblMieuTa, lblMessage,  lbla;
  private ExtendJTextField    txtCongViec, txtNoiDung;
  private TextPopup<Customer> txtDoiTac;
  private ExtendJTextArea     txtMieuTa;
  private DepartmentJComboBox cbPhongBan;
  private ExtendJComboBox     cbTrangThai;
  private HRJComboBox         cbNhanVien;
  private MyJDateChooser      dcNgay;
  private JPanel              panel1, panel2;
  private JSpinner 			   jsGio, jsPhut;
  @SuppressWarnings("unused")
private boolean             flagEdit      = false;
  private Appointment         app;
  private String[]            listTrangThai = { "Chưa làm", "Đang làm", "Hoàn thành" };
  private int i, j;
  private Date  date = new Date();
  private boolean reset = false;
  /**
   * Create the PanelTodoCalendar
   */

  // Hàm tạo
  @SuppressWarnings("unchecked")
public PanelToDoCalendar(boolean flagEdit) {
    this.flagEdit = flagEdit;
    if (flagEdit == false) {
      createBeginTest();
    }
    app = new Appointment();
    // Khởi tạo
    panel1 = new JPanel();
    panel2 = new JPanel();
    lblCongViec = new ExtendJLabel("Công việc");
    lblNoiDung = new ExtendJLabel("Nội dung");
    lblMieuTa = new ExtendJLabel("Miêu tả");
    lblGio = new ExtendJLabel("Giờ");
    lbla = new ExtendJLabel(":");
    lblPhongBan = new ExtendJLabel("Phòng ban");
    lblDoiTac = new ExtendJLabel("Đối tác");
    lblNgay = new ExtendJLabel("Ngày");
    lblNhanVien = new ExtendJLabel("Nhân viên");
    lblTrangThai = new ExtendJLabel("Trạng thái");
    lblMessage = new ExtendJLabel("");
    lblMessage.setForeground(Color.RED);

    txtCongViec = new ExtendJTextField();
    txtMieuTa = new ExtendJTextArea();

    txtNoiDung = new ExtendJTextField();
    txtDoiTac = new TextPopup<Customer>(Customer.class);
    try {
      txtDoiTac.setData(CustomerModelManager.getInstance().getCustomers(false));
    } catch (Exception e) {
    }

    scrollPaneMieuta = new JScrollPane();
    scrollPaneMieuta.setViewportView(txtMieuTa);
    

    dcNgay = new MyJDateChooser();
    ((JTextField) dcNgay.getDateEditor()).setHorizontalAlignment(JTextField.RIGHT);
    dcNgay.setDate(date);
    try {
    	 	SpinnerModel smGio = new SpinnerDateModel();
    	    jsGio = new JSpinner(smGio);
    	    DateEditor editor = new JSpinner.DateEditor(jsGio, "HH");
    	    jsGio.setEditor(editor);
    	    editor.getTextField().setHorizontalAlignment(SwingConstants.RIGHT);
    	    Calendar calendar1 = Calendar.getInstance();
    	    calendar1.setTime(new Date());
			i = calendar1.get(Calendar.HOUR_OF_DAY);
    	    jsGio.addChangeListener(new ChangeListener() {		
    			@Override
    			public void stateChanged(ChangeEvent e) {
    	    	    Calendar calendar1 = Calendar.getInstance();
    				Date date1 = (Date) ((JSpinner)e.getSource()).getValue();
    	     	    calendar1.setTime(date1);
    				i = calendar1.get(Calendar.HOUR_OF_DAY);
    			}
    		});
    	    SpinnerModel smPhut = new SpinnerDateModel();
    	    jsPhut = new JSpinner(smPhut);
    	    DateEditor deditor = new JSpinner.DateEditor(jsPhut, "mm");
    	    jsPhut.setEditor(deditor);
    	    deditor.getTextField().setHorizontalAlignment(SwingConstants.RIGHT);
			j = calendar1.get(Calendar.MINUTE);
    	    jsPhut.addChangeListener(new ChangeListener() {
    			
    			@Override
    			public void stateChanged(ChangeEvent e) {
    	    	    Calendar calendar1 = Calendar.getInstance();
    				Date date1 = (Date) ((JSpinner)e.getSource()).getValue();
    	     	    calendar1.setTime(date1);
    				j = calendar1.get(Calendar.MINUTE);
    				
    			}
    		});
	} catch (Exception e) {
	}
   
    cbNhanVien = new HRJComboBox();
    cbPhongBan = new DepartmentJComboBox();
    cbTrangThai = new ExtendJComboBox();
    cbTrangThai.setModel(new DefaultComboBoxModel<String>(listTrangThai));

    // Set name
    txtCongViec.setName("txtCongViec");
    txtMieuTa.setName("txtMieuTa");
    txtNoiDung.setName("txtNoiDung");
    txtDoiTac.setName("txtDoiTac");
    cbPhongBan.setName("cbPhongBan");
    cbNhanVien.setName("cbNhanVien");
    cbTrangThai.setName("cbTrangThai");

    drawLayout();
  }
//Panel thêm các component con
 protected class PanelContainer extends JPanel {
   public PanelContainer(Component comp1, Component comp2) {
     this.setLayout(new BorderLayout(0, 5));
     this.setOpaque(false);

//     this.add(comp1, BorderLayout.LINE_START);
     this.add(comp1, BorderLayout.CENTER);
     comp1.setPreferredSize(new Dimension(95,22));
     this.add(comp2, BorderLayout.LINE_END);
     comp2.setPreferredSize(new Dimension(95, 22));
   }
 }
  // Sắp xếp trình bày các components
  private void drawLayout() {
    setOpaque(false);
    panel1.setOpaque(false);
    panel2.setOpaque(false);
    
    PanelContainer pc = new PanelContainer(jsGio,jsPhut); 
    MyGroupLayout layout1 = new MyGroupLayout(this);
    layout1.add(0, 0, lblCongViec);
    layout1.add(0, 1, txtCongViec);
    layout1.add(1, 0, lblNoiDung);
    layout1.add(1, 1, txtNoiDung);
    layout1.add(2, 0, lblGio);
    layout1.add(2, 1, pc);
    layout1.add(2, 2, lblNgay);
    layout1.add(2, 3, dcNgay);
    layout1.add(3, 0, lblTrangThai);
    layout1.add(3, 1, cbTrangThai);
    layout1.add(3, 2, lblPhongBan);
    layout1.add(3, 3, cbPhongBan);
    layout1.add(4, 0, lblNhanVien);
    layout1.add(4, 1, cbNhanVien);
    layout1.add(4, 2, lblDoiTac);
    layout1.add(4, 3, txtDoiTac);
    layout1.add(5, 0, lblMieuTa);
    layout1.add(5, 1, scrollPaneMieuta);
    layout1.addMessenger(lblMessage);
    layout1.updateGui();
  }

  // Kiểm tra dữ liệu trước khi lưu
  private boolean checkData() {
    getData();
    if (app.getName().equals("") || app.getName() == null) {
      lblCongViec.setForeground(Color.red);
      lblMessage.setText("Lỗi dòng báo đỏ");
      return false;
    } else {
      lblCongViec.setForeground(new Color(51, 51, 51));
      lblMessage.setText("");
      return true;
    }
  }

  // Lấy dữ liệu từ giao diện đổ vào đối tượng
  private void getData() {

    app.setName(txtCongViec.getText());    
    Calendar c = Calendar.getInstance();
    c.setTime(dcNgay.getDate());
    c.set(Calendar.HOUR_OF_DAY, i);
    c.set(Calendar.MINUTE, j);
    app.setDateStart(c.getTime());
    app.setDescription(txtMieuTa.getText());
    if(txtDoiTac.getItem()!=null){
      app.setPartnerLoginId(txtDoiTac.getItem().getLoginId());
    }
    else
    {
    	app.setPartnerLoginId(null);
    }
    Status status = null;
    String strStatus = cbTrangThai.getSelectedItem().toString();
    if (strStatus.equals("Chưa làm"))
      status = Status.UNACCOMPLISHED;
    if (strStatus.equals("Đang làm"))
      status = Status.ONGOING;
    if (strStatus.equals("Hoàn thành"))
      status = Status.COMPLETE;

    app.setStatus(status);
    app.setContent(txtNoiDung.getText());
    try {
    	if(cbNhanVien.getSelectedEmployee() !=null)
    	{
    		app.setEmployeeLoginId(cbNhanVien.getSelectedEmployee().getLoginId());
    	}
    	else
    	{
    		app.setEmployeeLoginId(null);
    	}
    } catch (Exception ex) {
    }

    try {
    	if(cbPhongBan.getSelectedDepartment() !=null)
    	{
    		app.setGroupPath(cbPhongBan.getSelectedDepartment().getPath());
    	}
    	else
    	{
    		app.setGroupPath(null);
    	}
    } catch (Exception ex) {
    }

  }

  // Cho dữ liệu ra giao diện
  public void setData(Appointment a) {
	Calendar calendar = Calendar.getInstance();
    this.app = a;
    Date datea = a.getDateStart();
    calendar.setTime(datea);
    txtCongViec.setText(a.getName());
    txtMieuTa.setText(a.getDescription());
    dcNgay.setDate(datea);
    jsGio.setValue(datea);
    jsPhut.setValue(datea);
    txtNoiDung.setText(a.getContent());
    if (a.getEmployeeLoginId() != null) {
      cbNhanVien.setSelectedEmployee(a.getEmployeeLoginId());
    }
    try {
      cbPhongBan.setSelectDepartmentByPath(a.getGroupPath());
    } catch (Exception e) {
    }
    try {
      Customer c = CustomerModelManager.getInstance().getBydLoginId(a.getPartnerLoginId());
      txtDoiTac.setObject(c);
      txtDoiTac.setText(c.toString());
      txtDoiTac.setObject(null);
    } catch (Exception e) {
    }

    if (a.getStatus().equals(Status.UNACCOMPLISHED))
      cbTrangThai.setSelectedIndex(0);
    if (a.getStatus().equals(Status.ONGOING))
      cbTrangThai.setSelectedIndex(1);
    if (a.getStatus().equals(Status.COMPLETE))
      cbTrangThai.setSelectedIndex(2);

    setEnableCompoment(false);
  }

  // Thiết lập trạng thái cho chỉnh sửa hay không?
  private void setEnableCompoment(boolean value) {
//	txtPhut.setEnabled(value);
    txtCongViec.setEnabled(value);
    txtDoiTac.setEnabled(value);
    txtMieuTa.setEnabled(value);
    dcNgay.setEnabled(value);
    txtNoiDung.setEnabled(value);
    cbNhanVien.setEnabled(value);
    cbPhongBan.setEnabled(value);
    cbTrangThai.setEnabled(value);
    jsGio.setEnabled(value);
    jsPhut.setEnabled(value);

  }

  // Phương thức cho nút [Thêm]
  @Override
  public void save() throws Exception {
    if (UIConfigModelManager.getInstance().getPermission(permisson) == Capability.READ) {
      JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
    } else {
      if (!checkData())
        return;
      HRModelManager.getInstance().saveAppointment(app);
      reset();
    }
    if(reset = true)
    	((DialogMain) getRootPane().getParent()).dispose();

  }

  // Phương thức cho nút [Sửa]
  @Override
  public void edit() throws Exception {
    if (UIConfigModelManager.getInstance().getPermission(permisson) == Capability.READ) {
      JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
    } else {
      setEnableCompoment(true);
    }
  }

  // Phương thức cho nút [Xóa]
  @Override
  public void delete() throws Exception {
    if (UIConfigModelManager.getInstance().getPermission(permisson) == Capability.ADMIN) {

      String str = "Xóa ";
      PanelChoise pnPanel = new PanelChoise(str + app + "?");
      pnPanel.setName("Xoa");
      DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
      dialog1.setName("dlXoa");
      dialog1.setTitle("Xóa công việc hàng ngày");
      dialog1.setLocationRelativeTo(null);
      dialog1.setModal(true);
      dialog1.setVisible(true);

      if (pnPanel.isDelete()) {
        HRModelManager.getInstance().deleteAppointment(app);
        reset();
        ((DialogMain) getRootPane().getParent()).dispose();
      } else if (pnPanel.isDelete() == false) {
        reset();
      } else
        lblMessage.setText("Hãy chắc chắn là bạn đã chọn 1 công việc để xóa");
    } else
      JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
  }

  // Phương thức cho nút [Viết lại]
  @Override
  public void reset() throws Exception {
	  reset = true;
    app = new Appointment();
    txtCongViec.setText("");
    txtDoiTac.setItem(null);
    txtDoiTac.setObject(null);
    txtDoiTac.setText("");
    txtMieuTa.setText("");
    txtNoiDung.setText("");

    try {
      cbNhanVien.setSelectedIndex(0);
      cbPhongBan.setSelectedIndex(0);
      cbTrangThai.setSelectedIndex(0);
    } catch (Exception ex) {
    }

    lblCongViec.setForeground(new Color(51, 51, 51));
    lblMessage.setText("");
    setEnableCompoment(true);
    
  }

  // Phương thức cho nút [Xem lại]
  @Override
  public void refresh() throws Exception {
    setData(app);
  }

  // Tạo dữ liệu mẫu liên quan
  @Override
  public void createBeginTest() {
    if (MyPanel.isTest) {

      for (int i = 0; i < 10; i++) {
        // Tạo khách hàng
        Account account = new Account();
        account.setLoginId("HktTest" + i);
        account.setPassword("0000");
        account.setEmail("HktTest" + i + "@gmail.com");
        try {
          AccountModelManager.getInstance().saveAccount(account);
        } catch (Exception e1) {
          e1.printStackTrace();
        }
        Employee employee = new Employee();
        employee.setLoginId("HktTest" + i);
        employee.setOrganizationLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
        try {
          HRModelManager.getInstance().saveEmployee(employee);
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      }

    }
  }

  // Dữ liệu thêm mẫu tự động để Test
  @Override
  public void createDataTest() {
    for (int i = 11; i <= 60; i++) {
      Appointment appTest = new Appointment();
      appTest.setOrgLoginId("Công ty HktTest" + i);
      appTest.setEmployeeLoginId("HktTest2");
      appTest.setName("Công việc HktTest" + i);
      appTest.setPartnerLoginId("Đối tác HktTest" + i);
      appTest.setDateStart(new Date());
      appTest.setDescription("Miêu tả HktTest" + i);
      appTest.setStatus(Status.ONGOING);
      appTest.setContent("Nội dung HktTest" + i);

      try {
        HRModelManager.getInstance().saveAppointment(appTest);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  // Xóa toàn bộ dữ liệu mãu tự động sinh
  @Override
  public void deleteDataTest() {
    if (!PanelTestAll.runAll) {
      DialogTest.getInstance().show("Xóa toàn bộ dữ liệu test ?");
      if (DialogTest.getInstance().isTest()) {
        try {
          AccountModelManager.getInstance().deleteTest("HktTest");
          HRModelManager.getInstance().deleteTest("HktTest");
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

public void setDate(Date date) {
	this.date = date;
	dcNgay.setDate(date);
	
}


}
