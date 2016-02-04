package com.hkt.client.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hkt.client.rest.RESTClient;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.core.rest.Request;
import com.hkt.module.core.rest.Response;

@Component
public class UIConfigServiceClient {
  @Autowired
  private RESTClient client ;
  
  Request create(String method) {
    return new Request("config", "UIConfigService", method) ;
  }
  
  public Capability getPermission(String organizationLoginId, String loginId, String screen) throws Exception {
    Request req = create("getPermission") ;
    req.addParam("organizationLoginId", organizationLoginId);
    req.addParam("loginId", loginId);
    req.addParam("screen", screen);
    Response res = client.POST(req) ;
    return res.getDataAs(Capability.class);
  }
  
 
}
