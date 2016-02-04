package com.hkt.client.swingexp.model;

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.CustomerServiceClient;
import com.hkt.client.swingexp.app.banhang.list.TableInvoicePayment;
import com.hkt.client.swingexp.app.core.DialogList;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.partner.customer.entity.Credit;
import com.hkt.module.partner.customer.entity.CreditTransaction;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.customer.entity.Point;
import com.hkt.module.partner.customer.entity.PointConversionRule;
import com.hkt.module.partner.customer.entity.PointTransaction;
import com.hkt.module.partner.supplier.entity.Supplier;

public class CustomerModelManager {
	private static CustomerModelManager instance;

	private ClientContext clientContext = ClientContext.getInstance();
	private CustomerServiceClient clientCore = clientContext.getBean(CustomerServiceClient.class);

	private CustomerModelManager() {
	}

	static public CustomerModelManager getInstance() {
		if (instance == null) {
			instance = new CustomerModelManager();
		}
		return instance;
	}

	public Customer saveCustomer(Customer customer) throws Exception {
		return clientCore.saveCustomer(customer);
	}

	public List<Customer> findCustomerByCode(String code) {
		try {
			return clientCore.findCustomerByCode(code);
		} catch (Exception e) {
			return new ArrayList<Customer>();
		}
	}

	public List<AccountGroup> getCustomerGroup() {

		try {
			AccountGroup root = AccountModelManager.getInstance().getRootGroupDetail().getChildren().get(0);
			List<AccountGroup> list = AccountModelManager.getInstance()
			    .getGroupDetailByPath(root.getPath() + "/" + AccountModelManager.Customer).getChildren();
			Collections.sort(list, new Comparator<AccountGroup>() {
				@Override
				public int compare(AccountGroup it1, AccountGroup it2) {
					try {
						if (MyDouble.parseDouble(it1.getOwner()) > MyDouble.parseDouble(it2.getOwner())) {
							return 1;
						} else {
							if (MyDouble.parseDouble(it1.getOwner()) < MyDouble.parseDouble(it2.getOwner())) {
								return -1;
							} else {
								return 0;
							}
						}

					} catch (Exception e) {
						return 0;
					}

				}
			});
			return list;
		} catch (Exception e) {
			return new ArrayList<AccountGroup>();
		}

	}

	public boolean deleteCustomer(Customer customer) throws Exception {
		return clientCore.deleteCustomer(customer);
	}

	public List<Customer> getCustomers(boolean interactive) {
		try {
			return clientCore.searchCustomer(interactive);
		} catch (Exception e) {
			return new ArrayList<Customer>();
		}

	}

