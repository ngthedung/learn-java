package com.hkt.module.partner.customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.account.AccountService;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.core.ServiceCallback;
import com.hkt.module.partner.customer.entity.Credit;
import com.hkt.module.partner.customer.entity.CreditDetail;
import com.hkt.module.partner.customer.entity.CreditTransaction;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.customer.entity.Point;
import com.hkt.module.partner.customer.entity.PointConversionRule;
import com.hkt.module.partner.customer.entity.PointDetail;
import com.hkt.module.partner.customer.entity.PointTransaction;
import com.hkt.module.partner.customer.repository.CreditRepository;
import com.hkt.module.partner.customer.repository.CreditTransactionRepository;
import com.hkt.module.partner.customer.repository.CustomerRepository;
import com.hkt.module.partner.customer.repository.PointRatioRepository;
import com.hkt.module.partner.customer.repository.PointRepository;
import com.hkt.module.partner.customer.repository.PointTransactionRepository;
import com.hkt.module.partner.customer.util.CustomerScenario;
import com.hkt.module.partner.customer.util.CustomerScenario.Customers;
import com.hkt.module.partner.customer.util.CustomerScenario.Customers.CreditScenario;
import com.hkt.module.partner.customer.util.CustomerScenario.Customers.PointScenario;

@Service("CustomerService")
public class CustomerService {

	@Autowired
	AccountService accountService;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	PointRepository pointRepository;

	@Autowired
	PointRatioRepository pointRatioRepository;

	@Autowired
	PointTransactionRepository pointTransactionRepository;

	@Autowired
	CreditRepository creditRepository;

	@Autowired
	CreditTransactionRepository creditTransactionRepository;

	@Transactional
	public void createScenarioCustomer(CustomerScenario scenario) throws Exception {
		PointConversionRule pointRatio = scenario.getRatio();
		pointRatio = savePointConversionRatio(pointRatio);

		List<Customers> customerAll = scenario.getCustomers();
		for (Customers customers : customerAll) {
			List<AccountMembership> accountMemberships = customers.getMemberships();
			if (accountMemberships != null) {
				for (AccountMembership accountMembership : accountMemberships) {
					accountService.saveAccountMembership(accountMembership);
				}
			}
			Customer customer = customers.getCustomer();
			customer = saveCustomer(customer);
			PointScenario pointScenario = customers.getPoints();
			Point point = pointScenario.getPoint();
			point = createPoint(customer, point);
			List<PointTransaction> pointTransactions = pointScenario.getTransactions();
			for (PointTransaction pointTransaction : pointTransactions) {
				pointTransaction.setPointConversionRuleId(pointRatio.getId());
			}
			savePointDetail(new PointDetail(point, pointTransactions));
			CreditScenario creditScenario = customers.getCredits();
			Credit credit = creditScenario.getCredit();
			credit = createCredit(customer, credit);
			List<CreditTransaction> creditTransactions = creditScenario.getTransactions();
			saveCreditDetail(new CreditDetail(credit, creditTransactions));
		}
	}

	@Transactional(readOnly = true)
	public List<PointConversionRule> getPointConversionRules() {
		return (List<PointConversionRule>) pointRatioRepository.findByValueRecycleBin(false);
	}

	@Transactional
	public List<Customer> findCustomerByCode(String code) {
		return customerRepository.findCustomerByCode(code);
	}

	@Transactional
	public PointConversionRule savePointConversionRatio(PointConversionRule pointRatio) {
		return pointRatioRepository.save(pointRatio);
	}

	@Transactional
	public PointConversionRule getConversionRulePointToMoney(double pointToTrigger) {
		return pointRatioRepository.getConversionRulePointToMoney(pointToTrigger, new Date());
	}

	@Transactional
	public PointConversionRule getConversionRuleMoneyToPoint() {
		return pointRatioRepository.getConversionRuleMoneyToPoint(new Date());
	}

	@Transactional
	public PointConversionRule getConversionRuleById(long id) {
		return pointRatioRepository.findOne(id);
	}

