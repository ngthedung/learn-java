package com.hkt.client.swingexp.app.banhang.screen.often;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import antlr.collections.impl.Vector;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.module.promotion.entity.Promotion;
import com.hkt.module.promotion.entity.PromotionDetail;

@SuppressWarnings("serial")

public class TableModelViewPromotion extends DefaultTableModel{
	
	private String[] header = { "STT", "Tên", "Khuyến mãi theo %", "Khuyến mãi theo tiền"};
	
	public TableModelViewPromotion(List<PromotionDetail> listPromotion){
		dataVector = convertToVector(listPromotion.toArray());
		
	}

	@Override
	public int getColumnCount(){
		return header == null ? 0 : header.length;
	}
	
	@Override
	public String getColumnName(int column){
		return header[column];
	}
	
	 @Override
	  public boolean isCellEditable(int row, int column) {
	    return false;
	  }

	  @Override
	  public Object getValueAt(int row, int column) {
	    switch (column) {
	    case 0:
			try {
				return row + 1;
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
	    
	    case 1:
	      try {
	        return ((Promotion) dataVector.get(row));
	      } catch (Exception e) {
	        Vector rowVector = (Vector) dataVector.elementAt(row);
	        return rowVector.elementAt(column);
	      }
	      
	    case 2:
	      try {
	      	  
	        return MyDouble.valueOf(((Promotion) dataVector.get(row)).getDiscountRate()).toString();
	      } catch (Exception e) {
	        Vector rowVector = (Vector) dataVector.elementAt(row);
	        return rowVector.elementAt(column);
	      }

	    case 3:
	      try {
	    	  return MyDouble.valueOf(((Promotion) dataVector.get(row)).getDiscount()).toString();
	      } catch (Exception e) {
	        Vector rowVector = (Vector) dataVector.elementAt(row);
	        return rowVector.elementAt(column);
	      }

	    default: return "";
	    }
	  }
	    }
