package com.hkt.client.swingexp.app.banhang;

import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.AccountingServiceClient;
import com.hkt.client.swing.widget.BeanBindingJTable;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.module.accounting.entity.Bank;

public class BankTable  extends BeanBindingJTable<Bank> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[]							HEADERS			= { "STT", "Mã", "Chỉ tiêu", "Miêu tả" };
	private String[]							PROPERTIES	= { "id", "code", "name", "description" };
	private AccountingServiceClient	clientCore	= ClientContext.getInstance().getBean(AccountingServiceClient.class);

	public void setBanks(List<Bank> banks) {
		init(HEADERS, PROPERTIES, banks);
		setRowHeight(23);
		setFont(new Font("Tahoma", 0, 14));
		getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		getColumnModel().getColumn(0).setMaxWidth(50);
		getColumnModel().getColumn(1).setMaxWidth(70);
	}

	public BankTable(List<Bank> banks) {
		init(HEADERS, PROPERTIES, banks);
		setRowHeight(23);
		setFont(new Font("Tahoma", 0, 14));
		getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
	}

	public BankTable() {
	}

	protected Bank newBean() {
		return new Bank();
	}

	protected boolean isBeanEditableAt(int row, int col) {
		return false;
	}

	public void saveIndex() {
		try {
			for (int i = 0; i < getBeans().size(); i++) {
				getBeans().get(i).setIndex(i);
				clientCore.saveBank((getBeans().get(i)));
			}
			DialogNotice.getInstace().setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}