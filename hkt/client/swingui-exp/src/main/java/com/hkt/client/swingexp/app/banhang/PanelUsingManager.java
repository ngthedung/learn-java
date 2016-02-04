package com.hkt.client.swingexp.app.banhang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.swingexp.app.component.ExtendJButton;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJRadioButton;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.ImageTool;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.component.TextPopup;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.IMyObserver;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.khachhang.BasicInformation;
import com.hkt.client.swingexp.app.nhansu.list.TableModelToDoCalendar;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.module.core.entity.AbstractPersistable.State;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterQuery.Operator;
import com.hkt.module.hr.entity.Appointment;
import com.hkt.module.hr.entity.Appointment.Status;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.partner.customer.entity.Customer;

/** GIAO DIỆN ::: QUẢN LÝ SỬ DỤNG */
public class PanelUsingManager extends JPanel implements IMyObserver, IDialogResto {
	private TableModelToDoCalendar	modelTableLeft, modelTableRight;
	private JPanel									panelLeft, panelRight, panelCenter;
	private ButtonGroup							groupRadioButton;
	private ExtendJButton						btnAddOne, btnAddAll, btnRemoveOne, btnRemoveAll, btnCancel;
	private JTable									tableLeft, tableRight;
	private JScrollPane							scrollPane1;
	private JScrollPane							scrollPane2;
	private ExtendJLabel						lblName, lblCode, lblMobile, lblFromDate, lblAvatar;
	private ExtendJRadioButton			rdEmployee, rdCustomer;
	private TextPopup<Customer>			txtCustomer;
	private TextPopup<Employee>			txtEmployee;
	private ExtendJTextField				txtName, txtCode, txtMobile;
	private MyJDateChooser					dcFromDate;
	private List<Appointment>				listAppoinmentsLeft, listTemps, listAppoinmentsRight;

