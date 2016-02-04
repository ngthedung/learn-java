	package com.hkt.client.swingexp.app.banhang.screen.often;
	
	import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.ITableMain;
import com.hkt.client.swingexp.app.core.TableObservable;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.module.restaurant.entity.Table;
	
	@SuppressWarnings("serial")
	public class TableListChoosetable extends TableObservable implements ITableMain {
		private List<Table> listchoosetable = new ArrayList<Table>();
		private TableModelChossetable mdTbChossetable;
		
		public TableListChoosetable() {
			try {
				List<Table> listAllTable = RestaurantModelManager.getInstance().getTables();
				if(listAllTable.size() > 0){
					for(int i = 0; i < listAllTable.size(); i++){
						if(listAllTable.get(i).getStatus() == Table.Status.TableSet){
							listchoosetable.add(listAllTable.get(i));
						}
					}
				}
				else listchoosetable = new ArrayList<Table>();
			} catch (Exception e) {
				e.printStackTrace();
				listchoosetable = new ArrayList<Table>();
			}
			mdTbChossetable = new TableModelChossetable(new ArrayList<Table>());
			setModel(mdTbChossetable);
			getColumnModel().getColumn(0).setMaxWidth(50);
		}
	
		@Override
		public void click() {
			
		}
	
		@Override
		public int getListSize() {
			return listchoosetable.size();
		}
	
		@Override
		public List<Integer> getColumnSum() {
			return null;
		}
	
		@Override
		public DefaultTableModel loadTable(int currentPage, int pageSize) {
			try {
				mdTbChossetable.setData(listchoosetable.subList(currentPage,pageSize), currentPage);
			} catch (Exception ex) {
				mdTbChossetable = new TableModelChossetable(new ArrayList<Table>());
			}
			return mdTbChossetable;
		}

    @Override
    public boolean delete() {
    	try {
			String str = "Xóa tất cả danh sách đã chọn ";
			PanelChoise panel = new PanelChoise(str + " ?");
			panel.setName("Xoa");
			DialogResto dialog = new DialogResto(panel, false, 0, 80);
			dialog.setName("dlXoa");
			dialog.setTitle("Xóa đặt bàn");
			dialog.setLocationRelativeTo(null);
			dialog.setModal(true);
			dialog.setVisible(true);
			if (panel.isDelete()) {
				for (int i = 0; i < listchoosetable.size();) {
					Table t = listchoosetable.get(i);
					
					if (t.isRecycleBin()) {
						t.setRecycleBin(true);
						listchoosetable.remove(i);
						RestaurantModelManager.getInstance().deleteTable(t);
						
					} else {
						i++;
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		change();
      return false;
    }
	
    @Override
    public boolean isDelete() {
      return true;
    }
	}
