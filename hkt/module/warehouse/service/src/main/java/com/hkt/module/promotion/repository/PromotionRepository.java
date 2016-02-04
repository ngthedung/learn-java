package com.hkt.module.promotion.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hkt.module.promotion.entity.PromotionDetail;

public interface PromotionRepository extends JpaRepository<PromotionDetail, Long>  {
  
	@Query("SELECT p FROM PromotionDetail p WHERE (p.organizationLoginId = :organizationLoginId and p.recycleBin = 0) ORDER BY p.id DESC")
  List<PromotionDetail> findByOrganizationLoginId(@Param("organizationLoginId") String organizationLoginId);
  
  @Query(
    "SELECT p FROM PromotionDetail p JOIN p.productTargets t " 
    + "WHERE (t.productIdentifierId IN :tags "
    + "and (p.toDate > :date or p.toDate is null) and p.fromDate <= :date "
    + "and p.appliedToMinExpense <= :money and p.maxExpense >= :money "
    + "and p.haveCustomer = 0 and p.haveLocation = 0 and p.recycleBin = 0) "
    + "and (p.storeCode is null or p.storeCode = :storeCode) ORDER BY p.id DESC"
    )
  List<PromotionDetail> findByProduct(@Param("tags") List<String> tags, @Param("date") Date date, @Param("money") Double money
      , @Param("storeCode") String storeCode);
  
  @Query(
      "SELECT p FROM PromotionDetail p JOIN p.customerTargets t "
      + "WHERE (t.customerGroup in :customerGroups "
      + "and (p.toDate > :date or p.toDate is null) and p.fromDate <= :date "
      + "and p.appliedToMinExpense <= :money and p.maxExpense >= :money "
      + "and p.haveProduct = 0 and p.haveLocation = 0 and p.recycleBin = 0) "
      + "and (p.storeCode is null or p.storeCode = :storeCode) ORDER BY p.id DESC"
      )
  List<PromotionDetail> findByCustomerGroup(@Param("customerGroups") List<String> customerGroups, @Param("date") Date date, 
      @Param("money") Double money, @Param("storeCode") String storeCode);
  
  @Query(
      "SELECT p FROM PromotionDetail p JOIN p.locationTargets t "
      + "WHERE ((t.location = :location or t.location = :table) "
      + "and (p.toDate > :date or p.toDate is null) and p.fromDate <= :date "
      + "and p.appliedToMinExpense <= :money and p.maxExpense >= :money "
      + "and p.haveProduct = 0 and p.haveCustomer = 0 and p.recycleBin = 0) "
      + "and (p.storeCode is null or p.storeCode = :storeCode) ORDER BY p.id DESC"
      )
  List<PromotionDetail> findByLocation(@Param("table") String table,@Param("location") String location, @Param("date") Date date,
      @Param("money") Double money, @Param("storeCode") String storeCode);
  
  @Query(
      "SELECT p FROM PromotionDetail p "
      + "WHERE p.storeCode = :storeCode "
      + "and (p.toDate > :date or p.toDate is null) and p.fromDate <= :date "
      + "and p.appliedToMinExpense <= :money and p.maxExpense >= :money "
      + "and p.haveProduct = 0 and p.haveCustomer = 0 and p.haveLocation = 0 and p.recycleBin = 0 ORDER BY p.id DESC "
      )
  List<PromotionDetail> findByStore(@Param("date") Date date,
      @Param("money") Double money, @Param("storeCode") String storeCode);
  
  @Query(
      "SELECT p FROM PromotionDetail p JOIN p.locationTargets t JOIN p.customerTargets e "
      + "WHERE ((t.location = :location or t.location = :table) "
      + "and e.customerGroup IN :customerGroups "
      + "and (p.toDate > :date or p.toDate is null) and p.fromDate <= :date "
      + "and p.appliedToMinExpense <= :money and p.maxExpense >= :money "
      + "and p.haveProduct = 0 and p.recycleBin = 0) "
      + "and (p.storeCode is null or p.storeCode = :storeCode) ORDER BY p.id DESC"
      )
  List<PromotionDetail> findPromotion(@Param("table") String table,@Param("location") String location, @Param("customerGroups") List<String> customerGroup,
      @Param("date") Date date, @Param("money") Double money, @Param("storeCode") String storeCode);
  
