package com.hkt.client.swingexp.app.banhang;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.springframework.jdbc.support.lob.JtaLobCreatorSynchronization;


public class ProductChoose extends JPanel {
  public ProductChoose() {
    setLayout(new BorderLayout());
    PanelTongTien panelTongTien = new PanelTongTien();
    add(panelTongTien, BorderLayout.NORTH);
    PanelTable panelTable= new PanelTable();
    add(panelTable, BorderLayout.CENTER);
  }
  
  static class PanelTongTien extends JPanel{
    public PanelTongTien() {
      setLayout(new FlowLayout(FlowLayout.LEADING));
      setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()));
      JLabel label = new JLabel("Tổng Tiền:");
      label.setFont(new Font("Serif", Font.BOLD, 26));
      add(label);
    }
  }
  
  static class PanelTable extends JPanel{
    public PanelTable() {
      setLayout(new GridLayout());
      JScrollPane scrollPane = new JScrollPane();
      String[] columnNames = { "Product", "Quantity", "Price", "Total", "Unit Money"};
      Object[][] data = {
          {"Fried chicken", new Integer(1), new Integer(10), new Integer(10), "USD"},
          {"Teriyaki", new Integer(1), new Integer(10), new Integer(10), "USD"},
          {"Walnut ricotta", new Integer(1), new Integer(10), new Integer(10), "USD"},
          {"Sandwich", new Integer(1), new Integer(10), new Integer(10), "USD"},
          {"Vodka", new Integer(1), new Integer(15), new Integer(15), "USD"},
      };
      JTable table = new JTable(data, columnNames);
      scrollPane.setViewportView(table);
      add(scrollPane);
    }
  }
}
