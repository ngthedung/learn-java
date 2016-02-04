package com.hkt.client.rest.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.hkt.client.rest.ClientContext;
import com.hkt.module.core.entity.FilterQuery;

public class HRServiceClientIntegrationTest {
  ClientContext clientContext = ClientContext.getInstance() ;
  
  @Test
  public void testHRService() throws Exception {
    HRServiceClient client = clientContext.getBean(HRServiceClient.class);

    //assertEquals(5, client.searchEmployee(new FilterQuery()).getData().size());
  }
}
