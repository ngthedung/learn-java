package com.hkt.client.swingexp.app.khachhang.list;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.ITableMain;
import com.hkt.client.swingexp.app.core.TableObservable;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.khachhang.PanelCreditTransaction;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.partner.customer.entity.Credit;
import com.hkt.module.partner.customer.entity.CreditTransaction;

@SuppressWarnings("serial")
public class TableListCreditTransaction extends TableObservable implements ITableMain {

	private List<CreditTransaction> creditTransactions = new ArrayList<CreditTransaction>();
	private TableModelCreditTransaction modelTable;
	private List<Credit> credits;

	public TableListCreditTransaction() {
		try {
			credits = CustomerModelManager.getInstance().getAllCredits();
			for (Credit c : credits) {
				List<CreditTransaction> list = CustomerModelManager
						.getInstance().findCreditTransactionByCreditId(
								c.getId());
				for (CreditTransaction pointTran : list) {
					creditTransactions.add(pointTran);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			creditTransactions = new ArrayList<CreditTransaction>();
		}
		modelTable = new TableModelCreditTransaction(
				new ArrayList<CreditTransaction>());
		setModel(modelTable);

		
		
	}

	@Override
	public void click() {
   // Không cho kick đúp chỉnh sửa
	}

	@Override
	public int getListSize() {
		return creditTransactions.size();
	}

	@Override
	public List<Integer> getColumnSum() {
		 List<Integer> list = new ArrayList<Integer>();
		 list.add(3);
		 return null;
	}

	@Override
	public DefaultTableModel loadTable(int index, int pageSize) {
		try {
			modelTable.setData(creditTransactions.subList(index, pageSize), index);
		} catch (Exception ex) {
			modelTable = new TableModelCreditTransaction(new ArrayList<CreditTransaction>());
		}
		return modelTable;
	}

  @Override
  public boolean delete() {
	  if (UIConfigModelManager.getInstance().getPermission(
				PanelCreditTransaction.permisson) == Capability.ADMIN) {
			try {
				String str = "Xóa tất cả danh sách đã chọn ";
				PanelChoise panel = new PanelChoise(str + " ?");
				panel.setName("Xoa");
				DialogResto dialog = new DialogResto(panel, false, 0, 80);
				dialog.setName("dlXoa");
				dialog.setTitle("Xóa trả trước");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
				if (panel.isDelete()) {
					for (int i = 0; i < creditTransactions.size();) {
						CreditTransaction cre = creditTransactions.get(i);
						
						if (cre.isRecycleBin()) {
							cre.setRecycleBin(true);
							creditTransactions.remove(i);
							CustomerModelManager.getInstance().deleteCreditTransaction(cre);
							
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
