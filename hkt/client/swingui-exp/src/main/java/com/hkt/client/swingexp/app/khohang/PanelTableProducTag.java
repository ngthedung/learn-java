package com.hkt.client.swingexp.app.khohang;

import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.ProductServiceClient;
import com.hkt.client.swing.widget.BeanBindingJTable;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.module.product.entity.ProductTag;

@SuppressWarnings("serial")
public class PanelTableProducTag extends BeanBindingJTable<ProductTag> {
	
  private ProductServiceClient clientCore = ClientContext.getInstance().getBean(ProductServiceClient.class);

  static String[] HEADERS = { "STT", "Mã", "Tên ", "Nhóm cha","Miêu tả" };
  static String[] PROPERTIES = { "id", "code", "label", "tag","description" };

  public void setData(List<ProductTag> proTag) {
    init(HEADERS, PROPERTIES, proTag);
    setRowHeight(23);
    setFont(new Font("Tahoma", 0, 14));
    getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
    ((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
    getColumnModel().getColumn(0).setMaxWidth(50);

  }

  public PanelTableProducTag(List<ProductTag> proTag) {
    init(HEADERS, PROPERTIES, proTag);
    setRowHeight(23);
    setFont(new Font("Tahoma", 0, 14));
    getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
    ((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
  }

  public PanelTableProducTag() {
  }

  protected ProductTag newBean() {
    return new ProductTag();
  }

  protected boolean isBeanEditableAt(int row, int col) {
    return false;
  }

  @Override
  public Object getBeanValueAt(int row, int column) {
    if(column==3){
      String str = "";
      try {
        str = ProductModelManager.getInstance().getProductTagParent(getBeans().get(row)).getLabel();
      } catch (Exception e) {
      }
      return str;
    }else {
      return super.getBeanValueAt(row, column);  
    }
    
  }
  
  @Override
  public void saveIndex() {
    try {
      for (int i = 0; i < getBeans().size(); i++) {
    	  getBeans().get(i).setPriority(i+1);
    	  clientCore.saveProductTag(getBeans().get(i));
      }
     
//      DialogNotice.getInstace().setVisible(true);
    } catch (Exception e) {
    }
  }
  
  
}
