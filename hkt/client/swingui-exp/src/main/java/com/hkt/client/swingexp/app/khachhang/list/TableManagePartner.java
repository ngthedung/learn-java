package com.hkt.client.swingexp.app.khachhang.list;

import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.swing.widget.BeanBindingJTable;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.module.account.entity.Account.Type;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.partner.customer.entity.Customer;

@SuppressWarnings("serial")
public class TableManagePartner extends BeanBindingJTable<Customer> {
  static String[] HEADERSS = { "STT", "Tên đối tác", "Là DN/cá nhân", "Nhóm" };
  static String[] PROPERTIESS = { "id", "name", "organizationLoginId", "loginId" };

  static String[] HEADERS = { "STT", "Tên đối tác", "Là DN/cá nhân" };
  static String[] PROPERTIES = { "id", "name", "organizationLoginId" };

  public TableManagePartner(List<Customer> customer) {
    setCustomers(customer);
  }

  public TableManagePartner(List<Customer> customer, int fillter) {
    setCustomers(customer, fillter);
  }

  public void setCustomers(List<Customer> customer) {
    init(HEADERS, PROPERTIES, customer);
    setRowHeight(23);
    setFont(new Font("Tahoma", 0, 14));
    getColumnModel().getColumn(0).setMaxWidth(50);
    getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
    ((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
  }

  public void setCustomers(List<Customer> customer, int fillter) {
    if (fillter == 1) {
      init(HEADERS, PROPERTIES, customer);

    } else {
      init(HEADERSS, PROPERTIESS, customer);
    }

    setRowHeight(23);
    setFont(new Font("Tahoma", 0, 14));
    getColumnModel().getColumn(0).setMaxWidth(80);
    getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
    ((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
  }

  protected boolean isBeanEditableAt(int row, int col) {
    return false;
  }

  protected Customer newBean() {
    return new Customer();
  }

  @Override
  public Object getValueAt(int row, int column) {
    if (column == 2) {
      try {
        if (AccountModelManager.getInstance().getAccountByLoginId(super.getValueAt(row, column).toString()).getType()
            .equals(Type.USER)) {
          return "Cá nhân";
        } else {
          return "Doanh nghiệp";
        }
      } catch (Exception e) {
        return "";
      }

    } else if (column == 3) {
      String loginId = super.getValueAt(row, column).toString();
      try {
        List<AccountMembership> lstMemberShip = AccountModelManager.getInstance().findMembershipByAccountLoginId(
            loginId);
        String groupPath = null;
        for (int i = 0; i < lstMemberShip.size(); i++) {
          groupPath = lstMemberShip.get(i).getGroupPath();
        }
        AccountGroup acc = new AccountGroup();
        acc = AccountModelManager.getInstance().getGroupByPath(groupPath);
        if (acc != null) {
          return acc.getLabel();
        } else {
          return acc.getLabel();
        }
        // return
        // AccountModelManager.getInstance().getNameByLoginId(super.getValueAt(row,
        // column).toString());
      } catch (Exception e) {

        return "";
      }

    } else
      return super.getBeanValueAt(row, column);
  }

	public static String[] getHEADERS() {
		return HEADERS;
	}
	
}
