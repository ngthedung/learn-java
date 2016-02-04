package com.hkt.client.swingexp.app.nhansu.list;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.ITableModel;
import com.hkt.client.swingexp.app.core.MyDate;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.hr.entity.Employee;

@SuppressWarnings("serial")
public class TableModelEmployees extends DefaultTableModel implements ITableModel {

	static String[] header = { "STT", "Tài khoản", "Tên nhân viên", "Số ĐT", "Ngày bắt đầu", "Vị trí", "Phòng ban",
	    "Ngày sinh", "Xóa" };
	private int size;
	private HashMap<String, Integer> numberSize = new HashMap<String, Integer>();
	private HashMap<String, String> list1 = new HashMap<String, String>();
	private HashMap<String, String> list2 = new HashMap<String, String>();

	public TableModelEmployees(List<Employee> list) {
		dataVector = convertToVector(list.toArray());
		numberSize.put(header[0], 50);
		numberSize.put(header[8], 50);
	}

	public void setData(List<Employee> list, int size) {
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
		if (column < header.length - 1) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void fireTableRowsUpdated(int row, int lastRow) {
		super.fireTableRowsUpdated(row, lastRow);
		try {
			String id = ((Employee) dataVector.get(row)).getLoginId();
			List<AccountMembership> accountMemberships = AccountModelManager.getInstance().findMembershipByAccountLoginId(id);

			for (AccountMembership ac : accountMemberships) {
				if (ac != null && ac.getGroupPath().indexOf(AccountModelManager.Department) > 0) {
					String str1 = "";
					if (ac != null) {
						str1 = ac.getRole();
					}
					list1.put(id, str1);
					String groupPath = ac.getGroupPath();
					AccountGroup accountGroup = AccountModelManager.getInstance().getGroupByPath(groupPath);
					list2.put(id, accountGroup.getLabel());
				}
			}
		} catch (Exception e) {
			list1.put(((Employee) dataVector.get(row)).getLoginId(), "");
			list2.put(((Employee) dataVector.get(row)).getLoginId(), "");
		}

	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		if (column == header.length - 1) {
			((Employee) dataVector.get(row)).setRecycleBin(Boolean.parseBoolean(aValue.toString()));
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
				return ((Employee) dataVector.get(row)).getLoginId();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 2:
			try {
				try {
					String id = ((Employee) dataVector.get(row)).getLoginId();
					List<AccountMembership> accountMemberships = AccountModelManager.getInstance()
					    .findMembershipByAccountLoginId(id);

					for (AccountMembership ac : accountMemberships) {
						if (ac != null && ac.getGroupPath().indexOf(AccountModelManager.Department) > 0) {
							if (list1.get(id) == null) {
								String str1 = "";
								if (ac != null) {
									str1 = ac.getRole();
								}
								list1.put(id, str1);
							}
							if (list2.get(id) == null) {
								String groupPath = ac.getGroupPath();
								AccountGroup accountGroup = AccountModelManager.getInstance().getGroupByPath(groupPath);
								list2.put(id, accountGroup.getLabel());
							}
						}
					}
				} catch (Exception e) {
					list1.put(((Employee) dataVector.get(row)).getLoginId(), "");
					list2.put(((Employee) dataVector.get(row)).getLoginId(), "");
				}

				return ((Employee) dataVector.get(row));
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 3:
			try {
				return ((Employee) dataVector.get(row)).getMobile();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 4:
			try {
				try {
					if (((Employee) dataVector.get(row)).getStartDate() != null) {
						return new MyDate(((Employee) dataVector.get(row)).getStartDate());
					} else {
						return new MyDate(null);
					}
				} catch (Exception e) {
					return new MyDate(null);
				}
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 5:
			try {
				return list1.get(((Employee) dataVector.get(row)).getLoginId());
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 6:
			try {
				return list2.get(((Employee) dataVector.get(row)).getLoginId());
			} catch (Exception e) {
				try {
					Vector rowVector = (Vector) dataVector.elementAt(row);
					return rowVector.elementAt(column);
				} catch (Exception e2) {
				}

			}

		case 7:
			try {
				try {
					if (((Employee) dataVector.get(row)).getBirthDay() != null) {
						return new MyDate(((Employee) dataVector.get(row)).getBirthDay());
					} else {
						return new MyDate(null);
					}
				} catch (Exception e) {
					return new MyDate(null);
				}
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		default:
			return ((Employee) dataVector.get(row)).isRecycleBin();
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return Integer.class;
		case 2:
			return Employee.class;
		case 4:
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
		return "DS nhân sự";
	}

}
