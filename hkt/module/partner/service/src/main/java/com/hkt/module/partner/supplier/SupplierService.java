package com.hkt.module.partner.supplier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.account.AccountService;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.core.ServiceCallback;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.partner.supplier.entity.PriceHistory;
import com.hkt.module.partner.supplier.entity.SuppliedProduct;
import com.hkt.module.partner.supplier.entity.Supplier;
import com.hkt.module.partner.supplier.repository.PriceHistoryRepository;
import com.hkt.module.partner.supplier.repository.SuppliedProductRepository;
import com.hkt.module.partner.supplier.repository.SupplierRepository;
import com.hkt.module.partner.supplier.util.SupplierScenario;
import com.hkt.module.partner.supplier.util.SupplierScenario.SupplierDetail;
import com.hkt.module.partner.supplier.util.SupplierScenario.SupplierDetail.SuppliedProductScenario;

@Service("SupplierService")
public class SupplierService {

	@Autowired
	SupplierRepository supplierRepository;

	@Autowired
	SuppliedProductRepository suppliedProductRepository;

	@Autowired
	PriceHistoryRepository priceHistoryRepository;

	@Autowired
	AccountService accountService;

	@Transactional
	public void deleteAll() throws Exception {
		supplierRepository.deleteAll();
		suppliedProductRepository.deleteAll();
		priceHistoryRepository.deleteAll();
	}

	@Transactional(readOnly = true)
	public List<Supplier> findSupplierByCode(String code) {
		return supplierRepository.findSupplierByCode(code);
	}

	@Transactional
	public void createScenarioSupplier(SupplierScenario scenario) throws Exception {
		for (SupplierDetail supplierDetail : scenario.getSuppliers()) {
			List<AccountMembership> accountMemberships = supplierDetail.getMemberships();
			if (accountMemberships != null) {
				for (AccountMembership accountMembership : accountMemberships) {
					accountService.saveAccountMembership(accountMembership);
				}
			}
			Supplier supplier = supplierDetail.getSupplier();
			supplier = saveSupplier(supplier);
			List<SuppliedProductScenario> suppliedProducts = supplierDetail.getSuppliedProducts();
			for (SuppliedProductScenario supplierProduct : suppliedProducts) {
				createSuppliedProductScenario(supplier, supplierProduct);
			}
		}
	}

	private void createSuppliedProductScenario(Supplier supplier, SuppliedProductScenario supplierProducts) {
		SuppliedProduct suppliedProduct = supplierProducts.getSuppliedProduct();
		suppliedProduct.setSupplierId(supplier.getId());
		suppliedProduct.setSupplierLoginId(supplier.getLoginId());
		suppliedProduct = suppliedProductRepository.save(suppliedProduct);
		List<PriceHistory> list = supplierProducts.getPriceHistories();
		for (PriceHistory priceHistory : list) {
			priceHistory.setSuppliedProductId(suppliedProduct.getId());
			priceHistory.setSupplierLoginId(supplier.getLoginId());
			priceHistoryRepository.save(priceHistory);
		}

	}

	@Transactional
	public Supplier saveSupplier(Supplier supplier) {
		return supplierRepository.save(supplier);
	}

	@Transactional
	public boolean deleteSupplier(Supplier supplier) {
		return deleteSupplierCallBack(supplier, null);
	}

	// khanhdd
	public boolean deleteSupplierCallBack(Supplier supplier, ServiceCallback<SupplierService> callBack) {
		if (supplier == null) {
			return false;
		} else {
			if (supplier.isRecycleBin()) {
				List<SuppliedProduct> lstSuppliedProduct = suppliedProductRepository.findBySupplierId(supplier.getId());
				for (int i = 0; i < lstSuppliedProduct.size(); i++) {
					SuppliedProduct sp = lstSuppliedProduct.get(i);
					priceHistoryRepository.deleteBySuppliedProductId(sp.getSupplierId());

				}
				suppliedProductRepository.deleteBySupplierId(supplier.getId());
				supplierRepository.delete(supplier);
				if (supplier.getName().indexOf("test") >= 0) {
					try {
						accountService.deleteAccountByLoginId(supplier.getLoginId());
					} catch (Exception e) {
					}
				}
			} else {
				// List<SuppliedProduct> lstSuppliedProduct =
				// suppliedProductRepository.findBySupplierId(supplier.getId());
				// for(int i =0;i< lstSuppliedProduct.size();i++){
				// SuppliedProduct sp = lstSuppliedProduct.get(i);
				// //lấy ra tất cả các thằng priceHistory có SuppliedProductID
				// List<PriceHistory> lstPriceHistory =
				// priceHistoryRepository.findBySuppliedProductId(sp.getSupplierId());
				// for(int j=0; j< lstPriceHistory.size() ; j++){
				// PriceHistory p = lstPriceHistory.get(j);
				// //cập nhât lại trạng thái RecycleBin (1) cho từng priceHistory
				// p.setRecycleBin(true);
				// priceHistoryRepository.save(p);
				// }
				// //cập nhật lại trạng thái (1) cho SuppliedProduct
				// sp.setRecycleBin(true);
				// suppliedProductRepository.save(sp);
				// }
				supplier.setRecycleBin(true);
				if (supplierRepository.save(supplier) != null) {
					return true;
				} else {
					return false;
				}
			}
		}

		if (callBack != null) {
			callBack.callback(this);
			return true;
		} else {
			return false;
		}
	}

