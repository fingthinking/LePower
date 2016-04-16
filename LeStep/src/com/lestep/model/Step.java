package com.lestep.model;

import java.io.Serializable;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import com.google.gson.annotations.SerializedName;
import com.lestep.utils.BeanToCls.BeanEntry;


@Table(name = "le_step")
public class Step implements Serializable {
	@BeanEntry(ignore=true)
	private static final long serialVersionUID = -463148985026400051L;
	// 使用系统时间来存储
	@Column(isId = true, name = "stepId", autoGen=false)
	private long stepId; // 主键约束
	@SerializedName("date")
	@Column(name = "day")
	private String day; // 日期 格式： yyyy-MM-dd
	
	@Column(name = "startTime")
	private String startTime; // 开始时间 格式 : HH:mm
	
	@Column(name = "endTime")
	private String endTime; // 结束时间 格式： HH:mm

	@Column(name = "distance")
	private float distance; // 跑步距离 单位：m

	@Column(name = "caloria")
	private float caloria; // 消耗 单位：千卡
	@SerializedName("second")
	@Column(name = "stepTime")
	private long stepTime; // 活动时长 单位： ms

	@Column(name = "steps")
	private long steps; // 运动步数 单位： 步数
	
	@Column(name = "speed")
	private long speed; // 步频 单位： 步/分
	@Column(name="isToday")
	private boolean isToday;	// 今日记录，用于记录今日的总步数
	@Column(name="hasSynch")
	private boolean hasSynch;

	public boolean hasSynch() {
		return hasSynch;
	}

	public void setSynch(boolean hasSynch) {
		this.hasSynch = hasSynch;
	}

	public boolean isToday() {
		return isToday;
	}

	public void setToday(boolean isToday) {
		this.isToday = isToday;
	}

	public long getStepId() {
		return stepId;
	}

	public void setStepId(long stepId) {
		this.stepId = stepId;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
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

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public float getCaloria() {
		return caloria;
	}

	public void setCaloria(float caloria) {
		this.caloria = caloria;
	}

	public long getStepTime() {
		return stepTime;
	}

	public void setStepTime(long stepTime) {
		this.stepTime = stepTime;
	}

	public long getSteps() {
		return steps;
	}

	public void setSteps(long steps) {
		this.steps = steps;
	}

	public long getSpeed() {
		return speed;
	}

	public void setSpeed(long speed) {
		this.speed = speed;
	}

}
