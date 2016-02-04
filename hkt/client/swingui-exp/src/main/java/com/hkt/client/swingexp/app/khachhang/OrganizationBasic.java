package com.hkt.client.swingexp.app.khachhang;

public class OrganizationBasic {
	public static final String	NAME							= "name";
	public static final String	FULLNAME					= "fullName";
	public static final String	ORGANIZATION_TYPE	= "organizationType";
	public static final String	MANAGER						= "manager";
	public static final String	SLOGAN						= "slogan";
	public static final String	LOGOURL						= "logoURL";
	public static final String	FOUNDED_DATE			= "foundedDate";
	public static final String	TERMINATED_DATE		= "terminatedDate";
	public static final String	REGISTRATION_CODE	= "registrationCode";
	public static final String	REPRESENTATIVE		= "representative";
	public static final String	DESCRIPTION				= "description";
	public static final String	NOTE							= "note";
	
	private String							name;
	private String							fullName;
	private String							organizationType;
	private String							manager;
	private String							slogan;
	private String							logoURL;
	private String							foundedDate;
	private String							terminatedDate;
	private String							registrationCode;
	private String							representative;
	private String							description;
	private String							note;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getOrganizationType() {
		return organizationType;
	}
	
	public void setOrganizationType(String organizationType) {
		this.organizationType = organizationType;
	}
	
	public String getManager() {
		return manager;
	}
	
	public void setManager(String manager) {
		this.manager = manager;
	}
	
	public String getSlogan() {
		return slogan;
	}
	
	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}
	
	public String getLogoURL() {
		return logoURL;
	}
	
	public void setLogoURL(String logoURL) {
		this.logoURL = logoURL;
	}
	
	public String getFoundedDate() {
		return foundedDate;
	}
	
	public void setFoundedDate(String foundedDate) {
		this.foundedDate = foundedDate;
	}
	
	public String getTerminatedDate() {
		return terminatedDate;
	}
	
	public void setTerminatedDate(String terminatedDate) {
		this.terminatedDate = terminatedDate;
	}
	
	public String getRegistrationCode() {
		return registrationCode;
	}
	
	public void setRegistrationCode(String registrationCode) {
		this.registrationCode = registrationCode;
	}
	
	public String getRepresentative() {
		return representative;
	}
	
	public void setRepresentative(String representative) {
		this.representative = representative;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
