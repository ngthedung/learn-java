package com.hkt.client.swingexp.app.khohang.list;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.WarehouseModelManager;
import com.hkt.module.accounting.entity.Invoice.ActivityType;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.config.locale.ProductUnit;
import com.hkt.module.core.entity.AbstractPersistable.State;
import com.hkt.module.product.entity.Product;
import com.hkt.module.warehouse.entity.IdentityProduct;

public class TableModelIdentityProductExport extends DefaultTableModel {
	private List<IdentityProduct>	identityProducts;
	private ActivityType					activityType;
	private String[]							header	= new String[] { "", "STT", "Sản phẩm", "ĐVSP", "Số lượng", "Đơn giá", "ĐV tiền", "Kho" };

	public TableModelIdentityProductExport(List<IdentityProduct> eventorys, List<IdentityProduct> identityProducts, ActivityType activityType) {
		this.activityType = activityType;
		setListData(eventorys, identityProducts);
	}

	public TableModelIdentityProductExport(List<IdentityProduct> eventorys) {
		this.identityProducts = eventorys;
		dataVector = convertToVector(eventorys.toArray());
	}

	public void setListData(List<IdentityProduct> eventorys, List<IdentityProduct> identityProducts) {
		this.identityProducts = eventorys;
		dataVector = convertToVector(eventorys.toArray());
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
	public Object getValueAt(int row, int column) {
		switch (column) {
			case 0:
				try {
					return (IdentityProduct) dataVector.get(row);
				} catch (Exception e) {
					e.printStackTrace();
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
					String warehouseCode = ((IdentityProduct) dataVector.get(row)).getWarehouseCode();
					if (warehouseCode == null)
						warehouseCode = "";
					double count = 0;
					for (int i = 0; i < identityProducts.size(); i++) {
						String warehouseCodeI = identityProducts.get(i).getWarehouseCode();
						if (warehouseCodeI == null)
							warehouseCodeI = "";
						if (identityProducts.get(i).getProductCode().equals(productCode) && (warehouseCodeI.equals(warehouseCode)) && !identityProducts.get(i).getPersistableState().equals(State.DELETED)) {
							count++;
						}
					}
					return count;
				} catch (Exception e) {
					e.printStackTrace();
					return "";
				}
			case 5:
				try {
					if(activityType.equals(ActivityType.Payment))
						return ((IdentityProduct) dataVector.get(row)).getPrice();
					else return ((IdentityProduct) dataVector.get(row)).getPriceExport();
				} catch (Exception e) {
					e.printStackTrace();
					return "";
				}
			case 6:
				try {
					String invoiceCode = ((IdentityProduct) dataVector.get(row)).getInvoiceCode();
					InvoiceDetail detail = AccountingModelManager.getInstance().getInvoiceDetailByCode(invoiceCode);
					return detail.getCurrencyUnit();
				} catch (Exception e) {
					return "";
				}
			case 7:
				try {
					String warehouseCode = ((IdentityProduct) dataVector.get(row)).getWarehouseCode();
					return WarehouseModelManager.getInstance().getWarehouseByWarehouseId(warehouseCode);
				} catch (Exception e) {
					return "";
				}
			default:
				return "";
		}
	}

}
