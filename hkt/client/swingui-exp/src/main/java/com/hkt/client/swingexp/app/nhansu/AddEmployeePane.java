package com.hkt.client.swingexp.app.nhansu;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.khachhang.BasicInformation;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.Contact;
import com.hkt.module.account.entity.Account.Type;
import com.hkt.module.hr.entity.Employee;
import com.hkt.util.text.StringUtil;

public class AddEmployeePane extends JPanel implements IDialogResto {

	private JComboBox				cbOrg, cbAccount;
	private List<Employee>	employees			= new ArrayList<Employee>();
	private List<Account>		accounts;
	private List<Account>		accountActive	= new ArrayList<Account>();
	private MyJDateChooser	dcStartDate, dcEndDate;

	public AddEmployeePane() {
		// Lấy ra tất cả account có type = USE (cá nhân)
		try {
			accounts = AccountModelManager.getInstance().findAccountByType(Type.USER);
		} catch (Exception e1) {
			e1.printStackTrace();
			accounts = new ArrayList<Account>();
		}

		// Lấy ra danh sách tất cả nhân viên
		try {
			employees = HRModelManager.getInstance().getEmployees();
		} catch (Exception e2) {
			e2.printStackTrace();
			employees = new ArrayList<Employee>();
		}

		// Kiểm tra hiển thị những account là đối tác và chưa là nhân viên hiển thị
		// trong comboBox
		if (employees != null) {
			if (accounts != null) {
				for (int i = 0; i < accounts.size(); i++) {
					Account account = accounts.get(i);
					boolean flag = true;
					for (int j = 0; j < employees.size(); j++) {
						Employee employee = employees.get(j);
						if (account.getLoginId().equals(employee.getLoginId())) {
							flag = false;
							break;
						}
					}
					if(account.getLoginId().equals("admin")){
					  flag = false;
					}
					if (flag) {
						accountActive.add(account);
					}
				}
			}
		} else {
			accountActive.addAll(accounts);
		}
		cbAccount = new JComboBox(accountActive.toArray());

		// ComboBox doanh nghiệp
		String loginOrg = (AccountModelManager.getInstance().getNameByLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId()));
		List<String> accOrg = new ArrayList<String>();
		accOrg.add(loginOrg);
		cbOrg = new JComboBox(accOrg.toArray());
		cbOrg.setEnabled(false);

		dcStartDate = new MyJDateChooser();
		dcEndDate = new MyJDateChooser();
		dcEndDate.setDate(null);

		MyGroupLayout groupLayout = new MyGroupLayout(this);
		groupLayout.add(0, 0, new JLabel("Tài khoản"));
		groupLayout.add(0, 1, cbAccount);
		groupLayout.add(1, 0, new JLabel("Công ty"));
		groupLayout.add(1, 1, cbOrg);
		groupLayout.add(2, 0, new JLabel("Bắt đầu"));
		groupLayout.add(2, 1, dcStartDate);
		groupLayout.add(3, 0, new JLabel("Kết thúc"));
		groupLayout.add(3, 1, dcEndDate);
		groupLayout.updateGui();
	}

	private Employee setData(Account account) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Employee empConfig = new Employee();
		empConfig.setLoginId(new Date().getTime() + "");
		empConfig.setCode(new Date().getTime() + "");
		empConfig.setStartDate(dcStartDate.getDate());
		empConfig.setLeaveDate(dcEndDate.getDate());
		if(account.getContacts() != null && account.getContacts().size() > 0)
			empConfig.setMobile(StringUtil.joinStringArray(account.getContacts().get(0).getMobile(), ","));
		empConfig.setName(account.getProfiles().getBasic().get(BasicInformation.LAST_NAME).toString());
		empConfig.setBirthDay((Date) account.getProfiles().getBasic().get(BasicInformation.BIRTHDAY));
		try {
			empConfig.setStartDate(new Date());
		} catch (Exception e) {
		}
		
		return empConfig;
	}

	@Override
	public void Save() throws Exception {
		try {
			if(cbAccount.getSelectedItem() != null){
				Employee empConfig = setData((Account)cbAccount.getSelectedItem());
				EmployeeIsUser screenEmployee = new EmployeeIsUser(empConfig, true);
				screenEmployee.setName("EmployeeIsUser");
				screenEmployee.setPreferredSize(new Dimension(100, 100));
				DialogMain dialog = new DialogMain(screenEmployee);
				dialog.setName("dlEmployeeIsUser");
				dialog.setTitle("Thêm nhân sự");
				dialog.setModal(true);
				dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			((JDialog)this.getRootPane().getParent()).dispose();
		}
	}
}
