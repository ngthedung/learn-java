package com.hkt.client.swingexp.app.khuyenmai.list;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.PromotionServiceClient;
import com.hkt.client.swingexp.app.core.ITableMain;
import com.hkt.module.promotion.entity.CouponUsed;

@SuppressWarnings("serial")
public class TableListCouponUsed extends JTable implements ITableMain {

	private List<CouponUsed> listCouponUsed;
	private TableModelCouponUsed mdTbPromotionUsed;
	private ClientContext clientContext = ClientContext.getInstance();
	private PromotionServiceClient client = clientContext.getBean(PromotionServiceClient.class);
	private int index, size;

	public TableListCouponUsed() {
		try {
			listCouponUsed = client.getCouponUsed();
		} catch (Exception e) {
			e.printStackTrace();
			listCouponUsed = new ArrayList<CouponUsed>();
		}
		mdTbPromotionUsed = new TableModelCouponUsed(new ArrayList<CouponUsed>());
		setModel(mdTbPromotionUsed);

	}

	@Override
	public void click() {
	}

	@Override
	public int getListSize() {
		return listCouponUsed.size();
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
		this.index = index;
		this.size = pageSize;
		try {
			mdTbPromotionUsed.setData(listCouponUsed.subList(index, pageSize), index);
		} catch (Exception e) {
			mdTbPromotionUsed = new TableModelCouponUsed(new ArrayList<CouponUsed>());
		}
		return mdTbPromotionUsed;

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
