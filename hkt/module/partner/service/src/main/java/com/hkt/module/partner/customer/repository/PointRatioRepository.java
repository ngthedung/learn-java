package com.hkt.module.partner.customer.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.partner.customer.entity.Credit;
import com.hkt.module.partner.customer.entity.PointConversionRule;

public interface PointRatioRepository extends CrudRepository<PointConversionRule, Long> {
	
  //khanhdd add loc theo recycleBin
  @Query("SELECT p FROM PointConversionRule p WHERE p.organizationLoginId = :organizationLoginId and p.recycleBin = 0" )
  List<PointConversionRule> findByOrganizationLoginId(@Param("organizationLoginId") String organizationLoginId);
  
  //khanhdd add loc theo recycleBin
  @Query("SELECT p FROM PointConversionRule p WHERE p.name like %:name% and p.recycleBin =0")
  List<PointConversionRule> findByName(@Param("name") String name);
  
  @Query("SELECT p FROM PointConversionRule p WHERE p.minPointToTrigger <= :pointToTrigger "
      + "and (p.dateTo > :date or p.dateTo is null) and p.dateFrom <= :date "
      + "Group By p.id HAVING max(p.pointToCreditRatio) = (SELECT Max(pointToCreditRatio) FROM PointConversionRule) "
      + "and max(p.id) = (SELECT Max(id) FROM PointConversionRule)")
  PointConversionRule getConversionRulePointToMoney(@Param("pointToTrigger") double pointToTrigger
      ,@Param("date") Date date);
  
  @Query("SELECT p FROM PointConversionRule p WHERE "
      + "(p.dateTo > :date or p.dateTo is null) and p.dateFrom <= :date "
      + "Group By p.id HAVING max(p.pointToCreditRatio) = (SELECT Max(pointToCreditRatio) FROM PointConversionRule) "
      + "and max(p.id) = (SELECT Max(id) FROM PointConversionRule)")
  PointConversionRule getConversionRuleMoneyToPoint(@Param("date") Date date);
  
  //khanhdd
  @Query("SELECT p FROM PointConversionRule p WHERE p.recycleBin = :value")
  List<PointConversionRule> findByValueRecycleBin(@Param("value") boolean value); 
  
  @Modifying
  @Query(value = "DELETE FROM PointConversionRule WHERE organizationLoginId = :organizationLoginId")
  public void deleteByOrganizationLoginId(@Param("organizationLoginId") String organizationLoginId);
  
  @Modifying
  @Query("DELETE FROM PointConversionRule WHERE name like %:test%")
  public void deleteTest(@Param("test") String test);
}
