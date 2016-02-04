package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DateEditor;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.SwingConstants;

import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.GroupLayoutPanel;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.component.TextPopup;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.hr.entity.Appointment;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.hr.entity.Appointment.Status;
import com.hkt.module.partner.customer.entity.Customer;

public class PanelScheduleWork extends JPanel {
    private ExtendJComboBox cbStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private TextPopup<Employee> txtEmployee;
    private javax.swing.JTextArea txtghichu;
    private javax.swing.JSpinner jsGio;
    private MyJDateChooser dcngay;
    private javax.swing.JTextField txtsldat;
    private GroupLayoutPanel layoutPanel;
    private InvoiceDetail detail;
    private int ngay;
    private int slTong;
    private int gio, phut;
    private int sldat = 1;
    private String[]  listTrangThai = { "Chưa làm","Hoàn thành" };
    public PanelScheduleWork(InvoiceDetail detail, int ngay, int slTong) {
    	this.detail = detail;
    	this.ngay = ngay;
    	this.slTong = slTong;
    	initCompoment();
	}

	public GroupLayoutPanel getLayoutPanel() {
		return layoutPanel;
	}

	private void initCompoment() {
		layoutPanel = new GroupLayoutPanel();
		layoutPanel.removeAll();
		layoutPanel.loadLayout();
		for(int i = 0; i<slTong; i++)
		{
			layoutPanel.setOpaque(false);
		 	jPanel1 = new javax.swing.JPanel();
		 	jPanel1.setOpaque(false);
		 	jPanel1.setSize(new Dimension(510, 160));
		 	jPanel1.setPreferredSize(new Dimension(510,160));
		 	jPanel1.setLayout(new GridLayout(slTong, 1));
	        jLabel1 = new javax.swing.JLabel();
	        dcngay = new MyJDateChooser();
	        dcngay.setDate(detail.getStartDate());
	        if(i>0)
	        {
	        	
	        }
	        jLabel2 = new javax.swing.JLabel();
	        jsGio = new javax.swing.JSpinner();
	        jsGio.setFont(new java.awt.Font("Tahoma", 0, 14));
	        try {
				dcngay.setDate(detail.getStartDate());
	    	 	SpinnerModel smGio = new SpinnerDateModel();
	    	    jsGio = new JSpinner(smGio);
	    	    DateEditor editor = new JSpinner.DateEditor(jsGio, "HH:MM");
	    	    jsGio.setEditor(editor);
	    	    editor.getTextField().setHorizontalAlignment(SwingConstants.RIGHT);
	    	    Calendar calendar1 = Calendar.getInstance();
	    	    calendar1.setTime(dcngay.getDate());
	    	    jsGio.setValue(dcngay.getDate());
				gio = calendar1.get(Calendar.HOUR_OF_DAY);
				phut = calendar1.get(Calendar.MINUTE);
	    	    jsGio.addChangeListener(new ChangeListener() {
	
	    			@Override
	    			public void stateChanged(ChangeEvent e) {
	    	    	    Calendar calendar1 = Calendar.getInstance();
	    				Date date1 = (Date) ((JSpinner)e.getSource()).getValue();
	    				calendar1.setTime(date1);
	    				gio = calendar1.get(Calendar.HOUR_OF_DAY);
	    				phut = calendar1.get(Calendar.MINUTE);
	    			}
	    		});
		} catch (Exception e) {
		}
	        jLabel3 = new javax.swing.JLabel();
	        txtsldat = new javax.swing.JTextField(String.valueOf(sldat));
	        txtsldat.setFont(new java.awt.Font("Tahoma", 0, 14));
	        txtsldat.setHorizontalAlignment(SwingConstants.RIGHT);
	        txtsldat.addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {
					JTextField field = (JTextField) e.getSource();
					if(e.getKeyChar() == '\n')
					{
						int sldat = Integer.valueOf(field.getText());
						if(sldat > slTong)
						{
							JOptionPane.showMessageDialog(null, "Bạn vừa nhập số lượng là " + sldat + " lớn hơn tổng sản phẩm là " + slTong + ". Hãy nhập số nhỏ hơn hoặc bằng " + slTong );
						}
					}
					
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
	        jLabel4 = new javax.swing.JLabel();
	        txtEmployee = new TextPopup<Employee>(Employee.class);
	        try {
	            Employee employee = HRModelManager.getInstance().getEmployeeByCode(detail.getEmployeeCode());
	            if(employee !=null)
	            {
	            	txtEmployee.setText(employee.getName());
	            }
			} catch (Exception e) {
			}
	        jLabel5 = new javax.swing.JLabel();
	        cbStatus = new ExtendJComboBox();
	        cbStatus.setModel(new DefaultComboBoxModel<String>(listTrangThai));
	        ((JLabel)cbStatus.getRenderer()).setHorizontalAlignment(SwingConstants.RIGHT);
	        jLabel6 = new javax.swing.JLabel();
	        jScrollPane1 = new javax.swing.JScrollPane();
	        txtghichu = new javax.swing.JTextArea();
	        jLabel1.setText("Ngày");
	        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12));
	        jLabel2.setText("Giờ");
	        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12));
	        jLabel3.setText("Số lượng");
	        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12));
	        jLabel4.setText("Người phụ trách");
	        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12));
	        jLabel5.setText("Tình trạng");
	        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12));
	        jLabel6.setText("Ghi chú");
	        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12));
	        txtghichu.setColumns(20);
	        txtghichu.setRows(5);
	        jScrollPane1.setViewportView(txtghichu);
	        jPanel2 = new JPanel();
	        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
	        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
	        jPanel2.setLayout(jPanel2Layout);
	        jPanel2Layout.setHorizontalGroup(
	        		jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGap(0, 0, Short.MAX_VALUE)
	        );
	        jPanel2Layout.setVerticalGroup(
	        		jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGap(0, 0, Short.MAX_VALUE)
	        );
	        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
	        jPanel1.setLayout(jPanel1Layout);
	        jPanel1Layout.setHorizontalGroup(
	            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
	           .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
	           
	            .addGroup(jPanel1Layout.createSequentialGroup()
	                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
	                    .addGroup(jPanel1Layout.createSequentialGroup()
	                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER, false)
	                            .addGroup(jPanel1Layout.createSequentialGroup()
	                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
	                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                                .addComponent(dcngay, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
	                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
	                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                                .addComponent(jsGio, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
	                            .addGroup(jPanel1Layout.createSequentialGroup()
	                                .addComponent(jLabel4)
	                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                                .addComponent(txtEmployee)))
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
	                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                            .addComponent(txtsldat,javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
	                            .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
	                    .addGroup(jPanel1Layout.createSequentialGroup()
	                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(jScrollPane1,javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                        )
	                .addContainerGap(6,6))
	        );
	        jPanel1Layout.setVerticalGroup(
	            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
	            .addGroup(jPanel1Layout.createSequentialGroup()
	            	.addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jLabel1)
	                    .addComponent(dcngay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(jLabel2)
	                    .addComponent(jsGio, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(jLabel3)
	                    .addComponent(txtsldat, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jLabel4)
	                    .addComponent(txtEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(jLabel5)
	                    .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addGroup(jPanel1Layout.createSequentialGroup()
	                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
	                .addContainerGap()
	                )
	        );
		layoutPanel.addComponent(jPanel1);
		}
		layoutPanel.updateUI();
	}
	public void getdata()
	{

	}
	public void btSaveActionPerformed() {
		try {
			Appointment appointment = new Appointment();
			Customer customer = CustomerModelManager.getInstance().getCustomerByCode(detail.getCustomerCode());
			String loginId;
			try {
				Employee employee = HRModelManager.getInstance().getEmployeeByCode(detail.getEmployeeCode());
				loginId = employee.getLoginId();
			} catch (Exception e) {
				loginId="";
			}
			
			for(int i = 0; i< detail.getItems().size();i++ )
			{
			appointment.setName("KH " + customer.getName() + " đặt sản phẩm " + detail.getItems().get(i).getItemName());
			}
			dcngay.setDate(detail.getStartDate());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dcngay.getDate());
			appointment.setDateStart(calendar.getTime());
			if(!txtEmployee.getText().trim().isEmpty())
			{
				appointment.setEmployeeLoginId(loginId);
			}
			Status status = null;
			String strstatus = cbStatus.getSelectedItem().toString();
			if(strstatus.equals("Chưa làm"))
			{
				status = Status.UNACCOMPLISHED;
			}
			if(strstatus.equals("Hoàn thành"))
			{
				status = Status.COMPLETE;
			}
			appointment.setStatus(status);
			if(customer !=null)
			{
			appointment.setPartnerLoginId(customer.getLoginId());
			}
			appointment.setContent("Số lượng đặt" + txtsldat);
			appointment.setDescription(txtghichu.getText());
			appointment = HRModelManager.getInstance().saveAppointment(appointment);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean checkData() {
		
		return true;
	}
}
