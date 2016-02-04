package com.hkt.module.warehouse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.core.ServiceCallback;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.core.entity.ReportTable;
import com.hkt.module.core.entity.SQLSelectQuery;
import com.hkt.module.product.repository.ProductRepository;
import com.hkt.module.warehouse.entity.IdentityProduct;
import com.hkt.module.warehouse.entity.IdentityProduct.ExportType;
import com.hkt.module.warehouse.entity.IdentityProduct.ImportType;
import com.hkt.module.warehouse.entity.ProductCode;
import com.hkt.module.warehouse.entity.ProductCodes;
import com.hkt.module.warehouse.entity.Warehouse;
import com.hkt.module.warehouse.entity.WarehouseInventory;
import com.hkt.module.warehouse.entity.WarehouseProfile;
import com.hkt.module.warehouse.repository.IdentityProductRepository;
import com.hkt.module.warehouse.repository.InventoryRepository;
import com.hkt.module.warehouse.repository.WarehouseProfileRepository;
import com.hkt.module.warehouse.repository.WarehouseRepository;
import com.hkt.module.warehouse.util.WarehouseScenario;

@Service("WarehouseService")
public class WarehouseService {

  @Autowired
  WarehouseRepository warehouseRepo;

  @Autowired
  InventoryRepository inventoryRepo;

  @Autowired
  WarehouseProfileRepository profileRepository;

  @Autowired
  ProductRepository productRepo;

  @Autowired
  IdentityProductRepository identityProductRepo;

  @PostConstruct
  public void onInit() {
  }

  @Transactional
  public Warehouse getWarehouseByWarehouseId(String warehouseId) {
    return warehouseRepo.getByWarehouseId(warehouseId);
  }

  @Transactional
  public Warehouse saveWarehouse(Warehouse warehouse) {
    return warehouseRepo.save(warehouse);
  }

  /** @param warehouseId */
  @Transactional
  public boolean deleteWarehouseByWarehouseId(String warehouseId) {
    Warehouse warehouse = warehouseRepo.getByWarehouseId(warehouseId);
    if (warehouse == null) {
      return false;
    } else {
      return deleteWarehouseCallBack(warehouse, null);
    }
  }

  /**
   * @param warehouse
   * @param callBack
   */
  @Transactional
  public boolean deleteWarehouseCallBack(Warehouse warehouse, ServiceCallback<WarehouseService> callBack) {
    if (callBack != null) {
      callBack.callback(this);
    }
    if (warehouse == null) {
      return false;
    } else {
      if (warehouse.isRecycleBin()) {
        warehouseRepo.delete(warehouse);
        return true;
      } else {
        warehouse.setRecycleBin(true);
        if (saveWarehouse(warehouse) != null)
          return true;
        else
          return false;
      }
    }

  }

  @Transactional(readOnly = true)
  public List<Warehouse> findWarehouses() {
    return (List<Warehouse>) warehouseRepo.findByWarehouseRecycleBin(false);
  }

  @Transactional(readOnly = true)
  public List<Warehouse> findWarehouseByWarehouseId(String name) {
    return warehouseRepo.findByWarehouseId(name.toLowerCase());
  }
  
  @Transactional(readOnly = true)
  public List<WarehouseInventory> findInventoryByCreatedBy(String createdBy) {
    return inventoryRepo.findInventoryByCreatedBy(createdBy);
  }

  @Transactional
  public WarehouseInventory saveInventory(WarehouseInventory sel) {
    return inventoryRepo.save(sel);
  }

  @Transactional
  public WarehouseInventory getInventoryById(Long id) {
    return inventoryRepo.findOne(id);
  }
  
  @Transactional
  public void deleteWarehouseInventory(WarehouseInventory warehouseInventory) {
    inventoryRepo.delete(warehouseInventory);
  }

  @Transactional
  public WarehouseInventory getInventoryByProduct(String productCode, String code) {
    return inventoryRepo.getInventoryByProduct(productCode, code);
  }
  
