package com.hkt.client.swingexp.app.core;

import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

import com.hkt.client.swingexp.app.core.XTableColumnModel;

public class VisibleColumnTable {
  private final XTableColumnModel xTableColumnModel;    

  public VisibleColumnTable(JTable table) {
      xTableColumnModel = new XTableColumnModel();
      table.setColumnModel(xTableColumnModel);
      table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
      table.createDefaultColumnsFromModel();
  }
  
  public void hideColumn(int indexColumn) {
  	TableColumn columnX = xTableColumnModel.getColumnByModelIndex(indexColumn);
  	xTableColumnModel.setColumnVisible(columnX, false);
  }
  
  public void showColumn(int indexColumn) {
  	TableColumn columnX = xTableColumnModel.getColumnByModelIndex(indexColumn);
  	xTableColumnModel.setColumnVisible(columnX, true);
  }
}
