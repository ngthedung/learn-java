package com.hkt.client.swingexp.app.khuvucbanhang.list;

/**
 * author Khanhdd
 */

import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.swing.widget.BeanBindingJTable;
import com.hkt.module.account.entity.AccountGroup;

@SuppressWarnings("serial")
public class TableListDepartmentPrintMachine extends BeanBindingJTable<AccountGroup> {

	//
	// static String[] HEADERS = { "STT", "Mã",
	// "Tên phòng ban","Phòng trực thuộc", "Miêu tả" };
	// static String[] PROPERTIES = { "id", "code", "name",
	// "subDepartment","description"};

	static String[] HEADERS = { "STT", "Mã", "Tên", "Miêu tả" };
	static String[] PROPERTIES = { "id", "name", "label", "description" };

	public void setAccountGroup(List<AccountGroup> accountGroup) {
		init(HEADERS, PROPERTIES, accountGroup);
		setRowHeight(23);
		setFont(new Font("Tahoma", 0, 14));
		getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		getColumnModel().getColumn(0).setMaxWidth(50);
	}

	public TableListDepartmentPrintMachine(List<AccountGroup> accountGroup) {
		init(HEADERS, PROPERTIES, accountGroup);
		setRowHeight(23);
		setFont(new Font("Tahoma", 0, 14));
		getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
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
