package com.hkt.module.hr;

import org.springframework.stereotype.Component;

import com.hkt.module.config.generic.OptionGroup;
import com.hkt.module.config.generic.OptionGroupPlugin;

@Component
public class HROptionGroupPlugin extends OptionGroupPlugin {
  public OptionGroup[] getOptionGroups() throws Exception {
    String[] resPath = {
      "classpath:config/hr/work-position.json"
    };
    return loadOptionGroups(resPath);
  }

}
