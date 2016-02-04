package com.hkt.client.swingexp.app.khachhang;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextArea;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.core.CustomerJComboBox;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.customer.entity.Point;
import com.hkt.module.partner.customer.entity.PointTransaction;

@SuppressWarnings("serial")
public class PanelPointTransaction extends MyPanel implements IDialogMain {
	public static String permission;
	private ExtendJTextField txtTotalPoint, txtPointUsed, txtPointLeft;
	private ExtendJLabel lblPartner, lblTotalPoint, lblPointUsed, lblPointLeft, lblType, lbDescription, lblMessage;
	private ExtendJTextArea txtDescription;
	private JScrollPane scrollPane;
	private ExtendJComboBox cbType;
	private CustomerJComboBox cbCustomer;
	private String[] itemsType = { "Tăng thêm", "Giảm đi" };
	private Point point;
	private PointTransaction pointTransaction;
	private Long idPointTest = null;
	private boolean reset = false;

	/**
	 * Create the PanelPointTransaction
	 */

	//Hàm tạo 
	public PanelPointTransaction() {
		init();
		pointTransaction = new PointTransaction();
		
		//Bắt sự kiện khi thay đổi item trong comboBox
		cbCustomer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					point = CustomerModelManager.getInstance().getPointByCustomerId(cbCustomer.getSelectedCustomer().getId());
					if(point == null){
						point = new Point();
						point.setLoginId(cbCustomer.getSelectedCustomer().getLoginId());
						point.setCustomerId(cbCustomer.getSelectedCustomer().getId());
						point.setPoint(0);
						try {
							point = CustomerModelManager.getInstance().savePoint(point);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				txtTotalPoint.setText(MyDouble.valueOf(point.getPoint()).toString());
				if (!txtPointUsed.getText().equals("") && txtPointUsed.getText() != null) {
					calculatePoint();
				}
			}
		});
		
