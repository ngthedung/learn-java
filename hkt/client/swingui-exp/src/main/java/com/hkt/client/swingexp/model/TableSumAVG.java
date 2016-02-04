package com.hkt.client.swingexp.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.MyDouble;

public class TableSumAVG {
  private static TableSumAVG tableSumAVG;
  public static final int TYPE_AVG = 1;
  public static final int TYPE_SUM = 2;

  public static TableSumAVG getInstance() {
      if (tableSumAVG == null) {
          tableSumAVG = new TableSumAVG();
      }
      return tableSumAVG;
  }
  

  public void setSumTable( JTable table, List<Integer> columns, String unitMoney, Hashtable<String, String> hashtable) {
      if (table != null) {
      	 int totalRow = table.getRowSorter().getViewRowCount();
         DefaultTableModel model = (DefaultTableModel) table.getModel();
          List<String> list = new ArrayList<String>();
          List<String> list1 = new ArrayList<String>();
          for (int i = 0; i < table.getColumnCount(); i++) {
              list.add(" ");
              list1.add(" ");
          }
          int h = 0;
          if (model.getColumnName(0).trim().isEmpty()) {
              list.add(0, " ");
              list.set(1, "Tổng");
              h = 1;
          } else {
              list.set(0, "Tổng");
          }
          if (columns != null && !columns.isEmpty()) {
              for (int i = 0; i < columns.size(); i++) {
                  int column = columns.get(i);
                  double number = 0;
                  String value = "";
                  for (int j = 0; j < totalRow; j++) {
  
                      try {
                          String s = replaceSpace(table.getValueAt(j, column).toString());
                          number = number+MyDouble.parseDouble(s);
                      } catch (Exception e) {
                          number = 0;
                      }

                  }
                  if (column == 0) {
                      value = "Tổng " + number;
                  } else {
                      if (!unitMoney.trim().isEmpty() && i == columns.size() - 1) {
                          value = unitMoney;
                      } else {
                          value = new MyDouble(number).toString();
                      }
                  }
                  for (int j = 1; j < list.size(); j++) {
                      if (j == columns.get(i) + h) {
                          list.set(j, value);
                          break;
                      }
                  }
              }
              if (!hashtable.keys().nextElement().isEmpty()) {
                  for (int j = 1; j < list.size(); j++) {
                      if (table.getColumnName(j).equals(
                              hashtable.get(hashtable.keys().nextElement()))) {
                          list.set(j + h, hashtable.keys().nextElement());
                          list1.set(j + h, hashtable.keys().nextElement());
                          break;
                      }
                  }
              }
              model.addRow(list1.toArray());
              model.addRow(list.toArray());
          }
      }
  }

  public void setSumAVGTable( JTable table, List<Integer> columns, String unitMoney, Hashtable<String, String> hashtable) {
  
  	if (table != null) {
          int totalRow = table.getRowCount();
          List<String> list = new ArrayList<String>();
          List<String> list1 = new ArrayList<String>();
          List<String> list2 = new ArrayList<String>();
         
          for (int i = 0; i < table.getColumnCount(); i++) {
              list.add(" ");
              list1.add(" ");
              list2.add(" ");
          }
          int h = 0;
          if (table.getColumnName(0).trim().isEmpty()) {
              list.add(0, " ");
              list.set(1, "Tổng");
              list1.add(0, " ");
              list1.set(1, "Trung bình");
              h = 1;
          } else {
              list.set(0, "Tổng");
              list1.set(0, "Trung bình ");
          }
          if (columns != null && !columns.isEmpty()) {
              for (int i = 0; i < columns.size(); i++) {
                  int column = columns.get(i);
                  double sum = 0;
                  double avg = 0;
                  String strSum = "";
                  String strAvg = "";
                  if (totalRow > 0) {
                      for (int j = 0; j < totalRow; j++) {
                         double mid = 0;
                          try {
                              String s = replaceSpace(table.getValueAt(j, column).toString());
                              mid = MyDouble.parseDouble(s);
                          } catch (Exception e) {
                              mid = 0;
                          }
                          sum = sum+mid;
                      }
                      avg = sum/totalRow;
                      if (column == 0) {
                          strSum = "Tổng " + sum;
                          strAvg = "Trung bình " + avg;
                      } else {
                          if (!unitMoney.trim().isEmpty() && i == columns.size() - 1) {
                              strSum = unitMoney;
                              strAvg = unitMoney;
                          } else {

                              strSum = new MyDouble(sum).toString();
                             
                              strAvg = new MyDouble(avg).toString();
                          }
                      }
                      for (int j = 1; j < list.size(); j++) {
                          if (j == columns.get(i) + h) {
                              list.set(j, strSum);
                              list1.set(j, strAvg);
                              break;
                          }
                      }
                  }
              }
              if (!hashtable.keys().nextElement().isEmpty()) {
                  for (int j = 1; j < list.size(); j++) {
                      if (table.getColumnName(j).equals(
                              hashtable.get(hashtable.keys().nextElement()))) {
                          list.set(j + h, hashtable.keys().nextElement());
                          list1.set(j + h, hashtable.keys().nextElement());
                          list2.set(j + h, hashtable.keys().nextElement());
                          break;
                      }
                  }
              }
            	DefaultTableModel table1 = (DefaultTableModel) table.getModel();  
              table1.addRow(list2.toArray());
              table1.addRow(list.toArray());
              table1.addRow(list1.toArray());
          }
      }
  }

