package com.hkt.client.swingexp.app.khohang.list;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.WarehouseModelManager;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.config.locale.ProductUnit;
import com.hkt.module.product.entity.Product;

public class TableModelInvoiceItemExport extends DefaultTableModel {
	private String[]	header	= new String[] { "", "STT", "Mã hóa đơn", "Sản phẩm", "ĐVSP", "SL", "Đơn giá", "ĐVT", "Kho" };

	public TableModelInvoiceItemExport(List<InvoiceItem> eventorys) {
		setListData(eventorys);
	}

	public void setListData(List<InvoiceItem> eventorys) {
		for (int i = 0; i < eventorys.size(); i++) {
			InvoiceItem invoiceItem = eventorys.get(i);
			if (invoiceItem == null) {
				eventorys.remove(i);
				i--;
			}
		}
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
				return (InvoiceItem) dataVector.get(row);

			case 1:
				try {
					return row + 1;
				} catch (Exception e) {
					return 0;
				}

			case 2:
				try {
					return ((InvoiceItem) dataVector.get(row)).getInvoiceCode();
				} catch (Exception e) {
					return "";
				}

			case 3:
				try {
					String productCode = ((InvoiceItem) dataVector.get(row)).getProductCode();
					
					if (productCode != null) {
						Product product = ProductModelManager.getInstance().getProductByCode(productCode);
						return product;
					} else {
						return "";
					}
				} catch (Exception e) {
					return "";
				}

			case 4:
				try {
					String productCode = ((InvoiceItem) dataVector.get(row)).getProductCode();
					if (productCode != null) {
						String unit = ProductModelManager.getInstance().getProductByCode(productCode).getUnit();
						ProductUnit unitProduct = LocaleModelManager.getInstance().getProductUnitByCode(unit);
						return unitProduct;
					} else {
						return "";
					}
				} catch (Exception e) {
					return "";
				}

			case 5:
				try {
					double quantity = ((InvoiceItem) dataVector.get(row)).getQuantity() - ((InvoiceItem) dataVector.get(row)).getQuantityReal();
					return MyDouble.valueOf(quantity).toString();
				} catch (Exception e) {
					return "";
				}

			case 6:
				try {
					return ((InvoiceItem) dataVector.get(row)).getUnitPrice();
				} catch (Exception e) {
					return "";
				}

			case 7:
				try {
					String invoiceCode = ((InvoiceItem) dataVector.get(row)).getInvoiceCode();
					InvoiceDetail detail = AccountingModelManager.getInstance().getInvoiceDetailByCode(invoiceCode);
					return detail.getCurrencyUnit();
				} catch (Exception e) {
					return "";
				}

			case 8:
				try {
					String warehouseCode = ((InvoiceItem) dataVector.get(row)).getWarehouseCode();
					return WarehouseModelManager.getInstance().getWarehouseByWarehouseId(warehouseCode);
				} catch (Exception e) {
					return "";
				}
				
			default:
				return "";
		}
	}

}
