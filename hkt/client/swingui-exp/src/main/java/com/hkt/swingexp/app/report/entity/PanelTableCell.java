package com.hkt.swingexp.app.report.entity;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelTableCell extends JPanel{
	
	private JLabel icon;
	private JLabel text;
  private Object object;
  private int row;
  
  public PanelTableCell() {
  	this.setBackground(Color.white);
  	this.setOpaque(true);
		this.setLayout(new BorderLayout(5, 0));
		
		text = new JLabel();
		icon = new JLabel();
		text.setFont(new Font("Tahome", Font.PLAIN, 14));	
		
		this.add(icon, BorderLayout.LINE_START);
		this.add(text, BorderLayout.CENTER);
	}

  public int getRow() {
    return row;
  }

  public void setRow(int row) {
    this.row = row;
  }

  public Object getObject() {
    return object;
  }

  public void setObject(Object object) {
    this.object = object;
  }

	public JLabel getIcon() {
		return icon;
	}

	public void setIcon(JLabel icon) {
		this.icon = icon;
		this.removeAll();
		this.add(icon, BorderLayout.LINE_START);
		this.add(text, BorderLayout.CENTER);
	}

	public JLabel getText() {
		return text;
	}

	public void setText(JLabel text) {
		this.text = text;
	}
  
}
