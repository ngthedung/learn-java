package com.hkt.client.rest.service;

import org.junit.Test;

import com.hkt.client.rest.ClientContext;
import com.hkt.module.core.entity.FilterQuery;

public class UIConfigServiceClientIntegrationTest {
  ClientContext clientContext = ClientContext.getInstance();
  RestaurantServiceClient client = clientContext.getBean(RestaurantServiceClient.class);

  @Test
  public void testCongigService() throws Exception {
    System.out.println(client.filterRecipe(new FilterQuery()).getData());

  }
}
