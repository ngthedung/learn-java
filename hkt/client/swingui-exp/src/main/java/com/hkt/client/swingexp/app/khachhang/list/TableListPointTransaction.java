package com.hkt.client.swingexp.app.khachhang.list;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.ITableMain;
import com.hkt.client.swingexp.app.core.TableObservable;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.khachhang.PanelPointTransaction;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.customer.entity.Point;
import com.hkt.module.partner.customer.entity.PointTransaction;

@SuppressWarnings("serial")
public class TableListPointTransaction extends TableObservable implements ITableMain{

	private List<PointTransaction> pointTransactions = new ArrayList<PointTransaction>();
	private TableModelPointTransaction modelTable;
	private List<Point> points;
	
	public TableListPointTransaction() {
		try {
			points = CustomerModelManager.getInstance().getAllPoints();
			for (Point p : points) {
				List<PointTransaction> list = CustomerModelManager
						.getInstance().findPointTransactionByPointId(p.getId());
				System.out.println(list);
				for (PointTransaction pt : list) {
					pointTransactions.add(pt);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			pointTransactions = new ArrayList<PointTransaction>();
		}
		modelTable = new TableModelPointTransaction(new ArrayList<PointTransaction>());
		setModel(modelTable);

	}

	@Override
	public void click() {
		PointTransaction pointTransaction = (PointTransaction) this.getValueAt(this.getSelectedRow(), 5);
		try {
			Point p = CustomerModelManager.getInstance().getPointById(pointTransaction.getPointId());
			PanelPointTransaction panel = new PanelPointTransaction();
			panel.setData(p, pointTransaction);
			panel.setName("DungDiemTrucTiep");
			DialogMain dialog = new DialogMain(panel);
            dialog.setTitle("Dùng điểm");
            dialog.setIcon("pointsused1.png");
            dialog.setName("dlDungDiemTrucTiep");
            dialog.showButton(false);
            dialog.setModal(true);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
		pointTransactions.clear();
		try {
			points = CustomerModelManager.getInstance().getAllPoints();
			for (Point p : points) {
				List<PointTransaction> list = CustomerModelManager
						.getInstance().findPointTransactionByPointId(p.getId());
				for (PointTransaction pt : list) {
					pointTransactions.add(pt);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			pointTransactions = new ArrayList<PointTransaction>();
		}
		change();
	}

	@Override
	public int getListSize() {
		return pointTransactions.size();
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
		try {
			modelTable.setData(pointTransactions.subList(index, pageSize), index);
		} catch (Exception ex) {
			modelTable= new TableModelPointTransaction(new ArrayList<PointTransaction>());
		}

		return modelTable;
	}

  @Override
  public boolean delete() {
	  if (UIConfigModelManager.getInstance().getPermission(
				PanelPointTransaction.permission) == Capability.ADMIN) {
			try {
				String str = "Xóa tất cả danh sách đã chọn ";
				PanelChoise panel = new PanelChoise(str + " ?");
				panel.setName("Xoa");
				DialogResto dialog = new DialogResto(panel, false, 0, 80);
				dialog.setName("dlXoa");
				dialog.setTitle("Xóa điểm dùng");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
				if (panel.isDelete()) {
					for (int i = 0; i < pointTransactions.size();) {
						PointTransaction p = pointTransactions.get(i);
						
						if (p.isRecycleBin()) {
							p.setRecycleBin(true);
							pointTransactions.remove(i);
							CustomerModelManager.getInstance().deletePointTransaction(p);
							
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
