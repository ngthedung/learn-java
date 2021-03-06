package com.hkt.client.swingexp.app.khuyenmai.list;

import java.util.List;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.module.promotion.entity.MenuItem;
import com.hkt.module.promotion.entity.MenuItem.GroupType;
import com.hkt.module.promotion.entity.MenuItem.MenuItemType;

@SuppressWarnings("serial")
public class TableModelMenuItem extends DefaultTableModel {
  private String[] header = {"Tên sản phẩm", "Nhóm", "Kiểu", "Số lượng" };

  public TableModelMenuItem(List<MenuItem> listProduct) {
    dataVector = convertToVector(listProduct.toArray());
  }

  public void setData(List<MenuItem> listProduct) {
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
    if (column == 3) {
      if (((MenuItem) dataVector.get(row)).getMenuItemType() != MenuItemType.ByMenuItemRef)
        return true;
    }
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
          String groupTypeLabel = null;
          GroupType groupType = ((MenuItem) dataVector.get(row)).getGroupType();
          if (groupType == GroupType.Appetizer) {
            groupTypeLabel = "Món khai vị";
          } else if (groupType == GroupType.MainDishes) {
            groupTypeLabel = "Món chính";
          } else {
            groupTypeLabel = "Món tráng miệng";
          }
          return groupTypeLabel;
        } catch (Exception e) {
          Vector rowVector = (Vector) dataVector.elementAt(row);
          return rowVector.elementAt(column);
        }

      case 2:
        try {
          String itemType = null;
          MenuItemType menuItemType = ((MenuItem) dataVector.get(row)).getMenuItemType();
          if (menuItemType == MenuItemType.ByProduct) {
            itemType = "Sản phẩm";
          } else {
            itemType = "Tùy chọn";
          }
          return itemType;
        } catch (Exception e) {
          Vector rowVector = (Vector) dataVector.elementAt(row);
          return rowVector.elementAt(column);
        }

      case 3:
        try {

          return MyDouble.valueOf(((MenuItem) dataVector.get(row)).getQuantity());
        } catch (Exception e) {
          Vector rowVector = (Vector) dataVector.elementAt(row);
          return rowVector.elementAt(column);
        }

      default:
        return "";
    }
  }

  @Override
  public void setValueAt(Object aValue, int row, int column) {
    if (column == 3) {
      MenuItem rowVector = (MenuItem) dataVector.get(row);
      rowVector.setQuantity(MyDouble.parseDouble(aValue.toString()));
    }
  }
}
