package com.hkt.client.swingexp.app.khohang.list;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.module.product.entity.Product;
import com.hkt.module.restaurant.entity.RecipeIngredient;

@SuppressWarnings("serial")
public class TableModelRecipeIngredient extends DefaultTableModel {
  @SuppressWarnings("unused")
private DateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");
  @SuppressWarnings("unused")
private RecipeIngredient      recipeIngredients;
  private String[]   header = new String[] { "", "STT", "Tên SP", "Số lượng", "ĐVSP" };

  public TableModelRecipeIngredient(List<RecipeIngredient> eventorys) {
    dataVector = convertToVector(eventorys.toArray());
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
    return column==3;
  }

  @SuppressWarnings("unchecked")
@Override
public void setValueAt(Object aValue, int row, int column) {
	
	  if (column == 3) {
		  RecipeIngredient rowVector = (RecipeIngredient) dataVector.elementAt(row);
			rowVector.setQuantity(MyDouble.parseDouble(aValue.toString()));
			dataVector.set(row, rowVector);
			this.recipeIngredients = rowVector;
			fireTableRowsUpdated(row, row);
		}else {
			super.setValueAt(aValue, row, column);
		}
	
}

@Override
  public Object getValueAt(int row, int column) {
    switch (column) {
      case 0:
        return (RecipeIngredient) dataVector.get(row);
      case 1:
        try {
          return row + 1;
        } catch (Exception e) {
          e.printStackTrace();
        }
      case 2:
        try {
          return ProductModelManager.getInstance().getProductByCode(
      	      ((RecipeIngredient) dataVector.get(row)).getProductCode());
        } catch (Exception e) {
          e.printStackTrace();
        }
      case 3:
        try {
          return MyDouble.valueOf(((RecipeIngredient) dataVector.get(row)).getQuantity());
        } catch (Exception e) {
          e.printStackTrace();
        }

      case 4:
        try {
          return LocaleModelManager.getInstance().getProductUnitByCode(
          		ProductModelManager.getInstance().getProductByCode(
          	      ((RecipeIngredient) dataVector.get(row)).getProductCode()).getUnit());
        } catch (Exception e) {
          e.printStackTrace();
        }

      default:
        return "";
    }
  }
}
