package com.hkt.client.swingexp.app.banhang.list;

import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.ProductServiceClient;
import com.hkt.client.swing.widget.BeanBindingJTable;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.module.product.entity.ProductPrice;
import com.hkt.module.product.entity.ProductPrice;

public class KaraTable extends BeanBindingJTable<ProductPrice> {
	private String[]							HEADERS			= { "STT", "Mã", "Tên thuế", "Tỷ lệ", "Miêu tả" };
	private String[]							PROPERTIES	= { "id", "code", "name", "percent", "description" };
	private ProductServiceClient	clientCore	= ClientContext.getInstance().getBean(ProductServiceClient.class);

	public void setProductPrices(List<ProductPrice> ProductPrices) {
		init(HEADERS, PROPERTIES, ProductPrices);
		setRowHeight(23);
		setFont(new Font("Tahoma", 0, 14));
		getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		getColumnModel().getColumn(0).setMaxWidth(50);
		getColumnModel().getColumn(3).setMaxWidth(50);
	}

	public KaraTable(List<ProductPrice> ProductPrices) {
		init(HEADERS, PROPERTIES, ProductPrices);
		setRowHeight(23);
		setFont(new Font("Tahoma", 0, 14));
		getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
	}

	public KaraTable() {
	}

	protected ProductPrice newBean() {
		return new ProductPrice();
	}

	protected boolean isBeanEditableAt(int row, int col) {
		return false;
	}
}
