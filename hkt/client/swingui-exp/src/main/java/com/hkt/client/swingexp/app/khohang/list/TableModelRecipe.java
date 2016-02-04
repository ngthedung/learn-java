package com.hkt.client.swingexp.app.khohang.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.client.swingexp.app.core.ITableModelExt;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductPrice;
import com.hkt.module.restaurant.entity.Recipe;
import com.hkt.module.restaurant.entity.RecipeIngredient;

@SuppressWarnings("serial")
public class TableModelRecipe extends DefaultTableModel implements ITableModelExt{
	private String[] header = { "STT", "Tên định lượng", "Mã sản phẩm", "Tên sản phẩm", "Ghi chú", "Xóa" };
	@SuppressWarnings("unused")
	private List<ProductPrice> listProductPrice;
	private int size;
	private HashMap<String, Integer> numberSize = new HashMap<String, Integer>();

	public TableModelRecipe(List<Recipe> listRecipe) {
		dataVector = convertToVector(listRecipe.toArray());
		listProductPrice = new ArrayList<ProductPrice>();
		numberSize.put(header[0], 50);
		numberSize.put(header[5], 50);
	}

	public void setData(List<Recipe> listRecipe, int size) {
		this.size = size;
		dataVector = convertToVector(listRecipe.toArray());
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
	    ((Recipe) dataVector.get(row)).setRecycleBin(Boolean.parseBoolean(aValue.toString()));
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
				return ((Recipe) dataVector.get(row));
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 2:
			try {
				return ((Recipe) dataVector.get(row)).getProductCode();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 3:
			try {
				String productName = "";
				String productCode = ((Recipe) dataVector.get(row)).getProductCode();
				Product pro = ProductModelManager.getInstance().getProductByCode(productCode);

				if (pro != null) {
					productName = pro.getName();
				}

				return productName;

			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 4:
			try {
				return ((Recipe) dataVector.get(row)).getDescription();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		default:
			return  ((Recipe) dataVector.get(row)).isRecycleBin();
		}
	}
	
	@Override
	  public Class<?> getColumnClass(int columnIndex) {
	    switch (columnIndex) {
	    case 0:
		      return Integer.class;
	    case 1:
		      return Recipe.class;
	    case 5:
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
		return "Quản lý định lượng";
	}
	

	@Override
	public boolean loadDataColum(String name) {
		try {
			for(int i = 0; i< getRowCount(); i++){
				Product p = ProductModelManager.getInstance().getProductByCode(getValueAt(i, 2).toString());
				p.setRecipe(true);
				ProductModelManager.getInstance().saveProduct(p);
				Recipe r = (Recipe)getValueAt(i, 1);
				for(RecipeIngredient ri : r.getRecipeIngredients()){
					Product p1 = ProductModelManager.getInstance().getProductByCode(ri.getProductCode());
					System.out.println(p1+"   "+p1.getPrice());
					ri.setCreatedBy(new MyDouble(p1.getPrice()).toString());
				}
				RestaurantModelManager.getInstance().saveRecipe(r);
			}
			DialogNotice.getInstace().setVisible(true);
		} catch (Exception e) {
		}

		return false;
	}
}