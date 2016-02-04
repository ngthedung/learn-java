package com.hkt.module.restaurant;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hkt.module.config.generic.GenericOptionService;
import com.hkt.module.config.generic.OptionGroup;


public class GenericOptionServiceUnitTest extends AbstractUnitTest {
  @Autowired
  private GenericOptionService service;
  
  @Test
  public void testTableLocation() throws Exception {
    OptionGroup optGroup = service.getOptionGroup("restaurant", "RestaurantService", "the-cashier") ;
    assertNotNull(optGroup) ;
  }
  
}
