package com.hkt.module.hr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.hr.entity.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long>{

  // khanhdd
  @Query("SELECT e FROM Employee e WHERE e.organizationLoginId = :organizationLoginId and e.recycleBin = 0 ORDER BY e.id DESC")
  List<Employee> findByOrganizationLoginId(@Param("organizationLoginId") String organizationLoginId);

  @Query("SELECT e FROM Employee e WHERE e.permissionGroup = :permissionGroup and e.recycleBin = 0")
  List<Employee> findByPermissionGroup(@Param("permissionGroup") String permissionGroup);

  @Query("select e from Employee e where e.loginId = :loginId and e.organizationLoginId = :organizationLoginId")
  Employee getByOrgLoginIdAndLoginId(@Param("loginId") String loginId,
      @Param("organizationLoginId") String organizationLoginId);

  Employee getEmpByCode(String code);

  @Query("SELECT e FROM Employee e WHERE  e.recycleBin = :value")
  List<Employee> findByValueRecycleBin(@Param("value") boolean value);

  @Modifying
  @Query("DELETE FROM Employee WHERE name like %:test%")
  public void deleteTest(@Param("test") String test);
}
