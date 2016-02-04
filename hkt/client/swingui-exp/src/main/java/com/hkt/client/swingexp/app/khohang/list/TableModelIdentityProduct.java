package com.hkt.client.swingexp.app.khohang.list;

import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.WarehouseModelManager;
import com.hkt.module.config.locale.ProductUnit;
import com.hkt.module.product.entity.Product;
import com.hkt.module.warehouse.entity.IdentityProduct;
import com.hkt.module.warehouse.entity.IdentityProduct.ImportType;
import com.hkt.module.warehouse.entity.Warehouse;

public class TableModelIdentityProduct extends DefaultTableModel {
	private List<IdentityProduct> eventorys;
	private String		warehouseCode;
	private String[]	header	= new String[] { "", "STT", "Sản phẩm", "ĐVSP", "Số lượng", "Đơn giá", "ĐVT", "Kho" };

	public TableModelIdentityProduct(List<IdentityProduct> eventorys, String warehouseCode) {
		this.warehouseCode = warehouseCode;
		this.eventorys = eventorys;
		dataVector = convertToVector(eventorys.toArray());
	}

	public void setListData(List<IdentityProduct> eventorys, String warehouseCode) {
		try {
			for(IdentityProduct i : eventorys){
				List<IdentityProduct> identityProducts = WarehouseModelManager.getInstance().getByImportTypeAndProduct(ImportType.Import, i.getProductCode(), warehouseCode);
						i.setCreatedBy(identityProducts.size() + "");
				}
		} catch(Exception e){
			
		}
		
		this.warehouseCode = warehouseCode;
		dataVector = convertToVector(eventorys.toArray());
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
	public Object getValueAt(int row, int column) {
		switch (column) {
			case 0:
				try {
					return (IdentityProduct) dataVector.get(row);
				} catch (Exception ex) {
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
					Product product = ProductModelManager.getInstance().getProductByCode(((IdentityProduct) dataVector.get(row)).getProductCode());
					return product;
				} catch (Exception e) {
					return "";
				}

			case 3:
				try {
					String productCode = ((IdentityProduct) dataVector.get(row)).getProductCode();
					String unit = ProductModelManager.getInstance().getProductByCode(productCode).getUnit();
					ProductUnit unitProduct = LocaleModelManager.getInstance().getProductUnitByCode(unit);
					return unitProduct;
				} catch (Exception e) {
					return "";
				}

			case 4:
				try {
					String productCode = ((IdentityProduct) dataVector.get(row)).getProductCode();
					List<IdentityProduct> identityProducts = WarehouseModelManager.getInstance().getByImportTypeAndProduct(ImportType.Import, productCode, warehouseCode);
					return ((IdentityProduct) dataVector.get(row)).getCreatedBy();
				} catch (Exception e) {
					return "";
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
					return ((IdentityProduct) dataVector.get(row)).getUnitPrice();
				} catch (Exception e) {
					return "";
				}

			case 7:
				try {
					Warehouse warehouse = WarehouseModelManager.getInstance().getWarehouseByWarehouseId(((IdentityProduct) dataVector.get(row)).getWarehouseCode());
					return warehouse;
				} catch (Exception e) {
					return "";
				}

			default:
				return "";
		}
	}

}
