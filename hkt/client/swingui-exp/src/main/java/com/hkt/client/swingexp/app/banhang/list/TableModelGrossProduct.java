package com.hkt.client.swingexp.app.banhang.list;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.ITableModel;
import com.hkt.client.swingexp.app.core.MyDate;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.model.LocaleModelManager;

@SuppressWarnings("serial")
public class TableModelGrossProduct extends DefaultTableModel implements ITableModel{
	private String						header[]	= { "STT", "Nghiệp vụ", "Số lượng", "Đơn vị", "Doanh thu", "Đơn vị", "Tình trạng", "Từ ngày", "Đến ngày" };
	private SimpleDateFormat	sdf				= new SimpleDateFormat("dd/M/yyyy");
	private HashMap<String, Integer> numberSize = new HashMap<String, Integer>();
	private HashMap<String, String> list = new HashMap<String, String>();
	public TableModelGrossProduct(List<GrossProduct> listExam) {
		dataVector = convertToVector(listExam.toArray());
		
		numberSize.put(header[0], 50);
		numberSize.put(header[2], 80);
		numberSize.put(header[3], 80);
		numberSize.put(header[5], 80);
		numberSize.put(header[6], 150);
		numberSize.put(header[7], 120);
		numberSize.put(header[8], 120);
	}
	
	public void setData(List<GrossProduct> product) {
		dataVector = convertToVector(product.toArray());
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
			String str = LocaleModelManager.getInstance().getProductUnitByCode(((GrossProduct) dataVector.get(firstRow)).getUnit1()).toString();
			list.put(((GrossProduct) dataVector.get(firstRow)).getUnit1(), str);
		} catch (Exception e) {
			list.put(((GrossProduct) dataVector.get(firstRow)).getUnit1(), "");
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getValueAt(int row, int column) {
		switch (column) {
		case 0:
			try {
				return row + 1;
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
			case 1:
				try {
					return ((GrossProduct) dataVector.get(row)).getBusiness();
				} catch (Exception e) {
					Vector rowVector = (Vector) dataVector.elementAt(row);
					return rowVector.elementAt(column);
				}
			case 2:
				try {
					return ((GrossProduct) dataVector.get(row)).getQuantity();
				} catch (Exception e) {
					Vector rowVector = (Vector) dataVector.elementAt(row);
					return rowVector.elementAt(column);
				}

			case 3:
				try {
				  try {
					  try {
						if(list.get(((GrossProduct) dataVector.get(row)).getUnit1()) == null)
						{
							String str = LocaleModelManager.getInstance().getProductUnitByCode(((GrossProduct) dataVector.get(row)).getUnit1()).toString();
							list.put(((GrossProduct) dataVector.get(row)).getUnit1(), str);	
						}
					} catch (Exception e) {
						list.put(((GrossProduct) dataVector.get(row)).getUnit1(), "");	
					}
            return list.get(((GrossProduct) dataVector.get(row)).getUnit1());
          } catch (Exception e) {
            return "";
          }
				} catch (Exception e) {
					Vector rowVector = (Vector) dataVector.elementAt(row);
					return rowVector.elementAt(column);
				}

			case 4:
				try {
					return new MyDouble(((GrossProduct) dataVector.get(row)).getTurnover());
				} catch (Exception e) {
					Vector rowVector = (Vector) dataVector.elementAt(row);
					return rowVector.elementAt(column);
				}

			case 5:
				try {
					return ((GrossProduct) dataVector.get(row)).getUnit2();
				} catch (Exception e) {
					Vector rowVector = (Vector) dataVector.elementAt(row);
					return rowVector.elementAt(column);
				}

			case 6:
				try {
					return ((GrossProduct) dataVector.get(row)).getStatus();
				} catch (Exception e) {
					Vector rowVector = (Vector) dataVector.elementAt(row);
					return rowVector.elementAt(column);
				}
			case 7:
				try {
					String date = ((GrossProduct) dataVector.get(row)).getStartDate();
					if (!date.equals("") || !date.isEmpty())
						return new MyDate(sdf.parse(date));
					else return new MyDate(null);
				} catch (Exception e) {
					return new MyDate(null);
				}
			case 8:
				try {
					String date = ((GrossProduct) dataVector.get(row)).getEndDate();
					if (!date.equals("") || !date.isEmpty())
						return new MyDate(sdf.parse(date));
					else return new MyDate(null);
				} catch (Exception e) {
					return new MyDate(null);
				}
			default:
				return "";
		}
	}
	
	@Override
	  public Class<?> getColumnClass(int columnIndex) {
	    switch (columnIndex) {
	    case 0:
			return Integer.class;
	    case 4:
	      return MyDouble.class;
	    case 7:
	      return MyDate.class;
	    case 8:
		  return MyDate.class;
	    default:
	      return String.class;
	    }
	  }

	@Override
	public HashMap<String, Integer> getColumnSize() {
		return numberSize;
	}

	@Override
	public String getNameTableModel() {
		return "Danh sách sản phẩm";
	}
}
