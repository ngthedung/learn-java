package com.hkt.client.swingexp.app.banhang.list;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.banhang.screen.market.DialogSuperMarket;
import com.hkt.client.swingexp.app.banhang.screen.often.DialogConfig;
import com.hkt.client.swingexp.app.banhang.screen.often.ScreenOften;
import com.hkt.client.swingexp.app.banhang.screen.pos.DialogPhieuDatMuaHangPos;
import com.hkt.client.swingexp.app.banhang.screen.pos.DialogScreenOftenPos;
import com.hkt.client.swingexp.app.banhang.screen.pos.DialogSuperMarketPos;
import com.hkt.client.swingexp.app.core.DialogContain;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.ITableMainExt;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.core.TableObservable;
import com.hkt.client.swingexp.app.hethong.PanelOption;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.thuchi.PanelPaymentReceipt;
import com.hkt.client.swingexp.app.thuchi.PanelPhieuDatMuaHang;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.GenericOptionModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.client.swingexp.model.WarehouseModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.accounting.entity.Invoice;
import com.hkt.module.accounting.entity.Invoice.ActivityType;
import com.hkt.module.accounting.entity.Invoice.Status;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.config.generic.Option;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.ReportTable;
import com.hkt.module.core.entity.SQLSelectQuery;
import com.hkt.module.core.entity.FilterQuery.Operator;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.warehouse.entity.IdentityProduct;
import com.hkt.module.warehouse.entity.WarehouseInventory;

public class TableInvoicePaymentAffter extends TableObservable implements ITableMainExt {
	private TableModelPayment modelThuChi;
	private Date start, end;
	private Employee employee;
	private String currency;
	private ActivityType activityType;
	private List<Map<String, Object>> records = new ArrayList<Map<String, Object>>();
	private String type;

	public TableInvoicePaymentAffter(Date start, Date end, Employee employee, String currency, ActivityType activityType) {
		this.start = start;
		this.end = end;
		this.employee = employee;
		this.currency = currency;
		this.activityType = activityType;
		modelThuChi = new TableModelPayment();
		setModel(modelThuChi);
	}

