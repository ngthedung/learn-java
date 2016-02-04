package com.hkt.client.rest.service;

import java.util.List;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hkt.client.rest.RESTClient;
import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.Account.Type;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountGroupDetail;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.core.rest.Request;
import com.hkt.module.core.rest.Response;

@Component
public class AccountServiceClient {

  @Autowired
  private RESTClient client;

  public Account login(String loginId, String password) throws Exception {
    Request req = create("login");
    req.addParam("loginId", loginId);
    req.addParam("password", password);
    Response res = client.POST(req);
    return res.getDataAs(Account.class);
  }
  
  public Account getByEmail(String email) throws Exception {
    Request req = create("getByEmail");
    req.addParam("email", email);
    Response res = client.POST(req);
    return res.getDataAs(Account.class);
  }

  public Account getAccountByLoginId(String loginId) throws Exception {
    Request req = create("getAccountByLoginId");
    req.addParam("loginId", loginId);
    Response res = client.POST(req);
    return res.getDataAs(Account.class);
  }
  public Account getAccountById(long accoutId) throws Exception {
    Request req = create("getAccountById");
    req.addParam("accoutId", accoutId);
    Response res = client.POST(req);
    return res.getDataAs(Account.class);
  }

  public boolean deleteGroup(AccountGroup group) throws Exception {
    Request req = create("deleteGroup");
    req.addParam("group", group);
    client.POST(req);
    return true;
  }
  
  public boolean deleteAccountByLoginId(String loginId) throws Exception {
    Request req = create("deleteAccountByLoginId");
    req.addParam("loginId", loginId);
    client.POST(req);
    return true;
  }

  public Account saveAccount(Account account) throws Exception {
    Request req = create("saveAccount");
    req.addParam("account", account);
    Response res = client.POST(req);
    return res.getDataAs(Account.class);
  }

  public List<Account> findAccountByType(Type type) throws Exception {
    Request req = create("findAccountByType");
    req.addParam("type", type);
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<Account>>() {
    });
  }

  public List<Account> findAccountByLoginId(String loginId) throws Exception {
    Request req = create("findAccountByLoginId");
    req.addParam("loginId", loginId);
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<Account>>() {
    });
  }

  public List<AccountMembership> findMembershipByGroupPath(String path) throws Exception {
    Request req = create("findMembershipByGroupPath");
    req.addParam("path", path);
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<AccountMembership>>() {
    });
  }

  public AccountGroupDetail getRootGroupDetail() throws Exception {
    Request req = create("getRootGroupDetail");
    Response res = client.POST(req);
    return res.getDataAs(AccountGroupDetail.class);
  }

  public AccountGroup getGroupByPath(String path) throws Exception {
    Request req = create("getGroupByPath");
    req.addParam("path", path);
    Response res = client.POST(req);
    return res.getDataAs(AccountGroup.class);
  }
  
  public AccountGroup getGroupByName(String name) throws Exception {
    Request req = create("getByName");
    req.addParam("name", name);
    Response res = client.POST(req);
    return res.getDataAs(AccountGroup.class);
  }

  public AccountGroup saveGroup(AccountGroup group) throws Exception {
    Request req = create("saveGroup");
    req.addParam("group", group);
    Response res = client.POST(req);
    return res.getDataAs(AccountGroup.class);
  }

  public List<AccountGroup> findByParentPath(String parentPath) throws Exception {
    Request req = create("findByParentPath");
    req.addParam("parentPath", parentPath);
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<AccountGroup>>() {
    });
  }
  
  public List<AccountGroup> findByName(String path)  throws Exception{
    Request req = create("findByName");
    req.addParam("path", path);
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<AccountGroup>>() {
    });
  }
  
  public List<AccountGroup> getAllChildrensByPath(String path) throws Exception{
    Request req = create("getAllChildrensByPath");
    req.addParam("path", path);
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<AccountGroup>>() {
    });
  }

  public List<AccountGroup> getChildrensByPath(String path) throws Exception {
    Request req = create("getChildrensByPath");
    req.addParam("path", path);
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<AccountGroup>>() {
    });
  }

  public AccountGroupDetail getGroupDetailByPath(String path) throws Exception {
    Request req = create("getGroupDetailByPath");
    req.addParam("path", path);
    Response res = client.POST(req);
    return res.getDataAs(AccountGroupDetail.class);
  }

  public List<AccountMembership> findMembershipByAccountLoginId(String loginId) throws Exception {
    Request req = create("findMembershipByAccountLoginId");
    req.addParam("loginId", loginId);
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<AccountMembership>>() {
    });
  }

  public AccountMembership saveAccountMembership(AccountMembership accountMembership) throws Exception {
    Request req = create("saveAccountMembership");
    req.addParam("accountMembership", accountMembership);
    Response res = client.POST(req);
    return res.getDataAs(AccountMembership.class);
  }

  public void deleteTest(String test) throws Exception {
    Request req = create("deleteTest");
    req.addParam("test", test);
    client.POST(req);
  }
  
  public void deleteMembershipByLoginIdAndGroupPath(String loginId, String groupPath) throws Exception{
    Request req = create("deleteMembershipByLoginIdAndGroupPath");
    req.addParam("loginId", loginId);
    req.addParam("groupPath", groupPath);
    client.POST(req);
  }

  Request create(String method) {
    return new Request("account", "AccountService", method);
  }

  public AccountMembership getMembershipByAccountAndGroup(String loginId, String path) throws Exception {
    Request req = create("getMembershipByAccountAndGroup");
    req.addParam("loginId", loginId);
    req.addParam("path", path);
    Response res = client.POST(req);
    return res.getDataAs(AccountMembership.class);
  }
}