package com.hkt.client.swingexp.app.hethong;

import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.LocaleServiceClient;
import com.hkt.client.swing.widget.BeanBindingJTable;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.module.config.locale.Country;

@SuppressWarnings("serial")
public class CountriesTable extends BeanBindingJTable<Country> {

	static String[] HEADERS = { "STT", "Mã", "Tên", "Quốc tịch", "Miêu tả" };
	static String[] PROPERTIES = { "priority", "code", "name", "flag", "description" };
	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	private LocaleServiceClient clientCore = ClientContext.getInstance().getBean(LocaleServiceClient.class);

	public void setCountries(List<Country> country) {
		init(HEADERS, PROPERTIES, country);
		setRowHeight(23);
		setFont(new Font("Tahoma", 0, 14));
		getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.LEFT);
		this.getColumn("STT").setCellRenderer(centerRenderer);
		getColumnModel().getColumn(0).setMaxWidth(50);
	}

	public CountriesTable(List<Country> country) {
		init(HEADERS, PROPERTIES, country);
		setRowHeight(23);
		setFont(new Font("Tahoma", 0, 14));
		getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.LEFT);
		this.getColumn("STT").setCellRenderer(centerRenderer);
	}

	public CountriesTable() {

	}

	protected Country newBean() {
		return new Country();
	}

	protected boolean isBeanEditableAt(int row, int col) {
		return false;
	}

	@Override
	public void saveIndex() {
		try {
			// for (int i = 0; i < getBeans().size(); i++) {
			// getBeans().get(i).setIndex(i+1);
			// clientCore.saveCountry( getBeans().get(i));
			// }
			for (int i = 0; i < getBeans().size(); i++) {
				getBeans().get(i).setPriority(i);
			  getBeans().get(i).setIndex(i);
				clientCore.saveCountry(getBeans().get(i));
			}
			DialogNotice.getInstace().setVisible(true);
		} catch (Exception e) {
		}
	}
}
