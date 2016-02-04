package com.hkt.client.swingexp.app.hethong;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.hkt.client.swing.robot.console.JVMEnv;
import com.hkt.client.swing.robot.console.ShellConsole;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.component.TableFillterSorter;
import com.hkt.client.swingexp.app.core.FrameUI;
import com.hkt.client.swingexp.app.core.HKTFile;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDialogPlus;
import com.hkt.client.swingexp.app.core.MyFrame;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.test.ScriptRunnerExp;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.client.swingexp.homescreen.HomeScreen.MenuLeft;
import com.hkt.client.swingexp.homescreen.HomeScreen.MenuLeft.MenuLeftTop;
import com.hkt.client.swingexp.homescreen.MenuRightBanHang;
import com.hkt.client.swingexp.homescreen.MenuRightHeThong;
import com.hkt.client.swingexp.homescreen.MenuRightKhachHangDoiTac;
import com.hkt.client.swingexp.homescreen.MenuRightKhoHangHoa;
import com.hkt.client.swingexp.homescreen.MenuRightKhuVucBanHang;
import com.hkt.client.swingexp.homescreen.MenuRightKhuyenMai;
import com.hkt.client.swingexp.homescreen.MenuRightNhanSu;
import com.hkt.client.swingexp.homescreen.MenuRightThuChiMuaHang;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.GenericOptionModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.PromotionModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.SupplierModelManager;
import com.hkt.client.swingexp.model.WarehouseModelManager;
import com.hkt.util.IOUtil;

public class PanelTestAll extends JPanel implements IDialogResto {

  private DefaultTableModel model;
  private List<String> menus = new ArrayList<String>();
  private JTable jTable1;
  private HashMap<String, String> hashName = new HashMap<String, String>();
  private List<Test> listRun = new ArrayList<Test>();
  private TableFillterSorter tableFillterSorter;
  private JComboBox cbSearch;
  private JTextField txtSearch;
  public static boolean runAll = false;

