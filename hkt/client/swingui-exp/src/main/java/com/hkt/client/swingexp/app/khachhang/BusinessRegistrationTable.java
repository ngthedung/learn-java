package com.hkt.client.swingexp.app.khachhang;

import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.swing.widget.BeanBindingJTable;

@SuppressWarnings("serial")
public class BusinessRegistrationTable extends BeanBindingJTable<BusinessRegistration> {
  
  static String[] HEADERS = { "STT", "Tên tiếng việt", "Tên tiếng anh", "Mã đăng ký", "Mã số thuế",
    "Đại diện", "Vốn điều lệ", "Vốn pháp định", "Lĩnh vực kinh doanh" };
  static String[] PROPERTIES = { "label", "fullNameVN", "fullNameEN", "registrationCode", "taxRegistrationCode",
      "representative", "charterCapital", "legalCapital", "domain" };
  
  public void setBusinessRegistrations(List<BusinessRegistration> businessRegistration) {
    
    init(HEADERS, PROPERTIES, businessRegistration);
    setRowHeight(23);
    setOpaque(false);
    setFont(new Font("Tahoma", 0, 14));
    getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
    getColumnModel().getColumn(0).setMaxWidth(50);
    ((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
    
  }
  
  protected BusinessRegistration newBean() {
    return new BusinessRegistration();
  }
  
  protected boolean isBeanEditableAt(int row, int col) {
    return false;
  }
  
}
