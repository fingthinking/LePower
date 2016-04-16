package com.lesport.model;

/**
 * 
 * @author dell
 *
 */
public class UserSitup {
	private String situpId;
	private String userId;
	private String situpCount;         //仰卧起坐次数
	private String calorie;				//消耗卡路里
	private String startTime;			//开始时间
	private String endTime;			//结束时间
	private String date;				//日期
	private String createdDate;			//创建日期
	private String deleteFlag;			//是否删除
	public String getSitupId() {
		return situpId;
	}
	public void setSitupId(String situpId) {
		this.situpId = situpId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSitupCount() {
		return situpCount;
	}
	public void setSitupCount(String situpCount) {
		this.situpCount = situpCount;
	}
	public String getCalorie() {
		return calorie;
	}
	public void setCalorie(String calorie) {
		this.calorie = calorie;
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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
		return "UserSitup [situpId=" + situpId + ", userId=" + userId + ", situpCount=" + situpCount + ", calorie="
				+ calorie + ", startTime=" + startTime + ", endTime=" + endTime + ", date=" + date + ", createdDate="
				+ createdDate + ", deleteFlag=" + deleteFlag + "]";
	}
	
	
}