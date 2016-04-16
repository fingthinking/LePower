package com.lesport.model;

import java.sql.Timestamp;
import java.util.List;;
/**
 * @文件名 UserRun
 * @author 斌
 * @version V.1
 * @日期 2016.3.5
 *
 */
public class UserRun {
	private String runId;
	private String userId;
	private String second;				//总秒数
//	private String totalStep;			//总步数
	private String distance;			//总距离
	private String calorie;				//消耗卡路里
	private String currentSpeed;		//当前速度
	private String averageSpeed;		//平均速度
//	private String province;			//省份
//	private String city;				//城市
	private String date;				//日期
//	private String  startTime;			//开始时间
//	private String  endTime;				//结束时间
//	private String runType;				//运动类型
//	private String correctMeter;		//校对米数
	private String coordinate;			//坐标
// 	private String kiloNodeTime;		//每公里数据记录
//	private String grade;				//跑步评价
	private String createdDate;			//创建日期
	private String deleteFlag;			//是否删除
	public String getRunId() {
		return runId;
	}
	public void setRunId(String runId) {
		this.runId = runId;
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
//	public String getTotalStep() {
//		return totalStep;
//	}
//	public void setTotalStep(String totalStep) {
//		this.totalStep = totalStep;
//	}
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
//	public String getProvince() {
//		return province;
//	}
//	public void setProvince(String province) {
//		this.province = province;
//	}
//	public String getCity() {
//		return city;
//	}
//	public void setCity(String city) {
//		this.city = city;
//	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
//	public String getStartTime() {
//		return startTime;
//	}
//	public void setStartTime(String startTime) {
//		this.startTime = startTime;
//	}
//	public String getEndTime() {
//		return endTime;
//	}
//	public void setEndTime(String endTime) {
//		this.endTime = endTime;
//	}
//	public String getRunType() {
//		return runType;
//	}
//	public void setRunType(String runType) {
//		this.runType = runType;
//	}
//	public String getCorrectMeter() {
//		return correctMeter;
//	}
//	public void setCorrectMeter(String correctMeter) {
//		this.correctMeter = correctMeter;
//	}
	public String getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}
//	public String getKiloNodeTime() {
//		return kiloNodeTime;
//	}
//	public void setKiloNodeTime(String kiloNodeTime) {
//		this.kiloNodeTime = kiloNodeTime;
//	}
//	public String getGrade() {
//		return grade;
//	}
//	public void setGrade(String grade) {
//		this.grade = grade;
//	}
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
//	@Override
//	public String toString() {
//		return "UserRun [runId=" + runId + ", userId=" + userId + ", second=" + second + ", totalStep=" + totalStep
//				+ ", distance=" + distance + ", calorie=" + calorie + ", currentSpeed=" + currentSpeed
//				+ ", averageSpeed=" + averageSpeed + ", province=" + province + ", city=" + city + ", date=" + date
//				+ ", startTime=" + startTime + ", endTime=" + endTime + ", runType=" + runType + ", correctMeter="
//				+ correctMeter + ", coordinate=" + coordinate + ", kiloNodeTime=" + kiloNodeTime + ", grade=" + grade
//				+ ", createdDate=" + createdDate + ", deleteFlag=" + deleteFlag + "]";
//	}
	@Override
	public String toString() {
		return "UserRun [runId=" + runId + ", userId=" + userId + ", second=" + second
				+ ", distance=" + distance + ", calorie=" + calorie + ", currentSpeed=" + currentSpeed
				+ ", averageSpeed=" + averageSpeed + ", date=" + date + ", coordinate=" + coordinate + ", createdDate="
				+ createdDate + ", deleteFlag=" + deleteFlag + "]";
	}

	
	
}