	@Transactional(readOnly = true)
	public List<Supplier> searchSupplier(boolean interactive) {
		if (interactive) {
			return supplierRepository.findSupplierByInteractive(interactive);
		} else {
			return supplierRepository.findByOrganizationLoginId("hkt");
		}

	}

	@Transactional(readOnly = true)
	public Supplier getSupplierByCode(String code) {
		return supplierRepository.getSupplierByCode(code);
	}

	@Transactional(readOnly = true)
	public Supplier getByOrgLoginIdAndLoginId(String loginId, String orgLoginId) {
		return supplierRepository.getByOrgLoginIdAndLoginId(loginId, orgLoginId);
	}

	@Transactional(readOnly = true)
	public List<Supplier> findAllSuppliers() {
		return (List<Supplier>) supplierRepository.findByValueRecycleBin(false);
	}

	@Transactional
	public List<SuppliedProduct> findSuppliedProductBySupplierId(long supplierId) {
		return suppliedProductRepository.findBySupplierId(supplierId);
	}

	@Transactional
	public List<PriceHistory> findPriceHistoryBySuppliedProductId(long suppliedProductId) {
		return priceHistoryRepository.findBySuppliedProductId(suppliedProductId);
	}

	@Transactional(readOnly = true)
	public List<SuppliedProduct> getSuppliedProductBySupplier(long supplierId) {
		return suppliedProductRepository.findBySupplierId(supplierId);
	}

	@Transactional
	public SuppliedProduct createSuppliedProduct(Supplier supplier, SuppliedProduct suppliedProduct) {
		if (supplier != null) {
			suppliedProduct.setSupplierLoginId(supplier.getLoginId());
			suppliedProduct.setSupplierId(supplier.getId());
		}
		suppliedProduct = saveSuppliedProduct(suppliedProduct);
		createPriceHistory(suppliedProduct);
		return suppliedProduct;
	}

	@Transactional
	public SuppliedProduct saveSuppliedProduct(SuppliedProduct suppliedProduct) {
		return suppliedProductRepository.save(suppliedProduct);
	}

	@Transactional
	public PriceHistory createPriceHistory(SuppliedProduct suppliedProduct) {
		PriceHistory priceHistory = new PriceHistory();
		priceHistory.setSuppliedProductId(suppliedProduct.getId());
		priceHistory.setSupplierLoginId(suppliedProduct.getSupplierLoginId());
		priceHistory.setPrice(suppliedProduct.getLastPrice());
		priceHistory.setNote(suppliedProduct.getNote());
		return savePriceHistory(priceHistory);
	}

	@Transactional
	public PriceHistory savePriceHistory(PriceHistory priceHistory) {
		return priceHistoryRepository.save(priceHistory);
	}

	// khanhdd
	@Transactional
	public void deleteSuppliedProduct(SuppliedProduct suppliedProduct) {
		if (suppliedProduct != null) {
			if (suppliedProduct.isRecycleBin()) {
				priceHistoryRepository.deleteBySuppliedProductId(suppliedProduct.getId());
				suppliedProductRepository.delete(suppliedProduct);

			} else {
				List<PriceHistory> lstPriceHistory = priceHistoryRepository.findBySuppliedProductId(suppliedProduct
				    .getSupplierId());
				for (int i = 0; i < lstPriceHistory.size(); i++) {
					PriceHistory p = lstPriceHistory.get(i);
					p.setRecycleBin(true);
					priceHistoryRepository.save(p);
				}
				suppliedProduct.setRecycleBin(true);
				suppliedProductRepository.save(suppliedProduct);
			}
		}
	}

	@Transactional
	public List<PriceHistory> findPriceHistoryBySuppliedProductID(long suppliedProductId) {
		return priceHistoryRepository.findBySuppliedProductId(suppliedProductId);
	}

	@Transactional
	public void deleteTest(String test) {
		supplierRepository.deleteTest(test);
	}

}
