package com.hkt.client.rest.service;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import com.hkt.client.rest.ClientContext;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.partner.customer.entity.Credit;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.customer.entity.Point;

public class CustomerServiceClientIntegrationTest {
  ClientContext clientContext = ClientContext.getInstance() ;
  
  @Test
  public void testCustomerService() throws Exception {
//    CustomerServiceClient client = clientContext.getBean(CustomerServiceClient.class);
//    assertEquals(2, client.searchCustomer(new FilterQuery()).getData().size());
//    Customer customer = client.searchCustomer(new FilterQuery()).getData().get(0);
//    
//    Credit credit = client.getCreditByCustomerId(customer.getId());
//    Assert.assertNotNull(credit);
//    
//    Point point = client.getPointByCustomerId(customer.getId());
//    Assert.assertNotNull(point);
    
  }
}
