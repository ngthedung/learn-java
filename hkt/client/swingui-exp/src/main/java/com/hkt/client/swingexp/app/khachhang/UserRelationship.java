package com.hkt.client.swingexp.app.khachhang;

import com.hkt.module.account.entity.Account;

public class UserRelationship {
  public static final String RELATION = "relation";
  public static final String GENDER = "gender";
  public static final String FIRST_NAME = "code";
  public static final String LAST_NAME = "lastName";
  public static final String BIRTH_DAY = "birthday";
  public static final String OCCUPATION = "occupation";
//  public static final Account ACCOUNT = "account";
  
  private String relation;
  private String gender;
  private String firstName;
  private String lastName;
  private String birthday;
  private String occupation;
  private Account account;
  
  public String getRelation() { return relation; }
  public void setRelation(String relation) { this.relation = relation; }
  public String getGender() { return gender; }
  public void setGender(String gender) { this.gender = gender; }
  public String getFirstName() { return firstName; }
  public void setFirstName(String firstName) { this.firstName = firstName; }
  public String getLastName() { return lastName; }
  public void setLastName(String lastName) { this.lastName = lastName; }
  public String getBirthday() { return birthday; }
  public void setBirthday(String birthday) { this.birthday = birthday; }
  public String getOccupation() { return occupation; }
  public void setOccupation(String occupation) { this.occupation = occupation; }
public Account getAccount() {
	return account;
}
public void setAccount(Account account) {
	this.account = account;
}
  
}
