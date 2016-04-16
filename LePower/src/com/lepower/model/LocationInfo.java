package com.lepower.model;

import java.io.Serializable;

public class LocationInfo implements Serializable{
	
	/**
	 * 纬度
	 */
	private String latitude; 
	
	/**
	 * 经度
	 */
	private String longitude;

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String d) {
		this.latitude = d;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public LocationInfo(String latitude, String longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public LocationInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "LocationInfo [latitude=" + latitude + ", longitude="
				+ longitude + "]";
	}
	
	

}
