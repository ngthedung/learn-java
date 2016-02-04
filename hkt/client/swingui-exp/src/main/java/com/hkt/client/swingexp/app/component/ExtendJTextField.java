package com.hkt.client.swingexp.app.component;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTextField;

public class ExtendJTextField extends JTextField {
  public ExtendJTextField() {
    setFont(new Font("Tahoma", Font.PLAIN, 14));
    setPreferredSize(new Dimension(200, 23));
  }

  public ExtendJTextField(int columns) {
    setColumns(columns);
    setPreferredSize(new Dimension(200, 23));
    setFont(new Font("Tahoma", Font.PLAIN, 14));
  }
  
  public ExtendJTextField(String str) {
	  	setText(str);
	    setFont(new Font("Tahoma", Font.PLAIN, 14));
	    setPreferredSize(new Dimension(200, 23));
	  }

}
