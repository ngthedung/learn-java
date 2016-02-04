package com.hkt.client.swingexp.app.core;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import com.hkt.client.swingexp.model.GuiModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.module.restaurant.entity.Table;

public class TableJComboBox extends JComboBox<Table> {
  private List<Table> table;

  public TableJComboBox() {
    super();
    setFont(new Font("Tahoma", 0, 14));
    setOpaque(false);
    setPreferredSize(new Dimension(200, 23));
    resetData();
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getButton() == 3) {
          new GuiModelManager<Table>().viewDialog(TableJComboBox.this, Table.class);
          resetData();
        }
      }
    });
  }
  
  public void dataMarket(){
    try {
      table = RestaurantModelManager.getInstance().getTables();
    } catch (Exception e) {
      e.printStackTrace();
    }
    table.add(0, RestaurantModelManager.getInstance().getTablePaymentAfter());
    setModel(new DefaultComboBoxModel(table.toArray()));
  }

  public void resetData() {
    table = new ArrayList<Table>();
    try {
      table = RestaurantModelManager.getInstance().getTables();
    } catch (Exception e) {
      e.printStackTrace();
    }
    table.add(0, null);
    setModel(new DefaultComboBoxModel(table.toArray()));
  }

  public void setData(List<Table> listTable) {
    table = listTable;
    table.add(0, null);
    setModel(new DefaultComboBoxModel(table.toArray()));
  }

  public void removeIndex(int index) {
    table.remove(index);
    setModel(new DefaultComboBoxModel(table.toArray()));
  }

  public Table getSelectedTable() {
    try {
      return (Table) getSelectedItem();
    } catch (Exception ex) {
      return null;
    }
  }

}
