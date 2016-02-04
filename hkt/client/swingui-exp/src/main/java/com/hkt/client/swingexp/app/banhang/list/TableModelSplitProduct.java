package com.hkt.client.swingexp.app.banhang.list;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;

public class TableModelSplitProduct extends DefaultTableModel {
	private String[]					header	= { "STT","Sản phẩm", "Số phiếu","Số lượng", "Đơn vị", "Doanh thu", "Đơn vị", "Ngày", "Nhân viên", "Bàn", "Đối tác", "Tình trạng", "Ghi chú" };
	private SimpleDateFormat	sdf			= new SimpleDateFormat("dd/M/yyyy");
	private HashMap<String, String> list = new HashMap<String, String>();
	
	public TableModelSplitProduct(List<SplitProduct> listSplitProduct) {
		dataVector = convertToVector(listSplitProduct.toArray());
	}
	
	public void setData(List<SplitProduct> listSplitProduct) {
    dataVector = convertToVector(listSplitProduct.toArray());
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

	
	
	@Override
	public void fireTableRowsUpdated(int firstRow, int lastRow) {
		super.fireTableRowsUpdated(firstRow, lastRow);
		try {
			String str = LocaleModelManager.getInstance().getProductUnitByCode(((SplitProduct) dataVector.get(firstRow)).getUnit1()).toString();
			list.put(((SplitProduct) dataVector.get(firstRow)).getUnit1(), str);
		} catch (Exception e) {
			list.put(((SplitProduct) dataVector.get(firstRow)).getUnit1(), "");
		}
		
	}

	@Override
	public Object getValueAt(int row, int column) {
		switch (column) {
		case 0:
      try {
        return row +1;
      } catch (Exception e) {
        Vector rowVector = (Vector) dataVector.elementAt(row);
        return rowVector.elementAt(column);
      }
			case 1:
				try {
					  try {
						  if(list.get(((SplitProduct) dataVector.get(row)).getUnit1()) == null)
						  {
							  String str = LocaleModelManager.getInstance().getProductUnitByCode(((SplitProduct) dataVector.get(row)).getUnit1()).toString();
							  list.put(((SplitProduct) dataVector.get(row)).getUnit1(), str);
						  }
					} catch (Exception e) {
						 list.put(((SplitProduct) dataVector.get(row)).getUnit1(), "");
					}
					return ((SplitProduct) dataVector.get(row)).getProduct();
				} catch (Exception e) {
					Vector rowVector = (Vector) dataVector.elementAt(row);
					return rowVector.elementAt(column);
				}
			case 2:
				try {
					return  ((SplitProduct) dataVector.get(row)).getInvoiceCode();
				} catch (Exception e) {
					Vector rowVector = (Vector) dataVector.elementAt(row);
					return rowVector.elementAt(column);
				}

			case 3:
				try {
					String quantity = ((SplitProduct) dataVector.get(row)).getQuantity();
					if(!quantity.isEmpty() || !quantity.equals(""))
						return MyDouble.valueOf(quantity);
					else return 0;
				} catch (Exception e) {
					Vector rowVector = (Vector) dataVector.elementAt(row);
					return rowVector.elementAt(column);
				}

			case 4:
				try {
				  try {
				    return list.get(((SplitProduct) dataVector.get(row)).getUnit1());
          } catch (Exception e) {
            return "";
          }
					
				} catch (Exception e) {
					Vector rowVector = (Vector) dataVector.elementAt(row);
					return rowVector.elementAt(column);
				}

			case 5:
				try {
					String total = ((SplitProduct) dataVector.get(row)).getFinalCharge();
					if(!total.equals("") || !total.isEmpty())
						return MyDouble.valueOf(total);
					else return 0;
				} catch (Exception e) {
					Vector rowVector = (Vector) dataVector.elementAt(row);
					return rowVector.elementAt(column);
				}

			case 6:
				try {
					return ((SplitProduct) dataVector.get(row)).getUnit2();
				} catch (Exception e) {
					Vector rowVector = (Vector) dataVector.elementAt(row);
					return rowVector.elementAt(column);
				}
			case 7:
				try {
					String date = ((SplitProduct) dataVector.get(row)).getDate();
					if(!date.isEmpty() || !date.equals(""))
						return sdf.format(sdf.parse(date));
					else return "";
				} catch (Exception e) {
					return "";
				}
			case 8:
				try {
					String employee = ((SplitProduct) dataVector.get(row)).getEmployee();
					return employee;
				} catch (Exception e) {
					Vector rowVector = (Vector) dataVector.elementAt(row);
					return rowVector.elementAt(column);
				}
			case 9:
				try {
					String table = ((SplitProduct) dataVector.get(row)).getTable();
					return table;
				} catch (Exception e) {
					Vector rowVector = (Vector) dataVector.elementAt(row);
					return rowVector.elementAt(column);
				}
			case 10:
				try {
					String partner = ((SplitProduct) dataVector.get(row)).getPartner();
					return partner;
				} catch (Exception e) {
					Vector rowVector = (Vector) dataVector.elementAt(row);
					return rowVector.elementAt(column);
				}
			case 11:
				try {
					String status = ((SplitProduct) dataVector.get(row)).getStatus();
					return status;
				} catch (Exception e) {
					Vector rowVector = (Vector) dataVector.elementAt(row);
					return rowVector.elementAt(column);
				}
			case 12:
				try {
					String note = ((SplitProduct) dataVector.get(row)).getNote();
					return note;
				} catch (Exception e) {
					Vector rowVector = (Vector) dataVector.elementAt(row);
					return rowVector.elementAt(column);
				}
			default:
				return "";
		}
	}
}
