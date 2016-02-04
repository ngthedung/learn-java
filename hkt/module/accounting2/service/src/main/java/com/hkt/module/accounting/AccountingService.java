package com.hkt.module.accounting;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.accounting.entity.Bank;
import com.hkt.module.accounting.entity.BankAccount;
import com.hkt.module.accounting.entity.BankTransaction;
import com.hkt.module.accounting.entity.Invoice;
import com.hkt.module.accounting.entity.Invoice.ActivityType;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.accounting.entity.InvoiceTransaction;
import com.hkt.module.accounting.entity.InvoiceTransaction.TransactionType;
import com.hkt.module.accounting.entity.ManageCodes;
import com.hkt.module.accounting.entity.Quantitative;
import com.hkt.module.accounting.entity.Quantitative.Type;
import com.hkt.module.accounting.repository.BankAccountRepository;
import com.hkt.module.accounting.repository.BankRepository;
import com.hkt.module.accounting.repository.BankTransactionRepository;
import com.hkt.module.accounting.repository.CodesRespository;
import com.hkt.module.accounting.repository.InvoiceDetailRepository;
import com.hkt.module.accounting.repository.QuantitativeRepository;
import com.hkt.module.accounting.util.AccountingScenario;
import com.hkt.module.config.locale.LocaleService;
import com.hkt.module.core.JVMService;
import com.hkt.module.core.ServiceCallback;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.core.entity.ReportTable;
import com.hkt.module.core.entity.SQLSelectQuery;
import com.hkt.module.core.search.FSSearchService;
import com.hkt.module.core.search.SearchQuery;
import com.hkt.module.core.search.SearchResult;
import com.hkt.module.partner.customer.CustomerService;
import com.hkt.module.partner.customer.entity.Credit;
import com.hkt.module.partner.customer.entity.CreditTransaction;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.customer.entity.Point;
import com.hkt.module.partner.customer.entity.PointTransaction;
import com.hkt.module.product.ProductService;
import com.hkt.module.restaurant.RestaurantService;
import com.hkt.module.restaurant.entity.Recipe;
import com.hkt.module.restaurant.entity.RecipeIngredient;
import com.hkt.module.restaurant.entity.Shift;
import com.hkt.module.warehouse.WarehouseService;
import com.hkt.module.warehouse.entity.IdentityProduct;
import com.hkt.module.warehouse.entity.WarehouseInventory;

@Service("AccountingService")
public class AccountingService {
	@Autowired
	InvoiceDetailRepository invoiceDetailRepo;

	@Autowired
	BankAccountRepository bankAccountRepository;

	@Autowired
	QuantitativeRepository quantitativeRepository;

	@Autowired
	private FSSearchService searchService;

	@Autowired
	private LocaleService localeService;

	@Autowired
	private WarehouseService warehouseService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private RestaurantService restaurantService;

	@Autowired
	private CodesRespository codesRespository;
	
	@Autowired
	private BankRepository bankRepository;
	  
	@Autowired
	private BankTransactionRepository bankTransactionRepository;

	@Transactional(readOnly = true)
	public InvoiceDetail getInvoiceDetail(Long id) {
		InvoiceDetail instance = invoiceDetailRepo.findOne(id);
		return instance;
	}

	@Transactional(readOnly = true)
	public InvoiceDetail getInvoiceDetailByCredit(String customerCode) {
		InvoiceDetail instance = invoiceDetailRepo.getInvoiceDetailByCredit(customerCode);
		return instance;
	}

	@Transactional
	public List<InvoiceDetail> findInvoiceDetailByCode(String code, String type) {
		return invoiceDetailRepo.findInvoiceDetailByCode(code, type);
	}

	@Transactional
	public FilterResult<Invoice> searchInvoice(FilterQuery query) {
		if (query == null)
			query = new FilterQuery();
		return invoiceDetailRepo.searchInvoices(query);
	}

	@Transactional
	public FilterResult<InvoiceDetail> query(FilterQuery query) {
		if (query == null)
			query = new FilterQuery();
		return invoiceDetailRepo.query(query);
	}

