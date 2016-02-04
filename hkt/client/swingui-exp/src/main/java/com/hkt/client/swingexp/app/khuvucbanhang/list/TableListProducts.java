package com.hkt.client.swingexp.app.khuvucbanhang.list;

import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.swing.widget.BeanBindingJTable;
import com.hkt.module.product.entity.ProductTag;

@SuppressWarnings("serial")
public class TableListProducts extends BeanBindingJTable<ProductTag> {
	  
	  static String[] HEADERS = { "STT", "Mã nhóm", "Tên nhóm" };
	  static String[] PROPERTIES = { "id", "tag", "label" };
	  
	  public void setProductTags(List<ProductTag> ProductTags) {
	    init(HEADERS, PROPERTIES, ProductTags);
	    setRowHeight(23);
	    setFont(new Font("Tahoma", 0, 14));
	    getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
	    ((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
	    getColumnModel().getColumn(0).setMaxWidth(50);
	  }
	  
	  public TableListProducts(List<ProductTag> productTags) {
	    init(HEADERS, PROPERTIES, productTags);
	    setRowHeight(23);
	    setFont(new Font("Tahoma", 0, 14));
	    getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
	    ((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
	  }
	  
	  protected ProductTag newBean() {
	    return new ProductTag();
	  }
	  
	  protected boolean isBeanEditableAt(int row, int col) {
	    return false;
	  }
}
