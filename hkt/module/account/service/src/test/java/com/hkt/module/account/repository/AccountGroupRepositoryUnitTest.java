
package com.hkt.module.account.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.account.AbstractUnitTest;
import com.hkt.module.account.entity.AccountGroup;

@Transactional
public class AccountGroupRepositoryUnitTest extends AbstractUnitTest {
  
  @Autowired
  AccountGroupRepository repository;
  
  @Test
  public void testCrud() {
  
    AccountGroup employee = new AccountGroup("employee");
    employee = repository.save(employee);
    assertEquals(employee, repository.findOne(employee.getId()));
    
    employee.setDescription("update");
    assertEquals("update", repository.findOne(employee.getId()).getDescription());
    assertEquals(1, repository.count());
    
    repository.delete(employee.getId());
    assertNull(repository.findOne(employee.getId()));
    assertEquals(0, repository.count());
  }
  
  //khanhdd
  @Test
  public void testCrud1() {
  
    AccountGroup employee = new AccountGroup("employee");
    employee.setRecycleBin(true);
    employee = repository.save(employee);
    
    assertNotNull(employee);
    assertEquals(1, repository.findByValueRecycleBin(true).size());
  }
  @Test
  public void testParentChildren() {
  
    AccountGroup employee = new AccountGroup("employee");
    employee = repository.save(employee);
    
    AccountGroup hr = new AccountGroup(employee, "hr");
    hr = repository.save(hr);
    
    AccountGroup hrManager = new AccountGroup(hr, "manager");
    hrManager = repository.save(hrManager);
    
    AccountGroup hrManager1 = new AccountGroup(hrManager, "long");
    repository.save(hrManager1);
    
    assertEquals(4, repository.count());
    assertEquals(1, repository.findRootGroup().size());
    assertEquals(1, repository.findByParentPath("employee").size());
    assertNotNull(repository.getByPath("employee/hr"));
    
    AccountGroup kd = new AccountGroup(employee, "kd");
    kd = repository.save(kd);
    System.out.println(1);
    System.out.println(repository.getChildrensByPath("employee/hr"));
    System.out.println(repository.getAllChildrensByPath("employee/hr"));
    System.out.println(2);
    assertEquals(2, repository.findByParentPath("employee").size());
    assertEquals(1, repository.findByParentPathAndName("employee", "k").size());
    
     assertEquals(3, repository.cascadeDelete(hr)) ;
     assertEquals(2, repository.count());
     
    List<AccountGroup> accGroups = (List<AccountGroup>) repository.findAll();
    for (AccountGroup accGroup : accGroups) {
      System.out.print(accGroup.getPath() + " - " + accGroup.getParentPath());
      System.out.println();
    }
    System.out.println();
    List<AccountGroup> list = repository.getChildrensByPath(employee.getPath());
    for (AccountGroup accountGroup : list) {
      System.out.print(accountGroup.getPath() + " - " + accountGroup.getParentPath());
      System.out.println();
    }
    
  }
}