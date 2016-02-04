package com.hkt.module.partner.supplier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.partner.customer.entity.CreditTransaction;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.supplier.entity.Supplier;

public interface SupplierRepository extends CrudRepository<Supplier, Long>{

	//khanhdd
	@Query("SELECT s FROM Supplier s WHERE s.organizationLoginId = :organizationLoginId and s.recycleBin = 0 ORDER BY s.id DESC")
	List<Supplier> findByOrganizationLoginId(@Param("organizationLoginId") String organizationLoginId);	

	//khanhdd
	@Query("SELECT s FROM Supplier s WHERE  s.recycleBin = :value ORDER BY s.id DESC")
	List<Supplier> findByValueRecycleBin(@Param("value") boolean value); 
	

	@Query("SELECT p FROM Supplier p WHERE p.interactive = :interactive and p.recycleBin = 0  ORDER BY p.id DESC")
  List<Supplier> findSupplierByInteractive(@Param("interactive") boolean interactive);
	
	@Query("SELECT p FROM Supplier p WHERE p.code LIKE %:code% ORDER BY p.id DESC")
  List<Supplier> findSupplierByCode(@Param("code") String code);
	
	@Query("select s from Supplier s where s.loginId = :loginId and s.organizationLoginId = :organizationLoginId")
	Supplier getByOrgLoginIdAndLoginId(@Param("loginId") String loginId, @Param("organizationLoginId") String organizationLoginId);

	@Query("select s from Supplier s where s.code = :code")
	Supplier getSupplierByCode(@Param("code") String code);

	@Modifying
	@Query("DELETE FROM Supplier WHERE name like %:test%")
	public void deleteTest(@Param("test") String test);

}
