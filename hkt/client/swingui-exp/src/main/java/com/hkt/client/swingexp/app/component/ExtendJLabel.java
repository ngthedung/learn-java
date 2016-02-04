package com.hkt.client.swingexp.app.component;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

public class ExtendJLabel extends JLabel {
	
	public ExtendJLabel() {
    init();
	}
	
  public ExtendJLabel(String text) {
    setText(text);
    init();
  }
  
  public ExtendJLabel(String text, int horizontalAlignment) {
    setText(text);
    setHorizontalAlignment(horizontalAlignment);
    init();
  }
  
  public void init(){
    setFont(new Font("Tahoma", Font.BOLD, 14));
    setPreferredSize(new Dimension(86, 23));
    setSize(86, 23);
  }
}
