package com.hkt.module.restaurant.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.Account.Type;
import com.hkt.module.account.repository.AccountRepository;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.restaurant.AbstractUnitTest;
import com.hkt.module.restaurant.entity.Recipe;
import com.hkt.module.restaurant.entity.RecipeIngredient;

@Transactional
public class RecipeRepositoryUnitTest extends AbstractUnitTest {
  static {
    System.setProperty("hibernate.show_sql", "true");
    System.setProperty("hibernate.format_sql", "true");
  }
  
  @PersistenceContext
  private EntityManager em;
  
  @Autowired
  private RecipeRepository repo;
  
  @Autowired
  private AccountRepository accountRepository;
 
  
  @Before
  public void createContext() {
    Account account2 = new Account();
    account2.setLoginId("hkt");
    account2.setPassword("abc");
    account2.setEmail("hkt@email.com");
    account2.setType(Type.ORGANIZATION);
    accountRepository.save(account2);
    
    
  }

  @Test
  public void testCRUD() {
    Recipe recipe = createInstance("test");
    repo.save(recipe);
    
    Recipe recipeInDB = repo.findOne(recipe.getId());
    assertNotNull(recipeInDB);
    assertEquals(recipe.getRecipeIngredients().size(), recipeInDB.getRecipeIngredients().size());
    assertEquals(3, count(RecipeIngredient.class));
    repo.delete(recipe);
    assertEquals(0, count(RecipeIngredient.class));
  }
  
  @Test
  public void testfillterQuerry() {
	  Recipe recipe = createInstance("test");
	    repo.save(recipe);
	    FilterQuery fq = new FilterQuery();
	    fq.add("name", FilterQuery.Operator.EQUAL, "test");
	    System.out.print(repo.search(fq).getData());
  }
  
  //khanhdd
  @Test
  public void testCRUD1() {
    Recipe recipe = createInstance("test", true);
    repo.save(recipe);
    
    assertNotNull(recipe);
    assertEquals(1, repo.findRecipeByValueRecycleBin(true).size());
  }
  
  public Recipe createInstance(String name) {
    Recipe item = new Recipe();
    item.setOrganizationLoginId("hkt");
    item.setName(name);
    item.setRecycleBin(true);
    item.setProductCode("iphone5");   
    for (int i = 0; i < 3; i++) {
      RecipeIngredient ingredient = new RecipeIngredient();
      ingredient.setProductCode("product-" + i);
      item.addIngredient(ingredient);
    }
    
    item.setRecipe("hkt");
    item.setDescription("hkt");
    return item;
  }
  //khanhdd
  public Recipe createInstance(String name, boolean k) {
	    Recipe item = new Recipe();
	    item.setOrganizationLoginId("hkt");
	    item.setName(name);
	    item.setProductCode("iphone5");
	    item.setRecycleBin(k);
	    for (int i = 0; i < 3; i++) {
	      RecipeIngredient ingredient = new RecipeIngredient();
	      ingredient.setProductCode("product-" + i);
	      item.addIngredient(ingredient);
	    }
	    
	    item.setRecipe("hkt");
	    item.setDescription("hkt");
	    return item;
	  }
  
  protected <T> List<T> select(Class<T> type) {
    Query q = em.createQuery("SELECT o FROM " + type.getSimpleName() + " o");
    List<T> result = q.getResultList();
    return result;
  }
  
  protected <T> int count(Class<T> type) {
    return select(type).size();
  }
}