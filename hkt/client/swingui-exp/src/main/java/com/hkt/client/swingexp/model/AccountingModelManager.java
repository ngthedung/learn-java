package com.hkt.client.swingexp.model;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.AccountingServiceClient;
import com.hkt.client.swingexp.app.banhang.screen.often.InvoiceItemPromotion;
import com.hkt.client.swingexp.app.banhang.screen.often.ScreenOften;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.khuvucbanhang.PanelManageCodes;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.accounting.entity.Bank;
import com.hkt.module.accounting.entity.BankAccount;
import com.hkt.module.accounting.entity.BankTransaction;
import com.hkt.module.accounting.entity.Invoice;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.accounting.entity.ManageCodes;
import com.hkt.module.accounting.entity.ManageCodes.ManageType;
import com.hkt.module.accounting.entity.ManageCodes.RotationType;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.ReportTable;
import com.hkt.module.core.entity.SQLSelectQuery;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.customer.entity.Point;
import com.hkt.module.partner.supplier.entity.Supplier;
import com.hkt.module.restaurant.entity.Project;
import com.hkt.module.restaurant.entity.Shift;
import com.hkt.module.warehouse.entity.IdentityProduct;
import com.hkt.module.warehouse.entity.IdentityProduct.ExportType;
import com.hkt.module.warehouse.entity.IdentityProduct.ImportType;
import com.hkt.module.warehouse.entity.WarehouseInventory;
import com.hkt.module.warehouse.entity.WarehouseProfile;
import com.hkt.util.text.DateUtil;

public class AccountingModelManager {
	private static AccountingModelManager instance;
	public static String isRecord = "Đặt hàng";
	public static String isForRent = "Cho thuê";
	public static String isPayment = "Đã thanh toán";
	public static String isKitchen = "Đã chế biến";
	public static String isPromotion = "Khuyến mãi";
	public static String isCance = "Hủy";
	public static String pay = "Tiền mặt";
	public static String organization = "Doanh nghiệp";
	public static String product = "Hàng hóa";
	public static String location = "Khu vực";
	public static String table = "Bàn/quầy";
	public static String groupCustomer = "Nhóm khách hàng";
	public static String menu = "Menu";
	public static String typeBanHang = "Bán Hàng";
	public static String typeThuChi = "Thu Chi";
	public static String typeTraTruoc = "Trả Trước";
	public static String typeDatHang = "Đặt Hàng";
	public static String typeTraHang = "Trả Hàng";
	public static String typeSanXuat = "Nhập xuất nội bộ";
	public static int print = 1;
	public static boolean isVN = true;
	private Profile profile;

	public void setList(List<WarehouseInventory> list) {
	}

	private ClientContext clientContext = ClientContext.getInstance();
	private AccountingServiceClient clientCore = clientContext.getBean(AccountingServiceClient.class);

	private AccountingModelManager() {
		profile = AccountModelManager.getInstance().getProfileConfigAdmin();
		if (profile.get("InHoaDon") != null) {
			try {
				print = Integer.parseInt(profile.get("InHoaDon").toString());
			} catch (Exception e) {
				print = 1;
			}
		}
		createParentBank();
		//TODO goi ham vao day
	}

	static public AccountingModelManager getInstance() {
		if (instance == null) {
			instance = new AccountingModelManager();
		}
		return instance;
	}

	public void executeSQL(String sql) throws Exception {
		clientCore.executeSQL(sql);
	}

	public InvoiceDetail saveInvoice(InvoiceDetail invoiceDetail) throws Exception {
		return clientCore.saveInvoice(invoiceDetail);
	}

	public void updatePriceProduct() {

		try {
			String sql2 = "update product p join invoiceitem i on i.productCode = p.code set p.price = i.unitPrice where "
			    + "i.activityType = 'Payment'";
			System.out.println(sql2.toString());
			executeSQL(sql2.toString());
		} catch (Exception e) {
		}

		try {
			String sql2 = "update product p set p.updatePrice = 1";
			System.out.println(sql2.toString());
			executeSQL(sql2.toString());
		} catch (Exception e) {
		}

		try {
			String sql2 = "update product p join productattribute i on i.productId = p.id set p.description = i.value where i.name = 'BarCode'";
			System.out.println(sql2.toString());
			executeSQL(sql2.toString());
		} catch (Exception e) {

		}

		try {
			String sql2 = "update product p join restaurant_recipe i on i.productCode = p.code set p.recipe = 1";
			System.out.println(sql2.toString());
			executeSQL(sql2.toString());
		} catch (Exception e) {

		}

	}
	
	public void updateStatusInvoice(){
		
		try {
			String sql2 = "update invoicedetail p set p.status = 'Paid' where p.status is null";
			System.out.println(sql2.toString());
			executeSQL(sql2.toString());
		} catch (Exception e) {
		}
	}

	public void updateTypeTransaction() {

		try {
			String sql2 = "update invoicetransaction p join invoicedetail i on i.id = p.invoiceId set p.type = i.type";
			System.out.println(sql2.toString());
			executeSQL(sql2.toString());
		} catch (Exception e) {
		}
		
		try {
			String sql2 = "update invoicetransaction p set p.currencyRate = 1 where p.currencyRate = 0";
			System.out.println(sql2.toString());
			executeSQL(sql2.toString());
		} catch (Exception e) {
		}
		
		try {
			String sql2 = "update invoicedetail p set p.currencyRate = 1 where p.currencyRate = 0";
			System.out.println(sql2.toString());
			executeSQL(sql2.toString());
		} catch (Exception e) {
		}

	}
	
	public List<BankAccount> getBankAccounts(){
		try {
			return clientCore.getBankAccounts();
		} catch (Exception e) {
			return new ArrayList<BankAccount>();
		}
	}

