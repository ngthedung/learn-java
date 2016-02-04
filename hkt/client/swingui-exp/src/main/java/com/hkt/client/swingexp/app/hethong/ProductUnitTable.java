package com.hkt.client.swingexp.app.hethong;

import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.LocaleServiceClient;
import com.hkt.client.swing.widget.BeanBindingJTable;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.module.config.locale.ProductUnit;

@SuppressWarnings("serial")
public class ProductUnitTable extends BeanBindingJTable<ProductUnit> {

	static String[] HEADERS = { "STT", "Mã", "Tên", "Tỷ lệ", "Miêu tả" };
	static String[] PROPERTIES = { "priority", "code", "name", "rate", "description" };
	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();

	private LocaleServiceClient clientCore = ClientContext.getInstance().getBean(LocaleServiceClient.class);

	public void setProductUnits(List<ProductUnit> productUnits) {

		init(HEADERS, PROPERTIES, productUnits);
		setRowHeight(23);
		setFont(new Font("Tahoma", 0, 14));
		getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.LEFT);
		this.getColumn("STT").setCellRenderer(centerRenderer);
		getColumnModel().getColumn(0).setMaxWidth(50);
		getColumnModel().getColumn(3).setMaxWidth(60);
	}

	public ProductUnitTable() {
	}

	protected ProductUnit newBean() {
		return new ProductUnit();
	}

	protected boolean isBeanEditableAt(int row, int col) {
		return false;
	}

	public void saveIndex() {
		try {
			for (int i = 0; i < getBeans().size(); i++) {
				getBeans().get(i).setPriority(i);
				clientCore.saveProductUnit(getBeans().get(i));
			}
			 DialogNotice.getInstace().setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
