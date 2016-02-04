package com.hkt.client.swingexp.app.khuvucbanhang.list;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.GenericOptionServiceClient;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.ITableMain;
import com.hkt.client.swingexp.app.khuvucbanhang.PanelLocation;
import com.hkt.module.config.generic.Option;
import com.hkt.module.restaurant.entity.Table;

public class TableListOption extends JTable implements ITableMain{

	private List<Option> listOptions;
	private TableModelOption mdTbOption;
	private GenericOptionServiceClient clientCore = ClientContext.getInstance().getBean(GenericOptionServiceClient.class);
	
	public TableListOption(){
	  try {
	  	listOptions = clientCore.getOptionGroup("restaurant", "RestaurantService", "table-location").getOptions();
	  } catch (Exception e) {
	    e.printStackTrace();
	    listOptions = new ArrayList<Option>();
	  }
	  setModel(new TableModelLocalTable(new ArrayList<Table>()));
	}
	
	@Override
	public void click() {
		Option option = (Option)this.getValueAt(this.getSelectedRow(), 1);
		try {
			//PanelLocation pnOption = new PanelLocation("restaurant", "RestaurantService", "table-location");
			PanelLocation pnOption = new PanelLocation();
			pnOption.setName("pnOption");
			//pnOption.setData(option, getSelectedRow());
      DialogMain dialog = new DialogMain(pnOption, 220, 660);
      dialog.setName("dlOption");
      dialog.setTitle("Chi tiết khu vực");
      dialog.setVisible(true);
    } catch (Exception e) {
      e.printStackTrace();
    }
	}

	@Override
	public int getListSize() {
		return listOptions.size();
	}

	@Override
	public List<Integer> getColumnSum() {
    List<Integer> list = new ArrayList<Integer>();
    list.add(7);
    list.add(8);
    return list;
	}

	@Override
	public DefaultTableModel loadTable(int index, int pageSize) {
		try{
			mdTbOption = new TableModelOption(listOptions.subList(index, pageSize));
		} catch(Exception ex){
			mdTbOption = new TableModelOption(new ArrayList<Option>());
		}
		return mdTbOption;
	}

  @Override
  public boolean delete() {
    return false;
  }
  
  @Override
  public boolean isDelete() {
    return false;
  }
}