	public SearchResult<InvoiceDetail> searchInvoices(SearchQuery query) throws Exception {
		return this.searchService.query(InvoiceDetail.class, query);
	}

	@Transactional
	public List<InvoiceDetail> findInvoiceDetailByIdentifierId(String identifierId) {
		return invoiceDetailRepo.findInvoiceDetailByIdentifierId(identifierId);
	}

	@Transactional
	public List<InvoiceDetail> findInvoiceDetailByAttributeValue(String value) {
		return invoiceDetailRepo.findInvoiceDetailByAttributeValue(value);
	}

	@Transactional
	public List<InvoiceDetail> findInvoiceDetailByAttributeItemValue(String value) {
		return invoiceDetailRepo.findInvoiceDetailByAttributeItemValue(value);
	}

	@Transactional
	public InvoiceDetail createInvoiceDetail(InvoiceDetail invoiceDetail) throws Exception {
		if (invoiceDetail == null)
			return null;
		return updateInvoiceDetail(invoiceDetail);
	}

	@Transactional
	public InvoiceDetail calculateInvoiceDetail(InvoiceDetail invoiceDetail) throws Exception {
		return invoiceDetail.calculate(new DefaultInvoiceCalculator());
	}

	@Transactional
	public String getStartDateLessOnInvoiceDetail() throws Exception {
		return invoiceDetailRepo.getStartDateLessInvoiceDetail();
	}

	@Transactional
	public void deleteTest(String test) {
		invoiceDetailRepo.deleteTestInvoiceItemAttribute(test);
		invoiceDetailRepo.deleteTestInvoiceItem(test);
		invoiceDetailRepo.deleteTestInvoiceTransaction(test);
		invoiceDetailRepo.deleteTestAttribute(test);
		invoiceDetailRepo.deleteTestContributor(test);
		invoiceDetailRepo.deleteTest(test);
	}

	@Transactional
	public InvoiceDetail saveInvoice(InvoiceDetail invoice) throws Exception {
		return invoiceDetailRepo.save(invoice);
	}

