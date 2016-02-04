package com.hkt.module.restaurant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.account.AccountService;
import com.hkt.module.account.util.AccountScenario;
import com.hkt.module.cms.CMSService;
import com.hkt.module.core.ServiceCallback;
import com.hkt.module.restaurant.entity.Recipe;
import com.hkt.module.restaurant.entity.Table;
import com.hkt.module.restaurant.util.RestaurantScenario;

@Transactional
public class RestaurantServiceUnitTest extends AbstractUnitTest {
  static ServiceCallback<RestaurantService> FAIL_CALLBACK = new ServiceCallback<RestaurantService>() {
    public void callback(RestaurantService service) {
      throw new RuntimeException("Fail. Expect a rollback if method annotate with the Transactionnal");
    }
  };

  @Autowired
  private RestaurantService restaurantService;


  private AccountScenario accountScenario;
  
  @Autowired
  private AccountService accountService;

  private RestaurantScenario restaurantScenario;
  
  @Autowired
  private CMSService cmsService;


  @Before
  public void setup() throws Exception {
    
    accountScenario = AccountScenario.loadTest();
    accountService.createScenario(accountScenario);
    
    restaurantScenario = RestaurantScenario.loadTest();
    restaurantService.createScenario(restaurantScenario);

  }

  @Test
  public void testSerialization() {
    assertTrue(restaurantScenario.getRecipeIngredients().size() > 0);
  }

  @Test
  public void testRecipe() throws Exception {
    Recipe recipe = restaurantService.filterRecipe(null).getData().get(0);
    assertEquals(2, recipe.getRecipeIngredients().size());
    restaurantService.deleteRecipe(recipe);
    assertEquals(0, restaurantService.filterRecipe(null).getData().size());
  }

  @Test
  public void testTable() throws Exception {
    Table table = restaurantService.getTables().get(0);
    table.setName("A022");
    table = restaurantService.saveTable(table);
    assertEquals("A022", table.getName());
    try {
      restaurantService.deleteTableCallBack(table, FAIL_CALLBACK);
    } catch (Throwable t) {
      System.out.println("Fail callback exception: " + t.getMessage());
    }
    assertEquals(10, restaurantService.getTables().size());
  }

  @After
  public void testDown() throws Exception {
    restaurantService.deleteAll();
    accountService.deleteAll();
    cmsService.deleteAll();
  }

}
