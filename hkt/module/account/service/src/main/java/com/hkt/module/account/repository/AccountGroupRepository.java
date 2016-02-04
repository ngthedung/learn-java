
package com.hkt.module.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.account.entity.AccountGroup;

public interface AccountGroupRepository extends CrudRepository<AccountGroup, Long>, AccountGroupRepositoryCustom {
  
  @Query("select g from AccountGroup g where g.path =:path")
  AccountGroup getByPath(@Param("path") String path);
  
  @Query("select g from AccountGroup g where g.parentPath like :parentPath and g.path not like '%-other%' and g.recycleBin = 0")
  List<AccountGroup> findByParentPath(@Param("parentPath") String parentPath);
  
  @Query("select g from AccountGroup g where g.parentPath is null and g.recycleBin = 0")
  List<AccountGroup> findRootGroup();
  
  @Query("select g from AccountGroup g where LCASE(g.name) like %:name% and g.recycleBin = 0")
  List<AccountGroup> findByName(@Param("name") String name);
  
  @Query("select g from AccountGroup g where LCASE(g.name) =:name")
  AccountGroup getByName(@Param("name") String name);
  
  @Query("select g from AccountGroup g where g.parentPath =:parentPath and LCASE(g.name) like %:name% "
  		+ "and g.path not like '%-other%' and g.recycleBin = 0")
  List<AccountGroup> findByParentPathAndName(@Param("parentPath") String parentPath, @Param("name") String name);
  
  @Query("SELECT p FROM AccountGroup p WHERE "
  + "REPLACE (p.parentPath, :path, SPACE(0)) not like '%/%' "
  + "and REPLACE (p.parentPath, :path, SPACE(0)) != p.parentPath "
  + "and p.path not like '%-other%' and p.recycleBin = 0")
  List<AccountGroup> getChildrensByPath(@Param("path") String path);
  
  @Query("select g from AccountGroup g where g.parentPath like :path% and g.parentPath is not null "
  		+ "and g.path not like '%-other%' and g.recycleBin = 0 ORDER BY g.id DESC")
  List<AccountGroup> getAllChildrensByPath(@Param("path") String path);
  
  @Query("SELECT a FROM AccountGroup a WHERE  a.recycleBin = :value")
  List<AccountGroup> findByValueRecycleBin(@Param("value") boolean value); 
  
  @Modifying
  @Query("DELETE FROM AccountGroup WHERE label like %:test%")
  public void deleteTest(@Param("test") String test);
}
