package com.hkt.client.swingexp.app.nhansu.dailywork;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.nhansu.PanelToDoCalendar;
import com.hkt.module.hr.entity.Appointment;

public class MyLableWeek extends JLabel {
	private Appointment appointment;
	private int h, m;
	private PanelWorkOfWeek panelWorkOfWeek;

	public void setPanelWorkOfWeek(PanelWorkOfWeek panelWorkOfWeek) {
		this.panelWorkOfWeek = panelWorkOfWeek;
	}

	public MyLableWeek(final Appointment appointment, int h, int m) {
		super();
		this.appointment = appointment;
		this.h = h;
		this.m = m;
		setFont(new java.awt.Font("Tahoma", 0, 14));
		if (h < 10 && m < 10) {
			setText("0" + h + ":0" + m + " | " + appointment.getName());
		} else if (h > 10 && m < 10) {
			setText("" + h + ":0" + m + " | " + appointment.getName());
		} else if (h < 10 && m > 10) {
			setText("0" + h + ":" + m + " | " + appointment.getName());
		}

		else {
			setText("" + h + ":" + m + " | " + appointment.getName());
		}
		// hiển thị đầy đủ thông tin khi kick vào công việc
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					PanelToDoCalendar panelToDoCalendar = new PanelToDoCalendar(
							false);
					panelToDoCalendar.setData(appointment);
					DialogMain main = new DialogMain(panelToDoCalendar);
					main.setTitle("Công việc hàng ngày");
					main.setModal(true);
					main.setLocationRelativeTo(null);
					main.showButton(false);
					main.setVisible(true);
					panelWorkOfWeek.load();
				} catch (Exception ex) {
					ex.printStackTrace();
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