	@Transactional
	public InvoiceDetail updateInvoiceDetail(InvoiceDetail invoice) throws Exception {
		if (invoice == null)
			return null;
		long time1 = System.currentTimeMillis();
		List<InvoiceItem> items = invoice.getItems();
		double rate = 1;
		try {
			rate = localeService.getCurrency(invoice.getCurrencyUnit()).getRate();
		} catch (Exception e) {
		}
		invoice.setCurrencyRate(rate);
		invoice.setModifiedTime(new Date());
		if (items != null) {
			for (int i = 0; i < items.size(); i++) {
				InvoiceItem sel = items.get(i);
				sel.setWarehouseCode(invoice.getWarehouseId());
				sel.setCreatedTime(invoice.getStartDate());
				sel.setStartDate(invoice.getStartDate());
				sel.setCurrencyRate(rate);
				sel.setInvoiceCode(invoice.getInvoiceCode());
				sel.setTypeReport(InvoiceItem.Report);
				sel.setType(invoice.getType());
			}
		}
		System.out.println("time1: " + new DecimalFormat("#0.00000").format((System.currentTimeMillis() - time1) / 1000d));
		List<InvoiceTransaction> transactions = invoice.getTransactions();
		InvoiceTransaction transaction = null;
		if (transactions != null) {
			for (InvoiceTransaction invoiceTransaction : transactions) {
				invoiceTransaction.setCreatedTime(invoiceTransaction.getTransactionDate());
				invoiceTransaction.setEmployeeCode(invoice.getEmployeeCode());
				invoiceTransaction.setCustomerCode(invoice.getCustomerCode());
				invoiceTransaction.setDepartmentCode(invoice.getDepartmentCode());
				invoiceTransaction.setCurrencyRate(rate);
				invoiceTransaction.setGroupCustomerCode(invoice.getGroupCustomerCode());
				invoiceTransaction.setTableCode(invoice.getTableCode());
				invoiceTransaction.setLocationCode(invoice.getLocationCode());
				invoiceTransaction.setWarehouseId(invoice.getWarehouseId());
				invoiceTransaction.setStoreCode(invoice.getStoreCode());
				invoiceTransaction.setProjectCode(invoice.getProjectCode());
				// invoiceTransaction.setShiftCode(invoice.getShiftCode());
				// invoiceTransaction.setShiftDate(invoice.getShiftDate());
				invoiceTransaction.setSupplierCode(invoice.getSupplierCode());
				invoiceTransaction.setType(invoice.getType());
				if (invoice.getActivityType().equals(ActivityType.Payment)) {
					invoiceTransaction.setActivityType(InvoiceTransaction.ActivityType.Payment);
				} else {
					invoiceTransaction.setActivityType(InvoiceTransaction.ActivityType.Receipt);
				}
				if (invoiceTransaction.getId() == null) {

					if (invoiceTransaction.getShiftDate() == null) {
						Date date = new Date();
						List<Shift> shifts = restaurantService.getAllShift();
						DateFormat dfGioPhut = new SimpleDateFormat("HHmm");
						int shiftDate = Integer.valueOf(dfGioPhut.format(date).replaceAll(":", ""));
						for (int i = 0; i < shifts.size(); i++) {
							int shiftHourStart = Integer.valueOf(dfGioPhut.format(shifts.get(i).getHourStart()).replaceAll(":", ""));
							int shiftHourEnd = Integer.valueOf(dfGioPhut.format(shifts.get(i).getHourEnd()).replaceAll(":", ""));
							if ((shiftHourStart <= shiftDate) && (shiftDate <= shiftHourEnd)) {
								invoiceTransaction.setShiftDate(date);
								invoiceTransaction.setShiftCode(shifts.get(i).getCode());
							} else if ((shiftHourStart > shiftHourEnd)) {
								if (shiftHourStart <= shiftDate) {
									invoiceTransaction.setShiftDate(date);
									invoiceTransaction.setShiftCode(shifts.get(i).getCode());
								} else if (shiftHourEnd >= shiftDate) {
									Calendar calendar = Calendar.getInstance();
									calendar.setTime(date);
									int ngay = calendar.get(Calendar.DAY_OF_MONTH);
									calendar.set(Calendar.DAY_OF_MONTH, ngay - 1);
									date = calendar.getTime();
									invoiceTransaction.setShiftDate(date);
									invoiceTransaction.setShiftCode(shifts.get(i).getCode());

								}
							}
						}
					}
					transaction = invoiceTransaction;
				}
			}
		}
		invoice.setCreatedTime(invoice.getStartDate());
		invoice = invoiceDetailRepo.save(invoice);
		System.out.println("time2: " + new DecimalFormat("#0.00000").format((System.currentTimeMillis() - time1) / 1000d));
		if (invoice.getActivityType().equals(ActivityType.Receipt)) {
			if (transaction != null) {
				try {
					String loginId = invoice.getCustomerCode();
					Customer c = customerService.getCustomerByCode(loginId);
					if (c != null) {
						Point point = customerService.getPointByCustomerId(c.getId());
						PointTransaction pointTransaction1 = new PointTransaction();
						double a = customerService.getConversionRuleMoneyToPoint().numRatioCreditToPoint() * transaction.getTotal();
						pointTransaction1.setPoint(a);
						List<PointTransaction> pointTransactions = customerService.findByInvoiceId(invoice.getId());
						if (pointTransactions != null) {
							for (PointTransaction p : pointTransactions) {
								if (p.getPoint() > 0) {
									pointTransaction1 = p;
									pointTransaction1.setPoint(p.getPoint() + a);
								}
							}
						}
						pointTransaction1.setInvoiceId(invoice.getId());
						pointTransaction1.setLoginId(point.getLoginId());
						pointTransaction1.setPointId(point.getId());
						pointTransaction1.setPointConversionRuleId(customerService.getConversionRuleMoneyToPoint().getId());
						pointTransaction1.setDateExecute(new Date());
						customerService.savePointTransaction(pointTransaction1);
						point.setPoint(point.getPoint() + a);
						customerService.savePoint(point);
					}
				} catch (Exception e) {
					// e.printStackTrace();
				}
			} else {
				try {
					String loginId = invoice.getCustomerCode();
					Customer c = customerService.getCustomerByCode(loginId);
					if (c != null) {
						Point point = customerService.getPointByCustomerId(c.getId());
						PointTransaction pointTransaction1 = new PointTransaction();
						double a = customerService.getConversionRuleMoneyToPoint().numRatioCreditToPoint() * invoice.getTotal();
						pointTransaction1.setPoint(a);
						List<PointTransaction> pointTransactions = customerService.findByInvoiceId(invoice.getId());
						if (pointTransactions != null) {
							for (PointTransaction p : pointTransactions) {
								if (p.getPoint() > 0) {
									pointTransaction1 = p;
									pointTransaction1.setPoint(p.getPoint() + a);
								}
							}
						}
						pointTransaction1.setInvoiceId(invoice.getId());
						pointTransaction1.setLoginId(point.getLoginId());
						pointTransaction1.setPointId(point.getId());
						pointTransaction1.setPointConversionRuleId(customerService.getConversionRuleMoneyToPoint().getId());
						pointTransaction1.setDateExecute(new Date());
						customerService.savePointTransaction(pointTransaction1);
						point.setPoint(point.getPoint() + a);
						customerService.savePoint(point);
					}
				} catch (Exception e) {
					// e.printStackTrace();
				}
			}
			System.out
			    .println("time3: " + new DecimalFormat("#0.00000").format((System.currentTimeMillis() - time1) / 1000d));
			if (invoice.getPoint() > 0) {
				try {
					PointTransaction pointTransaction = customerService.getByInvoiceId(invoice.getId());
					if (pointTransaction == null) {
						String loginId = invoice.getCustomerCode();
						Customer c = customerService.getCustomerByCode(loginId);
						Point point = customerService.getPointByCustomerId(c.getId());
						pointTransaction = new PointTransaction();
						pointTransaction.setInvoiceId(invoice.getId());
						pointTransaction.setLoginId(point.getLoginId());
						pointTransaction.setPointId(point.getId());
						pointTransaction.setPointConversionRuleId(customerService.getConversionRulePointToMoney(point.getPoint())
						    .getId());
						double a = invoice.getPoint()
						    / customerService.getConversionRulePointToMoney(point.getPoint()).numRatioPointToCredit();
						pointTransaction.setPoint(a * (-1));
						pointTransaction.setDateExecute(new Date());
						customerService.savePointTransaction(pointTransaction);

						point.setPoint(point.getPoint() - a);
						customerService.savePoint(point);
					}
				} catch (Exception e) {
				}
			}

			if (invoice.getCredit() > 0) {
				try {
					CreditTransaction pointTransaction = customerService.getCreditTransactionByInvoiceId(invoice.getId());
					if (pointTransaction == null) {
						String loginId = invoice.getCustomerCode();
						Customer c = customerService.getCustomerByCode(loginId);
						Credit point = customerService.getCreditByCustomerId(c.getId());
						pointTransaction = new CreditTransaction();
						pointTransaction.setInvoiceId(invoice.getId());
						pointTransaction.setLoginId(point.getLoginId());
						pointTransaction.setCreditId(point.getId());
						pointTransaction.setCreatedBy(invoice.getCreatedBy());
						pointTransaction.setAmount(invoice.getCredit() * (-1));
						pointTransaction.setDateExecute(new Date());
						customerService.saveCreditTransaction(pointTransaction);
						point.setCredit(point.getCredit() - invoice.getCredit());
						customerService.saveCredit(point);
						updateInvoiceCredit(pointTransaction,invoice.getCustomerCode());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

		// if (invoice.getActivityType().equals(ActivityType.Receipt)) {
		// final long id = invoice.getId();
		// final Thread t = new Thread() {
		//
		// @Override
		// public void run() {
		// long endTime2 = System.currentTimeMillis();
		// InvoiceDetail invoice = invoiceDetailRepo.findOne(id);
		// if (invoice != null) {
		// try {
		// createQuantitative(invoice);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//
		// }
		//
		// System.out.println("time5: " + id + "  "
		// + new DecimalFormat("#0.00000").format((System.currentTimeMillis() -
		// endTime2) / 1000d));
		// }
		// };
		//
		// t.start();
		// }
		searchService.add(invoice.getId(), invoice, invoice.isNew());
		System.out.println("time4: " + new DecimalFormat("#0.00000").format((System.currentTimeMillis() - time1) / 1000d));
		System.out.println("........" + new JVMService().getMemoryInfo());
		return invoice;
	}

	private void updateInvoiceCredit(CreditTransaction creditTransaction,String customerCode) throws Exception {
		InvoiceDetail invoiceDetail = getInvoiceDetailByCredit(customerCode);
		InvoiceItem invoiceItem = invoiceDetail.getItems().get(0);
		invoiceItem.setUnitPrice(invoiceItem.getUnitPrice() + invoiceDetail.getTotal());
		invoiceItem.setTotal(invoiceItem.getUnitPrice() + invoiceDetail.getTotal());
		invoiceItem.setFinalCharge(invoiceItem.getUnitPrice() + invoiceDetail.getTotal());
		if(creditTransaction.getAmount()>0){
			invoiceDetail.setTotal(creditTransaction.getAmount() + invoiceDetail.getTotal());
			invoiceDetail.setFinalCharge(creditTransaction.getAmount() + invoiceDetail.getFinalCharge());
		}else{
			invoiceDetail.setTotal((creditTransaction.getAmount()*(-1)) + invoiceDetail.getTotal());
			invoiceDetail.setFinalCharge((creditTransaction.getAmount()*(-1)) + invoiceDetail.getFinalCharge());
		}
		
		InvoiceTransaction invoiceTransaction = new InvoiceTransaction();
		invoiceTransaction.setCreatedBy(creditTransaction.getCreatedBy());
		invoiceTransaction.setCurrencyRate(invoiceDetail.getCurrencyRate());
		invoiceTransaction.setCurrencyUnit(invoiceDetail.getCurrencyUnit());
		if(creditTransaction.getAmount()>0){
		invoiceTransaction.setTotal(creditTransaction.getAmount());
		}else{
			invoiceTransaction.setTotal(creditTransaction.getAmount()*(-1));
		}
		invoiceTransaction.setTransactionDate(creditTransaction.getDateExecute());
		invoiceTransaction.setTransactionType(TransactionType.Cash);
		invoiceDetail.add(invoiceTransaction);
		updateInvoiceDetail(invoiceDetail);
	}

	private void createQuantitative(InvoiceDetail invoice) throws Exception {
		List<InvoiceItem> invoiceItems = invoice.getItems();
		String warehouseId = invoice.getWarehouseId();
		for (InvoiceItem invoiceItem : invoiceItems) {
			String productCode = invoiceItem.getProductCode();
			double quantity = invoiceItem.getQuantity() - invoiceItem.getQuantityReal();
			if (productCode != null) {
				List<IdentityProduct> identityProducts = warehouseService.findByProductCodeAndExportType(productCode);
				if (quantity > identityProducts.size()) {
					createRecipe(productCode, invoiceItem, warehouseId, invoice.getActivityType());
				}
			}
		}
	}

	private void createRecipe(String productCode, InvoiceItem invoiceItem, String warehouseId, ActivityType activityType) {
		Recipe recipe = restaurantService.getRecipeByProductCode(productCode);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSS");
		if (recipe != null) {
			List<Quantitative> quantitatives = quantitativeRepository.findByInvoiceItemCode(invoiceItem.getItemCode());
			if (quantitatives.size() <= 1) {
				List<RecipeIngredient> ingredients = recipe.getRecipeIngredients();
				for (RecipeIngredient recipeIngredient : ingredients) {
					Quantitative quantitative = new Quantitative();
					quantitative.setQuantitativeCode(format.format(new Date()));
					quantitative.setDate(invoiceItem.getStartDate());
					quantitative.setProductCode(recipeIngredient.getProductCode());
					quantitative.setCurrencyRate(invoiceItem.getCurrencyRate());
					quantitative.setProductName(invoiceItem.getItemName());
					quantitative.setWarehouseId(warehouseId);
					quantitative.setQuantity(invoiceItem.getQuantity() * recipeIngredient.getQuantity());
					quantitative.setTotalPrice(0);
					quantitative.setUnitPrice(invoiceItem.getCurrencyUnit());
					quantitative.setInvoiceCode(invoiceItem.getInvoiceCode());
					quantitative.setInvoiceItemCode(invoiceItem.getItemCode());
					if (activityType == ActivityType.Receipt) {
						quantitative.setType(Type.Export);
					} else {
						quantitative.setType(Type.Import);
					}
					quantitative = quantitativeRepository.save(quantitative);
					WarehouseInventory inventory = warehouseService.getInventoryByProduct(quantitative.getProductCode(),
					    new SimpleDateFormat("ddMMyyyy").format(invoiceItem.getStartDate()));
					if (inventory == null) {
						inventory = new WarehouseInventory();
						inventory.setCode(new SimpleDateFormat("ddMMyyyy").format(invoiceItem.getStartDate()));
						inventory.setStartDate(invoiceItem.getStartDate());
						inventory.setProductCode(quantitative.getProductCode());
						inventory.setIncreaseQuantity(0);
						inventory.setIncreaseValue(0);
						inventory.setReductionVitualQuantity(quantitative.getQuantity());
						inventory.setReductionVitualValue(0);
						inventory.setProfitVitualQuantity(0 - quantitative.getQuantity());
						inventory.setProfitVitualValue(0);
						warehouseService.saveInventory(inventory);
					} else {
						inventory.addVitualReduction(quantitative.getQuantity(), 0);
						warehouseService.saveInventory(inventory);
					}
				}
			} else {
				for (Quantitative quantitative : quantitatives) {
					List<RecipeIngredient> recipeIngredients = recipe.getRecipeIngredients();
					for (RecipeIngredient recipeIngredientProduct : recipeIngredients) {
						if (recipeIngredientProduct.getProductCode().equals(quantitative.getProductCode())) {
							WarehouseInventory inventory = warehouseService.getInventoryByProduct(quantitative.getProductCode(),
							    new SimpleDateFormat("ddMMyyyy").format(invoiceItem.getStartDate()));
							if (inventory != null) {
								inventory.addVitualReduction(quantitative.getQuantity()
								    - (invoiceItem.getQuantity() * recipeIngredientProduct.getQuantity()), 0);
								warehouseService.saveInventory(inventory);
							}
							quantitative.setQuantity(invoiceItem.getQuantity() * recipeIngredientProduct.getQuantity());
							quantitative = quantitativeRepository.save(quantitative);

							break;
						}
					}
				}
			}
		}
	}

	// Edit
	@Transactional
	public boolean deleteInvoiceById(long id) throws Exception {
		try {
			customerService.deleteCreditTransactionByInvoiceId(id);
			customerService.deletePointTransactionByInvoiceId(id);
		} catch (Exception e) {
		}
		invoiceDetailRepo.delete(id);
		return true;
	}

	@Transactional
	public void deleteByinvoiceItemCode(InvoiceItem invoiceItem) {
		Recipe recipe = restaurantService.getRecipeByProductCode(invoiceItem.getProductCode());
		if (recipe != null) {
			List<Quantitative> quantitatives = quantitativeRepository.findByInvoiceItemCode(invoiceItem.getItemCode());
			if (quantitatives != null) {
				for (Quantitative quantitative : quantitatives) {
					WarehouseInventory inventory = warehouseService.getInventoryByProduct(quantitative.getProductCode(),
					    new SimpleDateFormat("ddMMyyyy").format(invoiceItem.getStartDate()));
					if (inventory != null) {
						inventory.addVitualReduction(quantitative.getQuantity() * (-1), 0);
						warehouseService.saveInventory(inventory);
					}
				}
			}
		}
		quantitativeRepository.deleteByinvoiceItemCode(invoiceItem.getItemCode());
	}

	@Transactional
	public boolean deleteInvoice(InvoiceDetail invoice, ServiceCallback<AccountingService> callback) throws Exception {
		if (callback != null) {
			callback.callback(this);
		}
		
		invoiceDetailRepo.delete(invoice.getId());
		return true;
	}

	public ReportTable[] reportQuery(SQLSelectQuery[] query) {
		return invoiceDetailRepo.reportQuery(query);
	}

	@Transactional
	public void createScenario(AccountingScenario scenario) throws Exception {
		List<InvoiceDetail> invoices = scenario.getInvoices();
		for (int i = 0; i < invoices.size(); i++) {
			InvoiceDetail invoice = invoices.get(i);
			if (invoice.getInvoiceCode() == null) {
				invoice.setInvoiceCode("invoice-" + i);
			}
			invoice.calculate(new DefaultInvoiceCalculator());
			createInvoiceDetail(invoice);
		}

		for (BankAccount bankAccount : scenario.getBankAccounts()) {
			bankAccountRepository.save(bankAccount);
		}
	}

	@Transactional(readOnly = true)
	public BankAccount getBankAccountById(String bankAccountId) {
		return bankAccountRepository.getByBankAccountId(bankAccountId);
	}

	@Transactional
	public BankAccount saveBankAccount(BankAccount bankAccount) {
		return bankAccountRepository.save(bankAccount);
	}

	@Transactional
	public boolean deleteBankAccountByAccountId(String bankAccountId) {
		BankAccount bank = bankAccountRepository.getByBankAccountId(bankAccountId);
		bankAccountRepository.delete(bank);
		return true;
	}

	@Transactional(readOnly = true)
	public List<BankAccount> getBankAccounts() {
		return (List<BankAccount>) bankAccountRepository.findAll();
	}

	@Transactional(readOnly = true)
	public List<BankAccount> findBankAccountByBankLoginId(String bankLoginId) {
		bankLoginId = bankLoginId.replace('*', '%');
		return bankAccountRepository.findByBankAccount(bankLoginId.toLowerCase());
	}

	public void deleteAll() throws Exception {
		invoiceDetailRepo.deleteAll();
		bankAccountRepository.deleteAll();
		searchService.delete(InvoiceDetail.class);
	}

	@Transactional(readOnly = true)
	public String ping() {
		return "AccountingService alive!!!";
	}

	@Transactional(readOnly = true)
	public InvoiceDetail getInvoiceDetailByCode(String invoiceCode) {
		return invoiceDetailRepo.getInvoiceDetailByCode(invoiceCode);
	}

	@Transactional(readOnly = true)
	public List<InvoiceDetail> findInvoiceDetailByName(String invoiceName) {
		return invoiceDetailRepo.findInvoiceDetailByName(invoiceName);
	}

	@Transactional(readOnly = true)
	public List<InvoiceItem> queryInvoiceItems(FilterQuery query) {
		return invoiceDetailRepo.queryInvoiceItems(query).getData();
	}

	public ReportTable[] getReportTable(SQLSelectQuery[] rQuery) {
		return invoiceDetailRepo.reportQuery(rQuery);
	}

	@Transactional(readOnly = true)
	public InvoiceDetail findOneByInvoiceItem(long itemId) {
		return invoiceDetailRepo.findOneByInvoiceItem(itemId);
	}

	@Transactional
	public void dropTable(Class<?> type) {
		invoiceDetailRepo.drop(type);
	}

	@Transactional
	public void executeSQL(String sql) {
		invoiceDetailRepo.executeSQL(sql);
	}

	@Transactional
	public void dropTableByName(String tableName) {
		invoiceDetailRepo.drop(tableName);
	}

	@Transactional
	public List<ManageCodes> getAllCodes() {
		return (List<ManageCodes>) codesRespository.findByValueRecycleBin(false);
	}

	@Transactional
	public ManageCodes saveCodes(ManageCodes codes) {
		return codesRespository.save(codes);
	}

	@Transactional
	public boolean deleteCodes(ManageCodes codes) throws Exception {
		if (codes == null) {
			return false;
		} else {
			codesRespository.delete(codes);
			return true;
		}
	}

	@Transactional
	public ManageCodes getCodesByCode(String code) {
		return codesRespository.getCodesByCode(code);
	}

	/** ----Bank ----
	   * getAllBank
	   * getByBankName
	   * saveBank
	   * getBankByCode
	   * getBankByParentCode
	   * getBankOnParentCode
	   * deleteBank
	   */
	  
	  @Transactional (readOnly = true)
	  public List<Bank> getAllBank(){
		  return bankRepository.findByBankRecycleBin(false);
	  }
	  
	  @Transactional (readOnly = true)
	  public List<Bank> getByBankName(String name){
		  return bankRepository.findByName(name);
	  }
	  
	  @Transactional
	  public Bank saveBank(Bank bank) throws Exception{
		  return bankRepository.save(bank);
	  }
	  
	  @Transactional
	  public Bank getBankByCode(String code) {
	    return bankRepository.getByCode(code);
	  }
	  
	  @Transactional (readOnly = true)
	  public List<Bank> getBankByParentCode(String parentCode){
		  return bankRepository.findByParentBank(parentCode);
	  }
	  
	  @Transactional (readOnly = true)
	  public Bank findByBankCode(String bankCode){
		  return bankRepository.findByBankCode(bankCode);
	  }
	  
	  @Transactional
	  public boolean deleteBank(Bank bank) throws Exception{
		  return deleteBankCallBack(bank);
	  }
	  
	  @Transactional
	  public boolean deleteBankCallBack(Bank bank) throws Exception{
//		  if (callBack != null){
//			  callBack.callback(this);
//		  }
		  if (bank == null){
			  return false;
		  } else {
			  if (bank.isRecycleBin()){
				  bankRepository.delete(bank);
				  return true;
			  } else {
				  bank.setRecycleBin(true);
				  if (saveBank(bank)!=null)
					  return true;
				  else
					  return false;
			  }
		  }
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
	  
	  @Transactional (readOnly = true)
	  public List<BankTransaction> getAllBankTransaction() throws Exception{
		  return bankTransactionRepository.findByBankTracsactionRecycleBin(false);
	  }
	  
	  @Transactional (readOnly = true)
	  public List<BankTransaction> getByBankTransactionName(String name) throws Exception{
		  return bankTransactionRepository.findByName(name);
	  }
	  
	  @Transactional (readOnly = true)
	  public List<BankTransaction> getByBankRangeTotal(long total) throws Exception{
		return  bankTransactionRepository.findByRangeTotal(total);  
	  }
	  
	  @Transactional (readOnly = true)
	  public List<BankTransaction> getByBankCode(String bankCode) throws Exception{
		  return bankTransactionRepository.findByBankCode(bankCode);
	  }
	  
	  @Transactional (readOnly = true) 
	  public List<BankTransaction> getTracsactionCode(String transactionCode) throws Exception{
		  return bankTransactionRepository.findByTracsactionCode(transactionCode);
	  }
	  
	  @Transactional (readOnly = true) 
	  public List<BankTransaction> getByBankType(boolean type) throws Exception{
		  return bankTransactionRepository.findByType(type);
	  }
	  
	  @Transactional
	  public BankTransaction saveBankTransaction(BankTransaction bankTransaction) throws Exception{
		  return bankTransactionRepository.save(bankTransaction);
	  }
	  
	  @Transactional (readOnly = true) 
	  public List<BankTransaction> getBankTransactionByCode(String code)  throws Exception{
	    return bankTransactionRepository.getByCode(code);
	  }
	  
	  @Transactional
	  public boolean deleteBankTransaction(BankTransaction bankTransaction) throws Exception{
		  return deleteBankTransactionCallBack(bankTransaction);
	  }
	  
	  @Transactional
	  public boolean deleteBankTransactionCallBack(BankTransaction bankTransaction) throws Exception{
//		  if (callBack != null){
//			  callBack.callback(this);
//		  }
		  if (bankTransaction == null) {
			  return false;
		  } else {
			  if (bankTransaction.isRecycleBin()){
				  bankTransactionRepository.delete(bankTransaction);
				  return true;
			  } else {
				  bankTransaction.setRecycleBin(true);
				  if (saveBankTransaction(bankTransaction) != null)
					  return true;
				  else
					  return false;
			  }
		  }
	  }
}
