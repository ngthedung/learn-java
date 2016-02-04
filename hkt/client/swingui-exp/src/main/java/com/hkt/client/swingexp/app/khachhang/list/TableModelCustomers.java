package com.hkt.client.swingexp.app.khachhang.list;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.ITableModel;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.account.entity.Account.Type;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.supplier.entity.Supplier;

public class TableModelCustomers extends DefaultTableModel implements ITableModel{
	 static String[] header = { "STT", "Tên đối tác", "Là DN/cá nhân", "Nhóm" };
 	 
	private int size;
	private HashMap<String, Integer> numberSize = new HashMap<String, Integer>();
	public TableModelCustomers(List<Customer> list){
		dataVector = convertToVector(list.toArray());
		
		numberSize.put(header[0], 50);
	}
	
	public void setData(List<Customer> list, int size){
		this.size = size;
		dataVector = convertToVector(list.toArray());
		fireTableDataChanged();
	}
	@Override
	public int getColumnCount() {
		return header == null ? 0 : header.length;
	}

	@Override
	public String getColumnName(int column) {
		return header[column];
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		if (column < header.length-1) {
			return false;
		} else {
			return true;
		}
	}
  
  @Override
	public void setValueAt(Object aValue, int row, int column) {
	  if(column==header.length-1){
	    ((Supplier) dataVector.get(row)).setRecycleBin(Boolean.parseBoolean(aValue.toString()));
	  }
	}
  
	@SuppressWarnings("rawtypes")
	@Override
	public Object getValueAt(int row, int column){
		switch (column) {
		case 0:
			try {
				return row + 1 + size;
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 1:
			try {
				return ((Customer) dataVector.get(row)).getName();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 2:
			try {
				return ((Customer) dataVector.get(row)).getType();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 3:
//		      String loginId = super.getValueAt(row, column).toString();
		      try {
		    	  return ((Customer) dataVector.get(row));
//		        List<AccountMembership> lstMemberShip = AccountModelManager.getInstance().findMembershipByAccountLoginId(
//		            loginId);
//		        String groupPath = null;
//		        for (int i = 0; i < lstMemberShip.size(); i++) {
//		          groupPath = lstMemberShip.get(i).getGroupPath();
//		        }
//		        AccountGroup acc = new AccountGroup();
//		        acc = AccountModelManager.getInstance().getGroupByPath(groupPath);
//		        if (acc != null) {
//		          return acc.getLabel();
//		        } else {
//		          return acc.getLabel();
//		        }
		        // return
		        // AccountModelManager.getInstance().getNameByLoginId(super.getValueAt(row,
		        // column).toString());
		      } catch (Exception e) {

		        return "";
		      }

		default:
			return ((Customer) dataVector.get(row)).isRecycleBin();
		}
	}
  
	@Override
	public HashMap<String, Integer> getColumnSize() {
	
		return numberSize;
	}
	@Override
	public String getNameTableModel() {
		// TODO Auto-generated method stub
		return "Test Table";
	}

}
