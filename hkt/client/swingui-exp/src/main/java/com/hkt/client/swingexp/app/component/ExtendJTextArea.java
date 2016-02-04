package com.hkt.client.swingexp.app.component;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTextArea;

public class ExtendJTextArea extends JTextArea {
  public ExtendJTextArea() {
    setFont(new Font("Tahoma", Font.PLAIN, 14));
    setPreferredSize(new Dimension(200, 23));
  }

  public ExtendJTextArea(int rows, int columns) {
    setFont(new Font("Tahoma", Font.PLAIN, 14));
    setPreferredSize(new Dimension(200, 23));
    setRows(rows);
    setColumns(columns);
  }
}
