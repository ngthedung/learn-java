package com.hkt.client.swingexp.app.khachhang;

public class UserEducation {
  public static final String SCHOOL_INSTITUTE = "schoolOrInstitute";
  public static final String FROM             = "from";
  public static final String TO               = "to";
  public static final String MAJOF            = "major";
  public static final String CERTIFICATE      = "certificate";
  public static final String LANGUAGE         = "language";
  public static final String DESCRIPTION         = "description";

  private String             schoolOrInstitute;
  private String             from;
  private String             to;
  private String             major;
  private String             certificate;
  private String             language;
  private String             description;

  public UserEducation() {

  }

  public String getSchoolOrInstitute() {
    return schoolOrInstitute;
  }

  public void setSchoolOrInstitute(String schoolOrInstitute) {
    this.schoolOrInstitute = schoolOrInstitute;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public String getMajor() {
    return major;
  }

  public void setMajor(String major) {
    this.major = major;
  }

  public String getCertificate() {
    return certificate;
  }

  public void setCertificate(String certificate) {
    this.certificate = certificate;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }  

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return schoolOrInstitute;
  }

}