  @Query(
      "SELECT p FROM PromotionDetail p JOIN p.customerTargets t JOIN p.productTargets e "
      + "WHERE (t.customerGroup in :customerGroups "
      + "and e.productIdentifierId in :tags "
      + "and (p.toDate > :date or p.toDate is null) and p.fromDate <= :date "
      + "and p.appliedToMinExpense <= :money and p.maxExpense >= :money "
      + "and p.haveLocation = 0 and p.recycleBin = 0) "
      + "and (p.storeCode is null or p.storeCode = :storeCode) ORDER BY p.id DESC"
      )
  List<PromotionDetail> findByCustomerGroupAndProduct(@Param("customerGroups") List<String> customerGroups, @Param("tags") List<String> tags, 
      @Param("date") Date date, @Param("money") Double money, @Param("storeCode") String storeCode);
  
  @Query(
      "SELECT p FROM PromotionDetail p JOIN p.locationTargets t JOIN p.productTargets e "
      + "WHERE ((t.location = :location or t.location =:table) "
      + "and e.productIdentifierId in :tags "
      + "and (p.toDate > :date or p.toDate is null) and p.fromDate <= :date "
      + "and p.appliedToMinExpense <= :money and p.maxExpense >= :money "
      + "and p.haveCustomer = 0 and p.recycleBin = 0) "
      + "and (p.storeCode is null or p.storeCode = :storeCode) ORDER BY p.id DESC"
      )
  List<PromotionDetail> findByLocationAndProduct(@Param("table") String table, @Param("location") String location, 
      @Param("tags") List<String> tags, @Param("date") Date date, @Param("money") Double money, @Param("storeCode") String storeCode);
  
  @Query(
      "SELECT p FROM PromotionDetail p JOIN p.customerTargets t JOIN p.productTargets e JOIN p.locationTargets l "
      + "WHERE (t.customerGroup in :customerGroups "
      + "and e.productIdentifierId in :tags "
      + "and (l.location = :location or l.location =:table) "
      + "and (p.toDate > :date or p.toDate is null) and p.fromDate <= :date "
      + "and p.appliedToMinExpense <= :money and p.maxExpense >= :money and p.recycleBin = 0) "
      + "and (p.storeCode is null or p.storeCode = :storeCode) ORDER BY p.id DESC"
      )
  List<PromotionDetail> findPromotionProduct(@Param("table") String table, @Param("location") String location,
      @Param("customerGroups") List<String> customerGroups, @Param("tags") List<String> tags, @Param("date") Date date,
      @Param("money") Double money, @Param("storeCode") String storeCode);
  
  @Query("SELECT p FROM PromotionDetail p LEFT JOIN FETCH p.productTargets LEFT JOIN FETCH p.customerTargets LEFT JOIN FETCH p.locationTargets WHERE p.id = :id ORDER BY p.id DESC")
  PromotionDetail findOne(@Param("id") Long id) ;

  @Query("SELECT p FROM PromotionDetail p WHERE p.recycleBin = :valueBoolean ORDER BY p.id DESC")
  List<PromotionDetail> findPromotionDetailByRecycleBin(@Param("valueBoolean") boolean valueBoolean);
  
  @Query(
      "SELECT p FROM PromotionDetail p JOIN p.customerTargets c "
      + "WHERE (c.customerGroup IN :groupPaths "
      + "and (p.toDate > :date or p.toDate is null) and p.fromDate <= :date "
      + "and p.appliedToMinExpense <= :money and p.maxExpense >= :money and p.recycleBin = 0) "
      + "and (p.storeCode is null or p.storeCode = :storeCode)"
  		)
  List<PromotionDetail> findByCustomerTargetGroup(@Param("groupPaths") List<String> groupPaths, @Param("date") Date date,
      @Param("money") Double money, @Param("storeCode") String storeCode) ;
  
  @Modifying
  @Query("DELETE FROM CustomerTarget "
      + "WHERE promotionId in (SELECT id FROM PromotionDetail WHERE name like %:test%)")
  public void deleteTestCustomerTarget(@Param("test") String test);
  
  @Modifying
  @Query("DELETE FROM LocationTarget "
      + "WHERE promotionId in (SELECT id FROM PromotionDetail WHERE name like %:test%)")
  public void deleteTestLocationTarget(@Param("test") String test);
  
  @Modifying
  @Query("DELETE FROM ProductTarget "
      + "WHERE promotionId in (SELECT id FROM PromotionDetail WHERE name like %:test%)")
  public void deleteTestProductTarget(@Param("test") String test);
  
  @Modifying
  @Query("DELETE FROM PromotionDetail WHERE name like %:test%")
  public void deleteTest(@Param("test") String test);
}
