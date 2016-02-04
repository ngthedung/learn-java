package com.hkt.client.swingexp.app.hethong;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.khachhang.BasicInformation;
import com.hkt.client.swingexp.app.khachhang.OrganizationBasic;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.Contact;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.account.entity.Profiles;
import com.hkt.module.account.entity.Account.Type;
import com.hkt.module.accounting.DefaultInvoiceCalculator;
import com.hkt.module.accounting.entity.Invoice.ActivityType;
import com.hkt.module.accounting.entity.Invoice.Status;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceTransaction;
import com.hkt.module.accounting.entity.InvoiceTransaction.TransactionType;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.restaurant.entity.Project;
import com.hkt.util.text.DateUtil;

public class DataExcel {
	private Account account;
	private Customer customer;
	private OrganizationBasic organizationBasic;
	private BasicInformation basicInformation;
	private Contact contact;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	public DataExcel() {

	}

	public void runImport(String fileName) throws Exception {
		try {
			// Create the input stream from the xlsx/xls file
			FileInputStream fis = new FileInputStream(fileName);
			// Create Workbook instance for xlsx/xls file input stream
			Workbook workbook = null;
			if (fileName.toLowerCase().endsWith("xlsx")) {
				workbook = new XSSFWorkbook(fis);
			} else if (fileName.toLowerCase().endsWith("xls")) {
				workbook = new HSSFWorkbook(fis);
			}
			// Get the number of sheets in the xlsx file
			int numberOfSheets = workbook.getNumberOfSheets();
			// // loop through each of the sheets
//			 for (int i = 0; i < numberOfSheets; i++) {
//			 Sheet sheet = workbook.getSheetAt(i);
//			 // map sheet to table
//			 MapTableProject(sheet);
//			 // break;
//			 }

//			 for (int i = 0; i < numberOfSheets; i++) {
//			 Sheet sheet = workbook.getSheetAt(i);
//			 MapTableInvoice(sheet);
//			 // break;
//			 }
			 for (int i = 0; i < numberOfSheets; i++) {
			 Sheet sheet = workbook.getSheetAt(i);
			 // map sheet to table
			 MapTableOrganization(sheet);
			 // break;
			 }
//			for (int i = 0; i < numberOfSheets; i++) {
//				Sheet sheet = workbook.getSheetAt(i);
//				MapTableUser(sheet);
//				// break;
//			}

			fis.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// sheet Project
	private void MapTableProject(Sheet sheet) {
		System.out.println("Table : " + sheet.getSheetName());
		String tableName = sheet.getSheetName();
		if (tableName.equals("Du an")) {
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				// Get the row object

				Project project = new Project();

				Row row = rowIterator.next();//
				// Every row has columns, get the column iterator and iterate over
				// them
				Iterator<Cell> cellIterator = row.cellIterator();
				if (row.getRowNum() > 0) {
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						if (cell.getRow().getRowNum() == 0) {
							System.out.println("---- Column name:" + cell.getNumericCellValue());
						} else {
							if (cell.getColumnIndex() == 2) {

								try {

									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										System.out.println("STT " + cell.getRowIndex());
										System.out.println("---- Parent Project: " + cell.getNumericCellValue());
										if (String.valueOf(cell.getNumericCellValue()) != null) {
											project.setParentCode(String.valueOf(cell.getNumericCellValue()));
										}
									} else {
										System.out.println("STT " + cell.getRowIndex());
										System.out.println("---- Parent Project: " + cell.getStringCellValue());
										if (cell.getStringCellValue() != null) {
											project.setParentCode(cell.getStringCellValue());
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							if (cell.getColumnIndex() == 1) {
								System.out.println("---- Name Project: " + cell.getStringCellValue());
								project.setName(cell.getStringCellValue());
								if (project.getName().trim().length() == 0) {
									continue;
								}
							}

							if (cell.getColumnIndex() == 0) {
								try {

									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {

										project.setCode(String.valueOf(cell.getNumericCellValue()));

									} else {
										project.setCode(cell.getStringCellValue());
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							if (cell.getColumnIndex() == 3) {
								System.out.println("---- Description: " + cell.getStringCellValue());
								project.setDepartmentPart(cell.getStringCellValue());
							}

							if (cell.getColumnIndex() == 4) {
								System.out.println("---- Description: " + cell.getStringCellValue());
								project.setDescription(cell.getStringCellValue());
							}

						}

					}
					try {
						if (checkProject(project)) {
							project.setStatus("Đã hoàn thành");
							RestaurantModelManager.getInstance().saveProject(project);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private boolean checkProject(Project project) {
		if (project == null)
			return false;
		if (project.getName() == null)
			return false;
		if (project.getCode() == null)
			return false;
		if (project.getName().trim().length() == 0)
			return false;
		if (project.getCode().trim().length() == 0)
			return false;
		return true;
	}

	// sheet Invoice
	private void MapTableInvoice(Sheet sheet) {

		System.out.println("Table : " + sheet.getSheetName());
		String tableName = sheet.getSheetName();
		if (tableName.equals("invoice")) {
			try {
				AccountGroup acc1 = new AccountGroup();
				acc1.setName("Hành Chính");
				acc1.setLabel("Hành Chính");
				acc1.setOwner(ManagerAuthenticate.getInstance().getOrganizationLoginId());
				acc1.setParent(HRModelManager.getInstance().getRootDepartment());
				AccountModelManager.getInstance().saveGroup(acc1);

				AccountGroup acc2 = new AccountGroup();
				acc2.setName("Hội Đồng Quản Trị");
				acc2.setLabel("Hội Đồng Quản Trị");
				acc2.setOwner(ManagerAuthenticate.getInstance().getOrganizationLoginId());
				acc2.setParent(HRModelManager.getInstance().getRootDepartment());
				AccountModelManager.getInstance().saveGroup(acc2);

				AccountGroup acc3 = new AccountGroup();
				acc3.setName("Kinh Doanh");
				acc3.setLabel("Kinh Doanh");
				acc3.setOwner(ManagerAuthenticate.getInstance().getOrganizationLoginId());
				acc3.setParent(HRModelManager.getInstance().getRootDepartment());
				AccountModelManager.getInstance().saveGroup(acc3);

				AccountGroup acc4 = new AccountGroup();
				acc4.setName("Ban Lãnh Đạo");
				acc4.setLabel("Ban Lãnh Đạo");
				acc4.setOwner(ManagerAuthenticate.getInstance().getOrganizationLoginId());
				acc4.setParent(HRModelManager.getInstance().getRootDepartment());
				AccountModelManager.getInstance().saveGroup(acc4);

				AccountGroup acc5 = new AccountGroup();
				acc5.setName("Nghiên Cứu");
				acc5.setLabel("Nghiên Cứu");
				acc5.setOwner(ManagerAuthenticate.getInstance().getOrganizationLoginId());
				acc5.setParent(HRModelManager.getInstance().getRootDepartment());
				AccountModelManager.getInstance().saveGroup(acc5);

			} catch (Exception e1) {
				e1.printStackTrace();
			}
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				// Get the row object
				InvoiceDetail invoicedetail = new InvoiceDetail();
				invoicedetail.setType(AccountingModelManager.typeThuChi);
				invoicedetail.setStatus(Status.Paid);
				invoicedetail.setCurrencyUnit("VND");
				invoicedetail.setCurrencyRate(1);

				Row row = rowIterator.next();//
				// Every row has columns, get the column iterator and iterate over
				// them
				Iterator<Cell> cellIterator = row.cellIterator();
				if (row.getRowNum() > 0) {
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();

						if (cell.getRow().getRowNum() == 0) {
							System.out.println("---- Column name:" + cell.getNumericCellValue());
						} else {
							if (cell.getColumnIndex() == 0) {
								// Tên phòng

								try {
									// System.out.println("STT " + cell.getRowIndex());
									// System.out.println("---- Accountgroup name: " +
									// cell.getStringCellValue());
									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										// AccountGroup accountGroup =
										// AccountModelManager.getInstance().getGroupByName(String.valueOf(cell.getNumericCellValue()));
										// System.out.println("---- aaaaaaaaaaaa:" +
										// cell.getNumericCellValue()+"      "+accountGroup.getPath());
										String path = "hkt/Phòng ban/" + String.valueOf(cell.getNumericCellValue());
										invoicedetail.setDepartmentCode(path);

									} else {
										// AccountGroup accountGroup =
										// AccountModelManager.getInstance().getGroupByName(cell.getStringCellValue());
										// System.out.println("---- aaaaaaaaaaaa:" +
										// cell.getStringCellValue()+"      "+accountGroup.getPath());
										String path = "hkt/Phòng ban/" + cell.getStringCellValue();
										invoicedetail.setDepartmentCode(path);
									}

								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							if (cell.getColumnIndex() == 1) {
								// Mã dự án

								try {
									if (cell.getStringCellValue() != null && !cell.getStringCellValue().equals("")) {
										// System.out.println("---- Project code: " +
										// cell.getStringCellValue());
										invoicedetail.setProjectCode("/" + cell.getStringCellValue());
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							// Operation Name
							if (cell.getColumnIndex() == 2) {
								try {
									System.out.println("---- Operation Name: " + cell.getStringCellValue());
									if (cell.getStringCellValue().length() > 100) {
										invoicedetail.setInvoiceName(cell.getStringCellValue().substring(0, 100));
									} else {
										invoicedetail.setInvoiceName(cell.getStringCellValue());
									}

									if (invoicedetail.getInvoiceName().trim().length() == 0) {
										continue;
									}
								} catch (Exception e) {
									e.printStackTrace();
								}

							}

							// Operation code
							if (cell.getColumnIndex() == 3) {
								try {
									String str = DateUtil.asCompactDateId(new Date()) + ":";
									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										// System.out.println("---- Operation Code: " +
										// cell.getNumericCellValue());
										invoicedetail.setInvoiceCode("TC" + str + "TC" + String.valueOf(cell.getNumericCellValue()));

									} else {
										// System.out.println("---- Operation Code2: " +
										// cell.getStringCellValue());
										invoicedetail.setInvoiceCode("TC" + str + "TC" + cell.getStringCellValue());
									}

								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							// description
							if (cell.getColumnIndex() == 4) {
								// System.out.println("---- Description: " +
								// cell.getStringCellValue());
								// invoicedetail.setDescription(String.valueOf(cell.getStringCellValue()));
							}

							// idOperationType
							if (cell.getColumnIndex() == 5) {
								try {
									// System.out.println("---- ActivityType: " +
									// cell.getStringCellValue());
									if (cell.getStringCellValue().equals("5")) {
										invoicedetail.setActivityType(ActivityType.Receipt);
									} else if (cell.getStringCellValue().equals("6")) {
										invoicedetail.setActivityType(ActivityType.Payment);
									} else {
										invoicedetail.setActivityType(ActivityType.Payment);
										invoicedetail.setType(AccountingModelManager.typeSanXuat);
									}

								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							// TotalBeforeDiscount
							if (cell.getColumnIndex() == 6) {
								try {
									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										// System.out.println("---- Total: " +
										// cell.getNumericCellValue());
										invoicedetail.setTotal(cell.getNumericCellValue());

									} else {
										// System.out.println("---- Total: " +
										// cell.getStringCellValue());
										invoicedetail.setTotal(Double.parseDouble(cell.getStringCellValue()));

									}

								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							// PercenDiscount
							if (cell.getColumnIndex() == 7) {
								try {
									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										// System.out.println("---- Percent Discount: " +
										// cell.getNumericCellValue());
										invoicedetail.setDiscountRate(cell.getNumericCellValue());

									} else {
										// System.out.println("---- Percent Discount: " +
										// cell.getStringCellValue());
										invoicedetail.setDiscountRate(Double.parseDouble(cell.getStringCellValue()));
									}

								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							// MoneyDiscount
							if (cell.getColumnIndex() == 8) {
								try {
									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										// System.out.println("---- MoneyDiscount: " +
										// cell.getNumericCellValue());
										invoicedetail.setDiscount(cell.getNumericCellValue());

									} else {
										// System.out.println("---- MoneyDiscount: " +
										// cell.getStringCellValue());
										invoicedetail.setDiscount(Double.parseDouble(cell.getStringCellValue()));
									}

								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							// MoneyTax
							if (cell.getColumnIndex() == 9) {
								try {
									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										// System.out.println("---- Total Money After Tax " +
										// cell.getNumericCellValue());
										invoicedetail.setTotalTax(cell.getNumericCellValue());

									} else {
										// System.out.println("---- Total Money After Tax " +
										// cell.getStringCellValue());
										invoicedetail.setTotalTax(Double.parseDouble(cell.getStringCellValue()));
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							// TotalMoneyAfterDiscount
							if (cell.getColumnIndex() == 10) {

							}

							// TotalMoneyAfterTax
							if (cell.getColumnIndex() == 11) {
								try {
									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										// System.out.println("---- Total Money After tax " +
										// cell.getNumericCellValue());
										invoicedetail.setFinalCharge(cell.getNumericCellValue());

									} else {
										// System.out.println("---- Total Money After tax " +
										// cell.getStringCellValue());
										invoicedetail.setFinalCharge(Double.parseDouble(cell.getStringCellValue()));
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							// DateExcute
							if (cell.getColumnIndex() == 12) {
								try {

									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										// System.out.println("---- Date Execute " +
										// cell.getNumericCellValue());

										Date startDate = df.parse(String.valueOf(cell.getNumericCellValue()));
										// System.out.println(startDate);
										invoicedetail.setStartDate(startDate);
										invoicedetail.setEndDate(startDate);
									} else {
										Date startDate = df.parse(cell.getStringCellValue());
										// System.out.println(startDate);
										invoicedetail.setStartDate(startDate);
										invoicedetail.setEndDate(startDate);
									}

								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}

					}
					try {
						if (checkInvoiDetail(invoicedetail)) {
							// invoicedetail.calculate(new DefaultInvoiceCalculator());
							invoicedetail.setTotalPaid(invoicedetail.getFinalCharge());
							InvoiceTransaction transactions = new InvoiceTransaction();
							transactions.setTransactionType(TransactionType.CreditCard);
							transactions.setDepartmentCode(invoicedetail.getDepartmentCode());
							transactions.setLocationCode(invoicedetail.getLocationCode());
							transactions.setTableCode(invoicedetail.getTableCode());
							transactions.setCustomerCode(invoicedetail.getCustomerCode());
							transactions.setProjectCode(invoicedetail.getProjectCode());
							transactions.setCreatedBy(invoicedetail.getDepartmentCode());
							transactions.setCurrencyRate(1);
							transactions.setCurrencyUnit(invoicedetail.getCurrencyUnit());
							transactions.setTotal(invoicedetail.getFinalCharge());
							transactions.setTransactionDate(invoicedetail.getStartDate());
							if (invoicedetail.getActivityType().equals(ActivityType.Receipt)) {
								transactions.setActivityType(InvoiceTransaction.ActivityType.Receipt);
							} else if (invoicedetail.getActivityType().equals(ActivityType.Payment)) {
								transactions.setActivityType(InvoiceTransaction.ActivityType.Payment);
							} else {
								transactions.setActivityType(null);
							}

							invoicedetail.add(transactions);
							invoicedetail.setLocationCode("other");
							invoicedetail.setTableCode("other");
							invoicedetail.setCustomerCode("hkt/Khách hàng/groupCustomer-other");
							// if
							// (invoicedetail.getType().equals(AccountingModelManager.typeSanXuat))
							// {
							AccountingModelManager.getInstance().saveInvoice(invoicedetail);
							// }

						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private boolean checkInvoiDetail(InvoiceDetail invoicedetail) {
		if (invoicedetail == null)
			return false;
		if (invoicedetail.getInvoiceCode() == null)
			return false;
		if (invoicedetail.getInvoiceName() == null)
			return false;
		if (invoicedetail.getInvoiceName().trim().length() == 0)
			return false;
		if (invoicedetail.getInvoiceCode().trim().length() == 0)
			return false;
		if (invoicedetail.getActivityType() == null)
			return false;
		if (invoicedetail.getType() == null)
			return false;
		if (invoicedetail.getStatus() == null)
			return false;

		return true;
	}

	// sheet DN
	private void MapTableOrganization(Sheet sheet) throws IOException {
		System.out.println("Table : " + sheet.getSheetName());
		String tableName = sheet.getSheetName();
		List<String> list = new ArrayList<String>();
		if (tableName.equals("DN")) {
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				// Get the row object
				account = new Account();
				account.setLoginId(new Date().getTime() + "");
				account.setPassword("0000");
				account.setType(Type.ORGANIZATION);
				account.setLastLoginTime(new Date());
				organizationBasic = new OrganizationBasic();

				contact = new Contact();

				customer = new Customer();
				customer.setOrganizationLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());

				Row row = rowIterator.next();//
				// Every row has columns, get the column iterator and iterate over
				// them
				Iterator<Cell> cellIterator = row.cellIterator();
				if (row.getRowNum() > 0) {
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();

						if (cell.getRow().getRowNum() == 0) {
							System.out.println("---- Column name:" + cell.getStringCellValue());
						} else {
							if (cell.getColumnIndex() == 0) {
								// enterpriseName

								try {
									System.out.println("STT " + cell.getRowIndex());
									System.out.println("---- Enterprise Name: " + cell.getStringCellValue());
									if (cell.getStringCellValue() != null && !cell.getStringCellValue().equals("")) {
										customer.setName(cell.getStringCellValue());
										organizationBasic.setName(cell.getStringCellValue());
									}

								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							if (cell.getColumnIndex() == 1) {
								// enterpriseCode

								try {
									
									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										System.out.println("---- Enterprise code: " + cell.getNumericCellValue());
										if(list.indexOf(String.valueOf(cell.getNumericCellValue()))>0){
											customer.setCode(String.valueOf(new Date().getTime()));
										}else {
											customer.setCode(String.valueOf(cell.getNumericCellValue()));
											list.add(customer.getCode());
										}
									} else {
										System.out.println("---- Enterprise code: " + cell.getStringCellValue());
										if(list.indexOf( cell.getStringCellValue())>0){
											customer.setCode(String.valueOf(new Date().getTime()));
										}else {
											customer.setCode(cell.getStringCellValue());
											list.add(customer.getCode());
										}
										
									}

								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							// Slogan
							if (cell.getColumnIndex() == 2) {
								try {
									System.out.println("---- Slogan: " + cell.getStringCellValue());
									organizationBasic.setSlogan(cell.getStringCellValue());

								} catch (Exception e) {
									e.printStackTrace();
								}

							}

							// Address
							if (cell.getColumnIndex() == 3) {
								try {
									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										System.out.println("---- Address: " + cell.getNumericCellValue());
										contact.setAddressNumber(String.valueOf(cell.getNumericCellValue()));
									} else {
										System.out.println("---- Address: " + cell.getStringCellValue());
										contact.setAddressNumber(cell.getStringCellValue());
									}

								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							// Telephone
							if (cell.getColumnIndex() == 4) {
								try {
									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										System.out.println("---- Telephone: " + cell.getNumericCellValue());
										
										String[] arrayPhone = String.valueOf(cell.getNumericCellValue()).split(",");
										contact.setPhone(arrayPhone);
										if(arrayPhone.length>0){
											customer.setMobile(arrayPhone[0]);
										}

									} else {
										System.out.println("---- Telephone: " + cell.getStringCellValue());
										String[] arrayPhone = cell.getStringCellValue().split(",");
										contact.setPhone(arrayPhone);
										if(arrayPhone.length>0){
											customer.setMobile(arrayPhone[0]);
										}

									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							// Email
							if (cell.getColumnIndex() == 6) {
								try {
									String str = new Date().getTime() + "";
									int str1 = cell.getStringCellValue().length();
									String str2 = cell.getStringCellValue();

									System.out.println("---- Email: " + cell.getStringCellValue());
									if (str1 > 4) {
										account.setEmail(str2);
									} else {
										account.setEmail(str + "@gmail.com");
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							// Website
							if (cell.getColumnIndex() == 7) {
								try {
									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										customer.setDescription(String.valueOf(cell.getNumericCellValue()));
									} else {
										if (cell.getStringCellValue() != null) {
											customer.setDescription(cell.getStringCellValue());
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							// Description
							if (cell.getColumnIndex() == 8) {
								try {
									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										System.out.println("---- Description: " + cell.getNumericCellValue());
										organizationBasic.setDescription(String.valueOf(cell.getNumericCellValue()));
									} else {
										System.out.println("---- Description: " + cell.getStringCellValue());
										if (cell.getStringCellValue() != null) {
											if (cell.getStringCellValue().length() > 100) {
												organizationBasic.setDescription(cell.getStringCellValue().substring(0, 100));
											} else {
												organizationBasic.setDescription(cell.getStringCellValue());
											}
											
										}
									}
									
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}

					}
					try {
						if (checkAccount(account)) {
							Profile profileOrgBasic = new Profile();
							OrganizationBasic orgBasic = this.organizationBasic;
							profileOrgBasic.put(OrganizationBasic.NAME, orgBasic.getName());
							profileOrgBasic.put(OrganizationBasic.SLOGAN, orgBasic.getSlogan());
							profileOrgBasic.put(OrganizationBasic.DESCRIPTION, orgBasic.getDescription());

							Profiles profiles = new Profiles();
							profiles.setBasic(profileOrgBasic);
							account.setProfiles(profiles);

							List<Contact> contacts = new ArrayList<Contact>();
							contacts.add(contact);
							account.setContacts(contacts);

							Account acc = AccountModelManager.getInstance().saveAccount(account);
							customer.setLoginId(acc.getLoginId());
							customer.setType("Doanh nghiệp");
							customer.setAddress(contact.getAddressNumber());
							CustomerModelManager.getInstance().saveCustomer(customer);
						}
					} catch (Exception e) {

					}
				}
			}
		}
	}

	// sheet Ca nhan
	private void MapTableUser(Sheet sheet) throws IOException {
		System.out.println("Table : " + sheet.getSheetName());
		String tableName = sheet.getSheetName();
		if (tableName.equals("Ca nhan")) {
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				// Get the row object
				account = new Account();
				account.setLoginId(new Date().getTime() + "");
				account.setPassword("0000");
				account.setType(Type.USER);
				account.setLastLoginTime(new Date());
				basicInformation = new BasicInformation();

				contact = new Contact();

				customer = new Customer();
				customer.setOrganizationLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
				String str = new Date().getTime() + "";
				customer.setCode(str);

				Row row = rowIterator.next();//
				// Every row has columns, get the column iterator and iterate over
				// them
				Iterator<Cell> cellIterator = row.cellIterator();
				if (row.getRowNum() > 0) {
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						if (cell.getRow().getRowNum() == 0) {
							System.out.println("---- Column name:" + cell.getStringCellValue());
						} else {
							if (cell.getColumnIndex() == 0) {
								// firstName

								try {
									System.out.println("STT " + cell.getRowIndex());
									System.out.println("---- firstName: " + cell.getStringCellValue());
									if (cell.getStringCellValue() != null && !cell.getStringCellValue().equals("")) {
										basicInformation.setFirstName(cell.getStringCellValue());
									}

								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							if (cell.getColumnIndex() == 1) {
								// lastName
								try {
									System.out.println("---- lastName: " + cell.getStringCellValue());
									if (cell.getStringCellValue() != null && !cell.getStringCellValue().equals("")) {
										
										basicInformation.setLastName(basicInformation.getFirstName()+" "+cell.getStringCellValue());
										customer.setName(basicInformation.getLastName());
									}

								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							if (cell.getColumnIndex() == 2) {
								// birthday
								try {

									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										System.out.println("---- birthday " + cell.getNumericCellValue());
										basicInformation.setBirthday(String.valueOf(cell.getNumericCellValue()));
									} else {
										System.out.println("---- birthday " + cell.getStringCellValue());
										basicInformation.setBirthday(cell.getStringCellValue().toString());
									}

								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							if (cell.getColumnIndex() == 3) {
								// gender
								try {
									System.out.println("---- gender: " + cell.getStringCellValue());
									if (cell.getStringCellValue() != null && !cell.getStringCellValue().equals("")) {
										basicInformation.setGender(cell.getStringCellValue());
									}

								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							if (cell.getColumnIndex() == 4) {
								// indentityCard

								try {
									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										System.out.println("---- indentityCard: " + cell.getNumericCellValue());
										basicInformation.setPersonalId(String.valueOf(cell.getNumericCellValue()));
									} else {
										System.out.println("---- indentityCard: " + cell.getStringCellValue());
										basicInformation.setPersonalId((cell.getStringCellValue()));
									}

								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							// address
							if (cell.getColumnIndex() == 5) {
								System.out.println("---- address: " + cell.getStringCellValue());
								contact.setAddressNumber(cell.getStringCellValue());
							}

							// telephone
							if (cell.getColumnIndex() == 6) {

								try {
									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										System.out.println("---- telephone1: " + cell.getNumericCellValue());
										String[] phone = String.valueOf(cell.getNumericCellValue()).split(",");
										contact.setPhone(phone);
									} else {
										System.out.println("---- telephone2: " + cell.getStringCellValue());
										String[] phone = cell.getStringCellValue().split(",");
										contact.setPhone(phone);
									}

								} catch (Exception e) {
									e.printStackTrace();
								}

							}

							if (cell.getColumnIndex() == 7) {
								// mobile
								try {
									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										System.out.println("---- mobile1: " + cell.getNumericCellValue());
										String[] arraymobile = String.valueOf(cell.getNumericCellValue()).split(",");
										contact.setMobile(arraymobile);

									} else {
										System.out.println("---- mobile2: " + cell.getStringCellValue());
										String[] arraymobile = cell.getStringCellValue().split(",");
										contact.setMobile(arraymobile);
									}
								} catch (Exception e) {

								}
							}

							// Email
							if (cell.getColumnIndex() == 8) {
								try {
									String string = new Date().getTime() + "";
									int str1 = cell.getStringCellValue().length();
									String str2 = cell.getStringCellValue();

									System.out.println("---- Email: " + cell.getStringCellValue());
									if (str1 > 4) {
										account.setEmail(str2);
									} else {
										account.setEmail(string + "@gmail.com");
									}

								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							// Description
							if (cell.getColumnIndex() == 9) {
								try {
									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										System.out.println("---- telephone1: " + cell.getNumericCellValue());
										String phone = String.valueOf(cell.getNumericCellValue());
										customer.setDescription(phone);
									} else {
										System.out.println("---- telephone2: " + cell.getStringCellValue());
										customer.setDescription(cell.getStringCellValue());
									}

								} catch (Exception e) {
									e.printStackTrace();
								}
							}

						}

					}

					try {

						Profile profileBasic = new Profile();
						BasicInformation basic = this.basicInformation;
						profileBasic.put(BasicInformation.LAST_NAME, basic.getLastName());
						profileBasic.put(BasicInformation.GENDER, basic.getGender());
						profileBasic.put(BasicInformation.BIRTHDAY, basic.getBirthday());
						profileBasic.put(BasicInformation.PERSONAL_ID, basic.getPersonalId());

						Profiles profiles = new Profiles();
						profiles.setBasic(profileBasic);
						account.setProfiles(profiles);

						List<Contact> contacts = new ArrayList<Contact>();
						contacts.add(contact);
						account.setContacts(contacts);

						Account acc = AccountModelManager.getInstance().saveAccount(account);
						customer.setLoginId(acc.getLoginId());
						customer.setType("Cá nhân");
						if (contact.getMobile().length > 0) {
							customer.setMobile(contact.getMobile()[0]);
						}
						try {
							customer.setBirthDay(new SimpleDateFormat("dd/MM/yyyy").parse(basic.getBirthday()));
            } catch (Exception e) {
            }
						
						CustomerModelManager.getInstance().saveCustomer(customer);

					} catch (Exception e) {
					}
				}
			}
		}
	}

	private boolean checkAccount(Account account) {
		if (account == null)
			return false;
		if (account.getEmail() == null)
			return false;
		if (account.getEmail().equals(""))
			return false;

		return true;
	}

}
