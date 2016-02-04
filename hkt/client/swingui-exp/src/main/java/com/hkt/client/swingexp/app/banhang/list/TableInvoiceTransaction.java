package com.hkt.client.swingexp.app.banhang.list;

import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.hkt.client.swingexp.app.thuchi.PanelPaymentReceipt;
import com.hkt.client.swingexp.app.thuchi.PanelPhieuDatMuaHang;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.WarehouseModelManager;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.accounting.entity.Invoice;
import com.hkt.module.accounting.entity.Invoice.Status;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceTransaction.ActivityType;
import com.hkt.module.core.entity.ReportTable;
import com.hkt.module.core.entity.SQLSelectQuery;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.warehouse.entity.IdentityProduct;

@SuppressWarnings("serial")
public class TableInvoiceTransaction extends TableObservable implements ITableMainExt {

	private TableModelInvoiceTransaction modelThuChi;
	private Date start, end;
	private Employee employee;
	@SuppressWarnings("unused")
	private String currency;
	private ActivityType activityType;
	private List<Map<String, Object>> records;

	public TableInvoiceTransaction(Date start, Date end, Employee employee, String currency, ActivityType type) {
		this.start = start;
		this.end = end;
		this.employee = employee;
		this.currency = currency;
		this.activityType = type;
		modelThuChi = new TableModelInvoiceTransaction();
		setModel(modelThuChi);
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
			rQuery.table("InvoiceTransaction i")
					.field("(select concat(`invoiceCode`,'--',`invoiceName`,'--',`finalCharge`,'--',`totalPaid`,'--',`type`) from `InvoiceDetail` where `id`= i.invoiceId) AS `invoice`", "invoice")
					.field("i.transactionDate AS `date`", "date").field("(select `name` from `employee` where `code`= i.employeeCode) AS `employee`", "employee")
					.field("i.projectCode AS `projectCode`", "projectCode").field("i.total AS `total`", "total").field("i.activityType AS `activityType`", "activityType")
					.field("(select `name` from `restaurant_project` where `code`= SUBSTRING_INDEX(i.projectCode,'/', -1)) AS `project`", "project")
					.field("(select `label` from `accountGroup` where `name`= SUBSTRING_INDEX(i.departmentCode,'/', -1)) AS `department`", "department");

			if (employee != null) {
				rQuery.cond("i.employeeCode = '" + employee.getCode() + "'");
			}
			if (activityType != null) {
				rQuery.cond("i.activityType = '" + activityType + "'");
			}
			rQuery.cond("i.transactionDate >= '" + dateStart + "' AND i.transactionDate <= '" + dateEnd + "'  ORDER BY i.transactionDate DESC");
			System.out.println(rQuery.createSQLQuery());
			ReportTable[] reportTable = AccountingModelManager.getInstance().reportQuery(new SQLSelectQuery[] { rQuery });
			reportTable[0].dump(new String[] { "invoice", "date", "employee", "projectCode", "total", "activityType" });

			records = reportTable[0].getRecords();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void cutData(int index, int pageSize){
		String[] field = new String[] { "STT", "invoice", "invoice", "activityType", "invoice","date", "employee", "projectCode", "project", "department", "invoice", "total", "invoice" };
		for (int i = index; i < pageSize; i++) {
			Map<String, Object> record = records.get(i);
			Object[] values = new Object[field.length];
			for (int j = 0; j < field.length; j++) {
				switch (j) {
				case 0:
					values[j] = i+1;
					break;
				case 1:
					values[j] = record.get(field[j]).toString().split("--")[0];
					if (values[j].toString().indexOf(":") > 0) {
						values[j] = values[j].toString().substring(values[j].toString().indexOf(":") + 1);
					}

					break;
				case 2:
					values[j] = record.get(field[j]).toString().split("--")[1];
					break;
				case 3:
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
				case 4:
					try {
						values[j] = record.get(field[j]).toString().split("--")[4];
          } catch (Exception e) {
          	values[j] ="";
          }
					
					break;
				case 5:
					values[j] = record.get(field[j]).toString().substring(0, 10);
					break;
				case 6:
					try {
						values[j] = record.get(field[j]).toString();
					} catch (Exception e) {
						values[j] = "";
					}

					break;
				case 7:
					try {
						if (record.get(field[j]).toString().indexOf("other") >= 0) {
							values[j] = "";
						} else {
							values[j] = record.get(field[j]).toString().substring(record.get(field[j]).toString().lastIndexOf("/") + 1);
						}
					} catch (Exception e) {
						 values[j] = "";
					}

					break;
				case 8:
					try {
						if (record.get(field[j]).toString().indexOf("khác") >= 0) {
							values[j] = "";
						} else {
							values[j] = record.get(field[j]).toString().substring(record.get(field[j]).toString().lastIndexOf("/") + 1);
						}
					} catch (Exception e) {
						values[j] = "";
					}

					break;
				case 9:
					try {
						if (record.get(field[j]).toString().indexOf("khác") >= 0) {
							values[j] = "";
						} else {
							values[j] = record.get(field[j]).toString().substring(record.get(field[j]).toString().lastIndexOf("/") + 1);
						}
					} catch (Exception e) {
						values[j] = "";
					}

					break;
				case 11:
					values[j] = new MyDouble(record.get(field[j]).toString());
					break;
				case 10:
					values[j] = new MyDouble(record.get(field[j]).toString().split("--")[2]);
					break;
				case 12:
					try {
						values[j] = new MyDouble(new MyDouble(values[10].toString()).doubleValue() - new MyDouble(record.get(field[j]).toString().split("--")[3]).doubleValue());
					} catch (Exception e) {
						values[j] = "0";
					}
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
	public void click() {
		try {
			viewSalse();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void viewSalse() throws Exception {
		Map<String, Object> record = records.get(getSelectedRow());
		String code = record.get("invoice").toString().split("--")[0];
		InvoiceDetail invoice = AccountingModelManager.getInstance().getInvoiceDetailByCode(code);
		if (invoice.getInvoiceCode().startsWith("TC")) {
			PanelPaymentReceipt panel = new PanelPaymentReceipt(invoice.getActivityType());
			panel.setInvoice(invoice);
			DialogMain dialog = new DialogMain(panel);
			dialog.getBtnExt().setVisible(true);
			dialog.getBtnExt().addActionListener(panel.getActionListener());
			dialog.showButton(false);
			dialog.setIcon("invoice1.png");
			dialog.setName("dlPhieuThuTien");
			dialog.setLocationRelativeTo(null);
			if (invoice.getActivityType().toString().equals(ActivityType.Receipt.toString())) {
				dialog.setTitle("Phiếu thu tiền");
			} else {
				dialog.setTitle("Phiếu chi tiền");
			}
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
		} else {
			if (invoice.getType().equals(AccountingModelManager.typeBanHang)) {
				try {
					Profile profile = AccountModelManager.getInstance().getProfileConfigAdmin();
					if (profile.get(DialogConfig.Maket).toString().equals("true") || profile.get(DialogConfig.Shop).toString().equals("true")) {
						if (profile.get(DialogConfig.Maket).toString().equals("true")) {
							if (profile.get(DialogConfig.GDPos).toString().equals("true")) {
								DialogSuperMarketPos marketPos = DialogSuperMarketPos.getInstance();
								marketPos.setInvoiceDetail(invoice);
								marketPos.setVisible(true);
							} else {
								DialogSuperMarket dialogSuperMarket = DialogSuperMarket.getInstance();
								dialogSuperMarket.setInvoiceDetail(invoice);
								dialogSuperMarket.setVisible(true);
							}
						} else {
							DialogScreenOftenPos dialog = DialogScreenOftenPos.getInstance();
							if (!DialogScreenOftenPos.isFlagScreenOften()) {
								DialogScreenOftenPos.setFlagScreenOften(true);
								dialog.getPanelTop2().add(dialog.getPanelProductTagRoot(), 1);
								dialog.getScrollPane_Product().setViewportView(dialog.getPanelProducts());
							}
							dialog.setInvoiceDetail(invoice);
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
							dialog.setInvoiceDetail(invoice);
							dialog.setVisible(true);
						} else {
							ScreenOften dialog = ScreenOften.getInstance();
							dialog.setInvoiceDetail(invoice);
							dialog.setVisible(true);
						}
					}
				} catch (Exception e2) {
					ScreenOften dialog = ScreenOften.getInstance();
					dialog.setInvoiceDetail(invoice);
					dialog.setVisible(true);
				}

			} else {
				int statusInvoice = 0;
				try {
					List<IdentityProduct> identityProducts = WarehouseModelManager.getInstance().getByInvoiceCode(invoice.getInvoiceCode());
					if (identityProducts.size() == 0 && invoice.getStatus() == Status.PostPaid) {
						statusInvoice = PanelPhieuDatMuaHang.UNPAID_AND_NOTIMPORT;
					}
					if (identityProducts.size() == 0 && invoice.getStatus() == Status.Paid) {
						statusInvoice = PanelPhieuDatMuaHang.PAID_AND_NOTIMPORT;
					}
					if (identityProducts.size() > 0 && invoice.getStatus() == Status.PostPaid) {
						statusInvoice = PanelPhieuDatMuaHang.UNPAID;
					}
					if (identityProducts.size() > 0 && invoice.getStatus() == Status.Paid) {
						statusInvoice = PanelPhieuDatMuaHang.PAID;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				String name = "PDH";
				if (invoice.getType().equals(AccountingModelManager.typeDatHang) && invoice.getActivityType().equals(ActivityType.Receipt)) {
					name = "KDH";
				}
				if (invoice.getType().equals(AccountingModelManager.typeTraHang)) {
					if (invoice.getActivityType().equals(ActivityType.Receipt)) {
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
					panel.setTypeInvoice(invoice, statusInvoice, name);
					panel.setVisible(true);
				} else {
					PanelPhieuDatMuaHang panel = new PanelPhieuDatMuaHang(invoice, statusInvoice, name);
					panel.setName("pnPhieuDatMuaHang");
					try {
						profile = AccountModelManager.getInstance().getProfileConfigAdmin();
						if (profile.get(DialogConfig.Keyboard) != null && profile.get(DialogConfig.Keyboard).toString().equals("true")) {
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
		loadDataByTime(this.start, this.end);
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
		list.add(10);
		list.add(11);
		list.add(12);
		return list;
	}

	@Override
	public boolean delete() {
		return false;
	}

	@Override
	public boolean isDelete() {
		return false;
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
