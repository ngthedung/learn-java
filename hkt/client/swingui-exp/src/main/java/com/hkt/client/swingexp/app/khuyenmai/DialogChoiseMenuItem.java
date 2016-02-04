package com.hkt.client.swingexp.app.khuyenmai;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.accounting.entity.InvoiceItemAttribute;
import com.hkt.module.product.entity.Product;
import com.hkt.module.promotion.entity.Menu;
import com.hkt.module.promotion.entity.MenuItem;
import com.hkt.module.promotion.entity.MenuItem.MenuItemType;

@SuppressWarnings("serial")
public class DialogChoiseMenuItem extends JPanel implements IDialogResto{
  private PanelMenuItemProduct panelListOperationProduct;
  private DefaultTableModel tableModel;
  private List<String> listStr = new ArrayList<String>();
  private List<String> listGroup = new ArrayList<String>();
  private InvoiceItem invoiceItem;

  public DialogChoiseMenuItem(Menu menu) {
    initComponents();
    panelListOperationProduct = new PanelMenuItemProduct(this, menu);
    jScrollPane1.setViewportView(panelListOperationProduct);
    tableModel = (DefaultTableModel) jTable1.getModel();
    JCheckBox chBox = new JCheckBox();
    chBox.setHorizontalAlignment(JLabel.CENTER);
    chBox.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        int row = jTable1.getSelectedRow();
        if (!jTable1.getValueAt(row, 2).toString().trim().isEmpty()) {
          Product operation1 = (Product) tableModel.getValueAt(row, 0);
          for (int i = 0; i < listStr.size();) {
            if (listStr.get(i).equals(operation1.getCode())) {
              listStr.remove(i);
              listGroup.remove(i);
              if(invoiceItem!=null){
                for(int j = 0; j< invoiceItem.getReferences().size();){
                  if(invoiceItem.getReferences().get(j).getName().equals("Menu")
                      &&invoiceItem.getReferences().get(j).getValue().equals(operation1.getCode())){
                    invoiceItem.getReferences().remove(j);
                  }else {
                    j++;
                  }
                }
              }
            } else {
              i++;
            }
          }
          tableModel.removeRow(row);
        }
      }
    });
    jTable1.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(chBox));
    jTable1.getColumnModel().getColumn(3).setCellRenderer(new MyCell());
    List<MenuItem> list = menu.getMenuItems();
    for (int i = 0; i < list.size();) {
      if (list.get(i).getMenuItemType() == MenuItemType.ByMenuItemRef) {
        list.remove(i);
      } else {
        Object[] objects = { list.get(i), list.get(i).getQuantity(), "", false };
        tableModel.addRow(objects);
        i++;
      }
    }
  }

  private void initComponents() {
	  this.setBackground(new Color(255, 255, 255));
	  setOpaque(false);
	  
    jScrollPane1 = new javax.swing.JScrollPane();
    btnNext = new javax.swing.JButton();
    jPanel1 = new javax.swing.JPanel();
    jScrollPane2 = new javax.swing.JScrollPane();
    jTable1 = new javax.swing.JTable();
    lbStt = new javax.swing.JLabel();

    btnNext.setFont(new java.awt.Font("Tahoma", 1, 14));
    btnNext.setText(">"); // NOI18N

    jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(
        javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(204, 204, 204), null, null),
        "Danh sách các món đã chọn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
        javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(124,
            0, 0))); // NOI18N

    jTable1.setFont(new java.awt.Font("Tahoma", 0, 14));
    jTable1.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

    }, new String[] { "Tên sản phẩm", "Số lg", "Nhóm", "Xóa" }) {
      boolean[] canEdit = new boolean[] { false, false, false, true };

      public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit[columnIndex];
      }
    });
    jTable1.setRowHeight(23);
    jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        jTable1MouseClicked(evt);
      }
    });
    jScrollPane2.setViewportView(jTable1);
    jTable1.getColumnModel().getColumn(0).setHeaderValue("Tên sản phẩm"); // NOI18N
    jTable1.getColumnModel().getColumn(1).setMaxWidth(45);
    jTable1.getColumnModel().getColumn(1).setHeaderValue("Số lg"); // NOI18N
    jTable1.getColumnModel().getColumn(2).setHeaderValue("Nhóm"); // NOI18N
    jTable1.getColumnModel().getColumn(3).setMaxWidth(45);
    jTable1.getColumnModel().getColumn(3).setHeaderValue("Xóa"); // NOI18N
    jTable1.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE));
    jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(
            jPanel1Layout.createSequentialGroup().addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)));

    lbStt.setForeground(new java.awt.Color(255, 0, 0));
    lbStt.setText(""); // NOI18N

    GroupLayout layout = new GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGroup(layout.createSequentialGroup()
                .addComponent(lbStt, javax.swing.GroupLayout.PREFERRED_SIZE, 706, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(50, 50, 50)));

    layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] { jPanel1, jScrollPane1 });

    layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
        layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout .createSequentialGroup()
                            .addGap(119, 119, 119)
                            .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(lbStt, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()))));

  }

  protected void save() {
    if (tableModel.getRowCount() > 0) {
    	((JDialog) getRootPane().getParent()).dispose();
    } else {
      lbStt.setText("Chưa chọn món");
    }

  }

  protected void jTable1MouseClicked(MouseEvent evt) {
    int k = jTable1.getSelectedRow();
    if (k >= 0) {
      if (evt.getButton() == MouseEvent.BUTTON3) {
        Product operation1 = (Product) tableModel.getValueAt(k, 0);
        MyDouble h = new MyDouble(tableModel.getValueAt(k, 1).toString());
        if (h.longValue() > 1) {
          double b = h.doubleValue() - 1;
          tableModel.setValueAt(b, k, 1);
          for (int i = 0; i < listStr.size();) {
            if (listStr.get(i).equals(operation1.getCode())) {
              listStr.remove(i);
              if(invoiceItem!=null){
                for(int j = 0; j< invoiceItem.getReferences().size();){
                  if(invoiceItem.getReferences().get(j).getName().equals("Menu")
                      &&invoiceItem.getReferences().get(j).getValue().equals(operation1.getCode())){
                    invoiceItem.getReferences().remove(j);
                  }else {
                    j++;
                  }
                }
              }
              break;
            } else {
              i++;
            }
          }
        } else {
          for (int i = 0; i < listStr.size();) {
            if (listStr.get(i).equals(operation1.getCode())) {
              listStr.remove(i);
              if(invoiceItem!=null){
                for(int j = 0; j< invoiceItem.getReferences().size();){
                  if(invoiceItem.getReferences().get(j).getName().equals("Menu")
                      &&invoiceItem.getReferences().get(j).getValue().equals(operation1.getCode())){
                    invoiceItem.getReferences().remove(j);
                  }else {
                    j++;
                  }
                }
              }
            } else {
              i++;
            }
          }
          if (!tableModel.getValueAt(k, 2).toString().trim().isEmpty()) {
            tableModel.removeRow(k);
          }
        }
      }
    }

  }
  
  

  public InvoiceItem getInvoiceItem() {
    return invoiceItem;
  }



  public class MyCell implements TableCellRenderer {

    public MyCell() {
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
        int row, int column) {
      JCheckBox checkBox = new JCheckBox();
      checkBox.setSelected((Boolean) value);
      checkBox.setOpaque(false);
      if (table.getValueAt(row, 2).toString().trim().isEmpty()) {
        checkBox.setSelected(false);
        checkBox.setEnabled(false);
      }
      return checkBox;
    }
  }

  public void addProduct(Product product, String group, double index) {
    boolean flag = false;
    double k = 0;
    for (int j = 0; j < tableModel.getRowCount(); j++) {
      String str = tableModel.getValueAt(j, 2).toString();
      if (str.equals(group)) {
        k = k + MyDouble.parseDouble(tableModel.getValueAt(j, 1).toString());
      }
    }
    if (k == index) {
      lbStt.setText("Sản phẩm đã đủ số lượng");
      flag = true;
      return;
    }
    for (int i = 0; i < tableModel.getRowCount(); i++) {
      if (!jTable1.getValueAt(i, 2).toString().trim().isEmpty()) {
        String str = tableModel.getValueAt(i, 2).toString();
        if (str.equals(group)) {
          Product operation1 = (Product) tableModel.getValueAt(i, 0);
          if (product.getId() == operation1.getId()) {
            flag = true;
            double sl = new MyDouble(tableModel.getValueAt(i, 1).toString()).doubleValue();
            if (sl < index) {
              sl = sl + 1;
              listStr.add(product.getCode());
              listGroup.add(group);
              if(invoiceItem!=null){
                InvoiceItemAttribute a = new InvoiceItemAttribute();
                a.setName("Menu");
                a.setValue(product.getCode());
                a.setAuthor(group);
                invoiceItem.add(a);
              }
              tableModel.setValueAt(sl, i, 1);
              lbStt.setText("");
              break;
            } else {
              lbStt.setText("Sản phẩm đã đủ số lượng");
              return;
            }
          }
        }
      }
    }
    if (!flag) {
      listStr.add(product.getCode());
      listGroup.add(group);
      if(invoiceItem!=null){
        InvoiceItemAttribute a = new InvoiceItemAttribute();
        a.setName("Menu");
        a.setValue(product.getCode());
        a.setAuthor(group);
        invoiceItem.add(a);
      }
      Object[] objects = { product, "1", group, false };
      tableModel.addRow(objects);
      lbStt.setText("");
    }
  }

  public void setData(InvoiceItem invoiceItem) throws Exception {
    List<InvoiceItemAttribute> invoiceItemAttributes = invoiceItem.getReferences();
    for (InvoiceItemAttribute item : invoiceItemAttributes) {
      if (item.getName().equals("Menu")) {
        Product p = ProductModelManager.getInstance().getProductByCode(item.getValue());
        addProduct(p, item.getAuthor(), Double.MAX_VALUE);
      }
    }
    this.invoiceItem = invoiceItem;
  }

  public List<String> getListStr() {
    return listStr;
  }

  public List<String> getListGroup() {
    return listGroup;
  }

  public JButton getBtnNext() {
    return btnNext;
  }

  private javax.swing.JButton btnNext;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JTable jTable1;
  private javax.swing.JLabel lbStt;

@Override
public void Save() throws Exception {
	save();
	
}
}
