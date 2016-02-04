package com.hkt.client.swingexp.app.license;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class KeyActivateLicense implements Serializable{
  private String key;
  private String license;
  private List<String> moduleCodes;
  private Date startDate;
  private Date endDate;
  private boolean admin;
  private boolean server;
  
  public boolean isAdmin() {
    return admin;
  }

  public void setAdmin(boolean admin) {
    this.admin = admin;
  }

  public boolean isServer() {
    return server;
  }

  public void setServer(boolean server) {
    this.server = server;
  }

  public KeyActivateLicense(){
    
  }
  
  public String getKey() {
    return key;
  }
  public void setKey(String key) {
    this.key = key;
  }
  public String getLicense() {
    return license;
  }
  public void setLicense(String license) {
    this.license = license;
  }

  

  public List<String> getModuleCodes() {
    return moduleCodes;
  }

  public void setModuleCodes(List<String> moduleCodes) {
    this.moduleCodes = moduleCodes;
  }

  public Date getStartDate() {
    return startDate;
  }
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }
  public Date getEndDate() {
    return endDate;
  }
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
  
  
}
