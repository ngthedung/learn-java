package com.hkt.client.swingexp.app.banhang.screen.often;

import java.util.ArrayList;
import java.util.List;

import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.core.TableModelObservable;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;

public class TableModelDiscountItem extends TableModelObservable {

  private boolean[] canEdit = new boolean[] { false, false, false, false, false, false, false };
  private String[] header = { "STT", "Tên SP", "Số lượng", "Tổng tiền", "%", "Chiết khấu", "" };
  private List<InvoiceItem> list = new ArrayList<InvoiceItem>();


  public TableModelDiscountItem(InvoiceDetail invoiceDetail) {
	for(int i=0;i<invoiceDetail.getItems().size();i++){
		if(invoiceDetail.getItems().get(i).getDiscountRate()!=0){
			list.add(invoiceDetail.getItems().get(i));
		}
	}
    dataVector = convertToVector(list.toArray());
  }



  public void setData(InvoiceDetail invoiceDetail) {
	  list.clear();
	  for(int i=0;i<invoiceDetail.getItems().size();i++){
			if(invoiceDetail.getItems().get(i).getDiscountRate()!=0){
				list.add(invoiceDetail.getItems().get(i));
			}
		}
    dataVector = convertToVector(list.toArray());
    fireTableDataChanged();
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
  public Object getValueAt(int row, int column) {
    switch (column) {
    case 0:
      return row + 1;
    case 1:
      try {
        return ((InvoiceItem) dataVector.get(row));
      } catch (Exception e) {
        return "";
      }

    case 2:
      try {
        return MyDouble.valueOf(((InvoiceItem) dataVector.get(row)).getQuantity()).toString();
      } catch (Exception e) {
        return "";
      }

    case 3:
      try {
        return MyDouble.valueOf(((InvoiceItem) dataVector.get(row)).getTotal()).toString();
      } catch (Exception e) {
        return "";
      }

    case 4:
      try {
        return MyDouble.valueOf(((InvoiceItem) dataVector.get(row)).getDiscountRate()).toString();
      } catch (Exception e) {
        return "";
      }

    case 5:
      try {
        return MyDouble.valueOf(((InvoiceItem) dataVector.get(row)).getDiscount()).toString();
      } catch (Exception e) {
        return "";
      }
    case 6:
      try {
        return "";
      } catch (Exception e) {
        return "";
      }

    default:
      return "";
    }
  }

  

}
