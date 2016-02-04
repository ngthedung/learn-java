package com.hkt.client.swingexp.app.khuyenmai.list;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.banhang.screen.often.TableModelViewPromotion;
import com.hkt.client.swingexp.app.core.ITableMain;
import com.hkt.client.swingexp.model.PromotionModelManager;
import com.hkt.module.promotion.entity.PromotionDetail;

@SuppressWarnings("serial")
public class TableListViewPromotion extends JTable implements ITableMain{

	private List<PromotionDetail> listPromotion;
	private TableModelViewPromotion mdTbViewPromotion;
	
	public TableListViewPromotion(){
	  try {
	  	listPromotion = PromotionModelManager.getInstance().getPromotions();
	  } catch (Exception e) {
	    e.printStackTrace();
	    listPromotion = new ArrayList<PromotionDetail>();
	  }
	  mdTbViewPromotion = new TableModelViewPromotion(new ArrayList<PromotionDetail>());

	}

	@Override
	public void click() {
		
	}

	@Override
	public int getListSize() {
		return listPromotion.size();
	}

	@Override
	public List<Integer> getColumnSum() {
		return null;
	}

@Override
public DefaultTableModel loadTable(int index, int pageSize) {
	try{
		mdTbViewPromotion = new TableModelViewPromotion(listPromotion.subList(index, pageSize));
	} catch(Exception ex){
		mdTbViewPromotion = new TableModelViewPromotion(new ArrayList<PromotionDetail>());
	}
	setModel(mdTbViewPromotion);
	getColumnModel().getColumn(0).setMaxWidth(50);
	return mdTbViewPromotion;
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



