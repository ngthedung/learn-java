package com.hkt.client.swingexp.app.nhansu.list;

import java.awt.Color;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;

import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextArea;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.core.DepartmentJComboBox;
import com.hkt.client.swingexp.app.core.HRJComboBox;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.hr.entity.Appointment;
import com.hkt.module.hr.entity.Appointment.Status;
import com.toedter.calendar.JTextFieldDateEditor;

public class PanelToDoCalendar extends MyPanel implements IDialogMain {
	
	public static String permisson;
	private JScrollPane scrollPaneMieuta;
	private ExtendJLabel lblCongViec, lblNhanVien, lblTrangThai, lblNgay, lblDoiTac, lblPhongBan, lblCongTy, 
	lblNoiDung, lblMieuTa, lblMessage;	
	private ExtendJTextField txtCongViec, txtCongTy, txtNoiDung, txtDoiTac;	
	private ExtendJTextArea txtMieuTa;
	private DepartmentJComboBox cbPhongBan;
	private ExtendJComboBox cbTrangThai;
	private HRJComboBox cbNhanVien;	
	private MyJDateChooser dcNgay;
	private JPanel panel1, panel2;
	
	private Appointment app;	
	private String[] listTrangThai = {"Chưa làm", "Đang làm", "Hoàn thành"};

	/**
	 * Create the PanelTodoCalendar
	 */
	
	//Hàm tạo
	public PanelToDoCalendar() {
		app = new Appointment();
		//Khởi tạo
		panel1 = new JPanel();		
		panel2 = new JPanel();
		
		lblCongViec = new ExtendJLabel("Công việc");		
		lblNoiDung = new ExtendJLabel("Nội dung");		
		lblMieuTa = new ExtendJLabel("Miêu tả");
		lblCongTy = new ExtendJLabel("Công ty");
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
		txtCongTy = new ExtendJTextField();
		txtDoiTac = new ExtendJTextField();
		
		scrollPaneMieuta = new JScrollPane();
		scrollPaneMieuta.setViewportView(txtMieuTa);
		
		dcNgay = new MyJDateChooser();
		((JTextField) dcNgay.getDateEditor()).setHorizontalAlignment(JTextField.RIGHT);
		dcNgay.setDate(new Date());
		
		cbNhanVien = new HRJComboBox();
		cbPhongBan = new DepartmentJComboBox();
		cbTrangThai = new ExtendJComboBox();
		cbTrangThai.setModel(new DefaultComboBoxModel<String>(listTrangThai));
		
		//Set name
		txtCongViec.setName("txtCongViec");	
		txtMieuTa.setName("txtMieuTa");	
		txtNoiDung.setName("txtNoiDung");
		txtCongTy.setName("txtCongTy");
		txtDoiTac.setName("txtDoiTac");
		cbPhongBan.setName("cbPhongBan");
		cbNhanVien.setName("cbNhanVien");
		cbTrangThai.setName("cbTrangThai");
		
		//Set màu comboBox
		UIManager.put( "ComboBox.background", Color.WHITE);
		UIManager.put( "ComboBox.disabledBackground", Color.WHITE);
		UIManager.put( "ComboBox.disabledForeground", new Color(51, 51, 51));
		UIManager.put( "TextField.inactiveBackground", Color.WHITE);
		UIManager.put( "TextField.inactiveForeground", new Color(51, 51, 51));
		drawLayout();
	}	
	
	//Sắp xếp trình bày các components
	
