package com.lesport.model;

/**
 * 
 * @author dell
 *
 */
public class UserBicycle {
	private String bicycleId;
	private String userId;
	private String second;				//总秒数
	private String distance;			//总距离
	private String calorie;				//消耗卡路里
	private String currentSpeed;		//当前速度
	private String averageSpeed;		//平均速度
	private String date;				//日期
	private String coordinate;			//坐标
	private String createdDate;			//创建日期
	private String deleteFlag;			//是否删除
	public String getBicycleId() {
		return bicycleId;
	}
	public void setBicycleId(String bicycleId) {
		this.bicycleId = bicycleId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSecond() {
		return second;
	}
	public void setSecond(String second) {
		this.second = second;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getCalorie() {
		return calorie;
	}
	public void setCalorie(String calorie) {
		this.calorie = calorie;
	}
	public String getCurrentSpeed() {
		return currentSpeed;
	}
	public void setCurrentSpeed(String currentSpeed) {
		this.currentSpeed = currentSpeed;
	}
	public String getAverageSpeed() {
		return averageSpeed;
	}
	public void setAverageSpeed(String averageSpeed) {
		this.averageSpeed = averageSpeed;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
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
		return "UserBicycle [bicycleId=" + bicycleId + ", userId=" + userId + ", second=" + second + ", distance="
				+ distance + ", calorie=" + calorie + ", currentSpeed=" + currentSpeed + ", averageSpeed="
				+ averageSpeed + ", date=" + date + ", coordinate=" + coordinate + ", createdDate=" + createdDate
				+ ", deleteFlag=" + deleteFlag + "]";
	}

	
}