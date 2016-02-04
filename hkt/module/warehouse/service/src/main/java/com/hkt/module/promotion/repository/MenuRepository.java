package com.hkt.module.promotion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hkt.module.promotion.entity.Menu;
import com.hkt.module.promotion.entity.Menu.Type;

public interface MenuRepository extends JpaRepository<Menu, Long>, MenuRepositoryCustom {

	@Query("SELECT m FROM Menu m LEFT JOIN FETCH m.menuItems WHERE m.code = :code ORDER BY m.id DESC")
	Menu getMenuByCode(@Param("code") String code);

	@Query("SELECT m FROM Menu m LEFT JOIN FETCH m.menuItems WHERE m.id = :id")
	Menu findOne(@Param("id") Long id);

	@Query("SELECT m FROM Menu m WHERE (m.type = :type and m.recycleBin = 0) ORDER BY m.id DESC")
	List<Menu> getMenuByType(@Param("type") Type type);
	
	@Query("SELECT m FROM Menu m WHERE m.recycleBin = :valueBoolean ORDER BY m.id DESC")
	List<Menu> findMenuByRecycleBin(@Param("valueBoolean") boolean valueBoolean);

	@Modifying
	@Query("DELETE FROM Menu WHERE name like %:test%")
	public void deleteTest(@Param("test") String test);

}
