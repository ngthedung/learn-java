package com.hkt.client.swingexp.app.component;

import java.awt.Font;

import com.toedter.calendar.JDateChooser;

public class ExtendJDateChooser extends JDateChooser {
  public ExtendJDateChooser() {
    setFont(new Font("Tahoma", 0, 14));
    setDateFormatString("dd/MM/yyyy");
  }
}
