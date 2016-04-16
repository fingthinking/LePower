package com.lesport.model;

import java.util.Date;
//import java.util.List;
/**
 */
public class UserInfo2 {
	private String userId;
	private String userName;
	private String nickName;
	private String imgURL; // 头像图片地址
	private String sex;
	private String age; // 年龄
	private String province;
	private String runDistance; // 跑步总距离
	private String totalStep;		//总步数 = 跑步 + 走路
	private String flag="1";
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
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
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
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
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
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public UserInfo2(String userId, String userName, String nickName, String imgURL, String sex, String age,
			String province, String runDistance, String totalStep, String flag) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.nickName = nickName;
		this.imgURL = imgURL;
		this.sex = sex;
		this.age = age;
		this.province = province;
		this.runDistance = runDistance;
		this.totalStep = totalStep;
		this.flag = flag;
	}
	public UserInfo2() {
		super();
	}
	@Override
	public String toString() {
		return "UserInfo2 [userId=" + userId + ", userName=" + userName + ", nickName=" + nickName + ", imgURL="
				+ imgURL + ", sex=" + sex + ", age=" + age + ", province=" + province + ", runDistance=" + runDistance
				+ ", totalStep=" + totalStep + ", flag=" + flag + "]";
	}
	
	
}