package com.hkt.client.swingexp.app.khuvucbanhang.list;

import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import com.hkt.client.swing.widget.BeanBindingJTable;
import com.hkt.client.swingexp.model.GenericOptionModelManager;
import com.hkt.module.config.generic.Option;
import com.hkt.module.config.generic.OptionGroup;
import com.hkt.module.restaurant.entity.Shift;

@SuppressWarnings("serial")
public class TableWorkingShift extends BeanBindingJTable<Shift>{
	public DateFormat datefomat = new SimpleDateFormat("HH:mm");
	static String[] HEADERS = { "STT", "Mã", "Tên ", "Cửa hàng", "Bắt đầu", "Kết thúc", "Miêu tả" };
	static String[] PROPERTIES = { "id", "code", "name", "store", "hourStart", "hourEnd", "description" };
	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	
	public void setWorkingShift(List<Shift> shift){
		init(HEADERS, PROPERTIES, shift);
		setRowHeight(23);
	    setOpaque(false);
	    setFont(new Font("Tahoma", 0, 14));
	    getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
	    getColumnModel().getColumn(0).setMaxWidth(40);
	    getColumnModel().getColumn(4).setMinWidth(70);
	    getColumnModel().getColumn(4).setMaxWidth(70);
	    getColumnModel().getColumn(5).setMinWidth(70);
	    getColumnModel().getColumn(5).setMaxWidth(70);
	    centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
	    ((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
	}
	
	public TableWorkingShift(List<Shift> shift){
		init(HEADERS, PROPERTIES, shift);
		setRowHeight(23);
	    setOpaque(false);
	    setFont(new Font("Tahoma", 0, 14));
	    getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
	    getColumnModel().getColumn(0).setMaxWidth(40);
	    getColumnModel().getColumn(4).setMinWidth(70);
	    getColumnModel().getColumn(4).setMaxWidth(70);
	    getColumnModel().getColumn(5).setMinWidth(70);
	    getColumnModel().getColumn(5).setMaxWidth(70);
	    centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
	    ((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
	}

	@Override
	protected Shift newBean() {
		return new Shift();
	}

	@Override
	protected boolean isBeanEditableAt(int row, int col) {
		return false;
	}
	
	@Override
	  public Object getBeanValueAt(int row, int column) {
		switch (column) {
		
		case 0:
			return row+1;
			
		case 1:
			try {
				return getBeans().get(row).getCode();
			} catch (Exception e) {
				return super.getBeanValueAt(row, column); 
			}
			
		case 2:
			try {
				return getBeans().get(row).getName();
			} catch (Exception e) {
				return super.getBeanValueAt(row, column); 
			}
			
		case 3:
			try {
				String opt = getBeans().get(row).getStore();
				OptionGroup optionGroup = GenericOptionModelManager.getInstance()
						.getOptionGroup("restaurant", "RestaurantService", "store");
				
				Option op = optionGroup.getOption(opt);
				return op.getLabel();
			} catch (Exception e) {
				return super.getBeanValueAt(row, column); 
				
			}	
		
		case 4:
			try {
				 return datefomat.format(getBeans().get(row).getHourStart());
			} catch (Exception e) {
				return super.getBeanValueAt(row, column); 
			}
			
		case 5:
			try {
				return datefomat.format(getBeans().get(row).getHourEnd());
			} catch (Exception e) {
				return super.getBeanValueAt(row, column); 
			}
			
		case 6:
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
