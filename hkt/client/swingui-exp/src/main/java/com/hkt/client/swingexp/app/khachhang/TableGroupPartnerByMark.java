package com.hkt.client.swingexp.app.khachhang;

import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.swing.widget.BeanBindingJTable;
import com.hkt.module.account.entity.AccountGroup;

@SuppressWarnings("serial")
public class TableGroupPartnerByMark extends BeanBindingJTable<AccountGroup> {

	static String[] HEADERS = { "STT", "Nhóm khách hàng", "Mức điểm",
			"Miêu tả" };
	static String[] PROPERTIES = { "id", "label", "owner", "description" };
	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();

	public void setAccountGroups(List<AccountGroup> accountGroups) {
		init(HEADERS, PROPERTIES, accountGroups);
		setRowHeight(23);
		setFont(new Font("Tahoma", 0, 14));
		getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer())
		.setHorizontalAlignment(JLabel.CENTER);
		centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		getColumnModel().getColumn(0).setMaxWidth(50);
		getColumnModel().getColumn(2).setMaxWidth(80);
	}

	public TableGroupPartnerByMark(List<AccountGroup> accountGroups) {
		init(HEADERS, PROPERTIES, accountGroups);
		setRowHeight(23);
		setFont(new Font("Tahoma", 0, 14));
		getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer())
		.setHorizontalAlignment(JLabel.CENTER);
		centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
	}

	@Override
	protected AccountGroup newBean() {
		return new AccountGroup();
	}

	@Override
	protected boolean isBeanEditableAt(int row, int col) {
		return false;
	}

}
