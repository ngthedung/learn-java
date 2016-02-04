package com.hkt.client.rest.service;

import org.junit.Test;

import com.hkt.client.rest.ClientContext;

public class RestaurantServiceClientIntegrationTest {
  private ClientContext clientContext = ClientContext.getInstance();
  private RestaurantServiceClient client = clientContext.getBean(RestaurantServiceClient.class);
  
  @Test
  public void testRestaurantService() throws Exception {
  
//    Table table = client.getTableByCode("A01");
//    assertNotNull(table);
//    table.setDescription("Test A01");
//    
//    client.saveTable(table);
//    table = client.getTableByCode("A01");
//    assertEquals("Test A01", table.getDescription());
//    
//    List<Table> tables = client.getTables();
//    for(Table bean: tables){
//      bean.setDescription("Test Table");
//    }
//    
//    // TODO error saveTables
//    client.saveTables(tables);
  //	System.out.println(client.findProjectByCode("10"));
  }
}
