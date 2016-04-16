package com.lesport.model;

public class Friendsreturn {
	private String  userId;
	private String  userName;
	private String  imgURL;
	private String  sex;
	private String  birthday;
	private String  runDistance;
	private String  totalStep;
	private String  friendFlag;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getImgURL() {
		return imgURL;
	}
	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getRunDistance() {
		return runDistance;
	}
	public void setRunDistance(String runDistance) {
		this.runDistance = runDistance;
	}
	public String getTotalStep() {
		return totalStep;
	}
	public void setTotalStep(String totalStep) {
		this.totalStep = totalStep;
	}
	public String getFriendFlag() {
		return friendFlag;
	}
	public void setFriendFlag(String friendFlag) {
		this.friendFlag = friendFlag;
	}
	public Friendsreturn(String userId, String userName, String imgURL, String sex, String birthday, String runDistance,
			String totalStep, String friendFlag) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.imgURL = imgURL;
		this.sex = sex;
		this.birthday = birthday;
		this.runDistance = runDistance;
		this.totalStep = totalStep;
		this.friendFlag = friendFlag;
	}
	public Friendsreturn() {
		super();
		// TODO Auto-generated constructor stub
	}

}
