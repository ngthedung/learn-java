package com.hkt.client.swingexp.app.banhang.screen.often;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.banhang.list.ChooseDelProduct;
import com.hkt.client.swingexp.app.core.DialogJurisdiction;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.accounting.entity.InvoiceTransaction;
import com.hkt.module.accounting.entity.InvoiceTransaction.TransactionType;

public class TableModelPayment extends DefaultTableModel {

	private String[] header = { "Loại", "Ngày", "Giờ", "Nhân viên", "Tổng tiền", "Đơn vị" };
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat df1 = new SimpleDateFormat("HH:mm");
	private HashMap<String, String> list = new HashMap<String, String>();
	private InvoiceDetail invoiceDetail;

	public TableModelPayment(List<InvoiceTransaction> listInvoiceTransaction) {
		dataVector = convertToVector(listInvoiceTransaction.toArray());
	}

	public void setData(InvoiceDetail invoiceDetail) {
		this.invoiceDetail = invoiceDetail;
		dataVector = convertToVector(invoiceDetail.getTransactions().toArray());
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
		if (column == 1) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		if (column == 1) {
			try {
				InvoiceTransaction rowVector = (InvoiceTransaction) dataVector.elementAt(row);
				String dateTime = "";
				List<String> mang = new ArrayList<String>();
				StringTokenizer st = new StringTokenizer(aValue.toString(), "/");
				while (st.hasMoreTokens()) {
					mang.add(st.nextToken());
				}
				if (mang.get(0).length() == 1) {
					dateTime = dateTime + "0" + mang.get(0);
				} else {
					dateTime = dateTime + mang.get(0);
				}
				if (mang.get(1).length() == 1) {
					dateTime = dateTime + "0" + mang.get(1);
				} else {
					dateTime = dateTime + mang.get(1);
				}
				if (mang.get(2).length() == 2) {
					dateTime = dateTime + "20" + mang.get(2);
				} else {
					dateTime = dateTime + mang.get(2);
				}
				if (invoiceDetail != null) {
					List<String> mang1 = new ArrayList<String>();
					StringTokenizer st1 = new StringTokenizer(new SimpleDateFormat("dd-MM-yyyy").format(invoiceDetail
					    .getStartDate()), "-");
					while (st1.hasMoreTokens()) {
						mang1.add(st1.nextToken());
					}
					String dateTime1 = "";
					if (mang1.get(0).length() == 1) {
						dateTime1 = dateTime1 + "0" + mang1.get(0);
					} else {
						dateTime1 = dateTime1 + mang1.get(0);
					}
					if (mang1.get(1).length() == 1) {
						dateTime1 = dateTime1 + "0" + mang1.get(1);
					} else {
						dateTime1 = dateTime1 + mang1.get(1);
					}
					if (mang1.get(2).length() == 2) {
						dateTime1 = dateTime1 + "20" + mang1.get(2);
					} else {
						dateTime1 = dateTime1 + mang1.get(2);
					}

					if (new SimpleDateFormat("ddMMyyyy").parse(dateTime)
					    .before(new SimpleDateFormat("ddMMyyyy").parse(dateTime1))) {
						ChooseDelProduct panel = new ChooseDelProduct("Ngày biên lai thanh toán không thể trước ngày tạo hóa đơn");
						DialogResto dialog = new DialogResto(panel, false, 0, 80);
						dialog.setTitle("Thông báo");
						dialog.setLocationRelativeTo(null);
						dialog.setModal(true);
						dialog.setVisible(true);
						return;
					}
				}

				dateTime = dateTime + "0600";
				rowVector.setTransactionDate(new SimpleDateFormat("ddMMyyyyHHmm").parse(dateTime));
				dataVector.set(row, rowVector);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public void fireTableRowsUpdated(int firstRow, int lastRow) {
		super.fireTableRowsUpdated(firstRow, lastRow);
		try {
			String str = HRModelManager.getInstance()
			    .getBydLoginId(((InvoiceTransaction) dataVector.get(firstRow)).getCreatedBy()).toString();
			list.put(((InvoiceTransaction) dataVector.get(firstRow)).getCreatedBy(), str);
		} catch (Exception e) {
			list.put(((InvoiceTransaction) dataVector.get(firstRow)).getCreatedBy(), "");
		}
	}

	@Override
	public Object getValueAt(int row, int column) {
		switch (column) {
		case 0:
			try {
				String str = "";
				TransactionType type = ((InvoiceTransaction) dataVector.get(row)).getTransactionType();
				if (type == TransactionType.CustomerCredit) {
					str = "Tiếp Khách";
				} else {
					if (type == TransactionType.CreditCard) {
						str = "Thẻ";
					} else {
						str = "Tiền mặt";
					}
				}
				return str;
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 1:
			try {
				return df.format(((InvoiceTransaction) dataVector.get(row)).getTransactionDate());
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 2:
			try {
				return df1.format(((InvoiceTransaction) dataVector.get(row)).getTransactionDate());
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 3:
			try {
				try {
					if (list.get(((InvoiceTransaction) dataVector.get(row)).getCreatedBy()) == null) {
						String str = HRModelManager.getInstance()
						    .getBydLoginId(((InvoiceTransaction) dataVector.get(row)).getCreatedBy()).toString();
						list.put(((InvoiceTransaction) dataVector.get(row)).getCreatedBy(), str);
					}
				} catch (Exception e) {
					list.put(((InvoiceTransaction) dataVector.get(row)).getCreatedBy(), "");
				}
				return list.get(((InvoiceTransaction) dataVector.get(row)).getCreatedBy());
			} catch (Exception e) {
				return "";
			}

		case 4:
			try {
				return MyDouble.valueOf(((InvoiceTransaction) dataVector.get(row)).getTotal()).toString();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 5:
			try {
				return ((InvoiceTransaction) dataVector.get(row)).getCurrencyUnit();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		default:
			return "";
		}
	}

}
