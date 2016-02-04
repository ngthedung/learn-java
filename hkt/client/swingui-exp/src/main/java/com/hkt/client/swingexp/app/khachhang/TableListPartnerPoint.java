package com.hkt.client.swingexp.app.khachhang;

import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.swing.widget.BeanBindingJTable;
import com.hkt.module.partner.customer.entity.PointConversionRule;

@SuppressWarnings("serial")
public class TableListPartnerPoint extends BeanBindingJTable<PointConversionRule> {
	  
	  static String[] HEADERS = {"STT", "Tên tỷ lệ", "Doanh thu", "Điểm TT",  "Từ ngày", "Đến ngày"};
	  static String[] PROPERTIES = { "id","name", "total", "pointToCreditRatio", "dateFrom", "dateTo"};
	  
	  public void setListPartnerPoint (List<PointConversionRule> taxs) {
	    init(HEADERS, PROPERTIES, taxs);
	    setRowHeight(23);
	    setFont(new Font("Tahoma", 0, 14));
	    getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
	    ((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
	    getColumnModel().getColumn(0).setMaxWidth(50);
	    getColumnModel().getColumn(3).setMaxWidth(80);
	    getColumnModel().getColumn(4).setMaxWidth(100);
	    getColumnModel().getColumn(4).setMinWidth(100);
	    getColumnModel().getColumn(5).setMaxWidth(100);
	    getColumnModel().getColumn(5).setMinWidth(100);
	  }
	  
	  public TableListPartnerPoint(List<PointConversionRule> point) {
	    init(HEADERS, PROPERTIES, point);
	    setRowHeight(23);
	    setFont(new Font("Tahoma", 0, 14));
	    getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
	    ((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
	  }
	  
	  public TableListPartnerPoint(){
	  	
	  }
	  
	  
	  protected PointConversionRule newBean() {
	    return new PointConversionRule();
	  }
	  
	  protected boolean isBeanEditableAt(int row, int col) {
	    return false;
	  }
	}