	private void drawLayout(){
		setOpaque(false);
		panel1.setOpaque(false);
		panel2.setOpaque(false);
		
		MyGroupLayout layout1 = new MyGroupLayout(panel1);
		layout1.add(0, 0, lblCongViec);
		layout1.add(0, 1, txtCongViec);
		layout1.add(1, 0, lblNoiDung);
		layout1.add(1, 1, txtNoiDung);
		layout1.add(2, 0, lblNgay);
		layout1.add(2, 1, dcNgay);
		layout1.add(2, 2, lblCongTy);
		layout1.add(2, 3, txtCongTy);
		layout1.add(3, 0, lblTrangThai);
		layout1.add(3, 1, cbTrangThai);
		layout1.add(3, 2, lblPhongBan);
		layout1.add(3, 3, cbPhongBan);
		layout1.add(4, 0, lblNhanVien);
		layout1.add(4, 1, cbNhanVien);
		layout1.add(4, 2, lblDoiTac);
		layout1.add(4, 3, txtDoiTac);
		layout1.updateGui();
		
		MyGroupLayout layout2 = new MyGroupLayout(panel2);
		layout2.add(0, 0, lblMieuTa);
		layout2.add(0, 1, scrollPaneMieuta);	
		layout2.updateGui();
		
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(panel1, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(panel2, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(lblMessage, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(panel1,
										GroupLayout.DEFAULT_SIZE, 130,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(panel2,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(lblMessage,
										GroupLayout.DEFAULT_SIZE, 20,
										GroupLayout.DEFAULT_SIZE)));
	}
	
	//Kiểm tra dữ liệu trước khi lưu
	private boolean checkData(){
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
	
	//Lấy dữ liệu từ giao diện đổ vào đối tượng
	private void getData(){
		app.setOrgLoginId(txtCongTy.getText());
		app.setName(txtCongViec.getText());
		app.setPartnerLoginId(txtDoiTac.getText());			
		app.setDateStart(dcNgay.getDate());			
		app.setDescription(txtMieuTa.getText());
		
		Status status = null;
		String strStatus = cbTrangThai.getSelectedItem().toString();
		if(strStatus.equals("Chưa làm"))
			status = Status.UNACCOMPLISHED;
		if(strStatus.equals("Đang làm"))
			status = Status.ONGOING;
		if(strStatus.equals("Hoàn thành"))
			status = Status.COMPLETE;
		
		app.setStatus(status);
		app.setContent(txtNoiDung.getText());
		
		try{
			app.setEmployeeLoginId(cbNhanVien.getSelectedEmployee().getLoginId());
		} catch(Exception ex){
		}
		
		try{	
			app.setGroupPath(cbPhongBan.getSelectedDepartment().getPath());
		} catch (Exception ex){
		}
		
	}

	//Cho dữ liệu ra giao diện
	public void setData(Appointment a){		
		this.app = a;		
		txtCongTy.setText(a.getOrgLoginId());
		txtCongViec.setText(a.getName());
		txtDoiTac.setText(a.getPartnerLoginId());
		txtMieuTa.setText(a.getContent());
		dcNgay.setDate(a.getDateStart());;
		txtNoiDung.setText(a.getContent());
		
		cbNhanVien.setSelectedEmployee(a.getEmployeeLoginId());
		cbPhongBan.setSelectDepartmentByPath(a.getGroupPath());
		
		if(a.getStatus().equals(Status.UNACCOMPLISHED))
			cbTrangThai.setSelectedIndex(0);
		if(a.getStatus().equals(Status.ONGOING))
			cbTrangThai.setSelectedIndex(1);
		if(a.getStatus().equals(Status.COMPLETE))
			cbTrangThai.setSelectedIndex(2);

		setEnableCompoment(false);
	}

	//Thiết lập trạng thái cho chỉnh sửa hay không?
	private void setEnableCompoment(boolean value){
		txtCongTy.setEnabled(value);
		txtCongViec.setEnabled(value);
		txtDoiTac.setEnabled(value);
		txtMieuTa.setEnabled(value);
		dcNgay.setEnabled(value);
		txtNoiDung.setEnabled(value);
		cbNhanVien.setEnabled(value);
		cbPhongBan.setEnabled(value);
		cbTrangThai.setEnabled(value);
		
		((JTextFieldDateEditor)dcNgay.getDateEditor()).setDisabledTextColor(new Color(51, 51, 51));
		
		txtCongTy.setDisabledTextColor(new Color(51, 51, 51));
		txtCongViec.setDisabledTextColor(new Color(51, 51, 51));
		txtDoiTac.setDisabledTextColor(new Color(51, 51, 51));
		txtMieuTa.setDisabledTextColor(new Color(51, 51, 51));
		txtNoiDung.setDisabledTextColor(new Color(51, 51, 51));
	}

	//Phương thức cho nút [Thêm]
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
	}

	//Phương thức cho nút [Sửa]
	@Override
	public void edit() throws Exception {
		if(UIConfigModelManager.getInstance().getPermission(permisson)==Capability.READ){
			JOptionPane.showMessageDialog(null,"Bạn chưa được cấp quyền này !");
		} else{
			setEnableCompoment(true);
		}
	}

	//Phương thức cho nút [Xóa]
	@Override
	public void delete() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permisson) == Capability.ADMIN) {
			if (HRModelManager.getInstance().deleteAppointment(app)) {
				reset();
			} else lblMessage.setText("Hãy chắc chắn là bạn đã chọn 1 công việc để xóa");
		} else JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
	}

	//Phương thức cho nút [Viết lại]
	@Override
	public void reset() throws Exception {
		app = new Appointment();
		
		txtCongTy.setText("");
		txtCongViec.setText("");
		txtDoiTac.setText("");
		txtMieuTa.setText("");
		dcNgay.setDate(new Date());
		txtNoiDung.setText("");
		
		try{
			cbNhanVien.setSelectedIndex(0);
			cbPhongBan.setSelectedIndex(0);
			cbTrangThai.setSelectedIndex(0);
		} catch(Exception ex){}
		
		lblCongViec.setForeground(new Color(51, 51, 51));
		lblMessage.setText("");
		setEnableCompoment(true);
	}

	//Phương thức cho nút [Xem lại]
	@Override
	public void refresh() throws Exception {
		setData(app);		
	}

	//Dữ liệu thêm mẫu tự động để Test
	@Override
	public void createDataTest() {
		for(int i = 4; i <= 50; i ++){
			app = new Appointment();
			app.setOrgLoginId("Công ty HktTest"+i);
//			app.setGroupPath();
//			app.setEmployeeLoginId();
			app.setName("Công việc HktTest"+i);
			app.setPartnerLoginId("Đối tác HktTest"+i);			
			app.setDateStart(new Date());			
			app.setDescription("Miêu tả HktTest"+i);
			app.setStatus(Status.ONGOING);
			app.setContent("Nội dung HktTest"+i);
			
			try {
				HRModelManager.getInstance().saveAppointment(app);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}
}
