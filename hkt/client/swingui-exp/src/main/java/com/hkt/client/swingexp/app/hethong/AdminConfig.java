package com.hkt.client.swingexp.app.hethong;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.component.GridLabelLayoutPabel;
import com.hkt.client.swingexp.app.core.HKTFile;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.MyGroupLayout;

public class AdminConfig extends JPanel implements IDialogResto {
  private JTextField txtName, txtPass;
  private JTextField txtServerName;

  public AdminConfig() {
    setOpaque(false);
    setLayout(new BorderLayout());

    InputPanel inputPanel = new InputPanel();
    inputPanel.setOpaque(false);
    add(inputPanel, BorderLayout.CENTER);

  }

  private class InputPanel extends JPanel {
    public InputPanel() {
      MyGroupLayout groupLayout = new MyGroupLayout(this);
      groupLayout.add(0, 0, new JLabel("Tài khoản"));
      txtName = new JTextField();
      txtName.setFont(new Font("Tahoma", Font.PLAIN, 14));
      groupLayout.add(0, 1, txtName);
      
      groupLayout.add(1, 0, new JLabel("Mật khẩu"));
      
      txtServerName = new JTextField();
      txtServerName.setFont(new Font("Tahoma", Font.PLAIN, 14));
      groupLayout.add(1, 1, txtServerName);
      
      groupLayout.add(2, 0, new JLabel("Xác nhận"));
      txtPass = new JTextField();
      txtPass.setFont(new Font("Tahoma", Font.PLAIN, 14));
      groupLayout.add(2, 1, txtPass);
      groupLayout.updateGui();
    }
  }

  private void writeData(String str) {
    try {
      FileOutputStream fi = new FileOutputStream(HKTFile.getFile("Database", "admin"));
      ObjectOutputStream of = new ObjectOutputStream(fi);
      of.writeObject(str);
      of.reset();
      of.close();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }

  }

  private String readData() {
    try {
      FileInputStream fi = new FileInputStream(HKTFile.getFile("Database", "admin"));
      ObjectInputStream of = new ObjectInputStream(fi);
      String str = of.readObject().toString();
      of.close();
      return str;
    } catch (Exception e) {
      return "";
    }
  }


  @Override
  public void Save() throws Exception {
    try {
      String str = txtName.getText()+"/"+txtServerName.getText();
      writeData(str);
      ((JDialog)getRootPane().getParent()).dispose();
  } catch (Exception e) {
      e.printStackTrace();
  }

  }
}
