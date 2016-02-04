package com.hkt.client.swingexp.app.banhang.screen.often;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.model.PromotionModelManager;
import com.hkt.module.promotion.entity.PromotionUsed;

public class TableModelPromotion extends DefaultTableModel {

  private boolean[] canEdit = new boolean[] { false, false, false, false, false, false };
  private String[] header = { "STT", "Tên SP", "Slg", "Đơn giá", "Tiền", "Tặng SP" };

  private Hashtable<String, PromotionProduct> hashMap;

//  public void updateValue(InvoiceItemPromotion invoiceItem) {
//    PromotionProduct rowVector = hashMap.get(invoiceItem.getName());
//    if (rowVector != null) {
//      rowVector.setQuantity(invoiceItem.getQuantity());
//      fireTableDataChanged();
//    }
//  }

  public void removeValue(String code) {
    try {
      hashMap.get(code).deletePromotion();
      hashMap.remove(code);
      dataVector = convertToVector(this.hashMap.values().toArray());
      fireTableDataChanged();
      PromotionModelManager.getInstance().deletePromotionUsedByCode(code);
    } catch (Exception e) {
    }

  }

  public TableModelPromotion(Hashtable<String, PromotionProduct> hashMap) {
    if(hashMap==null){
      this.hashMap= new Hashtable<String, PromotionProduct>();
    }else {
      this.hashMap = hashMap;  
    }
    
    dataVector = convertToVector(this.hashMap.values().toArray());
  }

  public void setData(Hashtable<String, PromotionProduct> hashMap) {
    this.hashMap = hashMap;
    dataVector = convertToVector(this.hashMap.values().toArray());
    fireTableDataChanged();
  }

  public void addItem(String code, PromotionProduct invoiceItem) {
    hashMap.put(code, invoiceItem);
    int row = getRowCount();
    dataVector.insertElementAt(invoiceItem, row);
    fireTableRowsInserted(row, row);
  }

  @Override
  public int getColumnCount() {
    return header == null ? 0 : header.length;
  }

  @Override
  public String getColumnName(int column) {
    return header[column];
  }

  public void setCanEdit(boolean[] canEdit) {
    this.canEdit = canEdit;
  }

  @Override
  public boolean isCellEditable(int row, int column) {
    return canEdit[column];
  }

  @Override
  public void setValueAt(Object aValue, int row, int column) {
    if (column == 2) {
      PromotionProduct rowVector = (PromotionProduct) dataVector.elementAt(row);
      rowVector.setQuantity(Double.parseDouble(aValue.toString()));
      fireTableRowsUpdated(row, row);
    } else {
      super.setValueAt(aValue, row, column);
    }
  }

  @Override
  public Object getValueAt(int row, int column) {
    switch (column) {
    case 0:
      return row + 1;
    case 1:
      try {
        return ((PromotionProduct) dataVector.get(row));
      } catch (Exception e) {
        Vector rowVector = (Vector) dataVector.elementAt(row);
        return rowVector.elementAt(column);
      }

    case 2:
      try {
        return MyDouble.valueOf(((PromotionProduct) dataVector.get(row)).getQuantity()).toString();
      } catch (Exception e) {
        Vector rowVector = (Vector) dataVector.elementAt(row);
        return rowVector.elementAt(column);
      }

    case 3:
      try {
        return MyDouble.valueOf(((PromotionProduct) dataVector.get(row)).getUnitPrice()).toString();
      } catch (Exception e) {
        Vector rowVector = (Vector) dataVector.elementAt(row);
        return rowVector.elementAt(column);
      }

    case 4:
      try {
        return MyDouble.valueOf(((PromotionProduct) dataVector.get(row)).getTotal()).toString();
      } catch (Exception e) {
        Vector rowVector = (Vector) dataVector.elementAt(row);
        return rowVector.elementAt(column);
      }
    case 5:
      try {
        return "";
      } catch (Exception e) {
        Vector rowVector = (Vector) dataVector.elementAt(row);
        return rowVector.elementAt(column);
      }

    default:
      return "";
    }
  }

  public void saveAllPromotionProduct() throws Exception {
    Enumeration enumeration = hashMap.elements();
    while (enumeration.hasMoreElements()) {
      ((PromotionProduct) enumeration.nextElement()).savePromotion();
    }
  }

  public void updateValuePromotion(PromotionUsed product) {
    PromotionProduct rowVector = hashMap.get(product.getCode());
    if (rowVector != null) {
      rowVector.setUnitPrice(product.getSaving());
      rowVector.setQuantity(product.getQuantity());
      fireTableDataChanged();
    }
    
  }

}