	public InvoiceDetail saveInvoiceDetail(InvoiceDetail invoiceDetail) {
		try {
			if (invoiceDetail.getShiftDate() == null) {
				Date date = new Date();
				List<Shift> shifts = RestaurantModelManager.getInstance().getAllShift();
				DateFormat dfGioPhut = new SimpleDateFormat("HHmm");
				int shiftDate = Integer.valueOf(dfGioPhut.format(date).replaceAll(":", ""));
				for (int i = 0; i < shifts.size(); i++) {

					int shiftHourStart = Integer.valueOf(dfGioPhut.format(shifts.get(i).getHourStart()).replaceAll(":", ""));
					int shiftHourEnd = Integer.valueOf(dfGioPhut.format(shifts.get(i).getHourEnd()).replaceAll(":", ""));
					if ((shiftHourStart <= shiftDate) && (shiftDate <= shiftHourEnd)) {
						invoiceDetail.setShiftCode(shifts.get(i).getCode());
						invoiceDetail.setShiftDate(date);
					} else if ((shiftHourStart > shiftHourEnd)) {
						if (shiftHourStart <= shiftDate) {
							invoiceDetail.setShiftDate(date);
							invoiceDetail.setShiftCode(shifts.get(i).getCode());
						} else if (shiftHourEnd >= shiftDate) {
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(date);
							int ngay = calendar.get(Calendar.DAY_OF_MONTH);
							calendar.set(Calendar.DAY_OF_MONTH, ngay - 1);
							date = calendar.getTime();
							invoiceDetail.setShiftDate(date);
							invoiceDetail.setShiftCode(shifts.get(i).getCode());
						}

					}
				}
			}

			invoiceDetail = clientCore.saveInvoiceDetail(invoiceDetail);
			if (invoiceDetail.getCustomerCode() != null && !invoiceDetail.getCustomerCode().trim().isEmpty()) {
				Customer c = CustomerModelManager.getInstance().getCustomerByCode(invoiceDetail.getCustomerCode());
				Point p = CustomerModelManager.getInstance().getPointByCustomerId(c.getId());
				if (p != null && p.getPoint() != 0) {
					List<AccountGroup> acs = CustomerModelManager.getInstance().getCustomerGroup();
					for (AccountGroup ac : acs) {
						try {
							double d = MyDouble.parseDouble(ac.getOwner());
							if (p.getPoint() >= d) {
								List<AccountMembership> accountMemberships = AccountModelManager.getInstance()
								    .findMembershipByAccountLoginId(c.getLoginId());
								AccountMembership accountMembership = null;
								if (accountMemberships.size() > 0) {
									for (AccountMembership ac1 : accountMemberships) {
										if (ac1.getRole().equals(AccountModelManager.Customer)) {
											accountMembership = ac1;
											break;
										}
									}

								}
								if (accountMembership == null) {
									accountMembership = new AccountMembership();
									accountMembership.setLoginId(c.getLoginId());
									accountMembership.setRole(AccountModelManager.Customer);
									accountMembership.setGroupPath(ac.getPath());
									AccountModelManager.getInstance().saveAccountMembership(accountMembership);
								} else {
									accountMembership.setGroupPath(ac.getPath());
									AccountModelManager.getInstance().saveAccountMembership(accountMembership);
								}
							}
						} catch (Exception e) {
						}
					}
				}
				if (!c.isInteractive()) {
					c.setInteractive(true);
					CustomerModelManager.getInstance().saveCustomer(c);
				}
			}
			if (invoiceDetail.getSupplierCode() != null && !invoiceDetail.getSupplierCode().trim().isEmpty()) {
				Supplier c = SupplierModelManager.getInstance().getSupplierByCode(invoiceDetail.getSupplierCode());
				if (!c.isInteractive()) {
					c.setInteractive(true);
					SupplierModelManager.getInstance().saveSupplier(c);
				}
			}

			// System.out.println("TIME RUN4: "
			// + new DecimalFormat("#0.00000").format((System.currentTimeMillis() -
			// startTime) / 1000d));
			return invoiceDetail;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	
	private void deleteIdentityProduct(InvoiceItem item) {
		if (item.getId() != null) {
			try {
				if (item.getActivityType().equals(InvoiceItem.ActivityType.Payment)) {
					List<IdentityProduct> products = WarehouseModelManager.getInstance().getByInvoiceItemIdImport(item.getId());
					for (int i = 0; i < products.size(); i++) {
						WarehouseModelManager.getInstance().deleteIdentityProduct(products.get(i));
					}
				} else {
					List<IdentityProduct> identityProducts = WarehouseModelManager.getInstance().getByInvoiceItemIdExport(
					    item.getId());
					for (IdentityProduct identityProduct : identityProducts) {
						WarehouseModelManager.getInstance().deleteIdentityProductExport(identityProduct);
					}
				}
			} catch (Exception e) {
				System.out.println("loi " + e);
			}
		}
	}

	public InvoiceDetail getInvoiceDetailByCredit(String loginId) {
		try {
			return clientCore.getInvoiceDetailByCredit(loginId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public List<InvoiceDetail> findInvoiceDetailByIdentifierId(String identifierId) {
		try {
			return clientCore.findInvoiceDetailByIdentifierId(identifierId);
		} catch (Exception e) {
			return new ArrayList<InvoiceDetail>();
		}
	}

	public List<InvoiceDetail> findInvoiceDetailByAttributeValue(String value) {
		try {
			return clientCore.findInvoiceDetailByAttributeValue(value);
		} catch (Exception e) {
			return new ArrayList<InvoiceDetail>();
		}
	}

	public List<InvoiceDetail> findInvoiceDetailByAttributeItemValue(String value) {
		try {
			return clientCore.findInvoiceDetailByAttributeItemValue(value);
		} catch (Exception e) {
			return new ArrayList<InvoiceDetail>();
		}
	}

	public String getStartDateLessYear() throws Exception {
		return clientCore.getStartDateLessYear();
	}

	public InvoiceDetail getInvoiceDetailByCode(String invoiceCode) {
		try {
			// long startTime = System.currentTimeMillis();
			InvoiceDetail invoiceDetail = clientCore.getInvoiceDetailByCode(invoiceCode);
			// long endTime = System.currentTimeMillis();
			// System.out.println("TIME RUN QUERY getInvoiceDetailByCode: "
			// + new DecimalFormat("#0.00000").format((endTime - startTime) /
			// 1000d));
			return invoiceDetail;
		} catch (Exception e) {
			return null;
		}
	}

	public List<InvoiceDetail> findInvoiceDetailByName(String invoiceName) {
		try {
			return clientCore.findInvoiceDetailByName(invoiceName);
		} catch (Exception e) {
			return null;
		}
	}

	public InvoiceDetail getInvoiceDetail(long id) {
		try {
			return clientCore.getInvoiceDetail(id);
		} catch (Exception e) {
			return null;
		}
	}

	public boolean deleteInvoice(final InvoiceDetail invoiceDetail) throws Exception {
	
		Thread t2 = new Thread() {
			public void run() {
				long endTime2 = System.currentTimeMillis();
				try {
					try {
						PromotionModelManager.getInstance().deleteByInvoiceId(invoiceDetail.getId());
					} catch (Exception e) {
					}
					try {
						for (InvoiceItem item : invoiceDetail.getItems()) {
							if (item.getProductCode() != null) {
								deleteIdentityProduct(item);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.println("time: "
					    + new DecimalFormat("#0.00000").format((System.currentTimeMillis() - endTime2) / 1000d));
				} catch (Exception e) {
				}

			};
		};
		t2.start();
		try {
			BankAccount bankAccount = new BankAccount();
			bankAccount.setEmployeeDelete(ManagerAuthenticate.getInstance().getLoginId());
			bankAccount.setAddress(invoiceDetail.getEmployeeCode());
			bankAccount.setCurrency(invoiceDetail.getInvoiceName());
			bankAccount.setCode(invoiceDetail.getInvoiceCode());
			bankAccount.setCreatedBy(invoiceDetail.getItems().toString());
			bankAccount.setModifiedBy(""+invoiceDetail.getStartDate());
			bankAccount.setBankAccountId(""+invoiceDetail.getEndDate());
			bankAccount.setRecycleBin(invoiceDetail.isRecycleBin());
			clientCore.saveBankAccount(bankAccount);
		} catch (Exception e) {
		}
		
		return clientCore.deleteInvoiceById(invoiceDetail.getId());
	}

	public boolean deleteQuantityByItemCode(InvoiceItem id) {
		try {
			return clientCore.deleteQuantityByItemCode(id);
		} catch (Exception e) {
			return false;
		}
	}

	public InvoiceItem getInvoiceItem(InvoiceDetail invoice, String productName, String status) {
		try {
			for (InvoiceItem invoiceItem : invoice.getItems()) {
				if (invoiceItem.getProductCode().equals(productName) && invoiceItem.getStatus().endsWith(status)) {
					return invoiceItem;
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	public InvoiceItemPromotion getInvoiceItemPromotion(Vector<InvoiceItem> items, String productName) {
		try {
			double quantity = 0;
			List<InvoiceItem> invoiceItems = new ArrayList<InvoiceItem>();
			for (int i = 0; i < items.size(); i++) {
				InvoiceItem invoiceItem = (InvoiceItem) items.elementAt(i);
				if (invoiceItem.getProductCode().equals(productName)
				    && !invoiceItem.getStatus().equals(AccountingModelManager.isCance)) {
					quantity = quantity + invoiceItem.getQuantity();
					invoiceItems.add(invoiceItem);
				}
			}

			return new InvoiceItemPromotion(quantity, invoiceItems);
		} catch (Exception e) {
			return null;
		}
	}

	public List<InvoiceItem> getInvoiceItemPrint(InvoiceDetail invoice) {
		try {
			List<String> listStr = new ArrayList<String>();
			List<InvoiceItem> invoiceItems = new ArrayList<InvoiceItem>();
			for (InvoiceItem invoiceItem : invoice.getItems()) {
				if (!invoiceItem.getStatus().equals(AccountingModelManager.isCance)) {
					if (listStr.indexOf(invoiceItem.getProductCode()) >= 0
					    && !invoiceItem.getStatus().equals(AccountingModelManager.isPromotion)) {
						InvoiceItem i = invoiceItems.get(listStr.indexOf(invoiceItem.getProductCode()));
						invoiceItems.get(listStr.indexOf(invoiceItem.getProductCode())).setQuantity(
						    i.getQuantity() + invoiceItem.getQuantity());
						invoiceItems.get(listStr.indexOf(invoiceItem.getProductCode())).setDiscountRate(
						    i.getDiscountRate() + invoiceItem.getDiscountRate());
						invoiceItems.get(listStr.indexOf(invoiceItem.getProductCode())).setTotal(
						    i.getTotal() + invoiceItem.getTotal() - i.getDiscount() - invoiceItem.getDiscount());

					} else {
						listStr.add(invoiceItem.getProductCode());
						InvoiceItem bean = new InvoiceItem();
						bean.setQuantity(invoiceItem.getQuantity());
						bean.setUnitPrice(invoiceItem.getUnitPrice());
						bean.setTotal(invoiceItem.getTotal());
						bean.setDiscount(invoiceItem.getDiscount());
						bean.setFinalCharge(invoiceItem.getFinalCharge());
						bean.setDiscountRate(invoiceItem.getDiscountRate());
						bean.setCurrencyUnit(invoiceItem.getCurrencyUnit());
						bean.setProductCode(invoiceItem.getProductCode());
						invoiceItems.add(bean);
					}
				}
			}
			return invoiceItems;
		} catch (Exception e) {
			return null;
		}
	}

	public ReportTable[] reportQuery(SQLSelectQuery[] query) throws Exception {
		return clientCore.reportQuery(query);
	}

	public InvoiceDetail findOneByInvoiceItem(long itemId) throws Exception {
		return clientCore.findOneByInvoiceItem(itemId);
	}

	public List<InvoiceItem> queryInvoiceItems(FilterQuery query) throws Exception {
		return clientCore.queryInvoiceItems(query);
	}

	public List<InvoiceDetail> searchInvoiceDetail(FilterQuery query) throws Exception {
		return clientCore.query(query).getData();
	}

	public void deleteTest(String test) throws Exception {
		clientCore.deleteTest(test);
	}

	public List<Invoice> searchInvoice(FilterQuery filterQuery) throws Exception {
		return clientCore.searchInvoice(filterQuery).getData();
	}

	public void dropTable(Class<?> type) throws Exception {
		clientCore.dropTable(type);
	}

	public void dropTable(String tableName) throws Exception {
		clientCore.dropTable(tableName);
	}

	// public String getCodeObject(String code1) {
	// try {
	// ManageCodes codes =
	// AccountingModelManager.getInstance().getCodesByCode(code1);
	// int a = codes.getPriority();
	// String code = String.valueOf(a);
	// a++;
	// codes.setPriority(a);
	// AccountingModelManager.getInstance().saveCodes(codes);
	// if (codes.getTypeCode().trim().isEmpty()) {
	// int l = code.length();
	// for (int i = l; i < 4; i++) {
	// code = "0" + code;
	// }
	// code = codes.getTypeCode() + code;
	// } else {
	// try {
	// code = new SimpleDateFormat(codes.getTypeCode()).format(new Date()) +
	// code;
	// } catch (Exception e) {
	// String str = "";
	// String str1 = "";
	// for (int j = 0; j < codes.getTypeCode().length(); j++) {
	// try {
	// if (codes.getTypeCode().substring(j, j + 1).equals("y")
	// || codes.getTypeCode().substring(j, j + 1).equals("M")
	// || codes.getTypeCode().substring(j, j + 1).equals("d")) {
	// str = new SimpleDateFormat(codes.getTypeCode().substring(j)).format(new
	// Date());
	// str1 = codes.getTypeCode().substring(0, j);
	//
	// break;
	// }
	//
	// } catch (Exception e2) {
	// }
	// }
	// code = str1 + str + code;
	// if (str.trim().isEmpty()) {
	// int l = code.length();
	// for (int i = l; i < 4; i++) {
	// code = "0" + code;
	// }
	// code = codes.getTypeCode() + code;
	// }
	// }
	// }
	// String str = DateUtil.asCompactDateId(new Date()) + ":";
	// return str + code;
	//
	// } catch (Exception e) {
	// return null;
	// }
	//
	// }

	public String getCodeObject(String code1, String type, Date date, boolean save) {
		try {
			ManageCodes codes = getCodesByCode(code1);
			String code;
			if (codes.getTypeCode().trim().isEmpty() || code1.equals(PanelManageCodes.SanPham)) {
				int a = codes.getPriority();
				code = String.valueOf(a);
				a++;
				if (save) {
					codes.setPriority(a);
					saveCodes(codes);
				}
				int l = code.length();
				for (int i = l; i < 4; i++) {
					code = "0" + code;
				}
				code = codes.getTypeCode() + code;
			} else {
				code = "1";
				String str = "";
				String str1 = "";
				for (int j = 0; j < codes.getTypeCode().length(); j++) {
					try {
						if (codes.getTypeCode().substring(j, j + 1).equals("y")
						    || codes.getTypeCode().substring(j, j + 1).equals("M")
						    || codes.getTypeCode().substring(j, j + 1).equals("d")) {
							str = new SimpleDateFormat(codes.getTypeCode().substring(j)).format(date);
							str1 = codes.getTypeCode().substring(0, j);
							int leng = str.length() + str1.length();
							if (code1.equals(PanelManageCodes.DuAn)) {
								List<Project> projects = RestaurantModelManager.getInstance().findProjectByCode(":" + str1 + str);
								if (projects.size() > 0) {
									code = String.valueOf(Integer.parseInt(projects.get(0).codeView().substring(leng)) + 1);
								}
							} else {
								if (code1.equals(PanelManageCodes.KhachHang)) {
									List<Customer> projects = CustomerModelManager.getInstance().findCustomerByCode(":" + str1 + str);
									if (projects.size() > 0) {
										code = String.valueOf(Integer.parseInt(projects.get(0).codeView().substring(leng)) + 1);
									}
								} else {
									if (code1.equals(PanelManageCodes.NhaCungCap)) {
										List<Supplier> projects = SupplierModelManager.getInstance().findSupplierByCode(":" + str1 + str);
										if (projects.size() > 0) {
											code = String.valueOf(Integer.parseInt(projects.get(0).codeView().substring(leng)) + 1);
										}
									} else {
										List<InvoiceDetail> projects = clientCore.findInvoiceDetailByCode(":" + str1 + str, type);
										if (projects.size() > 0) {
											int a = Integer.parseInt(projects.get(0).codeView().substring(leng)) + 1;
											for (InvoiceDetail in : projects) {
												int b = 0;
												try {
													b = Integer.parseInt(in.codeView().substring(leng)) + 1;
												} catch (Exception e) {
													b = 0;
												}
												if (b > a) {
													a = b;
												}
											}
											code = String.valueOf(a);
										}
									}

								}
							}
							break;
						}

					} catch (Exception e2) {
					}
				}

				if (str.trim().isEmpty()) {
					int a = codes.getPriority();
					code = String.valueOf(a);
					if (save) {
						a++;
						codes.setPriority(a);
						AccountingModelManager.getInstance().saveCodes(codes);
					}
					try {
						code = new SimpleDateFormat(codes.getTypeCode()).format(new Date()) + code;
					} catch (Exception e) {
						code = str1 + str + code;
						int l = code.length();
						for (int i = l; i < 4; i++) {
							code = "0" + code;
						}
						code = codes.getTypeCode() + code;
					}

				} else {
					code = str1 + str + code;
				}
			}

			String str = DateUtil.asCompactDateTimeId(new Date()) + ":";
			if (code1.equals(PanelManageCodes.SanPham)) {
				return code;
			}else {
				return str + code;
			}
			

		} catch (Exception e) {
			return null;
		}
		//
		// try {
		// ManageCodes codes = AccountingModelManager.getInstance()
		// .getCodesByCode(code1);
		// int a = codes.getPriority();
		// String code = String.valueOf(a);
		// a++;
		// codes.setPriority(a);
		// AccountingModelManager.getInstance().saveCodes(codes);
		// if (codes.getTypeCode().trim().isEmpty()) {
		// int l = code.length();
		// for (int i = l; i < 4; i++) {
		// code = "0" + code;
		// }
		// code = codes.getTypeCode() + code;
		// } else {
		// try {
		// code = new SimpleDateFormat(codes.getTypeCode())
		// .format(new Date()) + code;
		// } catch (Exception e) {
		// String str = "";
		// String str1 = "";
		// for (int j = 0; j < codes.getTypeCode().length(); j++) {
		// try {
		// if (codes.getTypeCode().substring(j, j + 1)
		// .equals("y")
		// || codes.getTypeCode().substring(j, j + 1)
		// .equals("M")
		// || codes.getTypeCode().substring(j, j + 1)
		// .equals("d")) {
		// str = new SimpleDateFormat(codes.getTypeCode()
		// .substring(j)).format(new Date());
		// str1 = codes.getTypeCode().substring(0, j);
		//
		// break;
		// }
		//
		// } catch (Exception e2) {
		// }
		// }
		// code = str1 + str + code;
		// if (str.trim().isEmpty()) {
		// int l = code.length();
		// for (int i = l; i < 4; i++) {
		// code = "0" + code;
		// }
		// code = codes.getTypeCode() + code;
		// }
		// }
		// }
		// String str = DateUtil.asCompactDateId(new Date()) + ":";
		// return str + code;
		//
		// } catch (Exception e) {
		// return null;
		// }

	}
	
	public void createParentBank(){
		List<Bank> lsBank = new ArrayList<Bank>();
		
		String [] tsnv = {"Tài sản", "Nguồn vốn"};
		String [] tsnvCode = {"ts", "nv"};
		
		String [] taiSan = {"Tài sản ngắn hạn", "Tài sản dài hạn"};
		String [] taiSanCode = {"ts.A", "ts.B"};
		
		String [] tsNganHan = {"Tài sản ngắn hạn", "Đầu tư tài chính ngắn hạn", "Các khoản phải thu ngắn hạn", "Hàng tồn kho", "Tài sản ngắn hạn khác"};
		String [] tsNganHanCode = {"ts.AI", "ts.AII", "ts.AIII", "ts.AIV", "ts.AV"};

		String [] tsDaiHan = {"Tài sản cố định", "Bất động sản đầu tư", "Các khoản đầu tư tài chính dài hạn", "Tài sản dài hạn khác"};
		String [] tsDaiHanCode = {"ts.BI", "ts.BII", "ts.BIII", "ts.BIV"};
		
		String [] nguonVon = {"Nợ phải trả", "Vốn chủ sở hữu"};
		String [] nguonVonCode = {"nv.A", "nv.B"};
		
		String [] nvNo = {"Nợ ngắn hạn", "Nợ dài hạn"};
		String [] nvNoCode = {"nv.AI", "nv.AII"};

		String [] nvVon = {"Vốn chủ sở hữu"};
		String [] nvVonCode = {"nv.BI"};
		
		autoCreatParentBank(lsBank, tsnv, tsnvCode, "this");
		autoCreatParentBank(lsBank, taiSan, taiSanCode, "ts");
		autoCreatParentBank(lsBank, tsNganHan, tsNganHanCode, "ts.A");
		autoCreatParentBank(lsBank, tsDaiHan, tsDaiHanCode, "ts.B");
		autoCreatParentBank(lsBank, nguonVon, nguonVonCode, "nv");
		autoCreatParentBank(lsBank, nvNo, nvNoCode, "nv.A");
		autoCreatParentBank(lsBank, nvVon, nvVonCode, "nv.B");
//		System.out.println(getBankByParentCode("ts.A"));
	}
	
	public void autoCreatParentBank(List<Bank> lsBank,String [] name, String code[], String parentCode){
		lsBank = getBankByParentCode(parentCode);
//		System.out.println(lsBank.size());
		if (lsBank.size()<name.length){
			for (int i=0; i<name.length; i++){
				Bank bank = new Bank();
				bank.setName(name[i]);
				bank.setShowBank(false);
				bank.setCode(code[i]);
				String [] arr = code[i].split("[.]");
				bank.setBankCode(arr[arr.length-1]);
				bank.setParentCode(parentCode);
				try {
					saveBank(bank);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void updateQuantityRealInvoceItem() {
		try {
			String sql = "update `invoiceItem` set `quantityReal` = `quantity` where"
			    + " activityType = \"Payment\" and `status`= \"Đã thanh toán\"";
			System.out.println(sql.toString());
			AccountingModelManager.getInstance().executeSQL(sql.toString());
		} catch (Exception e) {
		}

	}

	public void updateQuantityIdentityProduct() {
		try {
			String sql = "delete FROM `identityproductattribute` WHERE `identityproductId` in "
			    + "(select `id` from `identityproduct` where`invoiceCode` not in (select `invoiceCode` from `invoicedetail`))";
			System.out.println(sql.toString());
			AccountingModelManager.getInstance().executeSQL(sql.toString());
		} catch (Exception e) {
		}

		try {
			String sql = "delete FROM `identityproduct` WHERE `invoiceCode` not in (select `invoiceCode` from `invoicedetail`)";
			System.out.println(sql.toString());
			AccountingModelManager.getInstance().executeSQL(sql.toString());
		} catch (Exception e) {
		}

	}

	public void caculate(Date date, boolean warehouse, boolean quantitative) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 01);
			Calendar c1 = Calendar.getInstance();
			c1.setTime(date);
			c1.set(Calendar.HOUR_OF_DAY, 23);
			c1.set(Calendar.MINUTE, 59);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SQLSelectQuery rQuery1 = new SQLSelectQuery();

			rQuery1.table("InvoiceItem i ").field("i.id AS `Ma`", "value0").field("i.invoiceCode AS `SL`", "value1")
			    .field("i.productCode AS `SL1`", "value2").field("i.unitPrice AS `SL2`", "value3")
			    .field("i.quantity AS `SL3`", "value4").cond("i.activityType = 'Payment'")
			    .cond("i.typeReport = '" + InvoiceItem.Report + "'").cond("i.productCode is not null")
			    .cond("i.startDate >= '" + dateFormat.format(c.getTime()) + "'")
			    .cond("i.startDate <= '" + dateFormat.format(c1.getTime()) + "'").groupBy("i.id");
			System.out.println(rQuery1.createSQLQuery());
			ReportTable[] reportTable = reportQuery(new SQLSelectQuery[] { rQuery1 });
			String[] field = new String[rQuery1.getFields().size()];
			for (int i = 0; i < rQuery1.getFields().size(); i++) {
				field[i] = "value" + i;
			}
			reportTable[0].dumpEx(field);
			List<Map<String, Object>> records = reportTable[0].getRecords();
			Object[] values = new Object[field.length];
			for (int i = 0; i < records.size(); i++) {
				Map<String, Object> record = records.get(i);
				for (int j = 0; j < field.length; j++) {
					values[j] = record.get(field[j]);
				}
				if (values[2] != null) {
					String code = DateUtil.asCompactDateTimeId(new Date()) + i;
					List<IdentityProduct> identityProducts = WarehouseModelManager.getInstance().getByInvoiceItemIdImport(
					    Long.parseLong(values[0].toString()));
					if (identityProducts.size() < MyDouble.parseDouble(values[4].toString())) {
						for (int j = identityProducts.size(); j <= MyDouble.parseDouble(values[4].toString()); j++) {
							IdentityProduct identityProduct = new IdentityProduct();
							identityProduct.setInvoiceCode(values[1].toString());
							identityProduct.setInvoiceItemIdImport(Long.parseLong(values[0].toString()));
							identityProduct.setWarehouseCode(null);
							identityProduct.setProductCode(values[2].toString());
							identityProduct.setPrice(MyDouble.parseDouble(values[3].toString()));
							identityProduct.setUnitPrice("VND");
							identityProduct.setCurrencyRate(1);
							identityProduct.setShipmentImportCode(code);
							identityProduct.setImportDate(date);
							identityProduct.setImportType(ImportType.Import);
							identityProduct.setExportType(ExportType.NotExport);
							identityProduct = WarehouseModelManager.getInstance().saveIdentityProduct(identityProduct);
							System.out.println("Thanh cong " + values[0].toString());
						}
					} else {
						if (identityProducts.size() > MyDouble.parseDouble(values[4].toString())) {
							for (int j = new MyDouble(values[4].toString()).intValue(); j < identityProducts.size(); j++) {
								WarehouseModelManager.getInstance().deleteIdentityProduct(identityProducts.get(j));
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 01);
			Calendar c1 = Calendar.getInstance();
			c1.setTime(date);
			c1.set(Calendar.HOUR_OF_DAY, 23);
			c1.set(Calendar.MINUTE, 59);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SQLSelectQuery rQuery1 = new SQLSelectQuery();

			rQuery1.table("InvoiceItem i ").field("i.id AS `Ma`", "value0").field("i.invoiceCode AS `SL`", "value1")
			    .field("i.productCode AS `SL1`", "value2").field("i.unitPrice AS `SL2`", "value3")
			    .field("i.quantity AS `SL3`", "value4").field("i.startDate AS `SL4`", "value5")
			    .cond("i.activityType = 'Receipt'").cond("i.productCode is not null")
			    .cond("i.typeReport = '" + InvoiceItem.Report + "'")
			    .cond("i.startDate >= '" + dateFormat.format(c.getTime()) + "'")
			    .cond("i.startDate <= '" + dateFormat.format(c1.getTime()) + "'").groupBy("i.id");
			System.out.println(rQuery1.createSQLQuery());
			ReportTable[] reportTable = reportQuery(new SQLSelectQuery[] { rQuery1 });
			String[] field = new String[rQuery1.getFields().size()];
			for (int i = 0; i < rQuery1.getFields().size(); i++) {
				field[i] = "value" + i;
			}
			reportTable[0].dumpEx(field);
			List<Map<String, Object>> records = reportTable[0].getRecords();
			Object[] values = new Object[field.length];
			for (int i = 0; i < records.size(); i++) {
				Map<String, Object> record = records.get(i);
				for (int j = 0; j < field.length; j++) {
					values[j] = record.get(field[j]);
				}
				if (values[2] != null) {
					String code = DateUtil.asCompactDateTimeId(new Date()) + i;
					List<IdentityProduct> identityProducts = WarehouseModelManager.getInstance().findByProductCodeAndExportType(
					    values[2].toString());
					List<IdentityProduct> identityProducts1 = WarehouseModelManager.getInstance().getByInvoiceItemIdExport(
					    Long.parseLong(values[0].toString()));
					for (IdentityProduct t : identityProducts1) {
						WarehouseModelManager.getInstance().deleteIdentityProductExport(t);
					}
					double quantity = MyDouble.parseDouble(values[4].toString());
					int count = 0;
					if (identityProducts.size() > quantity) {
						for (IdentityProduct identityProduct : identityProducts) {
							try {
								identityProduct.setExportDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(values[5].toString()));
							} catch (Exception e) {
								identityProduct.setExportDate(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(values[5].toString()));
							}

							identityProduct.setShipmentExportCode(code);
							identityProduct.setExportType(ExportType.Export);
							identityProduct.setInvoiceExportCode(values[1].toString());
							identityProduct.setInvoiceItemIdExport(Long.parseLong(values[0].toString()));
							identityProduct.setCurrencyExportRate(1);
							identityProduct.setPriceExport(MyDouble.parseDouble(values[3].toString()));
							WarehouseModelManager.getInstance().saveIdentityProduct(identityProduct);
							count++;
							if (count == quantity) {
								break;
							}
							System.out.println("ok ok");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void caculateReport(String productCode, Date time1, boolean warehouse, boolean quantitative) throws Exception {
		// Calendar c = Calendar.getInstance();
		// c.setTime(time1);
		// c.set(Calendar.HOUR_OF_DAY, 23);
		// c.set(Calendar.MINUTE, 59);
		// c.set(Calendar.SECOND, 59);
		// Calendar c1 = Calendar.getInstance();
		// c1.setTime(time1);
		// c1.set(Calendar.HOUR_OF_DAY, 0);
		// c1.set(Calendar.MINUTE, 01);
		// double increaseQuantity = 0; // số lượng tăng
		// double increaseValue = 0; // giá trị tăng
		//
		// double reductionQuantity = 0;// Số lượng giảm
		// double reductionValue = 0; // Giá trị giảm
		//
		// double profitQuantity; // Lợi nhuận số lượng
		// double profitValue; // Lợi nhuận giá trị
		//
		// double reductionVitualQuantity = 0; // Số lượng ảo giảm
		// double reductionVitualValue = 0; // Giá trị ảo giảm
		// double profitVitualQuantity; // Lợi nhuận ảo số lượng
		// double profitVitualValue;
		// WarehouseInventory inventory =
		// WarehouseModelManager.getInstance().getInventoryByProduct(productCode,
		// new SimpleDateFormat("ddMMyyyy").format(time1));
		// if (inventory == null) {
		// inventory = new WarehouseInventory();
		// inventory.setCode(new SimpleDateFormat("ddMMyyyy").format(time1));
		// inventory.setStartDate(time1);
		// inventory.setProductCode(productCode);
		// inventory.setIncreaseQuantity(0);
		// inventory.setIncreaseValue(0);
		// inventory.setReductionVitualQuantity(0);
		// inventory.setReductionVitualValue(0);
		// inventory.setProfitVitualQuantity(0);
		// inventory.setProfitVitualValue(0);
		// } else {
		// increaseQuantity = inventory.getIncreaseQuantity();
		// increaseValue = inventory.getIncreaseValue();
		// reductionQuantity = inventory.getReductionQuantity();
		// reductionValue = inventory.getReductionValue();
		// }
		//
		// SimpleDateFormat dateFormat = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// if (quantitative) {
		// SQLSelectQuery rQuery4 = new SQLSelectQuery();
		//
		// rQuery4.table("InvoiceItem i ").field("COALESCE(SUM(i.quantity), 0) AS `SLTrongKyTang`",
		// "value0")
		// .field("COALESCE(SUM(i.total), 0) AS `SLTrongKyTang`", "value1")
		// .field("COALESCE(SUM(i.quantityReal), 0) AS `SLTrongKyTang1`", "value2")
		// .field("COALESCE(SUM(i.quantityReal*i.unitPrice), 0) AS `SLTrongKyTang1`",
		// "value3")
		// .cond("i.startDate >= '" + dateFormat.format(c1.getTime()) + "'")
		// .cond("i.startDate <= '" + dateFormat.format(c.getTime()) + "'")
		// .cond("i.productCode = '" + productCode + "'").cond("i.typeReport = '" +
		// InvoiceItem.Report + "'")
		// .cond("i.activityType = '" + "Receipt" + "'");
		// ReportTable[] reportTable4 = reportQuery(new SQLSelectQuery[] { rQuery4
		// });
		// String[] field4 = new String[rQuery4.getFields().size()];
		// for (int i = 0; i < rQuery4.getFields().size(); i++) {
		// field4[i] = "value" + i;
		// }
		// reportTable4[0].dumpEx(field4);
		// List<Map<String, Object>> records4 = reportTable4[0].getRecords();
		// for (int i = 0; i < records4.size(); i++) {
		// Map<String, Object> record = records4.get(i);
		// Object[] values = new Object[field4.length];
		// for (int j = 0; j < field4.length; j++) {
		// values[j] = record.get(field4[j]);
		// }
		// reductionVitualQuantity = MyDouble.parseDouble(values[0].toString());
		// reductionVitualValue = MyDouble.parseDouble(values[1].toString());
		// reductionQuantity = MyDouble.parseDouble(values[2].toString());
		// reductionValue = MyDouble.parseDouble(values[3].toString());
		// }
		//
		// Recipe recipe =
		// RestaurantModelManager.getInstance().getRecipeByProductCode(productCode);
		// if (recipe != null) {
		// List<RecipeIngredient> ingredients = recipe.getRecipeIngredients();
		// List<WarehouseInventory> warehouseInventories =
		// WarehouseModelManager.getInstance().findInventoryByCreatedBy(
		// String.valueOf(recipe.getId()));
		// for (RecipeIngredient recipeIngredient : ingredients) {
		// if (this.productCode != null) {
		// ProductCode pCode = new ProductCode();
		// pCode.setName(recipeIngredient.getProductCode());
		// this.productCode.add(pCode);
		// }
		// for (int j = 0; j < warehouseInventories.size();) {
		// if
		// (warehouseInventories.get(j).getModifiedBy().equals(String.valueOf(recipeIngredient.getId())))
		// {
		// warehouseInventories.remove(j);
		// } else {
		// j++;
		// }
		// }
		// WarehouseInventory inventory1 =
		// WarehouseModelManager.getInstance().getInventoryByProduct(
		// recipeIngredient.getProductCode(),
		// new SimpleDateFormat("ddMMyyyy").format(time1) +
		// String.valueOf(recipe.getId()));
		// if (inventory1 == null) {
		// inventory1 = new WarehouseInventory();
		// inventory1.setCode(new SimpleDateFormat("ddMMyyyy").format(time1) +
		// String.valueOf(recipe.getId()));
		// inventory1.setStartDate(time1);
		// inventory1.setProductCode(recipeIngredient.getProductCode());
		// inventory1.setIncreaseQuantity(0);
		// inventory1.setIncreaseValue(0);
		// }
		// inventory1.setCreatedBy(String.valueOf(recipe.getId()));
		// inventory1.setModifiedBy(String.valueOf(recipeIngredient.getId()));
		// inventory1.setReductionVitualQuantity(reductionVitualQuantity *
		// recipeIngredient.getQuantity());
		// inventory1.setReductionVitualValue(0);
		// inventory1.setProfitVitualQuantity(0 -
		// inventory1.getReductionVitualQuantity());
		// inventory1.setProfitVitualValue(0);
		//
		// WarehouseModelManager.getInstance().saveInventory(inventory1);
		// }
		// for (int j = 0; j < warehouseInventories.size(); j++) {
		// if (warehouseInventories.get(j).getIncreaseQuantity() == 0) {
		// WarehouseModelManager.getInstance().deleteWarehouseInventory(warehouseInventories.get(j));
		// }
		// }
		//
		// }
		// }
		//
		// if (warehouse) {
		// SQLSelectQuery rQuery1 = new SQLSelectQuery();
		// rQuery1.table("InvoiceItem i ").field("COALESCE(SUM(i.quantityReal), 0) AS `SLTrongKyTang`",
		// "value0")
		// .field("COALESCE(SUM(i.quantityReal*i.unitPrice), 0) AS `SLTrongKyTang`",
		// "value1")
		// .cond("i.startDate >= '" + dateFormat.format(c1.getTime()) + "'")
		// .cond("i.startDate <= '" + dateFormat.format(c.getTime()) + "'")
		// .cond("i.productCode = '" + productCode + "'").cond("i.activityType = '"
		// + "Payment" + "'");
		// ReportTable[] reportTable = reportQuery(new SQLSelectQuery[] { rQuery1
		// });
		// String[] field = new String[rQuery1.getFields().size()];
		// for (int i = 0; i < rQuery1.getFields().size(); i++) {
		// field[i] = "value" + i;
		// }
		// reportTable[0].dumpEx(field);
		// List<Map<String, Object>> records = reportTable[0].getRecords();
		// for (int i = 0; i < records.size(); i++) {
		// Map<String, Object> record = records.get(i);
		// Object[] values = new Object[field.length];
		// for (int j = 0; j < field.length; j++) {
		// values[j] = record.get(field[j]);
		// }
		// increaseQuantity = MyDouble.parseDouble(values[0].toString());
		// increaseValue = MyDouble.parseDouble(values[1].toString());
		// System.out.println(increaseQuantity);
		// }
		// }
		// profitQuantity = increaseQuantity - reductionQuantity;
		// profitValue = increaseValue - reductionValue;
		// profitVitualQuantity = increaseQuantity - reductionVitualQuantity;
		// profitVitualValue = increaseValue - reductionVitualValue;
		// inventory.setIncreaseQuantity(increaseQuantity);
		// inventory.setIncreaseValue(increaseValue);
		// inventory.setReductionQuantity(reductionQuantity);
		// inventory.setReductionValue(reductionValue);
		// inventory.setReductionVitualQuantity(reductionVitualQuantity);
		// inventory.setReductionVitualValue(reductionVitualValue);
		// inventory.setProfitQuantity(profitQuantity);
		// inventory.setProfitValue(profitValue);
		// inventory.setProfitVitualQuantity(profitVitualQuantity);
		// inventory.setProfitVitualValue(profitVitualValue);
		//
		// if (increaseQuantity == 0 && reductionQuantity == 0 &&
		// reductionVitualQuantity == 0) {
		// inventory.setIncreaseQuantity(0);
		// inventory.setIncreaseValue(0);
		// inventory.setReductionQuantity(0);
		// inventory.setReductionValue(0);
		// inventory.setReductionVitualQuantity(0);
		// inventory.setReductionVitualValue(0);
		// inventory.setProfitQuantity(0);
		// inventory.setProfitValue(0);
		// inventory.setProfitVitualQuantity(0);
		// inventory.setProfitVitualValue(0);
		// }
		//
		// WarehouseModelManager.getInstance().saveInventory(inventory);

	}

	public void caculateReportProfile(String productCode, String warehouseCode, Date time, boolean warehouse,
	    boolean quantitative) throws Exception {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		time = c.getTime();
		Calendar c1 = Calendar.getInstance();
		c1.setTime(time);
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 01);
		double increaseQuantity = 0; // số lượng tăng
		double increaseValue = 0; // giá trị tăng

		double reductionQuantity = 0;// Số lượng giảm
		double reductionValue = 0; // Giá trị giảm

		double profitQuantity; // Lợi nhuận số lượng
		double profitValue; // Lợi nhuận giá trị

		double reductionVitualQuantity = 0; // Số lượng ảo giảm
		double reductionVitualValue = 0; // Giá trị ảo giảm
		double profitVitualQuantity; // Lợi nhuận ảo số lượng
		double profitVitualValue;
		WarehouseProfile inventory = WarehouseModelManager.getInstance().getWarehouseProfileByProduct(productCode,
		    new SimpleDateFormat("ddMMyyyy").format(time) + warehouseCode);
		if (inventory == null) {
			inventory = new WarehouseProfile();
			inventory.setCode(new SimpleDateFormat("ddMMyyyy").format(time) + warehouseCode);
			inventory.setStartDate(time);
			inventory.setProductCode(productCode);
			inventory.setIncreaseQuantity(0);
			inventory.setIncreaseValue(0);
			inventory.setReductionVitualQuantity(0);
			inventory.setReductionVitualValue(0);
			inventory.setProfitVitualQuantity(0);
			inventory.setProfitVitualValue(0);
			inventory.setWarehouseCode(warehouseCode);
		} else {
			increaseQuantity = inventory.getIncreaseQuantity();
			increaseValue = inventory.getIncreaseValue();
			reductionQuantity = inventory.getReductionQuantity();
			reductionValue = inventory.getReductionValue();
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (quantitative) {
			SQLSelectQuery rQuery3 = new SQLSelectQuery();
			rQuery3.table("InvoiceItem i ").field("COALESCE(SUM(i.quantity), 0) AS `SLTrongKyTang`", "value0")
			    .field("COALESCE(SUM(i.total), 0) AS `SLTrongKyTang`", "value1")
			    .cond("i.startDate >= '" + dateFormat.format(c1.getTime()) + "'")
			    .cond("i.startDate <= '" + dateFormat.format(time) + "'").cond("i.productCode = '" + productCode + "'")
			    .cond("i.warehouseCode = '" + warehouseCode + "'").cond("i.typeReport = '" + InvoiceItem.Report + "'")
			    .cond("i.activityType = '" + "Receipt" + "'");
			ReportTable[] reportTable3 = reportQuery(new SQLSelectQuery[] { rQuery3 });
			String[] field3 = new String[rQuery3.getFields().size()];
			for (int i = 0; i < rQuery3.getFields().size(); i++) {
				field3[i] = "value" + i;
			}
			reportTable3[0].dumpEx(field3);
			List<Map<String, Object>> records3 = reportTable3[0].getRecords();
			for (int i = 0; i < records3.size(); i++) {
				Map<String, Object> record = records3.get(i);
				Object[] values = new Object[field3.length];
				for (int j = 0; j < field3.length; j++) {
					values[j] = record.get(field3[j]);
				}
				reductionVitualQuantity = MyDouble.parseDouble(values[0].toString());
				reductionVitualValue = MyDouble.parseDouble(values[1].toString());
				// System.out.println(warehouseCode+"   "+productCode+".........."+reductionVitualQuantity);
				// System.out.println(warehouseCode+".........."+reductionVitualValue);
				// System.out.println(warehouseCode+"................................");
			}
		}

		if (warehouse) {
			SQLSelectQuery rQuery2 = new SQLSelectQuery();
			rQuery2.table("IdentityProduct i ").field("count(i.id) AS `SLDauKy`", "value0")
			    .field("COALESCE(SUM(i.priceExport), 0) AS `SLTrongKyTang`", "value1").cond("i.exportDate is NOT NULL")
			    .cond("i.exportDate >= '" + dateFormat.format(c1.getTime()) + "'")
			    .cond("i.exportDate <= '" + dateFormat.format(time) + "'").cond("i.productCode = '" + productCode + "'")
			    .cond("i.warehouseCode = '" + warehouseCode + "'");
			ReportTable[] reportTable2 = reportQuery(new SQLSelectQuery[] { rQuery2 });
			String[] field2 = new String[rQuery2.getFields().size()];
			for (int i = 0; i < rQuery2.getFields().size(); i++) {
				field2[i] = "value" + i;
			}
			reportTable2[0].dumpEx(field2);
			List<Map<String, Object>> records2 = reportTable2[0].getRecords();
			for (int i = 0; i < records2.size(); i++) {
				Map<String, Object> record = records2.get(i);
				Object[] values = new Object[field2.length];
				for (int j = 0; j < field2.length; j++) {
					values[j] = record.get(field2[j]);
				}
				reductionQuantity = MyDouble.parseDouble(values[0].toString());
				reductionValue = MyDouble.parseDouble(values[1].toString());
				// System.out.println(warehouseCode+productCode+".........."+reductionQuantity);
				// System.out.println(warehouseCode+".........."+reductionValue);
				// System.out.println(warehouseCode+"................................");
			}

			// SQLSelectQuery rQuery1 = new SQLSelectQuery();
			//
			// rQuery1.table("IdentityProduct i JOIN IdentityProductAttribute p ON p.identityProductId = i.id")
			// .field("count(i.id) AS `SLDauKy`", "value0")
			// .field("COALESCE(SUM(i.price), 0) AS `SLTrongKyTang`", "value1")
			// // .cond("p.startDate <= '" + dateFormat.format(time) + "'")
			// .cond("(p.endDate is null or p.endDate >= '" +
			// dateFormat.format(c1.getTime()) + "')")
			// .cond("p.value = '" + warehouseCode + "'")
			// .cond("i.productCode = '" + productCode + "'");
			// ReportTable[] reportTable = reportQuery(new SQLSelectQuery[] {
			// rQuery1
			// });
			// String[] field = new String[rQuery1.getFields().size()];
			// for (int i = 0; i < rQuery1.getFields().size(); i++) {
			// field[i] = "value" + i;
			// }
			// reportTable[0].dumpEx(field);
			// List<Map<String, Object>> records = reportTable[0].getRecords();
			// for (int i = 0; i < records.size(); i++) {
			// Map<String, Object> record = records.get(i);
			// Object[] values = new Object[field.length];
			// for (int j = 0; j < field.length; j++) {
			// values[j] = record.get(field[j]);
			// }
			// increaseQuantity = MyDouble.parseDouble(values[0].toString());
			// increaseValue = MyDouble.parseDouble(values[1].toString());
			// System.out.println(warehouseCode+ "........" + productCode
			// +".........."+increaseQuantity);
			// System.out.println(warehouseCode+".........."+increaseValue);
			// System.out.println(warehouseCode+"................................!!!!!!!!");
			// }
		}

		profitQuantity = increaseQuantity - reductionQuantity;
		profitValue = increaseValue - reductionValue;
		profitVitualQuantity = increaseQuantity - reductionVitualQuantity;
		profitVitualValue = increaseValue - reductionVitualValue;
		inventory.setIncreaseQuantity(increaseQuantity);
		inventory.setIncreaseValue(increaseValue);
		inventory.setReductionQuantity(reductionQuantity);
		inventory.setReductionValue(reductionValue);
		inventory.setReductionVitualQuantity(reductionVitualQuantity);
		inventory.setReductionVitualValue(reductionVitualValue);
		inventory.setProfitQuantity(profitQuantity);
		inventory.setProfitValue(profitValue);
		inventory.setProfitVitualQuantity(profitVitualQuantity);
		inventory.setProfitVitualValue(profitVitualValue);
		WarehouseModelManager.getInstance().saveWarehouseProfile(inventory);

	}

	public List<ManageCodes> getAllCodes() throws Exception {

		return clientCore.getAllCodes();
	}

	public ManageCodes saveCodes(ManageCodes codes) throws Exception {
		codes = clientCore.saveCodes(codes);
		return codes;
	}

	public boolean deleteCodes(ManageCodes codes) throws Exception {
		clientCore.deleteCodes(codes);
		return true;
	}

	public ManageCodes getCodesByCode(String code) {
		try {
			ManageCodes manageCodes = clientCore.getCodesByCode(code);
			if (profile.get("AutoCode") == null) {
				if (manageCodes == null) {
					if (code.equals(PanelManageCodes.InvoiceBH)) {
						manageCodes = new ManageCodes();
						manageCodes.setCode(code);
						manageCodes.setTypeCode("BH");
						manageCodes.setManageType(ManageType.Circle);
						manageCodes.setRotationType(RotationType.ByYear);
						manageCodes = saveCodes(manageCodes);
					}
					if (code.equals(PanelManageCodes.InvoiceTC)) {
						manageCodes = new ManageCodes();
						manageCodes.setCode(code);
						manageCodes.setTypeCode("TC");
						manageCodes.setManageType(ManageType.Circle);
						manageCodes.setRotationType(RotationType.ByYear);
						manageCodes = saveCodes(manageCodes);
					}
					if (code.equals(PanelManageCodes.InvoiceDMH)) {
						manageCodes = new ManageCodes();
						manageCodes.setCode(code);
						manageCodes.setTypeCode("DMH");
						manageCodes.setManageType(ManageType.Circle);
						manageCodes.setRotationType(RotationType.ByYear);
						manageCodes = saveCodes(manageCodes);
					}
					if (code.equals(PanelManageCodes.KhachHang)) {
						manageCodes = new ManageCodes();
						manageCodes.setCode(code);
						manageCodes.setTypeCode("KHDT");
						manageCodes.setManageType(ManageType.Circle);
						manageCodes.setRotationType(RotationType.ByYear);
						manageCodes = saveCodes(manageCodes);
					}
					if (code.equals(PanelManageCodes.DuAn)) {
						manageCodes = new ManageCodes();
						manageCodes.setCode(code);
						manageCodes.setTypeCode("yyyyMM");
						manageCodes.setManageType(ManageType.Circle);
						manageCodes.setRotationType(RotationType.ByYear);
						manageCodes = saveCodes(manageCodes);
					}
				}
				profile.put("AutoCode", "true");
				AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(),
				    profile);
			}
			return manageCodes;
		} catch (Exception e) {
			return null;
		}

	}
	/** ----Bank ----
	 * getAllBank
	 * getByBankName
	 * saveBank
	 * getBankByCode
	 * getBanks
	 * getBankByParentCode
	 * getBankOnParentCode
	 * deleteBank
	 */
	
	public List<Bank> getAllBank(){
		try {
			return clientCore.getAllBank();
    } catch (Exception e) {
	    return new ArrayList<Bank>();
    	}
	}

	public List<Bank> getByBankName(String name){
		try {
			return clientCore.getByBankName(name);
    } catch (Exception e) {
	    return new ArrayList<Bank>();
    	}
	}
	
	public List<Bank> getBankByParentCode(String parentCode){
		try {
			return clientCore.getBankByParentCode(parentCode);
    } catch (Exception e) {
	    return new ArrayList<Bank>();
    	}
	}
	
	public Bank findByBankCode(String bankCode){
		try {
			return clientCore.findByBankCode(bankCode);
		} catch (Exception e) {
			return new Bank();
    	}
	}
	
	public Bank getBankByCode(String code) throws Exception {
		return clientCore.getBankByCode(code);
	}
	
	public Bank saveBank(Bank bank) throws Exception{
		bank = clientCore.saveBank(bank);
		Thread t = new Thread(){
		public void run(){
			try {
				ScreenOften.getInstance().setResetProduct(true);
			} catch (Exception e) {
				System.out.println("Loi load Table");
				}
			};
		};
		t.start();
		return bank;
	}
	
	public List<Bank> getBanks(int type)  {
		try {
			List<Bank> banks = new ArrayList<Bank>();
			switch (type){
			//Lấy về getShowBank = true;
			case 0:
				banks = clientCore.getAllBank();
				for (int i=banks.size()-1; i >= 0; i--){
					if (banks.get(i).isShowBank()==false){
						banks.remove(i);
					}
				}
				break;
			//Lấy cha to nhất
			case 1:
				banks = clientCore.getBankByParentCode("this");
				break;
			//Lấy cha cấp 1
			case 2:
				banks = clientCore.getBankByParentCode("ts");
//				for (int i=banks.size()-1; i >= 0; i--){
//					if (banks.get(i).getCode().length()>4){
//						banks.remove(i);
//					}
//				}
				break;
			//Lấy cha cấp 1
			case 3:
				banks = clientCore.getBankByParentCode("nv");
//				for (int i=banks.size()-1; i >= 0; i--){
//					if (banks.get(i).getCode().length()>4){
//						banks.remove(i);
//					}
//				}
				break;
			//Lấy cha cấp 2
			case 4:
				banks = clientCore.getBankByParentCode("ts.A");
//				for	(int i=banks.size()-1; i >= 0; i--){
//					String [] arr = banks.get(i).getCode().split("[.]");
//					if (arr.length>2){
//						banks.remove(i);
//					}
//				}
				break;
			case 5:
				banks = clientCore.getBankByParentCode("ts.B");
//				for	(int i=banks.size()-1; i >= 0; i--){
//					String [] arr = banks.get(i).getCode().split("[.]");
//					if (arr.length>2){
//						banks.remove(i);
//					}
//				}
				break;
			//Lấy cha cấp 2
			case 6:
				banks = clientCore.getBankByParentCode("nv.A");
//				for	(int i=banks.size()-1; i >= 0; i--){
//					String [] arr = banks.get(i).getCode().split("[.]");
//					if (arr.length>2){
//						banks.remove(i);
//					}
//				}
				break;
			case 7:
				banks = clientCore.getBankByParentCode("nv.B");
//				for	(int i=banks.size()-1; i >= 0; i--){
//					String [] arr = banks.get(i).getCode().split("[.]");
//					if (arr.length>2){
//						banks.remove(i);
//					}
//				}
				break;
			case 8:
				banks = clientCore.getBankByParentCode("ts.AI");
//				for	(int i=banks.size()-1; i >= 0; i--){
//					String [] arr = banks.get(i).getCode().split("[.]");
//					if (arr.length<2){
//						banks.remove(i);
//					}
//				}
				break;
			case 9:
				banks = clientCore.getBankByParentCode("ts.AII");
				break;
			case 10:
				banks = clientCore.getBankByParentCode("ts.AIII");
				break;
			case 11:
				banks = clientCore.getBankByParentCode("ts.AIV");
				break;
			case 12:
				banks = clientCore.getBankByParentCode("ts.AV");
				break;
			case 13:
				banks = clientCore.getBankByParentCode("ts.BI");
				break;
			case 14:
				banks = clientCore.getBankByParentCode("ts.BII");
				break;
			case 15:
				banks = clientCore.getBankByParentCode("ts.BIII");
				break;
			case 16:
				banks = clientCore.getBankByParentCode("ts.BIV");
				break;
			case 17:
				banks = clientCore.getBankByParentCode("nv.AI");
				break;
			case 18:
				banks = clientCore.getBankByParentCode("nv.AII");
				break;
			case 19:
				banks = clientCore.getBankByParentCode("nv.BI");
				break;
			case 20:
				banks = new ArrayList<Bank>();
			}

			Collections.sort(banks, new Comparator<Bank>() {
				@Override
				public int compare(Bank it1, Bank it2) {
					if (it1.getIndex() > it2.getIndex()) {
						return 1;
					} else {
						if (it1.getIndex() < it2.getIndex()) {
							return -1;
						} else {
							return 0;
						}
					}

				}
			});
			return banks;
    } catch (Exception e) {
	    return new ArrayList<Bank>();
    	}
	}
	
	public boolean deleteBank(Bank bank) throws Exception{
		clientCore.deleteBank(bank);
		Thread t = new Thread(){
			public void run(){
				try {
					ScreenOften.getInstance().setResetProduct(true);
				} catch (Exception e) {
					System.out.println("Loi load table");
				}
			};
		};
		t.start();
		return true;
	}
	
	/** ----BankTransaction ----
	 * getAllBankTransaction
	 * getByBankTransactionName
	 * getByBankRangeTotal
	 * getByBankCode
	 * getTracsactionCode
	 * getByBankType
	 * saveBankTransaction
	 * getBankTransactionByCode
	 * deleteBankTransaction
	 */
	
	public List<BankTransaction> getAllBankTransaction(){
		try {
			return clientCore.getAllBankTransaction();
    } catch (Exception e) {
	    return new ArrayList<BankTransaction>();
    	}
	}
	
	public List<BankTransaction> getByBankTransactionName(String name){
		try {
			return clientCore.getByBankTransactionName(name);
    } catch (Exception e) {
	    return new ArrayList<BankTransaction>();
    	}
	}
	
	public List<BankTransaction> getByBankRangeTotal(long total){
		try {
			return clientCore.getByBankRangeTotal(total);
    } catch (Exception e) {
	    return new ArrayList<BankTransaction>();
    	}
	}
	
	public List<BankTransaction> getByBankCode(String bankCode){
		try {
			return clientCore.getByBankCode(bankCode);
    } catch (Exception e) {
	    return new ArrayList<BankTransaction>();
    	}
	}
	
	public List<BankTransaction> getTracsactionCode(String tracsactionCode){
		try {
			return clientCore.getTracsactionCode(tracsactionCode);
    } catch (Exception e) {
	    return new ArrayList<BankTransaction>();
    	}
	}
	
	public List<BankTransaction> getByBankType(boolean type){
		try {
			return clientCore.getByBankType(type);
    } catch (Exception e) {
	    return new ArrayList<BankTransaction>();
    	}
	}
	
	public BankTransaction saveBankTransaction(BankTransaction bankTransaction) throws Exception{
		bankTransaction = clientCore.saveBankTransaction(bankTransaction);
		Thread t = new Thread(){
			public void run(){
				try {
					ScreenOften.getInstance().setResetProduct(true);
				} catch(Exception e) {
					System.out.println("Loi load Table");
				}
			};
		};
		t.start();
		return bankTransaction;
	}
	
	public List<BankTransaction> getBankTransactionByCode(String code){
		try {
			return clientCore.getBankTransactionByCode(code);
    	} catch (Exception e) {
    		return new ArrayList<BankTransaction>();
    	}
	}
	
	public boolean deleteBankTransaction(BankTransaction bankTransaction) throws Exception{
		clientCore.deleteBankTransaction(bankTransaction);
		Thread t = new Thread(){
			public void run(){
				try {
					ScreenOften.getInstance().setResetProduct(true);
				} catch (Exception e) {
					System.out.println("Loi load Table");
				}
			};
		};
		t.start();
		return true;
	}
}
