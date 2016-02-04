package com.hkt.module.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.product.entity.Tax;

public interface TaxRepository extends CrudRepository<Tax, Long> {

  Tax getByCode(String code);
  
  @Query(value = "SELECT t FROM Tax t WHERE (LCASE(t.name) LIKE %:name% and t.recycleBin = 0)")
  List<Tax> findByName(@Param("name") String name);
  
  @Query(value = "SELECT t FROM Tax t WHERE t.recycleBin = :valueBoolean")
  List<Tax> findByTaxRecycleBin(@Param("valueBoolean") boolean valueBoolean);
  
  @Modifying
  @Query("DELETE FROM Tax WHERE name like %:test%")
  public void deleteTest(@Param("test") String test);

}
