package com.lesport.model;

/**
 * @文件名 UserRun
 * @author 斌
 * @version V.1
 * @日期 2016.3.5
 *
 */
public class UserWalk {
	private String walkId;
	private String userId;
	private String date;				//日期	格式：yyyy-MM-dd
	private String startTime;			//开始时间	格式HH:mm
	private String endTime;			//结束时间	格式HH:mm
	private String distance;				//距离	单位：米
	private String steps;				//运动步数	
	private String second;			//运动时长	单位：秒
	private String speed;			//步频	单位：步/分
	private String calorie;				//消耗卡路里	
	private String isToday;				//今日记录	用于记录今日的总步数
	private String createdDate;			//创建日期
	private String deleteFlag;			//是否删除
	
	//加
	private String stepId;
	
	public String getStepId() {
		return stepId;
	}
	public void setStepId(String stepId) {
		this.stepId = stepId;
	}
	public String getWalkId() {
		return walkId;
	}
	public void setWalkId(String walkId) {
		this.walkId = walkId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getSteps() {
		return steps;
	}
	public void setSteps(String steps) {
		this.steps = steps;
	}
	public String getSecond() {
		return second;
	}
	public void setSecond(String second) {
		this.second = second;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getCalorie() {
		return calorie;
	}
	public void setCalorie(String calorie) {
		this.calorie = calorie;
	}
	public String getIsToday() {
		return isToday;
	}
	public void setIsToday(String isToday) {
		this.isToday = isToday;
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
		return "UserWalk [walkId=" + walkId + ", userId=" + userId + ", date=" + date + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", distance=" + distance + ", steps=" + steps + ", second=" + second
				+ ", speed=" + speed + ", calorie=" + calorie + ", isToday=" + isToday + ", createdDate=" + createdDate
				+ ", deleteFlag=" + deleteFlag + ", stepId=" + stepId + "]";
	}
	
	
	



}