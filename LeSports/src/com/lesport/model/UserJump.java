package com.lesport.model;

/**
 * 
 * @author dell
 *
 */
public class UserJump {
	private String jumpId;
	private String userId;
	private String jumpCount;         //跳绳次数
	private String calorie;				//消耗卡路里
	private String orderId;				//次数
	private String date;				//日期
	private String createdDate;			//创建日期
	private String deleteFlag;			//是否删除
	public String getJumpId() {
		return jumpId;
	}
	public void setJumpId(String jumpId) {
		this.jumpId = jumpId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getJumpCount() {
		return jumpCount;
	}
	public void setJumpCount(String jumpCount) {
		this.jumpCount = jumpCount;
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
		return "UserJump [jumpId=" + jumpId + ", userId=" + userId + ", jumpCount=" + jumpCount + ", calorie=" + calorie
				+ ", orderId=" + orderId + ", date=" + date + ", createdDate=" + createdDate + ", deleteFlag="
				+ deleteFlag + "]";
	}
	
	
	
	
}