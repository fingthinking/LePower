package com.lesport.model;

//import java.util.List;
/**
 * 
 * @author 曹汝帅
 * @version V.1 
 * @创建时间: 2016-03-05
 * @描述：用户信息实体类
 *
 */
public class UserInfo {
	private String userId;
	private String userName;
	private String userPwd;
	private String nickName;
	private String imgURL; // 头像图片地址
	private String sex;
	private String phoneNum;
	private String email;
	private String height;
	private String weight;
	private String birthday; // 出生日期
	private String sportType; // 运动标签
	private String level; // 等级
	private String weiboNum;
	private String qqNum;
	private String leNum; // 乐动力号
	private String circleAuth; // 乐友圈权限
	private String province;
	private String city;
	private String lng; // 经度
	private String lat; // 纬度
	private String runDistance; // 跑步总距离
	private String runTime; // 跑步总时间
	private String runCount;		//跑步总次数
	private String calorieSum;		//总消耗卡路里
	private String totalStep;		//总步数 = 跑步 + 走路
	private String lastLogTime;		//最后登录时间
	private String createdDate;			//创建日期
	private String deleteFlag;		//是否删除，默认为N
	public UserInfo(String userId, String userName, String userPwd, String nickName, String imgURL, String sex,
			String phoneNum, String email, String height, String weight, String birthday, String sportType,
			String level, String weiboNum, String qqNum, String leNum, String circleAuth, String province, String city,
			String lng, String lat, String runDistance, String runTime, String runCount, String calorieSum,
			String totalStep, String lastLogTime, String createdDate, String deleteFlag) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPwd = userPwd;
		this.nickName = nickName;
		this.imgURL = imgURL;
		this.sex = sex;
		this.phoneNum = phoneNum;
		this.email = email;
		this.height = height;
		this.weight = weight;
		this.birthday = birthday;
		this.sportType = sportType;
		this.level = level;
		this.weiboNum = weiboNum;
		this.qqNum = qqNum;
		this.leNum = leNum;
		this.circleAuth = circleAuth;
		this.province = province;
		this.city = city;
		this.lng = lng;
		this.lat = lat;
		this.runDistance = runDistance;
		this.runTime = runTime;
		this.runCount = runCount;
		this.calorieSum = calorieSum;
		this.totalStep = totalStep;
		this.lastLogTime = lastLogTime;
		this.createdDate = createdDate;
		this.deleteFlag = deleteFlag;
	}
	public UserInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
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
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
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
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getSportType() {
		return sportType;
	}
	public void setSportType(String sportType) {
		this.sportType = sportType;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getWeiboNum() {
		return weiboNum;
	}
	public void setWeiboNum(String weiboNum) {
		this.weiboNum = weiboNum;
	}
	public String getQqNum() {
		return qqNum;
	}
	public void setQqNum(String qqNum) {
		this.qqNum = qqNum;
	}
	public String getLeNum() {
		return leNum;
	}
	public void setLeNum(String leNum) {
		this.leNum = leNum;
	}
	public String getCircleAuth() {
		return circleAuth;
	}
	public void setCircleAuth(String circleAuth) {
		this.circleAuth = circleAuth;
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
	public String getRunDistance() {
		return runDistance;
	}
	public void setRunDistance(String runDistance) {
		this.runDistance = runDistance;
	}
	public String getRunTime() {
		return runTime;
	}
	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}
	public String getRunCount() {
		return runCount;
	}
	public void setRunCount(String runCount) {
		this.runCount = runCount;
	}
	public String getCalorieSum() {
		return calorieSum;
	}
	public void setCalorieSum(String calorieSum) {
		this.calorieSum = calorieSum;
	}
	public String getTotalStep() {
		return totalStep;
	}
	public void setTotalStep(String totalStep) {
		this.totalStep = totalStep;
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
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	@Override
	public String toString() {
		return "UserInfo [userId=" + userId + ", nickName=" + nickName + ", sex=" + sex + ", phoneNum=" + phoneNum
				+ ", email=" + email + ", birthday=" + birthday + ", weiboNum=" + weiboNum + ", qqNum=" + qqNum
				+ ", province=" + province + ", city=" + city + "]";
	}
	
	
	
	
}