package com.hkt.client.swingexp.app.nhansu.dailywork;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.hkt.client.swingexp.add.lunacalendar.LunarCalendar;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.nhansu.PanelToDoCalendar;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.hr.entity.Appointment;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.partner.customer.entity.Customer;

public class PanelDayOfMonth extends JPanel {
	private JLabel lbAdd;
	private JLabel lbNegative;
	private JLabel lbPositive;
	private JPanel panelWork;
	private JLabel lbWork;
	private Date date;
	private LunarCalendar lunarCalendar = new LunarCalendar();
	private CalendarAppointment c;
	private int n, day_of_month;
	private Employee employee;
	private Customer customer;
	private String checkkh,checkncc,checkns;
	private List<Appointment> appointments;
	public PanelDayOfMonth() {
		lbWork = new javax.swing.JLabel();
		lbPositive = new javax.swing.JLabel();
		lbNegative = new javax.swing.JLabel();
		panelWork = new javax.swing.JPanel();
		lbAdd = new javax.swing.JLabel("");

		setBackground(new java.awt.Color(255, 255, 255));

		lbPositive.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		lbPositive.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lbPositive.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		lbPositive.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				lbPositiveMouseClicked(evt);
			}
		});

		lbNegative.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		lbNegative.setForeground(new java.awt.Color(51, 51, 51));
		lbNegative.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

		panelWork.setBackground(new java.awt.Color(255, 255, 255));
		panelWork.setOpaque(false);
