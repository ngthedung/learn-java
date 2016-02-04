package com.hkt.module.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.product.entity.ProductTag;

public interface ProductTagRepository extends CrudRepository<ProductTag, Long> {
  
  ProductTag getByTag(String tag);
  
  ProductTag getByCode(String code);
  
  @Modifying
  @Query("SELECT p FROM ProductTag p WHERE ((LCASE(p.label) like %:search% or LCASE(p.tag) like %:search%) and p.recycleBin = 0)) ORDER BY p.id DESC")
  List<ProductTag> findProductTag(@Param("search") String search);
  
  @Query("SELECT p FROM ProductTag p WHERE (REPLACE (p.tag, :tagRoot, SPACE(0)) not like '%:%' and REPLACE (p.tag, :tagRoot, SPACE(0)) != p.tag and p.recycleBin = 0)")
  List<ProductTag> findProductTagRoot(@Param("tagRoot") String tagRoot);
  
  @Query("SELECT p FROM ProductTag p WHERE (p.parentTag = :parentTag and p.recycleBin = 0)")
  List<ProductTag> getChildProductTag(@Param("parentTag") String parenttag);
  
  @Query("SELECT p FROM ProductTag p WHERE (p.parentTag IS NOT NULL AND (LENGTH(TRIM(p.tag))-(LENGTH(REPLACE(TRIM(p.tag), ':', '')))) > 1 AND p.recycleBin = 0)")
  List<ProductTag> findProductTagsLevel3();
  
  @Query("SELECT p FROM ProductTag p WHERE (LCASE(p.tag) like :tag% and p.recycleBin = 0) ORDER BY p.id DESC")
  List<ProductTag> getChildrenTags(@Param("tag") String tag);
  
  @Query("SELECT p FROM ProductTag p WHERE p.recycleBin = :valueBoolean")
  List<ProductTag> findByProductTagRecycleBin(@Param("valueBoolean") boolean valueBoolean);
  
  @Modifying
  @Query("DELETE FROM ProductTag WHERE label like %:test%")
  public void deleteTest(@Param("test") String test);
    
}