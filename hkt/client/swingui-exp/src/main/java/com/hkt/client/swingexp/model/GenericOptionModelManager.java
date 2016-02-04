package com.hkt.client.swingexp.model;

import java.util.List;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.GenericOptionServiceClient;
import com.hkt.module.config.generic.Option;
import com.hkt.module.config.generic.OptionGroup;

public class GenericOptionModelManager {
  private static GenericOptionModelManager instance;

  private ClientContext clientContext = ClientContext.getInstance();
  private GenericOptionServiceClient clientCore = clientContext.getBean(GenericOptionServiceClient.class);

  private GenericOptionModelManager() {
    try {
      OptionGroup o =  getOptionGroup("accounting", "AccountingService", "type_invoice");
      if(o.getOption(AccountingModelManager.typeSanXuat)==null){
        Option option = new Option();
        option.setCode(AccountingModelManager.typeSanXuat);
        option.setLabel(AccountingModelManager.typeSanXuat);
        option.setPriority(6);
        o.getOptions().add(option);
        saveOptionGroup(o);
      }
    } catch (Exception e) {
    }
    

  }

  static public GenericOptionModelManager getInstance() {
    if (instance == null) {
      instance = new GenericOptionModelManager();
    }
    return instance;
  }

  public OptionGroup getOptionGroup(String module, String service, String name) throws Exception {
    return clientCore.getOptionGroup(module, service, name);
  }

  public boolean deleteOption(OptionGroup optionGroup, int index) throws Exception {
    return clientCore.deleteOption(optionGroup, index);
  }

  public void deleteTest(String test) throws Exception {
    List<OptionGroup> optionGroups = clientCore.getOptionGroups();
    for (OptionGroup optionGroup : optionGroups) {
      List<Option> countries = optionGroup.getOptions();
      for (int i = 0; i < countries.size(); i++) {
        if (countries.get(i).getLabel().indexOf(test) >= 0) {
          clientCore.deleteOption(optionGroup, i);
        }
      }
    }
  }

  public OptionGroup saveOptionGroup(OptionGroup optionGroup) throws Exception{
    return clientCore.saveOptionGroup(optionGroup);
  }
}
