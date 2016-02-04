package com.hkt.client.swingexp.app.khuvucbanhang.list;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import com.hkt.client.swingexp.app.component.ExtendJButton;
import com.hkt.client.swingexp.app.component.ExtendJCheckBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.khuvucbanhang.PanelProject;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.module.partner.customer.entity.Customer;

@SuppressWarnings("serial")
public class ContractSuplySoftware extends JPanel implements IDialogResto{
	   private javax.swing.JCheckBox chbBanhang;
	    private javax.swing.JCheckBox chbCafe;
	    private javax.swing.JCheckBox chbChucnangle;
	    private javax.swing.JCheckBox chbCo1;
	    private javax.swing.JCheckBox chbCo2;
	    private javax.swing.JCheckBox chbCo3;
	    private javax.swing.JCheckBox chbCo4;
	    private javax.swing.JCheckBox chbCo5;
	    private javax.swing.JCheckBox chbCo6;
	    private javax.swing.JCheckBox chbCo7;
	    private javax.swing.JCheckBox chbCo8;
	    private javax.swing.JCheckBox chbCo9;
	    private javax.swing.JCheckBox chbCoban;
	    private javax.swing.JCheckBox chbCobanNC;
	    private javax.swing.JCheckBox chbKara;
	    private javax.swing.JCheckBox chbKo2;
	    private javax.swing.JCheckBox chbKo3;
	    private javax.swing.JCheckBox chbKo4;
	    private javax.swing.JCheckBox chbKo5;
	    private javax.swing.JCheckBox chbKo6;
	    private javax.swing.JCheckBox chbKo7;
	    private javax.swing.JCheckBox chbNangcao;
	    private javax.swing.JCheckBox chbNangcao2;
	    private javax.swing.JCheckBox chbResto;
	    private javax.swing.JCheckBox chbSmartket;
	    private javax.swing.JCheckBox chbSoft;
	    private javax.swing.JScrollPane jScrollPane1;
	    private javax.swing.JTable table;
	    private javax.swing.JLabel lb1;
	    private javax.swing.JLabel lb2;
	    private javax.swing.JLabel lb3;
	    private javax.swing.JLabel lb4;
	    private javax.swing.JLabel lb5;
	    private javax.swing.JLabel lb6;
	    private javax.swing.JLabel lb7;
	    private javax.swing.JLabel lb8;
	    private javax.swing.JLabel lb9;
	    private javax.swing.JLabel lbAddressA;
	    private javax.swing.JLabel lbAddressB;
	    private javax.swing.JLabel lbBenA;
	    private javax.swing.JLabel lbBenB;
	    private javax.swing.JLabel lbChucnang;
	    private javax.swing.JLabel lbDaiDienA;
	    private javax.swing.JLabel lbDaiDienB;
	    private javax.swing.JLabel lbFaxA;
	    private javax.swing.JLabel lbFaxB;
	    private javax.swing.JLabel lbGTHopdong;
	    private javax.swing.JLabel lbMSTA;
	    private javax.swing.JLabel lbMSTB;
	    private javax.swing.JLabel lbPhanmem;
	    private javax.swing.JLabel lbPhienban;
	    private javax.swing.JLabel lbPhoneA;
	    private javax.swing.JLabel lbPhoneB;
	    private javax.swing.JLabel lbPositionA;
	    private javax.swing.JLabel lbPositionB;
	    private javax.swing.JLabel lbThoihanTT, lbTongcongChu, lbTongcongSo;
	    private javax.swing.JTextField txtAddessA;
	    private javax.swing.JTextField txtAddessB;
	    private javax.swing.JTextField txtBenA;
	    private javax.swing.JTextField txtBenB;
	    private javax.swing.JTextField txtDaiDienA;
	    private javax.swing.JTextField txtDaiDienB;
	    private javax.swing.JTextField txtFaxA;
	    private javax.swing.JTextField txtFaxB;
	    private javax.swing.JTextField txtMSTA;
	    private javax.swing.JTextField txtMSTB;
	    private javax.swing.JTextField txtPhoneA;
	    private javax.swing.JTextField txtPhoneB;
	    private javax.swing.JTextField txtPositionA;
	    private javax.swing.JTextField txtPositionB;
	    private javax.swing.JTextField txtThoihanTT, txtTongcongChu, txtTongcongSo;
	    private JButton btnPrint;
	    private String benA1, A1, ten1, benA2, A2, ten2, diachiA1, dcA1, diachi1, diachiA2, dcA2, diachi2, tel, fax, mst, daidien, diachiB1, dcB1, diachikhachhang1, diachiB2, dcB2, diachikhachhang2, benB1, B1, khachhang1, benB2, B2, khachhang2, telkhachhang, faxkhachhang, mstkhachhang, daidienkhachhang, cmtnd, email,
	    				resto0, cafe0,soft0, market0, kara0, coban0, cobancong0, nangcao0, nangcaocong0, chucnangle0, sale0,
	    				_1co0, _1ko0, _2co0, _2ko0, _3co0, _3ko0, _4co0, _4ko0, _5co0, _5ko0, _6co0, _6ko0, _7co0, _8co0, _9co0,
	    				sl1, dg1, gg1, tt1, sl2, dg2, gg2, tt2, sl3, dg3, gg3, tt3, sl4, sl5, dg4, gg4, tt4, tcso, tcchu, lantt;
	    private Customer c;
    
