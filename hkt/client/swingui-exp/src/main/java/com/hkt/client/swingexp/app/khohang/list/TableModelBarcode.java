package com.hkt.client.swingexp.app.khohang.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.client.swingexp.app.core.ITableModelExt;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductAttribute;
import com.hkt.module.product.entity.ProductPrice;
import com.hkt.module.product.entity.ProductTag;

public class TableModelBarcode extends DefaultTableModel implements ITableModelExt {
	private String[] header = { "STT", "Mã barcode", "Tên sản phẩm", "Đơn giá", "ĐV Tiền" };
	private String pricingType;
	private String currency = "VND";
	private HashMap<String, Integer> numberSize = new HashMap<String, Integer>();

	public TableModelBarcode(List<Product> listProduct, String pricingType, String currency) {
		this.pricingType = pricingType;
		this.currency = currency;
		dataVector = convertToVector(listProduct.toArray());
		numberSize.put(header[0], 50);

	}

	public void setData(List<Product> list) {
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

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

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
				try {
					return ((Product) dataVector.get(row)).getDescription();
				} catch (Exception e) {
					return ((Product) dataVector.get(row)).getCode();
				}

			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}
		case 2:
			try {
				return ((Product) dataVector.get(row));
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 3:
			try {
				try {
					ProductPrice priceDetail = ProductModelManager.getInstance().getByProductAndProductPriceType(
					    ((Product) dataVector.get(row)).getCode(), pricingType, "VND");
					return new MyDouble(priceDetail.getPrice());
				} catch (Exception e) {
					try {
						MyDouble b = new MyDouble(0);
						List<ProductPrice> priceDetail = ProductModelManager.getInstance().getProductPriceByProductCode(
						    ((Product) dataVector.get(row)).getCode());
						for (ProductPrice a : priceDetail) {
							if (a.getProductPriceType().getType().equals(pricingType)) {
								b = new MyDouble(a.getPrice());
							}
						}
						return b;
					} catch (Exception e2) {
						return 0;
					}
				}

			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		case 4:
			try {
				return currency;
			} catch (Exception e) {
				Vector rowVector = (Vector) dataVector.elementAt(row);
				return rowVector.elementAt(column);
			}

		default:
			return "";
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 3:
			return MyDouble.class;
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
		return "Quản lý mã barcode";
	}

	@Override
	public boolean loadDataColum(String name) {
		try {
			List<Product> products = ProductModelManager.getInstance().findAllProducts();
			for (Product p : products) {
				if(p.getDescription()==null || p.getDescription().isEmpty()){
					p.setDescription(p.getCode());
				}
				ProductModelManager.getInstance().saveProduct(p);
			}
			JOptionPane.showMessageDialog(null, "Thanh cong");
		} catch (Exception e) {
		}

		return false;
	}
}
