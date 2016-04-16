package com.lepower.model;

public class SportRecord {
	private String userId;
	private String sportCal; //总卡路里
	private String sportCount; //总次数
	private String sportDis; //总距离
	private String sportNum; //总个数
	private String sportSteps; //总步数
	private String sportTime; //总时间（
	private String type; //类型
	
	
	public SportRecord(String userId, String sportCal, String sportCount,
			String sportDis, String sportNum, String sportSteps, String sportTime,
			String type) {
		super();
		this.userId = userId;
		this.sportCal = sportCal;
		this.sportCount = sportCount;
		this.sportDis = sportDis;
		this.sportNum = sportNum;
		this.sportSteps = sportSteps;
		this.sportTime = sportTime;
		this.type = type;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSportCal() {
		return sportCal;
	}
	public void setSportCal(String sportCal) {
		this.sportCal = sportCal;
	}
	public String getSportCount() {
		return sportCount;
	}
	public void setSportCount(String sportCount) {
		this.sportCount = sportCount;
	}
	public String getSportDis() {
		return sportDis;
	}
	public void setSportDis(String sportDis) {
		this.sportDis = sportDis;
	}
	public String getSportNum() {
		return sportNum;
	}
	public void setSportNum(String sportNum) {
		this.sportNum = sportNum;
	}
	public String getSportSteps() {
		return sportSteps;
	}
	public void setSportSteps(String sportSteps) {
		this.sportSteps = sportSteps;
	}
	public String getSportTime() {
		return sportTime;
	}
	public void setSportTime(String sportTime) {
		this.sportTime = sportTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
