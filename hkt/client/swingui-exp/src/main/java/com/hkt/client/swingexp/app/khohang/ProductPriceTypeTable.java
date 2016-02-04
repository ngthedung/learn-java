package com.hkt.client.swingexp.app.khohang;

import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.ProductServiceClient;
import com.hkt.client.swing.widget.BeanBindingJTable;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.module.product.entity.ProductPriceType;

public class ProductPriceTypeTable extends BeanBindingJTable<ProductPriceType> {
	private ProductServiceClient clientCore = ClientContext.getInstance().getBean(ProductServiceClient.class);
	private String[] HEADERS = { "STT", "Mã", "Bảng giá", "Miêu tả" };
	private String[] PROPERTIES = { "id", "type", "name", "description" };

	public void setProductPriceTypes(List<ProductPriceType> productPriceTypes) {
		init(HEADERS, PROPERTIES, productPriceTypes);
		setRowHeight(23);
		setFont(new Font("Tahoma", 0, 14));
		getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		getColumnModel().getColumn(0).setMaxWidth(50);
	}

	public ProductPriceTypeTable(List<ProductPriceType> productPriceTypes) {
		init(HEADERS, PROPERTIES, productPriceTypes);
		setRowHeight(23);
		setFont(new Font("Tahoma", 0, 14));
		getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
	}

	protected ProductPriceType newBean() {
		return new ProductPriceType();
	}

	protected boolean isBeanEditableAt(int row, int col) {
		return false;
	}

	@Override
	public void saveIndex() {
		try {
			for (int i = 0; i < getBeans().size(); i++) {
				getBeans().get(i).setPriority(i + 1);
				clientCore.saveProductPriceType(getBeans().get(i));
			}
			DialogNotice.getInstace().setVisible(true);
		} catch (Exception e) {
		}
	}
}
