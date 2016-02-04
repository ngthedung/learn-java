package com.hkt.client.swingexp.app.khachhang;

import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.swing.widget.BeanBindingJTable;

public class UserRelationshipTable extends BeanBindingJTable<UserRelationship> {
  
  static String[] HEADERS = { "STT","Loại quan hệ", "Giới tính", "Họ", "Tên", "Ngày sinh", "Nghề nghiệp" };
  static String[] PROPERTIES = { "relation", "relation", "gender", "lastName", "firstName","birthday", "occupation" };
  
  public void setUserRelationships(List<UserRelationship> educations) {
    
    init(HEADERS, PROPERTIES, educations);
    setRowHeight(23);
    setOpaque(false);
    setFont(new Font("Tahoma", 0, 14));
    getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
    getColumnModel().getColumn(0).setMaxWidth(50);
    ((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
    
  }
  
  protected UserRelationship newBean() {
    return new UserRelationship();
  }
  
  protected boolean isBeanEditableAt(int row, int col) {
    return false;
  }
  
}
