package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.module.accounting.entity.InvoiceItem;

/**
 * 
 * @author longnt
 */
public class TableRerenderSale implements TableCellRenderer {

  private final Color COLOR_PAYED = new Color(0, 72, 0);// da thanh toan
  private final Color COLOR_SELECTED_ROW = new Color(220, 240, 253);// select
                                                                    // row in
                                                                    // table
  private final Color COLOR_DISTROYED = new Color(155, 43, 42);// huy mon an
  private final Color COLOR_PROCESSING = new Color(102, 0, 102);// dang xu ly
                                                                // mon an
  private final Color COLOR_PROCESSED = new Color(252, 124, 4);// da xu ly mon
                                                               // an
  private boolean flag;

  public TableRerenderSale(boolean flag) {
    this.flag = flag;
  }

  private Component getComponentPanel(JLabel lb, Color color, boolean has) {
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    lb.setHorizontalAlignment(JLabel.RIGHT);
    panel.add(lb, BorderLayout.CENTER);
    JLabel label1 = new JLabel("  *");
    if (has) {
      label1.setText("    ");
    }
    panel.setBackground(Color.white);
    panel.add(label1, BorderLayout.EAST);
    if (color != null) {
      label1.setBackground(color);
    }
    return panel;
  }

  private Component getCompoent(JLabel label, Color color, int column, boolean has) {
    if (color != null) {
      if (color.equals(COLOR_SELECTED_ROW)) {
        label.setBackground(color);
      } else {
        label.setForeground(color);
      }
    }
    if (column == 5) {
      if (has) {
        return getComponentPanel(label, color, has);
      } else {
        return label;
      }
    } else {
      return label;
    }
  }

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
      int row, int column) {
    JLabel label = new JLabel();
    label.setForeground(Color.black);
    JCheckBox checkBox = new JCheckBox();
    checkBox.setHorizontalAlignment(JLabel.CENTER);
    if (column == 6) {
      try {
        InvoiceItem operation = (InvoiceItem) table.getValueAt(row, 1);
        if (operation.getStatus() == AccountingModelManager.isPromotion) {
          checkBox.setSelected(true);
        } else {
          checkBox.setSelected(false);
        }
      } catch (Exception e) {
      }
      if (flag) {
        try {
          InvoiceItem operation = (InvoiceItem) table.getValueAt(row, 1);
          if (operation.getStatus().equals(AccountingModelManager.getInstance().isPayment)) {
            checkBox.setForeground(COLOR_PAYED);
          }
          if (operation.getStatus().equals(AccountingModelManager.getInstance().isCance)) {
            checkBox.setForeground(COLOR_DISTROYED);
          }
          if (operation.getStatus().equals(AccountingModelManager.getInstance().isPromotion)) {
            checkBox.setForeground(COLOR_PROCESSED);
          }
          if (operation.getStatus().equals(AccountingModelManager.getInstance().isKitchen)) {
            checkBox.setForeground(COLOR_PROCESSING);
          }
          if (isSelected) {
            checkBox.setBackground(COLOR_SELECTED_ROW);
          }
        } catch (Exception e) {
        }
      }
      return checkBox;
    } else {
      label.setOpaque(true);
      label.setFont(new Font(label.getFont().getFontName(), Font.BOLD, 12));
      try {
        label.setText(value.toString());
      } catch (Exception e) {
      }

      label.setBackground(Color.white);
      if (column == 3) {
        label.setHorizontalAlignment(JLabel.RIGHT);
      }
      if (column == 1) {
        label.setHorizontalAlignment(JLabel.LEFT);
        try {
      		InvoiceItem operation = (InvoiceItem) table.getValueAt(row, 1);
      		if(operation.getStatus().equals(AccountingModelManager.isForRent)){
      			label.setText("(Thuê) "+value.toString());
      		}else {
      			try {
      				if(((InvoiceItem) table.getValueAt(row+1, 1)).getStatus().equals(AccountingModelManager.isForRent)){
      					label.setText("(Cọc) "+value.toString());
      				}else {
      					label.setText(value.toString());
							}
            } catch (Exception e) {
            	label.setText(value.toString());
            }
      			
      			
					}
      		
        } catch (Exception e) {
        }
    		
      }
      if (column == 2) {
        label.setHorizontalAlignment(JLabel.CENTER);
      }
      if (column == 0) {
      	
        label.setHorizontalAlignment(JLabel.CENTER);
      }
      if (column == 4) {
        label.setHorizontalAlignment(JLabel.RIGHT);
      }
      if (column == 5) {
        label.setHorizontalAlignment(JLabel.RIGHT);
      }
    }
    if (flag) {
      try {
        InvoiceItem operation = (InvoiceItem) table.getValueAt(row, 1);
        if (operation.getStatus().equals(AccountingModelManager.getInstance().isPayment)) {
          label.setForeground(COLOR_PAYED);
        }
        if (operation.getStatus().equals(AccountingModelManager.getInstance().isCance)) {
          label.setForeground(COLOR_DISTROYED);
        }
        if (operation.getStatus().equals(AccountingModelManager.getInstance().isPromotion)) {
          label.setForeground(COLOR_PROCESSED);
        }
        if (operation.getStatus().equals(AccountingModelManager.getInstance().isKitchen)) {
          label.setForeground(COLOR_PROCESSING);
        }
        if (isSelected) {
          label.setBackground(COLOR_SELECTED_ROW);
        }
      } catch (Exception e) {
      }
    }
    return label;
  }
}
