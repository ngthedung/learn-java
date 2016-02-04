package com.hkt.client.swingexp.app.nhansu.dailywork;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import com.hkt.client.swingexp.app.component.GroupLayoutPanel;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.hr.entity.Appointment;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.partner.customer.entity.Customer;

public class PanelWorkOfDay extends JPanel {
	private JScrollPane jspWorkLeft, jspWorkRight;
	private JPanel jpnTotalWork;
  private JLabel lbHour;
  private GroupLayoutPanel panelWorkLeft, panelWorkRight;
  private CalendarAppointment c;
  private Calendar calendar;
  private Employee employee;
  private Date date;
  private MyLableDay myLable;
  private JPanel jPnWork;
  private int i;
  private Customer customer;
  private String checkkh,checkncc,checkns;
  public PanelWorkOfDay() {
    initComponents();
  }

  private void initComponents() {
    panelWorkLeft = new GroupLayoutPanel();
    panelWorkLeft.setOpaque(false);
    panelWorkRight = new GroupLayoutPanel();
    panelWorkRight.setOpaque(false);
    jspWorkLeft = new JScrollPane();
    jspWorkLeft.setOpaque(false);
    jspWorkLeft.getViewport().setOpaque(false);
    jspWorkLeft.setViewportView(panelWorkLeft);
    jspWorkLeft.setAutoscrolls(false);
    jspWorkLeft.setBorder(null);
    jspWorkRight = new JScrollPane();
    jspWorkRight.setOpaque(false);
    jspWorkRight.getViewport().setOpaque(false);
	jspWorkRight.setViewportView(panelWorkRight);
	jspWorkRight.setBorder(null);
    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
        layout.createSequentialGroup()
            .addComponent(jspWorkLeft, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jspWorkRight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            ));
    layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
    		 .addComponent(jspWorkLeft, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
    		 .addComponent(jspWorkRight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE));
  }


public javax.swing.JLabel getLbHour() {
	return lbHour;
}

public void setLbHour(javax.swing.JLabel lbHour) {
	this.lbHour = lbHour;
}

public Date getDate() {
	return date;
}

public JScrollPane getJspWorkRight() {
	return jspWorkRight;
}

public void setJspWorkRight(JScrollPane jspWorkRight) {
	this.jspWorkRight = jspWorkRight;
}

public int getI() {
	return i;
}

public void setI(int i) {
	this.i = i;
}

public void setDate(Date date) {
	this.date = date;
}

public void loadAppointment(CalendarAppointment c,Calendar calendar, Employee employee, final Customer customer,final String checkkh, final String checkncc, final String checkns) {
	this.c = c;
	this.calendar = calendar;
	this.employee =employee;
	this.customer = customer;
	this.checkkh = checkkh;
	this.checkncc = checkncc;
	this.checkns = checkns;
	setDate(calendar.getTime());
	load();
}

