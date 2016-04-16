package com.lesport.model;

/**
 * 
 * @author dell
 *
 */
public class UserPushup {
	private String pushupId;
	private String userId;
	private String pushupCount;         //俯卧撑次数
	private String calorie;				//消耗卡路里
	private String orderId;				//次数
	private String date;				//日期
	private String createdDate;			//创建日期
	private String deleteFlag;			//是否删除
	public String getPushupId() {
		return pushupId;
	}
	public void setPushupId(String pushupId) {
		this.pushupId = pushupId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPushupCount() {
		return pushupCount;
	}
	public void setPushupCount(String pushupCount) {
		this.pushupCount = pushupCount;
	}
	public String getCalorie() {
		return calorie;
	}
	public void setCalorie(String calorie) {
		this.calorie = calorie;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
		return "UserPushup [pushupId=" + pushupId + ", userId=" + userId + ", pushupCount=" + pushupCount + ", calorie="
				+ calorie + ", orderId=" + orderId + ", date=" + date + ", createdDate=" + createdDate + ", deleteFlag="
				+ deleteFlag + "]";
	}
	
	
}