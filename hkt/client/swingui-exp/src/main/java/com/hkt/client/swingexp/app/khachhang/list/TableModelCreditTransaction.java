package com.hkt.client.swingexp.app.khachhang.list;


import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.ITableModel;
import com.hkt.client.swingexp.app.core.MyDate;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.module.account.entity.Account;
import com.hkt.module.partner.customer.entity.CreditTransaction;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.customer.entity.PointTransaction;
import com.hkt.util.text.StringUtil;

@SuppressWarnings("serial")
public class TableModelCreditTransaction extends DefaultTableModel implements ITableModel{
	private String[] header = { "STT", "Khách hàng", "Mã hóa đơn", "Tên hóa đơn", "Số điện thoại", "Số tiền SD", "Loại", "Ngày thực hiện", "Xóa" };
	private int size;
	private HashMap<String, Integer> numberSize = new HashMap<String, Integer>();
	private HashMap<String, String> list1= new HashMap<String, String>();
	private HashMap<String, String> list2= new HashMap<String, String>();
	
	public TableModelCreditTransaction(List<CreditTransaction> listCreditTransaction) {
		dataVector = convertToVector(listCreditTransaction.toArray());
		numberSize.put(header[0], 50);
		numberSize.put(header[4], 180);
		numberSize.put(header[5], 200);
		numberSize.put(header[8], 50);
	}

	public void setData(List<CreditTransaction> listCreditTransaction, int size) {
		this.size = size;
		dataVector = convertToVector(listCreditTransaction.toArray());
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
					String id = ((PointTransaction) dataVector.get(row)).getLoginId();
					String loginId = ((CreditTransaction) dataVector.get(row)).getLoginId();
					String cus = CustomerModelManager.getInstance().getBydLoginId(loginId).getName();
					list1.put(loginId, cus);
					Account account = AccountModelManager.getInstance().getAccountByLoginId(id);
					String mobile =  StringUtil.joinStringArray(account.getContacts().get(0).getMobile(), ",");
					list2.put(id, mobile);
			} catch (Exception e) {
				list1.put(((PointTransaction) dataVector.get(row)).getLoginId(), "");
				list2.put(((PointTransaction) dataVector.get(row)).getLoginId(), "");
			}
		}
	
  @Override
	public void setValueAt(Object aValue, int row, int column) {
	  if(column==header.length-1){
	    ((CreditTransaction) dataVector.get(row)).setRecycleBin(Boolean.parseBoolean(aValue.toString()));
	  }
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getValueAt(int row, int column) {
		switch (column) {
		case 0:
			try {
				return row + 1 +size;
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 1:	
			
			try {
				String id = ((PointTransaction) dataVector.get(row)).getLoginId();
				String loginId = ((CreditTransaction) dataVector.get(row)).getLoginId();
				try {
					
					if(list1.get(loginId) == null)
					{
						String cus = CustomerModelManager.getInstance().getBydLoginId(loginId).getName();
						list1.put(loginId, cus);
					}
					if(list2.get(id)==null)
					{
						Account account = AccountModelManager.getInstance().getAccountByLoginId(id);
						String mobile =  StringUtil.joinStringArray(account.getContacts().get(0).getMobile(), ",");
						list2.put(id, mobile);
					}
				} catch (Exception e) {
					list1.put(((CreditTransaction) dataVector.get(row)).getLoginId(), "");
					list2.put(((PointTransaction) dataVector.get(row)).getLoginId(), "");
				}
				if(list1.get(loginId)!= null){
					return list1.get(loginId);
				} else {
					return "";
				}
				
			} catch (Exception e) {
				return "";
			}
			

		case 2:
			try {
				return ((CreditTransaction) dataVector.get(row)).getInvoiceId();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}


		case 3:
			try {
				return ((CreditTransaction) dataVector.get(row)).getCreditId();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 4:
			try {
				return list2.get(((PointTransaction) dataVector.get(row)).getLoginId());
			} catch (Exception e) {
				return "Chưa có";
			}

		case 5:
			try {
				return new MyDouble(((CreditTransaction) (dataVector.get(row))).getAmount()).toString();
				// return (CreditTransaction) dataVector.get(row);

			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 6:
			try {
				String str = "";
				if (((CreditTransaction) dataVector.get(row)).getAmount() < 0) {
					return str = "Giảm đi";
				}
				if (((CreditTransaction) dataVector.get(row)).getAmount() > 0) {
					return str = "Cộng thêm";
				} else
					str = "0";

				return str;

			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 7:
			try {
				if(((CreditTransaction) dataVector.get(row)).getDateExecute()!=null){
					return new MyDate(((CreditTransaction) dataVector.get(row)).getDateExecute());
				}else {
					return new MyDate(null);
				}
				
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		default:
			return ((CreditTransaction) dataVector.get(row)).isRecycleBin();
		}

	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return Integer.class;
		case 1:
			return Customer.class;
		case 3:
			return CreditTransaction.class;
		case 5:
			return MyDouble.class;
		case 7:
			return MyDate.class;
		case 8:
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
		return "DS trả trước";
	}
}