package com.hkt.module.partner.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.partner.customer.entity.Credit;

public interface CreditRepository extends CrudRepository<Credit, Long>, CreditRepositoryCustom {

  Credit getByCustomerId(Long customerId);
  
  @Query("SELECT c FROM Credit c WHERE  c.recycleBin = :value")
  List<Credit> findByValueRecycleBin(@Param("value") boolean value); 
  
  @Modifying
  @Query(value = "DELETE FROM Credit WHERE customerId = :customerId ")
  public void deleteByCustomerId(@Param("customerId") Long customerId);
  
  @Modifying
  @Query("DELETE FROM Credit "
      + "WHERE customerId in (SELECT id FROM Customer WHERE name like %:test%)")
  public void deleteTest(@Param("test") String test);
}
