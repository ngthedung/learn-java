package com.hkt.module.product.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.Account.Type;
import com.hkt.module.account.repository.AccountRepository;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductPrice;
import com.hkt.module.product.entity.ProductPriceType;
import com.hkt.module.product.entity.ProductTag;
import com.hkt.module.warehouse.AbstractUnitTest;
import com.hkt.util.json.JSONSerializer;

@Transactional
public class ProductPriceRepositoryUnitTest extends AbstractUnitTest {
	@Autowired
	private ProductPriceRepository			repo;

	@Autowired
	private ProductPriceTypeRepository	productPriceTypeRepo;

	@Autowired
	private ProductTagRepository				productTagRepo;

	@Autowired
	private ProductRepository						productRepo;

	@Autowired
	private AccountRepository						accountRepository;

	@Before
	public void createContext() {
		Account account2 = new Account();
		account2.setLoginId("hkt");
		account2.setPassword("abc");
		account2.setEmail("hkt@email.com");
		account2.setType(Type.ORGANIZATION);
		accountRepository.save(account2);

		String[] tags = { "Hang Hoa", "Thanh Pham", "Nguyen Vat Lieu" };

		for (int i = 0; i < tags.length; i++) {
			ProductTag tag = new ProductTag();
			tag.setTag(tags[i]);
			tag.setLabel(tags[i]);
			productTagRepo.save(tag);
		}

		Product product = new Product();
		product.setCode("iphone5HKT");
		product.setName("iphone5HKT");
		product.setMaker("iphone");
		product.setProductTags((List<ProductTag>) productTagRepo.findAll());
		product.setRecycleBin(false);
		productRepo.save(product);

		ProductPriceType productPriceType = ProductPriceTypeRepositoryUnitTest.createInstance("type");
		productPriceType = productPriceTypeRepo.save(productPriceType);
		productPriceType = JSONSerializer.INSTANCE.clone(productPriceType);
	}

	@Test
	public void testCRUD() {
		List<ProductTag> productTags = (List<ProductTag>) productTagRepo.findAll();
		ProductPrice productPrice = createInstance();
		productPrice = repo.save(productPrice);

		assertNotNull(productPrice);
		assertEquals(productPrice, repo.findOne(productPrice.getId()));
		assertNotNull(repo.getByProductAndProductPriceType("iphone5HKT", "type","VND"));
		assertEquals(1, repo.findByProductPriceType(productPrice.getProductPriceType().getType()).size());
		assertEquals(1, repo.findByProductPriceByTag(productTags.get(0).getTag(),"type","VND").size());
		assertEquals(productPrice, repo.getProductPriceByProductPriceType(productPrice.getProductPriceType()).get(0));
		
		repo.deleteTest("HKT");
		assertEquals(0, repo.findByProductPriceType(productPrice.getProductPriceType().getType()).size());

		productPrice = createInstance();
		productPrice = repo.save(productPrice);
		assertEquals(1, repo.findByProductCode("iphone5HKT").size());
    repo.deleteByProductPriceType(productPrice.getProductPriceType());
    assertEquals(0, repo.count());
    
		productPrice = createInstance();
		productPrice = repo.save(productPrice);
		assertEquals(1, repo.findByProductRecycleBin(false).size());
		repo.deleteByProduct(productPrice.getProduct());
    assertEquals(0, repo.count());
	}
	
	@After
	public void endTest(){
		productRepo.deleteTest("HKT");
		assertEquals(0, productRepo.count());
		
		productTagRepo.deleteAll();
		assertEquals(0, productTagRepo.count());
		
		productPriceTypeRepo.deleteAll();
		assertEquals(0, productPriceTypeRepo.count());
	}

	ProductPrice createInstance() {
		ProductPrice productPrice = new ProductPrice();
		productPrice.setOrganizationLoginId("hkt");
		productPrice.setCurrencyUnit("VND");
		productPrice.setDescription("HKT");
		productPrice.setProductPriceType(productPriceTypeRepo.getByType("type"));
		productPrice.setProduct(productRepo.getByCode("iphone5HKT"));
		productPrice.setRecycleBin(false);
		return productPrice;
	}
}