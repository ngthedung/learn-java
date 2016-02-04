package com.hkt.client.swingexp.app.khachhang;

import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.swing.widget.BeanBindingJTable;

@SuppressWarnings("serial")
public class UserEducationTable extends BeanBindingJTable<UserEducation> {
  
  static String[] HEADERS = {"STT", "Trường", "Từ", "Đến", "Chuyên ngành", "Bằng cấp", "Ngoại ngữ" };
  static String[] PROPERTIES = {"schoolOrInstitute", "schoolOrInstitute", "from", "to", "major", "certificate", "language" };
  
  public void setUserEducations(List<UserEducation> educations) {
    
    init(HEADERS, PROPERTIES, educations);
    setRowHeight(23);
    setOpaque(false);
    setFont(new Font("Tahoma", 0, 14));
    getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
    getColumnModel().getColumn(0).setMaxWidth(50);
    ((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
    
  }
  
  protected UserEducation newBean() {
    return new UserEducation();
  }
  
  protected boolean isBeanEditableAt(int row, int col) {
    return false;
  }
  
}
