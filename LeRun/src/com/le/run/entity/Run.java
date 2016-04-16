package com.le.run.entity;

import java.util.ArrayList;

public class Run {

	private int runId;
	private String userId;
	private double totalDistance;  // �ܲ��ܾ���
	private double currentSpeed;
	private double averageSpeed;
	private double calorie; 
	private double time; // �ܲ�ʱ��
	private String date; // ���������
	ArrayList<MyLatLng> myLatLng;

	
	public Run(int runId, String userId, double totalDistance,
			double currentSpeed, double averageSpeed, double calorie,
			double time, String date) {
		super();
		
		this.runId = runId;
		this.userId = userId;
		this.totalDistance = totalDistance;
		this.currentSpeed = currentSpeed;
		this.averageSpeed = averageSpeed;
		this.calorie = calorie;
		this.time = time;
		this.date = date;
	}

	public Run(double totalDistance,double currentSpeed, double averageSpeed, double calorie,double time, 
			ArrayList<MyLatLng> myLatLng) {
		super();
		this.totalDistance = totalDistance;
		this.currentSpeed = currentSpeed;
		this.averageSpeed = averageSpeed;
		this.calorie = calorie;
		this.time = time;
		this.myLatLng = myLatLng;
	}

	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public int getRunId() {
		return runId;
	}

	public void setRunId(int runId) {
		this.runId = runId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public double getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(double totalDistance) {
		this.totalDistance = totalDistance;
	}

	public double getCurrentSpeed() {
		return currentSpeed;
	}

	public void setCurrentSpeed(double currentSpeed) {
		this.currentSpeed = currentSpeed;
	}

	public double getAverageSpeed() {
		return averageSpeed;
	}

	public void setAverageSpeed(double averageSpeed) {
		this.averageSpeed = averageSpeed;
	}

	public double getCalorie() {
		return calorie;
	}

	public void setCalorie(double calorie) {
		this.calorie = calorie;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}
	
	
	
}
