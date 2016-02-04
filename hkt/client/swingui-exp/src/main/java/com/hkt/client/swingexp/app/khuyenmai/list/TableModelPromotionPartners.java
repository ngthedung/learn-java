package com.hkt.client.swingexp.app.khuyenmai.list;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.ITableModel;
import com.hkt.client.swingexp.app.core.MyDate;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.model.PromotionModelManager;
import com.hkt.module.promotion.entity.PromotionGiveUsed;

@SuppressWarnings("serial")
public class TableModelPromotionPartners extends DefaultTableModel implements ITableModel{
	private String[] header = { "STT", "Tên CT khuyến mãi", "Mã hóa đơn", "Ngày sử dụng", "Giá", "Số lượng", "Tên sản phẩm", "Xóa" };
	private int size;
	private HashMap<String, Integer> numberSize = new HashMap<String, Integer>();
	
	public TableModelPromotionPartners(List<PromotionGiveUsed> listPromotionGiveUsed) {
		dataVector = convertToVector(listPromotionGiveUsed.toArray());
		numberSize.put(header[0], 50);
		numberSize.put(header[3], 120);
		numberSize.put(header[5], 80);
		numberSize.put(header[7], 50);
	}

	public void setData(List<PromotionGiveUsed> listPromotion, int size) {
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
		    ((PromotionGiveUsed) dataVector.get(row)).setRecycleBin(Boolean.parseBoolean(aValue.toString()));
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
			  try {
			    return PromotionModelManager.getInstance().getPromotionGiveawaysByProductCode(
			        ((PromotionGiveUsed) dataVector.get(row)).getProductCode()).getName();
        } catch (Exception e) {
          return "";
        }
				
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 2:
			try {
				return ((PromotionGiveUsed) dataVector.get(row)).getInvoiceCode();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 3:
			try {
				Date date = ((PromotionGiveUsed) dataVector.get(row)).getUsedDate();
				if(date != null){
		        	return new MyDate(date);
		        } else {
		        	return new MyDate(null);
		        }
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 4:
			try {
				return ((PromotionGiveUsed) dataVector.get(row)).getPrice();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 5:
			try {
				return ((PromotionGiveUsed) dataVector.get(row)).getQuantity();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 6:
			try {
				return ((PromotionGiveUsed) dataVector.get(row)).getProductName();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		default:
			return  ((PromotionGiveUsed) dataVector.get(row)).isRecycleBin();
		}
	}

	@Override
	  public Class<?> getColumnClass(int columnIndex) {
	    switch (columnIndex) {
	    case 0:
		      return Integer.class;
	    case 1:
		      return PromotionGiveUsed.class;
	    case 3:
	      return MyDate.class;
	    case 5:
	      return MyDouble.class;
	    case 7:
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
		return "DS sử dụng khuyến mại tặng SP";
	}

}
