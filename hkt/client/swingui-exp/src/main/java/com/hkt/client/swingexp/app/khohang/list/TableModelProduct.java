package com.hkt.client.swingexp.app.khohang.list;

import java.awt.Dialog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.client.swingexp.app.core.ITableModel;
import com.hkt.client.swingexp.app.core.ITableModelExt;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductAttribute;
import com.hkt.module.product.entity.ProductTag;

@SuppressWarnings("serial")
public class TableModelProduct extends DefaultTableModel implements ITableModelExt {
	private String[] header = { "STT", "Mã sản phẩm", "Tên sản phẩm","Mã barcode","Hạn sử dụng","Nhóm", "Ngoại ngữ","Giá thống kê", "Đơn vị","Tính TK", "Xóa" };
	private HashMap<String, String> list = new HashMap<String, String>();
	private int size;
	private HashMap<String, Integer> numberSize = new HashMap<String, Integer>();

	public TableModelProduct(List<Product> listProduct) {
		dataVector = convertToVector(listProduct.toArray());
		numberSize.put(header[0], 50);
		numberSize.put(header[8], 100);
		numberSize.put(header[9], 80);
		numberSize.put(header[10], 50);
	}

	public void setData(List<Product> listProduct, int size) {
		this.size = size;
		dataVector = convertToVector(listProduct.toArray());
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
		if (column < header.length - 2) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		if (column == header.length - 1) {
			((Product) dataVector.get(row)).setRecycleBin(Boolean.parseBoolean(aValue.toString()));
		}
		if (column == header.length - 2) {
			((Product) dataVector.get(row)).setCalculateReport(Boolean.parseBoolean(aValue.toString()));
			try {
				ProductModelManager.getInstance().saveProduct(((Product) dataVector.get(row)));
      } catch (Exception e) {
      }
			
		}
	}

	@Override
	public void fireTableRowsUpdated(int row, int lastRow) {
		super.fireTableRowsUpdated(row, lastRow);
		try {
			Product p = ProductModelManager.getInstance().getProductByCode(((Product) dataVector.get(row)).getCode());
			dataVector.set(row, p);
			fireTableDataChanged();
			String str = LocaleModelManager.getInstance().getProductUnitByCode(((Product) dataVector.get(row)).getUnit())
			    .getName();
			list.put(((Product) dataVector.get(row)).getUnit(), str);
		} catch (Exception e) {
			list.put(((Product) dataVector.get(row)).getUnit(), "");
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getValueAt(int row, int column) {
		switch (column) {
		case 0:
			try {
				return row + 1 + size;
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 1:
			try {
				return ((Product) dataVector.get(row)).codeView();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 2:
			try {
				try {
					try {
						if (list.get(((Product) dataVector.get(row)).getUnit()) == null) {
							String str = LocaleModelManager.getInstance()
							    .getProductUnitByCode(((Product) dataVector.get(row)).getUnit()).getName();
							list.put(((Product) dataVector.get(row)).getUnit(), str);
						}
					} catch (Exception e) {
						list.put(((Product) dataVector.get(row)).getUnit(), "");
					}
					return ((Product) dataVector.get(row));
				} catch (Exception e) {
					return new Product();
				}

			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 3:
			try {
				return ((Product) dataVector.get(row)).getDescription();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 4:
			try {
				return ((Product) dataVector.get(row)).getModifiedBy();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 5:
			try {
				if (((Product) dataVector.get(row)).getProductTags().size() > 0) {
					return ((Product) dataVector.get(row)).getProductTags().toString().substring(1, 
							((Product) dataVector.get(row)).getProductTags().toString().length()-1);
				} else {
					return "";
				}

			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 6:
			try {
				return ((Product) dataVector.get(row)).getNameOther();
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 7:
			try {
				return new MyDouble(((Product) dataVector.get(row)).getPrice());
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 8:
			try {
				try {
					return list.get(((Product) dataVector.get(row)).getUnit());
				} catch (Exception e) {
					return "";
				}
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 9:
			try {
				try {
					return ((Product) dataVector.get(row)).isCalculateReport();
				} catch (Exception e) {
					return "";
				}
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		default:
			return ((Product) dataVector.get(row)).isRecycleBin();
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return Integer.class;
		case 2:
			return Product.class;
		case 7:
			return MyDouble.class;
		case 9:
			return Boolean.class;
		case 10:
			return Boolean.class;
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
		return "Quản lý hàng hóa";
	}

	@Override
	public boolean loadDataColum(String name) {
		try {
			AccountingModelManager.getInstance().updatePriceProduct();
			DialogNotice.getInstace().setVisible(true);
		} catch (Exception e) {
		}

		return false;
	}
}
