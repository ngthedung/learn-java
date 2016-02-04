package com.hkt.client.swingexp.app.khuvucbanhang.list;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.component.ExtendJButton;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.khuvucbanhang.PanelProject;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.module.partner.customer.entity.Customer;

public class ContractLease extends JPanel implements IDialogResto{
	private ExtendJLabel lbAddressA;
    private ExtendJLabel lbAddressB;
    private ExtendJLabel lbBangChu;
    private ExtendJLabel lbBenA;
    private ExtendJLabel lbBenB;
    private ExtendJLabel lbCMTND;
    private ExtendJLabel lbChiNhanh;
    private ExtendJLabel lbDaiDienA;
    private ExtendJLabel lbFaxA;
    private ExtendJLabel lbFaxB;
    private ExtendJLabel lbMSTA;
    private ExtendJLabel lbMSTB;
    private ExtendJLabel lbNganHang;
    private ExtendJLabel lbNgayCap;
    private ExtendJLabel lbNoiCap;
    private ExtendJLabel lbPhoneA;
    private ExtendJLabel lbPhoneB;
    private ExtendJLabel lbPositionA;
    private ExtendJLabel lbSoTK;
    private ExtendJLabel lbTenCD;
    private ExtendJLabel lbThoiHan;
    private ExtendJLabel lbTienTT;
    private ExtendJLabel lbDodai;
    private ExtendJTextField txtAddessA;
    private ExtendJTextField txtAddessB;
    private ExtendJTextField txtBangChu;
    private ExtendJTextField txtBenA;
    private ExtendJTextField txtBenB;
    private ExtendJTextField txtCMTND;
    private ExtendJTextField txtChiNhanh;
    private ExtendJTextField txtDaiDienA;
    private ExtendJTextField txtFaxA;
    private ExtendJTextField txtFaxB;
    private ExtendJTextField txtMSTA;
    private ExtendJTextField txtMSTB;
    private ExtendJTextField txtNganHang;
    private MyJDateChooser dcNgayCap;
    private ExtendJTextField txtNoiCap;
    private ExtendJTextField txtPhoneA;
    private ExtendJTextField txtPhoneB;
    private ExtendJTextField txtPositionA;
    private ExtendJTextField txtSoTK;
    private ExtendJTextField txtTenCD;
    private MyJDateChooser dcThoiHan;
    private ExtendJTextField txtTienTT;
    private ExtendJTextField txtDodai;
    private JButton btnPrint;
    private String benA1, A1, ten1, benA2, A2, ten2, diachiA1, dcA1, diachi1, diachiA2, dcA2, diachi2, tel, cmtnd, fax, mst, daidien, diachiB1, dcB1, diachikhachhang1, diachiB2, dcB2, diachikhachhang2, benB1, B1, khachhang1, benB2, B2, khachhang2,
    				cmtndkh, telkhachhang, faxkhachhang, mstkhachhang, daidienkhachhang, email, tenCD, tienTL, tienbangchu, dodai;
    private Date thoihan;
    private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    private Customer c;
    
	public ContractLease() {
		initComponents();
		String ten = AccountModelManager.getInstance().getNameByLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
		if(ten.length() <= 62){
			ten1 = ten;
			ten2 = "";
		} else {
			ten2 = ten;
			ten1 = "";
		}
		String diachi = AccountModelManager.getInstance().getAddressByLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
		if(diachi.length() <= 62){
			diachi1 = diachi;
			diachi2 = ""; 
		} else{
			diachi2 = diachi;
			diachi1 = ""; 
		}
	  	
	  	tel = AccountModelManager.getInstance().getPhoneByLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
	  	fax = AccountModelManager.getInstance().getFaxByLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
	  	mst = AccountModelManager.getInstance().getRegistrationCodeByLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
	  	cmtnd = AccountModelManager.getInstance().getRepresentativeByLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
	  	try {
	  	      c = PanelProject.name;
	  	      if (c != null){
	  	      if(c.toString().length() <= 62){
	  	    	khachhang1 = c.toString();
	  	    	khachhang2 = "";
	  	      } else {
	  	    	khachhang2 = c.toString();
	  	    	khachhang1 = "";
	  	      }
	  	      String diachikhachhang = c.getAddress();

	  	      if(diachikhachhang.length() <= 62){
	  	    	diachikhachhang1 = diachikhachhang;
	  	    	diachikhachhang2 = "";
	  	      } else {
	  	    	diachikhachhang2 = diachikhachhang;
	  	    	diachikhachhang1 = ""; 
	  	      }
	  	      telkhachhang = c.getMobile();
	  	      faxkhachhang = AccountModelManager.getInstance().getFaxByLoginId(c.getLoginId());
	  	      mstkhachhang = AccountModelManager.getInstance().getRegistrationCodeByLoginId(c.getLoginId());
	  	      daidienkhachhang = AccountModelManager.getInstance().getRepresentativeByLoginId(c.getLoginId());
	  	      cmtndkh = AccountModelManager.getInstance().getPersonalIDByLoginId(c.getLoginId());
	  	      }
	  	    } catch (Exception e) {
	  	    	e.printStackTrace();
	  	    } 
		setData();
	}
	
