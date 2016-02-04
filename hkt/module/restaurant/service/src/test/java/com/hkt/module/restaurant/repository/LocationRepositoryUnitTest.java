package com.hkt.module.restaurant.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.account.entity.AccountMembership.Status;
import com.hkt.module.restaurant.AbstractUnitTest;
import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.LocationAttribute;

@Transactional
public class LocationRepositoryUnitTest extends AbstractUnitTest {
  @Autowired
  private LocationRepository repo;

  @Before
  public void createContext() {
	  Location table = createTable("test");
	  repo.save(table);
  }

  @Test
  public void testCRUD() {
    

    Location table1 = createTable("test1");
    repo.save(table1);
    assertNotNull(table1);
    assertEquals(2, repo.findByAttribute("a", "b").size());
  }
  
  //khanhdd
  @Test
  public void testCRUD2() {
    Location table1 = createTable1("test3");
    repo.save(table1);
    assertNotNull(table1);
    assertEquals(2, repo.findLocationByStatus(com.hkt.module.restaurant.entity.Location.Status.Active).size());
  }
  
  //khanhdd
  @Test
  public void testCRUD1() {
    Location table1 = createTable("test2", true);
    repo.save(table1);
    assertNotNull(table1);
    assertEquals(1, repo.findByValueRecycleBin(true).size());
  }
  
  public Location createTable(String name) {
    Location table = new Location();
    table.setOrganizationLoginId("hkt");
    table.setName(name);
    table.setCode(name);
    table.setDescription(null);
    LocationAttribute attribute = new LocationAttribute();
    attribute.setName("a");
    attribute.setValue("b");
    table.add(attribute);
    return table;
  }
  
  //khanhdd
  public Location createTable(String name, boolean k) {
	    Location table = new Location();
	    table.setOrganizationLoginId("hkt");
	    table.setName(name);
	    table.setCode(name);
	    table.setDescription(null);
	    table.setRecycleBin(k);
	    LocationAttribute attribute = new LocationAttribute();
	    attribute.setName("a");
	    attribute.setValue("b");
	    table.add(attribute);
	    return table;
	  }

//khanhdd
  public Location createTable1(String name) {
	    Location table = new Location();
	    table.setOrganizationLoginId("hkt");
	    table.setName(name);
	    table.setCode(name);
	    table.setDescription(null);
	    table.setStatus(com.hkt.module.restaurant.entity.Location.Status.Active);
	    LocationAttribute attribute = new LocationAttribute();
	    attribute.setName("a");
	    attribute.setValue("b");
	    table.add(attribute);
	    return table;
	  }
}
