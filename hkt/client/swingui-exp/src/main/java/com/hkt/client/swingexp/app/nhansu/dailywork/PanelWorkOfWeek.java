package com.hkt.client.swingexp.app.nhansu.dailywork;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.hr.entity.Appointment;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.partner.customer.entity.Customer;

public class PanelWorkOfWeek extends JPanel {
  private javax.swing.JLabel      lbDate;
  private JPanel      pnWork;
  private CalendarAppointment c;
  private Date date;
  private int i;
  private Employee employee;
  private Customer customer;
  private String checkkh, checkncc, checkns;
  
  public PanelWorkOfWeek() {
    initComponents();
  }

  private void initComponents() {

    lbDate = new javax.swing.JLabel();
    pnWork = new JPanel();
    lbDate.setFont(new java.awt.Font("Tahoma", 0, 14));
    lbDate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);    
    pnWork.setBackground(new java.awt.Color(255, 255, 255));
    pnWork.setOpaque(true);
    pnWork.setSize(new Dimension(10, 14));
    pnWork.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(lbDate, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
        .addComponent(pnWork, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE));
    
    layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
        layout.createSequentialGroup()
            .addComponent(lbDate, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(pnWork, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)));
  }

public javax.swing.JLabel getLbDate() {
	return lbDate;
}

public void setLbDate(javax.swing.JLabel lbDate) {
	this.lbDate = lbDate;
}

public javax.swing.JPanel getPnWork() {
	return pnWork;
}

public void setPnWork(javax.swing.JPanel pnWork) {
	this.pnWork = pnWork;
}

public Date getDate() {
	return date;
}

public void setDate(Date date) {
	this.date = date;
}
public void loadAppointment(final CalendarAppointment c,
		final Calendar calendar, final int i, final Employee employee, final Customer customer, final String checkkh, final String checkncc, final String checkns) {
	this.c = c;
	this.i = i;
	this.customer = customer;
	this.employee = employee;
	this.checkkh = checkkh;
	this.checkncc = checkncc;
	this.checkns = checkns;
	setDate(calendar.getTime());
	load();
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
	pnWork.removeAll();
	FilterQuery filterQuery = new FilterQuery();
	Calendar calendar1 = Calendar.getInstance();
	calendar1.setTime(date);
	calendar1.set(Calendar.HOUR_OF_DAY, 0);
	calendar1.set(Calendar.DAY_OF_WEEK, 1);
	calendar1.set(Calendar.MINUTE, 1);
	Calendar calendar2 = Calendar.getInstance();
	calendar2.setTime(date);
	calendar2.set(Calendar.DAY_OF_WEEK, 7);
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
				if(o1.getDateStart() == o2.getDateStart())
				{
					return (o1.getName().compareTo(o2.getName()));
				}
				else
				{
					return (o1.getDateStart().compareTo(o2.getDateStart()));
				}
				
			}
		
		});
		for (int k = 0; k < appointments.size(); k++) {
			Appointment.Status status = appointments.get(k).getStatus();
			Date date1 = appointments.get(k).getDateStart();
			Calendar calendar3 = Calendar.getInstance();
			calendar3.setTime(date1);
			int h = calendar3.get(Calendar.HOUR_OF_DAY);
			int m = calendar3.get(Calendar.MINUTE);
			if ((i + 1) == calendar3.get(Calendar.DAY_OF_WEEK)) {
				MyLableWeek myLable = new MyLableWeek(
						appointments.get(k), h, m);
				myLable.setPanelWorkOfWeek(this);
				myLable.setVerticalAlignment(SwingConstants.BOTTOM);
				myLable.setBorder(BorderFactory
						.createLineBorder(new Color(168, 168, 168)));
				pnWork.add(myLable);
				pnWork.setLayout(new GridLayout(29, 1, 6, 1));
				if (status == Appointment.Status.UNACCOMPLISHED
						|| status == Appointment.Status.ONGOING) {
					myLable.setForeground(Color.RED);
				} else {
					myLable.setForeground(Color.decode("0x00FF00"));
				}
				setDate(calendar3.getTime());
				if(pnWork.getComponentCount() == 28)
				{
					pnWork.remove(27);
					Appointment a = new Appointment();
					a.setName(".............");
					pnWork.add(new MyLable(a)).addMouseListener(
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
		pnWork.updateUI();
	} catch (Exception e) {
		e.printStackTrace();
		appointments = new ArrayList<Appointment>();
	}
	}
}