    private void initComponents() {
    	setOpaque(false);

    	btnPrint = new ExtendJButton("In");
        lbBenA = new ExtendJLabel();
        txtPositionA = new ExtendJTextField();
        txtDaiDienA = new ExtendJTextField();
        txtFaxA = new ExtendJTextField();
        lbPositionA = new ExtendJLabel();
        txtMSTA = new ExtendJTextField();
        txtPhoneA = new ExtendJTextField();
        lbFaxA = new ExtendJLabel();
        lbDaiDienA = new ExtendJLabel();
        lbMSTA = new ExtendJLabel();
        lbPhoneA = new ExtendJLabel();
        txtAddessA = new ExtendJTextField();
        lbAddressA = new ExtendJLabel();
        txtBenA = new ExtendJTextField();
        lbMSTB = new ExtendJLabel();
        txtMSTB = new ExtendJTextField();
        txtPhoneB = new ExtendJTextField();
        lbFaxB = new ExtendJLabel();
        txtFaxB = new ExtendJTextField();
        lbPhoneB = new ExtendJLabel();
        txtAddessB = new ExtendJTextField();
        lbAddressB = new ExtendJLabel();
        txtBenB = new ExtendJTextField();
        lbBenB = new ExtendJLabel();
        lbCMTND = new ExtendJLabel();
        txtCMTND = new ExtendJTextField();
        lbNoiCap = new ExtendJLabel();
        txtNoiCap = new ExtendJTextField();
        lbSoTK = new ExtendJLabel();
        txtSoTK = new ExtendJTextField();
        lbNganHang = new ExtendJLabel();
        lbChiNhanh = new ExtendJLabel();
        lbNgayCap = new ExtendJLabel();
        txtNganHang = new ExtendJTextField();
        txtChiNhanh = new ExtendJTextField();
        dcNgayCap = new MyJDateChooser();
        lbTenCD = new ExtendJLabel();
        txtTenCD = new ExtendJTextField();
        lbTienTT = new ExtendJLabel();
        txtTienTT = new ExtendJTextField();
        lbBangChu = new ExtendJLabel();
        txtBangChu = new ExtendJTextField();
        lbThoiHan = new ExtendJLabel();
        dcThoiHan = new MyJDateChooser();
        lbDodai = new ExtendJLabel("Độ dài");
        txtDodai = new ExtendJTextField();
        ((JTextField) dcThoiHan.getDateEditor()).setHorizontalAlignment(JTextField.RIGHT);
        dcThoiHan.setDate(new Date());

        lbBenA.setText("Bên A");

        lbPositionA.setText("Chức vụ");

        lbFaxA.setText("Fax");

        lbDaiDienA.setText("Đại diện");

        lbMSTA.setText("Mã số thuế");

        lbPhoneA.setText("Điện thoại");

        lbAddressA.setText("Địa chỉ");

        lbMSTB.setText("Mã số thuế");
        
        lbFaxB.setText("Fax");

        lbPhoneB.setText("Điện thoại");

        lbAddressB.setText("Địa chỉ");

        lbBenB.setText("Bên B");

        lbCMTND.setText("Số CMTND");

        lbNoiCap.setText("Nơi cấp");

        lbSoTK.setText("Tài khoản số");

        lbNganHang.setText("Ngân hàng");

        lbChiNhanh.setText("Chi nhánh");

        lbNgayCap.setText("Ngày cấp");

        lbTenCD.setText("Tên chuyên đề");

        lbTienTT.setText("Tiền thanh toán");

        lbBangChu.setText("Bằng chữ");

        lbThoiHan.setText("Thời hạn");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lbBenB)
                        .addGap(80, 80, 80)
                        .addComponent(txtBenB, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(lbBenA)
                        .addGap(80, 80, 80)
                        .addComponent(txtBenA, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lbAddressB)
                        .addGap(76, 76, 76)
                        .addComponent(txtAddessB, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(lbAddressA)
                        .addGap(75, 75, 75)
                        .addComponent(txtAddessA, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lbCMTND)
                        .addGap(47, 47, 47)
                        .addComponent(txtCMTND, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(lbNgayCap)
                        .addGap(15, 15, 15)
                        .addComponent(dcNgayCap, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(lbPhoneA)
                        .addGap(51, 51, 51)
                        .addComponent(txtPhoneA, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(lbFaxA)
                        .addGap(55, 55, 55)
                        .addComponent(txtFaxA, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lbNoiCap)
                        .addGap(70, 70, 70)
                        .addComponent(txtNoiCap, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(lbMSTA)
                        .addGap(45, 45, 45)
                        .addComponent(txtMSTA, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lbPhoneB)
                        .addGap(51, 51, 51)
                        .addComponent(txtPhoneB, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(lbFaxB)
                        .addGap(55, 55, 55)
                        .addComponent(txtFaxB, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(lbDaiDienA)
                        .addGap(66, 66, 66)
                        .addComponent(txtDaiDienA, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(lbPositionA)
                        .addGap(20, 20, 20)
                        .addComponent(txtPositionA, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lbMSTB)
                        .addGap(45, 45, 45)
                        .addComponent(txtMSTB, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(348, 348, 348)
                        .addComponent(lbTienTT)
                        .addGap(12, 12, 12)
                        .addComponent(txtTienTT, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lbSoTK)
                        .addGap(33, 33, 33)
                        .addComponent(txtSoTK, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72)
                        .addComponent(lbBangChu)
                        .addGap(52, 52, 52)
                        .addComponent(txtBangChu, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lbNganHang)
                        .addGap(43, 43, 43)
                        .addComponent(txtNganHang, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73)
                        .addComponent(lbDodai)
                        .addGap(74, 74, 74)
                        .addComponent(txtDodai, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lbChiNhanh)
                        .addGap(48, 48, 48)
                        .addComponent(txtChiNhanh, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lbTenCD)
                        .addGap(17, 17, 17)
                        .addComponent(txtTenCD, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lbThoiHan)
                        .addGap(57, 57, 57)
                        .addComponent(dcThoiHan, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lbBenB))
                    .addComponent(txtBenB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbBenA)
                    .addComponent(txtBenA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbAddressB)
                    .addComponent(txtAddessB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbAddressA)
                    .addComponent(txtAddessA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbCMTND)
                    .addComponent(txtCMTND, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbNgayCap)
                    .addComponent(dcNgayCap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbPhoneA)
                    .addComponent(txtPhoneA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbFaxA)
                    .addComponent(txtFaxA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbNoiCap)
                    .addComponent(txtNoiCap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbMSTA)
                    .addComponent(txtMSTA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lbPhoneB))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(txtPhoneB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lbFaxB))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(txtFaxB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lbDaiDienA))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(txtDaiDienA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lbPositionA))
                    .addComponent(txtPositionA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbMSTB)
                    .addComponent(txtMSTB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTienTT)
                    .addComponent(txtTienTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbSoTK)
                    .addComponent(txtSoTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbBangChu)
                    .addComponent(txtBangChu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbNganHang)
                    .addComponent(txtNganHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbDodai)
                    .addComponent(txtDodai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbChiNhanh)
                    .addComponent(txtChiNhanh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbTenCD)
                    .addComponent(txtTenCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbThoiHan)
                    .addComponent(dcThoiHan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );
    }
    
    public JButton getBtnPrint() {
    	  btnPrint.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/print1.png")));
    	  btnPrint.addActionListener(new ActionListener() {
    	        @Override
    	        public void actionPerformed(ActionEvent e) {
    	          try {
    	        	printContract();

    	          } catch (Exception e2) {
    	            e2.printStackTrace();
    	          }
    	        }
    	      });
    	  return btnPrint;  
      }
    
    private void printContract(){
    	ReportProject report = new ReportProject();
		if(txtBenB.getText().toString().length() <= 62){
			khachhang1 = txtBenB.getText();
			khachhang2 = "";
			benB1 = "1";
			B1 = "1";
			benB2 = "";
			B2 = "";
		} else {
			khachhang2 = txtBenB.getText();
			khachhang1 = "";
			benB1 = "";
			B1 = "";
			benB2 = "1";
			B2 = "1";
		}
		if(txtAddessA.getText().toString().length() <= 62){
			diachikhachhang1 = txtAddessB.getText();
			diachikhachhang2 = "";
			diachiB1 = "1";
			dcB1 = "1";
			diachiB2 = "";
			dcB2 = "";
		} else {
			diachikhachhang2 = txtAddessB.getText();
			diachikhachhang1 = "";
			diachiB1 = "";
			dcB1 = "";
			diachiB2 = "1";
			dcB2 = "1";
		}
		telkhachhang = txtPhoneB.getText();
		faxkhachhang = txtFaxB.getText();
		mstkhachhang = txtMSTB.getText();

		if(txtBenA.getText().toString().length() <= 62){
			ten1 = txtBenA.getText();
			ten2 = "";
			benA1 = "1";
			benA2 = "";
			A1 = "1";
			A2 = "";
		} else{
			ten2 = txtBenA.getText();
			ten1 = "";
			benA1 = "";
			benA2 = "1";
			A1 = "";
			A2 = "1";
		}
		if(txtAddessA.getText().toString().length() <= 62){
			diachi1 = txtAddessA.getText();
			diachi2 = "";
			diachiA1 = "1";
			dcA1 = "1";
			diachiA2 = "";
			dcA2 = "";
		} else{
			diachi2 = txtAddessA.getText();
			diachi1 = "";
			diachiA1 = "";
			dcA1 = "";
			diachiA2 = "1";
			dcA2 = "1";
		}
		
		tel = txtPhoneA.getText();
		fax = txtFaxA.getText();
		mst = txtMSTA.getText();
		tenCD = txtTenCD.getText();
		thoihan = dcThoiHan.getDate();
		String strthoihan = df.format(thoihan);
		tienTL = txtTienTT.getText();
		tienbangchu = txtBangChu.getText();
		dodai = txtDodai.getText();
		
		String[] parametersReportProject = new String[] { benA1, A1, khachhang1, benA2, A2, khachhang2, diachiA1, dcA1, diachikhachhang1, diachiA2, dcA2, diachikhachhang2, "", "", "", telkhachhang, faxkhachhang, "", mstkhachhang, "", "", "", daidienkhachhang, 
					benB1, B1, ten1, benB2, B2, ten2, diachiB1, dcB1, diachi1, diachiB2, dcB2, diachi2, tel, fax, mst, daidien,
					"", "", "", "", "", "", "", "", "", "", "",
					"","", "", "", "", "", "", "", "", "", "", "", "", "", "",
					"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", tenCD, strthoihan, tienTL, tienbangchu, dodai  };
		report.setParameter(parametersReportProject);
		report.printReport();
		
    }
	
    private void setData(){
    	if(c != null){
    	if (!khachhang1.equals("")){
    		txtBenB.setText(khachhang1);
    	} else {
    		txtBenB.setText(khachhang2);
    	}
    	if (!diachikhachhang1.equals("")){
    		txtAddessB.setText(diachikhachhang1);
    	} else {
    		txtAddessB.setText(diachikhachhang2);
    	}
		txtPhoneB.setText(telkhachhang);
		txtFaxB.setText(faxkhachhang);
		txtMSTB.setText(mstkhachhang);
//		txtDaiDienA.setText(daidienkhachhang);
    	}
		if(!ten1.equals("")){
			txtBenA.setText(ten1);
		} else {
			txtBenA.setText(ten2);
		}
		if(!diachi1.equals("")){
			txtAddessA.setText(diachi1);
		} else {
			txtAddessA.setText(diachi2);
		}
		txtPhoneA.setText(tel);
		txtFaxA.setText(fax);
		txtMSTA.setText(mst);

    }
    
	@Override
	public void Save() throws Exception {
		
		
	}

}
