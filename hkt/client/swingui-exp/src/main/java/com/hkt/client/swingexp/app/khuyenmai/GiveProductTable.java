package com.hkt.client.swingexp.app.khuyenmai;

import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.LocaleServiceClient;
import com.hkt.client.rest.service.PromotionServiceClient;
import com.hkt.client.swing.widget.BeanBindingJTable;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.module.promotion.entity.PromotionGiveaways;



//khuyen mai tang san pham
public class GiveProductTable extends BeanBindingJTable<PromotionGiveaways> {
	
	
		private PromotionServiceClient clientCore = ClientContext.getInstance().getBean(PromotionServiceClient.class);
	  static String[] HEADERS = { "STT", "Sản Phẩm", "SL Mua", "SL Tặng","Từ","Đến","Miêu tả"};
	  static String[] PROPERTIES = {"id", "name", "quantityBuy", "quantityGive","fromDate","toDate","description" };
	  
	  public void setPromotionGiveaways(List<PromotionGiveaways> giveproducts) {
	    init(HEADERS, PROPERTIES, giveproducts);
	    setRowHeight(23);
	    setFont(new Font("Tahoma", 0, 14));
	    getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
	    ((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		  getColumnModel().getColumn(0).setMaxWidth(40);
		  getColumnModel().getColumn(1).setMinWidth(110);
		  getColumnModel().getColumn(2).setMaxWidth(60);
		  getColumnModel().getColumn(3).setMaxWidth(80);
		  getColumnModel().getColumn(4).setMinWidth(90);
		  getColumnModel().getColumn(5).setMinWidth(90);
	  }
	  
	  public GiveProductTable(List<PromotionGiveaways> GiveProducts) {
	    init(HEADERS, PROPERTIES, GiveProducts);
	    setRowHeight(23);
	    setFont(new Font("Tahoma", 0, 14));
	    getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
	    ((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
	  }
	  
	  public GiveProductTable(){

	  }
	  
	  
	  protected PromotionGiveaways newBean() {
	    return new PromotionGiveaways();
	  }
	  
	  protected boolean isBeanEditableAt(int row, int col) {
	    return false;
	  }
	  
	  @Override
	  public void saveIndex() {
	    try {
	      for (int i = 0; i < getBeans().size(); i++) {
	    	  getBeans().get(i).setPriority(i+1);
	    	  clientCore.savePromotionGiveaways(getBeans().get(i));
	      }
	     
	      DialogNotice.getInstace().setVisible(true);
	    } catch (Exception e) {
	    }
	  }

}
