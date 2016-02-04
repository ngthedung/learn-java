package com.hkt.client.swingexp.app.khuyenmai.list;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.module.promotion.entity.Promotion;
import com.hkt.module.promotion.entity.PromotionDetail;

@SuppressWarnings("serial")
public class TableModelPromotion extends DefaultTableModel {

  private String[] header = { "STT", "Tên KM", "KM Từ ngày", "KM Đến ngày", "Trạng thái", "Sử dụng", "Tỷ lệ giảm giá", "Giảm giá",
      "Tiền tối thiểu", "Tiền tối đa", "Đơn vị", "Mô tả" };

  public TableModelPromotion(List<PromotionDetail> listPromotion) {
    dataVector = convertToVector(listPromotion.toArray());
  }

  // Bước 7 : Xây dựng hàm setData
  public void setData(List<PromotionDetail> listPromotion) {
    dataVector = convertToVector(listPromotion.toArray());
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

  @Override
  public boolean isCellEditable(int row, int column) {
    return false;
  }

  @SuppressWarnings("rawtypes")
@Override
  public Object getValueAt(int row, int column) {
    switch (column) {
    case 0:
      try {
        return ((Promotion) dataVector.get(row));
      } catch (Exception e) {
        Vector rowVector = (Vector) dataVector.elementAt(row);
        return rowVector.elementAt(column);
      }

    case 1:
      try {
        Date date = ((Promotion) dataVector.get(row)).getFromDate();
        ;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        return sdf.format(date);
      } catch (Exception e) {
        Vector rowVector = (Vector) dataVector.elementAt(row);
        return rowVector.elementAt(column);
      }

    case 2:
      try {
        try {
          Date date = ((Promotion) dataVector.get(row)).getToDate();
          SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
          return sdf.format(date);
        } catch (Exception e) {
          return "";
        }

      } catch (Exception e) {
        Vector rowVector = (Vector) dataVector.elementAt(row);
        return rowVector.elementAt(column);
      }

    case 3:
      try {
        return ((Promotion) dataVector.get(row)).getStatus();
      } catch (Exception e) {
        Vector rowVector = (Vector) dataVector.elementAt(row);
        return rowVector.elementAt(column);
      }

    case 4:
      try {
        return ((Promotion) dataVector.get(row)).getMaxUsePerCustomer();
      } catch (Exception e) {
        Vector rowVector = (Vector) dataVector.elementAt(row);
        return rowVector.elementAt(column);
      }

    case 5:
      try {
        return ((Promotion) dataVector.get(row)).getDiscountRate();
      } catch (Exception e) {
        Vector rowVector = (Vector) dataVector.elementAt(row);
        return rowVector.elementAt(column);
      }

    case 6:
      try {
        return ((Promotion) dataVector.get(row)).getDiscount();
      } catch (Exception e) {
        Vector rowVector = (Vector) dataVector.elementAt(row);
        return rowVector.elementAt(column);
      }

    case 7:
      try {
        return ((PromotionDetail) dataVector.get(row)).getAppliedToMinExpense();
      } catch (Exception e) {
        Vector rowVector = (Vector) dataVector.elementAt(row);
        return rowVector.elementAt(column);
      }

    case 8:
      try {
        return ((PromotionDetail) dataVector.get(row)).getMaxExpense();
      } catch (Exception e) {
        Vector rowVector = (Vector) dataVector.elementAt(row);
        return rowVector.elementAt(column);
      }

    case 9:
      try {
        return ((Promotion) dataVector.get(row)).getCurrencyUnit();
      } catch (Exception e) {
        Vector rowVector = (Vector) dataVector.elementAt(row);
        return rowVector.elementAt(column);
      }

    case 10:
      try {
        return ((Promotion) dataVector.get(row)).getDescription();
      } catch (Exception e) {
        Vector rowVector = (Vector) dataVector.elementAt(row);
        return rowVector.elementAt(column);
      }

    default:
      return "";
    }
  }

}
