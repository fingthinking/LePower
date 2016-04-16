package com.lepower.model;

import java.io.Serializable;

public class User implements Serializable{
	
	private String userId;				// 用户Id
	private String nickName;			// 昵称
	private String phoneNum;			// 手机
	private String email;				// 邮箱
	private String province;		// 省份
	private String city;			// 城市
	private String birthday;		// 生日 yyyy-MM-dd
	private String sex;				// 性别
	private String weight;			// 体重 kg
	private String height;			// 身高 cm
	private String level;			// 等级
	private String leNum;			// 乐动力号
	private String qqNum;				// qq
	private String weiboNum;			// 微博
	private String imgURL;			// 头像
	private String lastLogTime;	// 最后一次登陆时间  yyyy-MM-dd HH:mm:ss
	private String createdDate; // 创建时间
	private String calorieSum;	// 总卡路里
	private String circleAuth;	// 乐友圈权限
	private String lng;			// 经度
	private String lat;			// 纬度
	
	public String getCalorieSum() {
		return calorieSum;
	}
	public void setCalorieSum(String calorieSum) {
		this.calorieSum = calorieSum;
	}
	public String getCircleAuth() {
		return circleAuth;
	}
	public void setCircleAuth(String circleAuth) {
		this.circleAuth = circleAuth;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
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
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getLeNum() {
		return leNum;
	}
	public void setLeNum(String leNum) {
		this.leNum = leNum;
	}
	public String getQqNum() {
		return qqNum;
	}
	public void setQqNum(String qqNum) {
		this.qqNum = qqNum;
	}
	public String getWeiboNum() {
		return weiboNum;
	}
	public void setWeiboNum(String weiboNum) {
		this.weiboNum = weiboNum;
	}
	public String getImgURL() {
		return imgURL;
	}
	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}
	public String getLastLogTime() {
		return lastLogTime;
	}
	public void setLastLogTime(String lastLogTime) {
		this.lastLogTime = lastLogTime;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", nickName=" + nickName
				+ ", phoneNum=" + phoneNum + ", email=" + email + ", province="
				+ province + ", city=" + city + ", birthday=" + birthday
				+ ", sex=" + sex + ", weight=" + weight + ", height=" + height
				+ ", level=" + level + ", leNum=" + leNum + ", qqNum=" + qqNum
				+ ", weiboNum=" + weiboNum + ", imgURL=" + imgURL
				+ ", lastLogTime=" + lastLogTime + ", createdDate="
				+ createdDate + ", calorieSum=" + calorieSum + ", circleAuth="
				+ circleAuth + ", lng=" + lng + ", lat=" + lat + "]";
	}
	
	
}
