package com.hkt.module.restaurant.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.springframework.transaction.annotation.Transactional;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.Account.Type;
import com.hkt.module.account.repository.AccountRepository;
import com.hkt.module.restaurant.AbstractUnitTest;
import com.hkt.module.restaurant.entity.Table;
import com.hkt.module.restaurant.entity.Table.Status;

@Transactional
public class TableRepositoryUnitTest extends AbstractUnitTest {
  @Autowired
  private TableRepository repo;

  @Autowired
  private AccountRepository accountRepository;

  @Before
  public void createContext() {
    Account account2 = new Account();
    account2.setLoginId("hkt");
    account2.setPassword("abc");
    account2.setEmail("hkt@email.com");
    account2.setType(Type.ORGANIZATION);
    accountRepository.save(account2);
  }

  @Test
  public void testCRUD() {
    Table table = createTable("test");
    repo.save(table);
    table = repo.getTableByCode("test");
    assertEquals(Status.TableFree, table.getStatus());
    assertNotNull(table);
    assertEquals(1, repo.findByLocationCode("hkt").size());
  }
  //khanhdd
  @Test
  public void testCRUD1(){
    Table table = createTable("test", true);
    repo.save(table);
   
    assertNotNull(table);
    assertEquals(1, repo.findByValueRecycleBin(true).size());
  }
  
  public Table createTable(String name) {
    Table table = new Table();
    table.setOrganizationLoginId("hkt");
    table.setResponsibleGroup("employer");
    table.setLocationCode("hkt");
    table.setName(name);
    table.setCode(name);
    table.setDescription(null);
    return table;
  }
  //khanhdd
  public Table createTable(String name, boolean k) {
	    Table table = new Table();
	    table.setOrganizationLoginId("hkt");
	    table.setResponsibleGroup("employer");
	    table.setLocationCode("hkt");
	    table.setName(name);
	    table.setCode(name);
	    table.setDescription(null);
	    table.setRecycleBin(k);
	    return table;
	  }
}
