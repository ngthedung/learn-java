package com.hkt.client.rest.service;

import java.util.List;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hkt.client.rest.RESTClient;
import com.hkt.module.accounting.entity.Bank;
import com.hkt.module.accounting.entity.BankAccount;
import com.hkt.module.accounting.entity.BankTransaction;
import com.hkt.module.accounting.entity.Invoice;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.accounting.entity.ManageCodes;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.core.entity.ReportTable;
import com.hkt.module.core.entity.SQLSelectQuery;
import com.hkt.module.core.rest.Request;
import com.hkt.module.core.rest.Response;

@Component
public class AccountingServiceClient {
	@Autowired
	private RESTClient client;

	public InvoiceDetail saveInvoice(InvoiceDetail invoiceDetail) throws Exception {
		Request req = create("saveInvoice");
		req.addParam("invoiceDetail", invoiceDetail);
		Response res = client.POST(req);
		return res.getDataAs(InvoiceDetail.class);
	}
	
	public BankAccount saveBankAccount(BankAccount bankAccount) throws Exception {
		Request req = create("saveBankAccount");
		req.addParam("invoiceDetail", bankAccount);
		Response res = client.POST(req);
		return res.getDataAs(BankAccount.class);
	}

	public List<InvoiceDetail> findInvoiceDetailByCode(String code, String type) throws Exception {
		Request req = create("findInvoiceDetailByCode");
		req.addParam("code", code);
		req.addParam("type", type);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<InvoiceDetail>>() {
		});
	}

	public InvoiceDetail saveInvoiceDetail(InvoiceDetail invoiceDetail) throws Exception {

		Request req = null;
		if (invoiceDetail.isNew()) {
			req = create("createInvoiceDetail");
		} else {
			req = create("updateInvoiceDetail");
		}
		req.addParam("invoiceDetail", invoiceDetail);
		Response res = client.POST(req);
		return res.getDataAs(InvoiceDetail.class);
	}

	public InvoiceDetail getInvoiceDetailByCredit(String loginId) throws Exception {
		Request req = create("getInvoiceDetailByCredit");
		req.addParam("loginId", loginId);
		Response res = client.POST(req);
		return res.getDataAs(InvoiceDetail.class);
	}

	public List<InvoiceDetail> findInvoiceDetailByIdentifierId(String identifierId) throws Exception {
		Request req = create("findInvoiceDetailByIdentifierId");
		req.addParam("identifierId", identifierId);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<InvoiceDetail>>() {
		});
	}

	public List<InvoiceDetail> findInvoiceDetailByAttributeValue(String value) throws Exception {
		Request req = create("findInvoiceDetailByAttributeValue");
		req.addParam("value", value);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<InvoiceDetail>>() {
		});
	}

	public List<InvoiceDetail> findInvoiceDetailByAttributeItemValue(String value) throws Exception {
		Request req = create("findInvoiceDetailByAttributeItemValue");
		req.addParam("value", value);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<InvoiceDetail>>() {
		});
	}

	public InvoiceDetail getInvoiceDetail(Long id) throws Exception {
		Request req = create("getInvoiceDetail");
		req.addParam("id", id);
		Response res = client.POST(req);
		return res.getDataAs(InvoiceDetail.class);
	}

	public String getStartDateLessYear() throws Exception {
		Request req = create("getStartDateLessOnInvoiceDetail");
		Response res = client.POST(req);
		return res.getDataAs(String.class);
	}

	public FilterResult<InvoiceDetail> query(FilterQuery query) throws Exception {
		Request req = create("query");
		req.addParam("query", query);
		Response res = client.POST(req);
		return res.getDataAsByFilter(new TypeReference<FilterResult<InvoiceDetail>>() {
		});
	}

	public FilterResult<Invoice> searchInvoice(FilterQuery query) throws Exception {
		Request req = create("searchInvoice");
		req.addParam("query", query);
		Response res = client.POST(req);
		return res.getDataAsByFilter(new TypeReference<FilterResult<Invoice>>() {
		});
	}

	public boolean deleteInvoiceById(Long id) throws Exception {
		Request req = create("deleteInvoiceById");
		req.addParam("id", id);
		client.POST(req);
		return true;
	}


	public boolean deleteQuantityByItemCode(InvoiceItem invoiceItem) throws Exception {
		Request req = create("deleteByinvoiceItemCode");
		req.addParam("invoiceItem", invoiceItem);
		client.POST(req);
		return true;
	}

	public ReportTable[] reportQuery(SQLSelectQuery[] query) throws Exception {
		Request req = create("reportQuery");
		req.addParam("query", query);
		Response res = client.POST(req);
		return res.getDataAs(ReportTable[].class);
	}

	public InvoiceDetail getInvoiceDetailByCode(String invoiceCode) throws Exception {
		Request req = create("getInvoiceDetailByCode");
		req.addParam("invoiceCode", invoiceCode);
		Response res = client.POST(req);
		return res.getDataAs(InvoiceDetail.class);
	}

	public List<InvoiceDetail> findInvoiceDetailByName(String invoiceName) throws Exception {
		Request req = create("findInvoiceDetailByName");
		req.addParam("invoiceName", invoiceName);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<InvoiceDetail>>() {
		});
	}

	public List<InvoiceItem> queryInvoiceItems(FilterQuery query) throws Exception {
		Request req = create("queryInvoiceItems");
		req.addParam("query", query);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<InvoiceItem>>() {
		});
	}

	public List<ManageCodes> getAllCodes() throws Exception {
		Request req = create("getAllCodes");
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<ManageCodes>>() {
		});
	}
	
	public List<BankAccount> getBankAccounts() throws Exception {
		Request req = create("getBankAccounts");
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<BankAccount>>() {
		});
	}

	public InvoiceDetail findOneByInvoiceItem(long itemId) throws Exception {
		Request req = create("findOneByInvoiceItem");
		req.addParam("itemId", itemId);
		Response res = client.POST(req);
		return res.getDataAs(InvoiceDetail.class);
	}

	public void deleteTest(String test) throws Exception {
		Request req = create("deleteTest");
		req.addParam("test", test);
		client.POST(req);
	}

	public void dropTable(Class<?> type) throws Exception {
		Request req = create("dropTable");
		req.addParam("type", type);
		client.POST(req);
	}

	public void dropTable(String tableName) throws Exception {
		Request req = create("dropTableByName");
		req.addParam("tableName", tableName);
		client.POST(req);
	}
	
	public void executeSQL(String sql) throws Exception {
		Request req = create("executeSQL");
		req.addParam("sql", sql);
		client.POST(req);
	}

	public ManageCodes saveCodes(ManageCodes codes) throws Exception {
		Request req = create("saveCodes");
		req.addParam("codes", codes);
		Response res = client.POST(req);
		return res.getDataAs(ManageCodes.class);
	}

	public boolean deleteCodes(ManageCodes codes) throws Exception {
		Request req = create("deleteCodes");
		req.addParam("codes", codes);
		client.POST(req);
		return true;
	}

	public ManageCodes getCodesByCode(String code) throws Exception {
		Request req = create("getCodesByCode");
		req.addParam("code", code);
		Response res = client.POST(req);
		return res.getDataAs(ManageCodes.class);
	}

	Request create(String method) {
		return new Request("accounting", "AccountingService", method);
	}
	
	/** ----Bank ----
	 * getAllBank
	 * getByBankName
	 * saveBank
	 * getBankByCode
	 * getBankByParentCode
	 * getOnParentBank
	 * deleteBank
	 */
	
	public List<Bank> getAllBank() throws Exception {
		Request req = create("getAllBank");
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<Bank>>() {
		});
	}
	
	public List<Bank> getByBankName(String name) throws Exception {
		Request req = create("getByBankName");
		req.addParam("name", name);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<Bank>>() {
		});
	}
	
	public Bank saveBank(Bank bank) throws Exception {
		Request req = create("saveBank");
		req.addParam("bank", bank);
		Response res = client.POST(req);
		return res.getDataAs(Bank.class);
	}
	
	public Bank getBankByCode(String code) throws Exception {
		Request req = create("getBankByCode");
		req.addParam("name", code);
		Response res = client.POST(req);
		return res.getDataAs(Bank.class);
	}
	
	public List<Bank> getBankByParentCode(String parentCode) throws Exception {
		Request req = create("getBankByParentCode");
		req.addParam("parentCode", parentCode);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<Bank>>() {
		});
	}
	
	public Bank findByBankCode(String bankCode) throws Exception {
		Request req = create("findByBankCode");
		req.addParam("bankCode", bankCode);
		Response res = client.POST(req);
		return res.getDataAs(Bank.class);
	}

	public boolean deleteBank(Bank bank) throws Exception {
		Request req = create("deleteBank");
		req.addParam("bank", bank);
		client.POST(req);
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
	
	public List<BankTransaction> getAllBankTransaction() throws Exception {
		Request req = create("getAllBankTransaction");
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<BankTransaction>>() {
		});
	}
	
	public List<BankTransaction> getByBankTransactionName(String name) throws Exception {
		Request req = create("getByBankTransactionName");
		req.addParam("name", name);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<BankTransaction>>() {
		});
	}
	
	public List<BankTransaction> getByBankRangeTotal(long total) throws Exception {
		Request req = create("getByBankRangeTotal");
		req.addParam("total", total);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<BankTransaction>>() {
		});
	}
	
	public List<BankTransaction> getByBankCode(String bankCode) throws Exception {
		Request req = create("getByBankCode");
		req.addParam("bankCode", bankCode);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<BankTransaction>>() {
		});
	}
	
	public List<BankTransaction> getTracsactionCode(String transactionCode) throws Exception {
		Request req = create("getTracsactionCode");
		req.addParam("transactionCode", transactionCode);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<BankTransaction>>() {
		});
	}
	
	public List<BankTransaction> getByBankType(boolean type) throws Exception {
		Request req = create("getByBankType");
		req.addParam("type", type);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<BankTransaction>>() {
		});
	}
	

	public BankTransaction saveBankTransaction(BankTransaction bankTransaction) throws Exception {
		Request req = create("saveBankTransaction");
		req.addParam("bankTransaction", bankTransaction);
		Response res = client.POST(req);
		return res.getDataAs(BankTransaction.class);
	}
	
	public List<BankTransaction> getBankTransactionByCode(String code) throws Exception {
		Request req = create("getBankTransactionByCode");
		req.addParam("code", code);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<BankTransaction>>() {
		});
	}
	
	public boolean deleteBankTransaction(BankTransaction bankTransaction) throws Exception {
		Request req = create("deleteBankTransaction");
		req.addParam("bankTransaction", bankTransaction);
		client.POST(req);
		return true;
	}
}