package com.hkt.module.restaurant;

import org.springframework.stereotype.Component;

import com.hkt.module.config.generic.OptionGroup;
import com.hkt.module.config.generic.OptionGroupPlugin;

@Component
public class RestaurantOptionGroupPlugin extends OptionGroupPlugin {
  public OptionGroup[] getOptionGroups() throws Exception {
    String[] resPath = {
      "classpath:config/restaurant/store.json",
      "classpath:config/restaurant/the-cashier.json"
    };
    return loadOptionGroups(resPath);
  }

}