		//Bắt sự kiện khi thay đổi item trong comboBox
		cbType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!txtTotalPoint.getText().equals("")
						&& txtTotalPoint.getText() != null
						&& !txtPointUsed.getText().equals("")
						&& txtPointUsed.getText() != null) {
					calculatePoint();
				}
			}
		});
		
		//Bắt sự kiện nhập phím khi nhập
		txtPointUsed.addCaretListener(new CaretListener() {
      
      @Override
      public void caretUpdate(CaretEvent e) {
        try {
          if (!txtTotalPoint.getText().equals("")
              && txtTotalPoint.getText() != null)
            calculatePoint();
          lblMessage.setText("");
          lblPointUsed.setForeground(new Color(51, 51, 51));
        } catch (Exception ex) {
          lblMessage.setText("Vui lòng nhập số");
          lblPointUsed.setForeground(Color.RED);
        }
        
      }
    });
		
		//Lựa chọn giá trị ban đầu cho comboBox và textField
		if(cbCustomer.getItemCount() > 0){
			try{
				cbCustomer.setSelectedIndex(0);
				point = CustomerModelManager.getInstance().getPointByCustomerId(cbCustomer.getSelectedCustomer().getId());
			} catch(Exception ex){
			}
			txtTotalPoint.setText(MyDouble.valueOf(point.getPoint()).toString());
		}
	}

	//Khởi tạo và vẽ các components cho giao diện
	@SuppressWarnings("unchecked")
	private void init() {
		createBeginTest();
		lblPartner = new ExtendJLabel("Khách hàng");
		lblTotalPoint = new ExtendJLabel("Tổng điểm");
		lblPointUsed = new ExtendJLabel("Điểm dùng");
		lblPointLeft = new ExtendJLabel("Điểm còn lại");
		lblType = new ExtendJLabel("Loại");
		lbDescription = new ExtendJLabel("Ghi chú");
		lblMessage = new ExtendJLabel("");
		lblMessage.setForeground(Color.RED);

		cbCustomer = new CustomerJComboBox();
		cbCustomer.removeIndex(0);
		cbType = new ExtendJComboBox();
		cbType.setModel(new DefaultComboBoxModel<String>(itemsType));

		txtPointLeft = new ExtendJTextField();
		txtPointUsed = new ExtendJTextField();
		txtTotalPoint = new ExtendJTextField();
		txtDescription = new ExtendJTextArea();
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(txtDescription);

		txtPointLeft.setHorizontalAlignment(JTextField.RIGHT);
		txtPointUsed.setHorizontalAlignment(JTextField.RIGHT);
		txtTotalPoint.setHorizontalAlignment(JTextField.RIGHT);
		
		txtTotalPoint.setEnabled(false);
		txtPointLeft.setEnabled(false);
		
		txtPointUsed.setName("txtDiemDung");
		txtDescription.setName("txtGhiChu");
		cbCustomer.setName("cbDoiTac");
		cbType.setName("cbLoai");
		
		MyGroupLayout layout = new MyGroupLayout(this);
		layout.add(0, 0, lblPartner);
		layout.add(0, 1, cbCustomer);
		layout.add(1, 0, lblTotalPoint);
		layout.add(1, 1, txtTotalPoint);
		layout.add(1, 2, lblPointLeft);
		layout.add(1, 3, txtPointLeft);
		layout.add(2, 0, lblPointUsed);
		layout.add(2, 1, txtPointUsed);
		layout.add(2, 2, lblType);
		layout.add(2, 3, cbType);
		layout.add(3, 0, lbDescription);
		layout.add(3, 1, scrollPane);
		layout.addMessenger(lblMessage);
		layout.updateGui();
	}

	//Tính toán điểm dùng
	private void calculatePoint() {
		double pointLeft = 0;
		if (cbType.getSelectedIndex() == 0) {
			pointLeft = MyDouble.parseDouble(txtTotalPoint.getText())
					+ MyDouble.parseDouble(txtPointUsed.getText());
		} else {
			pointLeft = MyDouble.parseDouble(txtTotalPoint.getText())
					- MyDouble.parseDouble(txtPointUsed.getText());
		}
		txtPointLeft.setText(MyDouble.valueOf(pointLeft).toString());
	}

	//Lấy dữ liệu từ giao diện đổ vào đối tượng
	public void getData(Point p, PointTransaction pt) {
		this.point = p;
		this.pointTransaction = pt;
		point.setPoint(MyDouble.parseDouble(txtPointLeft.getText()));
		pointTransaction.setLoginId(point.getLoginId());
		pointTransaction.setPointId(point.getId());
		pointTransaction.setDescription(txtDescription.getText());
		if (cbType.getSelectedIndex() == 0)
			pointTransaction
					.setPoint(MyDouble.parseDouble(txtPointUsed.getText()));
		if (cbType.getSelectedIndex() == 1)
			pointTransaction.setPoint(-MyDouble.parseDouble(txtPointUsed
					.getText()));

		pointTransaction.setDateExecute(new Date());
	}

	//Cho dữ liệu hiển thị ra giao diện
	public void setData(Point p, PointTransaction pt) {
		this.point = p;
		this.pointTransaction = pt;
		cbCustomer.setSelectItem(p.getCustomerId());
		txtTotalPoint.setText(MyDouble.valueOf(p.getPoint()).toString());
		txtDescription.setText(pt.getDescription());
		double numPointUsed = pt.getPoint();
		if (numPointUsed < 0) {
			cbType.setSelectedIndex(1);
			txtPointUsed.setText(MyDouble.valueOf(pt.getPoint() / (-1)).toString());
		}
		if (numPointUsed > 0) {
			cbType.setSelectedIndex(0);
			txtPointUsed.setText(MyDouble.valueOf(pt.getPoint()).toString());
		}
		setEnabledCompoment(false);
	}

	//Cho phép người dùng edit textField
	public void setEnabledCompoment(boolean value) {
		txtPointUsed.setEnabled(value);
		txtDescription.setEnabled(value);
		cbCustomer.setEnabled(value);
		cbType.setEnabled(value);
	}

	//Kiểm tra dữ liệu trước khi lưu
	public boolean checkData() {
		if(point != null){
			double numPointUsed = 0;
			int count = 0;
//			lblMessage.setText("");
//			lblPartner.setForeground(new Color(51, 51, 51));
			try {
				numPointUsed = MyDouble.parseDouble(txtPointUsed.getText());
				if (numPointUsed <= 0) {
					lblMessage.setText("Nhập số điểm dùng lớn hơn 0");
					lblPointLeft.setForeground(Color.RED);
					count++;
				} else {
					lblMessage.setText("");
					lblPointLeft.setForeground(new Color(51, 51, 51));
				}

				if (MyDouble.parseDouble(txtPointLeft.getText()) < 0) {
					count++;
				} else {
					lblMessage.setText("");
					lblPointLeft.setForeground(new Color(51, 51, 51));
				}
				if (txtTotalPoint.getText().equals("")
						&& txtTotalPoint.getText() == null) {
					count++;
				} else {
					lblMessage.setText("");
					lblPartner.setForeground(new Color(51, 51, 51));
				}

				if (count > 0) {
					lblMessage.setText("Vui lòng kiểm tra dòng báo đỏ");
					lblPointUsed.setForeground(Color.RED);
					return false;
				} else
					return true;
			} catch (Exception ex) {
				lblMessage.setText("Vui lòng kiểm tra dòng báo đỏ");
				lblPointUsed.setForeground(Color.RED);
				return false;
			} 
		} else {
			lblMessage.setText("Chưa có đối tác nào?");
			lblPartner.setForeground(Color.RED);
			return false;
		}
	}

	//Phương thức cho nút [Lưu]
	@Override
	public void save() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane
					.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
		} else {
			if (checkData()) {
				getData(point, pointTransaction);
				CustomerModelManager.getInstance().savePoint(point);
				CustomerModelManager.getInstance().savePointTransaction(pointTransaction);
				Customer c = cbCustomer.getSelectedCustomer();
				Point p = CustomerModelManager.getInstance().getPointByCustomerId(c.getId());
				if (p != null && p.getPoint() != 0) {
					List<AccountGroup> acs = CustomerModelManager.getInstance().getCustomerGroup();
					for (AccountGroup ac : acs) {
						try {
							double d = MyDouble.parseDouble(ac.getOwner());
							if (p.getPoint() >= d) {
								List<AccountMembership> accountMemberships = AccountModelManager.getInstance()
								    .findMembershipByAccountLoginId(c.getLoginId());
								AccountMembership accountMembership = null;
								if (accountMemberships.size() > 0) {
									for (AccountMembership ac1 : accountMemberships) {
										if (ac1.getRole().equals(AccountModelManager.Customer)) {
											accountMembership = ac1;
											break;
										}
									}

								}
								if (accountMembership == null) {
									accountMembership = new AccountMembership();
									accountMembership.setLoginId(c.getLoginId());
									accountMembership.setRole(AccountModelManager.Customer);
									accountMembership.setGroupPath(ac.getPath());
									AccountModelManager.getInstance().saveAccountMembership(accountMembership);
								} else {
									accountMembership.setGroupPath(ac.getPath());
									AccountModelManager.getInstance().saveAccountMembership(accountMembership);
								}
							}
						} catch (Exception e) {
						}
					}
				}
				DialogNotice.getInstace().setVisible(true);
				reset();
			}
			if(reset == true)
				((DialogMain) getRootPane().getParent()).dispose();
		}
	}

	//Phương thức cho nút [Sửa]
	@Override
	public void edit() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane
					.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
		} else {

			txtPointLeft.setText(MyDouble.valueOf(point.getPoint()).toString());
			double numPointUsed = pointTransaction.getPoint();
			if (numPointUsed < 0) {
				txtTotalPoint.setText(MyDouble.valueOf(point.getPoint()
						- pointTransaction.getPoint()).toString());
			}
			if (numPointUsed > 0) {
				txtTotalPoint.setText(MyDouble.valueOf(point.getPoint()
						- pointTransaction.getPoint()).toString());
			}
			setEnabledCompoment(true);
			reset = true;
		}
	}

	//Phương thức cho nút [Xóa]
	@Override
	public void delete() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.ADMIN) {

			
			
			double numPointUsed = pointTransaction.getPoint();
			if (numPointUsed < 0) {
				point.setPoint(point.getPoint() - pointTransaction.getPoint());
			}
			if (numPointUsed > 0) {
				point.setPoint(point.getPoint() + pointTransaction.getPoint());
			}
			
			String str = "Xóa điểm sử dụng ";
			PanelChoise pnPanel = new PanelChoise(str + pointTransaction + "?");
			pnPanel.setName("Xoa");
			DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
			dialog1.setName("dlXoa");
			dialog1.setTitle("Xóa ");
			dialog1.setLocationRelativeTo(null);
			dialog1.setModal(true);
			dialog1.setVisible(true);
			
			if (pnPanel.isDelete()) {
				CustomerModelManager.getInstance().deletePointTransaction(pointTransaction);
				reset();
				((DialogMain) getRootPane().getParent()).dispose();
	        }else if (pnPanel.isDelete()==false) {
		      reset();
	        } else {
				lblMessage.setText("Có lỗi không xóa được!");
			}
		
		} else {
			JOptionPane
					.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
		}
	}

	//Phương thức cho nút [Viết lại]
	@Override
	public void reset() throws Exception {
		txtPointUsed.setText("");
		txtPointLeft.setText("");
		txtDescription.setText("");
		cbType.setSelectedIndex(0);
		lblMessage.setText("");
		lblPointUsed.setForeground(new Color(51, 51, 51));
		lblPartner.setForeground(new Color(51, 51, 51));
		if(cbCustomer.getItemCount() > 0){
			try{
				cbCustomer.setSelectedIndex(0);
				point = CustomerModelManager.getInstance().getPointByCustomerId(cbCustomer.getSelectedCustomer().getId());
				txtTotalPoint.setText(MyDouble.valueOf(point.getPoint()).toString());
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}
		pointTransaction = new PointTransaction();
	}

	//Phương thức cho nút [Xem lại]
	@Override
	public void refresh() throws Exception {
		setData(point, pointTransaction);
		txtPointLeft.setText("");
	}

	//Tạo dữ liệu mẫu liên quan
	@Override
	public void createBeginTest() {
		if(isTest){
			//Tạo đối tác
			Account account = new Account();
			account.setLoginId("HktTest1");
			account.setPassword("HktTest1");
			account.setEmail("HktTest1@gmail.com");
			try {
				AccountModelManager.getInstance().saveAccount(account);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			Customer customer = new Customer();
			customer.setLoginId("HktTest1");
			customer.setOrganizationLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
			Long idCustomer;
			try {
				Customer cus = CustomerModelManager.getInstance().saveCustomer(customer);
				idCustomer = cus.getId();
			} catch (Exception e1) {
				e1.printStackTrace();
				idCustomer = null;
			}
			
			//Tạo điểm cho đối tác 
			Point p = new Point();
			p.setLoginId("HktTest1");
			p.setCustomerId(idCustomer);
			p.setPoint(0);
			try {
				p = CustomerModelManager.getInstance().savePoint(p);
				idPointTest = p.getId();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//Tạo dữ liệu mẫu test chuyển trang
	@Override
	public void createDataTest() {
		for(int i = 4; i <= 50; i++){
			PointTransaction ptTest = new PointTransaction();
			ptTest.setLoginId("HktTest1");
			ptTest.setPointId(idPointTest);
			ptTest.setPoint(10+i);
			ptTest.setDateExecute(new Date());
			try {
				CustomerModelManager.getInstance().savePointTransaction(ptTest);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//Xóa tất cả dữ liệu mẫu tự tạo 
	@Override
	public void deleteDataTest() {
		if (!PanelTestAll.runAll) {
			DialogTest.getInstance().show("Xóa toàn bộ dữ liệu test ?");
			if (DialogTest.getInstance().isTest()) {
				try {
					AccountModelManager.getInstance().deleteTest("HktTest");
					CustomerModelManager.getInstance().deleteTest("HktTest");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