	public PanelUsingManager() {
		drawLayout();

		//Khởi tạo các giá trị 
		listAppoinmentsLeft = new ArrayList<Appointment>();
		listAppoinmentsRight = new ArrayList<Appointment>();
		listTemps = new ArrayList<Appointment>();
		
		btnAddOne.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionButtonAddOne();
			}
		});
		
		btnRemoveOne.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionButtonRemoveOne();
			}
		});
		
		btnAddAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionButtonAddAll();
			}
		});
		
		btnRemoveAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionButtonRemoveAll();
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionButtonCancel();
			}
		});
	}

	private void drawLayout() {
		/** Khởi tạo các component */
		panelLeft = new JPanel();
		panelRight = new JPanel();
		panelCenter = new JPanel();

		panelLeft.setOpaque(false);
		panelRight.setOpaque(false);
		panelCenter.setOpaque(false);

		rdCustomer = new ExtendJRadioButton("Khách hàng");
		rdCustomer.setOpaque(false);
		rdCustomer.setSelected(true);
		rdEmployee = new ExtendJRadioButton("Nhân viên");
		rdEmployee.setOpaque(false);

		groupRadioButton = new ButtonGroup();
		groupRadioButton.add(rdCustomer);
		groupRadioButton.add(rdEmployee);

		lblName = new ExtendJLabel("Khách hàng");
		lblCode = new ExtendJLabel("Mã KH");
		lblMobile = new ExtendJLabel("Điện thoại");
		lblFromDate = new ExtendJLabel("Từ ngày");

		txtName = new ExtendJTextField();
		txtCode = new ExtendJTextField();
		txtMobile = new ExtendJTextField();
		dcFromDate = new MyJDateChooser();
		txtCustomer = new TextPopup<Customer>(Customer.class);
		txtCustomer.addObserver(this);
		try {
			txtCustomer.setData(CustomerModelManager.getInstance().getCustomers(false));
		} catch (Exception e1) {
			txtCustomer.setData(new ArrayList<Customer>());
		}
		txtEmployee = new TextPopup<Employee>(Employee.class);
		txtEmployee.addObserver(this);
		try {
			txtEmployee.setData(HRModelManager.getInstance().getEmployees());
		} catch (Exception e1) {
			txtEmployee.setData(new ArrayList<Employee>());
		}

		lblAvatar = new ExtendJLabel("Ảnh đại diện", SwingConstants.CENTER);
		lblAvatar.setFont(new Font("Tahoma", Font.ITALIC, 12));
		lblAvatar.setPreferredSize(new Dimension(90, 90));
		lblAvatar.setBorder(BorderFactory.createEtchedBorder());
		lblAvatar.setHorizontalTextPosition(SwingConstants.CENTER);

		modelTableLeft = new TableModelToDoCalendar(new ArrayList<Appointment>());
		modelTableRight = new TableModelToDoCalendar(new ArrayList<Appointment>());
		tableLeft = new JTable();
		tableLeft.setModel(modelTableLeft);
		tableLeft.setRowHeight(23);
		tableLeft.setFont(new Font("Tahoma", 0, 14));
		tableLeft.getColumnModel().getColumn(0).setMaxWidth(40);
		tableLeft.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		((DefaultTableCellRenderer) tableLeft.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		tableLeft.setBackground(Color.WHITE);
		tableLeft.setFillsViewportHeight(true);
		
		tableRight = new JTable();
		tableRight.setModel(modelTableRight);
		tableRight.setRowHeight(23);
		tableRight.setFont(new Font("Tahoma", 0, 14));
		tableRight.getColumnModel().getColumn(0).setMaxWidth(40);
		tableRight.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		((DefaultTableCellRenderer) tableRight.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		tableRight.setBackground(Color.WHITE);
		tableRight.setFillsViewportHeight(true);
		
		scrollPane1 = new JScrollPane();
		scrollPane2 = new JScrollPane();
		scrollPane1.setViewportView(tableLeft);
		scrollPane2.setViewportView(tableRight);

		btnAddOne = new ExtendJButton(">");
		btnAddAll = new ExtendJButton(">>");
		btnRemoveOne = new ExtendJButton("<");
		btnRemoveAll = new ExtendJButton("<<");
		btnCancel = new ExtendJButton("Hủy");

		this.setLayout(new BorderLayout(10, 0));
		this.setOpaque(false);

		/** Vẽ giao diện Panel trái */
		panelLeft.setLayout(new BorderLayout(0, 0));
		JPanel p1 = new JPanel();
		p1.setOpaque(false);
		p1.setLayout(new GridLayout(1, 2, 0, 0));
		p1.add(rdCustomer);
		p1.add(rdEmployee);
		final JPanel p2 = new JPanel();
		p2.setLayout(new BorderLayout());
		p2.add(txtCustomer, BorderLayout.CENTER);
		final JPanel panell1 = new JPanel();
		MyGroupLayout myLayoutLeft = new MyGroupLayout(panell1);
		myLayoutLeft.add(0, 0, new ExtendJLabel("Đối tượng"));
		myLayoutLeft.add(0, 1, p1);
		myLayoutLeft.add(1, 0, new ExtendJLabel("Tìm kiếm"));
		myLayoutLeft.add(1, 1, p2);
		myLayoutLeft.add(2, 0, lblFromDate);
		myLayoutLeft.add(2, 1, dcFromDate);
		myLayoutLeft.updateGui();

		JPanel panell2 = new JPanel();
		panell2.setOpaque(false);
		panell2.setLayout(new BorderLayout(0, 0));
		panell2.add(new ExtendJLabel("Công việc chưa hoàn thành"), BorderLayout.PAGE_START);
		panell2.add(scrollPane1);

		panelLeft.add(panell1, BorderLayout.PAGE_START);
		panelLeft.add(panell2, BorderLayout.CENTER);
		panelLeft.setPreferredSize(new Dimension(550, 100));

		/** Vẽ giao diện Panel giữa */
		panelCenter.setLayout(new GridLayout(3, 1, 0, 0));
		panelCenter.add(new JLabel(""));
		JPanel panelc1 = new JPanel();
		panelc1.setOpaque(false);
		panelc1.setLayout(new GridLayout(4, 1, 0, 5));
		panelc1.add(btnAddAll);
		panelc1.add(btnAddOne);
		panelc1.add(btnRemoveOne);
		panelc1.add(btnRemoveAll);
		panelCenter.add(panelc1);
		JPanel panelc2 = new JPanel();
		panelc2.setOpaque(false);
		panelc2.setLayout(new GridLayout(4, 1, 0, 5));
		panelc2.add(new JLabel(""));
		panelc2.add(btnCancel);
		panelc2.add(new JLabel(""));
		panelc2.add(new JLabel(""));
		panelCenter.add(panelc2);

		/** Vẽ giao diện Panel phải */
		panelRight.setLayout(new BorderLayout(0, 0));
		JPanel panelr1 = new JPanel();
		JPanel pr1 = new JPanel();
		pr1.setOpaque(false);
		pr1.setLayout(new BorderLayout());
		pr1.add(lblAvatar, BorderLayout.PAGE_START);
		JPanel pr2 = new JPanel();
		MyGroupLayout myLayoutRight = new MyGroupLayout(pr2);
		myLayoutRight.add(0, 0, lblCode);
		myLayoutRight.add(0, 1, txtCode);
		myLayoutRight.add(1, 0, lblName);
		myLayoutRight.add(1, 1, txtName);
		myLayoutRight.add(2, 0, lblMobile);
		myLayoutRight.add(2, 1, txtMobile);
		myLayoutRight.updateGui();

		panelr1.setLayout(new BorderLayout(5, 0));
		panelr1.setOpaque(false);
		panelr1.add(pr2, BorderLayout.CENTER);
		panelr1.add(pr1, BorderLayout.LINE_END);

		JPanel panelr2 = new JPanel();
		panelr2.setOpaque(false);
		panelr2.setLayout(new BorderLayout(0, 0));
		panelr2.add(new ExtendJLabel("Công việc đã hoàn thành"), BorderLayout.PAGE_START);
		panelr2.add(scrollPane2);

		panelRight.add(panelr1, BorderLayout.PAGE_START);
		panelRight.add(panelr2, BorderLayout.CENTER);
		panelRight.setPreferredSize(new Dimension(550, 100));

		//Add các Panel vào ContentPane
		this.add(panelLeft, BorderLayout.LINE_START);
		this.add(panelCenter, BorderLayout.CENTER);
		this.add(panelRight, BorderLayout.LINE_END);

		rdCustomer.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (rdCustomer.isSelected()) {
					p2.remove(txtEmployee);
					p2.add(txtCustomer);
					p2.revalidate();
					p2.repaint();
					revalidate();
					repaint();
					validate();
					txtEmployee.setItem(null);
					txtEmployee.setText("");
					lblCode.setText("Mã KH");
					lblName.setText("Khách hàng");
					txtCode.setText("");
					txtName.setText("");
					txtMobile.setText("");
					modelTableLeft.setData(new ArrayList<Appointment>(), 0);
					modelTableRight.setData(new ArrayList<Appointment>(), 0);
				}
			}
		});

		rdEmployee.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (rdEmployee.isSelected()) {
					p2.remove(txtCustomer);
					p2.add(txtEmployee);
					p2.revalidate();
					p2.repaint();
					revalidate();
					repaint();
					validate();
					txtCustomer.setItem(null);
					txtCustomer.setText("");
					lblCode.setText("Mã NV");
					lblName.setText("Nhân viên");
					txtCode.setText("");
					txtName.setText("");
					txtMobile.setText("");
					modelTableLeft.setData(new ArrayList<Appointment>(), 0);
					modelTableRight.setData(new ArrayList<Appointment>(), 0);
				}
			}
		});
	}

	private void getAppointmentsByLoginId(Object object, Date dateStart) {
		String loginId = "";
		String parameter = "";
		if(object instanceof Employee){
			loginId = ((Employee)object).getLoginId();
			parameter = "employeeLoginId";
		} else {
			loginId = ((Customer)object).getLoginId();
			parameter = "partnerLoginId";
		}
		
		listAppoinmentsLeft.clear();
		listAppoinmentsRight.clear();
		
		FilterQuery filterQuery = new FilterQuery();
		filterQuery.add("status", Operator.NOTEQUAL, Status.COMPLETE.toString());
		filterQuery.add(parameter, Operator.EQUAL, loginId);
		if(dateStart != null)
			filterQuery.add("dateStart", Operator.GT, dateStart);
		try {
			listAppoinmentsLeft = HRModelManager.getInstance().searchAppointmentById(filterQuery);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		FilterQuery filterQuery1 = new FilterQuery();
		filterQuery1.add("status", Operator.EQUAL, Status.COMPLETE.toString());
		filterQuery1.add(parameter, Operator.EQUAL, loginId);
		if(dateStart != null)
			filterQuery.add("dateStart", Operator.GT, dateStart);
		try {
			listAppoinmentsRight = HRModelManager.getInstance().searchAppointmentById(filterQuery1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		modelTableLeft.setData(listAppoinmentsLeft, 0);
		modelTableRight.setData(listAppoinmentsRight, 0);
	}
	
	private void actionButtonAddOne(){
		Appointment app = (Appointment) tableLeft.getValueAt(tableLeft.getSelectedRow(), 1);
		if(app != null){
			if(app.getPersistableState().equals(State.DELETED)){
				app.setPersistableState(State.ORIGIN);
				for(Appointment a : listAppoinmentsLeft){
					if(a.getId() == app.getId()){
						listAppoinmentsRight.add(a);
						listAppoinmentsLeft.remove(a);
						break;
					}
				}
				for(Appointment a : listTemps){
					if(a.getId() == app.getId()){
						listTemps.remove(a);
						break;
					}
				}
			} else {
				app.setPersistableState(State.NEW);
				for(Appointment a : listAppoinmentsLeft){
					if(a.getId() == app.getId()){
						listAppoinmentsRight.add(a);
						listTemps.add(a);
						listAppoinmentsLeft.remove(a);
						break;
					}
				}
			}
			modelTableLeft.setData(listAppoinmentsLeft, 0);
			modelTableRight.setData(listAppoinmentsRight, 0);
		}
	}
	
	private void actionButtonRemoveOne(){
		Appointment app = (Appointment) tableRight.getValueAt(tableRight.getSelectedRow(), 1);
		if(app != null){
			if(app.getPersistableState().equals(State.NEW)){
				app.setPersistableState(State.ORIGIN);
				for(Appointment a : listAppoinmentsRight){
					if(a.getId() == app.getId()){
						listAppoinmentsLeft.add(a);
						listAppoinmentsRight.remove(a);
						break;
					}
				}
				for(Appointment a : listTemps){
					if(a.getId() == app.getId()){
						listTemps.remove(a);
						break;
					}
				}
			} else {
				app.setPersistableState(State.DELETED);
				for(Appointment a : listAppoinmentsRight){
					if(a.getId() == app.getId()){
						listAppoinmentsLeft.add(a);
						listTemps.add(app);
						listAppoinmentsRight.remove(a);
						break;
					}
				}
			}
			modelTableLeft.setData(listAppoinmentsLeft, 0);
			modelTableRight.setData(listAppoinmentsRight, 0);
		}
	}
	
	private void actionButtonAddAll(){
		for(Appointment a : listAppoinmentsLeft){
			if(a.getPersistableState().equals(State.DELETED)){
				a.setPersistableState(State.ORIGIN);
				listAppoinmentsRight.add(a);
				
				for(Appointment app : listTemps){
					if(app.getId() == a.getId()){
						listTemps.remove(app);
						break;
					}
				}
			} else {
				a.setPersistableState(State.NEW);
				listAppoinmentsRight.add(a);
				listTemps.add(a);
			}
		}
		listAppoinmentsLeft.clear();
		modelTableLeft.setData(listAppoinmentsLeft, 0);
		modelTableRight.setData(listAppoinmentsRight, 0);
	}
	
	private void actionButtonRemoveAll(){
		for(Appointment a : listAppoinmentsRight){
			if(a.getPersistableState().equals(State.NEW)){
				a.setPersistableState(State.ORIGIN);
				listAppoinmentsLeft.add(a);
				
				for(Appointment app : listTemps){
					if(app.getId() == a.getId()){
						listTemps.remove(app);
						break;
					}
				}
			} else {
				a.setPersistableState(State.DELETED);
				listAppoinmentsLeft.add(a);
				listTemps.add(a);
			}
		}
		listAppoinmentsRight.clear();
		modelTableLeft.setData(listAppoinmentsLeft, 0);
		modelTableRight.setData(listAppoinmentsRight, 0);
	}
	
	private void actionButtonCancel(){
		Calendar date = Calendar.getInstance();
		date.setTime(dcFromDate.getDate());
		date.set(Calendar.HOUR_OF_DAY, 00);
		date.set(Calendar.MINUTE, 00);
		date.set(Calendar.SECOND, 00);
		if(rdCustomer.isSelected()){
			Customer c = txtCustomer.getItem();
			if(c != null)
				getAppointmentsByLoginId(c, date.getTime());
			else {
				listAppoinmentsLeft.clear();
				listAppoinmentsRight.clear();
				modelTableLeft.setData(listAppoinmentsLeft, 0);
				modelTableRight.setData(listAppoinmentsRight, 0);
			}
		} else {
			if(rdEmployee.isSelected()){
				Employee e = txtEmployee.getItem();
				if(e != null)
					getAppointmentsByLoginId(e, date.getTime());
				else {
					listAppoinmentsLeft.clear();
					listAppoinmentsRight.clear();
					modelTableLeft.setData(listAppoinmentsLeft, 0);
					modelTableRight.setData(listAppoinmentsRight, 0);
				}
			}
		}
		listTemps.clear();
	}

	@Override
	public void Save() throws Exception {
		for(Appointment a : listTemps){
			if(a.getPersistableState().equals(State.NEW)){
				a.setStatus(Status.COMPLETE);
			} else {
				if(a.getPersistableState().equals(State.DELETED)){
					a.setStatus(Status.UNACCOMPLISHED);
				}
			}
			a.setPersistableState(State.ORIGIN);
			HRModelManager.getInstance().saveAppointment(a);
		}
		listTemps.clear();
		DialogNotice.getInstace().setVisible(true);
	}

	@Override
	public void update(Object o, Object arg) {
		Calendar date = Calendar.getInstance();
		date.setTime(dcFromDate.getDate());
		date.set(Calendar.HOUR_OF_DAY, 00);
		date.set(Calendar.MINUTE, 00);
		date.set(Calendar.SECOND, 00);
		if(arg != null){
	    if (arg instanceof Employee || o instanceof Employee) {
	    	Employee e = txtEmployee.getItem();
	  		getAppointmentsByLoginId(e, date.getTime());
	  		txtCode.setText(e.getCode());
	  		txtName.setText(e.getName());
				txtMobile.setText(e.getMobile());
				try {
					String image = AccountModelManager.getInstance().getAccountByLoginId(e.getLoginId()).getProfiles().getBasic().get(BasicInformation.AVATAR).toString();
					ImageIcon icon = ImageTool.getInstance().decodeToImage(image);
					lblAvatar.setText("");
					lblAvatar.setIcon(icon);
				} catch (Exception e1) {
					lblAvatar.setText("Ảnh đại diện");
					lblAvatar.setIcon(null);
				}
	    } else {
	    	Customer c = txtCustomer.getItem();
	    	getAppointmentsByLoginId(txtCustomer.getItem(), date.getTime());
	    	txtCode.setText(c.getCode());
	  		txtName.setText(c.getName());
				txtMobile.setText(c.getMobile());
				try {
					String image = AccountModelManager.getInstance().getAccountByLoginId(c.getLoginId()).getProfiles().getBasic().get(BasicInformation.AVATAR).toString();
					ImageIcon icon = ImageTool.getInstance().decodeToImage(image);
					lblAvatar.setText("");
					lblAvatar.setIcon(icon);
				} catch (Exception e1) {
					lblAvatar.setText("Ảnh đại diện");
					lblAvatar.setIcon(null);
				}
	    }
		}
	}
}
