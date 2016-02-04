package com.hkt.client.swingexp.app.hethong.list;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class TableModelDeleteRight<E> extends DefaultTableModel {

  private String[] header = { "STT", "Tên" };

  public TableModelDeleteRight(List<E> customers) {
    dataVector = convertToVector(customers.toArray());
  }

  @Override
  public int getColumnCount() {
    return header.length;
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
      return row + 1;
    case 1:
      try {
        return dataVector.get(row);
      } catch (Exception e) {
        Vector rowVector = (Vector) dataVector.elementAt(row);
        return rowVector.elementAt(column);
      }
    default:
      return "";
    }
  }

  public void addRow(E elementAt) {
    dataVector.addElement(elementAt);
    fireTableDataChanged();
  }
}
