package com.hkt.client.swingexp.app.nhansu.dailywork;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.nhansu.PanelToDoCalendar;
import com.hkt.module.hr.entity.Appointment;

public class MyLableDay extends JLabel {
	private Appointment appointment;
	private String idemployee;
	private PanelWorkOfDay panelWorkOfDay;
	private CalendarAppointment c;

	

	public void setPanelWorkOfDay(PanelWorkOfDay panelWorkOfDay) {
		this.panelWorkOfDay = panelWorkOfDay;
	}


	public MyLableDay(final Appointment appointment) {
		super();
		setText(appointment.getName());
		this.appointment = appointment;
		setFont(new java.awt.Font("Tahoma", 0, 14));
		setPreferredSize(new Dimension(250, 25));
		setSize(250, 25);
		// hiển thị đầy đủ thông tin
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					PanelToDoCalendar panelToDoCalendar = new PanelToDoCalendar(
							true);
					panelToDoCalendar.setData(appointment);
					DialogMain main = new DialogMain(panelToDoCalendar);
					main.setModal(true);
					main.setLocationRelativeTo(null);
					main.showButton(false);
					main.setVisible(true);
					panelWorkOfDay.load();
				} catch (Exception ex) {
				}
			}
		});
	}
	
	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}
}
