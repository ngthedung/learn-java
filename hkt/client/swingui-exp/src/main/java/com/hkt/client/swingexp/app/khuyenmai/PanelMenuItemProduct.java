package com.hkt.client.swingexp.app.khuyenmai;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.model.PromotionModelManager;
import com.hkt.module.product.entity.Product;
import com.hkt.module.promotion.entity.Menu;
import com.hkt.module.promotion.entity.MenuItem;
import com.hkt.module.promotion.entity.MenuItem.MenuItemType;
import com.hkt.module.promotion.entity.MenuItemRef;

public class PanelMenuItemProduct extends javax.swing.JPanel {
private JScrollPane scrollPane;
private List<MenuItem> list;
private List<JLabel> listP;
private List<JTable> listT;
private boolean flag = true;
private JTable tableP;
private DialogChoiseMenuItem pChoiseMenuItem;

  public PanelMenuItemProduct(DialogChoiseMenuItem pChoiseMenuItem1, Menu menu) {
    initComponents();
    this.pChoiseMenuItem= pChoiseMenuItem1;
    try {
      menu = PromotionModelManager.getInstance().getMenuByCode(menu.getCode());
    } catch (Exception e) {
    }
    list = menu.getMenuItems();
    for(int i = 0; i< list.size();){
      if(list.get(i).getMenuItemType()==MenuItemType.ByProduct){
        list.remove(i);
      }else {
        i++;
      }
    }
    pChoiseMenuItem1.getBtnNext().addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
          if (tableP != null) {
              Product product = (Product) tableP.getValueAt(tableP.getSelectedRow(), 0);
              double str = new MyDouble(tableP.getValueAt(tableP.getSelectedRow(), 1).toString()).doubleValue();
              pChoiseMenuItem.addProduct(product, tableP.getName(), str);
          }
      }
  });
  }

  private void initComponents() {

    scrollPane = new javax.swing.JScrollPane();

    addComponentListener(new java.awt.event.ComponentAdapter() {
      public void componentResized(java.awt.event.ComponentEvent evt) {
        formComponentResized(evt);
      }
    });

    scrollPane.setViewportBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách các nhóm tùy chọn",
        javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
        new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(104, 0, 0))); // NOI18N

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
        scrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 185,
        Short.MAX_VALUE));
    layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
        scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE));
  }

  protected void formComponentResized(ComponentEvent evt) {
    if (flag) {
      try {
        loadGui();
      } catch (Exception e) {
        e.printStackTrace();
      }
     
  }
    
  }
  
  public void loadGui() throws Exception{
    listP = new ArrayList<JLabel>();
    listT = new ArrayList<JTable>();
    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout(FlowLayout.LEADING));
    panel.setPreferredSize(new Dimension(scrollPane.getWidth() - 5, scrollPane.getHeight() - 5));
    panel.setBackground(Color.white);
    int size = 0;

    for (int i = 0; i < list.size(); i++) {
        JPanel panel2 = new JPanel(new BorderLayout());
        final JLabel label3 = new JLabel("-");
        label3.setOpaque(true);
        label3.setBackground(Color.PINK);
        label3.setPreferredSize(new Dimension(10, 25));
        panel2.add(label3, BorderLayout.WEST);
        listP.add(label3);
        final JLabel label = new JLabel(" " + list.get(i).getName());
        panel2.setPreferredSize(new Dimension(260, 25));
        panel2.setOpaque(false);
        panel2.add(label, BorderLayout.CENTER);
        label.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(panel2);
        MenuItemRef m = PromotionModelManager.getInstance().getMenuItemRefByCode(list.get(i).getIdentifierId());
        final JTable table = new JTable(new TableModelProductVoucher(m));
        table.setRowHeight(23);
        table.setPreferredSize(new Dimension(380, 23 * m.getProducts().size()));
        table.getColumnModel().getColumn(1).setMaxWidth(70);
        table.getColumnModel().getColumn(1).setMinWidth(70);
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                JTable t = (JTable) e.getSource();
                if (table.getSelectedRow() >= 0) {
                    Product product = (Product) table.getValueAt(table.getSelectedRow(), 0);
                    if (tableP != null) {
                        if (!tableP.equals(t)) {
                            tableP.clearSelection();
                            tableP = t;
                            tableP.setName(label.getText().trim());
                        }
                    } else {
                        tableP = t;
                        tableP.setName(label.getText().trim());
                    }
                    if (e.getClickCount() >= 2) {
                        pChoiseMenuItem.addProduct(product, label.getText().trim(), MyDouble.parseDouble(table.getValueAt(table.getSelectedRow(), 1).toString()));
                    }
                }
            }
        });

        listT.add(table);
        panel.add(table);
         table.setVisible(false);
        size = size + panel2.getPreferredSize().height + table.getPreferredSize().height + 10;

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


}
