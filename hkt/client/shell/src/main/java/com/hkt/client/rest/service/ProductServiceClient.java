package com.hkt.client.rest.service;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hkt.client.rest.RESTClient;
import com.hkt.module.accounting.entity.Bank;
import com.hkt.module.accounting.entity.BankTransaction;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.core.rest.Request;
import com.hkt.module.core.rest.Response;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductPrice;
import com.hkt.module.product.entity.ProductPriceDetail;
import com.hkt.module.product.entity.ProductPriceType;
import com.hkt.module.product.entity.ProductTag;
import com.hkt.module.product.entity.ProductTagList;
import com.hkt.module.product.entity.Tax;

@Component
public class ProductServiceClient {
	@Autowired
	private RESTClient client;

	public List<Product> findByProductTag(String tag) throws Exception {
		Request req = create("findByProductTag");
		req.addParam("tag", tag);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<Product>>() {
		});
	}
	
	public List<Product> getProductByBarcode(String descriotion) throws Exception {
		Request req = create("getProductByBarcode");
		req.addParam("descriotion", descriotion);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<Product>>() {
		});
	}

	public List<ProductPriceType> getProductPriceTypes() throws Exception {
		Request req = create("getProductPriceTypes");
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<ProductPriceType>>() {
		});
	}

	public List<ProductPrice> findByProductPriceByTag(String tag, String type,
			String currencyUnit) throws Exception {
		Request req = create("findByProductPriceByTag");
		req.addParam("tag", tag);
		req.addParam("type", type);
		req.addParam("currencyUnit", currencyUnit);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<ProductPrice>>() {
		});
	}

	public ProductPriceType getProductPriceTypeByType(String type)
			throws Exception {
		Request req = create("getProductPriceTypeByType");
		req.addParam("type", type);
		Response res = client.POST(req);
		return res.getDataAs(ProductPriceType.class);
	}

	public ProductPriceType saveProductPriceType(
			ProductPriceType productPriceType) throws Exception {
		Request req = create("saveProductPriceType");
		req.addParam("productPriceType", productPriceType);
		Response res = client.POST(req);
		return res.getDataAs(ProductPriceType.class);
	}

	public boolean deleteProductPriceType(ProductPriceType productPriceType)
			throws Exception {
		Request req = create("deleteProductPriceType");
		req.addParam("productPriceType", productPriceType);
		client.POST(req);
		return true;
	}

	public ProductPrice saveProductPrice(ProductPrice productPrice)
			throws Exception {
		Request req = create("saveProductPrice");
		req.addParam("productPrice", productPrice);
		Response res = client.POST(req);
		return res.getDataAs(ProductPrice.class);
	}

	public ProductPrice getByProductAndProductPriceType(String code,
			String type, String currencyUnit) throws Exception {
		Request req = create("getByProductAndProductPriceType");
		req.addParam("code", code);
		req.addParam("type", type);
		req.addParam("currencyUnit", currencyUnit);
		Response res = client.POST(req);
		return res.getDataAs(ProductPrice.class);
	}

	// public ProductPrice getByProductAndProductPriceType(String code, String
	// type) throws Exception {
	// Request req = create("getByProductAndProductPriceType");
	// req.addParam("code", code);
	// req.addParam("type", type);
	// Response res = client.POST(req);
	// return res.getDataAs(ProductPrice.class);
	// }

	public List<ProductPrice> findByProductPriceByType(String type)
			throws Exception {
		Request req = create("searchByProductPriceType");
		req.addParam("type", type);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<ProductPrice>>() {
		});
	}

	public List<ProductPrice> searchByProductCode(String code) throws Exception {
		Request req = create("searchByProductCode");
		req.addParam("code", code);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<ProductPrice>>() {
		});
	}

	public boolean deleteProductPrice(ProductPrice productPrice)
			throws Exception {
		Request req = create("deleteProductPrice");
		req.addParam("productPrice", productPrice);
		client.POST(req);
		return true;
	}

	public List<ProductTag> getProductTags() throws Exception {
		Request req = create("getProductTags");
		Response res = client.POST(req);
		if (res.getData() == null)
			return new ArrayList<ProductTag>();
		return res.getDataAs(new TypeReference<List<ProductTag>>() {
		});
	}

	public List<Tax> getTaxs() throws Exception {
		Request req = create("getTaxs");
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<Tax>>() {
		});
	}

	public Tax getTaxByCode(String code) throws Exception {
		Request req = create("getTaxByCode");
		req.addParam("name", code);
		Response res = client.POST(req);
		return res.getDataAs(Tax.class);
	}

	public Tax saveTax(Tax tax) throws Exception {
		Request req = create("saveTax");
		req.addParam("tax", tax);
		Response res = client.POST(req);
		return res.getDataAs(Tax.class);
	}

	public boolean deleteTax(Tax tax) throws Exception {
		Request req = create("deleteTax");
		req.addParam("tax", tax);
		client.POST(req);
		return true;
	}

	public List<ProductPrice> getAllProductPrices() throws Exception {
		Request req = create("getAllProductPrices");
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<ProductPrice>>() {
		});
	}

	public FilterResult<Product> filterProduct(FilterQuery query)
			throws Exception {
		Request req = create("filterProduct");
		req.addParam("query", query);
		Response res = client.POST(req);
		return res
				.getDataAsByFilter(new TypeReference<FilterResult<Product>>() {
				});
	}

	public List<Product> findByProductTags(ProductTagList productTagList)
			throws Exception {
		Request req = create("findByProductTags");
		req.addParam("productTagList", productTagList);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<Product>>() {
		});
	}

	public List<Product> findAllProducts() throws Exception {
		Request req = create("findAllProducts");
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<Product>>() {
		});
	}

	public List<ProductTag> findProductTagRoot(String tagRoot) throws Exception {
		Request req = create("findProductTagRoot");
		req.addParam("tagRoot", tagRoot);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<ProductTag>>() {
		});
	}

	public List<ProductTag> getChildProductTag(String parentTag)
			throws Exception {
		Request req = create("getChildProductTag");
		req.addParam("parentTag", parentTag);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<ProductTag>>() {
		});
	}

	public ProductTag getProductTagByTag(String tag) throws Exception {
		Request req = create("getProductTagByTag");
		req.addParam("tag", tag);
		Response res = client.POST(req);
		return res.getDataAs(ProductTag.class);
	}

	public Product getProductByCode(String code) throws Exception {
		Request req = create("getProductByCode");
		req.addParam("code", code);
		Response res = client.POST(req);
		return res.getDataAs(Product.class);
	}

	public List<Product> findProductByName(String name) throws Exception {
		Request req = create("findProductByName");
		req.addParam("name", name);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<Product>>() {
		});
	}

	public Product saveProduct(Product product) throws Exception {
		Request req = create("saveProduct");
		req.addParam("product", product);
		Response res = client.POST(req);
		return res.getDataAs(Product.class);
	}

	public boolean deleteProductByCode(String code) throws Exception {
		Request req = create("deleteProductByCode");
		req.addParam("code", code);
		client.POST(req);
		return true;
	}

	public ProductPriceDetail getProductPriceDetail(String productCode,
			String type, String customerLoginId) throws Exception {
		Request req = create("getProductPriceDetail");
		req.addParam("productCode", productCode);
		req.addParam("type", type);
		req.addParam("customerLoginId", customerLoginId);
		Response res = client.POST(req);
		return res.getDataAs(ProductPriceDetail.class);
	}

	Request create(String method) {
		return new Request("warehouse", "ProductService", method);
	}

	public ProductTag saveProductTag(ProductTag tag) throws Exception {
		Request req = create("saveProductTag");
		req.addParam("tag", tag);
		Response res = client.POST(req);
		return res.getDataAs(ProductTag.class);
	}

	public boolean deleteProductTag(ProductTag tag) throws Exception {
		Request req = create("deleteProductTag");
		req.addParam("tag", tag);
		client.POST(req);
		return true;
	}

	public List<ProductTag> getChildrenTags(String tag) throws Exception {
		Request req = create("getChildrenTags");
		req.addParam("tag", tag);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<ProductTag>>() {
		});
	}

	public List<ProductTag> getRootTags() throws Exception {
		Request req = create("getRootTags");
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<ProductTag>>() {
		});
	}

	public ProductTag getProductTagByCode(String code) throws Exception {
		Request req = create("getProductTagByCode");
		req.addParam("code", code);
		Response res = client.POST(req);
		return res.getDataAs(ProductTag.class);
	}

	public ProductTag getProductTagById(long id) throws Exception {
		Request req = create("getProductTagById");
		req.addParam("id", id);
		Response res = client.POST(req);
		return res.getDataAs(ProductTag.class);
	}
	
	public List<ProductTag> findProductTagsLevel3() throws Exception {
		Request req = create("findProductTagsLevel3");
		Response res = client.POST(req);
		if (res.getData() == null)
			return new ArrayList<ProductTag>();
		return res.getDataAs(new TypeReference<List<ProductTag>>() {
		});
	}

	public Product getProductById(long id) throws Exception {
		Request req = create("getProductById");
		req.addParam("id", id);
		Response res = client.POST(req);
		return res.getDataAs(Product.class);
	}

	public List<ProductPriceType> getAllProductPriceType() throws Exception {
		Request req = create("getAllProductPriceType");
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<ProductPriceType>>() {
		});
	}

	public ProductPriceType getProductPriceTypeById(long id) throws Exception {
		Request req = create("getProductPriceTypeById");
		req.addParam("id", id);
		Response res = client.POST(req);
		return res.getDataAs(ProductPriceType.class);
	}

	public List<ProductPrice> getAllProductPrice() throws Exception {
		Request req = create("getAllProductPrice");
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<ProductPrice>>() {
		});
	}

	public ProductPrice getProductPriceById(long id) throws Exception {
		Request req = create("getProductPriceById");
		req.addParam("id", id);
		Response res = client.POST(req);
		return res.getDataAs(ProductPrice.class);
	}
	
	public void deleteTest(String test) throws Exception {
		Request req = create("deleteTest");
		req.addParam("test", test);
		client.POST(req);
	}

}