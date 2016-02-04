package com.hkt.client.swingexp.app.banhang;

import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.ProductServiceClient;
import com.hkt.client.swing.widget.BeanBindingJTable;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.module.product.entity.Tax;

public class TaxTable extends BeanBindingJTable<Tax> {
	private String[]							HEADERS			= { "STT", "Mã", "Tên thuế", "Tỷ lệ", "Miêu tả" };
	private String[]							PROPERTIES	= { "id", "code", "name", "percent", "description" };
	private ProductServiceClient	clientCore	= ClientContext.getInstance().getBean(ProductServiceClient.class);

	public void setTaxs(List<Tax> taxs) {
		init(HEADERS, PROPERTIES, taxs);
		setRowHeight(23);
		setFont(new Font("Tahoma", 0, 14));
		getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		getColumnModel().getColumn(0).setMaxWidth(50);
		getColumnModel().getColumn(3).setMaxWidth(50);
	}

	public TaxTable(List<Tax> taxs) {
		init(HEADERS, PROPERTIES, taxs);
		setRowHeight(23);
		setFont(new Font("Tahoma", 0, 14));
		getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
	}

	public TaxTable() {
	}

	protected Tax newBean() {
		return new Tax();
	}

	protected boolean isBeanEditableAt(int row, int col) {
		return false;
	}

	public void saveIndex() {
		try {
			for (int i = 0; i < getBeans().size(); i++) {
				getBeans().get(i).setIndex(i);
				clientCore.saveTax((getBeans().get(i)));
			}
			DialogNotice.getInstace().setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
