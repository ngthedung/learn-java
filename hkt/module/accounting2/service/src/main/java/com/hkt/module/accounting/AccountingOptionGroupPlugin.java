package com.hkt.module.accounting;

import org.springframework.stereotype.Component;

import com.hkt.module.config.generic.OptionGroup;
import com.hkt.module.config.generic.OptionGroupPlugin;

@Component
public class AccountingOptionGroupPlugin extends OptionGroupPlugin {
  public OptionGroup[] getOptionGroups() throws Exception {
    String[] resPath = {
      "classpath:config/accounting/contributor_role.json",
      "classpath:config/accounting/type_invoice.json"
    };
    return loadOptionGroups(resPath);
  }

}
