package com.lebicycling.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class SItem implements Parcelable{

	String description;
	int bicyclingId;
	public SItem(String description, int bicyclingId) {
		super();
		this.description = description;
		this.bicyclingId = bicyclingId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getbicyclingId() {
		return bicyclingId;
	}
	public void setbicyclingId(int bicyclingId) {
		this.bicyclingId = bicyclingId;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(description);
		out.writeInt(bicyclingId);
		
	}
	
	private SItem(Parcel in){
		description = in.readString();
		bicyclingId = in.readInt();
	}
	
	public static final Parcelable.Creator<SItem> CREATOR = new Parcelable.Creator<SItem>() {
		public SItem createFromParcel(Parcel in){
			return new SItem(in);
		}
		public SItem[] newArray(int size){
			return new SItem[size];
		}
	};
	
}
