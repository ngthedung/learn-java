package com.hkt.client.swingexp.app.nhansu.list;

import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.swing.widget.BeanBindingJTable;
import com.hkt.module.product.entity.ProductTag;

@SuppressWarnings("serial")
public class TableModelGroupEmployeeProduct extends BeanBindingJTable<ProductTag> {

	static String[] HEADERS = { "STT", "Nhóm sản phẩm", "Tên nhân viên", "Miêu tả"};
	static String[] PROPERTIES = {"id", "label", "loginId", "description" };
	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();

	public void setData(List<ProductTag> EmployeegroupProduct) {
		init(HEADERS, PROPERTIES, EmployeegroupProduct);
		setRowHeight(23);
		setFont(new Font("Tahoma", 0, 14));
		getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer())
		.setHorizontalAlignment(JLabel.CENTER);
		centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		getColumnModel().getColumn(0).setMaxWidth(50);
	}

	public TableModelGroupEmployeeProduct(List<ProductTag> EmployeegroupProduct) {
		init(HEADERS, PROPERTIES, EmployeegroupProduct);
		setRowHeight(23);
		setFont(new Font("Tahoma", 0, 14));
		getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer())
		.setHorizontalAlignment(JLabel.CENTER);
		centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
	}

	@Override
	protected ProductTag newBean() {
		return new ProductTag();
	}

	@Override
	protected boolean isBeanEditableAt(int row, int col) {
		return false;
	}

}
