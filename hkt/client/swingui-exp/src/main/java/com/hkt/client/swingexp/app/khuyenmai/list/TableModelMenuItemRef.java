package com.hkt.client.swingexp.app.khuyenmai.list;

import java.util.ArrayList;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.module.product.entity.Product;
import com.hkt.module.promotion.entity.MenuItemRef;

@SuppressWarnings("serial")
public class TableModelMenuItemRef extends DefaultTableModel {
  private String        refName     = "";
  private double        maxQuantity = 1;
  private String[]      header      = { "Tên sản phẩm tùy chọn", "Nhóm", "Số lượng" };

  public TableModelMenuItemRef(MenuItemRef menuItemRef) {
    if (menuItemRef != null) {
      this.refName = menuItemRef.getName();
      this.maxQuantity = menuItemRef.getQuantityMax();
      dataVector = convertToVector(menuItemRef.getProducts().toArray());
    }else {
      dataVector = convertToVector(new ArrayList<Product>().toArray());
    }
  }
  
  public void setData(MenuItemRef menuItemRef){
    if (menuItemRef != null) {
      this.refName = menuItemRef.getName();
      this.maxQuantity = menuItemRef.getQuantityMax();
      dataVector = convertToVector(menuItemRef.getProducts().toArray());
    }else {
      dataVector = convertToVector(new ArrayList<Product>().toArray());
    }
    
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
    return false;
  }

  @SuppressWarnings("rawtypes")
@Override
  public Object getValueAt(int row, int column) {
    switch (column) {
      case 0:
        try {
          return dataVector.get(row);
        } catch (Exception e) {
          Vector rowVector = (Vector) dataVector.elementAt(row);
          return rowVector.elementAt(column);
        }
      case 1:
        try {
          return refName;
        } catch (Exception e) {
          Vector rowVector = (Vector) dataVector.elementAt(row);
          return rowVector.elementAt(column);
        }

      case 2:
        try {
          return maxQuantity;
        } catch (Exception e) {
          Vector rowVector = (Vector) dataVector.elementAt(row);
          return rowVector.elementAt(column);
        }

      default:
        return "";
    }
  }
}
