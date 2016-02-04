package com.hkt.client.swingexp.app.component;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GridLabelLayoutPabel extends JPanel {

  private GridBagConstraints constraint;

  public GridLabelLayoutPabel() {
    setLayout(new GridBagLayout());
    constraint = new GridBagConstraints();
    constraint.fill = GridBagConstraints.HORIZONTAL;
    constraint.insets = new Insets(2, 2, 2, 2);

  }

  public GridLabelLayoutPabel(GridBagConstraints constraint) {
    setLayout(new GridBagLayout());
    this.constraint = constraint;
  }

  public void add(int row, int col, Component comp) {
    constraint.gridx = col;
    constraint.gridy = row;
    constraint.weightx = 1;
    constraint.gridwidth = 1;
    if (comp instanceof JLabel) {
      constraint.weightx = 0;
      JLabel label = ((JLabel) comp);
      label.setFont(new Font("Tahoma", Font.BOLD, 14));
      add(label, constraint);
    } else {
      comp.setFont(new Font("Tahoma", Font.BOLD, 14));
      add(comp, constraint);
    }
  }

  public void add(int row, int col, String label) {
    constraint.gridx = col;
    constraint.gridy = row;
    constraint.weightx = 0;
    constraint.gridwidth = 1;
    JLabel label2 = new JLabel(label);
    label2.setFont(new Font("Tahoma", Font.BOLD, 14));
    add(label2, constraint);
  }

  public void add(int row, int col, Component comp, int colspan) {
    constraint.gridx = col;
    constraint.gridy = row;
    constraint.gridwidth = colspan;
    constraint.weightx = 1;
    if (comp.getClass().equals("class javax.swing.JLabel")) {
      ((JLabel) comp).setFont(new Font("Tahoma", Font.BOLD, 14));
    }
    if (comp.getClass().equals("class javax.swing.JTextField")) {
      ((JTextField) comp).setFont(new Font("Tahoma", Font.BOLD, 14));
    }
    add(comp, constraint);
  }

  public void createBorder() {
    setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()));
  }

  public void createBorder(String title) {
    setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title));
  }
}
