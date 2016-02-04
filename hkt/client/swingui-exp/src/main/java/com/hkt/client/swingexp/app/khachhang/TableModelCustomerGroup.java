package com.hkt.client.swingexp.app.khachhang;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.ITableModel;
import com.hkt.client.swingexp.app.core.MyDate;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.module.account.entity.Account;
import com.hkt.module.accounting.entity.Invoice;

@SuppressWarnings("serial")
public class TableModelCustomerGroup extends DefaultTableModel implements ITableModel{
	private String[] header = { "STT", "Mã", "Tên", "Số điện thoại", "Website", "Quốc gia", "Thành phố", "Ngày bắt đầu", "Ngày dừng", "Xóa" };
	private int size;
	private HashMap<String, Integer> numberSize = new HashMap<String, Integer>();
	private HashMap<String, String> list1 = new HashMap<String, String>();
	private HashMap<String, String> list2 = new HashMap<String, String>();
	private HashMap<String, String> list3 = new HashMap<String, String>();
	private HashMap<String, String> list4= new HashMap<String, String>();
	private HashMap<String, String> list5 = new HashMap<String, String>();
	private HashMap<String, String> list6 = new HashMap<String, String>();
	private HashMap<String, String> list7 = new HashMap<String, String>();
	
	public TableModelCustomerGroup(List<Account> listAccount) {
		dataVector = convertToVector(listAccount.toArray());
		numberSize.put(header[0], 50);
		numberSize.put(header[5], 150);
		numberSize.put(header[6], 120);
		numberSize.put(header[7], 120);
		numberSize.put(header[8], 120);
		numberSize.put(header[9], 50);

	}

	public void setData(List<Account> listAccount, int size) {
		this.size = size;
		dataVector = convertToVector(listAccount.toArray());
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
	public void fireTableRowsUpdated(int row, int lastRow) {
		super.fireTableRowsUpdated(row, lastRow);
		try {
			String id = ((Account) dataVector.get(row)).getLoginId();
				String name = AccountModelManager.getInstance().getNameByLoginId(id);
				list1.put(id, name);
				String phone = AccountModelManager.getInstance().getPhoneByLoginId(id);
				list2.put(id, phone);
				String web = AccountModelManager.getInstance().getWebsideByLoginId(id);
				list3.put(id, web);
				String country = AccountModelManager.getInstance().getCountryByLoginId(id);
				list4.put(id, country);
				String city = AccountModelManager.getInstance().getCityByLoginId(id);
				list5.put(id, city);
				String startday = AccountModelManager.getInstance().getStartdayByLoginId(id);
				list6.put(id, startday);
				String stopday = AccountModelManager.getInstance().getStopdayByLoginId(id);
				list7.put(id, stopday);
		} catch (Exception e) {
			list1.put(((Account) dataVector.get(row)).getLoginId(),"");
			list2.put(((Account) dataVector.get(row)).getLoginId(),"");
			list3.put(((Account) dataVector.get(row)).getLoginId(),"");
			list4.put(((Account) dataVector.get(row)).getLoginId(),"");
			list5.put(((Account) dataVector.get(row)).getLoginId(),"");
			list6.put(((Account) dataVector.get(row)).getLoginId(),"");
			list7.put(((Account) dataVector.get(row)).getLoginId(),"");
		}
	}

@Override
	public void setValueAt(Object aValue, int row, int column) {
	  if(column==header.length-1){
	    ((Account) dataVector.get(row)).setRecycleBin(Boolean.parseBoolean(aValue.toString()));
	  }
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getValueAt(int row, int column) {
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
				try {
					String id = ((Account) dataVector.get(row)).getLoginId();
					if(list1.get(id) == null)
					{
						String name = AccountModelManager.getInstance().getNameByLoginId(id);
						list1.put(id, name);
					}
					if(list2.get(id) == null)
					{
						String phone = AccountModelManager.getInstance().getPhoneByLoginId(id);
						list2.put(id, phone);
					}
					if(list3.get(id) ==null)
					{
						String web = AccountModelManager.getInstance().getWebsideByLoginId(id);
						list3.put(id, web);
					}
					if(list4.get(id) ==null)
					{
						String country = AccountModelManager.getInstance().getCountryByLoginId(id);
						list4.put(id, country);
					}
					if(list5.get(id) ==null)
					{
						String city = AccountModelManager.getInstance().getCityByLoginId(id);
						list5.put(id, city);
					}
					if(list6.get(id) ==null)
					{
						String startday = AccountModelManager.getInstance().getStartdayByLoginId(id);
						list6.put(id, startday);
					}
					if(list7.get(id) ==null)
					{
						String stopday = AccountModelManager.getInstance().getStopdayByLoginId(id);
						list7.put(id, stopday);
					}
				} catch (Exception e) {
					list1.put(((Account) dataVector.get(row)).getLoginId(),"");
					list2.put(((Account) dataVector.get(row)).getLoginId(),"");
					list3.put(((Account) dataVector.get(row)).getLoginId(),"");
					list4.put(((Account) dataVector.get(row)).getLoginId(),"");
					list5.put(((Account) dataVector.get(row)).getLoginId(),"");
					list6.put(((Account) dataVector.get(row)).getLoginId(),"");
					list7.put(((Account) dataVector.get(row)).getLoginId(),"");
				}
				return dataVector.get(row);
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 2:
			try {
				return list1.get(((Account) dataVector.get(row)).getLoginId());
			} catch (Exception e) {
				return new Account();
			}

		case 3:
			try {
				return list2.get(((Account) dataVector.get(row)).getLoginId());
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 4:
			try {
				return list3.get(((Account) dataVector.get(row)).getLoginId());

			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 5:
			try {
				return list4.get(((Account) dataVector.get(row)).getLoginId());
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 6:
			try {
				return list5.get(((Account) dataVector.get(row)).getLoginId());
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 7:
			try {
				return list6.get(((Account) dataVector.get(row)).getLoginId());
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 8:
			try {
				return list7.get(((Account) dataVector.get(row)).getLoginId());
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		default:
			return ((Account) dataVector.get(row)).isRecycleBin();
		}
	}
	
	@Override
	  public Class<?> getColumnClass(int columnIndex) {
	    switch (columnIndex) {
	    case 0:
		      return Integer.class;
	    case 1:
		      return Account.class;
	    case 9:
		      return Boolean.class;
	    default:
	      return String.class;
	    }
	  }

	@Override
	public HashMap<String, Integer> getColumnSize() {
		return numberSize;
	}

	@Override
	public String getNameTableModel() {
		return "DS Doanh nghiệp";
	}

}