  public PanelTestAll() {
    String[] header = { "Menu", "Tên giao diện", "Cấp độ", "Chạy theo lựa chọn" };
    model = new DefaultTableModel(header, 0) {

      boolean[] canEdit = new boolean[] { false, false, false, true };

      @Override
      public boolean isCellEditable(int row, int column) {
        return canEdit[column];
      }
    };
    try {
      HomeScreen hom = new HomeScreen();
      MenuLeft men = (MenuLeft) hom.getComponent(1);
      MenuLeftTop menuRightTop = (MenuLeftTop) men.getComponent(0);
      for (int i = 0; i < menuRightTop.getComponentCount(); i++) {
        menus.add(((JButton) menuRightTop.getComponent(i)).getText().trim());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    Object[] row1 = { menus.get(0), "", "", false };
    model.addRow(row1);
    try {
      MenuRightBanHang.MenuRightTop menuRightTop = MenuRightBanHang.MenuRightTop.class.newInstance();
      for (int i = 0; i < menuRightTop.getComponentCount(); i++) {
        JButton btn = (JButton) menuRightTop.getComponent(i);
        if (btn.getActionCommand().length() == 1) {
          Object[] row = { "", getPlainText(btn.getText()), btn.getActionCommand(), false };
          model.addRow(row);
          hashName.put(getPlainText(((JButton) menuRightTop.getComponent(i)).getText()), menuRightTop.getComponent(i)
              .getName());
        }
      }
      MenuRightBanHang.MenuRightUnder menuRightUnder = MenuRightBanHang.MenuRightUnder.class.newInstance();
      for (int i = 0; i < menuRightUnder.getComponentCount(); i++) {
        JButton btn = (JButton) menuRightUnder.getComponent(i);
        if (btn.getActionCommand().length() == 1) {
          Object[] row = { "", getPlainText(btn.getText()), btn.getActionCommand(), false };
          model.addRow(row);
          hashName.put(getPlainText(((JButton) menuRightUnder.getComponent(i)).getText()),
              menuRightUnder.getComponent(i).getName());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    Object[] row2 = { menus.get(1), "", "" };
    model.addRow(row2);
    try {
      MenuRightThuChiMuaHang.MenuRightTop menuRightTop = MenuRightThuChiMuaHang.MenuRightTop.class.newInstance();
      for (int i = 0; i < menuRightTop.getComponentCount(); i++) {
        JButton btn = (JButton) menuRightTop.getComponent(i);
        if (btn.getActionCommand().length() == 1) {
          Object[] row = { "", getPlainText(btn.getText()), btn.getActionCommand(), false };
          model.addRow(row);
          hashName.put(getPlainText(((JButton) menuRightTop.getComponent(i)).getText()), menuRightTop.getComponent(i)
              .getName());
        }
      }
      MenuRightThuChiMuaHang.MenuRightUnder menuRightUnder = MenuRightThuChiMuaHang.MenuRightUnder.class.newInstance();
      for (int i = 0; i < menuRightUnder.getComponentCount(); i++) {
        JButton btn = (JButton) menuRightUnder.getComponent(i);
        if (btn.getActionCommand().length() == 1) {
          Object[] row = { "", getPlainText(btn.getText()), btn.getActionCommand(), false };
          model.addRow(row);
          hashName.put(getPlainText(((JButton) menuRightUnder.getComponent(i)).getText()),
              menuRightUnder.getComponent(i).getName());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    Object[] row3 = { menus.get(2), "", "" };
    model.addRow(row3);
    try {
      MenuRightKhuyenMai.MenuRightTop menuRightTop = MenuRightKhuyenMai.MenuRightTop.class.newInstance();
      for (int i = 0; i < menuRightTop.getComponentCount(); i++) {
        JButton btn = (JButton) menuRightTop.getComponent(i);
        if (btn.getActionCommand().length() == 1) {
          Object[] row = { "", getPlainText(btn.getText()), btn.getActionCommand(), false };
          model.addRow(row);
          hashName.put(getPlainText(((JButton) menuRightTop.getComponent(i)).getText()), menuRightTop.getComponent(i)
              .getName());
        }
      }
      MenuRightKhuyenMai.MenuRightUnder menuRightUnder = MenuRightKhuyenMai.MenuRightUnder.class.newInstance();
      for (int i = 0; i < menuRightUnder.getComponentCount(); i++) {
        JButton btn = (JButton) menuRightUnder.getComponent(i);
        if (btn.getActionCommand().length() == 1) {
          Object[] row = { "", getPlainText(btn.getText()), btn.getActionCommand(), false };
          model.addRow(row);
          hashName.put(getPlainText(((JButton) menuRightUnder.getComponent(i)).getText()),
              menuRightUnder.getComponent(i).getName());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    Object[] row4 = { menus.get(3), "", "" };
    model.addRow(row4);
    try {
      MenuRightKhoHangHoa.MenuRightTop menuRightTop = MenuRightKhoHangHoa.MenuRightTop.class.newInstance();
      for (int i = 0; i < menuRightTop.getComponentCount(); i++) {
        JButton btn = (JButton) menuRightTop.getComponent(i);
        if (btn.getActionCommand().length() == 1) {
          Object[] row = { "", getPlainText(btn.getText()), btn.getActionCommand(), false };
          model.addRow(row);
          hashName.put(getPlainText(((JButton) menuRightTop.getComponent(i)).getText()), menuRightTop.getComponent(i)
              .getName());
        }
      }
      MenuRightKhoHangHoa.MenuRightUnder menuRightUnder = MenuRightKhoHangHoa.MenuRightUnder.class.newInstance();
      for (int i = 0; i < menuRightUnder.getComponentCount(); i++) {
        JButton btn = (JButton) menuRightUnder.getComponent(i);
        if (btn.getActionCommand().length() == 1) {
          Object[] row = { "", getPlainText(btn.getText()), btn.getActionCommand(), false };
          model.addRow(row);
          hashName.put(getPlainText(((JButton) menuRightUnder.getComponent(i)).getText()),
              menuRightUnder.getComponent(i).getName());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    Object[] row5 = { menus.get(4), "", "" };
    model.addRow(row5);
    try {
      MenuRightKhachHangDoiTac.MenuRightTop menuRightTop = MenuRightKhachHangDoiTac.MenuRightTop.class.newInstance();
      for (int i = 0; i < menuRightTop.getComponentCount(); i++) {
        JButton btn = (JButton) menuRightTop.getComponent(i);
        if (btn.getActionCommand().length() == 1) {
          Object[] row = { "", getPlainText(btn.getText()), btn.getActionCommand(), false };
          model.addRow(row);
          hashName.put(getPlainText(((JButton) menuRightTop.getComponent(i)).getText()), menuRightTop.getComponent(i)
              .getName());
        }
      }
      MenuRightKhachHangDoiTac.MenuRightUnder menuRightUnder = MenuRightKhachHangDoiTac.MenuRightUnder.class
          .newInstance();
      for (int i = 0; i < menuRightUnder.getComponentCount(); i++) {
        JButton btn = (JButton) menuRightUnder.getComponent(i);
        if (btn.getActionCommand().length() == 1) {
          Object[] row = { "", getPlainText(btn.getText()), btn.getActionCommand(), false };
          model.addRow(row);
          hashName.put(getPlainText(((JButton) menuRightUnder.getComponent(i)).getText()),
              menuRightUnder.getComponent(i).getName());
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    Object[] row6 = { menus.get(5), "", "" };
    model.addRow(row6);
    try {
      MenuRightNhanSu.MenuRightTop menuRightTop = MenuRightNhanSu.MenuRightTop.class.newInstance();
      for (int i = 0; i < menuRightTop.getComponentCount(); i++) {
        JButton btn = (JButton) menuRightTop.getComponent(i);
        if (btn.getActionCommand().length() == 1) {
          Object[] row = { "", getPlainText(btn.getText()), btn.getActionCommand(), false };
          model.addRow(row);
          hashName.put(getPlainText(((JButton) menuRightTop.getComponent(i)).getText()), menuRightTop.getComponent(i)
              .getName());
        }
      }
      MenuRightNhanSu.MenuRightUnder menuRightUnder = MenuRightNhanSu.MenuRightUnder.class.newInstance();
      for (int i = 0; i < menuRightUnder.getComponentCount(); i++) {
        JButton btn = (JButton) menuRightUnder.getComponent(i);
        if (btn.getActionCommand().length() == 1) {
          Object[] row = { "", getPlainText(btn.getText()), btn.getActionCommand(), false };
          model.addRow(row);
          hashName.put(getPlainText(((JButton) menuRightUnder.getComponent(i)).getText()),
              menuRightUnder.getComponent(i).getName());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    Object[] row7 = { menus.get(6), "", "" };
    model.addRow(row7);
    try {
      MenuRightKhuVucBanHang.MenuRightTop menuRightTop = MenuRightKhuVucBanHang.MenuRightTop.class.newInstance();
      for (int i = 0; i < menuRightTop.getComponentCount(); i++) {
        JButton btn = (JButton) menuRightTop.getComponent(i);
        if (btn.getActionCommand().length() == 1) {
          Object[] row = { "", getPlainText(btn.getText()), btn.getActionCommand(), false };
          model.addRow(row);
          hashName.put(getPlainText(((JButton) menuRightTop.getComponent(i)).getText()), menuRightTop.getComponent(i)
              .getName());
        }
      }
      MenuRightKhuVucBanHang.MenuRightUnder menuRightUnder = MenuRightKhuVucBanHang.MenuRightUnder.class.newInstance();
      for (int i = 0; i < menuRightUnder.getComponentCount(); i++) {
        JButton btn = (JButton) menuRightUnder.getComponent(i);
        if (btn.getActionCommand().length() == 1) {
          Object[] row = { "", getPlainText(btn.getText()), btn.getActionCommand(), false };
          model.addRow(row);
          hashName.put(getPlainText(((JButton) menuRightUnder.getComponent(i)).getText()),
              menuRightUnder.getComponent(i).getName());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    Object[] row8 = { menus.get(7), "", "" };
    model.addRow(row8);
    try {
      MenuRightHeThong.MenuRightTop menuRightTop = MenuRightHeThong.MenuRightTop.class.newInstance();
      for (int i = 0; i < menuRightTop.getComponentCount(); i++) {
        JButton btn = (JButton) menuRightTop.getComponent(i);
        if (btn.getActionCommand().length() == 1) {
          Object[] row = { "", getPlainText(btn.getText()), btn.getActionCommand(), false };
          model.addRow(row);
          hashName.put(getPlainText(((JButton) menuRightTop.getComponent(i)).getText()), menuRightTop.getComponent(i)
              .getName());
        }
      }
      MenuRightHeThong.MenuRightUnder menuRightUnder = MenuRightHeThong.MenuRightUnder.class.newInstance();
      for (int i = 0; i < menuRightUnder.getComponentCount(); i++) {
        JButton btn = (JButton) menuRightUnder.getComponent(i);
        if (btn.getActionCommand().length() == 1) {
          Object[] row = { "", getPlainText(btn.getText()), btn.getActionCommand(), false };
          model.addRow(row);
          hashName.put(getPlainText(((JButton) menuRightUnder.getComponent(i)).getText()),
              menuRightUnder.getComponent(i).getName());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    JScrollPane scrollPane = new JScrollPane();
    jTable1 = new JTable();
    jTable1.setModel(model);
    jTable1.setFont(new Font("Tahoma", Font.PLAIN, 14));
    jTable1.setRowHeight(23);
    JCheckBox chBox = new JCheckBox();
    chBox.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        JCheckBox ch = (JCheckBox) e.getSource();
        if (ch.isSelected()) {
          try {
            Test test = new Test();
            test.setName(jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString());
            test.setIndex(Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 2).toString()));
            listRun.add(test);
          } catch (Exception e2) {
            e2.printStackTrace();
            ch.setSelected(false);
          }
        } else {
          try {
            int k = Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 2).toString());
            for (int i = 0; i < listRun.size();) {
              if (listRun.get(i).getIndex() == k) {
                listRun.remove(i);
              } else {
                i++;
              }
            }
          } catch (Exception e2) {
            e2.printStackTrace();
          }
        }
      }
    });
    chBox.setHorizontalAlignment(JLabel.CENTER);
    jTable1.getColumn("Chạy theo lựa chọn").setCellEditor(new DefaultCellEditor(chBox));
    jTable1.getColumn("Chạy theo lựa chọn").setCellRenderer(new TableCellRenderer() {

      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
          int row, int column) {
        JCheckBox chb = new JCheckBox();
        int k = JLabel.CENTER;
        chb.setHorizontalAlignment(k);
        try {
          if (value != null) {
            chb.setSelected(Boolean.valueOf(value.toString()));
          }else {
            chb.setSelected(false);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
        return chb;
      }
    });
    tableFillterSorter = new TableFillterSorter(jTable1);
    tableFillterSorter.createTableSorter();
    tableFillterSorter.createTableFillter();
    cbSearch = new JComboBox();
    cbSearch.setModel(new DefaultComboBoxModel(tableFillterSorter.getHashtable().keySet().toArray()));
    scrollPane.setViewportView(jTable1);
    JPanel panel = new JPanel();
    panel.setOpaque(false);
    panel.setLayout(new BorderLayout(10, 10));
    JLabel label = new JLabel("Tìm kiếm");
    panel.add(label, BorderLayout.WEST);
    txtSearch = new JTextField();
    txtSearch.setPreferredSize(new Dimension(200, 23));
    txtSearch.setSize(200, 23);
    txtSearch.addCaretListener(new CaretListener() {

      @Override
      public void caretUpdate(CaretEvent e) {
        tableFillterSorter.fillter(txtSearch.getText(), cbSearch.getSelectedItem().toString());
      }
    });
    panel.add(txtSearch, BorderLayout.CENTER);
    panel.add(cbSearch, BorderLayout.EAST);
    JPanel panel2 = new JPanel();
    panel2.setLayout(new GridLayout(1, 2));
    panel2.add(new JLabel());
    panel2.add(panel);
    panel2.setOpaque(false);
    MyGroupLayout groupLayout = new MyGroupLayout(this);
    groupLayout.add(0, 0, new JLabel());
    groupLayout.add(0, 1, panel2);
    groupLayout.add(1, 0, scrollPane);
    groupLayout.updateGui();
  }

  public String getPlainText(String html) {
    String htmlBody = html.replaceAll("<br>", " ");
    htmlBody = htmlBody.replaceAll("</br>", "");
    String plainTextBody = htmlBody.replaceAll("<[^<>]+>([^<>]*)<[^<>]+>", "$1");
    return plainTextBody;
  }

  @Override
  public void Save() throws Exception {
    if (listRun.isEmpty()) {
      MyPanel.isTest = true;
      ((JDialog) getRootPane().getParent()).dispose();

      Thread thread = new Thread() {
        public void run() {
          String value = jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString();
          System.out.println("Bắt đầu chạy test giao diện " +  hashName.get(value).toString());
          System.out.println(new JVMEnv().getMemoryInfo());
          FrameUI frameUI = FrameUI.getInstance();
          try {
            ScriptRunnerExp ScriptRunnerExp = new ScriptRunnerExp(frameUI);
          
            File file = HKTFile.getFile("test", hashName.get(value).toString() + ".js");
            InputStream is;
            if (file.exists()) {
              is = new FileInputStream(file);
            } else {
              is = ScriptRunnerExp.class.getResourceAsStream(hashName.get(value).toString() + ".js");
            }

            String script = IOUtil.getStreamContentAsString(is, "UTF-8");
            ScriptRunnerExp.eval(script);
            MyPanel.isTest = false;
            System.out.println(new JVMEnv().getMemoryInfo());
            System.out.println("...........................................................");
          } catch (Exception ex) {
            MyPanel.isTest = false;
            frameUI.destroy();
            ex.printStackTrace();
          } finally {

          }
        }
      };
      thread.start();
    } else {
      MyPanel.isTest = true;
      Collections.sort(listRun, new Comparator<Test>() {
        @Override
        public int compare(Test it1, Test it2) {
          if (it1.getIndex() > it2.getIndex()) {
            return -1;
          } else {
            if (it1.getIndex() < it2.getIndex()) {
              return 1;
            } else {
              return 0;
            }
          }

        }
      });
      ((JDialog) getRootPane().getParent()).dispose();
      for (int i = 0; i < listRun.size(); i++) {
        final int k = i;
        Thread thread = new Thread() {
          public void run() {
            String value = jTable1.getValueAt(k, 1).toString();
            System.out.println("Bắt đầu chạy test giao diện " +  hashName.get(value).toString());
            System.out.println(new JVMEnv().getMemoryInfo());
            FrameUI frameUI = FrameUI.getInstance();
            try {
              ScriptRunnerExp ScriptRunnerExp = new ScriptRunnerExp(frameUI);
              File file = HKTFile.getFile("test", hashName.get(listRun.get(k).getName()).toString() + ".js");
              InputStream is;
              if (file.exists()) {
                is = new FileInputStream(file);
              } else {
                is = ScriptRunnerExp.class.getResourceAsStream(hashName.get(listRun.get(k).getName()).toString()
                    + ".js");
              }

              String script = IOUtil.getStreamContentAsString(is, "UTF-8");
              ScriptRunnerExp.eval(script);
              System.out.println(new JVMEnv().getMemoryInfo());
              System.out.println("...........................................................");
              if (k < listRun.size() - 1) {
                MyPanel.isTest = true;
              } else {
                MyPanel.isTest = false;
              }
            } catch (Exception ex) {
              MyPanel.isTest = false;
              frameUI.destroy();
             // ex.printStackTrace();
              if (k < listRun.size() - 1) {
                MyPanel.isTest = true;
              } else {
                MyPanel.isTest = false;
              }
            } finally {
            }
          }
        };
        thread.start();
      }
    }

  }

  // private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {
  // int rowSelect = jTable1.getSelectedRow();
  // int index = jTable1.getSelectedRow() + 1;
  // if (jTable1.getSelectedColumn() == 0) {
  // if (index == jTable1.getRowCount() || !jTable1.getValueAt(rowSelect + 1,
  // 0).toString().trim().isEmpty()) {
  // for (int i = 0; i < hashMap.get(jTable1.getValueAt(rowSelect,
  // 0).toString()).size(); i++) {
  // Object[] row = { "", hashMap.get(jTable1.getValueAt(rowSelect,
  // 0).toString()).get(i), 1 };
  // model.insertRow(index, row);
  // index++;
  // }
  // } else {
  // while (jTable1.getValueAt(rowSelect + 1, 0).toString().trim().isEmpty()) {
  // model.removeRow(rowSelect + 1);
  // }
  // }
  // }
  // }

  public JButton getBtnEdit() {
    JButton btnEdit = new JButton("Tất cả");
    btnEdit.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/iconOk.png")));
    btnEdit.addMouseListener(new MouseEventClickButtonDialogPlus("modify1.png", "modify2.png", "modify3.png"));
    btnEdit.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        MyPanel.isTest = true;
        ((JDialog) getRootPane().getParent()).dispose();
        
        for (int i = 0; i < jTable1.getRowCount(); i++) {
          final int k = i;
          if (jTable1.getValueAt(i, 2).toString().equals("1")) {
            Thread thread = new Thread() {
              public void run() {
                String value = jTable1.getValueAt(k, 1).toString();
                System.out.println("Bắt đầu chạy test giao diện " +  hashName.get(value).toString());
                System.out.println(new JVMEnv().getMemoryInfo());
                FrameUI frameUI = FrameUI.getInstance();
                try {
                  ScriptRunnerExp ScriptRunnerExp = new ScriptRunnerExp(frameUI);
                  File file = HKTFile.getFile("test", hashName.get(value).toString() + ".js");
                  InputStream is;
                  if (file.exists()) {
                    is = new FileInputStream(file);
                  } else {
                    is = ScriptRunnerExp.class.getResourceAsStream(hashName.get(value).toString()
                        + ".js");
                  }

                  String script = IOUtil.getStreamContentAsString(is, "UTF-8");
                  ScriptRunnerExp.eval(script);
                  System.out.println(new JVMEnv().getMemoryInfo());
                  System.out.println("...........................................................");
                } catch (Exception ex) {
                  MyPanel.isTest = false;
                  frameUI.destroy();
                //  ex.printStackTrace();
                  
                } finally {
                }
              }
            };
            thread.start();
          }
        }

        for (int i = 0; i < jTable1.getRowCount(); i++) {
          if (jTable1.getValueAt(i, 2).toString().equals("2")) {
            final int k = i;
            Thread thread = new Thread() {
              public void run() {
                String value = jTable1.getValueAt(k, 1).toString();
                System.out.println("Bắt đầu chạy test giao diện " +  hashName.get(value).toString());
                System.out.println(new JVMEnv().getMemoryInfo());
                FrameUI frameUI = FrameUI.getInstance();
                try {
                  ScriptRunnerExp ScriptRunnerExp = new ScriptRunnerExp(frameUI);
                  File file = HKTFile.getFile("test", hashName.get(value).toString() + ".js");
                  InputStream is;
                  if (file.exists()) {
                    is = new FileInputStream(file);
                  } else {
                    is = ScriptRunnerExp.class.getResourceAsStream(hashName.get(value).toString()
                        + ".js");
                  }

                  String script = IOUtil.getStreamContentAsString(is, "UTF-8");
                  ScriptRunnerExp.eval(script);
                  System.out.println(new JVMEnv().getMemoryInfo());
                  System.out.println("...........................................................");
                } catch (Exception ex) {
                  MyPanel.isTest = false;
                  frameUI.destroy();
                //  ex.printStackTrace();
                } finally {
                }
              }
            };
            thread.start();
          }
        }

        for (int i = 0; i < jTable1.getRowCount(); i++) {
          if (jTable1.getValueAt(i, 2).toString().equals("3")) {
            final int k = i;
            Thread thread = new Thread() {
              public void run() {
                String value = jTable1.getValueAt(k, 1).toString();
                System.out.println("Bắt đầu chạy test giao diện " +  hashName.get(value).toString());
                System.out.println(new JVMEnv().getMemoryInfo());
                FrameUI frameUI = FrameUI.getInstance();
                try {
                  File file = HKTFile.getFile("test", hashName.get(value).toString() + ".js");
                  InputStream is;
                  if (file.exists()) {
                    is = new FileInputStream(file);
                  } else {
                    is = ScriptRunnerExp.class.getResourceAsStream(hashName.get(value).toString()
                        + ".js");
                  }
                  String script = IOUtil.getStreamContentAsString(is, "UTF-8");
                  ScriptRunnerExp scriptRunnerExp = new ScriptRunnerExp(frameUI);
                  scriptRunnerExp.eval(script);
                  System.out.println(new JVMEnv().getMemoryInfo());
                  System.out.println("...........................................................");
                } catch (Exception ex) {
                  MyPanel.isTest = false;
                  frameUI.destroy();
                  frameUI = null;
                  System.gc();
                //  ex.printStackTrace();
                } finally {
                }
              }
            };
            thread.start();
          }
        }
        MyPanel.isTest = false;
      }
    });
    return btnEdit;
  }

  public JButton getBtnRemove() {
    JButton btnEdit = new JButton("Xóa DL");
    btnEdit.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/delete1.png")));
    btnEdit.addMouseListener(new MouseEventClickButtonDialogPlus("cancel1.png", "cancel1.png", "cancel1.png"));
    btnEdit.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          AccountModelManager.getInstance().deleteTest("HktTest");
          HRModelManager.getInstance().deleteTest("HktTest");
          CustomerModelManager.getInstance().deleteTest("HktTest");
          SupplierModelManager.getInstance().deleteTest("HktTest");
          ProductModelManager.getInstance().deleteTest("HktTest");
          WarehouseModelManager.getInstance().deleteTest("HktTest");
          RestaurantModelManager.getInstance().deleteTest("HktTest");
          LocaleModelManager.getInstance().deleteTest("HktTest");
          GenericOptionModelManager.getInstance().deleteTest("HktTest");
          AccountingModelManager.getInstance().deleteTest("HktTest");
          PromotionModelManager.getInstance().deleteTest("HktTest");
        } catch (Exception e2) {
          e2.printStackTrace();
        }

      }
    });
    return btnEdit;
  }

  public class Test {
    private String name;
    private int index;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public int getIndex() {
      return index;
    }

    public void setIndex(int index) {
      this.index = index;
    }

  }
}
