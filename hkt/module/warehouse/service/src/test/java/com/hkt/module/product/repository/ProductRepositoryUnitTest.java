package com.hkt.module.product.repository;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductTag;
import com.hkt.module.warehouse.AbstractUnitTest;
import com.hkt.util.json.JSONSerializer;

@Transactional
public class ProductRepositoryUnitTest extends AbstractUnitTest {
  @Autowired
  ProductRepository repository;

  @Autowired
  private ProductTagRepository tagRepo;

  @Before
  public void createContext() {
    String[] tags = { "Tag 1", "Tag 2", "Tag 3" };
    for (int i = 0; i < tags.length; i++) {
      ProductTag tag = new ProductTag();
      tag.setTag(tags[i]);
      tag.setLabel(tags[i]);
      tag.setRecycleBin(false);
      tagRepo.save(tag);
    }
  }

  @Test
  public void testCrud() {
    List<ProductTag> pTags = (List<ProductTag>) tagRepo.findAll() ; 
    Product product = repository.save(createProduct("iPhone", "Product HktTest", "Made by", pTags));
    
    assertEquals(product, repository.findOne(product.getId()));
    assertEquals(product, repository.findByProductRecycleBin(false).get(0));
    repository.deleteTestProductAttribute("b");
    repository.deleteTest("HktTest");
    
    assertEquals(0, repository.count());
  }

  @Test
  public void testFind() throws IOException {
    for (int i = 0; i < 5; i++) {
      repository.save(createProduct("IPhone" + i, "Product", "Made by", (List<ProductTag>) tagRepo.findAll()));
    }
    assertEquals(5, repository.findByProductTags((List<ProductTag>) tagRepo.findAll()).size());
    FilterQuery query = new FilterQuery();
    query.add("maker", FilterQuery.Operator.LIKE, "Made*");
    FilterResult<Product> result = repository.search(query);
    assertEquals(5, result.getData().size());

    query.add("name", FilterQuery.Operator.EQUAL, "Product");
    assertEquals(5, result.getData().size());

    query.add("code", FilterQuery.Operator.LIKE, "IPhone1*");
    result = repository.search(query);
    assertEquals(1, result.getData().size());

    String json = JSONSerializer.INSTANCE.toString(query);
    query = JSONSerializer.INSTANCE.fromString(json, FilterQuery.class);
    System.out.println(json);
  }

}
