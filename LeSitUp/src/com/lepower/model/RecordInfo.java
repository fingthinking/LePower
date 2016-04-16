package com.lepower.model;

public class RecordInfo {
	private String id;//主键,自增
	private String userId;//用户Id
	private String startTime;//起始时间
	private String endTime;//结束时间
	private int situpCount;//每次的个数
	private int calorie;//消耗的卡路里
	private String date;//当天的日期
	
	//gouzao
	public RecordInfo(String userId, String startTime, String endTime, int situpCount, int calorie, String date) {
//		this.id = id;
		this.userId = userId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.situpCount = situpCount;
		this.calorie = calorie;
		this.date = date;
	}
	
	public RecordInfo(String id,String userId, String startTime, String endTime, int situpCount, int calorie, String date) {
		this.id = id;
		this.userId = userId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.situpCount = situpCount;
		this.calorie = calorie;
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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





	public int getsitupCount() {
		return situpCount;
	}





	public void setSitupCount(int situpCount) {
		this.situpCount = situpCount;
	}





	public int getCalorie() {
		return calorie;
	}





	public void setCalorie(int calorie) {
		this.calorie = calorie;
	}

	public String getDate() {
		return date;
	}


	/**
	 * 
	 * @param date
	 */
	public void setDate(String date) {
		this.date = date;
	}


	@Override
	public String toString() {
		return "RecordInfo [id=" + id + ", userId=" + userId + ", startTime="
				+ startTime + ", endTime=" + endTime + ", situpCount=" + situpCount
				+ ", calorie=" + calorie + ", date=" + date + "]";
	}
	
	
	

}
