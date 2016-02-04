package com.hkt.client.swingexp.app.khachhang;

public class BusinessRegistration {
  public static final String FULL_nameVN = "fullName.vn";
  public static final String FULL_nameEN = "fullName.en";
  public static final String REGISTRATION_CODE = "registrationCode";
  public static final String TAX_REGISTRATION_CODE = "taxRegistrationCode";
  public static final String REPRESENTATIVE = "representative";
  public static final String CHAPTER_CAPITAL = "charterCapital";
  public static final String LEGAL_CAPITAL = "legalCapital";
  public static final String DOMAIN = "domain";
  public static final String DESCRIPTION = "description";
  
  private String label;
  private String fullNameVN;
  private String fullNameEN;
  private String registrationCode;
  private String taxRegistrationCode;
  private String representative;
  private String charterCapital;
  private String legalCapital;
  private String domain;
  private String description;
  
  public String getLabel() { return label; }
  public void setLabel(String label) { this.label = label; }
  public String getFullNameVN() { return fullNameVN; }
  public void setFullNameVN(String fullNameVN) { this.fullNameVN = fullNameVN; } 
  public String getFullNameEN() { return fullNameEN; }
  public void setFullNameEN(String fullNameEN) { this.fullNameEN = fullNameEN; }
  public String getRegistrationCode() { return registrationCode; }
  public void setRegistrationCode(String registrationCode) { this.registrationCode = registrationCode; }
  public String getTaxRegistrationCode() { return taxRegistrationCode; }
  public void setTaxRegistrationCode(String taxRegistrationCode) { this.taxRegistrationCode = taxRegistrationCode; }
  public String getRepresentative() { return representative; }
  public void setRepresentative(String representative) { this.representative = representative; }
  public String getCharterCapital() { return charterCapital; }
  public void setCharterCapital(String charterCapital) { this.charterCapital = charterCapital; }
  public String getLegalCapital() { return legalCapital; }
  public void setLegalCapital(String legalCapital) { this.legalCapital = legalCapital; }
  public String getDomain() { return domain;}
  public void setDomain(String domain) { this.domain = domain; }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  
}
