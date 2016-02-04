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
import com.hkt.module.promotion.entity.Promotion;
import com.hkt.module.promotion.entity.PromotionUsed;

@SuppressWarnings("serial")
public class TableModelPromotionUsed extends DefaultTableModel implements ITableModel {

	private String[] header = { "STT", "Tên khuyến mại", "Tên khách hàng", "Hóa đơn", "Ngày sử dụng", "Tổng tiền", "Giảm giá", "Miêu tả", "Xóa" };
	private int size;
	private HashMap<String, Integer> numberSize = new HashMap<String, Integer>();
	
	public TableModelPromotionUsed(List<PromotionUsed> listPromotion) {
		dataVector = convertToVector(listPromotion.toArray());
		
		numberSize.put(header[0], 50);
		numberSize.put(header[4], 120);
		numberSize.put(header[8], 50);

	}

	public void setData(List<PromotionUsed> listPromotion, int size) {
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
		    ((PromotionUsed) dataVector.get(row)).setRecycleBin(Boolean.parseBoolean(aValue.toString()));
		  }
		}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getValueAt(int row, int column) {
		switch (column) {
		case 0:
			try {
				return row + 1+ size;
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 1:
			try {
				try {
					Promotion promotion = PromotionModelManager.getInstance().getPromotionById(((PromotionUsed) dataVector.get(row)).getPromotionId());
					return promotion.getName();
				} catch (Exception e) {
					return "";
				}
				
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 2:
			try {
				try {
					Promotion promotion = PromotionModelManager.getInstance().getPromotionById(((PromotionUsed) dataVector.get(row)).getPromotionId());
					return promotion.getOrganizationLoginId();
				} catch (Exception e) {
					return "";
				}
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 3:
			try {
				return ((PromotionUsed) dataVector.get(row)).getInvoiceId();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 4:
			try {

				Date date = ((PromotionUsed) dataVector.get(row)).getUsedDate();
				if(date != null){
		        	return new MyDate(date);
		        } else {
		        	return new MyDate(null);
		        }
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 5:
			try {
				return new MyDouble(((PromotionUsed) dataVector.get(row)).getExpense());
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 6:
			try {
				return new MyDouble(((PromotionUsed) dataVector.get(row)).getSaving());
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 7:
			try {
				return ((PromotionUsed) dataVector.get(row)).getDescription();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		default:
			return  ((PromotionUsed) dataVector.get(row)).isRecycleBin();
		}
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return Integer.class;
		case 1:
			return PromotionUsed.class;
		case 4:
			return MyDate.class;
		case 5:
			return MyDouble.class;
		case 6:
			return MyDouble.class;
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
		return "Danh sách sử dụng khuyến mại";
	}

}
