package com.hkt.client.swingexp.app.banhang;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.main.Main;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.WarehouseModelManager;
import com.hkt.module.accounting.DefaultInvoiceCalculator;
import com.hkt.module.accounting.entity.Invoice;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.accounting.entity.InvoiceTransaction;
import com.hkt.module.accounting.entity.Invoice.Status;
import com.hkt.module.accounting.entity.InvoiceTransaction.TransactionType;
import com.hkt.module.product.entity.Product;
import com.hkt.module.warehouse.entity.IdentityProduct;
import com.hkt.module.warehouse.entity.WarehouseInventory;
import com.hkt.module.warehouse.entity.IdentityProduct.ExportType;
import com.hkt.module.warehouse.entity.IdentityProduct.ImportType;
import com.hkt.util.text.DateUtil;

@SuppressWarnings("serial")
public class PanelCreateDataTest extends JPanel implements IDialogResto {
	private JLabel lblOwner, lbDate, lbStartDate, lbEndDate;
	private MyJDateChooser txtStartDate, txtEndDate;
	// private Processing processing;
	public static String permission;
	private boolean warehouse = false;
	private boolean quantitative = false;
	private int s = 0;
	private List<Product> products;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PanelCreateDataTest() {
		this.setBackground(new Color(255, 255, 255));
		System.out.println(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) + "   "
		    + Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		// processing = new Processing();
		lblOwner = new ExtendJLabel(" Loại");
		lbDate = new ExtendJLabel("Tính từ");

		lbStartDate = new ExtendJLabel("Từ ngày");
		lbEndDate = new ExtendJLabel("Đến ngày");

		txtStartDate = new MyJDateChooser();
		txtStartDate.setDateFormatString("dd/MM/yyyy");

		txtEndDate = new MyJDateChooser();
		txtEndDate.setDateFormatString("dd/MM/yyyy");

		try {
			products = ProductModelManager.getInstance().findAllProducts();
		} catch (Exception e) {
		}

		getContentPanel();

	}

	private void getContentPanel() {
		setOpaque(false);
		MyGroupLayout layout = new MyGroupLayout(this);
		layout.add(0, 0, lbStartDate);
		layout.add(0, 1, txtStartDate);
		layout.add(0, 2, lbEndDate);
		layout.add(0, 3, txtEndDate);
		layout.updateGui();

	}

