package com.hkt.client.swingexp.app.khohang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.hkt.client.swing.widget.GridBagLayoutPanel;

public class GeneralPanel extends JPanel implements IGeneralPanel {

  public GeneralPanel() {
    setLayout(new BorderLayout());
    JPanel panel = new JPanel();
    panel.setBorder(BorderFactory.createTitledBorder("General Panel"));
    panel.setLayout(new BorderLayout());
    panel.add(new panelLeft(), BorderLayout.LINE_START);
    panel.add(new panelRight(), BorderLayout.LINE_END);
    panel.add(new panelBot(), BorderLayout.PAGE_END);

    IGeneralButtonPanel generalButtonPanel = new GeneralPanelButton();
    add(new JLabel("   "), BorderLayout.LINE_START);
    add(new JLabel("   "), BorderLayout.LINE_END);
    add(new JLabel(" "), BorderLayout.PAGE_START);
    add((JPanel) generalButtonPanel, BorderLayout.PAGE_END);
    add(panel, BorderLayout.CENTER);
  }

  static class panelLeft extends GridBagLayoutPanel {
    public panelLeft() {
      setAutoscrolls(true);
      JLabel label1 = createLabel("label 1          ");
      label1.setSize(100, 23);
      add(0, 0, label1);
      JTextField textField1 = createTextField();
      add(0, 1, textField1);
      JLabel label2 = createLabel("label 2");
      add(1, 0, label2);
      JTextField textField2 = createTextField();
      add(1, 1, textField2);
      JLabel label3 = createLabel("label 3");
      add(2, 0, label3);
      JTextField textField3 = createTextField();
      add(2, 1, textField3);
      JLabel label4 = createLabel("label 4");
      add(3, 0, label4);
      JTextField textField4 = createTextField();
      add(3, 1, textField4);
      JLabel label5 = createLabel("label 5");
      add(4, 0, label5);
      JTextField textField5 = createTextField();
      add(4, 1, textField5);
    }
  }

  static class panelRight extends GridBagLayoutPanel {
    public panelRight() {
      setAutoscrolls(true);
      JLabel label1 = createLabel("label 1   ");
      add(0, 0, label1);
      JTextField textField1 = createTextField();
      add(0, 1, textField1);
      JLabel label2 = createLabel("label 2");
      add(1, 0, label2);
      JTextField textField2 = createTextField();
      add(1, 1, textField2);
      JLabel label3 = createLabel("label 3");
      add(2, 0, label3);
      JTextField textField3 = createTextField();
      add(2, 1, textField3);
      JLabel label4 = createLabel("label 4");
      add(3, 0, label4);
      JTextField textField4 = createTextField();
      add(3, 1, textField4);
      JLabel label5 = createLabel("label 5");
      add(4, 0, label5);
      JTextField textField5 = createTextField();
      add(4, 1, textField5);
    }
  }

  static class panelBot extends GridBagLayoutPanel {
    public panelBot() {
      JLabel label = new JLabel("Description");
      setAlignmentY(Component.TOP_ALIGNMENT);
      label.setFont(new Font("Tahoma", Font.BOLD, 14));
      add(0, 0, label);
      add(0, 1, new JTextArea(5, 56));
      JLabel labelErr = new JLabel("Error !!!");
      labelErr.setFont(new Font("Tahoma", Font.PLAIN, 14));
      labelErr.setForeground(Color.RED);
      add(1, 0, labelErr, 2);
    }
  }

  static JLabel createLabel(String name) {
    JLabel label = new JLabel(name);
    label.setFont(new Font("Tahoma", Font.BOLD, 14));
    return label;
  }

  static JTextField createTextField() {
    JTextField textField = new JTextField(22);
    textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
    return textField;
  }

  @Override
  public void load() {
    // TODO Auto-generated method stub

  }

}
