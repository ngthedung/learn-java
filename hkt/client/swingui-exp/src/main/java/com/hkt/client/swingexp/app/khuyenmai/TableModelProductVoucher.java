package com.hkt.client.swingexp.app.khuyenmai;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.module.promotion.entity.MenuItemRef;


/**
 * Model bảng danh sách sản phẩm
 * @author BinLe
 */
public class TableModelProductVoucher extends DefaultTableModel {

    public static String nameProduct = "Tên sản phẩm";
    private String[] header = new String[]{nameProduct,  "Số lg tối đa"};
    private MenuItemRef menuItemRef;
    
    public TableModelProductVoucher(MenuItemRef menuItemRef) {
      this.menuItemRef = menuItemRef;
      dataVector = convertToVector(menuItemRef.getProducts().toArray());
    }
    
    public void setData(MenuItemRef menuItemRef){
      this.menuItemRef = menuItemRef;
      dataVector = convertToVector(menuItemRef.getProducts().toArray());
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
            return MyDouble.valueOf(menuItemRef.getQuantityMax());
          } catch (Exception e) {
            Vector rowVector = (Vector) dataVector.elementAt(row);
            return rowVector.elementAt(column);
          }

        default:
          return "";
      }
    }
  }
