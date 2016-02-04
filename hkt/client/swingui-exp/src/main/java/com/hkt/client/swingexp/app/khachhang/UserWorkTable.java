package com.hkt.client.swingexp.app.khachhang;

import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.swing.widget.BeanBindingJTable;

public class UserWorkTable extends BeanBindingJTable<UserWork> {
  
  static String[] HEADERS = {"STT", "Công ty", "Từ", "Đến", "Vị trí", "Ghi chú" };
  static String[] PROPERTIES = {"organization", "organization", "from", "to", "position", "description" };
  
  public void setUserWorks(List<UserWork> userWorks) {
    init(HEADERS, PROPERTIES, userWorks);
    setRowHeight(23);
    setOpaque(false);
    setFont(new Font("Tahoma", 0, 14));
    getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
    getColumnModel().getColumn(0).setMaxWidth(50);
    ((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
    
  }
  
  protected UserWork newBean() {
    return new UserWork();
  }
  
  protected boolean isBeanEditableAt(int row, int col) {
    return false;
  }
  
}
