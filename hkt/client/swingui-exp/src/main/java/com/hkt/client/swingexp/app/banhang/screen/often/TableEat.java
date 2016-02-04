package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;

import com.hkt.client.swingexp.app.core.DialogList;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.module.restaurant.entity.Table;
import com.hkt.module.restaurant.entity.Table.Status;

public class TableEat extends JLabel {
  private boolean hadMouseListener;
  private boolean hadMouseMotionListener;
  private Table table = null;
  private final Color FREE_COLOR = new Color(240, 240, 240);
  private final Color NOT_FREE_COLOR = new Color(182, 89, 73);
  private boolean a;

  public TableEat(Table table) {
    this.table = table;
    setOpaque(true);
    if (table != null) {
      setText(table.toString());
      setName(table.toString());
    }
    setHorizontalAlignment(JLabel.CENTER);
    setColorTable();
  }

  public TableEat() {

  }

  public Table getTable() {
    return table;
  }

  public void setTable(Table table) {
    this.table = table;
  }

  @Override
  public synchronized void addMouseListener(MouseListener l) {
    if (hadMouseListener) {
      return;
    }
    hadMouseListener = true;
    super.addMouseListener(l);
  }

  @Override
  public synchronized void addMouseMotionListener(MouseMotionListener l) {
    if (hadMouseMotionListener) {
      return;
    }
    hadMouseMotionListener = true;
    super.addMouseMotionListener(l);
  }

  public boolean isHaveGuest() {
    return a;
  }

  public void setStatus(Status status) {
    try {
      table = RestaurantModelManager.getInstance().getTableByCode(table.getCode());
      table.setStatus(status);
      if (status.equals(Table.Status.TableFree)) {
        table.setInvoiceCode("");
      }
      table=RestaurantModelManager.getInstance().saveTable(table);
    } catch (Exception e) {
    }

  }

  public Status getStatus() {
    return table.getStatus();
  }
  
  public void reset(){
  	table = RestaurantModelManager.getInstance().getTableByCode(table.getCode());
  }

  public void setColorTable() {
    try {
      table = RestaurantModelManager.getInstance().getTableByCode(table.getCode());
      if (table.getStatus() == Status.TableFree) {
        setColor(FREE_COLOR);
        a = false;
      } else {
        a = true;
        if (table.getStatus() == Status.TableGross) {
          setColor(Color.YELLOW);
        } else {
          if (table.getStatus() == Status.TableServe) {
            setColor(new Color(0, 204, 204));
          } else {
            if (table.getStatus() == Status.TableKitchen) {
              setColor(new Color(255, 128, 0));
            } else {
              if (table.getStatus() == Status.TableSet) {
                setColor(Color.LIGHT_GRAY);
              } else {
                setColor(NOT_FREE_COLOR);
              }
            }
          }
        }

      }
    } catch (Exception e) {
    }

  }

  public void setColor(Color color) {
    setBackground(color);
  }

  @Override
  public String toString() {
    return table.getName();
  }

}
