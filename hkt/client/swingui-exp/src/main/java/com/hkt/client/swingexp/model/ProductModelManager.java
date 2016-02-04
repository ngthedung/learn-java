package com.hkt.client.swingexp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.ProductServiceClient;
import com.hkt.client.swingexp.app.banhang.screen.often.ScreenOften;
import com.hkt.module.accounting.entity.Bank;
import com.hkt.module.accounting.entity.BankTransaction;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.ReportTable;
import com.hkt.module.core.entity.SQLSelectQuery;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductPrice;
import com.hkt.module.product.entity.ProductPriceType;
import com.hkt.module.product.entity.ProductTag;
import com.hkt.module.product.entity.ProductTagList;
import com.hkt.module.product.entity.Tax;

public class ProductModelManager {

	private static ProductModelManager	instance;
	private ClientContext								clientContext	= ClientContext.getInstance();
	private ProductServiceClient				productClient	= clientContext.getBean(ProductServiceClient.class);

	private ProductModelManager() {
		
	}

	public void saveSettings() {
		 ClientContext.getInstance();
	}

	public static ProductModelManager getInstance() {
		if (instance == null) {
			instance = new ProductModelManager();
		}
		return instance;
	}

	public Product getProductKara() {
		try {
			Product p = getProductByCode("kara");
			if (p == null) {
				p = new Product();
				p.setName("Kara");
				p.setCode("kara");
				p.setMaker("hkt");
				p.setNameOther("kara");
				p = saveProduct(p);
			}
			p.setProductTags(new ArrayList<ProductTag>());
			return p;
		} catch (Exception e) {
			return null;
		}

	}

	public List<Product> findByProductTags(ProductTagList productTags) throws Exception {
		return productClient.findByProductTags(productTags);
	}
	
	public List<Product> getProductByBarcode(String descriotion){
		try {
	    return productClient.getProductByBarcode(descriotion);
    } catch (Exception e) {
	    return new ArrayList<Product>();
    }
	}
	
	public List<ProductTag> findProductTagsLevel3() throws Exception {
		List<ProductTag> list =  productClient.findProductTagsLevel3();
		Collections.sort(list, new Comparator<ProductTag>() {
			@Override
			public int compare(ProductTag it1, ProductTag it2) {
				if (it1.getPriority() > it2.getPriority()) {
					return 1;
				} else {
					if (it1.getPriority() < it2.getPriority()) {
						return -1;
					} else {
						return 0;
					}
				}

			}
		});
		return list;
	}

	public List<Product> findAllProducts() {
		try {
			List<Product> ps = productClient.findAllProducts();
			return ps;
    } catch (Exception e) {
	    return new ArrayList<Product>();
    }
		
	}

	public ProductPrice saveProductPrice(ProductPrice productPrice) throws Exception {
		return productClient.saveProductPrice(productPrice);
	}

	public List<ProductPrice> findByProductPriceByTag(String tag, String type, String currencyUnit) {
		try {
			return productClient.findByProductPriceByTag(tag, type, currencyUnit);
		} catch (Exception e) {
			return new ArrayList<ProductPrice>();
		}

	}

	public List<ProductPrice> getProductPriceByProductCode(String productCode){
		try {
			return productClient.searchByProductCode(productCode);
    } catch (Exception e) {
	   return new ArrayList<ProductPrice>();
    }
		
	}

	public boolean deleteProductPrice(ProductPrice productPrice) throws Exception {
		return productClient.deleteProductPrice(productPrice);
	}

	public ProductPriceType saveProductPriceType(ProductPriceType priceType) throws Exception {
		return productClient.saveProductPriceType(priceType);
	}

	public boolean deleteProductPriceType(ProductPriceType priceType) throws Exception {
		return productClient.deleteProductPriceType(priceType);
	}

	public ProductPriceType getProductPriceTypeByType(String type) throws Exception {
		return productClient.getProductPriceTypeByType(type);
	}

	public List<ProductPriceType> getProductPriceTypes() throws Exception {
		List<ProductPriceType> list = productClient.getProductPriceTypes();
		Collections.sort(list, new Comparator<ProductPriceType>() {
			@Override
			public int compare(ProductPriceType it1, ProductPriceType it2) {
				if (it1.getPriority() > it2.getPriority()) {
					return 1;
				} else {
					if (it1.getPriority() < it2.getPriority()) {
						return -1;
					} else {
						return 0;
					}
				}

			}
		});
		return list;
	}

	public boolean deleteTax(Tax tax) throws Exception {
		return productClient.deleteTax(tax);
	}

	public Tax getTaxByCode(String code) throws Exception {
		return productClient.getTaxByCode(code);
	}

