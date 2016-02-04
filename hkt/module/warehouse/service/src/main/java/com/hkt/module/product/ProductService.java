package com.hkt.module.product;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.account.AccountService;
import com.hkt.module.cms.CMSService;
import com.hkt.module.cms.entity.Node;
import com.hkt.module.cms.entity.NodeDetail;
import com.hkt.module.core.ServiceCallback;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.core.search.FSSearchService;
import com.hkt.module.core.search.SearchQuery;
import com.hkt.module.core.search.SearchResult;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductDetail;
import com.hkt.module.product.entity.ProductPrice;
import com.hkt.module.product.entity.ProductPriceType;
import com.hkt.module.product.entity.ProductTag;
import com.hkt.module.product.entity.ProductTagList;
import com.hkt.module.product.entity.Tax;
import com.hkt.module.product.repository.ProductPriceRepository;
import com.hkt.module.product.repository.ProductPriceTypeRepository;
import com.hkt.module.product.repository.ProductRepository;
import com.hkt.module.product.repository.ProductTagRepository;
import com.hkt.module.product.repository.TaxRepository;
import com.hkt.module.product.util.ProductScenario;
import com.hkt.module.promotion.repository.PromotionGiveawaysRepository;
import com.hkt.module.promotion.repository.PromotionRepository;
import com.hkt.module.warehouse.repository.InventoryRepository;

@Service("ProductService")
public class ProductService {
  @Autowired
  ProductRepository productRepo;

  @Autowired
  private TaxRepository taxRepository;

  @Autowired
  private ProductTagRepository productTagRepository;

  @Autowired
  private ProductPriceRepository productPriceRepo;

  @Autowired
  private ProductPriceTypeRepository productPriceTypeRepository;

  @Autowired
  InventoryRepository inRepo;

  @Autowired
  private PromotionRepository promotionRepository;

  @Autowired
  private PromotionGiveawaysRepository pGiveawaysRepository;

  @Autowired
  private AccountService accountService;  
  
  @Autowired
  private FSSearchService searchService;

  @Autowired
  CMSService cmsService;


  private String cmsBasePath = "warehouse/products";

  @PostConstruct
  public void onInit() {
  }

  @Transactional(readOnly = true)
  public List<ProductPrice> getAllProductPrices() {
    return productPriceRepo.findByProductRecycleBin(false);
  }
  
  @Transactional(readOnly = true)
  public List<ProductPrice> findByProductPriceByTag(String tag, String type, String currencyUnit) {
    return productPriceRepo.findByProductPriceByTag(tag, type, currencyUnit);
  }

  @Transactional
  public ProductPrice saveProductPrice(ProductPrice productPrice) {
    return productPriceRepo.save(productPrice);
  }

  /** Delete ProductPrice */
  @Transactional
  public boolean deleteProductPrice(ProductPrice productPrice) {
    if (productPrice == null) {
      return false;
    } else {
    	 productPriceRepo.delete(productPrice);
       return true;
    }
  }

  @Transactional(readOnly = true)
  public List<ProductPrice> searchByProductPriceType(String type) {
    return productPriceRepo.findByProductPriceType(type);
  }

  @Transactional(readOnly = true)
  public List<ProductPrice> searchByProductCode(String code) {
    return productPriceRepo.findByProductCode(code.toLowerCase());
  }
  
  @Transactional(readOnly = true)
  public List<Product> getProductByBarcode(String description) {
    return productRepo.getProductByBarcode(description);
  }

  @Transactional(readOnly = true)
  public List<ProductPriceType> getProductPriceTypes() {
    return productPriceTypeRepository.findByProductPriceTypeRecycleBin(false);
  }

  @Transactional(readOnly = true)
  public ProductPriceType getProductPriceTypeByType(String type) {
    return productPriceTypeRepository.getByType(type);
  }

  @Transactional(readOnly = true)
  public List<ProductPriceType> searchByType(String type) {
    return productPriceTypeRepository.searchByType(type.toLowerCase());
  }

  @Transactional
  public ProductPriceType saveProductPriceType(ProductPriceType productPriceType) {
    return productPriceTypeRepository.save(productPriceType);
  }

