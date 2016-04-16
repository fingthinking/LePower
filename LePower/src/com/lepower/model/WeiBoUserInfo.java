package com.lepower.model;

import com.google.gson.annotations.SerializedName;

public class WeiBoUserInfo {
	@SerializedName("avatar_large")
	private String headUrl;
	@SerializedName("name")
	private String nickname;
	private String location;
	private String gender;
	@SerializedName("idstr")
	private String id;
	public String getHeadUrl() {
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
