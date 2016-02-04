package com.hkt.client.swingexp.app.khuyenmai.list;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.PromotionServiceClient;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.ITableMain;
import com.hkt.client.swingexp.app.core.TableObservable;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.khuyenmai.DialogShowVoucher;
import com.hkt.client.swingexp.model.PromotionModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.promotion.entity.Menu;
import com.hkt.module.promotion.entity.Menu.Type;

@SuppressWarnings("serial")
public class TableListVoucher extends TableObservable implements ITableMain {
	private List<Menu> listMenu;
	private TableModelMenu mdTbMenu;
	private ClientContext clientContext = ClientContext.getInstance();
	private PromotionServiceClient client = clientContext.getBean(PromotionServiceClient.class);

	public TableListVoucher() {
		try {
			listMenu = client.getMenuByType(Type.Voucher);
		} catch (Exception e) {
			e.printStackTrace();
			listMenu = new ArrayList<Menu>();
		}
		mdTbMenu = new TableModelMenu(new ArrayList<Menu>());
		setModel(mdTbMenu);
	}

	@Override
	public void click() {
		Menu menu = (Menu) this.getValueAt(this.getSelectedRow(), 2);
		try {
			DialogShowVoucher dialogShowVoucher = new DialogShowVoucher(menu);
			dialogShowVoucher.setName("pnVoucher");
			dialogShowVoucher.setSize(100, 100);
			dialogShowVoucher.setPreferredSize(new Dimension(100, 100));
			DialogMain dialog = new DialogMain(dialogShowVoucher, true);
			dialog.setName("dlVoucher");
			dialog.setResizable(false);
			dialog.setTitle("Thiết lập voucher");
			dialog.setIcon("voucher1.png");
			dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
			dialog.setLocationRelativeTo(null);
			dialog.showButton(false);
			dialog.setVisible(true);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		try {
			listMenu = client.getMenuByType(Type.Voucher);
		} catch (Exception e) {
			e.printStackTrace();
			listMenu = new ArrayList<Menu>();
		}
		try {
			menu = PromotionModelManager.getInstance().getMenuByCode(menu.getCode());
			if (menu == null || menu.isRecycleBin()) {
				mdTbMenu.removeRow(this.getSelectedRow());
			} else {
				mdTbMenu.fireTableRowsUpdated(this.getSelectedRow(), this.getSelectedRow());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getListSize() {
		return listMenu.size();
	}

	@Override
	public List<Integer> getColumnSum() {
	  List<Integer> list = new ArrayList<Integer>();
    list.add(4);
    return list;
	}

	@Override
	public DefaultTableModel loadTable(int index, int pageSize) {
		getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer(){

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
			mdTbMenu.setData(listMenu.subList(index, pageSize), index);
		} catch (Exception ex) {
			mdTbMenu = new TableModelMenu(new ArrayList<Menu>());
		}

		return mdTbMenu;
	}

  @Override
  public boolean delete() {
	  if (UIConfigModelManager.getInstance().getPermission(
				DialogShowVoucher.permission) == Capability.ADMIN) {
			try {
				String str = "Xóa tất cả danh sách đã chọn ";
				PanelChoise panel = new PanelChoise(str + " ?");
				panel.setName("Xoa");
				DialogResto dialog = new DialogResto(panel, false, 0, 80);
				dialog.setName("dlXoa");
				dialog.setTitle("Xóa voucher");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
				if (panel.isDelete()) {
					for (int i = 0; i < listMenu.size();) {
						Menu menu = listMenu.get(i);
						
						if (menu.isRecycleBin()) {
							menu.setRecycleBin(true);
							listMenu.remove(i);
							PromotionModelManager.getInstance().deleteMenu(menu);
							
						} else {
							i++;
						}
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
    return false;
  }
  @Override
  public boolean isDelete() {
    return true;
  }
}