  @Transactional
  public ProductPrice getByProductAndProductPriceType(String code, String type, String currencyUnit) {
    return productPriceRepo.getByProductAndProductPriceType(code, type, currencyUnit);
  }
  
//  @Transactional
//  public ProductPrice getByProductAndProductPriceType(String code, String type) {
//    return productPriceRepo.getByProductAndProductPriceType(code, type);
//  }

  /** Delete ProductPriceType */
  @Transactional
  public boolean deleteProductPriceType(ProductPriceType productPriceType) throws Exception {
    return deleteProductPriceTypeCallBack(productPriceType, null);
  }

  /**
   * @param productPriceType
   * @param callBack
   * @return boolean
   */
  @Transactional
  public boolean deleteProductPriceTypeCallBack(ProductPriceType productPriceType,
      ServiceCallback<ProductService> callBack) {
    if (callBack != null) {
      callBack.callback(this);
    }
    if (productPriceType == null) {
      return false;
    } else {
      if (productPriceType.isRecycleBin()) {
        productPriceRepo.deleteByProductPriceType(productPriceType);
        productPriceTypeRepository.delete(productPriceType);
        return true;
      } else {
        try {
          List<ProductPrice> productPrices = productPriceRepo.getProductPriceByProductPriceType(productPriceType);
          for (ProductPrice pp : productPrices) {
            pp.setRecycleBin(true);
            productPriceRepo.save(pp);
          }
          productPriceType.setRecycleBin(true);
          productPriceTypeRepository.save(productPriceType);
          return true;
        } catch (Exception e) {
          return false;
        }
      }
    }
  }

  @Transactional(readOnly = true)
  public List<Tax> getTaxs() {
    return (List<Tax>) taxRepository.findByTaxRecycleBin(false);
  }

  @Transactional
  public Tax getTaxByCode(String code) {
    return taxRepository.getByCode(code);
  }

  @Transactional
  public Tax saveTax(Tax tax) {
    return taxRepository.save(tax);
  }

  /** Delete Tax */
  @Transactional
  public boolean deleteTax(Tax tax) throws Exception {
    return deleteTaxCallBack(tax, null);
  }

  /**
   * @param tax
   * @param callBack
   * @return boolean
   */
  @Transactional
  public boolean deleteTaxCallBack(Tax tax, ServiceCallback<ProductService> callBack) throws Exception {
    if (callBack != null) {
      callBack.callback(this);
    }
    if (tax == null) {
      return false;
    } else {
      if (tax.isRecycleBin()) {
        taxRepository.delete(tax);
        return true;
      } else {
        tax.setRecycleBin(true);
        if (saveTax(tax) != null)
          return true;
        else
          return false;
      }
    }
  }

  @Transactional(readOnly = true)
  public List<Tax> findTaxByName(String name) {
    return taxRepository.findByName(name.toLowerCase());
  }

  @Transactional
  public ProductTag saveProductTag(ProductTag productTag) {
    return productTagRepository.save(productTag);
  }

  /** Delete ProductTag */
  @Transactional
  public boolean deleteProductTag(ProductTag productTag) throws Exception {
    return deleteProductTagCallBack(productTag, null);
  }

  /**
   * @param productTag
   * @param callBack
   * @return boolean
   */
  @Transactional
  public boolean deleteProductTagCallBack(ProductTag productTag, ServiceCallback<ProductService> callBack)
      throws Exception {
    if (callBack != null) {
      callBack.callback(this);
    }
    if (productTag == null) {
      return false;
    } else {
      if (productTag.isRecycleBin()) {
        productTagRepository.delete(productTag);
        return true;
      } else {
        productTag.setRecycleBin(true);
        if (saveProductTag(productTag) != null) {
          return true;
        } else {
          return false;
        }
      }
    }
  }

  @Transactional
  public ProductTag getProductTagByTag(String tag) {
    return productTagRepository.getByTag(tag);
  }

  @Transactional
  public ProductTag getProductTagByCode(String code) {
    return productTagRepository.getByCode(code);
  }

