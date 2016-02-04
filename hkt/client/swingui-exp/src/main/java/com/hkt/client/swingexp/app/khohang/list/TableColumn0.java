package com.hkt.client.swingexp.app.khohang.list;

import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

import com.hkt.client.swingexp.app.core.XTableColumnModel;

public class TableColumn0 {
  private final XTableColumnModel xTableColumnModel;    
  private final TableColumn column0;

  public TableColumn0(JTable table) {
      xTableColumnModel = new XTableColumnModel();
      table.setColumnModel(xTableColumnModel);
      table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
      table.createDefaultColumnsFromModel();
      column0 = xTableColumnModel.getColumnByModelIndex(0);
  }

  public void showColumn0() {
      xTableColumnModel.setColumnVisible(column0, true);
  }

  public void hideColumn0() {
      xTableColumnModel.setColumnVisible(column0, false);
  }
  
  public void hideColumnX(int indexColumn) {
  	TableColumn columnX = xTableColumnModel.getColumnByModelIndex(indexColumn);
  	xTableColumnModel.setColumnVisible(columnX, false);
  }
  
  public void showColumnX(int indexColumn) {
  	TableColumn columnX = xTableColumnModel.getColumnByModelIndex(indexColumn);
  	xTableColumnModel.setColumnVisible(columnX, true);
  }
}
