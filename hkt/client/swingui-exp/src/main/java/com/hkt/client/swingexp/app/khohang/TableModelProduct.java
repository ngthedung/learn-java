package com.hkt.client.swingexp.app.khohang;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductPrice;

public class TableModelProduct extends AbstractTableModel {
	public static String nameProduct = "Tên sản phẩm";

	private String[] heade1 = new String[] { nameProduct, "Đơn vị", "Giá" };
	private String[] heade2 = new String[] { nameProduct, "Đơn vị" };
	private String[] header;
	private List<Product> list;
	private List<ProductPrice> list1;

	public TableModelProduct(String tag, String pString, String curceny) {
		this.header = heade1;
		list1 = ProductModelManager.getInstance().findByProductPriceByTag(tag, pString, curceny);
		
	}
	
	public TableModelProduct(List<Product> list) {
		this.list = list;
		this.header = heade2;
	}

	@Override
	public String getColumnName(int column) {
		return header[column];
	}

	@Override
	public int getRowCount() {
		if(header.equals(heade1)){
			return list1.size();
		}else {
			return list.size();
		}
		
	}

	@Override
	public int getColumnCount() {
		return header.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(header.equals(heade1)){
			switch (columnIndex) {

			case 0:
				try {
					return list1.get(rowIndex).getProduct();
				} catch (Exception e) {
					return "";
				}

			case 1:
				try {
					return LocaleModelManager.getInstance()
							.getProductUnitByCode(list1.get(rowIndex).getUnit())
							.toString();
				} catch (Exception e) {
					return "";
				}
			case 2:
				try {
					return new MyDouble(list1.get(rowIndex).getPrice());
				} catch (Exception e) {
					return "0";
				}
			default:
				return "";
			}
		}else {
			switch (columnIndex) {

			case 0:
				try {
					return list.get(rowIndex);
				} catch (Exception e) {
					return "";
				}

			case 1:
				try {
					return LocaleModelManager.getInstance()
							.getProductUnitByCode(list.get(rowIndex).getUnit())
							.toString();
				} catch (Exception e) {
					return "";
				}
			default:
				return "";
			}
		}
		
	}
}
