package com.hkt.client.swingexp.app.print;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class TableModeConvert extends AbstractTableModel {

  private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
  private final JTable table;
  private final String[] headerConvert;

  public TableModeConvert(JTable table, String[] headerConvert) {
      this.table = table;
      this.headerConvert = headerConvert;
  }

  @Override
  public int getRowCount() {
      return table.getRowCount();
  }

  @Override
  public int getColumnCount() {
      return headerConvert.length;
  }

  @Override
  public String getColumnName(int column) {
      return headerConvert[column];
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
      for (int i = 0; i < table.getColumnCount(); i++) {
          if (table.getColumnName(i).equals(headerConvert[columnIndex])) {
              Object obj = table.getValueAt(rowIndex, i);
              if (obj == null) {
                  return "";
              } else {
                  if (obj instanceof Date) {
                      return df.format(obj);
                  }
                  return obj;
              }
          }
      }
      return "";
  }
}

