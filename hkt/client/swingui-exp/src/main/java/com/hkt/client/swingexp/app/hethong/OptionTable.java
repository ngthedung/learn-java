package com.hkt.client.swingexp.app.hethong;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.GenericOptionServiceClient;
import com.hkt.client.swing.widget.BeanBindingJTable;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.module.config.generic.Option;
import com.hkt.module.config.generic.OptionGroup;

@SuppressWarnings("serial")
public class OptionTable extends BeanBindingJTable<Option> {

	static String[] HEADERS = { "STT", "Mã", "Tên", "Miêu tả" };
	static String[] PROPERTIES = { "priority", "code", "label", "description" };
	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	private OptionGroup optionGroup;
	private GenericOptionServiceClient clientCore = ClientContext.getInstance().getBean(GenericOptionServiceClient.class);

	public void setOptions(OptionGroup optionGroup) {
		this.optionGroup = optionGroup;
		init(HEADERS, PROPERTIES, this.optionGroup.getOptions());
		setRowHeight(23);
		setFont(new Font("Tahoma", 0, 14));
		getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.LEFT);
		 this.getColumn("STT").setCellRenderer(centerRenderer);
		 getColumnModel().getColumn(0).setMaxWidth(50);
	}

	public OptionTable() {

	}

	protected Option newBean() {
		return new Option();
	}

	protected boolean isBeanEditableAt(int row, int col) {
		return false;
	}

	@Override
	public void saveIndex() {
		try {
			int i = 1;
			for (Option option : getBeans()) {
				optionGroup.getOption(option.getCode()).setPriority(i);
				i++;
			}
			clientCore.saveOptionGroup(optionGroup);
			DialogNotice.getInstace().setVisible(true);
		} catch (Exception e) {
		}
	}

}
