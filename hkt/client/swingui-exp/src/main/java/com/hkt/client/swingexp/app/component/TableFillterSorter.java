package com.hkt.client.swingexp.app.component;

import java.text.Normalizer;
import java.util.Comparator;
import java.util.Hashtable;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import com.hkt.client.swingexp.app.core.VietnamesesComparator;

public class TableFillterSorter {
  private final JTable table;
  private Hashtable<String, Integer> hashtableHeader;
  private MyTableRowSorter<TableModel> tableRowSorter;

  public TableFillterSorter(JTable table) {
    this.table = table;
  }

  public void createTableSorter() {
    if (tableRowSorter == null) {
      initSorter();
    }
    table.setRowSorter(tableRowSorter);
  }

  private void initSorter() {
    tableRowSorter = new MyTableRowSorter<TableModel>(table.getModel()) {

      @Override
      public void setComparator(int column, Comparator<?> comparator) {
        if (table.getModel().getColumnClass(column).equals(String.class)) {
          VietnamesesComparator comparator1 = new VietnamesesComparator();
          super.setComparator(column, comparator1);
        } else {
          super.setComparator(column, comparator);
        }
      }
    };
  }

  public void createTableFillter() {
    initTypeSearch();
  }

  public Hashtable<String, Integer> getHashtable() {
    return hashtableHeader;
  }

  public void fillter(String textFillter, String typeFillter) {
    int columnIndex = hashtableHeader.get(typeFillter);
    if (tableRowSorter == null) {
      initSorter();
    }
    String str = Normalizer.normalize(textFillter, Normalizer.Form.NFD)
        .replaceAll("\\p{Block=CombiningDiacriticalMarks}+", "").toLowerCase().replaceAll("\u0111", "d");
    tableRowSorter.setRowFilter(MyRowFilter.regexFilter("(?i)" + str, columnIndex));
  }

  private void initTypeSearch() {
    TableModel model = table.getModel();
    hashtableHeader = new Hashtable<String, Integer>();
    for (int i = 0; i < model.getColumnCount(); i++) {
      if (!model.getColumnName(i).isEmpty()) {
        hashtableHeader.put(model.getColumnName(i), i);
      }
    }
  }
}
