package com.hkt.client.swingexp.app.khachhang;

import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.AccountServiceClient;
import com.hkt.client.swing.widget.BeanBindingJTable;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.module.account.entity.AccountGroup;

@SuppressWarnings("serial")
public class TableGroupPartner extends BeanBindingJTable<AccountGroup> {
	
	 private AccountServiceClient clientCore = ClientContext.getInstance().getBean(AccountServiceClient.class);
	
  static String[] HEADERS = { "STT", "Mã", "Tên", "Miêu tả" };
  static String[] PROPERTIES = { "id", "name", "label", "description" };
  
  public void setAccountGroups(List<AccountGroup> accountGroups) {
    init(HEADERS, PROPERTIES, accountGroups);
    setRowHeight(23);
    setFont(new Font("Tahoma", 0, 14));
    getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
    ((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
    getColumnModel().getColumn(0).setMaxWidth(50);
  }
  
  public TableGroupPartner(List<AccountGroup> accountGroups) {
    init(HEADERS, PROPERTIES, accountGroups);
    setRowHeight(23);
    setFont(new Font("Tahoma", 0, 14));
    getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
    ((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
  }
  
  public TableGroupPartner(){
  	
  }
  
  protected AccountGroup newBean() {
    return new AccountGroup();
  }
  
  protected boolean isBeanEditableAt(int row, int col) {
    return false;
  }
  
  public void saveIndex(){
  	try{
  		for(int i = 0; i < getBeans().size();i++){
  			getBeans().get(i).setPriority(i);
  			clientCore.saveGroup(getBeans().get(i));
  			}
  		 DialogNotice.getInstace().setVisible(true);
  	}catch(Exception e){
  		e.printStackTrace();
  	}
  }
  
}
