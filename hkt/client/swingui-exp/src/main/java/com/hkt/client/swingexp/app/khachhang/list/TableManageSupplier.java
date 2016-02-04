package com.hkt.client.swingexp.app.khachhang.list;

import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.swing.widget.BeanBindingJTable;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.account.entity.Account.Type;
import com.hkt.module.partner.supplier.entity.Supplier;

@SuppressWarnings("serial")
public class TableManageSupplier extends BeanBindingJTable<Supplier> {
    static String[] HEADERS = { "STT", "Tên đối tác", "Là DN/cá nhân"};
    static String[] PROPERTIES = { "id", "name", "organizationLoginId"};
    
    static String[] HEADERSP = { "STT", "Tên đối tác", "Là DN/cá nhân", "Nhóm hiện tại"};
    static String[] PROPERTIESP = { "id", "name", "organizationLoginId", "loginId" };
    
    public void setSuppliers(List<Supplier> supplier, int fillter) {
    	if(fillter ==1){
    		init(HEADERS, PROPERTIES, supplier);
    	}else {
    		init(HEADERSP, PROPERTIESP, supplier);
		}
      
      setRowHeight(23);
      setFont(new Font("Tahoma", 0, 14));
      getColumnModel().getColumn(0).setMaxWidth(50);
      getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
      ((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
    }
    
    public TableManageSupplier(List<Supplier> supplier, int fillter) {
    	setSuppliers(supplier, fillter);
//      init(HEADERS, PROPERTIES, supplier);
//      setRowHeight(23);
//      setFont(new Font("Tahoma", 0, 14));
//      getColumnModel().getColumn(0).setMaxWidth(80);
//      getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
//      ((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
    }
    
    protected boolean isBeanEditableAt(int row, int col) {
      return false;
    }

	protected Supplier newBean() {
		return new Supplier();
	}

	@Override
	public Object getValueAt(int row, int column) {
	  if (column == 2) {
      try {
        if (AccountModelManager.getInstance().getAccountByLoginId(super.getValueAt(row, column).toString()).getType()
            .equals(Type.USER)) {
          return "Cá nhân";
        } else {
          return "Doanh nghiệp";
        }
      } catch (Exception e) {
        return "";
      }

    }else {
			return super.getBeanValueAt(row, column);
		}
		
		
		
	}
	
}