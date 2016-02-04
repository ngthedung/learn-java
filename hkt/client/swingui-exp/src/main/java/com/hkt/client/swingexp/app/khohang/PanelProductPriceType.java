
package com.hkt.client.swingexp.app.khohang;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextArea;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.module.product.entity.ProductPriceType;

public class PanelProductPriceType extends JPanel implements IDialogMain {
  
  private JLabel lbType, lbOrganization, lbDescription, lbMessenger;
  private JTextField txtType, txtOrganization;
  private JTextArea txtDescription;
  private JScrollPane scrollPaneArea, scrollPaneTable;
  private ProductPriceTypeTable priceTypeTable;
  private ProductPriceType priceType = new ProductPriceType();
  private String reviewPriceType = "";
  
  public PanelProductPriceType() throws Exception {
    init();
    setOpaque(false);
  }
  
  private void init() throws Exception {
    this.setBackground(new Color(255, 255, 255));
    lbType = new ExtendJLabel("Loại giá");
    lbOrganization = new ExtendJLabel("Công ty");
    lbDescription = new ExtendJLabel("Miêu tả");
    
    lbMessenger = new JLabel();
    
    txtType = new ExtendJTextField();
    txtOrganization = new ExtendJTextField();
    txtDescription = new ExtendJTextArea();
    
    scrollPaneTable = new JScrollPane();
    scrollPaneTable.getViewport().setBackground(Color.white);
    
    scrollPaneArea = new JScrollPane();
    scrollPaneArea.setViewportView(txtDescription);
    
    priceTypeTable = new ProductPriceTypeTable(getListData());
    priceTypeTable.addMouseListener(new MouseAdapter() {
      
      public void mouseClicked(MouseEvent event) {
        priceTypeTableMouseClicked(event);
      }
    });
    scrollPaneTable.setViewportView(priceTypeTable);
    getGroupLayout();
  }
  
  /**
   * Tạo nội dung bên trong panel
   * */
  private void getGroupLayout() {
    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(layout
        .createParallelGroup(Alignment.LEADING)
        .addComponent(scrollPaneTable)
        .addComponent(lbMessenger, GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
        .addGroup(
            layout.createSequentialGroup()
                .addComponent(lbType, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE).addGap(4, 4, 4)
                .addComponent(txtType, GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE))
        .addGroup(
            layout.createSequentialGroup()
                .addComponent(lbOrganization, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4).addComponent(txtOrganization, GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE))
        .addGroup(
            layout.createSequentialGroup()
                .addComponent(lbDescription, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4).addComponent(scrollPaneArea, GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)));
    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
        layout
            .createSequentialGroup()
            .addGroup(
                layout
                    .createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lbType, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE))
            .addGap(10, 10, 10)
            .addGroup(
                layout
                    .createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lbOrganization, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOrganization, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE))
            .addGap(10, 10, 10)
            .addGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lbDescription, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                    .addComponent(scrollPaneArea, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(lbMessenger)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(scrollPaneTable, GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)));
  }
  
  protected void priceTypeTableMouseClicked(MouseEvent event) {
    try {
      if (event.getClickCount() >= 2) {
        setObject(priceTypeTable.getSelectedBean());
        setData();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @Override
  public void save() throws Exception {
    if (!checkData()) { return; }
    ProductModelManager.getInstance().saveProductPriceType(priceType);
    reset();
  }
  
  public boolean checkData() {
    getData();
    if (priceType.getType() == null | priceType.getType().isEmpty() | priceType.getOrganizationLoginId().isEmpty()) {
      
      if (priceType.getType().isEmpty()) {
        lbType.setForeground(Color.red);
      } else {
        lbType.setForeground(Color.black);
      }
      
      if (priceType.getOrganizationLoginId().isEmpty()) {
        lbOrganization.setForeground(Color.red);
      } else {
        lbOrganization.setForeground(Color.black);
      }
      
      lbMessenger.setText("Dữ liệu vùng đỏ bị lỗi");
      lbMessenger.setForeground(Color.red);
      lbMessenger.setVisible(true);
      return false;
    }
    lbMessenger.setVisible(false);
    return true;
  }
  
  @Override
  public void edit() throws Exception {
    if (!checkData()) { return; }
    ProductModelManager.getInstance().saveProductPriceType(priceType);
    reset();
  }
  
  @Override
  public void delete() throws Exception {
    if (ProductModelManager.getInstance().deleteProductPriceType(priceType)) {
      lbMessenger.setVisible(false);
      refresh();
      reset();
    } else {
      lbMessenger.setText("Hãy chắc chắn là bạn đã chọn 1 thuế để xóa");
      lbMessenger.setVisible(true);
      return;
    }
  }
  
  @Override
  public void reset() throws Exception {
    txtType.setText("");
    txtOrganization.setText("");
    txtDescription.setText("");
    
    lbType.setForeground(Color.black);
    lbOrganization.setForeground(Color.black);
    lbMessenger.setForeground(Color.black);
    lbMessenger.setText("");
    priceType = new ProductPriceType();
    loadTable();
  }
  
  @Override
  public void refresh() throws Exception {
    try {
      priceType = ProductModelManager.getInstance().getProductPriceTypeByType(reviewPriceType);
    } catch (Exception e) {
      priceType = new ProductPriceType();
    }
    setData();
  }
  
  private void loadTable() throws Exception {
    priceTypeTable.setProductPriceTypes(getListData());
    scrollPaneTable.setViewportView(priceTypeTable);
  }
  
  public void setObject(ProductPriceType object) throws Exception {
    this.priceType = object;
    if (object == null) {
      priceType = new ProductPriceType();
    }
    setData();
  }
  
  public List<ProductPriceType> getListData() throws Exception {
    return ProductModelManager.getInstance().getProductPriceTypes();
  }
  
  private void setData() {
    try {
      txtType.setText(priceType.getType());
      txtOrganization.setText(priceType.getOrganizationLoginId());
      txtDescription.setText(priceType.getDescription());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private boolean getData() {
    try {
      priceType.setType(txtType.getText());
      reviewPriceType = txtType.getText();
      priceType.setOrganizationLoginId(txtOrganization.getText());
      priceType.setDescription(txtDescription.getText());
      lbMessenger.setVisible(false);
      return true;
    } catch (Exception e) {
      lbMessenger.setVisible(true);
      lbMessenger.setText("Lỗi định dạng dữ liệu:" + e.toString());
      return false;
    }
  }
  
}
