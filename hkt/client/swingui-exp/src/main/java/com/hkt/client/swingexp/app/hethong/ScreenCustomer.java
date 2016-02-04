package com.hkt.client.swingexp.app.hethong;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.component.GridLabelLayoutPabel;

public class ScreenCustomer extends JPanel {
  public ScreenCustomer() {
    setLayout(new BorderLayout());
    JPanel panelAvatar = new JPanel();
    panelAvatar.setLayout(new GridLayout(1, 2));
    JLabel lbAvatar = new JLabel("Avatar", SwingConstants.CENTER);
    lbAvatar.setBorder(BorderFactory.createEtchedBorder());
    panelAvatar.add(new Account());
    panelAvatar.add(lbAvatar);
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    panel.add(panelAvatar, BorderLayout.PAGE_START);
    JPanel panel2 = new JPanel();
    panel2.setLayout(new BorderLayout());
    panel2.add(new BasicInfo(), BorderLayout.PAGE_START);
    panel2.add(new JPanel(),BorderLayout.CENTER);
    panel2.setBackground(Color.WHITE);
    panel.add(panel2, BorderLayout.CENTER);

    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.add(panel, "Thông tin cơ bản");
    tabbedPane.add(new EducationForm(), "Học vấn");
    tabbedPane.add(new WorkForm(), "Kinh nghiệm làm việc");
    tabbedPane.add(new RelationShipForm(), "Quan hệ");
    tabbedPane.add(new ContactForm(), "Thông tin liên lạc");
    add(tabbedPane, BorderLayout.CENTER);

    JButton btSave = new JButton("Save");
    JButton btClear = new JButton("Clear");
    JButton btCancel = new JButton("Cancel");
    JPanel panelControl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    panelControl.add(btSave);
    panelControl.add(btClear);
    panelControl.add(btCancel);
    add(panelControl, BorderLayout.PAGE_END);
  }

