package com.hkt.module.partner.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.partner.customer.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long>{
	//khanhdd
	@Query("SELECT c FROM Customer c WHERE c.organizationLoginId = :organizationLoginId and c.recycleBin = 0 ORDER BY c.id DESC")
	List<Customer> findByOrganizationLoginId(@Param("organizationLoginId") String organizationLoginId);

	@Query("select c from Customer c where c.loginId = :loginId and c.organizationLoginId = :organizationLoginId")
	Customer findCustomerByLoginIdAndOrganId(@Param("loginId") String loginId, @Param("organizationLoginId") String organizationLoginId);

	@Query("select c from Customer c where c.code = :code")
	Customer getCustomerByCode(@Param("code") String code);
	
	@Query("SELECT p FROM Customer p WHERE p.interactive = :interactive and p.recycleBin = 0 ORDER BY p.id DESC")
  List<Customer> findCustomerByInteractive(@Param("interactive") boolean interactive);
	
	@Query("SELECT p FROM Customer p WHERE p.code LIKE %:code% ORDER BY p.id DESC")
  List<Customer> findCustomerByCode(@Param("code") String code);
	//khanhdd
	@Query("SELECT c FROM Customer c WHERE  c.recycleBin = :value")
	List<Customer> findByValueRecycleBin(@Param("value") boolean value); 
	
	@Modifying
	@Query("DELETE FROM Customer WHERE name like %:test%")
	public void deleteTest(@Param("test") String test);
}
