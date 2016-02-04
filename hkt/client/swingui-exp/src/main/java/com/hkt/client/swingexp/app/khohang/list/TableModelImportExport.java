package com.hkt.client.swingexp.app.khohang.list;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.ITableModel;
import com.hkt.client.swingexp.app.core.MyDate;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.GenericOptionModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.SupplierModelManager;
import com.hkt.client.swingexp.model.WarehouseModelManager;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.accounting.entity.Contributor;
import com.hkt.module.accounting.entity.Invoice;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.Invoice.ActivityType;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.supplier.entity.Supplier;
import com.hkt.module.warehouse.entity.Warehouse;

@SuppressWarnings("serial")
public class TableModelImportExport extends DefaultTableModel implements ITableModel {
	private int size;
	private DateFormat dfTime = new SimpleDateFormat("HH:mm");
	private HashMap<String, Integer> numberSize = new HashMap<String, Integer>();
	private HashMap<String, String> list1 = new HashMap<String, String>();
	private HashMap<String, String> list5 = new HashMap<String, String>();
	private HashMap<String, String> list7 = new HashMap<String, String>();

	private String[] header = { "Stt", "Mã lô hàng", "Tên lô hàng", "Loại", "Ngày TH", "Giờ","Đối tác", "Sdt","Kho" };

	public TableModelImportExport(List<Invoice> eventorys) {
		dataVector = convertToVector(eventorys.toArray());

		numberSize.put(header[0], 50);
		numberSize.put(header[1], 120);
		numberSize.put(header[3], 100);
		numberSize.put(header[7], 100);
		numberSize.put(header[4], 100);
		numberSize.put(header[5], 60);
	
	}

