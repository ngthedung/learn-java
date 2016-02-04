package com.hkt.client.swingexp.app.hethong.list;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.ITableModel;
import com.hkt.client.swingexp.app.core.MyDate;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.accounting.entity.Invoice;

@SuppressWarnings("serial")
public class TableModelDepartment extends DefaultTableModel implements ITableModel{
	private String[] header = { "STT","Mã", "Tên", "Phòng trực thuộc", "Doanh nghiệp", "Miêu tả", "Xóa" };
	private int size;
	private HashMap<String, Integer> numberSize = new HashMap<String, Integer>();
	private HashMap<String, String> list1 = new HashMap<String, String>();
	private HashMap<String, String> list2 = new HashMap<String, String>();
	
	public TableModelDepartment(List<AccountGroup> department) {
		dataVector = convertToVector(department.toArray());
		numberSize.put(header[0], 50);
		numberSize.put(header[6], 50);
	}

	public void setData(List<AccountGroup> department, int size) {
		this.size = size;
		dataVector = convertToVector(department.toArray());
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
	    ((AccountGroup) dataVector.get(row)).setRecycleBin(Boolean.parseBoolean(aValue.toString()));
	  }
	}

  
  
	@Override
public void fireTableRowsUpdated(int row, int lastRow) {
	super.fireTableRowsUpdated(row, lastRow);
	try {
		String str = AccountModelManager.getInstance().getGroupByPath(((AccountGroup) dataVector.get(row)).getParentPath()).toString();
		list1.put(((AccountGroup) dataVector.get(row)).getParentPath(), str);
	} catch (Exception e) {
		list1.put(((AccountGroup) dataVector.get(row)).getParentPath(), "");
	}
	try {
		String str = AccountModelManager.getInstance().getNameByLoginId(((AccountGroup) dataVector.get(row)).getOwner());
		list2.put(((AccountGroup) dataVector.get(row)).getParentPath(), str);
	} catch (Exception e) {
		list2.put(((AccountGroup) dataVector.get(row)).getParentPath(), "");
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
				return ((AccountGroup) dataVector.get(row)).getName();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 2:
			try {
				try {
					if(list1.get(((AccountGroup) dataVector.get(row)).getParentPath()) ==null)
					{
					String str = AccountModelManager.getInstance().getGroupByPath(((AccountGroup) dataVector.get(row)).getParentPath()).toString();
					list1.put(((AccountGroup) dataVector.get(row)).getParentPath(), str);
					}
				} catch (Exception e) {
					list1.put(((AccountGroup) dataVector.get(row)).getParentPath(), "");
				}
				try {
					if(list2.get(((AccountGroup) dataVector.get(row)).getParentPath()) ==null)
					{
					String str = AccountModelManager.getInstance().getNameByLoginId(((AccountGroup) dataVector.get(row)).getOwner());
					list2.put(((AccountGroup) dataVector.get(row)).getParentPath(), str);
					}
				} catch (Exception e) {
					list2.put(((AccountGroup) dataVector.get(row)).getParentPath(), "");
				}
				return ((AccountGroup) dataVector.get(row));
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 3:
			try {
				try {
					String str = list1.get(((AccountGroup) dataVector.get(row)).getParentPath());
					if (str.equals("Nhóm phòng ban")) {
						return "";
					} else
						return str;
				} catch (Exception e) {
					return "";
				}

			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 4:
			try {
				return list2.get(((AccountGroup) dataVector.get(row)).getOwner());
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 5:
			try {
				return ((AccountGroup) dataVector.get(row)).getDescription();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		default:
			return ((AccountGroup) dataVector.get(row)).isRecycleBin();
		}
	}
	
	@Override
	  public Class<?> getColumnClass(int columnIndex) {
	    switch (columnIndex) {
	    case 0:
		      return Integer.class;
	    case 2:
		      return AccountGroup.class;
	    case 6:
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
		return "DS phòng ban";
	}
}
