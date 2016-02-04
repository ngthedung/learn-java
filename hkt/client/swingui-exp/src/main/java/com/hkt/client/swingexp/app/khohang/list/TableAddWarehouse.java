package com.hkt.client.swingexp.app.khohang.list;

import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.swing.widget.BeanBindingJTable;
import com.hkt.module.warehouse.entity.Warehouse;

@SuppressWarnings("serial")
public class TableAddWarehouse extends BeanBindingJTable<Warehouse> {

	static String[] HEADERS = { "STT", "Mã", "Tên ", "Miêu tả" };
	static String[] PROPERTIES = { "id", "warehouseId", "name", "description" };
	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	
	public void setData(List<Warehouse> warehouse) {
		init(HEADERS, PROPERTIES, warehouse);
		setRowHeight(23);
		setFont(new Font("Tahoma", 0, 14));
		getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(JLabel.CENTER);
		centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		getColumnModel().getColumn(0).setMaxWidth(50);
	}
	
	public TableAddWarehouse(List<Warehouse> warehouse) {
		init(HEADERS, PROPERTIES, warehouse);
		setRowHeight(23);
		setFont(new Font("Tahoma", 0, 14));
		getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(JLabel.CENTER);
		centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
	}

	@Override
	protected Warehouse newBean() {
		return new Warehouse();
	}

	@Override
	protected boolean isBeanEditableAt(int row, int col) {
		return false;
	}

}
