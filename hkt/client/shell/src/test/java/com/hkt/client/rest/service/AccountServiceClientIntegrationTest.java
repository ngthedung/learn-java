package com.hkt.client.rest.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.hkt.client.rest.ClientContext;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountGroupDetail;

public class AccountServiceClientIntegrationTest {
  ClientContext clientContext = ClientContext.getInstance();
  AccountServiceClient client = clientContext.getBean(AccountServiceClient.class);
  
  @Test
  public void testAccountService() throws Exception {
    
    assertNotNull(client.getAccountByLoginId("hkt"));
    assertNotNull(client.login("hkt", "hkt"));
    
    System.out.println(client.getAccountByLoginId("tuan").getProfiles().getBasic());
    
    assertEquals(1, client.findAccountByLoginId("hkt").size());
  }
  
  @Test
  public void testGroupService() throws Exception {
    AccountGroupDetail rootDetail = client.getRootGroupDetail();
    assertNotNull(rootDetail.getChildren().size());
    
    AccountGroup root = rootDetail.getChildren().get(0);
    
    AccountGroup group = new AccountGroup();
    group.setLabel("Nhóm đối tác");
    group.setName("partnertype");
    group.setOwner("hkt");
    group.setParent(root);
    group.setDescription("Nhóm đối tác trong group hkt");
    
    if (client.getGroupByPath(group.getPath()) == null) {
      group = client.saveGroup(group);
    } else {
      group = client.getGroupByPath(group.getPath());
    }
    
    assertNotNull(group);
    
    AccountGroup vip = new AccountGroup();
    vip.setLabel("Vip");
    vip.setName("vip");
    vip.setOwner("hkt");
    vip.setParent(group);
    vip.setDescription("Nhóm đối tác vip");
    if (client.getGroupByPath(vip.getPath()) == null) {
      vip = client.saveGroup(vip);
    } else {
      vip = client.getGroupByPath(vip.getPath());
    }
    
    AccountGroup khachquen = new AccountGroup();
    khachquen.setLabel("Khách quen");
    khachquen.setName("khachquen");
    khachquen.setOwner("hkt");
    khachquen.setParent(group);
    khachquen.setDescription("Nhóm đối tác Khách quen");
    if (client.getGroupByPath(khachquen.getPath()) == null) {
      khachquen = client.saveGroup(khachquen);
    } else {
      khachquen = client.getGroupByPath(khachquen.getPath());
    }
    
    assertEquals("hkt/partnertype/vip", vip.getPath());
    assertEquals("hkt/partnertype/khachquen", khachquen.getPath());
    
    AccountGroupDetail groupDetail = client.getGroupDetailByPath(root.getPath() + "/partnertype");
    assertEquals(2, groupDetail.getChildren().size());
    client.deleteGroup(khachquen);
    groupDetail = client.getGroupDetailByPath(root.getPath() + "/partnertype");
    assertEquals(1, groupDetail.getChildren().size());
  }
}
