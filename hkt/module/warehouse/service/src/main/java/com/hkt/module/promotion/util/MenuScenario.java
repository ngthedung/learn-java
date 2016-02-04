package com.hkt.module.promotion.util;

import java.util.List;

import com.hkt.module.product.entity.Product;
import com.hkt.module.promotion.entity.Menu;
import com.hkt.module.promotion.entity.MenuItemRef;
import com.hkt.util.IOUtil;
import com.hkt.util.json.JSONReader;

public class MenuScenario {
  private List<MenuItemRef> menuItemRefs;
  private List<Menu> menus; 
  
  public List<MenuItemRef> getMenuItemRefs() {
    return menuItemRefs;
  }

  public void setMenuItemRefs(List<MenuItemRef> menuItemRefs) {
    this.menuItemRefs = menuItemRefs;
  }

  public List<Menu> getMenus() {
    return menus;
  }

  public void setMenus(List<Menu> menus) {
    this.menus = menus;
  }

  static public MenuScenario load(String res) throws Exception {
    JSONReader reader = new JSONReader(IOUtil.loadRes(res)) ;
    MenuScenario scenario = reader.read(MenuScenario.class) ;
    return scenario ;
  }
  
  static public MenuScenario loadTest() throws Exception {
    return load("classpath:scenario/menu.json") ;
  }
  
}