  static class Account extends GridLabelLayoutPabel {
    public Account() {
      setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder("Thông tin tài khoản")));
      add(0, 0, new JLabel("Tên đăng nhập : "));
      add(0, 1, new JTextField());
      add(1, 0, new JLabel("Mật khẩu : "));
      add(1, 1, new JTextField());
      add(2, 0, new JLabel("Nhập lại mật khẩu : "));
      add(2, 1, new JTextField());
    }
  }

  static class BasicInfo extends GridLabelLayoutPabel {
    public BasicInfo() {
      setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder("Thông tin cơ bản")));
      add(0, 0, new JLabel("Họ"));
      add(0, 1, new JTextField());
      add(0, 2, new JLabel("Tên"));
      add(0, 3, new JTextField());
      add(1, 0, new JLabel("Giới tính"));
      String[] dataCb = {"Nam", "Nữ"};
      JComboBox comboBox = new JComboBox(dataCb);
      add(1, 1, comboBox);
      add(1, 2, new JLabel("Ngày sinh"));
      add(1, 3, new JTextField());
      add(2, 0, new JLabel("Chiều cao"));
      add(2, 1, new JTextField());
      add(2, 2, new JLabel("Cân nặng"));
      add(2, 3, new JTextField());
      add(3, 0, new JLabel("Chứng minh thư      "));
      add(3, 1, new JTextField());
      String[] maritalStatus = { "Chưa lập gia đình", "Đã có gia đình" };
      add(3, 2, new JLabel("Hôn nhân"));
      add(3, 3, new JComboBox(maritalStatus));
      add(4, 0, new JLabel("Sở thích"));
      add(4, 1, new JTextArea(5, 30), 3);
    }
  }

  static class EducationForm extends JPanel {
    public EducationForm() {
      String[] column_names = { "Header 1", "Header 2", "Header 3", "Header 4", "Header 5" };
      String[][] data = {
          { "Content1", "Content2", "Content3", "Content4", "Content5" },
          { "Content11", "Content22", "Content33", "Content44", "Content55" },
          { "Content111", "Content222", "Content333", "Content444", "Content555" } 
      };
      setLayout(new BorderLayout());
      add(new EduPanel(), BorderLayout.PAGE_START);
      add(new TableData(column_names, data), BorderLayout.CENTER);
    }
  }

  static class EduPanel extends GridLabelLayoutPabel {
    public EduPanel() {
      add(0, 0, new JLabel("Trường"));
      add(0, 1, new JTextField());
      add(0, 2, new JLabel("Ngành học"));
      add(0, 3, new JTextField());
      add(2, 0, new JLabel("Từ"));
      add(2, 1, new JTextField());
      add(2, 2, new JLabel("Loại tốt nghiệp"));
      add(2, 3, new JTextField());
      add(3, 0, new JLabel("Đến"));
      add(3, 1, new JTextField());
      add(3, 2, new JLabel("Ngoại ngữ"));
      add(3, 3, new JTextField());
      add(4, 0, new JLabel("Description"));
      add(4, 1, new JTextArea(5, 50), 3);
      JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      JButton button = new JButton("Add");
      panelButton.add(button);
      add(5, 0, panelButton, 4);
    }
  }

  static class WorkForm extends JPanel {
    public WorkForm() {
      String[] column_names = { "Header 1", "Header 2", "Header 3", "Header 4", "Header 5" };
      String[][] data = {
          { "Content1", "Content2", "Content3", "Content4", "Content5" },
          { "Content11", "Content22", "Content33", "Content44", "Content55" },
          { "Content111", "Content222", "Content333", "Content444", "Content555" } 
      };
      setLayout(new BorderLayout());
      add(new WorkPanel(), BorderLayout.PAGE_START);
      add(new TableData(column_names, data), BorderLayout.CENTER);
    }
  }

  static class WorkPanel extends GridLabelLayoutPabel{
    public WorkPanel() {
      add(0, 0, new JLabel("Công ty"));
      add(0, 1, new JTextField());
      add(0, 2, new JLabel("Từ"));
      add(0, 3, new JTextField());
      add(1, 0, new JLabel("Vị trí"));
      add(1, 1, new JTextField());
      add(1, 2, new JLabel("Đến"));
      add(1, 3, new JTextField());
      add(2, 0, new JLabel("Nội dung"));
      add(2, 1, new JTextArea(5, 50), 3);
      JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      JButton button = new JButton("Add");
      panelButton.add(button);
      add(3, 0, panelButton, 4);
    }
  }

  static class RelationShipForm extends JPanel {
    public RelationShipForm() {
      String[] column_names = { "Header 1", "Header 2", "Header 3", "Header 4", "Header 5" };
      String[][] data = {
          { "Content1", "Content2", "Content3", "Content4", "Content5" },
          { "Content11", "Content22", "Content33", "Content44", "Content55" },
          { "Content111", "Content222", "Content333", "Content444", "Content555" } 
      };
      setLayout(new BorderLayout());
      add(new RelationShipPanel(), BorderLayout.PAGE_START);
      add(new TableData(column_names, data), BorderLayout.CENTER);
    }
  }

  static class RelationShipPanel extends GridLabelLayoutPabel {
    public RelationShipPanel() {
      add(0, 0, new JLabel("Quan hệ"));
      add(0, 1, new JTextField());
      add(0, 2, new JLabel("Giới tính"));
      String[] dataGender = {"Nam", "Nữ"};
      add(0, 3, new JComboBox(dataGender));
      add(1, 0, new JLabel("Tên"));
      add(1, 1, new JTextField());
      add(1, 2, new JLabel("Họ"));
      add(1, 3, new JTextField());
      add(2, 0, new JLabel("Ngày sinh"));
      add(2, 1, new JTextField());
      add(2, 2, new JLabel("Nghề nghiệp"));
      add(2, 3, new JTextField());
      JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      JButton button = new JButton("Add");
      panelButton.add(button);
      add(3, 0, panelButton, 4);
    }
  }

  static class ContactForm extends JPanel {
    public ContactForm() {
      String[] column_names = { "Header 1", "Header 2", "Header 3", "Header 4", "Header 5" };
      String[][] data = { 
        { "Content1", "Content2", "Content3", "Content4", "Content5" },
        { "Content11", "Content22", "Content33", "Content44", "Content55" },
        { "Content111", "Content222", "Content333", "Content444", "Content555" } 
      };
      setLayout(new BorderLayout());
      add(new ContactsPanel(), BorderLayout.PAGE_START);
      add(new TableData(column_names, data), BorderLayout.CENTER);
    }
  }

  static class ContactsPanel extends GridLabelLayoutPabel {
    public ContactsPanel() {
      add(0, 0, new JLabel("Địa chỉ"));
      add(0, 1, new JTextField());
      add(0, 2, new JLabel("Xã/Phường"));
      add(0, 3, new JTextField());
      add(1, 0, new JLabel("Quận/Huyện"));
      add(1, 1, new JTextField());
      add(1, 2, new JLabel("Tỉnh/TP"));
      add(1, 3, new JTextField());
      add(2, 0, new JLabel("Quốc gia"));
      add(2, 1, new JTextField());
      add(2, 2, new JLabel("Điện thoại CĐ"));
      add(2, 3, new JTextField());
      add(3, 0, new JLabel("Điện thoại DĐ"));
      add(3, 1, new JTextField());
      add(3, 2, new JLabel("Fax"));
      add(3, 3, new JTextField());
      add(4, 0, new JLabel("Email"));
      add(4, 1, new JTextField());
      add(4, 2, new JLabel("Website"));
      add(4, 3, new JTextField());
      JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      JButton button = new JButton("Add");
      panelButton.add(button);
      add(5, 0, panelButton, 4);
    }
  }

  static class TableData extends JPanel {
    public TableData(String[] headers, String[][] datas) {
      setLayout(new GridLayout());
      DefaultTableModel table_model = new DefaultTableModel(datas, headers);
      JTable table = new JTable(table_model);
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setViewportView(table);
      add(scrollPane);
    }
  }
}
