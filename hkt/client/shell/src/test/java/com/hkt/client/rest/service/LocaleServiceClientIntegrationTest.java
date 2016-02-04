package com.hkt.client.rest.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.hkt.client.rest.ClientContext;
import com.hkt.module.config.locale.Country;

public class LocaleServiceClientIntegrationTest {
  ClientContext clientContext = ClientContext.getInstance();
  LocaleServiceClient client = clientContext.getBean(LocaleServiceClient.class);
  
  @Test
  public void testCountry() throws Exception {
    assertNotNull(client.getCountries());
    assertNotNull(client.getCountry("Việt Nam"));
    
    Country country = new Country();
    country.setName("French");
    country.setFlag("fr");
    country.setDescription("quốc gia Pháp");
    
    country = client.saveCountry(country);
    
    client.deleteCountry(country);
    assertEquals(1, client.getCountries().size());
  }
  
  @Test
  public void testCurrency() throws Exception {
    assertNotNull(client.getCurrency("USD"));
  }
  
}