  @Transactional(readOnly = true)
  public List<ProductTag> getProductTags(){
    return productTagRepository.findByProductTagRecycleBin(false);
  }
  
  @Transactional(readOnly = true)
  public List<ProductTag> findProductTagsLevel3(){
    return productTagRepository.findProductTagsLevel3();
  }

  @Transactional(readOnly = true)
  public List<ProductTag> findProductTag(String tag) throws Exception {
    return productTagRepository.findProductTag(tag.toLowerCase());
  }

  @Transactional
  public Product getProductByCode(String code) {
    return productRepo.getByCode(code);
  }

  @Transactional(readOnly = true)
  public List<Product> findProductByName(String name) throws Exception{
	  return productRepo.findProductByName(name.toLowerCase());
  }
  
  @Transactional(readOnly = true)
  public List<Product> findProductByNameOrCode(String name) throws Exception{
	  return productRepo.findProductByNameOrCode(name.toLowerCase());
  }
  
  @Transactional
  public Product saveProduct(Product product) throws Exception {
    return productRepo.save(product);
  }

  /** Delete Product */
  @Transactional
  public boolean deleteProductByCode(String code) throws Exception {
    Product product = productRepo.getByCode(code);
    if (product == null)
      return false;
    else
      return deleteProductCallBack(product, null);
  }

  /**
   * @param product
   * @param callBack
   * @return boolean
   */
  @Transactional
  public boolean deleteProductCallBack(Product product, ServiceCallback<ProductService> callBack) throws Exception {
    if (callBack != null) {
      callBack.callback(this);
    }
    searchService.delete(Product.class, product.getId());
    if (product.isRecycleBin()) {
      productPriceRepo.deleteByProduct(product);
      productRepo.delete(product);
      return true;
    } else {
      try {
        List<ProductPrice> pps = productPriceRepo.findByProduct(product.getCode());
        for (ProductPrice pp : pps) {
          productPriceRepo.delete(pp);
        }
        product.setCalculateReport(false);
        product.setRecycleBin(true);
        productRepo.save(product);
        return true;
      } catch (Exception e) {
        return false;
      }
    }
  }

  @Transactional(readOnly = true)
  public FilterResult<Product> filterProduct(FilterQuery query) {
    if (query == null)
      query = new FilterQuery();
    return productRepo.search(query);
  }

  public SearchResult<Product> searchProducts(SearchQuery query) throws Exception {
    return this.searchService.query(Product.class, query);
  }

  @Transactional(readOnly = true)
  public List<Product> findProductByCode(String code) {
    FilterQuery query = new FilterQuery();
    query.add("code", FilterQuery.Operator.LIKE, "*" + code + "*");
    FilterResult<Product> result = productRepo.search(query);
    return result.getData();
  }

  @Transactional(readOnly = true)
  public List<Product> findByProductTags(ProductTagList productTagList) {
    return productRepo.findByProductTags(productTagList.getTags());
  }

  @Transactional(readOnly = true)
  public List<Product> findByProductTag(String tag) {
    return productRepo.findByProductByTag(tag);
  }

  @Transactional(readOnly = true)
  public List<Product> findAllProducts() {
    return productRepo.findByProductRecycleBin(false);
  }
  
  @Transactional(readOnly = true)
  public List<Product> getProductDelete(){
    return productRepo.findByProductRecycleBin(true);
  }

  @Transactional
  public ProductDetail getProductDetailByCode(String pCode) {
    Product product = this.productRepo.getByCode(pCode);
    if (product == null)
      return null;
    ProductDetail pDetail = new ProductDetail();
    pDetail.setProduct(product);
    String path = this.cmsBasePath + "/" + product.getMaker() + "/" + pCode;
    NodeDetail nDetail = cmsService.getNodeDetail(path);
    pDetail.setCmsNode(nDetail);
    return pDetail;
  }

