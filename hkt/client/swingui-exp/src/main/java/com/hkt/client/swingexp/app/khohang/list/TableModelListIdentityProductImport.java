package com.hkt.client.swingexp.app.khohang.list;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.WarehouseModelManager;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.accounting.entity.InvoiceItemAttribute;
import com.hkt.module.product.entity.Product;

public class TableModelListIdentityProductImport extends DefaultTableModel {
  private String[] header = new String[] { "", "STT", "Mã hóa đơn", "Tên sản phầm", "ĐV sản phầm", "Số lượng", "Đơn giá", "ĐV tiền" };

  public TableModelListIdentityProductImport(List<InvoiceItem> eventorys) {
    dataVector = convertToVector(eventorys.toArray());
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

  @Override
  public Object getValueAt(int row, int column) {
    switch (column) {
      case 0:
        return (InvoiceItem) dataVector.get(row);
      case 1:
        try {
          return row + 1;
        } catch (Exception e) {
          e.printStackTrace();
        }
      case 2:
//        InvoiceItem invoiceItem = (InvoiceItem) dataVector.get(row);
//        InvoiceDetail detail = null;
//        try {
//          detail = AccountingModelManager.getInstance().findOneByInvoiceItem(invoiceItem);
//        } catch (Exception e) {
//          e.printStackTrace();
//        }
        return ((InvoiceItem) dataVector.get(row)).getInvoiceCode();
      case 3:
        try {
          Product product = ProductModelManager.getInstance().getProductByCode(((InvoiceItem) dataVector.get(row)).getProductCode());
          return product;
        } catch (Exception e) {
          e.printStackTrace();
        }
      case 4:
        try {
          String productCode = ((InvoiceItem) dataVector.get(row)).getProductCode();
          String unit = ProductModelManager.getInstance().getProductByCode(productCode).getUnit();
          if (unit != null) {
            return unit;
          } else {
            return "";
          }
        } catch (Exception e) {
          return "";
        }
      case 5:
        try {
          double quantity = ((InvoiceItem) dataVector.get(row)).getQuantity()
              - ((InvoiceItem) dataVector.get(row)).getQuantityReal();
          return quantity;
        } catch (Exception e) {
          e.printStackTrace();
        }
      case 6:
        try {
          return ((InvoiceItem) dataVector.get(row)).getFinalCharge();
        } catch (Exception e) {
          e.printStackTrace();
        }
      case 7:
        try {
          return ((InvoiceItem) dataVector.get(row)).getCurrencyUnit();
        } catch (Exception e) {
          e.printStackTrace();
        }

      default:
        return "";
    }
  }

}
