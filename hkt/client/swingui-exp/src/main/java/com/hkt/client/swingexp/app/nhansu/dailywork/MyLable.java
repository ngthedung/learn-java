package com.hkt.client.swingexp.app.nhansu.dailywork;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.nhansu.PanelToDoCalendar;
import com.hkt.module.hr.entity.Appointment;

public class MyLable extends JLabel {
	private Appointment appointment;
	private PanelDayOfMonth dayOfMonth;
	private CalendarAppointment c;

	public void setDayOfMonth(PanelDayOfMonth dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}



	public MyLable(final Appointment appointment) {
		super();
		setText(appointment.getName());
		this.appointment = appointment;
		setFont(new java.awt.Font("Tahoma", 0, 14));
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
					dayOfMonth.load();
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
