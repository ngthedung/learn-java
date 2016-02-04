package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.swing.widget.BeanBindingJTable;
import com.hkt.module.accounting.entity.InvoiceItem;

@SuppressWarnings("serial")
public class TableInvoiceItems extends BeanBindingJTable<InvoiceItem> {
	
	 static String[] HEADERS = { "STT","Tên", "Số lượng", "Đơn giá","Tiền" };
	 static String[] PROPERTIES = { "id","itemName","quantity", "unitPrice","total"}; 
	 
    public void setTableInvoiceItems(List<InvoiceItem> invoiItem) {
		    init(HEADERS, PROPERTIES, invoiItem);
		    setRowHeight(23);
		    setFont(new Font("Tahoma", 0, 14));
		    getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		    ((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		    getColumnModel().getColumn(0).setMaxWidth(50);
		  }
	public TableInvoiceItems(List<InvoiceItem> invoiItem) {
		    init(HEADERS, PROPERTIES, invoiItem);
		    setRowHeight(23);
		    setFont(new Font("Tahoma", 0, 14));
		    getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		    ((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		  }
	  

	@Override
	protected InvoiceItem newBean() {
		return new InvoiceItem();
	}

	@Override
	protected boolean isBeanEditableAt(int row, int col) {
		return false;
	}

	

}