public JScrollPane getJspWorkLeft() {
	return jspWorkLeft;
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
//public List<App>
public void load() {
	 panelWorkLeft.removeAll();
	 panelWorkLeft.loadLayout();
	 panelWorkRight.removeAll();
	 panelWorkRight.loadLayout();
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

	 List<Appointment> appointments = new ArrayList<Appointment>();
	 try {
		 if(checkkh.trim().isEmpty() && checkncc.trim().isEmpty() && checkns.trim().isEmpty())
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

			Hashtable<String, JPanel> hashtable= new Hashtable<String, JPanel>();
		for (int k = 0; k < appointments.size(); k++) {
				
	   // Hiển thị công việc
	   Appointment.Status status = appointments.get(k).getStatus();
	   Date date2 = appointments.get(k).getDateStart();
	   Calendar calendar3 = Calendar.getInstance();
	   calendar3.setTime(date2);
	   int h = calendar3.get(Calendar.HOUR_OF_DAY);
	   int m = calendar3.get(Calendar.MINUTE);
	   MyLableDay myLable = new MyLableDay(appointments.get(k));
	   myLable.setPanelWorkOfDay(this);
	   myLable.setVerticalAlignment(SwingConstants.CENTER);
	   myLable.setBackground(new java.awt.Color(255, 255, 255));
	   lbHour = new JLabel();
	   lbHour.setFont(new Font("Tahoma", 1, 14));
	   if (h < 10 && m < 10) {
	    lbHour.setText("0" + h + ":0" + m);
	   } else if (h > 10 && m < 10) {
	    lbHour.setText("" + h + ":0" + m);
	   } else if (h < 10 && m > 10) {
	    lbHour.setText("0" + h + ":" + m);
	   }
	   else {
	    lbHour.setText("" + h + ":" + m);
	   }
	   if(h < 12)
	   {
	   JPanel jpnContent = hashtable.get(lbHour.getText());
	   if(jpnContent==null){
		   jpnContent = new JPanel();
		   jpnContent.setBackground(new java.awt.Color(255, 255, 255));
		   jpnContent.setOpaque(true);
		   JPanel jpnHour = new JPanel();
		   jpnHour.setOpaque(false);
		   jpnHour.add(lbHour);
		   jPnWork = new JPanel();
		   jPnWork.setBackground(Color.WHITE);
		   jPnWork.setBorder(BorderFactory.createLineBorder(new Color(168, 168, 168)));
		   jPnWork.setOpaque(true);
		   GroupLayout panelWorkLayout = new GroupLayout(jPnWork);
		   jPnWork.setLayout(panelWorkLayout);
		   panelWorkLayout.setHorizontalGroup(
				   panelWorkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
				   .addGroup(panelWorkLayout.createSequentialGroup()
			       .addComponent(lbHour,javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
			       .addComponent(jpnContent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
			     );
		   		   panelWorkLayout.setVerticalGroup(
		   		   panelWorkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
		   		   .addGroup(panelWorkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
			       .addComponent(lbHour, javax.swing.GroupLayout.DEFAULT_SIZE, getI() * 33, javax.swing.GroupLayout.PREFERRED_SIZE)
			       .addComponent(jpnContent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
			     );
		   		   panelWorkLeft.addComponent(jPnWork);
			   hashtable.put(lbHour.getText(), jpnContent);
	   }
	   jpnContent.add(myLable);

	   jpnContent.setSize(250, 33);
	   jpnContent.setLayout(new GridLayout(jpnContent.getComponentCount(),1, 5,5));
	   }
	   else
	   {
		   JPanel jpnContent = hashtable.get(lbHour.getText());
		   if(jpnContent==null){
			   jpnContent = new JPanel();
			   jpnContent.setBackground(new java.awt.Color(255, 255, 255));
			   jpnContent.setOpaque(true);
			   JPanel jpnHour = new JPanel();
			   jpnHour.setOpaque(false);
			   jpnHour.add(lbHour);
			   jPnWork = new JPanel();
			   jPnWork.setBackground(Color.WHITE);
			   jPnWork.setBorder(BorderFactory.createLineBorder(new Color(168, 168, 168)));
			   jPnWork.setOpaque(true);
			   GroupLayout panelWorkLayout = new GroupLayout(jPnWork);
			   jPnWork.setLayout(panelWorkLayout);
			   panelWorkLayout.setHorizontalGroup(
					   panelWorkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
					   .addGroup(panelWorkLayout.createSequentialGroup()
				       .addComponent(lbHour,javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
				       .addComponent(jpnContent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
				     );
			   		   panelWorkLayout.setVerticalGroup(
			   		   panelWorkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
			   		   .addGroup(panelWorkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
				       .addComponent(lbHour, javax.swing.GroupLayout.DEFAULT_SIZE, getI() * 33, javax.swing.GroupLayout.PREFERRED_SIZE)
				       .addComponent(jpnContent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				     );
			   		   panelWorkRight.addComponent(jPnWork);
				   hashtable.put(lbHour.getText(), jpnContent);
				   
		   }
		   jpnContent.add(myLable);
		   jpnContent.setSize(250, 33);
		   jpnContent.setLayout(new GridLayout(jpnContent.getComponentCount(),1));
	   }
	   lbHour.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	   lbHour.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
	   lbHour.setOpaque(false);
	   lbHour.setBackground(new java.awt.Color(255, 255, 255));
	   myLable.setOpaque(false);
	   if (status == Appointment.Status.UNACCOMPLISHED
	     || status == Appointment.Status.ONGOING) {
	    myLable.setForeground(Color.red);

	   } else {
	    myLable.setForeground(Color.decode("0x00FF00"));
	   }
	   myLable.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	  }

	  panelWorkLeft.updateUI();
	  panelWorkRight.updateUI();
	 } catch (Exception e) {
	  e.printStackTrace();
	  appointments = new ArrayList<Appointment>();
	 }
	 
	}
}
