package com.hkt.module.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.product.entity.ProductPriceType;

public interface ProductPriceTypeRepository extends CrudRepository<ProductPriceType, Long> {

  ProductPriceType getByType(String type);
  
  @Query("SELECT p FROM ProductPriceType p WHERE (LCASE(p.type) like %:type% and p.recycleBin = 0)")
  List<ProductPriceType> searchByType(@Param("type") String type);
  
  @Query("SELECT p FROM ProductPriceType p WHERE p.recycleBin = :valueBoolean")
  List<ProductPriceType> findByProductPriceTypeRecycleBin(@Param("valueBoolean") boolean valueBoolean);
  
  @Modifying
  @Query("DELETE FROM ProductPriceType WHERE name like %:test%")
  public void deleteTest(@Param("test") String test);
}