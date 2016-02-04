package com.hkt.client.swingexp.app.khohang.list;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.module.core.entity.AbstractPersistable.State;
import com.hkt.module.product.entity.Product;
import com.hkt.module.warehouse.entity.IdentityProduct;

public class TableModelIdentityProductImport extends DefaultTableModel {
  private DateFormat            dfDate = new SimpleDateFormat("dd/MM/yyyy");
  private List<IdentityProduct> listAll;

  private String[]              header = new String[] { "", "STT", "Tên sản phầm", "ĐV sản phầm", "Số lượng",
      "Đơn giá", "ĐV tiền"            };

  public TableModelIdentityProductImport(List<IdentityProduct> eventorys, List<IdentityProduct> listAll) {
    this.listAll = listAll;
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
        return (IdentityProduct) dataVector.get(row);

      case 1:
        try {
          return row + 1;
        } catch (Exception e) {
          Vector rowVector = (Vector) dataVector.elementAt(row);
          return rowVector.elementAt(column);
        }
      case 2:
        try {
          Product product = ProductModelManager.getInstance().getProductByCode(
              ((IdentityProduct) dataVector.get(row)).getProductCode());
          return product;
        } catch (Exception e) {
          Vector rowVector = (Vector) dataVector.elementAt(row);
          return rowVector.elementAt(column);
        }
      case 3:
        try {
          String productCode = ((IdentityProduct) dataVector.get(row)).getProductCode();
          String unit = "";
          if (productCode != null) {
            unit = ProductModelManager.getInstance().getProductByCode(productCode).getUnit();
          } else {
            unit = "";
          }
          return unit;
        } catch (Exception e) {
          Vector rowVector = (Vector) dataVector.elementAt(row);
          return rowVector.elementAt(column);
        }

      case 4:
        try {
          if (listAll != null) {
            String productCode = ((IdentityProduct) dataVector.get(row)).getProductCode();
            int count = 0;
            for (IdentityProduct identityProduct : listAll) {
              String productCodeCount = identityProduct.getProductCode();
              if (productCodeCount.equals(productCode) && identityProduct.getPersistableState() != State.DELETED) {
                count++;
              }
            }
            return count;
          }
          return 1;
        } catch (Exception e) {
          Vector rowVector = (Vector) dataVector.elementAt(row);
          return rowVector.elementAt(column);
        }
      case 5:
        try {
          return ((IdentityProduct) dataVector.get(row)).getPrice();
        } catch (Exception e) {
          Vector rowVector = (Vector) dataVector.elementAt(row);
          return rowVector.elementAt(column);
        }
      case 6:
        try {
          return  ((IdentityProduct) dataVector.get(row)).getUnitPrice();
        } catch (Exception e) {
          Vector rowVector = (Vector) dataVector.elementAt(row);
          return rowVector.elementAt(column);
        }

      default:
        return "";
    }

  }

}
