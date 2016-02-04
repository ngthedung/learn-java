package com.hkt.client.swingexp.app.khuyenmai.list;

import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.ITableMain;
import com.hkt.client.swingexp.app.core.TableObservable;
import com.hkt.client.swingexp.app.khuyenmai.PanelGiveProduct;
import com.hkt.client.swingexp.app.khuyenmai.PanelPromotion;
import com.hkt.client.swingexp.model.PromotionModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.promotion.entity.Promotion;
import com.hkt.module.promotion.entity.PromotionDetail;
import com.hkt.module.promotion.entity.PromotionGiveaways;

@SuppressWarnings("serial")
public class TableListPromotion extends TableObservable implements ITableMain {
	private List<PromotionDetail> listPromotion;
	private List<Promotion> lstPromotionAll = new ArrayList<Promotion>();
	private TableModelAllPromotion mdTbPromotionAll;
	private List<PromotionGiveaways> listPromotionGiveaways;
	public static String permission;

	// Hàm tạo
	public TableListPromotion() {
		try {
			try {
				listPromotion = PromotionModelManager.getInstance().getPromotions();
				lstPromotionAll.addAll(listPromotion);
			} catch (Exception e) {
				e.printStackTrace();
				listPromotion = new ArrayList<PromotionDetail>();
			}

			try {
				listPromotionGiveaways = PromotionModelManager.getInstance().getPromotionGiveaways();
				
				lstPromotionAll.addAll(listPromotionGiveaways);
			} catch (Exception e) {
				e.printStackTrace();
				listPromotionGiveaways = new ArrayList<PromotionGiveaways>();
			}

		} catch (Exception e) {
			e.printStackTrace();
			lstPromotionAll = new ArrayList<Promotion>();
		}
		mdTbPromotionAll = new TableModelAllPromotion(new ArrayList<Promotion>());
		setModel(mdTbPromotionAll);


	}

	// Phương thức cho sự kiện click vào dòng trong bảng
	@Override
	public void click() {
		// if (UIConfigModelManager.getInstance().getPermission(
		// UIConfigModelManager.getInstance().getPlainText(
		// PanelPromotion.permission)) == null) {
		// return;
		// }
		Promotion promotion = (Promotion) this.getValueAt(this.getSelectedRow(), 1);
		PanelPromotion panelPromotion = null;
		try {
			panelPromotion = new PanelPromotion();
		} catch (Exception e) {

		}
		PanelGiveProduct pnGiveProduct = null;
		try {
			pnGiveProduct = new PanelGiveProduct();
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		if (promotion instanceof PromotionGiveaways) {
			PromotionGiveaways p = (PromotionGiveaways) promotion;

			pnGiveProduct.setName("KhuyenMaiTangSanPham");
			try {
				pnGiveProduct.setData(p);
				DialogMain dialog = new DialogMain(pnGiveProduct);
				dialog.setIcon("promosanpham1.png");
				dialog.setName("dlKhuyenMaiTangSanPham");
				dialog.setTitle("Khuyến Mãi Tặng Sản Phẩm");
				dialog.showButton(false);
				pnGiveProduct.setEnableCompoment(false);
				dialog.setVisible(true);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (promotion instanceof PromotionDetail) {

			PromotionDetail p = (PromotionDetail) promotion;
			panelPromotion.setName("LapKhuyenMai");
			
			try {
				panelPromotion.setData(p);
				DialogMain dialog = new DialogMain(panelPromotion);
				dialog.dispose();
				dialog.setUndecorated(true);
				dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
				dialog.setName("LapKhuyenMai");
				dialog.setIcon("promobophan1.png");
				dialog.setTitle("Lập khuyến mãi");
				dialog.setResizable(true);
				dialog.setLocationRelativeTo(null);
				dialog.showButton(false);
				dialog.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			listPromotion = new ArrayList<PromotionDetail>();
			lstPromotionAll = new ArrayList<Promotion>();
			listPromotionGiveaways = new ArrayList<PromotionGiveaways>();
			try {
				listPromotion = PromotionModelManager.getInstance().getPromotions();
				lstPromotionAll.addAll(listPromotion);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				listPromotionGiveaways = PromotionModelManager.getInstance().getPromotionGiveaways();
				
				lstPromotionAll.addAll(listPromotionGiveaways);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		change();
//
//		try {
//			promotion = PromotionModelManager.getInstance().getPromotionById(promotion.getId());
//			System.out.println(promotion.isRecycleBin());
//			if (promotion == null || promotion.isRecycleBin()) {
//				mdTbPromotionAll.removeRow(this.getSelectedRow());
//			} else {
//				mdTbPromotionAll.fireTableRowsUpdated(this.getSelectedRow(), this.getSelectedRow());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	// Lấy kích thước phần tử trong danh sách
	@Override
	public int getListSize() {
		try {
			int i = PromotionModelManager.getInstance().getPromotions().size();
			int k = PromotionModelManager.getInstance().getPromotionGiveaways().size();

			return i + k;
		} catch (Exception e) {
			return 0;
		}
		// return lstPromotionAll.size();
	}

	@Override
	public List<Integer> getColumnSum() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(7);
		list.add(8);
		return list;
	}

	// Hàm load lại dữ liệu lên bảng
	@Override
	public DefaultTableModel loadTable(int index, int pageSize) {
		try {
			mdTbPromotionAll.setData(lstPromotionAll.subList(index, pageSize), index);
		} catch (Exception ex) {
			mdTbPromotionAll= new TableModelAllPromotion(new ArrayList<Promotion>());
		}
		return mdTbPromotionAll;
	}

  @Override
  public boolean delete() {
	  Promotion promotion = (Promotion) this.getValueAt(this.getSelectedRow(), 1);
	  if (promotion instanceof PromotionDetail) {
	  if (UIConfigModelManager.getInstance().getPermission(
				PanelPromotion.permission) == Capability.ADMIN) {
		  
			try {
					for (int i = 0; i < listPromotion.size();) {
						Promotion p = listPromotion.get(i);
//						if (p instanceof PromotionDetail){
							PromotionDetail promo = PromotionModelManager.getInstance().getPromotionById(p.getId());

							if (p.isRecycleBin()) {
								p.setRecycleBin(true);
								listPromotion.remove(i);
								 
								 PromotionModelManager.getInstance().deletePromotion(promo);
							} else {
								i++;
							}	
//						} else {
//							
//						}
						
					}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			change();

		} else {
			JOptionPane
					.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			return false;
		}
	  } else if (promotion instanceof PromotionGiveaways){
		  if (UIConfigModelManager.getInstance().getPermission(
					PanelGiveProduct.permission) == Capability.ADMIN) {
			  
				try {
						for (int i = 0; i < listPromotionGiveaways.size();) {
							PromotionGiveaways promo = listPromotionGiveaways.get(i);
							if (promo.isRecycleBin()) {
								promo.setRecycleBin(true);
								listPromotionGiveaways.remove(i);
								 PromotionModelManager.getInstance().deletePromotionGiveaways(promo);
								
							} else {
								i++;
							}
						}
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				change();

			} else {
				JOptionPane
						.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
				return false;
			}
	  }
    return false;
  }
  @Override
  public boolean isDelete() {
    return true;
  }
}
