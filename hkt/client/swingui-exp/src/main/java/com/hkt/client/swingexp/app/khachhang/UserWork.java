package com.hkt.client.swingexp.app.khachhang;

public class UserWork {
  public static final String ORGANIZATION = "organization";
  public static final String FROM = "from";
  public static final String TO = "to";
  public static final String POSITION = "position";
  public static final String DESCRIPTION = "description";
  
  private String organization;
  private String from;
  private String to;
  private String position;
  private String description;
  
  public UserWork(){
    
  }
  
  public String getOrganization() { return organization; }
  public void setOrganization(String organization) { this.organization = organization; }
  public String getFrom() { return from; }
  public void setFrom(String from) { this.from = from; }
  public String getTo() { return to; }
  public void setTo(String to) { this.to = to; }
  public String getPosition() { return position; }
  public void setPosition(String position) { this.position = position; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
}
