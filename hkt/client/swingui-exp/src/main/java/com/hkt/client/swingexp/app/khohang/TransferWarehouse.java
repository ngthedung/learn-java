package com.hkt.client.swingexp.app.khohang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.hkt.client.swing.widget.GridBagLayoutPanel;

public class TransferWarehouse extends JPanel {
  public TransferWarehouse() {
    setBackground(Color.WHITE);
    setLayout(new BorderLayout());
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(1, 2));
    panel.add(new LeftPanel());
    panel.add(new RightPanel());
    add(panel, BorderLayout.CENTER);
    add(new ButtonPanel(), BorderLayout.PAGE_END);
  }

  static class ButtonPanel extends JPanel {
    public ButtonPanel() {
      setBackground(Color.WHITE);
      setLayout(new FlowLayout(FlowLayout.RIGHT));
      JButton button1 = new JButton("Điền mới");
      button1.setIcon(new ImageIcon("src/icon/kho/reset1.png"));
      JButton button2 = new JButton("Thêm");
      button2.setIcon(new ImageIcon("src/icon/kho/save1.png"));
      JButton button3 = new JButton("Sửa");
      button3.setIcon(new ImageIcon("src/icon/kho/modify1.png"));
      JButton button4 = new JButton("Xóa");
      button4.setIcon(new ImageIcon("src/icon/kho/delete1.png"));
      JButton button5 = new JButton("Thoát");
      button5.setIcon(new ImageIcon("src/icon/kho/cancel1.png"));
      add(button1);
      add(button2);
      add(button3);
      add(button4);
      add(button5);
    }
  }

  static class LeftPanel extends JPanel {
    public LeftPanel() {
      setBackground(Color.WHITE);
      setLayout(new BorderLayout());
      JPanel panel = new JPanel();
      panel.setBackground(Color.WHITE);
      panel.setLayout(new FlowLayout());
      panel.add(createLabel("Tìm kiếm"));
      panel.add(new JTextField(25));
      panel.add(createLabel("Số lượng"));
      panel.add(new JTextField(5));
      panel.add(createLabel("Đơn giá"));
      panel.add(new JTextField(10));
      add(panel, BorderLayout.PAGE_START);

      JPanel panel2 = new JPanel();
      panel2.setLayout(new GridLayout());
      panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()));
      add(panel2, BorderLayout.CENTER);
      add(new JLabel("    "), BorderLayout.LINE_START);

      panel2.add(new SelectiveProduct());

      panel2.add(new ProductList());
    }

    static class SelectiveProduct extends JPanel {
      public SelectiveProduct() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        GridBagLayoutPanel panelTop = new GridBagLayoutPanel();
        panelTop.setBackground(Color.WHITE);
        panelTop.add(0, 0, createLabel(" "), 2);
        panelTop.add(1, 0, createLabel("Nhóm sản phẩm :"));
        String[] data = { "Nhóm 1", "Nhóm 2", "Nhóm 3" };
        JComboBox comboGroup = new JComboBox(data);
        panelTop.add(1, 1, comboGroup);
        panelTop.add(2, 0, createLabel("Bảng giá :"));
        String[] dataBangGia = { "Bảng 1", "Bảng 2", "Bảng 3" };
        JComboBox comboGia = new JComboBox(dataBangGia);
        panelTop.add(2, 1, comboGia);
        panelTop.add(3, 0, createLabel("Đơn vị tiền :"));
        String[] dataMoney = { "VND", "USD", "EURO" };
        JComboBox comboMoney = new JComboBox(dataMoney);
        panelTop.add(3, 1, comboMoney);
        panelTop.add(4, 0, createLabel(" "), 2);
        add(panelTop, BorderLayout.PAGE_START);

        JPanel panelBot = new JPanel();
        panelBot.setBackground(Color.WHITE);
        panelBot.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()));
        add(panelBot, BorderLayout.CENTER);

      }
    }

    static class ProductList extends JPanel {
      public ProductList() {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Product list"));
      }
    }
  }

  static class RightPanel extends JPanel {
    public RightPanel() {
      setBackground(Color.WHITE);
      setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()));
      setLayout(new BorderLayout());
      add(new Infomation(), BorderLayout.PAGE_START);

      JScrollPane scrollPane = new JScrollPane();
      String[] columnNames = { "Product", "Quantity", "Price", "Total", "Unit Money" };
      Object[][] data = { { "Fried chicken", new Integer(1), new Integer(10), new Integer(10), "USD" },
          { "Teriyaki", new Integer(1), new Integer(10), new Integer(10), "USD" },
          { "Walnut ricotta", new Integer(1), new Integer(10), new Integer(10), "USD" },
          { "Sandwich", new Integer(1), new Integer(10), new Integer(10), "USD" },
          { "Vodka", new Integer(1), new Integer(15), new Integer(15), "USD" }, };
      JTable table = new JTable(data, columnNames);
      scrollPane.setViewportView(table);
      add(scrollPane, BorderLayout.CENTER);
      add(new JLabel("  "), BorderLayout.LINE_START);
      add(new JLabel("    "), BorderLayout.LINE_END);

      JPanel panelMoney = new JPanel();
      panelMoney.setBackground(Color.WHITE);
      panelMoney.setLayout(new FlowLayout(FlowLayout.LEADING));
      panelMoney.add(createLabel("Tổng tiền"));
      JTextField field = new JTextField(42);
      field.setFont(new Font("Tahoma", Font.PLAIN, 14));
      panelMoney.add(field);
      String[] dataMoney = { "VND", "USD", "EURO" };
      JComboBox comboMoney = new JComboBox(dataMoney);
      panelMoney.add(comboMoney);
      add(panelMoney, BorderLayout.PAGE_END);
    }

    static class Infomation extends GridBagLayoutPanel {
      public Infomation() {
        setBackground(Color.WHITE);
        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.WHITE);
        panel1.setLayout(new BorderLayout());
        panel1.add(createLabel("  Mã phiếu "), BorderLayout.LINE_START);
        JTextField textField1 = new JTextField(48);
        textField1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel1.add(textField1, BorderLayout.CENTER);
        panel1.add(new JLabel("   "), BorderLayout.LINE_END);
        add(0, 0, panel1);

        setBackground(Color.WHITE);
        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.WHITE);
        panel2.setLayout(new BorderLayout());
        panel2.add(createLabel("  Tên NV    "), BorderLayout.LINE_START);
        JTextField textField2 = new JTextField(48);
        textField2.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel2.add(textField2, BorderLayout.CENTER);
        panel2.add(new JLabel("   "), BorderLayout.LINE_END);
        add(1, 0, panel2);

        setBackground(Color.WHITE);
        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.WHITE);
        panel3.setLayout(new BorderLayout());
        panel3.add(createLabel("  Kho          "), BorderLayout.LINE_START);
        JTextField textField3 = new JTextField(48);
        textField3.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel3.add(textField3, BorderLayout.CENTER);
        panel3.add(new JLabel("   "), BorderLayout.LINE_END);
        add(2, 0, panel3);

        setBackground(Color.WHITE);
        JPanel panel4 = new JPanel();
        panel4.setBackground(Color.WHITE);
        panel4.setLayout(new BorderLayout());
        panel4.add(createLabel("  Nhân viên"), BorderLayout.LINE_START);
        JTextField textField4 = new JTextField(48);
        textField4.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel4.add(textField4, BorderLayout.CENTER);
        panel4.add(new JLabel("   "), BorderLayout.LINE_END);
        add(3, 0, panel4);

        setBackground(Color.WHITE);
        JPanel panel5 = new JPanel();
        panel5.setBackground(Color.WHITE);
        panel5.setLayout(new BorderLayout());
        panel5.add(createLabel("  Ngày        "), BorderLayout.LINE_START);
        JTextField textField5 = new JTextField(48);
        textField5.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel5.add(textField5, BorderLayout.CENTER);
        panel5.add(new JLabel("   "), BorderLayout.LINE_END);
        add(4, 0, panel5);
      }
    }
  }

  public static JLabel createLabel(String name) {
    JLabel label = new JLabel(name);
    label.setFont(new Font("Tahoma", Font.BOLD, 14));
    return label;
  }

  public static JTextField createTextField() {
    JTextField field = new JTextField();
    field.setFont(new Font("Tahoma", Font.PLAIN, 14));
    return field;
  }
}
