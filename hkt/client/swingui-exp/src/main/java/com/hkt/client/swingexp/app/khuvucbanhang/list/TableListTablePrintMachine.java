package com.hkt.client.swingexp.app.khuvucbanhang.list;

import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.swing.widget.BeanBindingJTable;
import com.hkt.module.restaurant.entity.Table;

@SuppressWarnings("serial")
public class TableListTablePrintMachine extends BeanBindingJTable<Table> {
	  
	  static String[] HEADERS = { "STT", "Mã bàn", "Tên bàn", "Mô tả" };
	  static String[] PROPERTIES = { "id", "code", "name", "description"};
	  
	  public void setTable(List<Table> table) {
	    init(HEADERS, PROPERTIES, table);
	    setRowHeight(23);
	    setFont(new Font("Tahoma", 0, 14));
	    getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
	    ((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
	    getColumnModel().getColumn(0).setMaxWidth(50);

	  }
	  
	  public TableListTablePrintMachine(List<Table> table) {
	    init(HEADERS, PROPERTIES, table);
	    setRowHeight(23);
	    setFont(new Font("Tahoma", 0, 14));
	    getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
	    ((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
	  }
	  
	  protected Table newBean() {
	    return new Table();
	  }
	  
	  protected boolean isBeanEditableAt(int row, int col) {
	    return false;
	  }
}
