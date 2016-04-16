package com.lesport.model;

public class SportTotal {
	private String userId;
	private String userName;
	private String type; 			// 运动类型：run,walk,bike,pushup,jump,situp
	private String sportCount; 		// 运动次数
	private String sportDis; 		// 运动距离(仅type取值为run,walk,bike时有数据)
	private String sportSteps; 		// 运动步数(仅type取值为walk时有数据)
	private String sportTime; 		// 运动时间(仅type取值为run,walk时有数据)
	private String sportNum; 		// 运动个数(仅type取值为pushup,jump,situp时有数据)
	private String sportCal; 		// 运动消耗卡路里
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getSportNum() {
		return sportNum;
	}
	public void setSportNum(String sportNum) {
		this.sportNum = sportNum;
	}
	public String getSportCal() {
		return sportCal;
	}
	public void setSportCal(String sportCal) {
		this.sportCal = sportCal;
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
	@Override
	public String toString() {
		return "SportTotal [userId=" + userId + ", userName=" + userName + ", type=" + type + ", sportCount="
				+ sportCount + ", sportDis=" + sportDis + ", sportSteps=" + sportSteps + ", sportTime=" + sportTime
				+ ", sportNum=" + sportNum + ", sportCal=" + sportCal + "]";
	}
}
