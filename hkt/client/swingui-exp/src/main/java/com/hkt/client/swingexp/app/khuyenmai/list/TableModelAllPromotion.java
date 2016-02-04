package com.hkt.client.swingexp.app.khuyenmai.list;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.ITableModel;
import com.hkt.client.swingexp.app.core.MyDate;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.module.promotion.entity.Promotion;


@SuppressWarnings("serial")
public class TableModelAllPromotion extends DefaultTableModel implements ITableModel{

	 private String[] header = { "STT", "Tên KM", "Từ ngày", "Đến ngày", "Trạng thái", "Sử dụng", "Tỷ lệ giảm", "Giảm giá","Đơn vị", "Kiểu KM","Mô tả", "Xóa" };
	 private int size;
	 private HashMap<String, Integer> numberSize = new HashMap<String, Integer>();
	 
	 public TableModelAllPromotion(List<Promotion> listPromotion) {
		    dataVector = convertToVector(listPromotion.toArray());
		    
		    numberSize.put(header[0], 50);
//		    numberSize.put(header[1], 150);
			numberSize.put(header[2], 100);
			numberSize.put(header[3], 100);
			numberSize.put(header[4], 100);
			numberSize.put(header[5], 80);
			numberSize.put(header[6], 100);
			numberSize.put(header[7], 80);
			numberSize.put(header[8], 70);
			numberSize.put(header[9], 100);
			numberSize.put(header[11], 50);
		  }

		  // Bước 7 : Xây dựng hàm setData
		  public void setData(List<Promotion> listPromotion, int size) {
			  this.size = size;
			  
		    dataVector = convertToVector(listPromotion.toArray());
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
			    ((Promotion) dataVector.get(row)).setRecycleBin(Boolean.parseBoolean(aValue.toString()));
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
		        return ((Promotion) dataVector.get(row));
		      } catch (Exception e) {
		        new Promotion();
		      }

		    case 2:
		      try {
		        Date date = ((Promotion) dataVector.get(row)).getFromDate();
		        if(date != null){
		        	return new MyDate(date);
		        } else {
		        	return new MyDate(null);
		        }
		        
		      } catch (Exception e) {
		    	  return new MyDate(null);
		      }

		    case 3:
		      
		        try {
		          Date date = ((Promotion) dataVector.get(row)).getToDate();
		          if(date != null){
			        	return new MyDate(date);
			        } else {
			        	return new MyDate(null);
			        }
		        } catch (Exception e) {
		        	return new MyDate(null);
		        }


		    case 4:
		      try {
		        return ((Promotion) dataVector.get(row)).getStatus();
		      } catch (Exception e) {
		        Vector rowVector = (Vector) dataVector.elementAt(row);
		        return rowVector.elementAt(column);
		      }

		    case 5:
		      try {
		        return ((Promotion) dataVector.get(row)).getMaxUsePerCustomer();
		      } catch (Exception e) {
		        Vector rowVector = (Vector) dataVector.elementAt(row);
		        return rowVector.elementAt(column);
		      }

		    case 6:
		      try {
		        return new MyDouble(((Promotion) dataVector.get(row)).getDiscountRate());
		      } catch (Exception e) {
		        Vector rowVector = (Vector) dataVector.elementAt(row);
		        return rowVector.elementAt(column);
		      }

		    case 7:
		      try {
		        return new MyDouble(((Promotion) dataVector.get(row)).getDiscount());
		      } catch (Exception e) {
		        Vector rowVector = (Vector) dataVector.elementAt(row);
		        return rowVector.elementAt(column);
		      }

		    case 8:
		      try {
		        return ((Promotion) dataVector.get(row)).getCurrencyUnit();
		      } catch (Exception e) {
		        Vector rowVector = (Vector) dataVector.elementAt(row);
		        return rowVector.elementAt(column);
		      }

		    case 9:
			      try {
			        return ((Promotion) dataVector.get(row)).getTypePromotion();
			      } catch (Exception e) {
			        Vector rowVector = (Vector) dataVector.elementAt(row);
			        return rowVector.elementAt(column);
			      }
		      
		    case 10:
		      try {
		        return ((Promotion) dataVector.get(row)).getDescription();
		      } catch (Exception e) {
		        Vector rowVector = (Vector) dataVector.elementAt(row);
		        return rowVector.elementAt(column);
		      }

		    default:
		    	return  ((Promotion) dataVector.get(row)).isRecycleBin();
		    }
		  }
		  
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return Integer.class;
		case 2:
			return MyDate.class;
		case 3:
			return MyDate.class;
		case 5:
			return Integer.class;
		case 6:
			return MyDouble.class;
		case 7:
			return MyDouble.class;
		case 11:
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
			return "Danh sách khuyến mại";
		}

}
