package com.le.run.entity;

public class MyLatLng {

	private int latLngId;
	private int runId;
	private double latitude;
	private double longitude;

	
	
	public MyLatLng(int runId, double latitude, double longitude) {
		super();
		this.runId = runId;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public MyLatLng(double latitude, double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public int getRunId() {
		return runId;
	}

    
	public void setRunId(int runId) {
		this.runId = runId;
	}


	public int getLatLngId() {
		return latLngId;
	}
	public void setLatLngId(int latLngId) {
		this.latLngId = latLngId;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	
	
}
