package com.hkt.client.swingexp.app.core;


public class ManagerAuthenticate {
  private static ManagerAuthenticate authenticate;
  private String loginId="";
  private String organizationLoginId="";
  private String loginIdAdmin="";


  public static ManagerAuthenticate getInstance() {
    if (authenticate == null) {
      authenticate = new ManagerAuthenticate();
    }
    return authenticate;
  }

  private ManagerAuthenticate() {

  }
  
  

  public void setLoginIdAdmin(String loginIdAdmin) {
    this.loginIdAdmin = loginIdAdmin;
  }

  public String getLoginIdAdmin() {
    return loginIdAdmin;
  }

  public String getLoginId() {
    return loginId;
  }

  public void setLoginId(String loginId) {
    this.loginId = loginId;
  }

  public String getOrganizationLoginId() {
    return organizationLoginId;
  }

  public void setOrganizationLoginId(String organizationLoginId) {
    this.organizationLoginId = organizationLoginId;
  }

}
