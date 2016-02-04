package com.hkt.client.swingexp.app.banhang.screen.often;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.ITableModel;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.module.accounting.entity.BankAccount;

public class TableModelDeleteInvoice extends DefaultTableModel  implements ITableModel{

	private String[] header = { "STT","Nhân viên tạo","NV Xóa", "Tên hóa đơn", "Mã hóa đơn", "Món", "Giờ vào", "Giờ ra","Giờ xóa","Xóa" };
 private List<BankAccount> listBankAccount;
	private HashMap<String, Integer> numberSize = new HashMap<String, Integer>();
 
	public List<BankAccount> getListBankAccount() {
	return listBankAccount;
}

public void setListBankAccount(List<BankAccount> listBankAccount) {
	this.listBankAccount = listBankAccount;
}

	public TableModelDeleteInvoice() {
		listBankAccount = AccountingModelManager.getInstance().getBankAccounts();
		dataVector = convertToVector(listBankAccount.toArray());
		numberSize.put(header[0], 50);
		numberSize.put(header[9], 50);
	}

	public void setData(List<BankAccount> listBankAccount) {
//		dataVector = convertToVector(listBankAccount.toArray());
//		fireTableDataChanged();
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
		return false;
	}

	@Override
	public Object getValueAt(int row, int column) {
		switch (column) {
		case 0: return row +1;
		case 1:
			try {
				return HRModelManager.getInstance().getEmployeeByCode(((BankAccount) dataVector.get(row)).getAddress());
			} catch (Exception e) {
				return "";
			}
		case 2:
			try {
				return AccountModelManager.getInstance().getAccountByLoginId(((BankAccount) dataVector.get(row)).getEmployeeDelete());
			} catch (Exception e) {
				return "";
			}
		case 3:
			try {
				return ((BankAccount) dataVector.get(row)).getCurrency();
			} catch (Exception e) {
				return "";
			}

		case 4:
			try {
				return ((BankAccount) dataVector.get(row)).getCode();
			} catch (Exception e) {
				return "";
			}

		case 5:
			try {
				return ((BankAccount) dataVector.get(row)).getCreatedBy();
			} catch (Exception e) {
				return "";
			}

		case 6:
			try {
				return ((BankAccount) dataVector.get(row)).getModifiedBy();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 7:
			try {
				return ((BankAccount) dataVector.get(row)).getBankAccountId();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 8:
			try {
				return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(((BankAccount) dataVector.get(row)).getCreatedTime());
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 9:
			try {
				return ((BankAccount) dataVector.get(row)).isRecycleBin();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		default:
			return "";
		}
	}

	@Override
	public HashMap<String, Integer> getColumnSize() {
		return numberSize;
	}

	@Override
	public String getNameTableModel() {
		return "Xóa hóa đơn";
	}

}
