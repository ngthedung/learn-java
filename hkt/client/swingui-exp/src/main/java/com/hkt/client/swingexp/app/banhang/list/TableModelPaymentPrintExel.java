package com.hkt.client.swingexp.app.banhang.list;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.ITableModel;
import com.hkt.client.swingexp.app.core.MyDate;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.SupplierModelManager;
import com.hkt.module.accounting.entity.Invoice;
import com.hkt.module.accounting.entity.Invoice.ActivityType;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.supplier.entity.Supplier;

@SuppressWarnings("serial")
public class TableModelPaymentPrintExel extends DefaultTableModel{
  private DateFormat dfTime = new SimpleDateFormat("HH:mm");
  private int size=0;
  private HashMap<String, String> list = new HashMap<String, String>();
  private String[] header = { "Stt", "Tên nghiệp vụ", "Mã",  "Người TH","Ngày TH", "Giờ TH", "Tiền hàng",
      "Chiết khấu", "Tiền thuế","Tổng tiền"};

  public TableModelPaymentPrintExel(List<Invoice> eventorys) {
    dataVector = convertToVector(eventorys.toArray());
  }

  public void setData(List<Invoice> invoices, int size) {
    this.size = size;
    dataVector = convertToVector(invoices.toArray());
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
public void fireTableRowsUpdated(int firstRow, int lastRow) {
	super.fireTableRowsUpdated(firstRow, lastRow);
	try {
		String str =  HRModelManager.getInstance().getEmployeeByCode(((Invoice) dataVector.get(firstRow)).getEmployeeCode()).toString();
		list.put(((Invoice) dataVector.get(firstRow)).getInvoiceCode(), str);
	} catch (Exception e) {
		list.put(((Invoice) dataVector.get(firstRow)).getInvoiceCode(), "");
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
    case 2:
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

    case 1:
      try {
    	  try {
				if (list.get(((Invoice) dataVector.get(row)).getInvoiceCode()) == null) {
					String str = HRModelManager.getInstance().getEmployeeByCode(((Invoice) dataVector.get(row)).getEmployeeCode()).toString();
					list.put(((Invoice) dataVector.get(row)).getInvoiceCode(), str);
				}
			} catch (Exception e) {
				list.put(((Invoice) dataVector.get(row)).getInvoiceCode(), "");
			}
        return ((Invoice) dataVector.get(row));
      } catch (Exception e) {
        Vector rowVector = (Vector) dataVector.elementAt(row);
        return rowVector.elementAt(column);
      }


    case 3:
      try {
        try {
          return list.get(((Invoice) dataVector.get(row)).getInvoiceCode());
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
          return "";
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
          return "";
        }

      } catch (Exception e) {
        Vector rowVector = (Vector) dataVector.elementAt(row);
        return rowVector.elementAt(column);
      }
      

    case 6:
      try {
        return new MyDouble(((Invoice) dataVector.get(row)).getTotal());
      } catch (Exception e) {
        Vector rowVector = (Vector) dataVector.elementAt(row);
        return rowVector.elementAt(column);
      }
    case 7:
      try {
        return new MyDouble(((Invoice) dataVector.get(row)).getDiscount());
      } catch (Exception e) {
        Vector rowVector = (Vector) dataVector.elementAt(row);
        return rowVector.elementAt(column);
      }
    case 8:
      try {
        return new MyDouble(((Invoice) dataVector.get(row)).getTotalTax());
      } catch (Exception e) {
        Vector rowVector = (Vector) dataVector.elementAt(row);
        return rowVector.elementAt(column);
      }

    case 9:
      try {
        return new MyDouble(((Invoice) dataVector.get(row)).getFinalCharge());
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


}
