package com.hkt.client.rest.service;

import java.util.List;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hkt.client.rest.RESTClient;
import com.hkt.module.config.generic.Option;
import com.hkt.module.config.generic.OptionGroup;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.core.rest.Request;
import com.hkt.module.core.rest.Response;

@Component
public class GenericOptionServiceClient {
  @Autowired
  private RESTClient client ;
  
  Request create(String method) {
    return new Request("config", "GenericOptionService", method) ;
  }
  
  public OptionGroup getOptionGroup(String module, String service, String name) throws Exception {
    Request req = create("getOptionGroup") ;
    req.addParam("module", module) ;
    req.addParam("service", service);
    req.addParam("name", name);
    Response res = client.POST(req) ;
    return res.getDataAs(OptionGroup.class);
  }
  
  public OptionGroup saveOptionGroup(OptionGroup optionGroup) throws Exception {
    Request req = create("saveOptionGroup") ;
    req.addParam("optionGroup", optionGroup) ;
    Response res = client.POST(req) ;
    return res.getDataAs(OptionGroup.class);
  }
  
  public List<OptionGroup> getOptionGroups() throws Exception {
    Request req = create("getOptionGroups") ;
    Response res = client.POST(req) ;
    return res.getDataAs(new TypeReference<List<OptionGroup>>() {
    });
  }
  
  public boolean deleteOption(OptionGroup optionGroup, int index) throws Exception {
    Request req = create("deleteOption");
    req.addParam("optionGroup", optionGroup);
    req.addParam("index", index);
    client.POST(req);
    return true;
  }
 
  public FilterResult<OptionGroup> filterOptionGroup(FilterQuery query) throws Exception {
	    Request req = create("filterOptionGroup");
	    req.addParam("query", query);
	    Response res = client.POST(req);
	    return res.getDataAsByFilter(new TypeReference<FilterResult<OptionGroup>>() { });
	  }
  
  public List<Option> findOptions(String module, String service, String name, String exp) throws Exception {
    Request req = create("findOptions") ;
    req.addParam("module", module) ;
    req.addParam("service", service);
    req.addParam("name", name);
    req.addParam("exp", exp);
    Response res = client.POST(req) ;
    return res.getDataAs(new TypeReference<List<Option>>() {
    });
  }
}
