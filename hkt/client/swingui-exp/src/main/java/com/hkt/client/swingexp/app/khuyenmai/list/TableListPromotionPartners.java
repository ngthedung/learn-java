package com.hkt.client.swingexp.app.khuyenmai.list;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.ITableMain;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.model.PromotionModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.promotion.entity.PromotionGiveUsed;

@SuppressWarnings("serial")
public class TableListPromotionPartners extends JTable implements ITableMain {

	List<PromotionGiveUsed> listpromotionGiveUsed;
	private TableModelPromotionPartners mdTbpromotionGiveUsed;
	public static String permission;

	public TableListPromotionPartners() {
		try {
			// Lấy danh sach Khuyến mại SP cho vao listpromotionGiveUsed
			listpromotionGiveUsed = PromotionModelManager.getInstance().getPromotionGiveUseds();

		} catch (Exception e) {
			e.printStackTrace();
			listpromotionGiveUsed = new ArrayList<PromotionGiveUsed>();

		}
		mdTbpromotionGiveUsed = new TableModelPromotionPartners(listpromotionGiveUsed);
		setModel(mdTbpromotionGiveUsed);

	}

	@Override
	public void click() {

	}

	@Override
	public int getListSize() {
		return listpromotionGiveUsed.size();
	}

	@Override
	public List<Integer> getColumnSum() {
		return null;
	}

	@Override
	public DefaultTableModel loadTable(int index, int pageSize) {
		try {
			mdTbpromotionGiveUsed.setData(listpromotionGiveUsed.subList(index, pageSize), index);

		} catch (Exception e) {
			mdTbpromotionGiveUsed = new TableModelPromotionPartners(new ArrayList<PromotionGiveUsed>());
		}
		return mdTbpromotionGiveUsed;
	}

  @Override
  public boolean delete() {
	  if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.ADMIN) {
			try {
				String str = "Xóa tất cả danh sách đã chọn ";
				PanelChoise panel = new PanelChoise(str + " ?");
				panel.setName("Xoa");
				DialogResto dialog = new DialogResto(panel, false, 0, 80);
				dialog.setName("dlXoa");
				dialog.setTitle("Xóa khuyến mại");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
				if (panel.isDelete()) {
					for (int i = 0; i < listpromotionGiveUsed.size();) {
						PromotionGiveUsed promo = listpromotionGiveUsed.get(i);
						
						if (promo.isRecycleBin()) {
							promo.setRecycleBin(true);
							listpromotionGiveUsed.remove(i);
							PromotionModelManager.getInstance().deletePromotionGiveUsed(promo);
							
						} else {
							i++;
						}
					}
				}

			} catch (Exception ex) {
			}

		} else {
			JOptionPane
					.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			return false;
		}
    return false;
  }
  @Override
  public boolean isDelete() {
    return true;
  }
}
