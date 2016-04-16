package com.lebicycling.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Bicycling implements Parcelable{

	private int bicyclingId;
	private String userId;
	private double totalDistance;  // �ܲ��ܾ���
	private double currentSpeed;
	private double averageSpeed;
	private double calorie; 
	private double time; // �ܲ�ʱ��
	private String date; // �� �������
	private List<MyLatLng> mylatList;// ���汾����¼�ľ�γ����Ϣ����


	public Bicycling(int bicyclingId, String userId, double totalDistance,
			double currentSpeed, double averageSpeed, double calorie,
			double time, String date) {
		super();
		this.bicyclingId = bicyclingId;
		this.userId = userId;
		this.totalDistance = totalDistance;
		this.currentSpeed = currentSpeed;
		this.averageSpeed = averageSpeed;
		this.calorie = calorie;
		this.time = time;
		this.date = date;
	}



	public Bicycling(double totalDistance, double calorie, double time,
			ArrayList<MyLatLng> mylatList) {
		super();
		this.totalDistance = totalDistance;
		this.calorie = calorie;
		this.time = time;
		this.mylatList = mylatList;
	}



	public List<MyLatLng> getMylatList() {
		return mylatList;
	}



	public void setMylatList(ArrayList<MyLatLng> mylatList) {
		this.mylatList = mylatList;
	}



	public int getBicyclingId() {
		return bicyclingId;
	}


	public void setBicyclingId(int bicyclingId) {
		this.bicyclingId = bicyclingId;
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


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}



	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public void writeToParcel(Parcel out, int flags) {
		// TODO Auto-generated method stub
		out.writeDouble(totalDistance);
		out.writeDouble(calorie);
		out.writeDouble(time);
	    out.writeParcelableArray((mylatList.toArray(new MyLatLng[mylatList.size()])), flags);

		


	}

	private Bicycling(Parcel in){
		totalDistance = in.readDouble();
		calorie = in.readDouble();
		time = in.readDouble();
		Parcelable[] pars = in.readParcelableArray(MyLatLng.class.getClassLoader());
		mylatList = Arrays.asList(Arrays.asList(pars).toArray(new MyLatLng[pars.length]));
		
		

	}
    
	public static final Parcelable.Creator<Bicycling> CREATOR = new Parcelable.Creator<Bicycling>() {
		public Bicycling createFromParcel(Parcel in){
			return new Bicycling(in);
		}
		public Bicycling[] newArray(int size){
			return new Bicycling[size];
		}
	};
	







}