	@Override
	public void click() {
		try {
			viewSalse();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JComboBox getComboType() {
		JComboBox cbo = new JComboBox();
		cbo.setFont(new Font("Tahoma", 0, 14));
		List<Option> options = new ArrayList<Option>();
		try {
			options = GenericOptionModelManager.getInstance()
			    .getOptionGroup("accounting", "AccountingService", "type_invoice").getOptions();
		} catch (Exception e) {
			e.printStackTrace();
		}
		options.add(0, null);
		cbo.setModel(new DefaultComboBoxModel(options.toArray()));
		cbo.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("unused")
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {

					try {
						PanelOption pnOption = new PanelOption("accounting", "AccountingService", "type_invoice");
						DialogMain dialog = new DialogMain(new PanelOption("accounting", "AccountingService", "type_invoice"));
						dialog.setTitle("Loại hóa đơn");
						dialog.setIcon("invoice1.png");
						dialog.setModal(true);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		cbo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				JComboBox cbo = (JComboBox) e.getSource();

				if (cbo.getSelectedItem() != null) {
					type = ((Option) cbo.getSelectedItem()).getCode();
				} else {
					type = "";
				}
				loadDataByTime(start, end);
			}
		});
		boolean a = false;
		for (int i = 1; i < options.size(); i++) {
			if (options.get(i).getLabel().equals(type)) {
				cbo.setSelectedIndex(i);
				a = true;
				break;
			}
		}
		if (!a) {
			loadDataByTime(start, end);
		}
		return cbo;
	}

	private void viewSalse() throws Exception {
		Map<String, Object> record = records.get(Integer.parseInt(this.getValueAt(getSelectedRow(), 0).toString())-1);
		String code = record.get("id").toString();
		InvoiceDetail invoiceDetail = AccountingModelManager.getInstance().getInvoiceDetail(Long.parseLong(code));
		if (invoiceDetail.getInvoiceCode().startsWith("TC")) {
			PanelPaymentReceipt panel = new PanelPaymentReceipt(invoiceDetail.getActivityType());
			panel.setInvoice(invoiceDetail);
			DialogMain dialog = new DialogMain(panel);
			dialog.getBtnExt().setVisible(true);
			dialog.getBtnExt().addActionListener(panel.getActionListener());
			dialog.showButton(false);
			dialog.setIcon("invoice1.png");
			dialog.setName("dlPhieuThuTien");
			dialog.setLocationRelativeTo(null);
			if (invoiceDetail.getActivityType() == ActivityType.Receipt) {
				dialog.setTitle("Phiếu thu tiền");
			} else {
				dialog.setTitle("Phiếu chi tiền");
			}
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
		} else {
			if (invoiceDetail.getType().equals(AccountingModelManager.typeBanHang)) {
				System.out.println("??????????");
				//InvoiceDetail invoiceDetail = AccountingModelManager.getInstance().getInvoiceDetail(invoice.getId());
				try {
					Profile profile = AccountModelManager.getInstance().getProfileConfigAdmin();
					if (profile.get(DialogConfig.Maket).toString().equals("true")
					    || profile.get(DialogConfig.Shop).toString().equals("true")) {
						if (profile.get(DialogConfig.Maket).toString().equals("true")) {
							if (profile.get(DialogConfig.GDPos).toString().equals("true")) {
								DialogSuperMarketPos marketPos = DialogSuperMarketPos.getInstance();
								marketPos.setInvoiceDetail(invoiceDetail);
								marketPos.setVisible(true);
							} else {
								DialogSuperMarket dialogSuperMarket = DialogSuperMarket.getInstance();
								dialogSuperMarket.setInvoiceDetail(invoiceDetail);
								dialogSuperMarket.setVisible(true);
							}
						} else {
							DialogScreenOftenPos dialog = DialogScreenOftenPos.getInstance();
							if (!DialogScreenOftenPos.isFlagScreenOften()) {
								DialogScreenOftenPos.setFlagScreenOften(true);
								dialog.getPanelTop2().add(dialog.getPanelProductTagRoot(), 1);
								dialog.getScrollPane_Product().setViewportView(dialog.getPanelProducts());
							}
							dialog.setInvoiceDetail(invoiceDetail);
							dialog.setVisible(true);
						}
					} else {
						if (profile.get(DialogConfig.GDPos).toString().equals("true")) {
							DialogScreenOftenPos dialog = DialogScreenOftenPos.getInstance();
							if (!DialogScreenOftenPos.isFlagScreenOften()) {
								DialogScreenOftenPos.setFlagScreenOften(true);
								dialog.getPanelTop2().add(dialog.getPanelProductTagRoot(), 1);
								dialog.getScrollPane_Product().setViewportView(dialog.getPanelProducts());
							}
							dialog.setInvoiceDetail(invoiceDetail);
							dialog.setVisible(true);
						} else {
							ScreenOften dialog = ScreenOften.getInstance();
							dialog.setInvoiceDetail(invoiceDetail);
							dialog.setVisible(true);
						}
					}
				} catch (Exception e2) {
					ScreenOften dialog = ScreenOften.getInstance();
					dialog.setInvoiceDetail(invoiceDetail);
					dialog.setVisible(true);
				}

			} else {
		//		InvoiceDetail invoiceDetail = AccountingModelManager.getInstance().getInvoiceDetail(invoiceDetail.getId());
				int statusInvoice = 0;
				try {
					List<IdentityProduct> identityProducts = WarehouseModelManager.getInstance().getByInvoiceCode(
					    invoiceDetail.getInvoiceCode());
					if (identityProducts.size() == 0 && invoiceDetail.getStatus() == Status.PostPaid) {
						statusInvoice = PanelPhieuDatMuaHang.UNPAID_AND_NOTIMPORT;
					}
					if (identityProducts.size() == 0 && invoiceDetail.getStatus() == Status.Paid) {
						statusInvoice = PanelPhieuDatMuaHang.PAID_AND_NOTIMPORT;
					}
					if (identityProducts.size() > 0 && invoiceDetail.getStatus() == Status.PostPaid) {
						statusInvoice = PanelPhieuDatMuaHang.UNPAID;
					}
					if (identityProducts.size() > 0 && invoiceDetail.getStatus() == Status.Paid) {
						statusInvoice = PanelPhieuDatMuaHang.PAID;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				String name = "PDH";
				if (invoiceDetail.getType().equals(AccountingModelManager.typeDatHang)
				    && invoiceDetail.getActivityType().equals(ActivityType.Receipt)) {
					name = "KDH";
				}
				if (invoiceDetail.getType().equals(AccountingModelManager.typeTraHang)) {
					if (invoiceDetail.getActivityType().equals(ActivityType.Receipt)) {
						name = "Khách trả hàng";
					} else {
						name = "Phiếu trả hàng";
					}
				}
				Profile profile = AccountModelManager.getInstance().getProfileConfigAdmin();
				if (profile.get(DialogConfig.GDPos).toString().equals("true")
						&&profile.get(DialogConfig.Keyboard) != null && profile.get(DialogConfig.Keyboard).toString().equals("true")) {
					DialogPhieuDatMuaHangPos panel = DialogPhieuDatMuaHangPos.getInstance();
					if (DialogScreenOftenPos.isFlagScreenOften()) {
						DialogScreenOftenPos.setFlagScreenOften(false);
						DialogScreenOftenPos pos = DialogScreenOftenPos.getInstance();
						panel.getPanelTop2().add(pos.getPanelProductTagRoot(), 1);
						panel.getScrollPane_Product().setViewportView(pos.getPanelProducts());
					}
					panel.setTypeInvoice(invoiceDetail, statusInvoice, name);
					panel.setVisible(true);
				} else {
					PanelPhieuDatMuaHang panel = new PanelPhieuDatMuaHang(invoiceDetail, statusInvoice, name);
					panel.setName("pnPhieuDatMuaHang");
					try {
						profile = AccountModelManager.getInstance().getProfileConfigAdmin();
						if (profile.get(DialogConfig.Keyboard) != null
						    && profile.get(DialogConfig.Keyboard).toString().equals("true")) {
							DialogResto dialog = new DialogResto(panel, true, 0, 0);
							dialog.setTitle("Khách trả hàng");
							dialog.setIcon("tra1.png");
							dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
							dialog.dispose();
							dialog.setUndecorated(true);
							dialog.setLocationRelativeTo(null);
							dialog.setBtnSave(false);
							dialog.setBtnExit(false);
							dialog.setModal(true);
							dialog.setVisible(true);
						} else {
							DialogContain dialog1 = new DialogContain(panel);
							dialog1.getContentPane().add(panel);
							dialog1.setName("dlPhieuDatMuaHang");
							dialog1.setSize(Toolkit.getDefaultToolkit().getScreenSize());
							dialog1.dispose();
							dialog1.setUndecorated(true);
							dialog1.setLocationRelativeTo(null);
							dialog1.setModal(true);
							dialog1.setVisible(true);
						}

					} catch (Exception e1) {
					}
				}
			}
		}
		loadDataByTime(start, end);

	}

	private void loadData(Date start, Date end) {

		try {
			String dateStart, dateEnd;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar calendarStart = Calendar.getInstance();
			Calendar calendarEnd = Calendar.getInstance();
			if (start != null) {
				calendarStart.setTime(start);
				calendarStart.set(Calendar.HOUR_OF_DAY, 00);
				calendarStart.set(Calendar.MINUTE, 00);
				calendarStart.set(Calendar.SECOND, 00);
			}
			if (end != null) {
				calendarEnd.setTime(end);
				calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
				calendarEnd.set(Calendar.MINUTE, 59);
				calendarEnd.set(Calendar.SECOND, 59);
			}
			dateStart = dateFormat.format(calendarStart.getTime());
			dateEnd = dateFormat.format(calendarEnd.getTime());
			SQLSelectQuery rQuery = new SQLSelectQuery();
			rQuery
			    .table("InvoiceDetail i")
			    .field("i.id AS `id`", "id")
			    .field("i.invoiceCode AS `invoiceCode`", "invoiceCode")
			    .field("i.invoiceName AS `invoiceName`", "invoiceName")
			    .field("i.type AS `type`", "type")
			    .field("i.activityType AS `activityType`", "activityType")
			    .field("i.status AS `status`", "status")
			    .field("(select `name` from `employee` where `code`= i.employeeCode) AS `employee`", "employee")
			    .field("(select `name` from `customer` where `code`= i.customerCode) AS `customer`",
			        "customer")
			        .field("(select `mobile` from `customer` where `code`= i.customerCode) AS `mobile`",
			        "mobile")
			    .field("(select `name` from `supplier` where `code`= i.supplierCode) AS `supplier`",
			        "supplier")
			        .field("(select `mobile` from `supplier` where `code`= i.supplierCode) AS `mobile1`",
			        "mobile1")
			    .field("i.startDate AS `date`", "date")
			    .field("i.guest AS `guest`", "guest")
			    .field("i.projectCode AS `projectCode`", "projectCode")
			    .field(
			        "(select `name` from `restaurant_project` where `code`= SUBSTRING_INDEX(i.projectCode,'/', -1)) AS `project`",
			        "project")
			    .field(
			        "(select `label` from `accountGroup` where `name`= SUBSTRING_INDEX(i.departmentCode,'/', -1)) AS `department`",
			        "department").field("i.code AS `code`", "code").field("i.total AS `total`", "total")
			    .field("i.discount AS `discount`", "discount").field("i.totalTax AS `totalTax`", "totalTax")
			    .field("i.service AS `service`", "service").field("i.finalCharge AS `finalCharge`", "finalCharge")
			    .field("i.totalPaid AS `totalPaid`", "totalPaid").field("i.currencyUnit AS `currencyUnit`", "currencyUnit");

			if (employee != null) {
				rQuery.cond("i.employeeCode = '" + employee.getCode() + "'");
			}
			if (activityType != null) {
				rQuery.cond("i.activityType = '" + activityType + "'");
			}
			if (type != null && !type.trim().isEmpty()) {
				rQuery.cond("i.type = '" + type + "'");
			}
			Profile profile = AccountModelManager.getInstance().getProfileConfigAdmin();
			if (profile.get("ForRent") == null) {
				rQuery.cond("i.status = '" + Invoice.Status.PostPaid.toString() + "'");
			} else {
				rQuery.cond("i.status = '" + Invoice.Status.ForRent.toString() + "'");
			}

			rQuery.cond("i.startDate >= '" + dateStart + "' AND i.startDate <= '" + dateEnd + "'  ORDER BY i.startDate DESC");
			System.out.println(rQuery.createSQLQuery());
			ReportTable[] reportTable = AccountingModelManager.getInstance().reportQuery(new SQLSelectQuery[] { rQuery });

			records = reportTable[0].getRecords();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void cutData(int index, int pageSize) {
		String[] field = new String[] { "Stt", "invoiceCode", "invoiceName", "type", "activityType", "status", "employee",
		    "customer", "supplier", "date", "date", "guest", "projectCode", "project", "department", "", "description",
		    "total", "discount", "totalTax", "service", "finalCharge", "totalPaid", "", "currencyUnit", "" };
		for (int i = index; i < pageSize; i++) {
			Map<String, Object> record = records.get(i);
			Object[] values = new Object[field.length];
			for (int j = 0; j < field.length; j++) {
				switch (j) {
				case 0:
					values[j] = i + 1;
					break;
				case 1:
					values[j] = record.get(field[j]).toString();
					if (values[j].toString().indexOf(":") > 0) {
						values[j] = values[j].toString().substring(values[j].toString().indexOf(":") + 1);
					}
					break;
				case 4:
					try {
						if (record.get(field[j]).toString().equals("Payment")) {
							values[j] = "Chi";
						} else {
							values[j] = "Thu";
						}
					} catch (Exception e) {
						values[j] = "Thu";
					}

					break;
				case 5:
					try {
						if (record.get(field[j]).toString().equals("Paid")) {
							values[j] = "Đã thanh toán";
						} else {
							values[j] = "Chưa thanh toán";
						}
					} catch (Exception e) {
						values[j] = "Thu";
					}

					break;
				case 9:
					values[j] = record.get(field[j]).toString().substring(0, 10);
					break;
				case 10:
					values[j] = record.get(field[j]).toString().substring(10, 16);
					break;

				case 7:
					try {
						if (!record.get(field[j]).toString().isEmpty()) {
							values[j] = record.get(field[j]).toString();
							values[8] = record.get("mobile").toString();
						}
					} catch (Exception e) {
					}

					break;
				case 8:
					try {
						try {
							if (!record.get(field[j]).toString().isEmpty()) {
								values[7] = record.get(field[j]).toString();
								values[j] = record.get("mobile1").toString();
								
							}
						} catch (Exception e) {
						}
					} catch (Exception e) {
					}

					break;
				case 12:
					try {
						if (record.get(field[j]).toString().indexOf("other") >= 0) {
							values[j] = "";
						} else {
							values[j] = record.get(field[j]).toString()
							    .substring(record.get(field[j]).toString().lastIndexOf("/") + 1);
						}
					} catch (Exception e) {
						values[j] = "";
					}

					break;
				case 13:
					try {
						if (record.get(field[j]).toString().indexOf("khác") >= 0) {
							values[j] = "";
						} else {
							values[j] = record.get(field[j]).toString()
							    .substring(record.get(field[j]).toString().lastIndexOf("/") + 1);
						}
					} catch (Exception e) {
						values[j] = "";
					}

					break;
				case 14:
					try {
						if (record.get(field[j]).toString().indexOf("khác") >= 0) {
							values[j] = "";
						} else {
							values[j] = record.get(field[j]).toString()
							    .substring(record.get(field[j]).toString().lastIndexOf("/") + 1);
						}
					} catch (Exception e) {
						values[j] = "";
					}
					break;
				case 22:
					values[j] = new MyDouble(record.get(field[j]).toString());
					break;
				case 17:
					values[j] = new MyDouble(record.get(field[j]).toString());
					break;
				case 18:
					values[j] = new MyDouble(record.get(field[j]).toString());
					break;
				case 19:
					values[j] = new MyDouble(record.get(field[j]).toString());
					break;
				case 20:
					values[j] = new MyDouble(record.get(field[j]).toString());
					break;
				case 21:
					values[j] = new MyDouble(record.get(field[j]).toString());
					break;
				case 23:
					try {
						values[j] = new MyDouble(new MyDouble(values[21].toString()).doubleValue()
						    - new MyDouble(values[22].toString()).doubleValue());
					} catch (Exception e) {
						values[j] = "0";
					}
					break;
				case 25:
					values[j] = "false";
					break;
				default:
					values[j] = record.get(field[j]);
					break;
				}
			}
			modelThuChi.addRow(values);
		}
	}

	@Override
	public int getListSize() {
		return records.size();
	}

	@Override
	public DefaultTableModel loadTable(int index, int pageSize) {
		int k = modelThuChi.getRowCount();
		while (k > 0) {
			modelThuChi.removeRow(k - 1);
			k--;

		}
		cutData(index, pageSize);
		return modelThuChi;
	}

	@Override
	public List<Integer> getColumnSum() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(17);
		list.add(18);
		list.add(19);
		list.add(20);
		list.add(21);
		list.add(22);
		list.add(23);
		return list;
	}

	@Override
	public boolean delete() {
		if (UIConfigModelManager.getInstance().getPermission(ScreenOften.permission) == Capability.ADMIN) {
			try {
				String str = "Xóa tất cả danh sách đã chọn ";
				PanelChoise panel = new PanelChoise(str + " ?");
				panel.setName("Xoa");
				DialogResto dialog = new DialogResto(panel, false, 0, 80);
				dialog.setName("dlXoa");
				dialog.setTitle("Xóa hóa đơn");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
				final HashMap<String, Date> hashMaps = new HashMap<String, Date>();
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				if (panel.isDelete()) {
					System.out.println(this.getRowCount());
					int colum = this.getColumnCount() - 1;
					for (int i = 0; i < this.getRowCount(); i++) {
						if (this.getValueAt(i, colum).toString().equals("true")) {
							Map<String, Object> record = records.get(i);
							String code = record.get("invoiceCode").toString();
							InvoiceDetail invoiceDetail = AccountingModelManager.getInstance().getInvoiceDetailByCode(code);
							hashMaps.put(df.format(invoiceDetail.getStartDate()), invoiceDetail.getStartDate());
							AccountingModelManager.getInstance().deleteInvoice(invoiceDetail);

						}
					}
					
					loadDataByTime(start, end);
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		} else {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			return false;
		}
		return false;
	}

	@Override
	public boolean isDelete() {
		return true;
	}

	@Override
	public void loadDataByTime(Date startDate, Date endDate) {
		this.start = startDate;
		this.end = endDate;
		int k = modelThuChi.getRowCount();
		while (k > 0) {
			modelThuChi.removeRow(k - 1);
			k--;

		}
		loadData(startDate, endDate);
		change();
	}

	@Override
	public Date getDateStart() {
		return start;
	}

	@Override
	public Date getDateEnd() {
		return end;
	}

}