	public List<Tax> getTaxs()  {
		try {
			List<Tax> taxs = productClient.getTaxs();
			Collections.sort(taxs, new Comparator<Tax>() {
				@Override
				public int compare(Tax it1, Tax it2) {
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
			return taxs;
    } catch (Exception e) {
	    return new ArrayList<Tax>();
    }
		
	}

	public Tax saveTax(Tax tax) throws Exception {
		return productClient.saveTax(tax);
	}

	public List<ProductTag> getProductTags() throws Exception {
		List<ProductTag> list = productClient.getProductTags();
		Collections.sort(list, new Comparator<ProductTag>() {
			@Override
			public int compare(ProductTag it1, ProductTag it2) {
				if (it1.getPriority() > it2.getPriority()) {
					return 1;
				} else {
					if (it1.getPriority() < it2.getPriority()) {
						return -1;
					} else {
						return 0;
					}
				}

			}
		});
		return list;
	}

	public List<ProductPrice> getAllProductPrice() throws Exception {
		return productClient.getAllProductPrices();
	}

	public List<Product> filterProduct() throws Exception {
		return productClient.filterProduct(new FilterQuery()).getData();
	}

	public List<ProductTag> findProductTagRoot(String tagRoot) throws Exception {
		List<ProductTag> list =  productClient.findProductTagRoot(tagRoot);
		Collections.sort(list, new Comparator<ProductTag>() {
			@Override
			public int compare(ProductTag it1, ProductTag it2) {
				if (it1.getPriority() > it2.getPriority()) {
					return 1;
				} else {
					if (it1.getPriority() < it2.getPriority()) {
						return -1;
					} else {
						return 0;
					}
				}

			}
		});
		return list;
	}

	public List<ProductTag> getChildProductTag(String parentTag) throws Exception {
		List<ProductTag> list =  productClient.getChildProductTag(parentTag);
		Collections.sort(list, new Comparator<ProductTag>() {
			@Override
			public int compare(ProductTag it1, ProductTag it2) {
				if (it1.getPriority() > it2.getPriority()) {
					return 1;
				} else {
					if (it1.getPriority() < it2.getPriority()) {
						return -1;
					} else {
						return 0;
					}
				}

			}
		});
		return list;
	}

	public ProductTag saveProductTag(ProductTag tag) throws Exception {
		return productClient.saveProductTag(tag);
	}

	public ProductTag getProductTagParent(ProductTag proTag) {
		try {
			String tagRoot = proTag.getTag().substring(0, proTag.getTag().indexOf(proTag.getCode()) - 1);
			return productClient.getProductTagByTag(tagRoot);
		} catch (Exception e) {
			return null;
		}

	}

	public boolean deleteProductTag(ProductTag tag) throws Exception {
		return productClient.deleteProductTag(tag);
	}

	public ProductTag getProductTagByTag(String tag) throws Exception {
		return productClient.getProductTagByTag(tag);
	}

	public List<Product> findByProductTag(String tag) throws Exception {
		return productClient.findByProductTag(tag);
	}
	
	public List<Product> findByNotProductPriceType(String type) throws Exception {
//		String[] column = { "id", "createdBy", "createdTime", "modifiedBy", "modifiedTime", "recycleBin", "code", "image", "maker", "name", "nameOther", "profilesData", "status", "type", "unit" };
		List<Product> products = new ArrayList<Product>();
		SQLSelectQuery rQuery = new SQLSelectQuery();
		
		rQuery.table("product p " +
				"LEFT JOIN warehouse_productprice w ON p.id = w.product_id " +
				"LEFT JOIN warehouse_productpricetype t ON t.id = w.productPriceType_Id")
		.field("p.id", "id")
//		.field("p.createdBy", "createdBy")
//		.field("p.createdTime", "createdTime")
//		.field("p.modifiedBy", "modifiedBy")
//		.field("p.modifiedTime", "modifiedTime")
//		.field("p.recycleBin", "recycleBin")
		.field("p.code", "code")
//		.field("p.image", "image")
//		.field("p.maker", "maker")
		.field("p.name", "name")
//		.field("p.nameOther", "nameOther")
		.field("p.profilesData", "profilesData")
//		.field("p.status", "status")
//		.field("p.type", "type")
//		.field("p.unit", "unit")		
		.cond("(t.type NOT LIKE '"+ type +"' OR w.id IS NULL) AND " +
				"NOT EXISTS ( " +
				"SELECT p1.code " +
				"FROM `product` p1 " +
				"LEFT JOIN `warehouse_productprice` w1 ON p1.id = w1.product_id " + 
				"LEFT JOIN `warehouse_productpricetype` t1 ON t1.id = w1.productPriceType_Id " +
				"WHERE (t1.type LIKE '"+ type +"') AND p1.code LIKE p.code)")	
		.groupBy("p.code");
		
//		System.out.println(rQuery.createSQLQuery());
		ReportTable[] reportTable = AccountingModelManager.getInstance().reportQuery(new SQLSelectQuery[] { rQuery });
		String[] field = new String[] { "id", "code", "name", "profilesData" };
		reportTable[0].dump(new String[] { "id", "code", "name", "profilesData" });
		List<Map<String, Object>> records = reportTable[0].getRecords();
		for (int i = 0; i < records.size(); i++) {
			Map<String, Object> record = records.get(i);
			Object[] values = new Object[field.length];
			for (int j = 0; j < field.length; j++) {
				values[j] = record.get(field[j]);
			}
			Product row = new Product();
			row.setId(Long.parseLong(values[0].toString()));
			row.setCode(values[1].toString());
			row.setName(values[2].toString());
			row.setProductTags(ProductModelManager.getInstance().getProductByCode(row.getCode()).getProductTags());
			row.setProfilesData(values[3]!=null?values[3].toString():null);
			products.add(row);
		}
		
		return products;
	}
	
	public ProductPrice getByProductAndProductPriceType(String code, String type, String currencyUnit) throws Exception {
		return productClient.getByProductAndProductPriceType(code, type, currencyUnit);
	}

	// public ProductPrice getByProductAndProductPriceType(String code, String
	// type) throws Exception {
	// return productClient.getByProductAndProductPriceType(code, type);
	// }

	public Product getProductByCode(String code) throws Exception {
		return productClient.getProductByCode(code);
	}

	public List<Product> findProductByName(String name) throws Exception {
		return productClient.findProductByName(name);
	}

	public Product saveProduct(Product product) throws Exception {
		product = productClient.saveProduct(product);
		Thread t = new Thread() {
			public void run() {
				try {
					ScreenOften.getInstance().setResetProduct(true);
				} catch (Exception e) {
					System.out.println("loi load Table");
				}

			};
		};
		t.start();
		return product;
	}

	public boolean deleteProductByCode(String code) throws Exception {
		productClient.deleteProductByCode(code);
		Thread t = new Thread() {
			public void run() {
				try {
					ScreenOften.getInstance().setResetProduct(true);
				} catch (Exception e) {
					System.out.println("loi load Table");
				}

			};
		};
		t.start();

		return true;
	}

	public List<ProductPrice> findByProductPriceByType(String type) throws Exception {
		return productClient.findByProductPriceByType(type);
	}

	public List<ProductTag> getChildrenTags(String tag) throws Exception {
		List<ProductTag> list =  productClient.getChildrenTags(tag);
		Collections.sort(list, new Comparator<ProductTag>() {
			@Override
			public int compare(ProductTag it1, ProductTag it2) {
				if (it1.getPriority() > it2.getPriority()) {
					return 1;
				} else {
					if (it1.getPriority() < it2.getPriority()) {
						return -1;
					} else {
						return 0;
					}
				}

			}
		});
		return list;
	}

	public List<ProductTag> getRootTags() throws Exception {
		List<ProductTag> list =  productClient.getRootTags();
		Collections.sort(list, new Comparator<ProductTag>() {
			@Override
			public int compare(ProductTag it1, ProductTag it2) {
				if (it1.getPriority() > it2.getPriority()) {
					return 1;
				} else {
					if (it1.getPriority() < it2.getPriority()) {
						return -1;
					} else {
						return 0;
					}
				}

			}
		});
		return list;
	}

	public ProductTag getProductTagByCode(String code) throws Exception {
		return productClient.getProductTagByCode(code);
	}

	public void deleteTest(String test) throws Exception {
		productClient.deleteTest(test);
	}

	public ProductTag getProductTagMenu() {
		try {
			ProductTag p = productClient.getProductTagByCode("MenuVoucher");
			if (p == null) {
				p = new ProductTag();
				p.setCode("MenuVoucher");
				p.setLabel("Menu Voucher");
				p.setTag("MenuVoucher");
				p.setPriority(0);
				p = productClient.saveProductTag(p);
			}
			return p;
		} catch (Exception e) {
			return null;
		}
	}

	public ProductTag getProductTagById(long id) throws Exception {
		return productClient.getProductTagById(id);
	}

	public List<ProductPriceType> getAllProductPriceType(){
		try {
			return productClient.getAllProductPriceType();
    } catch (Exception e) {
	    return new ArrayList<ProductPriceType>();
    }
		
	}

	public ProductPriceType getProductPriceTypeById(long id) throws Exception {
		return productClient.getProductPriceTypeById(id);
	}

	public ProductPrice getProductPriceById(long id) throws Exception {
		return productClient.getProductPriceById(id);
	}

	public Product getProductById(long id) throws Exception {
		return productClient.getProductById(id);
	}
}