//		panelWork.setPreferredSize(new java.awt.Dimension(113, 90));
		panelWork.setLayout(new GridLayout(4, 1));
		lbAdd.setForeground(new java.awt.Color(0, 102, 255));
		lbAdd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lbAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("icon/add.png")));
		lbAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		lbAdd.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				lbAddMouseClicked(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														lbPositive,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														50,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														lbAdd,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														50,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														lbNegative,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														50,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(3, 3, 3)
								.addComponent(panelWork,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										115,
										javax.swing.GroupLayout.PREFERRED_SIZE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(lbPositive,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										12,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(12, 12, 12)
								.addComponent(lbAdd,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										26,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(5, 5, 5)
								.addComponent(lbNegative,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										12,
										javax.swing.GroupLayout.PREFERRED_SIZE))
				.addComponent(panelWork,
						javax.swing.GroupLayout.PREFERRED_SIZE, 70,
						javax.swing.GroupLayout.PREFERRED_SIZE));

	}

	private void lbPositiveMouseClicked(java.awt.event.MouseEvent evt) {
		if (evt.getClickCount() > 1) {
			try {
				PanelToDoCalendar panelToDoCalendar = new PanelToDoCalendar(
						false);
				DialogMain main = new DialogMain(panelToDoCalendar);
				main.setTitle("Công việc hàng ngày");
				main.setVisible(true);
				load();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	private void lbAddMouseClicked(java.awt.event.MouseEvent evt) {
		if (evt.getClickCount() > 1) {
			try {
				PanelToDoCalendar panelToDoCalendar = new PanelToDoCalendar(
						true);
				panelToDoCalendar.setDate(date);
				DialogMain main = new DialogMain(panelToDoCalendar);
				main.setTitle("Công việc hàng ngày");
				main.setVisible(true);
				load();

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public JLabel getLbPositive() {
		return lbPositive;
	}

	public JLabel getLbNegative() {
		return lbNegative;
	}

	public JPanel getPnWork() {
		return panelWork;
	}

	public void setDate(Date date) {
		this.date = date;

	}

	public JLabel getLbAdd() {
		return lbAdd;
	}

	public Date getDate() {
		return date;
	}


	public JLabel getLbWork() {
		return lbWork;
	}

	public void setLbWork(JLabel lbWork) {
		this.lbWork = lbWork;
	}

	public void loadAppointment(final CalendarAppointment c,
		final Calendar calendar, Employee employee, final Customer customer, final String checkkh, final String checkncc, final String checkns) {
		this.c = c;
		this.employee = employee;
		this.customer = customer;
		this.checkkh = checkkh;
		this.checkncc = checkncc;
		this.checkns = checkns;
		n = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		day_of_month = calendar.get(Calendar.DAY_OF_MONTH);
		String middle = String.valueOf(day_of_month);
		String lunar = lunarCalendar.getLunarDate(calendar.getTime())
				.toString();
		String[] lunarDD = lunar.split("/");
		String lunarDay = "";
		if (lunarDD[0].equals("1")) {
			lunarDay = "" + lunarDD[0] + "/" + lunarDD[1];
		} else {
			lunarDay = "" + lunarDD[0];
		}
		setDate(calendar.getTime());
		// Hiển thị công việc hằng ngày
		load();
		lbPositive.setText(String.valueOf(middle));
		lbNegative.setText(lunarDay);

	}
	public List<Appointment> getListsn(String field,String str) throws Exception{	
		 List<Appointment> appointments;
		 FilterQuery filterQuery = new FilterQuery();
		 Calendar calendar1 = Calendar.getInstance();
		 calendar1.setTime(date);
		 calendar1.set(Calendar.HOUR_OF_DAY, 0);
		 calendar1.set(Calendar.MINUTE, 1);
		 Calendar calendar2 = Calendar.getInstance();
		 calendar2.setTime(date);
		 calendar2.set(Calendar.HOUR_OF_DAY, 23);
		 calendar2.set(Calendar.MINUTE, 59);
		 filterQuery.add("dateStart", FilterQuery.Operator.GT,calendar1.getTime());
		 filterQuery.add("dateStart", FilterQuery.Operator.LT,calendar2.getTime());

			if(customer !=null)
			{
				filterQuery.add("partnerLoginId", FilterQuery.Operator.EQUAL, customer.getLoginId());
			}
				filterQuery.add(field, FilterQuery.Operator.EQUAL, str);	
				appointments = HRModelManager.getInstance().searchAppointmentById(filterQuery);
			return appointments;
		}
	public List<Appointment> getList(String field,String str) throws Exception{
		
		 List<Appointment> appointments;
		 FilterQuery filterQuery = new FilterQuery();
		 Calendar calendar1 = Calendar.getInstance();
		 calendar1.setTime(date);
		 calendar1.set(Calendar.HOUR_OF_DAY, 0);
		 calendar1.set(Calendar.MINUTE, 1);
		 Calendar calendar2 = Calendar.getInstance();
		 calendar2.setTime(date);
		 calendar2.set(Calendar.HOUR_OF_DAY, 23);
		 calendar2.set(Calendar.MINUTE, 59);
		 filterQuery.add("dateStart", FilterQuery.Operator.GT,calendar1.getTime());
		 filterQuery.add("dateStart", FilterQuery.Operator.LT,calendar2.getTime());
				filterQuery.add(field, FilterQuery.Operator.EQUAL, str);
				appointments = HRModelManager.getInstance().searchAppointmentById(filterQuery);
			return appointments;
		}
	public void load() {
		panelWork.removeAll();
		FilterQuery filterQuery = new FilterQuery();
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date);
		calendar1.set(Calendar.HOUR_OF_DAY, 0);
		calendar1.set(Calendar.DAY_OF_MONTH, 1);
		calendar1.set(Calendar.MINUTE, 1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date);
		calendar2.set(Calendar.DAY_OF_MONTH, n);
		calendar2.set(Calendar.HOUR_OF_DAY, 23);
		calendar2.set(Calendar.MINUTE, 59);
		filterQuery.add("dateStart", FilterQuery.Operator.GT,
				calendar1.getTime());
		filterQuery.add("dateStart", FilterQuery.Operator.LT,
				calendar2.getTime());
	
		 List<Appointment> appointments = new ArrayList<Appointment>();
		try {
			 if((checkkh.trim().isEmpty()) && (checkncc.trim().isEmpty()) && (checkns.trim().isEmpty()))
				{
					if(employee != null)
					{
						filterQuery.add("employeeLoginId", FilterQuery.Operator.EQUAL, employee.getLoginId());
					}
					if(customer !=null)
					{
						filterQuery.add("partnerLoginId", FilterQuery.Operator.EQUAL, customer.getLoginId());
					}
						appointments = HRModelManager.getInstance().searchAppointmentById(filterQuery);
				}
				else
				{
				if(employee != null)
				{
					appointments.addAll(getListsn("employeeLoginId", employee.getLoginId()));
				}
				if(checkkh.equals("khachhang"))
				{
					appointments.addAll(getList("createdBy", checkkh));
				}
				if(checkncc.equals("nhacungcap"))
				{
					appointments.addAll(getList("createdBy", checkncc));
				}
				if(checkns .equals("nhansu"))
				{
					appointments.addAll(getList("createdBy", checkns));
				}
				}
			Collections.sort(appointments, new Comparator<Appointment>() {

				@Override
				public int compare(Appointment o1, Appointment o2) {
					return (o1.getDateStart().compareTo(o2.getDateStart()));
				}
				
			});
			for (int k = 0; k < appointments.size(); k++) {
				Appointment.Status status = appointments.get(k).getStatus();
				Date date1 = appointments.get(k).getDateStart();
				Calendar calendar3 = Calendar.getInstance();
				calendar3.setTime(date1);
				if (day_of_month == calendar3.get(Calendar.DAY_OF_MONTH)) {
					// Hiển thị công việc
					MyLable myLable = new MyLable(appointments.get(k));
					myLable.setDayOfMonth(this);
					if (panelWork.getComponentCount() < 6) {

						myLable.setVerticalAlignment(SwingConstants.CENTER);
						if (status == Appointment.Status.UNACCOMPLISHED
								|| status == Appointment.Status.ONGOING) {
							myLable.setForeground(Color.RED);
						} else {
							myLable.setForeground(Color.decode("0x00FF00"));
						}
						panelWork.add(myLable);
					}
					if (panelWork.getComponentCount()==5) {
						panelWork.remove(4);
						panelWork.remove(3);
						Appointment a = new Appointment();
						a.setName("Continues");
						panelWork.add(new MyLable(a)).addMouseListener(
								new MouseAdapter() {
									@Override
									public void mouseClicked(MouseEvent e) {
										c.setDate(date);
										c.loadPanelDay();
									}
								});
					}
				}
				}

			panelWork.updateUI();

		} catch (Exception e) {
			e.printStackTrace();
			appointments = new ArrayList<Appointment>();
		}
	}

}