	public ContractSuplySoftware() {
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
//	  	cmtnd = AccountModelManager.getInstance().getRepresentativeByLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
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
	  	      String diachikhachhang = AccountModelManager.getInstance().getAddressByLoginId(c.getLoginId());
	  	      if(diachikhachhang.length() <= 62){
	  	    	diachikhachhang1 = diachikhachhang;
	  	    	diachikhachhang2 = "";
	  	      } else {
	  	    	diachikhachhang2 = diachikhachhang;
	  	    	diachikhachhang1 = ""; 
	  	      }
	  	      telkhachhang = AccountModelManager.getInstance().getPhoneByLoginId(c.getLoginId());
	  	      faxkhachhang = AccountModelManager.getInstance().getFaxByLoginId(c.getLoginId());
	  	      mstkhachhang = AccountModelManager.getInstance().getRegistrationCodeByLoginId(c.getLoginId());
	  	      daidienkhachhang = AccountModelManager.getInstance().getRepresentativeByLoginId(c.getLoginId());
//	  	      cmtnd = AccountModelManager.getInstance().getPersonalIDByLoginId(c.getLoginId());
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
        lbBenB = new ExtendJLabel();
        txtBenA = new ExtendJTextField();
        txtBenB = new ExtendJTextField();
        lbAddressA = new ExtendJLabel();
        lbAddressB = new ExtendJLabel();
        txtAddessA = new ExtendJTextField();
        txtAddessB = new ExtendJTextField();
        lbPhoneA = new ExtendJLabel();
        lbMSTA = new ExtendJLabel();
        lbDaiDienA = new ExtendJLabel();
        lbFaxA = new ExtendJLabel();
        lbPositionA = new ExtendJLabel();
        txtPhoneA = new ExtendJTextField();
        txtMSTA = new ExtendJTextField();
        txtFaxA = new ExtendJTextField();
        txtDaiDienA = new ExtendJTextField();
        txtPositionA = new ExtendJTextField();
        lbPhoneB = new ExtendJLabel();
        txtFaxB = new ExtendJTextField();
        lbFaxB = new ExtendJLabel();
        txtPhoneB = new ExtendJTextField();
        txtMSTB = new ExtendJTextField();
        lbMSTB = new ExtendJLabel();
        lbDaiDienB = new ExtendJLabel();
        txtDaiDienB = new ExtendJTextField();
        txtPositionB = new ExtendJTextField();
        lbPositionB = new ExtendJLabel();
        chbSoft = new ExtendJCheckBox("");
        chbCafe = new ExtendJCheckBox("");
        chbResto = new ExtendJCheckBox("");
        chbSmartket = new ExtendJCheckBox("");
        chbKara = new ExtendJCheckBox("");
        chbCoban = new ExtendJCheckBox("");
        chbCobanNC = new ExtendJCheckBox("");
        chbNangcao = new ExtendJCheckBox("");
        chbBanhang = new ExtendJCheckBox("");
        chbChucnangle = new ExtendJCheckBox("");
        chbNangcao2 = new ExtendJCheckBox("");
        lb1 = new ExtendJLabel();
        lb2 = new ExtendJLabel();
        lb3 = new ExtendJLabel();
        lb4 = new ExtendJLabel();
        lb5 = new ExtendJLabel();
        lb6 = new ExtendJLabel();
        lb7 = new ExtendJLabel();
        lb8 = new ExtendJLabel();
        lbTongcongChu = new ExtendJLabel();
        lbTongcongSo = new ExtendJLabel();
        lbGTHopdong = new ExtendJLabel();
        chbCo1 = new ExtendJCheckBox("");
        chbCo4 = new ExtendJCheckBox("");
        chbCo7 = new ExtendJCheckBox("");
        chbCo5 = new ExtendJCheckBox("");
        chbCo2 = new ExtendJCheckBox("");
        chbCo8 = new ExtendJCheckBox("");
        chbCo3 = new ExtendJCheckBox("");
        chbCo6 = new ExtendJCheckBox("");
        chbKo2 = new ExtendJCheckBox("");
        chbKo3 = new ExtendJCheckBox("");
        chbKo4 = new ExtendJCheckBox("");
        chbKo5 = new ExtendJCheckBox("");
        chbKo6 = new ExtendJCheckBox("");
        chbKo7 = new ExtendJCheckBox("");
        chbCo9 = new ExtendJCheckBox("");
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new JTable();
        lb9 = new ExtendJLabel();
        txtThoihanTT = new ExtendJTextField();
        txtTongcongChu = new ExtendJTextField();
        txtTongcongSo = new ExtendJTextField();
        lbChucnang = new ExtendJLabel();
        lbPhienban = new ExtendJLabel();
        lbPhanmem = new ExtendJLabel();
        lbThoihanTT = new ExtendJLabel();

        lbBenA.setText("Bên A");

        lbBenB.setText("Bên B");


        lbAddressA.setText("Địa chỉ");

        lbAddressB.setText("Địa chỉ");


        lbPhoneA.setText("Điện thoại");

        lbMSTA.setText("Mã số thuế");

        lbDaiDienA.setText("Đại diện");

        lbFaxA.setText("Fax");

        lbPositionA.setText("Chức vụ");

        lbPhoneB.setText("Điện thoại");


        lbFaxB.setText("Fax");


        lbMSTB.setText("Mã số thuế");

        lbDaiDienB.setText("Đại diện");


        lbPositionB.setText("Chức vụ");

        chbSoft.setText("HKT SOFT");

        chbCafe.setText("HKT CAFE");

        chbResto.setText("HKT RESTO");

        chbSmartket.setText("HKT SMARKET");

        chbKara.setText("HKT KARA");

        chbCoban.setText("Cơ bản (Basic)");

        chbCobanNC.setText("Cơ bản + (Basic +)");

        chbNangcao.setText("Nâng cao (Advance)");

        chbBanhang.setText("Bán hàng (Sale)");

        chbChucnangle.setText("Chức năng lẻ");

        chbNangcao2.setText("Nâng cao + (Advance +)");

        lb1.setText("Các chức năng cơ bản (thiết lập danh mục, giá...):");

        lb2.setText("Bán hàng (màn hình & nghiệp vụ bán hàng)        :");

        lb3.setText("Báo cáo hóa đơn (sản phẩm, mua bán hàng)       :");

        lb4.setText("Báo cáo TK quản lý (thu, chi, lãi, công nợ)            :");

        lb5.setText("Quản lý kho (xuất nhập kho, định lượng)             :");

        lb6.setText("Khuyến mãi (khu vực, sản phẩm, đối tác)             :");

        lb7.setText("Khách hàng thân thiết (tích điểm thưởng)           :");
 
        lb8.setText("Phân quyền (quản lý, nhân viên)                           :");
 
        lbGTHopdong.setText("Giá trị hợp đồng:");
        
        lbTongcongChu.setText("Tổng cộng bằng chữ:");
        lbTongcongSo.setText("Tổng cộng bằng số:");

        chbCo1.setText("Có");

        chbCo4.setText("Có");

        chbCo7.setText("Có");

        chbCo5.setText("Có");

        chbCo2.setText("Có");

        chbCo8.setText("Có");

        chbCo3.setText("Có");

        chbCo6.setText("Có");

        chbKo2.setText("Không");

        chbKo3.setText("Không");

        chbKo4.setText("Không");

        chbKo5.setText("Không");

        chbKo6.setText("Không");

        chbKo7.setText("Không");

        chbCo9.setText("Có, tại cửa hàng, email, điện thoại và Teamviewer");
        
        chbCo2.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(chbCo2.isSelected()){
					chbKo2.setEnabled(false);
				} else {
					chbKo2.setEnabled(true);
				}
				
			}
		});
        
        chbCo3.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(chbCo3.isSelected()){
					chbKo3.setEnabled(false);
				} else {
					chbKo3.setEnabled(true);
				}
				
			}
		});
		
        chbCo4.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(chbCo4.isSelected()){
					chbKo4.setEnabled(false);
				} else {
					chbKo4.setEnabled(true);
				}
				
			}
		});
        
        chbCo5.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(chbCo5.isSelected()){
					chbKo5.setEnabled(false);
				} else {
					chbKo5.setEnabled(true);
				}
				
			}
		});
        
        chbCo6.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(chbCo6.isSelected()){
					chbKo6.setEnabled(false);
				} else {
					chbKo6.setEnabled(true);
				}
				
			}
		});
        
        chbCo7.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(chbCo7.isSelected()){
					chbKo7.setEnabled(false);
				} else {
					chbKo7.setEnabled(true);
				}
				
			}
		});
        
        chbKo2.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(chbKo2.isSelected()){
					chbCo2.setEnabled(false);
				} else {
					chbCo2.setEnabled(true);
				}
				
			}
		});
        
        chbKo3.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(chbKo3.isSelected()){
					chbCo3.setEnabled(false);
				} else {
					chbCo3.setEnabled(true);
				}
				
			}
		});

		chbKo4.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (chbKo4.isSelected()) {
					chbCo4.setEnabled(false);
				} else {
					chbCo4.setEnabled(true);
				}

			}
		});

		chbKo5.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (chbKo5.isSelected()) {
					chbCo5.setEnabled(false);
				} else {
					chbCo5.setEnabled(true);
				}

			}
		});

		chbKo6.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (chbKo6.isSelected()) {
					chbCo6.setEnabled(false);
				} else {
					chbCo6.setEnabled(true);
				}

			}
		});

		chbKo7.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (chbKo7.isSelected()) {
					chbCo7.setEnabled(false);
				} else {
					chbCo7.setEnabled(true);
				}

			}
		});

		DefaultTableModel tbmodal = new DefaultTableModel(
	            new Object [][] {null, null, null, null, null
	            				},
	            new String [] {
	                "<html><center>Sản phẩm & Dịch vụ</center></html>", "<html><center>Số<br>lượng", "<html>Đơn giá<br><center><b><font face= Tahoma>(VND)</font></b></center></html>", "<html>Giảm giá<br><center><b><font face= Tahoma>(%)</font></b></center></br></html>", "<html>Thành tiền<br><center><b><font face= Tahoma>(VND)</font></b></center></html> "
	            }){

            @Override
            public boolean isCellEditable(int row, int column) {
            	if (row == 2 && (column == 2 || column == 3 || column == 4))
               return false;
            	else 
            		return true;
            }
		};

        table.setModel(tbmodal);

        
        table.setValueAt(new String("<html>1. Phần mềm quản lý HKT cung<br>cấp theo điều 1 (máy chủ)</html>"), 0, 0);
        table.setValueAt(new String("<html>2. Phần mềm quản lý HKT cung<br>cấp theo điều 1 (máy trạm)</html>"), 1, 0);
        table.setValueAt(new String("3. Dịch vụ bảo trì miễn phí"), 2, 0);
        table.setValueAt(new String("4. Dịch vụ đào tạo tư vấn"), 3, 0);
        table.setValueAt(new String("<html>5. Chi phí khác (đi lại, ăn ở..."), 4, 0);
        table.setValueAt(new String("0"), 2, 2);
        table.setValueAt(new String("0"), 2, 3);
        table.setValueAt(new String("0"), 2, 4);

        jScrollPane1.getViewport().setBackground(Color.white);
        jScrollPane1.setViewportView(table);
        table.getColumnModel().getColumn(0).setPreferredWidth(190);
        table.getColumnModel().getColumn(1).setPreferredWidth(50);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(60);
        table.getColumnModel().getColumn(4).setPreferredWidth(110);
        
        table.setRowHeight(0, 50);
        table.setRowHeight(1, 50);
        table.setRowHeight(2, 23);
        table.setRowHeight(3, 23);
        table.setRowHeight(4, 23);
        
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

        table.setFont(new Font("Tahoma", 0, 14));
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(60, 40));
        header.setFont(new Font("Tahoma", Font.BOLD, 14));
        
        
        lb9.setText("Cài đặt và Hỗ trợ sử dụng:");

        lbChucnang.setText("Chức năng:");

        lbPhienban.setText("Phiên bản:");

        lbPhanmem.setText("Phần mềm:");

        lbThoihanTT.setText("Thời hạn thanh toán:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(lbBenA)
                .addGap(50, 50, 50)
                .addComponent(txtBenA, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(lbBenB)
                .addGap(50, 50, 50)
                .addComponent(txtBenB, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
            		.addGap(5, 5, 5)
                .addComponent(lbAddressA)
                .addGap(46, 46, 46)
                .addComponent(txtAddessA, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                 .addGap(40, 40, 40)
                .addComponent(lbAddressB)
                .addGap(46, 46, 46)
                .addComponent(txtAddessB, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
                
              .addGroup(layout.createSequentialGroup()
            		.addGap(5, 5, 5)
                .addComponent(lbPhoneA)
                .addGap(21, 21, 21)
                .addComponent(txtPhoneA, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                 .addGap(33, 33, 33)
                .addComponent(lbFaxA)
                .addGap(42, 42, 42)
                .addComponent(txtFaxA, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(lbPhoneB)
                .addGap(23, 23, 23)
                .addComponent(txtPhoneB, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(lbFaxB)
                .addGap(45, 45, 45)
                .addComponent(txtFaxB, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                
            .addGroup(layout.createSequentialGroup()
            		.addGap(5, 5, 5)
                .addComponent(lbDaiDienA)
                .addGap(36, 36, 36)
                .addComponent(txtDaiDienA, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                 .addGap(32, 32, 32)
                .addComponent(lbPositionA)
                .addGap(10, 10, 10)
                .addComponent(txtPositionA, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(layout.createSequentialGroup()
               		.addGap(5, 5, 5)
                   .addComponent(lbDaiDienB)
                   .addGap(36, 36, 36)
                   .addComponent(txtDaiDienB, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(30, 30, 30)
                   .addComponent(lbPositionB)
                   .addGap(12, 12, 12)
                   .addComponent(txtPositionB, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))

            .addGroup(layout.createSequentialGroup()
            		.addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbMSTA))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMSTA, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))

              .addGap(340, 340, 340)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbMSTB))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMSTB, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    
            .addGroup(layout.createSequentialGroup()
            		.addGap(5, 5, 5)
                    .addComponent(lbPhanmem)
                    .addGap(9, 9, 9)
                    .addComponent(chbResto)
                    .addGap(105, 105, 105)
                    .addComponent(chbCafe)
                    .addGap(100, 100, 100)
                    .addComponent(chbSoft)
                    .addGap(42, 42, 42)
                    .addComponent(lbPhienban)
                    .addGap(12, 12, 12)
                    .addComponent(chbCoban)
                        .addGap(45, 45, 45)
                        .addComponent(chbNangcao)
                        .addGap(35, 35, 35)
                        .addComponent(chbBanhang))
                        
              .addGroup(layout.createSequentialGroup()
            		  .addGap(92, 92, 92)
                     .addComponent(chbSmartket)
                         .addGap(84, 84, 84)
                        .addComponent(chbKara)
                        .addGap(320, 320, 320)
                        .addComponent(chbCobanNC)
                        .addGap(15, 15, 15)
                        .addComponent(chbNangcao2)
                        .addGap(5, 5, 5)
                        .addComponent(chbChucnangle)) 
              .addGroup(layout.createSequentialGroup()
            		.addGap(5, 5, 5)
            	.addComponent(lbChucnang)	
            	.addGap(10, 10, 10)
                        .addComponent(lb1)
                        .addGap(8, 8, 8)
                        .addComponent(chbCo1)
                        .addGap(155, 155, 155)
                        .addComponent(lbGTHopdong))
                   .addGroup(layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(lb2)
                        .addGap(8, 8, 8)
                        .addComponent(chbCo2)
                        .addGap(40, 40, 40)
                        .addComponent(chbKo2)
                        .addGap(45, 45, 45)
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                    		.addGap(95, 95, 95)
                        .addComponent(lb3)
                        .addGap(8, 8, 8)
                        .addComponent(chbCo3)
                         .addGap(40, 40, 40)
                        .addComponent(chbKo3))
                    .addGroup(layout.createSequentialGroup()
                    		.addGap(95, 95, 95)
                        .addComponent(lb4)
                        .addGap(8, 8, 8)
                        .addComponent(chbCo4)
                        .addGap(40, 40, 40)
                        .addComponent(chbKo4))
                    .addGroup(layout.createSequentialGroup()
                    		.addGap(95, 95, 95)
                        .addComponent(lb5)
                       .addGap(8, 8, 8)
                        .addComponent(chbCo5)
                        .addGap(40, 40, 40)
                        .addComponent(chbKo5))
                    .addGroup(layout.createSequentialGroup()
                    		.addGap(95, 95, 95)
                        .addComponent(lb6)
                        .addGap(8, 8, 8)
                        .addComponent(chbCo6)
                        .addGap(40, 40, 40)
                        .addComponent(chbKo6))
                    .addGroup(layout.createSequentialGroup()
                    		.addGap(95, 95, 95)
                        .addComponent(lb7)
                        .addGap(8, 8, 8)
                        .addComponent(chbCo7)
                        .addGap(40, 40, 40)
                        .addComponent(chbKo7))
                        .addGroup(layout.createSequentialGroup()
                    		.addGap(95, 95, 95)
                        .addComponent(lb8)
                        .addGap(8, 8, 8)
                        .addComponent(chbCo8))
                     .addGroup(layout.createSequentialGroup()
                    		.addGap(95, 95, 95)
                        .addComponent(lb9)
                        .addGap(5, 5, 5)
                        .addComponent(chbCo9))
                        .addGroup(layout.createSequentialGroup()
                    		.addGap(700, 700, 700)
                        .addComponent(lbTongcongSo)
                        .addGap(15, 15, 15)
                        .addComponent(txtTongcongSo, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                        		.addGap(700, 700, 700)
                        .addComponent(lbTongcongChu)
                        .addGap(5, 5, 5)
                        .addComponent(txtTongcongChu, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()            
                .addGap(5, 5, 5)
                .addComponent(lbThoihanTT)
                .addGap(15, 15, 15)
                .addComponent(txtThoihanTT, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbBenA))
                    .addComponent(txtBenA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbBenB))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtBenB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbAddressA))
                    .addComponent(txtAddessA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbAddressB))
                        .addComponent(txtAddessB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbPhoneA)
                        .addGap(15, 15, 15)
                        .addComponent(lbMSTA))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtPhoneA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtMSTA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(lbFaxA))
                    .addComponent(txtFaxA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbPhoneB)
                        .addGap(15, 15, 15)
                        .addComponent(lbMSTB))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtPhoneB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtMSTB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                    		.addGap(3, 3, 3)
                        .addComponent(lbFaxB))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtFaxB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbDaiDienA)
                    .addComponent(txtDaiDienA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(lbPositionA))
                    .addComponent(txtPositionA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbDaiDienB))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtDaiDienB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbPositionB))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtPositionB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbPhanmem)
                        .addGap(45, 45, 45)
                        .addComponent(lbChucnang))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chbResto)
                            .addComponent(chbCafe))
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chbSmartket)
                            .addComponent(chbKara))
                        .addGap(5, 5, 5)
                        .addComponent(lb1)
                        .addGap(16, 16, 16)
                        .addComponent(lb2)
                        .addGap(16, 16, 16)
                        .addComponent(lb3)
                        .addGap(16, 16, 16)
                        .addComponent(lb4)
                        .addGap(16, 16, 16)
                        .addComponent(lb5)
                        .addGap(16, 16, 16)
                        .addComponent(lb6)
                        .addGap(16, 16, 16)
                        .addComponent(lb7)
                        .addGap(16, 16, 16)
                        .addComponent(lb8)
                        .addGap(16, 16, 16)
                        .addComponent(lb9))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(chbCo1)
                                .addGap(8, 8, 8)
                                .addComponent(chbCo2)
                                .addGap(8, 8, 8)
                                .addComponent(chbCo3)
                                .addGap(8, 8, 8)
                                .addComponent(chbCo4)	
                                .addGap(8, 8, 8)
                                .addComponent(chbCo5)
                                .addGap(8, 8, 8)
                                .addComponent(chbCo6)
                                .addGap(8, 8, 8)
                                .addComponent(chbCo7)
                                .addGap(8, 8, 8)
                                .addComponent(chbCo8)
                                .addGap(8, 8, 8)
                                .addComponent(chbCo9)
                                
                                )
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(chbSoft)
                        .addGap(67, 67, 67)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chbKo2)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(chbKo3)))
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(chbKo4)
                                .addGroup(layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                            .addComponent(chbKo5)))
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chbKo6)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(chbKo7))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbPhienban)
                            .addComponent(chbCoban)
                            .addComponent(chbNangcao)
                            .addComponent(chbBanhang))
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chbCobanNC)
                            .addComponent(chbNangcao2)
                            .addComponent(chbChucnangle))
                        .addGap(7, 7, 7)
                        .addComponent(lbGTHopdong)
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lbTongcongSo)
                        .addComponent(txtTongcongSo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbThoihanTT)
                    .addComponent(txtThoihanTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbTongcongChu)
                            .addComponent(txtTongcongChu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            ))
                   
            )));
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
		if(txtBenA.getText().toString().length() <= 62){
			khachhang1 = txtBenA.getText();
			khachhang2 = "";
			benA1 = "1";
			A1 = "1";
			benA2 = "";
			A2 = "";
		} else {
			khachhang2 = txtBenA.getText();
			khachhang1 = "";
			benA1 = "";
			A1 = "";
			benA2 = "1";
			A2 = "1";
		}
		if(txtAddessA.getText().toString().length() <= 62){
			diachikhachhang1 = txtAddessA.getText();
			diachikhachhang2 = "";
			diachiA1 = "1";
			dcA1 = "1";
			diachiA2 = "";
			dcA2 = "";
		} else {
			diachikhachhang2 = txtAddessA.getText();
			diachikhachhang1 = "";
			diachiA1 = "";
			dcA1 = "";
			diachiA2 = "1";
			dcA2 = "1";
		}
		telkhachhang = txtPhoneA.getText();
		faxkhachhang = txtFaxA.getText();
		mstkhachhang = txtMSTA.getText();
		daidienkhachhang = txtDaiDienA.getText();
		if(txtBenB.getText().toString().length() <= 62){
			ten1 = txtBenB.getText();
			ten2 = "";
			benB1 = "1";
			benB2 = "";
			B1 = "1";
			B2 = "";
		} else{
			ten2 = txtBenB.getText();
			ten1 = "";
			benB1 = "";
			benB2 = "1";
			B1 = "";
			B2 = "1";
		}
		if(txtAddessB.getText().toString().length() <= 62){
			diachi1 = txtAddessB.getText();
			diachi2 = "";
			diachiB1 = "1";
			dcB1 = "1";
			diachiB2 = "";
			dcB2 = "";
		} else{
			diachi2 = txtAddessB.getText();
			diachi1 = "";
			diachiB1 = "";
			dcB1 = "";
			diachiB2 = "1";
			dcB2 = "1";
		}
		
		tel = txtPhoneB.getText();
		fax = txtFaxB.getText();
		mst = txtMSTB.getText();
		daidien = txtDaiDienB.getText();
		if(chbResto.isSelected()){
			resto0 = "";
		} else {
			resto0 = "1";
		}
		
		if(chbCafe.isSelected()){
			cafe0 = "";
		} else {
			cafe0 = "1";
		}
		
		if(chbSoft.isSelected()){
			soft0 = "";
		} else {
			soft0 = "1";
		}
		
		if(chbSmartket.isSelected()){
			market0 = "";
		} else {
			market0 = "1";
		}
		
		if(chbKara.isSelected()){
			kara0 = "";
		} else {
			kara0 = "1";
		}
		
		if(chbCoban.isSelected()){
			coban0 = "";
		} else {
			coban0 = "1";
		}
		
		if(chbNangcao.isSelected()){
			nangcao0 = "";
		} else {
			nangcao0 = "1";
		}
		
		if(chbBanhang.isSelected()){
			sale0 = "";
		} else {
			sale0 = "1";
		}
		
		if(chbCobanNC.isSelected()){
			cobancong0 = "";
		} else {
			cobancong0 = "1";
		}
		
		if(chbNangcao2.isSelected()){
			nangcaocong0 = "";
		} else {
			nangcaocong0 = "1";
		}
		
		if(chbChucnangle.isSelected()){
			chucnangle0 = "";
		} else {
			chucnangle0 = "1";
		}
		
		if(chbCo1.isSelected()){
			_1co0 = "";
		} else {
			_1co0 = "1";
		}
		
		if(chbCo2.isSelected()){
			_2co0 = "";
		} else {
			_2co0 = "1";
		}
		
		if(chbKo2.isSelected()){
			_1ko0 = "";
		} else {
			_1ko0 = "1";
		}
		if(chbCo3.isSelected()){
			_3co0 = "";
		} else {
			_3co0 = "1";
		}
		
		if(chbKo3.isSelected()){
			_2ko0 = "";
		} else {
			_2ko0 = "1";
		}
		if(chbCo4.isSelected()){
			_4co0 = "";
		} else {
			_4co0 = "1";
		}
		
		if(chbKo4.isSelected()){
			_3ko0 = "";
		} else {
			_3ko0 = "1";
		}
		if(chbCo5.isSelected()){
			_5co0 = "";
		} else {
			_5co0 = "1";
		}
		
		if(chbKo5.isSelected()){
			_4ko0 = "";
		} else {
			_4ko0 = "1";
		}
		if(chbCo6.isSelected()){
			_6co0 = "";
		} else {
			_6co0 = "1";
		}
		
		if(chbKo6.isSelected()){
			_5ko0 = "";
		} else {
			_5ko0 = "1";
		}
		if(chbCo7.isSelected()){
			_7co0 = "";
		} else {
			_7co0 = "1";
		}
		
		if(chbKo7.isSelected()){
			_6ko0 = "";
		} else {
			_6ko0 = "1";
		}
		
		if(chbCo8.isSelected()){
			_8co0 = "";
		} else {
			_8co0 = "1";
		}
		
		if(chbCo9.isSelected()){
			_9co0 = "";
		} else {
			_9co0 = "1";
		}
		
		try {
			sl1 = table.getModel().getValueAt(0, 1).toString();
			sl2 = table.getModel().getValueAt(1, 1).toString();
			sl3 = table.getModel().getValueAt(2, 1).toString();
			sl4 = table.getModel().getValueAt(3, 1).toString();
			
			dg1 = table.getModel().getValueAt(0, 2).toString();
			dg2 = table.getModel().getValueAt(1, 2).toString();
			dg3 = table.getModel().getValueAt(2, 2).toString();
			
			gg1 = table.getModel().getValueAt(0, 3).toString();
			gg2 = table.getModel().getValueAt(1, 3).toString();
			gg3 = table.getModel().getValueAt(2, 3).toString();
			gg4 = table.getModel().getValueAt(3, 3).toString();
			
			tt1 = table.getModel().getValueAt(0, 4).toString();
			tt2 = table.getModel().getValueAt(1, 4).toString();
			tt3 = table.getModel().getValueAt(2, 4).toString();
			tt4 = table.getModel().getValueAt(3, 4).toString();
		} catch (Exception e) {
			
		}
		
		tcso = txtTongcongSo.getText();
		tcchu = txtTongcongChu.getText();

  	    String[] parametersReportProject = new String[] { benA1, A1, khachhang1, benA2, A2, khachhang2, diachiA1, dcA1, diachikhachhang1, diachiA2, dcA2, diachikhachhang2, "", "", "", telkhachhang, faxkhachhang, "", mstkhachhang, "", "", "", daidienkhachhang, 
  	    													benB1, B1, ten1, benB2, B2, ten2, diachiB1, dcB1, diachi1, diachiB2, dcB2, diachi2, tel, fax, mst, daidien,
  	    													resto0, cafe0, soft0, market0, kara0, coban0, nangcao0, sale0, cobancong0, nangcaocong0, chucnangle0,
  	    													_1co0,_2co0, _1ko0, _3co0, _2ko0, _4co0, _3ko0, _5co0, _4ko0, _6co0, _5ko0, _7co0, _6ko0, _8co0, _9co0,
  	    													sl1, dg1, gg1, tt1, sl2, dg2, gg2, tt2, sl3, sl4, dg3, gg3, tt3, sl5, dg4, gg4, tt4, tcso, tcchu, ""  };
  	    report.setParameter(parametersReportProject);
  	    report.printReport();
    }
    
    private void setData(){
    	if(c != null){
    	if (!khachhang1.equals("")){
    		txtBenA.setText(khachhang1);
    	} else {
    		txtBenA.setText(khachhang2);
    	}
    	if (!diachikhachhang1.equals("")){
    		txtAddessA.setText(diachikhachhang1);
    	} else {
    		txtAddessA.setText(diachikhachhang2);
    	}
		txtPhoneA.setText(telkhachhang);
		txtFaxA.setText(faxkhachhang);
		txtMSTA.setText(mstkhachhang);
		txtDaiDienA.setText(daidienkhachhang);
    	}
		if(!ten1.equals("")){
			txtBenB.setText(ten1);
		} else {
			txtBenB.setText(ten2);
		}
		if(!diachi1.equals("")){
			txtAddessB.setText(diachi1);
		} else {
			txtAddessB.setText(diachi2);
		}
		txtPhoneB.setText(tel);
		txtFaxB.setText(fax);
		txtMSTB.setText(mst);
		txtDaiDienB.setText(daidien);   
    }
    
	
	@Override
	public void Save() throws Exception {

	}

}