  @Transactional(readOnly = true)
  public List<WarehouseInventory> findWarehouseInventoryNotUsed(ProductCodes listProductCode,
  		Date date){
  	List<String> str = new ArrayList<String>();
  	for(ProductCode co : listProductCode.getList()){
  		str.add(co.getName());
  	}
  	Calendar c = Calendar.getInstance();
  	c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 00);
		c.set(Calendar.SECOND, 01);
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		c1.set(Calendar.HOUR_OF_DAY, 23);
		c1.set(Calendar.MINUTE, 59);
		c1.set(Calendar.SECOND, 59);
  	return inventoryRepo.findWarehouseInventoryNotUsed(str,c.getTime(), c1.getTime());
  }

  /**
   * @param inventory
   * @return
   */
  @Transactional
  public boolean deleteInventory(WarehouseInventory inventory) {
    return deleteInventoryCallBack(inventory, null);
  }

  @Transactional
  public WarehouseProfile saveWarehouseProfile(WarehouseProfile sel) {
    return profileRepository.save(sel);
  }

  @Transactional
  public WarehouseProfile getWarehouseProfileById(Long id) {
    return profileRepository.findOne(id);
  }

  @Transactional
  public WarehouseProfile getWarehouseProfileByProduct(String productCode, String code) {
    return profileRepository.getWarehouseProfileByProduct(productCode, code);
  }
  
  @Transactional
  public double getPriceReport(String productCode){
  	try {
	    return identityProductRepo.getPriceReport(productCode);
    } catch (Exception e) {
	    return 0;
    }
  }

  /**
   * @param inventory
   * @return
   */
  @Transactional
  public boolean deleteInventory(WarehouseProfile inventory) {
    profileRepository.delete(inventory);
    return true;
  }

  /**
   * @param inventory
   * @param callBack
   * @return
   */
  @Transactional
  public boolean deleteInventoryCallBack(WarehouseInventory inventory, ServiceCallback<WarehouseService> callBack) {
    inventoryRepo.delete(inventory);
    if (callBack != null) {
      callBack.callback(this);
    }
    return true;
  }

  @Transactional
  public void createScenario(WarehouseScenario scenario) throws Exception {

  }

  @Transactional
  public void deleteAll() throws Exception {
    warehouseRepo.deleteAll();
    inventoryRepo.deleteAll();
  }

  public String ping() {
    return "WarehouseService alive!!!";
  }

  @Transactional
  public ReportTable[] getReportTableInventory(SQLSelectQuery[] rQuery) {
    return inventoryRepo.reportQuery(rQuery);
  }

  @Transactional
  public IdentityProduct deleteIdentityProductExport(IdentityProduct identityProduct) {
    identityProduct.setExportType(ExportType.NotExport);
    identityProduct.setExportDate(null);
    identityProduct.setInvoiceItemIdExport(0);
    identityProduct.setShipmentExportCode(null);
    identityProduct.setInvoiceExportCode(null);
    identityProduct.setPriceExport(0);
    identityProduct = saveIdentityProduct(identityProduct);
//    if (identityProduct.getWarehouseCode() != null) {
//      WarehouseProfile profile = profileRepository
//          .getWarehouseProfileByProduct(
//              identityProduct.getProductCode(),
//              new SimpleDateFormat("ddMMyyyy").format(identityProduct.getExportDate())
//                  + identityProduct.getWarehouseCode());
//      profile.addReduction(-1, (-1) * identityProduct.getPriceExport() * identityProduct.getCurrencyExportRate());
//      profileRepository.save(profile);
//    }
    return identityProduct;
  }

  @Transactional
  public IdentityProduct saveIdentityProduct(IdentityProduct identityProduct) {
//    if (identityProduct.getId() == null) {
//      WarehouseInventory inventory = inventoryRepo.getInventoryByProduct(identityProduct.getProductCode(),
//          new SimpleDateFormat("ddMMyyyy").format(identityProduct.getImportDate()));
//      if (inventory == null) {
//        inventory = new WarehouseInventory();
//        inventory.setCode(new SimpleDateFormat("ddMMyyyy").format(identityProduct.getImportDate()));
//        inventory.setStartDate(identityProduct.getImportDate());
//        inventory.setProductCode(identityProduct.getProductCode());
//        if(identityProduct.getProfit()>0){
//        	inventory.setIncreaseQuantity(1+identityProduct.getProfit());
//        }else {
//        	inventory.setIncreaseQuantity(1);	
//				}
//        
//        inventory.setIncreaseValue(identityProduct.getPrice() * identityProduct.getCurrencyRate());
//        inventory.setReductionQuantity(0);
//        inventory.setReductionValue(0);
//        inventory.setProfitQuantity(-1);
//        inventory.setProfitValue(identityProduct.getPrice() * identityProduct.getCurrencyRate());
//        inventory.setProfitVitualQuantity(-1);
//        inventory.setProfitVitualValue(identityProduct.getPrice() * identityProduct.getCurrencyRate());
//      } else {
//      	if(identityProduct.getProfit()>0){
//      		 inventory.addIncrease(1+identityProduct.getProfit(), identityProduct.getPrice() * identityProduct.getCurrencyRate());
//        }else {
//        	 inventory.addIncrease(1, identityProduct.getPrice() * identityProduct.getCurrencyRate());
//				}
//       
//      }
//      inventoryRepo.save(inventory);
//      if (identityProduct.getWarehouseCode() != null) {
//        WarehouseProfile profile = profileRepository.getWarehouseProfileByProduct(
//            identityProduct.getProductCode(),
//            new SimpleDateFormat("ddMMyyyy").format(identityProduct.getImportDate())
//                + identityProduct.getWarehouseCode());
//        if (profile == null) {
//          profile = new WarehouseProfile();
//          profile.setCode(new SimpleDateFormat("ddMMyyyy").format(identityProduct.getImportDate())
//              + identityProduct.getWarehouseCode());
//          profile.setStartDate(identityProduct.getImportDate());
//          profile.setProductCode(identityProduct.getProductCode());
//          profile.setWarehouseCode(identityProduct.getWarehouseCode());
//          profile.setIncreaseQuantity(1);
//          profile.setIncreaseValue(identityProduct.getPrice() * identityProduct.getCurrencyRate());
//          profile.setReductionQuantity(0);
//          profile.setReductionValue(0);
//          profile.setProfitQuantity(-1);
//          profile.setProfitValue(identityProduct.getPrice() * identityProduct.getCurrencyRate());
//          profile.setProfitVitualQuantity(-1);
//          profile.setProfitVitualValue(identityProduct.getPrice() * identityProduct.getCurrencyRate());
//        } else {
//          profile.addIncrease(1, identityProduct.getPrice() * identityProduct.getCurrencyRate());
//        }
//        profileRepository.save(profile);
//      }
//    } else {
//      if (identityProduct.getExportDate() != null) {
//        WarehouseInventory inventory = inventoryRepo.getInventoryByProduct(identityProduct.getProductCode(),
//            new SimpleDateFormat("ddMMyyyy").format(identityProduct.getExportDate()));
//        if (inventory == null) {
//          inventory = new WarehouseInventory();
//          inventory.setCode(new SimpleDateFormat("ddMMyyyy").format(identityProduct.getExportDate()));
//          inventory.setStartDate(identityProduct.getExportDate());
//          inventory.setProductCode(identityProduct.getProductCode());
//          inventory.setIncreaseQuantity(0);
//          inventory.setIncreaseValue(0);
//          inventory.setReductionQuantity(1);
//          inventory.setReductionValue(identityProduct.getPriceExport() * identityProduct.getCurrencyExportRate());
//          inventory.setProfitQuantity(-1);
//          inventory.setProfitValue((-1) * identityProduct.getPrice() * identityProduct.getCurrencyExportRate());
//        } else {
//          	inventory.addReduction(1, identityProduct.getPriceExport() * identityProduct.getCurrencyExportRate());
//
//        }
//        inventoryRepo.save(inventory);
//        if (identityProduct.getWarehouseCode() != null) {
//          WarehouseProfile profile = profileRepository.getWarehouseProfileByProduct(
//              identityProduct.getProductCode(),
//              new SimpleDateFormat("ddMMyyyy").format(identityProduct.getImportDate())
//                  + identityProduct.getWarehouseCode());
//          if (profile == null) {
//            profile = new WarehouseProfile();
//            profile.setCode(new SimpleDateFormat("ddMMyyyy").format(identityProduct.getImportDate())
//                + identityProduct.getWarehouseCode());
//            profile.setStartDate(identityProduct.getImportDate());
//            profile.setProductCode(identityProduct.getProductCode());
//            profile.setWarehouseCode(identityProduct.getWarehouseCode());
//            profile.setIncreaseQuantity(0);
//            profile.setIncreaseValue(0);
//            profile.setReductionQuantity(1);
//            profile.setReductionValue(identityProduct.getPriceExport() * identityProduct.getCurrencyExportRate());
//            profile.setProfitQuantity(-1);
//            profile.setProfitValue((-1) * identityProduct.getPrice() * identityProduct.getCurrencyExportRate());
//          } else {
//            profile.addReduction(1, identityProduct.getPriceExport() * identityProduct.getCurrencyExportRate());
//          }
//          profileRepository.save(profile);
//        }
//      }
//    }

    return identityProductRepo.save(identityProduct);
  }

  /**
   * @param identityProduct
   */
  @Transactional
  public void deleteIdentityProduct(IdentityProduct identityProduct) {
//    WarehouseInventory inventory = inventoryRepo.getInventoryByProduct(identityProduct.getProductCode(),
//        new SimpleDateFormat("ddMMyyyy").format(identityProduct.getImportDate()));
//    if (inventory != null) {
//      inventory.addIncrease(-1, (-1) * identityProduct.getPrice() * identityProduct.getCurrencyRate());
//      inventoryRepo.save(inventory);
//    }
//
//    if (identityProduct.getWarehouseCode() != null) {
//      WarehouseProfile profile = profileRepository
//          .getWarehouseProfileByProduct(
//              identityProduct.getProductCode(),
//              new SimpleDateFormat("ddMMyyyy").format(identityProduct.getImportDate())
//                  + identityProduct.getWarehouseCode());
//      inventory.addIncrease(-1, (-1) * identityProduct.getPrice() * identityProduct.getCurrencyRate());
//      profileRepository.save(profile);
//    }
    identityProductRepo.delete(identityProduct.getId());
  }

  @Transactional(readOnly = true)
  public IdentityProduct getIdentityProductById(long id) {
    return identityProductRepo.findOne(id);
  }

  @Transactional(readOnly = true)
  public List<IdentityProduct> getByImportType(ImportType importType) {
    return identityProductRepo.getByImportType(importType);
  }

  @Transactional(readOnly = true)
  public List<IdentityProduct> getByExportType(ExportType exportType) {
    return identityProductRepo.getByExportType(exportType);
  }

  @Transactional(readOnly = true)
  public List<IdentityProduct> getByProductCode(String productCode) {
    return identityProductRepo.getByProductCode(productCode);
  }

  @Transactional(readOnly = true)
  public List<IdentityProduct> getByImportTypeAndWarehouse(ImportType importType, String warehouseCode) {
    return identityProductRepo.getByImportTypeAndWarehouse(importType, warehouseCode, ExportType.NotExport);
  }

  @Transactional(readOnly = true)
  public List<IdentityProduct> getByImportTypeAndWarehouseIsNull(ImportType importType) {
    return identityProductRepo.getByImportTypeAndWarehouseIsNull(importType, ExportType.NotExport);
  }

  @Transactional(readOnly = true)
  public List<IdentityProduct> getByImportTypeAndProductAndWarehouse(ImportType importType, String productCode,
      String warehouseCode) {
    return identityProductRepo.getByImportTypeAndProductAndWarehouse(importType, productCode, warehouseCode,
        ExportType.NotExport);
  }

  @Transactional(readOnly = true)
  public List<IdentityProduct> getByImportTypeAndProductAndWarehouseIsNull(ImportType importType, String productCode) {
    return identityProductRepo.getByImportTypeAndProductAndWarehouseIsNull(importType, productCode,
        ExportType.NotExport);
  }

  @Transactional(readOnly = true)
  public List<IdentityProduct> getByExportTypeAndProductAndWarehouse(String productCode, String warehouseCode) {
    return identityProductRepo.getByImportTypeAndProductAndWarehouse(ImportType.Import, productCode, warehouseCode,
        ExportType.Export);
  }

  @Transactional(readOnly = true)
  public List<IdentityProduct> getByExportTypeAndProductAndWarehouseIsNull(String productCode) {
    return identityProductRepo.getByImportTypeAndProductAndWarehouseIsNull(ImportType.Import, productCode,
        ExportType.Export);
  }

  @Transactional(readOnly = true)
  public List<IdentityProduct> findByProductCodeAndExportTypeAndWarehoseCode(String productCode, String warehouseCode) {
    return identityProductRepo.findByProductCodeAndExportTypeAndWarehoseCode(productCode, ExportType.NotExport,
        warehouseCode);
  }

  @Transactional(readOnly = true)
  public List<IdentityProduct> getByInvoiceExportCode(String invoiceExportCode) {
    return identityProductRepo.getByInvoiceExportCode(invoiceExportCode);
  }

  @Transactional(readOnly = true)
  public List<IdentityProduct> getByImportTypeAndProductCode(ImportType importType, String productCode) {
    return identityProductRepo.getByImportTypeAndProductCode(importType, ExportType.NotExport, productCode);
  }

  @Transactional(readOnly = true)
  public List<IdentityProduct> getByInvoiceCode(String invoiceCode) {
    return identityProductRepo.getByInvoiceCode(invoiceCode);
  }

  @Transactional(readOnly = true)
  public List<IdentityProduct> getAllIdentityProduct() {
    return (List<IdentityProduct>) identityProductRepo.findAll();
  }

  @Transactional(readOnly = true)
  public List<IdentityProduct> findByProductCodeAndExportType(String productCode) {
    return identityProductRepo.findByProductCodeAndExportType(productCode, ExportType.NotExport);
  }

  @Transactional(readOnly = true)
  public List<IdentityProduct> getByInvoiceItemIdExport(long invoiceItemIdExport) {
    return identityProductRepo.getByInvoiceItemIdExport(invoiceItemIdExport);
  }

  @Transactional(readOnly = true)
  public List<IdentityProduct> getByInvoiceItemIdImport(long invoiceItemIdImport) {
    return identityProductRepo.getByInvoiceItemIdImport(invoiceItemIdImport);
  }

  @Transactional(readOnly = true)
  public List<IdentityProduct> getByShipmentImportCode(String shipmentImportCode) {
    return identityProductRepo.getByShipmentImportCode(shipmentImportCode);
  }

  @Transactional(readOnly = true)
  public List<IdentityProduct> getByShipmentExportCode(String shipmentExportCode) {
    return identityProductRepo.getByShipmentExportCode(shipmentExportCode);
  }

  @Transactional
  public void deleteByInvoiceCode(String invoiceCode) {
    identityProductRepo.deleteByInvoiceCode(invoiceCode);
  }

  /**
   * @param invoiceCode
   */
  @Transactional
  public void deleteIdentityProductAttributeByInvoiceCode(String invoiceCode) {
    identityProductRepo.deleteIdentityProductAttributeByInvoiceCode(invoiceCode);
  }

  /**
   * @param invoiceItemId
   */
  @Transactional
  public void deleteByInvoiceItemId(long invoiceItemId) {
    identityProductRepo.deleteByInvoiceItemId(invoiceItemId);
  }

  public FilterResult<IdentityProduct> query(FilterQuery query) {
    if (query == null)
      query = new FilterQuery();
    return identityProductRepo.query(query);
  }

  public ReportTable[] reportQuery(SQLSelectQuery[] query) {
    return identityProductRepo.reportQuery(query);
  }

  @Transactional
  public void deleteTest(String test) {
    identityProductRepo.deleteTest(test);
    inventoryRepo.deleteTest(test);
    warehouseRepo.deleteTest(test);
  }

}