package com.hkt.client.swingexp.app.khuyenmai;

import java.util.List;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import com.hkt.module.product.entity.Product;

@SuppressWarnings("serial")
public class TableModelProductMenuItemRef extends DefaultTableModel {
  private String[]      header = { "Tên sản phẩm", "Số lượng" };
  private String menuItemRef;

  public TableModelProductMenuItemRef(List<Product> listProduct) {
    dataVector = convertToVector(listProduct.toArray());
  }
  
  public void setData(String menuItemRef,List<Product> listProduct){
    this.menuItemRef = menuItemRef;
    dataVector = convertToVector(listProduct.toArray());
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
          return menuItemRef;
        } catch (Exception e) {
          Vector rowVector = (Vector) dataVector.elementAt(row);
          return rowVector.elementAt(column);
        }

      default:
        return "";
    }
  }
}
