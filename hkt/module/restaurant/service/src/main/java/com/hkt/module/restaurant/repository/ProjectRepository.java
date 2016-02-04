package com.hkt.module.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.restaurant.entity.Project;

public interface ProjectRepository extends CrudRepository<Project, Long> {

  Project getProjectByCode(String code);
  
  Project findOne(@Param("id") Long id);
  
  @Query("SELECT p FROM Project p WHERE p.parentCode = :parentCode and p.recycleBin = 0")
  List<Project> findProjectByParentCode(@Param("parentCode") String parentCode);
  
  @Query("SELECT p FROM Project p WHERE p.code LIKE %:code% ORDER BY p.id DESC")
  List<Project> findProjectByCode(@Param("code") String code);
  
  @Query("SELECT p FROM Project p WHERE p.recycleBin = :value AND p.code NOT LIKE '%other%'")
  List<Project> findByValueRecycleBin(@Param("value") boolean value); 
  
  @Query("SELECT p FROM Project p WHERE p.recycleBin = :value AND p.code NOT LIKE '%other%' "
      + "and p.status = :status ORDER BY p.id DESC ")
  List<Project> findByStatus(@Param("status") String status, @Param("value") boolean value); 
  
  @Query("SELECT p FROM Project p LEFT JOIN FETCH  p.projectMembers pm WHERE pm.employeeCode = :employeeCode ")
  List<Project> findInvoiceDetailByEmployeeCode(@Param("employeeCode") double employeeCode);
  
  @Modifying
  @Query("DELETE FROM ProjectMember WHERE projectId IN (SELECT id FROM Project WHERE name LIKE %:test%)")
  public void deleteTestProjectMember(@Param("test") String test);
  
  @Modifying
  @Query("DELETE FROM Project WHERE name like %:test%")
  public void deleteTest(@Param("test") String test);

}
