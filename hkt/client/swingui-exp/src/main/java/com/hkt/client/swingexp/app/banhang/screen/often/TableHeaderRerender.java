package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class TableHeaderRerender extends JLabel implements TableCellRenderer {

  public TableHeaderRerender() {
  }

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
      int row, int column) {
    this.setOpaque(true);
    this.setFont(new Font(this.getFont().getFontName(), Font.BOLD, this.getFont().getSize()));
    this.setHorizontalAlignment(JLabel.CENTER);
    this.setText(value.toString());
    return this;
  }
}

