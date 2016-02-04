package com.hkt.module.accounting.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hkt.module.accounting.AbstractUnitTest;
import com.hkt.module.accounting.entity.ManageCodes;
import com.hkt.module.accounting.entity.ManageCodes.ManageType;
import com.hkt.module.accounting.entity.ManageCodes.RotationType;
import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.LocationAttribute;

public class CodesRepositoryUnitTest extends AbstractUnitTest{
	@Autowired
	private CodesRespository codesRespository;
	
	@Before(value = "")
	public void createContext(){
		 ManageCodes table = createTable("test");
		 codesRespository.save(table);
	}
	
	 @Test
	  public void testCRUD() {
	    ManageCodes table1 = createTable("test2", true);
	    codesRespository.save(table1);
	    assertNotNull(table1);
	    System.out.println(codesRespository.getCodesByCode("test2"));
	    assertEquals(1, codesRespository.findByValueRecycleBin(true).size());
	  }
	 
	 public ManageCodes createTable(String name) {
		    ManageCodes table = new ManageCodes();
		    table.setManageType(ManageType.Circle);
		    table.setRotationType(RotationType.ByDay);
		    table.setCode(name);
		    table.setTypeCode(name);
		    table.setDescription(null);
		    
		    return table;
		  }
	 

	  public ManageCodes createTable(String name, boolean k) {
		    ManageCodes table = new ManageCodes();
		    table.setManageType(ManageType.Circle);
		    table.setRotationType(RotationType.ByDay);
		    table.setCode(name);
		    table.setTypeCode(name);
		    table.setDescription(null);
		    table.setRecycleBin(k);
		    return table;
		  }

}
