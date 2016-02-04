package com.hkt.client.swingexp.app.khachhang;

public class BasicInformation {
	public static final String	FIRST_NAME			= "firstName";
	public static final String	LAST_NAME				= "lastName";
	public static final String	GENDER					= "gender";
	public static final String	BIRTHDAY				= "birthday";
	public static final String	AVATAR					= "image";
	public static final String	WEIGHT					= "weight";
	public static final String	HEIGHT					= "height";
	public static final String	PERSONAL_ID			= "personalId";
	public static final String	MARITAL_STATUS	= "maritalStatus";
	public static final String	HOBBY						= "hobby";
	public static final String NICK_NAME = 			"nickName";

	private String							firstName;
	private String							lastName;
	private String							gender					= "";
	private String							birthday;
	private String							avatar;
	private String							weight					= "";
	private String							height					= "";
	private String							personalId;
	private String							maritalStatus		= "";
	private String							hobby;
	private String							nickName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getPersonalId() {
		return personalId;
	}

	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
}