	@Override
	public void Save() throws Exception {
		// processing.setVisible(true);
		// try {
		// new SwingWorker<Void, Void>() {
		// @Override
		// protected Void doInBackground() throws Exception {
		// // Tính thờ gian query
		Thread t = new Thread() {
			@Override
			public void run() {
				AccountingModelManager.getInstance().updateQuantityRealInvoceItem();
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				Main.labelStatus.setText("Đang tính lại thống kê kho:");
				long startTime = System.currentTimeMillis();
				if (txtStartDate.getDate() != null && txtEndDate.getDate() != null) {
					Calendar c = Calendar.getInstance();
					c.setTime(txtStartDate.getDate());
					c.set(Calendar.HOUR_OF_DAY, 0);
					c.set(Calendar.MINUTE, 01);

					Calendar c1 = Calendar.getInstance();
					c1.setTime(txtEndDate.getDate());
					c1.set(Calendar.HOUR_OF_DAY, 23);
					c1.set(Calendar.MINUTE, 59);
					while (c.before(c1)) {
						for (int l = 0; l < 100; l++) {
							InvoiceDetail invoiceDetail = new InvoiceDetail(true);
							invoiceDetail.setStartDate(c.getTime());
							invoiceDetail.setEndDate(c.getTime());
							invoiceDetail.setInvoiceCode(DateUtil.asCompactDateTimeId(c.getTime()) + l);
							invoiceDetail.setDepartmentCode("department-other");

							invoiceDetail.setType(AccountingModelManager.typeBanHang);
							invoiceDetail.setActivityType(Invoice.ActivityType.Receipt);
							invoiceDetail.setLocationCode(RestaurantModelManager.getInstance().getLocationOther().getCode());
							invoiceDetail.setTableCode(RestaurantModelManager.getInstance().getTableOther().getCode());
							invoiceDetail.setCurrencyRate(1);
							invoiceDetail.setCurrencyUnit("VND");
							invoiceDetail.setDiscount(0);
							invoiceDetail.setDiscountRate(0);
							invoiceDetail.setInvoiceName(invoiceDetail.getInvoiceCode());
							invoiceDetail.setTotalTax(0);
							invoiceDetail.setDescription("");

							String pathProject = RestaurantModelManager.getInstance().getProjectOther().getCode();
							invoiceDetail.setProjectCode(pathProject);
							invoiceDetail.setEmployeeCode(null);
							invoiceDetail.setDepartmentCode(HRModelManager.getInstance().getDepartmentOther().getPath());
							invoiceDetail.setGroupCustomerCode(CustomerModelManager.getInstance().getGroupCustomerOther().getPath());
							for (int k = 0; k < 10; k++) {
								InvoiceItem invoiceItem = new InvoiceItem();
								invoiceItem.setProductCode(products.get(s).getCode());
								invoiceItem.setCurrencyRate(invoiceDetail.getCurrencyRate());
								invoiceItem.setCurrencyUnit(invoiceDetail.getCurrencyUnit());
								invoiceItem.setDiscount(0);
								invoiceItem.setDiscountByInvoice(invoiceDetail.getDiscount());
								invoiceItem.setDiscountRate(0);
								invoiceItem.setStatus(AccountingModelManager.isPayment);
								invoiceItem.setDiscountRateByInvoice(invoiceDetail.getDiscountRate());
								invoiceItem.setItemCode(DateUtil.asCompactDateTimeId(c.getTime()) + l + k);
								invoiceItem.setItemName(products.get(s).getName());
								invoiceItem.setQuantity(10);
								invoiceItem.setTax(invoiceDetail.getTotalTax());
								invoiceItem.setActivityType(InvoiceItem.ActivityType.Receipt);
								invoiceItem.setUnitPrice(100);
								invoiceItem.setTotal(100);
								invoiceItem.setFinalCharge(100);
								invoiceDetail.add(invoiceItem);
								for (int t = 0; t <= 10; t++) {
									IdentityProduct identityProduct = new IdentityProduct();
									identityProduct.setInvoiceCode(invoiceDetail.getInvoiceCode());
									identityProduct.setInvoiceItemIdImport(s);
									identityProduct.setWarehouseCode(null);
									identityProduct.setImportDate(invoiceItem.getStartDate());
									identityProduct.setProductCode(invoiceItem.getProductCode());
									identityProduct.setPrice(invoiceItem.getUnitPrice());
									identityProduct.setUnitPrice(invoiceItem.getCurrencyUnit());
									identityProduct.setCurrencyRate(invoiceItem.getCurrencyRate());
									identityProduct.setShipmentImportCode(DateUtil.asCompactDateTimeId(new Date()) + k);
									identityProduct.setImportDate(invoiceItem.getStartDate());
									identityProduct.setImportType(ImportType.Import);
									if (t < 5) {
										identityProduct.setExportType(ExportType.NotExport);
									} else {
										identityProduct.setExportDate(invoiceItem.getStartDate());
										identityProduct.setShipmentExportCode(DateUtil.asCompactDateTimeId(new Date()) + k);
										identityProduct.setExportType(ExportType.Export);
										identityProduct.setInvoiceExportCode(invoiceDetail.getInvoiceCode());
										identityProduct.setExportDate(invoiceItem.getStartDate());
										identityProduct.setCurrencyExportRate(invoiceItem.getCurrencyRate());
										identityProduct.setPriceExport(invoiceItem.getUnitPrice());
									}
									identityProduct = WarehouseModelManager.getInstance().saveIdentityProduct(identityProduct);
								}
								s++;
								if (s == products.size()) {
									s = 0;
								}
							}

							// Cộng thêm tiền nếu invoice của khách hàng là chi và trả trước
							invoiceDetail.setStatus(Status.Paid);
							InvoiceTransaction invoiceTransaction = new InvoiceTransaction();
							invoiceTransaction.setCreatedBy("");
							invoiceTransaction.setCurrencyRate(invoiceDetail.getCurrencyRate());
							invoiceTransaction.setCurrencyUnit(invoiceDetail.getCurrencyUnit());
							invoiceTransaction.setTotal(10000);
							invoiceTransaction.setTransactionDate(c.getTime());
							invoiceTransaction.setTransactionType(TransactionType.Cash);
							invoiceDetail.add(invoiceTransaction);

							invoiceDetail = invoiceDetail.calculate(new DefaultInvoiceCalculator());
							invoiceDetail.setTotal(10000);
							invoiceDetail.setFinalCharge(10000);
							invoiceDetail.setTotalPaid(invoiceDetail.getFinalCharge());
							invoiceDetail = AccountingModelManager.getInstance().saveInvoiceDetail(invoiceDetail);
							Main.labelMessenger.setText("xong invoice: " + invoiceDetail);
						}

					}
				}

				// Kết quả thời gian query:
				long endTime = System.currentTimeMillis();
				NumberFormat formatter = new DecimalFormat("#0.00000");
				String time = formatter.format((endTime - startTime) / 1000d);
				Main.labelMessenger.setText("Đã tính xong thống kê, tổng thời gian tính: " + time);
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Main.labelMessenger.setText("");
				Main.labelStatus.setText("");
			}
		};
		t.start();
		((JDialog) getRootPane().getParent()).dispose();
		// System.out.println("###$$$ TIME RUN QUERY: " + time);
		//
		// return null;
		// };
		//
		// @Override
		// protected void done() {
		// processing.setVisible(false);
		// };
		// }.execute();
		// } catch (Exception ex) {
		// processing.setVisible(false);
		// JOptionPane.showMessageDialog(null, "Quá trình thao tác phát sinh lỗi!" +
		// ex.toString());
		// }
	}

}
