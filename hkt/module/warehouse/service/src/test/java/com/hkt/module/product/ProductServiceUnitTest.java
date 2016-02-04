package com.hkt.module.product;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hkt.module.account.AccountService;
import com.hkt.module.account.util.AccountScenario;
import com.hkt.module.cms.CMSService;
import com.hkt.module.cms.entity.NodeDetail;
import com.hkt.module.core.ServiceCallback;
import com.hkt.module.core.search.SearchQuery;
import com.hkt.module.core.search.SearchResult;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductDetail;
import com.hkt.module.product.entity.ProductPrice;
import com.hkt.module.product.entity.ProductPriceType;
import com.hkt.module.product.entity.ProductTag;
import com.hkt.module.product.entity.Tax;
import com.hkt.module.product.util.ProductScenario;
import com.hkt.module.warehouse.AbstractUnitTest;
import com.hkt.util.json.JSONSerializer;

public class ProductServiceUnitTest extends AbstractUnitTest {
	static ServiceCallback<ProductService>	FAIL_CALLBACK	= new ServiceCallback<ProductService>() {
																													public void callback(ProductService service) {
																														throw new RuntimeException("Fail. Expect a rollback if method annotate with the Transactionnal");
																													}
																												};

	static {
		System.setProperty("hibernate.show_sql", "false");
		System.setProperty("hibernate.format_sql", "false");
	}

	@Autowired
	CMSService															cmsService;

	private AccountScenario									accountScenario;

	@Autowired
	private AccountService									accountService;

	@Autowired
	private ProductService									service;

	private ProductScenario									scenario;

	@Before
	public void setup() throws Exception {
		accountScenario = AccountScenario.loadTest();
		accountService.createScenario(accountScenario);

		scenario = ProductScenario.loadTest();
		service.createScenario(scenario);
	}

	@After
	public void testDown() throws Exception {
		service.deleteAll();
		accountService.deleteAll();
		cmsService.deleteAll();
	}

	@Test
	public void testSerialization() {
		assertTrue(scenario.getProducts().size() > 0);
	}

	@Test
	public void testProductDetailCRUD() throws IOException {
		ProductDetail pDetail = service.getProductDetailByCode("iphone5s");
		assertNotNull(pDetail);
		NodeDetail nodeDetail = pDetail.getCmsNode();
		assertNotNull(nodeDetail);
		System.out.println(JSONSerializer.INSTANCE.toString(pDetail));
	}

	@Test
	public void testProduct() throws Exception {
		Product product = service.getProductByCode("iphone5s");
		assertNotNull(product);
		try {
			service.deleteProductCallBack(product, FAIL_CALLBACK);
		} catch (Throwable t) {
			System.out.println("Fail callback exception: " + t.getMessage());
		}
		service.deleteProductByCode("iphone5s");
		assertNull(service.getProductByCode("iphone5s"));
		assertEquals(10, service.filterProduct(null).getData().size());
		//assertEquals(10, service.findByProductTags(service.getProductTags()).size());

	}

	@Test
	public void testFindProducts() {
		List<Product> products = service.findProductByCode("i");
		assertEquals(4, products.size());
	}

	@Test
	public void testSearchProducts() throws Exception {
		SearchQuery query = new SearchQuery("title:ipad-air");
		SearchResult<Product> result = service.searchProducts(query);
		assertEquals(1, result.getTotalHits());

		query = new SearchQuery("Apple");
		result = service.searchProducts(query);
		assertEquals(2, result.getTotalHits());

		query = new SearchQuery("text:String");
		result = service.searchProducts(query);
		assertEquals(3, result.getTotalHits());

	}

	@Test
	public void testProductPrice() throws Exception {
		ProductPriceType productPriceType = service.getProductPriceTypeByType("Bang gia thuong");
		assertNotNull(productPriceType);
		try {
			service.deleteProductPriceTypeCallBack(productPriceType, FAIL_CALLBACK);
		} catch (Throwable t) {
			System.out.println("Fail callback exception: " + t.getMessage());
		}

		assertEquals(2, service.searchByProductCode("ip").size());
		assertEquals(3, service.searchByProductPriceType(productPriceType.getType()).size());
		assertEquals(3, service.searchByType("Ban").size());
		ProductPrice productPrice = service.searchByProductPriceType(productPriceType.getType()).get(0);
		assertNotNull(productPrice);
		service.deleteProductPrice(productPrice);
		assertEquals(2, service.searchByProductPriceType(productPriceType.getType()).size());
		service.deleteProductPriceType(productPriceType);
		assertEquals(0, service.searchByProductPriceType(productPriceType.getType()).size());
	}

	@Test
	public void testTax() throws Exception {
		Tax tax = service.getTaxs().get(0);
		tax.setName("VAT - Gia tri gia tang");

		tax = service.saveTax(tax);
		assertEquals("VAT - Gia tri gia tang", tax.getName());

		try {
			service.deleteTaxCallBack(tax, FAIL_CALLBACK);
		} catch (Throwable t) {
			System.out.println("Fail callback exception: " + t.getMessage());
		}

		assertEquals(1, service.getTaxs().size());
	}

	@Test
	public void testProductTag() throws Exception {
		ProductTag productTag = service.getProductTags().get(0);
		assertNotNull(productTag);
		assertEquals(4, service.getProductTags().size());
		for (int i = 0; i < service.findProductTagRoot("electronic").size(); i++) {
			System.out.println(service.findProductTagRoot("electronic").get(i).getTag());
			System.out.println("    " + service.findProductTagRoot(service.findProductTagRoot("electronic").get(i).getTag()));
		}
		service.deleteProductTag(productTag);
	}
}
