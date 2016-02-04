package com.hkt.client.swingexp.app.khohang.list;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.WarehouseModelManager;
import com.hkt.module.config.locale.ProductUnit;
import com.hkt.module.product.entity.Product;
import com.hkt.module.warehouse.entity.IdentityProduct;
import com.hkt.module.warehouse.entity.Warehouse;

public class TableModelChangeIdentityProduct extends DefaultTableModel {
	private List<IdentityProduct>	identityProducts;
	private String[]							header	= new String[] { "", "STT", "Sản phẩm", "ĐVSP", "Số lượng", "Đơn giá", "ĐVT", "Kho" };
	private HashMap<String, String> list1, list2,list3 = new HashMap<String, String>();
	
	public TableModelChangeIdentityProduct(List<IdentityProduct> identityProducts) {
		dataVector = convertToVector(identityProducts.toArray());
		this.identityProducts = identityProducts;
	}

	public void setDataList(List<IdentityProduct> identityProducts) {
		dataVector = convertToVector(identityProducts.toArray());
		this.identityProducts = identityProducts;
		this.fireTableDataChanged();
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
	public void fireTableRowsUpdated(int row, int lastRow) {
		super.fireTableRowsUpdated(row, lastRow);
		String productCode = ((IdentityProduct) dataVector.get(row)).getProductCode();
		try {
			String product = ProductModelManager.getInstance().getProductByCode(productCode).toString();
			list1.put(productCode, product);
				String unit = ProductModelManager.getInstance().getProductByCode(productCode).getUnit();
				String unitProduct = LocaleModelManager.getInstance().getProductUnitByCode(unit).toString();
				list2.put(productCode, unitProduct);
		} catch (Exception e) {
			list1.put(((IdentityProduct) dataVector.get(row)).getProductCode(), "");
			list2.put(((IdentityProduct) dataVector.get(row)).getProductCode(), "");
		}
		try {
			String warehouse = WarehouseModelManager.getInstance().getWarehouseByWarehouseId(((IdentityProduct) dataVector.get(row)).getWarehouseCode()).toString();
			list3.put(((IdentityProduct) dataVector.get(row)).getWarehouseCode(), warehouse);
		} catch (Exception e) {
			list3.put(((IdentityProduct) dataVector.get(row)).getWarehouseCode(), "");
		}
	}

	@Override
	public Object getValueAt(int row, int column) {
		switch (column) {
			case 0:
				try {
					return (IdentityProduct) dataVector.get(row);
				} catch (Exception e) {
					return null;
				}

			case 1:
				try {
					return row + 1;
				} catch (Exception e) {
					return "";
				}

			case 2:
				try {
					String productCode = ((IdentityProduct) dataVector.get(row)).getProductCode();
					try {
						if(list1.get(productCode) == null)
						{
						String product = ProductModelManager.getInstance().getProductByCode(productCode).toString();
						list1.put(productCode, product);
						}
						if(list2.get(productCode) == null)
						{
							String unit = ProductModelManager.getInstance().getProductByCode(productCode).getUnit();
							String unitProduct = LocaleModelManager.getInstance().getProductUnitByCode(unit).toString();
							list2.put(productCode, unitProduct);
						}
					} catch (Exception e) {
						list1.put(((IdentityProduct) dataVector.get(row)).getProductCode(), "");
						list2.put(((IdentityProduct) dataVector.get(row)).getProductCode(), "");
					}
					try {
						if(list3.get(((IdentityProduct) dataVector.get(row)).getWarehouseCode()) ==null)
						{
						String warehouse = WarehouseModelManager.getInstance().getWarehouseByWarehouseId(((IdentityProduct) dataVector.get(row)).getWarehouseCode()).toString();
						list3.put(((IdentityProduct) dataVector.get(row)).getWarehouseCode(), warehouse);
						}
						} catch (Exception e) {
						list3.put(((IdentityProduct) dataVector.get(row)).getWarehouseCode(), "");
					}
					return list1.get(productCode);
				} catch (Exception e) {
					Vector rowVector = (Vector) dataVector.elementAt(row);
					return rowVector.elementAt(column);
				}

			case 3:
				try {
					return list2.get(((IdentityProduct) dataVector.get(row)).getProductCode());
				} catch (Exception e) {
					return "";
				}

			case 4:
				try {
					String quantity = ((IdentityProduct) dataVector.get(row)).getModifiedBy();
					return MyDouble.parseDouble(quantity);
				} catch (Exception e) {
					return "";
				}

			case 5:
				try {
					return ((IdentityProduct) dataVector.get(row)).getPrice();
				} catch (Exception e) {
					return "";
				}

			case 6:
				try {
					return ((IdentityProduct) dataVector.get(row)).getUnitPrice();
				} catch (Exception e) {
					return "";
				}

			case 7:
				try {
					return list3.get(((IdentityProduct) dataVector.get(row)).getWarehouseCode());
				} catch (Exception e) {
					return "";
				}

			default:
				return "";
		}
	}
}