  public void setAVGTable(JTable table, List<Integer> columns, String unitMoney, Hashtable<String, String> hashtable) {
      if (table != null) {
          int totalRow = table.getRowCount();
          List<String> list2 = new ArrayList<String>();
          List<String> list = new ArrayList<String>();
          for (int i = 0; i < table.getColumnCount(); i++) {
              list.add(" ");
              list2.add(" ");
          }
          int h = 0;
          if (table.getColumnName(0).trim().isEmpty()) {
              list.add(0, " ");
              list.set(1, "Trung bình");
              h = 1;
          } else {
              list.set(0, "Trung bình");
          }
          if (columns != null && !columns.isEmpty()) {
              for (int i = 0; i < columns.size(); i++) {
                  int column = columns.get(i);
                  double bigDecimal =0;
                  String value = "Trung bình ";
                  if (totalRow > 0) {
                      for (int j = 0; j < totalRow; j++) {
                          double mid = 0;
                          try {
                              String s = replaceSpace(table.getValueAt(j, column).toString());
                              mid = MyDouble.parseDouble(s);
                          } catch (Exception e) {
                              mid = 0;
                          }
                          bigDecimal = bigDecimal+mid;
                      }
                      bigDecimal = bigDecimal/totalRow;
                      if (column == 0) {
                          value = value + bigDecimal;
                      } else {
                          if (!unitMoney.trim().isEmpty() && i == columns.size() - 1) {
                              value = unitMoney;
                          } else {
  
                              value = new MyDouble(bigDecimal).toString();
                          }
                      }
                      for (int j = 1; j < list.size(); j++) {
                          if (j == columns.get(i) + h) {
                              list.set(j, value);
                              break;
                          }
                      }
                  }
              }
              if (!hashtable.keys().nextElement().isEmpty()) {
                  for (int j = 1; j < list.size(); j++) {
                      if (table.getColumnName(j).equals(
                              hashtable.get(hashtable.keys().nextElement()))) {
                          list.set(j + h, hashtable.keys().nextElement());
                          list2.set(j + h, hashtable.keys().nextElement());
                          break;
                      }
                  }
              }
              DefaultTableModel table1 = (DefaultTableModel) table.getModel();  
              table1.addRow(list2.toArray());
              table1.addRow(list.toArray());
          }
      }
  }

  public void setSumAVGTable(JTable table, Hashtable<Integer, List<Integer>> hashtable) {
      DefaultTableModel model = (DefaultTableModel) table.getModel();
      int totalRow = table.getRowCount();
      model.addRow(new String[table.getColumnCount()]);
      String value = "Tổng và trung bình ";
      table.setValueAt(value, totalRow, 0);
      if (hashtable != null && !hashtable.isEmpty()) {
          //--------------------- SUM --------------------------/
          List<Integer> listSum = hashtable.get(TYPE_SUM);
          if (listSum != null && !listSum.isEmpty()) {
              for (int i = 0; i < listSum.size(); i++) {
                  BigDecimal bigDecimal = new BigDecimal(0);
                  int column = listSum.get(i);
                  for (int j = 0; j < totalRow; j++) {
                      BigDecimal mid = new BigDecimal(0);
                      try {
                          String s = replaceSpace(table.getValueAt(j, column).toString());
                          mid = new BigDecimal(s);
                      } catch (Exception e) {
                          mid = new BigDecimal(0);
                      }
                      bigDecimal = bigDecimal.add(mid);
                  }
                  if (column == 0) {
                      value = value + bigDecimal.toPlainString();
                      table.setValueAt(value, totalRow, column);
                  } else {
                      String s = "";
                      try {
                          s = s + " " + table.getValueAt(totalRow, column) == null ? "" : table.getValueAt(totalRow, column).toString();
                      } catch (Exception e) {
                          s = "";
                      }
                      s = s + " " + bigDecimal.toPlainString();
                      table.setValueAt(s, totalRow, column);
                  }
              }
          }

          //----------------------- AVG -------------------------/
          List<Integer> listAvg = hashtable.get(TYPE_AVG);
          if (listAvg != null && !listAvg.isEmpty()) {
              for (int i = 0; i < listAvg.size(); i++) {
                  BigDecimal bigDecimal = new BigDecimal(0);
                  int column = listAvg.get(i);
                  for (int j = 0; j < totalRow; j++) {
                      BigDecimal mid = new BigDecimal(0);
                      try {
                          String s = replaceSpace(table.getValueAt(j, column).toString());
                          mid = new BigDecimal(s);
                      } catch (Exception e) {
                          mid = new BigDecimal(0);
                      }
                      bigDecimal = bigDecimal.add(mid);
                  }
                  bigDecimal = bigDecimal.divide(new BigDecimal(totalRow), 2, RoundingMode.UP);
                  if (column == 0) {
                      value = value + bigDecimal.toPlainString() + " ";
                      table.setValueAt(value, totalRow, column);
                  } else {
                      String s = "";
                      try {
                          s = s + " " + table.getValueAt(totalRow, column) == null ? "" : table.getValueAt(totalRow, column).toString();
                      } catch (Exception e) {
                          s = "";
                      }
                      s = s + " " + bigDecimal.toPlainString();
                      table.setValueAt(s, totalRow, column);
                  }

              }
          }
      }
  }

  private String replaceSpace(String str) {
      if (str != null && str.length() > 0) {
          return str = str.replaceAll("\\s+", "");
      }
      return str;
  }
}
