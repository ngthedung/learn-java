package com.hkt.module.promotion.repository;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductTag;
import com.hkt.module.product.repository.ProductRepository;
import com.hkt.module.product.repository.ProductTagRepository;
import com.hkt.module.promotion.PromotionService;
import com.hkt.module.promotion.entity.Menu;
import com.hkt.module.promotion.entity.MenuItem;
import com.hkt.module.promotion.entity.MenuItemRef;
import com.hkt.module.promotion.entity.MenuItem.MenuItemType;
import com.hkt.module.warehouse.AbstractUnitTest;


@Transactional
public class MenuRepositoryUnitTest extends AbstractUnitTest {
  
  static {
    System.setProperty("hibernate.show_sql", "true");
    System.setProperty("hibernate.format_sql", "true");
  }
  
  @Autowired
  MenuRepository menuRepository;
  
  @Autowired
  MenuItemRepository itemRepository;
  
  
  @Autowired
  PromotionService menuItemRefRepository;
  
  @Autowired
  private ProductTagRepository tagRepo;
  
  @Autowired
  ProductRepository repository;
  
  @Before
  public void begin(){
    String[] tags = { "Tag 1", "Tag 2", "Tag 3" };
    for (int i = 0; i < tags.length; i++) {
      ProductTag productTag = new ProductTag();
      productTag.setTag(tags[i]);
      productTag.setLabel(tags[i]);
      productTag.setRecycleBin(false);
      tagRepo.save(productTag);
    }
    
    List<ProductTag> pTags = (List<ProductTag>) tagRepo.findAll() ; 
    for (int i = 0; i < 3; i++) {
      repository.save(createProduct("iPhone"+i, "Product HktTest"+i, "Made by", pTags));
    }    
  }
  
    
  @Test
  public void testCrud() {
    Menu m = menuRepository.save(createMenu());
    assertEquals(m, menuRepository.findOne(m.getId()));
    assertEquals(1, menuRepository.findMenuByRecycleBin(false).size());
    assertEquals(1, menuRepository.count());
    assertEquals(5, menuRepository.findOne(m.getId()).getMenuItems().size());
    assertEquals(5, itemRepository.getMenuItemByMenuItemType(MenuItemType.ByMenuItemRef).size());
    assertEquals(5, itemRepository.findMenuItemByRecycleBin(false).size());
    menuRepository.delete(m);
    assertEquals(0, menuRepository.count());
  }
  
  @Test
  public void testMenuItemRef() {
    MenuItemRef mir = new MenuItemRef();
    mir.setCode("1");
    mir.setName("a");
    mir.setRecycleBin(false);
    mir.setProducts((List<Product>)repository.findAll());
    mir = menuItemRefRepository.saveMenuItemRef(mir);
    assertEquals("a", mir.getName());
    mir.setName("b");
    mir.getProducts().remove(0);
    System.out.println("........................"+mir.getProducts());
    mir = menuItemRefRepository.saveMenuItemRef(mir);
    mir = menuItemRefRepository.getMenuItemRefByCode("1");
    mir.setProducts((List<Product>)repository.findAll());
    mir = menuItemRefRepository.saveMenuItemRef(mir);
    mir = menuItemRefRepository.getMenuItemRefByCode("1");
    System.out.println("........................"+mir.getProducts().get(0).getProductTags());
    assertEquals("b", mir.getName());
  }
  
  public Menu createMenu(){
    Menu  menu = new Menu();
    menu.setName("Menu 1");
    menu.setCode("0123");
    menu.setRecycleBin(false);
    List<MenuItem> menuItems = new ArrayList<MenuItem>();
    for (int i = 0; i < 5; i++) {
      MenuItem menuItem = new MenuItem();
      menuItem.setMenuItemType(MenuItemType.ByMenuItemRef);
      menuItem.setName("Item "+i);  
      menuItem.setRecycleBin(false);
      menuItems.add(menuItem);
      
    }
    menu.setMenuItems(menuItems);   
    return menu;
  }
  
  @After
  public void deleteAll(){
    menuRepository.deleteAll();
  }

}