  @Transactional
  public ProductDetail saveProductDetail(ProductDetail pDetail) throws Exception {
    Product product = pDetail.getProduct();
    productRepo.save(product);

    NodeDetail nDetail = pDetail.getCmsNode();
    Node node = nDetail.getNode();

    String makerFolder = this.cmsBasePath + "/" + product.getMaker();
    Node makerNode = cmsService.getNodeByPath(makerFolder);
    if (makerNode == null) {
      cmsService.createIfNotExists(makerFolder);
      makerNode = cmsService.getNodeByPath(makerFolder);
    }
    cmsService.createNode(makerFolder, node, nDetail.getAttributes());
    searchService.add(product.getId(), product, product.isNew());
    return pDetail;
  }

  @Transactional
  public void createScenario(ProductScenario scenario) throws Exception {
    List<ProductTag> productTags = (List<ProductTag>) productTagRepository.save(scenario.getProductTags());

    for (ProductDetail pDetail : scenario.getProducts()) {
      pDetail.getProduct().setProductTags(productTags);
      saveProductDetail(pDetail);
    }

    List<ProductPriceType> productPriceTypes = scenario.getProductPriceTypes();
    productPriceTypeRepository.save(productPriceTypes);

    List<ProductPrice> productPrices = scenario.getProductPrices();

    List<Tax> taxs = scenario.getTaxs();
    taxRepository.save(taxs);
    int i = 0;
    List<Product> products = productRepo.search(new FilterQuery()).getData();

    for (ProductPrice productPrice : productPrices) {
      productPrice.setProductPriceType(productPriceTypes.get(0));
      productPrice.setProduct(products.get(i));
      productPrice.setTax(taxs.get(0));
      i++;
    }
    productPriceRepo.save(productPrices);
  }

  @Transactional(readOnly = true)
  public List<ProductTag> findProductTagRoot(String tagRoot) {
    tagRoot = tagRoot + ":";
    return productTagRepository.findProductTagRoot(tagRoot);
  }

  @Transactional(readOnly = true)
  public List<ProductTag> getChildProductTag(String parentTag) {
    return productTagRepository.getChildProductTag(parentTag);
  }

  @Transactional(readOnly = true)
  public List<ProductTag> getChildrenTags(String tag) {
    tag = tag + ":";
    return productTagRepository.getChildrenTags(tag);
  }

  @Transactional(readOnly = true)
  public List<ProductTag> getRootTags() {
    List<ProductTag> productTags = new ArrayList<ProductTag>();
    List<ProductTag> list = getProductTags();
    for (ProductTag productTag : list) {
      if (productTag.getTag().split(":").length == 1) {
        productTags.add(productTag);
      }
    }
    return productTags;
  }

  @Transactional(readOnly = true)
  public ProductTag getProductTagById(long id) {
    return productTagRepository.findOne(id);
  }

  @Transactional(readOnly = true)
  public Product getProductById(long id) {
    return productRepo.findOne(id);
  }

  @Transactional(readOnly = true)
  public List<ProductPriceType> getAllProductPriceType() {
    return productPriceTypeRepository.findByProductPriceTypeRecycleBin(false);
  }

  @Transactional(readOnly = true)
  public ProductPriceType getProductPriceTypeById(long id) {
    return productPriceTypeRepository.findOne(id);
  }

  @Transactional(readOnly = true)
  public List<ProductPrice> getAllProductPrice() {
    return productPriceRepo.findByProductRecycleBin(false);
  }

  @Transactional(readOnly = true)
  public ProductPrice getProductPriceById(long id) {
    return productPriceRepo.findOne(id);
  }

  @Transactional
  public void deleteAll() throws Exception {
    productPriceRepo.deleteAll();
    productPriceTypeRepository.deleteAll();
    productRepo.deleteAll();
    productTagRepository.deleteAll();
    taxRepository.deleteAll();
    searchService.delete(Product.class);
  }

  @Transactional
  public void deleteTest(String test) {
    productPriceRepo.deleteTest(test);
    productPriceTypeRepository.deleteTest(test);
    productRepo.deleteTestProductAttribute(test);
    productRepo.deleteTest(test);
    productTagRepository.deleteTest(test);
    taxRepository.deleteTest(test);
  }

  public String ping() {
    return "WarehouseService alive!!!";
  }
}