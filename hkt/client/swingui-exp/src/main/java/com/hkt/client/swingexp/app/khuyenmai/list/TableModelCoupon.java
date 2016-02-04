package com.hkt.client.swingexp.app.khuyenmai.list;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.ITableModel;
import com.hkt.client.swingexp.app.core.MyDate;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.module.promotion.entity.Coupon;

@SuppressWarnings("serial")
public class TableModelCoupon extends DefaultTableModel implements ITableModel{
  private String[] header = { "STT", "Mã phiếu", "Tên phiếu", "Bắt đầu", "Kết thúc", "Giá trị", "Đơn vị",
      "Số lần", "Đã dùng", "Còn lại", "Xóa" };
  private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
  private int size;
  private HashMap<String, Integer> numberSize = new HashMap<String, Integer>();

  public TableModelCoupon(List<Coupon> listCoupon) {
    dataVector = convertToVector(listCoupon.toArray());
    numberSize.put(header[0], 50);
	numberSize.put(header[3], 100);
	numberSize.put(header[4], 100);
	numberSize.put(header[6], 80);
	numberSize.put(header[7], 80);
	numberSize.put(header[8], 80);
	numberSize.put(header[9], 80);
	numberSize.put(header[10], 50);
  }

  public void setData(List<Coupon> listCoupon, int size) {
	  this.size = size;
    dataVector = convertToVector(listCoupon.toArray());
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
	    ((Coupon) dataVector.get(row)).setRecycleBin(Boolean.parseBoolean(aValue.toString()));
	  }
	}

  @SuppressWarnings("rawtypes")
@Override
  public Object getValueAt(int row, int column) {
    switch (column) {
    case 0:
		try {
			return row + 1+size;
		} catch (Exception e) {
			Vector rowVector = (Vector) dataVector.elementAt(row);
			return rowVector.elementAt(column);
		}
    case 1:
      try {
        return ((Coupon) dataVector.get(row)).getCode();
      } catch (Exception e) {
        Vector rowVector = (Vector) dataVector.elementAt(row);
        return rowVector.elementAt(column);
      }
    case 2:
      try {
    	  try {
    		  return ((Coupon) dataVector.get(row));
		} catch (Exception e) {
			new Coupon();
		}
        
      } catch (Exception e) {
        Vector rowVector = (Vector) dataVector.elementAt(row);
        return rowVector.elementAt(column);
      }

    case 3:
      try {
    	  if (((Coupon) dataVector.get(row)).getFromDate()!= null){
    		  return new MyDate(((Coupon) dataVector.get(row)).getFromDate());
    	  } else {
    		  return new MyDate(null);
    	  }
       
      } catch (Exception e) {
    	  try {
    		  Vector rowVector = (Vector) dataVector.elementAt(row);
    		  return rowVector.elementAt(column);
		} catch (Exception e2) {
		}
       
        
      }

    case 4:
      try {
        try {
        	 if (((Coupon) dataVector.get(row)).getToDate()!= null){
       		  return new MyDate(((Coupon) dataVector.get(row)).getToDate());
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
        if (((Coupon) dataVector.get(row)).getDiscountRate() == 0) {
          return new MyDouble(((Coupon) dataVector.get(row)).getDiscount());
        } else {
          return new MyDouble(((Coupon) dataVector.get(row)).getDiscountRate());
        }

      } catch (Exception e) {
        Vector rowVector = (Vector) dataVector.elementAt(row);
        return rowVector.elementAt(column);
      }
    case 6:
      try {
        if (((Coupon) dataVector.get(row)).getDiscountRate() == 0) {
          return ((Coupon) dataVector.get(row)).getCurrencyUnit();
        } else {
          return "%";
        }

      } catch (Exception e) {
        Vector rowVector = (Vector) dataVector.elementAt(row);
        return rowVector.elementAt(column);
      }
    case 7:
      try {
        return ((Coupon) dataVector.get(row)).getMaxUsePerCustomer();
      } catch (Exception e) {
        Vector rowVector = (Vector) dataVector.elementAt(row);
        return rowVector.elementAt(column);
      }
    case 8:
      try {
        return 0;
      } catch (Exception e) {
        Vector rowVector = (Vector) dataVector.elementAt(row);
        return rowVector.elementAt(column);
      }
    case 9:
      try {
        return ((Coupon) dataVector.get(row)).getMaxUsePerCustomer();
      } catch (Exception e) {
        Vector rowVector = (Vector) dataVector.elementAt(row);
        return rowVector.elementAt(column);
      }
    default:
    	try {
    		return  ((Coupon) dataVector.get(row)).isRecycleBin();
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
        return Coupon.class;
    case 3:
        return MyDate.class;
    case 4:
      return MyDate.class;
    case 5:
      return MyDouble.class;
    case 7:
        return Integer.class;
    case 8:
        return Integer.class;
    case 9:
        return Integer.class;
    case 10:
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
	return "DS Phiếu giảm giá";
}
}
