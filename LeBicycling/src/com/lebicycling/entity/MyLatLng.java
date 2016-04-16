package com.lebicycling.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class MyLatLng implements Parcelable{

	private int latLngId;
	private int bicyclingId;
	private double latitude;
	private double longitude;

	
	
	public MyLatLng(int bicyclingId, double latitude, double longitude) {
		super();
		this.bicyclingId = bicyclingId;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	
	public MyLatLng(double latitude, double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}


	public int getbicyclingId() {
		return bicyclingId;
	}


	public void setbicyclingId(int bicyclingId) {
		this.bicyclingId = bicyclingId;
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


	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeDouble(latitude);
		out.writeDouble(longitude);
		
	}
	
	private MyLatLng(Parcel in){
		latitude = in.readDouble();
		longitude = in.readDouble();
	}
	
	public static final Parcelable.Creator<MyLatLng> CREATOR = new Parcelable.Creator<MyLatLng>() {
		
		public MyLatLng createFromParcel(Parcel in){
			return new MyLatLng(in);
		}
		
		public MyLatLng[] newArray(int size){
			return new MyLatLng[size];
		}
		
	};
	
	
	
	
	
	
	
	
	
	
	
	
}