	public JButton getBtnEmployee(final Employee em) {
		JButton btnEdit = new JButton();
		btnEdit = new JButton("Giao dịch Nội bộ");
		btnEdit.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnEdit.setMargin(new Insets(0, 0, 0, 0));
		btnEdit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Calendar calendarStart = Calendar.getInstance();
					Calendar calendarEnd = Calendar.getInstance();
					calendarEnd.add(Calendar.DATE, -30);
					List<String> types = new ArrayList<String>();
					TableInvoicePayment table = new TableInvoicePayment(calendarStart.getTime(), calendarEnd.getTime(), null,
					    "VND", null, types, "Hóa đơn bán hàng");
					table.setName("tbQuanLyDonDatHang");
					table.setEmployee(em);
					DialogList dialog = new DialogList(table);
					table.loadDataByTime(calendarStart.getTime(), calendarEnd.getTime());
					dialog.setTitle("Danh sách hóa đơn");
					dialog.setIcon("invoiceds1.png");
					//dialog.setComponent4(table.getComboType(), "Loại");
					dialog.setName("dlinvoice3");
					dialog.setVisible(true);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});

		return btnEdit;
	}

	public JButton getBtnSupplier(final Supplier supplier) {
		JButton btnEdit = new JButton();
		btnEdit = new JButton("GD Nhà cung cấp");
		btnEdit.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnEdit.setMargin(new Insets(0, 0, 0, 0));
		btnEdit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Calendar calendarStart = Calendar.getInstance();
					Calendar calendarEnd = Calendar.getInstance();
					calendarEnd.add(Calendar.MONTH, 30);
					List<String> types = new ArrayList<String>();
					TableInvoicePayment table = new TableInvoicePayment(calendarStart.getTime(), calendarEnd.getTime(), null,
					    "VND", null, types, "Hóa đơn bán hàng");
					table.setSupplier(supplier);
					table.setName("tbQuanLyDonDatHang");
					table.loadDataByTime(calendarStart.getTime(), calendarEnd.getTime());
					DialogList dialog = new DialogList(table);
					dialog.setTitle("Danh sách hóa đơn");
					dialog.setIcon("invoiceds1.png");
				//	dialog.setComponent4(table.getComboType(), "Loại");
					dialog.setName("dlinvoice3");
					dialog.setVisible(true);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});

		return btnEdit;
	}

	public JButton getBtnCustomer(final Customer customer) {
		JButton btnEdit = new JButton();
		btnEdit = new JButton("GD Khách hàng");
		btnEdit.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnEdit.setMargin(new Insets(0, 0, 0, 0));
		btnEdit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Calendar calendarStart = Calendar.getInstance();
					Calendar calendarEnd = Calendar.getInstance();
					calendarEnd.add(Calendar.MONTH, 30);
					List<String> types = new ArrayList<String>();
					TableInvoicePayment table = new TableInvoicePayment(calendarStart.getTime(), calendarEnd.getTime(), null,
					    "VND", null, types, "Hóa đơn bán hàng");
					table.setCustomer(customer);
					table.setName("tbQuanLyDonDatHang");
					table.loadDataByTime(calendarStart.getTime(), calendarEnd.getTime());
					DialogList dialog = new DialogList(table);
					dialog.setTitle("Danh sách hóa đơn");
					dialog.setIcon("invoiceds1.png");
				//	dialog.setComponent4(table.getComboType(), "Loại");
					dialog.setName("dlinvoice3");
					dialog.setVisible(true);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});

		return btnEdit;
	}

	// Truyền accountGroup lấy ra được danh sách customer
	public List<Customer> findCustomersByAccountGroup(AccountGroup accountGroup) throws Exception {
		List<Customer> customers = new ArrayList<Customer>();
		List<AccountMembership> accountMembership = AccountModelManager.getInstance().findMembershipByGroupPath(
		    accountGroup.getPath());
		for (AccountMembership am : accountMembership) {
			Customer c = getBydLoginId(am.getLoginId());
			customers.add(c);
		}
		return customers;
	}

	public AccountGroup getRootGroupCustomer() {
		try {
			AccountGroup rootGroupCustomer = AccountModelManager.getInstance().getGroupByPath(
			    ManagerAuthenticate.getInstance().getOrganizationLoginId() + "/" + AccountModelManager.Customer);
			if (rootGroupCustomer == null) {
				AccountGroup rootOrganization = AccountModelManager.getInstance().getRootGroupDetail().getChildren().get(0);
				rootGroupCustomer = new AccountGroup();
				rootGroupCustomer.setLabel("Nhóm khách hàng");
				rootGroupCustomer.setName(AccountModelManager.Customer);
				rootGroupCustomer.setOwner(ManagerAuthenticate.getInstance().getOrganizationLoginId());
				rootGroupCustomer.setParent(rootOrganization);
				rootGroupCustomer.setDescription("Nút gốc nhóm khách hàng");
				rootGroupCustomer = AccountModelManager.getInstance().saveGroup(rootGroupCustomer);
			}
			return rootGroupCustomer;
		} catch (Exception ex) {
			return null;
		}
	}

	public AccountGroup getGroupCustomerOther() {
		try {
			AccountGroup otherGroupCustomer = AccountModelManager.getInstance().getGroupByPath(
			    ManagerAuthenticate.getInstance().getOrganizationLoginId() + "/" + AccountModelManager.Customer
			        + "/groupCustomer-other");
			if (otherGroupCustomer == null) {
				otherGroupCustomer = new AccountGroup();
				otherGroupCustomer.setLabel("Nhóm khác");
				otherGroupCustomer.setName("groupCustomer-other");
				otherGroupCustomer.setOwner(ManagerAuthenticate.getInstance().getOrganizationLoginId());
				otherGroupCustomer.setParent(getRootGroupCustomer());
				otherGroupCustomer.setDescription("Nhóm khách hàng khác");
				otherGroupCustomer = AccountModelManager.getInstance().saveGroup(otherGroupCustomer);
			}
			return otherGroupCustomer;
		} catch (Exception ex) {
			return null;
		}
	}

	public Customer getBydLoginId(String loginId) throws Exception {
		return clientCore.getBydLoginId(loginId, ManagerAuthenticate.getInstance().getOrganizationLoginId());
	}

	public Customer getCustomerByCode(String code) {
		try {
			return clientCore.getCustomerByCode(code);
		} catch (Exception e) {
			return null;
		}

	}

	public List<PointConversionRule> getPointConversionRules() throws Exception {
		return clientCore.getPointConversionRules();
	}

	public PointConversionRule savePointConversionRatio(PointConversionRule pointConversionRule) throws Exception {
		return clientCore.savePointConversionRatio(pointConversionRule);
	}

	public boolean deletePointConversionRule(PointConversionRule pointConversionRule) throws Exception {
		return clientCore.deletePointConversionRule(pointConversionRule);
	}

	public Point savePoint(Point point) throws Exception {
		return clientCore.savePoint(point);
	}

	public List<Point> getAllPoints() throws Exception {
		return clientCore.getAllPoints();
	}

	public Point getPointById(long id) throws Exception {
		return clientCore.getPointById(id);
	}

	public Point getPointByCustomerId(long customerId) {
		try {
			return clientCore.getPointByCustomerId(customerId);
		} catch (Exception e) {
			return null;
		}

	}

	public PointTransaction savePointTransaction(PointTransaction pointTransaction) throws Exception {
		return clientCore.savePointTransaction(pointTransaction);
	}

	public boolean deletePointTransaction(PointTransaction pointTransaction) throws Exception {
		return clientCore.deletePointTransaction(pointTransaction);
	}

	public List<PointTransaction> findPointTransactionByPointId(long pointId) throws Exception {
		return clientCore.findPointTransactionByPointId(pointId);
	}

	public PointTransaction getByInvoiceId(long invoiceId) {
		try {
			return clientCore.getByInvoiceId(invoiceId);
		} catch (Exception e) {
			return null;
		}
	}

	public PointConversionRule getConversionRulePointToMoney(double pointToTrigger) throws Exception {
		return clientCore.getConversionRulePointToMoney(pointToTrigger);
	}

	public PointConversionRule getConversionRuleMoneyToPoint() {
		try {
			return clientCore.getConversionRuleMoneyToPoint();
    } catch (Exception e) {
	    return null;
    }
		
	}

	public Credit getCreditByCustomerId(long customerId) throws Exception {
		Credit credit = clientCore.getCreditByCustomerId(customerId);
		return credit;
	}

	public Credit saveCredit(Credit credit) throws Exception {
		return clientCore.saveCredit(credit);
	}

	public List<Credit> getAllCredits() throws Exception {
		return clientCore.getAllCredits();
	}

	public Credit getCreditById(long id) throws Exception {
		return clientCore.getCreditById(id);
	}

	public CreditTransaction saveCreditTransaction(CreditTransaction creditTransaction) throws Exception {
		return clientCore.saveCreditTransaction(creditTransaction);
	}

	public boolean deleteCreditTransaction(CreditTransaction creditTransaction) throws Exception {
		return clientCore.deleteCreditTransaction(creditTransaction);
	}

	public List<CreditTransaction> findCreditTransactionByCreditId(long creditId) throws Exception {
		return clientCore.findCreditTransactionByCreditId(creditId);
	}

	public CreditTransaction getCreditTransactionByInvoiceId(long invoiceId) {
		try {
			return clientCore.getCreditTransactionByInvoiceId(invoiceId);
		} catch (Exception e) {
			return null;
		}
	}

	public PointConversionRule getConversionRuleById(long id) {
		try {
			return clientCore.getConversionRuleById(id);
		} catch (Exception e) {
			return null;
		}
	}

	public void deleteTest(String test) throws Exception {
		clientCore.deleteTest(test);
	}

}
