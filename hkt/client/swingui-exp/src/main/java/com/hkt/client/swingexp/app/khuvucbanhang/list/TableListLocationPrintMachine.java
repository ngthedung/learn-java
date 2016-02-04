package com.hkt.client.swingexp.app.khuvucbanhang.list;

import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.swing.widget.BeanBindingJTable;
import com.hkt.module.restaurant.entity.Location;

@SuppressWarnings("serial")
public class TableListLocationPrintMachine extends BeanBindingJTable<Location> {
	  
	  static String[] HEADERS = { "STT", "Mã KV", "Tên khu vực", "Mô tả" };
	  static String[] PROPERTIES = { "id", "code", "name", "description" };
	  
	  public void setLocation(List<Location> location) {
	    init(HEADERS, PROPERTIES, location);
	    setRowHeight(23);
	    setFont(new Font("Tahoma", 0, 14));
	    getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
	    ((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
	    getColumnModel().getColumn(0).setMaxWidth(50);
	  }
	  
	  public TableListLocationPrintMachine(List<Location> location) {
	    init(HEADERS, PROPERTIES, location);
	    setRowHeight(23);
	    setFont(new Font("Tahoma", 0, 14));
	    getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
	    ((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
	  }
	  
	  protected Location newBean() {
	    return new Location();
	  }
	  
	  protected boolean isBeanEditableAt(int row, int col) {
	    return false;
	  }
}
