package com.hkt.module.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long>, RecipeRepositoryCustom {

  @Query(value = "SELECT r FROM Recipe r WHERE r.organizationLoginId = :organizationLoginId and r.recycleBin = 0")
  List<Recipe> findRecipeByOrganizationLoginId(@Param("organizationLoginId") String organizationLoginId);
  
  @Query("SELECT r FROM Recipe r WHERE  r.recycleBin = :value")
  List<Recipe> findRecipeByValueRecycleBin(@Param("value") boolean value);
  
  @Modifying
  @Query("DELETE FROM Recipe WHERE name like %:test%")
  public void deleteTest(@Param("test") String test);
  
  @Query("SELECT r FROM Recipe r WHERE r.productCode= :productCode  and r.recycleBin = 0 ")
  Recipe getRecipeByProductCode(@Param("productCode") String productCode);
}