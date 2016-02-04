package com.hkt.module.promotion.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.promotion.entity.MenuItem;
import com.hkt.module.promotion.entity.MenuItem.MenuItemType;

public interface MenuItemRepository extends CrudRepository<MenuItem, Long>, MenuItemRepositoryCustom {

  public List<MenuItem> getMenuItemByMenuItemType(MenuItemType menuItemType);
  
  @Query("SELECT m FROM MenuItem m WHERE m.recycleBin = :valueBoolean")
  List<MenuItem> findMenuItemByRecycleBin(@Param("valueBoolean") boolean valueBoolean);
  
  @Modifying
  @Query("DELETE FROM MenuItem WHERE name like %:test%")
  public void deleteTest(@Param("test") String test);
}
