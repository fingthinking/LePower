package com.lesport.model;

/**
 * 
 * @author dell
 *
 */
public class SportData {
	private String date;
	private double runCal;
	private double walkCal;
	private double bikeCal;
	private double runDis;
	private double walkDis;
	private double bikeDis;
	private double runTime;
	private double walkTime;
	private double bikeTime;
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getRunCal() {
		return runCal;
	}
	public void setRunCal(double runCal) {
		this.runCal = runCal;
	}
	public double getWalkCal() {
		return walkCal;
	}
	public void setWalkCal(double walkCal) {
		this.walkCal = walkCal;
	}
	public double getBikeCal() {
		return bikeCal;
	}
	public void setBikeCal(double bikeCal) {
		this.bikeCal = bikeCal;
	}
	public double getRunDis() {
		return runDis;
	}
	public void setRunDis(double runDis) {
		this.runDis = runDis;
	}
	public double getWalkDis() {
		return walkDis;
	}
	public void setWalkDis(double walkDis) {
		this.walkDis = walkDis;
	}
	public double getBikeDis() {
		return bikeDis;
	}
	public void setBikeDis(double bikeDis) {
		this.bikeDis = bikeDis;
	}
	public double getRunTime() {
		return runTime;
	}
	public void setRunTime(double runTime) {
		this.runTime = runTime;
	}
	public double getWalkTime() {
		return walkTime;
	}
	public void setWalkTime(double walkTime) {
		this.walkTime = walkTime;
	}
	public double getBikeTime() {
		return bikeTime;
	}
	public void setBikeTime(double bikeTime) {
		this.bikeTime = bikeTime;
	}
	@Override
	public String toString() {
		return "SportData [date=" + date + ", runCal=" + runCal + ", walkCal=" + walkCal + ", bikeCal=" + bikeCal
				+ ", runDis=" + runDis + ", walkDis=" + walkDis + ", bikeDis=" + bikeDis + ", runTime=" + runTime
				+ ", walkTime=" + walkTime + ", bikeTime=" + bikeTime + "]";
	}
	
}