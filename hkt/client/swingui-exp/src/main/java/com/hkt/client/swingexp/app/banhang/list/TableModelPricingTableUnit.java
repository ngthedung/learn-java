package com.hkt.client.swingexp.app.banhang.list;

import java.util.HashMap;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.ITableModel;
import com.hkt.client.swingexp.app.core.MyDate;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductPriceType;

public class TableModelPricingTableUnit extends DefaultTableModel implements ITableModel{
  private List<ProductPriceType> ProductPriceTypes;
  private HashMap<String, Integer> numberSize = new HashMap<String, Integer>();
  private HashMap<String, Double> list = new HashMap<String, Double>();

  public TableModelPricingTableUnit(List<Product> products, List<ProductPriceType> pricingTables) {
    this.ProductPriceTypes = pricingTables;
    dataVector = convertToVector(products.toArray());
  }

  public void setData(List<Product> products, List<ProductPriceType> pricingTables) {
    this.ProductPriceTypes = pricingTables;
    dataVector = convertToVector(products.toArray());
    fireTableDataChanged();
  }

  @Override
  public int getColumnCount() {
    return ProductPriceTypes.size() + 3;
  }
  
  @Override
  public boolean isCellEditable(int row, int column) {
    return false;
  }

  @Override
  public String getColumnName(int column) {
    if (column == 0) {
      return "Sản phẩm";
    } else {
    	if (column == 1) {
        return "Mã sản phẩm";
      }else{
      	if (column == 2) {
          return "Mã Barcode";
        }else{
        	 return ProductPriceTypes.get(column - 3).getName();
        }
      }
     
    }
  }

  
  
//  @Override
//public void fireTableRowsUpdated(int firstRow, int lastRow) {
//	super.fireTableRowsUpdated(firstRow, lastRow);
//	try {
//		MyDouble double1 = MyDouble(ProductModelManager.getInstance().getByProductAndProductPriceType(((Product) dataVector.get(rowIndex)).getCode(),
//	            ProductPriceTypes.get(columnIndex - 1).getType(), "VND").getPrice());
//	} catch (Exception e) {
//		// TODO: handle exception
//	}
//}

@Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    if (columnIndex == 0) {
      return ((Product) dataVector.get(rowIndex));
    } else {
    	if (columnIndex == 1) {
        return ((Product) dataVector.get(rowIndex)).getCode();
      } else {
      	if (columnIndex == 2) {
          return ((Product) dataVector.get(rowIndex)).getDescription();
        } else {
        	 try {
           	Double str = ProductModelManager.getInstance().getByProductAndProductPriceType(((Product) dataVector.get(rowIndex)).getCode(),
               ProductPriceTypes.get(columnIndex - 3).getType(), "VND").getPrice();
             return new MyDouble(str);
           } catch (Exception ex) {
             return new MyDouble("0");
           }
        }
      }
     
    }
  }
  
  @Override
  public Class<?> getColumnClass(int columnIndex) {
    switch (columnIndex) {
    case 0:
	      return String.class;
    case 1:
      return String.class;
    case 2:
      return String.class;
    default:
      return MyDouble.class;
    }
  }

@Override
public HashMap<String, Integer> getColumnSize() {
	return numberSize;
}

@Override
public String getNameTableModel() {
	return "Quản lý bảng giá";
}
}