	// khanhdd
	@Transactional
	public boolean deletePointConversionRule(PointConversionRule pointRatio) {
		if (pointRatio == null) {
			return false;
		} else {
			if (pointRatio.isRecycleBin()) {
				return deletePointConversionRuleCallBack(pointRatio, null);
			} else {
				pointRatio.setRecycleBin(true);
				pointRatio.setDateTo(new Date());
				if (pointRatioRepository.save(pointRatio) != null) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	@Transactional
	public boolean deletePointConversionRuleCallBack(PointConversionRule pointRatio,
	    ServiceCallback<CustomerService> callBack) {
		pointRatioRepository.delete(pointRatio);
		if (callBack != null) {
			callBack.callback(this);
		}
		return true;
	}

	@Transactional(readOnly = true)
	public List<PointConversionRule> findPointConversionRuleByName(String name) {
		name = name.replace('*', '%');
		return pointRatioRepository.findByName(name);
	}

	@Transactional
	public Customer saveCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	// khanhdd
	@Transactional
	public boolean deleteCustomer(Customer customer) {
		if (customer == null) {
			return false;
		} else {
			return deleteCustomerCallBack(customer, null);
		}
	}

	@Transactional(readOnly = true)
	public List<Customer> searchCustomer(boolean interactive) {
		if (interactive) {
			return customerRepository.findCustomerByInteractive(interactive);
		} else {
			return customerRepository.findByOrganizationLoginId("hkt");
		}
	}

	@Transactional(readOnly = true)
	public Customer findCustomerByLoginIdAndOrganId(String loginId, String organizationLoginId) {
		Customer customer = customerRepository.findCustomerByLoginIdAndOrganId(loginId, organizationLoginId);
		return customer;
	}

	@Transactional(readOnly = true)
	public Customer getCustomerByCode(String code) {
		Customer customer = customerRepository.getCustomerByCode(code);
		return customer;
	}

	// khanhdd
	public boolean deleteCustomerCallBack(Customer customer, ServiceCallback<CustomerService> callBack) {
		if (customer.isRecycleBin()) {
			creditRepository.deleteByCustomerId(customer.getId());
			creditTransactionRepository.deleteByLoginId(customer.getLoginId());
			pointRepository.deleteByCustomerId(customer.getId());
			pointTransactionRepository.deleteByLoginId(customer.getLoginId());

			customerRepository.delete(customer);
			if (customer.getName().indexOf("test") >= 0) {
				try {
					accountService.deleteAccountByLoginId(customer.getLoginId());
				} catch (Exception e) {
				}
			}
		} else {
			Credit credit = creditRepository.getByCustomerId(customer.getId());
			if (credit != null) {
				credit.setRecycleBin(true);

				creditRepository.save(credit);
				List<CreditTransaction> lstCreditTransaction = creditTransactionRepository.findByCreditId(credit.getId());
				for (int i = 0; i < lstCreditTransaction.size(); i++) {
					CreditTransaction c = lstCreditTransaction.get(i);
					c.setRecycleBin(true);
					creditTransactionRepository.save(c);
				}
			}

			Point point = pointRepository.getByCustomerId(customer.getId());
			if (point != null) {
				point.setRecycleBin(true);
				pointRepository.save(point);

				List<PointTransaction> lstPointTransaction = pointTransactionRepository.findByPointId(point.getId());
				for (int i = 0; i < lstPointTransaction.size(); i++) {
					PointTransaction p = lstPointTransaction.get(i);
					p.setRecycleBin(true);
					pointTransactionRepository.save(p);

				}
			}

			customer.setRecycleBin(true);
			customerRepository.save(customer);
		}
		if (callBack != null) {
			callBack.callback(this);
		}
		return true;
	}

	@Transactional
	public Credit createCredit(Customer customer, Credit credit) {
		credit.setCustomerId(customer.getId());
		credit.setLoginId(credit.getLoginId());
		return creditRepository.save(credit);
	}

	@Transactional
	public Credit saveCredit(Credit credit) {
		return creditRepository.save(credit);
	}

	@Transactional
	public CreditDetail saveCreditDetail(CreditDetail creditDetail) {
		Credit credit = creditDetail.getCredit();
		double numberPoint = 0;
		List<CreditTransaction> creditTransactions = creditDetail.getCreditTransactions();
		for (CreditTransaction creditTransaction : creditTransactions) {
			if (creditTransaction.getId() == null) {
				creditTransaction.setLoginId(credit.getLoginId());
				creditTransaction.setCreditId(credit.getId());
				numberPoint = numberPoint + creditTransaction.getAmount();
			}
		}
		credit.setCredit(numberPoint);
		creditTransactionRepository.save(creditTransactions);
		creditRepository.save(credit);
		return creditDetail;
	}

	@Transactional
	public CreditDetail getCreditDetailById(long creditId) {
		Credit credit = creditRepository.findOne(creditId);
		List<CreditTransaction> creditTransactions = creditTransactionRepository.findByCreditId(creditId);
		CreditDetail creditDetail = new CreditDetail(credit, creditTransactions);
		return creditDetail;
	}

	@Transactional
	public Credit getCreditByCustomerId(long customerId) {
		Credit credit = creditRepository.getByCustomerId(customerId);
		if (credit == null) {
			Customer c = customerRepository.findOne(customerId);
			credit = new Credit();
			credit.setLoginId(c.getLoginId());
			credit.setCustomerId(customerId);
			credit.setCredit(0);
			credit = creditRepository.save(credit);
		}
		return credit;
	}

	@Transactional(readOnly = true)
	public Credit getCreditById(long id) {
		return creditRepository.findOne(id);
	}

	@Transactional(readOnly = true)
	public List<Credit> getAllCredits() {
		return creditRepository.findByValueRecycleBin(false);
	}

	@Transactional
	public CreditTransaction saveCreditTransaction(CreditTransaction creditTransaction) {
		return creditTransactionRepository.save(creditTransaction);
	}

	@Transactional
	public List<CreditTransaction> findCreditTransactionByCreditId(long creditId) {
		return creditTransactionRepository.findByCreditId(creditId);
	}

	// khanhdd
	// long edit: cho phép xóa luôn
	@Transactional
	public boolean deleteCreditTransaction(CreditTransaction creditTransaction) {
		if (creditTransaction == null) {
			return false;
		} else {
			Credit credit = creditRepository.findOne(creditTransaction.getCreditId());
			double numberCredit = credit.getCredit() - creditTransaction.getAmount();
			credit.setCredit(numberCredit);
			creditRepository.save(credit);
			creditTransactionRepository.delete(creditTransaction);
			return true;
		}

	}

	@Transactional
	public Point createPoint(Customer customer, Point point) {
		if (customer != null)
			point.setLoginId(customer.getLoginId());
		point.setCustomerId(customer.getId());
		return pointRepository.save(point);
	}

	@Transactional
	public Point savePoint(Point point) {
		return pointRepository.save(point);
	}

	@Transactional(readOnly = true)
	public List<Point> getAllPoints() {
		return pointRepository.findByValueRecycleBin(false);
	}

	@Transactional(readOnly = true)
	public Point getPointById(long id) {
		return pointRepository.findOne(id);
	}

	@Transactional
	public PointDetail savePointDetail(PointDetail pointDetail) {
		Point point = pointDetail.getPoint();
		double numberPoint = 0;
		List<PointTransaction> pointTransactions = pointDetail.getPointTransactions();
		for (PointTransaction pointTransaction : pointTransactions) {
			if (pointTransaction.getId() == null) {
				numberPoint += pointTransaction.getPoint();
				pointTransaction.setLoginId(point.getLoginId());
				pointTransaction.setPointId(point.getId());
			}
		}
		point.setPoint(numberPoint);
		pointTransactionRepository.save(pointTransactions);
		pointRepository.save(point);
		return pointDetail;
	}

	@Transactional
	public PointDetail getPointDetailById(long pointId) {
		Point point = pointRepository.findOne(pointId);
		List<PointTransaction> pointTransactions = pointTransactionRepository.findByPointId(pointId);
		PointDetail pointDetail = new PointDetail(point, pointTransactions);
		return pointDetail;
	}

	@Transactional
	public Point getPointByCustomerId(long customerId) {
		Point point = pointRepository.getByCustomerId(customerId);
		if (point == null) {
			Customer c = customerRepository.findOne(customerId);
			point = new Point();
			point.setLoginId(c.getLoginId());
			point.setCustomerId(customerId);
			point.setPoint(0);
			point = pointRepository.save(point);
		}
		return point;
	}

	@Transactional
	public PointTransaction savePointTransaction(PointTransaction pointTransaction) {
		return pointTransactionRepository.save(pointTransaction);
	}

	@Transactional
	public List<PointTransaction> findPointTransactionByPointId(long pointId) {
		return pointTransactionRepository.findByPointId(pointId);
	}

	@Transactional
	public List<PointTransaction> findByInvoiceId(long invoiceId) {
		return pointTransactionRepository.findByInvoiceId(invoiceId);
	}

	// khanhdd
	// long edit: cho phép xóa luôn
	@Transactional
	public boolean deletePointTransaction(PointTransaction pointTransaction) {
		if (pointTransaction == null) {
			return false;
		} else {
			Point point = pointRepository.findOne(pointTransaction.getPointId());
			double numberPoint = point.getPoint() - pointTransaction.getPoint();
			point.setPoint(numberPoint);
			pointRepository.save(point);
			pointTransactionRepository.delete(pointTransaction);
			return true;
		}
	}

	// khanhdd
	// long edit: cho phép xóa luôn
	@Transactional
	public boolean deletePointTransactionByInvoiceId(long invoiceId) {
		List<PointTransaction> pointTransactions = pointTransactionRepository.findByInvoiceId(invoiceId);
		for (PointTransaction pointTransaction : pointTransactions) {
			Point point = pointRepository.findOne(pointTransaction.getPointId());
			double numberPoint = point.getPoint() - pointTransaction.getPoint();
			point.setPoint(numberPoint);
			pointRepository.save(point);
			pointTransactionRepository.deleteByInvoiceId(invoiceId);
		}
		return true;
	}

	@Transactional
	public PointTransaction getByInvoiceId(long invoiceId) {
		return pointTransactionRepository.getByInvoiceId(invoiceId);
	}

	@Transactional
	public CreditTransaction getCreditTransactionByInvoiceId(long invoiceId) {
		return creditTransactionRepository.getByInvoiceId(invoiceId);
	}

	// khanhdd
	// long edit: cho phép xóa luôn
	@Transactional
	public boolean deleteCreditTransactionByInvoiceId(long invoiceId) {
		CreditTransaction creditTransaction = creditTransactionRepository.getByInvoiceId(invoiceId);
		if (creditTransaction != null) {
			Credit credit = creditRepository.findOne(creditTransaction.getCreditId());
			double numberCredit = credit.getCredit() - creditTransaction.getAmount();
			credit.setCredit(numberCredit);
			creditRepository.save(credit);
			creditTransactionRepository.deleteByInvoiceId(invoiceId);
			return true;
		} else {
			return false;
		}

	}

	@Transactional
	public void deleteAll() throws Exception {
		customerRepository.deleteAll();
		pointRepository.deleteAll();
		pointRatioRepository.deleteAll();
		pointTransactionRepository.deleteAll();
		creditRepository.deleteAll();
		creditTransactionRepository.deleteAll();
	}

	@Transactional(readOnly = true)
	public PointDetail getPointDetailByCustomer(long customerId) {
		Point point = pointRepository.getByCustomerId(customerId);
		List<PointTransaction> pointTransactions = new ArrayList<PointTransaction>();
		if (point != null) {
			pointTransactions = pointTransactionRepository.findByPointId(point.getId());
		}
		PointDetail pointDetail = new PointDetail(point, pointTransactions);
		return pointDetail;
	}

	@Transactional(readOnly = true)
	public CreditDetail getCreditDetailByCustomer(long customerId) {
		Credit credit = creditRepository.getByCustomerId(customerId);
		List<CreditTransaction> creditTransactions = new ArrayList<CreditTransaction>();
		if (credit != null) {
			creditTransactions = creditTransactionRepository.findByCreditId(credit.getId());
		}
		CreditDetail creditDetail = new CreditDetail(credit, creditTransactions);
		return creditDetail;
	}

	@Transactional
	public void deleteTest(String test) {
		customerRepository.deleteTest(test);
		creditRepository.deleteTest(test);
		creditTransactionRepository.deleteTest(test);
		pointRatioRepository.deleteTest(test);
		pointRepository.deleteTest(test);
		pointTransactionRepository.deleteTest(test);
	}

}