	public void setData(List<Invoice> invoices, int size) {
		this.size = size;
		dataVector = convertToVector(invoices.toArray());
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
	public void setValueAt(Object aValue, int row, int column) {
		if (column == header.length - 1) {
			((Invoice) dataVector.get(row)).setRecycleBin(Boolean.parseBoolean(aValue.toString()));
		}
	}

	@Override
	public void fireTableRowsUpdated(int firstRow, int lastRow) {
		super.fireTableRowsUpdated(firstRow, lastRow);
		Invoice invoice = AccountingModelManager.getInstance().getInvoiceDetail(
		    ((Invoice) dataVector.get(firstRow)).getId());

		try {
			String sdt = "";
			if (((Invoice) dataVector.get(firstRow)).getActivityType() == ActivityType.Receipt) {
				Customer customer = CustomerModelManager.getInstance().getCustomerByCode(invoice.getCustomerCode());
				sdt = customer.getMobile();
			} else {
				Supplier s = SupplierModelManager.getInstance().getSupplierByCode(invoice.getSupplierCode());
				sdt = s.getMobile();
			}
			list5.put(((Invoice) dataVector.get(firstRow)).getInvoiceCode(), sdt);
		} catch (Exception e) {
			list5.put(((Invoice) dataVector.get(firstRow)).getInvoiceCode(), "");
		}

		try {
			String nameC = "";
			if (((Invoice) dataVector.get(firstRow)).getActivityType() == ActivityType.Receipt) {
				Customer customer = CustomerModelManager.getInstance().getCustomerByCode(invoice.getCustomerCode());
				nameC = customer.toString();
			} else {
				Supplier s = SupplierModelManager.getInstance().getSupplierByCode(invoice.getSupplierCode());
				nameC = s.toString();
			}
			list1.put(((Invoice) dataVector.get(firstRow)).getInvoiceCode(), nameC);

		} catch (Exception e) {
			list1.put(((Invoice) dataVector.get(firstRow)).getInvoiceCode(), "");
		}
		try {
			Warehouse w = WarehouseModelManager.getInstance().getWarehouseByWarehouseId(
			    ((Invoice) dataVector.get(firstRow)).getWarehouseId());
			list7.put(((Invoice) dataVector.get(firstRow)).getInvoiceCode(), w.getName());

		} catch (Exception e) {
			list7.put((((Invoice) dataVector.get(firstRow)).getInvoiceCode()), "");
		}
		((Invoice) dataVector.get(firstRow)).setInvoiceName(invoice.getInvoiceName());
		fireTableDataChanged();
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
				String code = ((Invoice) dataVector.get(row)).getInvoiceCode();
				if (code.indexOf(":") > 0) {
					return code.substring(code.indexOf(":") + 1);
				} else {
					return code;
				}

			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 2:
			try {
				try {
					Invoice invoice = (Invoice) dataVector.get(row);
					String invoiceCode = invoice.getInvoiceCode();

					try {
						if (list5.get(((Invoice) dataVector.get(row)).getInvoiceCode()) == null) {
							String sdt = "";
							if (((Invoice) dataVector.get(row)).getActivityType() == ActivityType.Receipt) {
								if (!invoice.getCustomerCode().isEmpty()) {
									Customer customer = CustomerModelManager.getInstance().getCustomerByCode(invoice.getCustomerCode());
									sdt = customer.getMobile();
								} else
									list5.put(invoiceCode, "");
							} else {
								if (!invoice.getSupplierCode().isEmpty()) {
									Supplier s = SupplierModelManager.getInstance().getSupplierByCode(invoice.getSupplierCode());
									sdt = s.getMobile();
								} else
									list5.put(invoiceCode, "");
							}
							list5.put(invoiceCode, sdt);
						}
					} catch (Exception e) {
						list5.put(invoiceCode, "");
					}

					try {
						if (list7.get(invoiceCode) == null) {
							Warehouse w = WarehouseModelManager.getInstance().getWarehouseByWarehouseId(
							    ((Invoice) dataVector.get(row)).getWarehouseId());
							list7.put(((Invoice) dataVector.get(row)).getInvoiceCode(), w.getName());
						}
					} catch (Exception e) {
						list7.put(invoiceCode, "");
					}
					try {
						if (list1.get(invoiceCode) == null) {
							String nameC = "";
							if (invoice.getActivityType() == ActivityType.Receipt) {
								if (!invoice.getCustomerCode().isEmpty()) {
									Customer customer = CustomerModelManager.getInstance().getCustomerByCode(invoice.getCustomerCode());
									nameC = customer.toString();
								} else
									list1.put(invoiceCode, "");
							} else {
								if (!invoice.getSupplierCode().isEmpty()) {
									Supplier s = SupplierModelManager.getInstance().getSupplierByCode(invoice.getSupplierCode());
									nameC = s.toString();
								} else
									list1.put(invoiceCode, "");
							}

							list1.put(invoiceCode, nameC);
						}
					} catch (Exception e) {
						list1.put(invoiceCode, "");
					}
					return ((Invoice) dataVector.get(row));
				} catch (Exception e) {
					return new Invoice();
				}
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 3:
			try {
				try {
					if (((Invoice) dataVector.get(row)).getActivityType().equals(ActivityType.Payment)) {
						return "Nhập";
					} else {
						return "Xuất";
					}
				} catch (Exception e) {
					return "";
				}
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 6:
			try {
				try {
					return list1.get(((Invoice) dataVector.get(row)).getInvoiceCode());
				} catch (Exception e) {
					return "";
				}
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 7:
			try {
				try {
					return list5.get(((Invoice) dataVector.get(row)).getInvoiceCode());
				} catch (Exception e) {
					return "";
				}
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 8:
			try {
				try {
					return list7.get(((Invoice) dataVector.get(row)).getInvoiceCode());

				} catch (Exception e) {
					return "";
				}
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 4:
			try {
				try {
					return new MyDate(((Invoice) dataVector.get(row)).getStartDate());
				} catch (Exception e) {
					return new MyDate(null);
				}

			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 5:
			try {
				try {
					return dfTime.format(((Invoice) dataVector.get(row)).getStartDate());
				} catch (Exception e) {
					return new MyDate(null);
				}

			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		default:
			try {
				return ((Invoice) dataVector.get(row)).isRecycleBin();
			} catch (Exception e) {
				return "";
			}

		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return Integer.class;
		case 2:
			return Invoice.class;
		case 4:
			return MyDate.class;
		case 5:
			return MyDate.class;
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
		return "Phiếu xuất nhập kho";
	}

}
