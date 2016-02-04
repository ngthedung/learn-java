package com.hkt.client.swingexp.app.khohang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductTag;

public class PanelListProduct extends JPanel {

  private IPanelListProduct parentPanel;
  private JScrollPane       scrollPane;
  private boolean           flag = true;
  private List<JLabel>      listP;
  private List<JTable>      listT;
  private List<ProductTag>  listProductG;
  private List<Product>     products;

  public PanelListProduct(IPanelListProduct parentPanel) {
    initComponents();
    this.parentPanel = parentPanel;
  }

  private void initComponents() {
    scrollPane = new javax.swing.JScrollPane();

    addComponentListener(new java.awt.event.ComponentAdapter() {
      public void componentResized(java.awt.event.ComponentEvent evt) {
        formComponentResized(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
        scrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 295,
        Short.MAX_VALUE));
    layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
        scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE));
  }

  private void formComponentResized(java.awt.event.ComponentEvent evt) {
    if (flag) {
      loadGui();
    }
  }

  public void loadGui() {
    products = new ArrayList<Product>();
    List<ProductTag> list = new ArrayList<ProductTag>();
    if (listProductG != null) {
      if (listProductG.size() > 0)
        for (int i = 0; i < listProductG.size(); i++) {
        	if(listProductG.get(i) != null){
        		list.add(listProductG.get(i));
            try {
              List<ProductTag> productTags = ProductModelManager.getInstance().getChildProductTag(listProductG.get(i).getTag());
              for (ProductTag productTag2 : productTags) {
                list.add(productTag2);
              }
            } catch (Exception e) {
              e.printStackTrace();
            }
        	}
        }
    }
    listP = new ArrayList<JLabel>();
    listT = new ArrayList<JTable>();
    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout(FlowLayout.LEADING));
    panel.setPreferredSize(new Dimension(scrollPane.getWidth() - 5, scrollPane.getHeight() - 5));
    panel.setBackground(Color.white);
    int size = 0;
    for (int i = 0; i < list.size(); i++) {
      JPanel panel2 = new JPanel(new BorderLayout());
      final JLabel label3 = new JLabel("+");
      label3.setOpaque(true);
      label3.setBackground(Color.PINK);
      label3.setPreferredSize(new Dimension(10, 25));
      panel2.add(label3, BorderLayout.WEST);
      listP.add(label3);
      JLabel label = new JLabel(" " + list.get(i).getLabel());
      if (label.getText().equals(" Nhóm SP HktTest111")) {
        label3.setName("lblNut" + i);
      }
      panel2.setPreferredSize(new Dimension(260, 25));
      panel2.setOpaque(false);
      panel2.add(label, BorderLayout.CENTER);
      label.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 14));
      panel.add(panel2);
      List<Product> list1 = new ArrayList<Product>();
      try {
        list1 = ProductModelManager.getInstance().findByProductTag(list.get(i).getTag());
      } catch (Exception e1) {
        e1.printStackTrace();
      }
      products.addAll(list1);
      final JTable table = new JTable(new TableModelProduct(list1));
      table.setName("tbDanhSachSanPham");
      table.setRowHeight(23);
      table.setPreferredSize(new Dimension(295, 23 * list1.size()));
      table.getColumnModel().getColumn(1).setMaxWidth(70);
      table.getColumnModel().getColumn(1).setMinWidth(70);
      table.setFont(new Font("Tahoma", Font.PLAIN, 14));
      table.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          int click = 2;
          JTable ta = (JTable) e.getSource();
          String value = ta.getValueAt(ta.getSelectedRow(), ta.getSelectedColumn()).toString();
          if (value.indexOf("HktTest") >= 0 && e.getButton() == 3) {
            click = 1;
          }
          if (e.getClickCount() >= click) {
            if (table.getSelectedRow() >= 0) {
              Product product = (Product) table.getValueAt(table.getSelectedRow(), 0);
              parentPanel.addProduct(product);
            }
          }
        }
      });
      listT.add(table);
      panel.add(table);
      table.setVisible(false);
      List<ProductTag> listPG = new ArrayList<ProductTag>();
      try {
        listPG = ProductModelManager.getInstance().findProductTagRoot(list.get(i).getTag() + ":");
      } catch (Exception e1) {
        e1.printStackTrace();
      }
      final JPanel list2 = getPanel(listPG);
      panel.add(list2);
      list2.setVisible(false);
      size = size + panel2.getPreferredSize().height + table.getPreferredSize().height + 10
          + list2.getPreferredSize().height;
      label.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          if (label3.getText().equals("+")) {
            table.setVisible(true);
            label3.setText("-");
          } else {
            table.setVisible(false);
            label3.setText("+");
          }
        }
      });
      label3.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          if (label3.getText().equals("+")) {
            table.setVisible(true);
            label3.setText("-");
          } else {
            table.setVisible(false);
            label3.setText("+");
          }
        }
      });
      table.addComponentListener(new ComponentAdapter() {
        @Override
        public void componentHidden(ComponentEvent e) {
          list2.setVisible(false);
        }

        @Override
        public void componentShown(ComponentEvent e) {
          if (label3.getText().equals("-")) {
            list2.setVisible(true);
          }
        }
      });
    }
    panel.setPreferredSize(new Dimension(260, size));
    scrollPane.setViewportView(panel);
    flag = false;
  }

  public void opendAll(boolean flag) {
    if (!flag) {
      for (int i = 0; i < listP.size(); i++) {
        listP.get(i).setText("+");
      }
      for (int i = 0; i < listT.size(); i++) {
        listT.get(i).setVisible(false);
      }
    } else {
      for (int i = 0; i < listP.size(); i++) {
        listP.get(i).setText("-");
      }
      for (int i = 0; i < listT.size(); i++) {
        listT.get(i).setVisible(true);
      }
    }
  }

  private JPanel getPanel(List<ProductTag> list) {
    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout(FlowLayout.LEADING));
    panel.setBackground(Color.white);
    int h = 0;
    for (int i = 0; i < list.size(); i++) {
      JPanel panel2 = new JPanel(new BorderLayout());
      final JLabel label3 = new JLabel("+");
      label3.setOpaque(true);
      label3.setBackground(Color.PINK);
      label3.setPreferredSize(new Dimension(10, 25));
      panel2.add(label3, BorderLayout.WEST);
      listP.add(label3);
      JLabel label = new JLabel(" " + list.get(i).toString());
      panel2.setPreferredSize(new Dimension(260, 25));
      panel2.setOpaque(false);
      panel2.add(label, BorderLayout.CENTER);
      label.setFont(new Font("Tahoma", Font.BOLD, 14));
      panel.add(panel2);
      List<Product> list1 = new ArrayList<Product>();
      try {
        list1 = ProductModelManager.getInstance().findByProductTag(list.get(i).getTag());
      } catch (Exception e2) {
        e2.printStackTrace();
      }
      products.addAll(list1);
      final JTable table = new JTable(new TableModelProduct(list1));
      table.setRowHeight(23);
      table.setPreferredSize(new Dimension(295, 23 * list1.size()));
      table.getColumnModel().getColumn(1).setMaxWidth(70);
      table.getColumnModel().getColumn(1).setMinWidth(70);
      table.setFont(new Font("Tahoma", Font.PLAIN, 14));
      table.addMouseListener(new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {
          for (int i = 0; i < listT.size(); i++) {
            if (!listT.get(i).toString().equals(table.toString())) {
              listT.get(i).clearSelection();
            }
          }
          if (e.getClickCount() >= 2) {
            if (table.getSelectedRow() >= 0) {
              Product product = (Product) table.getValueAt(table.getSelectedRow(), 0);
              parentPanel.addProduct(product);
            }
          }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
          for (int i = 0; i < listT.size(); i++) {
            if (!listT.get(i).toString().equals(table.toString())) {
              listT.get(i).clearSelection();
            }
          }
        }

        @Override
        public void mousePressed(MouseEvent e) {
          for (int i = 0; i < listT.size(); i++) {
            if (!listT.get(i).toString().equals(table.toString())) {
              listT.get(i).clearSelection();
            }
          }
        }
      });
      listT.add(table);
      panel.add(table);
      table.setVisible(false);
      List<ProductTag> listPG = new ArrayList<ProductTag>();
      try {
        listPG = ProductModelManager.getInstance().findProductTagRoot(list.get(i).getTag());
      } catch (Exception e1) {
        e1.printStackTrace();
      }
      final JPanel list2 = getPanel(listPG);
      h = h + panel2.getPreferredSize().height + table.getPreferredSize().height + 10 + list2.getPreferredSize().height;
      panel.add(list2);
      list2.setVisible(false);
      label.addMouseListener(new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {
          if (label3.getText().equals("+")) {
            table.setVisible(true);
            label3.setText("-");
          } else {
            table.setVisible(false);
            label3.setText("+");
          }
        }
      });
      label3.addMouseListener(new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {
          if (label3.getText().equals("+")) {
            table.setVisible(true);
            label3.setText("-");
          } else {
            table.setVisible(false);
            label3.setText("+");
          }
        }
      });
      table.addComponentListener(new ComponentAdapter() {

        @Override
        public void componentHidden(ComponentEvent e) {
          list2.setVisible(false);
          label3.setText("+");
        }

        @Override
        public void componentShown(ComponentEvent e) {
          if (label3.getText().equals("-")) {
            list2.setVisible(true);
          }
        }
      });
      panel.addComponentListener(new ComponentAdapter() {

        @Override
        public void componentHidden(ComponentEvent e) {
          table.setVisible(false);
          label3.setText("+");
        }

        @Override
        public void componentShown(ComponentEvent e) {
          if (label3.getText().equals("-")) {
            table.setVisible(true);
          }
        }
      });

    }
    panel.setPreferredSize(new Dimension(295, h));
    return panel;
  }

  public void setListProductTag(List<ProductTag> listProductTag) {
    this.listProductG = listProductTag;
  }

  public List<Product> getListProduct() {
    return products;
  }

}
