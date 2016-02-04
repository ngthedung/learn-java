package com.hkt.client.rest.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.hkt.client.rest.ClientContext;

public class PrintMachineServiceClientIntegrationTest {
	  private ClientContext clientContext = ClientContext.getInstance();
	  private PrintMachineServiceClient client = clientContext.getBean(PrintMachineServiceClient.class);
	  
	  @Test
	  public void testCurrency() throws Exception {
	    assertNotNull(client.getPrintMachines());
	  }
}
