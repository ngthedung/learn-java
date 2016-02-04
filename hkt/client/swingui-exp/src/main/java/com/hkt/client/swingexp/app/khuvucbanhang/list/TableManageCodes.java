package com.hkt.client.swingexp.app.khuvucbanhang.list;

import java.awt.Font;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import com.hkt.client.swing.widget.BeanBindingJTable;
import com.hkt.module.accounting.entity.ManageCodes;
import com.hkt.module.accounting.entity.ManageCodes.ManageType;
import com.hkt.module.accounting.entity.ManageCodes.RotationType;

@SuppressWarnings("serial")
public class TableManageCodes extends BeanBindingJTable<ManageCodes> {

	static String[] HEADERS = { "STT", "Mã", "Kiểu đặt mã", "Loại quản lý",
			"Quay vòng", "Miêu tả" };
	static String[] PROPERTIES = { "id", "code", "typeCode", "manageType",
			"rotationType", "description" };
	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();

	public void setManadeCodes(List<ManageCodes> lstCodes) {
		init(HEADERS, PROPERTIES, lstCodes);
		setRowHeight(23);
		setOpaque(false);
		setFont(new Font("Tahoma", 0, 14));
		getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		getColumnModel().getColumn(0).setMaxWidth(40);

		centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(JLabel.CENTER);
	}

	public TableManageCodes(List<ManageCodes> lstCodes) {
		init(HEADERS, PROPERTIES, lstCodes);
		setRowHeight(23);
		setOpaque(false);
		setFont(new Font("Tahoma", 0, 14));
		getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		getColumnModel().getColumn(0).setMaxWidth(40);

		centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(JLabel.CENTER);
	}

	@Override
	protected ManageCodes newBean() {
		return new ManageCodes();
	}

	@Override
	protected boolean isBeanEditableAt(int row, int col) {
		return false;
	}

	@Override
	public Object getBeanValueAt(int row, int column) {
		switch (column) {

		case 0:
			return row + 1;

		case 1:
			try {
				return getBeans().get(row).getCode();
			} catch (Exception e) {
				return super.getBeanValueAt(row, column);
			}

		case 2:
			try {
				return getBeans().get(row).getTypeCode();
			} catch (Exception e) {
				return super.getBeanValueAt(row, column);
			}
		case 3:
			try {

				if (getBeans().get(row).getManageType().toString()
						.equals(ManageType.Circle.toString())) {
					
					return "Quay vòng";
				} else {
					
					return "Tăng dần đều";
				}

			} catch (Exception e) {
				e.printStackTrace();
				return super.getBeanValueAt(row, column);
				
			}
			
		case 4:
			try {
				if (getBeans().get(row).getRotationType() != null){
					if (getBeans().get(row).getRotationType().toString()
							.equals(RotationType.ByDay.toString())) {
						return "Theo ngày";
					} else if (getBeans().get(row).getRotationType().toString()
								.equals(RotationType.ByMonth.toString())) {
							return "Theo tháng";
					} else if (getBeans().get(row).getRotationType().toString()
							.equals(RotationType.ByYear.toString())){
						return "Theo năm";
					}
				}
				
				else {
					return "";
				}

			} catch (Exception e) {
				e.printStackTrace();
				return super.getBeanValueAt(row, column);
			}
			
		case 5:
			try {
				return getBeans().get(row).getDescription();
			} catch (Exception e) {
				return super.getBeanValueAt(row, column);
			}

		default:

			return "";
		}
	}
}
