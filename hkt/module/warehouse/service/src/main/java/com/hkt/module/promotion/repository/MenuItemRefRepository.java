package com.hkt.module.promotion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.promotion.entity.MenuItemRef;

public interface MenuItemRefRepository extends CrudRepository<MenuItemRef, Long>  {
  
  MenuItemRef getMenuItemRefByCode(String code);
  
  @Query("SELECT m FROM MenuItemRef m WHERE m.recycleBin = :valueBoolean")
  List<MenuItemRef> findMenuItemRefByRecycleBin(@Param("valueBoolean") boolean valueBoolean);
  
  @Modifying
  @Query("DELETE FROM MenuItemRef WHERE name like %:test%")
  public void deleteTest(@Param("test") String test);
}
