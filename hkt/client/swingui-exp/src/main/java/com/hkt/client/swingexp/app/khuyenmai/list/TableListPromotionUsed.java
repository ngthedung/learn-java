package com.hkt.client.swingexp.app.khuyenmai.list;

import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.ITableMain;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.model.PromotionModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.promotion.entity.PromotionGiveUsed;
import com.hkt.module.promotion.entity.PromotionUsed;

@SuppressWarnings("serial")
public class TableListPromotionUsed extends JTable implements ITableMain {
	private List<PromotionUsed> listPromotionUsed;
	private TableModelPromotionUsed mdTbPromotionUsed;
	public static String permission;

	public TableListPromotionUsed() {
		try {
			listPromotionUsed = PromotionModelManager.getInstance().getAllPromotionUsed();
		} catch (Exception e) {
			e.printStackTrace();
			listPromotionUsed = new ArrayList<PromotionUsed>();
		}
		mdTbPromotionUsed = new TableModelPromotionUsed(new ArrayList<PromotionUsed>());
		setModel(mdTbPromotionUsed);

	}

	@Override
	public void click() {

	}

	@Override
	public int getListSize() {
		return listPromotionUsed.size();
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
		
		getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer(){

			@Override
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus, int row,
					int column) {
				JLabel label = new JLabel();
				label.setText(value.toString());
				label.setFont(new Font("Tahoma", 0, 14));
				label.setHorizontalAlignment(JLabel.RIGHT);
				if(isSelected){
					  label.setOpaque(true);
					  label.setBackground(table.getSelectionBackground());
					}
				return label;
				}
	    });
		
		getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer(){

			@Override
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus, int row,
					int column) {
				JLabel label = new JLabel();
				label.setText(value.toString());
				label.setFont(new Font("Tahoma", 0, 14));
				label.setHorizontalAlignment(JLabel.RIGHT);
				if(isSelected){
					  label.setOpaque(true);
					  label.setBackground(table.getSelectionBackground());
					}
				return label;
				}
	    });
		
		try {
			mdTbPromotionUsed.setData(listPromotionUsed.subList(index, pageSize), index);
		} catch (Exception ex) {
			mdTbPromotionUsed = new TableModelPromotionUsed(new ArrayList<PromotionUsed>());
			setModel(mdTbPromotionUsed);
			getColumnModel().getColumn(0).setMaxWidth(50);
			getColumnModel().getColumn(4).setMaxWidth(120);
			getColumnModel().getColumn(4).setMinWidth(120);
		}
		return mdTbPromotionUsed;
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
					for (int i = 0; i < listPromotionUsed.size();) {
						PromotionUsed promo = listPromotionUsed.get(i);
						
						if (promo.isRecycleBin()) {
							promo.setRecycleBin(true);
							listPromotionUsed.remove(i);
							System.out.println(listPromotionUsed+ "lst");
							PromotionModelManager.getInstance().deletePromotionUsed(promo);
							
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
